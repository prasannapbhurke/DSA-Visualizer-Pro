package com.dsavisualizer.app.data;

import com.dsavisualizer.app.model.TechniqueContent;

public final class ContentValidator {

    private ContentValidator() {
    }

    public static ValidationResult validateBundle(StructuredContentBundle bundle) {
        ValidationResult result = new ValidationResult();
        if (bundle == null) {
            result.addError("Structured content bundle is null.");
            return result;
        }
        result.addAll(bundle.errors);
        return result;
    }

    public static ValidationResult validateContent(TechniqueContent content) {
        ValidationResult result = new ValidationResult();
        if (content == null) {
            result.addError("Technique content is null.");
            return result;
        }
        if (content.title == null || content.title.trim().isEmpty()) {
            result.addError("Technique " + content.techniqueId + " is missing title.");
        }
        if (content.visualizationType == null || content.visualizationType.trim().isEmpty()) {
            result.addError("Technique " + content.techniqueId + " is missing visualization mapping.");
        }
        if (content.sourceType == null || content.sourceType.trim().isEmpty()) {
            result.addError("Technique " + content.techniqueId + " is missing source type.");
        }
        if (content.updatedAt == null || content.updatedAt.trim().isEmpty()) {
            result.addError("Technique " + content.techniqueId + " is missing last updated timestamp.");
        }
        if (content.explanationSteps.isEmpty()) {
            result.addError("Technique " + content.techniqueId + " is missing explanation steps.");
        }
        return result;
    }

    public static void throwIfInvalid(ValidationResult result) {
        if (result != null && !result.isValid()) {
            throw new IllegalStateException(result.toSingleMessage());
        }
    }
}
