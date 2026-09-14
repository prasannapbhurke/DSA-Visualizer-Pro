const express = require('express');
const crypto = require('crypto');
const {
  controlCenterOverviewResponseDto,
  controlCenterSettingsResponseDto,
  errorResponseDto,
  liveSessionResponseDto,
  parseLiveSessionBroadcastRequest,
  parseUpdateControlCenterSettingsRequest,
  successResponseDto,
} = require('../dto');
const { rowsToCamel, stripSnakeOnly } = require('../postgresStore');
const { sanitizeGoogleUser } = require('../utils/sanitize');

const DEFAULT_SETTINGS = {
  announcement: '',
  maintenanceMode: false,
  registrationEnabled: true,
  experimentalVisualizers: {
    avlTree: true,
    huffmanCoding: true,
    trie: true,
    dijkstra: true,
    unionFind: true,
  },
};

const createControlCenterRouter = ({ store, logger = console, requireAuth, requireRole }) => {
  const router = express.Router();

  const requireStaff = (req, res, next) => {
    if (!req.authUser) {
      return res.status(401).json(errorResponseDto('Authentication required', 'UNAUTHORIZED'));
    }
    if (!['owner', 'teacher'].includes(req.authUser.role)) {
      return res.status(403).json(errorResponseDto('Access restricted to teachers and administrators', 'FORBIDDEN'));
    }
    next();
  };

  // 1. Unified Overview
  router.get('/control-center/overview', requireAuth, requireStaff, async (req, res) => {
    const isOwner = req.authUser.role === 'owner';
    const userId = req.authUser.id;

    try {
      if (isOwner) {
        const [
          totalUsersRes,
          teachersRes,
          studentsRes,
          totalClassesRes,
          totalSessionsRes,
          totalResourcesRes,
          unreviewedFeedbackRes,
          topAlgosRes,
          recentLogsRes,
          settingsRes,
        ] = await Promise.all([
          store.query('SELECT COUNT(*) FROM app_users'),
          store.query("SELECT COUNT(*) FROM app_users WHERE role = 'teacher'"),
          store.query("SELECT COUNT(*) FROM app_users WHERE role = 'student'"),
          store.query('SELECT COUNT(*) FROM classes'),
          store.query('SELECT COUNT(*) FROM practice_sessions'),
          store.query('SELECT COUNT(*) FROM class_resources'),
          store.query('SELECT COUNT(*) FROM feedback_entries WHERE reviewed_at IS NULL'),
          store.query(`
            SELECT technique_id as id, COUNT(*) as count 
            FROM practice_sessions 
            GROUP BY technique_id 
            ORDER BY count DESC 
            LIMIT 5
          `),
          store.query('SELECT * FROM audit_logs ORDER BY created_at DESC LIMIT 5'),
          store.query("SELECT value FROM app_preferences WHERE key = 'control_center_settings'"),
        ]);

        let settings = DEFAULT_SETTINGS;
        if (settingsRes.rowCount > 0 && settingsRes.rows[0].value) {
          const raw = settingsRes.rows[0].value;
          settings = typeof raw === 'string' ? JSON.parse(raw) : raw;
        }

        const overview = {
          role: 'owner',
          viewer: sanitizeGoogleUser(req.authUser),
          stats: {
            totalUsers: parseInt(totalUsersRes.rows[0]?.count || 0, 10),
            totalTeachers: parseInt(teachersRes.rows[0]?.count || 0, 10),
            totalStudents: parseInt(studentsRes.rows[0]?.count || 0, 10),
            totalClasses: parseInt(totalClassesRes.rows[0]?.count || 0, 10),
            totalSessions: parseInt(totalSessionsRes.rows[0]?.count || 0, 10),
            totalResources: parseInt(totalResourcesRes.rows[0]?.count || 0, 10),
            unreviewedFeedback: parseInt(unreviewedFeedbackRes.rows[0]?.count || 0, 10),
          },
          topAlgorithms: topAlgosRes.rows.map((r) => ({
            id: r.id,
            count: parseInt(r.count, 10),
          })),
          recentAuditLogs: rowsToCamel(recentLogsRes.rows).map(stripSnakeOnly),
          settings,
        };

        return res.json(controlCenterOverviewResponseDto(overview));
      }

      // Teacher Overview
      const [
        myClassesRes,
        enrolledStudentsRes,
        myResourcesRes,
        pendingFeedbackRes,
        classesListRes,
        recentActivityRes,
      ] = await Promise.all([
        store.query('SELECT COUNT(*) FROM classes WHERE teacher_id = $1', [userId]),
        store.query(
          `SELECT COUNT(DISTINCT e.student_id) 
           FROM enrollments e 
           JOIN classes c ON e.class_id = c.id 
           WHERE c.teacher_id = $1`,
          [userId]
        ),
        store.query('SELECT COUNT(*) FROM class_resources WHERE uploaded_by = $1', [userId]),
        store.query(
          `SELECT COUNT(*) 
           FROM feedback_entries f 
           JOIN enrollments e ON f.user_id = e.student_id 
           JOIN classes c ON e.class_id = c.id 
           WHERE c.teacher_id = $1 AND f.reviewed_at IS NULL`,
          [userId]
        ),
        store.query(
          `SELECT c.id, c.name, c.join_code, c.created_at, COUNT(e.student_id) as student_count 
           FROM classes c 
           LEFT JOIN enrollments e ON c.id = e.class_id 
           WHERE c.teacher_id = $1 
           GROUP BY c.id 
           ORDER BY c.created_at DESC`,
          [userId]
        ),
        store.query(
          `SELECT ps.technique_id, ps.score, ps.created_at, u.name as student_name, u.email as student_email 
           FROM practice_sessions ps 
           JOIN enrollments e ON ps.user_id = e.student_id 
           JOIN classes c ON e.class_id = c.id 
           JOIN app_users u ON ps.user_id = u.id 
           WHERE c.teacher_id = $1 
           ORDER BY ps.created_at DESC 
           LIMIT 6`,
          [userId]
        ),
      ]);

      const overview = {
        role: 'teacher',
        viewer: sanitizeGoogleUser(req.authUser),
        stats: {
          totalClasses: parseInt(myClassesRes.rows[0]?.count || 0, 10),
          totalStudents: parseInt(enrolledStudentsRes.rows[0]?.count || 0, 10),
          totalResources: parseInt(myResourcesRes.rows[0]?.count || 0, 10),
          pendingFeedback: parseInt(pendingFeedbackRes.rows[0]?.count || 0, 10),
        },
        classes: classesListRes.rows.map((row) => ({
          id: row.id,
          name: row.name,
          joinCode: row.join_code,
          createdAt: row.created_at,
          studentCount: parseInt(row.student_count || 0, 10),
        })),
        recentStudentActivity: recentActivityRes.rows.map((row) => ({
          techniqueId: row.technique_id,
          score: row.score,
          createdAt: row.created_at,
          studentName: row.student_name || row.student_email,
        })),
      };

      return res.json(controlCenterOverviewResponseDto(overview));
    } catch (error) {
      if (logger && typeof logger.error === 'function') {
        logger.error('control_center.overview_failed', { error: error.message, userId });
      }
      return res.status(500).json(errorResponseDto('Failed to generate control center overview'));
    }
  });

  // 2. Platform Feature Flags & Settings (Owner only)
  router.get('/control-center/settings', requireAuth, async (req, res) => {
    if (req.authUser.role !== 'owner') {
      return res.status(403).json(errorResponseDto('Only owners can inspect control center settings', 'FORBIDDEN'));
    }

    try {
      const result = await store.query("SELECT value FROM app_preferences WHERE key = 'control_center_settings'");
      let settings = DEFAULT_SETTINGS;
      if (result.rowCount > 0 && result.rows[0].value) {
        const raw = result.rows[0].value;
        settings = typeof raw === 'string' ? JSON.parse(raw) : raw;
      }
      return res.json(controlCenterSettingsResponseDto(settings));
    } catch (error) {
      return res.status(500).json(errorResponseDto('Failed to load settings'));
    }
  });

  router.put('/control-center/settings', requireAuth, async (req, res) => {
    if (req.authUser.role !== 'owner') {
      return res.status(403).json(errorResponseDto('Only owners can modify control center settings', 'FORBIDDEN'));
    }

    const parsed = parseUpdateControlCenterSettingsRequest(req.body);
    if (!parsed.ok) {
      return res.status(400).json(parsed);
    }

    try {
      const { settings } = parsed.value;
      const serialized = JSON.stringify(settings);

      await store.query(
        `INSERT INTO app_preferences (key, value, updated_at) 
         VALUES ('control_center_settings', $1::jsonb, NOW()) 
         ON CONFLICT (key) DO UPDATE SET value = $1::jsonb, updated_at = NOW()`,
        [serialized]
      );

      // Audit log entry
      try {
        await store.query(
          `INSERT INTO audit_logs (id, action, actor_id, actor_name, details, created_at) 
           VALUES ($1, $2, $3, $4, $5::jsonb, NOW())`,
          [
            `audit_${crypto.randomUUID()}`,
            'update_control_center_settings',
            req.authUser.id,
            req.authUser.name || req.authUser.email,
            serialized,
          ]
        );
      } catch (auditErr) {
        if (logger && typeof logger.error === 'function') {
          logger.error('control_center.audit_log_failed', { error: auditErr.message });
        }
      }

      return res.json(controlCenterSettingsResponseDto(settings));
    } catch (error) {
      return res.status(500).json(errorResponseDto('Failed to save settings'));
    }
  });

  // 3. User & Student Roster
  router.get('/control-center/roster', requireAuth, requireStaff, async (req, res) => {
    const isOwner = req.authUser.role === 'owner';
    const userId = req.authUser.id;

    try {
      if (isOwner) {
        const result = await store.query(
          'SELECT id, google_id, email, name, role, created_at, last_seen_at FROM app_users ORDER BY created_at DESC LIMIT 150'
        );
        const users = rowsToCamel(result.rows).map((u) => sanitizeGoogleUser({ ...u, provider: 'google' }));
        return res.json(successResponseDto({ roster: users }));
      }

      // Teacher: only students in their classes
      const result = await store.query(
        `SELECT DISTINCT u.id, u.google_id, u.email, u.name, u.role, u.last_seen_at, 
                e.class_id, c.name as class_name, e.joined_at 
         FROM app_users u 
         JOIN enrollments e ON u.id = e.student_id 
         JOIN classes c ON e.class_id = c.id 
         WHERE c.teacher_id = $1 
         ORDER BY u.name ASC`,
        [userId]
      );

      const students = rowsToCamel(result.rows).map((row) => ({
        id: row.id,
        email: row.email,
        displayName: row.name || row.email,
        role: row.role,
        classId: row.classId,
        className: row.className,
        joinedAt: row.joinedAt,
        lastSeenAt: row.lastSeenAt,
      }));

      return res.json(successResponseDto({ roster: students }));
    } catch (error) {
      return res.status(500).json(errorResponseDto('Failed to load roster'));
    }
  });

  // 4. Live Classroom Broadcast Session ("Follow-the-Teacher")
  router.post('/control-center/live-session', requireAuth, requireStaff, async (req, res) => {
    const parsed = parseLiveSessionBroadcastRequest(req.body);
    if (!parsed.ok) {
      return res.status(400).json(parsed);
    }

    const { session } = parsed.value;
    const userId = req.authUser.id;

    try {
      // If teacher, confirm ownership of class
      if (req.authUser.role !== 'owner') {
        const classCheck = await store.query('SELECT teacher_id FROM classes WHERE id = $1', [session.classId]);
        if (classCheck.rowCount === 0) {
          return res.status(404).json(errorResponseDto('Class not found', 'NOT_FOUND'));
        }
        if (classCheck.rows[0].teacher_id !== userId) {
          return res.status(403).json(errorResponseDto('You do not teach this class', 'FORBIDDEN'));
        }
      }

      const sessionPayload = {
        ...session,
        broadcastBy: userId,
        broadcastByName: req.authUser.name || req.authUser.email,
        updatedAt: new Date().toISOString(),
      };

      const key = `live_session_${session.classId}`;
      await store.query(
        `INSERT INTO app_preferences (key, value, updated_at) 
         VALUES ($1, $2::jsonb, NOW()) 
         ON CONFLICT (key) DO UPDATE SET value = $2::jsonb, updated_at = NOW()`,
        [key, JSON.stringify(sessionPayload)]
      );

      return res.status(201).json(liveSessionResponseDto(sessionPayload));
    } catch (error) {
      return res.status(500).json(errorResponseDto('Failed to publish live session state'));
    }
  });

  router.get('/control-center/live-session/:classId', requireAuth, async (req, res) => {
    const classId = req.params.classId;
    const userId = req.authUser.id;

    try {
      // Validate access (must be enrolled or be the teacher/owner)
      if (req.authUser.role !== 'owner') {
        const membership = await store.query(
          `SELECT 1 FROM classes WHERE id = $1 AND teacher_id = $2 
           UNION 
           SELECT 1 FROM enrollments WHERE class_id = $1 AND student_id = $2`,
          [classId, userId]
        );
        if (membership.rowCount === 0) {
          return res.status(403).json(errorResponseDto('You are not a member of this class', 'FORBIDDEN'));
        }
      }

      const key = `live_session_${classId}`;
      const result = await store.query('SELECT value, updated_at FROM app_preferences WHERE key = $1', [key]);

      if (result.rowCount === 0 || !result.rows[0].value) {
        return res.json(liveSessionResponseDto(null));
      }

      const raw = result.rows[0].value;
      const session = typeof raw === 'string' ? JSON.parse(raw) : raw;
      return res.json(liveSessionResponseDto(session));
    } catch (error) {
      return res.status(500).json(errorResponseDto('Failed to load live session'));
    }
  });

  return router;
};

module.exports = {
  createControlCenterRouter,
  DEFAULT_SETTINGS,
};
