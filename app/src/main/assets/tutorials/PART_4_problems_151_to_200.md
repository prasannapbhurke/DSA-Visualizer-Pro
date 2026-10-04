# 📚 DSA Learning Roadmap — Part 1 | Section 4/5
## Core DSA (Problems 151-200): Stacks, Queues, Trees, BFS/DFS

> Study these problems in ORDER. Each one builds on the previous.

---

## Problem 151/250 — LeetCode #78: Subsets

**Pattern:** `Backtracking` | **Position in Roadmap:** #151

```
LeetCode 78 — Subsets
Problem #151 of 250 | Difficulty: Medium | Pattern: Backtracking

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 78: Subsets | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Backtracking

Try each choice. If it works → recurse deeper. If it fails → undo and try next.

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

For Backtracking problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Subsets.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Try each choice. If it works → recurse deeper. If it fails → undo and try next.

This problem is in your roadmap at position #151 because it reinforces
the Backtracking pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Backtracking

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

After solving Subsets:
  → Find one more problem using the same Backtracking pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 152/250 — LeetCode #90: Subsets II

**Pattern:** `Backtracking` | **Position in Roadmap:** #152

```
LeetCode 90 — Subsets II
Problem #152 of 250 | Difficulty: Medium | Pattern: Backtracking with Deduplication

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 90: Subsets II | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Backtracking with Deduplication

Try each choice. If it works → recurse deeper. If it fails → undo and try next.

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

For Backtracking with Deduplication problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Subsets II.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Try each choice. If it works → recurse deeper. If it fails → undo and try next.

This problem is in your roadmap at position #152 because it reinforces
the Backtracking with Deduplication pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Backtracking with Deduplication

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

After solving Subsets II:
  → Find one more problem using the same Backtracking with Deduplication pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 153/250 — LeetCode #77: Combinations

**Pattern:** `Backtracking` | **Position in Roadmap:** #153

```
LeetCode 77 — Combinations
Problem #153 of 250 | Difficulty: Medium | Pattern: Backtracking

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 77: Combinations | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Backtracking

Try each choice. If it works → recurse deeper. If it fails → undo and try next.

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

For Backtracking problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Combinations.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Try each choice. If it works → recurse deeper. If it fails → undo and try next.

This problem is in your roadmap at position #153 because it reinforces
the Backtracking pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Backtracking

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

After solving Combinations:
  → Find one more problem using the same Backtracking pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 154/250 — LeetCode #39: Combination Sum

**Pattern:** `Backtracking` | **Position in Roadmap:** #154

```
LeetCode 39 — Combination Sum
Problem #154 of 250 | Difficulty: Medium | Pattern: Backtracking — Unlimited Reuse

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 39: Combination Sum | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Backtracking — Unlimited Reuse

Try each choice. If it works → recurse deeper. If it fails → undo and try next.

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

For Backtracking — Unlimited Reuse problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Combination Sum.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Try each choice. If it works → recurse deeper. If it fails → undo and try next.

This problem is in your roadmap at position #154 because it reinforces
the Backtracking — Unlimited Reuse pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Backtracking — Unlimited Reuse

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

After solving Combination Sum:
  → Find one more problem using the same Backtracking — Unlimited Reuse pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 155/250 — LeetCode #40: Combination Sum II

**Pattern:** `Backtracking` | **Position in Roadmap:** #155

```
LeetCode 40 — Combination Sum II
Problem #155 of 250 | Difficulty: Medium | Pattern: Backtracking — No Reuse

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 40: Combination Sum II | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Backtracking — No Reuse

Try each choice. If it works → recurse deeper. If it fails → undo and try next.

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

For Backtracking — No Reuse problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Combination Sum II.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Try each choice. If it works → recurse deeper. If it fails → undo and try next.

This problem is in your roadmap at position #155 because it reinforces
the Backtracking — No Reuse pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Backtracking — No Reuse

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

After solving Combination Sum II:
  → Find one more problem using the same Backtracking — No Reuse pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 156/250 — LeetCode #46: Permutations

**Pattern:** `Backtracking` | **Position in Roadmap:** #156

```
LeetCode 46 — Permutations
Problem #156 of 250 | Difficulty: Medium | Pattern: Backtracking

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 46: Permutations | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Backtracking

Try each choice. If it works → recurse deeper. If it fails → undo and try next.

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

For Backtracking problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Permutations.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Try each choice. If it works → recurse deeper. If it fails → undo and try next.

This problem is in your roadmap at position #156 because it reinforces
the Backtracking pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Backtracking

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

After solving Permutations:
  → Find one more problem using the same Backtracking pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 157/250 — LeetCode #47: Permutations II

**Pattern:** `Backtracking` | **Position in Roadmap:** #157

```
LeetCode 47 — Permutations II
Problem #157 of 250 | Difficulty: Medium | Pattern: Backtracking with Deduplication

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 47: Permutations II | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Backtracking with Deduplication

Try each choice. If it works → recurse deeper. If it fails → undo and try next.

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

For Backtracking with Deduplication problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Permutations II.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Try each choice. If it works → recurse deeper. If it fails → undo and try next.

This problem is in your roadmap at position #157 because it reinforces
the Backtracking with Deduplication pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Backtracking with Deduplication

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

After solving Permutations II:
  → Find one more problem using the same Backtracking with Deduplication pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 158/250 — LeetCode #17: Letter Combinations of a Phone Number

**Pattern:** `Backtracking` | **Position in Roadmap:** #158

```
LeetCode 17 — Letter Combinations of a Phone Number
Problem #158 of 250 | Difficulty: Medium | Pattern: Backtracking

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 17: Letter Combinations of a Phone Number | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Backtracking

Try each choice. If it works → recurse deeper. If it fails → undo and try next.

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

For Backtracking problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Letter Combinations of a Phone Number.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Try each choice. If it works → recurse deeper. If it fails → undo and try next.

This problem is in your roadmap at position #158 because it reinforces
the Backtracking pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Backtracking

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

After solving Letter Combinations of a Phone Number:
  → Find one more problem using the same Backtracking pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 159/250 — LeetCode #79: Word Search

**Pattern:** `Backtracking` | **Position in Roadmap:** #159

```
LeetCode 79 — Word Search
Problem #159 of 250 | Difficulty: Medium | Pattern: Backtracking + DFS

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 79: Word Search | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Backtracking + DFS

Try each choice. If it works → recurse deeper. If it fails → undo and try next.

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

For Backtracking + DFS problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Word Search.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Try each choice. If it works → recurse deeper. If it fails → undo and try next.

This problem is in your roadmap at position #159 because it reinforces
the Backtracking + DFS pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Backtracking + DFS

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

After solving Word Search:
  → Find one more problem using the same Backtracking + DFS pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 160/250 — LeetCode #131: Palindrome Partitioning

**Pattern:** `Backtracking` | **Position in Roadmap:** #160

```
LeetCode 131 — Palindrome Partitioning
Problem #160 of 250 | Difficulty: Medium | Pattern: Backtracking + DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 131: Palindrome Partitioning | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Backtracking + DP

Try each choice. If it works → recurse deeper. If it fails → undo and try next.

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

For Backtracking + DP problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Palindrome Partitioning.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Try each choice. If it works → recurse deeper. If it fails → undo and try next.

This problem is in your roadmap at position #160 because it reinforces
the Backtracking + DP pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Backtracking + DP

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

After solving Palindrome Partitioning:
  → Find one more problem using the same Backtracking + DP pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 161/250 — LeetCode #51: N-Queens

**Pattern:** `Backtracking` | **Position in Roadmap:** #161

```
LeetCode 51 — N-Queens
Problem #161 of 250 | Difficulty: Hard | Pattern: Backtracking

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 51: N-Queens | Hard

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Backtracking

Try each choice. If it works → recurse deeper. If it fails → undo and try next.

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

For Backtracking problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for N-Queens.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Try each choice. If it works → recurse deeper. If it fails → undo and try next.

This problem is in your roadmap at position #161 because it reinforces
the Backtracking pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Backtracking

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

After solving N-Queens:
  → Find one more problem using the same Backtracking pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 162/250 — LeetCode #104: Maximum Depth of Binary Tree

**Pattern:** `Tree DFS` | **Position in Roadmap:** #162

```
LeetCode 104 — Maximum Depth of Binary Tree
Problem #162 of 250 | Difficulty: Easy | Pattern: Tree DFS — Post-order

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 104: Maximum Depth of Binary Tree | Easy
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Tree DFS — Post-order

Core Insight:
Depth of tree = 1 + max(depth of left subtree, depth of right subtree). Base case: null node has depth 0.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Recursively compute depth:
  depth(null) = 0
  depth(node) = 1 + max(depth(node.left), depth(node.right))

Or BFS: count levels.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def maxDepth(root):
    if not root: return 0
    return 1 + max(maxDepth(root.left), maxDepth(root.right))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Tree: [3,9,20,null,null,15,7]
       3
      / \
     9   20
        /  \
       15   7

depth(3) = 1 + max(depth(9), depth(20))
depth(9) = 1 + max(0,0) = 1
depth(20) = 1 + max(depth(15), depth(7)) = 1 + max(1,1) = 2
depth(3) = 1 + max(1,2) = 3 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Tree DFS with recursion: let the recursion tree mirror the actual tree structure.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def maxDepth(root):
    if not root: return 0
    return 1 + max(maxDepth(root.left), maxDepth(root.right))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(h) for recursion stack, h=height

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — identify it before coding
2. Confusing DFS vs BFS — DFS uses stack/recursion, BFS uses queue
3. Forgetting to mark visited nodes in graph problems
4. Off-by-one in tree traversals
5. Wrong base cases in DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: identify another problem using the same Tree DFS — Post-order pattern.
Can you solve it without hints?
```

---

## Problem 163/250 — LeetCode #100: Same Tree

**Pattern:** `Tree DFS` | **Position in Roadmap:** #163

```
LeetCode 100 — Same Tree
Problem #163 of 250 | Difficulty: Easy | Pattern: Tree DFS — Structural Comparison

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 100: Same Tree | Easy
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Tree DFS — Structural Comparison

Core Insight:
Two trees are the same if: both null, OR both non-null with same value and same left/right subtrees.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Recurse on both trees simultaneously:
  If both null → True
  If one null → False
  If values differ → False
  Otherwise: isSame(p.left, q.left) AND isSame(p.right, q.right)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def isSameTree(p, q):
    if not p and not q: return True
    if not p or not q or p.val != q.val: return False
    return isSameTree(p.left,q.left) and isSameTree(p.right,q.right)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

p=[1,2,3], q=[1,2,3]
isSame(1,1): vals equal → check children
  isSame(2,2): vals equal → check children
    isSame(null,null): True
    isSame(null,null): True → True
  isSame(3,3): True
→ True ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Structural comparison: recursion naturally handles trees. Check current node, then delegate to children.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def isSameTree(p, q):
    if not p and not q: return True
    if not p or not q or p.val != q.val: return False
    return isSameTree(p.left,q.left) and isSameTree(p.right,q.right)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(h)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — identify it before coding
2. Confusing DFS vs BFS — DFS uses stack/recursion, BFS uses queue
3. Forgetting to mark visited nodes in graph problems
4. Off-by-one in tree traversals
5. Wrong base cases in DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: identify another problem using the same Tree DFS — Structural Comparison pattern.
Can you solve it without hints?
```

---

## Problem 164/250 — LeetCode #226: Invert Binary Tree

**Pattern:** `Tree DFS` | **Position in Roadmap:** #164

```
LeetCode 226 — Invert Binary Tree
Problem #164 of 250 | Difficulty: Easy | Pattern: Tree DFS — Post-order Swap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 226: Invert Binary Tree | Easy
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Tree DFS — Post-order Swap

Core Insight:
Invert = swap left and right children at every node. Recurse on both children first, then swap.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Post-order: process children first, then swap.
  Or pre-order: swap first, then recurse.
  Both work! The key: swap happens at EVERY node.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def invertTree(root):
    if not root: return None
    root.left, root.right = invertTree(root.right), invertTree(root.left)
    return root

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    4              4
   / \            / \
  2   7   →    7   2
 /\  /\       /\  /\
1  3 6  9    9  6 3  1

invert(4): invert(2), invert(7), swap children
invert(2): invert(1)=1, invert(3)=3, swap → right=1,left=3
invert(7): swap similarly

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Tree inversion: recursion handles all nodes. swap(root.left, root.right) at each level.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def invertTree(root):
    if not root: return None
    root.left, root.right = invertTree(root.right), invertTree(root.left)
    return root

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(h)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — identify it before coding
2. Confusing DFS vs BFS — DFS uses stack/recursion, BFS uses queue
3. Forgetting to mark visited nodes in graph problems
4. Off-by-one in tree traversals
5. Wrong base cases in DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: identify another problem using the same Tree DFS — Post-order Swap pattern.
Can you solve it without hints?
```

---

## Problem 165/250 — LeetCode #101: Symmetric Tree

**Pattern:** `Tree DFS` | **Position in Roadmap:** #165

```
LeetCode 101 — Symmetric Tree
Problem #165 of 250 | Difficulty: Easy | Pattern: Tree DFS — Mirror Check

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 101: Symmetric Tree | Easy
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Tree DFS — Mirror Check

Core Insight:
A tree is symmetric if left subtree mirrors right subtree. Compare outer pair and inner pair recursively.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Helper: isMirror(left, right)
  Both null → True
  One null → False
  Values differ → False
  Return isMirror(left.left, right.right) AND isMirror(left.right, right.left)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def isSymmetric(root):
    def mirror(l, r):
        if not l and not r: return True
        if not l or not r or l.val != r.val: return False
        return mirror(l.left, r.right) and mirror(l.right, r.left)
    return mirror(root.left, root.right)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    1
   / \
  2   2
 /\ / \
3 4 4  3

isMirror(2,2): 2==2, check isMirror(3,3) AND isMirror(4,4) → True ✓

    1
   / \
  2   2
   \   \
    3    3
isMirror(2,2): isMirror(null,3)=False → False ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Mirror check: compare outer pair (left.left, right.right) and inner pair (left.right, right.left).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def isSymmetric(root):
    def mirror(l, r):
        if not l and not r: return True
        if not l or not r or l.val != r.val: return False
        return mirror(l.left, r.right) and mirror(l.right, r.left)
    return mirror(root.left, root.right)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(h)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — identify it before coding
2. Confusing DFS vs BFS — DFS uses stack/recursion, BFS uses queue
3. Forgetting to mark visited nodes in graph problems
4. Off-by-one in tree traversals
5. Wrong base cases in DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: identify another problem using the same Tree DFS — Mirror Check pattern.
Can you solve it without hints?
```

---

## Problem 166/250 — LeetCode #112: Path Sum

**Pattern:** `Tree DFS` | **Position in Roadmap:** #166

```
LeetCode 112 — Path Sum
Problem #166 of 250 | Difficulty: Easy | Pattern: Tree DFS — Target Tracking

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 112: Path Sum | Easy
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Tree DFS — Target Tracking

Core Insight:
At each node, subtract node.val from targetSum. At leaf, check if remainder == 0.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DFS with remaining target:
  If null → False
  If leaf and remaining == 0 → True (found it!)
  Recurse with target-node.val on left and right

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def hasPathSum(root, targetSum):
    if not root: return False
    if not root.left and not root.right:
        return targetSum == root.val
    return (hasPathSum(root.left, targetSum-root.val) or
            hasPathSum(root.right, targetSum-root.val))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Tree: [5,4,8,11,null,13,4,7,2], targetSum=22

5 (remain=22): go left
  4 (remain=17): go left
    11 (remain=13): go left
      7 (remain=6): leaf, 6≠0 → False
    11 → go right
      2 (remain=4): leaf, 4≠0 → False
  4 → go right... [8,13,4] side
  
Actually: 5→4→11→2: 5+4+11+2=22 ✓
remain at 2-leaf = 22-5-4-11-2=0 → True ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Path sum: pass remaining target down the recursion. At leaf, check if we've reached zero.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def hasPathSum(root, targetSum):
    if not root: return False
    if not root.left and not root.right:
        return targetSum == root.val
    return (hasPathSum(root.left, targetSum-root.val) or
            hasPathSum(root.right, targetSum-root.val))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(h)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — identify it before coding
2. Confusing DFS vs BFS — DFS uses stack/recursion, BFS uses queue
3. Forgetting to mark visited nodes in graph problems
4. Off-by-one in tree traversals
5. Wrong base cases in DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: identify another problem using the same Tree DFS — Target Tracking pattern.
Can you solve it without hints?
```

---

## Problem 167/250 — LeetCode #144: Binary Tree Preorder Traversal

**Pattern:** `Tree Traversal` | **Position in Roadmap:** #167

```
LeetCode 144 — Binary Tree Preorder Traversal
Problem #167 of 250 | Difficulty: Easy | Pattern: Tree — Preorder DFS

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 144: Binary Tree Preorder Traversal | Easy
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Tree — Preorder DFS

Core Insight:
Preorder: ROOT → LEFT → RIGHT. Visit root first, then left subtree, then right subtree.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Recursive: visit root, recurse left, recurse right.
Iterative: use a stack. Push root. Pop, record, push right then left (so left is processed first).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Recursive:
def preorder(root, result=[]):
    if root:
        result.append(root.val)
        preorder(root.left, result)
        preorder(root.right, result)

# Iterative:
stack, result = [root], []
while stack:
    node = stack.pop()
    if node:
        result.append(node.val)
        stack.append(node.right)  # right first (processed last)
        stack.append(node.left)   # left second (processed first)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Tree: [1,null,2,3]
     1
      \
       2
      /
     3

Preorder: [1, 2, 3]

Iterative:
stack=[1]
pop 1 → result=[1], push right=2 → stack=[2]
pop 2 → result=[1,2], push left=3 → stack=[3]
pop 3 → result=[1,2,3] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Three traversal orders: Preorder(NLR), Inorder(LNR), Postorder(LRN). Iterative uses a stack. Inorder is special for BST.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Recursive:
def preorder(root, result=[]):
    if root:
        result.append(root.val)
        preorder(root.left, result)
        preorder(root.right, result)

# Iterative:
stack, result = [root], []
while stack:
    node = stack.pop()
    if node:
        result.append(node.val)
        stack.append(node.right)  # right first (processed last)
        stack.append(node.left)   # left second (processed first)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(h)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — identify it before coding
2. Confusing DFS vs BFS — DFS uses stack/recursion, BFS uses queue
3. Forgetting to mark visited nodes in graph problems
4. Off-by-one in tree traversals
5. Wrong base cases in DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: identify another problem using the same Tree — Preorder DFS pattern.
Can you solve it without hints?
```

---

## Problem 168/250 — LeetCode #94: Binary Tree Inorder Traversal

**Pattern:** `Tree Traversal` | **Position in Roadmap:** #168

```
LeetCode 94 — Binary Tree Inorder Traversal
Problem #168 of 250 | Difficulty: Easy | Pattern: Tree — Inorder DFS (L-N-R)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 94: Binary Tree Inorder Traversal | Easy
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Tree — Inorder DFS (L-N-R)

Core Insight:
Inorder: LEFT → ROOT → RIGHT. For BST, gives sorted output!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Recursive: recurse left, visit root, recurse right.
Iterative: push all left nodes first. Pop, record, then go right.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def inorder(root):
    result, stack, curr = [], [], root
    while curr or stack:
        while curr: stack.append(curr); curr = curr.left
        curr = stack.pop()
        result.append(curr.val)
        curr = curr.right
    return result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BST: [1,null,2,3]
Inorder: [1,3,2] (L-N-R)

Actually BST [4,2,6,1,3,5,7]:
Inorder: 1,2,3,4,5,6,7 (sorted!) ✓

Iterative trace for [1,null,2,3]:
stack=[], curr=1
Push 1 (no left), pop 1, result=[1], go right=2
Push 2, go left=3: push 3, no left, pop 3, result=[1,3], go right=null
pop 2, result=[1,3,2]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Inorder traversal of BST gives sorted array. Critical for BST problems!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def inorder(root):
    result, stack, curr = [], [], root
    while curr or stack:
        while curr: stack.append(curr); curr = curr.left
        curr = stack.pop()
        result.append(curr.val)
        curr = curr.right
    return result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(h)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — identify it before coding
2. Confusing DFS vs BFS — DFS uses stack/recursion, BFS uses queue
3. Forgetting to mark visited nodes in graph problems
4. Off-by-one in tree traversals
5. Wrong base cases in DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: identify another problem using the same Tree — Inorder DFS (L-N-R) pattern.
Can you solve it without hints?
```

---

## Problem 169/250 — LeetCode #145: Binary Tree Postorder Traversal

**Pattern:** `Tree Traversal` | **Position in Roadmap:** #169

```
LeetCode 145 — Binary Tree Postorder Traversal
Problem #169 of 250 | Difficulty: Easy | Pattern: Tree — Postorder DFS (L-R-N)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 145: Binary Tree Postorder Traversal | Easy
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Tree — Postorder DFS (L-R-N)

Core Insight:
Postorder: LEFT → RIGHT → ROOT. Process children BEFORE the parent. Used for deletion, expression evaluation.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Recursive: recurse left, recurse right, then visit root.
Iterative trick: reverse of modified preorder (Root-Right-Left reversed = Left-Right-Root).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Iterative (reverse trick):
stack, result = [root], []
while stack:
    node = stack.pop()
    if node:
        result.append(node.val)
        stack.append(node.left)   # push left (processed last in "reversed preorder")
        stack.append(node.right)
return result[::-1]  # reverse!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Tree: [1,null,2,3] (1 has right child 2, 2 has left child 3)

Postorder: 3,2,1

Iterative: modified preorder gives [1,2,3], reverse → [3,2,1] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Postorder processes leaves before parents. Used for evaluating expression trees, deleting trees.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Iterative (reverse trick):
stack, result = [root], []
while stack:
    node = stack.pop()
    if node:
        result.append(node.val)
        stack.append(node.left)   # push left (processed last in "reversed preorder")
        stack.append(node.right)
return result[::-1]  # reverse!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(h)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — identify it before coding
2. Confusing DFS vs BFS — DFS uses stack/recursion, BFS uses queue
3. Forgetting to mark visited nodes in graph problems
4. Off-by-one in tree traversals
5. Wrong base cases in DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: identify another problem using the same Tree — Postorder DFS (L-R-N) pattern.
Can you solve it without hints?
```

---

## Problem 170/250 — LeetCode #102: Binary Tree Level Order Traversal

**Pattern:** `BFS` | **Position in Roadmap:** #170

```
LeetCode 102 — Binary Tree Level Order Traversal
Problem #170 of 250 | Difficulty: Medium | Pattern: BFS — Queue Level by Level

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 102: Binary Tree Level Order Traversal | Medium
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: BFS — Queue Level by Level

Core Insight:
Process nodes level by level using a queue. At each level, process all current nodes and enqueue their children.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Use a queue. 
At each step:
  Record the size of the queue (this is the current level's size).
  Process exactly that many nodes.
  For each, add to current level result, enqueue children.
  Add level to result.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

from collections import deque
def levelOrder(root):
    if not root: return []
    queue = deque([root])
    result = []
    while queue:
        level = []
        for _ in range(len(queue)):  # process this level
            node = queue.popleft()
            level.append(node.val)
            if node.left: queue.append(node.left)
            if node.right: queue.append(node.right)
        result.append(level)
    return result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Tree: [3,9,20,null,null,15,7]
queue=[3]

Level 0: size=1, process 3 → result=[[3]], enqueue 9,20 → queue=[9,20]
Level 1: size=2, process 9 (no children), 20 → result=[[3],[9,20]], enqueue 15,7
Level 2: size=2, process 15,7 → result=[[3],[9,20],[15,7]]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BFS uses a queue. Level-by-level: snapshot queue size at start of each level. That's how many nodes belong to this level.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

from collections import deque
def levelOrder(root):
    if not root: return []
    queue = deque([root])
    result = []
    while queue:
        level = []
        for _ in range(len(queue)):  # process this level
            node = queue.popleft()
            level.append(node.val)
            if node.left: queue.append(node.left)
            if node.right: queue.append(node.right)
        result.append(level)
    return result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(w) where w=max width

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — identify it before coding
2. Confusing DFS vs BFS — DFS uses stack/recursion, BFS uses queue
3. Forgetting to mark visited nodes in graph problems
4. Off-by-one in tree traversals
5. Wrong base cases in DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: identify another problem using the same BFS — Queue Level by Level pattern.
Can you solve it without hints?
```

---

## Problem 171/250 — LeetCode #107: Binary Tree Level Order Traversal II

**Pattern:** `BFS` | **Position in Roadmap:** #171

```
LeetCode 107 — Binary Tree Level Order Traversal II
Problem #171 of 250 | Difficulty: Medium | Pattern: BFS — Reversed Level Order

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 107: Binary Tree Level Order Traversal II | Medium
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: BFS — Reversed Level Order

Core Insight:
Same as level order, but return levels in BOTTOM-UP order. Simplest: do normal BFS, reverse the result.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BFS as normal, collect levels. At end: result.reverse(). Or: use deque.appendleft() for each level.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def levelOrderBottom(root):
    result = []  # same BFS as LC102
    # ... normal BFS ...
    return result[::-1]  # just reverse!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Same as LC102 but result reversed: [[15,7],[9,20],[3]] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

If output is reverse of natural order, collect normally then reverse. Don't overcomplicate.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def levelOrderBottom(root):
    result = []  # same BFS as LC102
    # ... normal BFS ...
    return result[::-1]  # just reverse!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(w)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — identify it before coding
2. Confusing DFS vs BFS — DFS uses stack/recursion, BFS uses queue
3. Forgetting to mark visited nodes in graph problems
4. Off-by-one in tree traversals
5. Wrong base cases in DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: identify another problem using the same BFS — Reversed Level Order pattern.
Can you solve it without hints?
```

---

## Problem 172/250 — LeetCode #103: Binary Tree Zigzag Level Order Traversal

**Pattern:** `BFS` | **Position in Roadmap:** #172

```
LeetCode 103 — Binary Tree Zigzag Level Order Traversal
Problem #172 of 250 | Difficulty: Medium | Pattern: BFS — Alternating Direction

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 103: Binary Tree Zigzag Level Order Traversal | Medium
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: BFS — Alternating Direction

Core Insight:
Even levels: left to right. Odd levels: right to left. Use a flag to alternate direction.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BFS level by level. For each level:
  If left_to_right: append normally
  If right_to_left: use deque and appendleft, or reverse the level
  Toggle flag after each level.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

queue = deque([root]); result = []; left_to_right = True
while queue:
    level = []
    for _ in range(len(queue)):
        node = queue.popleft()
        level.append(node.val)
        if node.left: queue.append(node.left)
        if node.right: queue.append(node.right)
    result.append(level if left_to_right else level[::-1])
    left_to_right = not left_to_right
return result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Tree: [3,9,20,null,null,15,7]
Level 0: [3] (L→R)
Level 1: [20,9] (R→L, reversed from BFS order [9,20])
Level 2: [15,7] (L→R)
Result: [[3],[20,9],[15,7]]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Zigzag = BFS with direction toggle. Simplest: collect level normally, reverse for odd levels.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

queue = deque([root]); result = []; left_to_right = True
while queue:
    level = []
    for _ in range(len(queue)):
        node = queue.popleft()
        level.append(node.val)
        if node.left: queue.append(node.left)
        if node.right: queue.append(node.right)
    result.append(level if left_to_right else level[::-1])
    left_to_right = not left_to_right
return result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(w)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — identify it before coding
2. Confusing DFS vs BFS — DFS uses stack/recursion, BFS uses queue
3. Forgetting to mark visited nodes in graph problems
4. Off-by-one in tree traversals
5. Wrong base cases in DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: identify another problem using the same BFS — Alternating Direction pattern.
Can you solve it without hints?
```

---

## Problem 173/250 — LeetCode #199: Binary Tree Right Side View

**Pattern:** `BFS / DFS` | **Position in Roadmap:** #173

```
LeetCode 199 — Binary Tree Right Side View
Problem #173 of 250 | Difficulty: Medium | Pattern: BFS — Last Node Each Level

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 199: Binary Tree Right Side View | Medium
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: BFS — Last Node Each Level

Core Insight:
The right side view is the LAST node at each level in BFS. Simply collect last element of each level.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BFS level by level. At each level, record only the LAST node (rightmost).
Or DFS: traverse right-first, record first node at each depth.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

queue = deque([root]); result = []
while queue:
    for i in range(len(queue)):
        node = queue.popleft()
        if i == len_level - 1:  # last node
            result.append(node.val)
        # enqueue children
# Or simpler: result.append(level[-1]) after collecting level

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Tree: [1,2,3,null,5,null,4]
     1
    / \
   2   3
    \    \
     5    4
Level 0: [1] → rightmost: 1
Level 1: [2,3] → rightmost: 3
Level 2: [5,4] → rightmost: 4
Result: [1,3,4] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Right side view = last element of each BFS level. Simple once you know BFS level-order.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

queue = deque([root]); result = []
while queue:
    for i in range(len(queue)):
        node = queue.popleft()
        if i == len_level - 1:  # last node
            result.append(node.val)
        # enqueue children
# Or simpler: result.append(level[-1]) after collecting level

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(w)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — identify it before coding
2. Confusing DFS vs BFS — DFS uses stack/recursion, BFS uses queue
3. Forgetting to mark visited nodes in graph problems
4. Off-by-one in tree traversals
5. Wrong base cases in DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: identify another problem using the same BFS — Last Node Each Level pattern.
Can you solve it without hints?
```

---

## Problem 174/250 — LeetCode #543: Diameter of Binary Tree

**Pattern:** `Tree DFS` | **Position in Roadmap:** #174

```
LeetCode 543 — Diameter of Binary Tree
Problem #174 of 250 | Difficulty: Easy | Pattern: Tree DFS — Post-order Max Depth

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 543: Diameter of Binary Tree | Easy
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Tree DFS — Post-order Max Depth

Core Insight:
Diameter through a node = left_height + right_height. Max diameter is the max over all nodes.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Post-order DFS. At each node:
  Compute height of left and right subtrees.
  Diameter through this node = left_height + right_height.
  Update global max.
  Return max(left,right) + 1 as height.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def diameterOfBinaryTree(root):
    self.max_d = 0
    def height(node):
        if not node: return 0
        l, r = height(node.left), height(node.right)
        self.max_d = max(self.max_d, l + r)
        return max(l, r) + 1
    height(root)
    return self.max_d

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    1
   / \
  2   3
 / \
4   5

At node 4: left=0, right=0, diam=0, height=1
At node 5: diam=0, height=1
At node 2: left=1(from 4), right=1(from 5), diam=2, height=2
At node 3: diam=0, height=1
At node 1: left=2, right=1, diam=3!, height=3

Answer: 3 ✓ (path: 4→2→5 or 4→2→1→3 = length 3)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Diameter at each node = left_depth + right_depth. Use a global variable updated during recursion.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def diameterOfBinaryTree(root):
    self.max_d = 0
    def height(node):
        if not node: return 0
        l, r = height(node.left), height(node.right)
        self.max_d = max(self.max_d, l + r)
        return max(l, r) + 1
    height(root)
    return self.max_d

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(h)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — identify it before coding
2. Confusing DFS vs BFS — DFS uses stack/recursion, BFS uses queue
3. Forgetting to mark visited nodes in graph problems
4. Off-by-one in tree traversals
5. Wrong base cases in DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: identify another problem using the same Tree DFS — Post-order Max Depth pattern.
Can you solve it without hints?
```

---

## Problem 175/250 — LeetCode #110: Balanced Binary Tree

**Pattern:** `Tree DFS` | **Position in Roadmap:** #175

```
LeetCode 110 — Balanced Binary Tree
Problem #175 of 250 | Difficulty: Easy | Pattern: Tree DFS — Height with Early Exit

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 110: Balanced Binary Tree | Easy
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Tree DFS — Height with Early Exit

Core Insight:
A tree is balanced if: height difference between left and right subtrees ≤ 1, AND both subtrees are balanced.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DFS returns height, or -1 if subtree is UNBALANCED.
  If left returns -1 → propagate -1 up (already unbalanced)
  If right returns -1 → propagate -1
  If |left_height - right_height| > 1 → return -1
  Otherwise return max(l,r)+1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def isBalanced(root):
    def height(node):
        if not node: return 0
        l = height(node.left)
        if l == -1: return -1
        r = height(node.right)
        if r == -1: return -1
        if abs(l-r) > 1: return -1
        return max(l,r) + 1
    return height(root) != -1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

[3,9,20,null,null,15,7] - balanced
  height(9)=1, height(20)=2
  At 3: |1-2|=1 ≤ 1 → balanced, height=3

[1,2,2,3,3,null,null,4,4] - unbalanced
  Deep left subtree returns large height
  |left-right| > 1 somewhere → return -1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Return -1 to signal imbalance propagates up. Avoids recomputing heights separately.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def isBalanced(root):
    def height(node):
        if not node: return 0
        l = height(node.left)
        if l == -1: return -1
        r = height(node.right)
        if r == -1: return -1
        if abs(l-r) > 1: return -1
        return max(l,r) + 1
    return height(root) != -1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(h)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — identify it before coding
2. Confusing DFS vs BFS — DFS uses stack/recursion, BFS uses queue
3. Forgetting to mark visited nodes in graph problems
4. Off-by-one in tree traversals
5. Wrong base cases in DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: identify another problem using the same Tree DFS — Height with Early Exit pattern.
Can you solve it without hints?
```

---

## Problem 176/250 — LeetCode #124: Binary Tree Maximum Path Sum

**Pattern:** `Tree DFS` | **Position in Roadmap:** #176

```
LeetCode 124 — Binary Tree Maximum Path Sum
Problem #176 of 250 | Difficulty: Hard | Pattern: Tree DFS — Max Gain Post-order

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 124: Binary Tree Maximum Path Sum | Hard
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Tree DFS — Max Gain Post-order

Core Insight:
At each node, compute: max path sum PASSING THROUGH this node = node.val + max(0, left_gain) + max(0, right_gain). Update global max. Return node.val + max(0, max(left,right)) as gain to parent (can only extend one side!).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Post-order: compute gains from both children.
  gain(node) = node.val + max(0, gain(left)) + max(0, gain(right)) is the PATH through node
  But we can RETURN only one side to the parent.
  Return: node.val + max(0, max(left_gain, right_gain))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

self.max_sum = float('-inf')
def gain(node):
    if not node: return 0
    l = max(0, gain(node.left))
    r = max(0, gain(node.right))
    self.max_sum = max(self.max_sum, node.val + l + r)
    return node.val + max(l, r)
gain(root)
return self.max_sum

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

[-10, 9, 20, null, null, 15, 7]

gain(9)=9
gain(15)=15, gain(7)=7
At 20: path = 20+15+7=42, return max = 20+15=35... wait
gain(20) as return = 20 + max(15,7) = 35
path through 20 = 20+15+7=42

At -10: path = -10 + max(9,0) + max(35,0) = -10+9+35=34 < 42

Max = 42 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

The key distinction: the path through a node uses BOTH children. But you can only RETURN one side to the parent.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

self.max_sum = float('-inf')
def gain(node):
    if not node: return 0
    l = max(0, gain(node.left))
    r = max(0, gain(node.right))
    self.max_sum = max(self.max_sum, node.val + l + r)
    return node.val + max(l, r)
gain(root)
return self.max_sum

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(h)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — identify it before coding
2. Confusing DFS vs BFS — DFS uses stack/recursion, BFS uses queue
3. Forgetting to mark visited nodes in graph problems
4. Off-by-one in tree traversals
5. Wrong base cases in DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: identify another problem using the same Tree DFS — Max Gain Post-order pattern.
Can you solve it without hints?
```

---

## Problem 177/250 — LeetCode #700: Search in a Binary Search Tree

**Pattern:** `BST` | **Position in Roadmap:** #177

```
LeetCode 700 — Search in a Binary Search Tree
Problem #177 of 250 | Difficulty: Easy | Pattern: BST — Binary Search on Tree

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 700: Search in a Binary Search Tree | Easy
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: BST — Binary Search on Tree

Core Insight:
BST property: left < node < right. Search like binary search: go left if target < node, right if target > node.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Recursive or iterative. At each node: if val==target, return node. If target<node.val, go left. Else go right.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def searchBST(root, val):
    if not root or root.val == val: return root
    if val < root.val: return searchBST(root.left, val)
    return searchBST(root.right, val)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BST: [4,2,7,1,3], val=2
  root=4: 2<4 → go left
  node=2: 2==2 → return node ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BST search is O(log n) average because you eliminate half the tree at each step.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def searchBST(root, val):
    if not root or root.val == val: return root
    if val < root.val: return searchBST(root.left, val)
    return searchBST(root.right, val)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(h), Space: O(h)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — identify it before coding
2. Confusing DFS vs BFS — DFS uses stack/recursion, BFS uses queue
3. Forgetting to mark visited nodes in graph problems
4. Off-by-one in tree traversals
5. Wrong base cases in DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: identify another problem using the same BST — Binary Search on Tree pattern.
Can you solve it without hints?
```

---

## Problem 178/250 — LeetCode #701: Insert into a Binary Search Tree

**Pattern:** `BST` | **Position in Roadmap:** #178

```
LeetCode 701 — Insert into a Binary Search Tree
Problem #178 of 250 | Difficulty: Medium | Pattern: BST — Find Correct Position

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 701: Insert into a Binary Search Tree | Medium
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: BST — Find Correct Position

Core Insight:
Insert at the correct leaf position maintaining BST property. Navigate like search, insert when you hit null.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Recurse like search. When you reach null, that's where the new node goes. The recursion naturally places it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def insertIntoBST(root, val):
    if not root: return TreeNode(val)
    if val < root.val: root.left = insertIntoBST(root.left, val)
    else: root.right = insertIntoBST(root.right, val)
    return root

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BST: [4,2,7,1,3], insert 5
  4: 5>4 → go right
  7: 5<7 → go left
  null: insert 5 here ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BST insert: navigate to the correct null position. No rebalancing needed (unbalanced BST is still valid).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def insertIntoBST(root, val):
    if not root: return TreeNode(val)
    if val < root.val: root.left = insertIntoBST(root.left, val)
    else: root.right = insertIntoBST(root.right, val)
    return root

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(h), Space: O(h)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — identify it before coding
2. Confusing DFS vs BFS — DFS uses stack/recursion, BFS uses queue
3. Forgetting to mark visited nodes in graph problems
4. Off-by-one in tree traversals
5. Wrong base cases in DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: identify another problem using the same BST — Find Correct Position pattern.
Can you solve it without hints?
```

---

## Problem 179/250 — LeetCode #450: Delete Node in a BST

**Pattern:** `BST` | **Position in Roadmap:** #179

```
LeetCode 450 — Delete Node in a BST
Problem #179 of 250 | Difficulty: Medium | Pattern: BST — Three Case Deletion

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 450: Delete Node in a BST | Medium
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: BST — Three Case Deletion

Core Insight:
Three cases when deleting a node:
  1. Node has no children → just remove it
  2. Node has one child → replace with that child
  3. Node has two children → replace with inorder successor (smallest in right subtree)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Find the node (BST search).
  Case 1 (leaf): return None
  Case 2 (one child): return the existing child
  Case 3 (two children): 
    Find smallest in right subtree (go right then all the way left)
    Replace current value with that smallest
    Delete that smallest from right subtree

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def deleteNode(root, key):
    if not root: return None
    if key < root.val: root.left = deleteNode(root.left, key)
    elif key > root.val: root.right = deleteNode(root.right, key)
    else:
        if not root.left: return root.right
        if not root.right: return root.left
        # Find inorder successor
        succ = root.right
        while succ.left: succ = succ.left
        root.val = succ.val
        root.right = deleteNode(root.right, succ.val)
    return root

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Delete 5 from [5,3,6,2,4,null,7]

Node 5 has two children (3 and 6).
Successor: smallest in right subtree = 6 (no left child of 6)
Replace 5's value with 6.
Delete 6 from right subtree (case 1: leaf... wait 6 has right child 7)
Actually successor is 6, replace root.val=6, delete 6 from right: 6 has right child 7, so replace 6 with 7.

Result: [6,3,7,2,4]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BST deletion case 3: use inorder successor. Go right once, then all the way left to find the smallest.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def deleteNode(root, key):
    if not root: return None
    if key < root.val: root.left = deleteNode(root.left, key)
    elif key > root.val: root.right = deleteNode(root.right, key)
    else:
        if not root.left: return root.right
        if not root.right: return root.left
        # Find inorder successor
        succ = root.right
        while succ.left: succ = succ.left
        root.val = succ.val
        root.right = deleteNode(root.right, succ.val)
    return root

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(h), Space: O(h)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — identify it before coding
2. Confusing DFS vs BFS — DFS uses stack/recursion, BFS uses queue
3. Forgetting to mark visited nodes in graph problems
4. Off-by-one in tree traversals
5. Wrong base cases in DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: identify another problem using the same BST — Three Case Deletion pattern.
Can you solve it without hints?
```

---

## Problem 180/250 — LeetCode #98: Validate Binary Search Tree

**Pattern:** `BST` | **Position in Roadmap:** #180

```
LeetCode 98 — Validate Binary Search Tree
Problem #180 of 250 | Difficulty: Medium | Pattern: BST — Inorder or Range Validation

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 98: Validate Binary Search Tree | Medium
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: BST — Inorder or Range Validation

Core Insight:
Valid BST: every node satisfies min < node.val < max where min and max are inherited from ancestors.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Method 1: Pass valid range [min, max] down recursion.
  Method 2: Inorder traversal must produce strictly increasing sequence.

Range method:
  root: range (-inf, +inf)
  Left child: range (-inf, parent.val)
  Right child: range (parent.val, +inf)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def isValidBST(root):
    def valid(node, lo, hi):
        if not node: return True
        if not (lo < node.val < hi): return False
        return valid(node.left, lo, node.val) and valid(node.right, node.val, hi)
    return valid(root, float('-inf'), float('inf'))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

[5,1,4,null,null,3,6] - INVALID
  5 range (-inf,inf) ✓
  1 range (-inf,5) ✓
  4 range (5,inf) → 4 < 5! INVALID ✓

[2,1,3] - VALID
  2 range (-inf,inf) ✓
  1 range (-inf,2): 1<2 ✓
  3 range (2,inf): 3>2 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Range validation: not just parent comparison! A node must satisfy ALL ancestor constraints. Pass the range down.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def isValidBST(root):
    def valid(node, lo, hi):
        if not node: return True
        if not (lo < node.val < hi): return False
        return valid(node.left, lo, node.val) and valid(node.right, node.val, hi)
    return valid(root, float('-inf'), float('inf'))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(h)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — identify it before coding
2. Confusing DFS vs BFS — DFS uses stack/recursion, BFS uses queue
3. Forgetting to mark visited nodes in graph problems
4. Off-by-one in tree traversals
5. Wrong base cases in DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: identify another problem using the same BST — Inorder or Range Validation pattern.
Can you solve it without hints?
```

---

## Problem 181/250 — LeetCode #235: Lowest Common Ancestor of a Binary Search Tree

**Pattern:** `BST` | **Position in Roadmap:** #181

```
LeetCode 235 — Lowest Common Ancestor of a Binary Search Tree
Problem #181 of 250 | Difficulty: Easy | Pattern: BST — Direction-based Search

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 235: Lowest Common Ancestor of a Binary Search Tree | Easy
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: BST — Direction-based Search

Core Insight:
In a BST: if both p and q are in left subtree, LCA is there. If both in right, LCA is there. Otherwise, current node IS the LCA.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

At each node:
  If both p.val and q.val < node.val → LCA in left subtree
  If both p.val and q.val > node.val → LCA in right subtree
  Otherwise → current node is LCA (they split here!)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def lowestCommonAncestor(root, p, q):
    if p.val < root.val and q.val < root.val:
        return lowestCommonAncestor(root.left, p, q)
    if p.val > root.val and q.val > root.val:
        return lowestCommonAncestor(root.right, p, q)
    return root  # split point or one is the ancestor

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BST [6,2,8,0,4,7,9], p=2, q=8
  node=6: p=2<6 and q=8>6 → they split here! LCA=6 ✓

BST [6,2,8,0,4,7,9], p=2, q=4
  node=6: both < 6 → go left
  node=2: 2=p, 4>2 → they split here! LCA=2 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BST property lets you determine LCA direction without examining full tree. No need for general tree LCA algorithm.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def lowestCommonAncestor(root, p, q):
    if p.val < root.val and q.val < root.val:
        return lowestCommonAncestor(root.left, p, q)
    if p.val > root.val and q.val > root.val:
        return lowestCommonAncestor(root.right, p, q)
    return root  # split point or one is the ancestor

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(h), Space: O(h)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — identify it before coding
2. Confusing DFS vs BFS — DFS uses stack/recursion, BFS uses queue
3. Forgetting to mark visited nodes in graph problems
4. Off-by-one in tree traversals
5. Wrong base cases in DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: identify another problem using the same BST — Direction-based Search pattern.
Can you solve it without hints?
```

---

## Problem 182/250 — LeetCode #108: Convert Sorted Array to Binary Search Tree

**Pattern:** `BST` | **Position in Roadmap:** #182

```
LeetCode 108 — Convert Sorted Array to Binary Search Tree
Problem #182 of 250 | Difficulty: Easy | Pattern: BST — Mid as Root

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 108: Convert Sorted Array to Binary Search Tree | Easy
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: BST — Mid as Root

Core Insight:
Take the MIDDLE element as root. Recursively build left BST from left half, right BST from right half. This ensures balance.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary search style: mid = (lo+hi)//2. Root = arr[mid]. Left subtree from arr[lo..mid-1]. Right from arr[mid+1..hi].

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def sortedArrayToBST(nums):
    def build(lo, hi):
        if lo > hi: return None
        mid = (lo + hi) // 2
        node = TreeNode(nums[mid])
        node.left = build(lo, mid-1)
        node.right = build(mid+1, hi)
        return node
    return build(0, len(nums)-1)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums = [-10,-3,0,5,9]
mid=2, root=0
Left half: [-10,-3] → mid=0, root=-10, right=-3
Right half: [5,9] → mid=0, root=5, right=9

BST: [0,-10,5,null,-3,null,9] ✓ (balanced)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Sorted array → balanced BST: always pick the middle as root. This is divide and conquer applied to BST construction.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def sortedArrayToBST(nums):
    def build(lo, hi):
        if lo > hi: return None
        mid = (lo + hi) // 2
        node = TreeNode(nums[mid])
        node.left = build(lo, mid-1)
        node.right = build(mid+1, hi)
        return node
    return build(0, len(nums)-1)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(log n)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — identify it before coding
2. Confusing DFS vs BFS — DFS uses stack/recursion, BFS uses queue
3. Forgetting to mark visited nodes in graph problems
4. Off-by-one in tree traversals
5. Wrong base cases in DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: identify another problem using the same BST — Mid as Root pattern.
Can you solve it without hints?
```

---

## Problem 183/250 — LeetCode #230: Kth Smallest Element in a BST

**Pattern:** `BST` | **Position in Roadmap:** #183

```
LeetCode 230 — Kth Smallest Element in a BST
Problem #183 of 250 | Difficulty: Medium | Pattern: BST — Inorder = Sorted

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 230: Kth Smallest Element in a BST | Medium
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: BST — Inorder = Sorted

Core Insight:
Inorder traversal of BST gives sorted sequence. The kth element in inorder = kth smallest.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Inorder traversal (left-root-right). Count nodes as visited.
When count reaches k, that's our answer.
Stop early once found!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def kthSmallest(root, k):
    stack, curr = [], root
    while curr or stack:
        while curr: stack.append(curr); curr = curr.left
        curr = stack.pop()
        k -= 1
        if k == 0: return curr.val
        curr = curr.right

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BST: [3,1,4,null,2], k=1
Inorder: 1,2,3,4
k=1 → answer = 1 ✓

Iterative with early stop:
stack=[], curr=3
push 3, go left to 1, push 1, go left to null
pop 1 → count=1 == k=1 → return 1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BST + kth smallest = inorder traversal. This is why inorder traversal is so important for BSTs!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def kthSmallest(root, k):
    stack, curr = [], root
    while curr or stack:
        while curr: stack.append(curr); curr = curr.left
        curr = stack.pop()
        k -= 1
        if k == 0: return curr.val
        curr = curr.right

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(H+k), Space: O(H)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — identify it before coding
2. Confusing DFS vs BFS — DFS uses stack/recursion, BFS uses queue
3. Forgetting to mark visited nodes in graph problems
4. Off-by-one in tree traversals
5. Wrong base cases in DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: identify another problem using the same BST — Inorder = Sorted pattern.
Can you solve it without hints?
```

---

## Problem 184/250 — LeetCode #173: Binary Search Tree Iterator

**Pattern:** `BST / Stack` | **Position in Roadmap:** #184

```
LeetCode 173 — Binary Search Tree Iterator
Problem #184 of 250 | Difficulty: Medium | Pattern: BST — Lazy Inorder with Stack

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 173: Binary Search Tree Iterator | Medium
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: BST — Lazy Inorder with Stack

Core Insight:
Simulate inorder traversal one step at a time. Use a stack to hold the 'in progress' path.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Constructor: push all left nodes from root onto stack.
  next(): pop top, push all left nodes of right child.
  hasNext(): stack is non-empty.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def __init__(self, root):
    self.stack = []
    self._push_left(root)
def _push_left(self, node):
    while node: self.stack.append(node); node = node.left
def next(self):
    node = self.stack.pop()
    self._push_left(node.right)
    return node.val
def hasNext(self): return bool(self.stack)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BST: [7,3,15,null,null,9,20]
Init: push 7, push 3 → stack=[7,3]
next(): pop 3 → return 3. push nothing (3 has no right). stack=[7]
next(): pop 7 → return 7. push 15, push 9 → stack=[15,9]
next(): pop 9 → return 9. push nothing. stack=[15]
next(): pop 15 → return 15. push 20. stack=[20]
next(): pop 20 → return 20. stack=[]
hasNext(): False

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Iterator pattern: maintain state between calls using a stack. Push left-spine on demand.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def __init__(self, root):
    self.stack = []
    self._push_left(root)
def _push_left(self, node):
    while node: self.stack.append(node); node = node.left
def next(self):
    node = self.stack.pop()
    self._push_left(node.right)
    return node.val
def hasNext(self): return bool(self.stack)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

next(): O(1) amortized, hasNext(): O(1)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — identify it before coding
2. Confusing DFS vs BFS — DFS uses stack/recursion, BFS uses queue
3. Forgetting to mark visited nodes in graph problems
4. Off-by-one in tree traversals
5. Wrong base cases in DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: identify another problem using the same BST — Lazy Inorder with Stack pattern.
Can you solve it without hints?
```

---

## Problem 185/250 — LeetCode #669: Trim a Binary Search Tree

**Pattern:** `BST` | **Position in Roadmap:** #185

```
LeetCode 669 — Trim a Binary Search Tree
Problem #185 of 250 | Difficulty: Medium | Pattern: BST — Range-based Pruning

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 669: Trim a Binary Search Tree | Medium
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: BST — Range-based Pruning

Core Insight:
Trim BST to keep only values in [low, high]. Use BST property to know which side to trim.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

At each node:
  If val < low: left subtree is all too small. Return trimmed right subtree.
  If val > high: right subtree is all too big. Return trimmed left subtree.
  Otherwise: trim both children and keep current node.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def trimBST(root, low, high):
    if not root: return None
    if root.val < low: return trimBST(root.right, low, high)
    if root.val > high: return trimBST(root.left, low, high)
    root.left = trimBST(root.left, low, high)
    root.right = trimBST(root.right, low, high)
    return root

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BST: [3,0,4,null,2,null,null,null,1], low=1, high=3
  node=3 (in [1,3]): trim both children
  node=0 (0<1): return trim(0.right) = trim(2)
  node=2 (in [1,3]): trim both
  node=1 (in [1,3]): no children, return
  node=4 (4>3): return trim(4.left) = None

Result: [3,2,null,1]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BST trimming: out-of-range nodes guide which subtree to keep. Elegant recursive solution.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def trimBST(root, low, high):
    if not root: return None
    if root.val < low: return trimBST(root.right, low, high)
    if root.val > high: return trimBST(root.left, low, high)
    root.left = trimBST(root.left, low, high)
    root.right = trimBST(root.right, low, high)
    return root

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(h)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — identify it before coding
2. Confusing DFS vs BFS — DFS uses stack/recursion, BFS uses queue
3. Forgetting to mark visited nodes in graph problems
4. Off-by-one in tree traversals
5. Wrong base cases in DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: identify another problem using the same BST — Range-based Pruning pattern.
Can you solve it without hints?
```

---

## Problem 186/250 — LeetCode #938: Range Sum of BST

**Pattern:** `BST` | **Position in Roadmap:** #186

```
LeetCode 938 — Range Sum of BST
Problem #186 of 250 | Difficulty: Easy | Pattern: BST — Conditional DFS

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 938: Range Sum of BST | Easy
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: BST — Conditional DFS

Core Insight:
Sum all values in [low, high]. Use BST property to skip subtrees outside range.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DFS. At each node:
  If val in [low,high]: add to sum
  If val > low: search left (might find values ≥ low)
  If val < high: search right (might find values ≤ high)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def rangeSumBST(root, low, high):
    if not root: return 0
    total = root.val if low <= root.val <= high else 0
    if root.val > low: total += rangeSumBST(root.left, low, high)
    if root.val < high: total += rangeSumBST(root.right, low, high)
    return total

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BST: [10,5,15,3,7,null,18], low=7, high=15
  10 in [7,15]: sum+=10. 10>7→go left, 10<15→go right
  5 not in [7,15]: 5>7? No, don't go left. 5<15→go right
  7 in [7,15]: sum+=7. 7>7? No left. 7<15→go right: no child
  15 in [7,15]: sum+=15. 15>7→go left: no child. 15<15? No, skip right.
  Sum = 10+7+15 = 32 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BST pruning: if node.val >= low, search left. If node.val <= high, search right. Efficient range query!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def rangeSumBST(root, low, high):
    if not root: return 0
    total = root.val if low <= root.val <= high else 0
    if root.val > low: total += rangeSumBST(root.left, low, high)
    if root.val < high: total += rangeSumBST(root.right, low, high)
    return total

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n) worst, O(log n + k) average

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — identify it before coding
2. Confusing DFS vs BFS — DFS uses stack/recursion, BFS uses queue
3. Forgetting to mark visited nodes in graph problems
4. Off-by-one in tree traversals
5. Wrong base cases in DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: identify another problem using the same BST — Conditional DFS pattern.
Can you solve it without hints?
```

---

## Problem 187/250 — LeetCode #703: Kth Largest Element in a Stream

**Pattern:** `Heap` | **Position in Roadmap:** #187

```
LeetCode 703 — Kth Largest Element in a Stream
Problem #187 of 250 | Difficulty: Easy | Pattern: Min Heap of size k

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 703: Kth Largest Element in a Stream | Easy
Read carefully. Identify: what K-th element or K-element set are you finding?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Min Heap of size k

HEAP INSIGHT:
A MIN-HEAP of size k maintains the k LARGEST elements seen so far.
  - If you want k largest: use min-heap (top = smallest of the k largest)
  - If you want k smallest: use max-heap (top = largest of the k smallest)

For "kth largest": maintain min-heap of size k.
  The minimum of the k largest = kth largest!

In Python, heapq is a MIN-heap by default.
To simulate MAX-heap: negate values (push -x, peek -heap[0]).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Kth Largest Element in a Stream":
  What are you trying to RANK? Values? Frequencies? Distances?
  Do you want the TOP K or BOTTOM K?
  What property determines the ordering?

Heap gives you O(log k) insertion and O(1) top access.
This beats sorting (O(n log n)) when k << n.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

import heapq

# For k largest elements (min-heap of size k):
heap = []
for num in nums:
    heapq.heappush(heap, num)
    if len(heap) > k:
        heapq.heappop(heap)  # remove smallest
# heap now contains k largest, heap[0] = kth largest

# For k smallest elements (max-heap of size k):
heap = []
for num in nums:
    heapq.heappush(heap, -num)  # negate for max-heap
    if len(heap) > k:
        heapq.heappop(heap)  # remove largest (most negative = least actual)
# -heap[0] = kth smallest

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Apply your algorithm to the first example on LeetCode.
Track the heap contents at each insertion step.
Verify heap[0] gives the correct answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Heap is your go-to for:
  - "k-th largest/smallest"
  - "top k elements"
  - "merge k sorted lists"
  - "running median" (two heaps!)

Time: O(n log k) — much better than O(n log n) sorting when k is small!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

import heapq
heap = []
for x in data:
    heapq.heappush(heap, x)  # or -x for max-heap
    if len(heap) > k:
        heapq.heappop(heap)
return heap[0]  # or -heap[0] for max-heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n log k) for building heap of size k
Space: O(k) for the heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Using a max-heap when min-heap is needed (or vice versa)
2. Forgetting to negate for max-heap in Python
3. Not maintaining heap size at exactly k
4. Confusing heapq.heappush with heapq.heapreplace

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Can you identify whether this problem needs a min-heap or max-heap?
Practice: sort a list using only heap operations (heapsort).
```

---

## Problem 188/250 — LeetCode #1046: Last Stone Weight

**Pattern:** `Max Heap` | **Position in Roadmap:** #188

```
LeetCode 1046 — Last Stone Weight
Problem #188 of 250 | Difficulty: Easy | Pattern: Max Heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 1046: Last Stone Weight | Easy
Read carefully. Identify: what K-th element or K-element set are you finding?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Max Heap

HEAP INSIGHT:
A MIN-HEAP of size k maintains the k LARGEST elements seen so far.
  - If you want k largest: use min-heap (top = smallest of the k largest)
  - If you want k smallest: use max-heap (top = largest of the k smallest)

For "kth largest": maintain min-heap of size k.
  The minimum of the k largest = kth largest!

In Python, heapq is a MIN-heap by default.
To simulate MAX-heap: negate values (push -x, peek -heap[0]).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Last Stone Weight":
  What are you trying to RANK? Values? Frequencies? Distances?
  Do you want the TOP K or BOTTOM K?
  What property determines the ordering?

Heap gives you O(log k) insertion and O(1) top access.
This beats sorting (O(n log n)) when k << n.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

import heapq

# For k largest elements (min-heap of size k):
heap = []
for num in nums:
    heapq.heappush(heap, num)
    if len(heap) > k:
        heapq.heappop(heap)  # remove smallest
# heap now contains k largest, heap[0] = kth largest

# For k smallest elements (max-heap of size k):
heap = []
for num in nums:
    heapq.heappush(heap, -num)  # negate for max-heap
    if len(heap) > k:
        heapq.heappop(heap)  # remove largest (most negative = least actual)
# -heap[0] = kth smallest

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Apply your algorithm to the first example on LeetCode.
Track the heap contents at each insertion step.
Verify heap[0] gives the correct answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Heap is your go-to for:
  - "k-th largest/smallest"
  - "top k elements"
  - "merge k sorted lists"
  - "running median" (two heaps!)

Time: O(n log k) — much better than O(n log n) sorting when k is small!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

import heapq
heap = []
for x in data:
    heapq.heappush(heap, x)  # or -x for max-heap
    if len(heap) > k:
        heapq.heappop(heap)
return heap[0]  # or -heap[0] for max-heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n log k) for building heap of size k
Space: O(k) for the heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Using a max-heap when min-heap is needed (or vice versa)
2. Forgetting to negate for max-heap in Python
3. Not maintaining heap size at exactly k
4. Confusing heapq.heappush with heapq.heapreplace

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Can you identify whether this problem needs a min-heap or max-heap?
Practice: sort a list using only heap operations (heapsort).
```

---

## Problem 189/250 — LeetCode #215: Kth Largest Element in an Array

**Pattern:** `Heap / Quickselect` | **Position in Roadmap:** #189

```
LeetCode 215 — Kth Largest Element in an Array
Problem #189 of 250 | Difficulty: Medium | Pattern: Min Heap / Quickselect

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 215: Kth Largest Element in an Array | Medium
Read carefully. Identify: what K-th element or K-element set are you finding?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Min Heap / Quickselect

HEAP INSIGHT:
A MIN-HEAP of size k maintains the k LARGEST elements seen so far.
  - If you want k largest: use min-heap (top = smallest of the k largest)
  - If you want k smallest: use max-heap (top = largest of the k smallest)

For "kth largest": maintain min-heap of size k.
  The minimum of the k largest = kth largest!

In Python, heapq is a MIN-heap by default.
To simulate MAX-heap: negate values (push -x, peek -heap[0]).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Kth Largest Element in an Array":
  What are you trying to RANK? Values? Frequencies? Distances?
  Do you want the TOP K or BOTTOM K?
  What property determines the ordering?

Heap gives you O(log k) insertion and O(1) top access.
This beats sorting (O(n log n)) when k << n.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

import heapq

# For k largest elements (min-heap of size k):
heap = []
for num in nums:
    heapq.heappush(heap, num)
    if len(heap) > k:
        heapq.heappop(heap)  # remove smallest
# heap now contains k largest, heap[0] = kth largest

# For k smallest elements (max-heap of size k):
heap = []
for num in nums:
    heapq.heappush(heap, -num)  # negate for max-heap
    if len(heap) > k:
        heapq.heappop(heap)  # remove largest (most negative = least actual)
# -heap[0] = kth smallest

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Apply your algorithm to the first example on LeetCode.
Track the heap contents at each insertion step.
Verify heap[0] gives the correct answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Heap is your go-to for:
  - "k-th largest/smallest"
  - "top k elements"
  - "merge k sorted lists"
  - "running median" (two heaps!)

Time: O(n log k) — much better than O(n log n) sorting when k is small!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

import heapq
heap = []
for x in data:
    heapq.heappush(heap, x)  # or -x for max-heap
    if len(heap) > k:
        heapq.heappop(heap)
return heap[0]  # or -heap[0] for max-heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n log k) for building heap of size k
Space: O(k) for the heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Using a max-heap when min-heap is needed (or vice versa)
2. Forgetting to negate for max-heap in Python
3. Not maintaining heap size at exactly k
4. Confusing heapq.heappush with heapq.heapreplace

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Can you identify whether this problem needs a min-heap or max-heap?
Practice: sort a list using only heap operations (heapsort).
```

---

## Problem 190/250 — LeetCode #347: Top K Frequent Elements

**Pattern:** `Heap` | **Position in Roadmap:** #190

```
LeetCode 347 — Top K Frequent Elements
Problem #190 of 250 | Difficulty: Medium | Pattern: HashMap + Min Heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 347: Top K Frequent Elements | Medium
Read carefully. Identify: what K-th element or K-element set are you finding?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: HashMap + Min Heap

HEAP INSIGHT:
A MIN-HEAP of size k maintains the k LARGEST elements seen so far.
  - If you want k largest: use min-heap (top = smallest of the k largest)
  - If you want k smallest: use max-heap (top = largest of the k smallest)

For "kth largest": maintain min-heap of size k.
  The minimum of the k largest = kth largest!

In Python, heapq is a MIN-heap by default.
To simulate MAX-heap: negate values (push -x, peek -heap[0]).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Top K Frequent Elements":
  What are you trying to RANK? Values? Frequencies? Distances?
  Do you want the TOP K or BOTTOM K?
  What property determines the ordering?

Heap gives you O(log k) insertion and O(1) top access.
This beats sorting (O(n log n)) when k << n.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

import heapq

# For k largest elements (min-heap of size k):
heap = []
for num in nums:
    heapq.heappush(heap, num)
    if len(heap) > k:
        heapq.heappop(heap)  # remove smallest
# heap now contains k largest, heap[0] = kth largest

# For k smallest elements (max-heap of size k):
heap = []
for num in nums:
    heapq.heappush(heap, -num)  # negate for max-heap
    if len(heap) > k:
        heapq.heappop(heap)  # remove largest (most negative = least actual)
# -heap[0] = kth smallest

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Apply your algorithm to the first example on LeetCode.
Track the heap contents at each insertion step.
Verify heap[0] gives the correct answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Heap is your go-to for:
  - "k-th largest/smallest"
  - "top k elements"
  - "merge k sorted lists"
  - "running median" (two heaps!)

Time: O(n log k) — much better than O(n log n) sorting when k is small!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

import heapq
heap = []
for x in data:
    heapq.heappush(heap, x)  # or -x for max-heap
    if len(heap) > k:
        heapq.heappop(heap)
return heap[0]  # or -heap[0] for max-heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n log k) for building heap of size k
Space: O(k) for the heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Using a max-heap when min-heap is needed (or vice versa)
2. Forgetting to negate for max-heap in Python
3. Not maintaining heap size at exactly k
4. Confusing heapq.heappush with heapq.heapreplace

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Can you identify whether this problem needs a min-heap or max-heap?
Practice: sort a list using only heap operations (heapsort).
```

---

## Problem 191/250 — LeetCode #973: K Closest Points to Origin

**Pattern:** `Heap` | **Position in Roadmap:** #191

```
LeetCode 973 — K Closest Points to Origin
Problem #191 of 250 | Difficulty: Medium | Pattern: Max Heap of size k

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 973: K Closest Points to Origin | Medium
Read carefully. Identify: what K-th element or K-element set are you finding?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Max Heap of size k

HEAP INSIGHT:
A MIN-HEAP of size k maintains the k LARGEST elements seen so far.
  - If you want k largest: use min-heap (top = smallest of the k largest)
  - If you want k smallest: use max-heap (top = largest of the k smallest)

For "kth largest": maintain min-heap of size k.
  The minimum of the k largest = kth largest!

In Python, heapq is a MIN-heap by default.
To simulate MAX-heap: negate values (push -x, peek -heap[0]).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "K Closest Points to Origin":
  What are you trying to RANK? Values? Frequencies? Distances?
  Do you want the TOP K or BOTTOM K?
  What property determines the ordering?

Heap gives you O(log k) insertion and O(1) top access.
This beats sorting (O(n log n)) when k << n.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

import heapq

# For k largest elements (min-heap of size k):
heap = []
for num in nums:
    heapq.heappush(heap, num)
    if len(heap) > k:
        heapq.heappop(heap)  # remove smallest
# heap now contains k largest, heap[0] = kth largest

# For k smallest elements (max-heap of size k):
heap = []
for num in nums:
    heapq.heappush(heap, -num)  # negate for max-heap
    if len(heap) > k:
        heapq.heappop(heap)  # remove largest (most negative = least actual)
# -heap[0] = kth smallest

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Apply your algorithm to the first example on LeetCode.
Track the heap contents at each insertion step.
Verify heap[0] gives the correct answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Heap is your go-to for:
  - "k-th largest/smallest"
  - "top k elements"
  - "merge k sorted lists"
  - "running median" (two heaps!)

Time: O(n log k) — much better than O(n log n) sorting when k is small!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

import heapq
heap = []
for x in data:
    heapq.heappush(heap, x)  # or -x for max-heap
    if len(heap) > k:
        heapq.heappop(heap)
return heap[0]  # or -heap[0] for max-heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n log k) for building heap of size k
Space: O(k) for the heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Using a max-heap when min-heap is needed (or vice versa)
2. Forgetting to negate for max-heap in Python
3. Not maintaining heap size at exactly k
4. Confusing heapq.heappush with heapq.heapreplace

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Can you identify whether this problem needs a min-heap or max-heap?
Practice: sort a list using only heap operations (heapsort).
```

---

## Problem 192/250 — LeetCode #692: Top K Frequent Words

**Pattern:** `Heap` | **Position in Roadmap:** #192

```
LeetCode 692 — Top K Frequent Words
Problem #192 of 250 | Difficulty: Medium | Pattern: HashMap + Min Heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 692: Top K Frequent Words | Medium
Read carefully. Identify: what K-th element or K-element set are you finding?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: HashMap + Min Heap

HEAP INSIGHT:
A MIN-HEAP of size k maintains the k LARGEST elements seen so far.
  - If you want k largest: use min-heap (top = smallest of the k largest)
  - If you want k smallest: use max-heap (top = largest of the k smallest)

For "kth largest": maintain min-heap of size k.
  The minimum of the k largest = kth largest!

In Python, heapq is a MIN-heap by default.
To simulate MAX-heap: negate values (push -x, peek -heap[0]).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Top K Frequent Words":
  What are you trying to RANK? Values? Frequencies? Distances?
  Do you want the TOP K or BOTTOM K?
  What property determines the ordering?

Heap gives you O(log k) insertion and O(1) top access.
This beats sorting (O(n log n)) when k << n.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

import heapq

# For k largest elements (min-heap of size k):
heap = []
for num in nums:
    heapq.heappush(heap, num)
    if len(heap) > k:
        heapq.heappop(heap)  # remove smallest
# heap now contains k largest, heap[0] = kth largest

# For k smallest elements (max-heap of size k):
heap = []
for num in nums:
    heapq.heappush(heap, -num)  # negate for max-heap
    if len(heap) > k:
        heapq.heappop(heap)  # remove largest (most negative = least actual)
# -heap[0] = kth smallest

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Apply your algorithm to the first example on LeetCode.
Track the heap contents at each insertion step.
Verify heap[0] gives the correct answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Heap is your go-to for:
  - "k-th largest/smallest"
  - "top k elements"
  - "merge k sorted lists"
  - "running median" (two heaps!)

Time: O(n log k) — much better than O(n log n) sorting when k is small!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

import heapq
heap = []
for x in data:
    heapq.heappush(heap, x)  # or -x for max-heap
    if len(heap) > k:
        heapq.heappop(heap)
return heap[0]  # or -heap[0] for max-heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n log k) for building heap of size k
Space: O(k) for the heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Using a max-heap when min-heap is needed (or vice versa)
2. Forgetting to negate for max-heap in Python
3. Not maintaining heap size at exactly k
4. Confusing heapq.heappush with heapq.heapreplace

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Can you identify whether this problem needs a min-heap or max-heap?
Practice: sort a list using only heap operations (heapsort).
```

---

## Problem 193/250 — LeetCode #621: Task Scheduler

**Pattern:** `Heap / Greedy` | **Position in Roadmap:** #193

```
LeetCode 621 — Task Scheduler
Problem #193 of 250 | Difficulty: Medium | Pattern: Heap + Greedy

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 621: Task Scheduler | Medium
Read carefully. Identify: what K-th element or K-element set are you finding?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Heap + Greedy

HEAP INSIGHT:
A MIN-HEAP of size k maintains the k LARGEST elements seen so far.
  - If you want k largest: use min-heap (top = smallest of the k largest)
  - If you want k smallest: use max-heap (top = largest of the k smallest)

For "kth largest": maintain min-heap of size k.
  The minimum of the k largest = kth largest!

In Python, heapq is a MIN-heap by default.
To simulate MAX-heap: negate values (push -x, peek -heap[0]).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Task Scheduler":
  What are you trying to RANK? Values? Frequencies? Distances?
  Do you want the TOP K or BOTTOM K?
  What property determines the ordering?

Heap gives you O(log k) insertion and O(1) top access.
This beats sorting (O(n log n)) when k << n.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

import heapq

# For k largest elements (min-heap of size k):
heap = []
for num in nums:
    heapq.heappush(heap, num)
    if len(heap) > k:
        heapq.heappop(heap)  # remove smallest
# heap now contains k largest, heap[0] = kth largest

# For k smallest elements (max-heap of size k):
heap = []
for num in nums:
    heapq.heappush(heap, -num)  # negate for max-heap
    if len(heap) > k:
        heapq.heappop(heap)  # remove largest (most negative = least actual)
# -heap[0] = kth smallest

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Apply your algorithm to the first example on LeetCode.
Track the heap contents at each insertion step.
Verify heap[0] gives the correct answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Heap is your go-to for:
  - "k-th largest/smallest"
  - "top k elements"
  - "merge k sorted lists"
  - "running median" (two heaps!)

Time: O(n log k) — much better than O(n log n) sorting when k is small!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

import heapq
heap = []
for x in data:
    heapq.heappush(heap, x)  # or -x for max-heap
    if len(heap) > k:
        heapq.heappop(heap)
return heap[0]  # or -heap[0] for max-heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n log k) for building heap of size k
Space: O(k) for the heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Using a max-heap when min-heap is needed (or vice versa)
2. Forgetting to negate for max-heap in Python
3. Not maintaining heap size at exactly k
4. Confusing heapq.heappush with heapq.heapreplace

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Can you identify whether this problem needs a min-heap or max-heap?
Practice: sort a list using only heap operations (heapsort).
```

---

## Problem 194/250 — LeetCode #378: Kth Smallest Element in a Sorted Matrix

**Pattern:** `Heap / Binary Search` | **Position in Roadmap:** #194

```
LeetCode 378 — Kth Smallest Element in a Sorted Matrix
Problem #194 of 250 | Difficulty: Medium | Pattern: Min Heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 378: Kth Smallest Element in a Sorted Matrix | Medium
Read carefully. Identify: what K-th element or K-element set are you finding?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Min Heap

HEAP INSIGHT:
A MIN-HEAP of size k maintains the k LARGEST elements seen so far.
  - If you want k largest: use min-heap (top = smallest of the k largest)
  - If you want k smallest: use max-heap (top = largest of the k smallest)

For "kth largest": maintain min-heap of size k.
  The minimum of the k largest = kth largest!

In Python, heapq is a MIN-heap by default.
To simulate MAX-heap: negate values (push -x, peek -heap[0]).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Kth Smallest Element in a Sorted Matrix":
  What are you trying to RANK? Values? Frequencies? Distances?
  Do you want the TOP K or BOTTOM K?
  What property determines the ordering?

Heap gives you O(log k) insertion and O(1) top access.
This beats sorting (O(n log n)) when k << n.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

import heapq

# For k largest elements (min-heap of size k):
heap = []
for num in nums:
    heapq.heappush(heap, num)
    if len(heap) > k:
        heapq.heappop(heap)  # remove smallest
# heap now contains k largest, heap[0] = kth largest

# For k smallest elements (max-heap of size k):
heap = []
for num in nums:
    heapq.heappush(heap, -num)  # negate for max-heap
    if len(heap) > k:
        heapq.heappop(heap)  # remove largest (most negative = least actual)
# -heap[0] = kth smallest

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Apply your algorithm to the first example on LeetCode.
Track the heap contents at each insertion step.
Verify heap[0] gives the correct answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Heap is your go-to for:
  - "k-th largest/smallest"
  - "top k elements"
  - "merge k sorted lists"
  - "running median" (two heaps!)

Time: O(n log k) — much better than O(n log n) sorting when k is small!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

import heapq
heap = []
for x in data:
    heapq.heappush(heap, x)  # or -x for max-heap
    if len(heap) > k:
        heapq.heappop(heap)
return heap[0]  # or -heap[0] for max-heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n log k) for building heap of size k
Space: O(k) for the heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Using a max-heap when min-heap is needed (or vice versa)
2. Forgetting to negate for max-heap in Python
3. Not maintaining heap size at exactly k
4. Confusing heapq.heappush with heapq.heapreplace

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Can you identify whether this problem needs a min-heap or max-heap?
Practice: sort a list using only heap operations (heapsort).
```

---

## Problem 195/250 — LeetCode #373: Find K Pairs with Smallest Sums

**Pattern:** `Heap` | **Position in Roadmap:** #195

```
LeetCode 373 — Find K Pairs with Smallest Sums
Problem #195 of 250 | Difficulty: Medium | Pattern: Min Heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 373: Find K Pairs with Smallest Sums | Medium
Read carefully. Identify: what K-th element or K-element set are you finding?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Min Heap

HEAP INSIGHT:
A MIN-HEAP of size k maintains the k LARGEST elements seen so far.
  - If you want k largest: use min-heap (top = smallest of the k largest)
  - If you want k smallest: use max-heap (top = largest of the k smallest)

For "kth largest": maintain min-heap of size k.
  The minimum of the k largest = kth largest!

In Python, heapq is a MIN-heap by default.
To simulate MAX-heap: negate values (push -x, peek -heap[0]).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Find K Pairs with Smallest Sums":
  What are you trying to RANK? Values? Frequencies? Distances?
  Do you want the TOP K or BOTTOM K?
  What property determines the ordering?

Heap gives you O(log k) insertion and O(1) top access.
This beats sorting (O(n log n)) when k << n.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

import heapq

# For k largest elements (min-heap of size k):
heap = []
for num in nums:
    heapq.heappush(heap, num)
    if len(heap) > k:
        heapq.heappop(heap)  # remove smallest
# heap now contains k largest, heap[0] = kth largest

# For k smallest elements (max-heap of size k):
heap = []
for num in nums:
    heapq.heappush(heap, -num)  # negate for max-heap
    if len(heap) > k:
        heapq.heappop(heap)  # remove largest (most negative = least actual)
# -heap[0] = kth smallest

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Apply your algorithm to the first example on LeetCode.
Track the heap contents at each insertion step.
Verify heap[0] gives the correct answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Heap is your go-to for:
  - "k-th largest/smallest"
  - "top k elements"
  - "merge k sorted lists"
  - "running median" (two heaps!)

Time: O(n log k) — much better than O(n log n) sorting when k is small!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

import heapq
heap = []
for x in data:
    heapq.heappush(heap, x)  # or -x for max-heap
    if len(heap) > k:
        heapq.heappop(heap)
return heap[0]  # or -heap[0] for max-heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n log k) for building heap of size k
Space: O(k) for the heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Using a max-heap when min-heap is needed (or vice versa)
2. Forgetting to negate for max-heap in Python
3. Not maintaining heap size at exactly k
4. Confusing heapq.heappush with heapq.heapreplace

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Can you identify whether this problem needs a min-heap or max-heap?
Practice: sort a list using only heap operations (heapsort).
```

---

## Problem 196/250 — LeetCode #295: Find Median from Data Stream

**Pattern:** `Two Heaps` | **Position in Roadmap:** #196

```
LeetCode 295 — Find Median from Data Stream
Problem #196 of 250 | Difficulty: Hard | Pattern: Two Heaps

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 295: Find Median from Data Stream | Hard
Read carefully. Identify: what K-th element or K-element set are you finding?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Two Heaps

HEAP INSIGHT:
A MIN-HEAP of size k maintains the k LARGEST elements seen so far.
  - If you want k largest: use min-heap (top = smallest of the k largest)
  - If you want k smallest: use max-heap (top = largest of the k smallest)

For "kth largest": maintain min-heap of size k.
  The minimum of the k largest = kth largest!

In Python, heapq is a MIN-heap by default.
To simulate MAX-heap: negate values (push -x, peek -heap[0]).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Find Median from Data Stream":
  What are you trying to RANK? Values? Frequencies? Distances?
  Do you want the TOP K or BOTTOM K?
  What property determines the ordering?

Heap gives you O(log k) insertion and O(1) top access.
This beats sorting (O(n log n)) when k << n.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

import heapq

# For k largest elements (min-heap of size k):
heap = []
for num in nums:
    heapq.heappush(heap, num)
    if len(heap) > k:
        heapq.heappop(heap)  # remove smallest
# heap now contains k largest, heap[0] = kth largest

# For k smallest elements (max-heap of size k):
heap = []
for num in nums:
    heapq.heappush(heap, -num)  # negate for max-heap
    if len(heap) > k:
        heapq.heappop(heap)  # remove largest (most negative = least actual)
# -heap[0] = kth smallest

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Apply your algorithm to the first example on LeetCode.
Track the heap contents at each insertion step.
Verify heap[0] gives the correct answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Heap is your go-to for:
  - "k-th largest/smallest"
  - "top k elements"
  - "merge k sorted lists"
  - "running median" (two heaps!)

Time: O(n log k) — much better than O(n log n) sorting when k is small!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

import heapq
heap = []
for x in data:
    heapq.heappush(heap, x)  # or -x for max-heap
    if len(heap) > k:
        heapq.heappop(heap)
return heap[0]  # or -heap[0] for max-heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n log k) for building heap of size k
Space: O(k) for the heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Using a max-heap when min-heap is needed (or vice versa)
2. Forgetting to negate for max-heap in Python
3. Not maintaining heap size at exactly k
4. Confusing heapq.heappush with heapq.heapreplace

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Can you identify whether this problem needs a min-heap or max-heap?
Practice: sort a list using only heap operations (heapsort).
```

---

## Problem 197/250 — LeetCode #24: Swap Nodes in Pairs

**Pattern:** `Linked List` | **Position in Roadmap:** #197

```
LeetCode 24 — Swap Nodes in Pairs
Problem #197 of 250 | Difficulty: Medium | Pattern: Linked List — Pointer Manipulation

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 24: Swap Nodes in Pairs | Medium
Read carefully. Draw the linked list and desired output on paper first.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Linked List — Pointer Manipulation

LINKED LIST INSIGHT:
Before coding, always draw the pointer changes:
  What pointers need to change?
  In what order? (Changing in wrong order can lose references!)
  Do you need prev/curr/next tracking?
  Should you use a dummy head?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw the transformation:
  BEFORE: A→B→C→D→E
  AFTER:  [your target arrangement]

For "Swap Nodes in Pairs":
  What are the key pointer changes?
  Which segments of the list need to be processed?

Linked list operations: always save next BEFORE modifying pointers!
Use dummy head to handle head deletion/replacement uniformly.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Common linked list patterns for Swap Nodes in Pairs:

# Find tail or nth node:
fast = slow = head
while fast and fast.next: fast = fast.next; slow = slow.next

# Reverse a segment [left, right]:
prev = None; curr = start
for _ in range(length):
    nxt = curr.next; curr.next = prev; prev = curr; curr = nxt

# Connect segments after manipulation

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Trace through with a short list (3-5 nodes).
Draw each pointer state after each line of code.
Verify the final structure is correct.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Advanced linked list problems combine:
  Finding positions (fast/slow, nth from end)
  Reversing segments (three-pointer dance)
  Reconnecting segments (dummy head, tail pointers)

The order of pointer updates is critical — always save next before modifying!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

dummy = ListNode(0, head)
# Find relevant positions
# Perform pointer manipulations
# Reconnect segments
return dummy.next

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(1)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not saving next before modifying pointers
2. Not using dummy head (head deletion becomes special case)
3. Off-by-one in counting positions
4. Not reconnecting tail after segment manipulation

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw your solution on paper with pointer arrows before coding.
Then translate the drawing to code.
```

---

## Problem 198/250 — LeetCode #25: Reverse Nodes in k-Group

**Pattern:** `Linked List` | **Position in Roadmap:** #198

```
LeetCode 25 — Reverse Nodes in k-Group
Problem #198 of 250 | Difficulty: Hard | Pattern: Linked List — Segment Reversal

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 25: Reverse Nodes in k-Group | Hard
Read carefully. Draw the linked list and desired output on paper first.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Linked List — Segment Reversal

LINKED LIST INSIGHT:
Before coding, always draw the pointer changes:
  What pointers need to change?
  In what order? (Changing in wrong order can lose references!)
  Do you need prev/curr/next tracking?
  Should you use a dummy head?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw the transformation:
  BEFORE: A→B→C→D→E
  AFTER:  [your target arrangement]

For "Reverse Nodes in k-Group":
  What are the key pointer changes?
  Which segments of the list need to be processed?

Linked list operations: always save next BEFORE modifying pointers!
Use dummy head to handle head deletion/replacement uniformly.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Common linked list patterns for Reverse Nodes in k-Group:

# Find tail or nth node:
fast = slow = head
while fast and fast.next: fast = fast.next; slow = slow.next

# Reverse a segment [left, right]:
prev = None; curr = start
for _ in range(length):
    nxt = curr.next; curr.next = prev; prev = curr; curr = nxt

# Connect segments after manipulation

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Trace through with a short list (3-5 nodes).
Draw each pointer state after each line of code.
Verify the final structure is correct.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Advanced linked list problems combine:
  Finding positions (fast/slow, nth from end)
  Reversing segments (three-pointer dance)
  Reconnecting segments (dummy head, tail pointers)

The order of pointer updates is critical — always save next before modifying!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

dummy = ListNode(0, head)
# Find relevant positions
# Perform pointer manipulations
# Reconnect segments
return dummy.next

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(1)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not saving next before modifying pointers
2. Not using dummy head (head deletion becomes special case)
3. Off-by-one in counting positions
4. Not reconnecting tail after segment manipulation

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw your solution on paper with pointer arrows before coding.
Then translate the drawing to code.
```

---

## Problem 199/250 — LeetCode #92: Reverse Linked List II

**Pattern:** `Linked List` | **Position in Roadmap:** #199

```
LeetCode 92 — Reverse Linked List II
Problem #199 of 250 | Difficulty: Medium | Pattern: Linked List — Partial Reversal

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 92: Reverse Linked List II | Medium
Read carefully. Draw the linked list and desired output on paper first.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Linked List — Partial Reversal

LINKED LIST INSIGHT:
Before coding, always draw the pointer changes:
  What pointers need to change?
  In what order? (Changing in wrong order can lose references!)
  Do you need prev/curr/next tracking?
  Should you use a dummy head?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw the transformation:
  BEFORE: A→B→C→D→E
  AFTER:  [your target arrangement]

For "Reverse Linked List II":
  What are the key pointer changes?
  Which segments of the list need to be processed?

Linked list operations: always save next BEFORE modifying pointers!
Use dummy head to handle head deletion/replacement uniformly.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Common linked list patterns for Reverse Linked List II:

# Find tail or nth node:
fast = slow = head
while fast and fast.next: fast = fast.next; slow = slow.next

# Reverse a segment [left, right]:
prev = None; curr = start
for _ in range(length):
    nxt = curr.next; curr.next = prev; prev = curr; curr = nxt

# Connect segments after manipulation

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Trace through with a short list (3-5 nodes).
Draw each pointer state after each line of code.
Verify the final structure is correct.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Advanced linked list problems combine:
  Finding positions (fast/slow, nth from end)
  Reversing segments (three-pointer dance)
  Reconnecting segments (dummy head, tail pointers)

The order of pointer updates is critical — always save next before modifying!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

dummy = ListNode(0, head)
# Find relevant positions
# Perform pointer manipulations
# Reconnect segments
return dummy.next

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(1)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not saving next before modifying pointers
2. Not using dummy head (head deletion becomes special case)
3. Off-by-one in counting positions
4. Not reconnecting tail after segment manipulation

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw your solution on paper with pointer arrows before coding.
Then translate the drawing to code.
```

---

## Problem 200/250 — LeetCode #61: Rotate List

**Pattern:** `Linked List` | **Position in Roadmap:** #200

```
LeetCode 61 — Rotate List
Problem #200 of 250 | Difficulty: Medium | Pattern: Linked List — Find New Head

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 61: Rotate List | Medium
Read carefully. Draw the linked list and desired output on paper first.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Linked List — Find New Head

LINKED LIST INSIGHT:
Before coding, always draw the pointer changes:
  What pointers need to change?
  In what order? (Changing in wrong order can lose references!)
  Do you need prev/curr/next tracking?
  Should you use a dummy head?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw the transformation:
  BEFORE: A→B→C→D→E
  AFTER:  [your target arrangement]

For "Rotate List":
  What are the key pointer changes?
  Which segments of the list need to be processed?

Linked list operations: always save next BEFORE modifying pointers!
Use dummy head to handle head deletion/replacement uniformly.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Common linked list patterns for Rotate List:

# Find tail or nth node:
fast = slow = head
while fast and fast.next: fast = fast.next; slow = slow.next

# Reverse a segment [left, right]:
prev = None; curr = start
for _ in range(length):
    nxt = curr.next; curr.next = prev; prev = curr; curr = nxt

# Connect segments after manipulation

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Trace through with a short list (3-5 nodes).
Draw each pointer state after each line of code.
Verify the final structure is correct.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Advanced linked list problems combine:
  Finding positions (fast/slow, nth from end)
  Reversing segments (three-pointer dance)
  Reconnecting segments (dummy head, tail pointers)

The order of pointer updates is critical — always save next before modifying!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

dummy = ListNode(0, head)
# Find relevant positions
# Perform pointer manipulations
# Reconnect segments
return dummy.next

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(1)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not saving next before modifying pointers
2. Not using dummy head (head deletion becomes special case)
3. Off-by-one in counting positions
4. Not reconnecting tail after segment manipulation

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw your solution on paper with pointer arrows before coding.
Then translate the drawing to code.
```

---

