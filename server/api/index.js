const { createApp } = require('../index');

let app;

module.exports = (req, res) => {
  try {
    if (!app) {
      app = createApp();
    }
    return app(req, res);
  } catch (error) {
    console.error('Serverless function invocation failed:', error);
    res.statusCode = 500;
    res.setHeader('Content-Type', 'application/json');
    return res.end(
      JSON.stringify({
        error: 'FUNCTION_INVOCATION_FAILED',
        message: error.message,
      })
    );
  }
};
