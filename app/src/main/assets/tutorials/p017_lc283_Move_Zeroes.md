# 📘 Problem 17 of 250 — LeetCode 283: Move Zeroes

> **Pattern:** Two Pointers | **Roadmap Position:** #17/250

---

LeetCode 283 — Move Zeroes
Problem #17 of 250 | Difficulty: Easy | Pattern: Two Pointers — Partition

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Move all zeros to end while maintaining relative order of non-zeros.
Two pointers: slow tracks next non-zero write position, fast scans for non-zeros.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Two Pointers — Partition

slow=0 (where next non-zero goes)
For fast in 0..n-1:
  If nums[fast]!=0: swap nums[slow] and nums[fast]; slow++

All non-zeros shifted left in relative order, zeros end up at the right.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def moveZeroes(nums):
    slow = 0
    for fast in range(len(nums)):
        if nums[fast] != 0:
            nums[slow], nums[fast] = nums[fast], nums[slow]
            slow += 1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums=[0,1,0,3,12]
slow=0
fast=0: 0 is zero, skip
fast=1: 1≠0 → swap(nums[0],nums[1])→[1,0,0,3,12], slow=1
fast=2: 0, skip
fast=3: 3≠0 → swap(nums[1],nums[3])→[1,3,0,0,12], slow=2
fast=4: 12≠0 → swap(nums[2],nums[4])→[1,3,12,0,0], slow=3
Result: [1,3,12,0,0] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Partition array: non-zeros first, zeros last. The slow pointer always points to the next 'slot' for a non-zero. Swapping maintains relative order.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #17 (Move Zeroes):
  Can you apply the Two Pointers — Partition pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?

---

*Next up: Problem 18 of 250*
