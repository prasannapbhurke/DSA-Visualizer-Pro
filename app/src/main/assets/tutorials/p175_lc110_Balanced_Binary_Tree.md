# 📘 Problem 175 of 250 — LeetCode 110: Balanced Binary Tree

> **Pattern:** Tree DFS | **Roadmap Position:** #175/250

---

LeetCode 110 — Balanced Binary Tree
Problem #175 of 250 | Difficulty: Easy | Pattern: Tree DFS — Height with Early Exit

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 110: Balanced Binary Tree | Easy
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Tree DFS — Height with Early Exit

Core Insight:
A tree is balanced if: height difference between left and right subtrees ≤ 1, AND both subtrees are balanced.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DFS returns height, or -1 if subtree is UNBALANCED.
  If left returns -1 → propagate -1 up (already unbalanced)
  If right returns -1 → propagate -1
  If |left_height - right_height| > 1 → return -1
  Otherwise return max(l,r)+1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def isBalanced(root):
    def height(node):
        if not node: return 0
        l = height(node.left)
        if l == -1: return -1
        r = height(node.right)
        if r == -1: return -1
        if abs(l-r) > 1: return -1
        return max(l,r) + 1
    return height(root) != -1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

[3,9,20,null,null,15,7] - balanced
  height(9)=1, height(20)=2
  At 3: |1-2|=1 ≤ 1 → balanced, height=3

[1,2,2,3,3,null,null,4,4] - unbalanced
  Deep left subtree returns large height
  |left-right| > 1 somewhere → return -1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Return -1 to signal imbalance propagates up. Avoids recomputing heights separately.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def isBalanced(root):
    def height(node):
        if not node: return 0
        l = height(node.left)
        if l == -1: return -1
        r = height(node.right)
        if r == -1: return -1
        if abs(l-r) > 1: return -1
        return max(l,r) + 1
    return height(root) != -1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(h)

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

After solving: identify another problem using the same Tree DFS — Height with Early Exit pattern.
Can you solve it without hints?

---

*Next up: Problem 176 of 250*
