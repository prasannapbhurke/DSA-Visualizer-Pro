# 📘 Problem 102 of 250 — LeetCode 189: Rotate Array

> **Pattern:** Array | **Roadmap Position:** #102/250

---

LeetCode 189 — Rotate Array
Problem #102 of 250 | Difficulty: Medium | Pattern: Array — Triple Reversal

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 189: Rotate Array | Medium

Before anything else:
  → Read the problem statement carefully
  → Identify: what is the INPUT? what is the OUTPUT?
  → Write 2 examples by hand to confirm your understanding

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Array — Triple Reversal

Core Insight:
Rotating right by k is equivalent to:
1. Reverse entire array
2. Reverse first k elements
3. Reverse remaining n-k elements
This uses O(1) space!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Approach:
Triple reversal trick:
  k %= n (handle k > n)
  Reverse all: [1,2,3,4,5,6,7] → [7,6,5,4,3,2,1]
  Reverse first k=3: [5,6,7,4,3,2,1]... wait
  Actually: reverse first k → [7,6,5] then reverse rest [4,3,2,1]
  → [5,6,7,1,2,3,4]... let me recount

For k=3, n=7: rotate right by 3
  [1,2,3,4,5,6,7] → [5,6,7,1,2,3,4]
  Reverse all: [7,6,5,4,3,2,1]
  Reverse first k=3: [5,6,7,4,3,2,1]
  Reverse last n-k=4: [5,6,7,1,2,3,4] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def rotate(nums, k):
    n = len(nums)
    k %= n
    nums.reverse()
    nums[:k] = reversed(nums[:k])
    nums[k:] = reversed(nums[k:])

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums=[1,2,3,4,5,6,7], k=3

Reverse all: [7,6,5,4,3,2,1]
Reverse first 3: [5,6,7,4,3,2,1]
Reverse last 4: [5,6,7,1,2,3,4] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

The triple reversal trick works for rotating any array in O(1) space.
Memorize: reverse all → reverse first k → reverse last n-k.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def rotate(nums, k):
    n = len(nums)
    k %= n
    nums.reverse()
    nums[:k] = reversed(nums[:k])
    nums[k:] = reversed(nums[k:])

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(1)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — think: "what have I seen that looks like this?"
2. Brute force when a smarter approach exists
3. Off-by-one errors in indices/boundaries
4. Not handling edge cases (empty, single, all same)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: Can you identify 2 other LeetCode problems with the same pattern?
Pattern mastery is built through RECOGNITION, not memorization.

---

*Next up: Problem 103 of 250*
