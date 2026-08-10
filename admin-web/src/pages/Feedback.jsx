import { useEffect, useState } from 'react';
import { motion } from 'framer-motion';
import { api } from '../api/api';

const formatDate = (value) => {
  if (!value) return '-';
  const date = new Date(value);
  return Number.isNaN(date.getTime()) ? '-' : date.toLocaleString();
};

const Feedback = ({ user }) => {
  const [feedback, setFeedback] = useState([]);
  const [error, setError] = useState('');

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

  const deleteFeedback = async (entry) => {
    const confirmed = window.confirm('Delete this feedback permanently? This cannot be undone.');
    if (!confirmed) return;

    try {
      await api(`/api/feedback/${entry.id}`, { method: 'DELETE' });
      setFeedback((current) => current.filter((item) => item.id !== entry.id));
      setError('');
    } catch (err) {
      setError(err.message || 'Feedback could not be deleted.');
    }
  };

  return (
    <motion.section initial={{ opacity: 0, x: -20 }} animate={{ opacity: 1, x: 0 }} className="section active-section">
      <div className="panel">
        <div className="panel-heading">
          <h2>User feedback</h2>
          <span>{feedback.length} entries</span>
        </div>
        {error && <p className="error-text">{error}</p>}
        <div className="feedback-list">
          {feedback.map((entry) => (
            <article className="feedback-card" key={entry.id}>
              <h3>{entry.type || 'Feedback'}</h3>
              <p>{entry.message}</p>
              <p className="meta-line">
                <span>From: {entry.userEmail || entry.userDisplayName || entry.userId || '-'}</span>
                <span>Class: {entry.className || '-'}</span>
                <span>Created: {formatDate(entry.createdAt)}</span>
                <span>{entry.reviewedAt ? 'Reviewed' : 'Open'}</span>
              </p>
              <div className="feedback-actions">
                <button type="button" className="row-btn danger-btn" onClick={() => deleteFeedback(entry)}>
                  Delete
                </button>
              </div>
            </article>
          ))}
          {feedback.length === 0 && <div className="meta-line">No feedback found.</div>}
        </div>
      </div>
    </motion.section>
  );
};

export default Feedback;

