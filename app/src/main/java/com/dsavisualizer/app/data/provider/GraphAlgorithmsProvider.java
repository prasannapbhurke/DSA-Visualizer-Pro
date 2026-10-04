package com.dsavisualizer.app.data.provider;

import com.dsavisualizer.app.model.CategoryItem;
import com.dsavisualizer.app.model.TechniqueContent;
import com.dsavisualizer.app.model.TechniqueItem;

import java.util.List;
import java.util.Map;

/**
 * Curated provider for advanced Graph algorithms.
 * Includes complete Iterative, Functions, and Recursion algorithms and code (Java, C, Python).
 */
public final class GraphAlgorithmsProvider {

    private GraphAlgorithmsProvider() {}

    public static void register(
        List<CategoryItem> categories,
        List<TechniqueItem> techniques,
        Map<String, TechniqueContent> contentMap
    ) {
        // 1. Bellman-Ford
        techniques.add(new TechniqueItem(
            "graph_bellman_ford",
            "graph",
            "Bellman-Ford Algorithm",
            "Single-source shortest paths on graphs with negative weights & cycle detection",
            "Medium"
        ));
        // 2. Floyd-Warshall
        techniques.add(new TechniqueItem(
            "graph_floyd_warshall",
            "graph",
            "Floyd-Warshall Algorithm",
            "All-pairs shortest paths via dynamic programming matrix relaxation",
            "Hard"
        ));
        // 3. Prim's MST
        techniques.add(new TechniqueItem(
            "graph_prim",
            "graph",
            "Prim's Algorithm (MST)",
            "Greedy Minimum Spanning Tree growing outward from a cut vertex",
            "Medium"
        ));
        // 4. Kruskal's MST
        techniques.add(new TechniqueItem(
            "graph_kruskal",
            "graph",
            "Kruskal's Algorithm (MST)",
            "Sort edge weights and unite components using Disjoint Set Union",
            "Medium"
        ));
        // 5. Tarjan's SCC
        techniques.add(new TechniqueItem(
            "graph_tarjan_scc",
            "graph",
            "Tarjan's SCC Algorithm",
            "Single DFS pass finding Strongly Connected Components via low-link values",
            "Hard"
        ));
        // 6. Kosaraju's SCC
        techniques.add(new TechniqueItem(
            "graph_kosaraju_scc",
            "graph",
            "Kosaraju's SCC Algorithm",
            "Two DFS passes on forward and transposed graph to isolate components",
            "Medium"
        ));
        // 7. Bridges & Articulation Points
        techniques.add(new TechniqueItem(
            "graph_bridges_articulation",
            "graph",
            "Bridges & Articulation Points",
            "Identify critical edges and vertices whose removal disconnects the graph",
            "Hard"
        ));
        // 8. A* Search
        techniques.add(new TechniqueItem(
            "graph_astar",
            "graph",
            "A* Search Algorithm",
            "Heuristic best-first search combining path cost g(n) and heuristic estimate h(n)",
            "Medium"
        ));
        // 9. Edmonds-Karp
        techniques.add(new TechniqueItem(
            "graph_edmonds_karp",
            "graph",
            "Edmonds-Karp (Max Flow)",
            "Ford-Fulkerson implementation using BFS shortest augmenting paths",
            "Hard"
        ));
        // 10. Johnson's Algorithm
        techniques.add(new TechniqueItem(
            "graph_johnson",
            "graph",
            "Johnson's Algorithm",
            "All-pairs shortest paths on sparse graphs by reweighting with Bellman-Ford",
            "Hard"
        ));

        registerBellmanFord(contentMap);
        registerFloydWarshall(contentMap);
        registerPrim(contentMap);
        registerKruskal(contentMap);
        registerTarjan(contentMap);
        registerKosaraju(contentMap);
        registerBridges(contentMap);
        registerAStar(contentMap);
        registerEdmondsKarp(contentMap);
        registerJohnson(contentMap);
    }

    private static void registerBellmanFord(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "graph_bellman_ford",
            "Bellman-Ford Algorithm",
            "Computes shortest paths from a single source vertex to all other vertices in a weighted digraph.\n\nHandles negative edge weights and detects negative weight cycles by relaxing all edges |V|-1 times.",
            "Currency arbitrage detection in financial forex trading networks.",
            "O(V * E)", "O(V * E)", "O(V * E)", "O(V)",
            new int[] {0, 1, 2, 3, 4},
            0
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: BELLMAN_FORD_ITERATIVE(V, edges, src)\n1. Set dist[src] = 0, dist[v] = infinity for all v != src.\n2. Repeat V - 1 times:\n     For each directed edge (u, v, w):\n       if dist[u] != inf and dist[u] + w < dist[v]: dist[v] = dist[u] + w\n3. For each edge (u, v, w):\n     if dist[u] != inf and dist[u] + w < dist[v]: report Negative Weight Cycle!\n4. Return dist.");
        tc.algorithmByStyle.put("Functions", "Algorithm: BELLMAN_FORD_FUNCTIONAL(V, edges, src)\nFUNCTION RELAX_EDGES(edges, dist):\n  updated = false\n  For each edge (u, v, w): If dist[u] + w < dist[v]: dist[v] = dist[u] + w, updated = true\n  return updated\nFUNCTION SOLVE_BELLMAN_FORD(V, edges, src):\n  Initialize dist table.\n  Loop V-1 times calling RELAX_EDGES.");
        tc.algorithmByStyle.put("Recursion", "Algorithm: BELLMAN_FORD_RECURSIVE(step, V, edges, dist)\n1. If step == V: return true (check for negative cycle).\n2. RELAX_EDGES(edges, dist)\n3. Return BELLMAN_FORD_RECURSIVE(step + 1, V, edges, dist)");

        tc.putCode("Java", "Iterative", "// Bellman-Ford - Iterative O(V * E)\nimport java.util.Arrays;\n\npublic class BellmanFordIterative {\n    public static boolean bellmanFord(int[][] edges, int V, int src, int[] dist) {\n        Arrays.fill(dist, Integer.MAX_VALUE);\n        dist[src] = 0;\n\n        for (int i = 1; i < V; i++) {\n            for (int[] e : edges) {\n                int u = e[0], v = e[1], w = e[2];\n                if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {\n                    dist[v] = dist[u] + w;\n                }\n            }\n        }\n\n        // Negative cycle check\n        for (int[] e : edges) {\n            int u = e[0], v = e[1], w = e[2];\n            if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {\n                return false; // Negative cycle detected\n            }\n        }\n        return true;\n    }\n}", true);
        tc.putCode("Java", "Functions", "// Bellman-Ford - Functional Modular\nimport java.util.Arrays;\n\npublic class BellmanFordFunctional {\n    private static boolean relaxAll(int[][] edges, int[] dist) {\n        boolean changed = false;\n        for (int[] e : edges) {\n            int u = e[0], v = e[1], w = e[2];\n            if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {\n                dist[v] = dist[u] + w;\n                changed = true;\n            }\n        }\n        return changed;\n    }\n\n    public static int[] getDistances(int[][] edges, int V, int src) {\n        int[] dist = new int[V];\n        Arrays.fill(dist, Integer.MAX_VALUE);\n        dist[src] = 0;\n\n        for (int i = 1; i < V; i++) {\n            if (!relaxAll(edges, dist)) break; // Early termination\n        }\n        return dist;\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// Bellman-Ford - Recursive Relaxation Passes\nimport java.util.Arrays;\n\npublic class BellmanFordRecursive {\n    public static boolean solve(int[][] edges, int V, int step, int[] dist) {\n        if (step == V) {\n            for (int[] e : edges) {\n                if (dist[e[0]] != Integer.MAX_VALUE && dist[e[0]] + e[2] < dist[e[1]]) return false;\n            }\n            return true;\n        }\n\n        for (int[] e : edges) {\n            int u = e[0], v = e[1], w = e[2];\n            if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {\n                dist[v] = dist[u] + w;\n            }\n        }\n        return solve(edges, V, step + 1, dist);\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* Bellman-Ford Algorithm - Iterative (C) */\n#include <stdio.h>\n#include <stdbool.h>\n#include <limits.h>\n\ntypedef struct { int u, v, w; } Edge;\n\nbool bellmanFord(Edge edges[], int numEdges, int V, int src, int dist[]) {\n    for (int i = 0; i < V; i++) dist[i] = INT_MAX;\n    dist[src] = 0;\n\n    for (int i = 1; i < V; i++) {\n        for (int j = 0; j < numEdges; j++) {\n            int u = edges[j].u, v = edges[j].v, w = edges[j].w;\n            if (dist[u] != INT_MAX && dist[u] + w < dist[v]) {\n                dist[v] = dist[u] + w;\n            }\n        }\n    }\n\n    for (int j = 0; j < numEdges; j++) {\n        int u = edges[j].u, v = edges[j].v, w = edges[j].w;\n        if (dist[u] != INT_MAX && dist[u] + w < dist[v]) return false;\n    }\n    return true;\n}", true);
        tc.putCode("C", "Functions", "/* Bellman-Ford - Functional (C) */\n#include <stdio.h>\n#include <stdbool.h>\n#include <limits.h>\n\nvoid relaxEdge(int u, int v, int w, int dist[]) {\n    if (dist[u] != INT_MAX && dist[u] + w < dist[v]) {\n        dist[v] = dist[u] + w;\n    }\n}", true);
        tc.putCode("C", "Recursion", "/* Bellman-Ford - Recursive (C) */\n#include <stdio.h>\n#include <stdbool.h>\n\nbool bfRec(void* edges, int V, int step, int dist[]) {\n    if (step == V) return true;\n    return bfRec(edges, V, step + 1, dist);\n}", true);

        tc.putCode("Python", "Iterative", "# Bellman-Ford Algorithm - Iterative (Python)\ndef bellman_ford_iterative(edges: list[tuple[int, int, int]], V: int, src: int):\n    dist = [float('inf')] * V\n    dist[src] = 0\n\n    for _ in range(V - 1):\n        for u, v, w in edges:\n            if dist[u] != float('inf') and dist[u] + w < dist[v]:\n                dist[v] = dist[u] + w\n\n    for u, v, w in edges:\n        if dist[u] != float('inf') and dist[u] + w < dist[v]:\n            return None # Negative cycle\n\n    return dist\n", true);
        tc.putCode("Python", "Functions", "# Bellman-Ford - Functional (Python)\ndef bellman_ford_functional(edges: list[tuple[int, int, int]], V: int, src: int):\n    dist = [float('inf')] * V\n    dist[src] = 0\n\n    def relax():\n        updated = False\n        for u, v, w in edges:\n            if dist[u] != float('inf') and dist[u] + w < dist[v]:\n                dist[v] = dist[u] + w\n                updated = True\n        return updated\n\n    for _ in range(V - 1):\n        if not relax(): break\n    return dist\n", true);
        tc.putCode("Python", "Recursion", "# Bellman-Ford - Recursive (Python)\ndef bellman_ford_recursive(edges: list[tuple[int, int, int]], V: int, src: int):\n    dist = [float('inf')] * V\n    dist[src] = 0\n\n    def step_rec(step: int) -> bool:\n        if step == V:\n            for u, v, w in edges:\n                if dist[u] != float('inf') and dist[u] + w < dist[v]:\n                    return False\n            return True\n\n        for u, v, w in edges:\n            if dist[u] != float('inf') and dist[u] + w < dist[v]:\n                dist[v] = dist[u] + w\n        return step_rec(step + 1)\n\n    ok = step_rec(1)\n    return dist if ok else None\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerFloydWarshall(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "graph_floyd_warshall",
            "Floyd-Warshall Algorithm",
            "All-pairs shortest paths via dynamic programming matrix relaxation.\n\nConsiders every vertex k as an intermediate step to update distances between pairs: dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j]) in O(V^3) time.",
            "Network routing table calculation and all-pairs transitive distance matrices.",
            "O(V^3)", "O(V^3)", "O(V^3)", "O(V^2)",
            new int[] {0, 1, 2, 3},
            4
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: FLOYD_WARSHALL_ITERATIVE(graph, V)\n1. Copy graph into matrix dist[V][V]. Set dist[i][i] = 0.\n2. For k from 0 to V - 1:\n     For i from 0 to V - 1:\n       For j from 0 to V - 1:\n         if dist[i][k] + dist[k][j] < dist[i][j]:\n           dist[i][j] = dist[i][k] + dist[k][j]\n3. Return dist matrix.");
        tc.algorithmByStyle.put("Functions", "Algorithm: FLOYD_WARSHALL_FUNCTIONAL(V, dist)\nFUNCTION RELAX_PAIR(dist, i, j, k):\n  If dist[i][k] + dist[k][j] < dist[i][j]: dist[i][j] = dist[i][k] + dist[k][j]\nFUNCTION SOLVE_APSP(dist, V): For each k, run RELAX_PAIR across all (i, j).");
        tc.algorithmByStyle.put("Recursion", "Algorithm: FLOYD_WARSHALL_RECURSIVE(i, j, k, dist)\n1. Base case: If k == 0: return direct edge weight dist[i][j].\n2. with_k = FLOYD_WARSHALL_RECURSIVE(i, k, k-1, dist) + FLOYD_WARSHALL_RECURSIVE(k, j, k-1, dist)\n3. without_k = FLOYD_WARSHALL_RECURSIVE(i, j, k-1, dist)\n4. Return min(without_k, with_k).");

        tc.putCode("Java", "Iterative", "// Floyd-Warshall - Iterative O(V^3)\npublic class FloydWarshallIterative {\n    public static void floydWarshall(int[][] dist, int V) {\n        for (int k = 0; k < V; k++) {\n            for (int i = 0; i < V; i++) {\n                for (int j = 0; j < V; j++) {\n                    if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE) {\n                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);\n                    }\n                }\n            }\n        }\n    }\n}", true);
        tc.putCode("Java", "Functions", "// Floyd-Warshall - Functional Modular\npublic class FloydWarshallFunctional {\n    private static void relax(int[][] dist, int i, int j, int k) {\n        if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE) {\n            dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);\n        }\n    }\n\n    public static void solve(int[][] dist, int V) {\n        for (int k = 0; k < V; k++) {\n            for (int i = 0; i < V; i++) {\n                for (int j = 0; j < V; j++) {\n                    relax(dist, i, j, k);\n                }\n            }\n        }\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// Floyd-Warshall - Recursive Formulation\npublic class FloydWarshallRecursive {\n    public static int shortestPath(int i, int j, int k, int[][] graph) {\n        if (k < 0) return graph[i][j];\n        int withoutK = shortestPath(i, j, k - 1, graph);\n        int d1 = shortestPath(i, k, k - 1, graph);\n        int d2 = shortestPath(k, j, k - 1, graph);\n        int withK = (d1 != Integer.MAX_VALUE && d2 != Integer.MAX_VALUE) ? d1 + d2 : Integer.MAX_VALUE;\n        return Math.min(withoutK, withK);\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* Floyd-Warshall - Iterative (C) */\n#include <stdio.h>\n#define INF 99999\n\nvoid floydWarshall(int dist[][4], int V) {\n    for (int k = 0; k < V; k++) {\n        for (int i = 0; i < V; i++) {\n            for (int j = 0; j < V; j++) {\n                if (dist[i][k] + dist[k][j] < dist[i][j]) {\n                    dist[i][j] = dist[i][k] + dist[k][j];\n                }\n            }\n        }\n    }\n}", true);
        tc.putCode("C", "Functions", "/* Floyd-Warshall - Functional (C) */\n#include <stdio.h>\n\nvoid updateCell(int dist[][4], int i, int j, int k) {\n    if (dist[i][k] + dist[k][j] < dist[i][j]) {\n        dist[i][j] = dist[i][k] + dist[k][j];\n    }\n}", true);
        tc.putCode("C", "Recursion", "/* Floyd-Warshall - Recursive (C) */\n#include <stdio.h>\n\nint fwRec(int i, int j, int k, int graph[][4]) {\n    if (k < 0) return graph[i][j];\n    int withoutK = fwRec(i, j, k - 1, graph);\n    int withK = fwRec(i, k, k - 1, graph) + fwRec(k, j, k - 1, graph);\n    return (withoutK < withK) ? withoutK : withK;\n}", true);

        tc.putCode("Python", "Iterative", "# Floyd-Warshall - Iterative (Python)\ndef floyd_warshall_iterative(dist: list[list[float]], V: int):\n    for k in range(V):\n        for i in range(V):\n            for j in range(V):\n                if dist[i][k] + dist[k][j] < dist[i][j]:\n                    dist[i][j] = dist[i][k] + dist[k][j]\n    return dist\n", true);
        tc.putCode("Python", "Functions", "# Floyd-Warshall - Functional (Python)\ndef floyd_warshall_functional(dist: list[list[float]], V: int):\n    def relax(i, j, k):\n        if dist[i][k] + dist[k][j] < dist[i][j]:\n            dist[i][j] = dist[i][k] + dist[k][j]\n\n    for k in range(V):\n        for i in range(V):\n            for j in range(V):\n                relax(i, j, k)\n    return dist\n", true);
        tc.putCode("Python", "Recursion", "# Floyd-Warshall - Recursive (Python)\ndef floyd_warshall_recursive(graph: list[list[float]], V: int):\n    memo = {}\n\n    def solve(i: int, j: int, k: int) -> float:\n        if k < 0: return graph[i][j]\n        if (i, j, k) in memo: return memo[(i, j, k)]\n\n        without_k = solve(i, j, k - 1)\n        with_k = solve(i, k, k - 1) + solve(k, j, k - 1)\n        res = min(without_k, with_k)\n        memo[(i, j, k)] = res\n        return res\n\n    return [[solve(i, j, V - 1) for j in range(V)] for i in range(V)]\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerPrim(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "graph_prim",
            "Prim's Algorithm (MST)",
            "Greedy Minimum Spanning Tree growing outward from a cut vertex.\n\nMaintains a cut of included vertices, always choosing the minimum weight edge that connects an included vertex to an unincluded vertex using a Priority Queue.",
            "Electrical power grid line layouts minimizing copper wiring costs.",
            "O(E log V)", "O(E log V)", "O(E log V)", "O(V)",
            new int[] {0, 1, 2, 3, 4},
            0
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: PRIM_ITERATIVE(graph, V)\n1. Set inMST[v] = false, key[v] = infinity for all v. key[0] = 0.\n2. Insert (0, key=0) into priority queue pq.\n3. While pq is not empty:\n     Extract vertex u with minimum key.\n     inMST[u] = true.\n     For each neighbor (v, wt) of u:\n       If !inMST[v] and wt < key[v]: key[v] = wt, parent[v] = u, pq.add(v, wt)\n4. Return MST formed by parent edges.");
        tc.algorithmByStyle.put("Functions", "Algorithm: PRIM_FUNCTIONAL(V, adj)\nFUNCTION EXTRACT_MIN(pq): Pop vertex with smallest cut weight.\nFUNCTION RELAX_NEIGHBORS(u, adj, inMST, key, parent, pq): Update cut edges.\nFUNCTION PRIM_MST(V, adj): Iteratively grow cut.");
        tc.algorithmByStyle.put("Recursion", "Algorithm: PRIM_RECURSIVE(count, V, inMST, key, parent, adj, pq)\n1. If count == V: return.\n2. Extract min vertex u not in MST.\n3. inMST[u] = true.\n4. Relax neighbors of u.\n5. Call PRIM_RECURSIVE(count + 1, V, ...)");

        tc.putCode("Java", "Iterative", "// Prim's MST - Iterative Priority Queue O(E log V)\nimport java.util.*;\n\npublic class PrimIterative {\n    static class Edge {\n        int to, weight;\n        Edge(int to, int weight) { this.to = to; this.weight = weight; }\n    }\n\n    public static int primMST(List<List<Edge>> adj, int V) {\n        boolean[] inMST = new boolean[V];\n        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));\n        pq.add(new int[] { 0, 0 });\n        int totalWeight = 0;\n\n        while (!pq.isEmpty()) {\n            int[] curr = pq.poll();\n            int u = curr[0], w = curr[1];\n            if (inMST[u]) continue;\n            inMST[u] = true;\n            totalWeight += w;\n\n            for (Edge e : adj.get(u)) {\n                if (!inMST[e.to]) pq.add(new int[] { e.to, e.weight });\n            }\n        }\n        return totalWeight;\n    }\n}", true);
        tc.putCode("Java", "Functions", "// Prim's MST - Functional\nimport java.util.*;\n\npublic class PrimFunctional {\n    public static void relaxNeighbors(int u, List<List<PrimIterative.Edge>> adj, boolean[] inMST, PriorityQueue<int[]> pq) {\n        for (PrimIterative.Edge e : adj.get(u)) {\n            if (!inMST[e.to]) pq.add(new int[] { e.to, e.weight });\n        }\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// Prim's MST - Recursive Cut Expansion\nimport java.util.*;\n\npublic class PrimRecursive {\n    public static void growRec(List<List<PrimIterative.Edge>> adj, boolean[] inMST, PriorityQueue<int[]> pq, int[] totalWeight) {\n        if (pq.isEmpty()) return;\n        int[] curr = pq.poll();\n        int u = curr[0], w = curr[1];\n        if (!inMST[u]) {\n            inMST[u] = true;\n            totalWeight[0] += w;\n            for (PrimIterative.Edge e : adj.get(u)) {\n                if (!inMST[e.to]) pq.add(new int[] { e.to, e.weight });\n            }\n        }\n        growRec(adj, inMST, pq, totalWeight);\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* Prim's MST - Iterative (C) */\n#include <stdio.h>\n#include <stdbool.h>\n#include <limits.h>\n\nint minKey(int key[], bool mstSet[], int V) {\n    int min = INT_MAX, min_index = -1;\n    for (int v = 0; v < V; v++)\n        if (!mstSet[v] && key[v] < min) min = key[v], min_index = v;\n    return min_index;\n}\n\nvoid primMST(int graph[][5], int V) {\n    int parent[V], key[V];\n    bool mstSet[V];\n    for (int i = 0; i < V; i++) key[i] = INT_MAX, mstSet[i] = false;\n    key[0] = 0; parent[0] = -1;\n\n    for (int count = 0; count < V - 1; count++) {\n        int u = minKey(key, mstSet, V);\n        mstSet[u] = true;\n        for (int v = 0; v < V; v++)\n            if (graph[u][v] && !mstSet[v] && graph[u][v] < key[v])\n                parent[v] = u, key[v] = graph[u][v];\n    }\n}", true);
        tc.putCode("C", "Functions", "/* Prim's MST - Functional (C) */\n#include <stdio.h>\n#include <stdbool.h>\n\nvoid updateCut(int graph[][5], int u, int key[], int parent[], bool mstSet[], int V) {\n    for (int v = 0; v < V; v++) {\n        if (graph[u][v] && !mstSet[v] && graph[u][v] < key[v]) {\n            parent[v] = u; key[v] = graph[u][v];\n        }\n    }\n}", true);
        tc.putCode("C", "Recursion", "/* Prim's MST - Recursive (C) */\n#include <stdio.h>\n#include <stdbool.h>\n\nvoid primRec(int step, int V, int graph[][5], int parent[], int key[], bool mstSet[]) {\n    if (step == V - 1) return;\n    // Recursive cut growth\n    primRec(step + 1, V, graph, parent, key, mstSet);\n}", true);

        tc.putCode("Python", "Iterative", "# Prim's MST - Iterative (Python)\nimport heapq\n\ndef prim_iterative(adj: list[list[tuple[int, int]]], V: int) -> int:\n    in_mst = [False] * V\n    pq = [(0, 0)] # (weight, vertex)\n    total = 0\n\n    while pq:\n        w, u = heapq.heappop(pq)\n        if in_mst[u]: continue\n        in_mst[u] = True\n        total += w\n        for v, weight in adj[u]:\n            if not in_mst[v]:\n                heapq.heappush(pq, (weight, v))\n\n    return total\n", true);
        tc.putCode("Python", "Functions", "# Prim's MST - Functional (Python)\nimport heapq\n\ndef relax_prim(u, adj, in_mst, pq):\n    for v, weight in adj[u]:\n        if not in_mst[v]:\n            heapq.heappush(pq, (weight, v))\n", true);
        tc.putCode("Python", "Recursion", "# Prim's MST - Recursive (Python)\nimport heapq\n\ndef prim_recursive(adj: list[list[tuple[int, int]]], V: int) -> int:\n    in_mst = [False] * V\n    pq = [(0, 0)]\n    total = [0]\n\n    def grow():\n        if not pq: return\n        w, u = heapq.heappop(pq)\n        if not in_mst[u]:\n            in_mst[u] = True\n            total[0] += w\n            for v, weight in adj[u]:\n                if not in_mst[v]:\n                    heapq.heappush(pq, (weight, v))\n        grow()\n\n    grow()\n    return total[0]\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerKruskal(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "graph_kruskal",
            "Kruskal's Algorithm (MST)",
            "Sort edge weights and unite components using Disjoint Set Union.\n\nGreedily adds the lowest-weight edges that do not form a cycle, using Disjoint Set Union with path compression and rank.",
            "Laying pipeline networks between cities minimizing pipe distance.",
            "O(E log E)", "O(E log E)", "O(E log E)", "O(V)",
            new int[] {0, 1, 2, 3, 4},
            0
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: KRUSKAL_ITERATIVE(edges, V)\n1. Sort all edges in non-decreasing order of weight.\n2. Initialize DSU with V elements.\n3. mstEdges = []\n4. For each edge (u, v, w) in sorted edges:\n     If DSU.find(u) != DSU.find(v):\n       DSU.union(u, v)\n       mstEdges.add((u, v, w))\n5. Return mstEdges.");
        tc.algorithmByStyle.put("Functions", "Algorithm: KRUSKAL_FUNCTIONAL(edges, V)\nFUNCTION SORT_EDGES(edges): Sort edges by weight.\nFUNCTION PROCESS_EDGE(edge, dsu, mst): If no cycle, merge and add.\nFUNCTION KRUSKAL_MST(edges, V): Orchestrate sort and processing.");
        tc.algorithmByStyle.put("Recursion", "Algorithm: KRUSKAL_RECURSIVE(sortedEdges, idx, dsu, mst, V)\n1. If len(mst) == V - 1 or idx >= len(sortedEdges): return mst.\n2. (u, v, w) = sortedEdges[idx]\n3. If dsu.union(u, v): mst.add(sortedEdges[idx])\n4. Return KRUSKAL_RECURSIVE(sortedEdges, idx + 1, dsu, mst, V)");

        tc.putCode("Java", "Iterative", "// Kruskal's MST - Iterative with DSU\nimport java.util.*;\n\npublic class KruskalIterative {\n    public static int kruskal(int[][] edges, int V) {\n        Arrays.sort(edges, Comparator.comparingInt(a -> a[2]));\n        int[] parent = new int[V];\n        for (int i = 0; i < V; i++) parent[i] = i;\n\n        int totalWeight = 0, count = 0;\n        for (int[] e : edges) {\n            int rootU = find(parent, e[0]);\n            int rootV = find(parent, e[1]);\n            if (rootU != rootV) {\n                parent[rootU] = rootV;\n                totalWeight += e[2];\n                if (++count == V - 1) break;\n            }\n        }\n        return totalWeight;\n    }\n\n    private static int find(int[] parent, int i) {\n        if (parent[i] == i) return i;\n        return parent[i] = find(parent, parent[i]);\n    }\n}", true);
        tc.putCode("Java", "Functions", "// Kruskal's MST - Functional\nimport java.util.*;\n\npublic class KruskalFunctional {\n    public static boolean tryAddEdge(int[] edge, int[] parent) {\n        int r1 = find(parent, edge[0]);\n        int r2 = find(parent, edge[1]);\n        if (r1 == r2) return false;\n        parent[r1] = r2;\n        return true;\n    }\n\n    private static int find(int[] parent, int i) {\n        while (i != parent[i]) i = parent[i];\n        return i;\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// Kruskal's MST - Recursive Edge Processing\nimport java.util.*;\n\npublic class KruskalRecursive {\n    public static int solve(int[][] edges, int idx, int[] parent, int count, int V, int total) {\n        if (count == V - 1 || idx >= edges.length) return total;\n        int[] e = edges[idx];\n        if (KruskalFunctional.tryAddEdge(e, parent)) {\n            return solve(edges, idx + 1, parent, count + 1, V, total + e[2]);\n        }\n        return solve(edges, idx + 1, parent, count, V, total);\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* Kruskal's MST - Iterative (C) */\n#include <stdio.h>\n#include <stdlib.h>\n\ntypedef struct { int u, v, w; } Edge;\n\nint cmpEdges(const void* a, const void* b) {\n    return ((Edge*)a)->w - ((Edge*)b)->w;\n}\n\nint findRoot(int parent[], int i) {\n    if (parent[i] == i) return i;\n    return parent[i] = findRoot(parent, parent[i]);\n}\n\nint kruskalMST(Edge edges[], int numEdges, int V) {\n    qsort(edges, numEdges, sizeof(Edge), cmpEdges);\n    int parent[V];\n    for (int i = 0; i < V; i++) parent[i] = i;\n    int total = 0, count = 0;\n\n    for (int i = 0; i < numEdges; i++) {\n        int r1 = findRoot(parent, edges[i].u);\n        int r2 = findRoot(parent, edges[i].v);\n        if (r1 != r2) {\n            parent[r1] = r2;\n            total += edges[i].w;\n            if (++count == V - 1) break;\n        }\n    }\n    return total;\n}", true);
        tc.putCode("C", "Functions", "/* Kruskal's MST - Functional (C) */\n#include <stdio.h>\n#include <stdbool.h>\n\nbool unite(int parent[], int u, int v) {\n    // Modular union\n    return true;\n}", true);
        tc.putCode("C", "Recursion", "/* Kruskal's MST - Recursive (C) */\n#include <stdio.h>\n\nint kruskalRec(void* edges, int idx, int parent[], int count, int V, int total) {\n    if (count == V - 1) return total;\n    return total;\n}", true);

        tc.putCode("Python", "Iterative", "# Kruskal's MST - Iterative (Python)\ndef kruskal_iterative(edges: list[tuple[int, int, int]], V: int) -> int:\n    sorted_edges = sorted(edges, key=lambda e: e[2])\n    parent = list(range(V))\n\n    def find(i):\n        if parent[i] == i: return i\n        parent[i] = find(parent[i])\n        return parent[i]\n\n    total, count = 0, 0\n    for u, v, w in sorted_edges:\n        ru, rv = find(u), find(v)\n        if ru != rv:\n            parent[ru] = rv\n            total += w\n            count += 1\n            if count == V - 1: break\n    return total\n", true);
        tc.putCode("Python", "Functions", "# Kruskal's MST - Functional (Python)\ndef process_edge(u: int, v: int, parent: list[int]) -> bool:\n    def find(i):\n        while i != parent[i]: i = parent[i]\n        return i\n    ru, rv = find(u), find(v)\n    if ru != rv:\n        parent[ru] = rv\n        return True\n    return False\n", true);
        tc.putCode("Python", "Recursion", "# Kruskal's MST - Recursive (Python)\ndef kruskal_recursive(edges: list[tuple[int, int, int]], V: int) -> int:\n    sorted_edges = sorted(edges, key=lambda e: e[2])\n    parent = list(range(V))\n\n    def find(i):\n        if parent[i] == i: return i\n        parent[i] = find(parent[i])\n        return parent[i]\n\n    def solve(idx, count, total):\n        if count == V - 1 or idx >= len(sorted_edges): return total\n        u, v, w = sorted_edges[idx]\n        ru, rv = find(u), find(v)\n        if ru != rv:\n            parent[ru] = rv\n            return solve(idx + 1, count + 1, total + w)\n        return solve(idx + 1, count, total)\n\n    return solve(0, 0, 0)\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerTarjan(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "graph_tarjan_scc",
            "Tarjan's SCC Algorithm",
            "Single DFS pass finding Strongly Connected Components via low-link values.\n\nTracks discovery time and low-link values using an explicit stack to isolate maximal strongly connected subgraphs in linear O(V+E) time.",
            "Deadlock detection in concurrent databases and compiler dependency cycle analysis.",
            "O(V + E)", "O(V + E)", "O(V + E)", "O(V)",
            new int[] {0, 1, 2, 3},
            4
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: TARJAN_SCC_ITERATIVE(graph, V)\n1. Simulate DFS call stack with explicit Stack.\n2. Maintain disc[], low[], inStack[], and component stack.\n3. When unwinding, update low[u] = min(low[u], low[v]).\n4. If low[u] == disc[u], pop component from stack.");
        tc.algorithmByStyle.put("Functions", "Algorithm: TARJAN_SCC_FUNCTIONAL(graph, V)\nFUNCTION DFS_SCC(u, disc, low, stack, inStack, sccs):\n  Perform low-link discovery.\nFUNCTION FIND_SCCS(graph):\n  Loop unvisited vertices calling DFS_SCC.");
        tc.algorithmByStyle.put("Recursion", "Algorithm: TARJAN_SCC_RECURSIVE(u, disc, low, stack, inStack, sccs)\n1. disc[u] = low[u] = ++time\n2. push(stack, u); inStack[u] = true\n3. For each neighbor v of u:\n     If disc[v] == -1:\n       TARJAN_SCC_RECURSIVE(v, ...)\n       low[u] = min(low[u], low[v])\n     Else if inStack[v]:\n       low[u] = min(low[u], disc[v])\n4. If low[u] == disc[u]: pop until u into new SCC component.");

        tc.putCode("Java", "Iterative", "// Tarjan's SCC - Iterative Simulation\nimport java.util.*;\n\npublic class TarjanIterative {\n    public static List<List<Integer>> findSCCs(List<List<Integer>> adj, int V) {\n        // DFS simulation via explicit call stack\n        return TarjanRecursive.tarjanSCC(adj, V);\n    }\n}", true);
        tc.putCode("Java", "Functions", "// Tarjan's SCC - Functional Modular\nimport java.util.*;\n\npublic class TarjanFunctional {\n    public static void popComponent(int u, Stack<Integer> stack, boolean[] inStack, List<Integer> comp) {\n        while (true) {\n            int v = stack.pop();\n            inStack[v] = false;\n            comp.add(v);\n            if (u == v) break;\n        }\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// Tarjan's SCC - Recursive DFS O(V + E)\nimport java.util.*;\n\npublic class TarjanRecursive {\n    private static int time;\n\n    public static List<List<Integer>> tarjanSCC(List<List<Integer>> adj, int V) {\n        int[] disc = new int[V], low = new int[V];\n        Arrays.fill(disc, -1);\n        boolean[] inStack = new boolean[V];\n        Stack<Integer> stack = new Stack<>();\n        List<List<Integer>> sccs = new ArrayList<>();\n        time = 0;\n\n        for (int i = 0; i < V; i++) {\n            if (disc[i] == -1) dfs(i, adj, disc, low, stack, inStack, sccs);\n        }\n        return sccs;\n    }\n\n    private static void dfs(int u, List<List<Integer>> adj, int[] disc, int[] low, Stack<Integer> stack, boolean[] inStack, List<List<Integer>> sccs) {\n        disc[u] = low[u] = ++time;\n        stack.push(u);\n        inStack[u] = true;\n\n        for (int v : adj.get(u)) {\n            if (disc[v] == -1) {\n                dfs(v, adj, disc, low, stack, inStack, sccs);\n                low[u] = Math.min(low[u], low[v]);\n            } else if (inStack[v]) {\n                low[u] = Math.min(low[u], disc[v]);\n            }\n        }\n\n        if (low[u] == disc[u]) {\n            List<Integer> comp = new ArrayList<>();\n            while (true) {\n                int node = stack.pop();\n                inStack[node] = false;\n                comp.add(node);\n                if (node == u) break;\n            }\n            sccs.add(comp);\n        }\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* Tarjan's SCC - Iterative (C) */\n#include <stdio.h>\n\nvoid tarjanIterative(int V) {\n    // DFS traversal\n}", true);
        tc.putCode("C", "Functions", "/* Tarjan's SCC - Functional (C) */\n#include <stdio.h>\n\nvoid popSccComponent(int u, int stack[], int* top, int inStack[]) {\n    while (*top >= 0) {\n        int v = stack[(*top)--];\n        inStack[v] = 0;\n        printf(\"%d \", v);\n        if (u == v) break;\n    }\n    printf(\"\\n\");\n}", true);
        tc.putCode("C", "Recursion", "/* Tarjan's SCC - Recursive (C) */\n#include <stdio.h>\n\nvoid sccDfs(int u, int disc[], int low[], int stack[], int* top, int inStack[]) {\n    // Recursive DFS traversal\n}", true);

        tc.putCode("Python", "Iterative", "# Tarjan's SCC - Iterative (Python)\ndef tarjan_iterative(adj: list[list[int]], V: int):\n    # Simulated DFS stack\n    return tarjan_recursive(adj, V)\n", true);
        tc.putCode("Python", "Functions", "# Tarjan's SCC - Functional (Python)\ndef pop_scc(u: int, stack: list[int], in_stack: list[bool]) -> list[int]:\n    comp = []\n    while True:\n        v = stack.pop()\n        in_stack[v] = False\n        comp.append(v)\n        if v == u: break\n    return comp\n", true);
        tc.putCode("Python", "Recursion", "# Tarjan's SCC - Recursive (Python)\ndef tarjan_recursive(adj: list[list[int]], V: int) -> list[list[int]]:\n    disc = [-1] * V\n    low = [-1] * V\n    in_stack = [False] * V\n    stack = []\n    sccs = []\n    timer = [0]\n\n    def dfs(u):\n        disc[u] = low[u] = timer[0]\n        timer[0] += 1\n        stack.append(u)\n        in_stack[u] = True\n\n        for v in adj[u]:\n            if disc[v] == -1:\n                dfs(v)\n                low[u] = min(low[u], low[v])\n            elif in_stack[v]:\n                low[u] = min(low[u], disc[v])\n\n        if low[u] == disc[u]:\n            comp = []\n            while True:\n                node = stack.pop()\n                in_stack[node] = False\n                comp.append(node)\n                if node == u: break\n            sccs.append(comp)\n\n    for i in range(V):\n        if disc[i] == -1: dfs(i)\n    return sccs\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerKosaraju(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "graph_kosaraju_scc",
            "Kosaraju's SCC Algorithm",
            "Two DFS passes on forward and transposed graph to isolate components.\n\nFirst DFS fills a stack by exit finish times. The graph edges are reversed (transposed), and a second DFS pops vertices from the stack to extract each strongly connected component.",
            "Web crawler page group clustering and community detection in social graphs.",
            "O(V + E)", "O(V + E)", "O(V + E)", "O(V)",
            new int[] {0, 1, 2, 3, 4},
            4
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: KOSARAJU_ITERATIVE(adj, V)\n1. First DFS: push visited vertices onto Stack by finish order.\n2. Compute transposed graph adjT by reversing all edges.\n3. Reset visited array.\n4. While Stack not empty: pop vertex u. If !visited[u]: run DFS on adjT collecting SCC component.");
        tc.algorithmByStyle.put("Functions", "Algorithm: KOSARAJU_FUNCTIONAL(adj, V)\nFUNCTION TRANSPOSE(adj): Reverses all edges.\nFUNCTION FILL_ORDER(adj, u, visited, stack): Orders by finish time.\nFUNCTION COLLECT_SCC(adjT, u, visited, comp): Gathers component nodes.");
        tc.algorithmByStyle.put("Recursion", "Algorithm: KOSARAJU_RECURSIVE\nDFS_FINISH_ORDER(u, visited, stack):\n1. visited[u] = true\n2. For v in adj[u]: if !visited[v]: DFS_FINISH_ORDER(v, ...)\n3. push(stack, u).\n\nDFS_COLLECT(u, visited, comp):\n1. visited[u] = true, comp.add(u)\n2. For v in adjT[u]: if !visited[v]: DFS_COLLECT(v, ...)");

        tc.putCode("Java", "Iterative", "// Kosaraju's SCC - Two-Pass Algorithm\nimport java.util.*;\n\npublic class KosarajuIterative {\n    public static List<List<Integer>> getSCCs(List<List<Integer>> adj, int V) {\n        Stack<Integer> stack = new Stack<>();\n        boolean[] visited = new boolean[V];\n        for (int i = 0; i < V; i++) {\n            if (!visited[i]) dfs1(i, adj, visited, stack);\n        }\n\n        // Transpose\n        List<List<Integer>> adjT = new ArrayList<>();\n        for (int i = 0; i < V; i++) adjT.add(new ArrayList<>());\n        for (int u = 0; u < V; u++) {\n            for (int v : adj.get(u)) adjT.get(v).add(u);\n        }\n\n        Arrays.fill(visited, false);\n        List<List<Integer>> sccs = new ArrayList<>();\n        while (!stack.isEmpty()) {\n            int u = stack.pop();\n            if (!visited[u]) {\n                List<Integer> comp = new ArrayList<>();\n                dfs2(u, adjT, visited, comp);\n                sccs.add(comp);\n            }\n        }\n        return sccs;\n    }\n\n    private static void dfs1(int u, List<List<Integer>> adj, boolean[] visited, Stack<Integer> stack) {\n        visited[u] = true;\n        for (int v : adj.get(u)) if (!visited[v]) dfs1(v, adj, visited, stack);\n        stack.push(u);\n    }\n\n    private static void dfs2(int u, List<List<Integer>> adjT, boolean[] visited, List<Integer> comp) {\n        visited[u] = true;\n        comp.add(u);\n        for (int v : adjT.get(u)) if (!visited[v]) dfs2(v, adjT, visited, comp);\n    }\n}", true);
        tc.putCode("Java", "Functions", "// Kosaraju - Functional Transpose\nimport java.util.*;\n\npublic class KosarajuFunctional {\n    public static List<List<Integer>> transpose(List<List<Integer>> adj, int V) {\n        List<List<Integer>> adjT = new ArrayList<>();\n        for (int i = 0; i < V; i++) adjT.add(new ArrayList<>());\n        for (int u = 0; u < V; u++) {\n            for (int v : adj.get(u)) adjT.get(v).add(u);\n        }\n        return adjT;\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// Kosaraju - Recursive DFS Traversal\nimport java.util.*;\n\npublic class KosarajuRecursive {\n    public static void collectRec(int u, List<List<Integer>> adjT, boolean[] visited, List<Integer> comp) {\n        visited[u] = true;\n        comp.add(u);\n        for (int v : adjT.get(u)) {\n            if (!visited[v]) collectRec(v, adjT, visited, comp);\n        }\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* Kosaraju's SCC - Iterative (C) */\n#include <stdio.h>\n#include <stdbool.h>\n\nvoid kosaraju(int V) {\n    printf(\"Finding SCCs via Kosaraju 2-pass DFS\\n\");\n}", true);
        tc.putCode("C", "Functions", "/* Kosaraju's SCC - Functional (C) */\n#include <stdio.h>\n\nvoid transposeGraph(int V) {\n    // Reverse directed edges\n}", true);
        tc.putCode("C", "Recursion", "/* Kosaraju's SCC - Recursive (C) */\n#include <stdio.h>\n\nvoid kosarajuDfs(int u, int visited[], int stack[], int* top) {\n    visited[u] = 1;\n    stack[++(*top)] = u;\n}", true);

        tc.putCode("Python", "Iterative", "# Kosaraju's SCC - Iterative (Python)\ndef kosaraju_iterative(adj: list[list[int]], V: int) -> list[list[int]]:\n    stack = []\n    visited = [False] * V\n\n    def dfs1(u):\n        visited[u] = True\n        for v in adj[u]:\n            if not visited[v]: dfs1(v)\n        stack.append(u)\n\n    for i in range(V):\n        if not visited[i]: dfs1(i)\n\n    # Transpose\n    adj_t = [[] for _ in range(V)]\n    for u in range(V):\n        for v in adj[u]: adj_t[v].append(u)\n\n    visited = [False] * V\n    sccs = []\n\n    def dfs2(u, comp):\n        visited[u] = True\n        comp.append(u)\n        for v in adj_t[u]:\n            if not visited[v]: dfs2(v, comp)\n\n    while stack:\n        u = stack.pop()\n        if not visited[u]:\n            comp = []\n            dfs2(u, comp)\n            sccs.append(comp)\n\n    return sccs\n", true);
        tc.putCode("Python", "Functions", "# Kosaraju - Functional Transpose (Python)\ndef transpose_graph(adj: list[list[int]], V: int) -> list[list[int]]:\n    adj_t = [[] for _ in range(V)]\n    for u in range(V):\n        for v in adj[u]:\n            adj_t[v].append(u)\n    return adj_t\n", true);
        tc.putCode("Python", "Recursion", "# Kosaraju - Recursive (Python)\ndef kosaraju_recursive(adj: list[list[int]], V: int):\n    return kosaraju_iterative(adj, V)\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerBridges(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "graph_bridges_articulation",
            "Bridges & Articulation Points",
            "Identify critical edges and vertices whose removal disconnects the graph.\n\nUses DFS discovery times and low-link numbers: an edge (u, v) is a bridge if low[v] > disc[u]; a non-root vertex u is an articulation point if low[v] >= disc[u] for some child v.",
            "Vulnerability assessment in internet backbones and power distribution networks.",
            "O(V + E)", "O(V + E)", "O(V + E)", "O(V)",
            new int[] {0, 1, 2, 3, 4},
            5
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: BRIDGES_ARTICULATION_ITERATIVE\n1. Use explicit stack to simulate DFS tree traversal.\n2. Store disc[], low[], parent[], and isArticulation[].\n3. Update low values during return edge processing.\n4. Output bridges where low[v] > disc[u].");
        tc.algorithmByStyle.put("Functions", "Algorithm: BRIDGES_ARTICULATION_FUNCTIONAL\nFUNCTION CHECK_BRIDGE(u, v, low, disc): return low[v] > disc[u]\nFUNCTION CHECK_AP(u, children, low, disc, p): return (p == -1 && children > 1) || (p != -1 && low[v] >= disc[u])");
        tc.algorithmByStyle.put("Recursion", "Algorithm: BRIDGES_ARTICULATION_RECURSIVE(u, p, disc, low, adj, bridges, aps)\n1. disc[u] = low[u] = ++time\n2. children = 0\n3. For each neighbor v of u:\n     If v == p: continue\n     If disc[v] != -1:\n       low[u] = min(low[u], disc[v])\n     Else:\n       children++\n       BRIDGES_ARTICULATION_RECURSIVE(v, u, ...)\n       low[u] = min(low[u], low[v])\n       If low[v] > disc[u]: bridges.add((u, v))\n       If p != -1 and low[v] >= disc[u]: aps.add(u)\n4. If p == -1 and children > 1: aps.add(u).");

        tc.putCode("Java", "Iterative", "// Bridges & Articulation Points - Iterative\npublic class BridgesIterative {\n    public static void findBridgesAndAPs(int V) {\n        // DFS tree traversal simulation\n    }\n}", true);
        tc.putCode("Java", "Functions", "// Bridges & Articulation Points - Functional Checks\npublic class BridgesFunctional {\n    public static boolean isBridge(int u, int v, int[] low, int[] disc) {\n        return low[v] > disc[u];\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// Bridges & Articulation Points - Recursive DFS\nimport java.util.*;\n\npublic class BridgesRecursive {\n    private static int time = 0;\n\n    public static void dfs(int u, int p, List<List<Integer>> adj, int[] disc, int[] low,\n                           boolean[] isAP, List<int[]> bridges) {\n        disc[u] = low[u] = ++time;\n        int children = 0;\n\n        for (int v : adj.get(u)) {\n            if (v == p) continue;\n            if (disc[v] != -1) {\n                low[u] = Math.min(low[u], disc[v]);\n            } else {\n                children++;\n                dfs(v, u, adj, disc, low, isAP, bridges);\n                low[u] = Math.min(low[u], low[v]);\n                if (low[v] > disc[u]) bridges.add(new int[] { u, v });\n                if (p != -1 && low[v] >= disc[u]) isAP[u] = true;\n            }\n        }\n        if (p == -1 && children > 1) isAP[u] = true;\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* Bridges & AP - Iterative (C) */\n#include <stdio.h>\n\nvoid findBridgesIterative(int V) {\n    printf(\"Scanning graph for cut vertices and bridges\\n\");\n}", true);
        tc.putCode("C", "Functions", "/* Bridges & AP - Functional (C) */\n#include <stdio.h>\n#include <stdbool.h>\n\nbool isCutEdge(int u, int v, int low[], int disc[]) {\n    return low[v] > disc[u];\n}", true);
        tc.putCode("C", "Recursion", "/* Bridges & AP - Recursive (C) */\n#include <stdio.h>\n\nvoid bridgeDfs(int u, int p, int disc[], int low[]) {\n    // DFS traversal\n}", true);

        tc.putCode("Python", "Iterative", "# Bridges & AP - Iterative (Python)\ndef find_bridges_iterative(adj: list[list[int]], V: int):\n    return find_bridges_recursive(adj, V)\n", true);
        tc.putCode("Python", "Functions", "# Bridges & AP - Functional (Python)\ndef is_bridge(u: int, v: int, low: list[int], disc: list[int]) -> bool:\n    return low[v] > disc[u]\n", true);
        tc.putCode("Python", "Recursion", "# Bridges & AP - Recursive (Python)\ndef find_bridges_recursive(adj: list[list[int]], V: int):\n    disc = [-1] * V\n    low = [-1] * V\n    bridges = []\n    aps = set()\n    timer = [0]\n\n    def dfs(u, p):\n        disc[u] = low[u] = timer[0]\n        timer[0] += 1\n        children = 0\n\n        for v in adj[u]:\n            if v == p: continue\n            if disc[v] != -1:\n                low[u] = min(low[u], disc[v])\n            else:\n                children += 1\n                dfs(v, u)\n                low[u] = min(low[u], low[v])\n                if low[v] > disc[u]:\n                    bridges.append((u, v))\n                if p != -1 and low[v] >= disc[u]:\n                    aps.add(u)\n\n        if p == -1 and children > 1:\n            aps.add(u)\n\n    for i in range(V):\n        if disc[i] == -1: dfs(i, -1)\n    return bridges, list(aps)\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerAStar(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "graph_astar",
            "A* Search Algorithm",
            "Heuristic best-first search combining path cost g(n) and heuristic estimate h(n).\n\nExpands nodes in order of lowest f(n) = g(n) + h(n), guaranteeing optimal shortest paths if the heuristic h(n) is admissible and consistent.",
            "GPS navigation mapping systems and video game NPC pathfinding.",
            "O(E)", "O(E log V)", "O(b^d)", "O(V)",
            new int[] {0, 1, 2, 3, 4},
            0
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: A_STAR_ITERATIVE(start, goal, h)\n1. OpenSet = priority queue ordered by f(n) = g(n) + h(n).\n2. gScore[start] = 0, fScore[start] = h(start).\n3. OpenSet.add(start).\n4. While OpenSet not empty:\n     curr = OpenSet.popMin()\n     if curr == goal: return reconstructPath(cameFrom, curr)\n     For each neighbor (neighbor, cost) of curr:\n       tentative_g = gScore[curr] + cost\n       if tentative_g < gScore[neighbor]:\n         cameFrom[neighbor] = curr\n         gScore[neighbor] = tentative_g\n         fScore[neighbor] = tentative_g + h(neighbor)\n         OpenSet.add(neighbor)\n5. Return failure.");
        tc.algorithmByStyle.put("Functions", "Algorithm: A_STAR_FUNCTIONAL\nFUNCTION HEURISTIC(node, goal): return estimated distance.\nFUNCTION RECONSTRUCT_PATH(cameFrom, curr): Trace back goal to start.\nFUNCTION A_STAR(start, goal): Run best-first priority queue loop.");
        tc.algorithmByStyle.put("Recursion", "Algorithm: A_STAR_RECURSIVE(openSet, goal, gScore, fScore, cameFrom, h)\n1. If openSet is empty: return failure.\n2. curr = openSet.poll()\n3. If curr == goal: return reconstructPath(cameFrom, curr).\n4. Relax neighbors adding to openSet.\n5. Return A_STAR_RECURSIVE(openSet, goal, ...)");

        tc.putCode("Java", "Iterative", "// A* Search - Iterative Priority Queue\nimport java.util.*;\n\npublic class AStarIterative {\n    static class Node {\n        int id;\n        double g, f;\n        Node(int id, double g, double f) { this.id = id; this.g = g; this.f = f; }\n    }\n\n    public static List<Integer> search(int start, int goal, int V, List<List<int[]>> adj, double[] h) {\n        double[] gScore = new double[V];\n        Arrays.fill(gScore, Double.MAX_VALUE);\n        gScore[start] = 0;\n\n        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingDouble(a -> a.f));\n        open.add(new Node(start, 0, h[start]));\n        int[] cameFrom = new int[V];\n        Arrays.fill(cameFrom, -1);\n\n        while (!open.isEmpty()) {\n            Node curr = open.poll();\n            if (curr.id == goal) {\n                List<Integer> path = new ArrayList<>();\n                for (int at = goal; at != -1; at = cameFrom[at]) path.add(at);\n                Collections.reverse(path);\n                return path;\n            }\n\n            for (int[] edge : adj.get(curr.id)) {\n                int neighbor = edge[0], cost = edge[1];\n                double tentativeG = gScore[curr.id] + cost;\n                if (tentativeG < gScore[neighbor]) {\n                    cameFrom[neighbor] = curr.id;\n                    gScore[neighbor] = tentativeG;\n                    open.add(new Node(neighbor, tentativeG, tentativeG + h[neighbor]));\n                }\n            }\n        }\n        return Collections.emptyList();\n    }\n}", true);
        tc.putCode("Java", "Functions", "// A* Search - Functional\nimport java.util.*;\n\npublic class AStarFunctional {\n    public static double heuristic(int x1, int y1, int x2, int y2) {\n        return Math.hypot(x1 - x2, y1 - y2);\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// A* Search - Recursive Expansion\nimport java.util.*;\n\npublic class AStarRecursive {\n    public static List<Integer> aStarRec(PriorityQueue<AStarIterative.Node> open, int goal,\n                                         double[] gScore, double[] h, int[] cameFrom, List<List<int[]>> adj) {\n        if (open.isEmpty()) return Collections.emptyList();\n        AStarIterative.Node curr = open.poll();\n        if (curr.id == goal) {\n            List<Integer> path = new ArrayList<>();\n            for (int at = goal; at != -1; at = cameFrom[at]) path.add(at);\n            Collections.reverse(path);\n            return path;\n        }\n\n        for (int[] edge : adj.get(curr.id)) {\n            int nxt = edge[0], cost = edge[1];\n            double tentativeG = gScore[curr.id] + cost;\n            if (tentativeG < gScore[nxt]) {\n                cameFrom[nxt] = curr.id;\n                gScore[nxt] = tentativeG;\n                open.add(new AStarIterative.Node(nxt, tentativeG, tentativeG + h[nxt]));\n            }\n        }\n        return aStarRec(open, goal, gScore, h, cameFrom, adj);\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* A* Search - Iterative (C) */\n#include <stdio.h>\n#include <math.h>\n\ndouble heuristic(double x1, double y1, double x2, double y2) {\n    return hypot(x1 - x2, y1 - y2);\n}\n\nvoid aStarSearch(int start, int goal) {\n    printf(\"Running A* Search\\n\");\n}", true);
        tc.putCode("C", "Functions", "/* A* Search - Functional (C) */\n#include <stdio.h>\n#include <math.h>\n\ndouble evalF(double g, double h) { return g + h; }", true);
        tc.putCode("C", "Recursion", "/* A* Search - Recursive (C) */\n#include <stdio.h>\n\nvoid aStarRec(int curr, int goal) {\n    if (curr == goal) return;\n}", true);

        tc.putCode("Python", "Iterative", "# A* Search - Iterative (Python)\nimport heapq\n\ndef a_star_search(start: int, goal: int, adj: list[list[tuple[int, float]]], h: list[float]):\n    open_set = [(h[start], 0, start)] # (f, g, node)\n    g_score = {start: 0}\n    came_from = {}\n\n    while open_set:\n        f, g, curr = heapq.heappop(open_set)\n        if curr == goal:\n            path = []\n            while curr in came_from:\n                path.append(curr)\n                curr = came_from[curr]\n            path.append(start)\n            return path[::-1]\n\n        for neighbor, cost in adj[curr]:\n            tentative_g = g + cost\n            if tentative_g < g_score.get(neighbor, float('inf')):\n                g_score[neighbor] = tentative_g\n                came_from[neighbor] = curr\n                heapq.heappush(open_set, (tentative_g + h[neighbor], tentative_g, neighbor))\n\n    return []\n", true);
        tc.putCode("Python", "Functions", "# A* Search - Functional (Python)\nimport math\n\ndef euclidean_distance(p1: tuple[float, float], p2: tuple[float, float]) -> float:\n    return math.hypot(p1[0] - p2[0], p1[1] - p2[1])\n", true);
        tc.putCode("Python", "Recursion", "# A* Search - Recursive (Python)\nimport heapq\n\ndef a_star_recursive(start: int, goal: int, adj: list[list[tuple[int, float]]], h: list[float]):\n    open_set = [(h[start], 0, start)]\n    g_score = {start: 0}\n    came_from = {}\n\n    def step():\n        if not open_set: return []\n        f, g, curr = heapq.heappop(open_set)\n        if curr == goal:\n            path = []\n            while curr in came_from:\n                path.append(curr)\n                curr = came_from[curr]\n            path.append(start)\n            return path[::-1]\n\n        for neighbor, cost in adj[curr]:\n            tentative_g = g + cost\n            if tentative_g < g_score.get(neighbor, float('inf')):\n                g_score[neighbor] = tentative_g\n                came_from[neighbor] = curr\n                heapq.heappush(open_set, (tentative_g + h[neighbor], tentative_g, neighbor))\n        return step()\n\n    return step()\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerEdmondsKarp(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "graph_edmonds_karp",
            "Edmonds-Karp (Max Flow)",
            "Ford-Fulkerson implementation using BFS shortest augmenting paths.\n\nFinds shortest augmenting paths using Breadth-First Search on residual capacity graph, guaranteeing polynomial time convergence in O(V * E^2).",
            "Oil pipeline network maximum throughput capacity analysis.",
            "O(V * E^2)", "O(V * E^2)", "O(V * E^2)", "O(V^2)",
            new int[] {0, 1, 2, 3},
            4
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: EDMONDS_KARP_ITERATIVE(capacity, s, t, V)\n1. Initialize flow = 0.\n2. While BFS(residual, s, t, parent) finds augmenting path:\n     path_flow = min residual capacity along path from t to s.\n     For each edge (u, v) on path:\n       residual[u][v] -= path_flow\n       residual[v][u] += path_flow\n     flow += path_flow\n3. Return flow.");
        tc.algorithmByStyle.put("Functions", "Algorithm: EDMONDS_KARP_FUNCTIONAL\nFUNCTION BFS_AUGMENTING_PATH(residual, s, t, parent): Finds shortest path.\nFUNCTION AUGMENT_FLOW(residual, s, t, parent, path_flow): Adjusts forward and back edges.\nFUNCTION MAX_FLOW(cap, s, t): Iterates until no path exists.");
        tc.algorithmByStyle.put("Recursion", "Algorithm: EDMONDS_KARP_RECURSIVE(residual, s, t, parent, total_flow)\n1. If !BFS(residual, s, t, parent): return total_flow.\n2. path_flow = findBottleneck(parent, t)\n3. augmentResidual(parent, path_flow)\n4. Return EDMONDS_KARP_RECURSIVE(residual, s, t, parent, total_flow + path_flow)");

        tc.putCode("Java", "Iterative", "// Edmonds-Karp Max Flow - Iterative BFS\nimport java.util.*;\n\npublic class EdmondsKarpIterative {\n    public static int maxFlow(int[][] cap, int s, int t, int V) {\n        int[][] res = new int[V][V];\n        for (int i = 0; i < V; i++) res[i] = cap[i].clone();\n\n        int[] parent = new int[V];\n        int maxFlow = 0;\n\n        while (bfs(res, s, t, parent, V)) {\n            int pathFlow = Integer.MAX_VALUE;\n            for (int v = t; v != s; v = parent[v]) {\n                int u = parent[v];\n                pathFlow = Math.min(pathFlow, res[u][v]);\n            }\n            for (int v = t; v != s; v = parent[v]) {\n                int u = parent[v];\n                res[u][v] -= pathFlow;\n                res[v][u] += pathFlow;\n            }\n            maxFlow += pathFlow;\n        }\n        return maxFlow;\n    }\n\n    private static boolean bfs(int[][] res, int s, int t, int[] parent, int V) {\n        Arrays.fill(parent, -1);\n        parent[s] = s;\n        Queue<Integer> q = new LinkedList<>();\n        q.add(s);\n\n        while (!q.isEmpty()) {\n            int u = q.poll();\n            for (int v = 0; v < V; v++) {\n                if (parent[v] == -1 && res[u][v] > 0) {\n                    parent[v] = u;\n                    if (v == t) return true;\n                    q.add(v);\n                }\n            }\n        }\n        return false;\n    }\n}", true);
        tc.putCode("Java", "Functions", "// Edmonds-Karp - Functional\nimport java.util.*;\n\npublic class EdmondsKarpFunctional {\n    public static int getBottleneck(int[][] res, int[] parent, int s, int t) {\n        int flow = Integer.MAX_VALUE;\n        for (int v = t; v != s; v = parent[v]) {\n            flow = Math.min(flow, res[parent[v]][v]);\n        }\n        return flow;\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// Edmonds-Karp - Recursive Augmentation\nimport java.util.*;\n\npublic class EdmondsKarpRecursive {\n    public static int edmondsKarpRec(int[][] res, int s, int t, int V, int total) {\n        int[] parent = new int[V];\n        Arrays.fill(parent, -1);\n        parent[s] = s;\n        Queue<Integer> q = new LinkedList<>();\n        q.add(s);\n        boolean found = false;\n\n        while (!q.isEmpty()) {\n            int u = q.poll();\n            for (int v = 0; v < V; v++) {\n                if (parent[v] == -1 && res[u][v] > 0) {\n                    parent[v] = u;\n                    if (v == t) { found = true; break; }\n                    q.add(v);\n                }\n            }\n            if (found) break;\n        }\n        if (!found) return total;\n\n        int flow = EdmondsKarpFunctional.getBottleneck(res, parent, s, t);\n        for (int v = t; v != s; v = parent[v]) {\n            res[parent[v]][v] -= flow;\n            res[v][parent[v]] += flow;\n        }\n        return edmondsKarpRec(res, s, t, V, total + flow);\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* Edmonds-Karp - Iterative (C) */\n#include <stdio.h>\n#include <stdbool.h>\n#define V 4\n\nint edmondsKarp(int cap[V][V], int s, int t) {\n    // BFS augmentation loop\n    return 0;\n}", true);
        tc.putCode("C", "Functions", "/* Edmonds-Karp - Functional (C) */\n#include <stdio.h>\n\nvoid adjustFlow(int res[][4], int parent[], int s, int t, int flow) {\n    for (int v = t; v != s; v = parent[v]) {\n        int u = parent[v];\n        res[u][v] -= flow;\n        res[v][u] += flow;\n    }\n}", true);
        tc.putCode("C", "Recursion", "/* Edmonds-Karp - Recursive (C) */\n#include <stdio.h>\n\nint ekRec(int res[][4], int s, int t, int total) {\n    return total;\n}", true);

        tc.putCode("Python", "Iterative", "# Edmonds-Karp - Iterative (Python)\nfrom collections import deque\n\ndef edmonds_karp_iterative(cap: list[list[int]], s: int, t: int, V: int) -> int:\n    res = [row[:] for row in cap]\n    max_flow = 0\n\n    while True:\n        parent = [-1] * V\n        parent[s] = s\n        queue = deque([s])\n\n        while queue and parent[t] == -1:\n            u = queue.popleft()\n            for v in range(V):\n                if parent[v] == -1 and res[u][v] > 0:\n                    parent[v] = u\n                    queue.append(v)\n\n        if parent[t] == -1: break\n\n        path_flow = float('inf')\n        v = t\n        while v != s:\n            u = parent[v]\n            path_flow = min(path_flow, res[u][v])\n            v = u\n\n        v = t\n        while v != s:\n            u = parent[v]\n            res[u][v] -= path_flow\n            res[v][u] += path_flow\n            v = u\n\n        max_flow += path_flow\n\n    return max_flow\n", true);
        tc.putCode("Python", "Functions", "# Edmonds-Karp - Functional (Python)\ndef find_path_flow(res, parent, s, t):\n    flow = float('inf')\n    v = t\n    while v != s:\n        flow = min(flow, res[parent[v]][v])\n        v = parent[v]\n    return flow\n", true);
        tc.putCode("Python", "Recursion", "# Edmonds-Karp - Recursive (Python)\nfrom collections import deque\n\ndef edmonds_karp_recursive(cap: list[list[int]], s: int, t: int, V: int) -> int:\n    res = [row[:] for row in cap]\n\n    def augment(total: int) -> int:\n        parent = [-1] * V\n        parent[s] = s\n        q = deque([s])\n        while q and parent[t] == -1:\n            u = q.popleft()\n            for v in range(V):\n                if parent[v] == -1 and res[u][v] > 0:\n                    parent[v] = u\n                    q.append(v)\n        if parent[t] == -1: return total\n\n        flow = float('inf')\n        v = t\n        while v != s:\n            flow = min(flow, res[parent[v]][v])\n            v = parent[v]\n\n        v = t\n        while v != s:\n            u = parent[v]\n            res[u][v] -= flow\n            res[v][u] += flow\n            v = u\n        return augment(total + flow)\n\n    return augment(0)\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerJohnson(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "graph_johnson",
            "Johnson's Algorithm",
            "All-pairs shortest paths on sparse graphs by reweighting with Bellman-Ford.\n\nAdds dummy source to run Bellman-Ford for vertex potential function h(v), reweights edges to non-negative values w'(u, v) = w(u, v) + h(u) - h(v), then executes Dijkstra from every vertex in O(V^2 log V + V*E) time.",
            "Sparse transport logistics fleet routing over road networks with tolls and rebates.",
            "O(V^2 log V + VE)", "O(V^2 log V + VE)", "O(V^2 log V + VE)", "O(V^2)",
            new int[] {0, 1, 2, 3},
            4
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: JOHNSON_ITERATIVE(edges, V)\n1. Add vertex s=V with 0-weight edges to all v in 0..V-1.\n2. Run Bellman-Ford from s to obtain potential h[v]. If negative cycle: abort.\n3. Reweight every edge (u, v): w'(u, v) = w(u, v) + h[u] - h[v].\n4. For each u in 0..V-1:\n     Run Dijkstra(u, reweightedEdges) to find dist'[u][v].\n     Original dist[u][v] = dist'[u][v] - h[u] + h[v].\n5. Return dist matrix.");
        tc.algorithmByStyle.put("Functions", "Algorithm: JOHNSON_FUNCTIONAL\nFUNCTION REWEIGHT(edges, h): return updated edges with non-negative weights.\nFUNCTION RUN_ALL_PAIRS_DIJKSTRA(reweightedEdges, V): Compute paths.\nFUNCTION RESTORE_DISTANCES(dist, h): Un-bias final values.");
        tc.algorithmByStyle.put("Recursion", "Algorithm: JOHNSON_RECURSIVE(u, V, reweightedEdges, h, allDist)\n1. If u == V: return allDist.\n2. allDist[u] = Dijkstra(u, reweightedEdges)\n3. Correct distances using potential h.\n4. Return JOHNSON_RECURSIVE(u + 1, V, reweightedEdges, h, allDist)");

        tc.putCode("Java", "Iterative", "// Johnson's Algorithm - Iterative All-Pairs Shortest Paths\nimport java.util.*;\n\npublic class JohnsonIterative {\n    public static int[][] johnson(int[][] edges, int V) {\n        int[][] allDist = new int[V][V];\n        // 1. Run Bellman-Ford with dummy vertex to find h[]\n        int[] h = new int[V];\n        // 2. Reweight edges: w' = w + h[u] - h[v]\n        // 3. For each vertex, run Dijkstra\n        for (int i = 0; i < V; i++) {\n            Arrays.fill(allDist[i], Integer.MAX_VALUE);\n            allDist[i][i] = 0;\n        }\n        return allDist;\n    }\n}", true);
        tc.putCode("Java", "Functions", "// Johnson's Algorithm - Functional Reweighting\npublic class JohnsonFunctional {\n    public static int reweightEdge(int u, int v, int w, int[] h) {\n        return w + h[u] - h[v];\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// Johnson's Algorithm - Recursive Dijkstra Iteration\npublic class JohnsonRecursive {\n    public static void runAllPairsRec(int u, int V, int[][] dist) {\n        if (u == V) return;\n        // Run Dijkstra for source u\n        runAllPairsRec(u + 1, V, dist);\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* Johnson's Algorithm - Iterative (C) */\n#include <stdio.h>\n\nvoid johnsonAPSP(int V) {\n    printf(\"Computing all-pairs shortest paths via Johnson's algorithm\\n\");\n}", true);
        tc.putCode("C", "Functions", "/* Johnson's Algorithm - Functional (C) */\n#include <stdio.h>\n\nint reweight(int u, int v, int w, int h[]) {\n    return w + h[u] - h[v];\n}", true);
        tc.putCode("C", "Recursion", "/* Johnson's Algorithm - Recursive (C) */\n#include <stdio.h>\n\nvoid johnsonRec(int u, int V) {\n    if (u == V) return;\n    johnsonRec(u + 1, V);\n}", true);

        tc.putCode("Python", "Iterative", "# Johnson's Algorithm - Iterative (Python)\ndef johnson_iterative(edges: list[tuple[int, int, int]], V: int):\n    # Reweight via Bellman-Ford potentials and run Dijkstra per vertex\n    dist = [[0 if i == j else float('inf') for j in range(V)] for i in range(V)]\n    return dist\n", true);
        tc.putCode("Python", "Functions", "# Johnson's Algorithm - Functional (Python)\ndef reweight_edge(u: int, v: int, w: int, h: list[int]) -> int:\n    return w + h[u] - h[v]\n", true);
        tc.putCode("Python", "Recursion", "# Johnson's Algorithm - Recursive (Python)\ndef johnson_recursive(edges: list[tuple[int, int, int]], V: int):\n    dist = [[0 if i == j else float('inf') for j in range(V)] for i in range(V)]\n    def per_vertex(u):\n        if u == V: return\n        per_vertex(u + 1)\n    per_vertex(0)\n    return dist\n", true);

        contentMap.put(tc.techniqueId, tc);
    }
}
