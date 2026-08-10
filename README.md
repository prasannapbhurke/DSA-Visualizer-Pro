# DSA Visualizer Pro

DSA Visualizer Pro is a full-stack learning app for Data Structures and Algorithms. It includes:

- Android app with Google sign-in, role-protected owner/teacher/student features, progress sync, feedback, classrooms, resources, and Canvas-based visualizers.
- React web client for shared visualizations and admin-style web workflows.
- Node/Express backend with Google authentication, progress APIs, feedback inboxes, classroom APIs, file/resource support, admin analytics, JSON fallback storage, and optional PostgreSQL production storage.
- Guided onboarding with role-based walkthroughs, resume-later support, and first-time learning tips across techniques and classrooms.

## Project Structure

```text
DSA/
|-- app/                         # Android app, Java + Gradle
|-- client/                      # React web visualizer
|-- server/                      # Express API, admin PWA, storage scripts
|   |-- public/admin/            # Owner/teacher admin PWA
|   |-- public/openapi.json      # API contract
|   |-- scripts/                 # Postgres migration, env check, load test
|   |-- postgres_schema.sql      # Production database schema
|   `-- postgresStore.js         # Postgres-backed store bridge
|-- docs/
|   |-- API.md                   # Human-readable API documentation
|   `-- PROJECT_DOCUMENTATION.md # Full project progress and fix notes
|-- DEPLOY_RENDER.md             # Render deployment notes
|-- PRODUCTION_BACKEND.md        # Backend production notes
|-- PRODUCTION_SCALE_PLAN.md     # Current scale/migration status
`-- CONTRIBUTING.md              # Developer workflow and standards
```

## Requirements

- Node.js 20+ for the backend and React client.
- Android Studio or Gradle wrapper for Android builds.
- Docker Desktop for image builds and Render deployment.
- Google Cloud OAuth credentials.
- PostgreSQL database for production scale, such as Supabase or Render Postgres.
- Supabase Storage or compatible cloud storage for production resource uploads.

## Environment Variables

Backend variables:

```text
PORT=5000
GOOGLE_CLIENT_ID=your-google-web-client-id.apps.googleusercontent.com
DATABASE_URL=postgresql://USER:PASSWORD@HOST:6543/postgres?pgbouncer=true
DIRECT_URL=postgresql://USER:PASSWORD@HOST:5432/postgres
SUPABASE_URL=https://YOUR_PROJECT.supabase.co
SUPABASE_SERVICE_ROLE_KEY=your-service-role-key
SUPABASE_STORAGE_BUCKET=dsa-resources
DATA_FILE=server/data.json
UPLOAD_DIR=server/uploads
```

Android local variables in `local.properties`:

```properties
GOOGLE_WEB_CLIENT_ID=your-google-web-client-id.apps.googleusercontent.com
ANDROID_REMOTE_API_BASE_URL=https://dsa-visualizer-api-latest.onrender.com
```

React client variables:

```text
REACT_APP_API_URL=http://localhost:5000
REACT_APP_GOOGLE_CLIENT_ID=your-google-web-client-id.apps.googleusercontent.com
```

## Local Setup

Install all dependencies:

```powershell
npm run install-all
```

Run the backend:

```powershell
cd server
npm install
npm start
```

Run the React client:

```powershell
cd client
npm install
npm start
```

Build the Android debug APK:

```powershell
.\gradlew.bat assembleDebug
```

The debug APK is generated at:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## PostgreSQL Migration

The backend can run in two modes:

- Local development fallback: no `DATABASE_URL`, reads/writes `server/data.json`.
- Production mode: `DATABASE_URL` set, reads/writes through `server/postgresStore.js`.

Run these commands from `server/` after setting real production environment variables:

```powershell
npm run env:check
npm run db:schema
npm run db:migrate-json
```

What each command does:

- `npm run env:check`: verifies required production variables are present.
- `npm run db:schema`: applies `server/postgres_schema.sql` to PostgreSQL.
- `npm run db:migrate-json`: imports existing `server/data.json` into PostgreSQL and skips old guest records.

## Deployment

Build and push the backend Docker image:

```powershell
docker build -t prasannapb/dsa-visualizer-api:latest server
docker push prasannapb/dsa-visualizer-api:latest
```

Render setup:

- Create a Web Service from existing Docker image.
- Image URL: `docker.io/prasannapb/dsa-visualizer-api:latest`.
- Set `GOOGLE_CLIENT_ID`, `DATABASE_URL`, Supabase variables, and any other required environment variables.
- Trigger deploy.
- Verify: `https://YOUR-RENDER-URL/status`.

Android release flow:

```powershell
.\gradlew.bat assembleRelease
```

Then install the APK on a device and test Google sign-in, progress sync, owner Manage Users, teacher classes, feedback, and resource flows.

## API Documentation

- OpenAPI JSON: `server/public/openapi.json`.
- Live endpoint when the backend is running: `/openapi.json`.
- Human-readable summary: `docs/API.md`.

Authentication uses:

```text
Authorization: Bearer <token returned by /api/auth/google>
```

## Project Documentation

- Full project history, problem/fix notes, and QA checklist: `docs/PROJECT_DOCUMENTATION.md`.
- Android compatibility notes: `ANDROID_COMPATIBILITY.md`.
- Production scale plan: `PRODUCTION_SCALE_PLAN.md`.
- Contribution and algorithm-addition guide: `CONTRIBUTING.md`.
- Recent Android UI and walkthrough changes: `PROJECT_PROGRESS_AND_FIXES.md`.

## Production Scale

Current scale work is tracked in `PRODUCTION_SCALE_PLAN.md`.

Important current status:

- Guest mode has been removed from the Android app and rejected by the backend.
- PostgreSQL schema and migration scripts exist.
- Backend supports PostgreSQL mode when `DATABASE_URL` is configured.
- Supabase Storage variables are supported for class resources.
- A 500-user load-test script exists, but should be run against staging before production.

## More Product Ideas

- Student streaks, badges, and mastery levels per topic.
- Teacher assignments with deadlines, attached resources, and completion dashboards.
- Weak-topic heatmaps for each class.
- Owner analytics for retention, most-used algorithms, most-confusing steps, and feedback trends.
- AI tutor hints that explain the next step without giving away the final answer.
- Offline Android practice mode that syncs when internet returns.
- PDF export for student progress reports.
- Replay mode where a teacher can watch a student's attempt step-by-step.
- Challenge mode with timed quizzes and leaderboard per class.
- Content issue reporting directly from an algorithm/code block.
## Developer Handoff

If another developer needs to continue this project quickly, start here:
- [DEVELOPER_HANDOFF.md](C:\Users\PRASANNA\Desktop\DSA apks\DSA x\New folder\DSA\DEVELOPER_HANDOFF.md)

## QA Review Prompt

If you want another developer or AI reviewer to perform a deep full-project audit, use:
- [QA_REVIEW_PROMPT.md](C:\Users\PRASANNA\Desktop\DSA apks\DSA x\New folder\DSA\QA_REVIEW_PROMPT.md)

## Bug Fix Priority

If another developer needs to know what to fix first, use:
- [BUG_FIX_PRIORITY.md](C:\Users\PRASANNA\Desktop\DSA apks\DSA x\New folder\DSA\BUG_FIX_PRIORITY.md)

## Full Review Package

If you want one single file that combines handoff, QA prompt, and bug-fix priorities, use:
- [FULL_REVIEW_PACKAGE.md](C:\Users\PRASANNA\Desktop\DSA apks\DSA x\New folder\DSA\FULL_REVIEW_PACKAGE.md)
