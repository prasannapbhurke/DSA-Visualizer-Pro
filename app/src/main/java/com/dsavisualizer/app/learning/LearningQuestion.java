package com.dsavisualizer.app.learning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LearningQuestion {

    public final String prompt;
    public final List<QuestionOption> options;
    public final String hint;
    public final DifficultyLevel difficultyLevel;

    public LearningQuestion(String prompt, List<QuestionOption> options, String hint, DifficultyLevel difficultyLevel) {
        this.prompt = prompt == null ? "" : prompt;
        this.options = options == null
            ? Collections.emptyList()
            : Collections.unmodifiableList(new ArrayList<>(options));
        this.hint = hint == null ? "" : hint;
        this.difficultyLevel = difficultyLevel == null ? DifficultyLevel.GUIDED : difficultyLevel;
    }

    public QuestionOption getCorrectOption() {
        for (QuestionOption option : options) {
            if (option.correct) {
                return option;
            }
        }
        return null;
    }
}
