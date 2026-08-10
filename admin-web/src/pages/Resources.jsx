import { useEffect, useState } from 'react';
import { motion } from 'framer-motion';
import { api } from '../api/api';

const Resources = () => {
  const [resources, setResources] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    const loadResources = async () => {
      try {
        const classRes = await api('/api/classes');
        const classes = classRes.data?.classes || [];
        const resourceGroups = await Promise.all(
          classes.map(async (classroom) => {
            try {
              const res = await api(`/api/classes/${classroom.id}/resources`);
              return (res.data?.resources || []).map((resource) => ({ ...resource, className: classroom.name }));
            } catch {
              return [];
            }
          })
        );
        setResources(resourceGroups.flat());
      } catch (err) {
        setError(err.message);
      }
    };

    loadResources();
  }, []);

  return (
    <motion.section initial={{ opacity: 0, x: -20 }} animate={{ opacity: 1, x: 0 }} className="section active-section">
      <div className="panel">
        <div className="panel-heading">
          <h2>Learning resources</h2>
          <span>{resources.length} files</span>
        </div>
        {error && <p className="error-text">{error}</p>}
        <div className="resources-list">
          {resources.map((resource) => (
            <article className="resource-card" key={resource.id}>
              <h3>{resource.title || resource.fileName || 'Untitled resource'}</h3>
              <p className="meta-line">
                <span>Class: {resource.className || '-'}</span>
                <span>Type: {resource.type || resource.mimeType || '-'}</span>
                <span>Uploaded by: {resource.uploadedByName || resource.uploadedBy || '-'}</span>
              </p>
              {resource.downloadUrl && (
                <div className="resource-actions">
                  <a className="row-btn" href={resource.downloadUrl} target="_blank" rel="noreferrer">Open</a>
                </div>
              )}
            </article>
          ))}
          {resources.length === 0 && <div className="meta-line">No resources found.</div>}
        </div>
      </div>
    </motion.section>
  );
};

export default Resources;
