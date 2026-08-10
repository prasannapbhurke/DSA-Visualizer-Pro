package com.dsavisualizer.app.learning;

import java.util.List;

public interface AlgorithmLearningPlugin {

    String getAlgorithmId();

    List<StepState> generateSteps(LearningInput input);

    StepState getStepState(LearningInput input, int stepIndex);

    ActionType getCorrectAction(StepState stepState);
}
