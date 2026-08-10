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
        categories.add(new CategoryItem("foundations", "Foundations", "Core thinking patterns and basics", "#F97316"));
        categories.add(new CategoryItem("advanced", "Advanced", "More specialized DSA topics", "#14B8A6"));
        return categories;
    }
}
