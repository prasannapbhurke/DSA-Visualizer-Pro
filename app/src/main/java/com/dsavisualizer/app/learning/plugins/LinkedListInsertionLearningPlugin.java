package com.dsavisualizer.app.learning.plugins;

import com.dsavisualizer.app.learning.ActionType;
import com.dsavisualizer.app.learning.AlgorithmLearningPlugin;
import com.dsavisualizer.app.learning.DecisionContext;
import com.dsavisualizer.app.learning.DecisionType;
import com.dsavisualizer.app.learning.LearningInput;
import com.dsavisualizer.app.learning.OperationType;
import com.dsavisualizer.app.learning.StepState;
import com.dsavisualizer.app.learning.StructureType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LinkedListInsertionLearningPlugin implements AlgorithmLearningPlugin {

    @Override
    public String getAlgorithmId() {
        return "linked_list_insertion";
    }

    @Override
    public List<StepState> generateSteps(LearningInput input) {
        List<Integer> values = input.values.isEmpty() ? Arrays.asList(10, 20, 30) : input.values;
        List<StepState> steps = new ArrayList<>();
        int newValue = input.target == 0 ? 15 : input.target;

        steps.add(new StepState.Builder()
            .setAlgorithmId(getAlgorithmId())
            .setAlgorithmTitle("Linked List Insertion")
            .setStructureType(StructureType.LINKED_LIST)
            .setOperationType(OperationType.INSERT)
            .setStepNumber(0)
            .setLinearData(values)
            .addPointer("head", 0)
            .putMetadata("newValue", String.valueOf(newValue))
            .setSummary("A new node with value " + newValue + " is ready to join the list.")
            .setHint("Insertion first connects the new node correctly before the list continues.")
            .setCorrectAction(ActionType.INSERT_NODE)
            .setDecisionContext(new DecisionContext(
                DecisionType.STRUCTURE_CHANGE,
                "What structural change should happen next?",
                "The algorithm should connect the new node into the list so the chain remains valid.",
                Arrays.asList(ActionType.INSERT_NODE, ActionType.DELETE, ActionType.MOVE_NEXT)
            ))
            .build());

        return steps;
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
