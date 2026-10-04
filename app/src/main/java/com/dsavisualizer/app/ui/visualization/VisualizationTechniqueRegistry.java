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
        "pattern_matching",
        "leetcode_two_sum",
        "leetcode_binary_search",
        "leetcode_best_time_stock"
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
        "leetcode_valid_parentheses",
        "leetcode_reverse_linked_list",
        "leetcode_merge_two_sorted_lists",
        "leetcode_linked_list_cycle"
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
        "trie",
        "leetcode_invert_binary_tree"
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
        "spanning_tree",
        "graph_bellman_ford",
        "graph_floyd_warshall",
        "graph_prim",
        "graph_kruskal",
        "graph_tarjan_scc",
        "graph_kosaraju_scc",
        "graph_bridges_articulation",
        "graph_astar",
        "graph_edmonds_karp",
        "graph_johnson"
    ));

    private static final Set<String> DYNAMIC_PROGRAMMING = new HashSet<>(Arrays.asList(
        "dp_knapsack",
        "dp_lcs",
        "dp_lis",
        "dp_coin_change",
        "dp_matrix_chain",
        "dp_edit_distance",
        "dp_rod_cutting",
        "dp_subset_sum",
        "dp_kadane"
    ));

    private static final Set<String> BACKTRACKING = new HashSet<>(Arrays.asList(
        "backtracking_n_queens", "bt_n_queens",
        "backtracking_sudoku", "bt_sudoku",
        "backtracking_knights_tour", "bt_knights_tour",
        "backtracking_maze_solver", "bt_maze",
        "backtracking_hamiltonian", "bt_hamiltonian",
        "backtracking_graph_coloring", "bt_graph_coloring"
    ));

    private static final Set<String> STRING_PATTERN = new HashSet<>(Arrays.asList(
        "string_kmp",
        "string_rabin_karp",
        "string_boyer_moore",
        "string_z_algorithm", "string_z_algo",
        "string_aho_corasick",
        "string_suffix_array",
        "string_manacher"
    ));

    private static final Set<String> ADVANCED_DS = new HashSet<>(Arrays.asList(
        "ds_trie", "adv_trie",
        "ds_segment_tree", "adv_segment_tree",
        "ds_persistent_segment_tree", "adv_persistent_st",
        "ds_fenwick_tree", "adv_fenwick_tree",
        "ds_avl_tree", "adv_avl_tree",
        "ds_red_black_tree", "adv_red_black_tree",
        "ds_splay_tree", "adv_splay_tree",
        "ds_b_tree", "adv_b_tree",
        "ds_disjoint_set", "adv_dsu",
        "ds_bloom_filter", "adv_bloom_filter",
        "ds_lru_cache", "adv_lru_cache"
    ));

    private static final Set<String> SPECIALIZED = new HashSet<>(Arrays.asList(
        "spec_convex_hull",
        "spec_closest_pair",
        "spec_line_intersection",
        "spec_fft", "spec_fast_fourier_transform",
        "spec_karatsuba",
        "spec_sieve", "spec_sieve_eratosthenes",
        "spec_miller_rabin",
        "spec_extended_euclidean", "spec_euclidean_gcd",
        "spec_crt", "spec_chinese_remainder",
        "spec_activity_selection"
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

    public static boolean isDynamicProgramming(String techniqueId) {
        return DYNAMIC_PROGRAMMING.contains(techniqueId);
    }

    public static boolean isBacktracking(String techniqueId) {
        return BACKTRACKING.contains(techniqueId);
    }

    public static boolean isStringPattern(String techniqueId) {
        return STRING_PATTERN.contains(techniqueId);
    }

    public static boolean isAdvancedDs(String techniqueId) {
        return ADVANCED_DS.contains(techniqueId);
    }

    public static boolean isSpecialized(String techniqueId) {
        return SPECIALIZED.contains(techniqueId);
    }

    public static boolean isTreeOrGraph(String techniqueId) {
        return isTree(techniqueId) || isGraph(techniqueId);
    }

    public static boolean needsTreeTargetInput(String techniqueId) {
        return TREE_TARGET_INPUT.contains(techniqueId);
    }

    public static boolean isPatternMatching(String techniqueId) {
        return "pattern_matching".equals(techniqueId) || isStringPattern(techniqueId);
    }
}
