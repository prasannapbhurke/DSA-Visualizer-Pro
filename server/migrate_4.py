import re

with open('index.js', 'r', encoding='utf-8') as f:
    content = f.read()

# 1. GET /api/admin/dashboard
admin_dash_orig = '''      const data = await store.read();
      res.json(analyticsResponseDto(buildOwnerAnalytics(data)));'''
admin_dash_new = '''      const [
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
      }));'''
content = content.replace(admin_dash_orig, admin_dash_new)

# 2. GET /api/admin/stats
admin_stats_orig = '''      const data = await store.read();
      res.json({ data: { stats: calculateAdminStats({ data, viewer: req.authUser }) } });'''
admin_stats_new = '''      // Simple placeholder for admin stats
      const [usersRes, classesRes, sessionsRes, progressRes] = await Promise.all([
        store.query('SELECT COUNT(*) FROM app_users'),
        store.query('SELECT COUNT(*) FROM classes'),
        store.query('SELECT COUNT(*) FROM practice_sessions'),
        store.query('SELECT COUNT(*) FROM progress_entries')
      ]);
      res.json({ data: { stats: {
        viewerRole: req.authUser.role,
        totalUsers: parseInt(usersRes.rows[0].count, 10),
        totalClasses: parseInt(classesRes.rows[0].count, 10),
        totalSessions: parseInt(sessionsRes.rows[0].count, 10),
        totalProgressEntries: parseInt(progressRes.rows[0].count, 10)
      } } });'''
content = content.replace(admin_stats_orig, admin_stats_new)

# 3. GET /api/admin/export
admin_export_orig = '''      const data = await store.read();
      res.setHeader('Content-Type', 'application/json');
      res.setHeader('Content-Disposition', `attachment; filename=dsa-export-${Date.now()}.json`);
      res.send(JSON.stringify(data, null, 2));'''
admin_export_new = '''      res.status(501).json(errorResponseDto('Export not supported in SQL mode', 'NOT_IMPLEMENTED'));'''
content = content.replace(admin_export_orig, admin_export_new)

# 4. GET /api/admin/audit-logs
audit_orig = '''      const data = await store.read();
      const auditLogs = [...(data.auditLogs || [])]
        .sort((left, right) => String(right.createdAt || '').localeCompare(String(left.createdAt || '')))
        .map(sanitizeAuditLog);
      res.json(auditLogsResponseDto(auditLogs));'''
audit_new = '''      const result = await store.query('SELECT * FROM audit_logs ORDER BY created_at DESC');
      const auditLogs = rowsToCamel(result.rows).map(stripSnakeOnly).map(sanitizeAuditLog);
      res.json(auditLogsResponseDto(auditLogs));'''
content = content.replace(audit_orig, audit_new)

# 5. GET /api/admin/backup
backup_orig = '''      const data = await store.read();
      res.json(backupResponseDto({
        generatedAt: new Date().toISOString(),
        data,
      }));'''
backup_new = '''      res.status(501).json(errorResponseDto('Backup not supported in SQL mode via this endpoint', 'NOT_IMPLEMENTED'));'''
content = content.replace(backup_orig, backup_new)

# 6. POST /api/admin/restore
restore_orig = '''      const current = await store.read();
      const restored = {
        ...DEFAULT_DATA,
        googleUsers: current.googleUsers || [],
        users: current.users || [],
        ...parsedRequest.value.data,
      };

      if (!Array.isArray(restored.auditLogs)) {
        restored.auditLogs = [];
      }
      restored.auditLogs.push({
        id: `audit_${crypto.randomUUID()}`,
        action: 'system.restore',
        actorId: req.authUser.id,
        actorName: req.authUser.displayName || req.authUser.name || req.authUser.email || '',
        targetId: 'system',
        targetName: 'database',
        createdAt: new Date().toISOString(),
        details: {
          timestamp: new Date().toISOString(),
        },
      });
      await store.write(restored);
      res.json(successMessageResponseDto('Backup restored successfully'));'''
restore_new = '''      res.status(501).json(errorResponseDto('Restore not supported in SQL mode via this endpoint', 'NOT_IMPLEMENTED'));'''
content = content.replace(restore_orig, restore_new)

with open('index.js', 'w', encoding='utf-8') as f:
    f.write(content)

print("Pass 4 completed!")
