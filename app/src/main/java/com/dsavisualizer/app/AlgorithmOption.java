package com.dsavisualizer.app;

/**
 * Lightweight UI model for searchable algorithm selection rows.
 */
public class AlgorithmOption {
    public final String title;
    public final String group;
    public final String subtitle;
    public final String difficulty;

    public AlgorithmOption(String title, String group, String subtitle, String difficulty) {
        this.title = title;
        this.group = group;
        this.subtitle = subtitle;
        this.difficulty = difficulty;
    }

    public String getBadgeText() {
        String[] words = group.split("\\s+");
        if (words.length == 0) {
            return "A";
        }

        if (words.length == 1) {
            return words[0].substring(0, 1).toUpperCase();
        }

        return (words[0].substring(0, 1) + words[1].substring(0, 1)).toUpperCase();
    }

    @Override
    public String toString() {
        return title;
    }
}
