@echo off
title DSA Android Admin Console — Launcher
color 0B

:menu
cls
echo ===================================================================
echo              DSA ANDROID ADMIN DESKTOP CONSOLE
echo ===================================================================
echo.
echo   [1] Launch Native Desktop Admin Console (Electron)
echo       - Manage Android Users, Promote/Demote Roles
echo       - Android Feedback and Bug Reports Inbox
echo       - Remote App Settings, Maintenance Mode, Announcements
echo.
echo   [2] Start Local Backend Server Only (Port 5000)
echo.
echo   [3] Exit
echo.
echo ===================================================================
set /p choice="Enter your choice [1-3]: "

if "%choice%"=="1" (
    call "%~dp0..\Run_DSA_Desktop.bat"
    exit
)
if "%choice%"=="2" (
    cd /d "%~dp0..\server"
    npm start
    goto menu
)
if "%choice%"=="3" (
    exit
)

echo Invalid choice. Please try again.
timeout /t 2 >nul
goto menu
