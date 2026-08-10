package com.dsavisualizer.app.learning;

import java.util.Collections;
import java.util.List;

public class EngineController {

    private final QuestionGenerator questionGenerator;
    private final ExplanationEngine explanationEngine;
    private final DifficultyManager difficultyManager;

    private List<StepState> steps = Collections.emptyList();
    private int currentIndex = 0;
    private DifficultyLevel difficultyLevel = DifficultyLevel.GUIDED;

    public EngineController(
        QuestionGenerator questionGenerator,
        ExplanationEngine explanationEngine,
        DifficultyManager difficultyManager
    ) {
        this.questionGenerator = questionGenerator;
        this.explanationEngine = explanationEngine;
        this.difficultyManager = difficultyManager;
    }

    public void start(List<StepState> steps, DifficultyLevel difficultyLevel) {
        this.steps = steps == null ? Collections.emptyList() : steps;
        this.currentIndex = 0;
        this.difficultyLevel = difficultyLevel == null ? DifficultyLevel.GUIDED : difficultyLevel;
    }

    public boolean hasCurrentStep() {
        return currentIndex >= 0 && currentIndex < steps.size();
    }

    public StepState getCurrentStep() {
        if (!hasCurrentStep()) {
            return null;
        }
        return steps.get(currentIndex);
    }

    public LearningQuestion getCurrentQuestion() {
        StepState stepState = getCurrentStep();
        if (stepState == null) {
            return null;
        }
        DifficultyConfig config = difficultyManager.resolve(difficultyLevel);
        DifficultyLevel resolvedLevel = config.showHintBeforeAnswer
            ? difficultyLevel
            : difficultyLevel;
        return questionGenerator.generate(stepState, resolvedLevel);
    }

    public AnswerFeedback submit(ActionType selectedAction) {
        StepState stepState = getCurrentStep();
        if (stepState == null) {
            return new AnswerFeedback(false, "No Step", "There is no active learning step right now.");
        }
        return explanationEngine.explain(stepState, selectedAction);
    }

    public void moveToNextStep() {
        if (currentIndex < steps.size()) {
            currentIndex++;
        }
    }
}
