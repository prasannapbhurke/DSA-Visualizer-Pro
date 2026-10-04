# 📘 Problem 184 of 250 — LeetCode 173: Binary Search Tree Iterator

> **Pattern:** BST / Stack | **Roadmap Position:** #184/250

---

LeetCode 173 — Binary Search Tree Iterator
Problem #184 of 250 | Difficulty: Medium | Pattern: BST — Lazy Inorder with Stack

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 173: Binary Search Tree Iterator | Medium
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: BST — Lazy Inorder with Stack

Core Insight:
Simulate inorder traversal one step at a time. Use a stack to hold the 'in progress' path.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Constructor: push all left nodes from root onto stack.
  next(): pop top, push all left nodes of right child.
  hasNext(): stack is non-empty.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def __init__(self, root):
    self.stack = []
    self._push_left(root)
def _push_left(self, node):
    while node: self.stack.append(node); node = node.left
def next(self):
    node = self.stack.pop()
    self._push_left(node.right)
    return node.val
def hasNext(self): return bool(self.stack)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BST: [7,3,15,null,null,9,20]
Init: push 7, push 3 → stack=[7,3]
next(): pop 3 → return 3. push nothing (3 has no right). stack=[7]
next(): pop 7 → return 7. push 15, push 9 → stack=[15,9]
next(): pop 9 → return 9. push nothing. stack=[15]
next(): pop 15 → return 15. push 20. stack=[20]
next(): pop 20 → return 20. stack=[]
hasNext(): False

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Iterator pattern: maintain state between calls using a stack. Push left-spine on demand.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def __init__(self, root):
    self.stack = []
    self._push_left(root)
def _push_left(self, node):
    while node: self.stack.append(node); node = node.left
def next(self):
    node = self.stack.pop()
    self._push_left(node.right)
    return node.val
def hasNext(self): return bool(self.stack)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

next(): O(1) amortized, hasNext(): O(1)

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

After solving: identify another problem using the same BST — Lazy Inorder with Stack pattern.
Can you solve it without hints?

---

*Next up: Problem 185 of 250*
