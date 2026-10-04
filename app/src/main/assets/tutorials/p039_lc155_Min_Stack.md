# 📘 Problem 39 of 250 — LeetCode 155: Min Stack

> **Pattern:** Stack | **Roadmap Position:** #39/250

---

LeetCode 155 — Min Stack
Problem #39 of 250 | Difficulty: Medium | Pattern: Stack + Auxiliary Min Stack

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Design a stack that supports push, pop, top, and getMin in O(1).

getMin() must return the MINIMUM element in the stack.

Example:
  push(-2), push(0), push(-3)
  getMin() → -3 (smallest element)
  pop()
  top() → 0
  getMin() → -2 (now smallest is -2)

Standard stack doesn't track minimum.
We need a clever data structure.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Problem: when we pop the current minimum, we need to know the NEXT minimum.
Solution: Maintain a SECOND stack that tracks minimums.

  Main stack: stores all values
  Min stack: stores the minimum AT EACH LEVEL

  push(-2): main=[-2], min=[-2]        (min is -2)
  push(0):  main=[-2,0], min=[-2,-2]  (min is still -2)
  push(-3): main=[-2,0,-3], min=[-2,-2,-3]  (min is now -3)
  pop():    main=[-2,0], min=[-2,-2]  (min is back to -2)
  getMin(): min[-1] = -2 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Keep a "running minimum" stack alongside the main stack.

Every time you push, also push to min_stack the MINIMUM of:
  current value vs current min_stack top

Every time you pop, also pop from min_stack.

The min_stack top ALWAYS tells you the current minimum!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

class MinStack:
    def __init__(self):
        self.stack = []
        self.min_stack = []

    def push(self, val):
        self.stack.append(val)
        if self.min_stack:
            self.min_stack.append(min(val, self.min_stack[-1]))
        else:
            self.min_stack.append(val)

    def pop(self):
        self.stack.pop()
        self.min_stack.pop()

    def top(self):
        return self.stack[-1]

    def getMin(self):
        return self.min_stack[-1]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

push(-2): stack=[-2], min_stack=[-2]
push(0):  stack=[-2,0], min_stack=[-2, min(0,-2)=-2]
push(-3): stack=[-2,0,-3], min_stack=[-2,-2, min(-3,-2)=-3]

getMin() → min_stack[-1] = -3 ✓

pop():    stack=[-2,0], min_stack=[-2,-2]
top()     → stack[-1] = 0 ✓
getMin()  → min_stack[-1] = -2 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Two stacks in tandem:
  Main stack: normal stack operations
  Min stack: tracks the minimum AT EVERY STATE

When something gets popped, the corresponding min gets popped too.
This way getMin() is always O(1).

The min_stack mirrors the structure of the main stack exactly.
When main has 3 elements, min_stack also has 3.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

self.stack = []
self.min_stack = []

def push(val):
    self.stack.append(val)
    self.min_stack.append(min(val, self.min_stack[-1] if self.min_stack else val))

def pop(): self.stack.pop(); self.min_stack.pop()
def top(): return self.stack[-1]
def getMin(): return self.min_stack[-1]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

push/pop/top/getMin: all O(1)
Space: O(n) — two stacks of equal size

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Only tracking global minimum (can't recover after pop)
2. Not syncing min_stack with main_stack pops
3. Forgetting to handle empty min_stack on first push

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC716 — Max Stack (track maximum instead of minimum)
What if you needed to support getMax() too?
Can you do both min and max in O(1) simultaneously?

---

*Next up: Problem 40 of 250*
