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

public class BfsLearningPlugin implements AlgorithmLearningPlugin {

    @Override
    public String getAlgorithmId() {
        return "graph_traversal";
    }

    @Override
    public List<StepState> generateSteps(LearningInput input) {
        StepState stepState = new StepState.Builder()
            .setAlgorithmId(getAlgorithmId())
            .setAlgorithmTitle("Breadth-First Search")
            .setStructureType(StructureType.GRAPH)
            .setOperationType(OperationType.TRAVERSE)
            .setStepNumber(0)
            .addNode("0", "0", true, true)
            .addNode("1", "1", false, false)
            .addNode("2", "2", false, false)
            .addEdge("0", "1", true)
            .addEdge("0", "2", false)
            .putMetadata("mode", "BFS")
            .setSummary("Node 0 is at the front of the queue, and its neighbors are ready to be processed.")
            .setHint("BFS uses a queue and visits neighbors level by level.")
            .setCorrectAction(ActionType.ENQUEUE_NEIGHBOR)
            .setDecisionContext(new DecisionContext(
                DecisionType.TRAVERSAL_CHOICE,
                "What should BFS do next?",
                "BFS should enqueue the unvisited neighbors of the current front node before moving deeper.",
                Arrays.asList(ActionType.ENQUEUE_NEIGHBOR, ActionType.VISIT_RIGHT, ActionType.POP_ITEM)
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
