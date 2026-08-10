import re

with open('index.js', 'r', encoding='utf-8') as f:
    content = f.read()

# 1. GET /api/users
get_users_orig = '''      const data = await store.read();
      const response = usersResponseDto((data.googleUsers || []).map(sanitizeGoogleUser));
      res.json(response);'''
get_users_new = '''      const result = await store.query('SELECT * FROM app_users ORDER BY created_at ASC');
      const response = usersResponseDto(rowsToCamel(result.rows).map(row => sanitizeGoogleUser({ ...row, provider: 'google' })));
      res.json(response);'''
content = content.replace(get_users_orig, get_users_new)

# 2. GET /api/users/:userId
get_user_orig = '''      const data = await store.read();
      const user = (data.googleUsers || []).find((entry) => entry.id === req.params.userId);
      if (!user) {
        return res.status(404).json(errorResponseDto('User not found', 'NOT_FOUND'));
      }
      res.json(userResponseDto(sanitizeGoogleUser(user)));'''
get_user_new = '''      const result = await store.query('SELECT * FROM app_users WHERE id = $1', [req.params.userId]);
      if (result.rowCount === 0) {
        return res.status(404).json(errorResponseDto('User not found', 'NOT_FOUND'));
      }
      const user = rowsToCamel(result.rows)[0];
      res.json(userResponseDto(sanitizeGoogleUser({ ...user, provider: 'google' })));'''
content = content.replace(get_user_orig, get_user_new)

# 3. GET /api/classes (lines ~1550-1600)
# This one is trickier because it relies on mapping multiple tables.
# I will use a Regex to replace the whole try/catch block inside GET /api/classes
classes_get_pattern = re.compile(
    r"app\.get\('/api/classes',\s*requireAuth,\s*async\s*\(req,\s*res\)\s*=>\s*\{.*?\n\s+try\s*\{\n\s+const data = await store\.read\(\);\n(.*?)\s+res\.json\(classesResponseDto\(\s*\[\.\.\.managed,\s*\.\.\.enrolled\]\s*\)\);\n\s+\}\s*catch\s*\(error\)\s*\{",
    re.DOTALL
)
classes_get_new = '''app.get('/api/classes', requireAuth, async (req, res) => {
    try {
      const [managedRes, enrolledRes, enrollCountRes] = await Promise.all([
        store.query('SELECT * FROM classes WHERE teacher_id = $1 ORDER BY created_at ASC', [req.authUser.id]),
        store.query(`
          SELECT c.*, e.joined_at, u.name as teacher_name, u.email as teacher_email
          FROM classes c
          JOIN enrollments e ON c.id = e.class_id
          JOIN app_users u ON c.teacher_id = u.id
          WHERE e.student_id = $1
          ORDER BY e.joined_at ASC
        `, [req.authUser.id]),
        store.query(`
          SELECT class_id, COUNT(*) as student_count 
          FROM enrollments 
          GROUP BY class_id
        `)
      ]);
      
      const counts = {};
      for (const row of enrollCountRes.rows) {
        counts[row.class_id] = parseInt(row.student_count, 10);
      }
      
      const managed = rowsToCamel(managedRes.rows).map(stripSnakeOnly).map((c) => 
        sanitizeClassroom(c, { studentCount: counts[c.id] || 0 })
      );
      
      const enrolled = rowsToCamel(enrolledRes.rows).map(stripSnakeOnly).map((c) => 
        sanitizeClassroom(c, { 
          studentCount: counts[c.id] || 0,
          joinedAt: c.joinedAt,
          teacherName: c.teacherName || c.teacherEmail || ''
        })
      );
      
      res.json(classesResponseDto([...managed, ...enrolled]));
    } catch (error) {'''
content = classes_get_pattern.sub(classes_get_new, content)

# 4. POST /api/classes
classes_post_pattern = re.compile(
    r"const data = await store\.read\(\);\n\s+const classroom = \{\n\s+id: `class_\$\{crypto\.randomUUID\(\)\}`,\n\s+teacherId: req\.authUser\.id,\n\s+createdAt: new Date\(\)\.toISOString\(\),\n\s+joinCode: createJoinCode\(data\.classes\),\n\s+\.\.\.parsedRequest\.value\.classroom,\n\s+\};\n\s+data\.classes\.push\(classroom\);\n\s+await store\.write\(data\);",
    re.DOTALL
)
classes_post_new = '''const codeRes = await store.query('SELECT join_code FROM classes');
      const existingCodes = codeRes.rows.map(r => r.join_code);
      const classroom = {
        id: `class_${crypto.randomUUID()}`,
        teacherId: req.authUser.id,
        createdAt: new Date().toISOString(),
        joinCode: createJoinCode(existingCodes.map(code => ({ joinCode: code }))),
        ...parsedRequest.value.classroom,
      };
      await store.query(
        `INSERT INTO classes (id, teacher_id, name, description, join_code, created_at, updated_at)
         VALUES ($1, $2, $3, $4, $5, $6::timestamptz, $6::timestamptz)`,
        [classroom.id, classroom.teacherId, classroom.name || 'Untitled class', classroom.description || '', classroom.joinCode, classroom.createdAt]
      );'''
content = classes_post_pattern.sub(classes_post_new, content)

# 5. POST /api/classes/join
classes_join_pattern = re.compile(
    r"const data = await store\.read\(\);\n\s+const classroom = \(data\.classes \|\| \[\]\)\.find\(\(item\) => item\.joinCode === parsedRequest\.value\.joinCode\);\n\s+if \(\!classroom\) \{\n\s+return res\.status\(404\)\.json\(errorResponseDto\('Invalid join code', 'NOT_FOUND'\)\);\n\s+\}\n\s+if \(classroom\.teacherId === req\.authUser\.id\) \{\n\s+return res\.status\(400\)\.json\(errorResponseDto\('You cannot join your own class', 'VALIDATION_ERROR'\)\);\n\s+\}\n\s+const existingEnrollment = \(data\.enrollments \|\| \[\]\)\.find\(\n\s+\(entry\) => entry\.classId === classroom\.id && entry\.studentId === req\.authUser\.id\n\s+\);\n\s+if \(existingEnrollment\) \{\n\s+return res\.status\(400\)\.json\(errorResponseDto\('Already enrolled in this class', 'VALIDATION_ERROR'\)\);\n\s+\}\n\s+const enrollment = \{\n\s+id: `enroll_\$\{crypto\.randomUUID\(\)\}`,\n\s+classId: classroom\.id,\n\s+studentId: req\.authUser\.id,\n\s+joinedAt: new Date\(\)\.toISOString\(\),\n\s+\};\n\s+data\.enrollments\.push\(enrollment\);\n\s+await store\.write\(data\);",
    re.DOTALL
)
classes_join_new = '''const classRes = await store.query('SELECT * FROM classes WHERE join_code = $1', [parsedRequest.value.joinCode]);
      if (classRes.rowCount === 0) {
        return res.status(404).json(errorResponseDto('Invalid join code', 'NOT_FOUND'));
      }
      const classroom = rowsToCamel(classRes.rows)[0];
      if (classroom.teacherId === req.authUser.id) {
        return res.status(400).json(errorResponseDto('You cannot join your own class', 'VALIDATION_ERROR'));
      }
      
      const enrollRes = await store.query('SELECT 1 FROM enrollments WHERE class_id = $1 AND student_id = $2', [classroom.id, req.authUser.id]);
      if (enrollRes.rowCount > 0) {
        return res.status(400).json(errorResponseDto('Already enrolled in this class', 'VALIDATION_ERROR'));
      }
      
      const enrollment = {
        id: `enroll_${crypto.randomUUID()}`,
        classId: classroom.id,
        studentId: req.authUser.id,
        joinedAt: new Date().toISOString(),
      };
      await store.query(
        `INSERT INTO enrollments (id, class_id, student_id, joined_at) VALUES ($1, $2, $3, $4::timestamptz)`,
        [enrollment.id, enrollment.classId, enrollment.studentId, enrollment.joinedAt]
      );'''
content = classes_join_pattern.sub(classes_join_new, content)

with open('index.js', 'w', encoding='utf-8') as f:
    f.write(content)

print("Pass 3 completed!")
