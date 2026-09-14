const { errorResponseDto } = require('../dto');

const createAuthMiddleware = (store, logger = console) => {
  const requireAuth = async (req, res, next) => {
    const authHeader = req.headers.authorization || '';
    const token = authHeader.startsWith('Bearer ') ? authHeader.slice('Bearer '.length).trim() : '';
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
