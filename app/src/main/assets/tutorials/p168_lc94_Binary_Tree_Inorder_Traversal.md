# 📘 Problem 168 of 250 — LeetCode 94: Binary Tree Inorder Traversal

> **Pattern:** Tree Traversal | **Roadmap Position:** #168/250

---

LeetCode 94 — Binary Tree Inorder Traversal
Problem #168 of 250 | Difficulty: Easy | Pattern: Tree — Inorder DFS (L-N-R)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 94: Binary Tree Inorder Traversal | Easy
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Tree — Inorder DFS (L-N-R)

Core Insight:
Inorder: LEFT → ROOT → RIGHT. For BST, gives sorted output!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Recursive: recurse left, visit root, recurse right.
Iterative: push all left nodes first. Pop, record, then go right.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def inorder(root):
    result, stack, curr = [], [], root
    while curr or stack:
        while curr: stack.append(curr); curr = curr.left
        curr = stack.pop()
        result.append(curr.val)
        curr = curr.right
    return result

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BST: [1,null,2,3]
Inorder: [1,3,2] (L-N-R)

Actually BST [4,2,6,1,3,5,7]:
Inorder: 1,2,3,4,5,6,7 (sorted!) ✓

Iterative trace for [1,null,2,3]:
stack=[], curr=1
Push 1 (no left), pop 1, result=[1], go right=2
Push 2, go left=3: push 3, no left, pop 3, result=[1,3], go right=null
pop 2, result=[1,3,2]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Inorder traversal of BST gives sorted array. Critical for BST problems!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def inorder(root):
    result, stack, curr = [], [], root
    while curr or stack:
        while curr: stack.append(curr); curr = curr.left
        curr = stack.pop()
        result.append(curr.val)
        curr = curr.right
    return result

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

After solving: identify another problem using the same Tree — Inorder DFS (L-N-R) pattern.
Can you solve it without hints?

---

*Next up: Problem 169 of 250*
