# 📘 Problem 176 of 250 — LeetCode 124: Binary Tree Maximum Path Sum

> **Pattern:** Tree DFS | **Roadmap Position:** #176/250

---

LeetCode 124 — Binary Tree Maximum Path Sum
Problem #176 of 250 | Difficulty: Hard | Pattern: Tree DFS — Max Gain Post-order

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 124: Binary Tree Maximum Path Sum | Hard
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Tree DFS — Max Gain Post-order

Core Insight:
At each node, compute: max path sum PASSING THROUGH this node = node.val + max(0, left_gain) + max(0, right_gain). Update global max. Return node.val + max(0, max(left,right)) as gain to parent (can only extend one side!).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Post-order: compute gains from both children.
  gain(node) = node.val + max(0, gain(left)) + max(0, gain(right)) is the PATH through node
  But we can RETURN only one side to the parent.
  Return: node.val + max(0, max(left_gain, right_gain))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

self.max_sum = float('-inf')
def gain(node):
    if not node: return 0
    l = max(0, gain(node.left))
    r = max(0, gain(node.right))
    self.max_sum = max(self.max_sum, node.val + l + r)
    return node.val + max(l, r)
gain(root)
return self.max_sum

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

[-10, 9, 20, null, null, 15, 7]

gain(9)=9
gain(15)=15, gain(7)=7
At 20: path = 20+15+7=42, return max = 20+15=35... wait
gain(20) as return = 20 + max(15,7) = 35
path through 20 = 20+15+7=42

At -10: path = -10 + max(9,0) + max(35,0) = -10+9+35=34 < 42

Max = 42 ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

The key distinction: the path through a node uses BOTH children. But you can only RETURN one side to the parent.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

self.max_sum = float('-inf')
def gain(node):
    if not node: return 0
    l = max(0, gain(node.left))
    r = max(0, gain(node.right))
    self.max_sum = max(self.max_sum, node.val + l + r)
    return node.val + max(l, r)
gain(root)
return self.max_sum

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

After solving: identify another problem using the same Tree DFS — Max Gain Post-order pattern.
Can you solve it without hints?

---

*Next up: Problem 177 of 250*
