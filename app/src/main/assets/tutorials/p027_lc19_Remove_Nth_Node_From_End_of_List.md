# 📘 Problem 27 of 250 — LeetCode 19: Remove Nth Node From End of List

> **Pattern:** Two Pointers | **Roadmap Position:** #27/250

---

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

---

*Next up: Problem 28 of 250*
