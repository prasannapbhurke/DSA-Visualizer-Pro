package com.dsavisualizer.app.ui.visualization.runtime;

public final class DijkstraStepEngine extends BaseStepEngine {

    public DijkstraStepEngine(VisualizationRuntimeHost host) {
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
        if (host.getStepIndex() == -1) {
            host.initializeDijkstraPreview();
            host.setStepIndex(0);
            host.setHighlightedLines(1, 0);
            host.updateStatusText("Initialize distances and settled set.");
            return;
        }

        int[] distances = host.getDijkstraDistances();
        boolean[] settled = host.getDijkstraSettled();
        int currentNode = host.getDijkstraCurrentNode();
        int phase = host.getDijkstraPhase();
        int neighborIndex = host.getDijkstraNeighborIndex();
        int[][] weights = host.getDijkstraWeights();

        if (phase == 0) {
            int next = findNextDijkstraNode(distances, settled);
            if (next == -1) {
                host.finish("Dijkstra completed.");
                return;
            }
            currentNode = next;
            neighborIndex = 0;
            phase = 1;
            host.setDijkstraCurrentNode(currentNode);
            host.setDijkstraNeighborIndex(neighborIndex);
            host.setDijkstraPhase(phase);
            host.clearActiveEdge();
            host.setDijkstraState(distances, settled, currentNode);
            host.setHighlightedLines(3, 1);
            host.updateStatusText("Pick node " + next + " with the smallest tentative distance.");
            return;
        }

        int neighbor = findNextDijkstraNeighbor(weights, settled, currentNode, neighborIndex);
        if (neighbor == -1) {
            settled[currentNode] = true;
            phase = 0;
            host.setDijkstraPhase(phase);
            host.clearActiveEdge();
            host.setDijkstraState(distances, settled, currentNode);
            host.setHighlightedLines(7, 2);
            host.updateStatusText("Node " + currentNode + " is now settled.");
            return;
        }

        int weight = weights[currentNode][neighbor];
        int candidate = safeAdd(distances[currentNode], weight);
        if (candidate < distances[neighbor]) {
            distances[neighbor] = candidate;
        }
        neighborIndex = neighbor + 1;
        host.setDijkstraNeighborIndex(neighborIndex);
        host.setActiveEdge(currentNode, neighbor);
        host.setDijkstraState(distances, settled, currentNode);
        host.setHighlightedLines(5, 2);
        host.updateStatusText("Relax edge " + currentNode + " -> " + neighbor + ".");
    }

    public static void initializePreview(VisualizationRuntimeHost host) {
        int nodeCount = host.getWorkingValues().length;
        if (nodeCount == 0) nodeCount = 5;
        int[][] dijkstraWeights = new int[nodeCount][nodeCount];
        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < nodeCount; j++) {
                dijkstraWeights[i][j] = 9999;
            }
            dijkstraWeights[i][i] = 0;
        }
        int[][] edges = host.getGraphEdges();
        for (int[] edge : edges) {
            if (edge[0] < nodeCount && edge[1] < nodeCount) {
                int w = GraphStepEngine.getGraphEdgeWeight(1, edge[0], edge[1]);
                if (w <= 0) w = Math.abs(edge[0] - edge[1]) + 2;
                dijkstraWeights[edge[0]][edge[1]] = w;
                dijkstraWeights[edge[1]][edge[0]] = w;
            }
        }

        int startNode = Math.max(0, Math.min(host.getGraphStartNode(), nodeCount - 1));
        int[] dijkstraDistances = new int[nodeCount];
        for (int i = 0; i < nodeCount; i++) dijkstraDistances[i] = 9999;
        dijkstraDistances[startNode] = 0;
        boolean[] dijkstraSettled = new boolean[nodeCount];
        int dijkstraCurrentNode = startNode;
        int dijkstraPhase = 0;
        int dijkstraNeighborIndex = 0;

        host.setDijkstraWeights(dijkstraWeights);
        host.setDijkstraDistances(dijkstraDistances);
        host.setDijkstraSettled(dijkstraSettled);
        host.setDijkstraCurrentNode(dijkstraCurrentNode);
        host.setDijkstraPhase(dijkstraPhase);
        host.setDijkstraNeighborIndex(dijkstraNeighborIndex);
        host.setDijkstraState(dijkstraDistances, dijkstraSettled, dijkstraCurrentNode);
    }

    public static int findNextDijkstraNode(int[] distances, boolean[] settled) {
        int bestNode = -1;
        int bestDistance = Integer.MAX_VALUE;
        for (int i = 0; i < distances.length; i++) {
            if (!settled[i] && distances[i] < bestDistance) {
                bestDistance = distances[i];
                bestNode = i;
            }
        }
        return bestNode;
    }

    public static int findNextDijkstraNeighbor(int[][] weights, boolean[] settled, int node, int startIndex) {
        if (node < 0 || node >= weights.length) {
            return -1;
        }
        for (int i = Math.max(0, startIndex); i < weights[node].length; i++) {
            int weight = weights[node][i];
            if (weight > 0 && weight < 9999 && !settled[i]) {
                return i;
            }
        }
        return -1;
    }

    private static int safeAdd(int left, int right) {
        if (left >= 9999 || right >= 9999) {
            return 9999;
        }
        return left + right;
    }
}
