package com.dsavisualizer.app.data.provider;

import com.dsavisualizer.app.model.CategoryItem;
import com.dsavisualizer.app.model.TechniqueContent;
import com.dsavisualizer.app.model.TechniqueItem;

import java.util.List;
import java.util.Map;

/**
 * Curated provider for all Backtracking algorithms with complete
 * Iterative, Functions, and Recursion algorithms and code implementations
 * across Java, C, and Python.
 */
public final class BacktrackingProvider {

    private BacktrackingProvider() {}

    public static void register(
        List<CategoryItem> categories,
        List<TechniqueItem> techniques,
        Map<String, TechniqueContent> contentMap
    ) {
        categories.add(new CategoryItem(
            "backtracking",
            "Backtracking",
            "Constraint satisfaction, recursive search, and pruning state trees",
            "#EC4899"
        ));

        // 1. N-Queens
        techniques.add(new TechniqueItem(
            "backtracking_n_queens",
            "backtracking",
            "N-Queens Problem",
            "Place N non-attacking queens on NxN chessboard • Conflict Pruning",
            "Hard"
        ));
        // 2. Sudoku Solver
        techniques.add(new TechniqueItem(
            "backtracking_sudoku",
            "backtracking",
            "Sudoku Solver",
            "Fill 9x9 grid with digits 1-9 satisfying row, col, and 3x3 box",
            "Hard"
        ));
        // 3. Knight's Tour
        techniques.add(new TechniqueItem(
            "backtracking_knights_tour",
            "backtracking",
            "Knight's Tour Problem",
            "Chess knight visiting all 64 chessboard squares exactly once",
            "Hard"
        ));
        // 4. Maze Solver (Rat in a Maze)
        techniques.add(new TechniqueItem(
            "backtracking_maze_solver",
            "backtracking",
            "Maze Solver (Rat in a Maze)",
            "Find valid path through 2D obstacle grid from start to exit",
            "Medium"
        ));
        // 5. Hamiltonian Path & Cycle
        techniques.add(new TechniqueItem(
            "backtracking_hamiltonian",
            "backtracking",
            "Hamiltonian Path & Cycle",
            "Visit every vertex of a graph exactly once without repeating",
            "Hard"
        ));
        // 6. Graph m-Coloring
        techniques.add(new TechniqueItem(
            "backtracking_graph_coloring",
            "backtracking",
            "Graph m-Coloring Problem",
            "Assign colors to vertices so no adjacent vertices share colors",
            "Medium"
        ));

        registerNQueens(contentMap);
        registerSudoku(contentMap);
        registerKnightsTour(contentMap);
        registerMazeSolver(contentMap);
        registerHamiltonian(contentMap);
        registerGraphColoring(contentMap);
    }

    private static void registerNQueens(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "backtracking_n_queens",
            "N-Queens Problem",
            "Place N queens on an N×N chessboard so that no two queens attack each other.\n\n"
                + "A queen attacks any square in the same row, column, or diagonal. We place queens row by row, checking for safety at each column, and backtrack when a row has no valid safe placements.",
            "Radio tower frequency planning where neighboring transmitters cannot cause mutual interference.",
            "O(N!)", "O(N!)", "O(N!)", "O(N)",
            new int[] {4, 4, 4, 4},
            4
        );

        // --- ALGORITHMS ---
        tc.algorithmByStyle.put("Iterative",
            "Algorithm: N_QUEENS_ITERATIVE(N)\n"
            + "1. Initialize an array cols of size N with -1 (cols[row] stores queen's column).\n"
            + "2. Set row = 0.\n"
            + "3. While row >= 0:\n"
            + "   a. Increment cols[row] by 1.\n"
            + "   b. While cols[row] < N and isConflict(row, cols[row]):\n"
            + "        Increment cols[row] by 1.\n"
            + "   c. If cols[row] < N (found safe column):\n"
            + "        If row == N - 1, return true (solution found!).\n"
            + "        Else advance to next row: row = row + 1, cols[row] = -1.\n"
            + "   d. Else (no safe column in this row):\n"
            + "        Backtrack: reset cols[row] = -1, row = row - 1.\n"
            + "4. Return false (no solution exists)."
        );
        tc.algorithmByStyle.put("Functions",
            "Algorithm: N_QUEENS_FUNCTIONAL(N)\n"
            + "FUNCTION IS_SAFE(board, row, col, N):\n"
            + "1. Check column: if board[i][col] == 1 for i < row, return false.\n"
            + "2. Check upper-left diagonal: if board[row-k][col-k] == 1, return false.\n"
            + "3. Check upper-right diagonal: if board[row-k][col+k] == 1, return false.\n"
            + "4. Return true.\n\n"
            + "FUNCTION SOLVE_N_QUEENS(N):\n"
            + "1. Allocate N x N grid board initialized to 0.\n"
            + "2. Call PLACE_QUEENS(board, 0, N).\n"
            + "3. Return board if successful, else report no solution."
        );
        tc.algorithmByStyle.put("Recursion",
            "Algorithm: N_QUEENS_RECURSIVE(board, row, N)\n"
            + "1. Base Case: If row == N, return true (all N queens placed).\n"
            + "2. For each column col from 0 to N - 1:\n"
            + "   a. If IS_SAFE(board, row, col, N) is true:\n"
            + "        Place queen: board[row][col] = 1.\n"
            + "        If N_QUEENS_RECURSIVE(board, row + 1, N) == true:\n"
            + "          return true (solution found down this branch).\n"
            + "        Backtrack: board[row][col] = 0.\n"
            + "3. Return false (no column worked for this row)."
        );

        // --- CODE: JAVA ---
        tc.putCode("Java", "Iterative",
            "// N-Queens - Iterative (Stack / State Simulation)\n"
            + "public class NQueensIterative {\n"
            + "    public static boolean solveNQueens(int n) {\n"
            + "        int[] cols = new int[n];\n"
            + "        java.util.Arrays.fill(cols, -1);\n"
            + "        int row = 0;\n"
            + "        while (row >= 0) {\n"
            + "            cols[row]++;\n"
            + "            while (cols[row] < n && !isSafe(cols, row)) {\n"
            + "                cols[row]++;\n"
            + "            }\n"
            + "            if (cols[row] < n) {\n"
            + "                if (row == n - 1) return true; // All queens placed\n"
            + "                row++;\n"
            + "                cols[row] = -1;\n"
            + "            } else {\n"
            + "                cols[row] = -1;\n"
            + "                row--; // Backtrack to previous row\n"
            + "            }\n"
            + "        }\n"
            + "        return false;\n"
            + "    }\n"
            + "    private static boolean isSafe(int[] cols, int row) {\n"
            + "        for (int i = 0; i < row; i++) {\n"
            + "            if (cols[i] == cols[row] || Math.abs(cols[i] - cols[row]) == row - i)\n"
            + "                return false;\n"
            + "        }\n"
            + "        return true;\n"
            + "    }\n"
            + "}", true);

        tc.putCode("Java", "Functions",
            "// N-Queens - Functional Modular Formulation\n"
            + "public class NQueensFunctional {\n"
            + "    public static boolean isSafe(int[][] board, int row, int col, int n) {\n"
            + "        for (int i = 0; i < row; i++) if (board[i][col] == 1) return false;\n"
            + "        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) if (board[i][j] == 1) return false;\n"
            + "        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) if (board[i][j] == 1) return false;\n"
            + "        return true;\n"
            + "    }\n"
            + "    public static boolean solve(int[][] board, int row, int n) {\n"
            + "        if (row == n) return true;\n"
            + "        for (int col = 0; col < n; col++) {\n"
            + "            if (isSafe(board, row, col, n)) {\n"
            + "                board[row][col] = 1;\n"
            + "                if (solve(board, row + 1, n)) return true;\n"
            + "                board[row][col] = 0; // Backtrack\n"
            + "            }\n"
            + "        }\n"
            + "        return false;\n"
            + "    }\n"
            + "}", true);

        tc.putCode("Java", "Recursion",
            "// N-Queens - Classic Recursive Backtracking\n"
            + "public class NQueensRecursive {\n"
            + "    public static boolean solveNQueens(int n) {\n"
            + "        int[][] board = new int[n][n];\n"
            + "        return placeQueen(board, 0, n);\n"
            + "    }\n"
            + "    private static boolean placeQueen(int[][] board, int r, int n) {\n"
            + "        if (r == n) return true;\n"
            + "        for (int c = 0; c < n; c++) {\n"
            + "            if (canPlace(board, r, c, n)) {\n"
            + "                board[r][c] = 1;\n"
            + "                if (placeQueen(board, r + 1, n)) return true;\n"
            + "                board[r][c] = 0; // Undo placement\n"
            + "            }\n"
            + "        }\n"
            + "        return false;\n"
            + "    }\n"
            + "    private static boolean canPlace(int[][] b, int r, int c, int n) {\n"
            + "        for (int i = 0; i < r; i++) if (b[i][c] == 1) return false;\n"
            + "        for (int i = r - 1, j = c - 1; i >= 0 && j >= 0; i--, j--) if (b[i][j] == 1) return false;\n"
            + "        for (int i = r - 1, j = c + 1; i >= 0 && j < n; i--, j++) if (b[i][j] == 1) return false;\n"
            + "        return true;\n"
            + "    }\n"
            + "}", true);

        // --- CODE: C ---
        tc.putCode("C", "Iterative",
            "/* N-Queens - Iterative (C) */\n"
            + "#include <stdio.h>\n"
            + "#include <stdlib.h>\n"
            + "#include <stdbool.h>\n\n"
            + "bool isSafe(int cols[], int row) {\n"
            + "    for (int i = 0; i < row; i++) {\n"
            + "        if (cols[i] == cols[row] || abs(cols[i] - cols[row]) == row - i)\n"
            + "            return false;\n"
            + "    }\n"
            + "    return true;\n"
            + "}\n\n"
            + "bool solveNQueens(int n) {\n"
            + "    int cols[n];\n"
            + "    for (int i = 0; i < n; i++) cols[i] = -1;\n"
            + "    int row = 0;\n"
            + "    while (row >= 0) {\n"
            + "        cols[row]++;\n"
            + "        while (cols[row] < n && !isSafe(cols, row)) cols[row]++;\n"
            + "        if (cols[row] < n) {\n"
            + "            if (row == n - 1) return true;\n"
            + "            row++;\n"
            + "            cols[row] = -1;\n"
            + "        } else {\n"
            + "            cols[row] = -1;\n"
            + "            row--; // Backtrack\n"
            + "        }\n"
            + "    }\n"
            + "    return false;\n"
            + "}\n", true);

        tc.putCode("C", "Functions",
            "/* N-Queens - Modular Functions (C) */\n"
            + "#include <stdio.h>\n"
            + "#include <stdbool.h>\n\n"
            + "bool isSafe(int board[][8], int row, int col, int n) {\n"
            + "    for (int i = 0; i < row; i++) if (board[i][col]) return false;\n"
            + "    for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) if (board[i][j]) return false;\n"
            + "    for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) if (board[i][j]) return false;\n"
            + "    return true;\n"
            + "}\n\n"
            + "bool solve(int board[][8], int row, int n) {\n"
            + "    if (row == n) return true;\n"
            + "    for (int col = 0; col < n; col++) {\n"
            + "        if (isSafe(board, row, col, n)) {\n"
            + "            board[row][col] = 1;\n"
            + "            if (solve(board, row + 1, n)) return true;\n"
            + "            board[row][col] = 0; // Backtrack\n"
            + "        }\n"
            + "    }\n"
            + "    return false;\n"
            + "}\n", true);

        tc.putCode("C", "Recursion",
            "/* N-Queens - Classic Recursive (C) */\n"
            + "#include <stdio.h>\n"
            + "#include <stdbool.h>\n\n"
            + "bool canPlace(int b[][8], int r, int c, int n) {\n"
            + "    for (int i = 0; i < r; i++) if (b[i][c]) return false;\n"
            + "    for (int i = r - 1, j = c - 1; i >= 0 && j >= 0; i--, j--) if (b[i][j]) return false;\n"
            + "    for (int i = r - 1, j = c + 1; i >= 0 && j < n; i--, j++) if (b[i][j]) return false;\n"
            + "    return true;\n"
            + "}\n\n"
            + "bool placeQueen(int b[][8], int r, int n) {\n"
            + "    if (r == n) return true;\n"
            + "    for (int c = 0; c < n; c++) {\n"
            + "        if (canPlace(b, r, c, n)) {\n"
            + "            b[r][c] = 1;\n"
            + "            if (placeQueen(b, r + 1, n)) return true;\n"
            + "            b[r][c] = 0; // Backtrack\n"
            + "        }\n"
            + "    }\n"
            + "    return false;\n"
            + "}\n", true);

        // --- CODE: PYTHON ---
        tc.putCode("Python", "Iterative",
            "# N-Queens - Iterative Backtracking\n"
            + "def solve_n_queens_iterative(n):\n"
            + "    cols = [-1] * n\n"
            + "    row = 0\n"
            + "    while row >= 0:\n"
            + "        cols[row] += 1\n"
            + "        while cols[row] < n and not is_safe(cols, row):\n"
            + "            cols[row] += 1\n"
            + "        if cols[row] < n:\n"
            + "            if row == n - 1:\n"
            + "                return True  # Found solution\n"
            + "            row += 1\n"
            + "            cols[row] = -1\n"
            + "        else:\n"
            + "            cols[row] = -1\n"
            + "            row -= 1  # Backtrack\n"
            + "    return False\n\n"
            + "def is_safe(cols, row):\n"
            + "    for i in range(row):\n"
            + "        if cols[i] == cols[row] or abs(cols[i] - cols[row]) == row - i:\n"
            + "            return False\n"
            + "    return True\n", true);

        tc.putCode("Python", "Functions",
            "# N-Queens - Functional Modular Design\n"
            + "def is_safe(board, row, col, n):\n"
            + "    for i in range(row):\n"
            + "        if board[i][col] == 1: return False\n"
            + "    for i, j in zip(range(row - 1, -1, -1), range(col - 1, -1, -1)):\n"
            + "        if board[i][j] == 1: return False\n"
            + "    for i, j in zip(range(row - 1, -1, -1), range(col + 1, n)):\n"
            + "        if board[i][j] == 1: return False\n"
            + "    return True\n\n"
            + "def solve(board, row, n):\n"
            + "    if row == n: return True\n"
            + "    for col in range(n):\n"
            + "        if is_safe(board, row, col, n):\n"
            + "            board[row][col] = 1\n"
            + "            if solve(board, row + 1, n): return True\n"
            + "            board[row][col] = 0  # Backtrack\n"
            + "    return False\n", true);

        tc.putCode("Python", "Recursion",
            "# N-Queens - Classic Recursive DFS\n"
            + "def solve_n_queens(n):\n"
            + "    board = [[0] * n for _ in range(n)]\n"
            + "    def place(r):\n"
            + "        if r == n: return True\n"
            + "        for c in range(n):\n"
            + "            if can_place(r, c):\n"
            + "                board[r][c] = 1\n"
            + "                if place(r + 1): return True\n"
            + "                board[r][c] = 0  # Backtrack\n"
            + "        return False\n"
            + "    def can_place(r, c):\n"
            + "        for i in range(r):\n"
            + "            if board[i][c] == 1: return False\n"
            + "        for i, j in zip(range(r - 1, -1, -1), range(c - 1, -1, -1)):\n"
            + "            if board[i][j] == 1: return False\n"
            + "        for i, j in zip(range(r - 1, -1, -1), range(c + 1, n)):\n"
            + "            if board[i][j] == 1: return False\n"
            + "        return True\n"
            + "    return solve_n_queens(n)\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerSudoku(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "backtracking_sudoku",
            "Sudoku Solver",
            "Solve a partially filled 9×9 Sudoku grid by filling empty cells with digits from 1 to 9.\n\n"
                + "Each digit 1–9 must occur exactly once in each row, each column, and each of the nine 3×3 subgrids. When a cell cannot take any digit without violation, we backtrack to the previous cell.",
            "Scheduling exam time slots where students, professors, and classrooms have non-overlapping constraints.",
            "O(9^(N*N))", "O(9^(N*N))", "O(9^(N*N))", "O(N*N)",
            new int[] {5, 3, 0, 0, 7, 0, 0, 0, 0},
            9
        );

        tc.algorithmByStyle.put("Iterative",
            "Algorithm: SUDOKU_ITERATIVE(grid)\n"
            + "1. Collect all empty cells into list emptyCells.\n"
            + "2. Set index = 0.\n"
            + "3. While index >= 0 and index < emptyCells.size():\n"
            + "   a. cell = emptyCells[index], get current value d = grid[cell.r][cell.c].\n"
            + "   b. Try next digits d = d + 1 up to 9:\n"
            + "        If isValid(grid, cell.r, cell.c, d): set grid[cell.r][cell.c] = d, break.\n"
            + "   c. If valid digit found: index = index + 1.\n"
            + "   d. Else: reset grid[cell.r][cell.c] = 0, index = index - 1 (backtrack).\n"
            + "4. Return index == emptyCells.size()."
        );
        tc.algorithmByStyle.put("Functions",
            "Algorithm: SUDOKU_FUNCTIONAL(grid)\n"
            + "FUNCTION IS_VALID(grid, row, col, num):\n"
            + "1. Check row and col for duplicate num.\n"
            + "2. Check 3x3 box (row - row%3, col - col%3) for duplicate num.\n"
            + "3. Return true if no duplicates.\n\n"
            + "FUNCTION SOLVE_SUDOKU(grid):\n"
            + "1. Locate next unassigned cell.\n"
            + "2. If none, return true.\n"
            + "3. Try candidate digits 1..9 with IS_VALID.\n"
            + "4. Return outcome."
        );
        tc.algorithmByStyle.put("Recursion",
            "Algorithm: SUDOKU_RECURSIVE(grid)\n"
            + "1. Find first unassigned cell (r, c). If none remain, return true.\n"
            + "2. For num = 1 to 9:\n"
            + "   a. If IS_VALID(grid, r, c, num):\n"
            + "        grid[r][c] = num\n"
            + "        If SUDOKU_RECURSIVE(grid) == true: return true\n"
            + "        grid[r][c] = 0 (backtrack)\n"
            + "3. Return false."
        );

        tc.putCode("Java", "Iterative",
            "// Sudoku Solver - Iterative using Empty Cell Stack\n"
            + "public class SudokuIterative {\n"
            + "    public static boolean solveSudoku(int[][] grid) {\n"
            + "        java.util.List<int[]> empty = new java.util.ArrayList<>();\n"
            + "        for (int r = 0; r < 9; r++)\n"
            + "            for (int c = 0; c < 9; c++)\n"
            + "                if (grid[r][c] == 0) empty.add(new int[]{r, c});\n"
            + "        int idx = 0;\n"
            + "        while (idx >= 0 && idx < empty.size()) {\n"
            + "            int[] cell = empty.get(idx);\n"
            + "            int r = cell[0], c = cell[1];\n"
            + "            int num = grid[r][c] + 1;\n"
            + "            while (num <= 9 && !isValid(grid, r, c, num)) num++;\n"
            + "            if (num <= 9) {\n"
            + "                grid[r][c] = num;\n"
            + "                idx++;\n"
            + "            } else {\n"
            + "                grid[r][c] = 0;\n"
            + "                idx--; // Backtrack\n"
            + "            }\n"
            + "        }\n"
            + "        return idx == empty.size();\n"
            + "    }\n"
            + "    private static boolean isValid(int[][] g, int r, int c, int n) {\n"
            + "        for (int i = 0; i < 9; i++) if (g[r][i] == n || g[i][c] == n) return false;\n"
            + "        int br = r - r % 3, bc = c - c % 3;\n"
            + "        for (int i = 0; i < 3; i++)\n"
            + "            for (int j = 0; j < 3; j++)\n"
            + "                if (g[br + i][bc + j] == n) return false;\n"
            + "        return true;\n"
            + "    }\n"
            + "}", true);

        tc.putCode("Java", "Functions",
            "// Sudoku Solver - Modular Functions\n"
            + "public class SudokuFunctional {\n"
            + "    public static boolean isValid(int[][] g, int r, int c, int num) {\n"
            + "        for (int i = 0; i < 9; i++) if (g[r][i] == num || g[i][c] == num) return false;\n"
            + "        int br = r - r % 3, bc = c - c % 3;\n"
            + "        for (int i = 0; i < 3; i++)\n"
            + "            for (int j = 0; j < 3; j++)\n"
            + "                if (g[br + i][bc + j] == num) return false;\n"
            + "        return true;\n"
            + "    }\n"
            + "    public static boolean solve(int[][] g) {\n"
            + "        for (int r = 0; r < 9; r++) {\n"
            + "            for (int c = 0; c < 9; c++) {\n"
            + "                if (g[r][c] == 0) {\n"
            + "                    for (int num = 1; num <= 9; num++) {\n"
            + "                        if (isValid(g, r, c, num)) {\n"
            + "                            g[r][c] = num;\n"
            + "                            if (solve(g)) return true;\n"
            + "                            g[r][c] = 0; // Backtrack\n"
            + "                        }\n"
            + "                    }\n"
            + "                    return false;\n"
            + "                }\n"
            + "            }\n"
            + "        }\n"
            + "        return true;\n"
            + "    }\n"
            + "}", true);

        tc.putCode("Java", "Recursion",
            "// Sudoku Solver - Classic Recursive Backtracking\n"
            + "public class SudokuRecursive {\n"
            + "    public static boolean solveSudoku(int[][] grid) {\n"
            + "        return backtrack(grid, 0, 0);\n"
            + "    }\n"
            + "    private static boolean backtrack(int[][] g, int r, int c) {\n"
            + "        if (r == 9) return true;\n"
            + "        if (c == 9) return backtrack(g, r + 1, 0);\n"
            + "        if (g[r][c] != 0) return backtrack(g, r, c + 1);\n"
            + "        for (int num = 1; num <= 9; num++) {\n"
            + "            if (isSafe(g, r, c, num)) {\n"
            + "                g[r][c] = num;\n"
            + "                if (backtrack(g, r, c + 1)) return true;\n"
            + "                g[r][c] = 0;\n"
            + "            }\n"
            + "        }\n"
            + "        return false;\n"
            + "    }\n"
            + "    private static boolean isSafe(int[][] g, int r, int c, int n) {\n"
            + "        for (int i = 0; i < 9; i++) if (g[r][i] == n || g[i][c] == n) return false;\n"
            + "        int br = r - r % 3, bc = c - c % 3;\n"
            + "        for (int i = 0; i < 3; i++)\n"
            + "            for (int j = 0; j < 3; j++)\n"
            + "                if (g[br + i][bc + j] == n) return false;\n"
            + "        return true;\n"
            + "    }\n"
            + "}", true);

        tc.putCode("C", "Iterative",
            "/* Sudoku Solver - Iterative (C) */\n"
            + "#include <stdio.h>\n"
            + "#include <stdbool.h>\n\n"
            + "bool isValid(int g[9][9], int r, int c, int n) {\n"
            + "    for (int i = 0; i < 9; i++) if (g[r][i] == n || g[i][c] == n) return false;\n"
            + "    int br = r - r % 3, bc = c - c % 3;\n"
            + "    for (int i = 0; i < 3; i++)\n"
            + "        for (int j = 0; j < 3; j++)\n"
            + "            if (g[br + i][bc + j] == n) return false;\n"
            + "    return true;\n"
            + "}\n\n"
            + "bool solveSudoku(int g[9][9]) {\n"
            + "    int emptyR[81], emptyC[81], count = 0;\n"
            + "    for (int r = 0; r < 9; r++)\n"
            + "        for (int c = 0; c < 9; c++)\n"
            + "            if (g[r][c] == 0) { emptyR[count] = r; emptyC[count++] = c; }\n"
            + "    int idx = 0;\n"
            + "    while (idx >= 0 && idx < count) {\n"
            + "        int r = emptyR[idx], c = emptyC[idx];\n"
            + "        int num = g[r][c] + 1;\n"
            + "        while (num <= 9 && !isValid(g, r, c, num)) num++;\n"
            + "        if (num <= 9) {\n"
            + "            g[r][c] = num;\n"
            + "            idx++;\n"
            + "        } else {\n"
            + "            g[r][c] = 0;\n"
            + "            idx--; // Backtrack\n"
            + "        }\n"
            + "    }\n"
            + "    return idx == count;\n"
            + "}\n", true);

        tc.putCode("C", "Functions",
            "/* Sudoku Solver - Functions (C) */\n"
            + "#include <stdio.h>\n"
            + "#include <stdbool.h>\n\n"
            + "bool isValid(int g[9][9], int r, int c, int num) {\n"
            + "    for (int i = 0; i < 9; i++) if (g[r][i] == num || g[i][c] == num) return false;\n"
            + "    int br = r - r % 3, bc = c - c % 3;\n"
            + "    for (int i = 0; i < 3; i++)\n"
            + "        for (int j = 0; j < 3; j++)\n"
            + "            if (g[br + i][bc + j] == num) return false;\n"
            + "    return true;\n"
            + "}\n\n"
            + "bool solve(int g[9][9]) {\n"
            + "    for (int r = 0; r < 9; r++) {\n"
            + "        for (int c = 0; c < 9; c++) {\n"
            + "            if (g[r][c] == 0) {\n"
            + "                for (int num = 1; num <= 9; num++) {\n"
            + "                    if (isValid(g, r, c, num)) {\n"
            + "                        g[r][c] = num;\n"
            + "                        if (solve(g)) return true;\n"
            + "                        g[r][c] = 0;\n"
            + "                    }\n"
            + "                }\n"
            + "                return false;\n"
            + "            }\n"
            + "        }\n"
            + "    }\n"
            + "    return true;\n"
            + "}\n", true);

        tc.putCode("C", "Recursion",
            "/* Sudoku Solver - Recursive (C) */\n"
            + "#include <stdio.h>\n"
            + "#include <stdbool.h>\n\n"
            + "bool isSafe(int g[9][9], int r, int c, int n) {\n"
            + "    for (int i = 0; i < 9; i++) if (g[r][i] == n || g[i][c] == n) return false;\n"
            + "    int br = r - r % 3, bc = c - c % 3;\n"
            + "    for (int i = 0; i < 3; i++)\n"
            + "        for (int j = 0; j < 3; j++)\n"
            + "            if (g[br + i][bc + j] == n) return false;\n"
            + "    return true;\n"
            + "}\n\n"
            + "bool backtrack(int g[9][9], int r, int c) {\n"
            + "    if (r == 9) return true;\n"
            + "    if (c == 9) return backtrack(g, r + 1, 0);\n"
            + "    if (g[r][c] != 0) return backtrack(g, r, c + 1);\n"
            + "    for (int num = 1; num <= 9; num++) {\n"
            + "        if (isSafe(g, r, c, num)) {\n"
            + "            g[r][c] = num;\n"
            + "            if (backtrack(g, r, c + 1)) return true;\n"
            + "            g[r][c] = 0; // Backtrack\n"
            + "        }\n"
            + "    }\n"
            + "    return false;\n"
            + "}\n", true);

        tc.putCode("Python", "Iterative",
            "# Sudoku Solver - Iterative with Stack\n"
            + "def solve_sudoku_iterative(grid):\n"
            + "    empty = [(r, c) for r in range(9) for c in range(9) if grid[r][c] == 0]\n"
            + "    idx = 0\n"
            + "    while 0 <= idx < len(empty):\n"
            + "        r, c = empty[idx]\n"
            + "        num = grid[r][c] + 1\n"
            + "        while num <= 9 and not is_valid(grid, r, c, num):\n"
            + "            num += 1\n"
            + "        if num <= 9:\n"
            + "            grid[r][c] = num\n"
            + "            idx += 1\n"
            + "        else:\n"
            + "            grid[r][c] = 0\n"
            + "            idx -= 1  # Backtrack\n"
            + "    return idx == len(empty)\n\n"
            + "def is_valid(g, r, c, num):\n"
            + "    for i in range(9):\n"
            + "        if g[r][i] == num or g[i][c] == num: return False\n"
            + "    br, bc = r - r % 3, c - c % 3\n"
            + "    for i in range(3):\n"
            + "        for j in range(3):\n"
            + "            if g[br + i][bc + j] == num: return False\n"
            + "    return True\n", true);

        tc.putCode("Python", "Functions",
            "# Sudoku Solver - Modular Functions\n"
            + "def solve_sudoku(grid):\n"
            + "    for r in range(9):\n"
            + "        for c in range(9):\n"
            + "            if grid[r][c] == 0:\n"
            + "                for num in range(1, 10):\n"
            + "                    if is_valid(grid, r, c, num):\n"
            + "                        grid[r][c] = num\n"
            + "                        if solve_sudoku(grid): return True\n"
            + "                        grid[r][c] = 0  # Backtrack\n"
            + "                return False\n"
            + "    return True\n", true);

        tc.putCode("Python", "Recursion",
            "# Sudoku Solver - Recursive Backtracking\n"
            + "def solve_sudoku_recursive(grid):\n"
            + "    def backtrack(r, c):\n"
            + "        if r == 9: return True\n"
            + "        if c == 9: return backtrack(r + 1, 0)\n"
            + "        if grid[r][c] != 0: return backtrack(r, c + 1)\n"
            + "        for num in range(1, 10):\n"
            + "            if is_valid(grid, r, c, num):\n"
            + "                grid[r][c] = num\n"
            + "                if backtrack(r, c + 1): return True\n"
            + "                grid[r][c] = 0  # Backtrack\n"
            + "        return False\n"
            + "    return backtrack(0, 0)\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerKnightsTour(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "backtracking_knights_tour",
            "Knight's Tour Problem",
            "A knight is placed on the empty board and, moving according to the rules of chess, must visit each square exactly once.\n\n"
                + "A knight has up to 8 possible L-shaped moves (2 squares in one direction, 1 in orthogonal direction). If all valid moves lead to dead ends, the knight retracts its step.",
            "Circuit board drill path optimization and warehouse inventory robot navigation.",
            "O(8^(N^2))", "O(8^(N^2))", "O(8^(N^2))", "O(N^2)",
            new int[] {8, 8},
            64
        );

        tc.algorithmByStyle.put("Iterative",
            "Algorithm: KNIGHTS_TOUR_ITERATIVE(N)\n"
            + "1. Initialize board of size N x N with -1.\n"
            + "2. Maintain stack of (x, y, nextMoveIndex).\n"
            + "3. Push start position (0, 0, 0) and set board[0][0] = 0.\n"
            + "4. While stack is not empty and stack.size() < N*N:\n"
            + "   a. Current state = top of stack.\n"
            + "   b. Find next valid move from current state among 8 offsets.\n"
            + "   c. If valid move (nx, ny) found:\n"
            + "        board[nx][ny] = stack.size()\n"
            + "        Push (nx, ny, 0) onto stack.\n"
            + "   d. Else (dead end reached):\n"
            + "        board[top.x][top.y] = -1\n"
            + "        Pop top and increment nextMoveIndex of new top (backtrack).\n"
            + "5. Return stack.size() == N*N."
        );
        tc.algorithmByStyle.put("Functions",
            "Algorithm: KNIGHTS_TOUR_FUNCTIONAL(N)\n"
            + "FUNCTION IS_SAFE(x, y, board, N):\n"
            + "1. Return (0 <= x < N and 0 <= y < N and board[x][y] == -1).\n\n"
            + "FUNCTION SOLVE_KNIGHTS_TOUR(N):\n"
            + "1. Initialize board N x N with -1. Place knight at (0, 0) = 0.\n"
            + "2. Define offsets dx = [2, 1, -1, -2, -2, -1, 1, 2], dy = [1, 2, 2, 1, -1, -2, -2, -1].\n"
            + "3. Call EXPLORE(0, 0, 1, board, dx, dy, N).\n"
            + "4. Return board."
        );
        tc.algorithmByStyle.put("Recursion",
            "Algorithm: KNIGHTS_TOUR_RECURSIVE(x, y, moveCount, board, N)\n"
            + "1. Base Case: If moveCount == N * N, return true (tour complete!).\n"
            + "2. For k = 0 to 7:\n"
            + "   a. nextX = x + dx[k], nextY = y + dy[k].\n"
            + "   b. If IS_SAFE(nextX, nextY, board, N):\n"
            + "        board[nextX][nextY] = moveCount\n"
            + "        If KNIGHTS_TOUR_RECURSIVE(nextX, nextY, moveCount + 1, board, N) == true:\n"
            + "          return true\n"
            + "        board[nextX][nextY] = -1 (backtrack)\n"
            + "3. Return false."
        );

        tc.putCode("Java", "Iterative",
            "// Knight's Tour - Iterative (Move Stack)\n"
            + "public class KnightsTourIterative {\n"
            + "    public static boolean solveKnightsTour(int n) {\n"
            + "        int[][] board = new int[n][n];\n"
            + "        for (int[] row : board) java.util.Arrays.fill(row, -1);\n"
            + "        int[] dx = {2, 1, -1, -2, -2, -1, 1, 2};\n"
            + "        int[] dy = {1, 2, 2, 1, -1, -2, -2, -1};\n"
            + "        int[] moveIdx = new int[n * n];\n"
            + "        int[] posX = new int[n * n];\n"
            + "        int[] posY = new int[n * n];\n"
            + "        int step = 0;\n"
            + "        posX[0] = 0; posY[0] = 0; moveIdx[0] = 0; board[0][0] = 0;\n"
            + "        while (step >= 0 && step < n * n - 1) {\n"
            + "            int x = posX[step], y = posY[step];\n"
            + "            boolean found = false;\n"
            + "            for (int k = moveIdx[step]; k < 8; k++) {\n"
            + "                int nx = x + dx[k], ny = y + dy[k];\n"
            + "                if (nx >= 0 && nx < n && ny >= 0 && ny < n && board[nx][ny] == -1) {\n"
            + "                    moveIdx[step] = k + 1;\n"
            + "                    step++;\n"
            + "                    posX[step] = nx; posY[step] = ny; moveIdx[step] = 0;\n"
            + "                    board[nx][ny] = step;\n"
            + "                    found = true;\n"
            + "                    break;\n"
            + "                }\n"
            + "            }\n"
            + "            if (!found) {\n"
            + "                board[posX[step]][posY[step]] = -1;\n"
            + "                step--; // Backtrack\n"
            + "            }\n"
            + "        }\n"
            + "        return step == n * n - 1;\n"
            + "    }\n"
            + "}", true);

        tc.putCode("Java", "Functions",
            "// Knight's Tour - Functions\n"
            + "public class KnightsTourFunctional {\n"
            + "    static final int[] DX = {2, 1, -1, -2, -2, -1, 1, 2};\n"
            + "    static final int[] DY = {1, 2, 2, 1, -1, -2, -2, -1};\n"
            + "    public static boolean isSafe(int x, int y, int[][] board, int n) {\n"
            + "        return x >= 0 && x < n && y >= 0 && y < n && board[x][y] == -1;\n"
            + "    }\n"
            + "    public static boolean solve(int x, int y, int count, int[][] board, int n) {\n"
            + "        if (count == n * n) return true;\n"
            + "        for (int k = 0; k < 8; k++) {\n"
            + "            int nx = x + DX[k], ny = y + DY[k];\n"
            + "            if (isSafe(nx, ny, board, n)) {\n"
            + "                board[nx][ny] = count;\n"
            + "                if (solve(nx, ny, count + 1, board, n)) return true;\n"
            + "                board[nx][ny] = -1; // Backtrack\n"
            + "            }\n"
            + "        }\n"
            + "        return false;\n"
            + "    }\n"
            + "}", true);

        tc.putCode("Java", "Recursion",
            "// Knight's Tour - Classic Recursive Backtracking\n"
            + "public class KnightsTourRecursive {\n"
            + "    public static boolean solveKnightsTour(int n) {\n"
            + "        int[][] board = new int[n][n];\n"
            + "        for (int[] row : board) java.util.Arrays.fill(row, -1);\n"
            + "        board[0][0] = 0;\n"
            + "        return solve(0, 0, 1, board, n);\n"
            + "    }\n"
            + "    private static boolean solve(int x, int y, int move, int[][] b, int n) {\n"
            + "        if (move == n * n) return true;\n"
            + "        int[] dx = {2, 1, -1, -2, -2, -1, 1, 2};\n"
            + "        int[] dy = {1, 2, 2, 1, -1, -2, -2, -1};\n"
            + "        for (int k = 0; k < 8; k++) {\n"
            + "            int nx = x + dx[k], ny = y + dy[k];\n"
            + "            if (nx >= 0 && nx < n && ny >= 0 && ny < n && b[nx][ny] == -1) {\n"
            + "                b[nx][ny] = move;\n"
            + "                if (solve(nx, ny, move + 1, b, n)) return true;\n"
            + "                b[nx][ny] = -1; // Backtrack\n"
            + "            }\n"
            + "        }\n"
            + "        return false;\n"
            + "    }\n"
            + "}", true);

        tc.putCode("C", "Iterative",
            "/* Knight's Tour - Iterative (C) */\n"
            + "#include <stdio.h>\n"
            + "#include <stdbool.h>\n\n"
            + "bool solveKnightsTour(int n) {\n"
            + "    int board[8][8];\n"
            + "    for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) board[i][j] = -1;\n"
            + "    int dx[] = {2, 1, -1, -2, -2, -1, 1, 2};\n"
            + "    int dy[] = {1, 2, 2, 1, -1, -2, -2, -1};\n"
            + "    int posX[64], posY[64], moveIdx[64], step = 0;\n"
            + "    posX[0] = 0; posY[0] = 0; moveIdx[0] = 0; board[0][0] = 0;\n"
            + "    while (step >= 0 && step < n * n - 1) {\n"
            + "        int x = posX[step], y = posY[step];\n"
            + "        bool found = false;\n"
            + "        for (int k = moveIdx[step]; k < 8; k++) {\n"
            + "            int nx = x + dx[k], ny = y + dy[k];\n"
            + "            if (nx >= 0 && nx < n && ny >= 0 && ny < n && board[nx][ny] == -1) {\n"
            + "                moveIdx[step] = k + 1;\n"
            + "                step++;\n"
            + "                posX[step] = nx; posY[step] = ny; moveIdx[step] = 0;\n"
            + "                board[nx][ny] = step;\n"
            + "                found = true;\n"
            + "                break;\n"
            + "            }\n"
            + "        }\n"
            + "        if (!found) { board[posX[step]][posY[step]] = -1; step--; }\n"
            + "    }\n"
            + "    return step == n * n - 1;\n"
            + "}\n", true);

        tc.putCode("C", "Functions",
            "/* Knight's Tour - Functions (C) */\n"
            + "#include <stdio.h>\n"
            + "#include <stdbool.h>\n\n"
            + "int dx[8] = {2, 1, -1, -2, -2, -1, 1, 2};\n"
            + "int dy[8] = {1, 2, 2, 1, -1, -2, -2, -1};\n\n"
            + "bool isSafe(int x, int y, int b[][8], int n) {\n"
            + "    return x >= 0 && x < n && y >= 0 && y < n && b[x][y] == -1;\n"
            + "}\n\n"
            + "bool solve(int x, int y, int count, int b[][8], int n) {\n"
            + "    if (count == n * n) return true;\n"
            + "    for (int k = 0; k < 8; k++) {\n"
            + "        int nx = x + dx[k], ny = y + dy[k];\n"
            + "        if (isSafe(nx, ny, b, n)) {\n"
            + "            b[nx][ny] = count;\n"
            + "            if (solve(nx, ny, count + 1, b, n)) return true;\n"
            + "            b[nx][ny] = -1;\n"
            + "        }\n"
            + "    }\n"
            + "    return false;\n"
            + "}\n", true);

        tc.putCode("C", "Recursion",
            "/* Knight's Tour - Recursive (C) */\n"
            + "#include <stdio.h>\n"
            + "#include <stdbool.h>\n\n"
            + "bool solveRecursive(int x, int y, int move, int b[][8], int n) {\n"
            + "    if (move == n * n) return true;\n"
            + "    int dx[] = {2, 1, -1, -2, -2, -1, 1, 2};\n"
            + "    int dy[] = {1, 2, 2, 1, -1, -2, -2, -1};\n"
            + "    for (int k = 0; k < 8; k++) {\n"
            + "        int nx = x + dx[k], ny = y + dy[k];\n"
            + "        if (nx >= 0 && nx < n && ny >= 0 && ny < n && b[nx][ny] == -1) {\n"
            + "            b[nx][ny] = move;\n"
            + "            if (solveRecursive(nx, ny, move + 1, b, n)) return true;\n"
            + "            b[nx][ny] = -1; // Backtrack\n"
            + "        }\n"
            + "    }\n"
            + "    return false;\n"
            + "}\n", true);

        tc.putCode("Python", "Iterative",
            "# Knight's Tour - Iterative (Python)\n"
            + "def knights_tour_iterative(n):\n"
            + "    board = [[-1] * n for _ in range(n)]\n"
            + "    dx = [2, 1, -1, -2, -2, -1, 1, 2]\n"
            + "    dy = [1, 2, 2, 1, -1, -2, -2, -1]\n"
            + "    posX, posY, moveIdx = [0]*(n*n), [0]*(n*n), [0]*(n*n)\n"
            + "    step = 0\n"
            + "    board[0][0] = 0\n"
            + "    while 0 <= step < n * n - 1:\n"
            + "        x, y = posX[step], posY[step]\n"
            + "        found = False\n"
            + "        for k in range(moveIdx[step], 8):\n"
            + "            nx, ny = x + dx[k], y + dy[k]\n"
            + "            if 0 <= nx < n and 0 <= ny < n and board[nx][ny] == -1:\n"
            + "                moveIdx[step] = k + 1\n"
            + "                step += 1\n"
            + "                posX[step], posY[step], moveIdx[step] = nx, ny, 0\n"
            + "                board[nx][ny] = step\n"
            + "                found = True\n"
            + "                break\n"
            + "        if not found:\n"
            + "            board[posX[step]][posY[step]] = -1\n"
            + "            step -= 1  # Backtrack\n"
            + "    return step == n * n - 1\n", true);

        tc.putCode("Python", "Functions",
            "# Knight's Tour - Functions (Python)\n"
            + "def is_safe(x, y, board, n):\n"
            + "    return 0 <= x < n and 0 <= y < n and board[x][y] == -1\n\n"
            + "def solve_tour(x, y, count, board, n, dx, dy):\n"
            + "    if count == n * n: return True\n"
            + "    for k in range(8):\n"
            + "        nx, ny = x + dx[k], y + dy[k]\n"
            + "        if is_safe(nx, ny, board, n):\n"
            + "            board[nx][ny] = count\n"
            + "            if solve_tour(nx, ny, count + 1, board, n, dx, dy): return True\n"
            + "            board[nx][ny] = -1  # Backtrack\n"
            + "    return False\n", true);

        tc.putCode("Python", "Recursion",
            "# Knight's Tour - Recursive (Python)\n"
            + "def knights_tour_recursive(n):\n"
            + "    board = [[-1] * n for _ in range(n)]\n"
            + "    board[0][0] = 0\n"
            + "    dx = [2, 1, -1, -2, -2, -1, 1, 2]\n"
            + "    dy = [1, 2, 2, 1, -1, -2, -2, -1]\n"
            + "    def solve(x, y, move):\n"
            + "        if move == n * n: return True\n"
            + "        for k in range(8):\n"
            + "            nx, ny = x + dx[k], y + dy[k]\n"
            + "            if 0 <= nx < n and 0 <= ny < n and board[nx][ny] == -1:\n"
            + "                board[nx][ny] = move\n"
            + "                if solve(nx, ny, move + 1): return True\n"
            + "                board[nx][ny] = -1  # Backtrack\n"
            + "        return False\n"
            + "    return solve(0, 0, 1)\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerMazeSolver(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "backtracking_maze_solver",
            "Maze Solver (Rat in a Maze)",
            "Given a 2D matrix where cells are either open (1) or blocked walls (0), find a route from the top-left (0,0) to the bottom-right (N-1, M-1).\n\n"
                + "We move forward through open cells (Up, Down, Left, Right). If a direction leads to a dead-end with no path to the exit, we backtrack and unmark the step.",
            "Automated vacuum cleaner mapping and robotic path planning through unknown floor plans.",
            "O(4^(N*M))", "O(4^(N*M))", "O(4^(N*M))", "O(N*M)",
            new int[] {1, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1},
            4
        );

        tc.algorithmByStyle.put("Iterative",
            "Algorithm: MAZE_SOLVER_ITERATIVE(maze, n)\n"
            + "1. Initialize solution grid sol of size n x n with 0.\n"
            + "2. Use a stack storing (r, c, directionIndex).\n"
            + "3. Push start (0, 0, 0) and mark sol[0][0] = 1.\n"
            + "4. While stack is not empty:\n"
            + "   a. Current = top of stack.\n"
            + "   b. If current == (n-1, n-1), path found, return true.\n"
            + "   c. Try directions (Down, Right, Up, Left) starting from directionIndex:\n"
            + "        If next cell (nr, nc) is valid open cell and sol[nr][nc] == 0:\n"
            + "          Update current's directionIndex = next + 1.\n"
            + "          Mark sol[nr][nc] = 1.\n"
            + "          Push (nr, nc, 0) onto stack.\n"
            + "          Break loop to move forward.\n"
            + "   d. If no direction valid:\n"
            + "        sol[current.r][current.c] = 0 (backtrack)\n"
            + "        Pop stack.\n"
            + "5. Return false."
        );
        tc.algorithmByStyle.put("Functions",
            "Algorithm: MAZE_SOLVER_FUNCTIONAL(maze, n)\n"
            + "FUNCTION IS_SAFE(maze, r, c, sol, n):\n"
            + "1. Return (0 <= r < n and 0 <= c < n and maze[r][c] == 1 and sol[r][c] == 0).\n\n"
            + "FUNCTION EXPLORE(maze, r, c, sol, n):\n"
            + "1. If (r == n-1 and c == n-1 and maze[r][c] == 1): sol[r][c] = 1, return true.\n"
            + "2. If IS_SAFE(maze, r, c, sol, n):\n"
            + "     sol[r][c] = 1.\n"
            + "     If EXPLORE(maze, r+1, c, sol, n) return true.\n"
            + "     If EXPLORE(maze, r, c+1, sol, n) return true.\n"
            + "     If EXPLORE(maze, r-1, c, sol, n) return true.\n"
            + "     If EXPLORE(maze, r, c-1, sol, n) return true.\n"
            + "     sol[r][c] = 0. (backtrack)\n"
            + "3. Return false."
        );
        tc.algorithmByStyle.put("Recursion",
            "Algorithm: MAZE_SOLVER_RECURSIVE(maze, r, c, sol, n)\n"
            + "1. Base Case: If r == n-1 and c == n-1: sol[r][c] = 1, return true.\n"
            + "2. Check if (r, c) is valid and open (maze[r][c] == 1 and sol[r][c] == 0):\n"
            + "   a. Mark sol[r][c] = 1.\n"
            + "   b. Recurse Down: if MAZE_SOLVER_RECURSIVE(maze, r+1, c, sol, n) == true: return true.\n"
            + "   c. Recurse Right: if MAZE_SOLVER_RECURSIVE(maze, r, c+1, sol, n) == true: return true.\n"
            + "   d. Recurse Up: if MAZE_SOLVER_RECURSIVE(maze, r-1, c, sol, n) == true: return true.\n"
            + "   e. Recurse Left: if MAZE_SOLVER_RECURSIVE(maze, r, c-1, sol, n) == true: return true.\n"
            + "   f. Backtrack: sol[r][c] = 0.\n"
            + "3. Return false."
        );

        tc.putCode("Java", "Iterative",
            "// Rat in a Maze - Iterative Backtracking\n"
            + "public class MazeSolverIterative {\n"
            + "    public static boolean solveMaze(int[][] maze, int n) {\n"
            + "        int[][] sol = new int[n][n];\n"
            + "        int[] dr = {1, 0, -1, 0};\n"
            + "        int[] dc = {0, 1, 0, -1};\n"
            + "        int[] stackR = new int[n * n];\n"
            + "        int[] stackC = new int[n * n];\n"
            + "        int[] dir = new int[n * n];\n"
            + "        int top = 0;\n"
            + "        stackR[0] = 0; stackC[0] = 0; dir[0] = 0; sol[0][0] = 1;\n"
            + "        while (top >= 0) {\n"
            + "            int r = stackR[top], c = stackC[top];\n"
            + "            if (r == n - 1 && c == n - 1) return true; // Reached destination\n"
            + "            boolean advanced = false;\n"
            + "            for (int d = dir[top]; d < 4; d++) {\n"
            + "                int nr = r + dr[d], nc = c + dc[d];\n"
            + "                if (nr >= 0 && nr < n && nc >= 0 && nc < n && maze[nr][nc] == 1 && sol[nr][nc] == 0) {\n"
            + "                    dir[top] = d + 1;\n"
            + "                    top++;\n"
            + "                    stackR[top] = nr; stackC[top] = nc; dir[top] = 0;\n"
            + "                    sol[nr][nc] = 1;\n"
            + "                    advanced = true;\n"
            + "                    break;\n"
            + "                }\n"
            + "            }\n"
            + "            if (!advanced) {\n"
            + "                sol[stackR[top]][stackC[top]] = 0;\n"
            + "                top--; // Backtrack\n"
            + "            }\n"
            + "        }\n"
            + "        return false;\n"
            + "    }\n"
            + "}", true);

        tc.putCode("Java", "Functions",
            "// Rat in a Maze - Functions\n"
            + "public class MazeSolverFunctional {\n"
            + "    public static boolean isSafe(int[][] maze, int r, int c, int[][] sol, int n) {\n"
            + "        return r >= 0 && r < n && c >= 0 && c < n && maze[r][c] == 1 && sol[r][c] == 0;\n"
            + "    }\n"
            + "    public static boolean solve(int[][] maze, int r, int c, int[][] sol, int n) {\n"
            + "        if (r == n - 1 && c == n - 1 && maze[r][c] == 1) {\n"
            + "            sol[r][c] = 1;\n"
            + "            return true;\n"
            + "        }\n"
            + "        if (isSafe(maze, r, c, sol, n)) {\n"
            + "            sol[r][c] = 1;\n"
            + "            if (solve(maze, r + 1, c, sol, n)) return true; // Down\n"
            + "            if (solve(maze, r, c + 1, sol, n)) return true; // Right\n"
            + "            if (solve(maze, r - 1, c, sol, n)) return true; // Up\n"
            + "            if (solve(maze, r, c - 1, sol, n)) return true; // Left\n"
            + "            sol[r][c] = 0; // Backtrack\n"
            + "        }\n"
            + "        return false;\n"
            + "    }\n"
            + "}", true);

        tc.putCode("Java", "Recursion",
            "// Rat in a Maze - Recursive DFS\n"
            + "public class MazeSolverRecursive {\n"
            + "    public static boolean solveMaze(int[][] maze, int n) {\n"
            + "        int[][] sol = new int[n][n];\n"
            + "        return dfs(maze, 0, 0, sol, n);\n"
            + "    }\n"
            + "    private static boolean dfs(int[][] m, int r, int c, int[][] sol, int n) {\n"
            + "        if (r == n - 1 && c == n - 1 && m[r][c] == 1) { sol[r][c] = 1; return true; }\n"
            + "        if (r >= 0 && r < n && c >= 0 && c < n && m[r][c] == 1 && sol[r][c] == 0) {\n"
            + "            sol[r][c] = 1;\n"
            + "            if (dfs(m, r + 1, c, sol, n)) return true;\n"
            + "            if (dfs(m, r, c + 1, sol, n)) return true;\n"
            + "            if (dfs(m, r - 1, c, sol, n)) return true;\n"
            + "            if (dfs(m, r, c - 1, sol, n)) return true;\n"
            + "            sol[r][c] = 0; // Backtrack\n"
            + "        }\n"
            + "        return false;\n"
            + "    }\n"
            + "}", true);

        tc.putCode("C", "Iterative",
            "/* Rat in a Maze - Iterative (C) */\n"
            + "#include <stdio.h>\n"
            + "#include <stdbool.h>\n\n"
            + "bool solveMaze(int maze[][4], int n) {\n"
            + "    int sol[4][4] = {0};\n"
            + "    int dr[] = {1, 0, -1, 0}, dc[] = {0, 1, 0, -1};\n"
            + "    int stackR[16], stackC[16], dir[16], top = 0;\n"
            + "    stackR[0] = 0; stackC[0] = 0; dir[0] = 0; sol[0][0] = 1;\n"
            + "    while (top >= 0) {\n"
            + "        int r = stackR[top], c = stackC[top];\n"
            + "        if (r == n - 1 && c == n - 1) return true;\n"
            + "        bool advanced = false;\n"
            + "        for (int d = dir[top]; d < 4; d++) {\n"
            + "            int nr = r + dr[d], nc = c + dc[d];\n"
            + "            if (nr >= 0 && nr < n && nc >= 0 && nc < n && maze[nr][nc] == 1 && sol[nr][nc] == 0) {\n"
            + "                dir[top] = d + 1;\n"
            + "                top++;\n"
            + "                stackR[top] = nr; stackC[top] = nc; dir[top] = 0;\n"
            + "                sol[nr][nc] = 1;\n"
            + "                advanced = true;\n"
            + "                break;\n"
            + "            }\n"
            + "        }\n"
            + "        if (!advanced) { sol[stackR[top]][stackC[top]] = 0; top--; }\n"
            + "    }\n"
            + "    return false;\n"
            + "}\n", true);

        tc.putCode("C", "Functions",
            "/* Rat in a Maze - Functions (C) */\n"
            + "#include <stdio.h>\n"
            + "#include <stdbool.h>\n\n"
            + "bool isSafe(int m[][4], int r, int c, int sol[][4], int n) {\n"
            + "    return r >= 0 && r < n && c >= 0 && c < n && m[r][c] == 1 && sol[r][c] == 0;\n"
            + "}\n\n"
            + "bool solve(int m[][4], int r, int c, int sol[][4], int n) {\n"
            + "    if (r == n - 1 && c == n - 1 && m[r][c] == 1) { sol[r][c] = 1; return true; }\n"
            + "    if (isSafe(m, r, c, sol, n)) {\n"
            + "        sol[r][c] = 1;\n"
            + "        if (solve(m, r + 1, c, sol, n)) return true;\n"
            + "        if (solve(m, r, c + 1, sol, n)) return true;\n"
            + "        if (solve(m, r - 1, c, sol, n)) return true;\n"
            + "        if (solve(m, r, c - 1, sol, n)) return true;\n"
            + "        sol[r][c] = 0;\n"
            + "    }\n"
            + "    return false;\n"
            + "}\n", true);

        tc.putCode("C", "Recursion",
            "/* Rat in a Maze - Recursive (C) */\n"
            + "#include <stdio.h>\n"
            + "#include <stdbool.h>\n\n"
            + "bool dfs(int m[][4], int r, int c, int sol[][4], int n) {\n"
            + "    if (r == n - 1 && c == n - 1 && m[r][c] == 1) { sol[r][c] = 1; return true; }\n"
            + "    if (r >= 0 && r < n && c >= 0 && c < n && m[r][c] == 1 && sol[r][c] == 0) {\n"
            + "        sol[r][c] = 1;\n"
            + "        if (dfs(m, r + 1, c, sol, n)) return true;\n"
            + "        if (dfs(m, r, c + 1, sol, n)) return true;\n"
            + "        if (dfs(m, r - 1, c, sol, n)) return true;\n"
            + "        if (dfs(m, r, c - 1, sol, n)) return true;\n"
            + "        sol[r][c] = 0; // Backtrack\n"
            + "    }\n"
            + "    return false;\n"
            + "}\n", true);

        tc.putCode("Python", "Iterative",
            "# Rat in a Maze - Iterative (Python)\n"
            + "def solve_maze_iterative(maze, n):\n"
            + "    sol = [[0] * n for _ in range(n)]\n"
            + "    dr, dc = [1, 0, -1, 0], [0, 1, 0, -1]\n"
            + "    stack = [(0, 0, 0)]\n"
            + "    sol[0][0] = 1\n"
            + "    while stack:\n"
            + "        r, c, d_idx = stack.pop()\n"
            + "        if r == n - 1 and c == n - 1:\n"
            + "            return True\n"
            + "        advanced = False\n"
            + "        for d in range(d_idx, 4):\n"
            + "            nr, nc = r + dr[d], c + dc[d]\n"
            + "            if 0 <= nr < n and 0 <= nc < n and maze[nr][nc] == 1 and sol[nr][nc] == 0:\n"
            + "                stack.append((r, c, d + 1))\n"
            + "                sol[nr][nc] = 1\n"
            + "                stack.append((nr, nc, 0))\n"
            + "                advanced = True\n"
            + "                break\n"
            + "        if not advanced:\n"
            + "            sol[r][c] = 0  # Backtrack\n"
            + "    return False\n", true);

        tc.putCode("Python", "Functions",
            "# Rat in a Maze - Functions (Python)\n"
            + "def is_safe(m, r, c, sol, n):\n"
            + "    return 0 <= r < n and 0 <= c < n and m[r][c] == 1 and sol[r][c] == 0\n\n"
            + "def solve_maze(m, r, c, sol, n):\n"
            + "    if r == n - 1 and c == n - 1 and m[r][c] == 1:\n"
            + "        sol[r][c] = 1\n"
            + "        return True\n"
            + "    if is_safe(m, r, c, sol, n):\n"
            + "        sol[r][c] = 1\n"
            + "        if solve_maze(m, r + 1, c, sol, n): return True\n"
            + "        if solve_maze(m, r, c + 1, sol, n): return True\n"
            + "        if solve_maze(m, r - 1, c, sol, n): return True\n"
            + "        if solve_maze(m, r, c - 1, sol, n): return True\n"
            + "        sol[r][c] = 0  # Backtrack\n"
            + "    return False\n", true);

        tc.putCode("Python", "Recursion",
            "# Rat in a Maze - Recursive DFS\n"
            + "def solve_maze_recursive(maze, n):\n"
            + "    sol = [[0] * n for _ in range(n)]\n"
            + "    def dfs(r, c):\n"
            + "        if r == n - 1 and c == n - 1 and maze[r][c] == 1:\n"
            + "            sol[r][c] = 1\n"
            + "            return True\n"
            + "        if 0 <= r < n and 0 <= c < n and maze[r][c] == 1 and sol[r][c] == 0:\n"
            + "            sol[r][c] = 1\n"
            + "            if dfs(r + 1, c) or dfs(r, c + 1) or dfs(r - 1, c) or dfs(r, c - 1):\n"
            + "                return True\n"
            + "            sol[r][c] = 0  # Backtrack\n"
            + "        return False\n"
            + "    return dfs(0, 0)\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerHamiltonian(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "backtracking_hamiltonian",
            "Hamiltonian Path & Cycle",
            "A Hamiltonian path in an undirected graph visits each vertex exactly once. A Hamiltonian cycle is a Hamiltonian path that is a cycle returning to the starting vertex.\n\n"
                + "We build the path vertex by vertex, checking adjacency and ensuring no vertex is visited twice, backtracking whenever no adjacent unvisited vertex remains.",
            "The Traveling Salesperson Problem (TSP) core decision problem and package delivery route verification.",
            "O(N!)", "O(N!)", "O(N!)", "O(N)",
            new int[] {0, 1, 2, 3, 4},
            5
        );

        tc.algorithmByStyle.put("Iterative",
            "Algorithm: HAMILTONIAN_ITERATIVE(graph, V)\n"
            + "1. Initialize path array of size V with -1. Set path[0] = 0.\n"
            + "2. Maintain vertex choices at each position: choice[V] initialized to 1.\n"
            + "3. Set pos = 1.\n"
            + "4. While pos > 0:\n"
            + "   a. While choice[pos] < V:\n"
            + "        v = choice[pos], increment choice[pos] by 1.\n"
            + "        If isSafe(v, graph, path, pos):\n"
            + "          path[pos] = v.\n"
            + "          If pos == V - 1:\n"
            + "            If graph[path[pos]][path[0]] == 1, return true (cycle found!).\n"
            + "          Else:\n"
            + "            pos = pos + 1, choice[pos] = 1.\n"
            + "   b. Reset path[pos] = -1, choice[pos] = 1, pos = pos - 1 (backtrack).\n"
            + "5. Return false."
        );
        tc.algorithmByStyle.put("Functions",
            "Algorithm: HAMILTONIAN_FUNCTIONAL(graph, V)\n"
            + "FUNCTION IS_SAFE(v, graph, path, pos):\n"
            + "1. If graph[path[pos-1]][v] == 0: return false (no edge).\n"
            + "2. For i = 0 to pos-1: if path[i] == v: return false (already visited).\n"
            + "3. Return true.\n\n"
            + "FUNCTION SOLVE_HAM_CYCLE(graph, V):\n"
            + "1. path = array of size V with -1. path[0] = 0.\n"
            + "2. Call EXTEND_PATH(graph, path, 1, V).\n"
            + "3. Return result."
        );
        tc.algorithmByStyle.put("Recursion",
            "Algorithm: HAMILTONIAN_RECURSIVE(graph, path, pos, V)\n"
            + "1. Base Case: If pos == V: return (graph[path[pos-1]][path[0]] == 1).\n"
            + "2. For each candidate vertex v from 1 to V-1:\n"
            + "   a. If IS_SAFE(v, graph, path, pos):\n"
            + "        path[pos] = v\n"
            + "        If HAMILTONIAN_RECURSIVE(graph, path, pos + 1, V) == true: return true\n"
            + "        path[pos] = -1 (backtrack)\n"
            + "3. Return false."
        );

        tc.putCode("Java", "Iterative",
            "// Hamiltonian Cycle - Iterative Backtracking\n"
            + "public class HamiltonianIterative {\n"
            + "    public static boolean hamCycle(int[][] graph, int V) {\n"
            + "        int[] path = new int[V];\n"
            + "        int[] choice = new int[V];\n"
            + "        java.util.Arrays.fill(path, -1);\n"
            + "        java.util.Arrays.fill(choice, 1);\n"
            + "        path[0] = 0;\n"
            + "        int pos = 1;\n"
            + "        while (pos > 0) {\n"
            + "            boolean found = false;\n"
            + "            while (choice[pos] < V) {\n"
            + "                int v = choice[pos]++;\n"
            + "                if (isSafe(v, graph, path, pos)) {\n"
            + "                    path[pos] = v;\n"
            + "                    if (pos == V - 1) {\n"
            + "                        if (graph[path[pos]][path[0]] == 1) return true;\n"
            + "                    } else {\n"
            + "                        pos++;\n"
            + "                        choice[pos] = 1;\n"
            + "                        found = true;\n"
            + "                        break;\n"
            + "                    }\n"
            + "                }\n"
            + "            }\n"
            + "            if (!found) {\n"
            + "                path[pos] = -1;\n"
            + "                choice[pos] = 1;\n"
            + "                pos--; // Backtrack\n"
            + "            }\n"
            + "        }\n"
            + "        return false;\n"
            + "    }\n"
            + "    private static boolean isSafe(int v, int[][] g, int[] path, int pos) {\n"
            + "        if (g[path[pos - 1]][v] == 0) return false;\n"
            + "        for (int i = 0; i < pos; i++) if (path[i] == v) return false;\n"
            + "        return true;\n"
            + "    }\n"
            + "}", true);

        tc.putCode("Java", "Functions",
            "// Hamiltonian Cycle - Functions\n"
            + "public class HamiltonianFunctional {\n"
            + "    public static boolean isSafe(int v, int[][] graph, int[] path, int pos) {\n"
            + "        if (graph[path[pos - 1]][v] == 0) return false;\n"
            + "        for (int i = 0; i < pos; i++) if (path[i] == v) return false;\n"
            + "        return true;\n"
            + "    }\n"
            + "    public static boolean solve(int[][] graph, int[] path, int pos, int V) {\n"
            + "        if (pos == V) return graph[path[pos - 1]][path[0]] == 1;\n"
            + "        for (int v = 1; v < V; v++) {\n"
            + "            if (isSafe(v, graph, path, pos)) {\n"
            + "                path[pos] = v;\n"
            + "                if (solve(graph, path, pos + 1, V)) return true;\n"
            + "                path[pos] = -1; // Backtrack\n"
            + "            }\n"
            + "        }\n"
            + "        return false;\n"
            + "    }\n"
            + "}", true);

        tc.putCode("Java", "Recursion",
            "// Hamiltonian Cycle - Recursive DFS\n"
            + "public class HamiltonianRecursive {\n"
            + "    public static boolean hamCycle(int[][] graph, int V) {\n"
            + "        int[] path = new int[V];\n"
            + "        java.util.Arrays.fill(path, -1);\n"
            + "        path[0] = 0;\n"
            + "        return solve(graph, path, 1, V);\n"
            + "    }\n"
            + "    private static boolean solve(int[][] g, int[] path, int pos, int V) {\n"
            + "        if (pos == V) return g[path[pos - 1]][path[0]] == 1;\n"
            + "        for (int v = 1; v < V; v++) {\n"
            + "            if (g[path[pos - 1]][v] == 1 && !inPath(path, v, pos)) {\n"
            + "                path[pos] = v;\n"
            + "                if (solve(g, path, pos + 1, V)) return true;\n"
            + "                path[pos] = -1;\n"
            + "            }\n"
            + "        }\n"
            + "        return false;\n"
            + "    }\n"
            + "    private static boolean inPath(int[] path, int v, int pos) {\n"
            + "        for (int i = 0; i < pos; i++) if (path[i] == v) return true;\n"
            + "        return false;\n"
            + "    }\n"
            + "}", true);

        tc.putCode("C", "Iterative",
            "/* Hamiltonian Cycle - Iterative (C) */\n"
            + "#include <stdio.h>\n"
            + "#include <stdbool.h>\n\n"
            + "bool isSafe(int v, int g[][5], int path[], int pos) {\n"
            + "    if (g[path[pos - 1]][v] == 0) return false;\n"
            + "    for (int i = 0; i < pos; i++) if (path[i] == v) return false;\n"
            + "    return true;\n"
            + "}\n\n"
            + "bool hamCycle(int g[][5], int V) {\n"
            + "    int path[5], choice[5];\n"
            + "    for (int i = 0; i < V; i++) { path[i] = -1; choice[i] = 1; }\n"
            + "    path[0] = 0;\n"
            + "    int pos = 1;\n"
            + "    while (pos > 0) {\n"
            + "        bool found = false;\n"
            + "        while (choice[pos] < V) {\n"
            + "            int v = choice[pos]++;\n"
            + "            if (isSafe(v, g, path, pos)) {\n"
            + "                path[pos] = v;\n"
            + "                if (pos == V - 1) {\n"
            + "                    if (g[path[pos]][path[0]] == 1) return true;\n"
            + "                } else {\n"
            + "                    pos++; choice[pos] = 1; found = true; break;\n"
            + "                }\n"
            + "            }\n"
            + "        }\n"
            + "        if (!found) { path[pos] = -1; choice[pos] = 1; pos--; }\n"
            + "    }\n"
            + "    return false;\n"
            + "}\n", true);

        tc.putCode("C", "Functions",
            "/* Hamiltonian Cycle - Functions (C) */\n"
            + "#include <stdio.h>\n"
            + "#include <stdbool.h>\n\n"
            + "bool isSafe(int v, int g[][5], int path[], int pos) {\n"
            + "    if (g[path[pos - 1]][v] == 0) return false;\n"
            + "    for (int i = 0; i < pos; i++) if (path[i] == v) return false;\n"
            + "    return true;\n"
            + "}\n\n"
            + "bool solve(int g[][5], int path[], int pos, int V) {\n"
            + "    if (pos == V) return g[path[pos - 1]][path[0]] == 1;\n"
            + "    for (int v = 1; v < V; v++) {\n"
            + "        if (isSafe(v, g, path, pos)) {\n"
            + "            path[pos] = v;\n"
            + "            if (solve(g, path, pos + 1, V)) return true;\n"
            + "            path[pos] = -1; // Backtrack\n"
            + "        }\n"
            + "    }\n"
            + "    return false;\n"
            + "}\n", true);

        tc.putCode("C", "Recursion",
            "/* Hamiltonian Cycle - Recursive (C) */\n"
            + "#include <stdio.h>\n"
            + "#include <stdbool.h>\n\n"
            + "bool solveRecursive(int g[][5], int path[], int pos, int V) {\n"
            + "    if (pos == V) return g[path[pos - 1]][path[0]] == 1;\n"
            + "    for (int v = 1; v < V; v++) {\n"
            + "        if (g[path[pos - 1]][v] == 1) {\n"
            + "            bool visited = false;\n"
            + "            for (int i = 0; i < pos; i++) if (path[i] == v) { visited = true; break; }\n"
            + "            if (!visited) {\n"
            + "                path[pos] = v;\n"
            + "                if (solveRecursive(g, path, pos + 1, V)) return true;\n"
            + "                path[pos] = -1;\n"
            + "            }\n"
            + "        }\n"
            + "    }\n"
            + "    return false;\n"
            + "}\n", true);

        tc.putCode("Python", "Iterative",
            "# Hamiltonian Cycle - Iterative\n"
            + "def ham_cycle_iterative(graph, V):\n"
            + "    path = [-1] * V\n"
            + "    choice = [1] * V\n"
            + "    path[0] = 0\n"
            + "    pos = 1\n"
            + "    while pos > 0:\n"
            + "        found = False\n"
            + "        while choice[pos] < V:\n"
            + "            v = choice[pos]\n"
            + "            choice[pos] += 1\n"
            + "            if graph[path[pos - 1]][v] == 1 and v not in path[:pos]:\n"
            + "                path[pos] = v\n"
            + "                if pos == V - 1:\n"
            + "                    if graph[path[pos]][path[0]] == 1: return True\n"
            + "                else:\n"
            + "                    pos += 1\n"
            + "                    choice[pos] = 1\n"
            + "                    found = True\n"
            + "                    break\n"
            + "        if not found:\n"
            + "            path[pos] = -1\n"
            + "            choice[pos] = 1\n"
            + "            pos -= 1  # Backtrack\n"
            + "    return False\n", true);

        tc.putCode("Python", "Functions",
            "# Hamiltonian Cycle - Functions\n"
            + "def is_safe(v, graph, path, pos):\n"
            + "    if graph[path[pos - 1]][v] == 0: return False\n"
            + "    if v in path[:pos]: return False\n"
            + "    return True\n\n"
            + "def solve_ham(graph, path, pos, V):\n"
            + "    if pos == V: return graph[path[pos - 1]][path[0]] == 1\n"
            + "    for v in range(1, V):\n"
            + "        if is_safe(v, graph, path, pos):\n"
            + "            path[pos] = v\n"
            + "            if solve_ham(graph, path, pos + 1, V): return True\n"
            + "            path[pos] = -1  # Backtrack\n"
            + "    return False\n", true);

        tc.putCode("Python", "Recursion",
            "# Hamiltonian Cycle - Recursive DFS\n"
            + "def hamiltonian_cycle_recursive(graph, V):\n"
            + "    path = [-1] * V\n"
            + "    path[0] = 0\n"
            + "    def backtrack(pos):\n"
            + "        if pos == V: return graph[path[pos - 1]][path[0]] == 1\n"
            + "        for v in range(1, V):\n"
            + "            if graph[path[pos - 1]][v] == 1 and v not in path[:pos]:\n"
            + "                path[pos] = v\n"
            + "                if backtrack(pos + 1): return True\n"
            + "                path[pos] = -1\n"
            + "        return False\n"
            + "    return backtrack(1)\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerGraphColoring(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "backtracking_graph_coloring",
            "Graph m-Coloring Problem",
            "Given an undirected graph and a number m, determine if the graph can be colored with at most m colors such that no two adjacent vertices share the same color.\n\n"
                + "We assign colors to vertices one by one. Before assigning, we check if adjacent vertices already have that color. If all colors fail, we backtrack to the previous vertex.",
            "Scheduling final exam timetables without conflicts and cellular mobile tower frequency band allocation.",
            "O(m^V)", "O(m^V)", "O(m^V)", "O(V)",
            new int[] {0, 1, 2, 3},
            3
        );

        tc.algorithmByStyle.put("Iterative",
            "Algorithm: GRAPH_COLORING_ITERATIVE(graph, m, V)\n"
            + "1. Initialize color array of size V with 0 (uncolored).\n"
            + "2. Set vertex v = 0.\n"
            + "3. While v >= 0 and v < V:\n"
            + "   a. Increment color[v] by 1.\n"
            + "   b. While color[v] <= m and isConflict(v, graph, color):\n"
            + "        Increment color[v] by 1.\n"
            + "   c. If color[v] <= m:\n"
            + "        v = v + 1 (advance to next vertex).\n"
            + "   d. Else:\n"
            + "        color[v] = 0, v = v - 1 (backtrack to previous vertex).\n"
            + "4. Return v == V (true if all vertices successfully colored)."
        );
        tc.algorithmByStyle.put("Functions",
            "Algorithm: GRAPH_COLORING_FUNCTIONAL(graph, m, V)\n"
            + "FUNCTION IS_SAFE(v, graph, color, c, V):\n"
            + "1. For each vertex i from 0 to V-1:\n"
            + "   If graph[v][i] == 1 and color[i] == c: return false.\n"
            + "2. Return true.\n\n"
            + "FUNCTION SOLVE_COLORING(graph, m, V):\n"
            + "1. Allocate color array of size V initialized to 0.\n"
            + "2. Call COLOR_GRAPH(graph, m, color, 0, V).\n"
            + "3. Return color array if true, else report failure."
        );
        tc.algorithmByStyle.put("Recursion",
            "Algorithm: GRAPH_COLORING_RECURSIVE(graph, m, color, v, V)\n"
            + "1. Base Case: If v == V, return true (all vertices colored).\n"
            + "2. For color c = 1 to m:\n"
            + "   a. If IS_SAFE(v, graph, color, c, V):\n"
            + "        color[v] = c\n"
            + "        If GRAPH_COLORING_RECURSIVE(graph, m, color, v + 1, V) == true: return true\n"
            + "        color[v] = 0 (backtrack)\n"
            + "3. Return false."
        );

        tc.putCode("Java", "Iterative",
            "// Graph Coloring - Iterative\n"
            + "public class GraphColoringIterative {\n"
            + "    public static boolean graphColoring(int[][] graph, int m, int V) {\n"
            + "        int[] color = new int[V];\n"
            + "        int v = 0;\n"
            + "        while (v >= 0 && v < V) {\n"
            + "            color[v]++;\n"
            + "            while (color[v] <= m && !isSafe(v, graph, color, color[v], V)) {\n"
            + "                color[v]++;\n"
            + "            }\n"
            + "            if (color[v] <= m) {\n"
            + "                v++;\n"
            + "            } else {\n"
            + "                color[v] = 0;\n"
            + "                v--; // Backtrack\n"
            + "            }\n"
            + "        }\n"
            + "        return v == V;\n"
            + "    }\n"
            + "    private static boolean isSafe(int v, int[][] g, int[] color, int c, int V) {\n"
            + "        for (int i = 0; i < V; i++) if (g[v][i] == 1 && color[i] == c) return false;\n"
            + "        return true;\n"
            + "    }\n"
            + "}", true);

        tc.putCode("Java", "Functions",
            "// Graph Coloring - Functions\n"
            + "public class GraphColoringFunctional {\n"
            + "    public static boolean isSafe(int v, int[][] graph, int[] color, int c, int V) {\n"
            + "        for (int i = 0; i < V; i++) if (graph[v][i] == 1 && color[i] == c) return false;\n"
            + "        return true;\n"
            + "    }\n"
            + "    public static boolean solve(int[][] graph, int m, int[] color, int v, int V) {\n"
            + "        if (v == V) return true;\n"
            + "        for (int c = 1; c <= m; c++) {\n"
            + "            if (isSafe(v, graph, color, c, V)) {\n"
            + "                color[v] = c;\n"
            + "                if (solve(graph, m, color, v + 1, V)) return true;\n"
            + "                color[v] = 0; // Backtrack\n"
            + "            }\n"
            + "        }\n"
            + "        return false;\n"
            + "    }\n"
            + "}", true);

        tc.putCode("Java", "Recursion",
            "// Graph Coloring - Recursive Backtracking\n"
            + "public class GraphColoringRecursive {\n"
            + "    public static boolean graphColoring(int[][] graph, int m, int V) {\n"
            + "        int[] color = new int[V];\n"
            + "        return colorVertex(graph, m, color, 0, V);\n"
            + "    }\n"
            + "    private static boolean colorVertex(int[][] g, int m, int[] color, int v, int V) {\n"
            + "        if (v == V) return true;\n"
            + "        for (int c = 1; c <= m; c++) {\n"
            + "            if (isSafe(g, v, color, c, V)) {\n"
            + "                color[v] = c;\n"
            + "                if (colorVertex(g, m, color, v + 1, V)) return true;\n"
            + "                color[v] = 0; // Backtrack\n"
            + "            }\n"
            + "        }\n"
            + "        return false;\n"
            + "    }\n"
            + "    private static boolean isSafe(int[][] g, int v, int[] color, int c, int V) {\n"
            + "        for (int i = 0; i < V; i++) if (g[v][i] == 1 && color[i] == c) return false;\n"
            + "        return true;\n"
            + "    }\n"
            + "}", true);

        tc.putCode("C", "Iterative",
            "/* Graph Coloring - Iterative (C) */\n"
            + "#include <stdio.h>\n"
            + "#include <stdbool.h>\n\n"
            + "bool isSafe(int v, int g[][4], int color[], int c, int V) {\n"
            + "    for (int i = 0; i < V; i++) if (g[v][i] && color[i] == c) return false;\n"
            + "    return true;\n"
            + "}\n\n"
            + "bool graphColoring(int g[][4], int m, int V) {\n"
            + "    int color[4] = {0};\n"
            + "    int v = 0;\n"
            + "    while (v >= 0 && v < V) {\n"
            + "        color[v]++;\n"
            + "        while (color[v] <= m && !isSafe(v, g, color, color[v], V)) color[v]++;\n"
            + "        if (color[v] <= m) {\n"
            + "            v++;\n"
            + "        } else {\n"
            + "            color[v] = 0;\n"
            + "            v--; // Backtrack\n"
            + "        }\n"
            + "    }\n"
            + "    return v == V;\n"
            + "}\n", true);

        tc.putCode("C", "Functions",
            "/* Graph Coloring - Functions (C) */\n"
            + "#include <stdio.h>\n"
            + "#include <stdbool.h>\n\n"
            + "bool isSafe(int v, int g[][4], int color[], int c, int V) {\n"
            + "    for (int i = 0; i < V; i++) if (g[v][i] && color[i] == c) return false;\n"
            + "    return true;\n"
            + "}\n\n"
            + "bool solve(int g[][4], int m, int color[], int v, int V) {\n"
            + "    if (v == V) return true;\n"
            + "    for (int c = 1; c <= m; c++) {\n"
            + "        if (isSafe(v, g, color, c, V)) {\n"
            + "            color[v] = c;\n"
            + "            if (solve(g, m, color, v + 1, V)) return true;\n"
            + "            color[v] = 0;\n"
            + "        }\n"
            + "    }\n"
            + "    return false;\n"
            + "}\n", true);

        tc.putCode("C", "Recursion",
            "/* Graph Coloring - Recursive (C) */\n"
            + "#include <stdio.h>\n"
            + "#include <stdbool.h>\n\n"
            + "bool colorVertex(int g[][4], int m, int color[], int v, int V) {\n"
            + "    if (v == V) return true;\n"
            + "    for (int c = 1; c <= m; c++) {\n"
            + "        bool safe = true;\n"
            + "        for (int i = 0; i < V; i++) if (g[v][i] && color[i] == c) { safe = false; break; }\n"
            + "        if (safe) {\n"
            + "            color[v] = c;\n"
            + "            if (colorVertex(g, m, color, v + 1, V)) return true;\n"
            + "            color[v] = 0;\n"
            + "        }\n"
            + "    }\n"
            + "    return false;\n"
            + "}\n", true);

        tc.putCode("Python", "Iterative",
            "# Graph Coloring - Iterative\n"
            + "def graph_coloring_iterative(graph, m, V):\n"
            + "    color = [0] * V\n"
            + "    v = 0\n"
            + "    while 0 <= v < V:\n"
            + "        color[v] += 1\n"
            + "        while color[v] <= m and any(graph[v][i] == 1 and color[i] == color[v] for i in range(V)):\n"
            + "            color[v] += 1\n"
            + "        if color[v] <= m:\n"
            + "            v += 1\n"
            + "        else:\n"
            + "            color[v] = 0\n"
            + "            v -= 1  # Backtrack\n"
            + "    return v == V\n", true);

        tc.putCode("Python", "Functions",
            "# Graph Coloring - Functions\n"
            + "def is_safe(v, graph, color, c, V):\n"
            + "    return not any(graph[v][i] == 1 and color[i] == c for i in range(V))\n\n"
            + "def solve_coloring(graph, m, color, v, V):\n"
            + "    if v == V: return True\n"
            + "    for c in range(1, m + 1):\n"
            + "        if is_safe(v, graph, color, c, V):\n"
            + "            color[v] = c\n"
            + "            if solve_coloring(graph, m, color, v + 1, V): return True\n"
            + "            color[v] = 0  # Backtrack\n"
            + "    return False\n", true);

        tc.putCode("Python", "Recursion",
            "# Graph Coloring - Recursive\n"
            + "def graph_coloring_recursive(graph, m, V):\n"
            + "    color = [0] * V\n"
            + "    def dfs(v):\n"
            + "        if v == V: return True\n"
            + "        for c in range(1, m + 1):\n"
            + "            if not any(graph[v][i] == 1 and color[i] == c for i in range(V)):\n"
            + "                color[v] = c\n"
            + "                if dfs(v + 1): return True\n"
            + "                color[v] = 0\n"
            + "        return False\n"
            + "    return dfs(0)\n", true);

        contentMap.put(tc.techniqueId, tc);
    }
}
