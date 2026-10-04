# 📘 Problem 187 of 250 — LeetCode 703: Kth Largest Element in a Stream

> **Pattern:** Heap | **Roadmap Position:** #187/250

---

LeetCode 703 — Kth Largest Element in a Stream
Problem #187 of 250 | Difficulty: Easy | Pattern: Min Heap of size k

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 703: Kth Largest Element in a Stream | Easy
Read carefully. Identify: what K-th element or K-element set are you finding?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Min Heap of size k

HEAP INSIGHT:
A MIN-HEAP of size k maintains the k LARGEST elements seen so far.
  - If you want k largest: use min-heap (top = smallest of the k largest)
  - If you want k smallest: use max-heap (top = largest of the k smallest)

For "kth largest": maintain min-heap of size k.
  The minimum of the k largest = kth largest!

In Python, heapq is a MIN-heap by default.
To simulate MAX-heap: negate values (push -x, peek -heap[0]).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Kth Largest Element in a Stream":
  What are you trying to RANK? Values? Frequencies? Distances?
  Do you want the TOP K or BOTTOM K?
  What property determines the ordering?

Heap gives you O(log k) insertion and O(1) top access.
This beats sorting (O(n log n)) when k << n.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

import heapq

# For k largest elements (min-heap of size k):
heap = []
for num in nums:
    heapq.heappush(heap, num)
    if len(heap) > k:
        heapq.heappop(heap)  # remove smallest
# heap now contains k largest, heap[0] = kth largest

# For k smallest elements (max-heap of size k):
heap = []
for num in nums:
    heapq.heappush(heap, -num)  # negate for max-heap
    if len(heap) > k:
        heapq.heappop(heap)  # remove largest (most negative = least actual)
# -heap[0] = kth smallest

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Apply your algorithm to the first example on LeetCode.
Track the heap contents at each insertion step.
Verify heap[0] gives the correct answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Heap is your go-to for:
  - "k-th largest/smallest"
  - "top k elements"
  - "merge k sorted lists"
  - "running median" (two heaps!)

Time: O(n log k) — much better than O(n log n) sorting when k is small!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

import heapq
heap = []
for x in data:
    heapq.heappush(heap, x)  # or -x for max-heap
    if len(heap) > k:
        heapq.heappop(heap)
return heap[0]  # or -heap[0] for max-heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n log k) for building heap of size k
Space: O(k) for the heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Using a max-heap when min-heap is needed (or vice versa)
2. Forgetting to negate for max-heap in Python
3. Not maintaining heap size at exactly k
4. Confusing heapq.heappush with heapq.heapreplace

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Can you identify whether this problem needs a min-heap or max-heap?
Practice: sort a list using only heap operations (heapsort).

---

*Next up: Problem 188 of 250*
