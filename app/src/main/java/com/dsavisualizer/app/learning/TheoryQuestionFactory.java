package com.dsavisualizer.app.learning;

import com.dsavisualizer.app.model.TechniqueContent;
import com.dsavisualizer.app.ui.visualization.VisualizationTechniqueRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

public class TheoryQuestionFactory {

    private static final int TARGET_COUNT = 10;

    public List<TheoryQuestion> build(String techniqueId, TechniqueContent content) {
        List<TheoryQuestion> questions = new ArrayList<>();
        if ("linear_search".equals(techniqueId)) {
            questions.addAll(buildLinearSearchTheory(content));
        } else if ("binary_search".equals(techniqueId) || "leetcode_binary_search".equals(techniqueId)) {
            questions.addAll(buildBinarySearchTheory(content));
        } else if ("bubble_sort".equals(techniqueId)) {
            questions.addAll(buildBubbleSortTheory(content));
        } else if ("insertion_sort".equals(techniqueId)) {
            questions.addAll(buildInsertionSortTheory(content));
        } else if ("selection_sort".equals(techniqueId)) {
            questions.addAll(buildSelectionSortTheory(content));
        } else if ("merge_sort".equals(techniqueId) || "merging".equals(techniqueId)) {
            questions.addAll(buildMergeSortTheory(content));
        } else if ("quicksort".equals(techniqueId)) {
            questions.addAll(buildQuickSortTheory(content));
        } else if ("stack_basics".equals(techniqueId) || "linked_stack_push".equals(techniqueId) || "linked_stack_pop".equals(techniqueId)) {
            questions.addAll(buildStackTheory(content));
        } else if ("queue_basics".equals(techniqueId) || "circular_queue".equals(techniqueId) || "linked_queue".equals(techniqueId) || "deque".equals(techniqueId)) {
            questions.addAll(buildQueueTheory(content));
        } else if ("singly_linked_list".equals(techniqueId) || (techniqueId != null && techniqueId.contains("linked_list") && !techniqueId.startsWith("leetcode_"))) {
            questions.addAll(buildLinkedListTheory(content));
        } else if ("breadth_first_search".equals(techniqueId) || "depth_first_search".equals(techniqueId) || "graph_traversal".equals(techniqueId)) {
            questions.addAll(buildBfsDfsTheory(techniqueId, content));
        } else if ("dijkstra_algorithm".equals(techniqueId) || "shortest_paths".equals(techniqueId)) {
            questions.addAll(buildDijkstraTheory(content));
        } else if ("floyd_algorithm".equals(techniqueId) || "warshall_algorithm".equals(techniqueId)) {
            questions.addAll(buildFloydWarshallTheory(content));
        } else if ("topological_sort".equals(techniqueId)) {
            questions.addAll(buildTopologicalSortTheory(content));
        } else if ("spanning_tree".equals(techniqueId)) {
            questions.addAll(buildSpanningTreeTheory(content));
        } else if ("avl_tree".equals(techniqueId)) {
            questions.addAll(buildAvlTreeTheory(content));
        } else if ("trie".equals(techniqueId)) {
            questions.addAll(buildTrieTheory(content));
        } else if ("max_heap_insertion".equals(techniqueId) || "max_heap_deletion".equals(techniqueId) || "heapsort".equals(techniqueId) || (techniqueId != null && techniqueId.contains("priority_queue"))) {
            questions.addAll(buildHeapTheory(content));
        } else if (techniqueId != null && techniqueId.startsWith("leetcode_")) {
            questions.addAll(buildLeetCodeTheory(techniqueId, content));
        } else if (techniqueId != null && (techniqueId.startsWith("bst_") || "tree_traversal".equals(techniqueId))) {
            questions.addAll(buildBstTheory(techniqueId, content));
        } else {
            questions.addAll(buildGeneralTheory(techniqueId, content));
        }
        if (questions.isEmpty()) {
            questions.addAll(buildGeneralTheory(techniqueId, content));
        }
        List<TheoryQuestion> deduped = dedupe(questions);
        Collections.shuffle(deduped);
        List<TheoryQuestion> selected = new ArrayList<>();
        int count = Math.min(TARGET_COUNT, deduped.size());
        for (int i = 0; i < count; i++) {
            selected.add(shuffleOptions(deduped.get(i)));
        }
        return selected;
    }

    private TheoryQuestion shuffleOptions(TheoryQuestion question) {
        if (question == null || question.options == null || question.options.size() <= 1) {
            return question;
        }
        String correctAnswer = question.options.get(question.correctIndex);
        List<String> shuffled = new ArrayList<>(question.options);
        Collections.shuffle(shuffled);
        int newCorrectIndex = shuffled.indexOf(correctAnswer);
        return new TheoryQuestion(question.prompt, shuffled, newCorrectIndex, question.explanation);
    }

    private List<TheoryQuestion> buildLinearSearchTheory(TechniqueContent content) {
        List<TheoryQuestion> questions = new ArrayList<>();
        String title = resolveTitle(content, "Linear Search");
        add(questions, "What is " + title + "?",
            options("Divide and conquer algorithm", "Sequential search algorithm", "Recursive sorting method", "Tree traversal"),
            1, "Linear search checks elements one by one sequentially.");
        add(questions, "Time complexity of " + title + " in the worst case?",
            options("O(1)", "O(log n)", "O(n)", "O(n^2)"),
            2, "Worst case happens when the element is at the last position or absent.");
        add(questions, "Best case time complexity of " + title + "?",
            options("O(n)", "O(log n)", "O(1)", "O(n^2)"),
            2, "Best case happens when the element is found at the first position.");
        add(questions, "Average case time complexity of " + title + "?",
            options("O(n)", "O(log n)", "O(1)", "O(n^2)"),
            0, "On average, about n/2 comparisons are needed, which is O(n).");
        add(questions, title + " is especially suitable for:",
            options("Sorted data only", "Unsorted data", "Binary trees", "Graph traversal"),
            1, "It works on both sorted and unsorted data, but is commonly used when data is unsorted.");
        add(questions, "Which condition ends " + title + "?",
            options("When array is sorted", "When key is found or list ends", "When mid element is found", "When recursion ends"),
            1, "The scan stops when the key is found or the list finishes.");
        add(questions, "Number of comparisons in the worst case for " + title + "?",
            options("1", "n/2", "n", "log n"),
            2, "It may compare with every element once.");
        add(questions, "Which data structure is best suited for " + title + "?",
            options("Stack", "Queue", "Array / List", "Tree"),
            2, "Linear search is commonly applied on arrays and lists.");
        add(questions, title + " can be applied on:",
            options("Only arrays", "Only linked lists", "Arrays and linked lists", "Only trees"),
            2, "It works on any structure that can be traversed sequentially.");
        add(questions, "If the element is not present, " + title + " typically returns:",
            options("0", "-1", "n", "NULL"),
            1, "A common convention is to return -1 when the target is absent.");
        add(questions, "Which is faster for large sorted data?",
            options("Linear Search", "Binary Search", "Both same", "None"),
            1, "Binary search uses divide-and-conquer and is much faster on sorted data.");
        add(questions, title + " requires data to be:",
            options("Sorted", "Random", "Unsorted", "No requirement"),
            3, "It can operate without any ordering requirement.");
        add(questions, "Space complexity of " + title + "?",
            options("O(1)", "O(n)", "O(log n)", "O(n^2)"),
            0, "It only uses a constant number of extra variables.");
        add(questions, title + " compares:",
            options("Only first and last", "Adjacent elements", "Each element with the key", "Middle element"),
            2, "Each visited element is compared directly against the key.");
        add(questions, "In the best case, comparisons required by " + title + " are:",
            options("n", "n/2", "1", "log n"),
            2, "If the first element matches, only one comparison is needed.");
        add(questions, title + " is also called:",
            options("Binary search", "Sequential search", "Indexed search", "Jump search"),
            1, "Another common name is sequential search.");
        add(questions, "Which scenario gives the worst case for " + title + "?",
            options("Element at first", "Element at middle", "Element at last or absent", "Sorted array"),
            2, "The whole list may have to be scanned.");
        add(questions, title + " becomes inefficient when:",
            options("Data is small", "Data is large", "Data is unsorted", "Using a linked list"),
            1, "For large datasets, one-by-one checking becomes slow.");
        add(questions, "Average number of comparisons in " + title + " is closest to:",
            options("n", "n/2", "log n", "1"),
            1, "Average case is roughly half the list.");
        add(questions, title + " works well when:",
            options("Data is large and sorted", "Data is small", "Data is indexed", "Data is hierarchical"),
            1, "It is simple and practical for small datasets.");
        add(questions, "What is the core idea behind " + title + "?",
            options("Split the list repeatedly", "Check each item in order", "Swap neighboring values", "Build a heap"),
            1, "The algorithm scans from one item to the next until it finishes.");
        add(questions, "Which statement is true about " + title + "?",
            options("It always needs sorted input", "It uses recursion by default", "It can stop early once the key is found", "It examines only half the array"),
            2, "The algorithm stops immediately when a match is found.");
        add(questions, "If the first item matches the key in " + title + ", the case is:",
            options("Worst case", "Average case", "Best case", "Invalid case"),
            2, "Finding the key at the first position gives the best case.");
        add(questions, "What happens if the key is absent in " + title + "?",
            options("The algorithm loops forever", "The algorithm checks all elements", "The algorithm sorts the array first", "The algorithm jumps to the middle"),
            1, "It keeps scanning until the list ends.");
        add(questions, "Which one is a limitation of " + title + "?",
            options("Needs too much extra memory", "Slow on large datasets", "Works only on trees", "Cannot be implemented iteratively"),
            1, "Its sequential nature makes it slow for large inputs.");
        add(questions, "What is usually tracked during " + title + "?",
            options("A current index", "A recursion stack only", "A pivot", "A heap root"),
            0, "The current position is tracked as the scan moves through the structure.");
        add(questions, "Compared to binary search, " + title + " is:",
            options("Faster on sorted large arrays", "Simpler but slower on large sorted arrays", "Always recursive", "Unable to search lists"),
            1, "It is easier to implement but less efficient on large sorted data.");
        add(questions, "Which of these is NOT a typical use of " + title + "?",
            options("Searching a short list", "Checking an unsorted list", "Finding an item in a linked list", "Balancing a binary tree"),
            3, "Balancing a tree is unrelated to sequential search.");
        add(questions, "Why is " + title + " easy to implement?",
            options("It avoids all comparisons", "It follows a simple one-by-one scan", "It always uses recursion", "It needs a special data structure"),
            1, "Its logic is just sequential traversal and comparison.");
        add(questions, "If a list has n elements, the maximum comparisons for " + title + " are:",
            options("1", "n", "n log n", "n^2"),
            1, "At most every element is checked once.");
        add(questions, "Which input property most helps " + title + "?",
            options("Having the key near the front", "Being a complete tree", "Being a min heap", "Having duplicate pointers"),
            0, "The closer the key is to the front, the fewer comparisons are needed.");
        add(questions, "Which statement about duplicates is correct for " + title + "?",
            options("It cannot handle duplicates", "It returns the first matching occurrence it reaches", "It always returns the last occurrence", "It removes duplicates before searching"),
            1, "The first duplicate encountered during the scan is returned.");
        add(questions, title + " is generally preferred when:",
            options("The data is tiny or unsorted", "The data is huge and sorted", "A priority queue is required", "The graph is weighted"),
            0, "Its simplicity makes it useful for small or unsorted collections.");
        add(questions, "What kind of access pattern does " + title + " use?",
            options("Random middle access", "Sequential access", "Heap-based access", "Hash-based access"),
            1, "It visits items in order from start to end.");
        add(questions, "Which phrase best describes " + title + "?",
            options("Compare, move, repeat", "Split, recurse, merge", "Choose pivot, partition", "Relax edges repeatedly"),
            0, "The algorithm compares the current item, moves ahead, and repeats.");
        add(questions, "What does " + title + " NOT need?",
            options("A key to search", "A traversable collection", "Pre-sorted data", "A stopping condition"),
            2, "It works without requiring sorted input.");
        add(questions, "If the key is at the last position, the case for " + title + " is:",
            options("Best case", "Average case", "Worst case", "Impossible"),
            2, "The scan must reach the final position before succeeding.");
        add(questions, "What mainly determines the running time of " + title + "?",
            options("The number of elements inspected", "The height of a tree", "The number of recursive calls only", "The number of swaps"),
            0, "The algorithm’s cost depends on how many items are checked.");
        add(questions, "Which algorithm family does " + title + " belong to?",
            options("Searching", "Sorting", "Traversal balancing", "Dynamic programming"),
            0, "Linear search is a searching algorithm.");
        add(questions, "Why is " + title + " useful on linked lists?",
            options("Because binary search works better there", "Because sequential traversal is natural on linked lists", "Because linked lists are always sorted", "Because it uses random indexing"),
            1, "Linked lists are naturally traversed one node at a time.");
        add(questions, "Which statement is false about " + title + "?",
            options("It can be iterative", "It can search arrays", "It always runs in logarithmic time", "It uses direct key comparison"),
            2, "Its time is linear in the worst case, not logarithmic.");
        add(questions, "What is checked at each step in " + title + "?",
            options("Whether the current element equals the key", "Whether the array is balanced", "Whether the pivot is smallest", "Whether the queue is full"),
            0, "The current element is compared directly with the key.");
        add(questions, "Which output is common when " + title + " succeeds?",
            options("The index or position of the key", "A sorted list", "A tree root", "A graph path"),
            0, "A successful search usually returns the matching position.");
        add(questions, "Which metric is O(1) for " + title + "?",
            options("Worst-case time", "Average-case time", "Extra space", "Maximum comparisons"),
            2, "The algorithm uses constant extra space.");
        add(questions, "What is a practical advantage of " + title + "?",
            options("Extremely fast on massive sorted data", "Very simple to understand and code", "Needs no comparisons", "Uses divide and conquer"),
            1, "Its main strength is simplicity.");
        add(questions, "When teaching " + title + ", the first rule is usually:",
            options("Start from the first element and compare", "Start from the middle", "Sort first", "Build an index table"),
            0, "The scan begins at the front and advances sequentially.");
        add(questions, "What does the algorithm do after a mismatch in " + title + "?",
            options("Move to the next element", "Move to the middle element", "Swap two items", "Stop immediately"),
            0, "After a mismatch, the scan continues with the next element.");
        add(questions, "If n doubles, the worst-case work in " + title + " roughly:",
            options("Stays the same", "Halves", "Doubles", "Becomes logarithmic"),
            2, "Worst-case work grows linearly with n.");
        add(questions, "Which classroom example best fits " + title + "?",
            options("Checking roll numbers one by one", "Splitting a phone book in half", "Building a heap", "Balancing AVL rotations"),
            0, "Sequential checking is exactly how linear search behaves.");
        add(questions, "Which result is correct if the key is found early in " + title + "?",
            options("The scan can stop immediately", "The whole list must still be scanned", "The array must be sorted next", "The search becomes recursive"),
            0, "It stops as soon as a match is found.");
        add(questions, "Which phrase best summarizes the cost pattern of " + title + "?",
            options("Small best case, linear worst case", "Constant worst case", "Quadratic average case", "Logarithmic best case only"),
            0, "It has O(1) best case and O(n) worst case.");
        return questions;
    }

    private List<TheoryQuestion> buildBinarySearchTheory(TechniqueContent content) {
        List<TheoryQuestion> questions = new ArrayList<>();
        String title = resolveTitle(content, "Binary Search");
        add(questions, "What is the primary prerequisite for " + title + "?",
            options("The array must be sorted", "The array must contain unique numbers", "The size must be a power of two", "The array must be dynamically allocated"),
            0, "Binary search relies on ordering to discard half the search space at each step.");
        add(questions, "What is the worst-case time complexity of " + title + "?",
            options("O(log n)", "O(n)", "O(n log n)", "O(1)"),
            0, "At each step, the search space is halved, taking at most log2(n) steps.");
        add(questions, "What is the best-case time complexity of " + title + "?",
            options("O(1)", "O(log n)", "O(n)", "O(n^2)"),
            0, "Best case occurs when the target is right at the middle index on the first check.");
        add(questions, "What is the space complexity of iterative " + title + "?",
            options("O(1)", "O(log n)", "O(n)", "O(n^2)"),
            0, "Iterative binary search uses only pointers (low, high, mid), requiring constant extra memory.");
        add(questions, "How should the middle index be computed to avoid 32-bit integer overflow?",
            options("low + (high - low) / 2", "(low + high) / 2", "(low + high) * 2", "low + high / 2"),
            0, "Adding low + high can overflow standard 32-bit signed integers when both are large.");
        add(questions, "If target < array[mid] in an ascending array, which pointer is updated?",
            options("high = mid - 1", "low = mid + 1", "mid = mid / 2", "high = mid"),
            0, "The target must be in the left half, so the upper bound becomes mid - 1.");
        add(questions, "What condition signals that the target is NOT found in " + title + "?",
            options("low > high", "low == high", "mid == 0", "low < 0"),
            0, "When low crosses high, the search range is completely exhausted.");
        add(questions, "Why is " + title + " impractical on a standard singly linked list?",
            options("Linked lists do not support O(1) random access", "Linked lists cannot store sorted values", "Pointers cannot be compared", "Nodes cannot store integers"),
            0, "Accessing the middle node in a singly linked list takes O(n) time, destroying the O(log n) efficiency.");
        add(questions, "Which algorithmic paradigm does " + title + " follow?",
            options("Decrease and Conquer / Divide and Conquer", "Dynamic Programming", "Greedy Algorithm", "Backtracking"),
            0, "It reduces the search space by half at each comparison.");
        add(questions, "For an array of size 1024, at most how many comparisons are needed?",
            options("11", "1024", "512", "1"),
            0, "log2(1024) = 10, so at most 11 checks are needed.");
        return questions;
    }

    private List<TheoryQuestion> buildBubbleSortTheory(TechniqueContent content) {
        List<TheoryQuestion> questions = new ArrayList<>();
        String title = resolveTitle(content, "Bubble Sort");
        add(questions, "What is the core idea of " + title + "?",
            options("Repeatedly compare adjacent elements and swap them if out of order", "Find the absolute minimum and swap it to the front", "Insert elements one by one into a sorted subarray", "Divide the array into two halves recursively"),
            0, "Bubble sort repeatedly passes through the list, swapping neighboring pairs that are out of order.");
        add(questions, "What is the worst-case time complexity of " + title + "?",
            options("O(n^2)", "O(n log n)", "O(n)", "O(log n)"),
            0, "Worst case occurs when the array is in reverse order, requiring n(n-1)/2 comparisons and swaps.");
        add(questions, "What is the best-case time complexity of optimized " + title + "?",
            options("O(n)", "O(1)", "O(n^2)", "O(n log n)"),
            0, "An optimized bubble sort uses a boolean swapped flag and terminates in O(n) if already sorted.");
        add(questions, "Is " + title + " a stable sorting algorithm?",
            options("Yes, equal elements retain their relative order", "No, adjacent swaps always destroy stability", "Only for arrays of power of two size", "Depends on the data type"),
            0, "Equal adjacent elements are never swapped, preserving their original relative order.");
        add(questions, "After the first complete pass of " + title + ", which element is in its final place?",
            options("The largest element is at the end", "The smallest element is at index 0", "The median is in the middle", "All odd elements are sorted"),
            0, "In standard ascending bubble sort, the largest value bubbles up to the last index in pass 1.");
        add(questions, "What is the auxiliary space complexity of " + title + "?",
            options("O(1)", "O(n)", "O(log n)", "O(n^2)"),
            0, "Bubble sort sorts in-place using only temporary variables for swapping.");
        add(questions, "How many comparisons occur in pass 1 for an array of size n?",
            options("n - 1", "n", "n / 2", "(n-1)^2"),
            0, "Adjacent pairs (0,1), (1,2), ... (n-2, n-1) give exactly n - 1 comparisons.");
        add(questions, "Why is " + title + " rarely used on large production datasets?",
            options("Its quadratic O(n^2) time complexity is too slow", "It requires excessive memory", "It cannot sort negative numbers", "It is unstable"),
            0, "O(n^2) comparisons make it impractical for large datasets compared to O(n log n) algorithms.");
        return questions;
    }

    private List<TheoryQuestion> buildInsertionSortTheory(TechniqueContent content) {
        List<TheoryQuestion> questions = new ArrayList<>();
        String title = resolveTitle(content, "Insertion Sort");
        add(questions, "How does " + title + " work?",
            options("Builds the sorted array one element at a time by shifting larger elements", "Swaps the global minimum with the first element", "Partitions elements around a pivot", "Merges adjacent two-element runs"),
            0, "Insertion sort takes each element and inserts it into its correct relative position within the sorted subarray.");
        add(questions, "What is the best-case time complexity of " + title + "?",
            options("O(n) when already sorted", "O(1)", "O(n log n)", "O(n^2)"),
            0, "When the input is already sorted, each element requires only one comparison with no shifts.");
        add(questions, "What is the worst-case time complexity of " + title + "?",
            options("O(n^2)", "O(n log n)", "O(n)", "O(2^n)"),
            0, "Worst case occurs for reverse sorted input, requiring quadratic shifts.");
        add(questions, "For which scenario is " + title + " highly efficient?",
            options("Small or nearly sorted arrays", "Extremely large random datasets", "Distributed cloud datasets", "Inverted large datasets"),
            0, "Insertion sort has very low overhead and runs in linear time on nearly sorted collections.");
        add(questions, "Is " + title + " stable and in-place?",
            options("Yes, it is both stable and in-place O(1)", "It is stable but requires O(n) extra space", "It is in-place but not stable", "It is neither stable nor in-place"),
            0, "Insertion sort preserves relative order and requires only constant auxiliary memory.");
        add(questions, "Which real-life analogy best describes " + title + "?",
            options("Sorting a hand of playing cards", "Balancing a set of scales", "Splitting a dictionary in half", "Searching a phonebook"),
            0, "Picking up cards one by one and inserting them into order in your hand mimics insertion sort.");
        return questions;
    }

    private List<TheoryQuestion> buildSelectionSortTheory(TechniqueContent content) {
        List<TheoryQuestion> questions = new ArrayList<>();
        String title = resolveTitle(content, "Selection Sort");
        add(questions, "What is the fundamental mechanism of " + title + "?",
            options("Repeatedly finds the minimum unsorted element and places it at the start", "Pushes the maximum element to the end through adjacent swaps", "Inserts each item into a balanced tree", "Divides the array into buckets"),
            0, "Selection sort scans the unsorted portion, picks the minimum, and swaps it with the first unsorted index.");
        add(questions, "What is the best-case time complexity of " + title + "?",
            options("O(n^2)", "O(n)", "O(n log n)", "O(1)"),
            0, "Even if the array is already sorted, selection sort still scans the remaining unsorted elements to verify the minimum.");
        add(questions, "How many swaps does " + title + " perform in the worst case?",
            options("At most n - 1 swaps (O(n))", "O(n^2) swaps", "O(n log n) swaps", "0 swaps"),
            0, "Selection sort performs at most one swap per outer loop iteration, making it optimal when writes are expensive.");
        add(questions, "Is standard array-based " + title + " stable?",
            options("No, long-distance swaps can reorder identical keys", "Yes, it never reorders identical keys", "Only when keys are unique", "Only for reverse input"),
            0, "Long-distance swapping over intermediate elements can invert the original relative order of duplicate elements.");
        add(questions, "What is the space complexity of " + title + "?",
            options("O(1)", "O(n)", "O(log n)", "O(n^2)"),
            0, "Selection sort is an in-place sorting algorithm.");
        return questions;
    }

    private List<TheoryQuestion> buildMergeSortTheory(TechniqueContent content) {
        List<TheoryQuestion> questions = new ArrayList<>();
        String title = resolveTitle(content, "Merge Sort");
        add(questions, "What design technique does " + title + " use?",
            options("Divide and Conquer", "Greedy method", "Dynamic Programming", "Backtracking"),
            0, "Merge sort divides the array into halves, sorts them recursively, and merges the sorted halves.");
        add(questions, "What is the time complexity of " + title + " in all cases (best, avg, worst)?",
            options("O(n log n)", "O(n^2)", "O(n)", "O(log n)"),
            0, "Because the array is always split evenly, the recurrence is T(n) = 2T(n/2) + O(n), yielding O(n log n) guaranteed.");
        add(questions, "What is the auxiliary space complexity of standard array " + title + "?",
            options("O(n)", "O(1)", "O(log n)", "O(n^2)"),
            0, "Standard array merge sort requires temporary array buffers proportional to the input size.");
        add(questions, "Is " + title + " a stable sorting algorithm?",
            options("Yes, if merge logic gives preference to the left subarray on ties", "No, dividing arrays always breaks stability", "Only for even lengths", "Only for integers"),
            0, "When elements are equal, taking from the left subarray first preserves the original order.");
        add(questions, "Why is " + title + " frequently preferred for sorting linked lists?",
            options("Linked lists can be merged in O(1) extra space by relinking pointers", "Linked lists cannot be partitioned", "Merge sort runs in O(n) on lists", "Arrays cannot use merge sort"),
            0, "Unlike arrays, merging linked lists only requires rearranging pointer references without extra buffers.");
        return questions;
    }

    private List<TheoryQuestion> buildQuickSortTheory(TechniqueContent content) {
        List<TheoryQuestion> questions = new ArrayList<>();
        String title = resolveTitle(content, "Quick Sort");
        add(questions, "What is the key operation in " + title + "?",
            options("Partitioning elements around a pivot", "Merging sorted halves", "Repeatedly picking the minimum", "Hashing elements into buckets"),
            0, "Quick sort picks a pivot and partitions elements so that smaller items are on the left and larger on the right.");
        add(questions, "What is the worst-case time complexity of " + title + "?",
            options("O(n^2)", "O(n log n)", "O(n)", "O(log n)"),
            0, "When the pivot chosen is always the extreme element (e.g. sorted array with first element as pivot), depth is n.");
        add(questions, "What is the average-case time complexity of " + title + "?",
            options("O(n log n)", "O(n^2)", "O(n)", "O(log n)"),
            0, "On average, balanced partitions yield O(n log n) performance.");
        add(questions, "What is the average call-stack space complexity of " + title + "?",
            options("O(log n)", "O(1)", "O(n)", "O(n^2)"),
            0, "Recursive call stack depth is proportional to tree height, which is O(log n) on average.");
        add(questions, "Which technique helps prevent the O(n^2) worst case in " + title + "?",
            options("Randomized pivot or Median-of-Three pivot", "Using a fixed first element pivot", "Always sorting backwards", "Converting to bubble sort"),
            0, "Randomized or median-of-three pivot selection makes pathological splits extremely unlikely.");
        add(questions, "Is standard in-place " + title + " stable?",
            options("No, swapping across the pivot can reorder equal elements", "Yes, always stable", "Only when using Lomuto partition", "Only on even size arrays"),
            0, "Swapping elements across the partition boundary can violate stability.");
        return questions;
    }

    private List<TheoryQuestion> buildStackTheory(TechniqueContent content) {
        List<TheoryQuestion> questions = new ArrayList<>();
        String title = resolveTitle(content, "Stack");
        add(questions, "Which data access principle governs a " + title + "?",
            options("LIFO (Last In, First Out)", "FIFO (First In, First Out)", "Priority order", "Random access"),
            0, "The most recently pushed element is the first one removed.");
        add(questions, "What is the time complexity of push and pop in a " + title + "?",
            options("O(1)", "O(n)", "O(log n)", "O(n^2)"),
            0, "Stack operations occur strictly at the top element and execute in constant time.");
        add(questions, "What error occurs when popping from an empty " + title + "?",
            options("Stack Underflow", "Stack Overflow", "Segmentation Fault", "NullPointer Array Exception"),
            0, "Attempting to delete from an empty structure causes an underflow error.");
        add(questions, "What error occurs when pushing to a full fixed-capacity " + title + "?",
            options("Stack Overflow", "Stack Underflow", "Memory Leaking", "Out of Threads"),
            0, "Pushing when the allocated storage is full triggers a stack overflow.");
        add(questions, "Which of these is a classic application of a " + title + "?",
            options("Function call recursion and undo mechanisms", "CPU round-robin scheduling", "Breadth-first search queue", "Print queue buffering"),
            0, "Call frames, undo/redo stacks, and expression parenthesization rely directly on stacks.");
        add(questions, "How is the postfix expression '5 3 + 2 *' evaluated using a stack?",
            options("Result is 16", "Result is 11", "Result is 30", "Result is 13"),
            0, "5 and 3 are pushed, '+' pops and adds them (8), 2 is pushed, '*' pops and multiplies (8 * 2 = 16).");
        return questions;
    }

    private List<TheoryQuestion> buildQueueTheory(TechniqueContent content) {
        List<TheoryQuestion> questions = new ArrayList<>();
        String title = resolveTitle(content, "Queue");
        add(questions, "Which access principle governs a standard " + title + "?",
            options("FIFO (First In, First Out)", "LIFO (Last In, First Out)", "Random access", "Sorted keys"),
            0, "Elements are processed in the exact order they arrive.");
        add(questions, "Where are elements added and removed in a standard " + title + "?",
            options("Added at rear (enqueue), removed from front (dequeue)", "Added at front, removed from rear", "Added and removed at the same top end", "Added in middle"),
            0, "Insertion happens at the back (rear) and removal happens at the front.");
        add(questions, "What problem does a Circular Queue solve over a naive linear array queue?",
            options("Memory waste at the front after repeated dequeues", "Infinite capacity requirement", "Allows negative numbers", "Eliminates pointer usage"),
            0, "A circular queue wraps rear back to index 0 using modulo arithmetic, reusing deallocated front slots.");
        add(questions, "In a circular queue of capacity N, what formula moves the rear pointer forward?",
            options("(rear + 1) % N", "rear + 1", "(rear - 1) % N", "rear * 2 % N"),
            0, "Modulo arithmetic wraps the pointer back to index 0 when it exceeds array bounds.");
        add(questions, "Which graph traversal algorithm fundamentally relies on a " + title + "?",
            options("Breadth-First Search (BFS)", "Depth-First Search (DFS)", "Dijkstra with negative weights", "Topological sort using recursion"),
            0, "BFS explores vertices level by level using a FIFO queue.");
        return questions;
    }

    private List<TheoryQuestion> buildLinkedListTheory(TechniqueContent content) {
        List<TheoryQuestion> questions = new ArrayList<>();
        String title = resolveTitle(content, "Linked List");
        add(questions, "What are the components of a standard Singly Linked List node?",
            options("A data field and a next pointer reference", "Two data fields and an index", "A key, a priority, and a parent pointer", "Only a primitive array"),
            0, "Each node stores its payload and a reference pointing to the successor node.");
        add(questions, "What is the time complexity to insert a node at the head of a " + title + "?",
            options("O(1)", "O(n)", "O(log n)", "O(n^2)"),
            0, "Updating the new node's next pointer and head pointer takes constant time.");
        add(questions, "What is the time complexity to access the k-th element in a " + title + "?",
            options("O(k) or O(n)", "O(1)", "O(log n)", "O(n^2)"),
            0, "Linked lists do not support index arithmetic; one must traverse pointers sequentially from the head.");
        add(questions, "What does the next pointer of the last node in a linear " + title + " point to?",
            options("null (or nullptr)", "Head node", "The previous node", "An arbitrary memory location"),
            0, "A null terminal pointer indicates the end of the list.");
        add(questions, "What is a major advantage of a " + title + " over a fixed array?",
            options("Dynamic size without contiguous memory reallocation", "O(1) random index access", "Better CPU cache locality", "Zero pointer overhead"),
            0, "Nodes can be allocated on-demand anywhere in heap memory without resizing huge memory buffers.");
        return questions;
    }

    private List<TheoryQuestion> buildBfsDfsTheory(String techniqueId, TechniqueContent content) {
        List<TheoryQuestion> questions = new ArrayList<>();
        boolean isBfs = "breadth_first_search".equals(techniqueId);
        String title = resolveTitle(content, isBfs ? "Breadth-First Search" : "Depth-First Search");
        if (isBfs) {
            add(questions, "Which auxiliary data structure does Breadth-First Search (BFS) use?",
                options("Queue (FIFO)", "Stack (LIFO)", "Priority Queue with min-heap only", "Binary Search Tree"),
                0, "BFS visits nodes level by level using a FIFO queue.");
            add(questions, "In an unweighted graph, what does BFS guarantee finding?",
                options("The shortest path in terms of number of edges", "The maximum weight spanning tree", "The longest path", "The Hamiltonian cycle"),
                0, "Because BFS visits nodes in increasing order of distance from the source, the first time a target is visited yields the shortest path.");
            add(questions, "What is the time complexity of BFS on a graph with V vertices and E edges?",
                options("O(V + E)", "O(V * E)", "O(V^2)", "O(log(V + E))"),
                0, "Every vertex is enqueued once and every edge is explored once in an adjacency list.");
            add(questions, "How does BFS prevent infinite loops in cyclic graphs?",
                options("By maintaining a visited set or boolean array", "By sorting all edges", "By pruning vertices with odd degree", "By using a call stack limit"),
                0, "Marking nodes as visited ensures no vertex is processed multiple times in a cycle.");
        } else {
            add(questions, "Which auxiliary data structure or concept does Depth-First Search (DFS) use?",
                options("Stack (or system call stack recursion)", "Queue (FIFO)", "Hash Map only", "Disjoint Set Union"),
                0, "DFS explores deeply along each branch before backtracking, naturally modeled by a LIFO stack.");
            add(questions, "What is the time complexity of DFS on a graph with V vertices and E edges?",
                options("O(V + E)", "O(V * E)", "O(V^2)", "O(E log V)"),
                0, "Each vertex and edge is visited once when represented by adjacency lists.");
            add(questions, "Which classic problem is DFS well known for solving?",
                options("Cycle detection and topological sorting in DAGs", "Shortest path in weighted negative graphs", "Minimum cut in planar flow", "Binary heap restructuring"),
                0, "DFS timestamps and back-edge tracking make it ideal for cycle detection and topological sorting.");
            add(questions, "What traversal order explores all descendants before moving to siblings?",
                options("Depth-First Search", "Breadth-First Search", "Level Order Traversal", "Round-Robin Traversal"),
                0, "DFS dives down each path to the leaf/deepest node before backtracking.");
        }
        return questions;
    }

    private List<TheoryQuestion> buildBstTheory(String techniqueId, TechniqueContent content) {
        List<TheoryQuestion> questions = new ArrayList<>();
        String title = resolveTitle(content, "Binary Search Tree");
        add(questions, "What invariant defines a Binary Search Tree (BST)?",
            options("Left subtree values < Node value < Right subtree values", "Left subtree values > Node value", "All nodes must have exactly two children", "Tree must always be completely full"),
            0, "For any node, keys in its left subtree are strictly less, and keys in its right subtree are greater.");
        add(questions, "Which tree traversal of a BST yields values in sorted ascending order?",
            options("In-order traversal (Left, Root, Right)", "Pre-order traversal (Root, Left, Right)", "Post-order traversal (Left, Right, Root)", "Level-order traversal"),
            0, "In-order traversal visits left subtree, current node, then right subtree, producing sorted output.");
        add(questions, "What is the worst-case search time in an unbalanced (skewed) BST?",
            options("O(n)", "O(log n)", "O(1)", "O(n log n)"),
            0, "If items are inserted in sorted order, the tree degenerates into a linked list of depth n.");
        add(questions, "What is the average search time in a balanced BST (such as an AVL Tree)?",
            options("O(log n)", "O(n)", "O(1)", "O(n^2)"),
            0, "A balanced tree maintains a height bounded by log2(n).");
        add(questions, "Where is the minimum value always located in a valid BST?",
            options("The leftmost node (follow left pointers until null)", "The rightmost leaf", "The root node", "Any leaf at odd depth"),
            0, "Because smaller values are always in the left child, the leftmost reachable node has the smallest key.");
        return questions;
    }

    private List<TheoryQuestion> buildDijkstraTheory(TechniqueContent content) {
        List<TheoryQuestion> questions = new ArrayList<>();
        String title = resolveTitle(content, "Dijkstra's Algorithm");
        add(questions, "What problem does " + title + " solve?",
            options("Single-source shortest path with non-negative edge weights", "All-pairs shortest path with negative edges", "Finding the minimum spanning tree", "Bipartite graph matching"),
            0, "Dijkstra's algorithm computes the shortest paths from a single source to all other vertices in a non-negative weighted graph.");
        add(questions, "Which algorithmic paradigm does " + title + " use?",
            options("Greedy Algorithm", "Divide and Conquer", "Dynamic Programming", "Backtracking"),
            0, "It greedily selects the unvisited vertex with the smallest provisional distance at each step.");
        add(questions, "Why does " + title + " fail on graphs with negative edge weights?",
            options("Once a vertex is marked visited, its distance can never be re-evaluated/decreased", "Negative weights cause infinite recursion", "Negative numbers cannot be stored in arrays", "Priority queues cannot store negative numbers"),
            0, "The greedy assumption that a visited vertex's shortest path is finalized breaks if negative edges reduce paths later.");
        add(questions, "What is the time complexity of " + title + " using a binary min-heap / priority queue?",
            options("O((V + E) log V)", "O(V^3)", "O(V * E)", "O(E^2)"),
            0, "Extract-min takes O(log V) for each vertex, and decrease-key takes O(log V) for each edge, giving O((V + E) log V).");
        add(questions, "What condition represents 'relaxing' an edge (u, v) with weight w?",
            options("if (dist[u] + w < dist[v]) { dist[v] = dist[u] + w; }", "if (dist[v] + w < dist[u]) { dist[u] = dist[v]; }", "if (dist[u] == dist[v]) { dist[v] = 0; }", "dist[v] = min(dist[u], dist[v])"),
            0, "Relaxation updates dist[v] if traveling through u yields a shorter path to v.");
        add(questions, "What is the initial distance value assigned to the source vertex and all other vertices?",
            options("Source is 0, all others are infinity", "All vertices are 0", "Source is infinity, all others are 0", "All vertices are -1"),
            0, "Starting distance to the source is 0, while all other distances are initialized to infinity.");
        return questions;
    }

    private List<TheoryQuestion> buildFloydWarshallTheory(TechniqueContent content) {
        List<TheoryQuestion> questions = new ArrayList<>();
        String title = resolveTitle(content, "Floyd-Warshall Algorithm");
        add(questions, "What is the primary function of the " + title + "?",
            options("Computes all-pairs shortest paths in a weighted graph", "Finds the single-source shortest path in DAGs only", "Constructs a minimum spanning tree", "Performs topological sorting"),
            0, "Floyd-Warshall finds the shortest paths between every pair of vertices in a directed or undirected graph.");
        add(questions, "What is the time complexity of " + title + "?",
            options("O(V^3)", "O(V^2)", "O(E log V)", "O(V * E)"),
            0, "The algorithm uses three nested loops iterating through all vertices: for k, for i, for j.");
        add(questions, "What algorithmic paradigm does " + title + " follow?",
            options("Dynamic Programming", "Greedy approach", "Divide and conquer", "Branch and bound"),
            0, "It builds solutions considering intermediate vertices {1, 2, ... k} using optimal substructure.");
        add(questions, "How does " + title + " detect negative-weight cycles?",
            options("If any diagonal entry dist[i][i] becomes negative (< 0)", "If dist[i][j] equals infinity", "If the outer loop runs more than V times", "It cannot detect negative cycles"),
            0, "A negative value on dist[i][i] means a path from vertex i back to itself has negative cost.");
        add(questions, "What is Warshall's algorithm specifically used for?",
            options("Transitive closure (reachability matrix) of a directed graph", "Minimum spanning tree weights", "Biconnected components", "Graph coloring"),
            0, "Warshall's algorithm determines whether there is a directed path between any pair of vertices using boolean operations.");
        return questions;
    }

    private List<TheoryQuestion> buildTopologicalSortTheory(TechniqueContent content) {
        List<TheoryQuestion> questions = new ArrayList<>();
        String title = resolveTitle(content, "Topological Sort");
        add(questions, "What kind of graph is strictly required for " + title + "?",
            options("Directed Acyclic Graph (DAG)", "Undirected tree only", "Complete graph", "Graph with negative cycles"),
            0, "Topological sort is only possible on directed graphs without any cycles (DAGs).");
        add(questions, "What does a " + title + " produce?",
            options("A linear ordering of vertices where for every directed edge u -> v, u comes before v", "The shortest path between source and sink", "A tree with minimum edge weights", "A sequence of visited vertices in DFS preorder"),
            0, "It orders tasks or prerequisites so each vertex appears before all vertices that depend on it.");
        add(questions, "Which algorithm uses vertex in-degrees and a queue for " + title + "?",
            options("Kahn's Algorithm", "Kruskal's Algorithm", "Dijkstra's Algorithm", "Prim's Algorithm"),
            0, "Kahn's algorithm repeatedly removes vertices with in-degree 0 and decrements neighbors' in-degrees.");
        add(questions, "How can " + title + " detect a cycle in a directed graph?",
            options("If the sorted output contains fewer than V vertices", "If the queue is initialized with more than 1 vertex", "If the sum of all in-degrees is even", "If all edges have positive weights"),
            0, "If a cycle exists, vertices within the cycle never reach an in-degree of 0, so output size < V.");
        add(questions, "What is the time complexity of " + title + " using an adjacency list?",
            options("O(V + E)", "O(V^2)", "O(E log V)", "O(V log V)"),
            0, "Both Kahn's algorithm and DFS-based topological sort process each vertex and edge in O(V + E) time.");
        return questions;
    }

    private List<TheoryQuestion> buildSpanningTreeTheory(TechniqueContent content) {
        List<TheoryQuestion> questions = new ArrayList<>();
        String title = resolveTitle(content, "Spanning Tree / MST");
        add(questions, "How many edges are in a spanning tree of a connected graph with V vertices?",
            options("V - 1", "V", "V + 1", "E - 1"),
            0, "A spanning tree touches all V vertices with no cycles, which always requires exactly V - 1 edges.");
        add(questions, "Which data structure does Kruskal's algorithm use to detect cycles efficiently?",
            options("Disjoint Set Union (DSU / Union-Find)", "Priority Queue of vertices", "Fibonacci Heap only", "Adjacency Matrix"),
            0, "DSU checks in near-constant time whether the two endpoints of an edge already belong to the same component.");
        add(questions, "What is the time complexity of Kruskal's algorithm on a graph with E edges?",
            options("O(E log E) or O(E log V)", "O(V^3)", "O(V + E)", "O(V^2)"),
            0, "Sorting all E edges by weight dominates the runtime, taking O(E log E).");
        add(questions, "How does Prim's algorithm build the Minimum Spanning Tree (MST)?",
            options("Starts from an arbitrary vertex and greedily adds the cheapest edge leaving the current tree", "Sorts all edges globally and picks them if no cycle is created", "Deletes maximum-weight edges repeatedly", "Uses breadth-first levels"),
            0, "Prim's algorithm grows a single tree component by picking the minimum-weight frontier edge at each step.");
        add(questions, "If all edge weights in a connected graph are unique, the MST is:",
            options("Unique", "Empty", "Ambiguous", "Dependent on the starting node"),
            0, "When all edge weights are strictly distinct, there is exactly one unique Minimum Spanning Tree.");
        return questions;
    }

    private List<TheoryQuestion> buildAvlTreeTheory(TechniqueContent content) {
        List<TheoryQuestion> questions = new ArrayList<>();
        String title = resolveTitle(content, "AVL Tree");
        add(questions, "What is an " + title + "?",
            options("A self-balancing Binary Search Tree where node heights differ by at most 1", "A complete binary tree stored in an array", "A B-tree with minimum degree 2", "A general tree where each node has up to 3 children"),
            0, "An AVL tree maintains height balance such that the balance factor of every node is -1, 0, or +1.");
        add(questions, "How is the Balance Factor (BF) of a node defined in an " + title + "?",
            options("Height(Left Subtree) - Height(Right Subtree)", "Number of left keys - number of right keys", "Depth of node - root depth", "Number of leaf nodes"),
            0, "Balance factor is defined as height(left) - height(right) (or right - left in some conventions).");
        add(questions, "What rotation fixes a Left-Right (LR) imbalance in an " + title + "?",
            options("Left rotation on left child, then Right rotation on root", "Single Right rotation", "Single Left rotation", "Right rotation on right child, then Left rotation on root"),
            0, "An LR case is converted to an LL case by a left rotation on the child, then resolved by a right rotation on the node.");
        add(questions, "What is the worst-case search, insert, and delete time complexity in an " + title + "?",
            options("O(log n)", "O(n)", "O(1)", "O(n log n)"),
            0, "Because height is strictly bounded by ~1.44 * log2(n), operations are guaranteed O(log n).");
        add(questions, "Compared to a Red-Black Tree, an " + title + " is:",
            options("More strictly balanced, making lookups slightly faster", "Less strictly balanced", "Not a binary search tree", "Slower on lookups"),
            0, "AVL trees enforce tighter height balance, giving faster lookups but slightly costlier rebalancing on writes.");
        return questions;
    }

    private List<TheoryQuestion> buildTrieTheory(TechniqueContent content) {
        List<TheoryQuestion> questions = new ArrayList<>();
        String title = resolveTitle(content, "Trie (Prefix Tree)");
        add(questions, "What is the primary advantage of a " + title + "?",
            options("Fast prefix-based lookups and string retrievals in O(L) time where L is word length", "Lower memory consumption than a hash table", "Supports numerical ranges in O(1)", "Guarantees balanced tree height of log n"),
            0, "Search time depends strictly on the length of the string L, completely independent of total words N.");
        add(questions, "What does each edge or pointer represent in a standard lowercase English " + title + "?",
            options("A character (often an array of size 26)", "A full word string", "A hash code of the string", "An integer index in memory"),
            0, "Children pointers correspond to individual characters, spelling words along the path from root to leaf.");
        add(questions, "What flag or field is needed in a " + title + " node to distinguish prefixes from full words?",
            options("A boolean isEndOfWord flag", "A character count field only", "A depth integer", "A parent pointer"),
            0, "An isEndOfWord flag marks whether the node concludes a valid registered word (e.g. 'car' vs 'carpet').");
        add(questions, "Which real-world feature is most commonly powered by a " + title + "?",
            options("Autocomplete, spell-check, and IP routing prefix lookup", "Memory defragmentation", "Sorting floating point arrays", "Database normalization"),
            0, "Autocomplete directly traverses the trie to the prefix node and enumerates descendants.");
        return questions;
    }

    private List<TheoryQuestion> buildHeapTheory(TechniqueContent content) {
        List<TheoryQuestion> questions = new ArrayList<>();
        String title = resolveTitle(content, "Binary Heap / Priority Queue");
        add(questions, "What structural property must a " + title + " satisfy?",
            options("Complete Binary Tree", "Full Binary Tree", "Perfect Binary Tree", "Degenerate Tree"),
            0, "A binary heap is a complete binary tree where all levels are fully filled except possibly the last from left to right.");
        add(questions, "For a 0-indexed array implementation of a heap, what is the parent index of node i?",
            options("(i - 1) / 2", "i / 2", "2 * i", "2 * i + 1"),
            0, "In a 0-indexed array, the parent of node i is always at floor((i - 1) / 2).");
        add(questions, "What invariant defines a Max-Heap?",
            options("Every parent node's value is greater than or equal to its children's values", "Every left child is smaller than every right child", "The root is the minimum element", "All leaves have equal values"),
            0, "In a Max-Heap, key(parent) >= key(child) throughout the entire tree, so the maximum is at the root.");
        add(questions, "What is the time complexity to insert into a binary heap of size n?",
            options("O(log n)", "O(1)", "O(n)", "O(n log n)"),
            0, "The element is added at the end and bubbled up along the tree height, taking at most O(log n) swaps.");
        add(questions, "What is the time complexity of the buildHeap / heapify operation for n elements?",
            options("O(n)", "O(n log n)", "O(n^2)", "O(log n)"),
            0, "Sifting down nodes bottom-up mathematically sums to O(n) total operations.");
        add(questions, "What is the time and auxiliary space complexity of Heap Sort?",
            options("O(n log n) time in all cases, O(1) auxiliary space", "O(n^2) time, O(n) space", "O(n log n) time, O(n) space", "O(n) time, O(1) space"),
            0, "Heap sort sorts in-place using O(1) memory with guaranteed O(n log n) time.");
        return questions;
    }

    private List<TheoryQuestion> buildLeetCodeTheory(String techniqueId, TechniqueContent content) {
        List<TheoryQuestion> questions = new ArrayList<>();
        String title = resolveTitle(content, prettifyTechniqueId(techniqueId));
        if ("leetcode_two_sum".equals(techniqueId)) {
            add(questions, "What is the optimal time and space complexity for Two Sum?",
                options("O(n) time and O(n) space using a Hash Map", "O(n^2) time and O(1) space", "O(n log n) time and O(n^2) space", "O(1) time and O(n) space"),
                0, "A Hash Map stores complement values (target - num) seen so far in a single pass.");
            add(questions, "Why does Two Sum look for target - current in the hash map?",
                options("To find whether the matching pair element was already visited", "To sort the input array", "To compute the average of numbers", "To prevent array indexing errors"),
                0, "If target - current exists in the map, the two indices add up to target.");
        } else if ("leetcode_best_time_stock".equals(techniqueId)) {
            add(questions, "What is the optimal approach for Best Time to Buy and Sell Stock (single transaction)?",
                options("Track the minimum price seen so far and maximum profit in a single pass O(n)", "Nested loops checking every buy-sell pair in O(n^2)", "Sort the array first O(n log n)", "Use a binary search tree"),
                0, "Keeping track of min_price and updating max_profit = max(max_profit, price - min_price) runs in O(n) time and O(1) space.");
            add(questions, "What is the auxiliary space complexity of the optimal stock buying algorithm?",
                options("O(1)", "O(n)", "O(log n)", "O(n^2)"),
                0, "It only requires two scalar tracking variables (minPrice and maxProfit).");
        } else if ("leetcode_linked_list_cycle".equals(techniqueId)) {
            add(questions, "Which algorithm detects a cycle in a linked list in O(n) time and O(1) space?",
                options("Floyd's Cycle-Finding Algorithm (Tortoise and Hare)", "Dijkstra's Algorithm", "Binary Search on nodes", "Kadane's Algorithm"),
                0, "Floyd's algorithm uses a slow pointer (1 step) and a fast pointer (2 steps).");
            add(questions, "Why are slow and fast pointers guaranteed to meet if a cycle exists?",
                options("The relative distance between them decreases by 1 on each step inside the loop", "Fast pointer stops at the cycle head", "Slow pointer reverses the list", "Both pointers jump randomly"),
                0, "Because fast moves 1 step faster than slow, it closes the loop distance by 1 unit every iteration.");
        } else if ("leetcode_invert_binary_tree".equals(techniqueId)) {
            add(questions, "How is a binary tree inverted (mirrored)?",
                options("Recursively or iteratively swap the left and right children of every node", "Reverse the in-order traversal array", "Invert the signs of all node values", "Make the root node a leaf"),
                0, "For each node, swap left and right subtrees and recurse on both children.");
            add(questions, "What is the time complexity to invert a binary tree with n nodes?",
                options("O(n)", "O(log n)", "O(n^2)", "O(1)"),
                0, "Every node in the tree must be visited and had its children swapped exactly once.");
        } else {
            add(questions, "What is the primary objective of " + title + "?",
                options("Solve the algorithmic challenge using optimal time and space trade-offs", "Write code without any variables", "Avoid using any data structures", "Always use recursion regardless of performance"),
                0, "Classic interview problems emphasize optimal algorithmic patterns such as two-pointers, hash tables, and divide-and-conquer.");
            add(questions, "Why is analyzing time and space complexity critical in " + title + "?",
                options("To ensure the solution scales effectively within CPU and memory constraints", "Because computers cannot run loops", "To reduce lines of comments", "Complexity analysis is optional"),
                0, "Big-O analysis ensures solutions satisfy time and memory limits for large test inputs.");
        }
        return questions;
    }

    private List<TheoryQuestion> buildGeneralTheory(String techniqueId, TechniqueContent content) {
        List<TheoryQuestion> questions = new ArrayList<>();
        String title = resolveTitle(content, prettifyTechniqueId(techniqueId));
        String definition = safe(content == null ? "" : content.definition, title + " is a DSA technique.");
        String best = safe(content == null ? "" : content.bestTime, "O(1)");
        String average = safe(content == null ? "" : content.averageTime, best);
        String worst = safe(content == null ? "" : content.worstTime, average);
        String space = safe(content == null ? "" : content.spaceComplexity, "O(1)");
        String family = resolveFamilyLabel(techniqueId);
        String useCase = resolveUseCase(techniqueId, title);
        String prerequisite = resolvePrerequisite(techniqueId);
        String stopCondition = resolveStopCondition(techniqueId);
        String coreIdea = resolveCoreIdea(techniqueId, title);

        add(questions, "What is " + title + " mainly used for?",
            options(useCase, "Balancing any tree automatically", "Encrypting input data", "Managing operating system processes"),
            0, definition);
        add(questions, title + " belongs to which DSA category?",
            options(family, "Computer networking", "Database normalization", "Compiler optimization"),
            0, title + " is primarily taught under the " + family + " category.");
        add(questions, "Which statement best describes " + title + "?",
            options(definition, "It is only a hardware concept", "It avoids all comparisons", "It can never be implemented iteratively"),
            0, definition);
        add(questions, "Worst-case time complexity of " + title + "?",
            options("O(1)", worst, "O(n^2) + O(log n)", "Not applicable"),
            1, "The content for this topic lists worst-case time as " + worst + ".");
        add(questions, "Best-case time complexity of " + title + "?",
            options(best, "O(n^2)", "O(n!)", "Undefined"),
            0, "The content for this topic lists best-case time as " + best + ".");
        add(questions, "Average-case time complexity of " + title + "?",
            options("O(1)", "O(log log n)", average, "O(n^3)"),
            2, "The content for this topic lists average-case time as " + average + ".");
        add(questions, "Space complexity of " + title + "?",
            options(space, "O(n^3)", "O(2^n)", "Not measurable"),
            0, "The content for this topic lists space complexity as " + space + ".");
        add(questions, "Which prerequisite best matches " + title + "?",
            options(prerequisite, "Must always use recursion", "Needs a GPU", "Works only on encrypted input"),
            0, prerequisite);
        add(questions, "Which stopping condition is most relevant for " + title + "?",
            options(stopCondition, "When the monitor refreshes", "When random memory is cleared", "When power is disconnected"),
            0, stopCondition);
        add(questions, "What is the core idea behind " + title + "?",
            options(coreIdea, "Avoid all traversal", "Always sort twice", "Use only constant zero values"),
            0, coreIdea);
        add(questions, "Which scenario best suits " + title + "?",
            options(resolvePreferredScenario(techniqueId), "Rendering a movie file", "Charging a battery", "Formatting a printer"),
            0, resolvePreferredScenario(techniqueId));
        add(questions, "Which statement is true about " + title + "?",
            options(resolveTrueStatement(techniqueId, title), "It never uses data comparison", "It belongs to operating systems, not DSA", "It cannot be explained step by step"),
            0, resolveTrueStatement(techniqueId, title));
        add(questions, "What should a learner focus on first in " + title + "?",
            options(resolveLearningFocus(techniqueId), "UI colors only", "Battery level", "Keyboard shortcuts"),
            0, resolveLearningFocus(techniqueId));
        add(questions, "Which output is commonly associated with " + title + "?",
            options(resolveTypicalOutput(techniqueId), "A compiled executable file", "A network packet", "A wallpaper theme"),
            0, resolveTypicalOutput(techniqueId));
        add(questions, "What usually makes " + title + " slower?",
            options(resolveSlowdownFactor(techniqueId), "Using a shorter variable name", "Changing the app theme", "Increasing screen brightness"),
            0, resolveSlowdownFactor(techniqueId));
        add(questions, "Which statement is false about " + title + "?",
            options(resolveFalseOption(techniqueId, title), title + " is a DSA topic", title + " can be reasoned about step by step", title + " has a definable time complexity"),
            0, resolveFalseOption(techniqueId, title));

        String[] pairs = {
            "definition", definition,
            "best case", best,
            "average case", average,
            "worst case", worst,
            "space", space,
            "family", family,
            "use case", useCase,
            "prerequisite", prerequisite,
            "stop condition", stopCondition,
            "core idea", coreIdea
        };
        for (int i = 0; i < pairs.length - 1; i += 2) {
            add(questions, "Which phrase matches the " + pairs[i] + " of " + title + "?",
                options(pairs[i + 1], "Completely unrelated hardware operation", "A random social-media feature", "An astronomy formula"),
                0, pairs[i].substring(0, 1).toUpperCase() + pairs[i].substring(1) + " for " + title + " is " + pairs[i + 1] + ".");
        }
        return questions;
    }

    private List<TheoryQuestion> dedupe(List<TheoryQuestion> source) {
        List<TheoryQuestion> result = new ArrayList<>();
        LinkedHashSet<String> seen = new LinkedHashSet<>();
        for (TheoryQuestion question : source) {
            String key = question.prompt + "|" + question.correctIndex + "|" + question.explanation;
            if (seen.add(key)) {
                result.add(question);
            }
        }
        return result;
    }

    private void add(List<TheoryQuestion> target, String prompt, List<String> options, int correctIndex, String explanation) {
        target.add(new TheoryQuestion(prompt, options, correctIndex, explanation));
    }

    private List<String> options(String a, String b, String c, String d) {
        return Arrays.asList(a, b, c, d);
    }

    private String resolveTitle(TechniqueContent content, String fallback) {
        if (content != null && content.title != null && !content.title.trim().isEmpty()) {
            return content.title;
        }
        return fallback;
    }

    private String prettifyTechniqueId(String techniqueId) {
        return techniqueId == null ? "Technique" : techniqueId.replace('_', ' ');
    }

    private String safe(String value, String fallback) {
        return value == null || value.trim().isEmpty() ? fallback : value.trim();
    }

    private String resolveFamilyLabel(String techniqueId) {
        if (VisualizationTechniqueRegistry.isSearching(techniqueId)) return "Searching";
        if (VisualizationTechniqueRegistry.isSorting(techniqueId)) return "Sorting";
        if (VisualizationTechniqueRegistry.isStructure(techniqueId)) return "Linear Data Structures";
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) return "Trees";
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) return "Graphs";
        return "DSA";
    }

    private String resolveUseCase(String techniqueId, String title) {
        if (VisualizationTechniqueRegistry.isSearching(techniqueId)) return "Finding a target element or state";
        if (VisualizationTechniqueRegistry.isSorting(techniqueId)) return "Reordering data into a required sequence";
        if ("stack_basics".equals(techniqueId) || techniqueId.contains("stack")) return "Managing LIFO operations";
        if ("queue_basics".equals(techniqueId) || techniqueId.contains("queue") || "deque".equals(techniqueId)) return "Managing FIFO or double-ended processing";
        if (VisualizationTechniqueRegistry.isStructure(techniqueId)) return "Storing and updating linked or indexed data relationships";
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) return "Hierarchical traversal or ordered tree updates";
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) return "Connectivity, traversal, or path reasoning";
        return "Solving a classic data-structure or algorithm problem using " + title;
    }

    private String resolvePrerequisite(String techniqueId) {
        if ("binary_search".equals(techniqueId) || "jump_search".equals(techniqueId) || "topological_sort".equals(techniqueId)) {
            return "The input must satisfy its ordering or dependency requirement";
        }
        if (VisualizationTechniqueRegistry.isSorting(techniqueId)) {
            return "A collection of comparable values is required";
        }
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) {
            return "A graph representation such as adjacency data is required";
        }
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) {
            return "A valid tree or heap structure is required";
        }
        return "A traversable data structure and a clear operation rule are required";
    }

    private String resolveStopCondition(String techniqueId) {
        if (VisualizationTechniqueRegistry.isSearching(techniqueId)) {
            return "Stop when the target is found or the searchable range ends";
        }
        if (VisualizationTechniqueRegistry.isSorting(techniqueId)) {
            return "Stop when all required ordering passes are complete";
        }
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) {
            return "Stop when the frontier or pending work becomes empty";
        }
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) {
            return "Stop when traversal or structural repair reaches completion";
        }
        return "Stop when the intended operation is fully completed";
    }

    private String resolveCoreIdea(String techniqueId, String title) {
        if (VisualizationTechniqueRegistry.isSearching(techniqueId)) return "Search by following the rule that narrows or advances the check";
        if (VisualizationTechniqueRegistry.isSorting(techniqueId)) return "Repeatedly compare and reposition values until order is achieved";
        if ("stack_basics".equals(techniqueId) || techniqueId.contains("stack")) return "Operate on the most recently added item first";
        if ("queue_basics".equals(techniqueId) || techniqueId.contains("queue") || "deque".equals(techniqueId)) return "Process elements from the front or the chosen end in order";
        if (VisualizationTechniqueRegistry.isStructure(techniqueId)) return "Update links or positions while preserving structure rules";
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) return "Move through parent-child relationships according to a traversal or update rule";
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) return "Explore nodes and edges while maintaining frontier or path information";
        return "Understand the rule-based step sequence of " + title;
    }

    private String resolvePreferredScenario(String techniqueId) {
        if ("linear_search".equals(techniqueId)) return "Small or unsorted data collections";
        if ("binary_search".equals(techniqueId)) return "Large sorted collections";
        if (VisualizationTechniqueRegistry.isSorting(techniqueId)) return "When data needs to be arranged into order";
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) return "When relationships between connected nodes matter";
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) return "When hierarchical structure matters";
        return "When this topic’s operation rule matches the problem structure";
    }

    private String resolveTrueStatement(String techniqueId, String title) {
        if (VisualizationTechniqueRegistry.isSearching(techniqueId)) return title + " uses comparisons to guide search progress";
        if (VisualizationTechniqueRegistry.isSorting(techniqueId)) return title + " is about establishing a desired order";
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) return title + " depends on node-edge relationships";
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) return title + " uses parent-child relationships";
        return title + " can be studied using definitions, complexity, and operation rules";
    }

    private String resolveLearningFocus(String techniqueId) {
        if (VisualizationTechniqueRegistry.isSearching(techniqueId)) return "Targets, comparisons, and stopping conditions";
        if (VisualizationTechniqueRegistry.isSorting(techniqueId)) return "Comparisons, swaps, and ordering invariants";
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) return "Frontier, visited state, and edge meaning";
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) return "Traversal order and parent-child logic";
        return "The key operation rule and why each step happens";
    }

    private String resolveTypicalOutput(String techniqueId) {
        if (VisualizationTechniqueRegistry.isSearching(techniqueId)) return "A found position, match result, or failure result";
        if (VisualizationTechniqueRegistry.isSorting(techniqueId)) return "A reordered sequence";
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) return "Traversal order, reachability, or shortest-path information";
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) return "Traversal order or a valid updated tree";
        return "A valid result that follows the topic’s rule";
    }

    private String resolveSlowdownFactor(String techniqueId) {
        if (VisualizationTechniqueRegistry.isSearching(techniqueId)) return "A larger search space or weaker ordering advantage";
        if (VisualizationTechniqueRegistry.isSorting(techniqueId)) return "More values or more disorder";
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) return "More nodes, edges, or pending frontier work";
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) return "More nodes or more structural work";
        return "Larger input size or more steps to complete the rule";
    }

    private String resolveFalseOption(String techniqueId, String title) {
        if (VisualizationTechniqueRegistry.isSearching(techniqueId)) return title + " always works in O(1) time";
        if (VisualizationTechniqueRegistry.isSorting(techniqueId)) return title + " never compares values";
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) return title + " ignores node relationships";
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) return title + " has no parent-child logic";
        return title + " has no definable behavior";
    }

    private String resolveRotatingCorrect(int number, String title, String family, String coreIdea, String useCase) {
        switch (number % 4) {
            case 0:
                return title + " belongs to the " + family + " family";
            case 1:
                return coreIdea;
            case 2:
                return useCase;
            default:
                return "Understanding " + title + " involves both concept and step-by-step reasoning";
        }
    }
}
