# 📘 Problem 6 of 250 — LeetCode 1: Two Sum

> **Pattern:** Hash Map | **Roadmap Position:** #6/250

---

LeetCode 1 — Two Sum
Problem #6 of 250 | Difficulty: Easy | Pattern: HashMap — Complement Lookup

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Find two numbers that add to target. For each number, ask: 'have I seen target-this_number before?'
Use HashMap to store (number → index) for O(1) lookup.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: HashMap — Complement Lookup

seen = {}
For each (i, num): check if (target-num) in seen → return [seen[target-num], i]
Else: add num to seen.

Key: check BEFORE adding to avoid using same element twice.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def twoSum(nums, target):
    seen = {}
    for i, num in enumerate(nums):
        complement = target - num
        if complement in seen:
            return [seen[complement], i]
        seen[num] = i

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums=[2,7,11,15], target=9
i=0, num=2: need 7, seen={}? No → seen={2:0}
i=1, num=7: need 2, seen={2:0}? YES → return [0,1] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

The MOST important HashMap pattern: complement lookup. Store what you've seen, look for what you need. O(n) vs O(n²) brute force.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #6 (Two Sum):
  Can you apply the HashMap — Complement Lookup pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?

---

*Next up: Problem 7 of 250*
