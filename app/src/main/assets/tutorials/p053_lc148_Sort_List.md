# 📘 Problem 53 of 250 — LeetCode 148: Sort List

> **Pattern:** Merge Sort / Linked List | **Roadmap Position:** #53/250

---

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

---

*Next up: Problem 54 of 250*
