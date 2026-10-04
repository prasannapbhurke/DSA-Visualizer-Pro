# 📘 Problem 31 of 250 — LeetCode 203: Remove Linked List Elements

> **Pattern:** Linked List | **Roadmap Position:** #31/250

---

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

---

*Next up: Problem 32 of 250*
