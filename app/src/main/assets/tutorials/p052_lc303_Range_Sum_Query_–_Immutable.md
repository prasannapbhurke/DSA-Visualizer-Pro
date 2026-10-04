# 📘 Problem 52 of 250 — LeetCode 303: Range Sum Query – Immutable

> **Pattern:** Prefix Sum | **Roadmap Position:** #52/250

---

LeetCode 303 — Range Sum Query - Immutable
Problem #52 of 250 | Difficulty: Easy | Pattern: Prefix Sum — Precompute for O(1) Queries

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given an array nums, answer multiple range sum queries.
sumRange(left, right) = sum of nums[left..right] inclusive.

Example:
  nums = [-2, 0, 3, -5, 2, -1]
  sumRange(0,2) = -2+0+3 = 1
  sumRange(2,5) = 3+(-5)+2+(-1) = -1
  sumRange(0,5) = -2+0+3+(-5)+2+(-1) = -3

Multiple queries! Precompute to make each query O(1).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Precompute prefix sums: prefix[i] = sum of nums[0..i-1]

  nums    = [-2, 0, 3, -5, 2, -1]
  prefix  = [0, -2, -2, 1, -4, -2, -3]
  (prefix[0]=0, prefix[i]=prefix[i-1]+nums[i-1])

  sumRange(left, right) = prefix[right+1] - prefix[left]

  sumRange(0,2) = prefix[3] - prefix[0] = 1 - 0 = 1 ✓
  sumRange(2,5) = prefix[6] - prefix[2] = -3 - (-2) = -1 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Build "cumulative distances" on a number line.
To find the distance between any two points: just subtract endpoints.

Prefix[i] stores: "total miles from start to position i"
Distance from A to B = prefix[B] - prefix[A]

Precompute once → answer any query in O(1)!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

class NumArray:
    def __init__(self, nums):
        n = len(nums)
        self.prefix = [0] * (n + 1)
        for i in range(n):
            self.prefix[i+1] = self.prefix[i] + nums[i]

    def sumRange(self, left, right):
        return self.prefix[right+1] - self.prefix[left]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums = [-2, 0, 3, -5, 2, -1]

Build prefix:
prefix[0]=0
prefix[1]=0+(-2)=-2
prefix[2]=-2+0=-2
prefix[3]=-2+3=1
prefix[4]=1+(-5)=-4
prefix[5]=-4+2=-2
prefix[6]=-2+(-1)=-3

sumRange(0,2) = prefix[3]-prefix[0] = 1-0 = 1 ✓
sumRange(2,5) = prefix[6]-prefix[2] = -3-(-2) = -1 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Prefix sums are a fundamental technique:
  Build once in O(n), answer each query in O(1).

The magic formula:
  sum(left, right) = prefix[right+1] - prefix[left]

Why right+1? prefix[i] = sum of first i elements.
So prefix[right+1] = sum of first (right+1) elements = sum up to and including right.

This pattern appears in 2D too: LC304 — 2D Range Sum Query.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

prefix = [0] * (n+1)
for i in range(n):
    prefix[i+1] = prefix[i] + nums[i]

def query(l, r): return prefix[r+1] - prefix[l]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Build: O(n)
Query: O(1)
Space: O(n)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Off-by-one: prefix has n+1 elements
2. forgetting prefix[0] = 0 (it's the "nothing" base)
3. Using prefix[right] - prefix[left-1] (works but messier for left=0)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC304 — Range Sum Query 2D
LC307 — Range Sum Query - Mutable (with updates, use Binary Indexed Tree)

---

*Next up: Problem 53 of 250*
