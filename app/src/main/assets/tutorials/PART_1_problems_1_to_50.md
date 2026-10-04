# 📚 DSA Learning Roadmap — Part 1 | Section 1/5
## Foundations (Problems 1-50): Arrays, HashMaps, Strings, Math

> Study these problems in ORDER. Each one builds on the previous.

---

## Problem 1/250 — LeetCode #1929: Concatenation of the Array

**Pattern:** `Array` | **Position in Roadmap:** #1

```
LeetCode 1929 — Concatenation of the Array
Problem #1 of 250 | Difficulty: Easy | Pattern: Array — Index Doubling

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given an integer array nums of length n, create and return an array ans of length 2n where ans[i]=nums[i] and ans[i+n]=nums[i] for all 0<=i<n. Simply double the array!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Array — Index Doubling

Two passes or list concatenation. ans = nums + nums in Python is one line!
  For each i in 0..n-1: ans[i] = nums[i] and ans[i+n] = nums[i]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def getConcatenation(nums):
    return nums + nums
    # Or: n=len(nums); return [nums[i%n] for i in range(2*n)]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums=[1,2,1]
ans=[1,2,1,1,2,1]
i=0: ans[0]=1, ans[3]=1
i=1: ans[1]=2, ans[4]=2
i=2: ans[2]=1, ans[5]=1 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Simplest array problem. Python list concatenation with + makes it a one-liner. Understand index arithmetic: ans[i+n] accesses the second half.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #1 (Concatenation of the Array):
  Can you apply the Array — Index Doubling pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?
```

---

## Problem 2/250 — LeetCode #1480: Running Sum of 1d Array

**Pattern:** `Array` | **Position in Roadmap:** #2

```
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
```

---

## Problem 3/250 — LeetCode #1470: Shuffle the Array

**Pattern:** `Array` | **Position in Roadmap:** #3

```
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
```

---

## Problem 4/250 — LeetCode #1431: Kids With the Greatest Number of Candies

**Pattern:** `Array` | **Position in Roadmap:** #4

```
LeetCode 1431 — Kids With the Greatest Number of Candies
Problem #4 of 250 | Difficulty: Easy | Pattern: Array — Max Comparison

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Each kid can have the greatest if their candies + extraCandies >= max(candies).
Precompute max_candy first. Then for each kid, check if kid+extra >= max_candy.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Array — Max Comparison

1. Find max_candy = max(candies)
2. For each kid: result[i] = (candies[i] + extraCandies >= max_candy)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def kidsWithCandies(candies, extraCandies):
    max_candy = max(candies)
    return [c + extraCandies >= max_candy for c in candies]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

candies=[2,3,5,1,3], extraCandies=3
max_candy=5
2+3=5>=5 True, 3+3=6>=5 True, 5+3=8>=5 True, 1+3=4>=5 False, 3+3=6>=5 True
Result: [True,True,True,False,True] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Compare each element to a precomputed max. List comprehension with boolean expressions is concise Python.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #4 (Kids With the Greatest Number of Candies):
  Can you apply the Array — Max Comparison pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?
```

---

## Problem 5/250 — LeetCode #1672: Richest Customer Wealth

**Pattern:** `Array` | **Position in Roadmap:** #5

```
LeetCode 1672 — Richest Customer Wealth
Problem #5 of 250 | Difficulty: Easy | Pattern: 2D Array — Row Sum Max

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

accounts[i][j] = money in bank j for customer i.
Compute sum of each row. Return the maximum row sum.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 2D Array — Row Sum Max

For each customer (row), compute their total wealth = sum(accounts[i]).
Track the maximum wealth seen.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def maximumWealth(accounts):
    return max(sum(row) for row in accounts)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

accounts=[[1,2,3],[3,2,1]]
Customer 0: 1+2+3=6
Customer 1: 3+2+1=6
max=6 ✓

accounts=[[1,5],[7,3],[3,5]]
Sums: 6, 10, 8 → max=10 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

2D array traversal: iterate rows, sum each. Python's sum() and max() with generator make it one line. Build intuition for 2D array access: accounts[row][col].

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #5 (Richest Customer Wealth):
  Can you apply the 2D Array — Row Sum Max pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?
```

---

## Problem 6/250 — LeetCode #1: Two Sum

**Pattern:** `Hash Map` | **Position in Roadmap:** #6

```
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
```

---

## Problem 7/250 — LeetCode #217: Contains Duplicate

**Pattern:** `Hash Set` | **Position in Roadmap:** #7

```
LeetCode 217 — Contains Duplicate
Problem #7 of 250 | Difficulty: Easy | Pattern: Hash Set — Seen Tracker

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given an integer array nums,
return true if any value appears AT LEAST TWICE.
Return false if every element is DISTINCT.

Example:
  nums = [1, 2, 3, 1] → true (1 appears twice)
  nums = [1, 2, 3, 4] → false (all unique)
  nums = [1, 1, 1, 3, 3, 4, 3, 2, 4, 2] → true

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

As you walk through the array, ask:
  "Have I seen this number before?"

  nums = [1, 2, 3, 1]

  Step | num | seen set | Have I seen it?
  ───────────────────────────────────────
    1  |  1  | {}       | No → add → {1}
    2  |  2  | {1}      | No → add → {1,2}
    3  |  3  | {1,2}    | No → add → {1,2,3}
    4  |  1  | {1,2,3}  | YES → return true!

A HashSet gives O(1) lookup for "have I seen this?".

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Imagine checking ID cards at the door:
  You keep a list of IDs you've already let in.
  If someone shows an ID already on the list → duplicate!
  If you finish without finding one → all unique.

That list is your HashSet.

Another way to think:
  Sort the array → duplicates will be adjacent
  [1,2,3,1] sorted = [1,1,2,3]
  Check: is any nums[i] == nums[i+1]? → yes → true

HashSet is O(n), sorting is O(n log n). HashSet is better!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

seen = set()

for num in nums:
    if num in seen:      # Already saw this!
        return True
    seen.add(num)        # Mark as seen

return False             # No duplicates found

One-liner (Pythonic):
  return len(nums) != len(set(nums))
  # If set is smaller than array → duplicates existed

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums = [4, 1, 2, 1, 2]

seen = {}

num=4: 4 in {} ? No → seen={4}
num=1: 1 in {4}? No → seen={4,1}
num=2: 2 in {4,1}? No → seen={4,1,2}
num=1: 1 in {4,1,2}? YES → return True ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

The "Seen Tracker" pattern:
  Use a set to remember what you've visited.
  At each step, check if current is already in the set.

Whenever you see:
  - "Does any value appear more than once?"
  - "Are all elements distinct?"
  - "Have I encountered this before?"
→ Think HashSet / seen tracker

The set gives O(1) lookup — much faster than searching a list O(n).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

seen = set()
for item in collection:
    if item in seen:
        # FOUND duplicate / revisited
        return True
    seen.add(item)
return False

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — one pass, O(1) per lookup
Space: O(n) — set stores up to n unique elements

Sorting approach: O(n log n) time, O(1) space
Pythonic: len(nums) != len(set(nums)) — clean but creates entire set first

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Using a list instead of set — list lookup is O(n), set is O(1)
2. Adding before checking — check first, then add
3. Comparing values vs indices — we want to know if the VALUE repeated

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC219 — Contains Duplicate II
  (duplicates must be within k indices of each other)

LC220 — Contains Duplicate III
  (values must be within t of each other and indices within k)
```

---

## Problem 8/250 — LeetCode #242: Valid Anagram

**Pattern:** `Hash Map` | **Position in Roadmap:** #8

```
LeetCode 242 — Valid Anagram
Problem #8 of 250 | Difficulty: Easy | Pattern: Frequency Count — HashMap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Two strings s and t are anagrams if t is a REARRANGEMENT of s.
They must have the same characters with the same frequencies.

Example:
  s = "anagram", t = "nagaram" → true
  (same letters: a×3, n×1, g×1, r×1, m×1)

Example:
  s = "rat", t = "car" → false
  (s has 't', t has 'c' — different characters)

Return true if t is an anagram of s, false otherwise.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Ask: "Do they have the exact same character frequencies?"

  s = "anagram"       t = "nagaram"
  Count s: {a:3, n:1, g:1, r:1, m:1}
  Count t: {n:1, a:3, g:1, r:1, m:1}
  Equal? YES → true

  s = "rat"           t = "car"
  Count s: {r:1, a:1, t:1}
  Count t: {c:1, a:1, r:1}
  Equal? NO (t has 't', c has 'c') → false

Trick: Use ONE counter — increment for s, decrement for t.
If all counters return to 0 → anagram!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Imagine two puzzles using the same pieces:
  Can you rearrange one puzzle's pieces to make the other?
  If yes → anagram.

To check programmatically:
  Count each letter in s
  Subtract each letter count for t
  If everything cancels to 0 → perfect rearrangement

Quick check: len(s) != len(t) → immediately false!
(Different lengths can't be anagrams)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Step 1 — Early exit:
  if len(s) != len(t):
      return False

Step 2 — Count frequencies:
  count = {}
  for c in s:
      count[c] = count.get(c, 0) + 1  # +1 for each char in s
  for c in t:
      count[c] = count.get(c, 0) - 1  # -1 for each char in t

Step 3 — Check all zeros:
  return all(v == 0 for v in count.values())

Or shortcut: if count goes negative, return False early:
  for c in t:
      count[c] = count.get(c, 0) - 1
      if count[c] < 0:   # t has more of this char than s
          return False

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

s = "rat", t = "car"

len(3) == len(3) → continue

Count s: r→{r:1}, a→{r:1,a:1}, t→{r:1,a:1,t:1}

Process t:
  c: count.get('c',0)-1 = -1 → return False ✓

Answer: False (correct — 'rat' and 'car' are NOT anagrams)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Frequency counting is fundamental in string problems.

The one-map trick:
  +1 for each character in s
  -1 for each character in t
  If all values = 0 → perfect balance → anagram

Alternatively: sorted(s) == sorted(t)
  But this is O(n log n) vs O(n) for the counter approach.

Whenever you see:
  - "Is X a rearrangement of Y?"
  - "Do they have the same characters?"
→ Frequency counter / HashMap pattern

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

if len(s) != len(t): return False

count = {}
for c in s: count[c] = count.get(c, 0) + 1
for c in t: count[c] = count.get(c, 0) - 1

return all(v == 0 for v in count.values())

# Or use Counter:
from collections import Counter
return Counter(s) == Counter(t)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — two passes of length n
Space: O(1) — at most 26 letters in counter (fixed alphabet)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not checking length early (saves time)
2. Forgetting to check negative values (t has char not in s)
3. Using sorted() — works but O(n log n)
4. Case sensitivity — "Anagram" vs "anagram" are different!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC49 — Group Anagrams (group all anagram strings from a list)
LC438 — Find All Anagrams in a String (sliding window + anagram check)
```

---

## Problem 9/250 — LeetCode #136: Single Number

**Pattern:** `Bit Manipulation` | **Position in Roadmap:** #9

```
LeetCode 136 — Single Number
Problem #9 of 250 | Difficulty: Easy | Pattern: Bit Manipulation — XOR

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Every number except one appears exactly twice. XOR of same numbers = 0. XOR with 0 gives the number.
So XOR all elements → pairs cancel to 0 → only the unique number remains!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Bit Manipulation — XOR

result = 0
For each num: result ^= num
At end, result = the single number.

Why? XOR is associative and commutative.
  a^a=0, a^0=a
  So all pairs cancel: (a^a)^(b^b)^...^x = 0^0^...^x = x

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def singleNumber(nums):
    result = 0
    for num in nums:
        result ^= num
    return result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums=[4,1,2,1,2]
result=0
^4=4, ^1=5, ^2=7, ^1=6 (5 XOR 1=4? no: 5=101, 1=001 XOR=100=4 ✓), ^2=6 (4=100, 2=010 XOR=110=6)
Wait: 0^4=4, 4^1=5, 5^2=7, 7^1=6, 6^2=4 ✓ (4 is single number)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

XOR is the perfect tool when pairs must cancel. x^x=0, x^0=x. No extra space needed! O(1) space, O(n) time.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #9 (Single Number):
  Can you apply the Bit Manipulation — XOR pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?
```

---

## Problem 10/250 — LeetCode #349: Intersection of Two Arrays

**Pattern:** `Hash Set` | **Position in Roadmap:** #10

```
LeetCode 349 — Intersection of Two Arrays
Problem #10 of 250 | Difficulty: Easy | Pattern: Hash Set — Intersection

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given two integer arrays nums1 and nums2,
return their intersection — elements that appear in BOTH.
Each element in result must be UNIQUE.

Example:
  nums1 = [1, 2, 2, 1], nums2 = [2, 2]
  → [2]  (2 is in both, but only once in result)

Example:
  nums1 = [4, 9, 5], nums2 = [9, 4, 9, 8, 4]
  → [9, 4]  (9 and 4 are in both)

No duplicates in the result — it's a SET intersection.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Convert nums1 to a set. Check each element of nums2.

  nums1 = [4, 9, 5]  →  set1 = {4, 9, 5}

  For nums2 = [9, 4, 9, 8, 4]:
  num | in set1? | result
  ─────────────────────────
   9  |   YES    | {9}
   4  |   YES    | {9, 4}
   9  |   YES    | {9, 4}  (already in result, no change)
   8  |   NO     | {9, 4}
   4  |   YES    | {9, 4}  (already in result)

  Return list({9, 4}) = [9, 4]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Find what's common between two groups.

Like: "Which students are in BOTH Math class AND Science class?"
  Math: {Alice, Bob, Carol, Dave}
  Science: {Bob, Eve, Carol}
  Answer: {Bob, Carol} — they're in both!

In code:
  Convert one array to a set (for fast lookup)
  Check each element of the other array against that set
  Collect matches (using a result set to avoid duplicates)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

set1 = set(nums1)    # O(1) lookup for nums1 elements
result = set()       # Automatically handles uniqueness

for num in nums2:
    if num in set1:
        result.add(num)

return list(result)

# Python one-liner:
return list(set(nums1) & set(nums2))
# & is the set intersection operator!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums1 = [1, 2, 2, 1], nums2 = [2, 2]

set1 = {1, 2}
result = {}

num=2: 2 in {1,2}? YES → result={2}
num=2: 2 in {1,2}? YES → already in result → result={2}

Return: [2] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Set intersection finds common elements efficiently.

Key insight: Converting to a set gives O(1) lookup.
Result set ensures NO DUPLICATES in the output.

Python's & operator is literally set intersection:
  {1,2,3} & {2,3,4} = {2,3}

Compare with LC350 (Intersection of Two Arrays II):
  That one KEEPS DUPLICATES based on frequency.
  This one (LC349) returns unique elements only.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

set1 = set(nums1)
result = set()
for num in nums2:
    if num in set1:
        result.add(num)
return list(result)

# Or simply:
return list(set(nums1) & set(nums2))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(m + n) — build set1 O(m), scan nums2 O(n)
Space: O(m) — set1 stores all unique elements of nums1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Using a list for result (then duplicates can sneak in)
2. Using list search O(n) instead of set lookup O(1)
3. Confusing with LC350 which keeps duplicates

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC350 — Intersection of Two Arrays II
  Now count matters! [2,2] ∩ [2,2,3] = [2,2] not [2]
  Use a HashMap/Counter instead of a Set
```

---

## Problem 11/250 — LeetCode #350: Intersection of Two Arrays II

**Pattern:** `Hash Map` | **Position in Roadmap:** #11

```
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
```

---

## Problem 12/250 — LeetCode #121: Best Time to Buy and Sell Stock

**Pattern:** `Array / Greedy` | **Position in Roadmap:** #12

```
LeetCode 121 — Best Time to Buy and Sell Stock
Problem #12 of 250 | Difficulty: Easy | Pattern: Array — Track Running Min

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Find max profit from one buy-sell. Buy at minimum price, sell at maximum after buy.
As you scan, track the minimum price seen so far. Profit = current_price - min_price.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Array — Track Running Min

min_price = infinity, max_profit = 0
For each price:
  profit = price - min_price
  max_profit = max(max_profit, profit)
  min_price = min(min_price, price)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def maxProfit(prices):
    min_price = float('inf')
    max_profit = 0
    for price in prices:
        max_profit = max(max_profit, price - min_price)
        min_price = min(min_price, price)
    return max_profit

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

prices=[7,1,5,3,6,4]
min=inf,profit=0
7: profit=-inf→0 (max=0), min=7
1: profit=-6→0 (max=0), min=1
5: profit=4 (max=4), min=1
3: profit=2 (max=4), min=1
6: profit=5 (max=5), min=1
4: profit=3 (max=5), min=1
Return 5 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Track running minimum. At each point, the best profit is current_price - running_minimum. Update both the min and the max_profit in one pass.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #12 (Best Time to Buy and Sell Stock):
  Can you apply the Array — Track Running Min pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?
```

---

## Problem 13/250 — LeetCode #122: Best Time to Buy and Sell Stock II

**Pattern:** `Greedy` | **Position in Roadmap:** #13

```
LeetCode 122 — Best Time to Buy and Sell Stock II
Problem #13 of 250 | Difficulty: Easy | Pattern: Greedy — Capture All Gains

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Can buy and sell multiple times. Key insight: capture EVERY upward move!
If prices[i+1] > prices[i], add the difference (buy on day i, sell on day i+1).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Greedy — Capture All Gains

Sum all positive differences between consecutive days.
profit += max(0, prices[i+1] - prices[i]) for each i.
This is equivalent to capturing every ascending sequence.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def maxProfit(prices):
    profit = 0
    for i in range(1, len(prices)):
        if prices[i] > prices[i-1]:
            profit += prices[i] - prices[i-1]
    return profit

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

prices=[7,1,5,3,6,4]
7→1: negative, skip
1→5: +4, profit=4
5→3: negative, skip
3→6: +3, profit=7
6→4: negative, skip
Return 7 ✓ (buy at 1, sell at 5; buy at 3, sell at 6)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Greedy: capture every profitable day transition. No need to find best buy/sell windows — just sum all gains. Elegant and provably optimal.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #13 (Best Time to Buy and Sell Stock II):
  Can you apply the Greedy — Capture All Gains pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?
```

---

## Problem 14/250 — LeetCode #88: Merge Sorted Array

**Pattern:** `Two Pointers` | **Position in Roadmap:** #14

```
LeetCode 88 — Merge Sorted Array
Problem #14 of 250 | Difficulty: Easy | Pattern: Two Pointers — Merge from Back

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

You have two sorted arrays: nums1 and nums2.
Merge nums2 into nums1 as one sorted array.

nums1 has extra space at the end (m + n total length).
m = actual elements in nums1, n = elements in nums2.

Example:
  nums1 = [1,2,3,0,0,0], m=3
  nums2 = [2,5,6],       n=3
  Result: [1,2,2,3,5,6] (merged in nums1)

The 0s in nums1 are placeholder spaces.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Naive approach: merge from front → shifting required (O(mn))

Smart approach: MERGE FROM THE BACK!
  - The largest element goes to position m+n-1
  - Compare nums1[m-1] and nums2[n-1], place larger at end
  - Work backwards

  nums1 = [1,2,3,0,0,0] m=3    nums2 = [2,5,6] n=3

  p1=2, p2=2, pos=5
  nums1[2]=3 vs nums2[2]=6: 6 > 3 → nums1[5]=6, p2=1, pos=4
  nums1[2]=3 vs nums2[1]=5: 5 > 3 → nums1[4]=5, p2=0, pos=3
  nums1[2]=3 vs nums2[0]=2: 3 > 2 → nums1[3]=3, p1=1, pos=2
  ...

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Imagine filling a trophy shelf from the back:
  You have two lists of scores, both sorted.
  The LARGEST score goes to the rightmost spot.
  Work left, always placing the current winner.

No shifting needed! We fill into the empty slots (zeros) first.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

p1 = m - 1        # pointer into nums1's actual elements
p2 = n - 1        # pointer into nums2
pos = m + n - 1   # where to place next element (start at end)

while p1 >= 0 and p2 >= 0:
    if nums1[p1] > nums2[p2]:
        nums1[pos] = nums1[p1]
        p1 -= 1
    else:
        nums1[pos] = nums2[p2]
        p2 -= 1
    pos -= 1

# If nums2 has remaining elements:
while p2 >= 0:
    nums1[pos] = nums2[p2]
    p2 -= 1
    pos -= 1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums1=[1,2,3,0,0,0] m=3, nums2=[2,5,6] n=3

p1=2, p2=2, pos=5

nums1[2]=3 vs nums2[2]=6: 6>3 → nums1[5]=6, p2=1, pos=4 → [1,2,3,0,0,6]
nums1[2]=3 vs nums2[1]=5: 5>3 → nums1[4]=5, p2=0, pos=3 → [1,2,3,0,5,6]
nums1[2]=3 vs nums2[0]=2: 3>2 → nums1[3]=3, p1=1, pos=2 → [1,2,3,3,5,6]
nums1[1]=2 vs nums2[0]=2: 2==2 → nums1[2]=2, p2=-1, pos=1 → [1,2,2,3,5,6]

p2 < 0, stop. nums1 = [1,2,2,3,5,6] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Merging from back avoids shifting elements:
  If merging from front, placing small element requires shifting everything.
  If merging from back, we write into empty space directly.

This "fill from back" trick is key in merge operations:
  Works because nums1 already has the extra space at the end.

After the loop, if p2 still has elements,
copy remaining nums2 — they're all smaller than anything in nums1!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

p1, p2, pos = m-1, n-1, m+n-1
while p1 >= 0 and p2 >= 0:
    if nums1[p1] >= nums2[p2]:
        nums1[pos] = nums1[p1]; p1 -= 1
    else:
        nums1[pos] = nums2[p2]; p2 -= 1
    pos -= 1
# Copy remaining nums2:
nums1[:p2+1] = nums2[:p2+1]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(m+n) — each element placed exactly once
Space: O(1) — in-place merge

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Merging from front (causes O(m×n) shifting)
2. Forgetting to handle remaining nums2 elements
3. Off-by-one with pointers (m-1, n-1, not m, n)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC21 — Merge Two Sorted Lists (same idea but with linked lists)
LC912 — Sort an Array (merge sort uses this merge step)
```

---

## Problem 15/250 — LeetCode #27: Remove Element

**Pattern:** `Two Pointers` | **Position in Roadmap:** #15

```
LeetCode 27 — Remove Element
Problem #15 of 250 | Difficulty: Easy | Pattern: Two Pointers — Write Head

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given an array nums and a value val,
remove all occurrences of val in-place.
Return the count k of remaining elements.

Example:
  nums = [3,2,2,3], val = 3
  Remove both 3s → nums = [2,2,_,_], return 2

Example:
  nums = [0,1,2,2,3,0,4,2], val = 2
  Remove all 2s → nums = [0,1,3,0,4,_,_,_], return 5

The first k elements of nums should hold the non-val elements.
The rest doesn't matter.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Two pointers: slow (write position) reads for non-val, fast scans all.

  nums = [3, 2, 2, 3], val = 3

  fast | nums[fast] | == val? | slow | Action
  ──────────────────────────────────────────────
    0  |     3      |   YES   |  0   | Skip (don't write)
    1  |     2      |   NO    |  0   | Write nums[0]=2, slow=1
    2  |     2      |   NO    |  1   | Write nums[1]=2, slow=2
    3  |     3      |   YES   |  2   | Skip

  Return slow=2, nums[0..1] = [2,2]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Imagine copying a book but SKIPPING certain words (val).
  - fast reads every word
  - slow marks where to write the next "keep" word
  - When fast finds a "remove" word, slow stays put

Only write when we encounter something we want to keep.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

slow = 0

for fast in range(len(nums)):
    if nums[fast] != val:     # This element is NOT val — keep it!
        nums[slow] = nums[fast]
        slow += 1

return slow   # slow = count of remaining elements

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums = [0,1,2,2,3,0,4,2], val = 2

slow = 0

fast=0: nums[0]=0 ≠ 2 → nums[0]=0, slow=1
fast=1: nums[1]=1 ≠ 2 → nums[1]=1, slow=2
fast=2: nums[2]=2 == 2 → skip
fast=3: nums[3]=2 == 2 → skip
fast=4: nums[4]=3 ≠ 2 → nums[2]=3, slow=3
fast=5: nums[5]=0 ≠ 2 → nums[3]=0, slow=4
fast=6: nums[6]=4 ≠ 2 → nums[4]=4, slow=5
fast=7: nums[7]=2 == 2 → skip

Return 5, nums = [0,1,3,0,4,_,_,_] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

The write-head pattern:
  slow only moves when we find something worth keeping.
  Effectively "compacts" the array by removing unwanted elements.

Almost identical to LC26 and LC283 — same two-pointer pattern
just with different keep conditions:
  LC26: keep if different from previous
  LC283: keep if non-zero
  LC27: keep if != val

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

slow = 0
for fast in range(len(nums)):
    if nums[fast] != val:   # Change condition as needed
        nums[slow] = nums[fast]
        slow += 1
return slow

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — one pass
Space: O(1) — in-place

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Using nums.remove(val) — that's O(n) per call, O(n²) total
2. Decrementing length tracking incorrectly
3. Returning fast instead of slow

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

What if you also need to return the modified array (not just count)?
Compare your solution to LC26 and LC283.
Notice the SAME template just with different filter conditions.
```

---

## Problem 16/250 — LeetCode #26: Remove Duplicates from Sorted Array

**Pattern:** `Two Pointers` | **Position in Roadmap:** #16

```
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
```

---

## Problem 17/250 — LeetCode #283: Move Zeroes

**Pattern:** `Two Pointers` | **Position in Roadmap:** #17

```
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
```

---

## Problem 18/250 — LeetCode #125: Valid Palindrome

**Pattern:** `Two Pointers` | **Position in Roadmap:** #18

```
LeetCode 125 — Valid Palindrome
Problem #18 of 250 | Difficulty: Easy | Pattern: Two Pointers — Converging

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After cleaning (lowercase, alphanumeric only), check if string reads same forwards and backwards.
Two pointers from both ends: compare characters until pointers meet.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Two Pointers — Converging

left=0, right=len(s)-1
Skip non-alphanumeric characters.
Compare s[left].lower() with s[right].lower().
If mismatch → not palindrome.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def isPalindrome(s):
    left, right = 0, len(s)-1
    while left < right:
        while left < right and not s[left].isalnum(): left += 1
        while left < right and not s[right].isalnum(): right -= 1
        if s[left].lower() != s[right].lower(): return False
        left += 1; right -= 1
    return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

s='A man, a plan, a canal: Panama'
After alnum filter: 'amanaplanacanalpanama'
left=A, right=a: a==a ✓
left=m, right=m: ✓
...all match → True ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Two-pointer palindrome check: converge from both ends. The key: skip non-alphanumeric and compare case-insensitively.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #18 (Valid Palindrome):
  Can you apply the Two Pointers — Converging pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?
```

---

## Problem 19/250 — LeetCode #344: Reverse String

**Pattern:** `Two Pointers` | **Position in Roadmap:** #19

```
LeetCode 344 — Reverse String
Problem #19 of 250 | Difficulty: Easy | Pattern: Two Pointers — Swap Ends

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Reverse in-place: swap character at index i with character at index n-1-i.
Two pointers from ends converging to middle.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Two Pointers — Swap Ends

left=0, right=len(s)-1
While left<right: swap s[left] and s[right], left++, right--

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def reverseString(s):
    left, right = 0, len(s)-1
    while left < right:
        s[left], s[right] = s[right], s[left]
        left += 1
        right -= 1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

s=['h','e','l','l','o']
left=0,right=4: swap h,o → ['o','e','l','l','h']
left=1,right=3: swap e,l → ['o','l','l','e','h']
left=2,right=2: left>=right, stop
Result: ['o','l','l','e','h'] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Two-pointer swapping from ends. This is the building block for array reversal, palindrome checking, and many other problems.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #19 (Reverse String):
  Can you apply the Two Pointers — Swap Ends pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?
```

---

## Problem 20/250 — LeetCode #167: Two Sum II – Input Array Is Sorted

**Pattern:** `Two Pointers` | **Position in Roadmap:** #20

```
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
```

---

## Problem 21/250 — LeetCode #15: 3Sum

**Pattern:** `Two Pointers` | **Position in Roadmap:** #21

```
LeetCode 15 — 3Sum
Problem #21 of 250 | Difficulty: Medium | Pattern: Two Pointers — Fix One, Search Two

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Find all triplets summing to 0. Fix one element (sorted array), use two pointers for the remaining two.
Sort first. Skip duplicates carefully.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Two Pointers — Fix One, Search Two

Sort nums. For i in 0..n-3:
  Skip if nums[i]==nums[i-1] (duplicate)
  left=i+1, right=n-1
  Two-pointer search for pair summing to -nums[i]
  On match: add to result, skip duplicates for both pointers

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def threeSum(nums):
    nums.sort()
    result = []
    for i in range(len(nums)-2):
        if i > 0 and nums[i] == nums[i-1]: continue
        left, right = i+1, len(nums)-1
        while left < right:
            total = nums[i]+nums[left]+nums[right]
            if total == 0:
                result.append([nums[i],nums[left],nums[right]])
                while left<right and nums[left]==nums[left+1]: left+=1
                while left<right and nums[right]==nums[right-1]: right-=1
                left+=1; right-=1
            elif total<0: left+=1
            else: right-=1
    return result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums=[-1,0,1,2,-1,-4] sorted: [-4,-1,-1,0,1,2]
i=0 (-4): left=1,right=5: -4-1+2=-3<0→left++: -4-1+2=-3<0→left++: -4+0+2=-2<0→left++: -4+1+2=-1<0→left++: done
i=1 (-1): left=2,right=5: -1-1+2=0 → add [-1,-1,2]! skip dups, left=3,right=4: -1+0+1=0 → add [-1,0,1]!
i=2: nums[2]==nums[1], skip
...
Result: [[-1,-1,2],[-1,0,1]] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

3Sum = Sort + Fix + Two Pointers. Deduplication by skipping repeated values. This pattern extends to 4Sum (fix two, search two).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #21 (3Sum):
  Can you apply the Two Pointers — Fix One, Search Two pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?
```

---

## Problem 22/250 — LeetCode #18: 4Sum

**Pattern:** `Two Pointers` | **Position in Roadmap:** #22

```
LeetCode 18 — 4Sum
Problem #22 of 250 | Difficulty: Medium | Pattern: Two Pointers — Fix Two, Search Two

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Extend 3Sum: fix two elements (outer and inner loops), use two pointers for the remaining two.
Sort first. Careful deduplication at both outer loops.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Two Pointers — Fix Two, Search Two

Sort. For i: for j=i+1: left=j+1, right=n-1. Two-pointer search for pair summing to target-nums[i]-nums[j].
Skip duplicates at i, j, and during two-pointer convergence.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def fourSum(nums, target):
    nums.sort(); result = []
    for i in range(len(nums)-3):
        if i>0 and nums[i]==nums[i-1]: continue
        for j in range(i+1, len(nums)-2):
            if j>i+1 and nums[j]==nums[j-1]: continue
            left,right = j+1,len(nums)-1
            while left<right:
                s = nums[i]+nums[j]+nums[left]+nums[right]
                if s==target:
                    result.append([nums[i],nums[j],nums[left],nums[right]])
                    while left<right and nums[left]==nums[left+1]: left+=1
                    while left<right and nums[right]==nums[right-1]: right-=1
                    left+=1; right-=1
                elif s<target: left+=1
                else: right-=1
    return result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums=[1,0,-1,0,-2,2], target=0
Sorted: [-2,-1,0,0,1,2]
i=0(-2),j=1(-1): look for 3 in [0,0,1,2]
  left=0,right=2: -2-1+0+2=-1<0→left++
  left=1,right=2: -2-1+0+1... this gets complex. Trust the pattern!
Result: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

4Sum extends 3Sum with one more fixed element. Time: O(n³). This pattern: Sort + (n-2 fixed) + two pointers.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #22 (4Sum):
  Can you apply the Two Pointers — Fix Two, Search Two pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?
```

---

## Problem 23/250 — LeetCode #977: Squares of a Sorted Array

**Pattern:** `Two Pointers` | **Position in Roadmap:** #23

```
LeetCode 977 — Squares of a Sorted Array
Problem #23 of 250 | Difficulty: Easy | Pattern: Two Pointers — Fill from Ends

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Array is sorted (may have negatives). Square of largest absolute value could be at either end.
Two pointers from both ends, fill result from BACK (largest first).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Two Pointers — Fill from Ends

left=0, right=n-1, pos=n-1
Compare |nums[left]| and |nums[right]|.
Whichever is larger, square it and put at result[pos], advance that pointer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def sortedSquares(nums):
    n = len(nums)
    result = [0] * n
    left, right = 0, n-1
    pos = n-1
    while left <= right:
        if abs(nums[left]) > abs(nums[right]):
            result[pos] = nums[left]**2; left += 1
        else:
            result[pos] = nums[right]**2; right -= 1
        pos -= 1
    return result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums=[-4,-1,0,3,10]
left=0,right=4: |-4|=4, |10|=10: 10>4 → result[4]=100, right=3
left=0,right=3: 4 vs 3: 4>3 → result[3]=16, left=1
left=1,right=3: 1 vs 3: 3>1 → result[2]=9, right=2
left=1,right=2: 1 vs 0: 1>0 → result[1]=1, left=2
left=2,right=2: 0 → result[0]=0
Return [0,1,9,16,100] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Fill from back when you know the largest element but not the order of smaller ones. Two pointers from ends, place the winner at the back.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #23 (Squares of a Sorted Array):
  Can you apply the Two Pointers — Fill from Ends pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?
```

---

## Problem 24/250 — LeetCode #11: Container With Most Water

**Pattern:** `Two Pointers` | **Position in Roadmap:** #24

```
LeetCode 11 — Container With Most Water
Problem #24 of 250 | Difficulty: Medium | Pattern: Two Pointers — Greedy Convergence

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Find two lines that form the largest container with the x-axis.
Area = min(height[left], height[right]) * (right - left).
Two pointers from ends: move the shorter line inward (it can only get worse if we keep it).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Two Pointers — Greedy Convergence

left=0, right=n-1, max_water=0
While left<right:
  water = min(height[left], height[right]) * (right-left)
  max_water = max(max_water, water)
  If height[left] < height[right]: left++ (move shorter line)
  Else: right--

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def maxArea(height):
    left, right = 0, len(height)-1
    max_water = 0
    while left < right:
        max_water = max(max_water, min(height[left],height[right])*(right-left))
        if height[left] < height[right]:
            left += 1
        else:
            right -= 1
    return max_water

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

height=[1,8,6,2,5,4,8,3,7]
left=0,right=8: min(1,7)*8=8, move left (1<7)
left=1,right=8: min(8,7)*7=49, move right (8>7)
left=1,right=7: min(8,3)*6=18, move right
...eventually max=49 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Greedy two-pointer: always move the shorter line. Why? Moving the taller line can only decrease or maintain the area (width decreases, height limited by the shorter). Moving the shorter MIGHT find a taller line → chance of improvement.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #24 (Container With Most Water):
  Can you apply the Two Pointers — Greedy Convergence pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?
```

---

## Problem 25/250 — LeetCode #141: Linked List Cycle

**Pattern:** `Fast & Slow Pointers` | **Position in Roadmap:** #25

```
LeetCode 141 — Linked List Cycle
Problem #25 of 250 | Difficulty: Easy | Pattern: Fast & Slow Pointers — Floyd's Cycle Detection

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given the head of a linked list, determine if it has a cycle.
A cycle means some node's next pointer points back to a previous node.

Example:
  3 → 2 → 0 → -4 → (back to 2) → true

Example:
  1 → 2 → (back to 1) → true

Example:
  1 → 2 → null → false

Return true if cycle exists, false if list terminates at null.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Floyd's Tortoise & Hare Algorithm:
  Slow pointer moves 1 step at a time.
  Fast pointer moves 2 steps at a time.

  If NO cycle: fast reaches null first
  If cycle: fast "laps" slow — they MUST meet!

  Imagine two runners on a circular track:
  The faster runner will always lap the slower one.
  They WILL meet eventually if the track is circular.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Two runners on a track:
  Slow jogs 1 lap/hour
  Fast sprints 2 laps/hour

If it's a straight track → fast finishes first, no meeting.
If it's circular → fast eventually comes around and meets slow.

In code:
  slow = head, fast = head
  Each step: slow goes 1, fast goes 2
  If fast hits null → no cycle
  If slow == fast → cycle detected!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

slow = head
fast = head

while fast and fast.next:    # fast must have room for 2 steps
    slow = slow.next         # tortoise: 1 step
    fast = fast.next.next    # hare: 2 steps

    if slow == fast:         # they met → cycle!
        return True

return False                 # fast reached end → no cycle

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

List: 3→2→0→-4→2(cycle back)

slow=3, fast=3

Step 1: slow=2, fast=0
Step 2: slow=0, fast=2  (fast: 0→-4→2)
Step 3: slow=-4, fast=-4 → slow==fast → return True ✓

No cycle example: 1→2→null
Step 1: slow=2, fast=null → while condition fails → return False ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Floyd's algorithm: the most elegant cycle detection.

Why fast catches slow:
  In the cycle of length L:
  Each step, fast gains 1 on slow (moves 2, slow moves 1)
  After L steps, fast has lapped slow → they meet!

Important: check AFTER moving (not before):
  At start, slow==fast==head (but that's before any movement)
  We want to find a meeting DURING traversal.

O(1) space! No HashSet needed (HashSet works but wastes space).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

slow = fast = head
while fast and fast.next:
    slow = slow.next
    fast = fast.next.next
    if slow == fast:
        return True
return False

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — at most n+L steps where L is cycle length
Space: O(1) — two pointer variables

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Checking slow==fast BEFORE moving (they always equal at start!)
2. Not checking fast.next before fast.next.next
3. Using a HashSet (O(n) space, defeats the purpose)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC142 — Linked List Cycle II
  Find WHERE the cycle BEGINS, not just if it exists.
  Math hint: when slow and fast meet, reset one to head,
  move both 1 step at a time — they'll meet at the cycle start!
```

---

## Problem 26/250 — LeetCode #876: Middle of the Linked List

**Pattern:** `Fast & Slow Pointers` | **Position in Roadmap:** #26

```
LeetCode 876 — Middle of the Linked List
Problem #26 of 250 | Difficulty: Easy | Pattern: Fast & Slow Pointers — Speed Difference

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Find the middle node of a singly linked list.
If two middles (even length), return the SECOND middle.

Example:
  1→2→3→4→5 → return node 3 (5 nodes, index 2)
  1→2→3→4→5→6 → return node 4 (6 nodes, second middle)

You could count length then walk to middle,
but there's a clever one-pass approach!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Fast/slow pointer with 2:1 speed ratio.

When fast reaches the end, slow is at the middle!

  1→2→3→4→5 (5 nodes)

  Start: slow=1, fast=1
  Step 1: slow=2, fast=3
  Step 2: slow=3, fast=5
  fast.next = null → stop
  Return slow = node 3 ✓

  1→2→3→4→5→6 (6 nodes)

  Start: slow=1, fast=1
  Step 1: slow=2, fast=3
  Step 2: slow=3, fast=5
  Step 3: slow=4, fast=null → stop
  Return slow = node 4 ✓ (second middle!)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Two cars start at the beginning of a road:
  Car A drives at 1 km/h
  Car B drives at 2 km/h

When Car B reaches the end, Car A is at the halfway point.
That's exactly where we want slow to be!

The 2:1 ratio guarantees this works.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

slow = head
fast = head

while fast and fast.next:
    slow = slow.next        # 1 step
    fast = fast.next.next   # 2 steps

return slow   # slow is now at the middle

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

List: 1→2→3→4→5→6

slow=1, fast=1

Iteration 1: slow=2, fast=3
Iteration 2: slow=3, fast=5
Iteration 3: slow=4, fast=None (5.next=6, 6.next=None... wait)

Actually: fast=5, fast.next=6, so:
Iteration 3: slow=4, fast=6→None=None... 
fast.next = None → while exits
Return slow = node 4 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

The 2:1 speed ratio creates a "natural midpoint detector."
When fast doubles the distance, slow reaches exactly half.

For even-length lists, returning the SECOND middle:
  "while fast and fast.next" → fast ends at the LAST node
  → slow lands on the 2nd middle

For FIRST middle with even list:
  Use "while fast.next and fast.next.next"
  → fast ends at 2nd-to-last → slow lands on 1st middle

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

slow = fast = head
while fast and fast.next:
    slow = slow.next
    fast = fast.next.next
return slow

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — n/2 iterations
Space: O(1) — two pointers

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Returning fast instead of slow
2. Wrong loop condition (causes null pointer)
3. Not understanding which "middle" is returned for even lengths

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Using this, solve LC234 — Palindrome Linked List:
  Find middle → reverse second half → compare with first half
Can you piece this together?
```

---

## Problem 27/250 — LeetCode #19: Remove Nth Node From End of List

**Pattern:** `Two Pointers` | **Position in Roadmap:** #27

```
LeetCode 19 — Remove Nth Node From End of List
Problem #27 of 250 | Difficulty: Medium | Pattern: Two Pointers — Fixed Gap of N

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given head of linked list and integer n,
remove the nth node from the END and return head.

Example:
  1→2→3→4→5, n=2
  Remove 4th node (2nd from end = node with value 4)
  → 1→2→3→5

The challenge: We don't know the length!
How do we find the nth from end in ONE PASS?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Create a gap of exactly n nodes between two pointers.

  1→2→3→4→5, n=2

  Use dummy node: D→1→2→3→4→5
  slow=D, fast=D

  Move fast n=2 steps: fast=2
  Now gap between slow and fast = 2

  Move both until fast.next is null:
  slow=1, fast=3
  slow=2, fast=4
  slow=3, fast=5 (fast.next=null → stop)

  slow is at 3, slow.next is 4 (the target!)
  slow.next = slow.next.next = 5

  Result: D→1→2→3→5

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Imagine two people walking the same path, but one starts n steps ahead.
When the leader reaches the end, the follower is exactly n steps from the end.

Dummy node trick: Add a fake node before head.
This handles the edge case of removing the head node gracefully.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

dummy = ListNode(0)
dummy.next = head
slow = dummy
fast = dummy

# Move fast n steps ahead
for _ in range(n):
    fast = fast.next

# Move both until fast reaches last node
while fast.next:
    slow = slow.next
    fast = fast.next

# Delete the nth node from end
slow.next = slow.next.next

return dummy.next

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1→2→3→4→5, n=2

dummy→1→2→3→4→5
slow=dummy, fast=dummy

Move fast 2 steps: fast=node(2)

Move both until fast.next=null:
  slow=1, fast=3
  slow=2, fast=4
  slow=3, fast=5 (5.next=null → stop)

slow.next = 4, slow.next.next = 5
slow.next = 5

List: dummy→1→2→3→5
Return dummy.next = node(1) → 1→2→3→5 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

The N-gap pointer technique:
  Create exactly n nodes gap between slow and fast.
  When fast hits the end, slow is at the predecessor of the target.

Why a dummy node?
  If we're deleting the head (n == list length),
  slow would need to be at position -1.
  The dummy node represents that -1 position!

This one-pass O(n) solution is elegant.
The two-pass alternative: count length, then walk to (length-n)th node.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

dummy = ListNode(0, head)
slow = fast = dummy
for _ in range(n): fast = fast.next
while fast.next:
    slow = slow.next
    fast = fast.next
slow.next = slow.next.next
return dummy.next

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(L) — one pass, L is list length
Space: O(1) — dummy node + two pointers

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not using dummy node (breaks when deleting head)
2. Moving fast n+1 times instead of n (off-by-one)
3. Stopping at fast==null instead of fast.next==null

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

What if n could be invalid (larger than list length)?
What if the list is empty?
LC21 — Merge Two Sorted Lists (similar linked list manipulation)
```

---

## Problem 28/250 — LeetCode #206: Reverse Linked List

**Pattern:** `Linked List` | **Position in Roadmap:** #28

```
LeetCode 206 — Reverse Linked List
Problem #28 of 250 | Difficulty: Easy | Pattern: Linked List — Three-Pointer Reversal

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given the head of a singly linked list, reverse it in-place.
Return the new head.

Example:
  1→2→3→4→5 → 5→4→3→2→1

You need to flip every arrow's direction!
Original: each node's .next points FORWARD
After: each node's .next points BACKWARD

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Use three pointers: prev, curr, next_node

  1 → 2 → 3 → null
  prev=null, curr=1

  Step 1: save next=2, flip 1.next=null, prev=1, curr=2
    null ← 1   2 → 3

  Step 2: save next=3, flip 2.next=1, prev=2, curr=3
    null ← 1 ← 2   3

  Step 3: save next=null, flip 3.next=2, prev=3, curr=null
    null ← 1 ← 2 ← 3

  curr=null → stop. New head = prev = 3

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think of it like reversing direction on a one-way street:
  Each car (node) needs to U-turn and point the other way.

  prev = null (the new tail points to null)
  curr = head (start reversing from here)

For each node:
  1. Remember where next is (or we lose it when we reverse!)
  2. Flip: curr.next = prev (U-turn!)
  3. Advance: prev = curr, curr = next_node

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

prev = None
curr = head

while curr:
    next_node = curr.next   # 1. Save next before overwriting
    curr.next = prev        # 2. Reverse the pointer
    prev = curr             # 3. Advance prev
    curr = next_node        # 4. Advance curr

return prev   # prev is now the new head (last node of original list)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1→2→3→null

prev=None, curr=1

Iter 1: next=2, 1.next=None, prev=1, curr=2
  null←1   2→3

Iter 2: next=3, 2.next=1, prev=2, curr=3
  null←1←2   3

Iter 3: next=null, 3.next=2, prev=3, curr=null
  null←1←2←3

curr=None → stop
Return prev = node(3)

Reversed: 3→2→1→null ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

The three-pointer dance is the core of linked list reversal:

  Save next FIRST (before you destroy the pointer!)
  Then reverse
  Then advance both

This exact same idea appears in:
  - Reversing a portion of a linked list (LC92)
  - Reorder List (LC143)
  - Palindrome Linked List (LC234)

Master this and you master most linked list problems.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

prev, curr = None, head
while curr:
    next_node = curr.next   # save
    curr.next = prev        # reverse
    prev = curr             # advance prev
    curr = next_node        # advance curr
return prev

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — visit each node exactly once
Space: O(1) — three pointer variables

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Forgetting to save next_node BEFORE reversing
   (After curr.next=prev, you've lost the link to the rest!)
2. Returning curr instead of prev (curr is null at end)
3. Starting with prev=head instead of prev=None

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Recursive version:
  def reverseList(head):
      if not head or not head.next: return head
      new_head = reverseList(head.next)  # reverse the rest
      head.next.next = head              # current head becomes tail
      head.next = None                   # disconnect
      return new_head

LC92 — Reverse Linked List II (reverse only positions left to right)
```

---

## Problem 29/250 — LeetCode #21: Merge Two Sorted Lists

**Pattern:** `Linked List` | **Position in Roadmap:** #29

```
LeetCode 21 — Merge Two Sorted Lists
Problem #29 of 250 | Difficulty: Easy | Pattern: Two Pointers — Merge with Dummy Head

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Merge two sorted linked lists into one sorted linked list.
Return the head of the merged list.

Example:
  list1: 1→2→4
  list2: 1→3→4
  merged: 1→1→2→3→4→4

Both lists are already sorted.
The merged list should also be sorted.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

At each step, pick the SMALLER of the two current nodes.
Advance the pointer in the list that gave the smaller node.

  list1: 1→2→4   list2: 1→3→4

  Compare | Pick  | Advance
  ─────────────────────────
   1  vs 1 |  1(2) | list2 (tie → either works)
   1  vs 3 |   1   | list1
   2  vs 3 |   2   | list1
   4  vs 3 |   3   | list2
   4  vs 4 |   4   | list1 or list2
  remaining|   4   | attach rest

Result: 1→1→2→3→4→4

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

You have two sorted stacks of cards, face up.
Repeatedly pick the card with the smaller face value from the top.
When one stack is empty, take all remaining from the other.

Use a "dummy" head node at the start — it makes the code cleaner.
You never have to special-case the first node.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

dummy = ListNode(0)  # Dummy head to simplify logic
current = dummy

while list1 and list2:
    if list1.val <= list2.val:
        current.next = list1
        list1 = list1.next
    else:
        current.next = list2
        list2 = list2.next
    current = current.next

# Attach any remaining nodes
current.next = list1 if list1 else list2

return dummy.next

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

list1: 1→2→4, list2: 1→3→4

dummy→?, current=dummy

1 vs 1: pick list1(1), current=node(1), list1=2→4 → dummy→1
2 vs 1: pick list2(1), current=node(1), list2=3→4 → dummy→1→1
2 vs 3: pick list1(2), current=node(2), list1=4   → dummy→1→1→2
4 vs 3: pick list2(3), current=node(3), list2=4   → dummy→1→1→2→3
4 vs 4: pick list1(4), current=node(4), list1=None → dummy→1→1→2→3→4

list1 is None → attach list2 remaining = node(4)
dummy→1→1→2→3→4→4

Return dummy.next = 1→1→2→3→4→4 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Dummy head pattern: Creating a fake node before the real head
saves you from special-casing the FIRST element.

Instead of: "if result is None: set result, else append"
You just: current.next = ... every time!

The dummy head pattern is fundamental in linked list problems.
Use it whenever you're building a new linked list from scratch.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

dummy = ListNode(0)
curr = dummy
while l1 and l2:
    if l1.val <= l2.val:
        curr.next = l1; l1 = l1.next
    else:
        curr.next = l2; l2 = l2.next
    curr = curr.next
curr.next = l1 or l2
return dummy.next

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(m+n) — each node visited once
Space: O(1) — reusing existing nodes

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not using dummy head (leads to complex first-node handling)
2. Forgetting to attach remaining nodes at the end
3. Advancing both pointers (only advance the one you picked from)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC23 — Merge k Sorted Lists (generalize to k lists!)
  Use a min-heap to efficiently pick the minimum across k lists.
```

---

## Problem 30/250 — LeetCode #83: Remove Duplicates from Sorted List

**Pattern:** `Linked List` | **Position in Roadmap:** #30

```
LeetCode 83 — Remove Duplicates from Sorted List
Problem #30 of 250 | Difficulty: Easy | Pattern: Linked List — Skip Duplicates

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given the head of a sorted linked list,
delete all DUPLICATE nodes so each value appears only once.
Return the modified list.

Example:
  1→1→2 → 1→2
  1→1→2→3→3 → 1→2→3

The list is sorted, so duplicates are always adjacent!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Walk through list. If curr.val == curr.next.val, skip curr.next.
Otherwise, advance to next node.

  1→1→2→3→3

  curr=1: 1==1? YES → curr.next = curr.next.next (skip a 1) → 1→2→3→3
  curr=1: 1==2? NO → advance curr to 2
  curr=2: 2==3? NO → advance curr to 3
  curr=3: 3==3? YES → curr.next = curr.next.next = None → 1→2→3

  Result: 1→2→3

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Walk through the list with a pointer.
When current value == next value:
  Skip the next node (it's a duplicate of current)
When current value != next value:
  Safe to advance to next

Because list is sorted, you only need to compare curr to curr.next!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

curr = head

while curr and curr.next:
    if curr.val == curr.next.val:
        curr.next = curr.next.next   # Skip the duplicate
    else:
        curr = curr.next             # Move to next unique

return head

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1→1→2→3→3

curr=node(1)
  1==1 → curr.next=2→3→3  (skip first extra 1) → 1→2→3→3
  1==2? No → curr=node(2)
  2==3? No → curr=node(3)
  3==3 → curr.next=None → 1→2→3
  curr.next=None → while exits

Return head = 1→2→3 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This problem exploits the SORTED property:
  Duplicates must be adjacent in a sorted list.
  So checking curr.next is enough!

Key pattern: when skipping, DON'T advance curr.
  We stay at curr and check the new curr.next.
  (Maybe the new next is ALSO a duplicate!)

Compare with LC82 — Remove Duplicates from Sorted List II
  (harder version: remove ALL nodes with duplicate values)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

curr = head
while curr and curr.next:
    if curr.val == curr.next.val:
        curr.next = curr.next.next  # skip duplicate
    else:
        curr = curr.next            # move to next unique
return head

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — one pass
Space: O(1) — just curr pointer

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Advancing curr even when skipping (may miss consecutive duplicates!)
2. Not handling None checks carefully

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC82 — Remove Duplicates from Sorted List II
  Remove ALL occurrences of values that appear more than once.
  Example: 1→2→3→3→4→4→5 → 1→2→5
  Much harder! Need a prev pointer to skip entire groups.
```

---

## Problem 31/250 — LeetCode #203: Remove Linked List Elements

**Pattern:** `Linked List` | **Position in Roadmap:** #31

```
LeetCode 203 — Remove Linked List Elements
Problem #31 of 250 | Difficulty: Easy | Pattern: Linked List — Remove All Occurrences

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Remove all nodes with value val from the linked list.

Example:
  head = 1→2→6→3→4→5→6, val = 6
  → 1→2→3→4→5

Example:
  head = 6→6→6, val = 6
  → empty list [] (return None)

The challenge: the value could be at the HEAD!
This is why we need a dummy node.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Walk through list. Skip nodes where val matches.

  1→2→6→3→4→5→6, val=6

  prev=dummy, curr=1

  curr=1: 1≠6 → advance: prev=1, curr=2
  curr=2: 2≠6 → advance: prev=2, curr=6
  curr=6: 6==6 → prev.next = curr.next → prev=2, curr=3 (skip!)
  curr=3: 3≠6 → advance: prev=3, curr=4
  curr=4: 4≠6 → advance: prev=4, curr=5
  curr=5: 5≠6 → advance: prev=5, curr=6
  curr=6: 6==6 → prev.next = None → done

  Result: 1→2→3→4→5

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Use a dummy node so that even removing the head is handled uniformly.

prev always points to the last "kept" node.
curr scans ahead.

If curr should be removed: prev.next = curr.next (bypass curr)
If curr should be kept: advance prev to curr

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

dummy = ListNode(0)
dummy.next = head
prev = dummy
curr = head

while curr:
    if curr.val == val:
        prev.next = curr.next   # Skip curr (remove it)
    else:
        prev = curr             # Keep curr, advance prev
    curr = curr.next

return dummy.next

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1→2→6→3→6, val=6

dummy→1→2→6→3→6
prev=dummy, curr=1

curr=1: 1≠6 → prev=1, curr=2
curr=2: 2≠6 → prev=2, curr=6
curr=6: 6==6 → prev(2).next=3, curr=3 → 1→2→3→6
curr=3: 3≠6 → prev=3, curr=6
curr=6: 6==6 → prev(3).next=None, curr=None → 1→2→3

Return dummy.next = 1→2→3 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Dummy head handles head deletion cleanly:
  Without dummy, you'd write:
    while head and head.val == val: head = head.next
  With dummy, no special case needed!

The (prev, curr) pair is a standard linked list pattern.
prev is always the node BEFORE the potential deletion point.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

dummy = ListNode(0, head)
prev = dummy
curr = head
while curr:
    if curr.val == val:
        prev.next = curr.next
    else:
        prev = curr
    curr = curr.next
return dummy.next

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — one pass
Space: O(1) — dummy + prev + curr

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not using dummy (head deletion becomes a special case)
2. Advancing prev when you should skip
3. Forgetting to advance curr at all

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

What if you needed to remove all nodes with EVEN values?
What if you needed to remove the kth node from the start?
```

---

## Problem 32/250 — LeetCode #234: Palindrome Linked List

**Pattern:** `Linked List` | **Position in Roadmap:** #32

```
LeetCode 234 — Palindrome Linked List
Problem #32 of 250 | Difficulty: Easy | Pattern: Fast/Slow + Reverse Half

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given head of a linked list, return true if it's a palindrome.
A palindrome reads the same forward and backward.

Example:
  1→2→2→1 → true
  1→2→3→2→1 → true
  1→2 → false

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Strategy: Find middle, reverse second half, compare with first half.

  1→2→2→1

  Step 1: Find middle using slow/fast → middle at node(2) [second one]
  Step 2: Reverse second half: 1→2  ← becomes →  1←2
  Step 3: Compare: 1==1, 2==2 → palindrome!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Fold the list in half.
Do the two halves mirror each other?

1. Find the middle (slow/fast pointers)
2. Reverse the second half in-place
3. Compare first half and reversed second half node by node
4. If all match → palindrome

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def isPalindrome(head):
    # Step 1: Find middle
    slow = fast = head
    while fast and fast.next:
        slow = slow.next
        fast = fast.next.next

    # Step 2: Reverse second half
    prev = None
    curr = slow
    while curr:
        next_node = curr.next
        curr.next = prev
        prev = curr
        curr = next_node

    # Step 3: Compare first half with reversed second half
    left, right = head, prev
    while right:   # right is shorter or equal
        if left.val != right.val:
            return False
        left = left.next
        right = right.next

    return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1→2→2→1

Find middle: slow=2(second), fast=1(last)
Reverse second half [2→1]: prev=None,curr=2
  next=1, 2.next=None, prev=2, curr=1
  next=None, 1.next=2, prev=1, curr=None
  Second half reversed: 1→2

Compare: left=1,right=1: 1==1 ✓ left=2,right=2: 2==2 ✓
Return True ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This combines THREE techniques:
  1. Fast/slow to find middle
  2. In-place reversal of second half
  3. Two-pointer comparison

This is a great example of combining multiple patterns!
Learn each piece separately first, then combine.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Find mid → Reverse second half → Compare
slow = fast = head
while fast and fast.next: slow=slow.next; fast=fast.next.next
prev, curr = None, slow
while curr: nxt=curr.next; curr.next=prev; prev=curr; curr=nxt
l, r = head, prev
while r:
    if l.val != r.val: return False
    l=l.next; r=r.next
return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — three passes
Space: O(1) — in-place reversal

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not handling odd-length lists (middle node doesn't matter)
2. Comparing after failing to properly reverse
3. Mutating the list (ideally restore it after comparing)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Can you solve this without modifying the list?
  Use a stack: push first half, pop while comparing second half.
```

---

## Problem 33/250 — LeetCode #160: Intersection of Two Linked Lists

**Pattern:** `Two Pointers` | **Position in Roadmap:** #33

```
LeetCode 160 — Intersection of Two Linked Lists
Problem #33 of 250 | Difficulty: Easy | Pattern: Two Pointers — Path Length Equalization

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given heads of two linked lists, return the node where they intersect.
If they don't intersect, return null.

Example:
  A: a1→a2→c1→c2→c3
  B: b1→b2→b3→c1→c2→c3
  Intersection at: c1

Note: "intersection" means same node in memory, not just same value!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Key insight: If you switch paths at the end,
both pointers travel the same total distance!

  List A length = a + c
  List B length = b + c

  Pointer pA: travels A, then switches to B → total = a+c+b
  Pointer pB: travels B, then switches to A → total = b+c+a
  Both travel = a+b+c → they arrive at intersection simultaneously!

If no intersection: both reach null at the same time.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Two people walking different paths that eventually merge:
  Person A walks path A then path B
  Person B walks path B then path A
  If paths merge, they'll meet exactly at the merge point!

When a pointer reaches the end of its list, redirect to the OTHER list's head.
Eventually they'll either both reach null (no intersection) or meet at the node.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

pA = headA
pB = headB

while pA != pB:
    pA = pA.next if pA else headB  # when done with A, start B
    pB = pB.next if pB else headA  # when done with B, start A

return pA   # either the intersection node or None (both null)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

A: 1→2→3→4→5 (lengths differ but merge at node 3 of A = node 3)
Simpler: A: 4→1→8→4→5, B: 5→6→1→8→4→5
(Common part: 8→4→5, length 3)

pA walks: 4,1,8 then switches to B: 5,6,1,8 — meets pB at node(8)
pB walks: 5,6,1,8 then switches to A: 4,1,8 — meets pA at node(8)
Return node(8) ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Distance equalization trick:
  Total path for A = len(A) + len(B)
  Total path for B = len(B) + len(A)
  Same total! So they sync up at the intersection.

This is O(1) space and very elegant.
Alternative: use a HashSet of visited nodes in list A, then walk B.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

pA, pB = headA, headB
while pA != pB:
    pA = pA.next if pA else headB
    pB = pB.next if pB else headA
return pA

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(m+n) — both traverse combined length
Space: O(1) — two pointers

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Comparing values (need to compare nodes/references)
2. Infinite loop if lists have no intersection and you forget None handling
3. Not handling the case where one list is much shorter

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Can you find the intersection without modifying the lists?
What if the lists are circular (have cycles)?
```

---

## Problem 34/250 — LeetCode #328: Odd Even Linked List

**Pattern:** `Linked List` | **Position in Roadmap:** #34

```
LeetCode 328 — Odd Even Linked List
Problem #34 of 250 | Difficulty: Medium | Pattern: Linked List — Two Chains

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given head of a linked list, group all ODD-indexed nodes first,
then EVEN-indexed nodes (1-indexed).

Example:
  1→2→3→4→5
  Odd-indexed: 1,3,5 (positions 1,3,5)
  Even-indexed: 2,4 (positions 2,4)
  Result: 1→3→5→2→4

Example:
  2→1→3→5→6→4→7
  Odd: 2,3,6,7 → Even: 1,5,4
  Result: 2→3→6→7→1→5→4

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Maintain two separate chains: odd and even.
Walk through list, alternating between chains.
At end, connect odd chain's tail to even chain's head.

  1→2→3→4→5

  Start: odd=1, even=2, even_head=2

  Step 1: 1.next=3 (odd), 2.next=4 (even), odd=3, even=4
  Step 2: 3.next=5 (odd), 4.next=null (even), odd=5, even=null
  Connect: 5.next = even_head = 2
  Result: 1→3→5→2→4

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Build two separate chains as you walk:
  First chain: nodes at positions 1, 3, 5, 7...
  Second chain: nodes at positions 2, 4, 6, 8...

At the end, connect first chain's last node to second chain's first node.

Track: odd (current odd node), even (current even node), even_head (start of even)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

if not head:
    return head

odd = head
even = head.next
even_head = even   # Save start of even chain

while even and even.next:
    odd.next = even.next   # odd chain: skip even node
    odd = odd.next
    even.next = odd.next   # even chain: skip odd node
    even = even.next

odd.next = even_head   # Connect chains
return head

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1→2→3→4→5

odd=1, even=2, even_head=2

Iter 1:
  odd.next = even.next = 3 → odd=3
  even.next = odd.next = 4 → even=4
  List: 1→3→4→5, even chain: 2→4

Iter 2:
  odd.next = even.next = 5 → odd=5
  even.next = odd.next = None → even=None

Connect: odd(5).next = even_head(2)
Result: 1→3→5→2→4 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Interleaving rearrangement with two chains:
  Don't physically move nodes — just rewire the pointers!
  Build two parallel chains and connect them.

Key: Save even_head at the start because we'll lose it when rewiring!
This "save the head" trick is common in linked list problems.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

odd = head
even = head.next
even_head = even
while even and even.next:
    odd.next = even.next; odd = odd.next
    even.next = odd.next; even = even.next
odd.next = even_head
return head

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — one pass
Space: O(1) — just pointer variables

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not saving even_head at start
2. Forgetting null checks (even and even.next)
3. Wrong order of updates (can corrupt the chain)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

What if you needed every 3rd node together?
Or group by value (negative first, then positive)?
```

---

## Problem 35/250 — LeetCode #2: Add Two Numbers

**Pattern:** `Linked List` | **Position in Roadmap:** #35

```
LeetCode 2 — Add Two Numbers
Problem #35 of 250 | Difficulty: Medium | Pattern: Linked List — Digit-by-Digit Addition

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Two non-negative integers represented as REVERSED linked lists.
Each node contains one digit. Add the two numbers and return the sum as a reversed list.

Example:
  l1 = 2→4→3 (represents 342)
  l2 = 5→6→4 (represents 465)
  342 + 465 = 807
  Result: 7→0→8

The lists are already reversed (LSB first), so we add from head naturally!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Add digit by digit, left to right (which is LSB to MSB since reversed):

  l1:  2→4→3   (342)
  l2:  5→6→4   (465)

  Position | l1 | l2 | carry | digit | carry_out
  ─────────────────────────────────────────────
     1      |  2 |  5 |   0   |   7   |    0
     2      |  4 |  6 |   0   |   0   |    1   (10, carry 1)
     3      |  3 |  4 |   1   |   8   |    0

  Result nodes: 7→0→8

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Elementary school addition, column by column:
    342
  + 465
  ─────
    807

Except the digits are already reversed for us!
So we just walk left to right, adding with carry.

At each step:
  total = l1.val + l2.val + carry
  digit = total % 10
  carry = total // 10

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

dummy = ListNode(0)
curr = dummy
carry = 0

while l1 or l2 or carry:
    val1 = l1.val if l1 else 0   # l1 might be shorter
    val2 = l2.val if l2 else 0   # l2 might be shorter

    total = val1 + val2 + carry
    digit = total % 10
    carry = total // 10

    curr.next = ListNode(digit)
    curr = curr.next

    if l1: l1 = l1.next
    if l2: l2 = l2.next

return dummy.next

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

l1: 2→4→3, l2: 5→6→4

carry=0

l1=2,l2=5: total=7, digit=7, carry=0 → create node(7)
l1=4,l2=6: total=10, digit=0, carry=1 → create node(0)
l1=3,l2=4: total=3+4+1=8, digit=8, carry=0 → create node(8)
l1=None, l2=None, carry=0 → stop

Return: 7→0→8 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Three key techniques combined:
  1. Dummy head (clean list construction)
  2. Simultaneous list traversal
  3. Carry propagation (elementary addition)

The while loop continues while:
  ANY of l1, l2, carry is non-zero
This handles: different lengths AND a final carry (e.g., 999 + 1 = 1000)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

dummy = ListNode(0)
curr = dummy
carry = 0
while l1 or l2 or carry:
    v1 = l1.val if l1 else 0
    v2 = l2.val if l2 else 0
    total = v1 + v2 + carry
    curr.next = ListNode(total % 10)
    carry = total // 10
    curr = curr.next
    if l1: l1 = l1.next
    if l2: l2 = l2.next
return dummy.next

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(max(m,n)) — traverse longer list
Space: O(max(m,n)) — result list length

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Forgetting the final carry (e.g., 5+5=10, need extra node)
2. Not handling different-length lists (use 0 for missing digits)
3. Forgetting to advance l1 and l2 pointers

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC445 — Add Two Numbers II (numbers NOT reversed)
  Must reverse lists first, then add, then reverse result
  OR use stacks to process from MSB to LSB
```

---

## Problem 36/250 — LeetCode #142: Linked List Cycle II

**Pattern:** `Fast & Slow` | **Position in Roadmap:** #36

```
LeetCode 142 — Linked List Cycle II
Problem #36 of 250 | Difficulty: Medium | Pattern: Floyd's Algorithm — Find Cycle Start

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given a linked list with a cycle, return the NODE where the cycle BEGINS.
If no cycle, return null.

Example:
  3→2→0→-4→(back to 2)
  Cycle starts at node with value 2

This is the HARDER version of LC141.
First detect the cycle, then FIND WHERE it starts.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Floyd's Algorithm has two phases:

Phase 1 — DETECT:
  Slow moves 1, fast moves 2.
  If they meet → cycle exists.
  Meeting point is INSIDE the cycle.

Phase 2 — FIND START:
  When slow and fast meet:
    Reset one pointer to HEAD.
    Move BOTH pointers 1 step at a time.
    Where they meet = cycle start!

  Mathematical proof:
    Let: a = distance from head to cycle start
         b = distance from cycle start to meeting point
         c = cycle length
    When they meet: slow traveled a+b, fast traveled a+b+n*c
    Fast = 2*slow: a+b+n*c = 2(a+b)
    Solving: a = n*c - b = distance from meeting point to cycle start!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Phase 1: Run the race. Find where the hare catches the tortoise.
Phase 2: Put a NEW runner at the START. Both new runner and tortoise walk 1 step.
They'll meet exactly at the cycle entrance.

Why? Because a = n*c - b mathematically.
The meeting point is exactly "a" steps from the cycle start,
and the head is also "a" steps from the cycle start.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Phase 1: Detect cycle and find meeting point
slow = fast = head
while fast and fast.next:
    slow = slow.next
    fast = fast.next.next
    if slow == fast:
        break
else:
    return None   # No cycle

# Phase 2: Find cycle start
pointer = head
while pointer != slow:
    pointer = pointer.next
    slow = slow.next

return slow  # Both pointers now at cycle start

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

List: head→A→B→C→D→B (cycle at B)

Phase 1:
slow=head,fast=head
Step 1: slow=A, fast=B
Step 2: slow=B, fast=D
Step 3: slow=C, fast=C → MEET at C!

Phase 2:
pointer=head, slow=C (meeting point)
Step 1: pointer=A, slow=D
Step 2: pointer=B, slow=B → MEET at B (cycle start!) ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

The math behind Floyd's:
  When you reset one pointer to head and walk both at speed 1,
  they meet at the cycle entrance.
  This works because of the mathematical relationship: a = nc - b.

Trust the math and remember the two-phase pattern:
  Phase 1: detect (fast=2×slow)
  Phase 2: find start (reset one to head, both at speed 1)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Phase 1: detect
slow = fast = head
while fast and fast.next:
    slow = slow.next; fast = fast.next.next
    if slow == fast: break
else: return None

# Phase 2: find start
ptr = head
while ptr != slow:
    ptr = ptr.next; slow = slow.next
return slow

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — linear
Space: O(1) — constant pointers

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not handling the no-cycle case
2. Forgetting to do Phase 2 (just finding meeting point is not the answer)
3. Confusing meeting point with cycle start

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Can you find the LENGTH of the cycle?
  After finding the cycle start, count steps until you return to it.
```

---

## Problem 37/250 — LeetCode #143: Reorder List

**Pattern:** `Linked List` | **Position in Roadmap:** #37

```
LeetCode 143 — Reorder List
Problem #37 of 250 | Difficulty: Medium | Pattern: Linked List — Find Middle + Reverse + Merge

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given linked list: L0→L1→...→Ln-1→Ln
Reorder it to: L0→Ln→L1→Ln-1→L2→Ln-2→...

Example:
  1→2→3→4 → 1→4→2→3
  1→2→3→4→5 → 1→5→2→4→3

You're interleaving from both ends toward the middle.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Three steps:
  1. Find the middle of the list
  2. Reverse the second half
  3. Merge the two halves alternately

  1→2→3→4→5
  Middle: 3
  First half: 1→2→3
  Second half reversed: 5→4→3

  Merge alternately: 1→5→2→4→3

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think of a snake folding in half:
  Take the tail, fold it over to the head.
  Now alternate: front-back-front-back...

Step 1: Find middle (where to fold)
Step 2: Reverse back half (flip the tail)
Step 3: Interleave (braid the two halves)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def reorderList(head):
    if not head or not head.next:
        return

    # Step 1: Find middle
    slow = fast = head
    while fast.next and fast.next.next:
        slow = slow.next
        fast = fast.next.next
    # slow is now at the END of first half

    # Step 2: Reverse second half
    prev, curr = None, slow.next
    slow.next = None  # Cut the list
    while curr:
        nxt = curr.next
        curr.next = prev
        prev = curr
        curr = nxt
    # prev is head of reversed second half

    # Step 3: Merge alternately
    first, second = head, prev
    while second:
        tmp1, tmp2 = first.next, second.next
        first.next = second
        second.next = tmp1
        first = tmp1
        second = tmp2

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1→2→3→4→5

Step 1: middle=3 (slow=3, fast=5)
First half: 1→2→3, slow.next=None
Second half: 4→5

Step 2: Reverse 4→5 → 5→4

Step 3: Merge 1→2→3 with 5→4:
  first=1, second=5
  tmp1=2, tmp2=4: 1.next=5, 5.next=2, first=2, second=4
  tmp1=3, tmp2=None: 2.next=4, 4.next=3, first=3, second=None
  second=None → stop

Result: 1→5→2→4→3 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Three fundamental operations combined:
  Find middle + Reverse half + Merge alternately

Each operation individually is a simpler problem.
This problem CHAINS them together.

Learn this combination — it appears in:
  LC234 (Palindrome LL) uses Find middle + Reverse half + Compare

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Find middle → cut → reverse second → merge
slow = fast = head
while fast.next and fast.next.next: slow=slow.next; fast=fast.next.next
prev, curr = None, slow.next; slow.next = None
while curr: nxt=curr.next; curr.next=prev; prev=curr; curr=nxt
first, second = head, prev
while second:
    t1,t2 = first.next,second.next
    first.next=second; second.next=t1
    first=t1; second=t2

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — three linear passes
Space: O(1) — in-place

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not cutting the list at the middle (needed to avoid infinite loop)
2. Wrong stopping condition for finding middle
3. Losing track of tmp pointers during merge

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Why do we use "while fast.next and fast.next.next" here
instead of "while fast and fast.next" (which we used in LC876)?

Hint: We want slow at the END of first half, not the START of second.
```

---

## Problem 38/250 — LeetCode #138: Copy List with Random Pointer

**Pattern:** `Hash Map` | **Position in Roadmap:** #38

```
LeetCode 138 — Copy List with Random Pointer
Problem #38 of 250 | Difficulty: Medium | Pattern: HashMap — Original to Clone Mapping

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

A linked list where each node has TWO pointers:
  next → next node
  random → ANY node in the list (or null)

Create a DEEP COPY (completely new nodes, no shared references).

Example:
  Original: [[7,null],[13,0],[11,4],[10,2],[1,0]]
  Each entry is [val, random_index]
  
Return the head of the deep-copied list.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Challenge: when creating node B's clone,
node B's random might point to a node we haven't cloned yet!

Solution: Two-pass approach with a HashMap:
  Pass 1: Create all clone nodes, store mapping {original → clone}
  Pass 2: Set next and random pointers for all clones

  HashMap: {node1_orig → node1_clone, node2_orig → node2_clone, ...}

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Imagine photocopying a document with cross-references:
  First, copy all pages (create all nodes).
  Then, fix all the cross-references to point to the COPIES, not originals.

Pass 1: Create clones (copy values only, no pointers)
  map[original_node] = new_clone_node

Pass 2: Wire up pointers
  clone.next = map[original.next]
  clone.random = map[original.random]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def copyRandomList(head):
    if not head:
        return None

    # Pass 1: Create all clone nodes
    node_map = {None: None}   # None maps to None (for null random pointers)
    curr = head
    while curr:
        node_map[curr] = Node(curr.val)
        curr = curr.next

    # Pass 2: Wire up next and random pointers
    curr = head
    while curr:
        node_map[curr].next = node_map[curr.next]
        node_map[curr].random = node_map[curr.random]
        curr = curr.next

    return node_map[head]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Nodes: A(7)→B(13)→C(11), A.random=None, B.random=A, C.random=C

Pass 1:
  node_map = {None:None, A:A'(7), B:B'(13), C:C'(11)}

Pass 2:
  curr=A: A'.next=node_map[B]=B', A'.random=node_map[None]=None
  curr=B: B'.next=node_map[C]=C', B'.random=node_map[A]=A'
  curr=C: C'.next=node_map[None]=None, C'.random=node_map[C]=C'

Return node_map[A] = A' ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

When copying structures with forward AND backward references,
use a HashMap as a "translation table":
  old node → new node

This two-pass approach avoids the chicken-and-egg problem
of "I need to set B's random to C's clone, but I haven't cloned C yet."

Pass 1 creates all clones.
Pass 2 wires them up.

Note: {None: None} in the map handles null pointers elegantly!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

node_map = {None: None}
curr = head
while curr: node_map[curr] = Node(curr.val); curr = curr.next
curr = head
while curr:
    node_map[curr].next = node_map[curr.next]
    node_map[curr].random = node_map[curr.random]
    curr = curr.next
return node_map[head]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — two passes
Space: O(n) — HashMap stores n node mappings

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not adding None→None to map (null random pointers crash)
2. Single pass (can't set random if clone doesn't exist yet)
3. Sharing node references (not a deep copy!)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

O(1) space approach: interweave copies into original list!
  Step 1: Insert A' between A and B, B' between B and C...
  Step 2: Fix random pointers using interweaved structure
  Step 3: Separate the two lists
```

---

## Problem 39/250 — LeetCode #155: Min Stack

**Pattern:** `Stack` | **Position in Roadmap:** #39

```
LeetCode 155 — Min Stack
Problem #39 of 250 | Difficulty: Medium | Pattern: Stack + Auxiliary Min Stack

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Design a stack that supports push, pop, top, and getMin in O(1).

getMin() must return the MINIMUM element in the stack.

Example:
  push(-2), push(0), push(-3)
  getMin() → -3 (smallest element)
  pop()
  top() → 0
  getMin() → -2 (now smallest is -2)

Standard stack doesn't track minimum.
We need a clever data structure.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Problem: when we pop the current minimum, we need to know the NEXT minimum.
Solution: Maintain a SECOND stack that tracks minimums.

  Main stack: stores all values
  Min stack: stores the minimum AT EACH LEVEL

  push(-2): main=[-2], min=[-2]        (min is -2)
  push(0):  main=[-2,0], min=[-2,-2]  (min is still -2)
  push(-3): main=[-2,0,-3], min=[-2,-2,-3]  (min is now -3)
  pop():    main=[-2,0], min=[-2,-2]  (min is back to -2)
  getMin(): min[-1] = -2 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Keep a "running minimum" stack alongside the main stack.

Every time you push, also push to min_stack the MINIMUM of:
  current value vs current min_stack top

Every time you pop, also pop from min_stack.

The min_stack top ALWAYS tells you the current minimum!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

class MinStack:
    def __init__(self):
        self.stack = []
        self.min_stack = []

    def push(self, val):
        self.stack.append(val)
        if self.min_stack:
            self.min_stack.append(min(val, self.min_stack[-1]))
        else:
            self.min_stack.append(val)

    def pop(self):
        self.stack.pop()
        self.min_stack.pop()

    def top(self):
        return self.stack[-1]

    def getMin(self):
        return self.min_stack[-1]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

push(-2): stack=[-2], min_stack=[-2]
push(0):  stack=[-2,0], min_stack=[-2, min(0,-2)=-2]
push(-3): stack=[-2,0,-3], min_stack=[-2,-2, min(-3,-2)=-3]

getMin() → min_stack[-1] = -3 ✓

pop():    stack=[-2,0], min_stack=[-2,-2]
top()     → stack[-1] = 0 ✓
getMin()  → min_stack[-1] = -2 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Two stacks in tandem:
  Main stack: normal stack operations
  Min stack: tracks the minimum AT EVERY STATE

When something gets popped, the corresponding min gets popped too.
This way getMin() is always O(1).

The min_stack mirrors the structure of the main stack exactly.
When main has 3 elements, min_stack also has 3.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

self.stack = []
self.min_stack = []

def push(val):
    self.stack.append(val)
    self.min_stack.append(min(val, self.min_stack[-1] if self.min_stack else val))

def pop(): self.stack.pop(); self.min_stack.pop()
def top(): return self.stack[-1]
def getMin(): return self.min_stack[-1]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

push/pop/top/getMin: all O(1)
Space: O(n) — two stacks of equal size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Only tracking global minimum (can't recover after pop)
2. Not syncing min_stack with main_stack pops
3. Forgetting to handle empty min_stack on first push

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC716 — Max Stack (track maximum instead of minimum)
What if you needed to support getMax() too?
Can you do both min and max in O(1) simultaneously?
```

---

## Problem 40/250 — LeetCode #20: Valid Parentheses

**Pattern:** `Stack` | **Position in Roadmap:** #40

```
LeetCode 20 — Valid Parentheses
Problem #40 of 250 | Difficulty: Easy | Pattern: Stack — Open/Close Matching

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given a string of brackets, determine if it's valid.
Valid means:
  - Every open bracket has a matching close bracket
  - Brackets are closed in the right ORDER

Example:
  "()" → true
  "()[]{}" → true
  "(]" → false (wrong type)
  "([)]" → false (wrong order)
  "{[]}" → true (nested correctly)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Use a stack. Rule:
  Open bracket → push onto stack
  Close bracket → check if matches the top of stack

  s = "({[]})"

  char | action        | stack
  ──────────────────────────────
   (   | push          | ['(']
   {   | push          | ['(', '{']
   [   | push          | ['(', '{', '[']
   ]   | matches '[' ✓ pop  | ['(', '{']
   }   | matches '{' ✓ pop  | ['(']
   )   | matches '(' ✓ pop  | []

  Stack empty → valid!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Imagine stacking colored plates:
  Open bracket → place a plate on the stack
  Close bracket → check the top plate matches!

If the colors don't match → invalid!
If you try to close and the stack is empty → invalid!
After processing all → if stack is empty → valid!

The stack maintains the "context" of what's currently open.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def isValid(s):
    stack = []
    matching = {')': '(', '}': '{', ']': '['}

    for char in s:
        if char in '({[':
            stack.append(char)   # Open bracket: push
        else:
            # Close bracket: check if it matches top of stack
            if not stack or stack[-1] != matching[char]:
                return False
            stack.pop()

    return len(stack) == 0  # Valid only if nothing left unmatched

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

s = "([)]"

char='(': push → stack=['(']
char='[': push → stack=['(','[']
char=')': closing, matching[')']='(' check stack[-1]='[' ≠ '(' → return False ✓

s = "{[]}"
char='{': push → ['{'
char='[': push → ['{','[']
char=']': matching=']'→'[', stack[-1]='[' ✓ → pop → ['{']
char='}': matching='}'→'{', stack[-1]='{' ✓ → pop → []
Empty stack → return True ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

The stack perfectly models "what's currently open."
When we see a close bracket, the stack top tells us what should close.

This pattern appears in:
  - Expression evaluation
  - HTML/XML tag validation
  - Compiler design (matching braces in code)

The matching dictionary maps close→open for clean comparison.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

stack = []
match = {')':'(', '}':'{', ']':'['}
for c in s:
    if c in '([{':
        stack.append(c)
    else:
        if not stack or stack[-1] != match[c]:
            return False
        stack.pop()
return not stack

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — one pass
Space: O(n) — stack can hold up to n/2 open brackets

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Returning True when stack is non-empty (unclosed brackets remain!)
2. Not checking if stack is empty before popping
3. Using a counter instead of stack (can't handle wrong ORDER)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC1249 — Minimum Remove to Make Valid Parentheses
  Remove the minimum brackets to make the string valid.
  Which brackets to remove? Use stack to track indices!
```

---

## Problem 41/250 — LeetCode #496: Next Greater Element I

**Pattern:** `Monotonic Stack` | **Position in Roadmap:** #41

```
LeetCode 496 — Next Greater Element I
Problem #41 of 250 | Difficulty: Easy | Pattern: Monotonic Stack — Next Larger to Right

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given two arrays nums1 and nums2 (all distinct),
for each element in nums1, find the NEXT GREATER element in nums2.
"Next greater" = first element to the right that is larger.

Example:
  nums1 = [4,1,2], nums2 = [1,3,4,2]
  For 4: no greater to its right → -1
  For 1: next greater = 3
  For 2: no greater to its right → -1
  Result: [-1, 3, -1]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Process nums2 with a MONOTONIC STACK.
Stack maintains elements without a "next greater" yet.

  nums2 = [1, 3, 4, 2]

  num=1: stack=[1]
  num=3: 3>1 → 1's next greater = 3! map[1]=3, pop 1. stack=[3]
  num=4: 4>3 → 3's next greater = 4! map[3]=4, pop 3. stack=[4]
  num=2: 2<4 → push. stack=[4,2]
  End: remaining stack = [4,2] → next greater = -1

  map = {1:3, 3:4, 4:-1, 2:-1}
  Result for nums1=[4,1,2]: [-1, 3, -1]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Imagine each person is waiting to find someone taller to their right.
Walk right to left through nums2.
Stack holds people still "waiting" for someone taller.

When you see a new person:
  If they're taller than the top of the stack → top's wait is over! Pop and record.
  Repeat until stack is empty or top is taller.
  Push new person — they now wait for their next greater.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def nextGreaterElement(nums1, nums2):
    stack = []
    next_greater = {}   # maps num → its next greater element

    for num in nums2:
        # Pop all elements smaller than current num
        while stack and stack[-1] < num:
            next_greater[stack.pop()] = num
        stack.append(num)

    # Remaining in stack have no next greater
    while stack:
        next_greater[stack.pop()] = -1

    return [next_greater[n] for n in nums1]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums2 = [1, 3, 4, 2]

num=1: stack empty, push 1 → stack=[1]
num=3: stack[-1]=1 < 3 → map[1]=3, pop. Stack empty → push 3 → stack=[3]
num=4: stack[-1]=3 < 4 → map[3]=4, pop. Stack empty → push 4 → stack=[4]
num=2: stack[-1]=4 > 2 → push 2 → stack=[4,2]

End: stack=[4,2] → map[2]=-1, map[4]=-1

next_greater = {1:3, 3:4, 2:-1, 4:-1}

nums1=[4,1,2] → [-1, 3, -1] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

MONOTONIC STACK: a stack that's always sorted (either increasing or decreasing).

When we push an element:
  Pop everything smaller (for "next greater") or larger (for "next smaller").
  The popped elements have found their answer!

This runs in O(n) total — each element is pushed and popped at most once.

Monotonic stacks appear in:
  LC739 — Daily Temperatures (same pattern!)
  LC84 — Largest Rectangle in Histogram
  LC42 — Trapping Rain Water

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

stack = []
next_greater = {}
for num in nums2:
    while stack and stack[-1] < num:
        next_greater[stack.pop()] = num
    stack.append(num)
while stack: next_greater[stack.pop()] = -1
return [next_greater[n] for n in nums1]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(m+n) — process nums2 O(n), build result O(m)
Space: O(n) — stack + HashMap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force: O(m×n) — searching for next greater for each element
2. Forgetting to handle elements with no next greater (assign -1)
3. Wrong direction: need to process nums2 and find first GREATER to RIGHT

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC503 — Next Greater Element II (circular array!)
LC739 — Daily Temperatures (same idea: next warmer day)
```

---

## Problem 42/250 — LeetCode #682: Baseball Game

**Pattern:** `Stack` | **Position in Roadmap:** #42

```
LeetCode 682 — Baseball Game
Problem #42 of 250 | Difficulty: Easy | Pattern: Stack — Simulation

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Simulate a baseball game with these operations:
  integer n: record new score of n
  '+': record sum of last two scores
  'D': record double of last score
  'C': invalidate (remove) last score

Return sum of all valid scores.

Example:
  ops = ["5","2","C","D","+"]
  5 → [5]
  2 → [5,2]
  C → [5] (remove 2)
  D → [5,10] (double of 5)
  + → [5,10,15] (5+10=15)
  Sum = 5+10+15 = 30

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Use a stack to track valid scores.

  ops = ["5","-2","4","C","D","9","+","+"]

  op   | stack
  ─────────────────────────────────
  "5"  | [5]
  "-2" | [5,-2]
  "4"  | [5,-2,4]
  "C"  | [5,-2] (pop 4)
  "D"  | [5,-2,-4] (double of -2)
  "9"  | [5,-2,-4,9]
  "+"  | [5,-2,-4,9,5] (-4+9=5)
  "+"  | [5,-2,-4,9,5,14] (9+5=14)

  Sum = 5+(-2)+(-4)+9+5+14 = 27

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Stack is perfect here: undo (C) = pop, and last scores = stack[-1] and stack[-2].

For each operation:
  Number → push it
  '+' → push stack[-1] + stack[-2]
  'D' → push stack[-1] * 2
  'C' → pop

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def calPoints(ops):
    stack = []

    for op in ops:
        if op == '+':
            stack.append(stack[-1] + stack[-2])
        elif op == 'D':
            stack.append(stack[-1] * 2)
        elif op == 'C':
            stack.pop()
        else:
            stack.append(int(op))

    return sum(stack)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

ops = ["5","2","C","D","+"]

"5": stack=[5]
"2": stack=[5,2]
"C": pop → stack=[5]
"D": double 5 → stack=[5,10]
"+": 5+10=15 → stack=[5,10,15]

sum([5,10,15]) = 30 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Stack for history/undo operations:
  C (cancel) = pop the last recorded item
  + needs last two items = stack[-1] + stack[-2]

Stack naturally provides access to recent history.
This is the core insight for simulation problems with "undo" operations.

Sum at end = sum(stack) — clean and simple.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

stack = []
for op in ops:
    if op == '+': stack.append(stack[-1]+stack[-2])
    elif op == 'D': stack.append(stack[-1]*2)
    elif op == 'C': stack.pop()
    else: stack.append(int(op))
return sum(stack)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — process each operation once
Space: O(n) — stack can hold all scores

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not converting string to int for number operations
2. Forgetting that 'D' and '+' need stack[-1] and [-2]
3. Not checking stack has enough elements (though problem guarantees valid input)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

What if 'C' could cancel the last k operations instead of 1?
What if scores had min/max limits?
```

---

## Problem 43/250 — LeetCode #1047: Remove All Adjacent Duplicates In String

**Pattern:** `Stack` | **Position in Roadmap:** #43

```
LeetCode 1047 — Remove All Adjacent Duplicates In String
Problem #43 of 250 | Difficulty: Easy | Pattern: Stack — Character Matching

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given a string s, repeatedly remove adjacent duplicate characters.
A "duplicate" = two adjacent identical characters.
Keep removing until no more adjacent duplicates.

Example:
  s = "abbaca"
  "abbaca" → remove "bb" → "aaca"
  "aaca" → remove "aa" → "ca"
  → "ca"

Example:
  s = "azxxzy"
  → remove "xx" → "azzy"
  → remove "zz" → "ay"
  → "ay" 

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Use a stack to process characters:
  If top of stack == current char → duplicate! Pop (remove both).
  Otherwise → push current char.

  s = "abbaca"

  char | stack
  ────────────
   a   | ['a']
   b   | ['a','b']
   b   | ['a'] (b==b → pop!) ← adjacent duplicate removed!
   a   | [] (a==a → pop!)
   c   | ['c']
   a   | ['c','a']

  Result: "ca" ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Stack simulates reading the string and erasing duplicates.
Think of a bubble popping:
  When a new character matches the top → they both disappear!

This handles chain reactions:
  "aabb" → process 'a'→['a'], 'a'→[] (pop!), 'b'→['b'], 'b'→[] (pop!)
  Result: "" (empty)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def removeDuplicates(s):
    stack = []

    for char in s:
        if stack and stack[-1] == char:
            stack.pop()   # Adjacent duplicate found! Remove both.
        else:
            stack.append(char)

    return ''.join(stack)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

s = "azxxzy"

a: stack=['a']
z: stack=['a','z']
x: stack=['a','z','x']
x: x==x → pop → stack=['a','z']
z: z==z → pop → stack=['a']
y: stack=['a','y']

Return "ay" ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

The stack handles CHAIN REACTIONS naturally!

When we pop a duplicate, the stack[-1] might now equal
what's coming NEXT. We don't need to re-scan — the stack
automatically handles consecutive reactions.

Example: "abba"
  a → [a]
  b → [a,b]
  b → [a] (pop b)
  a → [] (pop a) ← chain reaction!
  
Result: "" ✓ All removed!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

stack = []
for c in s:
    if stack and stack[-1] == c:
        stack.pop()
    else:
        stack.append(c)
return ''.join(stack)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — each character pushed and popped at most once
Space: O(n) — stack

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Using a loop to repeatedly scan and remove (O(n²)!)
   Stack does it in ONE pass.
2. Not joining stack at end (it's a list, need ''.join())

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC1209 — Remove All Adjacent Duplicates in String II
  Remove groups of k adjacent identical characters.
  Stack holds (char, count) pairs!
```

---

## Problem 44/250 — LeetCode #844: Backspace String Compare

**Pattern:** `Stack / Two Pointers` | **Position in Roadmap:** #44

```
LeetCode 844 — Backspace String Compare
Problem #44 of 250 | Difficulty: Easy | Pattern: Stack — Simulation or Two Pointers

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given two strings s and t where '#' means backspace,
return true if they're equal after processing.

Example:
  s = "ab#c", t = "ad#c"
  s: a,b → backspace removes b → "ac"
  t: a,d → backspace removes d → "ac"
  "ac" == "ac" → true

Example:
  s = "ab##", t = "c#d#"
  s: a,b → remove b → a → remove a → ""
  t: c → remove c → "" → d → remove d → ""
  "" == "" → true

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Stack approach:
  Regular char → push
  '#' → pop (backspace)
  At end, compare stacks

Two-pointer approach (O(1) space):
  Process from RIGHT to LEFT
  Skip characters that will be deleted by future '#'s
  Compare remaining characters

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Imagine typing on a keyboard with backspace:
  a,b,#,c → you type 'a', 'b', hit backspace, then 'c' → "ac"

Stack simulates this perfectly:
  letter → push
  # → pop (erase previous)

Compare the final strings after "typing" both.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Stack approach:
def backspaceCompare(s, t):
    def process(string):
        stack = []
        for c in string:
            if c != '#':
                stack.append(c)
            elif stack:
                stack.pop()
        return ''.join(stack)

    return process(s) == process(t)

# Two-pointer O(1) space:
def backspaceCompare(s, t):
    i, j = len(s)-1, len(t)-1
    skip_s = skip_t = 0
    while i >= 0 or j >= 0:
        while i >= 0:
            if s[i] == '#': skip_s += 1; i -= 1
            elif skip_s > 0: skip_s -= 1; i -= 1
            else: break
        while j >= 0:
            if t[j] == '#': skip_t += 1; j -= 1
            elif skip_t > 0: skip_t -= 1; j -= 1
            else: break
        if i >= 0 and j >= 0 and s[i] != t[j]: return False
        if (i >= 0) != (j >= 0): return False
        i -= 1; j -= 1
    return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

s = "ab#c", t = "ad#c"

process("ab#c"):
  a → [a], b → [a,b], # → [a] (pop b), c → [a,c]
  Result: "ac"

process("ad#c"):
  a → [a], d → [a,d], # → [a] (pop d), c → [a,c]
  Result: "ac"

"ac" == "ac" → True ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Stack elegantly simulates typing with backspace.
Two-pointer approach processes from right to left,
counting backspaces to skip characters — O(1) space!

The right-to-left approach works because:
  When we see '#', we know the PREVIOUS character gets deleted.
  Processing backwards lets us handle this without a stack.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def process(s):
    stack = []
    for c in s:
        if c != '#': stack.append(c)
        elif stack: stack.pop()
    return ''.join(stack)
return process(s) == process(t)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Stack: O(m+n) time, O(m+n) space
Two-pointer: O(m+n) time, O(1) space

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Popping from empty stack on '#' (check stack before popping)
2. Not handling multiple consecutive '#' correctly
3. Comparing character by character without processing backspaces first

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Can you solve it with O(1) extra space?
That's the two-pointer approach from right to left!
```

---

## Problem 45/250 — LeetCode #71: Simplify Path

**Pattern:** `Stack` | **Position in Roadmap:** #45

```
LeetCode 71 — Simplify Path
Problem #45 of 250 | Difficulty: Medium | Pattern: Stack — Path Processing

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given an UNIX absolute path, simplify it.
Rules:
  '.' = current directory (ignore it)
  '..' = parent directory (go up one level)
  '//' = treat as single '/'
  Any other name = directory name

Example:
  "/home/"  → "/home"
  "/../"    → "/"  (can't go above root)
  "/home//foo/" → "/home/foo"
  "/a/./b/../../c/" → "/c"
    a → a, . → stay, b → a/b, .. → a, .. → root, c → c

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Split by '/'. Process each part:
  "" or ".": skip
  "..": pop from stack (go up)
  anything else: push onto stack

  "/a/./b/../../c/"

  split: ['', 'a', '.', 'b', '..', '..', 'c', '']
  "" → skip
  "a" → push → stack=['a']
  "." → skip
  "b" → push → stack=['a','b']
  ".." → pop → stack=['a']
  ".." → pop → stack=[]
  "c" → push → stack=['c']
  "" → skip

  Result: "/" + "/".join(['c']) = "/c" 

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think of each directory as a level on a stack.
  Entering a dir → push
  '..' → go back up → pop
  '.' or empty → stay → skip

At end, join the stack with '/' to build the path.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def simplifyPath(path):
    parts = path.split('/')
    stack = []

    for part in parts:
        if part == '' or part == '.':
            continue          # Ignore empty segments and current dir
        elif part == '..':
            if stack:
                stack.pop()   # Go up (but not above root)
        else:
            stack.append(part)  # Valid directory name

    return '/' + '/'.join(stack)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

path = "/home//foo/"

split('/') = ['', 'home', '', 'foo', '']

'': skip
'home': push → ['home']
'': skip
'foo': push → ['home','foo']
'': skip

Result: "/" + "home/foo" = "/home/foo" ✓

path = "/../"
split = ['', '..', '']
'': skip
'..': stack empty, can't pop → stack=[]
'': skip
Result: "/" + "" = "/" ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Stack represents the directory hierarchy.
Each push is going deeper, each pop is going up.

Key observations:
  1. Split on '/' to get path components
  2. Stack handles '..' elegantly (just pop)
  3. Check stack non-empty before popping (can't go above root)
  4. Join with '/' and prepend '/' for absolute path

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

stack = []
for part in path.split('/'):
    if part in ('', '.'): continue
    elif part == '..':
        if stack: stack.pop()
    else: stack.append(part)
return '/' + '/'.join(stack)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — split + process
Space: O(n) — stack

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not handling empty strings from split ('//' gives empty between)
2. Popping when stack is empty (going above root → stay at root)
3. Forgetting to prepend '/' to the result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

What if the path could be relative (not starting with '/')?
What if you needed to handle Windows paths (with \)?
```

---

## Problem 46/250 — LeetCode #150: Evaluate Reverse Polish Notation

**Pattern:** `Stack` | **Position in Roadmap:** #46

```
LeetCode 150 — Evaluate Reverse Polish Notation
Problem #46 of 250 | Difficulty: Medium | Pattern: Stack — Expression Evaluation

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Evaluate an expression in Reverse Polish Notation (RPN).
Operands come before operators (postfix notation).
Valid operators: +, -, *, /

Example:
  ["2","1","+","3","*"] = ((2+1)*3) = 9
  ["4","13","5","/","+"] = (4+(13/5)) = 6
  ["10","6","9","3","+","-","11","*","/","17","+","5","+"] = 22

In RPN, you don't need parentheses — order handles precedence.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Process tokens left to right:
  Number → push onto stack
  Operator → pop TWO numbers, apply operator, push result

  ["2","1","+","3","*"]

  Token | Action            | Stack
  ───────────────────────────────────
  "2"   | push              | [2]
  "1"   | push              | [2,1]
  "+"   | pop 1,2; 2+1=3   | [3]
  "3"   | push              | [3,3]
  "*"   | pop 3,3; 3*3=9   | [9]

  Return 9 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

RPN is how calculators work internally.
As you scan tokens:
  If it's a number: put it on your "pile"
  If it's an operator: take the top two from the pile, combine them, put the result back

The pile is a stack!
At the end, one number remains: the answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def evalRPN(tokens):
    stack = []

    for token in tokens:
        if token in '+-*/':
            b = stack.pop()   # Second operand
            a = stack.pop()   # First operand
            if token == '+': stack.append(a + b)
            elif token == '-': stack.append(a - b)
            elif token == '*': stack.append(a * b)
            elif token == '/': stack.append(int(a / b))  # Truncate toward zero!
        else:
            stack.append(int(token))

    return stack[0]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

["4","13","5","/","+"]

"4": stack=[4]
"13": stack=[4,13]
"5": stack=[4,13,5]
"/": b=5, a=13, 13/5=2 (truncate) → stack=[4,2]
"+": b=2, a=4, 4+2=6 → stack=[6]

Return 6 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Stack is perfect for expression evaluation!

Important: pop order matters!
  b = pop() → this is the SECOND operand (right side)
  a = pop() → this is the FIRST operand (left side)
  
For subtraction: a - b (not b - a!)
For division: int(a/b) which truncates toward zero
  (Python's // floors toward negative infinity — not the same!)

Use int(a/b) not a//b for correct behavior with negatives.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

stack = []
for token in tokens:
    if token in '+-*/':
        b, a = stack.pop(), stack.pop()
        if token=='+': stack.append(a+b)
        elif token=='-': stack.append(a-b)
        elif token=='*': stack.append(a*b)
        else: stack.append(int(a/b))
    else: stack.append(int(token))
return stack[0]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — process each token once
Space: O(n) — stack

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Wrong pop order: b=pop(), a=pop() (second pop is the FIRST operand)
2. Using // instead of int(/) for division (wrong for negative numbers)
3. Not converting token to int before pushing

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC224 — Basic Calculator (infix notation with parentheses — much harder!)
LC227 — Basic Calculator II (no parentheses, but +,-,*,/)
```

---

## Problem 47/250 — LeetCode #739: Daily Temperatures

**Pattern:** `Monotonic Stack` | **Position in Roadmap:** #47

```
LeetCode 739 — Daily Temperatures
Problem #47 of 250 | Difficulty: Medium | Pattern: Monotonic Stack — Next Greater Index

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given daily temperatures, for each day find how many days until
a warmer temperature.
If no future warmer day exists, the answer is 0.

Example:
  temps = [73,74,75,71,69,72,76,73]
  For day 0 (73°): warmer on day 1 (74°) → wait 1 day
  For day 1 (74°): warmer on day 2 (75°) → wait 1 day
  For day 2 (75°): warmer on day 6 (76°) → wait 4 days
  For day 3 (71°): warmer on day 5 (72°) → wait 2 days
  Answer: [1,1,4,2,1,1,0,0]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Monotonic Stack: stores indices of temperatures waiting for a warmer day.
Stack is DECREASING (temperatures decrease from bottom to top).

  temps = [73, 74, 75, 71, 69, 72, 76, 73]

  i=0: push 0 → stack=[0] (73°)
  i=1: 74>73 → ans[0]=1-0=1, pop 0. Stack empty → push 1 → stack=[1]
  i=2: 75>74 → ans[1]=2-1=1, pop 1. Stack empty → push 2 → stack=[2]
  i=3: 71<75 → push 3 → stack=[2,3]
  i=4: 69<71 → push 4 → stack=[2,3,4]
  i=5: 72>69 → ans[4]=5-4=1, pop 4. 72>71 → ans[3]=5-3=2, pop 3. 72<75 → push 5 → stack=[2,5]
  i=6: 76>72 → ans[5]=6-5=1, pop 5. 76>75 → ans[2]=6-2=4, pop 2. Stack empty → push 6
  i=7: 73<76 → push 7 → stack=[6,7]
  End: stack=[6,7] → ans[6]=0, ans[7]=0

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Each person in a line is waiting to see someone TALLER ahead.
As a new (taller) person arrives, everyone who was shorter and waiting
gets their answer: "I waited X positions!"

The stack holds people who are STILL WAITING.
When a new person is taller than the top of the stack:
  The top person's wait is over! pop and record wait time.
  Repeat for anyone else who is now shorter.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def dailyTemperatures(temperatures):
    n = len(temperatures)
    answer = [0] * n
    stack = []   # Stack of indices

    for i in range(n):
        while stack and temperatures[i] > temperatures[stack[-1]]:
            prev_idx = stack.pop()
            answer[prev_idx] = i - prev_idx   # Days waited
        stack.append(i)

    return answer   # Remaining in stack keep answer=0

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

temps = [73,74,75,71,69,72,76,73]

i=0: stack=[], push 0 → [0]
i=1: 74>73 → ans[0]=1, pop. Push 1 → [1]
i=2: 75>74 → ans[1]=1, pop. Push 2 → [2]
i=3: 71<75 → push 3 → [2,3]
i=4: 69<71 → push 4 → [2,3,4]
i=5: 72>69 → ans[4]=1, pop. 72>71 → ans[3]=2, pop. Push 5 → [2,5]
i=6: 76>72 → ans[5]=1, pop. 76>75 → ans[2]=4, pop. Push 6 → [6]
i=7: 73<76 → push 7 → [6,7]

End: ans = [1,1,4,2,1,1,0,0] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Monotonic decreasing stack for "next greater element to the right."

The distance formula: i - prev_idx
  i = index of the warmer day (current)
  prev_idx = index of the cooler day (popped from stack)
  Difference = days waited

This single-pass O(n) solution beats nested loops O(n²).

Pattern trigger: "next X to the right" → monotonic stack!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

answer = [0] * n
stack = []  # indices, decreasing temperatures
for i in range(n):
    while stack and nums[i] > nums[stack[-1]]:
        idx = stack.pop()
        answer[idx] = i - idx
    stack.append(i)
return answer

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — each index pushed and popped at most once
Space: O(n) — stack

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force nested loops: O(n²) — too slow for large n
2. Storing values in stack instead of INDICES (need index for distance calc!)
3. Not initializing answer with zeros

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC496 — Next Greater Element I (same pattern, simpler version)
LC503 — Next Greater Element II (circular array version)
```

---

## Problem 48/250 — LeetCode #503: Next Greater Element II

**Pattern:** `Monotonic Stack` | **Position in Roadmap:** #48

```
LeetCode 503 — Next Greater Element II
Problem #48 of 250 | Difficulty: Medium | Pattern: Monotonic Stack — Circular Array

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given a CIRCULAR array, find next greater element for each position.
For circular: after the last element, wrap around to the first.

Example:
  nums = [1, 2, 1]
  For 1 (index 0): next greater going right = 2 → [2]
  For 2 (index 1): no greater in [1] going around → [-1]
  For 1 (index 2): wraps to index 0 → 2 is greater → [2]
  Result: [2, -1, 2]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Trick: Simulate circular by doubling the array!
Process indices 0 to 2n-1, using index % n for actual position.

  nums = [1, 2, 1] (n=3)

  Process indices 0,1,2,3,4,5:
  i=0: val=1, push 0
  i=1: val=2, 2>1 → ans[0]=2, pop. push 1
  i=2: val=1, push 2
  i=3: val=nums[0]=1, 1 < 2 → push 3%3=0... wait use % n
  Continue: only record in ans if i < n

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Simulate the circular walk by going TWICE around.
Process indices 0 to 2n-1, but use i%n to get actual value.
Only update answer for i < n (first lap sets the answers).
Second lap just resolves remaining elements that haven't found next greater.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def nextGreaterElements(nums):
    n = len(nums)
    result = [-1] * n
    stack = []   # Stack of indices

    for i in range(2 * n):
        while stack and nums[stack[-1]] < nums[i % n]:
            idx = stack.pop()
            result[idx] = nums[i % n]
        if i < n:
            stack.append(i)  # Only push first lap indices

    return result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums = [1, 2, 1], n=3

result = [-1,-1,-1], stack=[]

i=0: val=1, push 0 → stack=[0]
i=1: val=2, 2>1 → result[0]=2, pop. Stack empty → push 1 → stack=[1]
i=2: val=1, 1<2 → push 2 → stack=[1,2]
i=3: val=nums[0]=1, 1<2 → don't push (i>=n)
i=4: val=nums[1]=2, 2==2 → no. stack==[1,2]
i=5: val=nums[2]=1, 1<2 → no. done

stack=[1,2] → result[1]=-1, result[2]=-1... wait
Actually i=4: nums[4%3]=nums[1]=2. 2>1(stack[-1]=2 means nums[2]=1) → result[2]=2

stack=[1]. i=5: nums[5%3]=nums[2]=1 < nums[1]=2 → no.

End: result=[-1,-1,-1]... let me recheck.
Actually stack stores indices. stack[-1]=2 means nums[2]=1.
i=4: nums[4%3]=nums[1]=2. 2>nums[stack[-1]]=nums[2]=1 → result[2]=2, pop. stack=[1]. 2>nums[1]=2? No.
i=5: nums[2]=1 < nums[1]=2 → no pop.
End: stack=[1] → result[1]=-1.

result = [2, -1, 2] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

The "double array" trick for circular arrays:
  Process 2n indices using i%n for value access.
  Only push to stack during the first n iterations (i < n).
  Second round resolves remaining lookups.

This same doubling trick applies to other circular array problems.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

n = len(nums)
result = [-1] * n
stack = []
for i in range(2*n):
    while stack and nums[stack[-1]] < nums[i%n]:
        result[stack.pop()] = nums[i%n]
    if i < n: stack.append(i)
return result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — process 2n elements, each pushed/popped once
Space: O(n) — stack

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not using i%n for circular access
2. Pushing for i >= n (only need to push in first pass)
3. Confusing when to record answers

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

What if you needed the "next smaller element" in a circular array?
Change the comparison: nums[stack[-1]] > nums[i%n]
```

---

## Problem 49/250 — LeetCode #232: Implement Queue using Stacks

**Pattern:** `Stack / Queue` | **Position in Roadmap:** #49

```
LeetCode 232 — Implement Queue using Stacks
Problem #49 of 250 | Difficulty: Easy | Pattern: Stack — Amortized Push to Back

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Implement a queue (FIFO) using only two stacks.
Operations: push(x), pop(), peek(), empty()

Queue: first in, first out (like a line)
Stack: last in, first out (like a pile)

Example:
  push(1), push(2)
  peek() → 1 (front of queue)
  pop() → 1
  empty() → false

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Two stacks: stack1 (input), stack2 (output).

Push: always push to stack1.
Pop/Peek: if stack2 is empty, pour ALL of stack1 into stack2 (reverses order!).
          Then pop/peek from stack2.

  push 1, push 2, push 3:
  stack1 = [1,2,3], stack2 = []

  pop(): stack2 empty → pour stack1 into stack2
  stack1=[], stack2=[3,2,1] (reversed!)
  stack2.pop() → 1 ✓ (FIFO!)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Two stacks of plates:
  Incoming plates go to Stack1.
  When you need to serve (pop), flip Stack1 into Stack2.
  Now Stack2 has oldest plate on top — serve from Stack2.
  Once Stack2 is empty, flip again.

The pouring reversal makes last-in become last-out — FIFO!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

class MyQueue:
    def __init__(self):
        self.in_stack = []    # For push
        self.out_stack = []   # For pop/peek

    def push(self, x):
        self.in_stack.append(x)

    def _transfer(self):
        if not self.out_stack:    # Only transfer when out_stack is empty
            while self.in_stack:
                self.out_stack.append(self.in_stack.pop())

    def pop(self):
        self._transfer()
        return self.out_stack.pop()

    def peek(self):
        self._transfer()
        return self.out_stack[-1]

    def empty(self):
        return not self.in_stack and not self.out_stack

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

push(1), push(2), push(3)
in_stack=[1,2,3], out_stack=[]

pop():
  out_stack empty → transfer: pop 3,2,1 from in → push to out
  in_stack=[], out_stack=[3,2,1]
  out_stack.pop() → 1 ✓ (FIFO!)

peek():
  out_stack=[3,2], not empty → no transfer
  out_stack[-1] = 2 ✓

empty(): in_stack=[], out_stack=[3,2] → False ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Amortized O(1) for pop/peek!
  Each element is pushed/transferred/popped at most once.
  Total work across all operations = O(n).
  So each operation costs O(1) AMORTIZED.

Key insight: only transfer when out_stack is EMPTY.
Don't transfer for every pop — transfer the whole batch at once.

This teaches amortized analysis: occasional expensive operations
averaged over many cheap operations.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

in_stack, out_stack = [], []
def push(x): in_stack.append(x)
def _fill():
    if not out_stack:
        while in_stack: out_stack.append(in_stack.pop())
def pop(): _fill(); return out_stack.pop()
def peek(): _fill(); return out_stack[-1]
def empty(): return not in_stack and not out_stack

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

push: O(1)
pop/peek: O(1) amortized, O(n) worst case
empty: O(1)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Transferring on EVERY pop (costly — should only when out_stack empty)
2. Not checking out_stack before trying to transfer
3. Forgetting to check both stacks for empty()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC225 — Implement Stack using Queues (reverse challenge!)
Can you implement it with ONE queue instead of two?
```

---

## Problem 50/250 — LeetCode #225: Implement Stack using Queues

**Pattern:** `Queue / Stack` | **Position in Roadmap:** #50

```
LeetCode 225 — Implement Stack using Queues
Problem #50 of 250 | Difficulty: Easy | Pattern: Queue — Rotate for LIFO

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Implement a stack (LIFO) using only one or two queues.
Operations: push(x), pop(), top(), empty()

Stack: last in, first out
Queue: first in, first out

Example:
  push(1), push(2)
  top() → 2 (last pushed)
  pop() → 2
  empty() → false

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

One queue approach:
  On PUSH: push x, then rotate all other elements to put x at FRONT.

  push(1): queue=[1]
  push(2): queue=[1,2] → rotate 1 to back → queue=[2,1]
  pop():   queue.popleft() → 2 ✓ (LIFO behavior!)

  OR:
  On POP: rotate n-1 elements to back, then popleft.
  On PUSH: simple enqueue.

  pop/top are O(n), push is O(1) in this variant.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Queue acts like a line. We want stack behavior (serve from the same end we add).

After pushing x:
  Rotate all previously queued elements to BEHIND x.
  Now x is at the front of the queue (ready to be "popped").

  push(1): q=[1]
  push(2): q=[1,2] then rotate 1 → q=[2,1]
  pop(): q.popleft()=2 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

from collections import deque

class MyStack:
    def __init__(self):
        self.q = deque()

    def push(self, x):
        self.q.append(x)
        # Rotate everything before x to the back
        for _ in range(len(self.q) - 1):
            self.q.append(self.q.popleft())

    def pop(self):
        return self.q.popleft()

    def top(self):
        return self.q[0]

    def empty(self):
        return not self.q

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

push(1): q=[1]
push(2): q=[1,2] → rotate len-1=1 times: popleft 1, append → q=[2,1]
push(3): q=[2,1,3] → rotate 2 times: popleft 2→q=[1,3,2], popleft 1→q=[3,2,1]

top() → q[0] = 3 ✓
pop() → q.popleft() = 3 → q=[2,1]
top() → q[0] = 2 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

The rotation trick transforms FIFO into LIFO:
  After each push, the newest element is at the FRONT (popleft position).
  So popleft() gives us LIFO behavior!

Trade-off: push is O(n), pop/top is O(1).
(Compare to LC232 where push is O(1), pop is amortized O(1).)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

q = deque()
def push(x):
    q.append(x)
    for _ in range(len(q)-1):
        q.append(q.popleft())
def pop(): return q.popleft()
def top(): return q[0]
def empty(): return not q

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

push: O(n) — rotate n-1 elements
pop/top: O(1) — front of queue
empty: O(1)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Confusing this with LC232 (different trade-offs)
2. Not rotating the right number of times (rotate len-1, not len)
3. Using a list instead of deque (popleft on list is O(n)!)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC232 — Implement Queue using Stacks (reverse direction)
What's the trade-off between the two approaches?
When would you prefer O(n) push vs O(n) pop?
```

---

