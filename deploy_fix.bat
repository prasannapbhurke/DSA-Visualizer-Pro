@echo off
cd /d "%~dp0"
echo Staging changes...
git add server/index.js server/api/index.js server/postgresStore.js server/vercel.json server/package.json
echo Committing...
git commit -m "fix(server): prevent ENOENT crash on Vercel read-only filesystem"
echo Pushing to origin main...
git push origin main
echo Done.
pause
