package com.dsavisualizer.app.model;

public class ClassroomStudentSummary {
    public final String userId;
    public final String displayName;
    public final String email;
    public final String rollNumber;
    public final String studentBatch;
    public final int totalTechniquesStarted;
    public final int completedTechniques;
    public final int totalPracticeSessions;
    public final int averageQuizScore;
    public final int streakDays;
    public final String lastActiveAt;
    public final String joinedAt;

    public ClassroomStudentSummary(
        String userId,
        String displayName,
        String email,
        String rollNumber,
        String studentBatch,
        int totalTechniquesStarted,
        int completedTechniques,
        int totalPracticeSessions,
        int streakDays,
        String lastActiveAt,
        String joinedAt
    ) {
        this(
            userId,
            displayName,
            email,
            rollNumber,
            studentBatch,
            totalTechniquesStarted,
            completedTechniques,
            totalPracticeSessions,
            0,
            streakDays,
            lastActiveAt,
            joinedAt
        );
    }

    public ClassroomStudentSummary(
        String userId,
        String displayName,
        String email,
        String rollNumber,
        String studentBatch,
        int totalTechniquesStarted,
        int completedTechniques,
        int totalPracticeSessions,
        int averageQuizScore,
        int streakDays,
        String lastActiveAt,
        String joinedAt
    ) {
        this.userId = userId == null ? "" : userId;
        this.displayName = displayName == null ? "" : displayName;
        this.email = email == null ? "" : email;
        this.rollNumber = rollNumber == null ? "" : rollNumber;
        this.studentBatch = studentBatch == null ? "" : studentBatch;
        this.totalTechniquesStarted = totalTechniquesStarted;
        this.completedTechniques = completedTechniques;
        this.totalPracticeSessions = totalPracticeSessions;
        this.averageQuizScore = averageQuizScore;
        this.streakDays = streakDays;
        this.lastActiveAt = lastActiveAt == null ? "" : lastActiveAt;
        this.joinedAt = joinedAt == null ? "" : joinedAt;
    }
}
