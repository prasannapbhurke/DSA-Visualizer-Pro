import { useLocation } from 'react-router-dom';

const getTitle = (path) => {
  switch (path) {
    case '/': return 'Overview';
    case '/users': return 'User directory';
    case '/classes': return 'Class management';
    case '/resources': return 'Learning resources';
    case '/feedback': return 'User feedback';
    case '/audit': return 'Audit logs';
    default: return 'Dashboard';
  }
};

const Topbar = ({ user }) => {
  const location = useLocation();
  const title = getTitle(location.pathname);

  return (
    <header className="topbar">
      <div>
        <p className="system-label">Live Admin PWA</p>
        <h1 id="page-title">{title}</h1>
        <div id="live-status" className="live-status" aria-live="polite">
          <span className="live-dot"></span>
          <span id="live-status-text">React Vite Edition</span>
        </div>
      </div>
      <div className="topbar-actions">
        {user?.role === 'owner' && <button id="export-btn" className="accent-btn owner-only">Export DB</button>}
      </div>
    </header>
  );
};

export default Topbar;
