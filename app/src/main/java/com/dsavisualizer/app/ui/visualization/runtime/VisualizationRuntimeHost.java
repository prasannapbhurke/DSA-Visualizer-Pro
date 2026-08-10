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
    int[] getTraversalOrder();
    void setTraversalOrder(int[] values);
    int[] buildTraversalOrder();

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
