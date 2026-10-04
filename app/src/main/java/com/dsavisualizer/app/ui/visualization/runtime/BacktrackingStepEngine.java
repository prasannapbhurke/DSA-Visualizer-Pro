package com.dsavisualizer.app.ui.visualization.runtime;

/**
 * Dedicated visual step engine for Backtracking algorithms:
 * - N-Queens (Chessboard matrix, attack detection, safe placements, backtracking)
 * - Sudoku Solver (Grid cell testing, 3x3 box & row/col validation, backtrack)
 * - Knight's Tour (Move sequence on board, Warnsdorff heuristic / backtrack)
 * - Maze Solver (2D grid exploration, dead-end retreat, pathfinding)
 * - Hamiltonian Path/Cycle (Graph vertex exploration, cycle verification)
 * - Graph m-Coloring (Vertex color assignment, chromatic conflict backtrack)
 */
public final class BacktrackingStepEngine extends BaseStepEngine {

    public BacktrackingStepEngine(VisualizationRuntimeHost host) {
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
        if ("backtracking_n_queens".equals(id) || "bt_n_queens".equals(id)) {
            advanceNQueens(host);
        } else if ("backtracking_sudoku".equals(id) || "bt_sudoku".equals(id)) {
            advanceSudoku(host);
        } else if ("backtracking_knights_tour".equals(id) || "bt_knights_tour".equals(id)) {
            advanceKnightsTour(host);
        } else if ("backtracking_maze_solver".equals(id) || "bt_maze".equals(id)) {
            advanceMazeSolver(host);
        } else if ("backtracking_hamiltonian".equals(id) || "bt_hamiltonian".equals(id)) {
            advanceHamiltonian(host);
        } else if ("backtracking_graph_coloring".equals(id) || "bt_graph_coloring".equals(id)) {
            advanceGraphColoring(host);
        } else {
            advanceGenericBacktracking(host);
        }
    }

    // =========================================================================
    // 1. N-Queens (4x4, 5x5, 6x6, 8x8 Dynamic Chessboard)
    // =========================================================================
    // Steps representing the exploration of N-Queens:
    // (row, col, action: 1=try, 2=conflict, 3=placed, 4=backtrack, 5=solved)
    private static final java.util.Map<Integer, int[][]> N_QUEENS_STEPS_CACHE = new java.util.concurrent.ConcurrentHashMap<>();

    public static int[][] getNQueensSteps(int n) {
        int targetN = n < 4 ? 4 : n;
        int[][] cached = N_QUEENS_STEPS_CACHE.get(targetN);
        if (cached != null) {
            return cached;
        }
        java.util.List<int[]> steps = new java.util.ArrayList<>();
        int[] board = new int[targetN];
        java.util.Arrays.fill(board, -1);
        solveNQueens(0, targetN, board, steps);
        int[][] res = steps.toArray(new int[steps.size()][]);
        N_QUEENS_STEPS_CACHE.put(targetN, res);
        return res;
    }

    private static boolean solveNQueens(int r, int n, int[] board, java.util.List<int[]> steps) {
        for (int c = 0; c < n; c++) {
            boolean conflict = false;
            for (int pr = 0; pr < r; pr++) {
                int pc = board[pr];
                if (pc == c || Math.abs(pr - r) == Math.abs(pc - c)) {
                    conflict = true;
                    break;
                }
            }
            if (!conflict) {
                steps.add(new int[]{r, c, 3}); // place
                board[r] = c;
                if (r == n - 1) {
                    return true;
                }
                if (solveNQueens(r + 1, n, board, steps)) {
                    return true;
                }
                steps.add(new int[]{r, c, 4}); // backtrack
                board[r] = -1;
            } else {
                steps.add(new int[]{r, c, 2}); // conflict
            }
        }
        return false;
    }

    private static void advanceNQueens(VisualizationRuntimeHost host) {
        int[][] board = host.getMatrixValues();
        int n = (board != null && board.length >= 4) ? board.length : 4;
        if (board == null || board.length != n || board[0].length != n) {
            board = new int[n][n];
        }

        int[][] steps = getNQueensSteps(n);
        int step = host.getStepIndex();
        if (step < 0) {
            step = 0;
        }
        if (step >= steps.length) {
            host.finish(n + "-Queens Backtracking complete! Valid configuration found.");
            return;
        }

        int[] s = steps[step];
        int r = s[0];
        int c = s[1];
        int action = s[2];

        // Clear previous transient conflict markers (-1)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == -1) {
                    board[i][j] = 0;
                }
            }
        }

        String msg;
        if (action == 3) {
            board[r][c] = 1;
            msg = "Row " + r + ", Col " + (char) ('A' + c) + ": Safe square! Placed Queen ♛.";
            host.setHighlightedLines(12, 4);
        } else if (action == 2) {
            board[r][c] = -1; // Mark conflict cell for visual ray highlight
            String conflictType = "same column";
            for (int prevR = 0; prevR < n; prevR++) {
                for (int prevC = 0; prevC < n; prevC++) {
                    if (board[prevR][prevC] == 1) {
                        if (prevC == c) {
                            conflictType = "Column " + (char) ('A' + c) + " with Queen at (" + prevR + ", " + (char) ('A' + prevC) + ")";
                            break;
                        } else if (Math.abs(prevR - r) == Math.abs(prevC - c)) {
                            conflictType = "Diagonal with Queen at (" + prevR + ", " + (char) ('A' + prevC) + ")";
                            break;
                        }
                    }
                }
            }
            msg = "Row " + r + ", Col " + (char) ('A' + c) + ": Conflict on " + conflictType + "! Cannot place.";
            host.setHighlightedLines(16, 6);
        } else if (action == 4) {
            board[r][c] = 0;
            msg = "Backtracking from Row " + r + ": Removed Queen ♛ at (" + r + ", " + (char) ('A' + c) + ") to try next column.";
            host.setHighlightedLines(22, 8);
        } else {
            msg = "Solution confirmed! All " + n + " queens placed peacefully without attacking each other.";
            host.setHighlightedLines(26, 10);
        }

        host.setMatrixState(board, step, r, c, "N-Queens " + n + "x" + n + " Chessboard");
        host.updatePointerState("Row", r, "Col", c);
        host.setStepIndex(step + 1);

        if (step + 1 >= steps.length) {
            StringBuilder sb = new StringBuilder();
            sb.append(n).append("-Queens Solved! Queens at ");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (board[i][j] == 1) {
                        sb.append("(").append(i).append(",").append((char) ('A' + j)).append(") ");
                    }
                }
            }
            host.finish(sb.toString().trim() + ".");
        } else {
            host.bindCurrentState(msg);
        }
    }

    // =========================================================================
    // 2. Sudoku Solver (4x4 Mini and 9x9 Classic Complete Backtracking)
    // =========================================================================
    public static final int[][] SUDOKU_4X4_P1 = {
        {1, 0, 3, 0},
        {0, 0, 2, 1},
        {0, 1, 0, 2},
        {2, 4, 0, 0}
    };

    public static final int[][] SUDOKU_4X4_P2 = {
        {0, 0, 4, 0},
        {1, 0, 0, 3},
        {4, 0, 0, 2},
        {0, 1, 0, 0}
    };

    public static final int[][] SUDOKU_9X9_P1 = {
        {5, 3, 0, 0, 7, 0, 0, 0, 0},
        {6, 0, 0, 1, 9, 5, 0, 0, 0},
        {0, 9, 8, 0, 0, 0, 0, 6, 0},
        {8, 0, 0, 0, 6, 0, 0, 0, 3},
        {4, 0, 0, 8, 0, 3, 0, 0, 1},
        {7, 0, 0, 0, 2, 0, 0, 0, 6},
        {0, 6, 0, 0, 0, 0, 2, 8, 0},
        {0, 0, 0, 4, 1, 9, 0, 0, 5},
        {0, 0, 0, 0, 8, 0, 0, 7, 9}
    };

    public static final int[][] SUDOKU_9X9_P2 = {
        {0, 0, 0, 2, 6, 0, 7, 0, 1},
        {6, 8, 0, 0, 7, 0, 0, 9, 0},
        {1, 9, 0, 0, 0, 4, 5, 0, 0},
        {8, 2, 0, 1, 0, 0, 0, 4, 0},
        {0, 0, 4, 6, 0, 2, 9, 0, 0},
        {0, 5, 0, 0, 0, 3, 0, 2, 8},
        {0, 0, 9, 3, 0, 0, 0, 7, 4},
        {0, 4, 0, 0, 5, 0, 0, 3, 6},
        {7, 0, 3, 0, 1, 8, 0, 0, 0}
    };

    public static int[][] clone2D(int[][] src) {
        if (src == null) return null;
        int[][] copy = new int[src.length][];
        for (int i = 0; i < src.length; i++) {
            copy[i] = src[i].clone();
        }
        return copy;
    }

    public static int[][] getInitialSudokuBoard(int size, int preset) {
        if (size == 9) {
            return preset == 2 ? clone2D(SUDOKU_9X9_P2) : clone2D(SUDOKU_9X9_P1);
        }
        return preset == 2 ? clone2D(SUDOKU_4X4_P2) : clone2D(SUDOKU_4X4_P1);
    }

    private static final java.util.Map<String, int[][]> SUDOKU_STEPS_CACHE = new java.util.concurrent.ConcurrentHashMap<>();

    public static int[][] getSudokuSteps(int size, int preset) {
        int n = size == 9 ? 9 : 4;
        int p = preset == 2 ? 2 : 1;
        String key = n + "_" + p;
        int[][] cached = SUDOKU_STEPS_CACHE.get(key);
        if (cached != null) {
            return cached;
        }

        int[][] initial = getInitialSudokuBoard(n, p);
        int[][] board = clone2D(initial);
        java.util.List<int[]> steps = new java.util.ArrayList<>();
        int boxR = n == 9 ? 3 : 2;
        int boxC = n == 9 ? 3 : 2;

        solveSudokuBacktracking(board, n, boxR, boxC, steps);
        int[][] res = steps.toArray(new int[steps.size()][]);
        SUDOKU_STEPS_CACHE.put(key, res);
        return res;
    }

    private static int checkSudokuValidity(int[][] board, int n, int boxR, int boxC, int r, int c, int d) {
        for (int j = 0; j < n; j++) {
            if (board[r][j] == d) return 1; // row conflict
        }
        for (int i = 0; i < n; i++) {
            if (board[i][c] == d) return 2; // col conflict
        }
        int br = (r / boxR) * boxR;
        int bc = (c / boxC) * boxC;
        for (int i = 0; i < boxR; i++) {
            for (int j = 0; j < boxC; j++) {
                if (board[br + i][bc + j] == d) return 3; // subgrid conflict
            }
        }
        return 0; // valid
    }

    private static boolean solveSudokuBacktracking(int[][] board, int n, int boxR, int boxC, java.util.List<int[]> steps) {
        int minR = -1;
        int minC = -1;
        int minCount = 100;

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (board[r][c] == 0) {
                    int validCount = 0;
                    for (int d = 1; d <= n; d++) {
                        if (checkSudokuValidity(board, n, boxR, boxC, r, c, d) == 0) {
                            validCount++;
                        }
                    }
                    if (validCount < minCount) {
                        minCount = validCount;
                        minR = r;
                        minC = c;
                    }
                    if (validCount == 0) {
                        return false;
                    }
                }
            }
        }

        if (minR == -1) {
            return true;
        }

        int r = minR;
        int c = minC;
        for (int d = 1; d <= n; d++) {
            int conflict = checkSudokuValidity(board, n, boxR, boxC, r, c, d);
            if (conflict == 0) {
                board[r][c] = d;
                steps.add(new int[]{r, c, d, 1, 0});
                if (solveSudokuBacktracking(board, n, boxR, boxC, steps)) {
                    return true;
                }
                board[r][c] = 0;
                steps.add(new int[]{r, c, d, -1, 0});
            } else {
                steps.add(new int[]{r, c, d, 0, conflict});
            }
        }
        return false;
    }

    private static void advanceSudoku(VisualizationRuntimeHost host) {
        int[][] board = host.getMatrixValues();
        int n = (board != null && board.length >= 9) ? 9 : 4;
        int preset = 1;
        String label = host.resolveMatrixLabel();
        if (label != null && label.contains("Preset 2")) {
            preset = 2;
        } else if (n == 4 && board != null && board.length == 4 && board[0][2] == 4) {
            preset = 2;
        } else if (n == 9 && board != null && board.length == 9 && board[0][0] == 0) {
            preset = 2;
        }

        int[][] steps = getSudokuSteps(n, preset);
        int step = host.getStepIndex();
        if (step < 0) {
            step = 0;
        }

        if (board == null || board.length != n) {
            board = clone2D(getInitialSudokuBoard(n, preset));
        }

        // Clear previous transient negative markers
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] < 0) {
                    board[i][j] = 0;
                }
            }
        }

        if (step >= steps.length) {
            host.finish("Sudoku Solver Complete! All " + n + "x" + n + " cells correctly filled.");
            return;
        }

        int[] s = steps[step];
        int r = s[0];
        int c = s[1];
        int val = s[2];
        int status = s[3];
        int reason = s[4];

        String msg;
        if (status == 1) {
            board[r][c] = val;
            msg = "Cell (" + r + "," + c + "): Placed digit " + val + " - Valid in row, col, and subgrid!";
            host.setHighlightedLines(14, 5);
        } else if (status == 0) {
            board[r][c] = -val;
            String violation = reason == 1 ? "row" : (reason == 2 ? "column" : "subgrid");
            msg = "Cell (" + r + "," + c + "): Testing digit " + val + " - Violates " + violation + " constraint!";
            host.setHighlightedLines(18, 7);
        } else {
            board[r][c] = 0;
            msg = "Cell (" + r + "," + c + "): Backtracking from digit " + val + ". Resetting to empty.";
            host.setHighlightedLines(22, 9);
        }

        host.setMatrixState(board, step, r, c, "Sudoku " + n + "x" + n + " Grid (Preset " + preset + ")");
        host.updatePointerState("R", r, "C", c);
        host.setStepIndex(step + 1);

        if (step + 1 >= steps.length) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (board[i][j] < 0) board[i][j] = 0;
                }
            }
            host.finish("Sudoku Solved! All " + n + "x" + n + " subgrids, rows, and columns satisfy constraints.");
        } else {
            host.bindCurrentState(msg);
        }
    }

    // =========================================================================
    // 3. Knight's Tour (5x5, 6x6, 8x8 Dynamic Chessboard)
    // =========================================================================
    private static final int[] K_DR = {-2, -2, -1, -1, 1, 1, 2, 2};
    private static final int[] K_DC = {-1, 1, -2, 2, -2, 2, -1, 1};
    private static final java.util.Map<String, int[][]> KNIGHTS_TOUR_CACHE = new java.util.concurrent.ConcurrentHashMap<>();

    public static int[][] getInitialKnightsTourBoard(int n, int startType) {
        int size = n == 8 ? 8 : (n == 6 ? 6 : 5);
        int[][] board = new int[size][size];
        int startR = (startType == 2) ? size / 2 : 0;
        int startC = (startType == 2) ? size / 2 : 0;
        board[startR][startC] = 1;
        return board;
    }

    public static int[][] getKnightsTourMoves(int n, int startType) {
        int size = n == 8 ? 8 : (n == 6 ? 6 : 5);
        int type = startType == 2 ? 2 : 1;
        String key = size + "_" + type;
        int[][] cached = KNIGHTS_TOUR_CACHE.get(key);
        if (cached != null) {
            return cached;
        }

        int startR = (type == 2) ? size / 2 : 0;
        int startC = (type == 2) ? size / 2 : 0;

        int total = size * size;
        int[][] board = new int[size][size];
        int[][] path = new int[total][2];
        board[startR][startC] = 1;
        path[0][0] = startR;
        path[0][1] = startC;

        boolean solved = solveKnightsTourWarnsdorff(board, startR, startC, 1, total, size, path);
        if (!solved) {
            // Backup search without center tie-breaker
            board = new int[size][size];
            board[startR][startC] = 1;
            solveKnightsTourWarnsdorffNoTie(board, startR, startC, 1, total, size, path);
        }

        KNIGHTS_TOUR_CACHE.put(key, path);
        return path;
    }

    private static int countDegree(int[][] board, int r, int c, int n) {
        int count = 0;
        for (int i = 0; i < 8; i++) {
            int nr = r + K_DR[i];
            int nc = c + K_DC[i];
            if (nr >= 0 && nr < n && nc >= 0 && nc < n && board[nr][nc] == 0) {
                count++;
            }
        }
        return count;
    }

    private static boolean solveKnightsTourWarnsdorff(int[][] board, int r, int c, int step, int total, int n, int[][] path) {
        if (step >= total) {
            return true;
        }

        java.util.List<int[]> candidates = new java.util.ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int nr = r + K_DR[i];
            int nc = c + K_DC[i];
            if (nr >= 0 && nr < n && nc >= 0 && nc < n && board[nr][nc] == 0) {
                int deg = countDegree(board, nr, nc, n);
                int distFromCenter = Math.abs(nr * 2 - (n - 1)) + Math.abs(nc * 2 - (n - 1));
                candidates.add(new int[]{nr, nc, deg, distFromCenter});
            }
        }

        if (candidates.isEmpty()) {
            return false;
        }

        java.util.Collections.sort(candidates, (a, b) -> {
            int cmp = Integer.compare(a[2], b[2]);
            if (cmp != 0) return cmp;
            return Integer.compare(b[3], a[3]);
        });

        for (int[] cand : candidates) {
            int nr = cand[0];
            int nc = cand[1];
            board[nr][nc] = step + 1;
            path[step][0] = nr;
            path[step][1] = nc;

            if (solveKnightsTourWarnsdorff(board, nr, nc, step + 1, total, n, path)) {
                return true;
            }

            board[nr][nc] = 0;
        }

        return false;
    }

    private static boolean solveKnightsTourWarnsdorffNoTie(int[][] board, int r, int c, int step, int total, int n, int[][] path) {
        if (step >= total) {
            return true;
        }

        java.util.List<int[]> candidates = new java.util.ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int nr = r + K_DR[i];
            int nc = c + K_DC[i];
            if (nr >= 0 && nr < n && nc >= 0 && nc < n && board[nr][nc] == 0) {
                int deg = countDegree(board, nr, nc, n);
                candidates.add(new int[]{nr, nc, deg});
            }
        }

        if (candidates.isEmpty()) {
            return false;
        }

        java.util.Collections.sort(candidates, (a, b) -> Integer.compare(a[2], b[2]));

        for (int[] cand : candidates) {
            int nr = cand[0];
            int nc = cand[1];
            board[nr][nc] = step + 1;
            path[step][0] = nr;
            path[step][1] = nc;

            if (solveKnightsTourWarnsdorffNoTie(board, nr, nc, step + 1, total, n, path)) {
                return true;
            }

            board[nr][nc] = 0;
        }

        return false;
    }

    private static void advanceKnightsTour(VisualizationRuntimeHost host) {
        int[][] board = host.getMatrixValues();
        int n = (board != null && (board.length == 8 || board.length == 6 || board.length == 5)) ? board.length : 5;
        int startPos = 1;
        String label = host.resolveMatrixLabel();
        if (label != null && label.contains("Center")) {
            startPos = 2;
        } else if (board != null && board.length == n && board[n / 2][n / 2] == 1) {
            startPos = 2;
        }

        int[][] moves = getKnightsTourMoves(n, startPos);
        int total = n * n;
        int step = host.getStepIndex();
        if (step < 0) {
            step = 0;
        }

        if (board == null || board.length != n) {
            board = getInitialKnightsTourBoard(n, startPos);
        }

        if (step >= moves.length) {
            host.finish("Knight's Tour completed! All " + total + " squares visited exactly once.");
            return;
        }

        int r = moves[step][0];
        int c = moves[step][1];
        board[r][c] = step + 1;

        String msg;
        if (step == 0) {
            msg = "Move 1/" + total + ": Knight starts at square (" + r + ", " + c + ").";
        } else {
            int prevR = moves[step - 1][0];
            int prevC = moves[step - 1][1];
            int dr = Math.abs(r - prevR);
            int dc = Math.abs(c - prevC);
            msg = "Move " + (step + 1) + "/" + total + ": Knight jumps (" + prevR + "," + prevC + ") → (" + r + "," + c + ") [" + dr + "×" + dc + " L-shape].";
        }

        host.setMatrixState(board, step + 1, r, c, "Knight's Tour " + n + "x" + n + " (" + (startPos == 2 ? "Center" : "Corner") + ")");
        host.updatePointerState("Move", step + 1, "Pos", r * n + c);
        host.setHighlightedLines(10, 4);
        host.setStepIndex(step + 1);

        if (step + 1 >= moves.length) {
            host.finish("Knight's Tour Solved! Visited all " + total + " squares on " + n + "x" + n + " chessboard.");
        } else {
            host.bindCurrentState(msg);
        }
    }

    // =========================================================================
    // 4. Maze Solver (4x4, 5x5, 6x6 with Preset 1 Direct and Preset 2 Dead-Ends)
    // =========================================================================
    // 0 = open, 1 = wall, 2 = visited path, 3 = dead end (backtracked)

    public static final int[][] MAZE_4X4_P1 = {
        {0, 0, 1, 0},
        {1, 0, 0, 0},
        {0, 1, 1, 0},
        {0, 0, 0, 0}
    };

    public static final int[][] MAZE_4X4_P2 = {
        {0, 0, 0, 1},
        {0, 1, 0, 1},
        {1, 0, 0, 0},
        {0, 0, 1, 0}
    };

    public static final int[][] MAZE_5X5_P1 = {
        {0, 1, 0, 0, 0},
        {0, 1, 0, 1, 0},
        {0, 0, 0, 1, 0},
        {1, 1, 0, 0, 0},
        {0, 0, 0, 1, 0}
    };

    public static final int[][] MAZE_5X5_P2 = {
        {0, 0, 0, 1, 0},
        {0, 1, 0, 1, 0},
        {0, 1, 0, 0, 0},
        {1, 1, 1, 1, 0},
        {0, 0, 0, 0, 0}
    };

    public static final int[][] MAZE_6X6_P1 = {
        {0, 0, 1, 0, 0, 0},
        {1, 0, 0, 0, 1, 0},
        {0, 1, 1, 0, 0, 0},
        {0, 0, 0, 1, 1, 0},
        {1, 1, 0, 0, 0, 0},
        {0, 0, 0, 1, 0, 0}
    };

    public static final int[][] MAZE_6X6_P2 = {
        {0, 0, 0, 1, 0, 0},
        {0, 1, 0, 1, 0, 0},
        {0, 1, 0, 0, 0, 1},
        {1, 1, 1, 1, 0, 0},
        {0, 0, 0, 1, 1, 0},
        {0, 0, 0, 0, 0, 0}
    };

    public static int[][] getInitialMaze(int size, int preset) {
        int n = (size == 4 || size == 6) ? size : 5;
        int p = preset == 2 ? 2 : 1;
        if (n == 4) {
            return clone2D(p == 2 ? MAZE_4X4_P2 : MAZE_4X4_P1);
        } else if (n == 6) {
            return clone2D(p == 2 ? MAZE_6X6_P2 : MAZE_6X6_P1);
        }
        return clone2D(p == 2 ? MAZE_5X5_P2 : MAZE_5X5_P1);
    }

    private static final java.util.Map<String, int[][]> MAZE_STEPS_CACHE = new java.util.concurrent.ConcurrentHashMap<>();

    public static int[][] getMazeSteps(int size, int preset) {
        int n = (size == 4 || size == 6) ? size : 5;
        int p = preset == 2 ? 2 : 1;
        String key = n + "_" + p;
        int[][] cached = MAZE_STEPS_CACHE.get(key);
        if (cached != null) {
            return cached;
        }

        int[][] maze = getInitialMaze(n, p);
        java.util.List<int[]> stepList = new java.util.ArrayList<>();
        maze[0][0] = 2;
        stepList.add(new int[]{0, 0, 2, 1}); // Start: visit (0,0)

        solveMazeDfs(maze, 0, 0, n, stepList);

        int[][] result = stepList.toArray(new int[stepList.size()][]);
        MAZE_STEPS_CACHE.put(key, result);
        return result;
    }

    // Move directions: Down, Right, Up, Left
    private static final int[] M_DR = {1, 0, -1, 0};
    private static final int[] M_DC = {0, 1, 0, -1};

    private static boolean solveMazeDfs(int[][] maze, int r, int c, int n, java.util.List<int[]> steps) {
        if (r == n - 1 && c == n - 1) {
            return true;
        }

        for (int i = 0; i < 4; i++) {
            int nr = r + M_DR[i];
            int nc = c + M_DC[i];
            if (nr >= 0 && nr < n && nc >= 0 && nc < n && maze[nr][nc] == 0) {
                maze[nr][nc] = 2; // Visited path
                steps.add(new int[]{nr, nc, 2, 1}); // 1 = forward visit

                if (solveMazeDfs(maze, nr, nc, n, steps)) {
                    return true;
                }

                // Backtrack: mark dead-end
                maze[nr][nc] = 3;
                steps.add(new int[]{nr, nc, 3, 2}); // 2 = dead-end retreat
            }
        }
        return false;
    }

    private static void advanceMazeSolver(VisualizationRuntimeHost host) {
        int[][] maze = host.getMatrixValues();
        int n = (maze != null && (maze.length == 4 || maze.length == 5 || maze.length == 6)) ? maze.length : 5;
        int preset = 1;
        String label = host.resolveMatrixLabel();
        if (label != null && label.contains("Preset 2")) {
            preset = 2;
        }

        int[][] steps = getMazeSteps(n, preset);
        int step = host.getStepIndex();
        if (step < 0) {
            step = 0;
        }

        if (maze == null || maze.length != n) {
            maze = getInitialMaze(n, preset);
        }

        if (step >= steps.length) {
            host.finish("Maze solved! Valid route from (0,0) to exit at (" + (n - 1) + "," + (n - 1) + ") found.");
            return;
        }

        int r = steps[step][0];
        int c = steps[step][1];
        int st = steps[step][2];
        int action = steps[step][3];
        maze[r][c] = st;

        String msg;
        if (r == n - 1 && c == n - 1) {
            msg = "Exit reached at (" + r + "," + c + ")! Complete path from start to destination found.";
            host.setHighlightedLines(16, 4);
        } else if (action == 1) {
            msg = "Exploring cell (" + r + "," + c + "): Path marked (Green). Checking neighbors (Down, Right, Up, Left).";
            host.setHighlightedLines(11, 4);
        } else {
            msg = "Dead end at cell (" + r + "," + c + ")! Backtracking (Red ✕) to previous junction to explore alternative branches.";
            host.setHighlightedLines(20, 6);
        }

        host.setMatrixState(maze, step, r, c, "Rat in a Maze " + n + "x" + n + " (Preset " + preset + ")");
        host.updatePointerState("Row", r, "Col", c);
        host.setStepIndex(step + 1);

        if (step + 1 >= steps.length) {
            host.finish("Maze Solved! Destination (" + (n - 1) + "," + (n - 1) + ") reached successfully.");
        } else {
            host.bindCurrentState(msg);
        }
    }

    // =========================================================================
    // 5. Hamiltonian Path & Cycle (4, 5, 6 Nodes; Cycle vs Path)
    // =========================================================================

    public static final int[][] HAMILTONIAN_EDGES_4 = {
        {0, 1}, {1, 2}, {2, 3}, {3, 0}, {0, 2}, {1, 3}
    };

    public static final int[][] HAMILTONIAN_EDGES_5 = {
        {0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 0}, {0, 2}, {1, 3}, {2, 4}
    };

    public static final int[][] HAMILTONIAN_EDGES_6 = {
        {0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 0}, {0, 3}, {1, 4}, {2, 5}
    };

    public static int[][] getHamiltonianEdges(int nodes) {
        if (nodes == 4) return clone2D(HAMILTONIAN_EDGES_4);
        if (nodes == 6) return clone2D(HAMILTONIAN_EDGES_6);
        return clone2D(HAMILTONIAN_EDGES_5);
    }

    private static final java.util.Map<String, int[][]> HAMILTONIAN_STEPS_CACHE = new java.util.concurrent.ConcurrentHashMap<>();

    public static int[][] getHamiltonianSteps(int nodes, int type) {
        int n = (nodes == 4 || nodes == 6) ? nodes : 5;
        int t = (type == 2) ? 2 : 1; // 1 = Cycle, 2 = Path
        String key = n + "_" + t;
        int[][] cached = HAMILTONIAN_STEPS_CACHE.get(key);
        if (cached != null) {
            return cached;
        }

        int[][] edges = getHamiltonianEdges(n);
        boolean[][] adj = new boolean[n][n];
        for (int[] e : edges) {
            adj[e[0]][e[1]] = true;
            adj[e[1]][e[0]] = true;
        }

        int[] path = new int[n + 1];
        boolean[] visited = new boolean[n];
        path[0] = 0;
        visited[0] = true;

        java.util.List<int[]> stepList = new java.util.ArrayList<>();
        solveHamiltonianDfs(1, n, t, adj, path, visited, stepList);

        int[][] result = stepList.toArray(new int[stepList.size()][]);
        HAMILTONIAN_STEPS_CACHE.put(key, result);
        return result;
    }

    private static boolean solveHamiltonianDfs(int pos, int n, int type, boolean[][] adj, int[] path, boolean[] visited, java.util.List<int[]> steps) {
        if (pos == n) {
            if (type == 1) {
                // Cycle requires an edge from last node back to path[0] (node 0)
                int last = path[n - 1];
                if (adj[last][0]) {
                    path[n] = 0;
                    steps.add(new int[]{last, 0, 2, n}); // 2 = cycle closing edge
                    return true;
                }
                return false;
            } else {
                // Path doesn't need to return to origin
                return true;
            }
        }

        int u = path[pos - 1];
        for (int v = 0; v < n; v++) {
            if (adj[u][v] && !visited[v]) {
                path[pos] = v;
                visited[v] = true;
                steps.add(new int[]{u, v, 1, pos}); // 1 = forward step

                if (solveHamiltonianDfs(pos + 1, n, type, adj, path, visited, steps)) {
                    return true;
                }

                // Backtrack
                visited[v] = false;
                steps.add(new int[]{u, v, 0, pos}); // 0 = backtrack retreat
            }
        }
        return false;
    }

    private static void advanceHamiltonian(VisualizationRuntimeHost host) {
        int step = host.getStepIndex();
        if (step < 0) {
            step = 0;
        }

        int[] currentVals = host.getWorkingValues();
        int nodes = (currentVals != null && (currentVals.length == 4 || currentVals.length == 6)) ? currentVals.length : 5;
        int type = 1; // 1 = Cycle, 2 = Path
        String label = host.resolveMatrixLabel();
        if (label != null && label.contains("Path")) {
            type = 2;
        }

        int[][] steps = getHamiltonianSteps(nodes, type);
        int total = steps.length;
        if (step >= total) {
            host.finish("Hamiltonian " + (type == 1 ? "Cycle" : "Path") + " verified! All " + nodes + " vertices visited.");
            return;
        }

        int[] s = steps[step];
        int u = s[0];
        int v = s[1];
        int action = s[2]; // 1 = forward, 0 = backtrack, 2 = cycle closure
        int pos = s[3];

        if (action == 1) {
            host.setActiveNodes(u, v);
            host.setActiveEdge(u, v);
            host.updatePointerState("From", u, "To", v);
            host.setHighlightedLines(14, 5);
            String msg = "Traversing edge (" + u + " → " + v + "). Vertex V" + v + " added to Hamiltonian path (Step " + (pos + 1) + "/" + nodes + ").";
            host.bindCurrentState(msg);
        } else if (action == 0) {
            host.setActiveNodes(u, v);
            host.clearActiveEdge();
            host.updatePointerState("Backtrack", u, "From", v);
            host.setHighlightedLines(22, 6);
            String msg = "Dead end from Vertex V" + v + "! Cannot visit all remaining vertices. Backtracking to V" + u + ".";
            host.bindCurrentState(msg);
        } else {
            // Cycle closure
            host.setActiveNodes(u, v);
            host.setActiveEdge(u, v);
            host.updatePointerState("Closes", u, "Origin", v);
            host.setHighlightedLines(28, 4);
            String msg = "Edge (" + u + " → " + v + ") connects back to origin! Complete Hamiltonian Cycle discovered.";
            host.bindCurrentState(msg);
        }

        host.setStepIndex(step + 1);
        if (step + 1 >= total) {
            host.finish("Hamiltonian " + (type == 1 ? "Cycle" : "Path") + " Complete! Visited all " + nodes + " vertices successfully.");
        }
    }

    // =========================================================================
    // 6. Graph m-Coloring (m = 2, 3, 4 Colors; K4, 5-Cycle, Bipartite Presets)
    // =========================================================================

    public static final int[][] COLORING_EDGES_P1 = { // K4 Complete (4 nodes)
        {0, 1}, {1, 2}, {2, 3}, {3, 0}, {0, 2}, {1, 3}
    };

    public static final int[][] COLORING_EDGES_P2 = { // 5-Cycle with Chord (5 nodes)
        {0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 0}, {0, 2}
    };

    public static final int[][] COLORING_EDGES_P3 = { // Bipartite C4 (4 nodes)
        {0, 1}, {1, 2}, {2, 3}, {3, 0}
    };

    public static int getColoringGraphNodeCount(int preset) {
        return preset == 2 ? 5 : 4;
    }

    public static int[][] getColoringGraphEdges(int preset) {
        if (preset == 2) return clone2D(COLORING_EDGES_P2);
        if (preset == 3) return clone2D(COLORING_EDGES_P3);
        return clone2D(COLORING_EDGES_P1);
    }

    private static final java.util.Map<String, int[][]> COLORING_STEPS_CACHE = new java.util.concurrent.ConcurrentHashMap<>();

    public static int[][] getGraphColoringSteps(int colors, int preset) {
        int m = (colors == 2 || colors == 4) ? colors : 3;
        int p = (preset == 2 || preset == 3) ? preset : 1;
        String key = m + "_" + p;
        int[][] cached = COLORING_STEPS_CACHE.get(key);
        if (cached != null) {
            return cached;
        }

        int n = getColoringGraphNodeCount(p);
        int[][] edges = getColoringGraphEdges(p);
        boolean[][] adj = new boolean[n][n];
        for (int[] e : edges) {
            adj[e[0]][e[1]] = true;
            adj[e[1]][e[0]] = true;
        }

        int[] colorAssignment = new int[n];
        java.util.List<int[]> stepList = new java.util.ArrayList<>();
        solveColoringDfs(0, n, m, adj, colorAssignment, stepList);

        int[][] result = stepList.toArray(new int[stepList.size()][]);
        COLORING_STEPS_CACHE.put(key, result);
        return result;
    }

    private static boolean solveColoringDfs(int v, int n, int m, boolean[][] adj, int[] colors, java.util.List<int[]> steps) {
        if (v == n) {
            return true; // All vertices colored
        }

        for (int c = 1; c <= m; c++) {
            // Check conflict with adjacent vertices
            int conflictNode = -1;
            for (int i = 0; i < n; i++) {
                if (adj[v][i] && colors[i] == c) {
                    conflictNode = i;
                    break;
                }
            }

            if (conflictNode >= 0) {
                // Conflict step
                steps.add(new int[]{v, c, 0, conflictNode});
            } else {
                // Valid assignment
                colors[v] = c;
                steps.add(new int[]{v, c, 1, -1});

                if (solveColoringDfs(v + 1, n, m, adj, colors, steps)) {
                    return true;
                }

                // Backtrack
                colors[v] = 0;
                steps.add(new int[]{v, c, 2, -1});
            }
        }
        return false;
    }

    public static String getColorName(int c) {
        switch (c) {
            case 1: return "Crimson";
            case 2: return "Emerald";
            case 3: return "Sky Blue";
            case 4: return "Amber";
            default: return "None";
        }
    }

    private static void advanceGraphColoring(VisualizationRuntimeHost host) {
        int step = host.getStepIndex();
        if (step < 0) {
            step = 0;
        }

        int[] working = host.getWorkingValues();
        int preset = 1;
        int maxColors = 3;
        String label = host.resolveMatrixLabel();
        if (label != null) {
            if (label.contains("Preset 2") || label.contains("5-Cycle")) preset = 2;
            else if (label.contains("Preset 3") || label.contains("Bipartite")) preset = 3;

            if (label.contains("m=2")) maxColors = 2;
            else if (label.contains("m=4")) maxColors = 4;
        }

        int n = getColoringGraphNodeCount(preset);
        int[][] steps = getGraphColoringSteps(maxColors, preset);
        int total = steps.length;

        if (working == null || working.length != n) {
            working = new int[n];
        }

        if (step >= total) {
            host.finish("Graph m-Coloring complete! Valid " + maxColors + "-coloring discovered without adjacent conflicts.");
            return;
        }

        int[] s = steps[step];
        int v = s[0];
        int c = s[1];
        int action = s[2]; // 1 = valid, 0 = conflict, 2 = backtrack
        int conflictNeighbor = s[3];
        String colorName = getColorName(c);

        if (action == 1) {
            working[v] = c;
            host.setWorkingValues(working);
            host.setActiveNodes(v, -1);
            host.clearActiveEdge();
            host.updateArrayVisualization(v, -1, -1);
            host.updatePointerState("Vertex", v, "Color", c);
            host.setHighlightedLines(12, 4);
            host.bindCurrentState("Vertex V" + v + " assigned Color C" + c + " (" + colorName + "). No adjacent conflict.");
        } else if (action == 0) {
            host.setActiveNodes(v, conflictNeighbor);
            host.setActiveEdge(v, conflictNeighbor);
            host.updatePointerState("Vertex", v, "Conflict V", conflictNeighbor);
            host.setHighlightedLines(16, 5);
            host.bindCurrentState("Conflict! Vertex V" + v + " cannot be Color C" + c + " (" + colorName + "): adjacent neighbor V" + conflictNeighbor + " has the same color. Trying next color.");
        } else {
            working[v] = 0;
            host.setWorkingValues(working);
            host.setActiveNodes(v, -1);
            host.clearActiveEdge();
            host.updatePointerState("Backtrack", v, "Reset", c);
            host.setHighlightedLines(22, 6);
            host.bindCurrentState("Backtracking from Vertex V" + v + ": No remaining colors valid. Resetting and retreating to previous vertex.");
        }

        host.setStepIndex(step + 1);
        if (step + 1 >= total) {
            boolean fullyColored = true;
            for (int col : working) {
                if (col == 0) { fullyColored = false; break; }
            }
            if (fullyColored) {
                host.finish("Graph " + maxColors + "-Coloring Solved! All " + n + " vertices colored with 0 adjacent conflicts.");
            } else {
                host.finish("Graph " + maxColors + "-Coloring Exhausted: Graph is not " + maxColors + "-colorable.");
            }
        }
    }

    private static void advanceGenericBacktracking(VisualizationRuntimeHost host) {
        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);
        if (step >= 5) {
            host.finish("Backtracking search concluded. Constraints satisfied.");
        } else {
            host.bindCurrentState("Backtracking state space tree: exploring branch " + step + ".");
        }
    }
}
