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

    public static void step(VisualizationRuntimeHost host) {
        advance(host);
    }

    public static void advance(VisualizationRuntimeHost host) {
        if (host.getStepIndex() == -1) {
            initializePreview(host);
            host.setStepIndex(0);
            host.setHighlightedLines(1, 0);
            int[] q = host.getHuffmanQueueWeights();
            host.updateStatusText("Initialize min-priority queue with " + (q != null ? q.length : 0) + " sorted frequency weights.");
            return;
        }

        int[] huffmanQueueWeights = host.getHuffmanQueueWeights();
        if (huffmanQueueWeights == null || huffmanQueueWeights.length <= 1) {
            int root = (huffmanQueueWeights != null && huffmanQueueWeights.length == 1) ? huffmanQueueWeights[0] : host.getHuffmanMergedWeight();
            host.finish("Huffman tree completed! All character nodes merged into single root with total weight " + root + ".");
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

        int nextStep = host.getStepIndex() + 1;
        host.setStepIndex(nextStep);
        host.setHuffmanMergedWeight(merged);
        host.setHuffmanQueueWeights(mergedQueue);
        host.setHuffmanState(mergedQueue, merged, first, second);
        host.setHighlightedLines(3, 1);
        host.updateStatusText("Extract min weights (" + first + ", " + second + ") and merge into new subtree node with weight " + merged + ".");

        if (mergedQueue.length <= 1) {
            host.finish("Huffman tree completed! Optimal prefix tree constructed with root weight " + merged + ".");
        }
    }

    public static void initializePreview(VisualizationRuntimeHost host) {
        int[] working = host.getWorkingValues();
        int[] weights;
        if (working != null && working.length >= 2) {
            weights = working.clone();
            Arrays.sort(weights);
        } else {
            weights = getDefaultWeights(1);
        }
        int merged = -1;
        host.setHuffmanQueueWeights(weights);
        host.setHuffmanMergedWeight(merged);
        host.setHuffmanState(weights, merged, -1, -1);
    }

    public static void initializePreview(VisualizationRuntimeHost host, int[] initialWeights) {
        int[] weights;
        if (initialWeights != null && initialWeights.length >= 2) {
            weights = initialWeights.clone();
            Arrays.sort(weights);
        } else {
            weights = getDefaultWeights(1);
        }
        int merged = -1;
        host.setHuffmanQueueWeights(weights);
        host.setHuffmanMergedWeight(merged);
        host.setHuffmanState(weights, merged, -1, -1);
    }

    public static int[] getDefaultWeights(int preset) {
        switch (preset) {
            case 2:
                return new int[] {1, 2, 3, 5, 8, 13, 21};
            case 3:
                return new int[] {10, 10, 10, 10, 10, 10, 10, 10};
            case 1:
            default:
                return new int[] {5, 9, 12, 13, 16, 45};
        }
    }

    public static String getDefaultInput(int preset) {
        switch (preset) {
            case 2:
                return "1, 2, 3, 5, 8, 13, 21";
            case 3:
                return "10, 10, 10, 10, 10, 10, 10, 10";
            case 1:
            default:
                return "5, 9, 12, 13, 16, 45";
        }
    }

    public static String getPresetDescription(int preset) {
        switch (preset) {
            case 2:
                return "Fibonacci Skew: Exponentially increasing frequencies producing a heavily skewed prefix tree with maximum codeword length variation.";
            case 3:
                return "Uniform Distribution: 8 symbols with identical probabilities producing a perfectly balanced full binary code tree of uniform length 3.";
            case 1:
            default:
                return "CLRS Canonical: Standard textbook 6-character frequency distribution (a:45, b:13, c:12, d:16, e:9, f:5) demonstrating greedy prefix tree construction.";
        }
    }

    public static int getTotalSteps(int[] weights) {
        if (weights == null || weights.length <= 1) {
            return 1;
        }
        return weights.length;
    }
}
