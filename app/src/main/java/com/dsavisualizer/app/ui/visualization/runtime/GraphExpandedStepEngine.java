package com.dsavisualizer.app.ui.visualization.runtime;

/**
 * Dedicated visual step engine for expanded Graph Algorithms:
 * - Bellman-Ford (Edge relaxations across |V|-1 iterations and negative cycle detection)
 * - Floyd-Warshall (3D/2D all-pairs distance matrix updates via intermediate vertex k)
 * - Prim's MST (Greedy vertex cut expansion)
 * - Kruskal's MST (Sorted edge evaluation with DSU cycle prevention)
 * - Tarjan's SCC (DFS low-link discovery and stack decomposition)
 * - Kosaraju's SCC (Finish-time DFS stack on G followed by DFS on transposed G^T)
 * - Bridges & Articulation Points (DFS low-link bridge cut-edge detection)
 * - A* Search (f(n) = g(n) + h(n) heuristic-guided priority queue pathfinding)
 * - Edmonds-Karp (BFS augmenting path discovery and residual capacity updates)
 * - Johnson's Algorithm (Bellman-Ford reweighting followed by all-pairs Dijkstra)
 */
public final class GraphExpandedStepEngine extends BaseStepEngine {

    public GraphExpandedStepEngine(VisualizationRuntimeHost host) {
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
        String id = host.getTechniqueId();
        if ("graph_bellman_ford".equals(id)) {
            advanceBellmanFord(host);
        } else if ("graph_floyd_warshall".equals(id)) {
            advanceFloydWarshall(host);
        } else if ("graph_prim".equals(id) || "graph_prims".equals(id)) {
            advancePrims(host);
        } else if ("graph_kruskal".equals(id) || "graph_kruskals".equals(id)) {
            advanceKruskals(host);
        } else if ("graph_tarjan_scc".equals(id)) {
            advanceTarjan(host);
        } else if ("graph_kosaraju_scc".equals(id)) {
            advanceKosaraju(host);
        } else if ("graph_bridges_articulation".equals(id) || "graph_bridges".equals(id)) {
            advanceBridges(host);
        } else if ("graph_astar".equals(id) || "graph_a_star".equals(id)) {
            advanceAStar(host);
        } else if ("graph_edmonds_karp".equals(id)) {
            advanceEdmondsKarp(host);
        } else if ("graph_johnson".equals(id) || "graph_johnsons".equals(id)) {
            advanceJohnsons(host);
        } else {
            advanceGenericGraph(host);
        }
    }

    public static boolean isExpandedGraphTechnique(String id) {
        return "graph_bellman_ford".equals(id)
            || "graph_floyd_warshall".equals(id)
            || "graph_prim".equals(id)
            || "graph_prims".equals(id)
            || "graph_kruskal".equals(id)
            || "graph_kruskals".equals(id)
            || "graph_tarjan_scc".equals(id)
            || "graph_kosaraju_scc".equals(id)
            || "graph_bridges_articulation".equals(id)
            || "graph_bridges".equals(id)
            || "graph_astar".equals(id)
            || "graph_a_star".equals(id)
            || "graph_edmonds_karp".equals(id)
            || "graph_johnson".equals(id)
            || "graph_johnsons".equals(id);
    }

    public static boolean isDirected(String id) {
        return "graph_bellman_ford".equals(id)
            || "graph_floyd_warshall".equals(id)
            || "graph_tarjan_scc".equals(id)
            || "graph_kosaraju_scc".equals(id)
            || "graph_astar".equals(id)
            || "graph_a_star".equals(id)
            || "graph_edmonds_karp".equals(id)
            || "graph_johnson".equals(id)
            || "graph_johnsons".equals(id);
    }

    public static int getNodeCount(String id) {
        if ("graph_floyd_warshall".equals(id)) return 4;
        if ("graph_edmonds_karp".equals(id)) return 6;
        return 5;
    }

    public static int[][] getEdges(String id) {
        if ("graph_bellman_ford".equals(id)) {
            return new int[][]{{0,1},{0,2},{1,2},{1,3},{1,4},{4,3},{3,2},{3,1}};
        }
        if ("graph_floyd_warshall".equals(id)) {
            return new int[][]{{0,1},{0,3},{1,0},{1,2},{2,0},{2,3},{3,0}};
        }
        if ("graph_prim".equals(id) || "graph_prims".equals(id)) {
            return new int[][]{{0,1},{0,3},{1,2},{1,3},{1,4},{2,4},{3,4}};
        }
        if ("graph_kruskal".equals(id) || "graph_kruskals".equals(id)) {
            return new int[][]{{1,2},{2,3},{1,3},{0,1},{0,3},{1,4},{2,4},{3,4}};
        }
        if ("graph_tarjan_scc".equals(id) || "graph_kosaraju_scc".equals(id)) {
            return new int[][]{{0,1},{1,2},{2,0},{1,3},{3,4},{4,3}};
        }
        if ("graph_bridges_articulation".equals(id) || "graph_bridges".equals(id)) {
            return new int[][]{{0,1},{1,2},{1,3},{3,4}};
        }
        if ("graph_astar".equals(id) || "graph_a_star".equals(id)) {
            return new int[][]{{0,1},{0,2},{1,3},{2,3},{3,4},{1,4}};
        }
        if ("graph_edmonds_karp".equals(id)) {
            return new int[][]{{0,1},{0,2},{1,2},{1,3},{2,4},{3,5},{4,3},{4,5}};
        }
        if ("graph_johnson".equals(id) || "graph_johnsons".equals(id)) {
            return new int[][]{{0,1},{0,2},{1,2},{1,3},{2,3},{3,4},{4,1}};
        }
        return new int[0][0];
    }

    public static int getWeight(String id, int from, int to) {
        int[][] edges = getEdges(id);
        int[] weights = getWeights(id);
        for (int i = 0; i < edges.length && i < weights.length; i++) {
            if (edges[i][0] == from && edges[i][1] == to) return weights[i];
            if (!isDirected(id) && edges[i][0] == to && edges[i][1] == from) return weights[i];
        }
        return -1;
    }

    private static int[] getWeights(String id) {
        if ("graph_bellman_ford".equals(id)) return new int[]{-1,4,3,2,2,-3,5,1};
        if ("graph_floyd_warshall".equals(id)) return new int[]{3,7,8,2,5,1,2};
        if ("graph_prim".equals(id) || "graph_prims".equals(id)) return new int[]{2,6,3,8,5,7,9};
        if ("graph_kruskal".equals(id) || "graph_kruskals".equals(id)) return new int[]{1,2,3,4,5,6,7,8};
        if ("graph_astar".equals(id) || "graph_a_star".equals(id)) return new int[]{2,5,3,2,2,7};
        if ("graph_edmonds_karp".equals(id)) return new int[]{10,8,5,10,8,10,6,8};
        if ("graph_johnson".equals(id) || "graph_johnsons".equals(id)) return new int[]{-2,4,3,2,-1,2,1};
        return new int[]{1,1,1,1,1,1,1,1};
    }

    private static void showDistanceState(VisualizationRuntimeHost host, int[] distances, int currentNode, int... settledNodes) {
        boolean[] settled = new boolean[distances.length];
        for (int node : settledNodes) {
            if (node >= 0 && node < settled.length) settled[node] = true;
        }
        host.setDijkstraState(distances, settled, currentNode);
    }

    private static void ensureExpandedGraphValues(VisualizationRuntimeHost host) {
        int count = getNodeCount(host.getTechniqueId());
        int[] values = host.getWorkingValues();
        if (values == null || values.length != count) {
            values = new int[count];
            for (int i = 0; i < count; i++) values[i] = i;
            host.setWorkingValues(values);
        }
    }

    // =========================================================================
    // 1. Bellman-Ford
    // 5 vertices: 0, 1, 2, 3, 4.
    // Edges: (0->1, -1), (0->2, 4), (1->2, 3), (1->3, 2), (1->4, 2), (3->2, 5), (3->1, 1), (4->3, -3)
    // =========================================================================
    private static void advanceBellmanFord(VisualizationRuntimeHost host) {
        int step = Math.max(1, host.getStepIndex() + 1);
        host.setStepIndex(step);
        ensureExpandedGraphValues(host);
        host.setWorkingValues(new int[]{0, 1, 2, 3, 4});

        switch (step) {
            case 1:
                showDistanceState(host, new int[]{0, 9999, 9999, 9999, 9999}, 0, 0);
                host.clearActiveEdge();
                host.setActiveNodes(0, -1);
                host.updatePointerState("Pass", 1, "Source", 0);
                host.setHighlightedLines(8, 3);
                host.bindCurrentState("Step 1: Initialize Bellman-Ford from source 0. dist[0]=0 and all others are INF because only the source is reachable before relaxation.");
                break;
            case 2:
                showDistanceState(host, new int[]{0, -1, 9999, 9999, 9999}, 1, 0, 1);
                host.setActiveNodes(0, 1);
                host.setActiveEdge(0, 1);
                host.updatePointerState("Relax", 1, "w", -1);
                host.setHighlightedLines(14, 5);
                host.bindCurrentState("Step 2: Relax edge 0->1. Because dist[0]+(-1)=-1 is smaller than INF, update dist[1] to -1.");
                break;
            case 3:
                showDistanceState(host, new int[]{0, -1, 2, 1, 1}, 2, 0, 1, 2, 3, 4);
                host.setActiveNodes(1, 2);
                host.setActiveEdge(1, 2);
                host.updatePointerState("Relax", 2, "Pass", 1);
                host.setHighlightedLines(16, 6);
                host.bindCurrentState("Step 3: Relax edges from 1. Because dist[1] is -1, edges 1->2, 1->3, and 1->4 improve distances to 2, 1, and 1.");
                break;
            case 4:
                showDistanceState(host, new int[]{0, -1, 2, -2, 1}, 3, 0, 1, 2, 3, 4);
                host.setActiveNodes(4, 3);
                host.setActiveEdge(4, 3);
                host.updatePointerState("Relax", 3, "w", -3);
                host.setHighlightedLines(18, 7);
                host.bindCurrentState("Step 4: Relax edge 4->3. Because dist[4]+(-3)= -2 is smaller than current dist[3]=1, update dist[3] to -2.");
                break;
            case 5:
                showDistanceState(host, new int[]{0, -1, 2, -2, 1}, 2, 0, 1, 2, 3, 4);
                host.setActiveNodes(3, 2);
                host.setActiveEdge(3, 2);
                host.updatePointerState("Check", 2, "Stable", 1);
                host.setHighlightedLines(22, 9);
                host.bindCurrentState("Step 5: Scan edge 3->2 during the final check. Because dist[3]+5 = 3 is not smaller than dist[2]=2, no update happens.");
                break;
            case 6:
                showDistanceState(host, new int[]{0, -1, 2, -2, 1}, -1, 0, 1, 2, 3, 4);
                host.clearActiveEdge();
                host.setActiveNodes(-1, -1);
                host.updatePointerState("CycleCheck", 0, "Stable", 1);
                host.setHighlightedLines(24, 10);
                host.finish("Step 6: Bellman-Ford complete. Because no edge can still reduce a distance on the extra scan, no reachable negative cycle exists.");
                break;
            default:
                host.finish("Bellman-Ford complete.");
                break;
        }
    }


    // =========================================================================
    // 2. Floyd-Warshall (All-Pairs 4x4 Matrix)
    // =========================================================================
    private static void advanceFloydWarshall(VisualizationRuntimeHost host) {
        int step = Math.max(1, host.getStepIndex() + 1);
        host.setStepIndex(step);

        int[][] mat;
        switch (step) {
            case 1:
                mat = new int[][]{
                    {0, 3, 9999, 7},
                    {8, 0, 2, 9999},
                    {5, 9999, 0, 1},
                    {2, 9999, 9999, 0}
                };
                host.setMatrixState(mat, 0, 0, 1, "Floyd-Warshall: initial matrix, k=0");
                host.updatePointerState("k", 0, "Rule", 1);
                host.setHighlightedLines(14, 5);
                host.bindCurrentState("Step 1: Start with the direct-edge distance matrix. Because Floyd-Warshall tests every vertex k as a middle point, begin with k=0.");
                break;
            case 2:
                mat = new int[][]{
                    {0, 3, 9999, 7},
                    {8, 0, 2, 15},
                    {5, 8, 0, 1},
                    {2, 5, 9999, 0}
                };
                host.setMatrixState(mat, 0, 3, 1, "k=0 updates");
                host.updatePointerState("Update", 1, "via 0", 5);
                host.setHighlightedLines(18, 7);
                host.bindCurrentState("Step 2: With k=0, update dist[3][1]. Because 3->0->1 costs 2+3=5, it is smaller than INF.");
                break;
            case 3:
                mat = new int[][]{
                    {0, 3, 5, 7},
                    {8, 0, 2, 15},
                    {5, 8, 0, 1},
                    {2, 5, 7, 0}
                };
                host.setMatrixState(mat, 1, 0, 2, "k=1 updates");
                host.updatePointerState("Update", 2, "via 1", 5);
                host.setHighlightedLines(18, 7);
                host.bindCurrentState("Step 3: With k=1, update dist[0][2]. Because 0->1->2 costs 3+2=5, it is smaller than INF.");
                break;
            case 4:
                mat = new int[][]{
                    {0, 3, 5, 6},
                    {7, 0, 2, 3},
                    {5, 8, 0, 1},
                    {2, 5, 7, 0}
                };
                host.setMatrixState(mat, 2, 0, 3, "k=2 updates");
                host.updatePointerState("Update", 3, "via 2", 6);
                host.setHighlightedLines(18, 7);
                host.bindCurrentState("Step 4: With k=2, update paths such as 0->3. Because 0->2->3 costs 5+1=6, it improves old distance 7.");
                break;
            case 5:
                mat = new int[][]{
                    {0, 3, 5, 6},
                    {5, 0, 2, 3},
                    {3, 6, 0, 1},
                    {2, 5, 7, 0}
                };
                host.setMatrixState(mat, 3, 2, 0, "final all-pairs matrix");
                host.setHighlightedLines(24, 10);
                host.finish("Step 5: Floyd-Warshall complete. Because all k values have been tested, the matrix now stores shortest distances for every pair.");
                break;
            default:
                host.finish("Floyd-Warshall complete.");
                break;
        }
    }

    // =========================================================================
    // 3. Prim's MST
    // =========================================================================
    private static void advancePrims(VisualizationRuntimeHost host) {
        int step = Math.max(1, host.getStepIndex() + 1);
        host.setStepIndex(step);
        ensureExpandedGraphValues(host);
        host.setWorkingValues(new int[]{0, 1, 2, 3, 4});

        switch (step) {
            case 1:
                host.setActiveNodes(0, -1);
                host.clearActiveEdge();
                host.updatePointerState("MST", 0, "Cut", 2);
                host.setHighlightedLines(10, 3);
                host.bindCurrentState("Step 1: Start from vertex 0. MST = {0}. Candidate cut edges are 0-1 (2) and 0-3 (6). Because Prim always selects the minimum-weight edge that connects the MST to a new vertex, choose 0-1 next.");
                break;
            case 2:
                host.setActiveNodes(0, 1);
                host.setActiveEdge(0, 1);
                host.updatePointerState("Added", 1, "Total", 2);
                host.setHighlightedLines(15, 5);
                host.bindCurrentState("Step 2: Add edge 0-1 with weight 2 because 2 is smaller than 6. MST = {0,1}, total = 2. Now compare candidate cut edges 1-2 (3), 1-4 (5), 0-3 (6), and 1-3 (8).");
                break;
            case 3:
                host.setActiveNodes(1, 2);
                host.setActiveEdge(1, 2);
                host.updatePointerState("Added", 2, "Total", 5);
                host.setHighlightedLines(18, 7);
                host.bindCurrentState("Step 3: Add edge 1-2 with weight 3 because it is the smallest candidate edge crossing from MST {0,1} to an unvisited vertex. MST = {0,1,2}, total = 5.");
                break;
            case 4:
                host.setActiveNodes(1, 4);
                host.setActiveEdge(1, 4);
                host.updatePointerState("Added", 4, "Total", 10);
                host.setHighlightedLines(18, 7);
                host.bindCurrentState("Step 4: Add edge 1-4 with weight 5 because among the remaining crossing edges, 5 is less than 0-3 (6), 2-4 (7), and 1-3 (8). MST = {0,1,2,4}, total = 10.");
                break;
            case 5:
                host.setActiveNodes(0, 3);
                host.setActiveEdge(0, 3);
                host.updatePointerState("Added", 3, "Total", 16);
                host.setHighlightedLines(22, 9);
                host.finish("Step 5: Add edge 0-3 with weight 6 because it is the lightest edge that reaches the last unvisited vertex 3. Prim's MST is complete: edges {0-1, 1-2, 1-4, 0-3}; total weight = 16.");
                break;
            default:
                host.finish("Prim's complete.");
                break;
        }
    }


    // =========================================================================
    // 4. Kruskal's MST
    // =========================================================================
    private static void advanceKruskals(VisualizationRuntimeHost host) {
        int step = Math.max(1, host.getStepIndex() + 1);
        host.setStepIndex(step);
        ensureExpandedGraphValues(host);
        host.setWorkingValues(new int[]{0, 1, 2, 3, 4});

        switch (step) {
            case 1:
                host.setActiveNodes(1, 2);
                host.setActiveEdge(1, 2);
                host.updatePointerState("Accept", 1, "w", 1);
                host.setHighlightedLines(12, 4);
                host.bindCurrentState("Step 1: Sort all edges by increasing weight. Accept edge 1-2 because it is the lightest edge w=1 and vertices 1 and 2 are in different components.");
                break;
            case 2:
                host.setActiveNodes(2, 3);
                host.setActiveEdge(2, 3);
                host.updatePointerState("Accept", 2, "w", 2);
                host.setHighlightedLines(16, 5);
                host.bindCurrentState("Step 2: Accept edge 2-3 because w=2 is the next lightest safe edge and find(2) != find(3), so no cycle is formed.");
                break;
            case 3:
                host.setActiveNodes(1, 3);
                host.setActiveEdge(1, 3);
                host.updatePointerState("Reject", 3, "cycle", 1);
                host.setHighlightedLines(20, 7);
                host.bindCurrentState("Step 3: Reject edge 1-3 even though w=3 because vertices 1 and 3 are already connected through 1-2-3. Adding it would close a cycle.");
                break;
            case 4:
                host.setActiveNodes(0, 1);
                host.setActiveEdge(0, 1);
                host.updatePointerState("Accept", 0, "w", 4);
                host.setHighlightedLines(16, 5);
                host.bindCurrentState("Step 4: Accept edge 0-1 because it is the next lightest edge that connects isolated vertex 0 to component {1,2,3}.");
                break;
            case 5:
                host.setActiveNodes(0, 3);
                host.setActiveEdge(0, 3);
                host.updatePointerState("Reject", 5, "cycle", 1);
                host.setHighlightedLines(20, 7);
                host.bindCurrentState("Step 5: Reject edge 0-3 with weight 5 because vertices 0 and 3 are already connected through 0-1-2-3. Adding it would form a cycle.");
                break;
            case 6:
                host.setActiveNodes(1, 4);
                host.setActiveEdge(1, 4);
                host.updatePointerState("Accept", 4, "Total", 13);
                host.setHighlightedLines(16, 5);
                host.bindCurrentState("Step 6: Accept edge 1-4 because it connects the last isolated vertex 4 without forming a cycle. MST total becomes 1+2+4+6=13.");
                break;
            case 7:
                host.clearActiveEdge();
                host.setActiveNodes(-1, -1);
                host.updatePointerState("MST", 4, "Total", 13);
                host.setHighlightedLines(24, 10);
                host.finish("Step 7: Kruskal complete. Because |V|-1 safe edges are selected, the MST is {1-2, 2-3, 0-1, 1-4} with total weight 13.");
                break;
            default:
                host.finish("Kruskal's complete.");
                break;
        }
    }

    // =========================================================================
    // 5. Tarjan's Strongly Connected Components
    // =========================================================================
    private static void advanceTarjan(VisualizationRuntimeHost host) {
        int step = Math.max(1, host.getStepIndex() + 1);
        host.setStepIndex(step);
        ensureExpandedGraphValues(host);
        host.setWorkingValues(new int[]{0, 1, 2, 3, 4});

        switch (step) {
            case 1:
                host.setActiveNodes(0, 1);
                host.setActiveEdge(0, 1);
                host.updatePointerState("tin[0]", 0, "low[0]", 0);
                host.setHighlightedLines(12, 4);
                host.bindCurrentState("Step 1: Visit node 0 and follow tree edge 0->1. Because 0 is newly discovered, set tin[0]=low[0]=0 and push it on the stack.");
                break;
            case 2:
                host.setActiveNodes(1, 2);
                host.setActiveEdge(1, 2);
                host.updatePointerState("tin[1]", 1, "low[1]", 1);
                host.setHighlightedLines(15, 5);
                host.bindCurrentState("Step 2: Visit node 1 and follow 1->2. Because 1 is active in DFS, set tin[1]=low[1]=1 and keep it on stack.");
                break;
            case 3:
                host.setActiveNodes(2, 0);
                host.setActiveEdge(2, 0);
                host.updatePointerState("BackEdge", 0, "low", 0);
                host.setHighlightedLines(20, 8);
                host.bindCurrentState("Step 3: Edge 2->0 is a back edge to a node still on stack. Because it reaches ancestor 0, low[2] becomes 0.");
                break;
            case 4:
                host.setActiveNodes(0, 2);
                host.clearActiveEdge();
                host.updatePointerState("SCC", 0, "size", 3);
                host.setHighlightedLines(24, 10);
                host.bindCurrentState("Step 4: Return to node 0. Because low[0] == tin[0], pop 2,1,0 and output SCC {0,1,2}.");
                break;
            case 5:
                host.setActiveNodes(3, 4);
                host.setActiveEdge(3, 4);
                host.updatePointerState("Next SCC", 3, "low", 3);
                host.setHighlightedLines(15, 5);
                host.bindCurrentState("Step 5: Continue DFS at node 3 and follow 3->4. Because 4 has edge back to 3, low values identify SCC {3,4}.");
                break;
            case 6:
                host.setActiveNodes(3, 4);
                host.setActiveEdge(4, 3);
                host.updatePointerState("SCC", 3, "count", 2);
                host.setHighlightedLines(28, 12);
                host.finish("Step 6: Tarjan complete. Because every DFS root has been processed, SCCs are {0,1,2} and {3,4}.");
                break;
            default:
                host.finish("Tarjan's complete.");
                break;
        }
    }

    // =========================================================================
    // 6. Kosaraju's SCC
    // =========================================================================
    private static void advanceKosaraju(VisualizationRuntimeHost host) {
        int step = Math.max(1, host.getStepIndex() + 1);
        host.setStepIndex(step);
        ensureExpandedGraphValues(host);
        host.setWorkingValues(new int[]{0, 1, 2, 3, 4});

        switch (step) {
            case 1:
                host.setActiveNodes(0, 1);
                host.setActiveEdge(0, 1);
                host.updatePointerState("Pass 1", 1, "DFS", 0);
                host.setHighlightedLines(10, 3);
                host.bindCurrentState("Step 1: Run DFS on original graph G. Because Kosaraju needs finish order, push vertices only after all outgoing edges finish.");
                break;
            case 2:
                host.setActiveNodes(2, 0);
                host.setActiveEdge(2, 0);
                host.updatePointerState("Finish", 0, "Stack", 5);
                host.setHighlightedLines(12, 4);
                host.bindCurrentState("Step 2: Finish the first DFS. Because the cycle 0->1->2->0 finishes together, the stack orders SCC roots for pass 2.");
                break;
            case 3:
                host.clearActiveEdge();
                host.setActiveNodes(-1, -1);
                host.updatePointerState("Transpose", 2, "G^T", 1);
                host.setHighlightedLines(15, 5);
                host.bindCurrentState("Step 3: Reverse all edges to create G^T because SCCs stay connected internally while cross-component directions flip.");
                break;
            case 4:
                host.setActiveNodes(0, 2);
                host.setActiveEdge(0, 2);
                host.updatePointerState("Pop", 0, "SCC", 3);
                host.setHighlightedLines(20, 8);
                host.bindCurrentState("Step 4: Pop 0 and DFS in G^T. Because 0 reaches 2 and 1 in the transpose, output SCC {0,1,2}.");
                break;
            case 5:
                host.setActiveNodes(3, 4);
                host.setActiveEdge(3, 4);
                host.updatePointerState("Pop", 3, "SCC", 2);
                host.setHighlightedLines(20, 8);
                host.bindCurrentState("Step 5: Pop the next unvisited root 3. Because 3 and 4 reach each other in G^T, output SCC {3,4}.");
                break;
            case 6:
                host.clearActiveEdge();
                host.setActiveNodes(-1, -1);
                host.updatePointerState("Done", 2, "SCCs", 2);
                host.setHighlightedLines(24, 10);
                host.finish("Step 6: Kosaraju complete. Because the finish stack is empty, all SCCs are found: {0,1,2} and {3,4}.");
                break;
            default:
                host.finish("Kosaraju's complete.");
                break;
        }
    }

    // =========================================================================
    // 7. Bridges & Articulation Points
    // =========================================================================
    private static void advanceBridges(VisualizationRuntimeHost host) {
        int step = Math.max(1, host.getStepIndex() + 1);
        host.setStepIndex(step);
        ensureExpandedGraphValues(host);
        host.setWorkingValues(new int[]{0, 1, 2, 3, 4});

        switch (step) {
            case 1:
                host.setActiveNodes(0, 1);
                host.setActiveEdge(0, 1);
                host.updatePointerState("tin[0]", 0, "low[0]", 0);
                host.setHighlightedLines(12, 4);
                host.bindCurrentState("Step 1: Start DFS with edge 0-1. Because bridge detection compares tin and low values, set tin[0]=low[0]=0.");
                break;
            case 2:
                host.setActiveNodes(1, 2);
                host.setActiveEdge(1, 2);
                host.updatePointerState("tin[2]", 2, "low[2]", 2);
                host.setHighlightedLines(15, 5);
                host.bindCurrentState("Step 2: Visit child 2 from 1. Because node 2 has no back edge, low[2] stays equal to tin[2].");
                break;
            case 3:
                host.setActiveNodes(1, 2);
                host.setActiveEdge(1, 2);
                host.updatePointerState("Bridge", 2, "low>tin", 1);
                host.setHighlightedLines(22, 9);
                host.bindCurrentState("Step 3: Edge 1-2 is a bridge because low[2]=2 is greater than tin[1]=1, so child 2 cannot reconnect without edge 1-2.");
                break;
            case 4:
                host.setActiveNodes(1, 3);
                host.setActiveEdge(1, 3);
                host.updatePointerState("CutVertex", 1, "child", 3);
                host.setHighlightedLines(24, 10);
                host.bindCurrentState("Step 4: Vertex 1 is an articulation point because it has a child subtree that cannot reach an ancestor of 1.");
                break;
            case 5:
                host.setActiveNodes(3, 4);
                host.setActiveEdge(3, 4);
                host.updatePointerState("Bridge", 4, "Cut", 3);
                host.setHighlightedLines(24, 10);
                host.bindCurrentState("Step 5: Edge 3-4 is also a bridge, and vertex 3 is an articulation point because removing 3 disconnects vertex 4.");
                break;
            case 6:
                host.clearActiveEdge();
                host.setActiveNodes(1, 3);
                host.updatePointerState("Done", 2, "Cuts", 2);
                host.setHighlightedLines(28, 12);
                host.finish("Step 6: Bridges and articulation complete. Because low-link checks are done, bridges are tree edges here and cut vertices are 1 and 3.");
                break;
            default:
                host.finish("Bridge/articulation detection complete.");
                break;
        }
    }

    // =========================================================================
    // 8. A* Search
    // =========================================================================
    private static void advanceAStar(VisualizationRuntimeHost host) {
        int step = Math.max(1, host.getStepIndex() + 1);
        host.setStepIndex(step);
        ensureExpandedGraphValues(host);
        host.setWorkingValues(new int[]{0, 1, 2, 3, 4});

        switch (step) {
            case 1:
                showDistanceState(host, new int[]{0, 9999, 9999, 9999, 9999}, 0, 0);
                host.setActiveNodes(0, -1);
                host.clearActiveEdge();
                host.updatePointerState("Open", 0, "f", 10);
                host.setHighlightedLines(11, 4);
                host.bindCurrentState("Step 1: Start A* at node 0. Because g(0)=0 and h(0)=10, f(0)=10 and 0 is the only open node.");
                break;
            case 2:
                showDistanceState(host, new int[]{0, 2, 5, 9999, 9999}, 1, 0);
                host.setActiveNodes(0, 1);
                host.setActiveEdge(0, 1);
                host.updatePointerState("Best f", 1, "f", 8);
                host.setHighlightedLines(16, 6);
                host.bindCurrentState("Step 2: Expand 0. Because node 1 has f=2+6=8 and node 2 has f=5+7=12, choose node 1 next.");
                break;
            case 3:
                showDistanceState(host, new int[]{0, 2, 5, 5, 9}, 3, 0, 1);
                host.setActiveNodes(1, 3);
                host.setActiveEdge(1, 3);
                host.updatePointerState("Best f", 3, "f", 7);
                host.setHighlightedLines(18, 7);
                host.bindCurrentState("Step 3: Expand 1. Because path 0->1->3 gives g=5 and h(3)=2, f(3)=7, so node 3 becomes best.");
                break;
            case 4:
                showDistanceState(host, new int[]{0, 2, 5, 5, 7}, 4, 0, 1, 3);
                host.setActiveNodes(3, 4);
                host.setActiveEdge(3, 4);
                host.updatePointerState("Goal", 4, "Cost", 7);
                host.setHighlightedLines(22, 9);
                host.bindCurrentState("Step 4: Move from 3 to goal 4. Because g(3)+w(3,4)=5+2=7 is the best frontier route, choose the goal.");
                break;
            case 5:
                showDistanceState(host, new int[]{0, 2, 5, 5, 7}, 4, 0, 1, 3, 4);
                host.clearActiveEdge();
                host.setActiveNodes(0, 4);
                host.updatePointerState("Path", 4, "Cost", 7);
                host.setHighlightedLines(24, 10);
                host.finish("Step 5: A* complete. Because the goal was popped with the minimum f-score, path 0->1->3->4 is optimal with cost 7.");
                break;
            default:
                host.finish("A* complete.");
                break;
        }
    }


    // =========================================================================
    // 9. Edmonds-Karp Max Flow
    // =========================================================================
    private static void advanceEdmondsKarp(VisualizationRuntimeHost host) {
        int step = Math.max(1, host.getStepIndex() + 1);
        host.setStepIndex(step);
        ensureExpandedGraphValues(host);
        host.setWorkingValues(new int[]{0, 1, 2, 3, 4, 5});

        switch (step) {
            case 1:
                host.setActiveNodes(0, 1);
                host.setActiveEdge(0, 1);
                host.updatePointerState("BFS path", 1, "bottle", 10);
                host.setHighlightedLines(12, 4);
                host.bindCurrentState("Step 1: BFS finds path 0->1->3->5. Because residual capacities are 10,10,10, the bottleneck is 10.");
                break;
            case 2:
                host.setActiveNodes(3, 5);
                host.setActiveEdge(3, 5);
                host.updatePointerState("Augment", 10, "Flow", 10);
                host.setHighlightedLines(16, 6);
                host.bindCurrentState("Step 2: Augment 10 units on 0->1->3->5 because bottleneck 10 is the maximum flow this path can carry.");
                break;
            case 3:
                host.setActiveNodes(0, 2);
                host.setActiveEdge(0, 2);
                host.updatePointerState("BFS path", 2, "bottle", 8);
                host.setHighlightedLines(12, 4);
                host.bindCurrentState("Step 3: BFS finds second path 0->2->4->5. Because capacities are 8,8,8, add bottleneck flow 8 for total 18.");
                break;
            case 4:
                host.setActiveNodes(4, 5);
                host.setActiveEdge(4, 5);
                host.updatePointerState("MaxFlow", 18, "NoPath", 0);
                host.setHighlightedLines(22, 9);
                host.finish("Step 4: Edmonds-Karp complete. Because BFS cannot reach sink 5 in the residual graph, maximum flow is 18.");
                break;
            default:
                host.finish("Edmonds-Karp complete.");
                break;
        }
    }

    // =========================================================================
    // 10. Johnson's Algorithm
    // =========================================================================
    private static void advanceJohnsons(VisualizationRuntimeHost host) {
        int step = Math.max(1, host.getStepIndex() + 1);
        host.setStepIndex(step);

        switch (step) {
            case 1:
                host.setMatrixState(new int[][]{
                    {0, -2, 4, 9999, 9999},
                    {9999, 0, 3, 2, 9999},
                    {9999, 9999, 0, -1, 9999},
                    {9999, 9999, 9999, 0, 2},
                    {9999, 1, 9999, 9999, 0}
                }, 0, 0, 1, "Johnson: original weighted graph");
                host.updatePointerState("Start", 0, "NegEdges", 2);
                host.setHighlightedLines(10, 3);
                host.bindCurrentState("Step 1: Start with the original sparse graph. Because some edges are negative, plain Dijkstra cannot be used yet.");
                break;
            case 2:
                host.setMatrixState(new int[][]{
                    {0, -2, 4, 9999, 9999},
                    {9999, 0, 3, 2, 9999},
                    {9999, 9999, 0, -1, 9999},
                    {9999, 9999, 9999, 0, 2},
                    {9999, 1, 9999, 9999, 0}
                }, 0, 0, 1, "Bellman-Ford potentials h=[0,-2,0,-1,0]");
                host.updatePointerState("BF", 0, "h", -2);
                host.setHighlightedLines(12, 4);
                host.bindCurrentState("Step 2: Add synthetic source q and run Bellman-Ford. Because q reaches every vertex with 0 edges, potentials become h=[0,-2,0,-1,0].");
                break;
            case 3:
                host.setMatrixState(new int[][]{
                    {0, 0, 4, 9999, 9999},
                    {9999, 0, 1, 1, 9999},
                    {9999, 9999, 0, 0, 9999},
                    {9999, 9999, 9999, 0, 1},
                    {9999, 3, 9999, 9999, 0}
                }, 1, 0, 1, "Reweighted non-negative graph");
                host.updatePointerState("w'", 1, "nonneg", 1);
                host.setHighlightedLines(16, 5);
                host.bindCurrentState("Step 3: Reweight edges with w'=w+h(u)-h(v). Because every w' is non-negative, Dijkstra is now safe.");
                break;
            case 4:
                host.setMatrixState(new int[][]{
                    {0, 0, 1, 1, 2},
                    {9999, 0, 1, 1, 2},
                    {9999, 3, 0, 0, 1},
                    {9999, 4, 5, 0, 1},
                    {9999, 3, 4, 4, 0}
                }, 2, 0, 4, "Dijkstra on reweighted graph");
                host.updatePointerState("Dijkstra", 2, "AllPairs", 5);
                host.setHighlightedLines(22, 8);
                host.bindCurrentState("Step 4: Run Dijkstra from each vertex on the reweighted graph because all weights are now non-negative.");
                break;
            case 5:
                host.setMatrixState(new int[][]{
                    {0, -2, 1, 0, 2},
                    {9999, 0, 3, 2, 4},
                    {9999, 2, 0, -1, 1},
                    {9999, 3, 6, 0, 2},
                    {9999, 1, 4, 3, 0}
                }, 0, 0, 4, "Restored original distances");
                host.setHighlightedLines(28, 10);
                host.finish("Step 5: Johnson complete. Because Dijkstra results are converted back with d(u,v)=d'(u,v)-h(u)+h(v), final original shortest paths are restored.");
                break;
            default:
                host.finish("Johnson's complete.");
                break;
        }
    }


    private static void advanceGenericGraph(VisualizationRuntimeHost host) {
        int step = Math.max(1, host.getStepIndex() + 1);
        host.setStepIndex(step);
        if (step >= 5) {
            host.finish("Graph algorithm traversal completed.");
        } else {
            host.bindCurrentState("Graph algorithm step " + step + ".");
        }
    }

    public static int getTotalSteps(String id) {
        if (id == null) return 4;
        if ("graph_edmonds_karp".equals(id)) return 4;
        if ("graph_bellman_ford".equals(id)) return 6;
        if ("graph_prim".equals(id) || "graph_prims".equals(id)) return 5;
        if ("graph_bridges_articulation".equals(id) || "graph_bridges".equals(id)) return 6;
        if ("graph_kruskal".equals(id) || "graph_kruskals".equals(id)) return 7;
        if ("graph_tarjan_scc".equals(id)) return 6;
        if ("graph_kosaraju_scc".equals(id)) return 6;
        if ("graph_astar".equals(id) || "graph_a_star".equals(id)) return 5;
        if ("graph_johnson".equals(id) || "graph_johnsons".equals(id)) return 5;
        if ("graph_floyd_warshall".equals(id)) return 5;
        return 4;
    }
}
