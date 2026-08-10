package com.dsavisualizer.app.model;

public class ClassroomStudentSummary {
    public final String userId;
    public final String displayName;
    public final String email;
    public final int totalTechniquesStarted;
    public final int completedTechniques;
    public final int totalPracticeSessions;
    public final int streakDays;
    public final String lastActiveAt;
    public final String joinedAt;

    public ClassroomStudentSummary(
        String userId,
        String displayName,
        String email,
        int totalTechniquesStarted,
        int completedTechniques,
        int totalPracticeSessions,
        int streakDays,
        String lastActiveAt,
        String joinedAt
    ) {
        this.userId = userId == null ? "" : userId;
        this.displayName = displayName == null ? "" : displayName;
        this.email = email == null ? "" : email;
        this.totalTechniquesStarted = totalTechniquesStarted;
        this.completedTechniques = completedTechniques;
        this.totalPracticeSessions = totalPracticeSessions;
        this.streakDays = streakDays;
        this.lastActiveAt = lastActiveAt == null ? "" : lastActiveAt;
        this.joinedAt = joinedAt == null ? "" : joinedAt;
    }
}
