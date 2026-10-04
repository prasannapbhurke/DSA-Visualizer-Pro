# 📘 Problem 11 of 250 — LeetCode 350: Intersection of Two Arrays II

> **Pattern:** Hash Map | **Roadmap Position:** #11/250

---

LeetCode 350 — Intersection of Two Arrays II
Problem #11 of 250 | Difficulty: Easy | Pattern: HashMap — Frequency Count

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Like LC349 but NOW DUPLICATES MATTER.
The number of times an element appears in the result
= MINIMUM of how many times it appears in each array.

Example:
  nums1 = [1, 2, 2, 1], nums2 = [2, 2]
  2 appears twice in nums1, twice in nums2 → min(2,2) = 2 times
  → [2, 2]

Example:
  nums1 = [4, 9, 5], nums2 = [9, 4, 9, 8, 4]
  4: min(1,2)=1 time, 9: min(1,2)=1 time
  → [4, 9]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Count frequencies, then take the minimum for each element.

  nums1 = [1,2,2,1], nums2 = [2,2]

  count1 = {1:2, 2:2}
  count2 = {2:2}

  For each element in count2:
    2: min(count1[2], count2[2]) = min(2,2) = 2 times

  Result: [2, 2]

Strategy: Build frequency map of nums1.
Then for each num in nums2, if count > 0, include it and decrement.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

You have boxes of items:
  Box A: [apple, apple, banana, orange]
  Box B: [apple, apple, mango]

What do they share (keeping counts)?
  apple appears in both — 2 times in A, 2 in B → take 2 apples

With a counter:
  Build counter for nums1
  Walk through nums2
  If counter[num] > 0: include it, decrement counter

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

from collections import Counter

count = Counter(nums1)   # {num: frequency}
result = []

for num in nums2:
    if count[num] > 0:       # This num exists in nums1
        result.append(num)   # Include in result
        count[num] -= 1      # "Use up" one occurrence

return result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums1 = [1,2,2,1], nums2 = [2,2]

count = {1:2, 2:2}
result = []

num=2: count[2]=2 > 0 → result=[2], count={1:2, 2:1}
num=2: count[2]=1 > 0 → result=[2,2], count={1:2, 2:0}

Return: [2, 2] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

HashMap with frequency counts:
  Unlike LC349 (set intersection, unique only),
  here we track HOW MANY times each element appears.

The decrement trick ensures we never use more copies
than actually exist in nums1.

Think of count[num] as "remaining budget":
  Start with how many are in nums1
  Spend one each time we find a match in nums2

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

from collections import Counter
count = Counter(nums1)
result = []
for num in nums2:
    if count[num] > 0:
        result.append(num)
        count[num] -= 1
return result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(m + n) — build counter O(m), scan nums2 O(n)
Space: O(min(m,n)) — counter on the smaller array

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Using a set (loses count information)
2. Not decrementing count (gives too many duplicates)
3. Confusing with LC349 (that one uses set, no duplicates)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Follow-up: What if arrays are sorted?
  Use two pointers! Compare nums1[i] and nums2[j]:
  If equal → add to result, advance both
  If nums1[i] < nums2[j] → advance i
  If nums1[i] > nums2[j] → advance j

---

*Next up: Problem 12 of 250*
