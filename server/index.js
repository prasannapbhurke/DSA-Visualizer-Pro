const express = require('express');
const cors = require('cors');
const fs = require('fs');
const path = require('path');
const { createPostgresStore } = require('./postgresStore');
const { createCache } = require('./cache');
const { createRateLimiter } = require('./middleware/rateLimiter');
const { createAuthMiddleware } = require('./middleware/auth');
const { createPublicRouter } = require('./routes/public.routes');
const { createAuthRouter } = require('./routes/auth.routes');
const { createProgressRouter } = require('./routes/progress.routes');
const { createFeedbackRouter } = require('./routes/feedback.routes');
const { createClassRouter } = require('./routes/class.routes');
const { createAdminRouter } = require('./routes/admin.routes');
const { createControlCenterRouter } = require('./routes/control-center.routes');
const { createLegacyRouter } = require('./routes/legacy.routes');

const DEFAULT_DATA_FILE = process.env.DATA_FILE || path.join(__dirname, 'data.json');
const DEFAULT_UPLOAD_DIR = process.env.UPLOAD_DIR || path.join(__dirname, 'uploads');

const createLogger = () => ({
  info(message, meta = {}) {
    console.log(JSON.stringify({ level: 'info', message, ...meta, timestamp: new Date().toISOString() }));
  },
  error(message, meta = {}) {
    console.error(JSON.stringify({ level: 'error', message, ...meta, timestamp: new Date().toISOString() }));
  },
});

const createApp = ({
  dataFile = DEFAULT_DATA_FILE,
  uploadDir = DEFAULT_UPLOAD_DIR,
  logger = createLogger(),
  store: injectedStore,
} = {}) => {
  const app = express();
  let store = injectedStore;
  if (!store) {
    if (!process.env.DATABASE_URL) {
      throw new Error('DATABASE_URL environment variable is required');
    }
    store = createPostgresStore(process.env.DATABASE_URL);
  }

  const cache = createCache(300000);

  if (!fs.existsSync(uploadDir)) {
    fs.mkdirSync(uploadDir, { recursive: true });
  }

  app.set('trust proxy', true);
  app.use(cors());
  app.use(express.json());

  // Strictly Control Centre: root redirects to admin dashboard
  app.get('/', (req, res) => {
    res.redirect('/admin');
  });

  app.use(express.static(path.join(__dirname, 'public')));
  app.use('/uploads', express.static(uploadDir));
  app.use('/admin', express.static(path.join(__dirname, 'public', 'admin')));

  // Rate Limiter
  app.use(createRateLimiter({ logger }));

  // Request completion logger
  app.use((req, res, next) => {
    const start = Date.now();
    res.on('finish', () => {
      logger.info('request.completed', {
        method: req.method,
        path: req.originalUrl,
        statusCode: res.statusCode,
        durationMs: Date.now() - start,
      });
    });
    next();
  });

  // Authentication Middleware
  const { requireAuth, requireRole } = createAuthMiddleware(store, logger);

  // Mount Modular Routers
  app.use('/', createPublicRouter({ store, logger }));
  app.use('/api', createAuthRouter({ store, logger, requireAuth }));
  app.use('/api', createProgressRouter({ store, logger, requireAuth }));
  app.use('/api', createFeedbackRouter({ store, logger, requireAuth }));
  app.use('/api', createClassRouter({ store, cache, uploadDir, logger, requireAuth }));
  app.use('/api', createAdminRouter({ store, logger, requireAuth, requireRole }));
  app.use('/api', createControlCenterRouter({ store, logger, requireAuth, requireRole }));
  app.use('/', createLegacyRouter());

  return app;
};

const startServer = ({ port = process.env.PORT || 5000, dataFile, logger } = {}) => {
  const app = createApp({ dataFile, logger });
  const server = app.listen(port, () => {
    (logger || createLogger()).info('server.started', { port });
  });
  return { app, server };
};

if (require.main === module) {
  startServer();
}

module.exports = {
  createApp,
  createLogger,
  startServer,
};
