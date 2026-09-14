package com.dsavisualizer.app.data;

import com.dsavisualizer.app.data.snippets.CSnippetProvider;
import com.dsavisualizer.app.data.snippets.JavaSnippetProvider;
import com.dsavisualizer.app.data.snippets.PythonSnippetProvider;
import com.dsavisualizer.app.data.snippets.SnippetFormatter;
import com.dsavisualizer.app.data.snippets.SupplementalSnippetProvider;

/**
 * Returns code snippets for the Code tab in multiple languages.
 * Acts as a centralized facade delegating to modular language providers.
 */
public final class CodeSnippetProvider {

    private CodeSnippetProvider() {
    }

    public static String getCodeSnippet(String techniqueId, String style, String language) {
        String safeStyle = SnippetFormatter.normalizeStyle(style);
        String safeLanguage = language == null ? "Java" : language.trim();

        String supplemental = SupplementalSnippetProvider.getSupplementalSnippet(techniqueId, safeStyle, safeLanguage);
        if (supplemental != null) {
            return SnippetFormatter.formatCodeSnippet(safeLanguage, supplemental);
        }

        if ("C".equalsIgnoreCase(safeLanguage)) {
            return SnippetFormatter.formatCodeSnippet(safeLanguage, CSnippetProvider.getCCodeSnippet(techniqueId, safeStyle));
        }
        if ("Python".equalsIgnoreCase(safeLanguage)) {
            return SnippetFormatter.formatCodeSnippet(safeLanguage, PythonSnippetProvider.getPythonCodeSnippet(techniqueId, safeStyle));
        }
        return SnippetFormatter.formatCodeSnippet(safeLanguage, JavaSnippetProvider.getJavaCodeSnippet(techniqueId, safeStyle));
    }
}
