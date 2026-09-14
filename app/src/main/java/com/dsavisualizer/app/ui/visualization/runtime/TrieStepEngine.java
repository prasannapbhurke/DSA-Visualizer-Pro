package com.dsavisualizer.app.ui.visualization.runtime;

public final class TrieStepEngine extends BaseStepEngine {

    public TrieStepEngine(VisualizationRuntimeHost host) {
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
            host.setActiveNodes(0, -1);
            host.setHighlightedLines(1, 0);
            host.updateStatusText("Start at the root for the word \"cat\".");
            return;
        }
        if (step == 0) {
            host.setStepIndex(1);
            host.setActiveNodes(1, -1);
            host.setHighlightedLines(3, 1);
            host.updateStatusText("Create or move to node 'c'.");
            return;
        }
        if (step == 1) {
            host.setStepIndex(2);
            host.setActiveNodes(2, -1);
            host.setHighlightedLines(3, 1);
            host.updateStatusText("Create or move to node 'a'.");
            return;
        }
        if (step == 2) {
            host.setStepIndex(3);
            host.setActiveNodes(3, -1);
            host.setHighlightedLines(3, 1);
            host.updateStatusText("Create or move to node 't' and mark end.");
            return;
        }
        host.finish("Trie insertion completed.");
    }
}
