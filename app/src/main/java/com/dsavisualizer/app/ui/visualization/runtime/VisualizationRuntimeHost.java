package com.dsavisualizer.app.ui.visualization.runtime;

public interface VisualizationRuntimeHost {
    String getTechniqueId();
    String getExpressionInput();
    void setExpressionInput(String expressionInput);
    String getTraversalMode();
    int[] getWorkingValues();
    void setWorkingValues(int[] values);
    int getTarget();
    int getStepIndex();
    void setStepIndex(int value);
    int getAuxIndex();
    void setAuxIndex(int value);
    int getFoundIndex();
    void setFoundIndex(int value);
    int getBinaryPhase();
    void setBinaryPhase(int value);
    int getBinaryMidIndex();
    void setBinaryMidIndex(int value);
    int getHeapSize();
    void setHeapSize(int value);
    int getHeapSortPhase();
    void setHeapSortPhase(int value);
    int getHeapSortHeapifyIndex();
    void setHeapSortHeapifyIndex(int value);
    int getRadixDivisor();
    void setRadixDivisor(int value);
    int getRadixMaxValue();
    void setRadixMaxValue(int value);

    int getDifficultyLevel();
    void showPredictionUI(String question, String[] options, int correctIndex, String mistakeExplanation);
    boolean isPredictionPending();
    void resetVisualizationState();
    void restorePreviousSnapshot();
    boolean canRestorePreviousSnapshot();
    void captureCurrentSnapshot();
    boolean isPlaybackComplete();

    void sortWorkingValues();
    void swapValues(int left, int right);
    void buildMaxHeap();
    void updatePointerState(String primaryLabel, int primaryIndex, String secondaryLabel, int secondaryIndex);
    void setStructureMotion(String motion, int index);
    void clearPointerState();
    void setHighlightedLines(int codeLine, int algorithmLine);
    void updateArrayVisualization(int active, int secondary, int found);
    void updateTraversalVisualization(int[] traversalOrder, int activeStep);
    void updateStatusText(String message);
    void bindCurrentState(String message);
    void finish(String message);
    void finishWithoutResetLabel();
    void updateBinaryChips();
    void syncScrollToFocus(int index);
    void setActiveEdge(int from, int to);
    void clearActiveEdge();
    void setRadixState(int divisor, int activeBucket);
    default void setRadixState(int divisor, int activeBucket, int processedCount) {
        setRadixState(divisor, activeBucket);
    }
    int[] getTraversalOrder();
    void setTraversalOrder(int[] values);
    int[] buildTraversalOrder();
    default int getGraphStartNode() {
        return 0;
    }

    // Dijkstra state
    int[] getDijkstraDistances();
    void setDijkstraDistances(int[] distances);
    boolean[] getDijkstraSettled();
    void setDijkstraSettled(boolean[] settled);
    int getDijkstraCurrentNode();
    void setDijkstraCurrentNode(int node);
    int getDijkstraPhase();
    void setDijkstraPhase(int phase);
    int getDijkstraNeighborIndex();
    void setDijkstraNeighborIndex(int index);
    int[][] getDijkstraWeights();
    void setDijkstraWeights(int[][] weights);
    void setDijkstraState(int[] distances, boolean[] settled, int currentNode);
    void initializeDijkstraPreview();

    // Huffman state
    int[] getHuffmanQueueWeights();
    void setHuffmanQueueWeights(int[] weights);
    int getHuffmanMergedWeight();
    void setHuffmanMergedWeight(int weight);
    void setHuffmanState(int[] queueWeights, int mergedWeight);
    void setHuffmanState(int[] queueWeights, int mergedWeight, int leftWeight, int rightWeight);
    void initializeHuffmanPreview();

    // AVL & Trie
    void setActiveNodes(int primary, int secondary);

    // Advanced Data Structures
    default int getAdvancedDsPreset() {
        return 1;
    }
    default void setAdvancedDsPreset(int preset) {}
    default String getAdvancedDsCustomInput() {
        return "";
    }
    default void setAdvancedDsCustomInput(String input) {}

    // Matrix state
    int[][] getMatrixValues();
    void setMatrixValues(int[][] values);
    int getMatrixK();
    void setMatrixK(int k);
    int getMatrixRow();
    void setMatrixRow(int row);
    int getMatrixCol();
    void setMatrixCol(int col);
    void setMatrixState(int[][] values, int k, int row, int col, String label);
    String resolveMatrixLabel();
    void initializeCustomSortPreview();

    // Heap operation phase
    int getHeapModePhase();
    void setHeapModePhase(int phase);

    // Union-Find state
    int[] getUnionParents();
    void setUnionParents(int[] parents);
    int[] getUnionPath();
    void setUnionPath(int[] path);
    int getUnionPathStep();
    void setUnionPathStep(int step);
    int getUnionFocusNode();
    void setUnionFocusNode(int node);
    int getUnionRootNode();
    void setUnionRootNode(int node);
    void setUnionFindState(int[] parents, int focusNode, int rootNode);
    void initializeUnionFindPreview();

    // Graph
    int[][] getGraphEdges();

    // String & Pattern Matching state
    String getPatternText();
    void setPatternText(String text);
    String getPatternQuery();
    void setPatternQuery(String query);
    int getPatternPreset();
    void setPatternPreset(int preset);
    void setPatternState(String text, String query, int windowStart, int matchIndex, boolean found);
    void setAuxiliaryArray(int[] aux);

    void advancePatternMatchingStep();
    void advanceBinarySearchStep();
    void advanceJumpSearchStep();
    void advanceLinearSearchStep();

    void advanceMergingStep();
    void advanceMergeSortStep();
    void advanceRadixSortStep();
    void advanceQuickSortFamilyStep();
    void advanceHeapSortStep();
    void advanceBubbleSortStep();
    void advanceSelectionSortStep();
    void advanceInsertionSortStep();

    void advanceUnionFindStep();
    void advanceLinearStructureStepCore();

    void advanceMatrixStep();
    void advanceDijkstraStep();
    void advanceGraphTraversalStep();

    void advanceHeapOperationStep();
    void advanceHuffmanStep();
    void advanceAvlStep();
    void advanceTrieStep();
    void advanceTreeTraversalStep();
}
