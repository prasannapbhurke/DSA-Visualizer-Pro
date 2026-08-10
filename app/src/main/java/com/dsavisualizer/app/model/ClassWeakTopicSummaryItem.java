package com.dsavisualizer.app.model;

public class ClassWeakTopicSummaryItem {
    public final String techniqueId;
    public final int studentCount;
    public final int lowestBestScore;

    public ClassWeakTopicSummaryItem(String techniqueId, int studentCount, int lowestBestScore) {
        this.techniqueId = techniqueId == null ? "" : techniqueId;
        this.studentCount = studentCount;
        this.lowestBestScore = lowestBestScore;
    }
}
