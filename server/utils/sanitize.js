const sanitizeUser = (user) => ({
  id: user.id,
  type: user.type || user.provider || 'user',
  displayName: user.displayName || user.name || user.email || '',
  createdAt: user.createdAt,
  lastSeenAt: user.lastSeenAt,
  role: user.role || '',
  email: user.email || '',
});

const sanitizeGoogleUser = (user) => ({
  id: user.id,
  googleId: user.googleId,
  email: user.email,
  name: user.name,
  displayName: user.name,
  role: user.role,
  createdAt: user.createdAt,
  lastSeenAt: user.lastSeenAt,
});

const sanitizeClassroom = (classroom, extras = {}) => ({
  id: classroom.id,
  name: classroom.name,
  description: classroom.description || '',
  joinCode: classroom.joinCode,
  teacherId: classroom.teacherId,
  teacherName: extras.teacherName || '',
  createdAt: classroom.createdAt,
  updatedAt: classroom.updatedAt || classroom.createdAt,
  studentCount: extras.studentCount || 0,
  joinedAt: extras.joinedAt || '',
});

module.exports = {
  sanitizeUser,
  sanitizeGoogleUser,
  sanitizeClassroom,
};
