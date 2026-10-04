# 📘 Problem 28 of 250 — LeetCode 206: Reverse Linked List

> **Pattern:** Linked List | **Roadmap Position:** #28/250

---

LeetCode 206 — Reverse Linked List
Problem #28 of 250 | Difficulty: Easy | Pattern: Linked List — Three-Pointer Reversal

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given the head of a singly linked list, reverse it in-place.
Return the new head.

Example:
  1→2→3→4→5 → 5→4→3→2→1

You need to flip every arrow's direction!
Original: each node's .next points FORWARD
After: each node's .next points BACKWARD

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Use three pointers: prev, curr, next_node

  1 → 2 → 3 → null
  prev=null, curr=1

  Step 1: save next=2, flip 1.next=null, prev=1, curr=2
    null ← 1   2 → 3

  Step 2: save next=3, flip 2.next=1, prev=2, curr=3
    null ← 1 ← 2   3

  Step 3: save next=null, flip 3.next=2, prev=3, curr=null
    null ← 1 ← 2 ← 3

  curr=null → stop. New head = prev = 3

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think of it like reversing direction on a one-way street:
  Each car (node) needs to U-turn and point the other way.

  prev = null (the new tail points to null)
  curr = head (start reversing from here)

For each node:
  1. Remember where next is (or we lose it when we reverse!)
  2. Flip: curr.next = prev (U-turn!)
  3. Advance: prev = curr, curr = next_node

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

prev = None
curr = head

while curr:
    next_node = curr.next   # 1. Save next before overwriting
    curr.next = prev        # 2. Reverse the pointer
    prev = curr             # 3. Advance prev
    curr = next_node        # 4. Advance curr

return prev   # prev is now the new head (last node of original list)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1→2→3→null

prev=None, curr=1

Iter 1: next=2, 1.next=None, prev=1, curr=2
  null←1   2→3

Iter 2: next=3, 2.next=1, prev=2, curr=3
  null←1←2   3

Iter 3: next=null, 3.next=2, prev=3, curr=null
  null←1←2←3

curr=None → stop
Return prev = node(3)

Reversed: 3→2→1→null ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

The three-pointer dance is the core of linked list reversal:

  Save next FIRST (before you destroy the pointer!)
  Then reverse
  Then advance both

This exact same idea appears in:
  - Reversing a portion of a linked list (LC92)
  - Reorder List (LC143)
  - Palindrome Linked List (LC234)

Master this and you master most linked list problems.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

prev, curr = None, head
while curr:
    next_node = curr.next   # save
    curr.next = prev        # reverse
    prev = curr             # advance prev
    curr = next_node        # advance curr
return prev

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — visit each node exactly once
Space: O(1) — three pointer variables

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Forgetting to save next_node BEFORE reversing
   (After curr.next=prev, you've lost the link to the rest!)
2. Returning curr instead of prev (curr is null at end)
3. Starting with prev=head instead of prev=None

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Recursive version:
  def reverseList(head):
      if not head or not head.next: return head
      new_head = reverseList(head.next)  # reverse the rest
      head.next.next = head              # current head becomes tail
      head.next = None                   # disconnect
      return new_head

LC92 — Reverse Linked List II (reverse only positions left to right)

---

*Next up: Problem 29 of 250*
