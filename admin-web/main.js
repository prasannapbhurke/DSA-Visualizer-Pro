const { app, BrowserWindow, ipcMain, Notification, Tray, Menu, nativeImage, shell, net } = require('electron');
const path = require('path');
const http = require('http');
const fs = require('fs');
const isDev = !app.isPackaged;

const GOOGLE_CLIENT_ID = '628188239936-0m8j5kh9hht76t5edo86mkfij7f6s5t2.apps.googleusercontent.com';
const API_BASE_URL = 'https://dsa-visualizer-api-nu.vercel.app';

let mainWindow;
let tray;
let localServer;

function readRequestBody(req) {
  return new Promise((resolve, reject) => {
    let body = '';
    req.on('data', (chunk) => {
      body += chunk;
      if (body.length > 1024 * 1024) {
        req.destroy();
        reject(new Error('Request body too large'));
      }
    });
    req.on('end', () => resolve(body));
    req.on('error', reject);
  });
}

function sendHtml(res, statusCode, html) {
  res.writeHead(statusCode, { 'Content-Type': 'text/html; charset=utf-8' });
  res.end(html, 'utf-8');
}


async function postGoogleAuth(credential) {
  const requestOptions = {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ credential, idToken: credential }),
  };

  try {
    return await net.fetch(`${API_BASE_URL}/api/auth/google`, requestOptions);
  } catch (netError) {
    if (typeof fetch !== 'function') {
      throw new Error(`Unable to reach the login API: ${netError.message}`);
    }

    try {
      return await fetch(`${API_BASE_URL}/api/auth/google`, requestOptions);
    } catch (fetchError) {
      throw new Error(`Unable to reach the login API: ${fetchError.message || netError.message}`);
    }
  }
}
function desktopLoginPage() {
  return `<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>DSA Control Center Login</title>
    <script src="https://accounts.google.com/gsi/client" async defer></script>
    <style>
      body { min-height: 100vh; margin: 0; display: grid; place-items: center; background: #07111f; color: #eef6ff; font-family: Segoe UI, sans-serif; }
      main { width: min(92vw, 420px); padding: 32px; border: 1px solid rgba(151,182,225,.24); border-radius: 18px; background: #101f35; box-shadow: 0 24px 70px rgba(0,0,0,.34); }
      p { color: #9db0c7; line-height: 1.5; }
      #status { min-height: 1.5rem; color: #9db0c7; }
    </style>
  </head>
  <body>
    <main>
      <h1>DSA Control Center</h1>
      <p>Sign in here. When login completes, return to the desktop app.</p>
      <div id="g_id_onload"
        data-client_id="${GOOGLE_CLIENT_ID}"
        data-callback="handleCredentialResponse"
        data-ux_mode="popup">
      </div>
      <div class="g_id_signin" data-type="standard" data-theme="filled_black" data-size="large"></div>
      <p id="status"></p>
    </main>
    <script>
      async function handleCredentialResponse(response) {
        const status = document.getElementById('status');
        status.textContent = 'Completing sign-in...';
        try {
          const result = await fetch('/desktop-auth-callback', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ credential: response.credential })
          });
          const text = await result.text();
          document.open();
          document.write(text);
          document.close();
        } catch (error) {
          status.textContent = error.message || 'Unable to complete sign-in.';
        }
      }
    </script>
  </body>
</html>`;
}

function desktopResultPage(title, message) {
  return `<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>${title}</title>
    <style>
      body { min-height: 100vh; margin: 0; display: grid; place-items: center; background: #07111f; color: #eef6ff; font-family: Segoe UI, sans-serif; }
      main { width: min(92vw, 420px); padding: 32px; border: 1px solid rgba(151,182,225,.24); border-radius: 18px; background: #101f35; box-shadow: 0 24px 70px rgba(0,0,0,.34); }
      p { color: #9db0c7; line-height: 1.5; }
    </style>
  </head>
  <body><main><h1>${title}</h1><p>${message}</p></main></body>
</html>`;
}

async function handleDesktopAuthCallback(req, res) {
  try {
    const body = await readRequestBody(req);
    let credential;
    const contentType = req.headers['content-type'] || '';
    if (contentType.includes('application/json')) {
      credential = JSON.parse(body || '{}').credential;
    } else {
      const params = new URLSearchParams(body);
      credential = params.get('credential');
    }

    if (!credential) {
      throw new Error('Google did not return a credential.');
    }

    const authResponse = await postGoogleAuth(credential);
    const payload = await authResponse.json();
    const authData = payload.data || payload;
    const user = authData.user;
    const token = authData.token;

    if (!authResponse.ok) {
      throw new Error(payload.error?.message || 'Login failed');
    }
    if (!user || !['owner', 'teacher'].includes(user.role)) {
      throw new Error('Only owners and teachers can open the admin dashboard.');
    }
    if (!token) {
      throw new Error('Login succeeded but no session token was returned.');
    }

    if (mainWindow && !mainWindow.isDestroyed()) {
      mainWindow.webContents.send('desktop-auth-success', {
        token,
        user,
      });
      mainWindow.show();
      mainWindow.focus();
    }

    sendHtml(res, 200, desktopResultPage('Signed in', 'You can close this browser tab and return to DSA Control Center.'));
  } catch (error) {
    if (mainWindow && !mainWindow.isDestroyed()) {
      mainWindow.webContents.send('desktop-auth-error', error.message);
      mainWindow.show();
      mainWindow.focus();
    }
    sendHtml(res, 400, desktopResultPage('Login failed', error.message));
  }
}

function startLocalServer() {
  return new Promise((resolve) => {
    localServer = http.createServer(async (req, res) => {
      if (req.url.startsWith('/desktop-login')) {
        return sendHtml(res, 200, desktopLoginPage());
      }

      if (req.url.startsWith('/desktop-auth-callback') && req.method === 'POST') {
        return handleDesktopAuthCallback(req, res);
      }

      // Remove '/admin' prefix to correctly map to the dist folder
      let urlPath = req.url.replace(/^\/admin/, '');
      let filePath = path.join(__dirname, 'dist', urlPath === '/' || urlPath === '' ? '/index.html' : urlPath);

      const extname = String(path.extname(filePath)).toLowerCase();
      const mimeTypes = {
        '.html': 'text/html',
        '.js': 'text/javascript',
        '.css': 'text/css',
        '.json': 'application/json',
        '.svg': 'image/svg+xml',
      };

      const contentType = mimeTypes[extname] || 'application/octet-stream';

      fs.readFile(filePath, (error, content) => {
        if (error) {
          // Fallback to index.html for React Router
          fs.readFile(path.join(__dirname, 'dist', 'index.html'), (err, fallbackContent) => {
            res.writeHead(200, { 'Content-Type': 'text/html' });
            res.end(fallbackContent, 'utf-8');
          });
        } else {
          res.writeHead(200, { 'Content-Type': contentType });
          res.end(content, 'utf-8');
        }
      });
    });

    localServer.on('error', (error) => {
      console.error('Local server failed to start:', error);
      resolve(null);
    });

    localServer.listen(3000, 'localhost', () => {
      console.log('Local server running on http://localhost:3000');
      resolve(3000);
    });
  });
}

function createWindow(localServerPort) {
  mainWindow = new BrowserWindow({
    width: 1280,
    height: 800,
    minWidth: 900,
    minHeight: 600,
    webPreferences: {
      preload: path.join(__dirname, 'preload.js'),
      nodeIntegration: false,
      contextIsolation: true,
      nativeWindowOpen: true
    },
    icon: path.join(__dirname, 'public/favicon.svg'),
    autoHideMenuBar: true
  });

  // Strip Electron from User-Agent to bypass Google's embedded browser block for non-auth pages.
  const standardUserAgent = mainWindow.webContents.userAgent.replace(/Electron\/\S+\s/, '');
  mainWindow.webContents.userAgent = standardUserAgent;

  mainWindow.webContents.setWindowOpenHandler(({ url }) => {
    shell.openExternal(url);
    return { action: 'deny' };
  });

  if (isDev) {
    mainWindow.loadURL('http://localhost:5173/admin/', { userAgent: standardUserAgent });
  } else if (localServerPort) {
    mainWindow.loadURL(`http://localhost:${localServerPort}/admin/`, { userAgent: standardUserAgent });
  } else {
    mainWindow.loadFile(path.join(__dirname, 'dist', 'index.html'));
  }

  // Hide to tray instead of quitting when closed
  mainWindow.on('close', (event) => {
    if (!app.isQuiting) {
      event.preventDefault();
      mainWindow.hide();
    }
    return false;
  });
}

function createTray() {
  const iconPath = path.join(__dirname, 'public/favicon.svg');
  try {
    tray = new Tray(nativeImage.createFromPath(iconPath));
  } catch (e) {
    tray = new Tray(nativeImage.createEmpty());
  }

  const contextMenu = Menu.buildFromTemplate([
    { label: 'Open Control Center', click: () => mainWindow.show() },
    { type: 'separator' },
    { label: 'Quit', click: () => {
        app.isQuiting = true;
        app.quit();
      }
    }
  ]);

  tray.setToolTip('DSA Control Center');
  tray.setContextMenu(contextMenu);
  tray.on('click', () => mainWindow.show());
}

app.whenReady().then(async () => {
  let localServerPort = null;
  if (!isDev) {
    localServerPort = await startLocalServer();
  }
  createWindow(localServerPort);
  createTray();

  app.on('activate', () => {
    if (BrowserWindow.getAllWindows().length === 0) {
      createWindow(localServerPort);
    } else {
      mainWindow.show();
    }
  });
});

app.on('will-quit', () => {
  if (localServer) localServer.close();
});

ipcMain.handle('start-desktop-google-login', async () => {
  const loginUrl = isDev ? 'http://localhost:5173/admin/' : 'http://localhost:3000/desktop-login';
  await shell.openExternal(loginUrl);
});

// IPC Handler for Native Notifications
ipcMain.on('show-notification', (event, { title, body }) => {
  if (Notification.isSupported()) {
    const notif = new Notification({ title, body, icon: path.join(__dirname, 'public/favicon.svg') });
    notif.on('click', () => {
      if (mainWindow) {
        if (mainWindow.isMinimized()) mainWindow.restore();
        mainWindow.show();
      }
    });
    notif.show();
  }
});
