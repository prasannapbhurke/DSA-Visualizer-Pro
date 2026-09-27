const express = require('express');
const path = require('path');
const { statusResponseDto } = require('../dto');

const createPublicRouter = ({ store, logger } = {}) => {
  const router = express.Router();

  router.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, '..', 'public', 'index.html'));
  });

  router.get('/openapi.json', (req, res) => {
    res.sendFile(path.join(__dirname, '..', 'public', 'openapi.json'));
  });

  router.get('/visualize', (req, res) => {
    res.sendFile(path.join(__dirname, '..', 'public', 'visualize.html'));
  });

  router.get('/visualize.html', (req, res) => {
    res.sendFile(path.join(__dirname, '..', 'public', 'visualize.html'));
  });

  router.get('/privacy-policy', (req, res) => {
    res.set('Cache-Control', 'no-cache, no-store, must-revalidate');
    res.sendFile(path.join(__dirname, '..', 'public', 'privacy-policy.html'));
  });

  router.get('/privacy-policy.html', (req, res) => {
    res.redirect(301, '/privacy-policy');
  });

  router.get('/privacy', (req, res) => {
    res.redirect(301, '/privacy-policy');
  });

  router.get('/delete-account', (req, res) => {
    res.set('Cache-Control', 'no-cache, no-store, must-revalidate');
    res.sendFile(path.join(__dirname, '..', 'public', 'delete-account.html'));
  });

  router.get('/delete-account.html', (req, res) => {
    res.redirect(301, '/delete-account');
  });

  const handleStatus = async (req, res) => {
    let dbStatus = 'unconfigured';
    if (store && typeof store.query === 'function') {
      try {
        await store.query('SELECT 1');
        dbStatus = 'connected';
      } catch (error) {
        dbStatus = 'error';
        if (logger && typeof logger.warn === 'function') {
          logger.warn('db.keep_alive.error', { error: error.message });
        }
      }
    }
    const base = statusResponseDto();
    res.json({
      data: {
        ...base.data,
        database: dbStatus,
      },
    });
  };

  router.get('/status', handleStatus);
  router.get('/api/status', handleStatus);
  router.get('/api/health', handleStatus);
  router.get('/api/keep-alive', handleStatus);

  return router;
};

module.exports = {
  createPublicRouter,
};
