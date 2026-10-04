# 📘 Problem 34 of 250 — LeetCode 328: Odd Even Linked List

> **Pattern:** Linked List | **Roadmap Position:** #34/250

---

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

---

*Next up: Problem 35 of 250*
