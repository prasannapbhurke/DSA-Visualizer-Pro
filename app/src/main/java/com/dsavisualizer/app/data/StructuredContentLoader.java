package com.dsavisualizer.app.data;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.dsavisualizer.app.model.CategoryItem;
import com.dsavisualizer.app.model.TechniqueContent;
import com.dsavisualizer.app.model.TechniqueItem;
import com.dsavisualizer.app.ui.visualization.VisualizationTechniqueRegistry;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Loads the first phase of structured content from assets.
 * The format is intentionally small and readable so we can scale it up
 * without rewriting the app again later.
 */
final class StructuredContentLoader {

    private static final String ROOT = "structured_content";
    private static final String TAG = "StructuredContent";

    private StructuredContentLoader() {
    }

    static StructuredContentBundle load(Context context) {
        StructuredContentBundle bundle = new StructuredContentBundle();
        if (context == null) {
            return bundle;
        }

        try {
            AssetManager assets = context.getAssets();
            JSONObject catalog = new JSONObject(readAsset(assets, ROOT + "/catalog.json"));
            bundle.catalogVersion = catalog.optInt("version", 1);

            JSONArray categories = catalog.optJSONArray("categories");
            if (categories != null) {
                for (int i = 0; i < categories.length(); i++) {
                    JSONObject item = categories.optJSONObject(i);
                    if (item == null) {
                        continue;
                    }
                    StructuredContentValidator.validateCategory(item, bundle, i);
                    bundle.categories.add(new CategoryItem(
                        item.optString("id"),
                        item.optString("title"),
                        item.optString("subtitle"),
                        item.optString("accentHex", "#60A5FA")
                    ));
                }
            }

            JSONArray techniques = catalog.optJSONArray("techniques");
            if (techniques != null) {
                for (int i = 0; i < techniques.length(); i++) {
                    JSONObject item = techniques.optJSONObject(i);
                    if (item == null) {
                        continue;
                    }
                    StructuredContentValidator.validateTechnique(item, bundle, i);
                    bundle.techniques.add(new TechniqueItem(
                        item.optString("id"),
                        item.optString("categoryId"),
                        item.optString("title"),
                        item.optString("subtitle"),
                        item.optString("difficulty", "Medium")
                    ));
                }
            }

            JSONArray contentFiles = catalog.optJSONArray("contentFiles");
            if (contentFiles != null) {
                for (int i = 0; i < contentFiles.length(); i++) {
                    String fileName = contentFiles.optString(i);
                    if (fileName == null || fileName.trim().isEmpty()) {
                        continue;
                    }
                    try {
                        String raw = readAsset(assets, ROOT + "/contents/" + fileName);
                        JSONObject root = new JSONObject(raw);
                        JSONObject defaults = root.optJSONObject("defaults");
                        JSONArray contents = root.optJSONArray("contents");
                        if (contents != null) {
                            for (int j = 0; j < contents.length(); j++) {
                                JSONObject item = contents.optJSONObject(j);
                                if (item == null) {
                                    continue;
                                }
                                StructuredContentValidator.validateContent(item, defaults, bundle, fileName, j);
                                TechniqueContent content = parseContent(item, defaults);
                                bundle.contentById.put(content.techniqueId, content);
                            }
                        } else {
                            StructuredContentValidator.validateContent(root, defaults, bundle, fileName, 0);
                            TechniqueContent content = parseContent(root, defaults);
                            bundle.contentById.put(content.techniqueId, content);
                        }
                    } catch (Exception fileError) {
                        Log.w(TAG, "Skipping invalid structured content file: " + fileName, fileError);
                    }
                }
            }
        } catch (Exception error) {
            throw new IllegalStateException("Failed to load structured content from assets.", error);
        }

        return bundle;
    }

    private static TechniqueContent parseContent(JSONObject object, JSONObject defaults) {
        TechniqueContent content = new TechniqueContent(
            object.optString("id"),
            object.optString("title"),
            object.optString("definition"),
            object.optString("realLifeExample"),
            object.optJSONObject("complexity") == null ? "Varies" : object.optJSONObject("complexity").optString("best", "Varies"),
            object.optJSONObject("complexity") == null ? "Varies" : object.optJSONObject("complexity").optString("average", "Varies"),
            object.optJSONObject("complexity") == null ? "Varies" : object.optJSONObject("complexity").optString("worst", "Varies"),
            object.optJSONObject("complexity") == null ? "Varies" : object.optJSONObject("complexity").optString("space", "Varies"),
            parseIntArray(object.optJSONArray("sampleInput")),
            object.optInt("sampleTarget", 0)
        );
        JSONObject objectMetadata = object.optJSONObject("metadata");
        JSONObject defaultMetadata = defaults == null ? null : defaults.optJSONObject("metadata");
        content.contentVersion = readMetadataInt(objectMetadata, defaultMetadata, "contentVersion", object.optInt("contentVersion", 1));
        content.structuredFormatVersion = readMetadataInt(objectMetadata, defaultMetadata, "formatVersion", object.optInt("formatVersion", 1));
        content.sourceType = readMetadataString(objectMetadata, defaultMetadata, "sourceType", object.optString("sourceType", "Internal"));
        content.sourceTitle = readMetadataString(objectMetadata, defaultMetadata, "sourceTitle", object.optString("sourceTitle"));
        content.sourceChapter = readMetadataString(objectMetadata, defaultMetadata, "sourceChapter", object.optString("sourceChapter"));
        content.sourceReference = readMetadataString(objectMetadata, defaultMetadata, "sourceReference", object.optString("sourceReference"));
        content.updatedAt = readMetadataString(objectMetadata, defaultMetadata, "updatedAt", object.optString("updatedAt"));
        content.overrideOf = readMetadataString(objectMetadata, defaultMetadata, "overrideOf", object.optString("overrideOf"));
        content.visualizationType = pickValue(
            object.optString("visualizationType"),
            defaults == null ? "" : defaults.optString("visualizationType")
        );
        if (content.visualizationType.isEmpty()) {
            content.visualizationType = inferVisualizationType(content.techniqueId);
        }

        JSONObject algorithm = object.optJSONObject("algorithm");
        if (algorithm != null) {
            content.algorithmByStyle.put("Iterative", algorithm.optString("Iterative"));
            content.algorithmByStyle.put("Functions", algorithm.optString("Functions"));
            content.algorithmByStyle.put("Recursion", algorithm.optString("Recursion"));
        }

        JSONArray explanationSteps = object.optJSONArray("explanationSteps");
        if (explanationSteps != null) {
            for (int i = 0; i < explanationSteps.length(); i++) {
                String step = explanationSteps.optString(i);
                if (step != null && !step.trim().isEmpty()) {
                    content.explanationSteps.add(step.trim());
                }
            }
        }
        if (content.explanationSteps.isEmpty()) {
            content.explanationSteps.addAll(ExplanationStepFactory.buildDefaultSteps(content));
        }

        JSONObject code = object.optJSONObject("code");
        if (code != null) {
            if (code.has("Iterative") || code.has("Functions") || code.has("Recursion")) {
                content.codeByStyle.put("Iterative", code.optString("Iterative"));
                content.codeByStyle.put("Functions", code.optString("Functions"));
                content.codeByStyle.put("Recursion", code.optString("Recursion"));
            } else {
                parseLanguageCodeBlock(content, code, false);
            }
        }

        JSONObject commentedCode = object.optJSONObject("commentedCode");
        if (commentedCode != null) {
            parseLanguageCodeBlock(content, commentedCode, true);
        }

        return content;
    }

    private static int readMetadataInt(JSONObject objectMetadata, JSONObject defaultMetadata, String key, int fallback) {
        if (objectMetadata != null && objectMetadata.has(key)) {
            return objectMetadata.optInt(key, fallback);
        }
        if (defaultMetadata != null && defaultMetadata.has(key)) {
            return defaultMetadata.optInt(key, fallback);
        }
        return fallback;
    }

    private static String readMetadataString(JSONObject objectMetadata, JSONObject defaultMetadata, String key, String fallback) {
        if (objectMetadata != null && objectMetadata.has(key)) {
            return objectMetadata.optString(key, fallback);
        }
        if (defaultMetadata != null && defaultMetadata.has(key)) {
            return defaultMetadata.optString(key, fallback);
        }
        return fallback;
    }

    private static String pickValue(String preferred, String fallback) {
        return preferred != null && !preferred.trim().isEmpty()
            ? preferred.trim()
            : (fallback == null ? "" : fallback.trim());
    }

    private static String inferVisualizationType(String techniqueId) {
        if (VisualizationTechniqueRegistry.isSearching(techniqueId)) {
            return "searching";
        }
        if (VisualizationTechniqueRegistry.isSorting(techniqueId)) {
            return "sorting";
        }
        if (VisualizationTechniqueRegistry.isStructure(techniqueId)) {
            return "structure";
        }
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) {
            return "tree";
        }
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) {
            return "graph";
        }
        return "";
    }

    private static void parseLanguageCodeBlock(TechniqueContent content, JSONObject codeObject, boolean commented) {
        JSONArray languageNames = codeObject.names();
        if (languageNames == null) {
            return;
        }
        for (int i = 0; i < languageNames.length(); i++) {
            String language = languageNames.optString(i);
            JSONObject languageBlock = codeObject.optJSONObject(language);
            if (languageBlock == null) {
                continue;
            }
            putIfPresent(content, language, "Iterative", languageBlock, commented);
            putIfPresent(content, language, "Functions", languageBlock, commented);
            putIfPresent(content, language, "Recursion", languageBlock, commented);
        }
    }

    private static void putIfPresent(
        TechniqueContent content,
        String language,
        String style,
        JSONObject languageBlock,
        boolean commented
    ) {
        if (!languageBlock.has(style)) {
            return;
        }
        content.putCode(language, style, languageBlock.optString(style), commented);
    }

    private static int[] parseIntArray(JSONArray array) {
        if (array == null) {
            return new int[0];
        }
        int[] values = new int[array.length()];
        for (int i = 0; i < array.length(); i++) {
            values[i] = array.optInt(i, 0);
        }
        return values;
    }

    private static String readAsset(AssetManager assets, String path) throws Exception {
        try (InputStream inputStream = assets.open(path);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            return outputStream.toString(StandardCharsets.UTF_8.name());
        }
    }
}
