const express = require('express');
const crypto = require('crypto');
const {
  errorResponseDto,
  feedbackCreatedResponseDto,
  feedbackResponseDto,
  parseFeedbackRequest,
  teacherFeedbackResponseDto,
} = require('../dto');
const { rowsToCamel, stripSnakeOnly } = require('../postgresStore');

const createFeedbackRouter = ({ store, logger = console, requireAuth }) => {
  const router = express.Router();

  router.post('/feedback', requireAuth, async (req, res) => {
    if (req.authUser.role === 'guest' || req.authUser.type === 'guest' || req.authUser.provider === 'guest') {
      return res.status(403).json(errorResponseDto('Sign in with Google to send feedback', 'FORBIDDEN'));
    }

    const parsedRequest = parseFeedbackRequest(req.body);
    if (!parsedRequest.ok) {
      if (logger && typeof logger.info === 'function') {
        logger.info('feedback.invalid_payload', { body: req.body, userId: req.authUser.id });
      }
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
        [
          feedback.id,
          feedback.userId,
          feedback.type || '',
          feedback.message || '',
          feedback.techniqueId || '',
          feedback.screen || '',
          feedback.difficulty || '',
          feedback.createdAt,
        ]
      );
      if (logger && typeof logger.info === 'function') {
        logger.info('feedback.saved', {
          userId: req.authUser.id,
          feedbackId: feedback.id,
          type: feedback.type,
          techniqueId: feedback.techniqueId,
        });
      }
      res.status(201).json(feedbackCreatedResponseDto(feedback));
    } catch (error) {
      if (logger && typeof logger.error === 'function') {
        logger.error('feedback.save_failed', { error: error.message, userId: req.authUser.id });
      }
      res.status(500).json(errorResponseDto('Failed to save feedback'));
    }
  });

  router.get('/feedback', requireAuth, async (req, res) => {
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
      if (logger && typeof logger.error === 'function') {
        logger.error('feedback.list_failed', { error: error.message, userId: req.authUser.id });
      }
      res.status(500).json(errorResponseDto('Failed to load feedback'));
    }
  });

  router.delete('/feedback/:feedbackId', requireAuth, async (req, res) => {
    if (!['owner', 'teacher'].includes(req.authUser.role)) {
      return res.status(403).json(errorResponseDto('Only owners or teachers can delete feedback', 'FORBIDDEN'));
    }

    try {
      let result;
      if (req.authUser.role === 'owner') {
        result = await store.query('DELETE FROM feedback_entries WHERE id = $1 RETURNING id', [req.params.feedbackId]);
      } else {
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

      if (logger && typeof logger.info === 'function') {
        logger.info('feedback.deleted', {
          feedbackId: req.params.feedbackId,
          userId: req.authUser.id,
          role: req.authUser.role,
        });
      }
      res.json({ status: 'success', data: { id: req.params.feedbackId } });
    } catch (error) {
      if (logger && typeof logger.error === 'function') {
        logger.error('feedback.delete_failed', { error: error.message, userId: req.authUser.id });
      }
      res.status(500).json(errorResponseDto('Failed to delete feedback'));
    }
  });

  router.get('/teacher/feedback', requireAuth, async (req, res) => {
    if (!['teacher', 'owner'].includes(req.authUser.role)) {
      return res.status(403).json(errorResponseDto('Only teachers or owners can view teacher feedback', 'FORBIDDEN'));
    }
    try {
      let query = `SELECT f.*, u.name as user_display_name, u.email as user_email, u.role as user_role, 'google' as provider, c.id as class_id, c.name as class_name FROM feedback_entries f JOIN enrollments e ON f.user_id = e.student_id JOIN classes c ON e.class_id = c.id JOIN app_users u ON f.user_id = u.id WHERE (c.teacher_id = $1 OR $2 = 'owner')`;
      const params = [req.authUser.id, req.authUser.role];
      const classIdFilter = typeof req.query.classId === 'string' ? req.query.classId.trim() : '';
      const typeFilter = typeof req.query.type === 'string' ? req.query.type.trim().toLowerCase() : '';
      if (classIdFilter) {
        query += ` AND c.id = $3`;
        params.push(classIdFilter);
      }
      if (typeFilter) {
        query += ` AND LOWER(f.type) = $${params.length + 1}`;
        params.push(typeFilter);
      }
      query += ` ORDER BY f.created_at DESC`;
      const result = await store.query(query, params);
      res.json(teacherFeedbackResponseDto(rowsToCamel(result.rows).map(stripSnakeOnly)));
    } catch (error) {
      if (logger && typeof logger.error === 'function') {
        logger.error('teacher_feedback.load_failed', { error: error.message });
      }
      res.status(500).json(errorResponseDto('Failed to load feedback'));
    }
  });

  router.patch('/teacher/feedback/:feedbackId/review', requireAuth, async (req, res) => {
    if (!['teacher', 'owner'].includes(req.authUser.role)) {
      return res.status(403).json(errorResponseDto('Only teachers or owners can review feedback', 'FORBIDDEN'));
    }
    try {
      const result = await store.query(
        `UPDATE feedback_entries SET reviewed_at = NOW(), reviewed_by = $1 WHERE id = $2 RETURNING *`,
        [req.authUser.id, req.params.feedbackId]
      );
      if (result.rowCount === 0) return res.status(404).json(errorResponseDto('Feedback not found', 'NOT_FOUND'));
      res.json(teacherFeedbackResponseDto([stripSnakeOnly(rowsToCamel(result.rows)[0])]));
    } catch (error) {
      if (logger && typeof logger.error === 'function') {
        logger.error('feedback.review_failed', { error: error.message });
      }
      res.status(500).json(errorResponseDto('Failed to review feedback'));
    }
  });

  return router;
};

module.exports = {
  createFeedbackRouter,
};
