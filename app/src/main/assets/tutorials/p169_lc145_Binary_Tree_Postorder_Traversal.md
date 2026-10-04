# 📘 Problem 169 of 250 — LeetCode 145: Binary Tree Postorder Traversal

> **Pattern:** Tree Traversal | **Roadmap Position:** #169/250

---

LeetCode 145 — Binary Tree Postorder Traversal
Problem #169 of 250 | Difficulty: Easy | Pattern: Tree — Postorder DFS (L-R-N)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 145: Binary Tree Postorder Traversal | Easy
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Tree — Postorder DFS (L-R-N)

Core Insight:
Postorder: LEFT → RIGHT → ROOT. Process children BEFORE the parent. Used for deletion, expression evaluation.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Recursive: recurse left, recurse right, then visit root.
Iterative trick: reverse of modified preorder (Root-Right-Left reversed = Left-Right-Root).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Iterative (reverse trick):
stack, result = [root], []
while stack:
    node = stack.pop()
    if node:
        result.append(node.val)
        stack.append(node.left)   # push left (processed last in "reversed preorder")
        stack.append(node.right)
return result[::-1]  # reverse!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Tree: [1,null,2,3] (1 has right child 2, 2 has left child 3)

Postorder: 3,2,1

Iterative: modified preorder gives [1,2,3], reverse → [3,2,1] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Postorder processes leaves before parents. Used for evaluating expression trees, deleting trees.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Iterative (reverse trick):
stack, result = [root], []
while stack:
    node = stack.pop()
    if node:
        result.append(node.val)
        stack.append(node.left)   # push left (processed last in "reversed preorder")
        stack.append(node.right)
return result[::-1]  # reverse!

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

After solving: identify another problem using the same Tree — Postorder DFS (L-R-N) pattern.
Can you solve it without hints?

---

*Next up: Problem 170 of 250*
