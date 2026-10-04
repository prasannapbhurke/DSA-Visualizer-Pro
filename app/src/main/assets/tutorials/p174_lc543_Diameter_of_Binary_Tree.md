# 📘 Problem 174 of 250 — LeetCode 543: Diameter of Binary Tree

> **Pattern:** Tree DFS | **Roadmap Position:** #174/250

---

LeetCode 543 — Diameter of Binary Tree
Problem #174 of 250 | Difficulty: Easy | Pattern: Tree DFS — Post-order Max Depth

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 543: Diameter of Binary Tree | Easy
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Tree DFS — Post-order Max Depth

Core Insight:
Diameter through a node = left_height + right_height. Max diameter is the max over all nodes.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Post-order DFS. At each node:
  Compute height of left and right subtrees.
  Diameter through this node = left_height + right_height.
  Update global max.
  Return max(left,right) + 1 as height.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def diameterOfBinaryTree(root):
    self.max_d = 0
    def height(node):
        if not node: return 0
        l, r = height(node.left), height(node.right)
        self.max_d = max(self.max_d, l + r)
        return max(l, r) + 1
    height(root)
    return self.max_d

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    1
   / \
  2   3
 / \
4   5

At node 4: left=0, right=0, diam=0, height=1
At node 5: diam=0, height=1
At node 2: left=1(from 4), right=1(from 5), diam=2, height=2
At node 3: diam=0, height=1
At node 1: left=2, right=1, diam=3!, height=3

Answer: 3 ✓ (path: 4→2→5 or 4→2→1→3 = length 3)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Diameter at each node = left_depth + right_depth. Use a global variable updated during recursion.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def diameterOfBinaryTree(root):
    self.max_d = 0
    def height(node):
        if not node: return 0
        l, r = height(node.left), height(node.right)
        self.max_d = max(self.max_d, l + r)
        return max(l, r) + 1
    height(root)
    return self.max_d

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

After solving: identify another problem using the same Tree DFS — Post-order Max Depth pattern.
Can you solve it without hints?

---

*Next up: Problem 175 of 250*
