# 📘 Problem 42 of 250 — LeetCode 682: Baseball Game

> **Pattern:** Stack | **Roadmap Position:** #42/250

---

LeetCode 682 — Baseball Game
Problem #42 of 250 | Difficulty: Easy | Pattern: Stack — Simulation

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Simulate a baseball game with these operations:
  integer n: record new score of n
  '+': record sum of last two scores
  'D': record double of last score
  'C': invalidate (remove) last score

Return sum of all valid scores.

Example:
  ops = ["5","2","C","D","+"]
  5 → [5]
  2 → [5,2]
  C → [5] (remove 2)
  D → [5,10] (double of 5)
  + → [5,10,15] (5+10=15)
  Sum = 5+10+15 = 30

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Use a stack to track valid scores.

  ops = ["5","-2","4","C","D","9","+","+"]

  op   | stack
  ─────────────────────────────────
  "5"  | [5]
  "-2" | [5,-2]
  "4"  | [5,-2,4]
  "C"  | [5,-2] (pop 4)
  "D"  | [5,-2,-4] (double of -2)
  "9"  | [5,-2,-4,9]
  "+"  | [5,-2,-4,9,5] (-4+9=5)
  "+"  | [5,-2,-4,9,5,14] (9+5=14)

  Sum = 5+(-2)+(-4)+9+5+14 = 27

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Stack is perfect here: undo (C) = pop, and last scores = stack[-1] and stack[-2].

For each operation:
  Number → push it
  '+' → push stack[-1] + stack[-2]
  'D' → push stack[-1] * 2
  'C' → pop

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def calPoints(ops):
    stack = []

    for op in ops:
        if op == '+':
            stack.append(stack[-1] + stack[-2])
        elif op == 'D':
            stack.append(stack[-1] * 2)
        elif op == 'C':
            stack.pop()
        else:
            stack.append(int(op))

    return sum(stack)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

ops = ["5","2","C","D","+"]

"5": stack=[5]
"2": stack=[5,2]
"C": pop → stack=[5]
"D": double 5 → stack=[5,10]
"+": 5+10=15 → stack=[5,10,15]

sum([5,10,15]) = 30 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Stack for history/undo operations:
  C (cancel) = pop the last recorded item
  + needs last two items = stack[-1] + stack[-2]

Stack naturally provides access to recent history.
This is the core insight for simulation problems with "undo" operations.

Sum at end = sum(stack) — clean and simple.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

stack = []
for op in ops:
    if op == '+': stack.append(stack[-1]+stack[-2])
    elif op == 'D': stack.append(stack[-1]*2)
    elif op == 'C': stack.pop()
    else: stack.append(int(op))
return sum(stack)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — process each operation once
Space: O(n) — stack can hold all scores

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not converting string to int for number operations
2. Forgetting that 'D' and '+' need stack[-1] and [-2]
3. Not checking stack has enough elements (though problem guarantees valid input)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

What if 'C' could cancel the last k operations instead of 1?
What if scores had min/max limits?

---

*Next up: Problem 43 of 250*
