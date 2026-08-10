package com.dsavisualizer.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class GraphView extends View {

    private static final int STATUS_READY = 1;
    private static final int STATUS_WAITING = 2;
    private static final int STATUS_PROCESSED = 3;

    private static class Node {
        int id;
        float x;
        float y;

        Node(int id, float x, float y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }
    }

    private static class Edge {
        int from;
        int to;
        boolean directed;

        Edge(int from, int to, boolean directed) {
            this.from = from;
            this.to = to;
            this.directed = directed;
        }
    }

    private final List<Node> nodes = new ArrayList<>();
    private final List<Edge> edges = new ArrayList<>();
    private final Map<Integer, List<Integer>> adjacency = new HashMap<>();
    private final Map<Integer, Integer> nodeStatus = new HashMap<>();

    private final Set<Integer> visitedNodes = new HashSet<>();
    private final List<Integer> topologicalOrder = new ArrayList<>();

    private int activeNode = -1;
    private int selectedNodeId = -1;

    private String currentMode = "Traversing a Graph";
    private int[][] warshallMatrix;
    private int activeWarshallRow = -1;
    private int activeWarshallCol = -1;
    private int activeWarshallK = -1;

    private final Paint nodePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint edgePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint matrixCellPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint matrixTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public GraphView(Context context) {
        super(context);
        init();
    }

    public GraphView(Context context, @Nullable android.util.AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        edgePaint.setStrokeWidth(8f);
        edgePaint.setColor(Color.parseColor("#475569"));

        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(30f);
        textPaint.setTextAlign(Paint.Align.CENTER);

        matrixTextPaint.setColor(Color.WHITE);
        matrixTextPaint.setTextAlign(Paint.Align.CENTER);
        matrixTextPaint.setTextSize(28f);
    }

    public void setMode(String mode) {
        currentMode = mode;

        if (mode.equals("Posets; Topological Sorting")) {
            loadTopologicalDemo();
        } else if (mode.equals("Warshall's Algorithm; Shortest Paths")) {
            loadWarshallDemo();
        }

        invalidate();
    }

    public void clear() {
        nodes.clear();
        edges.clear();
        adjacency.clear();
        visitedNodes.clear();
        nodeStatus.clear();
        topologicalOrder.clear();
        activeNode = -1;
        selectedNodeId = -1;
        warshallMatrix = null;
        activeWarshallRow = -1;
        activeWarshallCol = -1;
        activeWarshallK = -1;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!currentMode.equals("Traversing a Graph")) {
            return super.onTouchEvent(event);
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float touchX = event.getX();
            float touchY = event.getY();
            int clickedId = -1;

            for (Node node : nodes) {
                if (Math.hypot(node.x - touchX, node.y - touchY) < 60f) {
                    clickedId = node.id;
                    break;
                }
            }

            if (clickedId == -1) {
                int id = nodes.size();
                nodes.add(new Node(id, touchX, touchY));
                adjacency.put(id, new ArrayList<>());
                nodeStatus.put(id, STATUS_READY);
                selectedNodeId = -1;
            } else if (selectedNodeId != -1 && selectedNodeId != clickedId) {
                addEdge(selectedNodeId, clickedId, false);
                selectedNodeId = -1;
            } else {
                selectedNodeId = clickedId;
            }

            invalidate();
            performClick();
            return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private void addEdge(int from, int to, boolean directed) {
        edges.add(new Edge(from, to, directed));
        adjacency.get(from).add(to);
        if (!directed) {
            adjacency.get(to).add(from);
        }
    }

    public void startBFS(int start, AlgorithmViewModel viewModel, AlgorithmSleeper sleeper) {
        currentMode = "Traversing a Graph";
        if (!adjacency.containsKey(start)) {
            viewModel.setRunning(false);
            return;
        }

        viewModel.getExecutor().execute(() -> {
            visitedNodes.clear();
            initializeReadyStatus();
            Queue<Integer> queue = new LinkedList<>();
            queue.add(start);
            nodeStatus.put(start, STATUS_WAITING);
            viewModel.updateState(1, "Set all nodes STATUS = 1. Enqueue " + start + " and set STATUS = 2.");
            postInvalidate();
            sleeper.sleep(850);

            while (!queue.isEmpty()) {
                int node = queue.poll();
                activeNode = node;
                nodeStatus.put(node, STATUS_PROCESSED);
                visitedNodes.add(node);
                viewModel.updateState(3, "Remove " + node + ", process it, and set STATUS = 3.");
                postInvalidate();
                sleeper.sleep(850);

                for (int next : adjacency.get(node)) {
                    if (nodeStatus.getOrDefault(next, STATUS_READY) == STATUS_READY) {
                        queue.add(next);
                        nodeStatus.put(next, STATUS_WAITING);
                        viewModel.updateState(2, "Add neighbor " + next + " to QUEUE and set STATUS = 2.");
                        postInvalidate();
                        sleeper.sleep(650);
                    }
                }
            }

            activeNode = -1;
            viewModel.updateState(-1, "BFS traversal finished");
            postInvalidate();
            viewModel.setRunning(false);
        });
    }

    public void startDFS(int start, AlgorithmViewModel viewModel, AlgorithmSleeper sleeper) {
        currentMode = "Traversing a Graph";
        if (!adjacency.containsKey(start)) {
            viewModel.setRunning(false);
            return;
        }

        viewModel.getExecutor().execute(() -> {
            visitedNodes.clear();
            initializeReadyStatus();
            ArrayDeque<Integer> stack = new ArrayDeque<>();
            stack.push(start);
            nodeStatus.put(start, STATUS_WAITING);
            viewModel.updateState(1, "Set all nodes STATUS = 1. Push " + start + " and set STATUS = 2.");
            postInvalidate();
            sleeper.sleep(850);

            while (!stack.isEmpty()) {
                int node = stack.pop();
                activeNode = node;
                nodeStatus.put(node, STATUS_PROCESSED);
                visitedNodes.add(node);
                viewModel.updateState(3, "Pop " + node + ", process it, and set STATUS = 3.");
                postInvalidate();
                sleeper.sleep(850);

                List<Integer> neighbors = adjacency.get(node);
                for (int i = neighbors.size() - 1; i >= 0; i--) {
                    int next = neighbors.get(i);
                    if (nodeStatus.getOrDefault(next, STATUS_READY) == STATUS_READY) {
                        stack.push(next);
                        nodeStatus.put(next, STATUS_WAITING);
                        viewModel.updateState(2, "Push ready neighbor " + next + " and set STATUS = 2.");
                        postInvalidate();
                        sleeper.sleep(650);
                    }
                }
            }
            activeNode = -1;
            viewModel.updateState(-1, "DFS traversal finished");
            postInvalidate();
            viewModel.setRunning(false);
        });
    }

    public void startTopologicalSort(AlgorithmViewModel viewModel, AlgorithmSleeper sleeper) {
        currentMode = "Posets; Topological Sorting";
        loadTopologicalDemo();

        viewModel.getExecutor().execute(() -> {
            visitedNodes.clear();
            topologicalOrder.clear();

            int[] indegree = new int[nodes.size()];
            for (Edge edge : edges) {
                indegree[edge.to]++;
            }

            Queue<Integer> queue = new ArrayDeque<>();
            for (int i = 0; i < indegree.length; i++) {
                if (indegree[i] == 0) {
                    queue.add(i);
                }
            }

            while (!queue.isEmpty()) {
                int node = queue.poll();
                activeNode = node;
                visitedNodes.add(node);
                topologicalOrder.add(node);
                viewModel.updateState(1, "Select in-degree 0 node " + node);
                postInvalidate();
                sleeper.sleep(900);

                for (int next : adjacency.get(node)) {
                    indegree[next]--;
                    if (indegree[next] == 0) {
                        queue.add(next);
                    }
                }
            }

            activeNode = -1;
            viewModel.updateState(-1, "Topological order: " + topologicalOrder.toString());
            postInvalidate();
            viewModel.setRunning(false);
        });
    }

    public void startWarshall(AlgorithmViewModel viewModel, AlgorithmSleeper sleeper) {
        currentMode = "Warshall's Algorithm; Shortest Paths";
        loadWarshallDemo();

        viewModel.getExecutor().execute(() -> {
            int n = warshallMatrix.length;

            for (int k = 0; k < n; k++) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        activeWarshallK = k;
                        activeWarshallRow = i;
                        activeWarshallCol = j;

                        int oldValue = warshallMatrix[i][j];
                        warshallMatrix[i][j] = warshallMatrix[i][j] | (warshallMatrix[i][k] & warshallMatrix[k][j]);

                        viewModel.updateState(
                            1,
                            "Update P[" + i + "][" + j + "] using intermediate " + k
                                + " : " + oldValue + " -> " + warshallMatrix[i][j]
                        );
                        postInvalidate();
                        sleeper.sleep(350);
                    }
                }
            }

            activeWarshallK = -1;
            activeWarshallRow = -1;
            activeWarshallCol = -1;
            viewModel.updateState(-1, "Warshall's transitive closure completed");
            postInvalidate();
            viewModel.setRunning(false);
        });
    }

    private void loadTopologicalDemo() {
        clearGraphOnly();
        nodes.add(new Node(0, 150f, 130f));
        nodes.add(new Node(1, 420f, 130f));
        nodes.add(new Node(2, 150f, 330f));
        nodes.add(new Node(3, 420f, 330f));
        nodes.add(new Node(4, 680f, 130f));
        nodes.add(new Node(5, 680f, 330f));

        for (Node node : nodes) {
            adjacency.put(node.id, new ArrayList<>());
            nodeStatus.put(node.id, STATUS_READY);
        }

        addEdge(0, 2, true);
        addEdge(0, 3, true);
        addEdge(1, 3, true);
        addEdge(1, 4, true);
        addEdge(2, 5, true);
        addEdge(3, 5, true);
        addEdge(4, 5, true);
    }

    private void loadWarshallDemo() {
        clearGraphOnly();
        warshallMatrix = new int[][]{
            {1, 1, 0, 0},
            {0, 1, 1, 0},
            {0, 0, 1, 1},
            {0, 0, 0, 1}
        };
    }

    private void clearGraphOnly() {
        nodes.clear();
        edges.clear();
        adjacency.clear();
        nodeStatus.clear();
        visitedNodes.clear();
        topologicalOrder.clear();
        activeNode = -1;
        selectedNodeId = -1;
        activeWarshallRow = -1;
        activeWarshallCol = -1;
        activeWarshallK = -1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (currentMode.equals("Warshall's Algorithm; Shortest Paths") && warshallMatrix != null) {
            drawWarshallMatrix(canvas);
            return;
        }

        drawGraph(canvas);

        if (currentMode.equals("Posets; Topological Sorting") && !topologicalOrder.isEmpty()) {
            drawTopologicalOrder(canvas);
        }
    }

    private void drawGraph(Canvas canvas) {
        for (Edge edge : edges) {
            Node fromNode = nodes.get(edge.from);
            Node toNode = nodes.get(edge.to);

            canvas.drawLine(fromNode.x, fromNode.y, toNode.x, toNode.y, edgePaint);
            if (edge.directed) {
                drawArrow(canvas, fromNode, toNode);
            }
        }

        for (Node node : nodes) {
            if (node.id == activeNode) {
                nodePaint.setColor(Color.parseColor("#F59E0B"));
            } else if (nodeStatus.getOrDefault(node.id, STATUS_READY) == STATUS_WAITING) {
                nodePaint.setColor(Color.parseColor("#38BDF8"));
            } else if (nodeStatus.getOrDefault(node.id, STATUS_READY) == STATUS_PROCESSED) {
                nodePaint.setColor(Color.parseColor("#10B981"));
            } else if (visitedNodes.contains(node.id)) {
                nodePaint.setColor(Color.parseColor("#10B981"));
            } else if (node.id == selectedNodeId) {
                nodePaint.setColor(Color.parseColor("#F87171"));
            } else {
                nodePaint.setColor(Color.parseColor("#38BDF8"));
            }

            canvas.drawCircle(node.x, node.y, 44f, nodePaint);
            canvas.drawText(String.valueOf(node.id), node.x, node.y + 10f, textPaint);
        }
    }

    private void initializeReadyStatus() {
        nodeStatus.clear();
        for (Node node : nodes) {
            nodeStatus.put(node.id, STATUS_READY);
        }
    }

    private void drawArrow(Canvas canvas, Node fromNode, Node toNode) {
        float dx = toNode.x - fromNode.x;
        float dy = toNode.y - fromNode.y;
        float length = (float) Math.sqrt(dx * dx + dy * dy);
        if (length == 0f) {
            return;
        }

        float ux = dx / length;
        float uy = dy / length;
        float arrowBaseX = toNode.x - ux * 50f;
        float arrowBaseY = toNode.y - uy * 50f;

        canvas.drawLine(arrowBaseX, arrowBaseY, arrowBaseX - uy * 14f - ux * 14f, arrowBaseY + ux * 14f - uy * 14f, edgePaint);
        canvas.drawLine(arrowBaseX, arrowBaseY, arrowBaseX + uy * 14f - ux * 14f, arrowBaseY - ux * 14f - uy * 14f, edgePaint);
    }

    private void drawTopologicalOrder(Canvas canvas) {
        Paint orderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        orderPaint.setColor(Color.parseColor("#E2E8F0"));
        orderPaint.setTextSize(28f);
        orderPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Order: " + topologicalOrder.toString(), 28f, getHeight() - 28f, orderPaint);
    }

    private void drawWarshallMatrix(Canvas canvas) {
        int size = warshallMatrix.length;
        float cell = Math.min(getWidth(), getHeight()) / (size + 2f);
        float startX = (getWidth() - (cell * size)) / 2f;
        float startY = (getHeight() - (cell * size)) / 2f;

        Paint labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        labelPaint.setColor(Color.parseColor("#CBD5E1"));
        labelPaint.setTextAlign(Paint.Align.CENTER);
        labelPaint.setTextSize(24f);

        for (int i = 0; i < size; i++) {
            canvas.drawText(String.valueOf(i), startX + i * cell + cell / 2f, startY - 20f, labelPaint);
            canvas.drawText(String.valueOf(i), startX - 24f, startY + i * cell + cell / 2f + 8f, labelPaint);
        }

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                float left = startX + col * cell;
                float top = startY + row * cell;
                float right = left + cell;
                float bottom = top + cell;

                if (row == activeWarshallRow && col == activeWarshallCol) {
                    matrixCellPaint.setColor(Color.parseColor("#F59E0B"));
                } else if (row == activeWarshallK || col == activeWarshallK) {
                    matrixCellPaint.setColor(Color.parseColor("#1D4ED8"));
                } else {
                    matrixCellPaint.setColor(Color.parseColor("#1E293B"));
                }

                matrixCellPaint.setStyle(Paint.Style.FILL);
                canvas.drawRoundRect(left, top, right, bottom, 14f, 14f, matrixCellPaint);

                matrixCellPaint.setStyle(Paint.Style.STROKE);
                matrixCellPaint.setColor(Color.parseColor("#38BDF8"));
                canvas.drawRoundRect(left, top, right, bottom, 14f, 14f, matrixCellPaint);

                canvas.drawText(
                    String.valueOf(warshallMatrix[row][col]),
                    left + cell / 2f,
                    top + cell / 2f + 10f,
                    matrixTextPaint
                );
            }
        }
    }

    public interface AlgorithmSleeper {
        void sleep(int ms);
    }
}
