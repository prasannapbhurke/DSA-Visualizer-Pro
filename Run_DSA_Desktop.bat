@echo off
title DSA Visualizer Pro — Desktop Launcher
echo ===================================================
echo     Launching DSA Visualizer Pro (Desktop App)
echo ===================================================

cd /d "%~dp0\server"

:: Check if server is already running on port 5000
netstat -ano | findstr :5000 >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [1/2] Starting DSA Pro backend server...
    start /b "" node --env-file=.env index.js
    timeout /t 2 /nobreak >nul
) else (
    echo [1/2] Backend server is already running on port 5000.
)

echo [2/2] Opening DSA Visualizer Pro standalone desktop window...

:: Check Edge or Chrome
set EDGE_PATH="C:\Program Files (x86)\Microsoft\Edge\Application\msedge.exe"
set CHROME_PATH="C:\Program Files\Google\Chrome\Application\chrome.exe"

if exist %EDGE_PATH% (
    start "" %EDGE_PATH% --app=http://localhost:5000 --window-size=1360,860
    goto done
)

if exist %CHROME_PATH% (
    start "" %CHROME_PATH% --app=http://localhost:5000 --window-size=1360,860
    goto done
)

:: Fallback default browser
start http://localhost:5000

:done
echo Done! App is now running in its dedicated desktop window.
timeout /t 2 >nul
exit
