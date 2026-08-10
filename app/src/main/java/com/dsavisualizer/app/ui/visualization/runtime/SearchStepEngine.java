package com.dsavisualizer.app.ui.visualization.runtime;

import java.util.Arrays;

public final class SearchStepEngine extends BaseStepEngine {
    private static final String ELEMENT_NOT_FOUND = "Element Not Found";

    public SearchStepEngine(VisualizationRuntimeHost host) {
        super(host);
    }

    @Override
    public Step next() {
        if (host == null) {
            return Step.idle(true, false);
        }
        advance(host);
        if (host.isPredictionPending()) {
            return Step.idle(false, false);
        }
        host.captureCurrentSnapshot();
        return Step.moved(false, host.isPlaybackComplete());
    }

    public static void advance(VisualizationRuntimeHost host) {
        String techniqueId = host.getTechniqueId();
        if ("pattern_matching".equals(techniqueId)) {
            host.advancePatternMatchingStep();
        } else if ("binary_search".equals(techniqueId)) {
            advanceBinarySearch(host);
        } else if ("jump_search".equals(techniqueId)) {
            advanceJumpSearch(host);
        } else {
            advanceLinearSearch(host);
        }
    }

    private static void advanceBinarySearch(VisualizationRuntimeHost host) {
        host.sortWorkingValues();
        int[] values = host.getWorkingValues();
        int target = host.getTarget();

        if (host.getStepIndex() == -1) {
            host.setStepIndex(0);
            host.setAuxIndex(values.length - 1);
            host.setBinaryPhase(0);
            host.setBinaryMidIndex(-1);
        }

        if (host.getStepIndex() > host.getAuxIndex()) {
            host.finish(ELEMENT_NOT_FOUND);
            return;
        }

        if (host.getBinaryPhase() == 0) {
            int mid = host.getStepIndex() + (host.getAuxIndex() - host.getStepIndex()) / 2;
            host.setBinaryMidIndex(mid);
            host.updatePointerState("low", host.getStepIndex(), "high", host.getAuxIndex());
            host.setHighlightedLines(1, 0);
            host.updateArrayVisualization(-1, -1, -1);
            host.updateStatusText("Current range is from index " + host.getStepIndex() + " to " + host.getAuxIndex());
            host.setBinaryPhase(1);
            host.updateBinaryChips();
            host.syncScrollToFocus(host.getStepIndex());
            return;
        }

        if (host.getBinaryPhase() == 1) {
            int mid = host.getBinaryMidIndex();
            
            // Prediction Point: Before comparing mid with target
            if (host.getDifficultyLevel() > 1 && !host.isPredictionPending()) {
                String question = "Compare target " + target + " with mid value " + values[mid] + ". What happens next?";
                String[] options = {"Target matches mid value", "Target is smaller (Go Left)", "Target is larger (Go Right)"};
                int correctIdx = values[mid] == target ? 0 : (target < values[mid] ? 1 : 2);
                String explanation = values[mid] == target ? 
                    "Correct! Mid value " + values[mid] + " equals target " + target + "." :
                    (target < values[mid] ? 
                        "Since " + target + " < " + values[mid] + ", the target must be in the left half." :
                        "Since " + target + " > " + values[mid] + ", the target must be in the right half.");
                
                host.showPredictionUI(question, options, correctIdx, explanation);
                return; // Wait for user prediction
            }

            host.updatePointerState("low", host.getStepIndex(), "high", host.getAuxIndex());
            host.setHighlightedLines(4, 1);
            host.updateArrayVisualization(mid, -1, -1);
            host.updateStatusText("Middle value is " + values[mid] + " at index " + mid);

            if (values[mid] == target) {
                host.setFoundIndex(mid);
                host.setHighlightedLines(5, 2);
                host.updateArrayVisualization(mid, -1, mid);
                host.updateStatusText("Found " + target + " at index " + mid);
                host.finishWithoutResetLabel();
                host.updateBinaryChips();
                host.syncScrollToFocus(mid);
                return;
            }

            host.setBinaryPhase(2);
            host.updateBinaryChips();
            host.syncScrollToFocus(mid);
            return;
        }

        int mid = host.getBinaryMidIndex();
        if (values[mid] < target) {
            host.setStepIndex(mid + 1);
            host.setHighlightedLines(8, 4);
            host.updateStatusText("Target is greater, so move low to index " + host.getStepIndex());
        } else {
            host.setAuxIndex(mid - 1);
            host.setHighlightedLines(10, 3);
            host.updateStatusText("Target is smaller, so move high to index " + host.getAuxIndex());
        }

        host.updatePointerState("low", host.getStepIndex(), "high", host.getAuxIndex());
        host.updateArrayVisualization(mid, -1, -1);
        host.setBinaryPhase(0);
        host.updateBinaryChips();
        int nextFocus = Math.max(0, Math.min(host.getStepIndex(), values.length - 1));
        host.syncScrollToFocus(nextFocus);
    }

    private static void advanceJumpSearch(VisualizationRuntimeHost host) {
        host.sortWorkingValues();
        int[] values = host.getWorkingValues();
        int blockSize = (int) Math.floor(Math.sqrt(values.length));
        if (host.getStepIndex() == -1) {
            host.setStepIndex(0);
        }

        if (host.getStepIndex() >= values.length) {
            host.finish(ELEMENT_NOT_FOUND);
            return;
        }

        int compareIndex = Math.min(host.getStepIndex() + blockSize - 1, values.length - 1);
        host.updatePointerState("jump", compareIndex, "start", host.getStepIndex());
        host.setHighlightedLines(4, 1);
        if (host.getTarget() > values[compareIndex]) {
            host.bindCurrentState("Jumping to next block after index " + compareIndex);
            host.updateArrayVisualization(compareIndex, -1, -1);
            host.setStepIndex(host.getStepIndex() + blockSize);
            host.syncScrollToFocus(compareIndex);
            return;
        }

        for (int i = host.getStepIndex(); i <= compareIndex; i++) {
            if (values[i] == host.getTarget()) {
                host.setFoundIndex(i);
                host.setHighlightedLines(9, 4);
                host.updateArrayVisualization(i, -1, i);
                host.finish("Found " + host.getTarget() + " at index " + i);
                host.syncScrollToFocus(i);
                return;
            }
        }
        host.finish(ELEMENT_NOT_FOUND);
    }

    private static void advanceLinearSearch(VisualizationRuntimeHost host) {
        host.setStepIndex(host.getStepIndex() + 1);
        if (host.getStepIndex() >= host.getWorkingValues().length) {
            host.finish(ELEMENT_NOT_FOUND);
            return;
        }

        host.updatePointerState("ptr", host.getStepIndex(), "", -1);
        host.setHighlightedLines(1, 1);
        host.updateArrayVisualization(host.getStepIndex(), -1, -1);
        if (host.getWorkingValues()[host.getStepIndex()] == host.getTarget()) {
            host.setFoundIndex(host.getStepIndex());
            host.setHighlightedLines(3, 2);
            host.updateArrayVisualization(host.getStepIndex(), -1, host.getFoundIndex());
            host.finish("Found " + host.getTarget() + " at index " + host.getStepIndex());
        } else {
            host.bindCurrentState("Comparing " + host.getTarget() + " with " + host.getWorkingValues()[host.getStepIndex()]);
        }
        host.syncScrollToFocus(host.getStepIndex());
    }
}
