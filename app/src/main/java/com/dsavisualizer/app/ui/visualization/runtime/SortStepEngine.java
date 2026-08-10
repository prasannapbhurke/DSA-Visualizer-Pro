package com.dsavisualizer.app.ui.visualization.runtime;

public final class SortStepEngine extends BaseStepEngine {
    public SortStepEngine(VisualizationRuntimeHost host) {
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
        if (host.getWorkingValues().length == 0) {
            host.finish("No values");
            return;
        }
        String techniqueId = host.getTechniqueId();
        if ("merging".equals(techniqueId)) {
            host.advanceMergingStep();
        } else if ("merge_sort".equals(techniqueId)) {
            host.advanceMergeSortStep();
        } else if ("radix_sort".equals(techniqueId)) {
            host.advanceRadixSortStep();
        } else if ("quicksort".equals(techniqueId)) {
            host.advanceQuickSortFamilyStep();
        } else if ("heapsort".equals(techniqueId)) {
            advanceHeapSort(host);
        } else if ("bubble_sort".equals(techniqueId)) {
            advanceBubbleSort(host);
        } else if ("selection_sort".equals(techniqueId)) {
            advanceSelectionSort(host);
        } else {
            advanceInsertionSort(host);
        }
    }

    private static void advanceHeapSort(VisualizationRuntimeHost host) {
        if (host.getStepIndex() == -1) {
            host.buildMaxHeap();
            host.setHeapSize(host.getWorkingValues().length);
            host.setHeapSortPhase(0);
            host.setHeapSortHeapifyIndex(0);
            host.setStepIndex(0);
            host.updatePointerState("root", 0, "", -1);
            host.updateArrayVisualization(0, -1, -1);
            host.updateStatusText("Max heap is ready. Now move the root to the sorted end one by one.");
            host.setHighlightedLines(1, 0);
            return;
        }
        if (host.getHeapSize() <= 1) {
            host.finish("HEAPSORT completed");
            return;
        }
        if (host.getHeapSortPhase() == 0) {
            int sortedIndex = host.getHeapSize() - 1;
            host.swapValues(0, sortedIndex);
            host.setHeapSize(host.getHeapSize() - 1);
            host.setHeapSortHeapifyIndex(0);
            host.setActiveEdge(0, sortedIndex);
            host.updatePointerState("root", 0, "sorted", sortedIndex);
            host.updateArrayVisualization(0, sortedIndex, -1);
            host.updateStatusText("Swap root with index " + sortedIndex + " before restoring heap order.");
            host.setHighlightedLines(3, 2);
            host.syncScrollToFocus(sortedIndex);
            host.setHeapSortPhase(1);
            return;
        }

        int sinkIndex = host.getHeapSortHeapifyIndex();
        int left = (2 * sinkIndex) + 1;
        int right = left + 1;
        if (left >= host.getHeapSize()) {
            host.setHeapSortPhase(0);
            host.updateStatusText("Heap restored for the next extraction.");
            return;
        }
        int[] values = host.getWorkingValues();
        int largest = left;
        if (right < host.getHeapSize() && values[right] > values[left]) {
            largest = right;
        }
        if (values[sinkIndex] >= values[largest]) {
            host.setHeapSortPhase(0);
            host.updateStatusText("Heap property holds at node " + sinkIndex + ".");
            return;
        }
        host.swapValues(sinkIndex, largest);
        host.setActiveEdge(sinkIndex, largest);
        host.updatePointerState("sink", sinkIndex, "child", largest);
        host.updateArrayVisualization(sinkIndex, largest, -1);
        host.updateStatusText("Percolate down by swapping node " + sinkIndex + " with child " + largest + ".");
        host.setHighlightedLines(5, 3);
        host.syncScrollToFocus(largest);
        host.setHeapSortHeapifyIndex(largest);
    }

    private static void advanceBubbleSort(VisualizationRuntimeHost host) {
        if (host.getStepIndex() == -1) {
            host.setStepIndex(0);
            host.setAuxIndex(0);
        }

        if (host.getStepIndex() >= host.getWorkingValues().length - 1) {
            host.finish("Bubble Sort completed");
            return;
        }

        if (host.getAuxIndex() >= host.getWorkingValues().length - host.getStepIndex() - 1) {
            host.setStepIndex(host.getStepIndex() + 1);
            host.setAuxIndex(0);
            advanceBubbleSort(host);
            return;
        }

        int[] values = host.getWorkingValues();
        int aux = host.getAuxIndex();
        if (values[aux] > values[aux + 1]) {
            int leftValue = values[aux];
            int rightValue = values[aux + 1];
            host.swapValues(aux, aux + 1);
            host.setHighlightedLines(3, 1);
            host.updateStatusText("Swapping " + leftValue + " and " + rightValue);
        } else {
            host.setHighlightedLines(2, 0);
            host.updateStatusText("Comparing " + values[aux] + " and " + values[aux + 1]);
        }
        host.updatePointerState("j", aux, "j+1", aux + 1);
        host.updateArrayVisualization(aux, aux + 1, -1);
        host.syncScrollToFocus(aux);
        host.setAuxIndex(aux + 1);
    }

    private static void advanceSelectionSort(VisualizationRuntimeHost host) {
        if (host.getStepIndex() == -1) {
            host.setStepIndex(0);
            host.setAuxIndex(1);
            host.setFoundIndex(0);
        }

        if (host.getStepIndex() >= host.getWorkingValues().length - 1) {
            host.finish("Selection Sort completed");
            return;
        }

        if (host.getAuxIndex() >= host.getWorkingValues().length) {
            host.swapValues(host.getStepIndex(), host.getFoundIndex());
            host.setHighlightedLines(8, 3);
            host.updateStatusText("Placing minimum at index " + host.getStepIndex());
            host.setStepIndex(host.getStepIndex() + 1);
            host.setFoundIndex(host.getStepIndex());
            host.setAuxIndex(host.getStepIndex() + 1);
            host.updatePointerState("i", host.getStepIndex() - 1, "min", host.getFoundIndex());
            host.updateArrayVisualization(host.getStepIndex() - 1, host.getFoundIndex(), -1);
            host.syncScrollToFocus(host.getStepIndex() - 1);
            return;
        }

        int[] values = host.getWorkingValues();
        if (values[host.getAuxIndex()] < values[host.getFoundIndex()]) {
            host.setFoundIndex(host.getAuxIndex());
        }
        host.updateStatusText("Scanning for minimum from index " + host.getStepIndex());
        host.setHighlightedLines(4, 1);
        host.updatePointerState("scan", host.getAuxIndex(), "min", host.getFoundIndex());
        host.updateArrayVisualization(host.getAuxIndex(), host.getFoundIndex(), -1);
        host.syncScrollToFocus(host.getAuxIndex());
        host.setAuxIndex(host.getAuxIndex() + 1);
    }

    private static void advanceInsertionSort(VisualizationRuntimeHost host) {
        if (host.getStepIndex() == -1) {
            host.setStepIndex(1);
            host.setAuxIndex(1);
        }

        if (host.getStepIndex() >= host.getWorkingValues().length) {
            host.finish("Insertion Sort completed");
            return;
        }

        int current = host.getAuxIndex();
        int[] values = host.getWorkingValues();
        if (current > 0 && values[current - 1] > values[current]) {
            host.swapValues(current - 1, current);
            host.setAuxIndex(current - 1);
            host.setHighlightedLines(4, 1);
            host.updateStatusText("Shifting " + host.getWorkingValues()[current] + " left");
            host.updatePointerState("key", host.getAuxIndex(), "prev", host.getAuxIndex() + 1);
            host.updateArrayVisualization(host.getAuxIndex(), host.getAuxIndex() + 1, -1);
            host.syncScrollToFocus(host.getAuxIndex());
        } else {
            host.setStepIndex(host.getStepIndex() + 1);
            host.setAuxIndex(host.getStepIndex());
            host.setHighlightedLines(8, 3);
            host.updateStatusText("Inserted element in sorted position");
            host.updatePointerState("i", host.getStepIndex() - 1, "", -1);
            host.updateArrayVisualization(host.getStepIndex() - 1, -1, -1);
            host.syncScrollToFocus(host.getStepIndex() - 1);
        }
    }
}
