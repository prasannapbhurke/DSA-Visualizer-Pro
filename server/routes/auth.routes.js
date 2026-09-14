const express = require('express');
const crypto = require('crypto');
const { OAuth2Client } = require('google-auth-library');
const {
  authResponseDto,
  errorResponseDto,
  logoutResponseDto,
  meResponseDto,
  successMessageResponseDto,
} = require('../dto');
const { sanitizeUser, sanitizeGoogleUser } = require('../utils/sanitize');

const createAuthRouter = ({ store, logger = console, requireAuth }) => {
  const router = express.Router();
  const googleClientId = process.env.GOOGLE_CLIENT_ID;
  const oauth2Client = new OAuth2Client(googleClientId);

  const persistGoogleUser = async ({ googleId, email, name }) => {
    const token = crypto.randomBytes(32).toString('hex');
    const refreshToken = crypto.randomBytes(32).toString('hex');
    const user = await store.upsertGoogleUser({
      googleId,
      email,
      name,
      token,
      refreshToken,
      ownerEmail: 'prasannapbhurke@gmail.com',
    });
    if (logger && typeof logger.info === 'function') {
      logger.info('auth.google.login_fast', { userId: user.id, email, role: user.role });
    }
    return user;
  };

  router.get('/auth/google-config', (req, res) => {
    res.json({
      data: {
        configured: true,
        clientId: process.env.GOOGLE_CLIENT_ID || '',
      },
    });
  });

  router.post('/auth/guest', (req, res) => {
    res.status(410).json(errorResponseDto('Guest mode has been removed. Sign in with Google.', 'GUEST_REMOVED'));
  });

  router.post('/auth/google', async (req, res) => {
    try {
      const credential = String(req.body?.credential || req.body?.idToken || '').trim();
      if (!credential) {
        return res.status(400).json(errorResponseDto('Google credential is required', 'VALIDATION_ERROR'));
      }
      if (!process.env.GOOGLE_CLIENT_ID) {
        if (logger && typeof logger.error === 'function') {
          logger.error('google.auth.missing_client_id');
        }
        return res.status(500).json(errorResponseDto('Google sign-in is not configured', 'CONFIGURATION_ERROR'));
      }

      const ticket = await oauth2Client.verifyIdToken({ idToken: credential, audience: process.env.GOOGLE_CLIENT_ID });
      const { sub: googleId, email, name } = ticket.getPayload();

      const user = await persistGoogleUser({ googleId, email, name });

      const token = user.token || req.authUser?.token || '';
      const refreshToken = user.refreshToken || req.authUser?.refreshToken || '';

      res.cookie('refreshToken', refreshToken, {
        httpOnly: true,
        secure: process.env.NODE_ENV === 'production',
        sameSite: 'lax',
        maxAge: 7 * 24 * 60 * 60 * 1000,
        path: '/api/auth',
      });

      res.json(authResponseDto({
        token,
        refreshToken,
        user: sanitizeGoogleUser({ ...user, provider: 'google' }),
      }));
    } catch (error) {
      if (logger && typeof logger.error === 'function') {
        logger.error('google.auth.error', { error: error.message });
      }
      const message = process.env.NODE_ENV === 'production'
        ? 'Invalid Google token'
        : `Invalid Google token: ${error.message}`;
      res.status(401).json(errorResponseDto(message, 'UNAUTHORIZED'));
    }
  });

  router.get('/me', requireAuth, async (req, res) => {
    const sanitized = req.authUser.googleId ? sanitizeGoogleUser(req.authUser) : sanitizeUser(req.authUser);
    res.json(meResponseDto(sanitized));
  });

  router.post('/auth/logout', requireAuth, async (req, res) => {
    try {
      if (typeof store.logoutUser === 'function') {
        await store.logoutUser(req.authUser.id);
      }
      if (logger && typeof logger.info === 'function') {
        logger.info('auth.logout', { userId: req.authUser.id });
      }
      res.json(logoutResponseDto());
    } catch (error) {
      if (logger && typeof logger.error === 'function') {
        logger.error('auth.logout_failed', { error: error.message, userId: req.authUser.id });
      }
      res.status(500).json(errorResponseDto('Failed to logout'));
    }
  });

  router.delete('/users/me', requireAuth, async (req, res) => {
    try {
      const userId = req.authUser.id;
      if (logger && typeof logger.info === 'function') {
        logger.info('user.deletion_requested', { userId, email: req.authUser.email });
      }
      if (typeof store.deleteUser === 'function') {
        await store.deleteUser(userId);
      } else {
        await store.query('DELETE FROM app_users WHERE id = $1', [userId]);
      }
      if (logger && typeof logger.info === 'function') {
        logger.info('user.deleted_successfully', { userId });
      }
      res.json(successMessageResponseDto('Account and all associated learning data have been permanently deleted'));
    } catch (error) {
      if (logger && typeof logger.error === 'function') {
        logger.error('user.deletion_failed', { error: error.message, userId: req.authUser?.id });
      }
      res.status(500).json(errorResponseDto('Failed to delete account'));
    }
  });

  router.post('/users/request-deletion', async (req, res) => {
    try {
      const email = String(req.body?.email || '').trim().toLowerCase();
      if (!email || !email.includes('@')) {
        return res.status(400).json(errorResponseDto('A valid email address is required', 'VALIDATION_ERROR'));
      }
      if (logger && typeof logger.info === 'function') {
        logger.info('user.web_deletion_request_received', { email });
      }
      if (typeof store.deleteUserByEmail === 'function') {
        await store.deleteUserByEmail(email);
      } else {
        const userRes = await store.query('SELECT id FROM app_users WHERE lower(email) = $1', [email]);
        if (userRes.rowCount > 0) {
          const userId = userRes.rows[0].id;
          await store.query('DELETE FROM app_users WHERE id = $1', [userId]);
        }
      }
      if (logger && typeof logger.info === 'function') {
        logger.info('user.web_deletion_request_processed', { email });
      }
      res.json(successMessageResponseDto('If an account exists with this email, all account and progress data have been permanently deleted.'));
    } catch (error) {
      if (logger && typeof logger.error === 'function') {
        logger.error('user.web_deletion_request_failed', { error: error.message });
      }
      res.status(500).json(errorResponseDto('Failed to process deletion request'));
    }
  });

  return router;
};

module.exports = {
  createAuthRouter,
};
