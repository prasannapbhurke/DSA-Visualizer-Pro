import React from 'react';
import './Dashboard.css';

const Dashboard = ({ categories, selectedCategoryId, techniqueCounts, onSelectCategory }) => (
  <section className="dashboard">
    <div className="dashboard-heading">
      <h2>Technique Families</h2>
      <p>Pick a family first, then open a technique with step-by-step visualization.</p>
    </div>
    <div className="dashboard-grid">
      {categories.map((category) => {
        const active = category.id === selectedCategoryId;
        const count = techniqueCounts[category.id] || 0;
        return (
          <button
            key={category.id}
            className={`dsa-card${active ? ' is-active' : ''}`}
            type="button"
            onClick={() => onSelectCategory(category.id)}
          >
            <span className="card-kicker">{count} techniques</span>
            <h3>{category.title}</h3>
            <p>{category.subtitle}</p>
          </button>
        );
      })}
    </div>
  </section>
);

export default Dashboard;
