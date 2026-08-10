package com.dsavisualizer.app.learning;

import com.dsavisualizer.app.model.TechniqueContent;
import com.dsavisualizer.app.ui.visualization.VisualizationTechniqueRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TechniquePracticeScenarioFactory {

    private final Random random = new Random();

    public StepState generate(String techniqueId, TechniqueContent content) {
        if ("binary_search".equals(techniqueId)) {
            return buildBinarySearchScenario(content);
        }
        if (VisualizationTechniqueRegistry.isSearching(techniqueId)) {
            return buildSearchScenario(techniqueId, content);
        }
        if (VisualizationTechniqueRegistry.isSorting(techniqueId)) {
            return buildSortScenario(techniqueId, content);
        }
        if (isStackTechnique(techniqueId)) {
            return buildStackScenario(techniqueId, content);
        }
        if (isQueueTechnique(techniqueId)) {
            return buildQueueScenario(techniqueId, content);
        }
        if (VisualizationTechniqueRegistry.isStructure(techniqueId)) {
            return buildLinkedScenario(techniqueId, content);
        }
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) {
            return buildTreeScenario(techniqueId, content);
        }
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) {
            return buildGraphScenario(techniqueId, content);
        }
        return null;
    }

    private StepState buildBinarySearchScenario(TechniqueContent content) {
        int[] values = ensureSorted(resolveValues(content, new int[]{3, 7, 11, 18, 24, 31, 42}));
        int low = random.nextInt(Math.max(1, values.length - 2));
        int high = Math.min(values.length - 1, low + 2 + random.nextInt(Math.max(1, values.length - low - 1)));
        int mid = (low + high) / 2;
        int mode = random.nextInt(3);
        int target = values[mid];
        ActionType correctAction = ActionType.FOUND;
        String relation = "equal";
        if (mode == 0 && mid > low) {
            target = values[mid] - 1;
            correctAction = ActionType.MOVE_LEFT;
            relation = "less";
        } else if (mode == 1 && mid < high) {
            target = values[mid] + 1;
            correctAction = ActionType.MOVE_RIGHT;
            relation = "greater";
        }

        return new StepState.Builder()
            .setAlgorithmId("binary_search")
            .setAlgorithmTitle(resolveTitle(content, "Binary Search"))
            .setStructureType(StructureType.SEARCH)
            .setOperationType(OperationType.COMPARE)
            .setStepNumber(random.nextInt(4) + 1)
            .setLinearData(toList(values))
            .addPointer("low", low)
            .addPointer("mid", mid)
            .addPointer("high", high)
            .setDecisionContext(new DecisionContext(
                DecisionType.POINTER_MOVEMENT,
                "Array = " + joinValues(values) + ". low=" + low + ", mid=" + mid + ", high=" + high
                    + ", target=" + target + ". What should binary search do next?",
                correctAction == ActionType.FOUND
                    ? "The middle value already matches the target."
                    : correctAction == ActionType.MOVE_LEFT
                        ? "The target is smaller than the middle value, so the search window moves left."
                        : "The target is greater than the middle value, so the search window moves right.",
                Arrays.asList(ActionType.MOVE_LEFT, ActionType.MOVE_RIGHT, ActionType.FOUND, ActionType.COMPLETE)
            ))
            .putMetadata("target", String.valueOf(target))
            .putMetadata("relation", relation)
            .setSummary("Current window: [" + low + ", " + high + "] with mid at " + mid + ".")
            .setHint("Compare target with the value at mid before moving the window.")
            .setCorrectAction(correctAction)
            .build();
    }

    private StepState buildSearchScenario(String techniqueId, TechniqueContent content) {
        int[] values = resolveValues(content, new int[]{5, 9, 12, 18, 21, 27});
        int index = random.nextInt(values.length);
        boolean found = random.nextBoolean();
        int target = found ? values[index] : values[index] + 1;
        return new StepState.Builder()
            .setAlgorithmId(techniqueId)
            .setAlgorithmTitle(resolveTitle(content, "Search Practice"))
            .setStructureType(StructureType.SEARCH)
            .setOperationType(OperationType.COMPARE)
            .setStepNumber(random.nextInt(5) + 1)
            .setLinearData(toList(values))
            .addPointer("index", index)
            .setDecisionContext(new DecisionContext(
                DecisionType.NEXT_STEP,
                "You are checking value " + values[index] + " at index " + index + " for target " + target + ". What happens next?",
                found ? "The current value matches the target." : "This value does not match, so the scan continues.",
                Arrays.asList(ActionType.FOUND, ActionType.MOVE_NEXT, ActionType.MOVE_LEFT, ActionType.MOVE_RIGHT)
            ))
            .putMetadata("target", String.valueOf(target))
            .putMetadata("relation", found ? "equal" : "different")
            .setSummary("Current comparison: " + values[index] + " vs " + target + ".")
            .setHint("Search questions are about the next comparison result, not the drawing.")
            .setCorrectAction(found ? ActionType.FOUND : ActionType.MOVE_NEXT)
            .build();
    }

    private StepState buildSortScenario(String techniqueId, TechniqueContent content) {
        int[] values = resolveValues(content, new int[]{9, 4, 7, 2, 8, 5});
        int left = random.nextInt(Math.max(1, values.length - 1));
        int right = Math.min(values.length - 1, left + 1);
        boolean swap = values[left] > values[right];
        return new StepState.Builder()
            .setAlgorithmId(techniqueId)
            .setAlgorithmTitle(resolveTitle(content, "Sorting Practice"))
            .setStructureType(StructureType.SORT)
            .setOperationType(OperationType.COMPARE)
            .setStepNumber(random.nextInt(5) + 1)
            .setLinearData(toList(values))
            .addPointer("left", left)
            .addPointer("right", right)
            .setDecisionContext(new DecisionContext(
                DecisionType.COMPARISON_RESULT,
                "Compare " + values[left] + " and " + values[right] + " in " + joinValues(values) + ". What should happen next?",
                swap ? "They are out of order for ascending sort." : "They are already in ascending order.",
                Arrays.asList(ActionType.SWAP, ActionType.NO_SWAP, ActionType.MOVE_NEXT, ActionType.MOVE_RIGHT)
            ))
            .putMetadata("relation", swap ? "out_of_order" : "in_order")
            .setSummary("Active pair: index " + left + " and " + right + ".")
            .setHint("Judge the pair order directly from the values.")
            .setCorrectAction(swap ? ActionType.SWAP : ActionType.NO_SWAP)
            .build();
    }

    private StepState buildStackScenario(String techniqueId, TechniqueContent content) {
        int[] values = resolveValues(content, new int[]{4, 7, 9});
        boolean pop = techniqueId.contains("pop") || (!techniqueId.contains("push") && random.nextBoolean());
        return new StepState.Builder()
            .setAlgorithmId(techniqueId)
            .setAlgorithmTitle(resolveTitle(content, "Stack Practice"))
            .setStructureType(StructureType.STACK)
            .setOperationType(pop ? OperationType.POP : OperationType.PUSH)
            .setStepNumber(random.nextInt(4) + 1)
            .setLinearData(toList(values))
            .addPointer("top", values.length - 1)
            .setDecisionContext(new DecisionContext(
                DecisionType.STRUCTURE_CHANGE,
                pop
                    ? "Stack top is " + values[values.length - 1] + ". The task is to remove the current top item. Which action matches that?"
                    : "Stack currently holds " + joinValues(values) + ". The next instruction is to add a new item on top. Which action matches that?",
                pop ? "Removing the top item is a pop." : "Adding a new top item is a push.",
                Arrays.asList(ActionType.PUSH_ITEM, ActionType.POP_ITEM, ActionType.MOVE_NEXT)
            ))
            .setSummary("Top index = " + (values.length - 1))
            .setHint("Think in terms of stack operation semantics.")
            .setCorrectAction(pop ? ActionType.POP_ITEM : ActionType.PUSH_ITEM)
            .build();
    }

    private StepState buildQueueScenario(String techniqueId, TechniqueContent content) {
        int[] values = resolveValues(content, new int[]{11, 14, 19, 23});
        boolean dequeue = techniqueId.contains("delete") || techniqueId.contains("dequeue") || random.nextBoolean();
        return new StepState.Builder()
            .setAlgorithmId(techniqueId)
            .setAlgorithmTitle(resolveTitle(content, "Queue Practice"))
            .setStructureType(StructureType.QUEUE)
            .setOperationType(dequeue ? OperationType.DEQUEUE : OperationType.ENQUEUE)
            .setStepNumber(random.nextInt(4) + 1)
            .setLinearData(toList(values))
            .addPointer("front", 0)
            .addPointer("rear", values.length - 1)
            .setDecisionContext(new DecisionContext(
                DecisionType.STRUCTURE_CHANGE,
                dequeue
                    ? "Queue front is " + values[0] + " and the next instruction removes the front item. What action matches that?"
                    : "Queue rear is " + values[values.length - 1] + " and the next instruction inserts a new item at the rear. What action matches that?",
                dequeue ? "Queues remove from the front." : "Queues insert at the rear.",
                Arrays.asList(ActionType.DEQUEUE_NODE, ActionType.ENQUEUE_NEIGHBOR, ActionType.MOVE_NEXT)
            ))
            .setSummary("front = 0, rear = " + (values.length - 1))
            .setHint("Queues are FIFO, so front and rear matter more than the picture.")
            .setCorrectAction(dequeue ? ActionType.DEQUEUE_NODE : ActionType.ENQUEUE_NEIGHBOR)
            .build();
    }

    private StepState buildLinkedScenario(String techniqueId, TechniqueContent content) {
        int[] values = resolveValues(content, new int[]{8, 12, 17, 25});
        ActionType action;
        String prompt;
        String explanation;
        if (techniqueId.contains("delete")) {
            action = ActionType.DELETE;
            prompt = "A linked-list delete operation is targeting node value " + values[random.nextInt(values.length)] + ". What action best matches the next step?";
            explanation = "This step is a deletion, so the link update removes a node.";
        } else if (techniqueId.contains("search") || techniqueId.contains("find") || techniqueId.contains("travers")) {
            action = ActionType.MOVE_NEXT;
            prompt = "You are traversing the linked list " + joinValues(values) + ". After checking one node and not finding the answer yet, what happens next?";
            explanation = "Traversal continues by moving to the next node.";
        } else {
            action = ActionType.INSERT_NODE;
            prompt = "The next linked-list operation adds a new node into " + joinValues(values) + ". What action best matches that step?";
            explanation = "Insertion changes the links to add a node.";
        }
        return new StepState.Builder()
            .setAlgorithmId(techniqueId)
            .setAlgorithmTitle(resolveTitle(content, "Linked List Practice"))
            .setStructureType(StructureType.LINKED_LIST)
            .setOperationType(action == ActionType.DELETE ? OperationType.DELETE : action == ActionType.INSERT_NODE ? OperationType.INSERT : OperationType.TRAVERSE)
            .setStepNumber(random.nextInt(4) + 1)
            .setLinearData(toList(values))
            .addPointer("node", random.nextInt(values.length))
            .setDecisionContext(new DecisionContext(
                DecisionType.STRUCTURE_CHANGE,
                prompt,
                explanation,
                Arrays.asList(ActionType.INSERT_NODE, ActionType.DELETE, ActionType.MOVE_NEXT, ActionType.MOVE_PREVIOUS)
            ))
            .setSummary("Linked list nodes: " + joinValues(values))
            .setHint("Focus on the operation meaning: move, insert, or delete.")
            .setCorrectAction(action)
            .build();
    }

    private StepState buildTreeScenario(String techniqueId, TechniqueContent content) {
        int[] values = resolveValues(content, new int[]{20, 10, 30, 5, 15, 25, 40});
        if (techniqueId.contains("bst")) {
            int nodeIndex = random.nextInt(3);
            int current = values[nodeIndex];
            int target = current;
            ActionType action = ActionType.FOUND;
            if (random.nextBoolean()) {
                target = current - 1;
                action = ActionType.MOVE_LEFT;
            } else if (random.nextBoolean()) {
                target = current + 1;
                action = ActionType.MOVE_RIGHT;
            }
            return new StepState.Builder()
                .setAlgorithmId(techniqueId)
                .setAlgorithmTitle(resolveTitle(content, "BST Practice"))
                .setStructureType(StructureType.TREE)
                .setOperationType(OperationType.TRAVERSE)
                .setStepNumber(random.nextInt(4) + 1)
                .setLinearData(toList(values))
                .addPointer("node", nodeIndex)
                .setDecisionContext(new DecisionContext(
                    DecisionType.TRAVERSAL_CHOICE,
                    "Current BST node is " + current + " and target is " + target + ". What is the next move?",
                    action == ActionType.FOUND ? "The current node matches the target." : action == ActionType.MOVE_LEFT
                        ? "Smaller values are searched in the left subtree."
                        : "Larger values are searched in the right subtree.",
                    Arrays.asList(ActionType.MOVE_LEFT, ActionType.MOVE_RIGHT, ActionType.FOUND, ActionType.INSERT_NODE)
                ))
                .putMetadata("target", String.valueOf(target))
                .setSummary("BST root sample: " + values[0])
                .setHint("Use the BST ordering rule instead of the rendered layout.")
                .setCorrectAction(action)
                .build();
        }

        ActionType action = randomTraversalAction();
        return new StepState.Builder()
            .setAlgorithmId(techniqueId)
            .setAlgorithmTitle(resolveTitle(content, "Tree Practice"))
            .setStructureType(StructureType.TREE)
            .setOperationType(OperationType.VISIT)
            .setStepNumber(random.nextInt(4) + 1)
            .setLinearData(toList(values))
            .addPointer("node", random.nextInt(Math.min(values.length, 3)))
            .setDecisionContext(new DecisionContext(
                DecisionType.TRAVERSAL_CHOICE,
                "For this tree-traversal step, which action should happen next?",
                resolveTraversalExplanation(action),
                Arrays.asList(ActionType.VISIT_LEFT, ActionType.VISIT_NODE, ActionType.VISIT_RIGHT)
            ))
            .setSummary("Traversal practice is based on the rule order, not the picture.")
            .setHint("Think about whether the algorithm visits left, node, or right next.")
            .setCorrectAction(action)
            .build();
    }

    private StepState buildGraphScenario(String techniqueId, TechniqueContent content) {
        StepState.Builder builder = new StepState.Builder()
            .setAlgorithmId(techniqueId)
            .setAlgorithmTitle(resolveTitle(content, "Graph Practice"))
            .setStructureType(StructureType.GRAPH)
            .setStepNumber(random.nextInt(4) + 1);

        if ("dijkstra_algorithm".equals(techniqueId) || "shortest_paths".equals(techniqueId)) {
            int from = random.nextInt(4);
            int to = Math.min(4, from + 1);
            return builder
                .setOperationType(OperationType.RELAX)
                .addPointer("from", from)
                .addPointer("to", to)
                .setDecisionContext(new DecisionContext(
                    DecisionType.TRAVERSAL_CHOICE,
                    "Dijkstra is examining edge " + from + " -> " + to + " to see whether it improves the path. What kind of action is this?",
                    "This step relaxes an edge by testing a better distance.",
                    Arrays.asList(ActionType.ENQUEUE_NEIGHBOR, ActionType.DEQUEUE_NODE, ActionType.VISIT_NODE, ActionType.COMPLETE)
                ))
                .setSummary("Weighted-graph practice question.")
                .setHint("Ask what the algorithm is doing conceptually: relax, settle, or move on.")
                .setCorrectAction(ActionType.ENQUEUE_NEIGHBOR)
                .build();
        }

        ActionType action = "topological_sort".equals(techniqueId) ? ActionType.DEQUEUE_NODE : (random.nextBoolean() ? ActionType.DEQUEUE_NODE : ActionType.ENQUEUE_NEIGHBOR);
        return builder
            .setOperationType(OperationType.EXPLORE_EDGE)
            .addPointer("node", random.nextInt(5))
            .setDecisionContext(new DecisionContext(
                DecisionType.TRAVERSAL_CHOICE,
                "Graph frontier question: should the algorithm process the current node now or add a discovered neighbor next?",
                action == ActionType.DEQUEUE_NODE
                    ? "The current frontier node should be processed now."
                    : "A newly discovered neighbor should be added to the frontier.",
                Arrays.asList(ActionType.ENQUEUE_NEIGHBOR, ActionType.DEQUEUE_NODE, ActionType.VISIT_RIGHT, ActionType.VISIT_NODE)
            ))
            .setSummary("Graph question generated independently from visualization state.")
            .setHint("Use the traversal rule and frontier logic.")
            .setCorrectAction(action)
            .build();
    }

    private boolean isStackTechnique(String techniqueId) {
        return "stack_basics".equals(techniqueId)
            || "linked_stack_push".equals(techniqueId)
            || "linked_stack_pop".equals(techniqueId);
    }

    private boolean isQueueTechnique(String techniqueId) {
        return "queue_basics".equals(techniqueId)
            || "circular_queue".equals(techniqueId)
            || "linked_queue".equals(techniqueId)
            || "deque".equals(techniqueId);
    }

    private ActionType randomTraversalAction() {
        int value = random.nextInt(3);
        if (value == 0) {
            return ActionType.VISIT_LEFT;
        }
        if (value == 1) {
            return ActionType.VISIT_NODE;
        }
        return ActionType.VISIT_RIGHT;
    }

    private String resolveTraversalExplanation(ActionType action) {
        if (action == ActionType.VISIT_LEFT) {
            return "The traversal rule descends into the left subtree next.";
        }
        if (action == ActionType.VISIT_RIGHT) {
            return "The traversal rule proceeds to the right subtree next.";
        }
        return "The traversal visits the current node at this step.";
    }

    private int[] resolveValues(TechniqueContent content, int[] fallback) {
        int[] base = content == null || content.sampleInput == null || content.sampleInput.length == 0
            ? fallback
            : content.sampleInput;
        int limit = Math.min(base.length, 7);
        int[] values = new int[limit];
        System.arraycopy(base, 0, values, 0, limit);
        return values;
    }

    private int[] ensureSorted(int[] values) {
        int[] sorted = values.clone();
        Arrays.sort(sorted);
        return sorted;
    }

    private List<Integer> toList(int[] values) {
        List<Integer> list = new ArrayList<>();
        for (int value : values) {
            list.add(value);
        }
        return list;
    }

    private String joinValues(int[] values) {
        List<String> parts = new ArrayList<>();
        for (int value : values) {
            parts.add(String.valueOf(value));
        }
        return "[" + String.join(", ", parts) + "]";
    }

    private String resolveTitle(TechniqueContent content, String fallback) {
        if (content != null && content.title != null && !content.title.trim().isEmpty()) {
            return content.title;
        }
        return fallback;
    }
}
