import { useEffect, useState } from 'react';
import { motion } from 'framer-motion';
import { api } from '../api/api';

const formatDate = (value) => {
  if (!value) return '-';
  const date = new Date(value);
  return Number.isNaN(date.getTime()) ? '-' : date.toLocaleDateString();
};

const Classes = () => {
  const [classes, setClasses] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    api('/api/classes')
      .then((res) => setClasses(res.data?.classes || []))
      .catch((err) => setError(err.message));
  }, []);

  return (
    <motion.section initial={{ opacity: 0, x: -20 }} animate={{ opacity: 1, x: 0 }} className="section active-section">
      <div className="panel">
        <div className="panel-heading">
          <h2>Class management</h2>
          <span>{classes.length} classes</span>
        </div>
        {error && <p className="error-text">{error}</p>}
        <div className="class-list">
          {classes.map((classroom) => (
            <article className="class-card" key={classroom.id}>
              <h3>{classroom.name}</h3>
              <p className="meta-line">
                <span>Teacher: {classroom.teacherName || classroom.teacherId || '-'}</span>
                <span>Join code: {classroom.joinCode || '-'}</span>
                <span>Students: {classroom.studentCount ?? 0}</span>
                <span>Created: {formatDate(classroom.createdAt)}</span>
              </p>
              {classroom.description && <p className="login-copy">{classroom.description}</p>}
            </article>
          ))}
          {classes.length === 0 && <div className="meta-line">No classes found.</div>}
        </div>
      </div>
    </motion.section>
  );
};

export default Classes;
