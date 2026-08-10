import { useState, useEffect, useRef } from 'react';
import { Routes, Route, useNavigate, useLocation } from 'react-router-dom';
import { api, getToken, setToken, removeToken, API_BASE_URL } from './api/api';
import Dashboard from './pages/Dashboard';
import Users from './pages/Users';
import Classes from './pages/Classes';
import Resources from './pages/Resources';
import Feedback from './pages/Feedback';
import Audit from './pages/Audit';
import Sidebar from './components/Sidebar';
import Topbar from './components/Topbar';

function App() {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const googleBtnRef = useRef(null);
  const isElectron = typeof window !== 'undefined' && !!window.electronAPI;

  useEffect(() => {
    const init = async () => {
      const token = getToken();
      if (token) {
        try {
          const me = await api('/api/me');
          const authUser = me.data?.user || me.user;
          if (!authUser || !['owner', 'teacher'].includes(authUser.role)) {
            throw new Error('Only owners and teachers can open the admin dashboard.');
          }
          setUser(authUser);
        } catch (err) {
          setError(err.message);
          removeToken();
        }
      }
      setLoading(false);
    };
    init();
  }, []);

  useEffect(() => {
    if (!window.electronAPI?.onDesktopAuthSuccess) return undefined;

    const removeSuccessListener = window.electronAPI.onDesktopAuthSuccess(({ token, user: authUser }) => {
      setToken(token);
      setUser(authUser);
      setError('');
    });
    const removeErrorListener = window.electronAPI.onDesktopAuthError((message) => {
      setError(message);
    });

    return () => {
      removeSuccessListener();
      removeErrorListener();
    };
  }, []);

  useEffect(() => {
    if (loading || user || isElectron) return;

    let intervalId;
    const initGoogle = () => {
      if (window.google) {
        if (intervalId) clearInterval(intervalId);
        window.google.accounts.id.initialize({
          client_id: '628188239936-0m8j5kh9hht76t5edo86mkfij7f6s5t2.apps.googleusercontent.com',
          callback: async (res) => {
            try {
              const authRes = await fetch(`${API_BASE_URL}/api/auth/google`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ credential: res.credential }),
              });
              const payload = await authRes.json();
              const authData = payload.data || payload;
              if (!authRes.ok) throw new Error(payload.error?.message || 'Login failed');
              if (!authData.user || !['owner', 'teacher'].includes(authData.user.role)) {
                throw new Error('Unauthorized role.');
              }
              setToken(authData.token);
              setUser(authData.user);
            } catch (err) {
              setError(err.message);
            }
          },
        });
        if (googleBtnRef.current) {
          window.google.accounts.id.renderButton(googleBtnRef.current, { theme: 'filled_black', size: 'large' });
        }
      }
    };

    if (window.google) {
      initGoogle();
    } else {
      intervalId = setInterval(initGoogle, 100);
    }

    return () => {
      if (intervalId) clearInterval(intervalId);
    };
  }, [loading, user, isElectron]);

  const handleDesktopLogin = async () => {
    setError('Opening Google sign-in in your browser...');
    try {
      await window.electronAPI.startDesktopGoogleLogin();
    } catch (err) {
      setError(err.message || 'Unable to open browser sign-in.');
    }
  };

  const handleLogout = () => {
    removeToken();
    setUser(null);
    navigate('/');
  };

  if (loading) return <div className="login-view">Loading...</div>;

  if (!user) {
    return (
      <div className="login-view">
        <div className="brand-mark">DSA</div>
        <div className="login-card">
          <p className="system-label">Control Center</p>
          {isElectron ? (
            <button type="button" className="google-browser-btn" onClick={handleDesktopLogin}>
              <span className="google-mark">G</span>
              <span>Sign in with Google in browser</span>
            </button>
          ) : (
            <div ref={googleBtnRef}></div>
          )}
          {error && <p id="login-error" className="error-text" style={{ display: 'block' }}>{error}</p>}
        </div>
      </div>
    );
  }

  return (
    <div className="dashboard-view">
      <Sidebar user={user} onLogout={handleLogout} />
      <section className="content">
        <Topbar user={user} />
        <Routes>
          <Route path="/" element={<Dashboard user={user} />} />
          <Route path="/users" element={<Users user={user} />} />
          <Route path="/classes" element={<Classes user={user} />} />
          <Route path="/resources" element={<Resources user={user} />} />
          <Route path="/feedback" element={<Feedback user={user} />} />
          <Route path="/audit" element={<Audit user={user} />} />
        </Routes>
      </section>
    </div>
  );
}

export default App;

