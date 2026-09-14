const assert = require('node:assert/strict');
const http = require('http');
const { createApp } = require('./index');

const USERS = {
  owner_token: { id: 'owner_1', email: 'admin@dsa.com', name: 'Admin Owner', role: 'owner', token: 'owner_token' },
  teacher_token: { id: 'teacher_1', email: 'teacher@dsa.com', name: 'Dr. Dijkstra', role: 'teacher', token: 'teacher_token' },
  student_token: { id: 'student_1', email: 'student@dsa.com', name: 'Student Alice', role: 'student', token: 'student_token' },
};

const createMockStore = () => {
  const preferences = new Map();
  const auditLogs = [];

  return {
    preferences,
    auditLogs,
    async findUserByToken(token) {
      return USERS[token] || null;
    },
    async query(sql, params = []) {
      const normalized = sql.replace(/\s+/g, ' ').trim().toLowerCase();

      if (normalized.includes("count(*) from app_users where role = 'teacher'")) {
        return { rowCount: 1, rows: [{ count: '4' }] };
      }
      if (normalized.includes("count(*) from app_users where role = 'student'")) {
        return { rowCount: 1, rows: [{ count: '28' }] };
      }
      if (normalized.includes('count(*) from app_users')) {
        return { rowCount: 1, rows: [{ count: '33' }] };
      }
      if (normalized.includes('count(*) from classes where teacher_id = $1')) {
        return { rowCount: 1, rows: [{ count: '2' }] };
      }
      if (normalized.includes('count(*) from classes')) {
        return { rowCount: 1, rows: [{ count: '5' }] };
      }
      if (normalized.includes('count(*) from practice_sessions')) {
        return { rowCount: 1, rows: [{ count: '120' }] };
      }
      if (normalized.includes('count(*) from class_resources')) {
        return { rowCount: 1, rows: [{ count: '14' }] };
      }
      if (normalized.includes('count(*) from feedback_entries where reviewed_at is null') || normalized.includes('reviewed_at is null')) {
        return { rowCount: 1, rows: [{ count: '2' }] };
      }
      if (normalized.includes('count(distinct e.student_id)')) {
        return { rowCount: 1, rows: [{ count: '18' }] };
      }
      if (normalized.includes('group by technique_id')) {
        return {
          rowCount: 3,
          rows: [
            { id: 'avl_tree', count: '45' },
            { id: 'quick_sort', count: '38' },
            { id: 'dijkstra', count: '22' },
          ],
        };
      }
      if (normalized.includes('from audit_logs order by created_at desc')) {
        return {
          rowCount: auditLogs.length,
          rows: auditLogs.slice(0, 5),
        };
      }
      if (normalized.includes('insert into audit_logs')) {
        auditLogs.unshift({
          id: params[0],
          action: params[1],
          actor_id: params[2],
          actor_name: params[3],
          details: params[4],
          created_at: new Date().toISOString(),
        });
        return { rowCount: 1, rows: [] };
      }
      if (normalized.includes('from app_preferences where key =')) {
        const key = params[0] || (normalized.includes("'control_center_settings'") ? 'control_center_settings' : '');
        const val = preferences.get(key);
        if (val) {
          return { rowCount: 1, rows: [{ value: val }] };
        }
        return { rowCount: 0, rows: [] };
      }
      if (normalized.includes('insert into app_preferences')) {
        const key = params[0];
        const val = params[1];
        preferences.set(key, val);
        return { rowCount: 1, rows: [] };
      }
      if (normalized.includes('from classes c left join enrollments e on c.id = e.class_id')) {
        return {
          rowCount: 2,
          rows: [
            { id: 'cls_1', name: 'Data Structures 101', join_code: 'DSA101', created_at: new Date().toISOString(), student_count: '12' },
            { id: 'cls_2', name: 'Advanced Algorithms', join_code: 'ALGO02', created_at: new Date().toISOString(), student_count: '6' },
          ],
        };
      }
      if (normalized.includes('from practice_sessions ps join enrollments e')) {
        return {
          rowCount: 1,
          rows: [
            { technique_id: 'avl_tree', score: 95, created_at: new Date().toISOString(), student_name: 'Student Alice', student_email: 'student@dsa.com' },
          ],
        };
      }
      if (normalized.includes('from classes where id = $1')) {
        return { rowCount: 1, rows: [{ teacher_id: 'teacher_1' }] };
      }
      if (normalized.includes('select 1 from classes where id = $1')) {
        return { rowCount: 1, rows: [{ exists: 1 }] };
      }
      if (normalized.includes('from app_users')) {
        return {
          rowCount: 2,
          rows: [
            { id: 'student_1', google_id: 'g_1', email: 'student@dsa.com', name: 'Student Alice', role: 'student', created_at: new Date().toISOString() },
            { id: 'teacher_1', google_id: 'g_2', email: 'teacher@dsa.com', name: 'Dr. Dijkstra', role: 'teacher', created_at: new Date().toISOString() },
          ],
        };
      }

      return { rowCount: 0, rows: [] };
    },
  };
};

const makeRequest = (baseUrl, path, { method = 'GET', token, body } = {}) =>
  new Promise((resolve, reject) => {
    const url = new URL(path, baseUrl);
    const headers = {
      'Content-Type': 'application/json',
    };
    if (token) {
      headers.Authorization = `Bearer ${token}`;
    }

    const req = http.request(
      url,
      {
        method,
        headers,
      },
      (res) => {
        let raw = '';
        res.on('data', (chunk) => {
          raw += chunk;
        });
        res.on('end', () => {
          let json = null;
          try {
            json = JSON.parse(raw);
          } catch (_) {
            json = raw;
          }
          resolve({ status: res.statusCode, body: json });
        });
      }
    );

    req.on('error', reject);
    if (body) {
      req.write(typeof body === 'string' ? body : JSON.stringify(body));
    }
    req.end();
  });

async function runTests() {
  console.log('--- Starting DSA Control Centre Integration Tests ---');
  const store = createMockStore();
  const app = createApp({
    store,
    logger: { info() {}, error() {} },
  });

  const server = await new Promise((resolve) => {
    const s = app.listen(0, () => resolve(s));
  });
  const port = server.address().port;
  const baseUrl = `http://127.0.0.1:${port}`;

  try {
    // Test 1: Unauthenticated request should return 401
    {
      const res = await makeRequest(baseUrl, '/api/control-center/overview');
      assert.equal(res.status, 401, 'Unauthenticated request must return 401');
      assert.equal(res.body.error.code, 'UNAUTHORIZED');
      console.log('✓ Test 1: 401 Unauthorized for unauthenticated overview');
    }

    // Test 2: Student role should return 403 Forbidden
    {
      const res = await makeRequest(baseUrl, '/api/control-center/overview', {
        token: 'student_token',
      });
      assert.equal(res.status, 403, 'Student access must be forbidden');
      assert.equal(res.body.error.code, 'FORBIDDEN');
      console.log('✓ Test 2: 403 Forbidden for student role on control center');
    }

    // Test 3: Owner overview should return full platform stats, settings, and top algorithms
    {
      const res = await makeRequest(baseUrl, '/api/control-center/overview', {
        token: 'owner_token',
      });
      assert.equal(res.status, 200);
      assert.ok(res.body.data && res.body.data.overview, 'Response must contain overview object');
      const overview = res.body.data.overview;
      assert.equal(overview.role, 'owner');
      assert.equal(overview.stats.totalUsers, 33);
      assert.equal(overview.stats.totalTeachers, 4);
      assert.equal(overview.stats.totalStudents, 28);
      assert.equal(overview.stats.totalClasses, 5);
      assert.equal(overview.topAlgorithms.length, 3);
      assert.equal(overview.topAlgorithms[0].id, 'avl_tree');
      assert.ok(overview.settings.experimentalVisualizers.avlTree);
      console.log('✓ Test 3: 200 OK for Owner overview with full platform metrics');
    }

    // Test 4: Teacher overview should return teacher-scoped classes and student activity
    {
      const res = await makeRequest(baseUrl, '/api/control-center/overview', {
        token: 'teacher_token',
      });
      assert.equal(res.status, 200);
      const overview = res.body.data.overview;
      assert.equal(overview.role, 'teacher');
      assert.equal(overview.stats.totalClasses, 2);
      assert.equal(overview.stats.totalStudents, 18);
      assert.equal(overview.classes.length, 2);
      assert.equal(overview.classes[0].name, 'Data Structures 101');
      assert.equal(overview.recentStudentActivity.length, 1);
      console.log('✓ Test 4: 200 OK for Teacher overview with scoped classrooms');
    }

    // Test 5: Teacher cannot access Owner-only settings
    {
      const res = await makeRequest(baseUrl, '/api/control-center/settings', {
        token: 'teacher_token',
      });
      assert.equal(res.status, 403, 'Teacher cannot access settings');
      console.log('✓ Test 5: 403 Forbidden for teacher accessing platform settings');
    }

    // Test 6: Owner can read and update platform feature flags & announcements
    {
      const getRes = await makeRequest(baseUrl, '/api/control-center/settings', {
        token: 'owner_token',
      });
      assert.equal(getRes.status, 200);
      assert.ok(getRes.body.data.settings);

      const updateRes = await makeRequest(baseUrl, '/api/control-center/settings', {
        method: 'PUT',
        token: 'owner_token',
        body: {
          settings: {
            announcement: 'Maintenance scheduled for Sunday 2 AM UTC',
            maintenanceMode: false,
            registrationEnabled: true,
            experimentalVisualizers: {
              avlTree: true,
              huffmanCoding: true,
              trie: true,
              dijkstra: true,
              unionFind: true,
              redBlackTree: false,
            },
          },
        },
      });
      assert.equal(updateRes.status, 200);
      assert.equal(updateRes.body.data.settings.announcement, 'Maintenance scheduled for Sunday 2 AM UTC');
      assert.equal(store.auditLogs.length, 1, 'Audit log must be created on settings update');
      assert.equal(store.auditLogs[0].action, 'update_control_center_settings');
      console.log('✓ Test 6: Owner successfully updated platform settings with audit log');
    }

    // Test 7: Teacher broadcasts a live session and reads it back
    {
      const livePostRes = await makeRequest(baseUrl, '/api/control-center/live-session', {
        method: 'POST',
        token: 'teacher_token',
        body: {
          session: {
            classId: 'cls_1',
            techniqueId: 'avl_tree',
            currentStep: 2,
            playbackSpeed: 1.5,
            isPaused: true,
            activeNodeIds: [10, 20],
            statusMessage: 'Demonstrating Right-Left rotation',
          },
        },
      });
      assert.equal(livePostRes.status, 201);
      assert.equal(livePostRes.body.data.session.techniqueId, 'avl_tree');
      assert.equal(livePostRes.body.data.session.currentStep, 2);

      // Student reads the live session state
      const liveGetRes = await makeRequest(baseUrl, '/api/control-center/live-session/cls_1', {
        token: 'student_token',
      });
      assert.equal(liveGetRes.status, 200);
      assert.ok(liveGetRes.body.data.session);
      assert.equal(liveGetRes.body.data.session.statusMessage, 'Demonstrating Right-Left rotation');
      console.log('✓ Test 7: Live session broadcasted by teacher and verified by student');
    }

    // Test 8: Web companion dashboard served at /admin
    {
      const webRes = await makeRequest(baseUrl, '/admin/');
      assert.equal(webRes.status, 200, 'Web companion dashboard should return 200 OK');
      assert.ok(typeof webRes.body === 'string' && webRes.body.includes('DSA Control Centre'), 'HTML must contain dashboard title');
      console.log('✓ Test 8: Desktop Web companion dashboard served with 200 OK at /admin/');
    }

    console.log('\nAll Control Centre tests PASSED successfully! 🚀');
    process.exit(0);
  } finally {
    server.close();
  }
}

runTests().catch((err) => {
  console.error('Test failed:', err);
  process.exit(1);
});
