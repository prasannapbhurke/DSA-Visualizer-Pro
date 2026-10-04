# 📘 Problem 45 of 250 — LeetCode 71: Simplify Path

> **Pattern:** Stack | **Roadmap Position:** #45/250

---

LeetCode 71 — Simplify Path
Problem #45 of 250 | Difficulty: Medium | Pattern: Stack — Path Processing

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Given an UNIX absolute path, simplify it.
Rules:
  '.' = current directory (ignore it)
  '..' = parent directory (go up one level)
  '//' = treat as single '/'
  Any other name = directory name

Example:
  "/home/"  → "/home"
  "/../"    → "/"  (can't go above root)
  "/home//foo/" → "/home/foo"
  "/a/./b/../../c/" → "/c"
    a → a, . → stay, b → a/b, .. → a, .. → root, c → c

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Split by '/'. Process each part:
  "" or ".": skip
  "..": pop from stack (go up)
  anything else: push onto stack

  "/a/./b/../../c/"

  split: ['', 'a', '.', 'b', '..', '..', 'c', '']
  "" → skip
  "a" → push → stack=['a']
  "." → skip
  "b" → push → stack=['a','b']
  ".." → pop → stack=['a']
  ".." → pop → stack=[]
  "c" → push → stack=['c']
  "" → skip

  Result: "/" + "/".join(['c']) = "/c" 

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think of each directory as a level on a stack.
  Entering a dir → push
  '..' → go back up → pop
  '.' or empty → stay → skip

At end, join the stack with '/' to build the path.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def simplifyPath(path):
    parts = path.split('/')
    stack = []

    for part in parts:
        if part == '' or part == '.':
            continue          # Ignore empty segments and current dir
        elif part == '..':
            if stack:
                stack.pop()   # Go up (but not above root)
        else:
            stack.append(part)  # Valid directory name

    return '/' + '/'.join(stack)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

path = "/home//foo/"

split('/') = ['', 'home', '', 'foo', '']

'': skip
'home': push → ['home']
'': skip
'foo': push → ['home','foo']
'': skip

Result: "/" + "home/foo" = "/home/foo" ✓

path = "/../"
split = ['', '..', '']
'': skip
'..': stack empty, can't pop → stack=[]
'': skip
Result: "/" + "" = "/" ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Stack represents the directory hierarchy.
Each push is going deeper, each pop is going up.

Key observations:
  1. Split on '/' to get path components
  2. Stack handles '..' elegantly (just pop)
  3. Check stack non-empty before popping (can't go above root)
  4. Join with '/' and prepend '/' for absolute path

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

stack = []
for part in path.split('/'):
    if part in ('', '.'): continue
    elif part == '..':
        if stack: stack.pop()
    else: stack.append(part)
return '/' + '/'.join(stack)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — split + process
Space: O(n) — stack

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not handling empty strings from split ('//' gives empty between)
2. Popping when stack is empty (going above root → stay at root)
3. Forgetting to prepend '/' to the result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

What if the path could be relative (not starting with '/')?
What if you needed to handle Windows paths (with \)?

---

*Next up: Problem 46 of 250*
