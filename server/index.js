const express = require('express');
const cors = require('cors');
const crypto = require('crypto');
const fs = require('fs');
const path = require('path');
const { OAuth2Client } = require('google-auth-library');
const { createPostgresStore, rowsToCamel, stripSnakeOnly } = require('./postgresStore');
const { createCache } = require('./cache');
const {
  authResponseDto,
  analyticsResponseDto,
  auditLogsResponseDto,
  backupResponseDto,
  errorResponseDto,
  logoutResponseDto,
  meResponseDto,
  googleAuthResponseDto,
  parseGoogleAuthRequest,
  parsePracticeSessionRequest,
  parseReviewFeedbackRequest,
  parseRestoreBackupRequest,
  parseShareVisualizationRequest,
  parseSavePreferencesRequest,
  parseSaveSessionRequest,
  parseTechniqueProgressRequest,
  practiceSessionCreatedResponseDto,
  practiceSessionsResponseDto,
  preferencesResponseDto,
  dashboardResponseDto,
  progressEntriesResponseDto,
  progressResponseDto,
  feedbackCreatedResponseDto,
  classResponseDto,
  classResourceCreatedResponseDto,
  classResourcesResponseDto,
  classesResponseDto,
  classStudentsResponseDto,
  feedbackResponseDto,
  shareCreatedResponseDto,
  sharedVisualizationResponseDto,
  sessionsResponseDto,
  statusResponseDto,
  studentDetailResponseDto,
  successMessageResponseDto,
  teacherFeedbackResponseDto,
  teacherNoteCreatedResponseDto,
  userResponseDto,
  usersResponseDto,
  parseCreateClassRequest,
  parseClassResourceUploadCompleteRequest,
  parseClassResourceUploadInitRequest,
  parseClassResourceUploadRequest,
  parseFeedbackRequest,
  parseJoinClassRequest,
  parseSaveMistakeRequest,
  parseTeacherStudentFeedbackRequest,
  mistakeCreatedResponseDto,
  mistakesResponseDto,
} = require('./dto');

const DEFAULT_DATA = {
  sessions: [],
  practiceSessions: [],
  preferences: {},
  progressEntries: [],
  feedbackEntries: [],
  shares: [],
  users: [],
  mistakes: [],
  googleUsers: [], // Real Google-authenticated users
  classes: [],
  enrollments: [],
  teacherNotes: [],
  classResources: [],
  auditLogs: [],
};

const DEFAULT_DATA_FILE = process.env.DATA_FILE || path.join(__dirname, 'data.json');
const DEFAULT_UPLOAD_DIR = process.env.UPLOAD_DIR || path.join(__dirname, 'uploads');
const SUPABASE_URL = process.env.SUPABASE_URL || '';
const SUPABASE_SERVICE_ROLE_KEY = process.env.SUPABASE_SERVICE_ROLE_KEY || '';
const SUPABASE_STORAGE_BUCKET = process.env.SUPABASE_STORAGE_BUCKET || '';
const SUPABASE_SIGNED_DOWNLOAD_EXPIRES_IN_SECONDS = Math.max(
  60,
  Number(process.env.SUPABASE_SIGNED_DOWNLOAD_EXPIRES_IN_SECONDS || 600)
);

const RATE_LIMIT_WINDOW_MS = 60 * 1000;
const RATE_LIMIT_MAX_REQUESTS = 120;

// Google OAuth client
const googleClientId = process.env.GOOGLE_CLIENT_ID;
const oauth2Client = new OAuth2Client(googleClientId);

const createLogger = () => ({
  info(message, meta = {}) {
    console.log(JSON.stringify({ level: 'info', message, ...meta, timestamp: new Date().toISOString() }));
  },
  error(message, meta = {}) {
    console.error(JSON.stringify({ level: 'error', message, ...meta, timestamp: new Date().toISOString() }));
  },
});


const sanitizeUser = (user) => ({
  id: user.id,
  type: user.type || user.provider || 'user',
  displayName: user.displayName || user.name || user.email || '',
  createdAt: user.createdAt,
  lastSeenAt: user.lastSeenAt,
  role: user.role || '',
  email: user.email || '',
});

const sanitizeGoogleUser = (user) => ({
  id: user.id,
  googleId: user.googleId,
  email: user.email,
  name: user.name,
  displayName: user.name,
  role: user.role,
  createdAt: user.createdAt,
  lastSeenAt: user.lastSeenAt,
});

const sanitizeClassroom = (classroom, extras = {}) => ({
  id: classroom.id,
  name: classroom.name,
  description: classroom.description || '',
  joinCode: classroom.joinCode,
  teacherId: classroom.teacherId,
  teacherName: extras.teacherName || '',
  createdAt: classroom.createdAt,
  updatedAt: classroom.updatedAt || classroom.createdAt,
  studentCount: extras.studentCount || 0,
  joinedAt: extras.joinedAt || '',
});

const DAILY_GOAL_TARGET = 3;
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

const toIsoDay = (value) => {
  if (!value) {
    return '';
  }
  const date = new Date(value);
  if (Number.isNaN(date.getTime())) {
    return '';
  }
  return date.toISOString().slice(0, 10);
};

const calculateStreakDays = (sessions) => {
  const dayKeys = new Set();
  for (const session of sessions || []) {
    const day = toIsoDay(session.createdAt);
    if (day) {
      dayKeys.add(day);
    }
  }

  let streak = 0;
  const cursor = new Date();
  cursor.setHours(0, 0, 0, 0);

  while (true) {
    const key = cursor.toISOString().slice(0, 10);
    if (!dayKeys.has(key)) {
      break;
    }
    streak += 1;
    cursor.setDate(cursor.getDate() - 1);
  }

  return streak;
};

const buildDashboard = ({ user, progressEntries, practiceSessions }) => {
  const sortedSessions = [...(practiceSessions || [])].sort((left, right) =>
    String(right.createdAt || '').localeCompare(String(left.createdAt || ''))
  );
  const recentActivity = sortedSessions.slice(0, 5).map((session) => ({
    techniqueId: session.techniqueId,
    difficulty: session.difficulty || '',
    score: session.score || 0,
    createdAt: session.createdAt || '',
    durationMs: session.durationMs || 0,
  }));

  const todayKey = new Date().toISOString().slice(0, 10);
  const todaySessions = sortedSessions.filter((session) => toIsoDay(session.createdAt) === todayKey).length;
  const completedTechniques = (progressEntries || []).filter((entry) => entry.completed).length;

  const weakTopics = [...(progressEntries || [])]
    .sort((left, right) => {
      const leftScore = Number(left.bestScore || 0);
      const rightScore = Number(right.bestScore || 0);
      if (leftScore !== rightScore) {
        return leftScore - rightScore;
      }
      return Number(right.attempts || 0) - Number(left.attempts || 0);
    })
    .slice(0, 3)
    .map((entry) => ({
      techniqueId: entry.techniqueId,
      bestScore: entry.bestScore || 0,
      attempts: entry.attempts || 0,
      masteryLevel: entry.masteryLevel || '',
      lastDifficulty: entry.lastDifficulty || '',
    }));

  return {
    user: sanitizeUser(user),
    summary: {
      totalTechniquesStarted: (progressEntries || []).length,
      completedTechniques,
      totalPracticeSessions: sortedSessions.length,
      streakDays: calculateStreakDays(sortedSessions),
      todaySessions,
      dailyGoalTarget: DAILY_GOAL_TARGET,
      dailyGoalCompleted: todaySessions >= DAILY_GOAL_TARGET,
      weakTopicCount: weakTopics.length,
    },
    recentActivity,
    weakTopics,
    progressEntries: [...(progressEntries || [])].sort((left, right) =>
      String(right.updatedAt || right.lastPlayedAt || '').localeCompare(String(left.updatedAt || left.lastPlayedAt || ''))
    ),
  };
};

const buildStudentSummary = ({ student, data }) => {
  const progressEntries = (data.progressEntries || []).filter((entry) => entry.userId === student.id);
  const practiceSessions = (data.practiceSessions || []).filter((entry) => entry.userId === student.id);
  const dashboard = buildDashboard({ user: student, progressEntries, practiceSessions });
  const latestActivity = dashboard.recentActivity.length > 0 ? dashboard.recentActivity[0].createdAt : '';
  return {
    user: sanitizeGoogleUser(student),
    summary: dashboard.summary,
    weakTopics: dashboard.weakTopics,
    recentActivity: dashboard.recentActivity.slice(0, 3),
    lastActiveAt: latestActivity || student.lastSeenAt || '',
  };
};

const sanitizeTeacherNote = (note, data) => {
  const teacher = (data.googleUsers || []).find((user) => user.id === note.teacherId);
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

const sanitizeResourceFileName = (value) => value.replace(/[^a-zA-Z0-9._-]/g, '_').slice(0, 120) || 'resource.bin';

const isCloudStorageConfigured = () =>
  Boolean(SUPABASE_URL.trim() && SUPABASE_SERVICE_ROLE_KEY.trim() && SUPABASE_STORAGE_BUCKET.trim());

const STORAGE_PATH_PREFIX = 'storage://';

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

const buildStorageDownloadPath = (storagePath) => {
  if (!isCloudStorageConfigured()) {
    return `/uploads/${storagePath}`;
  }
  return `${STORAGE_PATH_PREFIX}${storagePath}`;
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

const createSignedClassResourceUpload = async ({ classroomId, storedFileName, mimeType }) => {
  if (!isCloudStorageConfigured()) {
    return null;
  }

  const baseUrl = SUPABASE_URL.replace(/\/+$/, '');
  const storagePath = `${classroomId}/${storedFileName}`;
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
  const explicitToken = typeof payload?.token === 'string' ? payload.token.trim() : '';
  if (!uploadPath) {
    throw new Error('Create signed upload URL failed: missing upload URL');
  }

  const signedUploadUrl = uploadPath.startsWith('http')
      ? uploadPath
      : `${baseUrl}/storage/v1${uploadPath.startsWith('/') ? uploadPath : `/${uploadPath}`}`;
  const tokenMatch = signedUploadUrl.match(/[?&]token=([^&]+)/);
  const uploadToken = explicitToken || (tokenMatch ? decodeURIComponent(tokenMatch[1]) : '');

  return {
    storagePath,
    signedUploadUrl,
    uploadToken,
  };
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
  const uploader = (data.googleUsers || []).find((user) => user.id === resource.uploadedBy);
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
    sizeBytes: resource.sizeBytes || 0,
    createdAt: resource.createdAt || '',
    downloadPath,
    uploaderName: uploader ? (uploader.name || uploader.email || '') : '',
  };
};

const sanitizeAuditLog = (log) => ({
  id: log.id,
  action: log.action || '',
  actorId: log.actorId || '',
  actorName: log.actorName || '',
  targetId: log.targetId || '',
  targetName: log.targetName || '',
  details: log.details || {},
  createdAt: log.createdAt || '',
});

const buildWeakTopicSummary = ({ classroomId, data }) => {
  const enrollments = (data.enrollments || []).filter((entry) => entry.classId === classroomId);
  const counts = new Map();

  for (const enrollment of enrollments) {
    const progressEntries = (data.progressEntries || []).filter((entry) => entry.userId === enrollment.studentId);
    for (const entry of progressEntries) {
      const bestScore = Number(entry.bestScore || 0);
      const attempts = Number(entry.attempts || 0);
      if (attempts <= 0) {
        continue;
      }
      if (bestScore > 4) {
        continue;
      }
      const existing = counts.get(entry.techniqueId) || {
        techniqueId: entry.techniqueId,
        studentCount: 0,
        lowestBestScore: bestScore,
      };
      existing.studentCount += 1;
      existing.lowestBestScore = Math.min(existing.lowestBestScore, bestScore);
      counts.set(entry.techniqueId, existing);
    }
  }

  return [...counts.values()]
    .sort((left, right) => {
      if (right.studentCount !== left.studentCount) {
        return right.studentCount - left.studentCount;
      }
      return left.lowestBestScore - right.lowestBestScore;
    })
    .slice(0, 4);
};

const buildStudentDetail = ({ classroom, student, data }) => {
  const summary = buildStudentSummary({ student, data });
  const progressEntries = (data.progressEntries || [])
    .filter((entry) => entry.userId === student.id)
    .sort((left, right) =>
      String(right.updatedAt || right.lastPlayedAt || '').localeCompare(String(left.updatedAt || left.lastPlayedAt || ''))
    );
  const teacherNotes = (data.teacherNotes || [])
    .filter((note) => note.classId === classroom.id && note.studentId === student.id)
    .sort((left, right) => String(right.createdAt || '').localeCompare(String(left.createdAt || '')))
    .map((note) => sanitizeTeacherNote(note, data));

  return {
    ...summary,
    progressEntries,
    teacherNotes,
  };
};

const sanitizeFeedbackEntry = (feedback, data) => {
  const googleUser = (data.googleUsers || []).find((user) => user.id === feedback.userId);
  const guestUser = (data.users || []).find((user) => user.id === feedback.userId);
  const sender = googleUser || guestUser || null;
  return {
    id: feedback.id,
    userId: feedback.userId,
    type: feedback.type || '',
    message: feedback.message || '',
    techniqueId: feedback.techniqueId || '',
    screen: feedback.screen || '',
    difficulty: feedback.difficulty || '',
    createdAt: feedback.createdAt || '',
    userDisplayName: sender ? (sender.displayName || sender.name || sender.email || '') : '',
    userEmail: sender ? (sender.email || '') : '',
    userRole: sender ? (sender.role || sender.type || '') : '',
    provider: sender ? (sender.provider || sender.type || '') : '',
  };
};

const sanitizeTeacherFeedbackEntry = (feedback, data, classroom) => ({
  ...sanitizeFeedbackEntry(feedback, data),
  classId: classroom?.id || '',
  className: classroom?.name || '',
  reviewedAt: feedback.reviewedAt || '',
  reviewedBy: feedback.reviewedBy || '',
});

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

const buildOwnerAnalytics = (data) => {
  const now = Date.now();
  const THIRTY_DAYS_MS = 30 * 24 * 60 * 60 * 1000;
  const googleUsers = data.googleUsers || [];
  const classes = data.classes || [];
  const enrollments = data.enrollments || [];
  const practiceSessions = data.practiceSessions || [];
  const feedbackEntries = data.feedbackEntries || [];
  const classResources = data.classResources || [];

  const activeTeachers = googleUsers.filter((user) => {
    if (user.role !== 'teacher') {
      return false;
    }
    const lastSeen = new Date(user.lastSeenAt || 0).getTime();
    return Number.isFinite(lastSeen) && now - lastSeen <= THIRTY_DAYS_MS;
  }).length;

  const activeClasses = classes.filter((classroom) =>
    enrollments.some((entry) => entry.classId === classroom.id)
  ).length;

  const usageMap = new Map();
  for (const session of practiceSessions) {
    const key = session.techniqueId || '';
    if (!key) {
      continue;
    }
    usageMap.set(key, (usageMap.get(key) || 0) + 1);
  }
  const mostUsedTopics = [...usageMap.entries()]
    .map(([techniqueId, uses]) => ({ techniqueId, uses }))
    .sort((left, right) => right.uses - left.uses)
    .slice(0, 5);
  const issueMap = new Map();
  for (const feedback of feedbackEntries) {
    const key = `${feedback.techniqueId || 'general'}|${feedback.type || 'general'}`;
    issueMap.set(key, (issueMap.get(key) || 0) + 1);
  }
  const mostReportedContentIssues = [...issueMap.entries()]
    .map(([key, count]) => {
      const [techniqueId, type] = key.split('|');
      return {
        techniqueId,
        type,
        count,
      };
    })
    .sort((left, right) => right.count - left.count)
    .slice(0, 5);

  return {
    summary: {
      totalUsers: googleUsers.length,
      activeTeachers,
      totalClasses: classes.length,
    },
    mostUsedTopics,
    mostReportedContentIssues,
  };
};

const createApp = ({ dataFile = DEFAULT_DATA_FILE, uploadDir = DEFAULT_UPLOAD_DIR, logger = createLogger() } = {}) => {
  const app = express();
  if (!process.env.DATABASE_URL) {
    throw new Error('DATABASE_URL environment variable is required');
  }
  const store = createPostgresStore(process.env.DATABASE_URL);
  const cache = createCache(300000);
  const requestCounts = new Map();

  if (!fs.existsSync(uploadDir)) {
    fs.mkdirSync(uploadDir, { recursive: true });
  }

  app.set('trust proxy', true);
  app.use(cors());
  app.use(express.json());
  app.use('/uploads', express.static(uploadDir));
  app.use('/admin', express.static(path.join(__dirname, 'public', 'admin')));
  app.get('/openapi.json', (req, res) => {
    res.sendFile(path.join(__dirname, 'public', 'openapi.json'));
  });
  app.use((req, res, next) => {
    const now = Date.now();
    const ip = req.ip || req.socket.remoteAddress || 'unknown';
    const current = requestCounts.get(ip);

    if (!current || now - current.startedAt >= RATE_LIMIT_WINDOW_MS) {
      requestCounts.set(ip, { startedAt: now, count: 1 });
      return next();
    }

    if (current.count >= RATE_LIMIT_MAX_REQUESTS) {
      logger.info('request.rate_limited', {
        ip,
        method: req.method,
        path: req.originalUrl,
      });
      return res.status(429).json(errorResponseDto('Too many requests, please try again shortly', 'RATE_LIMITED'));
    }

    current.count += 1;
    next();
  });
  app.use((req, res, next) => {
    const start = Date.now();
    res.on('finish', () => {
      logger.info('request.completed', {
        method: req.method,
        path: req.originalUrl,
        statusCode: res.statusCode,
        durationMs: Date.now() - start,
      });
    });
    next();
  });

  app.get('/status', (req, res) => res.json({ data: { status: 'ok' } }));
  app.get('/api/auth/google-config', (req, res) => res.json({ data: { configured: true, clientId: process.env.GOOGLE_CLIENT_ID || '' } }));

  const persistGoogleUser = async ({ googleId, email, name }) => {
    const token = crypto.randomBytes(32).toString('hex');
    const refreshToken = crypto.randomBytes(32).toString('hex');
    const user = await store.upsertGoogleUser({
      googleId, email, name, token, refreshToken, ownerEmail: 'prasannapbhurke@gmail.com',
    });
    logger.info('auth.google.login_fast', { userId: user.id, email, role: user.role });
    return user;
  };

  const requireAuth = async (req, res, next) => {
    const authHeader = req.headers.authorization || '';
    const token = authHeader.startsWith('Bearer ') ? authHeader.slice('Bearer '.length).trim() : '';
    if (!token) return res.status(401).json(errorResponseDto('Authentication required', 'UNAUTHORIZED'));

    try {
      const user = await store.findUserByToken(token);
      if (!user) return res.status(401).json(errorResponseDto('Invalid session token', 'UNAUTHORIZED'));
      req.authUser = user;
      return next();
    } catch (error) {
      logger.error('auth.lookup_failed', { error: error.message });
      res.status(500).json(errorResponseDto('Failed to validate session'));
    }
  };

  app.post('/api/auth/guest', (req, res) => {
    res.status(410).json(errorResponseDto('Guest mode has been removed. Sign in with Google.', 'GUEST_REMOVED'));
  });

  app.post('/api/auth/google', async (req, res) => {
    try {
      const credential = String(req.body?.credential || req.body?.idToken || '').trim();
      if (!credential) {
        return res.status(400).json(errorResponseDto('Google credential is required', 'VALIDATION_ERROR'));
      }
      if (!process.env.GOOGLE_CLIENT_ID) {
        logger.error('google.auth.missing_client_id');
        return res.status(500).json(errorResponseDto('Google sign-in is not configured', 'CONFIGURATION_ERROR'));
      }

      const ticket = await oauth2Client.verifyIdToken({ idToken: credential, audience: process.env.GOOGLE_CLIENT_ID });
      const { sub: googleId, email, name } = ticket.getPayload();
      
      const user = await persistGoogleUser({ googleId, email, name });
      
      const token = user.token || req.authUser?.token || '';
      const refreshToken = user.refreshToken || req.authUser?.refreshToken || '';

      res.cookie('refreshToken', refreshToken, { httpOnly: true, secure: process.env.NODE_ENV === 'production', sameSite: 'lax', maxAge: 7 * 24 * 60 * 60 * 1000, path: '/api/auth' });
      res.json(authResponseDto({
        token,
        refreshToken,
        user: sanitizeGoogleUser({ ...user, provider: 'google' }),
      }));
    } catch (error) { 
      logger.error('google.auth.error', { error: error.message });
      const message = process.env.NODE_ENV === 'production'
        ? 'Invalid Google token'
        : `Invalid Google token: ${error.message}`;
      res.status(401).json(errorResponseDto(message, 'UNAUTHORIZED')); 
    }
  });

  app.get('/api/me', requireAuth, async (req, res) => {
    const sanitized = req.authUser.googleId ? sanitizeGoogleUser(req.authUser) : sanitizeUser(req.authUser);
    res.json(meResponseDto(sanitized));
  });

  app.post('/api/auth/logout', requireAuth, async (req, res) => {
    try {
      if (typeof store.logoutUser === 'function' && req.authUser.googleId) {
        await store.logoutUser(req.authUser.id);
        logger.info('auth.logout', { userId: req.authUser.id });
        return res.json(logoutResponseDto());
      }

      await store.logoutUser(req.authUser.id);
      logger.info('auth.logout', { userId: req.authUser.id });
      res.json(logoutResponseDto());
    } catch (error) {
      logger.error('auth.logout_failed', { error: error.message, userId: req.authUser.id });
      res.status(500).json(errorResponseDto('Failed to logout'));
    }
  });

  app.get('/api/techniques/:id/progress', requireAuth, async (req, res) => {
    try {
      const progRes = await store.query('SELECT * FROM progress_entries WHERE user_id = $1 AND technique_id = $2', [req.authUser.id, req.params.id]);
      if (progRes.rowCount === 0) return res.json({ data: null });
      res.json(progressEntryResponseDto(stripSnakeOnly(rowsToCamel(progRes.rows)[0])));
    } catch (error) { res.status(500).json(errorResponseDto('Failed to load progress')); }
  });

  app.get('/api/progress', requireAuth, async (req, res) => {
    try {
      const result = await store.query('SELECT * FROM progress_entries WHERE user_id = $1 ORDER BY updated_at DESC', [req.authUser.id]);
      const progressEntries = rowsToCamel(result.rows).map(stripSnakeOnly);
      res.json(progressEntriesResponseDto(progressEntries));
    } catch (error) {
      logger.error('progress.list_failed', { error: error.message, userId: req.authUser.id });
      res.status(500).json(errorResponseDto('Failed to load progress entries'));
    }
  });

  app.put('/api/techniques/:id/progress', requireAuth, async (req, res) => {
    const parsedRequest = parseTechniqueProgressRequest(req.body);
    if (!parsedRequest.ok) {
      logger.info('progress.invalid_payload', { body: req.body, techniqueId: req.params.id, userId: req.authUser.id });
      return res.status(400).json(parsedRequest);
    }

    try {
      const { attempts, bestScore, completed, lastDifficulty, masteryLevel, lastPlayedAt } = parsedRequest.value.progress;
      await store.query(
        `INSERT INTO progress_entries 
          (user_id, technique_id, attempts, best_score, completed, last_difficulty, mastery_level, last_played_at, updated_at) 
         VALUES ($1, $2, $3, $4, $5, $6, $7, $8::timestamptz, $9::timestamptz)
         ON CONFLICT (user_id, technique_id) DO UPDATE SET 
          attempts = EXCLUDED.attempts,
          best_score = EXCLUDED.best_score,
          completed = EXCLUDED.completed,
          last_difficulty = EXCLUDED.last_difficulty,
          mastery_level = EXCLUDED.mastery_level,
          last_played_at = EXCLUDED.last_played_at,
          updated_at = EXCLUDED.updated_at`,
        [
          req.authUser.id, req.params.id, 
          attempts || 0, bestScore || 0, Boolean(completed), 
          lastDifficulty || '', masteryLevel || '', 
          lastPlayedAt ? new Date(lastPlayedAt).toISOString() : new Date().toISOString(),
          new Date().toISOString()
        ]
      );
      
      const nextProgress = {
        userId: req.authUser.id,
        techniqueId: req.params.id,
        ...parsedRequest.value.progress,
        updatedAt: new Date().toISOString()
      };
      
      logger.info('progress.saved', { userId: req.authUser.id, techniqueId: req.params.id });
      res.json(progressResponseDto(nextProgress));
    } catch (error) {
      logger.error('progress.save_failed', { error: error.message, userId: req.authUser.id, techniqueId: req.params.id });
      res.status(500).json(errorResponseDto('Failed to save progress'));
    }
  });

  app.get('/api/sessions', requireAuth, async (req, res) => {
    try {
      const result = await store.query('SELECT * FROM practice_sessions WHERE user_id = $1 ORDER BY created_at DESC', [req.authUser.id]);
      const sessions = rowsToCamel(result.rows).map(stripSnakeOnly);
      res.json(practiceSessionsResponseDto(sessions));
    } catch (error) {
      logger.error('practice_sessions.load_failed', { error: error.message, userId: req.authUser.id });
      res.status(500).json(errorResponseDto('Failed to load practice sessions'));
    }
  });

  app.post('/api/sessions', requireAuth, async (req, res) => {
    const parsedRequest = parseSaveSessionRequest(req.body);
    if (!parsedRequest.ok) return res.status(400).json(parsedRequest);
    try {
      const session = {
        id: `session_${crypto.randomUUID()}`,
        userId: req.authUser.id,
        createdAt: new Date().toISOString(),
        ...parsedRequest.value.session,
      };
      await store.query('INSERT INTO practice_sessions (id, user_id, technique_id, score, total_nodes, time_taken_ms, config, history, created_at) VALUES ($1, $2, $3, $4, $5, $6, $7::jsonb, $8::jsonb, $9::timestamptz)', 
        [session.id, session.userId, session.techniqueId, session.score, session.totalNodes, session.timeTakenMs, JSON.stringify(session.config || {}), JSON.stringify(session.history || []), session.createdAt]);
      res.status(201).json(sessionSavedResponseDto(session.id));
    } catch (error) { res.status(500).json(errorResponseDto('Failed to save session')); }
  });

  app.post('/api/feedback', requireAuth, async (req, res) => {
    if (req.authUser.role === 'guest' || req.authUser.type === 'guest' || req.authUser.provider === 'guest') {
      return res.status(403).json(errorResponseDto('Sign in with Google to send feedback', 'FORBIDDEN'));
    }

    const parsedRequest = parseFeedbackRequest(req.body);
    if (!parsedRequest.ok) {
      logger.info('feedback.invalid_payload', { body: req.body, userId: req.authUser.id });
      return res.status(400).json(parsedRequest);
    }

    try {
      const feedback = {
        id: `feedback_${crypto.randomUUID()}`,
        userId: req.authUser.id,
        createdAt: new Date().toISOString(),
        ...parsedRequest.value.feedback,
      };
      await store.query(
        `INSERT INTO feedback_entries (id, user_id, type, message, technique_id, screen, difficulty, created_at)
         VALUES ($1, $2, $3, $4, $5, $6, $7, $8::timestamptz)`,
        [feedback.id, feedback.userId, feedback.type || '', feedback.message || '', feedback.techniqueId || '', feedback.screen || '', feedback.difficulty || '', feedback.createdAt]
      );
      logger.info('feedback.saved', {
        userId: req.authUser.id,
        feedbackId: feedback.id,
        type: feedback.type,
        techniqueId: feedback.techniqueId,
      });
      res.status(201).json(feedbackCreatedResponseDto(feedback));
    } catch (error) {
      logger.error('feedback.save_failed', { error: error.message, userId: req.authUser.id });
      res.status(500).json(errorResponseDto('Failed to save feedback'));
    }
  });

  app.get('/api/feedback', requireAuth, async (req, res) => {
    if (req.authUser.role !== 'owner') {
      return res.status(403).json(errorResponseDto('Only owners can view feedback', 'FORBIDDEN'));
    }
    try {
      const result = await store.query(`
        SELECT f.*, u.name as user_display_name, u.email as user_email, u.role as user_role, 'google' as provider 
        FROM feedback_entries f 
        LEFT JOIN app_users u ON f.user_id = u.id 
        ORDER BY f.created_at DESC
      `);
      const feedback = rowsToCamel(result.rows).map(stripSnakeOnly);
      res.json(feedbackResponseDto(feedback));
    } catch (error) {
      logger.error('feedback.list_failed', { error: error.message, userId: req.authUser.id });
      res.status(500).json(errorResponseDto('Failed to load feedback'));
    }
  });

  app.delete('/api/feedback/:feedbackId', requireAuth, async (req, res) => {
    if (!['owner', 'teacher'].includes(req.authUser.role)) {
      return res.status(403).json(errorResponseDto('Only owners or teachers can delete feedback', 'FORBIDDEN'));
    }

    try {
      let result;
      if (req.authUser.role === 'owner') {
        result = await store.query('DELETE FROM feedback_entries WHERE id = $1 RETURNING id', [req.params.feedbackId]);
      } else {
        // Teachers can delete feedback from any student enrolled in one of their classes,
        // regardless of whether the feedback itself has a classId attached.
        result = await store.query(
          `DELETE FROM feedback_entries
           WHERE id = $1
             AND user_id IN (
               SELECT DISTINCT e.student_id
               FROM enrollments e
               JOIN classes c ON e.class_id = c.id
               WHERE c.teacher_id = $2
             )
           RETURNING id`,
          [req.params.feedbackId, req.authUser.id]
        );
      }

      if (result.rowCount === 0) {
        return res.status(404).json(errorResponseDto('Feedback not found', 'NOT_FOUND'));
      }

      logger.info('feedback.deleted', {
        feedbackId: req.params.feedbackId,
        userId: req.authUser.id,
        role: req.authUser.role,
      });
      res.json({ status: 'success', data: { id: req.params.feedbackId } });
    } catch (error) {
      logger.error('feedback.delete_failed', { error: error.message, userId: req.authUser.id });
      res.status(500).json(errorResponseDto('Failed to delete feedback'));
    }
  });

  app.get('/api/teacher/feedback', requireAuth, async (req, res) => {
    if (!['teacher', 'owner'].includes(req.authUser.role)) return res.status(403).json(errorResponseDto('Only teachers or owners can view teacher feedback', 'FORBIDDEN'));
    try {
      let query = `SELECT f.*, u.name as user_display_name, u.email as user_email, u.role as user_role, 'google' as provider, c.id as class_id, c.name as class_name FROM feedback_entries f JOIN enrollments e ON f.user_id = e.student_id JOIN classes c ON e.class_id = c.id JOIN app_users u ON f.user_id = u.id WHERE (c.teacher_id = $1 OR $2 = 'owner')`;
      const params = [req.authUser.id, req.authUser.role];
      const classIdFilter = typeof req.query.classId === 'string' ? req.query.classId.trim() : '';
      const typeFilter = typeof req.query.type === 'string' ? req.query.type.trim().toLowerCase() : '';
      if (classIdFilter) { query += ` AND c.id = $3`; params.push(classIdFilter); }
      if (typeFilter) { query += ` AND LOWER(f.type) = ${params.length + 1}`; params.push(typeFilter); }
      query += ` ORDER BY f.created_at DESC`;
      const result = await store.query(query, params);
      res.json(teacherFeedbackResponseDto(rowsToCamel(result.rows).map(stripSnakeOnly)));
    } catch (error) { res.status(500).json(errorResponseDto('Failed to load feedback')); }
  });

  app.patch('/api/teacher/feedback/:feedbackId/review', requireAuth, async (req, res) => {
    if (!['teacher', 'owner'].includes(req.authUser.role)) return res.status(403).json(errorResponseDto('Only teachers or owners can review feedback', 'FORBIDDEN'));
    try {
      const result = await store.query(`UPDATE feedback_entries SET reviewed_at = NOW(), reviewed_by = $1 WHERE id = $2 RETURNING *`, [req.authUser.id, req.params.feedbackId]);
      if (result.rowCount === 0) return res.status(404).json(errorResponseDto('Feedback not found', 'NOT_FOUND'));
      res.json(teacherFeedbackResponseDto([stripSnakeOnly(rowsToCamel(result.rows)[0])]));
    } catch (error) { res.status(500).json(errorResponseDto('Failed to review feedback')); }
  });

  app.post('/api/mistakes', requireAuth, async (req, res) => {
    const parsedRequest = parseSaveMistakeRequest(req.body);
    if (!parsedRequest.ok) {
      logger.info('mistake.invalid_payload', { body: req.body, userId: req.authUser.id });
      return res.status(400).json(parsedRequest);
    }

    try {
      const mistake = {
        id: `mistake_${crypto.randomUUID()}`,
        userId: req.authUser.id,
        createdAt: new Date().toISOString(),
        ...parsedRequest.value.mistake,
      };
      await store.query(
        `INSERT INTO mistakes (id, user_id, technique_id, question, correct_answer, user_answer, payload, created_at)
         VALUES ($1, $2, $3, $4, $5, $6, $7::jsonb, $8::timestamptz)`,
        [mistake.id, mistake.userId, mistake.techniqueId, mistake.question, mistake.correctAnswer, mistake.userAnswer, mistake, mistake.createdAt]
      );
      logger.info('mistake.saved', {
        userId: req.authUser.id,
        mistakeId: mistake.id,
        techniqueId: mistake.techniqueId,
      });
      res.status(201).json(mistakeCreatedResponseDto(mistake));
    } catch (error) {
      logger.error('mistake.save_failed', { error: error.message, userId: req.authUser.id });
      res.status(500).json(errorResponseDto('Failed to save mistake'));
    }
  });

  app.get('/api/mistakes', requireAuth, async (req, res) => {
    try {
      const result = await store.query('SELECT * FROM mistakes WHERE user_id = $1 ORDER BY created_at DESC', [req.authUser.id]);
      const mistakes = rowsToCamel(result.rows).map(row => stripSnakeOnly({ ...row.payload, ...row }));
      res.json(mistakesResponseDto(mistakes));
    } catch (error) {
      logger.error('mistakes.load_failed', { error: error.message, userId: req.authUser.id });
      res.status(500).json(errorResponseDto('Failed to load mistakes'));
    }
  });

  app.get('/api/dashboard', requireAuth, async (req, res) => {
    try {
      const [progressRes, sessionsRes] = await Promise.all([
        store.query('SELECT * FROM progress_entries WHERE user_id = $1 ORDER BY updated_at DESC', [req.authUser.id]),
        store.query('SELECT * FROM practice_sessions WHERE user_id = $1 ORDER BY created_at DESC', [req.authUser.id])
      ]);
      const progressEntries = rowsToCamel(progressRes.rows).map(stripSnakeOnly);
      const practiceSessions = rowsToCamel(sessionsRes.rows).map(stripSnakeOnly);
      res.json(dashboardResponseDto(buildDashboard({
        user: req.authUser,
        progressEntries,
        practiceSessions,
      })));
    } catch (error) {
      logger.error('dashboard.load_failed', { error: error.message, userId: req.authUser.id });
      res.status(500).json(errorResponseDto('Failed to load dashboard'));
    }
  });

  app.get('/api/users', requireAuth, async (req, res) => {
    if (req.authUser.role !== 'owner') return res.status(403).json(errorResponseDto('Only owners can list users', 'FORBIDDEN'));
    try {
      const result = await store.query('SELECT * FROM app_users ORDER BY created_at ASC');
      res.json(usersResponseDto(rowsToCamel(result.rows).map(row => sanitizeGoogleUser({ ...row, provider: 'google' }))));
    } catch (error) { res.status(500).json(errorResponseDto('Failed to list users')); }
  });

  app.get('/api/admin/users/:userId/detail', requireAuth, async (req, res) => {
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
  });

  app.get('/api/admin/analytics', requireAuth, async (req, res) => {
    if (req.authUser.role !== 'owner') {
      return res.status(403).json(errorResponseDto('Only owners can view analytics', 'FORBIDDEN'));
    }
    try {
      const [
        totalUsersRes, activeTeachersRes, totalClassesRes, activeClassesRes, 
        totalResourcesRes, totalFeedbackRes, totalSessionsRes, mostUsedRes, mostReportedRes
      ] = await Promise.all([
        store.query('SELECT COUNT(*) FROM app_users'),
        store.query("SELECT COUNT(*) FROM app_users WHERE role = 'teacher' AND last_seen_at >= NOW() - INTERVAL '30 days'"),
        store.query('SELECT COUNT(*) FROM classes'),
        store.query('SELECT COUNT(DISTINCT class_id) FROM enrollments'),
        store.query('SELECT COUNT(*) FROM class_resources'),
        store.query('SELECT COUNT(*) FROM feedback_entries'),
        store.query('SELECT COUNT(*) FROM practice_sessions'),
        store.query('SELECT technique_id, COUNT(*) as uses FROM practice_sessions GROUP BY technique_id ORDER BY uses DESC LIMIT 5'),
        store.query('SELECT technique_id, type, COUNT(*) as count FROM feedback_entries GROUP BY technique_id, type ORDER BY count DESC LIMIT 5')
      ]);

      res.json(analyticsResponseDto({
        summary: {
          totalUsers: parseInt(totalUsersRes.rows[0].count, 10),
          activeTeachers: parseInt(activeTeachersRes.rows[0].count, 10),
          totalClasses: parseInt(totalClassesRes.rows[0].count, 10),
          activeClasses: parseInt(activeClassesRes.rows[0].count, 10),
          totalResources: parseInt(totalResourcesRes.rows[0].count, 10),
          totalFeedback: parseInt(totalFeedbackRes.rows[0].count, 10),
          totalPracticeSessions: parseInt(totalSessionsRes.rows[0].count, 10),
        },
        mostUsedTopics: mostUsedRes.rows.map(r => ({ techniqueId: r.technique_id, uses: parseInt(r.uses, 10) })),
        mostReportedContentIssues: mostReportedRes.rows.map(r => ({ techniqueId: r.technique_id, type: r.type, count: parseInt(r.count, 10) }))
      }));
    } catch (error) {
      logger.error('admin.analytics_failed', { error: error.message, userId: req.authUser.id });
      res.status(500).json(errorResponseDto('Failed to load analytics'));
    }
  });

  app.get('/api/admin/realtime-config', requireAuth, (req, res) => {
    if (!['owner', 'teacher'].includes(req.authUser.role)) {
      return res.status(403).json(errorResponseDto('Only owners or teachers can view config', 'FORBIDDEN'));
    }
    res.json(successResponseDto({
      supabaseUrl: process.env.SUPABASE_URL || '',
      supabaseKey: process.env.SUPABASE_PUBLISHABLE_KEY || ''
    }));
  });

  app.get('/api/admin/stats', requireAuth, async (req, res) => {
    if (!['owner', 'teacher'].includes(req.authUser.role)) {
      return res.status(403).json(errorResponseDto('Only owners or teachers can view admin stats', 'FORBIDDEN'));
    }

    try {
      const [usersRes, classesRes, sessionsRes, progressRes, topAlgosRes, engagementRes] = await Promise.all([
        store.query('SELECT COUNT(*) FROM app_users'),
        store.query('SELECT COUNT(*) FROM classes'),
        store.query('SELECT COUNT(*) FROM practice_sessions'),
        store.query('SELECT COUNT(*) FROM progress_entries'),
        store.query(`
          SELECT technique_id as id, COUNT(*) as count 
          FROM progress_entries 
          GROUP BY technique_id 
          ORDER BY count DESC 
          LIMIT 5
        `),
        store.query(`
          SELECT DATE(created_at) as date, COUNT(*) as count 
          FROM practice_sessions 
          WHERE created_at >= NOW() - INTERVAL '14 days'
          GROUP BY DATE(created_at) 
          ORDER BY date ASC
        `)
      ]);
      res.json({ data: { stats: {
        viewerRole: req.authUser.role,
        totalUsers: parseInt(usersRes.rows[0].count, 10),
        totalClasses: parseInt(classesRes.rows[0].count, 10),
        totalSessions: parseInt(sessionsRes.rows[0].count, 10),
        totalProgressEntries: parseInt(progressRes.rows[0].count, 10),
        topAlgorithms: topAlgosRes.rows.map(r => ({ id: r.id, count: parseInt(r.count, 10) })),
        dailyEngagement: engagementRes.rows.map(r => ({ date: r.date, count: parseInt(r.count, 10) }))
      } } });
    } catch (error) {
      logger.error('admin.stats_failed', { error: error.message, userId: req.authUser.id });
      res.status(500).json(errorResponseDto('Failed to load admin stats'));
    }
  });

  app.get('/api/admin/export', requireAuth, async (req, res) => {
    res.status(501).json(errorResponseDto('Export not supported in SQL mode', 'NOT_IMPLEMENTED'));
  });

  app.get('/api/admin/audit-logs', requireAuth, async (req, res) => {
    if (req.authUser.role !== 'owner') {
      return res.status(403).json(errorResponseDto('Only owners can view audit logs', 'FORBIDDEN'));
    }
    try {
      const result = await store.query('SELECT * FROM audit_logs ORDER BY created_at DESC');
      const auditLogs = rowsToCamel(result.rows).map(stripSnakeOnly).map(sanitizeAuditLog);
      res.json(auditLogsResponseDto(auditLogs));
    } catch (error) {
      logger.error('admin.audit_logs_failed', { error: error.message, userId: req.authUser.id });
      res.status(500).json(errorResponseDto('Failed to load audit logs'));
    }
  });

  app.get('/api/admin/backup', requireAuth, async (req, res) => {
    res.status(501).json(errorResponseDto('Backup not supported in SQL mode', 'NOT_IMPLEMENTED'));
  });

  app.post('/api/admin/restore', requireAuth, async (req, res) => {
    res.status(501).json(errorResponseDto('Restore not supported in SQL mode', 'NOT_IMPLEMENTED'));
  });

  app.post('/api/classes', requireAuth, async (req, res) => {
    if (!['teacher', 'owner'].includes(req.authUser.role)) return res.status(403).json(errorResponseDto('Only teachers or owners can create classes', 'FORBIDDEN'));
    const parsedRequest = parseCreateClassRequest(req.body);
    if (!parsedRequest.ok) return res.status(400).json(parsedRequest);
    try {
      const codeRes = await store.query('SELECT join_code FROM classes');
      const existingCodes = codeRes.rows.map(r => ({ joinCode: r.join_code }));
      const classroom = {
        id: `class_${crypto.randomUUID()}`, teacherId: req.authUser.id, createdAt: new Date().toISOString(),
        joinCode: createJoinCode(existingCodes), ...parsedRequest.value.classroom,
      };
      await store.query('INSERT INTO classes (id, teacher_id, name, description, join_code, created_at, updated_at) VALUES ($1, $2, $3, $4, $5, $6::timestamptz, $6::timestamptz)',
        [classroom.id, classroom.teacherId, classroom.name || 'Untitled class', classroom.description || '', classroom.joinCode, classroom.createdAt]);
      res.status(201).json(classResponseDto(sanitizeClassroom(classroom, { studentCount: 0 }), { isTeacherViewer: true }));
    } catch (error) { res.status(500).json(errorResponseDto('Failed to create class')); }
  });

  app.get('/api/classes', requireAuth, async (req, res) => {
    try {
      const cacheKey = `classes:${req.authUser.role}:${req.authUser.id}`;
      const cached = cache.get(cacheKey);
      if (cached) return res.json(cached);

      let result = [];
      if (req.authUser.role === 'owner') {
        const classRes = await store.query('SELECT c.*, u.name as teacher_name, u.email as teacher_email FROM classes c JOIN app_users u ON c.teacher_id = u.id');
        const enrollRes = await store.query('SELECT class_id, COUNT(*) as student_count FROM enrollments GROUP BY class_id');
        const counts = Object.fromEntries(enrollRes.rows.map(r => [r.class_id, parseInt(r.student_count, 10)]));
        result = rowsToCamel(classRes.rows).map(c => sanitizeClassroom(c, { teacherName: c.teacherName || c.teacherEmail || '', studentCount: counts[c.id] || 0 }));
      } else if (req.authUser.role === 'teacher') {
        const classRes = await store.query('SELECT c.*, u.name as teacher_name, u.email as teacher_email FROM classes c JOIN app_users u ON c.teacher_id = u.id WHERE c.teacher_id = $1', [req.authUser.id]);
        const classIds = classRes.rows.map(r => r.id);
        let counts = {};
        if (classIds.length > 0) {
          const enrollRes = await store.query('SELECT class_id, COUNT(*) as student_count FROM enrollments WHERE class_id = ANY($1) GROUP BY class_id', [classIds]);
          counts = Object.fromEntries(enrollRes.rows.map(r => [r.class_id, parseInt(r.student_count, 10)]));
        }
        result = rowsToCamel(classRes.rows).map(c => sanitizeClassroom(c, { teacherName: c.teacherName || c.teacherEmail || '', studentCount: counts[c.id] || 0 }));
      } else {
        const enrollRes = await store.query('SELECT c.*, u.name as teacher_name, u.email as teacher_email, e.joined_at FROM classes c JOIN app_users u ON c.teacher_id = u.id JOIN enrollments e ON c.id = e.class_id WHERE e.student_id = $1', [req.authUser.id]);
        result = rowsToCamel(enrollRes.rows).map(c => sanitizeClassroom(c, { teacherName: c.teacherName || c.teacherEmail || '', studentCount: 0 }));
      }

      const response = classesResponseDto(result);
      cache.set(cacheKey, response);
      res.json(response);
    } catch (error) { res.status(500).json(errorResponseDto('Failed to load classes')); }
  });

  app.post('/api/classes/join', requireAuth, async (req, res) => {
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
        [`enroll_${crypto.randomUUID()}`, classroom.id, req.authUser.id, new Date().toISOString()]);
      res.status(201).json(successMessageResponseDto('Joined class successfully'));
    } catch (error) { res.status(500).json(errorResponseDto('Failed to join class')); }
  });

  app.get('/api/classes/:classId/students', requireAuth, async (req, res) => {
    try {
      const classRes = await store.query('SELECT * FROM classes WHERE id = $1', [req.params.classId]);
      if (classRes.rowCount === 0) return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      const classroom = rowsToCamel(classRes.rows)[0];
      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      const studentsRes = await store.query(`SELECT u.*, e.joined_at FROM app_users u JOIN enrollments e ON u.id = e.student_id WHERE e.class_id = $1 ORDER BY u.name ASC`, [classroom.id]);
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
  });

  app.get('/api/classes/:classId/students/:studentId', requireAuth, async (req, res) => {
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

      res.json(studentDetailResponseDto({
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
  });

  app.post('/api/classes/:classId/students/:studentId/feedback', requireAuth, async (req, res) => {
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
      
      const note = { id: `note_${crypto.randomUUID()}`, classId: classroom.id, studentId: req.params.studentId, teacherId: req.authUser.id, createdAt: new Date().toISOString(), ...parsedRequest.value.note };
      await store.query('INSERT INTO teacher_notes (id, class_id, student_id, teacher_id, message, status, created_at) VALUES ($1, $2, $3, $4, $5, $6, $7::timestamptz)',
        [note.id, note.classId, note.studentId, note.teacherId, note.message, note.status || '', note.createdAt]);
      res.status(201).json(teacherNoteCreatedResponseDto(sanitizeTeacherNote(note, { googleUsers: [{ id: req.authUser.id, name: req.authUser.name || req.authUser.email }] })));
    } catch (error) { res.status(500).json(errorResponseDto('Failed to create note')); }
  });

  app.get('/api/classes/:classId/resources', requireAuth, async (req, res) => {
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
      
      // Build dummy data object for sanitizeClassResource which expects it
      const dummyData = { googleUsers: resourcesRes.rows.map(r => ({ id: r.uploaded_by, name: r.uploader_name, email: r.uploader_email })) };
      const resources = await Promise.all(resourceItems.map((item) => sanitizeClassResource(item, dummyData)));
      res.json(classResourcesResponseDto(resources));
    } catch (error) {
      logger.error('class.resources_failed', { error: error.message, userId: req.authUser.id, classId: req.params.classId });
      res.status(500).json(errorResponseDto('Failed to load class resources'));
    }
  });

  app.post('/api/classes/:classId/resources/upload-url', requireAuth, async (req, res) => {
    if (!['teacher', 'owner'].includes(req.authUser.role)) return res.status(403).json(errorResponseDto('Only teachers or owners can upload resources', 'FORBIDDEN'));
    const parsedRequest = parseClassResourceUploadInitRequest(req.body);
    if (!parsedRequest.ok) return res.status(400).json(parsedRequest);
    try {
      if (!isCloudStorageConfigured()) return res.status(503).json(errorResponseDto('Cloud storage is not configured for direct uploads', 'UNAVAILABLE'));
      const classRes = await store.query('SELECT * FROM classes WHERE id = $1', [req.params.classId]);
      if (classRes.rowCount === 0) return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      const classroom = rowsToCamel(classRes.rows)[0];
      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      
      const storedFileName = `${Date.now()}_${crypto.randomUUID().slice(0, 8)}_${sanitizeResourceFileName(parsedRequest.value.fileName)}`;
      const storagePath = `classes/${classroom.id}/${storedFileName}`;
      const uploadUrl = await generateUploadUrl(storagePath, parsedRequest.value.mimeType);
      res.json(classResourceUploadInitResponseDto(uploadUrl, storagePath));
    } catch (error) { res.status(500).json(errorResponseDto('Failed to initialize upload')); }
  });

  app.post('/api/classes/:classId/resources/complete', requireAuth, async (req, res) => {
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

      const resource = { id: `res_${crypto.randomUUID()}`, classId: classroom.id, title: parsedRequest.value.title, type: parsedRequest.value.type || 'resource', fileName: parsedRequest.value.fileName, mimeType: parsedRequest.value.mimeType, sizeBytes: parsedRequest.value.sizeBytes, uploadedBy: req.authUser.id, downloadPath: parsedRequest.value.storagePath, createdAt: new Date().toISOString() };
      await store.query('INSERT INTO class_resources (id, class_id, title, type, file_name, mime_type, size_bytes, uploaded_by, download_path, created_at) VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10::timestamptz)',
        [resource.id, resource.classId, resource.title, resource.type, resource.fileName, resource.mimeType, resource.sizeBytes, resource.uploadedBy, resource.downloadPath, resource.createdAt]);
      res.status(201).json(classResourceCreatedResponseDto(await sanitizeClassResource(resource, { googleUsers: [{ id: req.authUser.id, name: req.authUser.name || req.authUser.email }] })));
    } catch (error) { res.status(500).json(errorResponseDto('Failed to complete upload')); }
  });

  app.post('/api/classes/:classId/resources', requireAuth, async (req, res) => {
    if (!['teacher', 'owner'].includes(req.authUser.role)) return res.status(403).json(errorResponseDto('Only teachers or owners can upload resources', 'FORBIDDEN'));
    const parsedRequest = parseClassResourceUploadRequest(req.body);
    if (!parsedRequest.ok) return res.status(400).json(parsedRequest);
    try {
      const classRes = await store.query('SELECT * FROM classes WHERE id = $1', [req.params.classId]);
      if (classRes.rowCount === 0) return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
      const classroom = rowsToCamel(classRes.rows)[0];
      if (classroom.teacherId !== req.authUser.id && req.authUser.role !== 'owner') return res.status(403).json(errorResponseDto('You do not manage this class', 'FORBIDDEN'));
      if (!ALLOWED_RESOURCE_MIME_TYPES.has(parsedRequest.value.mimeType)) return res.status(400).json(errorResponseDto('This file type is not allowed', 'VALIDATION_ERROR'));

      const storedFileName = `${Date.now()}_${crypto.randomUUID().slice(0, 8)}_${sanitizeResourceFileName(parsedRequest.value.fileName)}`;
      const downloadPath = await uploadClassResourceFile({ uploadDir, classroomId: classroom.id, storedFileName, binary: req.body, mimeType: parsedRequest.value.mimeType });
      const resource = { id: `res_${crypto.randomUUID()}`, classId: classroom.id, title: parsedRequest.value.title, type: parsedRequest.value.type || 'resource', fileName: storedFileName, mimeType: parsedRequest.value.mimeType, sizeBytes: Buffer.byteLength(req.body), uploadedBy: req.authUser.id, downloadPath, createdAt: new Date().toISOString() };
      await store.query('INSERT INTO class_resources (id, class_id, title, type, file_name, mime_type, size_bytes, uploaded_by, download_path, created_at) VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10::timestamptz)',
        [resource.id, resource.classId, resource.title, resource.type, resource.fileName, resource.mimeType, resource.sizeBytes, resource.uploadedBy, resource.downloadPath, resource.createdAt]);
      res.status(201).json(classResourceCreatedResponseDto(await sanitizeClassResource(resource, { googleUsers: [{ id: req.authUser.id, name: req.authUser.name || req.authUser.email }] })));
    } catch (error) { res.status(500).json(errorResponseDto('Failed to upload resource')); }
  });

  app.delete('/api/classes/:classId/resources/:resourceId', requireAuth, async (req, res) => {
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
  });

  app.put('/api/users/:userId/role', requireAuth, async (req, res) => {
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
  });

  app.post('/save-session', async (req, res) => { res.status(410).json({ error: 'Endpoint removed' }); });

  app.get('/sessions', async (req, res) => { res.status(410).json({ error: 'Endpoint removed' }); });

  app.post('/share-visualization', async (req, res) => { res.status(410).json({ error: 'Endpoint removed' }); });

  app.get('/shared/:shareId', async (req, res) => { res.status(410).json({ error: 'Endpoint removed' }); });

  app.post('/save-preferences', async (req, res) => { res.status(410).json({ error: 'Endpoint removed' }); });

  app.get('/preferences', async (req, res) => { res.status(410).json({ error: 'Endpoint removed' }); });

  return app;
};

const startServer = ({ port = process.env.PORT || 5000, dataFile, logger } = {}) => {
  const app = createApp({ dataFile, logger });
  const server = app.listen(port, () => {
    (logger || createLogger()).info('server.started', { port });
  });
  return { app, server };
};

if (require.main === module) {
  startServer();
}

module.exports = {
  createApp,
  createLogger,
  startServer,
};


