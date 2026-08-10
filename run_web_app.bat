@echo off
echo ==========================================
echo   DSA Visualizer - Web App Launcher
echo ==========================================
echo.
echo 1. Starting Backend and Frontend...
echo 2. Opening Main App: http://localhost:3000
echo 3. Opening Admin Dashboard: http://localhost:5000/admin/
echo.

:: Wait a few seconds for server to boot, then open browsers
start http://localhost:3000
start http://localhost:5000/admin/

npm start
pause
