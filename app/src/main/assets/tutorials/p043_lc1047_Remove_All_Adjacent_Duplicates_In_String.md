# 📘 Problem 43 of 250 — LeetCode 1047: Remove All Adjacent Duplicates In String

> **Pattern:** Stack | **Roadmap Position:** #43/250

---

LeetCode 1047 — Remove All Adjacent Duplicates In String
Problem #43 of 250 | Difficulty: Easy | Pattern: Stack — Character Matching

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given a string s, repeatedly remove adjacent duplicate characters.
A "duplicate" = two adjacent identical characters.
Keep removing until no more adjacent duplicates.

Example:
  s = "abbaca"
  "abbaca" → remove "bb" → "aaca"
  "aaca" → remove "aa" → "ca"
  → "ca"

Example:
  s = "azxxzy"
  → remove "xx" → "azzy"
  → remove "zz" → "ay"
  → "ay" 

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Use a stack to process characters:
  If top of stack == current char → duplicate! Pop (remove both).
  Otherwise → push current char.

  s = "abbaca"

  char | stack
  ────────────
   a   | ['a']
   b   | ['a','b']
   b   | ['a'] (b==b → pop!) ← adjacent duplicate removed!
   a   | [] (a==a → pop!)
   c   | ['c']
   a   | ['c','a']

  Result: "ca" ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Stack simulates reading the string and erasing duplicates.
Think of a bubble popping:
  When a new character matches the top → they both disappear!

This handles chain reactions:
  "aabb" → process 'a'→['a'], 'a'→[] (pop!), 'b'→['b'], 'b'→[] (pop!)
  Result: "" (empty)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def removeDuplicates(s):
    stack = []

    for char in s:
        if stack and stack[-1] == char:
            stack.pop()   # Adjacent duplicate found! Remove both.
        else:
            stack.append(char)

    return ''.join(stack)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

s = "azxxzy"

a: stack=['a']
z: stack=['a','z']
x: stack=['a','z','x']
x: x==x → pop → stack=['a','z']
z: z==z → pop → stack=['a']
y: stack=['a','y']

Return "ay" ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

The stack handles CHAIN REACTIONS naturally!

When we pop a duplicate, the stack[-1] might now equal
what's coming NEXT. We don't need to re-scan — the stack
automatically handles consecutive reactions.

Example: "abba"
  a → [a]
  b → [a,b]
  b → [a] (pop b)
  a → [] (pop a) ← chain reaction!
  
Result: "" ✓ All removed!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

stack = []
for c in s:
    if stack and stack[-1] == c:
        stack.pop()
    else:
        stack.append(c)
return ''.join(stack)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — each character pushed and popped at most once
Space: O(n) — stack

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Using a loop to repeatedly scan and remove (O(n²)!)
   Stack does it in ONE pass.
2. Not joining stack at end (it's a list, need ''.join())

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LC1209 — Remove All Adjacent Duplicates in String II
  Remove groups of k adjacent identical characters.
  Stack holds (char, count) pairs!

---

*Next up: Problem 44 of 250*
