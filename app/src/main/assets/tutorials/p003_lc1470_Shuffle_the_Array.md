# 📘 Problem 3 of 250 — LeetCode 1470: Shuffle the Array

> **Pattern:** Array | **Roadmap Position:** #3/250

---

LeetCode 1470 — Shuffle the Array
Problem #3 of 250 | Difficulty: Easy | Pattern: Array — Interleaving

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given array [x1,x2,...,xn,y1,y2,...,yn], return [x1,y1,x2,y2,...,xn,yn].
Interleave first half with second half.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Array — Interleaving

n = len(nums)//2. Build result taking nums[i] from first half, nums[i+n] from second half.
  for i in 0..n-1: result.append(nums[i]); result.append(nums[i+n])

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def shuffle(nums, n):
    result = []
    for i in range(n):
        result.append(nums[i])
        result.append(nums[i+n])
    return result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums=[2,5,1,3,4,7], n=3
i=0: append nums[0]=2, nums[3]=3 → [2,3]
i=1: append nums[1]=5, nums[4]=4 → [2,3,5,4]
i=2: append nums[2]=1, nums[5]=7 → [2,3,5,4,1,7] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Interleaving: pair element i from first half with element i from second half. Index arithmetic: second half starts at index n.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #3 (Shuffle the Array):
  Can you apply the Array — Interleaving pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?

---

*Next up: Problem 4 of 250*
