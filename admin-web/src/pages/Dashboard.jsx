import { useEffect, useState, useRef } from 'react';
import { motion } from 'framer-motion';
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend, ArcElement, Filler } from 'chart.js';
import { Line, Doughnut } from 'react-chartjs-2';
import { api } from '../api/api';
import { useRealtime } from '../hooks/useRealtime';

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend, ArcElement, Filler);

const StatCard = ({ title, value, subtitle }) => (
  <motion.article 
    initial={{ opacity: 0, y: 20 }}
    animate={{ opacity: 1, y: 0 }}
    className="stat-card"
  >
    <span>{title}</span>
    <strong>{value ?? '--'}</strong>
    <small>{subtitle}</small>
  </motion.article>
);

const Dashboard = ({ user }) => {
  const [stats, setStats] = useState(null);

  const fetchStats = async () => {
    try {
      const res = await api('/api/admin/stats');
      setStats(res.data.stats);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchStats();
  }, []);

  useRealtime(fetchStats);

  const engagementData = {
    labels: (stats?.dailyEngagement || []).map(d => {
      const dt = new Date(d.date);
      return `${dt.getMonth() + 1}/${dt.getDate()}`;
    }),
    datasets: [{
      label: 'Practice Sessions',
      data: (stats?.dailyEngagement || []).map(d => d.count),
      borderColor: '#6366f1',
      backgroundColor: 'rgba(99, 102, 241, 0.1)',
      fill: true,
      tension: 0.4
    }]
  };

  const topicsData = {
    labels: (stats?.topAlgorithms || []).map(t => t.id),
    datasets: [{
      data: (stats?.topAlgorithms || []).map(t => t.count),
      backgroundColor: ['#6366f1', '#ec4899', '#14b8a6', '#f59e0b', '#8b5cf6'],
      borderWidth: 0
    }]
  };

  return (
    <section className="section active-section">
      <div className="stats-grid">
        <StatCard title="Total users" value={stats?.totalUsers} subtitle="Google accounts" />
        <StatCard title="Active 30d" value={stats?.activeUsersLast30Days} subtitle="Recent learners" />
        <StatCard title="Classes" value={stats?.totalClasses} subtitle="Visible to your role" />
        <StatCard title="Completion" value={stats ? `${stats.completionRate}%` : '--'} subtitle="Completed progress" />
      </div>

      <div className="panel-grid">
        <motion.article 
          initial={{ opacity: 0, scale: 0.95 }}
          animate={{ opacity: 1, scale: 1 }}
          transition={{ delay: 0.1 }}
          className="panel" style={{ gridColumn: 'span 2' }}
        >
          <div className="panel-heading">
            <h2>Student Engagement Over Time</h2>
          </div>
          <div className="chart-container" style={{ position: 'relative', height: '300px', width: '100%' }}>
            <Line data={engagementData} options={{ responsive: true, maintainAspectRatio: false, plugins: { legend: { display: false } }, scales: { y: { beginAtZero: true, grid: { color: 'rgba(255,255,255,0.1)' } }, x: { grid: { display: false } } } }} />
          </div>
        </motion.article>
        
        <motion.article 
          initial={{ opacity: 0, scale: 0.95 }}
          animate={{ opacity: 1, scale: 1 }}
          transition={{ delay: 0.2 }}
          className="panel"
        >
          <div className="panel-heading">
            <h2>Most Used Topics</h2>
          </div>
          <div className="chart-container" style={{ position: 'relative', height: '300px', width: '100%' }}>
            <Doughnut data={topicsData} options={{ responsive: true, maintainAspectRatio: false, plugins: { legend: { position: 'right', labels: { color: '#e2e8f0', padding: 20 } } } }} />
          </div>
        </motion.article>
      </div>
    </section>
  );
};

export default Dashboard;
