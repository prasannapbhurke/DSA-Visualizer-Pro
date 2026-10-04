# 📘 Problem 35 of 250 — LeetCode 2: Add Two Numbers

> **Pattern:** Linked List | **Roadmap Position:** #35/250

---

LeetCode 2 — Add Two Numbers
Problem #35 of 250 | Difficulty: Medium | Pattern: Linked List — Digit-by-Digit Addition

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Two non-negative integers represented as REVERSED linked lists.
Each node contains one digit. Add the two numbers and return the sum as a reversed list.

Example:
  l1 = 2→4→3 (represents 342)
  l2 = 5→6→4 (represents 465)
  342 + 465 = 807
  Result: 7→0→8

The lists are already reversed (LSB first), so we add from head naturally!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Add digit by digit, left to right (which is LSB to MSB since reversed):

  l1:  2→4→3   (342)
  l2:  5→6→4   (465)

  Position | l1 | l2 | carry | digit | carry_out
  ─────────────────────────────────────────────
     1      |  2 |  5 |   0   |   7   |    0
     2      |  4 |  6 |   0   |   0   |    1   (10, carry 1)
     3      |  3 |  4 |   1   |   8   |    0

  Result nodes: 7→0→8

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Elementary school addition, column by column:
    342
  + 465
  ─────
    807

Except the digits are already reversed for us!
So we just walk left to right, adding with carry.

At each step:
  total = l1.val + l2.val + carry
  digit = total % 10
  carry = total // 10

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

dummy = ListNode(0)
curr = dummy
carry = 0

while l1 or l2 or carry:
    val1 = l1.val if l1 else 0   # l1 might be shorter
    val2 = l2.val if l2 else 0   # l2 might be shorter

    total = val1 + val2 + carry
    digit = total % 10
    carry = total // 10

    curr.next = ListNode(digit)
    curr = curr.next

    if l1: l1 = l1.next
    if l2: l2 = l2.next

return dummy.next

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

l1: 2→4→3, l2: 5→6→4

carry=0

l1=2,l2=5: total=7, digit=7, carry=0 → create node(7)
l1=4,l2=6: total=10, digit=0, carry=1 → create node(0)
l1=3,l2=4: total=3+4+1=8, digit=8, carry=0 → create node(8)
l1=None, l2=None, carry=0 → stop

Return: 7→0→8 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Three key techniques combined:
  1. Dummy head (clean list construction)
  2. Simultaneous list traversal
  3. Carry propagation (elementary addition)

The while loop continues while:
  ANY of l1, l2, carry is non-zero
This handles: different lengths AND a final carry (e.g., 999 + 1 = 1000)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

dummy = ListNode(0)
curr = dummy
carry = 0
while l1 or l2 or carry:
    v1 = l1.val if l1 else 0
    v2 = l2.val if l2 else 0
    total = v1 + v2 + carry
    curr.next = ListNode(total % 10)
    carry = total // 10
    curr = curr.next
    if l1: l1 = l1.next
    if l2: l2 = l2.next
return dummy.next

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(max(m,n)) — traverse longer list
Space: O(max(m,n)) — result list length

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Forgetting the final carry (e.g., 5+5=10, need extra node)
2. Not handling different-length lists (use 0 for missing digits)
3. Forgetting to advance l1 and l2 pointers

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC445 — Add Two Numbers II (numbers NOT reversed)
  Must reverse lists first, then add, then reverse result
  OR use stacks to process from MSB to LSB

---

*Next up: Problem 36 of 250*
