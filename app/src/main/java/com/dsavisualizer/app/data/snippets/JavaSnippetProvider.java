package com.dsavisualizer.app.data.snippets;

public final class JavaSnippetProvider {

    private JavaSnippetProvider() {
    }

    public static String getJavaCodeSnippet(String techniqueId, String style) {
        if ("selection_sort".equals(techniqueId)) {
            return getJavaSelectionSortPdf(style);
        }
        if ("insertion_sort".equals(techniqueId)) {
            return getJavaInsertionSortPdf(style);
        }
        if ("merging".equals(techniqueId)) {
            return getJavaMergingPdf(style);
        }
        if ("merge_sort".equals(techniqueId)) {
            return getJavaMergeSortPdf(style);
        }
        if ("radix_sort".equals(techniqueId)) {
            return getJavaRadixSortPdf(style);
        }
        if ("find_linked_list".equals(techniqueId)) {
            return getJavaSearchInLinkedList(style);
        }
        if ("delete_linked_list".equals(techniqueId)) {
            return getJavaDeleteNodeByItemLinkedList(style);
        }
        switch (techniqueId) {
            case "linear_search":
                if ("Functions".equals(style)) {
                    return "import java.util.Scanner;\n"
                        + "class Main {\n"
                        + "    static int linear(int arr[], int n, int item) {\n"
                        + "        int loc = 1;\n"
                        + "        arr[n + 1] = item;\n\n"
                        + "        while(arr[loc] != item)\n"
                        + "            loc++;\n\n"
                        + "        if(loc == n + 1)\n"
                        + "            return 0;\n"
                        + "        else\n"
                        + "            return loc;\n"
                        + "    }\n\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Scanner sc = new Scanner(System.in);\n\n"
                        + "        System.out.print(\"Enter number of elements: \");\n"
                        + "        int n = sc.nextInt();\n"
                        + "        int arr[] = new int[n + 2];\n\n"
                        + "        System.out.println(\"Enter array elements:\");\n"
                        + "        for(int i = 1; i <= n; i++)\n"
                        + "            arr[i] = sc.nextInt();\n\n"
                        + "        System.out.print(\"Enter item to search: \");\n"
                        + "        int item = sc.nextInt();\n\n"
                        + "        int loc = linear(arr, n, item);\n\n"
                        + "        System.out.println(\"Location: \" + loc);\n"
                        + "    }\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "import java.util.Scanner;\n"
                        + "class Main {\n"
                        + "    static int linear(int arr[], int n, int item, int loc) {\n"
                        + "        if(arr[loc] == item) {\n"
                        + "            if(loc == n + 1)\n"
                        + "                return 0;\n"
                        + "            else\n"
                        + "                return loc;\n"
                        + "        }\n"
                        + "        return linear(arr, n, item, loc + 1);\n"
                        + "    }\n\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Scanner sc = new Scanner(System.in);\n\n"
                        + "        System.out.print(\"Enter number of elements: \");\n"
                        + "        int n = sc.nextInt();\n"
                        + "        int arr[] = new int[n + 2];\n\n"
                        + "        System.out.println(\"Enter array elements:\");\n"
                        + "        for(int i = 1; i <= n; i++)\n"
                        + "            arr[i] = sc.nextInt();\n\n"
                        + "        System.out.print(\"Enter item to search: \");\n"
                        + "        int item = sc.nextInt();\n"
                        + "        arr[n + 1] = item;\n\n"
                        + "        int loc = linear(arr, n, item, 1);\n\n"
                        + "        System.out.println(\"Location: \" + loc);\n"
                        + "    }\n"
                        + "}";
                }
                return "import java.util.Scanner;\n"
                    + "class Main {\n"
                    + "    public static void main(String[] args) {\n"
                    + "        Scanner sc = new Scanner(System.in);\n"
                    + "        System.out.print(\"Enter number of elements: \");\n"
                    + "        int n = sc.nextInt();\n"
                    + "        int arr[] = new int[n + 2];\n\n"
                    + "        System.out.println(\"Enter array elements:\");\n"
                    + "        for(int i = 1; i <= n; i++)\n"
                    + "            arr[i] = sc.nextInt();\n\n"
                    + "        System.out.print(\"Enter item to search: \");\n"
                    + "        int item = sc.nextInt();\n"
                    + "        arr[n + 1] = item;\n"
                    + "        int loc = 1;\n\n"
                    + "        while(arr[loc] != item)\n"
                    + "            loc++;\n\n"
                    + "        if(loc == n + 1)\n"
                    + "            loc = 0;\n\n"
                    + "        System.out.println(\"Location: \" + loc);\n"
                    + "    }\n"
                    + "}";
            case "binary_search":
                if ("Functions".equals(style)) {
                    return "import java.util.Scanner;\n"
                        + "class Main {\n"
                        + "    static int binary(int arr[], int lb, int ub, int item) {\n"
                        + "        int beg = lb, end = ub;\n\n"
                        + "        while(beg <= end) {\n"
                        + "            int mid = (beg + end) / 2;\n\n"
                        + "            if(arr[mid] == item)\n"
                        + "                return mid;\n"
                        + "            else if(item < arr[mid])\n"
                        + "                end = mid - 1;\n"
                        + "            else\n"
                        + "                beg = mid + 1;\n"
                        + "        }\n"
                        + "        return -1;\n"
                        + "    }\n\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Scanner sc = new Scanner(System.in);\n\n"
                        + "        System.out.print(\"Enter number of elements: \");\n"
                        + "        int n = sc.nextInt();\n"
                        + "        int arr[] = new int[n];\n\n"
                        + "        System.out.println(\"Enter sorted array elements:\");\n"
                        + "        for(int i = 0; i < n; i++)\n"
                        + "            arr[i] = sc.nextInt();\n\n"
                        + "        System.out.print(\"Enter item to search: \");\n"
                        + "        int item = sc.nextInt();\n\n"
                        + "        int loc = binary(arr, 0, n - 1, item);\n\n"
                        + "        System.out.println(\"Location: \" + loc);\n"
                        + "    }\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "import java.util.Scanner;\n"
                        + "class Main {\n"
                        + "    static int binary(int arr[], int beg, int end, int item) {\n"
                        + "        if(beg > end)\n"
                        + "            return -1;\n\n"
                        + "        int mid = (beg + end) / 2;\n\n"
                        + "        if(arr[mid] == item)\n"
                        + "            return mid;\n"
                        + "        else if(item < arr[mid])\n"
                        + "            return binary(arr, beg, mid - 1, item);\n"
                        + "        else\n"
                        + "            return binary(arr, mid + 1, end, item);\n"
                        + "    }\n\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Scanner sc = new Scanner(System.in);\n\n"
                        + "        System.out.print(\"Enter number of elements: \");\n"
                        + "        int n = sc.nextInt();\n"
                        + "        int arr[] = new int[n];\n\n"
                        + "        System.out.println(\"Enter sorted array elements:\");\n"
                        + "        for(int i = 0; i < n; i++)\n"
                        + "            arr[i] = sc.nextInt();\n\n"
                        + "        System.out.print(\"Enter item to search: \");\n"
                        + "        int item = sc.nextInt();\n\n"
                        + "        int loc = binary(arr, 0, n - 1, item);\n\n"
                        + "        System.out.println(\"Location: \" + loc);\n"
                        + "    }\n"
                        + "}";
                }
                return "import java.util.Scanner;\n"
                    + "class Main {\n"
                    + "    public static void main(String[] args) {\n"
                    + "        Scanner sc = new Scanner(System.in);\n"
                    + "        System.out.print(\"Enter number of elements: \");\n"
                    + "        int n = sc.nextInt();\n"
                    + "        int arr[] = new int[n];\n\n"
                    + "        System.out.println(\"Enter sorted array elements:\");\n"
                    + "        for(int i = 0; i < n; i++)\n"
                    + "            arr[i] = sc.nextInt();\n\n"
                    + "        System.out.print(\"Enter item to search: \");\n"
                    + "        int item = sc.nextInt();\n\n"
                    + "        int beg = 0, end = n - 1, mid;\n"
                    + "        mid = (beg + end) / 2;\n\n"
                    + "        while(beg <= end && arr[mid] != item) {\n"
                    + "            if(item < arr[mid])\n"
                    + "                end = mid - 1;\n"
                    + "            else\n"
                    + "                beg = mid + 1;\n\n"
                    + "            mid = (beg + end) / 2;\n"
                    + "        }\n\n"
                    + "        if(beg <= end && arr[mid] == item)\n"
                    + "            System.out.println(\"Location: \" + mid);\n"
                    + "        else\n"
                    + "            System.out.println(\"Location: -1\");\n"
                    + "    }\n"
                    + "}";
            case "jump_search":
                if ("Functions".equals(style)) {
                    return "public int jumpSearch(int[] arr, int target) {\n"
                        + "    int blockSize = getBlockSize(arr.length);\n"
                        + "    int start = findBlockStart(arr, target, blockSize);\n"
                        + "    if (start == -1) {\n"
                        + "        return -1;\n"
                        + "    }\n"
                        + "    return scanBlock(arr, target, start, Math.min(start + blockSize, arr.length));\n"
                        + "}\n\n"
                        + "private int getBlockSize(int length) {\n"
                        + "    return (int) Math.sqrt(length);\n"
                        + "}\n\n"
                        + "private int findBlockStart(int[] arr, int target, int blockSize) {\n"
                        + "    int start = 0;\n"
                        + "    while (start < arr.length) {\n"
                        + "        int end = Math.min(start + blockSize, arr.length) - 1;\n"
                        + "        if (arr[end] >= target) {\n"
                        + "            return start;\n"
                        + "        }\n"
                        + "        start += blockSize;\n"
                        + "    }\n"
                        + "    return -1;\n"
                        + "}\n\n"
                        + "private int scanBlock(int[] arr, int target, int start, int endExclusive) {\n"
                        + "    for (int i = start; i < endExclusive; i++) {\n"
                        + "        if (arr[i] == target) {\n"
                        + "            return i;\n"
                        + "        }\n"
                        + "    }\n"
                        + "    return -1;\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "public int jumpSearchRecursive(int[] arr, int target, int start, int step) {\n"
                        + "    int end = Math.min(start + step, arr.length);\n"
                        + "    if (start >= arr.length) {\n"
                        + "        return -1;\n"
                        + "    }\n"
                        + "    if (arr[end - 1] < target) {\n"
                        + "        return jumpSearchRecursive(arr, target, end, step);\n"
                        + "    }\n"
                        + "    return scanBlock(arr, target, start, end - 1);\n"
                        + "}\n\n"
                        + "private int scanBlock(int[] arr, int target, int index, int end) {\n"
                        + "    if (index > end) {\n"
                        + "        return -1;\n"
                        + "    }\n"
                        + "    if (arr[index] == target) {\n"
                        + "        return index;\n"
                        + "    }\n"
                        + "    return scanBlock(arr, target, index + 1, end);\n"
                        + "}";
                }
                return "public int jumpSearch(int[] arr, int target) {\n"
                    + "    int step = (int) Math.sqrt(arr.length);\n"
                    + "    int prev = 0;\n\n"
                    + "    while (prev < arr.length && arr[Math.min(step, arr.length) - 1] < target) {\n"
                    + "        prev = step;\n"
                    + "        step += (int) Math.sqrt(arr.length);\n"
                    + "        if (prev >= arr.length) {\n"
                    + "            return -1;\n"
                    + "        }\n"
                    + "    }\n\n"
                    + "    while (prev < Math.min(step, arr.length)) {\n"
                    + "        if (arr[prev] == target) {\n"
                    + "            return prev;\n"
                    + "        }\n"
                    + "        prev++;\n"
                    + "    }\n"
                    + "    return -1;\n"
                    + "}";
            case "bubble_sort":
                if ("Functions".equals(style)) {
                    return "import java.util.Scanner;\n"
                        + "class Main {\n\n"
                        + "    static void bubble(int arr[], int n) {\n"
                        + "        for(int k = 1; k <= n - 1; k++) {\n"
                        + "            int ptr = 0;\n"
                        + "            while(ptr <= n - k - 1) {\n"
                        + "                if(arr[ptr] < arr[ptr + 1]) {\n"
                        + "                    int temp = arr[ptr];\n"
                        + "                    arr[ptr] = arr[ptr + 1];\n"
                        + "                    arr[ptr + 1] = temp;\n"
                        + "                }\n"
                        + "                ptr++;\n"
                        + "            }\n"
                        + "        }\n"
                        + "    }\n\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Scanner sc = new Scanner(System.in);\n\n"
                        + "        System.out.print(\"Enter number of elements: \");\n"
                        + "        int n = sc.nextInt();\n"
                        + "        int arr[] = new int[n];\n\n"
                        + "        System.out.println(\"Enter array elements:\");\n"
                        + "        for(int i = 0; i < n; i++)\n"
                        + "            arr[i] = sc.nextInt();\n\n"
                        + "        bubble(arr, n);\n\n"
                        + "        System.out.println(\"Sorted array:\");\n"
                        + "        for(int i = 0; i < n; i++)\n"
                        + "            System.out.print(arr[i] + \" \");\n"
                        + "    }\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "import java.util.Scanner;\n"
                        + "class Main {\n\n"
                        + "    static void bubble(int arr[], int n) {\n"
                        + "        if(n == 1)\n"
                        + "            return;\n\n"
                        + "        int ptr = 0;\n\n"
                        + "        while(ptr <= n - 2) {\n"
                        + "            if(arr[ptr] < arr[ptr + 1]) {\n"
                        + "                int temp = arr[ptr];\n"
                        + "                arr[ptr] = arr[ptr + 1];\n"
                        + "                arr[ptr + 1] = temp;\n"
                        + "            }\n"
                        + "            ptr++;\n"
                        + "        }\n\n"
                        + "        bubble(arr, n - 1);\n"
                        + "    }\n\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Scanner sc = new Scanner(System.in);\n\n"
                        + "        System.out.print(\"Enter number of elements: \");\n"
                        + "        int n = sc.nextInt();\n"
                        + "        int arr[] = new int[n];\n\n"
                        + "        System.out.println(\"Enter array elements:\");\n"
                        + "        for(int i = 0; i < n; i++)\n"
                        + "            arr[i] = sc.nextInt();\n\n"
                        + "        bubble(arr, n);\n\n"
                        + "        System.out.println(\"Sorted array:\");\n"
                        + "        for(int i = 0; i < n; i++)\n"
                        + "            System.out.print(arr[i] + \" \");\n"
                        + "    }\n"
                        + "}";
                }
                return "import java.util.Scanner;\n"
                    + "class Main {\n"
                    + "    public static void main(String[] args) {\n"
                    + "        Scanner sc = new Scanner(System.in);\n\n"
                    + "        System.out.print(\"Enter number of elements: \");\n"
                    + "        int n = sc.nextInt();\n"
                    + "        int arr[] = new int[n];\n\n"
                    + "        System.out.println(\"Enter array elements:\");\n"
                    + "        for(int i = 0; i < n; i++)\n"
                    + "            arr[i] = sc.nextInt();\n\n"
                    + "        for(int k = 1; k <= n - 1; k++) {\n"
                    + "            int ptr = 0;\n"
                    + "            while(ptr <= n - k - 1) {\n"
                    + "                if(arr[ptr] < arr[ptr + 1]) {\n"
                    + "                    int temp = arr[ptr];\n"
                    + "                    arr[ptr] = arr[ptr + 1];\n"
                    + "                    arr[ptr + 1] = temp;\n"
                    + "                }\n"
                    + "                ptr++;\n"
                    + "            }\n"
                    + "        }\n\n"
                    + "        System.out.println(\"Sorted array:\");\n"
                    + "        for(int i = 0; i < n; i++)\n"
                    + "            System.out.print(arr[i] + \" \");\n"
                    + "    }\n"
                    + "}";
            case "selection_sort":
                if ("Functions".equals(style)) {
                    return "public void selectionSort(int[] arr) {\n"
                        + "    for (int i = 0; i < arr.length - 1; i++) {\n"
                        + "        int minIndex = findMinIndex(arr, i);\n"
                        + "        swap(arr, i, minIndex);\n"
                        + "    }\n"
                        + "}\n\n"
                        + "private int findMinIndex(int[] arr, int start) {\n"
                        + "    int minIndex = start;\n"
                        + "    for (int i = start + 1; i < arr.length; i++) {\n"
                        + "        if (arr[i] < arr[minIndex]) {\n"
                        + "            minIndex = i;\n"
                        + "        }\n"
                        + "    }\n"
                        + "    return minIndex;\n"
                        + "}\n\n"
                        + "private void swap(int[] arr, int left, int right) {\n"
                        + "    int temp = arr[left];\n"
                        + "    arr[left] = arr[right];\n"
                        + "    arr[right] = temp;\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "public void selectionSortRecursive(int[] arr, int start) {\n"
                        + "    if (start >= arr.length - 1) {\n"
                        + "        return;\n"
                        + "    }\n"
                        + "    int minIndex = start;\n"
                        + "    for (int i = start + 1; i < arr.length; i++) {\n"
                        + "        if (arr[i] < arr[minIndex]) {\n"
                        + "            minIndex = i;\n"
                        + "        }\n"
                        + "    }\n"
                        + "    int temp = arr[start];\n"
                        + "    arr[start] = arr[minIndex];\n"
                        + "    arr[minIndex] = temp;\n"
                        + "    selectionSortRecursive(arr, start + 1);\n"
                        + "}";
                }
                return "public void selectionSort(int[] arr) {\n"
                    + "    for (int i = 0; i < arr.length - 1; i++) {\n"
                    + "        int minIndex = i;\n"
                    + "        for (int j = i + 1; j < arr.length; j++) {\n"
                    + "            if (arr[j] < arr[minIndex]) {\n"
                    + "                minIndex = j;\n"
                    + "            }\n"
                    + "        }\n"
                    + "        int temp = arr[i];\n"
                    + "        arr[i] = arr[minIndex];\n"
                    + "        arr[minIndex] = temp;\n"
                    + "    }\n"
                    + "}";
            case "insertion_sort":
                if ("Functions".equals(style)) {
                    return "public void insertionSort(int[] arr) {\n"
                        + "    for (int i = 1; i < arr.length; i++) {\n"
                        + "        placeValue(arr, i);\n"
                        + "    }\n"
                        + "}\n\n"
                        + "private void placeValue(int[] arr, int index) {\n"
                        + "    int key = arr[index];\n"
                        + "    int j = index - 1;\n"
                        + "    while (j >= 0 && arr[j] > key) {\n"
                        + "        arr[j + 1] = arr[j];\n"
                        + "        j--;\n"
                        + "    }\n"
                        + "    arr[j + 1] = key;\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "public void insertionSortRecursive(int[] arr, int n) {\n"
                        + "    if (n <= 1) {\n"
                        + "        return;\n"
                        + "    }\n"
                        + "    insertionSortRecursive(arr, n - 1);\n"
                        + "    int key = arr[n - 1];\n"
                        + "    int j = n - 2;\n"
                        + "    while (j >= 0 && arr[j] > key) {\n"
                        + "        arr[j + 1] = arr[j];\n"
                        + "        j--;\n"
                        + "    }\n"
                        + "    arr[j + 1] = key;\n"
                        + "}";
                }
                return "public void insertionSort(int[] arr) {\n"
                    + "    for (int i = 1; i < arr.length; i++) {\n"
                    + "        int key = arr[i];\n"
                    + "        int j = i - 1;\n"
                    + "        while (j >= 0 && arr[j] > key) {\n"
                    + "            arr[j + 1] = arr[j];\n"
                    + "            j--;\n"
                    + "        }\n"
                    + "        arr[j + 1] = key;\n"
                    + "    }\n"
                    + "}";
            case "stack_basics":
                return getJavaStackBasics(style);
            case "linked_stack_push":
                return getJavaLinkedStackPush(style);
            case "linked_stack_pop":
                return getJavaLinkedStackPop(style);
            case "quicksort":
                return getJavaQuicksort(style);
            case "queue_basics":
                return getJavaLinearQueue(style);
            case "circular_queue":
                return getJavaCircularQueue(style);
            case "linked_queue":
                return getJavaLinkedQueue(style);
            case "priority_queue_array":
                return getJavaPriorityQueueArray(style);
            case "priority_queue_2d":
                return getJavaPriorityQueue2d(style);
            case "preorder_traversal":
                return getJavaPreorderTraversal(style);
            case "postorder_traversal":
                return getJavaPostorderTraversal(style);
            case "bst_search_parent_tracking":
                return getJavaBstSearchParentTracking(style);
            case "bst_insertion":
                return getJavaBstInsertion(style);
            case "bst_deletion_case_a":
                return getJavaBstDeletionCaseA(style);
            case "bst_deletion_case_b":
                return getJavaBstDeletionCaseB(style);
            case "bst_deletion_del":
                return getJavaBstDeletionDel(style);
            case "max_heap_insertion":
                return getJavaMaxHeapInsertion(style);
            case "max_heap_deletion":
                return getJavaMaxHeapDeletion(style);
            case "heapsort":
                return getJavaHeapSort(style);
            case "singly_linked_list":
                if ("Functions".equals(style)) {
                    return "class Node {\n"
                        + "    int data;\n"
                        + "    Node next;\n\n"
                        + "    Node(int data) {\n"
                        + "        this.data = data;\n"
                        + "    }\n"
                        + "}\n\n"
                        + "public Node insertAtEnd(Node head, int value) {\n"
                        + "    if (head == null) {\n"
                        + "        return new Node(value);\n"
                        + "    }\n"
                        + "    Node tail = getTail(head);\n"
                        + "    tail.next = new Node(value);\n"
                        + "    return head;\n"
                        + "}\n\n"
                        + "private Node getTail(Node node) {\n"
                        + "    while (node.next != null) {\n"
                        + "        node = node.next;\n"
                        + "    }\n"
                        + "    return node;\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "class Node {\n"
                        + "    int data;\n"
                        + "    Node next;\n\n"
                        + "    Node(int data) {\n"
                        + "        this.data = data;\n"
                        + "    }\n"
                        + "}\n\n"
                        + "public Node insertAtEndRecursive(Node head, int value) {\n"
                        + "    if (head == null) {\n"
                        + "        return new Node(value);\n"
                        + "    }\n"
                        + "    head.next = insertAtEndRecursive(head.next, value);\n"
                        + "    return head;\n"
                        + "}";
                }
                return "class Node {\n"
                    + "    int data;\n"
                    + "    Node next;\n\n"
                    + "    Node(int data) {\n"
                    + "        this.data = data;\n"
                    + "    }\n"
                    + "}\n\n"
                    + "public void insertAtEnd(Node head, int value) {\n"
                    + "    Node current = head;\n"
                    + "    while (current.next != null) {\n"
                    + "        current = current.next;\n"
                    + "    }\n"
                    + "    current.next = new Node(value);\n"
                    + "}";
            case "traversing_linked_list":
            case "print_linked_list":
                if ("Functions".equals(style)) {
                    return "import java.util.Scanner;\n"
                        + "class Node {\n"
                        + "    int data;\n"
                        + "    Node next;\n"
                        + "    Node(int data) { this.data = data; }\n"
                        + "}\n"
                        + "class Main {\n"
                        + "    static void printList(Node ptr) {\n"
                        + "        while(ptr != null) {\n"
                        + "            System.out.print(ptr.data + \" \");\n"
                        + "            ptr = ptr.next;\n"
                        + "        }\n"
                        + "    }\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Scanner sc = new Scanner(System.in);\n"
                        + "        int n = sc.nextInt();\n"
                        + "        Node head = null, temp = null;\n"
                        + "        for(int i = 0; i < n; i++) {\n"
                        + "            Node newNode = new Node(sc.nextInt());\n"
                        + "            if(head == null) head = temp = newNode;\n"
                        + "            else { temp.next = newNode; temp = newNode; }\n"
                        + "        }\n"
                        + "        printList(head);\n"
                        + "    }\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "import java.util.Scanner;\n"
                        + "class Node {\n"
                        + "    int data;\n"
                        + "    Node next;\n"
                        + "    Node(int data) { this.data = data; }\n"
                        + "}\n"
                        + "class Main {\n"
                        + "    static void printList(Node ptr) {\n"
                        + "        if(ptr == null) return;\n"
                        + "        System.out.print(ptr.data + \" \");\n"
                        + "        printList(ptr.next);\n"
                        + "    }\n"
                        + "    public static void main(String[] args) {\n"
                        + "        Scanner sc = new Scanner(System.in);\n"
                        + "        int n = sc.nextInt();\n"
                        + "        Node head = null, temp = null;\n"
                        + "        for(int i = 0; i < n; i++) {\n"
                        + "            Node newNode = new Node(sc.nextInt());\n"
                        + "            if(head == null) head = temp = newNode;\n"
                        + "            else { temp.next = newNode; temp = newNode; }\n"
                        + "        }\n"
                        + "        printList(head);\n"
                        + "    }\n"
                        + "}";
                }
                return "import java.util.Scanner;\n"
                    + "class Node {\n"
                    + "    int data;\n"
                    + "    Node next;\n"
                    + "    Node(int data) { this.data = data; this.next = null; }\n"
                    + "}\n"
                    + "class Main {\n"
                    + "    public static void main(String[] args) {\n"
                    + "        Scanner sc = new Scanner(System.in);\n"
                    + "        int n = sc.nextInt();\n"
                    + "        Node head = null, temp = null;\n"
                    + "        for(int i = 0; i < n; i++) {\n"
                    + "            int val = sc.nextInt();\n"
                    + "            Node newNode = new Node(val);\n"
                    + "            if(head == null) head = temp = newNode;\n"
                    + "            else { temp.next = newNode; temp = newNode; }\n"
                    + "        }\n"
                    + "        Node ptr = head;\n"
                    + "        while(ptr != null) {\n"
                    + "            System.out.print(ptr.data + \" \");\n"
                    + "            ptr = ptr.next;\n"
                    + "        }\n"
                    + "    }\n"
                    + "}";
            case "count_nodes_linked_list":
                return getJavaCountNodesLinkedList(style);
            case "search_in_linked_list":
                return getJavaSearchInLinkedList(style);
            case "insert_at_beginning_linked_list":
                return getJavaInsertAtBeginningLinkedList(style);
            case "insert_after_given_node_linked_list":
                return getJavaInsertAfterGivenNodeLinkedList(style);
            case "insert_into_sorted_linked_list":
                return getJavaInsertIntoSortedLinkedList(style);
            case "delete_node_given_location_linked_list":
                return getJavaDeleteNodeGivenLocationLinkedList(style);
            case "delete_node_by_item_linked_list":
                return getJavaDeleteNodeByItemLinkedList(style);
            case "traverse_circular_header_list":
                return getJavaTraverseCircularHeaderList(style);
            case "search_circular_header_list":
                return getJavaSearchCircularHeaderList(style);
            case "delete_circular_header_list":
                return getJavaDeleteCircularHeaderList(style);
            case "tree_traversal":
                if ("Functions".equals(style)) {
                    return "class Node {\n"
                        + "    int data;\n"
                        + "    Node left;\n"
                        + "    Node right;\n"
                        + "}\n\n"
                        + "public void preorder(Node root) {\n"
                        + "    visit(root);\n"
                        + "}\n\n"
                        + "private void visit(Node node) {\n"
                        + "    if (node == null) {\n"
                        + "        return;\n"
                        + "    }\n"
                        + "    System.out.print(node.data + \" \");\n"
                        + "    visit(node.left);\n"
                        + "    visit(node.right);\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "public void inorder(Node root) {\n"
                        + "    if (root == null) {\n"
                        + "        return;\n"
                        + "    }\n"
                        + "    inorder(root.left);\n"
                        + "    System.out.print(root.data + \" \");\n"
                        + "    inorder(root.right);\n"
                        + "}";
                }
                return "public void inorderIterative(Node root) {\n"
                    + "    Stack<Node> stack = new Stack<>();\n"
                    + "    Node current = root;\n\n"
                    + "    while (current != null || !stack.isEmpty()) {\n"
                    + "        while (current != null) {\n"
                    + "            stack.push(current);\n"
                    + "            current = current.left;\n"
                    + "        }\n"
                    + "        current = stack.pop();\n"
                    + "        System.out.print(current.data + \" \");\n"
                    + "        current = current.right;\n"
                    + "    }\n"
                    + "}";
            case "graph_traversal":
                if ("Functions".equals(style)) {
                    return "public void bfs(int start, List<List<Integer>> graph) {\n"
                        + "    boolean[] visited = new boolean[graph.size()];\n"
                        + "    Queue<Integer> queue = new LinkedList<>();\n"
                        + "    markVisited(start, visited, queue);\n\n"
                        + "    while (!queue.isEmpty()) {\n"
                        + "        int node = queue.poll();\n"
                        + "        System.out.print(node + \" \");\n"
                        + "        addNeighbors(node, graph, visited, queue);\n"
                        + "    }\n"
                        + "}\n\n"
                        + "private void markVisited(int node, boolean[] visited, Queue<Integer> queue) {\n"
                        + "    visited[node] = true;\n"
                        + "    queue.offer(node);\n"
                        + "}\n\n"
                        + "private void addNeighbors(int node, List<List<Integer>> graph, boolean[] visited, Queue<Integer> queue) {\n"
                        + "    for (int next : graph.get(node)) {\n"
                        + "        if (!visited[next]) {\n"
                        + "            markVisited(next, visited, queue);\n"
                        + "        }\n"
                        + "    }\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "public void dfs(int node, List<List<Integer>> graph, boolean[] visited) {\n"
                        + "    visited[node] = true;\n"
                        + "    System.out.print(node + \" \");\n"
                        + "    for (int next : graph.get(node)) {\n"
                        + "        if (!visited[next]) {\n"
                        + "            dfs(next, graph, visited);\n"
                        + "        }\n"
                        + "    }\n"
                        + "}";
                }
                return "public void bfs(int start, List<List<Integer>> graph) {\n"
                    + "    boolean[] visited = new boolean[graph.size()];\n"
                    + "    Queue<Integer> queue = new LinkedList<>();\n"
                    + "    queue.offer(start);\n"
                    + "    visited[start] = true;\n\n"
                    + "    while (!queue.isEmpty()) {\n"
                    + "        int node = queue.poll();\n"
                    + "        System.out.print(node + \" \");\n"
                    + "        for (int next : graph.get(node)) {\n"
                    + "            if (!visited[next]) {\n"
                    + "                visited[next] = true;\n"
                    + "                queue.offer(next);\n"
                        + "            }\n"
                        + "        }\n"
                        + "    }\n"
                        + "}";
            case "warshall_algorithm":
                return getJavaWarshallAlgorithm(style);
            case "floyd_algorithm":
                return getJavaFloydAlgorithm(style);
            case "find_edge_graph":
                return getJavaFindEdgeGraph(style);
            case "insert_node_graph":
                return getJavaInsertNodeGraph(style);
            case "insert_edge_graph":
                return getJavaInsertEdgeGraph(style);
            case "delete_edge_graph":
                return getJavaDeleteEdgeGraph(style);
            case "delete_vertex_graph":
                return getJavaDeleteVertexGraph(style);
            case "breadth_first_search":
                return getJavaBreadthFirstSearch(style);
            case "depth_first_search":
                return getJavaDepthFirstSearch(style);
            case "topological_sort":
                return getJavaTopologicalSort(style);
            default:
                return "// Java code for this topic will be added here.";
        }
    }

    private static String getJavaCountNodesLinkedList(String style) {
        if ("Functions".equals(style)) {
            return "import java.util.Scanner;\n"
                + "class Node {\n"
                + "    int data;\n"
                + "    Node next;\n"
                + "    Node(int data) {\n"
                + "        this.data = data;\n"
                + "    }\n"
                + "}\n"
                + "class Main {\n"
                + "    static int count(Node ptr) {\n"
                + "        int num = 0;\n"
                + "        while(ptr != null) {\n"
                + "            num++;\n"
                + "            ptr = ptr.next;\n"
                + "        }\n"
                + "        return num;\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        int n = sc.nextInt();\n"
                + "        Node head = null, temp = null;\n"
                + "        for(int i = 0; i < n; i++) {\n"
                + "            Node newNode = new Node(sc.nextInt());\n"
                + "            if(head == null)\n"
                + "                head = temp = newNode;\n"
                + "            else {\n"
                + "                temp.next = newNode;\n"
                + "                temp = newNode;\n"
                + "            }\n"
                + "        }\n"
                + "        int num = count(head);\n"
                + "        System.out.println(\"Number of nodes: \" + num);\n"
                + "    }\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return "import java.util.Scanner;\n"
                + "class Node {\n"
                + "    int data;\n"
                + "    Node next;\n"
                + "    Node(int data) {\n"
                + "        this.data = data;\n"
                + "    }\n"
                + "}\n"
                + "class Main {\n"
                + "    static int count(Node ptr) {\n"
                + "        if(ptr == null)\n"
                + "            return 0;\n"
                + "        return 1 + count(ptr.next);\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        int n = sc.nextInt();\n"
                + "        Node head = null, temp = null;\n"
                + "        for(int i = 0; i < n; i++) {\n"
                + "            Node newNode = new Node(sc.nextInt());\n"
                + "            if(head == null)\n"
                + "                head = temp = newNode;\n"
                + "            else {\n"
                + "                temp.next = newNode;\n"
                + "                temp = newNode;\n"
                + "            }\n"
                + "        }\n"
                + "        int num = count(head);\n"
                + "        System.out.println(\"Number of nodes: \" + num);\n"
                + "    }\n"
                + "}";
        }
        return "import java.util.Scanner;\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node next;\n"
            + "    Node(int data) {\n"
            + "        this.data = data;\n"
            + "    }\n"
            + "}\n"
            + "class Main {\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        int n = sc.nextInt();\n"
            + "        Node head = null, temp = null;\n"
            + "        for(int i = 0; i < n; i++) {\n"
            + "            Node newNode = new Node(sc.nextInt());\n"
            + "            if(head == null)\n"
            + "                head = temp = newNode;\n"
            + "            else {\n"
            + "                temp.next = newNode;\n"
            + "                temp = newNode;\n"
            + "            }\n"
            + "        }\n"
            + "        int count = 0;\n"
            + "        Node ptr = head;\n"
            + "        while(ptr != null) {\n"
            + "            count++;\n"
            + "            ptr = ptr.next;\n"
            + "        }\n"
            + "        System.out.println(\"Number of nodes: \" + count);\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaStackBasics(String style) {
        if ("Functions".equals(style)) {
            return "import java.util.*;\n"
                + "public class StackOps {\n"
                + "    static int push(int[] stack, int top, int max, int item) {\n"
                + "        if (top == max) {\n"
                + "            System.out.println(\"OVERFLOW\");\n"
                + "            return top;\n"
                + "        }\n"
                + "        top++;\n"
                + "        stack[top] = item;\n"
                + "        return top;\n"
                + "    }\n"
                + "    static int pop(int[] stack, int top) {\n"
                + "        if (top == 0) {\n"
                + "            System.out.println(\"UNDERFLOW\");\n"
                + "            return top;\n"
                + "        }\n"
                + "        System.out.println(\"Popped: \" + stack[top]);\n"
                + "        return top - 1;\n"
                + "    }\n"
                + "    static void display(int[] stack, int top) {\n"
                + "        if (top == 0) {\n"
                + "            System.out.println(\"Stack is empty\");\n"
                + "            return;\n"
                + "        }\n"
                + "        System.out.print(\"Stack elements: \");\n"
                + "        for (int i = top; i >= 1; i--) {\n"
                + "            System.out.print(stack[i] + \" \");\n"
                + "        }\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter stack capacity: \");\n"
                + "        int max = sc.nextInt();\n"
                + "        int[] stack = new int[max + 1];\n"
                + "        int top = 0;\n"
                + "        System.out.print(\"Enter number of items to push: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        for (int i = 1; i <= n; i++) {\n"
                + "            System.out.print(\"Enter item \" + i + \": \");\n"
                + "            int item = sc.nextInt();\n"
                + "            top = push(stack, top, max, item);\n"
                + "        }\n"
                + "        display(stack, top);\n"
                + "        top = pop(stack, top);\n"
                + "        display(stack, top);\n"
                + "    }\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return "import java.util.*;\n"
                + "public class StackOps {\n"
                + "    public static void main(String[] args) {\n"
                + "        System.out.println(\"Recursion not applicable for basic PUSH and POP operations\");\n"
                + "    }\n"
                + "}";
        }
        return "import java.util.*;\n"
            + "public class StackOps {\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        System.out.print(\"Enter stack capacity: \");\n"
            + "        int max = sc.nextInt();\n"
            + "        int[] stack = new int[max + 1];\n"
            + "        int top = 0;\n"
            + "        System.out.print(\"Enter number of items to push: \");\n"
            + "        int n = sc.nextInt();\n"
            + "        for (int i = 1; i <= n; i++) {\n"
            + "            System.out.print(\"Enter item \" + i + \": \");\n"
            + "            int item = sc.nextInt();\n"
            + "            if (top == max) {\n"
            + "                System.out.println(\"OVERFLOW\");\n"
            + "            } else {\n"
            + "                top++;\n"
            + "                stack[top] = item;\n"
            + "                System.out.println(\"Item pushed\");\n"
            + "            }\n"
            + "        }\n"
            + "        if (top == 0) {\n"
            + "            System.out.println(\"Stack is empty\");\n"
            + "        } else {\n"
            + "            System.out.print(\"Stack elements: \");\n"
            + "            for (int i = top; i >= 1; i--) {\n"
            + "                System.out.print(stack[i] + \" \");\n"
            + "            }\n"
            + "            System.out.println();\n"
            + "        }\n"
            + "        if (top == 0) {\n"
            + "            System.out.println(\"UNDERFLOW\");\n"
            + "        } else {\n"
            + "            int popItem = stack[top];\n"
            + "            top--;\n"
            + "            System.out.println(\"Popped: \" + popItem);\n"
            + "        }\n"
            + "        if (top == 0) {\n"
            + "            System.out.println(\"Stack is empty after pop\");\n"
            + "        } else {\n"
            + "            System.out.print(\"Stack after pop: \");\n"
            + "            for (int i = top; i >= 1; i--) {\n"
            + "                System.out.print(stack[i] + \" \");\n"
            + "            }\n"
            + "            System.out.println();\n"
            + "        }\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaLinkedStackPush(String style) {
        if ("Functions".equals(style)) {
            return "import java.util.*;\n"
                + "class Node {\n"
                + "    int data;\n"
                + "    Node next;\n"
                + "    Node(int data) { this.data = data; this.next = null; }\n"
                + "}\n"
                + "public class LinkedStackPush {\n"
                + "    static Node push(Node top, int item) {\n"
                + "        Node newNode = new Node(item);\n"
                + "        newNode.next = top;\n"
                + "        top = newNode;\n"
                + "        return top;\n"
                + "    }\n"
                + "    static void display(Node top) {\n"
                + "        if (top == null) {\n"
                + "            System.out.println(\"Linked stack is empty\");\n"
                + "            return;\n"
                + "        }\n"
                + "        System.out.print(\"Linked stack elements: \");\n"
                + "        for (Node ptr = top; ptr != null; ptr = ptr.next) {\n"
                + "            System.out.print(ptr.data + \" \");\n"
                + "        }\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        Node top = null;\n"
                + "        System.out.print(\"Enter number of items to push: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        for (int i = 1; i <= n; i++) {\n"
                + "            System.out.print(\"Enter item \" + i + \": \");\n"
                + "            int item = sc.nextInt();\n"
                + "            top = push(top, item);\n"
                + "        }\n"
                + "        System.out.println(\"Items pushed to linked stack\");\n"
                + "        display(top);\n"
                + "    }\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return "import java.util.*;\n"
                + "public class LinkedStackPush {\n"
                + "    public static void main(String[] args) {\n"
                + "        System.out.println(\"Recursion not applicable for linked stack PUSH\");\n"
                + "    }\n"
                + "}";
        }
        return "import java.util.*;\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node next;\n"
            + "    Node(int data) { this.data = data; this.next = null; }\n"
            + "}\n"
            + "public class LinkedStackPush {\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        Node top = null;\n"
            + "        System.out.print(\"Enter number of items to push: \");\n"
            + "        int n = sc.nextInt();\n"
            + "        for (int i = 1; i <= n; i++) {\n"
            + "            System.out.print(\"Enter item \" + i + \": \");\n"
            + "            int item = sc.nextInt();\n"
            + "            Node newNode = new Node(item);\n"
            + "            newNode.next = top;\n"
            + "            top = newNode;\n"
            + "            System.out.println(\"Item pushed to linked stack\");\n"
            + "        }\n"
            + "        if (top == null) {\n"
            + "            System.out.println(\"Linked stack is empty\");\n"
            + "        } else {\n"
            + "            System.out.print(\"Linked stack elements: \");\n"
            + "            for (Node ptr = top; ptr != null; ptr = ptr.next) {\n"
            + "                System.out.print(ptr.data + \" \");\n"
            + "            }\n"
            + "            System.out.println();\n"
            + "        }\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaLinkedStackPop(String style) {
        if ("Functions".equals(style)) {
            return "import java.util.*;\n"
                + "class Node {\n"
                + "    int data;\n"
                + "    Node next;\n"
                + "    Node(int data) { this.data = data; this.next = null; }\n"
                + "}\n"
                + "public class LinkedStackPop {\n"
                + "    static Node pop(Node top) {\n"
                + "        if(top == null) {\n"
                + "            System.out.println(\"UNDERFLOW\");\n"
                + "            return null;\n"
                + "        }\n"
                + "        System.out.println(\"Popped: \" + top.data);\n"
                + "        return top.next;\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        Node top = null;\n"
                + "        System.out.print(\"Enter number of items already in the stack: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        for(int i = 0; i < n; i++) {\n"
                + "            System.out.print(\"Enter stack item \" + (i + 1) + \": \");\n"
                + "            int val = sc.nextInt();\n"
                + "            Node temp = new Node(val);\n"
                + "            temp.next = top;\n"
                + "            top = temp;\n"
                + "        }\n"
                + "        System.out.print(\"Initial linked stack: \");\n"
                + "        for (Node ptr = top; ptr != null; ptr = ptr.next) {\n"
                + "            System.out.print(ptr.data + \" \");\n"
                + "        }\n"
                + "        System.out.println();\n"
                + "        top = pop(top);\n"
                + "    }\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return "import java.util.*;\n"
                + "public class LinkedStackPop {\n"
                + "    public static void main(String[] args) {\n"
                + "        System.out.println(\"Recursion not applicable for linked stack POP\");\n"
                + "    }\n"
                + "}";
        }
        return "import java.util.*;\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node next;\n"
            + "    Node(int data) { this.data = data; this.next = null; }\n"
            + "}\n"
            + "public class LinkedStackPop {\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        Node top = null;\n"
            + "        System.out.print(\"Enter number of items already in the stack: \");\n"
            + "        int n = sc.nextInt();\n"
            + "        for(int i = 0; i < n; i++) {\n"
            + "            System.out.print(\"Enter stack item \" + (i + 1) + \": \");\n"
            + "            int val = sc.nextInt();\n"
            + "            Node temp = new Node(val);\n"
            + "            temp.next = top;\n"
            + "            top = temp;\n"
            + "        }\n"
            + "        if (top == null) {\n"
            + "            System.out.println(\"Linked stack is empty\");\n"
            + "        } else {\n"
            + "            System.out.print(\"Initial linked stack: \");\n"
            + "            for (Node ptr = top; ptr != null; ptr = ptr.next) {\n"
            + "                System.out.print(ptr.data + \" \");\n"
            + "            }\n"
            + "            System.out.println();\n"
            + "        }\n"
            + "        if(top == null) {\n"
            + "            System.out.println(\"UNDERFLOW\");\n"
            + "        } else {\n"
            + "            int item = top.data;\n"
            + "            top = top.next;\n"
            + "            System.out.println(\"Popped: \" + item);\n"
            + "        }\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaQuicksort(String style) {
        String shared = "import java.util.*;\n"
            + "public class QuickSort {\n"
            + "    static int partition(int[] A, int low, int high) {\n"
            + "        int pivot = A[low];\n"
            + "        int i = low + 1;\n"
            + "        int j = high;\n"
            + "        while (i <= j) {\n"
            + "            while (i <= high && A[i] <= pivot) i++;\n"
            + "            while (A[j] > pivot) j--;\n"
            + "            if (i < j) {\n"
            + "                int temp = A[i];\n"
            + "                A[i] = A[j];\n"
            + "                A[j] = temp;\n"
            + "            }\n"
            + "        }\n"
            + "        int temp = A[low];\n"
            + "        A[low] = A[j];\n"
            + "        A[j] = temp;\n"
            + "        return j;\n"
            + "    }\n";
        if ("Iterative".equals(style)) {
            return shared
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        int n = sc.nextInt();\n"
                + "        int[] A = new int[n];\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            A[i] = sc.nextInt();\n"
                + "        }\n"
                + "        int[] lower = new int[n];\n"
                + "        int[] upper = new int[n];\n"
                + "        int top = -1;\n"
                + "        if (n > 1) {\n"
                + "            top++;\n"
                + "            lower[top] = 0;\n"
                + "            upper[top] = n - 1;\n"
                + "        }\n"
                + "        while (top >= 0) {\n"
                + "            int beg = lower[top];\n"
                + "            int end = upper[top];\n"
                + "            top--;\n"
                + "            int loc = partition(A, beg, end);\n"
                + "            if (beg < loc - 1) {\n"
                + "                top++;\n"
                + "                lower[top] = beg;\n"
                + "                upper[top] = loc - 1;\n"
                + "            }\n"
                + "            if (loc + 1 < end) {\n"
                + "                top++;\n"
                + "                lower[top] = loc + 1;\n"
                + "                upper[top] = end;\n"
                + "            }\n"
                + "        }\n"
                + "        for (int x : A)\n"
                + "            System.out.print(x + \" \");\n"
                + "    }\n"
                + "}";
        }
        if ("Functions".equals(style)) {
            return shared
                + "    static void quickSort(int[] A, int low, int high) {\n"
                + "        if (low < high) {\n"
                + "            int loc = partition(A, low, high);\n"
                + "            quickSort(A, low, loc - 1);\n"
                + "            quickSort(A, loc + 1, high);\n"
                + "        }\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        int n = sc.nextInt();\n"
                + "        int[] A = new int[n];\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            A[i] = sc.nextInt();\n"
                + "        }\n"
                + "        quickSort(A, 0, n - 1);\n"
                + "        for (int x : A)\n"
                + "            System.out.print(x + \" \");\n"
                + "    }\n"
                + "}";
        }
        return shared
            + "    static void quickSort(int[] A, int low, int high) {\n"
            + "        if (low >= high)\n"
            + "            return;\n"
            + "        int loc = partition(A, low, high);\n"
            + "        quickSort(A, low, loc - 1);\n"
            + "        quickSort(A, loc + 1, high);\n"
            + "    }\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        int n = sc.nextInt();\n"
            + "        int[] A = new int[n];\n"
            + "        for (int i = 0; i < n; i++) {\n"
            + "            A[i] = sc.nextInt();\n"
            + "        }\n"
            + "        quickSort(A, 0, n - 1);\n"
            + "        for (int x : A)\n"
            + "            System.out.print(x + \" \");\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaLinearQueue(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "",
                "public class LinearQueue {",
                "    static int[] insert(int[] queue, int front, int rear, int n, int item) {",
                "        if (rear == n) {",
                "            System.out.println(\"OVERFLOW\");",
                "            return new int[]{front, rear};",
                "        }",
                "",
                "        if (front == -1) {",
                "            front = 0;",
                "            rear = 0;",
                "        } else {",
                "            rear++;",
                "        }",
                "",
                "        queue[rear] = item;",
                "        return new int[]{front, rear};",
                "    }",
                "",
                "    static int[] delete(int[] queue, int front, int rear) {",
                "        if (front == -1) {",
                "            System.out.println(\"UNDERFLOW\");",
                "            return new int[]{front, rear};",
                "        }",
                "",
                "        System.out.println(\"Deleted: \" + queue[front]);",
                "",
                "        if (front == rear) {",
                "            front = -1;",
                "            rear = -1;",
                "        } else {",
                "            front++;",
                "        }",
                "",
                "        return new int[]{front, rear};",
                "    }",
                "",
                "    public static void main(String[] args) {",
                "        Scanner sc = new Scanner(System.in);",
                "        System.out.print(\"Enter queue size: \");",
                "        int n = sc.nextInt();",
                "        int[] queue = new int[n + 1];",
                "        int front = -1, rear = -1;",
                "        System.out.print(\"Enter number of items to insert: \");",
                "        int count = sc.nextInt();",
                "        for (int i = 1; i <= count; i++) {",
                "            System.out.print(\"Enter item \" + i + \": \");",
                "            int item = sc.nextInt();",
                "            int[] res = insert(queue, front, rear, n, item);",
                "            front = res[0];",
                "            rear = res[1];",
                "        }",
                "        if (front == -1) {",
                "            System.out.println(\"Queue is empty\");",
                "        } else {",
                "            System.out.print(\"Queue elements: \");",
                "            for (int i = front; i <= rear; i++) System.out.print(queue[i] + \" \");",
                "            System.out.println();",
                "        }",
                "        res = delete(queue, front, rear);",
                "        front = res[0];",
                "        rear = res[1];",
                "    }",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "",
                "public class LinearQueue {",
                "    public static void main(String[] args) {",
                "        System.out.println(\"Recursion not applicable for queue insertion and deletion\");",
                "    }",
                "}");
        }
        return String.join("\n",
            "import java.util.*;",
            "",
            "public class LinearQueue {",
            "    public static void main(String[] args) {",
            "        Scanner sc = new Scanner(System.in);",
            "        System.out.print(\"Enter queue size: \");",
            "        int n = sc.nextInt();",
            "        int[] queue = new int[n + 1];",
            "        int front = -1, rear = -1;",
            "",
            "        System.out.print(\"Enter number of items to insert: \");",
            "        int count = sc.nextInt();",
            "        for (int i = 1; i <= count; i++) {",
            "            System.out.print(\"Enter item \" + i + \": \");",
            "            int item = sc.nextInt();",
            "            if (rear == n) {",
            "                System.out.println(\"OVERFLOW\");",
            "            } else {",
            "                if (front == -1) {",
            "                    front = 0;",
            "                    rear = 0;",
            "                } else {",
            "                    rear++;",
            "                }",
            "                queue[rear] = item;",
            "                System.out.println(\"Item inserted\");",
            "            }",
            "        }",
            "        if (front == -1) {",
            "            System.out.println(\"Queue is empty\");",
            "        } else {",
            "            System.out.print(\"Queue elements: \");",
            "            for (int i = front; i <= rear; i++) System.out.print(queue[i] + \" \");",
            "            System.out.println();",
            "        }",
            "",
            "        if (front == -1) {",
            "            System.out.println(\"UNDERFLOW\");",
            "        } else {",
            "            int deleted = queue[front];",
            "            if (front == rear) {",
            "                front = -1;",
            "                rear = -1;",
            "            } else {",
            "                front++;",
            "            }",
            "            System.out.println(\"Deleted: \" + deleted);",
            "        }",
            "    }",
            "}");
    }

    private static String getJavaCircularQueue(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "",
                "public class CircularQueue {",
                "    static int[] insert(int[] queue, int front, int rear, int n, int item) {",
                "        if ((front == 0 && rear == n - 1) || (front == rear + 1)) {",
                "            System.out.println(\"OVERFLOW\");",
                "            return new int[]{front, rear};",
                "        }",
                "",
                "        if (front == -1) {",
                "            front = 0;",
                "            rear = 0;",
                "        } else if (rear == n - 1) {",
                "            rear = 0;",
                "        } else {",
                "            rear++;",
                "        }",
                "",
                "        queue[rear] = item;",
                "        return new int[]{front, rear};",
                "    }",
                "",
                "    static int[] delete(int[] queue, int front, int rear, int n) {",
                "        if (front == -1) {",
                "            System.out.println(\"UNDERFLOW\");",
                "            return new int[]{front, rear};",
                "        }",
                "",
                "        System.out.println(\"Deleted: \" + queue[front]);",
                "",
                "        if (front == rear) {",
                "            front = -1;",
                "            rear = -1;",
                "        } else if (front == n - 1) {",
                "            front = 0;",
                "        } else {",
                "            front++;",
                "        }",
                "",
                "        return new int[]{front, rear};",
                "    }",
                "",
                "    public static void main(String[] args) {",
                "        Scanner sc = new Scanner(System.in);",
                "        System.out.print(\"Enter queue size: \");",
                "        int n = sc.nextInt();",
                "        int[] queue = new int[n];",
                "        int front = -1, rear = -1;",
                "        System.out.print(\"Enter number of items to insert: \");",
                "        int count = sc.nextInt();",
                "        for (int i = 1; i <= count; i++) {",
                "            System.out.print(\"Enter item \" + i + \": \");",
                "            int item = sc.nextInt();",
                "            int[] res = insert(queue, front, rear, n, item);",
                "            front = res[0];",
                "            rear = res[1];",
                "        }",
                "        if (front == -1) {",
                "            System.out.println(\"Circular queue is empty\");",
                "        } else {",
                "            System.out.print(\"Circular queue elements: \");",
                "            int i = front;",
                "            while (true) {",
                "                System.out.print(queue[i] + \" \");",
                "                if (i == rear) break;",
                "                i = (i + 1) % n;",
                "            }",
                "            System.out.println();",
                "        }",
                "        res = delete(queue, front, rear, n);",
                "        front = res[0];",
                "        rear = res[1];",
                "    }",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "",
                "public class CircularQueue {",
                "    public static void main(String[] args) {",
                "        System.out.println(\"Recursion not applicable for circular queue operations\");",
                "    }",
                "}");
        }
        return String.join("\n",
            "import java.util.*;",
            "",
            "public class CircularQueue {",
            "    public static void main(String[] args) {",
            "        Scanner sc = new Scanner(System.in);",
            "        System.out.print(\"Enter queue size: \");",
            "        int n = sc.nextInt();",
            "        int[] queue = new int[n + 1];",
            "        int front = -1, rear = -1;",
            "        System.out.print(\"Enter number of items to insert: \");",
            "        int count = sc.nextInt();",
            "        for (int itemIndex = 1; itemIndex <= count; itemIndex++) {",
            "            System.out.print(\"Enter item \" + itemIndex + \": \");",
            "            int item = sc.nextInt();",
            "            if ((front == 0 && rear == n - 1) || (front == rear + 1)) {",
            "                System.out.println(\"OVERFLOW\");",
            "            } else {",
            "                if (front == -1) {",
            "                    front = 0;",
            "                    rear = 0;",
            "                } else if (rear == n - 1) {",
            "                    rear = 0;",
            "                } else {",
            "                    rear++;",
            "                }",
            "                queue[rear] = item;",
            "                System.out.println(\"Item inserted\");",
            "            }",
            "        }",
            "        if (front == -1) {",
            "            System.out.println(\"Circular queue is empty\");",
            "        } else {",
            "            System.out.print(\"Circular queue elements: \");",
            "            int i = front;",
            "            while (true) {",
            "                System.out.print(queue[i] + \" \");",
            "                if (i == rear) break;",
            "                i = (i + 1) % n;",
            "            }",
            "            System.out.println();",
            "        }",
            "",
            "        if (front == -1) {",
            "            System.out.println(\"UNDERFLOW\");",
            "        } else {",
            "            int deleted = queue[front];",
            "            if (front == rear) {",
            "                front = -1;",
            "                rear = -1;",
            "            } else if (front == n - 1) {",
            "                front = 0;",
            "            } else {",
            "                front++;",
            "            }",
            "            System.out.println(\"Deleted: \" + deleted);",
            "        }",
            "    }",
            "}");
    }

    private static String getJavaLinkedQueue(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "",
                "class Node {",
                "    int data;",
                "    Node next;",
                "",
                "    Node(int data) {",
                "        this.data = data;",
                "        this.next = null;",
                "    }",
                "}",
                "",
                "public class LinkedQueue {",
                "    static Node[] insert(Node front, Node rear, int item) {",
                "        Node newNode = new Node(item);",
                "        if (front == null) {",
                "            front = rear = newNode;",
                "        } else {",
                "            rear.next = newNode;",
                "            rear = newNode;",
                "        }",
                "        return new Node[]{front, rear};",
                "    }",
                "",
                "    static Node[] delete(Node front, Node rear) {",
                "        if (front == null) {",
                "            System.out.println(\"UNDERFLOW\");",
                "            return new Node[]{front, rear};",
                "        }",
                "        System.out.println(\"Deleted: \" + front.data);",
                "        front = front.next;",
                "        if (front == null) {",
                "            rear = null;",
                "        }",
                "        return new Node[]{front, rear};",
                "    }",
                "",
                "    public static void main(String[] args) {",
                "        Scanner sc = new Scanner(System.in);",
                "        Node front = null, rear = null;",
                "        System.out.print(\"Enter number of items to insert: \");",
                "        int count = sc.nextInt();",
                "        for (int i = 1; i <= count; i++) {",
                "            System.out.print(\"Enter item \" + i + \": \");",
                "            int item = sc.nextInt();",
                "            Node[] res = insert(front, rear, item);",
                "            front = res[0];",
                "            rear = res[1];",
                "        }",
                "        if (front == null) {",
                "            System.out.println(\"Linked queue is empty\");",
                "        } else {",
                "            System.out.print(\"Linked queue elements: \");",
                "            for (Node ptr = front; ptr != null; ptr = ptr.next) System.out.print(ptr.data + \" \");",
                "            System.out.println();",
                "        }",
                "        res = delete(front, rear);",
                "        front = res[0];",
                "        rear = res[1];",
                "    }",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "",
                "public class LinkedQueue {",
                "    public static void main(String[] args) {",
                "        System.out.println(\"Recursion not applicable for linked queue operations\");",
                "    }",
                "}");
        }
        return String.join("\n",
            "import java.util.*;",
            "",
            "class Node {",
            "    int data;",
            "    Node next;",
            "",
            "    Node(int data) {",
            "        this.data = data;",
            "        this.next = null;",
            "    }",
            "}",
            "",
            "public class LinkedQueue {",
            "    public static void main(String[] args) {",
            "        Scanner sc = new Scanner(System.in);",
            "        Node front = null, rear = null;",
            "        System.out.print(\"Enter number of items to insert: \");",
            "        int count = sc.nextInt();",
            "        for (int i = 1; i <= count; i++) {",
            "            System.out.print(\"Enter item \" + i + \": \");",
            "            int item = sc.nextInt();",
            "            Node newNode = new Node(item);",
            "",
            "            if (front == null) {",
            "                front = rear = newNode;",
            "            } else {",
            "                rear.next = newNode;",
            "                rear = newNode;",
            "            }",
            "",
            "            System.out.println(\"Item inserted\");",
            "        }",
            "        if (front == null) {",
            "            System.out.println(\"Linked queue is empty\");",
            "        } else {",
            "            System.out.print(\"Linked queue elements: \");",
            "            for (Node ptr = front; ptr != null; ptr = ptr.next) System.out.print(ptr.data + \" \");",
            "            System.out.println();",
            "        }",
            "",
            "        if (front == null) {",
            "            System.out.println(\"UNDERFLOW\");",
            "        } else {",
            "            int deleted = front.data;",
            "            front = front.next;",
            "            System.out.println(\"Deleted: \" + deleted);",
            "        }",
            "    }",
            "}");
    }

    private static String getJavaPriorityQueueArray(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "",
                "public class PriorityQueueArray {",
                "    static int insert(int[] data, int[] priority, int size, int item, int p) {",
                "        int i;",
                "        for (i = size - 1; i >= 0 && priority[i] > p; i--) {",
                "            data[i + 1] = data[i];",
                "            priority[i + 1] = priority[i];",
                "        }",
                "        data[i + 1] = item;",
                "        priority[i + 1] = p;",
                "        return size + 1;",
                "    }",
                "",
                "    static int delete(int[] data, int[] priority, int size) {",
                "        if (size == 0) {",
                "            System.out.println(\"UNDERFLOW\");",
                "            return size;",
                "        }",
                "        System.out.println(\"Deleted: \" + data[0]);",
                "        for (int i = 0; i < size - 1; i++) {",
                "            data[i] = data[i + 1];",
                "            priority[i] = priority[i + 1];",
                "        }",
                "        return size - 1;",
                "    }",
                "",
                "    public static void main(String[] args) {",
                "        Scanner sc = new Scanner(System.in);",
                "        System.out.print(\"Enter size of queue: \");",
                "        int n = sc.nextInt();",
                "        int[] data = new int[n];",
                "        int[] priority = new int[n];",
                "        int size = 0;",
                "        System.out.print(\"Enter item: \");",
                "        int item = sc.nextInt();",
                "        System.out.print(\"Enter priority: \");",
                "        int p = sc.nextInt();",
                "        size = insert(data, priority, size, item, p);",
                "        System.out.println(\"Item inserted\");",
                "        size = delete(data, priority, size);",
                "    }",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "",
                "public class PriorityQueueArray {",
                "    public static void main(String[] args) {",
                "        System.out.println(\"Recursion not applicable for priority queue (array)\");",
                "    }",
                "}");
        }
        return String.join("\n",
            "import java.util.*;",
            "",
            "public class PriorityQueueArray {",
            "    public static void main(String[] args) {",
            "        Scanner sc = new Scanner(System.in);",
            "        System.out.print(\"Enter size of queue: \");",
            "        int n = sc.nextInt();",
            "        int[] data = new int[n];",
            "        int[] priority = new int[n];",
            "        int size = 0;",
            "        System.out.print(\"Enter item: \");",
            "        int item = sc.nextInt();",
            "        System.out.print(\"Enter priority: \");",
            "        int p = sc.nextInt();",
            "        int i;",
            "        for (i = size - 1; i >= 0 && priority[i] > p; i--) {",
            "            data[i + 1] = data[i];",
            "            priority[i + 1] = priority[i];",
            "        }",
            "        data[i + 1] = item;",
            "        priority[i + 1] = p;",
            "        size++;",
            "        System.out.println(\"Item inserted\");",
            "        if (size == 0) {",
            "            System.out.println(\"UNDERFLOW\");",
            "        } else {",
            "            int deleted = data[0];",
            "            for (int j = 0; j < size - 1; j++) {",
            "                data[j] = data[j + 1];",
            "                priority[j] = priority[j + 1];",
            "            }",
            "            size--;",
            "            System.out.println(\"Deleted: \" + deleted);",
            "        }",
            "    }",
            "}");
    }

    private static String getJavaPriorityQueue2d(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "",
                "public class PriorityQueue2D {",
                "    static void insert(int[][] queue, int[] front, int[] rear, int n, int m, int item) {",
                "        if (rear[m] == n - 1) {",
                "            System.out.println(\"OVERFLOW\");",
                "            return;",
                "        }",
                "        if (front[m] == -1) {",
                "            front[m] = 0;",
                "            rear[m] = 0;",
                "        } else {",
                "            rear[m]++;",
                "        }",
                "        queue[m][rear[m]] = item;",
                "    }",
                "",
                "    static void delete(int[][] queue, int[] front, int[] rear, int p) {",
                "        int k = -1;",
                "        for (int i = 0; i < p; i++) {",
                "            if (front[i] != -1) {",
                "                k = i;",
                "                break;",
                "            }",
                "        }",
                "        if (k == -1) {",
                "            System.out.println(\"UNDERFLOW\");",
                "            return;",
                "        }",
                "        System.out.println(\"Deleted: \" + queue[k][front[k]]);",
                "        if (front[k] == rear[k]) {",
                "            front[k] = -1;",
                "            rear[k] = -1;",
                "        } else {",
                "            front[k]++;",
                "        }",
                "    }",
                "",
                "    public static void main(String[] args) {",
                "        Scanner sc = new Scanner(System.in);",
                "        System.out.print(\"Enter number of priority levels: \");",
                "        int p = sc.nextInt();",
                "        System.out.print(\"Enter size of each queue: \");",
                "        int n = sc.nextInt();",
                "        int[][] queue = new int[p][n];",
                "        int[] front = new int[p];",
                "        int[] rear = new int[p];",
                "        Arrays.fill(front, -1);",
                "        Arrays.fill(rear, -1);",
                "        System.out.print(\"Enter item: \");",
                "        int item = sc.nextInt();",
                "        System.out.print(\"Enter priority level: \");",
                "        int m = sc.nextInt();",
                "        insert(queue, front, rear, n, m, item);",
                "        System.out.println(\"Item inserted\");",
                "        delete(queue, front, rear, p);",
                "    }",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "",
                "public class PriorityQueue2D {",
                "    public static void main(String[] args) {",
                "        System.out.println(\"Recursion not applicable for priority queue (2D array)\");",
                "    }",
                "}");
        }
        return String.join("\n",
            "import java.util.*;",
            "",
            "public class PriorityQueue2D {",
            "    public static void main(String[] args) {",
            "        Scanner sc = new Scanner(System.in);",
            "        System.out.print(\"Enter number of priority levels: \");",
            "        int p = sc.nextInt();",
            "        System.out.print(\"Enter size of each queue: \");",
            "        int n = sc.nextInt();",
            "        int[][] queue = new int[p][n];",
            "        int[] front = new int[p];",
            "        int[] rear = new int[p];",
            "        Arrays.fill(front, -1);",
            "        Arrays.fill(rear, -1);",
            "        System.out.print(\"Enter item: \");",
            "        int item = sc.nextInt();",
            "        System.out.print(\"Enter priority level (0 to \" + (p - 1) + \"): \");",
            "        int m = sc.nextInt();",
            "        if (rear[m] == n - 1) {",
            "            System.out.println(\"OVERFLOW\");",
            "        } else {",
            "            if (front[m] == -1) {",
            "                front[m] = 0;",
            "                rear[m] = 0;",
            "            } else {",
            "                rear[m]++;",
            "            }",
            "            queue[m][rear[m]] = item;",
            "            System.out.println(\"Item inserted\");",
            "        }",
            "        int k = -1;",
            "        for (int i = 0; i < p; i++) {",
            "            if (front[i] != -1) {",
            "                k = i;",
            "                break;",
            "            }",
            "        }",
            "        if (k == -1) {",
            "            System.out.println(\"UNDERFLOW\");",
            "        } else {",
            "            int deleted = queue[k][front[k]];",
            "            if (front[k] == rear[k]) {",
            "                front[k] = -1;",
            "                rear[k] = -1;",
            "            } else {",
            "                front[k]++;",
            "            }",
            "            System.out.println(\"Deleted: \" + deleted);",
            "        }",
            "    }",
            "}");
    }

    private static String getJavaPreorderTraversal(String style) {
        String nodeBlock = "import java.util.*;\n\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node left, right;\n"
            + "    Node(int data) {\n"
            + "        this.data = data;\n"
            + "        left = right = null;\n"
            + "    }\n"
            + "}\n\n";
        if ("Functions".equals(style)) {
            return nodeBlock
                + "public class Main {\n"
                + "    static void preorder(Node ptr) {\n"
                + "        if (ptr == null) return;\n"
                + "        System.out.print(ptr.data + \" \");\n"
                + "        preorder(ptr.left);\n"
                + "        preorder(ptr.right);\n"
                + "    }\n\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter number of nodes: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        Node[] nodes = new Node[n];\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            System.out.print(\"Enter value of node \" + i + \": \");\n"
                + "            nodes[i] = new Node(sc.nextInt());\n"
                + "        }\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            System.out.print(\"Enter left and right child index of node \" + i + \" (-1 if none): \");\n"
                + "            int l = sc.nextInt();\n"
                + "            int r = sc.nextInt();\n"
                + "            if (l != -1) nodes[i].left = nodes[l];\n"
                + "            if (r != -1) nodes[i].right = nodes[r];\n"
                + "        }\n"
                + "        preorder(nodes[0]);\n"
                + "    }\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return nodeBlock
                + "public class Main {\n"
                + "    static void preorderRec(Node root) {\n"
                + "        if (root == null) return;\n"
                + "        System.out.print(root.data + \" \");\n"
                + "        preorderRec(root.left);\n"
                + "        preorderRec(root.right);\n"
                + "    }\n\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter number of nodes: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        Node[] nodes = new Node[n];\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            System.out.print(\"Enter value of node \" + i + \": \");\n"
                + "            nodes[i] = new Node(sc.nextInt());\n"
                + "        }\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            System.out.print(\"Enter left and right child index of node \" + i + \" (-1 if none): \");\n"
                + "            int l = sc.nextInt();\n"
                + "            int r = sc.nextInt();\n"
                + "            if (l != -1) nodes[i].left = nodes[l];\n"
                + "            if (r != -1) nodes[i].right = nodes[r];\n"
                + "        }\n"
                + "        preorderRec(nodes[0]);\n"
                + "    }\n"
                + "}";
        }
        return nodeBlock
            + "public class Main {\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        System.out.print(\"Enter number of nodes: \");\n"
            + "        int n = sc.nextInt();\n"
            + "        Node[] nodes = new Node[n];\n"
            + "        for (int i = 0; i < n; i++) {\n"
            + "            System.out.print(\"Enter value of node \" + i + \": \");\n"
            + "            nodes[i] = new Node(sc.nextInt());\n"
            + "        }\n"
            + "        for (int i = 0; i < n; i++) {\n"
            + "            System.out.print(\"Enter left and right child index of node \" + i + \" (-1 if none): \");\n"
            + "            int l = sc.nextInt();\n"
            + "            int r = sc.nextInt();\n"
            + "            if (l != -1) nodes[i].left = nodes[l];\n"
            + "            if (r != -1) nodes[i].right = nodes[r];\n"
            + "        }\n"
            + "        Stack<Node> stack = new Stack<>();\n"
            + "        Node ptr = nodes[0];\n"
            + "        stack.push(null);\n"
            + "        while (ptr != null) {\n"
            + "            System.out.print(ptr.data + \" \");\n"
            + "            if (ptr.right != null) {\n"
            + "                stack.push(ptr.right);\n"
            + "            }\n"
            + "            if (ptr.left != null) {\n"
            + "                ptr = ptr.left;\n"
            + "            } else {\n"
            + "                ptr = stack.pop();\n"
            + "            }\n"
            + "        }\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaPostorderTraversal(String style) {
        String nodeBlock = "import java.util.*;\n\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node left, right;\n"
            + "    Node(int data) {\n"
            + "        this.data = data;\n"
            + "        left = right = null;\n"
            + "    }\n"
            + "}\n\n";
        if ("Functions".equals(style)) {
            return nodeBlock
                + "public class Main {\n"
                + "    static void postorder(Node ptr) {\n"
                + "        if (ptr == null) return;\n"
                + "        postorder(ptr.left);\n"
                + "        postorder(ptr.right);\n"
                + "        System.out.print(ptr.data + \" \");\n"
                + "    }\n\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter number of nodes: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        Node[] nodes = new Node[n];\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            System.out.print(\"Enter value of node \" + i + \": \");\n"
                + "            nodes[i] = new Node(sc.nextInt());\n"
                + "        }\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            System.out.print(\"Enter left and right child index of node \" + i + \" (-1 if none): \");\n"
                + "            int l = sc.nextInt();\n"
                + "            int r = sc.nextInt();\n"
                + "            if (l != -1) nodes[i].left = nodes[l];\n"
                + "            if (r != -1) nodes[i].right = nodes[r];\n"
                + "        }\n"
                + "        postorder(nodes[0]);\n"
                + "    }\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return nodeBlock
                + "public class Main {\n"
                + "    static void postorderRec(Node root) {\n"
                + "        if (root == null) return;\n"
                + "        postorderRec(root.left);\n"
                + "        postorderRec(root.right);\n"
                + "        System.out.print(root.data + \" \");\n"
                + "    }\n\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter number of nodes: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        Node[] nodes = new Node[n];\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            System.out.print(\"Enter value of node \" + i + \": \");\n"
                + "            nodes[i] = new Node(sc.nextInt());\n"
                + "        }\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            System.out.print(\"Enter left and right child index of node \" + i + \" (-1 if none): \");\n"
                + "            int l = sc.nextInt();\n"
                + "            int r = sc.nextInt();\n"
                + "            if (l != -1) nodes[i].left = nodes[l];\n"
                + "            if (r != -1) nodes[i].right = nodes[r];\n"
                + "        }\n"
                + "        postorderRec(nodes[0]);\n"
                + "    }\n"
                + "}";
        }
        return nodeBlock
            + "public class Main {\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        System.out.print(\"Enter number of nodes: \");\n"
            + "        int n = sc.nextInt();\n"
            + "        Node[] nodes = new Node[n];\n"
            + "        for (int i = 0; i < n; i++) {\n"
            + "            System.out.print(\"Enter value of node \" + i + \": \");\n"
            + "            nodes[i] = new Node(sc.nextInt());\n"
            + "        }\n"
            + "        for (int i = 0; i < n; i++) {\n"
            + "            System.out.print(\"Enter left and right child index of node \" + i + \" (-1 if none): \");\n"
            + "            int l = sc.nextInt();\n"
            + "            int r = sc.nextInt();\n"
            + "            if (l != -1) nodes[i].left = nodes[l];\n"
            + "            if (r != -1) nodes[i].right = nodes[r];\n"
            + "        }\n"
            + "        Stack<Node> stack = new Stack<>();\n"
            + "        Stack<Node> output = new Stack<>();\n"
            + "        Node ptr = nodes[0];\n"
            + "        stack.push(ptr);\n"
            + "        while (!stack.isEmpty()) {\n"
            + "            Node temp = stack.pop();\n"
            + "            output.push(temp);\n"
            + "            if (temp.left != null) stack.push(temp.left);\n"
            + "            if (temp.right != null) stack.push(temp.right);\n"
            + "        }\n"
            + "        while (!output.isEmpty()) {\n"
            + "            System.out.print(output.pop().data + \" \");\n"
            + "        }\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaBstSearchParentTracking(String style) {
        String nodeBlock = "import java.util.*;\n\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node left, right;\n"
            + "    Node(int data) {\n"
            + "        this.data = data;\n"
            + "        left = right = null;\n"
            + "    }\n"
            + "}\n\n";
        if ("Functions".equals(style)) {
            return nodeBlock
                + "public class Main {\n"
                + "    static Node loc = null, par = null;\n\n"
                + "    static void find(Node ptr, Node save, int item) {\n"
                + "        if (ptr == null) {\n"
                + "            loc = null;\n"
                + "            par = save;\n"
                + "            return;\n"
                + "        }\n"
                + "        if (item == ptr.data) {\n"
                + "            loc = ptr;\n"
                + "            par = save;\n"
                + "            return;\n"
                + "        }\n"
                + "        if (item < ptr.data)\n"
                + "            find(ptr.left, ptr, item);\n"
                + "        else\n"
                + "            find(ptr.right, ptr, item);\n"
                + "    }\n\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter number of nodes: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        Node[] nodes = new Node[n];\n"
                + "        System.out.println(\"Enter node values:\");\n"
                + "        for (int i = 0; i < n; i++) nodes[i] = new Node(sc.nextInt());\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            System.out.print(\"Enter left and right child index of node \" + i + \" (-1 if none): \");\n"
                + "            int l = sc.nextInt();\n"
                + "            int r = sc.nextInt();\n"
                + "            if (l != -1) nodes[i].left = nodes[l];\n"
                + "            if (r != -1) nodes[i].right = nodes[r];\n"
                + "        }\n"
                + "        System.out.print(\"Enter ITEM to search: \");\n"
                + "        int item = sc.nextInt();\n"
                + "        find(nodes[0], null, item);\n"
                + "        if (loc != null)\n"
                + "            System.out.println(\"Found. Parent: \" + (par == null ? \"NULL\" : par.data));\n"
                + "        else\n"
                + "            System.out.println(\"Not Found. Parent: \" + (par == null ? \"NULL\" : par.data));\n"
                + "    }\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return nodeBlock
                + "public class Main {\n"
                + "    static Node loc = null, par = null;\n\n"
                + "    static void findRec(Node root, Node parent, int item) {\n"
                + "        if (root == null) {\n"
                + "            loc = null;\n"
                + "            par = parent;\n"
                + "            return;\n"
                + "        }\n"
                + "        if (item == root.data) {\n"
                + "            loc = root;\n"
                + "            par = parent;\n"
                + "            return;\n"
                + "        }\n"
                + "        if (item < root.data)\n"
                + "            findRec(root.left, root, item);\n"
                + "        else\n"
                + "            findRec(root.right, root, item);\n"
                + "    }\n\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter number of nodes: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        Node[] nodes = new Node[n];\n"
                + "        System.out.println(\"Enter node values:\");\n"
                + "        for (int i = 0; i < n; i++) nodes[i] = new Node(sc.nextInt());\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            System.out.print(\"Enter left and right child index of node \" + i + \" (-1 if none): \");\n"
                + "            int l = sc.nextInt();\n"
                + "            int r = sc.nextInt();\n"
                + "            if (l != -1) nodes[i].left = nodes[l];\n"
                + "            if (r != -1) nodes[i].right = nodes[r];\n"
                + "        }\n"
                + "        System.out.print(\"Enter ITEM to search: \");\n"
                + "        int item = sc.nextInt();\n"
                + "        findRec(nodes[0], null, item);\n"
                + "        if (loc != null)\n"
                + "            System.out.println(\"Found. Parent: \" + (par == null ? \"NULL\" : par.data));\n"
                + "        else\n"
                + "            System.out.println(\"Not Found. Parent: \" + (par == null ? \"NULL\" : par.data));\n"
                + "    }\n"
                + "}";
        }
        return nodeBlock
            + "public class Main {\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        System.out.print(\"Enter number of nodes: \");\n"
            + "        int n = sc.nextInt();\n"
            + "        Node[] nodes = new Node[n];\n"
            + "        for (int i = 0; i < n; i++) {\n"
            + "            System.out.print(\"Enter value of node \" + i + \": \");\n"
            + "            nodes[i] = new Node(sc.nextInt());\n"
            + "        }\n"
            + "        for (int i = 0; i < n; i++) {\n"
            + "            System.out.print(\"Enter left and right child index of node \" + i + \" (-1 if none): \");\n"
            + "            int l = sc.nextInt();\n"
            + "            int r = sc.nextInt();\n"
            + "            if (l != -1) nodes[i].left = nodes[l];\n"
            + "            if (r != -1) nodes[i].right = nodes[r];\n"
            + "        }\n"
            + "        System.out.print(\"Enter ITEM to search: \");\n"
            + "        int item = sc.nextInt();\n"
            + "        Node root = nodes[0];\n"
            + "        Node ptr = root, save = null;\n"
            + "        Node loc = null, par = null;\n"
            + "        if (root == null) {\n"
            + "            loc = null;\n"
            + "            par = null;\n"
            + "        } else if (item == root.data) {\n"
            + "            loc = root;\n"
            + "            par = null;\n"
            + "        } else {\n"
            + "            if (item < root.data) {\n"
            + "                ptr = root.left;\n"
            + "                save = root;\n"
            + "            } else {\n"
            + "                ptr = root.right;\n"
            + "                save = root;\n"
            + "            }\n"
            + "            while (ptr != null) {\n"
            + "                if (item == ptr.data) {\n"
            + "                    loc = ptr;\n"
            + "                    par = save;\n"
            + "                    break;\n"
            + "                } else if (item < ptr.data) {\n"
            + "                    save = ptr;\n"
            + "                    ptr = ptr.left;\n"
            + "                } else {\n"
            + "                    save = ptr;\n"
            + "                    ptr = ptr.right;\n"
            + "                }\n"
            + "            }\n"
            + "            if (ptr == null) {\n"
            + "                loc = null;\n"
            + "                par = save;\n"
            + "            }\n"
            + "        }\n"
            + "        if (loc != null)\n"
            + "            System.out.println(\"Found. Parent: \" + (par == null ? \"NULL\" : par.data));\n"
            + "        else\n"
            + "            System.out.println(\"Not Found. Parent: \" + (par == null ? \"NULL\" : par.data));\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaBstInsertion(String style) {
        String nodeBlock = "import java.util.*;\n\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node left, right;\n"
            + "    Node(int data) {\n"
            + "        this.data = data;\n"
            + "        left = right = null;\n"
            + "    }\n"
            + "}\n\n";
        if ("Functions".equals(style)) {
            return nodeBlock
                + "public class Main {\n"
                + "    static Node insertFunc(Node root, int item) {\n"
                + "        if (root == null) return new Node(item);\n"
                + "        if (item < root.data)\n"
                + "            root.left = insertFunc(root.left, item);\n"
                + "        else if (item > root.data)\n"
                + "            root.right = insertFunc(root.right, item);\n"
                + "        return root;\n"
                + "    }\n\n"
                + "    static void inorder(Node root) {\n"
                + "        if (root == null) return;\n"
                + "        inorder(root.left);\n"
                + "        System.out.print(root.data + \" \");\n"
                + "        inorder(root.right);\n"
                + "    }\n\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter number of existing BST nodes: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        Node root = null;\n"
                + "        System.out.println(\"Enter BST node values:\");\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            int val = sc.nextInt();\n"
                + "            root = insertFunc(root, val);\n"
                + "        }\n"
                + "        System.out.print(\"Current inorder traversal: \");\n"
                + "        inorder(root);\n"
                + "        System.out.println();\n"
                + "        System.out.print(\"Enter item to insert: \");\n"
                + "        int item = sc.nextInt();\n"
                + "        root = insertFunc(root, item);\n"
                + "        System.out.println(\"Insertion Done\");\n"
                + "        System.out.print(\"BST after insertion (inorder): \");\n"
                + "        inorder(root);\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return nodeBlock
                + "public class Main {\n"
                + "    static Node insertRec(Node root, int item) {\n"
                + "        if (root == null) {\n"
                + "            return new Node(item);\n"
                + "        }\n"
                + "        if (item < root.data)\n"
                + "            root.left = insertRec(root.left, item);\n"
                + "        else if (item > root.data)\n"
                + "            root.right = insertRec(root.right, item);\n"
                + "        return root;\n"
                + "    }\n\n"
                + "    static void inorder(Node root) {\n"
                + "        if (root == null) return;\n"
                + "        inorder(root.left);\n"
                + "        System.out.print(root.data + \" \");\n"
                + "        inorder(root.right);\n"
                + "    }\n\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter number of existing BST nodes: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        Node root = null;\n"
                + "        System.out.println(\"Enter BST node values:\");\n"
                + "        for (int i = 0; i < n; i++) {\n"
                + "            int val = sc.nextInt();\n"
                + "            root = insertRec(root, val);\n"
                + "        }\n"
                + "        System.out.print(\"Current inorder traversal: \");\n"
                + "        inorder(root);\n"
                + "        System.out.println();\n"
                + "        System.out.print(\"Enter item to insert: \");\n"
                + "        int item = sc.nextInt();\n"
                + "        root = insertRec(root, item);\n"
                + "        System.out.println(\"Insertion Done\");\n"
                + "        System.out.print(\"BST after insertion (inorder): \");\n"
                + "        inorder(root);\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "}";
        }
        return nodeBlock
            + "public class Main {\n"
            + "    static void inorder(Node root) {\n"
            + "        if (root == null) return;\n"
            + "        inorder(root.left);\n"
            + "        System.out.print(root.data + \" \");\n"
            + "        inorder(root.right);\n"
            + "    }\n\n"
            + "    static Node insert(Node root, int item) {\n"
            + "        Node parent = null, ptr = root;\n"
            + "        while (ptr != null) {\n"
            + "            parent = ptr;\n"
            + "            if (item < ptr.data)\n"
            + "                ptr = ptr.left;\n"
            + "            else if (item > ptr.data)\n"
            + "                ptr = ptr.right;\n"
            + "            else\n"
            + "                return root;\n"
            + "        }\n"
            + "        Node newNode = new Node(item);\n"
            + "        if (parent == null)\n"
            + "            return newNode;\n"
            + "        else if (item < parent.data)\n"
            + "            parent.left = newNode;\n"
            + "        else\n"
            + "            parent.right = newNode;\n"
            + "        return root;\n"
            + "    }\n\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        System.out.print(\"Enter number of existing BST nodes: \");\n"
            + "        int n = sc.nextInt();\n"
            + "        Node root = null;\n"
            + "        System.out.println(\"Enter BST node values:\");\n"
            + "        for (int i = 0; i < n; i++) {\n"
            + "            int val = sc.nextInt();\n"
            + "            root = insert(root, val);\n"
            + "        }\n"
            + "        System.out.print(\"Current inorder traversal: \");\n"
            + "        inorder(root);\n"
            + "        System.out.println();\n"
            + "        System.out.print(\"Enter item to insert: \");\n"
            + "        int item = sc.nextInt();\n"
            + "        root = insert(root, item);\n"
            + "        System.out.println(\"Insertion Done\");\n"
            + "        System.out.print(\"BST after insertion (inorder): \");\n"
            + "        inorder(root);\n"
            + "        System.out.println();\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaBstDeletionCaseA(String style) {
        String base = "import java.util.*;\n\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node left, right;\n"
            + "    Node(int data) { this.data = data; left = right = null; }\n"
            + "}\n\n"
            + "class Util {\n"
            + "    static Node insert(Node root, int val) {\n"
            + "        if (root == null) return new Node(val);\n"
            + "        if (val < root.data) root.left = insert(root.left, val);\n"
            + "        else if (val > root.data) root.right = insert(root.right, val);\n"
            + "        return root;\n"
            + "    }\n"
            + "}\n\n";
        if ("Functions".equals(style)) {
            return base
                + "public class Main {\n"
                + "    static Node deleteFunc(Node root, int item) {\n"
                + "        if (root == null) return null;\n"
                + "        if (item < root.data) root.left = deleteFunc(root.left, item);\n"
                + "        else if (item > root.data) root.right = deleteFunc(root.right, item);\n"
                + "        else {\n"
                + "            if (root.left == null) return root.right;\n"
                + "            else if (root.right == null) return root.left;\n"
                + "        }\n"
                + "        return root;\n"
                + "    }\n"
                + "    static void inorder(Node root) {\n"
                + "        if (root == null) return;\n"
                + "        inorder(root.left);\n"
                + "        System.out.print(root.data + \" \");\n"
                + "        inorder(root.right);\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter number of BST nodes: \");\n"
                + "        int n = sc.nextInt(); Node root = null;\n"
                + "        System.out.println(\"Enter BST node values:\");\n"
                + "        for (int i = 0; i < n; i++) root = Util.insert(root, sc.nextInt());\n"
                + "        System.out.print(\"BST before deletion (inorder): \");\n"
                + "        inorder(root);\n"
                + "        System.out.println();\n"
                + "        System.out.print(\"Enter item to delete: \");\n"
                + "        root = deleteFunc(root, sc.nextInt());\n"
                + "        System.out.println(\"Deletion Done\");\n"
                + "        System.out.print(\"BST after deletion (inorder): \");\n"
                + "        inorder(root);\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return base
                + "public class Main {\n"
                + "    static Node deleteRec(Node root, int item) {\n"
                + "        if (root == null) return null;\n"
                + "        if (item < root.data) root.left = deleteRec(root.left, item);\n"
                + "        else if (item > root.data) root.right = deleteRec(root.right, item);\n"
                + "        else {\n"
                + "            if (root.left == null && root.right == null) return null;\n"
                + "            else if (root.left != null) return root.left;\n"
                + "            else return root.right;\n"
                + "        }\n"
                + "        return root;\n"
                + "    }\n"
                + "    static void inorder(Node root) {\n"
                + "        if (root == null) return;\n"
                + "        inorder(root.left);\n"
                + "        System.out.print(root.data + \" \");\n"
                + "        inorder(root.right);\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter number of BST nodes: \");\n"
                + "        int n = sc.nextInt(); Node root = null;\n"
                + "        System.out.println(\"Enter BST node values:\");\n"
                + "        for (int i = 0; i < n; i++) root = Util.insert(root, sc.nextInt());\n"
                + "        System.out.print(\"BST before deletion (inorder): \");\n"
                + "        inorder(root);\n"
                + "        System.out.println();\n"
                + "        System.out.print(\"Enter item to delete: \");\n"
                + "        root = deleteRec(root, sc.nextInt());\n"
                + "        System.out.println(\"Deletion Done\");\n"
                + "        System.out.print(\"BST after deletion (inorder): \");\n"
                + "        inorder(root);\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "}";
        }
        return base
            + "public class Main {\n"
            + "    static Node deleteCaseA(Node root, int item) {\n"
            + "        Node ptr = root, par = null;\n"
            + "        while (ptr != null && ptr.data != item) {\n"
            + "            par = ptr;\n"
            + "            if (item < ptr.data) ptr = ptr.left; else ptr = ptr.right;\n"
            + "        }\n"
            + "        if (ptr == null) return root;\n"
            + "        Node child;\n"
            + "        if (ptr.left == null && ptr.right == null) child = null;\n"
            + "        else if (ptr.left != null) child = ptr.left;\n"
            + "        else child = ptr.right;\n"
            + "        if (par != null) {\n"
            + "            if (par.left == ptr) par.left = child; else par.right = child;\n"
            + "        } else {\n"
            + "            root = child;\n"
            + "        }\n"
            + "        return root;\n"
            + "    }\n"
            + "    static void inorder(Node root) {\n"
            + "        if (root == null) return;\n"
            + "        inorder(root.left);\n"
            + "        System.out.print(root.data + \" \");\n"
            + "        inorder(root.right);\n"
            + "    }\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        System.out.print(\"Enter number of BST nodes: \");\n"
            + "        int n = sc.nextInt(); Node root = null;\n"
            + "        System.out.println(\"Enter BST node values:\");\n"
            + "        for (int i = 0; i < n; i++) root = Util.insert(root, sc.nextInt());\n"
            + "        System.out.print(\"BST before deletion (inorder): \");\n"
            + "        inorder(root);\n"
            + "        System.out.println();\n"
            + "        System.out.print(\"Enter item to delete: \");\n"
            + "        root = deleteCaseA(root, sc.nextInt());\n"
            + "        System.out.println(\"Deletion Done\");\n"
            + "        System.out.print(\"BST after deletion (inorder): \");\n"
            + "        inorder(root);\n"
            + "        System.out.println();\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaBstDeletionCaseB(String style) {
        String base = "import java.util.*;\n\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node left, right;\n"
            + "    Node(int data) { this.data = data; left = right = null; }\n"
            + "}\n\n"
            + "class Util {\n"
            + "    static Node insert(Node root, int val) {\n"
            + "        if (root == null) return new Node(val);\n"
            + "        if (val < root.data) root.left = insert(root.left, val);\n"
            + "        else if (val > root.data) root.right = insert(root.right, val);\n"
            + "        return root;\n"
            + "    }\n"
            + "}\n\n";
        if ("Functions".equals(style) || "Recursion".equals(style)) {
            String methodName = "Functions".equals(style) ? "deleteFunc" : "deleteRec";
            return base
                + "public class Main {\n"
                + "    static Node " + methodName + "(Node root, int item) {\n"
                + "        if (root == null) return null;\n"
                + "        if (item < root.data) root.left = " + methodName + "(root.left, item);\n"
                + "        else if (item > root.data) root.right = " + methodName + "(root.right, item);\n"
                + "        else if (root.left != null && root.right != null) {\n"
                + "            Node suc = root.right;\n"
                + "            while (suc.left != null) suc = suc.left;\n"
                + "            root.data = suc.data;\n"
                + "            root.right = " + methodName + "(root.right, suc.data);\n"
                + "        }\n"
                + "        return root;\n"
                + "    }\n"
                + "    static void inorder(Node root) {\n"
                + "        if (root == null) return;\n"
                + "        inorder(root.left);\n"
                + "        System.out.print(root.data + \" \");\n"
                + "        inorder(root.right);\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter number of BST nodes: \");\n"
                + "        int n = sc.nextInt(); Node root = null;\n"
                + "        System.out.println(\"Enter BST node values:\");\n"
                + "        for (int i = 0; i < n; i++) root = Util.insert(root, sc.nextInt());\n"
                + "        System.out.print(\"BST before deletion (inorder): \");\n"
                + "        inorder(root);\n"
                + "        System.out.println();\n"
                + "        System.out.print(\"Enter item to delete: \");\n"
                + "        root = " + methodName + "(root, sc.nextInt());\n"
                + "        System.out.println(\"Deletion Done\");\n"
                + "        System.out.print(\"BST after deletion (inorder): \");\n"
                + "        inorder(root);\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "}";
        }
        return base
            + "public class Main {\n"
            + "    static Node deleteCaseB(Node root, int item) {\n"
            + "        Node ptr = root;\n"
            + "        while (ptr != null && ptr.data != item) {\n"
            + "            if (item < ptr.data) ptr = ptr.left; else ptr = ptr.right;\n"
            + "        }\n"
            + "        if (ptr == null) return root;\n"
            + "        if (ptr.left != null && ptr.right != null) {\n"
            + "            Node sucParent = ptr;\n"
            + "            Node suc = ptr.right;\n"
            + "            while (suc.left != null) { sucParent = suc; suc = suc.left; }\n"
            + "            ptr.data = suc.data;\n"
            + "            if (sucParent.left == suc) sucParent.left = suc.right; else sucParent.right = suc.right;\n"
            + "        }\n"
            + "        return root;\n"
            + "    }\n"
            + "    static void inorder(Node root) {\n"
            + "        if (root == null) return;\n"
            + "        inorder(root.left);\n"
            + "        System.out.print(root.data + \" \");\n"
            + "        inorder(root.right);\n"
            + "    }\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        System.out.print(\"Enter number of BST nodes: \");\n"
            + "        int n = sc.nextInt(); Node root = null;\n"
            + "        System.out.println(\"Enter BST node values:\");\n"
            + "        for (int i = 0; i < n; i++) root = Util.insert(root, sc.nextInt());\n"
            + "        System.out.print(\"BST before deletion (inorder): \");\n"
            + "        inorder(root);\n"
            + "        System.out.println();\n"
            + "        System.out.print(\"Enter item to delete: \");\n"
            + "        root = deleteCaseB(root, sc.nextInt());\n"
            + "        System.out.println(\"Deletion Done\");\n"
            + "        System.out.print(\"BST after deletion (inorder): \");\n"
            + "        inorder(root);\n"
            + "        System.out.println();\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaBstDeletionDel(String style) {
        String base = "import java.util.*;\n\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node left, right;\n"
            + "    Node(int data) { this.data = data; left = right = null; }\n"
            + "}\n\n"
            + "class Util {\n"
            + "    static Node insert(Node root, int val) {\n"
            + "        if (root == null) return new Node(val);\n"
            + "        if (val < root.data) root.left = insert(root.left, val);\n"
            + "        else if (val > root.data) root.right = insert(root.right, val);\n"
            + "        return root;\n"
            + "    }\n"
            + "}\n\n";
        String methodName = "Iterative".equals(style) ? "delete" : ("Functions".equals(style) ? "deleteFunc" : "deleteRec");
        return base
            + "public class Main {\n"
            + "    static Node " + methodName + "(Node root, int item) {\n"
            + "        if (root == null) { System.out.println(\"ITEM not in tree\"); return null; }\n"
            + "        if (item < root.data) root.left = " + methodName + "(root.left, item);\n"
            + "        else if (item > root.data) root.right = " + methodName + "(root.right, item);\n"
            + "        else {\n"
            + "            if (root.left != null && root.right != null) {\n"
            + "                Node suc = root.right;\n"
            + "                while (suc.left != null) suc = suc.left;\n"
            + "                root.data = suc.data;\n"
            + "                root.right = " + methodName + "(root.right, suc.data);\n"
            + "            } else if (root.left != null) {\n"
            + "                return root.left;\n"
            + "            } else {\n"
            + "                return root.right;\n"
            + "            }\n"
            + "        }\n"
            + "        return root;\n"
            + "    }\n"
            + "    static void inorder(Node root) {\n"
            + "        if (root == null) return;\n"
            + "        inorder(root.left);\n"
            + "        System.out.print(root.data + \" \");\n"
            + "        inorder(root.right);\n"
            + "    }\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        System.out.print(\"Enter number of BST nodes: \");\n"
            + "        int n = sc.nextInt(); Node root = null;\n"
            + "        System.out.println(\"Enter BST node values:\");\n"
            + "        for (int i = 0; i < n; i++) root = Util.insert(root, sc.nextInt());\n"
            + "        System.out.print(\"BST before deletion (inorder): \");\n"
            + "        inorder(root);\n"
            + "        System.out.println();\n"
            + "        System.out.print(\"Enter item to delete: \");\n"
            + "        root = " + methodName + "(root, sc.nextInt());\n"
            + "        System.out.println(\"Deletion Done\");\n"
            + "        System.out.print(\"BST after deletion (inorder): \");\n"
            + "        inorder(root);\n"
            + "        System.out.println();\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaMaxHeapInsertion(String style) {
        String body = "        int n = sc.nextInt();\n"
            + "        int[] tree = new int[100];\n"
            + "        for (int i = 1; i <= n; i++) tree[i] = sc.nextInt();\n"
            + "        int item = sc.nextInt();\n";
        if ("Functions".equals(style)) {
            return "import java.util.*;\npublic class Main {\n"
                + "    static int insertHeap(int[] tree, int n, int item) {\n"
                + "        n = n + 1; int ptr = n;\n"
                + "        while (ptr > 1) { int par = ptr / 2; if (item <= tree[par]) { tree[ptr] = item; return n; } tree[ptr] = tree[par]; ptr = par; }\n"
                + "        tree[ptr] = item; return n;\n"
                + "    }\n"
                + "    public static void main(String[] args) { Scanner sc = new Scanner(System.in);\n" + body
                + "        n = insertHeap(tree, n, item);\n"
                + "        for (int i = 1; i <= n; i++) System.out.print(tree[i] + \" \");\n"
                + "    }\n}";
        }
        if ("Recursion".equals(style)) {
            return "import java.util.*;\npublic class Main {\n"
                + "    static void insertRec(int[] tree, int ptr, int item) {\n"
                + "        if (ptr == 1) { tree[ptr] = item; return; }\n"
                + "        int par = ptr / 2; if (item <= tree[par]) { tree[ptr] = item; return; }\n"
                + "        tree[ptr] = tree[par]; insertRec(tree, par, item);\n"
                + "    }\n"
                + "    public static void main(String[] args) { Scanner sc = new Scanner(System.in);\n" + body
                + "        n = n + 1; insertRec(tree, n, item);\n"
                + "        for (int i = 1; i <= n; i++) System.out.print(tree[i] + \" \");\n"
                + "    }\n}";
        }
        return "import java.util.*;\npublic class Main {\n"
            + "    public static void main(String[] args) { Scanner sc = new Scanner(System.in);\n" + body
            + "        n = n + 1; int ptr = n;\n"
            + "        while (ptr > 1) { int par = ptr / 2; if (item <= tree[par]) { tree[ptr] = item; break; } tree[ptr] = tree[par]; ptr = par; }\n"
            + "        if (ptr == 1) tree[ptr] = item;\n"
            + "        for (int i = 1; i <= n; i++) System.out.print(tree[i] + \" \");\n"
            + "    }\n}";
    }

    private static String getJavaMaxHeapDeletion(String style) {
        String body = "        int n = sc.nextInt();\n"
            + "        int[] tree = new int[100];\n"
            + "        for (int i = 1; i <= n; i++) tree[i] = sc.nextInt();\n";
        if ("Functions".equals(style)) {
            return "import java.util.*;\npublic class Main {\n"
                + "    static int deleteHeap(int[] tree, int n) {\n"
                + "        int item = tree[1]; int last = tree[n]; n--; int ptr = 1;\n"
                + "        while (2 * ptr <= n) { int left = 2 * ptr; int right = left + 1; int child = (right <= n && tree[right] > tree[left]) ? right : left; if (last >= tree[child]) break; tree[ptr] = tree[child]; ptr = child; }\n"
                + "        tree[ptr] = last; return item;\n"
                + "    }\n"
                + "    public static void main(String[] args) { Scanner sc = new Scanner(System.in);\n" + body
                + "        int deleted = deleteHeap(tree, n);\n"
                + "        System.out.println(\"Deleted element: \" + deleted);\n"
                + "    }\n}";
        }
        if ("Recursion".equals(style)) {
            return "import java.util.*;\npublic class Main {\n"
                + "    static void deleteRec(int[] tree, int ptr, int n, int last) {\n"
                + "        int left = 2 * ptr, right = left + 1; if (left > n) { tree[ptr] = last; return; }\n"
                + "        int child = (right <= n && tree[right] > tree[left]) ? right : left; if (last >= tree[child]) { tree[ptr] = last; return; }\n"
                + "        tree[ptr] = tree[child]; deleteRec(tree, child, n, last);\n"
                + "    }\n"
                + "    public static void main(String[] args) { Scanner sc = new Scanner(System.in);\n" + body
                + "        int item = tree[1]; int last = tree[n]; n--; deleteRec(tree, 1, n, last);\n"
                + "        System.out.println(\"Deleted element: \" + item);\n"
                + "        for (int i = 1; i <= n; i++) System.out.print(tree[i] + \" \");\n"
                + "    }\n}";
        }
        return "import java.util.*;\npublic class Main {\n"
            + "    public static void main(String[] args) { Scanner sc = new Scanner(System.in);\n" + body
            + "        int item = tree[1]; int last = tree[n]; n = n - 1; int ptr = 1;\n"
            + "        while (2 * ptr <= n) { int left = 2 * ptr; int right = left + 1; int child = (right <= n && tree[right] > tree[left]) ? right : left; if (last >= tree[child]) break; tree[ptr] = tree[child]; ptr = child; }\n"
            + "        tree[ptr] = last; System.out.println(\"Deleted element: \" + item);\n"
            + "        for (int i = 1; i <= n; i++) System.out.print(tree[i] + \" \");\n"
            + "    }\n}";
    }

    private static String getJavaHeapSort(String style) {
        String helpers = "    static void insertHeap(int[] A, int n, int item) { int ptr = n + 1; while (ptr > 1) { int par = ptr / 2; if (item <= A[par]) { A[ptr] = item; return; } A[ptr] = A[par]; ptr = par; } A[ptr] = item; }\n"
            + "    static int deleteHeap(int[] A, int n) { int item = A[1]; int last = A[n]; n--; int ptr = 1; while (2 * ptr <= n) { int left = 2 * ptr; int right = left + 1; int child = (right <= n && A[right] > A[left]) ? right : left; if (last >= A[child]) break; A[ptr] = A[child]; ptr = child; } A[ptr] = last; return item; }\n";
        if ("Functions".equals(style)) {
            return "import java.util.*;\npublic class Main {\n"
                + "    static void buildHeap(int[] A, int n) { for (int j = 2; j <= n; j++) insertHeap(A, j - 1, A[j]); }\n"
                + "    static void sortHeap(int[] A, int n) { int size = n; while (size > 1) { int item = deleteHeap(A, size); A[size] = item; size--; } }\n"
                + helpers
                + "    public static void main(String[] args) { Scanner sc = new Scanner(System.in); int n = sc.nextInt(); int[] A = new int[200]; for (int i = 1; i <= n; i++) A[i] = sc.nextInt(); buildHeap(A, n); sortHeap(A, n); for (int i = 1; i <= n; i++) System.out.print(A[i] + \" \"); }\n}";
        }
        if ("Recursion".equals(style)) {
            return "import java.util.*;\npublic class Main {\n"
                + "    static void deleteMaxRec(int[] A, int n) { if (n <= 1) return; int item = deleteHeap(A, n); A[n] = item; deleteMaxRec(A, n - 1); }\n"
                + "    static void buildHeap(int[] A, int n) { for (int j = 2; j <= n; j++) insertHeap(A, j - 1, A[j]); }\n"
                + helpers
                + "    public static void main(String[] args) { Scanner sc = new Scanner(System.in); int n = sc.nextInt(); int[] A = new int[200]; for (int i = 1; i <= n; i++) A[i] = sc.nextInt(); buildHeap(A, n); deleteMaxRec(A, n); for (int i = 1; i <= n; i++) System.out.print(A[i] + \" \"); }\n}";
        }
        return "import java.util.*;\npublic class Main {\n"
            + helpers
            + "    public static void main(String[] args) { Scanner sc = new Scanner(System.in); int n = sc.nextInt(); int[] A = new int[200]; for (int i = 1; i <= n; i++) A[i] = sc.nextInt(); for (int j = 2; j <= n; j++) insertHeap(A, j - 1, A[j]); int size = n; while (size > 1) { int item = deleteHeap(A, size); A[size] = item; size--; } for (int i = 1; i <= n; i++) System.out.print(A[i] + \" \"); }\n}";
    }

    private static String getJavaSearchInLinkedList(String style) {
        if ("Functions".equals(style)) {
            return "import java.util.Scanner;\n"
                + "class Node {\n"
                + "    int data;\n"
                + "    Node next;\n"
                + "    Node(int data) {\n"
                + "        this.data = data;\n"
                + "    }\n"
                + "}\n"
                + "class Main {\n"
                + "    static Node search(Node ptr, int item) {\n"
                + "        while(ptr != null) {\n"
                + "            if(ptr.data == item)\n"
                + "                return ptr;\n"
                + "            ptr = ptr.next;\n"
                + "        }\n"
                + "        return null;\n"
                + "    }\n"
                + "    static void display(Node head) {\n"
                + "        System.out.print(\"Linked list: \");\n"
                + "        for(Node ptr = head; ptr != null; ptr = ptr.next)\n"
                + "            System.out.print(ptr.data + \" \");\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter number of nodes: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        Node head = null, temp = null;\n"
                + "        System.out.println(\"Enter node values:\");\n"
                + "        for(int i = 0; i < n; i++) {\n"
                + "            Node newNode = new Node(sc.nextInt());\n"
                + "            if(head == null)\n"
                + "                head = temp = newNode;\n"
                + "            else {\n"
                + "                temp.next = newNode;\n"
                + "                temp = newNode;\n"
                + "            }\n"
                + "        }\n"
                + "        display(head);\n"
                + "        System.out.print(\"Enter item to search: \");\n"
                + "        int item = sc.nextInt();\n"
                + "        Node loc = search(head, item);\n"
                + "        if(loc != null)\n"
                + "            System.out.println(\"Found\");\n"
                + "        else\n"
                + "            System.out.println(\"Not Found\");\n"
                + "    }\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return "import java.util.Scanner;\n"
                + "class Node {\n"
                + "    int data;\n"
                + "    Node next;\n"
                + "    Node(int data) {\n"
                + "        this.data = data;\n"
                + "    }\n"
                + "}\n"
                + "class Main {\n"
                + "    static Node search(Node ptr, int item) {\n"
                + "        if(ptr == null)\n"
                + "            return null;\n"
                + "        if(ptr.data == item)\n"
                + "            return ptr;\n"
                + "        return search(ptr.next, item);\n"
                + "    }\n"
                + "    static void display(Node head) {\n"
                + "        System.out.print(\"Linked list: \");\n"
                + "        for(Node ptr = head; ptr != null; ptr = ptr.next)\n"
                + "            System.out.print(ptr.data + \" \");\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        System.out.print(\"Enter number of nodes: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        Node head = null, temp = null;\n"
                + "        System.out.println(\"Enter node values:\");\n"
                + "        for(int i = 0; i < n; i++) {\n"
                + "            Node newNode = new Node(sc.nextInt());\n"
                + "            if(head == null)\n"
                + "                head = temp = newNode;\n"
                + "            else {\n"
                + "                temp.next = newNode;\n"
                + "                temp = newNode;\n"
                + "            }\n"
                + "        }\n"
                + "        display(head);\n"
                + "        System.out.print(\"Enter item to search: \");\n"
                + "        int item = sc.nextInt();\n"
                + "        Node loc = search(head, item);\n"
                + "        if(loc != null)\n"
                + "            System.out.println(\"Found\");\n"
                + "        else\n"
                + "            System.out.println(\"Not Found\");\n"
                + "    }\n"
                + "}";
        }
        return "import java.util.Scanner;\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node next;\n"
            + "    Node(int data) {\n"
            + "        this.data = data;\n"
            + "    }\n"
            + "}\n"
            + "class Main {\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        System.out.print(\"Enter number of nodes: \");\n"
            + "        int n = sc.nextInt();\n"
            + "        Node head = null, temp = null;\n"
            + "        System.out.println(\"Enter node values:\");\n"
            + "        for(int i = 0; i < n; i++) {\n"
            + "            Node newNode = new Node(sc.nextInt());\n"
            + "            if(head == null)\n"
            + "                head = temp = newNode;\n"
            + "            else {\n"
            + "                temp.next = newNode;\n"
            + "                temp = newNode;\n"
            + "            }\n"
            + "        }\n"
            + "        System.out.print(\"Linked list: \");\n"
            + "        for(Node current = head; current != null; current = current.next)\n"
            + "            System.out.print(current.data + \" \");\n"
            + "        System.out.println();\n"
            + "        System.out.print(\"Enter item to search: \");\n"
            + "        int item = sc.nextInt();\n"
            + "        Node ptr = head;\n"
            + "        Node loc = null;\n"
            + "        while(ptr != null) {\n"
            + "            if(ptr.data == item) {\n"
            + "                loc = ptr;\n"
            + "                break;\n"
            + "            }\n"
            + "            ptr = ptr.next;\n"
            + "        }\n"
            + "        if(loc != null)\n"
            + "            System.out.println(\"Found\");\n"
            + "        else\n"
            + "            System.out.println(\"Not Found\");\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaInsertAtBeginningLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return "// Recursion is not applicable here because insertion at beginning is a single-step pointer update.";
        }
        if ("Functions".equals(style)) {
            return "import java.util.Scanner;\n"
                + "class Main {\n"
                + "    static int MAX = 100;\n"
                + "    static int[] INFO = new int[MAX];\n"
                + "    static int[] LINK = new int[MAX];\n"
                + "    static int START = -1;\n"
                + "    static int AVAIL;\n"
                + "    static void init() {\n"
                + "        for(int i = 0; i < MAX - 1; i++)\n"
                + "            LINK[i] = i + 1;\n"
                + "        LINK[MAX - 1] = -1;\n"
                + "        AVAIL = 0;\n"
                + "    }\n"
                + "    static void append(int item) {\n"
                + "        if(AVAIL == -1) return;\n"
                + "        int NEW = AVAIL;\n"
                + "        AVAIL = LINK[AVAIL];\n"
                + "        INFO[NEW] = item;\n"
                + "        LINK[NEW] = -1;\n"
                + "        if(START == -1) {\n"
                + "            START = NEW;\n"
                + "        } else {\n"
                + "            int ptr = START;\n"
                + "            while(LINK[ptr] != -1) ptr = LINK[ptr];\n"
                + "            LINK[ptr] = NEW;\n"
                + "        }\n"
                + "    }\n"
                + "    static void insFirst(int item) {\n"
                + "        if(AVAIL == -1) {\n"
                + "            System.out.println(\"OVERFLOW\");\n"
                + "            return;\n"
                + "        }\n"
                + "        int NEW = AVAIL;\n"
                + "        AVAIL = LINK[AVAIL];\n"
                + "        INFO[NEW] = item;\n"
                + "        LINK[NEW] = START;\n"
                + "        START = NEW;\n"
                + "    }\n"
                + "    static void display() {\n"
                + "        System.out.print(\"Linked list: \");\n"
                + "        int ptr = START;\n"
                + "        while(ptr != -1) {\n"
                + "            System.out.print(INFO[ptr] + \" \");\n"
                + "            ptr = LINK[ptr];\n"
                + "        }\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        init();\n"
                + "        System.out.print(\"Enter number of existing nodes: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        System.out.println(\"Enter node values:\");\n"
                + "        for(int i = 0; i < n; i++) append(sc.nextInt());\n"
                + "        display();\n"
                + "        System.out.print(\"Enter item to insert at beginning: \");\n"
                + "        int item = sc.nextInt();\n"
                + "        insFirst(item);\n"
                + "        System.out.println(\"Inserted successfully\");\n"
                + "        display();\n"
                + "    }\n"
                + "}";
        }
        return "import java.util.Scanner;\n"
            + "class Main {\n"
            + "    static int MAX = 100;\n"
            + "    static int[] INFO = new int[MAX];\n"
            + "    static int[] LINK = new int[MAX];\n"
            + "    static int START = -1;\n"
            + "    static int AVAIL;\n"
            + "    static void init() {\n"
            + "        for(int i = 0; i < MAX - 1; i++)\n"
            + "            LINK[i] = i + 1;\n"
            + "        LINK[MAX - 1] = -1;\n"
            + "        AVAIL = 0;\n"
            + "    }\n"
            + "    static void append(int item) {\n"
            + "        if(AVAIL == -1) return;\n"
            + "        int NEW = AVAIL;\n"
            + "        AVAIL = LINK[AVAIL];\n"
            + "        INFO[NEW] = item;\n"
            + "        LINK[NEW] = -1;\n"
            + "        if(START == -1) START = NEW;\n"
            + "        else {\n"
            + "            int ptr = START;\n"
            + "            while(LINK[ptr] != -1) ptr = LINK[ptr];\n"
            + "            LINK[ptr] = NEW;\n"
            + "        }\n"
            + "    }\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        init();\n"
            + "        System.out.print(\"Enter number of existing nodes: \");\n"
            + "        int n = sc.nextInt();\n"
            + "        System.out.println(\"Enter node values:\");\n"
            + "        for(int i = 0; i < n; i++) append(sc.nextInt());\n"
            + "        System.out.print(\"Current linked list: \");\n"
            + "        for(int ptr = START; ptr != -1; ptr = LINK[ptr]) System.out.print(INFO[ptr] + \" \");\n"
            + "        System.out.println();\n"
            + "        System.out.print(\"Enter item to insert at beginning: \");\n"
            + "        int item = sc.nextInt();\n"
            + "        if(AVAIL == -1) {\n"
            + "            System.out.println(\"OVERFLOW\");\n"
            + "            return;\n"
            + "        }\n"
            + "        int NEW = AVAIL;\n"
            + "        AVAIL = LINK[AVAIL];\n"
            + "        INFO[NEW] = item;\n"
            + "        LINK[NEW] = START;\n"
            + "        START = NEW;\n"
            + "        System.out.println(\"Inserted successfully\");\n"
            + "        System.out.print(\"Linked list after insertion: \");\n"
            + "        for(int ptr = START; ptr != -1; ptr = LINK[ptr]) System.out.print(INFO[ptr] + \" \");\n"
            + "        System.out.println();\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaInsertAfterGivenNodeLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return "// Recursion is not applicable here because insertion after a given node is a direct pointer operation.";
        }
        if ("Functions".equals(style)) {
            return "import java.util.Scanner;\n"
                + "class Main {\n"
                + "    static int MAX = 100;\n"
                + "    static int[] INFO = new int[MAX];\n"
                + "    static int[] LINK = new int[MAX];\n"
                + "    static int START = -1;\n"
                + "    static int AVAIL;\n"
                + "    static void init() {\n"
                + "        for(int i = 0; i < MAX - 1; i++)\n"
                + "            LINK[i] = i + 1;\n"
                + "        LINK[MAX - 1] = -1;\n"
                + "        AVAIL = 0;\n"
                + "    }\n"
                + "    static void append(int item) {\n"
                + "        if(AVAIL == -1) return;\n"
                + "        int NEW = AVAIL;\n"
                + "        AVAIL = LINK[AVAIL];\n"
                + "        INFO[NEW] = item;\n"
                + "        LINK[NEW] = -1;\n"
                + "        if(START == -1) START = NEW;\n"
                + "        else {\n"
                + "            int ptr = START;\n"
                + "            while(LINK[ptr] != -1) ptr = LINK[ptr];\n"
                + "            LINK[ptr] = NEW;\n"
                + "        }\n"
                + "    }\n"
                + "    static void insLoc(int loc, int item) {\n"
                + "        if(AVAIL == -1) {\n"
                + "            System.out.println(\"OVERFLOW\");\n"
                + "            return;\n"
                + "        }\n"
                + "        int NEW = AVAIL;\n"
                + "        AVAIL = LINK[AVAIL];\n"
                + "        INFO[NEW] = item;\n"
                + "        if(loc == -1) {\n"
                + "            LINK[NEW] = START;\n"
                + "            START = NEW;\n"
                + "        } else {\n"
                + "            LINK[NEW] = LINK[loc];\n"
                + "            LINK[loc] = NEW;\n"
                + "        }\n"
                + "    }\n"
                + "    static void display() {\n"
                + "        System.out.print(\"Linked list: \");\n"
                + "        for(int ptr = START; ptr != -1; ptr = LINK[ptr]) System.out.print(INFO[ptr] + \" \");\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        init();\n"
                + "        System.out.print(\"Enter number of existing nodes: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        System.out.println(\"Enter node values:\");\n"
                + "        for(int i = 0; i < n; i++) append(sc.nextInt());\n"
                + "        display();\n"
                + "        System.out.print(\"Enter item to insert: \");\n"
                + "        int item = sc.nextInt();\n"
                + "        System.out.print(\"Enter location index after which to insert (-1 for beginning): \");\n"
                + "        int loc = sc.nextInt();\n"
                + "        insLoc(loc, item);\n"
                + "        System.out.println(\"Inserted successfully\");\n"
                + "        display();\n"
                + "    }\n"
                + "}";
        }
        return "import java.util.Scanner;\n"
            + "class Main {\n"
            + "    static int MAX = 100;\n"
            + "    static int[] INFO = new int[MAX];\n"
            + "    static int[] LINK = new int[MAX];\n"
            + "    static int START = -1;\n"
            + "    static int AVAIL;\n"
            + "    static void init() {\n"
            + "        for(int i = 0; i < MAX - 1; i++)\n"
            + "            LINK[i] = i + 1;\n"
            + "        LINK[MAX - 1] = -1;\n"
            + "        AVAIL = 0;\n"
            + "    }\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        init();\n"
            + "        int item = sc.nextInt();\n"
            + "        int loc = sc.nextInt();\n"
            + "        if(AVAIL == -1) {\n"
            + "            System.out.println(\"OVERFLOW\");\n"
            + "            return;\n"
            + "        }\n"
            + "        int NEW = AVAIL;\n"
            + "        AVAIL = LINK[AVAIL];\n"
            + "        INFO[NEW] = item;\n"
            + "        if(loc == -1) {\n"
            + "            LINK[NEW] = START;\n"
            + "            START = NEW;\n"
            + "        } else {\n"
            + "            LINK[NEW] = LINK[loc];\n"
            + "            LINK[loc] = NEW;\n"
            + "        }\n"
            + "        System.out.println(\"Inserted successfully\");\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaInsertIntoSortedLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return "class Main {\n"
                + "    static int[] INFO = new int[100];\n"
                + "    static int[] LINK = new int[100];\n"
                + "    static int findARec(int ptr, int item, int start) {\n"
                + "        if(ptr == -1)\n"
                + "            return start;\n"
                + "        if(item < INFO[ptr])\n"
                + "            return start;\n"
                + "        return findARec(LINK[ptr], item, ptr);\n"
                + "    }\n"
                + "}";
        }
        return "import java.util.Scanner;\n"
            + "class Main {\n"
            + "    static int MAX = 100;\n"
            + "    static int[] INFO = new int[MAX];\n"
            + "    static int[] LINK = new int[MAX];\n"
            + "    static int START = -1;\n"
            + "    static int AVAIL;\n"
            + "    static void init() {\n"
            + "        for(int i = 0; i < MAX - 1; i++)\n"
            + "            LINK[i] = i + 1;\n"
            + "        LINK[MAX - 1] = -1;\n"
            + "        AVAIL = 0;\n"
            + "    }\n"
            + "    static void append(int item) {\n"
            + "        if(AVAIL == -1) return;\n"
            + "        int NEW = AVAIL;\n"
            + "        AVAIL = LINK[AVAIL];\n"
            + "        INFO[NEW] = item;\n"
            + "        LINK[NEW] = -1;\n"
            + "        if(START == -1) START = NEW;\n"
            + "        else {\n"
            + "            int ptr = START;\n"
            + "            while(LINK[ptr] != -1) ptr = LINK[ptr];\n"
            + "            LINK[ptr] = NEW;\n"
            + "        }\n"
            + "    }\n"
            + "    static int findA(int item) {\n"
            + "        if(START == -1) return -1;\n"
            + "        if(item < INFO[START]) return -1;\n"
            + "        int save = START;\n"
            + "        int ptr = LINK[START];\n"
            + "        while(ptr != -1) {\n"
            + "            if(item < INFO[ptr])\n"
            + "                return save;\n"
            + "            save = ptr;\n"
            + "            ptr = LINK[ptr];\n"
            + "        }\n"
            + "        return save;\n"
            + "    }\n"
            + "    static void insLoc(int loc, int item) {\n"
            + "        if(AVAIL == -1) return;\n"
            + "        int NEW = AVAIL;\n"
            + "        AVAIL = LINK[AVAIL];\n"
            + "        INFO[NEW] = item;\n"
            + "        if(loc == -1) {\n"
            + "            LINK[NEW] = START;\n"
            + "            START = NEW;\n"
            + "        } else {\n"
            + "            LINK[NEW] = LINK[loc];\n"
            + "            LINK[loc] = NEW;\n"
            + "        }\n"
            + "    }\n"
            + "    static void display() {\n"
            + "        System.out.print(\"Sorted linked list: \");\n"
            + "        for(int ptr = START; ptr != -1; ptr = LINK[ptr]) System.out.print(INFO[ptr] + \" \");\n"
            + "        System.out.println();\n"
            + "    }\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        init();\n"
            + "        System.out.print(\"Enter number of existing sorted nodes: \");\n"
            + "        int n = sc.nextInt();\n"
            + "        System.out.println(\"Enter sorted node values:\");\n"
            + "        for(int i = 0; i < n; i++) append(sc.nextInt());\n"
            + "        display();\n"
            + "        System.out.print(\"Enter item to insert: \");\n"
            + "        int item = sc.nextInt();\n"
            + "        int loc = findA(item);\n"
            + "        insLoc(loc, item);\n"
            + "        System.out.println(\"Inserted successfully\");\n"
            + "        display();\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaDeleteNodeGivenLocationLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return "// Recursion is not applicable here because deletion by location is a direct pointer update.";
        }
        if ("Functions".equals(style)) {
            return "import java.util.Scanner;\n"
                + "class Main {\n"
                + "    static int MAX = 100;\n"
                + "    static int[] INFO = new int[MAX];\n"
                + "    static int[] LINK = new int[MAX];\n"
                + "    static int START = -1;\n"
                + "    static int AVAIL;\n"
                + "    static void init() {\n"
                + "        for(int i = 0; i < MAX - 1; i++) LINK[i] = i + 1;\n"
                + "        LINK[MAX - 1] = -1;\n"
                + "        AVAIL = 0;\n"
                + "    }\n"
                + "    static void append(int item) {\n"
                + "        if(AVAIL == -1) return;\n"
                + "        int NEW = AVAIL;\n"
                + "        AVAIL = LINK[AVAIL];\n"
                + "        INFO[NEW] = item;\n"
                + "        LINK[NEW] = -1;\n"
                + "        if(START == -1) START = NEW;\n"
                + "        else {\n"
                + "            int ptr = START;\n"
                + "            while(LINK[ptr] != -1) ptr = LINK[ptr];\n"
                + "            LINK[ptr] = NEW;\n"
                + "        }\n"
                + "    }\n"
                + "    static void del(int loc, int locp) {\n"
                + "        if(locp == -1) START = LINK[START];\n"
                + "        else LINK[locp] = LINK[loc];\n"
                + "        LINK[loc] = AVAIL;\n"
                + "        AVAIL = loc;\n"
                + "    }\n"
                + "    static void display() {\n"
                + "        System.out.print(\"Linked list: \");\n"
                + "        for(int ptr = START; ptr != -1; ptr = LINK[ptr]) System.out.print(INFO[ptr] + \" \");\n"
                + "        System.out.println();\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        init();\n"
                + "        System.out.print(\"Enter number of nodes: \");\n"
                + "        int n = sc.nextInt();\n"
                + "        System.out.println(\"Enter node values:\");\n"
                + "        for(int i = 0; i < n; i++) append(sc.nextInt());\n"
                + "        display();\n"
                + "        System.out.print(\"Enter location index to delete: \");\n"
                + "        int loc = sc.nextInt();\n"
                + "        System.out.print(\"Enter predecessor location index (-1 if deleting first node): \");\n"
                + "        int locp = sc.nextInt();\n"
                + "        del(loc, locp);\n"
                + "        System.out.println(\"Deleted successfully\");\n"
                + "        display();\n"
                + "    }\n"
                + "}";
        }
        return "import java.util.Scanner;\n"
            + "class Main {\n"
            + "    static int MAX = 100;\n"
            + "    static int[] INFO = new int[MAX];\n"
            + "    static int[] LINK = new int[MAX];\n"
            + "    static int START = -1;\n"
            + "    static int AVAIL;\n"
            + "    static void init() {\n"
            + "        for(int i = 0; i < MAX - 1; i++) LINK[i] = i + 1;\n"
            + "        LINK[MAX - 1] = -1;\n"
            + "        AVAIL = 0;\n"
            + "    }\n"
            + "    static void append(int item) {\n"
            + "        if(AVAIL == -1) return;\n"
            + "        int NEW = AVAIL;\n"
            + "        AVAIL = LINK[AVAIL];\n"
            + "        INFO[NEW] = item;\n"
            + "        LINK[NEW] = -1;\n"
            + "        if(START == -1) START = NEW;\n"
            + "        else {\n"
            + "            int ptr = START;\n"
            + "            while(LINK[ptr] != -1) ptr = LINK[ptr];\n"
            + "            LINK[ptr] = NEW;\n"
            + "        }\n"
            + "    }\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        init();\n"
            + "        System.out.print(\"Enter number of nodes: \");\n"
            + "        int n = sc.nextInt();\n"
            + "        System.out.println(\"Enter node values:\");\n"
            + "        for(int i = 0; i < n; i++) append(sc.nextInt());\n"
            + "        System.out.print(\"Current linked list: \");\n"
            + "        for(int ptr = START; ptr != -1; ptr = LINK[ptr]) System.out.print(INFO[ptr] + \" \");\n"
            + "        System.out.println();\n"
            + "        System.out.print(\"Enter location index to delete: \");\n"
            + "        int loc = sc.nextInt();\n"
            + "        System.out.print(\"Enter predecessor location index (-1 if deleting first node): \");\n"
            + "        int locp = sc.nextInt();\n"
            + "        if(locp == -1) {\n"
            + "            START = LINK[START];\n"
            + "        } else {\n"
            + "            LINK[locp] = LINK[loc];\n"
            + "        }\n"
            + "        LINK[loc] = AVAIL;\n"
            + "        AVAIL = loc;\n"
            + "        System.out.println(\"Deleted successfully\");\n"
            + "        System.out.print(\"Linked list after deletion: \");\n"
            + "        for(int ptr = START; ptr != -1; ptr = LINK[ptr]) System.out.print(INFO[ptr] + \" \");\n"
            + "        System.out.println();\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaDeleteNodeByItemLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return "// Recursion is not suitable here because predecessor tracking is required.";
        }
        return "import java.util.Scanner;\n"
            + "class Main {\n"
            + "    static int MAX = 100;\n"
            + "    static int[] INFO = new int[MAX];\n"
            + "    static int[] LINK = new int[MAX];\n"
            + "    static int START = -1;\n"
            + "    static int AVAIL;\n"
            + "    static void init() {\n"
            + "        for(int i = 0; i < MAX - 1; i++) LINK[i] = i + 1;\n"
            + "        LINK[MAX - 1] = -1;\n"
            + "        AVAIL = 0;\n"
            + "    }\n"
            + "    static void append(int item) {\n"
            + "        if(AVAIL == -1) return;\n"
            + "        int NEW = AVAIL;\n"
            + "        AVAIL = LINK[AVAIL];\n"
            + "        INFO[NEW] = item;\n"
            + "        LINK[NEW] = -1;\n"
            + "        if(START == -1) START = NEW;\n"
            + "        else {\n"
            + "            int ptr = START;\n"
            + "            while(LINK[ptr] != -1) ptr = LINK[ptr];\n"
            + "            LINK[ptr] = NEW;\n"
            + "        }\n"
            + "    }\n"
            + "    static int[] findb(int item) {\n"
            + "        int[] res = new int[]{-1, -1};\n"
            + "        if(START == -1) return res;\n"
            + "        if(INFO[START] == item) {\n"
            + "            res[0] = START;\n"
            + "            return res;\n"
            + "        }\n"
            + "        int save = START;\n"
            + "        int ptr = LINK[START];\n"
            + "        while(ptr != -1) {\n"
            + "            if(INFO[ptr] == item) {\n"
            + "                res[0] = ptr;\n"
            + "                res[1] = save;\n"
            + "                return res;\n"
            + "            }\n"
            + "            save = ptr;\n"
            + "            ptr = LINK[ptr];\n"
            + "        }\n"
            + "        return res;\n"
            + "    }\n"
            + "    static void deleteItem(int item) {\n"
            + "        int[] res = findb(item);\n"
            + "        int loc = res[0];\n"
            + "        int locp = res[1];\n"
            + "        if(loc == -1) {\n"
            + "            System.out.println(\"ITEM not in list\");\n"
            + "            return;\n"
            + "        }\n"
            + "        if(locp == -1) START = LINK[START];\n"
            + "        else LINK[locp] = LINK[loc];\n"
            + "        LINK[loc] = AVAIL;\n"
            + "        AVAIL = loc;\n"
            + "    }\n"
            + "    public static void main(String[] args) {\n"
            + "        init();\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        System.out.print(\"Enter number of nodes: \");\n"
            + "        int n = sc.nextInt();\n"
            + "        System.out.println(\"Enter node values:\");\n"
            + "        for(int i = 0; i < n; i++) append(sc.nextInt());\n"
            + "        System.out.print(\"Current linked list: \");\n"
            + "        for(int ptr = START; ptr != -1; ptr = LINK[ptr]) System.out.print(INFO[ptr] + \" \");\n"
            + "        System.out.println();\n"
            + "        System.out.print(\"Enter item to delete: \");\n"
            + "        int item = sc.nextInt();\n"
            + "        deleteItem(item);\n"
            + "        System.out.println(\"Deletion completed\");\n"
            + "        System.out.print(\"Linked list after deletion: \");\n"
            + "        for(int ptr = START; ptr != -1; ptr = LINK[ptr]) System.out.print(INFO[ptr] + \" \");\n"
            + "        System.out.println();\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaTraverseCircularHeaderList(String style) {
        if ("Recursion".equals(style)) {
            return "import java.util.*;\n"
                + "class Node {\n"
                + "    int data;\n"
                + "    Node next;\n"
                + "    Node(int data) {\n"
                + "        this.data = data;\n"
                + "        this.next = null;\n"
                + "    }\n"
                + "}\n"
                + "public class CircularHeaderList {\n"
                + "    static void traverse(Node ptr, Node header) {\n"
                + "        if(ptr == header) {\n"
                + "            return;\n"
                + "        }\n"
                + "        System.out.print(ptr.data + \" \");\n"
                + "        traverse(ptr.next, header);\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        int n = sc.nextInt();\n"
                + "        Node header = new Node(-1);\n"
                + "        header.next = header;\n"
                + "        Node last = header;\n"
                + "        for(int i = 0; i < n; i++) {\n"
                + "            int val = sc.nextInt();\n"
                + "            Node temp = new Node(val);\n"
                + "            last.next = temp;\n"
                + "            temp.next = header;\n"
                + "            last = temp;\n"
                + "        }\n"
                + "        traverse(header.next, header);\n"
                + "    }\n"
                + "}";
        }
        if ("Functions".equals(style)) {
            return "import java.util.*;\n"
                + "class Node {\n"
                + "    int data;\n"
                + "    Node next;\n"
                + "    Node(int data) {\n"
                + "        this.data = data;\n"
                + "        this.next = null;\n"
                + "    }\n"
                + "}\n"
                + "public class CircularHeaderList {\n"
                + "    static void traverse(Node header) {\n"
                + "        Node ptr = header.next;\n"
                + "        while(ptr != header) {\n"
                + "            System.out.print(ptr.data + \" \");\n"
                + "            ptr = ptr.next;\n"
                + "        }\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        int n = sc.nextInt();\n"
                + "        Node header = new Node(-1);\n"
                + "        header.next = header;\n"
                + "        Node last = header;\n"
                + "        for(int i = 0; i < n; i++) {\n"
                + "            int val = sc.nextInt();\n"
                + "            Node temp = new Node(val);\n"
                + "            last.next = temp;\n"
                + "            temp.next = header;\n"
                + "            last = temp;\n"
                + "        }\n"
                + "        traverse(header);\n"
                + "    }\n"
                + "}";
        }
        return "import java.util.*;\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node next;\n"
            + "    Node(int data) {\n"
            + "        this.data = data;\n"
            + "        this.next = null;\n"
            + "    }\n"
            + "}\n"
            + "public class CircularHeaderList {\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        int n = sc.nextInt();\n"
            + "        Node header = new Node(-1);\n"
            + "        header.next = header;\n"
            + "        Node last = header;\n"
            + "        for(int i = 0; i < n; i++) {\n"
            + "            int val = sc.nextInt();\n"
            + "            Node temp = new Node(val);\n"
            + "            last.next = temp;\n"
            + "            temp.next = header;\n"
            + "            last = temp;\n"
            + "        }\n"
            + "        Node ptr = header.next;\n"
            + "        while(ptr != header) {\n"
            + "            System.out.print(ptr.data + \" \");\n"
            + "            ptr = ptr.next;\n"
            + "        }\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaSearchCircularHeaderList(String style) {
        if ("Recursion".equals(style)) {
            return "import java.util.*;\n"
                + "class Node {\n"
                + "    int data;\n"
                + "    Node next;\n"
                + "    Node(int data) { this.data = data; this.next = null; }\n"
                + "}\n"
                + "public class CircularSearch {\n"
                + "    static Node search(Node ptr, Node header, int item) {\n"
                + "        if(ptr == header)\n"
                + "            return null;\n"
                + "        if(ptr.data == item)\n"
                + "            return ptr;\n"
                + "        return search(ptr.next, header, item);\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        int n = sc.nextInt();\n"
                + "        Node header = new Node(-1);\n"
                + "        header.next = header;\n"
                + "        Node last = header;\n"
                + "        for(int i = 0; i < n; i++) {\n"
                + "            int val = sc.nextInt();\n"
                + "            Node temp = new Node(val);\n"
                + "            last.next = temp;\n"
                + "            temp.next = header;\n"
                + "            last = temp;\n"
                + "        }\n"
                + "        int item = sc.nextInt();\n"
                + "        Node result = search(header.next, header, item);\n"
                + "        if(result != null)\n"
                + "            System.out.println(\"Element found\");\n"
                + "        else\n"
                + "            System.out.println(\"Element not found\");\n"
                + "    }\n"
                + "}";
        }
        if ("Functions".equals(style)) {
            return "import java.util.*;\n"
                + "class Node {\n"
                + "    int data;\n"
                + "    Node next;\n"
                + "    Node(int data) { this.data = data; this.next = null; }\n"
                + "}\n"
                + "public class CircularSearch {\n"
                + "    static Node search(Node header, int item) {\n"
                + "        Node ptr = header.next;\n"
                + "        while(ptr != header && ptr.data != item) {\n"
                + "            ptr = ptr.next;\n"
                + "        }\n"
                + "        if(ptr != header)\n"
                + "            return ptr;\n"
                + "        return null;\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        int n = sc.nextInt();\n"
                + "        Node header = new Node(-1);\n"
                + "        header.next = header;\n"
                + "        Node last = header;\n"
                + "        for(int i = 0; i < n; i++) {\n"
                + "            int val = sc.nextInt();\n"
                + "            Node temp = new Node(val);\n"
                + "            last.next = temp;\n"
                + "            temp.next = header;\n"
                + "            last = temp;\n"
                + "        }\n"
                + "        int item = sc.nextInt();\n"
                + "        Node result = search(header, item);\n"
                + "        if(result != null)\n"
                + "            System.out.println(\"Element found\");\n"
                + "        else\n"
                + "            System.out.println(\"Element not found\");\n"
                + "    }\n"
                + "}";
        }
        return "import java.util.*;\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node next;\n"
            + "    Node(int data) { this.data = data; this.next = null; }\n"
            + "}\n"
            + "public class CircularSearch {\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        int n = sc.nextInt();\n"
            + "        Node header = new Node(-1);\n"
            + "        header.next = header;\n"
            + "        Node last = header;\n"
            + "        for(int i = 0; i < n; i++) {\n"
            + "            int val = sc.nextInt();\n"
            + "            Node temp = new Node(val);\n"
            + "            last.next = temp;\n"
            + "            temp.next = header;\n"
            + "            last = temp;\n"
            + "        }\n"
            + "        int item = sc.nextInt();\n"
            + "        Node ptr = header.next;\n"
            + "        while(ptr != header && ptr.data != item) {\n"
            + "            ptr = ptr.next;\n"
            + "        }\n"
            + "        if(ptr != header)\n"
            + "            System.out.println(\"Element found\");\n"
            + "        else\n"
            + "            System.out.println(\"Element not found\");\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaDeleteCircularHeaderList(String style) {
        if ("Recursion".equals(style)) {
            return "import java.util.*;\n"
                + "class Node {\n"
                + "    int data;\n"
                + "    Node next;\n"
                + "    Node(int data) { this.data = data; this.next = null; }\n"
                + "}\n"
                + "public class DeleteCircularHL {\n"
                + "    static Node[] find(Node ptr, Node header, Node prev, int item) {\n"
                + "        if(ptr == header)\n"
                + "            return new Node[]{null, prev};\n"
                + "        if(ptr.data == item)\n"
                + "            return new Node[]{ptr, prev};\n"
                + "        return find(ptr.next, header, ptr, item);\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        int n = sc.nextInt();\n"
                + "        Node header = new Node(-1);\n"
                + "        header.next = header;\n"
                + "        Node last = header;\n"
                + "        for(int i = 0; i < n; i++) {\n"
                + "            int val = sc.nextInt();\n"
                + "            Node temp = new Node(val);\n"
                + "            last.next = temp;\n"
                + "            temp.next = header;\n"
                + "            last = temp;\n"
                + "        }\n"
                + "        int item = sc.nextInt();\n"
                + "        Node[] res = find(header.next, header, header, item);\n"
                + "        Node loc = res[0];\n"
                + "        Node locp = res[1];\n"
                + "        if(loc == null) {\n"
                + "            System.out.println(\"Item not found\");\n"
                + "        } else {\n"
                + "            locp.next = loc.next;\n"
                + "            System.out.println(\"Item deleted\");\n"
                + "        }\n"
                + "    }\n"
                + "}";
        }
        if ("Functions".equals(style)) {
            return "import java.util.*;\n"
                + "class Node {\n"
                + "    int data;\n"
                + "    Node next;\n"
                + "    Node(int data) { this.data = data; this.next = null; }\n"
                + "}\n"
                + "public class DeleteCircularHL {\n"
                + "    static Node[] find(Node header, int item) {\n"
                + "        Node ptr = header.next;\n"
                + "        Node save = header;\n"
                + "        while(ptr != header && ptr.data != item) {\n"
                + "            save = ptr;\n"
                + "            ptr = ptr.next;\n"
                + "        }\n"
                + "        if(ptr == header)\n"
                + "            return new Node[]{null, save};\n"
                + "        return new Node[]{ptr, save};\n"
                + "    }\n"
                + "    static void delete(Node header, int item) {\n"
                + "        Node[] res = find(header, item);\n"
                + "        Node loc = res[0];\n"
                + "        Node locp = res[1];\n"
                + "        if(loc == null) {\n"
                + "            System.out.println(\"Item not found\");\n"
                + "            return;\n"
                + "        }\n"
                + "        locp.next = loc.next;\n"
                + "        System.out.println(\"Item deleted\");\n"
                + "    }\n"
                + "    public static void main(String[] args) {\n"
                + "        Scanner sc = new Scanner(System.in);\n"
                + "        int n = sc.nextInt();\n"
                + "        Node header = new Node(-1);\n"
                + "        header.next = header;\n"
                + "        Node last = header;\n"
                + "        for(int i = 0; i < n; i++) {\n"
                + "            int val = sc.nextInt();\n"
                + "            Node temp = new Node(val);\n"
                + "            last.next = temp;\n"
                + "            temp.next = header;\n"
                + "            last = temp;\n"
                + "        }\n"
                + "        int item = sc.nextInt();\n"
                + "        delete(header, item);\n"
                + "    }\n"
                + "}";
        }
        return "import java.util.*;\n"
            + "class Node {\n"
            + "    int data;\n"
            + "    Node next;\n"
            + "    Node(int data) { this.data = data; this.next = null; }\n"
            + "}\n"
            + "public class DeleteCircularHL {\n"
            + "    public static void main(String[] args) {\n"
            + "        Scanner sc = new Scanner(System.in);\n"
            + "        int n = sc.nextInt();\n"
            + "        Node header = new Node(-1);\n"
            + "        header.next = header;\n"
            + "        Node last = header;\n"
            + "        for(int i = 0; i < n; i++) {\n"
            + "            int val = sc.nextInt();\n"
            + "            Node temp = new Node(val);\n"
            + "            last.next = temp;\n"
            + "            temp.next = header;\n"
            + "            last = temp;\n"
            + "        }\n"
            + "        int item = sc.nextInt();\n"
            + "        Node ptr = header.next;\n"
            + "        Node save = header;\n"
            + "        while(ptr != header && ptr.data != item) {\n"
            + "            save = ptr;\n"
            + "            ptr = ptr.next;\n"
            + "        }\n"
            + "        if(ptr == header) {\n"
            + "            System.out.println(\"Item not found\");\n"
            + "        } else {\n"
            + "            save.next = ptr.next;\n"
            + "            System.out.println(\"Item deleted\");\n"
            + "        }\n"
            + "    }\n"
            + "}";
    }

    private static String getJavaWarshallAlgorithm(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "public class Main {",
                "    static void initMatrix(int[][] A, int[][] P, int m) {",
                "        for (int i = 1; i <= m; i++)",
                "            for (int j = 1; j <= m; j++)",
                "                P[i][j] = (A[i][j] == 0) ? 0 : 1;",
                "    }",
                "    static void updateMatrix(int[][] P, int m) {",
                "        for (int k = 1; k <= m; k++)",
                "            for (int i = 1; i <= m; i++)",
                "                for (int j = 1; j <= m; j++)",
                "                    P[i][j] = P[i][j] | (P[i][k] & P[k][j]);",
                "    }",
                "    public static void main(String[] args) {",
                "        Scanner sc = new Scanner(System.in);",
                "        int m = sc.nextInt();",
                "        int[][] A = new int[m + 1][m + 1];",
                "        int[][] P = new int[m + 1][m + 1];",
                "        for (int i = 1; i <= m; i++)",
                "            for (int j = 1; j <= m; j++)",
                "                A[i][j] = sc.nextInt();",
                "        initMatrix(A, P, m);",
                "        updateMatrix(P, m);",
                "        for (int i = 1; i <= m; i++) {",
                "            for (int j = 1; j <= m; j++) System.out.print(P[i][j] + \" \");",
                "            System.out.println();",
                "        }",
                "    }",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "public class Main {",
                "    static void warshallRec(int[][] P, int k, int m) {",
                "        if (k > m) return;",
                "        for (int i = 1; i <= m; i++)",
                "            for (int j = 1; j <= m; j++)",
                "                P[i][j] = P[i][j] | (P[i][k] & P[k][j]);",
                "        warshallRec(P, k + 1, m);",
                "    }",
                "    public static void main(String[] args) {",
                "        Scanner sc = new Scanner(System.in);",
                "        int m = sc.nextInt();",
                "        int[][] A = new int[m + 1][m + 1];",
                "        int[][] P = new int[m + 1][m + 1];",
                "        for (int i = 1; i <= m; i++)",
                "            for (int j = 1; j <= m; j++)",
                "                A[i][j] = sc.nextInt();",
                "        for (int i = 1; i <= m; i++)",
                "            for (int j = 1; j <= m; j++)",
                "                P[i][j] = (A[i][j] == 0) ? 0 : 1;",
                "        warshallRec(P, 1, m);",
                "        for (int i = 1; i <= m; i++) {",
                "            for (int j = 1; j <= m; j++) System.out.print(P[i][j] + \" \");",
                "            System.out.println();",
                "        }",
                "    }",
                "}");
        }
        return String.join("\n",
            "import java.util.*;",
            "public class Main {",
            "    public static void main(String[] args) {",
            "        Scanner sc = new Scanner(System.in);",
            "        int m = sc.nextInt();",
            "        int[][] A = new int[m + 1][m + 1];",
            "        int[][] P = new int[m + 1][m + 1];",
            "        for (int i = 1; i <= m; i++)",
            "            for (int j = 1; j <= m; j++)",
            "                A[i][j] = sc.nextInt();",
            "        for (int i = 1; i <= m; i++)",
            "            for (int j = 1; j <= m; j++)",
            "                P[i][j] = (A[i][j] == 0) ? 0 : 1;",
            "        for (int k = 1; k <= m; k++)",
            "            for (int i = 1; i <= m; i++)",
            "                for (int j = 1; j <= m; j++)",
            "                    P[i][j] = P[i][j] | (P[i][k] & P[k][j]);",
            "        for (int i = 1; i <= m; i++) {",
            "            for (int j = 1; j <= m; j++) System.out.print(P[i][j] + \" \");",
            "            System.out.println();",
            "        }",
            "    }",
            "}");
    }

    private static String getJavaFloydAlgorithm(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "public class Main {",
                "    static final int INF = 999999;",
                "    static void initMatrix(int[][] W, int[][] Q, int m) {",
                "        for (int i = 1; i <= m; i++)",
                "            for (int j = 1; j <= m; j++)",
                "                Q[i][j] = (W[i][j] == 0) ? INF : W[i][j];",
                "    }",
                "    static void updateMatrix(int[][] Q, int m) {",
                "        for (int k = 1; k <= m; k++)",
                "            for (int i = 1; i <= m; i++)",
                "                for (int j = 1; j <= m; j++)",
                "                    Q[i][j] = Math.min(Q[i][j], Q[i][k] + Q[k][j]);",
                "    }",
                "    public static void main(String[] args) {",
                "        Scanner sc = new Scanner(System.in);",
                "        int m = sc.nextInt();",
                "        int[][] W = new int[m + 1][m + 1];",
                "        int[][] Q = new int[m + 1][m + 1];",
                "        for (int i = 1; i <= m; i++)",
                "            for (int j = 1; j <= m; j++)",
                "                W[i][j] = sc.nextInt();",
                "        initMatrix(W, Q, m);",
                "        updateMatrix(Q, m);",
                "        for (int i = 1; i <= m; i++) {",
                "            for (int j = 1; j <= m; j++) System.out.print(Q[i][j] + \" \");",
                "            System.out.println();",
                "        }",
                "    }",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "public class Main {",
                "    static final int INF = 999999;",
                "    static void floydRec(int[][] Q, int k, int m) {",
                "        if (k > m) return;",
                "        for (int i = 1; i <= m; i++)",
                "            for (int j = 1; j <= m; j++)",
                "                Q[i][j] = Math.min(Q[i][j], Q[i][k] + Q[k][j]);",
                "        floydRec(Q, k + 1, m);",
                "    }",
                "    public static void main(String[] args) {",
                "        Scanner sc = new Scanner(System.in);",
                "        int m = sc.nextInt();",
                "        int[][] W = new int[m + 1][m + 1];",
                "        int[][] Q = new int[m + 1][m + 1];",
                "        for (int i = 1; i <= m; i++)",
                "            for (int j = 1; j <= m; j++)",
                "                W[i][j] = sc.nextInt();",
                "        for (int i = 1; i <= m; i++)",
                "            for (int j = 1; j <= m; j++)",
                "                Q[i][j] = (W[i][j] == 0) ? INF : W[i][j];",
                "        floydRec(Q, 1, m);",
                "        for (int i = 1; i <= m; i++) {",
                "            for (int j = 1; j <= m; j++) System.out.print(Q[i][j] + \" \");",
                "            System.out.println();",
                "        }",
                "    }",
                "}");
        }
        return String.join("\n",
            "import java.util.*;",
            "public class Main {",
            "    static final int INF = 999999;",
            "    public static void main(String[] args) {",
            "        Scanner sc = new Scanner(System.in);",
            "        int m = sc.nextInt();",
            "        int[][] W = new int[m + 1][m + 1];",
            "        int[][] Q = new int[m + 1][m + 1];",
            "        for (int i = 1; i <= m; i++)",
            "            for (int j = 1; j <= m; j++)",
            "                W[i][j] = sc.nextInt();",
            "        for (int i = 1; i <= m; i++)",
            "            for (int j = 1; j <= m; j++)",
            "                Q[i][j] = (W[i][j] == 0) ? INF : W[i][j];",
            "        for (int k = 1; k <= m; k++)",
            "            for (int i = 1; i <= m; i++)",
            "                for (int j = 1; j <= m; j++)",
            "                    Q[i][j] = Math.min(Q[i][j], Q[i][k] + Q[k][j]);",
            "        for (int i = 1; i <= m; i++) {",
            "            for (int j = 1; j <= m; j++) System.out.print(Q[i][j] + \" \");",
            "            System.out.println();",
            "        }",
            "    }",
            "}");
    }

    private static String getJavaFindEdgeGraph(String style) { return String.join("\n", "import java.util.*;", "public class Main {", "    public static void main(String[] args) {", "        Scanner sc = new Scanner(System.in);", "        System.out.print(\"Enter number of vertices: \");", "        int n = sc.nextInt();", "        int[][] graph = new int[n][n];", "        System.out.println(\"Enter adjacency matrix:\");", "        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) graph[i][j] = sc.nextInt();", "        System.out.print(\"Enter source vertex: \");", "        int a = sc.nextInt();", "        System.out.print(\"Enter destination vertex: \");", "        int b = sc.nextInt();", "        if (a >= 0 && a < n && b >= 0 && b < n && graph[a][b] == 1) System.out.println(\"Edge Found\"); else System.out.println(\"Edge Not Found\");", "    }", "}"); }

    private static String getJavaInsertNodeGraph(String style) { return String.join("\n", "import java.util.*;", "public class Main {", "    static boolean exists(int[] node, int n, int item) { for (int i = 0; i < n; i++) if (node[i] == item) return true; return false; }", "    public static void main(String[] args) {", "        Scanner sc = new Scanner(System.in);", "        System.out.print(\"Enter number of existing nodes: \");", "        int n = sc.nextInt();", "        int[] node = new int[100];", "        System.out.println(\"Enter node values:\");", "        for (int i = 0; i < n; i++) node[i] = sc.nextInt();", "        System.out.print(\"Enter node value to insert: \");", "        int item = sc.nextInt();", "        if (exists(node, n, item)) System.out.println(\"Node Exists\"); else { node[n++] = item; System.out.println(\"Node Inserted\"); System.out.print(\"Nodes after insertion: \"); for (int i = 0; i < n; i++) System.out.print(node[i] + \" \"); }", "    }", "}"); }

    private static String getJavaInsertEdgeGraph(String style) { return String.join("\n", "import java.util.*;", "public class Main {", "    public static void main(String[] args) {", "        Scanner sc = new Scanner(System.in);", "        System.out.print(\"Enter number of vertices: \");", "        int n = sc.nextInt();", "        int[][] graph = new int[n][n];", "        System.out.println(\"Enter adjacency matrix:\");", "        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) graph[i][j] = sc.nextInt();", "        System.out.print(\"Enter source vertex: \");", "        int a = sc.nextInt();", "        System.out.print(\"Enter destination vertex: \");", "        int b = sc.nextInt();", "        if (a < 0 || a >= n || b < 0 || b >= n) System.out.println(\"Invalid Edge\"); else { graph[a][b] = 1; System.out.println(\"Edge Inserted\"); }", "    }", "}"); }

    private static String getJavaDeleteEdgeGraph(String style) { return String.join("\n", "import java.util.*;", "public class Main {", "    public static void main(String[] args) {", "        Scanner sc = new Scanner(System.in);", "        System.out.print(\"Enter number of vertices: \");", "        int n = sc.nextInt();", "        int[][] graph = new int[n][n];", "        System.out.println(\"Enter adjacency matrix:\");", "        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) graph[i][j] = sc.nextInt();", "        System.out.print(\"Enter source vertex: \");", "        int a = sc.nextInt();", "        System.out.print(\"Enter destination vertex: \");", "        int b = sc.nextInt();", "        if (a < 0 || a >= n || b < 0 || b >= n || graph[a][b] == 0) System.out.println(\"Edge Not Found\"); else { graph[a][b] = 0; System.out.println(\"Edge Deleted\"); }", "    }", "}"); }

    private static String getJavaDeleteVertexGraph(String style) { return String.join("\n", "import java.util.*;", "public class Main {", "    static void clearRec(int[][] graph, int target, int index, int n) { if (index == n) return; graph[target][index] = 0; graph[index][target] = 0; clearRec(graph, target, index + 1, n); }", "    public static void main(String[] args) {", "        Scanner sc = new Scanner(System.in);", "        System.out.print(\"Enter number of vertices: \");", "        int n = sc.nextInt();", "        int[][] graph = new int[n][n];", "        System.out.println(\"Enter adjacency matrix:\");", "        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) graph[i][j] = sc.nextInt();", "        System.out.print(\"Enter vertex index to delete: \");", "        int target = sc.nextInt();", "        if (target < 0 || target >= n) System.out.println(\"Node Not Found\"); else if (\"Recursion\".equals(\"" + "\")) System.out.println(\"Node Deleted\"); else { clearRec(graph, target, 0, n); System.out.println(\"Node Deleted\"); }", "    }", "}"); }

    private static String getJavaBreadthFirstSearch(String style) { return String.join("\n", "import java.util.*;", "public class Main {", "    public static void main(String[] args) {", "        Scanner sc = new Scanner(System.in);", "        System.out.print(\"Enter number of vertices: \");", "        int n = sc.nextInt();", "        int[][] graph = new int[n][n];", "        System.out.println(\"Enter adjacency matrix:\");", "        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) graph[i][j] = sc.nextInt();", "        System.out.print(\"Enter start vertex: \");", "        int start = sc.nextInt();", "        int[] status = new int[n];", "        Arrays.fill(status, 1);", "        Queue<Integer> q = new LinkedList<>();", "        q.add(start); status[start] = 2;", "        System.out.print(\"BFS traversal: \");", "        while (!q.isEmpty()) { int node = q.poll(); System.out.print(node + \" \"); status[node] = 3; for (int i = 0; i < n; i++) if (graph[node][i] == 1 && status[i] == 1) { q.add(i); status[i] = 2; } }", "    }", "}"); }

    private static String getJavaDepthFirstSearch(String style) { return String.join("\n", "import java.util.*;", "public class Main {", "    static void dfsRec(int[][] graph, int node, int[] status) { if (status[node] != 1) return; System.out.print(node + \" \"); status[node] = 3; for (int i = 0; i < graph.length; i++) if (graph[node][i] == 1 && status[i] == 1) dfsRec(graph, i, status); }", "    public static void main(String[] args) {", "        Scanner sc = new Scanner(System.in);", "        System.out.print(\"Enter number of vertices: \");", "        int n = sc.nextInt();", "        int[][] graph = new int[n][n];", "        System.out.println(\"Enter adjacency matrix:\");", "        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) graph[i][j] = sc.nextInt();", "        System.out.print(\"Enter start vertex: \");", "        int start = sc.nextInt();", "        int[] status = new int[n];", "        Arrays.fill(status, 1);", "        System.out.print(\"DFS traversal: \");", "        if (\"Recursion\".equals(\"" + "\")) dfsRec(graph, start, status); else { Stack<Integer> stack = new Stack<>(); stack.push(start); status[start] = 2; while (!stack.isEmpty()) { int node = stack.pop(); System.out.print(node + \" \"); status[node] = 3; for (int i = 0; i < n; i++) if (graph[node][i] == 1 && status[i] == 1) { stack.push(i); status[i] = 2; } } }", "    }", "}"); }

    private static String getJavaTopologicalSort(String style) { return String.join("\n", "import java.util.*;", "public class Main {", "    static void topoDFS(int[][] graph, int node, boolean[] visited, Stack<Integer> stack) { visited[node] = true; for (int i = 0; i < graph.length; i++) if (graph[node][i] == 1 && !visited[i]) topoDFS(graph, i, visited, stack); stack.push(node); }", "    public static void main(String[] args) {", "        Scanner sc = new Scanner(System.in);", "        System.out.print(\"Enter number of vertices: \");", "        int n = sc.nextInt();", "        int[][] graph = new int[n][n];", "        System.out.println(\"Enter adjacency matrix:\");", "        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) graph[i][j] = sc.nextInt();", "        System.out.print(\"Topological order: \");", "        if (\"Recursion\".equals(\"" + "\")) { boolean[] visited = new boolean[n]; Stack<Integer> stack = new Stack<>(); for (int i = 0; i < n; i++) if (!visited[i]) topoDFS(graph, i, visited, stack); while (!stack.isEmpty()) System.out.print(stack.pop() + \" \"); } else { int[] indeg = new int[n]; for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) if (graph[i][j] == 1) indeg[j]++; Queue<Integer> q = new LinkedList<>(); for (int i = 0; i < n; i++) if (indeg[i] == 0) q.add(i); while (!q.isEmpty()) { int node = q.poll(); System.out.print(node + \" \"); for (int j = 0; j < n; j++) if (graph[node][j] == 1) { indeg[j]--; if (indeg[j] == 0) q.add(j); } } }", "    }", "}"); }

    private static String getJavaInsertionSortPdf(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "public class Main {",
                "    static void insertElement(int[] A, int k) {",
                "        int temp = A[k];",
                "        int ptr = k - 1;",
                "        while (temp < A[ptr]) {",
                "            A[ptr + 1] = A[ptr];",
                "            ptr--;",
                "        }",
                "        A[ptr + 1] = temp;",
                "    }",
                "",
                "    static void insertionSort(int[] A, int n) {",
                "        A[0] = Integer.MIN_VALUE;",
                "        for (int k = 2; k <= n; k++) insertElement(A, k);",
                "    }",
                "",
                "    static void printArray(int[] A, int n) {",
                "        for (int i = 1; i <= n; i++) System.out.print(A[i] + \" \");",
                "        System.out.println();",
                "    }",
                "",
                "    public static void main(String[] args) {",
                "        Scanner sc = new Scanner(System.in);",
                "        System.out.print(\"Enter number of elements: \");",
                "        int n = sc.nextInt();",
                "        int[] A = new int[n + 1];",
                "        System.out.println(\"Enter elements:\");",
                "        for (int i = 1; i <= n; i++) A[i] = sc.nextInt();",
                "        insertionSort(A, n);",
                "        System.out.print(\"Sorted array: \");",
                "        printArray(A, n);",
                "    }",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "public class Main {",
                "    static void insertionRec(int[] A, int n) {",
                "        if (n <= 1) return;",
                "        insertionRec(A, n - 1);",
                "        int temp = A[n];",
                "        int ptr = n - 1;",
                "        while (ptr >= 1 && temp < A[ptr]) {",
                "            A[ptr + 1] = A[ptr];",
                "            ptr--;",
                "        }",
                "        A[ptr + 1] = temp;",
                "    }",
                "",
                "    static void printArray(int[] A, int n) {",
                "        for (int i = 1; i <= n; i++) System.out.print(A[i] + \" \");",
                "        System.out.println();",
                "    }",
                "",
                "    public static void main(String[] args) {",
                "        Scanner sc = new Scanner(System.in);",
                "        System.out.print(\"Enter number of elements: \");",
                "        int n = sc.nextInt();",
                "        int[] A = new int[n + 1];",
                "        A[0] = Integer.MIN_VALUE;",
                "        System.out.println(\"Enter elements:\");",
                "        for (int i = 1; i <= n; i++) A[i] = sc.nextInt();",
                "        insertionRec(A, n);",
                "        System.out.print(\"Sorted array: \");",
                "        printArray(A, n);",
                "    }",
                "}");
        }
        return String.join("\n",
            "import java.util.*;",
            "public class Main {",
            "    static void printArray(int[] A, int n) {",
            "        for (int i = 1; i <= n; i++) System.out.print(A[i] + \" \");",
            "        System.out.println();",
            "    }",
            "",
            "    public static void main(String[] args) {",
            "        Scanner sc = new Scanner(System.in);",
            "        System.out.print(\"Enter number of elements: \");",
            "        int n = sc.nextInt();",
            "        int[] A = new int[n + 1];",
            "        A[0] = Integer.MIN_VALUE;",
            "        System.out.println(\"Enter elements:\");",
            "        for (int i = 1; i <= n; i++) A[i] = sc.nextInt();",
            "        for (int k = 2; k <= n; k++) {",
            "            int temp = A[k];",
            "            int ptr = k - 1;",
            "            while (temp < A[ptr]) {",
            "                A[ptr + 1] = A[ptr];",
            "                ptr--;",
            "            }",
            "            A[ptr + 1] = temp;",
            "        }",
            "        System.out.print(\"Sorted array: \");",
            "        printArray(A, n);",
            "    }",
            "}");
    }

    private static String getJavaSelectionSortPdf(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "public class Main {",
                "    static int findMin(int[] A, int k, int n) {",
                "        int min = A[k], loc = k;",
                "        for (int j = k + 1; j <= n; j++) if (min > A[j]) { min = A[j]; loc = j; }",
                "        return loc;",
                "    }",
                "",
                "    static void swap(int[] A, int i, int j) {",
                "        int temp = A[i];",
                "        A[i] = A[j];",
                "        A[j] = temp;",
                "    }",
                "",
                "    static void selectionSort(int[] A, int n) {",
                "        for (int k = 1; k <= n - 1; k++) {",
                "            int loc = findMin(A, k, n);",
                "            swap(A, k, loc);",
                "        }",
                "    }",
                "",
                "    static void printArray(int[] A, int n) {",
                "        for (int i = 1; i <= n; i++) System.out.print(A[i] + \" \");",
                "        System.out.println();",
                "    }",
                "",
                "    public static void main(String[] args) {",
                "        Scanner sc = new Scanner(System.in);",
                "        System.out.print(\"Enter number of elements: \");",
                "        int n = sc.nextInt();",
                "        int[] A = new int[n + 1];",
                "        System.out.println(\"Enter elements:\");",
                "        for (int i = 1; i <= n; i++) A[i] = sc.nextInt();",
                "        selectionSort(A, n);",
                "        System.out.print(\"Sorted array: \");",
                "        printArray(A, n);",
                "    }",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "import java.util.*;",
                "public class Main {",
                "    static void selectionRec(int[] A, int k, int n) {",
                "        if (k == n) return;",
                "        int loc = k;",
                "        for (int j = k + 1; j <= n; j++) if (A[j] < A[loc]) loc = j;",
                "        int temp = A[k];",
                "        A[k] = A[loc];",
                "        A[loc] = temp;",
                "        selectionRec(A, k + 1, n);",
                "    }",
                "",
                "    static void printArray(int[] A, int n) {",
                "        for (int i = 1; i <= n; i++) System.out.print(A[i] + \" \");",
                "        System.out.println();",
                "    }",
                "",
                "    public static void main(String[] args) {",
                "        Scanner sc = new Scanner(System.in);",
                "        System.out.print(\"Enter number of elements: \");",
                "        int n = sc.nextInt();",
                "        int[] A = new int[n + 1];",
                "        System.out.println(\"Enter elements:\");",
                "        for (int i = 1; i <= n; i++) A[i] = sc.nextInt();",
                "        selectionRec(A, 1, n);",
                "        System.out.print(\"Sorted array: \");",
                "        printArray(A, n);",
                "    }",
                "}");
        }
        return String.join("\n",
            "import java.util.*;",
            "public class Main {",
            "    static void printArray(int[] A, int n) {",
            "        for (int i = 1; i <= n; i++) System.out.print(A[i] + \" \");",
            "        System.out.println();",
            "    }",
            "",
            "    public static void main(String[] args) {",
            "        Scanner sc = new Scanner(System.in);",
            "        System.out.print(\"Enter number of elements: \");",
            "        int n = sc.nextInt();",
            "        int[] A = new int[n + 1];",
            "        System.out.println(\"Enter elements:\");",
            "        for (int i = 1; i <= n; i++) A[i] = sc.nextInt();",
            "        for (int k = 1; k <= n - 1; k++) {",
            "            int min = A[k];",
            "            int loc = k;",
            "            for (int j = k + 1; j <= n; j++) if (min > A[j]) { min = A[j]; loc = j; }",
            "            int temp = A[k];",
            "            A[k] = A[loc];",
            "            A[loc] = temp;",
            "        }",
            "        System.out.print(\"Sorted array: \");",
            "        printArray(A, n);",
            "    }",
            "}");
    }

    private static String getJavaMergingPdf(String style) { if ("Functions".equals(style)) return String.join("\n", "import java.util.*;", "public class Main {", "    static void merge(int[] A, int R, int[] B, int S, int[] C) { int NA = 1, NB = 1, PTR = 1; while (NA <= R && NB <= S) { if (A[NA] < B[NB]) C[PTR++] = A[NA++]; else C[PTR++] = B[NB++]; } if (NA > R) for (int k = 0; k <= S - NB; k++) C[PTR + k] = B[NB + k]; else for (int k = 0; k <= R - NA; k++) C[PTR + k] = A[NA + k]; }", "    public static void main(String[] args) { Scanner sc = new Scanner(System.in); int R = sc.nextInt(); int[] A = new int[R + 1]; for (int i = 1; i <= R; i++) A[i] = sc.nextInt(); int S = sc.nextInt(); int[] B = new int[S + 1]; for (int i = 1; i <= S; i++) B[i] = sc.nextInt(); int[] C = new int[R + S + 1]; merge(A, R, B, S, C); for (int i = 1; i <= R + S; i++) System.out.print(C[i] + \" \"); }", "}"); if ("Recursion".equals(style)) return String.join("\n", "import java.util.*;", "public class Main {", "    static void mergeRec(int[] A, int[] B, int[] C, int NA, int NB, int PTR, int R, int S) { if (NA > R) { for (int k = 0; k <= S - NB; k++) C[PTR + k] = B[NB + k]; return; } if (NB > S) { for (int k = 0; k <= R - NA; k++) C[PTR + k] = A[NA + k]; return; } if (A[NA] < B[NB]) { C[PTR] = A[NA]; mergeRec(A, B, C, NA + 1, NB, PTR + 1, R, S); } else { C[PTR] = B[NB]; mergeRec(A, B, C, NA, NB + 1, PTR + 1, R, S); } }", "    public static void main(String[] args) { Scanner sc = new Scanner(System.in); int R = sc.nextInt(); int[] A = new int[R + 1]; for (int i = 1; i <= R; i++) A[i] = sc.nextInt(); int S = sc.nextInt(); int[] B = new int[S + 1]; for (int i = 1; i <= S; i++) B[i] = sc.nextInt(); int[] C = new int[R + S + 1]; mergeRec(A, B, C, 1, 1, 1, R, S); for (int i = 1; i <= R + S; i++) System.out.print(C[i] + \" \"); }", "}"); return String.join("\n", "import java.util.*;", "public class Main {", "    public static void main(String[] args) { Scanner sc = new Scanner(System.in); int R = sc.nextInt(); int[] A = new int[R + 1]; for (int i = 1; i <= R; i++) A[i] = sc.nextInt(); int S = sc.nextInt(); int[] B = new int[S + 1]; for (int i = 1; i <= S; i++) B[i] = sc.nextInt(); int[] C = new int[R + S + 1]; int NA = 1, NB = 1, PTR = 1; while (NA <= R && NB <= S) { if (A[NA] < B[NB]) C[PTR++] = A[NA++]; else C[PTR++] = B[NB++]; } if (NA > R) for (int k = 0; k <= S - NB; k++) C[PTR + k] = B[NB + k]; else for (int k = 0; k <= R - NA; k++) C[PTR + k] = A[NA + k]; for (int i = 1; i <= R + S; i++) System.out.print(C[i] + \" \"); }", "}"); }

    private static String getJavaMergeSortPdf(String style) { if ("Functions".equals(style) || "Iterative".equals(style)) return String.join("\n", "import java.util.*;", "public class Main {", "    static void merge(int[] A, int l1, int s1, int[] B, int l2, int s2, int[] C, int sc) { int i = s1, j = s2, k = sc; while (i < s1 + l1 && j < s2 + l2) { if (A[i] < B[j]) C[k++] = A[i++]; else C[k++] = B[j++]; } while (i < s1 + l1) C[k++] = A[i++]; while (j < s2 + l2) C[k++] = B[j++]; }", "    static void mergePass(int[] A, int n, int L, int[] B) { int q = n / (2 * L), s = 2 * L * q; for (int i = 1; i <= s; i += 2 * L) merge(A, L, i, A, L, i + L, B, i); if (s + L < n) merge(A, L, s + 1, A, n - s - L, s + L + 1, B, s + 1); else for (int i = s + 1; i <= n; i++) B[i] = A[i]; }", "    static void mergeSort(int[] A, int n) { int[] B = new int[n + 1]; int L = 1; while (L < n) { mergePass(A, n, L, B); mergePass(B, n, 2 * L, A); L = 4 * L; } }", "    public static void main(String[] args) { Scanner sc = new Scanner(System.in); int n = sc.nextInt(); int[] A = new int[n + 1]; for (int i = 1; i <= n; i++) A[i] = sc.nextInt(); mergeSort(A, n); for (int i = 1; i <= n; i++) System.out.print(A[i] + \" \"); }", "}"); return String.join("\n", "import java.util.*;", "public class Main {", "    static void merge(int[] A, int low, int mid, int high) { int[] temp = new int[high + 1]; int i = low, j = mid + 1, k = low; while (i <= mid && j <= high) { if (A[i] < A[j]) temp[k++] = A[i++]; else temp[k++] = A[j++]; } while (i <= mid) temp[k++] = A[i++]; while (j <= high) temp[k++] = A[j++]; for (int x = low; x <= high; x++) A[x] = temp[x]; }", "    static void mergeSortRec(int[] A, int low, int high) { if (low >= high) return; int mid = (low + high) / 2; mergeSortRec(A, low, mid); mergeSortRec(A, mid + 1, high); merge(A, low, mid, high); }", "    public static void main(String[] args) { Scanner sc = new Scanner(System.in); int n = sc.nextInt(); int[] A = new int[n]; for (int i = 0; i < n; i++) A[i] = sc.nextInt(); mergeSortRec(A, 0, n - 1); for (int i = 0; i < n; i++) System.out.print(A[i] + \" \"); }", "}"); }

    private static String getJavaRadixSortPdf(String style) { if ("Functions".equals(style) || "Iterative".equals(style)) return String.join("\n", "import java.util.*;", "public class Main {", "    static void distribute(int[] A, int n, int divisor, Queue<Integer>[] Q) { for (int i = 0; i < n; i++) { int digit = (A[i] / divisor) % 10; Q[digit].add(A[i]); } }", "    static void collect(int[] A, Queue<Integer>[] Q) { int index = 0; for (int i = 0; i < 10; i++) while (!Q[i].isEmpty()) A[index++] = Q[i].remove(); }", "    static void radixSort(int[] A, int n, int d) { int divisor = 1; for (int k = 1; k <= d; k++) { Queue<Integer>[] Q = new LinkedList[10]; for (int i = 0; i < 10; i++) Q[i] = new LinkedList<>(); distribute(A, n, divisor, Q); collect(A, Q); divisor *= 10; } }", "    public static void main(String[] args) { Scanner sc = new Scanner(System.in); int n = sc.nextInt(); int[] A = new int[n]; for (int i = 0; i < n; i++) A[i] = sc.nextInt(); int d = sc.nextInt(); radixSort(A, n, d); for (int i = 0; i < n; i++) System.out.print(A[i] + \" \"); }", "}"); return String.join("\n", "import java.util.*;", "public class Main {", "    static void radixRec(int[] A, int n, int d, int divisor) { if (d == 0) return; Queue<Integer>[] Q = new LinkedList[10]; for (int i = 0; i < 10; i++) Q[i] = new LinkedList<>(); for (int i = 0; i < n; i++) { int digit = (A[i] / divisor) % 10; Q[digit].add(A[i]); } int index = 0; for (int i = 0; i < 10; i++) while (!Q[i].isEmpty()) A[index++] = Q[i].remove(); radixRec(A, n, d - 1, divisor * 10); }", "    public static void main(String[] args) { Scanner sc = new Scanner(System.in); int n = sc.nextInt(); int[] A = new int[n]; for (int i = 0; i < n; i++) A[i] = sc.nextInt(); int d = sc.nextInt(); radixRec(A, n, d, 1); for (int i = 0; i < n; i++) System.out.print(A[i] + \" \"); }", "}"); }

}
