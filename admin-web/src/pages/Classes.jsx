import { useEffect, useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { FileSpreadsheet, Users, ChevronDown, ChevronUp, Filter, ArrowUpDown } from 'lucide-react';
import { api, getToken, API_BASE_URL } from '../api/api';

const formatDate = (value) => {
  if (!value) return '-';
  const date = new Date(value);
  return Number.isNaN(date.getTime()) ? '-' : date.toLocaleDateString();
};

const Classes = () => {
  const [classes, setClasses] = useState([]);
  const [error, setError] = useState('');
  const [expandedClassId, setExpandedClassId] = useState(null);
  const [studentsByClass, setStudentsByClass] = useState({});
  const [loadingStudents, setLoadingStudents] = useState({});
  const [batchFilter, setBatchFilter] = useState({});
  const [sortOption, setSortOption] = useState({});
  const [exporting, setExporting] = useState({});

  useEffect(() => {
    api('/api/classes')
      .then((res) => setClasses(res.data?.classes || []))
      .catch((err) => setError(err.message));
  }, []);

  const loadStudents = async (classId) => {
    if (loadingStudents[classId]) return;
    setLoadingStudents((prev) => ({ ...prev, [classId]: true }));
    try {
      const res = await api(`/api/classes/${classId}/students`);
      setStudentsByClass((prev) => ({ ...prev, [classId]: res.data?.students || [] }));
    } catch (err) {
      console.error('Failed to load students:', err);
    } finally {
      setLoadingStudents((prev) => ({ ...prev, [classId]: false }));
    }
  };

  const toggleExpand = (classId) => {
    if (expandedClassId === classId) {
      setExpandedClassId(null);
    } else {
      setExpandedClassId(classId);
      if (!studentsByClass[classId]) {
        loadStudents(classId);
      }
    }
  };

  const handleExportCsv = async (classroom) => {
    setExporting((prev) => ({ ...prev, [classroom.id]: true }));
    try {
      const token = getToken();
      const currentSort = sortOption[classroom.id] || 'roll_number';
      const currentBatch = batchFilter[classroom.id] || '';
      const params = new URLSearchParams();
      if (currentSort) params.append('sortBy', currentSort);
      if (currentBatch) params.append('batch', currentBatch);

      const endpoint = `/api/classes/${classroom.id}/export?${params.toString()}`;
      const url = endpoint.startsWith('http') ? endpoint : `${API_BASE_URL}${endpoint}`;

      const response = await fetch(url, {
        headers: {
          ...(token ? { Authorization: `Bearer ${token}` } : {}),
        },
      });

      if (!response.ok) {
        throw new Error(`Export failed with status ${response.status}`);
      }

      const blob = await response.blob();
      const downloadUrl = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = downloadUrl;
      const safeName = (classroom.name || 'Class').replace(/[^a-zA-Z0-9_-]/g, '_');
      a.download = `${safeName}_${classroom.joinCode || 'roster'}_marksheet.csv`;
      document.body.appendChild(a);
      a.click();
      a.remove();
      window.URL.revokeObjectURL(downloadUrl);
    } catch (err) {
      alert('Export failed: ' + err.message);
    } finally {
      setExporting((prev) => ({ ...prev, [classroom.id]: false }));
    }
  };

  return (
    <motion.section initial={{ opacity: 0, x: -20 }} animate={{ opacity: 1, x: 0 }} className="section active-section">
      <div className="panel">
        <div className="panel-heading">
          <h2>Class Management & Student Rosters</h2>
          <span>{classes.length} classes</span>
        </div>
        {error && <p className="error-text">{error}</p>}
        <div className="class-list">
          {classes.map((classroom) => {
            const isExpanded = expandedClassId === classroom.id;
            const students = studentsByClass[classroom.id] || [];
            const isLoading = loadingStudents[classroom.id];
            const currentBatch = batchFilter[classroom.id] || '';
            const currentSort = sortOption[classroom.id] || 'roll_number';

            const uniqueBatches = Array.from(
              new Set(students.map((s) => s.studentBatch).filter((b) => Boolean(b && b.trim())))
            ).sort();

            let filteredStudents = students.filter((s) => {
              if (!currentBatch) return true;
              return (s.studentBatch || '').toLowerCase() === currentBatch.toLowerCase();
            });

            filteredStudents.sort((a, b) => {
              if (currentSort === 'name') {
                return (a.user?.displayName || '').localeCompare(b.user?.displayName || '');
              }
              const rollA = a.rollNumber || '';
              const rollB = b.rollNumber || '';
              if (!rollA && !rollB) return 0;
              if (!rollA) return 1;
              if (!rollB) return -1;
              return rollA.localeCompare(rollB, undefined, { numeric: true, sensitivity: 'base' });
            });

            return (
              <article className="class-card" key={classroom.id} style={{ padding: '18px', marginBottom: '16px' }}>
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', flexWrap: 'wrap', gap: '12px' }}>
                  <div style={{ flex: 1, minWidth: '240px' }}>
                    <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
                      <h3 style={{ margin: 0, fontSize: '1.25rem' }}>{classroom.name}</h3>
                      <span
                        style={{
                          background: 'rgba(99, 102, 241, 0.15)',
                          color: '#818cf8',
                          padding: '2px 10px',
                          borderRadius: '12px',
                          fontSize: '0.8rem',
                          fontWeight: 600,
                          letterSpacing: '1px',
                          border: '1px solid rgba(99, 102, 241, 0.3)',
                        }}
                      >
                        CODE: {classroom.joinCode || '-'}
                      </span>
                    </div>
                    <p className="meta-line" style={{ marginTop: '8px' }}>
                      <span>Teacher: {classroom.teacherName || classroom.teacherId || '-'}</span>
                      <span>Enrolled Students: {classroom.studentCount ?? students.length}</span>
                      <span>Created: {formatDate(classroom.createdAt)}</span>
                    </p>
                    {classroom.description && <p className="login-copy" style={{ margin: '6px 0 0 0' }}>{classroom.description}</p>}
                  </div>

                  <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
                    <button
                      type="button"
                      onClick={() => handleExportCsv(classroom)}
                      disabled={exporting[classroom.id]}
                      style={{
                        display: 'inline-flex',
                        alignItems: 'center',
                        gap: '6px',
                        background: 'linear-gradient(135deg, #059669 0%, #10b981 100%)',
                        color: '#fff',
                        border: 'none',
                        borderRadius: '8px',
                        padding: '8px 14px',
                        fontSize: '0.85rem',
                        fontWeight: 600,
                        cursor: 'pointer',
                        boxShadow: '0 2px 8px rgba(16, 185, 129, 0.25)',
                      }}
                      title="Download full college marks sheet and attendance report"
                    >
                      <FileSpreadsheet size={16} />
                      {exporting[classroom.id] ? 'Exporting...' : 'Export Excel / CSV'}
                    </button>

                    <button
                      type="button"
                      onClick={() => toggleExpand(classroom.id)}
                      style={{
                        display: 'inline-flex',
                        alignItems: 'center',
                        gap: '4px',
                        background: 'rgba(255, 255, 255, 0.08)',
                        color: '#e2e8f0',
                        border: '1px solid rgba(255, 255, 255, 0.15)',
                        borderRadius: '8px',
                        padding: '8px 12px',
                        fontSize: '0.85rem',
                        cursor: 'pointer',
                      }}
                    >
                      <Users size={16} />
                      {isExpanded ? <ChevronUp size={16} /> : <ChevronDown size={16} />}
                    </button>
                  </div>
                </div>

                <AnimatePresence>
                  {isExpanded && (
                    <motion.div
                      initial={{ opacity: 0, height: 0 }}
                      animate={{ opacity: 1, height: 'auto' }}
                      exit={{ opacity: 0, height: 0 }}
                      transition={{ duration: 0.25 }}
                      style={{ marginTop: '18px', paddingTop: '16px', borderTop: '1px solid rgba(255, 255, 255, 0.1)' }}
                    >
                      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexWrap: 'wrap', gap: '10px', marginBottom: '14px' }}>
                        <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
                          <span style={{ fontWeight: 600, fontSize: '0.95rem', color: '#f1f5f9' }}>
                            Student Roster ({filteredStudents.length}{currentBatch ? ` in ${currentBatch}` : ''})
                          </span>
                        </div>

                        <div style={{ display: 'flex', alignItems: 'center', gap: '10px', flexWrap: 'wrap' }}>
                          {uniqueBatches.length > 0 && (
                            <div style={{ display: 'inline-flex', alignItems: 'center', gap: '6px' }}>
                              <Filter size={14} color="#94a3b8" />
                              <select
                                value={currentBatch}
                                onChange={(e) => setBatchFilter((prev) => ({ ...prev, [classroom.id]: e.target.value }))}
                                style={{
                                  background: 'rgba(15, 23, 42, 0.8)',
                                  color: '#f8fafc',
                                  border: '1px solid rgba(255, 255, 255, 0.15)',
                                  borderRadius: '6px',
                                  padding: '4px 8px',
                                  fontSize: '0.8rem',
                                }}
                              >
                                <option value="">All Batches</option>
                                {uniqueBatches.map((b) => (
                                  <option key={b} value={b}>
                                    {b}
                                  </option>
                                ))}
                              </select>
                            </div>
                          )}

                          <div style={{ display: 'inline-flex', alignItems: 'center', gap: '6px' }}>
                            <ArrowUpDown size={14} color="#94a3b8" />
                            <select
                              value={currentSort}
                              onChange={(e) => setSortOption((prev) => ({ ...prev, [classroom.id]: e.target.value }))}
                              style={{
                                background: 'rgba(15, 23, 42, 0.8)',
                                color: '#f8fafc',
                                border: '1px solid rgba(255, 255, 255, 0.15)',
                                borderRadius: '6px',
                                padding: '4px 8px',
                                fontSize: '0.8rem',
                              }}
                            >
                              <option value="roll_number">Sort by Roll Number</option>
                              <option value="name">Sort by Student Name</option>
                            </select>
                          </div>
                        </div>
                      </div>

                      {isLoading ? (
                        <p style={{ color: '#94a3b8', fontSize: '0.9rem' }}>Loading students...</p>
                      ) : filteredStudents.length === 0 ? (
                        <div style={{ padding: '16px', background: 'rgba(255, 255, 255, 0.02)', borderRadius: '8px', textAlign: 'center', color: '#94a3b8' }}>
                          No students found {currentBatch ? `in batch "${currentBatch}"` : 'in this class'}.
                        </div>
                      ) : (
                        <div style={{ overflowX: 'auto' }}>
                          <table style={{ width: '100%', borderCollapse: 'collapse', fontSize: '0.85rem' }}>
                            <thead>
                              <tr style={{ textAlign: 'left', borderBottom: '1px solid rgba(255, 255, 255, 0.1)', color: '#94a3b8' }}>
                                <th style={{ padding: '8px 10px' }}>Roll No / PRN</th>
                                <th style={{ padding: '8px 10px' }}>Student Name</th>
                                <th style={{ padding: '8px 10px' }}>Batch</th>
                                <th style={{ padding: '8px 10px' }}>Modules Done</th>
                                <th style={{ padding: '8px 10px' }}>Practice Sessions</th>
                                <th style={{ padding: '8px 10px' }}>Streak</th>
                                <th style={{ padding: '8px 10px' }}>Enrolled</th>
                              </tr>
                            </thead>
                            <tbody>
                              {filteredStudents.map((stu) => (
                                <tr
                                  key={stu.user?.id || stu.joinedAt}
                                  style={{ borderBottom: '1px solid rgba(255, 255, 255, 0.05)' }}
                                >
                                  <td style={{ padding: '8px 10px' }}>
                                    <span
                                      style={{
                                        display: 'inline-block',
                                        background: stu.rollNumber ? 'rgba(56, 189, 248, 0.15)' : 'rgba(255, 255, 255, 0.05)',
                                        color: stu.rollNumber ? '#38bdf8' : '#64748b',
                                        padding: '2px 8px',
                                        borderRadius: '4px',
                                        fontWeight: 600,
                                        fontSize: '0.8rem',
                                        fontFamily: 'monospace',
                                      }}
                                    >
                                      {stu.rollNumber || 'Not set'}
                                    </span>
                                  </td>
                                  <td style={{ padding: '8px 10px', color: '#f8fafc', fontWeight: 500 }}>
                                    {stu.user?.displayName || stu.user?.name || stu.user?.email || 'Student'}
                                  </td>
                                  <td style={{ padding: '8px 10px' }}>
                                    {stu.studentBatch ? (
                                      <span
                                        style={{
                                          background: 'rgba(168, 85, 247, 0.15)',
                                          color: '#c084fc',
                                          padding: '2px 8px',
                                          borderRadius: '4px',
                                          fontSize: '0.78rem',
                                        }}
                                      >
                                        {stu.studentBatch}
                                      </span>
                                    ) : (
                                      <span style={{ color: '#64748b' }}>-</span>
                                    )}
                                  </td>
                                  <td style={{ padding: '8px 10px', color: '#10b981', fontWeight: 600 }}>
                                    {stu.summary?.completedTechniques ?? 0} / {stu.summary?.totalTechniquesStarted ?? 0}
                                  </td>
                                  <td style={{ padding: '8px 10px' }}>{stu.summary?.totalPracticeSessions ?? 0}</td>
                                  <td style={{ padding: '8px 10px' }}>{stu.summary?.streakDays ?? 0} days</td>
                                  <td style={{ padding: '8px 10px', color: '#94a3b8' }}>{formatDate(stu.joinedAt)}</td>
                                </tr>
                              ))}
                            </tbody>
                          </table>
                        </div>
                      )}
                    </motion.div>
                  )}
                </AnimatePresence>
              </article>
            );
          })}
          {classes.length === 0 && <div className="meta-line">No classes found.</div>}
        </div>
      </div>
    </motion.section>
  );
};

export default Classes;
