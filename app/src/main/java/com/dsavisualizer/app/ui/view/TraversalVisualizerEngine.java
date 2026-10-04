package com.dsavisualizer.app.ui.view;

import android.graphics.Canvas;

/**
 * Trees and graphs are spatial traversals rather than linear tracks, so they
 * stay together in a dedicated traversal engine.
 */
final class TraversalVisualizerEngine implements VisualizerEngine {

    @Override
    public boolean supports(String techniqueId) {
        return "tree_traversal".equals(techniqueId)
            || "preorder_traversal".equals(techniqueId)
            || "postorder_traversal".equals(techniqueId)
            || "bst_search_parent_tracking".equals(techniqueId)
            || "bst_insertion".equals(techniqueId)
            || "bst_deletion_case_a".equals(techniqueId)
            || "bst_deletion_case_b".equals(techniqueId)
            || "bst_deletion_del".equals(techniqueId)
            || "max_heap_insertion".equals(techniqueId)
            || "max_heap_deletion".equals(techniqueId)
            || "traversal_using_stacks".equals(techniqueId)
            || "huffman_algorithm".equals(techniqueId)
            || "avl_tree".equals(techniqueId)
            || "trie".equals(techniqueId)
            || "graph_traversal".equals(techniqueId)
            || "warshall_algorithm".equals(techniqueId)
            || "floyd_algorithm".equals(techniqueId)
            || "shortest_paths".equals(techniqueId)
            || "dijkstra_algorithm".equals(techniqueId)
            || "find_edge_graph".equals(techniqueId)
            || "insert_node_graph".equals(techniqueId)
            || "insert_edge_graph".equals(techniqueId)
            || "delete_edge_graph".equals(techniqueId)
            || "delete_vertex_graph".equals(techniqueId)
            || "breadth_first_search".equals(techniqueId)
            || "depth_first_search".equals(techniqueId)
            || "topological_sort".equals(techniqueId)
            || "spanning_tree".equals(techniqueId)
            || "leetcode_invert_binary_tree".equals(techniqueId)
            || "graph_bellman_ford".equals(techniqueId)
            || "graph_floyd_warshall".equals(techniqueId)
            || "graph_prim".equals(techniqueId)
            || "graph_kruskal".equals(techniqueId)
            || "graph_tarjan_scc".equals(techniqueId)
            || "graph_kosaraju_scc".equals(techniqueId)
            || "graph_bridges_articulation".equals(techniqueId)
            || "graph_astar".equals(techniqueId)
            || "graph_edmonds_karp".equals(techniqueId)
            || "graph_johnson".equals(techniqueId);
    }

    @Override
    public void draw(Canvas canvas, TechniqueVisualizerView view, String techniqueId) {
        if ("tree_traversal".equals(techniqueId)
            || "preorder_traversal".equals(techniqueId)
            || "postorder_traversal".equals(techniqueId)
            || "bst_search_parent_tracking".equals(techniqueId)
            || "bst_insertion".equals(techniqueId)
            || "bst_deletion_case_a".equals(techniqueId)
            || "bst_deletion_case_b".equals(techniqueId)
            || "bst_deletion_del".equals(techniqueId)
            || "max_heap_insertion".equals(techniqueId)
            || "max_heap_deletion".equals(techniqueId)
            || "leetcode_invert_binary_tree".equals(techniqueId)) {
            view.drawHeapTree(canvas);
            return;
        }
        if ("huffman_algorithm".equals(techniqueId)) {
            view.drawHuffmanScene(canvas);
            return;
        }
        if ("avl_tree".equals(techniqueId)) {
            view.drawAvlScene(canvas);
            return;
        }
        if ("trie".equals(techniqueId)) {
            view.drawTrieScene(canvas);
            return;
        }
        if ("traversal_using_stacks".equals(techniqueId)) {
            view.drawTree(canvas);
            return;
        }
        if ("warshall_algorithm".equals(techniqueId)
            || "floyd_algorithm".equals(techniqueId)
            || "shortest_paths".equals(techniqueId)
            || "graph_floyd_warshall".equals(techniqueId)
            || "graph_johnson".equals(techniqueId)) {
            view.drawMatrixScene(canvas);
            return;
        }
        if ("graph_astar".equals(techniqueId) || "graph_a_star".equals(techniqueId)) {
            view.drawAStarScene(canvas);
            return;
        }
        if ("dijkstra_algorithm".equals(techniqueId)
            || "graph_bellman_ford".equals(techniqueId)) {
            view.drawDijkstraScene(canvas);
            return;
        }
        view.drawGraph(canvas);
    }

    @Override
    public int resolveDesiredWidth(TechniqueVisualizerView view, String techniqueId) {
        return view.resolveScreenWidthDesiredWidth();
    }

    @Override
    public int resolveDesiredHeight(TechniqueVisualizerView view, String techniqueId) {
        if ("warshall_algorithm".equals(techniqueId)
            || "floyd_algorithm".equals(techniqueId)
            || "shortest_paths".equals(techniqueId)
            || "graph_floyd_warshall".equals(techniqueId)
            || "graph_johnson".equals(techniqueId)) {
            return Math.max(view.resolveTraversalDesiredHeight(), 540);
        }
        if ("dijkstra_algorithm".equals(techniqueId)
            || "graph_bellman_ford".equals(techniqueId)
            || "graph_astar".equals(techniqueId)) {
            return Math.max(view.resolveTraversalDesiredHeight(), 620);
        }
        if ("huffman_algorithm".equals(techniqueId)) {
            return Math.max(view.resolveTraversalDesiredHeight(), 640);
        }
        if ("avl_tree".equals(techniqueId) || "trie".equals(techniqueId)) {
            return Math.max(view.resolveTraversalDesiredHeight(), 460);
        }
        return view.resolveTraversalDesiredHeight();
    }
}
