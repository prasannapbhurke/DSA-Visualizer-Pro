@echo off
title Deploy DSA Backend to Vercel and Supabase
color 0A

echo ===================================================
echo     DSA Cloud Deployment: Supabase and Vercel
echo ===================================================
echo.

:: Clean up any accidental root .vercel folder if created
if exist "%~dp0\.vercel" (
    rmdir /s /q "%~dp0\.vercel" >nul 2>&1
)

:: Step 1: Apply Supabase Postgres Schema
echo [1/3] Applying latest database schema to Supabase...
cd /d "%~dp0server"
if exist ".env" (
    node --env-file=.env scripts/apply-postgres-schema.js
    if %ERRORLEVEL% EQU 0 (
        echo [OK] Supabase schema updated successfully!
    ) else (
        echo [INFO] Dynamic auto-migration will also verify and run on server boot.
    )
) else (
    echo [WARNING] server/.env not found. Skipping direct script migration.
)
echo.

:: Step 2: Push changes to GitHub (triggers automated Vercel CI/CD)
echo [2/3] Committing and pushing updates to GitHub...
cd /d "%~dp0"
git add .
git commit -m "feat: two-way feedback reply, device diagnostic telemetry, and android desktop console"
git push origin main
if %ERRORLEVEL% EQU 0 (
    echo [OK] Pushed to GitHub main branch! Vercel auto-build triggered.
) else (
    echo [INFO] Git push returned code %ERRORLEVEL%. Attempting direct Vercel CLI deploy...
)
echo.

:: Step 3: Direct Vercel Deployment for Backend API
echo [3/3] Deploying server to Vercel production...
cd /d "%~dp0server"
call npx -y vercel --prod --yes
if %ERRORLEVEL% EQU 0 (
    echo [OK] Vercel production deployment completed successfully!
) else (
    echo [INFO] Vercel CLI finished.
)

echo.
echo ===================================================
echo     Deployment process complete!
echo ===================================================
pause
