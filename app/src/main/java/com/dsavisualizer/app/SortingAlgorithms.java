package com.dsavisualizer.app;

public class SortingAlgorithms {

    public static void prepare(VisualizerView visualizerView, int[] arr) {
        visualizerView.post(() -> {
            visualizerView.clearSorted();
            visualizerView.init(arr);
        });
    }

    public static void runBubbleSort(
        int[] arr,
        VisualizerView visualizerView,
        AlgorithmViewModel viewModel,
        GraphView.AlgorithmSleeper sleeper
    ) {
        for (int i = 0; i < arr.length - 1; i++) {
            viewModel.updateState(0, "Pass " + (i + 1));

            for (int j = 0; j < arr.length - i - 1; j++) {
                visualizerView.setHighlight(j, j + 1);
                viewModel.updateState(1, "Compare " + arr[j] + " and " + arr[j + 1]);
                sleeper.sleep(800);

                if (arr[j] > arr[j + 1]) {
                    viewModel.updateState(2, "Swap the out-of-order pair");
                    swap(arr, j, j + 1);
                    visualizerView.updateData(arr);
                }
            }

            visualizerView.setSorted(arr.length - i - 1);
        }

        visualizerView.setSorted(0);
        viewModel.updateState(-1, "Bubble Sort completed");
    }

    public static void runInsertionSort(
        int[] arr,
        VisualizerView visualizerView,
        AlgorithmViewModel viewModel,
        GraphView.AlgorithmSleeper sleeper
    ) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            visualizerView.setHighlight(i, -1);
            viewModel.updateState(1, "Pick key " + key);
            sleeper.sleep(800);

            while (j >= 0 && arr[j] > key) {
                viewModel.updateState(2, "Shift " + arr[j] + " to the right");
                arr[j + 1] = arr[j];
                visualizerView.updateData(arr);
                j--;
                sleeper.sleep(650);
            }

            arr[j + 1] = key;
            visualizerView.updateData(arr);
            viewModel.updateState(3, "Insert " + key + " into its position");

            for (int k = 0; k <= i; k++) {
                visualizerView.setSorted(k);
            }
        }

        viewModel.updateState(-1, "Insertion Sort completed");
    }

    public static void runSelectionSort(
        int[] arr,
        VisualizerView visualizerView,
        AlgorithmViewModel viewModel,
        GraphView.AlgorithmSleeper sleeper
    ) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            visualizerView.setHighlight(i, -1);
            viewModel.updateState(1, "Start a new minimum at index " + i);

            for (int j = i + 1; j < arr.length; j++) {
                visualizerView.setHighlight(j, minIndex);
                viewModel.updateState(2, "Compare against " + arr[minIndex]);
                sleeper.sleep(600);

                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                    viewModel.updateState(3, "New minimum is " + arr[minIndex]);
                }
            }

            swap(arr, i, minIndex);
            visualizerView.updateData(arr);
            visualizerView.setSorted(i);
            sleeper.sleep(700);
        }

        visualizerView.setSorted(arr.length - 1);
        viewModel.updateState(-1, "Selection Sort completed");
    }

    public static void runQuickSort(
        int[] arr,
        int low,
        int high,
        VisualizerView visualizerView,
        AlgorithmViewModel viewModel,
        GraphView.AlgorithmSleeper sleeper
    ) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high, visualizerView, viewModel, sleeper);
            visualizerView.setSorted(pivotIndex);
            runQuickSort(arr, low, pivotIndex - 1, visualizerView, viewModel, sleeper);
            runQuickSort(arr, pivotIndex + 1, high, visualizerView, viewModel, sleeper);
        } else if (low >= 0 && low < arr.length) {
            visualizerView.setSorted(low);
        }
    }

    public static void runMergeSort(
        int[] arr,
        int left,
        int right,
        VisualizerView visualizerView,
        AlgorithmViewModel viewModel,
        GraphView.AlgorithmSleeper sleeper
    ) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            runMergeSort(arr, left, mid, visualizerView, viewModel, sleeper);
            runMergeSort(arr, mid + 1, right, visualizerView, viewModel, sleeper);
            merge(arr, left, mid, right, visualizerView, sleeper);
        }

        if (left == 0 && right == arr.length - 1) {
            viewModel.updateState(-1, "Merge Sort completed");
        }
    }

    public static void runHeapSort(
        int[] arr,
        VisualizerView visualizerView,
        AlgorithmViewModel viewModel,
        GraphView.AlgorithmSleeper sleeper
    ) {
        int size = arr.length;

        for (int i = size / 2 - 1; i >= 0; i--) {
            viewModel.updateState(0, "Heapify subtree rooted at index " + i);
            heapify(arr, size, i, visualizerView, viewModel, sleeper);
        }

        for (int end = size - 1; end > 0; end--) {
            viewModel.updateState(1, "Move the maximum element to index " + end);
            swap(arr, 0, end);
            visualizerView.updateData(arr);
            visualizerView.setSorted(end);
            sleeper.sleep(700);
            heapify(arr, end, 0, visualizerView, viewModel, sleeper);
        }

        visualizerView.setSorted(0);
        viewModel.updateState(-1, "Heap Sort completed");
    }

    public static void runRadixSort(
        int[] arr,
        VisualizerView visualizerView,
        AlgorithmViewModel viewModel,
        GraphView.AlgorithmSleeper sleeper
    ) {
        int max = arr[0];

        for (int value : arr) {
            if (value > max) {
                max = value;
            }
        }

        for (int exp = 1; max / exp > 0; exp *= 10) {
            viewModel.updateState(0, "Sort by digit place " + exp);
            countSort(arr, exp, visualizerView, sleeper);
        }

        for (int i = 0; i < arr.length; i++) {
            visualizerView.setSorted(i);
        }

        viewModel.updateState(-1, "Radix Sort completed");
    }

    private static int partition(
        int[] arr,
        int low,
        int high,
        VisualizerView visualizerView,
        AlgorithmViewModel viewModel,
        GraphView.AlgorithmSleeper sleeper
    ) {
        int pivot = arr[high];
        int i = low - 1;
        viewModel.updateState(0, "Pivot selected: " + pivot);

        for (int j = low; j < high; j++) {
            visualizerView.setHighlight(j, high);
            sleeper.sleep(550);

            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
                visualizerView.updateData(arr);
            }
        }

        swap(arr, i + 1, high);
        visualizerView.updateData(arr);
        return i + 1;
    }

    private static void merge(
        int[] arr,
        int left,
        int mid,
        int right,
        VisualizerView visualizerView,
        GraphView.AlgorithmSleeper sleeper
    ) {
        int leftSize = mid - left + 1;
        int rightSize = right - mid;
        int[] leftPart = new int[leftSize];
        int[] rightPart = new int[rightSize];

        for (int i = 0; i < leftSize; i++) {
            leftPart[i] = arr[left + i];
        }

        for (int j = 0; j < rightSize; j++) {
            rightPart[j] = arr[mid + 1 + j];
        }

        int i = 0;
        int j = 0;
        int k = left;

        while (i < leftSize && j < rightSize) {
            visualizerView.setHighlight(left + i, mid + 1 + j);

            if (leftPart[i] <= rightPart[j]) {
                arr[k++] = leftPart[i++];
            } else {
                arr[k++] = rightPart[j++];
            }

            visualizerView.updateData(arr);
            sleeper.sleep(500);
        }

        while (i < leftSize) {
            arr[k++] = leftPart[i++];
            visualizerView.updateData(arr);
            sleeper.sleep(250);
        }

        while (j < rightSize) {
            arr[k++] = rightPart[j++];
            visualizerView.updateData(arr);
            sleeper.sleep(250);
        }
    }

    private static void countSort(
        int[] arr,
        int exp,
        VisualizerView visualizerView,
        GraphView.AlgorithmSleeper sleeper
    ) {
        int[] output = new int[arr.length];
        int[] count = new int[10];

        for (int value : arr) {
            count[(value / exp) % 10]++;
        }

        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        for (int i = arr.length - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            output[count[digit] - 1] = arr[i];
            count[digit]--;
        }

        for (int i = 0; i < arr.length; i++) {
            arr[i] = output[i];
            visualizerView.updateData(arr);
            visualizerView.setHighlight(i, -1);
            sleeper.sleep(350);
        }
    }

    private static void heapify(
        int[] arr,
        int size,
        int root,
        VisualizerView visualizerView,
        AlgorithmViewModel viewModel,
        GraphView.AlgorithmSleeper sleeper
    ) {
        int largest = root;
        int left = 2 * root + 1;
        int right = 2 * root + 2;

        if (left < size) {
            visualizerView.setHighlight(root, left);
            sleeper.sleep(350);
            if (arr[left] > arr[largest]) {
                largest = left;
            }
        }

        if (right < size) {
            visualizerView.setHighlight(largest, right);
            sleeper.sleep(350);
            if (arr[right] > arr[largest]) {
                largest = right;
            }
        }

        if (largest != root) {
            viewModel.updateState(2, "Swap " + arr[root] + " with " + arr[largest]);
            swap(arr, root, largest);
            visualizerView.updateData(arr);
            sleeper.sleep(650);
            heapify(arr, size, largest, visualizerView, viewModel, sleeper);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
