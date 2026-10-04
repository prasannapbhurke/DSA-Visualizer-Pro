package com.dsavisualizer.app.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.dsavisualizer.app.model.TechniqueContent;
import com.dsavisualizer.app.model.TechniqueItem;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryContentExhaustiveTest {

    private static final String[] TARGET_CATEGORIES = {
        "trees",
        "graph",
        "dynamic_programming",
        "backtracking",
        "string_pattern",
        "advanced_ds",
        "specialized"
    };

    private static final String[] STYLES = { "Iterative", "Functions", "Recursion" };
    private static final String[] LANGUAGES = { "Java", "C", "Python" };

    @Test
    public void auditAllTargetCategories() {
        List<String> missingItems = new ArrayList<>();
        int totalTechniques = 0;

        for (String catId : TARGET_CATEGORIES) {
            List<TechniqueItem> techniques = AppRepository.getTechniquesForCategory(catId);
            System.out.println("Category [" + catId + "] has " + techniques.size() + " techniques.");
            totalTechniques += techniques.size();

            for (TechniqueItem item : techniques) {
                TechniqueContent content = AppRepository.getContent(item.id);
                if (content == null) {
                    missingItems.add("Missing TechniqueContent for: " + item.id + " in " + catId);
                    continue;
                }

                // Check 3 Algorithm styles
                for (String style : STYLES) {
                    String algo = content.algorithmByStyle.get(style);
                    if (algo == null || algo.trim().isEmpty() || algo.contains("will be expanded here")) {
                        missingItems.add("Missing/stub algorithm: [" + item.id + "] style=" + style);
                    }
                }

                // Check 3x3 Codes (Java, C, Python x Iterative, Functions, Recursion)
                for (String style : STYLES) {
                    for (String lang : LANGUAGES) {
                        // Commented code
                        String commentedCode = AppRepository.getCodeSnippet(item.id, style, lang, true);
                        if (commentedCode == null || commentedCode.trim().isEmpty() || commentedCode.contains("will be expanded here") || commentedCode.contains("will be added here")) {
                            missingItems.add("Missing commented code: [" + item.id + "] lang=" + lang + " style=" + style);
                        }

                        // Clean code
                        String cleanCode = AppRepository.getCodeSnippet(item.id, style, lang, false);
                        if (cleanCode == null || cleanCode.trim().isEmpty() || cleanCode.contains("will be expanded here") || cleanCode.contains("will be added here")) {
                            missingItems.add("Missing clean code: [" + item.id + "] lang=" + lang + " style=" + style);
                        }
                    }
                }
            }
        }

        System.out.println("Total techniques audited across 7 categories: " + totalTechniques);
        if (!missingItems.isEmpty()) {
            System.out.println("TOTAL MISSING ITEMS: " + missingItems.size());
            for (int i = 0; i < Math.min(100, missingItems.size()); i++) {
                System.out.println("  - " + missingItems.get(i));
            }
        }
        assertTrue("Missing content detected across categories: " + missingItems.size(), missingItems.isEmpty());
    }
}
