import re

with open('index.js', 'r', encoding='utf-8') as f:
    content = f.read()

# 1. GET /api/classes/:classId/resources
get_res_orig = '''      const data = await store.read();
      const classroom = (data.classes || []).find((item) => item.id === req.params.classId);
      if (!classroom) {
        return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      }

      const isTeacherViewer = ['teacher', 'owner'].includes(req.authUser.role)
        && (classroom.teacherId === req.authUser.id || req.authUser.role === 'owner');
      const isStudentViewer = (data.enrollments || []).some(
        (entry) => entry.classId === classroom.id && entry.studentId === req.authUser.id
      );
      if (!isTeacherViewer && !isStudentViewer) {
        return res.status(403).json(errorResponseDto('You do not have access to this class', 'FORBIDDEN'));
      }

      const resourceItems = (data.classResources || [])
        .filter((item) => item.classId === classroom.id)
        .sort((left, right) => String(right.createdAt || '').localeCompare(String(left.createdAt || '')));
      const resources = await Promise.all(resourceItems.map((item) => sanitizeClassResource(item, data)));
      res.json(classResourcesResponseDto(resources));'''
get_res_new = '''      const classRes = await store.query('SELECT * FROM classes WHERE id = $1', [req.params.classId]);
      if (classRes.rowCount === 0) {
        return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      }
      const classroom = rowsToCamel(classRes.rows)[0];

      const isTeacherViewer = ['teacher', 'owner'].includes(req.authUser.role) && (classroom.teacherId === req.authUser.id || req.authUser.role === 'owner');
      const enrollRes = await store.query('SELECT 1 FROM enrollments WHERE class_id = $1 AND student_id = $2', [classroom.id, req.authUser.id]);
      const isStudentViewer = enrollRes.rowCount > 0;
      
      if (!isTeacherViewer && !isStudentViewer) {
        return res.status(403).json(errorResponseDto('You do not have access to this class', 'FORBIDDEN'));
      }

      const resourcesRes = await store.query(`
        SELECT r.*, u.name as uploader_name, u.email as uploader_email
        FROM class_resources r
        JOIN app_users u ON r.uploaded_by = u.id
        WHERE r.class_id = $1
        ORDER BY r.created_at ASC
      `, [classroom.id]);
      
      const resourceItems = rowsToCamel(resourcesRes.rows).map(stripSnakeOnly);
      
      // Build dummy data object for sanitizeClassResource which expects it
      const dummyData = { googleUsers: resourcesRes.rows.map(r => ({ id: r.uploaded_by, name: r.uploader_name, email: r.uploader_email })) };
      const resources = await Promise.all(resourceItems.map((item) => sanitizeClassResource(item, dummyData)));
      res.json(classResourcesResponseDto(resources));'''
content = content.replace(get_res_orig, get_res_new)

# 2. DELETE /api/classes/:classId/resources/:resourceId
del_res_orig = '''      const data = await store.read();
      const classroom = (data.classes || []).find((item) => item.id === req.params.classId);
      if (!classroom) {
        return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      }
      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') {
        return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      }

      const resourceIndex = (data.classResources || []).findIndex(
        (item) => item.id === req.params.resourceId && item.classId === classroom.id
      );
      if (resourceIndex < 0) {
        return res.status(404).json(errorResponseDto('Resource not found', 'NOT_FOUND'));
      }

      const resource = data.classResources[resourceIndex];
      await deleteClassResourceFile({
        uploadDir,
        classroomId: classroom.id,
        storedFileName: resource.fileName,
      });

      data.classResources.splice(resourceIndex, 1);
      data.auditLogs.push({
        id: `audit_${crypto.randomUUID()}`,
        action: 'resource.deleted',
        actorId: req.authUser.id,
        actorName: req.authUser.displayName || req.authUser.name || req.authUser.email || '',
        targetId: resource.id,
        targetName: resource.title || resource.fileName || '',
        createdAt: new Date().toISOString(),
        details: { title: resource.title, fileName: resource.fileName },
      });
      await store.write(data);'''
del_res_new = '''      const classRes = await store.query('SELECT * FROM classes WHERE id = $1', [req.params.classId]);
      if (classRes.rowCount === 0) {
        return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      }
      const classroom = rowsToCamel(classRes.rows)[0];
      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') {
        return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      }

      const resRes = await store.query('SELECT * FROM class_resources WHERE id = $1 AND class_id = $2', [req.params.resourceId, classroom.id]);
      if (resRes.rowCount === 0) {
        return res.status(404).json(errorResponseDto('Resource not found', 'NOT_FOUND'));
      }
      const resource = rowsToCamel(resRes.rows)[0];

      await deleteClassResourceFile({
        uploadDir,
        classroomId: classroom.id,
        storedFileName: resource.fileName,
      });

      await store.query('DELETE FROM class_resources WHERE id = $1', [resource.id]);
      await store.query(`
        INSERT INTO audit_logs (id, action, actor_id, actor_name, target_id, target_name, details, created_at)
        VALUES ($1, 'resource.deleted', $2, $3, $4, $5, $6::jsonb, $7::timestamptz)
      `, [
        `audit_${crypto.randomUUID()}`, req.authUser.id, req.authUser.displayName || req.authUser.name || req.authUser.email || '',
        resource.id, resource.title || resource.fileName || '', JSON.stringify({ title: resource.title, fileName: resource.fileName }), new Date().toISOString()
      ]);'''
content = content.replace(del_res_orig, del_res_new)

# 3. GET /api/classes/:classId
get_class_orig = '''      const data = await store.read();
      const classroom = (data.classes || []).find((item) => item.id === req.params.classId);
      if (!classroom) {
        return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      }

      const isTeacherViewer = ['teacher', 'owner'].includes(req.authUser.role)
        && (classroom.teacherId === req.authUser.id || req.authUser.role === 'owner');
      const isStudentViewer = (data.enrollments || []).some(
        (entry) => entry.classId === classroom.id && entry.studentId === req.authUser.id
      );

      if (!isTeacherViewer && !isStudentViewer) {
        return res.status(403).json(errorResponseDto('You do not have access to this class', 'FORBIDDEN'));
      }

      const enrollments = (data.enrollments || []).filter((entry) => entry.classId === classroom.id);
      const joinedAt = isStudentViewer
        ? (data.enrollments || []).find(
            (entry) => entry.classId === classroom.id && entry.studentId === req.authUser.id
          )?.joinedAt
        : undefined;

      const teacher = (data.googleUsers || []).find((user) => user.id === classroom.teacherId);

      res.json(classResponseDto({
        ...classroom,
        studentCount: enrollments.length,
        teacherName: teacher ? (teacher.name || teacher.email || '') : '',
        joinedAt,
      }, { isTeacherViewer }));'''
get_class_new = '''      const classRes = await store.query(`
        SELECT c.*, u.name as teacher_name, u.email as teacher_email 
        FROM classes c 
        JOIN app_users u ON c.teacher_id = u.id 
        WHERE c.id = $1
      `, [req.params.classId]);
      if (classRes.rowCount === 0) {
        return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      }
      const classroom = rowsToCamel(classRes.rows)[0];

      const enrollRes = await store.query('SELECT * FROM enrollments WHERE class_id = $1', [classroom.id]);
      const enrollments = rowsToCamel(enrollRes.rows);
      
      const isTeacherViewer = ['teacher', 'owner'].includes(req.authUser.role) && (classroom.teacherId === req.authUser.id || req.authUser.role === 'owner');
      const studentEnrollment = enrollments.find(e => e.studentId === req.authUser.id);
      const isStudentViewer = !!studentEnrollment;

      if (!isTeacherViewer && !isStudentViewer) {
        return res.status(403).json(errorResponseDto('You do not have access to this class', 'FORBIDDEN'));
      }

      res.json(classResponseDto({
        ...classroom,
        studentCount: enrollments.length,
        teacherName: classroom.teacherName || classroom.teacherEmail || '',
        joinedAt: studentEnrollment ? studentEnrollment.joinedAt : undefined,
      }, { isTeacherViewer }));'''
content = content.replace(get_class_orig, get_class_new)

# 4. POST /api/classes/:classId/students/:studentId/feedback
post_teacher_feedback_orig = '''      const data = await store.read();
      const classroom = (data.classes || []).find((item) => item.id === req.params.classId);
      if (!classroom) {
        return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      }
      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') {
        return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      }
      const enrollment = (data.enrollments || []).find(
        (entry) => entry.classId === classroom.id && entry.studentId === req.params.studentId
      );
      if (!enrollment) {
        return res.status(404).json(errorResponseDto('Student is not enrolled in this class', 'NOT_FOUND'));
      }

      const note = {
        id: `note_${crypto.randomUUID()}`,
        classId: classroom.id,
        studentId: req.params.studentId,
        teacherId: req.authUser.id,
        createdAt: new Date().toISOString(),
        ...parsedRequest.value.note,
      };

      data.teacherNotes.push(note);
      data.auditLogs.push({
        id: `audit_${crypto.randomUUID()}`,
        action: 'teacher_note.created',
        actorId: req.authUser.id,
        actorName: req.authUser.displayName || req.authUser.name || req.authUser.email || '',
        targetId: note.id,
        targetName: 'Teacher Note',
        createdAt: new Date().toISOString(),
        details: { classId: classroom.id, status: note.status },
      });
      await store.write(data);
      res.status(201).json(teacherNoteCreatedResponseDto(sanitizeTeacherNote(note, data)));'''
post_teacher_feedback_new = '''      const classRes = await store.query('SELECT * FROM classes WHERE id = $1', [req.params.classId]);
      if (classRes.rowCount === 0) {
        return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      }
      const classroom = rowsToCamel(classRes.rows)[0];
      
      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') {
        return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      }
      
      const enrollRes = await store.query('SELECT 1 FROM enrollments WHERE class_id = $1 AND student_id = $2', [classroom.id, req.params.studentId]);
      if (enrollRes.rowCount === 0) {
        return res.status(404).json(errorResponseDto('Student is not enrolled in this class', 'NOT_FOUND'));
      }

      const note = {
        id: `note_${crypto.randomUUID()}`,
        classId: classroom.id,
        studentId: req.params.studentId,
        teacherId: req.authUser.id,
        createdAt: new Date().toISOString(),
        ...parsedRequest.value.note,
      };

      await store.query(
        `INSERT INTO teacher_notes (id, class_id, student_id, teacher_id, message, status, created_at)
         VALUES ($1, $2, $3, $4, $5, $6, $7::timestamptz)`,
        [note.id, note.classId, note.studentId, note.teacherId, note.message, note.status || '', note.createdAt]
      );
      
      await store.query(`
        INSERT INTO audit_logs (id, action, actor_id, actor_name, target_id, target_name, details, created_at)
        VALUES ($1, 'teacher_note.created', $2, $3, $4, $5, $6::jsonb, $7::timestamptz)
      `, [
        `audit_${crypto.randomUUID()}`, req.authUser.id, req.authUser.displayName || req.authUser.name || req.authUser.email || '',
        note.id, 'Teacher Note', JSON.stringify({ classId: classroom.id, status: note.status }), new Date().toISOString()
      ]);
      
      // Dummy data for sanitizeTeacherNote
      const dummyData = { googleUsers: [{ id: req.authUser.id, name: req.authUser.name || req.authUser.email }] };
      res.status(201).json(teacherNoteCreatedResponseDto(sanitizeTeacherNote(note, dummyData)));'''
content = content.replace(post_teacher_feedback_orig, post_teacher_feedback_new)

with open('index.js', 'w', encoding='utf-8') as f:
    f.write(content)

print("Pass 6 completed!")
