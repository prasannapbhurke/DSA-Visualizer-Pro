import re

with open('index.js', 'r', encoding='utf-8') as f:
    content = f.read()

# 1. Remove createDataStore completely
content = re.sub(
    r'const createDataStore = \(dataFile\) => \{.*?\n\s+return \{\n\s+read: async \(\) => readDataFile\(\),\n\s+write: async \(data\) => writeDataFile\(data\),\n\s+\};\n\};\n',
    '',
    content,
    flags=re.DOTALL
)

# 2. Update store creation
content = content.replace(
    '''  const store = process.env.DATABASE_URL
    ? createPostgresStore(process.env.DATABASE_URL)
    : createDataStore(dataFile);''',
    '''  if (!process.env.DATABASE_URL) {
    throw new Error('DATABASE_URL environment variable is required');
  }
  const store = createPostgresStore(process.env.DATABASE_URL);'''
)

# 3. /api/auth/logout
logout_orig = '''      const data = await store.read();
      // For Google users, we just invalidate the token but keep the user.
      const user = data.googleUsers.find(u => u.id === req.authUser.id);
      if (user) {
        delete user.token;
      } else {
        data.users = (data.users || []).filter((entry) => entry.id !== req.authUser.id);
      }
      await store.write(data);'''
logout_new = '''      await store.logoutUser(req.authUser.id);'''
content = content.replace(logout_orig, logout_new)

# 4. /api/progress
prog_orig = '''      const data = await store.read();
      const progressEntries = (data.progressEntries || []).filter((entry) => entry.userId === req.authUser.id);
      res.json(progressEntriesResponseDto(progressEntries));'''
prog_new = '''      const result = await store.query('SELECT * FROM progress_entries WHERE user_id = $1 ORDER BY updated_at DESC', [req.authUser.id]);
      const progressEntries = rowsToCamel(result.rows).map(stripSnakeOnly);
      res.json(progressEntriesResponseDto(progressEntries));'''
content = content.replace(prog_orig, prog_new)

# 5. PUT /api/techniques/:id/progress
put_prog_orig = '''      const data = await store.read();
      const nextProgress = {
        userId: req.authUser.id,
        techniqueId: req.params.id,
        ...parsedRequest.value.progress,
        updatedAt: new Date().toISOString(),
      };

      const existingIndex = (data.progressEntries || []).findIndex(
        (entry) => entry.userId === req.authUser.id && entry.techniqueId === req.params.id
      );

      if (existingIndex >= 0) {
        data.progressEntries[existingIndex] = nextProgress;
      } else {
        data.progressEntries.push(nextProgress);
      }

      await store.write(data);
      logger.info('progress.saved', { userId: req.authUser.id, techniqueId: req.params.id });
      res.json(progressResponseDto(nextProgress));'''
put_prog_new = '''      const { attempts, bestScore, completed, lastDifficulty, masteryLevel, lastPlayedAt } = parsedRequest.value.progress;
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
      res.json(progressResponseDto(nextProgress));'''
content = content.replace(put_prog_orig, put_prog_new)

# 6. GET /api/sessions
sessions_orig = '''      const data = await store.read();
      const sessions = (data.practiceSessions || []).filter((entry) => entry.userId === req.authUser.id);
      res.json(practiceSessionsResponseDto(sessions));'''
sessions_new = '''      const result = await store.query('SELECT * FROM practice_sessions WHERE user_id = $1 ORDER BY created_at DESC', [req.authUser.id]);
      const sessions = rowsToCamel(result.rows).map(stripSnakeOnly);
      res.json(practiceSessionsResponseDto(sessions));'''
content = content.replace(sessions_orig, sessions_new)

# 7. POST /api/sessions
post_sessions_orig = '''      const data = await store.read();
      const session = {
        id: crypto.randomUUID(),
        userId: req.authUser.id,
        ...parsedRequest.value.session,
        createdAt: new Date().toISOString(),
      };
      data.practiceSessions.push(session);
      await store.write(data);
      logger.info('practice_sessions.saved', {
        userId: req.authUser.id,
        techniqueId: session.techniqueId,
      });
      res.status(201).json(practiceSessionCreatedResponseDto(session.id));'''
post_sessions_new = '''      const session = {
        id: crypto.randomUUID(),
        userId: req.authUser.id,
        ...parsedRequest.value.session,
        createdAt: new Date().toISOString(),
      };
      await store.query(
        `INSERT INTO practice_sessions (id, user_id, technique_id, score, difficulty, duration_ms, created_at)
         VALUES ($1, $2, $3, $4, $5, $6, $7::timestamptz)`,
        [session.id, session.userId, session.techniqueId, session.score || 0, session.difficulty || '', session.durationMs || 0, session.createdAt]
      );
      logger.info('practice_sessions.saved', {
        userId: req.authUser.id,
        techniqueId: session.techniqueId,
      });
      res.status(201).json(practiceSessionCreatedResponseDto(session.id));'''
content = content.replace(post_sessions_orig, post_sessions_new)

with open('index.js', 'w', encoding='utf-8') as f:
    f.write(content)

print("Pass 1 completed!")
