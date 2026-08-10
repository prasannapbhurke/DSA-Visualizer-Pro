package com.dsavisualizer.app.learning;

public final class ActionLabelResolver {

    private ActionLabelResolver() {
    }

    public static String toLabel(ActionType actionType) {
        if (actionType == null) {
            return "Continue";
        }
        switch (actionType) {
            case MOVE_LEFT:
                return "Move left";
            case MOVE_RIGHT:
                return "Move right";
            case MOVE_NEXT:
                return "Move to next item";
            case MOVE_PREVIOUS:
                return "Move to previous item";
            case FOUND:
                return "Element found";
            case SWAP:
                return "Swap the values";
            case NO_SWAP:
                return "Do not swap";
            case INSERT_NODE:
                return "Insert the new node";
            case PUSH_ITEM:
                return "Push the item";
            case POP_ITEM:
                return "Pop the top item";
            case VISIT_LEFT:
                return "Visit left subtree";
            case VISIT_RIGHT:
                return "Visit right subtree";
            case VISIT_NODE:
                return "Visit the current node";
            case ENQUEUE_NEIGHBOR:
                return "Enqueue the next neighbor";
            case DEQUEUE_NODE:
                return "Dequeue the front node";
            case TAKE_INCLUDE:
                return "Include this choice";
            case TAKE_EXCLUDE:
                return "Skip this choice";
            case RECURSE_DEEPER:
                return "Recurse deeper";
            case BACKTRACK_STEP:
                return "Backtrack";
            case COMPLETE:
            default:
                return "Complete the step";
        }
    }
}
