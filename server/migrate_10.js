const fs = require('fs');
let content = fs.readFileSync('index.js', 'utf8');

// The ultimate fallback: just rewrite endpoints that contain store.write or store.read
const endpointsToRewrite = [
  {
    sig: "app.post('/api/sessions', requireAuth",
    replacement: `app.post('/api/sessions', requireAuth, async (req, res) => {
    const parsedRequest = parseSaveSessionRequest(req.body);
    if (!parsedRequest.ok) return res.status(400).json(parsedRequest);
    try {
      const session = {
        id: \`session_\${crypto.randomUUID()}\`,
        userId: req.authUser.id,
        createdAt: new Date().toISOString(),
        ...parsedRequest.value.session,
      };
      await store.query('INSERT INTO practice_sessions (id, user_id, technique_id, score, total_nodes, time_taken_ms, config, history, created_at) VALUES ($1, $2, $3, $4, $5, $6, $7::jsonb, $8::jsonb, $9::timestamptz)', 
        [session.id, session.userId, session.techniqueId, session.score, session.totalNodes, session.timeTakenMs, JSON.stringify(session.config || {}), JSON.stringify(session.history || []), session.createdAt]);
      res.status(201).json(sessionSavedResponseDto(session.id));
    } catch (error) { res.status(500).json(errorResponseDto('Failed to save session')); }
  });`
  },
  {
    sig: "app.post('/api/classes', requireAuth",
    replacement: `app.post('/api/classes', requireAuth, async (req, res) => {
    if (!['teacher', 'owner'].includes(req.authUser.role)) return res.status(403).json(errorResponseDto('Only teachers or owners can create classes', 'FORBIDDEN'));
    const parsedRequest = parseCreateClassRequest(req.body);
    if (!parsedRequest.ok) return res.status(400).json(parsedRequest);
    try {
      const codeRes = await store.query('SELECT join_code FROM classes');
      const existingCodes = codeRes.rows.map(r => ({ joinCode: r.join_code }));
      const classroom = {
        id: \`class_\${crypto.randomUUID()}\`, teacherId: req.authUser.id, createdAt: new Date().toISOString(),
        joinCode: createJoinCode(existingCodes), ...parsedRequest.value.classroom,
      };
      await store.query('INSERT INTO classes (id, teacher_id, name, description, join_code, created_at, updated_at) VALUES ($1, $2, $3, $4, $5, $6::timestamptz, $6::timestamptz)',
        [classroom.id, classroom.teacherId, classroom.name || 'Untitled class', classroom.description || '', classroom.joinCode, classroom.createdAt]);
      res.status(201).json(classResponseDto(sanitizeClassroom(classroom, { studentCount: 0 }), { isTeacherViewer: true }));
    } catch (error) { res.status(500).json(errorResponseDto('Failed to create class')); }
  });`
  },
  {
    sig: "app.post('/api/classes/join', requireAuth",
    replacement: `app.post('/api/classes/join', requireAuth, async (req, res) => {
    const parsedRequest = parseJoinClassRequest(req.body);
    if (!parsedRequest.ok) return res.status(400).json(parsedRequest);
    try {
      const classRes = await store.query('SELECT * FROM classes WHERE join_code = $1', [parsedRequest.value.joinCode]);
      if (classRes.rowCount === 0) return res.status(404).json(errorResponseDto('Invalid join code', 'NOT_FOUND'));
      const classroom = rowsToCamel(classRes.rows)[0];
      if (classroom.teacherId === req.authUser.id) return res.status(400).json(errorResponseDto('You cannot join your own class', 'VALIDATION_ERROR'));
      
      const enrollRes = await store.query('SELECT 1 FROM enrollments WHERE class_id = $1 AND student_id = $2', [classroom.id, req.authUser.id]);
      if (enrollRes.rowCount > 0) return res.status(400).json(errorResponseDto('Already enrolled in this class', 'VALIDATION_ERROR'));
      
      await store.query('INSERT INTO enrollments (id, class_id, student_id, joined_at) VALUES ($1, $2, $3, $4::timestamptz)', 
        [\`enroll_\${crypto.randomUUID()}\`, classroom.id, req.authUser.id, new Date().toISOString()]);
      res.status(201).json(classJoinedResponseDto(classroom.id));
    } catch (error) { res.status(500).json(errorResponseDto('Failed to join class')); }
  });`
  },
  {
    sig: "app.post('/api/classes/:classId/students/:studentId/feedback', requireAuth",
    replacement: `app.post('/api/classes/:classId/students/:studentId/feedback', requireAuth, async (req, res) => {
    if (!['teacher', 'owner'].includes(req.authUser.role)) return res.status(403).json(errorResponseDto('Only teachers or owners can send student feedback', 'FORBIDDEN'));
    const parsedRequest = parseTeacherStudentFeedbackRequest(req.body);
    if (!parsedRequest.ok) return res.status(400).json(parsedRequest);
    try {
      const classRes = await store.query('SELECT * FROM classes WHERE id = $1', [req.params.classId]);
      if (classRes.rowCount === 0) return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      const classroom = rowsToCamel(classRes.rows)[0];
      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      
      const enrollRes = await store.query('SELECT 1 FROM enrollments WHERE class_id = $1 AND student_id = $2', [classroom.id, req.params.studentId]);
      if (enrollRes.rowCount === 0) return res.status(404).json(errorResponseDto('Student is not enrolled in this class', 'NOT_FOUND'));
      
      const note = { id: \`note_\${crypto.randomUUID()}\`, classId: classroom.id, studentId: req.params.studentId, teacherId: req.authUser.id, createdAt: new Date().toISOString(), ...parsedRequest.value.note };
      await store.query('INSERT INTO teacher_notes (id, class_id, student_id, teacher_id, message, status, created_at) VALUES ($1, $2, $3, $4, $5, $6, $7::timestamptz)',
        [note.id, note.classId, note.studentId, note.teacherId, note.message, note.status || '', note.createdAt]);
      res.status(201).json(teacherNoteCreatedResponseDto(sanitizeTeacherNote(note, { googleUsers: [{ id: req.authUser.id, name: req.authUser.name || req.authUser.email }] })));
    } catch (error) { res.status(500).json(errorResponseDto('Failed to create note')); }
  });`
  },
  {
    sig: "app.post('/api/classes/:classId/resources/upload-url', requireAuth",
    replacement: `app.post('/api/classes/:classId/resources/upload-url', requireAuth, async (req, res) => {
    if (!['teacher', 'owner'].includes(req.authUser.role)) return res.status(403).json(errorResponseDto('Only teachers or owners can upload resources', 'FORBIDDEN'));
    const parsedRequest = parseClassResourceUploadInitRequest(req.body);
    if (!parsedRequest.ok) return res.status(400).json(parsedRequest);
    try {
      if (!isCloudStorageConfigured()) return res.status(503).json(errorResponseDto('Cloud storage is not configured for direct uploads', 'UNAVAILABLE'));
      const classRes = await store.query('SELECT * FROM classes WHERE id = $1', [req.params.classId]);
      if (classRes.rowCount === 0) return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      const classroom = rowsToCamel(classRes.rows)[0];
      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      
      const storedFileName = \`\${Date.now()}_\${crypto.randomUUID().slice(0, 8)}_\${sanitizeResourceFileName(parsedRequest.value.fileName)}\`;
      const storagePath = \`classes/\${classroom.id}/\${storedFileName}\`;
      const uploadUrl = await generateUploadUrl(storagePath, parsedRequest.value.mimeType);
      res.json(classResourceUploadInitResponseDto(uploadUrl, storagePath));
    } catch (error) { res.status(500).json(errorResponseDto('Failed to initialize upload')); }
  });`
  },
  {
    sig: "app.post('/api/classes/:classId/resources/complete', requireAuth",
    replacement: `app.post('/api/classes/:classId/resources/complete', requireAuth, async (req, res) => {
    if (!['teacher', 'owner'].includes(req.authUser.role)) return res.status(403).json(errorResponseDto('Only teachers or owners can upload resources', 'FORBIDDEN'));
    const parsedRequest = parseClassResourceUploadCompleteRequest(req.body);
    if (!parsedRequest.ok) return res.status(400).json(parsedRequest);
    try {
      const classRes = await store.query('SELECT * FROM classes WHERE id = $1', [req.params.classId]);
      if (classRes.rowCount === 0) return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      const classroom = rowsToCamel(classRes.rows)[0];
      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      if (!ALLOWED_RESOURCE_MIME_TYPES.has(parsedRequest.value.mimeType)) return res.status(400).json(errorResponseDto('This file type is not allowed', 'VALIDATION_ERROR'));
      if (parsedRequest.value.sizeBytes > RESOURCE_MAX_BYTES) return res.status(400).json(errorResponseDto('File size exceeds limit', 'VALIDATION_ERROR'));

      const resource = { id: \`res_\${crypto.randomUUID()}\`, classId: classroom.id, title: parsedRequest.value.title, type: parsedRequest.value.type || 'resource', fileName: parsedRequest.value.fileName, mimeType: parsedRequest.value.mimeType, sizeBytes: parsedRequest.value.sizeBytes, uploadedBy: req.authUser.id, downloadPath: parsedRequest.value.storagePath, createdAt: new Date().toISOString() };
      await store.query('INSERT INTO class_resources (id, class_id, title, type, file_name, mime_type, size_bytes, uploaded_by, download_path, created_at) VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10::timestamptz)',
        [resource.id, resource.classId, resource.title, resource.type, resource.fileName, resource.mimeType, resource.sizeBytes, resource.uploadedBy, resource.downloadPath, resource.createdAt]);
      res.status(201).json(classResourceCreatedResponseDto(await sanitizeClassResource(resource, { googleUsers: [{ id: req.authUser.id, name: req.authUser.name || req.authUser.email }] })));
    } catch (error) { res.status(500).json(errorResponseDto('Failed to complete upload')); }
  });`
  },
  {
    sig: "app.post('/api/classes/:classId/resources', requireAuth",
    replacement: `app.post('/api/classes/:classId/resources', requireAuth, async (req, res) => {
    if (!['teacher', 'owner'].includes(req.authUser.role)) return res.status(403).json(errorResponseDto('Only teachers or owners can upload resources', 'FORBIDDEN'));
    const parsedRequest = parseClassResourceUploadRequest(req.body);
    if (!parsedRequest.ok) return res.status(400).json(parsedRequest);
    try {
      const classRes = await store.query('SELECT * FROM classes WHERE id = $1', [req.params.classId]);
      if (classRes.rowCount === 0) return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      const classroom = rowsToCamel(classRes.rows)[0];
      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      if (!ALLOWED_RESOURCE_MIME_TYPES.has(parsedRequest.value.mimeType)) return res.status(400).json(errorResponseDto('This file type is not allowed', 'VALIDATION_ERROR'));

      const storedFileName = \`\${Date.now()}_\${crypto.randomUUID().slice(0, 8)}_\${sanitizeResourceFileName(parsedRequest.value.fileName)}\`;
      const downloadPath = await uploadClassResourceFile({ uploadDir, classroomId: classroom.id, storedFileName, binary: req.body, mimeType: parsedRequest.value.mimeType });
      const resource = { id: \`res_\${crypto.randomUUID()}\`, classId: classroom.id, title: parsedRequest.value.title, type: parsedRequest.value.type || 'resource', fileName: storedFileName, mimeType: parsedRequest.value.mimeType, sizeBytes: Buffer.byteLength(req.body), uploadedBy: req.authUser.id, downloadPath, createdAt: new Date().toISOString() };
      await store.query('INSERT INTO class_resources (id, class_id, title, type, file_name, mime_type, size_bytes, uploaded_by, download_path, created_at) VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10::timestamptz)',
        [resource.id, resource.classId, resource.title, resource.type, resource.fileName, resource.mimeType, resource.sizeBytes, resource.uploadedBy, resource.downloadPath, resource.createdAt]);
      res.status(201).json(classResourceCreatedResponseDto(await sanitizeClassResource(resource, { googleUsers: [{ id: req.authUser.id, name: req.authUser.name || req.authUser.email }] })));
    } catch (error) { res.status(500).json(errorResponseDto('Failed to upload resource')); }
  });`
  },
  {
    sig: "app.delete('/api/classes/:classId/resources/:resourceId', requireAuth",
    replacement: `app.delete('/api/classes/:classId/resources/:resourceId', requireAuth, async (req, res) => {
    try {
      const classRes = await store.query('SELECT * FROM classes WHERE id = $1', [req.params.classId]);
      if (classRes.rowCount === 0) return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      const classroom = rowsToCamel(classRes.rows)[0];
      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      const resRes = await store.query('SELECT * FROM class_resources WHERE id = $1 AND class_id = $2', [req.params.resourceId, classroom.id]);
      if (resRes.rowCount === 0) return res.status(404).json(errorResponseDto('Resource not found', 'NOT_FOUND'));
      const resource = rowsToCamel(resRes.rows)[0];
      
      await deleteClassResourceFile({ uploadDir, classroomId: classroom.id, storedFileName: resource.fileName });
      await store.query('DELETE FROM class_resources WHERE id = $1', [resource.id]);
      res.json(successMessageResponseDto('Resource deleted'));
    } catch (error) { res.status(500).json(errorResponseDto('Failed to delete resource')); }
  });`
  },
  {
    sig: "app.put('/api/users/:userId/role', requireAuth",
    replacement: `app.put('/api/users/:userId/role', requireAuth, async (req, res) => {
    if (req.authUser.role !== 'owner') return res.status(403).json(errorResponseDto('Only owners can manage roles', 'FORBIDDEN'));
    const parsedRequest = parseUpdateUserRoleRequest(req.body);
    if (!parsedRequest.ok) return res.status(400).json(parsedRequest);
    try {
      const { newRole } = parsedRequest.value;
      const targetUserId = req.params.userId;
      if (targetUserId === req.authUser.id) return res.status(400).json(errorResponseDto('Cannot change own role', 'VALIDATION_ERROR'));
      const userRes = await store.query('SELECT * FROM app_users WHERE id = $1', [targetUserId]);
      if (userRes.rowCount === 0) return res.status(404).json(errorResponseDto('User not found', 'NOT_FOUND'));
      await store.query('UPDATE app_users SET role = $1 WHERE id = $2', [newRole, targetUserId]);
      res.json(successMessageResponseDto('User role updated'));
    } catch (error) { res.status(500).json(errorResponseDto('Failed to update role')); }
  });`
  },
  {
    sig: "app.post('/save-session', async",
    replacement: `app.post('/save-session', async (req, res) => { res.status(410).json({ error: 'Endpoint removed' }); });`
  },
  {
    sig: "app.get('/sessions', async",
    replacement: `app.get('/sessions', async (req, res) => { res.status(410).json({ error: 'Endpoint removed' }); });`
  },
  {
    sig: "app.post('/share-visualization', async",
    replacement: `app.post('/share-visualization', async (req, res) => { res.status(410).json({ error: 'Endpoint removed' }); });`
  },
  {
    sig: "app.get('/shared/:shareId', async",
    replacement: `app.get('/shared/:shareId', async (req, res) => { res.status(410).json({ error: 'Endpoint removed' }); });`
  },
  {
    sig: "app.post('/save-preferences', async",
    replacement: `app.post('/save-preferences', async (req, res) => { res.status(410).json({ error: 'Endpoint removed' }); });`
  },
  {
    sig: "app.get('/preferences', async",
    replacement: `app.get('/preferences', async (req, res) => { res.status(410).json({ error: 'Endpoint removed' }); });`
  }
];

let changedCount = 0;
for (const rep of endpointsToRewrite) {
  const startIdx = content.indexOf(rep.sig);
  if (startIdx !== -1) {
    // Find the end of the app.get/post block
    let openBraces = 0;
    let endIdx = -1;
    let started = false;
    for (let i = startIdx; i < content.length; i++) {
      if (content[i] === '{') {
        openBraces++;
        started = true;
      }
      if (content[i] === '}') {
        openBraces--;
        if (started && openBraces === 0) {
          // Look for ); after the closing brace
          if (content.slice(i, i + 5).includes(');')) {
            endIdx = content.indexOf(');', i) + 2;
            break;
          }
        }
      }
    }
    if (endIdx !== -1) {
      const originalBlock = content.slice(startIdx, endIdx);
      content = content.replace(originalBlock, rep.replacement);
      console.log(`Replaced: ${rep.sig}`);
      changedCount++;
    } else {
      console.log(`Could not find end of: ${rep.sig}`);
    }
  } else {
    console.log(`Not matched: ${rep.sig}`);
  }
}

fs.writeFileSync('index.js', content, 'utf8');
console.log(`Done! Made ${changedCount} replacements.`);
