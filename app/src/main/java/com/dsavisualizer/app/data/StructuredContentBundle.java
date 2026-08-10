package com.dsavisualizer.app.data;

import com.dsavisualizer.app.model.CategoryItem;
import com.dsavisualizer.app.model.TechniqueContent;
import com.dsavisualizer.app.model.TechniqueItem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory container for JSON-backed content loaded from assets.
 * This lets the repository overlay structured content without breaking
 * the existing hardcoded fallback content.
 */
final class StructuredContentBundle {

    int catalogVersion = 1;
    final List<CategoryItem> categories = new ArrayList<>();
    final List<TechniqueItem> techniques = new ArrayList<>();
    final Map<String, TechniqueContent> contentById = new LinkedHashMap<>();
    final List<String> errors = new ArrayList<>();
}
