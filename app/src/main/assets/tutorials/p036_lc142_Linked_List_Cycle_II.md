# 📘 Problem 36 of 250 — LeetCode 142: Linked List Cycle II

> **Pattern:** Fast & Slow | **Roadmap Position:** #36/250

---

LeetCode 142 — Linked List Cycle II
Problem #36 of 250 | Difficulty: Medium | Pattern: Floyd's Algorithm — Find Cycle Start

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given a linked list with a cycle, return the NODE where the cycle BEGINS.
If no cycle, return null.

Example:
  3→2→0→-4→(back to 2)
  Cycle starts at node with value 2

This is the HARDER version of LC141.
First detect the cycle, then FIND WHERE it starts.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Floyd's Algorithm has two phases:

Phase 1 — DETECT:
  Slow moves 1, fast moves 2.
  If they meet → cycle exists.
  Meeting point is INSIDE the cycle.

Phase 2 — FIND START:
  When slow and fast meet:
    Reset one pointer to HEAD.
    Move BOTH pointers 1 step at a time.
    Where they meet = cycle start!

  Mathematical proof:
    Let: a = distance from head to cycle start
         b = distance from cycle start to meeting point
         c = cycle length
    When they meet: slow traveled a+b, fast traveled a+b+n*c
    Fast = 2*slow: a+b+n*c = 2(a+b)
    Solving: a = n*c - b = distance from meeting point to cycle start!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Phase 1: Run the race. Find where the hare catches the tortoise.
Phase 2: Put a NEW runner at the START. Both new runner and tortoise walk 1 step.
They'll meet exactly at the cycle entrance.

Why? Because a = n*c - b mathematically.
The meeting point is exactly "a" steps from the cycle start,
and the head is also "a" steps from the cycle start.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Phase 1: Detect cycle and find meeting point
slow = fast = head
while fast and fast.next:
    slow = slow.next
    fast = fast.next.next
    if slow == fast:
        break
else:
    return None   # No cycle

# Phase 2: Find cycle start
pointer = head
while pointer != slow:
    pointer = pointer.next
    slow = slow.next

return slow  # Both pointers now at cycle start

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

List: head→A→B→C→D→B (cycle at B)

Phase 1:
slow=head,fast=head
Step 1: slow=A, fast=B
Step 2: slow=B, fast=D
Step 3: slow=C, fast=C → MEET at C!

Phase 2:
pointer=head, slow=C (meeting point)
Step 1: pointer=A, slow=D
Step 2: pointer=B, slow=B → MEET at B (cycle start!) ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

The math behind Floyd's:
  When you reset one pointer to head and walk both at speed 1,
  they meet at the cycle entrance.
  This works because of the mathematical relationship: a = nc - b.

Trust the math and remember the two-phase pattern:
  Phase 1: detect (fast=2×slow)
  Phase 2: find start (reset one to head, both at speed 1)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Phase 1: detect
slow = fast = head
while fast and fast.next:
    slow = slow.next; fast = fast.next.next
    if slow == fast: break
else: return None

# Phase 2: find start
ptr = head
while ptr != slow:
    ptr = ptr.next; slow = slow.next
return slow

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — linear
Space: O(1) — constant pointers

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not handling the no-cycle case
2. Forgetting to do Phase 2 (just finding meeting point is not the answer)
3. Confusing meeting point with cycle start

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Can you find the LENGTH of the cycle?
  After finding the cycle start, count steps until you return to it.

---

*Next up: Problem 37 of 250*
