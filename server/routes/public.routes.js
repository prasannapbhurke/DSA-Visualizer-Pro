const express = require('express');
const path = require('path');
const { statusResponseDto } = require('../dto');

const createPublicRouter = () => {
  const router = express.Router();

  router.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, '..', 'public', 'index.html'));
  });

  router.get('/openapi.json', (req, res) => {
    res.sendFile(path.join(__dirname, '..', 'public', 'openapi.json'));
  });

  router.get('/privacy-policy', (req, res) => {
    res.sendFile(path.join(__dirname, '..', 'public', 'privacy-policy.html'));
  });

  router.get('/privacy-policy.html', (req, res) => {
    res.redirect(301, '/privacy-policy');
  });

  router.get('/privacy', (req, res) => {
    res.redirect(301, '/privacy-policy');
  });

  router.get('/delete-account', (req, res) => {
    res.sendFile(path.join(__dirname, '..', 'public', 'delete-account.html'));
  });

  router.get('/delete-account.html', (req, res) => {
    res.redirect(301, '/delete-account');
  });

  router.get('/status', (req, res) => {
    res.json(statusResponseDto());
  });

  return router;
};

module.exports = {
  createPublicRouter,
};
