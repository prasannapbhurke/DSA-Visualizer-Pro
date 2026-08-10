package com.dsavisualizer.app.data;

import com.dsavisualizer.app.model.TechniqueContent;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Reusable validator for structured content JSON.
 * This keeps schema checks in one place as Phase 3 grows.
 */
final class StructuredContentValidator {

    private static final String[] REQUIRED_STYLES = {"Iterative", "Functions", "Recursion"};
    private static final String[] REQUIRED_LANGUAGES = {"Java", "C", "Python"};

    private StructuredContentValidator() {
    }

    static void validateCategory(JSONObject item, StructuredContentBundle bundle, int index) {
        if (item == null) {
            bundle.errors.add("Category at index " + index + " is null.");
            return;
        }
        if (item.optString("id").trim().isEmpty()) {
            bundle.errors.add("Category at index " + index + " is missing id.");
        }
        if (item.optString("title").trim().isEmpty()) {
            bundle.errors.add("Category " + item.optString("id", "#" + index) + " is missing title.");
        }
    }

    static void validateTechnique(JSONObject item, StructuredContentBundle bundle, int index) {
        if (item == null) {
            bundle.errors.add("Technique at index " + index + " is null.");
            return;
        }
        if (item.optString("id").trim().isEmpty()) {
            bundle.errors.add("Technique at index " + index + " is missing id.");
        }
        if (item.optString("categoryId").trim().isEmpty()) {
            bundle.errors.add("Technique " + item.optString("id", "#" + index) + " is missing categoryId.");
        }
        if (item.optString("title").trim().isEmpty()) {
            bundle.errors.add("Technique " + item.optString("id", "#" + index) + " is missing title.");
        }
    }

    static void validateContent(
        JSONObject object,
        JSONObject defaults,
        StructuredContentBundle bundle,
        String fileName,
        int index
    ) {
        if (object == null) {
            bundle.errors.add("Content entry in " + fileName + " at index " + index + " is null.");
            return;
        }

        String id = object.optString("id").trim();
        String label = id.isEmpty() ? "#" + index : id;
        if (id.isEmpty()) {
            bundle.errors.add("Content entry in " + fileName + " at index " + index + " is missing id.");
        }
        if (object.optString("title").trim().isEmpty()) {
            bundle.errors.add("Content entry " + label + " in " + fileName + " is missing title.");
        }
        if (object.optString("definition").trim().isEmpty()) {
            bundle.errors.add("Content entry " + label + " in " + fileName + " is missing definition.");
        }
        if (object.optJSONObject("complexity") == null) {
            bundle.errors.add("Content entry " + label + " in " + fileName + " is missing complexity.");
        }
        validateMetadata(object, defaults, bundle, fileName, label);
        validateVisualizationType(object, defaults, bundle, fileName, label);
        if (hasFullStructuredContent(object)) {
            validateAlgorithm(object, bundle, fileName, label);
            validateCodeBlocks(object, bundle, fileName, label, "code");
            validateCodeBlocks(object, bundle, fileName, label, "commentedCode");
        }
    }

    static void validateMergedContent(TechniqueContent content, StructuredContentBundle bundle, String fileName) {
        if (content == null) {
            return;
        }
        String label = content.techniqueId == null || content.techniqueId.trim().isEmpty() ? fileName : content.techniqueId;
        if (content.title == null || content.title.trim().isEmpty()) {
            bundle.errors.add("Merged content " + label + " is missing title.");
        }
        if (content.definition == null || content.definition.trim().isEmpty()) {
            bundle.errors.add("Merged content " + label + " is missing definition.");
        }
        validateMergedMetadata(content, bundle, label);
        validateMergedAlgorithm(content, bundle, label);
        validateMergedCode(content, bundle, label);
        if (content.visualizationType == null || content.visualizationType.trim().isEmpty()) {
            bundle.errors.add("Merged content " + label + " is missing visualizationType.");
        }
    }

    private static void validateMetadata(
        JSONObject object,
        JSONObject defaults,
        StructuredContentBundle bundle,
        String fileName,
        String label
    ) {
        JSONObject metadata = firstObject(object.optJSONObject("metadata"), defaults == null ? null : defaults.optJSONObject("metadata"));
        if (metadata == null) {
            bundle.errors.add("Content entry " + label + " in " + fileName + " is missing metadata.");
            return;
        }
        if (metadata.optInt("contentVersion", 0) <= 0) {
            bundle.errors.add("Content entry " + label + " in " + fileName + " has invalid metadata.contentVersion.");
        }
        if (metadata.optInt("formatVersion", 0) <= 0) {
            bundle.errors.add("Content entry " + label + " in " + fileName + " has invalid metadata.formatVersion.");
        }
        if (metadata.optString("sourceTitle").trim().isEmpty()) {
            bundle.errors.add("Content entry " + label + " in " + fileName + " is missing metadata.sourceTitle.");
        }
        if (metadata.optString("sourceChapter").trim().isEmpty()) {
            bundle.errors.add("Content entry " + label + " in " + fileName + " is missing metadata.sourceChapter.");
        }
        if (metadata.optString("sourceReference").trim().isEmpty()) {
            bundle.errors.add("Content entry " + label + " in " + fileName + " is missing metadata.sourceReference.");
        }
        if (metadata.optString("updatedAt").trim().isEmpty()) {
            bundle.errors.add("Content entry " + label + " in " + fileName + " is missing metadata.updatedAt.");
        }
    }

    private static void validateVisualizationType(
        JSONObject object,
        JSONObject defaults,
        StructuredContentBundle bundle,
        String fileName,
        String label
    ) {
        String visualizationType = pickValue(object.optString("visualizationType"), defaults == null ? "" : defaults.optString("visualizationType"));
        if (visualizationType.isEmpty() && inferVisualizationType(label).isEmpty()) {
            bundle.errors.add("Content entry " + label + " in " + fileName + " is missing visualizationType.");
        }
    }

    private static void validateAlgorithm(JSONObject object, StructuredContentBundle bundle, String fileName, String label) {
        JSONObject algorithm = object.optJSONObject("algorithm");
        if (algorithm == null) {
            bundle.errors.add("Content entry " + label + " in " + fileName + " is missing algorithm block.");
            return;
        }
        for (String style : REQUIRED_STYLES) {
            if (algorithm.optString(style).trim().isEmpty()) {
                bundle.errors.add("Content entry " + label + " in " + fileName + " is missing algorithm style " + style + ".");
            }
        }
    }

    private static void validateCodeBlocks(
        JSONObject object,
        StructuredContentBundle bundle,
        String fileName,
        String label,
        String blockName
    ) {
        JSONObject codeBlock = object.optJSONObject(blockName);
        if (codeBlock == null) {
            if ("code".equals(blockName)) {
                bundle.errors.add("Content entry " + label + " in " + fileName + " is missing code block.");
            }
            return;
        }

        if (hasFlatStyleShape(codeBlock)) {
            validateRequiredStyles(codeBlock, bundle, fileName, label, blockName, "default");
            return;
        }

        JSONArray languageNames = codeBlock.names();
        if (languageNames == null || languageNames.length() == 0) {
            bundle.errors.add("Content entry " + label + " in " + fileName + " has empty " + blockName + " block.");
            return;
        }

        for (int i = 0; i < languageNames.length(); i++) {
            String language = languageNames.optString(i);
            JSONObject languageBlock = codeBlock.optJSONObject(language);
            if (languageBlock == null) {
                bundle.errors.add("Content entry " + label + " in " + fileName + " has invalid " + blockName + " block for language " + language + ".");
                continue;
            }
            validateRequiredStyles(languageBlock, bundle, fileName, label, blockName, language);
        }
    }

    private static void validateMergedMetadata(TechniqueContent content, StructuredContentBundle bundle, String label) {
        if (content.contentVersion <= 0) {
            bundle.errors.add("Merged content " + label + " has invalid contentVersion.");
        }
        if (content.structuredFormatVersion <= 0) {
            bundle.errors.add("Merged content " + label + " has invalid structuredFormatVersion.");
        }
        if (content.sourceTitle == null || content.sourceTitle.trim().isEmpty()) {
            bundle.errors.add("Merged content " + label + " is missing sourceTitle.");
        }
        if (content.sourceChapter == null || content.sourceChapter.trim().isEmpty()) {
            bundle.errors.add("Merged content " + label + " is missing sourceChapter.");
        }
        if (content.sourceReference == null || content.sourceReference.trim().isEmpty()) {
            bundle.errors.add("Merged content " + label + " is missing sourceReference.");
        }
        if (content.updatedAt == null || content.updatedAt.trim().isEmpty()) {
            bundle.errors.add("Merged content " + label + " is missing updatedAt.");
        }
    }

    private static void validateMergedAlgorithm(TechniqueContent content, StructuredContentBundle bundle, String label) {
        for (String style : REQUIRED_STYLES) {
            String value = content.algorithmByStyle.get(style);
            if (value == null || value.trim().isEmpty()) {
                bundle.errors.add("Merged content " + label + " is missing algorithm style " + style + ".");
            }
        }
    }

    private static void validateMergedCode(TechniqueContent content, StructuredContentBundle bundle, String label) {
        for (String language : REQUIRED_LANGUAGES) {
            for (String style : REQUIRED_STYLES) {
                String value = AppRepository.getCodeSnippet(content.techniqueId, style, language, false);
                if (value == null || value.trim().isEmpty()) {
                    bundle.errors.add("Merged content " + label + " is missing code for " + language + " / " + style + ".");
                }
            }
        }
    }

    private static JSONObject firstObject(JSONObject primary, JSONObject fallback) {
        return primary != null ? primary : fallback;
    }

    private static String pickValue(String preferred, String fallback) {
        return preferred != null && !preferred.trim().isEmpty()
            ? preferred.trim()
            : (fallback == null ? "" : fallback.trim());
    }

    private static String inferVisualizationType(String techniqueId) {
        if (techniqueId == null) {
            return "";
        }
        if (com.dsavisualizer.app.ui.visualization.VisualizationTechniqueRegistry.isSearching(techniqueId)) {
            return "searching";
        }
        if (com.dsavisualizer.app.ui.visualization.VisualizationTechniqueRegistry.isSorting(techniqueId)) {
            return "sorting";
        }
        if (com.dsavisualizer.app.ui.visualization.VisualizationTechniqueRegistry.isStructure(techniqueId)) {
            return "structure";
        }
        if (com.dsavisualizer.app.ui.visualization.VisualizationTechniqueRegistry.isTree(techniqueId)) {
            return "tree";
        }
        if (com.dsavisualizer.app.ui.visualization.VisualizationTechniqueRegistry.isGraph(techniqueId)) {
            return "graph";
        }
        return "";
    }

    private static boolean hasFlatStyleShape(JSONObject object) {
        for (String style : REQUIRED_STYLES) {
            if (object.has(style)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasFullStructuredContent(JSONObject object) {
        return object != null
            && (object.has("algorithm")
            || object.has("code")
            || object.has("commentedCode"));
    }

    private static void validateRequiredStyles(
        JSONObject block,
        StructuredContentBundle bundle,
        String fileName,
        String label,
        String blockName,
        String scope
    ) {
        for (String style : REQUIRED_STYLES) {
            if (block.optString(style).trim().isEmpty()) {
                bundle.errors.add(
                    "Content entry " + label
                        + " in " + fileName
                        + " is missing " + blockName
                        + " style " + style
                        + " for " + scope + "."
                );
            }
        }
    }
}
