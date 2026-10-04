# 📘 Problem 7 of 250 — LeetCode 217: Contains Duplicate

> **Pattern:** Hash Set | **Roadmap Position:** #7/250

---

LeetCode 217 — Contains Duplicate
Problem #7 of 250 | Difficulty: Easy | Pattern: Hash Set — Seen Tracker

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given an integer array nums,
return true if any value appears AT LEAST TWICE.
Return false if every element is DISTINCT.

Example:
  nums = [1, 2, 3, 1] → true (1 appears twice)
  nums = [1, 2, 3, 4] → false (all unique)
  nums = [1, 1, 1, 3, 3, 4, 3, 2, 4, 2] → true

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

As you walk through the array, ask:
  "Have I seen this number before?"

  nums = [1, 2, 3, 1]

  Step | num | seen set | Have I seen it?
  ───────────────────────────────────────
    1  |  1  | {}       | No → add → {1}
    2  |  2  | {1}      | No → add → {1,2}
    3  |  3  | {1,2}    | No → add → {1,2,3}
    4  |  1  | {1,2,3}  | YES → return true!

A HashSet gives O(1) lookup for "have I seen this?".

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Imagine checking ID cards at the door:
  You keep a list of IDs you've already let in.
  If someone shows an ID already on the list → duplicate!
  If you finish without finding one → all unique.

That list is your HashSet.

Another way to think:
  Sort the array → duplicates will be adjacent
  [1,2,3,1] sorted = [1,1,2,3]
  Check: is any nums[i] == nums[i+1]? → yes → true

HashSet is O(n), sorting is O(n log n). HashSet is better!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

seen = set()

for num in nums:
    if num in seen:      # Already saw this!
        return True
    seen.add(num)        # Mark as seen

return False             # No duplicates found

One-liner (Pythonic):
  return len(nums) != len(set(nums))
  # If set is smaller than array → duplicates existed

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums = [4, 1, 2, 1, 2]

seen = {}

num=4: 4 in {} ? No → seen={4}
num=1: 1 in {4}? No → seen={4,1}
num=2: 2 in {4,1}? No → seen={4,1,2}
num=1: 1 in {4,1,2}? YES → return True ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

The "Seen Tracker" pattern:
  Use a set to remember what you've visited.
  At each step, check if current is already in the set.

Whenever you see:
  - "Does any value appear more than once?"
  - "Are all elements distinct?"
  - "Have I encountered this before?"
→ Think HashSet / seen tracker

The set gives O(1) lookup — much faster than searching a list O(n).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

seen = set()
for item in collection:
    if item in seen:
        # FOUND duplicate / revisited
        return True
    seen.add(item)
return False

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — one pass, O(1) per lookup
Space: O(n) — set stores up to n unique elements

Sorting approach: O(n log n) time, O(1) space
Pythonic: len(nums) != len(set(nums)) — clean but creates entire set first

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Using a list instead of set — list lookup is O(n), set is O(1)
2. Adding before checking — check first, then add
3. Comparing values vs indices — we want to know if the VALUE repeated

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC219 — Contains Duplicate II
  (duplicates must be within k indices of each other)

LC220 — Contains Duplicate III
  (values must be within t of each other and indices within k)

---

*Next up: Problem 8 of 250*
