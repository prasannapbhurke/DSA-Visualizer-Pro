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
import java.util.Collections;
import java.util.List;

public class BinarySearchLearningPlugin implements AlgorithmLearningPlugin {

    @Override
    public String getAlgorithmId() {
        return "binary_search";
    }

    @Override
    public List<StepState> generateSteps(LearningInput input) {
        List<Integer> values = input.values.isEmpty()
            ? Arrays.asList(3, 7, 11, 18, 25, 32, 40)
            : input.values;
        List<StepState> steps = new ArrayList<>();
        int low = 0;
        int high = values.size() - 1;
        int step = 0;

        while (low <= high) {
            int mid = low + ((high - low) / 2);
            int midValue = values.get(mid);
            ActionType correctAction;
            String relation;
            String reason;
            if (input.target == midValue) {
                correctAction = ActionType.FOUND;
                relation = "equal";
                reason = "Because the target matches the middle value, the search stops at this step.";
            } else if (input.target > midValue) {
                correctAction = ActionType.MOVE_RIGHT;
                relation = "greater";
                reason = "Because the target is greater than the middle value, the left half cannot contain the answer.";
            } else {
                correctAction = ActionType.MOVE_LEFT;
                relation = "less";
                reason = "Because the target is smaller than the middle value, the right half can be discarded.";
            }

            steps.add(new StepState.Builder()
                .setAlgorithmId(getAlgorithmId())
                .setAlgorithmTitle("Binary Search")
                .setStructureType(StructureType.SEARCH)
                .setOperationType(OperationType.COMPARE)
                .setStepNumber(step)
                .setLinearData(values)
                .addPointer("low", low)
                .addPointer("mid", mid)
                .addPointer("high", high)
                .putMetadata("target", String.valueOf(input.target))
                .putMetadata("midValue", String.valueOf(midValue))
                .putMetadata("relation", relation)
                .setSummary("Compare target " + input.target + " with middle value " + midValue + ".")
                .setHint("Compare the target with the middle value before deciding which half survives.")
                .setCorrectAction(correctAction)
                .setDecisionContext(new DecisionContext(
                    DecisionType.POINTER_MOVEMENT,
                    "What will happen next in Binary Search?",
                    reason,
                    Arrays.asList(ActionType.MOVE_LEFT, ActionType.MOVE_RIGHT, ActionType.FOUND)
                ))
                .build());

            step++;
            if (correctAction == ActionType.FOUND) {
                break;
            }
            if (correctAction == ActionType.MOVE_RIGHT) {
                low = mid + 1;
            } else {
                high = mid - 1;
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
}
