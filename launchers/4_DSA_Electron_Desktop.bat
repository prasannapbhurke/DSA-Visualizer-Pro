@echo off
title DSA Control Center (Electron Desktop App)
echo ===================================================
echo     Launching DSA Control Center (Electron App)
echo ===================================================

cd /d "%~dp0\..\admin-web"

echo Starting Vite and Electron desktop runtime...
npm run electron:dev
pause
