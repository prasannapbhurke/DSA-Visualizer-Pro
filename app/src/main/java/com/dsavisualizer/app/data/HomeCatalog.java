package com.dsavisualizer.app.data;

import com.dsavisualizer.app.model.CategoryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Lightweight category source for the home screen so app launch does not need
 * to load the full technique repository.
 */
public final class HomeCatalog {

    private HomeCatalog() {
    }

    public static List<CategoryItem> getCategories() {
        List<CategoryItem> categories = new ArrayList<>();
        categories.add(new CategoryItem("searching", "Searching", "Find data quickly and clearly", "#4ADE80"));
        categories.add(new CategoryItem("sorting", "Sorting", "Arrange items step by step", "#60A5FA"));
        categories.add(new CategoryItem("stack", "Stack", "Last in, first out structure", "#F59E0B"));
        categories.add(new CategoryItem("queue", "Queue", "First in, first out structure", "#22D3EE"));
        categories.add(new CategoryItem("linked_list", "Linked List", "Connected node based storage", "#A78BFA"));
        categories.add(new CategoryItem("trees", "Trees", "Hierarchical node structures", "#FBBF24"));
        categories.add(new CategoryItem("graph", "Graph", "Vertex and edge relationships", "#FB7185"));
        categories.add(new CategoryItem("dynamic_programming", "Dynamic Programming", "Optimal subproblems and memoization", "#6366F1"));
        categories.add(new CategoryItem("backtracking", "Backtracking", "Constraint satisfaction & state exploration", "#EC4899"));
        categories.add(new CategoryItem("string_pattern", "String & Pattern Matching", "Fast substring search & text processing", "#06B6D4"));
        categories.add(new CategoryItem("advanced_ds", "Advanced Data Structures", "Tries, trees, bloom filters and caches", "#8B5CF6"));
        // Temporarily disabled from UI sections - preserved to add back later:
        // categories.add(new CategoryItem("specialized", "Specialized & Math Algorithms", "Geometry, number theory, FFT & greedy", "#10B981"));
        // categories.add(new CategoryItem("leetcode", "🔥 LeetCode Master 2000", "8 Parts • 2,000 Curated Problems with Search", "#F59E0B"));
        return categories;
    }
}
