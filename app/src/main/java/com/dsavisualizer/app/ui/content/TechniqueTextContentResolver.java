package com.dsavisualizer.app.ui.content;

import com.dsavisualizer.app.data.TechniqueContentRepository;
import com.dsavisualizer.app.model.TechniqueContent;

/**
 * Resolves display-ready text for algorithm/code tabs.
 * This separates content selection from fragment view code.
 */
public class TechniqueTextContentResolver {

    public static final String CONTENT_TYPE_ALGORITHM = "algorithm";
    public static final String CONTENT_TYPE_CODE = "code";

    private final TechniqueContentRepository repository;
    private final TextContentFormatter formatter;

    public TechniqueTextContentResolver() {
        this(new TechniqueContentRepository(), new TextContentFormatter());
    }

    public TechniqueTextContentResolver(
        TechniqueContentRepository repository,
        TextContentFormatter formatter
    ) {
        this.repository = repository;
        this.formatter = formatter;
    }

    public String resolve(
        String techniqueId,
        String contentType,
        String style,
        String language,
        boolean showComments
    ) {
        TechniqueContent content = repository.getContent(techniqueId);
        if (CONTENT_TYPE_CODE.equals(contentType)) {
            String raw = repository.getCodeSnippet(content.techniqueId, style, language, showComments);
            if (showComments && !repository.hasExplicitCommentedCode(content.techniqueId, style, language)) {
                return formatter.formatCode(content.techniqueId, raw, language, true);
            }
            if (!showComments) {
                return formatter.formatCode(content.techniqueId, raw, language, false);
            }
            return raw;
        }

        String key = "Functions".equals(style) ? "Functions" : style;
        String value = content.algorithmByStyle.get(key);
        if (value == null && "Functions".equals(style)) {
            value = content.algorithmByStyle.get("Function");
        }
        return formatter.formatAlgorithm(value == null ? "Algorithm explanation will be added soon." : value);
    }
}
