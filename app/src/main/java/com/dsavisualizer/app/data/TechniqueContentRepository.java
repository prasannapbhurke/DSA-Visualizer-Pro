package com.dsavisualizer.app.data;

import com.dsavisualizer.app.model.TechniqueContent;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Small repository wrapper for technique-related reads.
 * This gives Phase 2 a single content-facing entry point without
 * changing the existing AppRepository storage yet.
 */
public class TechniqueContentRepository {

    private static final int MAX_CODE_CACHE_ENTRIES = 72;
    private static final Map<String, TechniqueContent> CONTENT_CACHE = new LinkedHashMap<>();
    private static final Map<String, String> CODE_CACHE = new LinkedHashMap<String, String>(MAX_CODE_CACHE_ENTRIES, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
            return size() > MAX_CODE_CACHE_ENTRIES;
        }
    };
    private static final Map<String, Boolean> COMMENTED_CODE_CACHE = new LinkedHashMap<>();

    public TechniqueContent getContent(String techniqueId) {
        String safeTechniqueId = techniqueId == null ? "linear_search" : techniqueId;
        synchronized (CONTENT_CACHE) {
            TechniqueContent cached = CONTENT_CACHE.get(safeTechniqueId);
            if (cached != null) {
                return cached;
            }
            TechniqueContent loaded = AppRepository.getContent(safeTechniqueId);
            CONTENT_CACHE.put(safeTechniqueId, loaded);
            return loaded;
        }
    }

    public String getCodeSnippet(String techniqueId, String style, String language, boolean preferCommented) {
        String key = buildCodeKey(techniqueId, style, language, preferCommented);
        synchronized (CODE_CACHE) {
            String cached = CODE_CACHE.get(key);
            if (cached != null) {
                return cached;
            }
            String loaded = AppRepository.getCodeSnippet(
                techniqueId == null ? "linear_search" : techniqueId,
                style == null ? "Iterative" : style,
                language == null ? "Java" : language,
                preferCommented
            );
            CODE_CACHE.put(key, loaded);
            return loaded;
        }
    }

    public boolean hasExplicitCommentedCode(String techniqueId, String style, String language) {
        String key = buildCodeKey(techniqueId, style, language, true);
        synchronized (COMMENTED_CODE_CACHE) {
            Boolean cached = COMMENTED_CODE_CACHE.get(key);
            if (cached != null) {
                return cached;
            }
            boolean loaded = AppRepository.hasExplicitCommentedCode(
                techniqueId == null ? "linear_search" : techniqueId,
                style == null ? "Iterative" : style,
                language == null ? "Java" : language
            );
            COMMENTED_CODE_CACHE.put(key, loaded);
            return loaded;
        }
    }

    private String buildCodeKey(String techniqueId, String style, String language, boolean preferCommented) {
        return (techniqueId == null ? "linear_search" : techniqueId)
            + "|"
            + (style == null ? "Iterative" : style)
            + "|"
            + (language == null ? "Java" : language)
            + "|"
            + preferCommented;
    }
}
