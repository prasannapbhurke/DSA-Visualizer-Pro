const fs = require('fs');
let content = fs.readFileSync('index.js', 'utf8');

const endpointsToRewrite = [
  {
    sig: "app.post('/api/auth/google', async (req, res)",
    replacement: `app.post('/api/auth/google', async (req, res) => {
    try {
      const { credential } = req.body;
      const ticket = await authClient.verifyIdToken({ idToken: credential, audience: process.env.GOOGLE_CLIENT_ID });
      const { sub: googleId, email, name, picture: avatarUrl } = ticket.getPayload();
      
      const { token, refreshToken } = createSessionTokens({ id: googleId, email, name, role: 'student', type: 'google' });
      
      const userRes = await store.query('SELECT * FROM app_users WHERE google_id = $1 OR email = $2', [googleId, email]);
      let user;
      
      if (userRes.rowCount > 0) {
        user = rowsToCamel(userRes.rows)[0];
        await store.query('UPDATE app_users SET name = $1, last_seen_at = $2::timestamptz, refresh_token = $3, token = $4 WHERE id = $5', 
          [name || user.name, new Date().toISOString(), refreshToken, token, user.id]);
      } else {
        const newId = \`user_\${crypto.randomUUID()}\`;
        await store.query(\`INSERT INTO app_users (id, google_id, email, name, avatar_url, role, refresh_token, token, created_at, last_seen_at) 
                           VALUES ($1, $2, $3, $4, $5, 'student', $6, $7, $8::timestamptz, $8::timestamptz)\`, 
          [newId, googleId, email, name || email.split('@')[0], avatarUrl || '', refreshToken, token, new Date().toISOString()]);
        
        const newRes = await store.query('SELECT * FROM app_users WHERE id = $1', [newId]);
        user = rowsToCamel(newRes.rows)[0];
      }

      res.cookie('refreshToken', refreshToken, { httpOnly: true, secure: process.env.NODE_ENV === 'production', sameSite: 'lax', maxAge: 7 * 24 * 60 * 60 * 1000, path: '/api/auth' });
      res.json(authResponseDto(sanitizeGoogleUser({ ...user, provider: 'google' }), token));
    } catch (error) { res.status(401).json(errorResponseDto('Invalid Google token', 'UNAUTHORIZED')); }
  });`
  },
  {
    sig: "app.post('/api/auth/google-access-token', async (req, res)",
    replacement: `app.post('/api/auth/google-access-token', async (req, res) => {
    try {
      const { access_token } = req.body;
      const response = await fetch('https://www.googleapis.com/oauth2/v3/userinfo', { headers: { Authorization: \`Bearer \${access_token}\` } });
      if (!response.ok) throw new Error('Failed to fetch user info from Google');
      
      const { sub: googleId, email, name, picture: avatarUrl } = await response.json();
      const { token, refreshToken } = createSessionTokens({ id: googleId, email, name, role: 'student', type: 'google' });
      
      const userRes = await store.query('SELECT * FROM app_users WHERE google_id = $1 OR email = $2', [googleId, email]);
      let user;
      
      if (userRes.rowCount > 0) {
        user = rowsToCamel(userRes.rows)[0];
        await store.query('UPDATE app_users SET name = $1, last_seen_at = $2::timestamptz, refresh_token = $3, token = $4 WHERE id = $5', 
          [name || user.name, new Date().toISOString(), refreshToken, token, user.id]);
      } else {
        const newId = \`user_\${crypto.randomUUID()}\`;
        await store.query(\`INSERT INTO app_users (id, google_id, email, name, avatar_url, role, refresh_token, token, created_at, last_seen_at) 
                           VALUES ($1, $2, $3, $4, $5, 'student', $6, $7, $8::timestamptz, $8::timestamptz)\`, 
          [newId, googleId, email, name || email.split('@')[0], avatarUrl || '', refreshToken, token, new Date().toISOString()]);
        
        const newRes = await store.query('SELECT * FROM app_users WHERE id = $1', [newId]);
        user = rowsToCamel(newRes.rows)[0];
      }

      res.cookie('refreshToken', refreshToken, { httpOnly: true, secure: process.env.NODE_ENV === 'production', sameSite: 'lax', maxAge: 7 * 24 * 60 * 60 * 1000, path: '/api/auth' });
      res.json(authResponseDto(sanitizeGoogleUser({ ...user, provider: 'google' }), token));
    } catch (error) { res.status(401).json(errorResponseDto('Invalid Google access token', 'UNAUTHORIZED')); }
  });`
  },
  {
    sig: "app.post('/api/auth/refresh', async (req, res)",
    replacement: `app.post('/api/auth/refresh', async (req, res) => {
    try {
      const refreshToken = req.cookies.refreshToken;
      if (!refreshToken) return res.status(401).json(errorResponseDto('No refresh token provided', 'UNAUTHORIZED'));
      
      const userRes = await store.query('SELECT * FROM app_users WHERE refresh_token = $1', [refreshToken]);
      if (userRes.rowCount === 0) return res.status(401).json(errorResponseDto('Invalid refresh token', 'UNAUTHORIZED'));
      const user = rowsToCamel(userRes.rows)[0];
      
      const { token } = createSessionTokens({ id: user.googleId || user.id, email: user.email, name: user.name, role: user.role, type: 'google' });
      await store.query('UPDATE app_users SET token = $1, last_seen_at = $2::timestamptz WHERE id = $3', [token, new Date().toISOString(), user.id]);
      
      res.json(authResponseDto(sanitizeGoogleUser({ ...user, provider: 'google' }), token));
    } catch (error) { res.status(401).json(errorResponseDto('Invalid refresh token', 'UNAUTHORIZED')); }
  });`
  },
  {
    sig: "app.get('/api/techniques/:id/progress', requireAuth",
    replacement: `app.get('/api/techniques/:id/progress', requireAuth, async (req, res) => {
    try {
      const progRes = await store.query('SELECT * FROM progress_entries WHERE user_id = $1 AND technique_id = $2', [req.authUser.id, req.params.id]);
      if (progRes.rowCount === 0) return res.json({ data: null });
      res.json(progressEntryResponseDto(stripSnakeOnly(rowsToCamel(progRes.rows)[0])));
    } catch (error) { res.status(500).json(errorResponseDto('Failed to load progress')); }
  });`
  },
  {
    sig: "app.get('/api/teacher/feedback', requireAuth",
    replacement: `app.get('/api/teacher/feedback', requireAuth, async (req, res) => {
    if (!['teacher', 'owner'].includes(req.authUser.role)) return res.status(403).json(errorResponseDto('Only teachers or owners can view teacher feedback', 'FORBIDDEN'));
    try {
      let query = \`SELECT f.*, u.name as user_display_name, u.email as user_email, u.role as user_role, 'google' as provider, c.id as class_id, c.name as class_name FROM feedback_entries f JOIN enrollments e ON f.user_id = e.student_id JOIN classes c ON e.class_id = c.id JOIN app_users u ON f.user_id = u.id WHERE (c.teacher_id = $1 OR $2 = 'owner')\`;
      const params = [req.authUser.id, req.authUser.role];
      const classIdFilter = typeof req.query.classId === 'string' ? req.query.classId.trim() : '';
      const typeFilter = typeof req.query.type === 'string' ? req.query.type.trim().toLowerCase() : '';
      if (classIdFilter) { query += \` AND c.id = $3\`; params.push(classIdFilter); }
      if (typeFilter) { query += \` AND LOWER(f.type) = $\${params.length + 1}\`; params.push(typeFilter); }
      query += \` ORDER BY f.created_at DESC\`;
      const result = await store.query(query, params);
      res.json(teacherFeedbackResponseDto(rowsToCamel(result.rows).map(stripSnakeOnly)));
    } catch (error) { res.status(500).json(errorResponseDto('Failed to load feedback')); }
  });`
  },
  {
    sig: "app.patch('/api/teacher/feedback/:feedbackId/review', requireAuth",
    replacement: `app.patch('/api/teacher/feedback/:feedbackId/review', requireAuth, async (req, res) => {
    if (!['teacher', 'owner'].includes(req.authUser.role)) return res.status(403).json(errorResponseDto('Only teachers or owners can review feedback', 'FORBIDDEN'));
    try {
      const result = await store.query(\`UPDATE feedback_entries SET reviewed_at = NOW(), reviewed_by = $1 WHERE id = $2 RETURNING *\`, [req.authUser.id, req.params.feedbackId]);
      if (result.rowCount === 0) return res.status(404).json(errorResponseDto('Feedback not found', 'NOT_FOUND'));
      res.json(teacherFeedbackResponseDto([stripSnakeOnly(rowsToCamel(result.rows)[0])]));
    } catch (error) { res.status(500).json(errorResponseDto('Failed to review feedback')); }
  });`
  },
  {
    sig: "app.get('/api/users', requireAuth",
    replacement: `app.get('/api/users', requireAuth, async (req, res) => {
    if (req.authUser.role !== 'owner') return res.status(403).json(errorResponseDto('Only owners can list users', 'FORBIDDEN'));
    try {
      const result = await store.query('SELECT * FROM app_users ORDER BY created_at ASC');
      res.json(usersResponseDto(rowsToCamel(result.rows).map(row => sanitizeGoogleUser({ ...row, provider: 'google' }))));
    } catch (error) { res.status(500).json(errorResponseDto('Failed to list users')); }
  });`
  },
  {
    sig: "app.get('/api/classes/:classId/students', requireAuth",
    replacement: `app.get('/api/classes/:classId/students', requireAuth, async (req, res) => {
    try {
      const classRes = await store.query('SELECT * FROM classes WHERE id = $1', [req.params.classId]);
      if (classRes.rowCount === 0) return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      const classroom = rowsToCamel(classRes.rows)[0];
      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      const studentsRes = await store.query(\`SELECT u.*, e.joined_at FROM app_users u JOIN enrollments e ON u.id = e.student_id WHERE e.class_id = $1 ORDER BY u.name ASC\`, [classroom.id]);
      const studentIds = studentsRes.rows.map(r => r.id);
      let allProgress = [], allSessions = [], allNotes = [];
      if (studentIds.length > 0) {
        allProgress = rowsToCamel((await store.query('SELECT * FROM progress_entries WHERE user_id = ANY($1)', [studentIds])).rows).map(stripSnakeOnly);
        allSessions = rowsToCamel((await store.query('SELECT * FROM practice_sessions WHERE user_id = ANY($1)', [studentIds])).rows).map(stripSnakeOnly);
        allNotes = rowsToCamel((await store.query('SELECT t.*, u.name as teacher_name FROM teacher_notes t JOIN app_users u ON t.teacher_id = u.id WHERE t.class_id = $1', [classroom.id])).rows).map(stripSnakeOnly);
      }
      const students = rowsToCamel(studentsRes.rows).map(row => {
         const student = stripSnakeOnly({ ...row, provider: 'google' });
         const progressEntries = allProgress.filter(p => p.userId === student.id).sort((a,b) => String(b.updatedAt || '').localeCompare(String(a.updatedAt || '')));
         const practiceSessions = allSessions.filter(p => p.userId === student.id);
         const teacherNotes = allNotes.filter(n => n.studentId === student.id).sort((a,b) => String(b.createdAt || '').localeCompare(String(a.createdAt || '')));
         const dashboard = buildDashboard({ user: student, progressEntries, practiceSessions });
         return {
           user: sanitizeGoogleUser(student), summary: dashboard.summary, weakTopics: dashboard.weakTopics, recentActivity: dashboard.recentActivity.slice(0, 3),
           lastActiveAt: dashboard.recentActivity.length > 0 ? dashboard.recentActivity[0].createdAt : (student.lastSeenAt || ''), progressEntries, teacherNotes,
         };
      });
      res.json(classStudentsResponseDto({ classId: classroom.id, students }));
    } catch (error) { res.status(500).json(errorResponseDto('Failed to load students')); }
  });`
  },
  {
    sig: "app.get('/api/classes/:classId', requireAuth",
    replacement: `app.get('/api/classes/:classId', requireAuth, async (req, res) => {
    try {
      const classRes = await store.query(\`SELECT c.*, u.name as teacher_name, u.email as teacher_email FROM classes c JOIN app_users u ON c.teacher_id = u.id WHERE c.id = $1\`, [req.params.classId]);
      if (classRes.rowCount === 0) return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      const classroom = rowsToCamel(classRes.rows)[0];
      const enrollRes = await store.query('SELECT * FROM enrollments WHERE class_id = $1', [classroom.id]);
      const enrollments = rowsToCamel(enrollRes.rows);
      const isTeacherViewer = ['teacher', 'owner'].includes(req.authUser.role) && (classroom.teacherId === req.authUser.id || req.authUser.role === 'owner');
      const studentEnrollment = enrollments.find(e => e.studentId === req.authUser.id);
      if (!isTeacherViewer && !studentEnrollment) return res.status(403).json(errorResponseDto('You do not have access to this class', 'FORBIDDEN'));
      res.json(classResponseDto({ ...classroom, studentCount: enrollments.length, teacherName: classroom.teacherName || classroom.teacherEmail || '', joinedAt: studentEnrollment ? studentEnrollment.joinedAt : undefined }, { isTeacherViewer }));
    } catch (error) { res.status(500).json(errorResponseDto('Failed to load class')); }
  });`
  }
];

let changedCount = 0;
for (const rep of endpointsToRewrite) {
  const startIdx = content.indexOf(rep.sig);
  if (startIdx !== -1) {
    let openBraces = 0;
    let endIdx = -1;
    let started = false;
    for (let i = startIdx; i < content.length; i++) {
      if (content[i] === '{') { openBraces++; started = true; }
      if (content[i] === '}') {
        openBraces--;
        if (started && openBraces === 0) {
          if (content.slice(i, i + 5).includes(');')) { endIdx = content.indexOf(');', i) + 2; break; }
        }
      }
    }
    if (endIdx !== -1) {
      const originalBlock = content.slice(startIdx, endIdx);
      content = content.replace(originalBlock, rep.replacement);
      console.log('Replaced: ' + rep.sig);
      changedCount++;
    } else { console.log('Could not find end of: ' + rep.sig); }
  } else { console.log('Not matched: ' + rep.sig); }
}
fs.writeFileSync('index.js', content, 'utf8');
console.log('Done! Made ' + changedCount + ' replacements.');
