package com.dsavisualizer.app.learning;

public class ExplanationEngine {

    public AnswerFeedback explain(StepState stepState, ActionType selectedAction) {
        boolean correct = selectedAction == stepState.correctAction;
        if (correct) {
            return new AnswerFeedback(
                true,
                "Correct",
                buildCorrectExplanation(stepState)
            );
        }
        return new AnswerFeedback(
            false,
            "Wrong Answer",
            buildMistakeExplanation(stepState, selectedAction)
        );
    }

    private String buildCorrectExplanation(StepState stepState) {
        String base = stepState.decisionContext.expectedReason;
        if (!base.isEmpty()) {
            return base;
        }
        return "That is correct because the current state logically leads to " + ActionLabelResolver.toLabel(stepState.correctAction).toLowerCase() + ".";
    }

    private String buildMistakeExplanation(StepState stepState, ActionType selectedAction) {
        StringBuilder builder = new StringBuilder();
        builder.append("The correct step is ");
        builder.append(ActionLabelResolver.toLabel(stepState.correctAction).toLowerCase());
        builder.append(". ");

        if (stepState.structureType == StructureType.SEARCH) {
            builder.append(buildSearchReason(stepState, selectedAction));
        } else if (stepState.structureType == StructureType.SORT) {
            builder.append(buildSortReason(stepState, selectedAction));
        } else if (stepState.structureType == StructureType.TREE) {
            builder.append(buildTreeReason(stepState, selectedAction));
        } else if (stepState.structureType == StructureType.GRAPH) {
            builder.append(buildGraphReason(stepState, selectedAction));
        } else if (stepState.structureType == StructureType.DP) {
            builder.append(buildDpReason(stepState, selectedAction));
        } else {
            builder.append(buildGenericReason(stepState, selectedAction));
        }
        return builder.toString();
    }

    private String buildSearchReason(StepState stepState, ActionType selectedAction) {
        String relation = stepState.getMetadata("relation");
        if ("greater".equals(relation)) {
            return "Because the target is greater than the current middle value, the algorithm discards the left half and moves right. "
                + "Choosing " + ActionLabelResolver.toLabel(selectedAction).toLowerCase() + " keeps the wrong search space.";
        }
        if ("less".equals(relation)) {
            return "Because the target is smaller than the current middle value, the algorithm discards the right half and moves left. "
                + "Choosing " + ActionLabelResolver.toLabel(selectedAction).toLowerCase() + " would ignore that comparison.";
        }
        if ("equal".equals(relation)) {
            return "Because the target matches the active value exactly, the search should stop. "
                + "Moving again would skip a successful match.";
        }
        return buildGenericReason(stepState, selectedAction);
    }

    private String buildSortReason(StepState stepState, ActionType selectedAction) {
        String relation = stepState.getMetadata("relation");
        if ("out_of_order".equals(relation)) {
            return "The active pair is out of order, so swapping moves the larger value toward its correct side. "
                + "Choosing " + ActionLabelResolver.toLabel(selectedAction).toLowerCase() + " would leave the array less sorted.";
        }
        if ("in_order".equals(relation)) {
            return "The current pair is already in the correct order, so swapping would create unnecessary movement.";
        }
        return buildGenericReason(stepState, selectedAction);
    }

    private String buildTreeReason(StepState stepState, ActionType selectedAction) {
        String traversal = stepState.getMetadata("traversal");
        if ("inorder".equalsIgnoreCase(traversal)) {
            return "In inorder traversal, we visit left subtree first, then the node, then the right subtree. "
                + "That is why the current move follows that order, not " + ActionLabelResolver.toLabel(selectedAction).toLowerCase() + ".";
        }
        return buildGenericReason(stepState, selectedAction);
    }

    private String buildGraphReason(StepState stepState, ActionType selectedAction) {
        String mode = stepState.getMetadata("mode");
        if ("BFS".equalsIgnoreCase(mode)) {
            return "Breadth-first search uses a queue, so it processes the current front node and then adds its unvisited neighbors layer by layer. "
                + "The wrong choice breaks that level-order behavior.";
        }
        return buildGenericReason(stepState, selectedAction);
    }

    private String buildDpReason(StepState stepState, ActionType selectedAction) {
        return "Dynamic programming chooses the option that preserves the best smaller sub-solution. "
            + "The wrong choice does not follow the optimal substructure for this state.";
    }

    private String buildGenericReason(StepState stepState, ActionType selectedAction) {
        String expectedReason = stepState.decisionContext.expectedReason;
        if (!expectedReason.isEmpty()) {
            return expectedReason + " "
                + "The selected option, " + ActionLabelResolver.toLabel(selectedAction).toLowerCase()
                + ", does not match the active state.";
        }
        return "The current pointers, active values, and operation type point to "
            + ActionLabelResolver.toLabel(stepState.correctAction).toLowerCase()
            + ", not " + ActionLabelResolver.toLabel(selectedAction).toLowerCase() + ".";
    }
}
