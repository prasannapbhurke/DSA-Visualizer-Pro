package com.dsavisualizer.app.ui.visualization;

import java.util.Locale;

/**
 * Sort-family helper logic extracted from the fragment.
 */
public final class SortVisualizationEngine {

    private SortVisualizationEngine() {
    }

    public static String resolveIntro(String techniqueId) {
        if ("merging".equals(techniqueId)) {
            return "Both sorted lists are being combined into one sorted output.";
        }
        if ("merge_sort".equals(techniqueId)) {
            return "Sorted blocks are being merged back together.";
        }
        if ("radix_sort".equals(techniqueId)) {
            return "Numbers are being reordered digit by digit.";
        }
        return "Choose a pivot, partition the subarray, then continue with the left and right parts.";
    }

    public static String resolveStep(String techniqueId, int index) {
        if ("merging".equals(techniqueId)) {
            return "Merged output is confirmed up to index " + index;
        }
        if ("merge_sort".equals(techniqueId)) {
            return "Merged runs are settled through index " + index;
        }
        if ("radix_sort".equals(techniqueId)) {
            return "Digit passes have settled value at index " + index;
        }
        if ("bubble_sort".equals(techniqueId)) {
            return "Bubble Sort is scanning neighbors near index " + index;
        }
        if ("selection_sort".equals(techniqueId)) {
            return "Selection Sort is fixing the sorted position at index " + index;
        }
        if ("insertion_sort".equals(techniqueId)) {
            return "Insertion Sort is inserting the active value around index " + index;
        }
        if ("heapsort".equals(techniqueId)) {
            return "Heap Sort is restoring heap order around index " + index;
        }
        if ("quicksort".equals(techniqueId)) {
            return "Quicksort is partitioning around a pivot near index " + index;
        }
        return "Subarrays are sorted around index " + index;
    }

    public static String resolveDone(String techniqueId) {
        if ("merging".equals(techniqueId)) {
            return "Merging completed";
        }
        if ("merge_sort".equals(techniqueId)) {
            return "Merge Sort completed";
        }
        if ("radix_sort".equals(techniqueId)) {
            return "Radix Sort completed";
        }
        return "Quicksort completed";
    }

    public static String resolveOperationLabel(
        String techniqueId,
        String message,
        int stepIndex,
        int auxIndex,
        int foundIndex,
        int heapSize,
        int heapPhase
    ) {
        String safe = message == null ? "" : message.toLowerCase(Locale.US);
        if ("selection_sort".equals(techniqueId)) {
            if (safe.contains("placing minimum")) {
                return "Placing Minimum";
            }
            if (safe.contains("scanning")) {
                return foundIndex == auxIndex ? "New Minimum" : "Scanning";
            }
            return stepIndex <= 0 ? "Starting" : "Selection Pass";
        }
        if ("bubble_sort".equals(techniqueId)) {
            if (safe.contains("swapping")) {
                return "Swapping";
            }
            if (safe.contains("comparing")) {
                return "Comparing";
            }
            return stepIndex <= 0 ? "Starting Pass" : "Bubble Pass";
        }
        if ("insertion_sort".equals(techniqueId)) {
            if (safe.contains("shifting")) {
                return "Shifting Left";
            }
            if (safe.contains("inserted")) {
                return "Inserted";
            }
            return stepIndex <= 1 ? "Starting" : "Insertion Pass";
        }
        if ("quicksort".equals(techniqueId)) {
            if (safe.contains("choose pivot")) {
                return "Choose Pivot";
            }
            if (safe.contains("swap it into the left partition")) {
                return "Partition Swap";
            }
            if (safe.contains("already belongs in the left partition")) {
                return "Keep Left";
            }
            if (safe.contains("leave it on the right side")) {
                return "Keep Right";
            }
            if (safe.contains("place pivot")) {
                return "Place Pivot";
            }
            if (safe.contains("partition")) {
                return "Partition";
            }
            if (safe.contains("pivot")) {
                return "Pivot";
            }
        }
        if ("heapsort".equals(techniqueId)) {
            if (safe.contains("max heap")) {
                return "Heap Ready";
            }
            if (safe.contains("swap root")) {
                return "Extracting Max";
            }
            if (safe.contains("percolate")) {
                return "Heapifying";
            }
            if (safe.contains("heap restored") || safe.contains("heap property")) {
                return "Heap Restored";
            }
            return heapPhase == 0 && heapSize > 1 ? "Extracting Max" : "Heapifying";
        }
        if ("merge_sort".equals(techniqueId) || "merging".equals(techniqueId)) {
            return safe.contains("completed") ? "Completed" : "Merging";
        }
        if ("radix_sort".equals(techniqueId)) {
            return safe.contains("completed") ? "Completed" : "Bucket Pass";
        }
        return null;
    }

    public static String buildDetailedExplanation(
        String techniqueId,
        String message,
        int[] values,
        int stepIndex,
        int auxIndex,
        int foundIndex,
        int heapSize,
        int heapPhase
    ) {
        int[] safeValues = values == null ? new int[0] : values;
        String safeMessage = message == null ? "" : message.toLowerCase(Locale.US);
        if ("bubble_sort".equals(techniqueId)) {
            if (hasPair(safeValues, stepIndex, auxIndex)) {
                int left = safeValues[stepIndex];
                int right = safeValues[auxIndex];
                if (safeMessage.contains("swapping")) {
                    return "These two neighboring values are out of order, so Bubble Sort swaps "
                        + left + " and " + right + " to push the larger value toward the unsorted end.";
                }
                return "Bubble Sort compares neighboring values " + left + " and " + right
                    + " before deciding whether the larger one should move right.";
            }
            return "Bubble Sort compares neighbors and pushes the largest unsorted value to the end in each pass.";
        }
        if ("selection_sort".equals(techniqueId)) {
            if (safeMessage.contains("placing minimum")) {
                int placedIndex = Math.max(0, stepIndex - 1);
                int placedValue = readValue(safeValues, placedIndex);
                return "The smallest value found in the unsorted region is now placed at index "
                    + placedIndex + (placedValue == Integer.MIN_VALUE ? "." : " with value " + placedValue + ".");
            }
            if (foundIndex >= 0 && foundIndex < safeValues.length && auxIndex >= 0 && auxIndex < safeValues.length) {
                int candidate = safeValues[auxIndex];
                int minimum = safeValues[foundIndex];
                if (foundIndex == auxIndex) {
                    return "Selection Sort found a new smallest candidate " + minimum
                        + " in the unsorted region, so that index becomes the current minimum to beat.";
                }
                return "Selection Sort is scanning the unsorted region: it checks candidate " + candidate
                    + " while the current minimum stays at " + minimum + ".";
            }
            return "Selection Sort searches for the smallest unsorted value and places it into the next sorted position.";
        }
        if ("insertion_sort".equals(techniqueId)) {
            if (safeMessage.contains("shifting")) {
                int activeValue = readValue(safeValues, stepIndex);
                int comparedValue = readValue(safeValues, auxIndex);
                return "Insertion Sort shifts larger values until the active value"
                    + (activeValue == Integer.MIN_VALUE ? "" : " " + activeValue)
                    + " can slide into the correct sorted spot"
                    + (comparedValue == Integer.MIN_VALUE ? "." : " before " + comparedValue + ".");
            }
            if (safeMessage.contains("inserted")) {
                int insertedIndex = Math.max(0, stepIndex - 1);
                int insertedValue = readValue(safeValues, insertedIndex);
                return "The current value"
                    + (insertedValue == Integer.MIN_VALUE ? "" : " " + insertedValue)
                    + " has reached its sorted position, so the sorted prefix now extends through index "
                    + insertedIndex + ".";
            }
            return "Insertion Sort grows the sorted part by shifting larger values and inserting the current value in place.";
        }
        if ("quicksort".equals(techniqueId)) {
            if (safeMessage.contains("choose pivot")) {
                return "Quicksort picks a pivot for the current subarray, then scans the remaining values to build a left partition of values less than or equal to that pivot.";
            }
            if (safeMessage.contains("swap it into the left partition")) {
                return "Because the current value is less than or equal to the pivot, it is swapped forward so the left partition stays grouped together.";
            }
            if (safeMessage.contains("already belongs in the left partition")) {
                return "This value is less than or equal to the pivot and is already in the correct left-partition zone, so no swap is needed.";
            }
            if (safeMessage.contains("leave it on the right side")) {
                return "This value is greater than the pivot, so Quicksort leaves it in the right-side region for now and continues scanning.";
            }
            if (safeMessage.contains("place pivot")) {
                return "After scanning the subarray, Quicksort places the pivot between the left and right partitions. The pivot is now in its final sorted position.";
            }
            return "Quicksort partitions values around a pivot, then keeps sorting the smaller left and right parts.";
        }
        if ("heapsort".equals(techniqueId)) {
            if (safeMessage.contains("max heap")) {
                return "The array has been reorganized into a max heap, so the root now holds the largest remaining value and can be extracted first.";
            }
            if (safeMessage.contains("swap root")) {
                return "Heap Sort moves the root, which is the largest remaining value, to sorted index "
                    + Math.max(0, heapSize - 1) + " before shrinking the heap.";
            }
            if (safeMessage.contains("percolate")) {
                return "After removing the max, Heap Sort percolates the new root downward until the heap property is restored in the remaining unsorted region.";
            }
            if (safeMessage.contains("heap restored") || safeMessage.contains("heap property")) {
                return "The heap order holds again, so the next extraction can safely move the current maximum to the sorted suffix.";
            }
            return heapPhase == 0
                ? "Heap Sort repeatedly extracts the maximum root into the sorted suffix."
                : "Heap Sort is restoring the heap after the latest extraction.";
        }
        if ("radix_sort".equals(techniqueId) && safeMessage.contains("finished the")) {
            return "The current digit pass is complete, so the array is now stable for that place value and ready for the next larger digit.";
        }
        if ("radix_sort".equals(techniqueId) && stepIndex >= 0 && stepIndex < safeValues.length) {
            return "Radix Sort is grouping values by the current digit, so value " + safeValues[stepIndex]
                + " is being placed according to its active bucket.";
        }
        return null;
    }

    private static boolean hasPair(int[] values, int first, int second) {
        return first >= 0 && second >= 0 && first < values.length && second < values.length;
    }

    private static int readValue(int[] values, int index) {
        if (index < 0 || index >= values.length) {
            return Integer.MIN_VALUE;
        }
        return values[index];
    }

    public static int[] countingSortByDigit(int[] values, int divisor) {
        int[] counts = new int[10];
        int[] output = new int[values.length];

        for (int value : values) {
            counts[Math.abs(value / divisor) % 10]++;
        }
        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }
        for (int i = values.length - 1; i >= 0; i--) {
            int digit = Math.abs(values[i] / divisor) % 10;
            output[counts[digit] - 1] = values[i];
            counts[digit]--;
        }
        return output;
    }

    public static int resolveMaxAbsValue(int[] values) {
        int max = 0;
        for (int value : values) {
            max = Math.max(max, Math.abs(value));
        }
        return max;
    }

    public static String describeDigit(int divisor) {
        if (divisor == 1) {
            return "ones";
        }
        if (divisor == 10) {
            return "tens";
        }
        if (divisor == 100) {
            return "hundreds";
        }
        return divisor + "s place";
    }

    public static int safeAdd(int left, int right) {
        if (left >= 9999 || right >= 9999) {
            return 9999;
        }
        return left + right;
    }
}
