# DSA Android Admin Desktop Console

Native desktop administration suite for managing and monitoring the DSA Visualizer Android mobile app.

## Features
- **Android Users & Progress Directory:**
  - View all registered Android app learners.
  - 1-click Role Management: Promote to Teacher, Demote to Student, or assign Owner permissions.
  - In-depth Progress Inspection: View individual completed algorithms, quiz scores, and practice records.
- **Feedback & Bug Report Center:**
  - Real-time inbox for bug reports, difficulty feedback, and screen issues reported from Android devices.
  - Filter by category (Bug, Feedback, Difficulty, Feature) and review status.
  - 1-click "Mark as Reviewed" and deletion.
- **Remote In-App Controls & Feature Flags:**
  - Broadcast Announcements directly into the Android app home screen.
  - Emergency Maintenance Mode toggle (locks student app access during updates).
  - Enable/disable new user registrations.
  - Remote algorithm module feature flags.
- **Classrooms & Student Rosters:**
  - Inspect created classrooms, join codes, student counts, and CSV export.
- **Dual API Environment Selector:**
  - Switch with 1-click in the topbar between Local Server (`http://localhost:5000`) and Cloud API.

## Quick Start
Run from the project root:
```cmd
Run_DSA_Desktop.bat
```
Or via npm:
```cmd
npm run desktop
```
