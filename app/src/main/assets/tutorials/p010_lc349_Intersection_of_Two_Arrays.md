# 📘 Problem 10 of 250 — LeetCode 349: Intersection of Two Arrays

> **Pattern:** Hash Set | **Roadmap Position:** #10/250

---

LeetCode 349 — Intersection of Two Arrays
Problem #10 of 250 | Difficulty: Easy | Pattern: Hash Set — Intersection

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given two integer arrays nums1 and nums2,
return their intersection — elements that appear in BOTH.
Each element in result must be UNIQUE.

Example:
  nums1 = [1, 2, 2, 1], nums2 = [2, 2]
  → [2]  (2 is in both, but only once in result)

Example:
  nums1 = [4, 9, 5], nums2 = [9, 4, 9, 8, 4]
  → [9, 4]  (9 and 4 are in both)

No duplicates in the result — it's a SET intersection.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Convert nums1 to a set. Check each element of nums2.

  nums1 = [4, 9, 5]  →  set1 = {4, 9, 5}

  For nums2 = [9, 4, 9, 8, 4]:
  num | in set1? | result
  ─────────────────────────
   9  |   YES    | {9}
   4  |   YES    | {9, 4}
   9  |   YES    | {9, 4}  (already in result, no change)
   8  |   NO     | {9, 4}
   4  |   YES    | {9, 4}  (already in result)

  Return list({9, 4}) = [9, 4]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Find what's common between two groups.

Like: "Which students are in BOTH Math class AND Science class?"
  Math: {Alice, Bob, Carol, Dave}
  Science: {Bob, Eve, Carol}
  Answer: {Bob, Carol} — they're in both!

In code:
  Convert one array to a set (for fast lookup)
  Check each element of the other array against that set
  Collect matches (using a result set to avoid duplicates)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

set1 = set(nums1)    # O(1) lookup for nums1 elements
result = set()       # Automatically handles uniqueness

for num in nums2:
    if num in set1:
        result.add(num)

return list(result)

# Python one-liner:
return list(set(nums1) & set(nums2))
# & is the set intersection operator!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums1 = [1, 2, 2, 1], nums2 = [2, 2]

set1 = {1, 2}
result = {}

num=2: 2 in {1,2}? YES → result={2}
num=2: 2 in {1,2}? YES → already in result → result={2}

Return: [2] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Set intersection finds common elements efficiently.

Key insight: Converting to a set gives O(1) lookup.
Result set ensures NO DUPLICATES in the output.

Python's & operator is literally set intersection:
  {1,2,3} & {2,3,4} = {2,3}

Compare with LC350 (Intersection of Two Arrays II):
  That one KEEPS DUPLICATES based on frequency.
  This one (LC349) returns unique elements only.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

set1 = set(nums1)
result = set()
for num in nums2:
    if num in set1:
        result.add(num)
return list(result)

# Or simply:
return list(set(nums1) & set(nums2))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(m + n) — build set1 O(m), scan nums2 O(n)
Space: O(m) — set1 stores all unique elements of nums1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Using a list for result (then duplicates can sneak in)
2. Using list search O(n) instead of set lookup O(1)
3. Confusing with LC350 which keeps duplicates

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC350 — Intersection of Two Arrays II
  Now count matters! [2,2] ∩ [2,2,3] = [2,2] not [2]
  Use a HashMap/Counter instead of a Set

---

*Next up: Problem 11 of 250*
