const { Pool } = require('pg');
const crypto = require('crypto');
const fs = require('fs');
const path = require('path');

const createPool = (databaseUrl) =>
  new Pool({
    connectionString: databaseUrl,
    ssl: process.env.PGSSLMODE === 'disable' ? false : { rejectUnauthorized: false },
  });

const schemaPath = path.join(__dirname, 'postgres_schema.sql');
const schemaSql = fs.readFileSync(schemaPath, 'utf8');

const rowsToCamel = (rows) =>
  rows.map((row) => ({
    ...row,
    googleId: row.google_id,
    refreshToken: row.refresh_token || '',
    displayName: row.name || row.email || '',
    createdAt: row.created_at ? row.created_at.toISOString() : '',
    lastSeenAt: row.last_seen_at ? row.last_seen_at.toISOString() : '',
    teacherId: row.teacher_id,
    joinCode: row.join_code,
    updatedAt: row.updated_at ? row.updated_at.toISOString() : '',
    classId: row.class_id,
    studentId: row.student_id,
    rollNumber: row.roll_number || '',
    studentBatch: row.student_batch || '',
    joinedAt: row.joined_at ? row.joined_at.toISOString() : '',
    userId: row.user_id,
    techniqueId: row.technique_id,
    bestScore: row.best_score,
    lastDifficulty: row.last_difficulty,
    masteryLevel: row.mastery_level,
    lastPlayedAt: row.last_played_at ? row.last_played_at.toISOString() : '',
    durationMs: row.duration_ms,
    reviewedAt: row.reviewed_at ? row.reviewed_at.toISOString() : '',
    reviewedBy: row.reviewed_by || '',
    teacherName: row.teacher_name,
    teacherId: row.teacher_id,
    fileName: row.file_name,
    mimeType: row.mime_type,
    sizeBytes: row.size_bytes,
    uploadedBy: row.uploaded_by,
    downloadPath: row.download_path,
    actorId: row.actor_id,
    actorName: row.actor_name,
    targetId: row.target_id,
    targetName: row.target_name,
    currentStep: row.current_step,
    techniqueTitle: row.technique_title,
    categoryId: row.category_id,
    correctAnswer: row.correct_answer,
    userAnswer: row.user_answer,
  }));

const stripSnakeOnly = (item) => {
  const copy = { ...item };
  for (const key of Object.keys(copy)) {
    if (key.includes('_')) {
      delete copy[key];
    }
  }
  return copy;
};

const rowToGoogleUser = (row) => {
  if (!row) {
    return null;
  }
  return stripSnakeOnly({ ...rowsToCamel([row])[0], provider: 'google' });
};

const asArray = (value) => (Array.isArray(value) ? value : []);
const asObject = (value) => (value && typeof value === 'object' && !Array.isArray(value) ? value : {});
const asTimestamp = (value) => {
  if (!value) {
    return null;
  }
  const date = new Date(value);
  return Number.isNaN(date.getTime()) ? null : date.toISOString();
};
const asText = (value) => (typeof value === 'string' ? value : '');
const asInt = (value) => (Number.isFinite(Number(value)) ? Math.trunc(Number(value)) : 0);

const createPostgresStore = (databaseUrl) => {
  const pool = createPool(databaseUrl);
  const ready = (async () => {
    await pool.query(schemaSql);
    await pool.query('alter table app_users add column if not exists refresh_token text');
    await pool.query('create index if not exists idx_app_users_token on app_users(token)');
    await pool.query('create index if not exists idx_app_users_refresh_token on app_users(refresh_token)');
    await pool.query("alter table enrollments add column if not exists roll_number text not null default ''");
    await pool.query("alter table enrollments add column if not exists student_batch text not null default ''");
    await pool.query('create index if not exists idx_enrollments_roll_number on enrollments(class_id, roll_number)');
  })();

  const read = async () => {
    await ready;
    const [
      users,
      classes,
      enrollments,
      progressEntries,
      practiceSessions,
      mistakes,
      feedbackEntries,
      teacherNotes,
      classResources,
      auditLogs,
      shares,
      legacySessions,
      preferences,
    ] = await Promise.all([
      pool.query('select * from app_users order by created_at asc'),
      pool.query('select * from classes order by created_at asc'),
      pool.query('select * from enrollments order by joined_at asc'),
      pool.query('select * from progress_entries order by updated_at desc'),
      pool.query('select * from practice_sessions order by created_at desc'),
      pool.query('select * from mistakes order by created_at desc'),
      pool.query('select * from feedback_entries order by created_at desc'),
      pool.query('select * from teacher_notes order by created_at desc'),
      pool.query('select * from class_resources order by created_at desc'),
      pool.query('select * from audit_logs order by created_at desc'),
      pool.query('select * from shared_visualizations order by created_at desc'),
      pool.query('select * from legacy_sessions order by created_at desc'),
      pool.query("select value from app_preferences where key = 'global'"),
    ]);

    return {
      sessions: legacySessions.rows.map((row) => row.payload || { id: row.id }),
      practiceSessions: rowsToCamel(practiceSessions.rows).map(stripSnakeOnly),
      preferences: preferences.rows[0]?.value || {},
      progressEntries: rowsToCamel(progressEntries.rows).map(stripSnakeOnly),
      feedbackEntries: rowsToCamel(feedbackEntries.rows).map(stripSnakeOnly),
      shares: rowsToCamel(shares.rows).map(stripSnakeOnly),
      users: [],
      mistakes: rowsToCamel(mistakes.rows).map((row) => stripSnakeOnly({ ...(row.payload || {}), ...row })),
      googleUsers: rowsToCamel(users.rows).map((row) => stripSnakeOnly({ ...row, provider: 'google' })),
      classes: rowsToCamel(classes.rows).map(stripSnakeOnly),
      enrollments: rowsToCamel(enrollments.rows).map(stripSnakeOnly),
      teacherNotes: rowsToCamel(teacherNotes.rows).map(stripSnakeOnly),
      classResources: rowsToCamel(classResources.rows).map(stripSnakeOnly),
      auditLogs: rowsToCamel(auditLogs.rows).map(stripSnakeOnly),
    };
  };

  const write = async (data) => {
    await ready;
    const client = await pool.connect();
    try {
      await client.query('begin');
      await client.query('delete from legacy_sessions');
      await client.query('delete from app_preferences');
      await client.query('delete from audit_logs');
      await client.query('delete from class_resources');
      await client.query('delete from teacher_notes');
      await client.query('delete from feedback_entries');
      await client.query('delete from mistakes');
      await client.query('delete from practice_sessions');
      await client.query('delete from progress_entries');
      await client.query('delete from enrollments');
      await client.query('delete from shared_visualizations');
      await client.query('delete from classes');
      await client.query('delete from app_users');

      for (const user of asArray(data.googleUsers)) {
        if (!user.id || !user.googleId || !user.email) {
          continue;
        }
        await client.query(
          `insert into app_users (id, google_id, email, name, role, token, refresh_token, created_at, last_seen_at)
           values ($1, $2, $3, $4, $5, $6, $7, coalesce($8::timestamptz, now()), coalesce($9::timestamptz, now()))`,
          [
            user.id,
            user.googleId,
            user.email,
            asText(user.name) || asText(user.displayName) || user.email,
            ['owner', 'teacher', 'student'].includes(user.role) ? user.role : 'student',
            asText(user.token) || null,
            asText(user.refreshToken) || null,
            asTimestamp(user.createdAt),
            asTimestamp(user.lastSeenAt),
          ]
        );
      }

      for (const classroom of asArray(data.classes)) {
        if (!classroom.id || !classroom.teacherId) {
          continue;
        }
        await client.query(
          `insert into classes (id, teacher_id, name, description, join_code, created_at, updated_at)
           values ($1, $2, $3, $4, $5, coalesce($6::timestamptz, now()), coalesce($7::timestamptz, now()))`,
          [
            classroom.id,
            classroom.teacherId,
            asText(classroom.name) || 'Untitled class',
            asText(classroom.description),
            asText(classroom.joinCode),
            asTimestamp(classroom.createdAt),
            asTimestamp(classroom.updatedAt || classroom.createdAt),
          ]
        );
      }

      for (const enrollment of asArray(data.enrollments)) {
        if (!enrollment.id || !enrollment.classId || !enrollment.studentId) {
          continue;
        }
        await client.query(
          `insert into enrollments (id, class_id, student_id, joined_at)
           values ($1, $2, $3, coalesce($4::timestamptz, now()))`,
          [enrollment.id, enrollment.classId, enrollment.studentId, asTimestamp(enrollment.joinedAt)]
        );
      }

      for (const entry of asArray(data.progressEntries)) {
        if (!entry.userId || !entry.techniqueId) {
          continue;
        }
        await client.query(
          `insert into progress_entries
            (user_id, technique_id, attempts, best_score, completed, last_difficulty, mastery_level, last_played_at, updated_at)
           values ($1, $2, $3, $4, $5, $6, $7, $8::timestamptz, coalesce($9::timestamptz, now()))`,
          [
            entry.userId,
            entry.techniqueId,
            asInt(entry.attempts),
            asInt(entry.bestScore),
            Boolean(entry.completed),
            asText(entry.lastDifficulty),
            asText(entry.masteryLevel),
            asTimestamp(entry.lastPlayedAt),
            asTimestamp(entry.updatedAt || entry.lastPlayedAt),
          ]
        );
      }

      for (const session of asArray(data.practiceSessions)) {
        if (!session.id || !session.userId || !session.techniqueId) {
          continue;
        }
        await client.query(
          `insert into practice_sessions (id, user_id, technique_id, score, difficulty, duration_ms, created_at)
           values ($1, $2, $3, $4, $5, $6, coalesce($7::timestamptz, now()))`,
          [session.id, session.userId, session.techniqueId, asInt(session.score), asText(session.difficulty), asInt(session.durationMs), asTimestamp(session.createdAt)]
        );
      }

      for (const mistake of asArray(data.mistakes)) {
        if (!mistake.id || !mistake.userId || !mistake.techniqueId) {
          continue;
        }
        await client.query(
          `insert into mistakes (id, user_id, technique_id, question, correct_answer, user_answer, payload, created_at)
           values ($1, $2, $3, $4, $5, $6, $7, coalesce($8::timestamptz, now()))`,
          [
            mistake.id,
            mistake.userId,
            mistake.techniqueId,
            asText(mistake.question),
            asText(mistake.correctAnswer),
            asText(mistake.userAnswer),
            mistake,
            asTimestamp(mistake.createdAt),
          ]
        );
      }

      for (const feedback of asArray(data.feedbackEntries)) {
        if (!feedback.id || !feedback.userId) {
          continue;
        }
        await client.query(
          `insert into feedback_entries
            (id, user_id, type, message, technique_id, screen, difficulty, reviewed_at, reviewed_by, created_at)
           values ($1, $2, $3, $4, $5, $6, $7, $8::timestamptz, nullif($9, ''), coalesce($10::timestamptz, now()))`,
          [
            feedback.id,
            feedback.userId,
            asText(feedback.type),
            asText(feedback.message),
            asText(feedback.techniqueId),
            asText(feedback.screen),
            asText(feedback.difficulty),
            asTimestamp(feedback.reviewedAt),
            asText(feedback.reviewedBy),
            asTimestamp(feedback.createdAt),
          ]
        );
      }

      for (const note of asArray(data.teacherNotes)) {
        if (!note.id || !note.classId || !note.studentId || !note.teacherId) {
          continue;
        }
        await client.query(
          `insert into teacher_notes (id, class_id, student_id, teacher_id, message, status, created_at)
           values ($1, $2, $3, $4, $5, $6, coalesce($7::timestamptz, now()))`,
          [note.id, note.classId, note.studentId, note.teacherId, asText(note.message), asText(note.status), asTimestamp(note.createdAt)]
        );
      }

      for (const resource of asArray(data.classResources)) {
        if (!resource.id || !resource.classId || !resource.uploadedBy) {
          continue;
        }
        await client.query(
          `insert into class_resources
            (id, class_id, title, type, file_name, mime_type, size_bytes, uploaded_by, download_path, created_at)
           values ($1, $2, $3, $4, $5, $6, $7, $8, $9, coalesce($10::timestamptz, now()))`,
          [
            resource.id,
            resource.classId,
            asText(resource.title),
            asText(resource.type) || 'resource',
            asText(resource.fileName),
            asText(resource.mimeType),
            asInt(resource.sizeBytes),
            resource.uploadedBy,
            asText(resource.downloadPath),
            asTimestamp(resource.createdAt),
          ]
        );
      }

      for (const log of asArray(data.auditLogs)) {
        if (!log.id) {
          continue;
        }
        await client.query(
          `insert into audit_logs (id, action, actor_id, actor_name, target_id, target_name, details, created_at)
           values ($1, $2, nullif($3, ''), $4, $5, $6, $7, coalesce($8::timestamptz, now()))`,
          [
            log.id,
            asText(log.action),
            asText(log.actorId),
            asText(log.actorName),
            asText(log.targetId),
            asText(log.targetName),
            asObject(log.details),
            asTimestamp(log.createdAt),
          ]
        );
      }

      for (const share of asArray(data.shares)) {
        if (!share.id || !share.techniqueId) {
          continue;
        }
        await client.query(
          `insert into shared_visualizations
            (id, technique_id, technique_title, category_id, current_step, note, created_at)
           values ($1, $2, $3, $4, $5, $6, coalesce($7::timestamptz, now()))`,
          [
            share.id,
            share.techniqueId,
            asText(share.techniqueTitle),
            asText(share.categoryId),
            asInt(share.currentStep),
            asText(share.note),
            asTimestamp(share.createdAt),
          ]
        );
      }

      for (const session of asArray(data.sessions)) {
        const id = session.id ? String(session.id) : `legacy_${Date.now()}_${Math.random().toString(36).slice(2)}`;
        await client.query(
          `insert into legacy_sessions (id, payload, created_at) values ($1, $2, now())`,
          [id, session]
        );
      }

      await client.query(
        `insert into app_preferences (key, value, updated_at) values ('global', $1, now())`,
        [asObject(data.preferences)]
      );

      await client.query('commit');
    } catch (error) {
      await client.query('rollback');
      throw error;
    } finally {
      client.release();
    }
  };

  const findUserByToken = async (token) => {
    await ready;
    if (!token) {
      return null;
    }
    const result = await pool.query(
      `update app_users
       set last_seen_at = now()
       where token = $1
       returning *`,
      [token]
    );
    return rowToGoogleUser(result.rows[0]);
  };

  const upsertGoogleUser = async ({ googleId, email, name, token, refreshToken, ownerEmail }) => {
    await ready;
    const defaultRole = String(email || '').toLowerCase() === String(ownerEmail || '').toLowerCase() ? 'owner' : 'student';
    const result = await pool.query(
      `insert into app_users (id, google_id, email, name, role, token, refresh_token, created_at, last_seen_at)
       values ($1, $2, $3, $4, $5, $6, $7, now(), now())
       on conflict (google_id) do update
       set email = excluded.email,
           name = excluded.name,
           role = case
             when lower(excluded.email) = lower($8) then 'owner'
             else app_users.role
           end,
           token = excluded.token,
           refresh_token = excluded.refresh_token,
           last_seen_at = now()
       returning *`,
      [`user_${crypto.randomUUID()}`, googleId, email, name || email, defaultRole, token, refreshToken, ownerEmail]
    );
    return rowToGoogleUser(result.rows[0]);
  };

  const logoutUser = async (userId) => {
    await ready;
    await pool.query('update app_users set token = null, refresh_token = null where id = $1', [userId]);
  };

  const refreshUserSession = async (refreshToken) => {
    await ready;
    if (!refreshToken) {
      return null;
    }
    const nextToken = crypto.randomBytes(32).toString('hex');
    const nextRefreshToken = crypto.randomBytes(32).toString('hex');
    const result = await pool.query(
      `update app_users
       set token = $1,
           refresh_token = $2,
           last_seen_at = now()
       where refresh_token = $3
       returning *`,
      [nextToken, nextRefreshToken, refreshToken]
    );
    return rowToGoogleUser(result.rows[0]);
  };

  const updateUserRole = async ({ targetUserId, newRole, actor, auditId, createdAt }) => {
    await ready;
    const client = await pool.connect();
    try {
      await client.query('begin');
      const result = await client.query(
        'update app_users set role = $1 where id = $2 returning *',
        [newRole, targetUserId]
      );
      const user = rowToGoogleUser(result.rows[0]);
      if (!user) {
        await client.query('rollback');
        return null;
      }
      await client.query(
        `insert into audit_logs (id, action, actor_id, actor_name, target_id, target_name, details, created_at)
         values ($1, 'user.role_changed', $2, $3, $4, $5, $6, $7::timestamptz)`,
        [
          auditId,
          actor?.id || null,
          actor?.name || actor?.displayName || actor?.email || '',
          user.id,
          user.email || user.name || user.id,
          { role: newRole },
          createdAt,
        ]
      );
      await client.query('commit');
      return user;
    } catch (error) {
      await client.query('rollback');
      throw error;
    } finally {
      client.release();
    }
  };

  const deleteUser = async (userId) => {
    await ready;
    const client = await pool.connect();
    try {
      await client.query('begin');
      await client.query('update feedback_entries set reviewed_by = null where reviewed_by = $1', [userId]);
      await client.query('update audit_logs set actor_id = null where actor_id = $1', [userId]);
      const res = await client.query('delete from app_users where id = $1 returning *', [userId]);
      await client.query('commit');
      return res.rowCount > 0;
    } catch (error) {
      await client.query('rollback');
      throw error;
    } finally {
      client.release();
    }
  };

  const deleteUserByEmail = async (email) => {
    await ready;
    const client = await pool.connect();
    try {
      await client.query('begin');
      const userRes = await client.query('select id from app_users where lower(email) = lower($1)', [email]);
      if (userRes.rowCount === 0) {
        await client.query('rollback');
        return false;
      }
      const userId = userRes.rows[0].id;
      await client.query('update feedback_entries set reviewed_by = null where reviewed_by = $1', [userId]);
      await client.query('update audit_logs set actor_id = null where actor_id = $1', [userId]);
      await client.query('delete from app_users where id = $1', [userId]);
      await client.query('commit');
      return true;
    } catch (error) {
      await client.query('rollback');
      throw error;
    } finally {
      client.release();
    }
  };

  return {
    query: (text, params) => pool.query(text, params),
    findUserByToken,
    upsertGoogleUser,
    refreshUserSession,
    logoutUser,
    updateUserRole,
    deleteUser,
    deleteUserByEmail,
    close: () => pool.end(),
  };
};

module.exports = {
  createPostgresStore,
  rowsToCamel,
  stripSnakeOnly,
};


