const isPlainObject = (value) => value && typeof value === 'object' && !Array.isArray(value);

const createValidationError = (message) => ({
  ok: false,
  error: {
    code: 'VALIDATION_ERROR',
    message,
  },
});

const parseSaveSessionRequest = (body) => {
  if (!isPlainObject(body) || !isPlainObject(body.session)) {
    return createValidationError('A valid session object is required');
  }

  return {
    ok: true,
    value: {
      session: { ...body.session },
    },
  };
};

const parseSavePreferencesRequest = (body) => {
  if (!isPlainObject(body) || !isPlainObject(body.preferences)) {
    return createValidationError('A valid preferences object is required');
  }

  return {
    ok: true,
    value: {
      preferences: { ...body.preferences },
    },
  };
};

const parseShareVisualizationRequest = (body) => {
  if (!isPlainObject(body) || !isPlainObject(body.share)) {
    return createValidationError('A valid share object is required');
  }

  const { share } = body;
  if (typeof share.techniqueId !== 'string' || !share.techniqueId.trim()) {
    return createValidationError('share.techniqueId is required');
  }

  if (share.currentStep != null && (!Number.isInteger(share.currentStep) || share.currentStep < 0)) {
    return createValidationError('share.currentStep must be a non-negative integer');
  }

  return {
    ok: true,
    value: {
      share: {
        techniqueId: share.techniqueId.trim(),
        techniqueTitle: typeof share.techniqueTitle === 'string' ? share.techniqueTitle.trim() : '',
        categoryId: typeof share.categoryId === 'string' ? share.categoryId.trim() : '',
        currentStep: Number.isInteger(share.currentStep) ? share.currentStep : 0,
        note: typeof share.note === 'string' ? share.note.trim() : '',
      },
    },
  };
};

const parseGuestAuthRequest = (body) => {
  if (!isPlainObject(body)) {
    return createValidationError('Request body is required');
  }

  const displayName = typeof body?.displayName === 'string' && body.displayName.trim()
    ? body.displayName.trim()
    : 'Guest Learner';

  return {
    ok: true,
    value: {
      displayName,
    },
  };
};

const parseGoogleAuthRequest = (body) => {
  if (!isPlainObject(body)) {
    return createValidationError('Request body is required');
  }

  const idToken = typeof body?.idToken === 'string' && body.idToken.trim()
    ? body.idToken.trim()
    : null;

  if (!idToken) {
    return createValidationError('Valid idToken is required');
  }

  return {
    ok: true,
    value: {
      idToken,
    },
  };
};

const parseTechniqueProgressRequest = (body) => {
  if (!isPlainObject(body) || !isPlainObject(body.progress)) {
    return createValidationError('A valid progress object is required');
  }

  const progress = body.progress;
  const attempts = Number.isInteger(progress.attempts) && progress.attempts >= 0 ? progress.attempts : 0;
  const bestScore = typeof progress.bestScore === 'number' && progress.bestScore >= 0 ? progress.bestScore : 0;
  const completed = Boolean(progress.completed);
  const lastDifficulty = typeof progress.lastDifficulty === 'string' ? progress.lastDifficulty.trim() : '';
  const masteryLevel = typeof progress.masteryLevel === 'string' ? progress.masteryLevel.trim() : '';
  const lastPlayedAt = typeof progress.lastPlayedAt === 'string' ? progress.lastPlayedAt.trim() : '';

  return {
    ok: true,
    value: {
      progress: {
        attempts,
        bestScore,
        completed,
        lastDifficulty,
        masteryLevel,
        lastPlayedAt,
      },
    },
  };
};

const parsePracticeSessionRequest = (body) => {
  if (!isPlainObject(body) || !isPlainObject(body.session)) {
    return createValidationError('A valid practice session object is required');
  }

  const { session } = body;
  if (typeof session.techniqueId !== 'string' || !session.techniqueId.trim()) {
    return createValidationError('session.techniqueId is required');
  }

  return {
    ok: true,
    value: {
      session: {
        techniqueId: session.techniqueId.trim(),
        score: typeof session.score === 'number' && session.score >= 0 ? session.score : 0,
        difficulty: typeof session.difficulty === 'string' ? session.difficulty.trim() : '',
        durationMs: typeof session.durationMs === 'number' && session.durationMs >= 0 ? session.durationMs : 0,
      },
    },
  };
};

const parseFeedbackRequest = (body) => {
  if (!isPlainObject(body) || !isPlainObject(body.feedback)) {
    return createValidationError('A valid feedback object is required');
  }

  const { feedback } = body;
  const message = typeof feedback.message === 'string' ? feedback.message.trim() : '';
  if (!message) {
    return createValidationError('feedback.message is required');
  }

  return {
    ok: true,
    value: {
      feedback: {
        type: typeof feedback.type === 'string' ? feedback.type.trim() : '',
        message,
        techniqueId: typeof feedback.techniqueId === 'string' ? feedback.techniqueId.trim() : '',
        screen: typeof feedback.screen === 'string' ? feedback.screen.trim() : '',
        difficulty: typeof feedback.difficulty === 'string' ? feedback.difficulty.trim() : '',
      },
    },
  };
};

const parseCreateClassRequest = (body) => {
  if (!isPlainObject(body)) {
    return createValidationError('Request body is required');
  }

  const name = typeof body?.name === 'string' ? body.name.trim() : '';
  if (!name) {
    return createValidationError('name is required');
  }

  return {
    ok: true,
    value: {
      name,
      description: typeof body?.description === 'string' ? body.description.trim() : '',
    },
  };
};

const parseJoinClassRequest = (body) => {
  if (!isPlainObject(body)) {
    return createValidationError('Request body is required');
  }

  const joinCode = typeof body?.joinCode === 'string' ? body.joinCode.trim().toUpperCase() : '';
  if (!joinCode) {
    return createValidationError('joinCode is required');
  }

  return {
    ok: true,
    value: {
      joinCode,
    },
  };
};

const parseTeacherStudentFeedbackRequest = (body) => {
  if (!isPlainObject(body)) {
    return createValidationError('Request body is required');
  }

  const message = typeof body?.message === 'string' ? body.message.trim() : '';
  if (!message) {
    return createValidationError('message is required');
  }

  return {
    ok: true,
    value: {
      message,
      status: typeof body?.status === 'string' ? body.status.trim() : '',
    },
  };
};

const parseClassResourceUploadRequest = (body) => {
  if (!isPlainObject(body)) {
    return createValidationError('Request body is required');
  }

  const title = typeof body?.title === 'string' ? body.title.trim() : '';
  const type = typeof body?.type === 'string' ? body.type.trim() : '';
  const fileName = typeof body?.fileName === 'string' ? body.fileName.trim() : '';
  const mimeType = typeof body?.mimeType === 'string' ? body.mimeType.trim() : '';
  const base64Data = typeof body?.base64Data === 'string' ? body.base64Data.trim() : '';

  if (!title) {
    return createValidationError('title is required');
  }
  if (!type) {
    return createValidationError('type is required');
  }
  if (!fileName) {
    return createValidationError('fileName is required');
  }
  if (!mimeType) {
    return createValidationError('mimeType is required');
  }
  if (!base64Data) {
    return createValidationError('base64Data is required');
  }

  return {
    ok: true,
    value: {
      title,
      type,
      fileName,
      mimeType,
      base64Data,
    },
  };
};

const parseClassResourceUploadInitRequest = (body) => {
  if (!isPlainObject(body)) {
    return createValidationError('Request body is required');
  }

  const title = typeof body?.title === 'string' ? body.title.trim() : '';
  const type = typeof body?.type === 'string' ? body.type.trim() : '';
  const fileName = typeof body?.fileName === 'string' ? body.fileName.trim() : '';
  const mimeType = typeof body?.mimeType === 'string' ? body.mimeType.trim() : '';
  const sizeBytes = Number.isInteger(body?.sizeBytes) ? body.sizeBytes : Number(body?.sizeBytes);

  if (!title) {
    return createValidationError('title is required');
  }
  if (!type) {
    return createValidationError('type is required');
  }
  if (!fileName) {
    return createValidationError('fileName is required');
  }
  if (!mimeType) {
    return createValidationError('mimeType is required');
  }
  if (!Number.isFinite(sizeBytes) || sizeBytes <= 0) {
    return createValidationError('sizeBytes must be a positive number');
  }

  return {
    ok: true,
    value: {
      title,
      type,
      fileName,
      mimeType,
      sizeBytes: Math.floor(sizeBytes),
    },
  };
};

const parseClassResourceUploadCompleteRequest = (body) => {
  if (!isPlainObject(body)) {
    return createValidationError('Request body is required');
  }

  const resourceId = typeof body?.resourceId === 'string' ? body.resourceId.trim() : '';
  const title = typeof body?.title === 'string' ? body.title.trim() : '';
  const type = typeof body?.type === 'string' ? body.type.trim() : '';
  const fileName = typeof body?.fileName === 'string' ? body.fileName.trim() : '';
  const mimeType = typeof body?.mimeType === 'string' ? body.mimeType.trim() : '';
  const sizeBytes = Number.isInteger(body?.sizeBytes) ? body.sizeBytes : Number(body?.sizeBytes);

  if (!resourceId) {
    return createValidationError('resourceId is required');
  }
  if (!title) {
    return createValidationError('title is required');
  }
  if (!type) {
    return createValidationError('type is required');
  }
  if (!fileName) {
    return createValidationError('fileName is required');
  }
  if (!mimeType) {
    return createValidationError('mimeType is required');
  }
  if (!Number.isFinite(sizeBytes) || sizeBytes <= 0) {
    return createValidationError('sizeBytes must be a positive number');
  }

  return {
    ok: true,
    value: {
      resourceId,
      title,
      type,
      fileName,
      mimeType,
      sizeBytes: Math.floor(sizeBytes),
    },
  };
};

const parseRestoreBackupRequest = (body) => {
  if (!isPlainObject(body) || !isPlainObject(body.backupData)) {
    return createValidationError('backupData object is required');
  }

  return {
    ok: true,
    value: {
      backupData: { ...body.backupData },
    },
  };
};

const parseReviewFeedbackRequest = (body) => {
  if (!isPlainObject(body)) {
    return createValidationError('Request body is required');
  }

  return {
    ok: true,
    value: {
      reviewed: body.reviewed !== false,
    },
  };
};

const statusResponseDto = () => ({
  data: {
    status: 'Server is running',
    timestamp: new Date().toISOString(),
  },
});

const sessionsResponseDto = (sessions) => ({
  data: {
    sessions,
  },
});

const preferencesResponseDto = (preferences) => ({
  data: {
    preferences,
  },
});

const successMessageResponseDto = (message) => ({
  data: {
    message,
  },
});

const googleAuthResponseDto = ({ token, refreshToken, user }) => authResponseDto({ token, refreshToken, user });

const userResponseDto = (user) => ({
  data: {
    user,
  },
});

const usersResponseDto = (users) => ({
  data: {
    users,
  },
});

const feedbackResponseDto = (feedback) => ({
  data: {
    feedback,
  },
});

const classResponseDto = (classroom) => ({
  data: {
    classroom,
  },
});

const classesResponseDto = (classes) => ({
  data: {
    classes,
  },
});

const classStudentsResponseDto = ({ classroom, students }) => ({
  data: {
    classroom,
    students,
  },
});

const classResourcesResponseDto = (resources) => ({
  data: {
    resources,
  },
});

const classResourceCreatedResponseDto = (resource) => ({
  data: {
    resource,
  },
});

const analyticsResponseDto = (analytics) => ({
  data: {
    analytics,
  },
});

const auditLogsResponseDto = (auditLogs) => ({
  data: {
    auditLogs,
  },
});

const backupResponseDto = (backup) => ({
  data: {
    backup,
  },
});

const teacherFeedbackResponseDto = (feedback) => ({
  data: {
    feedback,
  },
});

const studentDetailResponseDto = (student) => ({
  data: {
    student,
  },
});

const teacherNoteCreatedResponseDto = (note) => ({
  data: {
    note,
  },
});

const authResponseDto = ({ token, refreshToken, user }) => ({
  data: {
    token,
    refreshToken: refreshToken || '',
    user,
  },
});

const meResponseDto = (user) => ({
  data: {
    user,
  },
});

const logoutResponseDto = () => ({
  data: {
    message: 'Logged out successfully',
  },
});

const progressResponseDto = (progress) => ({
  data: {
    progress,
  },
});

const practiceSessionsResponseDto = (sessions) => ({
  data: {
    sessions,
  },
});

const practiceSessionCreatedResponseDto = (session) => ({
  data: {
    session,
  },
});

const progressEntriesResponseDto = (progressEntries) => ({
  data: {
    progressEntries,
  },
});

const dashboardResponseDto = (dashboard) => ({
  data: {
    dashboard,
  },
});

const shareCreatedResponseDto = (shareId) => ({
  data: {
    shareId,
  },
});

const feedbackCreatedResponseDto = (feedback) => ({
  data: {
    feedback,
  },
});

const sharedVisualizationResponseDto = (share) => ({
  data: {
    share,
  },
});

const parseSaveMistakeRequest = (body) => {
  if (!isPlainObject(body) || !isPlainObject(body.mistake)) {
    return createValidationError('A valid mistake object is required');
  }

  const { mistake } = body;
  if (typeof mistake.techniqueId !== 'string' || !mistake.techniqueId.trim()) {
    return createValidationError('mistake.techniqueId is required');
  }

  if (typeof mistake.question !== 'string' || !mistake.question.trim()) {
    return createValidationError('mistake.question is required');
  }

  if (typeof mistake.correctAnswer !== 'string' || !mistake.correctAnswer.trim()) {
    return createValidationError('mistake.correctAnswer is required');
  }

  if (typeof mistake.userAnswer !== 'string' || !mistake.userAnswer.trim()) {
    return createValidationError('mistake.userAnswer is required');
  }

  return {
    ok: true,
    value: {
      mistake: { ...mistake },
    },
  };
};

const mistakeCreatedResponseDto = (mistake) => ({
  data: {
    mistake,
  },
});

const mistakesResponseDto = (mistakes) => ({
  data: {
    mistakes,
  },
});

const errorResponseDto = (message, code = 'INTERNAL_ERROR') => ({
  error: {
    code,
    message,
  },
});

module.exports = {
  authResponseDto,
  analyticsResponseDto,
  auditLogsResponseDto,
  backupResponseDto,
  errorResponseDto,
  logoutResponseDto,
  meResponseDto,
  dashboardResponseDto,
  feedbackCreatedResponseDto,
  googleAuthResponseDto,
  classResponseDto,
  classesResponseDto,
  classStudentsResponseDto,
  classResourcesResponseDto,
  classResourceCreatedResponseDto,
  feedbackResponseDto,
  mistakeCreatedResponseDto,
  mistakesResponseDto,
  parseCreateClassRequest,
  parseFeedbackRequest,
  parseGoogleAuthRequest,
  parseGuestAuthRequest,
  parseJoinClassRequest,
  parsePracticeSessionRequest,
  parseReviewFeedbackRequest,
  parseRestoreBackupRequest,
  parseSaveMistakeRequest,
  parseSavePreferencesRequest,
  parseSaveSessionRequest,
  parseShareVisualizationRequest,
  parseClassResourceUploadCompleteRequest,
  parseClassResourceUploadInitRequest,
  parseClassResourceUploadRequest,
  parseTeacherStudentFeedbackRequest,
  parseTechniqueProgressRequest,
  practiceSessionCreatedResponseDto,
  practiceSessionsResponseDto,
  preferencesResponseDto,
  progressEntriesResponseDto,
  progressResponseDto,
  shareCreatedResponseDto,
  sharedVisualizationResponseDto,
  sessionsResponseDto,
  statusResponseDto,
  studentDetailResponseDto,
  successMessageResponseDto,
  teacherFeedbackResponseDto,
  teacherNoteCreatedResponseDto,
  userResponseDto,
  usersResponseDto,
};
