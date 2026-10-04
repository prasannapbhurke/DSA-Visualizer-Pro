# 📘 Problem 249 of 250 — LeetCode 1143: Longest Common Subsequence

> **Pattern:** DP | **Roadmap Position:** #249/250

---

LeetCode 297 — Serialize and Deserialize Binary Tree
Problem #249 of 250 | Difficulty: Hard | Pattern: BFS / DFS

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🎯 FINAL STRETCH — Problem #249 of 250!

LeetCode 297: Serialize and Deserialize Binary Tree | Hard

You've come a long way! This is an advanced problem.
Core technique: BFS / DFS

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: BFS / DFS

At this level:
  You should recognize the pattern immediately.
  You should know WHICH approach to try first.
  You should be able to identify the edge cases.

Key questions for BFS / DFS:
  - What is the state? (DP: dp[i][j] represents what?)
  - What are the transitions? (How does state i depend on i-1?)
  - What is the base case? (Smallest valid state)
  - What is the answer? (Which state or combination gives the final answer?)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For hard problems:
  1. Start with brute force. What's the obvious but slow approach?
  2. Identify what's being recomputed. Can you memoize it?
  3. What is the "subproblem" structure?
  4. Is there a greedy proof? Or must you try all options (DP)?

Don't rush to code. Spend 10+ minutes planning.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Serialize and Deserialize Binary Tree" with pattern BFS / DFS:

Step 1: Define the DP/recursion state clearly.
Step 2: Write the recurrence relation on paper.
Step 3: Handle edge cases.
Step 4: Implement bottom-up (or top-down with memo).
Step 5: Verify with small examples.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Use LeetCode's example 1.
Draw the DP table (for 2D DP problems).
Fill it step by step according to your recurrence.
Verify the final answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

You're solving LeetCode #297 — a Hard problem!

The fact that you've reached problem #249 means:
  ✓ You understand the fundamental patterns
  ✓ You can recognize and apply DSA templates
  ✓ You've built systematic problem-solving skills

Hard problems are just combinations of patterns you already know!
Serialize and Deserialize Binary Tree uses BFS / DFS — you've seen this before.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# For BFS / DFS:
# See previous BFS  problems for the template
# Apply the same structure with this problem's specific twist

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Analyze your solution:
  Time: How many states × time per state?
  Space: How much does your dp table or recursion use?

Target for this problem:
  Hard problems often need O(n²) or O(n log n) — the naive O(n³) or O(2^n) is too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not spending enough time on the state definition
2. Wrong transition direction (especially in interval DP)
3. Missing edge cases for empty strings/arrays
4. Integer overflow for counting problems

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🏆 ACHIEVEMENT UNLOCKED: Problem #249/250

Keep going — 1 more problems to master!

After solving: Think about what GENERAL PATTERN this problem exemplifies.
In an interview, you want to be able to identify the pattern in < 2 minutes.

---

*Next up: Problem 250 of 250*
