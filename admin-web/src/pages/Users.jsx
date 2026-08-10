import { useEffect, useMemo, useState } from 'react';
import { motion } from 'framer-motion';
import { api } from '../api/api';

const getSeenAt = (entry) => entry.lastSeenAt || entry.last_seen_at || entry.createdAt || entry.created_at || '';
const getCreatedAt = (entry) => entry.createdAt || entry.created_at || '';
const getName = (entry) => entry.name || entry.displayName || '';

const formatDate = (value) => {
  if (!value) return 'Never';
  const date = new Date(value);
  return Number.isNaN(date.getTime()) ? 'Never' : date.toLocaleString();
};

const daysAgo = (days) => Date.now() - days * 24 * 60 * 60 * 1000;

const views = [
  { id: 'all', label: 'All' },
  { id: 'recent', label: 'Recent' },
  { id: 'new', label: 'New' },
  { id: 'owner', label: 'Owners' },
  { id: 'teacher', label: 'Teachers' },
  { id: 'student', label: 'Students' },
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
  const [activeView, setActiveView] = useState('all');
  const [sortBy, setSortBy] = useState('recent');
  const [query, setQuery] = useState('');

  useEffect(() => {
    if (user?.role !== 'owner') return;
    api('/api/users')
      .then((res) => setUsers(res.data?.users || []))
      .catch((err) => setError(err.message));
  }, [user]);

  const visibleUsers = useMemo(() => {
    const normalizedQuery = query.trim().toLowerCase();
    const recentCutoff = daysAgo(30);

    const filtered = users.filter((entry) => {
      if (activeView === 'recent') {
        const seen = new Date(getSeenAt(entry)).getTime();
        if (!Number.isFinite(seen) || seen < recentCutoff) return false;
      } else if (['owner', 'teacher', 'student'].includes(activeView) && entry.role !== activeView) {
        return false;
      }

      if (!normalizedQuery) return true;
      return [entry.email, getName(entry), entry.role]
        .filter(Boolean)
        .some((value) => String(value).toLowerCase().includes(normalizedQuery));
    });

    const sorted = filtered.sort((left, right) => {
      if (activeView === 'new' || sortBy === 'newest') {
        return new Date(getCreatedAt(right)).getTime() - new Date(getCreatedAt(left)).getTime();
      }
      if (sortBy === 'name') {
        return getName(left).localeCompare(getName(right));
      }
      if (sortBy === 'email') {
        return String(left.email || '').localeCompare(String(right.email || ''));
      }
      if (sortBy === 'role') {
        return String(left.role || '').localeCompare(String(right.role || '')) || String(left.email || '').localeCompare(String(right.email || ''));
      }
      return new Date(getSeenAt(right)).getTime() - new Date(getSeenAt(left)).getTime();
    });

    return activeView === 'new' ? sorted.slice(0, 10) : sorted;
  }, [activeView, query, sortBy, users]);

  if (user?.role !== 'owner') {
    return <div className="meta-line">Access restricted to owners.</div>;
  }

  return (
    <motion.section initial={{ opacity: 0, x: -20 }} animate={{ opacity: 1, x: 0 }} className="section active-section">
      <div className="panel">
        <div className="panel-heading compact-heading">
          <div>
            <h2>User directory</h2>
            <span>{visibleUsers.length} shown from {users.length} accounts</span>
          </div>
        </div>

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

        <div className="panel-controls user-controls">
          <input
            className="search-input"
            type="search"
            placeholder="Search users"
            value={query}
            onChange={(event) => setQuery(event.target.value)}
          />
          <label className="select-label compact-input">
            Sort
            <select className="select-input" value={sortBy} onChange={(event) => setSortBy(event.target.value)}>
              {sortOptions.map((option) => (
                <option key={option.id} value={option.id}>{option.label}</option>
              ))}
            </select>
          </label>
        </div>

        {error && <p className="error-text">{error}</p>}
        <div className="table-wrap">
          <table className="data-table">
            <thead>
              <tr>
                <th>Email</th>
                <th>Name</th>
                <th>Role</th>
                <th>Joined</th>
                <th>Last seen</th>
              </tr>
            </thead>
            <tbody>
              {visibleUsers.map((entry) => (
                <tr key={entry.id}>
                  <td>{entry.email}</td>
                  <td>{getName(entry) || '-'}</td>
                  <td><span className={`role-badge ${entry.role}`}>{entry.role}</span></td>
                  <td>{formatDate(getCreatedAt(entry))}</td>
                  <td>{formatDate(getSeenAt(entry))}</td>
                </tr>
              ))}
              {visibleUsers.length === 0 && <tr><td colSpan="5" className="meta-line">No users match this view.</td></tr>}
            </tbody>
          </table>
        </div>
      </div>
    </motion.section>
  );
};

export default Users;
