package com.dsavisualizer.app.ui.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.dsavisualizer.app.ui.visualization.VisualizationTechniqueRegistry;
import com.dsavisualizer.app.ui.visualization.runtime.BacktrackingStepEngine;

/**
 * High-fidelity Canvas renderer for Backtracking problems:
 * - N-Queens (Checkered chessboard, gold ♛ queens, attack conflict rays)
 * - Sudoku Solver (Thick 3x3 subgrid borders, active cell highlight, digit validation)
 * - Knight's Tour (5x5 chessboard, move sequence trails, knight ♞ piece)
 * - Maze Solver (Obstacle walls, explorer trail, dead-end retreat markers)
 * - Hamiltonian Path & Graph Coloring (Chromatic vertex fills and cycle verification)
 */
final class BacktrackingVisualizerEngine implements VisualizerEngine {

    private final Paint tilePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint highlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint rayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF rect = new RectF();

    public BacktrackingVisualizerEngine() {
        borderPaint.setStyle(Paint.Style.STROKE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setFakeBoldText(true);
        rayPaint.setStyle(Paint.Style.STROKE);
        rayPaint.setStrokeWidth(3.5f);
        rayPaint.setPathEffect(new DashPathEffect(new float[]{10f, 8f}, 0f));
    }

    @Override
    public boolean supports(String techniqueId) {
        return VisualizationTechniqueRegistry.isBacktracking(techniqueId)
            || (techniqueId != null && (techniqueId.startsWith("backtracking_") || techniqueId.startsWith("bt_")));
    }

    @Override
    public void draw(Canvas canvas, TechniqueVisualizerView view, String techniqueId) {
        if ("backtracking_n_queens".equals(techniqueId) || "bt_n_queens".equals(techniqueId)) {
            drawNQueens(canvas, view);
        } else if ("backtracking_sudoku".equals(techniqueId) || "bt_sudoku".equals(techniqueId)) {
            drawSudoku(canvas, view);
        } else if ("backtracking_knights_tour".equals(techniqueId) || "bt_knights_tour".equals(techniqueId)) {
            drawKnightsTour(canvas, view);
        } else if ("backtracking_maze_solver".equals(techniqueId) || "bt_maze".equals(techniqueId) || "bt_rat_in_a_maze".equals(techniqueId)) {
            drawMaze(canvas, view);
        } else if ("backtracking_graph_coloring".equals(techniqueId) || "bt_graph_coloring".equals(techniqueId)) {
            drawGraphColoring(canvas, view);
        } else {
            drawHamiltonian(canvas, view);
        }
    }

    // =========================================================================
    // 1. N-Queens Dynamic Chessboard
    // =========================================================================
    private void drawNQueens(Canvas canvas, TechniqueVisualizerView view) {
        float width = view.getWidth();
        float height = view.getHeight();
        if (width <= 0 || height <= 0) {
            return;
        }

        int[][] board = view.getMatrixValues();
        int n = (board != null && board.length >= 4) ? board.length : 4;

        float topMargin = 40f;
        float bottomMargin = 40f;
        float availableH = Math.max(120f, height - topMargin - bottomMargin);
        float availableW = Math.max(120f, width * 0.88f);
        float boardSize = Math.min(availableW, availableH);
        float tileSize = boardSize / n;
        float startX = (width - boardSize) / 2f;
        float startY = topMargin + (availableH - boardSize) / 2f;

        // Draw title
        textPaint.setTextSize(Math.max(18f, Math.min(24f, tileSize * 0.42f)));
        textPaint.setColor(Color.parseColor("#F8FAFC"));
        canvas.drawText("N-Queens Chessboard (" + n + "×" + n + ")", width / 2f, startY - 14f, textPaint);

        int activeRow = view.getPrimaryPointerIndex() >= 0 ? view.getPrimaryPointerIndex() : view.getMatrixRow();
        int activeCol = view.getSecondaryPointerIndex() >= 0 ? view.getSecondaryPointerIndex() : view.getMatrixCol();

        int conflictTargetR = -1;
        int conflictTargetC = -1;

        // Locate any active conflict cell (-1)
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (board != null && r < board.length && c < board[r].length && board[r][c] == -1) {
                    conflictTargetR = r;
                    conflictTargetC = c;
                    break;
                }
            }
            if (conflictTargetR >= 0) break;
        }

        // Check which placed queens attack the conflict cell
        boolean[][] isAttackerMatrix = new boolean[n][n];
        if (conflictTargetR >= 0 && conflictTargetC >= 0 && board != null) {
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    if (r < board.length && c < board[r].length && board[r][c] == 1) {
                        if (c == conflictTargetC || Math.abs(r - conflictTargetR) == Math.abs(c - conflictTargetC)) {
                            isAttackerMatrix[r][c] = true;
                        }
                    }
                }
            }
        }

        // Draw checkered board
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                float left = startX + (c * tileSize);
                float top = startY + (r * tileSize);
                float right = left + tileSize;
                float bottom = top + tileSize;
                rect.set(left, top, right, bottom);

                boolean isDark = (r + c) % 2 == 1;
                int tileColor = isDark ? Color.parseColor("#1E293B") : Color.parseColor("#334155");

                int cellVal = (board != null && r < board.length && c < board[r].length) ? board[r][c] : 0;

                // Highlight active placement or conflict cell
                if (cellVal == -1) {
                    tileColor = Color.parseColor("#7F1D1D"); // Conflict crimson
                } else if (r == activeRow && c == activeCol) {
                    tileColor = Color.parseColor("#0369A1"); // Active cyan
                }

                tilePaint.setStyle(Paint.Style.FILL);
                tilePaint.setColor(tileColor);
                canvas.drawRect(rect, tilePaint);

                // Draw Queen if placed
                if (cellVal == 1) {
                    boolean isAttacker = isAttackerMatrix[r][c];
                    drawQueenPiece(canvas, (left + right) / 2f, (top + bottom) / 2f, tileSize, isAttacker);
                } else if (cellVal == -1) {
                    // Conflict mark ✕
                    textPaint.setTextSize(tileSize * 0.48f);
                    textPaint.setColor(Color.parseColor("#EF4444"));
                    canvas.drawText("✕", (left + right) / 2f, top + (tileSize * 0.66f), textPaint);
                }
            }
        }

        // Draw attack conflict rays from all attacking queens
        if (conflictTargetR >= 0 && conflictTargetC >= 0 && board != null) {
            float toX = startX + (conflictTargetC * tileSize) + (tileSize / 2f);
            float toY = startY + (conflictTargetR * tileSize) + (tileSize / 2f);

            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    if (isAttackerMatrix[r][c]) {
                        float fromX = startX + (c * tileSize) + (tileSize / 2f);
                        float fromY = startY + (r * tileSize) + (tileSize / 2f);

                        rayPaint.setColor(Color.parseColor("#EF4444"));
                        canvas.drawLine(fromX, fromY, toX, toY, rayPaint);

                        // Midpoint conflict warning indicator
                        float midX = (fromX + toX) / 2f;
                        float midY = (fromY + toY) / 2f;
                        highlightPaint.setStyle(Paint.Style.FILL);
                        highlightPaint.setColor(Color.parseColor("#991B1B"));
                        highlightPaint.setAlpha(230);
                        canvas.drawCircle(midX, midY, 13f, highlightPaint);
                        textPaint.setTextSize(13f);
                        textPaint.setColor(Color.parseColor("#FEF2F2"));
                        canvas.drawText("!", midX, midY + 4.5f, textPaint);
                    }
                }
            }
        }

        // Draw board border
        borderPaint.setColor(Color.parseColor("#64748B"));
        borderPaint.setStrokeWidth(4f);
        canvas.drawRect(startX, startY, startX + boardSize, startY + boardSize, borderPaint);

        // Draw row/col labels (Row 0..N-1 matches algorithm narrative; Col A..Z)
        float coordTextSize = Math.max(11f, Math.min(16f, tileSize * 0.32f));
        textPaint.setTextSize(coordTextSize);
        textPaint.setColor(Color.parseColor("#94A3B8"));
        for (int i = 0; i < n; i++) {
            char colLetter = (char) ('A' + i);
            canvas.drawText(String.valueOf(colLetter), startX + (i * tileSize) + (tileSize / 2f), startY - 5f, textPaint);
            canvas.drawText(String.valueOf(i), startX - 14f, startY + (i * tileSize) + (tileSize * 0.62f), textPaint);
        }

        // Bottom guide legend
        textPaint.setTextSize(13f);
        textPaint.setColor(Color.parseColor("#94A3B8"));
        canvas.drawText("♛ Placed Queen    ✕ Conflict Square    ┄ Attack Ray", width / 2f, startY + boardSize + 26f, textPaint);
    }

    private void drawQueenPiece(Canvas canvas, float cx, float cy, float size, boolean isAttacker) {
        float halfW = size * 0.36f;
        float halfH = size * 0.28f;

        // Outer glow aura
        highlightPaint.setStyle(Paint.Style.FILL);
        highlightPaint.setColor(isAttacker ? Color.parseColor("#EF4444") : Color.parseColor("#F59E0B"));
        highlightPaint.setAlpha(isAttacker ? 110 : 70);
        canvas.drawCircle(cx, cy, size * 0.44f, highlightPaint);

        // Crown Path
        android.graphics.Path crownPath = new android.graphics.Path();
        float baseLeft = cx - halfW * 0.85f;
        float baseRight = cx + halfW * 0.85f;
        float baseY = cy + halfH * 0.65f;
        float peakY = cy - halfH * 0.85f;
        float sidePeakY = cy - halfH * 0.55f;
        float valleyY = cy + halfH * 0.05f;

        crownPath.moveTo(baseLeft, baseY);
        crownPath.lineTo(cx - halfW, sidePeakY);
        crownPath.lineTo(cx - halfW * 0.45f, valleyY);
        crownPath.lineTo(cx, peakY);
        crownPath.lineTo(cx + halfW * 0.45f, valleyY);
        crownPath.lineTo(cx + halfW, sidePeakY);
        crownPath.lineTo(baseRight, baseY);
        crownPath.close();

        // Crown body fill
        tilePaint.setStyle(Paint.Style.FILL);
        tilePaint.setColor(isAttacker ? Color.parseColor("#F87171") : Color.parseColor("#FBBF24"));
        canvas.drawPath(crownPath, tilePaint);

        // Crown border stroke
        borderPaint.setColor(isAttacker ? Color.parseColor("#DC2626") : Color.parseColor("#D97706"));
        borderPaint.setStrokeWidth(2.5f);
        canvas.drawPath(crownPath, borderPaint);

        // Crown jewels on peaks
        highlightPaint.setColor(isAttacker ? Color.parseColor("#FEE2E2") : Color.parseColor("#FEF3C7"));
        highlightPaint.setAlpha(255);
        float jewelR = size * 0.065f;
        canvas.drawCircle(cx - halfW, sidePeakY, jewelR, highlightPaint);
        canvas.drawCircle(cx, peakY, jewelR * 1.15f, highlightPaint);
        canvas.drawCircle(cx + halfW, sidePeakY, jewelR, highlightPaint);

        // Crown headband
        rect.set(baseLeft - 2f, baseY - halfH * 0.22f, baseRight + 2f, baseY + 1f);
        tilePaint.setColor(isAttacker ? Color.parseColor("#B91C1C") : Color.parseColor("#B45309"));
        canvas.drawRoundRect(rect, 3f, 3f, tilePaint);

        // Queen "Q" badge text
        textPaint.setTextSize(Math.max(10f, size * 0.22f));
        textPaint.setColor(isAttacker ? Color.parseColor("#7F1D1D") : Color.parseColor("#78350F"));
        canvas.drawText("Q", cx, baseY - halfH * 0.02f, textPaint);
    }

    // =========================================================================
    // 2. Sudoku 4x4 / 9x9 Grid
    // =========================================================================
    private void drawSudoku(Canvas canvas, TechniqueVisualizerView view) {
        int[][] board = view.getMatrixValues();
        int n = (board != null && board.length >= 4) ? board.length : 4;
        int subBox = (n == 9) ? 3 : 2;

        float width = view.getWidth();
        float height = view.getHeight();
        float boardSize = Math.min(width * 0.86f, height * 0.76f);
        float cellSize = boardSize / n;
        float startX = (width - boardSize) / 2f;
        float startY = (height - boardSize) / 2f + 14f;

        textPaint.setTextSize(26f);
        textPaint.setColor(Color.parseColor("#F8FAFC"));
        canvas.drawText("Sudoku Solver (" + n + "×" + n + " Grid)", width / 2f, startY - 22f, textPaint);

        int activeR = view.getPrimaryPointerIndex() >= 0 ? view.getPrimaryPointerIndex() : view.getMatrixRow();
        int activeC = view.getSecondaryPointerIndex() >= 0 ? view.getSecondaryPointerIndex() : view.getMatrixCol();

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                float left = startX + (c * cellSize);
                float top = startY + (r * cellSize);
                float right = left + cellSize;
                float bottom = top + cellSize;
                rect.set(left, top, right, bottom);

                int val = (board != null && r < board.length && c < board[r].length) ? board[r][c] : 0;

                int cellBg = Color.parseColor("#0F172A");
                if (val < 0) {
                    cellBg = Color.parseColor("#7F1D1D"); // Conflict crimson
                } else if (r == activeR && c == activeC) {
                    cellBg = Color.parseColor("#1E3A8A"); // Blue glow for active cell
                }
                tilePaint.setColor(cellBg);
                canvas.drawRect(rect, tilePaint);

                // Cell boundary
                borderPaint.setColor(Color.parseColor("#334155"));
                borderPaint.setStrokeWidth(1.5f);
                canvas.drawRect(rect, borderPaint);

                // Digit value
                int displayVal = Math.abs(val);
                if (displayVal > 0) {
                    textPaint.setTextSize(cellSize * 0.52f);
                    if (val < 0) {
                        textPaint.setColor(Color.parseColor("#FCA5A5")); // Red digit for conflict
                    } else if (r == activeR && c == activeC) {
                        textPaint.setColor(Color.parseColor("#38BDF8")); // Cyan digit for active placement
                    } else {
                        textPaint.setColor(Color.parseColor("#F1F5F9"));
                    }
                    canvas.drawText(String.valueOf(displayVal), (left + right) / 2f, top + (cellSize * 0.68f), textPaint);
                }
            }
        }

        // Draw thick box separators
        borderPaint.setColor(Color.parseColor("#38BDF8"));
        borderPaint.setStrokeWidth(4f);
        for (int i = 0; i <= n; i += subBox) {
            float x = startX + (i * cellSize);
            canvas.drawLine(x, startY, x, startY + boardSize, borderPaint);
            float y = startY + (i * cellSize);
            canvas.drawLine(startX, y, startX + boardSize, y, borderPaint);
        }

        // Guide legend at bottom
        textPaint.setTextSize(13f);
        textPaint.setColor(Color.parseColor("#94A3B8"));
        canvas.drawText("Active Cell (Blue)    Placed Digit (Cyan)    Conflict / Backtrack (Red)", width / 2f, startY + boardSize + 26f, textPaint);
    }

    // =========================================================================
    // 3. Knight's Tour (5x5 Board)
    // =========================================================================
    private void drawKnightsTour(Canvas canvas, TechniqueVisualizerView view) {
        int[][] board = view.getMatrixValues();
        int n = (board != null && board.length >= 5) ? board.length : 5;

        float width = view.getWidth();
        float height = view.getHeight();
        float boardSize = Math.min(width * 0.88f, height * 0.78f);
        float tileSize = boardSize / n;
        float startX = (width - boardSize) / 2f;
        float startY = (height - boardSize) / 2f + 14f;

        textPaint.setTextSize(26f);
        textPaint.setColor(Color.parseColor("#F8FAFC"));
        canvas.drawText("Knight's Tour (" + n + "×" + n + " Board)", width / 2f, startY - 22f, textPaint);

        int activeMove = view.getPrimaryPointerIndex() >= 0 ? view.getPrimaryPointerIndex() : view.getMatrixK();

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                float left = startX + (c * tileSize);
                float top = startY + (r * tileSize);
                float right = left + tileSize;
                float bottom = top + tileSize;
                rect.set(left, top, right, bottom);

                boolean isDark = (r + c) % 2 == 1;
                int tileColor = isDark ? Color.parseColor("#1E293B") : Color.parseColor("#334155");

                int move = (board != null && r < board.length && c < board[r].length) ? board[r][c] : 0;
                if (move == activeMove && move > 0) {
                    tileColor = Color.parseColor("#854D0E"); // Gold spotlight
                } else if (move > 0) {
                    tileColor = Color.parseColor("#1E3A8A"); // Visited blue
                }

                tilePaint.setColor(tileColor);
                canvas.drawRect(rect, tilePaint);

                if (move > 0) {
                    if (move == activeMove) {
                        textPaint.setTextSize(tileSize * 0.50f);
                        textPaint.setColor(Color.parseColor("#FBBF24"));
                        canvas.drawText("♞", (left + right) / 2f, top + (tileSize * 0.68f), textPaint);
                    } else {
                        textPaint.setTextSize(tileSize * 0.38f);
                        textPaint.setColor(Color.parseColor("#93C5FD"));
                        canvas.drawText(String.valueOf(move), (left + right) / 2f, top + (tileSize * 0.62f), textPaint);
                    }
                }
            }
        }

        borderPaint.setColor(Color.parseColor("#64748B"));
        borderPaint.setStrokeWidth(4f);
        canvas.drawRect(startX, startY, startX + boardSize, startY + boardSize, borderPaint);

        // Draw row/col labels
        float coordTextSize = Math.max(10f, Math.min(16f, tileSize * 0.32f));
        textPaint.setTextSize(coordTextSize);
        textPaint.setColor(Color.parseColor("#94A3B8"));
        for (int i = 0; i < n; i++) {
            char colLetter = (char) ('A' + i);
            canvas.drawText(String.valueOf(colLetter), startX + (i * tileSize) + (tileSize / 2f), startY - 6f, textPaint);
            canvas.drawText(String.valueOf(n - i), startX - 14f, startY + (i * tileSize) + (tileSize * 0.62f), textPaint);
        }

        // Guide legend at bottom
        textPaint.setTextSize(13f);
        textPaint.setColor(Color.parseColor("#94A3B8"));
        canvas.drawText("♞ Active Knight (Gold)    Numbers: Step Sequence    Visited (Blue)", width / 2f, startY + boardSize + 26f, textPaint);
    }

    // =========================================================================
    // 4. Maze Solver (2D Grid)
    // =========================================================================
    private void drawMaze(Canvas canvas, TechniqueVisualizerView view) {
        int[][] maze = view.getMatrixValues();
        int n = (maze != null && (maze.length == 4 || maze.length == 5 || maze.length == 6)) ? maze.length : 5;

        float width = view.getWidth();
        float height = view.getHeight();
        float boardSize = Math.min(width * 0.88f, height * 0.78f);
        float cellSize = boardSize / n;
        float startX = (width - boardSize) / 2f;
        float startY = (height - boardSize) / 2f + 14f;

        textPaint.setTextSize(26f);
        textPaint.setColor(Color.parseColor("#F8FAFC"));
        canvas.drawText("Rat in a Maze (" + n + "×" + n + " Grid)", width / 2f, startY - 22f, textPaint);

        int activeR = view.getPrimaryPointerIndex() >= 0 ? view.getPrimaryPointerIndex() : view.getMatrixRow();
        int activeC = view.getSecondaryPointerIndex() >= 0 ? view.getSecondaryPointerIndex() : view.getMatrixCol();

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                float left = startX + (c * cellSize);
                float top = startY + (r * cellSize);
                float right = left + cellSize;
                float bottom = top + cellSize;
                rect.set(left + 2.5f, top + 2.5f, right - 2.5f, bottom - 2.5f);

                int cellVal = (maze != null && r < maze.length && c < maze[r].length) ? maze[r][c] : 0;
                int bgColor = Color.parseColor("#0F172A");

                if (cellVal == 1) {
                    bgColor = Color.parseColor("#334155"); // Wall
                } else if (cellVal == 2) {
                    bgColor = Color.parseColor("#065F46"); // Visited path (emerald dark)
                } else if (cellVal == 3) {
                    bgColor = Color.parseColor("#7F1D1D"); // Dead-end backtrack (crimson)
                }

                if (r == 0 && c == 0 && cellVal != 1) {
                    bgColor = Color.parseColor("#047857"); // Start emerald
                } else if (r == n - 1 && c == n - 1 && cellVal != 1) {
                    bgColor = (cellVal == 2) ? Color.parseColor("#059669") : Color.parseColor("#991B1B"); // Exit rose
                }

                tilePaint.setColor(bgColor);
                canvas.drawRoundRect(rect, 8f, 8f, tilePaint);

                // Highlight explorer active head
                if (r == activeR && c == activeC) {
                    borderPaint.setColor(Color.parseColor("#F59E0B"));
                    borderPaint.setStrokeWidth(3.5f);
                    canvas.drawRoundRect(rect, 8f, 8f, borderPaint);

                    highlightPaint.setStyle(Paint.Style.FILL);
                    highlightPaint.setColor(Color.parseColor("#F59E0B"));
                    highlightPaint.setAlpha(60);
                    canvas.drawRoundRect(rect, 8f, 8f, highlightPaint);
                }

                if (r == 0 && c == 0) {
                    textPaint.setTextSize(Math.max(10f, cellSize * 0.24f));
                    textPaint.setColor(Color.parseColor("#34D399"));
                    canvas.drawText("START", (left + right) / 2f, top + (cellSize * 0.58f), textPaint);
                } else if (r == n - 1 && c == n - 1) {
                    textPaint.setTextSize(Math.max(10f, cellSize * 0.24f));
                    textPaint.setColor(Color.parseColor("#FCA5A5"));
                    canvas.drawText("EXIT", (left + right) / 2f, top + (cellSize * 0.58f), textPaint);
                } else if (cellVal == 2) {
                    highlightPaint.setStyle(Paint.Style.FILL);
                    highlightPaint.setColor(Color.parseColor("#10B981"));
                    highlightPaint.setAlpha(255);
                    canvas.drawCircle((left + right) / 2f, (top + bottom) / 2f, cellSize * 0.16f, highlightPaint);
                } else if (cellVal == 3) {
                    // Red ✕ marker
                    textPaint.setTextSize(cellSize * 0.40f);
                    textPaint.setColor(Color.parseColor("#EF4444"));
                    canvas.drawText("✕", (left + right) / 2f, top + (cellSize * 0.65f), textPaint);
                } else if (cellVal == 1) {
                    // Wall symbol
                    textPaint.setTextSize(cellSize * 0.38f);
                    textPaint.setColor(Color.parseColor("#64748B"));
                    canvas.drawText("■", (left + right) / 2f, top + (cellSize * 0.64f), textPaint);
                }
            }
        }

        borderPaint.setColor(Color.parseColor("#0EA5E9"));
        borderPaint.setStrokeWidth(4f);
        canvas.drawRoundRect(startX, startY, startX + boardSize, startY + boardSize, 12f, 12f, borderPaint);

        // Bottom legend
        textPaint.setTextSize(13f);
        textPaint.setColor(Color.parseColor("#94A3B8"));
        canvas.drawText("■ Wall    ● Path    ✕ Dead-End Backtrack    ⦿ Explorer Head", width / 2f, startY + boardSize + 28f, textPaint);
    }

    // =========================================================================
    // 5. Hamiltonian Path & Cycle
    // =========================================================================
    private void drawHamiltonian(Canvas canvas, TechniqueVisualizerView view) {
        float width = view.getWidth();
        float height = view.getHeight();

        int[] workingVals = view.getArrayValues();
        int n = (workingVals != null && (workingVals.length == 4 || workingVals.length == 6)) ? workingVals.length : 5;

        String label = view.resolveMatrixLabel();
        boolean isPathMode = label != null && label.contains("Path");

        textPaint.setTextSize(26f);
        textPaint.setColor(Color.parseColor("#F8FAFC"));
        canvas.drawText("Hamiltonian " + (isPathMode ? "Path" : "Cycle") + " (" + n + " Vertices)", width / 2f, 44f, textPaint);

        // Circular layout for symmetric vertices
        float cx = width / 2f;
        float cy = (height - 60f) / 2f + 20f;
        float radius = Math.min(width * 0.36f, height * 0.32f);
        float[][] coords = new float[n][2];
        for (int i = 0; i < n; i++) {
            double angle = (2.0 * Math.PI * i / n) - (Math.PI / 2.0);
            coords[i][0] = (float) (cx + radius * Math.cos(angle));
            coords[i][1] = (float) (cy + radius * Math.sin(angle));
        }

        // Draw base graph edges
        int[][] baseEdges = BacktrackingStepEngine.getHamiltonianEdges(n);
        borderPaint.setColor(Color.parseColor("#334155"));
        borderPaint.setStrokeWidth(3.5f);
        for (int[] edge : baseEdges) {
            int u = edge[0];
            int v = edge[1];
            if (u < n && v < n) {
                canvas.drawLine(coords[u][0], coords[u][1], coords[v][0], coords[v][1], borderPaint);
            }
        }

        // Active edge highlighting
        int activeFrom = view.getActiveEdgeFrom();
        int activeTo = view.getActiveEdgeTo();
        String pLabel = view.getPrimaryPointerLabel();
        boolean isBacktrack = pLabel != null && pLabel.contains("Backtrack");
        boolean isCycleClosure = pLabel != null && pLabel.contains("Closes");

        if (activeFrom >= 0 && activeTo >= 0 && activeFrom < n && activeTo < n) {
            if (isBacktrack) {
                rayPaint.setColor(Color.parseColor("#EF4444"));
                canvas.drawLine(coords[activeFrom][0], coords[activeFrom][1], coords[activeTo][0], coords[activeTo][1], rayPaint);
            } else if (isCycleClosure) {
                borderPaint.setColor(Color.parseColor("#10B981"));
                borderPaint.setStrokeWidth(7f);
                canvas.drawLine(coords[activeFrom][0], coords[activeFrom][1], coords[activeTo][0], coords[activeTo][1], borderPaint);
            } else {
                borderPaint.setColor(Color.parseColor("#F59E0B"));
                borderPaint.setStrokeWidth(6.5f);
                canvas.drawLine(coords[activeFrom][0], coords[activeFrom][1], coords[activeTo][0], coords[activeTo][1], borderPaint);
            }
        }

        // Draw vertices
        float nodeR = (n == 6) ? 24f : 28f;
        for (int i = 0; i < n; i++) {
            float nx = coords[i][0];
            float ny = coords[i][1];

            boolean isActive = (i == activeFrom || i == activeTo);

            int nodeBg = (i == 0) ? Color.parseColor("#065F46") : Color.parseColor("#1E293B");
            if (isActive) {
                nodeBg = isBacktrack ? Color.parseColor("#7F1D1D") : Color.parseColor("#854D0E");
            }

            tilePaint.setColor(nodeBg);
            canvas.drawCircle(nx, ny, nodeR, tilePaint);

            // Ring stroke
            borderPaint.setColor(isActive ? (isBacktrack ? Color.parseColor("#EF4444") : Color.parseColor("#F59E0B")) : (i == 0 ? Color.parseColor("#10B981") : Color.parseColor("#64748B")));
            borderPaint.setStrokeWidth(isActive ? 4.5f : 3f);
            canvas.drawCircle(nx, ny, nodeR, borderPaint);

            // Glow for active
            if (isActive) {
                highlightPaint.setStyle(Paint.Style.STROKE);
                highlightPaint.setStrokeWidth(8f);
                highlightPaint.setColor(isBacktrack ? Color.parseColor("#EF4444") : Color.parseColor("#F59E0B"));
                highlightPaint.setAlpha(60);
                canvas.drawCircle(nx, ny, nodeR + 6f, highlightPaint);
            }

            // Node text: V0, V1...
            textPaint.setTextSize(15f);
            textPaint.setColor(Color.parseColor("#FFFFFF"));
            canvas.drawText("V" + i, nx, ny + 5f, textPaint);
        }

        // Bottom legend
        textPaint.setTextSize(13f);
        textPaint.setColor(Color.parseColor("#94A3B8"));
        canvas.drawText("● V0 Origin    ● Active Edge (Gold)    ┄ Backtrack (Red)    ― Base Edge", width / 2f, cy + radius + 48f, textPaint);
    }

    // =========================================================================
    // 6. Graph m-Coloring
    // =========================================================================
    private void drawGraphColoring(Canvas canvas, TechniqueVisualizerView view) {
        float width = view.getWidth();
        float height = view.getHeight();

        int[] colors = view.getArrayValues();
        int n = (colors != null && colors.length == 5) ? 5 : 4;

        int preset = 1;
        String label = view.resolveMatrixLabel();
        if (label != null) {
            if (label.contains("Preset 2") || label.contains("5-Cycle")) preset = 2;
            else if (label.contains("Preset 3") || label.contains("Bipartite")) preset = 3;
        }

        textPaint.setTextSize(26f);
        textPaint.setColor(Color.parseColor("#F8FAFC"));
        canvas.drawText("Graph m-Coloring (Chromatic Backtracking)", width / 2f, 44f, textPaint);

        // Circular coordinates for vertices
        float cx = width / 2f;
        float cy = (height - 60f) / 2f + 20f;
        float radius = Math.min(width * 0.36f, height * 0.32f);
        float[][] coords = new float[n][2];
        for (int i = 0; i < n; i++) {
            double angle = (2.0 * Math.PI * i / n) - (Math.PI / 2.0);
            coords[i][0] = (float) (cx + radius * Math.cos(angle));
            coords[i][1] = (float) (cy + radius * Math.sin(angle));
        }

        // Draw graph edges
        int[][] edges = BacktrackingStepEngine.getColoringGraphEdges(preset);
        borderPaint.setColor(Color.parseColor("#334155"));
        borderPaint.setStrokeWidth(3.5f);
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            if (u < n && v < n) {
                canvas.drawLine(coords[u][0], coords[u][1], coords[v][0], coords[v][1], borderPaint);
            }
        }

        // Check active conflict edge
        int activeFrom = view.getActiveEdgeFrom();
        int activeTo = view.getActiveEdgeTo();
        if (activeFrom >= 0 && activeTo >= 0 && activeFrom < n && activeTo < n) {
            rayPaint.setColor(Color.parseColor("#EF4444"));
            canvas.drawLine(coords[activeFrom][0], coords[activeFrom][1], coords[activeTo][0], coords[activeTo][1], rayPaint);

            // Midpoint conflict warning badge
            float midX = (coords[activeFrom][0] + coords[activeTo][0]) / 2f;
            float midY = (coords[activeFrom][1] + coords[activeTo][1]) / 2f;
            highlightPaint.setStyle(Paint.Style.FILL);
            highlightPaint.setColor(Color.parseColor("#991B1B"));
            highlightPaint.setAlpha(230);
            canvas.drawCircle(midX, midY, 14f, highlightPaint);
            textPaint.setTextSize(13f);
            textPaint.setColor(Color.parseColor("#FEF2F2"));
            canvas.drawText("✕", midX, midY + 4.5f, textPaint);
        }

        // Draw vertices
        float nodeR = (n == 5) ? 26f : 29f;
        int activePointer = view.getPrimaryPointerIndex();

        for (int i = 0; i < n; i++) {
            float nx = coords[i][0];
            float ny = coords[i][1];

            int c = (colors != null && i < colors.length) ? colors[i] : 0;
            int fillColor;
            switch (c) {
                case 1: fillColor = Color.parseColor("#DC2626"); break; // Crimson
                case 2: fillColor = Color.parseColor("#059669"); break; // Emerald
                case 3: fillColor = Color.parseColor("#0284C7"); break; // Sky Blue
                case 4: fillColor = Color.parseColor("#D97706"); break; // Amber
                default: fillColor = Color.parseColor("#1E293B"); break; // Uncolored
            }

            tilePaint.setColor(fillColor);
            canvas.drawCircle(nx, ny, nodeR, tilePaint);

            boolean isActive = (i == activePointer || i == activeFrom);
            borderPaint.setColor(isActive ? Color.parseColor("#F59E0B") : Color.parseColor("#64748B"));
            borderPaint.setStrokeWidth(isActive ? 4.5f : 3f);
            canvas.drawCircle(nx, ny, nodeR, borderPaint);

            if (isActive) {
                highlightPaint.setStyle(Paint.Style.STROKE);
                highlightPaint.setStrokeWidth(8f);
                highlightPaint.setColor(Color.parseColor("#F59E0B"));
                highlightPaint.setAlpha(60);
                canvas.drawCircle(nx, ny, nodeR + 6f, highlightPaint);
            }

            textPaint.setTextSize(14f);
            textPaint.setColor(Color.parseColor("#FFFFFF"));
            if (c > 0) {
                canvas.drawText("V" + i, nx, ny - 3f, textPaint);
                textPaint.setTextSize(11f);
                canvas.drawText("C" + c, nx, ny + 12f, textPaint);
            } else {
                canvas.drawText("V" + i, nx, ny + 5f, textPaint);
            }
        }

        // Color palette guide legend at bottom
        textPaint.setTextSize(13f);
        textPaint.setColor(Color.parseColor("#94A3B8"));
        canvas.drawText("● C1 (Red)   ● C2 (Green)   ● C3 (Blue)   ● C4 (Amber)   ┄ Conflict", width / 2f, cy + radius + 48f, textPaint);
    }

    @Override
    public int resolveDesiredWidth(TechniqueVisualizerView view, String techniqueId) {
        return view.resolveScreenWidthDesiredWidth();
    }

    @Override
    public int resolveDesiredHeight(TechniqueVisualizerView view, String techniqueId) {
        return Math.max(view.resolveTraversalDesiredHeight(), 540);
    }
}
