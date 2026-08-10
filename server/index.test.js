const assert = require('node:assert/strict');
const fs = require('fs');
const os = require('os');
const path = require('path');

const { createApp } = require('./index');

const createTestServer = async () => {
  const tempDir = fs.mkdtempSync(path.join(os.tmpdir(), 'dsa-server-test-'));
  const dataFile = path.join(tempDir, 'data.json');
  const app = createApp({
    dataFile,
    logger: {
      info() {},
      error() {},
    },
  });
  const server = await new Promise((resolve) => {
    const instance = app.listen(0, () => resolve(instance));
  });
  const address = server.address();

  return {
    baseUrl: `http://127.0.0.1:${address.port}`,
    dataFile,
    async close() {
      await new Promise((resolve, reject) => {
        server.close((error) => (error ? reject(error) : resolve()));
        server.closeIdleConnections?.();
        server.closeAllConnections?.();
      });
      fs.rmSync(tempDir, { recursive: true, force: true });
    },
  };
};

const seedGoogleUser = (server, {
  id = 'student_1',
  token = 'student_token',
  name = 'Student One',
  email = 'student@example.com',
  role = 'student',
} = {}) => {
  const seedData = {
    sessions: [],
    practiceSessions: [],
    preferences: {},
    progressEntries: [],
    feedbackEntries: [],
    shares: [],
    users: [],
    mistakes: [],
    googleUsers: [
      {
        id,
        googleId: `${id}_google`,
        email,
        name,
        role,
        token,
        createdAt: '2026-04-26T08:00:00.000Z',
        lastSeenAt: '2026-04-26T08:00:00.000Z',
      },
    ],
    classes: [],
    enrollments: [],
    teacherNotes: [],
    classResources: [],
    auditLogs: [],
  };
  fs.writeFileSync(server.dataFile, JSON.stringify(seedData, null, 2));
  return { userId: id, token, name, email, role };
};

const run = async (name, fn) => {
  try {
    await fn();
    console.log(`PASS ${name}`);
  } catch (error) {
    console.error(`FAIL ${name}`);
    console.error(error);
    process.exitCode = 1;
  }
};

const runTests = async () => {
  await run('guest auth is removed', async () => {
    const server = await createTestServer();
    try {
      const authResponse = await fetch(`${server.baseUrl}/api/auth/guest`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ displayName: 'Prasanna Guest' }),
      });
      assert.equal(authResponse.status, 410);
      const authBody = await authResponse.json();
      assert.equal(authBody.error.code, 'GUEST_REMOVED');

      const unauthorizedResponse = await fetch(`${server.baseUrl}/api/me`);
      assert.equal(unauthorizedResponse.status, 401);
    } finally {
      await server.close();
    }
  });

  await run('progress endpoints persist per-user technique progress', async () => {
    const server = await createTestServer();
    try {
      const authUser = seedGoogleUser(server, { name: 'Learner One', token: 'learner_token' });
      const authHeaders = {
        Authorization: `Bearer ${authUser.token}`,
        'Content-Type': 'application/json',
      };

      const initialResponse = await fetch(`${server.baseUrl}/api/techniques/linear_search/progress`, {
        headers: { Authorization: `Bearer ${authUser.token}` },
      });
      assert.equal(initialResponse.status, 200);
      const initialBody = await initialResponse.json();
      assert.equal(initialBody.data.progress.techniqueId, 'linear_search');
      assert.equal(initialBody.data.progress.attempts, 0);

      const saveResponse = await fetch(`${server.baseUrl}/api/techniques/linear_search/progress`, {
        method: 'PUT',
        headers: authHeaders,
        body: JSON.stringify({
          progress: {
            attempts: 4,
            bestScore: 8,
            completed: true,
            lastDifficulty: 'expert',
            masteryLevel: 'gold',
            lastPlayedAt: '2026-04-22T09:30:00.000Z',
          },
        }),
      });
      assert.equal(saveResponse.status, 200);
      const saveBody = await saveResponse.json();
      assert.equal(saveBody.data.progress.bestScore, 8);
      assert.equal(saveBody.data.progress.masteryLevel, 'gold');

      const loadResponse = await fetch(`${server.baseUrl}/api/techniques/linear_search/progress`, {
        headers: { Authorization: `Bearer ${authUser.token}` },
      });
      const loadBody = await loadResponse.json();
      assert.equal(loadBody.data.progress.completed, true);
      assert.equal(loadBody.data.progress.lastDifficulty, 'expert');
    } finally {
      await server.close();
    }
  });

  await run('practice sessions are created and listed for the authenticated user', async () => {
    const server = await createTestServer();
    try {
      const authUser = seedGoogleUser(server, { name: 'Practice User', token: 'practice_token' });
      const authHeaders = {
        Authorization: `Bearer ${authUser.token}`,
        'Content-Type': 'application/json',
      };

      const invalidResponse = await fetch(`${server.baseUrl}/api/sessions`, {
        method: 'POST',
        headers: authHeaders,
        body: JSON.stringify({ session: {} }),
      });
      assert.equal(invalidResponse.status, 400);

      const createResponse = await fetch(`${server.baseUrl}/api/sessions`, {
        method: 'POST',
        headers: authHeaders,
        body: JSON.stringify({
          session: {
            techniqueId: 'stack',
            difficulty: 'guided',
            score: 5,
            durationMs: 21000,
          },
        }),
      });
      assert.equal(createResponse.status, 201);
      const createBody = await createResponse.json();
      assert.equal(createBody.data.session.techniqueId, 'stack');
      assert.ok(createBody.data.session.id);

      const listResponse = await fetch(`${server.baseUrl}/api/sessions`, {
        headers: { Authorization: `Bearer ${authUser.token}` },
      });
      assert.equal(listResponse.status, 200);
      const listBody = await listResponse.json();
      assert.equal(listBody.data.sessions.length, 1);
      assert.equal(listBody.data.sessions[0].difficulty, 'guided');

      const logoutResponse = await fetch(`${server.baseUrl}/api/auth/logout`, {
        method: 'POST',
        headers: { Authorization: `Bearer ${authUser.token}` },
      });
      assert.equal(logoutResponse.status, 200);

      const afterLogoutResponse = await fetch(`${server.baseUrl}/api/sessions`, {
        headers: { Authorization: `Bearer ${authUser.token}` },
      });
      assert.equal(afterLogoutResponse.status, 401);
    } finally {
      await server.close();
    }
  });

  await run('dashboard endpoint summarizes streaks, recent activity, and weak topics', async () => {
    const server = await createTestServer();
    try {
      const authUser = seedGoogleUser(server, { name: 'Dashboard User', token: 'dashboard_token' });
      const authHeaders = {
        Authorization: `Bearer ${authUser.token}`,
        'Content-Type': 'application/json',
      };

      await fetch(`${server.baseUrl}/api/techniques/linear_search/progress`, {
        method: 'PUT',
        headers: authHeaders,
        body: JSON.stringify({
          progress: {
            attempts: 5,
            bestScore: 2,
            completed: false,
            lastDifficulty: 'guided',
            masteryLevel: 'bronze',
            lastPlayedAt: '2026-04-22T09:30:00.000Z',
          },
        }),
      });

      await fetch(`${server.baseUrl}/api/techniques/binary_search/progress`, {
        method: 'PUT',
        headers: authHeaders,
        body: JSON.stringify({
          progress: {
            attempts: 3,
            bestScore: 8,
            completed: true,
            lastDifficulty: 'expert',
            masteryLevel: 'gold',
            lastPlayedAt: '2026-04-22T10:00:00.000Z',
          },
        }),
      });

      await fetch(`${server.baseUrl}/api/sessions`, {
        method: 'POST',
        headers: authHeaders,
        body: JSON.stringify({
          session: {
            techniqueId: 'linear_search',
            difficulty: 'guided',
            score: 2,
            durationMs: 16000,
          },
        }),
      });

      const progressListResponse = await fetch(`${server.baseUrl}/api/progress`, {
        headers: { Authorization: `Bearer ${authUser.token}` },
      });
      assert.equal(progressListResponse.status, 200);
      const progressListBody = await progressListResponse.json();
      assert.equal(progressListBody.data.progressEntries.length, 2);

      const dashboardResponse = await fetch(`${server.baseUrl}/api/dashboard`, {
        headers: { Authorization: `Bearer ${authUser.token}` },
      });
      assert.equal(dashboardResponse.status, 200);
      const dashboardBody = await dashboardResponse.json();
      assert.equal(dashboardBody.data.dashboard.user.displayName, 'Dashboard User');
      assert.equal(dashboardBody.data.dashboard.summary.totalTechniquesStarted, 2);
      assert.equal(dashboardBody.data.dashboard.summary.completedTechniques, 1);
      assert.equal(dashboardBody.data.dashboard.summary.totalPracticeSessions, 1);
      assert.ok(dashboardBody.data.dashboard.weakTopics.length >= 1);
      assert.equal(dashboardBody.data.dashboard.weakTopics[0].techniqueId, 'linear_search');
      assert.equal(dashboardBody.data.dashboard.recentActivity[0].techniqueId, 'linear_search');
    } finally {
      await server.close();
    }
  });

  await run('feedback endpoint stores in-app feedback for Google users only', async () => {
    const server = await createTestServer();
    try {
      const authResponse = await fetch(`${server.baseUrl}/api/auth/guest`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ displayName: 'Feedback User' }),
      });
      assert.equal(authResponse.status, 410);

      const googleData = {
        sessions: [],
        practiceSessions: [],
        preferences: {},
        progressEntries: [],
        feedbackEntries: [],
        shares: [],
        users: [],
        mistakes: [],
        googleUsers: [
          {
            id: 'student_1',
            googleId: 'student_google',
            email: 'student@example.com',
            name: 'Student One',
            role: 'student',
            token: 'student_token',
            createdAt: '2026-04-26T08:00:00.000Z',
            lastSeenAt: '2026-04-26T08:00:00.000Z',
          },
        ],
        classes: [],
        enrollments: [],
        teacherNotes: [],
        classResources: [],
        auditLogs: [],
      };
      fs.writeFileSync(server.dataFile, JSON.stringify(googleData, null, 2));
      const googleHeaders = {
        Authorization: 'Bearer student_token',
        'Content-Type': 'application/json',
      };

      const invalidResponse = await fetch(`${server.baseUrl}/api/feedback`, {
        method: 'POST',
        headers: googleHeaders,
        body: JSON.stringify({ feedback: { type: 'bug' } }),
      });
      assert.equal(invalidResponse.status, 400);

      const saveResponse = await fetch(`${server.baseUrl}/api/feedback`, {
        method: 'POST',
        headers: googleHeaders,
        body: JSON.stringify({
          feedback: {
            type: 'content',
            message: 'Binary Search explanation should mention sorted input earlier.',
            techniqueId: 'binary_search',
            screen: 'practice',
            difficulty: 'guided',
          },
        }),
      });
      assert.equal(saveResponse.status, 201);
      const saveBody = await saveResponse.json();
      assert.equal(saveBody.data.feedback.type, 'content');
      assert.equal(saveBody.data.feedback.techniqueId, 'binary_search');
      assert.ok(saveBody.data.feedback.id);
    } finally {
      await server.close();
    }
  });

  await run('owner can list collected feedback with sender details', async () => {
    const server = await createTestServer();
    try {
      const seedData = {
        sessions: [],
        practiceSessions: [],
        preferences: {},
        progressEntries: [],
        feedbackEntries: [
          {
            id: 'feedback_1',
            userId: 'student_1',
            createdAt: '2026-04-26T12:00:00.000Z',
            type: 'feature',
            message: 'Please add more tree problems.',
            techniqueId: 'trees',
            screen: 'home',
            difficulty: '',
          },
        ],
        shares: [],
        users: [],
        mistakes: [],
        googleUsers: [
          {
            id: 'owner_1',
            googleId: 'owner_google',
            email: 'owner@example.com',
            name: 'Owner One',
            role: 'owner',
            token: 'owner_token',
            createdAt: '2026-04-26T08:00:00.000Z',
            lastSeenAt: '2026-04-26T08:00:00.000Z',
          },
          {
            id: 'student_1',
            googleId: 'student_google',
            email: 'student@example.com',
            name: 'Student One',
            role: 'student',
            token: 'student_token',
            createdAt: '2026-04-26T08:15:00.000Z',
            lastSeenAt: '2026-04-26T08:15:00.000Z',
          },
        ],
        classes: [],
        enrollments: [],
        teacherNotes: [],
        classResources: [],
      };
      fs.writeFileSync(server.dataFile, JSON.stringify(seedData, null, 2));

      const ownerResponse = await fetch(`${server.baseUrl}/api/feedback`, {
        headers: { Authorization: 'Bearer owner_token' },
      });
      assert.equal(ownerResponse.status, 200);
      const ownerBody = await ownerResponse.json();
      assert.equal(ownerBody.data.feedback.length, 1);
      assert.equal(ownerBody.data.feedback[0].userEmail, 'student@example.com');
      assert.equal(ownerBody.data.feedback[0].message, 'Please add more tree problems.');

      const studentResponse = await fetch(`${server.baseUrl}/api/feedback`, {
        headers: { Authorization: 'Bearer student_token' },
      });
      assert.equal(studentResponse.status, 403);
    } finally {
      await server.close();
    }
  });

  await run('owner analytics, audit logs, and backup endpoints work', async () => {
    const server = await createTestServer();
    try {
      const now = new Date();
      const recentIso = now.toISOString();
      const olderIso = new Date(now.getTime() - (7 * 24 * 60 * 60 * 1000)).toISOString();
      const seedData = {
        sessions: [],
        practiceSessions: [
          {
            id: 'session_1',
            userId: 'student_1',
            techniqueId: 'binary_search',
            difficulty: 'guided',
            score: 6,
            durationMs: 18000,
            createdAt: recentIso,
          },
          {
            id: 'session_2',
            userId: 'student_1',
            techniqueId: 'binary_search',
            difficulty: 'expert',
            score: 7,
            durationMs: 20000,
            createdAt: recentIso,
          },
        ],
        preferences: {},
        progressEntries: [],
        feedbackEntries: [
          {
            id: 'feedback_1',
            userId: 'student_1',
            createdAt: recentIso,
            type: 'content',
            message: 'Need more detail.',
            techniqueId: 'binary_search',
            screen: 'practice',
            difficulty: 'guided',
          },
        ],
        shares: [],
        users: [],
        mistakes: [],
        googleUsers: [
          {
            id: 'owner_1',
            googleId: 'owner_google',
            email: 'owner@example.com',
            name: 'Owner One',
            role: 'owner',
            token: 'owner_token',
            createdAt: olderIso,
            lastSeenAt: recentIso,
          },
          {
            id: 'teacher_1',
            googleId: 'teacher_google',
            email: 'teacher@example.com',
            name: 'Teacher One',
            role: 'teacher',
            token: 'teacher_token',
            createdAt: olderIso,
            lastSeenAt: recentIso,
          },
          {
            id: 'student_1',
            googleId: 'student_google',
            email: 'student@example.com',
            name: 'Student One',
            role: 'student',
            token: 'student_token',
            createdAt: olderIso,
            lastSeenAt: recentIso,
          },
        ],
        classes: [
          {
            id: 'class_1',
            name: 'Class A',
            description: '',
            joinCode: 'ABC123',
            teacherId: 'teacher_1',
            createdAt: recentIso,
            updatedAt: recentIso,
          },
        ],
        enrollments: [
          {
            id: 'enrollment_1',
            classId: 'class_1',
            studentId: 'student_1',
            joinedAt: recentIso,
          },
        ],
        teacherNotes: [],
        classResources: [],
        auditLogs: [
          {
            id: 'audit_1',
            action: 'user.role_changed',
            actorId: 'owner_1',
            actorName: 'Owner One',
            targetId: 'teacher_1',
            targetName: 'teacher@example.com',
            details: { role: 'teacher' },
            createdAt: recentIso,
          },
        ],
      };
      fs.writeFileSync(server.dataFile, JSON.stringify(seedData, null, 2));

      const analyticsResponse = await fetch(`${server.baseUrl}/api/admin/analytics`, {
        headers: { Authorization: 'Bearer owner_token' },
      });
      assert.equal(analyticsResponse.status, 200);
      const analyticsBody = await analyticsResponse.json();
      assert.equal(analyticsBody.data.analytics.summary.totalUsers, 3);
      assert.equal(analyticsBody.data.analytics.summary.activeTeachers, 1);
      assert.equal(analyticsBody.data.analytics.summary.activeClasses, 1);
      assert.equal(analyticsBody.data.analytics.mostUsedTopics[0].techniqueId, 'binary_search');

      const auditResponse = await fetch(`${server.baseUrl}/api/admin/audit-logs`, {
        headers: { Authorization: 'Bearer owner_token' },
      });
      assert.equal(auditResponse.status, 200);
      const auditBody = await auditResponse.json();
      assert.equal(auditBody.data.auditLogs.length, 1);
      assert.equal(auditBody.data.auditLogs[0].action, 'user.role_changed');

      const backupResponse = await fetch(`${server.baseUrl}/api/admin/backup`, {
        headers: { Authorization: 'Bearer owner_token' },
      });
      assert.equal(backupResponse.status, 200);
      const backupBody = await backupResponse.json();
      assert.equal(backupBody.data.backup.backupData.googleUsers.length, 3);

      const restoreResponse = await fetch(`${server.baseUrl}/api/admin/restore`, {
        method: 'POST',
        headers: {
          Authorization: 'Bearer owner_token',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          backupData: {
            ...seedData,
            googleUsers: seedData.googleUsers.slice(0, 2),
          },
        }),
      });
      assert.equal(restoreResponse.status, 200);
    } finally {
      await server.close();
    }
  });

  await run('teacher feedback inbox lists class-scoped student feedback and supports review', async () => {
    const server = await createTestServer();
    try {
      const seedData = {
        sessions: [],
        practiceSessions: [],
        preferences: {},
        progressEntries: [],
        feedbackEntries: [
          {
            id: 'feedback_1',
            userId: 'student_1',
            type: 'content',
            message: 'Binary search explanation is confusing.',
            techniqueId: 'binary_search',
            screen: 'technique_detail',
            difficulty: 'guided',
            createdAt: '2026-04-27T08:30:00.000Z',
          },
          {
            id: 'feedback_2',
            userId: 'student_2',
            type: 'bug',
            message: 'Tree quiz crashed once.',
            techniqueId: 'trees',
            screen: 'practice',
            difficulty: 'expert',
            createdAt: '2026-04-27T09:00:00.000Z',
          },
        ],
        shares: [],
        users: [],
        mistakes: [],
        googleUsers: [
          {
            id: 'teacher_1',
            googleId: 'teacher_google',
            email: 'teacher@example.com',
            name: 'Teacher One',
            role: 'teacher',
            token: 'teacher_token',
            createdAt: '2026-04-26T08:00:00.000Z',
            lastSeenAt: '2026-04-26T08:00:00.000Z',
          },
          {
            id: 'student_1',
            googleId: 'student_google',
            email: 'student1@example.com',
            name: 'Student One',
            role: 'student',
            token: 'student1_token',
            createdAt: '2026-04-26T08:00:00.000Z',
            lastSeenAt: '2026-04-26T08:00:00.000Z',
          },
          {
            id: 'student_2',
            googleId: 'student_two_google',
            email: 'student2@example.com',
            name: 'Student Two',
            role: 'student',
            token: 'student2_token',
            createdAt: '2026-04-26T08:00:00.000Z',
            lastSeenAt: '2026-04-26T08:00:00.000Z',
          },
        ],
        classes: [
          {
            id: 'class_1',
            name: 'Batch A',
            description: '',
            joinCode: 'ABC123',
            teacherId: 'teacher_1',
            createdAt: '2026-04-26T08:00:00.000Z',
            updatedAt: '2026-04-26T08:00:00.000Z',
          },
        ],
        enrollments: [
          {
            id: 'enrollment_1',
            classId: 'class_1',
            studentId: 'student_1',
            joinedAt: '2026-04-26T09:00:00.000Z',
          },
        ],
        teacherNotes: [],
        classResources: [],
        auditLogs: [],
      };
      fs.writeFileSync(server.dataFile, JSON.stringify(seedData, null, 2));

      const listResponse = await fetch(`${server.baseUrl}/api/teacher/feedback?classId=class_1&type=content&techniqueId=binary_search`, {
        headers: { Authorization: 'Bearer teacher_token' },
      });
      assert.equal(listResponse.status, 200);
      const listBody = await listResponse.json();
      assert.equal(listBody.data.feedback.length, 1);
      assert.equal(listBody.data.feedback[0].className, 'Batch A');
      assert.equal(listBody.data.feedback[0].userEmail, 'student1@example.com');
      assert.equal(listBody.data.feedback[0].reviewedAt, '');

      const reviewResponse = await fetch(`${server.baseUrl}/api/teacher/feedback/feedback_1/review`, {
        method: 'PATCH',
        headers: {
          Authorization: 'Bearer teacher_token',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ reviewed: true }),
      });
      assert.equal(reviewResponse.status, 200);
      const reviewBody = await reviewResponse.json();
      assert.equal(reviewBody.data.feedback.length, 1);
      assert.equal(reviewBody.data.feedback[0].reviewedBy, 'teacher_1');
      assert.ok(reviewBody.data.feedback[0].reviewedAt);

      const forbiddenResponse = await fetch(`${server.baseUrl}/api/teacher/feedback`, {
        headers: { Authorization: 'Bearer student1_token' },
      });
      assert.equal(forbiddenResponse.status, 403);
    } finally {
      await server.close();
    }
  });

  await run('status endpoint returns running state', async () => {
    const server = await createTestServer();
    try {
      const response = await fetch(`${server.baseUrl}/status`);
      assert.equal(response.status, 200);
      const body = await response.json();
      assert.equal(body.data.status, 'Server is running');
      assert.ok(body.data.timestamp);
    } finally {
      await server.close();
    }
  });

  await run('teacher class endpoints create classes, join students, and expose student progress', async () => {
    const server = await createTestServer();
    try {
      const seedData = {
        sessions: [],
        practiceSessions: [
          {
            id: 'session_1',
            userId: 'student_1',
            techniqueId: 'binary_search',
            difficulty: 'guided',
            score: 6,
            durationMs: 18000,
            createdAt: '2026-04-26T10:00:00.000Z',
          },
        ],
        preferences: {},
        progressEntries: [
          {
            userId: 'student_1',
            techniqueId: 'binary_search',
            attempts: 2,
            bestScore: 6,
            completed: true,
            lastDifficulty: 'guided',
            masteryLevel: 'silver',
            lastPlayedAt: '2026-04-26T10:00:00.000Z',
            updatedAt: '2026-04-26T10:00:00.000Z',
          },
        ],
        feedbackEntries: [],
        shares: [],
        users: [],
        mistakes: [],
        googleUsers: [
          {
            id: 'teacher_1',
            googleId: 'teacher_google',
            email: 'teacher@example.com',
            name: 'Teacher One',
            role: 'teacher',
            token: 'teacher_token',
            createdAt: '2026-04-26T08:00:00.000Z',
            lastSeenAt: '2026-04-26T08:00:00.000Z',
          },
          {
            id: 'student_1',
            googleId: 'student_google',
            email: 'student@example.com',
            name: 'Student One',
            role: 'student',
            token: 'student_token',
            createdAt: '2026-04-26T08:15:00.000Z',
            lastSeenAt: '2026-04-26T08:15:00.000Z',
          },
        ],
        classes: [],
        enrollments: [],
        teacherNotes: [],
        classResources: [],
      };
      fs.writeFileSync(server.dataFile, JSON.stringify(seedData, null, 2));

      const createResponse = await fetch(`${server.baseUrl}/api/classes`, {
        method: 'POST',
        headers: {
          Authorization: 'Bearer teacher_token',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ name: 'DSA Batch A', description: 'Evening learners' }),
      });
      assert.equal(createResponse.status, 201);
      const createBody = await createResponse.json();
      assert.equal(createBody.data.classroom.name, 'DSA Batch A');
      assert.ok(createBody.data.classroom.joinCode);

      const classId = createBody.data.classroom.id;
      const joinCode = createBody.data.classroom.joinCode;

      const joinResponse = await fetch(`${server.baseUrl}/api/classes/join`, {
        method: 'POST',
        headers: {
          Authorization: 'Bearer student_token',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ joinCode }),
      });
      assert.equal(joinResponse.status, 200);
      const joinBody = await joinResponse.json();
      assert.equal(joinBody.data.classroom.id, classId);

      const teacherClassesResponse = await fetch(`${server.baseUrl}/api/classes`, {
        headers: { Authorization: 'Bearer teacher_token' },
      });
      assert.equal(teacherClassesResponse.status, 200);
      const teacherClassesBody = await teacherClassesResponse.json();
      assert.equal(teacherClassesBody.data.classes.length, 1);
      assert.equal(teacherClassesBody.data.classes[0].studentCount, 1);

      const studentClassesResponse = await fetch(`${server.baseUrl}/api/classes`, {
        headers: { Authorization: 'Bearer student_token' },
      });
      assert.equal(studentClassesResponse.status, 200);
      const studentClassesBody = await studentClassesResponse.json();
      assert.equal(studentClassesBody.data.classes.length, 1);
      assert.equal(studentClassesBody.data.classes[0].teacherName, 'Teacher One');

      const studentsResponse = await fetch(`${server.baseUrl}/api/classes/${classId}/students`, {
        headers: { Authorization: 'Bearer teacher_token' },
      });
      assert.equal(studentsResponse.status, 200);
      const studentsBody = await studentsResponse.json();
      assert.equal(studentsBody.data.students.length, 1);
      assert.equal(studentsBody.data.students[0].user.email, 'student@example.com');
      assert.equal(studentsBody.data.students[0].summary.totalPracticeSessions, 1);
      assert.equal(studentsBody.data.students[0].weakTopics[0].techniqueId, 'binary_search');
      assert.ok(Array.isArray(studentsBody.data.classroom.weakTopicSummary));

      const detailResponse = await fetch(`${server.baseUrl}/api/classes/${classId}/students/student_1`, {
        headers: { Authorization: 'Bearer teacher_token' },
      });
      assert.equal(detailResponse.status, 200);
      const detailBody = await detailResponse.json();
      assert.equal(detailBody.data.student.user.email, 'student@example.com');
      assert.equal(detailBody.data.student.progressEntries.length, 1);

      const noteResponse = await fetch(`${server.baseUrl}/api/classes/${classId}/students/student_1/feedback`, {
        method: 'POST',
        headers: {
          Authorization: 'Bearer teacher_token',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          message: 'Focus on binary search midpoint updates.',
          status: 'needs_practice',
        }),
      });
      assert.equal(noteResponse.status, 201);
      const noteBody = await noteResponse.json();
      assert.equal(noteBody.data.note.status, 'needs_practice');

      const detailAfterNoteResponse = await fetch(`${server.baseUrl}/api/classes/${classId}/students/student_1`, {
        headers: { Authorization: 'Bearer teacher_token' },
      });
      const detailAfterNoteBody = await detailAfterNoteResponse.json();
      assert.equal(detailAfterNoteBody.data.student.teacherNotes.length, 1);
      assert.equal(detailAfterNoteBody.data.student.teacherNotes[0].message, 'Focus on binary search midpoint updates.');

      const uploadResponse = await fetch(`${server.baseUrl}/api/classes/${classId}/resources`, {
        method: 'POST',
        headers: {
          Authorization: 'Bearer teacher_token',
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          title: 'Binary Search Notes',
          type: 'notes',
          fileName: 'binary-search.txt',
          mimeType: 'text/plain',
          base64Data: Buffer.from('hello class').toString('base64'),
        }),
      });
      assert.equal(uploadResponse.status, 201);
      const uploadBody = await uploadResponse.json();
      assert.equal(uploadBody.data.resource.title, 'Binary Search Notes');

      const resourceListResponse = await fetch(`${server.baseUrl}/api/classes/${classId}/resources`, {
        headers: { Authorization: 'Bearer student_token' },
      });
      assert.equal(resourceListResponse.status, 200);
      const resourceListBody = await resourceListResponse.json();
      assert.equal(resourceListBody.data.resources.length, 1);
      assert.equal(resourceListBody.data.resources[0].mimeType, 'text/plain');
    } finally {
      await server.close();
    }
  });

  await run('save-session rejects invalid payload and persists valid session', async () => {
    const server = await createTestServer();
    try {
      const invalidResponse = await fetch(`${server.baseUrl}/save-session`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({}),
      });
      assert.equal(invalidResponse.status, 400);
      const invalidBody = await invalidResponse.json();
      assert.equal(invalidBody.error.code, 'VALIDATION_ERROR');

      const validResponse = await fetch(`${server.baseUrl}/save-session`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ session: { algorithm: 'linear_search', score: 3 } }),
      });
      assert.equal(validResponse.status, 200);
      const validBody = await validResponse.json();
      assert.equal(validBody.data.message, 'Session saved successfully');

      const sessionsResponse = await fetch(`${server.baseUrl}/sessions`);
      const sessionsBody = await sessionsResponse.json();
      const sessions = sessionsBody.data.sessions;
      assert.equal(sessions.length, 1);
      assert.equal(sessions[0].algorithm, 'linear_search');
      assert.equal(sessions[0].score, 3);
      assert.ok(typeof sessions[0].id === 'number');
    } finally {
      await server.close();
    }
  });

  await run('save-preferences persists and loads preferences', async () => {
    const server = await createTestServer();
    try {
      const saveResponse = await fetch(`${server.baseUrl}/save-preferences`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ preferences: { theme: 'dark', difficulty: 'guided' } }),
      });
      assert.equal(saveResponse.status, 200);
      const saveBody = await saveResponse.json();
      assert.equal(saveBody.data.message, 'Preferences saved successfully');

      const preferencesResponse = await fetch(`${server.baseUrl}/preferences`);
      assert.equal(preferencesResponse.status, 200);
      const preferencesBody = await preferencesResponse.json();
      const preferences = preferencesBody.data.preferences;
      assert.deepEqual(preferences, { theme: 'dark', difficulty: 'guided' });
    } finally {
      await server.close();
    }
  });

  await run('share-visualization creates and loads a shared state', async () => {
    const server = await createTestServer();
    try {
      const invalidResponse = await fetch(`${server.baseUrl}/share-visualization`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ share: {} }),
      });
      assert.equal(invalidResponse.status, 400);

      const createResponse = await fetch(`${server.baseUrl}/share-visualization`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          share: {
            techniqueId: 'selection_sort',
            techniqueTitle: 'Selection Sort',
            categoryId: 'sorting',
            currentStep: 3,
          },
        }),
      });
      assert.equal(createResponse.status, 200);
      const createBody = await createResponse.json();
      assert.ok(createBody.data.shareId);

      const loadResponse = await fetch(`${server.baseUrl}/shared/${createBody.data.shareId}`);
      assert.equal(loadResponse.status, 200);
      const loadBody = await loadResponse.json();
      assert.equal(loadBody.data.share.techniqueId, 'selection_sort');
      assert.equal(loadBody.data.share.currentStep, 3);

      const missingResponse = await fetch(`${server.baseUrl}/shared/missing_share`);
      assert.equal(missingResponse.status, 404);
      const missingBody = await missingResponse.json();
      assert.equal(missingBody.error.code, 'NOT_FOUND');
    } finally {
      await server.close();
    }
  });

  await run('admin stats and export support owner and teacher dashboard roles', async () => {
    const server = await createTestServer();
    try {
      const seedData = {
        sessions: [],
        practiceSessions: [
          {
            id: 'session_1',
            userId: 'student_1',
            techniqueId: 'linear_search',
            score: 8,
            difficulty: 'guided',
            durationMs: 30000,
            createdAt: new Date().toISOString(),
          },
          {
            id: 'session_2',
            userId: 'student_2',
            techniqueId: 'stack',
            score: 5,
            difficulty: 'guided',
            durationMs: 25000,
            createdAt: new Date().toISOString(),
          },
        ],
        preferences: {},
        progressEntries: [
          { userId: 'student_1', techniqueId: 'linear_search', attempts: 2, bestScore: 8, completed: true },
          { userId: 'student_2', techniqueId: 'stack', attempts: 1, bestScore: 5, completed: false },
        ],
        feedbackEntries: [
          { id: 'feedback_1', userId: 'student_1', type: 'bug', message: 'Need clearer hint', createdAt: new Date().toISOString() },
        ],
        shares: [],
        users: [],
        mistakes: [],
        googleUsers: [
          {
            id: 'owner_1',
            googleId: 'owner_google',
            email: 'owner@example.com',
            name: 'Owner',
            role: 'owner',
            token: 'owner_token',
            createdAt: new Date().toISOString(),
            lastSeenAt: new Date().toISOString(),
          },
          {
            id: 'teacher_1',
            googleId: 'teacher_google',
            email: 'teacher@example.com',
            name: 'Teacher',
            role: 'teacher',
            token: 'teacher_token',
            createdAt: new Date().toISOString(),
            lastSeenAt: new Date().toISOString(),
          },
          {
            id: 'student_1',
            googleId: 'student_google',
            email: 'student@example.com',
            name: 'Student',
            role: 'student',
            token: 'student_token',
            createdAt: new Date().toISOString(),
            lastSeenAt: new Date().toISOString(),
          },
          {
            id: 'student_2',
            googleId: 'student2_google',
            email: 'student2@example.com',
            name: 'Other Student',
            role: 'student',
            token: 'student2_token',
            createdAt: new Date().toISOString(),
            lastSeenAt: new Date().toISOString(),
          },
        ],
        classes: [
          {
            id: 'class_1',
            teacherId: 'teacher_1',
            name: 'Teacher Class',
            description: '',
            joinCode: 'ABC123',
            createdAt: new Date().toISOString(),
            updatedAt: new Date().toISOString(),
          },
          {
            id: 'class_2',
            teacherId: 'owner_1',
            name: 'Owner Class',
            description: '',
            joinCode: 'XYZ789',
            createdAt: new Date().toISOString(),
            updatedAt: new Date().toISOString(),
          },
        ],
        enrollments: [
          { id: 'enrollment_1', classId: 'class_1', studentId: 'student_1', joinedAt: new Date().toISOString() },
          { id: 'enrollment_2', classId: 'class_2', studentId: 'student_2', joinedAt: new Date().toISOString() },
        ],
        teacherNotes: [],
        classResources: [],
        auditLogs: [],
      };
      fs.writeFileSync(server.dataFile, JSON.stringify(seedData, null, 2));

      const ownerStatsResponse = await fetch(`${server.baseUrl}/api/admin/stats`, {
        headers: { Authorization: 'Bearer owner_token' },
      });
      assert.equal(ownerStatsResponse.status, 200);
      const ownerStats = await ownerStatsResponse.json();
      assert.equal(ownerStats.data.stats.totalUsers, 4);
      assert.equal(ownerStats.data.stats.totalClasses, 2);

      const teacherStatsResponse = await fetch(`${server.baseUrl}/api/admin/stats`, {
        headers: { Authorization: 'Bearer teacher_token' },
      });
      assert.equal(teacherStatsResponse.status, 200);
      const teacherStats = await teacherStatsResponse.json();
      assert.equal(teacherStats.data.stats.totalUsers, 2);
      assert.equal(teacherStats.data.stats.totalClasses, 1);

      const ownerClassesResponse = await fetch(`${server.baseUrl}/api/classes`, {
        headers: { Authorization: 'Bearer owner_token' },
      });
      const ownerClasses = await ownerClassesResponse.json();
      assert.equal(ownerClasses.data.classes.length, 2);

      const exportResponse = await fetch(`${server.baseUrl}/api/admin/export`, {
        headers: { Authorization: 'Bearer owner_token' },
      });
      assert.equal(exportResponse.status, 200);
      assert.match(exportResponse.headers.get('content-disposition'), /dsa-export-/);
      const exportBody = await exportResponse.json();
      assert.equal(exportBody.summary.users, 4);

      const forbiddenExportResponse = await fetch(`${server.baseUrl}/api/admin/export`, {
        headers: { Authorization: 'Bearer teacher_token' },
      });
      assert.equal(forbiddenExportResponse.status, 403);
    } finally {
      await server.close();
    }
  });

  if (process.exitCode && process.exitCode !== 0) {
    throw new Error('One or more server tests failed.');
  }
};

runTests()
  .then(() => {
    process.exit(process.exitCode || 0);
  })
  .catch((error) => {
    console.error(error);
    process.exit(1);
  });
