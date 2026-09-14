const { sanitizeUser, sanitizeGoogleUser } = require('./sanitize');

const DAILY_GOAL_TARGET = 3;

const toIsoDay = (value) => {
  if (!value) {
    return '';
  }
  const date = new Date(value);
  if (Number.isNaN(date.getTime())) {
    return '';
  }
  return date.toISOString().slice(0, 10);
};

const calculateStreakDays = (sessions) => {
  const dayKeys = new Set();
  for (const session of sessions || []) {
    const day = toIsoDay(session.createdAt);
    if (day) {
      dayKeys.add(day);
    }
  }

  let streak = 0;
  const cursor = new Date();
  cursor.setHours(0, 0, 0, 0);

  while (true) {
    const key = cursor.toISOString().slice(0, 10);
    if (!dayKeys.has(key)) {
      break;
    }
    streak += 1;
    cursor.setDate(cursor.getDate() - 1);
  }

  return streak;
};

const buildDashboard = ({ user, progressEntries, practiceSessions }) => {
  const sortedSessions = [...(practiceSessions || [])].sort((left, right) =>
    String(right.createdAt || '').localeCompare(String(left.createdAt || ''))
  );
  const recentActivity = sortedSessions.slice(0, 5).map((session) => ({
    techniqueId: session.techniqueId,
    difficulty: session.difficulty || '',
    score: session.score || 0,
    createdAt: session.createdAt || '',
    durationMs: session.durationMs || 0,
  }));

  const todayKey = new Date().toISOString().slice(0, 10);
  const todaySessions = sortedSessions.filter((session) => toIsoDay(session.createdAt) === todayKey).length;
  const completedTechniques = (progressEntries || []).filter((entry) => entry.completed).length;

  const weakTopics = [...(progressEntries || [])]
    .sort((left, right) => {
      const leftScore = Number(left.bestScore || 0);
      const rightScore = Number(right.bestScore || 0);
      if (leftScore !== rightScore) {
        return leftScore - rightScore;
      }
      return Number(right.attempts || 0) - Number(left.attempts || 0);
    })
    .slice(0, 3)
    .map((entry) => ({
      techniqueId: entry.techniqueId,
      bestScore: entry.bestScore || 0,
      attempts: entry.attempts || 0,
      masteryLevel: entry.masteryLevel || '',
      lastDifficulty: entry.lastDifficulty || '',
    }));

  return {
    user: sanitizeUser(user),
    summary: {
      totalTechniquesStarted: (progressEntries || []).length,
      completedTechniques,
      totalPracticeSessions: sortedSessions.length,
      streakDays: calculateStreakDays(sortedSessions),
      todaySessions,
      dailyGoalTarget: DAILY_GOAL_TARGET,
      dailyGoalCompleted: todaySessions >= DAILY_GOAL_TARGET,
      weakTopicCount: weakTopics.length,
    },
    recentActivity,
    weakTopics,
    progressEntries: [...(progressEntries || [])].sort((left, right) =>
      String(right.updatedAt || right.lastPlayedAt || '').localeCompare(String(left.updatedAt || left.lastPlayedAt || ''))
    ),
  };
};

const buildStudentSummary = ({ student, data }) => {
  const progressEntries = (data.progressEntries || []).filter((entry) => entry.userId === student.id);
  const practiceSessions = (data.practiceSessions || []).filter((entry) => entry.userId === student.id);
  const dashboard = buildDashboard({ user: student, progressEntries, practiceSessions });
  const latestActivity = dashboard.recentActivity.length > 0 ? dashboard.recentActivity[0].createdAt : '';
  return {
    user: sanitizeGoogleUser(student),
    summary: dashboard.summary,
    weakTopics: dashboard.weakTopics,
    recentActivity: dashboard.recentActivity.slice(0, 3),
    lastActiveAt: latestActivity || student.lastSeenAt || '',
  };
};

module.exports = {
  DAILY_GOAL_TARGET,
  toIsoDay,
  calculateStreakDays,
  buildDashboard,
  buildStudentSummary,
};
