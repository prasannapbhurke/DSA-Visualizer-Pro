# 📘 Problem 227 of 250 — LeetCode 322: Coin Change

> **Pattern:** Unbounded Knapsack | **Roadmap Position:** #227/250

---

LeetCode 1143 — Longest Common Subsequence
Problem #227 of 250 | Difficulty: Medium | Pattern: 2D DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 1143: Longest Common Subsequence | Medium

For DP problems, ask:
  1. What is the "subproblem"? (What smaller answer helps build the bigger answer?)
  2. What do I need to REMEMBER? (What are my DP state variables?)
  3. How does dp[i] relate to dp[i-1] or dp[i][j] to dp[i-1][j]?

DP Type: 2D DP — Classic

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 2D DP

Recurrence Relation:
dp[i][j] = dp[i-1][j-1]+1 if text1[i-1]==text2[j-1]
           else max(dp[i-1][j], dp[i][j-1])

Base Cases:
dp[0][j] = 0, dp[i][0] = 0 (empty string has LCS=0 with anything)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP Thinking Steps:
  1. Define what dp[i] (or dp[i][j]) represents in plain English
  2. Find the recurrence: how does dp[i] depend on previous states?
  3. Set base cases: what are the smallest valid states?
  4. Determine traversal order: which way do you fill the table?
  5. Identify what to return: dp[n]? max(dp)? dp[m][n]?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Example trace:
text1="abcde", text2="ace"

   | '' a c e
 --+---------
 ''|  0 0 0 0
 a |  0 1 1 1
 b |  0 1 1 1
 c |  0 1 2 2
 d |  0 1 2 2
 e |  0 1 2 3

dp[5][3] = 3 ✓ (LCS = "ace")

If chars match: diagonal+1
If not: max(up, left)



━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Fill in the DP table for a small example:
  Draw the table with rows and columns labeled.
  Fill left to right, top to bottom (or the appropriate order).
  Check each cell against your recurrence.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP solves problems with OVERLAPPING SUBPROBLEMS and OPTIMAL SUBSTRUCTURE.
The recurrence relation is the heart of DP.
Once you have the recurrence, the code practically writes itself.

Practice: Can you derive the recurrence WITHOUT looking at the solution?
That's the skill you're building!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# 1D DP:
dp = [0] * (n+1)
dp[0] = base_case_0
dp[1] = base_case_1
for i in range(2, n+1):
    dp[i] = f(dp[i-1], dp[i-2])  # your recurrence
return dp[n]

# 2D DP:
dp = [[0]*(n+1) for _ in range(m+1)]
for i in range(m+1): dp[i][0] = ...  # base cases
for j in range(n+1): dp[0][j] = ...  # base cases
for i in range(1,m+1):
    for j in range(1,n+1):
        dp[i][j] = f(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])  # recurrence

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n) for 1D DP, O(m*n) for 2D DP
Space: O(n) or O(m*n), can often optimize to O(1) or O(n) with rolling array

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Wrong recurrence relation (most common mistake!)
2. Off-by-one in dp array size (use dp[n+1] for n elements)
3. Wrong base cases
4. Wrong traversal direction (sometimes must go right-to-left)
5. Returning wrong part of dp table

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving with full dp array: can you optimize space to O(1)?
Many 1D DP problems only need the previous 1 or 2 values.
Many 2D DP problems only need the previous row.

---

*Next up: Problem 228 of 250*
