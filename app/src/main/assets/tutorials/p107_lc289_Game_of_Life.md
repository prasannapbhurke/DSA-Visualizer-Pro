# 📘 Problem 107 of 250 — LeetCode 289: Game of Life

> **Pattern:** Matrix | **Roadmap Position:** #107/250

---

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

---

*Next up: Problem 108 of 250*
