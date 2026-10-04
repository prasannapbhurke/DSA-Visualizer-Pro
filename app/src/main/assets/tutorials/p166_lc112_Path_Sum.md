# 📘 Problem 166 of 250 — LeetCode 112: Path Sum

> **Pattern:** Tree DFS | **Roadmap Position:** #166/250

---

LeetCode 112 — Path Sum
Problem #166 of 250 | Difficulty: Easy | Pattern: Tree DFS — Target Tracking

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 112: Path Sum | Easy
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Tree DFS — Target Tracking

Core Insight:
At each node, subtract node.val from targetSum. At leaf, check if remainder == 0.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DFS with remaining target:
  If null → False
  If leaf and remaining == 0 → True (found it!)
  Recurse with target-node.val on left and right

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def hasPathSum(root, targetSum):
    if not root: return False
    if not root.left and not root.right:
        return targetSum == root.val
    return (hasPathSum(root.left, targetSum-root.val) or
            hasPathSum(root.right, targetSum-root.val))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Tree: [5,4,8,11,null,13,4,7,2], targetSum=22

5 (remain=22): go left
  4 (remain=17): go left
    11 (remain=13): go left
      7 (remain=6): leaf, 6≠0 → False
    11 → go right
      2 (remain=4): leaf, 4≠0 → False
  4 → go right... [8,13,4] side
  
Actually: 5→4→11→2: 5+4+11+2=22 ✓
remain at 2-leaf = 22-5-4-11-2=0 → True ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Path sum: pass remaining target down the recursion. At leaf, check if we've reached zero.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def hasPathSum(root, targetSum):
    if not root: return False
    if not root.left and not root.right:
        return targetSum == root.val
    return (hasPathSum(root.left, targetSum-root.val) or
            hasPathSum(root.right, targetSum-root.val))

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

After solving: identify another problem using the same Tree DFS — Target Tracking pattern.
Can you solve it without hints?

---

*Next up: Problem 167 of 250*
