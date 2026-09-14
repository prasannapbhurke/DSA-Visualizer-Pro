package com.dsavisualizer.app.ui.visualization.runtime;

public final class HeapOperationStepEngine extends BaseStepEngine {

    public HeapOperationStepEngine(VisualizationRuntimeHost host) {
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
        int[] workingValues = host.getWorkingValues();
        if (workingValues.length == 0) {
            host.finish("Heap is empty");
            return;
        }

        int stepIndex = host.getStepIndex();
        int heapSize = host.getHeapSize();
        int heapModePhase = host.getHeapModePhase();

        if (stepIndex == -1) {
            heapSize = workingValues.length;
            heapModePhase = 0;
            stepIndex = "max_heap_insertion".equals(host.getTechniqueId()) ? heapSize - 1 : 0;
            host.setHeapSize(heapSize);
            host.setHeapModePhase(heapModePhase);
            host.setStepIndex(stepIndex);
        }

        if ("max_heap_insertion".equals(host.getTechniqueId())) {
            int parent = (stepIndex - 1) / 2;
            if (stepIndex <= 0 || workingValues[parent] >= workingValues[stepIndex]) {
                host.finish("Heap insertion completed");
                return;
            }
            host.swapValues(parent, stepIndex);
            workingValues = host.getWorkingValues();
            host.setActiveEdge(parent, stepIndex);
            host.updateArrayVisualization(parent, stepIndex, -1);
            host.setHighlightedLines(4, 2);
            host.updateStatusText("Percolate up: swap " + workingValues[stepIndex] + " with parent " + workingValues[parent] + ".");
            host.setStepIndex(parent);
            return;
        }

        if (heapModePhase == 0) {
            host.swapValues(0, heapSize - 1);
            host.setActiveEdge(0, heapSize - 1);
            host.updateArrayVisualization(0, heapSize - 1, -1);
            host.setHighlightedLines(2, 1);
            host.updateStatusText("Move the maximum value to the end of the heap.");
            heapSize--;
            stepIndex = 0;
            heapModePhase = 1;
            host.setHeapSize(heapSize);
            host.setStepIndex(stepIndex);
            host.setHeapModePhase(heapModePhase);
            return;
        }

        int left = (2 * stepIndex) + 1;
        int right = (2 * stepIndex) + 2;
        if (left >= heapSize) {
            host.setHeapModePhase(0);
            if (heapSize <= 1) {
                host.finish("Heap deletion completed");
            } else {
                host.updateStatusText("Heap order restored after deletion.");
            }
            return;
        }

        int largest = left;
        if (right < heapSize && workingValues[right] > workingValues[left]) {
            largest = right;
        }
        if (workingValues[stepIndex] >= workingValues[largest]) {
            host.setHeapModePhase(0);
            host.updateStatusText("Heap property already holds at node " + stepIndex + ".");
            return;
        }

        host.swapValues(stepIndex, largest);
        workingValues = host.getWorkingValues();
        host.setActiveEdge(stepIndex, largest);
        host.updateArrayVisualization(stepIndex, largest, -1);
        host.setHighlightedLines(4, 2);
        host.updateStatusText("Percolate down: swap node " + stepIndex + " with child " + largest + ".");
        host.setStepIndex(largest);
    }
}
