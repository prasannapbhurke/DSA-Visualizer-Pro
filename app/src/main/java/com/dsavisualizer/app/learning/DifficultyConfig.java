package com.dsavisualizer.app.learning;

public class DifficultyConfig {

    public final boolean showHintBeforeAnswer;
    public final boolean showHighlightedVariables;
    public final long suggestedDelayMs;

    public DifficultyConfig(boolean showHintBeforeAnswer, boolean showHighlightedVariables, long suggestedDelayMs) {
        this.showHintBeforeAnswer = showHintBeforeAnswer;
        this.showHighlightedVariables = showHighlightedVariables;
        this.suggestedDelayMs = suggestedDelayMs;
    }
}
