import { useEffect, useState } from 'react';
import { motion } from 'framer-motion';
import { useNavigate } from 'react-router-dom';
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend, ArcElement, Filler } from 'chart.js';
import { Line, Doughnut } from 'react-chartjs-2';
import { api } from '../api/api';
import { useRealtime } from '../hooks/useRealtime';

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend, ArcElement, Filler);

const StatCard = ({ title, value, subtitle, highlight, onClick }) => (
  <motion.article 
    initial={{ opacity: 0, y: 15 }}
    animate={{ opacity: 1, y: 0 }}
    className="stat-card"
    onClick={onClick}
    style={{
      cursor: onClick ? 'pointer' : 'default',
      border: highlight ? '1px solid rgba(245, 158, 11, 0.4)' : undefined,
      background: highlight ? 'rgba(245, 158, 11, 0.05)' : undefined,
    }}
  >
    <span>{title}</span>
    <strong style={{ color: highlight ? '#f59e0b' : undefined }}>{value ?? '--'}</strong>
    <small>{subtitle}</small>
  </motion.article>
);

const Dashboard = ({ user }) => {
  const navigate = useNavigate();
  const [overview, setOverview] = useState(null);
  const [stats, setStats] = useState(null);

  const fetchOverview = async () => {
    try {
      const res = await api('/api/control-center/overview');
      setOverview(res.data || res);
    } catch (err) {
      console.warn('Overview fetch error:', err.message);
    }
  };

  const fetchStats = async () => {
    try {
      const res = await api('/api/admin/stats');
      setStats(res.data?.stats || res.stats);
    } catch (err) {
      console.warn('Stats fetch error:', err.message);
    }
  };

  useEffect(() => {
    fetchOverview();
    fetchStats();
  }, []);

  useRealtime(() => {
    fetchOverview();
    fetchStats();
  });

  const totalUsers = overview?.stats?.totalUsers ?? stats?.totalUsers ?? 0;
  const totalStudents = overview?.stats?.totalStudents ?? 0;
  const unreviewedFeedback = overview?.stats?.unreviewedFeedback ?? 0;
  const totalClasses = overview?.stats?.totalClasses ?? stats?.totalClasses ?? 0;
  const announcement = overview?.settings?.announcement || '';
  const isMaintenance = overview?.settings?.maintenanceMode || false;

  const engagementData = {
    labels: (stats?.dailyEngagement || []).map((d) => {
      const dt = new Date(d.date);
      return `${dt.getMonth() + 1}/${dt.getDate()}`;
    }),
    datasets: [{
      label: 'Android Practice Sessions',
      data: (stats?.dailyEngagement || []).map((d) => d.count),
      borderColor: '#38bdf8',
      backgroundColor: 'rgba(56, 189, 248, 0.1)',
      fill: true,
      tension: 0.4,
    }],
  };

  const topicsList = overview?.topAlgorithms || stats?.topAlgorithms || [];
  const topicsData = {
    labels: topicsList.map((t) => t.id),
    datasets: [{
      data: topicsList.map((t) => t.count),
      backgroundColor: ['#38bdf8', '#ec4899', '#10b981', '#f59e0b', '#8b5cf6'],
      borderWidth: 0,
    }],
  };

  return (
    <section className="section active-section">
      {/* Real-time In-App Status Banner */}
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(280px, 1fr))', gap: '14px', marginBottom: '20px' }}>
        <div style={{ background: '#0e1726', border: '1px solid rgba(255,255,255,0.08)', borderRadius: '12px', padding: '16px' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '6px' }}>
            <span style={{ fontSize: '0.8rem', color: '#94a3b8', textTransform: 'uppercase' }}>📢 In-App Announcement</span>
            <button
              type="button"
              className="row-btn"
              onClick={() => navigate('/config')}
              style={{ fontSize: '0.75rem', padding: '2px 8px' }}
            >
              Configure
            </button>
          </div>
          <p style={{ margin: 0, color: announcement ? '#38bdf8' : '#64748b', fontSize: '0.9rem', fontStyle: announcement ? 'normal' : 'italic' }}>
            {announcement ? `"${announcement}"` : 'No broadcast announcement currently active in Android app.'}
          </p>
        </div>

        <div style={{ background: '#0e1726', border: isMaintenance ? '1px solid #ef4444' : '1px solid rgba(255,255,255,0.08)', borderRadius: '12px', padding: '16px' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '6px' }}>
            <span style={{ fontSize: '0.8rem', color: '#94a3b8', textTransform: 'uppercase' }}>🔒 Android Access Status</span>
            <button
              type="button"
              className="row-btn"
              onClick={() => navigate('/config')}
              style={{ fontSize: '0.75rem', padding: '2px 8px' }}
            >
              Manage
            </button>
          </div>
          <p style={{ margin: 0, color: isMaintenance ? '#ef4444' : '#10b981', fontWeight: 600, fontSize: '0.95rem' }}>
            {isMaintenance ? '⚠️ Emergency Maintenance Mode Active' : '● Normal Student Operations Active'}
          </p>
        </div>
      </div>

      {/* Primary KPI Stats Cards */}
      <div className="stats-grid">
        <StatCard
          title="Android App Users"
          value={totalUsers}
          subtitle={`${totalStudents} Students`}
          onClick={() => navigate('/users')}
        />
        <StatCard
          title="Pending Feedback"
          value={unreviewedFeedback}
          subtitle="Bugs & questions"
          highlight={unreviewedFeedback > 0}
          onClick={() => navigate('/feedback')}
        />
        <StatCard
          title="Active Classes"
          value={totalClasses}
          subtitle="Classrooms & rosters"
          onClick={() => navigate('/classes')}
        />
        <StatCard
          title="Practice Sessions"
          value={overview?.stats?.totalSessions ?? stats?.totalSessions ?? '--'}
          subtitle="Android algorithm runs"
        />
      </div>

      {/* Charts and Visual Analytics */}
      <div className="panel-grid" style={{ marginTop: '20px' }}>
        <motion.article 
          initial={{ opacity: 0, scale: 0.95 }}
          animate={{ opacity: 1, scale: 1 }}
          transition={{ delay: 0.1 }}
          className="panel" style={{ gridColumn: 'span 2' }}
        >
          <div className="panel-heading">
            <h2>Android Student Practice Over Time</h2>
          </div>
          <div className="chart-container" style={{ position: 'relative', height: '280px', width: '100%' }}>
            <Line
              data={engagementData}
              options={{
                responsive: true,
                maintainAspectRatio: false,
                plugins: { legend: { display: false } },
                scales: {
                  y: { beginAtZero: true, grid: { color: 'rgba(255,255,255,0.07)' } },
                  x: { grid: { display: false } },
                },
              }}
            />
          </div>
        </motion.article>
        
        <motion.article 
          initial={{ opacity: 0, scale: 0.95 }}
          animate={{ opacity: 1, scale: 1 }}
          transition={{ delay: 0.2 }}
          className="panel"
        >
          <div className="panel-heading">
            <h2>Top Practiced Algorithms</h2>
          </div>
          <div className="chart-container" style={{ position: 'relative', height: '280px', width: '100%' }}>
            <Doughnut
              data={topicsData}
              options={{
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                  legend: { position: 'bottom', labels: { color: '#e2e8f0', padding: 12 } },
                },
              }}
            />
          </div>
        </motion.article>
      </div>
    </section>
  );
};

export default Dashboard;
