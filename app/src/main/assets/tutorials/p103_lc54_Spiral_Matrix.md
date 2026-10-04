# 📘 Problem 103 of 250 — LeetCode 54: Spiral Matrix

> **Pattern:** Matrix | **Roadmap Position:** #103/250

---

LeetCode 54 — Spiral Matrix
Problem #103 of 250 | Difficulty: Medium | Pattern: Matrix — Boundary Shrinking

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 54: Spiral Matrix | Medium

Before anything else:
  → Read the problem statement carefully
  → Identify: what is the INPUT? what is the OUTPUT?
  → Write 2 examples by hand to confirm your understanding

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Matrix — Boundary Shrinking

Core Insight:
Use four boundaries: top, bottom, left, right.
Traverse: right along top → down along right → left along bottom → up along left.
Shrink boundaries after each traversal direction.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Approach:
Maintain 4 pointers: top=0, bottom=m-1, left=0, right=n-1
While top<=bottom and left<=right:
  Go right across top row, then top++
  Go down right column, then right--
  Go left across bottom row (if top<=bottom), then bottom--
  Go up left column (if left<=right), then left--

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

result = []
top, bot, left, right = 0, m-1, 0, n-1
while top<=bot and left<=right:
    for c in range(left,right+1): result.append(matrix[top][c])
    top += 1
    for r in range(top,bot+1): result.append(matrix[r][right])
    right -= 1
    if top<=bot:
        for c in range(right,left-1,-1): result.append(matrix[bot][c])
        bot -= 1
    if left<=right:
        for r in range(bot,top-1,-1): result.append(matrix[r][left])
        left += 1
return result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

matrix = [[1,2,3],[4,5,6],[7,8,9]]
top=0,bot=2,left=0,right=2

Right: 1,2,3 → top=1
Down: 6,9 → right=1
Left: 8,7 → bot=1
Up: 4 → left=1
Right: 5 → top=2 > bot=1 → stop

Result: [1,2,3,6,9,8,7,4,5] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Spiral traversal = boundary shrinking.
After each direction, shrink that boundary inward.
The guard conditions (top<=bottom, left<=right) prevent re-visiting.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

result = []
top, bot, left, right = 0, m-1, 0, n-1
while top<=bot and left<=right:
    for c in range(left,right+1): result.append(matrix[top][c])
    top += 1
    for r in range(top,bot+1): result.append(matrix[r][right])
    right -= 1
    if top<=bot:
        for c in range(right,left-1,-1): result.append(matrix[bot][c])
        bot -= 1
    if left<=right:
        for r in range(bot,top-1,-1): result.append(matrix[r][left])
        left += 1
return result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(m*n), Space: O(1) extra

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — think: "what have I seen that looks like this?"
2. Brute force when a smarter approach exists
3. Off-by-one errors in indices/boundaries
4. Not handling edge cases (empty, single, all same)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: Can you identify 2 other LeetCode problems with the same pattern?
Pattern mastery is built through RECOGNITION, not memorization.

---

*Next up: Problem 104 of 250*
