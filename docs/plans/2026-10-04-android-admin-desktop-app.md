# DSA Android Admin Desktop Console Implementation Plan

**Goal:** Clean up all legacy desktop visualizer launcher artifacts and build a dedicated, modern Electron desktop application focused 100% on managing and controlling the DSA Android App's live data (Users, Role assignments, Feedback inbox, Learning progress, Classrooms, and Remote in-app config/announcements).

**Architecture:** Electron 43 + React 19 + Vite 8 desktop application running natively on Windows with direct authenticated connectivity to the DSA Node.js/PostgreSQL/Supabase backend. Configurable environment switcher (Local vs. Cloud API) so the admin can manage live or dev Android databases in real time.

**Tech Stack:**
- Frontend: React 19, Vite 8, Framer Motion, Lucide Icons, Chart.js
- Desktop Shell: Electron 43, electron-builder
- Backend APIs: Node.js/Express (`/api/users`, `/api/admin/users/:id/detail`, `/api/feedback`, `/api/control-center/*`)

---

### Task 1: Clean Up Stale Desktop Launchers & Legacy Scripts
**Files:**
- Replace: `Run_DSA_Desktop.bat`
- Clean/Update: `launchers/Launcher_Menu.bat`
- Remove redundant standalone browser `--app` launchers in `launchers/`

### Task 2: Enhance Desktop API Client & Environment Selector
**Files:**
- Modify: `admin-web/src/api/api.js`
- Responsibilities: Support dynamic switching between Localhost (`http://localhost:5000`) and Cloud Server (`https://dsa-visualizer-api-nu.vercel.app`), persist token & server endpoint cleanly.

### Task 3: Comprehensive Android User Management & Inspector
**Files:**
- Modify: `admin-web/src/pages/Users.jsx`
- Responsibilities:
  - Searchable, filterable list of all registered Android app users (Student, Teacher, Owner).
  - 1-click role promote/demote (Student <-> Teacher <-> Owner).
  - User detail inspection modal: view topics completed, quiz performance, last active timestamp, feedback sent.

### Task 4: User Feedback & Bug Report Triage Center
**Files:**
- Modify: `admin-web/src/pages/Feedback.jsx`
- Responsibilities:
  - Triage Android user submissions (Bug, UI Issue, Feature Request, Algorithm Difficulty rating).
  - Displays device/screen origin, algorithm ID, rating, and user message.
  - 1-click "Mark as Reviewed" and "Delete" actions.

### Task 5: Remote Android App Configuration & Announcements
**Files:**
- Create: `admin-web/src/pages/RemoteConfig.jsx`
- Modify: `admin-web/src/App.jsx`
- Modify: `admin-web/src/components/Sidebar.jsx`
- Responsibilities:
  - Broadcast Announcement: live message shown in Android app home banner.
  - Emergency Maintenance Mode toggle (locks Android app access).
  - Registration toggle (enable/disable new signups).
  - Feature Flags: toggle specific algorithm visualizers remotely.

### Task 6: Streamlined Dashboard & Single-Click Desktop Launcher
**Files:**
- Modify: `admin-web/src/pages/Dashboard.jsx`
- Modify: `Run_DSA_Desktop.bat`
- Modify: `admin-web/src/components/Topbar.jsx`
- Responsibilities:
  - Live counts of total Android users, unreviewed feedback, active classrooms.
  - 1-click `Run_DSA_Desktop.bat` to boot Vite/Electron seamlessly.
