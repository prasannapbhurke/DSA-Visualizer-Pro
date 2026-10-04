# 📘 Problem 46 of 250 — LeetCode 150: Evaluate Reverse Polish Notation

> **Pattern:** Stack | **Roadmap Position:** #46/250

---

LeetCode 150 — Evaluate Reverse Polish Notation
Problem #46 of 250 | Difficulty: Medium | Pattern: Stack — Expression Evaluation

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Evaluate an expression in Reverse Polish Notation (RPN).
Operands come before operators (postfix notation).
Valid operators: +, -, *, /

Example:
  ["2","1","+","3","*"] = ((2+1)*3) = 9
  ["4","13","5","/","+"] = (4+(13/5)) = 6
  ["10","6","9","3","+","-","11","*","/","17","+","5","+"] = 22

In RPN, you don't need parentheses — order handles precedence.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Process tokens left to right:
  Number → push onto stack
  Operator → pop TWO numbers, apply operator, push result

  ["2","1","+","3","*"]

  Token | Action            | Stack
  ───────────────────────────────────
  "2"   | push              | [2]
  "1"   | push              | [2,1]
  "+"   | pop 1,2; 2+1=3   | [3]
  "3"   | push              | [3,3]
  "*"   | pop 3,3; 3*3=9   | [9]

  Return 9 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

RPN is how calculators work internally.
As you scan tokens:
  If it's a number: put it on your "pile"
  If it's an operator: take the top two from the pile, combine them, put the result back

The pile is a stack!
At the end, one number remains: the answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def evalRPN(tokens):
    stack = []

    for token in tokens:
        if token in '+-*/':
            b = stack.pop()   # Second operand
            a = stack.pop()   # First operand
            if token == '+': stack.append(a + b)
            elif token == '-': stack.append(a - b)
            elif token == '*': stack.append(a * b)
            elif token == '/': stack.append(int(a / b))  # Truncate toward zero!
        else:
            stack.append(int(token))

    return stack[0]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

["4","13","5","/","+"]

"4": stack=[4]
"13": stack=[4,13]
"5": stack=[4,13,5]
"/": b=5, a=13, 13/5=2 (truncate) → stack=[4,2]
"+": b=2, a=4, 4+2=6 → stack=[6]

Return 6 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Stack is perfect for expression evaluation!

Important: pop order matters!
  b = pop() → this is the SECOND operand (right side)
  a = pop() → this is the FIRST operand (left side)
  
For subtraction: a - b (not b - a!)
For division: int(a/b) which truncates toward zero
  (Python's // floors toward negative infinity — not the same!)

Use int(a/b) not a//b for correct behavior with negatives.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

stack = []
for token in tokens:
    if token in '+-*/':
        b, a = stack.pop(), stack.pop()
        if token=='+': stack.append(a+b)
        elif token=='-': stack.append(a-b)
        elif token=='*': stack.append(a*b)
        else: stack.append(int(a/b))
    else: stack.append(int(token))
return stack[0]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — process each token once
Space: O(n) — stack

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Wrong pop order: b=pop(), a=pop() (second pop is the FIRST operand)
2. Using // instead of int(/) for division (wrong for negative numbers)
3. Not converting token to int before pushing

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC224 — Basic Calculator (infix notation with parentheses — much harder!)
LC227 — Basic Calculator II (no parentheses, but +,-,*,/)

---

*Next up: Problem 47 of 250*
