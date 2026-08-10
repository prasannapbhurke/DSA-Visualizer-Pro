package com.dsavisualizer.app.model;

public class TeacherNote {
    public final String message;
    public final String status;
    public final String createdAt;
    public final String teacherName;

    public TeacherNote(String message, String status, String createdAt, String teacherName) {
        this.message = message == null ? "" : message;
        this.status = status == null ? "" : status;
        this.createdAt = createdAt == null ? "" : createdAt;
        this.teacherName = teacherName == null ? "" : teacherName;
    }
}
