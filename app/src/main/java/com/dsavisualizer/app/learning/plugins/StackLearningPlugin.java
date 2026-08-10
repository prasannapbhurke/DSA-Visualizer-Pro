package com.dsavisualizer.app.learning.plugins;

import com.dsavisualizer.app.learning.ActionType;
import com.dsavisualizer.app.learning.AlgorithmLearningPlugin;
import com.dsavisualizer.app.learning.DecisionContext;
import com.dsavisualizer.app.learning.DecisionType;
import com.dsavisualizer.app.learning.LearningInput;
import com.dsavisualizer.app.learning.OperationType;
import com.dsavisualizer.app.learning.StepState;
import com.dsavisualizer.app.learning.StructureType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StackLearningPlugin implements AlgorithmLearningPlugin {

    @Override
    public String getAlgorithmId() {
        return "stack";
    }

    @Override
    public List<StepState> generateSteps(LearningInput input) {
        List<Integer> values = input.values.isEmpty() ? Arrays.asList(4, 8, 12) : input.values;
        int newValue = input.target == 0 ? 16 : input.target;
        return Collections.singletonList(new StepState.Builder()
            .setAlgorithmId(getAlgorithmId())
            .setAlgorithmTitle("Stack Push")
            .setStructureType(StructureType.STACK)
            .setOperationType(OperationType.PUSH)
            .setStepNumber(0)
            .setLinearData(values)
            .addPointer("top", values.size() - 1)
            .putMetadata("newValue", String.valueOf(newValue))
            .setSummary("A new value " + newValue + " is waiting above the stack.")
            .setHint("Stacks add and remove items only from the top.")
            .setCorrectAction(ActionType.PUSH_ITEM)
            .setDecisionContext(new DecisionContext(
                DecisionType.STRUCTURE_CHANGE,
                "What should happen next in the stack?",
                "Because stacks follow LIFO order, the new item must be pushed onto the top.",
                Arrays.asList(ActionType.PUSH_ITEM, ActionType.POP_ITEM, ActionType.MOVE_NEXT)
            ))
            .build());
    }

    @Override
    public StepState getStepState(LearningInput input, int stepIndex) {
        List<StepState> steps = generateSteps(input);
        if (stepIndex < 0 || stepIndex >= steps.size()) {
            return null;
        }
        return steps.get(stepIndex);
    }

    @Override
    public ActionType getCorrectAction(StepState stepState) {
        return stepState == null ? ActionType.COMPLETE : stepState.correctAction;
    }
}
