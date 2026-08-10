const fs = require('fs');
let content = fs.readFileSync('index.js', 'utf8');

const endpointsToRewrite = [
  {
    sig: "app.get('/api/classes', requireAuth",
    replacement: `app.get('/api/classes', requireAuth, async (req, res) => {
    try {
      const cacheKey = \`classes:\${req.authUser.role}:\${req.authUser.id}\`;
      const cached = cache.get(cacheKey);
      if (cached) return res.json(cached);

      let result = [];
      if (req.authUser.role === 'owner') {
        const classRes = await store.query('SELECT c.*, u.name as teacher_name, u.email as teacher_email FROM classes c JOIN app_users u ON c.teacher_id = u.id');
        const enrollRes = await store.query('SELECT class_id, COUNT(*) as student_count FROM enrollments GROUP BY class_id');
        const counts = Object.fromEntries(enrollRes.rows.map(r => [r.class_id, parseInt(r.student_count, 10)]));
        result = rowsToCamel(classRes.rows).map(c => sanitizeClassroom(c, { teacherName: c.teacherName || c.teacherEmail || '', studentCount: counts[c.id] || 0 }));
      } else if (req.authUser.role === 'teacher') {
        const classRes = await store.query('SELECT c.*, u.name as teacher_name, u.email as teacher_email FROM classes c JOIN app_users u ON c.teacher_id = u.id WHERE c.teacher_id = $1', [req.authUser.id]);
        const classIds = classRes.rows.map(r => r.id);
        let counts = {};
        if (classIds.length > 0) {
          const enrollRes = await store.query('SELECT class_id, COUNT(*) as student_count FROM enrollments WHERE class_id = ANY($1) GROUP BY class_id', [classIds]);
          counts = Object.fromEntries(enrollRes.rows.map(r => [r.class_id, parseInt(r.student_count, 10)]));
        }
        result = rowsToCamel(classRes.rows).map(c => sanitizeClassroom(c, { teacherName: c.teacherName || c.teacherEmail || '', studentCount: counts[c.id] || 0 }));
      } else {
        const enrollRes = await store.query('SELECT c.*, u.name as teacher_name, u.email as teacher_email, e.joined_at FROM classes c JOIN app_users u ON c.teacher_id = u.id JOIN enrollments e ON c.id = e.class_id WHERE e.student_id = $1', [req.authUser.id]);
        result = rowsToCamel(enrollRes.rows).map(c => sanitizeClassroom(c, { teacherName: c.teacherName || c.teacherEmail || '', studentCount: 0 }));
      }

      const response = classListResponseDto(result);
      cache.set(cacheKey, response);
      res.json(response);
    } catch (error) { res.status(500).json(errorResponseDto('Failed to load classes')); }
  });`
  }
];

let changedCount = 0;
for (const rep of endpointsToRewrite) {
  const startIdx = content.indexOf(rep.sig);
  if (startIdx !== -1) {
    let openBraces = 0;
    let endIdx = -1;
    let started = false;
    for (let i = startIdx; i < content.length; i++) {
      if (content[i] === '{') { openBraces++; started = true; }
      if (content[i] === '}') {
        openBraces--;
        if (started && openBraces === 0) {
          if (content.slice(i, i + 5).includes(');')) { endIdx = content.indexOf(');', i) + 2; break; }
        }
      }
    }
    if (endIdx !== -1) {
      const originalBlock = content.slice(startIdx, endIdx);
      content = content.replace(originalBlock, rep.replacement);
      console.log('Replaced: ' + rep.sig);
      changedCount++;
    } else { console.log('Could not find end of: ' + rep.sig); }
  } else { console.log('Not matched: ' + rep.sig); }
}
fs.writeFileSync('index.js', content, 'utf8');
console.log('Done! Made ' + changedCount + ' replacements.');
