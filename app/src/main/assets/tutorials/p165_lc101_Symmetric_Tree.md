# 📘 Problem 165 of 250 — LeetCode 101: Symmetric Tree

> **Pattern:** Tree DFS | **Roadmap Position:** #165/250

---

LeetCode 101 — Symmetric Tree
Problem #165 of 250 | Difficulty: Easy | Pattern: Tree DFS — Mirror Check

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 101: Symmetric Tree | Easy
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Tree DFS — Mirror Check

Core Insight:
A tree is symmetric if left subtree mirrors right subtree. Compare outer pair and inner pair recursively.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Helper: isMirror(left, right)
  Both null → True
  One null → False
  Values differ → False
  Return isMirror(left.left, right.right) AND isMirror(left.right, right.left)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def isSymmetric(root):
    def mirror(l, r):
        if not l and not r: return True
        if not l or not r or l.val != r.val: return False
        return mirror(l.left, r.right) and mirror(l.right, r.left)
    return mirror(root.left, root.right)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    1
   / \
  2   2
 /\ / \
3 4 4  3

isMirror(2,2): 2==2, check isMirror(3,3) AND isMirror(4,4) → True ✓

    1
   / \
  2   2
   \   \
    3    3
isMirror(2,2): isMirror(null,3)=False → False ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Mirror check: compare outer pair (left.left, right.right) and inner pair (left.right, right.left).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def isSymmetric(root):
    def mirror(l, r):
        if not l and not r: return True
        if not l or not r or l.val != r.val: return False
        return mirror(l.left, r.right) and mirror(l.right, r.left)
    return mirror(root.left, root.right)

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

After solving: identify another problem using the same Tree DFS — Mirror Check pattern.
Can you solve it without hints?

---

*Next up: Problem 166 of 250*
