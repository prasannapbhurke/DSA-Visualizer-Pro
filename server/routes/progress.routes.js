const express = require('express');
const crypto = require('crypto');
const {
  dashboardResponseDto,
  errorResponseDto,
  mistakeCreatedResponseDto,
  mistakesResponseDto,
  parseSaveMistakeRequest,
  parseSaveSessionRequest,
  parseTechniqueProgressRequest,
  practiceSessionsResponseDto,
  progressEntriesResponseDto,
  progressResponseDto,
  sessionSavedResponseDto,
} = require('../dto');
const { rowsToCamel, stripSnakeOnly } = require('../postgresStore');
const { buildDashboard } = require('../utils/dashboard');

const createProgressRouter = ({ store, logger = console, requireAuth }) => {
  const router = express.Router();

  router.get('/techniques/:id/progress', requireAuth, async (req, res) => {
    try {
      const progRes = await store.query(
        'SELECT * FROM progress_entries WHERE user_id = $1 AND technique_id = $2',
        [req.authUser.id, req.params.id]
      );
      if (progRes.rowCount === 0) return res.json({ data: null });
      const entry = stripSnakeOnly(rowsToCamel(progRes.rows)[0]);
      res.json(progressResponseDto(entry));
    } catch (error) {
      if (logger && typeof logger.error === 'function') {
        logger.error('technique_progress.load_failed', { error: error.message });
      }
      res.status(500).json(errorResponseDto('Failed to load progress'));
    }
  });

  router.get('/progress', requireAuth, async (req, res) => {
    try {
      const result = await store.query(
        'SELECT * FROM progress_entries WHERE user_id = $1 ORDER BY updated_at DESC',
        [req.authUser.id]
      );
      const progressEntries = rowsToCamel(result.rows).map(stripSnakeOnly);
      res.json(progressEntriesResponseDto(progressEntries));
    } catch (error) {
      if (logger && typeof logger.error === 'function') {
        logger.error('progress.list_failed', { error: error.message, userId: req.authUser.id });
      }
      res.status(500).json(errorResponseDto('Failed to load progress entries'));
    }
  });

  router.put('/techniques/:id/progress', requireAuth, async (req, res) => {
    const parsedRequest = parseTechniqueProgressRequest(req.body);
    if (!parsedRequest.ok) {
      if (logger && typeof logger.info === 'function') {
        logger.info('progress.invalid_payload', { body: req.body, techniqueId: req.params.id, userId: req.authUser.id });
      }
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
          req.authUser.id,
          req.params.id,
          attempts || 0,
          bestScore || 0,
          Boolean(completed),
          lastDifficulty || '',
          masteryLevel || '',
          lastPlayedAt ? new Date(lastPlayedAt).toISOString() : new Date().toISOString(),
          new Date().toISOString(),
        ]
      );

      const nextProgress = {
        userId: req.authUser.id,
        techniqueId: req.params.id,
        ...parsedRequest.value.progress,
        updatedAt: new Date().toISOString(),
      };

      if (logger && typeof logger.info === 'function') {
        logger.info('progress.saved', { userId: req.authUser.id, techniqueId: req.params.id });
      }
      res.json(progressResponseDto(nextProgress));
    } catch (error) {
      if (logger && typeof logger.error === 'function') {
        logger.error('progress.save_failed', { error: error.message, userId: req.authUser.id, techniqueId: req.params.id });
      }
      res.status(500).json(errorResponseDto('Failed to save progress'));
    }
  });

  router.get('/sessions', requireAuth, async (req, res) => {
    try {
      const result = await store.query(
        'SELECT * FROM practice_sessions WHERE user_id = $1 ORDER BY created_at DESC',
        [req.authUser.id]
      );
      const sessions = rowsToCamel(result.rows).map(stripSnakeOnly);
      res.json(practiceSessionsResponseDto(sessions));
    } catch (error) {
      if (logger && typeof logger.error === 'function') {
        logger.error('practice_sessions.load_failed', { error: error.message, userId: req.authUser.id });
      }
      res.status(500).json(errorResponseDto('Failed to load practice sessions'));
    }
  });

  router.post('/sessions', requireAuth, async (req, res) => {
    const parsedRequest = parseSaveSessionRequest(req.body);
    if (!parsedRequest.ok) return res.status(400).json(parsedRequest);
    try {
      const session = {
        id: `session_${crypto.randomUUID()}`,
        userId: req.authUser.id,
        createdAt: new Date().toISOString(),
        ...parsedRequest.value.session,
      };
      await store.query(
        `INSERT INTO practice_sessions (id, user_id, technique_id, score, total_nodes, time_taken_ms, config, history, created_at)
         VALUES ($1, $2, $3, $4, $5, $6, $7::jsonb, $8::jsonb, $9::timestamptz)`,
        [
          session.id,
          session.userId,
          session.techniqueId,
          session.score,
          session.totalNodes,
          session.timeTakenMs,
          JSON.stringify(session.config || {}),
          JSON.stringify(session.history || []),
          session.createdAt,
        ]
      );
      res.status(201).json(sessionSavedResponseDto(session.id));
    } catch (error) {
      if (logger && typeof logger.error === 'function') {
        logger.error('practice_sessions.save_failed', { error: error.message });
      }
      res.status(500).json(errorResponseDto('Failed to save session'));
    }
  });

  router.post('/mistakes', requireAuth, async (req, res) => {
    const parsedRequest = parseSaveMistakeRequest(req.body);
    if (!parsedRequest.ok) {
      if (logger && typeof logger.info === 'function') {
        logger.info('mistake.invalid_payload', { body: req.body, userId: req.authUser.id });
      }
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
        [
          mistake.id,
          mistake.userId,
          mistake.techniqueId,
          mistake.question,
          mistake.correctAnswer,
          mistake.userAnswer,
          mistake,
          mistake.createdAt,
        ]
      );
      if (logger && typeof logger.info === 'function') {
        logger.info('mistake.saved', {
          userId: req.authUser.id,
          mistakeId: mistake.id,
          techniqueId: mistake.techniqueId,
        });
      }
      res.status(201).json(mistakeCreatedResponseDto(mistake));
    } catch (error) {
      if (logger && typeof logger.error === 'function') {
        logger.error('mistake.save_failed', { error: error.message, userId: req.authUser.id });
      }
      res.status(500).json(errorResponseDto('Failed to save mistake'));
    }
  });

  router.get('/mistakes', requireAuth, async (req, res) => {
    try {
      const result = await store.query('SELECT * FROM mistakes WHERE user_id = $1 ORDER BY created_at DESC', [req.authUser.id]);
      const mistakes = rowsToCamel(result.rows).map((row) => stripSnakeOnly({ ...row.payload, ...row }));
      res.json(mistakesResponseDto(mistakes));
    } catch (error) {
      if (logger && typeof logger.error === 'function') {
        logger.error('mistakes.load_failed', { error: error.message, userId: req.authUser.id });
      }
      res.status(500).json(errorResponseDto('Failed to load mistakes'));
    }
  });

  router.get('/dashboard', requireAuth, async (req, res) => {
    try {
      const [progressRes, sessionsRes] = await Promise.all([
        store.query('SELECT * FROM progress_entries WHERE user_id = $1 ORDER BY updated_at DESC', [req.authUser.id]),
        store.query('SELECT * FROM practice_sessions WHERE user_id = $1 ORDER BY created_at DESC', [req.authUser.id]),
      ]);
      const progressEntries = rowsToCamel(progressRes.rows).map(stripSnakeOnly);
      const practiceSessions = rowsToCamel(sessionsRes.rows).map(stripSnakeOnly);
      res.json(dashboardResponseDto(buildDashboard({
        user: req.authUser,
        progressEntries,
        practiceSessions,
      })));
    } catch (error) {
      if (logger && typeof logger.error === 'function') {
        logger.error('dashboard.load_failed', { error: error.message, userId: req.authUser.id });
      }
      res.status(500).json(errorResponseDto('Failed to load dashboard'));
    }
  });

  return router;
};

module.exports = {
  createProgressRouter,
};
