import { useEffect, useState, useMemo } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { api } from '../api/api';

const formatDate = (value) => {
  if (!value) return '-';
  const date = new Date(value);
  return Number.isNaN(date.getTime()) ? '-' : date.toLocaleString();
};

const Feedback = ({ user }) => {
  const [feedback, setFeedback] = useState([]);
  const [error, setError] = useState('');
  const [successMsg, setSuccessMsg] = useState('');
  const [filterType, setFilterType] = useState('all');
  const [statusFilter, setStatusFilter] = useState('all'); // all, open, reviewed
  const [searchQuery, setSearchQuery] = useState('');

  // Reply state: map of feedbackId -> replyDraft
  const [replyDrafts, setReplyDrafts] = useState({});
  const [activeReplyId, setActiveReplyId] = useState(null);
  const [sendingReply, setSendingReply] = useState(false);

  const loadFeedback = () => {
    const endpoint = user?.role === 'owner' ? '/api/feedback' : '/api/teacher/feedback';
    api(endpoint)
      .then((res) => {
        setFeedback(res.data?.feedback || []);
        setError('');
      })
      .catch((err) => setError(err.message));
  };

  useEffect(() => {
    loadFeedback();
  }, [user]);

  const markReviewed = async (entry) => {
    try {
      await api(`/api/teacher/feedback/${entry.id}/review`, { method: 'PATCH' });
      setFeedback((current) =>
        current.map((item) =>
          item.id === entry.id ? { ...item, reviewedAt: new Date().toISOString() } : item
        )
      );
      setSuccessMsg('Feedback marked as reviewed!');
      setTimeout(() => setSuccessMsg(''), 3000);
    } catch (err) {
      setError(err.message || 'Could not mark feedback as reviewed');
    }
  };

  const handleSendReply = async (entry) => {
    const draft = (replyDrafts[entry.id] || '').trim();
    if (!draft) {
      alert('Please enter a reply message before sending.');
      return;
    }

    setSendingReply(true);
    try {
      const res = await api(`/api/feedback/${entry.id}/reply`, {
        method: 'POST',
        body: JSON.stringify({ reply: draft }),
      });
      const updated = res.data || res;
      setFeedback((current) =>
        current.map((item) =>
          item.id === entry.id
            ? {
                ...item,
                adminReply: updated.adminReply || draft,
                repliedAt: updated.repliedAt || new Date().toISOString(),
                reviewedAt: updated.reviewedAt || new Date().toISOString(),
              }
            : item
        )
      );
      setReplyDrafts((prev) => ({ ...prev, [entry.id]: '' }));
      setActiveReplyId(null);
      setSuccessMsg(`Reply delivered to ${entry.userEmail || 'student'}!`);
      setTimeout(() => setSuccessMsg(''), 4000);
    } catch (err) {
      setError(err.message || 'Failed to deliver reply to student');
    } finally {
      setSendingReply(false);
    }
  };

  const deleteFeedback = async (entry) => {
    const confirmed = window.confirm('Delete this feedback permanently? This cannot be undone.');
    if (!confirmed) return;

    try {
      await api(`/api/feedback/${entry.id}`, { method: 'DELETE' });
      setFeedback((current) => current.filter((item) => item.id !== entry.id));
      setSuccessMsg('Feedback deleted.');
      setTimeout(() => setSuccessMsg(''), 3000);
    } catch (err) {
      setError(err.message || 'Feedback could not be deleted.');
    }
  };

  const filteredFeedback = useMemo(() => {
    const q = searchQuery.trim().toLowerCase();
    return feedback.filter((item) => {
      // Status filter
      if (statusFilter === 'open' && item.reviewedAt) return false;
      if (statusFilter === 'reviewed' && !item.reviewedAt) return false;

      // Type filter
      if (filterType !== 'all' && (item.type || '').toLowerCase() !== filterType) return false;

      // Search query
      if (q) {
        const text = [
          item.message,
          item.userEmail,
          item.userDisplayName,
          item.techniqueId,
          item.screen,
          item.type,
          item.adminReply,
        ].filter(Boolean).join(' ').toLowerCase();
        if (!text.includes(q)) return false;
      }

      return true;
    });
  }, [feedback, statusFilter, filterType, searchQuery]);

  const unreviewedCount = feedback.filter((f) => !f.reviewedAt).length;

  return (
    <motion.section initial={{ opacity: 0, x: -20 }} animate={{ opacity: 1, x: 0 }} className="section active-section">
      <div className="panel">
        <div className="panel-heading compact-heading" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <div>
            <h2>Android Feedback & Bug Reports</h2>
            <span>
              {feedback.length} total submissions ({unreviewedCount} pending review)
            </span>
          </div>
          <button type="button" className="action-btn" onClick={loadFeedback}>
            Refresh Inbox
          </button>
        </div>

        {successMsg && (
          <p style={{ color: '#10b981', background: 'rgba(16,185,129,0.1)', padding: '10px 14px', borderRadius: '8px', marginBottom: '14px' }}>
            {successMsg}
          </p>
        )}
        {error && <p className="error-text" style={{ color: '#ef4444', marginBottom: '14px' }}>{error}</p>}

        {/* Filter Controls */}
        <div style={{ display: 'flex', gap: '10px', flexWrap: 'wrap', marginBottom: '16px', alignItems: 'center' }}>
          <div className="filter-strip" style={{ display: 'flex', gap: '6px' }}>
            <button
              type="button"
              className={`filter-chip ${statusFilter === 'all' ? 'active' : ''}`}
              onClick={() => setStatusFilter('all')}
            >
              All Status
            </button>
            <button
              type="button"
              className={`filter-chip ${statusFilter === 'open' ? 'active' : ''}`}
              onClick={() => setStatusFilter('open')}
            >
              Pending ({unreviewedCount})
            </button>
            <button
              type="button"
              className={`filter-chip ${statusFilter === 'reviewed' ? 'active' : ''}`}
              onClick={() => setStatusFilter('reviewed')}
            >
              Reviewed ({feedback.length - unreviewedCount})
            </button>
          </div>

          <select
            value={filterType}
            onChange={(e) => setFilterType(e.target.value)}
            style={{ padding: '8px 12px', borderRadius: '8px', background: '#0e1726', color: '#fff', border: '1px solid rgba(255,255,255,0.15)' }}
          >
            <option value="all">All Categories</option>
            <option value="bug">Bugs</option>
            <option value="feedback">General Feedback</option>
            <option value="difficulty">Difficulty Reports</option>
            <option value="feature">Feature Requests</option>
          </select>

          <input
            type="search"
            placeholder="Search messages, email, screen, algorithm or device..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            style={{ flex: 1, minWidth: '220px', padding: '8px 12px', borderRadius: '8px', border: '1px solid rgba(255,255,255,0.15)', background: '#0e1726', color: '#fff' }}
          />
        </div>

        {/* Feedback List */}
        <div className="feedback-list" style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
          {filteredFeedback.map((entry) => {
            const isReviewed = Boolean(entry.reviewedAt);
            const isBug = (entry.type || '').toLowerCase() === 'bug';
            const rawDevice = entry.deviceInfo || entry.device_info;
            const deviceInfo = typeof rawDevice === 'string' ? JSON.parse(rawDevice) : rawDevice;
            const hasReply = Boolean(entry.adminReply || entry.admin_reply);
            const replyText = entry.adminReply || entry.admin_reply || '';
            const isReplying = activeReplyId === entry.id;

            return (
              <article
                className="feedback-card"
                key={entry.id}
                style={{
                  background: isReviewed ? '#0f172a' : '#141e33',
                  border: isReviewed ? '1px solid rgba(255,255,255,0.08)' : '1px solid rgba(59,130,246,0.35)',
                  borderRadius: '12px',
                  padding: '20px',
                  position: 'relative',
                }}
              >
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '10px' }}>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
                    <span
                      style={{
                        padding: '4px 10px',
                        borderRadius: '6px',
                        fontSize: '0.8rem',
                        fontWeight: 'bold',
                        textTransform: 'uppercase',
                        background: isBug ? 'rgba(239, 68, 68, 0.2)' : 'rgba(59, 130, 246, 0.2)',
                        color: isBug ? '#ef4444' : '#38bdf8',
                      }}
                    >
                      {entry.type || 'Feedback'}
                    </span>
                    <span style={{ color: isReviewed ? '#10b981' : '#f59e0b', fontSize: '0.85rem', fontWeight: 500 }}>
                      {hasReply ? '✓ Answered' : isReviewed ? '✓ Reviewed' : '● Action Required'}
                    </span>
                  </div>
                  <small style={{ color: '#94a3b8' }}>{formatDate(entry.createdAt)}</small>
                </div>

                <p style={{ color: '#f1f5f9', fontSize: '1.05rem', lineHeight: '1.5', margin: '8px 0 14px 0' }}>
                  {entry.message}
                </p>

                {/* Device Diagnostic Telemetry Banner */}
                {deviceInfo && (deviceInfo.deviceModel || deviceInfo.osVersion || deviceInfo.appVersion) && (
                  <div
                    style={{
                      background: 'rgba(59, 130, 246, 0.08)',
                      border: '1px solid rgba(59, 130, 246, 0.2)',
                      padding: '8px 12px',
                      borderRadius: '8px',
                      marginBottom: '12px',
                      display: 'flex',
                      flexWrap: 'wrap',
                      gap: '12px',
                      fontSize: '0.8rem',
                      color: '#93c5fd',
                    }}
                  >
                    <span><strong>📱 Device:</strong> {deviceInfo.deviceModel || 'Android'}</span>
                    <span><strong>OS:</strong> {deviceInfo.osVersion || 'Android'}</span>
                    <span><strong>App Version:</strong> {deviceInfo.appVersion || '-'}</span>
                    {deviceInfo.display && <span><strong>Display:</strong> {deviceInfo.display}</span>}
                  </div>
                )}

                {/* App Context Metadata */}
                <div style={{ display: 'flex', flexWrap: 'wrap', gap: '16px', fontSize: '0.85rem', color: '#94a3b8', background: '#090f1d', padding: '10px', borderRadius: '8px', marginBottom: '14px' }}>
                  <span><strong>Student:</strong> {entry.userEmail || entry.userDisplayName || entry.userId || 'Android Student'}</span>
                  {entry.screen && <span><strong>Screen:</strong> {entry.screen}</span>}
                  {entry.techniqueId && <span><strong>Algorithm:</strong> {entry.techniqueId}</span>}
                  {entry.difficulty && <span><strong>Difficulty:</strong> {entry.difficulty}</span>}
                </div>

                {/* Existing Admin Reply Box (if answered) */}
                {hasReply && (
                  <div
                    style={{
                      background: 'rgba(16, 185, 129, 0.08)',
                      border: '1px solid rgba(16, 185, 129, 0.25)',
                      borderRadius: '8px',
                      padding: '12px 14px',
                      marginBottom: '14px',
                    }}
                  >
                    <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '4px', fontSize: '0.8rem', color: '#10b981', fontWeight: 'bold' }}>
                      <span>💬 Admin Response Sent to Student:</span>
                      <small style={{ color: '#94a3b8', fontWeight: 'normal' }}>
                        {formatDate(entry.repliedAt || entry.replied_at)}
                      </small>
                    </div>
                    <p style={{ margin: 0, color: '#e2e8f0', fontSize: '0.95rem' }}>{replyText}</p>
                  </div>
                )}

                {/* Inline Reply Composition Drawer */}
                <AnimatePresence>
                  {isReplying && (
                    <motion.div
                      initial={{ opacity: 0, height: 0 }}
                      animate={{ opacity: 1, height: 'auto' }}
                      exit={{ opacity: 0, height: 0 }}
                      style={{ marginBottom: '14px', overflow: 'hidden' }}
                    >
                      <div style={{ background: '#09101d', padding: '14px', borderRadius: '8px', border: '1px solid rgba(56, 189, 248, 0.3)' }}>
                        <label style={{ display: 'block', fontSize: '0.85rem', color: '#38bdf8', marginBottom: '6px', fontWeight: 600 }}>
                          Direct Reply to {entry.userDisplayName || entry.userEmail}:
                        </label>
                        <textarea
                          rows={3}
                          placeholder="Type your response to the student here... They will receive it in their Android app."
                          value={replyDrafts[entry.id] || ''}
                          onChange={(e) =>
                            setReplyDrafts({ ...replyDrafts, [entry.id]: e.target.value })
                          }
                          style={{
                            width: '100%',
                            padding: '10px',
                            borderRadius: '6px',
                            background: '#070b14',
                            color: '#fff',
                            border: '1px solid rgba(255,255,255,0.15)',
                            resize: 'vertical',
                            fontFamily: 'inherit',
                            fontSize: '0.9rem',
                            marginBottom: '10px',
                          }}
                        />
                        <div style={{ display: 'flex', gap: '8px', justifyContent: 'flex-end' }}>
                          <button
                            type="button"
                            className="ghost-btn"
                            onClick={() => setActiveReplyId(null)}
                            style={{ padding: '6px 12px', fontSize: '0.85rem' }}
                          >
                            Cancel
                          </button>
                          <button
                            type="button"
                            disabled={sendingReply}
                            onClick={() => handleSendReply(entry)}
                            style={{
                              background: '#2563eb',
                              color: '#fff',
                              border: 'none',
                              padding: '6px 16px',
                              borderRadius: '6px',
                              fontWeight: 600,
                              fontSize: '0.85rem',
                              cursor: 'pointer',
                            }}
                          >
                            {sendingReply ? 'Delivering...' : 'Send to Student Phone'}
                          </button>
                        </div>
                      </div>
                    </motion.div>
                  )}
                </AnimatePresence>

                {/* Card Actions */}
                <div className="feedback-actions" style={{ display: 'flex', gap: '10px', justifyContent: 'flex-end', alignItems: 'center' }}>
                  <button
                    type="button"
                    className="row-btn"
                    onClick={() => setActiveReplyId(isReplying ? null : entry.id)}
                    style={{ background: '#1e293b', color: '#38bdf8', border: '1px solid rgba(56,189,248,0.3)', padding: '6px 14px', borderRadius: '6px' }}
                  >
                    {isReplying ? 'Close Reply Box' : hasReply ? '✍️ Update Reply' : '✍️ Reply to Student'}
                  </button>

                  {!isReviewed && (
                    <button
                      type="button"
                      className="row-btn"
                      onClick={() => markReviewed(entry)}
                      style={{ background: '#0284c7', color: '#fff', border: 'none', padding: '6px 14px', borderRadius: '6px' }}
                    >
                      Mark as Reviewed
                    </button>
                  )}

                  <button
                    type="button"
                    className="row-btn danger-btn"
                    onClick={() => deleteFeedback(entry)}
                    style={{ background: 'transparent', color: '#ef4444', border: '1px solid rgba(239,68,68,0.3)', padding: '6px 14px', borderRadius: '6px' }}
                  >
                    Delete
                  </button>
                </div>
              </article>
            );
          })}

          {filteredFeedback.length === 0 && (
            <div className="meta-line" style={{ textAlign: 'center', padding: '36px', color: '#94a3b8' }}>
              No feedback entries match your filters.
            </div>
          )}
        </div>
      </div>
    </motion.section>
  );
};

export default Feedback;
