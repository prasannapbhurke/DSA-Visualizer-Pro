package com.dsavisualizer.app.ui.visualization.runtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Dedicated visual step engine for Advanced Data Structures:
 * - Trie (Prefix search and node branches)
 * - Segment Tree (Range sums, point updates, tree hierarchy)
 * - Persistent Segment Tree (Versioned nodes with immutable shared subtrees)
 * - Fenwick Tree / BIT (LSB navigation with i & -i)
 * - AVL Tree (Balance factor check, LL/RR/LR/RL rotations)
 * - Red-Black Tree (Red/black coloring, recoloring and rotation invariants)
 * - Splay Tree (Zig, Zig-Zig, Zig-Zag splaying to root)
 * - B-Tree (Multi-key nodes, child pointers, node splits)
 * - Disjoint Set Union (Union by rank, path compression)
 * - Bloom Filter (Bit array hashing, membership test)
 * - LRU Cache (Hash map + Doubly Linked List node promotion/eviction)
 */
public final class AdvancedDsStepEngine extends BaseStepEngine {

    public AdvancedDsStepEngine(VisualizationRuntimeHost host) {
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
        String id = host.getTechniqueId();
        if ("ds_trie".equals(id) || "adv_trie".equals(id)) {
            advanceTrie(host);
        } else if ("ds_segment_tree".equals(id) || "adv_segment_tree".equals(id)) {
            advanceSegmentTree(host);
        } else if ("ds_persistent_segment_tree".equals(id) || "adv_persistent_st".equals(id)) {
            advancePersistentSegmentTree(host);
        } else if ("ds_fenwick_tree".equals(id) || "adv_fenwick_tree".equals(id)) {
            advanceFenwickTree(host);
        } else if ("ds_avl_tree".equals(id) || "adv_avl_tree".equals(id)) {
            advanceAvlTree(host);
        } else if ("ds_red_black_tree".equals(id) || "adv_red_black_tree".equals(id)) {
            advanceRedBlackTree(host);
        } else if ("ds_splay_tree".equals(id) || "adv_splay_tree".equals(id)) {
            advanceSplayTree(host);
        } else if ("ds_b_tree".equals(id) || "adv_b_tree".equals(id)) {
            advanceBTree(host);
        } else if ("ds_disjoint_set".equals(id) || "adv_dsu".equals(id)) {
            advanceDsu(host);
        } else if ("ds_bloom_filter".equals(id) || "adv_bloom_filter".equals(id)) {
            advanceBloomFilter(host);
        } else if ("ds_lru_cache".equals(id) || "adv_lru_cache".equals(id)) {
            advanceLruCache(host);
        } else {
            advanceGenericDs(host);
        }
    }

    private static int nextStep(VisualizationRuntimeHost host) {
        int current = host.getStepIndex();
        int step = (current < 1) ? 1 : current + 1;
        host.setStepIndex(step);
        return step;
    }

    public static int bloomHash1(String s) {
        int h = 0;
        for (int i = 0; i < s.length(); i++) h = (h * 31 + s.charAt(i)) & 0x7FFFFFFF;
        return h % 16;
    }

    public static int bloomHash2(String s) {
        int h = 0;
        for (int i = 0; i < s.length(); i++) h = (h * 17 + s.charAt(i) * 7) & 0x7FFFFFFF;
        return (h + 3) % 16;
    }

    public static int bloomHash3(String s) {
        int h = 0;
        for (int i = 0; i < s.length(); i++) h = (h * 37 + s.charAt(i) * 13) & 0x7FFFFFFF;
        return (h + 7) % 16;
    }

    public static int[] parseBaseArray(String input, int defaultLength, int[] fallback) {
        if (input != null && !input.trim().isEmpty()) {
            List<Integer> list = new ArrayList<>();
            for (String p : input.trim().split("[,\\s]+")) {
                try {
                    list.add(Integer.parseInt(p.trim()));
                } catch (Exception ignored) {}
            }
            if (!list.isEmpty()) {
                int[] res = new int[defaultLength];
                int delta = (list.size() > 1) ? (list.get(1) - list.get(0)) : 10;
                if (delta == 0) delta = 5;
                for (int i = 0; i < defaultLength; i++) {
                    if (i < list.size()) {
                        res[i] = list.get(i);
                    } else {
                        res[i] = res[i - 1] + delta;
                    }
                }
                return res;
            }
        }
        return fallback;
    }

    /** Parses ALL integers from user input without capping length. Returns fallback only if input is empty/invalid. */
    public static int[] parseFullArray(String input, int[] fallback) {
        if (input != null && !input.trim().isEmpty()) {
            List<Integer> list = new ArrayList<>();
            for (String p : input.trim().split("[,\\s]+")) {
                try { list.add(Integer.parseInt(p.trim())); } catch (Exception ignored) {}
            }
            if (!list.isEmpty()) {
                int[] res = new int[list.size()];
                for (int i = 0; i < list.size(); i++) res[i] = list.get(i);
                return res;
            }
        }
        return fallback;
    }

    // =========================================================================
    // 1. Trie (6 Steps)
    // =========================================================================
    private static void advanceTrie(VisualizationRuntimeHost host) {
        int step = nextStep(host);

        String input = host.getExpressionInput();
        if (input == null || input.trim().isEmpty()) {
            input = host.getAdvancedDsCustomInput();
        }
        if (input == null || input.trim().isEmpty()) {
            input = getDefaultInput("ds_trie", host.getAdvancedDsPreset());
        }
        String[] words = input.split("[,\\s]+");
        String w1 = words.length > 0 && !words[0].isEmpty() ? words[0] : "cat";
        String w2 = words.length > 1 && !words[1].isEmpty() ? words[1] : "car";
        String w3 = words.length > 2 && !words[2].isEmpty() ? words[2] : "card";

        char c0 = !w1.isEmpty() ? w1.charAt(0) : 'c';
        char c1 = w1.length() > 1 ? w1.charAt(1) : (!w2.isEmpty() ? w2.charAt(0) : 'a');
        char c2 = w1.length() > 2 ? w1.charAt(2) : (w1.length() > 1 ? w1.charAt(1) : 't');
        char cBranch2 = w2.length() > 2 ? w2.charAt(2) : (!w2.isEmpty() ? w2.charAt(w2.length() - 1) : 'r');
        char cBranch3 = w3.length() > 3 ? w3.charAt(3) : (!w3.isEmpty() ? w3.charAt(w3.length() - 1) : 'd');

        switch (step) {
            case 1:
                host.setActiveNodes(0, 1);
                host.updatePointerState("Root", 0, "Char", c0);
                host.updateArrayVisualization(0, 1, -1);
                host.setHighlightedLines(10, 3);
                host.bindCurrentState("Trie: Insert '" + w1 + "'. Check Root 0 for char '" + c0 + "'. No branch exists -> allocate Node 1.");
                break;
            case 2:
                host.setActiveNodes(1, 2);
                host.updatePointerState("Parent", 1, "Char", c1);
                host.updateArrayVisualization(1, 2, -1);
                host.setHighlightedLines(14, 5);
                host.bindCurrentState("Trie: Transition along edge '" + c1 + "' from Node 1 -> allocate Node 2.");
                break;
            case 3:
                host.setActiveNodes(2, 3);
                host.updatePointerState("Terminal", 3, "Char", c2);
                host.updateArrayVisualization(2, 3, 3);
                host.setHighlightedLines(18, 7);
                host.bindCurrentState("Trie: Transition along edge '" + c2 + "' -> allocate Node 3. Mark isEndOfWord = true for '" + w1 + "'!");
                break;
            case 4:
                host.setActiveNodes(2, 4);
                host.updatePointerState("Shared", 2, "Branch", cBranch2);
                host.updateArrayVisualization(2, 4, 4);
                host.setHighlightedLines(15, 6);
                host.bindCurrentState("Trie: Insert '" + w2 + "'. Reuses common prefix '" + c0 + "" + c1 + "' at Node 2, branches on '" + cBranch2 + "' -> Node 4 (WordEnd).");
                break;
            case 5:
                host.setActiveNodes(4, 5);
                host.updatePointerState("Parent", 4, "Branch", cBranch3);
                host.updateArrayVisualization(4, 5, 5);
                host.setHighlightedLines(20, 8);
                host.bindCurrentState("Trie: Insert '" + w3 + "'. Reuses prefix up to Node 4, branches on '" + cBranch3 + "' -> Node 5 (WordEnd). Space-efficient sharing!");
                break;
            case 6:
            default:
                host.setActiveNodes(0, 2);
                host.updatePointerState("Query", 2, "Matches", 3);
                host.updateArrayVisualization(0, 2, 2);
                host.setHighlightedLines(26, 11);
                host.finish("Trie: Prefix query for '" + c0 + "" + c1 + "' reaches Node 2 in O(L=2). Traversal yields all extensions ('" + w1 + "', '" + w2 + "', '" + w3 + "') instantly!");
                break;
        }
    }

    // =========================================================================
    // 2. Segment Tree (6 Steps)
    // =========================================================================
    private static void advanceSegmentTree(VisualizationRuntimeHost host) {
        int step = nextStep(host);

        String expr = host.getExpressionInput();
        if (expr == null || expr.trim().isEmpty()) {
            expr = host.getAdvancedDsCustomInput();
        }
        int[] parsed = parseBaseArray(expr, 4, new int[]{1, 3, 5, 7});
        int a0 = parsed[0];
        int a1 = parsed[1];
        int a2 = parsed[2];
        int a3 = parsed[3];
        int leftSum = a0 + a1;
        int rightSum = a2 + a3;
        int rootSum = leftSum + rightSum;

        switch (step) {
            case 1:
                host.setWorkingValues(new int[]{0, 0, 0, a0, a1, a2, a3});
                host.updateArrayVisualization(3, 4, -1);
                host.updatePointerState("Leaves", 4, "Base", 0);
                host.setHighlightedLines(10, 3);
                host.bindCurrentState("Segment Tree: Base array [" + a0 + ", " + a1 + ", " + a2 + ", " + a3 + "] initialized as tree leaf nodes.");
                break;
            case 2:
                host.setWorkingValues(new int[]{0, leftSum, 0, a0, a1, a2, a3});
                host.updateArrayVisualization(1, 3, 4);
                host.updatePointerState("[0..1]", leftSum, "Leaves", a0 + a1);
                host.setHighlightedLines(15, 5);
                host.bindCurrentState("Build Left Subtree: Node [0..1] merges leaf 0 (" + a0 + ") and leaf 1 (" + a1 + ") -> Sum = " + leftSum + ".");
                break;
            case 3:
                host.setWorkingValues(new int[]{0, leftSum, rightSum, a0, a1, a2, a3});
                host.updateArrayVisualization(2, 5, 6);
                host.updatePointerState("[2..3]", rightSum, "Leaves", a2 + a3);
                host.setHighlightedLines(17, 6);
                host.bindCurrentState("Build Right Subtree: Node [2..3] merges leaf 2 (" + a2 + ") and leaf 3 (" + a3 + ") -> Sum = " + rightSum + ".");
                break;
            case 4:
                host.setWorkingValues(new int[]{rootSum, leftSum, rightSum, a0, a1, a2, a3});
                host.updateArrayVisualization(0, 1, 2);
                host.updatePointerState("Root", rootSum, "Total", rootSum);
                host.setHighlightedLines(20, 8);
                host.bindCurrentState("Build Root: Node [0..3] merges left (" + leftSum + ") + right (" + rightSum + ") -> Total Range Sum = " + rootSum + ". Tree ready!");
                break;
            case 5:
                int queryResult = a1 + rightSum;
                host.setWorkingValues(new int[]{rootSum, leftSum, rightSum, a0, a1, a2, a3});
                host.updateArrayVisualization(4, 2, 0);
                host.updatePointerState("Query L", 1, "Query R", 3);
                host.setHighlightedLines(26, 11);
                host.bindCurrentState("Range Query [1..3]: Canonically covers leaf [1] (" + a1 + ") and segment [2..3] (" + rightSum + ") -> Sum = " + queryResult + " in O(log N)!");
                break;
            case 6:
            default:
                int updatedLeaf = a2 + 3;
                int updatedRight = updatedLeaf + a3;
                int updatedRoot = leftSum + updatedRight;
                host.setWorkingValues(new int[]{updatedRoot, leftSum, updatedRight, a0, a1, updatedLeaf, a3});
                host.updateArrayVisualization(5, 2, 0);
                host.updatePointerState("Updated", 2, "Delta", 3);
                host.setHighlightedLines(32, 14);
                host.finish("Point Update: Modified leaf 2 (+3). Propagated up branch to [2..3] and Root [0..3] in O(log N) time!");
                break;
        }
    }

    // =========================================================================
    // 3. Persistent Segment Tree (5 Steps)
    // =========================================================================
    private static void advancePersistentSegmentTree(VisualizationRuntimeHost host) {
        int step = nextStep(host);

        String expr = host.getExpressionInput();
        if (expr == null || expr.trim().isEmpty()) {
            expr = host.getAdvancedDsCustomInput();
        }
        int[] parsed = parseBaseArray(expr, 4, new int[]{1, 3, 5, 7});
        int a0 = parsed[0];
        int a1 = parsed[1];
        int a2 = parsed[2];
        int a3 = parsed[3];
        int left0 = a0 + a1;
        int right0 = a2 + a3;
        int root0 = left0 + right0;
        int newVal = a2 + 3;
        int right1 = newVal + a3;
        int root1 = left0 + right1;

        switch (step) {
            case 1:
                host.setWorkingValues(new int[]{root0, left0, right0, a0, a1, a2, a3});
                host.updatePointerState("Version", 0, "Root", root0);
                host.updateArrayVisualization(0, 1, 2);
                host.setHighlightedLines(10, 3);
                host.bindCurrentState("Version 0 (Base Tree): Root V0 created with sum " + root0 + " covering range [0..3]. Historical state is frozen and immutable.");
                break;
            case 2:
                host.updatePointerState("Target", 2, "NewVal", newVal);
                host.updateArrayVisualization(5, 2, -1);
                host.setHighlightedLines(16, 6);
                host.bindCurrentState("Point Update request at index 2 (value " + a2 + " -> " + newVal + ", delta +3). Prepares new Version 1 branch without cloning unmodified nodes.");
                break;
            case 3:
                host.setWorkingValues(new int[]{root1, left0, right1, a0, a1, newVal, a3});
                host.updatePointerState("V1 Root", root1, "Shared", left0);
                host.updateArrayVisualization(0, 2, 1);
                host.setHighlightedLines(22, 9);
                host.bindCurrentState("Path Copying: V1 Root created (sum " + root1 + "). Reuses untouched left subtree [0..1] directly from V0. Zero memory duplication!");
                break;
            case 4:
                host.updatePointerState("NewNode", newVal, "SharedNode", a3);
                host.updateArrayVisualization(5, 6, 2);
                host.setHighlightedLines(26, 11);
                host.bindCurrentState("Leaf Assignment: Leaf [2]* set to " + newVal + ". Unmodified leaf [3] (" + a3 + ") is shared. Only O(log N) = 3 new nodes allocated for V1!");
                break;
            case 5:
            default:
                host.updatePointerState("V0 Query", root0, "V1 Query", root1);
                host.updateArrayVisualization(0, 0, 0);
                host.setHighlightedLines(32, 15);
                host.finish("Persistent SegTree Complete: Query V0 -> " + root0 + ", Query V1 -> " + root1 + ". Complete history preserved forever in O(log N) space per version!");
                break;
        }
    }

    // =========================================================================
    // 4. Fenwick Tree / BIT (6 Steps)
    // =========================================================================
    private static void advanceFenwickTree(VisualizationRuntimeHost host) {
        int step = nextStep(host);

        String expr = host.getExpressionInput();
        if (expr == null || expr.trim().isEmpty()) {
            expr = host.getAdvancedDsCustomInput();
        }
        int[] baseVals = parseBaseArray(expr, 6, new int[]{2, 1, 1, 3, 2, 3});
        int[] b = new int[6];
        for (int i = 0; i < 6; i++) {
            b[i] = (i < baseVals.length) ? baseVals[i] : (i + 1);
        }

        int[] bit = new int[7];
        for (int i = 1; i <= 6; i++) {
            int v = b[i - 1];
            for (int idx = i; idx <= 6; idx += idx & (-idx)) {
                bit[idx] += v;
            }
        }

        switch (step) {
            case 1:
                host.setWorkingValues(new int[7]);
                host.updatePointerState("Size", 6, "LSB(i)", 1);
                host.updateArrayVisualization(1, -1, -1);
                host.setHighlightedLines(8, 3);
                host.bindCurrentState("Fenwick Tree (BIT): Base array [" + b[0] + ", " + b[1] + ", " + b[2] + ", " + b[3] + ", " + b[4] + ", " + b[5] + "]. LSB(i) = i & (-i) dictates range coverage.");
                break;
            case 2:
                int[] s2 = new int[7];
                s2[1] = b[0];
                s2[2] = b[0] + b[1];
                host.setWorkingValues(s2);
                host.updatePointerState("i", 2, "LSB(2)", 2);
                host.updateArrayVisualization(2, 1, -1);
                host.setHighlightedLines(14, 5);
                host.bindCurrentState("Build BIT: Index 2 has LSB(2)=2, covering range [1..2]. BIT[2] = arr[1] (" + b[0] + ") + arr[2] (" + b[1] + ") = " + s2[2] + ".");
                break;
            case 3:
                int[] s3 = bit.clone();
                host.setWorkingValues(s3);
                host.updatePointerState("i", 4, "LSB(4)", 4);
                host.updateArrayVisualization(4, 6, -1);
                host.setHighlightedLines(18, 7);
                host.bindCurrentState("Build BIT: Index 4 has LSB(4)=4, covering range [1..4] -> BIT[4] = " + s3[4] + ". Power-of-two jump hierarchy ready!");
                break;
            case 4:
                int[] s4 = bit.clone();
                s4[3] += 2;
                s4[4] += 2;
                host.setWorkingValues(s4);
                host.updatePointerState("Update", 3, "Delta", 2);
                host.updateArrayVisualization(3, 4, -1);
                host.setHighlightedLines(24, 10);
                host.bindCurrentState("Point Update(3, +2): Add delta (+2) to BIT[3] (" + s4[3] + "), then jump via idx += LSB(idx) to update BIT[4] (" + s4[4] + ") in O(log N)!");
                break;
            case 5:
                int[] s5 = bit.clone();
                s5[3] += 2;
                s5[4] += 2;
                int prefix5 = s5[5] + s5[4];
                host.setWorkingValues(s5);
                host.updatePointerState("Prefix(5)", prefix5, "Hops", 2);
                host.updateArrayVisualization(5, 4, -1);
                host.setHighlightedLines(30, 13);
                host.bindCurrentState("Prefix Sum(5): Accumulate BIT[5] (" + s5[5] + ") + BIT[4] (" + s5[4] + ") = " + prefix5 + " using idx -= LSB(idx). Only 2 pointer hops!");
                break;
            case 6:
            default:
                int[] s6 = bit.clone();
                s6[3] += 2;
                s6[4] += 2;
                int p5 = s6[5] + s6[4];
                int p1 = s6[1];
                int rangeSum = p5 - p1;
                host.setWorkingValues(s6);
                host.updatePointerState("Range[2..5]", rangeSum, "O(log N)", 1);
                host.updateArrayVisualization(5, 1, 0);
                host.setHighlightedLines(36, 16);
                host.finish("Fenwick Tree Complete: Range sum [2..5] = prefix(5) (" + p5 + ") - prefix(1) (" + p1 + ") = " + rangeSum + " in O(log N) time with zero pointer memory overhead!");
                break;
        }
    }

    // =========================================================================
    // 5. AVL Tree (5 Steps)
    // =========================================================================
    private static void advanceAvlTree(VisualizationRuntimeHost host) {
        int step = nextStep(host);

        String expr = host.getExpressionInput();
        if (expr == null || expr.trim().isEmpty()) {
            expr = host.getAdvancedDsCustomInput();
        }
        // Parse ALL user-provided values (no fixed-length cap)
        int[] allParsed = parseFullArray(expr, new int[]{10, 20, 30});

        // If more than 3 nodes provided: show multi-node balanced tree animation
        if (allParsed.length > 3) {
            int[] balancedArr = AvlTreeStepEngine.buildBalancedTreeArray(allParsed);
            int rootV = balancedArr[0];
            int childL = balancedArr.length > 1 ? balancedArr[1] : (rootV - 10);
            int childR = balancedArr.length > 2 ? balancedArr[2] : (rootV + 10);
            switch (step) {
                case 1:
                    host.setWorkingValues(new int[]{allParsed[0]});
                    host.setActiveNodes(allParsed[0], -1);
                    host.updatePointerState("Root", allParsed[0], "BF", 0);
                    host.setHighlightedLines(10, 3);
                    host.bindCurrentState("AVL Tree: Insert Root " + allParsed[0] + ". BF = 0.");
                    break;
                case 2:
                    host.setWorkingValues(new int[]{allParsed[0], allParsed[1]});
                    host.setActiveNodes(allParsed[0], allParsed[1]);
                    host.updatePointerState("Root", allParsed[0], "BF", (allParsed[1] < allParsed[0]) ? 1 : -1);
                    host.setHighlightedLines(15, 5);
                    host.bindCurrentState("AVL Tree: Inserted " + allParsed[1] + ". Checking balance factors.");
                    break;
                case 3:
                    host.setWorkingValues(new int[]{allParsed[0], allParsed[1], allParsed[2]});
                    host.setActiveNodes(allParsed[0], allParsed[2]);
                    host.updatePointerState("Inserting", allParsed[2], "Checking BF", 0);
                    host.setHighlightedLines(20, 8);
                    host.bindCurrentState("AVL Tree: Inserting " + allParsed[2] + ". Recalculating balance factors.");
                    break;
                case 4:
                    host.setWorkingValues(balancedArr);
                    host.setActiveNodes(rootV, childL);
                    host.updatePointerState("Rotate", rootV, "Pivot", childL);
                    host.setHighlightedLines(25, 10);
                    host.bindCurrentState("AVL Tree: Rotation(s) applied. Building balanced " + allParsed.length + "-node tree.");
                    break;
                case 5:
                default:
                    host.setWorkingValues(balancedArr);
                    host.setActiveNodes(rootV, -1);
                    host.updatePointerState("NewRoot", rootV, "BF", 0);
                    host.setHighlightedLines(30, 13);
                    host.finish("AVL Restored: Balanced " + allParsed.length + "-node tree. Root " + rootV + " (left: " + childL + ", right: " + childR + "). All |BF| <= 1!");
                    break;
            }
            return;
        }

        // Standard 1-3 node AVL path
        int v1 = allParsed[0];
        int v2 = allParsed.length > 1 ? allParsed[1] : 20;
        int v3 = allParsed.length > 2 ? allParsed[2] : 30;

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

        if (isBalanced) {
            switch (step) {
                case 1:
                    host.setWorkingValues(new int[]{v1});
                    host.setActiveNodes(v1, -1);
                    host.updatePointerState("Root", v1, "BF", 0);
                    host.setHighlightedLines(10, 3);
                    host.bindCurrentState("AVL Tree: Inserted Root " + v1 + ". Height = 1, Balance Factor = 0. Balanced.");
                    break;
                case 2:
                    host.setWorkingValues(new int[]{v1, v2});
                    host.setActiveNodes(v1, v2);
                    int bf2 = (v2 < v1) ? 1 : -1;
                    host.updatePointerState("Root", v1, "BF", bf2);
                    host.setHighlightedLines(15, 5);
                    host.bindCurrentState("AVL Tree: Inserted " + v2 + ((v2 < v1) ? " to left." : " to right.") + " Root BF = " + (bf2 > 0 ? "+1" : "-1") + ". Invariant |BF| <= 1 holds.");
                    break;
                case 3:
                    host.setWorkingValues(new int[]{v1, v2, v3});
                    host.setActiveNodes(v3, -1);
                    host.updatePointerState("Root", v1, "Insert", v3);
                    host.setHighlightedLines(20, 7);
                    host.bindCurrentState("AVL Tree: Inserted " + v3 + ". Tree contains [" + v1 + ", " + v2 + ", " + v3 + "]. Checking balance factors bottom-up...");
                    break;
                case 4:
                    host.setWorkingValues(new int[]{midVal, minVal, maxVal});
                    host.setActiveNodes(midVal, -1);
                    host.updatePointerState("Root", midVal, "BF", 0);
                    host.setHighlightedLines(26, 9);
                    host.bindCurrentState("AVL Tree: Balance Factor Invariant verified: |BF| <= 1 for all nodes (BF=0). No rotation needed!");
                    break;
                case 5:
                default:
                    host.setWorkingValues(new int[]{midVal, minVal, maxVal});
                    host.setActiveNodes(midVal, -1);
                    host.updatePointerState("Root", midVal, "BF", 0);
                    host.setHighlightedLines(30, 13);
                    host.finish("AVL Tree: Complete! Tree perfectly balanced with root " + midVal + ", left child " + minVal + ", and right child " + maxVal + ". All balance factors = 0.");
                    break;
            }
            return;
        }

        if (isLL) {
            switch (step) {
                case 1:
                    host.setWorkingValues(new int[]{v1});
                    host.setActiveNodes(v1, -1);
                    host.updatePointerState("Root", v1, "BF", 0);
                    host.setHighlightedLines(10, 3);
                    host.bindCurrentState("AVL Tree: Inserted Root " + v1 + ". Height = 1, Balance Factor (BF = H_left - H_right) = 0. Balanced.");
                    break;
                case 2:
                    host.setWorkingValues(new int[]{v1, v2});
                    host.setActiveNodes(v1, v2);
                    host.updatePointerState("Root", v1, "Child", v2);
                    host.setHighlightedLines(15, 5);
                    host.bindCurrentState("AVL Tree: Inserted " + v2 + " to left. Root " + v1 + " BF = +1. Invariant |BF| <= 1 holds. Balanced.");
                    break;
                case 3:
                    host.setWorkingValues(new int[]{v1, v2, v3});
                    host.setActiveNodes(v1, v3);
                    host.updatePointerState("Imbalance", v1, "BF", 2);
                    host.setHighlightedLines(20, 8);
                    host.bindCurrentState("Imbalance Detected: Inserted " + v3 + ". Root " + v1 + " has BF = +2 (Left-Left LL heavy imbalance)! Immediate rotation required.");
                    break;
                case 4:
                    host.setActiveNodes(v2, v1);
                    host.updatePointerState("Pivot", v2, "RotateRoot", v1);
                    host.setHighlightedLines(25, 10);
                    host.bindCurrentState("Rotation Prep: Left-Left (LL) heavy -> Perform Right Rotation. Node " + v2 + " becomes new root, " + v1 + " becomes right child.");
                    break;
                case 5:
                default:
                    host.setWorkingValues(new int[]{midVal, minVal, maxVal});
                    host.setActiveNodes(midVal, -1);
                    host.updatePointerState("NewRoot", midVal, "BF", 0);
                    host.setHighlightedLines(30, 13);
                    host.finish("AVL Restored: Right rotation on " + midVal + " complete. Root " + midVal + " (left: " + minVal + ", right: " + maxVal + "). All balance factors = 0!");
                    break;
            }
            return;
        }

        if (isLR) {
            switch (step) {
                case 1:
                    host.setWorkingValues(new int[]{v1});
                    host.setActiveNodes(v1, -1);
                    host.updatePointerState("Root", v1, "BF", 0);
                    host.setHighlightedLines(10, 3);
                    host.bindCurrentState("AVL Tree: Inserted Root " + v1 + ". Height = 1, Balance Factor = 0. Balanced.");
                    break;
                case 2:
                    host.setWorkingValues(new int[]{v1, v2});
                    host.setActiveNodes(v1, v2);
                    host.updatePointerState("Root", v1, "Child", v2);
                    host.setHighlightedLines(15, 5);
                    host.bindCurrentState("AVL Tree: Inserted " + v2 + " to left. Root " + v1 + " BF = +1. Invariant |BF| <= 1 holds.");
                    break;
                case 3:
                    host.setWorkingValues(new int[]{v1, v2, v3});
                    host.setActiveNodes(v1, v3);
                    host.updatePointerState("Imbalance", v1, "BF", 2);
                    host.setHighlightedLines(20, 8);
                    host.bindCurrentState("Imbalance Detected: Inserted " + v3 + " to right of " + v2 + ". Root " + v1 + " BF = +2, Child " + v2 + " BF = -1 (Left-Right LR imbalance)!");
                    break;
                case 4:
                    host.setActiveNodes(v3, v1);
                    host.updatePointerState("Pivot", v3, "DoubleRotate", v1);
                    host.setHighlightedLines(25, 10);
                    host.bindCurrentState("Double Rotation (LR): Left-rotate on " + v2 + ", then Right-rotate on root " + v1 + ". " + v3 + " ascends to root.");
                    break;
                case 5:
                default:
                    host.setWorkingValues(new int[]{midVal, minVal, maxVal});
                    host.setActiveNodes(midVal, -1);
                    host.updatePointerState("NewRoot", midVal, "BF", 0);
                    host.setHighlightedLines(30, 13);
                    host.finish("AVL Restored: Double rotation on " + midVal + " complete. Root " + midVal + " (left: " + minVal + ", right: " + maxVal + "). All balance factors = 0!");
                    break;
            }
            return;
        }

        if (isRL) {
            switch (step) {
                case 1:
                    host.setWorkingValues(new int[]{v1});
                    host.setActiveNodes(v1, -1);
                    host.updatePointerState("Root", v1, "BF", 0);
                    host.setHighlightedLines(10, 3);
                    host.bindCurrentState("AVL Tree: Inserted Root " + v1 + ". Height = 1, Balance Factor = 0. Balanced.");
                    break;
                case 2:
                    host.setWorkingValues(new int[]{v1, v2});
                    host.setActiveNodes(v1, v2);
                    host.updatePointerState("Root", v1, "Child", v2);
                    host.setHighlightedLines(15, 5);
                    host.bindCurrentState("AVL Tree: Inserted " + v2 + " to right. Root " + v1 + " BF = -1. Invariant |BF| <= 1 holds.");
                    break;
                case 3:
                    host.setWorkingValues(new int[]{v1, v2, v3});
                    host.setActiveNodes(v1, v3);
                    host.updatePointerState("Imbalance", v1, "BF", -2);
                    host.setHighlightedLines(20, 8);
                    host.bindCurrentState("Imbalance Detected: Inserted " + v3 + " to left of " + v2 + ". Root " + v1 + " BF = -2, Child " + v2 + " BF = +1 (Right-Left RL imbalance)!");
                    break;
                case 4:
                    host.setActiveNodes(v3, v1);
                    host.updatePointerState("Pivot", v3, "DoubleRotate", v1);
                    host.setHighlightedLines(25, 10);
                    host.bindCurrentState("Double Rotation (RL): Right-rotate on " + v2 + ", then Left-rotate on root " + v1 + ". " + v3 + " ascends to root.");
                    break;
                case 5:
                default:
                    host.setWorkingValues(new int[]{midVal, minVal, maxVal});
                    host.setActiveNodes(midVal, -1);
                    host.updatePointerState("NewRoot", midVal, "BF", 0);
                    host.setHighlightedLines(30, 13);
                    host.finish("AVL Restored: Double rotation on " + midVal + " complete. Root " + midVal + " (left: " + minVal + ", right: " + maxVal + "). All balance factors = 0!");
                    break;
            }
            return;
        }

        // RR case (default)
        switch (step) {
            case 1:
                host.setWorkingValues(new int[]{v1});
                host.setActiveNodes(v1, -1);
                host.updatePointerState("Root", v1, "BF", 0);
                host.setHighlightedLines(10, 3);
                host.bindCurrentState("AVL Tree: Inserted Root " + v1 + ". Height = 1, Balance Factor (BF = H_left - H_right) = 0. Balanced.");
                break;
            case 2:
                host.setWorkingValues(new int[]{v1, v2});
                host.setActiveNodes(v1, v2);
                host.updatePointerState("Root", v1, "Child", v2);
                host.setHighlightedLines(15, 5);
                host.bindCurrentState("AVL Tree: Inserted " + v2 + " to right. Root " + v1 + " BF = -1. Invariant |BF| <= 1 holds. Balanced.");
                break;
            case 3:
                host.setWorkingValues(new int[]{v1, v2, v3});
                host.setActiveNodes(v1, v3);
                host.updatePointerState("Imbalance", v1, "BF", -2);
                host.setHighlightedLines(20, 8);
                host.bindCurrentState("Imbalance Detected: Inserted " + v3 + ". Root " + v1 + " has BF = -2 (Right-Right heavy imbalance)! Immediate rotation required.");
                break;
            case 4:
                host.setActiveNodes(v2, v1);
                host.updatePointerState("Pivot", v2, "RotateRoot", v1);
                host.setHighlightedLines(25, 10);
                host.bindCurrentState("Rotation Prep: Right-Right (RR) heavy -> Perform Left Rotation. Node " + v2 + " becomes new root, " + v1 + " becomes left child.");
                break;
            case 5:
            default:
                host.setWorkingValues(new int[]{midVal, minVal, maxVal});
                host.setActiveNodes(midVal, -1);
                host.updatePointerState("NewRoot", midVal, "BF", 0);
                host.setHighlightedLines(30, 13);
                host.finish("AVL Restored: Left rotation on " + midVal + " complete. Root " + midVal + " (left: " + minVal + ", right: " + maxVal + "). All balance factors = 0!");
                break;
        }
    }

    // =========================================================================
    // 6. Red-Black Tree (5 Steps)
    // =========================================================================
    private static void advanceRedBlackTree(VisualizationRuntimeHost host) {
        int step = nextStep(host);

        String expr = host.getExpressionInput();
        if (expr == null || expr.trim().isEmpty()) {
            expr = host.getAdvancedDsCustomInput();
        }
        // Parse ALL user-provided values
        int[] allParsed = parseFullArray(expr, new int[]{10, 15, 25});
        int v1 = allParsed[0];
        int v2 = allParsed.length > 1 ? allParsed[1] : 15;
        int v3 = allParsed.length > 2 ? allParsed[2] : 25;

        int[] sorted = new int[]{v1, v2, v3};
        java.util.Arrays.sort(sorted);
        int minVal = sorted[0];
        int midVal = sorted[1];
        int maxVal = sorted[2];

        switch (step) {
            case 1:
                host.setWorkingValues(new int[]{v1});
                host.setActiveNodes(v1, -1);
                host.updatePointerState("Root(B)", v1, "Color", 0);
                host.setHighlightedLines(10, 3);
                host.bindCurrentState("Red-Black Tree: Inserted Root " + v1 + ". Property 2 strictly enforced: Root must always be BLACK.");
                break;
            case 2:
                host.setWorkingValues(new int[]{v1, v2});
                host.setActiveNodes(v1, v2);
                host.updatePointerState("Parent(B)", v1, "Child(R)", v2);
                host.setHighlightedLines(15, 5);
                host.bindCurrentState("Insert " + v2 + " as RED: Parent " + v1 + " is BLACK. No violation (Red node has black parent). Tree valid.");
                break;
            case 3:
                host.setWorkingValues(new int[]{v1, v2, v3});
                host.setActiveNodes(v2, v3);
                host.updatePointerState("Parent(R)", v2, "New(R)", v3);
                host.setHighlightedLines(20, 8);
                host.bindCurrentState("Violation Detected: Inserted " + v3 + " as RED. Both parent " + v2 + " and child " + v3 + " are RED! Property 4 violated.");
                break;
            case 4:
                host.updatePointerState("Uncle", 0, "Action", 1);
                host.setHighlightedLines(26, 11);
                host.bindCurrentState("Rebalancing Tree: Rotations and recoloring around median key " + midVal + ". Recolor " + midVal + " to BLACK, and children (" + minVal + ", " + maxVal + ") to RED.");
                break;
            case 5:
            default:
                host.setWorkingValues(new int[]{midVal, minVal, maxVal});
                host.setActiveNodes(midVal, -1);
                host.updatePointerState("Root(B)", midVal, "BlackDepth", 2);
                host.setHighlightedLines(32, 14);
                host.finish("Red-Black Balanced: Root " + midVal + " is BLACK. Left child " + minVal + " (RED), Right child " + maxVal + " (RED). Equal black-depth (2) strictly preserved!");
                break;
        }
    }

    // =========================================================================
    // 7. Splay Tree (5 Steps)
    // =========================================================================
    private static void advanceSplayTree(VisualizationRuntimeHost host) {
        int step = nextStep(host);

        String expr = host.getExpressionInput();
        if (expr == null || expr.trim().isEmpty()) {
            expr = host.getAdvancedDsCustomInput();
        }
        // Parse ALL user-provided values, use first 4 after sorting
        int[] allParsed = parseFullArray(expr, new int[]{5, 10, 15, 20});
        java.util.Arrays.sort(allParsed);
        int v1 = allParsed[0];
        int v2 = allParsed.length > 1 ? allParsed[1] : (v1 + 5);
        int v3 = allParsed.length > 2 ? allParsed[2] : (v2 + 5);
        int v4 = allParsed.length > 3 ? allParsed[3] : (v3 + 5);

        switch (step) {
            case 1:
                host.setWorkingValues(new int[]{v1, v2, v3, v4});
                host.setActiveNodes(v1, v4);
                host.updatePointerState("Access", v4, "Root", v1);
                host.setHighlightedLines(10, 3);
                host.bindCurrentState("Splay Tree: Accessed node " + v4 + " at depth 3. Splay operation triggered to rotate node " + v4 + " to the Root.");
                break;
            case 2:
                host.setActiveNodes(v2, v3);
                host.updatePointerState("Zig-Zig", v4, "Grandparent", v2);
                host.setHighlightedLines(16, 6);
                host.bindCurrentState("Zig-Zig Step: Node " + v4 + " and Parent " + v3 + " are both right children. Rotate Grandparent " + v2 + " left first, then Parent " + v3 + ".");
                break;
            case 3:
                host.setWorkingValues(new int[]{v3, v2, v4, v1});
                host.setActiveNodes(v3, v4);
                host.updatePointerState("Intermediate", v4, "Parent", v3);
                host.setHighlightedLines(22, 9);
                host.bindCurrentState("First Splay Rotation: Node " + v4 + " elevated up the tree. Path length halved for all sibling nodes!");
                break;
            case 4:
                host.setWorkingValues(new int[]{v4, v3, v2, v1});
                host.setActiveNodes(v4, -1);
                host.updatePointerState("NewRoot", v4, "OldRoot", v1);
                host.setHighlightedLines(28, 12);
                host.bindCurrentState("Final Splay Rotation: Node " + v4 + " becomes the new ROOT! All other nodes restructured beneath it.");
                break;
            case 5:
            default:
                host.updatePointerState("Root", v4, "Lookup", 1);
                host.setHighlightedLines(34, 15);
                host.finish("Splay Tree Complete: Node " + v4 + " is now at Root. High-frequency queries enjoy O(1) cache-like lookup speed!");
                break;
        }
    }

    // =========================================================================
    // 8. B-Tree / 2-3-4 Tree (5 Steps)
    // =========================================================================
    private static void advanceBTree(VisualizationRuntimeHost host) {
        int step = nextStep(host);

        String expr = host.getExpressionInput();
        if (expr == null || expr.trim().isEmpty()) {
            expr = host.getAdvancedDsCustomInput();
        }
        // Parse ALL user-provided values, use first 4 after sorting
        int[] allParsed = parseFullArray(expr, new int[]{10, 20, 25, 30});
        java.util.Arrays.sort(allParsed);
        int k1 = allParsed[0];
        int k2 = allParsed.length > 1 ? allParsed[1] : (k1 + 10);
        int k3 = allParsed.length > 2 ? allParsed[2] : (k2 + 5);
        int k4 = allParsed.length > 3 ? allParsed[3] : (k3 + 5);

        switch (step) {
            case 1:
                host.setWorkingValues(new int[]{k1, k2});
                host.updateArrayVisualization(0, 1, -1);
                host.updatePointerState("Keys", 2, "Max", 3);
                host.setHighlightedLines(10, 3);
                host.bindCurrentState("B-Tree (Order M=4 / 2-3-4 Tree): Root node contains keys [" + k1 + ", " + k2 + "]. Node has space for up to 3 keys.");
                break;
            case 2:
                host.setWorkingValues(new int[]{k1, k2, k4});
                host.updateArrayVisualization(0, 1, 2);
                host.updatePointerState("Node", 3, "Full", 1);
                host.setHighlightedLines(14, 5);
                host.bindCurrentState("Insert " + k4 + ": Node now holds maximum keys [" + k1 + ", " + k2 + ", " + k4 + "]. Any additional insert will trigger a Node Split.");
                break;
            case 3:
                host.setWorkingValues(new int[]{k1, k2, k3, k4});
                host.updateArrayVisualization(0, 1, 2);
                host.updatePointerState("Overflow", 4, "Key", k3);
                host.setHighlightedLines(18, 7);
                host.bindCurrentState("Insert " + k3 + " -> Overflow! Node exceeds capacity with 4 keys [" + k1 + ", " + k2 + ", " + k3 + ", " + k4 + "]. Initiating Median Promotion split.");
                break;
            case 4:
                host.setWorkingValues(new int[]{k2, k1, k3, k4});
                host.updatePointerState("Promoted", k2, "Children", 2);
                host.setHighlightedLines(24, 10);
                host.bindCurrentState("Node Split: Median key " + k2 + " promoted to new parent. Left child: [" + k1 + "], Right child: [" + k3 + ", " + k4 + "].");
                break;
            case 5:
            default:
                host.updatePointerState("Root", k2, "Depth", 1);
                host.setHighlightedLines(30, 13);
                host.finish("B-Tree Balanced: Root promoted. Tree grows upwards from root, guaranteeing uniform leaf depth and optimal disk I/O!");
                break;
        }
    }

    // =========================================================================
    // 9. Disjoint Set Union / DSU (5 Steps)
    // =========================================================================
    private static void advanceDsu(VisualizationRuntimeHost host) {
        int step = nextStep(host);

        String expr = host.getExpressionInput();
        if (expr == null || expr.trim().isEmpty()) {
            expr = host.getAdvancedDsCustomInput();
        }
        int[] vals = parseBaseArray(expr, 6, new int[]{0, 1, 2, 3, 4, 5});

        switch (step) {
            case 1:
                int[] p1 = {0, 1, 2, 3, 4, 5};
                host.setWorkingValues(p1);
                host.setUnionFindState(p1, 0, 0);
                host.updatePointerState("Sets", 6, "Rank", 0);
                host.setHighlightedLines(10, 3);
                host.bindCurrentState("DSU: Initialized 6 disjoint sets {" + vals[0] + "}, {" + vals[1] + "}, {" + vals[2] + "}, {" + vals[3] + "}, {" + vals[4] + "}, {" + vals[5] + "}. parent[i] = i.");
                break;
            case 2:
                int[] p2 = {0, 0, 2, 3, 4, 5};
                host.setWorkingValues(p2);
                host.setUnionFindState(p2, 1, 0);
                host.updatePointerState("Union(" + vals[0] + "," + vals[1] + ")", vals[1], "Root", vals[0]);
                host.setHighlightedLines(14, 5);
                host.bindCurrentState("union(" + vals[0] + ", " + vals[1] + "): find(" + vals[0] + ")=" + vals[0] + ", find(" + vals[1] + ")=" + vals[1] + ". Set parent[" + vals[1] + "] = " + vals[0] + ".");
                break;
            case 3:
                int[] p3 = {0, 0, 0, 3, 4, 5};
                host.setWorkingValues(p3);
                host.setUnionFindState(p3, 2, 0);
                host.updatePointerState("Union(" + vals[0] + "," + vals[2] + ")", vals[2], "Root", vals[0]);
                host.setHighlightedLines(18, 7);
                host.bindCurrentState("union(" + vals[0] + ", " + vals[2] + "): find(" + vals[0] + ")=" + vals[0] + ", find(" + vals[2] + ")=" + vals[2] + ". Merged {" + vals[0] + ", " + vals[1] + ", " + vals[2] + "} under root " + vals[0] + ".");
                break;
            case 4:
                int[] p4 = {0, 0, 0, 2, 3, 5};
                host.setWorkingValues(p4);
                host.setUnionFindState(p4, 4, 0);
                host.updatePointerState("Find(" + vals[4] + ")", vals[4], "Root", vals[0]);
                host.setHighlightedLines(24, 10);
                host.bindCurrentState("find(" + vals[4] + ") without compression: Traverses " + vals[4] + " -> " + vals[3] + " -> " + vals[2] + " -> " + vals[0] + " (3 hops). Path Compression now initiates!");
                break;
            case 5:
            default:
                int[] p5 = {0, 0, 0, 0, 0, 5};
                host.setWorkingValues(p5);
                host.setUnionFindState(p5, 4, 0);
                host.updatePointerState("Compressed", vals[4], "Root", vals[0]);
                host.setHighlightedLines(30, 13);
                host.finish("Path Compression Complete: Flattened nodes " + vals[3] + " and " + vals[4] + " directly to root " + vals[0] + ". Amortized time per operation: alpha(N) < 4!");
                break;
        }
    }

    // =========================================================================
    // 10. Bloom Filter (5 Steps)
    // =========================================================================
    private static void advanceBloomFilter(VisualizationRuntimeHost host) {
        int step = nextStep(host);

        String input = host.getExpressionInput();
        if (input == null || input.trim().isEmpty()) {
            input = host.getAdvancedDsCustomInput();
        }
        String[] words = (input != null && !input.trim().isEmpty())
                ? input.trim().split("[,\\s]+")
                : new String[]{"apple", "banana", "cherry"};
        String w1 = words.length > 0 && !words[0].isEmpty() ? words[0] : "apple";
        String w2 = words.length > 1 && !words[1].isEmpty() ? words[1] : "banana";
        String wQuery = words.length > 2 && !words[2].isEmpty() ? words[2] : "cherry";

        int h1_1 = bloomHash1(w1);
        int h1_2 = bloomHash2(w1);
        int h1_3 = bloomHash3(w1);

        int h2_1 = bloomHash1(w2);
        int h2_2 = bloomHash2(w2);
        int h2_3 = bloomHash3(w2);

        switch (step) {
            case 1:
                host.setWorkingValues(new int[16]);
                host.updateArrayVisualization(-1, -1, -1);
                host.updatePointerState("Bits", 16, "k", 3);
                host.setHighlightedLines(10, 3);
                host.bindCurrentState("Bloom Filter: 16-bit array initialized to all 0s. Keys to insert: '" + w1 + "', '" + w2 + "'. Uses k=3 hash functions.");
                break;
            case 2:
                int[] b2 = new int[16];
                b2[h1_1] = 1; b2[h1_2] = 1; b2[h1_3] = 1;
                host.setWorkingValues(b2);
                host.updateArrayVisualization(h1_1, h1_2, h1_3);
                host.updatePointerState("h1", h1_1, "h2", h1_2);
                host.setHighlightedLines(14, 5);
                host.bindCurrentState("Insert '" + w1 + "': Hashes h1=" + h1_1 + ", h2=" + h1_2 + ", h3=" + h1_3 + " computed. Set bitArray[" + h1_1 + "]=1, [" + h1_2 + "]=1, [" + h1_3 + "]=1.");
                break;
            case 3:
                int[] b3 = new int[16];
                b3[h1_1] = 1; b3[h1_2] = 1; b3[h1_3] = 1;
                b3[h2_1] = 1; b3[h2_2] = 1; b3[h2_3] = 1;
                host.setWorkingValues(b3);
                host.updateArrayVisualization(h2_1, h2_2, h2_3);
                host.updatePointerState("h1", h2_1, "h3", h2_3);
                host.setHighlightedLines(18, 7);
                host.bindCurrentState("Insert '" + w2 + "': Hashes h1=" + h2_1 + ", h2=" + h2_2 + ", h3=" + h2_3 + " computed. Array now holds items: '" + w1 + "', '" + w2 + "'.");
                break;
            case 4:
                host.updateArrayVisualization(h1_1, h1_2, h1_3);
                host.updatePointerState("Query", 1, "Status", 1);
                host.setHighlightedLines(24, 10);
                host.bindCurrentState("Query '" + w1 + "': Check bits [" + h1_1 + ", " + h1_2 + ", " + h1_3 + "]. All bits are 1 -> PROBABLY IN SET (Potential false positive bounded by ~1%).");
                break;
            case 5:
            default:
                int[] bFinal = new int[16];
                bFinal[h1_1] = 1; bFinal[h1_2] = 1; bFinal[h1_3] = 1;
                bFinal[h2_1] = 1; bFinal[h2_2] = 1; bFinal[h2_3] = 1;
                host.setWorkingValues(bFinal);

                int q1 = bloomHash1(wQuery);
                int q2 = bloomHash2(wQuery);
                int q3 = bloomHash3(wQuery);
                int zeroBit = -1;
                if (bFinal[q1] == 0) zeroBit = q1;
                else if (bFinal[q2] == 0) zeroBit = q2;
                else if (bFinal[q3] == 0) zeroBit = q3;

                if (zeroBit != -1) {
                    host.updateArrayVisualization(zeroBit, -1, -1);
                    host.updatePointerState("Query", 0, "ZeroBit", zeroBit);
                    host.setHighlightedLines(30, 13);
                    host.finish("Query '" + wQuery + "': Bit " + zeroBit + " is 0! DEFINITELY NOT IN SET. Bloom filters guarantee ZERO false negatives in O(k) time!");
                } else {
                    host.updateArrayVisualization(q1, q2, q3);
                    host.updatePointerState("Query", 1, "ZeroNeg", 1);
                    host.setHighlightedLines(30, 13);
                    host.finish("Query '" + wQuery + "': All bits [" + q1 + ", " + q2 + ", " + q3 + "] are 1 -> MATCH! ZERO false negatives guaranteed in O(k) time.");
                }
                break;
        }
    }

    // =========================================================================
    // 11. LRU Cache (5 Steps)
    // =========================================================================
    private static void advanceLruCache(VisualizationRuntimeHost host) {
        int step = nextStep(host);

        String expr = host.getExpressionInput();
        if (expr == null || expr.trim().isEmpty()) {
            expr = host.getAdvancedDsCustomInput();
        }
        int[] keys = parseBaseArray(expr, 4, new int[]{1, 2, 3, 4});
        int k1 = (keys.length > 0) ? keys[0] : 1;
        int k2 = (keys.length > 1) ? keys[1] : 2;
        int k3 = (keys.length > 2) ? keys[2] : 3;
        int k4 = (keys.length > 3) ? keys[3] : 4;

        switch (step) {
            case 1:
                host.setWorkingValues(new int[]{k1});
                host.updateArrayVisualization(0, -1, -1);
                host.updatePointerState("MRU", k1, "LRU", k1);
                host.setHighlightedLines(10, 3);
                host.bindCurrentState("LRU Cache (Capacity 3): put(" + k1 + ") -> Head <-> [Key: " + k1 + "] <-> Tail. Hash map maps key " + k1 + " -> Node. Count = 1/3.");
                break;
            case 2:
                host.setWorkingValues(new int[]{k3, k2, k1});
                host.updateArrayVisualization(0, 2, -1);
                host.updatePointerState("MRU", k3, "LRU", k1);
                host.setHighlightedLines(16, 6);
                host.bindCurrentState("put(" + k2 + "), put(" + k3 + "): Inserted at Head (MRU). Cache full (3/3). Head: [" + k3 + "] <-> [" + k2 + "] <-> [" + k1 + "] :Tail. LRU key is " + k1 + ".");
                break;
            case 3:
                host.setWorkingValues(new int[]{k2, k3, k1});
                host.updateArrayVisualization(0, 1, 2);
                host.updatePointerState("Hit", k2, "NewMRU", k2);
                host.setHighlightedLines(22, 9);
                host.bindCurrentState("get(" + k2 + ") Cache Hit: Key " + k2 + " accessed. Detached and moved to Head (MRU) in O(1). New order: [" + k2 + "] <-> [" + k3 + "] <-> [" + k1 + "].");
                break;
            case 4:
                host.setWorkingValues(new int[]{k2, k3});
                host.updateArrayVisualization(1, -1, 1);
                host.updatePointerState("Evict", k1, "Tail", k1);
                host.setHighlightedLines(28, 12);
                host.bindCurrentState("put(" + k4 + ") Overflow: Cache at max capacity 3. Evict oldest LRU tail node [Key: " + k1 + "] in O(1) time.");
                break;
            case 5:
            default:
                host.setWorkingValues(new int[]{k4, k2, k3});
                host.updateArrayVisualization(0, 2, -1);
                host.updatePointerState("MRU", k4, "LRU", k3);
                host.setHighlightedLines(34, 15);
                host.finish("put(" + k4 + ") Complete: Node [" + k4 + "] inserted at MRU Head. List: [" + k4 + "] <-> [" + k2 + "] <-> [" + k3 + "]. Hash map + DLL achieves strict O(1) get & put!");
                break;
        }
    }

    private static void advanceGenericDs(VisualizationRuntimeHost host) {
        int step = nextStep(host);
        if (step >= 5) {
            host.finish("Advanced Data Structure operation complete.");
        } else {
            host.bindCurrentState("Data structure state transition: step " + step + ".");
        }
    }

    public static String getPresetDescription(String techniqueId, int preset) {
        String id = techniqueId != null ? techniqueId : "";
        if ("ds_trie".equals(id) || "adv_trie".equals(id)) {
            switch (preset) {
                case 2: return "Prefix Overlap: Insert 'car', 'card', 'care'. Reuses root-to-'a' path prefix, branching only on distinct characters.";
                case 3: return "Dictionary Search: Insert 'apple', 'apply', 'app', 'apt'. Multi-way prefix matching for autocompletion.";
                case 1:
                default: return "Insert 'cat', 'car', 'card'. Demonstrates path creation, terminal markers, and O(L) prefix queries.";
            }
        }
        if ("ds_segment_tree".equals(id) || "adv_segment_tree".equals(id)) {
            switch (preset) {
                case 2: return "Point Update: Range sums on [2, 4, 6, 8]. Modifies element and propagates delta up the tree in O(log N).";
                case 3: return "Dense Range Query: Array [5, 1, 9, 3]. Evaluates disjoint interval coverage without scanning raw array elements.";
                case 1:
                default: return "Standard Range Sum: Array [1, 3, 5, 7]. Bottom-up construction, canonical query, and point update in O(log N).";
            }
        }
        if ("ds_persistent_segment_tree".equals(id) || "adv_persistent_st".equals(id)) {
            switch (preset) {
                case 2: return "Multi-Version History: Successive point updates yielding distinct version roots V0, V1 with zero mutation.";
                case 3: return "Range Invariant: Point update on [4, 2, 6, 8]. Verifies that older version queries remain consistent and valid.";
                case 1:
                default: return "Version 0 -> Version 1: Point update on element at index 2. Copies only O(log N) path nodes; preserves immutable V0.";
            }
        }
        if ("ds_fenwick_tree".equals(id) || "adv_fenwick_tree".equals(id)) {
            switch (preset) {
                case 2: return "Uniform Unit Array [1, 1, 1, 1, 1, 1]. Power-of-two jump leaps: BIT[4] covers 4 elements, BIT[2] covers 2.";
                case 3: return "Point Update + Range Query: Update BIT[i] by delta and compute range sum [L..R] via prefix(R) - prefix(L-1).";
                case 1:
                default: return "Standard Prefix Accumulator: Array of 6 elements. Explores binary LSB intervals (i & -i) and O(log N) sum traversal.";
            }
        }
        if ("ds_avl_tree".equals(id) || "adv_avl_tree".equals(id)) {
            switch (preset) {
                case 2: return "LL Heavy Imbalance: Sequential insert [30, 20, 10]. Detects balance factor = +2, triggering Right Rotation.";
                case 3: return "LR Double Rotation: Insert [30, 10, 20]. Imbalance requires Left Rotation on child followed by Right Rotation on parent.";
                case 1:
                default: return "RR Heavy Imbalance: Sequential insert [10, 20, 30]. Detects balance factor = -2 at root, triggering Left Rotation on pivot 20.";
            }
        }
        if ("ds_red_black_tree".equals(id) || "adv_red_black_tree".equals(id)) {
            switch (preset) {
                case 2: return "Red Uncle Recoloring: Grandparent black, parent & uncle red. Simple color flip restores red-black invariants.";
                case 3: return "Alternating Insertion: Insert keys [30, 10, 20, 50]. Balances tree maintaining equal black-depth on all paths.";
                case 1:
                default: return "Red-Red Child Conflict: Root 10 (Black), Child 15 (Red), New 25 (Red). Black uncle triggers Left Rotation and color flip.";
            }
        }
        if ("ds_splay_tree".equals(id) || "adv_splay_tree".equals(id)) {
            switch (preset) {
                case 2: return "Zig-Zag Rotation: Access key with zig-zag ancestor relationship. Dual rotations bring key to root.";
                case 3: return "Temporal Locality: Sequential lookups of key. Subsequent queries take O(1) time because target resides at root.";
                case 1:
                default: return "Deep Access Splay: Access deep leaf node 20. Performs Zig-Zig step to bring accessed item directly to root.";
            }
        }
        if ("ds_b_tree".equals(id) || "adv_b_tree".equals(id)) {
            switch (preset) {
                case 2: return "Ascending Keys [5, 10, 15, 20]: Forces upward tree growth from root, guaranteeing uniform leaf depth.";
                case 3: return "Multi-Key Search: Demonstrates binary search within node keys combined with child pointer navigation.";
                case 1:
                default: return "Order M=4 (2-3-4 Tree): Node with keys [10, 20, 30] overflows on insert 25. Median key promoted; node splits.";
            }
        }
        if ("ds_disjoint_set".equals(id) || "adv_dsu".equals(id)) {
            switch (preset) {
                case 2: return "Deep Chain Path Compression: Chain 4 -> 3 -> 2 -> 0. find(4) compresses path, rewiring all nodes directly to root.";
                case 3: return "Multi-Component Union: Merges disjoint components {0, 2} and {1, 3}, detecting connected components.";
                case 1:
                default: return "Union by Rank: Merge sets {0, 1} and {0, 2}, followed by find(4) with path compression flattening the tree.";
            }
        }
        if ("ds_bloom_filter".equals(id) || "adv_bloom_filter".equals(id)) {
            switch (preset) {
                case 2: return "High Saturation: Insert multiple elements into 16-bit array. Demonstrates hash collisions and bounded false positives.";
                case 3: return "Query Workflow: Fast O(k) membership filtering. Guarantees zero false negatives.";
                case 1:
                default: return "Multi-Hash Insertion: Insert 'apple' and 'banana' with k=3 hashes. Query 'cherry' yields guaranteed 0 false negatives.";
            }
        }
        if ("ds_lru_cache".equals(id) || "adv_lru_cache".equals(id)) {
            switch (preset) {
                case 2: return "Frequent Access: Repeated get(1) keeps key 1 warm at MRU head while older items drift toward eviction tail.";
                case 3: return "Sequential Thrashing: Capacity 3 populated with keys [1, 2, 3, 4, 5]. Every insert causes eviction of oldest entry.";
                case 1:
                default: return "Capacity 3 Cache: Keys [1, 2, 3] resident. get(2) promotes key 2 to MRU. put(4) evicts key 1 (Tail LRU) in O(1).";
            }
        }
        return "Advanced Data Structure scenario: Preset " + preset + ". Invariants maintained in amortized or logarithmic bounds.";
    }

    public static String getDefaultInput(String techniqueId, int preset) {
        String id = techniqueId != null ? techniqueId : "";
        if ("ds_trie".equals(id) || "adv_trie".equals(id)) {
            switch (preset) {
                case 2: return "car, card, care";
                case 3: return "apple, apply, app, apt";
                case 1:
                default: return "cat, car, card";
            }
        }
        if ("ds_segment_tree".equals(id) || "adv_segment_tree".equals(id)) {
            switch (preset) {
                case 2: return "2, 4, 6, 8";
                case 3: return "5, 1, 9, 3";
                case 1:
                default: return "1, 3, 5, 7";
            }
        }
        if ("ds_persistent_segment_tree".equals(id) || "adv_persistent_st".equals(id)) {
            switch (preset) {
                case 2: return "10, 20, 30, 40";
                case 3: return "4, 2, 6, 8";
                case 1:
                default: return "1, 3, 5, 7";
            }
        }
        if ("ds_fenwick_tree".equals(id) || "adv_fenwick_tree".equals(id)) {
            switch (preset) {
                case 2: return "1, 1, 1, 1, 1, 1";
                case 3: return "4, 2, 7, 1, 5, 3";
                case 1:
                default: return "2, 1, 1, 3, 2, 3";
            }
        }
        if ("ds_avl_tree".equals(id) || "adv_avl_tree".equals(id)) {
            switch (preset) {
                case 2: return "30, 20, 10";
                case 3: return "30, 10, 20";
                case 1:
                default: return "10, 20, 30";
            }
        }
        if ("ds_red_black_tree".equals(id) || "adv_red_black_tree".equals(id)) {
            switch (preset) {
                case 2: return "20, 10, 30, 5";
                case 3: return "30, 10, 20, 50";
                case 1:
                default: return "10, 15, 25";
            }
        }
        if ("ds_splay_tree".equals(id) || "adv_splay_tree".equals(id)) {
            switch (preset) {
                case 2: return "1, 2, 3, 4";
                case 3: return "25, 15, 35, 10";
                case 1:
                default: return "5, 10, 15, 20";
            }
        }
        if ("ds_b_tree".equals(id) || "adv_b_tree".equals(id)) {
            switch (preset) {
                case 2: return "5, 10, 15, 20";
                case 3: return "50, 40, 30, 20";
                case 1:
                default: return "10, 20, 30, 25";
            }
        }
        if ("ds_disjoint_set".equals(id) || "adv_dsu".equals(id)) {
            switch (preset) {
                case 2: return "0-1, 1-2, 2-3, 3-4";
                case 3: return "0-2, 1-3, 2-4";
                case 1:
                default: return "0-1, 0-2, 3-4";
            }
        }
        if ("ds_bloom_filter".equals(id) || "adv_bloom_filter".equals(id)) {
            switch (preset) {
                case 2: return "alpha, beta, gamma";
                case 3: return "cat, dog, bird, fish";
                case 1:
                default: return "apple, banana, cherry";
            }
        }
        if ("ds_lru_cache".equals(id) || "adv_lru_cache".equals(id)) {
            switch (preset) {
                case 2: return "1, 2, 1, 2, 3";
                case 3: return "1, 2, 3, 4, 5";
                case 1:
                default: return "1, 2, 3, 2, 4";
            }
        }
        return "10, 20, 30, 40";
    }

    public static int getTotalSteps(String techniqueId, int preset) {
        String id = techniqueId != null ? techniqueId : "";
        if ("ds_trie".equals(id) || "adv_trie".equals(id)) {
            return 6;
        }
        if ("ds_segment_tree".equals(id) || "adv_segment_tree".equals(id)) {
            return 6;
        }
        if ("ds_persistent_segment_tree".equals(id) || "adv_persistent_st".equals(id)) {
            return 5;
        }
        if ("ds_fenwick_tree".equals(id) || "adv_fenwick_tree".equals(id)) {
            return 6;
        }
        if ("ds_avl_tree".equals(id) || "adv_avl_tree".equals(id)) {
            return 5;
        }
        if ("ds_red_black_tree".equals(id) || "adv_red_black_tree".equals(id)) {
            return 5;
        }
        if ("ds_splay_tree".equals(id) || "adv_splay_tree".equals(id)) {
            return 5;
        }
        if ("ds_b_tree".equals(id) || "adv_b_tree".equals(id)) {
            return 5;
        }
        if ("ds_disjoint_set".equals(id) || "adv_dsu".equals(id)) {
            return 5;
        }
        if ("ds_bloom_filter".equals(id) || "adv_bloom_filter".equals(id)) {
            return 5;
        }
        if ("ds_lru_cache".equals(id) || "adv_lru_cache".equals(id)) {
            return 5;
        }
        return 5;
    }
}
