# 📘 Problem 29 of 250 — LeetCode 21: Merge Two Sorted Lists

> **Pattern:** Linked List | **Roadmap Position:** #29/250

---

LeetCode 21 — Merge Two Sorted Lists
Problem #29 of 250 | Difficulty: Easy | Pattern: Two Pointers — Merge with Dummy Head

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Merge two sorted linked lists into one sorted linked list.
Return the head of the merged list.

Example:
  list1: 1→2→4
  list2: 1→3→4
  merged: 1→1→2→3→4→4

Both lists are already sorted.
The merged list should also be sorted.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

At each step, pick the SMALLER of the two current nodes.
Advance the pointer in the list that gave the smaller node.

  list1: 1→2→4   list2: 1→3→4

  Compare | Pick  | Advance
  ─────────────────────────
   1  vs 1 |  1(2) | list2 (tie → either works)
   1  vs 3 |   1   | list1
   2  vs 3 |   2   | list1
   4  vs 3 |   3   | list2
   4  vs 4 |   4   | list1 or list2
  remaining|   4   | attach rest

Result: 1→1→2→3→4→4

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

You have two sorted stacks of cards, face up.
Repeatedly pick the card with the smaller face value from the top.
When one stack is empty, take all remaining from the other.

Use a "dummy" head node at the start — it makes the code cleaner.
You never have to special-case the first node.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

dummy = ListNode(0)  # Dummy head to simplify logic
current = dummy

while list1 and list2:
    if list1.val <= list2.val:
        current.next = list1
        list1 = list1.next
    else:
        current.next = list2
        list2 = list2.next
    current = current.next

# Attach any remaining nodes
current.next = list1 if list1 else list2

return dummy.next

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

list1: 1→2→4, list2: 1→3→4

dummy→?, current=dummy

1 vs 1: pick list1(1), current=node(1), list1=2→4 → dummy→1
2 vs 1: pick list2(1), current=node(1), list2=3→4 → dummy→1→1
2 vs 3: pick list1(2), current=node(2), list1=4   → dummy→1→1→2
4 vs 3: pick list2(3), current=node(3), list2=4   → dummy→1→1→2→3
4 vs 4: pick list1(4), current=node(4), list1=None → dummy→1→1→2→3→4

list1 is None → attach list2 remaining = node(4)
dummy→1→1→2→3→4→4

Return dummy.next = 1→1→2→3→4→4 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Dummy head pattern: Creating a fake node before the real head
saves you from special-casing the FIRST element.

Instead of: "if result is None: set result, else append"
You just: current.next = ... every time!

The dummy head pattern is fundamental in linked list problems.
Use it whenever you're building a new linked list from scratch.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

dummy = ListNode(0)
curr = dummy
while l1 and l2:
    if l1.val <= l2.val:
        curr.next = l1; l1 = l1.next
    else:
        curr.next = l2; l2 = l2.next
    curr = curr.next
curr.next = l1 or l2
return dummy.next

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(m+n) — each node visited once
Space: O(1) — reusing existing nodes

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not using dummy head (leads to complex first-node handling)
2. Forgetting to attach remaining nodes at the end
3. Advancing both pointers (only advance the one you picked from)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC23 — Merge k Sorted Lists (generalize to k lists!)
  Use a min-heap to efficiently pick the minimum across k lists.

---

*Next up: Problem 30 of 250*
