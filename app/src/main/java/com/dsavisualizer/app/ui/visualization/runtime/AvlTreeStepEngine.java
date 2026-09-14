package com.dsavisualizer.app.ui.visualization.runtime;

public final class AvlTreeStepEngine extends BaseStepEngine {

    public AvlTreeStepEngine(VisualizationRuntimeHost host) {
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
        int step = host.getStepIndex();
        if (step == -1) {
            host.setStepIndex(0);
            host.setActiveNodes(1, 3);
            host.setHighlightedLines(2, 1);
            host.updateStatusText("Insert 10 and check balance at node 20.");
            return;
        }
        if (step == 0) {
            host.setStepIndex(1);
            host.setActiveNodes(0, 1);
            host.setHighlightedLines(4, 2);
            host.updateStatusText("Perform right rotation to balance the AVL tree.");
            return;
        }
        host.finish("AVL rotation completed.");
    }
}
