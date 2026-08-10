package com.dsavisualizer.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class AdvancedView extends View {

    private static class TreeNode {
        int key;
        TreeNode left;
        TreeNode right;
        float x;
        float y;

        TreeNode(int key) {
            this.key = key;
        }
    }

    private static class HanoiMove {
        int fromRod;
        int toRod;
        int diskSize;

        HanoiMove(int fromRod, int toRod, int diskSize) {
            this.fromRod = fromRod;
            this.toRod = toRod;
            this.diskSize = diskSize;
        }
    }

    private int boardSize = 4;
    private int[][] board;
    private int currentRow = -1;
    private int currentCol = -1;
    private boolean checkingSafe = false;
    private boolean isSafeResult = false;

    private TreeNode root;
    private int lastInsertedKey = -1;
    private int activeTreeKey = Integer.MIN_VALUE;
    private final List<Integer> traversalOrder = new ArrayList<>();

    private final List<Stack<Integer>> hanoiRods = new ArrayList<>();
    private int activeFromRod = -1;
    private int activeToRod = -1;
    private int activeDisk = -1;
    private int hanoiDiskCount = 3;

    private final Paint boardPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint queenPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint nodePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint accentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint rodPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint diskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private String currentMode = "";

    public AdvancedView(Context context) {
        super(context);
        init();
    }

    public AdvancedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        boardPaint.setStrokeWidth(2f);
        queenPaint.setColor(Color.parseColor("#38BDF8"));

        nodePaint.setStyle(Paint.Style.FILL);
        linePaint.setColor(Color.parseColor("#475569"));
        linePaint.setStrokeWidth(4f);

        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(28f);

        accentPaint.setStyle(Paint.Style.STROKE);
        accentPaint.setStrokeWidth(5f);
        accentPaint.setColor(Color.parseColor("#F59E0B"));

        rodPaint.setColor(Color.parseColor("#CBD5E1"));
        rodPaint.setStrokeWidth(12f);

        diskPaint.setStyle(Paint.Style.FILL);

        for (int i = 0; i < 3; i++) {
            hanoiRods.add(new Stack<>());
        }
    }

    public void clear() {
        board = null;
        currentRow = -1;
        currentCol = -1;
        checkingSafe = false;
        isSafeResult = false;

        root = null;
        lastInsertedKey = -1;
        activeTreeKey = Integer.MIN_VALUE;
        traversalOrder.clear();

        resetHanoiState(3);
        currentMode = "";
        invalidate();
    }

    public void startAlgorithm(
        String mode,
        int value,
        AlgorithmViewModel viewModel,
        GraphView.AlgorithmSleeper sleeper
    ) {
        currentMode = mode;

        if (mode.equals("Binary Search Tree")) {
            startBstInsert(value, viewModel, sleeper);
            return;
        }

        if (mode.equals("Inorder Traversal")
            || mode.equals("Preorder Traversal")
            || mode.equals("Postorder Traversal")) {
            startTraversal(mode, viewModel, sleeper);
            return;
        }

        if (mode.equals("Tower of Hanoi")) {
            startTowerOfHanoi(value, viewModel, sleeper);
        }
    }

    private void startBacktracking(
        int value,
        AlgorithmViewModel viewModel,
        GraphView.AlgorithmSleeper sleeper
    ) {
        boardSize = Math.max(4, Math.min(8, value));
        board = new int[boardSize][boardSize];

        viewModel.getExecutor().execute(() -> {
            if (solveQueens(0, viewModel, sleeper)) {
                viewModel.updateState(-1, "N-Queens solved for N = " + boardSize);
            } else {
                viewModel.updateState(-1, "No solution exists for N = " + boardSize);
            }

            currentRow = -1;
            currentCol = -1;
            postInvalidate();
            viewModel.setRunning(false);
        });
    }

    private void startBstInsert(
        int value,
        AlgorithmViewModel viewModel,
        GraphView.AlgorithmSleeper sleeper
    ) {
        ensureSampleTreeIfEmpty();
        lastInsertedKey = value;
        activeTreeKey = value;
        traversalOrder.clear();

        viewModel.getExecutor().execute(() -> {
            root = insert(root, value, viewModel, sleeper);
            activeTreeKey = Integer.MIN_VALUE;
            viewModel.updateState(-1, "Inserted " + value + " into the BST");
            postInvalidate();
            viewModel.setRunning(false);
        });
    }

    private void startTraversal(
        String mode,
        AlgorithmViewModel viewModel,
        GraphView.AlgorithmSleeper sleeper
    ) {
        ensureSampleTreeIfEmpty();
        traversalOrder.clear();
        lastInsertedKey = -1;

        viewModel.getExecutor().execute(() -> {
            if (mode.equals("Inorder Traversal")) {
                inorder(root, viewModel, sleeper);
            } else if (mode.equals("Preorder Traversal")) {
                preorder(root, viewModel, sleeper);
            } else {
                postorder(root, viewModel, sleeper);
            }

            activeTreeKey = Integer.MIN_VALUE;
            viewModel.updateState(-1, "Traversal order: " + traversalOrder.toString());
            postInvalidate();
            viewModel.setRunning(false);
        });
    }

    private void startTowerOfHanoi(
        int diskCount,
        AlgorithmViewModel viewModel,
        GraphView.AlgorithmSleeper sleeper
    ) {
        hanoiDiskCount = Math.max(3, Math.min(6, diskCount));
        resetHanoiState(hanoiDiskCount);

        viewModel.getExecutor().execute(() -> {
            solveHanoi(hanoiDiskCount, 0, 1, 2, viewModel, sleeper);
            activeFromRod = -1;
            activeToRod = -1;
            activeDisk = -1;
            postInvalidate();
            viewModel.updateState(-1, "Tower of Hanoi completed with " + hanoiDiskCount + " disks");
            viewModel.setRunning(false);
        });
    }

    private boolean solveQueens(
        int row,
        AlgorithmViewModel viewModel,
        GraphView.AlgorithmSleeper sleeper
    ) {
        if (row >= boardSize) {
            return true;
        }

        for (int col = 0; col < boardSize; col++) {
            currentRow = row;
            currentCol = col;
            checkingSafe = true;
            isSafeResult = isSafe(row, col);
            viewModel.updateState(3, "Check row " + row + ", col " + col);
            postInvalidate();
            sleeper.sleep(500);

            if (isSafeResult) {
                board[row][col] = 1;
                checkingSafe = false;
                viewModel.updateState(4, "Place queen at (" + row + ", " + col + ")");
                postInvalidate();
                sleeper.sleep(650);

                if (solveQueens(row + 1, viewModel, sleeper)) {
                    return true;
                }

                board[row][col] = 0;
                viewModel.updateState(5, "Backtrack from row " + row);
                postInvalidate();
                sleeper.sleep(500);
            }
        }

        return false;
    }

    private boolean isSafe(int row, int col) {
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 1) {
                return false;
            }
        }

        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        for (int i = row, j = col; i >= 0 && j < boardSize; i--, j++) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        return true;
    }

    private TreeNode insert(
        TreeNode node,
        int key,
        AlgorithmViewModel viewModel,
        GraphView.AlgorithmSleeper sleeper
    ) {
        if (node == null) {
            viewModel.updateState(2, "Create node " + key);
            postInvalidate();
            sleeper.sleep(600);
            return new TreeNode(key);
        }

        activeTreeKey = node.key;
        postInvalidate();
        sleeper.sleep(500);

        if (key < node.key) {
            viewModel.updateState(3, key + " < " + node.key + ", move left");
            node.left = insert(node.left, key, viewModel, sleeper);
        } else if (key > node.key) {
            viewModel.updateState(4, key + " > " + node.key + ", move right");
            node.right = insert(node.right, key, viewModel, sleeper);
        } else {
            viewModel.updateState(5, "Value already exists in the tree");
        }

        return node;
    }

    private void inorder(
        TreeNode node,
        AlgorithmViewModel viewModel,
        GraphView.AlgorithmSleeper sleeper
    ) {
        if (node == null) {
            return;
        }

        inorder(node.left, viewModel, sleeper);
        visitNode(node, "Inorder visit " + node.key, viewModel, sleeper);
        inorder(node.right, viewModel, sleeper);
    }

    private void preorder(
        TreeNode node,
        AlgorithmViewModel viewModel,
        GraphView.AlgorithmSleeper sleeper
    ) {
        if (node == null) {
            return;
        }

        visitNode(node, "Preorder visit " + node.key, viewModel, sleeper);
        preorder(node.left, viewModel, sleeper);
        preorder(node.right, viewModel, sleeper);
    }

    private void postorder(
        TreeNode node,
        AlgorithmViewModel viewModel,
        GraphView.AlgorithmSleeper sleeper
    ) {
        if (node == null) {
            return;
        }

        postorder(node.left, viewModel, sleeper);
        postorder(node.right, viewModel, sleeper);
        visitNode(node, "Postorder visit " + node.key, viewModel, sleeper);
    }

    private void visitNode(
        TreeNode node,
        String message,
        AlgorithmViewModel viewModel,
        GraphView.AlgorithmSleeper sleeper
    ) {
        activeTreeKey = node.key;
        traversalOrder.add(node.key);
        viewModel.updateState(1, message);
        postInvalidate();
        sleeper.sleep(700);
    }

    private void resetHanoiState(int disks) {
        for (Stack<Integer> rod : hanoiRods) {
            rod.clear();
        }

        for (int disk = disks; disk >= 1; disk--) {
            hanoiRods.get(0).push(disk);
        }

        activeFromRod = -1;
        activeToRod = -1;
        activeDisk = -1;
    }

    private void solveHanoi(
        int n,
        int from,
        int aux,
        int to,
        AlgorithmViewModel viewModel,
        GraphView.AlgorithmSleeper sleeper
    ) {
        if (n == 0) {
            return;
        }

        solveHanoi(n - 1, from, to, aux, viewModel, sleeper);
        moveDisk(from, to, viewModel, sleeper);
        solveHanoi(n - 1, aux, from, to, viewModel, sleeper);
    }

    private void moveDisk(
        int fromRod,
        int toRod,
        AlgorithmViewModel viewModel,
        GraphView.AlgorithmSleeper sleeper
    ) {
        if (hanoiRods.get(fromRod).isEmpty()) {
            return;
        }

        int disk = hanoiRods.get(fromRod).pop();
        hanoiRods.get(toRod).push(disk);

        activeFromRod = fromRod;
        activeToRod = toRod;
        activeDisk = disk;

        viewModel.updateState(
            1,
            "Move disk " + disk + " from rod " + (fromRod + 1) + " to rod " + (toRod + 1)
        );
        postInvalidate();
        sleeper.sleep(700);
    }

    private void ensureSampleTreeIfEmpty() {
        if (root != null) {
            return;
        }

        int[] sample = {50, 30, 70, 20, 40, 60, 80};
        for (int value : sample) {
            root = insertWithoutAnimation(root, value);
        }
    }

    private TreeNode insertWithoutAnimation(TreeNode node, int key) {
        if (node == null) {
            return new TreeNode(key);
        }

        if (key < node.key) {
            node.left = insertWithoutAnimation(node.left, key);
        } else if (key > node.key) {
            node.right = insertWithoutAnimation(node.right, key);
        }

        return node;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (currentMode.equals("Tower of Hanoi")) {
            drawHanoi(canvas);
            return;
        }

        if ((currentMode.equals("Binary Search Tree")
            || currentMode.equals("Inorder Traversal")
            || currentMode.equals("Preorder Traversal")
            || currentMode.equals("Postorder Traversal")) && root != null) {
            calculateNodePositions(root, getWidth() / 2f, 90f, getWidth() / 4f);
            drawTree(canvas, root);
            drawTraversalFooter(canvas);
        }
    }

    private void drawBoard(Canvas canvas) {
        float size = Math.min(getWidth(), getHeight()) * 0.85f;
        float cellSize = size / boardSize;
        float left = (getWidth() - size) / 2f;
        float top = (getHeight() - size) / 2f;

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                float l = left + col * cellSize;
                float t = top + row * cellSize;

                boardPaint.setStyle(Paint.Style.FILL);
                boardPaint.setColor(((row + col) % 2 == 0) ? Color.parseColor("#1E293B") : Color.parseColor("#0F172A"));
                canvas.drawRect(l, t, l + cellSize, t + cellSize, boardPaint);

                boardPaint.setStyle(Paint.Style.STROKE);
                boardPaint.setColor(Color.parseColor("#334155"));
                canvas.drawRect(l, t, l + cellSize, t + cellSize, boardPaint);

                if (board[row][col] == 1) {
                    canvas.drawCircle(l + cellSize / 2f, t + cellSize / 2f, cellSize / 3f, queenPaint);
                }

                if (row == currentRow && col == currentCol) {
                    Paint overlay = new Paint(Paint.ANTI_ALIAS_FLAG);
                    overlay.setColor(checkingSafe
                        ? (isSafeResult ? Color.parseColor("#2234D399") : Color.parseColor("#22EF4444"))
                        : Color.parseColor("#22F59E0B"));
                    overlay.setStyle(Paint.Style.FILL);
                    canvas.drawRect(l + 4, t + 4, l + cellSize - 4, t + cellSize - 4, overlay);
                }
            }
        }
    }

    private void calculateNodePositions(TreeNode node, float x, float y, float xOffset) {
        if (node == null) {
            return;
        }

        node.x = x;
        node.y = y;
        calculateNodePositions(node.left, x - xOffset, y + 110f, xOffset / 2f);
        calculateNodePositions(node.right, x + xOffset, y + 110f, xOffset / 2f);
    }

    private void drawTree(Canvas canvas, TreeNode node) {
        if (node == null) {
            return;
        }

        if (node.left != null) {
            canvas.drawLine(node.x, node.y, node.left.x, node.left.y, linePaint);
            drawTree(canvas, node.left);
        }

        if (node.right != null) {
            canvas.drawLine(node.x, node.y, node.right.x, node.right.y, linePaint);
            drawTree(canvas, node.right);
        }

        if (node.key == activeTreeKey) {
            nodePaint.setColor(Color.parseColor("#F59E0B"));
        } else if (traversalOrder.contains(node.key)) {
            nodePaint.setColor(Color.parseColor("#10B981"));
        } else if (node.key == lastInsertedKey) {
            nodePaint.setColor(Color.parseColor("#EF4444"));
        } else {
            nodePaint.setColor(Color.parseColor("#1E293B"));
        }

        canvas.drawCircle(node.x, node.y, 36f, nodePaint);
        canvas.drawCircle(node.x, node.y, 36f, accentPaint);
        canvas.drawText(String.valueOf(node.key), node.x, node.y + 8f, textPaint);
    }

    private void drawTraversalFooter(Canvas canvas) {
        if (traversalOrder.isEmpty()) {
            return;
        }

        Paint footerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        footerPaint.setColor(Color.parseColor("#E2E8F0"));
        footerPaint.setTextAlign(Paint.Align.LEFT);
        footerPaint.setTextSize(28f);

        canvas.drawText(
            "Order: " + traversalOrder.toString(),
            24f,
            getHeight() - 30f,
            footerPaint
        );
    }

    private void drawHanoi(Canvas canvas) {
        float baseY = getHeight() - 60f;
        float topY = 140f;
        float[] rodX = {
            getWidth() * 0.2f,
            getWidth() * 0.5f,
            getWidth() * 0.8f
        };

        Paint basePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        basePaint.setColor(Color.parseColor("#94A3B8"));
        canvas.drawRect(40f, baseY, getWidth() - 40f, baseY + 18f, basePaint);

        for (int i = 0; i < rodX.length; i++) {
            canvas.drawLine(rodX[i], topY, rodX[i], baseY, rodPaint);
        }

        for (int rodIndex = 0; rodIndex < hanoiRods.size(); rodIndex++) {
            Stack<Integer> rod = hanoiRods.get(rodIndex);
            for (int level = 0; level < rod.size(); level++) {
                int disk = rod.get(level);
                float width = 45f + (disk * 22f);
                float bottom = baseY - (level * 28f) - 8f;
                float top = bottom - 22f;

                if (disk == activeDisk && rodIndex == activeToRod) {
                    diskPaint.setColor(Color.parseColor("#F59E0B"));
                } else {
                    diskPaint.setColor(levelColor(disk));
                }

                canvas.drawRoundRect(
                    rodX[rodIndex] - width / 2f,
                    top,
                    rodX[rodIndex] + width / 2f,
                    bottom,
                    12f,
                    12f,
                    diskPaint
                );
            }
        }
    }

    private int levelColor(int level) {
        String[] palette = {
            "#38BDF8",
            "#34D399",
            "#F59E0B",
            "#F87171",
            "#A78BFA",
            "#22C55E"
        };
        return Color.parseColor(palette[(level - 1) % palette.length]);
    }
}
