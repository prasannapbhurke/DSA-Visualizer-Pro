# 📘 Problem 15 of 250 — LeetCode 27: Remove Element

> **Pattern:** Two Pointers | **Roadmap Position:** #15/250

---

LeetCode 27 — Remove Element
Problem #15 of 250 | Difficulty: Easy | Pattern: Two Pointers — Write Head

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given an array nums and a value val,
remove all occurrences of val in-place.
Return the count k of remaining elements.

Example:
  nums = [3,2,2,3], val = 3
  Remove both 3s → nums = [2,2,_,_], return 2

Example:
  nums = [0,1,2,2,3,0,4,2], val = 2
  Remove all 2s → nums = [0,1,3,0,4,_,_,_], return 5

The first k elements of nums should hold the non-val elements.
The rest doesn't matter.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Two pointers: slow (write position) reads for non-val, fast scans all.

  nums = [3, 2, 2, 3], val = 3

  fast | nums[fast] | == val? | slow | Action
  ──────────────────────────────────────────────
    0  |     3      |   YES   |  0   | Skip (don't write)
    1  |     2      |   NO    |  0   | Write nums[0]=2, slow=1
    2  |     2      |   NO    |  1   | Write nums[1]=2, slow=2
    3  |     3      |   YES   |  2   | Skip

  Return slow=2, nums[0..1] = [2,2]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Imagine copying a book but SKIPPING certain words (val).
  - fast reads every word
  - slow marks where to write the next "keep" word
  - When fast finds a "remove" word, slow stays put

Only write when we encounter something we want to keep.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

slow = 0

for fast in range(len(nums)):
    if nums[fast] != val:     # This element is NOT val — keep it!
        nums[slow] = nums[fast]
        slow += 1

return slow   # slow = count of remaining elements

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums = [0,1,2,2,3,0,4,2], val = 2

slow = 0

fast=0: nums[0]=0 ≠ 2 → nums[0]=0, slow=1
fast=1: nums[1]=1 ≠ 2 → nums[1]=1, slow=2
fast=2: nums[2]=2 == 2 → skip
fast=3: nums[3]=2 == 2 → skip
fast=4: nums[4]=3 ≠ 2 → nums[2]=3, slow=3
fast=5: nums[5]=0 ≠ 2 → nums[3]=0, slow=4
fast=6: nums[6]=4 ≠ 2 → nums[4]=4, slow=5
fast=7: nums[7]=2 == 2 → skip

Return 5, nums = [0,1,3,0,4,_,_,_] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

The write-head pattern:
  slow only moves when we find something worth keeping.
  Effectively "compacts" the array by removing unwanted elements.

Almost identical to LC26 and LC283 — same two-pointer pattern
just with different keep conditions:
  LC26: keep if different from previous
  LC283: keep if non-zero
  LC27: keep if != val

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

slow = 0
for fast in range(len(nums)):
    if nums[fast] != val:   # Change condition as needed
        nums[slow] = nums[fast]
        slow += 1
return slow

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — one pass
Space: O(1) — in-place

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Using nums.remove(val) — that's O(n) per call, O(n²) total
2. Decrementing length tracking incorrectly
3. Returning fast instead of slow

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

What if you also need to return the modified array (not just count)?
Compare your solution to LC26 and LC283.
Notice the SAME template just with different filter conditions.

---

*Next up: Problem 16 of 250*
