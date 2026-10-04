# 📘 Problem 121 of 250 — LeetCode 56: Merge Intervals

> **Pattern:** Intervals | **Roadmap Position:** #121/250

---

LeetCode 56 — Merge Intervals
Problem #121 of 250 | Difficulty: Medium | Pattern: Intervals — Sort and Merge

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 56: Merge Intervals | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Intervals — Sort and Merge

Sort by start time. Process in order. Track current interval's end for merging decisions.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Intervals — Sort and Merge problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Merge Intervals.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Sort by start time. Process in order. Track current interval's end for merging decisions.

This problem is in your roadmap at position #121 because it reinforces
the Intervals — Sort and Merge pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Intervals — Sort and Merge

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Merge Intervals:
  → Find one more problem using the same Intervals — Sort and Merge pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution

---

*Next up: Problem 122 of 250*
