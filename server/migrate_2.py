import re

with open('index.js', 'r', encoding='utf-8') as f:
    content = f.read()

# 1. POST /api/feedback
post_feedback_orig = '''      const data = await store.read();
      const feedback = {
        id: `feedback_${crypto.randomUUID()}`,
        userId: req.authUser.id,
        createdAt: new Date().toISOString(),
        ...parsedRequest.value.feedback,
      };
      data.feedbackEntries.push(feedback);
      await store.write(data);'''
post_feedback_new = '''      const feedback = {
        id: `feedback_${crypto.randomUUID()}`,
        userId: req.authUser.id,
        createdAt: new Date().toISOString(),
        ...parsedRequest.value.feedback,
      };
      await store.query(
        `INSERT INTO feedback_entries (id, user_id, type, message, technique_id, screen, difficulty, created_at)
         VALUES ($1, $2, $3, $4, $5, $6, $7, $8::timestamptz)`,
        [feedback.id, feedback.userId, feedback.type || '', feedback.message || '', feedback.techniqueId || '', feedback.screen || '', feedback.difficulty || '', feedback.createdAt]
      );'''
content = content.replace(post_feedback_orig, post_feedback_new)

# 2. GET /api/feedback
get_feedback_orig = '''      const data = await store.read();
      const feedback = [...(data.feedbackEntries || [])]
        .sort((left, right) => String(right.createdAt || '').localeCompare(String(left.createdAt || '')))
        .map((entry) => sanitizeFeedbackEntry(entry, data));
      res.json(feedbackResponseDto(feedback));'''
get_feedback_new = '''      const result = await store.query(`
        SELECT f.*, u.name as user_display_name, u.email as user_email, u.role as user_role, 'google' as provider 
        FROM feedback_entries f 
        LEFT JOIN app_users u ON f.user_id = u.id 
        ORDER BY f.created_at DESC
      `);
      const feedback = rowsToCamel(result.rows).map(stripSnakeOnly);
      res.json(feedbackResponseDto(feedback));'''
content = content.replace(get_feedback_orig, get_feedback_new)

# 3. POST /api/mistakes
post_mistakes_orig = '''      const data = await store.read();
      const mistake = {
        id: `mistake_${crypto.randomUUID()}`,
        userId: req.authUser.id,
        createdAt: new Date().toISOString(),
        ...parsedRequest.value.mistake,
      };
      data.mistakes.push(mistake);
      await store.write(data);'''
post_mistakes_new = '''      const mistake = {
        id: `mistake_${crypto.randomUUID()}`,
        userId: req.authUser.id,
        createdAt: new Date().toISOString(),
        ...parsedRequest.value.mistake,
      };
      await store.query(
        `INSERT INTO mistakes (id, user_id, technique_id, question, correct_answer, user_answer, payload, created_at)
         VALUES ($1, $2, $3, $4, $5, $6, $7::jsonb, $8::timestamptz)`,
        [mistake.id, mistake.userId, mistake.techniqueId, mistake.question, mistake.correctAnswer, mistake.userAnswer, mistake, mistake.createdAt]
      );'''
content = content.replace(post_mistakes_orig, post_mistakes_new)

# 4. GET /api/mistakes
get_mistakes_orig = '''      const data = await store.read();
      const mistakes = (data.mistakes || []).filter((entry) => entry.userId === req.authUser.id);
      res.json(mistakesResponseDto(mistakes));'''
get_mistakes_new = '''      const result = await store.query('SELECT * FROM mistakes WHERE user_id = $1 ORDER BY created_at DESC', [req.authUser.id]);
      const mistakes = rowsToCamel(result.rows).map(row => stripSnakeOnly({ ...row.payload, ...row }));
      res.json(mistakesResponseDto(mistakes));'''
content = content.replace(get_mistakes_orig, get_mistakes_new)

# 5. GET /api/dashboard
get_dashboard_orig = '''      const data = await store.read();
      const progressEntries = (data.progressEntries || []).filter((entry) => entry.userId === req.authUser.id);
      const practiceSessions = (data.practiceSessions || []).filter((entry) => entry.userId === req.authUser.id);
      res.json(dashboardResponseDto(buildDashboard({
        user: req.authUser,
        progressEntries,
        practiceSessions,
      })));'''
get_dashboard_new = '''      const [progressRes, sessionsRes] = await Promise.all([
        store.query('SELECT * FROM progress_entries WHERE user_id = $1 ORDER BY updated_at DESC', [req.authUser.id]),
        store.query('SELECT * FROM practice_sessions WHERE user_id = $1 ORDER BY created_at DESC', [req.authUser.id])
      ]);
      const progressEntries = rowsToCamel(progressRes.rows).map(stripSnakeOnly);
      const practiceSessions = rowsToCamel(sessionsRes.rows).map(stripSnakeOnly);
      res.json(dashboardResponseDto(buildDashboard({
        user: req.authUser,
        progressEntries,
        practiceSessions,
      })));'''
content = content.replace(get_dashboard_orig, get_dashboard_new)

with open('index.js', 'w', encoding='utf-8') as f:
    f.write(content)

print("Pass 2 completed!")
