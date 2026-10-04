# 📘 Problem 44 of 250 — LeetCode 844: Backspace String Compare

> **Pattern:** Stack / Two Pointers | **Roadmap Position:** #44/250

---

LeetCode 844 — Backspace String Compare
Problem #44 of 250 | Difficulty: Easy | Pattern: Stack — Simulation or Two Pointers

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given two strings s and t where '#' means backspace,
return true if they're equal after processing.

Example:
  s = "ab#c", t = "ad#c"
  s: a,b → backspace removes b → "ac"
  t: a,d → backspace removes d → "ac"
  "ac" == "ac" → true

Example:
  s = "ab##", t = "c#d#"
  s: a,b → remove b → a → remove a → ""
  t: c → remove c → "" → d → remove d → ""
  "" == "" → true

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Stack approach:
  Regular char → push
  '#' → pop (backspace)
  At end, compare stacks

Two-pointer approach (O(1) space):
  Process from RIGHT to LEFT
  Skip characters that will be deleted by future '#'s
  Compare remaining characters

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Imagine typing on a keyboard with backspace:
  a,b,#,c → you type 'a', 'b', hit backspace, then 'c' → "ac"

Stack simulates this perfectly:
  letter → push
  # → pop (erase previous)

Compare the final strings after "typing" both.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Stack approach:
def backspaceCompare(s, t):
    def process(string):
        stack = []
        for c in string:
            if c != '#':
                stack.append(c)
            elif stack:
                stack.pop()
        return ''.join(stack)

    return process(s) == process(t)

# Two-pointer O(1) space:
def backspaceCompare(s, t):
    i, j = len(s)-1, len(t)-1
    skip_s = skip_t = 0
    while i >= 0 or j >= 0:
        while i >= 0:
            if s[i] == '#': skip_s += 1; i -= 1
            elif skip_s > 0: skip_s -= 1; i -= 1
            else: break
        while j >= 0:
            if t[j] == '#': skip_t += 1; j -= 1
            elif skip_t > 0: skip_t -= 1; j -= 1
            else: break
        if i >= 0 and j >= 0 and s[i] != t[j]: return False
        if (i >= 0) != (j >= 0): return False
        i -= 1; j -= 1
    return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

s = "ab#c", t = "ad#c"

process("ab#c"):
  a → [a], b → [a,b], # → [a] (pop b), c → [a,c]
  Result: "ac"

process("ad#c"):
  a → [a], d → [a,d], # → [a] (pop d), c → [a,c]
  Result: "ac"

"ac" == "ac" → True ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Stack elegantly simulates typing with backspace.
Two-pointer approach processes from right to left,
counting backspaces to skip characters — O(1) space!

The right-to-left approach works because:
  When we see '#', we know the PREVIOUS character gets deleted.
  Processing backwards lets us handle this without a stack.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def process(s):
    stack = []
    for c in s:
        if c != '#': stack.append(c)
        elif stack: stack.pop()
    return ''.join(stack)
return process(s) == process(t)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Stack: O(m+n) time, O(m+n) space
Two-pointer: O(m+n) time, O(1) space

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Popping from empty stack on '#' (check stack before popping)
2. Not handling multiple consecutive '#' correctly
3. Comparing character by character without processing backspaces first

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Can you solve it with O(1) extra space?
That's the two-pointer approach from right to left!

---

*Next up: Problem 45 of 250*
