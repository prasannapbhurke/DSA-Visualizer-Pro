package com.dsavisualizer.app.model;

public class ManagedUser {
    public final String id;
    public final String displayName;
    public final String email;
    public final String role;
    public final String lastSeenAt;
    public final String createdAt;

    public ManagedUser(String id, String displayName, String email, String role, String lastSeenAt, String createdAt) {
        this.id = id == null ? "" : id;
        this.displayName = displayName == null ? "" : displayName;
        this.email = email == null ? "" : email;
        this.role = role == null ? "" : role;
        this.lastSeenAt = lastSeenAt == null ? "" : lastSeenAt;
        this.createdAt = createdAt == null ? "" : createdAt;
    }

    public String getPrimaryLabel() {
        if (!displayName.trim().isEmpty()) {
            return displayName;
        }
        if (!email.trim().isEmpty()) {
            return email;
        }
        return "User";
    }
}
