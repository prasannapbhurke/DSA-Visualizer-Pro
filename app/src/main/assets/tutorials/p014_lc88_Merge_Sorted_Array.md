# 📘 Problem 14 of 250 — LeetCode 88: Merge Sorted Array

> **Pattern:** Two Pointers | **Roadmap Position:** #14/250

---

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

---

*Next up: Problem 15 of 250*
