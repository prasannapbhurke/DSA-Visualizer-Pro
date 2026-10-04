import { NavLink } from 'react-router-dom';

const Sidebar = ({ user, onLogout }) => {
  return (
    <aside className="sidebar">
      <div className="side-brand">
        <div className="brand-mark small" style={{ background: '#2563eb', color: '#fff', borderRadius: '8px', padding: '6px 8px', fontWeight: 'bold' }}>
          DSA
        </div>
        <div style={{ display: 'flex', flexDirection: 'column' }}>
          <strong style={{ fontSize: '0.95rem' }}>Android Admin</strong>
          <span id="role-label" style={{ fontSize: '0.75rem', color: '#94a3b8', textTransform: 'capitalize' }}>
            {user?.role} • {user?.email}
          </span>
        </div>
      </div>

      <nav className="nav-stack" aria-label="Dashboard sections" style={{ marginTop: '20px' }}>
        <NavLink to="/" className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}>
          <span>📊 Overview & Stats</span>
        </NavLink>
        
        {user?.role === 'owner' && (
          <NavLink to="/users" className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}>
            <span>👥 Android Users</span>
          </NavLink>
        )}

        <NavLink to="/feedback" className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}>
          <span>💬 Feedback & Bugs</span>
        </NavLink>

        <NavLink to="/classes" className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}>
          <span>🏫 Classrooms</span>
        </NavLink>

        <NavLink to="/resources" className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}>
          <span>📁 App Resources</span>
        </NavLink>

        {user?.role === 'owner' && (
          <NavLink to="/config" className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}>
            <span>⚙️ Remote In-App Config</span>
          </NavLink>
        )}

        {user?.role === 'owner' && (
          <NavLink to="/audit" className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}>
            <span>📜 Audit Logs</span>
          </NavLink>
        )}
      </nav>

      <div style={{ marginTop: 'auto', paddingTop: '16px', borderTop: '1px solid rgba(255,255,255,0.08)' }}>
        <button onClick={onLogout} className="ghost-btn" style={{ width: '100%', textAlign: 'center', padding: '10px' }}>
          Sign out
        </button>
      </div>
    </aside>
  );
};

export default Sidebar;
