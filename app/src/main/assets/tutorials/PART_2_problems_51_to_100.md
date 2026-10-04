# 📚 DSA Learning Roadmap — Part 1 | Section 2/5
## Building Blocks (Problems 51-100): Two Pointers, Sliding Window, Sorting

> Study these problems in ORDER. Each one builds on the previous.

---

## Problem 51/250 — LeetCode #724: Find Pivot Index

**Pattern:** `Prefix Sum` | **Position in Roadmap:** #51

```
LeetCode 724 — Find Pivot Index
Problem #51 of 250 | Difficulty: Easy | Pattern: Prefix Sum — Left Sum = Right Sum

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Find the index where the sum of elements to the LEFT equals the sum to the RIGHT.

Example:
  nums = [1, 7, 3, 6, 5, 6]
  Index 3: left=[1,7,3]=11, right=[5,6]=11 → pivot!
  Return 3

Example:
  nums = [1, 2, 3] → no pivot, return -1

Left sum doesn't include pivot itself. Same for right sum.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Total sum = left_sum + pivot + right_sum
When left_sum == right_sum:
  left_sum == (total - pivot - left_sum)
  2 * left_sum + pivot = total
  left_sum = (total - pivot) / 2

Or more simply:
  Scan left to right, maintain left_sum.
  At each position: right_sum = total - left_sum - nums[i]
  If left_sum == right_sum → found pivot!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Balance a seesaw at each position.
Walk left to right, keeping a running left_sum.
At each position i:
  right_sum = total - left_sum - nums[i]
  If left_sum == right_sum → this is the pivot!

No need to recompute right_sum from scratch each time!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def pivotIndex(nums):
    total = sum(nums)
    left_sum = 0

    for i, num in enumerate(nums):
        right_sum = total - left_sum - num
        if left_sum == right_sum:
            return i
        left_sum += num

    return -1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums = [1, 7, 3, 6, 5, 6], total=28

i=0: right=28-0-1=27, left=0≠27, left=1
i=1: right=28-1-7=20, left=1≠20, left=8
i=2: right=28-8-3=17, left=8≠17, left=11
i=3: right=28-11-6=11, left=11==11 → return 3 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Total sum trick:
  right_sum = total - left_sum - current_element
  This gives you right_sum in O(1) without rescanning!

Build prefix sum on the fly, use total to compute suffix in O(1).
This is a classic prefix sum optimization.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

total = sum(nums)
left = 0
for i, num in enumerate(nums):
    if left == total - left - num: return i
    left += num
return -1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — one pass (plus O(n) for sum)
Space: O(1) — two variables

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Including pivot in left or right sum
2. Not computing total first
3. Using nested loops O(n²) — prefix sum trick makes it O(n)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC1991 — Find the Middle Index in Array (same problem, different name)
What if you needed the LAST pivot instead of first?
```

---

## Problem 52/250 — LeetCode #303: Range Sum Query – Immutable

**Pattern:** `Prefix Sum` | **Position in Roadmap:** #52

```
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
```

---

## Problem 53/250 — LeetCode #148: Sort List

**Pattern:** `Merge Sort / Linked List` | **Position in Roadmap:** #53

```
LeetCode 148 — Sort List
Problem #53 of 250 | Difficulty: Medium | Pattern: Merge Sort on Linked List

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Sort a linked list in O(n log n) time using O(1) extra memory.

Example:
  4→2→1→3 → 1→2→3→4
  -1→5→3→4→0 → -1→0→3→4→5

Merge sort is the natural choice for linked lists:
  Finding middle: O(n), Merging two sorted lists: O(n)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Merge Sort steps:
  1. If list has 0 or 1 node → already sorted
  2. Find the middle (split into two halves)
  3. Recursively sort left half
  4. Recursively sort right half
  5. Merge the two sorted halves

  4→2→1→3
  Split: [4→2] and [1→3]
  Sort left: [2→4]
  Sort right: [1→3]
  Merge: 1→2→3→4

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Divide and conquer:
  Split the list in half, sort each half independently,
  then merge the two sorted halves.

This is like how you'd sort a deck of cards:
  Divide pile in half repeatedly,
  then merge sorted piles together.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def sortList(head):
    # Base case: 0 or 1 nodes
    if not head or not head.next:
        return head

    # Step 1: Find middle and split
    slow, fast = head, head.next
    while fast and fast.next:
        slow = slow.next
        fast = fast.next.next
    mid = slow.next
    slow.next = None  # Cut the list in half

    # Step 2: Recursively sort each half
    left = sortList(head)
    right = sortList(mid)

    # Step 3: Merge two sorted halves
    dummy = ListNode(0)
    curr = dummy
    while left and right:
        if left.val <= right.val:
            curr.next = left; left = left.next
        else:
            curr.next = right; right = right.next
        curr = curr.next
    curr.next = left or right
    return dummy.next

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

4→2→1→3

Split: [4→2], [1→3]
Sort [4→2]: split→[4],[2] → merge→[2→4]
Sort [1→3]: split→[1],[3] → merge→[1→3]
Merge [2→4] with [1→3]:
  1<2 → [1], 2<3 → [1,2], 3<4 → [1,2,3], 4 remaining → [1,2,3,4]

Return: 1→2→3→4 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Merge sort on linked list is elegant because:
  Finding middle: fast/slow pointers
  Splitting: just set slow.next = None
  Merging: standard merge two sorted lists

The recursion naturally divides the problem.
Space is O(log n) for the call stack (not O(n) like array merge sort!).

Bottom-up merge sort achieves true O(1) space.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def sortList(head):
    if not head or not head.next: return head
    slow, fast = head, head.next
    while fast and fast.next: slow=slow.next; fast=fast.next.next
    mid = slow.next; slow.next = None
    left = sortList(head); right = sortList(mid)
    # merge left and right...
    dummy = ListNode(0); curr = dummy
    while left and right:
        if left.val <= right.val: curr.next=left; left=left.next
        else: curr.next=right; right=right.next
        curr = curr.next
    curr.next = left or right
    return dummy.next

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n log n) — merge sort
Space: O(log n) — recursion stack depth

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Using fast=head instead of fast=head.next (gives wrong middle)
2. Forgetting slow.next=None (must split the list!)
3. Using arr.sort() — valid but defeats the purpose of the problem

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Bottom-up merge sort: sort pairs, then groups of 4, then 8...
No recursion, truly O(1) space. Can you implement it?
```

---

## Problem 54/250 — LeetCode #169: Majority Element

**Pattern:** `Hashing / Voting` | **Position in Roadmap:** #54

```
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
```

---

## Problem 55/250 — LeetCode #205: Isomorphic Strings

**Pattern:** `Hash Map` | **Position in Roadmap:** #55

```
LeetCode 205 — Isomorphic Strings
Problem #55 of 250 | Difficulty: Easy | Pattern: HashMap — Bidirectional Mapping

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Two strings s and t are isomorphic if characters can be MAPPED one-to-one.
Every occurrence of a char in s maps to the same char in t, and vice versa.

Example:
  s="egg", t="add": e→a, g→d → isomorphic
  s="foo", t="bar": f→b, o→a, o→r → NOT isomorphic (o maps to both a and r!)
  s="paper", t="title": p→t, a→i, p→t, e→l, r→e → isomorphic

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Need two mappings:
  s_to_t: char in s → char in t
  t_to_s: char in t → char in s (to avoid two-to-one)

  s="foo", t="bar"

  i=0: f→b: s_to_t={f:b}, t_to_s={b:f}
  i=1: o→a: s_to_t={f:b,o:a}, t_to_s={b:f,a:o}
  i=2: o→r: s_to_t[o]=a ≠ r → NOT isomorphic!

  (o already mapped to a, but now needs to map to r)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think of it as a cipher/code:
  Each letter in s has exactly one corresponding letter in t.
  No two different letters in s can map to the same letter in t.

You need to track BOTH directions:
  Forward: s_char → t_char (one s letter maps to one t letter)
  Backward: t_char → s_char (no two s letters map to the same t letter)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def isIsomorphic(s, t):
    s_to_t = {}
    t_to_s = {}

    for cs, ct in zip(s, t):
        if cs in s_to_t:
            if s_to_t[cs] != ct:   # s's char already mapped to different t char
                return False
        elif ct in t_to_s:
            if t_to_s[ct] != cs:   # t's char already mapped from different s char
                return False
        else:
            s_to_t[cs] = ct
            t_to_s[ct] = cs

    return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

s="egg", t="add"

e,a: s_to_t={e:a}, t_to_s={a:e}
g,d: s_to_t={e:a,g:d}, t_to_s={a:e,d:g}
g,d: s_to_t[g]=d == d ✓, t_to_s[d]=g == g ✓

Return True ✓

s="foo", t="bar"
f,b: {f:b}, {b:f}
o,a: {f:b,o:a}, {b:f,a:o}
o,r: s_to_t[o]=a ≠ r → return False ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Bidirectional mapping is crucial for isomorphism.
One-directional check misses cases like:
  s="ab", t="aa" → a→a, b→a (two s chars to one t char — NOT valid!)

The two-map approach checks both:
  Does this s char already have a different t mapping?
  Does this t char already have a different s mapping?

Same bidirectional approach applies to LC290 (Word Pattern).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

s_to_t, t_to_s = {}, {}
for cs, ct in zip(s, t):
    if s_to_t.get(cs) not in (None, ct): return False
    if t_to_s.get(ct) not in (None, cs): return False
    s_to_t[cs] = ct; t_to_s[ct] = cs
return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — one pass through both strings
Space: O(1) — at most 26 characters mapped

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. One-directional mapping only (misses many-to-one case)
2. Forgetting to check both directions
3. Confusing "already mapped" vs "not yet mapped" in condition

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC290 — Word Pattern (same idea but with WORDS instead of characters)
s="dog cat cat dog" and pattern="abba"
Same bidirectional mapping, just at word level.
```

---

## Problem 56/250 — LeetCode #290: Word Pattern

**Pattern:** `Hash Map` | **Position in Roadmap:** #56

```
LeetCode 290 — Word Pattern
Problem #56 of 250 | Difficulty: Easy | Pattern: HashMap — Bidirectional Word Mapping

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern 'aabb' and string 'dog dog cat cat'.
Check if pattern characters biject to words.
Needs bidirectional mapping: char→word AND word→char.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: HashMap — Bidirectional Word Mapping

Split string into words. Check len(pattern)==len(words).
Two maps: char_to_word and word_to_char.
For each (char, word): check consistency in both directions.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def wordPattern(pattern, s):
    words = s.split()
    if len(pattern) != len(words): return False
    c_to_w, w_to_c = {}, {}
    for c, w in zip(pattern, words):
        if c in c_to_w and c_to_w[c] != w: return False
        if w in w_to_c and w_to_c[w] != c: return False
        c_to_w[c] = w; w_to_c[w] = c
    return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

pattern='abba', s='dog cat cat dog'
words=['dog','cat','cat','dog']
a,dog: c_to_w={a:dog}, w_to_c={dog:a}
b,cat: c_to_w={a:dog,b:cat}, w_to_c={dog:a,cat:b}
b,cat: b→cat ✓, cat→b ✓
a,dog: a→dog ✓, dog→a ✓
Return True ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Same as Isomorphic Strings but at word level. Bidirectional mapping catches: 'ab'/'aa' (a→a, b→a, but a→? maps to both a and b when reversed).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #56 (Word Pattern):
  Can you apply the HashMap — Bidirectional Word Mapping pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?
```

---

## Problem 57/250 — LeetCode #599: Minimum Index Sum of Two Lists

**Pattern:** `Hash Map` | **Position in Roadmap:** #57

```
LeetCode 599 — Minimum Index Sum of Two Lists
Problem #57 of 250 | Difficulty: Easy | Pattern: HashMap — Index Sum Minimization

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Find common strings with minimum sum of indices.
Store list1 with indices in HashMap. Scan list2, check for common items, track minimum index sum.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: HashMap — Index Sum Minimization

map1 = {word: index} for list1.
min_sum = infinity, result = []
For i, word in list2:
  If word in map1: idx_sum = map1[word]+i
    If idx_sum < min_sum: update result
    If idx_sum == min_sum: append to result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def findRestaurant(list1, list2):
    map1 = {word: i for i, word in enumerate(list1)}
    min_sum = float('inf')
    result = []
    for i, word in enumerate(list2):
        if word in map1:
            s = map1[word] + i
            if s < min_sum: min_sum = s; result = [word]
            elif s == min_sum: result.append(word)
    return result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

list1=['Shogun','Tapioca','Burger']
list2=['Piatti','Shogun','Tapioca']
map1={Shogun:0, Tapioca:1, Burger:2}
i=1,Shogun: sum=0+1=1, min=1, result=[Shogun]
i=2,Tapioca: sum=1+2=3>1, skip
Return ['Shogun'] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

HashMap lookup converts O(n²) brute force to O(m+n). Pattern: index one list, scan the other while checking HashMap.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving #57 (Minimum Index Sum of Two Lists):
  Can you apply the HashMap — Index Sum Minimization pattern to a new problem?
  Can you explain why this approach is O(n) and not O(n²)?
```

---

## Problem 58/250 — LeetCode #387: First Unique Character in a String

**Pattern:** `Hash Map` | **Position in Roadmap:** #58

```
LeetCode 1 — Two Sum (Variant)
Problem #58 of 250 | Difficulty: Easy | Pattern: HashMap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 1: Two Sum (Variant)
Difficulty: Easy | Core Pattern: HashMap

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: HashMap

Use HashMap for O(1) lookup. Common patterns: complement lookup, frequency counting, index tracking.

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Two Sum (Variant):
  The key insight is to use HashMap to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for HashMap pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: HashMap

Use HashMap for O(1) lookup. Common patterns: complement lookup, frequency counting, index tracking.

This is problem #58 of 250 in your roadmap.
It reinforces the HashMap pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# HashMap template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For HashMap patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Two Sum (Variant):
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 59/250 — LeetCode #219: Contains Duplicate II

**Pattern:** `Hash Map` | **Position in Roadmap:** #59

```
LeetCode 219 — Contains Duplicate II
Problem #59 of 250 | Difficulty: Easy | Pattern: Sliding Window + HashMap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 219: Contains Duplicate II
Difficulty: Easy | Core Pattern: Sliding Window + HashMap

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Sliding Window + HashMap

Maintain a window that satisfies a constraint. Expand right, shrink left when constraint violated. Track the window's metric (max length, min size, etc.).

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Contains Duplicate II:
  The key insight is to use Sliding Window + HashMap to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Sliding Window + HashMap pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Sliding Window + HashMap

Maintain a window that satisfies a constraint. Expand right, shrink left when constraint violated. Track the window's metric (max length, min size, etc.).

This is problem #59 of 250 in your roadmap.
It reinforces the Sliding Window + HashMap pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Sliding Window + HashMap template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Sliding Window + HashMap patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Contains Duplicate II:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 60/250 — LeetCode #36: Valid Sudoku

**Pattern:** `Hash Set` | **Position in Roadmap:** #60

```
LeetCode 36 — Valid Sudoku
Problem #60 of 250 | Difficulty: Medium | Pattern: Hash Set per Row/Col/Box

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 36: Valid Sudoku
Difficulty: Medium | Core Pattern: Hash Set per Row/Col/Box

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Hash Set per Row/Col/Box

Identify the key constraint and choose the right data structure.

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Valid Sudoku:
  The key insight is to use Hash Set per Row to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Hash Set per Row pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Hash Set per Row/Col/Box

Identify the key constraint and choose the right data structure.

This is problem #60 of 250 in your roadmap.
It reinforces the Hash Set per Row pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Hash Set per Row/Col/Box template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Hash Set per Row patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Valid Sudoku:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 61/250 — LeetCode #128: Longest Consecutive Sequence

**Pattern:** `Hash Set` | **Position in Roadmap:** #61

```
LeetCode 128 — Longest Consecutive Sequence
Problem #61 of 250 | Difficulty: Medium | Pattern: Hash Set

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 128: Longest Consecutive Sequence
Difficulty: Medium | Core Pattern: Hash Set

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Hash Set

Use HashSet for O(1) membership check. Perfect for 'seen before', 'unique elements', 'intersection/union'.

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Longest Consecutive Sequence:
  The key insight is to use Hash Set to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Hash Set pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Hash Set

Use HashSet for O(1) membership check. Perfect for 'seen before', 'unique elements', 'intersection/union'.

This is problem #61 of 250 in your roadmap.
It reinforces the Hash Set pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Hash Set template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Hash Set patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Longest Consecutive Sequence:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 62/250 — LeetCode #49: Group Anagrams

**Pattern:** `Hash Map` | **Position in Roadmap:** #62

```
LeetCode 49 — Group Anagrams
Problem #62 of 250 | Difficulty: Medium | Pattern: HashMap + Sorting

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 49: Group Anagrams
Difficulty: Medium | Core Pattern: HashMap + Sorting

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: HashMap + Sorting

Use HashMap for O(1) lookup. Common patterns: complement lookup, frequency counting, index tracking.

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Group Anagrams:
  The key insight is to use HashMap + Sorting to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for HashMap + Sorting pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: HashMap + Sorting

Use HashMap for O(1) lookup. Common patterns: complement lookup, frequency counting, index tracking.

This is problem #62 of 250 in your roadmap.
It reinforces the HashMap + Sorting pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# HashMap + Sorting template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For HashMap + Sorting patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Group Anagrams:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 63/250 — LeetCode #347: Top K Frequent Elements

**Pattern:** `Hash Map / Heap` | **Position in Roadmap:** #63

```
LeetCode 347 — Top K Frequent Elements
Problem #63 of 250 | Difficulty: Medium | Pattern: HashMap + Heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 347: Top K Frequent Elements
Difficulty: Medium | Core Pattern: HashMap + Heap

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: HashMap + Heap

Use HashMap for O(1) lookup. Common patterns: complement lookup, frequency counting, index tracking.

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Top K Frequent Elements:
  The key insight is to use HashMap + Heap to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for HashMap + Heap pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: HashMap + Heap

Use HashMap for O(1) lookup. Common patterns: complement lookup, frequency counting, index tracking.

This is problem #63 of 250 in your roadmap.
It reinforces the HashMap + Heap pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# HashMap + Heap template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For HashMap + Heap patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Top K Frequent Elements:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 64/250 — LeetCode #238: Product of Array Except Self

**Pattern:** `Prefix / Suffix` | **Position in Roadmap:** #64

```
LeetCode 238 — Product of Array Except Self
Problem #64 of 250 | Difficulty: Medium | Pattern: Prefix/Suffix

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 238: Product of Array Except Self
Difficulty: Medium | Core Pattern: Prefix/Suffix

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Prefix/Suffix

Identify the key constraint and choose the right data structure.

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Product of Array Except Self:
  The key insight is to use Prefix to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Prefix pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Prefix/Suffix

Identify the key constraint and choose the right data structure.

This is problem #64 of 250 in your roadmap.
It reinforces the Prefix pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Prefix/Suffix template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Prefix patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Product of Array Except Self:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 65/250 — LeetCode #271: Encode and Decode Strings

**Pattern:** `Strings` | **Position in Roadmap:** #65

```
LeetCode 271 — Encode and Decode Strings
Problem #65 of 250 | Difficulty: Medium | Pattern: String Design

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 271: Encode and Decode Strings
Difficulty: Medium | Core Pattern: String Design

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: String Design

Identify the key constraint and choose the right data structure.

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Encode and Decode Strings:
  The key insight is to use String Design to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for String Design pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: String Design

Identify the key constraint and choose the right data structure.

This is problem #65 of 250 in your roadmap.
It reinforces the String Design pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# String Design template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For String Design patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Encode and Decode Strings:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 66/250 — LeetCode #271: Encode and Decode Strings

**Pattern:** `String Design` | **Position in Roadmap:** #66

```
LeetCode 271 — Encode and Decode Strings
Problem #66 of 250 | Difficulty: Medium | Pattern: String Design

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 271: Encode and Decode Strings
Difficulty: Medium | Core Pattern: String Design

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: String Design

Identify the key constraint and choose the right data structure.

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Encode and Decode Strings:
  The key insight is to use String Design to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for String Design pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: String Design

Identify the key constraint and choose the right data structure.

This is problem #66 of 250 in your roadmap.
It reinforces the String Design pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# String Design template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For String Design patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Encode and Decode Strings:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 67/250 — LeetCode #560: Subarray Sum Equals K

**Pattern:** `Prefix Sum / Hash Map` | **Position in Roadmap:** #67

```
LeetCode 560 — Subarray Sum Equals K
Problem #67 of 250 | Difficulty: Medium | Pattern: Prefix Sum + HashMap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 560: Subarray Sum Equals K
Difficulty: Medium | Core Pattern: Prefix Sum + HashMap

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Prefix Sum + HashMap

prefix[i] = sum of elements 0..i-1. Query sum(l,r) = prefix[r+1]-prefix[l] in O(1).

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Subarray Sum Equals K:
  The key insight is to use Prefix Sum + HashMap to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Prefix Sum + HashMap pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Prefix Sum + HashMap

prefix[i] = sum of elements 0..i-1. Query sum(l,r) = prefix[r+1]-prefix[l] in O(1).

This is problem #67 of 250 in your roadmap.
It reinforces the Prefix Sum + HashMap pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Prefix Sum + HashMap template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Prefix Sum + HashMap patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Subarray Sum Equals K:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 68/250 — LeetCode #525: Contiguous Array

**Pattern:** `Prefix Sum` | **Position in Roadmap:** #68

```
LeetCode 525 — Contiguous Array
Problem #68 of 250 | Difficulty: Medium | Pattern: Prefix Sum

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 525: Contiguous Array
Difficulty: Medium | Core Pattern: Prefix Sum

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Prefix Sum

prefix[i] = sum of elements 0..i-1. Query sum(l,r) = prefix[r+1]-prefix[l] in O(1).

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Contiguous Array:
  The key insight is to use Prefix Sum to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Prefix Sum pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Prefix Sum

prefix[i] = sum of elements 0..i-1. Query sum(l,r) = prefix[r+1]-prefix[l] in O(1).

This is problem #68 of 250 in your roadmap.
It reinforces the Prefix Sum pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Prefix Sum template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Prefix Sum patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Contiguous Array:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 69/250 — LeetCode #974: Subarray Sums Divisible by K

**Pattern:** `Prefix Sum` | **Position in Roadmap:** #69

```
LeetCode 974 — Subarray Sums Divisible by K
Problem #69 of 250 | Difficulty: Medium | Pattern: Prefix Sum

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 974: Subarray Sums Divisible by K
Difficulty: Medium | Core Pattern: Prefix Sum

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Prefix Sum

prefix[i] = sum of elements 0..i-1. Query sum(l,r) = prefix[r+1]-prefix[l] in O(1).

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Subarray Sums Divisible by K:
  The key insight is to use Prefix Sum to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Prefix Sum pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Prefix Sum

prefix[i] = sum of elements 0..i-1. Query sum(l,r) = prefix[r+1]-prefix[l] in O(1).

This is problem #69 of 250 in your roadmap.
It reinforces the Prefix Sum pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Prefix Sum template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Prefix Sum patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Subarray Sums Divisible by K:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 70/250 — LeetCode #523: Continuous Subarray Sum

**Pattern:** `Prefix Sum` | **Position in Roadmap:** #70

```
LeetCode 523 — Continuous Subarray Sum
Problem #70 of 250 | Difficulty: Medium | Pattern: Prefix Sum + HashMap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 523: Continuous Subarray Sum
Difficulty: Medium | Core Pattern: Prefix Sum + HashMap

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Prefix Sum + HashMap

prefix[i] = sum of elements 0..i-1. Query sum(l,r) = prefix[r+1]-prefix[l] in O(1).

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Continuous Subarray Sum:
  The key insight is to use Prefix Sum + HashMap to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Prefix Sum + HashMap pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Prefix Sum + HashMap

prefix[i] = sum of elements 0..i-1. Query sum(l,r) = prefix[r+1]-prefix[l] in O(1).

This is problem #70 of 250 in your roadmap.
It reinforces the Prefix Sum + HashMap pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Prefix Sum + HashMap template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Prefix Sum + HashMap patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Continuous Subarray Sum:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 71/250 — LeetCode #14: Longest Common Prefix

**Pattern:** `String` | **Position in Roadmap:** #71

```
LeetCode 14 — Longest Common Prefix
Problem #71 of 250 | Difficulty: Easy | Pattern: String

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 14: Longest Common Prefix
Difficulty: Easy | Core Pattern: String

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: String

String manipulation: split, strip, traverse from end, character checking.

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Longest Common Prefix:
  The key insight is to use String to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for String pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: String

String manipulation: split, strip, traverse from end, character checking.

This is problem #71 of 250 in your roadmap.
It reinforces the String pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# String template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For String patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Longest Common Prefix:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 72/250 — LeetCode #28: Find the Index of the First Occurrence in a String

**Pattern:** `String` | **Position in Roadmap:** #72

```
LeetCode 28 — Find the Index of the First Occurrence
Problem #72 of 250 | Difficulty: Easy | Pattern: String / KMP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 28: Find the Index of the First Occurrence
Difficulty: Easy | Core Pattern: String / KMP

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: String / KMP

String manipulation: split, strip, traverse from end, character checking.

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Find the Index of the First Occurrence:
  The key insight is to use String  to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for String  pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: String / KMP

String manipulation: split, strip, traverse from end, character checking.

This is problem #72 of 250 in your roadmap.
It reinforces the String  pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# String / KMP template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For String  patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Find the Index of the First Occurrence:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 73/250 — LeetCode #58: Length of Last Word

**Pattern:** `String` | **Position in Roadmap:** #73

```
LeetCode 58 — Length of Last Word
Problem #73 of 250 | Difficulty: Easy | Pattern: String

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 58: Length of Last Word
Difficulty: Easy | Core Pattern: String

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: String

String manipulation: split, strip, traverse from end, character checking.

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Length of Last Word:
  The key insight is to use String to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for String pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: String

String manipulation: split, strip, traverse from end, character checking.

This is problem #73 of 250 in your roadmap.
It reinforces the String pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# String template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For String patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Length of Last Word:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 74/250 — LeetCode #387: First Unique Character in a String

**Pattern:** `Hash Map` | **Position in Roadmap:** #74

```
LeetCode 387 — First Unique Character in a String
Problem #74 of 250 | Difficulty: Easy | Pattern: HashMap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 387: First Unique Character in a String
Difficulty: Easy | Core Pattern: HashMap

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: HashMap

Use HashMap for O(1) lookup. Common patterns: complement lookup, frequency counting, index tracking.

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For First Unique Character in a String:
  The key insight is to use HashMap to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for HashMap pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: HashMap

Use HashMap for O(1) lookup. Common patterns: complement lookup, frequency counting, index tracking.

This is problem #74 of 250 in your roadmap.
It reinforces the HashMap pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# HashMap template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For HashMap patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving First Unique Character in a String:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 75/250 — LeetCode #383: Ransom Note

**Pattern:** `Hash Map` | **Position in Roadmap:** #75

```
LeetCode 383 — Ransom Note
Problem #75 of 250 | Difficulty: Easy | Pattern: HashMap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 383: Ransom Note
Difficulty: Easy | Core Pattern: HashMap

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: HashMap

Use HashMap for O(1) lookup. Common patterns: complement lookup, frequency counting, index tracking.

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Ransom Note:
  The key insight is to use HashMap to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for HashMap pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: HashMap

Use HashMap for O(1) lookup. Common patterns: complement lookup, frequency counting, index tracking.

This is problem #75 of 250 in your roadmap.
It reinforces the HashMap pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# HashMap template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For HashMap patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Ransom Note:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 76/250 — LeetCode #205: Isomorphic Strings

**Pattern:** `Hash Map` | **Position in Roadmap:** #76

```
LeetCode 205 — Isomorphic Strings
Problem #76 of 250 | Difficulty: Easy | Pattern: HashMap Bidirectional

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 205: Isomorphic Strings
Difficulty: Easy | Core Pattern: HashMap Bidirectional

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: HashMap Bidirectional

Identify the key constraint and choose the right data structure.

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Isomorphic Strings:
  The key insight is to use HashMap Bidirectional to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for HashMap Bidirectional pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: HashMap Bidirectional

Identify the key constraint and choose the right data structure.

This is problem #76 of 250 in your roadmap.
It reinforces the HashMap Bidirectional pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# HashMap Bidirectional template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For HashMap Bidirectional patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Isomorphic Strings:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 77/250 — LeetCode #290: Word Pattern

**Pattern:** `Hash Map` | **Position in Roadmap:** #77

```
LeetCode 290 — Word Pattern
Problem #77 of 250 | Difficulty: Easy | Pattern: HashMap Bidirectional

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 290: Word Pattern
Difficulty: Easy | Core Pattern: HashMap Bidirectional

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: HashMap Bidirectional

Identify the key constraint and choose the right data structure.

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Word Pattern:
  The key insight is to use HashMap Bidirectional to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for HashMap Bidirectional pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: HashMap Bidirectional

Identify the key constraint and choose the right data structure.

This is problem #77 of 250 in your roadmap.
It reinforces the HashMap Bidirectional pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# HashMap Bidirectional template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For HashMap Bidirectional patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Word Pattern:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 78/250 — LeetCode #409: Longest Palindrome

**Pattern:** `Hash Map` | **Position in Roadmap:** #78

```
LeetCode 409 — Longest Palindrome
Problem #78 of 250 | Difficulty: Easy | Pattern: Frequency Count

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 409: Longest Palindrome
Difficulty: Easy | Core Pattern: Frequency Count

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Frequency Count

Count character frequencies using a HashMap/Counter. Key insight: anagrams/valid structures have specific frequency requirements.

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Longest Palindrome:
  The key insight is to use Frequency Count to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Frequency Count pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Frequency Count

Count character frequencies using a HashMap/Counter. Key insight: anagrams/valid structures have specific frequency requirements.

This is problem #78 of 250 in your roadmap.
It reinforces the Frequency Count pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Frequency Count template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Frequency Count patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Longest Palindrome:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 79/250 — LeetCode #345: Reverse Vowels of a String

**Pattern:** `Two Pointers` | **Position in Roadmap:** #79

```
LeetCode 345 — Reverse Vowels of a String
Problem #79 of 250 | Difficulty: Easy | Pattern: Two Pointers

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 345: Reverse Vowels of a String
Difficulty: Easy | Core Pattern: Two Pointers

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Two Pointers

Two pointers converge from both ends or move at different speeds. Eliminates nested loops.

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Reverse Vowels of a String:
  The key insight is to use Two Pointers to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Two Pointers pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Two Pointers

Two pointers converge from both ends or move at different speeds. Eliminates nested loops.

This is problem #79 of 250 in your roadmap.
It reinforces the Two Pointers pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Two Pointers template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Two Pointers patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Reverse Vowels of a String:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 80/250 — LeetCode #151: Reverse Words in a String

**Pattern:** `String` | **Position in Roadmap:** #80

```
LeetCode 151 — Reverse Words in a String
Problem #80 of 250 | Difficulty: Medium | Pattern: String

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 151: Reverse Words in a String
Difficulty: Medium | Core Pattern: String

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: String

String manipulation: split, strip, traverse from end, character checking.

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Reverse Words in a String:
  The key insight is to use String to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for String pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: String

String manipulation: split, strip, traverse from end, character checking.

This is problem #80 of 250 in your roadmap.
It reinforces the String pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# String template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For String patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Reverse Words in a String:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 81/250 — LeetCode #443: String Compression

**Pattern:** `Two Pointers` | **Position in Roadmap:** #81

```
LeetCode 443 — String Compression
Problem #81 of 250 | Difficulty: Medium | Pattern: Two Pointers

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 443: String Compression
Difficulty: Medium | Core Pattern: Two Pointers

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Two Pointers

Two pointers converge from both ends or move at different speeds. Eliminates nested loops.

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For String Compression:
  The key insight is to use Two Pointers to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Two Pointers pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Two Pointers

Two pointers converge from both ends or move at different speeds. Eliminates nested loops.

This is problem #81 of 250 in your roadmap.
It reinforces the Two Pointers pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Two Pointers template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Two Pointers patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving String Compression:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 82/250 — LeetCode #3: Longest Substring Without Repeating Characters

**Pattern:** `Sliding Window` | **Position in Roadmap:** #82

```
LeetCode 3 — Longest Substring Without Repeating Characters
Problem #82 of 250 | Difficulty: Medium | Pattern: Sliding Window

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 3: Longest Substring Without Repeating Characters
Difficulty: Medium | Core Pattern: Sliding Window

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Sliding Window

Maintain a window that satisfies a constraint. Expand right, shrink left when constraint violated. Track the window's metric (max length, min size, etc.).

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Longest Substring Without Repeating Characters:
  The key insight is to use Sliding Window to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Sliding Window pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Sliding Window

Maintain a window that satisfies a constraint. Expand right, shrink left when constraint violated. Track the window's metric (max length, min size, etc.).

This is problem #82 of 250 in your roadmap.
It reinforces the Sliding Window pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Sliding Window template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Sliding Window patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Longest Substring Without Repeating Characters:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 83/250 — LeetCode #424: Longest Repeating Character Replacement

**Pattern:** `Sliding Window` | **Position in Roadmap:** #83

```
LeetCode 424 — Longest Repeating Character Replacement
Problem #83 of 250 | Difficulty: Medium | Pattern: Sliding Window

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 424: Longest Repeating Character Replacement
Difficulty: Medium | Core Pattern: Sliding Window

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Sliding Window

Maintain a window that satisfies a constraint. Expand right, shrink left when constraint violated. Track the window's metric (max length, min size, etc.).

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Longest Repeating Character Replacement:
  The key insight is to use Sliding Window to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Sliding Window pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Sliding Window

Maintain a window that satisfies a constraint. Expand right, shrink left when constraint violated. Track the window's metric (max length, min size, etc.).

This is problem #83 of 250 in your roadmap.
It reinforces the Sliding Window pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Sliding Window template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Sliding Window patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Longest Repeating Character Replacement:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 84/250 — LeetCode #567: Permutation in String

**Pattern:** `Sliding Window` | **Position in Roadmap:** #84

```
LeetCode 567 — Permutation in String
Problem #84 of 250 | Difficulty: Medium | Pattern: Sliding Window

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 567: Permutation in String
Difficulty: Medium | Core Pattern: Sliding Window

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Sliding Window

Maintain a window that satisfies a constraint. Expand right, shrink left when constraint violated. Track the window's metric (max length, min size, etc.).

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Permutation in String:
  The key insight is to use Sliding Window to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Sliding Window pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Sliding Window

Maintain a window that satisfies a constraint. Expand right, shrink left when constraint violated. Track the window's metric (max length, min size, etc.).

This is problem #84 of 250 in your roadmap.
It reinforces the Sliding Window pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Sliding Window template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Sliding Window patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Permutation in String:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 85/250 — LeetCode #438: Find All Anagrams in a String

**Pattern:** `Sliding Window` | **Position in Roadmap:** #85

```
LeetCode 438 — Find All Anagrams in a String
Problem #85 of 250 | Difficulty: Medium | Pattern: Sliding Window

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 438: Find All Anagrams in a String
Difficulty: Medium | Core Pattern: Sliding Window

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Sliding Window

Maintain a window that satisfies a constraint. Expand right, shrink left when constraint violated. Track the window's metric (max length, min size, etc.).

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Find All Anagrams in a String:
  The key insight is to use Sliding Window to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Sliding Window pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Sliding Window

Maintain a window that satisfies a constraint. Expand right, shrink left when constraint violated. Track the window's metric (max length, min size, etc.).

This is problem #85 of 250 in your roadmap.
It reinforces the Sliding Window pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Sliding Window template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Sliding Window patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Find All Anagrams in a String:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 86/250 — LeetCode #76: Minimum Window Substring

**Pattern:** `Sliding Window` | **Position in Roadmap:** #86

```
LeetCode 76 — Minimum Window Substring
Problem #86 of 250 | Difficulty: Hard | Pattern: Sliding Window

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 76: Minimum Window Substring
Difficulty: Hard | Core Pattern: Sliding Window

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Sliding Window

Maintain a window that satisfies a constraint. Expand right, shrink left when constraint violated. Track the window's metric (max length, min size, etc.).

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Minimum Window Substring:
  The key insight is to use Sliding Window to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Sliding Window pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Sliding Window

Maintain a window that satisfies a constraint. Expand right, shrink left when constraint violated. Track the window's metric (max length, min size, etc.).

This is problem #86 of 250 in your roadmap.
It reinforces the Sliding Window pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Sliding Window template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Sliding Window patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Minimum Window Substring:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 87/250 — LeetCode #5: Longest Palindromic Substring

**Pattern:** `Two Pointers / DP` | **Position in Roadmap:** #87

```
LeetCode 5 — Longest Palindromic Substring
Problem #87 of 250 | Difficulty: Medium | Pattern: Expand Around Center

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 5: Longest Palindromic Substring
Difficulty: Medium | Core Pattern: Expand Around Center

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Expand Around Center

For palindromes, expand from each center position (including between characters for even length).

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Longest Palindromic Substring:
  The key insight is to use Expand Around Center to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Expand Around Center pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Expand Around Center

For palindromes, expand from each center position (including between characters for even length).

This is problem #87 of 250 in your roadmap.
It reinforces the Expand Around Center pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Expand Around Center template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Expand Around Center patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Longest Palindromic Substring:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 88/250 — LeetCode #647: Palindromic Substrings

**Pattern:** `Two Pointers / DP` | **Position in Roadmap:** #88

```
LeetCode 647 — Palindromic Substrings
Problem #88 of 250 | Difficulty: Medium | Pattern: Expand Around Center

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 647: Palindromic Substrings
Difficulty: Medium | Core Pattern: Expand Around Center

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Expand Around Center

For palindromes, expand from each center position (including between characters for even length).

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Palindromic Substrings:
  The key insight is to use Expand Around Center to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Expand Around Center pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Expand Around Center

For palindromes, expand from each center position (including between characters for even length).

This is problem #88 of 250 in your roadmap.
It reinforces the Expand Around Center pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Expand Around Center template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Expand Around Center patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Palindromic Substrings:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 89/250 — LeetCode #516: Longest Palindromic Subsequence

**Pattern:** `Dynamic Programming` | **Position in Roadmap:** #89

```
LeetCode 516 — Longest Palindromic Subsequence
Problem #89 of 250 | Difficulty: Medium | Pattern: 2D DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 516: Longest Palindromic Subsequence
Difficulty: Medium | Core Pattern: 2D DP

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: 2D DP

dp[i][j] depends on dp[i-1][j], dp[i][j-1], dp[i-1][j-1]. Build table bottom-up.

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Longest Palindromic Subsequence:
  The key insight is to use 2D DP to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for 2D DP pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 2D DP

dp[i][j] depends on dp[i-1][j], dp[i][j-1], dp[i-1][j-1]. Build table bottom-up.

This is problem #89 of 250 in your roadmap.
It reinforces the 2D DP pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# 2D DP template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For 2D DP patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Longest Palindromic Subsequence:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 90/250 — LeetCode #125: Valid Palindrome

**Pattern:** `Two Pointers` | **Position in Roadmap:** #90

```
LeetCode 125 — Valid Palindrome
Problem #90 of 250 | Difficulty: Easy | Pattern: Two Pointers

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 125: Valid Palindrome
Difficulty: Easy | Core Pattern: Two Pointers

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Two Pointers

Two pointers converge from both ends or move at different speeds. Eliminates nested loops.

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Valid Palindrome:
  The key insight is to use Two Pointers to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Two Pointers pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Two Pointers

Two pointers converge from both ends or move at different speeds. Eliminates nested loops.

This is problem #90 of 250 in your roadmap.
It reinforces the Two Pointers pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Two Pointers template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Two Pointers patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Valid Palindrome:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 91/250 — LeetCode #680: Valid Palindrome II

**Pattern:** `Two Pointers` | **Position in Roadmap:** #91

```
LeetCode 680 — Valid Palindrome II
Problem #91 of 250 | Difficulty: Easy | Pattern: Two Pointers

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 680: Valid Palindrome II
Difficulty: Easy | Core Pattern: Two Pointers

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Two Pointers

Two pointers converge from both ends or move at different speeds. Eliminates nested loops.

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Valid Palindrome II:
  The key insight is to use Two Pointers to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Two Pointers pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Two Pointers

Two pointers converge from both ends or move at different speeds. Eliminates nested loops.

This is problem #91 of 250 in your roadmap.
It reinforces the Two Pointers pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Two Pointers template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Two Pointers patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Valid Palindrome II:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 92/250 — LeetCode #643: Maximum Average Subarray I

**Pattern:** `Sliding Window` | **Position in Roadmap:** #92

```
LeetCode 643 — Maximum Average Subarray I
Problem #92 of 250 | Difficulty: Easy | Pattern: Fixed Sliding Window

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 643: Maximum Average Subarray I
Difficulty: Easy | Core Pattern: Fixed Sliding Window

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Fixed Sliding Window

Window size is FIXED. Slide one step at a time. Add new element, remove old element, update answer.

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Maximum Average Subarray I:
  The key insight is to use Fixed Sliding Window to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Fixed Sliding Window pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Fixed Sliding Window

Window size is FIXED. Slide one step at a time. Add new element, remove old element, update answer.

This is problem #92 of 250 in your roadmap.
It reinforces the Fixed Sliding Window pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Fixed Sliding Window template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Fixed Sliding Window patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Maximum Average Subarray I:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 93/250 — LeetCode #1456: Maximum Number of Vowels in a Substring of Given Length

**Pattern:** `Sliding Window` | **Position in Roadmap:** #93

```
LeetCode 1456 — Maximum Number of Vowels in a Substring
Problem #93 of 250 | Difficulty: Medium | Pattern: Fixed Sliding Window

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 1456: Maximum Number of Vowels in a Substring
Difficulty: Medium | Core Pattern: Fixed Sliding Window

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Fixed Sliding Window

Window size is FIXED. Slide one step at a time. Add new element, remove old element, update answer.

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Maximum Number of Vowels in a Substring:
  The key insight is to use Fixed Sliding Window to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Fixed Sliding Window pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Fixed Sliding Window

Window size is FIXED. Slide one step at a time. Add new element, remove old element, update answer.

This is problem #93 of 250 in your roadmap.
It reinforces the Fixed Sliding Window pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Fixed Sliding Window template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Fixed Sliding Window patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Maximum Number of Vowels in a Substring:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 94/250 — LeetCode #209: Minimum Size Subarray Sum

**Pattern:** `Sliding Window` | **Position in Roadmap:** #94

```
LeetCode 209 — Minimum Size Subarray Sum
Problem #94 of 250 | Difficulty: Medium | Pattern: Variable Sliding Window

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 209: Minimum Size Subarray Sum
Difficulty: Medium | Core Pattern: Variable Sliding Window

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Variable Sliding Window

Window size varies. Expand right always. Shrink left until constraint is satisfied. Track optimal window.

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Minimum Size Subarray Sum:
  The key insight is to use Variable Sliding Window to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Variable Sliding Window pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Variable Sliding Window

Window size varies. Expand right always. Shrink left until constraint is satisfied. Track optimal window.

This is problem #94 of 250 in your roadmap.
It reinforces the Variable Sliding Window pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Variable Sliding Window template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Variable Sliding Window patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Minimum Size Subarray Sum:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 95/250 — LeetCode #1004: Max Consecutive Ones III

**Pattern:** `Sliding Window` | **Position in Roadmap:** #95

```
LeetCode 1004 — Max Consecutive Ones III
Problem #95 of 250 | Difficulty: Medium | Pattern: Sliding Window

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 1004: Max Consecutive Ones III
Difficulty: Medium | Core Pattern: Sliding Window

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Sliding Window

Maintain a window that satisfies a constraint. Expand right, shrink left when constraint violated. Track the window's metric (max length, min size, etc.).

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Max Consecutive Ones III:
  The key insight is to use Sliding Window to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Sliding Window pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Sliding Window

Maintain a window that satisfies a constraint. Expand right, shrink left when constraint violated. Track the window's metric (max length, min size, etc.).

This is problem #95 of 250 in your roadmap.
It reinforces the Sliding Window pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Sliding Window template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Sliding Window patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Max Consecutive Ones III:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 96/250 — LeetCode #1493: Longest Subarray of 1's After Deleting One Element

**Pattern:** `Sliding Window` | **Position in Roadmap:** #96

```
LeetCode 1493 — Longest Subarray of 1's After Deleting One Element
Problem #96 of 250 | Difficulty: Medium | Pattern: Sliding Window

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 1493: Longest Subarray of 1's After Deleting One Element
Difficulty: Medium | Core Pattern: Sliding Window

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Sliding Window

Maintain a window that satisfies a constraint. Expand right, shrink left when constraint violated. Track the window's metric (max length, min size, etc.).

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Longest Subarray of 1's After Deleting One Element:
  The key insight is to use Sliding Window to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Sliding Window pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Sliding Window

Maintain a window that satisfies a constraint. Expand right, shrink left when constraint violated. Track the window's metric (max length, min size, etc.).

This is problem #96 of 250 in your roadmap.
It reinforces the Sliding Window pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Sliding Window template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Sliding Window patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Longest Subarray of 1's After Deleting One Element:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 97/250 — LeetCode #904: Fruit Into Baskets

**Pattern:** `Sliding Window` | **Position in Roadmap:** #97

```
LeetCode 904 — Fruit Into Baskets
Problem #97 of 250 | Difficulty: Medium | Pattern: Sliding Window

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 904: Fruit Into Baskets
Difficulty: Medium | Core Pattern: Sliding Window

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Sliding Window

Maintain a window that satisfies a constraint. Expand right, shrink left when constraint violated. Track the window's metric (max length, min size, etc.).

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Fruit Into Baskets:
  The key insight is to use Sliding Window to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Sliding Window pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Sliding Window

Maintain a window that satisfies a constraint. Expand right, shrink left when constraint violated. Track the window's metric (max length, min size, etc.).

This is problem #97 of 250 in your roadmap.
It reinforces the Sliding Window pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Sliding Window template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Sliding Window patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Fruit Into Baskets:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 98/250 — LeetCode #1695: Maximum Erasure Value

**Pattern:** `Sliding Window` | **Position in Roadmap:** #98

```
LeetCode 1695 — Maximum Erasure Value
Problem #98 of 250 | Difficulty: Medium | Pattern: Sliding Window

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 1695: Maximum Erasure Value
Difficulty: Medium | Core Pattern: Sliding Window

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Sliding Window

Maintain a window that satisfies a constraint. Expand right, shrink left when constraint violated. Track the window's metric (max length, min size, etc.).

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Maximum Erasure Value:
  The key insight is to use Sliding Window to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Sliding Window pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Sliding Window

Maintain a window that satisfies a constraint. Expand right, shrink left when constraint violated. Track the window's metric (max length, min size, etc.).

This is problem #98 of 250 in your roadmap.
It reinforces the Sliding Window pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Sliding Window template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Sliding Window patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Maximum Erasure Value:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 99/250 — LeetCode #1208: Get Equal Substrings Within Budget

**Pattern:** `Sliding Window` | **Position in Roadmap:** #99

```
LeetCode 1208 — Get Equal Substrings Within Budget
Problem #99 of 250 | Difficulty: Medium | Pattern: Sliding Window

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 1208: Get Equal Substrings Within Budget
Difficulty: Medium | Core Pattern: Sliding Window

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Sliding Window

Maintain a window that satisfies a constraint. Expand right, shrink left when constraint violated. Track the window's metric (max length, min size, etc.).

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Get Equal Substrings Within Budget:
  The key insight is to use Sliding Window to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Sliding Window pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Sliding Window

Maintain a window that satisfies a constraint. Expand right, shrink left when constraint violated. Track the window's metric (max length, min size, etc.).

This is problem #99 of 250 in your roadmap.
It reinforces the Sliding Window pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Sliding Window template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Sliding Window patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Get Equal Substrings Within Budget:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

## Problem 100/250 — LeetCode #1423: Maximum Points You Can Obtain from Cards

**Pattern:** `Sliding Window` | **Position in Roadmap:** #100

```
LeetCode 1423 — Maximum Points You Can Obtain from Cards
Problem #100 of 250 | Difficulty: Medium | Pattern: Sliding Window

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This is LeetCode problem 1423: Maximum Points You Can Obtain from Cards
Difficulty: Medium | Core Pattern: Sliding Window

Look up this problem on LeetCode to see the exact constraints and examples.
Your job: understand what's being asked BEFORE looking at any solution.

Ask yourself these questions:
  Q1 - What is the INPUT? (What data am I given?)
  Q2 - What is the OUTPUT? (What should I return?)
  Q3 - What TRANSFORMS input to output? (The algorithm)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern Trigger: Sliding Window

Maintain a window that satisfies a constraint. Expand right, shrink left when constraint violated. Track the window's metric (max length, min size, etc.).

When you see problems asking about:
  - Substrings with unique chars → Sliding Window + HashSet
  - Subarrays summing to K → Prefix Sum + HashMap
  - Anagram checking → Frequency Count comparison
  - Next/Previous larger element → Monotonic Stack
  - Paths in grid → BFS/DFS

Always ask: "Have I seen a similar PATTERN before?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing any code:
  1. Write down 2-3 example inputs and compute outputs BY HAND
  2. Look for the PATTERN in how you computed it manually
  3. Can you describe the algorithm in plain English?
  4. What data structure would help? Why?

For Maximum Points You Can Obtain from Cards:
  The key insight is to use Sliding Window to avoid recomputing.
  Think about what you need to REMEMBER as you scan through the data.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

General approach for Sliding Window pattern:

  # Initialize your data structure
  # Scan through the input
  # At each step: update your data structure AND check/update answer
  # Return the answer

The key variables to track:
  - What does your current "state" need to remember?
  - What is the "answer" variable you update at each step?
  - What are your base cases?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Always dry-run with a simple example first!
For this problem:
  Take the first example from LeetCode
  Trace through your algorithm step by step
  Write the state of ALL variables at each step
  Verify the output matches the expected answer

If it doesn't match → find where the logic diverges and fix it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Sliding Window

Maintain a window that satisfies a constraint. Expand right, shrink left when constraint violated. Track the window's metric (max length, min size, etc.).

This is problem #100 of 250 in your roadmap.
It reinforces the Sliding Window pattern you're building toward mastery.

When you SEE this pattern trigger in future problems → APPLY this solution!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Sliding Window template:
# 1. Initialize
# 2. Scan input
# 3. Update state
# 4. Check answer condition
# 5. Return result

Practice: Can you write the solution from memory in 5 minutes?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think through:
  Time complexity: How many times does each element get processed?
  Space complexity: What extra data structures am I using?

For Sliding Window patterns:
  Typically O(n) time (one pass)
  O(n) or O(1) space depending on data structure size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Brute force first (nested loops) — look for the pattern!
2. Off-by-one errors in window boundaries
3. Not handling edge cases (empty input, single element, all same)
4. Forgetting to reset/update state correctly
5. Wrong data structure choice — think carefully about what operations you need

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Maximum Points You Can Obtain from Cards:
  Can you solve the next problem in your roadmap with the same pattern?
  Can you explain the solution to someone else in plain English?
  Can you identify when this pattern applies to new problems?

Pattern recognition is the key skill you're building!
```

---

