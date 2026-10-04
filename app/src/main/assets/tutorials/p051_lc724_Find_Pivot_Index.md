# 📘 Problem 51 of 250 — LeetCode 724: Find Pivot Index

> **Pattern:** Prefix Sum | **Roadmap Position:** #51/250

---

LeetCode 724 — Find Pivot Index
Problem #51 of 250 | Difficulty: Easy | Pattern: Prefix Sum — Left Sum = Right Sum

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Find the index where the sum of elements to the LEFT equals the sum to the RIGHT.

Example:
  nums = [1, 7, 3, 6, 5, 6]
  Index 3: left=[1,7,3]=11, right=[5,6]=11 → pivot!
  Return 3

Example:
  nums = [1, 2, 3] → no pivot, return -1

Left sum doesn't include pivot itself. Same for right sum.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Total sum = left_sum + pivot + right_sum
When left_sum == right_sum:
  left_sum == (total - pivot - left_sum)
  2 * left_sum + pivot = total
  left_sum = (total - pivot) / 2

Or more simply:
  Scan left to right, maintain left_sum.
  At each position: right_sum = total - left_sum - nums[i]
  If left_sum == right_sum → found pivot!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Balance a seesaw at each position.
Walk left to right, keeping a running left_sum.
At each position i:
  right_sum = total - left_sum - nums[i]
  If left_sum == right_sum → this is the pivot!

No need to recompute right_sum from scratch each time!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def pivotIndex(nums):
    total = sum(nums)
    left_sum = 0

    for i, num in enumerate(nums):
        right_sum = total - left_sum - num
        if left_sum == right_sum:
            return i
        left_sum += num

    return -1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums = [1, 7, 3, 6, 5, 6], total=28

i=0: right=28-0-1=27, left=0≠27, left=1
i=1: right=28-1-7=20, left=1≠20, left=8
i=2: right=28-8-3=17, left=8≠17, left=11
i=3: right=28-11-6=11, left=11==11 → return 3 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Total sum trick:
  right_sum = total - left_sum - current_element
  This gives you right_sum in O(1) without rescanning!

Build prefix sum on the fly, use total to compute suffix in O(1).
This is a classic prefix sum optimization.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

total = sum(nums)
left = 0
for i, num in enumerate(nums):
    if left == total - left - num: return i
    left += num
return -1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — one pass (plus O(n) for sum)
Space: O(1) — two variables

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Including pivot in left or right sum
2. Not computing total first
3. Using nested loops O(n²) — prefix sum trick makes it O(n)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC1991 — Find the Middle Index in Array (same problem, different name)
What if you needed the LAST pivot instead of first?

---

*Next up: Problem 52 of 250*
