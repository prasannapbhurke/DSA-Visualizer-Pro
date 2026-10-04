# 📘 Problem 223 of 250 — LeetCode 746: Min Cost Climbing Stairs

> **Pattern:** 1D DP | **Roadmap Position:** #223/250

---

LeetCode 198 — House Robber
Problem #223 of 250 | Difficulty: Medium | Pattern: 1D DP — Skip

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 198: House Robber | Medium

For DP problems, ask:
  1. What is the "subproblem"? (What smaller answer helps build the bigger answer?)
  2. What do I need to REMEMBER? (What are my DP state variables?)
  3. How does dp[i] relate to dp[i-1] or dp[i][j] to dp[i-1][j]?

DP Type: Linear 1D DP with skip

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 1D DP — Skip

Recurrence Relation:
dp[i] = max(dp[i-1], dp[i-2] + nums[i])
'Either skip house i (take dp[i-1]) or rob house i (dp[i-2]+nums[i])'

Base Cases:
dp[0] = nums[0]
dp[1] = max(nums[0], nums[1])

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
nums = [2,7,9,3,1]

dp[0] = 2
dp[1] = max(2,7) = 7
dp[2] = max(dp[1], dp[0]+9) = max(7, 2+9) = 11
dp[3] = max(dp[2], dp[1]+3) = max(11, 7+3) = 11
dp[4] = max(dp[3], dp[2]+1) = max(11, 11+1) = 12

Return dp[4] = 12 ✓ (rob houses 0,2,4: 2+9+1=12)

Optimization:
prev2=0, prev1=0
for num: curr=max(prev1, prev2+num); prev2=prev1; prev1=curr

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

*Next up: Problem 224 of 250*
