# 📘 Problem 40 of 250 — LeetCode 20: Valid Parentheses

> **Pattern:** Stack | **Roadmap Position:** #40/250

---

LeetCode 20 — Valid Parentheses
Problem #40 of 250 | Difficulty: Easy | Pattern: Stack — Open/Close Matching

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given a string of brackets, determine if it's valid.
Valid means:
  - Every open bracket has a matching close bracket
  - Brackets are closed in the right ORDER

Example:
  "()" → true
  "()[]{}" → true
  "(]" → false (wrong type)
  "([)]" → false (wrong order)
  "{[]}" → true (nested correctly)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Use a stack. Rule:
  Open bracket → push onto stack
  Close bracket → check if matches the top of stack

  s = "({[]})"

  char | action        | stack
  ──────────────────────────────
   (   | push          | ['(']
   {   | push          | ['(', '{']
   [   | push          | ['(', '{', '[']
   ]   | matches '[' ✓ pop  | ['(', '{']
   }   | matches '{' ✓ pop  | ['(']
   )   | matches '(' ✓ pop  | []

  Stack empty → valid!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Imagine stacking colored plates:
  Open bracket → place a plate on the stack
  Close bracket → check the top plate matches!

If the colors don't match → invalid!
If you try to close and the stack is empty → invalid!
After processing all → if stack is empty → valid!

The stack maintains the "context" of what's currently open.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def isValid(s):
    stack = []
    matching = {')': '(', '}': '{', ']': '['}

    for char in s:
        if char in '({[':
            stack.append(char)   # Open bracket: push
        else:
            # Close bracket: check if it matches top of stack
            if not stack or stack[-1] != matching[char]:
                return False
            stack.pop()

    return len(stack) == 0  # Valid only if nothing left unmatched

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

s = "([)]"

char='(': push → stack=['(']
char='[': push → stack=['(','[']
char=')': closing, matching[')']='(' check stack[-1]='[' ≠ '(' → return False ✓

s = "{[]}"
char='{': push → ['{'
char='[': push → ['{','[']
char=']': matching=']'→'[', stack[-1]='[' ✓ → pop → ['{']
char='}': matching='}'→'{', stack[-1]='{' ✓ → pop → []
Empty stack → return True ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

The stack perfectly models "what's currently open."
When we see a close bracket, the stack top tells us what should close.

This pattern appears in:
  - Expression evaluation
  - HTML/XML tag validation
  - Compiler design (matching braces in code)

The matching dictionary maps close→open for clean comparison.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

stack = []
match = {')':'(', '}':'{', ']':'['}
for c in s:
    if c in '([{':
        stack.append(c)
    else:
        if not stack or stack[-1] != match[c]:
            return False
        stack.pop()
return not stack

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — one pass
Space: O(n) — stack can hold up to n/2 open brackets

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Returning True when stack is non-empty (unclosed brackets remain!)
2. Not checking if stack is empty before popping
3. Using a counter instead of stack (can't handle wrong ORDER)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC1249 — Minimum Remove to Make Valid Parentheses
  Remove the minimum brackets to make the string valid.
  Which brackets to remove? Use stack to track indices!

---

*Next up: Problem 41 of 250*
