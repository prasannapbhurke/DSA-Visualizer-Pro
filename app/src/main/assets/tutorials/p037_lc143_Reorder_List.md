# 📘 Problem 37 of 250 — LeetCode 143: Reorder List

> **Pattern:** Linked List | **Roadmap Position:** #37/250

---

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

---

*Next up: Problem 38 of 250*
