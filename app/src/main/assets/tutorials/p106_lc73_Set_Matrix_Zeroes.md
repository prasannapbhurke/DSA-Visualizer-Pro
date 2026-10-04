# 📘 Problem 106 of 250 — LeetCode 73: Set Matrix Zeroes

> **Pattern:** Matrix | **Roadmap Position:** #106/250

---

LeetCode 73 — Set Matrix Zeroes
Problem #106 of 250 | Difficulty: Medium | Pattern: Matrix — In-place Marker

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 73: Set Matrix Zeroes | Medium

Before anything else:
  → Read the problem statement carefully
  → Identify: what is the INPUT? what is the OUTPUT?
  → Write 2 examples by hand to confirm your understanding

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Matrix — In-place Marker

Core Insight:
When matrix[i][j]=0: mark its entire row AND column as zero.
Naive: mark rows/cols then apply. But marking during traversal causes cascade.
Smart: use FIRST ROW and FIRST COLUMN as markers!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Approach:
1. Check if first row/column contain zeros (special case).
2. Use matrix[i][0] and matrix[0][j] as markers.
3. For each zero in matrix[i][j]: mark matrix[i][0]=0 and matrix[0][j]=0.
4. Apply zeroes to interior using markers.
5. Handle first row and column separately.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

m, n = len(matrix), len(matrix[0])
first_row_zero = 0 in matrix[0]
first_col_zero = any(matrix[i][0]==0 for i in range(m))
for i in range(1,m):
    for j in range(1,n):
        if matrix[i][j]==0:
            matrix[i][0] = matrix[0][j] = 0
for i in range(1,m):
    for j in range(1,n):
        if matrix[i][0]==0 or matrix[0][j]==0:
            matrix[i][j] = 0
if first_row_zero:
    for j in range(n): matrix[0][j] = 0
if first_col_zero:
    for i in range(m): matrix[i][0] = 0

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

matrix = [[1,1,1],[1,0,1],[1,1,1]]

Find zero at (1,1):
  Mark matrix[1][0]=0, matrix[0][1]=0

Now: [[1,0,1],[0,0,1],[1,1,1]]

Apply: for each i>0,j>0: if matrix[i][0]==0 or matrix[0][j]==0 → matrix[i][j]=0
  matrix[1][1]: matrix[1][0]=0 → set 0
  matrix[1][2]: matrix[1][0]=0 → set 0
  matrix[0][1]=0 → matrix[0][1] already handled...

Result: [[1,0,1],[0,0,0],[1,0,1]] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Use the matrix itself as scratch space for markers.
This achieves O(1) space by reusing row 0 and column 0 as flag arrays.
But first check whether row 0 or col 0 themselves have zeros!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

m, n = len(matrix), len(matrix[0])
first_row_zero = 0 in matrix[0]
first_col_zero = any(matrix[i][0]==0 for i in range(m))
for i in range(1,m):
    for j in range(1,n):
        if matrix[i][j]==0:
            matrix[i][0] = matrix[0][j] = 0
for i in range(1,m):
    for j in range(1,n):
        if matrix[i][0]==0 or matrix[0][j]==0:
            matrix[i][j] = 0
if first_row_zero:
    for j in range(n): matrix[0][j] = 0
if first_col_zero:
    for i in range(m): matrix[i][0] = 0

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(m*n), Space: O(1)

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

*Next up: Problem 107 of 250*
