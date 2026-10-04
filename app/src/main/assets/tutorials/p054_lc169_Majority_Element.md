# 📘 Problem 54 of 250 — LeetCode 169: Majority Element

> **Pattern:** Hashing / Voting | **Roadmap Position:** #54/250

---

LeetCode 169 — Majority Element
Problem #54 of 250 | Difficulty: Easy | Pattern: Boyer-Moore Voting / HashMap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Find the element that appears MORE THAN n/2 times.
(The majority element — it always exists.)

Example:
  nums = [3,2,3] → 3 (appears 2 times, n=3, 2>3/2=1.5)
  nums = [2,2,1,1,1,2,2] → 2 (appears 4 times, n=7, 4>3.5)

It's GUARANTEED that majority element exists.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Three approaches of increasing cleverness:

1. HashMap: count frequencies, return element with count > n//2
   O(n) time, O(n) space

2. Sort: middle element is always the majority
   O(n log n) time, O(1) space

3. Boyer-Moore Voting: O(n) time, O(1) space!
   candidate = nums[0], count = 1
   For each num: if num==candidate → count++, else → count--
   If count==0 → switch candidate
   The survivor is the majority!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Boyer-Moore is like a battle royal:
  Majority elements TEAM UP against everyone else.
  Every mismatch cancels one majority vs one minority.
  Since majority appears > n/2 times, they always WIN.

The last remaining candidate is the majority element!

Think of it as: majority votes cancel minority votes.
With > n/2 votes, majority always has votes left.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Boyer-Moore Voting (O(n) time, O(1) space):
def majorityElement(nums):
    candidate = nums[0]
    count = 1

    for i in range(1, len(nums)):
        if count == 0:
            candidate = nums[i]   # New candidate!
            count = 1
        elif nums[i] == candidate:
            count += 1             # Support our candidate
        else:
            count -= 1             # Battle: minority cancels majority

    return candidate   # Majority always survives

# Simple HashMap approach:
from collections import Counter
def majorityElement(nums):
    return Counter(nums).most_common(1)[0][0]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums = [2,2,1,1,1,2,2]

candidate=2, count=1

num=2: same → count=2
num=1: diff → count=1
num=1: diff → count=0
num=1: count=0 → new candidate=1, count=1
num=2: diff → count=0
num=2: count=0 → new candidate=2, count=1

Return candidate=2 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Boyer-Moore Voting Algorithm:
  The majority (>n/2) always survives the "battle."
  Each non-match cancels one vote.
  Since majority has more than HALF the votes, it can't be completely cancelled.

This is brilliant because it needs NO extra space!

Warning: Boyer-Moore only works when majority is GUARANTEED.
If no guarantee, you'd need a second pass to verify.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

candidate, count = nums[0], 1
for num in nums[1:]:
    if count == 0: candidate, count = num, 1
    elif num == candidate: count += 1
    else: count -= 1
return candidate

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Boyer-Moore: O(n) time, O(1) space
HashMap: O(n) time, O(n) space
Sort: O(n log n) time, O(1) space

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Using Boyer-Moore when majority not guaranteed (must verify!)
2. Returning count instead of candidate
3. Not handling single-element arrays

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC229 — Majority Element II (elements appearing > n/3 times)
Boyer-Moore variant with 2 candidates simultaneously!

---

*Next up: Problem 55 of 250*
