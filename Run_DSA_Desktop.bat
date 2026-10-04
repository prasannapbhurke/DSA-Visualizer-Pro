@echo off
title DSA Android Admin Console — Desktop
echo ===================================================
echo     Launching DSA Android Admin Desktop Console
echo ===================================================

cd /d "%~dp0\server"
netstat -ano | findstr :5000 >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [1/2] Starting DSA backend server...
    start /b "" node --env-file=.env index.js
    timeout /t 2 /nobreak >nul
) else (
    echo [1/2] Backend server is already running on port 5000.
)

cd /d "%~dp0\admin-web"
echo [2/2] Launching Native Desktop App...
start "" npm run electron:dev
exit
