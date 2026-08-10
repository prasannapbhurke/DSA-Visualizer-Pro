const { contextBridge, ipcRenderer } = require('electron');

contextBridge.exposeInMainWorld('electronAPI', {
  showNotification: (title, body) => ipcRenderer.send('show-notification', { title, body }),
  startDesktopGoogleLogin: () => ipcRenderer.invoke('start-desktop-google-login'),
  onDesktopAuthSuccess: (callback) => {
    const listener = (_event, payload) => callback(payload);
    ipcRenderer.on('desktop-auth-success', listener);
    return () => ipcRenderer.removeListener('desktop-auth-success', listener);
  },
  onDesktopAuthError: (callback) => {
    const listener = (_event, message) => callback(message);
    ipcRenderer.on('desktop-auth-error', listener);
    return () => ipcRenderer.removeListener('desktop-auth-error', listener);
  },
});