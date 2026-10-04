# 📘 Problem 167 of 250 — LeetCode 144: Binary Tree Preorder Traversal

> **Pattern:** Tree Traversal | **Roadmap Position:** #167/250

---

LeetCode 144 — Binary Tree Preorder Traversal
Problem #167 of 250 | Difficulty: Easy | Pattern: Tree — Preorder DFS

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 144: Binary Tree Preorder Traversal | Easy
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Tree — Preorder DFS

Core Insight:
Preorder: ROOT → LEFT → RIGHT. Visit root first, then left subtree, then right subtree.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Recursive: visit root, recurse left, recurse right.
Iterative: use a stack. Push root. Pop, record, push right then left (so left is processed first).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Recursive:
def preorder(root, result=[]):
    if root:
        result.append(root.val)
        preorder(root.left, result)
        preorder(root.right, result)

# Iterative:
stack, result = [root], []
while stack:
    node = stack.pop()
    if node:
        result.append(node.val)
        stack.append(node.right)  # right first (processed last)
        stack.append(node.left)   # left second (processed first)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Tree: [1,null,2,3]
     1
      \
       2
      /
     3

Preorder: [1, 2, 3]

Iterative:
stack=[1]
pop 1 → result=[1], push right=2 → stack=[2]
pop 2 → result=[1,2], push left=3 → stack=[3]
pop 3 → result=[1,2,3] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Three traversal orders: Preorder(NLR), Inorder(LNR), Postorder(LRN). Iterative uses a stack. Inorder is special for BST.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# Recursive:
def preorder(root, result=[]):
    if root:
        result.append(root.val)
        preorder(root.left, result)
        preorder(root.right, result)

# Iterative:
stack, result = [root], []
while stack:
    node = stack.pop()
    if node:
        result.append(node.val)
        stack.append(node.right)  # right first (processed last)
        stack.append(node.left)   # left second (processed first)

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

After solving: identify another problem using the same Tree — Preorder DFS pattern.
Can you solve it without hints?

---

*Next up: Problem 168 of 250*
