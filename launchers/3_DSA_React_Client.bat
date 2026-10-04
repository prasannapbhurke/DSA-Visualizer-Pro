@echo off
title DSA React Web Visualizer Client
echo ===================================================
echo     Launching DSA React Web Client (port 3000)
echo ===================================================

cd /d "%~dp0\..\server"

:: Check if backend server is already running on port 5000
netstat -ano | findstr :5000 >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [1/2] Starting DSA backend server on port 5000...
    start /b "" node --env-file=.env index.js
    timeout /t 2 /nobreak >nul
) else (
    echo [1/2] Backend server is already running on port 5000.
)

echo [2/2] Starting React Visualizer client...
cd /d "%~dp0\..\client"

:: Start React development server
npm start
pause
