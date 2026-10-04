# 📘 Problem 173 of 250 — LeetCode 199: Binary Tree Right Side View

> **Pattern:** BFS / DFS | **Roadmap Position:** #173/250

---

LeetCode 199 — Binary Tree Right Side View
Problem #173 of 250 | Difficulty: Medium | Pattern: BFS — Last Node Each Level

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 199: Binary Tree Right Side View | Medium
Read the problem on LeetCode. Write 2 examples by hand before coding.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: BFS — Last Node Each Level

Core Insight:
The right side view is the LAST node at each level in BFS. Simply collect last element of each level.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BFS level by level. At each level, record only the LAST node (rightmost).
Or DFS: traverse right-first, record first node at each depth.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

queue = deque([root]); result = []
while queue:
    for i in range(len(queue)):
        node = queue.popleft()
        if i == len_level - 1:  # last node
            result.append(node.val)
        # enqueue children
# Or simpler: result.append(level[-1]) after collecting level

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Tree: [1,2,3,null,5,null,4]
     1
    / \
   2   3
    \    \
     5    4
Level 0: [1] → rightmost: 1
Level 1: [2,3] → rightmost: 3
Level 2: [5,4] → rightmost: 4
Result: [1,3,4] ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Right side view = last element of each BFS level. Simple once you know BFS level-order.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

queue = deque([root]); result = []
while queue:
    for i in range(len(queue)):
        node = queue.popleft()
        if i == len_level - 1:  # last node
            result.append(node.val)
        # enqueue children
# Or simpler: result.append(level[-1]) after collecting level

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(w)

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

After solving: identify another problem using the same BFS — Last Node Each Level pattern.
Can you solve it without hints?

---

*Next up: Problem 174 of 250*
