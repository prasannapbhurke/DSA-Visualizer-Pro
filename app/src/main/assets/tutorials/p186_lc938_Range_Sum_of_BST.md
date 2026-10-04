# 📘 Problem 186 of 250 — LeetCode 938: Range Sum of BST

> **Pattern:** BST | **Roadmap Position:** #186/250

---

LeetCode 938 — Range Sum of BST
Problem #186 of 250 | Difficulty: Easy | Pattern: BST — Conditional DFS

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 938: Range Sum of BST | Easy
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: BST — Conditional DFS

Core Insight:
Sum all values in [low, high]. Use BST property to skip subtrees outside range.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DFS. At each node:
  If val in [low,high]: add to sum
  If val > low: search left (might find values ≥ low)
  If val < high: search right (might find values ≤ high)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def rangeSumBST(root, low, high):
    if not root: return 0
    total = root.val if low <= root.val <= high else 0
    if root.val > low: total += rangeSumBST(root.left, low, high)
    if root.val < high: total += rangeSumBST(root.right, low, high)
    return total

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BST: [10,5,15,3,7,null,18], low=7, high=15
  10 in [7,15]: sum+=10. 10>7→go left, 10<15→go right
  5 not in [7,15]: 5>7? No, don't go left. 5<15→go right
  7 in [7,15]: sum+=7. 7>7? No left. 7<15→go right: no child
  15 in [7,15]: sum+=15. 15>7→go left: no child. 15<15? No, skip right.
  Sum = 10+7+15 = 32 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BST pruning: if node.val >= low, search left. If node.val <= high, search right. Efficient range query!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def rangeSumBST(root, low, high):
    if not root: return 0
    total = root.val if low <= root.val <= high else 0
    if root.val > low: total += rangeSumBST(root.left, low, high)
    if root.val < high: total += rangeSumBST(root.right, low, high)
    return total

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n) worst, O(log n + k) average

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not recognizing the pattern — identify it before coding
2. Confusing DFS vs BFS — DFS uses stack/recursion, BFS uses queue
3. Forgetting to mark visited nodes in graph problems
4. Off-by-one in tree traversals
5. Wrong base cases in DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: identify another problem using the same BST — Conditional DFS pattern.
Can you solve it without hints?

---

*Next up: Problem 187 of 250*
