# 📚 DSA Learning Roadmap — Part 1 | Section 3/5
## Intermediate (Problems 101-150): Binary Search, Recursion, Linked Lists

> Study these problems in ORDER. Each one builds on the previous.

---

## Problem 101/250 — LeetCode #1052: Grumpy Bookstore Owner

**Pattern:** `Sliding Window` | **Position in Roadmap:** #101

```
LeetCode 1052 — Grumpy Bookstore Owner
Problem #101 of 250 | Difficulty: Medium | Pattern: Fixed Sliding Window

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 1052: Grumpy Bookstore Owner | Medium

Before anything else:
  → Read the problem statement carefully
  → Identify: what is the INPUT? what is the OUTPUT?
  → Write 2 examples by hand to confirm your understanding

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Fixed Sliding Window

Core Insight:
The bookstore owner can suppress grumpiness for a window of 'minutes' consecutive minutes.
Baseline = satisfaction when owner is NOT grumpy.
Extra = maximum additional customers gained from the best 'minutes' window of grumpy minutes.
Answer = baseline + max_extra

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Approach:
1. Compute baseline: sum of customers[i] where grumpy[i]=0
2. Find window of size 'minutes' that captures the MOST customers where grumpy[i]=1
3. Add that maximum extra to the baseline

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

baseline = sum(c for c,g in zip(customers,grumpy) if g==0)
window = sum(c*g for c,g in zip(customers[:k], grumpy[:k]))
max_extra = window
for i in range(k, len(customers)):
    window += customers[i]*grumpy[i] - customers[i-k]*grumpy[i-k]
    max_extra = max(max_extra, window)
return baseline + max_extra

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

customers=[1,0,1,2,1,1,7,5], grumpy=[0,1,0,1,0,1,0,1], minutes=3

baseline = 1+1+1+7 = 10 (non-grumpy minutes: 0,2,4,6)

Sliding window of size 3 over grumpy minutes:
[0,1,2]: extra = 0 (customers[1]=0, grumpy[1]=1)
[1,2,3]: extra = 2 (customers[3]=2, grumpy[3]=1)
[2,3,4]: extra = 2
[3,4,5]: extra = 2+1=3
[4,5,6]: extra = 1
[5,6,7]: extra = 1+5=6 ← max extra!

Answer = 10 + 6 = 16

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Fixed-size sliding window: add new, remove old, track max.
Separation of baseline + bonus makes the problem tractable.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

baseline = sum(c for c,g in zip(customers,grumpy) if g==0)
window = sum(c*g for c,g in zip(customers[:k], grumpy[:k]))
max_extra = window
for i in range(k, len(customers)):
    window += customers[i]*grumpy[i] - customers[i-k]*grumpy[i-k]
    max_extra = max(max_extra, window)
return baseline + max_extra

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(1)

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
```

---

## Problem 102/250 — LeetCode #189: Rotate Array

**Pattern:** `Array` | **Position in Roadmap:** #102

```
LeetCode 189 — Rotate Array
Problem #102 of 250 | Difficulty: Medium | Pattern: Array — Triple Reversal

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 189: Rotate Array | Medium

Before anything else:
  → Read the problem statement carefully
  → Identify: what is the INPUT? what is the OUTPUT?
  → Write 2 examples by hand to confirm your understanding

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Array — Triple Reversal

Core Insight:
Rotating right by k is equivalent to:
1. Reverse entire array
2. Reverse first k elements
3. Reverse remaining n-k elements
This uses O(1) space!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Approach:
Triple reversal trick:
  k %= n (handle k > n)
  Reverse all: [1,2,3,4,5,6,7] → [7,6,5,4,3,2,1]
  Reverse first k=3: [5,6,7,4,3,2,1]... wait
  Actually: reverse first k → [7,6,5] then reverse rest [4,3,2,1]
  → [5,6,7,1,2,3,4]... let me recount

For k=3, n=7: rotate right by 3
  [1,2,3,4,5,6,7] → [5,6,7,1,2,3,4]
  Reverse all: [7,6,5,4,3,2,1]
  Reverse first k=3: [5,6,7,4,3,2,1]
  Reverse last n-k=4: [5,6,7,1,2,3,4] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def rotate(nums, k):
    n = len(nums)
    k %= n
    nums.reverse()
    nums[:k] = reversed(nums[:k])
    nums[k:] = reversed(nums[k:])

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

nums=[1,2,3,4,5,6,7], k=3

Reverse all: [7,6,5,4,3,2,1]
Reverse first 3: [5,6,7,4,3,2,1]
Reverse last 4: [5,6,7,1,2,3,4] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

The triple reversal trick works for rotating any array in O(1) space.
Memorize: reverse all → reverse first k → reverse last n-k.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def rotate(nums, k):
    n = len(nums)
    k %= n
    nums.reverse()
    nums[:k] = reversed(nums[:k])
    nums[k:] = reversed(nums[k:])

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(1)

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
```

---

## Problem 103/250 — LeetCode #54: Spiral Matrix

**Pattern:** `Matrix` | **Position in Roadmap:** #103

```
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
```

---

## Problem 104/250 — LeetCode #59: Spiral Matrix II

**Pattern:** `Matrix` | **Position in Roadmap:** #104

```
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
```

---

## Problem 105/250 — LeetCode #48: Rotate Image

**Pattern:** `Matrix` | **Position in Roadmap:** #105

```
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
```

---

## Problem 106/250 — LeetCode #73: Set Matrix Zeroes

**Pattern:** `Matrix` | **Position in Roadmap:** #106

```
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
```

---

## Problem 107/250 — LeetCode #289: Game of Life

**Pattern:** `Matrix` | **Position in Roadmap:** #107

```
LeetCode 289 — Game of Life
Problem #107 of 250 | Difficulty: Medium | Pattern: Matrix — In-place State Machine

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 289: Game of Life | Medium

Before anything else:
  → Read the problem statement carefully
  → Identify: what is the INPUT? what is the OUTPUT?
  → Write 2 examples by hand to confirm your understanding

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Matrix — In-place State Machine

Core Insight:
Cells change based on neighbor counts.
But all changes happen simultaneously! If we update in-place, later cells see updated neighbors.
Solution: use ENCODED states to represent old and new values simultaneously.
  0 → 0: stays 0 (0)
  1 → 0: was alive, now dead (2)
  0 → 1: was dead, now alive (3)
  1 → 1: stays 1 (1)
Old state = value % 2, New state = value // 2... wait
Actually encode: 2 = was 1, now 0; 3 = was 0, now 1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Approach:
For each cell, count neighbors (using old values: value&1 or value%2).
Apply rules, encoding new state.
After processing all cells, decode: 0,3→0... wait:
  Use: encode 'was live, now dead' as value=2 (bit 1 set in bit 1)
  Then final: value = value >> 1 for all cells... actually:
  
Simple approach: use original values (1,0), store change using 2=live→dead, -1=dead→live.
Then: cells with -1 become 1, cells with 2 become 0.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def count_neighbors(board, r, c):
    dirs = [(-1,-1),(-1,0),(-1,1),(0,-1),(0,1),(1,-1),(1,0),(1,1)]
    return sum(1 for dr,dc in dirs
               if 0<=r+dr<len(board) and 0<=c+dc<len(board[0])
               and abs(board[r+dr][c+dc])==1)

for r in range(len(board)):
    for c in range(len(board[0])):
        live = count_neighbors(board,r,c)
        if board[r][c]==1 and live not in (2,3): board[r][c]=-1
        elif board[r][c]==0 and live==3: board[r][c]=2
for r in range(len(board)):
    for c in range(len(board[0])):
        if board[r][c]==-1: board[r][c]=0
        elif board[r][c]==2: board[r][c]=1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

board = [[0,1,0],[0,0,1],[1,1,1]]

Count live neighbors for each cell.
Apply rules:
  (0,1)=1, neighbors: (0,0)=0,(0,2)=0,(1,0)=0,(1,1)=0,(1,2)=1 → 1 neighbor → die
  (1,2)=1, neighbors include (0,1)=1,(2,1)=1,(2,2)=1 → 3 live → survive

... result: [[0,0,0],[1,0,1],[0,1,1]]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

In-place simulation with encoded states avoids needing a copy.
The old state must be preserved while computing new state.
Encoding (using extra bits or negative values) is the key trick.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def count_neighbors(board, r, c):
    dirs = [(-1,-1),(-1,0),(-1,1),(0,-1),(0,1),(1,-1),(1,0),(1,1)]
    return sum(1 for dr,dc in dirs
               if 0<=r+dr<len(board) and 0<=c+dc<len(board[0])
               and abs(board[r+dr][c+dc])==1)

for r in range(len(board)):
    for c in range(len(board[0])):
        live = count_neighbors(board,r,c)
        if board[r][c]==1 and live not in (2,3): board[r][c]=-1
        elif board[r][c]==0 and live==3: board[r][c]=2
for r in range(len(board)):
    for c in range(len(board[0])):
        if board[r][c]==-1: board[r][c]=0
        elif board[r][c]==2: board[r][c]=1

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
```

---

## Problem 108/250 — LeetCode #498: Diagonal Traverse

**Pattern:** `Matrix` | **Position in Roadmap:** #108

```
LeetCode 498 — Diagonal Traverse
Problem #108 of 250 | Difficulty: Medium | Pattern: Matrix — Direction Toggle

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 498: Diagonal Traverse | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Matrix — Direction Toggle

Process cells in specific order. Use boundaries or directions array for traversal.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Matrix — Direction Toggle problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Diagonal Traverse.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Process cells in specific order. Use boundaries or directions array for traversal.

This problem is in your roadmap at position #108 because it reinforces
the Matrix — Direction Toggle pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Matrix — Direction Toggle

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Diagonal Traverse:
  → Find one more problem using the same Matrix — Direction Toggle pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 109/250 — LeetCode #885: Spiral Matrix III

**Pattern:** `Matrix` | **Position in Roadmap:** #109

```
LeetCode 885 — Spiral Matrix III
Problem #109 of 250 | Difficulty: Medium | Pattern: Matrix — Spiral Traversal

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 885: Spiral Matrix III | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Matrix — Spiral Traversal

Process cells in specific order. Use boundaries or directions array for traversal.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Matrix — Spiral Traversal problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Spiral Matrix III.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Process cells in specific order. Use boundaries or directions array for traversal.

This problem is in your roadmap at position #109 because it reinforces
the Matrix — Spiral Traversal pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Matrix — Spiral Traversal

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Spiral Matrix III:
  → Find one more problem using the same Matrix — Spiral Traversal pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 110/250 — LeetCode #6: Zigzag Conversion

**Pattern:** `String` | **Position in Roadmap:** #110

```
LeetCode 6 — Zigzag Conversion
Problem #110 of 250 | Difficulty: Medium | Pattern: String — Row Simulation

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 6: Zigzag Conversion | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: String — Row Simulation

Core technique: String — Row Simulation. Identify what data structure enables O(1) or O(log n) operations.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For String — Row Simulation problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Zigzag Conversion.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Core technique: String — Row Simulation. Identify what data structure enables O(1) or O(log n) operations.

This problem is in your roadmap at position #110 because it reinforces
the String — Row Simulation pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: String — Row Simulation

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Zigzag Conversion:
  → Find one more problem using the same String — Row Simulation pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 111/250 — LeetCode #75: Sort Colors

**Pattern:** `Dutch National Flag` | **Position in Roadmap:** #111

```
LeetCode 75 — Sort Colors
Problem #111 of 250 | Difficulty: Medium | Pattern: Dutch National Flag / Three-Way Partition

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 75: Sort Colors | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Dutch National Flag / Three-Way Partition

Core technique: Dutch National Flag / Three-Way Partition. Identify what data structure enables O(1) or O(log n) operations.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Dutch National Flag / Three-Way Partition problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Sort Colors.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Core technique: Dutch National Flag / Three-Way Partition. Identify what data structure enables O(1) or O(log n) operations.

This problem is in your roadmap at position #111 because it reinforces
the Dutch National Flag  pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Dutch National Flag / Three-Way Partition

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Sort Colors:
  → Find one more problem using the same Dutch National Flag  pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 112/250 — LeetCode #31: Next Permutation

**Pattern:** `Array` | **Position in Roadmap:** #112

```
LeetCode 31 — Next Permutation
Problem #112 of 250 | Difficulty: Medium | Pattern: Array — Find Rightmost Dip + Swap + Reverse

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 31: Next Permutation | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Array — Find Rightmost Dip + Swap + Reverse

Think about what properties the array has. Use index as hash, negation as marker, etc.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Array — Find Rightmost Dip + Swap + Reverse problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Next Permutation.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think about what properties the array has. Use index as hash, negation as marker, etc.

This problem is in your roadmap at position #112 because it reinforces
the Array — Find Rightmost Dip + Swap + Reverse pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Array — Find Rightmost Dip + Swap + Reverse

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Next Permutation:
  → Find one more problem using the same Array — Find Rightmost Dip + Swap + Reverse pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 113/250 — LeetCode #41: First Missing Positive

**Pattern:** `Array` | **Position in Roadmap:** #113

```
LeetCode 41 — First Missing Positive
Problem #113 of 250 | Difficulty: Hard | Pattern: Array — Index as Hash

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 41: First Missing Positive | Hard

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Array — Index as Hash

Think about what properties the array has. Use index as hash, negation as marker, etc.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Array — Index as Hash problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for First Missing Positive.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think about what properties the array has. Use index as hash, negation as marker, etc.

This problem is in your roadmap at position #113 because it reinforces
the Array — Index as Hash pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Array — Index as Hash

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving First Missing Positive:
  → Find one more problem using the same Array — Index as Hash pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 114/250 — LeetCode #268: Missing Number

**Pattern:** `Array / XOR` | **Position in Roadmap:** #114

```
LeetCode 268 — Missing Number
Problem #114 of 250 | Difficulty: Easy | Pattern: Array / XOR / Gauss Sum

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 268: Missing Number | Easy

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Array / XOR / Gauss Sum

Think about what properties the array has. Use index as hash, negation as marker, etc.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Array / XOR / Gauss Sum problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Missing Number.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think about what properties the array has. Use index as hash, negation as marker, etc.

This problem is in your roadmap at position #114 because it reinforces
the Array  pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Array / XOR / Gauss Sum

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Missing Number:
  → Find one more problem using the same Array  pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 115/250 — LeetCode #448: Find All Numbers Disappeared in an Array

**Pattern:** `Array` | **Position in Roadmap:** #115

```
LeetCode 448 — Find All Numbers Disappeared in an Array
Problem #115 of 250 | Difficulty: Easy | Pattern: Array — Negation as Mark

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 448: Find All Numbers Disappeared in an Array | Easy

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Array — Negation as Mark

Think about what properties the array has. Use index as hash, negation as marker, etc.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Array — Negation as Mark problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Find All Numbers Disappeared in an Array.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think about what properties the array has. Use index as hash, negation as marker, etc.

This problem is in your roadmap at position #115 because it reinforces
the Array — Negation as Mark pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Array — Negation as Mark

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Find All Numbers Disappeared in an Array:
  → Find one more problem using the same Array — Negation as Mark pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 116/250 — LeetCode #287: Find the Duplicate Number

**Pattern:** `Fast & Slow` | **Position in Roadmap:** #116

```
LeetCode 287 — Find the Duplicate Number
Problem #116 of 250 | Difficulty: Medium | Pattern: Fast & Slow Pointers (Floyd's)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 287: Find the Duplicate Number | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Fast & Slow Pointers (Floyd's)

Core technique: Fast & Slow Pointers (Floyd's). Identify what data structure enables O(1) or O(log n) operations.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Fast & Slow Pointers (Floyd's) problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Find the Duplicate Number.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Core technique: Fast & Slow Pointers (Floyd's). Identify what data structure enables O(1) or O(log n) operations.

This problem is in your roadmap at position #116 because it reinforces
the Fast & Slow Pointers (Floyd's) pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Fast & Slow Pointers (Floyd's)

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Find the Duplicate Number:
  → Find one more problem using the same Fast & Slow Pointers (Floyd's) pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 117/250 — LeetCode #442: Find All Duplicates in an Array

**Pattern:** `Array` | **Position in Roadmap:** #117

```
LeetCode 442 — Find All Duplicates in an Array
Problem #117 of 250 | Difficulty: Medium | Pattern: Array — Negation as Mark

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 442: Find All Duplicates in an Array | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Array — Negation as Mark

Think about what properties the array has. Use index as hash, negation as marker, etc.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Array — Negation as Mark problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Find All Duplicates in an Array.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Think about what properties the array has. Use index as hash, negation as marker, etc.

This problem is in your roadmap at position #117 because it reinforces
the Array — Negation as Mark pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Array — Negation as Mark

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Find All Duplicates in an Array:
  → Find one more problem using the same Array — Negation as Mark pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 118/250 — LeetCode #380: Insert Delete GetRandom O(1)

**Pattern:** `Hash Map` | **Position in Roadmap:** #118

```
LeetCode 380 — Insert Delete GetRandom O(1)
Problem #118 of 250 | Difficulty: Medium | Pattern: HashMap + Array

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 380: Insert Delete GetRandom O(1) | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: HashMap + Array

Core technique: HashMap + Array. Identify what data structure enables O(1) or O(log n) operations.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For HashMap + Array problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Insert Delete GetRandom O(1).
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Core technique: HashMap + Array. Identify what data structure enables O(1) or O(log n) operations.

This problem is in your roadmap at position #118 because it reinforces
the HashMap + Array pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: HashMap + Array

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Insert Delete GetRandom O(1):
  → Find one more problem using the same HashMap + Array pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 119/250 — LeetCode #36: Valid Sudoku

**Pattern:** `Hash Set` | **Position in Roadmap:** #119

```
LeetCode 36 — Valid Sudoku
Problem #119 of 250 | Difficulty: Medium | Pattern: HashSet Validation

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 36: Valid Sudoku | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: HashSet Validation

Core technique: HashSet Validation. Identify what data structure enables O(1) or O(log n) operations.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For HashSet Validation problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Valid Sudoku.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Core technique: HashSet Validation. Identify what data structure enables O(1) or O(log n) operations.

This problem is in your roadmap at position #119 because it reinforces
the HashSet Validation pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: HashSet Validation

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Valid Sudoku:
  → Find one more problem using the same HashSet Validation pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 120/250 — LeetCode #54: Spiral Matrix

**Pattern:** `Matrix` | **Position in Roadmap:** #120

```
LeetCode 54 — Spiral Matrix
Problem #120 of 250 | Difficulty: Medium | Pattern: Matrix Boundary Traversal

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 54: Spiral Matrix | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Matrix Boundary Traversal

Process cells in specific order. Use boundaries or directions array for traversal.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Matrix Boundary Traversal problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Spiral Matrix.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Process cells in specific order. Use boundaries or directions array for traversal.

This problem is in your roadmap at position #120 because it reinforces
the Matrix Boundary Traversal pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Matrix Boundary Traversal

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Spiral Matrix:
  → Find one more problem using the same Matrix Boundary Traversal pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 121/250 — LeetCode #56: Merge Intervals

**Pattern:** `Intervals` | **Position in Roadmap:** #121

```
LeetCode 56 — Merge Intervals
Problem #121 of 250 | Difficulty: Medium | Pattern: Intervals — Sort and Merge

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 56: Merge Intervals | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Intervals — Sort and Merge

Sort by start time. Process in order. Track current interval's end for merging decisions.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Intervals — Sort and Merge problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Merge Intervals.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Sort by start time. Process in order. Track current interval's end for merging decisions.

This problem is in your roadmap at position #121 because it reinforces
the Intervals — Sort and Merge pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Intervals — Sort and Merge

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Merge Intervals:
  → Find one more problem using the same Intervals — Sort and Merge pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 122/250 — LeetCode #57: Insert Interval

**Pattern:** `Intervals` | **Position in Roadmap:** #122

```
LeetCode 57 — Insert Interval
Problem #122 of 250 | Difficulty: Medium | Pattern: Intervals — Binary Search + Merge

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 57: Insert Interval | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Intervals — Binary Search + Merge

Sort by start time. Process in order. Track current interval's end for merging decisions.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Intervals — Binary Search + Merge problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Insert Interval.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Sort by start time. Process in order. Track current interval's end for merging decisions.

This problem is in your roadmap at position #122 because it reinforces
the Intervals — Binary Search + Merge pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Intervals — Binary Search + Merge

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Insert Interval:
  → Find one more problem using the same Intervals — Binary Search + Merge pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 123/250 — LeetCode #435: Non-overlapping Intervals

**Pattern:** `Greedy` | **Position in Roadmap:** #123

```
LeetCode 435 — Non-overlapping Intervals
Problem #123 of 250 | Difficulty: Medium | Pattern: Greedy — Min Removals

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 435: Non-overlapping Intervals | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Greedy — Min Removals

At each step, make the locally optimal choice. Prove it leads to global optimum.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Greedy — Min Removals problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Non-overlapping Intervals.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

At each step, make the locally optimal choice. Prove it leads to global optimum.

This problem is in your roadmap at position #123 because it reinforces
the Greedy — Min Removals pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Greedy — Min Removals

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Non-overlapping Intervals:
  → Find one more problem using the same Greedy — Min Removals pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 124/250 — LeetCode #452: Minimum Number of Arrows to Burst Balloons

**Pattern:** `Greedy / Intervals` | **Position in Roadmap:** #124

```
LeetCode 452 — Minimum Number of Arrows to Burst Balloons
Problem #124 of 250 | Difficulty: Medium | Pattern: Greedy / Intervals

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 452: Minimum Number of Arrows to Burst Balloons | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Greedy / Intervals

At each step, make the locally optimal choice. Prove it leads to global optimum.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Greedy / Intervals problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Minimum Number of Arrows to Burst Balloons.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

At each step, make the locally optimal choice. Prove it leads to global optimum.

This problem is in your roadmap at position #124 because it reinforces
the Greedy  pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Greedy / Intervals

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Minimum Number of Arrows to Burst Balloons:
  → Find one more problem using the same Greedy  pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 125/250 — LeetCode #986: Interval List Intersections

**Pattern:** `Two Pointers` | **Position in Roadmap:** #125

```
LeetCode 986 — Interval List Intersections
Problem #125 of 250 | Difficulty: Medium | Pattern: Two Pointers on Intervals

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 986: Interval List Intersections | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Two Pointers on Intervals

Core technique: Two Pointers on Intervals. Identify what data structure enables O(1) or O(log n) operations.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Two Pointers on Intervals problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Interval List Intersections.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Core technique: Two Pointers on Intervals. Identify what data structure enables O(1) or O(log n) operations.

This problem is in your roadmap at position #125 because it reinforces
the Two Pointers on Intervals pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Two Pointers on Intervals

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Interval List Intersections:
  → Find one more problem using the same Two Pointers on Intervals pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 126/250 — LeetCode #1288: Remove Covered Intervals

**Pattern:** `Intervals` | **Position in Roadmap:** #126

```
LeetCode 1288 — Remove Covered Intervals
Problem #126 of 250 | Difficulty: Medium | Pattern: Intervals — Sort and Filter

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 1288: Remove Covered Intervals | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Intervals — Sort and Filter

Sort by start time. Process in order. Track current interval's end for merging decisions.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Intervals — Sort and Filter problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Remove Covered Intervals.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Sort by start time. Process in order. Track current interval's end for merging decisions.

This problem is in your roadmap at position #126 because it reinforces
the Intervals — Sort and Filter pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Intervals — Sort and Filter

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Remove Covered Intervals:
  → Find one more problem using the same Intervals — Sort and Filter pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 127/250 — LeetCode #763: Partition Labels

**Pattern:** `Greedy` | **Position in Roadmap:** #127

```
LeetCode 763 — Partition Labels
Problem #127 of 250 | Difficulty: Medium | Pattern: Greedy — Last Occurrence

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 763: Partition Labels | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Greedy — Last Occurrence

At each step, make the locally optimal choice. Prove it leads to global optimum.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Greedy — Last Occurrence problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Partition Labels.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

At each step, make the locally optimal choice. Prove it leads to global optimum.

This problem is in your roadmap at position #127 because it reinforces
the Greedy — Last Occurrence pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Greedy — Last Occurrence

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Partition Labels:
  → Find one more problem using the same Greedy — Last Occurrence pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 128/250 — LeetCode #1029: Two City Scheduling

**Pattern:** `Greedy` | **Position in Roadmap:** #128

```
LeetCode 1029 — Two City Scheduling
Problem #128 of 250 | Difficulty: Medium | Pattern: Greedy / Sorting

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 1029: Two City Scheduling | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Greedy / Sorting

At each step, make the locally optimal choice. Prove it leads to global optimum.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Greedy / Sorting problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Two City Scheduling.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

At each step, make the locally optimal choice. Prove it leads to global optimum.

This problem is in your roadmap at position #128 because it reinforces
the Greedy  pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Greedy / Sorting

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Two City Scheduling:
  → Find one more problem using the same Greedy  pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 129/250 — LeetCode #55: Jump Game

**Pattern:** `Greedy` | **Position in Roadmap:** #129

```
LeetCode 55 — Jump Game
Problem #129 of 250 | Difficulty: Medium | Pattern: Greedy — Max Reach

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 55: Jump Game | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Greedy — Max Reach

At each step, make the locally optimal choice. Prove it leads to global optimum.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Greedy — Max Reach problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Jump Game.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

At each step, make the locally optimal choice. Prove it leads to global optimum.

This problem is in your roadmap at position #129 because it reinforces
the Greedy — Max Reach pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Greedy — Max Reach

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Jump Game:
  → Find one more problem using the same Greedy — Max Reach pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 130/250 — LeetCode #45: Jump Game II

**Pattern:** `Greedy` | **Position in Roadmap:** #130

```
LeetCode 45 — Jump Game II
Problem #130 of 250 | Difficulty: Medium | Pattern: Greedy — BFS Level

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 45: Jump Game II | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Greedy — BFS Level

At each step, make the locally optimal choice. Prove it leads to global optimum.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Greedy — BFS Level problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Jump Game II.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

At each step, make the locally optimal choice. Prove it leads to global optimum.

This problem is in your roadmap at position #130 because it reinforces
the Greedy — BFS Level pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Greedy — BFS Level

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Jump Game II:
  → Find one more problem using the same Greedy — BFS Level pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 131/250 — LeetCode #134: Gas Station

**Pattern:** `Greedy` | **Position in Roadmap:** #131

```
LeetCode 134 — Gas Station
Problem #131 of 250 | Difficulty: Medium | Pattern: Greedy — Running Sum

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 134: Gas Station | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Greedy — Running Sum

At each step, make the locally optimal choice. Prove it leads to global optimum.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Greedy — Running Sum problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Gas Station.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

At each step, make the locally optimal choice. Prove it leads to global optimum.

This problem is in your roadmap at position #131 because it reinforces
the Greedy — Running Sum pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Greedy — Running Sum

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Gas Station:
  → Find one more problem using the same Greedy — Running Sum pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 132/250 — LeetCode #704: Binary Search

**Pattern:** `Binary Search` | **Position in Roadmap:** #132

```
LeetCode 704 — Binary Search
Problem #132 of 250 | Difficulty: Easy | Pattern: Binary Search — Classic

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 704: Binary Search | Easy

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search — Classic

Core technique: Binary Search — Classic. Identify what data structure enables O(1) or O(log n) operations.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Binary Search — Classic problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Binary Search.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Core technique: Binary Search — Classic. Identify what data structure enables O(1) or O(log n) operations.

This problem is in your roadmap at position #132 because it reinforces
the Binary Search — Classic pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search — Classic

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Binary Search:
  → Find one more problem using the same Binary Search — Classic pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 133/250 — LeetCode #35: Search Insert Position

**Pattern:** `Binary Search` | **Position in Roadmap:** #133

```
LeetCode 35 — Search Insert Position
Problem #133 of 250 | Difficulty: Easy | Pattern: Binary Search

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 35: Search Insert Position | Easy

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search

Core technique: Binary Search. Identify what data structure enables O(1) or O(log n) operations.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Binary Search problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Search Insert Position.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Core technique: Binary Search. Identify what data structure enables O(1) or O(log n) operations.

This problem is in your roadmap at position #133 because it reinforces
the Binary Search pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Search Insert Position:
  → Find one more problem using the same Binary Search pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 134/250 — LeetCode #69: Sqrt(x)

**Pattern:** `Binary Search` | **Position in Roadmap:** #134

```
LeetCode 69 — Sqrt(x)
Problem #134 of 250 | Difficulty: Easy | Pattern: Binary Search on Answer

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 69: Sqrt(x) | Easy

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search on Answer

Core technique: Binary Search on Answer. Identify what data structure enables O(1) or O(log n) operations.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Binary Search on Answer problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Sqrt(x).
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Core technique: Binary Search on Answer. Identify what data structure enables O(1) or O(log n) operations.

This problem is in your roadmap at position #134 because it reinforces
the Binary Search on Answer pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search on Answer

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Sqrt(x):
  → Find one more problem using the same Binary Search on Answer pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 135/250 — LeetCode #374: Guess Number Higher or Lower

**Pattern:** `Binary Search` | **Position in Roadmap:** #135

```
LeetCode 374 — Guess Number Higher or Lower
Problem #135 of 250 | Difficulty: Easy | Pattern: Binary Search

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 374: Guess Number Higher or Lower | Easy

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search

Core technique: Binary Search. Identify what data structure enables O(1) or O(log n) operations.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Binary Search problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Guess Number Higher or Lower.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Core technique: Binary Search. Identify what data structure enables O(1) or O(log n) operations.

This problem is in your roadmap at position #135 because it reinforces
the Binary Search pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Guess Number Higher or Lower:
  → Find one more problem using the same Binary Search pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 136/250 — LeetCode #278: First Bad Version

**Pattern:** `Binary Search` | **Position in Roadmap:** #136

```
LeetCode 278 — First Bad Version
Problem #136 of 250 | Difficulty: Easy | Pattern: Binary Search — Leftmost True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 278: First Bad Version | Easy

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search — Leftmost True

Core technique: Binary Search — Leftmost True. Identify what data structure enables O(1) or O(log n) operations.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Binary Search — Leftmost True problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for First Bad Version.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Core technique: Binary Search — Leftmost True. Identify what data structure enables O(1) or O(log n) operations.

This problem is in your roadmap at position #136 because it reinforces
the Binary Search — Leftmost True pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search — Leftmost True

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving First Bad Version:
  → Find one more problem using the same Binary Search — Leftmost True pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 137/250 — LeetCode #367: Valid Perfect Square

**Pattern:** `Binary Search` | **Position in Roadmap:** #137

```
LeetCode 367 — Valid Perfect Square
Problem #137 of 250 | Difficulty: Easy | Pattern: Binary Search

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 367: Valid Perfect Square | Easy

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search

Core technique: Binary Search. Identify what data structure enables O(1) or O(log n) operations.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Binary Search problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Valid Perfect Square.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Core technique: Binary Search. Identify what data structure enables O(1) or O(log n) operations.

This problem is in your roadmap at position #137 because it reinforces
the Binary Search pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Valid Perfect Square:
  → Find one more problem using the same Binary Search pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 138/250 — LeetCode #34: Find First and Last Position of Element in Sorted Array

**Pattern:** `Binary Search` | **Position in Roadmap:** #138

```
LeetCode 34 — Find First and Last Position
Problem #138 of 250 | Difficulty: Medium | Pattern: Binary Search — Both Bounds

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 34: Find First and Last Position | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search — Both Bounds

Core technique: Binary Search — Both Bounds. Identify what data structure enables O(1) or O(log n) operations.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Binary Search — Both Bounds problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Find First and Last Position.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Core technique: Binary Search — Both Bounds. Identify what data structure enables O(1) or O(log n) operations.

This problem is in your roadmap at position #138 because it reinforces
the Binary Search — Both Bounds pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search — Both Bounds

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Find First and Last Position:
  → Find one more problem using the same Binary Search — Both Bounds pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 139/250 — LeetCode #33: Search in Rotated Sorted Array

**Pattern:** `Binary Search` | **Position in Roadmap:** #139

```
LeetCode 33 — Search in Rotated Sorted Array
Problem #139 of 250 | Difficulty: Medium | Pattern: Binary Search — Rotated

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 33: Search in Rotated Sorted Array | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search — Rotated

Core technique: Binary Search — Rotated. Identify what data structure enables O(1) or O(log n) operations.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Binary Search — Rotated problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Search in Rotated Sorted Array.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Core technique: Binary Search — Rotated. Identify what data structure enables O(1) or O(log n) operations.

This problem is in your roadmap at position #139 because it reinforces
the Binary Search — Rotated pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search — Rotated

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Search in Rotated Sorted Array:
  → Find one more problem using the same Binary Search — Rotated pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 140/250 — LeetCode #153: Find Minimum in Rotated Sorted Array

**Pattern:** `Binary Search` | **Position in Roadmap:** #140

```
LeetCode 153 — Find Minimum in Rotated Sorted Array
Problem #140 of 250 | Difficulty: Medium | Pattern: Binary Search

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 153: Find Minimum in Rotated Sorted Array | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search

Core technique: Binary Search. Identify what data structure enables O(1) or O(log n) operations.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Binary Search problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Find Minimum in Rotated Sorted Array.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Core technique: Binary Search. Identify what data structure enables O(1) or O(log n) operations.

This problem is in your roadmap at position #140 because it reinforces
the Binary Search pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Find Minimum in Rotated Sorted Array:
  → Find one more problem using the same Binary Search pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 141/250 — LeetCode #154: Find Minimum in Rotated Sorted Array II

**Pattern:** `Binary Search` | **Position in Roadmap:** #141

```
LeetCode 154 — Find Minimum in Rotated Sorted Array II
Problem #141 of 250 | Difficulty: Hard | Pattern: Binary Search with Duplicates

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 154: Find Minimum in Rotated Sorted Array II | Hard

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search with Duplicates

Core technique: Binary Search with Duplicates. Identify what data structure enables O(1) or O(log n) operations.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Binary Search with Duplicates problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Find Minimum in Rotated Sorted Array II.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Core technique: Binary Search with Duplicates. Identify what data structure enables O(1) or O(log n) operations.

This problem is in your roadmap at position #141 because it reinforces
the Binary Search with Duplicates pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search with Duplicates

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Find Minimum in Rotated Sorted Array II:
  → Find one more problem using the same Binary Search with Duplicates pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 142/250 — LeetCode #81: Search in Rotated Sorted Array II

**Pattern:** `Binary Search` | **Position in Roadmap:** #142

```
LeetCode 81 — Search in Rotated Sorted Array II
Problem #142 of 250 | Difficulty: Medium | Pattern: Binary Search

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 81: Search in Rotated Sorted Array II | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search

Core technique: Binary Search. Identify what data structure enables O(1) or O(log n) operations.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Binary Search problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Search in Rotated Sorted Array II.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Core technique: Binary Search. Identify what data structure enables O(1) or O(log n) operations.

This problem is in your roadmap at position #142 because it reinforces
the Binary Search pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Search in Rotated Sorted Array II:
  → Find one more problem using the same Binary Search pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 143/250 — LeetCode #162: Find Peak Element

**Pattern:** `Binary Search` | **Position in Roadmap:** #143

```
LeetCode 162 — Find Peak Element
Problem #143 of 250 | Difficulty: Medium | Pattern: Binary Search

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 162: Find Peak Element | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search

Core technique: Binary Search. Identify what data structure enables O(1) or O(log n) operations.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Binary Search problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Find Peak Element.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Core technique: Binary Search. Identify what data structure enables O(1) or O(log n) operations.

This problem is in your roadmap at position #143 because it reinforces
the Binary Search pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Find Peak Element:
  → Find one more problem using the same Binary Search pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 144/250 — LeetCode #875: Koko Eating Bananas

**Pattern:** `Binary Search on Answer` | **Position in Roadmap:** #144

```
LeetCode 875 — Koko Eating Bananas
Problem #144 of 250 | Difficulty: Medium | Pattern: Binary Search on Answer

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 875: Koko Eating Bananas | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search on Answer

Core technique: Binary Search on Answer. Identify what data structure enables O(1) or O(log n) operations.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Binary Search on Answer problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Koko Eating Bananas.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Core technique: Binary Search on Answer. Identify what data structure enables O(1) or O(log n) operations.

This problem is in your roadmap at position #144 because it reinforces
the Binary Search on Answer pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search on Answer

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Koko Eating Bananas:
  → Find one more problem using the same Binary Search on Answer pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 145/250 — LeetCode #1011: Capacity To Ship Packages Within D Days

**Pattern:** `Binary Search on Answer` | **Position in Roadmap:** #145

```
LeetCode 1011 — Capacity To Ship Packages Within D Days
Problem #145 of 250 | Difficulty: Medium | Pattern: Binary Search on Answer

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 1011: Capacity To Ship Packages Within D Days | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search on Answer

Core technique: Binary Search on Answer. Identify what data structure enables O(1) or O(log n) operations.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Binary Search on Answer problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Capacity To Ship Packages Within D Days.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Core technique: Binary Search on Answer. Identify what data structure enables O(1) or O(log n) operations.

This problem is in your roadmap at position #145 because it reinforces
the Binary Search on Answer pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search on Answer

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Capacity To Ship Packages Within D Days:
  → Find one more problem using the same Binary Search on Answer pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 146/250 — LeetCode #410: Split Array Largest Sum

**Pattern:** `Binary Search on Answer` | **Position in Roadmap:** #146

```
LeetCode 410 — Split Array Largest Sum
Problem #146 of 250 | Difficulty: Hard | Pattern: Binary Search on Answer

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 410: Split Array Largest Sum | Hard

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search on Answer

Core technique: Binary Search on Answer. Identify what data structure enables O(1) or O(log n) operations.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Binary Search on Answer problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Split Array Largest Sum.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Core technique: Binary Search on Answer. Identify what data structure enables O(1) or O(log n) operations.

This problem is in your roadmap at position #146 because it reinforces
the Binary Search on Answer pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search on Answer

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Split Array Largest Sum:
  → Find one more problem using the same Binary Search on Answer pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 147/250 — LeetCode #509: Fibonacci Number

**Pattern:** `Recursion / DP` | **Position in Roadmap:** #147

```
LeetCode 509 — Fibonacci Number
Problem #147 of 250 | Difficulty: Easy | Pattern: Recursion / DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 509: Fibonacci Number | Easy

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Recursion / DP

Core technique: Recursion / DP. Identify what data structure enables O(1) or O(log n) operations.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Recursion / DP problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Fibonacci Number.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Core technique: Recursion / DP. Identify what data structure enables O(1) or O(log n) operations.

This problem is in your roadmap at position #147 because it reinforces
the Recursion  pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Recursion / DP

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Fibonacci Number:
  → Find one more problem using the same Recursion  pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 148/250 — LeetCode #70: Climbing Stairs

**Pattern:** `DP` | **Position in Roadmap:** #148

```
LeetCode 70 — Climbing Stairs
Problem #148 of 250 | Difficulty: Easy | Pattern: 1D DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 70: Climbing Stairs | Easy

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 1D DP

Core technique: 1D DP. Identify what data structure enables O(1) or O(log n) operations.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For 1D DP problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Climbing Stairs.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Core technique: 1D DP. Identify what data structure enables O(1) or O(log n) operations.

This problem is in your roadmap at position #148 because it reinforces
the 1D DP pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 1D DP

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Climbing Stairs:
  → Find one more problem using the same 1D DP pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 149/250 — LeetCode #1137: N-th Tribonacci Number

**Pattern:** `DP` | **Position in Roadmap:** #149

```
LeetCode 1137 — N-th Tribonacci Number
Problem #149 of 250 | Difficulty: Easy | Pattern: DP — Keep Last 3

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 1137: N-th Tribonacci Number | Easy

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: DP — Keep Last 3

Core technique: DP — Keep Last 3. Identify what data structure enables O(1) or O(log n) operations.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For DP — Keep Last 3 problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for N-th Tribonacci Number.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Core technique: DP — Keep Last 3. Identify what data structure enables O(1) or O(log n) operations.

This problem is in your roadmap at position #149 because it reinforces
the DP — Keep Last 3 pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: DP — Keep Last 3

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving N-th Tribonacci Number:
  → Find one more problem using the same DP — Keep Last 3 pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

## Problem 150/250 — LeetCode #22: Generate Parentheses

**Pattern:** `Backtracking` | **Position in Roadmap:** #150

```
LeetCode 22 — Generate Parentheses
Problem #150 of 250 | Difficulty: Medium | Pattern: Backtracking

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 22: Generate Parentheses | Medium

Step 1: Read the problem carefully on LeetCode.
Step 2: Ask — what is the INPUT? what is the OUTPUT?
Step 3: Write out 2 examples manually and verify your understanding.
Step 4: Identify the CONSTRAINT that makes brute force too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Backtracking

Try each choice. If it works → recurse deeper. If it fails → undo and try next.

Pattern Recognition Question:
  "Does this array/answer space have a MONOTONIC property?"
  "Can I eliminate HALF the search space at each step?"
  "Is there an optimal SUBSTRUCTURE (smaller answer helps build larger)?"
  "What choices can I UNDO (backtrack)?"

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Before writing code, solve a small example BY HAND.
Document your thought process:
  What did you CHECK first?
  What did you ELIMINATE?
  What did you TRACK?

That manual process IS the algorithm!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For Backtracking problems, the code structure is:

  # Initialize state variables
  # Loop with the pattern-specific structure:
  #   - Binary Search: while lo <= hi: mid = (lo+hi)//2
  #   - Backtracking: def dfs(state): try each choice, recurse, undo
  #   - Greedy: sort, then scan with greedy decision
  #   - DP: build dp table from base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Take the FIRST example from LeetCode for Generate Parentheses.
Trace through your solution step by step.
At each step, write the values of ALL key variables.
Verify the final output.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Try each choice. If it works → recurse deeper. If it fails → undo and try next.

This problem is in your roadmap at position #150 because it reinforces
the Backtracking pattern. After solving it, you should be able to:
  1. Recognize this pattern in new problems
  2. Apply the template from memory
  3. Analyze the time/space complexity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Backtracking

# Binary Search template:
lo, hi = 0, len(arr)-1
while lo <= hi:
    mid = (lo + hi) // 2
    if feasible(mid): hi = mid - 1  # or lo = mid + 1
    else: lo = mid + 1

# Backtracking template:
def dfs(start, current):
    if is_complete(current): result.append(current[:])
    for i in range(start, n):
        if not is_valid(current, i): continue
        current.append(choices[i])
        dfs(i+1, current)  # or dfs(i, ...) for reuse
        current.pop()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Binary Search: O(log n) time, O(1) space
Greedy: O(n log n) for sort + O(n) scan = O(n log n)
Backtracking: O(2^n) or O(n!) worst case, varies with pruning
DP: O(n) or O(n²) depending on dimensions

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern → brute force
2. Binary Search: wrong condition (lo<hi vs lo<=hi), wrong update
3. Backtracking: forgetting to UNDO the choice (pop/restore)
4. Greedy: not proving the greedy choice is optimal
5. DP: wrong recurrence relation or base cases

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving Generate Parentheses:
  → Find one more problem using the same Backtracking pattern
  → Solve it without looking at any hints
  → Then compare your approach with the optimal solution
```

---

