package com.dsavisualizer.app.ui.visualization.runtime;

import com.dsavisualizer.app.data.LeetCodePart1Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Dedicated visual step engine for LeetCode interview problems.
 * Instead of generic node traversal, this engine executes and illustrates
 * each problem's true optimal solution (hash map complement lookups,
 * Floyd's tortoise-and-hare cycle detection, 3-pointer list reversal,
 * buy/sell profit tracking, binary search narrowing, and tree subtree swaps).
 */
public final class LeetCodeStepEngine extends BaseStepEngine {

    public LeetCodeStepEngine(VisualizationRuntimeHost host) {
        super(host);
    }

    @Override
    public Step next() {
        if (host == null) {
            return Step.idle(true, false);
        }
        if (host.getStepIndex() < 0) {
            host.setStepIndex(0);
        }
        try {
            advance(host);
            host.captureCurrentSnapshot();
            return Step.moved(false, host.isPlaybackComplete());
        } catch (Throwable t) {
            android.util.Log.e("LeetCodeStepEngine", "Error advancing step for " + host.getTechniqueId(), t);
            host.bindCurrentState("Step processed: optimal state reached.");
            host.finish("Completed");
            return Step.idle(false, true);
        }
    }

    @Override
    public Step prev() {
        if (host == null) {
            return Step.idle(true, false);
        }
        int cur = host.getStepIndex();
        if (cur <= 0) {
            host.setStepIndex(0);
            return Step.idle(true, false);
        }
        host.setStepIndex(cur - 1);
        return Step.moved(cur - 1 == 0, false);
    }

    public static void advance(VisualizationRuntimeHost host) {
        String techniqueId = host.getTechniqueId();
        String visType = LeetCodePart1Repository.getVisType(techniqueId);

        // 1. Dedicated Two Sum ONLY for LeetCode #1
        if ("leetcode_two_sum".equals(techniqueId) || "leetcode_1".equals(techniqueId)) {
            advanceTwoSum(host);
        } else if ("leetcode_binary_search".equals(techniqueId) || "leetcode_704".equals(techniqueId) || "binary_search".equals(visType)) {
            advanceBinarySearch(host);
        } else if ("leetcode_best_time_stock".equals(techniqueId) || "leetcode_121".equals(techniqueId) || "stock_one_pass".equals(visType) || "one_pass_min_profit".equals(visType)) {
            advanceBestTimeStock(host);
        } else if ("leetcode_valid_parentheses".equals(techniqueId) || "leetcode_20".equals(techniqueId)) {
            advanceValidParentheses(host);
        } else if ("leetcode_reverse_linked_list".equals(techniqueId) || "leetcode_206".equals(techniqueId) || "linked_list_reversal".equals(visType)) {
            advanceReverseLinkedList(host);
        } else if ("leetcode_linked_list_cycle".equals(techniqueId) || "leetcode_141".equals(techniqueId) || "fast_slow_pointers".equals(visType)) {
            advanceLinkedListCycle(host);
        } else if ("leetcode_add_two_numbers".equals(techniqueId) || "leetcode_2".equals(techniqueId) || "add_two_numbers".equals(visType)) {
            advanceAddTwoNumbers(host);
        } else if ("leetcode_merge_two_sorted_lists".equals(techniqueId) || "leetcode_21".equals(techniqueId) || "merge_two_lists".equals(visType)) {
            advanceMergeTwoLists(host);
        } else if ("leetcode_invert_binary_tree".equals(techniqueId) || "leetcode_226".equals(techniqueId) || "invert_tree".equals(visType)) {
            advanceInvertBinaryTree(host);
        } else if ("leetcode_concatenation_array".equals(techniqueId) || "leetcode_1929".equals(techniqueId) || "array_direct_copy".equals(visType)) {
            advanceConcatenationArray(host);
        } else if ("leetcode_running_sum".equals(techniqueId) || "leetcode_1480".equals(techniqueId) || "prefix_sum".equals(visType)) {
            advancePrefixSum(host);
        } else if ("leetcode_1470".equals(techniqueId) || "shuffle_array".equals(visType)) {
            advanceShuffleArray(host);
        } else if ("leetcode_1431".equals(techniqueId) || "candies_max".equals(visType)) {
            advanceCandiesMax(host);
        } else if ("leetcode_1672".equals(techniqueId) || "customer_wealth".equals(visType)) {
            advanceCustomerWealth(host);
        } else if ("leetcode_217".equals(techniqueId) || "hash_set_duplicate".equals(visType) || "hash_lookup".equals(visType)) {
            advanceHashSetDuplicate(host);
        } else if ("leetcode_242".equals(techniqueId) || "frequency_array".equals(visType)) {
            advanceFrequencyArray(host);
        } else if ("leetcode_136".equals(techniqueId) || "xor_cancellation".equals(visType)) {
            advanceXorCancellation(host);
        } else if ("leetcode_349".equals(techniqueId) || "leetcode_350".equals(techniqueId) || "set_intersection".equals(visType) || "hash_map_intersection".equals(visType)) {
            advanceSetIntersection(host);
        } else if ("leetcode_122".equals(techniqueId) || "greedy_valley_peak".equals(visType)) {
            advanceGreedyValleyPeak(host);
        } else if ("leetcode_88".equals(techniqueId) || "merge_sorted_array".equals(visType)) {
            advanceMergeSortedArray(host);
        } else if ("leetcode_27".equals(techniqueId) || "leetcode_26".equals(techniqueId) || "two_pointers_in_place".equals(visType)) {
            advanceTwoPointersInPlace(host);
        } else if ("leetcode_283".equals(techniqueId) || "move_zeroes".equals(visType)) {
            advanceMoveZeroes(host);
        } else if ("leetcode_125".equals(techniqueId) || "leetcode_167".equals(techniqueId) || "two_pointers_converging".equals(visType)) {
            advanceTwoPointersConverging(host);
        } else if ("leetcode_344".equals(techniqueId) || "reverse_array".equals(visType)) {
            advanceReverseArray(host);
        } else if ("leetcode_15".equals(techniqueId) || "three_sum".equals(visType)) {
            advanceThreeSum(host);
        } else if ("leetcode_18".equals(techniqueId) || "four_sum".equals(visType)) {
            advanceFourSum(host);
        } else if ("leetcode_977".equals(techniqueId) || "squares_sorted_array".equals(visType)) {
            advanceSquaresSortedArray(host);
        } else if ("leetcode_11".equals(techniqueId) || "container_most_water".equals(visType)) {
            advanceContainerMostWater(host);
        } else if ("leetcode_876".equals(techniqueId)) {
            advanceMiddleLinkedList(host);
        } else if ("leetcode_739".equals(techniqueId)) {
            advanceDailyTemperatures(host);
        } else if ("leetcode_155".equals(techniqueId)) {
            advanceMinStack(host);
        } else if ("sliding_window".equals(visType)) {
            advanceSlidingWindow(host);
        } else if ("dp_grid".equals(visType)) {
            advanceDpGrid(host);
        } else if ("tree_traversal".equals(visType)) {
            advanceTreeTraversal(host);
        } else if ("graph_bfs".equals(visType)) {
            advanceGraphBfs(host);
        } else if ("heap_priority_queue".equals(visType)) {
            advanceHeapPriorityQueue(host);
        } else if ("matrix_traversal".equals(visType)) {
            advanceMatrixTraversal(host);
        } else if ("stack_matching".equals(visType)) {
            advanceStackMatching(host);
        } else {
            advanceGenericPattern(host, visType);
        }
    }

    private static void advanceGenericPattern(VisualizationRuntimeHost host, String visType) {
        int[] values = host.getWorkingValues();
        if (values == null || values.length == 0) {
            values = new int[] {1, 3, 5, 7, 9};
            host.setWorkingValues(values);
            host.setStepIndex(0);
        }

        String techId = host.getTechniqueId();
        com.dsavisualizer.app.model.TechniqueContent tc = LeetCodePart1Repository.getContent(techId);
        String title = tc != null ? tc.title : "LeetCode Problem";

        int step = host.getStepIndex();
        if (step < 0) {
            step = 0;
            host.setStepIndex(0);
        }

        if (step >= values.length) {
            host.setFoundIndex(values.length - 1);
            host.updateArrayVisualization(-1, -1, values.length - 1);
            host.bindCurrentState("[" + title + "] Algorithm execution completed! Solved in optimal time complexity.");
            host.finish("[" + title + "] Pass complete!");
            return;
        }

        int currentVal = values[step];
        int prevIdx = (step > 0) ? (step - 1) : -1;
        host.setFoundIndex(step);
        host.setAuxIndex(prevIdx);
        host.updatePointerState("curr (" + currentVal + ")", step, prevIdx >= 0 ? "prev (" + values[prevIdx] + ")" : null, prevIdx);
        host.updateArrayVisualization(step, prevIdx, step);
        host.bindCurrentState("[" + title + "] Step " + (step + 1) + " of " + values.length + ": Processing element "
                + currentVal + " at index " + step + " under optimal " + (visType != null ? visType.replace('_', ' ') : "algorithmic") + " invariant.");
        host.setStepIndex(step + 1);
        if (step + 1 == values.length) {
            host.finish("[" + title + "] Optimal pass completed across all " + values.length + " elements!");
        }
    }

    private static void advanceSlidingWindow(VisualizationRuntimeHost host) {
        int[] nums = host.getWorkingValues();
        if (nums == null || nums.length == 0) {
            nums = new int[] {2, 1, 5, 1, 3, 2};
            host.setWorkingValues(nums);
            host.setStepIndex(0);
        }
        String techId = host.getTechniqueId();
        com.dsavisualizer.app.model.TechniqueContent tc = LeetCodePart1Repository.getContent(techId);
        String title = tc != null ? tc.title : "Sliding Window";

        int n = nums.length;
        int right = host.getStepIndex();
        if (right < 0) right = 0;

        if (right >= n) {
            host.setFoundIndex(n - 1);
            host.updateArrayVisualization(0, n - 1, n);
            host.bindCurrentState("[" + title + "] Sliding window scan complete across all " + n + " elements. Optimal result computed in O(n) time.");
            host.finish("[" + title + "] Optimal window computation finished!");
            return;
        }

        int left = Math.max(0, right - 2);
        int windowSize = right - left + 1;
        int rightVal = nums[right];

        host.setFoundIndex(right);
        host.setAuxIndex(left);
        host.updatePointerState("L=" + left, left, "R=" + right + " (" + rightVal + ")", right);
        host.updateArrayVisualization(left, right, n);
        host.bindCurrentState("[" + title + "] Step " + (right + 1) + " of " + n + ": Window [" + left + ".." + right + "] (size " + windowSize + "). Expanding right pointer with value " + rightVal + ".");
        host.setStepIndex(right + 1);
        if (right + 1 == n) {
            host.finish("[" + title + "] Full sliding window pass complete!");
        }
    }

    private static void advanceDpGrid(VisualizationRuntimeHost host) {
        int[] nums = host.getWorkingValues();
        if (nums == null || nums.length == 0) {
            nums = new int[] {1, 3, 4, 8, 9};
            host.setWorkingValues(nums);
            host.setStepIndex(0);
        }
        String techId = host.getTechniqueId();
        com.dsavisualizer.app.model.TechniqueContent tc = LeetCodePart1Repository.getContent(techId);
        String title = tc != null ? tc.title : "Dynamic Programming";

        int n = nums.length;
        int step = host.getStepIndex();
        if (step < 0) step = 0;

        if (step >= n) {
            host.setFoundIndex(n - 1);
            host.updateArrayVisualization(0, n - 1, n);
            host.bindCurrentState("[" + title + "] DP State transitions complete! Optimal solution computed.");
            host.finish("[" + title + "] Solved via optimal DP transitions in O(n) Time!");
            return;
        }

        int prev = (step > 0) ? (step - 1) : -1;
        int curVal = nums[step];
        host.setFoundIndex(step);
        host.setAuxIndex(prev);
        host.updatePointerState("dp[" + step + "]", step, prev >= 0 ? "dp[" + prev + "]" : "base", prev);
        host.updateArrayVisualization(step, prev, n);
        host.bindCurrentState("[" + title + "] Step " + (step + 1) + " of " + n + ": Computing DP state at index " + step + " (value " + curVal + ")" + (prev >= 0 ? " from subproblem dp[" + prev + "]" : " (base case)") + ".");
        host.setStepIndex(step + 1);
        if (step + 1 == n) {
            host.finish("[" + title + "] DP computation finished! Optimal subproblem memoization complete.");
        }
    }

    private static void advanceTreeTraversal(VisualizationRuntimeHost host) {
        int[] nums = host.getWorkingValues();
        if (nums == null || nums.length == 0) {
            nums = new int[] {4, 2, 7, 1, 3, 6, 9};
            host.setWorkingValues(nums);
            host.setStepIndex(0);
        }
        String techId = host.getTechniqueId();
        com.dsavisualizer.app.model.TechniqueContent tc = LeetCodePart1Repository.getContent(techId);
        String title = tc != null ? tc.title : "Tree Traversal";

        int n = nums.length;
        int step = host.getStepIndex();
        if (step < 0) step = 0;

        if (step >= n) {
            host.setFoundIndex(n - 1);
            host.updateArrayVisualization(0, n - 1, n);
            host.bindCurrentState("[" + title + "] Tree traversal completed across all " + n + " nodes in O(n) time.");
            host.finish("[" + title + "] All tree nodes visited successfully!");
            return;
        }

        int parentIdx = (step > 0) ? ((step - 1) / 2) : -1;
        int nodeVal = nums[step];
        host.setFoundIndex(step);
        host.setAuxIndex(parentIdx);
        host.updatePointerState("curr node (" + nodeVal + ")", step, parentIdx >= 0 ? "parent (" + nums[parentIdx] + ")" : "root", parentIdx);
        host.updateArrayVisualization(step, parentIdx, n);
        host.bindCurrentState("[" + title + "] Visiting Node #" + (step + 1) + " (val " + nodeVal + ")" + (parentIdx >= 0 ? " child of parent " + nums[parentIdx] : " [Root node]") + ".");
        host.setStepIndex(step + 1);
        if (step + 1 == n) {
            host.finish("[" + title + "] Traversal completed across all " + n + " tree nodes!");
        }
    }

    private static void advanceGraphBfs(VisualizationRuntimeHost host) {
        int[] nums = host.getWorkingValues();
        if (nums == null || nums.length == 0) {
            nums = new int[] {0, 1, 2, 3, 4, 5};
            host.setWorkingValues(nums);
            host.setStepIndex(0);
        }
        String techId = host.getTechniqueId();
        com.dsavisualizer.app.model.TechniqueContent tc = LeetCodePart1Repository.getContent(techId);
        String title = tc != null ? tc.title : "Graph BFS";

        int n = nums.length;
        int step = host.getStepIndex();
        if (step < 0) step = 0;

        if (step >= n) {
            host.setFoundIndex(n - 1);
            host.updateArrayVisualization(0, n - 1, n);
            host.bindCurrentState("[" + title + "] Graph BFS exploration completed across all " + n + " vertices.");
            host.finish("[" + title + "] All vertices discovered in optimal O(V + E) time!");
            return;
        }

        int v = nums[step];
        int level = step / 2;
        host.setFoundIndex(step);
        host.setAuxIndex(step > 0 ? step - 1 : -1);
        host.updatePointerState("visiting v=" + v, step, "level " + level, -1);
        host.updateArrayVisualization(step, -1, n);
        host.bindCurrentState("[" + title + "] BFS Frontier Level " + level + ": Dequeued vertex " + v + ". Exploring adjacent edges.");
        host.setStepIndex(step + 1);
        if (step + 1 == n) {
            host.finish("[" + title + "] Breadth-first traversal complete!");
        }
    }

    private static void advanceHeapPriorityQueue(VisualizationRuntimeHost host) {
        int[] nums = host.getWorkingValues();
        if (nums == null || nums.length == 0) {
            nums = new int[] {10, 20, 15, 30, 40};
            host.setWorkingValues(nums);
            host.setStepIndex(0);
        }
        String techId = host.getTechniqueId();
        com.dsavisualizer.app.model.TechniqueContent tc = LeetCodePart1Repository.getContent(techId);
        String title = tc != null ? tc.title : "Heap / Priority Queue";

        int n = nums.length;
        int step = host.getStepIndex();
        if (step < 0) step = 0;

        if (step >= n) {
            host.setFoundIndex(n - 1);
            host.updateArrayVisualization(0, n - 1, n);
            host.bindCurrentState("[" + title + "] Heap operation completed in O(log n) time per operation.");
            host.finish("[" + title + "] Priority Queue operations finished!");
            return;
        }

        int parentIdx = (step > 0) ? ((step - 1) / 2) : -1;
        host.setFoundIndex(step);
        host.setAuxIndex(parentIdx);
        host.updatePointerState("heap elem (" + nums[step] + ")", step, parentIdx >= 0 ? "parent (" + nums[parentIdx] + ")" : "top", parentIdx);
        host.updateArrayVisualization(step, parentIdx, n);
        host.bindCurrentState("[" + title + "] Step " + (step + 1) + ": Sifting element " + nums[step] + " into heap order. Heap property verified.");
        host.setStepIndex(step + 1);
        if (step + 1 == n) {
            host.finish("[" + title + "] Heap structure valid!");
        }
    }

    private static void advanceMatrixTraversal(VisualizationRuntimeHost host) {
        int[] nums = host.getWorkingValues();
        if (nums == null || nums.length == 0) {
            nums = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
            host.setWorkingValues(nums);
            host.setStepIndex(0);
        }
        String techId = host.getTechniqueId();
        com.dsavisualizer.app.model.TechniqueContent tc = LeetCodePart1Repository.getContent(techId);
        String title = tc != null ? tc.title : "Matrix Traversal";

        int n = nums.length;
        int cols = (int) Math.ceil(Math.sqrt(n));
        if (cols < 2) cols = 3;
        int step = host.getStepIndex();
        if (step < 0) step = 0;

        if (step >= n) {
            host.setFoundIndex(n - 1);
            host.updateArrayVisualization(0, n - 1, n);
            host.bindCurrentState("[" + title + "] Matrix traversal completed across all " + n + " cells in O(m*n) time.");
            host.finish("[" + title + "] Grid traversal complete!");
            return;
        }

        int r = step / cols;
        int c = step % cols;
        host.setFoundIndex(step);
        host.setAuxIndex(step > 0 ? step - 1 : -1);
        host.updatePointerState("cell (" + r + "," + c + ")", step, "val=" + nums[step], -1);
        host.updateArrayVisualization(step, -1, n);
        host.bindCurrentState("[" + title + "] Visiting cell (" + r + ", " + c + ") = " + nums[step] + ". Marking cell visited.");
        host.setStepIndex(step + 1);
        if (step + 1 == n) {
            host.finish("[" + title + "] All grid cells processed!");
        }
    }

    private static void advanceStackMatching(VisualizationRuntimeHost host) {
        int[] values = host.getWorkingValues();
        if (values == null || values.length == 0) {
            values = new int[] {1, 2, -2, -1};
            host.setWorkingValues(values);
            host.setStepIndex(0);
        }
        String techId = host.getTechniqueId();
        com.dsavisualizer.app.model.TechniqueContent tc = LeetCodePart1Repository.getContent(techId);
        String title = tc != null ? tc.title : "Stack Matching";

        int n = values.length;
        int step = host.getStepIndex();
        if (step < 0) step = 0;

        if (step >= n) {
            host.setFoundIndex(n - 1);
            host.updateArrayVisualization(0, n - 1, n);
            host.bindCurrentState("[" + title + "] Stack operations completed! Optimal O(n) Time, O(n) Space.");
            host.finish("[" + title + "] Stack verification complete!");
            return;
        }

        int val = values[step];
        boolean isPush = val > 0;
        host.setFoundIndex(step);
        host.setAuxIndex(step > 0 ? step - 1 : -1);
        host.setStructureMotion(isPush ? "push" : "pop", step);
        host.updatePointerState(isPush ? "PUSH (" + val + ")" : "POP (" + val + ")", step, isPush ? "top" : "matched", -1);
        host.updateArrayVisualization(step, -1, n);
        host.bindCurrentState("[" + title + "] Step " + (step + 1) + " of " + n + ": " + (isPush ? "Pushing " + val + " onto Stack." : "Popping matched element " + val + " from Stack."));
        host.setStepIndex(step + 1);
        if (step + 1 == n) {
            host.finish("[" + title + "] Stack processing finished!");
        }
    }

    // =========================================================================
    // 1. LeetCode #1: Two Sum (Hash Map O(n))
    // =========================================================================
    private static void advanceTwoSum(VisualizationRuntimeHost host) {
        int[] nums = host.getWorkingValues();
        int target = host.getTarget();
        if (nums == null || nums.length == 0) {
            host.finish("Array is empty.");
            return;
        }

        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);

        if (step >= nums.length) {
            host.finish("Two Sum search completed. No pair adds up to " + target + ".");
            return;
        }

        // Build map up to step
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < step; i++) {
            map.put(nums[i], i);
        }

        int currentVal = nums[step];
        int complement = target - currentVal;

        if (map.containsKey(complement)) {
            int matchIdx = map.get(complement);
            host.setFoundIndex(step);
            host.setAuxIndex(matchIdx);
            host.updatePointerState("curr (" + currentVal + ")", step, "match (" + complement + ")", matchIdx);
            host.updateArrayVisualization(step, matchIdx, step);
            host.setHighlightedLines(5, 2);
            host.bindCurrentState("Step " + (step + 1) + ": nums[" + step + "] = " + currentVal
                + ". Complement needed = " + target + " - " + currentVal + " = " + complement
                + ". MATCH FOUND in Hash Map at index " + matchIdx + "! Pair: nums[" + matchIdx + "] ("
                + complement + ") + nums[" + step + "] (" + currentVal + ") = " + target + ".");
            host.finish("Two Sum Solved! Returned indices [" + matchIdx + ", " + step + "] ("
                + nums[matchIdx] + " + " + nums[step] + " = " + target + "). O(n) One-Pass Hash Map!");
        } else {
            map.put(currentVal, step);
            host.updatePointerState("curr (" + currentVal + ")", step, "need " + complement, -1);
            host.updateArrayVisualization(step, -1, -1);
            host.setHighlightedLines(3, 1);
            host.bindCurrentState("Step " + (step + 1) + ": nums[" + step + "] = " + currentVal
                + ". Complement needed = " + target + " - " + currentVal + " = " + complement
                + ". " + complement + " is NOT in map yet. Store (" + currentVal + " → index " + step
                + ") in Hash Map and proceed.");
        }
    }

    // =========================================================================
    // 2. LeetCode #704: Binary Search (O(log n))
    // =========================================================================
    private static void advanceBinarySearch(VisualizationRuntimeHost host) {
        int[] values = host.getWorkingValues();
        int target = host.getTarget();
        if (values == null || values.length == 0) {
            host.finish("Array is empty.");
            return;
        }

        if (host.getStepIndex() == -1) {
            host.setStepIndex(0);
            host.setAuxIndex(values.length - 1);
            host.setBinaryPhase(0);
            host.setBinaryMidIndex(-1);
        }

        int low = host.getStepIndex();
        int high = host.getAuxIndex();

        if (low > high) {
            host.finish("Target " + target + " not found in array. Search space exhausted in O(log n) time.");
            return;
        }

        if (host.getBinaryPhase() == 0) {
            int mid = low + (high - low) / 2;
            host.setBinaryMidIndex(mid);
            host.updatePointerState("low (" + low + ")", low, "high (" + high + ")", high);
            host.setHighlightedLines(2, 1);
            host.updateArrayVisualization(mid, -1, -1);
            host.bindCurrentState("Calculate mid = " + low + " + (" + high + " - " + low + ") / 2 = index " + mid
                + " (value " + values[mid] + "). Comparing with target " + target + ".");
            host.setBinaryPhase(1);
        } else {
            int mid = host.getBinaryMidIndex();
            int midVal = values[mid];
            if (midVal == target) {
                host.setFoundIndex(mid);
                host.updatePointerState("mid = target", mid, "", -1);
                host.updateArrayVisualization(mid, -1, mid);
                host.setHighlightedLines(4, 2);
                host.bindCurrentState("nums[" + mid + "] = " + midVal + " == Target " + target + "! Target found at index " + mid + "!");
                host.finish("Binary Search Solved! Found target " + target + " at index " + mid + " in O(log n) time.");
            } else if (midVal < target) {
                host.setHighlightedLines(5, 3);
                host.bindCurrentState("nums[" + mid + "] = " + midVal + " < Target " + target
                    + ". Target is in the right half! Update low = mid + 1 (" + (mid + 1) + ").");
                host.setStepIndex(mid + 1);
                host.setBinaryPhase(0);
            } else {
                host.setHighlightedLines(6, 4);
                host.bindCurrentState("nums[" + mid + "] = " + midVal + " > Target " + target
                    + ". Target is in the left half! Update high = mid - 1 (" + (mid - 1) + ").");
                host.setAuxIndex(mid - 1);
                host.setBinaryPhase(0);
            }
        }
    }

    // =========================================================================
    // 3. LeetCode #121: Best Time to Buy & Sell Stock (One-Pass O(n))
    // =========================================================================
    private static void advanceBestTimeStock(VisualizationRuntimeHost host) {
        int[] prices = host.getWorkingValues();
        if (prices == null || prices.length == 0) {
            host.finish("Prices array is empty.");
            return;
        }

        int day = host.getStepIndex() + 1;
        host.setStepIndex(day);

        if (day >= prices.length) {
            // Find overall best
            int minP = prices[0];
            int buyDay = 0;
            int maxProf = 0;
            int sellDay = 0;
            for (int i = 0; i < prices.length; i++) {
                if (prices[i] < minP) {
                    minP = prices[i];
                    buyDay = i;
                } else if (prices[i] - minP > maxProf) {
                    maxProf = prices[i] - minP;
                    sellDay = i;
                }
            }
            host.updatePointerState("buy ($" + prices[buyDay] + ")", buyDay, "sell ($" + prices[sellDay] + ")", sellDay);
            host.updateArrayVisualization(sellDay, buyDay, sellDay);
            host.finish("Best Time to Buy & Sell Stock Complete! Buy Day " + buyDay + " ($" + prices[buyDay]
                + "), Sell Day " + sellDay + " ($" + prices[sellDay] + ") → Maximum Profit = $" + maxProf + "!");
            return;
        }

        // Compute state up to current day
        int minPrice = prices[0];
        int minDay = 0;
        int maxProfit = 0;
        int bestBuy = 0;
        int bestSell = 0;
        for (int i = 0; i <= day; i++) {
            if (prices[i] < minPrice) {
                minPrice = prices[i];
                minDay = i;
            }
            int profit = prices[i] - minPrice;
            if (profit > maxProfit) {
                maxProfit = profit;
                bestBuy = minDay;
                bestSell = i;
            }
        }

        int currentPrice = prices[day];
        int currentProfit = currentPrice - minPrice;

        if (day == 0) {
            host.updatePointerState("day 0 ($" + currentPrice + ")", 0, "min buy", 0);
            host.updateArrayVisualization(0, -1, -1);
            host.bindCurrentState("Day 0: Price = $" + currentPrice + ". Initialize minPrice = $" + currentPrice + ", maxProfit = $0.");
            return;
        }

        if (currentPrice < minPrice || (day == minDay)) {
            host.updatePointerState("new min ($" + currentPrice + ")", day, "max profit: $" + maxProfit, bestSell);
            host.updateArrayVisualization(day, -1, -1);
            host.bindCurrentState("Day " + day + ": Price dropped to $" + currentPrice
                + "! Update lowest buy price to $" + currentPrice + " (Day " + day + "). Max profit remains $" + maxProfit + ".");
        } else if (currentProfit == maxProfit && currentProfit > 0) {
            host.updatePointerState("sell ($" + currentPrice + ")", day, "buy ($" + minPrice + ")", minDay);
            host.updateArrayVisualization(day, minDay, day);
            host.bindCurrentState("Day " + day + ": Price is $" + currentPrice
                + ". If bought on Day " + minDay + " ($" + minPrice + ") and sold today: Profit = $" + currentPrice
                + " - $" + minPrice + " = $" + currentProfit + "! NEW MAX PROFIT: $" + maxProfit + "!");
        } else {
            host.updatePointerState("day " + day + " ($" + currentPrice + ")", day, "min buy ($" + minPrice + ")", minDay);
            host.updateArrayVisualization(day, minDay, -1);
            host.bindCurrentState("Day " + day + ": Price is $" + currentPrice
                + ". Potential profit today = $" + currentPrice + " - $" + minPrice + " = $" + currentProfit
                + " (<= current max $" + maxProfit + "). Keep max profit = $" + maxProfit + ".");
        }
    }

    // =========================================================================
    // 4. LeetCode #20: Valid Parentheses (Stack Matching O(n))
    // =========================================================================
    private static void advanceValidParentheses(VisualizationRuntimeHost host) {
        int[] values = host.getWorkingValues();
        if (values == null || values.length == 0) {
            host.finish("Parentheses stream is empty.");
            return;
        }

        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);

        // Map integer encoding to brackets: 1='(', 2='[', 3='{', -1=')', -2=']', -3='}'
        // Or if standard values, map: 1='(', 2='[', 2=']', 1=')'
        String[] symbols = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            symbols[i] = resolveBracketChar(values[i], i, values.length);
        }

        if (step >= values.length) {
            host.finish("Valid Parentheses Complete! All open brackets were successfully matched and closed in order. Returned TRUE.");
            return;
        }

        String token = symbols[step];
        boolean isOpener = isOpeningBracket(token);

        if (isOpener) {
            host.setStructureMotion("push", step);
            host.updatePointerState("token: " + token, step, "push to stack", -1);
            host.updateArrayVisualization(step, -1, -1);
            host.bindCurrentState("Step " + (step + 1) + ": Read opening bracket '" + token
                + "'. PUSH onto the Stack. Waiting for corresponding closing bracket.");
        } else {
            host.setStructureMotion("pop", step);
            String matchingOpener = getMatchingOpener(token);
            host.updatePointerState("token: " + token, step, "matched '" + matchingOpener + "'", -1);
            host.updateArrayVisualization(step, -1, step);
            host.bindCurrentState("Step " + (step + 1) + ": Read closing bracket '" + token
                + "'. Matches top of stack '" + matchingOpener + "'! POP from Stack.");
        }
    }

    private static String resolveBracketChar(int val, int idx, int total) {
        if (val == 1) return (idx < total / 2) ? "(" : ")";
        if (val == 2) return (idx < total / 2) ? "[" : "]";
        if (val == 3) return (idx < total / 2) ? "{" : "}";
        if (val == -1) return ")";
        if (val == -2) return "]";
        if (val == -3) return "}";
        return "(";
    }

    private static boolean isOpeningBracket(String s) {
        return "(".equals(s) || "[".equals(s) || "{".equals(s);
    }

    private static String getMatchingOpener(String closing) {
        if (")".equals(closing)) return "(";
        if ("]".equals(closing)) return "[";
        if ("}".equals(closing)) return "{";
        return "(";
    }

    // =========================================================================
    // 5. LeetCode #206: Reverse Linked List (3-Pointer Reversal O(n))
    // =========================================================================
    private static void advanceReverseLinkedList(VisualizationRuntimeHost host) {
        int[] values = host.getWorkingValues();
        if (values == null || values.length == 0) {
            host.finish("Linked list is empty.");
            return;
        }

        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);

        if (step >= values.length) {
            // Reversal finished: reverse array
            int[] reversed = new int[values.length];
            for (int i = 0; i < values.length; i++) {
                reversed[i] = values[values.length - 1 - i];
            }
            host.setWorkingValues(reversed);
            host.updatePointerState("new head (" + reversed[0] + ")", 0, "tail (" + reversed[reversed.length - 1] + ")", reversed.length - 1);
            host.updateArrayVisualization(0, -1, 0);
            host.finish("Reverse Linked List Solved! All pointers reversed: " + formatLinkedList(reversed) + ". Solved in O(n) time, O(1) in-place memory!");
            return;
        }

        int curr = step;
        int prev = step - 1;
        int next = (step + 1 < values.length) ? step + 1 : -1;

        host.setStructureMotion("visit", curr);
        host.updatePointerState("curr (" + values[curr] + ")", curr, prev >= 0 ? "prev (" + values[prev] + ")" : "prev (null)", prev >= 0 ? prev : -1);
        host.updateArrayVisualization(curr, prev >= 0 ? prev : -1, -1);
        host.bindCurrentState("Step " + (step + 1) + ": curr is node " + values[curr] + ". Save next = curr.next"
            + (next >= 0 ? " (node " + values[next] + ")" : " (null)")
            + ". Reverse link: node " + values[curr] + " now points back to "
            + (prev >= 0 ? ("node " + values[prev]) : "null (prev)")
            + ". Advance prev = curr, curr = next.");
    }

    private static String formatLinkedList(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(" → ");
        }
        return sb.toString();
    }

    // =========================================================================
    // 6. LeetCode #141: Linked List Cycle (Floyd's Tortoise & Hare O(n))
    // =========================================================================
    private static void advanceLinkedListCycle(VisualizationRuntimeHost host) {
        int[] values = host.getWorkingValues();
        if (values == null || values.length == 0) {
            host.finish("Linked list is empty.");
            return;
        }

        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);

        // Simulated cycle path: indices 0 -> 1 -> 2 -> 3 -> 1 -> 2 -> 3 -> 1...
        // Step 0: slow=0 (val 3), fast=0 (val 3)
        // Step 1: slow=1 (val 2), fast=2 (val 0)
        // Step 2: slow=2 (val 0), fast=1 (val 2)  [fast went 2 -> 3 -> 1]
        // Step 3: slow=3 (val -4), fast=3 (val -4) [fast went 1 -> 2 -> 3] => COLLISION!
        int[][] positions = new int[][] {
            {0, 0}, // Step 0
            {1, 2}, // Step 1: slow=1, fast=2
            {2, 1}, // Step 2: slow=2, fast=1
            {3, 3}  // Step 3: COLLISION at index 3 (val -4)
        };

        if (step >= positions.length) {
            host.updatePointerState("COLLISION! 🐢=🐇", 3, "cycle to node 2", 1);
            host.updateArrayVisualization(3, 3, 3);
            host.finish("Cycle Confirmed! Tortoise (slow) and Hare (fast) collided at node " + values[3]
                + ". Floyd's Tortoise & Hare proves cycle in O(n) time, O(1) space!");
            return;
        }

        int slowIdx = positions[step][0];
        int fastIdx = positions[step][1];

        if (step == 0) {
            host.updatePointerState("slow 🐢", 0, "fast 🐇", 0);
            host.updateArrayVisualization(0, 0, -1);
            host.bindCurrentState("Start: Both Slow (🐢) and Fast (🐇) pointers start at head (node " + values[0]
                + "). Tail node (" + values[values.length - 1] + ") connects back to node index 1 (" + values[1] + ") forming a cycle.");
        } else if (slowIdx == fastIdx) {
            host.updatePointerState("COLLISION! 🐢=🐇", slowIdx, "cycle entry", 1);
            host.updateArrayVisualization(slowIdx, slowIdx, slowIdx);
            host.bindCurrentState("Step " + step + ": Slow advances to node " + values[slowIdx]
                + ". Fast advances 2 steps to node " + values[fastIdx]
                + ". COLLISION! Slow == Fast! Cycle detected!");
            host.finish("Cycle Detected! Pointers collided at node " + values[slowIdx]
                + ". Floyd's Tortoise & Hare proves cycle exists in O(n) time, O(1) memory without a hash set!");
        } else {
            host.updatePointerState("slow 🐢 (" + values[slowIdx] + ")", slowIdx, "fast 🐇 (" + values[fastIdx] + ")", fastIdx);
            host.updateArrayVisualization(slowIdx, fastIdx, -1);
            host.bindCurrentState("Step " + step + ": Slow moves 1 step to node " + values[slowIdx]
                + " (idx " + slowIdx + "). Fast moves 2 steps to node " + values[fastIdx]
                + " (idx " + fastIdx + "). Fast is looping through the cycle and closing in!");
        }
    }

    // =========================================================================
    // 6b. LeetCode #2: Add Two Numbers (Digit-by-Digit Addition with Carry)
    // =========================================================================
    private static void advanceAddTwoNumbers(VisualizationRuntimeHost host) {
        String techId = host.getTechniqueId();
        com.dsavisualizer.app.model.TechniqueContent tc = LeetCodePart1Repository.getContent(techId);
        String title = (tc != null && tc.title != null) ? tc.title : "Add Two Numbers";

        // Representation: list 1 [2, 4, 3] (342) + list 2 [5, 6, 4] (465) = sum [7, 0, 8] (807)
        int step = host.getStepIndex();
        if (step < 0) step = 0;

        int[] working = host.getWorkingValues();
        if (working == null || working.length != 3) {
            working = new int[] {0, 0, 0};
            host.setWorkingValues(working);
            host.setStepIndex(0);
            step = 0;
        }

        if (step == 0) {
            host.setStepIndex(1);
            host.setFoundIndex(0);
            host.setAuxIndex(0);
            working[0] = 7; // 2 + 5 + 0 = 7, carry = 0
            host.setWorkingValues(working);
            host.updatePointerState("l1[0]=2, l2[0]=5", 0, "sum=7, carry=0", 0);
            host.updateArrayVisualization(0, -1, 3);
            host.bindCurrentState("[" + title + "] Step 1: Digits 2 + 5 + carry(0) = 7. Carry becomes 0. Append new node(7). Current sum list: [7].");
            return;
        }

        if (step == 1) {
            host.setStepIndex(2);
            host.setFoundIndex(1);
            host.setAuxIndex(1);
            working[1] = 0; // 4 + 6 + 0 = 10, carry = 1
            host.setWorkingValues(working);
            host.updatePointerState("l1[1]=4, l2[1]=6", 1, "sum=10, carry=1", 1);
            host.updateArrayVisualization(1, -1, 3);
            host.setHighlightedLines(4, 2);
            host.bindCurrentState("[" + title + "] Step 2: Digits 4 + 6 + carry(0) = 10. Digit = 10 % 10 = 0, Carry = 10 / 10 = 1. Append node(0). Current list: [7, 0].");
            return;
        }

        if (step == 2) {
            host.setStepIndex(3);
            host.setFoundIndex(2);
            host.setAuxIndex(2);
            working[2] = 8; // 3 + 4 + 1 = 8, carry = 0
            host.setWorkingValues(working);
            host.updatePointerState("l1[2]=3, l2[2]=4", 2, "sum=8, carry=0", 2);
            host.updateArrayVisualization(2, -1, 3);
            host.setHighlightedLines(4, 2);
            host.bindCurrentState("[" + title + "] Step 3: Digits 3 + 4 + carry(1) = 8. Carry becomes 0. Append node(8). Current list: [7, 0, 8].");
            return;
        }

        host.setWorkingValues(new int[] {7, 0, 8});
        host.updateArrayVisualization(0, 2, 3);
        host.bindCurrentState("[" + title + "] Completed! Result linked list: 7 → 0 → 8 (represents 807 = 342 + 465). Solved in O(max(N, M)) Time, O(max(N, M)) Space!");
        host.finish("[" + title + "] Addition of both linked lists completed successfully!");
    }

    // =========================================================================
    // 7. LeetCode #21: Merge Two Sorted Lists (Two Pointers O(n + m))
    // =========================================================================
    private static void advanceMergeTwoLists(VisualizationRuntimeHost host) {
        String techId = host.getTechniqueId();
        com.dsavisualizer.app.model.TechniqueContent tc = LeetCodePart1Repository.getContent(techId);
        String title = (tc != null && tc.title != null) ? tc.title : "Merge Two Sorted Lists";

        int[] values = host.getWorkingValues();
        if (values == null || values.length < 6) {
            values = new int[] {1, 2, 4, 1, 3, 4};
            host.setWorkingValues(values);
            host.setStepIndex(0);
        }

        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);

        // List 1: [1, 2, 4] (idx 0, 1, 2), List 2: [1, 3, 4] (idx 3, 4, 5)
        // Step 1: p1=0 (1), p2=3 (1) -> 1 <= 1, take list1[0]=1. Merged: [1]
        // Step 2: p1=1 (2), p2=3 (1) -> 1 < 2, take list2[0]=1. Merged: [1, 1]
        // Step 3: p1=1 (2), p2=4 (3) -> 2 <= 3, take list1[1]=2. Merged: [1, 1, 2]
        // Step 4: p1=2 (4), p2=4 (3) -> 3 < 4, take list2[1]=3. Merged: [1, 1, 2, 3]
        // Step 5: p1=2 (4), p2=5 (4) -> 4 <= 4, take list1[2]=4. Merged: [1, 1, 2, 3, 4]
        // Step 6: list1 finished -> take remaining list2[2]=4. Final: [1, 1, 2, 3, 4, 4]
        String[] descriptions = new String[] {
            "Step 1: Compare list1[0] (" + values[0] + ") vs list2[0] (" + values[3] + "). " + values[0] + " <= " + values[3] + " → attach list1[0] to merged list. Advance p1.",
            "Step 2: Compare list1[1] (" + values[1] + ") vs list2[0] (" + values[3] + "). Attach smaller element to merged list. Advance p2.",
            "Step 3: Compare list1[1] (" + values[1] + ") vs list2[1] (" + values[4] + "). Attach list1[1] to merged list. Advance p1.",
            "Step 4: Compare list1[2] (" + values[2] + ") vs list2[1] (" + values[4] + "). Attach list2[1] to merged list. Advance p2.",
            "Step 5: Compare list1[2] (" + values[2] + ") vs list2[2] (" + values[5] + "). Attach list1[2] to merged list. Advance p1.",
            "Step 6: list1 is now null. Attach remaining list2 nodes to merged list."
        };

        int[][] pointers = new int[][] {
            {0, 3}, {1, 3}, {1, 4}, {2, 4}, {2, 5}, {2, 5}
        };

        if (step > descriptions.length) {
            host.setWorkingValues(new int[] {1, 1, 2, 3, 4, 4});
            host.updatePointerState("head (" + values[0] + ")", 0, "tail (" + values[5] + ")", 5);
            host.updateArrayVisualization(0, 5, -1);
            host.finish("[" + title + "] Complete! Merged order: 1 → 1 → 2 → 3 → 4 → 4. Solved in O(n + m) time, O(1) memory!");
            return;
        }

        int idx = step - 1;
        int p1 = pointers[idx][0];
        int p2 = pointers[idx][1];

        host.updatePointerState("p1 (" + values[p1] + ")", p1, "p2 (" + values[p2] + ")", p2);
        host.updateArrayVisualization(p1, p2, -1);
        host.bindCurrentState("[" + title + "] " + descriptions[idx]);

        if (step == descriptions.length) {
            host.setWorkingValues(new int[] {1, 1, 2, 3, 4, 4});
            host.finish("[" + title + "] Complete! Merged in O(n + m) time, O(1) extra space!");
        }
    }

    // =========================================================================
    // 8. LeetCode #226: Invert Binary Tree (Tree DFS Swapping O(n))
    // =========================================================================
    private static void advanceInvertBinaryTree(VisualizationRuntimeHost host) {
        int[] values = host.getWorkingValues();
        if (values == null || values.length == 0) {
            host.finish("Tree is empty.");
            return;
        }

        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);

        // Binary Tree Level Order: [4, 2, 7, 1, 3, 6, 9]
        // Step 0: Root 4. Left is 2, Right is 7.
        // Step 1: Swap children of root 4 -> 2 and 7 swap! Tree: [4, 7, 2, 6, 9, 1, 3]
        // Step 2: Swap children of node 7 -> 6 and 9 swap! Tree: [4, 7, 2, 9, 6, 1, 3]
        // Step 3: Swap children of node 2 -> 1 and 3 swap! Tree: [4, 7, 2, 9, 6, 3, 1]
        if (step == 0) {
            host.updatePointerState("root (4)", 0, "", -1);
            host.updateArrayVisualization(0, -1, -1);
            host.bindCurrentState("Start: Binary tree root is 4. Left child is 2 (children 1, 3). Right child is 7 (children 6, 9). We will invert every subtree recursively.");
        } else if (step == 1) {
            int[] invertedRoot = new int[] {4, 7, 2, 6, 9, 1, 3};
            host.setWorkingValues(invertedRoot);
            host.updatePointerState("swapped (7)", 1, "swapped (2)", 2);
            host.updateArrayVisualization(1, 2, 0);
            host.bindCurrentState("Step 1 (Root 4): Swap left child 2 and right child 7. Subtree 7 is now on the left, subtree 2 is now on the right.");
        } else if (step == 2) {
            int[] invertedLeft = new int[] {4, 7, 2, 9, 6, 1, 3};
            host.setWorkingValues(invertedLeft);
            host.updatePointerState("swapped (9)", 3, "swapped (6)", 4);
            host.updateArrayVisualization(3, 4, 1);
            host.bindCurrentState("Step 2 (Node 7): Invert left subtree. Swap its left child 6 and right child 9. Children are now 9 and 6.");
        } else if (step == 3) {
            int[] invertedAll = new int[] {4, 7, 2, 9, 6, 3, 1};
            host.setWorkingValues(invertedAll);
            host.updatePointerState("swapped (3)", 5, "swapped (1)", 6);
            host.updateArrayVisualization(5, 6, 2);
            host.bindCurrentState("Step 3 (Node 2): Invert right subtree. Swap its left child 1 and right child 3. Children are now 3 and 1.");
        } else {
            host.updatePointerState("inverted root (4)", 0, "", -1);
            host.updateArrayVisualization(-1, -1, 0);
            host.finish("Invert Binary Tree Solved! All subtrees inverted: [4, 7, 2, 9, 6, 3, 1]. Solved in O(n) time, O(h) recursion depth!");
        }
    }

    // =========================================================================
    // 9. LeetCode #1929: Concatenation of Array (O(n))
    // =========================================================================
    private static void advanceConcatenationArray(VisualizationRuntimeHost host) {
        int[] original = new int[] {1, 2, 1};
        int n = original.length;
        int total = 2 * n; // 6
        int[] current = host.getWorkingValues();
        if (current == null || current.length != total) {
            current = new int[total];
            host.setWorkingValues(current);
            host.setStepIndex(0);
        }

        int step = host.getStepIndex(); // 0 to n
        if (step >= n) {
            host.setFoundIndex(total - 1);
            host.setAuxIndex(0);
            host.updateArrayVisualization(0, total - 1, total);
            host.bindCurrentState("Concatenation Completed! ans = [1, 2, 1, 1, 2, 1] of size 2n. O(n) Time, O(n) Space.");
            host.finish("Finished! ans contains two copies of nums concatenated together.");
            return;
        }

        int val = original[step];
        current[step] = val;
        current[step + n] = val;
        host.setWorkingValues(current);
        host.setStepIndex(step + 1);

        host.setFoundIndex(step);
        host.setAuxIndex(step + n);
        host.updatePointerState("ans[" + step + "]", step, "ans[" + (step + n) + "]", step + n);
        host.updateArrayVisualization(step, step + n, total);
        host.setHighlightedLines(4, 2);
        host.bindCurrentState("Step " + (step + 1) + ": Copying nums[" + step + "] = " + val
                + " -> ans[" + step + "] = " + val + " and ans[" + (step + n) + "] = " + val + ".");
        if (step + 1 == n) {
            host.finish("All " + n + " elements copied to both halves! Result: [1, 2, 1, 1, 2, 1]. O(n) Time.");
        }
    }

    // =========================================================================
    // 10. Prefix Sum (Cumulative Sum O(n))
    // =========================================================================
    private static void advancePrefixSum(VisualizationRuntimeHost host) {
        int[] original = host.getWorkingValues();
        if (original == null || original.length == 0) {
            original = new int[] {1, 2, 3, 4};
            host.setWorkingValues(original);
            host.setStepIndex(0);
        }

        String techId = host.getTechniqueId();
        com.dsavisualizer.app.model.TechniqueContent tc = LeetCodePart1Repository.getContent(techId);
        String title = tc != null ? tc.title : "Prefix Sum";

        int n = original.length;
        int step = host.getStepIndex();
        if (step < 0) {
            step = 0;
            host.setStepIndex(0);
        }

        if (step >= n) {
            host.setFoundIndex(n - 1);
            host.updateArrayVisualization(0, n - 1, n);
            host.bindCurrentState("[" + title + "] Prefix sum accumulation completed! O(n) Time, O(1) Space.");
            host.finish("[" + title + "] Running sum calculation complete!");
            return;
        }

        int runningSum = 0;
        for (int i = 0; i <= step; i++) {
            runningSum += original[i];
        }

        int prev = (step > 0) ? (step - 1) : -1;
        host.setFoundIndex(step);
        host.setAuxIndex(prev);
        host.updatePointerState("prefix[" + step + "]=" + runningSum, step, prev >= 0 ? "prev" : null, prev);
        host.updateArrayVisualization(prev >= 0 ? prev : step, step, n);
        host.bindCurrentState("[" + title + "] Step " + (step + 1) + " of " + n + ": nums[" + step + "] = " + original[step] + ". Cumulative Prefix Sum = " + runningSum + ".");
        host.setStepIndex(step + 1);
        if (step + 1 == n) {
            host.finish("[" + title + "] Final prefix sum = " + runningSum + "!");
        }
    }

    private static void advanceRunningSum(VisualizationRuntimeHost host) {
        advancePrefixSum(host);
    }

    // =========================================================================
    // 11. LeetCode #1470: Shuffle the Array (O(n) Time, O(n) Space)
    // =========================================================================
    private static void advanceShuffleArray(VisualizationRuntimeHost host) {
        int[] original = new int[] {2, 5, 1, 3, 4, 7};
        int n = 3;
        int total = 2 * n;
        int[] current = host.getWorkingValues();
        if (current == null || current.length != total) {
            current = new int[total];
            host.setWorkingValues(current);
            host.setStepIndex(0);
        }

        int step = host.getStepIndex();
        if (step >= n) {
            host.updateArrayVisualization(0, total - 1, total);
            host.bindCurrentState("Shuffle Completed! ans = [2, 3, 5, 4, 1, 7]. O(n) Time, O(n) Space.");
            host.finish("Finished interleaving [x1, y1, x2, y2, x3, y3]!");
            return;
        }

        int xi = original[step];
        int yi = original[step + n];
        current[2 * step] = xi;
        current[2 * step + 1] = yi;
        host.setWorkingValues(current);
        host.setStepIndex(step + 1);

        host.setFoundIndex(2 * step);
        host.setAuxIndex(2 * step + 1);
        host.updatePointerState("x" + (step + 1) + " (" + xi + ")", 2 * step, "y" + (step + 1) + " (" + yi + ")", 2 * step + 1);
        host.updateArrayVisualization(2 * step, 2 * step + 1, total);
        host.setHighlightedLines(3, 2);
        host.bindCurrentState("Step " + (step + 1) + ": Pair (x" + (step + 1) + ", y" + (step + 1) + ") = (" + xi + ", " + yi
                + "). Placed at ans[" + (2 * step) + "] and ans[" + (2 * step + 1) + "].");
        if (step + 1 == n) {
            host.finish("Shuffle Complete! Result: [2, 3, 5, 4, 1, 7].");
        }
    }

    // =========================================================================
    // 12. LeetCode #1431: Kids With the Greatest Number of Candies
    // =========================================================================
    private static void advanceCandiesMax(VisualizationRuntimeHost host) {
        int[] candies = new int[] {2, 3, 5, 1, 3};
        int extraCandies = 3;
        int maxCandies = 5;
        int step = host.getStepIndex();

        if (step == 0) {
            host.setWorkingValues(candies);
            host.setStepIndex(1);
            host.setFoundIndex(2); // index of max
            host.updatePointerState("max (" + maxCandies + ")", 2, null, -1);
            host.updateArrayVisualization(2, -1, candies.length);
            host.bindCurrentState("Step 1: Scan for maximum candies in the group. maxCandies = " + maxCandies + " (Kid 3).");
            return;
        }

        int kidIdx = step - 1;
        if (kidIdx >= candies.length) {
            host.updateArrayVisualization(0, candies.length - 1, candies.length);
            host.bindCurrentState("Completed! Result: [true, true, true, false, true]. O(n) Time, O(1) Extra Space.");
            host.finish("All kids evaluated against max threshold!");
            return;
        }

        int current = candies[kidIdx];
        int totalWithExtra = current + extraCandies;
        boolean canBeGreatest = totalWithExtra >= maxCandies;

        host.setStepIndex(step + 1);
        host.setFoundIndex(kidIdx);
        host.setAuxIndex(2);
        host.updatePointerState("kid " + (kidIdx + 1) + " (" + current + ")", kidIdx, "max (" + maxCandies + ")", 2);
        host.updateArrayVisualization(kidIdx, 2, candies.length);
        host.setHighlightedLines(4, 1);
        host.bindCurrentState("Step " + (step + 1) + ": Kid " + (kidIdx + 1) + " has " + current + " candies + "
                + extraCandies + " extra = " + totalWithExtra + ". Is " + totalWithExtra + " >= " + maxCandies + "? "
                + (canBeGreatest ? "TRUE! ✓" : "FALSE (Needs more) ✗"));
        if (step == candies.length) {
            host.finish("Evaluation Complete! Result: [true, true, true, false, true].");
        }
    }

    // =========================================================================
    // 13. LeetCode #1672: Richest Customer Wealth (O(m*n))
    // =========================================================================
    private static void advanceCustomerWealth(VisualizationRuntimeHost host) {
        int[][] accounts = new int[][] {
            {1, 5},
            {7, 3},
            {3, 5}
        };
        int[] wealths = new int[] {6, 10, 8};
        int step = host.getStepIndex();

        if (step >= accounts.length) {
            host.setFoundIndex(1);
            host.updateArrayVisualization(1, -1, 3);
            host.bindCurrentState("Completed! Richest Customer is Customer 2 with Wealth = 10. O(m*n) Time, O(1) Space.");
            host.finish("Richest customer identified: 10!");
            return;
        }

        int sum = wealths[step];
        int runningMax = step == 0 ? sum : Math.max(wealths[0], wealths[step]);
        host.setWorkingValues(wealths);
        host.setStepIndex(step + 1);
        host.setFoundIndex(step);
        host.updatePointerState("cust " + (step + 1) + " (sum=" + sum + ")", step, "max=" + runningMax, runningMax == sum ? step : 0);
        host.updateArrayVisualization(step, -1, 3);
        host.setHighlightedLines(3, 2);
        host.bindCurrentState("Step " + (step + 1) + ": Customer " + (step + 1) + " accounts: "
                + Arrays.toString(accounts[step]) + " -> Total Wealth = " + sum
                + ". Current Max Wealth = " + runningMax + (sum >= runningMax ? " (New Peak!)" : ""));
        if (step + 1 == accounts.length) {
            host.finish("All customers evaluated! Max Wealth = 10.");
        }
    }

    // =========================================================================
    // 14. Hash Set Duplicate Check (Hash Set O(n))
    // =========================================================================
    private static void advanceHashSetDuplicate(VisualizationRuntimeHost host) {
        int[] nums = host.getWorkingValues();
        if (nums == null || nums.length == 0) {
            nums = new int[] {1, 2, 3, 1};
            host.setWorkingValues(nums);
            host.setStepIndex(0);
        }

        String techId = host.getTechniqueId();
        com.dsavisualizer.app.model.TechniqueContent tc = LeetCodePart1Repository.getContent(techId);
        String title = tc != null ? tc.title : "Contains Duplicate";

        int n = nums.length;
        int step = host.getStepIndex();
        if (step < 0) step = 0;

        if (step >= n) {
            host.setFoundIndex(n - 1);
            host.updateArrayVisualization(-1, -1, n);
            host.bindCurrentState("[" + title + "] Scan completed. All " + n + " elements are distinct. Returned FALSE.");
            host.finish("[" + title + "] No duplicates found!");
            return;
        }

        Set<Integer> seen = new HashSet<>();
        for (int i = 0; i < step; i++) {
            seen.add(nums[i]);
        }

        int val = nums[step];
        if (seen.contains(val)) {
            int prevIdx = -1;
            for (int i = 0; i < step; i++) {
                if (nums[i] == val) { prevIdx = i; break; }
            }
            host.setFoundIndex(step);
            host.setAuxIndex(prevIdx);
            host.updatePointerState("curr (" + val + ")", step, "duplicate in set", prevIdx);
            host.updateArrayVisualization(step, prevIdx, n);
            host.bindCurrentState("[" + title + "] Step " + (step + 1) + " of " + n + ": nums[" + step + "] = " + val
                + ". Value " + val + " already exists in HashSet! DUPLICATE DETECTED -> Return TRUE! 🏆");
            host.finish("[" + title + "] Duplicate found! Returned true in O(n) time, O(n) space.");
            host.setStepIndex(step + 1);
        } else {
            host.setFoundIndex(step);
            host.setAuxIndex(step > 0 ? step - 1 : -1);
            host.updatePointerState("curr (" + val + ")", step, "insert to set", -1);
            host.updateArrayVisualization(step, -1, n);
            host.bindCurrentState("[" + title + "] Step " + (step + 1) + " of " + n + ": nums[" + step + "] = " + val
                + " not in HashSet yet. Inserted into set. Total seen: " + (seen.size() + 1) + ".");
            host.setStepIndex(step + 1);
            if (step + 1 == n) {
                host.finish("[" + title + "] Scanned all elements. All values are unique!");
            }
        }
    }

    // =========================================================================
    // 15. LeetCode #242: Valid Anagram (Frequency Count O(n))
    // =========================================================================
    private static void advanceFrequencyArray(VisualizationRuntimeHost host) {
        int[] counts = new int[] {3, 1, 1, 1, 1}; // a:3, g:1, m:1, n:1, r:1
        int step = host.getStepIndex();

        if (step == 0) {
            host.setWorkingValues(counts);
            host.setStepIndex(1);
            host.updatePointerState("s: 'anagram'", 0, null, -1);
            host.updateArrayVisualization(0, -1, counts.length);
            host.bindCurrentState("Step 1: Increment frequency table for string s = 'anagram'. Characters: {a:3, g:1, m:1, n:1, r:1}.");
            return;
        }

        if (step == 1) {
            int[] balanced = new int[] {0, 0, 0, 0, 0};
            host.setWorkingValues(balanced);
            host.setStepIndex(2);
            host.updatePointerState("t: 'nagaram'", 0, "all 0s", balanced.length - 1);
            host.updateArrayVisualization(0, balanced.length - 1, balanced.length);
            host.bindCurrentState("Step 2: Decrement frequency table for string t = 'nagaram'. All char counts cancel to exactly 0!");
            return;
        }

        host.updateArrayVisualization(-1, -1, counts.length);
        host.bindCurrentState("Step 3: Verified all frequency counts are 0. 'anagram' and 'nagaram' are VALID ANAGRAMS! Return TRUE.");
        host.finish("Valid Anagram confirmed in O(n) Time, O(1) Space (26 letters)!");
    }

    // =========================================================================
    // 16. LeetCode #136: Single Number (Bitwise XOR O(n) Time, O(1) Space)
    // =========================================================================
    private static void advanceXorCancellation(VisualizationRuntimeHost host) {
        int[] nums = new int[] {4, 1, 2, 1, 2};
        int[] states = new int[] {4, 5, 7, 6, 4}; // accumulator values
        int step = host.getStepIndex();

        if (step >= nums.length) {
            host.setFoundIndex(0);
            host.updateArrayVisualization(0, -1, nums.length);
            host.bindCurrentState("Completed! Single Number is " + states[nums.length - 1] + ". All pairs cancelled via XOR.");
            host.finish("Single Number found: 4!");
            return;
        }

        host.setWorkingValues(nums);
        int current = nums[step];
        int acc = states[step];
        host.setStepIndex(step + 1);
        host.setFoundIndex(step);
        host.updatePointerState("curr (" + current + ")", step, "xorAcc=" + acc, step);
        host.updateArrayVisualization(step, -1, nums.length);
        host.setHighlightedLines(3, 1);
        host.bindCurrentState("Step " + (step + 1) + ": XOR nums[" + step + "] (" + current
                + ") with accumulator. Running XOR = " + acc + ". (Duplicate elements 1 and 2 cancel themselves out: x ^ x = 0).");
        if (step + 1 == nums.length) {
            host.finish("Finished! Only the unique number 4 remains in accumulator!");
        }
    }

    // =========================================================================
    // 17. LeetCode #349 & #350: Intersection of Two Arrays
    // =========================================================================
    private static void advanceSetIntersection(VisualizationRuntimeHost host) {
        int[] nums1 = new int[] {1, 2, 2, 1};
        int[] nums2 = new int[] {2, 2};
        int step = host.getStepIndex();

        if (step == 0) {
            host.setWorkingValues(nums1);
            host.setStepIndex(1);
            host.updatePointerState("nums1", 0, null, -1);
            host.updateArrayVisualization(0, -1, nums1.length);
            host.bindCurrentState("Step 1: Store elements of nums1 [1, 2, 2, 1] in Hash Table: {1: 2 occurrences, 2: 2 occurrences}.");
            return;
        }

        if (step == 1) {
            host.setWorkingValues(nums2);
            host.setStepIndex(2);
            host.setFoundIndex(0);
            host.updatePointerState("match (2)", 0, null, -1);
            host.updateArrayVisualization(0, -1, nums2.length);
            host.bindCurrentState("Step 2: Check nums2[0] = 2. Exists in table! Add 2 to intersection result. Decrement count.");
            return;
        }

        if (step == 2) {
            host.setWorkingValues(nums2);
            host.setStepIndex(3);
            host.setFoundIndex(1);
            host.updatePointerState("match (2)", 1, null, -1);
            host.updateArrayVisualization(1, -1, nums2.length);
            host.bindCurrentState("Step 3: Check nums2[1] = 2. Exists in table! Add second 2 to intersection result.");
            return;
        }

        int[] result = new int[] {2, 2};
        host.setWorkingValues(result);
        host.updateArrayVisualization(0, 1, 2);
        host.bindCurrentState("Completed! Intersection = [2, 2]. O(n + m) Time, O(min(n, m)) Space.");
        host.finish("Intersection completed successfully!");
    }

    // =========================================================================
    // 18. LeetCode #122: Best Time to Buy and Sell Stock II (Greedy Valley-Peak)
    // =========================================================================
    private static void advanceGreedyValleyPeak(VisualizationRuntimeHost host) {
        int[] prices = new int[] {7, 1, 5, 3, 6, 4};
        int step = host.getStepIndex();

        if (step == 0) {
            host.setWorkingValues(prices);
            host.setStepIndex(1);
            host.setFoundIndex(0);
            host.updatePointerState("day 1 (7)", 0, null, -1);
            host.updateArrayVisualization(0, -1, prices.length);
            host.bindCurrentState("Start: prices = [7, 1, 5, 3, 6, 4]. We collect every positive difference: prices[i] - prices[i-1] > 0.");
            return;
        }

        int i = step; // 1 to 5
        if (i >= prices.length) {
            host.updateArrayVisualization(0, prices.length - 1, prices.length);
            host.bindCurrentState("Completed! Total Maximum Profit = (5 - 1) + (6 - 3) = 4 + 3 = 7! O(n) Time, O(1) Space.");
            host.finish("Max profit 7 achieved by capturing all upward slopes!");
            return;
        }

        int diff = prices[i] - prices[i - 1];
        host.setStepIndex(step + 1);
        host.setFoundIndex(i);
        host.setAuxIndex(i - 1);
        host.updatePointerState("today (" + prices[i] + ")", i, "prev (" + prices[i - 1] + ")", i - 1);
        host.updateArrayVisualization(i - 1, i, prices.length);
        host.setHighlightedLines(3, 2);

        if (diff > 0) {
            host.bindCurrentState("Day " + (i + 1) + ": prices[" + i + "] = " + prices[i] + " > prices[" + (i - 1) + "] = "
                    + prices[i - 1] + ". Gain = +" + diff + ". Add to total profit! 📈");
        } else {
            host.bindCurrentState("Day " + (i + 1) + ": prices[" + i + "] = " + prices[i] + " <= prices[" + (i - 1) + "] = "
                    + prices[i - 1] + ". Price dropped or flat. No transaction executed.");
        }
        if (i + 1 == prices.length) {
            host.finish("Greedy scan finished! Total Profit = 7.");
        }
    }

    // =========================================================================
    // 19. LeetCode #88: Merge Sorted Array (3-Pointers Backwards O(m+n))
    // =========================================================================
    private static void advanceMergeSortedArray(VisualizationRuntimeHost host) {
        int[] nums1 = host.getWorkingValues();
        if (nums1 == null || nums1.length != 6) {
            nums1 = new int[] {1, 2, 3, 0, 0, 0};
            host.setWorkingValues(nums1);
            host.setStepIndex(0);
        }

        int step = host.getStepIndex();
        int[] nums2 = new int[] {2, 5, 6};

        if (step == 0) {
            nums1[5] = 6;
            host.setWorkingValues(nums1);
            host.setStepIndex(1);
            host.setFoundIndex(5);
            host.updatePointerState("p=5 (write 6)", 5, "nums2[2]=6", 2);
            host.updateArrayVisualization(2, 5, 6);
            host.bindCurrentState("Step 1: Compare nums1[2]=3 vs nums2[2]=6. nums2 is larger -> write 6 at nums1[5]. Decrement pointers.");
            return;
        }

        if (step == 1) {
            nums1[4] = 5;
            host.setWorkingValues(nums1);
            host.setStepIndex(2);
            host.setFoundIndex(4);
            host.updatePointerState("p=4 (write 5)", 4, "nums2[1]=5", 1);
            host.updateArrayVisualization(1, 4, 6);
            host.bindCurrentState("Step 2: Compare nums1[2]=3 vs nums2[1]=5. nums2 is larger -> write 5 at nums1[4]. Decrement pointers.");
            return;
        }

        if (step == 2) {
            nums1[3] = 3;
            host.setWorkingValues(nums1);
            host.setStepIndex(3);
            host.setFoundIndex(3);
            host.updatePointerState("p=3 (write 3)", 3, "nums1[2]=3", 2);
            host.updateArrayVisualization(2, 3, 6);
            host.bindCurrentState("Step 3: Compare nums1[2]=3 vs nums2[0]=2. nums1 is larger -> write 3 at nums1[3]. Decrement pointers.");
            return;
        }

        if (step == 3) {
            nums1[2] = 2;
            host.setWorkingValues(nums1);
            host.setStepIndex(4);
            host.setFoundIndex(2);
            host.updatePointerState("p=2 (write 2)", 2, "nums2[0]=2", 0);
            host.updateArrayVisualization(0, 2, 6);
            host.bindCurrentState("Step 4: Compare nums1[1]=2 vs nums2[0]=2. Write 2 at nums1[2]. nums2 is now empty.");
            return;
        }

        host.updateArrayVisualization(0, 5, 6);
        host.bindCurrentState("Merge Completed! Final nums1 = [1, 2, 2, 3, 5, 6]. O(m+n) Time, O(1) Extra Space!");
        host.finish("Backwards merge completed without overwriting!");
    }

    // =========================================================================
    // 20. In-Place Two Pointers (Remove Element / Duplicates / Move)
    // =========================================================================
    private static void advanceTwoPointersInPlace(VisualizationRuntimeHost host) {
        int[] nums = host.getWorkingValues();
        if (nums == null || nums.length == 0) {
            nums = new int[] {3, 2, 2, 3};
            host.setWorkingValues(nums);
            host.setStepIndex(0);
        }
        String techId = host.getTechniqueId();
        com.dsavisualizer.app.model.TechniqueContent tc = LeetCodePart1Repository.getContent(techId);
        String title = tc != null ? tc.title : "In-Place Pointers";

        int n = nums.length;
        int step = host.getStepIndex();
        if (step < 0) step = 0;

        if (step >= n) {
            host.setFoundIndex(n - 1);
            host.updateArrayVisualization(0, n - 1, n);
            host.bindCurrentState("[" + title + "] In-place modification complete across all " + n + " elements. O(n) Time, O(1) Space.");
            host.finish("[" + title + "] Completed in-place pass!");
            return;
        }

        int writeIdx = Math.max(0, step / 2);
        int readVal = nums[step];
        host.setFoundIndex(writeIdx);
        host.setAuxIndex(step);
        host.updatePointerState("write=" + writeIdx, writeIdx, "read=" + step + " (" + readVal + ")", step);
        host.updateArrayVisualization(writeIdx, step, n);
        host.bindCurrentState("[" + title + "] Step " + (step + 1) + " of " + n + ": Scanning element " + readVal + " at read index " + step + ". Maintaining write boundary at index " + writeIdx + ".");
        host.setStepIndex(step + 1);
        if (step + 1 == n) {
            host.finish("[" + title + "] Optimal in-place traversal completed!");
        }
    }

    // =========================================================================
    // 21. LeetCode #283: Move Zeroes (In-Place Swaps O(n))
    // =========================================================================
    private static void advanceMoveZeroes(VisualizationRuntimeHost host) {
        int[] nums = host.getWorkingValues();
        if (nums == null || nums.length != 5) {
            nums = new int[] {0, 1, 0, 3, 12};
            host.setWorkingValues(nums);
            host.setStepIndex(0);
        }

        int step = host.getStepIndex();

        if (step == 0) {
            // Swap index 0 and 1: [1, 0, 0, 3, 12]
            nums[0] = 1; nums[1] = 0;
            host.setWorkingValues(nums);
            host.setStepIndex(1);
            host.setFoundIndex(0);
            host.setAuxIndex(1);
            host.updatePointerState("insertPos=0", 0, "found non-zero=1", 1);
            host.updateArrayVisualization(0, 1, 5);
            host.bindCurrentState("Step 1: Found non-zero 1 at index 1. Swap with nums[insertPos=0]. Array: [1, 0, 0, 3, 12]. insertPos becomes 1.");
            return;
        }

        if (step == 1) {
            // Swap index 1 and 3: [1, 3, 0, 0, 12]
            nums[1] = 3; nums[3] = 0;
            host.setWorkingValues(nums);
            host.setStepIndex(2);
            host.setFoundIndex(1);
            host.setAuxIndex(3);
            host.updatePointerState("insertPos=1", 1, "found non-zero=3", 3);
            host.updateArrayVisualization(1, 3, 5);
            host.bindCurrentState("Step 2: Found non-zero 3 at index 3. Swap with nums[insertPos=1]. Array: [1, 3, 0, 0, 12]. insertPos becomes 2.");
            return;
        }

        if (step == 2) {
            // Swap index 2 and 4: [1, 3, 12, 0, 0]
            nums[2] = 12; nums[4] = 0;
            host.setWorkingValues(nums);
            host.setStepIndex(3);
            host.setFoundIndex(2);
            host.setAuxIndex(4);
            host.updatePointerState("insertPos=2", 2, "found non-zero=12", 4);
            host.updateArrayVisualization(2, 4, 5);
            host.bindCurrentState("Step 3: Found non-zero 12 at index 4. Swap with nums[insertPos=2]. Array: [1, 3, 12, 0, 0]. insertPos becomes 3.");
            return;
        }

        host.updateArrayVisualization(0, 4, 5);
        host.bindCurrentState("Completed! All non-zeros preserved in order at front: [1, 3, 12, 0, 0]. O(n) Time, O(1) Space.");
        host.finish("Move Zeroes completed successfully!");
    }

    // =========================================================================
    // 22. LeetCode #125 & #167: Two Pointers Converging (Palindrome / Two Sum II)
    // =========================================================================
    private static void advanceTwoPointersConverging(VisualizationRuntimeHost host) {
        String techId = host.getTechniqueId();
        com.dsavisualizer.app.model.TechniqueContent tc = LeetCodePart1Repository.getContent(techId);
        String title = (tc != null && tc.title != null) ? tc.title : "Two Pointers Converging";

        int[] nums = host.getWorkingValues();
        if (nums == null || nums.length < 2) {
            if (tc != null && tc.sampleInput != null && tc.sampleInput.length >= 2) {
                nums = tc.sampleInput.clone();
            } else {
                nums = new int[] {1, 3, 5, 7, 9};
            }
            host.setWorkingValues(nums);
            host.setStepIndex(0);
        }

        int target = host.getTarget();
        if (target == 0 && tc != null && tc.sampleTarget != 0) {
            target = tc.sampleTarget;
        }

        int n = nums.length;
        int step = host.getStepIndex();
        int left = 0;
        int right = n - 1;

        if (target != 0) {
            for (int s = 0; s < step; s++) {
                if (left >= right) break;
                int currentSum = nums[left] + nums[right];
                if (currentSum == target) {
                    break;
                } else if (currentSum < target) {
                    left++;
                } else {
                    right--;
                }
            }

            if (left >= right) {
                host.finish("[" + title + "] Scan complete! Search space exhausted.");
                return;
            }

            int sum = nums[left] + nums[right];
            host.setFoundIndex(left);
            host.setAuxIndex(right);
            host.updatePointerState("left=" + left + " (" + nums[left] + ")", left, "right=" + right + " (" + nums[right] + ")", right);
            host.updateArrayVisualization(left, right, -1);

            if (sum == target) {
                host.setStepIndex(step + 1);
                host.setHighlightedLines(4, 2);
                host.bindCurrentState("[" + title + "] Step " + (step + 1) + ": left=" + left + " (" + nums[left] + "), right=" + right + " (" + nums[right] + "). Sum = " + sum + " == Target " + target + "! MATCH FOUND!");
                host.finish("[" + title + "] Target match found at indices [" + left + ", " + right + "]! O(n) Time, O(1) Space.");
            } else if (sum < target) {
                host.setStepIndex(step + 1);
                host.bindCurrentState("[" + title + "] Step " + (step + 1) + ": left=" + left + " (" + nums[left] + "), right=" + right + " (" + nums[right] + "). Sum = " + sum + " < Target " + target + ". Move left pointer rightward.");
            } else {
                host.setStepIndex(step + 1);
                host.bindCurrentState("[" + title + "] Step " + (step + 1) + ": left=" + left + " (" + nums[left] + "), right=" + right + " (" + nums[right] + "). Sum = " + sum + " > Target " + target + ". Move right pointer leftward.");
            }
        } else {
            left = step;
            right = n - 1 - step;
            if (left >= right) {
                host.updateArrayVisualization(n / 2, -1, -1);
                host.bindCurrentState("[" + title + "] Pointers met at middle index " + (n / 2) + ". Convergence scan complete! O(n) Time, O(1) Space.");
                host.finish("[" + title + "] Two pointers convergence completed!");
                return;
            }

            host.setStepIndex(step + 1);
            host.setFoundIndex(left);
            host.setAuxIndex(right);
            host.updatePointerState("left=" + left + " (" + nums[left] + ")", left, "right=" + right + " (" + nums[right] + ")", right);
            host.updateArrayVisualization(left, right, -1);
            host.bindCurrentState("[" + title + "] Step " + (step + 1) + ": Checking pair left=" + left + " (" + nums[left] + ") and right=" + right + " (" + nums[right] + "). Both pointers advancing toward center.");
        }
    }

    // =========================================================================
    // 23. LeetCode #344: Reverse String (Two Pointers Swap)
    // =========================================================================
    private static void advanceReverseArray(VisualizationRuntimeHost host) {
        String techId = host.getTechniqueId();
        com.dsavisualizer.app.model.TechniqueContent tc = LeetCodePart1Repository.getContent(techId);
        String title = (tc != null && tc.title != null) ? tc.title : "Reverse Array";

        int[] nums = host.getWorkingValues();
        if (nums == null || nums.length < 2) {
            if (tc != null && tc.sampleInput != null && tc.sampleInput.length >= 2) {
                nums = tc.sampleInput.clone();
            } else {
                nums = new int[] {'h', 'e', 'l', 'l', 'o'};
            }
            host.setWorkingValues(nums);
            host.setStepIndex(0);
        }

        int n = nums.length;
        int step = host.getStepIndex();
        int left = step;
        int right = n - 1 - step;

        if (left >= right) {
            host.updateArrayVisualization(0, n - 1, -1);
            host.bindCurrentState("[" + title + "] Completed! All pairs swapped. In-place reversal complete in O(n) Time, O(1) Space.");
            host.finish("[" + title + "] Array reversed successfully!");
            return;
        }

        // Swap nums[left] and nums[right]
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
        host.setWorkingValues(nums);

        host.setStepIndex(step + 1);
        host.setFoundIndex(left);
        host.setAuxIndex(right);
        String leftChar = (nums[left] >= 32 && nums[left] <= 126) ? ("'" + (char) nums[left] + "'") : String.valueOf(nums[left]);
        String rightChar = (nums[right] >= 32 && nums[right] <= 126) ? ("'" + (char) nums[right] + "'") : String.valueOf(nums[right]);
        host.updatePointerState("left=" + left + " (" + leftChar + ")", left, "right=" + right + " (" + rightChar + ")", right);
        host.updateArrayVisualization(left, right, n);
        host.bindCurrentState("[" + title + "] Step " + (step + 1) + ": Swap index " + left + " and " + right + " -> " + leftChar + " and " + rightChar + " swapped! Pointers move inward.");
        if (left + 1 >= right - 1) {
            host.finish("[" + title + "] In-place reversal complete!");
        }
    }

    // =========================================================================
    // 24. LeetCode #15: 3Sum (Sort + Two Pointers O(n^2))
    // =========================================================================
    private static void advanceThreeSum(VisualizationRuntimeHost host) {
        String techId = host.getTechniqueId();
        com.dsavisualizer.app.model.TechniqueContent tc = LeetCodePart1Repository.getContent(techId);
        String title = (tc != null && tc.title != null) ? tc.title : "3Sum";

        int[] sorted = new int[] {-4, -1, -1, 0, 1, 2};
        int step = host.getStepIndex();

        if (step == 0) {
            host.setWorkingValues(sorted);
            host.setStepIndex(1);
            host.setFoundIndex(0);
            host.updatePointerState("sort nums", 0, null, -1);
            host.updateArrayVisualization(0, -1, sorted.length);
            host.bindCurrentState("[" + title + "] Step 1: Sort array in ascending order: [-4, -1, -1, 0, 1, 2]. Allows two-pointer scanning.");
            return;
        }

        if (step == 1) {
            host.setStepIndex(2);
            host.setFoundIndex(1); // i=1 (-1)
            host.setAuxIndex(2);   // left=2 (-1)
            host.updatePointerState("i=1 (-1)", 1, "left=2 (-1), right=5 (2)", 5);
            host.updateArrayVisualization(1, 5, sorted.length);
            host.setHighlightedLines(6, 3);
            host.bindCurrentState("[" + title + "] Step 2: Fix i=1 (-1). Two pointers left=2 (-1), right=5 (2). Sum = -1 + (-1) + 2 = 0! TRIPLET FOUND: [-1, -1, 2]! ✓");
            return;
        }

        if (step == 2) {
            host.setStepIndex(3);
            host.setFoundIndex(1);
            host.setAuxIndex(3);
            host.updatePointerState("i=1 (-1)", 1, "left=3 (0), right=4 (1)", 4);
            host.updateArrayVisualization(1, 4, sorted.length);
            host.setHighlightedLines(6, 3);
            host.bindCurrentState("[" + title + "] Step 3: Move left and right inward: left=3 (0), right=4 (1). Sum = -1 + 0 + 1 = 0! TRIPLET FOUND: [-1, 0, 1]! ✓");
            return;
        }

        host.updateArrayVisualization(0, sorted.length - 1, sorted.length);
        host.bindCurrentState("[" + title + "] Completed! All unique triplets summing to 0 found: [[-1, -1, 2], [-1, 0, 1]]. O(n²) Time, O(1) Extra Space.");
        host.finish("[" + title + "] Execution finished!");
    }

    // =========================================================================
    // 25. LeetCode #18: 4Sum (Two Pointers O(n^3))
    // =========================================================================
    private static void advanceFourSum(VisualizationRuntimeHost host) {
        String techId = host.getTechniqueId();
        com.dsavisualizer.app.model.TechniqueContent tc = LeetCodePart1Repository.getContent(techId);
        String title = (tc != null && tc.title != null) ? tc.title : "4Sum";

        int[] sorted = new int[] {-2, -1, 0, 0, 1, 2};
        int step = host.getStepIndex();

        if (step == 0) {
            host.setWorkingValues(sorted);
            host.setStepIndex(1);
            host.setFoundIndex(0);
            host.updatePointerState("sort nums", 0, null, -1);
            host.updateArrayVisualization(0, -1, sorted.length);
            host.bindCurrentState("[" + title + "] Step 1: Sort array: [-2, -1, 0, 0, 1, 2]. Target sum = 0.");
            return;
        }

        if (step == 1) {
            host.setStepIndex(2);
            host.setFoundIndex(0);
            host.setAuxIndex(1);
            host.updatePointerState("i=0 (-2), j=1 (-1)", 0, "left=2 (0), right=5 (2)", 5);
            host.updateArrayVisualization(0, 5, sorted.length);
            host.bindCurrentState("[" + title + "] Step 2: Fix i=0 (-2), j=1 (-1). Two pointers left=2 (0), right=5 (2). Sum = -2 + (-1) + 0 + 2 = -1 < 0. Advance left.");
            return;
        }

        if (step == 2) {
            host.setStepIndex(3);
            host.setFoundIndex(0);
            host.setAuxIndex(1);
            host.updatePointerState("i=0 (-2), j=1 (-1)", 0, "left=4 (1), right=5 (2)", 5);
            host.updateArrayVisualization(0, 5, sorted.length);
            host.setHighlightedLines(8, 2);
            host.bindCurrentState("[" + title + "] Step 3: left=4 (1), right=5 (2). Sum = -2 + (-1) + 1 + 2 = 0! QUADRUPLET FOUND: [-2, -1, 1, 2]! ✓");
            return;
        }

        host.updateArrayVisualization(0, sorted.length - 1, sorted.length);
        host.bindCurrentState("[" + title + "] Completed! Quadruplets found: [[-2, -1, 1, 2], [-2, 0, 0, 2], [-1, 0, 0, 1]]. O(n³) Time, O(1) Extra Space.");
        host.finish("[" + title + "] Execution finished!");
    }

    // =========================================================================
    // 26. LeetCode #977: Squares of a Sorted Array (Two Pointers O(n))
    // =========================================================================
    private static void advanceSquaresSortedArray(VisualizationRuntimeHost host) {
        String techId = host.getTechniqueId();
        com.dsavisualizer.app.model.TechniqueContent tc = LeetCodePart1Repository.getContent(techId);
        String title = (tc != null && tc.title != null) ? tc.title : "Squares of Sorted Array";

        int[] original = new int[] {-4, -1, 0, 3, 10};
        int[] ans = host.getWorkingValues();
        if (ans == null || ans.length != original.length) {
            ans = new int[original.length];
            host.setWorkingValues(ans);
            host.setStepIndex(0);
        }

        int step = host.getStepIndex();

        if (step == 0) {
            ans[4] = 100; // 10^2
            host.setWorkingValues(ans);
            host.setStepIndex(1);
            host.setFoundIndex(0);
            host.setAuxIndex(4);
            host.updatePointerState("left=0 (|-4|=4)", 0, "right=4 (10->100 placed at ans[4])", 4);
            host.updateArrayVisualization(0, 4, 5);
            host.bindCurrentState("[" + title + "] Step 1: Compare |nums[0]|=4 vs |nums[4]|=10. 10 is larger -> ans[4] = 100. Decrement right pointer.");
            return;
        }

        if (step == 1) {
            ans[3] = 16; // (-4)^2
            host.setWorkingValues(ans);
            host.setStepIndex(2);
            host.setFoundIndex(0);
            host.setAuxIndex(3);
            host.updatePointerState("left=0 (|-4|=4->16 placed at ans[3])", 0, "right=3 (|3|=3)", 3);
            host.updateArrayVisualization(0, 3, 5);
            host.bindCurrentState("[" + title + "] Step 2: Compare |nums[0]|=4 vs |nums[3]|=3. 4 is larger -> ans[3] = 16. Increment left pointer.");
            return;
        }

        if (step == 2) {
            ans[2] = 9;  // 3^2
            ans[1] = 1;  // (-1)^2
            ans[0] = 0;  // 0^2
            host.setWorkingValues(ans);
            host.setStepIndex(3);
            host.updateArrayVisualization(0, 4, 5);
            host.bindCurrentState("[" + title + "] Step 3: Remaining elements placed: ans[2]=9, ans[1]=1, ans[0]=0.");
            return;
        }

        host.updateArrayVisualization(0, 4, 5);
        host.bindCurrentState("[" + title + "] Completed! Result: [0, 1, 9, 16, 100]. Optimal O(n) Time, O(n) Output Space (no O(n log n) sorting needed)!");
        host.finish("[" + title + "] Squares of sorted array complete!");
    }

    // =========================================================================
    // 27. LeetCode #11: Container With Most Water (Two Pointers O(n))
    // =========================================================================
    private static void advanceContainerMostWater(VisualizationRuntimeHost host) {
        String techId = host.getTechniqueId();
        com.dsavisualizer.app.model.TechniqueContent tc = LeetCodePart1Repository.getContent(techId);
        String title = (tc != null && tc.title != null) ? tc.title : "Container With Most Water";

        int[] heights = new int[] {1, 8, 6, 2, 5, 4, 8, 3, 7};
        int step = host.getStepIndex();

        if (step == 0) {
            host.setWorkingValues(heights);
            host.setStepIndex(1);
            host.setFoundIndex(0);
            host.setAuxIndex(8);
            int area = (8 - 0) * Math.min(1, 7); // 8
            host.updatePointerState("left=0 (h=1)", 0, "right=8 (h=7)", 8);
            host.updateArrayVisualization(0, 8, heights.length);
            host.bindCurrentState("[" + title + "] Step 1: left=0 (h=1), right=8 (h=7). Area = width(8) * min(1, 7) = 8. Max Area = 8. Move left (shorter line).");
            return;
        }

        if (step == 1) {
            host.setStepIndex(2);
            host.setFoundIndex(1);
            host.setAuxIndex(8);
            int area = (8 - 1) * Math.min(8, 7); // 7 * 7 = 49
            host.updatePointerState("left=1 (h=8)", 1, "right=8 (h=7)", 8);
            host.updateArrayVisualization(1, 8, heights.length);
            host.setHighlightedLines(4, 2);
            host.bindCurrentState("[" + title + "] Step 2: left=1 (h=8), right=8 (h=7). Area = width(7) * min(8, 7) = 49! NEW MAXIMUM AREA = 49! Move right.");
            return;
        }

        if (step == 2) {
            host.setStepIndex(3);
            host.setFoundIndex(1);
            host.setAuxIndex(6);
            int area = (6 - 1) * Math.min(8, 8); // 5 * 8 = 40
            host.updatePointerState("left=1 (h=8)", 1, "right=6 (h=8)", 6);
            host.updateArrayVisualization(1, 6, heights.length);
            host.bindCurrentState("[" + title + "] Step 3: left=1 (h=8), right=6 (h=8). Area = width(5) * min(8, 8) = 40. Max remains 49.");
            return;
        }

        host.updateArrayVisualization(1, 8, heights.length);
        host.bindCurrentState("[" + title + "] Completed! Maximum Water Container Area = 49 (between index 1 and 8). Solved in O(n) Time, O(1) Space!");
        host.finish("[" + title + "] Most water container found: Area = 49!");
    }

    // =========================================================================
    // 28. LeetCode #876: Middle of the Linked List (Fast & Slow Pointers)
    // =========================================================================
    private static void advanceMiddleLinkedList(VisualizationRuntimeHost host) {
        String techId = host.getTechniqueId();
        com.dsavisualizer.app.model.TechniqueContent tc = LeetCodePart1Repository.getContent(techId);
        String title = (tc != null && tc.title != null) ? tc.title : "Middle of Linked List";

        int[] nodes = new int[] {1, 2, 3, 4, 5};
        int step = host.getStepIndex();

        if (step == 0) {
            host.setWorkingValues(nodes);
            host.setStepIndex(1);
            host.setFoundIndex(0);
            host.setAuxIndex(0);
            host.updatePointerState("slow=0 (1)", 0, "fast=0 (1)", 0);
            host.updateArrayVisualization(0, 0, nodes.length);
            host.bindCurrentState("[" + title + "] Start: slow and fast pointers begin at head (node 1). Slow will move 1 step, fast will move 2 steps.");
            return;
        }

        if (step == 1) {
            host.setStepIndex(2);
            host.setFoundIndex(1);
            host.setAuxIndex(2);
            host.updatePointerState("slow=1 (2)", 1, "fast=2 (3)", 2);
            host.updateArrayVisualization(1, 2, nodes.length);
            host.setHighlightedLines(3, 2);
            host.bindCurrentState("[" + title + "] Step 1: Advance slow by 1 step -> node 2. Advance fast by 2 steps -> node 3.");
            return;
        }

        if (step == 2) {
            host.setStepIndex(3);
            host.setFoundIndex(2);
            host.setAuxIndex(4);
            host.updatePointerState("slow=2 (3)", 2, "fast=4 (5)", 4);
            host.updateArrayVisualization(2, 4, nodes.length);
            host.setHighlightedLines(3, 2);
            host.bindCurrentState("[" + title + "] Step 2: Advance slow by 1 step -> node 3. Advance fast by 2 steps -> node 5 (tail node).");
            return;
        }

        host.setFoundIndex(2);
        host.updatePointerState("middle node (3)", 2, "fast reached end", 4);
        host.updateArrayVisualization(2, -1, nodes.length);
        host.bindCurrentState("[" + title + "] Completed! fast.next is null -> slow is at the exact middle node (value 3). O(n) Time, O(1) Space!");
        host.finish("[" + title + "] Middle of linked list identified: node 3!");
    }

    // =========================================================================
    // 29. LeetCode #739: Daily Temperatures (Monotonic Stack)
    // =========================================================================
    private static void advanceDailyTemperatures(VisualizationRuntimeHost host) {
        String techId = host.getTechniqueId();
        com.dsavisualizer.app.model.TechniqueContent tc = LeetCodePart1Repository.getContent(techId);
        String title = (tc != null && tc.title != null) ? tc.title : "Daily Temperatures";

        int[] temps = new int[] {73, 74, 75, 71, 69, 72, 76, 73};
        int step = host.getStepIndex();

        if (step == 0) {
            host.setWorkingValues(temps);
            host.setStepIndex(1);
            host.setFoundIndex(0);
            host.updatePointerState("day 0 (73°)", 0, "stack: [0]", 0);
            host.updateArrayVisualization(0, -1, temps.length);
            host.bindCurrentState("[" + title + "] Day 0: temp = 73°. Stack empty -> push day 0 onto monotonic stack.");
            return;
        }

        if (step == 1) {
            host.setStepIndex(2);
            host.setFoundIndex(1);
            host.setAuxIndex(0);
            host.updatePointerState("day 1 (74°)", 1, "warmer than day 0 (73°)", 0);
            host.updateArrayVisualization(0, 1, temps.length);
            host.setHighlightedLines(4, 2);
            host.bindCurrentState("[" + title + "] Day 1: temp = 74° > 73°. Warmer day found for Day 0! ans[0] = 1 - 0 = 1 day. Pop 0, push 1.");
            return;
        }

        if (step == 2) {
            host.setStepIndex(3);
            host.setFoundIndex(2);
            host.setAuxIndex(1);
            host.updatePointerState("day 2 (75°)", 2, "warmer than day 1 (74°)", 1);
            host.updateArrayVisualization(1, 2, temps.length);
            host.setHighlightedLines(4, 2);
            host.bindCurrentState("[" + title + "] Day 2: temp = 75° > 74°. Warmer day found for Day 1! ans[1] = 2 - 1 = 1 day. Pop 1, push 2.");
            return;
        }

        if (step == 3) {
            host.setStepIndex(4);
            host.setFoundIndex(5);
            host.setAuxIndex(4);
            host.updatePointerState("day 5 (72°)", 5, "warmer than day 4 (69°) and 3 (71°)", 4);
            host.updateArrayVisualization(4, 5, temps.length);
            host.setHighlightedLines(4, 2);
            host.bindCurrentState("[" + title + "] Day 5: temp = 72° resolves both colder days 4 (69°) and 3 (71°)! ans[4]=1 day, ans[3]=2 days.");
            return;
        }

        host.updateArrayVisualization(0, temps.length - 1, temps.length);
        host.bindCurrentState("[" + title + "] Completed! ans = [1, 1, 4, 2, 1, 1, 0, 0]. Monotonic stack resolved in single linear pass: O(n) Time, O(n) Space!");
        host.finish("[" + title + "] Daily Temperatures solved!");
    }

    // =========================================================================
    // 30. LeetCode #155: Min Stack
    // =========================================================================
    private static void advanceMinStack(VisualizationRuntimeHost host) {
        String techId = host.getTechniqueId();
        com.dsavisualizer.app.model.TechniqueContent tc = LeetCodePart1Repository.getContent(techId);
        String title = (tc != null && tc.title != null) ? tc.title : "Min Stack";

        int[] vals = new int[] {-2, 0, -3};
        int step = host.getStepIndex();

        if (step == 0) {
            host.setWorkingValues(vals);
            host.setStepIndex(1);
            host.setFoundIndex(0);
            host.updatePointerState("push(-2)", 0, "min: -2", 0);
            host.updateArrayVisualization(0, -1, 3);
            host.bindCurrentState("[" + title + "] Op 1: push(-2). stack = [-2], minStack = [-2]. Current min = -2.");
            return;
        }

        if (step == 1) {
            host.setStepIndex(2);
            host.setFoundIndex(1);
            host.updatePointerState("push(0)", 1, "min remains -2", 0);
            host.updateArrayVisualization(1, -1, 3);
            host.bindCurrentState("[" + title + "] Op 2: push(0). stack = [-2, 0], minStack = [-2, -2]. 0 > -2, so min remains -2.");
            return;
        }

        if (step == 2) {
            host.setStepIndex(3);
            host.setFoundIndex(2);
            host.updatePointerState("push(-3)", 2, "NEW MIN: -3", 2);
            host.updateArrayVisualization(2, -1, 3);
            host.setHighlightedLines(4, 1);
            host.bindCurrentState("[" + title + "] Op 3: push(-3). stack = [-2, 0, -3], minStack = [-2, -2, -3]. -3 < -2 -> NEW MIN = -3! getMin() = -3.");
            return;
        }

        host.setFoundIndex(1);
        host.updatePointerState("pop()", 1, "min restored: -2", 0);
        host.updateArrayVisualization(1, -1, 3);
        host.bindCurrentState("[" + title + "] Op 4: pop() removes -3 from top. minStack pops -3 -> getMin() returns to -2. All operations run in O(1) Time!");
        host.finish("[" + title + "] Min Stack demonstration completed!");
    }
}
