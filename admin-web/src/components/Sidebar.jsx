import { NavLink } from 'react-router-dom';

const Sidebar = ({ user, onLogout }) => {
  return (
    <aside className="sidebar">
      <div className="side-brand">
        <div className="brand-mark small">DSA</div>
        <div>
          <strong>Control Center</strong>
          <span id="role-label">{user?.role} - {user?.email}</span>
        </div>
      </div>
      <nav className="nav-stack" aria-label="Dashboard sections">
        <NavLink to="/" className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}>Overview</NavLink>
        {user?.role === 'owner' && (
          <NavLink to="/users" className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}>Users</NavLink>
        )}
        <NavLink to="/classes" className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}>Classes</NavLink>
        <NavLink to="/resources" className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}>Resources</NavLink>
        <NavLink to="/feedback" className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}>Feedback</NavLink>
        {user?.role === 'owner' && (
          <NavLink to="/audit" className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}>Audit</NavLink>
        )}
      </nav>
      <button onClick={onLogout} className="ghost-btn" style={{ marginTop: 'auto' }}>Sign out</button>
    </aside>
  );
};

export default Sidebar;
