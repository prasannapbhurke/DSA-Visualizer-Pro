# 📘 Problem 162 of 250 — LeetCode 104: Maximum Depth of Binary Tree

> **Pattern:** Tree DFS | **Roadmap Position:** #162/250

---

LeetCode 104 — Maximum Depth of Binary Tree
Problem #162 of 250 | Difficulty: Easy | Pattern: Tree DFS — Post-order

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 104: Maximum Depth of Binary Tree | Easy
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Tree DFS — Post-order

Core Insight:
Depth of tree = 1 + max(depth of left subtree, depth of right subtree). Base case: null node has depth 0.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Recursively compute depth:
  depth(null) = 0
  depth(node) = 1 + max(depth(node.left), depth(node.right))

Or BFS: count levels.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def maxDepth(root):
    if not root: return 0
    return 1 + max(maxDepth(root.left), maxDepth(root.right))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Tree: [3,9,20,null,null,15,7]
       3
      / \
     9   20
        /  \
       15   7

depth(3) = 1 + max(depth(9), depth(20))
depth(9) = 1 + max(0,0) = 1
depth(20) = 1 + max(depth(15), depth(7)) = 1 + max(1,1) = 2
depth(3) = 1 + max(1,2) = 3 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Tree DFS with recursion: let the recursion tree mirror the actual tree structure.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def maxDepth(root):
    if not root: return 0
    return 1 + max(maxDepth(root.left), maxDepth(root.right))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(h) for recursion stack, h=height

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

After solving: identify another problem using the same Tree DFS — Post-order pattern.
Can you solve it without hints?

---

*Next up: Problem 163 of 250*
