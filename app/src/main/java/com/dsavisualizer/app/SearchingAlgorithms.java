package com.dsavisualizer.app;

public class SearchingAlgorithms {

    public static void prepare(VisualizerView visualizerView, int[] arr) {
        visualizerView.post(() -> {
            visualizerView.clearSorted();
            visualizerView.init(arr);
        });
    }

    public static void runLinearSearch(
        int[] arr,
        int target,
        VisualizerView visualizerView,
        AlgorithmViewModel viewModel,
        GraphView.AlgorithmSleeper sleeper
    ) {
        for (int i = 0; i < arr.length; i++) {
            visualizerView.setHighlight(i, -1);
            viewModel.updateState(0, "Check index " + i + " with value " + arr[i]);
            sleeper.sleep(850);

            if (arr[i] == target) {
                visualizerView.setSorted(i);
                viewModel.updateState(-1, "Target found at index " + i);
                return;
            }
        }

        viewModel.updateState(-1, "Target not found");
    }

    public static void runBinarySearch(
        int[] arr,
        int target,
        VisualizerView visualizerView,
        AlgorithmViewModel viewModel,
        GraphView.AlgorithmSleeper sleeper
    ) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            visualizerView.setHighlight(mid, -1);
            viewModel.updateState(1, "Middle value is " + arr[mid]);
            sleeper.sleep(1000);

            if (arr[mid] == target) {
                visualizerView.setSorted(mid);
                viewModel.updateState(-1, "Target found at index " + mid);
                return;
            }

            if (arr[mid] < target) {
                left = mid + 1;
                viewModel.updateState(2, "Move to the right half");
            } else {
                right = mid - 1;
                viewModel.updateState(3, "Move to the left half");
            }
        }

        viewModel.updateState(-1, "Target not found");
    }

    public static void runPatternMatching(
        String text,
        String pattern,
        AlgorithmViewModel viewModel,
        GraphView.AlgorithmSleeper sleeper
    ) {
        if (pattern.isEmpty()) {
            viewModel.updateState(-1, "Pattern is empty");
            return;
        }

        for (int start = 0; start <= text.length() - pattern.length(); start++) {
            viewModel.updateState(0, "Try alignment at index " + start);
            sleeper.sleep(700);

            int j = 0;
            while (j < pattern.length() && text.charAt(start + j) == pattern.charAt(j)) {
                viewModel.updateState(1, "Matched '" + pattern.charAt(j) + "' at text index " + (start + j));
                sleeper.sleep(450);
                j++;
            }

            if (j == pattern.length()) {
                viewModel.updateState(-1, "Pattern found at index " + start);
                return;
            }

            if (start + j < text.length()) {
                viewModel.updateState(2, "Mismatch at text index " + (start + j));
                sleeper.sleep(450);
            }
        }

        viewModel.updateState(-1, "Pattern not found");
    }
}
