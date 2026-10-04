package com.dsavisualizer.app.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import androidx.annotation.NonNull;

import androidx.annotation.Nullable;

import com.dsavisualizer.app.ui.visualization.SortVisualizationEngine;
import com.dsavisualizer.app.ui.visualization.VisualizationTechniqueRegistry;
import com.dsavisualizer.app.ui.visualization.runtime.GraphExpandedStepEngine;
import android.graphics.DashPathEffect;
import com.dsavisualizer.app.ui.visualization.runtime.HuffmanStepEngine;
import com.dsavisualizer.app.ui.visualization.runtime.HuffmanTreeModel;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Reusable animated visualizer for array-based algorithms, linear structures, trees, and graphs.
 * The goal is to keep the app lightweight while still making the simulation feel alive.
 */
public class TechniqueVisualizerView extends View {
    private static final String TAG = "TechniqueVisualizerView";

    private static final float HORIZONTAL_PADDING = 24f;
    private static final float MIN_CELL_WIDTH = 76f;
    private static final float MAX_CELL_WIDTH = 182f;
    private static final float CELL_GAP = 18f;

    private final Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint accentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint softGlowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF tempRect = new RectF();

    private String techniqueId = "linear_search";
    private int[] values = new int[0];
    private int[] previousValues = new int[0];
    private int activeIndex = -1;
    private int secondaryIndex = -1;
    private int foundIndex = -1;
    private int completedStart = -1;
    private int completedEnd = -1;
    private int traversalStep = -1;
    private int[] traversalOrder = new int[0];
    private int[] mergeLeftValues = new int[0];
    private int[] mergeRightValues = new int[0];
    private int[] mergeOutputValues = new int[0];
    private int mergeWriteIndex = -1;
    private boolean mergeTransferFromLeft = true;
    private int mergeTransferSourceIndex = -1;
    private int mergeTransferTargetIndex = -1;
    private int mergeTransferValue = Integer.MIN_VALUE;
    private float mergeTransferProgress = 1f;
    private int radixDivisor = 1;
    private int radixActiveBucket = -1;
    private int radixProcessedCount = -1;
    private boolean radixComplete = false;
    private int radixMaxValue = 0;
    private int[] radixFinalValues = null;
    private int[] radixOriginalValues = null;
    private int[][] matrixValues = new int[0][0];
    private int matrixK = -1;
    private int matrixRow = -1;
    private int matrixCol = -1;
    private int previousMatrixRow = -1;
    private int previousMatrixCol = -1;
    private float matrixHighlightProgress = 1f;
    private String matrixLabel = "";
    private String patternText = "";
    private String patternQuery = "";
    private String expressionInput = "";
    private int patternWindowStart = -1;
    private int patternMatchIndex = -1;
    private boolean patternFound = false;
    private int[] unionParents = new int[0];
    private int unionFocusNode = -1;
    private int unionRootNode = -1;
    private int[] dijkstraDistances = new int[0];
    private boolean[] dijkstraSettled = new boolean[0];
    private int dijkstraCurrentNode = -1;
    private int[] huffmanQueueWeights = new int[0];
    private int[] huffmanSourceWeights = new int[0];
    private int huffmanMergedWeight = -1;
    private int huffmanMergedLeft = -1;
    private int huffmanMergedRight = -1;
    private int huffmanStepIndex = -1;
    private int currentStepIndex = -1;
    private int[] auxiliaryArray = new int[0];
    private int graphPreset = 1;
    private String overlayBadgeText = "";
    private String inlineTeachingMessage = "";
    private int inlineTeachingTarget = Integer.MIN_VALUE;
    private float inlineTeachingProgress = 1f;
    private boolean memoryAddressModeEnabled = false;

    private String primaryPointerLabel = "";
    private int primaryPointerIndex = -1;
    private String secondaryPointerLabel = "";
    private int secondaryPointerIndex = -1;
    private int previousPrimaryPointerIndex = -1;
    private int previousSecondaryPointerIndex = -1;
    private float pointerMotionProgress = 1f;
    private int activeEdgeFrom = -1;
    private int activeEdgeTo = -1;
    private float edgeProgress = 0f;
    private String structureOperation = "";
    private int movingStructureIndex = -1;
    private float structureMotionProgress = 1f;
    private float binaryRangeFadeProgress = 1f;

    private float transitionProgress = 1f;
    private float pulseProgress = 0f;
    private boolean swapAnimating = false;
    private int swapFromIndex = -1;
    private int swapToIndex = -1;
    private float swapProgress = 1f;
    private final java.util.List<Particle> particles = new java.util.ArrayList<>();
    private int lastBurstIndex = -1;

    // Multi-sensory: sound + haptics
    private AlgoSoundHapticEngine soundHaptic;
    private int lastFoundIndex = -1;
    private int lastMaxValue = MAX_VALUE_RANGE;
    private static final int MAX_VALUE_RANGE = 200;

    private ValueAnimator transitionAnimator;
    private ValueAnimator pulseAnimator;
    private ValueAnimator edgeAnimator;
    private ValueAnimator swapAnimator;
    private ValueAnimator pointerAnimator;
    private ValueAnimator structureAnimator;
    private ValueAnimator binaryRangeAnimator;
    private ValueAnimator mergeTransferAnimator;
    private ValueAnimator inlineTeachingAnimator;
    private ValueAnimator matrixHighlightAnimator;
    private final List<VisualizerEngine> engines = Arrays.asList(
        new SearchVisualizerEngine(),
        new SortVisualizerEngine(),
        new StructureVisualizerEngine(),
        new TraversalVisualizerEngine(),
        new BacktrackingVisualizerEngine(),
        new DpVisualizerEngine(),
        new StringPatternVisualizerEngine(),
        new AdvancedDsVisualizerEngine(),
        new SpecializedVisualizerEngine()
    );

    public TechniqueVisualizerView(Context context) {
        super(context);
        init();
    }

    public TechniqueVisualizerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(4f);
        strokePaint.setColor(Color.parseColor("#334155"));

        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(34f);

        accentPaint.setStyle(Paint.Style.STROKE);
        accentPaint.setStrokeWidth(6f);
        accentPaint.setColor(Color.parseColor("#60A5FA"));

        softGlowPaint.setStyle(Paint.Style.FILL);

        pulseAnimator = ValueAnimator.ofFloat(0f, 1f);
        pulseAnimator.setDuration(900L);
        pulseAnimator.setRepeatCount(ValueAnimator.INFINITE);
        pulseAnimator.setRepeatMode(ValueAnimator.REVERSE);
        pulseAnimator.addUpdateListener(animation -> {
            pulseProgress = (float) animation.getAnimatedValue();
            invalidate();
        });
        pulseAnimator.start();

        // Init multi-sensory engine
        soundHaptic = new AlgoSoundHapticEngine(getContext());
    }

    /** Toggle mute on/off. Returns the new muted state. */
    public boolean toggleMute() {
        boolean nowMuted = !soundHaptic.isMuted();
        soundHaptic.setMuted(nowMuted);
        return nowMuted;
    }

    public void setSoundMuted(boolean muted) {
        if (soundHaptic != null) soundHaptic.setMuted(muted);
    }

    public void bindTechnique(String techniqueId) {
        this.techniqueId = techniqueId;
        invalidate();
    }

    public void setExpressionInput(String expressionInput) {
        this.expressionInput = expressionInput == null ? "" : expressionInput.trim();
        invalidate();
    }

    public String getExpressionInput() {
        return expressionInput == null ? "" : expressionInput;
    }

    public void setArrayState(int[] values, int activeIndex, int secondaryIndex, int foundIndex) {
        setArrayState(values, activeIndex, secondaryIndex, foundIndex, -1, -1);
    }

    public void setArrayState(int[] values, int activeIndex, int secondaryIndex, int foundIndex, int completedStart, int completedEnd) {
        int[] safeValues = values == null ? new int[0] : values;
        if (Arrays.equals(this.values, safeValues)
            && this.activeIndex == activeIndex
            && this.secondaryIndex == secondaryIndex
            && this.foundIndex == foundIndex
            && this.completedStart == completedStart
            && this.completedEnd == completedEnd) {
            return;
        }
        detectSwap(this.values, safeValues);
        previousValues = this.values.clone();
        this.values = safeValues.clone();
        this.activeIndex = activeIndex;
        this.secondaryIndex = secondaryIndex;
        this.foundIndex = foundIndex;
        this.completedStart = completedStart;
        this.completedEnd = completedEnd;
        startTransition();

        // ── Multi-sensory feedback ──
        if (soundHaptic != null && safeValues.length > 0) {
            // Determine max value for pitch mapping
            int maxVal = safeValues[0];
            for (int v : safeValues) if (v > maxVal) maxVal = v;
            if (maxVal < 1) maxVal = MAX_VALUE_RANGE;
            lastMaxValue = maxVal;

            // Compare: two elements highlighted simultaneously
            if (activeIndex >= 0 && secondaryIndex >= 0
                    && activeIndex < safeValues.length
                    && secondaryIndex < safeValues.length
                    && !swapAnimating) {
                soundHaptic.onCompare(safeValues[activeIndex], maxVal);
            }

            // Found: foundIndex newly set
            if (foundIndex >= 0 && foundIndex != lastFoundIndex
                    && foundIndex < safeValues.length) {
                soundHaptic.onFound(safeValues[foundIndex], maxVal);
                lastFoundIndex = foundIndex;
            } else if (foundIndex < 0) {
                lastFoundIndex = -1;
            }

            // Complete: whole array marked sorted
            if (completedStart == 0 && completedEnd == safeValues.length - 1
                    && safeValues.length > 1) {
                soundHaptic.onComplete();
            }
        }
    }

    public void setTraversalState(int[] values, int[] traversalOrder, int traversalStep) {
        int[] safeValues = values == null ? new int[0] : values;
        int[] safeOrder = traversalOrder == null ? new int[0] : traversalOrder;
        if (Arrays.equals(this.values, safeValues)
            && Arrays.equals(this.traversalOrder, safeOrder)
            && this.traversalStep == traversalStep) {
            return;
        }
        previousValues = this.values.clone();
        this.values = safeValues.clone();
        this.traversalOrder = safeOrder.clone();
        this.traversalStep = traversalStep;
        startTransition();
    }

    public void setPointerState(String primaryLabel, int primaryIndex, String secondaryLabel, int secondaryIndex) {
        boolean changed = this.primaryPointerIndex != primaryIndex || this.secondaryPointerIndex != secondaryIndex;
        previousPrimaryPointerIndex = this.primaryPointerIndex;
        previousSecondaryPointerIndex = this.secondaryPointerIndex;
        this.primaryPointerLabel = primaryLabel == null ? "" : primaryLabel;
        this.primaryPointerIndex = primaryIndex;
        this.secondaryPointerLabel = secondaryLabel == null ? "" : secondaryLabel;
        this.secondaryPointerIndex = secondaryIndex;
        if (changed) {
            startPointerAnimation();
            if ("binary_search".equals(techniqueId)
                && "low".equalsIgnoreCase(this.primaryPointerLabel)
                && "high".equalsIgnoreCase(this.secondaryPointerLabel)) {
                startBinaryRangeFade();
            }
        } else {
            pointerMotionProgress = 1f;
        }
        invalidate();
    }

    public void clearPointerState() {
        setPointerState("", -1, "", -1);
    }

    public void setActiveNodes(int active, int secondary) {
        this.activeIndex = active;
        this.secondaryIndex = secondary;
        invalidate();
    }

    public String getPrimaryPointerLabel() {
        return primaryPointerLabel;
    }

    public int getPrimaryPointerIndex() {
        return primaryPointerIndex;
    }

    public String getSecondaryPointerLabel() {
        return secondaryPointerLabel;
    }

    public int getSecondaryPointerIndex() {
        return secondaryPointerIndex;
    }

    public int getActiveEdgeFrom() {
        return activeEdgeFrom;
    }

    public int getActiveEdgeTo() {
        return activeEdgeTo;
    }

    public String getStructureOperation() {
        return structureOperation;
    }

    public int getMovingStructureIndex() {
        return movingStructureIndex;
    }

    public int[] getArrayValues() {
        return values == null ? new int[0] : values.clone();
    }

    public int getHighlightIndex() {
        return activeIndex;
    }

    public int getSecondaryHighlightIndex() {
        return secondaryIndex;
    }

    public int getFoundIndex() {
        return foundIndex;
    }

    public float getTransitionProgress() {
        return transitionProgress;
    }

    public float getPulseProgress() {
        return pulseProgress;
    }

    public int getStepIndex() {
        return currentStepIndex;
    }

    public void setStepIndex(int stepIndex) {
        this.currentStepIndex = stepIndex;
        invalidate();
    }

    public int[][] getMatrixValues() {
        if (matrixValues == null) {
            return new int[0][0];
        }
        int[][] copy = new int[matrixValues.length][];
        for (int i = 0; i < matrixValues.length; i++) {
            copy[i] = matrixValues[i] == null ? new int[0] : matrixValues[i].clone();
        }
        return copy;
    }

    public int getMatrixK() {
        return matrixK;
    }

    public int getMatrixRow() {
        return matrixRow;
    }

    public int getMatrixCol() {
        return matrixCol;
    }

    public String resolveMatrixLabel() {
        return matrixLabel == null || matrixLabel.trim().isEmpty() ? "Matrix" : matrixLabel;
    }

    public int getPatternWindowStart() {
        return patternWindowStart;
    }

    public int getPatternMatchIndex() {
        return patternMatchIndex;
    }

    public boolean isPatternFound() {
        return patternFound;
    }

    public String getPatternText() {
        return patternText == null ? "" : patternText;
    }

    public String getPatternQuery() {
        return patternQuery == null ? "" : patternQuery;
    }

    public int[] getAuxiliaryArray() {
        return auxiliaryArray == null ? new int[0] : auxiliaryArray.clone();
    }

    public void setOverlayBadgeText(String badgeText) {
        String safe = badgeText == null ? "" : badgeText;
        if (safe.equals(overlayBadgeText)) {
            return;
        }
        overlayBadgeText = safe;
        invalidate();
    }

    public void setMemoryAddressMode(boolean enabled) {
        if (memoryAddressModeEnabled == enabled) {
            return;
        }
        memoryAddressModeEnabled = enabled;
        invalidate();
    }

    public void setInlineTeaching(String message, int target) {
        String safeMessage = message == null ? "" : message;
        boolean changed = !safeMessage.equals(inlineTeachingMessage) || inlineTeachingTarget != target;
        inlineTeachingMessage = safeMessage;
        inlineTeachingTarget = target;
        if (!changed) {
            return;
        }
        if (inlineTeachingAnimator != null) {
            inlineTeachingAnimator.cancel();
        }
        inlineTeachingAnimator = ValueAnimator.ofFloat(0f, 1f);
        inlineTeachingAnimator.setDuration(220L);
        inlineTeachingAnimator.setInterpolator(new DecelerateInterpolator());
        inlineTeachingAnimator.addUpdateListener(animation -> {
            inlineTeachingProgress = (float) animation.getAnimatedValue();
            invalidate();
        });
        inlineTeachingAnimator.start();
    }

    public void setMergeState(int[] leftValues, int[] rightValues, int[] outputValues, int writeIndex) {
        mergeLeftValues = leftValues == null ? new int[0] : leftValues.clone();
        mergeRightValues = rightValues == null ? new int[0] : rightValues.clone();
        mergeOutputValues = outputValues == null ? new int[0] : outputValues.clone();
        mergeWriteIndex = writeIndex;
        invalidate();
    }

    public void setMergeTransfer(boolean fromLeft, int sourceIndex, int targetIndex, int value) {
        mergeTransferFromLeft = fromLeft;
        mergeTransferSourceIndex = sourceIndex;
        mergeTransferTargetIndex = targetIndex;
        mergeTransferValue = value;
        startMergeTransferAnimation();
    }

    public void setRadixState(int divisor, int activeBucket) {
        setRadixState(divisor, activeBucket, -1);
    }

    public void setRadixState(int divisor, int activeBucket, int processedCount) {
        radixDivisor = Math.max(1, divisor);
        radixActiveBucket = activeBucket;
        radixProcessedCount = processedCount;
        radixComplete = false;
        requestLayout();
        invalidate();
    }

    public void setRadixComplete(boolean complete, int[] finalValues) {
        radixComplete = complete;
        if (finalValues != null) {
            radixFinalValues = finalValues.clone();
        }
        invalidate();
    }

    public void clearRadixComplete() {
        radixComplete = false;
        radixFinalValues = null;
        radixMaxValue = 0;
        invalidate();
    }

    public void setRadixMaxValue(int value) {
        radixMaxValue = value;
        invalidate();
    }

    public void setRadixOriginalValues(int[] original) {
        if (original != null) {
            radixOriginalValues = original.clone();
            if (radixMaxValue <= 0) {
                radixMaxValue = SortVisualizationEngine.resolveMaxAbsValue(original);
            }
        }
    }

    public void clearCustomSortState() {
        mergeLeftValues = new int[0];
        mergeRightValues = new int[0];
        mergeOutputValues = new int[0];
        mergeWriteIndex = -1;
        radixDivisor = 1;
        radixActiveBucket = -1;
        radixProcessedCount = -1;
        radixComplete = false;
        radixFinalValues = null;
        radixMaxValue = 0;
        invalidate();
    }

    public void setMatrixState(int[][] values, int kIndex, int rowIndex, int colIndex, String label) {
        String safeLabel = label == null ? "" : label;
        if (matricesEqual(matrixValues, values)
            && matrixK == kIndex
            && matrixRow == rowIndex
            && matrixCol == colIndex
            && safeLabel.equals(matrixLabel)) {
            return;
        }
        boolean focusChanged = matrixRow != rowIndex || matrixCol != colIndex;
        previousMatrixRow = matrixRow;
        previousMatrixCol = matrixCol;
        if (values == null) {
            matrixValues = new int[0][0];
        } else {
            matrixValues = new int[values.length][];
            for (int i = 0; i < values.length; i++) {
                matrixValues[i] = values[i].clone();
            }
        }
        matrixK = kIndex;
        matrixRow = rowIndex;
        matrixCol = colIndex;
        matrixLabel = safeLabel;
        if (focusChanged && rowIndex >= 0 && colIndex >= 0) {
            startMatrixHighlightAnimation();
        } else {
            invalidate();
        }
    }

    public void clearMatrixState() {
        matrixValues = new int[0][0];
        matrixK = -1;
        matrixRow = -1;
        matrixCol = -1;
        previousMatrixRow = -1;
        previousMatrixCol = -1;
        matrixHighlightProgress = 1f;
        matrixLabel = "";
        if (matrixHighlightAnimator != null) {
            matrixHighlightAnimator.cancel();
        }
        invalidate();
    }

    public void setPatternState(String text, String query, int windowStart, int matchIndex, boolean found) {
        patternText = text == null ? "" : text;
        patternQuery = query == null ? "" : query;
        patternWindowStart = windowStart;
        patternMatchIndex = matchIndex;
        patternFound = found;
        invalidate();
    }

    public void clearPatternState() {
        setPatternState("", "", -1, -1, false);
    }

    public void setUnionFindState(int[] parents, int focusNode, int rootNode) {
        int[] safeParents = parents == null ? new int[0] : parents;
        if (Arrays.equals(unionParents, safeParents)
            && unionFocusNode == focusNode
            && unionRootNode == rootNode) {
            return;
        }
        unionParents = safeParents.clone();
        unionFocusNode = focusNode;
        unionRootNode = rootNode;
        invalidate();
    }

    public void setDijkstraState(int[] distances, boolean[] settled, int currentNode) {
        int[] safeDistances = distances == null ? new int[0] : distances;
        boolean[] safeSettled = settled == null ? new boolean[0] : settled;
        if (Arrays.equals(dijkstraDistances, safeDistances)
            && Arrays.equals(dijkstraSettled, safeSettled)
            && dijkstraCurrentNode == currentNode) {
            return;
        }
        dijkstraDistances = safeDistances.clone();
        dijkstraSettled = safeSettled.clone();
        dijkstraCurrentNode = currentNode;
        invalidate();
    }

    public void setHuffmanState(int[] queueWeights, int mergedWeight) {
        int[] safeWeights = queueWeights == null ? new int[0] : queueWeights;
        if (Arrays.equals(huffmanQueueWeights, safeWeights) && huffmanMergedWeight == mergedWeight) {
            return;
        }
        huffmanQueueWeights = safeWeights.clone();
        huffmanSourceWeights = safeWeights.clone();
        huffmanMergedWeight = mergedWeight;
        huffmanMergedLeft = -1;
        huffmanMergedRight = -1;
        huffmanStepIndex = -1;
        invalidate();
    }

    public void setHuffmanState(int[] sourceWeights, int[] queueWeights, int mergedWeight, int leftWeight, int rightWeight, int stepIndex) {
        int[] safeSource = sourceWeights == null ? new int[0] : sourceWeights;
        int[] safeQueue = queueWeights == null ? new int[0] : queueWeights;
        if (Arrays.equals(huffmanSourceWeights, safeSource)
            && Arrays.equals(huffmanQueueWeights, safeQueue)
            && huffmanMergedWeight == mergedWeight
            && huffmanMergedLeft == leftWeight
            && huffmanMergedRight == rightWeight
            && huffmanStepIndex == stepIndex) {
            return;
        }
        huffmanSourceWeights = safeSource.clone();
        huffmanQueueWeights = safeQueue.clone();
        huffmanMergedWeight = mergedWeight;
        huffmanMergedLeft = leftWeight;
        huffmanMergedRight = rightWeight;
        huffmanStepIndex = stepIndex;
        invalidate();
    }

    public void setGraphPreset(int preset) {
        if (graphPreset == preset) {
            return;
        }
        graphPreset = preset;
        invalidate();
    }

    public void setAuxiliaryArray(int[] aux) {
        int[] safeAux = aux == null ? new int[0] : aux;
        if (Arrays.equals(auxiliaryArray, safeAux)) {
            return;
        }
        auxiliaryArray = safeAux.clone();
        invalidate();
    }

    public void setActiveEdge(int from, int to) {
        this.activeEdgeFrom = from;
        this.activeEdgeTo = to;
        startEdgeAnimation();
    }

    public void clearActiveEdge() {
        this.activeEdgeFrom = -1;
        this.activeEdgeTo = -1;
        this.edgeProgress = 0f;
        invalidate();
    }

    public void setStructureMotion(String operation, int movingIndex) {
        this.structureOperation = operation == null ? "" : operation;
        this.movingStructureIndex = movingIndex;
        if (this.structureOperation.isEmpty() || this.movingStructureIndex < 0) {
            structureMotionProgress = 1f;
        } else {
            startStructureMotionAnimation();
        }
        invalidate();
    }

    public int getFocusScrollX(int index) {
        if (values.length == 0) {
            return 0;
        }
        int safeIndex = Math.max(0, Math.min(index, values.length - 1));
        float cellWidth = resolveHorizontalCellWidth();
        float baseWidth = cellWidth - CELL_GAP;
        float center = HORIZONTAL_PADDING + (safeIndex * cellWidth) + (baseWidth / 2f);
        return Math.round(center - (getWidth() / 2f));
    }

    public int getPatternFocusScrollX(int index) {
        int safeIndex = Math.max(0, index);
        float cellWidth = 56f;
        float center = HORIZONTAL_PADDING + (safeIndex * cellWidth) + (cellWidth / 2f);
        return Math.round(center - (getWidth() / 2f));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        VisualizerEngine engine = resolveEngine();
        int requestedWidth = engine == null ? resolveScreenWidthDesiredWidth() : engine.resolveDesiredWidth(this, techniqueId);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int measuredWidth = resolveSize(requestedWidth, widthMeasureSpec);

        ViewGroup.LayoutParams lp = getLayoutParams();
        if (lp != null && lp.width > 0) {
            measuredWidth = lp.width;
        } else if (isLandscapeCanvas()) {
            int parentWidth = 0;
            if (getParent() instanceof View) {
                parentWidth = ((View) getParent()).getWidth();
            }
            if (parentWidth > 0) {
                measuredWidth = parentWidth;
            } else if (widthMode != MeasureSpec.UNSPECIFIED && widthSize > 0) {
                measuredWidth = widthSize;
            }
        }

        int requestedHeight;
        if ("radix_sort".equals(techniqueId)) {
            requestedHeight = resolveRadixDesiredHeight();
        } else {
            requestedHeight = engine == null ? resolveStandardDesiredHeight() : engine.resolveDesiredHeight(this, techniqueId);
        }

        if (lp != null && lp.height > 0) {
            requestedHeight = lp.height;
        }

        int measuredHeight = resolveSize(requestedHeight, heightMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            int saveCount = canvas.save();
            canvas.clipRect(
                getPaddingLeft(),
                getPaddingTop(),
                getWidth() - getPaddingRight(),
                getHeight() - getPaddingBottom()
            );
            VisualizerEngine engine = resolveEngine();
            if (engine != null) {
                engine.draw(canvas, this, techniqueId);
            } else {
                drawHorizontalBoxes(canvas, false);
            }
            drawOverlayBadge(canvas);
            drawParticles(canvas);
            canvas.restoreToCount(saveCount);
        } catch (Throwable drawError) {
            Log.e(TAG, "Visualizer draw failed for technique " + techniqueId, drawError);
            drawVisualizerErrorState(canvas);
        }
    }

    private void drawVisualizerErrorState(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#020617"));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(28f);
        textPaint.setColor(Color.parseColor("#E2E8F0"));
        canvas.drawText("Visualization temporarily unavailable", getWidth() / 2f, getHeight() * 0.42f, textPaint);
        textPaint.setTextSize(18f);
        textPaint.setColor(Color.parseColor("#94A3B8"));
        canvas.drawText("Please restart this topic and try again.", getWidth() / 2f, getHeight() * 0.54f, textPaint);
    }

    void drawHorizontalBoxes(Canvas canvas, boolean linked) {
        if (values.length == 0) {
            if (isStructureTechnique()) {
                drawEmptyStructureState(canvas, linked ? "linked" : techniqueId);
            }
            return;
        }

        float padding = isStructureTechnique() ? resolveStructureHorizontalPadding() : 24f;
        float cellWidth = isStructureTechnique()
            ? resolveHorizontalCellWidth(resolveStructureAvailableWidth(padding))
            : resolveHorizontalCellWidth();
        float baseWidth = cellWidth - CELL_GAP;
        float top = isStructureTechnique() ? resolveStructureTop() : getHeight() * 0.34f;
        float bottom = top + (isStructureTechnique() ? resolveStructureBoxHeight() : resolveBoxHeight());
        float textSize = isStructureTechnique() ? resolveStructureValueTextSize() : resolveValueTextSize();

        drawBinarySearchRange(canvas, padding, cellWidth, baseWidth, top, bottom);
        drawJumpSearchRange(canvas, padding, cellWidth, baseWidth, top, bottom);

        for (int i = 0; i < values.length; i++) {
            if (swapAnimating && (i == swapFromIndex || i == swapToIndex)) {
                continue;
            }

            float left = padding + (i * cellWidth)
                + resolveStructureTranslationX(i)
                + resolveInsertionShiftX(i, cellWidth)
                + resolveLinkedDeletionCollapseX(i, cellWidth);
            if (isStructureTechnique() && isLandscapeCanvas()) {
                left = clamp(left, padding, Math.max(padding, getWidth() - padding - baseWidth));
            }
            float right = left + baseWidth;
            float lift = resolveLift(i);
            float translateY = resolveStructureTranslationY(i, false);
            float animatedTop = top - lift + translateY;
            float animatedBottom = bottom - lift + translateY;
            int alpha = Math.round(255f * resolveCellAlpha(i));
            int saveCount = canvas.saveLayerAlpha(left - 18f, animatedTop - 18f, right + 18f, animatedBottom + 18f, alpha);

            applyBoxShader(left, animatedTop, right, animatedBottom, i);
            canvas.drawRoundRect(left, animatedTop, right, animatedBottom, 24f, 24f, fillPaint);

            // Neon gradient stroke configuration for the box border
            int strokeColorA = Color.parseColor("#38BDF8"); // Cyan neon
            int strokeColorB = Color.parseColor("#C084FC"); // Purple neon
            if (i == activeIndex) {
                strokeColorA = Color.parseColor("#F59E0B"); // Amber neon
                strokeColorB = Color.parseColor("#EF4444"); // Red neon
            } else if (i == secondaryIndex) {
                strokeColorA = Color.parseColor("#C084FC"); // Purple neon
                strokeColorB = Color.parseColor("#A855F7"); // Violet neon
            } else if (i == foundIndex || isCompletedIndex(i)) {
                strokeColorA = Color.parseColor("#34D399"); // Emerald neon
                strokeColorB = Color.parseColor("#10B981"); // Green neon
            }

            Paint boxStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            boxStrokePaint.setStyle(Paint.Style.STROKE);
            boxStrokePaint.setStrokeWidth(5f);
            boxStrokePaint.setShader(new LinearGradient(left, animatedTop, right, animatedBottom, strokeColorA, strokeColorB, Shader.TileMode.CLAMP));
            canvas.drawRoundRect(left, animatedTop, right, animatedBottom, 24f, 24f, boxStrokePaint);

            drawGlow(canvas, left, animatedTop, right, animatedBottom, i);



            textPaint.setColor(Color.WHITE);
            textPaint.setFakeBoldText(true);
            textPaint.setTextSize(textSize);
            float boxHeight = animatedBottom - animatedTop;
            canvas.drawText(String.valueOf(resolveAnimatedValue(i)), (left + right) / 2f, animatedTop + (boxHeight * 0.58f), textPaint);
            drawStructureCardLabel(canvas, left, animatedTop, right, animatedBottom, i, false);
            drawStructureArrivalEffect(canvas, (left + right) / 2f, (animatedTop + animatedBottom) / 2f, Math.min(baseWidth, boxHeight) * 0.42f, i);
            canvas.restoreToCount(saveCount);

            if (linked && i < values.length - 1) {
                float lineY = (animatedTop + animatedBottom) / 2f;
                drawAnimatedConnector(canvas, right + 8f, lineY, right + 32f, lineY, linked && isLinkedRelinkConnector(i));
            }
        }

        drawComparisonBeam(canvas, padding, cellWidth, baseWidth, top, bottom);
        drawPartitionSweep(canvas, padding, cellWidth, baseWidth, top, bottom);
        drawCompletedPassSweep(canvas, padding, cellWidth, baseWidth, top, bottom);
        drawSwapBoxes(canvas, padding, cellWidth, baseWidth, top, bottom);
        drawArrayPointers(canvas, padding, cellWidth, baseWidth, top);
        drawInlineArrayMath(canvas, padding, cellWidth, baseWidth, top, bottom);
        drawDiscardedFlashTrails(canvas, padding, cellWidth, baseWidth, top, bottom);
        drawArrayIndices(canvas, padding, cellWidth, baseWidth, bottom);
        if (linked || isLinkedTechnique()) {
            drawLinkedMemoryView(canvas, padding, cellWidth, baseWidth, bottom);
        }
    }

    void drawVerticalStack(Canvas canvas) {
        drawVerticalStack(canvas, false);
    }

    void drawVerticalStack(Canvas canvas, boolean linked) {
        if (values.length == 0) {
            drawEmptyStructureState(canvas, "stack");
            return;
        }

        float stackWidth = resolveStackVisualWidth();
        float left = (getWidth() - stackWidth) / 2f;
        float right = left + stackWidth;
        float cellHeight = resolveStackCellHeight();
        float gap = resolveStackGap();
        float startBottom = getHeight() - resolveStackBottomInset();

        for (int i = 0; i < values.length; i++) {
            int visualIndex = i;
            float bottom = startBottom - (i * (cellHeight + gap));
            float translateY = resolveStructureTranslationY(visualIndex, true);
            float top = bottom - cellHeight - resolveLift(visualIndex) + translateY;
            float animatedBottom = bottom - resolveLift(visualIndex) + translateY;
            int alpha = Math.round(255f * resolveCellAlpha(visualIndex));
            int saveCount = canvas.saveLayerAlpha(left - 18f, top - 18f, right + 18f, animatedBottom + 18f, alpha);

            applyBoxShader(left, top, right, animatedBottom, visualIndex);
            canvas.drawRoundRect(left, top, right, animatedBottom, 20f, 20f, fillPaint);
            canvas.drawRoundRect(left, top, right, animatedBottom, 20f, 20f, strokePaint);
            drawGlow(canvas, left, top, right, animatedBottom, visualIndex);
            canvas.drawText(String.valueOf(resolveAnimatedValue(visualIndex)), (left + right) / 2f, top + 56f, textPaint);
            drawStructureCardLabel(canvas, left, top, right, animatedBottom, visualIndex, true);
            drawStructureArrivalEffect(canvas, (left + right) / 2f, (top + animatedBottom) / 2f, Math.min(right - left, animatedBottom - top) * 0.40f, visualIndex);
            canvas.restoreToCount(saveCount);

            if (i > 0) {
                drawAnimatedConnector(
                    canvas,
                    (left + right) / 2f,
                    animatedBottom + 8f,
                    (left + right) / 2f,
                    animatedBottom + 24f,
                    linked
                );
            }

            if (linked) {
                drawLinkedStackArrow(canvas, right + 18f, top + (cellHeight * 0.5f), i == values.length - 1);
            }
        }

        if (primaryPointerIndex >= 0 && primaryPointerIndex < values.length) {
            float pointerCenterY = startBottom - (primaryPointerIndex * (cellHeight + gap)) - cellHeight - 22f;
            float pointerX = clamp(right + 40f, 44f, Math.max(44f, getWidth() - 44f));
            drawPointer(canvas, pointerX, pointerCenterY, primaryPointerLabel.isEmpty() ? "top" : primaryPointerLabel);
        }

        drawStackIndices(canvas, left, startBottom, cellHeight, gap);
    }

    void drawExpressionStackScene(Canvas canvas) {
        boolean postfix = "postfix_evaluation".equals(techniqueId);
        boolean prefix = "infix_to_prefix".equals(techniqueId);
        String expression = resolveExpressionInput(postfix, prefix);
        String sceneTitle = postfix ? "Postfix Expression" : (prefix ? "Reversed Infix Expression" : "Infix Expression");
        String stackTitle = postfix ? "Operand Stack" : "Operator Stack";
        String resultTitle = postfix ? "Value" : (prefix ? "Prefix Result" : "Postfix Result");
        int tokenIndex = Math.max(0, activeIndex);

        ExpressionSceneState state = postfix
            ? buildPostfixSceneState(tokenIndex)
            : buildConversionSceneState(expression, tokenIndex, prefix);

        float width = getWidth();
        float height = getHeight();
        float margin = Math.max(20f, width * 0.045f);
        float cardTop = Math.max(20f, height * 0.04f);
        float cardBottom = height - Math.max(24f, height * 0.06f);
        float cardLeft = margin;
        float cardRight = width - margin;

        Paint cardFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        cardFill.setStyle(Paint.Style.FILL);
        cardFill.setColor(Color.parseColor("#0B1020"));
        cardFill.setAlpha(238);
        Paint cardStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        cardStroke.setStyle(Paint.Style.STROKE);
        cardStroke.setStrokeWidth(2.4f);
        cardStroke.setColor(Color.parseColor("#1F2937"));
        canvas.drawRoundRect(cardLeft, cardTop, cardRight, cardBottom, 28f, 28f, cardFill);
        canvas.drawRoundRect(cardLeft, cardTop, cardRight, cardBottom, 28f, 28f, cardStroke);

        Paint labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        labelPaint.setTextAlign(Paint.Align.CENTER);
        labelPaint.setColor(Color.parseColor("#F8FAFC"));
        labelPaint.setFakeBoldText(true);
        labelPaint.setTextSize(resolveResponsiveTextSize(24f, 30f));
        canvas.drawText(sceneTitle, width / 2f, cardTop + 56f, labelPaint);

        drawExpressionLine(canvas, expression, tokenIndex, width / 2f, cardTop + 118f, postfix);

        float midY = cardTop + ((cardBottom - cardTop) * 0.56f);
        float leftCenter = cardLeft + ((cardRight - cardLeft) * 0.31f);
        float rightCenter = cardLeft + ((cardRight - cardLeft) * 0.70f);

        labelPaint.setTextSize(resolveResponsiveTextSize(22f, 28f));
        canvas.drawText(stackTitle, leftCenter, midY - 156f, labelPaint);
        canvas.drawText(resultTitle, rightCenter, midY - 156f, labelPaint);

        drawExpressionStackBox(canvas, state.stack, leftCenter, midY + 52f);

        Paint resultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        resultPaint.setTextAlign(Paint.Align.CENTER);
        resultPaint.setFakeBoldText(true);
        resultPaint.setColor(Color.parseColor("#2DD4BF"));
        resultPaint.setTextSize(resolveResponsiveTextSize(30f, 42f));
        String result = state.result.isEmpty() ? "[ empty ]" : state.result;
        if (postfix && tokenIndex >= 5 && !state.result.isEmpty()) {
            result = "VALUE = " + state.result;
        }
        canvas.drawText(result, rightCenter, midY - 60f, resultPaint);

        if (!state.actionLine.isEmpty()) {
            drawExpressionActionLine(canvas, state.actionLine, rightCenter, midY + 2f);
        }

        Paint hintPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        hintPaint.setTextAlign(Paint.Align.CENTER);
        hintPaint.setColor(Color.parseColor("#CBD5E1"));
        hintPaint.setTextSize(resolveResponsiveTextSize(18f, 22f));
        canvas.drawText(state.note, width / 2f, cardBottom - 48f, hintPaint);
    }

    private void drawExpressionActionLine(Canvas canvas, String actionLine, float centerX, float baseline) {
        Paint actionFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        actionFill.setStyle(Paint.Style.FILL);
        actionFill.setColor(Color.parseColor("#172033"));
        actionFill.setAlpha(235);
        Paint actionStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        actionStroke.setStyle(Paint.Style.STROKE);
        actionStroke.setStrokeWidth(2.2f);
        actionStroke.setColor(Color.parseColor("#FACC15"));
        actionStroke.setAlpha(190);
        Paint actionText = new Paint(Paint.ANTI_ALIAS_FLAG);
        actionText.setTextAlign(Paint.Align.CENTER);
        actionText.setFakeBoldText(true);
        actionText.setColor(Color.parseColor("#FDE68A"));
        float pulse = "pop".equals(structureOperation) ? (1f - structureMotionProgress) : pulseProgress;
        actionText.setTextSize(resolveResponsiveTextSize(20f, 25f) + (pulse * 4f));
        float width = Math.min(getWidth() * 0.42f, Math.max(128f, actionText.measureText(actionLine) + 32f));
        float height = 46f + (pulse * 6f);
        tempRect.set(centerX - (width / 2f), baseline - height + 12f, centerX + (width / 2f), baseline + 12f);
        canvas.drawRoundRect(tempRect, 12f, 12f, actionFill);
        canvas.drawRoundRect(tempRect, 12f, 12f, actionStroke);
        canvas.drawText(actionLine, centerX, baseline - 2f, actionText);
    }

    private void drawExpressionLine(Canvas canvas, String expression, int tokenIndex, float centerX, float baseline, boolean tokenized) {
        String display = tokenized ? expression : expression;
        Paint expressionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        expressionPaint.setTextAlign(Paint.Align.LEFT);
        expressionPaint.setColor(Color.parseColor("#CBD5E1"));
        expressionPaint.setTextSize(resolveResponsiveTextSize(32f, 44f));
        expressionPaint.setFakeBoldText(false);

        float totalWidth = expressionPaint.measureText(display);
        float startX = centerX - (totalWidth / 2f);
        int highlightCharIndex = tokenized ? postfixTokenCharIndex(expression, tokenIndex) : tokenIndex;
        if (highlightCharIndex >= 0 && highlightCharIndex < display.length()) {
            float highlightLeft = startX + expressionPaint.measureText(display, 0, highlightCharIndex) - 5f;
            float highlightRight = startX + expressionPaint.measureText(display, 0, highlightCharIndex + 1) + 5f;
            Paint highlight = new Paint(Paint.ANTI_ALIAS_FLAG);
            highlight.setStyle(Paint.Style.FILL);
            highlight.setColor(Color.parseColor("#FACC15"));
            highlight.setAlpha(70);
            canvas.drawRoundRect(highlightLeft, baseline - 42f, highlightRight, baseline + 10f, 8f, 8f, highlight);
        }
        canvas.drawText(display, startX, baseline, expressionPaint);
    }

    private void drawExpressionStackBox(Canvas canvas, String[] stack, float centerX, float centerY) {
        float boxWidth = Math.min(172f, Math.max(126f, getWidth() * 0.22f));
        float boxHeight = 238f;
        float left = centerX - (boxWidth / 2f);
        float top = centerY - (boxHeight / 2f);
        float right = centerX + (boxWidth / 2f);
        float bottom = centerY + (boxHeight / 2f);

        Paint shellFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        shellFill.setStyle(Paint.Style.FILL);
        shellFill.setColor(Color.parseColor("#0F1220"));
        shellFill.setAlpha(220);
        Paint shellStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        shellStroke.setStyle(Paint.Style.STROKE);
        shellStroke.setStrokeWidth(2.2f);
        shellStroke.setColor(Color.parseColor("#2A2F45"));
        canvas.drawRoundRect(left, top, right, bottom, 18f, 18f, shellFill);
        canvas.drawRoundRect(left, top, right, bottom, 18f, 18f, shellStroke);

        Paint boxStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        boxStroke.setStyle(Paint.Style.STROKE);
        boxStroke.setStrokeWidth(4f);
        boxStroke.setColor(Color.parseColor("#2DD4BF"));
        Paint boxFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        boxFill.setStyle(Paint.Style.FILL);
        boxFill.setColor(Color.parseColor("#111827"));
        Paint itemText = new Paint(Paint.ANTI_ALIAS_FLAG);
        itemText.setColor(Color.parseColor("#F8FAFC"));
        itemText.setTextAlign(Paint.Align.CENTER);
        itemText.setFakeBoldText(true);
        itemText.setTextSize(resolveResponsiveTextSize(24f, 30f));

        if (stack.length == 0) {
            Paint emptyPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            emptyPaint.setColor(Color.parseColor("#94A3B8"));
            emptyPaint.setTextAlign(Paint.Align.CENTER);
            emptyPaint.setTextSize(resolveResponsiveTextSize(20f, 24f));
            emptyPaint.setTextSkewX(-0.18f);
            canvas.drawText("[ empty ]", centerX, centerY + 8f, emptyPaint);
            return;
        }

        float itemHeight = 64f;
        float gap = 12f;
        float currentBottom = bottom - 24f;
        for (int i = 0; i < stack.length; i++) {
            int visualIndex = i;
            float itemBottom = currentBottom - (i * (itemHeight + gap));
            float itemTop = itemBottom - itemHeight;
            float itemLeft = left + 28f;
            float itemRight = right - 28f;
            canvas.drawRoundRect(itemLeft, itemTop, itemRight, itemBottom, 9f, 9f, boxFill);
            canvas.drawRoundRect(itemLeft, itemTop, itemRight, itemBottom, 9f, 9f, boxStroke);
            canvas.drawText(stack[visualIndex], centerX, itemTop + 42f, itemText);
        }
    }

    private ExpressionSceneState buildPostfixSceneState(int tokenIndex) {
        String[] tokens = tokenizePostfix(resolveExpressionInput(true, false));
        int safeIndex = Math.min(Math.max(0, tokenIndex), tokens.length - 1);
        int[] temp = new int[tokens.length];
        int top = -1;
        String note = "";
        String actionLine = "";
        for (int i = 0; i <= safeIndex; i++) {
            String token = tokens[i];
            if (")".equals(token)) {
                note = "Sentinel reached. The top of STACK is the final value.";
                break;
            }
            if (isNumericToken(token)) {
                temp[++top] = Integer.parseInt(token);
                note = "Scanning token '" + token + "'. Operand is pushed on STACK.";
            } else if (top >= 1) {
                int a = temp[top--];
                int b = temp[top--];
                int result = applyExpressionOperator(b, token.charAt(0), a);
                temp[++top] = result;
                note = "Scanning token '" + token + "'. Pop A and B, evaluate B " + token + " A, push result.";
                actionLine = b + " " + token + " " + a + " = " + result;
            }
        }
        String[] stack = new String[top + 1];
        for (int i = 0; i <= top; i++) {
            stack[i] = String.valueOf(temp[i]);
        }
        return new ExpressionSceneState(stack, top >= 0 ? String.valueOf(temp[top]) : "", note, actionLine);
    }

    private ExpressionSceneState buildConversionSceneState(String expression, int tokenIndex, boolean prefixMode) {
        int safeIndex = Math.min(Math.max(0, tokenIndex), expression.length());
        char[] stackTemp = new char[expression.length() + 1];
        int top = -1;
        StringBuilder output = new StringBuilder();
        String note = "";
        for (int i = 0; i < safeIndex && i < expression.length(); i++) {
            char token = expression.charAt(i);
            if (Character.isLetterOrDigit(token)) {
                output.append(token);
                note = "Scanning token '" + token + "'. Operand goes directly to result.";
            } else if (token == '(') {
                stackTemp[++top] = token;
                note = "Scanning token '('. Push it on the operator stack.";
            } else if (token == ')') {
                while (top >= 0 && stackTemp[top] != '(') {
                    output.append(stackTemp[top--]);
                }
                if (top >= 0 && stackTemp[top] == '(') {
                    top--;
                }
                note = "Scanning token ')'. Pop until '(' is removed.";
            } else if (isExpressionOperator(token)) {
                while (top >= 0
                    && stackTemp[top] != '('
                    && (prefixMode
                        ? expressionPrecedence(stackTemp[top]) > expressionPrecedence(token)
                        : expressionPrecedence(stackTemp[top]) >= expressionPrecedence(token))) {
                    output.append(stackTemp[top--]);
                }
                stackTemp[++top] = token;
                note = "Scanning token '" + token + "'. Compare precedence and push operator.";
            }
        }
        if (safeIndex >= expression.length()) {
            while (top >= 0) {
                if (stackTemp[top] != '(') {
                    output.append(stackTemp[top]);
                }
                top--;
            }
            note = prefixMode
                ? "Conversion complete. Reverse the temporary result to get prefix."
                : "Conversion complete. Remaining operators are popped from STACK.";
        }
        String[] stack = new String[top + 1];
        for (int i = 0; i <= top; i++) {
            stack[i] = String.valueOf(stackTemp[i]);
        }
        String result = prefixMode ? new StringBuilder(output).reverse().toString() : output.toString();
        return new ExpressionSceneState(stack, result, note, resolveConversionActionLine(stack, result, prefixMode));
    }

    private String resolveConversionActionLine(String[] stack, String result, boolean prefixMode) {
        if (result == null || result.isEmpty()) {
            return stack.length == 0 ? "" : "Stack top: " + stack[stack.length - 1];
        }
        if (stack.length == 0) {
            return prefixMode ? "Reverse result: " + result : "Output: " + result;
        }
        return "Output: " + result + "   Top: " + stack[stack.length - 1];
    }

    private int postfixTokenCharIndex(String expression, int tokenIndex) {
        if (tokenIndex < 0 || expression == null || expression.isEmpty()) {
            return -1;
        }
        String[] tokens = tokenizePostfix(expression);
        if (tokenIndex >= tokens.length) {
            return -1;
        }
        if (expression.indexOf(' ') >= 0) {
            int searchFrom = 0;
            for (int i = 0; i <= tokenIndex && i < tokens.length; i++) {
                if (")".equals(tokens[i])) {
                    return -1;
                }
                int foundAt = expression.indexOf(tokens[i], searchFrom);
                if (foundAt < 0) {
                    return -1;
                }
                if (i == tokenIndex) {
                    return foundAt;
                }
                searchFrom = foundAt + tokens[i].length();
            }
            return -1;
        }
        return tokenIndex < expression.length() ? tokenIndex : -1;
    }

    private String resolveExpressionInput(boolean postfix, boolean prefix) {
        if (expressionInput != null && !expressionInput.trim().isEmpty()) {
            return expressionInput.trim();
        }
        if (postfix) {
            return "2 3 4 * +";
        }
        return prefix ? "(e-d^c)*b+a" : "a+b*(c^d-e)";
    }

    private String[] tokenizePostfix(String expression) {
        String safe = expression == null ? "" : expression.trim();
        if (safe.isEmpty()) {
            return new String[] {"2", "3", "4", "*", "+", ")"};
        }
        if (safe.contains(" ")) {
            String[] parts = safe.split("\\s+");
            if (parts.length == 0) {
                return new String[] {"2", "3", "4", "*", "+", ")"};
            }
            if (!")".equals(parts[parts.length - 1])) {
                String[] withSentinel = new String[parts.length + 1];
                System.arraycopy(parts, 0, withSentinel, 0, parts.length);
                withSentinel[parts.length] = ")";
                return withSentinel;
            }
            return parts;
        }
        String[] tokens = new String[safe.length() + (safe.endsWith(")") ? 0 : 1)];
        for (int i = 0; i < safe.length(); i++) {
            tokens[i] = String.valueOf(safe.charAt(i));
        }
        if (!safe.endsWith(")")) {
            tokens[tokens.length - 1] = ")";
        }
        return tokens;
    }

    private boolean isExpressionOperator(char token) {
        return token == '+' || token == '-' || token == '*' || token == '/' || token == '^';
    }

    private boolean isNumericToken(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        int start = token.charAt(0) == '-' && token.length() > 1 ? 1 : 0;
        for (int i = start; i < token.length(); i++) {
            if (!Character.isDigit(token.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private int expressionPrecedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        }
        if (operator == '*' || operator == '/') {
            return 2;
        }
        if (operator == '^') {
            return 3;
        }
        return 0;
    }

    private int applyExpressionOperator(int b, char operator, int a) {
        if (operator == '+') {
            return b + a;
        }
        if (operator == '-') {
            return b - a;
        }
        if (operator == '*') {
            return b * a;
        }
        if (operator == '/' && a != 0) {
            return b / a;
        }
        if (operator == '^') {
            return (int) Math.pow(b, a);
        }
        return 0;
    }

    private float resolveResponsiveTextSize(float phoneSize, float tabletSize) {
        return getWidth() >= 760f ? tabletSize : phoneSize;
    }

    private static final class ExpressionSceneState {
        final String[] stack;
        final String result;
        final String note;
        final String actionLine;

        ExpressionSceneState(String[] stack, String result, String note, String actionLine) {
            this.stack = stack;
            this.result = result == null ? "" : result;
            this.note = note == null ? "" : note;
            this.actionLine = actionLine == null ? "" : actionLine;
        }
    }

    void drawCircularStructure(Canvas canvas, boolean headerNode) {
        if (values.length == 0) {
            drawEmptyStructureState(canvas, headerNode ? "circular_header" : "circular");
            return;
        }

        float centerX = getWidth() / 2f;
        float centerY = getHeight() * 0.50f;
        int ringCount = values.length + (headerNode ? 1 : 0);
        float shortestSide = Math.min(getWidth(), getHeight());
        float radius = shortestSide * (ringCount >= 8 ? 0.20f : 0.23f);
        radius = Math.max(92f, Math.min(radius, 190f));
        float circumference = (float) (2d * Math.PI * radius);
        float spacingLimitedRadius = Math.max(18f, Math.min(34f, (circumference / Math.max(1, ringCount)) * 0.24f));
        float nodeRadius = Math.min(34f, Math.max(20f, spacingLimitedRadius));
        float angleStep = (float) ((Math.PI * 2d) / Math.max(1, ringCount));
        float startAngle = (float) (-Math.PI / 2d);
        float[][] centers = new float[ringCount][2];

        for (int i = 0; i < ringCount; i++) {
            float angle = startAngle + (i * angleStep);
            centers[i][0] = centerX + ((float) Math.cos(angle) * radius);
            centers[i][1] = centerY + ((float) Math.sin(angle) * radius);
        }

        for (int i = 0; i < ringCount; i++) {
            int next = (i + 1) % ringCount;
            boolean relink = isCircularRelinkConnector(i, headerNode);
            drawAnimatedConnector(
                canvas,
                centers[i][0],
                centers[i][1],
                centers[next][0],
                centers[next][1],
                relink
            );
        }

        for (int i = 0; i < ringCount; i++) {
            boolean isHeader = headerNode && i == 0;
            int valueIndex = headerNode ? i - 1 : i;
            int renderIndex = isHeader ? -1 : valueIndex;
            int fillColor = isHeader ? resolveCircularHeaderColor() : resolveFill(valueIndex);
            float cx = centers[i][0];
            float cy = centers[i][1];
            applyNodeShader(cx, cy, fillColor);
            float radiusBoost = (!isHeader && (valueIndex == activeIndex || valueIndex == secondaryIndex || valueIndex == foundIndex))
                ? pulseProgress * 4f : 0f;
            canvas.drawCircle(cx, cy, nodeRadius + radiusBoost, fillPaint);
            canvas.drawCircle(cx, cy, nodeRadius + radiusBoost, strokePaint);
            if (!isHeader) {
                drawNodeHalo(canvas, cx, cy, nodeRadius, renderIndex);
            }
            textPaint.setTextSize(isHeader ? 18f : (nodeRadius <= 24f ? 20f : 24f));
            canvas.drawText(isHeader ? "H" : String.valueOf(resolveAnimatedValue(valueIndex)), cx, cy + 8f, textPaint);
        }

        if ("circular_queue".equals(techniqueId) && values.length > 0) {
            drawCircularBadge(canvas, centers[0][0], centers[0][1] - nodeRadius - 26f, "Front", "#2563EB");
            drawCircularBadge(canvas, centers[values.length - 1][0], centers[values.length - 1][1] + nodeRadius + 26f, "Rear", "#0F766E");
        }

        if (headerNode) {
            drawCircularBadge(canvas, centers[0][0], centers[0][1] - nodeRadius - 26f, "Head", "#0F766E");
        }
    }

    void drawTree(Canvas canvas) {
        if (values.length == 0) {
            return;
        }

        float[][] points = resolveTreeLayout(0.22f, 0.42f, 0.66f);

        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {1, 4}, {2, 5}, {2, 6}};
        for (int[] edge : edges) {
            Paint linePaint = new Paint(strokePaint);
            if (isTraversalEdgeActive(edge[0], edge[1])) {
                linePaint.setColor(Color.parseColor("#38BDF8"));
                linePaint.setStrokeWidth(8f);
            }
            canvas.drawLine(points[edge[0]][0], points[edge[0]][1], points[edge[1]][0], points[edge[1]][1], linePaint);
        }
        drawTraversalTravelTrail(canvas, points);

        for (int i = 0; i < Math.min(values.length, points.length); i++) {
            applyNodeShader(points[i][0], points[i][1], resolveTraversalFill(i));
            float radius = resolveNodeRadius(i, resolveTreeNodeRadius());
            canvas.drawCircle(points[i][0], points[i][1], radius, fillPaint);
            canvas.drawCircle(points[i][0], points[i][1], radius, strokePaint);
            drawNodeHalo(canvas, points[i][0], points[i][1], radius, i);
            drawNodeArrivalEffect(canvas, points[i][0], points[i][1], radius, i, false);
            textPaint.setTextSize(resolveTreeTextSize());
            canvas.drawText(String.valueOf(resolveAnimatedValue(i)), points[i][0], points[i][1] + 10f, textPaint);
        }
        drawTreeTeachingOverlays(canvas, points, Math.min(values.length, points.length), false, false);
        drawTreeMemoryOverlay(canvas, points, Math.min(values.length, points.length), false);
        drawGraphOrTreeWhyBubble(canvas, points, false);
    }

    void drawGraph(Canvas canvas) {
        int nodeCount = Math.max(1, Math.min(values.length == 0 ? 5 : values.length, 6));
        float[][] points = resolveGraphLayout(nodeCount);
        int[][] edges = resolveGraphEdges(nodeCount);
        for (int[] edge : edges) {
            drawGraphEdge(canvas, points, edge[0], edge[1]);
        }
        drawPrimMstOverlay(canvas, points);
        drawKruskalMstOverlay(canvas, points);
        drawTraversalTravelTrail(canvas, points);

        for (int i = 0; i < nodeCount; i++) {
            applyNodeShader(points[i][0], points[i][1], resolveTraversalFill(i));
            float radius = resolveNodeRadius(i, isCompactGraphLayout() ? 34f : 42f);
            canvas.drawCircle(points[i][0], points[i][1], radius, fillPaint);
            canvas.drawCircle(points[i][0], points[i][1], radius, strokePaint);
            drawNodeHalo(canvas, points[i][0], points[i][1], radius, i);
            drawNodeArrivalEffect(canvas, points[i][0], points[i][1], radius, i, true);
            textPaint.setTextSize(isCompactGraphLayout() ? 22f : 24f);
            int label = values.length > i ? values[i] : i;
            canvas.drawText(String.valueOf(label), points[i][0], points[i][1] + 8f, textPaint);
        }

        drawGraphOperationStage(canvas, points);
        drawGraphMemoryOverlay(canvas, points, nodeCount);
        drawGraphOrTreeWhyBubble(canvas, points, true);

        if ("delete_edge_graph".equals(techniqueId) && activeEdgeFrom >= 0 && activeEdgeTo >= 0
            && activeEdgeFrom < points.length && activeEdgeTo < points.length) {
            drawInlineMathBadge(
                canvas,
                (points[activeEdgeFrom][0] + points[activeEdgeTo][0]) / 2f,
                (points[activeEdgeFrom][1] + points[activeEdgeTo][1]) / 2f - 22f,
                "edge removed",
                "#DC2626"
            );
        } else if ("insert_edge_graph".equals(techniqueId) && activeEdgeFrom >= 0 && activeEdgeTo >= 0
            && activeEdgeFrom < points.length && activeEdgeTo < points.length) {
            drawInlineMathBadge(
                canvas,
                (points[activeEdgeFrom][0] + points[activeEdgeTo][0]) / 2f,
                (points[activeEdgeFrom][1] + points[activeEdgeTo][1]) / 2f - 22f,
                "edge added",
                "#16A34A"
            );
        }
    }

    void drawHeapTree(Canvas canvas) {
        if (values.length == 0) {
            return;
        }

        float[][] points = resolveTreeLayout(0.20f, 0.40f, 0.64f);

        for (int parent = 0; parent < Math.min(values.length, points.length); parent++) {
            int left = (2 * parent) + 1;
            int right = (2 * parent) + 2;
            if (left < values.length && left < points.length) {
                drawHeapEdge(canvas, points, parent, left);
            }
            if (right < values.length && right < points.length) {
                drawHeapEdge(canvas, points, parent, right);
            }
        }

        for (int i = 0; i < Math.min(values.length, points.length); i++) {
            applyNodeShader(points[i][0], points[i][1], resolveFill(i));
            float radius = resolveHeapNodeRadius() + ((i == activeIndex || i == secondaryIndex) ? pulseProgress * 6f : 0f);
            canvas.drawCircle(points[i][0], points[i][1], radius, fillPaint);
            canvas.drawCircle(points[i][0], points[i][1], radius, strokePaint);
            drawNodeHalo(canvas, points[i][0], points[i][1], radius, i);
            textPaint.setTextSize(resolveTreeTextSize());
            canvas.drawText(String.valueOf(values[i]), points[i][0], points[i][1] + 10f, textPaint);
        }

        drawTreeTeachingOverlays(canvas, points, Math.min(values.length, points.length), true, false);
        drawHeapRelationshipBadges(canvas, points);
        drawTreeMemoryOverlay(canvas, points, Math.min(values.length, points.length), true);

        drawHeapArrayStrip(canvas);
    }

    void drawMatrixScene(Canvas canvas) {
        if (matrixValues.length == 0) {
            return;
        }

        float top = 90f;
        float startX = 90f;
        float size = Math.min((getWidth() - 120f) / Math.max(1, matrixValues.length), 108f);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(28f);
        canvas.drawText(matrixLabel.isEmpty() ? "Matrix" : matrixLabel, 24f, 44f, textPaint);
        canvas.drawText(matrixK >= 0 ? "k = " + matrixK : "", 24f, 76f, textPaint);

        for (int row = 0; row < matrixValues.length; row++) {
            for (int col = 0; col < matrixValues[row].length; col++) {
                float left = startX + (col * size);
                float right = left + size - 8f;
                float cellTop = top + (row * size);
                float bottom = cellTop + size - 8f;
                int baseColor = resolveMatrixColor(row, col);
                if (row == matrixRow && col == matrixCol) {
                    baseColor = blendColors(Color.parseColor("#1E293B"), baseColor, matrixHighlightProgress);
                }
                drawMatrixCellPulse(canvas, left, cellTop, right, bottom, row, col);
                int brighter = lightenColor(baseColor, 0.18f);
                fillPaint.setShader(new LinearGradient(left, cellTop, right, bottom, brighter, baseColor, Shader.TileMode.CLAMP));
                canvas.drawRoundRect(left, cellTop, right, bottom, 16f, 16f, fillPaint);
                canvas.drawRoundRect(left, cellTop, right, bottom, 16f, 16f, strokePaint);
                textPaint.setTextAlign(Paint.Align.CENTER);
                textPaint.setTextSize(24f);
                String value = matrixValues[row][col] >= 9999 ? "INF" : String.valueOf(matrixValues[row][col]);
                canvas.drawText(value, (left + right) / 2f, cellTop + ((bottom - cellTop) * 0.60f), textPaint);
            }
        }

        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(22f);
        for (int i = 0; i < matrixValues.length; i++) {
            canvas.drawText(String.valueOf(i), startX - 34f, top + (i * size) + (size * 0.55f), textPaint);
            canvas.drawText(String.valueOf(i), startX + (i * size) + (size * 0.45f), top - 18f, textPaint);
        }
    }

    void drawPatternScene(Canvas canvas) {
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(28f);
        canvas.drawText("Text", 24f, 56f, textPaint);
        drawStringRow(canvas, patternText, 24f, 86f, true);
        canvas.drawText("Pattern", 24f, 196f, textPaint);
        drawStringRow(canvas, patternQuery, 24f + Math.max(0, patternWindowStart) * 56f, 226f, false);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    void drawUnionFindScene(Canvas canvas) {
        if (unionParents.length == 0) {
            return;
        }
        float topY = getHeight() * 0.30f;
        float bottomY = getHeight() * 0.68f;
        float gap = Math.min(120f, (getWidth() - 120f) / Math.max(1, unionParents.length - 1));
        float startX = (getWidth() - ((unionParents.length - 1) * gap)) / 2f;
        for (int i = 0; i < unionParents.length; i++) {
            float x = startX + (i * gap);
            if (unionParents[i] != i && unionParents[i] < unionParents.length) {
                float parentX = startX + (unionParents[i] * gap);
                Paint linePaint = new Paint(strokePaint);
                if (i == unionFocusNode) {
                    linePaint.setColor(Color.parseColor("#38BDF8"));
                    linePaint.setStrokeWidth(8f);
                }
                canvas.drawLine(x, bottomY, parentX, topY, linePaint);
                drawUnionCompressionTravel(canvas, x, bottomY, parentX, topY, i);
            }
        }
        for (int i = 0; i < unionParents.length; i++) {
            float x = startX + (i * gap);
            float y = unionParents[i] == i ? topY : bottomY;
            applyNodeShader(x, y, i == unionRootNode ? Color.parseColor("#22C55E") : resolveFill(i));
            canvas.drawCircle(x, y, 34f, fillPaint);
            canvas.drawCircle(x, y, 34f, strokePaint);
            canvas.drawText(String.valueOf(i), x, y + 10f, textPaint);
        }
    }

    void drawDijkstraScene(Canvas canvas) {
        float[][] points = resolveGraphLayout();
        int[][] edges = {{0, 1}, {1, 2}, {0, 3}, {1, 3}, {1, 4}, {2, 4}, {3, 4}};
        for (int[] edge : edges) {
            drawGraphEdge(canvas, points, edge[0], edge[1]);
        }

        for (int i = 0; i < points.length; i++) {
            applyNodeShader(points[i][0], points[i][1], resolveTraversalFill(i));
            float radius = isCompactGraphLayout() ? 34f : 42f;
            radius = resolveNodeRadius(i, radius);
            canvas.drawCircle(points[i][0], points[i][1], radius, fillPaint);
            canvas.drawCircle(points[i][0], points[i][1], radius, strokePaint);
            drawNodeHalo(canvas, points[i][0], points[i][1], radius, i);
            drawSettledFeedback(canvas, points[i][0], points[i][1], radius, i);
            textPaint.setTextSize(isCompactGraphLayout() ? 22f : 24f);
            canvas.drawText(String.valueOf(i), points[i][0], points[i][1] + 8f, textPaint);
        }

        drawUpdatedEdgeTrail(canvas, points);

        if (dijkstraDistances.length == 0) {
            return;
        }
        boolean compact = isCompactGraphLayout();
        float top = getHeight() * (compact ? 0.73f : 0.78f);
        float startX = compact ? 12f : 20f;
        float cellWidth = Math.min(compact ? 82f : 100f, (getWidth() - (compact ? 24f : 40f)) / Math.max(1, dijkstraDistances.length));
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(compact ? 20f : 24f);
        canvas.drawText("Distances", startX, top - 22f, textPaint);
        for (int i = 0; i < dijkstraDistances.length; i++) {
            float left = startX + (i * cellWidth);
            float right = left + cellWidth - 10f;
            int baseColor = dijkstraSettled.length > i && dijkstraSettled[i]
                ? Color.parseColor("#16A34A")
                : i == dijkstraCurrentNode ? Color.parseColor("#F59E0B") : Color.parseColor("#1E293B");
            int bright = lightenColor(baseColor, 0.18f);
            float boxHeight = compact ? 56f : 64f;
            fillPaint.setShader(new LinearGradient(left, top, right, top + boxHeight, bright, baseColor, Shader.TileMode.CLAMP));
            canvas.drawRoundRect(left, top, right, top + boxHeight, 18f, 18f, fillPaint);
            canvas.drawRoundRect(left, top, right, top + boxHeight, 18f, 18f, strokePaint);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(compact ? 18f : 20f);
            canvas.drawText(String.valueOf(i), (left + right) / 2f, top - 10f, textPaint);
            String value = dijkstraDistances[i] >= 9999 ? "INF" : String.valueOf(dijkstraDistances[i]);
            canvas.drawText(value, (left + right) / 2f, top + (compact ? 35f : 40f), textPaint);
        }
        textPaint.setTextAlign(Paint.Align.CENTER);
        drawTreeRoleBadge(canvas, getWidth() - (compact ? 56f : 72f), top - 24f, compact ? "Now" : "Current", "#D97706");
        drawInlineDijkstraMath(canvas, points, compact, top);
    }

    void drawHuffmanScene(Canvas canvas) {
        boolean compact = isCompactTreeLayout();

        // 1. Resolve deterministic source weights and build complete prefix tree model
        int[] source = (huffmanSourceWeights != null && huffmanSourceWeights.length >= 2)
            ? huffmanSourceWeights
            : ((huffmanQueueWeights != null && huffmanQueueWeights.length >= 2)
                ? huffmanQueueWeights
                : HuffmanStepEngine.getDefaultWeights(1));
        HuffmanTreeModel treeModel = HuffmanTreeModel.build(source);

        // 2. Resolve active step index for progressive tree emergence
        int currentStep = huffmanStepIndex;
        if (currentStep < 0) {
            if (huffmanMergedWeight > 0) {
                currentStep = 0;
                for (HuffmanTreeModel.Node n : treeModel.getAllNodes()) {
                    if (!n.isLeaf() && n.weight == huffmanMergedWeight) {
                        currentStep = n.stepCreated;
                        break;
                    }
                }
            } else {
                currentStep = 0;
            }
        }

        // 3. Draw Priority Queue (Min-Heap array) at top
        float startX = compact ? 16f : 24f;
        float headerY = compact ? 34f : 40f;
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(compact ? 20f : 24f);
        textPaint.setColor(Color.WHITE);
        textPaint.setFakeBoldText(true);
        canvas.drawText("Priority Queue (Min-Heap)", startX, headerY, textPaint);
        textPaint.setFakeBoldText(false);

        float queueY = compact ? 46f : 54f;
        float boxWidth = compact ? 48f : 58f;
        float boxHeight = compact ? 38f : 44f;
        float gap = compact ? 56f : 68f;

        float x = startX;
        int qLen = huffmanQueueWeights.length;
        for (int i = 0; i < qLen; i++) {
            boolean isMinTwo = (i == 0 || i == 1) && qLen > 1;
            drawMiniValueBox(canvas, huffmanQueueWeights[i], x, queueY, x + boxWidth, queueY + boxHeight, isMinTwo);
            x += gap;
        }

        if (qLen > 1) {
            drawTreeRoleBadge(canvas, startX + boxWidth, queueY - 14f, compact ? "Min 2" : "Extract Min 2", "#2563EB");
        }

        // Active merge math badge at the top right
        if (huffmanMergedWeight >= 0) {
            int leftVal = huffmanMergedLeft >= 0 ? huffmanMergedLeft : -1;
            int rightVal = huffmanMergedRight >= 0 ? huffmanMergedRight : -1;
            if (leftVal >= 0 && rightVal >= 0) {
                drawInlineMathBadge(
                    canvas,
                    getWidth() - (compact ? 96f : 124f),
                    queueY + (boxHeight / 2f),
                    leftVal + " + " + rightVal + " = " + huffmanMergedWeight,
                    "#16A34A"
                );
            }
        }

        // 4. Compute Tree Layout Geometry
        float bottomBarHeight = compact ? 38f : 46f;
        float bottomBarY = getHeight() - bottomBarHeight - (compact ? 10f : 14f);
        float treeTop = queueY + boxHeight + (compact ? 32f : 40f);
        float treeBottom = bottomBarY - (compact ? 26f : 34f);
        float treeLeft = compact ? 18f : 28f;
        float treeWidth = getWidth() - (2f * treeLeft);
        float treeHeight = Math.max(140f, treeBottom - treeTop);

        treeModel.layout(treeLeft, treeTop, treeWidth, treeHeight, compact);

        // 5. Draw Tree: Edges first, then Nodes
        drawHuffmanTreeEdges(canvas, treeModel.getRoot(), currentStep, compact);
        drawHuffmanTreeNodes(canvas, treeModel.getRoot(), currentStep, compact, treeModel);

        // 6. Draw Prefix Code Dictionary Summary Bar at Bottom
        drawHuffmanCodeDictionary(canvas, treeModel, bottomBarY, bottomBarHeight, compact);

        // Reset painter states
        strokePaint.setPathEffect(null);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setFakeBoldText(false);
    }

    private void drawHuffmanTreeEdges(Canvas canvas, HuffmanTreeModel.Node node, int currentStep, boolean compact) {
        if (node == null || node.isLeaf()) {
            return;
        }
        drawHuffmanTreeEdges(canvas, node.left, currentStep, compact);
        drawHuffmanTreeEdges(canvas, node.right, currentStep, compact);

        boolean parentFormed = node.stepCreated <= currentStep;
        boolean isCurrentMerge = node.stepCreated == currentStep && currentStep > 0;

        if (node.left != null) {
            drawSingleHuffmanEdge(canvas, node.x, node.y, node.left.x, node.left.y, "0", parentFormed, isCurrentMerge, true, compact);
        }
        if (node.right != null) {
            drawSingleHuffmanEdge(canvas, node.x, node.y, node.right.x, node.right.y, "1", parentFormed, isCurrentMerge, false, compact);
        }
    }

    private void drawSingleHuffmanEdge(Canvas canvas, float startX, float startY, float endX, float endY,
                                       String bit, boolean formed, boolean activeMerge, boolean isLeft, boolean compact) {
        Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);

        if (formed) {
            linePaint.setStrokeWidth(activeMerge ? 6f : (compact ? 3.5f : 4.5f));
            int baseColor = activeMerge
                ? Color.parseColor("#38BDF8")
                : (isLeft ? Color.parseColor("#38BDF8") : Color.parseColor("#F59E0B"));
            linePaint.setColor(baseColor);
            linePaint.setAlpha(activeMerge ? Math.round(180f + (pulseProgress * 75f)) : 220);
            canvas.drawLine(startX, startY, endX, endY, linePaint);

            if (activeMerge) {
                Paint dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                dotPaint.setColor(Color.parseColor("#FCD34D"));
                float dotX = lerp(startX, endX, pulseProgress);
                float dotY = lerp(startY, endY, pulseProgress);
                canvas.drawCircle(dotX, dotY, 6f + (pulseProgress * 2f), dotPaint);
            }
        } else {
            linePaint.setStrokeWidth(2f);
            linePaint.setColor(Color.parseColor("#475569"));
            linePaint.setAlpha(110);
            linePaint.setPathEffect(new DashPathEffect(new float[]{8f, 6f}, 0f));
            canvas.drawLine(startX, startY, endX, endY, linePaint);
        }

        // Branch bit pill ("0" or "1")
        float midX = startX + ((endX - startX) * 0.44f);
        float midY = startY + ((endY - startY) * 0.44f);
        float pillHalfW = compact ? 11f : 13f;
        float pillHalfH = compact ? 9f : 11f;

        Paint pillFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        pillFill.setStyle(Paint.Style.FILL);
        int pillBg = formed
            ? (isLeft ? Color.parseColor("#0369A1") : Color.parseColor("#B45309"))
            : Color.parseColor("#1E293B");
        pillFill.setColor(pillBg);
        pillFill.setAlpha(formed ? 230 : 130);

        Paint pillStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        pillStroke.setStyle(Paint.Style.STROKE);
        pillStroke.setStrokeWidth(1.5f);
        pillStroke.setColor(formed ? (isLeft ? Color.parseColor("#38BDF8") : Color.parseColor("#FBBF24")) : Color.parseColor("#475569"));
        pillStroke.setAlpha(formed ? 255 : 120);

        canvas.drawRoundRect(midX - pillHalfW, midY - pillHalfH, midX + pillHalfW, midY + pillHalfH, 6f, 6f, pillFill);
        canvas.drawRoundRect(midX - pillHalfW, midY - pillHalfH, midX + pillHalfW, midY + pillHalfH, 6f, 6f, pillStroke);

        Paint textP = new Paint(Paint.ANTI_ALIAS_FLAG);
        textP.setColor(formed ? Color.WHITE : Color.parseColor("#94A3B8"));
        textP.setTextAlign(Paint.Align.CENTER);
        textP.setTextSize(compact ? 13f : 15f);
        textP.setFakeBoldText(true);
        canvas.drawText(bit, midX, midY + (compact ? 4.5f : 5.5f), textP);
    }

    private void drawHuffmanTreeNodes(Canvas canvas, HuffmanTreeModel.Node node, int currentStep,
                                      boolean compact, HuffmanTreeModel model) {
        if (node == null) {
            return;
        }
        drawSingleHuffmanNode(canvas, node, currentStep, compact, model);
        drawHuffmanTreeNodes(canvas, node.left, currentStep, compact, model);
        drawHuffmanTreeNodes(canvas, node.right, currentStep, compact, model);
    }

    private void drawSingleHuffmanNode(Canvas canvas, HuffmanTreeModel.Node node, int currentStep,
                                       boolean compact, HuffmanTreeModel model) {
        float nodeRadius = compact ? 20f : 24f;
        boolean isLeaf = node.isLeaf();
        boolean isFormed = node.stepCreated <= currentStep;
        boolean isActiveMerge = !isLeaf && node.stepCreated == currentStep && currentStep > 0;
        boolean isCompletedRoot = (node == model.getRoot()) && (huffmanQueueWeights.length <= 1 || currentStep >= (model.getAllNodes().size() - model.getLeaves().size()));

        float effectiveRadius = nodeRadius + (isActiveMerge ? pulseProgress * 4f : 0f);

        if (isActiveMerge) {
            Paint haloPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            haloPaint.setStyle(Paint.Style.STROKE);
            haloPaint.setStrokeWidth(6f);
            haloPaint.setColor(Color.parseColor("#22C55E"));
            haloPaint.setAlpha(Math.round(120f + (pulseProgress * 80f)));
            canvas.drawCircle(node.x, node.y, effectiveRadius + 8f + (pulseProgress * 6f), haloPaint);
        } else if (isCompletedRoot) {
            Paint haloPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            haloPaint.setStyle(Paint.Style.STROKE);
            haloPaint.setStrokeWidth(5f);
            haloPaint.setColor(Color.parseColor("#10B981"));
            haloPaint.setAlpha(120);
            canvas.drawCircle(node.x, node.y, effectiveRadius + 7f, haloPaint);
        }

        if (isLeaf) {
            applyNodeShader(node.x, node.y, Color.parseColor("#0D9488"));
            canvas.drawCircle(node.x, node.y, effectiveRadius, fillPaint);

            strokePaint.setColor(Color.parseColor("#2DD4BF"));
            strokePaint.setStrokeWidth(2.5f);
            strokePaint.setPathEffect(null);
            canvas.drawCircle(node.x, node.y, effectiveRadius, strokePaint);

            // Character badge above leaf circle
            drawHuffmanCharBadge(canvas, node.x, node.y - effectiveRadius - (compact ? 12f : 14f), node.character, compact);

            // Frequency inside circle
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(compact ? 16f : 18f);
            textPaint.setColor(Color.WHITE);
            textPaint.setFakeBoldText(true);
            canvas.drawText(String.valueOf(node.weight), node.x, node.y + (compact ? 5f : 6f), textPaint);

            // Prefix code pill below leaf circle
            drawHuffmanCodePill(canvas, node.x, node.y + effectiveRadius + (compact ? 13f : 15f), node.code, isFormed, compact);
        } else {
            if (isFormed) {
                int baseColor = isActiveMerge
                    ? Color.parseColor("#16A34A")
                    : (isCompletedRoot ? Color.parseColor("#059669") : Color.parseColor("#1E293B"));
                applyNodeShader(node.x, node.y, baseColor);
                canvas.drawCircle(node.x, node.y, effectiveRadius, fillPaint);

                strokePaint.setColor(isActiveMerge ? Color.parseColor("#86EFAC") : (isCompletedRoot ? Color.parseColor("#34D399") : Color.parseColor("#60A5FA")));
                strokePaint.setStrokeWidth(isActiveMerge ? 3.5f : 2.5f);
                strokePaint.setPathEffect(null);
                canvas.drawCircle(node.x, node.y, effectiveRadius, strokePaint);

                if (isActiveMerge) {
                    drawTreeRoleBadge(canvas, node.x, node.y - effectiveRadius - (compact ? 12f : 14f), compact ? "Merged" : "New Node", "#16A34A");
                } else if (isCompletedRoot) {
                    drawTreeRoleBadge(canvas, node.x, node.y - effectiveRadius - (compact ? 12f : 14f), "Root", "#059669");
                }

                textPaint.setTextAlign(Paint.Align.CENTER);
                textPaint.setTextSize(compact ? 16f : 18f);
                textPaint.setColor(Color.WHITE);
                textPaint.setFakeBoldText(true);
                canvas.drawText(String.valueOf(node.weight), node.x, node.y + (compact ? 5f : 6f), textPaint);
            } else {
                Paint unformedFill = new Paint(Paint.ANTI_ALIAS_FLAG);
                unformedFill.setStyle(Paint.Style.FILL);
                unformedFill.setColor(Color.parseColor("#0F172A"));
                unformedFill.setAlpha(120);
                canvas.drawCircle(node.x, node.y, effectiveRadius, unformedFill);

                Paint unformedStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
                unformedStroke.setStyle(Paint.Style.STROKE);
                unformedStroke.setStrokeWidth(2f);
                unformedStroke.setColor(Color.parseColor("#475569"));
                unformedStroke.setPathEffect(new DashPathEffect(new float[]{6f, 5f}, 0f));
                canvas.drawCircle(node.x, node.y, effectiveRadius, unformedStroke);

                textPaint.setTextAlign(Paint.Align.CENTER);
                textPaint.setTextSize(compact ? 15f : 17f);
                textPaint.setColor(Color.parseColor("#64748B"));
                textPaint.setFakeBoldText(false);
                canvas.drawText(String.valueOf(node.weight), node.x, node.y + (compact ? 5f : 6f), textPaint);
            }
        }
    }

    private void drawHuffmanCharBadge(Canvas canvas, float centerX, float centerY, String character, boolean compact) {
        if (character == null || character.isEmpty()) {
            return;
        }
        float halfW = compact ? 12f : 14f;
        float halfH = compact ? 10f : 12f;

        Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
        fill.setStyle(Paint.Style.FILL);
        fill.setColor(Color.parseColor("#0284C7"));

        Paint stroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        stroke.setStyle(Paint.Style.STROKE);
        stroke.setStrokeWidth(1.5f);
        stroke.setColor(Color.parseColor("#38BDF8"));

        canvas.drawRoundRect(centerX - halfW, centerY - halfH, centerX + halfW, centerY + halfH, 6f, 6f, fill);
        canvas.drawRoundRect(centerX - halfW, centerY - halfH, centerX + halfW, centerY + halfH, 6f, 6f, stroke);

        Paint text = new Paint(Paint.ANTI_ALIAS_FLAG);
        text.setColor(Color.WHITE);
        text.setTextAlign(Paint.Align.CENTER);
        text.setTextSize(compact ? 13f : 15f);
        text.setFakeBoldText(true);
        canvas.drawText(character, centerX, centerY + (compact ? 4.5f : 5.5f), text);
    }

    private void drawHuffmanCodePill(Canvas canvas, float centerX, float centerY, String code, boolean isFormed, boolean compact) {
        if (code == null || code.isEmpty()) {
            return;
        }
        float textWidth = Math.max(code.length() * (compact ? 8f : 9.5f), compact ? 20f : 24f);
        float halfW = (textWidth / 2f) + (compact ? 6f : 8f);
        float halfH = compact ? 9f : 11f;

        Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
        fill.setStyle(Paint.Style.FILL);
        fill.setColor(Color.parseColor(isFormed ? "#064E3B" : "#1E293B"));
        fill.setAlpha(isFormed ? 230 : 140);

        Paint stroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        stroke.setStyle(Paint.Style.STROKE);
        stroke.setStrokeWidth(1.5f);
        stroke.setColor(Color.parseColor(isFormed ? "#34D399" : "#475569"));

        canvas.drawRoundRect(centerX - halfW, centerY - halfH, centerX + halfW, centerY + halfH, 6f, 6f, fill);
        canvas.drawRoundRect(centerX - halfW, centerY - halfH, centerX + halfW, centerY + halfH, 6f, 6f, stroke);

        Paint text = new Paint(Paint.ANTI_ALIAS_FLAG);
        text.setColor(Color.parseColor(isFormed ? "#A7F3D0" : "#94A3B8"));
        text.setTextAlign(Paint.Align.CENTER);
        text.setTextSize(compact ? 12f : 14f);
        text.setFakeBoldText(true);
        canvas.drawText(code, centerX, centerY + (compact ? 4f : 5f), text);
    }

    private void drawHuffmanCodeDictionary(Canvas canvas, HuffmanTreeModel model, float topY, float height, boolean compact) {
        List<HuffmanTreeModel.CodeEntry> codes = model.getCodesList();
        if (codes.isEmpty()) {
            return;
        }
        float left = compact ? 12f : 18f;
        float right = getWidth() - left;
        float bottomY = topY + height;

        Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.parseColor("#0F172A"));
        bgPaint.setAlpha(230);

        Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(1.5f);
        borderPaint.setColor(Color.parseColor("#334155"));

        canvas.drawRoundRect(left, topY, right, bottomY, 12f, 12f, bgPaint);
        canvas.drawRoundRect(left, topY, right, bottomY, 12f, 12f, borderPaint);

        Paint labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        labelPaint.setColor(Color.parseColor("#94A3B8"));
        labelPaint.setTextAlign(Paint.Align.LEFT);
        labelPaint.setTextSize(compact ? 14f : 16f);
        labelPaint.setFakeBoldText(true);
        float labelX = left + (compact ? 12f : 16f);
        float centerY = topY + (height / 2f);
        canvas.drawText("Codes:", labelX, centerY + (compact ? 4.5f : 5.5f), labelPaint);

        float labelWidth = compact ? 54f : 66f;
        float entriesStartX = labelX + labelWidth;
        float availableW = Math.max(10f, right - entriesStartX - (compact ? 8f : 12f));
        int count = codes.size();
        float itemGap = availableW / count;

        Paint charPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        charPaint.setColor(Color.WHITE);
        charPaint.setTextAlign(Paint.Align.LEFT);
        charPaint.setTextSize(compact ? 13f : 15f);
        charPaint.setFakeBoldText(true);

        Paint codePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        codePaint.setColor(Color.parseColor("#FCD34D"));
        codePaint.setTextAlign(Paint.Align.LEFT);
        codePaint.setTextSize(compact ? 13f : 15f);
        codePaint.setFakeBoldText(true);

        for (int i = 0; i < count; i++) {
            HuffmanTreeModel.CodeEntry entry = codes.get(i);
            float itemCenterX = entriesStartX + (i * itemGap) + (itemGap * 0.45f);

            String text = entry.character + ":" + entry.code;
            float pillW = Math.max(text.length() * (compact ? 8.5f : 10f), compact ? 36f : 44f);
            float pillHalfW = pillW / 2f;
            float pillHalfH = compact ? 10f : 12f;

            Paint pillBg = new Paint(Paint.ANTI_ALIAS_FLAG);
            pillBg.setStyle(Paint.Style.FILL);
            pillBg.setColor(Color.parseColor("#1E293B"));
            pillBg.setAlpha(200);

            Paint pillStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
            pillStroke.setStyle(Paint.Style.STROKE);
            pillStroke.setStrokeWidth(1f);
            pillStroke.setColor(Color.parseColor("#475569"));

            canvas.drawRoundRect(itemCenterX - pillHalfW, centerY - pillHalfH, itemCenterX + pillHalfW, centerY + pillHalfH, 6f, 6f, pillBg);
            canvas.drawRoundRect(itemCenterX - pillHalfW, centerY - pillHalfH, itemCenterX + pillHalfW, centerY + pillHalfH, 6f, 6f, pillStroke);

            float textStartX = itemCenterX - pillHalfW + (compact ? 5f : 7f);
            float textBaseline = centerY + (compact ? 4.5f : 5.5f);
            canvas.drawText(entry.character + ":", textStartX, textBaseline, charPaint);
            float colonOffset = charPaint.measureText(entry.character + ":") + 2f;
            canvas.drawText(entry.code, textStartX + colonOffset, textBaseline, codePaint);
        }
    }

    void drawTrieScene(Canvas canvas) {
        float rootX = getWidth() / 2f;
        float rootY = getHeight() * 0.18f;
        drawLabeledNode(canvas, rootX, rootY, "Root", activeIndex == 0);
        char[] letters = {'c', 'a', 't'};
        for (int i = 0; i < letters.length; i++) {
            float x = rootX;
            float y = rootY + ((i + 1) * 110f);
            boolean revealed = activeIndex >= i + 1;
            drawTrieConnectorReveal(canvas, x, y - 72f, x, y - 34f, i);
            drawTrieLetterReveal(canvas, x, y, String.valueOf(letters[i]), revealed, i);
        }
    }

    void drawAvlScene(Canvas canvas) {
        int[] srcValues = (values != null && values.length > 0) ? values : new int[] {30, 20, 40, 10, 25};
        int step = getStepIndex();
        boolean compact = isCompactTreeLayout();

        if (step <= 0) {
            drawAvlPreviewScene(canvas, srcValues, compact);
            return;
        }

        if (srcValues.length == 3) {
            drawAvlScene3Nodes(canvas, srcValues, step, compact);
            return;
        }

        drawAvlSceneMultiNodes(canvas, srcValues, step, compact);
    }

    private void drawAvlPreviewScene(Canvas canvas, int[] srcValues, boolean compact) {
        float cx = getWidth() / 2f;
        float topY = getHeight() * (compact ? 0.22f : 0.26f);
        int n = srcValues.length;
        int display = Math.min(n, 7);
        float boxR = compact ? 26f : 32f;
        float gap = compact ? 16f : 20f;
        float totalW = display * 2 * boxR + (display - 1) * gap;
        float startX = cx - totalW / 2f + boxR;

        Paint headerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        headerPaint.setColor(Color.parseColor("#94A3B8"));
        headerPaint.setTextAlign(Paint.Align.CENTER);
        headerPaint.setTextSize(compact ? 18f : 22f);
        canvas.drawText("Input sequence (" + n + " keys) — tap Step or Play to build AVL tree:", cx, topY - 30f, headerPaint);

        for (int i = 0; i < display; i++) {
            float x = startX + i * (2 * boxR + gap);
            float y = topY + boxR + 10f;
            drawLabeledNode(canvas, x, y, String.valueOf(srcValues[i]), i == 0);
            if (i < display - 1) {
                Paint arrowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                arrowPaint.setColor(Color.parseColor("#38BDF8"));
                arrowPaint.setTextSize(compact ? 22f : 26f);
                arrowPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("›", x + boxR + gap / 2f, y + 8f, arrowPaint);
            }
        }
        if (n > 7) {
            Paint morePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            morePaint.setColor(Color.parseColor("#94A3B8"));
            morePaint.setTextSize(16f);
            morePaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText("+" + (n - 7) + " more", cx + totalW / 2f + 16f, topY + boxR + 16f, morePaint);
        }

        Paint subPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        subPaint.setColor(Color.parseColor("#38BDF8"));
        subPaint.setTextSize(compact ? 15f : 17f);
        subPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("AVL Tree self-balances after every insertion via LL, RR, LR, RL rotations", cx, topY + boxR * 2 + 58f, subPaint);
        drawTreeRoleBadge(canvas, cx, topY + boxR * 2 + 96f, "Ready to Insert", "#0284C7");
    }

    private void drawAvlScene3Nodes(Canvas canvas, int[] srcValues, int step, boolean compact) {
        int v0 = srcValues[0];
        int v1 = srcValues[1];
        int v2 = srcValues[2];

        boolean isLL = (v0 > v1 && v1 > v2);
        boolean isRR = (v0 < v1 && v1 < v2);
        boolean isLR = (v0 > v1 && v2 > v1 && v2 < v0);
        boolean isRL = (v0 < v1 && v2 < v1 && v2 > v0);
        boolean isRotated = (step >= 2);

        float cx = getWidth() / 2f;
        float rootY = getHeight() * (compact ? 0.20f : 0.24f);
        float dx = getWidth() * (compact ? 0.20f : 0.24f);
        float dy = getHeight() * (compact ? 0.20f : 0.22f);

        if (isRotated) {
            float xL = cx - dx;
            float xR = cx + dx;
            float childY = rootY + dy;

            Paint edgePaint = new Paint(strokePaint);
            edgePaint.setColor(Color.parseColor("#64748B"));
            edgePaint.setStrokeWidth(4f);
            canvas.drawLine(cx, rootY, xL, childY, edgePaint);
            canvas.drawLine(cx, rootY, xR, childY, edgePaint);

            drawLabeledNode(canvas, cx, rootY, String.valueOf(v0), true);
            drawTreeRoleBadge(canvas, cx + 46f, rootY - 14f, "BF: 0", "#10B981");
            drawTreeRoleBadge(canvas, cx, rootY - (compact ? 44f : 52f), "Balanced Root", "#10B981");

            drawLabeledNode(canvas, xL, childY, String.valueOf(v1), false);
            drawTreeRoleBadge(canvas, xL + 42f, childY - 12f, "BF: 0", "#10B981");

            drawLabeledNode(canvas, xR, childY, String.valueOf(v2), false);
            drawTreeRoleBadge(canvas, xR + 42f, childY - 12f, "BF: 0", "#10B981");
            return;
        }

        // Before rotation: Draw insertion structure showing imbalance
        float p0x = cx;
        float p0y = rootY;
        float p1x = (isLL || isLR) ? (cx - dx) : (cx + dx);
        float p1y = rootY + dy;
        float p2x;
        float p2y = rootY + (dy * 1.85f);
        if (isLL) {
            p2x = p1x - (dx * 0.8f);
        } else if (isRR) {
            p2x = p1x + (dx * 0.8f);
        } else if (isLR) {
            p2x = p1x + (dx * 0.7f);
        } else {
            p2x = p1x - (dx * 0.7f);
        }

        Paint edge01 = new Paint(strokePaint);
        edge01.setColor(step == 1 ? Color.parseColor("#F59E0B") : Color.parseColor("#64748B"));
        edge01.setStrokeWidth(step == 1 ? 7f : 4f);
        canvas.drawLine(p0x, p0y, p1x, p1y, edge01);

        Paint edge12 = new Paint(strokePaint);
        edge12.setColor(step == 0 ? Color.parseColor("#38BDF8") : Color.parseColor("#64748B"));
        edge12.setStrokeWidth(step == 0 ? 7f : 4f);
        canvas.drawLine(p1x, p1y, p2x, p2y, edge12);

        drawLabeledNode(canvas, p0x, p0y, String.valueOf(v0), step == 1);
        String rootBf = (isLL || isLR) ? "BF: +2" : "BF: -2";
        drawTreeRoleBadge(canvas, p0x + 48f, p0y - 14f, rootBf, "#EF4444");
        if (step == 1) {
            drawTreeRoleBadge(canvas, p0x, p0y - (compact ? 44f : 52f), "Imbalance", "#EF4444");
        }

        drawLabeledNode(canvas, p1x, p1y, String.valueOf(v1), step == 0 || (step == 1 && (isLL || isRR)));
        String childBf = isLR ? "BF: -1" : (isLL ? "BF: +1" : (isRL ? "BF: +1" : "BF: -1"));
        drawTreeRoleBadge(canvas, p1x + 44f, p1y - 12f, childBf, "#F59E0B");
        if (step == 0) {
            drawTreeRoleBadge(canvas, p1x - 44f, p1y, "Parent", "#0EA5E9");
        } else if (step == 1 && (isLL || isRR)) {
            drawTreeRoleBadge(canvas, p1x - 44f, p1y, "Pivot", "#F59E0B");
        }

        drawLabeledNode(canvas, p2x, p2y, String.valueOf(v2), step == 0 || (step == 1 && (isLR || isRL)));
        drawTreeRoleBadge(canvas, p2x + 42f, p2y - 12f, "BF: 0", "#10B981");
        if (step == 0) {
            drawTreeRoleBadge(canvas, p2x, p2y + (compact ? 44f : 50f), "Inserted", "#38BDF8");
        } else if (step == 1 && (isLR || isRL)) {
            drawTreeRoleBadge(canvas, p2x, p2y + (compact ? 44f : 50f), "Pivot", "#F59E0B");
        }
    }

    private void drawAvlSceneMultiNodes(Canvas canvas, int[] srcValues, int step, boolean compact) {
        float[][] basePoints = resolveTreeLayout(0.22f, 0.46f, 0.70f);
        int nodeCount = Math.max(1, Math.min(srcValues.length, basePoints.length));
        float[][] points = new float[nodeCount][2];
        for (int i = 0; i < nodeCount; i++) {
            points[i][0] = basePoints[i][0];
            points[i][1] = basePoints[i][1];
        }

        float rotationShift = (activeIndex >= 0 && secondaryIndex >= 0)
            ? (1f - Math.abs((pulseProgress * 2f) - 1f)) * (compact ? 20f : 28f)
            : 0f;
        if ((activeIndex == 1 || secondaryIndex == 1) && nodeCount > 1) {
            points[1][0] += rotationShift;
            if (nodeCount > 3) {
                points[3][0] += rotationShift * 0.6f;
            }
            if (nodeCount > 4) {
                points[4][0] += rotationShift * 0.4f;
            }
        }
        if ((activeIndex == 0 || secondaryIndex == 0) && nodeCount > 0) {
            points[0][0] -= rotationShift * 0.45f;
        }

        for (int i = 1; i < nodeCount; i++) {
            int parent = (i - 1) / 2;
            Paint linePaint = new Paint(strokePaint);
            if ((parent == activeIndex && i == secondaryIndex) || (parent == secondaryIndex && i == activeIndex)) {
                linePaint.setColor(Color.parseColor("#38BDF8"));
                linePaint.setStrokeWidth(8f);
            }
            canvas.drawLine(points[parent][0], points[parent][1], points[i][0], points[i][1], linePaint);
        }

        for (int i = 0; i < nodeCount; i++) {
            drawLabeledNode(canvas, points[i][0], points[i][1], String.valueOf(srcValues[i]), i == activeIndex || i == secondaryIndex);
            if (i == 0 && step < 2) {
                drawTreeRoleBadge(canvas, points[i][0] + 46f, points[i][1] - 14f, "BF: +2", "#EF4444");
            } else if (i < 3) {
                drawTreeRoleBadge(canvas, points[i][0] + 42f, points[i][1] - 12f, (i == 1 && step < 2) ? "BF: +1" : "BF: 0", "#10B981");
            } else {
                drawTreeRoleBadge(canvas, points[i][0] + 38f, points[i][1] - 10f, "BF: 0", "#10B981");
            }
        }

        drawTreeTeachingOverlays(canvas, points, nodeCount, false, true);
        if (activeIndex >= 0 && activeIndex < nodeCount) {
            drawTreeRoleBadge(canvas, points[activeIndex][0], points[activeIndex][1] + (compact ? 48f : 58f), compact ? "Rebal." : "Rebalancing", "#D97706");
        }
    }

    void drawMergeScene(Canvas canvas) {
        float leftPadding = 24f;
        float topRowY = getHeight() * 0.16f;
        float bottomRowY = getHeight() * 0.60f;
        float cellHeight = 96f;
        drawMergeRow(canvas, mergeLeftValues, leftPadding, topRowY, "Left");
        float rightStart = leftPadding + (Math.max(mergeLeftValues.length, 1) * 74f) + 64f;
        drawMergeRow(canvas, mergeRightValues, rightStart, topRowY, "Right");
        drawMergeOutput(canvas, mergeOutputValues.length == 0 ? values : mergeOutputValues, leftPadding, bottomRowY, cellHeight);
        drawMergeRunBraces(canvas, leftPadding, rightStart, topRowY);
    }

    void drawRadixScene(Canvas canvas) {
        if (values == null || values.length == 0) {
            return;
        }

        float width = getWidth() > 0 ? getWidth() : resolveRadixDesiredWidth();
        float height = getHeight() > 0 ? getHeight() : resolveRadixDesiredHeight();

        int divisor = Math.max(1, radixDivisor);
        int maxVal = radixMaxValue > 0 ? radixMaxValue : SortVisualizationEngine.resolveMaxAbsValue(values);
        boolean isFinished = radixComplete || (maxVal > 0 && divisor > maxVal && radixActiveBucket == -1);

        // 1. Array Row at Top - reusing existing app array box design
        int n = values.length;
        float padding = 16f;
        float availW = width - (padding * 2f);
        float cellGap = Math.min(8f, Math.max(3f, (availW / n) * 0.10f));
        float cellWidth = Math.min(76f, Math.max(38f, (availW - (n - 1) * cellGap) / Math.max(n, 1)));
        float totalArrayW = n * cellWidth + (n - 1) * cellGap;
        float arrayStartX = Math.max(padding, (width - totalArrayW) / 2f);

        float arrayTop = isLandscapeCanvas() ? 80f : 96f;
        float boxHeight = Math.min(60f, Math.max(44f, resolveBoxHeight() * 0.65f));
        float arrayBottom = arrayTop + boxHeight;
        float textSize = resolveResponsiveTextSize(18f, 24f);

        float activeCellCenterX = -1f;
        float activeCellBottomY = arrayBottom;
        int activeDigit = -1;

        for (int i = 0; i < n; i++) {
            float left = arrayStartX + i * (cellWidth + cellGap);
            float right = left + cellWidth;
            float top = arrayTop;
            float bottom = arrayBottom;

            boolean isActive = (i == activeIndex && radixActiveBucket >= 0);
            boolean isCompleted = isFinished || isCompletedIndex(i);

            int val = values[i];
            int digit = Math.abs(val / divisor) % 10;

            if (isActive) {
                activeCellCenterX = (left + right) / 2f;
                activeCellBottomY = bottom;
                activeDigit = digit;
            }

            // Box fill using app's box shader
            applyBoxShader(left, top, right, bottom, i);
            canvas.drawRoundRect(left, top, right, bottom, 18f, 18f, fillPaint);

            // Neon gradient stroke matching app's standard design
            int strokeColorA = Color.parseColor("#38BDF8"); // Cyan
            int strokeColorB = Color.parseColor("#C084FC"); // Purple
            if (isActive) {
                strokeColorA = Color.parseColor("#F59E0B"); // Amber
                strokeColorB = Color.parseColor("#EF4444"); // Red
            } else if (isCompleted) {
                strokeColorA = Color.parseColor("#34D399"); // Emerald
                strokeColorB = Color.parseColor("#10B981"); // Green
            }

            Paint boxStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
            boxStroke.setStyle(Paint.Style.STROKE);
            boxStroke.setStrokeWidth(isActive ? 4.5f : 3.5f);
            boxStroke.setShader(new LinearGradient(left, top, right, bottom, strokeColorA, strokeColorB, Shader.TileMode.CLAMP));
            canvas.drawRoundRect(left, top, right, bottom, 18f, 18f, boxStroke);

            // Outer glow
            drawGlow(canvas, left, top, right, bottom, i);

            // Number value text
            textPaint.setColor(Color.WHITE);
            textPaint.setFakeBoldText(true);
            textPaint.setTextSize(textSize);
            textPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(String.valueOf(val), (left + right) / 2f, top + (boxHeight * 0.58f), textPaint);

            // Highlight the active digit being examined with a dedicated prominent badge
            if (isActive) {
                String badgeText;
                if (primaryPointerLabel != null && !primaryPointerLabel.isEmpty()) {
                    badgeText = primaryPointerLabel;
                } else {
                    String placeName = SortVisualizationEngine.describeDigit(divisor);
                    badgeText = placeName.substring(0, 1).toUpperCase(java.util.Locale.US) + placeName.substring(1) + " = " + digit;
                }

                float badgeCenterX = (left + right) / 2f;
                float arrowTipY = top - 4f;
                float arrowBaseY = top - 12f;
                float badgeBottomY = arrowBaseY;
                float badgeH = isLandscapeCanvas() ? 26f : 28f;
                float badgeTopY = badgeBottomY - badgeH;

                Paint badgeTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                badgeTextPaint.setColor(Color.parseColor("#FCD34D"));
                badgeTextPaint.setTextSize(isLandscapeCanvas() ? 14f : 15f);
                badgeTextPaint.setFakeBoldText(true);
                badgeTextPaint.setTextAlign(Paint.Align.CENTER);

                float textW = badgeTextPaint.measureText(badgeText);
                float badgeW = textW + 20f;
                float badgeLeft = badgeCenterX - (badgeW / 2f);
                float badgeRight = badgeLeft + badgeW;

                if (badgeLeft < 6f) {
                    float shift = 6f - badgeLeft;
                    badgeLeft += shift;
                    badgeRight += shift;
                } else if (badgeRight > width - 6f) {
                    float shift = badgeRight - (width - 6f);
                    badgeLeft -= shift;
                    badgeRight -= shift;
                }

                // Badge pill background
                RectF badgeRect = new RectF(badgeLeft, badgeTopY, badgeRight, badgeBottomY);
                Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                bgPaint.setStyle(Paint.Style.FILL);
                bgPaint.setColor(Color.parseColor("#0F172A")); // Slate 900
                canvas.drawRoundRect(badgeRect, 8f, 8f, bgPaint);

                // Badge border
                Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                borderPaint.setStyle(Paint.Style.STROKE);
                borderPaint.setStrokeWidth(2f);
                borderPaint.setColor(Color.parseColor("#F59E0B"));
                canvas.drawRoundRect(badgeRect, 8f, 8f, borderPaint);

                // Pointer downward triangle
                Path arrowPath = new Path();
                arrowPath.moveTo(badgeCenterX - 5.5f, arrowBaseY);
                arrowPath.lineTo(badgeCenterX + 5.5f, arrowBaseY);
                arrowPath.lineTo(badgeCenterX, arrowTipY);
                arrowPath.close();

                Paint arrowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                arrowPaint.setStyle(Paint.Style.FILL);
                arrowPaint.setColor(Color.parseColor("#F59E0B"));
                canvas.drawPath(arrowPath, arrowPaint);

                // Badge text
                float textBaseline = badgeTopY + (badgeH * 0.72f);
                canvas.drawText(badgeText, (badgeLeft + badgeRight) / 2f, textBaseline, badgeTextPaint);

                // Small pill badge underneath value showing digit
                Paint pillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                pillPaint.setStyle(Paint.Style.FILL);
                pillPaint.setColor(Color.parseColor("#F59E0B"));
                float pillW = 30f;
                float pillH = 14f;
                float pillX = (left + right) / 2f - pillW / 2f;
                float pillY = bottom - 15f;
                canvas.drawRoundRect(pillX, pillY, pillX + pillW, pillY + pillH, 6f, 6f, pillPaint);

                Paint pillText = new Paint(Paint.ANTI_ALIAS_FLAG);
                pillText.setColor(Color.BLACK);
                pillText.setTextSize(9.5f);
                pillText.setFakeBoldText(true);
                pillText.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("d=" + digit, pillX + pillW / 2f, pillY + 10.5f, pillText);
            }

            // Array index [i] below card
            Paint indexPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            indexPaint.setColor(Color.parseColor("#94A3B8"));
            indexPaint.setTextAlign(Paint.Align.CENTER);
            indexPaint.setTextSize(12f);
            canvas.drawText("[" + i + "]", (left + right) / 2f, bottom + 16f, indexPaint);
        }

        // Elements placed into buckets so far in this pass
        int processedLimit;
        if (radixActiveBucket == -1) {
            processedLimit = values.length;
        } else if (radixProcessedCount >= 0) {
            processedLimit = Math.min(radixProcessedCount, values.length);
        } else if (activeIndex >= 0) {
            processedLimit = Math.min(activeIndex + 1, values.length);
        } else {
            processedLimit = 0;
        }

        int maxBucketItems = 0;
        java.util.List<java.util.List<Integer>> bucketLists = new java.util.ArrayList<>(10);
        for (int b = 0; b < 10; b++) {
            java.util.List<Integer> list = new java.util.ArrayList<>();
            for (int k = 0; k < processedLimit; k++) {
                if (Math.abs(values[k] / divisor) % 10 == b) {
                    list.add(values[k]);
                }
            }
            if (list.size() > maxBucketItems) {
                maxBucketItems = list.size();
            }
            bucketLists.add(list);
        }

        // 2. Buckets Row - exactly 10 horizontal buckets (0 to 9)
        float bMargin = 10f;
        float bAvailableW = width - (bMargin * 2f);
        float bGap = Math.min(6f, Math.max(2.5f, (bAvailableW / 10f) * 0.06f));
        float bWidth = (bAvailableW - (9f * bGap)) / 10f;
        float bTop = arrayBottom + (isLandscapeCanvas() ? 28f : 34f);

        float headerH = 22f;
        float chipH = isLandscapeCanvas() ? 17f : 20f;
        float chipGap = 3f;
        float baseEmptyHeight = headerH + 24f;
        float bHeight = maxBucketItems == 0
            ? baseEmptyHeight
            : (headerH + 8f + (maxBucketItems * (chipH + chipGap)) + 4f);
        float bBottom = bTop + bHeight;

        // Animated trajectory from active array cell to target bucket
        if (activeCellCenterX > 0 && radixActiveBucket >= 0 && radixActiveBucket < 10) {
            float targetBucketX = bMargin + radixActiveBucket * (bWidth + bGap) + (bWidth / 2f);
            float targetBucketY = bTop;

            Path trajectory = new Path();
            trajectory.moveTo(activeCellCenterX, activeCellBottomY);
            float ctrlY = (activeCellBottomY + targetBucketY) / 2f;
            trajectory.quadTo((activeCellCenterX + targetBucketX) / 2f, ctrlY, targetBucketX, targetBucketY);

            Paint trajPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            trajPaint.setStyle(Paint.Style.STROKE);
            trajPaint.setStrokeWidth(3f);
            trajPaint.setColor(Color.parseColor("#F59E0B"));
            trajPaint.setAlpha(170);
            canvas.drawPath(trajectory, trajPaint);

            // Particle along path
            float t = pulseProgress;
            float omt = 1f - t;
            float midX = (activeCellCenterX + targetBucketX) / 2f;
            float px = omt * omt * activeCellCenterX + 2f * omt * t * midX + t * t * targetBucketX;
            float py = omt * omt * activeCellBottomY + 2f * omt * t * ctrlY + t * t * targetBucketY;

            Paint dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            dotPaint.setColor(Color.parseColor("#FCD34D"));
            canvas.drawCircle(px, py, 5f + (pulseProgress * 2f), dotPaint);
        }

        for (int b = 0; b < 10; b++) {
            float bLeft = bMargin + b * (bWidth + bGap);
            float bRight = bLeft + bWidth;

            boolean isBucketActive = (b == radixActiveBucket);
            boolean isGatherPhase = (radixActiveBucket == -1);

            // Bucket Container Card (using app's card/box aesthetic)
            RectF bRect = new RectF(bLeft, bTop, bRight, bBottom);
            Paint bFill = new Paint(Paint.ANTI_ALIAS_FLAG);
            bFill.setStyle(Paint.Style.FILL);
            bFill.setColor(isBucketActive ? Color.parseColor("#172033") : Color.parseColor("#0B1020"));
            canvas.drawRoundRect(bRect, 12f, 12f, bFill);

            // Bucket Border
            Paint bStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
            bStroke.setStyle(Paint.Style.STROKE);
            if (isBucketActive) {
                bStroke.setColor(Color.parseColor("#F59E0B"));
                bStroke.setStrokeWidth(3f);
                softGlowPaint.setColor(Color.parseColor("#F59E0B"));
                softGlowPaint.setAlpha((int) (40 + pulseProgress * 50));
                canvas.drawRoundRect(bLeft - 3f, bTop - 3f, bRight + 3f, bBottom + 3f, 14f, 14f, softGlowPaint);
            } else if (isGatherPhase) {
                bStroke.setColor(Color.parseColor("#10B981"));
                bStroke.setStrokeWidth(1.8f);
            } else {
                bStroke.setColor(Color.parseColor("#1E293B"));
                bStroke.setStrokeWidth(1.2f);
            }
            canvas.drawRoundRect(bRect, 12f, 12f, bStroke);

            // Bucket Header Strip
            RectF headerRect = new RectF(bLeft, bTop, bRight, bTop + headerH);
            Paint hFill = new Paint(Paint.ANTI_ALIAS_FLAG);
            hFill.setStyle(Paint.Style.FILL);
            hFill.setColor(isBucketActive ? Color.parseColor("#F59E0B") : (isGatherPhase ? Color.parseColor("#065F46") : Color.parseColor("#1E293B")));
            canvas.drawRoundRect(headerRect, 12f, 12f, hFill);
            canvas.drawRect(bLeft, bTop + headerH - 6f, bRight, bTop + headerH, hFill);

            // Header Digit Label ("0", "1", ... "9")
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(12f);
            textPaint.setFakeBoldText(true);
            textPaint.setColor(isBucketActive ? Color.BLACK : Color.WHITE);
            canvas.drawText(String.valueOf(b), bRect.centerX(), bTop + 15.5f, textPaint);

            // Items inside this bucket in FIFO order
            java.util.List<Integer> inBucket = bucketLists.get(b);
            if (inBucket.isEmpty()) {
                textPaint.setColor(Color.parseColor("#475569"));
                textPaint.setTextSize(13f);
                textPaint.setFakeBoldText(false);
                canvas.drawText("—", bRect.centerX(), bTop + headerH + 16f, textPaint);
            } else {
                float itemStartY = bTop + headerH + 5f;
                int count = inBucket.size();
                for (int m = 0; m < count; m++) {
                    int itemVal = inBucket.get(m);
                    boolean isLatest = isBucketActive && (m == count - 1);
                    float cY = itemStartY + m * (chipH + chipGap);

                    // Reusing the app's mini value box design
                    drawMiniValueBox(canvas, itemVal, bLeft + 2.5f, cY, bRight - 2.5f, cY + chipH, isLatest);
                }
            }
        }

        // 3. Collection Notice Banner when buckets are collected
        if (radixActiveBucket == -1 && !isFinished && processedLimit > 0) {
            float bannerY = bBottom + 8f;
            RectF bannerRect = new RectF(bMargin, bannerY, width - bMargin, bannerY + 24f);
            Paint bFill = new Paint(Paint.ANTI_ALIAS_FLAG);
            bFill.setStyle(Paint.Style.FILL);
            bFill.setColor(Color.parseColor("#064E3B"));
            canvas.drawRoundRect(bannerRect, 6f, 6f, bFill);

            Paint bStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
            bStroke.setStyle(Paint.Style.STROKE);
            bStroke.setColor(Color.parseColor("#10B981"));
            bStroke.setStrokeWidth(1.2f);
            canvas.drawRoundRect(bannerRect, 6f, 6f, bStroke);

            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setColor(Color.parseColor("#34D399"));
            textPaint.setTextSize(11f);
            textPaint.setFakeBoldText(true);
            canvas.drawText("✓ COLLECTED BUCKETS 0 → 9 (FIFO Stable)", bannerRect.centerX(), bannerRect.centerY() + 4f, textPaint);
        }
    }

    public int resolveRadixDesiredWidth() {
        if (isLandscapeCanvas() && getParent() instanceof View) {
            int parentWidth = ((View) getParent()).getWidth();
            if (parentWidth > 0) {
                return parentWidth;
            }
        }
        return getResources().getDisplayMetrics().widthPixels;
    }

    public int resolveRadixDesiredHeight() {
        int n = values != null ? values.length : 0;
        int divisor = Math.max(1, radixDivisor);
        int maxCount = 0;
        if (n > 0) {
            int limit = radixActiveBucket == -1 ? n : (radixProcessedCount >= 0 ? Math.min(radixProcessedCount, n) : 0);
            int[] counts = new int[10];
            for (int i = 0; i < limit; i++) {
                int d = Math.abs(values[i] / divisor) % 10;
                counts[d]++;
                if (counts[d] > maxCount) {
                    maxCount = counts[d];
                }
            }
        }
        float arrayTop = isLandscapeCanvas() ? 80f : 96f;
        float boxHeight = Math.min(60f, Math.max(44f, resolveBoxHeight() * 0.65f));
        float arrayBottom = arrayTop + boxHeight + 18f;
        float bTop = arrayBottom + (isLandscapeCanvas() ? 28f : 34f);
        float headerH = 22f;
        float chipH = isLandscapeCanvas() ? 17f : 20f;
        float chipGap = 3f;
        float bHeight = maxCount == 0
            ? (headerH + 24f)
            : (headerH + 8f + (maxCount * (chipH + chipGap)) + 4f);
        float totalH = bTop + bHeight + (isLandscapeCanvas() ? 16f : 26f);
        if (radixActiveBucket == -1 && n > 0) {
            totalH += 32f;
        }
        return Math.max(Math.round(totalH), isLandscapeCanvas() ? 200 : 250);
    }

    private String resolveDigitLabel() {
        if (radixDivisor == 1) {
            return "ones digit";
        }
        if (radixDivisor == 10) {
            return "tens digit";
        }
        if (radixDivisor == 100) {
            return "hundreds digit";
        }
        return radixDivisor + "s place";
    }

    private void drawGraphEdge(Canvas canvas, float[][] points, int from, int to) {
        boolean directed = isDirectedGraphMode();
        boolean isActive = directed
            ? (from == activeEdgeFrom && to == activeEdgeTo)
            : ((from == activeEdgeFrom && to == activeEdgeTo) || (from == activeEdgeTo && to == activeEdgeFrom));
        Paint linePaint = isActive ? new Paint(accentPaint) : strokePaint;
        if (isActive) {
            linePaint.setColor(Color.parseColor("#38BDF8"));
            linePaint.setStrokeWidth(8f);
        } else if ("dijkstra_algorithm".equals(techniqueId) && isDijkstraGlowEdge(from, to)) {
            linePaint = new Paint(strokePaint);
            linePaint.setColor(Color.parseColor("#34D399"));
            linePaint.setStrokeWidth(6f);
            linePaint.setAlpha(170);
        }
        float startX = points[from][0];
        float startY = points[from][1];
        float endX = points[to][0];
        float endY = points[to][1];
        canvas.drawLine(startX, startY, endX, endY, linePaint);
        if (directed) {
            drawArrowHead(canvas, startX, startY, endX, endY, linePaint);
        }
        drawGraphEdgeWeight(canvas, points, from, to, isActive);

        if (isActive) {
            Paint glowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            glowPaint.setStyle(Paint.Style.STROKE);
            glowPaint.setStrokeWidth(16f);
            glowPaint.setColor(Color.parseColor("#38BDF8"));
            glowPaint.setAlpha(60);
            canvas.drawLine(startX, startY, endX, endY, glowPaint);
            float dotX = lerp(startX, endX, edgeProgress);
            float dotY = lerp(startY, endY, edgeProgress);
            Paint dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            dotPaint.setColor(Color.parseColor("#FCD34D"));
            canvas.drawCircle(dotX, dotY, 10f + (pulseProgress * 4f), dotPaint);
        }
    }

    private void applyBoxShader(float left, float top, float right, float bottom, int index) {
        int baseColor = resolveFill(index);
        int fillA, fillB;
        if (index != activeIndex && index != secondaryIndex && index != foundIndex && !isCompletedIndex(index)) {
            // Dark glassmorphic background: translucent dark slate-blue
            fillA = Color.argb(190, 15, 23, 42); // slate-900 transparent
            fillB = Color.argb(190, 30, 41, 59); // slate-800 transparent
        } else {
            // Highlighted gradient fill
            fillA = lightenColor(baseColor, 0.16f);
            fillB = baseColor;
            // Retain subtle transparency for high-tech glow card feel
            fillA = Color.argb(220, Color.red(fillA), Color.green(fillA), Color.blue(fillA));
            fillB = Color.argb(220, Color.red(fillB), Color.green(fillB), Color.blue(fillB));
        }
        fillPaint.setShader(new LinearGradient(left, top, right, bottom, fillA, fillB, Shader.TileMode.CLAMP));
    }

    private void applyNodeShader(float centerX, float centerY, int baseColor) {
        int brighter = lightenColor(baseColor, 0.22f);
        fillPaint.setShader(new LinearGradient(
            centerX - 42f,
            centerY - 42f,
            centerX + 42f,
            centerY + 42f,
            brighter,
            baseColor,
            Shader.TileMode.CLAMP
        ));
    }

    private void drawGlow(Canvas canvas, float left, float top, float right, float bottom, int index) {
        if (index != activeIndex && index != secondaryIndex && index != foundIndex) {
            return;
        }

        int glowColor;
        if (index == foundIndex) {
            glowColor = Color.parseColor("#22C55E");
        } else if (index == secondaryIndex) {
            glowColor = isSortingTechnique() ? Color.parseColor("#A855F7") : Color.parseColor("#38BDF8");
        } else {
            glowColor = isSearchingTechnique() ? Color.parseColor("#38BDF8") : Color.parseColor("#F59E0B");
        }

        softGlowPaint.setShader(null);
        softGlowPaint.setColor(glowColor);
        softGlowPaint.setAlpha(index == foundIndex ? (int) (42 + (pulseProgress * 52)) : (int) (24 + (pulseProgress * 36)));
        tempRect.set(left - 16f, top - 16f, right + 16f, bottom + 16f);
        canvas.drawRoundRect(tempRect, 34f, 34f, softGlowPaint);

        if (index == foundIndex) {
            Paint spotlight = new Paint(Paint.ANTI_ALIAS_FLAG);
            spotlight.setShader(new LinearGradient(
                (left + right) / 2f,
                top - 84f,
                (left + right) / 2f,
                bottom,
                Color.argb(110, 134, 239, 172),
                Color.argb(0, 134, 239, 172),
                Shader.TileMode.CLAMP
            ));
            canvas.drawRoundRect(left - 8f, top - 80f, right + 8f, bottom + 8f, 28f, 28f, spotlight);
        }

        Paint glowStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        glowStroke.setStyle(Paint.Style.STROKE);
        glowStroke.setStrokeWidth(index == foundIndex ? 12f : 10f);
        glowStroke.setColor(lightenColor(glowColor, 0.18f));
        glowStroke.setAlpha((int) (70 + (pulseProgress * 80)));
        canvas.drawRoundRect(left - 6f, top - 6f, right + 6f, bottom + 6f, 28f, 28f, glowStroke);
    }

    private void drawMergeRow(Canvas canvas, int[] rowValues, float startX, float topY, String label) {
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(26f);
        canvas.drawText(label, startX, topY - 18f, textPaint);
        for (int i = 0; i < rowValues.length; i++) {
            float left = startX + (i * 74f);
            drawMiniValueBox(canvas, rowValues[i], left, topY, left + 60f, topY + 54f, false);
        }
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    private void drawHeapEdge(Canvas canvas, float[][] points, int from, int to) {
        Paint edgePaint = new Paint(strokePaint);
        if ((from == activeIndex && to == secondaryIndex) || (from == secondaryIndex && to == activeIndex) || isTraversalEdgeActive(from, to)) {
            edgePaint.setColor(Color.parseColor("#38BDF8"));
            edgePaint.setStrokeWidth(8f);
        }
        canvas.drawLine(points[from][0], points[from][1], points[to][0], points[to][1], edgePaint);
        if ((from == activeIndex && to == secondaryIndex) || (from == secondaryIndex && to == activeIndex)) {
            Paint dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            dotPaint.setColor(Color.parseColor("#FCD34D"));
            float dotX = lerp(points[from][0], points[to][0], pulseProgress);
            float dotY = lerp(points[from][1], points[to][1], pulseProgress);
            canvas.drawCircle(dotX, dotY, 9f + (pulseProgress * 2f), dotPaint);
        }
    }

    private void drawStringRow(Canvas canvas, String value, float startX, float topY, boolean useWindow) {
        for (int i = 0; i < value.length(); i++) {
            float left = startX + (i * 56f);
            float right = left + 46f;
            boolean active = useWindow && i == patternWindowStart + patternMatchIndex;
            boolean inWindow = useWindow && patternWindowStart >= 0 && i >= patternWindowStart && i < patternWindowStart + patternQuery.length();
            int baseColor = patternFound && inWindow ? Color.parseColor("#16A34A") : active ? Color.parseColor("#F59E0B") : inWindow ? Color.parseColor("#0EA5E9") : Color.parseColor("#1E293B");
            int bright = lightenColor(baseColor, 0.18f);
            fillPaint.setShader(new LinearGradient(left, topY, right, topY + 52f, bright, baseColor, Shader.TileMode.CLAMP));
            canvas.drawRoundRect(left, topY, right, topY + 52f, 14f, 14f, fillPaint);
            canvas.drawRoundRect(left, topY, right, topY + 52f, 14f, 14f, strokePaint);
            textPaint.setTextSize(24f);
            canvas.drawText(String.valueOf(value.charAt(i)), (left + right) / 2f, topY + 34f, textPaint);
        }
    }

    private void drawLabeledNode(Canvas canvas, float centerX, float centerY, String label, boolean active) {
        float effectiveRadius = 38f + (active ? pulseProgress * 5f : 0f);
        if (active) {
            Paint halo = new Paint(Paint.ANTI_ALIAS_FLAG);
            halo.setStyle(Paint.Style.FILL);
            halo.setColor(Color.parseColor("#F59E0B"));
            halo.setAlpha((int) (40 + pulseProgress * 60));
            canvas.drawCircle(centerX, centerY, effectiveRadius + 8f + pulseProgress * 4f, halo);
        }
        applyNodeShader(centerX, centerY, active ? Color.parseColor("#F59E0B") : Color.parseColor("#1E293B"));
        canvas.drawCircle(centerX, centerY, effectiveRadius, fillPaint);
        Paint stroke = new Paint(strokePaint);
        stroke.setColor(active ? Color.parseColor("#FDE68A") : Color.parseColor("#475569"));
        stroke.setStrokeWidth(active ? 4.5f : 3f);
        canvas.drawCircle(centerX, centerY, effectiveRadius, stroke);
        textPaint.setTextSize(active ? 25f + pulseProgress * 2.5f : 24f);
        textPaint.setFakeBoldText(true);
        canvas.drawText(label, centerX, centerY + 8f, textPaint);
    }

    private void drawLinkedStackArrow(Canvas canvas, float baseX, float centerY, boolean topNode) {
        Paint arrowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arrowPaint.setStyle(Paint.Style.STROKE);
        arrowPaint.setStrokeWidth(4f);
        arrowPaint.setColor(topNode ? Color.parseColor("#FCD34D") : Color.parseColor("#7DD3FC"));
        arrowPaint.setAlpha(topNode ? 220 : 170);
        float arrowEndX = baseX + 22f;
        canvas.drawLine(baseX, centerY, arrowEndX, centerY, arrowPaint);
        canvas.drawLine(arrowEndX - 8f, centerY - 7f, arrowEndX, centerY, arrowPaint);
        canvas.drawLine(arrowEndX - 8f, centerY + 7f, arrowEndX, centerY, arrowPaint);
    }

    private void drawCircularBadge(Canvas canvas, float centerX, float centerY, String label, String colorHex) {
        Paint badgeFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        badgeFill.setStyle(Paint.Style.FILL);
        badgeFill.setColor(Color.parseColor(colorHex));
        badgeFill.setAlpha(215);
        Paint badgeStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        badgeStroke.setStyle(Paint.Style.STROKE);
        badgeStroke.setStrokeWidth(3f);
        badgeStroke.setColor(lightenColor(Color.parseColor(colorHex), 0.18f));
        float halfWidth = 38f;
        float halfHeight = 16f;
        canvas.drawRoundRect(centerX - halfWidth, centerY - halfHeight, centerX + halfWidth, centerY + halfHeight, 16f, 16f, badgeFill);
        canvas.drawRoundRect(centerX - halfWidth, centerY - halfHeight, centerX + halfWidth, centerY + halfHeight, 16f, 16f, badgeStroke);

        Paint labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        labelPaint.setColor(Color.WHITE);
        labelPaint.setTextAlign(Paint.Align.CENTER);
        labelPaint.setTextSize(20f);
        canvas.drawText(label, centerX, centerY + 6f, labelPaint);
    }

    private void drawTreeTeachingOverlays(Canvas canvas, float[][] points, int count, boolean heapMode, boolean avlMode) {
        if (count <= 0) {
            return;
        }
        boolean compact = isCompactTreeLayout();
        drawTreeRoleBadge(canvas, points[0][0], points[0][1] - (compact ? 46f : 54f), heapMode ? "ROOT" : "Root", "#2563EB");
        if (count > 1) {
            drawTreeRoleBadge(canvas, points[1][0], points[1][1] - (compact ? 42f : 50f), compact ? "L" : "Left", "#0F766E");
        }
        if (count > 2) {
            drawTreeRoleBadge(canvas, points[2][0], points[2][1] - (compact ? 42f : 50f), compact ? "R" : "Right", "#9333EA");
        }
        if (!heapMode && activeIndex > 0 && activeIndex < count) {
            int parentIndex = (activeIndex - 1) / 2;
            if (parentIndex >= 0 && parentIndex < count) {
                drawTreeRoleBadge(canvas, points[parentIndex][0], points[parentIndex][1] + (compact ? 44f : 52f), compact ? "P" : "Parent", "#0EA5E9");
            }
        }
        int leafLimit = compact ? Math.min(count, 5) : count;
        for (int i = 3; i < leafLimit; i++) {
            drawTreeRoleBadge(canvas, points[i][0], points[i][1] + (compact ? 44f : 52f), compact ? "Leaf" : "Leaf", "#475569");
        }
        if (!heapMode && secondaryIndex >= 0 && secondaryIndex < count) {
            drawTreeRoleBadge(canvas, points[secondaryIndex][0], points[secondaryIndex][1] - (compact ? 70f : 82f), compact ? "Succ" : "Successor", "#16A34A");
        }
        if (avlMode && activeIndex >= 0 && secondaryIndex >= 0 && activeIndex < count) {
            drawTreeRoleBadge(canvas, points[activeIndex][0], points[activeIndex][1] - (compact ? 70f : 82f), compact ? "Height" : "Height Shift", "#D97706");
            drawTreeRoleBadge(canvas, points[activeIndex][0], points[activeIndex][1] + (compact ? 48f : 58f), compact ? "BF" : "Balance", "#F59E0B");
        }
    }

    private void drawTreeRoleBadge(Canvas canvas, float centerX, float centerY, String label, String colorHex) {
        boolean compact = isCompactTreeLayout();
        Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
        fill.setColor(Color.parseColor(colorHex));
        fill.setAlpha(205);
        Paint stroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        stroke.setStyle(Paint.Style.STROKE);
        stroke.setStrokeWidth(2f);
        stroke.setColor(lightenColor(Color.parseColor(colorHex), 0.16f));
        float halfWidth = compact
            ? Math.max(26f, Math.min(48f, label.length() * 4.4f))
            : Math.max(32f, Math.min(62f, label.length() * 4.9f));
        float halfHeight = compact ? 12f : 14f;
        canvas.drawRoundRect(centerX - halfWidth, centerY - halfHeight, centerX + halfWidth, centerY + halfHeight, 12f, 12f, fill);
        canvas.drawRoundRect(centerX - halfWidth, centerY - halfHeight, centerX + halfWidth, centerY + halfHeight, 12f, 12f, stroke);
        Paint text = new Paint(Paint.ANTI_ALIAS_FLAG);
        text.setColor(Color.WHITE);
        text.setTextAlign(Paint.Align.CENTER);
        text.setTextSize(compact ? 15f : 17f);
        canvas.drawText(label, centerX, centerY + 5f, text);
    }

    private void drawTraversalTravelTrail(Canvas canvas, float[][] points) {
        if (traversalStep <= 0 || traversalStep >= traversalOrder.length) {
            return;
        }
        int from = traversalOrder[traversalStep - 1];
        int to = traversalOrder[traversalStep];
        if (from < 0 || to < 0 || from >= points.length || to >= points.length) {
            return;
        }
        Paint trailPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        trailPaint.setStyle(Paint.Style.STROKE);
        trailPaint.setStrokeWidth(isCompactGraphLayout() ? 8f : 10f);
        trailPaint.setStrokeCap(Paint.Cap.ROUND);
        trailPaint.setShader(new LinearGradient(
            points[from][0],
            points[from][1],
            points[to][0],
            points[to][1],
            Color.argb(40, 56, 189, 248),
            Color.argb(180, 56, 189, 248),
            Shader.TileMode.CLAMP
        ));
        canvas.drawLine(points[from][0], points[from][1], points[to][0], points[to][1], trailPaint);

        Paint dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dotPaint.setColor(Color.parseColor("#FCD34D"));
        float dotX = lerp(points[from][0], points[to][0], pulseProgress);
        float dotY = lerp(points[from][1], points[to][1], pulseProgress);
        canvas.drawCircle(dotX, dotY, 7f + (pulseProgress * 3f), dotPaint);
    }

    private void drawNodeArrivalEffect(Canvas canvas, float centerX, float centerY, float radius, int nodeIndex, boolean graphMode) {
        if (!isInsertionFocusNode(nodeIndex, graphMode)) {
            return;
        }
        Paint haloPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        haloPaint.setStyle(Paint.Style.STROKE);
        haloPaint.setStrokeWidth(graphMode ? 8f : 9f);
        haloPaint.setColor(graphMode ? Color.parseColor("#22C55E") : Color.parseColor("#38BDF8"));
        haloPaint.setAlpha(130);
        float arrivalRadius = radius + 12f + ((1f - structureMotionProgress) * 22f) + (pulseProgress * 8f);
        canvas.drawCircle(centerX, centerY, arrivalRadius, haloPaint);

        Paint sparkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        sparkPaint.setColor(Color.parseColor("#FCD34D"));
        float orbit = radius + 18f + (pulseProgress * 8f);
        canvas.drawCircle(centerX + orbit, centerY - 6f, 4f, sparkPaint);
        canvas.drawCircle(centerX - orbit * 0.72f, centerY - orbit * 0.34f, 3.5f, sparkPaint);
    }

    private void drawStructureArrivalEffect(Canvas canvas, float centerX, float centerY, float radius, int index) {
        if (index != movingStructureIndex) {
            return;
        }
        if (isStructureExitOperation()) {
            Paint particlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            particlePaint.setStyle(Paint.Style.FILL);
            particlePaint.setColor(Color.parseColor("#EF4444")); // Coral Red explosion
            particlePaint.setAlpha((int) ((1f - structureMotionProgress) * 255));
            float distance = radius + (structureMotionProgress * 70f);
            for (int angle = 0; angle < 360; angle += 45) {
                float rad = (float) Math.toRadians(angle);
                float px = centerX + (float) Math.cos(rad) * distance;
                float py = centerY + (float) Math.sin(rad) * distance;
                canvas.drawCircle(px, py, 6f * (1f - structureMotionProgress), particlePaint);
            }
            return;
        }
        if (!isStructureEntryOperation()) {
            return;
        }
        Paint haloPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        haloPaint.setStyle(Paint.Style.STROKE);
        haloPaint.setStrokeWidth(8f);
        haloPaint.setColor(Color.parseColor("#38BDF8"));
        haloPaint.setAlpha(125);
        float haloRadius = radius + 10f + ((1f - structureMotionProgress) * 26f);
        canvas.drawCircle(centerX, centerY, haloRadius, haloPaint);

        Paint sparkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        sparkPaint.setColor(Color.parseColor("#FCD34D"));
        float sparkOffset = radius + 18f + ((1f - structureMotionProgress) * 10f);
        canvas.drawCircle(centerX - sparkOffset, centerY, 4f + (pulseProgress * 1.5f), sparkPaint);
        canvas.drawCircle(centerX + (sparkOffset * 0.72f), centerY - (sparkOffset * 0.36f), 3.5f, sparkPaint);
    }

    private void drawGraphEdgeWeight(Canvas canvas, float[][] points, int from, int to, boolean active) {
        int weight = resolveGraphEdgeWeight(from, to);
        if (weight == Integer.MIN_VALUE) {
            return;
        }
        float midX = (points[from][0] + points[to][0]) / 2f;
        float midY = (points[from][1] + points[to][1]) / 2f;
        Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
        fill.setColor(active ? Color.parseColor("#1D4ED8") : Color.parseColor("#0F172A"));
        fill.setAlpha(active ? 235 : 205);
        Paint stroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        stroke.setStyle(Paint.Style.STROKE);
        stroke.setStrokeWidth(2f);
        stroke.setColor(active ? Color.parseColor("#93C5FD") : Color.parseColor("#64748B"));
        canvas.drawRoundRect(midX - 24f, midY - 18f, midX + 24f, midY + 18f, 14f, 14f, fill);
        canvas.drawRoundRect(midX - 24f, midY - 18f, midX + 24f, midY + 18f, 14f, 14f, stroke);
        Paint text = new Paint(Paint.ANTI_ALIAS_FLAG);
        text.setColor(Color.WHITE);
        text.setTextAlign(Paint.Align.CENTER);
        text.setTextSize(22f);
        canvas.drawText(String.valueOf(weight), midX, midY + 8f, text);
    }

    private void drawLinkedMemoryView(Canvas canvas, float padding, float cellWidth, float baseWidth, float bottom) {
        float startY = Math.min(getHeight() - 82f, bottom + 34f);
        Paint labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        labelPaint.setColor(Color.parseColor("#CBD5E1"));
        labelPaint.setTextAlign(Paint.Align.LEFT);
        labelPaint.setTextSize(18f);
        canvas.drawText(memoryAddressModeEnabled ? "Memory Address View" : "Memory View", padding, startY - 12f, labelPaint);
        float laneHeight = memoryAddressModeEnabled ? 42f : 26f;
        for (int i = 0; i < values.length; i++) {
            float left = padding + (i * cellWidth);
            float split = left + (baseWidth * 0.58f);
            float right = left + baseWidth;
            Paint infoFill = new Paint(Paint.ANTI_ALIAS_FLAG);
            infoFill.setColor(Color.parseColor("#334155"));
            Paint linkFill = new Paint(Paint.ANTI_ALIAS_FLAG);
            linkFill.setColor(Color.parseColor("#1E3A8A"));
            canvas.drawRoundRect(left, startY, split, startY + laneHeight, 12f, 12f, infoFill);
            canvas.drawRoundRect(split + 4f, startY, right, startY + laneHeight, 12f, 12f, linkFill);
            Paint tiny = new Paint(Paint.ANTI_ALIAS_FLAG);
            tiny.setColor(Color.WHITE);
            tiny.setTextAlign(Paint.Align.CENTER);
            tiny.setTextSize(memoryAddressModeEnabled ? 12f : 14f);
            if (memoryAddressModeEnabled) {
                canvas.drawText(resolveMemoryAddress(i), (left + split) / 2f, startY + 15f, tiny);
                canvas.drawText("val=" + values[i], (left + split) / 2f, startY + 31f, tiny);
                String nextLabel = i == values.length - 1 ? "next=NULL" : "next=" + resolveMemoryAddress(i + 1);
                canvas.drawText(nextLabel, (split + 4f + right) / 2f, startY + 24f, tiny);
            } else {
                canvas.drawText("INFO", (left + split) / 2f, startY + 17f, tiny);
                canvas.drawText(i == values.length - 1 ? "NULL" : "LINK", (split + 4f + right) / 2f, startY + 17f, tiny);
            }
        }
    }

    private void drawTreeMemoryOverlay(Canvas canvas, float[][] points, int count, boolean heapMode) {
        if (!memoryAddressModeEnabled || count <= 0) {
            return;
        }
        for (int i = 0; i < count; i++) {
            drawAddressBadge(canvas, points[i][0], points[i][1] - (heapMode ? 78f : 72f), resolveMemoryAddress(i));
            int left = (2 * i) + 1;
            int right = (2 * i) + 2;
            if (left < count) {
                drawMemoryPointerLabel(canvas, (points[i][0] + points[left][0]) / 2f - 18f, (points[i][1] + points[left][1]) / 2f, "L->" + resolveMemoryAddress(left));
            }
            if (right < count) {
                drawMemoryPointerLabel(canvas, (points[i][0] + points[right][0]) / 2f + 18f, (points[i][1] + points[right][1]) / 2f, "R->" + resolveMemoryAddress(right));
            }
        }
    }

    private void drawGraphMemoryOverlay(Canvas canvas, float[][] points, int count) {
        if (!memoryAddressModeEnabled || count <= 0) {
            return;
        }
        for (int i = 0; i < count; i++) {
            drawAddressBadge(canvas, points[i][0], points[i][1] - 68f, resolveMemoryAddress(i));
        }
        int[][] edges = resolveGraphEdges(count);
        for (int[] edge : edges) {
            float x = (points[edge[0]][0] + points[edge[1]][0]) / 2f;
            float y = (points[edge[0]][1] + points[edge[1]][1]) / 2f + 22f;
            drawMemoryPointerLabel(canvas, x, y, resolveMemoryAddress(edge[0]) + " -> " + resolveMemoryAddress(edge[1]));
        }
    }

    private void drawAddressBadge(Canvas canvas, float centerX, float centerY, String label) {
        Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
        fill.setColor(Color.parseColor("#0F172A"));
        fill.setAlpha(225);
        Paint stroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        stroke.setStyle(Paint.Style.STROKE);
        stroke.setStrokeWidth(2f);
        stroke.setColor(Color.parseColor("#60A5FA"));
        float halfWidth = Math.max(46f, Math.min(72f, label.length() * 5.1f));
        float halfHeight = 13f;
        canvas.drawRoundRect(centerX - halfWidth, centerY - halfHeight, centerX + halfWidth, centerY + halfHeight, 12f, 12f, fill);
        canvas.drawRoundRect(centerX - halfWidth, centerY - halfHeight, centerX + halfWidth, centerY + halfHeight, 12f, 12f, stroke);
        Paint text = new Paint(Paint.ANTI_ALIAS_FLAG);
        text.setColor(Color.parseColor("#DBEAFE"));
        text.setTextAlign(Paint.Align.CENTER);
        text.setTextSize(14f);
        canvas.drawText(label, centerX, centerY + 5f, text);
    }

    private void drawMemoryPointerLabel(Canvas canvas, float centerX, float centerY, String label) {
        Paint text = new Paint(Paint.ANTI_ALIAS_FLAG);
        text.setColor(Color.parseColor("#BFDBFE"));
        text.setTextAlign(Paint.Align.CENTER);
        text.setTextSize(12f);
        canvas.drawText(label, centerX, centerY, text);
    }

    private String resolveMemoryAddress(int index) {
        return String.format("0x%04X", 0x1200 + (index * 0x20));
    }

    private boolean isDirectedGraphMode() {
        return GraphExpandedStepEngine.isDirected(techniqueId)
            || "topological_sort".equals(techniqueId)
            || "warshall_algorithm".equals(techniqueId)
            || "floyd_algorithm".equals(techniqueId)
            || "shortest_paths".equals(techniqueId)
            || "dijkstra_algorithm".equals(techniqueId)
            || "insert_edge_graph".equals(techniqueId)
            || "delete_edge_graph".equals(techniqueId)
            || "find_edge_graph".equals(techniqueId);
    }

    private void drawArrowHead(Canvas canvas, float startX, float startY, float endX, float endY, Paint sourcePaint) {
        float angle = (float) Math.atan2(endY - startY, endX - startX);
        float radius = isCompactGraphLayout() ? 34f : 42f;
        float tipX = endX - ((float) Math.cos(angle) * radius * 0.92f);
        float tipY = endY - ((float) Math.sin(angle) * radius * 0.92f);
        float arrowSize = 16f;
        Paint arrowPaint = new Paint(sourcePaint);
        arrowPaint.setStyle(Paint.Style.STROKE);
        arrowPaint.setStrokeCap(Paint.Cap.ROUND);
        float leftX = tipX - ((float) Math.cos(angle - Math.PI / 6f) * arrowSize);
        float leftY = tipY - ((float) Math.sin(angle - Math.PI / 6f) * arrowSize);
        float rightX = tipX - ((float) Math.cos(angle + Math.PI / 6f) * arrowSize);
        float rightY = tipY - ((float) Math.sin(angle + Math.PI / 6f) * arrowSize);
        canvas.drawLine(tipX, tipY, leftX, leftY, arrowPaint);
        canvas.drawLine(tipX, tipY, rightX, rightY, arrowPaint);
    }

    private void drawHeapRelationshipBadges(Canvas canvas, float[][] points) {
        if (activeIndex < 0 || activeIndex >= values.length || activeIndex >= points.length) {
            return;
        }
        int parent = activeIndex == 0 ? -1 : (activeIndex - 1) / 2;
        int left = (2 * activeIndex) + 1;
        int right = (2 * activeIndex) + 2;
        float cx = points[activeIndex][0];
        float cy = points[activeIndex][1];
        if (parent >= 0 && parent < points.length) {
            drawTreeRoleBadge(canvas, (cx + points[parent][0]) / 2f, (cy + points[parent][1]) / 2f, "P", "#2563EB");
        }
        if (left < values.length && left < points.length) {
            drawTreeRoleBadge(canvas, (cx + points[left][0]) / 2f - 16f, (cy + points[left][1]) / 2f, "L", "#0F766E");
        }
        if (right < values.length && right < points.length) {
            drawTreeRoleBadge(canvas, (cx + points[right][0]) / 2f + 16f, (cy + points[right][1]) / 2f, "R", "#9333EA");
        }
    }


    private void drawPrimMstOverlay(Canvas canvas, float[][] points) {
        if (!("graph_prim".equals(techniqueId) || "graph_prims".equals(techniqueId))) {
            return;
        }
        String message = inlineTeachingMessage == null ? "" : inlineTeachingMessage;
        int visualStep = 0;
        if (message.contains("Step 5")) visualStep = 5;
        else if (message.contains("Step 4")) visualStep = 4;
        else if (message.contains("Step 3")) visualStep = 3;
        else if (message.contains("Step 2")) visualStep = 2;
        else if (message.contains("Step 1")) visualStep = 1;

        int[][] selected = new int[4][3];
        int selectedCount = 0;
        int totalWeight = 0;
        if (visualStep >= 2) { selected[selectedCount++] = new int[]{0, 1, 2}; totalWeight += 2; }
        if (visualStep >= 3) { selected[selectedCount++] = new int[]{1, 2, 3}; totalWeight += 3; }
        if (visualStep >= 4) { selected[selectedCount++] = new int[]{1, 4, 5}; totalWeight += 5; }
        if (visualStep >= 5) { selected[selectedCount++] = new int[]{0, 3, 6}; totalWeight += 6; }

        Paint mstPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mstPaint.setStyle(Paint.Style.STROKE);
        mstPaint.setStrokeCap(Paint.Cap.ROUND);
        mstPaint.setStrokeWidth(10f);
        mstPaint.setColor(Color.parseColor("#22C55E"));
        mstPaint.setAlpha(205);
        for (int i = 0; i < selectedCount; i++) {
            int from = selected[i][0];
            int to = selected[i][1];
            if (from >= points.length || to >= points.length) continue;
            canvas.drawLine(points[from][0], points[from][1], points[to][0], points[to][1], mstPaint);
            drawTreeRoleBadge(canvas, (points[from][0] + points[to][0]) / 2f, (points[from][1] + points[to][1]) / 2f + 34f, "MST", "#16A34A");
            drawGraphEdgeWeight(canvas, points, from, to, true);
        }
        if (selectedCount > 0) {
            drawInlineMathBadge(canvas, getWidth() * 0.50f, 58f, "Prim MST edges: " + selectedCount + "/4  •  total = " + totalWeight, "#0F766E");
        }
    }


    private void drawKruskalMstOverlay(Canvas canvas, float[][] points) {
        if (!("graph_kruskal".equals(techniqueId) || "graph_kruskals".equals(techniqueId))) {
            return;
        }
        String message = inlineTeachingMessage == null ? "" : inlineTeachingMessage;
        int visualStep = 0;
        if (message.contains("Step 7")) visualStep = 7;
        else if (message.contains("Step 6")) visualStep = 6;
        else if (message.contains("Step 5")) visualStep = 5;
        else if (message.contains("Step 4")) visualStep = 4;
        else if (message.contains("Step 3")) visualStep = 3;
        else if (message.contains("Step 2")) visualStep = 2;
        else if (message.contains("Step 1")) visualStep = 1;

        int[][] selected = new int[4][3];
        int selectedCount = 0;
        int totalWeight = 0;
        if (visualStep >= 1) { selected[selectedCount++] = new int[]{1, 2, 1}; totalWeight += 1; }
        if (visualStep >= 2) { selected[selectedCount++] = new int[]{2, 3, 2}; totalWeight += 2; }
        if (visualStep >= 4) { selected[selectedCount++] = new int[]{0, 1, 4}; totalWeight += 4; }
        if (visualStep >= 6) { selected[selectedCount++] = new int[]{1, 4, 6}; totalWeight += 6; }

        Paint mstPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mstPaint.setStyle(Paint.Style.STROKE);
        mstPaint.setStrokeCap(Paint.Cap.ROUND);
        mstPaint.setStrokeWidth(10f);
        mstPaint.setColor(Color.parseColor("#22C55E"));
        mstPaint.setAlpha(205);
        for (int i = 0; i < selectedCount; i++) {
            int from = selected[i][0];
            int to = selected[i][1];
            if (from >= points.length || to >= points.length) continue;
            canvas.drawLine(points[from][0], points[from][1], points[to][0], points[to][1], mstPaint);
            drawTreeRoleBadge(canvas, (points[from][0] + points[to][0]) / 2f, (points[from][1] + points[to][1]) / 2f + 34f, "MST", "#16A34A");
            drawGraphEdgeWeight(canvas, points, from, to, true);
        }

        if ((visualStep == 3 || visualStep == 5) && points.length > 3) {
            Paint rejectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            rejectPaint.setStyle(Paint.Style.STROKE);
            rejectPaint.setStrokeCap(Paint.Cap.ROUND);
            rejectPaint.setStrokeWidth(10f);
            rejectPaint.setColor(Color.parseColor("#EF4444"));
            rejectPaint.setAlpha(215);
            int rejectFrom = visualStep == 5 ? 0 : 1;
            int rejectTo = 3;
            canvas.drawLine(points[rejectFrom][0], points[rejectFrom][1], points[rejectTo][0], points[rejectTo][1], rejectPaint);
            drawTreeRoleBadge(canvas, (points[rejectFrom][0] + points[rejectTo][0]) / 2f, (points[rejectFrom][1] + points[rejectTo][1]) / 2f - 34f, "Cycle", "#DC2626");
            drawGraphEdgeWeight(canvas, points, rejectFrom, rejectTo, true);
        }

        String decisionText;
        if (visualStep == 0) {
            decisionText = "Rule: sort + DSU no-cycle";
        } else if (visualStep == 1) {
            decisionText = "Accept: 1-2 is lightest";
        } else if (visualStep == 2) {
            decisionText = "Accept: new component";
        } else if (visualStep == 3) {
            decisionText = "Reject: cycle found";
        } else if (visualStep == 4) {
            decisionText = "Accept: adds vertex 0";
        } else if (visualStep == 5) {
            decisionText = "Reject: cycle found";
        } else if (visualStep == 6) {
            decisionText = "Accept: adds vertex 4";
        } else {
            decisionText = "Done: total = 13";
        }
        if (selectedCount > 0) {
            drawInlineMathBadge(canvas, getWidth() * 0.50f, 58f, "Kruskal MST edges: " + selectedCount + "/4  •  total = " + totalWeight, "#0F766E");
        }
        drawInlineMathBadge(canvas, getWidth() * 0.50f, 104f, decisionText, (visualStep == 3 || visualStep == 5) ? "#DC2626" : "#7C3AED");
        drawKruskalSortedEdgeStrip(canvas, visualStep);
    }

    private void drawKruskalSortedEdgeStrip(Canvas canvas, int visualStep) {
        String[] labels = {"1-2", "2-3", "1-3", "0-1", "0-3", "1-4"};
        int[] weights = {1, 2, 3, 4, 5, 6};
        float boxWidth = Math.max(70f, Math.min(92f, (getWidth() - 48f) / labels.length - 8f));
        float gap = 8f;
        float totalWidth = (boxWidth * labels.length) + (gap * (labels.length - 1));
        float startX = (getWidth() - totalWidth) / 2f;
        float top = getHeight() - 86f;
        Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint stroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        stroke.setStyle(Paint.Style.STROKE);
        stroke.setStrokeWidth(2f);
        Paint labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        labelPaint.setTextAlign(Paint.Align.CENTER);
        labelPaint.setColor(Color.WHITE);
        labelPaint.setTextSize(16f);
        for (int i = 0; i < labels.length; i++) {
            int orderStep = i + 1;
            boolean accepted = (visualStep >= 1 && i == 0) || (visualStep >= 2 && i == 1) || (visualStep >= 4 && i == 3) || (visualStep >= 6 && i == 5);
            boolean rejected = (visualStep >= 3 && i == 2) || (visualStep >= 5 && i == 4);
            boolean current = visualStep == orderStep;
            int color = accepted ? Color.parseColor("#16A34A") : rejected ? Color.parseColor("#DC2626") : current ? Color.parseColor("#7C3AED") : Color.parseColor("#1E293B");
            fill.setColor(color);
            fill.setAlpha(current || accepted || rejected ? 235 : 185);
            stroke.setColor(lightenColor(color, 0.20f));
            float left = startX + (i * (boxWidth + gap));
            float right = left + boxWidth;
            canvas.drawRoundRect(left, top, right, top + 54f, 14f, 14f, fill);
            canvas.drawRoundRect(left, top, right, top + 54f, 14f, 14f, stroke);
            canvas.drawText(labels[i], (left + right) / 2f, top + 22f, labelPaint);
            String status = accepted ? "✓" : rejected ? "×" : "w=" + weights[i];
            canvas.drawText(status, (left + right) / 2f, top + 42f, labelPaint);
        }
    }


    void drawAStarScene(Canvas canvas) {
        boolean compact = isCompactGraphLayout();
        float width = getWidth();
        float height = getHeight();
        if (width <= 0 || height <= 0) {
            return;
        }

        // 1. Resolve active A* visual step (1 to 5)
        String msg = inlineTeachingMessage == null ? "" : inlineTeachingMessage;
        int visualStep = 1;
        if (msg.contains("Step 5") || (dijkstraSettled != null && dijkstraSettled.length > 4 && dijkstraSettled[4])) {
            visualStep = 5;
        } else if (msg.contains("Step 4") || (dijkstraSettled != null && dijkstraSettled.length > 3 && dijkstraSettled[3])) {
            visualStep = 4;
        } else if (msg.contains("Step 3") || (dijkstraSettled != null && dijkstraSettled.length > 1 && dijkstraSettled[1])) {
            visualStep = 3;
        } else if (msg.contains("Step 2") || (dijkstraDistances != null && dijkstraDistances.length > 1 && dijkstraDistances[1] == 2)) {
            visualStep = 2;
        } else {
            visualStep = 1;
        }

        // 2. Exact A* metrics: Heuristic estimate h(n) and g-scores
        int[] h = {10, 6, 7, 2, 0};
        int[] gScores = new int[5];
        if (dijkstraDistances != null && dijkstraDistances.length >= 5) {
            for (int i = 0; i < 5; i++) {
                gScores[i] = dijkstraDistances[i];
            }
        } else {
            if (visualStep == 1) gScores = new int[]{0, 9999, 9999, 9999, 9999};
            else if (visualStep == 2) gScores = new int[]{0, 2, 5, 9999, 9999};
            else if (visualStep == 3) gScores = new int[]{0, 2, 5, 5, 9};
            else if (visualStep == 4) gScores = new int[]{0, 2, 5, 5, 7};
            else gScores = new int[]{0, 2, 5, 5, 7};
        }

        // 3. Header & Decision Banner
        float bannerCenterY = compact ? 26f : 32f;
        drawAStarHeaderBanner(canvas, width, bannerCenterY, compact);

        float decisionY = compact ? 62f : 74f;
        drawAStarDecisionBanner(canvas, width, decisionY, visualStep, compact);

        // 4. Spatial 5-Node Graph Layout (Left-to-Right directional flow)
        float graphTop = compact ? 98f : 114f;
        float bottomPanelH = compact ? 76f : 88f;
        float graphBottom = height - bottomPanelH - (compact ? 20f : 26f);
        float midY = (graphTop + graphBottom) / 2f;
        float vertSpan = (graphBottom - graphTop) * 0.38f;

        float[][] points = new float[][]{
            {width * 0.12f, midY},               // Node 0: Start
            {width * 0.37f, midY - vertSpan},    // Node 1: Upper intermediate
            {width * 0.37f, midY + vertSpan},    // Node 2: Lower intermediate
            {width * 0.65f, midY},               // Node 3: Pre-goal junction
            {width * 0.88f, midY}                // Node 4: Goal
        };
        float nodeRadius = compact ? 28f : 35f;

        // 5. Draw Directed Edges with Weight Badges and Highlighting
        drawAStarEdges(canvas, points, nodeRadius, visualStep, compact);

        // 6. Draw Nodes with Badges, Evaluation Metric Pills (h, g, f), and Halos
        drawAStarNodes(canvas, points, nodeRadius, visualStep, h, gScores, compact);

        // 7. Bottom Dual Cards: Open Set (Priority Queue) and Closed Set (Visited)
        drawAStarBottomPanels(canvas, width, height - bottomPanelH - (compact ? 8f : 12f), bottomPanelH, visualStep, compact);

        // Reset text alignment and paint state
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setFakeBoldText(false);
        strokePaint.setPathEffect(null);
        fillPaint.setShader(null);
    }

    private void drawAStarHeaderBanner(Canvas canvas, float width, float centerY, boolean compact) {
        float centerX = width / 2f;
        float halfW = compact ? 150f : 180f;
        float halfH = compact ? 14f : 17f;

        Paint bg = new Paint(Paint.ANTI_ALIAS_FLAG);
        bg.setColor(Color.parseColor("#0F172A"));
        bg.setAlpha(220);
        Paint border = new Paint(Paint.ANTI_ALIAS_FLAG);
        border.setStyle(Paint.Style.STROKE);
        border.setStrokeWidth(1.5f);
        border.setColor(Color.parseColor("#38BDF8"));
        border.setAlpha(180);

        canvas.drawRoundRect(centerX - halfW, centerY - halfH, centerX + halfW, centerY + halfH, 14f, 14f, bg);
        canvas.drawRoundRect(centerX - halfW, centerY - halfH, centerX + halfW, centerY + halfH, 14f, 14f, border);

        Paint tp = new Paint(Paint.ANTI_ALIAS_FLAG);
        tp.setTextAlign(Paint.Align.CENTER);
        tp.setColor(Color.parseColor("#38BDF8"));
        tp.setTextSize(compact ? 13f : 15f);
        tp.setFakeBoldText(true);
        canvas.drawText("A* SEARCH: f(n) = g(n) + h(n)", centerX, centerY + (compact ? 4.5f : 5.5f), tp);
    }

    private void drawAStarDecisionBanner(Canvas canvas, float width, float centerY, int visualStep, boolean compact) {
        float centerX = width / 2f;
        String text;
        String colorHex;
        if (visualStep == 1) {
            text = "Step 1: Init Start 0 | g=0, h=10 ⇒ f=10. Put into Open Set.";
            colorHex = "#2563EB";
        } else if (visualStep == 2) {
            text = "Step 2: Expand 0 | Node 1 (f=2+6=8) vs Node 2 (f=5+7=12) → Pop Node 1";
            colorHex = "#7C3AED";
        } else if (visualStep == 3) {
            text = "Step 3: Expand 1 | Node 3 (f=5+2=7) beats Node 4 (f=9) & 2 (f=12) → Pop Node 3";
            colorHex = "#7C3AED";
        } else if (visualStep == 4) {
            text = "Step 4: Expand 3 | Route 3→4 updates Goal 4 to g=7, h=0 ⇒ f=7 → Pop Goal 4";
            colorHex = "#D97706";
        } else {
            text = "Step 5: Goal Reached! Optimal Path: 0 → 1 → 3 → 4  (Total Cost = 7)";
            colorHex = "#059669";
        }

        Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
        fill.setColor(Color.parseColor(colorHex));
        fill.setAlpha(220);
        Paint border = new Paint(Paint.ANTI_ALIAS_FLAG);
        border.setStyle(Paint.Style.STROKE);
        border.setStrokeWidth(1.5f);
        border.setColor(lightenColor(Color.parseColor(colorHex), 0.25f));

        float textW = text.length() * (compact ? 5.0f : 6.0f);
        float halfW = Math.max(140f, Math.min(width * 0.46f, textW));
        float halfH = compact ? 15f : 18f;

        canvas.drawRoundRect(centerX - halfW, centerY - halfH, centerX + halfW, centerY + halfH, 14f, 14f, fill);
        canvas.drawRoundRect(centerX - halfW, centerY - halfH, centerX + halfW, centerY + halfH, 14f, 14f, border);

        Paint tp = new Paint(Paint.ANTI_ALIAS_FLAG);
        tp.setTextAlign(Paint.Align.CENTER);
        tp.setColor(Color.WHITE);
        tp.setTextSize(compact ? 13f : 15f);
        tp.setFakeBoldText(true);
        canvas.drawText(text, centerX, centerY + (compact ? 4.5f : 5.5f), tp);
    }

    private void drawAStarEdges(Canvas canvas, float[][] points, float nodeRadius, int visualStep, boolean compact) {
        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {2, 3}, {3, 4}, {1, 4}};
        int[] weights = {2, 5, 3, 2, 2, 7};

        Paint edgePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        edgePaint.setStrokeCap(Paint.Cap.ROUND);

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            int w = weights[i];

            boolean isPathEdge = (u == 0 && v == 1 && visualStep >= 2)
                || (u == 1 && v == 3 && visualStep >= 3)
                || (u == 3 && v == 4 && visualStep >= 4);

            boolean isEvaluating = (u == activeEdgeFrom && v == activeEdgeTo);

            float startX = points[u][0];
            float startY = points[u][1];
            float endX = points[v][0];
            float endY = points[v][1];

            // Outer glow and main line
            if (isPathEdge) {
                edgePaint.setStyle(Paint.Style.STROKE);
                edgePaint.setStrokeWidth(compact ? 14f : 18f);
                edgePaint.setColor(Color.parseColor("#10B981"));
                edgePaint.setAlpha(60);
                canvas.drawLine(startX, startY, endX, endY, edgePaint);

                edgePaint.setStrokeWidth(compact ? 6f : 8f);
                edgePaint.setColor(Color.parseColor("#10B981"));
                edgePaint.setAlpha(240);
                canvas.drawLine(startX, startY, endX, endY, edgePaint);
                drawAStarArrowHead(canvas, startX, startY, endX, endY, nodeRadius, edgePaint);
            } else if (isEvaluating) {
                edgePaint.setStyle(Paint.Style.STROKE);
                edgePaint.setStrokeWidth(compact ? 12f : 15f);
                edgePaint.setColor(Color.parseColor("#F59E0B"));
                edgePaint.setAlpha(65);
                canvas.drawLine(startX, startY, endX, endY, edgePaint);

                edgePaint.setStrokeWidth(compact ? 5f : 7f);
                edgePaint.setColor(Color.parseColor("#F59E0B"));
                edgePaint.setAlpha(235);
                canvas.drawLine(startX, startY, endX, endY, edgePaint);
                drawAStarArrowHead(canvas, startX, startY, endX, endY, nodeRadius, edgePaint);
            } else {
                edgePaint.setStyle(Paint.Style.STROKE);
                edgePaint.setStrokeWidth(compact ? 2.5f : 3.5f);
                edgePaint.setColor(Color.parseColor("#475569"));
                edgePaint.setAlpha(160);
                canvas.drawLine(startX, startY, endX, endY, edgePaint);
                drawAStarArrowHead(canvas, startX, startY, endX, endY, nodeRadius, edgePaint);
            }

            // Draw Weight badge in middle of edge
            float midX = (startX + endX) / 2f;
            float midY = (startY + endY) / 2f;
            if (u == 1 && v == 4) {
                midY -= (compact ? 14f : 18f);
            } else if (u == 0 && v == 2) {
                midX -= (compact ? 10f : 14f);
            } else if (u == 0 && v == 1) {
                midX -= (compact ? 10f : 14f);
            }

            drawAStarWeightBadge(canvas, midX, midY, w, isPathEdge, isEvaluating, compact);
        }
    }

    private void drawAStarArrowHead(Canvas canvas, float startX, float startY, float endX, float endY, float nodeRadius, Paint paint) {
        float angle = (float) Math.atan2(endY - startY, endX - startX);
        float tipX = endX - ((float) Math.cos(angle) * (nodeRadius + 4f));
        float tipY = endY - ((float) Math.sin(angle) * (nodeRadius + 4f));
        float arrowSize = 14f;

        Paint ap = new Paint(paint);
        ap.setStyle(Paint.Style.STROKE);
        ap.setStrokeCap(Paint.Cap.ROUND);
        float leftX = tipX - ((float) Math.cos(angle - Math.PI / 6f) * arrowSize);
        float leftY = tipY - ((float) Math.sin(angle - Math.PI / 6f) * arrowSize);
        float rightX = tipX - ((float) Math.cos(angle + Math.PI / 6f) * arrowSize);
        float rightY = tipY - ((float) Math.sin(angle + Math.PI / 6f) * arrowSize);
        canvas.drawLine(tipX, tipY, leftX, leftY, ap);
        canvas.drawLine(tipX, tipY, rightX, rightY, ap);
    }

    private void drawAStarWeightBadge(Canvas canvas, float x, float y, int weight, boolean isPath, boolean isEval, boolean compact) {
        Paint bg = new Paint(Paint.ANTI_ALIAS_FLAG);
        bg.setColor(isPath ? Color.parseColor("#064E3B") : isEval ? Color.parseColor("#78350F") : Color.parseColor("#0F172A"));
        bg.setAlpha(235);
        Paint stroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        stroke.setStyle(Paint.Style.STROKE);
        stroke.setStrokeWidth(1.5f);
        stroke.setColor(isPath ? Color.parseColor("#10B981") : isEval ? Color.parseColor("#F59E0B") : Color.parseColor("#64748B"));

        float halfW = compact ? 15f : 18f;
        float halfH = compact ? 11f : 13f;
        canvas.drawRoundRect(x - halfW, y - halfH, x + halfW, y + halfH, 8f, 8f, bg);
        canvas.drawRoundRect(x - halfW, y - halfH, x + halfW, y + halfH, 8f, 8f, stroke);

        Paint tp = new Paint(Paint.ANTI_ALIAS_FLAG);
        tp.setTextAlign(Paint.Align.CENTER);
        tp.setColor(isPath ? Color.parseColor("#A7F3D0") : isEval ? Color.parseColor("#FDE68A") : Color.WHITE);
        tp.setTextSize(compact ? 13f : 15f);
        tp.setFakeBoldText(true);
        canvas.drawText(String.valueOf(weight), x, y + (compact ? 4.5f : 5.5f), tp);
    }

    private void drawAStarNodes(Canvas canvas, float[][] points, float nodeRadius, int visualStep,
                                int[] h, int[] gScores, boolean compact) {
        for (int i = 0; i < points.length; i++) {
            float cx = points[i][0];
            float cy = points[i][1];

            boolean isStart = (i == 0);
            boolean isGoal = (i == 4);

            boolean isSettled = (i == 0 && visualStep >= 2)
                || (i == 1 && visualStep >= 3)
                || (i == 3 && visualStep >= 4)
                || (i == 4 && visualStep >= 5);

            boolean isActive = (visualStep == 1 && i == 0)
                || (visualStep == 2 && i == 1)
                || (visualStep == 3 && i == 3)
                || (visualStep == 4 && i == 4);

            boolean isOpen = !isSettled && (
                (visualStep >= 1 && i == 0)
                || (visualStep >= 2 && (i == 1 || i == 2))
                || (visualStep >= 3 && (i == 2 || i == 3 || i == 4))
                || (visualStep >= 4 && (i == 2 || i == 4))
            );

            // Halo effect for active or completed path nodes
            if (isActive) {
                Paint haloPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                haloPaint.setStyle(Paint.Style.STROKE);
                haloPaint.setStrokeWidth(isGoal ? 8f : 6f);
                haloPaint.setColor(isGoal ? Color.parseColor("#10B981") : Color.parseColor("#F59E0B"));
                haloPaint.setAlpha(150);
                float haloR = nodeRadius + 5f + (pulseProgress * 6f);
                canvas.drawCircle(cx, cy, haloR, haloPaint);
            } else if (visualStep == 5 && (i == 0 || i == 1 || i == 3 || i == 4)) {
                Paint pathGlow = new Paint(Paint.ANTI_ALIAS_FLAG);
                pathGlow.setStyle(Paint.Style.STROKE);
                pathGlow.setStrokeWidth(5f);
                pathGlow.setColor(Color.parseColor("#34D399"));
                pathGlow.setAlpha(120);
                canvas.drawCircle(cx, cy, nodeRadius + 5f, pathGlow);
            }

            // Fill color
            int baseColor;
            int strokeColor;
            if (isActive) {
                baseColor = isGoal ? Color.parseColor("#059669") : Color.parseColor("#D97706");
                strokeColor = isGoal ? Color.parseColor("#6EE7B7") : Color.parseColor("#FDE68A");
            } else if (isSettled) {
                baseColor = Color.parseColor("#047857");
                strokeColor = Color.parseColor("#34D399");
            } else if (isOpen) {
                baseColor = Color.parseColor("#3730A3");
                strokeColor = Color.parseColor("#A5B4FC");
            } else {
                baseColor = Color.parseColor("#1E293B");
                strokeColor = Color.parseColor("#475569");
            }

            applyNodeShader(cx, cy, baseColor);
            canvas.drawCircle(cx, cy, nodeRadius, fillPaint);

            strokePaint.setStyle(Paint.Style.STROKE);
            strokePaint.setColor(strokeColor);
            strokePaint.setStrokeWidth(isActive ? 3.5f : 2.2f);
            canvas.drawCircle(cx, cy, nodeRadius, strokePaint);

            // Node ID Number in Center
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setColor(Color.WHITE);
            textPaint.setTextSize(compact ? 19f : 23f);
            textPaint.setFakeBoldText(true);
            canvas.drawText(String.valueOf(i), cx, cy + (compact ? 7f : 8f), textPaint);
            textPaint.setFakeBoldText(false);

            // Badge above node: Role / State
            String roleText;
            String roleColor;
            if (isStart) {
                roleText = isSettled ? "START ✓" : "START";
                roleColor = "#10B981";
            } else if (isGoal) {
                roleText = (visualStep >= 4) ? "GOAL ★" : "GOAL";
                roleColor = (visualStep >= 4) ? "#10B981" : "#F59E0B";
            } else if (isActive) {
                roleText = "MIN f";
                roleColor = "#D97706";
            } else if (isSettled) {
                roleText = "CLOSED";
                roleColor = "#047857";
            } else if (isOpen) {
                roleText = "OPEN";
                roleColor = "#6366F1";
            } else {
                roleText = null;
                roleColor = null;
            }

            if (roleText != null) {
                drawAStarRoleBadge(canvas, cx, cy - nodeRadius - (compact ? 12f : 14f), roleText, roleColor, compact);
            }

            // Metric Pill below node: h(n) and f(n) = g(n) + h(n)
            int gVal = gScores[i];
            int hVal = h[i];
            String metricText;
            if (gVal >= 9999) {
                metricText = "h=" + hVal + " • f=∞";
            } else {
                metricText = "g=" + gVal + " h=" + hVal + " ⇒ f=" + (gVal + hVal);
            }
            drawAStarMetricPill(canvas, cx, cy + nodeRadius + (compact ? 14f : 16f), metricText, isActive || isSettled, compact);
        }
    }

    private void drawAStarRoleBadge(Canvas canvas, float centerX, float centerY, String label, String colorHex, boolean compact) {
        Paint bg = new Paint(Paint.ANTI_ALIAS_FLAG);
        bg.setColor(Color.parseColor(colorHex));
        bg.setAlpha(225);
        Paint stroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        stroke.setStyle(Paint.Style.STROKE);
        stroke.setStrokeWidth(1.5f);
        stroke.setColor(lightenColor(Color.parseColor(colorHex), 0.25f));

        float halfW = Math.max(compact ? 22f : 26f, label.length() * (compact ? 4.0f : 4.8f));
        float halfH = compact ? 9f : 11f;

        canvas.drawRoundRect(centerX - halfW, centerY - halfH, centerX + halfW, centerY + halfH, 6f, 6f, bg);
        canvas.drawRoundRect(centerX - halfW, centerY - halfH, centerX + halfW, centerY + halfH, 6f, 6f, stroke);

        Paint tp = new Paint(Paint.ANTI_ALIAS_FLAG);
        tp.setTextAlign(Paint.Align.CENTER);
        tp.setColor(Color.WHITE);
        tp.setTextSize(compact ? 10f : 12f);
        tp.setFakeBoldText(true);
        canvas.drawText(label, centerX, centerY + (compact ? 3.5f : 4f), tp);
    }

    private void drawAStarMetricPill(Canvas canvas, float centerX, float centerY, String text, boolean isHighlighted, boolean compact) {
        Paint bg = new Paint(Paint.ANTI_ALIAS_FLAG);
        bg.setColor(Color.parseColor("#0F172A"));
        bg.setAlpha(235);
        Paint stroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        stroke.setStyle(Paint.Style.STROKE);
        stroke.setStrokeWidth(1.5f);
        stroke.setColor(isHighlighted ? Color.parseColor("#38BDF8") : Color.parseColor("#334155"));

        float halfW = Math.max(compact ? 36f : 44f, text.length() * (compact ? 3.4f : 4.2f));
        float halfH = compact ? 10f : 12f;

        canvas.drawRoundRect(centerX - halfW, centerY - halfH, centerX + halfW, centerY + halfH, 6f, 6f, bg);
        canvas.drawRoundRect(centerX - halfW, centerY - halfH, centerX + halfW, centerY + halfH, 6f, 6f, stroke);

        Paint tp = new Paint(Paint.ANTI_ALIAS_FLAG);
        tp.setTextAlign(Paint.Align.CENTER);
        tp.setColor(isHighlighted ? Color.parseColor("#FCD34D") : Color.parseColor("#CBD5E1"));
        tp.setTextSize(compact ? 10.5f : 12f);
        tp.setFakeBoldText(isHighlighted);
        canvas.drawText(text, centerX, centerY + (compact ? 3.5f : 4f), tp);
    }

    private void drawAStarBottomPanels(Canvas canvas, float width, float panelTop, float panelH, int visualStep, boolean compact) {
        float padding = compact ? 12f : 18f;
        float gap = compact ? 8f : 12f;
        float availableW = width - (padding * 2f);
        float openW = availableW * 0.62f;
        float closedW = availableW - openW - gap;

        float openLeft = padding;
        float openRight = openLeft + openW;
        float closedLeft = openRight + gap;
        float closedRight = closedLeft + closedW;
        float bottomY = panelTop + panelH;

        // Panel 1: Open Set (Priority Queue - Min-Heap by f)
        Paint panelBg = new Paint(Paint.ANTI_ALIAS_FLAG);
        panelBg.setColor(Color.parseColor("#0F172A"));
        panelBg.setAlpha(235);
        Paint panelBorder = new Paint(Paint.ANTI_ALIAS_FLAG);
        panelBorder.setStyle(Paint.Style.STROKE);
        panelBorder.setStrokeWidth(1.5f);
        panelBorder.setColor(Color.parseColor("#1E293B"));

        canvas.drawRoundRect(openLeft, panelTop, openRight, bottomY, 12f, 12f, panelBg);
        canvas.drawRoundRect(openLeft, panelTop, openRight, bottomY, 12f, 12f, panelBorder);

        Paint headerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        headerPaint.setTextAlign(Paint.Align.LEFT);
        headerPaint.setColor(Color.parseColor("#38BDF8"));
        headerPaint.setTextSize(compact ? 11f : 13f);
        headerPaint.setFakeBoldText(true);
        canvas.drawText("OPEN SET (Min-Heap by f = g + h)", openLeft + 10f, panelTop + (compact ? 16f : 18f), headerPaint);

        // Open Set Items
        String[] openNodeLabels;
        String[] openNodeMetrics;
        int minFIndex;

        if (visualStep == 1) {
            openNodeLabels = new String[]{"Node 0"};
            openNodeMetrics = new String[]{"g=0, h=10 ⇒ f=10"};
            minFIndex = 0;
        } else if (visualStep == 2) {
            openNodeLabels = new String[]{"Node 1 (POP)", "Node 2"};
            openNodeMetrics = new String[]{"2+6 = f=8", "5+7 = f=12"};
            minFIndex = 0;
        } else if (visualStep == 3) {
            openNodeLabels = new String[]{"Node 3 (POP)", "Node 4", "Node 2"};
            openNodeMetrics = new String[]{"5+2 = f=7", "9+0 = f=9", "5+7 = f=12"};
            minFIndex = 0;
        } else if (visualStep == 4) {
            openNodeLabels = new String[]{"Node 4 (GOAL)", "Node 2"};
            openNodeMetrics = new String[]{"7+0 = f=7", "5+7 = f=12"};
            minFIndex = 0;
        } else {
            openNodeLabels = new String[0];
            openNodeMetrics = new String[0];
            minFIndex = -1;
        }

        if (openNodeLabels.length == 0) {
            Paint emptyPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            emptyPaint.setTextAlign(Paint.Align.CENTER);
            emptyPaint.setColor(Color.parseColor("#34D399"));
            emptyPaint.setTextSize(compact ? 12f : 14f);
            emptyPaint.setFakeBoldText(true);
            canvas.drawText("✓ Search Complete — Goal Reached!", (openLeft + openRight) / 2f, panelTop + (panelH * 0.62f), emptyPaint);
        } else {
            float itemGap = compact ? 6f : 8f;
            float totalItemGap = (openNodeLabels.length - 1) * itemGap;
            float itemBoxW = (openW - 20f - totalItemGap) / openNodeLabels.length;
            float itemBoxH = compact ? 38f : 44f;
            float itemTop = panelTop + (compact ? 24f : 28f);

            for (int k = 0; k < openNodeLabels.length; k++) {
                float boxLeft = openLeft + 10f + (k * (itemBoxW + itemGap));
                float boxRight = boxLeft + itemBoxW;
                float boxBottom = itemTop + itemBoxH;

                boolean isMin = (k == minFIndex);

                Paint itemBg = new Paint(Paint.ANTI_ALIAS_FLAG);
                itemBg.setColor(isMin ? Color.parseColor("#78350F") : Color.parseColor("#1E293B"));
                itemBg.setAlpha(isMin ? 230 : 180);

                Paint itemStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
                itemStroke.setStyle(Paint.Style.STROKE);
                itemStroke.setStrokeWidth(isMin ? 2f : 1f);
                itemStroke.setColor(isMin ? Color.parseColor("#F59E0B") : Color.parseColor("#475569"));

                canvas.drawRoundRect(boxLeft, itemTop, boxRight, boxBottom, 8f, 8f, itemBg);
                canvas.drawRoundRect(boxLeft, itemTop, boxRight, boxBottom, 8f, 8f, itemStroke);

                Paint itemText = new Paint(Paint.ANTI_ALIAS_FLAG);
                itemText.setTextAlign(Paint.Align.CENTER);
                itemText.setColor(isMin ? Color.parseColor("#FCD34D") : Color.WHITE);
                itemText.setTextSize(compact ? 11f : 12.5f);
                itemText.setFakeBoldText(isMin);
                canvas.drawText(openNodeLabels[k], (boxLeft + boxRight) / 2f, itemTop + (compact ? 15f : 17f), itemText);

                itemText.setColor(isMin ? Color.parseColor("#FEF08A") : Color.parseColor("#94A3B8"));
                itemText.setTextSize(compact ? 9.5f : 11f);
                itemText.setFakeBoldText(false);
                canvas.drawText(openNodeMetrics[k], (boxLeft + boxRight) / 2f, itemTop + (compact ? 30f : 34f), itemText);
            }
        }

        // Panel 2: Closed Set (Visited / Settled Nodes)
        canvas.drawRoundRect(closedLeft, panelTop, closedRight, bottomY, 12f, 12f, panelBg);
        canvas.drawRoundRect(closedLeft, panelTop, closedRight, bottomY, 12f, 12f, panelBorder);

        Paint closedHeader = new Paint(Paint.ANTI_ALIAS_FLAG);
        closedHeader.setTextAlign(Paint.Align.LEFT);
        closedHeader.setColor(Color.parseColor("#34D399"));
        closedHeader.setTextSize(compact ? 11f : 13f);
        closedHeader.setFakeBoldText(true);
        canvas.drawText("CLOSED SET (Visited)", closedLeft + 10f, panelTop + (compact ? 16f : 18f), closedHeader);

        int[] settledNodes;
        if (visualStep == 1) settledNodes = new int[0];
        else if (visualStep == 2) settledNodes = new int[]{0};
        else if (visualStep == 3) settledNodes = new int[]{0, 1};
        else if (visualStep == 4) settledNodes = new int[]{0, 1, 3};
        else settledNodes = new int[]{0, 1, 3, 4};

        if (settledNodes.length == 0) {
            Paint emptyClosed = new Paint(Paint.ANTI_ALIAS_FLAG);
            emptyClosed.setTextAlign(Paint.Align.CENTER);
            emptyClosed.setColor(Color.parseColor("#64748B"));
            emptyClosed.setTextSize(compact ? 11f : 13f);
            canvas.drawText("— Empty —", (closedLeft + closedRight) / 2f, panelTop + (panelH * 0.62f), emptyClosed);
        } else {
            float chipW = compact ? 26f : 32f;
            float chipH = compact ? 22f : 26f;
            float chipY = panelTop + (compact ? 24f : 28f);
            float chipStartX = closedLeft + 10f;
            float chipGap = compact ? 6f : 8f;

            for (int m = 0; m < settledNodes.length; m++) {
                float cL = chipStartX + (m * (chipW + chipGap));
                float cR = cL + chipW;
                float cB = chipY + chipH;

                Paint chipBg = new Paint(Paint.ANTI_ALIAS_FLAG);
                chipBg.setColor(Color.parseColor("#064E3B"));
                Paint chipBorder = new Paint(Paint.ANTI_ALIAS_FLAG);
                chipBorder.setStyle(Paint.Style.STROKE);
                chipBorder.setStrokeWidth(1.5f);
                chipBorder.setColor(Color.parseColor("#10B981"));

                canvas.drawRoundRect(cL, chipY, cR, cB, 6f, 6f, chipBg);
                canvas.drawRoundRect(cL, chipY, cR, cB, 6f, 6f, chipBorder);

                Paint chipText = new Paint(Paint.ANTI_ALIAS_FLAG);
                chipText.setTextAlign(Paint.Align.CENTER);
                chipText.setColor(Color.WHITE);
                chipText.setTextSize(compact ? 11f : 12.5f);
                chipText.setFakeBoldText(true);
                canvas.drawText("✓" + settledNodes[m], (cL + cR) / 2f, chipY + (compact ? 15f : 17f), chipText);
            }
        }
    }

    private void drawGraphOperationStage(Canvas canvas, float[][] points) {
        String lower = inlineTeachingMessage == null ? "" : inlineTeachingMessage.toLowerCase();
        if ("insert_node_graph".equals(techniqueId) && values.length > 0) {
            int nodeIndex = Math.min(values.length - 1, points.length - 1);
            String label = lower.contains("scanning") ? "Create" : lower.contains("inserted") ? "Settle" : "Connect";
            drawTreeRoleBadge(canvas, points[nodeIndex][0], points[nodeIndex][1] - 56f, label, "#16A34A");
        } else if ("delete_vertex_graph".equals(techniqueId) && activeIndex >= 0 && activeIndex < points.length) {
            String label = lower.contains("remove") ? "Delete" : "Locate";
            drawTreeRoleBadge(canvas, points[activeIndex][0], points[activeIndex][1] - 56f, label, "#DC2626");
        }
    }

    private void drawGraphOrTreeWhyBubble(Canvas canvas, float[][] points, boolean graphMode) {
        if (inlineTeachingMessage == null || inlineTeachingMessage.trim().isEmpty()) {
            return;
        }
        String why = resolveInlineWhyBubble();
        if (why.isEmpty()) {
            return;
        }
        float x;
        float y;
        if (graphMode && activeEdgeFrom >= 0 && activeEdgeTo >= 0 && activeEdgeFrom < points.length && activeEdgeTo < points.length) {
            x = (points[activeEdgeFrom][0] + points[activeEdgeTo][0]) / 2f;
            y = (points[activeEdgeFrom][1] + points[activeEdgeTo][1]) / 2f - 48f;
        } else {
            int focus = activeIndex >= 0 && activeIndex < points.length ? activeIndex : (secondaryIndex >= 0 && secondaryIndex < points.length ? secondaryIndex : 0);
            x = points[focus][0];
            y = points[focus][1] - 62f;
        }
        drawInlineMathBadge(canvas, x, y, why, graphMode ? "#7C3AED" : "#0F766E");
    }

    private String resolveInlineWhyBubble() {
        String safe = inlineTeachingMessage.toLowerCase();
        if (GraphExpandedStepEngine.isExpandedGraphTechnique(techniqueId)) {
            if ("graph_bellman_ford".equals(techniqueId)) {
                if (safe.contains("step 1")) return "init: source=0, others INF";
                if (safe.contains("step 2")) return "relax: 0 + (-1) < INF";
                if (safe.contains("step 3")) return "dist[1] improves neighbors";
                if (safe.contains("step 4")) return "negative edge improves dist[3]";
                if (safe.contains("step 5")) return "no smaller value found";
                if (safe.contains("step 6")) return "no negative cycle";
            }
            if ("graph_floyd_warshall".equals(techniqueId)) {
                if (safe.contains("step 1")) return "try each k as middle";
                if (safe.contains("step 2")) return "3-0-1 beats INF";
                if (safe.contains("step 3")) return "0-1-2 beats INF";
                if (safe.contains("step 4")) return "0-2-3 improves 7";
                if (safe.contains("step 5")) return "all k tested";
            }
            if ("graph_kruskal".equals(techniqueId) || "graph_kruskals".equals(techniqueId)) {
                if (safe.contains("step 1")) return "accept: lightest safe edge";
                if (safe.contains("step 2")) return "accept: different components";
                if (safe.contains("step 3")) return "reject: cycle";
                if (safe.contains("step 4")) return "accept: connects vertex 0";
                if (safe.contains("step 5")) return "accept: connects vertex 4";
                if (safe.contains("step 6")) return "MST has |V|-1 edges";
            }
            if ("graph_tarjan_scc".equals(techniqueId)) {
                if (safe.contains("step 1")) return "push active DFS node";
                if (safe.contains("step 2")) return "tree edge continues DFS";
                if (safe.contains("step 3")) return "back edge lowers low[]";
                if (safe.contains("step 4")) return "low==tin: pop SCC";
                if (safe.contains("step 5")) return "3 and 4 reach each other";
                if (safe.contains("step 6")) return "all SCC roots processed";
            }
            if ("graph_kosaraju_scc".equals(techniqueId)) {
                if (safe.contains("step 1")) return "pass 1 records finish order";
                if (safe.contains("step 2")) return "cycle finishes together";
                if (safe.contains("step 3")) return "reverse graph";
                if (safe.contains("step 4")) return "DFS in transpose gives SCC";
                if (safe.contains("step 5")) return "next SCC {3,4}";
                if (safe.contains("step 6")) return "stack empty";
            }
            if ("graph_bridges_articulation".equals(techniqueId) || "graph_bridges".equals(techniqueId)) {
                if (safe.contains("step 1")) return "DFS tin/low starts";
                if (safe.contains("step 2")) return "no back edge from 2";
                if (safe.contains("step 3")) return "low[2] > tin[1]";
                if (safe.contains("step 4")) return "removing 1 disconnects";
                if (safe.contains("step 5")) return "removing 3 disconnects 4";
                if (safe.contains("step 6")) return "low-link tests done";
            }
            if ("graph_astar".equals(techniqueId) || "graph_a_star".equals(techniqueId)) {
                if (safe.contains("step 1")) return "f=g+h selects frontier";
                if (safe.contains("step 2")) return "node 1 has smaller f";
                if (safe.contains("step 3")) return "node 3 has best f";
                if (safe.contains("step 4")) return "goal path cost becomes 7";
                if (safe.contains("step 5")) return "goal popped with min f";
            }
            if ("graph_edmonds_karp".equals(techniqueId)) {
                if (safe.contains("step 1")) return "BFS path bottleneck=10";
                if (safe.contains("step 2")) return "augment by bottleneck";
                if (safe.contains("step 3")) return "second BFS bottleneck=8";
                if (safe.contains("step 4")) return "no residual path to sink";
            }
            if ("graph_johnson".equals(techniqueId) || "graph_johnsons".equals(techniqueId)) {
                if (safe.contains("step 1")) return "negative edges need reweighting";
                if (safe.contains("step 2")) return "Bellman-Ford finds h(v)";
                if (safe.contains("step 3")) return "w' is non-negative";
                if (safe.contains("step 4")) return "Dijkstra is now safe";
                if (safe.contains("step 5")) return "restore original distances";
            }
        }
        if (safe.contains("because")) {
            String because = inlineTeachingMessage.substring(safe.indexOf("because"));
            return because.length() > 42 ? because.substring(0, 42) + "..." : because;
        }
        if (safe.contains("settled")) return "final shortest";
        if (safe.contains("cycle-free")) return "avoid cycle";
        if (safe.contains("prerequisite")) return "incoming done";
        if (safe.contains("heap")) return "restore order";
        if (safe.contains("remove")) return "disconnect links";
        return "";
    }

    private boolean isLinkedTechnique() {
        return "linked_queue".equals(techniqueId)
            || techniqueId.contains("linked_list")
            || "linked_stack_push".equals(techniqueId)
            || "linked_stack_pop".equals(techniqueId);
    }

    private void drawEmptyStructureState(Canvas canvas, String mode) {
        float centerX = getWidth() / 2f;
        float centerY = getHeight() * 0.48f;
        Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(8f);
        linePaint.setColor(lightenColor(resolveTechniqueBaseColor(), 0.22f));
        linePaint.setAlpha(190);
        Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
        fill.setStyle(Paint.Style.FILL);
        fill.setColor(resolveTechniqueBaseColor());
        fill.setAlpha(55);

        if ("stack".equals(mode)) {
            canvas.drawRoundRect(centerX - 86f, centerY - 120f, centerX + 86f, centerY + 96f, 26f, 26f, fill);
            canvas.drawRoundRect(centerX - 86f, centerY - 120f, centerX + 86f, centerY + 96f, 26f, 26f, linePaint);
            drawTreeRoleBadge(canvas, centerX, centerY - 144f, "Empty Stack", "#64748B");
        } else if ("circular".equals(mode) || "circular_header".equals(mode)) {
            canvas.drawCircle(centerX, centerY, 104f, linePaint);
            drawTreeRoleBadge(canvas, centerX, centerY - 134f, "Empty Ring", "#0F766E");
        } else {
            canvas.drawRoundRect(centerX - 160f, centerY - 44f, centerX + 160f, centerY + 44f, 30f, 30f, fill);
            canvas.drawRoundRect(centerX - 160f, centerY - 44f, centerX + 160f, centerY + 44f, 30f, 30f, linePaint);
            if ("linked".equals(mode)) {
                drawAnimatedConnector(canvas, centerX - 94f, centerY, centerX + 94f, centerY, true);
                drawTreeRoleBadge(canvas, centerX, centerY - 74f, "Empty Linked", "#9333EA");
            } else {
                drawTreeRoleBadge(canvas, centerX, centerY - 74f, "Empty Queue", "#2563EB");
            }
        }
    }

    private void drawHeapArrayStrip(Canvas canvas) {
        boolean compact = isCompactTreeLayout();
        float top = getHeight() * (compact ? 0.76f : 0.80f);
        float cellWidth = compact
            ? resolveHorizontalCellWidth(Math.max(0f, getWidth() - 24f))
            : resolveHorizontalCellWidth();
        float boxWidth = compact ? Math.max(44f, cellWidth - 14f) : cellWidth - CELL_GAP;
        float boxHeight = compact ? 58f : 66f;
        for (int i = 0; i < values.length; i++) {
            float left = (compact ? 12f : 24f) + (i * cellWidth);
            float right = left + boxWidth;
            applyBoxShader(left, top, right, top + boxHeight, i);
            canvas.drawRoundRect(left, top, right, top + boxHeight, 18f, 18f, fillPaint);
            canvas.drawRoundRect(left, top, right, top + boxHeight, 18f, 18f, strokePaint);
            textPaint.setTextSize(compact ? 18f : 22f);
            canvas.drawText(String.valueOf(values[i]), (left + right) / 2f, top + (compact ? 35f : 40f), textPaint);
        }
        drawHeapIndices(canvas, compact ? 12f : 24f, cellWidth, boxWidth, top, boxHeight, compact);
    }

    private int resolveMatrixColor(int row, int col) {
        if (row == matrixRow && col == matrixCol) {
            return Color.parseColor("#F59E0B");
        }
        if (row == matrixK || col == matrixK) {
            return Color.parseColor("#0EA5E9");
        }
        return Color.parseColor("#1E293B");
    }

    private void drawMergeOutput(Canvas canvas, int[] outputValues, float startX, float topY, float cellHeight) {
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(28f);
        canvas.drawText("Merged Output", startX, topY - 20f, textPaint);
        textPaint.setTextAlign(Paint.Align.CENTER);
        for (int i = 0; i < outputValues.length; i++) {
            float left = startX + (i * 74f);
            float right = left + 60f;
            int baseColor = i <= mergeWriteIndex ? Color.parseColor("#16A34A") : Color.parseColor("#334155");
            int bright = lightenColor(baseColor, 0.20f);
            float animatedTop = topY;
            if ("merge_sort".equals(techniqueId) && i <= mergeWriteIndex) {
                animatedTop = topY - ((1f - transitionProgress) * 56f);
            }
            fillPaint.setShader(new LinearGradient(left, animatedTop, right, animatedTop + cellHeight, bright, baseColor, Shader.TileMode.CLAMP));
            canvas.drawRoundRect(left, animatedTop, right, animatedTop + 64f, 18f, 18f, fillPaint);
            canvas.drawRoundRect(left, animatedTop, right, animatedTop + 64f, 18f, 18f, strokePaint);
            textPaint.setTextSize(24f);
            if (i <= mergeWriteIndex && outputValues[i] != Integer.MIN_VALUE) {
                canvas.drawText(String.valueOf(outputValues[i]), (left + right) / 2f, animatedTop + 40f, textPaint);
            } else {
                canvas.drawText("_", (left + right) / 2f, animatedTop + 40f, textPaint);
            }
        }
        drawMergeTransferOverlay(canvas, startX, topY);
    }

    private void drawMiniValueBox(Canvas canvas, int value, float left, float top, float right, float bottom, boolean active) {
        int baseColor = active ? Color.parseColor("#F59E0B") : Color.parseColor("#1E3A5F");
        int brighter = lightenColor(baseColor, 0.24f);
        fillPaint.setShader(new LinearGradient(left, top, right, bottom, brighter, baseColor, Shader.TileMode.CLAMP));
        canvas.drawRoundRect(left, top, right, bottom, 14f, 14f, fillPaint);
        canvas.drawRoundRect(left, top, right, bottom, 14f, 14f, strokePaint);
        textPaint.setTextSize(20f);
        canvas.drawText(String.valueOf(value), (left + right) / 2f, top + ((bottom - top) * 0.62f), textPaint);
    }

    private float resolveRadixDropOffset(int bucket, int position) {
        if (bucket != radixActiveBucket) {
            return 0f;
        }
        float stagger = Math.max(0f, pulseProgress - (position * 0.08f));
        float normalized = Math.min(1f, stagger / 0.92f);
        float eased = 1f - ((1f - normalized) * (1f - normalized));
        return (1f - eased) * 64f;
    }

    private void drawUnionCompressionTravel(Canvas canvas, float startX, float startY, float endX, float endY, int node) {
        boolean onCompressionPath = unionFocusNode == node || unionParents[node] == unionRootNode;
        if (!onCompressionPath) {
            return;
        }
        Paint glowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        glowPaint.setColor(Color.parseColor("#FCD34D"));
        glowPaint.setAlpha(180);
        float t = 0.12f + (pulseProgress * 0.76f);
        float dotX = lerp(startX, endX, t);
        float dotY = lerp(startY, endY, t);
        canvas.drawCircle(dotX, dotY, 8f + (pulseProgress * 4f), glowPaint);
    }

    private void drawTrieConnectorReveal(Canvas canvas, float startX, float startY, float endX, float endY, int level) {
        boolean revealed = activeIndex >= level + 1;
        Paint pathPaint = new Paint(strokePaint);
        if (revealed) {
            pathPaint.setColor(Color.parseColor("#38BDF8"));
            pathPaint.setStrokeWidth(7f);
            float revealProgress = activeIndex == level + 1 ? pulseProgress : 1f;
            float currentY = lerp(startY, endY, revealProgress);
            canvas.drawLine(startX, startY, endX, currentY, pathPaint);
            Paint dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            dotPaint.setColor(Color.parseColor("#FCD34D"));
            canvas.drawCircle(endX, currentY, 7f + (pulseProgress * 3f), dotPaint);
            return;
        }
        drawAnimatedConnector(canvas, startX, startY, endX, endY, false);
    }

    private void drawTrieLetterReveal(Canvas canvas, float x, float y, String label, boolean revealed, int level) {
        if (!revealed) {
            drawLabeledNode(canvas, x, y, label, false);
            return;
        }
        float revealScale = activeIndex == level + 1 ? 0.92f + (pulseProgress * 0.12f) : 1f;
        int saveCount = canvas.save();
        canvas.scale(revealScale, revealScale, x, y);
        drawLabeledNode(canvas, x, y, label, activeIndex == level + 1);
        canvas.restoreToCount(saveCount);
    }

    private void drawMatrixCellPulse(Canvas canvas, float left, float top, float right, float bottom, int row, int col) {
        if (row != matrixRow || col != matrixCol) {
            return;
        }
        Paint pulsePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pulsePaint.setStyle(Paint.Style.FILL);
        pulsePaint.setColor(Color.parseColor("#FCD34D"));
        pulsePaint.setAlpha((int) (76f * (1f - matrixHighlightProgress)));
        float inset = 18f * (1f - matrixHighlightProgress);
        canvas.drawRoundRect(left - inset, top - inset, right + inset, bottom + inset, 24f, 24f, pulsePaint);
    }

    private void drawCompletedPassSweep(Canvas canvas, float padding, float cellWidth, float baseWidth, float top, float bottom) {
        if (!"bubble_sort".equals(techniqueId) && !"selection_sort".equals(techniqueId) && !"insertion_sort".equals(techniqueId)) {
            return;
        }
        if (completedStart < 0 || completedEnd < completedStart) {
            return;
        }
        float left = padding + (completedStart * cellWidth) - 4f;
        float right = padding + (completedEnd * cellWidth) + baseWidth + 4f;
        float sweepX = lerp(left, right, pulseProgress);
        Paint glowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        glowPaint.setShader(new LinearGradient(
            sweepX - 36f,
            top,
            sweepX + 36f,
            bottom,
            Color.argb(0, 134, 239, 172),
            Color.argb(165, 134, 239, 172),
            Shader.TileMode.CLAMP
        ));
        canvas.drawRoundRect(left, top - 8f, right, bottom + 8f, 28f, 28f, glowPaint);
        Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.parseColor("#86EFAC"));
        linePaint.setAlpha(150);
        linePaint.setStrokeWidth(5f);
        canvas.drawLine(sweepX, top - 6f, sweepX, bottom + 6f, linePaint);
    }

    private void drawMergeTransferOverlay(Canvas canvas, float outputStartX, float outputTopY) {
        if (mergeTransferTargetIndex < 0 || mergeTransferValue == Integer.MIN_VALUE) {
            return;
        }
        float leftRowStart = 24f;
        float leftSourceX = leftRowStart + (Math.max(0, mergeTransferSourceIndex) * 74f) + 30f;
        float rightRowStart = leftRowStart + (Math.max(mergeLeftValues.length, 1) * 74f) + 64f;
        float sourceX = (mergeTransferFromLeft ? leftSourceX : rightRowStart + (Math.max(0, mergeTransferSourceIndex) * 74f) + 30f);
        float sourceY = getHeight() * 0.16f + 27f;
        float targetX = outputStartX + (mergeTransferTargetIndex * 74f) + 30f;
        float targetY = outputTopY + 32f;
        float currentX = lerp(sourceX, targetX, mergeTransferProgress);
        float currentY = lerp(sourceY, targetY, mergeTransferProgress);
        float width = 60f;
        float height = 54f;
        float left = currentX - (width / 2f);
        float top = currentY - (height / 2f);
        int baseColor = mergeTransferFromLeft ? Color.parseColor("#0EA5E9") : Color.parseColor("#8B5CF6");
        int bright = lightenColor(baseColor, 0.24f);
        softGlowPaint.setShader(null);
        softGlowPaint.setColor(baseColor);
        softGlowPaint.setAlpha(Math.round(70f * (1f - Math.abs((mergeTransferProgress * 2f) - 1f))));
        canvas.drawRoundRect(left - 10f, top - 10f, left + width + 10f, top + height + 10f, 22f, 22f, softGlowPaint);
        fillPaint.setShader(new LinearGradient(left, top, left + width, top + height, bright, baseColor, Shader.TileMode.CLAMP));
        canvas.drawRoundRect(left, top, left + width, top + height, 18f, 18f, fillPaint);
        canvas.drawRoundRect(left, top, left + width, top + height, 18f, 18f, strokePaint);
        textPaint.setTextSize(22f);
        canvas.drawText(String.valueOf(mergeTransferValue), currentX, top + 34f, textPaint);
    }

    private void drawSwapBoxes(Canvas canvas, float padding, float cellWidth, float baseWidth, float top, float bottom) {
        if (!swapAnimating || swapFromIndex < 0 || swapToIndex < 0 || previousValues.length != values.length) {
            return;
        }

        float fromLeft = padding + (swapFromIndex * cellWidth);
        float toLeft = padding + (swapToIndex * cellWidth);
        float currentLeftA = lerp(fromLeft, toLeft, swapProgress);
        float currentLeftB = lerp(toLeft, fromLeft, swapProgress);
        float archA = (float) Math.sin(Math.PI * swapProgress) * 55f;
        float archB = (float) Math.sin(Math.PI * swapProgress) * 40f;

        // Draw curved neon arch swap trail path (glowing trajectory)
        Paint archPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        archPaint.setStyle(Paint.Style.STROKE);
        archPaint.setStrokeWidth(6f);
        archPaint.setShader(new LinearGradient(fromLeft, top, toLeft, top, Color.parseColor("#38BDF8"), Color.parseColor("#C084FC"), Shader.TileMode.CLAMP));
        
        android.graphics.Path arcPath = new android.graphics.Path();
        float midX = (fromLeft + toLeft + baseWidth) / 2f;
        float peakY = top - 110f;
        arcPath.moveTo(fromLeft + baseWidth / 2f, top - 20f);
        arcPath.quadTo(midX, peakY, toLeft + baseWidth / 2f, top - 20f);
        canvas.drawPath(arcPath, archPaint);

        // Draw glowing wave pulse/ripples at the top peak of the arc
        Paint pulseTrailPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pulseTrailPaint.setStyle(Paint.Style.STROKE);
        pulseTrailPaint.setStrokeWidth(3f);
        pulseTrailPaint.setColor(Color.parseColor("#38BDF8"));
        pulseTrailPaint.setAlpha((int) ((1f - swapProgress) * 140));
        canvas.drawCircle(midX, peakY + 10f, 12f + swapProgress * 22f, pulseTrailPaint);

        // Draw Box A (with tilt and scale pop)
        canvas.save();
        float centerX_A = currentLeftA + baseWidth / 2f;
        float centerY_A = (top - archA + bottom - archA) / 2f;
        float angleA = (float) Math.sin(Math.PI * swapProgress) * -12f;
        canvas.rotate(angleA, centerX_A, centerY_A);
        float scaleA = 1f + (float) Math.sin(Math.PI * swapProgress) * 0.08f;
        canvas.scale(scaleA, scaleA, centerX_A, centerY_A);
        drawSwapBox(canvas, previousValues[swapFromIndex], currentLeftA, top - archA, currentLeftA + baseWidth, bottom - archA, Color.parseColor("#C084FC")); // Purple neon box
        canvas.restore();

        // Draw Box B (with tilt and scale pop)
        canvas.save();
        float centerX_B = currentLeftB + baseWidth / 2f;
        float centerY_B = (top - archB + bottom - archB) / 2f;
        float angleB = (float) Math.sin(Math.PI * swapProgress) * 12f;
        canvas.rotate(angleB, centerX_B, centerY_B);
        float scaleB = 1f + (float) Math.sin(Math.PI * swapProgress) * 0.08f;
        canvas.scale(scaleB, scaleB, centerX_B, centerY_B);
        drawSwapBox(canvas, previousValues[swapToIndex], currentLeftB, top - archB, currentLeftB + baseWidth, bottom - archB, Color.parseColor("#38BDF8")); // Cyan neon box
        canvas.restore();
    }

    private void drawSwapBox(Canvas canvas, int value, float left, float top, float right, float bottom, int baseColor) {
        // Dark glass background
        int fillA = Color.argb(200, 15, 23, 42);
        int fillB = Color.argb(200, 30, 41, 59);
        fillPaint.setShader(new LinearGradient(left, top, right, bottom, fillA, fillB, Shader.TileMode.CLAMP));

        // Soft outer glow of matching color
        softGlowPaint.setShader(null);
        softGlowPaint.setColor(baseColor);
        softGlowPaint.setAlpha(110);
        canvas.drawRoundRect(left - 12f, top - 12f, right + 12f, bottom + 12f, 30f, 30f, softGlowPaint);
        
        // Draw Glass background
        canvas.drawRoundRect(left, top, right, bottom, 24f, 24f, fillPaint);
        
        // Draw Glowing Neon outline
        Paint swapStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        swapStrokePaint.setStyle(Paint.Style.STROKE);
        swapStrokePaint.setStrokeWidth(6f);
        swapStrokePaint.setShader(new LinearGradient(left, top, right, bottom, baseColor, lightenColor(baseColor, 0.35f), Shader.TileMode.CLAMP));
        canvas.drawRoundRect(left, top, right, bottom, 24f, 24f, swapStrokePaint);

        // Draw Value text
        textPaint.setColor(Color.WHITE);
        textPaint.setFakeBoldText(true);
        textPaint.setTextSize(resolveValueTextSize());
        canvas.drawText(String.valueOf(value), (left + right) / 2f, top + (((bottom - top)) * 0.58f), textPaint);
    }

    private void drawComparisonBeam(Canvas canvas, float padding, float cellWidth, float baseWidth, float top, float bottom) {
        if (activeIndex < 0 || secondaryIndex < 0 || activeIndex >= values.length || secondaryIndex >= values.length) {
            return;
        }

        float firstX = padding + (activeIndex * cellWidth) + (baseWidth / 2f);
        float secondX = padding + (secondaryIndex * cellWidth) + (baseWidth / 2f);
        float beamY = bottom + 22f + (pulseProgress * 6f);

        Paint beamPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        beamPaint.setStyle(Paint.Style.STROKE);
        beamPaint.setStrokeWidth(7f);
        beamPaint.setShader(new LinearGradient(
            firstX,
            beamY,
            secondX,
            beamY,
            Color.parseColor("#F59E0B"),
            Color.parseColor("#A855F7"),
            Shader.TileMode.CLAMP
        ));

        canvas.drawLine(firstX, beamY, secondX, beamY, beamPaint);

        Paint dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dotPaint.setColor(Color.parseColor("#E2E8F0"));
        float travel = lerp(firstX, secondX, pulseProgress);
        canvas.drawCircle(travel, beamY, 8f, dotPaint);
    }

    private void drawPartitionSweep(Canvas canvas, float padding, float cellWidth, float baseWidth, float top, float bottom) {
        if (!"quicksort".equals(techniqueId) || activeIndex < 0 || secondaryIndex < 0 || activeIndex >= values.length || secondaryIndex >= values.length) {
            return;
        }

        float leftIndex = Math.min(activeIndex, secondaryIndex);
        float rightIndex = Math.max(activeIndex, secondaryIndex);
        float sweepLeft = padding + (leftIndex * cellWidth) - 6f;
        float sweepRight = padding + (rightIndex * cellWidth) + baseWidth + 6f;
        float sweepY = bottom + 34f;

        Paint sweepPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        sweepPaint.setStrokeWidth(10f);
        sweepPaint.setShader(new LinearGradient(
            sweepLeft,
            sweepY,
            sweepRight,
            sweepY,
            Color.parseColor("#F59E0B"),
            Color.parseColor("#38BDF8"),
            Shader.TileMode.CLAMP
        ));
        sweepPaint.setAlpha(150);
        canvas.drawLine(sweepLeft, sweepY, sweepRight, sweepY, sweepPaint);

        Paint sweepDot = new Paint(Paint.ANTI_ALIAS_FLAG);
        sweepDot.setColor(Color.parseColor("#F8FAFC"));
        float sweepX = lerp(sweepLeft, sweepRight, pulseProgress);
        canvas.drawCircle(sweepX, sweepY, 8f + (pulseProgress * 3f), sweepDot);
    }

    private void drawBinarySearchRange(Canvas canvas, float padding, float cellWidth, float baseWidth, float top, float bottom) {
        if (!"binary_search".equals(techniqueId)) {
            return;
        }
        if (!"low".equalsIgnoreCase(primaryPointerLabel) || primaryPointerIndex < 0 || secondaryPointerIndex < 0) {
            return;
        }

        int lowIndex = Math.min(primaryPointerIndex, secondaryPointerIndex);
        int highIndex = Math.max(primaryPointerIndex, secondaryPointerIndex);
        if (lowIndex >= values.length || highIndex >= values.length) {
            return;
        }

        float animatedLow = resolveAnimatedPointerIndex(lowIndex, true);
        float animatedHigh = resolveAnimatedPointerIndex(highIndex, false);
        float left = padding + (animatedLow * cellWidth) - 10f;
        float right = padding + (animatedHigh * cellWidth) + baseWidth + 10f;
        float fullLeft = padding - 10f;
        float fullRight = padding + ((values.length - 1) * cellWidth) + baseWidth + 10f;

        Paint rangeFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        rangeFill.setColor(Color.parseColor("#0EA5E9"));
        rangeFill.setAlpha(32);
        canvas.drawRoundRect(left, top - 22f, right, bottom + 26f, 30f, 30f, rangeFill);

        Paint rangeStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        rangeStroke.setStyle(Paint.Style.STROKE);
        rangeStroke.setStrokeWidth(6f);
        rangeStroke.setColor(Color.parseColor("#38BDF8"));
        rangeStroke.setAlpha(140);
        canvas.drawRoundRect(left, top - 22f, right, bottom + 26f, 30f, 30f, rangeStroke);

        Paint discardedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        discardedPaint.setColor(Color.parseColor("#020617"));
        discardedPaint.setAlpha(Math.round(90f * binaryRangeFadeProgress));
        if (left > fullLeft + 4f) {
            canvas.drawRoundRect(fullLeft, top - 18f, left - 6f, bottom + 22f, 24f, 24f, discardedPaint);
        }
        if (right < fullRight - 4f) {
            canvas.drawRoundRect(right + 6f, top - 18f, fullRight, bottom + 22f, 24f, 24f, discardedPaint);
        }

        if (activeIndex >= 0 && activeIndex < values.length) {
            float midCenter = padding + (resolveAnimatedMidIndex() * cellWidth) + (baseWidth / 2f);
            Paint midBeam = new Paint(Paint.ANTI_ALIAS_FLAG);
            midBeam.setStrokeWidth(8f);
            midBeam.setColor(Color.parseColor("#FCD34D"));
            midBeam.setAlpha(180);
            canvas.drawLine(midCenter, top - 40f, midCenter, bottom + 18f, midBeam);
        }

        drawRangeBrace(canvas, left + 10f, right - 10f, bottom + 30f, "search range");
    }

    private void drawJumpSearchRange(Canvas canvas, float padding, float cellWidth, float baseWidth, float top, float bottom) {
        if (!"jump_search".equals(techniqueId)) {
            return;
        }
        if (values.length == 0 || primaryPointerIndex < 0) {
            return;
        }
        int startIndex = secondaryPointerIndex >= 0 ? secondaryPointerIndex : Math.max(0, primaryPointerIndex);
        int endIndex = Math.max(startIndex, Math.min(values.length - 1, primaryPointerIndex));
        float left = padding + (startIndex * cellWidth) - 8f;
        float right = padding + (endIndex * cellWidth) + baseWidth + 8f;
        Paint blockFill = new Paint(Paint.ANTI_ALIAS_FLAG);
        blockFill.setColor(Color.parseColor("#2563EB"));
        blockFill.setAlpha(24);
        canvas.drawRoundRect(left, top - 12f, right, bottom + 12f, 24f, 24f, blockFill);
        drawRangeBrace(canvas, left + 8f, right - 8f, bottom + 30f, "jump block");
    }

    private void drawMergeRunBraces(Canvas canvas, float leftStart, float rightStart, float topRowY) {
        if (mergeLeftValues.length > 0) {
            float leftEnd = leftStart + ((mergeLeftValues.length - 1) * 74f) + 60f;
            drawRangeBrace(canvas, leftStart, leftEnd, topRowY + 72f, "left run");
        }
        if (mergeRightValues.length > 0) {
            float rightEnd = rightStart + ((mergeRightValues.length - 1) * 74f) + 60f;
            drawRangeBrace(canvas, rightStart, rightEnd, topRowY + 72f, "right run");
        }
    }

    private void drawRangeBrace(Canvas canvas, float left, float right, float y, String label) {
        if (right <= left) {
            return;
        }
        Paint bracePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bracePaint.setColor(Color.parseColor("#93C5FD"));
        bracePaint.setStrokeWidth(4f);
        bracePaint.setStyle(Paint.Style.STROKE);
        float notch = Math.min(18f, (right - left) * 0.16f);
        canvas.drawLine(left, y, left, y + 12f, bracePaint);
        canvas.drawLine(left, y + 12f, left + notch, y + 12f, bracePaint);
        canvas.drawLine(left + notch, y + 12f, right - notch, y + 12f, bracePaint);
        canvas.drawLine(right - notch, y + 12f, right, y + 12f, bracePaint);
        canvas.drawLine(right, y + 12f, right, y, bracePaint);

        Paint labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        labelPaint.setColor(Color.parseColor("#DBEAFE"));
        labelPaint.setTextAlign(Paint.Align.CENTER);
        labelPaint.setTextSize(18f);
        canvas.drawText(label, (left + right) / 2f, y + 30f, labelPaint);
    }

    private void drawInlineArrayMath(Canvas canvas, float padding, float cellWidth, float baseWidth, float top, float bottom) {
        if ("linear_search".equals(techniqueId) && activeIndex >= 0 && activeIndex < values.length) {
            float centerX = padding + (activeIndex * cellWidth) + (baseWidth / 2f);
            drawInlineMathBadge(
                canvas,
                centerX,
                top - 88f,
                "arr[" + activeIndex + "] = " + values[activeIndex],
                "#2563EB"
            );
            if (inlineTeachingTarget != Integer.MIN_VALUE && inlineTeachingMessage.toLowerCase().contains("compar")) {
                drawInlineMathBadge(
                    canvas,
                    centerX,
                    top - 58f,
                    "item " + inlineTeachingTarget + " ? " + values[activeIndex],
                    "#0F766E"
                );
            }
            return;
        }
        if ("binary_search".equals(techniqueId)
            && primaryPointerIndex >= 0
            && secondaryPointerIndex >= 0
            && activeIndex >= 0
            && activeIndex < values.length) {
            float centerX = padding + (resolveAnimatedMidIndex() * cellWidth) + (baseWidth / 2f);
            int low = Math.min(primaryPointerIndex, secondaryPointerIndex);
            int high = Math.max(primaryPointerIndex, secondaryPointerIndex);
            String safe = inlineTeachingMessage == null ? "" : inlineTeachingMessage.toLowerCase();
            drawInlineMathBadge(canvas, centerX, top - 98f, "(" + low + " + " + high + ") / 2 = " + activeIndex, "#2563EB");
            if (safe.contains("middle value")) {
                drawInlineMathBadge(canvas, centerX, top - 68f, "data = arr[mid] = " + values[activeIndex], "#0F766E");
            } else if (safe.contains("target is greater")) {
                drawInlineMathBadge(canvas, centerX, top - 68f, "item " + inlineTeachingTarget + " > data " + values[activeIndex], "#0F766E");
                drawInlineMathBadge(canvas, centerX, top - 38f, "beg = mid + 1 = " + low, "#D97706");
            } else if (safe.contains("target is smaller")) {
                drawInlineMathBadge(canvas, centerX, top - 68f, "item " + inlineTeachingTarget + " < data " + values[activeIndex], "#0F766E");
                drawInlineMathBadge(canvas, centerX, top - 38f, "end = mid - 1 = " + high, "#D97706");
            } else if (safe.contains("found")) {
                drawInlineMathBadge(canvas, centerX, top - 68f, "item = data = " + inlineTeachingTarget, "#16A34A");
            }
            return;
        }
        if ("jump_search".equals(techniqueId) && values.length > 0 && primaryPointerIndex >= 0) {
            int startIndex = secondaryPointerIndex >= 0 ? secondaryPointerIndex : Math.max(0, primaryPointerIndex);
            int endIndex = Math.max(startIndex, Math.min(values.length - 1, primaryPointerIndex));
            float centerX = padding + (((startIndex + endIndex) / 2f) * cellWidth) + (baseWidth / 2f);
            int block = Math.max(1, (int) Math.floor(Math.sqrt(values.length)));
            drawInlineMathBadge(
                canvas,
                centerX,
                top - 88f,
                "sqrt(" + values.length + ") -> " + block,
                "#2563EB"
            );
        }
    }

    private void drawInlineDijkstraMath(Canvas canvas, float[][] points, boolean compact, float distanceTop) {
        if (!("dijkstra_algorithm".equals(techniqueId) || "shortest_paths".equals(techniqueId))) {
            return;
        }
        if (activeEdgeFrom < 0 || activeEdgeTo < 0 || activeEdgeFrom >= points.length || activeEdgeTo >= points.length) {
            return;
        }
        if (dijkstraDistances.length <= activeEdgeFrom) {
            return;
        }
        int fromDist = dijkstraDistances[activeEdgeFrom];
        if (fromDist >= 9999) {
            return;
        }
        int weight = resolveGraphEdgeWeight(activeEdgeFrom, activeEdgeTo);
        if (weight <= 0) {
            return;
        }
        float midX = (points[activeEdgeFrom][0] + points[activeEdgeTo][0]) / 2f;
        float midY = (points[activeEdgeFrom][1] + points[activeEdgeTo][1]) / 2f;
        float badgeY = Math.min(distanceTop - (compact ? 42f : 48f), midY - 18f);
        drawInlineMathBadge(
            canvas,
            midX,
            badgeY,
            fromDist + " + " + weight + " = " + (fromDist + weight),
            "#0F766E"
        );
    }

    private int resolveGraphEdgeWeight(int from, int to) {
        if (GraphExpandedStepEngine.isExpandedGraphTechnique(techniqueId)) {
            int expanded = GraphExpandedStepEngine.getWeight(techniqueId, from, to);
            return expanded == -1 ? Integer.MIN_VALUE : expanded;
        }
        if ((from == 0 && to == 1) || (from == 1 && to == 0)) return 4;
        if ((from == 1 && to == 2) || (from == 2 && to == 1)) return 3;
        if ((from == 0 && to == 3) || (from == 3 && to == 0)) return 2;
        if ((from == 1 && to == 3) || (from == 3 && to == 1)) return 1;
        if ((from == 1 && to == 4) || (from == 4 && to == 1)) return 5;
        if ((from == 2 && to == 4) || (from == 4 && to == 2)) return 2;
        if ((from == 3 && to == 4) || (from == 4 && to == 3)) return 7;
        return Integer.MIN_VALUE;
    }

    private void drawInlineMathBadge(Canvas canvas, float centerX, float centerY, String label, String colorHex) {
        if (label == null || label.trim().isEmpty()) {
            return;
        }
        float eased = inlineTeachingProgress <= 0f ? 1f : inlineTeachingProgress;
        float animatedY = centerY + ((1f - eased) * 12f);
        int alpha = Math.max(0, Math.min(255, Math.round(235f * eased)));
        Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
        fill.setColor(Color.parseColor(colorHex));
        fill.setAlpha(alpha);
        Paint stroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        stroke.setStyle(Paint.Style.STROKE);
        stroke.setStrokeWidth(2f);
        stroke.setColor(lightenColor(Color.parseColor(colorHex), 0.18f));
        stroke.setAlpha(alpha);
        Paint text = new Paint(Paint.ANTI_ALIAS_FLAG);
        text.setColor(Color.WHITE);
        text.setTextAlign(Paint.Align.CENTER);
        text.setTextSize(20f);
        text.setAlpha(alpha);

        float halfWidth = Math.max(54f, Math.min(156f, label.length() * 5.6f));
        float halfHeight = 18f;
        canvas.drawRoundRect(centerX - halfWidth, animatedY - halfHeight, centerX + halfWidth, animatedY + halfHeight, 14f, 14f, fill);
        canvas.drawRoundRect(centerX - halfWidth, animatedY - halfHeight, centerX + halfWidth, animatedY + halfHeight, 14f, 14f, stroke);
        canvas.drawText(label, centerX, animatedY + 5f, text);
    }

    private void drawDiscardedFlashTrails(Canvas canvas, float padding, float cellWidth, float baseWidth, float top, float bottom) {
        if (!isSearchingTechnique()) {
            return;
        }
        Paint trailPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        for (int i = 0; i < values.length; i++) {
            if (!isBinaryDiscardedIndex(i) && !isJumpDiscardedIndex(i) && !isLinearDiscardedIndex(i)) {
                continue;
            }
            float left = padding + (i * cellWidth);
            float right = left + baseWidth;
            float sweep = lerp(left - 18f, right + 18f, pulseProgress);
            trailPaint.setShader(new LinearGradient(
                sweep - 28f,
                top,
                sweep + 28f,
                bottom,
                Color.argb(0, 96, 165, 250),
                Color.argb(82, 96, 165, 250),
                Shader.TileMode.CLAMP
            ));
            canvas.drawRoundRect(left - 6f, top - 4f, right + 6f, bottom + 4f, 22f, 22f, trailPaint);
        }
        trailPaint.setShader(null);
    }

    private void drawUpdatedEdgeTrail(Canvas canvas, float[][] points) {
        if (!("dijkstra_algorithm".equals(techniqueId) || "shortest_paths".equals(techniqueId))) {
            return;
        }
        if (activeEdgeFrom < 0 || activeEdgeTo < 0 || activeEdgeFrom >= points.length || activeEdgeTo >= points.length) {
            return;
        }
        Paint trailPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        trailPaint.setStyle(Paint.Style.STROKE);
        trailPaint.setStrokeWidth(10f);
        trailPaint.setShader(new LinearGradient(
            points[activeEdgeFrom][0],
            points[activeEdgeFrom][1],
            points[activeEdgeTo][0],
            points[activeEdgeTo][1],
            Color.argb(0, 56, 189, 248),
            Color.argb(150, 56, 189, 248),
            Shader.TileMode.CLAMP
        ));
        canvas.drawLine(points[activeEdgeFrom][0], points[activeEdgeFrom][1], points[activeEdgeTo][0], points[activeEdgeTo][1], trailPaint);

        Paint dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dotPaint.setColor(Color.parseColor("#FCD34D"));
        float dotX = lerp(points[activeEdgeFrom][0], points[activeEdgeTo][0], pulseProgress);
        float dotY = lerp(points[activeEdgeFrom][1], points[activeEdgeTo][1], pulseProgress);
        canvas.drawCircle(dotX, dotY, 7f + (pulseProgress * 3f), dotPaint);
    }

    private void drawSettledFeedback(Canvas canvas, float centerX, float centerY, float radius, int index) {
        if (!("dijkstra_algorithm".equals(techniqueId) || "shortest_paths".equals(techniqueId))) {
            return;
        }
        if (dijkstraSettled.length <= index || !dijkstraSettled[index]) {
            return;
        }
        Paint settledPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        settledPaint.setStyle(Paint.Style.STROKE);
        settledPaint.setStrokeWidth(5f);
        settledPaint.setColor(Color.parseColor("#4ADE80"));
        settledPaint.setAlpha(90);
        float haloRadius = radius + 10f + (pulseProgress * 8f);
        canvas.drawCircle(centerX, centerY, haloRadius, settledPaint);
    }

    private void drawAnimatedConnector(Canvas canvas, float startX, float startY, float endX, float endY, boolean relink) {
        Paint connectorPaint = new Paint(accentPaint);
        connectorPaint.setStrokeWidth(relink ? 7f : 5f);
        connectorPaint.setAlpha(relink ? Math.round(130f + (pulseProgress * 90f)) : 180);
        if (relink) {
            connectorPaint.setColor(Color.parseColor("#60A5FA"));
        }
        canvas.drawLine(startX, startY, endX, endY, connectorPaint);

        Paint dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dotPaint.setColor(relink ? Color.parseColor("#FCD34D") : Color.parseColor("#7DD3FC"));
        float dotX = lerp(startX, endX, pulseProgress);
        float dotY = lerp(startY, endY, pulseProgress);
        canvas.drawCircle(dotX, dotY, (relink ? 8f : 6f) + (pulseProgress * 2f), dotPaint);
    }

    private void drawNodeHalo(Canvas canvas, float centerX, float centerY, float radius, int nodeIndex) {
        if (!isTraversalNodeActive(nodeIndex) && !isTraversalNodeVisited(nodeIndex) && !isTraversalFrontierNode(nodeIndex)) {
            return;
        }

        Paint haloPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        haloPaint.setStyle(Paint.Style.STROKE);
        boolean active = isTraversalNodeActive(nodeIndex);
        boolean frontier = isTraversalFrontierNode(nodeIndex);
        haloPaint.setStrokeWidth(active ? 10f : frontier ? 8f : 6f);
        haloPaint.setColor(active ? Color.parseColor("#FCD34D") : frontier ? Color.parseColor("#60A5FA") : Color.parseColor("#4ADE80"));
        haloPaint.setAlpha(active
            ? Math.round(150f + (pulseProgress * 70f))
            : frontier ? Math.round(120f + (pulseProgress * 50f)) : Math.round(90f + (pulseProgress * 24f)));
        float haloRadius = radius + 12f + (active ? pulseProgress * 10f : frontier ? pulseProgress * 16f : 0f);
        canvas.drawCircle(centerX, centerY, haloRadius, haloPaint);
    }

    private void drawArrayPointers(Canvas canvas, float padding, float cellWidth, float baseWidth, float top) {
        if (primaryPointerIndex >= 0 && primaryPointerIndex < values.length) {
            float previousX = previousPrimaryPointerIndex >= 0 && previousPrimaryPointerIndex < values.length
                ? padding + (previousPrimaryPointerIndex * cellWidth) + (baseWidth / 2f)
                : Float.NaN;
            float x = padding + (resolveAnimatedPointerIndex(primaryPointerIndex, true) * cellWidth) + (baseWidth / 2f);
            drawPointerTrail(canvas, previousX, x, top - 34f, true);
            drawPointer(canvas, x, top - 34f, primaryPointerLabel);
        }

        if (secondaryPointerIndex >= 0 && secondaryPointerIndex < values.length) {
            float previousX = previousSecondaryPointerIndex >= 0 && previousSecondaryPointerIndex < values.length
                ? padding + (previousSecondaryPointerIndex * cellWidth) + (baseWidth / 2f)
                : Float.NaN;
            float x = padding + (resolveAnimatedPointerIndex(secondaryPointerIndex, false) * cellWidth) + (baseWidth / 2f);
            drawPointerTrail(canvas, previousX, x, top - 72f, false);
            drawPointer(canvas, x, top - 72f, secondaryPointerLabel);
        }
    }

    private void drawArrayIndices(Canvas canvas, float padding, float cellWidth, float baseWidth, float bottom) {
        if (!shouldDrawArrayIndices()) {
            return;
        }
        Paint indexPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        indexPaint.setColor(Color.parseColor("#94A3B8"));
        indexPaint.setTextAlign(Paint.Align.CENTER);
        indexPaint.setTextSize(isStructureTechnique() ? 20f : 22f);
        float baseline = bottom + (isStructureTechnique() ? 24f : 28f);
        for (int i = 0; i < values.length; i++) {
            float x = padding + (i * cellWidth) + (baseWidth / 2f);
            canvas.drawText(String.valueOf(i), x, baseline, indexPaint);
        }
    }

    private void drawStackIndices(Canvas canvas, float left, float startBottom, float cellHeight, float gap) {
        if (!shouldDrawStackIndices()) {
            return;
        }
        Paint indexPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        indexPaint.setColor(Color.parseColor("#94A3B8"));
        indexPaint.setTextAlign(Paint.Align.RIGHT);
        indexPaint.setTextSize(20f);
        float labelX = left - 18f;
        for (int i = 0; i < values.length; i++) {
            float bottom = startBottom - (i * (cellHeight + gap));
            float centerY = bottom - (cellHeight / 2f) + 7f;
            canvas.drawText(String.valueOf(i), labelX, centerY, indexPaint);
        }
    }

    private void drawHeapIndices(Canvas canvas, float startX, float cellWidth, float boxWidth, float top, float boxHeight, boolean compact) {
        Paint indexPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        indexPaint.setColor(Color.parseColor("#94A3B8"));
        indexPaint.setTextAlign(Paint.Align.CENTER);
        indexPaint.setTextSize(compact ? 18f : 20f);
        float baseline = top + boxHeight + (compact ? 18f : 20f);
        for (int i = 0; i < values.length; i++) {
            float left = startX + (i * cellWidth);
            canvas.drawText(String.valueOf(i), left + (boxWidth / 2f), baseline, indexPaint);
        }
    }

    private void drawPointer(Canvas canvas, float centerX, float baselineY, String label) {
        Paint pointerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointerPaint.setColor(Color.parseColor("#E2E8F0"));
        pointerPaint.setStrokeWidth(5f);
        canvas.drawLine(centerX, baselineY, centerX, baselineY + 18f, pointerPaint);
        canvas.drawCircle(centerX, baselineY + 20f, 6f, pointerPaint);

        Paint labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        labelPaint.setColor(Color.parseColor("#F8FAFC"));
        labelPaint.setTextAlign(Paint.Align.CENTER);
        labelPaint.setTextSize(24f);
        canvas.drawText(label == null || label.isEmpty() ? "ptr" : label, centerX, baselineY - 8f, labelPaint);
    }

    private void drawPointerTrail(Canvas canvas, float startX, float endX, float baselineY, boolean primary) {
        if (Float.isNaN(startX) || Math.abs(endX - startX) < 10f || pointerMotionProgress >= 0.995f) {
            return;
        }
        Paint trailPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        trailPaint.setStyle(Paint.Style.STROKE);
        trailPaint.setStrokeWidth(primary ? 8f : 6f);
        trailPaint.setStrokeCap(Paint.Cap.ROUND);
        int trailColor = primary ? Color.parseColor("#60A5FA") : Color.parseColor("#A78BFA");
        trailPaint.setShader(new LinearGradient(startX, baselineY, endX, baselineY, Color.argb(30, Color.red(trailColor), Color.green(trailColor), Color.blue(trailColor)), Color.argb(180, Color.red(trailColor), Color.green(trailColor), Color.blue(trailColor)), Shader.TileMode.CLAMP));
        canvas.drawLine(startX, baselineY + 20f, endX, baselineY + 20f, trailPaint);

        Paint dotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dotPaint.setColor(primary ? Color.parseColor("#FCD34D") : Color.parseColor("#C4B5FD"));
        float dotX = lerp(startX, endX, pointerMotionProgress);
        canvas.drawCircle(dotX, baselineY + 20f, (primary ? 7f : 6f) + ((1f - pointerMotionProgress) * 3f), dotPaint);
    }

    private void drawOverlayBadge(Canvas canvas) {
        if (overlayBadgeText == null || overlayBadgeText.trim().isEmpty()) {
            return;
        }
        Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
        fill.setColor(resolveTechniqueBaseColor());
        fill.setAlpha(230);
        Paint outline = new Paint(Paint.ANTI_ALIAS_FLAG);
        outline.setStyle(Paint.Style.STROKE);
        outline.setStrokeWidth(2f);
        outline.setColor(lightenColor(resolveTechniqueBaseColor(), 0.20f));
        Paint labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        labelPaint.setColor(Color.parseColor("#F8FAFC"));
        labelPaint.setTextAlign(Paint.Align.CENTER);
        labelPaint.setTextSize(20f);

        float width = Math.min(getWidth() * 0.52f, Math.max(148f, overlayBadgeText.length() * 10.3f));
        float left = getWidth() - width - 20f;
        float top = 18f;
        float right = getWidth() - 20f;
        float bottom = top + 40f;
        canvas.drawRoundRect(left, top, right, bottom, 18f, 18f, fill);
        canvas.drawRoundRect(left, top, right, bottom, 18f, 18f, outline);
        canvas.drawText(overlayBadgeText, (left + right) / 2f, top + 26f, labelPaint);
    }

    private void drawStructureCardLabel(Canvas canvas, float left, float top, float right, float bottom, int index, boolean verticalStack) {
        String label = resolveStructureCardLabel(index, verticalStack);
        if (label == null) {
            return;
        }

        Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
        fill.setStyle(Paint.Style.FILL);
        fill.setColor(Color.parseColor("#0F172A"));
        fill.setAlpha(170);
        Paint outline = new Paint(Paint.ANTI_ALIAS_FLAG);
        outline.setStyle(Paint.Style.STROKE);
        outline.setStrokeWidth(2f);
        outline.setColor(lightenColor(resolveTechniqueBaseColor(), 0.36f));

        float badgeLeft = left + 8f;
        float badgeTop = top + 8f;
        float badgeWidth = label.length() <= 3 ? 48f : 64f;
        float badgeHeight = 24f;
        canvas.drawRoundRect(badgeLeft, badgeTop, badgeLeft + badgeWidth, badgeTop + badgeHeight, 10f, 10f, fill);
        canvas.drawRoundRect(badgeLeft, badgeTop, badgeLeft + badgeWidth, badgeTop + badgeHeight, 10f, 10f, outline);

        Paint text = new Paint(Paint.ANTI_ALIAS_FLAG);
        text.setColor(Color.parseColor("#E2E8F0"));
        text.setTextSize(22f);
        text.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(label, badgeLeft + (badgeWidth / 2f), badgeTop + 17f, text);
    }

    private float resolveLift(int index) {
        if (index == activeIndex || index == secondaryIndex) {
            return 16f + (pulseProgress * 10f);
        }
        if (index == foundIndex) {
            return 10f + (pulseProgress * 6f);
        }
        return 0f;
    }

    private float resolveStructureTranslationX(int index) {
        if (index != movingStructureIndex) {
            return 0f;
        }

        float eased = resolveStructureEaseProgress();
        float amplitude = 56f;
        if ("enqueue".equals(structureOperation) || "enqueue_right".equals(structureOperation) || "rear_in".equals(structureOperation)) {
            return amplitude * (1f - eased);
        }
        if ("front_in".equals(structureOperation)) {
            return -amplitude * (1f - eased);
        }
        if ("dequeue".equals(structureOperation) || "dequeue_left".equals(structureOperation) || "front_out".equals(structureOperation)) {
            return -amplitude * eased;
        }
        if ("rear_out".equals(structureOperation)) {
            return amplitude * eased;
        }
        return 0f;
    }

    private float resolveStructureTranslationY(int index, boolean verticalMode) {
        if (index != movingStructureIndex) {
            return 0f;
        }

        float eased = resolveStructureEaseProgress();
        float amplitude = 30f;
        if ("push".equals(structureOperation)) {
            float entry = amplitude * (1f - eased);
            return verticalMode ? entry : -entry;
        }
        if ("pop".equals(structureOperation)) {
            float exit = amplitude * eased;
            return verticalMode ? -exit : exit;
        }
        if ("visit".equals(structureOperation) || "link".equals(structureOperation)) {
            return -8f - ((1f - Math.abs((eased * 2f) - 1f)) * 12f);
        }
        return 0f;
    }

    private float resolveCellAlpha(int index) {
        float alpha = 1f;
        if ("binary_search".equals(techniqueId) && isBinaryDiscardedIndex(index)) {
            alpha *= 1f - (0.52f * binaryRangeFadeProgress);
        }
        if (index == movingStructureIndex) {
            if (isStructureEntryOperation()) {
                alpha *= 0.38f + (0.62f * structureMotionProgress);
            } else if (isStructureExitOperation()) {
                alpha *= 1f - (0.62f * structureMotionProgress);
            }
        }
        return Math.max(0.18f, Math.min(1f, alpha));
    }

    private float resolveInsertionShiftX(int index, float cellWidth) {
        if (!"insertion_sort".equals(techniqueId) || activeIndex <= 0 || secondaryIndex < 0 || activeIndex <= secondaryIndex) {
            return 0f;
        }
        float strength = 1f - transitionProgress;
        if (index == activeIndex) {
            float distance = Math.min((activeIndex - secondaryIndex) * cellWidth, cellWidth * 1.1f);
            return -distance * strength;
        }
        if (index >= secondaryIndex && index < activeIndex) {
            return (cellWidth * 0.55f) * strength;
        }
        return 0f;
    }

    private float resolveLinkedDeletionCollapseX(int index, float cellWidth) {
        if (!techniqueId.contains("delete") || !techniqueId.contains("linked_list") || activeIndex < 0) {
            return 0f;
        }
        if (index > activeIndex) {
            return -(cellWidth * 0.42f) * (1f - transitionProgress);
        }
        return 0f;
    }

    private boolean isLinkedRelinkConnector(int connectorIndex) {
        if (!techniqueId.contains("linked_list")
            && !"linked_queue".equals(techniqueId)
            && !techniqueId.contains("circular_header_list")
            && !"linked_stack_push".equals(techniqueId)
            && !"linked_stack_pop".equals(techniqueId)) {
            return false;
        }
        if (!(techniqueId.contains("insert") || techniqueId.contains("delete"))) {
            return false;
        }
        return connectorIndex == activeIndex || connectorIndex + 1 == activeIndex || connectorIndex == secondaryIndex;
    }

    private boolean isCircularRelinkConnector(int connectorIndex, boolean headerNode) {
        if ("circular_queue".equals(techniqueId)) {
            return connectorIndex == movingStructureIndex
                || ((connectorIndex + 1) % Math.max(1, values.length)) == movingStructureIndex;
        }
        if (!headerNode) {
            return false;
        }
        int activeRingIndex = activeIndex < 0 ? -1 : activeIndex + 1;
        int secondaryRingIndex = secondaryIndex < 0 ? -1 : secondaryIndex + 1;
        return connectorIndex == 0
            || connectorIndex == activeRingIndex
            || ((connectorIndex + 1) % Math.max(1, values.length + 1)) == activeRingIndex
            || connectorIndex == secondaryRingIndex;
    }

    private boolean isStructureEntryOperation() {
        return "push".equals(structureOperation)
            || "enqueue".equals(structureOperation)
            || "enqueue_right".equals(structureOperation)
            || "front_in".equals(structureOperation)
            || "rear_in".equals(structureOperation);
    }

    private boolean isStructureExitOperation() {
        return "pop".equals(structureOperation)
            || "dequeue".equals(structureOperation)
            || "dequeue_left".equals(structureOperation)
            || "front_out".equals(structureOperation)
            || "rear_out".equals(structureOperation);
    }

    private boolean isInsertionFocusNode(int nodeIndex, boolean graphMode) {
        String lower = inlineTeachingMessage == null ? "" : inlineTeachingMessage.toLowerCase();
        boolean insertionScene = techniqueId.contains("insert")
            || lower.contains("insert")
            || lower.contains("added")
            || lower.contains("pushed")
            || lower.contains("enqueued");
        if (!insertionScene || nodeIndex < 0) {
            return false;
        }
        if (graphMode && "insert_node_graph".equals(techniqueId)) {
            return nodeIndex == Math.min(Math.max(0, values.length - 1), 5);
        }
        if (activeIndex >= 0) {
            return nodeIndex == activeIndex;
        }
        return nodeIndex == Math.max(0, values.length - 1);
    }

    private float resolveAnimatedPointerIndex(int targetIndex, boolean primary) {
        int previous = primary ? previousPrimaryPointerIndex : previousSecondaryPointerIndex;
        if (targetIndex < 0) {
            return 0f;
        }
        if (previous < 0) {
            return targetIndex;
        }
        return lerp(previous, targetIndex, pointerMotionProgress);
    }

    private float resolveAnimatedMidIndex() {
        if (activeIndex < 0) {
            return 0f;
        }
        if (!"binary_search".equals(techniqueId) || previousPrimaryPointerIndex < 0 || previousSecondaryPointerIndex < 0) {
            return activeIndex;
        }
        float previousMid = (previousPrimaryPointerIndex + previousSecondaryPointerIndex) / 2f;
        return lerp(previousMid, activeIndex, pointerMotionProgress);
    }

    private float resolveNodeRadius(int nodeIndex, float baseRadius) {
        if (isTraversalNodeActive(nodeIndex)) {
            return baseRadius + (pulseProgress * 8f);
        }
        if (isTraversalFrontierNode(nodeIndex)) {
            return baseRadius + (pulseProgress * 5f);
        }
        return baseRadius;
    }

    private boolean isTraversalFrontierNode(int nodeIndex) {
        int nextIndex = traversalStep + 1;
        return nextIndex >= 0 && nextIndex < traversalOrder.length && traversalOrder[nextIndex] == nodeIndex;
    }

    private boolean isTraversalNodeActive(int nodeIndex) {
        return traversalStep >= 0 && traversalStep < traversalOrder.length && traversalOrder[traversalStep] == nodeIndex;
    }

    private boolean isTraversalNodeVisited(int nodeIndex) {
        if (traversalStep < 0) {
            return false;
        }
        for (int i = 0; i < Math.min(traversalStep, traversalOrder.length); i++) {
            if (traversalOrder[i] == nodeIndex) {
                return true;
            }
        }
        return false;
    }

    private boolean isTraversalEdgeActive(int from, int to) {
        if (traversalStep <= 0 || traversalStep >= traversalOrder.length) {
            return false;
        }
        int previous = traversalOrder[traversalStep - 1];
        int current = traversalOrder[traversalStep];
        return (from == previous && to == current) || (from == current && to == previous);
    }

    private boolean isDijkstraGlowEdge(int from, int to) {
        if (dijkstraSettled.length == 0) {
            return false;
        }
        boolean fromSettled = from < dijkstraSettled.length && dijkstraSettled[from];
        boolean toSettled = to < dijkstraSettled.length && dijkstraSettled[to];
        return (fromSettled && to == dijkstraCurrentNode) || (toSettled && from == dijkstraCurrentNode);
    }

    private int resolveFill(int index) {
        if (index == foundIndex) {
            return Color.parseColor("#22C55E");
        }
        if (isCompletedIndex(index)) {
            return Color.parseColor("#16A34A");
        }
        if (isDiscardedIndex(index)) {
            return Color.parseColor("#475569");
        }
        if (index == secondaryIndex) {
            return Color.parseColor("#A855F7");
        }
        if (index == activeIndex) {
            return isSearchingTechnique() ? Color.parseColor("#38BDF8") : Color.parseColor("#F59E0B");
        }
        if (isSearchingTechnique()) {
            return Color.parseColor("#1E3A5F");
        }
        if (isSortingTechnique()) {
            return Color.parseColor("#3A2A1B");
        }
        return resolveTechniqueBaseColor();
    }

    private int resolveTraversalFill(int nodeIndex) {
        if (traversalStep >= 0 && traversalStep < traversalOrder.length && traversalOrder[traversalStep] == nodeIndex) {
            return VisualizationTechniqueRegistry.isTree(techniqueId)
                ? Color.parseColor("#10B981")
                : Color.parseColor("#F59E0B");
        }
        if (traversalStep >= 0) {
            for (int i = 0; i < Math.min(traversalStep, traversalOrder.length); i++) {
                if (traversalOrder[i] == nodeIndex) {
                    return VisualizationTechniqueRegistry.isTree(techniqueId)
                        ? Color.parseColor("#2563EB")
                        : Color.parseColor("#22C55E");
                }
            }
        }
        return VisualizationTechniqueRegistry.isTree(techniqueId)
            ? Color.parseColor("#183A31")
            : Color.parseColor("#1E293B");
    }

    private boolean isCompletedIndex(int index) {
        return completedStart >= 0 && completedEnd >= completedStart && index >= completedStart && index <= completedEnd;
    }

    private boolean isDiscardedIndex(int index) {
        return isBinaryDiscardedIndex(index) || isJumpDiscardedIndex(index) || isLinearDiscardedIndex(index);
    }

    private int resolveTechniqueBaseColor() {
        if ("stack_basics".equals(techniqueId)
            || "linked_stack_push".equals(techniqueId)
            || "linked_stack_pop".equals(techniqueId)) {
            return Color.parseColor("#23324A");
        }
        if ("queue_basics".equals(techniqueId) || "priority_queue_array".equals(techniqueId) || "priority_queue_2d".equals(techniqueId)) {
            return Color.parseColor("#1E3A5F");
        }
        if ("deque".equals(techniqueId)) {
            return Color.parseColor("#1F4D4F");
        }
        if ("linked_queue".equals(techniqueId)
            || "singly_linked_list".equals(techniqueId)
            || "traversing_linked_list".equals(techniqueId)
            || "print_linked_list".equals(techniqueId)
            || "count_nodes_linked_list".equals(techniqueId)
            || "search_in_linked_list".equals(techniqueId)
            || "insert_at_beginning_linked_list".equals(techniqueId)
            || "insert_after_given_node_linked_list".equals(techniqueId)
            || "insert_into_sorted_linked_list".equals(techniqueId)
            || "delete_node_given_location_linked_list".equals(techniqueId)
            || "delete_node_by_item_linked_list".equals(techniqueId)) {
            return Color.parseColor("#4A2F3F");
        }
        if ("circular_queue".equals(techniqueId)
            || "traverse_circular_header_list".equals(techniqueId)
            || "search_circular_header_list".equals(techniqueId)
            || "delete_circular_header_list".equals(techniqueId)) {
            return Color.parseColor("#145A5C");
        }
        return Color.parseColor("#1E293B");
    }

    private int resolveCircularHeaderColor() {
        return Color.parseColor("#0F766E");
    }

    private boolean isBinaryDiscardedIndex(int index) {
        if (!"binary_search".equals(techniqueId)) {
            return false;
        }
        if (!"low".equalsIgnoreCase(primaryPointerLabel) || !"high".equalsIgnoreCase(secondaryPointerLabel)) {
            return false;
        }
        if (primaryPointerIndex < 0 || secondaryPointerIndex < 0) {
            return false;
        }
        int lowIndex = Math.min(primaryPointerIndex, secondaryPointerIndex);
        int highIndex = Math.max(primaryPointerIndex, secondaryPointerIndex);
        return index < lowIndex || index > highIndex;
    }

    private boolean isJumpDiscardedIndex(int index) {
        if (!"jump_search".equals(techniqueId)) {
            return false;
        }
        if (!"start".equalsIgnoreCase(secondaryPointerLabel) || secondaryPointerIndex <= 0) {
            return false;
        }
        return index < secondaryPointerIndex;
    }

    private boolean isLinearDiscardedIndex(int index) {
        if (!"linear_search".equals(techniqueId)) {
            return false;
        }
        if (!"ptr".equalsIgnoreCase(primaryPointerLabel) || primaryPointerIndex <= 0) {
            return false;
        }
        return index < primaryPointerIndex;
    }

    private boolean shouldDrawArrayIndices() {
        if (values.length == 0) {
            return false;
        }
        if (isSortingTechnique() || isSearchingTechnique()) {
            return true;
        }
        return isStructureTechnique() && !isVerticalStructureTechnique() && !"circular_queue".equals(techniqueId);
    }

    private boolean shouldDrawStackIndices() {
        return values.length > 0 && ("stack_basics".equals(techniqueId)
            || "linked_stack_push".equals(techniqueId)
            || "linked_stack_pop".equals(techniqueId));
    }

    private int resolveAnimatedValue(int index) {
        if (index >= values.length) {
            return 0;
        }
        if (previousValues.length != values.length || index >= previousValues.length) {
            return values[index];
        }
        float start = previousValues[index];
        float end = values[index];
        return Math.round(start + ((end - start) * transitionProgress));
    }

    private void detectSwap(int[] oldValues, int[] newValues) {
        swapAnimating = false;
        swapFromIndex = -1;
        swapToIndex = -1;

        if (oldValues == null || newValues == null || oldValues.length != newValues.length || oldValues.length == 0) {
            return;
        }

        Log.d("TechniqueVisualizer", "detectSwap called: old=" + java.util.Arrays.toString(oldValues) + " new=" + java.util.Arrays.toString(newValues));

        int firstMismatch = -1;
        int secondMismatch = -1;
        for (int i = 0; i < oldValues.length; i++) {
            if (oldValues[i] != newValues[i]) {
                if (firstMismatch == -1) {
                    firstMismatch = i;
                } else if (secondMismatch == -1) {
                    secondMismatch = i;
                } else {
                    Log.d("TechniqueVisualizer", "detectSwap: more than 2 mismatches, returning");
                    return;
                }
            }
        }

        if (firstMismatch >= 0
            && secondMismatch >= 0
            && oldValues[firstMismatch] == newValues[secondMismatch]
            && oldValues[secondMismatch] == newValues[firstMismatch]) {
            swapAnimating = true;
            swapFromIndex = firstMismatch;
            swapToIndex = secondMismatch;
            Log.d("TechniqueVisualizer", "detectSwap: Swap detected from " + swapFromIndex + " to " + swapToIndex);
            startSwapAnimation();
            // Fire swap audio + haptic
            if (soundHaptic != null
                    && firstMismatch < newValues.length
                    && secondMismatch < newValues.length) {
                soundHaptic.onSwap(newValues[firstMismatch], newValues[secondMismatch], lastMaxValue);
            }
        } else {
            Log.d("TechniqueVisualizer", "detectSwap: Mismatches found but not a clean swap: first=" + firstMismatch + " second=" + secondMismatch);
        }
    }

    private void startTransition() {
        if (transitionAnimator != null) {
            transitionAnimator.cancel();
        }
        transitionAnimator = ValueAnimator.ofFloat(0f, 1f);
        transitionAnimator.setDuration(280L);
        transitionAnimator.addUpdateListener(animation -> {
            transitionProgress = (float) animation.getAnimatedValue();
            invalidate();
        });
        transitionAnimator.start();
    }

    private void startEdgeAnimation() {
        if (edgeAnimator != null) {
            edgeAnimator.cancel();
        }
        edgeAnimator = ValueAnimator.ofFloat(0f, 1f);
        edgeAnimator.setDuration(420L);
        edgeAnimator.addUpdateListener(animation -> {
            edgeProgress = (float) animation.getAnimatedValue();
            invalidate();
        });
        edgeAnimator.start();
    }

    private void startPointerAnimation() {
        if (pointerAnimator != null) {
            pointerAnimator.cancel();
        }
        pointerMotionProgress = 0f;
        pointerAnimator = ValueAnimator.ofFloat(0f, 1f);
        pointerAnimator.setDuration(260L);
        pointerAnimator.addUpdateListener(animation -> {
            pointerMotionProgress = (float) animation.getAnimatedValue();
            invalidate();
        });
        pointerAnimator.start();
    }

    private void startBinaryRangeFade() {
        if (binaryRangeAnimator != null) {
            binaryRangeAnimator.cancel();
        }
        binaryRangeFadeProgress = 0f;
        binaryRangeAnimator = ValueAnimator.ofFloat(0f, 1f);
        binaryRangeAnimator.setDuration(320L);
        binaryRangeAnimator.addUpdateListener(animation -> {
            binaryRangeFadeProgress = (float) animation.getAnimatedValue();
            invalidate();
        });
        binaryRangeAnimator.start();
    }

    private void startMergeTransferAnimation() {
        if (mergeTransferAnimator != null) {
            mergeTransferAnimator.cancel();
        }
        mergeTransferProgress = 0f;
        mergeTransferAnimator = ValueAnimator.ofFloat(0f, 1f);
        mergeTransferAnimator.setDuration(320L);
        mergeTransferAnimator.addUpdateListener(animation -> {
            mergeTransferProgress = (float) animation.getAnimatedValue();
            invalidate();
        });
        mergeTransferAnimator.start();
    }

    private void startMatrixHighlightAnimation() {
        if (matrixHighlightAnimator != null) {
            matrixHighlightAnimator.cancel();
        }
        matrixHighlightProgress = 0f;
        matrixHighlightAnimator = ValueAnimator.ofFloat(0f, 1f);
        matrixHighlightAnimator.setDuration(260L);
        matrixHighlightAnimator.setInterpolator(new DecelerateInterpolator());
        matrixHighlightAnimator.addUpdateListener(animation -> {
            matrixHighlightProgress = (float) animation.getAnimatedValue();
            invalidate();
        });
        matrixHighlightAnimator.start();
    }

    private void startStructureMotionAnimation() {
        if (structureAnimator != null) {
            structureAnimator.cancel();
        }
        structureMotionProgress = 0f;
        structureAnimator = ValueAnimator.ofFloat(0f, 1f);
        structureAnimator.setDuration(resolveStructureAnimationDuration());
        structureAnimator.setInterpolator(isStructureExitOperation()
            ? new AccelerateDecelerateInterpolator()
            : new android.view.animation.OvershootInterpolator(1.3f));
        structureAnimator.addUpdateListener(animation -> {
            structureMotionProgress = (float) animation.getAnimatedValue();
            invalidate();
        });
        structureAnimator.start();
    }

    private void startSwapAnimation() {
        if (swapAnimator != null) {
            swapAnimator.cancel();
        }
        swapProgress = 0f;
        Log.d("TechniqueVisualizer", "startSwapAnimation: Creating ValueAnimator");
        swapAnimator = ValueAnimator.ofFloat(0f, 1f);
        swapAnimator.setDuration(600L);
        swapAnimator.setInterpolator(new android.view.animation.OvershootInterpolator(1.4f));
        swapAnimator.addUpdateListener(animation -> {
            swapProgress = (float) animation.getAnimatedValue();
            Log.d("TechniqueVisualizer", "startSwapAnimation progress: " + swapProgress + ", fraction: " + animation.getAnimatedFraction());
            if (animation.getAnimatedFraction() >= 0.99f) {
                swapAnimating = false;
                Log.d("TechniqueVisualizer", "startSwapAnimation finished, swapAnimating set to false");
            }
            invalidate();
        });
        swapAnimator.start();
    }

    private int lightenColor(int color, float factor) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        red += Math.round((255 - red) * factor);
        green += Math.round((255 - green) * factor);
        blue += Math.round((255 - blue) * factor);

        return Color.rgb(Math.min(255, red), Math.min(255, green), Math.min(255, blue));
    }

    private int blendColors(int startColor, int endColor, float amount) {
        float clamped = Math.max(0f, Math.min(1f, amount));
        int red = Math.round(Color.red(startColor) + ((Color.red(endColor) - Color.red(startColor)) * clamped));
        int green = Math.round(Color.green(startColor) + ((Color.green(endColor) - Color.green(startColor)) * clamped));
        int blue = Math.round(Color.blue(startColor) + ((Color.blue(endColor) - Color.blue(startColor)) * clamped));
        return Color.rgb(red, green, blue);
    }

    private float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    private float lerp(float start, float end, float amount) {
        return start + ((end - start) * amount);
    }

    int resolveArrayDesiredWidth() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        if (values.length == 0) {
            return screenWidth;
        }
        float availableWidth = Math.max(0f, screenWidth - (HORIZONTAL_PADDING * 2f));
        float desiredCellWidth = resolveHorizontalCellWidth(availableWidth);
        int desired = Math.round((HORIZONTAL_PADDING * 2f) + (values.length * desiredCellWidth));
        return Math.max(screenWidth, desired);
    }

    int resolvePatternDesiredWidth() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int maxLength = Math.max(patternText.length(), patternQuery.length());
        float cellWidth = 56f;
        int desired = Math.round((HORIZONTAL_PADDING * 2f) + (maxLength * cellWidth));
        return Math.max(screenWidth, desired);
    }

    int resolveScreenWidthDesiredWidth() {
        if (isLandscapeCanvas() && getParent() instanceof View) {
            int parentWidth = ((View) getParent()).getWidth();
            if (parentWidth > 0) {
                return parentWidth;
            }
        }
        return getResources().getDisplayMetrics().widthPixels;
    }

    int resolveStackDesiredHeight() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenHeight = metrics.heightPixels;
        return Math.max(screenHeight / 2, 480);
    }

    int resolveTraversalDesiredHeight() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenHeight = metrics.heightPixels;
        return Math.max(screenHeight / 3, 360);
    }

    int resolveStandardDesiredHeight() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenHeight = metrics.heightPixels;
        return Math.max(screenHeight / 3, 340);
    }

    private VisualizerEngine resolveEngine() {
        for (VisualizerEngine engine : engines) {
            if (engine.supports(techniqueId)) {
                return engine;
            }
        }
        return null;
    }

    private boolean matricesEqual(int[][] left, int[][] right) {
        if (left == right) {
            return true;
        }
        if (left == null || right == null || left.length != right.length) {
            return false;
        }
        for (int i = 0; i < left.length; i++) {
            if (!Arrays.equals(left[i], right[i])) {
                return false;
            }
        }
        return true;
    }

    private float resolveHorizontalCellWidth() {
        float availableWidth = getWidth() > 0
            ? (getWidth() - (HORIZONTAL_PADDING * 2f))
            : (getResources().getDisplayMetrics().widthPixels - (HORIZONTAL_PADDING * 2f));
        return resolveHorizontalCellWidth(availableWidth);
    }

    private float resolveHorizontalCellWidth(float availableWidth) {
        if (values.length == 0) {
            return MAX_CELL_WIDTH;
        }
        float perItem = Math.max(0f, availableWidth) / (float) values.length;
        if (isLandscapeCanvas()) {
            return Math.max(48f, Math.min(MAX_CELL_WIDTH, perItem - 8f));
        }
        if (values.length <= 6) {
            return Math.max(118f, Math.min(MAX_CELL_WIDTH, perItem - 10f));
        }
        if (values.length <= 10) {
            return Math.max(96f, Math.min(148f, perItem - 8f));
        }
        if (values.length <= 14) {
            return Math.max(78f, Math.min(120f, perItem - 6f));
        }
        if (values.length <= 20) {
            return Math.max(66f, Math.min(96f, perItem - 4f));
        }
        return Math.max(56f, Math.min(84f, perItem - 2f));
    }

    private float resolveValueTextSize() {
        int maxDigits = 1;
        for (int value : values) {
            maxDigits = Math.max(maxDigits, String.valueOf(Math.abs(value)).length());
        }

        float baseSize;
        if (values.length <= 5) {
            baseSize = 38f;
        } else if (values.length <= 8) {
            baseSize = 32f;
        } else if (values.length <= 10) {
            baseSize = 28f;
        } else if (values.length <= 14) {
            baseSize = 24f;
        } else if (values.length <= 20) {
            baseSize = 20f;
        } else {
            baseSize = 18f;
        }

        if (maxDigits >= 4) {
            baseSize -= 4f;
        } else if (maxDigits >= 3) {
            baseSize -= 2f;
        }
        return Math.max(16f, baseSize);
    }

    private float resolveBoxHeight() {
        if (values.length <= 5) {
            return 138f;
        }
        if (values.length <= 10) {
            return 124f;
        }
        if (values.length <= 14) {
            return 112f;
        }
        return 102f;
    }

    private boolean isStructureTechnique() {
        return "queue_basics".equals(techniqueId)
            || "circular_queue".equals(techniqueId)
            || "linked_queue".equals(techniqueId)
            || "deque".equals(techniqueId)
            || "priority_queue_array".equals(techniqueId)
            || "priority_queue_2d".equals(techniqueId)
            || "singly_linked_list".equals(techniqueId)
            || "traversing_linked_list".equals(techniqueId)
            || "print_linked_list".equals(techniqueId)
            || "count_nodes_linked_list".equals(techniqueId)
            || "search_in_linked_list".equals(techniqueId)
            || "insert_at_beginning_linked_list".equals(techniqueId)
            || "insert_after_given_node_linked_list".equals(techniqueId)
            || "insert_into_sorted_linked_list".equals(techniqueId)
            || "delete_node_given_location_linked_list".equals(techniqueId)
            || "delete_node_by_item_linked_list".equals(techniqueId)
            || "traverse_circular_header_list".equals(techniqueId)
            || "search_circular_header_list".equals(techniqueId)
            || "delete_circular_header_list".equals(techniqueId);
    }

    private boolean isVerticalStructureTechnique() {
        return "stack_basics".equals(techniqueId)
            || "linked_stack_push".equals(techniqueId)
            || "linked_stack_pop".equals(techniqueId);
    }

    private float resolveStructureHorizontalPadding() {
        return values.length >= 8 ? 18f : 22f;
    }

    private float resolveStructureAvailableWidth(float padding) {
        return Math.max(0f, getWidth() - (padding * 2f));
    }

    private boolean isLandscapeCanvas() {
        return getResources().getConfiguration().orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE;
    }

    private float resolveStructureTop() {
        return getHeight() * 0.39f;
    }

    private float resolveStructureBoxHeight() {
        if (values.length <= 5) {
            return 100f;
        }
        if (values.length <= 8) {
            return 92f;
        }
        if (values.length <= 12) {
            return 84f;
        }
        return 76f;
    }

    private float resolveStructureValueTextSize() {
        if (values.length <= 5) {
            return 28f;
        }
        if (values.length <= 8) {
            return 24f;
        }
        if (values.length <= 12) {
            return 21f;
        }
        return 18f;
    }

    private float resolveStackVisualWidth() {
        float availableWidth = getWidth() > 0 ? getWidth() : getResources().getDisplayMetrics().widthPixels;
        return Math.max(160f, Math.min(availableWidth * 0.42f, 240f));
    }

    private float resolveStackCellHeight() {
        if (values.length <= 4) {
            return 84f;
        }
        if (values.length <= 7) {
            return 74f;
        }
        return 66f;
    }

    private float resolveStackGap() {
        if (values.length <= 4) {
            return 10f;
        }
        if (values.length <= 7) {
            return 8f;
        }
        return 6f;
    }

    private float resolveStackBottomInset() {
        return values.length <= 5 ? 54f : 42f;
    }

    private boolean isSearchingTechnique() {
        return "linear_search".equals(techniqueId)
            || "binary_search".equals(techniqueId)
            || "jump_search".equals(techniqueId)
            || "pattern_matching".equals(techniqueId);
    }

    private boolean isSortingTechnique() {
        return "bubble_sort".equals(techniqueId)
            || "selection_sort".equals(techniqueId)
            || "insertion_sort".equals(techniqueId)
            || "quicksort".equals(techniqueId)
            || "merge_sort".equals(techniqueId)
            || "radix_sort".equals(techniqueId)
            || "heapsort".equals(techniqueId);
    }

    private String resolveStructureCardLabel(int index, boolean verticalStack) {
        if (verticalStack) {
            return index == values.length - 1 ? "TOP" : null;
        }
        if ("queue_basics".equals(techniqueId) || "linked_queue".equals(techniqueId) || "circular_queue".equals(techniqueId)) {
            if (values.length == 1 && index == 0) {
                return "F/R";
            }
            if (index == 0) {
                return "FRONT";
            }
            if (index == values.length - 1) {
                return "REAR";
            }
            return null;
        }
        if ("deque".equals(techniqueId)) {
            if (values.length == 1 && index == 0) {
                return "F/R";
            }
            if (index == 0) {
                return "FRONT";
            }
            if (index == values.length - 1) {
                return "REAR";
            }
        }
        return null;
    }

    private float resolveStructureEaseProgress() {
        float eased = structureMotionProgress;
        if (!isStructureExitOperation()) {
            return eased;
        }
        eased = 1f - ((1f - eased) * (1f - eased));
        if (isStructureExitOperation()) {
            eased = (float) (0.5f - (Math.cos(Math.PI * eased) / 2f));
        }
        return eased;
    }

    private long resolveStructureAnimationDuration() {
        if ("push".equals(structureOperation) || "enqueue".equals(structureOperation)
            || "enqueue_right".equals(structureOperation) || "rear_in".equals(structureOperation)
            || "front_in".equals(structureOperation)) {
            return 380L;
        }
        if (isStructureExitOperation()) {
            return 360L;
        }
        return 300L;
    }

    private float[][] resolveTreeLayout(float rootYFactor, float middleYFactor, float leafYFactor) {
        float width = getWidth() > 0 ? getWidth() : getResources().getDisplayMetrics().widthPixels;
        boolean compact = width <= 900f;
        float safeNodeInset = resolveTreeNodeRadius() + (compact ? 18f : 26f);
        float left = width * (compact ? 0.18f : 0.14f);
        float right = width * (compact ? 0.82f : 0.86f);
        float center = width / 2f;
        float quarterLeft = width * (compact ? 0.35f : 0.32f);
        float quarterRight = width * (compact ? 0.65f : 0.68f);
        float leafOne = width * (compact ? 0.22f : 0.18f);
        float leafTwo = width * (compact ? 0.42f : 0.42f);
        float leafThree = width * (compact ? 0.58f : 0.58f);
        float leafFour = width * (compact ? 0.78f : 0.82f);
        float rootY = getHeight() * (compact ? rootYFactor + 0.02f : rootYFactor);
        float midY = getHeight() * (compact ? middleYFactor + 0.01f : middleYFactor);
        float leafY = getHeight() * (compact ? leafYFactor - 0.02f : leafYFactor);
        float[][] points = new float[][]{
            {center, rootY},
            {quarterLeft, midY},
            {quarterRight, midY},
            {leafOne, leafY},
            {leafTwo, leafY},
            {leafThree, leafY},
            {leafFour, leafY}
        };
        for (float[] point : points) {
            point[0] = clamp(point[0], safeNodeInset, Math.max(safeNodeInset, width - safeNodeInset));
            point[1] = clamp(point[1], safeNodeInset, Math.max(safeNodeInset, getHeight() - safeNodeInset));
        }
        return points;
    }

    private float[][] resolveGraphLayout() {
        return resolveGraphLayout(Math.max(values.length == 0 ? 5 : values.length, 5));
    }

    private float[][] resolveGraphLayout(int nodeCount) {
        boolean compact = isCompactGraphLayout();
        float[][] base = compact
            ? new float[][]{
                {getWidth() * 0.22f, getHeight() * 0.30f},
                {getWidth() * 0.50f, getHeight() * 0.18f},
                {getWidth() * 0.78f, getHeight() * 0.30f},
                {getWidth() * 0.32f, getHeight() * 0.56f},
                {getWidth() * 0.68f, getHeight() * 0.56f},
                {getWidth() * 0.50f, getHeight() * 0.78f}
            }
            : new float[][]{
                {getWidth() * 0.20f, getHeight() * 0.34f},
                {getWidth() * 0.50f, getHeight() * 0.18f},
                {getWidth() * 0.80f, getHeight() * 0.34f},
                {getWidth() * 0.32f, getHeight() * 0.68f},
                {getWidth() * 0.68f, getHeight() * 0.68f},
                {getWidth() * 0.50f, getHeight() * 0.84f}
            };
        return Arrays.copyOf(base, Math.max(1, Math.min(nodeCount, base.length)));
    }

    private int[][] resolveGraphEdges(int nodeCount) {
        if (GraphExpandedStepEngine.isExpandedGraphTechnique(techniqueId)) {
            int[][] expanded = GraphExpandedStepEngine.getEdges(techniqueId);
            int count = 0;
            int[][] working = new int[expanded.length][2];
            for (int[] edge : expanded) {
                if (edge[0] < nodeCount && edge[1] < nodeCount) {
                    working[count++] = edge;
                }
            }
            return Arrays.copyOf(working, count);
        }
        int[][] base = new int[][]{
            {0, 1}, {1, 2}, {0, 3}, {1, 3}, {1, 4}, {2, 4}, {3, 4}
        };
        int[][] working = new int[base.length + 1][2];
        int count = 0;
        for (int[] edge : base) {
            if (edge[0] >= nodeCount || edge[1] >= nodeCount) {
                continue;
            }
            if ("delete_edge_graph".equals(techniqueId)
                && edgeMatches(edge[0], edge[1], activeEdgeFrom, activeEdgeTo)) {
                continue;
            }
            working[count++] = edge;
        }
        if ("insert_edge_graph".equals(techniqueId)
            && activeEdgeFrom >= 0
            && activeEdgeTo >= 0
            && activeEdgeFrom < nodeCount
            && activeEdgeTo < nodeCount
            && !containsEdge(working, count, activeEdgeFrom, activeEdgeTo)) {
            working[count++] = new int[] {activeEdgeFrom, activeEdgeTo};
        }
        return Arrays.copyOf(working, count);
    }

    private boolean containsEdge(int[][] edges, int length, int from, int to) {
        for (int i = 0; i < length; i++) {
            if (edgeMatches(edges[i][0], edges[i][1], from, to)) {
                return true;
            }
        }
        return false;
    }

    private boolean edgeMatches(int fromA, int toA, int fromB, int toB) {
        if (isDirectedGraphMode()) {
            return fromA == fromB && toA == toB;
        }
        return (fromA == fromB && toA == toB) || (fromA == toB && toA == fromB);
    }

    private float resolveTreeNodeRadius() {
        float width = getWidth() > 0 ? getWidth() : getResources().getDisplayMetrics().widthPixels;
        return isCompactTreeLayout()
            ? Math.max(30f, Math.min(36f, width * 0.054f))
            : Math.max(34f, Math.min(42f, width * 0.062f));
    }

    private float resolveHeapNodeRadius() {
        float width = getWidth() > 0 ? getWidth() : getResources().getDisplayMetrics().widthPixels;
        return isCompactTreeLayout()
            ? Math.max(28f, Math.min(34f, width * 0.05f))
            : Math.max(32f, Math.min(40f, width * 0.058f));
    }

    private float resolveTreeTextSize() {
        return resolveTreeNodeRadius() <= 32f ? 20f : resolveTreeNodeRadius() <= 36f ? 22f : 24f;
    }

    private boolean isCompactTreeLayout() {
        float width = getWidth() > 0 ? getWidth() : getResources().getDisplayMetrics().widthPixels;
        return width <= 900f;
    }

    private boolean isCompactGraphLayout() {
        float width = getWidth() > 0 ? getWidth() : getResources().getDisplayMetrics().widthPixels;
        return width <= 900f;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (transitionAnimator != null) {
            transitionAnimator.cancel();
        }
        if (edgeAnimator != null) {
            edgeAnimator.cancel();
        }
        if (swapAnimator != null) {
            swapAnimator.cancel();
        }
        if (pointerAnimator != null) {
            pointerAnimator.cancel();
        }
        if (structureAnimator != null) {
            structureAnimator.cancel();
        }
        if (binaryRangeAnimator != null) {
            binaryRangeAnimator.cancel();
        }
        if (mergeTransferAnimator != null) {
            mergeTransferAnimator.cancel();
        }
        if (matrixHighlightAnimator != null) {
            matrixHighlightAnimator.cancel();
        }
        if (pulseAnimator != null) {
            pulseAnimator.cancel();
        }
        if (soundHaptic != null) {
            soundHaptic.release();
        }
    }

    private void drawParticles(Canvas canvas) {
        if (!particles.isEmpty()) {
            Paint pPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            pPaint.setStyle(Paint.Style.FILL);
            for (int i = particles.size() - 1; i >= 0; i--) {
                Particle p = particles.get(i);
                p.update();
                if (p.alpha <= 0f) {
                    particles.remove(i);
                } else {
                    pPaint.setColor(p.color);
                    pPaint.setAlpha((int) (p.alpha * 255));
                    canvas.drawCircle(p.x, p.y, p.size * p.alpha, pPaint);
                }
            }
            postInvalidateDelayed(16); // Force redraw to animate particles smoothly!
        }
    }

    private static class Particle {
        float x, y;
        float vx, vy;
        int color;
        float size;
        float alpha = 1.0f;
        float decay;

        Particle(float x, float y, float vx, float vy, int color, float size, float decay) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            this.color = color;
            this.size = size;
            this.decay = decay;
        }

        void update() {
            x += vx;
            y += vy;
            vy += 0.2f; // Gravity pull downwards
            alpha -= decay;
        }
    }
}
