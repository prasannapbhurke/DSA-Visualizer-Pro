import re

with open('index.js', 'r', encoding='utf-8') as f:
    content = f.read()

# 1. POST /api/classes/:classId/resources/upload-url
upload_url_orig = '''      const data = await store.read();
      const classroom = (data.classes || []).find((item) => item.id === req.params.classId);
      if (!classroom) {
        return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      }
      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') {
        return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      }'''
upload_url_new = '''      const classRes = await store.query('SELECT * FROM classes WHERE id = $1', [req.params.classId]);
      if (classRes.rowCount === 0) {
        return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      }
      const classroom = rowsToCamel(classRes.rows)[0];
      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') {
        return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      }'''
content = content.replace(upload_url_orig, upload_url_new)

# 2. POST /api/classes/:classId/resources/complete
upload_complete_orig = '''      const data = await store.read();
      const classroom = (data.classes || []).find((item) => item.id === req.params.classId);
      if (!classroom) {
        return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      }
      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') {
        return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      }
      if (!ALLOWED_RESOURCE_MIME_TYPES.has(parsedRequest.value.mimeType)) {
        return res.status(400).json(errorResponseDto('This file type is not allowed', 'VALIDATION_ERROR'));
      }
      if (parsedRequest.value.sizeBytes > RESOURCE_MAX_BYTES) {
        return res.status(400).json(errorResponseDto('File size exceeds limit', 'VALIDATION_ERROR'));
      }

      const resource = {
        id: `res_${crypto.randomUUID()}`,
        classId: classroom.id,
        title: parsedRequest.value.title,
        type: parsedRequest.value.type || 'resource',
        fileName: parsedRequest.value.fileName,
        mimeType: parsedRequest.value.mimeType,
        sizeBytes: parsedRequest.value.sizeBytes,
        uploadedBy: req.authUser.id,
        downloadPath: parsedRequest.value.storagePath,
        createdAt: new Date().toISOString(),
      };

      data.classResources.push(resource);
      data.auditLogs.push({
        id: `audit_${crypto.randomUUID()}`,
        action: 'resource.uploaded',
        actorId: req.authUser.id,
        actorName: req.authUser.displayName || req.authUser.name || req.authUser.email || '',
        targetId: resource.id,
        targetName: resource.title || resource.fileName || '',
        createdAt: new Date().toISOString(),
        details: { title: resource.title, type: resource.type, fileName: resource.fileName, sizeBytes: resource.sizeBytes },
      });
      await store.write(data);
      res.status(201).json(classResourceCreatedResponseDto(await sanitizeClassResource(resource, data)));'''
upload_complete_new = '''      const classRes = await store.query('SELECT * FROM classes WHERE id = $1', [req.params.classId]);
      if (classRes.rowCount === 0) {
        return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      }
      const classroom = rowsToCamel(classRes.rows)[0];
      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') {
        return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      }
      if (!ALLOWED_RESOURCE_MIME_TYPES.has(parsedRequest.value.mimeType)) {
        return res.status(400).json(errorResponseDto('This file type is not allowed', 'VALIDATION_ERROR'));
      }
      if (parsedRequest.value.sizeBytes > RESOURCE_MAX_BYTES) {
        return res.status(400).json(errorResponseDto('File size exceeds limit', 'VALIDATION_ERROR'));
      }

      const resource = {
        id: `res_${crypto.randomUUID()}`,
        classId: classroom.id,
        title: parsedRequest.value.title,
        type: parsedRequest.value.type || 'resource',
        fileName: parsedRequest.value.fileName,
        mimeType: parsedRequest.value.mimeType,
        sizeBytes: parsedRequest.value.sizeBytes,
        uploadedBy: req.authUser.id,
        downloadPath: parsedRequest.value.storagePath,
        createdAt: new Date().toISOString(),
      };

      await store.query(
        `INSERT INTO class_resources (id, class_id, title, type, file_name, mime_type, size_bytes, uploaded_by, download_path, created_at)
         VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10::timestamptz)`,
        [resource.id, resource.classId, resource.title, resource.type, resource.fileName, resource.mimeType, resource.sizeBytes, resource.uploadedBy, resource.downloadPath, resource.createdAt]
      );
      await store.query(`
        INSERT INTO audit_logs (id, action, actor_id, actor_name, target_id, target_name, details, created_at)
        VALUES ($1, 'resource.uploaded', $2, $3, $4, $5, $6::jsonb, $7::timestamptz)
      `, [
        `audit_${crypto.randomUUID()}`, req.authUser.id, req.authUser.displayName || req.authUser.name || req.authUser.email || '',
        resource.id, resource.title || resource.fileName || '', JSON.stringify({ title: resource.title, type: resource.type, fileName: resource.fileName, sizeBytes: resource.sizeBytes }), new Date().toISOString()
      ]);

      const dummyData = { googleUsers: [{ id: req.authUser.id, name: req.authUser.name || req.authUser.email }] };
      res.status(201).json(classResourceCreatedResponseDto(await sanitizeClassResource(resource, dummyData)));'''
content = content.replace(upload_complete_orig, upload_complete_new)

# 3. POST /api/classes/:classId/resources (binary upload)
upload_bin_orig = '''      const data = await store.read();
      const classroom = (data.classes || []).find((item) => item.id === req.params.classId);
      if (!classroom) {
        return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      }
      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') {
        return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      }
      if (!ALLOWED_RESOURCE_MIME_TYPES.has(parsedRequest.value.mimeType)) {
        return res.status(400).json(errorResponseDto('This file type is not allowed', 'VALIDATION_ERROR'));
      }

      const storedFileName = `${Date.now()}_${crypto.randomUUID().slice(0, 8)}_${sanitizeResourceFileName(parsedRequest.value.fileName)}`;
      const downloadPath = await uploadClassResourceFile({
        uploadDir,
        classroomId: classroom.id,
        storedFileName,
        binary: req.body,
        mimeType: parsedRequest.value.mimeType,
      });

      const resource = {
        id: `res_${crypto.randomUUID()}`,
        classId: classroom.id,
        title: parsedRequest.value.title,
        type: parsedRequest.value.type || 'resource',
        fileName: storedFileName,
        mimeType: parsedRequest.value.mimeType,
        sizeBytes: Buffer.byteLength(req.body),
        uploadedBy: req.authUser.id,
        downloadPath,
        createdAt: new Date().toISOString(),
      };

      data.classResources.push(resource);
      data.auditLogs.push({
        id: `audit_${crypto.randomUUID()}`,
        action: 'resource.uploaded_binary',
        actorId: req.authUser.id,
        actorName: req.authUser.displayName || req.authUser.name || req.authUser.email || '',
        targetId: resource.id,
        targetName: resource.title || resource.fileName || '',
        createdAt: new Date().toISOString(),
        details: { title: resource.title, type: resource.type, fileName: resource.fileName },
      });
      await store.write(data);
      res.status(201).json(classResourceCreatedResponseDto(await sanitizeClassResource(resource, data)));'''
upload_bin_new = '''      const classRes = await store.query('SELECT * FROM classes WHERE id = $1', [req.params.classId]);
      if (classRes.rowCount === 0) {
        return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      }
      const classroom = rowsToCamel(classRes.rows)[0];
      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') {
        return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      }
      if (!ALLOWED_RESOURCE_MIME_TYPES.has(parsedRequest.value.mimeType)) {
        return res.status(400).json(errorResponseDto('This file type is not allowed', 'VALIDATION_ERROR'));
      }

      const storedFileName = `${Date.now()}_${crypto.randomUUID().slice(0, 8)}_${sanitizeResourceFileName(parsedRequest.value.fileName)}`;
      const downloadPath = await uploadClassResourceFile({
        uploadDir,
        classroomId: classroom.id,
        storedFileName,
        binary: req.body,
        mimeType: parsedRequest.value.mimeType,
      });

      const resource = {
        id: `res_${crypto.randomUUID()}`,
        classId: classroom.id,
        title: parsedRequest.value.title,
        type: parsedRequest.value.type || 'resource',
        fileName: storedFileName,
        mimeType: parsedRequest.value.mimeType,
        sizeBytes: Buffer.byteLength(req.body),
        uploadedBy: req.authUser.id,
        downloadPath,
        createdAt: new Date().toISOString(),
      };

      await store.query(
        `INSERT INTO class_resources (id, class_id, title, type, file_name, mime_type, size_bytes, uploaded_by, download_path, created_at)
         VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10::timestamptz)`,
        [resource.id, resource.classId, resource.title, resource.type, resource.fileName, resource.mimeType, resource.sizeBytes, resource.uploadedBy, resource.downloadPath, resource.createdAt]
      );
      await store.query(`
        INSERT INTO audit_logs (id, action, actor_id, actor_name, target_id, target_name, details, created_at)
        VALUES ($1, 'resource.uploaded_binary', $2, $3, $4, $5, $6::jsonb, $7::timestamptz)
      `, [
        `audit_${crypto.randomUUID()}`, req.authUser.id, req.authUser.displayName || req.authUser.name || req.authUser.email || '',
        resource.id, resource.title || resource.fileName || '', JSON.stringify({ title: resource.title, type: resource.type, fileName: resource.fileName }), new Date().toISOString()
      ]);

      const dummyData = { googleUsers: [{ id: req.authUser.id, name: req.authUser.name || req.authUser.email }] };
      res.status(201).json(classResourceCreatedResponseDto(await sanitizeClassResource(resource, dummyData)));'''
content = content.replace(upload_bin_orig, upload_bin_new)


with open('index.js', 'w', encoding='utf-8') as f:
    f.write(content)

print("Pass 7 completed!")
