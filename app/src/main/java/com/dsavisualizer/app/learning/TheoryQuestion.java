package com.dsavisualizer.app.learning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TheoryQuestion {

    public final String prompt;
    public final List<String> options;
    public final int correctIndex;
    public final String explanation;

    public TheoryQuestion(String prompt, List<String> options, int correctIndex, String explanation) {
        this.prompt = prompt == null ? "" : prompt;
        this.options = options == null
            ? Collections.emptyList()
            : Collections.unmodifiableList(new ArrayList<>(options));
        this.correctIndex = Math.max(0, Math.min(this.options.isEmpty() ? 0 : this.options.size() - 1, correctIndex));
        this.explanation = explanation == null ? "" : explanation;
    }
}
