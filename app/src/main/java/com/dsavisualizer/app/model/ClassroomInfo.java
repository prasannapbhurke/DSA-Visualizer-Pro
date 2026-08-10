package com.dsavisualizer.app.model;

public class ClassroomInfo {
    public final String id;
    public final String name;
    public final String description;
    public final String joinCode;
    public final String teacherName;
    public final int studentCount;
    public final String joinedAt;

    public ClassroomInfo(String id, String name, String description, String joinCode, String teacherName, int studentCount, String joinedAt) {
        this.id = id == null ? "" : id;
        this.name = name == null ? "" : name;
        this.description = description == null ? "" : description;
        this.joinCode = joinCode == null ? "" : joinCode;
        this.teacherName = teacherName == null ? "" : teacherName;
        this.studentCount = studentCount;
        this.joinedAt = joinedAt == null ? "" : joinedAt;
    }
}
