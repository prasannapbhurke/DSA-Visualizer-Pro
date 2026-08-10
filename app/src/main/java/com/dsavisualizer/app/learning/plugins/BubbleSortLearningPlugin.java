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

public class BubbleSortLearningPlugin implements AlgorithmLearningPlugin {

    @Override
    public String getAlgorithmId() {
        return "bubble_sort";
    }

    @Override
    public List<StepState> generateSteps(LearningInput input) {
        int[] values = toArray(input.values.isEmpty() ? Arrays.asList(9, 4, 7, 2, 6) : input.values);
        List<StepState> steps = new ArrayList<>();
        int step = 0;

        for (int pass = 0; pass < values.length - 1; pass++) {
            for (int j = 0; j < values.length - pass - 1; j++) {
                boolean shouldSwap = values[j] > values[j + 1];
                steps.add(new StepState.Builder()
                    .setAlgorithmId(getAlgorithmId())
                    .setAlgorithmTitle("Bubble Sort")
                    .setStructureType(StructureType.SORT)
                    .setOperationType(OperationType.COMPARE)
                    .setStepNumber(step++)
                    .setLinearData(toList(values))
                    .addPointer("j", j)
                    .addPointer("j+1", j + 1)
                    .putMetadata("pass", String.valueOf(pass + 1))
                    .putMetadata("relation", shouldSwap ? "out_of_order" : "in_order")
                    .setSummary("Compare " + values[j] + " and " + values[j + 1] + ".")
                    .setHint("If the left value is larger than the right value, Bubble Sort swaps them.")
                    .setCorrectAction(shouldSwap ? ActionType.SWAP : ActionType.NO_SWAP)
                    .setDecisionContext(new DecisionContext(
                        DecisionType.COMPARISON_RESULT,
                        "What should happen to this pair?",
                        shouldSwap
                            ? "The left value is larger, so swapping moves the larger value toward the end."
                            : "The pair is already in order, so no swap is needed.",
                        Arrays.asList(ActionType.SWAP, ActionType.NO_SWAP, ActionType.MOVE_RIGHT)
                    ))
                    .build());
                if (shouldSwap) {
                    int temp = values[j];
                    values[j] = values[j + 1];
                    values[j + 1] = temp;
                }
            }
        }
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

    private int[] toArray(List<Integer> values) {
        int[] result = new int[values.size()];
        for (int i = 0; i < values.size(); i++) {
            result[i] = values.get(i);
        }
        return result;
    }

    private List<Integer> toList(int[] values) {
        List<Integer> list = new ArrayList<>();
        for (int value : values) {
            list.add(value);
        }
        return list;
    }
}
