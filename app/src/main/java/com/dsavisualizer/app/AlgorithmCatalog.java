package com.dsavisualizer.app;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Central catalog for the app modes.
 * The UI groups algorithms by learning area instead of by book chapter.
 */
public final class AlgorithmCatalog {

    public static final String[] CATEGORY_TITLES = {
        "String Algorithms",
        "Sorting Algorithms",
        "Searching Algorithms",
        "Linked Structures",
        "Stack Queue Recursion",
        "Trees",
        "Graphs"
    };

    public static final String[][] CATEGORY_ITEMS = {
        {"Pattern Matching Algorithms"},
        {"Bubble Sort", "Quick Sort", "Heap; Heapsort", "Insertion Sort", "Selection Sort", "Merging", "Merge-Sort", "Radix Sort"},
        {"Linear Search", "Binary Search", "Binary Search Trees", "Searching and Inserting in Binary Search Trees", "Deleting in a Binary Search Tree", "Hashing"},
        {"Singly Linked List", "Doubly Linked List"},
        {"Stack", "Queue", "Deque", "Priority Queue", "Tower of Hanoi"},
        {"Traversing Binary Trees", "Traversal Algorithms using Stacks", "Path Lengths; Huffman's Algorithm"},
        {"Traversing a Graph", "Posets; Topological Sorting", "Warshall's Algorithm; Shortest Paths", "Spanning Tree"}
    };

    private static final Map<String, AlgorithmInfo> INFO = new LinkedHashMap<>();

    static {
        add(
            "Pattern Matching Algorithms",
            "String Algorithms",
            "Matches a pattern inside a text using direct comparison or a faster table-driven approach.",
            "Medium",
            "O(n)",
            "O(n^2)",
            "O(n^2)",
            "O(1) to O(m)",
            "TEXT=ABABDABACDABABCABAB | PATTERN=ABABCABAB"
        );

        add("Bubble Sort", "Sorting Algorithms", "Repeated passes compare adjacent elements and move the largest item toward the end.", "Easy", "O(n)", "O(n^2)", "O(n^2)", "O(1)", "8, 3, 1, 5, 2, 9");
        add("Quick Sort", "Sorting Algorithms", "Partitions the array around a pivot and sorts the smaller subproblems recursively.", "Hard", "O(n log n)", "O(n log n)", "O(n^2)", "O(log n)", "8, 3, 1, 5, 2, 9");
        addSame("Heap; Heapsort", "Sorting Algorithms", "Builds a heap and repeatedly removes the root into its final position.", "Hard", "O(n log n)", "O(1)", "8, 3, 1, 5, 2, 9");
        add("Insertion Sort", "Sorting Algorithms", "Inserts each new item into the correct place in the already sorted prefix.", "Easy", "O(n)", "O(n^2)", "O(n^2)", "O(1)", "8, 3, 1, 5, 2, 9");
        addSame("Selection Sort", "Sorting Algorithms", "Selects the smallest remaining item on each pass and swaps it into place.", "Easy", "O(n^2)", "O(1)", "8, 3, 1, 5, 2, 9");
        addSame("Merging", "Sorting Algorithms", "Combines two already sorted lists into one sorted result.", "Medium", "O(n)", "O(n)", "A=11, 22, 40 | B=15, 33, 44");
        addSame("Merge-Sort", "Sorting Algorithms", "Splits the array, sorts both halves, and merges them back together.", "Medium", "O(n log n)", "O(n)", "8, 3, 1, 5, 2, 9");
        addSame("Radix Sort", "Sorting Algorithms", "Uses stable digit-by-digit passes from least significant to most significant position.", "Medium", "O(dsn)", "O(n + d)", "348, 143, 361, 423, 538");

        add("Linear Search", "Searching Algorithms", "Scans values one by one until the target is found or the data ends.", "Easy", "O(1)", "O(n)", "O(n)", "O(1)", "10, 20, 30, 40, 50, 60");
        add("Binary Search", "Searching Algorithms", "Narrows the active range by comparing the target with the middle value.", "Medium", "O(1)", "O(log n)", "O(log n)", "O(1)", "10, 20, 30, 40, 50, 60, 70, 80");
        add("Binary Search Trees", "Searching Algorithms", "Ordered hierarchical structure where left subtrees contain smaller values and right contain larger.", "Medium", "O(log n)", "O(log n)", "O(n)", "O(h)", "50");
        add("Searching and Inserting in Binary Search Trees", "Searching Algorithms", "Follows a unique path from the root to locate a value or a valid null link for insertion.", "Medium", "O(log n)", "O(log n)", "O(n)", "O(h)", "50");
        add("Deleting in a Binary Search Tree", "Searching Algorithms", "Removes a node by handling three distinct cases: leaf removal, single child bypass, or inorder successor swap.", "Hard", "O(log n)", "O(log n)", "O(n)", "O(h)", "50");
        add("Hashing", "Searching Algorithms", "Maps keys to table addresses using a hash function and resolves collisions via probing or chaining.", "Medium", "O(1)", "O(1)", "O(n)", "O(n)", "3205, 7148, 2345");

        addSame("Singly Linked List", "Linked Structures", "Linear collection of nodes connected in one direction, supporting dynamic insertion and deletion.", "Easy", "O(n)", "O(n)", "15");
        addSame("Doubly Linked List", "Linked Structures", "Nodes containing pointers to both successor and predecessor, allowing bidirectional traversal.", "Medium", "O(n)", "O(n)", "15");

        addSame("Stack", "Stack Queue Recursion", "Linear data structure following the Last-In, First-Out (LIFO) principle for element access.", "Easy", "O(1)", "O(n)", "15");
        addSame("Queue", "Stack Queue Recursion", "Abstract data type following the First-In, First-Out (FIFO) principle for sequential processing.", "Easy", "O(1)", "O(n)", "15");
        addSame("Deque", "Stack Queue Recursion", "Double-Ended Queue allowing efficient insertion and deletion from both the front and rear endpoints.", "Medium", "O(1)", "O(n)", "15");
        add("Priority Queue", "Stack Queue Recursion", "Collection where each element has a priority; elements with higher priority are dequeued first.", "Medium", "O(1)", "O(log n)", "O(n)", "O(n)", "15");
        addSame("Tower of Hanoi", "Stack Queue Recursion", "Mathematical puzzle demonstrating recursive logic by moving a stack of disks across three pegs.", "Medium", "O(2^n)", "O(n)", "3");

        addSame("Traversing Binary Trees", "Trees", "Systematic methods (Inorder, Preorder, Postorder) for visiting every node in a tree exactly once.", "Medium", "O(n)", "O(h)", "Built-in sample tree");
        addSame("Traversal Algorithms using Stacks", "Trees", "Non-recursive tree traversal implementation using an auxiliary stack to manage node processing.", "Medium", "O(n)", "O(h)", "Built-in sample tree");
        addSame("Path Lengths; Huffman's Algorithm", "Trees", "Greedy approach to building optimal prefix trees by repeatedly merging the two least frequent nodes.", "Hard", "O(n log n)", "O(n)", "A:5, B:9, C:12, D:13, E:16, F:45");

        addSame("Traversing a Graph", "Graphs", "Exploration of vertex-edge relationships level-by-level (BFS) or following a single branch (DFS).", "Medium", "O(V + E)", "O(V)", "Built-in graph demo");
        addSame("Posets; Topological Sorting", "Graphs", "Ordering vertices of a DAG such that for every directed edge (u, v), u precedes v in the sequence.", "Hard", "O(V + E)", "O(V)", "Built-in DAG demo");
        addSame("Warshall's Algorithm; Shortest Paths", "Graphs", "Dynamic programming technique to compute the transitive closure or all-pairs shortest paths in a graph.", "Hard", "O(n^3)", "O(n^2)", "Built-in adjacency matrix demo");
        addSame("Spanning Tree", "Graphs", "Sub-graph that connects all vertices of the original graph without any cycles.", "Medium", "O(V + E)", "O(V)", "Built-in graph idea");
    }

    private AlgorithmCatalog() {
    }

    private static void addSame(
        String mode,
        String group,
        String summary,
        String difficulty,
        String time,
        String space,
        String sampleInput
    ) {
        add(mode, group, summary, difficulty, time, time, time, space, sampleInput);
    }

    private static void add(
        String mode,
        String group,
        String summary,
        String difficulty,
        String bestTime,
        String averageTime,
        String worstTime,
        String space,
        String sampleInput
    ) {
        INFO.put(mode, new AlgorithmInfo(group, summary, difficulty, bestTime, averageTime, worstTime, space, sampleInput));
    }

    public static String[] getCategoryItems(int categoryIndex) {
        return CATEGORY_ITEMS[categoryIndex];
    }

    public static AlgorithmInfo getInfo(String mode) {
        AlgorithmInfo info = INFO.get(mode);
        if (info != null) {
            return info;
        }
        return new AlgorithmInfo("General", "Learning mode for " + mode, "Medium", "Varies", "Varies", "Varies", "Varies", "");
    }

    public static String normalizeModeKey(String mode) {
        return mode;
    }

    public static String[] getAllModes() {
        return INFO.keySet().toArray(new String[0]);
    }

    public static String getCategoryTitle(int categoryIndex) {
        return CATEGORY_TITLES[categoryIndex];
    }

    public static final class AlgorithmInfo {
        public final String group;
        public final String summary;
        public final String difficulty;
        public final String bestTime;
        public final String averageTime;
        public final String worstTime;
        public final String spaceComplexity;
        public final String sampleInput;

        private AlgorithmInfo(
            String group,
            String summary,
            String difficulty,
            String bestTime,
            String averageTime,
            String worstTime,
            String spaceComplexity,
            String sampleInput
        ) {
            this.group = group;
            this.summary = summary;
            this.difficulty = difficulty;
            this.bestTime = bestTime;
            this.averageTime = averageTime;
            this.worstTime = worstTime;
            this.spaceComplexity = spaceComplexity;
            this.sampleInput = sampleInput;
        }
    }
}
