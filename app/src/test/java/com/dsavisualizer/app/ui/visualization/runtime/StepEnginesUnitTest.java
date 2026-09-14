package com.dsavisualizer.app.ui.visualization.runtime;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StepEnginesUnitTest {

    @Test
    public void dijkstra_findNextDijkstraNode_selectsMinimumUnsettled() {
        int[] distances = {0, 4, 2, 9999, 9999};
        boolean[] settled = {true, false, false, false, false};

        int nextNode = DijkstraStepEngine.findNextDijkstraNode(distances, settled);
        assertEquals(2, nextNode);
    }

    @Test
    public void dijkstra_findNextNeighbor_findsValidAdjacentEdge() {
        int[][] weights = {
            {0, 2, 9999, 1, 9999},
            {2, 0, 3, 9999, 9999}
        };
        boolean[] settled = {false, false, false, false, false};

        int firstNeighbor = DijkstraStepEngine.findNextDijkstraNeighbor(weights, settled, 0, 0);
        assertEquals(1, firstNeighbor);

        int secondNeighbor = DijkstraStepEngine.findNextDijkstraNeighbor(weights, settled, 0, 2);
        assertEquals(3, secondNeighbor);
    }

    @Test
    public void unionFind_buildUnionPath_findsRootWalk() {
        int[] parents = {0, 0, 1, 2}; // 3 -> 2 -> 1 -> 0
        int[] path = UnionFindStepEngine.buildUnionPath(parents, 3);

        assertArrayEquals(new int[]{3, 2, 1, 0}, path);
    }

    @Test
    public void unionFind_buildUnionPath_handlesSelfRoot() {
        int[] parents = {0, 1, 2};
        int[] path = UnionFindStepEngine.buildUnionPath(parents, 1);

        assertArrayEquals(new int[]{1}, path);
    }

    @Test
    public void testMockHostStepEngineTransitions() {
        // Test basic advance logic using a lightweight mock host
        MockRuntimeHost host = new MockRuntimeHost();
        host.techniqueId = "avl_tree";
        host.stepIndex = -1;

        AvlTreeStepEngine.advance(host);
        assertEquals(0, host.stepIndex);
        assertEquals(1, host.activePrimary);
        assertEquals(3, host.activeSecondary);

        AvlTreeStepEngine.advance(host);
        assertEquals(1, host.stepIndex);
        assertEquals(0, host.activePrimary);
        assertEquals(1, host.activeSecondary);

        AvlTreeStepEngine.advance(host);
        assertTrue(host.playbackComplete);
    }

    @Test
    public void testTrieStepEngineTransitions() {
        MockRuntimeHost host = new MockRuntimeHost();
        host.techniqueId = "trie";
        host.stepIndex = -1;

        TrieStepEngine.advance(host);
        assertEquals(0, host.stepIndex);
        assertEquals(0, host.activePrimary);

        TrieStepEngine.advance(host);
        assertEquals(1, host.stepIndex);
        assertEquals(1, host.activePrimary);

        TrieStepEngine.advance(host);
        assertEquals(2, host.stepIndex);
        assertEquals(2, host.activePrimary);

        TrieStepEngine.advance(host);
        assertEquals(3, host.stepIndex);
        assertEquals(3, host.activePrimary);

        TrieStepEngine.advance(host);
        assertTrue(host.playbackComplete);
    }

    @Test
    public void testHuffmanStepEngineProgression() {
        MockRuntimeHost host = new MockRuntimeHost();
        host.stepIndex = -1;

        HuffmanStepEngine.advance(host);
        assertEquals(0, host.stepIndex);
        assertEquals(6, host.huffmanQueue.length);

        HuffmanStepEngine.advance(host);
        // 5 + 9 = 14, queue length becomes 5
        assertEquals(5, host.huffmanQueue.length);
        assertEquals(14, host.huffmanMerged);
    }

    @Test
    public void testWarshallMatrixAlgorithmProgression() {
        MockRuntimeHost host = new MockRuntimeHost();
        host.techniqueId = "warshall_algorithm";
        host.matrix = new int[][]{
            {0, 1, 0, 1},
            {0, 0, 1, 0},
            {0, 0, 0, 1},
            {0, 0, 0, 0}
        };

        MatrixAlgorithmEngine.advance(host);
        assertEquals(0, host.matrixK);
        assertEquals(0, host.matrixRow);
        assertEquals(1, host.matrixCol);
    }

    // Lightweight mock host for isolated unit tests
    private static class MockRuntimeHost implements VisualizationRuntimeHost {
        String techniqueId = "";
        int stepIndex = -1;
        int activePrimary = -1;
        int activeSecondary = -1;
        boolean playbackComplete = false;
        int[] huffmanQueue = new int[0];
        int huffmanMerged = -1;
        int[][] matrix = new int[0][0];
        int matrixK = -1;
        int matrixRow = -1;
        int matrixCol = -1;

        @Override public String getTechniqueId() { return techniqueId; }
        @Override public String getExpressionInput() { return ""; }
        @Override public void setExpressionInput(String s) {}
        @Override public String getTraversalMode() { return ""; }
        @Override public int[] getWorkingValues() { return new int[]{10, 20, 30}; }
        @Override public void setWorkingValues(int[] v) {}
        @Override public int getTarget() { return 0; }
        @Override public int getStepIndex() { return stepIndex; }
        @Override public void setStepIndex(int v) { this.stepIndex = v; }
        @Override public int getAuxIndex() { return 0; }
        @Override public void setAuxIndex(int v) {}
        @Override public int getFoundIndex() { return 0; }
        @Override public void setFoundIndex(int v) {}
        @Override public int getBinaryPhase() { return 0; }
        @Override public void setBinaryPhase(int v) {}
        @Override public int getBinaryMidIndex() { return 0; }
        @Override public void setBinaryMidIndex(int v) {}
        @Override public int getHeapSize() { return 0; }
        @Override public void setHeapSize(int v) {}
        @Override public int getHeapSortPhase() { return 0; }
        @Override public void setHeapSortPhase(int v) {}
        @Override public int getHeapSortHeapifyIndex() { return 0; }
        @Override public void setHeapSortHeapifyIndex(int v) {}
        @Override public int getRadixDivisor() { return 1; }
        @Override public void setRadixDivisor(int v) {}
        @Override public int getRadixMaxValue() { return 0; }
        @Override public void setRadixMaxValue(int v) {}
        @Override public int getDifficultyLevel() { return 0; }
        @Override public void showPredictionUI(String q, String[] opt, int c, String exp) {}
        @Override public boolean isPredictionPending() { return false; }
        @Override public void resetVisualizationState() {}
        @Override public void restorePreviousSnapshot() {}
        @Override public boolean canRestorePreviousSnapshot() { return false; }
        @Override public void captureCurrentSnapshot() {}
        @Override public boolean isPlaybackComplete() { return playbackComplete; }
        @Override public void sortWorkingValues() {}
        @Override public void swapValues(int left, int right) {}
        @Override public void buildMaxHeap() {}
        @Override public void updatePointerState(String pLabel, int pIdx, String sLabel, int sIdx) {}
        @Override public void setStructureMotion(String motion, int index) {}
        @Override public void clearPointerState() {}
        @Override public void setHighlightedLines(int code, int algo) {}
        @Override public void updateArrayVisualization(int a, int s, int f) {}
        @Override public void updateTraversalVisualization(int[] order, int step) {}
        @Override public void updateStatusText(String m) {}
        @Override public void bindCurrentState(String m) {}
        @Override public void finish(String m) { this.playbackComplete = true; }
        @Override public void finishWithoutResetLabel() { this.playbackComplete = true; }
        @Override public void updateBinaryChips() {}
        @Override public void syncScrollToFocus(int idx) {}
        @Override public void setActiveEdge(int from, int to) {}
        @Override public void clearActiveEdge() {}
        @Override public void setRadixState(int d, int b) {}
        @Override public int[] getTraversalOrder() { return new int[0]; }
        @Override public void setTraversalOrder(int[] v) {}
        @Override public int[] buildTraversalOrder() { return new int[0]; }

        @Override public int[] getDijkstraDistances() { return new int[0]; }
        @Override public void setDijkstraDistances(int[] d) {}
        @Override public boolean[] getDijkstraSettled() { return new boolean[0]; }
        @Override public void setDijkstraSettled(boolean[] s) {}
        @Override public int getDijkstraCurrentNode() { return 0; }
        @Override public void setDijkstraCurrentNode(int n) {}
        @Override public int getDijkstraPhase() { return 0; }
        @Override public void setDijkstraPhase(int p) {}
        @Override public int getDijkstraNeighborIndex() { return 0; }
        @Override public void setDijkstraNeighborIndex(int i) {}
        @Override public int[][] getDijkstraWeights() { return new int[0][0]; }
        @Override public void setDijkstraWeights(int[][] w) {}
        @Override public void setDijkstraState(int[] dist, boolean[] set, int cur) {}
        @Override public void initializeDijkstraPreview() {}

        @Override public int[] getHuffmanQueueWeights() { return huffmanQueue; }
        @Override public void setHuffmanQueueWeights(int[] w) { this.huffmanQueue = w; }
        @Override public int getHuffmanMergedWeight() { return huffmanMerged; }
        @Override public void setHuffmanMergedWeight(int w) { this.huffmanMerged = w; }
        @Override public void setHuffmanState(int[] w, int m) {}
        @Override public void initializeHuffmanPreview() {
            HuffmanStepEngine.initializePreview(this);
        }

        @Override public void setActiveNodes(int primary, int secondary) {
            this.activePrimary = primary;
            this.activeSecondary = secondary;
        }

        @Override public int[][] getMatrixValues() { return matrix; }
        @Override public void setMatrixValues(int[][] v) { this.matrix = v; }
        @Override public int getMatrixK() { return matrixK; }
        @Override public void setMatrixK(int k) { this.matrixK = k; }
        @Override public int getMatrixRow() { return matrixRow; }
        @Override public void setMatrixRow(int r) { this.matrixRow = r; }
        @Override public int getMatrixCol() { return matrixCol; }
        @Override public void setMatrixCol(int c) { this.matrixCol = c; }
        @Override public void setMatrixState(int[][] v, int k, int r, int c, String l) {}
        @Override public String resolveMatrixLabel() { return "Warshall"; }
        @Override public void initializeCustomSortPreview() {}

        @Override public int getHeapModePhase() { return 0; }
        @Override public void setHeapModePhase(int p) {}

        @Override public int[] getUnionParents() { return new int[0]; }
        @Override public void setUnionParents(int[] p) {}
        @Override public int[] getUnionPath() { return new int[0]; }
        @Override public void setUnionPath(int[] p) {}
        @Override public int getUnionPathStep() { return 0; }
        @Override public void setUnionPathStep(int s) {}
        @Override public int getUnionFocusNode() { return 0; }
        @Override public void setUnionFocusNode(int n) {}
        @Override public int getUnionRootNode() { return 0; }
        @Override public void setUnionRootNode(int n) {}
        @Override public void setUnionFindState(int[] p, int f, int r) {}
        @Override public void initializeUnionFindPreview() {}

        @Override public int[][] getGraphEdges() { return new int[0][0]; }

        @Override public void advancePatternMatchingStep() {}
        @Override public void advanceBinarySearchStep() {}
        @Override public void advanceJumpSearchStep() {}
        @Override public void advanceLinearSearchStep() {}
        @Override public void advanceMergingStep() {}
        @Override public void advanceMergeSortStep() {}
        @Override public void advanceRadixSortStep() {}
        @Override public void advanceQuickSortFamilyStep() {}
        @Override public void advanceHeapSortStep() {}
        @Override public void advanceBubbleSortStep() {}
        @Override public void advanceSelectionSortStep() {}
        @Override public void advanceInsertionSortStep() {}
        @Override public void advanceUnionFindStep() {}
        @Override public void advanceLinearStructureStepCore() {}
        @Override public void advanceMatrixStep() {}
        @Override public void advanceDijkstraStep() {}
        @Override public void advanceGraphTraversalStep() {}
        @Override public void advanceHeapOperationStep() {}
        @Override public void advanceHuffmanStep() {}
        @Override public void advanceAvlStep() {}
        @Override public void advanceTrieStep() {}
        @Override public void advanceTreeTraversalStep() {}
    }
}
