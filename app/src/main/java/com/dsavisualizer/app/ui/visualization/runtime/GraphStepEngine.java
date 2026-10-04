package com.dsavisualizer.app.ui.visualization.runtime;

public final class GraphStepEngine extends BaseStepEngine {
    private static final int[] FIND_EDGE = {1, 4};
    private static final int[] INSERT_EDGE = {0, 4};
    private static final int[] DELETE_EDGE = {1, 3};

    public GraphStepEngine(VisualizationRuntimeHost host) {
        super(host);
    }

    @Override
    public Step next() {
        if (host == null) {
            return Step.idle(true, false);
        }
        advance(host);
        host.captureCurrentSnapshot();
        return Step.moved(false, host.isPlaybackComplete());
    }

    public static void advance(VisualizationRuntimeHost host) {
        String techniqueId = host.getTechniqueId();
        if ("warshall_algorithm".equals(techniqueId)
            || "floyd_algorithm".equals(techniqueId)
            || "shortest_paths".equals(techniqueId)) {
            MatrixAlgorithmEngine.advance(host);
        } else if ("dijkstra_algorithm".equals(techniqueId)) {
            DijkstraStepEngine.advance(host);
        } else {
            advanceTraversalStep(host);
        }
    }

    public static void advanceTraversalStep(VisualizationRuntimeHost host) {
        String techniqueId = host.getTechniqueId();
        if (isGraphOperationTechnique(techniqueId)) {
            advanceGraphOperationStep(host);
            return;
        }
        int[] traversalOrder = host.getTraversalOrder();
        if (traversalOrder.length == 0) {
            traversalOrder = host.buildTraversalOrder();
            host.setTraversalOrder(traversalOrder);
        }
        host.setStepIndex(host.getStepIndex() + 1);
        int stepIndex = host.getStepIndex();
        if (stepIndex >= traversalOrder.length) {
            host.finish("Traversal completed");
            return;
        }

        host.updateTraversalVisualization(traversalOrder, stepIndex);
        host.setHighlightedLines(Math.min(stepIndex + 1, 5), Math.min(stepIndex, 3));
        if (stepIndex > 0) {
            host.setActiveEdge(traversalOrder[stepIndex - 1], traversalOrder[stepIndex]);
        } else {
            host.clearActiveEdge();
        }
        int currentNode = traversalOrder[stepIndex];
        if ("find_edge_graph".equals(techniqueId)) {
            host.bindCurrentState("Checking whether the requested edge touches node " + currentNode + ".");
            return;
        }
        if ("insert_node_graph".equals(techniqueId)) {
            if (stepIndex == traversalOrder.length - 1) {
                host.bindCurrentState("Reached the end of the node list. Insert the new node here.");
            } else {
                host.bindCurrentState("Scanning node " + currentNode + " before inserting the new graph node.");
            }
            return;
        }
        if ("insert_edge_graph".equals(techniqueId)) {
            host.bindCurrentState("Locating the source and destination around node " + currentNode + " before adding the edge.");
            return;
        }
        if ("delete_edge_graph".equals(techniqueId)) {
            host.bindCurrentState("Scanning adjacency around node " + currentNode + " to remove the requested edge.");
            return;
        }
        if ("delete_vertex_graph".equals(techniqueId)) {
            host.bindCurrentState("Checking node " + currentNode + " while removing the requested vertex and its incident edges.");
            return;
        }
        if ("spanning_tree".equals(techniqueId)) {
            host.bindCurrentState("Add graph node " + currentNode + " through a safe edge so the spanning tree stays cycle-free.");
            return;
        }
        if ("topological_sort".equals(techniqueId)) {
            host.bindCurrentState("Place node " + currentNode + " only after its prerequisite edges are satisfied.");
            return;
        }
        if ("depth_first_search".equals(techniqueId) || "DFS".equals(host.getTraversalMode())) {
            host.bindCurrentState("Visit graph node " + currentNode + " and continue deeper before backtracking.");
            return;
        }
        host.bindCurrentState("Visit graph node " + currentNode + " and then expand its nearest neighbors.");
    }

    private static void advanceGraphOperationStep(VisualizationRuntimeHost host) {
        String techniqueId = host.getTechniqueId();
        int[] values = host.getWorkingValues();
        if (values.length == 0) {
            host.finish("Graph has no nodes to visualize.");
            return;
        }

        if ("insert_node_graph".equals(techniqueId)) {
            advanceInsertNode(host, values);
            return;
        }
        if ("delete_vertex_graph".equals(techniqueId)) {
            advanceDeleteVertex(host, values);
            return;
        }
        if ("find_edge_graph".equals(techniqueId)) {
            advanceEdgeOperation(host, values, FIND_EDGE, "Checking whether edge ", "Edge found: ");
            return;
        }
        if ("insert_edge_graph".equals(techniqueId)) {
            advanceEdgeOperation(host, values, INSERT_EDGE, "Locate endpoints before inserting edge ", "Inserted edge ");
            return;
        }
        if ("delete_edge_graph".equals(techniqueId)) {
            advanceEdgeOperation(host, values, DELETE_EDGE, "Locate endpoints before deleting edge ", "Deleted edge ");
        }
    }

    private static void advanceInsertNode(VisualizationRuntimeHost host, int[] values) {
        int targetValue = resolveGraphNodeValue(host, values);
        host.setStepIndex(host.getStepIndex() + 1);
        int stepIndex = host.getStepIndex();
        if (stepIndex < values.length) {
            host.updateTraversalVisualization(buildLinearOrder(values.length), stepIndex);
            host.setHighlightedLines(Math.min(stepIndex + 1, 4), Math.min(stepIndex, 3));
            host.clearActiveEdge();
            host.bindCurrentState("Scanning node " + values[stepIndex] + " before appending new node " + targetValue + ".");
            return;
        }

        int[] updated = appendValue(values, targetValue);
        host.setWorkingValues(updated);
        int[] order = buildLinearOrder(updated.length);
        host.setTraversalOrder(order);
        host.updateTraversalVisualization(order, updated.length - 1);
        host.setHighlightedLines(4, 2);
        host.clearActiveEdge();
        host.bindCurrentState("Inserted node " + targetValue + " into the graph.");
        host.finish("Inserted node " + targetValue + " into the graph.");
    }

    private static void advanceDeleteVertex(VisualizationRuntimeHost host, int[] values) {
        int deleteIndex = resolveDeleteIndex(host, values);
        host.setStepIndex(host.getStepIndex() + 1);
        int stepIndex = host.getStepIndex();
        if (stepIndex < deleteIndex) {
            host.updateTraversalVisualization(buildLinearOrder(values.length), stepIndex);
            host.setHighlightedLines(Math.min(stepIndex + 1, 4), Math.min(stepIndex, 3));
            host.clearActiveEdge();
            host.bindCurrentState("Scanning node " + values[stepIndex] + " while locating the vertex to delete.");
            return;
        }
        if (stepIndex == deleteIndex) {
            host.updateTraversalVisualization(buildLinearOrder(values.length), deleteIndex);
            host.setHighlightedLines(4, 2);
            host.clearActiveEdge();
            host.bindCurrentState("Found vertex " + values[deleteIndex] + ". Remove its incident edges next.");
            return;
        }

        int removedValue = values[deleteIndex];
        int[] updated = removeAt(values, deleteIndex);
        host.setWorkingValues(updated);
        int[] order = buildLinearOrder(updated.length);
        host.setTraversalOrder(order);
        host.updateTraversalVisualization(order, Math.max(0, Math.min(deleteIndex, updated.length - 1)));
        host.setHighlightedLines(5, 3);
        host.clearActiveEdge();
        host.bindCurrentState("Deleted vertex " + removedValue + " and removed its connected edges.");
        host.finish("Deleted vertex " + removedValue + " from the graph.");
    }

    private static void advanceEdgeOperation(
        VisualizationRuntimeHost host,
        int[] values,
        int[] edge,
        String scanPrefix,
        String finishPrefix
    ) {
        host.setStepIndex(host.getStepIndex() + 1);
        int stepIndex = host.getStepIndex();
        int sourceIndex = Math.min(edge[0], values.length - 1);
        int destIndex = Math.min(edge[1], values.length - 1);
        int[] order = buildLinearOrder(values.length);
        host.setTraversalOrder(order);
        if (stepIndex == 0) {
            host.updateTraversalVisualization(order, sourceIndex);
            host.setHighlightedLines(2, 1);
            host.clearActiveEdge();
            host.bindCurrentState(scanPrefix + values[sourceIndex] + " -> " + values[destIndex] + ".");
            return;
        }
        host.updateTraversalVisualization(order, destIndex);
        host.setHighlightedLines(4, 2);
        host.setActiveEdge(sourceIndex, destIndex);
        host.bindCurrentState(finishPrefix + values[sourceIndex] + " -> " + values[destIndex] + ".");
        host.finish(finishPrefix + values[sourceIndex] + " -> " + values[destIndex] + ".");
    }

    private static boolean isGraphOperationTechnique(String techniqueId) {
        return "find_edge_graph".equals(techniqueId)
            || "insert_node_graph".equals(techniqueId)
            || "insert_edge_graph".equals(techniqueId)
            || "delete_edge_graph".equals(techniqueId)
            || "delete_vertex_graph".equals(techniqueId);
    }

    private static int resolveGraphNodeValue(VisualizationRuntimeHost host, int[] values) {
        int target = host.getTarget();
        if (indexOf(values, target) < 0 && target != 0) {
            return target;
        }
        int max = 0;
        for (int value : values) {
            if (value > max) {
                max = value;
            }
        }
        return max + 1;
    }

    private static int resolveDeleteIndex(VisualizationRuntimeHost host, int[] values) {
        int target = host.getTarget();
        int found = indexOf(values, target);
        if (found >= 0) {
            return found;
        }
        return Math.max(0, values.length - 1);
    }

    private static int[] buildLinearOrder(int size) {
        int[] order = new int[Math.max(0, size)];
        for (int i = 0; i < size; i++) {
            order[i] = i;
        }
        return order;
    }

    private static int[] appendValue(int[] values, int value) {
        int[] updated = new int[values.length + 1];
        System.arraycopy(values, 0, updated, 0, values.length);
        updated[values.length] = value;
        return updated;
    }

    private static int[] removeAt(int[] values, int index) {
        if (values.length == 0) {
            return values;
        }
        int safeIndex = Math.max(0, Math.min(values.length - 1, index));
        int[] updated = new int[values.length - 1];
        System.arraycopy(values, 0, updated, 0, safeIndex);
        System.arraycopy(values, safeIndex + 1, updated, safeIndex, values.length - safeIndex - 1);
        return updated;
    }

    private static int indexOf(int[] values, int target) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static final int[][] PRESET_1_EDGES = {
        {0, 1}, {0, 3}, {1, 2}, {1, 3}, {1, 4}, {2, 4}, {3, 4}
    };
    public static final int[] PRESET_1_WEIGHTS = {4, 2, 3, 1, 5, 2, 7};

    public static final int[][] PRESET_2_EDGES = {
        {0, 1}, {0, 2}, {1, 2}, {1, 3}, {2, 3}, {2, 4}, {3, 4}, {3, 5}, {4, 5}
    };
    public static final int[] PRESET_2_WEIGHTS = {3, 1, 1, 4, 2, 6, 3, 5, 2};

    public static final int[][] PRESET_3_EDGES = {
        {0, 1}, {0, 2}, {0, 3}, {0, 4}, {1, 2}, {3, 4}
    };
    public static final int[] PRESET_3_WEIGHTS = {5, 2, 8, 4, 3, 1};

    public static int[][] getGraphEdges(int preset, int nodeCount) {
        int[][] source;
        if (preset == 2) {
            source = PRESET_2_EDGES;
        } else if (preset == 3) {
            source = PRESET_3_EDGES;
        } else {
            source = PRESET_1_EDGES;
        }
        java.util.List<int[]> valid = new java.util.ArrayList<>();
        for (int[] e : source) {
            if (e[0] < nodeCount && e[1] < nodeCount) {
                valid.add(new int[]{e[0], e[1]});
            }
        }
        return valid.toArray(new int[0][]);
    }

    public static int getGraphEdgeWeight(int preset, int from, int to) {
        int[][] edges;
        int[] weights;
        if (preset == 2) {
            edges = PRESET_2_EDGES;
            weights = PRESET_2_WEIGHTS;
        } else if (preset == 3) {
            edges = PRESET_3_EDGES;
            weights = PRESET_3_WEIGHTS;
        } else {
            edges = PRESET_1_EDGES;
            weights = PRESET_1_WEIGHTS;
        }
        for (int i = 0; i < edges.length; i++) {
            if ((edges[i][0] == from && edges[i][1] == to) || (edges[i][0] == to && edges[i][1] == from)) {
                return weights[i];
            }
        }
        if (from >= 0 && to >= 0 && from != to) {
            return Math.abs(from - to) + 2;
        }
        return -1;
    }

    public static int getNodeCountForPreset(int preset) {
        if (preset == 2) return 6;
        return 5;
    }

    public static int getDefaultStartNode(int preset) {
        return 0;
    }

    public static String getPresetDescription(String techniqueId, int preset, int startNode, int nodeCount) {
        if (preset == 2) {
            return "Preset 2: Dense Mesh (" + nodeCount + " Vertices, Start: Node " + startNode + ")\n"
                + "• Edges: (0-1: 3), (0-2: 1), (1-2: 1), (1-3: 4), (2-3: 2), (2-4: 6), (3-4: 3), (3-5: 5), (4-5: 2)\n"
                + "• Multi-path cyclic topology ideal for all-pairs shortest paths, cycle detection, and alternative routing.";
        }
        if (preset == 3) {
            return "Preset 3: Star Hub & Clusters (" + nodeCount + " Vertices, Start: Node " + startNode + ")\n"
                + "• Edges: (0-1: 5), (0-2: 2), (0-3: 8), (0-4: 4), (1-2: 3), (3-4: 1)\n"
                + "• Node 0 acts as a central hub connecting clusters {1, 2} and {3, 4}. Highlights bridges, cut vertices, and tree branching.";
        }
        return "Preset 1: Classic DAG / Balanced (" + nodeCount + " Vertices, Start: Node " + startNode + ")\n"
            + "• Edges: (0-1: 4), (0-3: 2), (1-2: 3), (1-3: 1), (1-4: 5), (2-4: 2), (3-4: 7)\n"
            + "• Textbook diamond graph with multiple paths, bottleneck edges, and clear BFS/DFS discovery ordering.";
    }
}
