package com.dsavisualizer.app.model;

public class CategoryItem {
    public final String id;
    public final String title;
    public final String subtitle;
    public final String accentHex;

    public CategoryItem(String id, String title, String subtitle, String accentHex) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.accentHex = accentHex;
    }
}
