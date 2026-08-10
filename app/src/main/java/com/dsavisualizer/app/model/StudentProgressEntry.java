package com.dsavisualizer.app.model;

public class StudentProgressEntry {
    public final String techniqueId;
    public final int attempts;
    public final int bestScore;
    public final boolean completed;
    public final String masteryLevel;
    public final String lastDifficulty;
    public final String lastPlayedAt;

    public StudentProgressEntry(String techniqueId, int attempts, int bestScore, boolean completed, String masteryLevel, String lastDifficulty, String lastPlayedAt) {
        this.techniqueId = techniqueId == null ? "" : techniqueId;
        this.attempts = attempts;
        this.bestScore = bestScore;
        this.completed = completed;
        this.masteryLevel = masteryLevel == null ? "" : masteryLevel;
        this.lastDifficulty = lastDifficulty == null ? "" : lastDifficulty;
        this.lastPlayedAt = lastPlayedAt == null ? "" : lastPlayedAt;
    }
}
