const fs = require('fs');

let content = fs.readFileSync('index.js', 'utf8');

const replacements = [
  {
    name: 'GET /api/admin/export',
    pattern: /app\.get\('\/api\/admin\/export', requireAuth, async \(req, res\) => \{[\s\S]*?\}\);/,
    replacement: `app.get('/api/admin/export', requireAuth, async (req, res) => {
    res.status(501).json(errorResponseDto('Export not supported in SQL mode', 'NOT_IMPLEMENTED'));
  });`
  },
  {
    name: 'GET /api/admin/backup',
    pattern: /app\.get\('\/api\/admin\/backup', requireAuth, async \(req, res\) => \{[\s\S]*?\}\);/,
    replacement: `app.get('/api/admin/backup', requireAuth, async (req, res) => {
    res.status(501).json(errorResponseDto('Backup not supported in SQL mode', 'NOT_IMPLEMENTED'));
  });`
  },
  {
    name: 'POST /api/admin/restore',
    pattern: /app\.post\('\/api\/admin\/restore', requireAuth, async \(req, res\) => \{[\s\S]*?\}\);/,
    replacement: `app.post('/api/admin/restore', requireAuth, async (req, res) => {
    res.status(501).json(errorResponseDto('Restore not supported in SQL mode', 'NOT_IMPLEMENTED'));
  });`
  },
  {
    name: 'GET /api/admin/users/:userId/detail',
    pattern: /app\.get\('\/api\/admin\/users\/:userId\/detail', requireAuth, async \(req, res\) => \{[\s\S]*?\}\);/,
    replacement: `app.get('/api/admin/users/:userId/detail', requireAuth, async (req, res) => {
    if (req.authUser.role !== 'owner') {
      return res.status(403).json(errorResponseDto('Only owners can view user details', 'FORBIDDEN'));
    }
    try {
      const result = await store.query('SELECT * FROM app_users WHERE id = $1', [req.params.userId]);
      if (result.rowCount === 0) return res.status(404).json(errorResponseDto('User not found', 'NOT_FOUND'));
      const user = sanitizeGoogleUser({ ...rowsToCamel(result.rows)[0], provider: 'google' });
      
      const progRes = await store.query('SELECT * FROM progress_entries WHERE user_id = $1', [user.id]);
      const sessRes = await store.query('SELECT * FROM practice_sessions WHERE user_id = $1', [user.id]);
      const feedRes = await store.query('SELECT * FROM feedback_entries WHERE user_id = $1', [user.id]);
      
      res.json(userDetailResponseDto({
        user,
        progressEntries: rowsToCamel(progRes.rows).map(stripSnakeOnly),
        practiceSessions: rowsToCamel(sessRes.rows).map(stripSnakeOnly),
        feedbackEntries: rowsToCamel(feedRes.rows).map(stripSnakeOnly),
      }));
    } catch (error) {
      res.status(500).json(errorResponseDto('Failed to load user detail'));
    }
  });`
  },
  {
    name: 'GET /api/classes/:classId/students/:studentId',
    pattern: /app\.get\('\/api\/classes\/:classId\/students\/:studentId', requireAuth, async \(req, res\) => \{[\s\S]*?\}\);/,
    replacement: `app.get('/api/classes/:classId/students/:studentId', requireAuth, async (req, res) => {
    try {
      const classRes = await store.query('SELECT * FROM classes WHERE id = $1', [req.params.classId]);
      if (classRes.rowCount === 0) return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      const classroom = rowsToCamel(classRes.rows)[0];
      
      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') {
        return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      }
      
      const enrollRes = await store.query('SELECT * FROM enrollments WHERE class_id = $1 AND student_id = $2', [classroom.id, req.params.studentId]);
      if (enrollRes.rowCount === 0) return res.status(404).json(errorResponseDto('Student not enrolled', 'NOT_FOUND'));
      
      const stuRes = await store.query('SELECT * FROM app_users WHERE id = $1', [req.params.studentId]);
      if (stuRes.rowCount === 0) return res.status(404).json(errorResponseDto('Student not found', 'NOT_FOUND'));
      const student = { ...rowsToCamel(stuRes.rows)[0], provider: 'google' };

      const progRes = await store.query('SELECT * FROM progress_entries WHERE user_id = $1', [student.id]);
      const sessRes = await store.query('SELECT * FROM practice_sessions WHERE user_id = $1', [student.id]);
      const notesRes = await store.query('SELECT t.*, u.name as teacher_name FROM teacher_notes t JOIN app_users u ON t.teacher_id = u.id WHERE t.class_id = $1 AND t.student_id = $2', [classroom.id, student.id]);

      const progressEntries = rowsToCamel(progRes.rows).map(stripSnakeOnly).sort((a,b) => String(b.updatedAt || '').localeCompare(String(a.updatedAt || '')));
      const practiceSessions = rowsToCamel(sessRes.rows).map(stripSnakeOnly);
      const teacherNotes = rowsToCamel(notesRes.rows).map(stripSnakeOnly).sort((a,b) => String(b.createdAt || '').localeCompare(String(a.createdAt || '')));
      
      const dashboard = buildDashboard({ user: student, progressEntries, practiceSessions });

      res.json(classStudentDetailResponseDto({
        user: sanitizeGoogleUser(student),
        summary: dashboard.summary,
        weakTopics: dashboard.weakTopics,
        recentActivity: dashboard.recentActivity.slice(0, 5),
        lastActiveAt: dashboard.recentActivity.length > 0 ? dashboard.recentActivity[0].createdAt : (student.lastSeenAt || ''),
        progressEntries,
        teacherNotes,
      }));
    } catch (error) {
      res.status(500).json(errorResponseDto('Failed to load student detail'));
    }
  });`
  }
];

let changedCount = 0;
for (const rep of replacements) {
  if (rep.pattern.test(content)) {
    content = content.replace(rep.pattern, rep.replacement);
    console.log(`Replaced: ${rep.name}`);
    changedCount++;
  } else {
    console.log(`Not matched: ${rep.name}`);
  }
}

fs.writeFileSync('index.js', content, 'utf8');
console.log(`Done! Made ${changedCount} replacements.`);
