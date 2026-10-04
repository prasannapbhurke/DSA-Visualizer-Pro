# 📘 Problem 105 of 250 — LeetCode 48: Rotate Image

> **Pattern:** Matrix | **Roadmap Position:** #105/250

---

LeetCode 48 — Rotate Image
Problem #105 of 250 | Difficulty: Medium | Pattern: Matrix — Transpose + Flip

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 48: Rotate Image | Medium

Before anything else:
  → Read the problem statement carefully
  → Identify: what is the INPUT? what is the OUTPUT?
  → Write 2 examples by hand to confirm your understanding

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Matrix — Transpose + Flip

Core Insight:
Rotate 90° clockwise = Transpose + Reverse each row.
Transpose: swap matrix[i][j] with matrix[j][i]
Then reverse each row left-to-right.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Approach:
In-place rotation in two steps:
1. Transpose the matrix (flip along main diagonal)
2. Reverse each row

Why does this work?
  Original: (i,j) → after transpose: (j,i) → after row reverse: (j, n-1-i)
  90° clockwise rule: (i,j) → (j, n-1-i) ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

n = len(matrix)
# Transpose
for i in range(n):
    for j in range(i+1, n):
        matrix[i][j], matrix[j][i] = matrix[j][i], matrix[i][j]
# Reverse each row
for row in matrix:
    row.reverse()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

[[1,2,3],[4,5,6],[7,8,9]]

Transpose: swap (0,1)↔(1,0), (0,2)↔(2,0), (1,2)↔(2,1)
[[1,4,7],[2,5,8],[3,6,9]]

Reverse each row:
[[7,4,1],[8,5,2],[9,6,3]] ✓ (90° clockwise)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Matrix rotation = transpose + reverse rows.
For counter-clockwise: transpose + reverse columns.
For 180°: reverse rows + reverse columns.

Remember this formula — it's used in many matrix problems.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

n = len(matrix)
# Transpose
for i in range(n):
    for j in range(i+1, n):
        matrix[i][j], matrix[j][i] = matrix[j][i], matrix[i][j]
# Reverse each row
for row in matrix:
    row.reverse()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n²), Space: O(1)

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

*Next up: Problem 106 of 250*
