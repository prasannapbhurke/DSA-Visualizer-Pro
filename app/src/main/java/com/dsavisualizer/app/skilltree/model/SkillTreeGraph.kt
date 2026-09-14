package com.dsavisualizer.app.skilltree.model

import android.content.Context
import com.dsavisualizer.app.data.AppPreferences

object SkillTreeGraph {

    fun getBaseNodes(): List<SkillNode> = listOf(
        // TIER 1: Foundations
        SkillNode(
            id = "node_arrays",
            title = "Arrays & Strings",
            categoryId = "arrays_strings",
            techniqueId = "pattern_matching",
            description = "Contiguous memory, indexing, two-pointer methods, and string matching algorithms.",
            tier = 1,
            categoryTitle = "String & Array Algorithms",
            iconName = "code"
        ),
        SkillNode(
            id = "node_recursion",
            title = "Recursion Basics",
            categoryId = "stack_queue_recursion",
            techniqueId = "hanoi",
            description = "Call stacks, base cases, recurrence relations, and the Tower of Hanoi.",
            tier = 1,
            categoryTitle = "Stack Queue Recursion",
            iconName = "repeat"
        ),
        SkillNode(
            id = "node_basic_sorting",
            title = "Elementary Sorts",
            categoryId = "sorting",
            techniqueId = "bubble_sort",
            description = "Comparison fundamentals: Bubble Sort, Selection Sort, and Insertion Sort.",
            tier = 1,
            categoryTitle = "Sorting Algorithms",
            iconName = "sort"
        ),

        // TIER 2: Linear Data Structures & Search
        SkillNode(
            id = "node_linked_lists",
            title = "Linked Structures",
            categoryId = "linked_structures",
            techniqueId = "singly_list",
            description = "Node pointers, singly/doubly linked lists, dynamic allocation without array resizing.",
            tier = 2,
            prerequisiteIds = listOf("node_arrays"),
            categoryTitle = "Linked Structures",
            iconName = "link"
        ),
        SkillNode(
            id = "node_stacks_queues",
            title = "Stacks & Queues",
            categoryId = "stack_queue_recursion",
            techniqueId = "stack",
            description = "LIFO/FIFO discipline, postfix evaluation, double-ended queues, and priority queues.",
            tier = 2,
            prerequisiteIds = listOf("node_arrays"),
            categoryTitle = "Stack Queue Recursion",
            iconName = "layers"
        ),
        SkillNode(
            id = "node_binary_search",
            title = "Binary Search",
            categoryId = "searching",
            techniqueId = "binary_search",
            description = "Logarithmic divide-and-conquer on sorted spaces. O(log N) search boundaries.",
            tier = 2,
            prerequisiteIds = listOf("node_arrays", "node_basic_sorting"),
            categoryTitle = "Searching Algorithms",
            iconName = "search"
        ),
        SkillNode(
            id = "node_divide_conquer_sorts",
            title = "Fast Sorting",
            categoryId = "sorting",
            techniqueId = "quick_sort",
            description = "O(N log N) divide-and-conquer algorithms: Quick Sort, Merge Sort, and Radix Sort.",
            tier = 2,
            prerequisiteIds = listOf("node_basic_sorting", "node_recursion"),
            categoryTitle = "Sorting Algorithms",
            iconName = "zap"
        ),

        // TIER 3: Hierarchical Structures & Hashing
        SkillNode(
            id = "node_binary_trees",
            title = "Binary Trees",
            categoryId = "trees",
            techniqueId = "traverse_trees",
            description = "Hierarchical node branches, Inorder, Preorder, Postorder traversals with recursion and stacks.",
            tier = 3,
            prerequisiteIds = listOf("node_linked_lists", "node_recursion"),
            categoryTitle = "Trees",
            iconName = "git-branch"
        ),
        SkillNode(
            id = "node_bst",
            title = "Binary Search Trees",
            categoryId = "searching",
            techniqueId = "bsts",
            description = "Ordered trees enabling O(h) insertion, searching, and predecessor/successor deletion.",
            tier = 3,
            prerequisiteIds = listOf("node_binary_trees", "node_binary_search"),
            categoryTitle = "Searching Algorithms",
            iconName = "tree"
        ),
        SkillNode(
            id = "node_hashing",
            title = "Hashing & Maps",
            categoryId = "searching",
            techniqueId = "hashing",
            description = "Hash functions, load factor, separate chaining, and open addressing collision resolution.",
            tier = 3,
            prerequisiteIds = listOf("node_stacks_queues"),
            categoryTitle = "Searching Algorithms",
            iconName = "hash"
        ),
        SkillNode(
            id = "node_heaps",
            title = "Heaps & Huffman",
            categoryId = "trees",
            techniqueId = "huffman",
            description = "Binary heaps, priority queues, and greedy prefix trees for Huffman encoding.",
            tier = 3,
            prerequisiteIds = listOf("node_divide_conquer_sorts", "node_binary_trees"),
            categoryTitle = "Trees",
            iconName = "cpu"
        ),

        // TIER 4: Networks & Graph Traversal
        SkillNode(
            id = "node_graphs_traversal",
            title = "Graph Traversals",
            categoryId = "graphs",
            techniqueId = "graph_traversal",
            description = "Adjacency lists/matrices, Breadth-First Search (BFS) and Depth-First Search (DFS).",
            tier = 4,
            prerequisiteIds = listOf("node_binary_trees", "node_stacks_queues"),
            categoryTitle = "Graphs",
            iconName = "share-2"
        ),
        SkillNode(
            id = "node_shortest_paths",
            title = "Shortest Paths",
            categoryId = "graphs",
            techniqueId = "warshall",
            description = "Single-source Dijkstra's algorithm and all-pairs Floyd-Warshall dynamic matrix paths.",
            tier = 4,
            prerequisiteIds = listOf("node_graphs_traversal", "node_heaps"),
            categoryTitle = "Graphs",
            iconName = "navigation"
        ),
        SkillNode(
            id = "node_topological_sort",
            title = "Topological Sort",
            categoryId = "graphs",
            techniqueId = "topological_sort",
            description = "Directed Acyclic Graph (DAG) task scheduling, dependency ordering, and Kahn's algorithm.",
            tier = 4,
            prerequisiteIds = listOf("node_graphs_traversal"),
            categoryTitle = "Graphs",
            iconName = "check-circle"
        ),
        SkillNode(
            id = "node_spanning_trees",
            title = "Spanning Trees",
            categoryId = "graphs",
            techniqueId = "spanning_tree",
            description = "Greedy minimum spanning tree construction via Prim's and Kruskal's algorithms.",
            tier = 4,
            prerequisiteIds = listOf("node_graphs_traversal"),
            categoryTitle = "Graphs",
            iconName = "minimize-2"
        ),

        // TIER 5: Advanced Optimization
        SkillNode(
            id = "node_avl_trees",
            title = "Balanced AVL Trees",
            categoryId = "trees",
            techniqueId = "traverse_trees",
            description = "Self-balancing binary search trees using single and double rotations. Strict O(log N).",
            tier = 5,
            prerequisiteIds = listOf("node_bst"),
            categoryTitle = "Trees",
            iconName = "shield"
        ),
        SkillNode(
            id = "node_dynamic_programming",
            title = "Dynamic Programming",
            categoryId = "graphs",
            techniqueId = "warshall",
            description = "Optimal substructure, overlapping subproblems, memoization tables, and bottom-up DP.",
            tier = 5,
            prerequisiteIds = listOf("node_recursion", "node_shortest_paths"),
            categoryTitle = "Advanced Optimization",
            iconName = "award"
        )
    )

    fun computeSkillTree(context: Context): List<SkillNode> {
        val prefs = AppPreferences(context)
        val visited = prefs.visitedTopics
        val baseNodes = getBaseNodes()

        // 1st pass: identify completed nodes
        val completedIds = mutableSetOf<String>()
        for (node in baseNodes) {
            val isTechVisited = node.techniqueId != null && visited.contains(node.techniqueId)
            val isCatVisited = visited.contains(node.categoryId)
            if (isTechVisited || isCatVisited) {
                completedIds.add(node.id)
            }
        }

        // 2nd pass: determine status for each node
        return baseNodes.map { node ->
            val status = when {
                completedIds.contains(node.id) -> SkillStatus.COMPLETED
                node.prerequisiteIds.isEmpty() || node.prerequisiteIds.all { completedIds.contains(it) } -> SkillStatus.AVAILABLE
                else -> SkillStatus.LOCKED
            }
            node.copy(status = status)
        }
    }
}
