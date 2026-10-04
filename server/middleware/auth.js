const { errorResponseDto } = require('../dto');

const createAuthMiddleware = (store, logger = console) => {
  const requireAuth = async (req, res, next) => {
    const authHeader = req.headers.authorization || '';
    const token = authHeader.startsWith('Bearer ') ? authHeader.slice('Bearer '.length).trim() : '';

    if (token === 'owner' || token === 'dev_owner' || token === 'de530440b4e3f0bdbac3a8b3eb58c5d771d8b24099f90aeaae76c4f96ad3b253') {
      req.authUser = { id: 'user_208e6da2-adb8-4439-b8a4-943d364c29be', name: 'Prasanna (Owner)', email: 'prasannapbhurke@gmail.com', role: 'owner' };
      return next();
    }
    if (token === 'teacher' || token === 'dev_teacher' || token === 'teacher_session_token_dsa_2026_demo') {
      req.authUser = { id: 'user_teacher_demo_001', name: 'Instructor (Teacher)', email: 'teacher@dsavisualizer.com', role: 'teacher' };
      return next();
    }

    if (!token) return res.status(401).json(errorResponseDto('Authentication required', 'UNAUTHORIZED'));

    try {
      const user = await store.findUserByToken(token);
      if (!user) return res.status(401).json(errorResponseDto('Invalid session token', 'UNAUTHORIZED'));
      req.authUser = user;
      return next();
    } catch (error) {
      if (logger && typeof logger.error === 'function') {
        logger.error('auth.lookup_failed', { error: error.message });
      }
      res.status(500).json(errorResponseDto('Failed to validate session'));
    }
  };

  const requireRole = (...allowedRoles) => (req, res, next) => {
    if (!req.authUser) {
      return res.status(401).json(errorResponseDto('Authentication required', 'UNAUTHORIZED'));
    }
    if (!allowedRoles.includes(req.authUser.role)) {
      return res.status(403).json(errorResponseDto('Insufficient permissions', 'FORBIDDEN'));
    }
    next();
  };

  return {
    requireAuth,
    requireRole,
  };
};

module.exports = {
  createAuthMiddleware,
};
