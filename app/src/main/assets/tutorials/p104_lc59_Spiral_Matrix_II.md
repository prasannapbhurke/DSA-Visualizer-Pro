# 📘 Problem 104 of 250 — LeetCode 59: Spiral Matrix II

> **Pattern:** Matrix | **Roadmap Position:** #104/250

---

LeetCode 59 — Spiral Matrix II
Problem #104 of 250 | Difficulty: Medium | Pattern: Matrix — Boundary Shrinking

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 59: Spiral Matrix II | Medium

Before anything else:
  → Read the problem statement carefully
  → Identify: what is the INPUT? what is the OUTPUT?
  → Write 2 examples by hand to confirm your understanding

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Matrix — Boundary Shrinking

Core Insight:
Same as Spiral Matrix I, but FILL instead of read.
Fill numbers 1 to n² in spiral order using the same boundary-shrinking technique.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Approach:
Create n×n matrix. Use boundaries top/bottom/left/right.
Fill counter from 1 to n².
Same traversal order: right → down → left → up. Shrink after each.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

matrix = [[0]*n for _ in range(n)]
num = 1
top,bot,left,right = 0,n-1,0,n-1
while num <= n*n:
    for c in range(left,right+1): matrix[top][c]=num; num+=1
    top += 1
    for r in range(top,bot+1): matrix[r][right]=num; num+=1
    right -= 1
    for c in range(right,left-1,-1): matrix[bot][c]=num; num+=1
    bot -= 1
    for r in range(bot,top-1,-1): matrix[r][left]=num; num+=1
    left += 1
return matrix

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

n=3, create 3×3 matrix
top=0,bot=2,left=0,right=2, num=1

Right (row 0): matrix[0][0..2] = 1,2,3, num=4, top=1
Down (col 2): matrix[1..2][2] = 4,5, num=6, right=1... wait n*n=9
Actually: matrix[1][2]=4, matrix[2][2]=5, right=1, num=6
Left (row 2): matrix[2][1..0] = 6,7, num=8, bot=1
Up (col 0): matrix[1][0] = 8, num=9, left=1
Right (row 1): matrix[1][1] = 9

Result: [[1,2,3],[8,9,4],[7,6,5]] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Same spiral logic as LC54, just writing values instead of reading.
Build the matrix, fill values, return the matrix.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

matrix = [[0]*n for _ in range(n)]
num = 1
top,bot,left,right = 0,n-1,0,n-1
while num <= n*n:
    for c in range(left,right+1): matrix[top][c]=num; num+=1
    top += 1
    for r in range(top,bot+1): matrix[r][right]=num; num+=1
    right -= 1
    for c in range(right,left-1,-1): matrix[bot][c]=num; num+=1
    bot -= 1
    for r in range(bot,top-1,-1): matrix[r][left]=num; num+=1
    left += 1
return matrix

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n²), Space: O(n²)

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

*Next up: Problem 105 of 250*
