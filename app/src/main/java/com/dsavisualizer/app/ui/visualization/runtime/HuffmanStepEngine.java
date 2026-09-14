package com.dsavisualizer.app.ui.visualization.runtime;

import java.util.Arrays;

public final class HuffmanStepEngine extends BaseStepEngine {

    public HuffmanStepEngine(VisualizationRuntimeHost host) {
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
        if (host.getStepIndex() == -1) {
            initializePreview(host);
            host.setStepIndex(0);
            host.setHighlightedLines(1, 0);
            host.updateStatusText("Initialize the priority queue with leaf weights.");
            return;
        }

        int[] huffmanQueueWeights = host.getHuffmanQueueWeights();
        if (huffmanQueueWeights.length <= 1) {
            host.finish("Huffman tree completed.");
            return;
        }

        int first = huffmanQueueWeights[0];
        int second = huffmanQueueWeights[1];
        int merged = first + second;
        int[] mergedQueue = new int[huffmanQueueWeights.length - 1];
        mergedQueue[0] = merged;
        if (huffmanQueueWeights.length > 2) {
            System.arraycopy(huffmanQueueWeights, 2, mergedQueue, 1, huffmanQueueWeights.length - 2);
        }
        Arrays.sort(mergedQueue);
        host.setHuffmanMergedWeight(merged);
        host.setHuffmanQueueWeights(mergedQueue);
        host.setHuffmanState(mergedQueue, merged);
        host.setHighlightedLines(3, 1);
        host.updateStatusText("Merge weights " + first + " and " + second + " into " + merged + ".");
    }

    public static void initializePreview(VisualizationRuntimeHost host) {
        int[] weights = new int[] {5, 9, 12, 13, 16, 45};
        int merged = -1;
        host.setHuffmanQueueWeights(weights);
        host.setHuffmanMergedWeight(merged);
        host.setHuffmanState(weights, merged);
    }
}
