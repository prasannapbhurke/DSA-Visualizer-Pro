# 📘 Problem 30 of 250 — LeetCode 83: Remove Duplicates from Sorted List

> **Pattern:** Linked List | **Roadmap Position:** #30/250

---

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

---

*Next up: Problem 31 of 250*
