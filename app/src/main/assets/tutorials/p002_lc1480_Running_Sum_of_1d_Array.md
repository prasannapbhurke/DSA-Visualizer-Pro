# 📘 Problem 2 of 250 — LeetCode 1480: Running Sum of 1d Array

> **Pattern:** Array | **Roadmap Position:** #2/250

---

LeetCode 1480 — Running Sum of 1d Array
Problem #2 of 250 | Difficulty: Easy | Pattern: Prefix Sum — In-place

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Running sum: each element becomes the sum of itself and all previous elements.
nums=[1,2,3,4] → [1,3,6,10]
nums[i] += nums[i-1] builds it in-place.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Prefix Sum — In-place

Walk left to right. At each index i (starting from 1): nums[i] = nums[i] + nums[i-1].
The previous element already holds the running sum up to that point!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def runningSum(nums):
    for i in range(1, len(nums)):
        nums[i] += nums[i-1]
    return nums

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums=[1,2,3,4]
i=1: nums[1]=2+1=3 → [1,3,3,4]
i=2: nums[2]=3+3=6 → [1,3,6,4]
i=3: nums[3]=4+6=10 → [1,3,6,10] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

In-place prefix sum. Previous answer helps compute current. This is the FOUNDATION of prefix sum — understand it deeply!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #2 (Running Sum of 1d Array):
  Can you apply the Prefix Sum — In-place pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?

---

*Next up: Problem 3 of 250*
