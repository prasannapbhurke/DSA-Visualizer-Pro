import { useEffect, useState } from 'react';
import { motion } from 'framer-motion';
import { api } from '../api/api';

const formatDate = (value) => {
  if (!value) return '-';
  const date = new Date(value);
  return Number.isNaN(date.getTime()) ? '-' : date.toLocaleString();
};

const Audit = ({ user }) => {
  const [logs, setLogs] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    if (user?.role !== 'owner') return;
    api('/api/admin/audit-logs')
      .then((res) => setLogs(res.data?.auditLogs || []))
      .catch((err) => setError(err.message));
  }, [user]);

  if (user?.role !== 'owner') {
    return <div className="meta-line">Access restricted to owners.</div>;
  }

  return (
    <motion.section initial={{ opacity: 0, x: -20 }} animate={{ opacity: 1, x: 0 }} className="section active-section">
      <div className="panel">
        <div className="panel-heading">
          <h2>Audit logs</h2>
          <span>{logs.length} events</span>
        </div>
        {error && <p className="error-text">{error}</p>}
        <div className="audit-list">
          {logs.map((log) => (
            <article className="audit-card" key={log.id}>
              <h3>{log.action}</h3>
              <p className="meta-line">
                <span>Actor: {log.actorName || log.actorId || '-'}</span>
                <span>Target: {log.targetName || log.targetId || '-'}</span>
                <span>{formatDate(log.createdAt)}</span>
              </p>
            </article>
          ))}
          {logs.length === 0 && <div className="meta-line">No audit logs found.</div>}
        </div>
      </div>
    </motion.section>
  );
};

export default Audit;
