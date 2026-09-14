const express = require('express');

const createLegacyRouter = () => {
  const router = express.Router();
  const goneResponse = (req, res) => res.status(410).json({ error: 'Endpoint removed' });

  router.post('/save-session', goneResponse);
  router.get('/sessions', goneResponse);
  router.post('/share-visualization', goneResponse);
  router.get('/shared/:shareId', goneResponse);
  router.post('/save-preferences', goneResponse);
  router.get('/preferences', goneResponse);

  return router;
};

module.exports = {
  createLegacyRouter,
};
