import { useEffect, useMemo, useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { api } from '../api/api';

const getSeenAt = (entry) => entry.lastSeenAt || entry.last_seen_at || entry.createdAt || entry.created_at || '';
const getCreatedAt = (entry) => entry.createdAt || entry.created_at || '';
const getName = (entry) => entry.name || entry.displayName || '';

const formatDate = (value) => {
  if (!value) return 'Never';
  const date = new Date(value);
  return Number.isNaN(date.getTime()) ? 'Never' : date.toLocaleString();
};

const views = [
  { id: 'all', label: 'All Users' },
  { id: 'student', label: 'Students' },
  { id: 'teacher', label: 'Teachers' },
  { id: 'owner', label: 'Owners' },
];

const sortOptions = [
  { id: 'recent', label: 'Recently active' },
  { id: 'newest', label: 'Newest joined' },
  { id: 'name', label: 'Name A-Z' },
  { id: 'email', label: 'Email A-Z' },
  { id: 'role', label: 'Role' },
];

const Users = ({ user }) => {
  const [users, setUsers] = useState([]);
  const [error, setError] = useState('');
  const [successMsg, setSuccessMsg] = useState('');
  const [activeView, setActiveView] = useState('all');
  const [sortBy, setSortBy] = useState('recent');
  const [query, setQuery] = useState('');
  const [updatingId, setUpdatingId] = useState(null);

  // Inspector modal state
  const [selectedUser, setSelectedUser] = useState(null);
  const [userDetail, setUserDetail] = useState(null);
  const [loadingDetail, setLoadingDetail] = useState(false);

  const fetchUsers = () => {
    if (user?.role !== 'owner') return;
    api('/api/users')
      .then((res) => {
        setUsers(res.data?.users || []);
        setError('');
      })
      .catch((err) => setError(err.message));
  };

  useEffect(() => {
    fetchUsers();
  }, [user]);

  const changeUserRole = async (targetUser, newRole) => {
    if (targetUser.id === user?.id) {
      alert('You cannot modify your own role.');
      return;
    }

    const confirmed = window.confirm(
      `Are you sure you want to change ${getName(targetUser) || targetUser.email}'s role to "${newRole.toUpperCase()}"?`
    );
    if (!confirmed) return;

    setUpdatingId(targetUser.id);
    try {
      await api(`/api/users/${targetUser.id}/role`, {
        method: 'PUT',
        body: JSON.stringify({ newRole }),
      });
      setSuccessMsg(`Successfully changed role to ${newRole}`);
      setTimeout(() => setSuccessMsg(''), 4000);
      setUsers((prev) =>
        prev.map((u) => (u.id === targetUser.id ? { ...u, role: newRole } : u))
      );
    } catch (err) {
      setError(err.message || 'Failed to update user role');
    } finally {
      setUpdatingId(null);
    }
  };

  const inspectUser = async (targetUser) => {
    setSelectedUser(targetUser);
    setUserDetail(null);
    setLoadingDetail(true);
    try {
      const res = await api(`/api/admin/users/${targetUser.id}/detail`);
      setUserDetail(res.data || res);
    } catch (err) {
      setError(`Failed to inspect user: ${err.message}`);
    } finally {
      setLoadingDetail(false);
    }
  };

  const visibleUsers = useMemo(() => {
    const normalizedQuery = query.trim().toLowerCase();

    const filtered = users.filter((entry) => {
      if (['owner', 'teacher', 'student'].includes(activeView) && entry.role !== activeView) {
        return false;
      }
      if (!normalizedQuery) return true;
      return [entry.email, getName(entry), entry.role]
        .filter(Boolean)
        .some((value) => String(value).toLowerCase().includes(normalizedQuery));
    });

    return filtered.sort((left, right) => {
      if (sortBy === 'newest') {
        return new Date(getCreatedAt(right)).getTime() - new Date(getCreatedAt(left)).getTime();
      }
      if (sortBy === 'name') {
        return getName(left).localeCompare(getName(right));
      }
      if (sortBy === 'email') {
        return String(left.email || '').localeCompare(String(right.email || ''));
      }
      if (sortBy === 'role') {
        return String(left.role || '').localeCompare(String(right.role || ''));
      }
      return new Date(getSeenAt(right)).getTime() - new Date(getSeenAt(left)).getTime();
    });
  }, [activeView, query, sortBy, users]);

  if (user?.role !== 'owner') {
    return <div className="meta-line">Access restricted to Owners.</div>;
  }

  return (
    <motion.section initial={{ opacity: 0, x: -20 }} animate={{ opacity: 1, x: 0 }} className="section active-section">
      <div className="panel">
        <div className="panel-heading compact-heading">
          <div>
            <h2>Android Users Management</h2>
            <span>{visibleUsers.length} shown of {users.length} registered app users</span>
          </div>
          <button type="button" className="action-btn" onClick={fetchUsers}>
            Refresh Users
          </button>
        </div>

        {successMsg && <p className="success-banner" style={{ color: '#10b981', padding: '8px 12px', background: 'rgba(16,185,129,0.1)', borderRadius: '8px', marginBottom: '12px' }}>{successMsg}</p>}
        {error && <p className="error-text" style={{ color: '#ef4444', marginBottom: '12px' }}>{error}</p>}

        <div className="filter-strip user-toolbar">
          {views.map((view) => (
            <button
              key={view.id}
              className={`filter-chip ${activeView === view.id ? 'active' : ''}`}
              type="button"
              onClick={() => setActiveView(view.id)}
            >
              {view.label}
            </button>
          ))}
        </div>

        <div className="panel-controls user-controls" style={{ display: 'flex', gap: '12px', alignItems: 'center', marginBottom: '16px' }}>
          <input
            className="search-input"
            type="search"
            placeholder="Search by name, email, or role..."
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            style={{ flex: 1, padding: '10px 14px', borderRadius: '8px', border: '1px solid rgba(255,255,255,0.15)', background: '#0e1726', color: '#fff' }}
          />
          <label className="select-label compact-input" style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
            <span>Sort:</span>
            <select
              className="select-input"
              value={sortBy}
              onChange={(e) => setSortBy(e.target.value)}
              style={{ padding: '8px 12px', borderRadius: '8px', background: '#0e1726', color: '#fff', border: '1px solid rgba(255,255,255,0.15)' }}
            >
              {sortOptions.map((opt) => (
                <option key={opt.id} value={opt.id}>{opt.label}</option>
              ))}
            </select>
          </label>
        </div>

        <div className="table-wrap">
          <table className="data-table">
            <thead>
              <tr>
                <th>User</th>
                <th>Role</th>
                <th>Joined</th>
                <th>Last Active</th>
                <th style={{ textAlign: 'right' }}>Actions</th>
              </tr>
            </thead>
            <tbody>
              {visibleUsers.map((entry) => {
                const isCurrent = entry.id === user?.id;
                return (
                  <tr key={entry.id}>
                    <td>
                      <div style={{ display: 'flex', flexDirection: 'column' }}>
                        <strong>{getName(entry) || 'Android Student'}</strong>
                        <small style={{ color: '#94a3b8' }}>{entry.email}</small>
                      </div>
                    </td>
                    <td>
                      <span className={`role-badge ${entry.role}`} style={{ padding: '4px 8px', borderRadius: '6px', fontSize: '0.8rem', textTransform: 'capitalize' }}>
                        {entry.role}
                      </span>
                    </td>
                    <td>{formatDate(getCreatedAt(entry))}</td>
                    <td>{formatDate(getSeenAt(entry))}</td>
                    <td style={{ textAlign: 'right' }}>
                      <div style={{ display: 'flex', gap: '8px', justifyContent: 'flex-end', alignItems: 'center' }}>
                        <button
                          type="button"
                          className="row-btn"
                          onClick={() => inspectUser(entry)}
                          style={{ padding: '6px 12px', fontSize: '0.85rem' }}
                        >
                          Inspect Data
                        </button>
                        
                        {!isCurrent && (
                          <select
                            disabled={updatingId === entry.id}
                            value={entry.role}
                            onChange={(e) => changeUserRole(entry, e.target.value)}
                            style={{ padding: '4px 8px', borderRadius: '6px', background: '#1e293b', color: '#fff', border: '1px solid rgba(255,255,255,0.2)', fontSize: '0.85rem' }}
                          >
                            <option value="student">Student</option>
                            <option value="teacher">Teacher</option>
                            <option value="owner">Owner</option>
                          </select>
                        )}
                      </div>
                    </td>
                  </tr>
                );
              })}
              {visibleUsers.length === 0 && (
                <tr>
                  <td colSpan="5" className="meta-line" style={{ textAlign: 'center', padding: '24px' }}>
                    No Android app users matched your search criteria.
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>

      {/* User Progress & Activity Inspection Modal */}
      <AnimatePresence>
        {selectedUser && (
          <div
            className="modal-backdrop"
            style={{
              position: 'fixed',
              top: 0,
              left: 0,
              right: 0,
              bottom: 0,
              background: 'rgba(0,0,0,0.7)',
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
              zIndex: 9999,
              backdropFilter: 'blur(4px)',
            }}
            onClick={() => setSelectedUser(null)}
          >
            <motion.div
              initial={{ scale: 0.9, opacity: 0 }}
              animate={{ scale: 1, opacity: 1 }}
              exit={{ scale: 0.9, opacity: 0 }}
              className="panel"
              style={{
                width: 'min(92vw, 700px)',
                maxHeight: '85vh',
                overflowY: 'auto',
                background: '#111c2e',
                border: '1px solid rgba(255,255,255,0.15)',
                boxShadow: '0 20px 50px rgba(0,0,0,0.6)',
                padding: '24px',
                borderRadius: '16px',
              }}
              onClick={(e) => e.stopPropagation()}
            >
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '16px', borderBottom: '1px solid rgba(255,255,255,0.1)', paddingBottom: '12px' }}>
                <div>
                  <h2 style={{ margin: 0, fontSize: '1.4rem' }}>{getName(selectedUser) || 'Android User'}</h2>
                  <span style={{ color: '#94a3b8' }}>{selectedUser.email}</span>
                </div>
                <button
                  type="button"
                  className="ghost-btn"
                  onClick={() => setSelectedUser(null)}
                  style={{ fontSize: '1.2rem', padding: '4px 10px' }}
                >
                  ✕
                </button>
              </div>

              {loadingDetail ? (
                <div style={{ textAlign: 'center', padding: '40px', color: '#94a3b8' }}>
                  Loading Android user learning history...
                </div>
              ) : (
                <div style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}>
                  {/* Account Metadata Summary */}
                  <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(180px, 1fr))', gap: '12px' }}>
                    <div style={{ background: '#1e293b', padding: '12px', borderRadius: '10px' }}>
                      <small style={{ color: '#94a3b8' }}>Role</small>
                      <div style={{ fontWeight: 'bold', textTransform: 'capitalize', color: '#38bdf8' }}>{selectedUser.role}</div>
                    </div>
                    <div style={{ background: '#1e293b', padding: '12px', borderRadius: '10px' }}>
                      <small style={{ color: '#94a3b8' }}>Topics Completed</small>
                      <div style={{ fontWeight: 'bold', fontSize: '1.2rem' }}>
                        {userDetail?.progressEntries?.length || 0}
                      </div>
                    </div>
                    <div style={{ background: '#1e293b', padding: '12px', borderRadius: '10px' }}>
                      <small style={{ color: '#94a3b8' }}>Practice Sessions</small>
                      <div style={{ fontWeight: 'bold', fontSize: '1.2rem' }}>
                        {userDetail?.practiceSessions?.length || 0}
                      </div>
                    </div>
                    <div style={{ background: '#1e293b', padding: '12px', borderRadius: '10px' }}>
                      <small style={{ color: '#94a3b8' }}>Feedback Submitted</small>
                      <div style={{ fontWeight: 'bold', fontSize: '1.2rem' }}>
                        {userDetail?.feedbackEntries?.length || 0}
                      </div>
                    </div>
                  </div>

                  {/* Completed Learning Modules */}
                  <div>
                    <h3 style={{ fontSize: '1.1rem', marginBottom: '10px', color: '#e2e8f0' }}>Completed Learning Modules & Quizzes</h3>
                    {userDetail?.progressEntries && userDetail.progressEntries.length > 0 ? (
                      <div style={{ maxHeight: '200px', overflowY: 'auto', background: '#0b1320', borderRadius: '8px', padding: '10px' }}>
                        {userDetail.progressEntries.map((p, idx) => (
                          <div key={idx} style={{ display: 'flex', justifyContent: 'space-between', padding: '8px 0', borderBottom: '1px solid rgba(255,255,255,0.06)' }}>
                            <span><strong>{p.techniqueId || p.technique_id}</strong></span>
                            <span style={{ color: '#10b981' }}>{p.status || 'Completed'} {p.score ? `(Score: ${p.score})` : ''}</span>
                          </div>
                        ))}
                      </div>
                    ) : (
                      <p style={{ color: '#64748b', margin: 0, fontStyle: 'italic' }}>No completed topics recorded yet in the Android app.</p>
                    )}
                  </div>

                  {/* User's Submitted Feedback */}
                  <div>
                    <h3 style={{ fontSize: '1.1rem', marginBottom: '10px', color: '#e2e8f0' }}>Feedback from this User</h3>
                    {userDetail?.feedbackEntries && userDetail.feedbackEntries.length > 0 ? (
                      <div style={{ maxHeight: '180px', overflowY: 'auto', background: '#0b1320', borderRadius: '8px', padding: '10px' }}>
                        {userDetail.feedbackEntries.map((f, idx) => (
                          <div key={idx} style={{ padding: '8px 0', borderBottom: '1px solid rgba(255,255,255,0.06)' }}>
                            <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '4px' }}>
                              <span style={{ color: '#f59e0b', fontWeight: 'bold' }}>{f.type || 'Feedback'}</span>
                              <small style={{ color: '#94a3b8' }}>{formatDate(f.createdAt || f.created_at)}</small>
                            </div>
                            <p style={{ margin: 0, color: '#cbd5e1', fontSize: '0.9rem' }}>{f.message}</p>
                          </div>
                        ))}
                      </div>
                    ) : (
                      <p style={{ color: '#64748b', margin: 0, fontStyle: 'italic' }}>This user has not submitted any bug reports or feedback.</p>
                    )}
                  </div>
                </div>
              )}
            </motion.div>
          </div>
        )}
      </AnimatePresence>
    </motion.section>
  );
};

export default Users;
