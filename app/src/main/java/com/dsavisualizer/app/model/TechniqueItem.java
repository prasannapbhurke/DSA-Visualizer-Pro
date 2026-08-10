package com.dsavisualizer.app.model;

public class TechniqueItem {
    public final String id;
    public final String categoryId;
    public final String title;
    public final String subtitle;
    public final String difficulty;

    public TechniqueItem(String id, String categoryId, String title, String subtitle, String difficulty) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.subtitle = subtitle;
        this.difficulty = difficulty;
    }
}
