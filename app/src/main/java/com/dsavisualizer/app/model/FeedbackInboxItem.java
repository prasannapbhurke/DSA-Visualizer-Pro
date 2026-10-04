package com.dsavisualizer.app.model;

public class FeedbackInboxItem {
    public final String id;
    public final String type;
    public final String message;
    public final String techniqueId;
    public final String screen;
    public final String difficulty;
    public final String createdAt;
    public final String userDisplayName;
    public final String userEmail;
    public final String userRole;
    public final String classId;
    public final String className;
    public final String reviewedAt;
    public final String reviewedBy;
    public final String adminReply;
    public final String repliedAt;

    public FeedbackInboxItem(
        String id,
        String type,
        String message,
        String techniqueId,
        String screen,
        String difficulty,
        String createdAt,
        String userDisplayName,
        String userEmail,
        String userRole,
        String classId,
        String className,
        String reviewedAt,
        String reviewedBy
    ) {
        this(id, type, message, techniqueId, screen, difficulty, createdAt, userDisplayName, userEmail, userRole, classId, className, reviewedAt, reviewedBy, "", "");
    }

    public FeedbackInboxItem(
        String id,
        String type,
        String message,
        String techniqueId,
        String screen,
        String difficulty,
        String createdAt,
        String userDisplayName,
        String userEmail,
        String userRole,
        String classId,
        String className,
        String reviewedAt,
        String reviewedBy,
        String adminReply,
        String repliedAt
    ) {
        this.id = id == null ? "" : id;
        this.type = type == null ? "" : type;
        this.message = message == null ? "" : message;
        this.techniqueId = techniqueId == null ? "" : techniqueId;
        this.screen = screen == null ? "" : screen;
        this.difficulty = difficulty == null ? "" : difficulty;
        this.createdAt = createdAt == null ? "" : createdAt;
        this.userDisplayName = userDisplayName == null ? "" : userDisplayName;
        this.userEmail = userEmail == null ? "" : userEmail;
        this.userRole = userRole == null ? "" : userRole;
        this.classId = classId == null ? "" : classId;
        this.className = className == null ? "" : className;
        this.reviewedAt = reviewedAt == null ? "" : reviewedAt;
        this.reviewedBy = reviewedBy == null ? "" : reviewedBy;
        this.adminReply = adminReply == null ? "" : adminReply;
        this.repliedAt = repliedAt == null ? "" : repliedAt;
    }
}
