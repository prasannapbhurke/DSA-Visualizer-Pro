# 📘 Problem 132 of 250 — LeetCode 704: Binary Search

> **Pattern:** Binary Search | **Roadmap Position:** #132/250

---

LeetCode 704 — Binary Search
Problem #132 of 250 | Difficulty: Easy | Pattern: Binary Search — Classic

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 704: Binary Search | Easy

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search — Classic

Core technique: Binary Search — Classic. Identify what data structure enables O(1) or O(log n) operations.

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

For Binary Search — Classic problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Binary Search.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Core technique: Binary Search — Classic. Identify what data structure enables O(1) or O(log n) operations.

This problem is in your roadmap at position #132 because it reinforces
the Binary Search — Classic pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search — Classic

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

After solving Binary Search:
  → Find one more problem using the same Binary Search — Classic pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution

---

*Next up: Problem 133 of 250*
