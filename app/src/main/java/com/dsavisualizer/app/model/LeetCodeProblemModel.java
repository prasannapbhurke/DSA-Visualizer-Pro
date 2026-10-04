package com.dsavisualizer.app.model;

/**
 * Model representing a LeetCode problem in the Master 2000 Roadmap.
 */
public class LeetCodeProblemModel {

    public final int order;
    public final int lcNumber;
    public final String title;
    public final String difficulty;
    public final String pattern;
    public final String section;
    public final int part;
    public final String companies;
    public final String slug;
    public final String url;

    public boolean isSolved;
    public boolean isBookmarked;

    public LeetCodeProblemModel(
            int order,
            int lcNumber,
            String title,
            String difficulty,
            String pattern,
            String section,
            int part,
            String companies,
            String slug,
            String url) {
        this.order = order;
        this.lcNumber = lcNumber;
        this.title = title == null ? "" : title;
        this.difficulty = difficulty == null ? "Medium" : difficulty;
        this.pattern = pattern == null ? "General" : pattern;
        this.section = section == null ? "Core Problem Set" : section;
        this.part = part;
        this.companies = companies == null ? "" : companies;
        this.slug = slug == null ? "" : slug;
        this.url = url == null ? "" : url;
    }

    public String getId() {
        return "lc_" + lcNumber;
    }

    public String getDisplayNumber() {
        return "#" + lcNumber;
    }

    public boolean matchesQuery(String query) {
        if (query == null || query.trim().isEmpty()) {
            return true;
        }
        String q = query.trim().toLowerCase();
        if (String.valueOf(lcNumber).equals(q) || ("#" + lcNumber).equals(q) || String.valueOf(order).equals(q)) {
            return true;
        }
        if (title.toLowerCase().contains(q)) {
            return true;
        }
        if (pattern.toLowerCase().contains(q)) {
            return true;
        }
        if (section.toLowerCase().contains(q)) {
            return true;
        }
        if (companies.toLowerCase().contains(q)) {
            return true;
        }
        return false;
    }
}
