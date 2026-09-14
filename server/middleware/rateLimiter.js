const { errorResponseDto } = require('../dto');

const RATE_LIMIT_WINDOW_MS = 60 * 1000;
const RATE_LIMIT_MAX_REQUESTS = 120;

const createRateLimiter = ({
  windowMs = RATE_LIMIT_WINDOW_MS,
  maxRequests = RATE_LIMIT_MAX_REQUESTS,
  logger = console,
} = {}) => {
  const requestCounts = new Map();

  return (req, res, next) => {
    const now = Date.now();
    const ip = req.ip || req.socket.remoteAddress || 'unknown';
    const current = requestCounts.get(ip);

    if (!current || now - current.startedAt >= windowMs) {
      requestCounts.set(ip, { startedAt: now, count: 1 });
      return next();
    }

    if (current.count >= maxRequests) {
      if (logger && typeof logger.info === 'function') {
        logger.info('request.rate_limited', {
          ip,
          method: req.method,
          path: req.originalUrl,
        });
      }
      return res.status(429).json(errorResponseDto('Too many requests, please try again shortly', 'RATE_LIMITED'));
    }

    current.count += 1;
    next();
  };
};

module.exports = {
  createRateLimiter,
  RATE_LIMIT_WINDOW_MS,
  RATE_LIMIT_MAX_REQUESTS,
};
