import re

with open('index.js', 'r', encoding='utf-8') as f:
    content = f.read()

# 1. GET /api/teacher/feedback
get_t_feedback_orig = '''      const data = await store.read();
      const classIdFilter = typeof req.query.classId === 'string' ? req.query.classId.trim() : '';
      const typeFilter = typeof req.query.type === 'string' ? req.query.type.trim().toLowerCase() : '';

      const myClasses = (data.classes || []).filter((item) => item.teacherId === req.authUser.id || req.authUser.role === 'owner');
      const myClassIds = new Set(myClasses.map((item) => item.id));

      const enrollments = data.enrollments || [];
      const myStudentIds = new Set(
        enrollments.filter((entry) => myClassIds.has(entry.classId)).map((entry) => entry.studentId)
      );

      const feedback = [...(data.feedbackEntries || [])]
        .filter((entry) => {
          if (!myStudentIds.has(entry.userId)) {
            return false;
          }
          if (typeFilter && entry.type?.toLowerCase() !== typeFilter) {
            return false;
          }
          if (classIdFilter) {
            const studentEnrolledInClass = enrollments.some(
              (en) => en.studentId === entry.userId && en.classId === classIdFilter
            );
            if (!studentEnrolledInClass) {
              return false;
            }
          }
          return true;
        })
        .sort((left, right) => String(right.createdAt || '').localeCompare(String(left.createdAt || '')))
        .map((entry) => {
          const studentEnrollment = enrollments.find((en) => en.studentId === entry.userId && myClassIds.has(en.classId));
          const classroom = myClasses.find((item) => item.id === studentEnrollment?.classId);
          return sanitizeTeacherFeedbackEntry(entry, data, classroom);
        });

      res.json(teacherFeedbackResponseDto(feedback));'''
get_t_feedback_new = '''      let query = `
        SELECT f.*, u.name as user_display_name, u.email as user_email, u.role as user_role, 'google' as provider,
               c.id as class_id, c.name as class_name
        FROM feedback_entries f
        JOIN enrollments e ON f.user_id = e.student_id
        JOIN classes c ON e.class_id = c.id
        JOIN app_users u ON f.user_id = u.id
        WHERE (c.teacher_id = $1 OR $2 = 'owner')
      `;
      const params = [req.authUser.id, req.authUser.role];
      const classIdFilter = typeof req.query.classId === 'string' ? req.query.classId.trim() : '';
      const typeFilter = typeof req.query.type === 'string' ? req.query.type.trim().toLowerCase() : '';

      if (classIdFilter) {
        query += ` AND c.id = $3`;
        params.push(classIdFilter);
      }
      if (typeFilter) {
        query += ` AND LOWER(f.type) = $${params.length + 1}`;
        params.push(typeFilter);
      }
      query += ` ORDER BY f.created_at DESC`;

      const result = await store.query(query, params);
      const feedback = rowsToCamel(result.rows).map(stripSnakeOnly);
      res.json(teacherFeedbackResponseDto(feedback));'''
content = content.replace(get_t_feedback_orig, get_t_feedback_new)

# 2. PATCH /api/teacher/feedback/:feedbackId/review
patch_review_orig = '''      const data = await store.read();
      const feedback = (data.feedbackEntries || []).find((entry) => entry.id === req.params.feedbackId);
      if (!feedback) {
        return res.status(404).json(errorResponseDto('Feedback not found', 'NOT_FOUND'));
      }

      feedback.reviewedAt = new Date().toISOString();
      feedback.reviewedBy = req.authUser.id;
      await store.write(data);'''
patch_review_new = '''      const result = await store.query(
        `UPDATE feedback_entries 
         SET reviewed_at = NOW(), reviewed_by = $1 
         WHERE id = $2 
         RETURNING *`,
        [req.authUser.id, req.params.feedbackId]
      );
      if (result.rowCount === 0) {
        return res.status(404).json(errorResponseDto('Feedback not found', 'NOT_FOUND'));
      }
      const feedback = rowsToCamel(result.rows)[0];'''
content = content.replace(patch_review_orig, patch_review_new)

# 3. GET /api/classes/:classId/students
get_students_orig = '''      const data = await store.read();
      const enrollments = data.enrollments || [];
      const classes = data.classes || [];
      const classroom = classes.find((item) => item.id === req.params.classId);

      if (!classroom) {
        return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      }

      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') {
        return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      }

      const classEnrollments = enrollments.filter((entry) => entry.classId === classroom.id);
      const studentIds = new Set(classEnrollments.map((entry) => entry.studentId));

      const students = (data.googleUsers || [])
        .filter((user) => studentIds.has(user.id))
        .sort((left, right) => String(left.name || '').localeCompare(String(right.name || '')))
        .map((student) => buildStudentDetail({ classroom, student, data }));

      res.json(classStudentsResponseDto({
        classId: classroom.id,
        students,
      }));'''
get_students_new = '''      const classRes = await store.query('SELECT * FROM classes WHERE id = $1', [req.params.classId]);
      if (classRes.rowCount === 0) {
        return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      }
      const classroom = rowsToCamel(classRes.rows)[0];

      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') {
        return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      }

      const studentsRes = await store.query(`
        SELECT u.*, e.joined_at 
        FROM app_users u 
        JOIN enrollments e ON u.id = e.student_id 
        WHERE e.class_id = $1 
        ORDER BY u.name ASC
      `, [classroom.id]);

      // Instead of deep buildStudentDetail logic, we query the stats
      const studentIds = studentsRes.rows.map(r => r.id);
      let progressRes = { rows: [] };
      let sessionsRes = { rows: [] };
      let notesRes = { rows: [] };
      if (studentIds.length > 0) {
        progressRes = await store.query('SELECT * FROM progress_entries WHERE user_id = ANY($1)', [studentIds]);
        sessionsRes = await store.query('SELECT * FROM practice_sessions WHERE user_id = ANY($1)', [studentIds]);
        notesRes = await store.query('SELECT t.*, u.name as teacher_name FROM teacher_notes t JOIN app_users u ON t.teacher_id = u.id WHERE t.class_id = $1', [classroom.id]);
      }
      
      const allProgress = rowsToCamel(progressRes.rows).map(stripSnakeOnly);
      const allSessions = rowsToCamel(sessionsRes.rows).map(stripSnakeOnly);
      const allNotes = rowsToCamel(notesRes.rows).map(stripSnakeOnly);

      const students = rowsToCamel(studentsRes.rows).map(row => {
         const student = stripSnakeOnly({ ...row, provider: 'google' });
         const progressEntries = allProgress.filter(p => p.userId === student.id).sort((a,b) => String(b.updatedAt || '').localeCompare(String(a.updatedAt || '')));
         const practiceSessions = allSessions.filter(p => p.userId === student.id);
         const teacherNotes = allNotes.filter(n => n.studentId === student.id).sort((a,b) => String(b.createdAt || '').localeCompare(String(a.createdAt || '')));
         const dashboard = buildDashboard({ user: student, progressEntries, practiceSessions });
         return {
           user: sanitizeGoogleUser(student),
           summary: dashboard.summary,
           weakTopics: dashboard.weakTopics,
           recentActivity: dashboard.recentActivity.slice(0, 3),
           lastActiveAt: dashboard.recentActivity.length > 0 ? dashboard.recentActivity[0].createdAt : (student.lastSeenAt || ''),
           progressEntries,
           teacherNotes,
         };
      });

      res.json(classStudentsResponseDto({
        classId: classroom.id,
        students,
      }));'''
content = content.replace(get_students_orig, get_students_new)

with open('index.js', 'w', encoding='utf-8') as f:
    f.write(content)

print("Pass 5 completed!")
