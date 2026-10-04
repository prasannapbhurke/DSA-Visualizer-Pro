# 📘 Problem 88 of 250 — LeetCode 647: Palindromic Substrings

> **Pattern:** Two Pointers / DP | **Roadmap Position:** #88/250

---

LeetCode 647 — Palindromic Substrings
Problem #88 of 250 | Difficulty: Medium | Pattern: Expand Around Center

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 647: Palindromic Substrings
Difficulty: Medium | Core Pattern: Expand Around Center

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Expand Around Center

For palindromes, expand from each center position (including between characters for even length).

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Palindromic Substrings:
  The key insight is to use Expand Around Center to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Expand Around Center pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Expand Around Center

For palindromes, expand from each center position (including between characters for even length).

This is problem #88 of 250 in your roadmap.
It reinforces the Expand Around Center pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Expand Around Center template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Expand Around Center patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Palindromic Substrings:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!

---

*Next up: Problem 89 of 250*
