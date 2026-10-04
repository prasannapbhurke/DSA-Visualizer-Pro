const express = require('express');
const {
  errorResponseDto,
  usersResponseDto,
  userDetailResponseDto,
  analyticsResponseDto,
  successResponseDto,
  auditLogsResponseDto,
  parseUpdateUserRoleRequest,
  successMessageResponseDto,
} = require('../dto');
const { sanitizeGoogleUser } = require('../utils/sanitize');
const { rowsToCamel, stripSnakeOnly } = require('../postgresStore');

const sanitizeAuditLog = (log) => ({
  id: log.id,
  action: log.action,
  actorId: log.actorId,
  actorName: log.actorName || '',
  targetId: log.targetId || '',
  targetName: log.targetName || '',
  details: log.details || {},
  createdAt: log.createdAt || '',
});

const createAdminRouter = ({ store, logger = console, requireAuth }) => {
  const router = express.Router();

  router.get('/users', requireAuth, async (req, res) => {
    if (req.authUser.role !== 'owner') {
      return res.status(403).json(errorResponseDto('Only owners can list users', 'FORBIDDEN'));
    }
    try {
      if (store.query) {
        const result = await store.query('SELECT * FROM app_users ORDER BY created_at ASC');
        if (result && result.rows && result.rows.length > 0) {
          return res.json(usersResponseDto(rowsToCamel(result.rows).map((row) => sanitizeGoogleUser({ ...row, provider: 'google' }))));
        }
      }
    } catch (error) {
      if (logger && logger.warn) logger.warn('admin.users_db_query_fallback', { error: error.message });
    }

    try {
      const fs = require('fs');
      const path = require('path');
      const dataFilePath = path.join(__dirname, '../data.json');
      if (fs.existsSync(dataFilePath)) {
        const data = JSON.parse(fs.readFileSync(dataFilePath, 'utf-8'));
        const users = (data.googleUsers || []).map((u) => ({
          id: u.id,
          email: u.email,
          displayName: u.name || u.displayName || u.email,
          role: u.role || 'student',
          createdAt: u.createdAt || new Date().toISOString(),
          lastSeenAt: u.lastSeenAt || new Date().toISOString(),
        }));
        return res.json(usersResponseDto(users));
      }
    } catch (e) {
      if (logger && logger.error) logger.error('admin.users_fallback_failed', { error: e.message });
    }

    return res.status(500).json(errorResponseDto('Failed to list users'));
  });

  router.put('/users/:userId/role', requireAuth, async (req, res) => {
    if (req.authUser.role !== 'owner') {
      return res.status(403).json(errorResponseDto('Only owners can manage roles', 'FORBIDDEN'));
    }
    const parsedRequest = parseUpdateUserRoleRequest(req.body);
    if (!parsedRequest.ok) return res.status(400).json(parsedRequest);
    try {
      const { newRole } = parsedRequest.value;
      const targetUserId = req.params.userId;
      if (targetUserId === req.authUser.id) {
        return res.status(400).json(errorResponseDto('Cannot change own role', 'VALIDATION_ERROR'));
      }

      let updatedInDb = false;
      try {
        if (store.query) {
          const userRes = await store.query('SELECT * FROM app_users WHERE id = $1', [targetUserId]);
          if (userRes.rowCount > 0) {
            await store.query('UPDATE app_users SET role = $1 WHERE id = $2', [newRole, targetUserId]);
            updatedInDb = true;
          }
        }
      } catch (dbErr) {
        if (logger && logger.warn) logger.warn('admin.update_role_db_failed', { error: dbErr.message });
      }

      // Sync data.json as well
      const fs = require('fs');
      const path = require('path');
      const dataFilePath = path.join(__dirname, '../data.json');
      if (fs.existsSync(dataFilePath)) {
        const data = JSON.parse(fs.readFileSync(dataFilePath, 'utf-8'));
        const u = (data.googleUsers || []).find((x) => x.id === targetUserId || x.email === targetUserId);
        if (u) {
          u.role = newRole;
          fs.writeFileSync(dataFilePath, JSON.stringify(data, null, 2));
        }
      }

      return res.json(successMessageResponseDto(`User role updated to ${newRole}`));
    } catch (error) {
      return res.status(500).json(errorResponseDto('Failed to update role'));
    }
  });

  router.get('/admin/users/:userId/detail', requireAuth, async (req, res) => {
    if (req.authUser.role !== 'owner') {
      return res.status(403).json(errorResponseDto('Only owners can view user details', 'FORBIDDEN'));
    }
    try {
      const result = await store.query('SELECT * FROM app_users WHERE id = $1', [req.params.userId]);
      if (result.rowCount === 0) {
        return res.status(404).json(errorResponseDto('User not found', 'NOT_FOUND'));
      }
      const user = sanitizeGoogleUser({ ...rowsToCamel(result.rows)[0], provider: 'google' });

      const progRes = await store.query('SELECT * FROM progress_entries WHERE user_id = $1', [user.id]);
      const sessRes = await store.query('SELECT * FROM practice_sessions WHERE user_id = $1', [user.id]);
      const feedRes = await store.query('SELECT * FROM feedback_entries WHERE user_id = $1', [user.id]);

      res.json(
        userDetailResponseDto({
          user,
          progressEntries: rowsToCamel(progRes.rows).map(stripSnakeOnly),
          practiceSessions: rowsToCamel(sessRes.rows).map(stripSnakeOnly),
          feedbackEntries: rowsToCamel(feedRes.rows).map(stripSnakeOnly),
        })
      );
    } catch (error) {
      res.status(500).json(errorResponseDto('Failed to load user detail'));
    }
  });

  router.get('/admin/analytics', requireAuth, async (req, res) => {
    if (req.authUser.role !== 'owner') {
      return res.status(403).json(errorResponseDto('Only owners can view analytics', 'FORBIDDEN'));
    }
    try {
      const [
        totalUsersRes,
        activeTeachersRes,
        totalClassesRes,
        activeClassesRes,
        totalResourcesRes,
        totalFeedbackRes,
        totalSessionsRes,
        mostUsedRes,
        mostReportedRes,
      ] = await Promise.all([
        store.query('SELECT COUNT(*) FROM app_users'),
        store.query("SELECT COUNT(*) FROM app_users WHERE role = 'teacher' AND last_seen_at >= NOW() - INTERVAL '30 days'"),
        store.query('SELECT COUNT(*) FROM classes'),
        store.query('SELECT COUNT(DISTINCT class_id) FROM enrollments'),
        store.query('SELECT COUNT(*) FROM class_resources'),
        store.query('SELECT COUNT(*) FROM feedback_entries'),
        store.query('SELECT COUNT(*) FROM practice_sessions'),
        store.query(
          'SELECT technique_id, COUNT(*) as uses FROM practice_sessions GROUP BY technique_id ORDER BY uses DESC LIMIT 5'
        ),
        store.query(
          'SELECT technique_id, type, COUNT(*) as count FROM feedback_entries GROUP BY technique_id, type ORDER BY count DESC LIMIT 5'
        ),
      ]);

      res.json(
        analyticsResponseDto({
          summary: {
            totalUsers: parseInt(totalUsersRes.rows[0].count, 10),
            activeTeachers: parseInt(activeTeachersRes.rows[0].count, 10),
            totalClasses: parseInt(totalClassesRes.rows[0].count, 10),
            activeClasses: parseInt(activeClassesRes.rows[0].count, 10),
            totalResources: parseInt(totalResourcesRes.rows[0].count, 10),
            totalFeedback: parseInt(totalFeedbackRes.rows[0].count, 10),
            totalPracticeSessions: parseInt(totalSessionsRes.rows[0].count, 10),
          },
          mostUsedTopics: mostUsedRes.rows.map((r) => ({ techniqueId: r.technique_id, uses: parseInt(r.uses, 10) })),
          mostReportedContentIssues: mostReportedRes.rows.map((r) => ({
            techniqueId: r.technique_id,
            type: r.type,
            count: parseInt(r.count, 10),
          })),
        })
      );
    } catch (error) {
      logger.error('admin.analytics_failed', { error: error.message, userId: req.authUser.id });
      res.status(500).json(errorResponseDto('Failed to load analytics'));
    }
  });

  router.get('/admin/realtime-config', requireAuth, (req, res) => {
    if (!['owner', 'teacher'].includes(req.authUser.role)) {
      return res.status(403).json(errorResponseDto('Only owners or teachers can view config', 'FORBIDDEN'));
    }
    res.json(
      successResponseDto({
        supabaseUrl: process.env.SUPABASE_URL || '',
        supabaseKey: process.env.SUPABASE_PUBLISHABLE_KEY || '',
      })
    );
  });

  router.get('/admin/stats', requireAuth, async (req, res) => {
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
        `),
      ]);
      res.json({
        data: {
          stats: {
            viewerRole: req.authUser.role,
            totalUsers: parseInt(usersRes.rows[0].count, 10),
            totalClasses: parseInt(classesRes.rows[0].count, 10),
            totalSessions: parseInt(sessionsRes.rows[0].count, 10),
            totalProgressEntries: parseInt(progressRes.rows[0].count, 10),
            topAlgorithms: topAlgosRes.rows.map((r) => ({ id: r.id, count: parseInt(r.count, 10) })),
            dailyEngagement: engagementRes.rows.map((r) => ({ date: r.date, count: parseInt(r.count, 10) })),
          },
        },
      });
    } catch (error) {
      logger.error('admin.stats_failed', { error: error.message, userId: req.authUser.id });
      res.status(500).json(errorResponseDto('Failed to load admin stats'));
    }
  });

  router.get('/admin/export', requireAuth, async (req, res) => {
    res.status(501).json(errorResponseDto('Export not supported in SQL mode', 'NOT_IMPLEMENTED'));
  });

  router.get('/admin/audit-logs', requireAuth, async (req, res) => {
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

  router.get('/admin/backup', requireAuth, async (req, res) => {
    res.status(501).json(errorResponseDto('Backup not supported in SQL mode', 'NOT_IMPLEMENTED'));
  });

  router.post('/admin/restore', requireAuth, async (req, res) => {
    res.status(501).json(errorResponseDto('Restore not supported in SQL mode', 'NOT_IMPLEMENTED'));
  });

  return router;
};

module.exports = {
  createAdminRouter,
  sanitizeAuditLog,
};
