const fs = require('fs');

let content = fs.readFileSync('index.js', 'utf8');

const replacements = [
  {
    name: 'POST /api/auth/google',
    pattern: /app\.post\('\/api\/auth\/google', async \(req, res\) => \{[\s\S]*?const data = await store\.read\(\);[\s\S]*?await store\.write\(data\);[\s\S]*?\}\);/,
    replacement: `app.post('/api/auth/google', async (req, res) => {
    try {
      const { credential } = req.body;
      const ticket = await authClient.verifyIdToken({ idToken: credential, audience: process.env.GOOGLE_CLIENT_ID });
      const payload = ticket.getPayload();
      const { sub: googleId, email, name, picture: avatarUrl } = payload;
      
      const { token, refreshToken } = createSessionTokens({ id: googleId, email, name, role: 'student', type: 'google' });
      
      const userRes = await store.query('SELECT * FROM app_users WHERE google_id = $1 OR email = $2', [googleId, email]);
      let user;
      
      if (userRes.rowCount > 0) {
        user = rowsToCamel(userRes.rows)[0];
        await store.query('UPDATE app_users SET name = $1, last_seen_at = $2::timestamptz, refresh_token = $3, token = $4 WHERE id = $5', 
          [name || user.name, new Date().toISOString(), refreshToken, token, user.id]);
      } else {
        const newId = \`user_\${crypto.randomUUID()}\`;
        await store.query(\`INSERT INTO app_users (id, google_id, email, name, avatar_url, role, refresh_token, token, created_at, last_seen_at) 
                           VALUES ($1, $2, $3, $4, $5, 'student', $6, $7, $8::timestamptz, $8::timestamptz)\`, 
          [newId, googleId, email, name || email.split('@')[0], avatarUrl || '', refreshToken, token, new Date().toISOString()]);
        
        const newRes = await store.query('SELECT * FROM app_users WHERE id = $1', [newId]);
        user = rowsToCamel(newRes.rows)[0];
      }

      res.cookie('refreshToken', refreshToken, {
        httpOnly: true,
        secure: process.env.NODE_ENV === 'production',
        sameSite: 'lax',
        maxAge: 7 * 24 * 60 * 60 * 1000,
        path: '/api/auth'
      });
      res.json(authResponseDto(sanitizeGoogleUser({ ...user, provider: 'google' }), token));
    } catch (error) {
      logger.error('auth.google_login_failed', { error: error.message });
      res.status(401).json(errorResponseDto('Invalid Google token', 'UNAUTHORIZED'));
    }
  });`
  },
  {
    name: 'POST /api/auth/google-access-token',
    pattern: /app\.post\('\/api\/auth\/google-access-token', async \(req, res\) => \{[\s\S]*?const data = await store\.read\(\);[\s\S]*?await store\.write\(data\);[\s\S]*?\}\);/,
    replacement: `app.post('/api/auth/google-access-token', async (req, res) => {
    try {
      const { access_token } = req.body;
      const response = await fetch('https://www.googleapis.com/oauth2/v3/userinfo', {
        headers: { Authorization: \`Bearer \${access_token}\` }
      });
      
      if (!response.ok) throw new Error('Failed to fetch user info from Google');
      
      const { sub: googleId, email, name, picture: avatarUrl } = await response.json();
      const { token, refreshToken } = createSessionTokens({ id: googleId, email, name, role: 'student', type: 'google' });
      
      const userRes = await store.query('SELECT * FROM app_users WHERE google_id = $1 OR email = $2', [googleId, email]);
      let user;
      
      if (userRes.rowCount > 0) {
        user = rowsToCamel(userRes.rows)[0];
        await store.query('UPDATE app_users SET name = $1, last_seen_at = $2::timestamptz, refresh_token = $3, token = $4 WHERE id = $5', 
          [name || user.name, new Date().toISOString(), refreshToken, token, user.id]);
      } else {
        const newId = \`user_\${crypto.randomUUID()}\`;
        await store.query(\`INSERT INTO app_users (id, google_id, email, name, avatar_url, role, refresh_token, token, created_at, last_seen_at) 
                           VALUES ($1, $2, $3, $4, $5, 'student', $6, $7, $8::timestamptz, $8::timestamptz)\`, 
          [newId, googleId, email, name || email.split('@')[0], avatarUrl || '', refreshToken, token, new Date().toISOString()]);
        
        const newRes = await store.query('SELECT * FROM app_users WHERE id = $1', [newId]);
        user = rowsToCamel(newRes.rows)[0];
      }

      res.cookie('refreshToken', refreshToken, {
        httpOnly: true, secure: process.env.NODE_ENV === 'production', sameSite: 'lax',
        maxAge: 7 * 24 * 60 * 60 * 1000, path: '/api/auth'
      });
      res.json(authResponseDto(sanitizeGoogleUser({ ...user, provider: 'google' }), token));
    } catch (error) {
      logger.error('auth.google_access_token_failed', { error: error.message });
      res.status(401).json(errorResponseDto('Invalid Google access token', 'UNAUTHORIZED'));
    }
  });`
  },
  {
    name: 'POST /api/auth/refresh',
    pattern: /app\.post\('\/api\/auth\/refresh', async \(req, res\) => \{[\s\S]*?const data = await store\.read\(\);[\s\S]*?await store\.write\(data\);[\s\S]*?\}\);/,
    replacement: `app.post('/api/auth/refresh', async (req, res) => {
    try {
      const refreshToken = req.cookies.refreshToken;
      if (!refreshToken) return res.status(401).json(errorResponseDto('No refresh token provided', 'UNAUTHORIZED'));
      
      const userRes = await store.query('SELECT * FROM app_users WHERE refresh_token = $1', [refreshToken]);
      if (userRes.rowCount === 0) return res.status(401).json(errorResponseDto('Invalid refresh token', 'UNAUTHORIZED'));
      const user = rowsToCamel(userRes.rows)[0];
      
      const { token } = createSessionTokens({ id: user.googleId || user.id, email: user.email, name: user.name, role: user.role, type: 'google' });
      await store.query('UPDATE app_users SET token = $1, last_seen_at = $2::timestamptz WHERE id = $3', [token, new Date().toISOString(), user.id]);
      
      res.json(authResponseDto(sanitizeGoogleUser({ ...user, provider: 'google' }), token));
    } catch (error) {
      res.status(401).json(errorResponseDto('Invalid refresh token', 'UNAUTHORIZED'));
    }
  });`
  }
];

let changedCount = 0;
for (const rep of replacements) {
  if (rep.pattern.test(content)) {
    content = content.replace(rep.pattern, rep.replacement);
    console.log(`Replaced: ${rep.name}`);
    changedCount++;
  } else {
    console.log(`Not matched: ${rep.name}`);
  }
}

// Remove legacy endpoints
const legacyStartPattern = /\/\/ Legacy unauthenticated endpoints[\s\S]*$/;
if (legacyStartPattern.test(content)) {
  content = content.replace(legacyStartPattern, `// Legacy unauthenticated endpoints removed during SQL migration\n});\n`);
  console.log('Removed legacy unauthenticated endpoints');
  changedCount++;
}

// Also remove `app.post('/api/admin/impersonate'` since we don't have password logins anyway
const impersonatePattern = /app\.post\('\/api\/admin\/impersonate', requireAuth, async \(req, res\) => \{[\s\S]*?\}\);/;
if (impersonatePattern.test(content)) {
    content = content.replace(impersonatePattern, `// Impersonate removed`);
    console.log('Removed impersonate endpoint');
    changedCount++;
}

fs.writeFileSync('index.js', content, 'utf8');
console.log(`Done! Made ${changedCount} replacements.`);
