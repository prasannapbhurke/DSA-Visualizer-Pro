# 📘 Problem 26 of 250 — LeetCode 876: Middle of the Linked List

> **Pattern:** Fast & Slow Pointers | **Roadmap Position:** #26/250

---

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

---

*Next up: Problem 27 of 250*
