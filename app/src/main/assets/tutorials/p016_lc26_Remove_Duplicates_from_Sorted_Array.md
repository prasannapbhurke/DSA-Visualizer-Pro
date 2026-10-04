# 📘 Problem 16 of 250 — LeetCode 26: Remove Duplicates from Sorted Array

> **Pattern:** Two Pointers | **Roadmap Position:** #16/250

---

LeetCode 26 — Remove Duplicates from Sorted Array
Problem #16 of 250 | Difficulty: Easy | Pattern: Two Pointers — Slow/Fast

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Array is sorted, so duplicates are adjacent. Use two pointers: slow (write position) and fast (read position).
When fast finds a new unique value, write it to slow position and advance slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Two Pointers — Slow/Fast

slow=0 (next write position)
For fast in 1..n-1:
  If nums[fast] != nums[slow]: slow++, nums[slow]=nums[fast]
Return slow+1 (count of unique elements)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def removeDuplicates(nums):
    slow = 0
    for fast in range(1, len(nums)):
        if nums[fast] != nums[slow]:
            slow += 1
            nums[slow] = nums[fast]
    return slow + 1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums=[1,1,2,2,3]
slow=0
fast=1: 1==1, skip
fast=2: 2!=1 → slow=1, nums[1]=2 → [1,2,2,2,3]
fast=3: 2==2, skip
fast=4: 3!=2 → slow=2, nums[2]=3 → [1,2,3,2,3]
Return slow+1=3 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Slow/fast pointer pattern for in-place array modification. Slow tracks the 'write' position. Fast explores. They sync when a new unique value is found.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #16 (Remove Duplicates from Sorted Array):
  Can you apply the Two Pointers — Slow/Fast pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?

---

*Next up: Problem 17 of 250*
