import { useEffect, useState } from 'react';
import { motion } from 'framer-motion';
import { api } from '../api/api';

const DEFAULT_SETTINGS = {
  announcement: '',
  maintenanceMode: false,
  registrationEnabled: true,
  experimentalVisualizers: {
    avlTree: true,
    huffmanCoding: true,
    trie: true,
    dijkstra: true,
    unionFind: true,
  },
};

const RemoteConfig = ({ user }) => {
  const [settings, setSettings] = useState(DEFAULT_SETTINGS);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState('');
  const [successMsg, setSuccessMsg] = useState('');

  const fetchSettings = async () => {
    setLoading(true);
    try {
      const res = await api('/api/control-center/settings');
      if (res.data?.settings) {
        setSettings(res.data.settings);
      } else if (res.settings) {
        setSettings(res.settings);
      }
      setError('');
    } catch (err) {
      setError(err.message || 'Failed to load app settings');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchSettings();
  }, []);

  const handleSave = async (e) => {
    e?.preventDefault?.();
    setSaving(true);
    setError('');
    setSuccessMsg('');
    try {
      await api('/api/control-center/settings', {
        method: 'PUT',
        body: JSON.stringify({ settings }),
      });
      setSuccessMsg('Android app remote configuration successfully updated!');
      setTimeout(() => setSuccessMsg(''), 4000);
    } catch (err) {
      setError(err.message || 'Failed to save configuration');
    } finally {
      setSaving(false);
    }
  };

  const toggleFeature = (key) => {
    setSettings((prev) => ({
      ...prev,
      experimentalVisualizers: {
        ...(prev.experimentalVisualizers || {}),
        [key]: !prev.experimentalVisualizers?.[key],
      },
    }));
  };

  if (user?.role !== 'owner') {
    return <div className="meta-line">Access restricted to Owners.</div>;
  }

  return (
    <motion.section initial={{ opacity: 0, x: -20 }} animate={{ opacity: 1, x: 0 }} className="section active-section">
      <div className="panel" style={{ maxWidth: '850px' }}>
        <div className="panel-heading compact-heading" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <div>
            <h2>Android App Remote Controls & Config</h2>
            <span>Configure real-time in-app announcements, system locks, and feature toggles</span>
          </div>
          <button type="button" className="action-btn" onClick={fetchSettings} disabled={loading}>
            Reload
          </button>
        </div>

        {successMsg && (
          <p style={{ color: '#10b981', background: 'rgba(16,185,129,0.1)', padding: '10px 14px', borderRadius: '8px', marginBottom: '16px' }}>
            {successMsg}
          </p>
        )}
        {error && <p className="error-text" style={{ color: '#ef4444', marginBottom: '16px' }}>{error}</p>}

        {loading ? (
          <div style={{ textAlign: 'center', padding: '40px', color: '#94a3b8' }}>Loading settings...</div>
        ) : (
          <form onSubmit={handleSave} style={{ display: 'flex', flexDirection: 'column', gap: '24px' }}>
            {/* 1. Global Announcement */}
            <div style={{ background: '#0e1726', padding: '18px', borderRadius: '12px', border: '1px solid rgba(255,255,255,0.08)' }}>
              <h3 style={{ margin: '0 0 8px 0', fontSize: '1.1rem', color: '#38bdf8' }}>📢 Broadcast Announcement</h3>
              <p style={{ margin: '0 0 12px 0', fontSize: '0.85rem', color: '#94a3b8' }}>
                This message will be prominently displayed at the top of the Android app home screen for all students.
              </p>
              <textarea
                rows={3}
                placeholder="e.g. Server maintenance tonight at 10 PM. New Tree Visualizer released!"
                value={settings.announcement || ''}
                onChange={(e) => setSettings({ ...settings, announcement: e.target.value })}
                style={{
                  width: '100%',
                  padding: '12px',
                  borderRadius: '8px',
                  background: '#070d18',
                  color: '#fff',
                  border: '1px solid rgba(255,255,255,0.15)',
                  resize: 'vertical',
                  fontFamily: 'inherit',
                }}
              />
              {settings.announcement && (
                <button
                  type="button"
                  onClick={() => setSettings({ ...settings, announcement: '' })}
                  style={{ marginTop: '8px', background: 'transparent', color: '#ef4444', border: 'none', cursor: 'pointer', fontSize: '0.85rem' }}
                >
                  Clear Announcement
                </button>
              )}
            </div>

            {/* 2. System Status & Security Toggles */}
            <div style={{ background: '#0e1726', padding: '18px', borderRadius: '12px', border: '1px solid rgba(255,255,255,0.08)' }}>
              <h3 style={{ margin: '0 0 14px 0', fontSize: '1.1rem', color: '#f59e0b' }}>🔒 Android App Access Controls</h3>
              
              <div style={{ display: 'flex', flexDirection: 'column', gap: '14px' }}>
                <label style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', cursor: 'pointer' }}>
                  <div>
                    <strong>Emergency Maintenance Mode</strong>
                    <div style={{ fontSize: '0.85rem', color: '#94a3b8' }}>
                      Locks student access on Android and shows a maintenance screen.
                    </div>
                  </div>
                  <input
                    type="checkbox"
                    checked={Boolean(settings.maintenanceMode)}
                    onChange={(e) => setSettings({ ...settings, maintenanceMode: e.target.checked })}
                    style={{ width: '20px', height: '20px', accentColor: '#ef4444', cursor: 'pointer' }}
                  />
                </label>

                <label style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', cursor: 'pointer', borderTop: '1px solid rgba(255,255,255,0.06)', paddingTop: '12px' }}>
                  <div>
                    <strong>New User Registrations</strong>
                    <div style={{ fontSize: '0.85rem', color: '#94a3b8' }}>
                      Allow new Android students to register accounts.
                    </div>
                  </div>
                  <input
                    type="checkbox"
                    checked={Boolean(settings.registrationEnabled)}
                    onChange={(e) => setSettings({ ...settings, registrationEnabled: e.target.checked })}
                    style={{ width: '20px', height: '20px', accentColor: '#10b981', cursor: 'pointer' }}
                  />
                </label>
              </div>
            </div>

            {/* 3. Feature Flags */}
            <div style={{ background: '#0e1726', padding: '18px', borderRadius: '12px', border: '1px solid rgba(255,255,255,0.08)' }}>
              <h3 style={{ margin: '0 0 14px 0', fontSize: '1.1rem', color: '#a855f7' }}>⚡ Visualizer Feature Flags</h3>
              <p style={{ margin: '0 0 14px 0', fontSize: '0.85rem', color: '#94a3b8' }}>
                Remotely enable or disable specific algorithm modules in the Android app.
              </p>

              <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))', gap: '12px' }}>
                {Object.entries(settings.experimentalVisualizers || {}).map(([key, val]) => (
                  <label
                    key={key}
                    style={{
                      display: 'flex',
                      alignItems: 'center',
                      gap: '10px',
                      background: '#111c2e',
                      padding: '10px 14px',
                      borderRadius: '8px',
                      cursor: 'pointer',
                    }}
                  >
                    <input
                      type="checkbox"
                      checked={Boolean(val)}
                      onChange={() => toggleFeature(key)}
                      style={{ accentColor: '#38bdf8', cursor: 'pointer' }}
                    />
                    <span style={{ textTransform: 'capitalize' }}>
                      {key.replace(/([A-Z])/g, ' $1')}
                    </span>
                  </label>
                ))}
              </div>
            </div>

            <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '12px' }}>
              <button
                type="submit"
                disabled={saving}
                style={{
                  background: '#2563eb',
                  color: '#fff',
                  border: 'none',
                  padding: '12px 24px',
                  borderRadius: '8px',
                  fontWeight: 'bold',
                  fontSize: '1rem',
                  cursor: 'pointer',
                  opacity: saving ? 0.7 : 1,
                }}
              >
                {saving ? 'Publishing Changes...' : 'Save & Publish to Android App'}
              </button>
            </div>
          </form>
        )}
      </div>
    </motion.section>
  );
};

export default RemoteConfig;
