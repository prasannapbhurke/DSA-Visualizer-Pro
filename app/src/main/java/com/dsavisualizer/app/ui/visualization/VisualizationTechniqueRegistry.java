package com.dsavisualizer.app.ui.visualization;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Central technique-family registry for visualization routing.
 * This removes large technique-id checks from the Fragment.
 */
public final class VisualizationTechniqueRegistry {

    private static final Set<String> SEARCHING = new HashSet<>(Arrays.asList(
        "linear_search",
        "binary_search",
        "jump_search",
        "pattern_matching"
    ));

    private static final Set<String> SORTING = new HashSet<>(Arrays.asList(
        "bubble_sort",
        "selection_sort",
        "insertion_sort",
        "merging",
        "merge_sort",
        "radix_sort",
        "quicksort",
        "heapsort"
    ));

    private static final Set<String> STRUCTURES = new HashSet<>(Arrays.asList(
        "stack_basics",
        "linked_stack_push",
        "linked_stack_pop",
        "postfix_evaluation",
        "infix_to_postfix",
        "infix_to_prefix",
        "queue_basics",
        "circular_queue",
        "linked_queue",
        "priority_queue_array",
        "priority_queue_2d",
        "deque",
        "singly_linked_list",
        "traversing_linked_list",
        "print_linked_list",
        "count_nodes_linked_list",
        "search_in_linked_list",
        "find_linked_list",
        "insert_at_beginning_linked_list",
        "insert_after_given_node_linked_list",
        "insert_into_sorted_linked_list",
        "delete_node_given_location_linked_list",
        "delete_node_by_item_linked_list",
        "delete_linked_list",
        "traverse_circular_header_list",
        "search_circular_header_list",
        "delete_circular_header_list",
        "union_find",
        "hash_collision_handling",
        "recursion_basics",
        "dynamic_programming_basics"
    ));

    private static final Set<String> TREES = new HashSet<>(Arrays.asList(
        "tree_traversal",
        "preorder_traversal",
        "postorder_traversal",
        "bst_search_parent_tracking",
        "bst_insertion",
        "bst_deletion_case_a",
        "bst_deletion_case_b",
        "bst_deletion_del",
        "max_heap_insertion",
        "max_heap_deletion",
        "traversal_using_stacks",
        "huffman_algorithm",
        "avl_tree",
        "trie"
    ));

    private static final Set<String> GRAPHS = new HashSet<>(Arrays.asList(
        "graph_traversal",
        "warshall_algorithm",
        "floyd_algorithm",
        "shortest_paths",
        "dijkstra_algorithm",
        "find_edge_graph",
        "insert_node_graph",
        "insert_edge_graph",
        "delete_edge_graph",
        "delete_vertex_graph",
        "breadth_first_search",
        "depth_first_search",
        "topological_sort",
        "spanning_tree"
    ));

    private static final Set<String> TREE_TARGET_INPUT = new HashSet<>(Arrays.asList(
        "bst_search_parent_tracking",
        "bst_insertion",
        "bst_deletion_case_a",
        "bst_deletion_case_b",
        "bst_deletion_del",
        "max_heap_insertion",
        "max_heap_deletion"
    ));

    private VisualizationTechniqueRegistry() {
    }

    public static boolean isSearching(String techniqueId) {
        return SEARCHING.contains(techniqueId);
    }

    public static boolean isSorting(String techniqueId) {
        return SORTING.contains(techniqueId);
    }

    public static boolean isStructure(String techniqueId) {
        return STRUCTURES.contains(techniqueId);
    }

    public static boolean isTree(String techniqueId) {
        return TREES.contains(techniqueId);
    }

    public static boolean isGraph(String techniqueId) {
        return GRAPHS.contains(techniqueId);
    }

    public static boolean isTreeOrGraph(String techniqueId) {
        return isTree(techniqueId) || isGraph(techniqueId);
    }

    public static boolean needsTreeTargetInput(String techniqueId) {
        return TREE_TARGET_INPUT.contains(techniqueId);
    }

    public static boolean isPatternMatching(String techniqueId) {
        return "pattern_matching".equals(techniqueId);
    }
}
