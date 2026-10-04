# 📘 Problem 49 of 250 — LeetCode 232: Implement Queue using Stacks

> **Pattern:** Stack / Queue | **Roadmap Position:** #49/250

---

LeetCode 232 — Implement Queue using Stacks
Problem #49 of 250 | Difficulty: Easy | Pattern: Stack — Amortized Push to Back

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Implement a queue (FIFO) using only two stacks.
Operations: push(x), pop(), peek(), empty()

Queue: first in, first out (like a line)
Stack: last in, first out (like a pile)

Example:
  push(1), push(2)
  peek() → 1 (front of queue)
  pop() → 1
  empty() → false

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Two stacks: stack1 (input), stack2 (output).

Push: always push to stack1.
Pop/Peek: if stack2 is empty, pour ALL of stack1 into stack2 (reverses order!).
          Then pop/peek from stack2.

  push 1, push 2, push 3:
  stack1 = [1,2,3], stack2 = []

  pop(): stack2 empty → pour stack1 into stack2
  stack1=[], stack2=[3,2,1] (reversed!)
  stack2.pop() → 1 ✓ (FIFO!)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Two stacks of plates:
  Incoming plates go to Stack1.
  When you need to serve (pop), flip Stack1 into Stack2.
  Now Stack2 has oldest plate on top — serve from Stack2.
  Once Stack2 is empty, flip again.

The pouring reversal makes last-in become last-out — FIFO!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

class MyQueue:
    def __init__(self):
        self.in_stack = []    # For push
        self.out_stack = []   # For pop/peek

    def push(self, x):
        self.in_stack.append(x)

    def _transfer(self):
        if not self.out_stack:    # Only transfer when out_stack is empty
            while self.in_stack:
                self.out_stack.append(self.in_stack.pop())

    def pop(self):
        self._transfer()
        return self.out_stack.pop()

    def peek(self):
        self._transfer()
        return self.out_stack[-1]

    def empty(self):
        return not self.in_stack and not self.out_stack

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

push(1), push(2), push(3)
in_stack=[1,2,3], out_stack=[]

pop():
  out_stack empty → transfer: pop 3,2,1 from in → push to out
  in_stack=[], out_stack=[3,2,1]
  out_stack.pop() → 1 ✓ (FIFO!)

peek():
  out_stack=[3,2], not empty → no transfer
  out_stack[-1] = 2 ✓

empty(): in_stack=[], out_stack=[3,2] → False ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Amortized O(1) for pop/peek!
  Each element is pushed/transferred/popped at most once.
  Total work across all operations = O(n).
  So each operation costs O(1) AMORTIZED.

Key insight: only transfer when out_stack is EMPTY.
Don't transfer for every pop — transfer the whole batch at once.

This teaches amortized analysis: occasional expensive operations
averaged over many cheap operations.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

in_stack, out_stack = [], []
def push(x): in_stack.append(x)
def _fill():
    if not out_stack:
        while in_stack: out_stack.append(in_stack.pop())
def pop(): _fill(); return out_stack.pop()
def peek(): _fill(); return out_stack[-1]
def empty(): return not in_stack and not out_stack

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

push: O(1)
pop/peek: O(1) amortized, O(n) worst case
empty: O(1)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Transferring on EVERY pop (costly — should only when out_stack empty)
2. Not checking out_stack before trying to transfer
3. Forgetting to check both stacks for empty()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC225 — Implement Stack using Queues (reverse challenge!)
Can you implement it with ONE queue instead of two?

---

*Next up: Problem 50 of 250*
