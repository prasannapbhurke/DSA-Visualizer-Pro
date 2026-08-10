package com.dsavisualizer.app.learning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LearningInput {

    public final List<Integer> values;
    public final int target;
    public final String traversalMode;
    public final String text;
    public final String pattern;

    public LearningInput(List<Integer> values, int target, String traversalMode, String text, String pattern) {
        this.values = values == null ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList<>(values));
        this.target = target;
        this.traversalMode = traversalMode == null ? "" : traversalMode;
        this.text = text == null ? "" : text;
        this.pattern = pattern == null ? "" : pattern;
    }
}
