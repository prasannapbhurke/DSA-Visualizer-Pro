# 📘 Problem 198 of 250 — LeetCode 25: Reverse Nodes in k-Group

> **Pattern:** Linked List | **Roadmap Position:** #198/250

---

LeetCode 25 — Reverse Nodes in k-Group
Problem #198 of 250 | Difficulty: Hard | Pattern: Linked List — Segment Reversal

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 25: Reverse Nodes in k-Group | Hard
Read carefully. Draw the linked list and desired output on paper first.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Linked List — Segment Reversal

LINKED LIST INSIGHT:
Before coding, always draw the pointer changes:
  What pointers need to change?
  In what order? (Changing in wrong order can lose references!)
  Do you need prev/curr/next tracking?
  Should you use a dummy head?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw the transformation:
  BEFORE: A→B→C→D→E
  AFTER:  [your target arrangement]

For "Reverse Nodes in k-Group":
  What are the key pointer changes?
  Which segments of the list need to be processed?

Linked list operations: always save next BEFORE modifying pointers!
Use dummy head to handle head deletion/replacement uniformly.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Common linked list patterns for Reverse Nodes in k-Group:

# Find tail or nth node:
fast = slow = head
while fast and fast.next: fast = fast.next; slow = slow.next

# Reverse a segment [left, right]:
prev = None; curr = start
for _ in range(length):
    nxt = curr.next; curr.next = prev; prev = curr; curr = nxt

# Connect segments after manipulation

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Trace through with a short list (3-5 nodes).
Draw each pointer state after each line of code.
Verify the final structure is correct.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Advanced linked list problems combine:
  Finding positions (fast/slow, nth from end)
  Reversing segments (three-pointer dance)
  Reconnecting segments (dummy head, tail pointers)

The order of pointer updates is critical — always save next before modifying!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

dummy = ListNode(0, head)
# Find relevant positions
# Perform pointer manipulations
# Reconnect segments
return dummy.next

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(1)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not saving next before modifying pointers
2. Not using dummy head (head deletion becomes special case)
3. Off-by-one in counting positions
4. Not reconnecting tail after segment manipulation

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw your solution on paper with pointer arrows before coding.
Then translate the drawing to code.

---

*Next up: Problem 199 of 250*
