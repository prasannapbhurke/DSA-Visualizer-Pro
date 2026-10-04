# 📘 Problem 50 of 250 — LeetCode 225: Implement Stack using Queues

> **Pattern:** Queue / Stack | **Roadmap Position:** #50/250

---

LeetCode 225 — Implement Stack using Queues
Problem #50 of 250 | Difficulty: Easy | Pattern: Queue — Rotate for LIFO

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Implement a stack (LIFO) using only one or two queues.
Operations: push(x), pop(), top(), empty()

Stack: last in, first out
Queue: first in, first out

Example:
  push(1), push(2)
  top() → 2 (last pushed)
  pop() → 2
  empty() → false

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

One queue approach:
  On PUSH: push x, then rotate all other elements to put x at FRONT.

  push(1): queue=[1]
  push(2): queue=[1,2] → rotate 1 to back → queue=[2,1]
  pop():   queue.popleft() → 2 ✓ (LIFO behavior!)

  OR:
  On POP: rotate n-1 elements to back, then popleft.
  On PUSH: simple enqueue.

  pop/top are O(n), push is O(1) in this variant.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Queue acts like a line. We want stack behavior (serve from the same end we add).

After pushing x:
  Rotate all previously queued elements to BEHIND x.
  Now x is at the front of the queue (ready to be "popped").

  push(1): q=[1]
  push(2): q=[1,2] then rotate 1 → q=[2,1]
  pop(): q.popleft()=2 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

from collections import deque

class MyStack:
    def __init__(self):
        self.q = deque()

    def push(self, x):
        self.q.append(x)
        # Rotate everything before x to the back
        for _ in range(len(self.q) - 1):
            self.q.append(self.q.popleft())

    def pop(self):
        return self.q.popleft()

    def top(self):
        return self.q[0]

    def empty(self):
        return not self.q

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

push(1): q=[1]
push(2): q=[1,2] → rotate len-1=1 times: popleft 1, append → q=[2,1]
push(3): q=[2,1,3] → rotate 2 times: popleft 2→q=[1,3,2], popleft 1→q=[3,2,1]

top() → q[0] = 3 ✓
pop() → q.popleft() = 3 → q=[2,1]
top() → q[0] = 2 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

The rotation trick transforms FIFO into LIFO:
  After each push, the newest element is at the FRONT (popleft position).
  So popleft() gives us LIFO behavior!

Trade-off: push is O(n), pop/top is O(1).
(Compare to LC232 where push is O(1), pop is amortized O(1).)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

q = deque()
def push(x):
    q.append(x)
    for _ in range(len(q)-1):
        q.append(q.popleft())
def pop(): return q.popleft()
def top(): return q[0]
def empty(): return not q

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

push: O(n) — rotate n-1 elements
pop/top: O(1) — front of queue
empty: O(1)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Confusing this with LC232 (different trade-offs)
2. Not rotating the right number of times (rotate len-1, not len)
3. Using a list instead of deque (popleft on list is O(n)!)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC232 — Implement Queue using Stacks (reverse direction)
What's the trade-off between the two approaches?
When would you prefer O(n) push vs O(n) pop?

---

*Next up: Problem 51 of 250*
