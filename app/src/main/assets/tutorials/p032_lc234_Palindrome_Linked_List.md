# 📘 Problem 32 of 250 — LeetCode 234: Palindrome Linked List

> **Pattern:** Linked List | **Roadmap Position:** #32/250

---

LeetCode 234 — Palindrome Linked List
Problem #32 of 250 | Difficulty: Easy | Pattern: Fast/Slow + Reverse Half

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given head of a linked list, return true if it's a palindrome.
A palindrome reads the same forward and backward.

Example:
  1→2→2→1 → true
  1→2→3→2→1 → true
  1→2 → false

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Strategy: Find middle, reverse second half, compare with first half.

  1→2→2→1

  Step 1: Find middle using slow/fast → middle at node(2) [second one]
  Step 2: Reverse second half: 1→2  ← becomes →  1←2
  Step 3: Compare: 1==1, 2==2 → palindrome!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Fold the list in half.
Do the two halves mirror each other?

1. Find the middle (slow/fast pointers)
2. Reverse the second half in-place
3. Compare first half and reversed second half node by node
4. If all match → palindrome

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def isPalindrome(head):
    # Step 1: Find middle
    slow = fast = head
    while fast and fast.next:
        slow = slow.next
        fast = fast.next.next

    # Step 2: Reverse second half
    prev = None
    curr = slow
    while curr:
        next_node = curr.next
        curr.next = prev
        prev = curr
        curr = next_node

    # Step 3: Compare first half with reversed second half
    left, right = head, prev
    while right:   # right is shorter or equal
        if left.val != right.val:
            return False
        left = left.next
        right = right.next

    return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1→2→2→1

Find middle: slow=2(second), fast=1(last)
Reverse second half [2→1]: prev=None,curr=2
  next=1, 2.next=None, prev=2, curr=1
  next=None, 1.next=2, prev=1, curr=None
  Second half reversed: 1→2

Compare: left=1,right=1: 1==1 ✓ left=2,right=2: 2==2 ✓
Return True ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

This combines THREE techniques:
  1. Fast/slow to find middle
  2. In-place reversal of second half
  3. Two-pointer comparison

This is a great example of combining multiple patterns!
Learn each piece separately first, then combine.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Find mid → Reverse second half → Compare
slow = fast = head
while fast and fast.next: slow=slow.next; fast=fast.next.next
prev, curr = None, slow
while curr: nxt=curr.next; curr.next=prev; prev=curr; curr=nxt
l, r = head, prev
while r:
    if l.val != r.val: return False
    l=l.next; r=r.next
return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — three passes
Space: O(1) — in-place reversal

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not handling odd-length lists (middle node doesn't matter)
2. Comparing after failing to properly reverse
3. Mutating the list (ideally restore it after comparing)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Can you solve this without modifying the list?
  Use a stack: push first half, pop while comparing second half.

---

*Next up: Problem 33 of 250*
