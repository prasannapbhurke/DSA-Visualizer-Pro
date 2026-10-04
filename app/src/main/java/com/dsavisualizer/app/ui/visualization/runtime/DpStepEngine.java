package com.dsavisualizer.app.ui.visualization.runtime;

/**
 * Dedicated visual step engine for all Dynamic Programming algorithms.
 * Renders 2D state transition tables, subproblem dependency lookups,
 * cell updates, and optimal solution reconstructions.
 * Supports customizable presets for each algorithm.
 */
public final class DpStepEngine extends BaseStepEngine {

    public DpStepEngine(VisualizationRuntimeHost host) {
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
        if ("dp_knapsack".equals(id)) {
            advanceKnapsack(host);
        } else if ("dp_lcs".equals(id)) {
            advanceLcs(host);
        } else if ("dp_lis".equals(id)) {
            advanceLis(host);
        } else if ("dp_coin_change".equals(id)) {
            advanceCoinChange(host);
        } else if ("dp_matrix_chain".equals(id)) {
            advanceMatrixChain(host);
        } else if ("dp_edit_distance".equals(id)) {
            advanceEditDistance(host);
        } else if ("dp_rod_cutting".equals(id)) {
            advanceRodCutting(host);
        } else if ("dp_subset_sum".equals(id)) {
            advanceSubsetSum(host);
        } else if ("dp_kadane".equals(id)) {
            advanceKadane(host);
        } else {
            advanceGenericDp(host);
        }
    }

    // =========================================================================
    // Helpers & Presets
    // =========================================================================
    private static int resolvePreset(VisualizationRuntimeHost host) {
        int target = host.getTarget();
        if (target >= 1 && target <= 3) {
            return target;
        }
        return 1;
    }

    public static String getPresetDescription(String techniqueId, int preset) {
        if ("dp_knapsack".equals(techniqueId)) {
            if (preset == 2) return "Capacity W=8, Items: [wt=2, val=3], [wt=3, val=4], [wt=4, val=5], [wt=5, val=8]";
            if (preset == 3) return "Capacity W=5, Items: [wt=1, val=2], [wt=2, val=4], [wt=3, val=7]";
            return "Capacity W=6, Items: [wt=2, val=3], [wt=3, val=4], [wt=4, val=5], [wt=5, val=8]";
        }
        if ("dp_lcs".equals(techniqueId)) {
            if (preset == 2) return "s1 = 'AGGTAB', s2 = 'GXTXAYB' (Expected LCS: 'GTAB')";
            if (preset == 3) return "s1 = 'STONE', s2 = 'LONGEST' (Expected LCS: 'ONE')";
            return "s1 = 'ABCDE', s2 = 'ACE' (Expected LCS: 'ACE')";
        }
        if ("dp_lis".equals(techniqueId)) {
            if (preset == 2) return "Array: [3, 10, 2, 1, 20] (Expected LIS: 3)";
            if (preset == 3) return "Array: [50, 3, 10, 7, 40, 80] (Expected LIS: 4)";
            return "Array: [10, 22, 9, 33, 21, 50, 41, 60] (Expected LIS: 5)";
        }
        if ("dp_coin_change".equals(techniqueId)) {
            if (preset == 2) return "Amount = 11, Coins = {1, 5, 6, 8}";
            if (preset == 3) return "Amount = 6, Coins = {2, 3, 5}";
            return "Amount = 8, Coins = {1, 2, 5}";
        }
        if ("dp_matrix_chain".equals(techniqueId)) {
            if (preset == 2) return "Dimensions: [10, 30, 5, 60] (3 Matrices: 10x30, 30x5, 5x60)";
            if (preset == 3) return "Dimensions: [40, 20, 30, 10, 30] (4 Matrices: 40x20, 20x30, 30x10, 10x30)";
            return "Dimensions: [10, 20, 30, 40, 30] (4 Matrices: 10x20, 20x30, 30x40, 40x30)";
        }
        if ("dp_edit_distance".equals(techniqueId)) {
            if (preset == 2) return "Word1 = 'INTENTION', Word2 = 'EXECUTION'";
            if (preset == 3) return "Word1 = 'KITTEN', Word2 = 'SITTING'";
            return "Word1 = 'HORSE', Word2 = 'ROS'";
        }
        if ("dp_rod_cutting".equals(techniqueId)) {
            if (preset == 2) return "Rod Length = 6, Prices = [2, 5, 7, 8, 10, 14]";
            if (preset == 3) return "Rod Length = 5, Prices = [3, 5, 8, 9, 10]";
            return "Rod Length = 8, Prices = [1, 5, 8, 9, 10, 17, 17, 20]";
        }
        if ("dp_subset_sum".equals(techniqueId)) {
            if (preset == 2) return "Set = [1, 3, 4, 8], Target Sum = 6 (Possible: 4+1? No, 1+3+... etc.)";
            if (preset == 3) return "Set = [2, 3, 7, 8, 10], Target Sum = 11 (Possible: 8+3)";
            return "Set = [3, 34, 4, 12, 5, 2], Target Sum = 9 (Possible: 4+3+2)";
        }
        if ("dp_kadane".equals(techniqueId)) {
            if (preset == 2) return "Array: [1, 2, 3, -2, 5] (Expected Max: 9)";
            if (preset == 3) return "Array: [-1, -2, -3, -4] (Expected Max: -1)";
            return "Array: [-2, 1, -3, 4, -1, 2, 1, -5, 4] (Expected Max: 6)";
        }
        return "Standard dynamic programming configuration";
    }

    public static int getDpTotalSteps(String techniqueId, int preset) {
        if ("dp_knapsack".equals(techniqueId)) {
            int n = (preset == 3) ? 3 : 4;
            int w = (preset == 2) ? 8 : ((preset == 3) ? 5 : 6);
            return (n + 1) * (w + 1);
        }
        if ("dp_lcs".equals(techniqueId)) {
            if (preset == 2) return (6 + 1) * (7 + 1);
            if (preset == 3) return (5 + 1) * (7 + 1);
            return (5 + 1) * (3 + 1);
        }
        if ("dp_lis".equals(techniqueId)) {
            if (preset == 2) return 5;
            if (preset == 3) return 6;
            return 8;
        }
        if ("dp_coin_change".equals(techniqueId)) {
            if (preset == 2) return 12;
            if (preset == 3) return 7;
            return 9;
        }
        if ("dp_matrix_chain".equals(techniqueId)) {
            int n = (preset == 2) ? 4 : 5;
            int count = 0;
            for (int L = 2; L < n; L++) {
                for (int i = 1; i < n - L + 1; i++) {
                    count++;
                }
            }
            return count + 1;
        }
        if ("dp_edit_distance".equals(techniqueId)) {
            if (preset == 2) return (9 + 1) * (9 + 1);
            if (preset == 3) return (6 + 1) * (7 + 1);
            return (5 + 1) * (3 + 1);
        }
        if ("dp_rod_cutting".equals(techniqueId)) {
            if (preset == 2) return 7;
            if (preset == 3) return 6;
            return 9;
        }
        if ("dp_subset_sum".equals(techniqueId)) {
            if (preset == 2) return (4 + 1) * (6 + 1);
            if (preset == 3) return (5 + 1) * (11 + 1);
            return (6 + 1) * (9 + 1);
        }
        if ("dp_kadane".equals(techniqueId)) {
            if (preset == 2) return 5;
            if (preset == 3) return 4;
            return 9;
        }
        return 10;
    }

    public static int[][] getInitialDpMatrix(String techniqueId, int preset) {
        if ("dp_knapsack".equals(techniqueId)) {
            int n = (preset == 3) ? 3 : 4;
            int w = (preset == 2) ? 8 : ((preset == 3) ? 5 : 6);
            return new int[n + 1][w + 1];
        }
        if ("dp_lcs".equals(techniqueId)) {
            if (preset == 2) return new int[7][8];
            if (preset == 3) return new int[6][8];
            return new int[6][4];
        }
        if ("dp_lis".equals(techniqueId)) {
            int len = (preset == 2) ? 5 : ((preset == 3) ? 6 : 8);
            int[][] mat = new int[2][len];
            int[] arr = getLisArray(preset);
            System.arraycopy(arr, 0, mat[0], 0, len);
            java.util.Arrays.fill(mat[1], 1);
            return mat;
        }
        if ("dp_coin_change".equals(techniqueId)) {
            int amt = (preset == 2) ? 11 : ((preset == 3) ? 6 : 8);
            int[][] mat = new int[2][amt + 1];
            for (int i = 0; i <= amt; i++) mat[0][i] = i;
            return mat;
        }
        if ("dp_matrix_chain".equals(techniqueId)) {
            int n = (preset == 2) ? 4 : 5;
            return new int[n][n];
        }
        if ("dp_edit_distance".equals(techniqueId)) {
            if (preset == 2) return new int[10][10];
            if (preset == 3) return new int[7][8];
            return new int[6][4];
        }
        if ("dp_rod_cutting".equals(techniqueId)) {
            int n = (preset == 2) ? 6 : ((preset == 3) ? 5 : 8);
            int[][] mat = new int[2][n + 1];
            for (int i = 0; i <= n; i++) mat[0][i] = i;
            return mat;
        }
        if ("dp_subset_sum".equals(techniqueId)) {
            int n = (preset == 2) ? 4 : ((preset == 3) ? 5 : 6);
            int sum = (preset == 2) ? 6 : ((preset == 3) ? 11 : 9);
            return new int[n + 1][sum + 1];
        }
        if ("dp_kadane".equals(techniqueId)) {
            int len = (preset == 2) ? 5 : ((preset == 3) ? 4 : 9);
            int[][] mat = new int[2][len];
            int[] arr = getKadaneArray(preset);
            System.arraycopy(arr, 0, mat[0], 0, len);
            return mat;
        }
        return new int[4][4];
    }

    public static String[] getColumnHeaders(String techniqueId, int cols) {
        if ("dp_knapsack".equals(techniqueId) || "dp_subset_sum".equals(techniqueId) || "dp_coin_change".equals(techniqueId)) {
            String[] headers = new String[cols];
            for (int i = 0; i < cols; i++) headers[i] = String.valueOf(i);
            return headers;
        }
        if ("dp_lcs".equals(techniqueId)) {
            // String 2 chars
            String s2 = "ACE";
            String[] headers = new String[cols];
            headers[0] = "Ø";
            for (int j = 1; j < cols && j <= s2.length(); j++) {
                headers[j] = String.valueOf(s2.charAt(j - 1));
            }
            return headers;
        }
        if ("dp_edit_distance".equals(techniqueId)) {
            String w2 = "ROS";
            String[] headers = new String[cols];
            headers[0] = "Ø";
            for (int j = 1; j < cols && j <= w2.length(); j++) {
                headers[j] = String.valueOf(w2.charAt(j - 1));
            }
            return headers;
        }
        if ("dp_matrix_chain".equals(techniqueId)) {
            String[] headers = new String[cols];
            for (int i = 0; i < cols; i++) headers[i] = "M" + i;
            return headers;
        }
        if ("dp_lis".equals(techniqueId) || "dp_kadane".equals(techniqueId) || "dp_rod_cutting".equals(techniqueId)) {
            String[] headers = new String[cols];
            for (int i = 0; i < cols; i++) headers[i] = "[" + i + "]";
            return headers;
        }
        return null;
    }

    public static String[] getRowHeaders(String techniqueId, int rows) {
        if ("dp_knapsack".equals(techniqueId)) {
            String[] headers = new String[rows];
            headers[0] = "0:Ø";
            for (int i = 1; i < rows; i++) headers[i] = "It" + i;
            return headers;
        }
        if ("dp_lcs".equals(techniqueId)) {
            String s1 = "ABCDE";
            String[] headers = new String[rows];
            headers[0] = "Ø";
            for (int i = 1; i < rows && i <= s1.length(); i++) {
                headers[i] = String.valueOf(s1.charAt(i - 1));
            }
            return headers;
        }
        if ("dp_edit_distance".equals(techniqueId)) {
            String w1 = "HORSE";
            String[] headers = new String[rows];
            headers[0] = "Ø";
            for (int i = 1; i < rows && i <= w1.length(); i++) {
                headers[i] = String.valueOf(w1.charAt(i - 1));
            }
            return headers;
        }
        if ("dp_subset_sum".equals(techniqueId)) {
            String[] headers = new String[rows];
            headers[0] = "Ø";
            for (int i = 1; i < rows; i++) headers[i] = "e" + i;
            return headers;
        }
        if ("dp_matrix_chain".equals(techniqueId)) {
            String[] headers = new String[rows];
            for (int i = 0; i < rows; i++) headers[i] = "M" + i;
            return headers;
        }
        if ("dp_lis".equals(techniqueId)) {
            return new String[] {"Num", "LIS"};
        }
        if ("dp_coin_change".equals(techniqueId)) {
            return new String[] {"Amt", "Coins"};
        }
        if ("dp_rod_cutting".equals(techniqueId)) {
            return new String[] {"Len", "Rev"};
        }
        if ("dp_kadane".equals(techniqueId)) {
            return new String[] {"Num", "MaxSub"};
        }
        return null;
    }

    private static int[] getLisArray(int preset) {
        if (preset == 2) return new int[] {3, 10, 2, 1, 20};
        if (preset == 3) return new int[] {50, 3, 10, 7, 40, 80};
        return new int[] {10, 22, 9, 33, 21, 50, 41, 60};
    }

    private static int[] getKadaneArray(int preset) {
        if (preset == 2) return new int[] {1, 2, 3, -2, 5};
        if (preset == 3) return new int[] {-1, -2, -3, -4};
        return new int[] {-2, 1, -3, 4, -1, 2, 1, -5, 4};
    }

    // =========================================================================
    // 1. 0/1 Knapsack
    // =========================================================================
    private static void advanceKnapsack(VisualizationRuntimeHost host) {
        int preset = resolvePreset(host);
        int[] wt;
        int[] val;
        int W;
        if (preset == 2) {
            wt = new int[] {2, 3, 4, 5};
            val = new int[] {3, 4, 5, 8};
            W = 8;
        } else if (preset == 3) {
            wt = new int[] {1, 2, 3};
            val = new int[] {2, 4, 7};
            W = 5;
        } else {
            wt = new int[] {2, 3, 4, 5};
            val = new int[] {3, 4, 5, 8};
            W = 6;
        }
        int N = wt.length;

        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);

        int totalCells = (N + 1) * (W + 1);
        if (step >= totalCells) {
            int[][] finalDp = computeFullKnapsack(wt, val, W);
            host.setMatrixState(finalDp, 0, N, W, "0/1 Knapsack: Optimal = " + finalDp[N][W]);
            host.finish("0/1 Knapsack Complete! Max value for capacity " + W + " = " + finalDp[N][W]);
            return;
        }

        int currI = step / (W + 1);
        int currW = step % (W + 1);

        int[][] dp = new int[N + 1][W + 1];
        for (int i = 0; i <= N; i++) {
            for (int w = 0; w <= W; w++) {
                int cellIndex = i * (W + 1) + w;
                if (cellIndex <= step) {
                    if (i == 0 || w == 0) {
                        dp[i][w] = 0;
                    } else if (wt[i - 1] <= w) {
                        dp[i][w] = Math.max(val[i - 1] + dp[i - 1][w - wt[i - 1]], dp[i - 1][w]);
                    } else {
                        dp[i][w] = dp[i - 1][w];
                    }
                }
            }
        }

        host.setMatrixState(dp, 0, currI, currW, "Knapsack Table (Item " + currI + ", Cap " + currW + ")");
        host.updatePointerState("item " + currI, Math.min(currI, wt.length - 1), "cap " + currW, currW);
        host.setHighlightedLines(3, 2);

        if (currI == 0 || currW == 0) {
            host.bindCurrentState("Base Case: dp[" + currI + "][" + currW + "] = 0 (No items or zero capacity).");
        } else {
            int itemWt = wt[currI - 1];
            int itemVal = val[currI - 1];
            if (itemWt <= currW) {
                int includeVal = itemVal + dp[currI - 1][currW - itemWt];
                int excludeVal = dp[currI - 1][currW];
                host.bindCurrentState("Item " + currI + " (wt=" + itemWt + ", val=" + itemVal + ") fits! "
                    + "Include: " + includeVal + ", Exclude: " + excludeVal + " -> dp[" + currI + "][" + currW + "] = " + dp[currI][currW]);
            } else {
                host.bindCurrentState("Item " + currI + " (wt=" + itemWt + ") exceeds capacity " + currW + ". "
                    + "Exclude item -> dp[" + currI + "][" + currW + "] = dp[" + (currI - 1) + "][" + currW + "] = " + dp[currI][currW]);
            }
        }
    }

    private static int[][] computeFullKnapsack(int[] wt, int[] val, int W) {
        int N = wt.length;
        int[][] dp = new int[N + 1][W + 1];
        for (int i = 1; i <= N; i++) {
            for (int w = 1; w <= W; w++) {
                if (wt[i - 1] <= w) {
                    dp[i][w] = Math.max(val[i - 1] + dp[i - 1][w - wt[i - 1]], dp[i - 1][w]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }
        return dp;
    }

    // =========================================================================
    // 2. Longest Common Subsequence (LCS)
    // =========================================================================
    private static void advanceLcs(VisualizationRuntimeHost host) {
        int preset = resolvePreset(host);
        String s1 = (preset == 2) ? "AGGTAB" : ((preset == 3) ? "STONE" : "ABCDE");
        String s2 = (preset == 2) ? "GXTXAYB" : ((preset == 3) ? "LONGEST" : "ACE");
        int m = s1.length(), n = s2.length();

        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);

        int total = (m + 1) * (n + 1);
        if (step >= total) {
            int[][] finalDp = computeFullLcs(s1, s2);
            host.setMatrixState(finalDp, 0, m, n, "LCS Complete: Length = " + finalDp[m][n]);
            host.finish("LCS Complete! Longest Common Subsequence length = " + finalDp[m][n]);
            return;
        }

        int currI = step / (n + 1);
        int currJ = step % (n + 1);

        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                int cellIndex = i * (n + 1) + j;
                if (cellIndex <= step) {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 0;
                    } else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                        dp[i][j] = 1 + dp[i - 1][j - 1];
                    } else {
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                    }
                }
            }
        }

        host.setMatrixState(dp, 0, currI, currJ, "LCS Matrix (s1=" + s1 + ", s2=" + s2 + ")");
        host.updatePointerState("s1[" + currI + "]", currI, "s2[" + currJ + "]", currJ);

        if (currI == 0 || currJ == 0) {
            host.bindCurrentState("Base Case: dp[" + currI + "][" + currJ + "] = 0 (empty prefix).");
        } else {
            char c1 = s1.charAt(currI - 1);
            char c2 = s2.charAt(currJ - 1);
            if (c1 == c2) {
                host.bindCurrentState("Match found ('" + c1 + "' == '" + c2 + "')! Take diagonal 1 + dp["
                    + (currI - 1) + "][" + (currJ - 1) + "] = " + dp[currI][currJ]);
            } else {
                host.bindCurrentState("Mismatch ('" + c1 + "' != '" + c2 + "'). Max of Top ("
                    + dp[currI - 1][currJ] + ") & Left (" + dp[currI][currJ - 1] + ") = " + dp[currI][currJ]);
            }
        }
    }

    private static int[][] computeFullLcs(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp;
    }

    // =========================================================================
    // 3. Longest Increasing Subsequence (LIS)
    // =========================================================================
    private static void advanceLis(VisualizationRuntimeHost host) {
        int preset = resolvePreset(host);
        int[] nums = getLisArray(preset);

        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);

        int n = nums.length;
        int[] dp = new int[n];
        java.util.Arrays.fill(dp, 1);
        int maxLen = 1;

        int[][] mat = new int[2][n];
        System.arraycopy(nums, 0, mat[0], 0, n);

        for (int i = 0; i <= Math.min(step, n - 1); i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            mat[1][i] = dp[i];
            if (dp[i] > maxLen) maxLen = dp[i];
        }

        if (step >= n) {
            host.setMatrixState(mat, 0, 1, n - 1, "LIS Table: Max Length = " + maxLen);
            host.finish("LIS Complete! Max increasing subsequence length = " + maxLen);
            return;
        }

        host.setMatrixState(mat, 0, 1, step, "LIS State: Evaluating Index " + step);
        host.updatePointerState("nums[" + step + "]=" + nums[step], step, "LIS=" + dp[step], step);
        host.bindCurrentState("Step " + (step + 1) + ": Examining nums[" + step + "] = " + nums[step]
            + ". Best increasing subsequence ending here has length = " + dp[step] + ".");
    }

    // =========================================================================
    // 4. Coin Change
    // =========================================================================
    private static void advanceCoinChange(VisualizationRuntimeHost host) {
        int preset = resolvePreset(host);
        int[] coins = (preset == 2) ? new int[] {1, 5, 6, 8} : ((preset == 3) ? new int[] {2, 3, 5} : new int[] {1, 2, 5});
        int amount = (preset == 2) ? 11 : ((preset == 3) ? 6 : 8);

        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);

        int[] dp = new int[amount + 1];
        java.util.Arrays.fill(dp, 9999);
        dp[0] = 0;

        int[][] mat = new int[2][amount + 1];
        for (int a = 0; a <= amount; a++) mat[0][a] = a;

        for (int i = 1; i <= Math.min(step, amount); i++) {
            for (int c : coins) {
                if (c <= i && dp[i - c] != 9999) {
                    dp[i] = Math.min(dp[i], 1 + dp[i - c]);
                }
            }
            mat[1][i] = dp[i];
        }

        if (step > amount) {
            int optimal = (dp[amount] >= 9999) ? -1 : dp[amount];
            host.setMatrixState(mat, 0, 1, amount, "Coin Change Complete: " + optimal + " coins");
            host.finish("Coin Change Complete! Fewest coins for amount " + amount + " = " + (optimal == -1 ? "Impossible" : optimal));
            return;
        }

        host.setMatrixState(mat, 0, 1, step, "Coin Change: Amount " + step);
        host.updatePointerState("Amt=" + step, step, "MinCoins=" + (dp[step] >= 9999 ? "∞" : String.valueOf(dp[step])), step);
        host.bindCurrentState("Amount " + step + ": Minimum coins needed = "
            + (dp[step] >= 9999 ? "Not Possible" : dp[step]) + ".");
    }

    // =========================================================================
    // 5. Matrix Chain Multiplication
    // =========================================================================
    private static void advanceMatrixChain(VisualizationRuntimeHost host) {
        int preset = resolvePreset(host);
        int[] p = (preset == 2) ? new int[] {10, 30, 5, 60} : ((preset == 3) ? new int[] {40, 20, 30, 10, 30} : new int[] {10, 20, 30, 40, 30});
        int n = p.length;

        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);

        int[][] m = new int[n][n];
        int count = 0;
        int activeI = 1, activeJ = 2;
        boolean finished = true;

        for (int L = 2; L < n; L++) {
            for (int i = 1; i < n - L + 1; i++) {
                int j = i + L - 1;
                count++;
                if (count <= step) {
                    m[i][j] = Integer.MAX_VALUE;
                    for (int k = i; k <= j - 1; k++) {
                        int q = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];
                        if (q < m[i][j]) m[i][j] = q;
                    }
                    activeI = i;
                    activeJ = j;
                } else {
                    finished = false;
                    break;
                }
            }
            if (!finished) break;
        }

        if (finished && count <= step) {
            host.setMatrixState(m, 0, 1, n - 1, "MCM Complete: Min Ops = " + m[1][n - 1]);
            host.finish("Matrix Chain Multiplication Complete! Minimum scalar multiplications = " + m[1][n - 1]);
            return;
        }

        host.setMatrixState(m, 0, activeI, activeJ, "MCM Cost Matrix [L=" + (activeJ - activeI + 1) + "]");
        host.updatePointerState("A" + activeI, activeI, "A" + activeJ, activeJ);
        host.bindCurrentState("Chain A" + activeI + "..A" + activeJ + ": Minimum operations = " + m[activeI][activeJ]);
    }

    // =========================================================================
    // 6. Edit Distance
    // =========================================================================
    private static void advanceEditDistance(VisualizationRuntimeHost host) {
        int preset = resolvePreset(host);
        String w1 = (preset == 2) ? "INTENTION" : ((preset == 3) ? "KITTEN" : "HORSE");
        String w2 = (preset == 2) ? "EXECUTION" : ((preset == 3) ? "SITTING" : "ROS");
        int m = w1.length(), n = w2.length();

        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);

        int total = (m + 1) * (n + 1);
        if (step >= total) {
            int[][] finalDp = computeFullEditDistance(w1, w2);
            host.setMatrixState(finalDp, 0, m, n, "Edit Distance: " + finalDp[m][n] + " ops");
            host.finish("Edit Distance Complete! Minimum edit operations = " + finalDp[m][n]);
            return;
        }

        int currI = step / (n + 1);
        int currJ = step % (n + 1);

        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int cellIndex = i * (n + 1) + j;
                if (cellIndex <= step) {
                    if (w1.charAt(i - 1) == w2.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else {
                        dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
                    }
                }
            }
        }

        host.setMatrixState(dp, 0, currI, currJ, "Edit Distance ('" + w1 + "' -> '" + w2 + "')");
        host.updatePointerState("w1[" + currI + "]", currI, "w2[" + currJ + "]", currJ);
        host.bindCurrentState("Cell (" + currI + ", " + currJ + "): Min edit operations = " + dp[currI][currJ]);
    }

    private static int[][] computeFullEditDistance(String w1, String w2) {
        int m = w1.length(), n = w2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (w1.charAt(i - 1) == w2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }
        return dp;
    }

    // =========================================================================
    // 7. Rod Cutting
    // =========================================================================
    private static void advanceRodCutting(VisualizationRuntimeHost host) {
        int preset = resolvePreset(host);
        int[] price = (preset == 2) ? new int[] {2, 5, 7, 8, 10, 14}
            : ((preset == 3) ? new int[] {3, 5, 8, 9, 10} : new int[] {1, 5, 8, 9, 10, 17, 17, 20});
        int n = price.length;

        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);

        int[] val = new int[n + 1];
        val[0] = 0;
        int[][] mat = new int[2][n + 1];
        for (int l = 0; l <= n; l++) mat[0][l] = l;

        for (int i = 1; i <= Math.min(step, n); i++) {
            int maxVal = Integer.MIN_VALUE;
            for (int j = 0; j < i; j++) {
                maxVal = Math.max(maxVal, price[j] + val[i - j - 1]);
            }
            val[i] = maxVal;
            mat[1][i] = val[i];
        }

        if (step > n) {
            host.setMatrixState(mat, 0, 1, n, "Rod Cutting Complete: Revenue = " + val[n]);
            host.finish("Rod Cutting Complete! Max revenue for length " + n + " = " + val[n]);
            return;
        }

        host.setMatrixState(mat, 0, 1, step, "Rod Cutting: Length " + step);
        host.updatePointerState("Length " + step, step, "MaxRev=" + val[step], step);
        host.bindCurrentState("Rod Length " + step + ": Maximum obtainable revenue = " + val[step]);
    }

    // =========================================================================
    // 8. Subset Sum
    // =========================================================================
    private static void advanceSubsetSum(VisualizationRuntimeHost host) {
        int preset = resolvePreset(host);
        int[] set = (preset == 2) ? new int[] {1, 3, 4, 8} : ((preset == 3) ? new int[] {2, 3, 7, 8, 10} : new int[] {3, 34, 4, 12, 5, 2});
        int sum = (preset == 2) ? 6 : ((preset == 3) ? 11 : 9);
        int n = set.length;

        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);

        int total = (n + 1) * (sum + 1);
        if (step >= total) {
            int[][] finalDp = computeFullSubsetSum(set, sum);
            host.setMatrixState(finalDp, 0, n, sum, "Subset Sum: " + (finalDp[n][sum] == 1 ? "Possible" : "Impossible"));
            host.finish("Subset Sum Complete! Target sum " + sum + " is " + (finalDp[n][sum] == 1 ? "attainable" : "not attainable") + ".");
            return;
        }

        int currI = step / (sum + 1);
        int currJ = step % (sum + 1);

        int[][] dp = new int[n + 1][sum + 1];
        for (int i = 0; i <= n; i++) dp[i][0] = 1; // 1 = true

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= sum; j++) {
                int cellIndex = i * (sum + 1) + j;
                if (cellIndex <= step) {
                    if (set[i - 1] <= j) {
                        dp[i][j] = (dp[i - 1][j] == 1 || dp[i - 1][j - set[i - 1]] == 1) ? 1 : 0;
                    } else {
                        dp[i][j] = dp[i - 1][j];
                    }
                }
            }
        }

        host.setMatrixState(dp, 0, currI, currJ, "Subset Sum Matrix (Target=" + sum + ")");
        host.updatePointerState("Elem " + currI, currI, "Sum " + currJ, currJ);
        host.bindCurrentState("Evaluating element set[" + (currI > 0 ? currI - 1 : 0) + "] for sum "
            + currJ + ": " + (dp[currI][currJ] == 1 ? "Possible (TRUE)" : "Not Possible (FALSE)"));
    }

    private static int[][] computeFullSubsetSum(int[] set, int sum) {
        int n = set.length;
        int[][] dp = new int[n + 1][sum + 1];
        for (int i = 0; i <= n; i++) dp[i][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= sum; j++) {
                if (set[i - 1] <= j) {
                    dp[i][j] = (dp[i - 1][j] == 1 || dp[i - 1][j - set[i - 1]] == 1) ? 1 : 0;
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp;
    }

    // =========================================================================
    // 9. Kadane's Algorithm
    // =========================================================================
    private static void advanceKadane(VisualizationRuntimeHost host) {
        int preset = resolvePreset(host);
        int[] nums = getKadaneArray(preset);

        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);

        int n = nums.length;
        int[][] mat = new int[2][n];
        System.arraycopy(nums, 0, mat[0], 0, n);

        int maxSoFar = nums[0];
        int currentMax = nums[0];
        mat[1][0] = currentMax;

        for (int i = 1; i <= Math.min(step, n - 1); i++) {
            currentMax = Math.max(nums[i], currentMax + nums[i]);
            maxSoFar = Math.max(maxSoFar, currentMax);
            mat[1][i] = currentMax;
        }

        if (step >= n) {
            host.setMatrixState(mat, 0, 1, n - 1, "Kadane's Complete: Max Sum = " + maxSoFar);
            host.finish("Kadane's Algorithm Complete! Maximum contiguous subarray sum = " + maxSoFar);
            return;
        }

        host.setMatrixState(mat, 0, 1, step, "Kadane's Algorithm: Index " + step);
        host.updatePointerState("CurrSum=" + currentMax, step, "MaxSoFar=" + maxSoFar, step);
        host.bindCurrentState("Index " + step + " (" + nums[step] + "): CurrentSubarraySum="
            + currentMax + ", MaxSubarrayFound=" + maxSoFar);
    }

    private static void advanceGenericDp(VisualizationRuntimeHost host) {
        int step = host.getStepIndex() + 1;
        host.setStepIndex(step);
        host.bindCurrentState("DP Step " + (step + 1) + " evaluated.");
    }
}
