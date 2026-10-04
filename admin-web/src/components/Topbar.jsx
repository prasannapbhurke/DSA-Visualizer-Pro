import { useState } from 'react';
import { useLocation } from 'react-router-dom';
import { getApiBaseUrl, setApiBaseUrl, ENVIRONMENTS } from '../api/api';

const getTitle = (path) => {
  switch (path) {
    case '/': return 'Android Overview & Live Telemetry';
    case '/users': return 'Android Users & Progress Directory';
    case '/classes': return 'Classrooms & Student Rosters';
    case '/resources': return 'In-App Learning Resources';
    case '/feedback': return 'Android Feedback & Bug Center';
    case '/config': return 'Remote In-App Controls & Feature Flags';
    case '/audit': return 'Admin & Role Change Audit Logs';
    default: return 'Android Admin Console';
  }
};

const Topbar = ({ user }) => {
  const location = useLocation();
  const title = getTitle(location.pathname);
  const [currentEnv, setCurrentEnv] = useState(getApiBaseUrl());

  const handleEnvChange = (e) => {
    const newUrl = e.target.value;
    setApiBaseUrl(newUrl);
    setCurrentEnv(newUrl);
    window.location.reload();
  };

  return (
    <header className="topbar" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
      <div>
        <p className="system-label" style={{ fontSize: '0.8rem', color: '#38bdf8', margin: 0, textTransform: 'uppercase', letterSpacing: '0.05em' }}>
          DSA Android Management Console
        </p>
        <h1 id="page-title" style={{ margin: '4px 0', fontSize: '1.5rem' }}>{title}</h1>
        <div id="live-status" className="live-status" style={{ display: 'flex', alignItems: 'center', gap: '8px', fontSize: '0.85rem' }}>
          <span className="live-dot" style={{ width: '8px', height: '8px', borderRadius: '50%', background: '#10b981', display: 'inline-block' }}></span>
          <span id="live-status-text" style={{ color: '#94a3b8' }}>
            Connected to: <strong style={{ color: '#f1f5f9' }}>{currentEnv}</strong>
          </span>
        </div>
      </div>

      <div className="topbar-actions" style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '8px', background: '#0e1726', padding: '6px 12px', borderRadius: '8px', border: '1px solid rgba(255,255,255,0.1)' }}>
          <span style={{ fontSize: '0.8rem', color: '#94a3b8' }}>API Target:</span>
          <select
            value={currentEnv}
            onChange={handleEnvChange}
            style={{ background: 'transparent', color: '#fff', border: 'none', fontSize: '0.85rem', cursor: 'pointer' }}
          >
            {ENVIRONMENTS.map((env) => (
              <option key={env.id} value={env.url} style={{ background: '#111c2e', color: '#fff' }}>
                {env.label}
              </option>
            ))}
          </select>
        </div>
      </div>
    </header>
  );
};

export default Topbar;
