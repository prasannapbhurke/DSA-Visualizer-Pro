package com.dsavisualizer.app.data;

import android.content.Context;

import com.dsavisualizer.app.model.CategoryItem;
import com.dsavisualizer.app.model.TechniqueContent;
import com.dsavisualizer.app.model.TechniqueItem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Local offline repository for categories, techniques, explanations, and sample content.
 */
public final class AppRepository {
    private static final List<CategoryItem> CATEGORIES = new ArrayList<>();
    private static final List<TechniqueItem> TECHNIQUES = new ArrayList<>();
    private static final Map<String, TechniqueContent> CONTENT = new LinkedHashMap<>();
    private static final AtomicBoolean STRUCTURED_CONTENT_LOADED = new AtomicBoolean(false);

    static {
        CATEGORIES.add(new CategoryItem("searching", "Searching", "Find data quickly and clearly", "#4ADE80"));
        CATEGORIES.add(new CategoryItem("sorting", "Sorting", "Arrange items step by step", "#60A5FA"));
        CATEGORIES.add(new CategoryItem("stack", "Stack", "Last in, first out structure", "#F59E0B"));
        CATEGORIES.add(new CategoryItem("queue", "Queue", "First in, first out structure", "#22D3EE"));
        CATEGORIES.add(new CategoryItem("linked_list", "Linked List", "Connected node based storage", "#A78BFA"));
        CATEGORIES.add(new CategoryItem("trees", "Trees", "Hierarchical node structures", "#FBBF24"));
        CATEGORIES.add(new CategoryItem("graph", "Graph", "Vertex and edge relationships", "#FB7185"));
        CATEGORIES.add(new CategoryItem("foundations", "Foundations", "Core thinking patterns and basics", "#F97316"));
        CATEGORIES.add(new CategoryItem("advanced", "Advanced", "More specialized DSA topics", "#14B8A6"));

        TECHNIQUES.add(new TechniqueItem("linear_search", "searching", "Linear Search", "Checks each element one by one", "Easy"));
        TECHNIQUES.add(new TechniqueItem("binary_search", "searching", "Binary Search", "Cuts the sorted range in half", "Medium"));
        TECHNIQUES.add(new TechniqueItem("jump_search", "searching", "Jump Search", "Skips blocks before scanning", "Medium"));
        TECHNIQUES.add(new TechniqueItem("pattern_matching", "searching", "Pattern Matching", "Find a pattern inside a longer text", "Medium"));

        TECHNIQUES.add(new TechniqueItem("bubble_sort", "sorting", "Bubble Sort", "Swaps nearby values until sorted", "Easy"));
        TECHNIQUES.add(new TechniqueItem("selection_sort", "sorting", "Selection Sort", "Picks the smallest remaining value", "Easy"));
        TECHNIQUES.add(new TechniqueItem("insertion_sort", "sorting", "Insertion Sort", "Builds a sorted part gradually", "Easy"));
        TECHNIQUES.add(new TechniqueItem("merging", "sorting", "Merging", "Merge two sorted arrays into one", "Medium"));
        TECHNIQUES.add(new TechniqueItem("merge_sort", "sorting", "Merge Sort", "Split and merge arrays in sorted order", "Hard"));
        TECHNIQUES.add(new TechniqueItem("radix_sort", "sorting", "Radix Sort", "Sort numbers digit by digit", "Hard"));

        TECHNIQUES.add(new TechniqueItem("stack_basics", "stack", "Stack Operations", "Push and pop on the top", "Easy"));
        TECHNIQUES.add(new TechniqueItem("linked_stack_push", "stack", "Linked Stack PUSH", "Push one item onto a linked stack", "Easy"));
        TECHNIQUES.add(new TechniqueItem("linked_stack_pop", "stack", "Linked Stack POP", "Pop one item from a linked stack", "Easy"));
        TECHNIQUES.add(new TechniqueItem("postfix_evaluation", "stack", "Evaluation of a Postfix Expression", "Evaluate postfix notation using a stack", "Medium"));
        TECHNIQUES.add(new TechniqueItem("infix_to_postfix", "stack", "Infix to Postfix (POLISH)", "Transform infix notation into postfix notation", "Hard"));
        TECHNIQUES.add(new TechniqueItem("infix_to_prefix", "stack", "Infix to Prefix", "Transform infix notation into prefix notation", "Hard"));
        TECHNIQUES.add(new TechniqueItem("queue_basics", "queue", "Linear Queue Insertion and Deletion", "Insert and delete using front and rear pointers", "Easy"));
        TECHNIQUES.add(new TechniqueItem("circular_queue", "queue", "Circular Queue Insertion and Deletion", "Wrap the queue around when rear reaches the end", "Medium"));
        TECHNIQUES.add(new TechniqueItem("linked_queue", "queue", "Linked List as Queue Insertion and Deletion", "Use linked nodes for queue insert and delete", "Medium"));
        TECHNIQUES.add(new TechniqueItem("priority_queue_array", "queue", "Priority Queue (One-dimensional Array)", "Insert by priority and delete the front item", "Medium"));
        TECHNIQUES.add(new TechniqueItem("priority_queue_2d", "queue", "Priority Queue (Two-dimensional Array)", "Separate queues for each priority level", "Hard"));
        TECHNIQUES.add(new TechniqueItem("deque", "queue", "Deque", "Insert and delete at both ends", "Medium"));
        TECHNIQUES.add(new TechniqueItem("singly_linked_list", "linked_list", "Singly Linked List", "Nodes connected in one direction", "Medium"));
        TECHNIQUES.add(new TechniqueItem("traversing_linked_list", "linked_list", "Traversing a Linked List", "Visit each node one by one", "Easy"));
        TECHNIQUES.add(new TechniqueItem("print_linked_list", "linked_list", "Print Linked List", "Print each node in sequence", "Easy"));
        TECHNIQUES.add(new TechniqueItem("count_nodes_linked_list", "linked_list", "Count Nodes in Linked List", "Count how many nodes are present", "Easy"));
        TECHNIQUES.add(new TechniqueItem("search_in_linked_list", "linked_list", "Search in Linked List", "Find the first node containing an item", "Easy"));
        TECHNIQUES.add(new TechniqueItem("find_linked_list", "linked_list", "FIND (Search in Linked List)", "Search the first matching node by the PDF procedure name", "Easy"));
        TECHNIQUES.add(new TechniqueItem("insert_at_beginning_linked_list", "linked_list", "Insert at Beginning of a Linked List", "Add a new first node", "Easy"));
        TECHNIQUES.add(new TechniqueItem("insert_after_given_node_linked_list", "linked_list", "Inserting After a Given Node in Linked List", "Insert after a known location", "Medium"));
        TECHNIQUES.add(new TechniqueItem("insert_into_sorted_linked_list", "linked_list", "Inserting into a Sorted Linked List", "Place a new value in sorted order", "Medium"));
        TECHNIQUES.add(new TechniqueItem("delete_node_given_location_linked_list", "linked_list", "Deleting a Node from a Linked List (Given Location)", "Delete a node when location is known", "Medium"));
        TECHNIQUES.add(new TechniqueItem("delete_node_by_item_linked_list", "linked_list", "Deleting a Node by ITEM in a Linked List", "Find and delete the first matching item", "Medium"));
        TECHNIQUES.add(new TechniqueItem("delete_linked_list", "linked_list", "DELETE (Delete from Linked List)", "Delete the first matching node by the PDF procedure name", "Medium"));
        TECHNIQUES.add(new TechniqueItem("traverse_circular_header_list", "linked_list", "Traversing a Circular Header List", "Visit each node until the header is reached again", "Medium"));
        TECHNIQUES.add(new TechniqueItem("search_circular_header_list", "linked_list", "Searching a Circular Header List", "Search nodes in a circular header list", "Medium"));
        TECHNIQUES.add(new TechniqueItem("delete_circular_header_list", "linked_list", "Deleting from a Circular Header List", "Find and delete an item from a circular header list", "Medium"));
        TECHNIQUES.add(new TechniqueItem("tree_traversal", "trees", "Tree Traversal", "Visit nodes in a defined order", "Medium"));
        TECHNIQUES.add(new TechniqueItem("preorder_traversal", "trees", "Preorder Traversal", "Visit root, then left subtree, then right subtree", "Medium"));
        TECHNIQUES.add(new TechniqueItem("postorder_traversal", "trees", "Postorder Traversal", "Visit left subtree, right subtree, then root", "Medium"));
        TECHNIQUES.add(new TechniqueItem("bst_search_parent_tracking", "trees", "BST Search with Parent Tracking", "Search an item and also track its parent node", "Medium"));
        TECHNIQUES.add(new TechniqueItem("bst_insertion", "trees", "BST Insertion (INSBST)", "Insert a new item into the binary search tree", "Medium"));
        TECHNIQUES.add(new TechniqueItem("bst_deletion_case_a", "trees", "BST Deletion (CASE A)", "Delete a node with at most one child", "Medium"));
        TECHNIQUES.add(new TechniqueItem("bst_deletion_case_b", "trees", "BST Deletion (CASE B)", "Delete a node with two children using inorder successor", "Hard"));
        TECHNIQUES.add(new TechniqueItem("bst_deletion_del", "trees", "BST Deletion (DEL Operation)", "General deletion using case A or case B", "Hard"));
        TECHNIQUES.add(new TechniqueItem("max_heap_insertion", "trees", "Insertion in a Max Heap (INSHEAP)", "Insert a value and restore max-heap order", "Medium"));
        TECHNIQUES.add(new TechniqueItem("max_heap_deletion", "trees", "Deletion from Max Heap (DELHEAP)", "Delete the root and restore heap order", "Medium"));
        TECHNIQUES.add(new TechniqueItem("traversal_using_stacks", "trees", "Traversal Algorithms Using Stacks", "Use an explicit stack to simulate traversal", "Medium"));
        TECHNIQUES.add(new TechniqueItem("huffman_algorithm", "trees", "Huffman's Algorithm", "Build an optimal prefix coding tree", "Hard"));
        TECHNIQUES.add(new TechniqueItem("avl_tree", "trees", "AVL Tree", "Self-balancing binary search tree", "Hard"));
        TECHNIQUES.add(new TechniqueItem("trie", "trees", "Trie", "Prefix tree for strings and dictionaries", "Hard"));
        TECHNIQUES.add(new TechniqueItem("graph_traversal", "graph", "Graph Traversal", "Visit graph nodes with BFS or DFS", "Medium"));
        TECHNIQUES.add(new TechniqueItem("warshall_algorithm", "graph", "Warshall's Algorithm", "Build the transitive closure matrix", "Hard"));
        TECHNIQUES.add(new TechniqueItem("floyd_algorithm", "graph", "Floyd's Algorithm", "Compute all-pairs shortest paths", "Hard"));
        TECHNIQUES.add(new TechniqueItem("shortest_paths", "graph", "Shortest Paths", "Find the minimum cost route through a graph", "Hard"));
        TECHNIQUES.add(new TechniqueItem("dijkstra_algorithm", "graph", "Dijkstra's Algorithm", "Grow shortest distances from one source", "Hard"));
        TECHNIQUES.add(new TechniqueItem("find_edge_graph", "graph", "FINDEDGE", "Find whether an edge exists in the graph", "Medium"));
        TECHNIQUES.add(new TechniqueItem("insert_node_graph", "graph", "INSNODE", "Insert a new node into the graph structure", "Medium"));
        TECHNIQUES.add(new TechniqueItem("insert_edge_graph", "graph", "INSEDGE", "Insert a directed edge between two nodes", "Medium"));
        TECHNIQUES.add(new TechniqueItem("delete_edge_graph", "graph", "Delete Edge in Graph", "Delete an edge from adjacency list representation", "Hard"));
        TECHNIQUES.add(new TechniqueItem("delete_vertex_graph", "graph", "Delete Node in Graph", "Delete a vertex and its incident edges", "Hard"));
        TECHNIQUES.add(new TechniqueItem("breadth_first_search", "graph", "Breadth-First Search (BFS)", "Visit graph nodes level by level", "Medium"));
        TECHNIQUES.add(new TechniqueItem("depth_first_search", "graph", "Depth-First Search (DFS)", "Visit graph nodes by going deep first", "Medium"));
        TECHNIQUES.add(new TechniqueItem("topological_sort", "graph", "Topological Sort", "Order a DAG so every edge goes forward", "Hard"));
        TECHNIQUES.add(new TechniqueItem("spanning_tree", "graph", "Spanning Tree", "Connect all vertices without cycles", "Hard"));
        TECHNIQUES.add(new TechniqueItem("quicksort", "sorting", "Quicksort", "Partition around a pivot and sort subarrays", "Medium"));
        TECHNIQUES.add(new TechniqueItem("heapsort", "sorting", "HEAPSORT", "Build a max heap and repeatedly delete the max", "Hard"));
        TECHNIQUES.add(new TechniqueItem("union_find", "advanced", "Union-Find", "Track connected components with parent sets", "Medium"));
        TECHNIQUES.add(new TechniqueItem("hash_collision_handling", "advanced", "Hash Collision Handling", "Resolve hash conflicts by chaining or probing", "Medium"));
        TECHNIQUES.add(new TechniqueItem("recursion_basics", "foundations", "Recursion Basics", "Solve a problem by reducing it to smaller cases", "Easy"));
        TECHNIQUES.add(new TechniqueItem("dynamic_programming_basics", "foundations", "Dynamic Programming Basics", "Reuse solved subproblems to save time", "Medium"));

        TechniqueContent linearSearch = new TechniqueContent(
            "linear_search",
            "Linear Search",
            "Linear Search checks each value from left to right until the target is found or the list ends.",
            "Looking for your name in an attendance sheet line by line is a real-life linear search.",
            "O(1)",
            "O(n)",
            "O(n)",
            "O(1)",
            new int[] {12, 7, 19, 4, 25, 9},
            25
        );
        linearSearch.algorithmByStyle.put(
            "Iterative",
            "(Linear Search) LINEAR(DATA, N, ITEM, LOC)\n\n"
                + "1. Set DATA[N + 1] := ITEM.\n"
                + "2. Set LOC := 1.\n"
                + "3. Repeat while DATA[LOC] != ITEM: set LOC := LOC + 1.\n"
                + "4. If LOC = N + 1, then set LOC := 0.\n"
                + "5. Exit."
        );
        linearSearch.algorithmByStyle.put(
            "Functions",
            "FUNCTION LINEAR(DATA, N, ITEM)\n\n"
                + "1. Set DATA[N + 1] := ITEM.\n"
                + "2. Set LOC := 1.\n"
                + "3. Repeat while DATA[LOC] != ITEM: set LOC := LOC + 1.\n"
                + "4. If LOC = N + 1, return 0; else return LOC.\n\n"
                + "MAIN:\n"
                + "1. Read N, DATA, ITEM.\n"
                + "2. LOC := LINEAR(DATA, N, ITEM).\n"
                + "3. Print LOC."
        );
        linearSearch.algorithmByStyle.put(
            "Recursion",
            "FUNCTION LINEAR(DATA, N, ITEM, LOC)\n\n"
                + "1. If DATA[LOC] = ITEM, then:\n"
                + "   If LOC = N + 1, return 0; else return LOC.\n"
                + "2. Else call LINEAR(DATA, N, ITEM, LOC + 1)."
        );
        linearSearch.codeByStyle.put("Iterative", "public int linearSearch(int[] arr, int target) {\n    for (int i = 0; i < arr.length; i++) {\n        if (arr[i] == target) {\n            return i;\n        }\n    }\n    return -1;\n}");
        linearSearch.codeByStyle.put("Functions", "public int findLinear(int[] values, int key) {\n    for (int index = 0; index < values.length; index++) {\n        if (values[index] == key) {\n            return index;\n        }\n    }\n    return -1;\n}");
        linearSearch.codeByStyle.put("Recursion", "public int linearSearchRecursive(int[] arr, int target, int index) {\n    if (index >= arr.length) {\n        return -1;\n    }\n    if (arr[index] == target) {\n        return index;\n    }\n    return linearSearchRecursive(arr, target, index + 1);\n}");
        CONTENT.put(linearSearch.techniqueId, linearSearch);

        TechniqueContent binarySearch = new TechniqueContent(
            "binary_search",
            "Binary Search",
            "Binary Search works on sorted data and repeatedly checks the middle value.",
            "Looking up a word in a dictionary by opening near the middle is binary search.",
            "O(1)",
            "O(log n)",
            "O(log n)",
            "O(1)",
            new int[] {3, 8, 14, 22, 27, 31, 40},
            27
        );
        binarySearch.algorithmByStyle.put(
            "Iterative",
            "(Binary Search) BINARY(DATA, LB, UB, ITEM, LOC)\n\n"
                + "1. Set BEG := LB, END := UB,\n"
                + "   and MID := INT((BEG + END) / 2).\n"
                + "2. Repeat while BEG <= END\n"
                + "   and DATA[MID] != ITEM.\n"
                + "3. If ITEM < DATA[MID],\n"
                + "      set END := MID - 1.\n"
                + "   else\n"
                + "      set BEG := MID + 1.\n"
                + "4. Set MID := INT((BEG + END) / 2).\n"
                + "5. If DATA[MID] = ITEM,\n"
                + "      set LOC := MID.\n"
                + "   else\n"
                + "      set LOC := NULL.\n"
                + "6. Exit."
        );
        binarySearch.algorithmByStyle.put(
            "Functions",
            "FUNCTION BINARY(DATA, LB, UB, ITEM)\n\n"
                + "1. Set BEG := LB, END := UB.\n"
                + "2. Repeat while BEG <= END:\n"
                + "   Set MID := INT((BEG + END) / 2).\n"
                + "   If DATA[MID] = ITEM,\n"
                + "      return MID.\n"
                + "   If ITEM < DATA[MID],\n"
                + "      set END := MID - 1.\n"
                + "   else\n"
                + "      set BEG := MID + 1.\n"
                + "3. Return NULL.\n\n"
                + "MAIN:\n"
                + "1. Read DATA, LB, UB, ITEM.\n"
                + "2. LOC := BINARY(DATA, LB, UB, ITEM).\n"
                + "3. Print LOC."
        );
        binarySearch.algorithmByStyle.put(
            "Recursion",
            "FUNCTION BINARY(DATA, BEG, END, ITEM)\n\n"
                + "1. If BEG > END, return NULL.\n"
                + "2. Set MID := INT((BEG + END) / 2).\n"
                + "3. If DATA[MID] = ITEM, return MID.\n"
                + "4. If ITEM < DATA[MID], return BINARY(DATA, BEG, MID - 1, ITEM).\n"
                + "5. Else return BINARY(DATA, MID + 1, END, ITEM)."
        );
        binarySearch.codeByStyle.put("Iterative", "public int binarySearch(int[] arr, int target) {\n    int low = 0;\n    int high = arr.length - 1;\n\n    while (low <= high) {\n        int mid = low + (high - low) / 2;\n        if (arr[mid] == target) {\n            return mid;\n        }\n        if (arr[mid] < target) {\n            low = mid + 1;\n        } else {\n            high = mid - 1;\n        }\n    }\n    return -1;\n}");
        binarySearch.codeByStyle.put("Functions", binarySearch.codeByStyle.get("Iterative"));
        binarySearch.codeByStyle.put("Recursion", "public int binarySearchRecursive(int[] arr, int target, int low, int high) {\n    if (low > high) {\n        return -1;\n    }\n    int mid = low + (high - low) / 2;\n    if (arr[mid] == target) {\n        return mid;\n    }\n    if (arr[mid] < target) {\n        return binarySearchRecursive(arr, target, mid + 1, high);\n    }\n    return binarySearchRecursive(arr, target, low, mid - 1);\n}");
        CONTENT.put(binarySearch.techniqueId, binarySearch);

        TechniqueContent jumpSearch = new TechniqueContent(
            "jump_search",
            "Jump Search",
            "Jump Search skips ahead by blocks and then scans inside the likely block.",
            "It is like checking every few pages in a notebook first, then reading line by line in the correct section.",
            "O(1)",
            "O(sqrt n)",
            "O(sqrt n)",
            "O(1)",
            new int[] {2, 5, 9, 14, 18, 23, 27, 31, 36},
            23
        );
        jumpSearch.algorithmByStyle.put("Iterative", "1. Choose a jump size of sqrt(n).\n2. Jump through the sorted array block by block.\n3. Stop when the block may contain the target.\n4. Scan linearly inside that block.\n5. Return the index or not found.");
        jumpSearch.algorithmByStyle.put("Functions", "1. Put the jump size, array, and target inside one function.\n2. Move from block to block until the possible range is found.\n3. Search inside that range.\n4. Return the matched index or -1.");
        jumpSearch.algorithmByStyle.put("Recursion", "1. Recursively jump to the next block while the block end is smaller than the target.\n2. When the correct block is reached, recursively scan inside it.\n3. Return the index or -1.");
        jumpSearch.codeByStyle.put("Iterative", "public int jumpSearch(int[] arr, int target) {\n    int step = (int) Math.sqrt(arr.length);\n    int prev = 0;\n\n    while (prev < arr.length && arr[Math.min(step, arr.length) - 1] < target) {\n        prev = step;\n        step += (int) Math.sqrt(arr.length);\n        if (prev >= arr.length) {\n            return -1;\n        }\n    }\n\n    while (prev < Math.min(step, arr.length)) {\n        if (arr[prev] == target) {\n            return prev;\n        }\n        prev++;\n    }\n    return -1;\n}");
        jumpSearch.codeByStyle.put("Functions", jumpSearch.codeByStyle.get("Iterative"));
        jumpSearch.codeByStyle.put("Recursion", "public int jumpSearchRecursive(int[] arr, int target, int start, int step) {\n    int end = Math.min(start + step, arr.length);\n    if (start >= arr.length) {\n        return -1;\n    }\n    if (arr[end - 1] < target) {\n        return jumpSearchRecursive(arr, target, end, step);\n    }\n    return linearScanRecursive(arr, target, start, end - 1);\n}\n\nprivate int linearScanRecursive(int[] arr, int target, int index, int end) {\n    if (index > end) {\n        return -1;\n    }\n    if (arr[index] == target) {\n        return index;\n    }\n    return linearScanRecursive(arr, target, index + 1, end);\n}");
        CONTENT.put(jumpSearch.techniqueId, jumpSearch);

        TechniqueContent bubbleSort = createTechniqueContent(
            "bubble_sort",
            "Bubble Sort",
            "Bubble Sort compares adjacent values and swaps them until the larger values move to the end.",
            "It feels like heavier bubbles rising toward the end after each pass.",
            "O(n^2)",
            "O(n^2)",
            "O(n^2)",
            "O(1)",
            new int[] {9, 4, 7, 2, 8, 1, 5},
            0
        );
        bubbleSort.algorithmByStyle.put(
            "Iterative",
            "(Bubble Sort) BUBBLE(DATA, N)\n\n"
                + "1. Repeat Steps 2 and 3 for K = 1 to N - 1.\n"
                + "2. Set PTR := 1. [Initializes pass pointer PTR.]\n"
                + "3. Repeat while PTR <= N - K: [Executes pass.]\n"
                + "   (a) If DATA[PTR] < DATA[PTR + 1], then interchange DATA[PTR] and DATA[PTR + 1].\n"
                + "   (b) Set PTR := PTR + 1.\n"
                + "4. Exit."
        );
        bubbleSort.algorithmByStyle.put(
            "Functions",
            "FUNCTION BUBBLE(DATA, N)\n\n"
                + "1. Repeat Steps 2 and 3 for K = 1 to N - 1.\n"
                + "2. Set PTR := 1.\n"
                + "3. Repeat while PTR <= N - K:\n"
                + "   (a) If DATA[PTR] < DATA[PTR + 1], then interchange DATA[PTR] and DATA[PTR + 1].\n"
                + "   (b) Set PTR := PTR + 1.\n"
                + "4. Return DATA.\n\n"
                + "MAIN:\n"
                + "1. Read N and DATA.\n"
                + "2. Call BUBBLE(DATA, N).\n"
                + "3. Print DATA."
        );
        bubbleSort.algorithmByStyle.put(
            "Recursion",
            "FUNCTION BUBBLE(DATA, N)\n\n"
                + "1. If N = 1, then return.\n"
                + "2. Set PTR := 1.\n"
                + "3. Repeat while PTR <= N - 1:\n"
                + "   (a) If DATA[PTR] < DATA[PTR + 1], then interchange DATA[PTR] and DATA[PTR + 1].\n"
                + "   (b) Set PTR := PTR + 1.\n"
                + "4. Call BUBBLE(DATA, N - 1)."
        );
        CONTENT.put("bubble_sort", bubbleSort);
        TechniqueContent selectionSort = createTechniqueContent(
            "selection_sort",
            "Selection Sort",
            "Selection sort repeatedly selects the smallest remaining element and swaps it into the next sorted position.",
            "It is like scanning an unsorted row of cards, picking the smallest one, and placing it at the front.",
            "O(n^2)",
            "O(n^2)",
            "O(n^2)",
            "O(1)",
            new int[] {14, 3, 11, 7, 2, 9},
            0
        );
        selectionSort.algorithmByStyle.put(
            "Iterative",
            "Algorithm: SELECTION(A, N)\n"
                + "1. Repeat for K = 1 to N - 1.\n"
                + "2. Set MIN := A[K] and LOC := K.\n"
                + "3. Repeat for J = K + 1 to N.\n"
                + "   If MIN > A[J], then set MIN := A[J] and LOC := J.\n"
                + "4. Swap A[K] and A[LOC].\n"
                + "5. Exit."
        );
        selectionSort.algorithmByStyle.put(
            "Functions",
            "Algorithm: SELECTION_FUNC(A, N)\n"
                + "1. For K = 1 to N - 1, set LOC := FIND_MIN(A, K, N).\n"
                + "2. Call SWAP(A, K, LOC).\n"
                + "3. Return.\n\n"
                + "Procedure FIND_MIN(A, K, N)\n"
                + "1. MIN := A[K], LOC := K.\n"
                + "2. For J = K + 1 to N, update MIN and LOC when a smaller value appears.\n"
                + "3. Return LOC.\n\n"
                + "Procedure SWAP(A, I, J)\n"
                + "1. TEMP := A[I].\n"
                + "2. A[I] := A[J].\n"
                + "3. A[J] := TEMP."
        );
        selectionSort.algorithmByStyle.put(
            "Recursion",
            "Algorithm: SELECTION_REC(A, K, N)\n"
                + "1. If K = N, return.\n"
                + "2. Set LOC := K.\n"
                + "3. For J = K + 1 to N, if A[J] < A[LOC], set LOC := J.\n"
                + "4. Swap A[K] and A[LOC].\n"
                + "5. Call SELECTION_REC(A, K + 1, N)."
        );
        CONTENT.put("selection_sort", selectionSort);

        TechniqueContent insertionSort = createTechniqueContent(
            "insertion_sort",
            "Insertion Sort",
            "Insertion sort inserts each new element into the correct place in the already sorted left part of the array.",
            "It is similar to arranging playing cards in your hand one card at a time.",
            "O(n)",
            "O(n^2)",
            "O(n^2)",
            "O(1)",
            new int[] {12, 5, 8, 3, 10, 1},
            0
        );
        insertionSort.algorithmByStyle.put(
            "Iterative",
            "Algorithm 9.1: INSERTION(A, N)\n"
                + "1. Set A[0] := -∞.\n"
                + "2. Repeat Steps 3 to 5 for K = 2, 3, ... , N.\n"
                + "3. Set TEMP := A[K] and PTR := K - 1.\n"
                + "4. Repeat while TEMP < A[PTR]: move A[PTR] one step forward and decrement PTR.\n"
                + "5. Set A[PTR + 1] := TEMP.\n"
                + "6. Return."
        );
        insertionSort.algorithmByStyle.put(
            "Functions",
            "Algorithm: INSERTION_FUNC(A, N)\n"
                + "1. SET_SENTINEL(A).\n"
                + "2. For K = 2 to N, call INSERT_ELEMENT(A, K).\n"
                + "3. Return.\n\n"
                + "Procedure SET_SENTINEL(A)\n"
                + "1. A[0] := -∞.\n\n"
                + "Procedure INSERT_ELEMENT(A, K)\n"
                + "1. TEMP := A[K].\n"
                + "2. PTR := K - 1.\n"
                + "3. While TEMP < A[PTR], shift A[PTR] right and decrement PTR.\n"
                + "4. Place TEMP at A[PTR + 1]."
        );
        insertionSort.algorithmByStyle.put(
            "Recursion",
            "Algorithm: INSERTION_REC(A, N)\n"
                + "1. If N <= 1, return.\n"
                + "2. Call INSERTION_REC(A, N - 1).\n"
                + "3. Set TEMP := A[N] and PTR := N - 1.\n"
                + "4. While PTR >= 1 and TEMP < A[PTR], shift A[PTR] right and decrement PTR.\n"
                + "5. Set A[PTR + 1] := TEMP.\n"
                + "6. Return."
        );
        CONTENT.put("insertion_sort", insertionSort);

        TechniqueContent merging = createTechniqueContent(
            "merging",
            "Merging",
            "Merging combines two already sorted arrays into one larger sorted array.",
            "It is like combining two sorted attendance lists into one final sorted list.",
            "O(r + s)",
            "O(r + s)",
            "O(r + s)",
            "O(r + s)",
            new int[] {2, 5, 8, 1, 4, 9},
            0
        );
        merging.algorithmByStyle.put(
            "Iterative",
            "Algorithm 9.4: MERGING(A, R, B, S, C)\n"
                + "1. Set NA := 1, NB := 1 and PTR := 1.\n"
                + "2. While NA <= R and NB <= S, compare A[NA] and B[NB], copy the smaller one to C[PTR], and advance pointers.\n"
                + "3. Copy the remaining elements from the unfinished array into C.\n"
                + "4. Exit."
        );
        merging.algorithmByStyle.put(
            "Functions",
            "Algorithm: MERGING_FUNC(A, R, B, S, C)\n"
                + "1. INITIALIZE(NA, NB, PTR).\n"
                + "2. MERGE_COMPARE(A, B, C, R, S).\n"
                + "3. COPY_REMAINING(A, B, C, R, S).\n"
                + "4. Return."
        );
        merging.algorithmByStyle.put(
            "Recursion",
            "Algorithm: MERGE_REC(A, B, C, NA, NB, PTR, R, S)\n"
                + "1. If NA > R, copy remaining elements of B and return.\n"
                + "2. If NB > S, copy remaining elements of A and return.\n"
                + "3. Compare A[NA] and B[NB], place the smaller in C[PTR].\n"
                + "4. Recursively merge the remaining part."
        );
        CONTENT.put("merging", merging);

        TechniqueContent mergeSort = createTechniqueContent(
            "merge_sort",
            "Merge Sort",
            "Merge sort repeatedly merges sorted subarrays until the whole array becomes sorted.",
            "It is like splitting a long sorted task into small parts and then carefully merging them back together.",
            "O(n log n)",
            "O(n log n)",
            "O(n log n)",
            "O(n)",
            new int[] {12, 5, 8, 3, 10, 1, 7},
            0
        );
        mergeSort.algorithmByStyle.put(
            "Iterative",
            "MERGEPASS: merge adjacent sorted blocks of length L into output array B.\n\n"
                + "MERGESORT:\n"
                + "1. Set L := 1.\n"
                + "2. While L < N:\n"
                + "3. Call MERGEPASS(A, N, L, B).\n"
                + "4. Call MERGEPASS(B, N, 2 * L, A).\n"
                + "5. Set L := 4 * L.\n"
                + "6. Exit."
        );
        mergeSort.algorithmByStyle.put(
            "Functions",
            "Algorithm: MERGESORT_FUNC(A, N)\n"
                + "1. L := 1.\n"
                + "2. While L < N, call MERGEPASS twice and update L := 4 * L.\n"
                + "3. Return.\n\n"
                + "Procedure MERGEPASS(A, N, L, B)\n"
                + "1. Compute Q, S, R.\n"
                + "2. For each pair of sorted runs, call MERGE.\n"
                + "3. Copy remaining elements.\n\n"
                + "Procedure MERGE merges two sorted subarrays into the output array."
        );
        mergeSort.algorithmByStyle.put(
            "Recursion",
            "Algorithm: MERGESORT_REC(A, LOW, HIGH)\n"
                + "1. If LOW >= HIGH, return.\n"
                + "2. MID := (LOW + HIGH) / 2.\n"
                + "3. Call MERGESORT_REC(A, LOW, MID).\n"
                + "4. Call MERGESORT_REC(A, MID + 1, HIGH).\n"
                + "5. Merge the two halves."
        );
        CONTENT.put("merge_sort", mergeSort);

        TechniqueContent radixSort = createTechniqueContent(
            "radix_sort",
            "Radix Sort",
            "Radix sort processes numbers digit by digit from least significant digit to most significant digit.",
            "It is like sorting exam roll numbers by last digit first, then tens digit, and so on.",
            "O(d(n + b))",
            "O(d(n + b))",
            "O(d(n + b))",
            "O(n + b)",
            new int[] {170, 45, 75, 90, 802, 24, 2, 66},
            0
        );
        radixSort.algorithmByStyle.put(
            "Iterative",
            "Algorithm: RADIXSORT(A, N, D)\n"
                + "1. Initialize 10 queues Q[0] to Q[9].\n"
                + "2. For K = 1 to D,\n"
                + "   distribute each element into the queue\n"
                + "   matching its K-th digit.\n"
                + "3. Collect elements from Q[0] to Q[9]\n"
                + "   back into A.\n"
                + "4. Repeat for the next digit.\n"
                + "5. Exit."
        );
        radixSort.algorithmByStyle.put(
            "Functions",
            "Algorithm: RADIXSORT_FUNC(A, N, D)\n"
                + "1. For K = 1 to D:\n"
                + "2. DISTRIBUTE(A, N, K, Q).\n"
                + "3. COLLECT(A, Q).\n"
                + "4. Return.\n\n"
                + "Procedure DISTRIBUTE inserts each number\n"
                + "into the queue for the current digit.\n"
                + "Procedure COLLECT copies the queues\n"
                + "back into the array."
        );
        radixSort.algorithmByStyle.put(
            "Recursion",
            "Algorithm: RADIX_REC(A, N, D, K)\n"
                + "1. If K > D, return.\n"
                + "2. Distribute elements based on the K-th digit.\n"
                + "3. Collect them back into A.\n"
                + "4. Call RADIX_REC(A, N, D, K + 1)."
        );
        CONTENT.put("radix_sort", radixSort);
        TechniqueContent findLinkedList = createTechniqueContent(
            "find_linked_list",
            "FIND (Search in Linked List)",
            "FIND searches a linked list and returns the location of the first node containing the target item.",
            "It is like checking each person in a line until you reach the one with the requested ID.",
            "O(1)",
            "O(n)",
            "O(n)",
            "O(1)",
            new int[] {10, 22, 35, 47, 59},
            35
        );
        findLinkedList.algorithmByStyle.put(
            "Iterative",
            "Procedure: FIND(INFO, LINK, START, ITEM, LOC)\n"
                + "1. Set PTR := START.\n"
                + "2. Repeat while PTR != NULL.\n"
                + "   If ITEM = INFO[PTR], set LOC := PTR and Return.\n"
                + "   Else set PTR := LINK[PTR].\n"
                + "3. Set LOC := NULL and Return."
        );
        findLinkedList.algorithmByStyle.put(
            "Functions",
            "Algorithm: FIND_FUNC(INFO, LINK, START, ITEM)\n"
                + "1. Call SEARCH(INFO, LINK, START, ITEM).\n"
                + "2. Return LOC.\n\n"
                + "Procedure SEARCH(INFO, LINK, START, ITEM)\n"
                + "1. Set PTR := START.\n"
                + "2. While PTR != NULL, if ITEM = INFO[PTR] return PTR, else move to LINK[PTR].\n"
                + "3. Return NULL."
        );
        findLinkedList.algorithmByStyle.put(
            "Recursion",
            "Algorithm: FIND_REC(INFO, LINK, PTR, ITEM)\n"
                + "1. If PTR = NULL, return NULL.\n"
                + "2. If ITEM = INFO[PTR], return PTR.\n"
                + "3. Return FIND_REC(INFO, LINK, LINK[PTR], ITEM)."
        );
        CONTENT.put("find_linked_list", findLinkedList);

        TechniqueContent deleteLinkedList = createTechniqueContent(
            "delete_linked_list",
            "DELETE (Delete from Linked List)",
            "DELETE removes the first node containing the given item from the linked list.",
            "It is like finding the first matching name on a list and removing that entry.",
            "O(1)",
            "O(n)",
            "O(n)",
            "O(1)",
            new int[] {10, 22, 35, 47, 59},
            35
        );
        deleteLinkedList.algorithmByStyle.put(
            "Iterative",
            "Procedure: DELETE(INFO, LINK, START, AVAIL, ITEM, FLAG)\n"
                + "1. If START = NULL, set FLAG := FALSE and Return.\n"
                + "2. If INFO[START] = ITEM, delete the first node and set FLAG := TRUE.\n"
                + "3. Initialize PTR and SAVE.\n"
                + "4. While PTR != NULL, if INFO[PTR] = ITEM, relink around PTR, move PTR to AVAIL, set FLAG := TRUE and Return.\n"
                + "5. Set FLAG := FALSE and Return."
        );
        deleteLinkedList.algorithmByStyle.put(
            "Functions",
            "Algorithm: DELETE_FUNC(INFO, LINK, START, AVAIL, ITEM)\n"
                + "1. Call DELETE_NODE(INFO, LINK, START, AVAIL, ITEM).\n"
                + "2. Return FLAG.\n\n"
                + "Procedure DELETE_NODE(INFO, LINK, START, AVAIL, ITEM)\n"
                + "1. If START = NULL, return FALSE.\n"
                + "2. Handle deletion of the first node if it matches ITEM.\n"
                + "3. Otherwise walk through the list with PTR and SAVE until ITEM is found and relink the list.\n"
                + "4. Return TRUE or FALSE."
        );
        deleteLinkedList.algorithmByStyle.put(
            "Recursion",
            "Algorithm: DELETE_REC(PTR, SAVE, ITEM)\n"
                + "1. If PTR = NULL, return FALSE.\n"
                + "2. If INFO[PTR] = ITEM, relink SAVE around PTR, move PTR to AVAIL, and return TRUE.\n"
                + "3. Return DELETE_REC(LINK[PTR], PTR, ITEM).\n"
                + "Special handling is required before recursion when deleting the first node."
        );
        CONTENT.put("delete_linked_list", deleteLinkedList);
        TechniqueContent stackBasics = createTechniqueContent(
            "stack_basics",
            "Stack Operations",
            "Stack Operations use PUSH to insert an item at the top and POP to remove the current top item.",
            "A stack of plates is the classic example because the last plate placed on top is removed first.",
            "O(1)",
            "O(1)",
            "O(1)",
            "O(n)",
            new int[] {10, 20, 30, 40},
            0
        );
        stackBasics.algorithmByStyle.put(
            "Iterative",
            "Procedure: PUSH\n"
                + "1. If TOP = MAXSTK:\n"
                + "   Print OVERFLOW and Return.\n"
                + "2. Set TOP := TOP + 1.\n"
                + "3. Set STACK[TOP] := ITEM.\n"
                + "4. Return.\n\n"
                + "Procedure: POP\n"
                + "1. If TOP = 0:\n"
                + "   Print UNDERFLOW and Return.\n"
                + "2. Set ITEM := STACK[TOP].\n"
                + "3. Set TOP := TOP - 1.\n"
                + "4. Return."
        );
        stackBasics.algorithmByStyle.put(
            "Functions",
            "1. Define function push(stack, top, max, item).\n"
                + "   a) Check overflow.\n"
                + "   b) Increment top.\n"
                + "   c) Insert item.\n\n"
                + "2. Define function pop(stack, top).\n"
                + "   a) Check underflow.\n"
                + "   b) Return top element.\n"
                + "   c) Decrement top."
        );
        stackBasics.algorithmByStyle.put(
            "Recursion",
            "Stack operations are not naturally recursive because PUSH and POP are single-step operations and no repeated subproblem exists."
        );
        CONTENT.put("stack_basics", stackBasics);

        TechniqueContent linkedStackPush = createTechniqueContent(
            "linked_stack_push",
            "Linked Stack PUSH",
            "Linked Stack PUSH creates a new node, links it to the current top, and makes it the new top.",
            "It is like placing one more plate on top of a linked chain of plates.",
            "O(1)",
            "O(1)",
            "O(1)",
            "O(1)",
            new int[] {12, 24, 36},
            48
        );
        linkedStackPush.algorithmByStyle.put(
            "Iterative",
            "Procedure: PUSH_LINKSTACK\n"
                + "1. If AVAIL = NULL:\n"
                + "   Print OVERFLOW and Exit.\n"
                + "2. Set NEW := AVAIL and AVAIL := LINK[AVAIL].\n"
                + "3. Set INFO[NEW] := ITEM.\n"
                + "4. Set LINK[NEW] := TOP.\n"
                + "5. Set TOP := NEW.\n"
                + "6. Exit."
        );
        linkedStackPush.algorithmByStyle.put(
            "Functions",
            "1. Define function push(top, avail, item).\n"
                + "   a) If avail == NULL, overflow.\n"
                + "   b) Create new node from avail.\n"
                + "   c) Assign data.\n"
                + "   d) Link new node to top.\n"
                + "   e) Update top."
        );
        linkedStackPush.algorithmByStyle.put(
            "Recursion",
            "Linked stack PUSH is not suitable for recursion because it is a single-step insertion operation with no repetitive subproblem."
        );
        CONTENT.put("linked_stack_push", linkedStackPush);

        TechniqueContent linkedStackPop = createTechniqueContent(
            "linked_stack_pop",
            "Linked Stack POP",
            "Linked Stack POP removes the top node, returns its data, and moves the top pointer to the next node.",
            "It is like removing the top plate from a linked stack of plates.",
            "O(1)",
            "O(1)",
            "O(1)",
            "O(1)",
            new int[] {15, 25, 35, 45},
            0
        );
        linkedStackPop.algorithmByStyle.put(
            "Iterative",
            "Procedure: POP_LINKSTACK\n"
                + "1. If TOP = NULL:\n"
                + "   Print UNDERFLOW and Exit.\n"
                + "2. Set ITEM := INFO[TOP].\n"
                + "3. Set TEMP := TOP and TOP := LINK[TOP].\n"
                + "4. Set LINK[TEMP] := AVAIL and AVAIL := TEMP.\n"
                + "5. Exit."
        );
        linkedStackPop.algorithmByStyle.put(
            "Functions",
            "1. Define function pop(top).\n"
                + "   a) If top == NULL, underflow.\n"
                + "   b) Store top data.\n"
                + "   c) Move top to next node.\n"
                + "   d) Return deleted value."
        );
        linkedStackPop.algorithmByStyle.put(
            "Recursion",
            "Linked stack POP is not suitable for recursion because it is a direct removal operation with no repeated subproblem."
        );
        CONTENT.put("linked_stack_pop", linkedStackPop);
        registerExpressionStackTopics();
        TechniqueContent linearQueue = createTechniqueContent(
            "queue_basics",
            "Linear Queue Insertion and Deletion",
            "A linear queue inserts at the rear and deletes from the front using two pointers named FRONT and REAR.",
            "A ticket counter line where the newest person joins at the back and the earliest person leaves first works like a linear queue.",
            "O(1)",
            "O(1)",
            "O(1)",
            "O(n)",
            new int[] {10, 20, 30, 40},
            0
        );
        linearQueue.algorithmByStyle.put(
            "Iterative",
            "Procedure: QINSERT\n"
                + "1. If REAR = N:\n"
                + "   Print \"OVERFLOW\" and Return.\n"
                + "2. If FRONT = NULL:\n"
                + "   FRONT := 1 and REAR := 1.\n"
                + "   Else:\n"
                + "      REAR := REAR + 1.\n"
                + "3. Set QUEUE[REAR] := ITEM.\n"
                + "4. Return.\n\n"
                + "Procedure: QDELETE\n"
                + "1. If FRONT = NULL:\n"
                + "   Print \"UNDERFLOW\" and Return.\n"
                + "2. Set ITEM := QUEUE[FRONT].\n"
                + "3. If FRONT = REAR:\n"
                + "   FRONT := NULL and REAR := NULL.\n"
                + "   Else:\n"
                + "      FRONT := FRONT + 1.\n"
                + "4. Return."
        );
        linearQueue.algorithmByStyle.put(
            "Functions",
            "1. Define function insert(queue, front, rear, n, item).\n"
                + "   a) Check overflow.\n"
                + "   b) Update front and rear.\n"
                + "   c) Insert element.\n\n"
                + "2. Define function delete(queue, front, rear).\n"
                + "   a) Check underflow.\n"
                + "   b) Remove element.\n"
                + "   c) Update front and rear."
        );
        linearQueue.algorithmByStyle.put(
            "Recursion",
            "Queue insertion and deletion are not suitable for recursion because:\n"
                + "- They are single-step operations.\n"
                + "- No repeated subproblem exists.\n\n"
                + "Conclusion:\n"
                + "Recursion is not efficient or required for queue operations."
        );
        CONTENT.put("queue_basics", linearQueue);

        TechniqueContent circularQueue = createTechniqueContent(
            "circular_queue",
            "Circular Queue Insertion and Deletion",
            "A circular queue reuses empty positions by moving REAR and FRONT back to the beginning when they reach the last index.",
            "A round token system where service continues around the same circular counter behaves like a circular queue.",
            "O(1)",
            "O(1)",
            "O(1)",
            "O(n)",
            new int[] {11, 22, 33, 44},
            0
        );
        circularQueue.algorithmByStyle.put(
            "Iterative",
            "Procedure: QINSERT\n"
                + "1. If (FRONT = 1 and REAR = N) or (FRONT = REAR + 1):\n"
                + "   Print \"OVERFLOW\" and Return.\n"
                + "2. If FRONT = NULL:\n"
                + "   FRONT := 1 and REAR := 1.\n"
                + "   Else if REAR = N:\n"
                + "   REAR := 1.\n"
                + "   Else:\n"
                + "   REAR := REAR + 1.\n"
                + "3. Set QUEUE[REAR] := ITEM.\n"
                + "4. Return.\n\n"
                + "Procedure: QDELETE\n"
                + "1. If FRONT = NULL:\n"
                + "   Print \"UNDERFLOW\" and Return.\n"
                + "2. Set ITEM := QUEUE[FRONT].\n"
                + "3. If FRONT = REAR:\n"
                + "   FRONT := NULL and REAR := NULL.\n"
                + "   Else if FRONT = N:\n"
                + "   FRONT := 1.\n"
                + "   Else:\n"
                + "   FRONT := FRONT + 1.\n"
                + "4. Return."
        );
        circularQueue.algorithmByStyle.put(
            "Functions",
            "1. Define function insert(queue, front, rear, n, item).\n"
                + "   a) Check overflow condition.\n"
                + "   b) Update rear using circular logic.\n"
                + "   c) Insert element.\n\n"
                + "2. Define function delete(queue, front, rear, n).\n"
                + "   a) Check underflow condition.\n"
                + "   b) Remove element.\n"
                + "   c) Update front using circular logic."
        );
        circularQueue.algorithmByStyle.put(
            "Recursion",
            "Circular queue operations are not suitable for recursion because:\n"
                + "- They are direct operations with no repeated subproblem.\n"
                + "- Iterative control with pointers is more efficient.\n\n"
                + "Conclusion:\n"
                + "Recursion is not required for circular queue insertion and deletion."
        );
        CONTENT.put("circular_queue", circularQueue);

        TechniqueContent linkedQueue = createTechniqueContent(
            "linked_queue",
            "Linked List as Queue Insertion and Deletion",
            "A linked queue inserts new nodes at the rear and deletes nodes from the front using linked nodes instead of a fixed array.",
            "A queue of people where each person points to the next person is a good way to imagine a linked queue.",
            "O(1)",
            "O(1)",
            "O(1)",
            "O(n)",
            new int[] {7, 14, 21, 28},
            0
        );
        linkedQueue.algorithmByStyle.put(
            "Iterative",
            "Procedure: LINKQ_INSERT\n"
                + "1. If AVAIL = NULL:\n"
                + "   Print \"OVERFLOW\" and Exit.\n"
                + "2. Set NEW := AVAIL.\n"
                + "   Set AVAIL := LINK[AVAIL].\n"
                + "3. Set INFO[NEW] := ITEM.\n"
                + "   Set LINK[NEW] := NULL.\n"
                + "4. If FRONT = NULL:\n"
                + "   FRONT := REAR := NEW.\n"
                + "   Else:\n"
                + "   LINK[REAR] := NEW.\n"
                + "   REAR := NEW.\n"
                + "5. Exit.\n\n"
                + "Procedure: LINKQ_DELETE\n"
                + "1. If FRONT = NULL:\n"
                + "   Print \"UNDERFLOW\" and Exit.\n"
                + "2. Set TEMP := FRONT.\n"
                + "3. Set ITEM := INFO[TEMP].\n"
                + "4. Set FRONT := LINK[TEMP].\n"
                + "5. Set LINK[TEMP] := AVAIL.\n"
                + "   Set AVAIL := TEMP.\n"
                + "6. Exit."
        );
        linkedQueue.algorithmByStyle.put(
            "Functions",
            "1. Define function insert(front, rear, item).\n"
                + "   a) Create new node.\n"
                + "   b) If queue empty, set front = rear = new.\n"
                + "   c) Else attach at rear and update rear.\n\n"
                + "2. Define function delete(front, rear).\n"
                + "   a) If empty, underflow.\n"
                + "   b) Remove front node.\n"
                + "   c) Update front pointer."
        );
        linkedQueue.algorithmByStyle.put(
            "Recursion",
            "Linked queue operations are not suitable for recursion because:\n"
                + "- They are single-step operations.\n"
                + "- No repeated subproblem exists.\n\n"
                + "Conclusion:\n"
                + "Recursion is not efficient or required for linked queue operations."
        );
        CONTENT.put("linked_queue", linkedQueue);

        TechniqueContent priorityQueueArray = createTechniqueContent(
            "priority_queue_array",
            "Priority Queue (One-dimensional Array)",
            "A priority queue in a one-dimensional array keeps items ordered by priority so deletion always removes the highest-priority front item.",
            "Hospital cases arranged so urgent patients are handled first match the idea of a priority queue.",
            "O(1)",
            "O(n)",
            "O(n)",
            "O(n)",
            new int[] {3, 1, 4, 2},
            0
        );
        priorityQueueArray.algorithmByStyle.put(
            "Iterative",
            "Procedure: INSERT\n"
                + "1. Traverse the list from START.\n"
                + "2. Find node where priority > N.\n"
                + "3. Insert ITEM before that node.\n"
                + "4. If no such node found:\n"
                + "   Insert ITEM at end.\n"
                + "5. Exit.\n\n"
                + "Procedure: DELETE\n"
                + "1. If queue empty:\n"
                + "   Print \"UNDERFLOW\" and Exit.\n"
                + "2. Set ITEM := first element.\n"
                + "3. Delete first element.\n"
                + "4. Process ITEM.\n"
                + "5. Exit."
        );
        priorityQueueArray.algorithmByStyle.put(
            "Functions",
            "1. Define function insert(queue, priorityQueue, size, item, priority).\n"
                + "   a) Traverse to find correct position.\n"
                + "   b) Shift elements.\n"
                + "   c) Insert item with priority.\n\n"
                + "2. Define function delete(queue, priorityQueue, size).\n"
                + "   a) Remove first element.\n"
                + "   b) Shift remaining elements."
        );
        priorityQueueArray.algorithmByStyle.put(
            "Recursion",
            "Priority queue insertion and deletion are not suitable for recursion because:\n"
                + "- They involve direct traversal and shifting.\n"
                + "- No repeated subproblem structure.\n\n"
                + "Conclusion:\n"
                + "Recursion is not efficient for priority queue operations in array."
        );
        CONTENT.put("priority_queue_array", priorityQueueArray);

        TechniqueContent priorityQueue2d = createTechniqueContent(
            "priority_queue_2d",
            "Priority Queue (Two-dimensional Array)",
            "A two-dimensional priority queue stores a separate queue for each priority level and deletes from the first non-empty priority row.",
            "Different service counters for emergency, urgent, and normal requests can be visualized as a two-dimensional priority queue.",
            "O(1)",
            "O(n)",
            "O(n)",
            "O(n x p)",
            new int[] {5, 10, 15, 20},
            0
        );
        priorityQueue2d.algorithmByStyle.put(
            "Iterative",
            "Procedure: INSERT\n"
                + "1. Insert ITEM at REAR of row M.\n"
                + "2. Update REAR[M].\n"
                + "3. Exit.\n\n"
                + "Procedure: DELETE\n"
                + "1. Find smallest K such that FRONT[K] != NULL.\n"
                + "2. Delete element from FRONT[K].\n"
                + "3. Update FRONT[K].\n"
                + "4. Exit."
        );
        priorityQueue2d.algorithmByStyle.put(
            "Functions",
            "1. Define function insert(queue, front[], rear[], m, item).\n"
                + "   a) Insert item in row m.\n"
                + "   b) Update rear pointer.\n\n"
                + "2. Define function delete(queue, front[], rear[], n).\n"
                + "   a) Find first non-empty row.\n"
                + "   b) Remove element from that row."
        );
        priorityQueue2d.algorithmByStyle.put(
            "Recursion",
            "Priority queue operations in 2D array are not suitable for recursion because:\n"
                + "- They involve direct insertion and deletion.\n"
                + "- No repeated subproblem exists.\n\n"
                + "Conclusion:\n"
                + "Recursion is not required for 2D priority queue operations."
        );
        CONTENT.put("priority_queue_2d", priorityQueue2d);
        CONTENT.put("singly_linked_list", createTechniqueContent(
            "singly_linked_list",
            "Singly Linked List",
            "A singly linked list stores nodes where each node points to the next node only.",
            "It is like a treasure hunt where each clue leads to exactly one next clue.",
            "O(1)",
            "O(n)",
            "O(n)",
            "O(n)",
            new int[] {6, 12, 18, 24, 30},
            0
        ));
        TechniqueContent traversingLinkedList = createTechniqueContent(
            "traversing_linked_list",
            "Traversing a Linked List",
            "Traversing a linked list means visiting each node from the start node until the pointer becomes null.",
            "It is like moving through a chain of connected boxes and reading each value one by one.",
            "O(n)",
            "O(n)",
            "O(n)",
            "O(1)",
            new int[] {11, 22, 33, 44, 55},
            0
        );
        traversingLinkedList.algorithmByStyle.put(
            "Iterative",
            "Algorithm 1: (Traversing a Linked List)\n\n"
                + "1. Set PTR := START.\n"
                + "2. Repeat while PTR != NULL.\n"
                + "3. Apply PROCESS to INFO[PTR].\n"
                + "4. Set PTR := LINK[PTR].\n"
                + "5. Exit."
        );
        traversingLinkedList.algorithmByStyle.put(
            "Functions",
            "FUNCTION TRAVERSE(START)\n\n"
                + "1. Set PTR := START.\n"
                + "2. Repeat while PTR != NULL:\n"
                + "   Apply PROCESS to INFO[PTR].\n"
                + "   Set PTR := LINK[PTR].\n"
                + "3. Return.\n\n"
                + "MAIN:\n"
                + "1. Create Linked List.\n"
                + "2. Call TRAVERSE(START)."
        );
        traversingLinkedList.algorithmByStyle.put(
            "Recursion",
            "FUNCTION TRAVERSE(PTR)\n\n"
                + "1. If PTR = NULL, return.\n"
                + "2. Apply PROCESS to INFO[PTR].\n"
                + "3. Call TRAVERSE(LINK[PTR])."
        );
        CONTENT.put("traversing_linked_list", traversingLinkedList);

        TechniqueContent printLinkedList = createTechniqueContent(
            "print_linked_list",
            "Print Linked List",
            "Print Linked List writes the data stored in each node from the start node to the end of the list.",
            "It is like reading out all names from a connected register one after another.",
            "O(n)",
            "O(n)",
            "O(n)",
            "O(1)",
            new int[] {15, 25, 35, 45, 55},
            0
        );
        printLinkedList.algorithmByStyle.put(
            "Iterative",
            "Procedure: PRINT(INFO, LINK, START)\n\n"
                + "1. Set PTR := START.\n"
                + "2. Repeat while PTR != NULL.\n"
                + "3. Write INFO[PTR].\n"
                + "4. Set PTR := LINK[PTR].\n"
                + "5. Return."
        );
        printLinkedList.algorithmByStyle.put(
            "Functions",
            "FUNCTION PRINT(START)\n\n"
                + "1. Set PTR := START.\n"
                + "2. Repeat while PTR != NULL:\n"
                + "   Write INFO[PTR].\n"
                + "   Set PTR := LINK[PTR].\n"
                + "3. Return.\n\n"
                + "MAIN:\n"
                + "1. Create Linked List.\n"
                + "2. Call PRINT(START)."
        );
        printLinkedList.algorithmByStyle.put(
            "Recursion",
            "FUNCTION PRINT(PTR)\n\n"
                + "1. If PTR = NULL, return.\n"
                + "2. Write INFO[PTR].\n"
                + "3. Call PRINT(LINK[PTR])."
        );
        CONTENT.put("print_linked_list", printLinkedList);

        TechniqueContent countNodesLinkedList = createTechniqueContent(
            "count_nodes_linked_list",
            "Count Nodes in Linked List",
            "Count Nodes in Linked List moves from the start node to the end and increases a counter for each node visited.",
            "It is like counting the number of people standing in a connected queue one by one.",
            "O(n)",
            "O(n)",
            "O(n)",
            "O(1)",
            new int[] {9, 18, 27, 36, 45},
            0
        );
        countNodesLinkedList.algorithmByStyle.put(
            "Iterative",
            "Procedure: COUNT(INFO, LINK, START, NUM)\n\n"
                + "1. Set NUM := 0. [Initializes counter.]\n"
                + "2. Set PTR := START. [Initializes pointer.]\n"
                + "3. Repeat Steps 4 and 5 while PTR != NULL.\n"
                + "4. Set NUM := NUM + 1. [Increases NUM by 1.]\n"
                + "5. Set PTR := LINK[PTR]. [Updates pointer.]\n"
                + "6. Return."
        );
        countNodesLinkedList.algorithmByStyle.put(
            "Functions",
            "FUNCTION COUNT(START)\n\n"
                + "1. Set NUM := 0.\n"
                + "2. Set PTR := START.\n"
                + "3. Repeat while PTR != NULL:\n"
                + "   Set NUM := NUM + 1.\n"
                + "   Set PTR := LINK[PTR].\n"
                + "4. Return NUM.\n\n"
                + "MAIN:\n"
                + "1. Create Linked List.\n"
                + "2. NUM := COUNT(START).\n"
                + "3. Print NUM."
        );
        countNodesLinkedList.algorithmByStyle.put(
            "Recursion",
            "FUNCTION COUNT(PTR)\n\n"
                + "1. If PTR = NULL, return 0.\n"
                + "2. Return 1 + COUNT(LINK[PTR])."
        );
        CONTENT.put("count_nodes_linked_list", countNodesLinkedList);

        TechniqueContent searchInLinkedList = createTechniqueContent(
            "search_in_linked_list",
            "Search in Linked List",
            "Search in Linked List checks each node from the start until the item is found or the list ends.",
            "It is like checking connected lockers one after another until you find the required label.",
            "O(1)",
            "O(n)",
            "O(n)",
            "O(1)",
            new int[] {14, 21, 28, 35, 42},
            35
        );
        searchInLinkedList.algorithmByStyle.put(
            "Iterative",
            "Algorithm 2 SEARCH(INFO, LINK, START, ITEM, LOC)\n\n"
                + "1. Set PTR := START.\n"
                + "2. Repeat Step 3 while PTR != NULL.\n"
                + "3. If ITEM = INFO[PTR], then:\n"
                + "   Set LOC := PTR\n"
                + "   and Exit.\n"
                + "   Else set PTR := LINK[PTR].\n"
                + "4. Set LOC := NULL.\n"
                + "5. Exit."
        );
        searchInLinkedList.algorithmByStyle.put(
            "Functions",
            "FUNCTION SEARCH(START, ITEM)\n\n"
                + "1. Set PTR := START.\n"
                + "2. Repeat while PTR != NULL:\n"
                + "   If INFO[PTR] = ITEM, return PTR.\n"
                + "   Else set PTR := LINK[PTR].\n"
                + "3. Return NULL.\n\n"
                + "MAIN:\n"
                + "1. Create Linked List.\n"
                + "2. LOC := SEARCH(START, ITEM).\n"
                + "3. Print LOC."
        );
        searchInLinkedList.algorithmByStyle.put(
            "Recursion",
            "FUNCTION SEARCH(PTR, ITEM)\n\n"
                + "1. If PTR = NULL, return NULL.\n"
                + "2. If INFO[PTR] = ITEM, return PTR.\n"
                + "3. Return SEARCH(LINK[PTR], ITEM)."
        );
        CONTENT.put("search_in_linked_list", searchInLinkedList);

        TechniqueContent insertAtBeginningLinkedList = createTechniqueContent(
            "insert_at_beginning_linked_list",
            "Insert at Beginning of a Linked List",
            "Insert at Beginning adds a new node before the current first node and makes it the new start.",
            "It is like placing a new card at the very front of a chain of cards.",
            "O(1)",
            "O(1)",
            "O(1)",
            "O(1)",
            new int[] {20, 30, 40, 50},
            10
        );
        insertAtBeginningLinkedList.algorithmByStyle.put(
            "Iterative",
            "Algorithm: INSFIRST(INFO, LINK, START, AVAIL, ITEM)\n\n"
                + "1. If AVAIL = NULL,\n"
                + "   then write OVERFLOW and Exit.\n"
                + "2. Set NEW := AVAIL\n"
                + "   and AVAIL := LINK[AVAIL].\n"
                + "3. Set INFO[NEW] := ITEM.\n"
                + "4. Set LINK[NEW] := START.\n"
                + "5. Set START := NEW.\n"
                + "6. Exit."
        );
        insertAtBeginningLinkedList.algorithmByStyle.put(
            "Functions",
            "FUNCTION INSFIRST(INFO, LINK, START, AVAIL, ITEM)\n\n"
                + "1. If AVAIL = NULL, print OVERFLOW and return.\n"
                + "2. Set NEW := AVAIL.\n"
                + "3. Set AVAIL := LINK[AVAIL].\n"
                + "4. Set INFO[NEW] := ITEM.\n"
                + "5. Set LINK[NEW] := START.\n"
                + "6. Set START := NEW.\n"
                + "7. Return START."
        );
        insertAtBeginningLinkedList.algorithmByStyle.put(
            "Recursion",
            "Recursion is not applicable here because insertion at beginning is a single-step pointer update."
        );
        CONTENT.put("insert_at_beginning_linked_list", insertAtBeginningLinkedList);

        TechniqueContent insertAfterGivenNodeLinkedList = createTechniqueContent(
            "insert_after_given_node_linked_list",
            "Inserting After a Given Node in Linked List",
            "This insertion adds a new node after the node whose location is already known.",
            "It is like placing a new link immediately after a selected chain link.",
            "O(1)",
            "O(1)",
            "O(1)",
            "O(1)",
            new int[] {11, 22, 33, 44},
            99
        );
        insertAfterGivenNodeLinkedList.algorithmByStyle.put(
            "Iterative",
            "Algorithm 5.5: INSLOC(INFO, LINK, START, AVAIL, LOC, ITEM)\n\n"
                + "1. If AVAIL = NULL,\n"
                + "   then write OVERFLOW and Exit.\n"
                + "2. Set NEW := AVAIL\n"
                + "   and AVAIL := LINK[AVAIL].\n"
                + "3. Set INFO[NEW] := ITEM.\n"
                + "4. If LOC = NULL,\n"
                + "   then set LINK[NEW] := START\n"
                + "   and START := NEW.\n"
                + "   Else set LINK[NEW] := LINK[LOC]\n"
                + "   and LINK[LOC] := NEW.\n"
                + "5. Exit."
        );
        insertAfterGivenNodeLinkedList.algorithmByStyle.put(
            "Functions",
            "FUNCTION INSLOC(INFO, LINK, START, AVAIL, LOC, ITEM)\n\n"
                + "1. If AVAIL = NULL, print OVERFLOW and return.\n"
                + "2. Set NEW := AVAIL.\n"
                + "3. Set AVAIL := LINK[AVAIL].\n"
                + "4. Set INFO[NEW] := ITEM.\n"
                + "5. If LOC = NULL:\n"
                + "   Set LINK[NEW] := START\n"
                + "   and START := NEW.\n"
                + "   Else set LINK[NEW] := LINK[LOC]\n"
                + "   and LINK[LOC] := NEW.\n"
                + "6. Return START."
        );
        insertAfterGivenNodeLinkedList.algorithmByStyle.put(
            "Recursion",
            "Recursion is not applicable here because insertion after a given node is a direct pointer manipulation operation."
        );
        CONTENT.put("insert_after_given_node_linked_list", insertAfterGivenNodeLinkedList);

        TechniqueContent insertIntoSortedLinkedList = createTechniqueContent(
            "insert_into_sorted_linked_list",
            "Inserting into a Sorted Linked List",
            "Inserting into a Sorted Linked List first finds the correct position and then inserts the item there.",
            "It is like inserting a new roll number into an already sorted attendance line without breaking the order.",
            "O(1)",
            "O(n)",
            "O(n)",
            "O(1)",
            new int[] {10, 20, 30, 50, 60},
            40
        );
        insertIntoSortedLinkedList.algorithmByStyle.put(
            "Iterative",
            "Procedure: FINDA(INFO, LINK, START, ITEM, LOC)\n\n"
                + "1. If START = NULL,\n"
                + "   set LOC := NULL and Return.\n"
                + "2. If ITEM < INFO[START],\n"
                + "   set LOC := NULL and Return.\n"
                + "3. Set SAVE := START\n"
                + "   and PTR := LINK[START].\n"
                + "4. Repeat Steps 5 and 6 while PTR != NULL.\n"
                + "5. If ITEM < INFO[PTR],\n"
                + "   set LOC := SAVE and Return.\n"
                + "6. Set SAVE := PTR\n"
                + "   and PTR := LINK[PTR].\n"
                + "7. Set LOC := SAVE.\n"
                + "8. Return.\n\n"
                + "Algorithm: INSERT(INFO, LINK, START, AVAIL, ITEM)\n"
                + "1. Call FINDA(INFO, LINK, START, ITEM, LOC).\n"
                + "2. Call INSLOC(INFO, LINK, START, AVAIL, LOC, ITEM).\n"
                + "3. Exit."
        );
        insertIntoSortedLinkedList.algorithmByStyle.put(
            "Functions",
            "FUNCTION FINDA(INFO, LINK, START, ITEM)\n\n"
                + "1. If START = NULL, return NULL.\n"
                + "2. If ITEM < INFO[START], return NULL.\n"
                + "3. Set SAVE := START\n"
                + "   and PTR := LINK[START].\n"
                + "4. While PTR != NULL:\n"
                + "   If ITEM < INFO[PTR], return SAVE.\n"
                + "   Set SAVE := PTR\n"
                + "   and PTR := LINK[PTR].\n"
                + "5. Return SAVE.\n\n"
                + "FUNCTION INSERT(...)\n"
                + "1. LOC := FINDA(...)\n"
                + "2. Call INSLOC(...)\n"
                + "3. Return."
        );
        insertIntoSortedLinkedList.algorithmByStyle.put(
            "Recursion",
            "FUNCTION FINDA(PTR, ITEM)\n\n"
                + "1. If PTR = NULL, return NULL.\n"
                + "2. If LINK[PTR] = NULL, return PTR.\n"
                + "3. If ITEM < INFO[LINK[PTR]], return PTR.\n"
                + "4. Return FINDA(LINK[PTR], ITEM).\n\n"
                + "FUNCTION INSERT(START, AVAIL, ITEM)\n"
                + "1. LOC := FINDA(START, ITEM).\n"
                + "2. Call INSLOC(LOC, ITEM)."
        );
        CONTENT.put("insert_into_sorted_linked_list", insertIntoSortedLinkedList);

        TechniqueContent deleteNodeGivenLocationLinkedList = createTechniqueContent(
            "delete_node_given_location_linked_list",
            "Deleting a Node from a Linked List (Given Location)",
            "This deletion removes a node when its location and predecessor location are already known.",
            "It is like removing one known link from a chain when you already know which link comes before it.",
            "O(1)",
            "O(1)",
            "O(1)",
            "O(1)",
            new int[] {13, 26, 39, 52, 65},
            2
        );
        deleteNodeGivenLocationLinkedList.algorithmByStyle.put(
            "Iterative",
            "Algorithm: DEL(INFO, LINK, START, AVAIL, LOC, LOCP)\n\n"
                + "1. If LOCP = NULL,\n"
                + "   then set START := LINK[START].\n"
                + "   Else set LINK[LOCP] := LINK[LOC].\n"
                + "2. Set LINK[LOC] := AVAIL\n"
                + "   and AVAIL := LOC.\n"
                + "3. Exit."
        );
        deleteNodeGivenLocationLinkedList.algorithmByStyle.put(
            "Functions",
            "FUNCTION DEL(START, AVAIL, LOC, LOCP)\n\n"
                + "1. If LOCP = NULL,\n"
                + "   set START := LINK[START].\n"
                + "   Else set LINK[LOCP] := LINK[LOC].\n"
                + "2. Set LINK[LOC] := AVAIL\n"
                + "   and AVAIL := LOC.\n"
                + "3. Return START, AVAIL."
        );
        deleteNodeGivenLocationLinkedList.algorithmByStyle.put(
            "Recursion",
            "Recursion is not naturally applicable here because deletion is a direct pointer update operation."
        );
        CONTENT.put("delete_node_given_location_linked_list", deleteNodeGivenLocationLinkedList);

        TechniqueContent deleteNodeByItemLinkedList = createTechniqueContent(
            "delete_node_by_item_linked_list",
            "Deleting a Node by ITEM in a Linked List",
            "This method first finds the node containing the item and its predecessor, then removes that node.",
            "It is like finding a named card in a chain and reconnecting the chain after removing that card.",
            "O(1)",
            "O(n)",
            "O(n)",
            "O(1)",
            new int[] {17, 34, 51, 68, 85},
            51
        );
        deleteNodeByItemLinkedList.algorithmByStyle.put(
            "Iterative",
            "Procedure: FINDB(INFO, LINK, START, ITEM, LOC, LOCP)\n\n"
                + "1. If START = NULL,\n"
                + "   set LOC := NULL,\n"
                + "   LOCP := NULL,\n"
                + "   and Return.\n"
                + "2. If INFO[START] = ITEM,\n"
                + "   set LOC := START,\n"
                + "   LOCP := NULL,\n"
                + "   and Return.\n"
                + "3. Set SAVE := START\n"
                + "   and PTR := LINK[START].\n"
                + "4. Repeat Steps 5 and 6 while PTR != NULL.\n"
                + "5. If INFO[PTR] = ITEM,\n"
                + "   set LOC := PTR,\n"
                + "   LOCP := SAVE,\n"
                + "   and Return.\n"
                + "6. Set SAVE := PTR\n"
                + "   and PTR := LINK[PTR].\n"
                + "7. Set LOC := NULL.\n"
                + "8. Return.\n\n"
                + "Algorithm: DELETE(INFO, LINK, START, AVAIL, ITEM)\n"
                + "1. Call FINDB(INFO, LINK, START, ITEM, LOC, LOCP).\n"
                + "2. If LOC = NULL,\n"
                + "   write ITEM not in list,\n"
                + "   and Exit.\n"
                + "3. If LOCP = NULL,\n"
                + "   set START := LINK[START];\n"
                + "   else set LINK[LOCP] := LINK[LOC].\n"
                + "4. Set LINK[LOC] := AVAIL\n"
                + "   and AVAIL := LOC.\n"
                + "5. Exit."
        );
        deleteNodeByItemLinkedList.algorithmByStyle.put(
            "Functions",
            "FUNCTION FINDB(START, ITEM)\n\n"
                + "1. If START = NULL, return (NULL, NULL).\n"
                + "2. If INFO[START] = ITEM, return (START, NULL).\n"
                + "3. Set SAVE := START\n"
                + "   and PTR := LINK[START].\n"
                + "4. While PTR != NULL:\n"
                + "   If INFO[PTR] = ITEM, return (PTR, SAVE).\n"
                + "   Set SAVE := PTR\n"
                + "   and PTR := LINK[PTR].\n"
                + "5. Return (NULL, NULL).\n\n"
                + "FUNCTION DELETE(START, AVAIL, ITEM)\n"
                + "1. (LOC, LOCP) := FINDB(START, ITEM).\n"
                + "2. If LOC = NULL,\n"
                + "   print ITEM not in list\n"
                + "   and return START.\n"
                + "3. If LOCP = NULL,\n"
                + "   START := LINK[START];\n"
                + "   else LINK[LOCP] := LINK[LOC].\n"
                + "4. LINK[LOC] := AVAIL\n"
                + "   and AVAIL := LOC.\n"
                + "5. Return START."
        );
        deleteNodeByItemLinkedList.algorithmByStyle.put(
            "Recursion",
            "FINDB and DELETE are not naturally recursive because predecessor tracking is required throughout the traversal."
        );
        CONTENT.put("delete_node_by_item_linked_list", deleteNodeByItemLinkedList);

        TechniqueContent traverseCircularHeaderList = createTechniqueContent(
            "traverse_circular_header_list",
            "Traversing a Circular Header List",
            "Traversing a circular header list starts from the first node after the header and continues until the header is reached again.",
            "It is like moving around a circular chain and stopping only when you return to the starting marker.",
            "O(n)",
            "O(n)",
            "O(n)",
            "O(1)",
            new int[] {8, 16, 24, 32, 40},
            0
        );
        traverseCircularHeaderList.algorithmByStyle.put(
            "Iterative",
            "1. Set PTR := LINK[START].\n"
                + "2. Repeat Steps 3 and 4 while PTR != START.\n"
                + "3. Apply PROCESS to INFO[PTR].\n"
                + "4. Set PTR := LINK[PTR].\n"
                + "5. Exit."
        );
        traverseCircularHeaderList.algorithmByStyle.put(
            "Functions",
            "1. Create a function traverse(LIST).\n"
                + "2. Inside function set PTR := LINK[START].\n"
                + "3. While PTR != START:\n"
                + "   PROCESS(INFO[PTR]).\n"
                + "   PTR := LINK[PTR].\n"
                + "4. Return to main."
        );
        traverseCircularHeaderList.algorithmByStyle.put(
            "Recursion",
            "1. Define recursive function traverse(PTR, START).\n"
                + "2. If PTR == START, return.\n"
                + "3. PROCESS(INFO[PTR]).\n"
                + "4. Call traverse(LINK[PTR], START)."
        );
        CONTENT.put("traverse_circular_header_list", traverseCircularHeaderList);

        TechniqueContent searchCircularHeaderList = createTechniqueContent(
            "search_circular_header_list",
            "Searching a Circular Header List",
            "Searching a circular header list moves through nodes until the item is found or the pointer returns to the header.",
            "It is like checking names around a circular seating arrangement until the person is found or you reach the start again.",
            "O(1)",
            "O(n)",
            "O(n)",
            "O(1)",
            new int[] {7, 14, 21, 28, 35},
            21
        );
        searchCircularHeaderList.algorithmByStyle.put(
            "Iterative",
            "1. Set PTR := LINK[START].\n"
                + "2. Repeat while INFO[PTR] != ITEM and PTR != START:\n"
                + "   PTR := LINK[PTR].\n"
                + "3. If INFO[PTR] = ITEM, set LOC := PTR; else set LOC := NULL.\n"
                + "4. Exit."
        );
        searchCircularHeaderList.algorithmByStyle.put(
            "Functions",
            "1. Create function search(LIST, ITEM).\n"
                + "2. Set PTR := LINK[START].\n"
                + "3. While INFO[PTR] != ITEM and PTR != START:\n"
                + "   PTR := LINK[PTR].\n"
                + "4. If INFO[PTR] == ITEM, return PTR; else return NULL."
        );
        searchCircularHeaderList.algorithmByStyle.put(
            "Recursion",
            "1. Define recursive function search(PTR, START, ITEM).\n"
                + "2. If PTR == START, return NULL.\n"
                + "3. If INFO[PTR] == ITEM, return PTR.\n"
                + "4. Return search(LINK[PTR], START, ITEM)."
        );
        CONTENT.put("search_circular_header_list", searchCircularHeaderList);

        TechniqueContent deleteCircularHeaderList = createTechniqueContent(
            "delete_circular_header_list",
            "Deleting from a Circular Header List",
            "Deleting from a circular header list first finds the node and its previous node, then reconnects the links to remove it.",
            "It is like removing one link from a circular chain and joining the remaining ends together.",
            "O(1)",
            "O(n)",
            "O(n)",
            "O(1)",
            new int[] {12, 24, 36, 48, 60},
            36
        );
        deleteCircularHeaderList.algorithmByStyle.put(
            "Iterative",
            "Procedure 1: FINDBHL\n"
                + "1. Set SAVE := START\n"
                + "   and PTR := LINK[START].\n"
                + "2. Repeat while INFO[PTR] != ITEM and PTR != START:\n"
                + "   SAVE := PTR.\n"
                + "   PTR := LINK[PTR].\n"
                + "3. If INFO[PTR] = ITEM,\n"
                + "   set LOC := PTR\n"
                + "   and LOCP := SAVE;\n"
                + "   else set LOC := NULL\n"
                + "   and LOCP := SAVE.\n"
                + "4. Exit.\n\n"
                + "Procedure 2: DELLOCHL\n"
                + "1. Call FINDBHL to find LOC and LOCP.\n"
                + "2. If LOC = NULL, print ITEM not in list and Exit.\n"
                + "3. Set LINK[LOCP] := LINK[LOC].\n"
                + "4. Set LINK[LOC] := AVAIL and AVAIL := LOC.\n"
                + "5. Exit."
        );
        deleteCircularHeaderList.algorithmByStyle.put(
            "Functions",
            "1. Create function findNode(header, item).\n"
                + "2. Set SAVE := header\n"
                + "   and PTR := header.next.\n"
                + "3. While PTR != header and PTR.data != item:\n"
                + "   SAVE := PTR.\n"
                + "   PTR := PTR.next.\n"
                + "4. Return (PTR, SAVE).\n\n"
                + "5. Create function deleteNode(header, item).\n"
                + "6. Call findNode.\n"
                + "7. If node not found, print message.\n"
                + "8. Else adjust links to delete node."
        );
        deleteCircularHeaderList.algorithmByStyle.put(
            "Recursion",
            "1. Define recursive find function find(PTR, START, PREV, ITEM).\n"
                + "2. If PTR == START, return NULL and previous.\n"
                + "3. If PTR.data == ITEM, return PTR and previous.\n"
                + "4. Return find(LINK[PTR], START, PTR, ITEM).\n"
                + "5. Delete using the returned location and previous node."
        );
        CONTENT.put("delete_circular_header_list", deleteCircularHeaderList);

        TechniqueContent quicksort = createTechniqueContent(
            "quicksort",
            "Quicksort",
            "Quicksort partitions the array around a pivot and then sorts the left and right subarrays.",
            "It is like choosing one reference number, placing smaller values on one side and larger values on the other, then repeating the same idea inside each side.",
            "O(n log n)",
            "O(n log n)",
            "O(n^2)",
            "O(log n)",
            new int[] {44, 12, 38, 5, 29, 18, 50},
            0
        );
        quicksort.algorithmByStyle.put(
            "Iterative",
            "(Quick Sort) QUICK(A, N)\n\n"
                + "1. Set TOP := NULL.\n"
                + "2. If N > 1:\n"
                + "   TOP := TOP + 1.\n"
                + "   LOWER[TOP] := 1.\n"
                + "   UPPER[TOP] := N.\n"
                + "3. Repeat while TOP != NULL.\n"
                + "4. Set BEG := LOWER[TOP],\n"
                + "   END := UPPER[TOP].\n"
                + "   TOP := TOP - 1.\n"
                + "5. Partition A[BEG...END]\n"
                + "   and get pivot position LOC.\n"
                + "6. If BEG < LOC - 1:\n"
                + "   TOP := TOP + 1.\n"
                + "   LOWER[TOP] := BEG.\n"
                + "   UPPER[TOP] := LOC - 1.\n"
                + "7. If LOC + 1 < END:\n"
                + "   TOP := TOP + 1.\n"
                + "   LOWER[TOP] := LOC + 1.\n"
                + "   UPPER[TOP] := END.\n"
                + "8. Exit."
        );
        quicksort.algorithmByStyle.put(
            "Functions",
            "FUNCTION quickSort(A, low, high)\n\n"
                + "1. Define quickSort(A, low, high).\n"
                + "2. If low < high:\n"
                + "   a) Partition A[low...high]\n"
                + "      and get pivot index.\n"
                + "   b) quickSort(A, low, pivot - 1).\n"
                + "   c) quickSort(A, pivot + 1, high)."
        );
        quicksort.algorithmByStyle.put(
            "Recursion",
            "Base Case:\n"
                + "If low >= high, return.\n\n"
                + "Recursive Case:\n"
                + "1. Partition A[low...high]\n"
                + "   and get pivot index.\n"
                + "2. Recursively sort left subarray.\n"
                + "3. Recursively sort right subarray."
        );
        CONTENT.put("quicksort", quicksort);

        TechniqueContent preorderTraversal = createTechniqueContent(
            "preorder_traversal",
            "Preorder Traversal",
            "Preorder traversal visits the root first, then the left subtree, and finally the right subtree.",
            "It is like announcing the team leader before introducing the left team and then the right team.",
            "O(n)",
            "O(n)",
            "O(n)",
            "O(h)",
            new int[] {40, 20, 60, 10, 30, 50, 70},
            0
        );
        preorderTraversal.algorithmByStyle.put(
            "Iterative",
            "Algorithm 7.1: PREORD(INFO, LEFT, RIGHT, ROOT)\n"
                + "1. Set TOP := 1,\n"
                + "   STACK[1] := NULL,\n"
                + "   and PTR := ROOT.\n"
                + "2. Repeat Steps 3 to 5 while PTR != NULL.\n"
                + "3. Apply PROCESS to INFO[PTR].\n"
                + "4. If RIGHT[PTR] != NULL, then:\n"
                + "   Set TOP := TOP + 1\n"
                + "   and STACK[TOP] := RIGHT[PTR].\n"
                + "5. If LEFT[PTR] != NULL, then:\n"
                + "   Set PTR := LEFT[PTR].\n"
                + "   Else:\n"
                + "   Set PTR := STACK[TOP]\n"
                + "   and TOP := TOP - 1.\n"
                + "6. Exit."
        );
        preorderTraversal.algorithmByStyle.put(
            "Functions",
            "Algorithm: PREORDER_FUNC(INFO, LEFT, RIGHT, ROOT)\n"
                + "1. Call PREORDER(ROOT).\n"
                + "2. Exit.\n\n"
                + "Procedure PREORDER(PTR)\n"
                + "1. If PTR = NULL, then return.\n"
                + "2. Apply PROCESS to INFO[PTR].\n"
                + "3. Call PREORDER(LEFT[PTR]).\n"
                + "4. Call PREORDER(RIGHT[PTR]).\n"
                + "5. Return."
        );
        preorderTraversal.algorithmByStyle.put(
            "Recursion",
            "Algorithm: PREORDER_REC(INFO, LEFT, RIGHT, ROOT)\n"
                + "1. If ROOT = NULL, then Exit.\n"
                + "2. Apply PROCESS to INFO[ROOT].\n"
                + "3. Call PREORDER_REC(INFO, LEFT, RIGHT, LEFT[ROOT]).\n"
                + "4. Call PREORDER_REC(INFO, LEFT, RIGHT, RIGHT[ROOT]).\n"
                + "5. Exit."
        );
        CONTENT.put("preorder_traversal", preorderTraversal);

        TechniqueContent postorderTraversal = createTechniqueContent(
            "postorder_traversal",
            "Postorder Traversal",
            "Postorder traversal visits the left subtree, then the right subtree, and processes the root at the end.",
            "It is like checking both branches of a task tree fully before reporting back to the manager.",
            "O(n)",
            "O(n)",
            "O(n)",
            "O(h)",
            new int[] {40, 20, 60, 10, 30, 50, 70},
            0
        );
        postorderTraversal.algorithmByStyle.put(
            "Iterative",
            "Algorithm: POSTORD(INFO, LEFT, RIGHT, ROOT)\n"
                + "1. Set TOP := 1,\n"
                + "   STACK[1] := NULL,\n"
                + "   and PTR := ROOT.\n"
                + "2. Repeat Steps 3 to 5 while PTR != NULL.\n"
                + "3. Set TOP := TOP + 1\n"
                + "   and STACK[TOP] := PTR.\n"
                + "4. If RIGHT[PTR] != NULL,\n"
                + "   then set TOP := TOP + 1\n"
                + "   and STACK[TOP] := RIGHT[PTR].\n"
                + "5. Set PTR := LEFT[PTR].\n"
                + "6. Set PTR := STACK[TOP]\n"
                + "   and TOP := TOP - 1.\n"
                + "7. Repeat while PTR > 0:\n"
                + "   a) Apply PROCESS to INFO[PTR].\n"
                + "   b) Set PTR := STACK[TOP]\n"
                + "      and TOP := TOP - 1.\n"
                + "8. If PTR < 0, then set PTR := -PTR and go to Step 2.\n"
                + "9. Exit."
        );
        postorderTraversal.algorithmByStyle.put(
            "Functions",
            "Algorithm: POSTORDER_FUNC(INFO, LEFT, RIGHT, ROOT)\n"
                + "1. Call POSTORDER(ROOT).\n"
                + "2. Exit.\n\n"
                + "Procedure POSTORDER(PTR)\n"
                + "1. If PTR = NULL, then return.\n"
                + "2. Call POSTORDER(LEFT[PTR]).\n"
                + "3. Call POSTORDER(RIGHT[PTR]).\n"
                + "4. Apply PROCESS to INFO[PTR].\n"
                + "5. Return."
        );
        postorderTraversal.algorithmByStyle.put(
            "Recursion",
            "Algorithm: POSTORDER_REC(INFO, LEFT, RIGHT, ROOT)\n"
                + "1. If ROOT = NULL, then Exit.\n"
                + "2. Call POSTORDER_REC(INFO, LEFT, RIGHT, LEFT[ROOT]).\n"
                + "3. Call POSTORDER_REC(INFO, LEFT, RIGHT, RIGHT[ROOT]).\n"
                + "4. Apply PROCESS to INFO[ROOT].\n"
                + "5. Exit."
        );
        CONTENT.put("postorder_traversal", postorderTraversal);

        TechniqueContent bstSearchParentTracking = createTechniqueContent(
            "bst_search_parent_tracking",
            "BST Search with Parent Tracking",
            "This BST search finds whether the item exists and also records the parent of the current or final node.",
            "It is like searching a family tree for a person and also remembering their immediate parent.",
            "O(1)",
            "O(log n)",
            "O(n)",
            "O(h)",
            new int[] {40, 20, 60, 10, 30, 50, 70},
            30
        );
        bstSearchParentTracking.algorithmByStyle.put(
            "Iterative",
            "Procedure 7.4: FIND(INFO, LEFT, RIGHT, ROOT, ITEM, LOC, PAR)\n"
                + "1. If ROOT = NULL,\n"
                + "   then set LOC := NULL and PAR := NULL,\n"
                + "   and Return.\n"
                + "2. If ITEM = INFO[ROOT],\n"
                + "   then set LOC := ROOT and PAR := NULL,\n"
                + "   and Return.\n"
                + "3. If ITEM < INFO[ROOT],\n"
                + "   then set PTR := LEFT[ROOT] and SAVE := ROOT.\n"
                + "   Else set PTR := RIGHT[ROOT] and SAVE := ROOT.\n"
                + "4. Repeat Steps 5 and 6 while PTR != NULL.\n"
                + "5. If ITEM = INFO[PTR],\n"
                + "   then set LOC := PTR and PAR := SAVE,\n"
                + "   and Return.\n"
                + "6. If ITEM < INFO[PTR],\n"
                + "   then set SAVE := PTR and PTR := LEFT[PTR].\n"
                + "   Else set SAVE := PTR and PTR := RIGHT[PTR].\n"
                + "7. Set LOC := NULL and PAR := SAVE.\n"
                + "8. Exit."
        );
        bstSearchParentTracking.algorithmByStyle.put(
            "Functions",
            "Algorithm: FIND_FUNC(INFO, LEFT, RIGHT, ROOT, ITEM, LOC, PAR)\n"
                + "1. Call FIND_REC(ROOT, NULL).\n"
                + "2. Exit.\n\n"
                + "Procedure FIND_REC(PTR, SAVE)\n"
                + "1. If PTR = NULL, then set LOC := NULL and PAR := SAVE, and return.\n"
                + "2. If ITEM = INFO[PTR], then set LOC := PTR and PAR := SAVE, and return.\n"
                + "3. If ITEM < INFO[PTR], then call FIND_REC(LEFT[PTR], PTR).\n"
                + "4. Else call FIND_REC(RIGHT[PTR], PTR).\n"
                + "5. Return."
        );
        bstSearchParentTracking.algorithmByStyle.put(
            "Recursion",
            "Algorithm: FIND_REC(INFO, LEFT, RIGHT, ROOT, ITEM, LOC, PAR)\n"
                + "1. If ROOT = NULL, then set LOC := NULL and PAR := NULL, and Exit.\n"
                + "2. If ITEM = INFO[ROOT], then set LOC := ROOT and PAR := NULL, and Exit.\n"
                + "3. If ITEM < INFO[ROOT], then call FIND_REC(INFO, LEFT, RIGHT, LEFT[ROOT], ITEM, LOC, ROOT).\n"
                + "4. Else call FIND_REC(INFO, LEFT, RIGHT, RIGHT[ROOT], ITEM, LOC, ROOT).\n"
                + "5. Exit."
        );
        CONTENT.put("bst_search_parent_tracking", bstSearchParentTracking);

        TechniqueContent bstInsertion = createTechniqueContent(
            "bst_insertion",
            "BST Insertion (INSBST)",
            "BST insertion places a new node in the correct position so the binary search tree order remains valid.",
            "It is like adding a new name into a family tree in the correct branch based on value rules.",
            "O(1)",
            "O(log n)",
            "O(n)",
            "O(h)",
            new int[] {40, 20, 60, 10, 30, 50},
            70
        );
        bstInsertion.algorithmByStyle.put(
            "Iterative",
            "Algorithm 7.5: INSBST(INFO, LEFT, RIGHT, ROOT, AVAIL, ITEM, LOC)\n"
                + "1. Call FIND(INFO, LEFT, RIGHT, ROOT, ITEM, LOC, PAR).\n"
                + "2. If LOC != NULL, then Exit.\n"
                + "3. If AVAIL = NULL, then write OVERFLOW and Exit.\n"
                + "4. Set NEW := AVAIL,\n"
                + "   AVAIL := LEFT[AVAIL],\n"
                + "   and INFO[NEW] := ITEM.\n"
                + "5. Set LOC := NEW,\n"
                + "   LEFT[NEW] := NULL,\n"
                + "   and RIGHT[NEW] := NULL.\n"
                + "6. If PAR = NULL, then set ROOT := NEW.\n"
                + "7. Else if ITEM < INFO[PAR], then set LEFT[PAR] := NEW.\n"
                + "8. Else set RIGHT[PAR] := NEW.\n"
                + "9. Exit."
        );
        bstInsertion.algorithmByStyle.put(
            "Functions",
            "Algorithm: INSBST_FUNC(INFO, LEFT, RIGHT, ROOT, ITEM, LOC)\n"
                + "1. Call FIND_FUNC(INFO, LEFT, RIGHT, ROOT, ITEM, LOC, PAR).\n"
                + "2. If LOC != NULL, then return.\n"
                + "3. Create NEW node.\n"
                + "4. Set INFO[NEW] := ITEM, LEFT[NEW] := NULL, RIGHT[NEW] := NULL.\n"
                + "5. If PAR = NULL, then set ROOT := NEW.\n"
                + "6. Else if ITEM < INFO[PAR], then set LEFT[PAR] := NEW.\n"
                + "7. Else set RIGHT[PAR] := NEW.\n"
                + "8. Return."
        );
        bstInsertion.algorithmByStyle.put(
            "Recursion",
            "Algorithm: INSBST_REC(INFO, LEFT, RIGHT, ROOT, ITEM)\n"
                + "1. If ROOT = NULL,\n"
                + "   then create NEW node,\n"
                + "   copy ITEM,\n"
                + "   set LEFT[NEW] := NULL\n"
                + "   and RIGHT[NEW] := NULL,\n"
                + "   set ROOT := NEW,\n"
                + "   and Exit.\n"
                + "2. If ITEM < INFO[ROOT], then call INSBST_REC(INFO, LEFT, RIGHT, LEFT[ROOT], ITEM).\n"
                + "3. Else if ITEM > INFO[ROOT], then call INSBST_REC(INFO, LEFT, RIGHT, RIGHT[ROOT], ITEM).\n"
                + "4. Else Exit.\n"
                + "5. Exit."
        );
        CONTENT.put("bst_insertion", bstInsertion);

        TechniqueContent bstDeletionCaseA = createTechniqueContent(
            "bst_deletion_case_a",
            "BST Deletion (CASE A: Node with at most one child)",
            "Case A deletion removes a BST node that has no child or only one child.",
            "It is like removing a person from a chain when that person has at most one direct next connection to reconnect.",
            "O(1)",
            "O(log n)",
            "O(n)",
            "O(h)",
            new int[] {40, 20, 60, 10, 30, 50, 70},
            10
        );
        bstDeletionCaseA.algorithmByStyle.put(
            "Iterative",
            "Procedure 7.6: CASEA(INFO, LEFT, RIGHT, ROOT, LOC, PAR)\n"
                + "1. If LEFT[LOC] = NULL and RIGHT[LOC] = NULL,\n"
                + "   then set CHILD := NULL.\n"
                + "2. Else if LEFT[LOC] != NULL,\n"
                + "   then set CHILD := LEFT[LOC].\n"
                + "3. Else set CHILD := RIGHT[LOC].\n"
                + "4. If PAR != NULL, then:\n"
                + "   If LOC = LEFT[PAR], then set LEFT[PAR] := CHILD.\n"
                + "   Else set RIGHT[PAR] := CHILD.\n"
                + "5. Else set ROOT := CHILD.\n"
                + "6. Return."
        );
        bstDeletionCaseA.algorithmByStyle.put(
            "Functions",
            "Algorithm: CASEA_FUNC(INFO, LEFT, RIGHT, ROOT, LOC, PAR)\n"
                + "1. Call DELETE_NODE(LOC, PAR).\n"
                + "2. Return.\n\n"
                + "Procedure DELETE_NODE(LOC, PAR)\n"
                + "1. If LEFT[LOC] = NULL and RIGHT[LOC] = NULL, then set CHILD := NULL.\n"
                + "2. Else if LEFT[LOC] != NULL, then set CHILD := LEFT[LOC].\n"
                + "3. Else set CHILD := RIGHT[LOC].\n"
                + "4. If PAR != NULL, then link CHILD as left or right child of PAR.\n"
                + "5. Else set ROOT := CHILD.\n"
                + "6. Return."
        );
        bstDeletionCaseA.algorithmByStyle.put(
            "Recursion",
            "Algorithm: CASEA_REC(INFO, LEFT, RIGHT, ROOT, ITEM)\n"
                + "1. If ROOT = NULL, then Exit.\n"
                + "2. If ITEM < INFO[ROOT], then call CASEA_REC(INFO, LEFT, RIGHT, LEFT[ROOT], ITEM).\n"
                + "3. Else if ITEM > INFO[ROOT], then call CASEA_REC(INFO, LEFT, RIGHT, RIGHT[ROOT], ITEM).\n"
                + "4. Else if LEFT[ROOT] = NULL and RIGHT[ROOT] = NULL, then set ROOT := NULL.\n"
                + "5. Else if LEFT[ROOT] != NULL, then set ROOT := LEFT[ROOT].\n"
                + "6. Else set ROOT := RIGHT[ROOT].\n"
                + "7. Exit."
        );
        CONTENT.put("bst_deletion_case_a", bstDeletionCaseA);

        TechniqueContent bstDeletionCaseB = createTechniqueContent(
            "bst_deletion_case_b",
            "BST Deletion (CASE B: Node with two children)",
            "Case B deletion replaces the node by its inorder successor and then removes that successor.",
            "It is like replacing a leader with the next-in-order qualified person before removing the original leader.",
            "O(log n)",
            "O(log n)",
            "O(n)",
            "O(h)",
            new int[] {40, 20, 60, 10, 30, 50, 70},
            40
        );
        bstDeletionCaseB.algorithmByStyle.put(
            "Iterative",
            "Procedure 7.7: CASEB(INFO, LEFT, RIGHT, ROOT, LOC, PAR)\n"
                + "1. Set PTR := RIGHT[LOC]\n"
                + "   and SAVE := LOC.\n"
                + "2. Repeat while LEFT[PTR] != NULL:\n"
                + "   set SAVE := PTR\n"
                + "   and PTR := LEFT[PTR].\n"
                + "3. Set SUC := PTR and PARSUC := SAVE.\n"
                + "4. Call CASEA(INFO, LEFT, RIGHT, ROOT, SUC, PARSUC).\n"
                + "5. Replace LOC by SUC at PAR or ROOT.\n"
                + "6. Set LEFT[SUC] := LEFT[LOC] and RIGHT[SUC] := RIGHT[LOC].\n"
                + "7. Return."
        );
        bstDeletionCaseB.algorithmByStyle.put(
            "Functions",
            "Algorithm: CASEB_FUNC(INFO, LEFT, RIGHT, ROOT, LOC, PAR)\n"
                + "1. Find SUC and PARSUC using helper procedure.\n"
                + "2. Call CASEA_FUNC(INFO, LEFT, RIGHT, ROOT, SUC, PARSUC).\n"
                + "3. Replace LOC with SUC.\n"
                + "4. Return.\n\n"
                + "Procedure FIND_SUC(LOC)\n"
                + "1. Set PTR := RIGHT[LOC] and SAVE := LOC.\n"
                + "2. While LEFT[PTR] != NULL, set SAVE := PTR and PTR := LEFT[PTR].\n"
                + "3. Set SUC := PTR and PARSUC := SAVE.\n"
                + "4. Return."
        );
        bstDeletionCaseB.algorithmByStyle.put(
            "Recursion",
            "Algorithm: CASEB_REC(INFO, LEFT, RIGHT, ROOT, ITEM)\n"
                + "1. If ROOT = NULL, then Exit.\n"
                + "2. If ITEM < INFO[ROOT], then call CASEB_REC(INFO, LEFT, RIGHT, LEFT[ROOT], ITEM).\n"
                + "3. Else if ITEM > INFO[ROOT], then call CASEB_REC(INFO, LEFT, RIGHT, RIGHT[ROOT], ITEM).\n"
                + "4. Else find inorder successor SUC in RIGHT[ROOT].\n"
                + "5. Replace INFO[ROOT] := INFO[SUC].\n"
                + "6. Call CASEA on RIGHT subtree to delete SUC.\n"
                + "7. Exit."
        );
        CONTENT.put("bst_deletion_case_b", bstDeletionCaseB);

        TechniqueContent bstDeletionDel = createTechniqueContent(
            "bst_deletion_del",
            "BST Deletion (DEL Operation)",
            "The DEL operation first finds the node and then chooses CASE A or CASE B based on the number of children.",
            "It is like finding a person in an organization chart and then deciding the correct removal rule based on their role connections.",
            "O(log n)",
            "O(log n)",
            "O(n)",
            "O(h)",
            new int[] {40, 20, 60, 10, 30, 50, 70},
            20
        );
        bstDeletionDel.algorithmByStyle.put(
            "Iterative",
            "Algorithm: DEL(INFO, LEFT, RIGHT, ROOT, AVAIL, ITEM)\n"
                + "1. Call FIND(INFO, LEFT, RIGHT, ROOT, ITEM, LOC, PAR).\n"
                + "2. If LOC = NULL, then write ITEM not in tree, and Exit.\n"
                + "3. If RIGHT[LOC] != NULL and LEFT[LOC] != NULL,\n"
                + "   then call CASEB(INFO, LEFT, RIGHT, ROOT, LOC, PAR).\n"
                + "4. Else call CASEA(INFO, LEFT, RIGHT, ROOT, LOC, PAR).\n"
                + "5. Set LEFT[LOC] := AVAIL\n"
                + "   and AVAIL := LOC.\n"
                + "6. Exit."
        );
        bstDeletionDel.algorithmByStyle.put(
            "Functions",
            "Algorithm: DEL_FUNC(INFO, LEFT, RIGHT, ROOT, ITEM)\n"
                + "1. Call FIND_FUNC(INFO, LEFT, RIGHT, ROOT, ITEM, LOC, PAR).\n"
                + "2. If LOC = NULL, then return.\n"
                + "3. If LEFT[LOC] != NULL and RIGHT[LOC] != NULL, then call CASEB_FUNC(INFO, LEFT, RIGHT, ROOT, LOC, PAR).\n"
                + "4. Else call CASEA_FUNC(INFO, LEFT, RIGHT, ROOT, LOC, PAR).\n"
                + "5. Return."
        );
        bstDeletionDel.algorithmByStyle.put(
            "Recursion",
            "Algorithm: DEL_REC(INFO, LEFT, RIGHT, ROOT, ITEM)\n"
                + "1. If ROOT = NULL, then Exit.\n"
                + "2. If ITEM < INFO[ROOT], then call DEL_REC(INFO, LEFT, RIGHT, LEFT[ROOT], ITEM).\n"
                + "3. Else if ITEM > INFO[ROOT], then call DEL_REC(INFO, LEFT, RIGHT, RIGHT[ROOT], ITEM).\n"
                + "4. Else if LEFT[ROOT] != NULL and RIGHT[ROOT] != NULL,\n"
                + "   then find inorder successor SUC,\n"
                + "   copy INFO[SUC],\n"
                + "   and call DEL_REC on the right subtree.\n"
                + "5. Else if LEFT[ROOT] != NULL, then set ROOT := LEFT[ROOT].\n"
                + "6. Else set ROOT := RIGHT[ROOT].\n"
                + "7. Exit."
        );
        CONTENT.put("bst_deletion_del", bstDeletionDel);

        TechniqueContent maxHeapInsertion = createTechniqueContent(
            "max_heap_insertion",
            "Insertion in a Max Heap (INSHEAP)",
            "Max-heap insertion adds the item at the end and bubbles it upward until heap order is restored.",
            "It is like placing a new champion into a ranking ladder and moving them up until they reach the correct position.",
            "O(1)",
            "O(log n)",
            "O(log n)",
            "O(1)",
            new int[] {90, 70, 60, 50, 65, 55},
            80
        );
        maxHeapInsertion.algorithmByStyle.put(
            "Iterative",
            "Procedure INSHEAP(TREE, N, ITEM)\n"
                + "1. Set N := N + 1\n"
                + "   and PTR := N.\n"
                + "2. Repeat while PTR > 1.\n"
                + "3. Set PAR := PTR // 2.\n"
                + "4. If ITEM <= TREE[PAR],\n"
                + "   then set TREE[PTR] := ITEM\n"
                + "   and Return.\n"
                + "5. Set TREE[PTR] := TREE[PAR].\n"
                + "6. Set PTR := PAR.\n"
                + "7. Set TREE[PTR] := ITEM.\n"
                + "8. Return."
        );
        maxHeapInsertion.algorithmByStyle.put(
            "Functions",
            "Algorithm: INSHEAP_FUNC(TREE, N, ITEM)\n"
                + "1. Call INSERT_HEAP(TREE, N, ITEM).\n"
                + "2. Return.\n\n"
                + "Procedure INSERT_HEAP(TREE, N, ITEM)\n"
                + "1. Set N := N + 1\n"
                + "   and PTR := N.\n"
                + "2. While PTR > 1,\n"
                + "   set PAR := PTR // 2\n"
                + "   and compare ITEM with TREE[PAR].\n"
                + "3. If ITEM <= TREE[PAR],\n"
                + "   then place ITEM and Return.\n"
                + "4. Move parent down and PTR up.\n"
                + "5. Set TREE[PTR] := ITEM and Return."
        );
        maxHeapInsertion.algorithmByStyle.put(
            "Recursion",
            "Algorithm: INSHEAP_REC(TREE, PTR, ITEM)\n"
                + "1. If PTR = 1, then set TREE[PTR] := ITEM and Return.\n"
                + "2. Set PAR := PTR // 2.\n"
                + "3. If ITEM <= TREE[PAR], then set TREE[PTR] := ITEM and Return.\n"
                + "4. Set TREE[PTR] := TREE[PAR].\n"
                + "5. Call INSHEAP_REC(TREE, PAR, ITEM).\n"
                + "6. Return."
        );
        CONTENT.put("max_heap_insertion", maxHeapInsertion);

        TechniqueContent maxHeapDeletion = createTechniqueContent(
            "max_heap_deletion",
            "Deletion from Max Heap (DELHEAP)",
            "Max-heap deletion removes the root and pushes the last element downward until heap order is restored.",
            "It is like removing the top-ranked player and sliding the last player down to the correct rank.",
            "O(1)",
            "O(log n)",
            "O(log n)",
            "O(1)",
            new int[] {90, 70, 60, 50, 65, 55, 40},
            0
        );
        maxHeapDeletion.algorithmByStyle.put(
            "Iterative",
            "Procedure DELHEAP(TREE, N, ITEM)\n"
                + "1. Set ITEM := TREE[1].\n"
                + "2. Set LAST := TREE[N]\n"
                + "   and N := N - 1.\n"
                + "3. Set PTR := 1.\n"
                + "4. Repeat while RIGHT <= N.\n"
                + "5. If LAST >= TREE[LEFT] and LAST >= TREE[RIGHT],\n"
                + "   then set TREE[PTR] := LAST\n"
                + "   and Return.\n"
                + "6. Move the larger child up and set PTR to that child.\n"
                + "7. Update LEFT and RIGHT.\n"
                + "8. Handle the case when only LEFT child exists.\n"
                + "9. Set TREE[PTR] := LAST.\n"
                + "10. Return."
        );
        maxHeapDeletion.algorithmByStyle.put(
            "Functions",
            "Algorithm: DELHEAP_FUNC(TREE, N, ITEM)\n"
                + "1. Call DELETE_HEAP(TREE, N, ITEM).\n"
                + "2. Return.\n\n"
                + "Procedure DELETE_HEAP(TREE, N, ITEM)\n"
                + "1. Set ITEM := TREE[1],\n"
                + "   LAST := TREE[N],\n"
                + "   and N := N - 1.\n"
                + "2. Set PTR := 1.\n"
                + "3. While 2 * PTR <= N, choose the larger child.\n"
                + "4. If LAST >= TREE[CHILD], then break.\n"
                + "5. Move TREE[CHILD] up and continue downward.\n"
                + "6. Set TREE[PTR] := LAST.\n"
                + "7. Return."
        );
        maxHeapDeletion.algorithmByStyle.put(
            "Recursion",
            "Algorithm: DELHEAP_REC(TREE, PTR, N, LAST)\n"
                + "1. Set LEFT := 2 * PTR and RIGHT := LEFT + 1.\n"
                + "2. If LEFT > N, then set TREE[PTR] := LAST and Return.\n"
                + "3. Choose CHILD as the larger of LEFT and RIGHT.\n"
                + "4. If LAST >= TREE[CHILD], then set TREE[PTR] := LAST and Return.\n"
                + "5. Set TREE[PTR] := TREE[CHILD].\n"
                + "6. Call DELHEAP_REC(TREE, CHILD, N, LAST).\n"
                + "7. Return."
        );
        CONTENT.put("max_heap_deletion", maxHeapDeletion);

        TechniqueContent heapSort = createTechniqueContent(
            "heapsort",
            "HEAPSORT",
            "Heapsort first builds a max heap and then repeatedly deletes the maximum element to produce sorted order.",
            "It is like repeatedly taking the highest-priority item from a ranked pile and placing it into the final sorted positions.",
            "O(n log n)",
            "O(n log n)",
            "O(n log n)",
            "O(1)",
            new int[] {45, 20, 14, 12, 31, 7, 11},
            0
        );
        heapSort.algorithmByStyle.put(
            "Iterative",
            "Algorithm HEAPSORT(A, N)\n"
                + "1. Repeat for J = 2 to N:\n"
                + "   call INSHEAP(A, J - 1, A[J]).\n"
                + "2. Repeat while N > 1:\n"
                + "   a) Call DELHEAP(A, N, ITEM).\n"
                + "   b) Set A[N + 1] := ITEM.\n"
                + "3. Exit."
        );
        heapSort.algorithmByStyle.put(
            "Functions",
            "Algorithm: HEAPSORT_FUNC(A, N)\n"
                + "1. Call BUILD_HEAP(A, N).\n"
                + "2. Call SORT_HEAP(A, N).\n"
                + "3. Exit.\n\n"
                + "Procedure BUILD_HEAP(A, N)\n"
                + "1. For J = 2 to N, call INSHEAP(A, J - 1, A[J]).\n"
                + "2. Return.\n\n"
                + "Procedure SORT_HEAP(A, N)\n"
                + "1. While N > 1, call DELHEAP(A, N, ITEM) and place ITEM at A[N + 1].\n"
                + "   [Each deleted maximum is stored at the end.]\n"
                + "2. Return."
        );
        heapSort.algorithmByStyle.put(
            "Recursion",
            "Algorithm: HEAPSORT_REC(A, N)\n"
                + "1. If N <= 1, then Exit.\n"
                + "2. Call BUILD_HEAP(A, N).\n"
                + "3. Call DELETE_MAX_REC(A, N).\n"
                + "4. Exit.\n\n"
                + "Procedure DELETE_MAX_REC(A, N)\n"
                + "1. If N <= 1, then return.\n"
                + "2. Call DELHEAP(A, N, ITEM).\n"
                + "3. Set A[N + 1] := ITEM.\n"
                + "4. Call DELETE_MAX_REC(A, N - 1).\n"
                + "5. Return."
        );
        CONTENT.put("heapsort", heapSort);

        CONTENT.put("tree_traversal", createTechniqueContent(
            "tree_traversal",
            "Tree Traversal",
            "Tree traversal visits each node in a chosen order such as inorder, preorder, or postorder.",
            "Reading family relationships from top to bottom is a simple tree-like example.",
            "O(n)",
            "O(n)",
            "O(n)",
            "O(h)",
            new int[] {40, 20, 60, 10, 30, 50, 70},
            0
        ));
        TechniqueContent graphTraversal = createTechniqueContent(
            "graph_traversal",
            "Graph Traversal",
            "Graph traversal explores vertices and edges systematically using strategies such as BFS or DFS.",
            "Finding all reachable stations in a transport map is a graph traversal problem.",
            "O(V + E)",
            "O(V + E)",
            "O(V + E)",
            "O(V)",
            new int[] {0, 1, 2, 3, 4},
            0
        );
        graphTraversal.algorithmByStyle.put(
            "Iterative",
            "Algorithm: Graph Traversal (BFS and DFS)\n"
                + "1. Choose a starting node and mark all nodes as unvisited.\n"
                + "2. For BFS mode, insert the start node into a queue.\n"
                + "3. Repeatedly remove the front node,\n"
                + "   visit it,\n"
                + "   and enqueue each unvisited neighbor.\n"
                + "4. For DFS mode, push the start node onto a stack.\n"
                + "5. Repeatedly pop the top node,\n"
                + "   visit it,\n"
                + "   and push each unvisited neighbor.\n"
                + "6. Continue until the queue or stack becomes empty.\n"
                + "7. Exit after every reachable node has been visited."
        );
        graphTraversal.algorithmByStyle.put(
            "Functions",
            "Algorithm: GRAPH_TRAVERSAL_FUNC(GRAPH, START, MODE)\n"
                + "1. INITIALIZE_VISITED(VISITED).\n"
                + "2. If MODE = BFS, call ENQUEUE_START(QUEUE, START, VISITED).\n"
                + "3. While QUEUE is not empty, DEQUEUE a node, process it, and call ADD_BFS_NEIGHBORS.\n"
                + "4. If MODE = DFS, call PUSH_START(STACK, START, VISITED).\n"
                + "5. While STACK is not empty, POP a node, process it, and call ADD_DFS_NEIGHBORS.\n"
                + "6. Return after the selected traversal structure becomes empty."
        );
        graphTraversal.algorithmByStyle.put(
            "Recursion",
            "Algorithm: GRAPH_TRAVERSAL_REC(GRAPH, START, MODE)\n"
                + "1. If MODE = DFS, call DFS_REC(GRAPH, START, VISITED).\n"
                + "2. In DFS_REC, mark the current node visited and process it.\n"
                + "3. Recursively continue to each unvisited neighbor.\n"
                + "4. If MODE = BFS,\n"
                + "   simulate the frontier recursively\n"
                + "   by removing the next queued node,\n"
                + "   processing it,\n"
                + "   and recursing on the remaining queue.\n"
                + "5. Return after every reachable node has been visited."
        );
        CONTENT.put("graph_traversal", graphTraversal);
        TechniqueContent warshallAlgorithm = createTechniqueContent(
            "warshall_algorithm",
            "Warshall's Algorithm",
            "Warshall's algorithm builds the transitive closure of a directed graph using its adjacency matrix.",
            "It is like marking every pair of cities that can reach each other, even through intermediate cities.",
            "O(n^3)",
            "O(n^3)",
            "O(n^3)",
            "O(n^2)",
            new int[] {0, 1, 2, 3, 4},
            0
        );
        warshallAlgorithm.algorithmByStyle.put(
            "Iterative",
            "Algorithm: (Warshall's Algorithm)\n"
                + "1. Repeat for I, J = 1, 2, ... , M\n"
                + "   and initialize path matrix P\n"
                + "   from adjacency matrix A.\n"
                + "2. Repeat Steps 3 and 4 for K = 1, 2, ... , M.\n"
                + "3. Repeat Step 4 for I = 1, 2, ... , M.\n"
                + "4. Repeat for J = 1, 2, ... , M\n"
                + "   and set P[I, J] := P[I, J]\n"
                + "   OR (P[I, K] AND P[K, J]).\n"
                + "5. Exit."
        );
        warshallAlgorithm.algorithmByStyle.put(
            "Functions",
            "Algorithm: WARSHALL_FUNC(A, M)\n"
                + "1. Call INIT_MATRIX(A, P, M).\n"
                + "2. Call UPDATE_MATRIX(P, M).\n"
                + "3. Return.\n\n"
                + "Procedure INIT_MATRIX(A, P, M)\n"
                + "1. For I = 1 to M and J = 1 to M, copy 0 or 1 into P based on A[I, J].\n"
                + "2. Return.\n\n"
                + "Procedure UPDATE_MATRIX(P, M)\n"
                + "1. For K = 1 to M, I = 1 to M, and J = 1 to M, update P[I, J] using intermediate node K.\n"
                + "2. Return."
        );
        warshallAlgorithm.algorithmByStyle.put(
            "Recursion",
            "Algorithm: WARSHALL_REC(P, K, M)\n"
                + "1. If K > M, then Exit.\n"
                + "2. For I = 1 to M and J = 1 to M, set P[I, J] := P[I, J] OR (P[I, K] AND P[K, J]).\n"
                + "3. Call WARSHALL_REC(P, K + 1, M).\n"
                + "4. Return."
        );
        CONTENT.put("warshall_algorithm", warshallAlgorithm);

        TechniqueContent floydAlgorithm = createTechniqueContent(
            "floyd_algorithm",
            "Floyd's Algorithm",
            "Floyd's algorithm finds the shortest path length between every pair of nodes in a weighted graph.",
            "It is like comparing all indirect routes between cities to keep only the shortest distance.",
            "O(n^3)",
            "O(n^3)",
            "O(n^3)",
            "O(n^2)",
            new int[] {0, 1, 2, 3, 4},
            0
        );
        floydAlgorithm.algorithmByStyle.put(
            "Iterative",
            "Algorithm: (Shortest-Path Algorithm)\n"
                + "1. Repeat for I, J = 1, 2, ... , M\n"
                + "   and initialize Q from weight matrix W,\n"
                + "   using INFINITY for missing edges.\n"
                + "2. Repeat Steps 3 and 4 for K = 1, 2, ... , M.\n"
                + "3. Repeat Step 4 for I = 1, 2, ... , M.\n"
                + "4. Repeat for J = 1, 2, ... , M\n"
                + "   and set Q[I, J] := MIN(Q[I, J],\n"
                + "   Q[I, K] + Q[K, J]).\n"
                + "5. Exit."
        );
        floydAlgorithm.algorithmByStyle.put(
            "Functions",
            "Algorithm: FLOYD_FUNC(W, M)\n"
                + "1. Call INIT_MATRIX(W, Q, M).\n"
                + "2. Call UPDATE_MATRIX(Q, M).\n"
                + "3. Return.\n\n"
                + "Procedure INIT_MATRIX(W, Q, M)\n"
                + "1. For I = 1 to M and J = 1 to M, put INFINITY or W[I, J] into Q.\n"
                + "2. Return.\n\n"
                + "Procedure UPDATE_MATRIX(Q, M)\n"
                + "1. For K = 1 to M, I = 1 to M, and J = 1 to M, replace Q[I, J] by the smaller of direct and indirect path lengths.\n"
                + "2. Return."
        );
        floydAlgorithm.algorithmByStyle.put(
            "Recursion",
            "Algorithm: FLOYD_REC(Q, K, M)\n"
                + "1. If K > M, then Exit.\n"
                + "2. For I = 1 to M and J = 1 to M, set Q[I, J] := MIN(Q[I, J], Q[I, K] + Q[K, J]).\n"
                + "3. Call FLOYD_REC(Q, K + 1, M).\n"
                + "4. Return."
        );
        CONTENT.put("floyd_algorithm", floydAlgorithm);

        TechniqueContent findEdgeGraph = createTechniqueContent(
            "find_edge_graph",
            "FINDEDGE",
            "FINDEDGE locates the adjacency-list entry of an edge (A, B) if both endpoint nodes exist.",
            "It is like checking whether a road from city A to city B is already stored in a route map.",
            "O(V + E)",
            "O(V + E)",
            "O(V + E)",
            "O(1)",
            new int[] {0, 1, 2, 3, 4},
            0
        );
        findEdgeGraph.algorithmByStyle.put(
            "Iterative",
            "Procedure: FINDEDGE(NODE, NEXT, ADJ, START, DEST, LINK, A, B, LOC)\n"
                + "1. Call FIND to locate node A and node B.\n"
                + "2. If either location is NULL, then set LOC := NULL.\n"
                + "3. Otherwise call FIND on the destination list\n"
                + "   beginning at ADJ[LOCA]\n"
                + "   to search for LOCB.\n"
                + "4. Return."
        );
        findEdgeGraph.algorithmByStyle.put(
            "Functions",
            "Algorithm: FINDEDGE_FUNC(NODE, NEXT, ADJ, START, DEST, LINK, A, B)\n"
                + "1. LOCA := FIND_NODE(NODE, NEXT, START, A).\n"
                + "2. LOCB := FIND_NODE(NODE, NEXT, START, B).\n"
                + "3. If LOCA = NULL or LOCB = NULL, then return NULL.\n"
                + "4. Return FIND_EDGE(DEST, LINK, ADJ[LOCA], LOCB).\n\n"
                + "Procedure FIND_NODE and Procedure FIND_EDGE each scan their linked lists and return the first matching location."
        );
        findEdgeGraph.algorithmByStyle.put(
            "Recursion",
            "Algorithm: FINDEDGE_REC(NODE, NEXT, ADJ, START, DEST, LINK, A, B)\n"
                + "1. LOCA := FIND_NODE_REC(START, A).\n"
                + "2. LOCB := FIND_NODE_REC(START, B).\n"
                + "3. If LOCA = NULL or LOCB = NULL, then return NULL.\n"
                + "4. Return FIND_EDGE_REC(ADJ[LOCA], LOCB)."
        );
        CONTENT.put("find_edge_graph", findEdgeGraph);

        TechniqueContent insertNodeGraph = createTechniqueContent(
            "insert_node_graph",
            "INSNODE",
            "INSNODE inserts a new graph node into the node list and gives it an empty adjacency list.",
            "It is like adding a new station to a map before any roads are connected to it.",
            "O(1)",
            "O(1)",
            "O(V)",
            "O(1)",
            new int[] {0, 1, 2, 3, 4},
            0
        );
        insertNodeGraph.algorithmByStyle.put(
            "Iterative",
            "Procedure: INSNODE(NODE, NEXT, ADJ, START, AVAILN, N, FLAG)\n"
                + "1. If AVAILN = NULL, then set FLAG := FALSE and Return.\n"
                + "2. Remove one free node from AVAILN and place N in NODE[NEW].\n"
                + "3. Set ADJ[NEW] := NULL.\n"
                + "4. Insert NEW into the node list and set FLAG := TRUE.\n"
                + "5. Return."
        );
        insertNodeGraph.algorithmByStyle.put(
            "Functions",
            "Algorithm: INSNODE_FUNC(NODE, NEXT, ADJ, START, N)\n"
                + "1. If node N already exists, return FALSE.\n"
                + "2. Find a free location.\n"
                + "3. Store N, initialize its adjacency list to NULL, and link it into the node list.\n"
                + "4. Return TRUE."
        );
        insertNodeGraph.algorithmByStyle.put(
            "Recursion",
            "Algorithm: INSNODE_REC(NODE, NEXT, ADJ, PTR, N)\n"
                + "1. If PTR = NULL, create a new node for N and return it.\n"
                + "2. If NODE[PTR] = N, return PTR.\n"
                + "3. Recursively continue on NEXT[PTR] until insertion position is found.\n"
                + "4. Return."
        );
        CONTENT.put("insert_node_graph", insertNodeGraph);

        TechniqueContent insertEdgeGraph = createTechniqueContent(
            "insert_edge_graph",
            "INSEDGE",
            "INSEDGE inserts an edge from node A to node B in adjacency-list representation.",
            "It is like recording a direct road from one city to another city in a map.",
            "O(V + E)",
            "O(V + E)",
            "O(V + E)",
            "O(1)",
            new int[] {0, 1, 2, 3, 4},
            0
        );
        insertEdgeGraph.algorithmByStyle.put(
            "Iterative",
            "Procedure: INSEDGE(NODE, NEXT, ADJ, START, DEST, LINK, AVAILE, A, B, FLAG)\n"
                + "1. Locate node A and node B.\n"
                + "2. If either node is missing\n"
                + "   or no edge cell is available,\n"
                + "   set FLAG := FALSE and Return.\n"
                + "3. Remove one edge cell from AVAILE.\n"
                + "4. Store destination LOCB\n"
                + "   and insert that cell at the front\n"
                + "   of A's adjacency list.\n"
                + "5. Set FLAG := TRUE and Return."
        );
        insertEdgeGraph.algorithmByStyle.put(
            "Functions",
            "Algorithm: INSEDGE_FUNC(...)\n"
                + "1. Find the source node and destination node.\n"
                + "2. If either is absent, return FALSE.\n"
                + "3. Create a new edge entry, point it to the destination node location, and attach it to the source adjacency list.\n"
                + "4. Return TRUE."
        );
        insertEdgeGraph.algorithmByStyle.put(
            "Recursion",
            "Algorithm: INSEDGE_REC(...)\n"
                + "1. Recursively locate node A and node B.\n"
                + "2. If either search fails, return FALSE.\n"
                + "3. Recursively place the new edge entry at the correct linked-list position.\n"
                + "4. Return TRUE."
        );
        CONTENT.put("insert_edge_graph", insertEdgeGraph);

        TechniqueContent deleteEdgeGraph = createTechniqueContent(
            "delete_edge_graph",
            "Delete Edge in Graph",
            "This operation deletes an edge from adjacency-list representation if the source and destination exist.",
            "It is like removing one directed road from a stored city map.",
            "O(V + E)",
            "O(V + E)",
            "O(V + E)",
            "O(1)",
            new int[] {0, 1, 2, 3, 4},
            0
        );
        deleteEdgeGraph.algorithmByStyle.put(
            "Iterative",
            "Procedure: DELETE EDGE(A, B)\n"
                + "1. Find the node locations of A and B.\n"
                + "2. If either location is NULL, return FALSE.\n"
                + "3. Traverse A's adjacency list until the destination B is found.\n"
                + "4. Relink the list to bypass that edge entry.\n"
                + "5. Return TRUE if deleted, otherwise FALSE."
        );
        deleteEdgeGraph.algorithmByStyle.put(
            "Functions",
            "Algorithm: DELETE_EDGE_MAIN(...)\n"
                + "1. Call FIND_NODE for A and B.\n"
                + "2. If either is absent, return FALSE.\n"
                + "3. Call DELETE_EDGE on the adjacency list beginning at ADJ[LOCA].\n"
                + "4. Return the result."
        );
        deleteEdgeGraph.algorithmByStyle.put(
            "Recursion",
            "Algorithm: DELETE_EDGE_REC(...)\n"
                + "1. Recursively locate A and B.\n"
                + "2. If either is absent, return FALSE.\n"
                + "3. Recursively scan the edge list until the target destination is found.\n"
                + "4. Return TRUE if deletion happens, otherwise FALSE."
        );
        CONTENT.put("delete_edge_graph", deleteEdgeGraph);

        TechniqueContent deleteVertexGraph = createTechniqueContent(
            "delete_vertex_graph",
            "Delete Node in Graph",
            "Deleting a node in adjacency-list representation removes the node and the edges connected to it.",
            "It is like removing a station from a transport map and also removing every route that touches it.",
            "O(V + E)",
            "O(V + E)",
            "O(V + E)",
            "O(1)",
            new int[] {0, 1, 2, 3, 4},
            0
        );
        deleteVertexGraph.algorithmByStyle.put(
            "Iterative",
            "Procedure: DELNODE(...)\n"
                + "1. Locate node N.\n"
                + "2. If it is missing, set FLAG := FALSE and Return.\n"
                + "3. Delete all edges ending at N by scanning every adjacency list.\n"
                + "4. Move all outgoing edges of N to the available edge list.\n"
                + "5. Delete the node itself from the node list.\n"
                + "6. Return."
        );
        deleteVertexGraph.algorithmByStyle.put(
            "Functions",
            "Algorithm: DELNODE_FUNC(...)\n"
                + "1. LOC := FIND_NODE(...).\n"
                + "2. If LOC = NULL, return FALSE.\n"
                + "3. Delete incoming edges to LOC from every node.\n"
                + "4. Move the successor list of LOC to the available list if it exists.\n"
                + "5. Delete the node from the node list and return TRUE."
        );
        deleteVertexGraph.algorithmByStyle.put(
            "Recursion",
            "Algorithm: DELNODE_REC(...)\n"
                + "1. Recursively find node N.\n"
                + "2. If it is absent, return FALSE.\n"
                + "3. Recursively delete all edges pointing to that node.\n"
                + "4. Recursively handle the outgoing successor list and then delete the node.\n"
                + "5. Return TRUE."
        );
        CONTENT.put("delete_vertex_graph", deleteVertexGraph);

        TechniqueContent breadthFirstSearch = createTechniqueContent(
            "breadth_first_search",
            "Breadth-First Search (BFS)",
            "Breadth-first search visits a graph level by level using a queue.",
            "It is like exploring nearby stops first before moving to farther stops in a transport network.",
            "O(V + E)",
            "O(V + E)",
            "O(V + E)",
            "O(V)",
            new int[] {0, 1, 2, 3, 4},
            0
        );
        breadthFirstSearch.algorithmByStyle.put(
            "Iterative",
            "Algorithm A: Breadth-First Search\n"
                + "1. Initialize all nodes so STATUS := 1.\n"
                + "   [STATUS = 1 means READY.]\n"
                + "2. Put the starting node A in QUEUE\n"
                + "   and set STATUS[A] := 2.\n"
                + "   [STATUS = 2 means WAITING.]\n"
                + "3. Repeat until QUEUE is empty.\n"
                + "4. Remove the front node N,\n"
                + "   process it,\n"
                + "   and set STATUS[N] := 3.\n"
                + "   [STATUS = 3 means PROCESSED.]\n"
                + "5. Add all neighbors of N with STATUS = 1\n"
                + "   to the rear of QUEUE,\n"
                + "   and set their status to 2.\n"
                + "6. Exit."
        );
        breadthFirstSearch.algorithmByStyle.put(
            "Functions",
            "Algorithm: BFS_FUNC(GRAPH, NODES, START)\n"
                + "1. INITIALIZE_STATUS(STATUS, NODES).\n"
                + "   [Set each STATUS := 1.]\n"
                + "2. ENQUEUE(QUEUE, START) and set STATUS[START] := 2.\n"
                + "3. While QUEUE is not empty,\n"
                + "   DEQUEUE a node,\n"
                + "   process it,\n"
                + "   set STATUS := 3,\n"
                + "   and call ADD_NEIGHBORS.\n"
                + "4. Return."
        );
        breadthFirstSearch.algorithmByStyle.put(
            "Recursion",
            "Algorithm: BFS_REC(GRAPH, QUEUE, STATUS)\n"
                + "1. If QUEUE is empty, return.\n"
                + "2. NODE := DEQUEUE(QUEUE).\n"
                + "3. Process NODE and set STATUS[NODE] := 3.\n"
                + "4. Enqueue all neighbors with STATUS = 1\n"
                + "   and set their status to 2.\n"
                + "5. Call BFS_REC(GRAPH, QUEUE, STATUS)."
        );
        CONTENT.put("breadth_first_search", breadthFirstSearch);

        TechniqueContent depthFirstSearch = createTechniqueContent(
            "depth_first_search",
            "Depth-First Search (DFS)",
            "Depth-first search goes deep along one path before backtracking to another path.",
            "It is like following one corridor of a maze fully before returning to try the next corridor.",
            "O(V + E)",
            "O(V + E)",
            "O(V + E)",
            "O(V)",
            new int[] {0, 1, 2, 3, 4},
            0
        );
        depthFirstSearch.algorithmByStyle.put(
            "Iterative",
            "Algorithm B: Depth-First Search\n"
                + "1. Initialize all nodes so STATUS := 1.\n"
                + "   [STATUS = 1 means READY.]\n"
                + "2. Push the starting node A onto STACK\n"
                + "   and set STATUS[A] := 2.\n"
                + "   [STATUS = 2 means WAITING.]\n"
                + "3. Repeat until STACK is empty.\n"
                + "4. Pop the top node N,\n"
                + "   process it,\n"
                + "   and set STATUS[N] := 3.\n"
                + "   [STATUS = 3 means PROCESSED.]\n"
                + "5. Push all neighbors of N with STATUS = 1\n"
                + "   and set their status to 2.\n"
                + "6. Exit."
        );
        depthFirstSearch.algorithmByStyle.put(
            "Functions",
            "Algorithm: DFS_FUNC(GRAPH, START)\n"
                + "1. INITIALIZE_STATUS(STATUS).\n"
                + "   [Set each STATUS := 1.]\n"
                + "2. PUSH(STACK, START) and set STATUS[START] := 2.\n"
                + "3. While STACK is not empty,\n"
                + "   POP a node,\n"
                + "   process it,\n"
                + "   set STATUS := 3,\n"
                + "   and call ADD_NEIGHBORS.\n"
                + "4. Return."
        );
        depthFirstSearch.algorithmByStyle.put(
            "Recursion",
            "Algorithm: DFS_REC(GRAPH, NODE, STATUS)\n"
                + "1. If STATUS[NODE] != 1, return.\n"
                + "2. Set STATUS[NODE] := 2.\n"
                + "3. Process NODE and set STATUS[NODE] := 3.\n"
                + "4. For each neighbor v with STATUS[v] = 1,\n"
                + "   call DFS_REC(GRAPH, v, STATUS).\n"
                + "5. Return."
        );
        CONTENT.put("depth_first_search", depthFirstSearch);

        TechniqueContent topologicalSort = createTechniqueContent(
            "topological_sort",
            "Topological Sort",
            "Topological sort orders the vertices of a DAG so every directed edge goes from earlier to later in the order.",
            "It is like arranging course prerequisites so each subject appears before the subjects that depend on it.",
            "O(V + E)",
            "O(V + E)",
            "O(V + E)",
            "O(V)",
            new int[] {0, 1, 2, 3, 4},
            0
        );
        topologicalSort.algorithmByStyle.put(
            "Iterative",
            "Algorithm C: Topological Sort\n"
                + "1. Find the indegree of each node.\n"
                + "2. Put all zero-indegree nodes in a queue.\n"
                + "3. Repeat until the queue is empty.\n"
                + "4. Remove the front node N of the queue.\n"
                + "5. For each neighbor M of N,\n"
                + "   decrease INDEG(M),\n"
                + "   and if it becomes 0,\n"
                + "   enqueue M.\n"
                + "6. Exit."
        );
        topologicalSort.algorithmByStyle.put(
            "Functions",
            "Algorithm: TOPO_FUNC(GRAPH, N)\n"
                + "1. COMPUTE_INDEGREE(GRAPH, INDEG, N).\n"
                + "2. ENQUEUE_ZERO_INDEG(QUEUE, INDEG, N).\n"
                + "3. While QUEUE is not empty,\n"
                + "   DEQUEUE a node,\n"
                + "   print it,\n"
                + "   and UPDATE_NEIGHBORS.\n"
                + "4. Return."
        );
        topologicalSort.algorithmByStyle.put(
            "Recursion",
            "Algorithm: TOPO_DFS_REC(GRAPH, NODE, VISITED, STACK)\n"
                + "1. Mark NODE as visited.\n"
                + "2. For each unvisited neighbor v,\n"
                + "   call TOPO_DFS_REC(GRAPH, v, VISITED, STACK).\n"
                + "3. Push NODE to STACK during backtracking.\n"
                + "4. Return."
        );
        CONTENT.put("topological_sort", topologicalSort);

        registerSupplementalTopic("pattern_matching", "Pattern Matching", "Pattern matching searches for a smaller pattern inside a larger text by comparing aligned characters.", "It is like sliding a name strip along a notice board until every character lines up.", "O(n)", "O(nm)", "O(nm)", "O(1)", new int[] {2, 1, 2, 3, 2, 3, 4}, 0, "Algorithm: MATCH(TEXT, N, PATTERN, M)\n1. Set I := 1.\n2. Repeat while I <= N - M + 1.\n3. Compare PATTERN with TEXT\n   beginning at position I.\n4. If all pattern symbols match,\n   report I and return.\n5. Otherwise set I := I + 1.\n6. Return failure.", "Algorithm: MATCH_FUNC(TEXT, PATTERN)\n1. Define COMPARE_AT(TEXT, PATTERN, I).\n2. For each starting position I,\n   call COMPARE_AT.\n3. If COMPARE_AT succeeds,\n   return I.\n4. If no position succeeds,\n   return -1.", "Algorithm: MATCH_REC(TEXT, PATTERN, I, J)\n1. If J reaches the pattern length,\n   return the start index.\n2. If I reaches the text end,\n   return failure.\n3. If current symbols match,\n   recurse with I + 1 and J + 1.\n4. Otherwise restart from the next text position.\n5. Return the first successful match.");
        registerSupplementalTopic("deque", "Deque", "A deque allows insertion and deletion at both the front and the rear.", "It is like a line where people can enter or leave from either side.", "O(1)", "O(1)", "O(1)", "O(n)", new int[] {12, 24, 36, 48}, 0, "Algorithm: DEQUE_INSERT_DELETE(DQ)\n1. Maintain FRONT and REAR pointers.\n2. Insert at front\n   by decreasing FRONT\n   and storing ITEM.\n3. Insert at rear\n   by increasing REAR\n   and storing ITEM.\n4. Delete at front\n   by removing DQ[FRONT]\n   and advancing FRONT.\n5. Delete at rear\n   by removing DQ[REAR]\n   and decreasing REAR.\n6. Return.", "Algorithm: DEQUE_FUNC(DQ, OP)\n1. Define INSERT_FRONT,\n   INSERT_REAR,\n   DELETE_FRONT,\n   and DELETE_REAR.\n2. Dispatch OP to the proper helper.\n3. Update FRONT and REAR correctly.\n4. Return the updated deque state.", "Algorithm: DEQUE_REC(DQ, OPS, INDEX)\n1. If INDEX reaches the number of operations,\n   return.\n2. Perform the current operation.\n3. Recurse for INDEX + 1.\n4. Return.");
        registerSupplementalTopic("traversal_using_stacks", "Traversal Algorithms Using Stacks", "Traversal using stacks replaces the system recursion stack with an explicit stack data structure.", "It is like keeping your own to-do pile of tree nodes instead of relying on memory automatically.", "O(n)", "O(n)", "O(n)", "O(n)", new int[] {50, 30, 70, 20, 40, 60, 80}, 0, "Algorithm: STACK_TRAVERSAL(ROOT)\n1. Push the root on the stack.\n2. Repeat while the stack is not empty.\n3. Pop the top node and process it.\n4. Push children in the order\n   required by the traversal.\n5. Continue until all nodes are processed.", "Algorithm: STACK_TRAVERSAL_FUNC(ROOT)\n1. Define PUSH_CHILDREN according to the chosen traversal.\n2. Pop, process,\n   and call PUSH_CHILDREN repeatedly.\n3. Return the visit order.", "Algorithm: STACK_TRAVERSAL_REC(STACK)\n1. If the explicit stack is empty,\n   return.\n2. Pop one node,\n   process it,\n   and push required children.\n3. Recurse on the updated stack.\n4. Return.");
        registerSupplementalTopic("huffman_algorithm", "Huffman's Algorithm", "Huffman's algorithm repeatedly combines the two least frequent symbols to build an optimal prefix code tree.", "It is like repeatedly tying together the two lightest bundles until one coding tree remains.", "O(n log n)", "O(n log n)", "O(n log n)", "O(n)", new int[] {5, 9, 12, 13, 16, 45}, 0, "Algorithm: HUFFMAN(FREQ)\n1. Insert each symbol into a min-priority queue.\n2. Repeat while more than one node remains.\n3. Remove the two nodes\n   with the smallest frequencies.\n4. Create a parent\n   with combined frequency\n   and attach both nodes.\n5. Insert the parent back into the queue.\n6. The final node is the Huffman tree root.", "Algorithm: HUFFMAN_FUNC(FREQ)\n1. BUILD_QUEUE from all frequencies.\n2. EXTRACT_MIN twice\n   and COMBINE them.\n3. INSERT the new parent into the queue.\n4. Repeat until one node remains.", "Algorithm: HUFFMAN_REC(QUEUE)\n1. If QUEUE has one node,\n   return it.\n2. Remove the two least frequent nodes.\n3. Combine them into a new parent.\n4. Insert the parent and recurse.\n5. Return the final root.");
        registerSupplementalTopic("spanning_tree", "Spanning Tree", "A spanning tree connects all vertices of a connected graph without creating cycles.", "It is like selecting roads so every city stays connected while avoiding unnecessary loops.", "O(V + E)", "O(V + E)", "O(V + E)", "O(V)", new int[] {0, 1, 2, 3, 4}, 0, "Algorithm: SPANNING_TREE(G)\n1. Choose a starting node and mark it visited.\n2. Repeatedly add one edge\n   that reaches an unvisited node.\n3. Mark that node visited\n   and keep the edge.\n4. Continue until all vertices are connected.\n5. Return the selected tree edges.", "Algorithm: SPANNING_TREE_FUNC(G)\n1. Maintain VISITED and TREEEDGE collections.\n2. FIND_NEXT_EDGE chooses one edge\n   from visited to unvisited.\n3. ADD_EDGE stores it\n   and marks the new node visited.\n4. Return TREEEDGE.", "Algorithm: SPANNING_TREE_REC(G, VISITED)\n1. If all vertices are visited,\n   return.\n2. Select one edge from visited to unvisited.\n3. Add it to the tree and recurse.\n4. Return.");
        registerSupplementalTopic("shortest_paths", "Shortest Paths", "Shortest path algorithms keep improving tentative distances until the cheapest route is known.", "It is like checking route alternatives on a map and always keeping the cheapest travel cost seen so far.", "O(V^2)", "O(V^2)", "O(V^3)", "O(V^2)", new int[] {0, 1, 2, 3, 4}, 0, "Algorithm: SHORTEST_PATHS(G)\n1. Initialize distance estimates.\n2. Repeatedly relax routes\n   through direct or intermediate vertices.\n3. If a cheaper route is found,\n   update the distance.\n4. Continue until no improvement remains.\n5. Return final path lengths.", "Algorithm: SHORTEST_PATHS_FUNC(G)\n1. INIT_DISTANCE creates the first table.\n2. RELAX updates costs\n   using one chosen rule.\n3. LOOP until all improvements are processed.\n4. Return the final distance data.", "Algorithm: SHORTEST_PATHS_REC(STATE)\n1. If no new intermediate choice remains,\n   return.\n2. Relax all affected routes.\n3. Recurse on the next state.\n4. Return.");
        registerSupplementalTopic("dijkstra_algorithm", "Dijkstra's Algorithm", "Dijkstra's algorithm grows shortest known distances from one source using the nearest unsettled vertex each time.", "It is like always choosing the closest confirmed city before extending routes to farther cities.", "O(V^2)", "O((V + E) log V)", "O((V + E) log V)", "O(V)", new int[] {0, 1, 2, 3, 4}, 0, "Algorithm: DIJKSTRA(G, SOURCE)\n1. Set distance of SOURCE to 0\n   and all others to infinity.\n2. Choose the unsettled vertex\n   with smallest distance.\n3. Relax all outgoing edges of that vertex.\n4. Mark it settled.\n5. Continue until all reachable vertices are settled.", "Algorithm: DIJKSTRA_FUNC(G, SOURCE)\n1. EXTRACT_MIN chooses the next unsettled vertex.\n2. RELAX_NEIGHBORS updates tentative distances.\n3. MARK_SETTLED fixes the chosen distance.\n4. Return the final distance array.", "Algorithm: DIJKSTRA_REC(G, STATE)\n1. If no unsettled reachable vertex remains,\n   return.\n2. Pick the current minimum-distance vertex.\n3. Relax its outgoing edges.\n4. Recurse on the updated state.\n5. Return the final distances.");
        registerSupplementalTopic("avl_tree", "AVL Tree", "An AVL tree keeps the height of left and right subtrees nearly balanced after insertions and deletions.", "It is like keeping a shelf balanced so one side never grows too heavy compared with the other.", "O(log n)", "O(log n)", "O(log n)", "O(n)", new int[] {30, 20, 40, 10, 25, 35, 50}, 0, "Algorithm: AVL_INSERT(T, ITEM)\n1. Insert ITEM as in a BST.\n2. Update heights while moving upward.\n3. Compute the balance factor\n   of each ancestor.\n4. If unbalanced,\n   apply the needed rotation.\n5. Return the rebalanced root.", "Algorithm: AVL_FUNC(T, ITEM)\n1. BST_INSERT places the node.\n2. UPDATE_HEIGHT refreshes ancestors.\n3. REBALANCE chooses LL, RR, LR,\n   or RL rotation.\n4. Return the new root.", "Algorithm: AVL_REC(T, ITEM)\n1. If T is null,\n   create and return a node.\n2. Recursively insert ITEM\n   into left or right subtree.\n3. Update height and balance factor.\n4. Apply any needed rotation\n   and return the root.");
        registerSupplementalTopic("trie", "Trie", "A trie stores strings by characters along root-to-leaf paths so shared prefixes reuse the same path.", "It is like storing words in a branching dictionary where common beginnings share the same corridor.", "O(m)", "O(m)", "O(m)", "O(alphabet * nodes)", new int[] {19, 18, 9, 5}, 0, "Algorithm: TRIE_INSERT(ROOT, WORD)\n1. Start from ROOT.\n2. For each character in WORD,\n   follow its child edge.\n3. If the child does not exist,\n   create it.\n4. Move to the child and continue.\n5. Mark the final node as end-of-word.", "Algorithm: TRIE_FUNC(ROOT, WORD)\n1. FIND_OR_CREATE_CHILD handles each next character.\n2. MOVE advances to that child.\n3. MARK_END records the whole word.\n4. Return the updated root.", "Algorithm: TRIE_REC(NODE, WORD, INDEX)\n1. If INDEX reaches the word length,\n   mark end-of-word and return.\n2. Create the next child if needed.\n3. Recurse on the child and INDEX + 1.\n4. Return.");
        registerSupplementalTopic("union_find", "Union-Find", "Union-Find tracks which elements belong to the same set using parent representatives.", "It is like grouping students by club and quickly checking whether two students belong to the same club.", "O(1)", "O(alpha(n))", "O(alpha(n))", "O(n)", new int[] {0, 1, 2, 3, 4, 5}, 0, "Algorithm: UNION_FIND\n1. MAKESET gives each element itself as parent.\n2. FIND follows parent links\n   to the representative.\n3. UNION joins two representatives.\n4. Path compression and rank\n   improve performance.", "Algorithm: UNION_FIND_FUNC\n1. FIND_SET returns the leader.\n2. LINK joins two leaders.\n3. UNION calls FIND_SET\n   and LINK together.\n4. Return the updated parent structure.", "Algorithm: UNION_FIND_REC(PARENT, X)\n1. If PARENT[X] = X,\n   return X.\n2. Recursively find the representative.\n3. Compress the path while returning.\n4. Return the representative.");
        registerSupplementalTopic("hash_collision_handling", "Hash Collision Handling", "Collision handling stores multiple items that map to the same hash address by chaining or probing.", "It is like multiple people reaching the same locker number and following a backup rule to store their items.", "O(1)", "O(1)", "O(n)", "O(n)", new int[] {14, 24, 34, 44, 54}, 0, "Algorithm: HASH_INSERT(KEY)\n1. Compute the hash address H(KEY).\n2. If the slot is empty,\n   store the item there.\n3. If a collision occurs,\n   follow chaining or probing.\n4. Store the item\n   in the resolved location.\n5. Return.", "Algorithm: HASH_FUNC(KEY)\n1. HASH maps the key to an index.\n2. RESOLVE_COLLISION finds the next place.\n3. INSERT stores the value.\n4. SEARCH follows the same collision path.", "Algorithm: HASH_REC(TABLE, INDEX)\n1. If the current slot is suitable,\n   use it and return.\n2. Otherwise recurse on the next candidate slot.\n3. Return the resolved location.");
        registerSupplementalTopic("recursion_basics", "Recursion Basics", "Recursion solves a problem by handling one small part now and calling the same logic on a smaller case.", "It is like climbing down stairs by saying: solve one step, then solve the rest in the same way.", "O(n)", "O(n)", "O(n)", "O(n)", new int[] {5}, 5, "Algorithm: RECURSION(N)\n1. Define a base case.\n2. Reduce the problem to a smaller similar case.\n3. Call the same procedure on that smaller case.\n4. Combine or return the result.", "Algorithm: RECURSION_FUNC(N)\n1. If N matches the base case, return directly.\n2. Otherwise call the same function on a smaller value.\n3. Use the returned value to build the answer.\n4. Return.", "Algorithm: RECURSION_REC(N)\n1. If N = 0 or N = 1, return the base value.\n2. Return the result of a recursive call on smaller N.\n3. Continue until the base case stops the calls.");
        registerSupplementalTopic("dynamic_programming_basics", "Dynamic Programming Basics", "Dynamic programming stores answers to repeated subproblems so the same work is not done again.", "It is like keeping solved homework steps written down so you can reuse them instantly later.", "O(n)", "O(n^2)", "O(n^2)", "O(n)", new int[] {1, 2, 3, 4, 5}, 5, "Algorithm: DP(PROBLEM)\n1. Define the state\n   of a smaller subproblem.\n2. Write a recurrence\n   between one state and smaller states.\n3. Store solved values in a table.\n4. Fill the table in dependency order.\n5. Return the final state value.", "Algorithm: DP_FUNC(PROBLEM)\n1. BASE initializes known answers.\n2. TRANSITION computes each new state\n   from stored older states.\n3. MEMO or TABLE keeps results.\n4. Return the target state.", "Algorithm: DP_REC(STATE)\n1. If STATE is already stored,\n   return it.\n2. If STATE is a base case,\n   return its base answer.\n3. Recursively solve smaller states.\n4. Store and return the computed answer.");
        applyPdfAlgorithmCorrections();
    }

    private static void registerExpressionStackTopics() {
        registerSupplementalTopic(
            "postfix_evaluation",
            "Evaluation of a Postfix Expression",
            "Postfix evaluation scans an expression from left to right and uses a stack to hold operands until an operator can be applied.",
            "It is like keeping numbers on a scratch pad until an arithmetic sign tells you which two values to combine.",
            "O(n)",
            "O(n)",
            "O(n)",
            "O(n)",
            new int[] {2, 3, 4},
            14,
            "Algorithm 6.5: This algorithm finds the VALUE of an arithmetic expression P written in postfix notation.\n\n"
                + "1. Add a right parenthesis \")\" at the end of P. [This acts as a sentinel.]\n\n"
                + "2. Scan P from left to right and repeat Steps 3 and 4 for each element of P until the sentinel \")\" is encountered.\n\n"
                + "3. If an operand is encountered, put it on STACK.\n\n"
                + "4. If an operator is encountered, then:\n"
                + "(a) Remove the two top elements of STACK, where A is the top element and B is the next-to-top element.\n"
                + "(b) Evaluate B operator A.\n"
                + "(c) Place the result of (b) back on STACK.\n"
                + "[End of If structure.]\n"
                + "[End of Step 2 loop.]\n\n"
                + "5. Set VALUE equal to the top element on STACK.\n\n"
                + "6. Exit.",
            "Algorithm: EVALUATE_POSTFIX(P)\n\n"
                + "1. Define helper functions IS_OPERAND, IS_OPERATOR, and APPLY.\n"
                + "2. Add \")\" at the end of P as a sentinel.\n"
                + "3. Scan each token until \")\" is found.\n"
                + "4. If the token is an operand, PUSH it on STACK.\n"
                + "5. If the token is an operator, POP A, POP B, compute APPLY(B, token, A), and PUSH the result.\n"
                + "6. Return the top element of STACK as VALUE.",
            "Algorithm: EVALUATE_POSTFIX_REC(P, INDEX, STACK)\n\n"
                + "1. If P[INDEX] is the sentinel \")\", return the top element of STACK.\n"
                + "2. If P[INDEX] is an operand, push it on STACK.\n"
                + "3. If P[INDEX] is an operator, pop A and B, evaluate B operator A, and push the result.\n"
                + "4. Recurse with INDEX + 1 and the updated STACK."
        );
        registerSupplementalTopic(
            "infix_to_postfix",
            "Infix to Postfix (POLISH)",
            "The POLISH algorithm converts an infix expression into postfix form by using a stack for operators and parentheses.",
            "It is like temporarily holding operation signs until the output order is safe and unambiguous.",
            "O(n)",
            "O(n)",
            "O(n)",
            "O(n)",
            new int[] {2, 3, 4},
            0,
            "Algorithm 6.6: POLISH(Q, P)\n\n"
                + "Suppose Q is an arithmetic expression written in infix notation. This algorithm finds the equivalent postfix expression P.\n\n"
                + "1. Push \"(\" onto STACK, and add \")\" to the end of Q.\n\n"
                + "2. Scan Q from left to right and repeat Steps 3 to 6 for each element of Q until the STACK is empty:\n\n"
                + "3. If an operand is encountered, add it to P.\n\n"
                + "4. If a left parenthesis is encountered, push it onto STACK.\n\n"
                + "5. If an operator is encountered, then:\n"
                + "(a) Repeatedly pop from STACK and add to P each operator on the top of STACK which has the same precedence as or higher precedence than the current operator.\n"
                + "(b) Add the current operator to STACK.\n\n"
                + "6. If a right parenthesis is encountered, then:\n"
                + "(a) Repeatedly pop from STACK and add to P each operator on the top of STACK until a left parenthesis is encountered.\n"
                + "(b) Remove the left parenthesis. [Do not add the left parenthesis to P.]\n"
                + "[End of If structure.]\n"
                + "[End of Step 2 loop.]\n\n"
                + "7. Exit.",
            "Algorithm: POLISH_FUNCTIONS(Q, P)\n\n"
                + "1. Define PRECEDENCE, IS_OPERAND, IS_OPERATOR, PUSH, and POP.\n"
                + "2. Push \"(\" and append \")\" to Q.\n"
                + "3. For each symbol, call the matching helper.\n"
                + "4. Operand helper appends to P.\n"
                + "5. Operator helper pops higher-or-equal precedence operators before pushing the current operator.\n"
                + "6. Right-parenthesis helper pops until \"(\" and removes it.\n"
                + "7. Return P.",
            "Algorithm: POLISH_REC(Q, INDEX, STACK, P)\n\n"
                + "1. If STACK is empty, return P.\n"
                + "2. Process Q[INDEX] using the same operand, operator, and parenthesis rules.\n"
                + "3. Recurse with INDEX + 1, updated STACK, and updated P.\n"
                + "4. Return the final postfix expression."
        );
        registerSupplementalTopic(
            "infix_to_prefix",
            "Infix to Prefix",
            "Infix to prefix conversion reverses the expression, swaps parentheses, uses a stack conversion pass, and reverses the result.",
            "It is like reading the expression from the other side, arranging operators by priority, then flipping the answer back.",
            "O(n)",
            "O(n)",
            "O(n)",
            "O(n)",
            new int[] {2, 3, 4},
            0,
            "Algorithm: PREFIX(Q, P)\n\n"
                + "Suppose Q is an arithmetic expression written in infix notation. This algorithm finds the equivalent prefix expression P.\n\n"
                + "1. Reverse the infix expression Q.\n"
                + "Replace every \"(\" with \")\" and every \")\" with \"(\".\n\n"
                + "2. Push \"(\" onto STACK, and add \")\" to the end of the reversed Q.\n\n"
                + "3. Scan the modified Q from left to right and repeat Steps 4 to 7 for each element of Q until the STACK is empty.\n\n"
                + "4. If an operand is encountered, add it to P.\n\n"
                + "5. If a left parenthesis is encountered, push it onto STACK.\n\n"
                + "6. If an operator is encountered, then:\n"
                + "(a) Repeatedly pop from STACK and add to P each operator which has higher precedence than the current operator.\n"
                + "(b) Push the current operator onto STACK.\n\n"
                + "7. If a right parenthesis is encountered, then:\n"
                + "(a) Repeatedly pop from STACK and add to P until a left parenthesis is encountered.\n"
                + "(b) Remove the left parenthesis (do not add it to P).\n\n"
                + "8. After scanning is complete, reverse the obtained expression P.\n\n"
                + "9. Exit.",
            "Algorithm: PREFIX_FUNCTIONS(Q, P)\n\n"
                + "1. Define REVERSE_AND_SWAP, INFIX_TO_POSTFIX_VARIANT, and REVERSE_RESULT.\n"
                + "2. Reverse Q and swap parentheses.\n"
                + "3. Convert the modified expression using a stack, popping only higher precedence operators.\n"
                + "4. Reverse the temporary result.\n"
                + "5. Return P.",
            "Algorithm: PREFIX_REC(Q, INDEX, STACK, P)\n\n"
                + "1. Reverse Q and swap parentheses before recursion begins.\n"
                + "2. If STACK is empty, reverse P and return it.\n"
                + "3. Process the current symbol using the prefix conversion rules.\n"
                + "4. Recurse with INDEX + 1, updated STACK, and updated P."
        );
    }

    private AppRepository() {
    }

    public static void initialize(Context context) {
        if (context == null || STRUCTURED_CONTENT_LOADED.getAndSet(true)) {
            return;
        }

        StructuredContentBundle bundle = StructuredContentLoader.load(context.getApplicationContext());
        ContentValidator.throwIfInvalid(ContentValidator.validateBundle(bundle));
        for (CategoryItem item : bundle.categories) {
            upsertCategory(item);
        }
        for (TechniqueItem item : bundle.techniques) {
            upsertTechnique(item);
        }
        for (Map.Entry<String, TechniqueContent> entry : bundle.contentById.entrySet()) {
            TechniqueContent existing = CONTENT.get(entry.getKey());
            if (existing == null) {
                CONTENT.put(entry.getKey(), entry.getValue());
            } else {
                CONTENT.put(entry.getKey(), mergeTechniqueContent(existing, entry.getValue()));
            }
            StructuredContentValidator.validateMergedContent(CONTENT.get(entry.getKey()), bundle, entry.getKey());
        }
        applyPdfAlgorithmCorrections();
        ContentValidator.throwIfInvalid(ContentValidator.validateBundle(bundle));
    }

    private static void applyPdfAlgorithmCorrections() {
        putPdfAlgorithm(
            "linear_search",
            "(Linear Search) LINEAR(DATA, N, ITEM, LOC)\n\n"
                + "Here DATA is a linear array with N elements, and ITEM is a given item of information.\n"
                + "This algorithm finds the location LOC of ITEM in DATA, or sets LOC := 0 if the search is unsuccessful.\n\n"
                + "1. [Insert ITEM at the end of DATA.] Set DATA[N + 1] := ITEM.\n"
                + "2. [Initialize counter.] Set LOC := 1.\n"
                + "3. [Search for ITEM.]\n"
                + "   Repeat while DATA[LOC] != ITEM:\n"
                + "       Set LOC := LOC + 1.\n"
                + "   [End of loop.]\n"
                + "4. [Successful?] If LOC = N + 1, then: Set LOC := 0.\n"
                + "5. Exit."
        );
        putPdfAlgorithm(
            "quicksort",
            "(Quicksort)\n\n"
                + "This algorithm sorts an array A with N elements.\n\n"
                + "1. [Initialize.] TOP := NULL.\n"
                + "2. [Push boundary values of A onto stacks when A has 2 or more elements.]\n"
                + "   If N > 1, then: TOP := TOP + 1, LOWER[1] := 1, UPPER[1] := N.\n"
                + "3. Repeat Steps 4 to 7 while TOP != NULL.\n"
                + "4. [Pop sublist from stacks.]\n"
                + "   Set BEG := LOWER[TOP], END := UPPER[TOP], TOP := TOP - 1.\n"
                + "5. Call QUICK(A, N, BEG, END, LOC).\n"
                + "6. [Push left sublist onto stacks when it has 2 or more elements.]\n"
                + "   If BEG < LOC - 1, then: TOP := TOP + 1, LOWER[TOP] := BEG, UPPER[TOP] := LOC - 1.\n"
                + "7. [Push right sublist onto stacks when it has 2 or more elements.]\n"
                + "   If LOC + 1 < END, then: TOP := TOP + 1, LOWER[TOP] := LOC + 1, UPPER[TOP] := END.\n"
                + "   [End of Step 3 loop.]\n"
                + "8. Exit."
        );
        putPdfAlgorithm(
            "merge_sort",
            "MERGESORT\n\n"
                + "Algorithm 9.7: MERGESORT(A, N)\n\n"
                + "This algorithm sorts the N-element array A using an auxiliary array B.\n\n"
                + "1. Set L := 1. [Initializes the number of elements in subarrays.]\n"
                + "2. Repeat Steps 3 to 5 while L < N:\n"
                + "3.     Call MERGEPASS(A, N, L, B).\n"
                + "4.     Call MERGEPASS(B, N, 2 * L, A).\n"
                + "5.     Set L := 4 * L.\n"
                + "   [End of Step 2 loop.]\n"
                + "6. Exit."
        );
        putPdfAlgorithm(
            "radix_sort",
            "RADIX SORT (LSD Method)\n\n"
                + "Algorithm: RADIXSORT(A, N, D)\n\n"
                + "This algorithm sorts the array A with N elements, where each element has at most D digits.\n\n"
                + "1. Initialize 10 queues Q[0], Q[1], ..., Q[9].\n"
                + "2. Repeat for K = 1 to D: [Process each digit]\n"
                + "3.     Repeat for I = 1 to N:\n"
                + "           Extract the K-th digit of A[I] (from right).\n"
                + "           Insert A[I] into queue Q[digit].\n"
                + "       [End of loop.]\n"
                + "4.     Collect elements from Q[0] to Q[9] in order and store back into array A.\n"
                + "       [End of Step 2 loop.]\n"
                + "5. Exit."
        );
        putPdfAlgorithm(
            "warshall_algorithm",
            "(Warshall's Algorithm)\n\n"
                + "A directed graph G with M nodes is maintained in memory by its adjacency matrix A.\n"
                + "This algorithm finds the (Boolean) path matrix P of the graph G.\n\n"
                + "1. Repeat for I, J = 1, 2, ... , M: [Initializes P.]\n"
                + "   If A[I, J] = 0, then:\n"
                + "       Set P[I, J] := 0;\n"
                + "   Else:\n"
                + "       Set P[I, J] := 1;\n"
                + "   [End of loop.]\n"
                + "2. Repeat Steps 3 and 4 for K = 1, 2, ... , M: [Updates P.]\n"
                + "3. Repeat Step 4 for I = 1, 2, ... , M:\n"
                + "4. Repeat for J = 1, 2, ... , M:\n"
                + "       Set P[I, J] := P[I, J] OR (P[I, K] AND P[K, J]).\n"
                + "   [End of loop.]\n"
                + "   [End of Step 3 loop.]\n"
                + "   [End of Step 2 loop.]\n"
                + "5. Exit."
        );
        putPdfAlgorithm(
            "floyd_algorithm",
            "(Shortest-Path Algorithm)\n\n"
                + "A weighted graph G with M nodes is maintained in memory by its weight matrix W.\n"
                + "This algorithm finds a matrix Q such that Q[I, J] is the length of a shortest path from node VI to node VJ.\n\n"
                + "1. Repeat for I, J = 1, 2, ... , M: [Initializes Q.]\n"
                + "   If W[I, J] = 0, then:\n"
                + "       Set Q[I, J] := INFINITY;\n"
                + "   Else:\n"
                + "       Set Q[I, J] := W[I, J].\n"
                + "   [End of loop.]\n"
                + "2. Repeat Steps 3 and 4 for K = 1, 2, ... , M: [Updates Q.]\n"
                + "3. Repeat Step 4 for I = 1, 2, ... , M:\n"
                + "4. Repeat for J = 1, 2, ... , M:\n"
                + "       Set Q[I, J] := MIN(Q[I, J], Q[I, K] + Q[K, J]).\n"
                + "   [End of loop.]\n"
                + "   [End of Step 3 loop.]\n"
                + "   [End of Step 2 loop.]\n"
                + "5. Exit."
        );
        putPdfAlgorithm(
            "breadth_first_search",
            "Breadth-First Search (BFS)\n\n"
                + "Algorithm A: This algorithm executes a breadth-first search on a graph G beginning at a starting node A.\n\n"
                + "1. Initialize all nodes to the ready state (STATUS = 1).\n"
                + "2. Put the starting node A in QUEUE and change its status to the waiting state (STATUS = 2).\n"
                + "3. Repeat Steps 4 and 5 until QUEUE is empty:\n"
                + "4. Remove the front node N of QUEUE.\n"
                + "   Process N and change the status of N to the processed state (STATUS = 3).\n"
                + "5. Add to the rear of QUEUE all the neighbors of N that are in the ready state (STATUS = 1), and change their status to the waiting state (STATUS = 2).\n"
                + "   [End of Step 3 loop.]\n"
                + "6. Exit."
        );
        putPdfAlgorithm(
            "depth_first_search",
            "Depth-First Search (DFS)\n\n"
                + "Algorithm B: This algorithm executes a depth-first search on a graph G beginning at a starting node A.\n\n"
                + "1. Initialize all nodes to the ready state (STATUS = 1).\n"
                + "2. Push the starting node A onto STACK and change its status to the waiting state (STATUS = 2).\n"
                + "3. Repeat Steps 4 and 5 until STACK is empty:\n"
                + "4. Pop the top node N of STACK.\n"
                + "   Process N and change its status to the processed state (STATUS = 3).\n"
                + "5. Push onto STACK all the neighbors of N that are still in the ready state (STATUS = 1), and change their status to the waiting state (STATUS = 2).\n"
                + "   [End of Step 3 loop.]\n"
                + "6. Exit."
        );
        putPdfAlgorithm(
            "topological_sort",
            "Topological Sort\n\n"
                + "Algorithm C: This algorithm finds a topological sort T of a graph S without cycles.\n\n"
                + "1. Find the indegree INDEG(N) of each node N of S.\n"
                + "   [This can be done by traversing each adjacency list.]\n"
                + "2. Put in a queue all the nodes with zero indegree.\n"
                + "3. Repeat Steps 4 and 5 until the queue is empty:\n"
                + "4. Remove the front node N of the queue (by setting FRONT := FRONT + 1).\n"
                + "5. Repeat the following for each neighbor M of node N:\n"
                + "   (a) Set INDEG(M) := INDEG(M) - 1.\n"
                + "       [Deletes the edge from N to M.]\n"
                + "   (b) If INDEG(M) = 0, then:\n"
                + "           Add M to the rear of the queue.\n"
                + "   [End of loop.]\n"
                + "   [End of Step 3 loop.]\n"
                + "6. Exit."
        );
    }

    private static void putPdfAlgorithm(String techniqueId, String algorithm) {
        TechniqueContent content = CONTENT.get(techniqueId);
        if (content == null || algorithm == null || algorithm.trim().isEmpty()) {
            return;
        }
        content.algorithmByStyle.put("Iterative", algorithm);
        content.algorithmByStyle.put("Functions", algorithm);
        content.algorithmByStyle.put("Recursion", algorithm);
    }

    public static List<CategoryItem> getCategories() {
        return new ArrayList<>(CATEGORIES);
    }

    public static List<TechniqueItem> getTechniquesForCategory(String categoryId) {
        List<TechniqueItem> result = new ArrayList<>();
        for (TechniqueItem item : TECHNIQUES) {
            if (item.categoryId.equals(categoryId)) {
                result.add(item);
            }
        }
        return result;
    }

    public static TechniqueItem getTechnique(String techniqueId) {
        for (TechniqueItem item : TECHNIQUES) {
            if (item.id.equals(techniqueId)) {
                return item;
            }
        }
        return null;
    }

    public static TechniqueContent getContent(String techniqueId) {
        TechniqueContent content = CONTENT.get(techniqueId);
        if (content != null) {
            return content;
        }

        TechniqueItem item = getTechnique(techniqueId);
        TechniqueContent placeholder = new TechniqueContent(
            techniqueId,
            item != null ? item.title : "Technique",
            "This topic is ready for explanation, algorithm, code, and visualization expansion.",
            "A practical everyday example will appear here as the content grows.",
            "Varies",
            "Varies",
            "Varies",
            "Varies",
            new int[] {5, 9, 2, 7, 1},
            7
        );
        placeholder.algorithmByStyle.put("Iterative", "This algorithm content will be added in the same beginner-friendly style.");
        placeholder.algorithmByStyle.put("Functions", "This function-style algorithm content will be added next.");
        placeholder.algorithmByStyle.put("Recursion", "This recursive algorithm content will be added where suitable.");
        placeholder.codeByStyle.put("Iterative", "// Java implementation will be added here.");
        placeholder.codeByStyle.put("Functions", "// Function-oriented Java implementation will be added here.");
        placeholder.codeByStyle.put("Recursion", "// Recursive Java implementation will be added here.");
        return placeholder;
    }

    public static String getCodeSnippet(String techniqueId, String style, String language) {
        return CodeSnippetProvider.getCodeSnippet(techniqueId, style, language);
    }

    public static String getCodeSnippet(String techniqueId, String style, String language, boolean preferCommented) {
        TechniqueContent content = CONTENT.get(techniqueId);
        if (content != null) {
            String explicit = content.getCode(language, style, preferCommented);
            if (explicit != null && !explicit.trim().isEmpty()) {
                return explicit;
            }
            if (preferCommented) {
                String clean = content.getCode(language, style, false);
                if (clean != null && !clean.trim().isEmpty()) {
                    return clean;
                }
            }
        }
        return CodeSnippetProvider.getCodeSnippet(techniqueId, style, language);
    }

    public static boolean hasExplicitCommentedCode(String techniqueId, String style, String language) {
        TechniqueContent content = CONTENT.get(techniqueId);
        if (content == null) {
            return false;
        }
        String explicit = content.getCode(language, style, true);
        return explicit != null && !explicit.trim().isEmpty();
    }

    private static TechniqueContent createTechniqueContent(
        String techniqueId,
        String title,
        String definition,
        String realLifeExample,
        String bestTime,
        String averageTime,
        String worstTime,
        String spaceComplexity,
        int[] sampleInput,
        int sampleTarget
    ) {
        TechniqueContent content = new TechniqueContent(
            techniqueId,
            title,
            definition,
            realLifeExample,
            bestTime,
            averageTime,
            worstTime,
            spaceComplexity,
            sampleInput,
            sampleTarget
        );

        content.algorithmByStyle.put("Iterative", "1. Follow the core steps from left to right.\n2. Update the important pointers or values.\n3. Continue until the technique reaches its final state.\n4. Return the result or final structure.");
        content.algorithmByStyle.put("Functions", "1. Put the main logic inside a reusable function.\n2. Pass the current values and control variables.\n3. Return the updated result after each operation.\n4. Keep the flow simple and beginner-friendly.");
        content.algorithmByStyle.put("Recursion", "1. Solve one small step first.\n2. Call the same logic for the remaining work when recursion fits.\n3. Stop at the base condition.\n4. Return the final answer clearly.");

        content.codeByStyle.put("Iterative", "// Java implementation for " + title + " will be expanded here in iterative style.");
        content.codeByStyle.put("Functions", "// Java implementation for " + title + " will be expanded here with helper functions.");
        content.codeByStyle.put("Recursion", "// Recursive Java implementation for " + title + " will be expanded where it fits naturally.");
        content.explanationSteps.addAll(ExplanationStepFactory.buildDefaultSteps(content));
        return content;
    }

    private static void registerSupplementalTopic(
        String techniqueId,
        String title,
        String definition,
        String realLifeExample,
        String bestTime,
        String averageTime,
        String worstTime,
        String spaceComplexity,
        int[] sampleInput,
        int sampleTarget,
        String iterativeAlgorithm,
        String functionAlgorithm,
        String recursionAlgorithm
    ) {
        TechniqueContent content = createTechniqueContent(
            techniqueId,
            title,
            definition,
            realLifeExample,
            bestTime,
            averageTime,
            worstTime,
            spaceComplexity,
            sampleInput,
            sampleTarget
        );
        content.algorithmByStyle.put("Iterative", iterativeAlgorithm);
        content.algorithmByStyle.put("Functions", functionAlgorithm);
        content.algorithmByStyle.put("Recursion", recursionAlgorithm);
        CONTENT.put(techniqueId, content);
    }

    private static void upsertCategory(CategoryItem categoryItem) {
        for (int i = 0; i < CATEGORIES.size(); i++) {
            if (CATEGORIES.get(i).id.equals(categoryItem.id)) {
                CATEGORIES.set(i, categoryItem);
                return;
            }
        }
        CATEGORIES.add(categoryItem);
    }

    private static void upsertTechnique(TechniqueItem techniqueItem) {
        for (int i = 0; i < TECHNIQUES.size(); i++) {
            if (TECHNIQUES.get(i).id.equals(techniqueItem.id)) {
                TECHNIQUES.set(i, techniqueItem);
                return;
            }
        }
        TECHNIQUES.add(techniqueItem);
    }

    private static TechniqueContent mergeTechniqueContent(TechniqueContent base, TechniqueContent overlay) {
        TechniqueContent merged = new TechniqueContent(
            base.techniqueId,
            pickValue(overlay.title, base.title),
            pickValue(overlay.definition, base.definition),
            pickValue(overlay.realLifeExample, base.realLifeExample),
            pickValue(overlay.bestTime, base.bestTime),
            pickValue(overlay.averageTime, base.averageTime),
            pickValue(overlay.worstTime, base.worstTime),
            pickValue(overlay.spaceComplexity, base.spaceComplexity),
            overlay.sampleInput != null && overlay.sampleInput.length > 0 ? overlay.sampleInput : base.sampleInput,
            overlay.sampleInput != null && overlay.sampleInput.length > 0 ? overlay.sampleTarget : base.sampleTarget
        );
        merged.contentVersion = overlay.contentVersion > 0 ? overlay.contentVersion : base.contentVersion;
        merged.structuredFormatVersion = overlay.structuredFormatVersion > 0 ? overlay.structuredFormatVersion : base.structuredFormatVersion;
        merged.sourceType = pickValue(overlay.sourceType, base.sourceType);
        merged.sourceTitle = pickValue(overlay.sourceTitle, base.sourceTitle);
        merged.sourceChapter = pickValue(overlay.sourceChapter, base.sourceChapter);
        merged.sourceReference = pickValue(overlay.sourceReference, base.sourceReference);
        merged.updatedAt = pickValue(overlay.updatedAt, base.updatedAt);
        merged.overrideOf = pickValue(overlay.overrideOf, base.overrideOf);
        merged.visualizationType = pickValue(overlay.visualizationType, base.visualizationType);
        merged.explanationSteps.addAll(base.explanationSteps);
        for (String step : overlay.explanationSteps) {
            if (step != null && !step.trim().isEmpty()) {
                merged.explanationSteps.add(step);
            }
        }
        if (merged.explanationSteps.isEmpty()) {
            merged.explanationSteps.addAll(ExplanationStepFactory.buildDefaultSteps(merged));
        }

        merged.algorithmByStyle.putAll(base.algorithmByStyle);
        putNonEmpty(merged.algorithmByStyle, overlay.algorithmByStyle);

        merged.codeByStyle.putAll(base.codeByStyle);
        putNonEmpty(merged.codeByStyle, overlay.codeByStyle);

        merged.codeByLanguageAndStyle.putAll(base.codeByLanguageAndStyle);
        putLanguageCode(merged.codeByLanguageAndStyle, overlay.codeByLanguageAndStyle);

        merged.commentedCodeByLanguageAndStyle.putAll(base.commentedCodeByLanguageAndStyle);
        putLanguageCode(merged.commentedCodeByLanguageAndStyle, overlay.commentedCodeByLanguageAndStyle);

        return merged;
    }

    private static String pickValue(String preferred, String fallback) {
        return preferred != null && !preferred.trim().isEmpty() ? preferred : fallback;
    }

    private static void putNonEmpty(Map<String, String> target, Map<String, String> source) {
        for (Map.Entry<String, String> entry : source.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().trim().isEmpty()) {
                target.put(entry.getKey(), entry.getValue());
            }
        }
    }

    private static void putLanguageCode(
        Map<String, Map<String, String>> target,
        Map<String, Map<String, String>> source
    ) {
        for (Map.Entry<String, Map<String, String>> languageEntry : source.entrySet()) {
            Map<String, String> mergedStyles = target.get(languageEntry.getKey());
            if (mergedStyles == null) {
                mergedStyles = new LinkedHashMap<>();
                target.put(languageEntry.getKey(), mergedStyles);
            }
            putNonEmpty(mergedStyles, languageEntry.getValue());
        }
    }
}
