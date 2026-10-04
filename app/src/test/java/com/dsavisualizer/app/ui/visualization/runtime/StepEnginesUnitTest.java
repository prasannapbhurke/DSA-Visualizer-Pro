package com.dsavisualizer.app.ui.visualization.runtime;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StepEnginesUnitTest {

    @Test
    public void dijkstra_findNextDijkstraNode_selectsMinimumUnsettled() {
        int[] distances = {0, 4, 2, 9999, 9999};
        boolean[] settled = {true, false, false, false, false};

        int nextNode = DijkstraStepEngine.findNextDijkstraNode(distances, settled);
        assertEquals(2, nextNode);
    }

    @Test
    public void dijkstra_findNextNeighbor_findsValidAdjacentEdge() {
        int[][] weights = {
            {0, 2, 9999, 1, 9999},
            {2, 0, 3, 9999, 9999}
        };
        boolean[] settled = {false, false, false, false, false};

        int firstNeighbor = DijkstraStepEngine.findNextDijkstraNeighbor(weights, settled, 0, 0);
        assertEquals(1, firstNeighbor);

        int secondNeighbor = DijkstraStepEngine.findNextDijkstraNeighbor(weights, settled, 0, 2);
        assertEquals(3, secondNeighbor);
    }

    @Test
    public void unionFind_buildUnionPath_findsRootWalk() {
        int[] parents = {0, 0, 1, 2}; // 3 -> 2 -> 1 -> 0
        int[] path = UnionFindStepEngine.buildUnionPath(parents, 3);

        assertArrayEquals(new int[]{3, 2, 1, 0}, path);
    }

    @Test
    public void unionFind_buildUnionPath_handlesSelfRoot() {
        int[] parents = {0, 1, 2};
        int[] path = UnionFindStepEngine.buildUnionPath(parents, 1);

        assertArrayEquals(new int[]{1}, path);
    }

    @Test
    public void testMockHostStepEngineTransitions() {
        // Test basic advance logic using a lightweight mock host
        MockRuntimeHost host = new MockRuntimeHost();
        host.techniqueId = "avl_tree";
        host.stepIndex = -1;

        AvlTreeStepEngine.advance(host);
        assertEquals(0, host.stepIndex);
        assertEquals(1, host.activePrimary);
        assertEquals(3, host.activeSecondary);

        AvlTreeStepEngine.advance(host);
        assertEquals(1, host.stepIndex);
        assertEquals(0, host.activePrimary);
        assertEquals(1, host.activeSecondary);

        AvlTreeStepEngine.advance(host);
        assertTrue(host.playbackComplete);
    }

    @Test
    public void testTrieStepEngineTransitions() {
        MockRuntimeHost host = new MockRuntimeHost();
        host.techniqueId = "trie";
        host.stepIndex = -1;

        TrieStepEngine.advance(host);
        assertEquals(0, host.stepIndex);
        assertEquals(0, host.activePrimary);

        TrieStepEngine.advance(host);
        assertEquals(1, host.stepIndex);
        assertEquals(1, host.activePrimary);

        TrieStepEngine.advance(host);
        assertEquals(2, host.stepIndex);
        assertEquals(2, host.activePrimary);

        TrieStepEngine.advance(host);
        assertEquals(3, host.stepIndex);
        assertEquals(3, host.activePrimary);

        TrieStepEngine.advance(host);
        assertTrue(host.playbackComplete);
    }

    @Test
    public void testHuffmanStepEngineProgression() {
        MockRuntimeHost host = new MockRuntimeHost();
        host.setWorkingValues(new int[]{5, 9, 12, 13, 16, 45});
        host.stepIndex = -1;

        HuffmanStepEngine.advance(host);
        assertEquals(0, host.stepIndex);
        assertEquals(6, host.huffmanQueue.length);

        HuffmanStepEngine.advance(host);
        // 5 + 9 = 14, queue length becomes 5
        assertEquals(5, host.huffmanQueue.length);
        assertEquals(14, host.huffmanMerged);
        assertEquals(5, host.huffmanMergedLeft);
        assertEquals(9, host.huffmanMergedRight);

        // Step 2: queue has {12, 13, 14, 16, 45} -> merges 12 + 13 = 25
        HuffmanStepEngine.advance(host);
        assertEquals(4, host.huffmanQueue.length);
        assertEquals(25, host.huffmanMerged);
        assertEquals(12, host.huffmanMergedLeft);
        assertEquals(13, host.huffmanMergedRight);

        // Step 3: queue has {14, 16, 25, 45} -> merges 14 + 16 = 30
        HuffmanStepEngine.advance(host);
        assertEquals(3, host.huffmanQueue.length);
        assertEquals(30, host.huffmanMerged);
        assertEquals(14, host.huffmanMergedLeft);
        assertEquals(16, host.huffmanMergedRight);

        // Step 4: queue has {25, 30, 45} -> merges 25 + 30 = 55
        HuffmanStepEngine.advance(host);
        assertEquals(2, host.huffmanQueue.length);
        assertEquals(55, host.huffmanMerged);
        assertEquals(25, host.huffmanMergedLeft);
        assertEquals(30, host.huffmanMergedRight);

        // Step 5: queue has {45, 55} -> merges 45 + 55 = 100
        HuffmanStepEngine.advance(host);
        assertEquals(1, host.huffmanQueue.length);
        assertEquals(100, host.huffmanMerged);
        assertEquals(45, host.huffmanMergedLeft);
        assertEquals(55, host.huffmanMergedRight);
        assertTrue(host.playbackComplete);
    }

    @Test
    public void testHuffman_allPresetsCompleteCleanly() {
        for (int preset = 1; preset <= 3; preset++) {
            MockRuntimeHost host = new MockRuntimeHost();
            int[] defaultWeights = HuffmanStepEngine.getDefaultWeights(preset);
            assertNotNull(defaultWeights);
            assertTrue(defaultWeights.length >= 2);

            host.workingValues = defaultWeights;
            HuffmanStepEngine.initializePreview(host, defaultWeights);
            assertEquals(defaultWeights.length, host.huffmanQueue.length);

            int totalExpectedSteps = HuffmanStepEngine.getTotalSteps(defaultWeights);
            assertEquals(defaultWeights.length, totalExpectedSteps);

            host.stepIndex = -1;
            for (int s = 0; s < totalExpectedSteps; s++) {
                assertFalse("Should not complete prematurely at step " + s, host.playbackComplete);
                HuffmanStepEngine.advance(host);
            }
            assertTrue("Preset " + preset + " must complete at final step", host.playbackComplete);
            assertEquals(1, host.huffmanQueue.length);
        }
    }

    @Test
    public void testHuffman_customWeightsProgression() {
        MockRuntimeHost host = new MockRuntimeHost();
        int[] custom = new int[]{4, 7, 10, 15, 20};
        host.workingValues = custom;
        HuffmanStepEngine.initializePreview(host, custom);
        assertEquals(5, host.huffmanQueue.length);
        assertEquals(5, HuffmanStepEngine.getTotalSteps(custom));

        host.stepIndex = -1;
        // Step 0: init
        HuffmanStepEngine.advance(host);
        assertEquals(5, host.huffmanQueue.length);

        // Step 1: merge 4 + 7 = 11
        HuffmanStepEngine.advance(host);
        assertEquals(4, host.huffmanQueue.length);
        assertEquals(11, host.huffmanMerged);
        assertEquals(4, host.huffmanMergedLeft);
        assertEquals(7, host.huffmanMergedRight);

        // Run until end
        while (!host.playbackComplete) {
            HuffmanStepEngine.advance(host);
        }
        assertTrue(host.playbackComplete);
        assertEquals(1, host.huffmanQueue.length);
    }

    @Test
    public void testHuffman_presetDescriptionsAndInputsAreValid() {
        for (int preset = 1; preset <= 3; preset++) {
            String desc = HuffmanStepEngine.getPresetDescription(preset);
            assertNotNull(desc);
            assertTrue(desc.length() > 10);

            String input = HuffmanStepEngine.getDefaultInput(preset);
            assertNotNull(input);
            assertTrue(input.contains(","));

            int[] weights = HuffmanStepEngine.getDefaultWeights(preset);
            assertNotNull(weights);
            assertTrue(weights.length >= 2);
        }
    }

    @Test
    public void testWarshallMatrixAlgorithmProgression() {
        MockRuntimeHost host = new MockRuntimeHost();
        host.techniqueId = "warshall_algorithm";
        host.matrix = new int[][]{
            {0, 1, 0, 1},
            {0, 0, 1, 0},
            {0, 0, 0, 1},
            {0, 0, 0, 0}
        };

        MatrixAlgorithmEngine.advance(host);
        assertEquals(0, host.matrixK);
        assertEquals(0, host.matrixRow);
        assertEquals(1, host.matrixCol);
    }

    @Test
    public void testHomeCatalogLeetCodeIsLast() {
        java.util.List<com.dsavisualizer.app.model.CategoryItem> categories =
            com.dsavisualizer.app.data.HomeCatalog.getCategories();
        assertTrue(!categories.isEmpty());
        com.dsavisualizer.app.model.CategoryItem last = categories.get(categories.size() - 1);
        assertEquals("leetcode", last.id);
    }

    @Test
    public void testLeetCodeTwoSumProgression() {
        MockRuntimeHost host = new MockRuntimeHost();
        host.techniqueId = "leetcode_two_sum";
        host.workingValues = new int[]{2, 7, 11, 15};
        host.target = 9;
        host.stepIndex = -1;

        // Step 1: checks index 0 (val 2), complement 7 not in map
        LeetCodeStepEngine.advance(host);
        assertEquals(0, host.stepIndex);
        assertEquals(false, host.playbackComplete);

        // Step 2: checks index 1 (val 7), complement 2 found in map!
        LeetCodeStepEngine.advance(host);
        assertEquals(1, host.stepIndex);
        assertTrue(host.playbackComplete);
    }

    @Test
    public void testLeetCodeLinkedListCycleCollision() {
        MockRuntimeHost host = new MockRuntimeHost();
        host.techniqueId = "leetcode_linked_list_cycle";
        host.workingValues = new int[]{3, 2, 0, -4};
        host.stepIndex = -1;

        // Step 0: start
        LeetCodeStepEngine.advance(host);
        assertEquals(0, host.stepIndex);

        // Step 1: slow=1, fast=2
        LeetCodeStepEngine.advance(host);
        assertEquals(1, host.stepIndex);

        // Step 2: slow=2, fast=1
        LeetCodeStepEngine.advance(host);
        assertEquals(2, host.stepIndex);

        // Step 3: slow=3, fast=3 (collision detected!)
        LeetCodeStepEngine.advance(host);
        assertEquals(3, host.stepIndex);
        assertTrue(host.playbackComplete);
    }

    @Test
    public void testLeetCodeInvertBinaryTreeSwaps() {
        MockRuntimeHost host = new MockRuntimeHost();
        host.techniqueId = "leetcode_invert_binary_tree";
        host.workingValues = new int[]{4, 2, 7, 1, 3, 6, 9};
        host.stepIndex = -1;

        // Step 0: start
        LeetCodeStepEngine.advance(host);
        assertEquals(0, host.stepIndex);

        // Step 1: swap children of root 4 (2 and 7)
        LeetCodeStepEngine.advance(host);
        assertEquals(1, host.stepIndex);
        assertArrayEquals(new int[]{4, 7, 2, 6, 9, 1, 3}, host.workingValues);

        // Step 2: swap children of node 7 (6 and 9)
        LeetCodeStepEngine.advance(host);
        assertEquals(2, host.stepIndex);
        assertArrayEquals(new int[]{4, 7, 2, 9, 6, 1, 3}, host.workingValues);

        // Step 3: swap children of node 2 (1 and 3)
        LeetCodeStepEngine.advance(host);
        assertEquals(3, host.stepIndex);
        assertArrayEquals(new int[]{4, 7, 2, 9, 6, 3, 1}, host.workingValues);

        // Step 4: finish
        LeetCodeStepEngine.advance(host);
        assertTrue(host.playbackComplete);
    }

    @Test
    public void testNQueensStepEngineProgression() {
        MockRuntimeHost host = new MockRuntimeHost();
        host.techniqueId = "backtracking_n_queens";
        host.stepIndex = -1;
        host.matrix = new int[4][4];

        // Step 0: Should place Queen at (0, 0) and advance stepIndex to 1 without crashing on -1
        BacktrackingStepEngine.advance(host);
        assertEquals(1, host.stepIndex);
        assertEquals(1, host.matrix[0][0]);

        // Step 1: Conflict test at (1, 0) (same column)
        BacktrackingStepEngine.advance(host);
        assertEquals(2, host.stepIndex);
        assertEquals(-1, host.matrix[1][0]); // Conflict marked

        // Step 2: Previous conflict at (1, 0) should be cleared to 0, new conflict at (1, 1) marked
        BacktrackingStepEngine.advance(host);
        assertEquals(3, host.stepIndex);
        assertEquals(0, host.matrix[1][0]);
        assertEquals(-1, host.matrix[1][1]);

        // Advance through all remaining steps until playback completes
        while (!host.playbackComplete) {
            BacktrackingStepEngine.advance(host);
        }

        assertTrue(host.playbackComplete);
        // Final solution: Queens placed at (0,1), (1,3), (2,0), (3,2)
        assertEquals(1, host.matrix[0][1]);
        assertEquals(1, host.matrix[1][3]);
        assertEquals(1, host.matrix[2][0]);
        assertEquals(1, host.matrix[3][2]);
    }

    @Test
    public void testNQueensDynamicBoardSizesProduceValidSolutions() {
        int[] sizes = {4, 5, 6, 8};
        for (int n : sizes) {
            int[][] steps = BacktrackingStepEngine.getNQueensSteps(n);
            assertTrue("Steps for N=" + n + " must not be empty", steps != null && steps.length > 0);

            MockRuntimeHost host = new MockRuntimeHost();
            host.techniqueId = "backtracking_n_queens";
            host.stepIndex = -1;
            host.matrix = new int[n][n];

            while (!host.playbackComplete) {
                BacktrackingStepEngine.advance(host);
            }

            assertTrue("Playback must complete for N=" + n, host.playbackComplete);

            // Verify final board has exactly n queens and none conflict
            int queenCount = 0;
            java.util.List<int[]> queens = new java.util.ArrayList<>();
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    if (host.matrix[r][c] == 1) {
                        queenCount++;
                        queens.add(new int[]{r, c});
                    }
                }
            }
            assertEquals("Board must have exactly " + n + " queens", n, queenCount);

            // Verify no conflicts between placed queens
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    int[] q1 = queens.get(i);
                    int[] q2 = queens.get(j);
                    assertTrue("No same row", q1[0] != q2[0]);
                    assertTrue("No same col", q1[1] != q2[1]);
                    assertTrue("No diagonal attack", Math.abs(q1[0] - q2[0]) != Math.abs(q1[1] - q2[1]));
                }
            }
        }
    }

    @Test
    public void testSudokuStepEngineProgression() {
        MockRuntimeHost host = new MockRuntimeHost();
        host.techniqueId = "backtracking_sudoku";
        host.stepIndex = -1;
        host.matrix = BacktrackingStepEngine.clone2D(BacktrackingStepEngine.getInitialSudokuBoard(4, 1));
        host.matrixLabel = "Sudoku 4x4 Grid (Preset 1)";

        // Step 0: Should place or test first digit without crashing on -1
        BacktrackingStepEngine.advance(host);
        assertEquals(1, host.stepIndex);

        // Advance through all remaining steps until playback completes
        while (!host.playbackComplete) {
            BacktrackingStepEngine.advance(host);
        }

        assertTrue(host.playbackComplete);
        // Verify board is 4x4, full and valid
        assertEquals(4, host.matrix.length);
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                assertTrue("Cell (" + r + "," + c + ") must be filled > 0", host.matrix[r][c] > 0);
            }
        }
    }

    @Test
    public void testSudokuDynamicSolvingCompletes() {
        int[][] configs = {
            {4, 1},
            {4, 2},
            {9, 1},
            {9, 2}
        };

        for (int[] cfg : configs) {
            int size = cfg[0];
            int preset = cfg[1];
            int[][] steps = BacktrackingStepEngine.getSudokuSteps(size, preset);
            assertTrue("Steps for Sudoku " + size + "x" + size + " P" + preset + " must not be empty", steps != null && steps.length > 0);

            MockRuntimeHost host = new MockRuntimeHost();
            host.techniqueId = "backtracking_sudoku";
            host.stepIndex = -1;
            host.matrix = BacktrackingStepEngine.clone2D(BacktrackingStepEngine.getInitialSudokuBoard(size, preset));
            host.matrixLabel = "Sudoku " + size + "x" + size + " Grid (Preset " + preset + ")";

            while (!host.playbackComplete) {
                BacktrackingStepEngine.advance(host);
            }

            assertTrue("Sudoku " + size + "x" + size + " P" + preset + " must complete", host.playbackComplete);

            // Verify grid dimensions
            assertEquals(size, host.matrix.length);

            // Verify all cells filled with 1..size
            int subR = size == 9 ? 3 : 2;
            int subC = size == 9 ? 3 : 2;

            for (int r = 0; r < size; r++) {
                boolean[] rowSeen = new boolean[size + 1];
                for (int c = 0; c < size; c++) {
                    int val = host.matrix[r][c];
                    assertTrue("Cell must be between 1 and " + size + ", but was " + val, val >= 1 && val <= size);
                    assertTrue("Duplicate digit " + val + " in row " + r, !rowSeen[val]);
                    rowSeen[val] = true;
                }
            }

            for (int c = 0; c < size; c++) {
                boolean[] colSeen = new boolean[size + 1];
                for (int r = 0; r < size; r++) {
                    int val = host.matrix[r][c];
                    assertTrue("Duplicate digit " + val + " in col " + c, !colSeen[val]);
                    colSeen[val] = true;
                }
            }

            for (int br = 0; br < size; br += subR) {
                for (int bc = 0; bc < size; bc += subC) {
                    boolean[] boxSeen = new boolean[size + 1];
                    for (int r = 0; r < subR; r++) {
                        for (int c = 0; c < subC; c++) {
                            int val = host.matrix[br + r][bc + c];
                            assertTrue("Duplicate digit " + val + " in subgrid (" + br + "," + bc + ")", !boxSeen[val]);
                            boxSeen[val] = true;
                        }
                    }
                }
            }
        }
    }

    @Test
    public void testKnightsTourDynamicMovesAreValidKnightJumpsAndComplete() {
        int[][] configs = {
            {5, 1}, // 5x5 Corner
            {5, 2}, // 5x5 Center
            {6, 1}, // 6x6 Corner
            {6, 2}, // 6x6 Center
            {8, 1}, // 8x8 Corner
            {8, 2}  // 8x8 Center
        };

        for (int[] cfg : configs) {
            int n = cfg[0];
            int startPos = cfg[1];
            int total = n * n;
            int[][] moves = BacktrackingStepEngine.getKnightsTourMoves(n, startPos);
            assertTrue("Moves array must not be null", moves != null);
            assertEquals("Moves count must be " + total + " for " + n + "x" + n, total, moves.length);

            boolean[][] visited = new boolean[n][n];

            for (int i = 0; i < total; i++) {
                int r = moves[i][0];
                int c = moves[i][1];
                assertTrue("Move " + (i + 1) + " row " + r + " in bounds [0," + (n - 1) + "]", r >= 0 && r < n);
                assertTrue("Move " + (i + 1) + " col " + c + " in bounds [0," + (n - 1) + "]", c >= 0 && c < n);
                assertTrue("Square (" + r + "," + c + ") visited more than once on move " + (i + 1), !visited[r][c]);
                visited[r][c] = true;

                if (i > 0) {
                    int prevR = moves[i - 1][0];
                    int prevC = moves[i - 1][1];
                    int dr = Math.abs(r - prevR);
                    int dc = Math.abs(c - prevC);
                    boolean isLegalKnightJump = (dr == 1 && dc == 2) || (dr == 2 && dc == 1);
                    assertTrue("Step " + (i + 1) + " from (" + prevR + "," + prevC + ") to (" + r + "," + c +
                            ") must be a legal knight jump (dr=" + dr + ", dc=" + dc + ")", isLegalKnightJump);
                }
            }

            // Verify all squares on board were visited
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    assertTrue("Square (" + r + "," + c + ") must be visited", visited[r][c]);
                }
            }
        }
    }

    @Test
    public void testKnightsTourStepEngineProgression() {
        MockRuntimeHost host = new MockRuntimeHost();
        host.techniqueId = "backtracking_knights_tour";
        host.stepIndex = -1;
        host.matrix = BacktrackingStepEngine.getInitialKnightsTourBoard(5, 1);
        host.matrixLabel = "Knight's Tour 5x5 (Corner)";

        // Step 0: start
        BacktrackingStepEngine.advance(host);
        assertEquals(1, host.stepIndex);

        while (!host.playbackComplete) {
            BacktrackingStepEngine.advance(host);
        }

        assertTrue(host.playbackComplete);
        assertEquals(5, host.matrix.length);
        // All 25 squares must have 1..25
        boolean[] seen = new boolean[26];
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                int moveNum = host.matrix[r][c];
                assertTrue("Move number must be in [1, 25]", moveNum >= 1 && moveNum <= 25);
                assertTrue("Duplicate move number " + moveNum, !seen[moveNum]);
                seen[moveNum] = true;
            }
        }
    }

    @Test
    public void testMazeSolverDynamicStepGeneration() {
        int[] sizes = {4, 5, 6};
        int[] presets = {1, 2};

        for (int n : sizes) {
            for (int p : presets) {
                int[][] initial = BacktrackingStepEngine.getInitialMaze(n, p);
                int[][] steps = BacktrackingStepEngine.getMazeSteps(n, p);

                assertTrue("Steps must not be empty for size " + n + " preset " + p, steps.length > 0);

                // Initial cell must be (0,0)
                assertEquals(0, steps[0][0]);
                assertEquals(0, steps[0][1]);

                // Final step must reach exit (n-1, n-1)
                int[] lastStep = steps[steps.length - 1];
                assertEquals(n - 1, lastStep[0]);
                assertEquals(n - 1, lastStep[1]);
                assertEquals(2, lastStep[2]); // State 2 = path

                // Verify steps never traverse wall cells
                for (int[] s : steps) {
                    int r = s[0];
                    int c = s[1];
                    assertTrue("Row in bounds", r >= 0 && r < n);
                    assertTrue("Col in bounds", c >= 0 && c < n);
                    assertEquals("Step must not visit a wall obstacle", 0, initial[r][c]);
                }

                // Preset 2 should contain dead-end backtracks (state 3)
                if (p == 2) {
                    boolean hasDeadEnd = false;
                    for (int[] s : steps) {
                        if (s[2] == 3) {
                            hasDeadEnd = true;
                            break;
                        }
                    }
                    assertTrue("Preset 2 must exercise dead-end backtracking", hasDeadEnd);
                }
            }
        }
    }

    @Test
    public void testMazeSolverStepEngineProgression() {
        MockRuntimeHost host = new MockRuntimeHost();
        host.techniqueId = "backtracking_maze_solver";
        host.stepIndex = -1;
        host.matrix = BacktrackingStepEngine.getInitialMaze(5, 2);
        host.matrixLabel = "Rat in a Maze 5x5 (Preset 2)";

        BacktrackingStepEngine.advance(host);
        assertEquals(1, host.stepIndex);

        while (!host.playbackComplete) {
            BacktrackingStepEngine.advance(host);
        }

        assertTrue(host.playbackComplete);
        assertEquals(2, host.matrix[4][4]); // Exit reached
    }

    @Test
    public void testHamiltonianDynamicStepGeneration() {
        int[] nodeCounts = {4, 5, 6};
        int[] types = {1, 2}; // 1 = Cycle, 2 = Path

        for (int n : nodeCounts) {
            for (int t : types) {
                int[][] edges = BacktrackingStepEngine.getHamiltonianEdges(n);
                boolean[][] adj = new boolean[n][n];
                for (int[] e : edges) {
                    adj[e[0]][e[1]] = true;
                    adj[e[1]][e[0]] = true;
                }

                int[][] steps = BacktrackingStepEngine.getHamiltonianSteps(n, t);
                assertTrue("Hamiltonian steps must not be empty for n=" + n + " t=" + t, steps.length > 0);

                // Verify all forward edges exist in graph
                for (int[] s : steps) {
                    int u = s[0];
                    int v = s[1];
                    assertTrue("Edge (" + u + "," + v + ") must exist in topology", adj[u][v]);
                }

                // If cycle (type 1), final step must close back to origin node 0
                if (t == 1) {
                    int[] lastStep = steps[steps.length - 1];
                    assertEquals("Cycle must end with action 2 (cycle closure)", 2, lastStep[2]);
                    assertEquals("Cycle must close to node 0", 0, lastStep[1]);
                }
            }
        }
    }

    @Test
    public void testHamiltonianStepEngineProgression() {
        MockRuntimeHost host = new MockRuntimeHost();
        host.techniqueId = "bt_hamiltonian";
        host.stepIndex = -1;
        host.workingValues = new int[]{0, 1, 2, 3, 4};
        host.matrixLabel = "Hamiltonian Cycle (5 Vertices)";

        BacktrackingStepEngine.advance(host);
        assertEquals(1, host.stepIndex);

        while (!host.playbackComplete) {
            BacktrackingStepEngine.advance(host);
        }

        assertTrue(host.playbackComplete);
    }

    @Test
    public void testGraphColoringDynamicStepGeneration() {
        // Preset 1 = K4 (Chromatic number 4)
        int[][] stepsK4_m4 = BacktrackingStepEngine.getGraphColoringSteps(4, 1);
        assertTrue("K4 with m=4 must have steps", stepsK4_m4.length > 0);

        // Preset 2 = 5-Cycle (Chromatic number 3)
        int[][] steps5Cycle_m3 = BacktrackingStepEngine.getGraphColoringSteps(3, 2);
        assertTrue("5-Cycle with m=3 must have steps", steps5Cycle_m3.length > 0);

        // Verify valid coloring assignments have 0 adjacent conflicts
        int n = BacktrackingStepEngine.getColoringGraphNodeCount(2);
        int[][] edges = BacktrackingStepEngine.getColoringGraphEdges(2);
        int[] finalColors = new int[n];
        for (int[] s : steps5Cycle_m3) {
            int v = s[0];
            int c = s[1];
            int action = s[2];
            if (action == 1) {
                finalColors[v] = c;
            } else if (action == 2) {
                finalColors[v] = 0;
            }
        }

        // All vertices must be colored in 1..3
        for (int i = 0; i < n; i++) {
            assertTrue("Vertex " + i + " must be colored", finalColors[i] >= 1 && finalColors[i] <= 3);
        }

        // No adjacent vertex can have the same color
        for (int[] e : edges) {
            assertTrue("Adjacent vertices V" + e[0] + " and V" + e[1] + " must have different colors",
                finalColors[e[0]] != finalColors[e[1]]);
        }
    }

    @Test
    public void testGraphColoringStepEngineProgression() {
        MockRuntimeHost host = new MockRuntimeHost();
        host.techniqueId = "backtracking_graph_coloring";
        host.stepIndex = -1;
        host.workingValues = new int[5];
        host.matrixLabel = "Graph m-Coloring (m=3, Preset 2)";

        BacktrackingStepEngine.advance(host);
        assertEquals(1, host.stepIndex);

        while (!host.playbackComplete) {
            BacktrackingStepEngine.advance(host);
        }

        assertTrue(host.playbackComplete);
    }

    @Test
    public void testBacktrackingAlgorithmsHandleNegativeInitialStepIndex() {
        String[] algorithms = {
            "backtracking_n_queens",
            "bt_n_queens",
            "backtracking_sudoku",
            "bt_sudoku",
            "backtracking_knights_tour",
            "bt_knights_tour",
            "backtracking_maze_solver",
            "bt_maze",
            "backtracking_hamiltonian",
            "bt_hamiltonian",
            "backtracking_graph_coloring",
            "bt_graph_coloring"
        };

        for (String algoId : algorithms) {
            MockRuntimeHost host = new MockRuntimeHost();
            host.techniqueId = algoId;
            host.stepIndex = -1; // fragment reset state
            host.matrix = new int[4][4];

            // Must not throw ArrayIndexOutOfBoundsException on stepIndex == -1
            BacktrackingStepEngine.advance(host);
            assertTrue("Step index should advance from -1 for " + algoId, host.stepIndex >= 0);
        }
    }

    @Test
    public void testAllDynamicProgrammingAlgorithmsStepProgressionAndCompletion() {
        String[] dpAlgorithms = {
            "dp_knapsack",
            "dp_lcs",
            "dp_lis",
            "dp_coin_change",
            "dp_matrix_chain",
            "dp_edit_distance",
            "dp_rod_cutting",
            "dp_subset_sum",
            "dp_kadane"
        };

        for (String dpId : dpAlgorithms) {
            for (int preset = 1; preset <= 3; preset++) {
                MockRuntimeHost host = new MockRuntimeHost();
                host.techniqueId = dpId;
                host.target = preset;
                host.stepIndex = -1;

                int[][] initMat = DpStepEngine.getInitialDpMatrix(dpId, preset);
                assertTrue("Initial DP matrix should have valid dimensions for " + dpId + " preset " + preset,
                    initMat != null && initMat.length > 0 && initMat[0].length > 0);

                int totalEstimatedSteps = DpStepEngine.getDpTotalSteps(dpId, preset);
                assertTrue("Estimated steps should be > 0 for " + dpId + " preset " + preset, totalEstimatedSteps > 0);

                // Advance once
                DpStepEngine.advance(host);
                assertEquals("First step index should be 0 for " + dpId + " preset " + preset, 0, host.stepIndex);
                assertTrue("Matrix should be non-empty after advancing " + dpId, host.matrix != null && host.matrix.length > 0);

                // Run until completion or max safeguard
                int safety = 0;
                while (!host.playbackComplete && safety < totalEstimatedSteps + 15) {
                    DpStepEngine.advance(host);
                    safety++;
                }

                assertTrue("Algorithm " + dpId + " with preset " + preset + " should complete playback", host.playbackComplete);
            }
        }
    }

    @Test
    public void testDpHeadersAndDescriptions() {
        String[] dpAlgorithms = {
            "dp_knapsack", "dp_lcs", "dp_lis", "dp_coin_change", "dp_matrix_chain",
            "dp_edit_distance", "dp_rod_cutting", "dp_subset_sum", "dp_kadane"
        };

        for (String id : dpAlgorithms) {
            for (int p = 1; p <= 3; p++) {
                String desc = DpStepEngine.getPresetDescription(id, p);
                assertTrue("Preset description should be informative for " + id + " preset " + p, desc != null && !desc.isEmpty());
            }

            String[] colHeaders = DpStepEngine.getColumnHeaders(id, 5);
            assertTrue("Column headers should be non-empty for " + id, colHeaders != null && colHeaders.length == 5);

            String[] rowHeaders = DpStepEngine.getRowHeaders(id, 2);
            assertTrue("Row headers should be non-empty for " + id, rowHeaders != null && rowHeaders.length == 2);
        }
    }

    @Test
    public void testAllStringPatternAlgorithmsStepProgressionAndCompletion() {
        String[] stringAlgorithms = {
            "string_kmp",
            "string_rabin_karp",
            "string_boyer_moore",
            "string_z_algorithm",
            "string_z_algo",
            "string_aho_corasick",
            "string_suffix_array",
            "string_manacher"
        };

        for (String strId : stringAlgorithms) {
            for (int preset = 1; preset <= 3; preset++) {
                MockRuntimeHost host = new MockRuntimeHost();
                host.techniqueId = strId;
                host.patternPreset = preset;
                host.patternText = StringPatternStepEngine.getPresetText(strId, preset);
                host.patternQuery = StringPatternStepEngine.getPresetQuery(strId, preset);
                host.stepIndex = -1;

                int totalEstimatedSteps = StringPatternStepEngine.getStringPatternTotalSteps(
                    strId, host.patternText, host.patternQuery, preset
                );
                assertTrue("Estimated steps should be > 0 for " + strId + " preset " + preset, totalEstimatedSteps > 0);

                // Advance initial step
                StringPatternStepEngine.advance(host);
                assertTrue("Step index should advance from -1 for " + strId, host.stepIndex >= 0);

                int safety = 0;
                while (!host.playbackComplete && safety < totalEstimatedSteps + 20) {
                    StringPatternStepEngine.advance(host);
                    safety++;
                }

                assertTrue("Algorithm " + strId + " with preset " + preset + " should complete playback", host.playbackComplete);
            }
        }
    }

    @Test
    public void testStringPatternAlgorithmsHelpers() {
        // Test KMP LPS computation
        int[] lps = StringPatternStepEngine.computeLps("ABABCABAB");
        assertArrayEquals(new int[]{0, 0, 1, 2, 0, 1, 2, 3, 4}, lps);

        // Test Z-Algorithm
        int[] z = StringPatternStepEngine.computeZArray("aabacaabaad");
        assertEquals(11, z[0]); // full string match
        assertEquals(1, z[1]);
        assertEquals(0, z[4]); // 'c' does not match 'a'
        assertEquals(4, z[5]); // "aaba" matches prefix "aaba"

        // Test Suffix Array
        int[] sa = StringPatternStepEngine.computeSuffixArray("banana$");
        assertArrayEquals(new int[]{6, 5, 3, 1, 0, 4, 2}, sa);

        // Test Kasai LCP
        int[] lcp = StringPatternStepEngine.computeKasaiLcp("banana$", sa);
        assertArrayEquals(new int[]{0, 1, 3, 0, 0, 2, 0}, lcp);

        // Test Manacher
        int[] p = StringPatternStepEngine.computeManacher("abacaba");
        assertTrue("Manacher array should have odd transformed length", p.length == 15);
        // Center of "abacaba" at index 7 (char 'c') has radius 7
        assertEquals(7, p[7]);
    }

    @Test
    public void graph_presets_returnValidEdgesAndWeights() {
        int[][] p1 = GraphStepEngine.getGraphEdges(1, 5);
        assertTrue("Preset 1 should have edges", p1.length > 0);
        assertEquals(7, p1.length);

        int[][] p2 = GraphStepEngine.getGraphEdges(2, 6);
        assertTrue("Preset 2 should have edges", p2.length > 0);
        assertEquals(9, p2.length);

        int[][] p3 = GraphStepEngine.getGraphEdges(3, 5);
        assertTrue("Preset 3 should have edges", p3.length > 0);
        assertEquals(6, p3.length);

        // Edge weights
        int w01_p1 = GraphStepEngine.getGraphEdgeWeight(1, 0, 1);
        assertEquals(4, w01_p1);

        int w01_p2 = GraphStepEngine.getGraphEdgeWeight(2, 0, 1);
        assertEquals(3, w01_p2);

        int w01_p3 = GraphStepEngine.getGraphEdgeWeight(3, 0, 1);
        assertEquals(5, w01_p3);

        // Description
        String desc1 = GraphStepEngine.getPresetDescription("breadth_first_search", 1, 0, 5);
        assertTrue(desc1.contains("Classic DAG"));

        String desc2 = GraphStepEngine.getPresetDescription("graph_floyd_warshall", 2, 0, 6);
        assertTrue(desc2.contains("Dense Mesh"));

        String desc3 = GraphStepEngine.getPresetDescription("graph_bridges_articulation", 3, 0, 5);
        assertTrue(desc3.contains("Star Hub"));
    }

    @Test
    public void graph_traversal_stepTransitions() {
        MockRuntimeHost host = new MockRuntimeHost();
        host.techniqueId = "breadth_first_search";
        host.workingValues = new int[]{0, 1, 2, 3, 4};
        host.setTraversalOrder(new int[]{0, 1, 3, 2, 4});
        host.stepIndex = -1;

        GraphStepEngine.advance(host);
        assertEquals(0, host.stepIndex);

        GraphStepEngine.advance(host);
        assertEquals(1, host.stepIndex);

        for (int i = 2; i <= 5; i++) {
            GraphStepEngine.advance(host);
        }
        assertTrue("Graph traversal should finish when reaching end of order", host.playbackComplete);
    }

    @Test
    public void graph_expanded_allAlgorithmsCompleteCleanly() {
        String[] expanded = {
            "graph_bellman_ford",
            "graph_floyd_warshall",
            "graph_prim",
            "graph_kruskal",
            "graph_tarjan_scc",
            "graph_kosaraju_scc",
            "graph_bridges_articulation",
            "graph_astar",
            "graph_edmonds_karp",
            "graph_johnson"
        };

        for (String tech : expanded) {
            MockRuntimeHost host = new MockRuntimeHost();
            host.techniqueId = tech;
            host.stepIndex = 0;
            int totalSteps = GraphExpandedStepEngine.getTotalSteps(tech);
            assertTrue("Total steps should be positive for " + tech, totalSteps > 0);

            for (int step = 1; step <= totalSteps; step++) {
                GraphExpandedStepEngine.advance(host);
            }
            assertTrue("Algorithm " + tech + " should be completed after " + totalSteps + " steps", host.playbackComplete);
        }
    }

    @Test
    public void advancedDs_all11StructuresCompleteCleanly() {
        String[] algorithms = {
            "ds_trie", "adv_trie",
            "ds_segment_tree", "adv_segment_tree",
            "ds_persistent_segment_tree", "adv_persistent_st",
            "ds_fenwick_tree", "adv_fenwick_tree",
            "ds_avl_tree", "adv_avl_tree",
            "ds_red_black_tree", "adv_red_black_tree",
            "ds_splay_tree", "adv_splay_tree",
            "ds_b_tree", "adv_b_tree",
            "ds_disjoint_set", "adv_dsu",
            "ds_bloom_filter", "adv_bloom_filter",
            "ds_lru_cache", "adv_lru_cache"
        };

        for (String tech : algorithms) {
            MockRuntimeHost host = new MockRuntimeHost();
            host.techniqueId = tech;
            host.stepIndex = 0;
            int totalSteps = AdvancedDsStepEngine.getTotalSteps(tech, 1);
            assertTrue("Total steps should be positive for " + tech, totalSteps > 0);

            for (int step = 1; step <= totalSteps; step++) {
                AdvancedDsStepEngine.advance(host);
            }
            assertTrue("Structure " + tech + " should be completed after " + totalSteps + " steps", host.playbackComplete);
        }
    }

    @Test
    public void advancedDs_presetsAndDescriptionsAreValid() {
        String[] algorithms = {
            "ds_trie",
            "ds_segment_tree",
            "ds_persistent_segment_tree",
            "ds_fenwick_tree",
            "ds_avl_tree",
            "ds_red_black_tree",
            "ds_splay_tree",
            "ds_b_tree",
            "ds_disjoint_set",
            "ds_bloom_filter",
            "ds_lru_cache"
        };

        for (String tech : algorithms) {
            for (int preset = 1; preset <= 3; preset++) {
                String desc = AdvancedDsStepEngine.getPresetDescription(tech, preset);
                assertNotNull("Description should not be null for " + tech + " preset " + preset, desc);
                assertTrue("Description should have content for " + tech + " preset " + preset, desc.length() > 10);

                String input = AdvancedDsStepEngine.getDefaultInput(tech, preset);
                assertNotNull("Default input should not be null for " + tech + " preset " + preset, input);
                assertTrue("Default input should have content for " + tech + " preset " + preset, input.length() > 0);

                int steps = AdvancedDsStepEngine.getTotalSteps(tech, preset);
                assertTrue("Total steps must be positive for " + tech + " preset " + preset, steps > 0);
            }
        }
    }

    @Test
    public void advancedDs_visualizerEngineSupportsAll11AlgorithmsAndAliases() {
        String[] algorithms = {
            "ds_trie", "adv_trie",
            "ds_segment_tree", "adv_segment_tree",
            "ds_persistent_segment_tree", "adv_persistent_st",
            "ds_fenwick_tree", "adv_fenwick_tree",
            "ds_avl_tree", "adv_avl_tree",
            "ds_red_black_tree", "adv_red_black_tree",
            "ds_splay_tree", "adv_splay_tree",
            "ds_b_tree", "adv_b_tree",
            "ds_disjoint_set", "adv_dsu",
            "ds_bloom_filter", "adv_bloom_filter",
            "ds_lru_cache", "adv_lru_cache"
        };

        for (String tech : algorithms) {
            assertTrue("AdvancedDsVisualizerEngine must support " + tech, com.dsavisualizer.app.ui.view.AdvancedDsVisualizerEngine.isSupportedTechnique(tech));
            assertTrue("VisualizationTechniqueRegistry must recognize " + tech, com.dsavisualizer.app.ui.visualization.VisualizationTechniqueRegistry.isAdvancedDs(tech));
        }
    }

    @Test
    public void advancedDs_allPresetsAndCustomInputsAdvanceCleanly() {
        String[] algorithms = {
            "ds_trie", "adv_trie",
            "ds_segment_tree", "adv_segment_tree",
            "ds_persistent_segment_tree", "adv_persistent_st",
            "ds_fenwick_tree", "adv_fenwick_tree",
            "ds_avl_tree", "adv_avl_tree",
            "ds_red_black_tree", "adv_red_black_tree",
            "ds_splay_tree", "adv_splay_tree",
            "ds_b_tree", "adv_b_tree",
            "ds_disjoint_set", "adv_dsu",
            "ds_bloom_filter", "adv_bloom_filter",
            "ds_lru_cache", "adv_lru_cache"
        };

        for (String tech : algorithms) {
            for (int preset = 1; preset <= 3; preset++) {
                MockRuntimeHost host = new MockRuntimeHost();
                host.techniqueId = tech;
                host.stepIndex = 0;
                String defaultInput = AdvancedDsStepEngine.getDefaultInput(tech, preset);
                host.setExpressionInput(defaultInput);
                int totalSteps = AdvancedDsStepEngine.getTotalSteps(tech, preset);

                for (int step = 1; step <= totalSteps; step++) {
                    AdvancedDsStepEngine.advance(host);
                    assertNotNull("Status message should be bound", host.lastStatus);
                    assertTrue("Status message should not be empty", host.lastStatus.length() > 0);
                }
                assertTrue("Technique " + tech + " preset " + preset + " should complete after " + totalSteps + " steps", host.playbackComplete);
            }
        }
    }

    @Test
    public void advancedDs_initialStepIndexNegativeOneAdvancesStepByStep() {
        String[] algorithms = {
            "ds_trie", "adv_trie",
            "ds_segment_tree", "adv_segment_tree",
            "ds_persistent_segment_tree", "adv_persistent_st",
            "ds_fenwick_tree", "adv_fenwick_tree",
            "ds_avl_tree", "adv_avl_tree",
            "ds_red_black_tree", "adv_red_black_tree",
            "ds_splay_tree", "adv_splay_tree",
            "ds_b_tree", "adv_b_tree",
            "ds_disjoint_set", "adv_dsu",
            "ds_bloom_filter", "adv_bloom_filter",
            "ds_lru_cache", "adv_lru_cache"
        };

        for (String tech : algorithms) {
            MockRuntimeHost host = new MockRuntimeHost();
            host.techniqueId = tech;
            host.stepIndex = -1;
            int totalSteps = AdvancedDsStepEngine.getTotalSteps(tech, 1);

            AdvancedDsStepEngine.advance(host);
            assertEquals("Structure " + tech + " step 1 should set stepIndex to 1", 1, host.stepIndex);
            assertFalse("Structure " + tech + " should NOT finish on step 1", host.playbackComplete);

            for (int step = 2; step <= totalSteps; step++) {
                AdvancedDsStepEngine.advance(host);
                assertEquals("Structure " + tech + " should be on step " + step, step, host.stepIndex);
            }
            assertTrue("Structure " + tech + " should complete after " + totalSteps + " steps", host.playbackComplete);
        }
    }

    @Test
    public void advancedDs_customInputReflectsDynamicallyInFenwickTree() {
        MockRuntimeHost host = new MockRuntimeHost();
        host.techniqueId = "ds_fenwick_tree";
        host.stepIndex = 0;
        host.setExpressionInput("5, 10, 15, 20, 25, 30");

        AdvancedDsStepEngine.advance(host);
        assertTrue(host.lastStatus.contains("5"));
        assertTrue(host.lastStatus.contains("10"));

        AdvancedDsStepEngine.advance(host);
        assertTrue(host.lastStatus.contains("15")); // 5 + 10 = 15

        AdvancedDsStepEngine.advance(host);
        AdvancedDsStepEngine.advance(host);
        AdvancedDsStepEngine.advance(host);
        AdvancedDsStepEngine.advance(host);
        assertTrue(host.playbackComplete);
    }

    @Test
    public void advancedDs_customInputReflectsDynamicallyInBloomFilter() {
        MockRuntimeHost host = new MockRuntimeHost();
        host.techniqueId = "ds_bloom_filter";
        host.stepIndex = 0;
        host.setExpressionInput("google, android, kotlin");

        AdvancedDsStepEngine.advance(host);
        assertTrue(host.lastStatus.contains("google"));
        assertTrue(host.lastStatus.contains("android"));

        AdvancedDsStepEngine.advance(host);
        assertTrue(host.lastStatus.contains("google"));

        AdvancedDsStepEngine.advance(host);
        assertTrue(host.lastStatus.contains("android"));

        AdvancedDsStepEngine.advance(host);
        assertTrue(host.lastStatus.contains("google"));

        AdvancedDsStepEngine.advance(host);
        assertTrue(host.lastStatus.contains("kotlin"));
        assertTrue(host.playbackComplete);
    }

    @Test
    public void advancedDs_customInputReflectsDynamicallyInLruCache() {
        MockRuntimeHost host = new MockRuntimeHost();
        host.techniqueId = "ds_lru_cache";
        host.stepIndex = 0;
        host.setExpressionInput("100, 200, 300, 400");

        AdvancedDsStepEngine.advance(host);
        assertTrue(host.lastStatus.contains("100"));

        AdvancedDsStepEngine.advance(host);
        assertTrue(host.lastStatus.contains("200"));
        assertTrue(host.lastStatus.contains("300"));

        AdvancedDsStepEngine.advance(host);
        assertTrue(host.lastStatus.contains("200"));

        AdvancedDsStepEngine.advance(host);
        assertTrue(host.lastStatus.contains("400"));
        assertTrue(host.lastStatus.contains("100"));

        AdvancedDsStepEngine.advance(host);
        assertTrue(host.lastStatus.contains("400"));
        assertTrue(host.playbackComplete);
    }

    @Test
    public void advancedDs_customInputReflectsDynamicallyInSegmentTree() {
        MockRuntimeHost host = new MockRuntimeHost();
        host.techniqueId = "ds_segment_tree";
        host.stepIndex = 0;
        host.setExpressionInput("10, 20, 30, 40");

        AdvancedDsStepEngine.advance(host);
        assertTrue(host.lastStatus.contains("10"));
        assertTrue(host.lastStatus.contains("40"));

        AdvancedDsStepEngine.advance(host);
        assertTrue(host.lastStatus.contains("30")); // 10 + 20 = 30

        AdvancedDsStepEngine.advance(host);
        assertTrue(host.lastStatus.contains("70")); // 30 + 40 = 70

        AdvancedDsStepEngine.advance(host);
        assertTrue(host.lastStatus.contains("100")); // 30 + 70 = 100
        assertTrue(host.workingValues[0] == 100);

        AdvancedDsStepEngine.advance(host);
        AdvancedDsStepEngine.advance(host);
        assertTrue(host.playbackComplete);
    }

    @Test
    public void specialized_all10AlgorithmsCompleteCleanly() {
        String[] algorithms = {
            "spec_convex_hull",
            "spec_closest_pair",
            "spec_line_intersection",
            "spec_fft",
            "spec_karatsuba",
            "spec_sieve",
            "spec_miller_rabin",
            "spec_extended_euclidean",
            "spec_crt",
            "spec_activity_selection"
        };

        for (String tech : algorithms) {
            MockRuntimeHost host = new MockRuntimeHost();
            host.techniqueId = tech;
            host.stepIndex = 0;

            for (int step = 1; step <= 4; step++) {
                SpecializedStepEngine.advance(host);
                assertEquals("Step index should match step count for " + tech, step, host.stepIndex);
            }
            assertTrue("Step 4 should mark playback complete for " + tech, host.playbackComplete);
        }
    }

    @Test
    public void specialized_presetsAndDescriptionsAreValid() {
        String[] algorithms = {
            "spec_convex_hull",
            "spec_closest_pair",
            "spec_line_intersection",
            "spec_fft",
            "spec_karatsuba",
            "spec_sieve",
            "spec_miller_rabin",
            "spec_extended_euclidean",
            "spec_crt",
            "spec_activity_selection"
        };

        for (String tech : algorithms) {
            for (int preset = 1; preset <= 3; preset++) {
                String desc = SpecializedStepEngine.getPresetDescription(tech, preset);
                assertNotNull("Description should not be null for " + tech + " preset " + preset, desc);
                assertTrue("Description should have content for " + tech + " preset " + preset, desc.length() > 10);

                String input = SpecializedStepEngine.getDefaultInput(tech, preset);
                assertNotNull("Default input should not be null for " + tech + " preset " + preset, input);
                assertTrue("Default input should have content for " + tech + " preset " + preset, input.length() > 0);

                int steps = SpecializedStepEngine.getTotalSteps(tech, preset);
                assertTrue("Total steps must be positive for " + tech + " preset " + preset, steps > 0);
            }
        }
    }

    @Test
    public void specialized_visualizerEngineSupportsAll10AlgorithmsAndAliases() {
        String[] algorithms = {
            "spec_convex_hull",
            "spec_closest_pair",
            "spec_line_intersection",
            "spec_fft", "spec_fast_fourier_transform",
            "spec_karatsuba",
            "spec_sieve", "spec_sieve_eratosthenes",
            "spec_miller_rabin",
            "spec_extended_euclidean", "spec_euclidean_gcd",
            "spec_crt", "spec_chinese_remainder",
            "spec_activity_selection"
        };

        for (String tech : algorithms) {
            assertTrue("SpecializedVisualizerEngine must support " + tech, com.dsavisualizer.app.ui.view.SpecializedVisualizerEngine.isSupportedTechnique(tech));
            assertTrue("VisualizationTechniqueRegistry must recognize " + tech, com.dsavisualizer.app.ui.visualization.VisualizationTechniqueRegistry.isSpecialized(tech));
        }
    }

    @Test
    public void huffman_presetsAndDescriptionsAreValid() {
        for (int preset = 1; preset <= 3; preset++) {
            String desc = HuffmanStepEngine.getPresetDescription(preset);
            assertNotNull("Description should not be null for preset " + preset, desc);
            assertTrue("Description should have content for preset " + preset, desc.length() > 10);

            String input = HuffmanStepEngine.getDefaultInput(preset);
            assertNotNull("Default input should not be null for preset " + preset, input);
            assertTrue("Default input should have content for preset " + preset, input.length() > 0);

            int[] weights = HuffmanStepEngine.getDefaultWeights(preset);
            assertNotNull("Default weights should not be null for preset " + preset, weights);
            assertTrue("Default weights length >= 2 for preset " + preset, weights.length >= 2);

            int totalSteps = HuffmanStepEngine.getTotalSteps(weights);
            assertEquals("Total steps should equal weights length for preset " + preset, weights.length, totalSteps);
        }
    }

    @Test
    public void huffman_stepExecutionMergesMinWeights() {
        MockRuntimeHost host = new MockRuntimeHost();
        host.techniqueId = "huffman_algorithm";
        host.stepIndex = -1;
        host.setWorkingValues(new int[]{5, 9, 12, 13, 16, 45});

        // Step 0: initialization
        HuffmanStepEngine.step(host);
        assertEquals(0, host.stepIndex);
        int[] q0 = host.getHuffmanQueueWeights();
        assertNotNull(q0);
        assertEquals(6, q0.length);

        // Step 1: merge 5 and 9 -> 14. Remaining queue: [12, 13, 14, 16, 45]
        HuffmanStepEngine.step(host);
        assertEquals(1, host.stepIndex);
        assertEquals(14, host.getHuffmanMergedWeight());
        int[] q1 = host.getHuffmanQueueWeights();
        assertEquals(5, q1.length);
        assertEquals(12, q1[0]);
        assertEquals(13, q1[1]);

        // Step 2: merge 12 and 13 -> 25. Remaining queue: [14, 16, 25, 45]
        HuffmanStepEngine.step(host);
        assertEquals(2, host.stepIndex);
        assertEquals(25, host.getHuffmanMergedWeight());
        int[] q2 = host.getHuffmanQueueWeights();
        assertEquals(4, q2.length);
        assertEquals(14, q2[0]);
        assertEquals(16, q2[1]);

        // Step 3: merge 14 and 16 -> 30. Remaining queue: [25, 30, 45]
        HuffmanStepEngine.step(host);
        assertEquals(3, host.stepIndex);
        assertEquals(30, host.getHuffmanMergedWeight());
        int[] q3 = host.getHuffmanQueueWeights();
        assertEquals(3, q3.length);

        // Step 4: merge 25 and 30 -> 55. Remaining queue: [45, 55]
        HuffmanStepEngine.step(host);
        assertEquals(4, host.stepIndex);
        assertEquals(55, host.getHuffmanMergedWeight());
        int[] q4 = host.getHuffmanQueueWeights();
        assertEquals(2, q4.length);

        // Step 5: merge 45 and 55 -> 100. Remaining queue: [100], completed!
        HuffmanStepEngine.step(host);
        assertEquals(5, host.stepIndex);
        assertEquals(100, host.getHuffmanMergedWeight());
        int[] q5 = host.getHuffmanQueueWeights();
        assertEquals(1, q5.length);
        assertEquals(100, q5[0]);
        assertTrue("Playback should finish when root reached", host.playbackComplete);
    }

    @Test
    public void huffman_customWeightsExecution() {
        MockRuntimeHost host = new MockRuntimeHost();
        host.techniqueId = "huffman_algorithm";
        host.stepIndex = -1;
        int[] customWeights = new int[]{10, 20, 30};
        host.setWorkingValues(customWeights);
        HuffmanStepEngine.initializePreview(host, customWeights);

        int[] q = host.getHuffmanQueueWeights();
        assertNotNull(q);
        assertEquals(3, q.length);
        assertEquals(10, q[0]);
        assertEquals(20, q[1]);
        assertEquals(30, q[2]);

        // Step 0: initialization
        HuffmanStepEngine.step(host);
        assertEquals(0, host.stepIndex);

        // Step 1: Merge 10 and 20 -> 30. Remaining queue: [30, 30]
        HuffmanStepEngine.step(host);
        assertEquals(1, host.stepIndex);
        assertEquals(30, host.getHuffmanMergedWeight());
        assertEquals(2, host.getHuffmanQueueWeights().length);

        // Step 2: Merge 30 and 30 -> 60. Remaining queue: [60], finished!
        HuffmanStepEngine.step(host);
        assertEquals(2, host.stepIndex);
        assertEquals(60, host.getHuffmanMergedWeight());
        assertEquals(1, host.getHuffmanQueueWeights().length);
        assertTrue(host.playbackComplete);
    }

    @Test
    public void avlTree_customInputReflectedInStatusMessages() {
        MockRuntimeHost host = new MockRuntimeHost();
        host.techniqueId = "avl_tree";
        host.stepIndex = -1;
        int[] customVals = new int[]{80, 50, 95, 25, 60};
        host.setWorkingValues(customVals);

        AvlTreeStepEngine.advance(host);
        assertEquals(0, host.stepIndex);
        assertTrue("Status must include inserted value 25", host.lastStatus.contains("25"));
        assertTrue("Status must include parent value 50", host.lastStatus.contains("50"));

        AvlTreeStepEngine.advance(host);
        assertEquals(1, host.stepIndex);
        assertTrue("Status must include root value 80", host.lastStatus.contains("80"));
        assertTrue("Status must include pivot value 50", host.lastStatus.contains("50"));

        AvlTreeStepEngine.advance(host);
        assertTrue(host.playbackComplete);
        assertTrue("Status must confirm AVL rotation completed", host.lastStatus.contains("AVL rotation completed"));
    }

    @Test
    public void advancedDsAvlTree_customLLAndLRRotations() {
        // Test LL Imbalance
        MockRuntimeHost hostLL = new MockRuntimeHost();
        hostLL.techniqueId = "ds_avl_tree";
        hostLL.setExpressionInput("30, 20, 10");
        hostLL.stepIndex = 0;

        // Step 1: Root 30
        AdvancedDsStepEngine.advance(hostLL);
        assertEquals(1, hostLL.stepIndex);
        assertTrue(hostLL.lastStatus.contains("30"));

        // Step 2: Insert 20 (left)
        AdvancedDsStepEngine.advance(hostLL);
        assertEquals(2, hostLL.stepIndex);
        assertTrue(hostLL.lastStatus.contains("20"));

        // Step 3: Insert 10 -> LL Heavy Imbalance
        AdvancedDsStepEngine.advance(hostLL);
        assertEquals(3, hostLL.stepIndex);
        assertTrue(hostLL.lastStatus.contains("Left-Left") || hostLL.lastStatus.contains("LL"));

        // Step 4: Rotation prep (Right Rotation)
        AdvancedDsStepEngine.advance(hostLL);
        assertEquals(4, hostLL.stepIndex);
        assertTrue(hostLL.lastStatus.contains("Right Rotation"));

        // Step 5: Finished with new root 20, left 10, right 30
        AdvancedDsStepEngine.advance(hostLL);
        assertTrue(hostLL.playbackComplete);
        assertArrayEquals(new int[]{20, 10, 30}, hostLL.getWorkingValues());

        // Test LR Imbalance
        MockRuntimeHost hostLR = new MockRuntimeHost();
        hostLR.techniqueId = "ds_avl_tree";
        hostLR.setExpressionInput("30, 10, 20");
        hostLR.stepIndex = 0;

        AdvancedDsStepEngine.advance(hostLR); // Step 1
        AdvancedDsStepEngine.advance(hostLR); // Step 2
        AdvancedDsStepEngine.advance(hostLR); // Step 3
        assertTrue(hostLR.lastStatus.contains("Left-Right") || hostLR.lastStatus.contains("LR"));

        AdvancedDsStepEngine.advance(hostLR); // Step 4
        assertTrue(hostLR.lastStatus.contains("Double Rotation"));

        AdvancedDsStepEngine.advance(hostLR); // Step 5
        assertTrue(hostLR.playbackComplete);
        assertArrayEquals(new int[]{20, 10, 30}, hostLR.getWorkingValues());
    }

    @Test
    public void advancedDsAvlTree_customRLRotation() {
        MockRuntimeHost hostRL = new MockRuntimeHost();
        hostRL.techniqueId = "ds_avl_tree";
        hostRL.setExpressionInput("10, 30, 20");
        hostRL.stepIndex = 0;

        AdvancedDsStepEngine.advance(hostRL); // Step 1: Root 10
        assertEquals(1, hostRL.stepIndex);
        assertTrue(hostRL.lastStatus.contains("10"));

        AdvancedDsStepEngine.advance(hostRL); // Step 2: Insert 30
        assertEquals(2, hostRL.stepIndex);
        assertTrue(hostRL.lastStatus.contains("30"));

        AdvancedDsStepEngine.advance(hostRL); // Step 3: Insert 20 -> RL Imbalance
        assertEquals(3, hostRL.stepIndex);
        assertTrue(hostRL.lastStatus.contains("Right-Left") || hostRL.lastStatus.contains("RL"));

        AdvancedDsStepEngine.advance(hostRL); // Step 4: Double Rotation (RL)
        assertEquals(4, hostRL.stepIndex);
        assertTrue(hostRL.lastStatus.contains("Double Rotation (RL)"));

        AdvancedDsStepEngine.advance(hostRL); // Step 5: Finished
        assertTrue(hostRL.playbackComplete);
        assertArrayEquals(new int[]{20, 10, 30}, hostRL.getWorkingValues());
    }

    @Test
    public void avlTree_allPresetsAndRotations() {
        // Preset 1: RR (10, 20, 30) -> finishes with balanced [20, 10, 30]
        MockRuntimeHost hostRR = new MockRuntimeHost();
        hostRR.techniqueId = "avl_tree";
        hostRR.setWorkingValues(new int[]{10, 20, 30});
        hostRR.stepIndex = -1;
        AvlTreeStepEngine.advance(hostRR);
        assertEquals(0, hostRR.stepIndex);
        AvlTreeStepEngine.advance(hostRR);
        assertEquals(1, hostRR.stepIndex);
        assertTrue(hostRR.lastStatus.contains("RR") || hostRR.lastStatus.contains("left rotation"));
        AvlTreeStepEngine.advance(hostRR);
        assertTrue(hostRR.playbackComplete);
        assertArrayEquals(new int[]{20, 10, 30}, hostRR.getWorkingValues());

        // Preset 2: LL (30, 20, 10) -> finishes with balanced [20, 10, 30]
        MockRuntimeHost hostLL = new MockRuntimeHost();
        hostLL.techniqueId = "avl_tree";
        hostLL.setWorkingValues(new int[]{30, 20, 10});
        hostLL.stepIndex = -1;
        AvlTreeStepEngine.advance(hostLL);
        assertEquals(0, hostLL.stepIndex);
        AvlTreeStepEngine.advance(hostLL);
        assertEquals(1, hostLL.stepIndex);
        assertTrue(hostLL.lastStatus.contains("LL") || hostLL.lastStatus.contains("right rotation"));
        AvlTreeStepEngine.advance(hostLL);
        assertTrue(hostLL.playbackComplete);
        assertArrayEquals(new int[]{20, 10, 30}, hostLL.getWorkingValues());

        // Preset 3: LR (30, 10, 20) -> finishes with balanced [20, 10, 30]
        MockRuntimeHost hostLR = new MockRuntimeHost();
        hostLR.techniqueId = "avl_tree";
        hostLR.setWorkingValues(new int[]{30, 10, 20});
        hostLR.stepIndex = -1;
        AvlTreeStepEngine.advance(hostLR);
        AvlTreeStepEngine.advance(hostLR);
        assertTrue(hostLR.lastStatus.contains("LR") || hostLR.lastStatus.contains("double rotation"));
        AvlTreeStepEngine.advance(hostLR);
        assertTrue(hostLR.playbackComplete);
        assertArrayEquals(new int[]{20, 10, 30}, hostLR.getWorkingValues());

        // Preset 4: RL (10, 30, 20) -> finishes with balanced [20, 10, 30]
        MockRuntimeHost hostRL = new MockRuntimeHost();
        hostRL.techniqueId = "avl_tree";
        hostRL.setWorkingValues(new int[]{10, 30, 20});
        hostRL.stepIndex = -1;
        AvlTreeStepEngine.advance(hostRL);
        AvlTreeStepEngine.advance(hostRL);
        assertTrue(hostRL.lastStatus.contains("RL") || hostRL.lastStatus.contains("double rotation"));
        AvlTreeStepEngine.advance(hostRL);
        assertTrue(hostRL.playbackComplete);
        assertArrayEquals(new int[]{20, 10, 30}, hostRL.getWorkingValues());

        // Total steps check
        assertEquals(3, AvlTreeStepEngine.getTotalSteps(new int[]{10, 20, 30}));
    }


    // Lightweight mock host for isolated unit tests
    private static class MockRuntimeHost implements VisualizationRuntimeHost {
        String techniqueId = "";
        int stepIndex = -1;
        int activePrimary = -1;
        int activeSecondary = -1;
        boolean playbackComplete = false;
        int[] huffmanQueue = new int[0];
        int huffmanMerged = -1;
        int[][] matrix = new int[0][0];
        int matrixK = -1;
        int matrixRow = -1;
        int matrixCol = -1;

        int[] workingValues = new int[]{10, 20, 30};
        int target = 0;

        String patternText = "";
        String patternQuery = "";
        int patternPreset = 1;
        int patternWindowStart = -1;
        int patternMatchIndex = -1;
        boolean patternFound = false;
        int[] auxArray = new int[0];

        @Override public String getPatternText() { return patternText; }
        @Override public void setPatternText(String t) { this.patternText = t; }
        @Override public String getPatternQuery() { return patternQuery; }
        @Override public void setPatternQuery(String q) { this.patternQuery = q; }
        @Override public int getPatternPreset() { return patternPreset; }
        @Override public void setPatternPreset(int p) { this.patternPreset = p; }
        @Override public void setPatternState(String t, String q, int w, int m, boolean f) {
            this.patternText = t;
            this.patternQuery = q;
            this.patternWindowStart = w;
            this.patternMatchIndex = m;
            this.patternFound = f;
        }
        @Override public void setAuxiliaryArray(int[] a) { this.auxArray = a; }

        @Override public String getTechniqueId() { return techniqueId; }
        String expressionInput = "";
        @Override public String getExpressionInput() { return expressionInput; }
        @Override public void setExpressionInput(String s) { this.expressionInput = s; }
        @Override public String getTraversalMode() { return ""; }
        @Override public int[] getWorkingValues() { return workingValues; }
        @Override public void setWorkingValues(int[] v) { this.workingValues = v; }
        @Override public int getTarget() { return target; }
        @Override public int getStepIndex() { return stepIndex; }
        @Override public void setStepIndex(int v) { this.stepIndex = v; }
        @Override public int getAuxIndex() { return 0; }
        @Override public void setAuxIndex(int v) {}
        @Override public int getFoundIndex() { return 0; }
        @Override public void setFoundIndex(int v) {}
        @Override public int getBinaryPhase() { return 0; }
        @Override public void setBinaryPhase(int v) {}
        @Override public int getBinaryMidIndex() { return 0; }
        @Override public void setBinaryMidIndex(int v) {}
        @Override public int getHeapSize() { return 0; }
        @Override public void setHeapSize(int v) {}
        @Override public int getHeapSortPhase() { return 0; }
        @Override public void setHeapSortPhase(int v) {}
        @Override public int getHeapSortHeapifyIndex() { return 0; }
        @Override public void setHeapSortHeapifyIndex(int v) {}
        @Override public int getRadixDivisor() { return 1; }
        @Override public void setRadixDivisor(int v) {}
        @Override public int getRadixMaxValue() { return 0; }
        @Override public void setRadixMaxValue(int v) {}
        @Override public int getDifficultyLevel() { return 0; }
        @Override public void showPredictionUI(String q, String[] opt, int c, String exp) {}
        @Override public boolean isPredictionPending() { return false; }
        @Override public void resetVisualizationState() {}
        @Override public void restorePreviousSnapshot() {}
        @Override public boolean canRestorePreviousSnapshot() { return false; }
        @Override public void captureCurrentSnapshot() {}
        @Override public boolean isPlaybackComplete() { return playbackComplete; }
        @Override public void sortWorkingValues() {}
        @Override public void swapValues(int left, int right) {}
        @Override public void buildMaxHeap() {}
        @Override public void updatePointerState(String pLabel, int pIdx, String sLabel, int sIdx) {}
        @Override public void setStructureMotion(String motion, int index) {}
        @Override public void clearPointerState() {}
        @Override public void setHighlightedLines(int code, int algo) {}
        @Override public void updateArrayVisualization(int a, int s, int f) {}
        @Override public void updateTraversalVisualization(int[] order, int step) {}
        @Override public void updateStatusText(String m) { this.lastStatus = m; }
        String lastStatus = "";
        @Override public void bindCurrentState(String m) { this.lastStatus = m; }
        @Override public void finish(String m) { this.lastStatus = m; this.playbackComplete = true; }
        @Override public void finishWithoutResetLabel() { this.playbackComplete = true; }
        @Override public void updateBinaryChips() {}
        @Override public void syncScrollToFocus(int idx) {}
        @Override public void setActiveEdge(int from, int to) {}
        @Override public void clearActiveEdge() {}
        @Override public void setRadixState(int d, int b) {}
        int[] traversalOrder = new int[0];
        @Override public int[] getTraversalOrder() { return traversalOrder; }
        @Override public void setTraversalOrder(int[] v) { this.traversalOrder = v; }
        @Override public int[] buildTraversalOrder() { return traversalOrder; }

        @Override public int[] getDijkstraDistances() { return new int[0]; }
        @Override public void setDijkstraDistances(int[] d) {}
        @Override public boolean[] getDijkstraSettled() { return new boolean[0]; }
        @Override public void setDijkstraSettled(boolean[] s) {}
        @Override public int getDijkstraCurrentNode() { return 0; }
        @Override public void setDijkstraCurrentNode(int n) {}
        @Override public int getDijkstraPhase() { return 0; }
        @Override public void setDijkstraPhase(int p) {}
        @Override public int getDijkstraNeighborIndex() { return 0; }
        @Override public void setDijkstraNeighborIndex(int i) {}
        @Override public int[][] getDijkstraWeights() { return new int[0][0]; }
        @Override public void setDijkstraWeights(int[][] w) {}
        @Override public void setDijkstraState(int[] dist, boolean[] set, int cur) {}
        @Override public void initializeDijkstraPreview() {}

        int huffmanMergedLeft = -1;
        int huffmanMergedRight = -1;

        @Override public int[] getHuffmanQueueWeights() { return huffmanQueue; }
        @Override public void setHuffmanQueueWeights(int[] w) { this.huffmanQueue = w; }
        @Override public int getHuffmanMergedWeight() { return huffmanMerged; }
        @Override public void setHuffmanMergedWeight(int w) { this.huffmanMerged = w; }
        @Override public void setHuffmanState(int[] w, int m) {
            this.huffmanQueue = w;
            this.huffmanMerged = m;
        }
        @Override public void setHuffmanState(int[] w, int m, int l, int r) {
            this.huffmanQueue = w;
            this.huffmanMerged = m;
            this.huffmanMergedLeft = l;
            this.huffmanMergedRight = r;
        }
        @Override public void initializeHuffmanPreview() {
            HuffmanStepEngine.initializePreview(this);
        }

        @Override public void setActiveNodes(int primary, int secondary) {
            this.activePrimary = primary;
            this.activeSecondary = secondary;
        }

        String matrixLabel = "";

        @Override public int[][] getMatrixValues() { return matrix; }
        @Override public void setMatrixValues(int[][] v) { this.matrix = v; }
        @Override public int getMatrixK() { return matrixK; }
        @Override public void setMatrixK(int k) { this.matrixK = k; }
        @Override public int getMatrixRow() { return matrixRow; }
        @Override public void setMatrixRow(int r) { this.matrixRow = r; }
        @Override public int getMatrixCol() { return matrixCol; }
        @Override public void setMatrixCol(int c) { this.matrixCol = c; }
        @Override public void setMatrixState(int[][] v, int k, int r, int c, String l) {
            this.matrix = v;
            this.matrixK = k;
            this.matrixRow = r;
            this.matrixCol = c;
            this.matrixLabel = l;
        }
        @Override public String resolveMatrixLabel() { return matrixLabel != null && !matrixLabel.isEmpty() ? matrixLabel : "Warshall"; }
        @Override public void initializeCustomSortPreview() {}

        @Override public int getHeapModePhase() { return 0; }
        @Override public void setHeapModePhase(int p) {}

        @Override public int[] getUnionParents() { return new int[0]; }
        @Override public void setUnionParents(int[] p) {}
        @Override public int[] getUnionPath() { return new int[0]; }
        @Override public void setUnionPath(int[] p) {}
        @Override public int getUnionPathStep() { return 0; }
        @Override public void setUnionPathStep(int s) {}
        @Override public int getUnionFocusNode() { return 0; }
        @Override public void setUnionFocusNode(int n) {}
        @Override public int getUnionRootNode() { return 0; }
        @Override public void setUnionRootNode(int n) {}
        @Override public void setUnionFindState(int[] p, int f, int r) {}
        @Override public void initializeUnionFindPreview() {}

        @Override public int[][] getGraphEdges() { return new int[0][0]; }

        @Override public void advancePatternMatchingStep() {}
        @Override public void advanceBinarySearchStep() {}
        @Override public void advanceJumpSearchStep() {}
        @Override public void advanceLinearSearchStep() {}
        @Override public void advanceMergingStep() {}
        @Override public void advanceMergeSortStep() {}
        @Override public void advanceRadixSortStep() {}
        @Override public void advanceQuickSortFamilyStep() {}
        @Override public void advanceHeapSortStep() {}
        @Override public void advanceBubbleSortStep() {}
        @Override public void advanceSelectionSortStep() {}
        @Override public void advanceInsertionSortStep() {}
        @Override public void advanceUnionFindStep() {}
        @Override public void advanceLinearStructureStepCore() {}
        @Override public void advanceMatrixStep() {}
        @Override public void advanceDijkstraStep() {}
        @Override public void advanceGraphTraversalStep() {}
        @Override public void advanceHeapOperationStep() {}
        @Override public void advanceHuffmanStep() {}
        @Override public void advanceAvlStep() {}
        @Override public void advanceTrieStep() {}
        @Override public void advanceTreeTraversalStep() {}
    }
}
