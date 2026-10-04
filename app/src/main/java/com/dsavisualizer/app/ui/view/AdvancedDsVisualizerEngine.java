package com.dsavisualizer.app.ui.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.dsavisualizer.app.ui.visualization.runtime.AvlTreeStepEngine;

/**
 * Visualizer engine for Advanced Data Structures:
 * Trie, Segment Tree, Persistent Segment Tree, Fenwick Tree (BIT),
 * AVL Tree, Red-Black Tree, Splay Tree, B-Tree (2-3-4 Tree),
 * Disjoint Set Union (DSU), Bloom Filter, LRU Cache.
 */
public class AdvancedDsVisualizerEngine implements VisualizerEngine {

    private static final Set<String> SUPPORTED = new HashSet<>(Arrays.asList(
            "ds_trie", "adv_trie",
            "ds_segment_tree", "adv_segment_tree",
            "ds_persistent_segment_tree", "adv_persistent_st",
            "ds_fenwick_tree", "adv_fenwick_tree",
            "ds_avl_tree", "adv_avl_tree",
            "ds_red_black_tree", "adv_red_black_tree",
            "ds_splay_tree", "adv_splay_tree",
            "ds_b_tree", "adv_b_tree",
            "ds_disjoint_set", "adv_dsu",
            "ds_bloom_filter", "adv_bloom_filter",
            "ds_lru_cache", "adv_lru_cache"
    ));

    private final Paint nodePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint subTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint badgePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF rect = new RectF();
    private final Path path = new Path();

    public AdvancedDsVisualizerEngine() {
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(3f);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(3.5f);
        linePaint.setColor(Color.parseColor("#475569"));
        textPaint.setTextAlign(Paint.Align.CENTER);
        subTextPaint.setTextAlign(Paint.Align.CENTER);
        badgePaint.setStyle(Paint.Style.FILL);
    }

    public static boolean isSupportedTechnique(String techniqueId) {
        return techniqueId != null && SUPPORTED.contains(techniqueId);
    }

    @Override
    public boolean supports(String techniqueId) {
        return isSupportedTechnique(techniqueId);
    }

    @Override
    public int resolveDesiredWidth(TechniqueVisualizerView view, String techniqueId) {
        return Math.max(view.getWidth(), 520);
    }

    @Override
    public int resolveDesiredHeight(TechniqueVisualizerView view, String techniqueId) {
        return Math.max(view.getHeight(), 400);
    }

    @Override
    public void draw(Canvas canvas, TechniqueVisualizerView view, String techniqueId) {
        int[] arr = view.getArrayValues();
        int highlightA = view.getHighlightIndex();
        int highlightB = view.getSecondaryHighlightIndex();
        String id = techniqueId != null ? techniqueId : "";

        switch (id) {
            case "ds_bloom_filter":
            case "adv_bloom_filter":
                drawBloomFilter(canvas, view, arr, highlightA, highlightB);
                break;
            case "ds_lru_cache":
            case "adv_lru_cache":
                drawLruCache(canvas, view, arr, highlightA, highlightB);
                break;
            case "ds_trie":
            case "adv_trie":
                drawTrie(canvas, view, arr, highlightA, highlightB);
                break;
            case "ds_disjoint_set":
            case "adv_dsu":
                drawDisjointSet(canvas, view, arr, highlightA, highlightB);
                break;
            case "ds_fenwick_tree":
            case "adv_fenwick_tree":
                drawFenwickTree(canvas, view, arr, highlightA, highlightB);
                break;
            case "ds_avl_tree":
            case "adv_avl_tree":
                drawAvlTree(canvas, view, arr, highlightA, highlightB);
                break;
            case "ds_red_black_tree":
            case "adv_red_black_tree":
                drawRedBlackTree(canvas, view, arr, highlightA, highlightB);
                break;
            case "ds_splay_tree":
            case "adv_splay_tree":
                drawSplayTree(canvas, view, arr, highlightA, highlightB);
                break;
            case "ds_b_tree":
            case "adv_b_tree":
                drawBTree(canvas, view, arr, highlightA, highlightB);
                break;
            case "ds_persistent_segment_tree":
            case "adv_persistent_st":
                drawPersistentSegmentTree(canvas, view, arr, highlightA, highlightB);
                break;
            case "ds_segment_tree":
            case "adv_segment_tree":
            default:
                drawSegmentTree(canvas, view, arr, highlightA, highlightB);
                break;
        }
    }

    // =========================================================================
    // 1. Trie
    // =========================================================================
    private void drawTrie(Canvas canvas, TechniqueVisualizerView view, int[] arr, int hA, int hB) {
        float cx = view.getWidth() / 2f;
        float startY = 48f;

        subTextPaint.setColor(Color.parseColor("#10B981"));
        subTextPaint.setTextSize(24f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText("TRIE (PREFIX TREE)", cx, startY, subTextPaint);

        int step = view.getStepIndex();
        String expr = view.getExpressionInput();
        if (expr == null || expr.trim().isEmpty()) {
            expr = "cat, car, card";
        }
        String[] words = expr.split("[,\\s]+");
        String w1 = words.length > 0 && !words[0].isEmpty() ? words[0] : "cat";
        String w2 = words.length > 1 && !words[1].isEmpty() ? words[1] : "car";
        String w3 = words.length > 2 && !words[2].isEmpty() ? words[2] : "card";

        char c0 = !w1.isEmpty() ? w1.charAt(0) : 'c';
        char c1 = w1.length() > 1 ? w1.charAt(1) : (!w2.isEmpty() ? w2.charAt(0) : 'a');
        char c2 = w1.length() > 2 ? w1.charAt(2) : (w1.length() > 1 ? w1.charAt(1) : 't');
        char cBranch2 = w2.length() > 2 ? w2.charAt(2) : (!w2.isEmpty() ? w2.charAt(w2.length() - 1) : 'r');
        char cBranch3 = w3.length() > 3 ? w3.charAt(3) : (!w3.isEmpty() ? w3.charAt(w3.length() - 1) : 'd');

        boolean showN1 = (step >= 1);
        boolean showN2 = (step >= 2);
        boolean showN3 = (step >= 3);
        boolean showN4 = (step >= 4);
        boolean showN5 = (step >= 5);

        float pulse = view.getPulseProgress();

        // Root Node (0)
        float rootY = startY + 45f;
        boolean isRootActive = (hA == 0 || step == 6);
        drawCircleNode(canvas, cx, rootY, "ROOT", isRootActive ? Color.parseColor("#F59E0B") : Color.parseColor("#3B82F6"), 26f, isRootActive, pulse);

        // Level 1: Node 1 ('c')
        float y1 = rootY + 70f;
        float x1 = cx;
        if (showN1) {
            linePaint.setColor(step == 6 ? Color.parseColor("#F59E0B") : Color.parseColor("#64748B"));
            canvas.drawLine(cx, rootY + 26f, x1, y1 - 22f, linePaint);
            drawEdgeLabel(canvas, (cx + x1) / 2f + 14f, (rootY + 26f + y1 - 22f) / 2f, String.valueOf(c0));
            boolean isN1Active = (hA == 1 || hB == 1 || step == 6);
            drawCircleNode(canvas, x1, y1, String.valueOf(c0), isN1Active ? Color.parseColor("#F59E0B") : Color.parseColor("#1E293B"), 22f, isN1Active, pulse);
        }

        // Level 2: Node 2 ('a')
        float y2 = y1 + 70f;
        float x2 = cx;
        if (showN2) {
            linePaint.setColor(step == 6 ? Color.parseColor("#F59E0B") : Color.parseColor("#64748B"));
            canvas.drawLine(x1, y1 + 22f, x2, y2 - 22f, linePaint);
            drawEdgeLabel(canvas, (x1 + x2) / 2f + 14f, (y1 + 22f + y2 - 22f) / 2f, String.valueOf(c1));
            boolean isN2Active = (hA == 2 || hB == 2 || step == 6);
            drawCircleNode(canvas, x2, y2, String.valueOf(c1), isN2Active ? Color.parseColor("#F59E0B") : Color.parseColor("#1E293B"), 22f, isN2Active, pulse);
        }

        // Level 3: Node 3 ('t' - end of w1) and Node 4 ('r' - branch for w2)
        float y3 = y2 + 70f;
        float x3 = cx - 80f;
        float x4 = cx + 80f;

        // Edge 2 -> 3
        if (showN3) {
            linePaint.setColor(Color.parseColor("#64748B"));
            canvas.drawLine(x2, y2 + 22f, x3, y3 - 22f, linePaint);
            drawEdgeLabel(canvas, (x2 + x3) / 2f - 14f, (y2 + 22f + y3 - 22f) / 2f, String.valueOf(c2));
            boolean isN3Active = (hA == 3 || hB == 3);
            drawTerminalNode(canvas, x3, y3, String.valueOf(c2), isN3Active ? Color.parseColor("#F59E0B") : Color.parseColor("#059669"), 22f, w1, isN3Active, pulse);
        }

        // Edge 2 -> 4
        if (showN4) {
            linePaint.setColor(Color.parseColor("#64748B"));
            canvas.drawLine(x2, y2 + 22f, x4, y3 - 22f, linePaint);
            drawEdgeLabel(canvas, (x2 + x4) / 2f + 14f, (y2 + 22f + y3 - 22f) / 2f, String.valueOf(cBranch2));
            boolean isN4Active = (hA == 4 || hB == 4);
            drawTerminalNode(canvas, x4, y3, String.valueOf(cBranch2), isN4Active ? Color.parseColor("#F59E0B") : Color.parseColor("#059669"), 22f, w2, isN4Active, pulse);
        }

        // Level 4: Node 5 ('d' - branch for w3 from Node 4)
        float y4 = y3 + 70f;
        float x5 = x4;
        if (showN5) {
            linePaint.setColor(Color.parseColor("#64748B"));
            canvas.drawLine(x4, y3 + 22f, x5, y4 - 22f, linePaint);
            drawEdgeLabel(canvas, x5 + 14f, (y3 + 22f + y4 - 22f) / 2f, String.valueOf(cBranch3));
            boolean isN5Active = (hA == 5 || hB == 5);
            drawTerminalNode(canvas, x5, y4, String.valueOf(cBranch3), isN5Active ? Color.parseColor("#F59E0B") : Color.parseColor("#059669"), 22f, w3, isN5Active, pulse);
        }

        // Bottom Legend Banner
        subTextPaint.setColor(Color.parseColor("#94A3B8"));
        subTextPaint.setTextSize(14f);
        subTextPaint.setFakeBoldText(false);
        if (step <= 0) {
            subTextPaint.setColor(Color.parseColor("#10B981"));
            subTextPaint.setFakeBoldText(true);
            canvas.drawText("Words to insert: \"" + w1 + "\", \"" + w2 + "\", \"" + w3 + "\". Tap Play or Step to build Trie incrementally.", cx, y4 + 40f, subTextPaint);
        } else if (step == 6) {
            subTextPaint.setColor(Color.parseColor("#F59E0B"));
            canvas.drawText("Query: Prefix '" + c0 + "" + c1 + "' reaches Node 2 in O(L=2). Traversal finds all extensions!", cx, y4 + 40f, subTextPaint);
        } else {
            canvas.drawText("Green nodes = End of Word (Terminal)  |  Prefix queries execute in O(L) time", cx, y4 + 40f, subTextPaint);
        }
    }

    private void drawEdgeLabel(Canvas canvas, float x, float y, String label) {
        subTextPaint.setColor(Color.parseColor("#38BDF8"));
        subTextPaint.setTextSize(16f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText(label, x, y, subTextPaint);
    }

    private void drawTerminalNode(Canvas canvas, float cx, float cy, String text, int fillColor, float radius, String wordLabel) {
        drawTerminalNode(canvas, cx, cy, text, fillColor, radius, wordLabel, false, 0f);
    }

    private void drawTerminalNode(Canvas canvas, float cx, float cy, String text, int fillColor, float radius, String wordLabel, boolean isHighlighted, float pulse) {
        drawCircleNode(canvas, cx, cy, text, fillColor, radius, isHighlighted, pulse);
        // Double ring for terminal
        borderPaint.setColor(Color.parseColor("#6EE7B7"));
        canvas.drawCircle(cx, cy, radius + (isHighlighted ? 4f + pulse * 3f : 4f), borderPaint);

        subTextPaint.setColor(Color.parseColor("#6EE7B7"));
        subTextPaint.setTextSize(12f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText("\"" + wordLabel + "\"", cx, cy + radius + 16f, subTextPaint);
    }

    // =========================================================================
    // 2. Segment Tree
    // =========================================================================
    private void drawSegmentTree(Canvas canvas, TechniqueVisualizerView view, int[] arr, int hA, int hB) {
        float cx = view.getWidth() / 2f;
        float top = 48f;

        subTextPaint.setColor(Color.parseColor("#10B981"));
        subTextPaint.setTextSize(24f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText("SEGMENT TREE (INTERVAL SUM / CANONICAL QUERY)", cx, top, subTextPaint);

        String expr = view.getExpressionInput();
        int[] parsed = com.dsavisualizer.app.ui.visualization.runtime.AdvancedDsStepEngine.parseBaseArray(expr, 4, new int[]{1, 3, 5, 7});
        int l0 = (arr != null && arr.length > 3) ? arr[3] : parsed[0];
        int l1 = (arr != null && arr.length > 4) ? arr[4] : (parsed.length > 1 ? parsed[1] : 3);
        int l2 = (arr != null && arr.length > 5) ? arr[5] : (parsed.length > 2 ? parsed[2] : 5);
        int l3 = (arr != null && arr.length > 6) ? arr[6] : (parsed.length > 3 ? parsed[3] : 7);
        int leftSum = l0 + l1;
        int rightSum = l2 + l3;
        int rootSum = leftSum + rightSum;
        int step = view.getStepIndex();

        String leftLabel = (step >= 2) ? String.valueOf((arr != null && arr.length > 1 && arr[1] != 0) ? arr[1] : leftSum) : "?";
        String rightLabel = (step >= 3) ? String.valueOf((arr != null && arr.length > 2 && arr[2] != 0) ? arr[2] : rightSum) : "?";
        String rootLabel = (step >= 4) ? String.valueOf((arr != null && arr.length > 0 && arr[0] != 0) ? arr[0] : rootSum) : "?";

        float pulse = view.getPulseProgress();

        // Root [0..3]
        float rootY = top + 45f;
        boolean rootActive = (hA == 0 || hB == 0);
        drawIntervalNode(canvas, cx, rootY, "[0..3]: " + rootLabel, rootActive ? Color.parseColor("#F59E0B") : (step >= 4 ? Color.parseColor("#059669") : Color.parseColor("#1E293B")), rootActive, pulse);

        // Level 1: [0..1] and [2..3]
        float y1 = rootY + 75f;
        float leftX = cx - 110f;
        float rightX = cx + 110f;

        linePaint.setColor(Color.parseColor("#64748B"));
        canvas.drawLine(cx, rootY + 20f, leftX, y1 - 20f, linePaint);
        canvas.drawLine(cx, rootY + 20f, rightX, y1 - 20f, linePaint);

        boolean lActive = (hA == 1 || hB == 1);
        boolean rActive = (hA == 2 || hB == 2);
        drawIntervalNode(canvas, leftX, y1, "[0..1]: " + leftLabel, lActive ? Color.parseColor("#F59E0B") : (step >= 2 ? Color.parseColor("#059669") : Color.parseColor("#1E293B")), lActive, pulse);
        drawIntervalNode(canvas, rightX, y1, "[2..3]: " + rightLabel, rActive ? Color.parseColor("#F59E0B") : (step >= 3 ? Color.parseColor("#059669") : Color.parseColor("#1E293B")), rActive, pulse);

        // Level 2: Leaves [0], [1], [2], [3]
        float y2 = y1 + 75f;
        float[] leafX = {leftX - 55f, leftX + 55f, rightX - 55f, rightX + 55f};
        int[] leafVals = {l0, l1, l2, l3};

        for (int i = 0; i < 4; i++) {
            float px = (i < 2) ? leftX : rightX;
            canvas.drawLine(px, y1 + 20f, leafX[i], y2 - 20f, linePaint);
            boolean leafActive = (hA == i + 3 || hB == i + 3);
            drawIntervalNode(canvas, leafX[i], y2, "[" + i + "]: " + leafVals[i], leafActive ? Color.parseColor("#F59E0B") : Color.parseColor("#334155"), leafActive, pulse);
        }

        // Base array row at bottom
        float y3 = y2 + 65f;
        subTextPaint.setColor(Color.parseColor("#94A3B8"));
        subTextPaint.setTextSize(15f);
        if (step <= 0) {
            subTextPaint.setColor(Color.parseColor("#10B981"));
            subTextPaint.setFakeBoldText(true);
            canvas.drawText("Raw Array: [" + l0 + ", " + l1 + ", " + l2 + ", " + l3 + "] loaded into leaves. Tap Play or Step to compute range sums.", cx, y3, subTextPaint);
        } else {
            subTextPaint.setFakeBoldText(false);
            canvas.drawText("Raw Array: [" + l0 + ", " + l1 + ", " + l2 + ", " + l3 + "]  |  Query [1..3] merges [1] + [2..3] in O(log N)", cx, y3, subTextPaint);
        }
    }

    // =========================================================================
    // 3. Persistent Segment Tree
    // =========================================================================
    private void drawPersistentSegmentTree(Canvas canvas, TechniqueVisualizerView view, int[] arr, int hA, int hB) {
        float cx = view.getWidth() / 2f;
        float top = 48f;

        subTextPaint.setColor(Color.parseColor("#A855F7"));
        subTextPaint.setTextSize(24f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText("PERSISTENT SEGMENT TREE (VERSIONED)", cx, top, subTextPaint);

        int step = view.getStepIndex();
        String expr = view.getExpressionInput();
        int[] parsed = com.dsavisualizer.app.ui.visualization.runtime.AdvancedDsStepEngine.parseBaseArray(expr, 4, new int[]{1, 3, 5, 7});
        int l0 = (arr != null && arr.length > 3) ? arr[3] : parsed[0];
        int l1 = (arr != null && arr.length > 4) ? arr[4] : (parsed.length > 1 ? parsed[1] : 3);
        int l2 = (arr != null && arr.length > 5) ? arr[5] : (parsed.length > 2 ? parsed[2] : 5);
        int l3 = (arr != null && arr.length > 6) ? arr[6] : (parsed.length > 3 ? parsed[3] : 7);
        int left0 = l0 + l1;
        int right0 = l2 + l3;
        int root0 = (arr != null && arr.length > 0 && arr[0] != 0) ? arr[0] : (left0 + right0);
        int newLeaf2 = l2 + 3;
        int right1 = newLeaf2 + l3;
        int root1 = left0 + right1;

        // Version roots: V0 on left, V1 on right
        float r0X = cx - 110f;
        float r1X = cx + 110f;
        float rY = top + 45f;

        float pulse = view.getPulseProgress();
        drawIntervalNode(canvas, r0X, rY, "V0: [0..3] = " + root0, Color.parseColor("#0E7490"));

        boolean showV1 = (step >= 3);
        if (showV1) {
            drawIntervalNode(canvas, r1X, rY, "V1: [0..3]* = " + root1, Color.parseColor("#7E22CE"), step == 5, pulse);
        } else {
            drawIntervalNode(canvas, r1X, rY, "V1 (Pending Update)", Color.parseColor("#334155"));
        }

        // Level 1:
        // Left child [0..1] is SHARED by both V0 and V1!
        // Right child [2..3] has V0 version and V1 updated version!
        float y1 = rY + 80f;
        float sharedLeftX = cx - 110f;
        float oldRightX = cx;
        float newRightX = cx + 120f;

        // V0 connections
        linePaint.setColor(Color.parseColor("#38BDF8"));
        canvas.drawLine(r0X, rY + 20f, sharedLeftX, y1 - 20f, linePaint);
        canvas.drawLine(r0X, rY + 20f, oldRightX, y1 - 20f, linePaint);

        // V1 connections: shared left + new right
        if (showV1) {
            linePaint.setColor(Color.parseColor("#A855F7"));
            canvas.drawLine(r1X, rY + 20f, sharedLeftX, y1 - 20f, linePaint);
            canvas.drawLine(r1X, rY + 20f, newRightX, y1 - 20f, linePaint);
        }

        drawIntervalNode(canvas, sharedLeftX, y1, "[0..1] SHARED", Color.parseColor("#0F766E"));
        drawIntervalNode(canvas, oldRightX, y1, "[2..3] (V0)", Color.parseColor("#1E293B"));
        if (showV1) {
            drawIntervalNode(canvas, newRightX, y1, "[2..3]* (V1)", Color.parseColor("#6B21A8"), step == 4, pulse);
        }

        // Leaves level
        float y2 = y1 + 75f;
        float leaf0 = sharedLeftX - 35f;
        float leaf1 = sharedLeftX + 35f;
        float leaf2New = newRightX - 35f;
        float leaf3Shared = newRightX + 35f;

        linePaint.setColor(Color.parseColor("#64748B"));
        canvas.drawLine(sharedLeftX, y1 + 20f, leaf0, y2 - 20f, linePaint);
        canvas.drawLine(sharedLeftX, y1 + 20f, leaf1, y2 - 20f, linePaint);
        drawIntervalNode(canvas, leaf0, y2, "[0]: " + l0, Color.parseColor("#334155"));
        drawIntervalNode(canvas, leaf1, y2, "[1]: " + l1, Color.parseColor("#334155"));

        if (showV1) {
            linePaint.setColor(Color.parseColor("#A855F7"));
            canvas.drawLine(newRightX, y1 + 20f, leaf2New, y2 - 20f, linePaint);
            linePaint.setColor(Color.parseColor("#64748B"));
            canvas.drawLine(newRightX, y1 + 20f, leaf3Shared, y2 - 20f, linePaint);
            drawIntervalNode(canvas, leaf2New, y2, "[2]*: " + newLeaf2, Color.parseColor("#7E22CE"), step == 3, pulse);
            drawIntervalNode(canvas, leaf3Shared, y2, "[3]: " + l3, Color.parseColor("#334155"));
        } else {
            drawIntervalNode(canvas, leaf3Shared, y2, "[2]: " + l2 + ", [3]: " + l3, Color.parseColor("#334155"));
        }

        // Bottom Banner
        subTextPaint.setColor(step <= 0 ? Color.parseColor("#A855F7") : Color.parseColor("#CBD5E1"));
        subTextPaint.setTextSize(14f);
        subTextPaint.setFakeBoldText(step <= 0);
        if (step <= 0) {
            canvas.drawText("Initial Version V0 ready. Press Step or Play to create Version V1 via O(log N) path copying.", cx, y2 + 55f, subTextPaint);
        } else {
            canvas.drawText("Path Copying: Only O(log N) new nodes allocated per update. Unmodified subtrees shared!", cx, y2 + 55f, subTextPaint);
        }
    }

    // =========================================================================
    // 4. Fenwick Tree / BIT
    // =========================================================================
    private void drawFenwickTree(Canvas canvas, TechniqueVisualizerView view, int[] arr, int hA, int hB) {
        int n = 6;
        float startX = 35f;
        float top = 50f;
        float boxW = 56f;
        float boxH = 42f;
        float gap = 12f;

        subTextPaint.setColor(Color.parseColor("#EC4899"));
        subTextPaint.setTextSize(24f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText("FENWICK TREE / BINARY INDEXED TREE (BIT)", view.getWidth() / 2f, top, subTextPaint);

        // Top Row: Base Array
        float yArray = top + 40f;
        subTextPaint.setColor(Color.parseColor("#94A3B8"));
        subTextPaint.setTextSize(16f);
        canvas.drawText("Base Array arr[i]:", startX + 50f, yArray + 28f, subTextPaint);

        String expr = view.getExpressionInput();
        int[] baseVals = com.dsavisualizer.app.ui.visualization.runtime.AdvancedDsStepEngine.parseBaseArray(expr, 6, new int[]{2, 1, 1, 3, 2, 3});
        int[] b = new int[6];
        for (int i = 0; i < 6; i++) {
            b[i] = (i < baseVals.length) ? baseVals[i] : (i + 1);
        }

        int[] computedBit = new int[7];
        for (int i = 1; i <= 6; i++) {
            int v = b[i - 1];
            for (int idx = i; idx <= 6; idx += idx & (-idx)) {
                computedBit[idx] += v;
            }
        }

        for (int i = 1; i <= n; i++) {
            float x = startX + 130f + (i - 1) * (boxW + gap);
            rect.set(x, yArray, x + boxW, yArray + boxH);

            nodePaint.setColor(Color.parseColor("#1E293B"));
            borderPaint.setColor(Color.parseColor("#475569"));
            nodePaint.setStyle(Paint.Style.FILL);
            canvas.drawRoundRect(rect, 8f, 8f, nodePaint);
            canvas.drawRoundRect(rect, 8f, 8f, borderPaint);

            textPaint.setColor(Color.WHITE);
            textPaint.setTextSize(18f);
            canvas.drawText(String.valueOf(b[i - 1]), rect.centerX(), rect.centerY() + 6f, textPaint);
        }

        // Bottom Row: BIT Array
        float yBit = yArray + boxH + 35f;
        subTextPaint.setColor(Color.parseColor("#F472B6"));
        subTextPaint.setTextSize(16f);
        canvas.drawText("BIT Tree[i]:", startX + 50f, yBit + 28f, subTextPaint);

        float pulse = view.getPulseProgress();

        for (int i = 1; i <= n; i++) {
            float x = startX + 130f + (i - 1) * (boxW + gap);
            rect.set(x, yBit, x + boxW, yBit + boxH);

            boolean active = (i == hA || i == hB);
            if (active) {
                badgePaint.setColor(Color.parseColor("#BE185D"));
                badgePaint.setAlpha((int) (40 + pulse * 60));
                rectFExpand(rect, 4f + pulse * 3f);
                canvas.drawRoundRect(rect, 10f, 10f, badgePaint);
                rect.set(x, yBit, x + boxW, yBit + boxH);
            }

            nodePaint.setColor(active ? Color.parseColor("#BE185D") : Color.parseColor("#1E293B"));
            borderPaint.setColor(active ? Color.parseColor("#F472B6") : Color.parseColor("#475569"));
            borderPaint.setStrokeWidth(active ? 4f : 3f);
            nodePaint.setStyle(Paint.Style.FILL);

            canvas.drawRoundRect(rect, 8f, 8f, nodePaint);
            canvas.drawRoundRect(rect, 8f, 8f, borderPaint);
            borderPaint.setStrokeWidth(3f);

            int bitVal = 0;
            if (view.getStepIndex() > 0) {
                bitVal = (arr != null && i < arr.length) ? arr[i] : computedBit[i];
            }
            textPaint.setColor(Color.WHITE);
            textPaint.setTextSize(active ? 19f + pulse * 2f : 18f);
            textPaint.setFakeBoldText(true);
            canvas.drawText(String.valueOf(bitVal), rect.centerX(), rect.centerY() + 6f, textPaint);

            // Subtext: LSB
            int lsb = i & (-i);
            subTextPaint.setColor(Color.parseColor("#94A3B8"));
            subTextPaint.setTextSize(13f);
            canvas.drawText("i=" + i, rect.centerX(), yBit + boxH + 18f, subTextPaint);
            subTextPaint.setColor(Color.parseColor("#F472B6"));
            canvas.drawText("len=" + lsb, rect.centerX(), yBit + boxH + 34f, subTextPaint);
        }

        // Educational navigation footer
        float yFoot = yBit + boxH + 60f;
        subTextPaint.setColor(view.getStepIndex() <= 0 ? Color.parseColor("#EC4899") : Color.parseColor("#CBD5E1"));
        subTextPaint.setTextSize(14f);
        subTextPaint.setFakeBoldText(view.getStepIndex() <= 0);
        if (view.getStepIndex() <= 0) {
            canvas.drawText("Base Array ready. BIT array initialized to 0. Press Step or Play to build BIT via point updates.", view.getWidth() / 2f, yFoot, subTextPaint);
        } else {
            canvas.drawText("Update: i += i & (-i) [Ascend]   |   Query: i -= i & (-i) [Descend]   |   Time: O(log N)", view.getWidth() / 2f, yFoot, subTextPaint);
        }
    }

    // =========================================================================
    // 5. AVL Tree
    // =========================================================================
    private void drawAvlTree(Canvas canvas, TechniqueVisualizerView view, int[] arr, int hA, int hB) {
        float cx = view.getWidth() / 2f;
        float top = 48f;

        subTextPaint.setColor(Color.parseColor("#6366F1"));
        subTextPaint.setTextSize(24f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText("AVL TREE (STRICTLY HEIGHT-BALANCED BST)", cx, top, subTextPaint);

        int step = view.getStepIndex();
        String expr = view.getExpressionInput();
        List<Integer> parsedList = new ArrayList<>();
        if (expr != null && !expr.trim().isEmpty()) {
            try {
                for (String p : expr.trim().split("[,\\s]+")) {
                    p = p.trim();
                    if (!p.isEmpty()) parsedList.add(Integer.parseInt(p));
                }
            } catch (Exception ignored) {}
        }
        if (parsedList.isEmpty() && arr != null && arr.length > 0) {
            for (int val : arr) parsedList.add(val);
        }
        if (parsedList.isEmpty()) {
            parsedList.add(10); parsedList.add(20); parsedList.add(30);
        }

        float rootY = top + 45f;

        if (parsedList.size() > 3) {
            drawMultiNodeAvlTree(canvas, view, cx, rootY, parsedList, step);
            return;
        }

        int v1 = parsedList.get(0);
        int v2 = parsedList.size() > 1 ? parsedList.get(1) : (v1 + 10);
        int v3 = parsedList.size() > 2 ? parsedList.get(2) : (v2 + 10);

        int[] sorted = new int[]{v1, v2, v3};
        java.util.Arrays.sort(sorted);
        int minVal = sorted[0];
        int midVal = sorted[1];
        int maxVal = sorted[2];

        boolean isLL = (v1 > v2 && v2 > v3);
        boolean isRR = (v1 < v2 && v2 < v3);
        boolean isLR = (v1 > v2 && v3 > v2 && v3 < v1);
        boolean isRL = (v1 < v2 && v3 < v2 && v3 > v1);
        boolean isBalanced = !isLL && !isRR && !isLR && !isRL;

        // Step <= 0: Initial / Preview state — show incoming key queue waiting to be inserted
        if (step <= 0) {
            int display = Math.min(parsedList.size(), 6);
            float boxR = 24f;
            float gap = 20f;
            float totalW = display * 2 * boxR + (display - 1) * gap;
            float startX = cx - totalW / 2f + boxR;

            subTextPaint.setColor(Color.parseColor("#94A3B8"));
            subTextPaint.setTextSize(14f);
            subTextPaint.setFakeBoldText(false);
            canvas.drawText("Incoming Key Sequence (" + parsedList.size() + " values) — Ready to Insert:", cx, rootY - 10f, subTextPaint);

            for (int i = 0; i < display; i++) {
                float x = startX + i * (2 * boxR + gap);
                int col = (i == 0) ? Color.parseColor("#4338CA") : Color.parseColor("#1E3A5F");
                drawCircleNode(canvas, x, rootY + boxR + 15f, String.valueOf(parsedList.get(i)), col, boxR);
                if (i < display - 1) {
                    subTextPaint.setColor(Color.parseColor("#6366F1"));
                    subTextPaint.setTextSize(22f);
                    canvas.drawText("\u203a", x + boxR + gap / 2f - 2f, rootY + boxR + 23f, subTextPaint);
                }
            }

            drawBfBadge(canvas, cx, rootY + boxR * 2 + 45f, "READY TO INSERT", Color.parseColor("#10B981"));

            subTextPaint.setColor(Color.parseColor("#10B981"));
            subTextPaint.setTextSize(14f);
            subTextPaint.setFakeBoldText(true);
            canvas.drawText("Tap Play or Step to insert nodes incrementally and visualize rebalancing rotations.", cx, rootY + boxR * 2 + 75f, subTextPaint);
            return;
        }

        if (step == 1) {
            drawCircleNode(canvas, cx, rootY, String.valueOf(v1), Color.parseColor("#4338CA"), 26f);
            drawBfBadge(canvas, cx + 32f, rootY - 14f, "BF: 0", Color.parseColor("#10B981"));
            subTextPaint.setColor(Color.parseColor("#94A3B8"));
            subTextPaint.setTextSize(14f);
            canvas.drawText("Step 1: Root " + v1 + " inserted. Height = 1, Balance Factor = 0.", cx, rootY + 70f, subTextPaint);
            return;
        }

        if (step == 2) {
            float y1 = rootY + 80f;
            boolean leftChild = (v2 < v1);
            float xChild = leftChild ? (cx - 80f) : (cx + 80f);
            String bfRoot = leftChild ? "BF: +1" : "BF: -1";
            linePaint.setColor(Color.parseColor("#64748B"));
            canvas.drawLine(cx, rootY + 26f, xChild, y1 - 24f, linePaint);
            drawCircleNode(canvas, cx, rootY, String.valueOf(v1), Color.parseColor("#4338CA"), 26f);
            drawBfBadge(canvas, cx + 32f, rootY - 14f, bfRoot, Color.parseColor("#10B981"));
            drawCircleNode(canvas, xChild, y1, String.valueOf(v2), Color.parseColor("#F59E0B"), 24f);
            drawBfBadge(canvas, xChild + 28f, y1 - 12f, "BF: 0", Color.parseColor("#10B981"));
            subTextPaint.setColor(Color.parseColor("#94A3B8"));
            subTextPaint.setTextSize(14f);
            canvas.drawText("Step 2: Inserted " + v2 + (leftChild ? " to left." : " to right.") + " Root " + bfRoot + ". Tree balanced.", cx, y1 + 65f, subTextPaint);
            return;
        }

        if (step == 3 && !isBalanced) {
            float y1 = rootY + 70f;
            float y2 = y1 + 70f;
            float xChild1 = isLL || isLR ? (cx - 60f) : (cx + 60f);
            float xChild2 = isLL ? (xChild1 - 60f) : (isLR ? (cx - 10f) : (isRL ? (cx + 10f) : (xChild1 + 60f)));
            String imbText = isLL ? "LL Heavy Imbalance" : (isLR ? "LR Heavy Imbalance" : (isRL ? "RL Heavy Imbalance" : "RR Heavy Imbalance"));
            String bfVal = isLL || isLR ? "BF: +2" : "BF: -2";

            linePaint.setColor(Color.parseColor("#EF4444"));
            canvas.drawLine(cx, rootY + 26f, xChild1, y1 - 24f, linePaint);
            canvas.drawLine(xChild1, y1 + 24f, xChild2, y2 - 24f, linePaint);
            drawCircleNode(canvas, cx, rootY, String.valueOf(v1), Color.parseColor("#DC2626"), 26f);
            drawBfBadge(canvas, cx + 32f, rootY - 14f, bfVal, Color.parseColor("#EF4444"));
            drawCircleNode(canvas, xChild1, y1, String.valueOf(v2), Color.parseColor("#B45309"), 24f);
            drawBfBadge(canvas, xChild1 + 28f, y1 - 12f, isLR ? "BF: -1" : (isLL ? "BF: +1" : (isRL ? "BF: +1" : "BF: -1")), Color.parseColor("#F59E0B"));
            drawCircleNode(canvas, xChild2, y2, String.valueOf(v3), Color.parseColor("#DC2626"), 24f);
            drawBfBadge(canvas, xChild2 + 28f, y2 - 12f, "BF: 0", Color.parseColor("#10B981"));
            subTextPaint.setColor(Color.parseColor("#EF4444"));
            subTextPaint.setTextSize(14f);
            canvas.drawText("Step 3: " + imbText + "! Root " + v1 + " has " + bfVal + ". Rotation required.", cx, y2 + 50f, subTextPaint);
            return;
        }

        if (step == 4 && !isBalanced) {
            float y1 = rootY + 70f;
            float y2 = y1 + 70f;
            float xChild1 = isLL || isLR ? (cx - 60f) : (cx + 60f);
            float xChild2 = isLL ? (xChild1 - 60f) : (isLR ? (cx - 10f) : (isRL ? (cx + 10f) : (xChild1 + 60f)));
            String rotDesc = isLL
                ? "Rotate Right on pivot " + v2 + ". " + v2 + " rises to Root, " + v1 + " becomes Right child."
                : (isLR
                    ? "Double Rotate (LR): Left on " + v2 + ", Right on " + v1 + ". " + v3 + " rises to Root."
                    : (isRL
                        ? "Double Rotate (RL): Right on " + v2 + ", Left on " + v1 + ". " + v3 + " rises to Root."
                        : "Rotate Left on pivot " + v2 + ". " + v2 + " rises to Root, " + v1 + " becomes Left child."));

            linePaint.setColor(Color.parseColor("#F59E0B"));
            canvas.drawLine(cx, rootY + 26f, xChild1, y1 - 24f, linePaint);
            canvas.drawLine(xChild1, y1 + 24f, xChild2, y2 - 24f, linePaint);
            drawCircleNode(canvas, cx, rootY, String.valueOf(v1), Color.parseColor("#312E81"), 26f);
            drawCircleNode(canvas, xChild1, y1, String.valueOf(v2), Color.parseColor((isLR || isRL) ? "#312E81" : "#F59E0B"), 26f);
            if (!isLR && !isRL) {
                drawBfBadge(canvas, xChild1 + 34f, y1 - 14f, "PIVOT", Color.parseColor("#F59E0B"));
            }
            drawCircleNode(canvas, xChild2, y2, String.valueOf(v3), Color.parseColor((isLR || isRL) ? "#F59E0B" : "#312E81"), 24f);
            if (isLR || isRL) {
                drawBfBadge(canvas, xChild2 + 34f, y2 - 14f, "PIVOT", Color.parseColor("#F59E0B"));
            }
            subTextPaint.setColor(Color.parseColor("#F59E0B"));
            subTextPaint.setTextSize(14f);
            canvas.drawText("Step 4: " + rotDesc, cx, y2 + 50f, subTextPaint);
            return;
        }

        // Final step / restored: Perfectly balanced BST with minVal on Left, midVal at Root, maxVal on Right
        drawCircleNode(canvas, cx, rootY, String.valueOf(midVal), Color.parseColor("#4338CA"), 26f);
        drawBfBadge(canvas, cx + 32f, rootY - 14f, "BF: 0", Color.parseColor("#10B981"));

        float y1 = rootY + 80f;
        float xL = cx - 100f;
        float xR = cx + 100f;

        linePaint.setColor(Color.parseColor("#64748B"));
        canvas.drawLine(cx, rootY + 26f, xL, y1 - 24f, linePaint);
        canvas.drawLine(cx, rootY + 26f, xR, y1 - 24f, linePaint);

        drawCircleNode(canvas, xL, y1, String.valueOf(minVal), Color.parseColor("#312E81"), 24f);
        drawBfBadge(canvas, xL + 28f, y1 - 12f, "BF: 0", Color.parseColor("#10B981"));

        drawCircleNode(canvas, xR, y1, String.valueOf(maxVal), Color.parseColor("#312E81"), 24f);
        drawBfBadge(canvas, xR + 28f, y1 - 12f, "BF: 0", Color.parseColor("#10B981"));

        subTextPaint.setColor(Color.parseColor("#94A3B8"));
        subTextPaint.setTextSize(14f);
        subTextPaint.setFakeBoldText(false);
        canvas.drawText("Balance Factor BF = Height(Left) - Height(Right) strictly in {-1, 0, 1}", cx, y1 + 65f, subTextPaint);
        canvas.drawText("Guaranteed Maximum Height <= 1.44 log2(N) via LL, RR, LR, RL rotations", cx, y1 + 88f, subTextPaint);
    }

    private void drawMultiNodeAvlTree(Canvas canvas, TechniqueVisualizerView view, float cx, float rootY, List<Integer> nodes, int step) {
        int n = nodes.size();

        // ── Step <= 0: Preview — show insertion queue, not the final balanced result ──
        if (step <= 0) {
            int display = Math.min(n, 6);
            float boxR = 22f;
            float gap  = 16f;
            float totalW = display * 2 * boxR + (display - 1) * gap;
            float startX = cx - totalW / 2f + boxR;

            subTextPaint.setColor(Color.parseColor("#94A3B8"));
            subTextPaint.setTextSize(13f);
            subTextPaint.setFakeBoldText(false);
            canvas.drawText("Insertion order (" + n + " values) — press Step/Play to animate:", cx, rootY - 10f, subTextPaint);

            for (int i = 0; i < display; i++) {
                float x = startX + i * (2 * boxR + gap);
                int col = (i == 0) ? Color.parseColor("#4338CA") : Color.parseColor("#1E3A5F");
                drawCircleNode(canvas, x, rootY + boxR + 10f, String.valueOf(nodes.get(i)), col, boxR);
                if (i < display - 1) {
                    subTextPaint.setColor(Color.parseColor("#6366F1"));
                    subTextPaint.setTextSize(20f);
                    canvas.drawText("\u203a", x + boxR + gap / 2f - 2f, rootY + boxR + 17f, subTextPaint);
                }
            }
            if (n > 6) {
                subTextPaint.setColor(Color.parseColor("#94A3B8"));
                subTextPaint.setTextSize(13f);
                canvas.drawText("+" + (n - 6) + " more", cx + totalW / 2f + 10f, rootY + boxR + 17f, subTextPaint);
            }

            subTextPaint.setColor(Color.parseColor("#6366F1"));
            subTextPaint.setTextSize(14f);
            subTextPaint.setFakeBoldText(true);
            canvas.drawText("AVL self-balances after every insertion (rotations restore |BF| \u2264 1)", cx, rootY + boxR * 2 + 34f, subTextPaint);
            return;
        }

        float pulse = view.getPulseProgress();

        // ── Step 1: Insert root only ──
        if (step == 1) {
            drawCircleNode(canvas, cx, rootY, String.valueOf(nodes.get(0)), Color.parseColor("#4338CA"), 26f, true, pulse);
            drawBfBadge(canvas, cx + 32f, rootY - 14f, "BF: 0", Color.parseColor("#10B981"));
            subTextPaint.setColor(Color.parseColor("#94A3B8"));
            subTextPaint.setTextSize(14f);
            subTextPaint.setFakeBoldText(false);
            canvas.drawText("Step 1: Inserted root " + nodes.get(0) + ". Height = 1, BF = 0. Balanced.", cx, rootY + 72f, subTextPaint);
            return;
        }

        // ── Step 2: Root + second node ──
        if (step == 2 && n >= 2) {
            int v0 = nodes.get(0), v1 = nodes.get(1);
            float y1 = rootY + 85f;
            boolean goLeft = v1 < v0;
            float xC = goLeft ? (cx - 85f) : (cx + 85f);
            linePaint.setColor(Color.parseColor("#64748B"));
            canvas.drawLine(cx, rootY + 26f, xC, y1 - 24f, linePaint);
            drawCircleNode(canvas, cx, rootY, String.valueOf(v0), Color.parseColor("#4338CA"), 26f);
            drawBfBadge(canvas, cx + 32f, rootY - 14f, goLeft ? "BF: +1" : "BF: -1", Color.parseColor("#10B981"));
            drawCircleNode(canvas, xC, y1, String.valueOf(v1), Color.parseColor("#F59E0B"), 24f, true, pulse);
            drawBfBadge(canvas, xC + 28f, y1 - 12f, "BF: 0", Color.parseColor("#10B981"));
            subTextPaint.setColor(Color.parseColor("#94A3B8"));
            subTextPaint.setTextSize(14f);
            subTextPaint.setFakeBoldText(false);
            canvas.drawText("Step 2: Inserted " + v1 + (goLeft ? " left of " : " right of ") + v0 + ". |BF| \u2264 1, balanced.", cx, y1 + 65f, subTextPaint);
            return;
        }

        // ── Step 3: Three nodes — show potential imbalance ──
        if (step == 3 && n >= 3) {
            int v0 = nodes.get(0), v1 = nodes.get(1), v2 = nodes.get(2);
            boolean isLL = v0 > v1 && v1 > v2;
            boolean isRR = v0 < v1 && v1 < v2;
            boolean isLR = v0 > v2 && v2 > v1;
            boolean isRL = v0 < v2 && v2 < v1;
            boolean imb = isLL || isRR || isLR || isRL;
            float y1 = rootY + 70f, y2 = y1 + 68f;
            float x1 = (isLL || isLR) ? cx - 65f : cx + 65f;
            float x2;
            if (isLL) x2 = x1 - 55f;
            else if (isRR) x2 = x1 + 55f;
            else if (isLR) x2 = cx - 10f;
            else if (isRL) x2 = cx + 10f;
            else x2 = v2 < v0 ? cx - 80f : cx + 80f;
            int rootCol = imb ? Color.parseColor("#DC2626") : Color.parseColor("#4338CA");
            int badgeCol = imb ? Color.parseColor("#EF4444") : Color.parseColor("#10B981");
            String bfRoot = (isLL || isLR) ? "BF: +2!" : (imb ? "BF: -2!" : "BF: 0");
            linePaint.setColor(imb ? Color.parseColor("#EF4444") : Color.parseColor("#64748B"));
            canvas.drawLine(cx, rootY + 26f, x1, y1 - 24f, linePaint);
            canvas.drawLine(x1, y1 + 24f, x2, y2 - 22f, linePaint);
            drawCircleNode(canvas, cx, rootY, String.valueOf(v0), rootCol, 26f, imb, pulse);
            drawBfBadge(canvas, cx + 32f, rootY - 14f, bfRoot, badgeCol);
            drawCircleNode(canvas, x1, y1, String.valueOf(v1), Color.parseColor("#B45309"), 24f);
            drawBfBadge(canvas, x1 + 28f, y1 - 12f, imb ? "BF: \u00b11" : "BF: 0", Color.parseColor("#F59E0B"));
            drawCircleNode(canvas, x2, y2, String.valueOf(v2), imb ? Color.parseColor("#DC2626") : Color.parseColor("#312E81"), 24f, true, pulse);
            drawBfBadge(canvas, x2 + 28f, y2 - 12f, "BF: 0", Color.parseColor("#10B981"));
            subTextPaint.setColor(imb ? Color.parseColor("#EF4444") : Color.parseColor("#10B981"));
            subTextPaint.setTextSize(14f);
            subTextPaint.setFakeBoldText(imb);
            canvas.drawText(imb ? "Step 3: Imbalance at root " + v0 + " (|BF|=2)! Rotation required." : "Step 3: Three nodes, still balanced. Continuing insertions.", cx, y2 + 50f, subTextPaint);
            return;
        }

        // ── Step 4: Rotation in progress ──
        if (step == 4) {
            int[] bal = AvlTreeStepEngine.buildBalancedTreeArray(toIntArray(nodes));
            int rootV = bal[0];
            int childL = bal.length > 1 ? bal[1] : rootV - 10;
            int childR = bal.length > 2 ? bal[2] : rootV + 10;
            float y1 = rootY + 85f;
            linePaint.setColor(Color.parseColor("#F59E0B"));
            canvas.drawLine(cx, rootY + 26f, cx - 100f, y1 - 24f, linePaint);
            canvas.drawLine(cx, rootY + 26f, cx + 100f, y1 - 24f, linePaint);
            drawCircleNode(canvas, cx, rootY, String.valueOf(rootV), Color.parseColor("#312E81"), 26f, true, pulse);
            drawBfBadge(canvas, cx + 32f, rootY - 14f, "BF: 0", Color.parseColor("#F59E0B"));
            drawCircleNode(canvas, cx - 100f, y1, String.valueOf(childL), Color.parseColor("#4338CA"), 23f);
            drawCircleNode(canvas, cx + 100f, y1, String.valueOf(childR), Color.parseColor("#4338CA"), 23f);
            subTextPaint.setColor(Color.parseColor("#F59E0B"));
            subTextPaint.setTextSize(14f);
            subTextPaint.setFakeBoldText(true);
            canvas.drawText("Step 4: AVL rotation(s) applied. Balancing " + n + "-node tree around root " + rootV + ".", cx, y1 + 55f, subTextPaint);
            return;
        }

        // ── Step 5+ / final: Balanced complete tree ──
        int[] balanced = AvlTreeStepEngine.buildBalancedTreeArray(toIntArray(nodes));
        int count = Math.min(balanced.length, 7);

        float yL1 = rootY + 70f;
        float yL2 = yL1 + 70f;
        float xL1_L = cx - 110f;
        float xL1_R = cx + 110f;
        float xL2_LL = cx - 160f;
        float xL2_LR = cx - 60f;
        float xL2_RL = cx + 60f;
        float xL2_RR = cx + 160f;

        linePaint.setColor(Color.parseColor("#64748B"));
        if (count > 1) canvas.drawLine(cx, rootY + 22f, xL1_L, yL1 - 22f, linePaint);
        if (count > 2) canvas.drawLine(cx, rootY + 22f, xL1_R, yL1 - 22f, linePaint);
        if (count > 3) canvas.drawLine(xL1_L, yL1 + 22f, xL2_LL, yL2 - 20f, linePaint);
        if (count > 4) canvas.drawLine(xL1_L, yL1 + 22f, xL2_LR, yL2 - 20f, linePaint);
        if (count > 5) canvas.drawLine(xL1_R, yL1 + 22f, xL2_RL, yL2 - 20f, linePaint);
        if (count > 6) canvas.drawLine(xL1_R, yL1 + 22f, xL2_RR, yL2 - 20f, linePaint);

        drawCircleNode(canvas, cx, rootY, String.valueOf(balanced[0]), Color.parseColor("#4338CA"), 24f);
        drawBfBadge(canvas, cx + 32f, rootY - 12f, "BF: 0", Color.parseColor("#10B981"));

        if (count > 1) {
            drawCircleNode(canvas, xL1_L, yL1, String.valueOf(balanced[1]), Color.parseColor("#312E81"), 22f);
            drawBfBadge(canvas, xL1_L + 28f, yL1 - 10f, "BF: 0", Color.parseColor("#10B981"));
        }
        if (count > 2) {
            drawCircleNode(canvas, xL1_R, yL1, String.valueOf(balanced[2]), Color.parseColor("#312E81"), 22f);
            drawBfBadge(canvas, xL1_R + 28f, yL1 - 10f, "BF: 0", Color.parseColor("#10B981"));
        }
        if (count > 3) {
            drawCircleNode(canvas, xL2_LL, yL2, String.valueOf(balanced[3]), Color.parseColor("#1E293B"), 20f);
            drawBfBadge(canvas, xL2_LL + 26f, yL2 - 10f, "BF: 0", Color.parseColor("#10B981"));
        }
        if (count > 4) {
            drawCircleNode(canvas, xL2_LR, yL2, String.valueOf(balanced[4]), Color.parseColor("#1E293B"), 20f);
            drawBfBadge(canvas, xL2_LR + 26f, yL2 - 10f, "BF: 0", Color.parseColor("#10B981"));
        }
        if (count > 5) {
            drawCircleNode(canvas, xL2_RL, yL2, String.valueOf(balanced[5]), Color.parseColor("#1E293B"), 20f);
            drawBfBadge(canvas, xL2_RL + 26f, yL2 - 10f, "BF: 0", Color.parseColor("#10B981"));
        }
        if (count > 6) {
            drawCircleNode(canvas, xL2_RR, yL2, String.valueOf(balanced[6]), Color.parseColor("#1E293B"), 20f);
            drawBfBadge(canvas, xL2_RR + 26f, yL2 - 10f, "BF: 0", Color.parseColor("#10B981"));
        }

        subTextPaint.setColor(Color.parseColor("#10B981"));
        subTextPaint.setTextSize(14f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText("Balanced " + count + "-Node AVL Tree: All Balance Factors strictly within {-1, 0, 1}.", cx, yL2 + 45f, subTextPaint);
    }

    private static int[] toIntArray(List<Integer> list) {
        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) arr[i] = list.get(i);
        return arr;
    }

    private void drawBfBadge(Canvas canvas, float x, float y, String text, int color) {
        rect.set(x - 24f, y - 10f, x + 24f, y + 10f);
        badgePaint.setColor(color);
        canvas.drawRoundRect(rect, 4f, 4f, badgePaint);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(11f);
        canvas.drawText(text, x, y + 4f, textPaint);
    }

    // =========================================================================
    // 6. Red-Black Tree
    // =========================================================================
    private void drawRedBlackTree(Canvas canvas, TechniqueVisualizerView view, int[] arr, int hA, int hB) {
        float cx = view.getWidth() / 2f;
        float top = 48f;

        subTextPaint.setColor(Color.parseColor("#EF4444"));
        subTextPaint.setTextSize(24f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText("RED-BLACK TREE (BALANCED BST)", cx, top, subTextPaint);

        int step = view.getStepIndex();
        String expr = view.getExpressionInput();
        List<Integer> parsedList = new ArrayList<>();
        if (expr != null && !expr.trim().isEmpty()) {
            try {
                for (String p : expr.trim().split("[,\\s]+")) {
                    p = p.trim();
                    if (!p.isEmpty()) parsedList.add(Integer.parseInt(p));
                }
            } catch (Exception ignored) {}
        }
        if (parsedList.isEmpty() && arr != null && arr.length > 0) {
            for (int val : arr) parsedList.add(val);
        }
        if (parsedList.isEmpty()) {
            parsedList.add(10); parsedList.add(15); parsedList.add(25);
        }

        float rootY = top + 45f;

        if (parsedList.size() > 3) {
            drawMultiNodeRedBlackTree(canvas, cx, rootY, parsedList, step);
            return;
        }

        int v1 = parsedList.get(0);
        int v2 = parsedList.size() > 1 ? parsedList.get(1) : (v1 + 10);
        int v3 = parsedList.size() > 2 ? parsedList.get(2) : (v2 + 10);

        int[] sorted = new int[]{v1, v2, v3};
        java.util.Arrays.sort(sorted);
        int minVal = sorted[0];
        int midVal = sorted[1];
        int maxVal = sorted[2];

        // Step <= 0: Initial / Preview state — show incoming key queue waiting to be inserted
        if (step <= 0) {
            int display = Math.min(parsedList.size(), 6);
            float boxR = 24f;
            float gap = 20f;
            float totalW = display * 2 * boxR + (display - 1) * gap;
            float startX = cx - totalW / 2f + boxR;

            subTextPaint.setColor(Color.parseColor("#94A3B8"));
            subTextPaint.setTextSize(14f);
            subTextPaint.setFakeBoldText(false);
            canvas.drawText("Incoming Key Sequence (" + parsedList.size() + " values) — Ready to Insert:", cx, rootY - 10f, subTextPaint);

            for (int i = 0; i < display; i++) {
                float x = startX + i * (2 * boxR + gap);
                int col = (i == 0) ? Color.parseColor("#0F172A") : Color.parseColor("#DC2626");
                drawCircleNode(canvas, x, rootY + boxR + 15f, String.valueOf(parsedList.get(i)), col, boxR);
                if (i < display - 1) {
                    subTextPaint.setColor(Color.parseColor("#EF4444"));
                    subTextPaint.setTextSize(22f);
                    canvas.drawText("\u203a", x + boxR + gap / 2f - 2f, rootY + boxR + 23f, subTextPaint);
                }
            }

            drawBfBadge(canvas, cx, rootY + boxR * 2 + 45f, "READY TO INSERT", Color.parseColor("#10B981"));

            subTextPaint.setColor(Color.parseColor("#EF4444"));
            subTextPaint.setTextSize(14f);
            subTextPaint.setFakeBoldText(true);
            canvas.drawText("Red-Black Tree: Root is always BLACK. Tap Play or Step to animate insertions & rotations.", cx, rootY + boxR * 2 + 75f, subTextPaint);
            return;
        }

        float pulse = view.getPulseProgress();

        if (step == 1) {
            drawCircleNode(canvas, cx, rootY, v1 + "(B)", Color.parseColor("#0F172A"), 26f, true, pulse);
            subTextPaint.setColor(Color.parseColor("#94A3B8"));
            subTextPaint.setTextSize(14f);
            canvas.drawText("Step 1: Insert " + v1 + " as Root. Root Property: Must always be BLACK.", cx, rootY + 70f, subTextPaint);
            return;
        }

        if (step == 2) {
            float y1 = rootY + 80f;
            boolean leftChild = (v2 < v1);
            float xChild = leftChild ? (cx - 80f) : (cx + 80f);
            linePaint.setColor(Color.parseColor("#64748B"));
            canvas.drawLine(cx, rootY + 26f, xChild, y1 - 24f, linePaint);
            drawCircleNode(canvas, cx, rootY, v1 + "(B)", Color.parseColor("#0F172A"), 26f);
            drawCircleNode(canvas, xChild, y1, v2 + "(R)", Color.parseColor("#DC2626"), 24f, true, pulse);
            subTextPaint.setColor(Color.parseColor("#34D399"));
            subTextPaint.setTextSize(14f);
            canvas.drawText("Step 2: Insert " + v2 + " (RED)" + (leftChild ? " to left." : " to right.") + " Parent is BLACK. No violation! Valid.", cx, y1 + 65f, subTextPaint);
            return;
        }

        if (step == 3) {
            float y1 = rootY + 70f;
            float y2 = y1 + 70f;
            boolean left1 = (v2 < v1);
            float x1 = left1 ? (cx - 70f) : (cx + 70f);
            boolean left2 = (v3 < v2);
            float x2 = left2 ? (x1 - 50f) : (x1 + 50f);
            linePaint.setColor(Color.parseColor("#EF4444"));
            canvas.drawLine(cx, rootY + 26f, x1, y1 - 24f, linePaint);
            canvas.drawLine(x1, y1 + 24f, x2, y2 - 24f, linePaint);
            drawCircleNode(canvas, cx, rootY, v1 + "(B)", Color.parseColor("#0F172A"), 26f);
            drawCircleNode(canvas, x1, y1, v2 + "(R)", Color.parseColor("#DC2626"), 24f, true, pulse);
            drawCircleNode(canvas, x2, y2, v3 + "(R)", Color.parseColor("#DC2626"), 24f, true, pulse);
            subTextPaint.setColor(Color.parseColor("#EF4444"));
            subTextPaint.setTextSize(14f);
            subTextPaint.setFakeBoldText(true);
            canvas.drawText("Step 3: VIOLATION! Both parent " + v2 + " and child " + v3 + " are RED.", cx, y2 + 50f, subTextPaint);
            return;
        }

        if (step == 4) {
            float y1 = rootY + 70f;
            float y2 = y1 + 70f;
            boolean left1 = (v2 < v1);
            float x1 = left1 ? (cx - 70f) : (cx + 70f);
            boolean left2 = (v3 < v2);
            float x2 = left2 ? (x1 - 50f) : (x1 + 50f);
            linePaint.setColor(Color.parseColor("#F59E0B"));
            canvas.drawLine(cx, rootY + 26f, x1, y1 - 24f, linePaint);
            canvas.drawLine(x1, y1 + 24f, x2, y2 - 24f, linePaint);
            drawCircleNode(canvas, cx, rootY, minVal + "(->R)", Color.parseColor("#B45309"), 26f, true, pulse);
            drawCircleNode(canvas, x1, y1, midVal + "(->B)", Color.parseColor("#0F172A"), 26f, true, pulse);
            drawCircleNode(canvas, x2, y2, maxVal + "(R)", Color.parseColor("#DC2626"), 24f);
            subTextPaint.setColor(Color.parseColor("#F59E0B"));
            subTextPaint.setTextSize(14f);
            canvas.drawText("Step 4: Rotate around pivot " + midVal + ". Recolor " + midVal + " to BLACK, " + minVal + " to RED.", cx, y2 + 50f, subTextPaint);
            return;
        }

        // Step 5 or preview: Balanced Red-Black tree
        drawCircleNode(canvas, cx, rootY, midVal + "(B)", Color.parseColor("#0F172A"), 26f);
        float y1 = rootY + 80f;
        float xL = cx - 100f;
        float xR = cx + 100f;
        linePaint.setColor(Color.parseColor("#64748B"));
        canvas.drawLine(cx, rootY + 26f, xL, y1 - 24f, linePaint);
        canvas.drawLine(cx, rootY + 26f, xR, y1 - 24f, linePaint);
        drawCircleNode(canvas, xL, y1, minVal + "(R)", Color.parseColor("#DC2626"), 24f);
        drawCircleNode(canvas, xR, y1, maxVal + "(R)", Color.parseColor("#DC2626"), 24f);

        float y2 = y1 + 65f;
        subTextPaint.setColor(Color.parseColor("#34D399"));
        subTextPaint.setTextSize(14f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText("[x] Root is Black  [x] No Adjacent Red Nodes  [x] Equal Black-Height = 2", cx, y2, subTextPaint);
        subTextPaint.setColor(Color.parseColor("#94A3B8"));
        subTextPaint.setTextSize(13f);
        subTextPaint.setFakeBoldText(false);
        canvas.drawText("Rebalancing: Recoloring (Red Uncle) or Rotations (Black Uncle) in O(1) amortized", cx, y2 + 24f, subTextPaint);
    }

    private void drawMultiNodeRedBlackTree(Canvas canvas, float cx, float rootY, List<Integer> nodes, int step) {
        int n = nodes.size();

        // ── Step <= 0: Preview — insertion queue ──
        if (step <= 0) {
            int display = Math.min(n, 6);
            float boxR = 22f;
            float gap  = 16f;
            float totalW = display * 2 * boxR + (display - 1) * gap;
            float startX = cx - totalW / 2f + boxR;

            subTextPaint.setColor(Color.parseColor("#94A3B8"));
            subTextPaint.setTextSize(13f);
            subTextPaint.setFakeBoldText(false);
            canvas.drawText("Insertion order (" + n + " values) — press Step/Play to animate:", cx, rootY - 10f, subTextPaint);

            for (int i = 0; i < display; i++) {
                float x = startX + i * (2 * boxR + gap);
                int col = (i == 0) ? Color.parseColor("#0F172A") : Color.parseColor("#DC2626");
                drawCircleNode(canvas, x, rootY + boxR + 10f, String.valueOf(nodes.get(i)), col, boxR);
                if (i < display - 1) {
                    subTextPaint.setColor(Color.parseColor("#EF4444"));
                    subTextPaint.setTextSize(20f);
                    canvas.drawText("\u203a", x + boxR + gap / 2f - 2f, rootY + boxR + 17f, subTextPaint);
                }
            }
            if (n > 6) {
                subTextPaint.setColor(Color.parseColor("#94A3B8"));
                subTextPaint.setTextSize(13f);
                canvas.drawText("+" + (n - 6) + " more", cx + totalW / 2f + 10f, rootY + boxR + 17f, subTextPaint);
            }

            subTextPaint.setColor(Color.parseColor("#EF4444"));
            subTextPaint.setTextSize(14f);
            subTextPaint.setFakeBoldText(true);
            canvas.drawText("Red-Black Tree: Root is always BLACK. Recoloring/rotations fix consecutive RED violations.", cx, rootY + boxR * 2 + 34f, subTextPaint);
            return;
        }

        // ── Step 1: Root only (BLACK) ──
        if (step == 1) {
            drawCircleNode(canvas, cx, rootY, nodes.get(0) + "(B)", Color.parseColor("#0F172A"), 26f);
            subTextPaint.setColor(Color.parseColor("#94A3B8"));
            subTextPaint.setTextSize(14f);
            subTextPaint.setFakeBoldText(false);
            canvas.drawText("Step 1: Inserted root " + nodes.get(0) + ". Property 2: Root is always BLACK.", cx, rootY + 70f, subTextPaint);
            return;
        }

        // ── Step 2: Root (B) + second child (R) ──
        if (step == 2 && n >= 2) {
            int v0 = nodes.get(0), v1 = nodes.get(1);
            float y1 = rootY + 82f;
            boolean goLeft = v1 < v0;
            float xC = goLeft ? cx - 82f : cx + 82f;
            linePaint.setColor(Color.parseColor("#64748B"));
            canvas.drawLine(cx, rootY + 26f, xC, y1 - 24f, linePaint);
            drawCircleNode(canvas, cx, rootY, v0 + "(B)", Color.parseColor("#0F172A"), 26f);
            drawCircleNode(canvas, xC, y1, v1 + "(R)", Color.parseColor("#DC2626"), 24f);
            subTextPaint.setColor(Color.parseColor("#34D399"));
            subTextPaint.setTextSize(14f);
            subTextPaint.setFakeBoldText(false);
            canvas.drawText("Step 2: Insert " + v1 + " (RED). Parent is BLACK — no violation. Valid.", cx, y1 + 65f, subTextPaint);
            return;
        }

        // ── Step 3: Third node — show RR violation ──
        if (step == 3 && n >= 3) {
            int v0 = nodes.get(0), v1 = nodes.get(1), v2 = nodes.get(2);
            float y1 = rootY + 70f, y2 = y1 + 68f;
            boolean left1 = v1 < v0;
            float x1 = left1 ? cx - 68f : cx + 68f;
            boolean left2 = v2 < v1;
            float x2 = left2 ? x1 - 52f : x1 + 52f;
            linePaint.setColor(Color.parseColor("#EF4444"));
            canvas.drawLine(cx, rootY + 26f, x1, y1 - 24f, linePaint);
            canvas.drawLine(x1, y1 + 24f, x2, y2 - 22f, linePaint);
            drawCircleNode(canvas, cx, rootY, v0 + "(B)", Color.parseColor("#0F172A"), 26f);
            drawCircleNode(canvas, x1, y1, v1 + "(R)", Color.parseColor("#DC2626"), 24f);
            drawCircleNode(canvas, x2, y2, v2 + "(R)", Color.parseColor("#DC2626"), 24f);
            subTextPaint.setColor(Color.parseColor("#EF4444"));
            subTextPaint.setTextSize(14f);
            subTextPaint.setFakeBoldText(true);
            canvas.drawText("Step 3: VIOLATION! " + v1 + "(R) \u2192 " + v2 + "(R): consecutive RED nodes. Recolor/rotate to fix.", cx, y2 + 50f, subTextPaint);
            return;
        }

        // ── Step 4: Rebalancing in progress ──
        if (step == 4) {
            int[] bal = AvlTreeStepEngine.buildBalancedTreeArray(toIntArray(nodes));
            int mid = bal[0];
            int mn  = bal.length > 1 ? bal[1] : mid - 10;
            int mx  = bal.length > 2 ? bal[2] : mid + 10;
            float y1 = rootY + 80f;
            linePaint.setColor(Color.parseColor("#F59E0B"));
            canvas.drawLine(cx, rootY + 26f, cx - 95f, y1 - 24f, linePaint);
            canvas.drawLine(cx, rootY + 26f, cx + 95f, y1 - 24f, linePaint);
            drawCircleNode(canvas, cx, rootY, mid + "(\u2192B)", Color.parseColor("#0F172A"), 26f);
            drawCircleNode(canvas, cx - 95f, y1, mn + "(\u2192R)", Color.parseColor("#B45309"), 24f);
            drawCircleNode(canvas, cx + 95f, y1, mx + "(R)", Color.parseColor("#DC2626"), 24f);
            subTextPaint.setColor(Color.parseColor("#F59E0B"));
            subTextPaint.setTextSize(14f);
            subTextPaint.setFakeBoldText(true);
            canvas.drawText("Step 4: Recoloring/rotating. Promoting " + mid + " to BLACK root, children to RED.", cx, y1 + 55f, subTextPaint);
            return;
        }

        // ── Step 5+ / final: Balanced Red-Black tree ──
        int[] balanced = AvlTreeStepEngine.buildBalancedTreeArray(toIntArray(nodes));
        int count = Math.min(balanced.length, 7);

        float yL1 = rootY + 70f;
        float yL2 = yL1 + 70f;
        float xL1_L = cx - 110f;
        float xL1_R = cx + 110f;
        float xL2_LL = cx - 160f;
        float xL2_LR = cx - 60f;
        float xL2_RL = cx + 60f;
        float xL2_RR = cx + 160f;

        linePaint.setColor(Color.parseColor("#64748B"));
        if (count > 1) canvas.drawLine(cx, rootY + 22f, xL1_L, yL1 - 22f, linePaint);
        if (count > 2) canvas.drawLine(cx, rootY + 22f, xL1_R, yL1 - 22f, linePaint);
        if (count > 3) canvas.drawLine(xL1_L, yL1 + 22f, xL2_LL, yL2 - 20f, linePaint);
        if (count > 4) canvas.drawLine(xL1_L, yL1 + 22f, xL2_LR, yL2 - 20f, linePaint);
        if (count > 5) canvas.drawLine(xL1_R, yL1 + 22f, xL2_RL, yL2 - 20f, linePaint);
        if (count > 6) canvas.drawLine(xL1_R, yL1 + 22f, xL2_RR, yL2 - 20f, linePaint);

        drawCircleNode(canvas, cx, rootY, balanced[0] + "(B)", Color.parseColor("#0F172A"), 24f);

        if (count > 1) drawCircleNode(canvas, xL1_L, yL1, balanced[1] + "(R)", Color.parseColor("#DC2626"), 22f);
        if (count > 2) drawCircleNode(canvas, xL1_R, yL1, balanced[2] + "(R)", Color.parseColor("#DC2626"), 22f);
        if (count > 3) drawCircleNode(canvas, xL2_LL, yL2, balanced[3] + "(B)", Color.parseColor("#0F172A"), 20f);
        if (count > 4) drawCircleNode(canvas, xL2_LR, yL2, balanced[4] + "(B)", Color.parseColor("#0F172A"), 20f);
        if (count > 5) drawCircleNode(canvas, xL2_RL, yL2, balanced[5] + "(B)", Color.parseColor("#0F172A"), 20f);
        if (count > 6) drawCircleNode(canvas, xL2_RR, yL2, balanced[6] + "(B)", Color.parseColor("#0F172A"), 20f);

        subTextPaint.setColor(Color.parseColor("#34D399"));
        subTextPaint.setTextSize(14f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText("Balanced " + count + "-Node Red-Black Tree: Black-Height preserved across all paths.", cx, yL2 + 45f, subTextPaint);
    }

    // =========================================================================
    // 7. Splay Tree
    // =========================================================================
    private void drawSplayTree(Canvas canvas, TechniqueVisualizerView view, int[] arr, int hA, int hB) {
        float cx = view.getWidth() / 2f;
        float top = 48f;

        subTextPaint.setColor(Color.parseColor("#F59E0B"));
        subTextPaint.setTextSize(24f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText("SPLAY TREE (SELF-ADJUSTING BST)", cx, top, subTextPaint);

        int step = view.getStepIndex();
        String expr = view.getExpressionInput();
        int[] parsed = com.dsavisualizer.app.ui.visualization.runtime.AdvancedDsStepEngine.parseFullArray(
                expr, new int[]{5, 10, 15, 20});
        if (arr != null && arr.length >= 4 && (expr == null || expr.trim().isEmpty())) {
            parsed = arr.clone();
        }
        java.util.Arrays.sort(parsed);
        int v1 = parsed[0];
        int v2 = parsed.length > 1 ? parsed[1] : (v1 + 5);
        int v3 = parsed.length > 2 ? parsed[2] : (v2 + 5);
        int v4 = parsed.length > 3 ? parsed[3] : (v3 + 5);

        float rootY = top + 45f;
        if (step <= 0) {
            // Initial State: Target node at depth 3 before splay begins
            float y1 = rootY + 55f;
            float y2 = y1 + 55f;
            float y3 = y2 + 55f;
            float x1 = cx - 40f;
            float x2 = cx;
            float x3 = cx + 40f;
            float x4 = cx + 80f;

            linePaint.setColor(Color.parseColor("#64748B"));
            canvas.drawLine(x1, rootY + 22f, x2, y1 - 22f, linePaint);
            canvas.drawLine(x2, y1 + 22f, x3, y2 - 22f, linePaint);
            canvas.drawLine(x3, y2 + 22f, x4, y3 - 22f, linePaint);

            drawCircleNode(canvas, x1, rootY, String.valueOf(v1), Color.parseColor("#1E293B"), 22f);
            drawCircleNode(canvas, x2, y1, String.valueOf(v2), Color.parseColor("#1E293B"), 22f);
            drawCircleNode(canvas, x3, y2, String.valueOf(v3), Color.parseColor("#1E293B"), 22f);
            drawCircleNode(canvas, x4, y3, String.valueOf(v4), Color.parseColor("#B45309"), 24f);
            borderPaint.setColor(Color.parseColor("#FDE68A"));
            canvas.drawCircle(x4, y3, 28f, borderPaint);

            subTextPaint.setColor(Color.parseColor("#F59E0B"));
            subTextPaint.setTextSize(14f);
            subTextPaint.setFakeBoldText(true);
            canvas.drawText("Initial State: Target key " + v4 + " located at depth 3. Tap Play or Step to splay target to root.", cx, y3 + 45f, subTextPaint);
            return;
        }

        float pulse = view.getPulseProgress();

        if (step == 1) {
            // Step 1: Deep path with accessed target v4
            float y1 = rootY + 55f;
            float y2 = y1 + 55f;
            float y3 = y2 + 55f;
            float x1 = cx - 40f;
            float x2 = cx;
            float x3 = cx + 40f;
            float x4 = cx + 80f;

            linePaint.setColor(Color.parseColor("#64748B"));
            canvas.drawLine(x1, rootY + 22f, x2, y1 - 22f, linePaint);
            canvas.drawLine(x2, y1 + 22f, x3, y2 - 22f, linePaint);
            canvas.drawLine(x3, y2 + 22f, x4, y3 - 22f, linePaint);

            drawCircleNode(canvas, x1, rootY, String.valueOf(v1), Color.parseColor("#1E293B"), 22f);
            drawCircleNode(canvas, x2, y1, String.valueOf(v2), Color.parseColor("#1E293B"), 22f);
            drawCircleNode(canvas, x3, y2, String.valueOf(v3), Color.parseColor("#1E293B"), 22f);
            drawCircleNode(canvas, x4, y3, String.valueOf(v4), Color.parseColor("#B45309"), 24f, true, pulse);
            borderPaint.setColor(Color.parseColor("#FDE68A"));
            canvas.drawCircle(x4, y3, 28f + pulse * 4f, borderPaint);

            subTextPaint.setColor(Color.parseColor("#F59E0B"));
            subTextPaint.setTextSize(14f);
            canvas.drawText("Step 1: Accessed Node " + v4 + " at depth 3. Splay initiated!", cx, y3 + 45f, subTextPaint);
            return;
        }

        if (step == 2) {
            // Step 2: Zig-Zig rotation
            float y1 = rootY + 60f;
            float y2 = y1 + 60f;
            float x1 = cx - 50f;
            float x2 = cx + 30f;
            float x3 = cx + 90f;

            linePaint.setColor(Color.parseColor("#F59E0B"));
            canvas.drawLine(x1, rootY + 22f, x2, y1 - 22f, linePaint);
            canvas.drawLine(x2, y1 + 22f, x3, y2 - 22f, linePaint);

            drawCircleNode(canvas, x1, rootY, String.valueOf(v1), Color.parseColor("#1E293B"), 22f);
            drawCircleNode(canvas, x2, y1, String.valueOf(v2), Color.parseColor("#F59E0B"), 24f, true, pulse);
            drawCircleNode(canvas, x3, y2, String.valueOf(v4), Color.parseColor("#B45309"), 24f, true, pulse);

            subTextPaint.setColor(Color.parseColor("#F59E0B"));
            subTextPaint.setTextSize(14f);
            canvas.drawText("Step 2: Zig-Zig step: Rotate Grandparent " + v2 + " left first.", cx, y2 + 45f, subTextPaint);
            return;
        }

        if (step == 3) {
            // Step 3: Intermediate elevation
            float y1 = rootY + 70f;
            float xL = cx - 70f;
            float xR = cx + 70f;

            linePaint.setColor(Color.parseColor("#64748B"));
            canvas.drawLine(cx, rootY + 22f, xL, y1 - 22f, linePaint);
            canvas.drawLine(cx, rootY + 22f, xR, y1 - 22f, linePaint);

            drawCircleNode(canvas, cx, rootY, String.valueOf(v3), Color.parseColor("#1E293B"), 22f);
            drawCircleNode(canvas, xL, y1, String.valueOf(v2), Color.parseColor("#1E293B"), 22f);
            drawCircleNode(canvas, xR, y1, String.valueOf(v4), Color.parseColor("#B45309"), 24f, true, pulse);

            subTextPaint.setColor(Color.parseColor("#F59E0B"));
            subTextPaint.setTextSize(14f);
            canvas.drawText("Step 3: Node " + v4 + " elevated to depth 1. Path halved!", cx, y1 + 55f, subTextPaint);
            return;
        }

        // Step 4, 5, or preview: Node v4 is now ROOT!
        drawCircleNode(canvas, cx, rootY, String.valueOf(v4), Color.parseColor("#B45309"), 26f, step == 4, pulse);
        borderPaint.setColor(Color.parseColor("#FDE68A"));
        canvas.drawCircle(cx, rootY, 30f + (step == 4 ? pulse * 5f : 0f), borderPaint);

        subTextPaint.setColor(Color.parseColor("#FDE68A"));
        subTextPaint.setTextSize(13f);
        canvas.drawText("ROOT (Splayed)", cx, rootY + 44f, subTextPaint);

        float y1 = rootY + 90f;
        float xL = cx - 90f;
        float xR = cx + 90f;

        linePaint.setColor(Color.parseColor("#64748B"));
        canvas.drawLine(cx, rootY + 26f, xL, y1 - 22f, linePaint);
        canvas.drawLine(cx, rootY + 26f, xR, y1 - 22f, linePaint);

        drawCircleNode(canvas, xL, y1, String.valueOf(v3), Color.parseColor("#1E293B"), 22f);
        drawCircleNode(canvas, xR, y1, String.valueOf(v1), Color.parseColor("#1E293B"), 22f);

        float y2 = y1 + 65f;
        subTextPaint.setColor(Color.parseColor("#CBD5E1"));
        subTextPaint.setTextSize(14f);
        canvas.drawText("Splay Rotations (Zig-Zig & Zig-Zag) halve depth of all accessed ancestors", cx, y2, subTextPaint);
        subTextPaint.setColor(Color.parseColor("#F59E0B"));
        canvas.drawText("Frequent keys reside at root: O(1) repeat access, amortized O(log N) operations", cx, y2 + 24f, subTextPaint);
    }

    // =========================================================================
    // 8. B-Tree / 2-3-4 Tree
    // =========================================================================
    private void drawBTree(Canvas canvas, TechniqueVisualizerView view, int[] arr, int hA, int hB) {
        float cx = view.getWidth() / 2f;
        float top = 48f;

        subTextPaint.setColor(Color.parseColor("#06B6D4"));
        subTextPaint.setTextSize(24f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText("B-TREE / 2-3-4 TREE (ORDER M=4)", cx, top, subTextPaint);

        int step = view.getStepIndex();
        String expr = view.getExpressionInput();
        int[] sorted = com.dsavisualizer.app.ui.visualization.runtime.AdvancedDsStepEngine.parseFullArray(
                expr, new int[]{10, 20, 25, 30});
        if (arr != null && arr.length >= 4 && (expr == null || expr.trim().isEmpty())) {
            sorted = arr.clone();
        }
        java.util.Arrays.sort(sorted);
        int k1 = sorted[0];
        int k2 = sorted.length > 1 ? sorted[1] : (k1 + 10);
        int k3 = sorted.length > 2 ? sorted[2] : (k2 + 5);
        int k4 = sorted.length > 3 ? sorted[3] : (k3 + 5);

        float rootY = top + 55f;
        float pulse = view.getPulseProgress();

        if (step <= 0) {
            drawBTreeNode(canvas, cx, rootY, new int[]{k1, k2}, Color.parseColor("#0E7490"));
            subTextPaint.setColor(Color.parseColor("#38BDF8"));
            subTextPaint.setTextSize(14f);
            subTextPaint.setFakeBoldText(true);
            canvas.drawText("Initial State: Root holds [" + k1 + ", " + k2 + "] (2/3 keys).", cx, rootY + 65f, subTextPaint);
            subTextPaint.setColor(Color.parseColor("#94A3B8"));
            subTextPaint.setFakeBoldText(false);
            canvas.drawText("Tap Play or Step to insert " + k4 + " and " + k3 + " and observe node splitting.", cx, rootY + 86f, subTextPaint);
            return;
        }

        if (step == 1) {
            drawBTreeNode(canvas, cx, rootY, new int[]{k1, k2}, Color.parseColor("#0E7490"), true, pulse);
            subTextPaint.setColor(Color.parseColor("#94A3B8"));
            subTextPaint.setTextSize(14f);
            canvas.drawText("Step 1: Root holds [" + k1 + ", " + k2 + "] (2/3 keys). Node has room.", cx, rootY + 65f, subTextPaint);
            return;
        }

        if (step == 2) {
            drawBTreeNode(canvas, cx, rootY, new int[]{k1, k2, k4}, Color.parseColor("#0E7490"), true, pulse);
            subTextPaint.setColor(Color.parseColor("#F59E0B"));
            subTextPaint.setTextSize(14f);
            canvas.drawText("Step 2: Insert " + k4 + " -> Node full [" + k1 + ", " + k2 + ", " + k4 + "] (3/3 keys).", cx, rootY + 65f, subTextPaint);
            return;
        }

        if (step == 3) {
            drawBTreeNode(canvas, cx, rootY, new int[]{k1, k2, k3, k4}, Color.parseColor("#991B1B"), true, pulse);
            subTextPaint.setColor(Color.parseColor("#EF4444"));
            subTextPaint.setTextSize(14f);
            subTextPaint.setFakeBoldText(true);
            canvas.drawText("Step 3: Insert " + k3 + " -> OVERFLOW! 4 keys. Split required around median " + k2 + "!", cx, rootY + 65f, subTextPaint);
            return;
        }

        // Step 4, 5, or preview: Split into parent + 2 children
        drawBTreeNode(canvas, cx, rootY, new int[]{k2}, Color.parseColor("#0E7490"), step == 4, pulse);
        float y1 = rootY + 85f;
        float xLeft = cx - 110f;
        float xRight = cx + 110f;

        linePaint.setColor(Color.parseColor("#64748B"));
        canvas.drawLine(cx - 20f, rootY + 20f, xLeft, y1 - 20f, linePaint);
        canvas.drawLine(cx + 20f, rootY + 20f, xRight, y1 - 20f, linePaint);

        drawBTreeNode(canvas, xLeft, y1, new int[]{k1}, Color.parseColor("#1E293B"));
        drawBTreeNode(canvas, xRight, y1, new int[]{k3, k4}, Color.parseColor("#1E293B"), step == 4, pulse);

        float y2 = y1 + 65f;
        subTextPaint.setColor(Color.parseColor("#94A3B8"));
        subTextPaint.setTextSize(14f);
        canvas.drawText("Nodes contain up to M-1 = 3 keys. Median key promoted on overflow.", cx, y2, subTextPaint);
        canvas.drawText("Tree grows upwards from the root, guaranteeing uniform leaf depth!", cx, y2 + 24f, subTextPaint);
    }

    private void drawBTreeNode(Canvas canvas, float cx, float cy, int[] keys, int fillColor) {
        drawBTreeNode(canvas, cx, cy, keys, fillColor, false, 0f);
    }

    private void drawBTreeNode(Canvas canvas, float cx, float cy, int[] keys, int fillColor, boolean isHighlighted, float pulse) {
        int count = Math.max(1, Math.min(keys.length, 4));
        float cellW = 42f;
        float h = 38f + (isHighlighted ? pulse * 4f : 0f);
        float totalW = cellW * count + (isHighlighted ? pulse * 6f : 0f);
        float left = cx - totalW / 2f;

        rect.set(left, cy - h / 2f, left + totalW, cy + h / 2f);
        if (isHighlighted) {
            badgePaint.setColor(fillColor);
            badgePaint.setAlpha((int) (40 + pulse * 60));
            rectFExpand(rect, 4f + pulse * 3f);
            canvas.drawRoundRect(rect, 10f, 10f, badgePaint);
            rect.set(left, cy - h / 2f, left + totalW, cy + h / 2f);
        }

        nodePaint.setColor(fillColor);
        nodePaint.setStyle(Paint.Style.FILL);
        borderPaint.setColor(isHighlighted ? Color.parseColor("#FDE68A") : Color.parseColor("#22D3EE"));
        borderPaint.setStrokeWidth(isHighlighted ? 4f : 3f);

        canvas.drawRoundRect(rect, 8f, 8f, nodePaint);
        canvas.drawRoundRect(rect, 8f, 8f, borderPaint);
        borderPaint.setStrokeWidth(3f);

        for (int i = 1; i < count; i++) {
            float divX = left + i * (totalW / count);
            canvas.drawLine(divX, cy - h / 2f, divX, cy + h / 2f, borderPaint);
        }

        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(isHighlighted ? 18f + pulse * 2f : 17f);
        textPaint.setFakeBoldText(true);
        for (int i = 0; i < count; i++) {
            float kx = left + i * (totalW / count) + (totalW / count) / 2f;
            canvas.drawText(String.valueOf(keys[i]), kx, cy + 6f, textPaint);
        }
    }

    // =========================================================================
    // 9. Disjoint Set Union (DSU)
    // =========================================================================
    private void drawDisjointSet(Canvas canvas, TechniqueVisualizerView view, int[] arr, int hA, int hB) {
        int n = 6;
        float top = 48f;
        float size = 52f;
        float gap = 14f;
        float totalWidth = n * size + (n - 1) * gap;
        float startX = (view.getWidth() - totalWidth) / 2f;

        subTextPaint.setColor(Color.parseColor("#06B6D4"));
        subTextPaint.setTextSize(24f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText("DISJOINT SET UNION (DSU WITH PATH COMPRESSION)", view.getWidth() / 2f, top, subTextPaint);

        String expr = view.getExpressionInput();
        int[] vals = com.dsavisualizer.app.ui.visualization.runtime.AdvancedDsStepEngine.parseBaseArray(expr, n, new int[]{0, 1, 2, 3, 4, 5});

        float pulse = view.getPulseProgress();
        float yNodes = top + 80f;

        for (int i = 0; i < n; i++) {
            float x = startX + i * (size + gap);
            rect.set(x, yNodes, x + size, yNodes + size);

            int parent = (arr != null && i < arr.length) ? arr[i] : i;
            boolean isRoot = (parent == i);
            boolean isHighlighted = (i == hA || i == hB);

            if (isHighlighted) {
                badgePaint.setColor(Color.parseColor("#06B6D4"));
                badgePaint.setAlpha((int) (40 + pulse * 60));
                rectFExpand(rect, 4f + pulse * 3f);
                canvas.drawRoundRect(rect, 12f, 12f, badgePaint);
                rect.set(x, yNodes, x + size, yNodes + size);
            }

            nodePaint.setColor(isRoot ? Color.parseColor("#0E7490") : Color.parseColor("#1E293B"));
            borderPaint.setColor(isHighlighted ? Color.parseColor("#67E8F9") : (isRoot ? Color.parseColor("#22D3EE") : Color.parseColor("#475569")));
            borderPaint.setStrokeWidth(isHighlighted ? 4f : 3f);
            nodePaint.setStyle(Paint.Style.FILL);

            canvas.drawRoundRect(rect, 10f, 10f, nodePaint);
            canvas.drawRoundRect(rect, 10f, 10f, borderPaint);
            borderPaint.setStrokeWidth(3f);

            textPaint.setColor(Color.WHITE);
            String nodeText = String.valueOf(vals[i]);
            float baseTextSize = nodeText.length() > 2 ? 15f : 20f;
            textPaint.setTextSize(isHighlighted ? baseTextSize * (1f + pulse * 0.12f) : baseTextSize);
            canvas.drawText(nodeText, rect.centerX(), rect.centerY() + 7f, textPaint);

            // Parent label below
            subTextPaint.setColor(isRoot ? Color.parseColor("#22D3EE") : Color.parseColor("#94A3B8"));
            subTextPaint.setTextSize(14f);
            int parentVal = (parent >= 0 && parent < n) ? vals[parent] : parent;
            canvas.drawText(isRoot ? "ROOT" : "p=" + parentVal, rect.centerX(), yNodes + size + 20f, subTextPaint);

            // Draw parent pointer arrow upwards
            if (!isRoot && parent < n) {
                float targetX = startX + parent * (size + gap) + size / 2f;
                linePaint.setColor(Color.parseColor("#38BDF8"));
                path.reset();
                path.moveTo(rect.centerX(), yNodes);
                path.quadTo((rect.centerX() + targetX) / 2f, yNodes - 30f, targetX, yNodes);
                canvas.drawPath(path, linePaint);
            }
        }

        // Summary footer
        float yFoot = yNodes + size + 65f;
        if (view.getStepIndex() <= 0) {
            subTextPaint.setColor(Color.parseColor("#38BDF8"));
            subTextPaint.setTextSize(14f);
            subTextPaint.setFakeBoldText(true);
            canvas.drawText("Initial State: Each element is an independent set (parent = self)", view.getWidth() / 2f, yFoot, subTextPaint);
            subTextPaint.setColor(Color.parseColor("#94A3B8"));
            subTextPaint.setFakeBoldText(false);
            canvas.drawText("Tap Play or Step to visualize Union and Find with Path Compression", view.getWidth() / 2f, yFoot + 22f, subTextPaint);
        } else {
            subTextPaint.setColor(Color.parseColor("#CBD5E1"));
            subTextPaint.setTextSize(14f);
            subTextPaint.setFakeBoldText(false);
            canvas.drawText("Path Compression: Every node along find path points directly to root", view.getWidth() / 2f, yFoot, subTextPaint);
            subTextPaint.setColor(Color.parseColor("#22D3EE"));
            canvas.drawText("Amortized time per operation: alpha(N) < 4 (Practically Constant Time)", view.getWidth() / 2f, yFoot + 22f, subTextPaint);
        }
    }

    // =========================================================================
    // 10. Bloom Filter
    // =========================================================================
    private void drawBloomFilter(Canvas canvas, TechniqueVisualizerView view, int[] arr, int hA, int hB) {
        int bits = 16;
        float startX = 30f;
        float top = 48f;
        float cellSize = 38f;
        float spacing = 6f;

        String expr = view.getExpressionInput();
        String[] words = (expr != null && !expr.trim().isEmpty())
                ? expr.trim().split("[,\\s]+")
                : new String[]{"apple", "banana", "cherry"};
        String w1 = words.length > 0 && !words[0].isEmpty() ? words[0] : "apple";
        String w2 = words.length > 1 && !words[1].isEmpty() ? words[1] : "banana";
        String wQuery = words.length > 2 && !words[2].isEmpty() ? words[2] : "cherry";

        int h1_1 = com.dsavisualizer.app.ui.visualization.runtime.AdvancedDsStepEngine.bloomHash1(w1);
        int h1_2 = com.dsavisualizer.app.ui.visualization.runtime.AdvancedDsStepEngine.bloomHash2(w1);
        int h1_3 = com.dsavisualizer.app.ui.visualization.runtime.AdvancedDsStepEngine.bloomHash3(w1);
        int h2_1 = com.dsavisualizer.app.ui.visualization.runtime.AdvancedDsStepEngine.bloomHash1(w2);
        int h2_2 = com.dsavisualizer.app.ui.visualization.runtime.AdvancedDsStepEngine.bloomHash2(w2);
        int h2_3 = com.dsavisualizer.app.ui.visualization.runtime.AdvancedDsStepEngine.bloomHash3(w2);

        int[] previewBits = new int[bits];
        previewBits[h1_1] = 1; previewBits[h1_2] = 1; previewBits[h1_3] = 1;
        previewBits[h2_1] = 1; previewBits[h2_2] = 1; previewBits[h2_3] = 1;

        subTextPaint.setColor(Color.parseColor("#38BDF8"));
        subTextPaint.setTextSize(24f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText("BLOOM FILTER (m=16 Bits, k=3 Hash Functions)", view.getWidth() / 2f, top, subTextPaint);

        subTextPaint.setColor(Color.parseColor("#FDE68A"));
        subTextPaint.setTextSize(14f);
        subTextPaint.setFakeBoldText(false);
        canvas.drawText("Keys: \"" + w1 + "\", \"" + w2 + "\"  |  Query: \"" + wQuery + "\"", view.getWidth() / 2f, top + 24f, subTextPaint);

        float yBits = top + 65f;

        float pulse = view.getPulseProgress();

        for (int i = 0; i < bits; i++) {
            float x = startX + i * (cellSize + spacing);
            rect.set(x, yBits, x + cellSize, yBits + cellSize);

            boolean isBitSet = (arr != null && i < arr.length) ? (arr[i] == 1) : false;
            if (view.getStepIndex() <= 0) {
                isBitSet = false;
            }
            boolean isQueried = (view.getStepIndex() > 0 && (i == hA || i == hB));

            if (isQueried) {
                badgePaint.setColor(Color.parseColor("#F59E0B"));
                badgePaint.setAlpha((int) (40 + pulse * 60));
                rectFExpand(rect, 4f + pulse * 3f);
                canvas.drawRoundRect(rect, 10f, 10f, badgePaint);
                rect.set(x, yBits, x + cellSize, yBits + cellSize);
            }

            if (isQueried) {
                nodePaint.setColor(Color.parseColor("#F59E0B")); // Amber active query
                borderPaint.setColor(Color.parseColor("#FDE68A"));
                borderPaint.setStrokeWidth(4f);
            } else if (isBitSet) {
                nodePaint.setColor(Color.parseColor("#10B981")); // Emerald 1
                borderPaint.setColor(Color.parseColor("#6EE7B7"));
                borderPaint.setStrokeWidth(3f);
            } else {
                nodePaint.setColor(Color.parseColor("#1E293B")); // Dark slate 0
                borderPaint.setColor(Color.parseColor("#334155"));
                borderPaint.setStrokeWidth(3f);
            }

            nodePaint.setStyle(Paint.Style.FILL);
            canvas.drawRoundRect(rect, 8f, 8f, nodePaint);
            canvas.drawRoundRect(rect, 8f, 8f, borderPaint);
            borderPaint.setStrokeWidth(3f);

            textPaint.setColor(Color.WHITE);
            textPaint.setTextSize(isQueried ? 19.5f + pulse * 2f : 18f);
            textPaint.setFakeBoldText(true);
            canvas.drawText(isBitSet ? "1" : "0", rect.centerX(), rect.centerY() + 6f, textPaint);

            subTextPaint.setColor(Color.parseColor("#64748B"));
            subTextPaint.setTextSize(14f);
            subTextPaint.setFakeBoldText(false);
            canvas.drawText(String.valueOf(i), rect.centerX(), yBits + cellSize + 18f, subTextPaint);
        }

        // Draw hash arrows
        if (view.getStepIndex() > 0 && hA >= 0 && hA < bits) {
            float targetX = startX + hA * (cellSize + spacing) + cellSize / 2f;
            linePaint.setColor(Color.parseColor("#F59E0B"));
            canvas.drawLine(targetX, yBits - 25f, targetX, yBits - 6f, linePaint);
            subTextPaint.setColor(Color.parseColor("#F59E0B"));
            subTextPaint.setTextSize(14f);
            canvas.drawText("h(x)", targetX, yBits - 30f, subTextPaint);
        }

        // Footer banner
        float yFoot = yBits + cellSize + 55f;
        if (view.getStepIndex() <= 0) {
            subTextPaint.setColor(Color.parseColor("#38BDF8"));
            subTextPaint.setTextSize(14f);
            subTextPaint.setFakeBoldText(true);
            canvas.drawText("Initial State: All 16 filter bits cleared to 0.", view.getWidth() / 2f, yFoot, subTextPaint);
            subTextPaint.setColor(Color.parseColor("#94A3B8"));
            subTextPaint.setFakeBoldText(false);
            canvas.drawText("Tap Play or Step to hash and insert \"" + w1 + "\" & \"" + w2 + "\", then query \"" + wQuery + "\".", view.getWidth() / 2f, yFoot + 22f, subTextPaint);
        } else {
            subTextPaint.setColor(Color.parseColor("#10B981"));
            subTextPaint.setTextSize(14f);
            subTextPaint.setFakeBoldText(true);
            canvas.drawText("Guaranteed ZERO False Negatives: If any hash bit is 0, item is DEFINITELY absent", view.getWidth() / 2f, yFoot, subTextPaint);
            subTextPaint.setColor(Color.parseColor("#94A3B8"));
            subTextPaint.setFakeBoldText(false);
            canvas.drawText("Space-efficient probabilistic membership test in strict O(k) time", view.getWidth() / 2f, yFoot + 22f, subTextPaint);
        }
    }

    // =========================================================================
    // 11. LRU Cache
    // =========================================================================
    private void drawLruCache(Canvas canvas, TechniqueVisualizerView view, int[] arr, int hA, int hB) {
        float cx = view.getWidth() / 2f;
        float top = 48f;

        subTextPaint.setColor(Color.parseColor("#A855F7"));
        subTextPaint.setTextSize(24f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText("LRU CACHE (HASH MAP + DOUBLY LINKED LIST)", cx, top, subTextPaint);

        String expr = view.getExpressionInput();
        int[] parsed = com.dsavisualizer.app.ui.visualization.runtime.AdvancedDsStepEngine.parseBaseArray(expr, 4, new int[]{1, 2, 3, 4});
        int k1 = parsed.length > 0 ? parsed[0] : 1;
        int k2 = parsed.length > 1 ? parsed[1] : 2;
        int k3 = parsed.length > 2 ? parsed[2] : 3;
        int k4 = parsed.length > 3 ? parsed[3] : 4;

        if (view.getStepIndex() <= 0 || (arr != null && arr.length == 0)) {
            float yEmpty = top + 55f;
            float emptyW = 320f;
            float emptyH = 50f;
            rect.set(cx - emptyW / 2f, yEmpty, cx + emptyW / 2f, yEmpty + emptyH);
            nodePaint.setColor(Color.parseColor("#1E293B"));
            borderPaint.setColor(Color.parseColor("#38BDF8"));
            borderPaint.setPathEffect(new DashPathEffect(new float[]{10f, 10f}, 0f));
            nodePaint.setStyle(Paint.Style.FILL);
            canvas.drawRoundRect(rect, 10f, 10f, nodePaint);
            canvas.drawRoundRect(rect, 10f, 10f, borderPaint);
            borderPaint.setPathEffect(null);

            textPaint.setColor(Color.parseColor("#38BDF8"));
            textPaint.setTextSize(16f);
            textPaint.setFakeBoldText(true);
            canvas.drawText("[ Cache Empty (Capacity = 3) ]", cx, yEmpty + 31f, textPaint);

            float yFoot = yEmpty + emptyH + 45f;
            subTextPaint.setColor(Color.parseColor("#CBD5E1"));
            subTextPaint.setTextSize(14f);
            subTextPaint.setFakeBoldText(true);
            canvas.drawText("Incoming Key Stream: put(" + k1 + "), put(" + k2 + "), put(" + k3 + "), get(" + k2 + "), put(" + k4 + ")", cx, yFoot, subTextPaint);
            subTextPaint.setColor(Color.parseColor("#94A3B8"));
            subTextPaint.setFakeBoldText(false);
            canvas.drawText("Tap Play or Step to visualize O(1) MRU head insertions and LRU tail evictions", cx, yFoot + 22f, subTextPaint);

            // HashMap representation card below
            float yMap = yFoot + 55f;
            rect.set(cx - 210f, yMap, cx + 210f, yMap + 44f);
            nodePaint.setColor(Color.parseColor("#0F172A"));
            borderPaint.setColor(Color.parseColor("#334155"));
            canvas.drawRoundRect(rect, 8f, 8f, nodePaint);
            canvas.drawRoundRect(rect, 8f, 8f, borderPaint);

            subTextPaint.setColor(Color.parseColor("#A855F7"));
            subTextPaint.setTextSize(14f);
            subTextPaint.setFakeBoldText(true);
            canvas.drawText("HashMap: { Key -> &Node }  |  get() & put() in strict O(1) time", cx, yMap + 27f, subTextPaint);
            return;
        }

        int[] displayArr;
        if (arr != null && arr.length > 0) {
            displayArr = arr;
        } else if (parsed.length >= 3) {
            displayArr = new int[]{parsed[2], parsed[1], parsed[0]};
        } else {
            displayArr = new int[]{3, 2, 1};
        }
        int count = Math.min(displayArr.length, 5);
        float startX = 60f;
        float yDll = top + 55f;
        float nodeW = 75f;
        float nodeH = 46f;
        float gap = 34f;

        // Head label (MRU)
        subTextPaint.setColor(Color.parseColor("#38BDF8"));
        subTextPaint.setTextSize(16f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText("HEAD [MRU]", startX + 35f, yDll - 15f, subTextPaint);

        float pulse = view.getPulseProgress();

        for (int i = 0; i < count; i++) {
            float x = startX + i * (nodeW + gap);
            rect.set(x, yDll, x + nodeW, yDll + nodeH);

            boolean isCurrent = (i == hA);
            if (isCurrent) {
                badgePaint.setColor(Color.parseColor("#8B5CF6"));
                badgePaint.setAlpha((int) (40 + pulse * 60));
                rectFExpand(rect, 4f + pulse * 3f);
                canvas.drawRoundRect(rect, 10f, 10f, badgePaint);
                rect.set(x, yDll, x + nodeW, yDll + nodeH);
            }

            nodePaint.setColor(isCurrent ? Color.parseColor("#8B5CF6") : Color.parseColor("#1E293B"));
            borderPaint.setColor(isCurrent ? Color.parseColor("#C4B5FD") : Color.parseColor("#475569"));
            borderPaint.setStrokeWidth(isCurrent ? 4f : 3f);
            nodePaint.setStyle(Paint.Style.FILL);

            canvas.drawRoundRect(rect, 8f, 8f, nodePaint);
            canvas.drawRoundRect(rect, 8f, 8f, borderPaint);
            borderPaint.setStrokeWidth(3f);

            int val = (i < displayArr.length) ? displayArr[i] : (i + 1);
            textPaint.setColor(Color.WHITE);
            textPaint.setTextSize(isCurrent ? 19.5f + pulse * 2f : 18f);
            textPaint.setFakeBoldText(true);
            canvas.drawText("Key " + val, rect.centerX(), rect.centerY() + 6f, textPaint);

            // Bidirectional arrows between nodes
            if (i < count - 1) {
                float right = x + nodeW;
                float nextLeft = right + gap;
                linePaint.setColor(Color.parseColor("#94A3B8"));
                canvas.drawLine(right, yDll + nodeH * 0.35f, nextLeft, yDll + nodeH * 0.35f, linePaint);
                canvas.drawLine(nextLeft, yDll + nodeH * 0.65f, right, yDll + nodeH * 0.65f, linePaint);
            }
        }

        // Tail label (LRU)
        float tailX = startX + (count - 1) * (nodeW + gap) + nodeW / 2f;
        subTextPaint.setColor(Color.parseColor("#EF4444"));
        subTextPaint.setTextSize(16f);
        canvas.drawText("TAIL [LRU]", tailX, yDll + nodeH + 24f, subTextPaint);

        // HashMap representation card below
        float yMap = yDll + nodeH + 50f;
        rect.set(cx - 210f, yMap, cx + 210f, yMap + 44f);
        nodePaint.setColor(Color.parseColor("#0F172A"));
        borderPaint.setColor(Color.parseColor("#334155"));
        canvas.drawRoundRect(rect, 8f, 8f, nodePaint);
        canvas.drawRoundRect(rect, 8f, 8f, borderPaint);

        subTextPaint.setColor(Color.parseColor("#A855F7"));
        subTextPaint.setTextSize(14f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText("HashMap: { Key -> &Node }  |  get() & put() in strict O(1) time", cx, yMap + 27f, subTextPaint);
    }

    // =========================================================================
    // Helper Drawing Primitives
    // =========================================================================
    private void drawCircleNode(Canvas canvas, float cx, float cy, String text, int fillColor, float radius) {
        drawCircleNode(canvas, cx, cy, text, fillColor, radius, false, 0f);
    }

    private void drawCircleNode(Canvas canvas, float cx, float cy, String text, int fillColor, float radius, boolean isHighlighted, float pulse) {
        float effectiveR = radius + (isHighlighted ? pulse * 5f : 0f);

        if (isHighlighted) {
            badgePaint.setColor(Color.parseColor("#F59E0B"));
            badgePaint.setAlpha((int) (40 + pulse * 60));
            canvas.drawCircle(cx, cy, effectiveR + 8f + pulse * 4f, badgePaint);
        }

        nodePaint.setColor(fillColor);
        nodePaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(cx, cy, effectiveR, nodePaint);

        borderPaint.setColor(isHighlighted ? Color.parseColor("#FDE68A") : Color.parseColor("#94A3B8"));
        borderPaint.setStrokeWidth(isHighlighted ? 4.5f : 3f);
        canvas.drawCircle(cx, cy, effectiveR, borderPaint);
        borderPaint.setStrokeWidth(3f);

        textPaint.setColor(Color.WHITE);
        float baseTextSize = radius * 0.82f;
        textPaint.setTextSize(isHighlighted ? baseTextSize * (1f + pulse * 0.12f) : baseTextSize);
        textPaint.setFakeBoldText(true);
        canvas.drawText(text, cx, cy + radius * 0.32f, textPaint);
    }

    private void drawIntervalNode(Canvas canvas, float cx, float cy, String label, int fillColor) {
        drawIntervalNode(canvas, cx, cy, label, fillColor, false, 0f);
    }

    private void drawIntervalNode(Canvas canvas, float cx, float cy, String label, int fillColor, boolean isHighlighted, float pulse) {
        float w = 96f + (isHighlighted ? pulse * 8f : 0f);
        float h = 40f + (isHighlighted ? pulse * 4f : 0f);
        rect.set(cx - w / 2f, cy - h / 2f, cx + w / 2f, cy + h / 2f);

        if (isHighlighted) {
            badgePaint.setColor(Color.parseColor("#F59E0B"));
            badgePaint.setAlpha((int) (40 + pulse * 60));
            rectFExpand(rect, 6f + pulse * 3f);
            canvas.drawRoundRect(rect, 12f, 12f, badgePaint);
            rect.set(cx - w / 2f, cy - h / 2f, cx + w / 2f, cy + h / 2f);
        }

        nodePaint.setColor(fillColor);
        nodePaint.setStyle(Paint.Style.FILL);
        borderPaint.setColor(isHighlighted ? Color.parseColor("#FDE68A") : Color.parseColor("#64748B"));
        borderPaint.setStrokeWidth(isHighlighted ? 4f : 3f);

        canvas.drawRoundRect(rect, 8f, 8f, nodePaint);
        canvas.drawRoundRect(rect, 8f, 8f, borderPaint);
        borderPaint.setStrokeWidth(3f);

        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(isHighlighted ? 16.5f + pulse * 1.5f : 15f);
        textPaint.setFakeBoldText(true);
        canvas.drawText(label, cx, cy + 5f, textPaint);
    }

    private static void rectFExpand(RectF r, float delta) {
        r.left -= delta;
        r.top -= delta;
        r.right += delta;
        r.bottom += delta;
    }
}
