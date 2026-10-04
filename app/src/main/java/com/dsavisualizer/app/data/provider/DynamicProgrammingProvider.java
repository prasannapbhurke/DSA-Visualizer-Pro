package com.dsavisualizer.app.data.provider;

import com.dsavisualizer.app.model.CategoryItem;
import com.dsavisualizer.app.model.TechniqueContent;
import com.dsavisualizer.app.model.TechniqueItem;

import java.util.List;
import java.util.Map;

/**
 * Curated provider for all Dynamic Programming algorithms.
 * Includes complete Iterative, Functions, and Recursion algorithms and code (Java, C, Python).
 */
public final class DynamicProgrammingProvider {

    private DynamicProgrammingProvider() {}

    public static void register(
        List<CategoryItem> categories,
        List<TechniqueItem> techniques,
        Map<String, TechniqueContent> contentMap
    ) {
        categories.add(new CategoryItem(
            "dynamic_programming",
            "Dynamic Programming",
            "Optimal subproblems, memoization, and tabular transitions",
            "#6366F1"
        ));

        // 1. 0/1 Knapsack
        techniques.add(new TechniqueItem(
            "dp_knapsack",
            "dynamic_programming",
            "0/1 Knapsack Problem",
            "Maximize value within weight capacity • Table DP",
            "Medium"
        ));
        // 2. Longest Common Subsequence (LCS)
        techniques.add(new TechniqueItem(
            "dp_lcs",
            "dynamic_programming",
            "Longest Common Subsequence (LCS)",
            "Find longest shared sequence across two strings • 2D Grid",
            "Medium"
        ));
        // 3. Longest Increasing Subsequence (LIS)
        techniques.add(new TechniqueItem(
            "dp_lis",
            "dynamic_programming",
            "Longest Increasing Subsequence (LIS)",
            "Longest strictly ascending sequence • Array DP",
            "Medium"
        ));
        // 4. Coin Change Problem
        techniques.add(new TechniqueItem(
            "dp_coin_change",
            "dynamic_programming",
            "Coin Change (Min Coins & Ways)",
            "Fewest coins to reach target total • Unbounded DP",
            "Medium"
        ));
        // 5. Matrix Chain Multiplication (MCM)
        techniques.add(new TechniqueItem(
            "dp_matrix_chain",
            "dynamic_programming",
            "Matrix Chain Multiplication",
            "Optimal parenthesization order • Interval DP",
            "Hard"
        ));
        // 6. Edit Distance (Levenshtein)
        techniques.add(new TechniqueItem(
            "dp_edit_distance",
            "dynamic_programming",
            "Edit Distance (Levenshtein)",
            "Minimum insert, delete, replace steps • 2D Grid",
            "Hard"
        ));
        // 7. Rod Cutting Problem
        techniques.add(new TechniqueItem(
            "dp_rod_cutting",
            "dynamic_programming",
            "Rod Cutting Problem",
            "Partition length into pieces to maximize revenue",
            "Medium"
        ));
        // 8. Subset Sum Problem
        techniques.add(new TechniqueItem(
            "dp_subset_sum",
            "dynamic_programming",
            "Subset Sum Problem",
            "Determine if any subset matches exact sum • Boolean DP",
            "Medium"
        ));
        // 9. Kadane's Algorithm (Max Subarray)
        techniques.add(new TechniqueItem(
            "dp_kadane",
            "dynamic_programming",
            "Maximum Subarray (Kadane's Algorithm)",
            "Highest contiguous subarray sum in O(n) linear time",
            "Easy"
        ));

        registerKnapsack(contentMap);
        registerLcs(contentMap);
        registerLis(contentMap);
        registerCoinChange(contentMap);
        registerMatrixChain(contentMap);
        registerEditDistance(contentMap);
        registerRodCutting(contentMap);
        registerSubsetSum(contentMap);
        registerKadane(contentMap);
    }

    private static void registerKnapsack(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "dp_knapsack",
            "0/1 Knapsack Problem",
            "Given weights and values of N items, find the maximum value subset that fits in capacity W without splitting items.\n\nEach item can either be included (1) or excluded (0). We construct a 2D table dp[i][w] representing the max value using a subset of the first i items with capacity limit w.",
            "Packing a travel backpack with weight limits where you want the highest utility items without exceeding baggage limits.",
            "O(N * W)", "O(N * W)", "O(N * W)", "O(N * W)",
            new int[] {2, 3, 4, 5},
            7
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: KNAPSACK_01_ITERATIVE(wt, val, N, W)\n1. Create a 2D table dp of size (N+1) x (W+1) initialized to 0.\n2. For i from 1 to N:\n   For w from 1 to W:\n     If wt[i-1] <= w:\n       dp[i][w] = max(val[i-1] + dp[i-1][w - wt[i-1]], dp[i-1][w])\n     Else:\n       dp[i][w] = dp[i-1][w]\n3. Return dp[N][W].");
        tc.algorithmByStyle.put("Functions", "Algorithm: KNAPSACK_01_FUNCTIONAL(wt, val, N, W)\nFUNCTION COMPUTE_CELL(i, w, wt, val, dp):\n  If wt[i-1] <= w:\n    return max(val[i-1] + dp[i-1][w - wt[i-1]], dp[i-1][w])\n  return dp[i-1][w]\n\nFUNCTION KNAPSACK(wt, val, W):\n1. Initialize dp[N+1][W+1] with zeros.\n2. Fill table using COMPUTE_CELL for each (i, w).\n3. Return dp[N][W].");
        tc.algorithmByStyle.put("Recursion", "Algorithm: KNAPSACK_01_RECURSIVE(wt, val, n, W)\n1. Base Case: If n == 0 or W == 0, return 0.\n2. If wt[n-1] > W:\n     return KNAPSACK_01_RECURSIVE(wt, val, n-1, W)\n3. Else return max(\n     val[n-1] + KNAPSACK_01_RECURSIVE(wt, val, n-1, W - wt[n-1]),\n     KNAPSACK_01_RECURSIVE(wt, val, n-1, W)\n   )");

        tc.putCode("Java", "Iterative", "// 0/1 Knapsack - Iterative Tabulation (Bottom-Up)\npublic class KnapsackIterative {\n    public static int knapsack(int[] wt, int[] val, int W) {\n        int n = wt.length;\n        int[][] dp = new int[n + 1][W + 1];\n\n        // Build table dp[][] in bottom-up manner\n        for (int i = 1; i <= n; i++) {\n            for (int w = 1; w <= W; w++) {\n                if (wt[i - 1] <= w) {\n                    dp[i][w] = Math.max(val[i - 1] + dp[i - 1][w - wt[i - 1]], dp[i - 1][w]);\n                } else {\n                    dp[i][w] = dp[i - 1][w];\n                }\n            }\n        }\n        return dp[n][W];\n    }\n}", true);
        tc.putCode("Java", "Functions", "// 0/1 Knapsack - Modular Functional Approach\npublic class KnapsackFunctional {\n    private static int pickBest(int includeVal, int excludeVal) {\n        return Math.max(includeVal, excludeVal);\n    }\n\n    public static int solveKnapsack(int[] wt, int[] val, int W) {\n        int n = wt.length;\n        int[][] dp = new int[n + 1][W + 1];\n\n        for (int i = 1; i <= n; i++) {\n            for (int w = 1; w <= W; w++) {\n                int exclude = dp[i - 1][w];\n                int include = (wt[i - 1] <= w) ? val[i - 1] + dp[i - 1][w - wt[i - 1]] : 0;\n                dp[i][w] = pickBest(include, exclude);\n            }\n        }\n        return dp[n][W];\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// 0/1 Knapsack - Recursive with Memoization\npublic class KnapsackRecursive {\n    public static int knapSack(int[] wt, int[] val, int W) {\n        int n = wt.length;\n        int[][] memo = new int[n + 1][W + 1];\n        for (int[] row : memo) java.util.Arrays.fill(row, -1);\n        return knapSackRec(wt, val, W, n, memo);\n    }\n\n    private static int knapSackRec(int[] wt, int[] val, int W, int n, int[][] memo) {\n        // Base case: no items remaining or capacity is 0\n        if (n == 0 || W == 0) return 0;\n        if (memo[n][W] != -1) return memo[n][W];\n\n        // If weight of current item exceeds remaining capacity, exclude it\n        if (wt[n - 1] > W) {\n            return memo[n][W] = knapSackRec(wt, val, W, n - 1, memo);\n        }\n        // Return maximum of including or excluding current item\n        int include = val[n - 1] + knapSackRec(wt, val, W - wt[n - 1], n - 1, memo);\n        int exclude = knapSackRec(wt, val, W, n - 1, memo);\n        return memo[n][W] = Math.max(include, exclude);\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* 0/1 Knapsack - Iterative Tabulation (C) */\n#include <stdio.h>\n\nint max(int a, int b) { return (a > b) ? a : b; }\n\nint knapsackIterative(int wt[], int val[], int n, int W) {\n    int dp[n + 1][W + 1];\n    for (int i = 0; i <= n; i++) {\n        for (int w = 0; w <= W; w++) {\n            if (i == 0 || w == 0)\n                dp[i][w] = 0;\n            else if (wt[i - 1] <= w)\n                dp[i][w] = max(val[i - 1] + dp[i - 1][w - wt[i - 1]], dp[i - 1][w]);\n            else\n                dp[i][w] = dp[i - 1][w];\n        }\n    }\n    return dp[n][W];\n}", true);
        tc.putCode("C", "Functions", "/* 0/1 Knapsack - Functional Modular (C) */\n#include <stdio.h>\n\nint maxVal(int a, int b) { return (a > b) ? a : b; }\n\nint evaluateChoice(int itemWt, int itemVal, int currentW, int prevBest) {\n    if (itemWt <= currentW) return itemVal + prevBest;\n    return 0;\n}\n\nint knapsackFunctional(int wt[], int val[], int n, int W) {\n    int dp[n + 1][W + 1];\n    for (int i = 0; i <= n; i++) {\n        for (int w = 0; w <= W; w++) {\n            if (i == 0 || w == 0) {\n                dp[i][w] = 0;\n            } else {\n                int inc = (wt[i - 1] <= w) ? val[i - 1] + dp[i - 1][w - wt[i - 1]] : 0;\n                int exc = dp[i - 1][w];\n                dp[i][w] = maxVal(inc, exc);\n            }\n        }\n    }\n    return dp[n][W];\n}", true);
        tc.putCode("C", "Recursion", "/* 0/1 Knapsack - Recursive (C) */\n#include <stdio.h>\n\nint max(int a, int b) { return (a > b) ? a : b; }\n\nint knapSackRec(int W, int wt[], int val[], int n) {\n    // Base case\n    if (n == 0 || W == 0) return 0;\n    // Exclude if overweight\n    if (wt[n - 1] > W)\n        return knapSackRec(W, wt, val, n - 1);\n    // Return max of included and excluded\n    return max(\n        val[n - 1] + knapSackRec(W - wt[n - 1], wt, val, n - 1),\n        knapSackRec(W, wt, val, n - 1)\n    );\n}", true);

        tc.putCode("Python", "Iterative", "# 0/1 Knapsack - Iterative Tabulation (Python)\ndef knapsack_iterative(wt: list[int], val: list[int], W: int) -> int:\n    n = len(wt)\n    dp = [[0] * (W + 1) for _ in range(n + 1)]\n\n    # Bottom-up population\n    for i in range(1, n + 1):\n        for w in range(1, W + 1):\n            if wt[i - 1] <= w:\n                dp[i][w] = max(val[i - 1] + dp[i - 1][w - wt[i - 1]], dp[i - 1][w])\n            else:\n                dp[i][w] = dp[i - 1][w]\n\n    return dp[n][W]\n", true);
        tc.putCode("Python", "Functions", "# 0/1 Knapsack - Functional Modular (Python)\ndef knapsack_functional(wt: list[int], val: list[int], W: int) -> int:\n    n = len(wt)\n\n    def transition(i: int, w: int, dp: list[list[int]]) -> int:\n        exclude = dp[i - 1][w]\n        include = (val[i - 1] + dp[i - 1][w - wt[i - 1]]) if wt[i - 1] <= w else 0\n        return max(include, exclude)\n\n    dp = [[0] * (W + 1) for _ in range(n + 1)]\n    for i in range(1, n + 1):\n        for w in range(1, W + 1):\n            dp[i][w] = transition(i, w, dp)\n    return dp[n][W]\n", true);
        tc.putCode("Python", "Recursion", "# 0/1 Knapsack - Recursive with Memoization (Python)\ndef knapsack_recursive(wt: list[int], val: list[int], W: int) -> int:\n    memo = {}\n\n    def solve(n: int, capacity: int) -> int:\n        if n == 0 or capacity == 0:\n            return 0\n        if (n, capacity) in memo:\n            return memo[(n, capacity)]\n\n        if wt[n - 1] > capacity:\n            res = solve(n - 1, capacity)\n        else:\n            res = max(\n                val[n - 1] + solve(n - 1, capacity - wt[n - 1]),\n                solve(n - 1, capacity)\n            )\n        memo[(n, capacity)] = res\n        return res\n\n    return solve(len(wt), W)\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerLcs(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "dp_lcs",
            "Longest Common Subsequence (LCS)",
            "Find the longest subsequence present in both strings in the same relative order (not necessarily contiguous).\n\nIf characters match at S1[i-1] == S2[j-1], dp[i][j] = 1 + dp[i-1][j-1]. Otherwise dp[i][j] = max(dp[i-1][j], dp[i][j-1]).",
            "Git diff file comparison and DNA sequence similarity alignment.",
            "O(M * N)", "O(M * N)", "O(M * N)", "O(M * N)",
            new int[] {65, 66, 67, 68, 69},
            3
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: LCS_ITERATIVE(s1, s2)\n1. Let m = length(s1), n = length(s2).\n2. Initialize dp[m+1][n+1] with zeros.\n3. For i from 1 to m:\n     For j from 1 to n:\n       If s1[i-1] == s2[j-1]:\n         dp[i][j] = 1 + dp[i-1][j-1]\n       Else:\n         dp[i][j] = max(dp[i-1][j], dp[i][j-1])\n4. Return dp[m][n].");
        tc.algorithmByStyle.put("Functions", "Algorithm: LCS_FUNCTIONAL(s1, s2)\nFUNCTION MATCH_SCORE(s1, s2, i, j, dp):\n  If s1[i-1] == s2[j-1]: return 1 + dp[i-1][j-1]\n  return max(dp[i-1][j], dp[i][j-1])\n\nFUNCTION LCS(s1, s2):\n1. Initialize grid dp of size (len(s1)+1) x (len(s2)+1).\n2. Populate grid using MATCH_SCORE.\n3. Return dp[m][n].");
        tc.algorithmByStyle.put("Recursion", "Algorithm: LCS_RECURSIVE(s1, s2, m, n)\n1. Base case: If m == 0 or n == 0, return 0.\n2. If s1[m-1] == s2[n-1]:\n     return 1 + LCS_RECURSIVE(s1, s2, m-1, n-1)\n3. Else:\n     return max(LCS_RECURSIVE(s1, s2, m-1, n), LCS_RECURSIVE(s1, s2, m, n-1))");

        tc.putCode("Java", "Iterative", "// LCS - Iterative Tabulation (Bottom-Up)\npublic class LcsIterative {\n    public static int longestCommonSubsequence(String s1, String s2) {\n        int m = s1.length(), n = s2.length();\n        int[][] dp = new int[m + 1][n + 1];\n\n        for (int i = 1; i <= m; i++) {\n            for (int j = 1; j <= n; j++) {\n                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {\n                    dp[i][j] = 1 + dp[i - 1][j - 1];\n                } else {\n                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);\n                }\n            }\n        }\n        return dp[m][n];\n    }\n}", true);
        tc.putCode("Java", "Functions", "// LCS - Functional Modular\npublic class LcsFunctional {\n    private static int step(String s1, String s2, int i, int j, int[][] dp) {\n        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {\n            return 1 + dp[i - 1][j - 1];\n        }\n        return Math.max(dp[i - 1][j], dp[i][j - 1]);\n    }\n\n    public static int longestCommonSubsequence(String s1, String s2) {\n        int m = s1.length(), n = s2.length();\n        int[][] dp = new int[m + 1][n + 1];\n\n        for (int i = 1; i <= m; i++) {\n            for (int j = 1; j <= n; j++) {\n                dp[i][j] = step(s1, s2, i, j, dp);\n            }\n        }\n        return dp[m][n];\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// LCS - Recursive with Memoization\npublic class LcsRecursive {\n    public static int longestCommonSubsequence(String s1, String s2) {\n        int m = s1.length(), n = s2.length();\n        int[][] memo = new int[m + 1][n + 1];\n        for (int[] row : memo) java.util.Arrays.fill(row, -1);\n        return solve(s1, s2, m, n, memo);\n    }\n\n    private static int solve(String s1, String s2, int i, int j, int[][] memo) {\n        if (i == 0 || j == 0) return 0;\n        if (memo[i][j] != -1) return memo[i][j];\n\n        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {\n            return memo[i][j] = 1 + solve(s1, s2, i - 1, j - 1, memo);\n        }\n        return memo[i][j] = Math.max(solve(s1, s2, i - 1, j, memo), solve(s1, s2, i, j - 1, memo));\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* LCS - Iterative Tabulation (C) */\n#include <stdio.h>\n#include <string.h>\n\nint maxVal(int a, int b) { return (a > b) ? a : b; }\n\nint lcsIterative(const char* s1, const char* s2) {\n    int m = strlen(s1), n = strlen(s2);\n    int dp[m + 1][n + 1];\n\n    for (int i = 0; i <= m; i++) {\n        for (int j = 0; j <= n; j++) {\n            if (i == 0 || j == 0)\n                dp[i][j] = 0;\n            else if (s1[i - 1] == s2[j - 1])\n                dp[i][j] = 1 + dp[i - 1][j - 1];\n            else\n                dp[i][j] = maxVal(dp[i - 1][j], dp[i][j - 1]);\n        }\n    }\n    return dp[m][n];\n}", true);
        tc.putCode("C", "Functions", "/* LCS - Functional Modular (C) */\n#include <stdio.h>\n#include <string.h>\n\nint max(int a, int b) { return (a > b) ? a : b; }\n\nint cellVal(char c1, char c2, int diag, int up, int left) {\n    if (c1 == c2) return 1 + diag;\n    return max(up, left);\n}\n\nint lcsFunctional(const char* s1, const char* s2) {\n    int m = strlen(s1), n = strlen(s2);\n    int dp[m + 1][n + 1];\n    for (int i = 0; i <= m; i++) dp[i][0] = 0;\n    for (int j = 0; j <= n; j++) dp[0][j] = 0;\n\n    for (int i = 1; i <= m; i++) {\n        for (int j = 1; j <= n; j++) {\n            dp[i][j] = cellVal(s1[i - 1], s2[j - 1], dp[i - 1][j - 1], dp[i - 1][j], dp[i - 1][j - 1]);\n        }\n    }\n    return dp[m][n];\n}", true);
        tc.putCode("C", "Recursion", "/* LCS - Recursive (C) */\n#include <stdio.h>\n\nint max(int a, int b) { return (a > b) ? a : b; }\n\nint lcsRec(const char* s1, const char* s2, int m, int n) {\n    if (m == 0 || n == 0) return 0;\n    if (s1[m - 1] == s2[n - 1])\n        return 1 + lcsRec(s1, s2, m - 1, n - 1);\n    return max(lcsRec(s1, s2, m - 1, n), lcsRec(s1, s2, m, n - 1));\n}", true);

        tc.putCode("Python", "Iterative", "# LCS - Iterative Tabulation (Python)\ndef lcs_iterative(s1: str, s2: str) -> int:\n    m, n = len(s1), len(s2)\n    dp = [[0] * (n + 1) for _ in range(m + 1)]\n\n    for i in range(1, m + 1):\n        for j in range(1, n + 1):\n            if s1[i - 1] == s2[j - 1]:\n                dp[i][j] = 1 + dp[i - 1][j - 1]\n            else:\n                dp[i][j] = max(dp[i - 1][j], dp[i][j - 1])\n\n    return dp[m][n]\n", true);
        tc.putCode("Python", "Functions", "# LCS - Functional Modular (Python)\ndef lcs_functional(s1: str, s2: str) -> int:\n    m, n = len(s1), len(s2)\n\n    def transition(i: int, j: int, table: list[list[int]]) -> int:\n        if s1[i - 1] == s2[j - 1]:\n            return 1 + table[i - 1][j - 1]\n        return max(table[i - 1][j], table[i][j - 1])\n\n    dp = [[0] * (n + 1) for _ in range(m + 1)]\n    for i in range(1, m + 1):\n        for j in range(1, n + 1):\n            dp[i][j] = transition(i, j, dp)\n    return dp[m][n]\n", true);
        tc.putCode("Python", "Recursion", "# LCS - Recursive with Memoization (Python)\ndef lcs_recursive(s1: str, s2: str) -> int:\n    memo = {}\n\n    def solve(i: int, j: int) -> int:\n        if i == 0 or j == 0:\n            return 0\n        if (i, j) in memo:\n            return memo[(i, j)]\n\n        if s1[i - 1] == s2[j - 1]:\n            res = 1 + solve(i - 1, j - 1)\n        else:\n            res = max(solve(i - 1, j), solve(i, j - 1))\n        memo[(i, j)] = res\n        return res\n\n    return solve(len(s1), len(s2))\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerLis(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "dp_lis",
            "Longest Increasing Subsequence (LIS)",
            "Find the length of the longest subsequence such that all elements of the subsequence are sorted in strictly increasing order.\n\ndp[i] stores the length of the LIS ending at index i. For every j < i where nums[j] < nums[i], dp[i] = max(dp[i], dp[j] + 1).",
            "Predicting upward trends in stock price history and card game ordering.",
            "O(N log N)", "O(N^2)", "O(N^2)", "O(N)",
            new int[] {10, 22, 9, 33, 21, 50, 41, 60},
            5
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: LIS_ITERATIVE(nums)\n1. If nums is empty, return 0.\n2. Initialize array dp of size N with 1.\n3. For i from 1 to N - 1:\n     For j from 0 to i - 1:\n       If nums[j] < nums[i]:\n         dp[i] = max(dp[i], dp[j] + 1)\n4. Return maximum value in dp array.");
        tc.algorithmByStyle.put("Functions", "Algorithm: LIS_FUNCTIONAL(nums)\nFUNCTION EXTEND_SUBSEQUENCE(nums, dp, i):\n  For j from 0 to i-1:\n    If nums[j] < nums[i]:\n      dp[i] = max(dp[i], dp[j] + 1)\n\nFUNCTION LIS(nums):\n1. Initialize dp array with 1.\n2. For each index i, call EXTEND_SUBSEQUENCE.\n3. Return max(dp).");
        tc.algorithmByStyle.put("Recursion", "Algorithm: LIS_RECURSIVE(nums, n, max_ref)\n1. Base Case: If n == 1, return 1.\n2. Set max_ending_here = 1.\n3. For i from 1 to n - 1:\n     res = LIS_RECURSIVE(nums, i, max_ref)\n     If nums[i-1] < nums[n-1] and res + 1 > max_ending_here:\n       max_ending_here = res + 1\n4. max_ref = max(max_ref, max_ending_here)\n5. Return max_ending_here.");

        tc.putCode("Java", "Iterative", "// LIS - Iterative Dynamic Programming O(N^2)\npublic class LisIterative {\n    public static int lengthOfLIS(int[] nums) {\n        if (nums == null || nums.length == 0) return 0;\n        int n = nums.length;\n        int[] dp = new int[n];\n        java.util.Arrays.fill(dp, 1);\n        int maxLen = 1;\n\n        for (int i = 1; i < n; i++) {\n            for (int j = 0; j < i; j++) {\n                if (nums[j] < nums[i]) {\n                    dp[i] = Math.max(dp[i], dp[j] + 1);\n                }\n            }\n            maxLen = Math.max(maxLen, dp[i]);\n        }\n        return maxLen;\n    }\n}", true);
        tc.putCode("Java", "Functions", "// LIS - Functional Modular\npublic class LisFunctional {\n    private static void updateAt(int[] nums, int[] dp, int i) {\n        for (int j = 0; j < i; j++) {\n            if (nums[j] < nums[i]) {\n                dp[i] = Math.max(dp[i], dp[j] + 1);\n            }\n        }\n    }\n\n    public static int lengthOfLIS(int[] nums) {\n        if (nums == null || nums.length == 0) return 0;\n        int[] dp = new int[nums.length];\n        java.util.Arrays.fill(dp, 1);\n        for (int i = 1; i < nums.length; i++) {\n            updateAt(nums, dp, i);\n        }\n        int max = 0;\n        for (int val : dp) max = Math.max(max, val);\n        return max;\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// LIS - Recursive with Memoization\npublic class LisRecursive {\n    public static int lengthOfLIS(int[] nums) {\n        if (nums == null || nums.length == 0) return 0;\n        int[][] memo = new int[nums.length][nums.length + 1];\n        for (int[] row : memo) java.util.Arrays.fill(row, -1);\n        return solve(nums, 0, -1, memo);\n    }\n\n    private static int solve(int[] nums, int curr, int prev, int[][] memo) {\n        if (curr == nums.length) return 0;\n        if (memo[curr][prev + 1] != -1) return memo[curr][prev + 1];\n\n        int take = 0;\n        if (prev == -1 || nums[curr] > nums[prev]) {\n            take = 1 + solve(nums, curr + 1, curr, memo);\n        }\n        int skip = solve(nums, curr + 1, prev, memo);\n        return memo[curr][prev + 1] = Math.max(take, skip);\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* LIS - Iterative O(N^2) (C) */\n#include <stdio.h>\n\nint lengthOfLIS(int nums[], int n) {\n    if (n <= 0) return 0;\n    int dp[n];\n    for (int i = 0; i < n; i++) dp[i] = 1;\n    int maxLen = 1;\n\n    for (int i = 1; i < n; i++) {\n        for (int j = 0; j < i; j++) {\n            if (nums[j] < nums[i] && dp[j] + 1 > dp[i]) {\n                dp[i] = dp[j] + 1;\n            }\n        }\n        if (dp[i] > maxLen) maxLen = dp[i];\n    }\n    return maxLen;\n}", true);
        tc.putCode("C", "Functions", "/* LIS - Functional Modular (C) */\n#include <stdio.h>\n\nvoid relax(int nums[], int dp[], int i) {\n    for (int j = 0; j < i; j++) {\n        if (nums[j] < nums[i] && dp[j] + 1 > dp[i]) {\n            dp[i] = dp[j] + 1;\n        }\n    }\n}\n\nint lisFunctional(int nums[], int n) {\n    if (n <= 0) return 0;\n    int dp[n];\n    for (int i = 0; i < n; i++) dp[i] = 1;\n    for (int i = 1; i < n; i++) relax(nums, dp, i);\n\n    int maxLen = 1;\n    for (int i = 0; i < n; i++) {\n        if (dp[i] > maxLen) maxLen = dp[i];\n    }\n    return maxLen;\n}", true);
        tc.putCode("C", "Recursion", "/* LIS - Recursive (C) */\n#include <stdio.h>\n\nint lisRec(int nums[], int n, int* max_ref) {\n    if (n == 1) return 1;\n    int res, max_ending_here = 1;\n\n    for (int i = 1; i < n; i++) {\n        res = lisRec(nums, i, max_ref);\n        if (nums[i - 1] < nums[n - 1] && res + 1 > max_ending_here) {\n            max_ending_here = res + 1;\n        }\n    }\n    if (*max_ref < max_ending_here) *max_ref = max_ending_here;\n    return max_ending_here;\n}", true);

        tc.putCode("Python", "Iterative", "# LIS - Iterative Tabulation (Python)\ndef length_of_lis_iterative(nums: list[int]) -> int:\n    if not nums:\n        return 0\n    n = len(nums)\n    dp = [1] * n\n\n    for i in range(1, n):\n        for j in range(i):\n            if nums[j] < nums[i]:\n                dp[i] = max(dp[i], dp[j] + 1)\n\n    return max(dp)\n", true);
        tc.putCode("Python", "Functions", "# LIS - Functional Modular (Python)\ndef length_of_lis_functional(nums: list[int]) -> int:\n    if not nums:\n        return 0\n\n    def compute_lis_for_index(i: int, dp: list[int]):\n        for j in range(i):\n            if nums[j] < nums[i]:\n                dp[i] = max(dp[i], dp[j] + 1)\n\n    dp = [1] * len(nums)\n    for i in range(1, len(nums)):\n        compute_lis_for_index(i, dp)\n    return max(dp)\n", true);
        tc.putCode("Python", "Recursion", "# LIS - Recursive with Memoization (Python)\ndef length_of_lis_recursive(nums: list[int]) -> int:\n    memo = {}\n\n    def solve(curr: int, prev: int) -> int:\n        if curr == len(nums):\n            return 0\n        if (curr, prev) in memo:\n            return memo[(curr, prev)]\n\n        take = 0\n        if prev == -1 or nums[curr] > nums[prev]:\n            take = 1 + solve(curr + 1, curr)\n        skip = solve(curr + 1, prev)\n\n        memo[(curr, prev)] = max(take, skip)\n        return memo[(curr, prev)]\n\n    return solve(0, -1)\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerCoinChange(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "dp_coin_change",
            "Coin Change (Min Coins & Ways)",
            "Given coins of different denominations and an integer amount, compute the fewest number of coins needed to make up that amount.\n\nIf that amount of money cannot be made up by any combination of the coins, return -1.",
            "Automated vending machines dispensing exact change using the fewest possible coins.",
            "O(N * Amount)", "O(N * Amount)", "O(N * Amount)", "O(Amount)",
            new int[] {1, 2, 5},
            11
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: COIN_CHANGE_ITERATIVE(coins, amount)\n1. Create array dp of size amount + 1, filled with amount + 1.\n2. Set dp[0] = 0.\n3. For i from 1 to amount:\n     For each coin c in coins:\n       If c <= i:\n         dp[i] = min(dp[i], 1 + dp[i - c])\n4. Return dp[amount] > amount ? -1 : dp[amount].");
        tc.algorithmByStyle.put("Functions", "Algorithm: COIN_CHANGE_FUNCTIONAL(coins, amount)\nFUNCTION TRY_COIN(coin, target, dp):\n  If coin <= target:\n    return 1 + dp[target - coin]\n  return dp[target]\n\nFUNCTION COIN_CHANGE(coins, amount):\n1. Initialize dp array with sentinel value.\n2. For each sub-amount, update minimum using TRY_COIN.\n3. Return result.");
        tc.algorithmByStyle.put("Recursion", "Algorithm: COIN_CHANGE_RECURSIVE(coins, amount, memo)\n1. Base cases: If amount == 0 return 0; if amount < 0 return infinity.\n2. If amount is in memo, return memo[amount].\n3. min_coins = infinity.\n4. For each coin c in coins:\n     res = COIN_CHANGE_RECURSIVE(coins, amount - c, memo)\n     If res != infinity:\n       min_coins = min(min_coins, 1 + res)\n5. Store and return min_coins.");

        tc.putCode("Java", "Iterative", "// Coin Change - Iterative Dynamic Programming\npublic class CoinChangeIterative {\n    public static int coinChange(int[] coins, int amount) {\n        int[] dp = new int[amount + 1];\n        java.util.Arrays.fill(dp, amount + 1);\n        dp[0] = 0;\n\n        for (int i = 1; i <= amount; i++) {\n            for (int coin : coins) {\n                if (coin <= i) {\n                    dp[i] = Math.min(dp[i], 1 + dp[i - coin]);\n                }\n            }\n        }\n        return dp[amount] > amount ? -1 : dp[amount];\n    }\n}", true);
        tc.putCode("Java", "Functions", "// Coin Change - Functional Modular\npublic class CoinChangeFunctional {\n    private static int updateMin(int currentMin, int coin, int amount, int[] dp) {\n        if (coin <= amount && dp[amount - coin] != Integer.MAX_VALUE) {\n            return Math.min(currentMin, 1 + dp[amount - coin]);\n        }\n        return currentMin;\n    }\n\n    public static int coinChange(int[] coins, int amount) {\n        int[] dp = new int[amount + 1];\n        java.util.Arrays.fill(dp, Integer.MAX_VALUE);\n        dp[0] = 0;\n\n        for (int i = 1; i <= amount; i++) {\n            for (int coin : coins) {\n                dp[i] = updateMin(dp[i], coin, i, dp);\n            }\n        }\n        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// Coin Change - Recursive with Memoization\npublic class CoinChangeRecursive {\n    public static int coinChange(int[] coins, int amount) {\n        int[] memo = new int[amount + 1];\n        return solve(coins, amount, memo);\n    }\n\n    private static int solve(int[] coins, int rem, int[] memo) {\n        if (rem < 0) return -1;\n        if (rem == 0) return 0;\n        if (memo[rem] != 0) return memo[rem];\n\n        int min = Integer.MAX_VALUE;\n        for (int coin : coins) {\n            int res = solve(coins, rem - coin, memo);\n            if (res >= 0 && res < min) {\n                min = 1 + res;\n            }\n        }\n        memo[rem] = (min == Integer.MAX_VALUE) ? -1 : min;\n        return memo[rem];\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* Coin Change - Iterative Tabulation (C) */\n#include <stdio.h>\n\nint minCoinsIterative(int coins[], int numCoins, int amount) {\n    int dp[amount + 1];\n    for (int i = 1; i <= amount; i++) dp[i] = amount + 1;\n    dp[0] = 0;\n\n    for (int i = 1; i <= amount; i++) {\n        for (int j = 0; j < numCoins; j++) {\n            if (coins[j] <= i && dp[i - coins[j]] + 1 < dp[i]) {\n                dp[i] = dp[i - coins[j]] + 1;\n            }\n        }\n    }\n    return (dp[amount] > amount) ? -1 : dp[amount];\n}", true);
        tc.putCode("C", "Functions", "/* Coin Change - Functional Modular (C) */\n#include <stdio.h>\n\nint tryCoin(int currentMin, int coin, int target, int dp[]) {\n    if (coin <= target && dp[target - coin] + 1 < currentMin) {\n        return dp[target - coin] + 1;\n    }\n    return currentMin;\n}\n\nint coinChangeFunctional(int coins[], int n, int amount) {\n    int dp[amount + 1];\n    for (int i = 1; i <= amount; i++) dp[i] = amount + 1;\n    dp[0] = 0;\n\n    for (int i = 1; i <= amount; i++) {\n        for (int j = 0; j < n; j++) {\n            dp[i] = tryCoin(dp[i], coins[j], i, dp);\n        }\n    }\n    return (dp[amount] > amount) ? -1 : dp[amount];\n}", true);
        tc.putCode("C", "Recursion", "/* Coin Change - Recursive (C) */\n#include <stdio.h>\n#include <limits.h>\n\nint minCoinsRec(int coins[], int n, int amount) {\n    if (amount == 0) return 0;\n    int res = INT_MAX;\n\n    for (int i = 0; i < n; i++) {\n        if (coins[i] <= amount) {\n            int sub_res = minCoinsRec(coins, n, amount - coins[i]);\n            if (sub_res != INT_MAX && sub_res + 1 < res) {\n                res = sub_res + 1;\n            }\n        }\n    }\n    return res;\n}", true);

        tc.putCode("Python", "Iterative", "# Coin Change - Iterative Tabulation (Python)\ndef coin_change_iterative(coins: list[int], amount: int) -> int:\n    dp = [amount + 1] * (amount + 1)\n    dp[0] = 0\n\n    for i in range(1, amount + 1):\n        for coin in coins:\n            if coin <= i:\n                dp[i] = min(dp[i], 1 + dp[i - coin])\n\n    return dp[amount] if dp[amount] <= amount else -1\n", true);
        tc.putCode("Python", "Functions", "# Coin Change - Functional Modular (Python)\ndef coin_change_functional(coins: list[int], amount: int) -> int:\n    def evaluate_amount(curr: int, dp: list[int]) -> int:\n        valid_options = [1 + dp[curr - c] for c in coins if c <= curr]\n        return min(valid_options) if valid_options else amount + 1\n\n    dp = [0] + [amount + 1] * amount\n    for i in range(1, amount + 1):\n        dp[i] = evaluate_amount(i, dp)\n    return dp[amount] if dp[amount] <= amount else -1\n", true);
        tc.putCode("Python", "Recursion", "# Coin Change - Recursive with Memoization (Python)\ndef coin_change_recursive(coins: list[int], amount: int) -> int:\n    memo = {}\n\n    def solve(rem: int) -> int:\n        if rem == 0:\n            return 0\n        if rem < 0:\n            return -1\n        if rem in memo:\n            return memo[rem]\n\n        min_coins = float('inf')\n        for coin in coins:\n            res = solve(rem - coin)\n            if res >= 0 and res < min_coins:\n                min_coins = 1 + res\n\n        ans = min_coins if min_coins != float('inf') else -1\n        memo[rem] = ans\n        return ans\n\n    return solve(amount)\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerMatrixChain(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "dp_matrix_chain",
            "Matrix Chain Multiplication",
            "Given a sequence of matrices, find the most efficient way to multiply these matrices together.\n\nThe problem is not actually to perform the multiplications, but merely to decide in which order to perform the multiplications to minimize scalar multiplications.",
            "Optimizing complex 3D graphic transformation pipelines and database query join trees.",
            "O(N^3)", "O(N^3)", "O(N^3)", "O(N^2)",
            new int[] {10, 20, 30, 40, 30},
            4
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: MATRIX_CHAIN_ITERATIVE(p, n)\n1. Allocate table m[n][n] initialized to 0.\n2. For chain length L from 2 to n-1:\n     For i from 1 to n - L:\n       j = i + L - 1\n       m[i][j] = infinity\n       For k from i to j - 1:\n         cost = m[i][k] + m[k+1][j] + p[i-1]*p[k]*p[j]\n         m[i][j] = min(m[i][j], cost)\n3. Return m[1][n-1].");
        tc.algorithmByStyle.put("Functions", "Algorithm: MATRIX_CHAIN_FUNCTIONAL(p, n)\nFUNCTION CALCULATE_SPLIT(m, p, i, j, k):\n  return m[i][k] + m[k+1][j] + p[i-1]*p[k]*p[j]\n\nFUNCTION SOLVE_MCM(p, n):\n1. Loop through chain lengths L=2..n-1.\n2. Optimize each interval [i, j] over split points k.\n3. Return m[1][n-1].");
        tc.algorithmByStyle.put("Recursion", "Algorithm: MATRIX_CHAIN_RECURSIVE(p, i, j)\n1. Base Case: If i == j return 0.\n2. min_cost = infinity.\n3. For k from i to j - 1:\n     cost = MATRIX_CHAIN_RECURSIVE(p, i, k) + MATRIX_CHAIN_RECURSIVE(p, k+1, j) + p[i-1]*p[k]*p[j]\n     min_cost = min(min_cost, cost)\n4. Return min_cost.");

        tc.putCode("Java", "Iterative", "// Matrix Chain Multiplication - Iterative O(N^3)\npublic class MatrixChainIterative {\n    public static int matrixChainOrder(int[] p) {\n        int n = p.length;\n        int[][] m = new int[n][n];\n\n        // L is chain length\n        for (int L = 2; L < n; L++) {\n            for (int i = 1; i < n - L + 1; i++) {\n                int j = i + L - 1;\n                m[i][j] = Integer.MAX_VALUE;\n                for (int k = i; k <= j - 1; k++) {\n                    int q = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];\n                    if (q < m[i][j]) {\n                        m[i][j] = q;\n                    }\n                }\n            }\n        }\n        return m[1][n - 1];\n    }\n}", true);
        tc.putCode("Java", "Functions", "// Matrix Chain Multiplication - Functional Modular\npublic class MatrixChainFunctional {\n    private static int evaluateSplit(int[][] m, int[] p, int i, int j, int k) {\n        return m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];\n    }\n\n    public static int matrixChainOrder(int[] p) {\n        int n = p.length;\n        int[][] m = new int[n][n];\n\n        for (int L = 2; L < n; L++) {\n            for (int i = 1; i <= n - L; i++) {\n                int j = i + L - 1;\n                m[i][j] = Integer.MAX_VALUE;\n                for (int k = i; k < j; k++) {\n                    m[i][j] = Math.min(m[i][j], evaluateSplit(m, p, i, j, k));\n                }\n            }\n        }\n        return m[1][n - 1];\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// Matrix Chain Multiplication - Recursive with Memoization\npublic class MatrixChainRecursive {\n    public static int matrixChainOrder(int[] p) {\n        int n = p.length;\n        int[][] memo = new int[n][n];\n        for (int[] row : memo) java.util.Arrays.fill(row, -1);\n        return solve(p, 1, n - 1, memo);\n    }\n\n    private static int solve(int[] p, int i, int j, int[][] memo) {\n        if (i >= j) return 0;\n        if (memo[i][j] != -1) return memo[i][j];\n\n        int minCost = Integer.MAX_VALUE;\n        for (int k = i; k < j; k++) {\n            int cost = solve(p, i, k, memo) + solve(p, k + 1, j, memo) + p[i - 1] * p[k] * p[j];\n            minCost = Math.min(minCost, cost);\n        }\n        return memo[i][j] = minCost;\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* Matrix Chain Multiplication - Iterative (C) */\n#include <stdio.h>\n#include <limits.h>\n\nint matrixChainIterative(int p[], int n) {\n    int m[n][n];\n    for (int i = 1; i < n; i++) m[i][i] = 0;\n\n    for (int L = 2; L < n; L++) {\n        for (int i = 1; i < n - L + 1; i++) {\n            int j = i + L - 1;\n            m[i][j] = INT_MAX;\n            for (int k = i; k <= j - 1; k++) {\n                int q = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];\n                if (q < m[i][j]) m[i][j] = q;\n            }\n        }\n    }\n    return m[1][n - 1];\n}", true);
        tc.putCode("C", "Functions", "/* Matrix Chain Multiplication - Functional (C) */\n#include <stdio.h>\n#include <limits.h>\n\nint calcCost(int m[][100], int p[], int i, int j, int k) {\n    return m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];\n}\n\nint matrixChainFunctional(int p[], int n) {\n    int m[100][100] = {0};\n    for (int L = 2; L < n; L++) {\n        for (int i = 1; i < n - L + 1; i++) {\n            int j = i + L - 1;\n            m[i][j] = INT_MAX;\n            for (int k = i; k < j; k++) {\n                int cost = calcCost(m, p, i, j, k);\n                if (cost < m[i][j]) m[i][j] = cost;\n            }\n        }\n    }\n    return m[1][n - 1];\n}", true);
        tc.putCode("C", "Recursion", "/* Matrix Chain Multiplication - Recursive (C) */\n#include <stdio.h>\n#include <limits.h>\n\nint matrixChainRec(int p[], int i, int j) {\n    if (i == j) return 0;\n    int min = INT_MAX;\n\n    for (int k = i; k < j; k++) {\n        int count = matrixChainRec(p, i, k) + matrixChainRec(p, k + 1, j) + p[i - 1] * p[k] * p[j];\n        if (count < min) min = count;\n    }\n    return min;\n}", true);

        tc.putCode("Python", "Iterative", "# Matrix Chain Multiplication - Iterative (Python)\ndef matrix_chain_iterative(p: list[int]) -> int:\n    n = len(p)\n    m = [[0] * n for _ in range(n)]\n\n    for L in range(2, n):\n        for i in range(1, n - L + 1):\n            j = i + L - 1\n            m[i][j] = float('inf')\n            for k in range(i, j):\n                q = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j]\n                if q < m[i][j]:\n                    m[i][j] = q\n\n    return m[1][n - 1]\n", true);
        tc.putCode("Python", "Functions", "# Matrix Chain Multiplication - Functional (Python)\ndef matrix_chain_functional(p: list[int]) -> int:\n    n = len(p)\n    m = [[0] * n for _ in range(n)]\n\n    def cost(i: int, j: int, k: int) -> int:\n        return m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j]\n\n    for L in range(2, n):\n        for i in range(1, n - L + 1):\n            j = i + L - 1\n            m[i][j] = min(cost(i, j, k) for k in range(i, j))\n\n    return m[1][n - 1]\n", true);
        tc.putCode("Python", "Recursion", "# Matrix Chain Multiplication - Recursive with Memoization (Python)\ndef matrix_chain_recursive(p: list[int]) -> int:\n    memo = {}\n\n    def solve(i: int, j: int) -> int:\n        if i >= j:\n            return 0\n        if (i, j) in memo:\n            return memo[(i, j)]\n\n        min_cost = float('inf')\n        for k in range(i, j):\n            c = solve(i, k) + solve(k + 1, j) + p[i - 1] * p[k] * p[j]\n            min_cost = min(min_cost, c)\n\n        memo[(i, j)] = min_cost\n        return min_cost\n\n    return solve(1, len(p) - 1)\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerEditDistance(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "dp_edit_distance",
            "Edit Distance (Levenshtein)",
            "Find minimum operations (Insert, Delete, Replace) required to convert string word1 to string word2.\n\ndp[i][j] stores min operations to convert word1[0..i-1] to word2[0..j-1].",
            "Autocorrect spelling suggestions and DNA alignment scoring in bioinformatics.",
            "O(M * N)", "O(M * N)", "O(M * N)", "O(M * N)",
            new int[] {104, 111, 114, 115, 101},
            5
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: EDIT_DISTANCE_ITERATIVE(word1, word2)\n1. Let m = len(word1), n = len(word2).\n2. Initialize dp[m+1][n+1]. Set dp[i][0] = i, dp[0][j] = j.\n3. For i from 1 to m:\n     For j from 1 to n:\n       If word1[i-1] == word2[j-1]:\n         dp[i][j] = dp[i-1][j-1]\n       Else:\n         dp[i][j] = 1 + min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])\n4. Return dp[m][n].");
        tc.algorithmByStyle.put("Functions", "Algorithm: EDIT_DISTANCE_FUNCTIONAL(w1, w2)\nFUNCTION STEP_COST(w1, w2, i, j, dp):\n  If w1[i-1] == w2[j-1]: return dp[i-1][j-1]\n  return 1 + min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])\n\nFUNCTION MIN_DISTANCE(w1, w2):\n1. Initialize table with base deletion/insertion counts.\n2. Compute each cell using STEP_COST.\n3. Return dp[m][n].");
        tc.algorithmByStyle.put("Recursion", "Algorithm: EDIT_DISTANCE_RECURSIVE(w1, w2, m, n)\n1. Base Cases: If m == 0 return n; if n == 0 return m.\n2. If w1[m-1] == w2[n-1]:\n     return EDIT_DISTANCE_RECURSIVE(w1, w2, m-1, n-1)\n3. Return 1 + min(\n     EDIT_DISTANCE_RECURSIVE(w1, w2, m, n-1),   // Insert\n     EDIT_DISTANCE_RECURSIVE(w1, w2, m-1, n),   // Remove\n     EDIT_DISTANCE_RECURSIVE(w1, w2, m-1, n-1) // Replace\n   )");

        tc.putCode("Java", "Iterative", "// Edit Distance - Iterative Dynamic Programming\npublic class EditDistanceIterative {\n    public static int minDistance(String word1, String word2) {\n        int m = word1.length(), n = word2.length();\n        int[][] dp = new int[m + 1][n + 1];\n\n        for (int i = 0; i <= m; i++) dp[i][0] = i;\n        for (int j = 0; j <= n; j++) dp[0][j] = j;\n\n        for (int i = 1; i <= m; i++) {\n            for (int j = 1; j <= n; j++) {\n                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {\n                    dp[i][j] = dp[i - 1][j - 1];\n                } else {\n                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1],\n                                   Math.min(dp[i - 1][j], dp[i][j - 1]));\n                }\n            }\n        }\n        return dp[m][n];\n    }\n}", true);
        tc.putCode("Java", "Functions", "// Edit Distance - Functional Modular\npublic class EditDistanceFunctional {\n    private static int min3(int a, int b, int c) {\n        return Math.min(a, Math.min(b, c));\n    }\n\n    public static int minDistance(String w1, String w2) {\n        int m = w1.length(), n = w2.length();\n        int[][] dp = new int[m + 1][n + 1];\n        for (int i = 0; i <= m; i++) dp[i][0] = i;\n        for (int j = 0; j <= n; j++) dp[0][j] = j;\n\n        for (int i = 1; i <= m; i++) {\n            for (int j = 1; j <= n; j++) {\n                if (w1.charAt(i - 1) == w2.charAt(j - 1)) {\n                    dp[i][j] = dp[i - 1][j - 1];\n                } else {\n                    dp[i][j] = 1 + min3(dp[i - 1][j - 1], dp[i - 1][j], dp[i][j - 1]);\n                }\n            }\n        }\n        return dp[m][n];\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// Edit Distance - Recursive with Memoization\npublic class EditDistanceRecursive {\n    public static int minDistance(String word1, String word2) {\n        int m = word1.length(), n = word2.length();\n        int[][] memo = new int[m + 1][n + 1];\n        for (int[] row : memo) java.util.Arrays.fill(row, -1);\n        return solve(word1, word2, m, n, memo);\n    }\n\n    private static int solve(String w1, String w2, int i, int j, int[][] memo) {\n        if (i == 0) return j;\n        if (j == 0) return i;\n        if (memo[i][j] != -1) return memo[i][j];\n\n        if (w1.charAt(i - 1) == w2.charAt(j - 1)) {\n            return memo[i][j] = solve(w1, w2, i - 1, j - 1, memo);\n        }\n        int insertOp = solve(w1, w2, i, j - 1, memo);\n        int deleteOp = solve(w1, w2, i - 1, j, memo);\n        int replaceOp = solve(w1, w2, i - 1, j - 1, memo);\n        return memo[i][j] = 1 + Math.min(replaceOp, Math.min(insertOp, deleteOp));\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* Edit Distance - Iterative (C) */\n#include <stdio.h>\n#include <string.h>\n\nint min3(int a, int b, int c) {\n    int m = (a < b) ? a : b;\n    return (m < c) ? m : c;\n}\n\nint minDistance(const char* word1, const char* word2) {\n    int m = strlen(word1), n = strlen(word2);\n    int dp[m + 1][n + 1];\n\n    for (int i = 0; i <= m; i++) dp[i][0] = i;\n    for (int j = 0; j <= n; j++) dp[0][j] = j;\n\n    for (int i = 1; i <= m; i++) {\n        for (int j = 1; j <= n; j++) {\n            if (word1[i - 1] == word2[j - 1])\n                dp[i][j] = dp[i - 1][j - 1];\n            else\n                dp[i][j] = 1 + min3(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1]);\n        }\n    }\n    return dp[m][n];\n}", true);
        tc.putCode("C", "Functions", "/* Edit Distance - Functional Modular (C) */\n#include <stdio.h>\n#include <string.h>\n\nint minVal(int a, int b) { return (a < b) ? a : b; }\n\nint transformCost(char c1, char c2, int rep, int del, int ins) {\n    if (c1 == c2) return rep;\n    return 1 + minVal(rep, minVal(del, ins));\n}\n\nint editDistanceFunctional(const char* w1, const char* w2) {\n    int m = strlen(w1), n = strlen(w2);\n    int dp[m + 1][n + 1];\n    for (int i = 0; i <= m; i++) dp[i][0] = i;\n    for (int j = 0; j <= n; j++) dp[0][j] = j;\n\n    for (int i = 1; i <= m; i++) {\n        for (int j = 1; j <= n; j++) {\n            dp[i][j] = transformCost(w1[i - 1], w2[j - 1], dp[i - 1][j - 1], dp[i - 1][j], dp[i][j - 1]);\n        }\n    }\n    return dp[m][n];\n}", true);
        tc.putCode("C", "Recursion", "/* Edit Distance - Recursive (C) */\n#include <stdio.h>\n\nint min3(int a, int b, int c) {\n    int m = (a < b) ? a : b;\n    return (m < c) ? m : c;\n}\n\nint editDistRec(const char* s1, const char* s2, int m, int n) {\n    if (m == 0) return n;\n    if (n == 0) return m;\n\n    if (s1[m - 1] == s2[n - 1])\n        return editDistRec(s1, s2, m - 1, n - 1);\n\n    return 1 + min3(\n        editDistRec(s1, s2, m, n - 1),   // Insert\n        editDistRec(s1, s2, m - 1, n),   // Remove\n        editDistRec(s1, s2, m - 1, n - 1) // Replace\n    );\n}", true);

        tc.putCode("Python", "Iterative", "# Edit Distance - Iterative Tabulation (Python)\ndef min_distance_iterative(word1: str, word2: str) -> int:\n    m, n = len(word1), len(word2)\n    dp = [[0] * (n + 1) for _ in range(m + 1)]\n\n    for i in range(m + 1):\n        dp[i][0] = i\n    for j in range(n + 1):\n        dp[0][j] = j\n\n    for i in range(1, m + 1):\n        for j in range(1, n + 1):\n            if word1[i - 1] == word2[j - 1]:\n                dp[i][j] = dp[i - 1][j - 1]\n            else:\n                dp[i][j] = 1 + min(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1])\n\n    return dp[m][n]\n", true);
        tc.putCode("Python", "Functions", "# Edit Distance - Functional Modular (Python)\ndef min_distance_functional(w1: str, w2: str) -> int:\n    m, n = len(w1), len(w2)\n\n    def transition(i: int, j: int, dp: list[list[int]]) -> int:\n        if w1[i - 1] == w2[j - 1]:\n            return dp[i - 1][j - 1]\n        return 1 + min(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1])\n\n    dp = [[0] * (n + 1) for _ in range(m + 1)]\n    for i in range(m + 1): dp[i][0] = i\n    for j in range(n + 1): dp[0][j] = j\n\n    for i in range(1, m + 1):\n        for j in range(1, n + 1):\n            dp[i][j] = transition(i, j, dp)\n    return dp[m][n]\n", true);
        tc.putCode("Python", "Recursion", "# Edit Distance - Recursive with Memoization (Python)\ndef min_distance_recursive(w1: str, w2: str) -> int:\n    memo = {}\n\n    def solve(i: int, j: int) -> int:\n        if i == 0: return j\n        if j == 0: return i\n        if (i, j) in memo: return memo[(i, j)]\n\n        if w1[i - 1] == w2[j - 1]:\n            res = solve(i - 1, j - 1)\n        else:\n            res = 1 + min(\n                solve(i, j - 1),     # Insert\n                solve(i - 1, j),     # Delete\n                solve(i - 1, j - 1)  # Replace\n            )\n        memo[(i, j)] = res\n        return res\n\n    return solve(len(w1), len(w2))\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerRodCutting(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "dp_rod_cutting",
            "Rod Cutting Problem",
            "Given a rod of length N and an array of prices that contains prices of all pieces of size smaller than N, determine the maximum value obtainable by cutting up the rod and selling the pieces.",
            "Industrial manufacturing cutting timber, steel rods, or fabric rolls to maximize total revenue.",
            "O(N^2)", "O(N^2)", "O(N^2)", "O(N)",
            new int[] {1, 5, 8, 9, 10, 17, 17, 20},
            8
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: ROD_CUTTING_ITERATIVE(price, n)\n1. Allocate array val of size n + 1, set val[0] = 0.\n2. For i from 1 to n:\n     max_val = -infinity\n     For j from 0 to i - 1:\n       max_val = max(max_val, price[j] + val[i - j - 1])\n     val[i] = max_val\n3. Return val[n].");
        tc.algorithmByStyle.put("Functions", "Algorithm: ROD_CUTTING_FUNCTIONAL(price, n)\nFUNCTION MAX_REVENUE_FOR_LEN(i, price, val):\n  max_so_far = 0\n  For j from 0 to i-1:\n    max_so_far = max(max_so_far, price[j] + val[i - j - 1])\n  return max_so_far\n\nFUNCTION CUT_ROD(price, n):\n1. Build up val array iteratively using MAX_REVENUE_FOR_LEN.\n2. Return val[n].");
        tc.algorithmByStyle.put("Recursion", "Algorithm: ROD_CUTTING_RECURSIVE(price, n)\n1. Base Case: If n <= 0 return 0.\n2. max_val = -infinity.\n3. For i from 0 to n - 1:\n     max_val = max(max_val, price[i] + ROD_CUTTING_RECURSIVE(price, n - i - 1))\n4. Return max_val.");

        tc.putCode("Java", "Iterative", "// Rod Cutting - Iterative Tabulation\npublic class RodCuttingIterative {\n    public static int cutRod(int[] price, int n) {\n        int[] val = new int[n + 1];\n        val[0] = 0;\n\n        for (int i = 1; i <= n; i++) {\n            int maxVal = Integer.MIN_VALUE;\n            for (int j = 0; j < i; j++) {\n                maxVal = Math.max(maxVal, price[j] + val[i - j - 1]);\n            }\n            val[i] = maxVal;\n        }\n        return val[n];\n    }\n}", true);
        tc.putCode("Java", "Functions", "// Rod Cutting - Functional Modular\npublic class RodCuttingFunctional {\n    private static int bestCut(int i, int[] price, int[] val) {\n        int best = Integer.MIN_VALUE;\n        for (int j = 0; j < i; j++) {\n            best = Math.max(best, price[j] + val[i - j - 1]);\n        }\n        return best;\n    }\n\n    public static int cutRod(int[] price, int n) {\n        int[] val = new int[n + 1];\n        for (int i = 1; i <= n; i++) {\n            val[i] = bestCut(i, price, val);\n        }\n        return val[n];\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// Rod Cutting - Recursive with Memoization\npublic class RodCuttingRecursive {\n    public static int cutRod(int[] price, int n) {\n        int[] memo = new int[n + 1];\n        java.util.Arrays.fill(memo, -1);\n        return solve(price, n, memo);\n    }\n\n    private static int solve(int[] price, int n, int[] memo) {\n        if (n <= 0) return 0;\n        if (memo[n] != -1) return memo[n];\n\n        int maxVal = Integer.MIN_VALUE;\n        for (int i = 0; i < n; i++) {\n            maxVal = Math.max(maxVal, price[i] + solve(price, n - i - 1, memo));\n        }\n        return memo[n] = maxVal;\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* Rod Cutting - Iterative (C) */\n#include <stdio.h>\n#include <limits.h>\n\nint cutRodIterative(int price[], int n) {\n    int val[n + 1];\n    val[0] = 0;\n\n    for (int i = 1; i <= n; i++) {\n        int maxVal = INT_MIN;\n        for (int j = 0; j < i; j++) {\n            int current = price[j] + val[i - j - 1];\n            if (current > maxVal) maxVal = current;\n        }\n        val[i] = maxVal;\n    }\n    return val[n];\n}", true);
        tc.putCode("C", "Functions", "/* Rod Cutting - Functional (C) */\n#include <stdio.h>\n#include <limits.h>\n\nint findMaxCut(int length, int price[], int val[]) {\n    int maxVal = INT_MIN;\n    for (int j = 0; j < length; j++) {\n        int rev = price[j] + val[length - j - 1];\n        if (rev > maxVal) maxVal = rev;\n    }\n    return maxVal;\n}\n\nint cutRodFunctional(int price[], int n) {\n    int val[n + 1];\n    val[0] = 0;\n    for (int i = 1; i <= n; i++) {\n        val[i] = findMaxCut(i, price, val);\n    }\n    return val[n];\n}", true);
        tc.putCode("C", "Recursion", "/* Rod Cutting - Recursive (C) */\n#include <stdio.h>\n#include <limits.h>\n\nint cutRodRec(int price[], int n) {\n    if (n <= 0) return 0;\n    int maxVal = INT_MIN;\n\n    for (int i = 0; i < n; i++) {\n        int current = price[i] + cutRodRec(price, n - i - 1);\n        if (current > maxVal) maxVal = current;\n    }\n    return maxVal;\n}", true);

        tc.putCode("Python", "Iterative", "# Rod Cutting - Iterative Tabulation (Python)\ndef cut_rod_iterative(price: list[int], n: int) -> int:\n    val = [0] * (n + 1)\n\n    for i in range(1, n + 1):\n        max_val = -float('inf')\n        for j in range(i):\n            max_val = max(max_val, price[j] + val[i - j - 1])\n        val[i] = max_val\n\n    return val[n]\n", true);
        tc.putCode("Python", "Functions", "# Rod Cutting - Functional (Python)\ndef cut_rod_functional(price: list[int], n: int) -> int:\n    val = [0] * (n + 1)\n\n    def optimize_length(i: int) -> int:\n        return max(price[j] + val[i - j - 1] for j in range(i))\n\n    for i in range(1, n + 1):\n        val[i] = optimize_length(i)\n    return val[n]\n", true);
        tc.putCode("Python", "Recursion", "# Rod Cutting - Recursive with Memoization (Python)\ndef cut_rod_recursive(price: list[int], n: int) -> int:\n    memo = {}\n\n    def solve(rem: int) -> int:\n        if rem <= 0:\n            return 0\n        if rem in memo:\n            return memo[rem]\n\n        max_val = -float('inf')\n        for i in range(rem):\n            max_val = max(max_val, price[i] + solve(rem - i - 1))\n\n        memo[rem] = max_val\n        return max_val\n\n    return solve(n)\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerSubsetSum(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "dp_subset_sum",
            "Subset Sum Problem",
            "Given a set of non-negative integers and a target sum, determine if there is a subset of the given set with sum equal to target sum.\n\ndp[i][j] is true if sum j can be achieved using a subset of elements up to index i.",
            "Financial fraud detection matching total transactions against target account balance gaps.",
            "O(N * Sum)", "O(N * Sum)", "O(N * Sum)", "O(N * Sum)",
            new int[] {3, 34, 4, 12, 5, 2},
            9
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: SUBSET_SUM_ITERATIVE(arr, sum)\n1. Let n = len(arr). Allocate dp[n+1][sum+1] initialized to false.\n2. Set dp[i][0] = true for all i from 0 to n (sum 0 always possible).\n3. For i from 1 to n:\n     For j from 1 to sum:\n       If arr[i-1] <= j:\n         dp[i][j] = dp[i-1][j] OR dp[i-1][j - arr[i-1]]\n       Else:\n         dp[i][j] = dp[i-1][j]\n4. Return dp[n][sum].");
        tc.algorithmByStyle.put("Functions", "Algorithm: SUBSET_SUM_FUNCTIONAL(arr, sum)\nFUNCTION EVAL_SUBSET(arr, dp, i, j):\n  If arr[i-1] <= j: return dp[i-1][j] || dp[i-1][j - arr[i-1]]\n  return dp[i-1][j]\n\nFUNCTION IS_SUBSET_SUM(arr, sum):\n1. Initialize table with base column true.\n2. Populate boolean DP grid with EVAL_SUBSET.\n3. Return dp[n][sum].");
        tc.algorithmByStyle.put("Recursion", "Algorithm: SUBSET_SUM_RECURSIVE(arr, n, sum)\n1. Base Cases:\n     If sum == 0 return true.\n     If n == 0 and sum != 0 return false.\n2. If arr[n-1] > sum:\n     return SUBSET_SUM_RECURSIVE(arr, n-1, sum)\n3. Return SUBSET_SUM_RECURSIVE(arr, n-1, sum) OR SUBSET_SUM_RECURSIVE(arr, n-1, sum - arr[n-1])");

        tc.putCode("Java", "Iterative", "// Subset Sum - Iterative Dynamic Programming\npublic class SubsetSumIterative {\n    public static boolean isSubsetSum(int[] set, int sum) {\n        int n = set.length;\n        boolean[][] dp = new boolean[n + 1][sum + 1];\n\n        // Sum 0 is always achievable with empty subset\n        for (int i = 0; i <= n; i++) dp[i][0] = true;\n\n        for (int i = 1; i <= n; i++) {\n            for (int j = 1; j <= sum; j++) {\n                if (set[i - 1] <= j) {\n                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - set[i - 1]];\n                } else {\n                    dp[i][j] = dp[i - 1][j];\n                }\n            }\n        }\n        return dp[n][sum];\n    }\n}", true);
        tc.putCode("Java", "Functions", "// Subset Sum - Functional Modular\npublic class SubsetSumFunctional {\n    private static boolean canForm(int elem, int target, int i, boolean[][] dp) {\n        if (elem <= target) {\n            return dp[i - 1][target] || dp[i - 1][target - elem];\n        }\n        return dp[i - 1][target];\n    }\n\n    public static boolean isSubsetSum(int[] set, int sum) {\n        int n = set.length;\n        boolean[][] dp = new boolean[n + 1][sum + 1];\n        for (int i = 0; i <= n; i++) dp[i][0] = true;\n\n        for (int i = 1; i <= n; i++) {\n            for (int j = 1; j <= sum; j++) {\n                dp[i][j] = canForm(set[i - 1], j, i, dp);\n            }\n        }\n        return dp[n][sum];\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// Subset Sum - Recursive with Memoization\npublic class SubsetSumRecursive {\n    public static boolean isSubsetSum(int[] set, int sum) {\n        int n = set.length;\n        Boolean[][] memo = new Boolean[n + 1][sum + 1];\n        return solve(set, n, sum, memo);\n    }\n\n    private static boolean solve(int[] set, int n, int sum, Boolean[][] memo) {\n        if (sum == 0) return true;\n        if (n == 0) return false;\n        if (memo[n][sum] != null) return memo[n][sum];\n\n        if (set[n - 1] > sum) {\n            return memo[n][sum] = solve(set, n - 1, sum, memo);\n        }\n        return memo[n][sum] = solve(set, n - 1, sum, memo) ||\n                              solve(set, n - 1, sum - set[n - 1], memo);\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* Subset Sum - Iterative Tabulation (C) */\n#include <stdio.h>\n#include <stdbool.h>\n\nbool isSubsetSumIterative(int set[], int n, int sum) {\n    bool dp[n + 1][sum + 1];\n    for (int i = 0; i <= n; i++) dp[i][0] = true;\n    for (int j = 1; j <= sum; j++) dp[0][j] = false;\n\n    for (int i = 1; i <= n; i++) {\n        for (int j = 1; j <= sum; j++) {\n            if (set[i - 1] <= j)\n                dp[i][j] = dp[i - 1][j] || dp[i - 1][j - set[i - 1]];\n            else\n                dp[i][j] = dp[i - 1][j];\n        }\n    }\n    return dp[n][sum];\n}", true);
        tc.putCode("C", "Functions", "/* Subset Sum - Functional (C) */\n#include <stdio.h>\n#include <stdbool.h>\n\nbool evaluateCell(int elem, int target, bool exc, bool inc) {\n    if (elem <= target) return exc || inc;\n    return exc;\n}\n\nbool isSubsetSumFunctional(int set[], int n, int sum) {\n    bool dp[n + 1][sum + 1];\n    for (int i = 0; i <= n; i++) dp[i][0] = true;\n    for (int j = 1; j <= sum; j++) dp[0][j] = false;\n\n    for (int i = 1; i <= n; i++) {\n        for (int j = 1; j <= sum; j++) {\n            bool inc = (set[i - 1] <= j) ? dp[i - 1][j - set[i - 1]] : false;\n            dp[i][j] = evaluateCell(set[i - 1], j, dp[i - 1][j], inc);\n        }\n    }\n    return dp[n][sum];\n}", true);
        tc.putCode("C", "Recursion", "/* Subset Sum - Recursive (C) */\n#include <stdio.h>\n#include <stdbool.h>\n\nbool isSubsetSumRec(int set[], int n, int sum) {\n    if (sum == 0) return true;\n    if (n == 0) return false;\n\n    if (set[n - 1] > sum)\n        return isSubsetSumRec(set, n - 1, sum);\n\n    return isSubsetSumRec(set, n - 1, sum) ||\n           isSubsetSumRec(set, n - 1, sum - set[n - 1]);\n}", true);

        tc.putCode("Python", "Iterative", "# Subset Sum - Iterative Tabulation (Python)\ndef is_subset_sum_iterative(set_arr: list[int], target: int) -> bool:\n    n = len(set_arr)\n    dp = [[False] * (target + 1) for _ in range(n + 1)]\n\n    for i in range(n + 1):\n        dp[i][0] = True\n\n    for i in range(1, n + 1):\n        for j in range(1, target + 1):\n            if set_arr[i - 1] <= j:\n                dp[i][j] = dp[i - 1][j] or dp[i - 1][j - set_arr[i - 1]]\n            else:\n                dp[i][j] = dp[i - 1][j]\n\n    return dp[n][target]\n", true);
        tc.putCode("Python", "Functions", "# Subset Sum - Functional (Python)\ndef is_subset_sum_functional(set_arr: list[int], target: int) -> bool:\n    n = len(set_arr)\n\n    def transition(i: int, j: int, dp: list[list[bool]]) -> bool:\n        if set_arr[i - 1] <= j:\n            return dp[i - 1][j] or dp[i - 1][j - set_arr[i - 1]]\n        return dp[i - 1][j]\n\n    dp = [[False] * (target + 1) for _ in range(n + 1)]\n    for i in range(n + 1): dp[i][0] = True\n\n    for i in range(1, n + 1):\n        for j in range(1, target + 1):\n            dp[i][j] = transition(i, j, dp)\n    return dp[n][target]\n", true);
        tc.putCode("Python", "Recursion", "# Subset Sum - Recursive with Memoization (Python)\ndef is_subset_sum_recursive(set_arr: list[int], target: int) -> bool:\n    memo = {}\n\n    def solve(n: int, s: int) -> bool:\n        if s == 0: return True\n        if n == 0: return False\n        if (n, s) in memo: return memo[(n, s)]\n\n        if set_arr[n - 1] > s:\n            res = solve(n - 1, s)\n        else:\n            res = solve(n - 1, s) or solve(n - 1, s - set_arr[n - 1])\n\n        memo[(n, s)] = res\n        return res\n\n    return solve(len(set_arr), target)\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerKadane(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "dp_kadane",
            "Maximum Subarray (Kadane's Algorithm)",
            "Find the contiguous subarray within a one-dimensional array of numbers that has the largest sum in O(n) linear time.\n\nAt each index i, we decide whether to add nums[i] to the running subarray or start a fresh subarray at nums[i]: currentMax = max(nums[i], currentMax + nums[i]).",
            "Tracking the most profitable consecutive days in stock market fluctuations.",
            "O(n)", "O(n)", "O(n)", "O(1)",
            new int[] {-2, 1, -3, 4, -1, 2, 1, -5, 4},
            6
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: KADANE_ITERATIVE(nums)\n1. Set maxSoFar = nums[0], currentMax = nums[0].\n2. For i from 1 to len(nums) - 1:\n     currentMax = max(nums[i], currentMax + nums[i])\n     maxSoFar = max(maxSoFar, currentMax)\n3. Return maxSoFar.");
        tc.algorithmByStyle.put("Functions", "Algorithm: KADANE_FUNCTIONAL(nums)\nFUNCTION EXTEND_OR_RESET(currentMax, num):\n  return max(num, currentMax + num)\n\nFUNCTION MAX_SUBARRAY(nums):\n1. Initialize currentMax and maxSoFar with nums[0].\n2. Iterate through elements updating with EXTEND_OR_RESET.\n3. Return maxSoFar.");
        tc.algorithmByStyle.put("Recursion", "Algorithm: MAX_SUBARRAY_RECURSIVE_DIVIDE_CONQUER(nums, left, right)\n1. If left == right: return nums[left].\n2. mid = (left + right) / 2.\n3. leftMax = MAX_SUBARRAY_RECURSIVE_DIVIDE_CONQUER(nums, left, mid).\n4. rightMax = MAX_SUBARRAY_RECURSIVE_DIVIDE_CONQUER(nums, mid + 1, right).\n5. crossMax = MAX_CROSSING_SUBARRAY(nums, left, mid, right).\n6. Return max(leftMax, rightMax, crossMax).");

        tc.putCode("Java", "Iterative", "// Maximum Subarray - Kadane's Algorithm Iterative O(N)\npublic class KadaneIterative {\n    public static int maxSubArray(int[] nums) {\n        int maxSoFar = nums[0];\n        int currentMax = nums[0];\n\n        for (int i = 1; i < nums.length; i++) {\n            currentMax = Math.max(nums[i], currentMax + nums[i]);\n            maxSoFar = Math.max(maxSoFar, currentMax);\n        }\n        return maxSoFar;\n    }\n}", true);
        tc.putCode("Java", "Functions", "// Maximum Subarray - Functional Modular\npublic class KadaneFunctional {\n    private static int nextSum(int currentMax, int val) {\n        return Math.max(val, currentMax + val);\n    }\n\n    public static int maxSubArray(int[] nums) {\n        int maxSoFar = nums[0];\n        int currentMax = nums[0];\n\n        for (int i = 1; i < nums.length; i++) {\n            currentMax = nextSum(currentMax, nums[i]);\n            maxSoFar = Math.max(maxSoFar, currentMax);\n        }\n        return maxSoFar;\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// Maximum Subarray - Recursive Divide and Conquer O(N log N)\npublic class KadaneRecursive {\n    public static int maxSubArray(int[] nums) {\n        return solve(nums, 0, nums.length - 1);\n    }\n\n    private static int solve(int[] nums, int left, int right) {\n        if (left == right) return nums[left];\n        int mid = left + (right - left) / 2;\n\n        int leftMax = solve(nums, left, mid);\n        int rightMax = solve(nums, mid + 1, right);\n        int crossMax = maxCrossing(nums, left, mid, right);\n\n        return Math.max(Math.max(leftMax, rightMax), crossMax);\n    }\n\n    private static int maxCrossing(int[] nums, int left, int mid, int right) {\n        int sum = 0, leftSum = Integer.MIN_VALUE;\n        for (int i = mid; i >= left; i--) {\n            sum += nums[i];\n            if (sum > leftSum) leftSum = sum;\n        }\n        sum = 0;\n        int rightSum = Integer.MIN_VALUE;\n        for (int i = mid + 1; i <= right; i++) {\n            sum += nums[i];\n            if (sum > rightSum) rightSum = sum;\n        }\n        return leftSum + rightSum;\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* Maximum Subarray - Kadane's Algorithm (C) */\n#include <stdio.h>\n\nint maxVal(int a, int b) { return (a > b) ? a : b; }\n\nint maxSubArrayIterative(int nums[], int n) {\n    int maxSoFar = nums[0];\n    int currentMax = nums[0];\n\n    for (int i = 1; i < n; i++) {\n        currentMax = maxVal(nums[i], currentMax + nums[i]);\n        maxSoFar = maxVal(maxSoFar, currentMax);\n    }\n    return maxSoFar;\n}", true);
        tc.putCode("C", "Functions", "/* Maximum Subarray - Functional Modular (C) */\n#include <stdio.h>\n\nint extendOrReset(int currentMax, int num) {\n    return (num > currentMax + num) ? num : (currentMax + num);\n}\n\nint maxSubArrayFunctional(int nums[], int n) {\n    int maxSoFar = nums[0];\n    int currentMax = nums[0];\n\n    for (int i = 1; i < n; i++) {\n        currentMax = extendOrReset(currentMax, nums[i]);\n        if (currentMax > maxSoFar) maxSoFar = currentMax;\n    }\n    return maxSoFar;\n}", true);
        tc.putCode("C", "Recursion", "/* Maximum Subarray - Recursive Divide and Conquer (C) */\n#include <stdio.h>\n#include <limits.h>\n\nint max3(int a, int b, int c) {\n    int m = (a > b) ? a : b;\n    return (m > c) ? m : c;\n}\n\nint maxCrossing(int nums[], int l, int m, int h) {\n    int sum = 0, leftSum = INT_MIN;\n    for (int i = m; i >= l; i--) {\n        sum += nums[i];\n        if (sum > leftSum) leftSum = sum;\n    }\n    sum = 0;\n    int rightSum = INT_MIN;\n    for (int i = m + 1; i <= h; i++) {\n        sum += nums[i];\n        if (sum > rightSum) rightSum = sum;\n    }\n    return leftSum + rightSum;\n}\n\nint maxSubArrayRec(int nums[], int l, int h) {\n    if (l == h) return nums[l];\n    int m = (l + h) / 2;\n    return max3(\n        maxSubArrayRec(nums, l, m),\n        maxSubArrayRec(nums, m + 1, h),\n        maxCrossing(nums, l, m, h)\n    );\n}", true);

        tc.putCode("Python", "Iterative", "# Maximum Subarray - Kadane's Algorithm Iterative (Python)\ndef max_sub_array_iterative(nums: list[int]) -> int:\n    max_so_far = nums[0]\n    current_max = nums[0]\n\n    for x in nums[1:]:\n        current_max = max(x, current_max + x)\n        max_so_far = max(max_so_far, current_max)\n\n    return max_so_far\n", true);
        tc.putCode("Python", "Functions", "# Maximum Subarray - Functional Modular (Python)\ndef max_sub_array_functional(nums: list[int]) -> int:\n    def extend_or_reset(curr: int, val: int) -> int:\n        return max(val, curr + val)\n\n    max_so_far = current_max = nums[0]\n    for x in nums[1:]:\n        current_max = extend_or_reset(current_max, x)\n        max_so_far = max(max_so_far, current_max)\n    return max_so_far\n", true);
        tc.putCode("Python", "Recursion", "# Maximum Subarray - Recursive Divide and Conquer (Python)\ndef max_sub_array_recursive(nums: list[int]) -> int:\n    def solve(left: int, right: int) -> int:\n        if left == right:\n            return nums[left]\n        mid = (left + right) // 2\n\n        left_max = solve(left, mid)\n        right_max = solve(mid + 1, right)\n        cross_max = max_crossing(left, mid, right)\n\n        return max(left_max, right_max, cross_max)\n\n    def max_crossing(left: int, mid: int, right: int) -> int:\n        total = 0\n        left_sum = -float('inf')\n        for i in range(mid, left - 1, -1):\n            total += nums[i]\n            if total > left_sum:\n                left_sum = total\n\n        total = 0\n        right_sum = -float('inf')\n        for i in range(mid + 1, right + 1):\n            total += nums[i]\n            if total > right_sum:\n                right_sum = total\n\n        return left_sum + right_sum\n\n    return solve(0, len(nums) - 1)\n", true);

        contentMap.put(tc.techniqueId, tc);
    }
}
