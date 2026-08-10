package com.dsavisualizer.app.data;

import com.dsavisualizer.app.model.TechniqueContent;

import java.util.ArrayList;
import java.util.List;

final class ExplanationStepFactory {

    private ExplanationStepFactory() {
    }

    static List<String> buildDefaultSteps(TechniqueContent content) {
        List<String> steps = new ArrayList<>();
        if (content == null) {
            return steps;
        }

        if (content.definition != null && !content.definition.trim().isEmpty()) {
            steps.add(content.definition.trim());
        }
        if (content.realLifeExample != null && !content.realLifeExample.trim().isEmpty()) {
            steps.add(content.realLifeExample.trim());
        }
        if (content.algorithmByStyle.containsKey("Iterative")) {
            String iterative = content.algorithmByStyle.get("Iterative");
            if (iterative != null && !iterative.trim().isEmpty()) {
                steps.add("Core flow: " + firstLine(iterative));
            }
        }
        return steps;
    }

    private static String firstLine(String value) {
        String normalized = value == null ? "" : value.trim();
        int newlineIndex = normalized.indexOf('\n');
        if (newlineIndex >= 0) {
            return normalized.substring(0, newlineIndex).trim();
        }
        return normalized;
    }
}
