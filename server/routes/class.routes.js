const express = require('express');
const crypto = require('crypto');
const fs = require('fs');
const path = require('path');
const {
  classResourceCreatedResponseDto,
  classResourcesResponseDto,
  classResourceUploadInitResponseDto,
  classResponseDto,
  classesResponseDto,
  classStudentsResponseDto,
  errorResponseDto,
  parseClassResourceUploadCompleteRequest,
  parseClassResourceUploadInitRequest,
  parseClassResourceUploadRequest,
  parseCreateClassRequest,
  parseJoinClassRequest,
  parseTeacherStudentFeedbackRequest,
  parseUpdateEnrollmentProfileRequest,
  studentDetailResponseDto,
  successMessageResponseDto,
  teacherNoteCreatedResponseDto,
} = require('../dto');
const { rowsToCamel, stripSnakeOnly } = require('../postgresStore');
const { sanitizeClassroom, sanitizeGoogleUser } = require('../utils/sanitize');
const { buildDashboard } = require('../utils/dashboard');

const CLASS_JOIN_CODE_LENGTH = 6;
const RESOURCE_MAX_BYTES = Math.max(
  1,
  Number(process.env.RESOURCE_MAX_BYTES || 500 * 1024 * 1024)
);
const ALLOWED_RESOURCE_MIME_TYPES = new Set([
  'application/pdf',
  'text/plain',
  'image/png',
  'image/jpeg',
  'image/jpg',
]);

const SUPABASE_URL = process.env.SUPABASE_URL || '';
const SUPABASE_SERVICE_ROLE_KEY = process.env.SUPABASE_SERVICE_ROLE_KEY || '';
const SUPABASE_STORAGE_BUCKET = process.env.SUPABASE_STORAGE_BUCKET || '';
const SUPABASE_SIGNED_DOWNLOAD_EXPIRES_IN_SECONDS = Math.max(
  60,
  Number(process.env.SUPABASE_SIGNED_DOWNLOAD_EXPIRES_IN_SECONDS || 600)
);
const STORAGE_PATH_PREFIX = 'storage://';

const isCloudStorageConfigured = () =>
  Boolean(SUPABASE_URL.trim() && SUPABASE_SERVICE_ROLE_KEY.trim() && SUPABASE_STORAGE_BUCKET.trim());

const sanitizeResourceFileName = (value) =>
  String(value || '').replace(/[^a-zA-Z0-9._-]/g, '_').slice(0, 120) || 'resource.bin';

const createJoinCode = (classes) => {
  const alphabet = 'ABCDEFGHJKLMNPQRSTUVWXYZ23456789';
  let code = '';
  do {
    code = Array.from({ length: CLASS_JOIN_CODE_LENGTH }, () =>
      alphabet[Math.floor(Math.random() * alphabet.length)]
    ).join('');
  } while ((classes || []).some((entry) => entry.joinCode === code));
  return code;
};

const sanitizeTeacherNote = (note, data) => {
  const teacher = (data?.googleUsers || []).find((user) => user.id === note.teacherId);
  return {
    id: note.id,
    classId: note.classId,
    studentId: note.studentId,
    teacherId: note.teacherId,
    message: note.message || '',
    status: note.status || '',
    createdAt: note.createdAt || '',
    teacherName: teacher ? (teacher.name || teacher.email || '') : '',
  };
};

const resolveResourceStoragePath = (resource) => {
  const value = String(resource?.downloadPath || '').trim();
  if (value.startsWith(STORAGE_PATH_PREFIX)) {
    return value.slice(STORAGE_PATH_PREFIX.length);
  }
  if (!isCloudStorageConfigured()) {
    return value.replace(/^\/uploads\//, '');
  }
  const baseUrl = SUPABASE_URL.replace(/\/+$/, '');
  const publicPrefix = `${baseUrl}/storage/v1/object/public/${encodeURIComponent(SUPABASE_STORAGE_BUCKET)}/`;
  if (value.startsWith(publicPrefix)) {
    return decodeURIComponent(value.slice(publicPrefix.length));
  }
  const signedPrefix = `${baseUrl}/storage/v1/object/sign/${encodeURIComponent(SUPABASE_STORAGE_BUCKET)}/`;
  if (value.startsWith(signedPrefix)) {
    return decodeURIComponent(value.slice(signedPrefix.length).split('?')[0]);
  }
  return value;
};

const createSignedClassResourceDownloadUrl = async (storagePath) => {
  if (!isCloudStorageConfigured()) {
    return `/uploads/${storagePath}`;
  }
  const baseUrl = SUPABASE_URL.replace(/\/+$/, '');
  const endpoint = `${baseUrl}/storage/v1/object/sign/${encodeURIComponent(SUPABASE_STORAGE_BUCKET)}/${storagePath}`;
  const response = await fetch(endpoint, {
    method: 'POST',
    headers: {
      Authorization: `Bearer ${SUPABASE_SERVICE_ROLE_KEY}`,
      apikey: SUPABASE_SERVICE_ROLE_KEY,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ expiresIn: SUPABASE_SIGNED_DOWNLOAD_EXPIRES_IN_SECONDS }),
  });

  if (!response.ok) {
    const body = await response.text();
    throw new Error(`Create signed download URL failed: ${response.status} ${body}`);
  }

  const payload = await response.json();
  const signedPath = payload?.signedURL || payload?.signedUrl || payload?.url || '';
  if (!signedPath) {
    throw new Error('Create signed download URL failed: missing signed URL');
  }
  return signedPath.startsWith('http')
    ? signedPath
    : `${baseUrl}/storage/v1${signedPath.startsWith('/') ? signedPath : `/${signedPath}`}`;
};

const generateUploadUrl = async (storagePath, mimeType) => {
  if (!isCloudStorageConfigured()) return null;
  const baseUrl = SUPABASE_URL.replace(/\/+$/, '');
  const endpoint = `${baseUrl}/storage/v1/object/upload/sign/${encodeURIComponent(SUPABASE_STORAGE_BUCKET)}/${storagePath}`;
  const response = await fetch(endpoint, {
    method: 'POST',
    headers: {
      Authorization: `Bearer ${SUPABASE_SERVICE_ROLE_KEY}`,
      apikey: SUPABASE_SERVICE_ROLE_KEY,
      'Content-Type': 'application/json',
      'x-upsert': 'true',
    },
    body: JSON.stringify({ expiresIn: 7200 }),
  });
  if (!response.ok) {
    const body = await response.text();
    throw new Error(`Create signed upload URL failed: ${response.status} ${body}`);
  }
  const payload = await response.json();
  const uploadPath = payload?.url || payload?.signedUrl || payload?.signedURL || '';
  return uploadPath.startsWith('http')
    ? uploadPath
    : `${baseUrl}/storage/v1${uploadPath.startsWith('/') ? uploadPath : `/${uploadPath}`}`;
};

const uploadClassResourceFile = async ({ uploadDir, classroomId, storedFileName, binary, mimeType }) => {
  if (!isCloudStorageConfigured()) {
    const resourceDir = path.join(uploadDir, classroomId);
    if (!fs.existsSync(resourceDir)) {
      fs.mkdirSync(resourceDir, { recursive: true });
    }
    fs.writeFileSync(path.join(resourceDir, storedFileName), binary);
    return `/uploads/${classroomId}/${storedFileName}`;
  }

  const baseUrl = SUPABASE_URL.replace(/\/+$/, '');
  const storagePath = `${classroomId}/${storedFileName}`;
  const uploadUrl = `${baseUrl}/storage/v1/object/${encodeURIComponent(SUPABASE_STORAGE_BUCKET)}/${storagePath}`;
  const response = await fetch(uploadUrl, {
    method: 'POST',
    headers: {
      Authorization: `Bearer ${SUPABASE_SERVICE_ROLE_KEY}`,
      apikey: SUPABASE_SERVICE_ROLE_KEY,
      'Content-Type': mimeType,
      'x-upsert': 'true',
    },
    body: binary,
  });

  if (!response.ok) {
    const body = await response.text();
    throw new Error(`Cloud storage upload failed: ${response.status} ${body}`);
  }

  return `${STORAGE_PATH_PREFIX}${storagePath}`;
};

const deleteClassResourceFile = async ({ uploadDir, classroomId, storedFileName }) => {
  if (!storedFileName) {
    return;
  }

  if (!isCloudStorageConfigured()) {
    const filePath = path.join(uploadDir, classroomId, storedFileName);
    if (fs.existsSync(filePath)) {
      fs.unlinkSync(filePath);
    }
    return;
  }

  const baseUrl = SUPABASE_URL.replace(/\/+$/, '');
  const storagePath = `${classroomId}/${storedFileName}`;
  const deleteUrl = `${baseUrl}/storage/v1/object/${encodeURIComponent(SUPABASE_STORAGE_BUCKET)}/${storagePath}`;
  const response = await fetch(deleteUrl, {
    method: 'DELETE',
    headers: {
      Authorization: `Bearer ${SUPABASE_SERVICE_ROLE_KEY}`,
      apikey: SUPABASE_SERVICE_ROLE_KEY,
    },
  });

  if (!response.ok && response.status !== 404) {
    const body = await response.text();
    throw new Error(`Cloud storage delete failed: ${response.status} ${body}`);
  }
};

const sanitizeClassResource = async (resource, data) => {
  const uploader = (data?.googleUsers || []).find((user) => user.id === resource.uploadedBy);
  const storagePath = resolveResourceStoragePath(resource);
  const downloadPath = storagePath
    ? await createSignedClassResourceDownloadUrl(storagePath)
    : '';
  return {
    id: resource.id,
    classId: resource.classId,
    title: resource.title,
    type: resource.type,
    fileName: resource.fileName,
    mimeType: resource.mimeType,
    sizeBytes: resource.sizeBytes,
    uploadedBy: resource.uploadedBy,
    uploaderName: uploader ? (uploader.name || uploader.email || '') : (resource.uploaderName || ''),
    downloadPath,
    createdAt: resource.createdAt,
  };
};

const createClassRouter = ({ store, cache, uploadDir, logger = console, requireAuth }) => {
  const router = express.Router();

  router.post('/classes', requireAuth, async (req, res) => {
    if (!['teacher', 'owner'].includes(req.authUser.role)) {
      return res.status(403).json(errorResponseDto('Only teachers or owners can create classes', 'FORBIDDEN'));
    }
    const parsedRequest = parseCreateClassRequest(req.body);
    if (!parsedRequest.ok) return res.status(400).json(parsedRequest);
    try {
      const codeRes = await store.query('SELECT join_code FROM classes');
      const existingCodes = codeRes.rows.map((r) => ({ joinCode: r.join_code }));
      const classroom = {
        id: `class_${crypto.randomUUID()}`,
        teacherId: req.authUser.id,
        createdAt: new Date().toISOString(),
        joinCode: createJoinCode(existingCodes),
        ...parsedRequest.value.classroom,
      };
      await store.query(
        'INSERT INTO classes (id, teacher_id, name, description, join_code, created_at, updated_at) VALUES ($1, $2, $3, $4, $5, $6::timestamptz, $6::timestamptz)',
        [classroom.id, classroom.teacherId, classroom.name || 'Untitled class', classroom.description || '', classroom.joinCode, classroom.createdAt]
      );
      res.status(201).json(classResponseDto(sanitizeClassroom(classroom, { studentCount: 0 }), { isTeacherViewer: true }));
    } catch (error) {
      res.status(500).json(errorResponseDto('Failed to create class'));
    }
  });

  router.get('/classes', requireAuth, async (req, res) => {
    try {
      const cacheKey = `classes:${req.authUser.role}:${req.authUser.id}`;
      const cached = cache?.get(cacheKey);
      if (cached) return res.json(cached);

      let result = [];
      if (req.authUser.role === 'owner') {
        const classRes = await store.query('SELECT c.*, u.name as teacher_name, u.email as teacher_email FROM classes c JOIN app_users u ON c.teacher_id = u.id');
        const enrollRes = await store.query('SELECT class_id, COUNT(*) as student_count FROM enrollments GROUP BY class_id');
        const counts = Object.fromEntries(enrollRes.rows.map((r) => [r.class_id, parseInt(r.student_count, 10)]));
        result = rowsToCamel(classRes.rows).map((c) => sanitizeClassroom(c, { teacherName: c.teacherName || c.teacherEmail || '', studentCount: counts[c.id] || 0 }));
      } else if (req.authUser.role === 'teacher') {
        const classRes = await store.query('SELECT c.*, u.name as teacher_name, u.email as teacher_email FROM classes c JOIN app_users u ON c.teacher_id = u.id WHERE c.teacher_id = $1', [req.authUser.id]);
        const classIds = classRes.rows.map((r) => r.id);
        let counts = {};
        if (classIds.length > 0) {
          const enrollRes = await store.query('SELECT class_id, COUNT(*) as student_count FROM enrollments WHERE class_id = ANY($1) GROUP BY class_id', [classIds]);
          counts = Object.fromEntries(enrollRes.rows.map((r) => [r.class_id, parseInt(r.student_count, 10)]));
        }
        result = rowsToCamel(classRes.rows).map((c) => sanitizeClassroom(c, { teacherName: c.teacherName || c.teacherEmail || '', studentCount: counts[c.id] || 0 }));
      } else {
        const enrollRes = await store.query('SELECT c.*, u.name as teacher_name, u.email as teacher_email, e.joined_at FROM classes c JOIN app_users u ON c.teacher_id = u.id JOIN enrollments e ON c.id = e.class_id WHERE e.student_id = $1', [req.authUser.id]);
        result = rowsToCamel(enrollRes.rows).map((c) => sanitizeClassroom(c, { teacherName: c.teacherName || c.teacherEmail || '', studentCount: 0 }));
      }

      const response = classesResponseDto(result);
      cache?.set(cacheKey, response);
      res.json(response);
    } catch (error) {
      res.status(500).json(errorResponseDto('Failed to load classes'));
    }
  });

  router.post('/classes/join', requireAuth, async (req, res) => {
    const parsedRequest = parseJoinClassRequest(req.body);
    if (!parsedRequest.ok) return res.status(400).json(parsedRequest);
    try {
      const classRes = await store.query('SELECT * FROM classes WHERE join_code = $1', [parsedRequest.value.joinCode]);
      if (classRes.rowCount === 0) return res.status(404).json(errorResponseDto('Invalid join code', 'NOT_FOUND'));
      const classroom = rowsToCamel(classRes.rows)[0];
      if (classroom.teacherId === req.authUser.id) return res.status(400).json(errorResponseDto('You cannot join your own class', 'VALIDATION_ERROR'));

      const enrollRes = await store.query('SELECT 1 FROM enrollments WHERE class_id = $1 AND student_id = $2', [classroom.id, req.authUser.id]);
      if (enrollRes.rowCount > 0) return res.status(400).json(errorResponseDto('Already enrolled in this class', 'VALIDATION_ERROR'));

      await store.query(
        'INSERT INTO enrollments (id, class_id, student_id, joined_at, roll_number, student_batch) VALUES ($1, $2, $3, $4::timestamptz, $5, $6)',
        [
          `enroll_${crypto.randomUUID()}`,
          classroom.id,
          req.authUser.id,
          new Date().toISOString(),
          parsedRequest.value.rollNumber || '',
          parsedRequest.value.studentBatch || '',
        ]
      );
      res.status(201).json(successMessageResponseDto('Joined class successfully'));
    } catch (error) {
      res.status(500).json(errorResponseDto('Failed to join class'));
    }
  });

  router.put('/classes/:classId/profile', requireAuth, async (req, res) => {
    const parsedRequest = parseUpdateEnrollmentProfileRequest(req.body);
    if (!parsedRequest.ok) return res.status(400).json(parsedRequest);
    try {
      const enrollRes = await store.query(
        'UPDATE enrollments SET roll_number = $1, student_batch = $2 WHERE class_id = $3 AND student_id = $4 RETURNING *',
        [parsedRequest.value.rollNumber, parsedRequest.value.studentBatch, req.params.classId, req.authUser.id]
      );
      if (enrollRes.rowCount === 0) {
        return res.status(404).json(errorResponseDto('Student is not enrolled in this class', 'NOT_FOUND'));
      }
      res.json(successMessageResponseDto('Class profile updated successfully'));
    } catch (error) {
      res.status(500).json(errorResponseDto('Failed to update class profile'));
    }
  });

  router.get('/classes/:classId/students', requireAuth, async (req, res) => {
    try {
      const classRes = await store.query('SELECT * FROM classes WHERE id = $1', [req.params.classId]);
      if (classRes.rowCount === 0) return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      const classroom = rowsToCamel(classRes.rows)[0];
      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') {
        return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      }

      const sortBy = String(req.query.sortBy || 'name').toLowerCase();
      const batchFilter = typeof req.query.batch === 'string' ? req.query.batch.trim() : '';

      let queryText = `
        SELECT u.*, e.joined_at, e.roll_number, e.student_batch
        FROM app_users u
        JOIN enrollments e ON u.id = e.student_id
        WHERE e.class_id = $1
      `;
      const queryParams = [classroom.id];
      if (batchFilter) {
        queryParams.push(batchFilter);
        queryText += ` AND LOWER(e.student_batch) = LOWER($${queryParams.length})`;
      }

      if (sortBy === 'roll_number' || sortBy === 'rollnumber') {
        queryText += ` ORDER BY NULLIF(e.roll_number, '') ASC NULLS LAST, u.name ASC`;
      } else {
        queryText += ` ORDER BY u.name ASC`;
      }

      const studentsRes = await store.query(queryText, queryParams);
      const studentIds = studentsRes.rows.map((r) => r.id);
      let allProgress = [], allSessions = [], allNotes = [];
      if (studentIds.length > 0) {
        allProgress = rowsToCamel((await store.query('SELECT * FROM progress_entries WHERE user_id = ANY($1)', [studentIds])).rows).map(stripSnakeOnly);
        allSessions = rowsToCamel((await store.query('SELECT * FROM practice_sessions WHERE user_id = ANY($1)', [studentIds])).rows).map(stripSnakeOnly);
        allNotes = rowsToCamel((await store.query('SELECT t.*, u.name as teacher_name FROM teacher_notes t JOIN app_users u ON t.teacher_id = u.id WHERE t.class_id = $1', [classroom.id])).rows).map(stripSnakeOnly);
      }
      const students = rowsToCamel(studentsRes.rows).map((row) => {
        const student = stripSnakeOnly({ ...row, provider: 'google' });
        const progressEntries = allProgress.filter((p) => p.userId === student.id).sort((a, b) => String(b.updatedAt || '').localeCompare(String(a.updatedAt || '')));
        const practiceSessions = allSessions.filter((p) => p.userId === student.id);
        const teacherNotes = allNotes.filter((n) => n.studentId === student.id).sort((a, b) => String(b.createdAt || '').localeCompare(String(a.createdAt || '')));
        const dashboard = buildDashboard({ user: student, progressEntries, practiceSessions });
        return {
          user: sanitizeGoogleUser(student),
          rollNumber: row.rollNumber || row.roll_number || '',
          studentBatch: row.studentBatch || row.student_batch || '',
          joinedAt: row.joinedAt || row.joined_at || '',
          summary: dashboard.summary,
          weakTopics: dashboard.weakTopics,
          recentActivity: dashboard.recentActivity.slice(0, 3),
          lastActiveAt: dashboard.recentActivity.length > 0 ? dashboard.recentActivity[0].createdAt : (student.lastSeenAt || ''),
          progressEntries,
          teacherNotes,
        };
      });
      res.json(classStudentsResponseDto({ classId: classroom.id, students }));
    } catch (error) {
      res.status(500).json(errorResponseDto('Failed to load students'));
    }
  });

  router.get('/classes/:classId/export', requireAuth, async (req, res) => {
    try {
      const classRes = await store.query('SELECT * FROM classes WHERE id = $1', [req.params.classId]);
      if (classRes.rowCount === 0) return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      const classroom = rowsToCamel(classRes.rows)[0];
      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') {
        return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      }

      const sortBy = String(req.query.sortBy || 'roll_number').toLowerCase();
      const batchFilter = typeof req.query.batch === 'string' ? req.query.batch.trim() : '';

      let queryText = `
        SELECT u.*, e.joined_at, e.roll_number, e.student_batch
        FROM app_users u
        JOIN enrollments e ON u.id = e.student_id
        WHERE e.class_id = $1
      `;
      const queryParams = [classroom.id];
      if (batchFilter) {
        queryParams.push(batchFilter);
        queryText += ` AND LOWER(e.student_batch) = LOWER($${queryParams.length})`;
      }

      if (sortBy === 'name') {
        queryText += ` ORDER BY u.name ASC`;
      } else {
        queryText += ` ORDER BY NULLIF(e.roll_number, '') ASC NULLS LAST, u.name ASC`;
      }

      const studentsRes = await store.query(queryText, queryParams);
      const studentIds = studentsRes.rows.map((r) => r.id);
      let allProgress = [], allSessions = [];
      if (studentIds.length > 0) {
        allProgress = rowsToCamel((await store.query('SELECT * FROM progress_entries WHERE user_id = ANY($1)', [studentIds])).rows).map(stripSnakeOnly);
        allSessions = rowsToCamel((await store.query('SELECT * FROM practice_sessions WHERE user_id = ANY($1)', [studentIds])).rows).map(stripSnakeOnly);
      }

      const escapeCsv = (val) => {
        if (val === null || val === undefined) return '""';
        const str = String(val).replace(/"/g, '""');
        return `"${str}"`;
      };

      const headers = [
        'Roll No / PRN',
        'Student Name',
        'Batch / Section',
        'Email',
        'Enrolled At',
        'Last Active',
        'Modules Started',
        'Modules Completed',
        'Practice Sessions',
        'Current Streak (Days)',
        'Average Score',
        'Attendance / Status'
      ];

      const rows = rowsToCamel(studentsRes.rows).map((row) => {
        const student = stripSnakeOnly({ ...row, provider: 'google' });
        const progressEntries = allProgress.filter((p) => p.userId === student.id);
        const practiceSessions = allSessions.filter((p) => p.userId === student.id);
        const dashboard = buildDashboard({ user: student, progressEntries, practiceSessions });

        const avgScore = practiceSessions.length > 0
          ? Math.round(practiceSessions.reduce((acc, cur) => acc + (cur.score || 0), 0) / practiceSessions.length)
          : (dashboard.summary?.averageScore || 0);

        const lastActive = dashboard.recentActivity.length > 0
          ? dashboard.recentActivity[0].createdAt
          : (student.lastSeenAt || '');

        const status = dashboard.summary?.totalPracticeSessions > 0 || dashboard.summary?.totalTechniquesStarted > 0
          ? 'Active'
          : 'Enrolled (No Activity)';

        return [
          escapeCsv(row.rollNumber || row.roll_number || '-'),
          escapeCsv(student.name || student.email || 'Unnamed Student'),
          escapeCsv(row.studentBatch || row.student_batch || '-'),
          escapeCsv(student.email || '-'),
          escapeCsv(row.joinedAt ? new Date(row.joinedAt).toLocaleDateString() : '-'),
          escapeCsv(lastActive ? new Date(lastActive).toLocaleDateString() : '-'),
          escapeCsv(dashboard.summary?.totalTechniquesStarted || 0),
          escapeCsv(dashboard.summary?.completedTechniques || 0),
          escapeCsv(dashboard.summary?.totalPracticeSessions || 0),
          escapeCsv(dashboard.summary?.streakDays || 0),
          escapeCsv(`${avgScore}%`),
          escapeCsv(status)
        ].join(',');
      });

      const csvContent = '\uFEFF' + [headers.map(escapeCsv).join(','), ...rows].join('\r\n');
      const safeClassName = (classroom.name || 'Class').replace(/[^a-zA-Z0-9_-]/g, '_');
      const filename = `${safeClassName}_${classroom.joinCode || 'roster'}_marksheet.csv`;

      res.setHeader('Content-Type', 'text/csv; charset=utf-8');
      res.setHeader('Content-Disposition', `attachment; filename="${filename}"`);
      res.send(csvContent);
    } catch (error) {
      res.status(500).json(errorResponseDto('Failed to export class roster'));
    }
  });

  router.get('/classes/:classId/students/:studentId', requireAuth, async (req, res) => {
    try {
      const classRes = await store.query('SELECT * FROM classes WHERE id = $1', [req.params.classId]);
      if (classRes.rowCount === 0) return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      const classroom = rowsToCamel(classRes.rows)[0];

      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') {
        return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      }

      const enrollRes = await store.query('SELECT * FROM enrollments WHERE class_id = $1 AND student_id = $2', [classroom.id, req.params.studentId]);
      if (enrollRes.rowCount === 0) return res.status(404).json(errorResponseDto('Student not enrolled', 'NOT_FOUND'));
      const enrollment = rowsToCamel(enrollRes.rows)[0];

      const stuRes = await store.query('SELECT * FROM app_users WHERE id = $1', [req.params.studentId]);
      if (stuRes.rowCount === 0) return res.status(404).json(errorResponseDto('Student not found', 'NOT_FOUND'));
      const student = { ...rowsToCamel(stuRes.rows)[0], provider: 'google' };

      const progRes = await store.query('SELECT * FROM progress_entries WHERE user_id = $1', [student.id]);
      const sessRes = await store.query('SELECT * FROM practice_sessions WHERE user_id = $1', [student.id]);
      const notesRes = await store.query('SELECT t.*, u.name as teacher_name FROM teacher_notes t JOIN app_users u ON t.teacher_id = u.id WHERE t.class_id = $1 AND t.student_id = $2', [classroom.id, student.id]);

      const progressEntries = rowsToCamel(progRes.rows).map(stripSnakeOnly).sort((a, b) => String(b.updatedAt || '').localeCompare(String(a.updatedAt || '')));
      const practiceSessions = rowsToCamel(sessRes.rows).map(stripSnakeOnly);
      const teacherNotes = rowsToCamel(notesRes.rows).map(stripSnakeOnly).sort((a, b) => String(b.createdAt || '').localeCompare(String(a.createdAt || '')));

      const dashboard = buildDashboard({ user: student, progressEntries, practiceSessions });

      res.json(studentDetailResponseDto({
        user: sanitizeGoogleUser(student),
        rollNumber: enrollment.rollNumber || enrollment.roll_number || '',
        studentBatch: enrollment.studentBatch || enrollment.student_batch || '',
        joinedAt: enrollment.joinedAt || enrollment.joined_at || '',
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
  });

  router.post('/classes/:classId/students/:studentId/feedback', requireAuth, async (req, res) => {
    if (!['teacher', 'owner'].includes(req.authUser.role)) {
      return res.status(403).json(errorResponseDto('Only teachers or owners can send student feedback', 'FORBIDDEN'));
    }
    const parsedRequest = parseTeacherStudentFeedbackRequest(req.body);
    if (!parsedRequest.ok) return res.status(400).json(parsedRequest);
    try {
      const classRes = await store.query('SELECT * FROM classes WHERE id = $1', [req.params.classId]);
      if (classRes.rowCount === 0) return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      const classroom = rowsToCamel(classRes.rows)[0];
      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') {
        return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      }

      const enrollRes = await store.query('SELECT 1 FROM enrollments WHERE class_id = $1 AND student_id = $2', [classroom.id, req.params.studentId]);
      if (enrollRes.rowCount === 0) return res.status(404).json(errorResponseDto('Student is not enrolled in this class', 'NOT_FOUND'));

      const note = {
        id: `note_${crypto.randomUUID()}`,
        classId: classroom.id,
        studentId: req.params.studentId,
        teacherId: req.authUser.id,
        createdAt: new Date().toISOString(),
        ...parsedRequest.value.note,
      };
      await store.query(
        'INSERT INTO teacher_notes (id, class_id, student_id, teacher_id, message, status, created_at) VALUES ($1, $2, $3, $4, $5, $6, $7::timestamptz)',
        [note.id, note.classId, note.studentId, note.teacherId, note.message, note.status || '', note.createdAt]
      );
      res.status(201).json(teacherNoteCreatedResponseDto(sanitizeTeacherNote(note, { googleUsers: [{ id: req.authUser.id, name: req.authUser.name || req.authUser.email }] })));
    } catch (error) {
      res.status(500).json(errorResponseDto('Failed to create note'));
    }
  });

  router.get('/classes/:classId/resources', requireAuth, async (req, res) => {
    try {
      const classRes = await store.query('SELECT * FROM classes WHERE id = $1', [req.params.classId]);
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
      const dummyData = { googleUsers: resourcesRes.rows.map((r) => ({ id: r.uploaded_by, name: r.uploader_name, email: r.uploader_email })) };
      const resources = await Promise.all(resourceItems.map((item) => sanitizeClassResource(item, dummyData)));
      res.json(classResourcesResponseDto(resources));
    } catch (error) {
      if (logger && typeof logger.error === 'function') {
        logger.error('class.resources_failed', { error: error.message, userId: req.authUser.id, classId: req.params.classId });
      }
      res.status(500).json(errorResponseDto('Failed to load class resources'));
    }
  });

  router.post('/classes/:classId/resources/upload-url', requireAuth, async (req, res) => {
    if (!['teacher', 'owner'].includes(req.authUser.role)) {
      return res.status(403).json(errorResponseDto('Only teachers or owners can upload resources', 'FORBIDDEN'));
    }
    const parsedRequest = parseClassResourceUploadInitRequest(req.body);
    if (!parsedRequest.ok) return res.status(400).json(parsedRequest);
    try {
      if (!isCloudStorageConfigured()) {
        return res.status(503).json(errorResponseDto('Cloud storage is not configured for direct uploads', 'UNAVAILABLE'));
      }
      const classRes = await store.query('SELECT * FROM classes WHERE id = $1', [req.params.classId]);
      if (classRes.rowCount === 0) return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      const classroom = rowsToCamel(classRes.rows)[0];
      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') {
        return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      }

      const storedFileName = `${Date.now()}_${crypto.randomUUID().slice(0, 8)}_${sanitizeResourceFileName(parsedRequest.value.fileName)}`;
      const storagePath = `classes/${classroom.id}/${storedFileName}`;
      const uploadUrl = await generateUploadUrl(storagePath, parsedRequest.value.mimeType);
      res.json(classResourceUploadInitResponseDto(uploadUrl, storagePath));
    } catch (error) {
      res.status(500).json(errorResponseDto('Failed to initialize upload'));
    }
  });

  router.post('/classes/:classId/resources/complete', requireAuth, async (req, res) => {
    if (!['teacher', 'owner'].includes(req.authUser.role)) {
      return res.status(403).json(errorResponseDto('Only teachers or owners can upload resources', 'FORBIDDEN'));
    }
    const parsedRequest = parseClassResourceUploadCompleteRequest(req.body);
    if (!parsedRequest.ok) return res.status(400).json(parsedRequest);
    try {
      const classRes = await store.query('SELECT * FROM classes WHERE id = $1', [req.params.classId]);
      if (classRes.rowCount === 0) return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
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
        'INSERT INTO class_resources (id, class_id, title, type, file_name, mime_type, size_bytes, uploaded_by, download_path, created_at) VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10::timestamptz)',
        [resource.id, resource.classId, resource.title, resource.type, resource.fileName, resource.mimeType, resource.sizeBytes, resource.uploadedBy, resource.downloadPath, resource.createdAt]
      );
      res.status(201).json(classResourceCreatedResponseDto(await sanitizeClassResource(resource, { googleUsers: [{ id: req.authUser.id, name: req.authUser.name || req.authUser.email }] })));
    } catch (error) {
      res.status(500).json(errorResponseDto('Failed to complete upload'));
    }
  });

  router.post('/classes/:classId/resources', requireAuth, async (req, res) => {
    if (!['teacher', 'owner'].includes(req.authUser.role)) {
      return res.status(403).json(errorResponseDto('Only teachers or owners can upload resources', 'FORBIDDEN'));
    }
    const parsedRequest = parseClassResourceUploadRequest(req.body);
    if (!parsedRequest.ok) return res.status(400).json(parsedRequest);
    try {
      const classRes = await store.query('SELECT * FROM classes WHERE id = $1', [req.params.classId]);
      if (classRes.rowCount === 0) return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
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
        'INSERT INTO class_resources (id, class_id, title, type, file_name, mime_type, size_bytes, uploaded_by, download_path, created_at) VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10::timestamptz)',
        [resource.id, resource.classId, resource.title, resource.type, resource.fileName, resource.mimeType, resource.sizeBytes, resource.uploadedBy, resource.downloadPath, resource.createdAt]
      );
      res.status(201).json(classResourceCreatedResponseDto(await sanitizeClassResource(resource, { googleUsers: [{ id: req.authUser.id, name: req.authUser.name || req.authUser.email }] })));
    } catch (error) {
      res.status(500).json(errorResponseDto('Failed to upload resource'));
    }
  });

  router.delete('/classes/:classId/resources/:resourceId', requireAuth, async (req, res) => {
    try {
      const classRes = await store.query('SELECT * FROM classes WHERE id = $1', [req.params.classId]);
      if (classRes.rowCount === 0) return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      const classroom = rowsToCamel(classRes.rows)[0];
      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') {
        return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      }
      const resRes = await store.query('SELECT * FROM class_resources WHERE id = $1 AND class_id = $2', [req.params.resourceId, classroom.id]);
      if (resRes.rowCount === 0) return res.status(404).json(errorResponseDto('Resource not found', 'NOT_FOUND'));
      const resource = rowsToCamel(resRes.rows)[0];

      await deleteClassResourceFile({ uploadDir, classroomId: classroom.id, storedFileName: resource.fileName });
      await store.query('DELETE FROM class_resources WHERE id = $1', [resource.id]);
      res.json(successMessageResponseDto('Resource deleted'));
    } catch (error) {
      res.status(500).json(errorResponseDto('Failed to delete resource'));
    }
  });

  return router;
};

module.exports = {
  createClassRouter,
};
