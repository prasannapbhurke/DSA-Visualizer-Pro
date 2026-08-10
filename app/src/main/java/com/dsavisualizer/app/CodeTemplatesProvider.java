package com.dsavisualizer.app;

import java.util.Map;

public class CodeTemplatesProvider {
    public static void populate(Map<String, String[]> templates) {
        // --- BUBBLE SORT ---
        // Logic
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
        templates.put("Bubble Sort_Logic_Recursion", new String[]{
            "Algorithm: BUBBLE_REC(DATA, N)",
            "1. If N = 1 return.",
            "2. Repeat for I = 1 to N-1:",
            "3.   If DATA[I] > DATA[I+1] swap.",
            "4. Call BUBBLE_REC(DATA, N-1)."
        });
        
        // Code
        templates.put("Bubble Sort_Code_Iterative", new String[]{
            "void bubbleSort(int arr[], int n) {",
            "  for (int i = 0; i < n - 1; i++)",
            "    for (int j = 0; j < n - i - 1; j++)",
            "      if (arr[j] > arr[j + 1])",
            "        swap(&arr[j], &arr[j + 1]);",
            "}"
        });
        templates.put("Bubble Sort_Code_Recursion", new String[]{
            "void bubbleSort(int arr[], int n) {",
            "  if (n == 1) return;",
            "  for (int i=0; i<n-1; i++)",
            "    if (arr[i] > arr[i+1]) swap(arr, i, i+1);",
            "  bubbleSort(arr, n-1);",
            "}"
        });

        // --- QUICK SORT ---
        templates.put("Quick Sort_Logic_Recursion", new String[]{
            "Algorithm: QUICKSORT(A, BEG, END)",
            "1. If BEG < END then:",
            "2.   Call PARTITION(A, BEG, END, LOC)",
            "3.   Call QUICKSORT(A, BEG, LOC-1)",
            "4.   Call QUICKSORT(A, LOC+1, END)",
            "5. [End of If]"
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

        // --- MERGE SORT ---
        templates.put("Merge Sort_Logic_Recursion", new String[]{
            "Algorithm: MERGESORT(A, BEG, END)",
            "1. If BEG < END then:",
            "2.   Set MID = INT((BEG + END) / 2)",
            "3.   Call MERGESORT(A, BEG, MID)",
            "4.   Call MERGESORT(A, MID+1, END)",
            "5.   Call MERGE(A, BEG, MID, END)",
            "6. [End of If]"
        });

        // --- BINARY SEARCH ---
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
        
        // --- STACK ---
        templates.put("Stack_Logic_Iterative", new String[]{
            "Algorithm: PUSH(STACK, TOP, MAXSTK, ITEM)",
            "1. If TOP = MAXSTK then: Write OVERFLOW, Exit",
            "2. Set TOP = TOP + 1",
            "3. Set STACK[TOP] = ITEM",
            "4. Exit"
        });

        // --- QUEUE ---
        templates.put("Queue_Logic_Iterative", new String[]{
            "Algorithm: QINSERT(QUEUE, N, FRONT, REAR, ITEM)",
            "1. If REAR = N then: Write OVERFLOW, Exit",
            "2. If FRONT = NULL then: Set FRONT = 1 and REAR = 1",
            "3. Else: Set REAR = REAR + 1",
            "4. Set QUEUE[REAR] = ITEM",
            "5. Exit"
        });

        // --- LINKED LIST ---
        templates.put("Linked List (Singly)_Logic_Iterative", new String[]{
            "Algorithm: INSERT_FIRST(INFO, LINK, START, AVAIL, ITEM)",
            "1. If AVAIL = NULL then: Write OVERFLOW, Exit",
            "2. Set NEW = AVAIL and AVAIL = LINK[AVAIL]",
            "3. Set INFO[NEW] = ITEM and LINK[NEW] = START",
            "4. Set START = NEW",
            "5. Exit"
        });
        
        // --- BST ---
        templates.put("Binary Search Tree_Logic_Recursion", new String[]{
            "Algorithm: BST_INSERT(ROOT, ITEM)",
            "1. If ROOT = NULL then: Create NewNode, Set ROOT = NewNode, Exit",
            "2. If ITEM < ROOT.DATA then: Call BST_INSERT(ROOT.LEFT, ITEM)",
            "3. Else if ITEM > ROOT.DATA then: Call BST_INSERT(ROOT.RIGHT, ITEM)",
            "4. Exit"
        });
    }
}
