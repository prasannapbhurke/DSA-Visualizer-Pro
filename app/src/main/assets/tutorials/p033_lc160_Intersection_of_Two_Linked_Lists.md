# 📘 Problem 33 of 250 — LeetCode 160: Intersection of Two Linked Lists

> **Pattern:** Two Pointers | **Roadmap Position:** #33/250

---

LeetCode 160 — Intersection of Two Linked Lists
Problem #33 of 250 | Difficulty: Easy | Pattern: Two Pointers — Path Length Equalization

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given heads of two linked lists, return the node where they intersect.
If they don't intersect, return null.

Example:
  A: a1→a2→c1→c2→c3
  B: b1→b2→b3→c1→c2→c3
  Intersection at: c1

Note: "intersection" means same node in memory, not just same value!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Key insight: If you switch paths at the end,
both pointers travel the same total distance!

  List A length = a + c
  List B length = b + c

  Pointer pA: travels A, then switches to B → total = a+c+b
  Pointer pB: travels B, then switches to A → total = b+c+a
  Both travel = a+b+c → they arrive at intersection simultaneously!

If no intersection: both reach null at the same time.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Two people walking different paths that eventually merge:
  Person A walks path A then path B
  Person B walks path B then path A
  If paths merge, they'll meet exactly at the merge point!

When a pointer reaches the end of its list, redirect to the OTHER list's head.
Eventually they'll either both reach null (no intersection) or meet at the node.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

pA = headA
pB = headB

while pA != pB:
    pA = pA.next if pA else headB  # when done with A, start B
    pB = pB.next if pB else headA  # when done with B, start A

return pA   # either the intersection node or None (both null)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

A: 1→2→3→4→5 (lengths differ but merge at node 3 of A = node 3)
Simpler: A: 4→1→8→4→5, B: 5→6→1→8→4→5
(Common part: 8→4→5, length 3)

pA walks: 4,1,8 then switches to B: 5,6,1,8 — meets pB at node(8)
pB walks: 5,6,1,8 then switches to A: 4,1,8 — meets pA at node(8)
Return node(8) ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Distance equalization trick:
  Total path for A = len(A) + len(B)
  Total path for B = len(B) + len(A)
  Same total! So they sync up at the intersection.

This is O(1) space and very elegant.
Alternative: use a HashSet of visited nodes in list A, then walk B.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

pA, pB = headA, headB
while pA != pB:
    pA = pA.next if pA else headB
    pB = pB.next if pB else headA
return pA

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(m+n) — both traverse combined length
Space: O(1) — two pointers

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Comparing values (need to compare nodes/references)
2. Infinite loop if lists have no intersection and you forget None handling
3. Not handling the case where one list is much shorter

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Can you find the intersection without modifying the lists?
What if the lists are circular (have cycles)?

---

*Next up: Problem 34 of 250*
