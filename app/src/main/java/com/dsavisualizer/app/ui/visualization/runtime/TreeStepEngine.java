package com.dsavisualizer.app.ui.visualization.runtime;

public final class TreeStepEngine extends BaseStepEngine {
    public TreeStepEngine(VisualizationRuntimeHost host) {
        super(host);
    }

    @Override
    public Step next() {
        if (host == null) {
            return Step.idle(true, false);
        }
        advance(host);
        host.captureCurrentSnapshot();
        return Step.moved(false, host.isPlaybackComplete());
    }

    public static void advance(VisualizationRuntimeHost host) {
        String techniqueId = host.getTechniqueId();
        if ("max_heap_insertion".equals(techniqueId) || "max_heap_deletion".equals(techniqueId)) {
            host.advanceHeapOperationStep();
        } else if ("huffman_algorithm".equals(techniqueId)) {
            host.advanceHuffmanStep();
        } else if ("avl_tree".equals(techniqueId)) {
            host.advanceAvlStep();
        } else if ("trie".equals(techniqueId)) {
            host.advanceTrieStep();
        } else {
            advanceTraversalStep(host);
        }
    }

    public static void advanceTraversalStep(VisualizationRuntimeHost host) {
        int[] traversalOrder = host.getTraversalOrder();
        if (traversalOrder.length == 0) {
            traversalOrder = host.buildTraversalOrder();
            host.setTraversalOrder(traversalOrder);
        }
        host.setStepIndex(host.getStepIndex() + 1);
        int stepIndex = host.getStepIndex();
        if (stepIndex >= traversalOrder.length) {
            host.finish("Traversal completed");
            return;
        }

        host.updateTraversalVisualization(traversalOrder, stepIndex);
        host.setHighlightedLines(Math.min(stepIndex + 1, 5), Math.min(stepIndex, 3));
        host.clearActiveEdge();

        int currentIndex = traversalOrder[stepIndex];
        int currentValue = host.getWorkingValues()[currentIndex];
        String techniqueId = host.getTechniqueId();
        if ("bst_search_parent_tracking".equals(techniqueId)) {
            if (currentValue == host.getTarget()) {
                String parentText = stepIndex == 0
                    ? "NULL"
                    : String.valueOf(host.getWorkingValues()[traversalOrder[stepIndex - 1]]);
                host.bindCurrentState("Found " + host.getTarget() + ". Parent is " + parentText);
            } else if (stepIndex == traversalOrder.length - 1) {
                host.bindCurrentState("Not found. Parent would be " + currentValue);
            } else {
                host.bindCurrentState("Checking node " + currentValue + " while searching for " + host.getTarget());
            }
            return;
        }

        if ("bst_insertion".equals(techniqueId)) {
            if (stepIndex == traversalOrder.length - 1) {
                host.bindCurrentState("Insertion path ends near node " + currentValue + " for item " + host.getTarget());
            } else {
                host.bindCurrentState("Comparing insert item " + host.getTarget() + " with node " + currentValue);
            }
            return;
        }

        if ("bst_deletion_case_a".equals(techniqueId)
            || "bst_deletion_case_b".equals(techniqueId)
            || "bst_deletion_del".equals(techniqueId)) {
            if (stepIndex == traversalOrder.length - 1) {
                host.bindCurrentState("Delete operation reached node " + currentValue + " for item " + host.getTarget());
            } else {
                host.bindCurrentState("Searching deletion path through node " + currentValue);
            }
            return;
        }

        if ("max_heap_insertion".equals(techniqueId) || "max_heap_deletion".equals(techniqueId)) {
            host.bindCurrentState(
                "max_heap_insertion".equals(techniqueId)
                    ? "Heap insertion compares with node " + currentValue
                    : "Heap deletion adjusts node " + currentValue
            );
            return;
        }

        host.bindCurrentState("Visiting tree node " + currentValue);
    }
}
