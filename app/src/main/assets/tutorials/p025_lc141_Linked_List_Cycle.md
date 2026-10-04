# 📘 Problem 25 of 250 — LeetCode 141: Linked List Cycle

> **Pattern:** Fast & Slow Pointers | **Roadmap Position:** #25/250

---

LeetCode 141 — Linked List Cycle
Problem #25 of 250 | Difficulty: Easy | Pattern: Fast & Slow Pointers — Floyd's Cycle Detection

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given the head of a linked list, determine if it has a cycle.
A cycle means some node's next pointer points back to a previous node.

Example:
  3 → 2 → 0 → -4 → (back to 2) → true

Example:
  1 → 2 → (back to 1) → true

Example:
  1 → 2 → null → false

Return true if cycle exists, false if list terminates at null.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Floyd's Tortoise & Hare Algorithm:
  Slow pointer moves 1 step at a time.
  Fast pointer moves 2 steps at a time.

  If NO cycle: fast reaches null first
  If cycle: fast "laps" slow — they MUST meet!

  Imagine two runners on a circular track:
  The faster runner will always lap the slower one.
  They WILL meet eventually if the track is circular.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Two runners on a track:
  Slow jogs 1 lap/hour
  Fast sprints 2 laps/hour

If it's a straight track → fast finishes first, no meeting.
If it's circular → fast eventually comes around and meets slow.

In code:
  slow = head, fast = head
  Each step: slow goes 1, fast goes 2
  If fast hits null → no cycle
  If slow == fast → cycle detected!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

slow = head
fast = head

while fast and fast.next:    # fast must have room for 2 steps
    slow = slow.next         # tortoise: 1 step
    fast = fast.next.next    # hare: 2 steps

    if slow == fast:         # they met → cycle!
        return True

return False                 # fast reached end → no cycle

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

List: 3→2→0→-4→2(cycle back)

slow=3, fast=3

Step 1: slow=2, fast=0
Step 2: slow=0, fast=2  (fast: 0→-4→2)
Step 3: slow=-4, fast=-4 → slow==fast → return True ✓

No cycle example: 1→2→null
Step 1: slow=2, fast=null → while condition fails → return False ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Floyd's algorithm: the most elegant cycle detection.

Why fast catches slow:
  In the cycle of length L:
  Each step, fast gains 1 on slow (moves 2, slow moves 1)
  After L steps, fast has lapped slow → they meet!

Important: check AFTER moving (not before):
  At start, slow==fast==head (but that's before any movement)
  We want to find a meeting DURING traversal.

O(1) space! No HashSet needed (HashSet works but wastes space).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

slow = fast = head
while fast and fast.next:
    slow = slow.next
    fast = fast.next.next
    if slow == fast:
        return True
return False

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — at most n+L steps where L is cycle length
Space: O(1) — two pointer variables

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Checking slow==fast BEFORE moving (they always equal at start!)
2. Not checking fast.next before fast.next.next
3. Using a HashSet (O(n) space, defeats the purpose)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC142 — Linked List Cycle II
  Find WHERE the cycle BEGINS, not just if it exists.
  Math hint: when slow and fast meet, reset one to head,
  move both 1 step at a time — they'll meet at the cycle start!

---

*Next up: Problem 26 of 250*
