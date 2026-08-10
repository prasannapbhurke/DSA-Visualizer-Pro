const fs = require('fs');
const path = require('path');
const { Pool } = require('pg');

const databaseUrl = process.env.DATABASE_URL;
const dataFile = process.env.DATA_FILE || path.join(__dirname, '..', 'data.json');

if (!databaseUrl) {
  console.error('DATABASE_URL is required.');
  process.exit(1);
}

const pool = new Pool({
  connectionString: databaseUrl,
  ssl: process.env.PGSSLMODE === 'disable' ? false : { rejectUnauthorized: false },
});

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

const insertAuditLog = async (client, log) => {
  await client.query(
    `insert into audit_logs (id, action, actor_id, actor_name, target_id, target_name, details, created_at)
     values ($1, $2, $3, $4, $5, $6, $7, coalesce($8::timestamptz, now()))
     on conflict (id) do update set
       action = excluded.action,
       actor_id = excluded.actor_id,
       actor_name = excluded.actor_name,
       target_id = excluded.target_id,
       target_name = excluded.target_name,
       details = excluded.details,
       created_at = excluded.created_at`,
    [
      asText(log.id) || `audit_${Date.now()}_${Math.random().toString(36).slice(2)}`,
      asText(log.action),
      asText(log.actorId) || null,
      asText(log.actorName),
      asText(log.targetId),
      asText(log.targetName),
      asObject(log.details),
      asTimestamp(log.createdAt),
    ]
  );
};

const main = async () => {
  if (!fs.existsSync(dataFile)) {
    throw new Error(`JSON data file not found: ${dataFile}`);
  }

  const data = JSON.parse(fs.readFileSync(dataFile, 'utf8'));
  const client = await pool.connect();
  const counts = {
    users: 0,
    classes: 0,
    enrollments: 0,
    progressEntries: 0,
    practiceSessions: 0,
    mistakes: 0,
    feedbackEntries: 0,
    teacherNotes: 0,
    classResources: 0,
    auditLogs: 0,
    shares: 0,
    legacySessions: 0,
    preferences: 0,
  };

  try {
    await client.query('begin');

    for (const user of asArray(data.googleUsers)) {
      if (!user.id || !user.googleId || !user.email) {
        continue;
      }
      await client.query(
        `insert into app_users (id, google_id, email, name, role, token, created_at, last_seen_at)
         values ($1, $2, $3, $4, $5, $6, coalesce($7::timestamptz, now()), coalesce($8::timestamptz, now()))
         on conflict (id) do update set
           google_id = excluded.google_id,
           email = excluded.email,
           name = excluded.name,
           role = excluded.role,
           token = excluded.token,
           last_seen_at = excluded.last_seen_at`,
        [
          user.id,
          user.googleId,
          user.email,
          asText(user.name) || asText(user.displayName) || user.email,
          ['owner', 'teacher', 'student'].includes(user.role) ? user.role : 'student',
          asText(user.token) || null,
          asTimestamp(user.createdAt),
          asTimestamp(user.lastSeenAt),
        ]
      );
      counts.users += 1;
    }

    for (const classroom of asArray(data.classes)) {
      if (!classroom.id || !classroom.teacherId) {
        continue;
      }
      await client.query(
        `insert into classes (id, teacher_id, name, description, join_code, created_at, updated_at)
         values ($1, $2, $3, $4, $5, coalesce($6::timestamptz, now()), coalesce($7::timestamptz, now()))
         on conflict (id) do update set
           teacher_id = excluded.teacher_id,
           name = excluded.name,
           description = excluded.description,
           join_code = excluded.join_code,
           updated_at = excluded.updated_at`,
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
      counts.classes += 1;
    }

    for (const enrollment of asArray(data.enrollments)) {
      if (!enrollment.id || !enrollment.classId || !enrollment.studentId) {
        continue;
      }
      await client.query(
        `insert into enrollments (id, class_id, student_id, joined_at)
         values ($1, $2, $3, coalesce($4::timestamptz, now()))
         on conflict (class_id, student_id) do update set joined_at = excluded.joined_at`,
        [enrollment.id, enrollment.classId, enrollment.studentId, asTimestamp(enrollment.joinedAt)]
      );
      counts.enrollments += 1;
    }

    for (const entry of asArray(data.progressEntries)) {
      if (!entry.userId || !entry.techniqueId) {
        continue;
      }
      await client.query(
        `insert into progress_entries
          (user_id, technique_id, attempts, best_score, completed, last_difficulty, mastery_level, last_played_at, updated_at)
         values ($1, $2, $3, $4, $5, $6, $7, $8::timestamptz, coalesce($9::timestamptz, now()))
         on conflict (user_id, technique_id) do update set
           attempts = excluded.attempts,
           best_score = excluded.best_score,
           completed = excluded.completed,
           last_difficulty = excluded.last_difficulty,
           mastery_level = excluded.mastery_level,
           last_played_at = excluded.last_played_at,
           updated_at = excluded.updated_at`,
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
      counts.progressEntries += 1;
    }

    for (const session of asArray(data.practiceSessions)) {
      if (!session.id || !session.userId || !session.techniqueId) {
        continue;
      }
      await client.query(
        `insert into practice_sessions (id, user_id, technique_id, score, difficulty, duration_ms, created_at)
         values ($1, $2, $3, $4, $5, $6, coalesce($7::timestamptz, now()))
         on conflict (id) do update set
           user_id = excluded.user_id,
           technique_id = excluded.technique_id,
           score = excluded.score,
           difficulty = excluded.difficulty,
           duration_ms = excluded.duration_ms,
           created_at = excluded.created_at`,
        [
          session.id,
          session.userId,
          session.techniqueId,
          asInt(session.score),
          asText(session.difficulty),
          asInt(session.durationMs),
          asTimestamp(session.createdAt),
        ]
      );
      counts.practiceSessions += 1;
    }

    for (const mistake of asArray(data.mistakes)) {
      if (!mistake.id || !mistake.userId || !mistake.techniqueId) {
        continue;
      }
      await client.query(
        `insert into mistakes
          (id, user_id, technique_id, question, correct_answer, user_answer, payload, created_at)
         values ($1, $2, $3, $4, $5, $6, $7, coalesce($8::timestamptz, now()))
         on conflict (id) do update set
           user_id = excluded.user_id,
           technique_id = excluded.technique_id,
           question = excluded.question,
           correct_answer = excluded.correct_answer,
           user_answer = excluded.user_answer,
           payload = excluded.payload,
           created_at = excluded.created_at`,
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
      counts.mistakes += 1;
    }

    for (const feedback of asArray(data.feedbackEntries)) {
      if (!feedback.id || !feedback.userId) {
        continue;
      }
      await client.query(
        `insert into feedback_entries
          (id, user_id, type, message, technique_id, screen, difficulty, reviewed_at, reviewed_by, created_at)
         values ($1, $2, $3, $4, $5, $6, $7, $8::timestamptz, nullif($9, ''), coalesce($10::timestamptz, now()))
         on conflict (id) do update set
           user_id = excluded.user_id,
           type = excluded.type,
           message = excluded.message,
           technique_id = excluded.technique_id,
           screen = excluded.screen,
           difficulty = excluded.difficulty,
           reviewed_at = excluded.reviewed_at,
           reviewed_by = excluded.reviewed_by,
           created_at = excluded.created_at`,
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
      counts.feedbackEntries += 1;
    }

    for (const note of asArray(data.teacherNotes)) {
      if (!note.id || !note.classId || !note.studentId || !note.teacherId) {
        continue;
      }
      await client.query(
        `insert into teacher_notes (id, class_id, student_id, teacher_id, message, status, created_at)
         values ($1, $2, $3, $4, $5, $6, coalesce($7::timestamptz, now()))
         on conflict (id) do update set
           class_id = excluded.class_id,
           student_id = excluded.student_id,
           teacher_id = excluded.teacher_id,
           message = excluded.message,
           status = excluded.status,
           created_at = excluded.created_at`,
        [note.id, note.classId, note.studentId, note.teacherId, asText(note.message), asText(note.status), asTimestamp(note.createdAt)]
      );
      counts.teacherNotes += 1;
    }

    for (const resource of asArray(data.classResources)) {
      if (!resource.id || !resource.classId || !resource.uploadedBy) {
        continue;
      }
      await client.query(
        `insert into class_resources
          (id, class_id, title, type, file_name, mime_type, size_bytes, uploaded_by, download_path, created_at)
         values ($1, $2, $3, $4, $5, $6, $7, $8, $9, coalesce($10::timestamptz, now()))
         on conflict (id) do update set
           class_id = excluded.class_id,
           title = excluded.title,
           type = excluded.type,
           file_name = excluded.file_name,
           mime_type = excluded.mime_type,
           size_bytes = excluded.size_bytes,
           uploaded_by = excluded.uploaded_by,
           download_path = excluded.download_path,
           created_at = excluded.created_at`,
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
      counts.classResources += 1;
    }

    for (const log of asArray(data.auditLogs)) {
      await insertAuditLog(client, log);
      counts.auditLogs += 1;
    }

    for (const share of asArray(data.shares)) {
      if (!share.id || !share.techniqueId) {
        continue;
      }
      await client.query(
        `insert into shared_visualizations
          (id, technique_id, technique_title, category_id, current_step, note, created_at)
         values ($1, $2, $3, $4, $5, $6, coalesce($7::timestamptz, now()))
         on conflict (id) do update set
           technique_id = excluded.technique_id,
           technique_title = excluded.technique_title,
           category_id = excluded.category_id,
           current_step = excluded.current_step,
           note = excluded.note,
           created_at = excluded.created_at`,
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
      counts.shares += 1;
    }

    for (const session of asArray(data.sessions)) {
      const id = session.id ? String(session.id) : `legacy_${Date.now()}_${Math.random().toString(36).slice(2)}`;
      await client.query(
        `insert into legacy_sessions (id, payload, created_at)
         values ($1, $2, now())
         on conflict (id) do update set payload = excluded.payload`,
        [id, session]
      );
      counts.legacySessions += 1;
    }

    if (Object.keys(asObject(data.preferences)).length > 0) {
      await client.query(
        `insert into app_preferences (key, value, updated_at)
         values ('global', $1, now())
         on conflict (key) do update set value = excluded.value, updated_at = excluded.updated_at`,
        [asObject(data.preferences)]
      );
      counts.preferences = 1;
    }

    await client.query('commit');
    console.log(JSON.stringify({ migrated: counts }, null, 2));
  } catch (error) {
    await client.query('rollback');
    throw error;
  } finally {
    client.release();
  }
};

main()
  .catch((error) => {
    console.error(`Migration failed: ${error.message}`);
    process.exitCode = 1;
  })
  .finally(async () => {
    await pool.end();
  });
