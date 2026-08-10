package com.dsavisualizer.app;

import java.util.Map;

public class AlgorithmDataProvider {
    public static void populate(Map<String, String[]> templates) {
        // Format: Mode_ViewType_Style
        // ViewTypes: Logic, Code
        // Styles: Iterative, Function, Recursion

        // ==========================================
        // BUBBLE SORT
        // ==========================================
        
        // Iterative
        templates.put("Bubble Sort_Logic_Iterative", new String[]{
            "Algorithm: BUBBLE(DATA, N)",
            "1. Repeat Steps 2 and 3 for K = 1 to N-1:",
            "2.   Set PTR = 1.",
            "3.   Repeat while PTR <= N - K:",
            "4.     If DATA[PTR] > DATA[PTR+1] then:",
            "         Interchange DATA[PTR] and DATA[PTR+1]",
            "5.     Set PTR = PTR + 1.",
            "6. [End of inner loop]",
            "7. [End of outer loop]",
            "8. Exit"
        });
        templates.put("Bubble Sort_Code_Iterative", new String[]{
            "for (int i = 0; i < n - 1; i++) {",
            "  for (int j = 0; j < n - i - 1; j++) {",
            "    if (arr[j] > arr[j + 1]) {",
            "      int temp = arr[j];",
            "      arr[j] = arr[j + 1];",
            "      arr[j + 1] = temp;",
            "    }",
            "  }",
            "}"
        });

        // Function
        templates.put("Bubble Sort_Logic_Function", new String[]{
            "Procedure BUBBLE_PASS(DATA, N, K)",
            "1. For J = 1 to N - K:",
            "2.   If DATA[J] > DATA[J+1] swap(DATA[J], DATA[J+1])",
            "Procedure BUBBLE_SORT(DATA, N)",
            "1. For K = 1 to N - 1:",
            "2.   Call BUBBLE_PASS(DATA, N, K)"
        });
        templates.put("Bubble Sort_Code_Function", new String[]{
            "void bubblePass(int a[], int limit) {",
            "  for(int j=0; j < limit; j++)",
            "    if(a[j] > a[j+1]) swap(a, j, j+1);",
            "}",
            "void bubbleSort(int a[], int n) {",
            "  for(int i=0; i < n-1; i++) bubblePass(a, n-i-1);",
            "}"
        });

        // Recursion
        templates.put("Bubble Sort_Logic_Recursion", new String[]{
            "Algorithm: BUBBLE_REC(DATA, N)",
            "1. If N = 1, return.",
            "2. Repeat for I = 1 to N-1:",
            "3.   If DATA[I] > DATA[I+1] swap.",
            "4. Call BUBBLE_REC(DATA, N-1)."
        });
        templates.put("Bubble Sort_Code_Recursion", new String[]{
            "void bubbleSortRec(int a[], int n) {",
            "  if (n == 1) return;",
            "  for (int i=0; i < n-1; i++)",
            "    if (a[i] > a[i+1]) swap(a, i, i+1);",
            "  bubbleSortRec(a, n-1);",
            "}"
        });

        // ==========================================
        // QUICK SORT
        // ==========================================
        templates.put("Quick Sort_Logic_Recursion", new String[]{
            "Algorithm: QUICKSORT(A, BEG, END)",
            "1. If BEG < END then:",
            "2.   Call PARTITION(A, BEG, END, LOC)",
            "3.   Call QUICKSORT(A, BEG, LOC-1)",
            "4.   Call QUICKSORT(A, LOC+1, END)",
            "5. Exit"
        });
        templates.put("Quick Sort_Code_Recursion", new String[]{
            "void quickSort(int arr[], int low, int high) {",
            "  if (low < high) {",
            "    int pi = partition(arr, low, high);",
            "    quickSort(arr, low, pi - 1);",
            "    quickSort(arr, pi + 1, high);",
            "  }",
            "}"
        });

        // ==========================================
        // MERGE SORT
        // ==========================================
        templates.put("Merge Sort_Logic_Recursion", new String[]{
            "Algorithm: MERGESORT(A, BEG, END)",
            "1. If BEG < END then:",
            "2.   Set MID = INT((BEG + END) / 2)",
            "3.   Call MERGESORT(A, BEG, MID)",
            "4.   Call MERGESORT(A, MID+1, END)",
            "5.   Call MERGE(A, BEG, MID, END)",
            "6. Exit"
        });
        templates.put("Merge Sort_Code_Recursion", new String[]{
            "void mergeSort(int arr[], int l, int r) {",
            "  if (l < r) {",
            "    int m = l + (r - l) / 2;",
            "    mergeSort(arr, l, m);",
            "    mergeSort(arr, m + 1, r);",
            "    merge(arr, l, m, r);",
            "  }",
            "}"
        });

        // ==========================================
        // BINARY SEARCH
        // ==========================================
        templates.put("Binary Search_Logic_Iterative", new String[]{
            "Algorithm: BINARY(DATA, LB, UB, ITEM, LOC)",
            "1. Set BEG = LB, END = UB and MID = INT((BEG+END)/2)",
            "2. Repeat while BEG <= END and DATA[MID] != ITEM:",
            "3.   If ITEM < DATA[MID] then: Set END = MID - 1",
            "4.   Else: Set BEG = MID + 1",
            "5.   Set MID = INT((BEG+END)/2)",
            "6. If DATA[MID] == ITEM then: Set LOC = MID",
            "7. Else: Set LOC = NULL",
            "8. Exit"
        });
        templates.put("Binary Search_Code_Iterative", new String[]{
            "int binarySearch(int arr[], int n, int x) {",
            "  int l = 0, r = n - 1;",
            "  while (l <= r) {",
            "    int m = l + (r - l) / 2;",
            "    if (arr[m] == x) return m;",
            "    if (arr[m] < x) l = m + 1;",
            "    else r = m - 1;",
            "  }",
            "  return -1;",
            "}"
        });
        templates.put("Binary Search_Logic_Recursion", new String[]{
            "Algorithm: BINARY_REC(DATA, BEG, END, ITEM, LOC)",
            "1. If BEG > END then: Set LOC = NULL, Exit",
            "2. Set MID = INT((BEG + END) / 2)",
            "3. If DATA[MID] = ITEM then: Set LOC = MID, Exit",
            "4. If ITEM < DATA[MID] then:",
            "     Call BINARY_REC(DATA, BEG, MID - 1, ITEM, LOC)",
            "   Else:",
            "     Call BINARY_REC(DATA, MID + 1, END, ITEM, LOC)"
        });
        templates.put("Binary Search_Code_Recursion", new String[]{
            "int binarySearch(int a[], int l, int r, int x) {",
            "  if (r >= l) {",
            "    int mid = l + (r - l) / 2;",
            "    if (a[mid] == x) return mid;",
            "    if (a[mid] > x) return binarySearch(a, l, mid - 1, x);",
            "    return binarySearch(a, mid + 1, r, x);",
            "  }",
            "  return -1;",
            "}"
        });

        // ==========================================
        // DATA STRUCTURES
        // ==========================================
        templates.put("Stack_Logic_Iterative", new String[]{
            "Algorithm: PUSH(STACK, TOP, MAXSTK, ITEM)",
            "1. If TOP = MAXSTK then: Write OVERFLOW, Exit",
            "2. Set TOP = TOP + 1",
            "3. Set STACK[TOP] = ITEM",
            "4. Exit"
        });
        templates.put("Stack_Code_Iterative", new String[]{
            "void push(int x) {",
            "  if (top == MAX - 1) { printf(\"Overflow\"); return; }",
            "  stack[++top] = x;",
            "}"
        });

        templates.put("Queue_Logic_Iterative", new String[]{
            "Algorithm: QINSERT(QUEUE, N, FRONT, REAR, ITEM)",
            "1. If REAR = N then: Write OVERFLOW, Exit",
            "2. If FRONT = NULL then: Set FRONT = 1 and REAR = 1",
            "3. Else: Set REAR = REAR + 1",
            "4. Set QUEUE[REAR] = ITEM",
            "5. Exit"
        });

        templates.put("Linked List (Singly)_Logic_Iterative", new String[]{
            "Algorithm: INSERT_FIRST(INFO, LINK, START, AVAIL, ITEM)",
            "1. If AVAIL = NULL then: Write OVERFLOW, Exit",
            "2. Set NEW = AVAIL and AVAIL = LINK[AVAIL]",
            "3. Set INFO[NEW] = ITEM and LINK[NEW] = START",
            "4. Set START = NEW",
            "5. Exit"
        });
        templates.put("Linked List (Singly)_Code_Function", new String[]{
            "void insertFirst(int d) {",
            "  Node newNode = new Node(d);",
            "  newNode.next = start;",
            "  start = newNode;",
            "}"
        });

        templates.put("Binary Search Tree_Logic_Recursion", new String[]{
            "Algorithm: BST_INSERT(ROOT, ITEM)",
            "1. If ROOT = NULL then: Create NewNode, Set ROOT = NewNode, Exit",
            "2. If ITEM < ROOT.DATA then: Call BST_INSERT(ROOT.LEFT, ITEM)",
            "3. Else if ITEM > ROOT.DATA then: Call BST_INSERT(ROOT.RIGHT, ITEM)",
            "4. Exit"
        });
        templates.put("Binary Search Tree_Code_Recursion", new String[]{
            "Node insert(Node root, int key) {",
            "  if (root == null) return new Node(key);",
            "  if (key < root.key) root.left = insert(root.left, key);",
            "  else if (key > root.key) root.right = insert(root.right, key);",
            "  return root;",
            "}"
        });
    }
}
