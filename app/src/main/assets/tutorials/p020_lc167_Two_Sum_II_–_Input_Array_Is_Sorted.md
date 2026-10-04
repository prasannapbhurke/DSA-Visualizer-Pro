# 📘 Problem 20 of 250 — LeetCode 167: Two Sum II – Input Array Is Sorted

> **Pattern:** Two Pointers | **Roadmap Position:** #20/250

---

LeetCode 167 — Two Sum II - Input Array Is Sorted
Problem #20 of 250 | Difficulty: Easy | Pattern: Two Pointers — Converging on Sorted Array

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Array is sorted. Two pointers from both ends.
If sum too large → move right pointer left (decrease sum).
If sum too small → move left pointer right (increase sum).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Two Pointers — Converging on Sorted Array

left=0, right=len-1
While left<right:
  total = numbers[left]+numbers[right]
  If total==target: return [left+1, right+1] (1-indexed!)
  If total<target: left++
  If total>target: right--

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def twoSum(numbers, target):
    left, right = 0, len(numbers)-1
    while left < right:
        total = numbers[left] + numbers[right]
        if total == target: return [left+1, right+1]
        elif total < target: left += 1
        else: right -= 1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

numbers=[2,7,11,15], target=9
left=0,right=3: 2+15=17>9 → right=2
left=0,right=2: 2+11=13>9 → right=1
left=0,right=1: 2+7=9==9 → return [1,2] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Sorted array + find pair summing to target = two pointers from ends. This is O(n) vs O(n log n) for binary search approach. The sorted property lets us make directional decisions.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #20 (Two Sum II - Input Array Is Sorted):
  Can you apply the Two Pointers — Converging on Sorted Array pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?

---

*Next up: Problem 21 of 250*
