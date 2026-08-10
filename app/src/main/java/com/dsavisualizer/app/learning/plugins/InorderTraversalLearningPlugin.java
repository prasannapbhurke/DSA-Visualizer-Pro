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

public class InorderTraversalLearningPlugin implements AlgorithmLearningPlugin {

    @Override
    public String getAlgorithmId() {
        return "tree_traversal";
    }

    @Override
    public List<StepState> generateSteps(LearningInput input) {
        StepState stepState = new StepState.Builder()
            .setAlgorithmId(getAlgorithmId())
            .setAlgorithmTitle("Inorder Traversal")
            .setStructureType(StructureType.TREE)
            .setOperationType(OperationType.TRAVERSE)
            .setStepNumber(0)
            .addNode("20", "20", true, false)
            .addNode("10", "10", false, false)
            .addNode("30", "30", false, false)
            .putMetadata("traversal", "inorder")
            .setSummary("The current node is 20, and inorder traversal must decide the next visit.")
            .setHint("In inorder traversal, the left subtree comes before the node itself.")
            .setCorrectAction(ActionType.VISIT_LEFT)
            .setDecisionContext(new DecisionContext(
                DecisionType.TRAVERSAL_CHOICE,
                "Which move comes next in inorder traversal?",
                "Inorder traversal visits left subtree first, then the node, then the right subtree.",
                Arrays.asList(ActionType.VISIT_LEFT, ActionType.VISIT_NODE, ActionType.VISIT_RIGHT)
            ))
            .build();
        return Collections.singletonList(stepState);
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
