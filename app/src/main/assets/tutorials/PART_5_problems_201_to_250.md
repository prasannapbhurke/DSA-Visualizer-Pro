# 📚 DSA Learning Roadmap — Part 1 | Section 5/5
## Advanced (Problems 201-250): Dynamic Programming, Graphs, Advanced Patterns

> Study these problems in ORDER. Each one builds on the previous.

---

## Problem 201/250 — LeetCode #86: Partition List

**Pattern:** `Linked List` | **Position in Roadmap:** #201

```
LeetCode 86 — Partition List
Problem #201 of 250 | Difficulty: Medium | Pattern: Linked List — Two Chains

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 86: Partition List | Medium
Read carefully. Draw the linked list and desired output on paper first.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Linked List — Two Chains

LINKED LIST INSIGHT:
Before coding, always draw the pointer changes:
  What pointers need to change?
  In what order? (Changing in wrong order can lose references!)
  Do you need prev/curr/next tracking?
  Should you use a dummy head?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw the transformation:
  BEFORE: A→B→C→D→E
  AFTER:  [your target arrangement]

For "Partition List":
  What are the key pointer changes?
  Which segments of the list need to be processed?

Linked list operations: always save next BEFORE modifying pointers!
Use dummy head to handle head deletion/replacement uniformly.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Common linked list patterns for Partition List:

# Find tail or nth node:
fast = slow = head
while fast and fast.next: fast = fast.next; slow = slow.next

# Reverse a segment [left, right]:
prev = None; curr = start
for _ in range(length):
    nxt = curr.next; curr.next = prev; prev = curr; curr = nxt

# Connect segments after manipulation

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Trace through with a short list (3-5 nodes).
Draw each pointer state after each line of code.
Verify the final structure is correct.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Advanced linked list problems combine:
  Finding positions (fast/slow, nth from end)
  Reversing segments (three-pointer dance)
  Reconnecting segments (dummy head, tail pointers)

The order of pointer updates is critical — always save next before modifying!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

dummy = ListNode(0, head)
# Find relevant positions
# Perform pointer manipulations
# Reconnect segments
return dummy.next

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n), Space: O(1)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not saving next before modifying pointers
2. Not using dummy head (head deletion becomes special case)
3. Off-by-one in counting positions
4. Not reconnecting tail after segment manipulation

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw your solution on paper with pointer arrows before coding.
Then translate the drawing to code.
```

---

## Problem 202/250 — LeetCode #1971: Find if Path Exists in Graph

**Pattern:** `DFS / BFS` | **Position in Roadmap:** #202

```
LeetCode 1971 — Find if Path Exists in Graph
Problem #202 of 250 | Difficulty: Easy | Pattern: BFS/DFS Graph Traversal

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 1971: Find if Path Exists in Graph | Easy

For graph problems, always identify:
  NODES: what are the vertices? (cells, integers, strings, words)
  EDGES: what connects them? (adjacency, character differences, courses)
  GOAL: shortest path? connected components? cycle? order?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: BFS/DFS Graph Traversal

Start from source. Use visited set. BFS for shortest path, DFS for connectivity.

Choose your traversal:
  DFS: uses stack/recursion, good for connectivity, components, paths
  BFS: uses queue, good for shortest paths, level-by-level processing
  Union-Find: good for dynamic connectivity, cycle detection in undirected graphs
  Dijkstra: good for weighted shortest paths (non-negative weights)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Find if Path Exists in Graph":
  Draw a small example graph (5-6 nodes).
  Trace the BFS/DFS manually.
  Track: visited set, queue/stack, answer variable.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS template:
def dfs(node, visited):
    visited.add(node)
    for neighbor in graph[node]:
        if neighbor not in visited:
            dfs(neighbor, visited)

# BFS template:
from collections import deque
queue = deque([start])
visited = {start}
while queue:
    node = queue.popleft()
    for neighbor in graph[node]:
        if neighbor not in visited:
            visited.add(neighbor)
            queue.append(neighbor)

# Union-Find template:
parent = list(range(n))
def find(x): 
    if parent[x] != x: parent[x] = find(parent[x])
    return parent[x]
def union(x, y):
    px, py = find(x), find(y)
    if px == py: return False  # already connected
    parent[px] = py; return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw a small example from LeetCode.
Trace your BFS/DFS/Union-Find step by step.
Track: current node, visited set, queue state, answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Start from source. Use visited set. BFS for shortest path, DFS for connectivity.

BFS vs DFS:
  BFS = guaranteed shortest path (unweighted graph)
  DFS = simpler for connectivity, cycles, paths
  Union-Find = O(α) nearly O(1) per operation, great for dynamic connectivity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS for connectivity:
def dfs(node): visited.add(node); [dfs(nb) for nb in adj[node] if nb not in visited]

# BFS for shortest path:
queue=deque([start]); visited={start}; dist=0
while queue:
    for _ in range(len(queue)):
        node=queue.popleft()
        for nb in adj[node]:
            if nb not in visited: visited.add(nb); queue.append(nb)
    dist+=1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DFS/BFS: O(V+E) time, O(V) space for visited
Dijkstra: O((V+E) log V)
Union-Find: O(α(n)) per operation (nearly O(1))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Forgetting to mark nodes as visited (infinite loop!)
2. Using DFS when BFS needed for shortest path
3. Not adding to visited BEFORE enqueuing (adds duplicates to queue)
4. Union-Find: not applying path compression

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: implement the same problem with the OTHER traversal method.
If you used DFS, try BFS. If BFS, try Union-Find.
```

---

## Problem 203/250 — LeetCode #733: Flood Fill

**Pattern:** `DFS / BFS` | **Position in Roadmap:** #203

```
LeetCode 733 — Flood Fill
Problem #203 of 250 | Difficulty: Easy | Pattern: DFS/BFS Grid

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 733: Flood Fill | Easy

For graph problems, always identify:
  NODES: what are the vertices? (cells, integers, strings, words)
  EDGES: what connects them? (adjacency, character differences, courses)
  GOAL: shortest path? connected components? cycle? order?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: DFS/BFS Grid

Grid is a graph. Each cell is a node. Neighbors are up/down/left/right. Mark visited to avoid revisiting.

Choose your traversal:
  DFS: uses stack/recursion, good for connectivity, components, paths
  BFS: uses queue, good for shortest paths, level-by-level processing
  Union-Find: good for dynamic connectivity, cycle detection in undirected graphs
  Dijkstra: good for weighted shortest paths (non-negative weights)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Flood Fill":
  Draw a small example graph (5-6 nodes).
  Trace the BFS/DFS manually.
  Track: visited set, queue/stack, answer variable.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS template:
def dfs(node, visited):
    visited.add(node)
    for neighbor in graph[node]:
        if neighbor not in visited:
            dfs(neighbor, visited)

# BFS template:
from collections import deque
queue = deque([start])
visited = {start}
while queue:
    node = queue.popleft()
    for neighbor in graph[node]:
        if neighbor not in visited:
            visited.add(neighbor)
            queue.append(neighbor)

# Union-Find template:
parent = list(range(n))
def find(x): 
    if parent[x] != x: parent[x] = find(parent[x])
    return parent[x]
def union(x, y):
    px, py = find(x), find(y)
    if px == py: return False  # already connected
    parent[px] = py; return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw a small example from LeetCode.
Trace your BFS/DFS/Union-Find step by step.
Track: current node, visited set, queue state, answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Grid is a graph. Each cell is a node. Neighbors are up/down/left/right. Mark visited to avoid revisiting.

BFS vs DFS:
  BFS = guaranteed shortest path (unweighted graph)
  DFS = simpler for connectivity, cycles, paths
  Union-Find = O(α) nearly O(1) per operation, great for dynamic connectivity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS for connectivity:
def dfs(node): visited.add(node); [dfs(nb) for nb in adj[node] if nb not in visited]

# BFS for shortest path:
queue=deque([start]); visited={start}; dist=0
while queue:
    for _ in range(len(queue)):
        node=queue.popleft()
        for nb in adj[node]:
            if nb not in visited: visited.add(nb); queue.append(nb)
    dist+=1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DFS/BFS: O(V+E) time, O(V) space for visited
Dijkstra: O((V+E) log V)
Union-Find: O(α(n)) per operation (nearly O(1))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Forgetting to mark nodes as visited (infinite loop!)
2. Using DFS when BFS needed for shortest path
3. Not adding to visited BEFORE enqueuing (adds duplicates to queue)
4. Union-Find: not applying path compression

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: implement the same problem with the OTHER traversal method.
If you used DFS, try BFS. If BFS, try Union-Find.
```

---

## Problem 204/250 — LeetCode #200: Number of Islands

**Pattern:** `DFS / BFS` | **Position in Roadmap:** #204

```
LeetCode 200 — Number of Islands
Problem #204 of 250 | Difficulty: Medium | Pattern: DFS/BFS Grid — Count Components

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 200: Number of Islands | Medium

For graph problems, always identify:
  NODES: what are the vertices? (cells, integers, strings, words)
  EDGES: what connects them? (adjacency, character differences, courses)
  GOAL: shortest path? connected components? cycle? order?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: DFS/BFS Grid — Count Components

Each unvisited land cell starts a DFS/BFS. Count how many DFS calls = number of islands.

Choose your traversal:
  DFS: uses stack/recursion, good for connectivity, components, paths
  BFS: uses queue, good for shortest paths, level-by-level processing
  Union-Find: good for dynamic connectivity, cycle detection in undirected graphs
  Dijkstra: good for weighted shortest paths (non-negative weights)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Number of Islands":
  Draw a small example graph (5-6 nodes).
  Trace the BFS/DFS manually.
  Track: visited set, queue/stack, answer variable.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS template:
def dfs(node, visited):
    visited.add(node)
    for neighbor in graph[node]:
        if neighbor not in visited:
            dfs(neighbor, visited)

# BFS template:
from collections import deque
queue = deque([start])
visited = {start}
while queue:
    node = queue.popleft()
    for neighbor in graph[node]:
        if neighbor not in visited:
            visited.add(neighbor)
            queue.append(neighbor)

# Union-Find template:
parent = list(range(n))
def find(x): 
    if parent[x] != x: parent[x] = find(parent[x])
    return parent[x]
def union(x, y):
    px, py = find(x), find(y)
    if px == py: return False  # already connected
    parent[px] = py; return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw a small example from LeetCode.
Trace your BFS/DFS/Union-Find step by step.
Track: current node, visited set, queue state, answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Each unvisited land cell starts a DFS/BFS. Count how many DFS calls = number of islands.

BFS vs DFS:
  BFS = guaranteed shortest path (unweighted graph)
  DFS = simpler for connectivity, cycles, paths
  Union-Find = O(α) nearly O(1) per operation, great for dynamic connectivity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS for connectivity:
def dfs(node): visited.add(node); [dfs(nb) for nb in adj[node] if nb not in visited]

# BFS for shortest path:
queue=deque([start]); visited={start}; dist=0
while queue:
    for _ in range(len(queue)):
        node=queue.popleft()
        for nb in adj[node]:
            if nb not in visited: visited.add(nb); queue.append(nb)
    dist+=1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DFS/BFS: O(V+E) time, O(V) space for visited
Dijkstra: O((V+E) log V)
Union-Find: O(α(n)) per operation (nearly O(1))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Forgetting to mark nodes as visited (infinite loop!)
2. Using DFS when BFS needed for shortest path
3. Not adding to visited BEFORE enqueuing (adds duplicates to queue)
4. Union-Find: not applying path compression

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: implement the same problem with the OTHER traversal method.
If you used DFS, try BFS. If BFS, try Union-Find.
```

---

## Problem 205/250 — LeetCode #695: Max Area of Island

**Pattern:** `DFS / BFS` | **Position in Roadmap:** #205

```
LeetCode 695 — Max Area of Island
Problem #205 of 250 | Difficulty: Medium | Pattern: DFS/BFS Grid — Max Component

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 695: Max Area of Island | Medium

For graph problems, always identify:
  NODES: what are the vertices? (cells, integers, strings, words)
  EDGES: what connects them? (adjacency, character differences, courses)
  GOAL: shortest path? connected components? cycle? order?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: DFS/BFS Grid — Max Component

DFS flood fill, return size of each island. Track maximum.

Choose your traversal:
  DFS: uses stack/recursion, good for connectivity, components, paths
  BFS: uses queue, good for shortest paths, level-by-level processing
  Union-Find: good for dynamic connectivity, cycle detection in undirected graphs
  Dijkstra: good for weighted shortest paths (non-negative weights)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Max Area of Island":
  Draw a small example graph (5-6 nodes).
  Trace the BFS/DFS manually.
  Track: visited set, queue/stack, answer variable.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS template:
def dfs(node, visited):
    visited.add(node)
    for neighbor in graph[node]:
        if neighbor not in visited:
            dfs(neighbor, visited)

# BFS template:
from collections import deque
queue = deque([start])
visited = {start}
while queue:
    node = queue.popleft()
    for neighbor in graph[node]:
        if neighbor not in visited:
            visited.add(neighbor)
            queue.append(neighbor)

# Union-Find template:
parent = list(range(n))
def find(x): 
    if parent[x] != x: parent[x] = find(parent[x])
    return parent[x]
def union(x, y):
    px, py = find(x), find(y)
    if px == py: return False  # already connected
    parent[px] = py; return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw a small example from LeetCode.
Trace your BFS/DFS/Union-Find step by step.
Track: current node, visited set, queue state, answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DFS flood fill, return size of each island. Track maximum.

BFS vs DFS:
  BFS = guaranteed shortest path (unweighted graph)
  DFS = simpler for connectivity, cycles, paths
  Union-Find = O(α) nearly O(1) per operation, great for dynamic connectivity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS for connectivity:
def dfs(node): visited.add(node); [dfs(nb) for nb in adj[node] if nb not in visited]

# BFS for shortest path:
queue=deque([start]); visited={start}; dist=0
while queue:
    for _ in range(len(queue)):
        node=queue.popleft()
        for nb in adj[node]:
            if nb not in visited: visited.add(nb); queue.append(nb)
    dist+=1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DFS/BFS: O(V+E) time, O(V) space for visited
Dijkstra: O((V+E) log V)
Union-Find: O(α(n)) per operation (nearly O(1))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Forgetting to mark nodes as visited (infinite loop!)
2. Using DFS when BFS needed for shortest path
3. Not adding to visited BEFORE enqueuing (adds duplicates to queue)
4. Union-Find: not applying path compression

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: implement the same problem with the OTHER traversal method.
If you used DFS, try BFS. If BFS, try Union-Find.
```

---

## Problem 206/250 — LeetCode #463: Island Perimeter

**Pattern:** `Grid` | **Position in Roadmap:** #206

```
LeetCode 463 — Island Perimeter
Problem #206 of 250 | Difficulty: Easy | Pattern: Grid — Neighbor Count

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 463: Island Perimeter | Easy

For graph problems, always identify:
  NODES: what are the vertices? (cells, integers, strings, words)
  EDGES: what connects them? (adjacency, character differences, courses)
  GOAL: shortest path? connected components? cycle? order?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Grid — Neighbor Count

Graph traversal using Grid — Neighbor Count. Identify nodes, edges, and what you're counting.

Choose your traversal:
  DFS: uses stack/recursion, good for connectivity, components, paths
  BFS: uses queue, good for shortest paths, level-by-level processing
  Union-Find: good for dynamic connectivity, cycle detection in undirected graphs
  Dijkstra: good for weighted shortest paths (non-negative weights)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Island Perimeter":
  Draw a small example graph (5-6 nodes).
  Trace the BFS/DFS manually.
  Track: visited set, queue/stack, answer variable.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS template:
def dfs(node, visited):
    visited.add(node)
    for neighbor in graph[node]:
        if neighbor not in visited:
            dfs(neighbor, visited)

# BFS template:
from collections import deque
queue = deque([start])
visited = {start}
while queue:
    node = queue.popleft()
    for neighbor in graph[node]:
        if neighbor not in visited:
            visited.add(neighbor)
            queue.append(neighbor)

# Union-Find template:
parent = list(range(n))
def find(x): 
    if parent[x] != x: parent[x] = find(parent[x])
    return parent[x]
def union(x, y):
    px, py = find(x), find(y)
    if px == py: return False  # already connected
    parent[px] = py; return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw a small example from LeetCode.
Trace your BFS/DFS/Union-Find step by step.
Track: current node, visited set, queue state, answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Graph traversal using Grid — Neighbor Count. Identify nodes, edges, and what you're counting.

BFS vs DFS:
  BFS = guaranteed shortest path (unweighted graph)
  DFS = simpler for connectivity, cycles, paths
  Union-Find = O(α) nearly O(1) per operation, great for dynamic connectivity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS for connectivity:
def dfs(node): visited.add(node); [dfs(nb) for nb in adj[node] if nb not in visited]

# BFS for shortest path:
queue=deque([start]); visited={start}; dist=0
while queue:
    for _ in range(len(queue)):
        node=queue.popleft()
        for nb in adj[node]:
            if nb not in visited: visited.add(nb); queue.append(nb)
    dist+=1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DFS/BFS: O(V+E) time, O(V) space for visited
Dijkstra: O((V+E) log V)
Union-Find: O(α(n)) per operation (nearly O(1))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Forgetting to mark nodes as visited (infinite loop!)
2. Using DFS when BFS needed for shortest path
3. Not adding to visited BEFORE enqueuing (adds duplicates to queue)
4. Union-Find: not applying path compression

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: implement the same problem with the OTHER traversal method.
If you used DFS, try BFS. If BFS, try Union-Find.
```

---

## Problem 207/250 — LeetCode #994: Rotting Oranges

**Pattern:** `BFS` | **Position in Roadmap:** #207

```
LeetCode 994 — Rotting Oranges
Problem #207 of 250 | Difficulty: Medium | Pattern: Multi-source BFS

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 994: Rotting Oranges | Medium

For graph problems, always identify:
  NODES: what are the vertices? (cells, integers, strings, words)
  EDGES: what connects them? (adjacency, character differences, courses)
  GOAL: shortest path? connected components? cycle? order?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Multi-source BFS

Start BFS from MULTIPLE sources simultaneously. All sources at level 0. Process outward.

Choose your traversal:
  DFS: uses stack/recursion, good for connectivity, components, paths
  BFS: uses queue, good for shortest paths, level-by-level processing
  Union-Find: good for dynamic connectivity, cycle detection in undirected graphs
  Dijkstra: good for weighted shortest paths (non-negative weights)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Rotting Oranges":
  Draw a small example graph (5-6 nodes).
  Trace the BFS/DFS manually.
  Track: visited set, queue/stack, answer variable.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS template:
def dfs(node, visited):
    visited.add(node)
    for neighbor in graph[node]:
        if neighbor not in visited:
            dfs(neighbor, visited)

# BFS template:
from collections import deque
queue = deque([start])
visited = {start}
while queue:
    node = queue.popleft()
    for neighbor in graph[node]:
        if neighbor not in visited:
            visited.add(neighbor)
            queue.append(neighbor)

# Union-Find template:
parent = list(range(n))
def find(x): 
    if parent[x] != x: parent[x] = find(parent[x])
    return parent[x]
def union(x, y):
    px, py = find(x), find(y)
    if px == py: return False  # already connected
    parent[px] = py; return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw a small example from LeetCode.
Trace your BFS/DFS/Union-Find step by step.
Track: current node, visited set, queue state, answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Start BFS from MULTIPLE sources simultaneously. All sources at level 0. Process outward.

BFS vs DFS:
  BFS = guaranteed shortest path (unweighted graph)
  DFS = simpler for connectivity, cycles, paths
  Union-Find = O(α) nearly O(1) per operation, great for dynamic connectivity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS for connectivity:
def dfs(node): visited.add(node); [dfs(nb) for nb in adj[node] if nb not in visited]

# BFS for shortest path:
queue=deque([start]); visited={start}; dist=0
while queue:
    for _ in range(len(queue)):
        node=queue.popleft()
        for nb in adj[node]:
            if nb not in visited: visited.add(nb); queue.append(nb)
    dist+=1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DFS/BFS: O(V+E) time, O(V) space for visited
Dijkstra: O((V+E) log V)
Union-Find: O(α(n)) per operation (nearly O(1))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Forgetting to mark nodes as visited (infinite loop!)
2. Using DFS when BFS needed for shortest path
3. Not adding to visited BEFORE enqueuing (adds duplicates to queue)
4. Union-Find: not applying path compression

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: implement the same problem with the OTHER traversal method.
If you used DFS, try BFS. If BFS, try Union-Find.
```

---

## Problem 208/250 — LeetCode #542: 01 Matrix

**Pattern:** `Multi-source BFS` | **Position in Roadmap:** #208

```
LeetCode 542 — 01 Matrix
Problem #208 of 250 | Difficulty: Medium | Pattern: Multi-source BFS

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 542: 01 Matrix | Medium

For graph problems, always identify:
  NODES: what are the vertices? (cells, integers, strings, words)
  EDGES: what connects them? (adjacency, character differences, courses)
  GOAL: shortest path? connected components? cycle? order?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Multi-source BFS

Start BFS from MULTIPLE sources simultaneously. All sources at level 0. Process outward.

Choose your traversal:
  DFS: uses stack/recursion, good for connectivity, components, paths
  BFS: uses queue, good for shortest paths, level-by-level processing
  Union-Find: good for dynamic connectivity, cycle detection in undirected graphs
  Dijkstra: good for weighted shortest paths (non-negative weights)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "01 Matrix":
  Draw a small example graph (5-6 nodes).
  Trace the BFS/DFS manually.
  Track: visited set, queue/stack, answer variable.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS template:
def dfs(node, visited):
    visited.add(node)
    for neighbor in graph[node]:
        if neighbor not in visited:
            dfs(neighbor, visited)

# BFS template:
from collections import deque
queue = deque([start])
visited = {start}
while queue:
    node = queue.popleft()
    for neighbor in graph[node]:
        if neighbor not in visited:
            visited.add(neighbor)
            queue.append(neighbor)

# Union-Find template:
parent = list(range(n))
def find(x): 
    if parent[x] != x: parent[x] = find(parent[x])
    return parent[x]
def union(x, y):
    px, py = find(x), find(y)
    if px == py: return False  # already connected
    parent[px] = py; return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw a small example from LeetCode.
Trace your BFS/DFS/Union-Find step by step.
Track: current node, visited set, queue state, answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Start BFS from MULTIPLE sources simultaneously. All sources at level 0. Process outward.

BFS vs DFS:
  BFS = guaranteed shortest path (unweighted graph)
  DFS = simpler for connectivity, cycles, paths
  Union-Find = O(α) nearly O(1) per operation, great for dynamic connectivity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS for connectivity:
def dfs(node): visited.add(node); [dfs(nb) for nb in adj[node] if nb not in visited]

# BFS for shortest path:
queue=deque([start]); visited={start}; dist=0
while queue:
    for _ in range(len(queue)):
        node=queue.popleft()
        for nb in adj[node]:
            if nb not in visited: visited.add(nb); queue.append(nb)
    dist+=1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DFS/BFS: O(V+E) time, O(V) space for visited
Dijkstra: O((V+E) log V)
Union-Find: O(α(n)) per operation (nearly O(1))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Forgetting to mark nodes as visited (infinite loop!)
2. Using DFS when BFS needed for shortest path
3. Not adding to visited BEFORE enqueuing (adds duplicates to queue)
4. Union-Find: not applying path compression

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: implement the same problem with the OTHER traversal method.
If you used DFS, try BFS. If BFS, try Union-Find.
```

---

## Problem 209/250 — LeetCode #130: Surrounded Regions

**Pattern:** `DFS / BFS` | **Position in Roadmap:** #209

```
LeetCode 130 — Surrounded Regions
Problem #209 of 250 | Difficulty: Medium | Pattern: DFS/BFS from Boundary

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 130: Surrounded Regions | Medium

For graph problems, always identify:
  NODES: what are the vertices? (cells, integers, strings, words)
  EDGES: what connects them? (adjacency, character differences, courses)
  GOAL: shortest path? connected components? cycle? order?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: DFS/BFS from Boundary

Graph traversal using DFS/BFS from Boundary. Identify nodes, edges, and what you're counting.

Choose your traversal:
  DFS: uses stack/recursion, good for connectivity, components, paths
  BFS: uses queue, good for shortest paths, level-by-level processing
  Union-Find: good for dynamic connectivity, cycle detection in undirected graphs
  Dijkstra: good for weighted shortest paths (non-negative weights)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Surrounded Regions":
  Draw a small example graph (5-6 nodes).
  Trace the BFS/DFS manually.
  Track: visited set, queue/stack, answer variable.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS template:
def dfs(node, visited):
    visited.add(node)
    for neighbor in graph[node]:
        if neighbor not in visited:
            dfs(neighbor, visited)

# BFS template:
from collections import deque
queue = deque([start])
visited = {start}
while queue:
    node = queue.popleft()
    for neighbor in graph[node]:
        if neighbor not in visited:
            visited.add(neighbor)
            queue.append(neighbor)

# Union-Find template:
parent = list(range(n))
def find(x): 
    if parent[x] != x: parent[x] = find(parent[x])
    return parent[x]
def union(x, y):
    px, py = find(x), find(y)
    if px == py: return False  # already connected
    parent[px] = py; return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw a small example from LeetCode.
Trace your BFS/DFS/Union-Find step by step.
Track: current node, visited set, queue state, answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Graph traversal using DFS/BFS from Boundary. Identify nodes, edges, and what you're counting.

BFS vs DFS:
  BFS = guaranteed shortest path (unweighted graph)
  DFS = simpler for connectivity, cycles, paths
  Union-Find = O(α) nearly O(1) per operation, great for dynamic connectivity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS for connectivity:
def dfs(node): visited.add(node); [dfs(nb) for nb in adj[node] if nb not in visited]

# BFS for shortest path:
queue=deque([start]); visited={start}; dist=0
while queue:
    for _ in range(len(queue)):
        node=queue.popleft()
        for nb in adj[node]:
            if nb not in visited: visited.add(nb); queue.append(nb)
    dist+=1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DFS/BFS: O(V+E) time, O(V) space for visited
Dijkstra: O((V+E) log V)
Union-Find: O(α(n)) per operation (nearly O(1))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Forgetting to mark nodes as visited (infinite loop!)
2. Using DFS when BFS needed for shortest path
3. Not adding to visited BEFORE enqueuing (adds duplicates to queue)
4. Union-Find: not applying path compression

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: implement the same problem with the OTHER traversal method.
If you used DFS, try BFS. If BFS, try Union-Find.
```

---

## Problem 210/250 — LeetCode #417: Pacific Atlantic Water Flow

**Pattern:** `DFS` | **Position in Roadmap:** #210

```
LeetCode 417 — Pacific Atlantic Water Flow
Problem #210 of 250 | Difficulty: Medium | Pattern: DFS from Both Oceans

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 417: Pacific Atlantic Water Flow | Medium

For graph problems, always identify:
  NODES: what are the vertices? (cells, integers, strings, words)
  EDGES: what connects them? (adjacency, character differences, courses)
  GOAL: shortest path? connected components? cycle? order?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: DFS from Both Oceans

Graph traversal using DFS from Both Oceans. Identify nodes, edges, and what you're counting.

Choose your traversal:
  DFS: uses stack/recursion, good for connectivity, components, paths
  BFS: uses queue, good for shortest paths, level-by-level processing
  Union-Find: good for dynamic connectivity, cycle detection in undirected graphs
  Dijkstra: good for weighted shortest paths (non-negative weights)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Pacific Atlantic Water Flow":
  Draw a small example graph (5-6 nodes).
  Trace the BFS/DFS manually.
  Track: visited set, queue/stack, answer variable.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS template:
def dfs(node, visited):
    visited.add(node)
    for neighbor in graph[node]:
        if neighbor not in visited:
            dfs(neighbor, visited)

# BFS template:
from collections import deque
queue = deque([start])
visited = {start}
while queue:
    node = queue.popleft()
    for neighbor in graph[node]:
        if neighbor not in visited:
            visited.add(neighbor)
            queue.append(neighbor)

# Union-Find template:
parent = list(range(n))
def find(x): 
    if parent[x] != x: parent[x] = find(parent[x])
    return parent[x]
def union(x, y):
    px, py = find(x), find(y)
    if px == py: return False  # already connected
    parent[px] = py; return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw a small example from LeetCode.
Trace your BFS/DFS/Union-Find step by step.
Track: current node, visited set, queue state, answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Graph traversal using DFS from Both Oceans. Identify nodes, edges, and what you're counting.

BFS vs DFS:
  BFS = guaranteed shortest path (unweighted graph)
  DFS = simpler for connectivity, cycles, paths
  Union-Find = O(α) nearly O(1) per operation, great for dynamic connectivity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS for connectivity:
def dfs(node): visited.add(node); [dfs(nb) for nb in adj[node] if nb not in visited]

# BFS for shortest path:
queue=deque([start]); visited={start}; dist=0
while queue:
    for _ in range(len(queue)):
        node=queue.popleft()
        for nb in adj[node]:
            if nb not in visited: visited.add(nb); queue.append(nb)
    dist+=1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DFS/BFS: O(V+E) time, O(V) space for visited
Dijkstra: O((V+E) log V)
Union-Find: O(α(n)) per operation (nearly O(1))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Forgetting to mark nodes as visited (infinite loop!)
2. Using DFS when BFS needed for shortest path
3. Not adding to visited BEFORE enqueuing (adds duplicates to queue)
4. Union-Find: not applying path compression

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: implement the same problem with the OTHER traversal method.
If you used DFS, try BFS. If BFS, try Union-Find.
```

---

## Problem 211/250 — LeetCode #133: Clone Graph

**Pattern:** `Graph DFS/BFS` | **Position in Roadmap:** #211

```
LeetCode 133 — Clone Graph
Problem #211 of 250 | Difficulty: Medium | Pattern: DFS/BFS + HashMap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 133: Clone Graph | Medium

For graph problems, always identify:
  NODES: what are the vertices? (cells, integers, strings, words)
  EDGES: what connects them? (adjacency, character differences, courses)
  GOAL: shortest path? connected components? cycle? order?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: DFS/BFS + HashMap

Graph traversal using DFS/BFS + HashMap. Identify nodes, edges, and what you're counting.

Choose your traversal:
  DFS: uses stack/recursion, good for connectivity, components, paths
  BFS: uses queue, good for shortest paths, level-by-level processing
  Union-Find: good for dynamic connectivity, cycle detection in undirected graphs
  Dijkstra: good for weighted shortest paths (non-negative weights)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Clone Graph":
  Draw a small example graph (5-6 nodes).
  Trace the BFS/DFS manually.
  Track: visited set, queue/stack, answer variable.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS template:
def dfs(node, visited):
    visited.add(node)
    for neighbor in graph[node]:
        if neighbor not in visited:
            dfs(neighbor, visited)

# BFS template:
from collections import deque
queue = deque([start])
visited = {start}
while queue:
    node = queue.popleft()
    for neighbor in graph[node]:
        if neighbor not in visited:
            visited.add(neighbor)
            queue.append(neighbor)

# Union-Find template:
parent = list(range(n))
def find(x): 
    if parent[x] != x: parent[x] = find(parent[x])
    return parent[x]
def union(x, y):
    px, py = find(x), find(y)
    if px == py: return False  # already connected
    parent[px] = py; return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw a small example from LeetCode.
Trace your BFS/DFS/Union-Find step by step.
Track: current node, visited set, queue state, answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Graph traversal using DFS/BFS + HashMap. Identify nodes, edges, and what you're counting.

BFS vs DFS:
  BFS = guaranteed shortest path (unweighted graph)
  DFS = simpler for connectivity, cycles, paths
  Union-Find = O(α) nearly O(1) per operation, great for dynamic connectivity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS for connectivity:
def dfs(node): visited.add(node); [dfs(nb) for nb in adj[node] if nb not in visited]

# BFS for shortest path:
queue=deque([start]); visited={start}; dist=0
while queue:
    for _ in range(len(queue)):
        node=queue.popleft()
        for nb in adj[node]:
            if nb not in visited: visited.add(nb); queue.append(nb)
    dist+=1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DFS/BFS: O(V+E) time, O(V) space for visited
Dijkstra: O((V+E) log V)
Union-Find: O(α(n)) per operation (nearly O(1))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Forgetting to mark nodes as visited (infinite loop!)
2. Using DFS when BFS needed for shortest path
3. Not adding to visited BEFORE enqueuing (adds duplicates to queue)
4. Union-Find: not applying path compression

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: implement the same problem with the OTHER traversal method.
If you used DFS, try BFS. If BFS, try Union-Find.
```

---

## Problem 212/250 — LeetCode #207: Course Schedule

**Pattern:** `Topological Sort` | **Position in Roadmap:** #212

```
LeetCode 207 — Course Schedule
Problem #212 of 250 | Difficulty: Medium | Pattern: Topological Sort — Cycle Detection

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 207: Course Schedule | Medium

For graph problems, always identify:
  NODES: what are the vertices? (cells, integers, strings, words)
  EDGES: what connects them? (adjacency, character differences, courses)
  GOAL: shortest path? connected components? cycle? order?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Topological Sort — Cycle Detection

Build adjacency list + in-degree count. Start with 0 in-degree nodes. If all processed → no cycle.

Choose your traversal:
  DFS: uses stack/recursion, good for connectivity, components, paths
  BFS: uses queue, good for shortest paths, level-by-level processing
  Union-Find: good for dynamic connectivity, cycle detection in undirected graphs
  Dijkstra: good for weighted shortest paths (non-negative weights)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Course Schedule":
  Draw a small example graph (5-6 nodes).
  Trace the BFS/DFS manually.
  Track: visited set, queue/stack, answer variable.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS template:
def dfs(node, visited):
    visited.add(node)
    for neighbor in graph[node]:
        if neighbor not in visited:
            dfs(neighbor, visited)

# BFS template:
from collections import deque
queue = deque([start])
visited = {start}
while queue:
    node = queue.popleft()
    for neighbor in graph[node]:
        if neighbor not in visited:
            visited.add(neighbor)
            queue.append(neighbor)

# Union-Find template:
parent = list(range(n))
def find(x): 
    if parent[x] != x: parent[x] = find(parent[x])
    return parent[x]
def union(x, y):
    px, py = find(x), find(y)
    if px == py: return False  # already connected
    parent[px] = py; return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw a small example from LeetCode.
Trace your BFS/DFS/Union-Find step by step.
Track: current node, visited set, queue state, answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Build adjacency list + in-degree count. Start with 0 in-degree nodes. If all processed → no cycle.

BFS vs DFS:
  BFS = guaranteed shortest path (unweighted graph)
  DFS = simpler for connectivity, cycles, paths
  Union-Find = O(α) nearly O(1) per operation, great for dynamic connectivity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS for connectivity:
def dfs(node): visited.add(node); [dfs(nb) for nb in adj[node] if nb not in visited]

# BFS for shortest path:
queue=deque([start]); visited={start}; dist=0
while queue:
    for _ in range(len(queue)):
        node=queue.popleft()
        for nb in adj[node]:
            if nb not in visited: visited.add(nb); queue.append(nb)
    dist+=1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DFS/BFS: O(V+E) time, O(V) space for visited
Dijkstra: O((V+E) log V)
Union-Find: O(α(n)) per operation (nearly O(1))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Forgetting to mark nodes as visited (infinite loop!)
2. Using DFS when BFS needed for shortest path
3. Not adding to visited BEFORE enqueuing (adds duplicates to queue)
4. Union-Find: not applying path compression

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: implement the same problem with the OTHER traversal method.
If you used DFS, try BFS. If BFS, try Union-Find.
```

---

## Problem 213/250 — LeetCode #210: Course Schedule II

**Pattern:** `Topological Sort` | **Position in Roadmap:** #213

```
LeetCode 210 — Course Schedule II
Problem #213 of 250 | Difficulty: Medium | Pattern: Topological Sort — Order

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 210: Course Schedule II | Medium

For graph problems, always identify:
  NODES: what are the vertices? (cells, integers, strings, words)
  EDGES: what connects them? (adjacency, character differences, courses)
  GOAL: shortest path? connected components? cycle? order?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Topological Sort — Order

Graph traversal using Topological Sort — Order. Identify nodes, edges, and what you're counting.

Choose your traversal:
  DFS: uses stack/recursion, good for connectivity, components, paths
  BFS: uses queue, good for shortest paths, level-by-level processing
  Union-Find: good for dynamic connectivity, cycle detection in undirected graphs
  Dijkstra: good for weighted shortest paths (non-negative weights)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Course Schedule II":
  Draw a small example graph (5-6 nodes).
  Trace the BFS/DFS manually.
  Track: visited set, queue/stack, answer variable.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS template:
def dfs(node, visited):
    visited.add(node)
    for neighbor in graph[node]:
        if neighbor not in visited:
            dfs(neighbor, visited)

# BFS template:
from collections import deque
queue = deque([start])
visited = {start}
while queue:
    node = queue.popleft()
    for neighbor in graph[node]:
        if neighbor not in visited:
            visited.add(neighbor)
            queue.append(neighbor)

# Union-Find template:
parent = list(range(n))
def find(x): 
    if parent[x] != x: parent[x] = find(parent[x])
    return parent[x]
def union(x, y):
    px, py = find(x), find(y)
    if px == py: return False  # already connected
    parent[px] = py; return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw a small example from LeetCode.
Trace your BFS/DFS/Union-Find step by step.
Track: current node, visited set, queue state, answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Graph traversal using Topological Sort — Order. Identify nodes, edges, and what you're counting.

BFS vs DFS:
  BFS = guaranteed shortest path (unweighted graph)
  DFS = simpler for connectivity, cycles, paths
  Union-Find = O(α) nearly O(1) per operation, great for dynamic connectivity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS for connectivity:
def dfs(node): visited.add(node); [dfs(nb) for nb in adj[node] if nb not in visited]

# BFS for shortest path:
queue=deque([start]); visited={start}; dist=0
while queue:
    for _ in range(len(queue)):
        node=queue.popleft()
        for nb in adj[node]:
            if nb not in visited: visited.add(nb); queue.append(nb)
    dist+=1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DFS/BFS: O(V+E) time, O(V) space for visited
Dijkstra: O((V+E) log V)
Union-Find: O(α(n)) per operation (nearly O(1))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Forgetting to mark nodes as visited (infinite loop!)
2. Using DFS when BFS needed for shortest path
3. Not adding to visited BEFORE enqueuing (adds duplicates to queue)
4. Union-Find: not applying path compression

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: implement the same problem with the OTHER traversal method.
If you used DFS, try BFS. If BFS, try Union-Find.
```

---

## Problem 214/250 — LeetCode #684: Redundant Connection

**Pattern:** `Union Find` | **Position in Roadmap:** #214

```
LeetCode 684 — Redundant Connection
Problem #214 of 250 | Difficulty: Medium | Pattern: Union Find

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 684: Redundant Connection | Medium

For graph problems, always identify:
  NODES: what are the vertices? (cells, integers, strings, words)
  EDGES: what connects them? (adjacency, character differences, courses)
  GOAL: shortest path? connected components? cycle? order?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Union Find

Maintain parent array. union(a,b) joins components. find(a) finds root. If find(a)==find(b) → same component.

Choose your traversal:
  DFS: uses stack/recursion, good for connectivity, components, paths
  BFS: uses queue, good for shortest paths, level-by-level processing
  Union-Find: good for dynamic connectivity, cycle detection in undirected graphs
  Dijkstra: good for weighted shortest paths (non-negative weights)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Redundant Connection":
  Draw a small example graph (5-6 nodes).
  Trace the BFS/DFS manually.
  Track: visited set, queue/stack, answer variable.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS template:
def dfs(node, visited):
    visited.add(node)
    for neighbor in graph[node]:
        if neighbor not in visited:
            dfs(neighbor, visited)

# BFS template:
from collections import deque
queue = deque([start])
visited = {start}
while queue:
    node = queue.popleft()
    for neighbor in graph[node]:
        if neighbor not in visited:
            visited.add(neighbor)
            queue.append(neighbor)

# Union-Find template:
parent = list(range(n))
def find(x): 
    if parent[x] != x: parent[x] = find(parent[x])
    return parent[x]
def union(x, y):
    px, py = find(x), find(y)
    if px == py: return False  # already connected
    parent[px] = py; return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw a small example from LeetCode.
Trace your BFS/DFS/Union-Find step by step.
Track: current node, visited set, queue state, answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Maintain parent array. union(a,b) joins components. find(a) finds root. If find(a)==find(b) → same component.

BFS vs DFS:
  BFS = guaranteed shortest path (unweighted graph)
  DFS = simpler for connectivity, cycles, paths
  Union-Find = O(α) nearly O(1) per operation, great for dynamic connectivity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS for connectivity:
def dfs(node): visited.add(node); [dfs(nb) for nb in adj[node] if nb not in visited]

# BFS for shortest path:
queue=deque([start]); visited={start}; dist=0
while queue:
    for _ in range(len(queue)):
        node=queue.popleft()
        for nb in adj[node]:
            if nb not in visited: visited.add(nb); queue.append(nb)
    dist+=1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DFS/BFS: O(V+E) time, O(V) space for visited
Dijkstra: O((V+E) log V)
Union-Find: O(α(n)) per operation (nearly O(1))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Forgetting to mark nodes as visited (infinite loop!)
2. Using DFS when BFS needed for shortest path
3. Not adding to visited BEFORE enqueuing (adds duplicates to queue)
4. Union-Find: not applying path compression

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: implement the same problem with the OTHER traversal method.
If you used DFS, try BFS. If BFS, try Union-Find.
```

---

## Problem 215/250 — LeetCode #547: Number of Provinces

**Pattern:** `Union Find / DFS` | **Position in Roadmap:** #215

```
LeetCode 547 — Number of Provinces
Problem #215 of 250 | Difficulty: Medium | Pattern: Union Find/DFS

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 547: Number of Provinces | Medium

For graph problems, always identify:
  NODES: what are the vertices? (cells, integers, strings, words)
  EDGES: what connects them? (adjacency, character differences, courses)
  GOAL: shortest path? connected components? cycle? order?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Union Find/DFS

Graph traversal using Union Find/DFS. Identify nodes, edges, and what you're counting.

Choose your traversal:
  DFS: uses stack/recursion, good for connectivity, components, paths
  BFS: uses queue, good for shortest paths, level-by-level processing
  Union-Find: good for dynamic connectivity, cycle detection in undirected graphs
  Dijkstra: good for weighted shortest paths (non-negative weights)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Number of Provinces":
  Draw a small example graph (5-6 nodes).
  Trace the BFS/DFS manually.
  Track: visited set, queue/stack, answer variable.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS template:
def dfs(node, visited):
    visited.add(node)
    for neighbor in graph[node]:
        if neighbor not in visited:
            dfs(neighbor, visited)

# BFS template:
from collections import deque
queue = deque([start])
visited = {start}
while queue:
    node = queue.popleft()
    for neighbor in graph[node]:
        if neighbor not in visited:
            visited.add(neighbor)
            queue.append(neighbor)

# Union-Find template:
parent = list(range(n))
def find(x): 
    if parent[x] != x: parent[x] = find(parent[x])
    return parent[x]
def union(x, y):
    px, py = find(x), find(y)
    if px == py: return False  # already connected
    parent[px] = py; return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw a small example from LeetCode.
Trace your BFS/DFS/Union-Find step by step.
Track: current node, visited set, queue state, answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Graph traversal using Union Find/DFS. Identify nodes, edges, and what you're counting.

BFS vs DFS:
  BFS = guaranteed shortest path (unweighted graph)
  DFS = simpler for connectivity, cycles, paths
  Union-Find = O(α) nearly O(1) per operation, great for dynamic connectivity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS for connectivity:
def dfs(node): visited.add(node); [dfs(nb) for nb in adj[node] if nb not in visited]

# BFS for shortest path:
queue=deque([start]); visited={start}; dist=0
while queue:
    for _ in range(len(queue)):
        node=queue.popleft()
        for nb in adj[node]:
            if nb not in visited: visited.add(nb); queue.append(nb)
    dist+=1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DFS/BFS: O(V+E) time, O(V) space for visited
Dijkstra: O((V+E) log V)
Union-Find: O(α(n)) per operation (nearly O(1))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Forgetting to mark nodes as visited (infinite loop!)
2. Using DFS when BFS needed for shortest path
3. Not adding to visited BEFORE enqueuing (adds duplicates to queue)
4. Union-Find: not applying path compression

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: implement the same problem with the OTHER traversal method.
If you used DFS, try BFS. If BFS, try Union-Find.
```

---

## Problem 216/250 — LeetCode #323: Number of Connected Components in an Undirected Graph

**Pattern:** `Union Find / DFS` | **Position in Roadmap:** #216

```
LeetCode 323 — Number of Connected Components
Problem #216 of 250 | Difficulty: Medium | Pattern: Union Find/DFS

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 323: Number of Connected Components | Medium

For graph problems, always identify:
  NODES: what are the vertices? (cells, integers, strings, words)
  EDGES: what connects them? (adjacency, character differences, courses)
  GOAL: shortest path? connected components? cycle? order?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Union Find/DFS

Graph traversal using Union Find/DFS. Identify nodes, edges, and what you're counting.

Choose your traversal:
  DFS: uses stack/recursion, good for connectivity, components, paths
  BFS: uses queue, good for shortest paths, level-by-level processing
  Union-Find: good for dynamic connectivity, cycle detection in undirected graphs
  Dijkstra: good for weighted shortest paths (non-negative weights)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Number of Connected Components":
  Draw a small example graph (5-6 nodes).
  Trace the BFS/DFS manually.
  Track: visited set, queue/stack, answer variable.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS template:
def dfs(node, visited):
    visited.add(node)
    for neighbor in graph[node]:
        if neighbor not in visited:
            dfs(neighbor, visited)

# BFS template:
from collections import deque
queue = deque([start])
visited = {start}
while queue:
    node = queue.popleft()
    for neighbor in graph[node]:
        if neighbor not in visited:
            visited.add(neighbor)
            queue.append(neighbor)

# Union-Find template:
parent = list(range(n))
def find(x): 
    if parent[x] != x: parent[x] = find(parent[x])
    return parent[x]
def union(x, y):
    px, py = find(x), find(y)
    if px == py: return False  # already connected
    parent[px] = py; return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw a small example from LeetCode.
Trace your BFS/DFS/Union-Find step by step.
Track: current node, visited set, queue state, answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Graph traversal using Union Find/DFS. Identify nodes, edges, and what you're counting.

BFS vs DFS:
  BFS = guaranteed shortest path (unweighted graph)
  DFS = simpler for connectivity, cycles, paths
  Union-Find = O(α) nearly O(1) per operation, great for dynamic connectivity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS for connectivity:
def dfs(node): visited.add(node); [dfs(nb) for nb in adj[node] if nb not in visited]

# BFS for shortest path:
queue=deque([start]); visited={start}; dist=0
while queue:
    for _ in range(len(queue)):
        node=queue.popleft()
        for nb in adj[node]:
            if nb not in visited: visited.add(nb); queue.append(nb)
    dist+=1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DFS/BFS: O(V+E) time, O(V) space for visited
Dijkstra: O((V+E) log V)
Union-Find: O(α(n)) per operation (nearly O(1))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Forgetting to mark nodes as visited (infinite loop!)
2. Using DFS when BFS needed for shortest path
3. Not adding to visited BEFORE enqueuing (adds duplicates to queue)
4. Union-Find: not applying path compression

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: implement the same problem with the OTHER traversal method.
If you used DFS, try BFS. If BFS, try Union-Find.
```

---

## Problem 217/250 — LeetCode #721: Accounts Merge

**Pattern:** `Union Find` | **Position in Roadmap:** #217

```
LeetCode 721 — Accounts Merge
Problem #217 of 250 | Difficulty: Medium | Pattern: Union Find

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 721: Accounts Merge | Medium

For graph problems, always identify:
  NODES: what are the vertices? (cells, integers, strings, words)
  EDGES: what connects them? (adjacency, character differences, courses)
  GOAL: shortest path? connected components? cycle? order?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Union Find

Maintain parent array. union(a,b) joins components. find(a) finds root. If find(a)==find(b) → same component.

Choose your traversal:
  DFS: uses stack/recursion, good for connectivity, components, paths
  BFS: uses queue, good for shortest paths, level-by-level processing
  Union-Find: good for dynamic connectivity, cycle detection in undirected graphs
  Dijkstra: good for weighted shortest paths (non-negative weights)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Accounts Merge":
  Draw a small example graph (5-6 nodes).
  Trace the BFS/DFS manually.
  Track: visited set, queue/stack, answer variable.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS template:
def dfs(node, visited):
    visited.add(node)
    for neighbor in graph[node]:
        if neighbor not in visited:
            dfs(neighbor, visited)

# BFS template:
from collections import deque
queue = deque([start])
visited = {start}
while queue:
    node = queue.popleft()
    for neighbor in graph[node]:
        if neighbor not in visited:
            visited.add(neighbor)
            queue.append(neighbor)

# Union-Find template:
parent = list(range(n))
def find(x): 
    if parent[x] != x: parent[x] = find(parent[x])
    return parent[x]
def union(x, y):
    px, py = find(x), find(y)
    if px == py: return False  # already connected
    parent[px] = py; return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw a small example from LeetCode.
Trace your BFS/DFS/Union-Find step by step.
Track: current node, visited set, queue state, answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Maintain parent array. union(a,b) joins components. find(a) finds root. If find(a)==find(b) → same component.

BFS vs DFS:
  BFS = guaranteed shortest path (unweighted graph)
  DFS = simpler for connectivity, cycles, paths
  Union-Find = O(α) nearly O(1) per operation, great for dynamic connectivity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS for connectivity:
def dfs(node): visited.add(node); [dfs(nb) for nb in adj[node] if nb not in visited]

# BFS for shortest path:
queue=deque([start]); visited={start}; dist=0
while queue:
    for _ in range(len(queue)):
        node=queue.popleft()
        for nb in adj[node]:
            if nb not in visited: visited.add(nb); queue.append(nb)
    dist+=1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DFS/BFS: O(V+E) time, O(V) space for visited
Dijkstra: O((V+E) log V)
Union-Find: O(α(n)) per operation (nearly O(1))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Forgetting to mark nodes as visited (infinite loop!)
2. Using DFS when BFS needed for shortest path
3. Not adding to visited BEFORE enqueuing (adds duplicates to queue)
4. Union-Find: not applying path compression

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: implement the same problem with the OTHER traversal method.
If you used DFS, try BFS. If BFS, try Union-Find.
```

---

## Problem 218/250 — LeetCode #743: Network Delay Time

**Pattern:** `Dijkstra` | **Position in Roadmap:** #218

```
LeetCode 743 — Network Delay Time
Problem #218 of 250 | Difficulty: Medium | Pattern: Dijkstra

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 743: Network Delay Time | Medium

For graph problems, always identify:
  NODES: what are the vertices? (cells, integers, strings, words)
  EDGES: what connects them? (adjacency, character differences, courses)
  GOAL: shortest path? connected components? cycle? order?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Dijkstra

Shortest path with non-negative weights. Use min-heap. Process nodes in order of current shortest distance.

Choose your traversal:
  DFS: uses stack/recursion, good for connectivity, components, paths
  BFS: uses queue, good for shortest paths, level-by-level processing
  Union-Find: good for dynamic connectivity, cycle detection in undirected graphs
  Dijkstra: good for weighted shortest paths (non-negative weights)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Network Delay Time":
  Draw a small example graph (5-6 nodes).
  Trace the BFS/DFS manually.
  Track: visited set, queue/stack, answer variable.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS template:
def dfs(node, visited):
    visited.add(node)
    for neighbor in graph[node]:
        if neighbor not in visited:
            dfs(neighbor, visited)

# BFS template:
from collections import deque
queue = deque([start])
visited = {start}
while queue:
    node = queue.popleft()
    for neighbor in graph[node]:
        if neighbor not in visited:
            visited.add(neighbor)
            queue.append(neighbor)

# Union-Find template:
parent = list(range(n))
def find(x): 
    if parent[x] != x: parent[x] = find(parent[x])
    return parent[x]
def union(x, y):
    px, py = find(x), find(y)
    if px == py: return False  # already connected
    parent[px] = py; return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw a small example from LeetCode.
Trace your BFS/DFS/Union-Find step by step.
Track: current node, visited set, queue state, answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Shortest path with non-negative weights. Use min-heap. Process nodes in order of current shortest distance.

BFS vs DFS:
  BFS = guaranteed shortest path (unweighted graph)
  DFS = simpler for connectivity, cycles, paths
  Union-Find = O(α) nearly O(1) per operation, great for dynamic connectivity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS for connectivity:
def dfs(node): visited.add(node); [dfs(nb) for nb in adj[node] if nb not in visited]

# BFS for shortest path:
queue=deque([start]); visited={start}; dist=0
while queue:
    for _ in range(len(queue)):
        node=queue.popleft()
        for nb in adj[node]:
            if nb not in visited: visited.add(nb); queue.append(nb)
    dist+=1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DFS/BFS: O(V+E) time, O(V) space for visited
Dijkstra: O((V+E) log V)
Union-Find: O(α(n)) per operation (nearly O(1))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Forgetting to mark nodes as visited (infinite loop!)
2. Using DFS when BFS needed for shortest path
3. Not adding to visited BEFORE enqueuing (adds duplicates to queue)
4. Union-Find: not applying path compression

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: implement the same problem with the OTHER traversal method.
If you used DFS, try BFS. If BFS, try Union-Find.
```

---

## Problem 219/250 — LeetCode #787: Cheapest Flights Within K Stops

**Pattern:** `Graph / DP` | **Position in Roadmap:** #219

```
LeetCode 787 — Cheapest Flights Within K Stops
Problem #219 of 250 | Difficulty: Medium | Pattern: Bellman-Ford/DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 787: Cheapest Flights Within K Stops | Medium

For graph problems, always identify:
  NODES: what are the vertices? (cells, integers, strings, words)
  EDGES: what connects them? (adjacency, character differences, courses)
  GOAL: shortest path? connected components? cycle? order?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Bellman-Ford/DP

Graph traversal using Bellman-Ford/DP. Identify nodes, edges, and what you're counting.

Choose your traversal:
  DFS: uses stack/recursion, good for connectivity, components, paths
  BFS: uses queue, good for shortest paths, level-by-level processing
  Union-Find: good for dynamic connectivity, cycle detection in undirected graphs
  Dijkstra: good for weighted shortest paths (non-negative weights)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Cheapest Flights Within K Stops":
  Draw a small example graph (5-6 nodes).
  Trace the BFS/DFS manually.
  Track: visited set, queue/stack, answer variable.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS template:
def dfs(node, visited):
    visited.add(node)
    for neighbor in graph[node]:
        if neighbor not in visited:
            dfs(neighbor, visited)

# BFS template:
from collections import deque
queue = deque([start])
visited = {start}
while queue:
    node = queue.popleft()
    for neighbor in graph[node]:
        if neighbor not in visited:
            visited.add(neighbor)
            queue.append(neighbor)

# Union-Find template:
parent = list(range(n))
def find(x): 
    if parent[x] != x: parent[x] = find(parent[x])
    return parent[x]
def union(x, y):
    px, py = find(x), find(y)
    if px == py: return False  # already connected
    parent[px] = py; return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw a small example from LeetCode.
Trace your BFS/DFS/Union-Find step by step.
Track: current node, visited set, queue state, answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Graph traversal using Bellman-Ford/DP. Identify nodes, edges, and what you're counting.

BFS vs DFS:
  BFS = guaranteed shortest path (unweighted graph)
  DFS = simpler for connectivity, cycles, paths
  Union-Find = O(α) nearly O(1) per operation, great for dynamic connectivity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS for connectivity:
def dfs(node): visited.add(node); [dfs(nb) for nb in adj[node] if nb not in visited]

# BFS for shortest path:
queue=deque([start]); visited={start}; dist=0
while queue:
    for _ in range(len(queue)):
        node=queue.popleft()
        for nb in adj[node]:
            if nb not in visited: visited.add(nb); queue.append(nb)
    dist+=1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DFS/BFS: O(V+E) time, O(V) space for visited
Dijkstra: O((V+E) log V)
Union-Find: O(α(n)) per operation (nearly O(1))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Forgetting to mark nodes as visited (infinite loop!)
2. Using DFS when BFS needed for shortest path
3. Not adding to visited BEFORE enqueuing (adds duplicates to queue)
4. Union-Find: not applying path compression

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: implement the same problem with the OTHER traversal method.
If you used DFS, try BFS. If BFS, try Union-Find.
```

---

## Problem 220/250 — LeetCode #1584: Min Cost to Connect All Points

**Pattern:** `MST` | **Position in Roadmap:** #220

```
LeetCode 1584 — Min Cost to Connect All Points
Problem #220 of 250 | Difficulty: Medium | Pattern: MST — Prim's/Kruskal's

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 1584: Min Cost to Connect All Points | Medium

For graph problems, always identify:
  NODES: what are the vertices? (cells, integers, strings, words)
  EDGES: what connects them? (adjacency, character differences, courses)
  GOAL: shortest path? connected components? cycle? order?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: MST — Prim's/Kruskal's

Connect all nodes with minimum total edge weight. Kruskal: sort edges, union-find. Prim: min-heap.

Choose your traversal:
  DFS: uses stack/recursion, good for connectivity, components, paths
  BFS: uses queue, good for shortest paths, level-by-level processing
  Union-Find: good for dynamic connectivity, cycle detection in undirected graphs
  Dijkstra: good for weighted shortest paths (non-negative weights)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Min Cost to Connect All Points":
  Draw a small example graph (5-6 nodes).
  Trace the BFS/DFS manually.
  Track: visited set, queue/stack, answer variable.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS template:
def dfs(node, visited):
    visited.add(node)
    for neighbor in graph[node]:
        if neighbor not in visited:
            dfs(neighbor, visited)

# BFS template:
from collections import deque
queue = deque([start])
visited = {start}
while queue:
    node = queue.popleft()
    for neighbor in graph[node]:
        if neighbor not in visited:
            visited.add(neighbor)
            queue.append(neighbor)

# Union-Find template:
parent = list(range(n))
def find(x): 
    if parent[x] != x: parent[x] = find(parent[x])
    return parent[x]
def union(x, y):
    px, py = find(x), find(y)
    if px == py: return False  # already connected
    parent[px] = py; return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw a small example from LeetCode.
Trace your BFS/DFS/Union-Find step by step.
Track: current node, visited set, queue state, answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Connect all nodes with minimum total edge weight. Kruskal: sort edges, union-find. Prim: min-heap.

BFS vs DFS:
  BFS = guaranteed shortest path (unweighted graph)
  DFS = simpler for connectivity, cycles, paths
  Union-Find = O(α) nearly O(1) per operation, great for dynamic connectivity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS for connectivity:
def dfs(node): visited.add(node); [dfs(nb) for nb in adj[node] if nb not in visited]

# BFS for shortest path:
queue=deque([start]); visited={start}; dist=0
while queue:
    for _ in range(len(queue)):
        node=queue.popleft()
        for nb in adj[node]:
            if nb not in visited: visited.add(nb); queue.append(nb)
    dist+=1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DFS/BFS: O(V+E) time, O(V) space for visited
Dijkstra: O((V+E) log V)
Union-Find: O(α(n)) per operation (nearly O(1))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Forgetting to mark nodes as visited (infinite loop!)
2. Using DFS when BFS needed for shortest path
3. Not adding to visited BEFORE enqueuing (adds duplicates to queue)
4. Union-Find: not applying path compression

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: implement the same problem with the OTHER traversal method.
If you used DFS, try BFS. If BFS, try Union-Find.
```

---

## Problem 221/250 — LeetCode #127: Word Ladder

**Pattern:** `BFS` | **Position in Roadmap:** #221

```
LeetCode 127 — Word Ladder
Problem #221 of 250 | Difficulty: Hard | Pattern: BFS — Shortest Path

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 127: Word Ladder | Hard

For graph problems, always identify:
  NODES: what are the vertices? (cells, integers, strings, words)
  EDGES: what connects them? (adjacency, character differences, courses)
  GOAL: shortest path? connected components? cycle? order?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: BFS — Shortest Path

BFS on implicit graph. Each 'state' is a node. Transitions are edges. Level = distance.

Choose your traversal:
  DFS: uses stack/recursion, good for connectivity, components, paths
  BFS: uses queue, good for shortest paths, level-by-level processing
  Union-Find: good for dynamic connectivity, cycle detection in undirected graphs
  Dijkstra: good for weighted shortest paths (non-negative weights)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Word Ladder":
  Draw a small example graph (5-6 nodes).
  Trace the BFS/DFS manually.
  Track: visited set, queue/stack, answer variable.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS template:
def dfs(node, visited):
    visited.add(node)
    for neighbor in graph[node]:
        if neighbor not in visited:
            dfs(neighbor, visited)

# BFS template:
from collections import deque
queue = deque([start])
visited = {start}
while queue:
    node = queue.popleft()
    for neighbor in graph[node]:
        if neighbor not in visited:
            visited.add(neighbor)
            queue.append(neighbor)

# Union-Find template:
parent = list(range(n))
def find(x): 
    if parent[x] != x: parent[x] = find(parent[x])
    return parent[x]
def union(x, y):
    px, py = find(x), find(y)
    if px == py: return False  # already connected
    parent[px] = py; return True

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Draw a small example from LeetCode.
Trace your BFS/DFS/Union-Find step by step.
Track: current node, visited set, queue state, answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BFS on implicit graph. Each 'state' is a node. Transitions are edges. Level = distance.

BFS vs DFS:
  BFS = guaranteed shortest path (unweighted graph)
  DFS = simpler for connectivity, cycles, paths
  Union-Find = O(α) nearly O(1) per operation, great for dynamic connectivity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# DFS for connectivity:
def dfs(node): visited.add(node); [dfs(nb) for nb in adj[node] if nb not in visited]

# BFS for shortest path:
queue=deque([start]); visited={start}; dist=0
while queue:
    for _ in range(len(queue)):
        node=queue.popleft()
        for nb in adj[node]:
            if nb not in visited: visited.add(nb); queue.append(nb)
    dist+=1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DFS/BFS: O(V+E) time, O(V) space for visited
Dijkstra: O((V+E) log V)
Union-Find: O(α(n)) per operation (nearly O(1))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Forgetting to mark nodes as visited (infinite loop!)
2. Using DFS when BFS needed for shortest path
3. Not adding to visited BEFORE enqueuing (adds duplicates to queue)
4. Union-Find: not applying path compression

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving: implement the same problem with the OTHER traversal method.
If you used DFS, try BFS. If BFS, try Union-Find.
```

---

## Problem 222/250 — LeetCode #70: Climbing Stairs

**Pattern:** `1D DP` | **Position in Roadmap:** #222

```
LeetCode 746 — Min Cost Climbing Stairs
Problem #222 of 250 | Difficulty: Easy | Pattern: 1D DP — Linear

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 746: Min Cost Climbing Stairs | Easy

For DP problems, ask:
  1. What is the "subproblem"? (What smaller answer helps build the bigger answer?)
  2. What do I need to REMEMBER? (What are my DP state variables?)
  3. How does dp[i] relate to dp[i-1] or dp[i][j] to dp[i-1][j]?

DP Type: Linear 1D DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 1D DP — Linear

Recurrence Relation:
dp[i] = min(dp[i-1] + cost[i-1], dp[i-2] + cost[i-2])
OR: cost[i] += min(cost[i-1], cost[i-2]) in-place

Base Cases:
dp[0] = 0 (no cost to start from step 0 or 1)
dp[1] = 0

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP Thinking Steps:
  1. Define what dp[i] (or dp[i][j]) represents in plain English
  2. Find the recurrence: how does dp[i] depend on previous states?
  3. Set base cases: what are the smallest valid states?
  4. Determine traversal order: which way do you fill the table?
  5. Identify what to return: dp[n]? max(dp)? dp[m][n]?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Example trace:
cost = [10,15,20]

dp = [0, 0, 0, 0]  (size n+1)
dp[2] = min(dp[0]+cost[0], dp[1]+cost[1]) = min(0+10, 0+15) = 10
dp[3] = min(dp[1]+cost[1], dp[2]+cost[2]) = min(0+15, 10+20) = 15

Return dp[3] = 15 ✓ (take step 1 → done)

Optimization:
Use two variables instead of array: prev2, prev1 = 0, 0

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Fill in the DP table for a small example:
  Draw the table with rows and columns labeled.
  Fill left to right, top to bottom (or the appropriate order).
  Check each cell against your recurrence.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP solves problems with OVERLAPPING SUBPROBLEMS and OPTIMAL SUBSTRUCTURE.
The recurrence relation is the heart of DP.
Once you have the recurrence, the code practically writes itself.

Practice: Can you derive the recurrence WITHOUT looking at the solution?
That's the skill you're building!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# 1D DP:
dp = [0] * (n+1)
dp[0] = base_case_0
dp[1] = base_case_1
for i in range(2, n+1):
    dp[i] = f(dp[i-1], dp[i-2])  # your recurrence
return dp[n]

# 2D DP:
dp = [[0]*(n+1) for _ in range(m+1)]
for i in range(m+1): dp[i][0] = ...  # base cases
for j in range(n+1): dp[0][j] = ...  # base cases
for i in range(1,m+1):
    for j in range(1,n+1):
        dp[i][j] = f(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])  # recurrence

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n) for 1D DP, O(m*n) for 2D DP
Space: O(n) or O(m*n), can often optimize to O(1) or O(n) with rolling array

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Wrong recurrence relation (most common mistake!)
2. Off-by-one in dp array size (use dp[n+1] for n elements)
3. Wrong base cases
4. Wrong traversal direction (sometimes must go right-to-left)
5. Returning wrong part of dp table

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving with full dp array: can you optimize space to O(1)?
Many 1D DP problems only need the previous 1 or 2 values.
Many 2D DP problems only need the previous row.
```

---

## Problem 223/250 — LeetCode #746: Min Cost Climbing Stairs

**Pattern:** `1D DP` | **Position in Roadmap:** #223

```
LeetCode 198 — House Robber
Problem #223 of 250 | Difficulty: Medium | Pattern: 1D DP — Skip

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 198: House Robber | Medium

For DP problems, ask:
  1. What is the "subproblem"? (What smaller answer helps build the bigger answer?)
  2. What do I need to REMEMBER? (What are my DP state variables?)
  3. How does dp[i] relate to dp[i-1] or dp[i][j] to dp[i-1][j]?

DP Type: Linear 1D DP with skip

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 1D DP — Skip

Recurrence Relation:
dp[i] = max(dp[i-1], dp[i-2] + nums[i])
'Either skip house i (take dp[i-1]) or rob house i (dp[i-2]+nums[i])'

Base Cases:
dp[0] = nums[0]
dp[1] = max(nums[0], nums[1])

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP Thinking Steps:
  1. Define what dp[i] (or dp[i][j]) represents in plain English
  2. Find the recurrence: how does dp[i] depend on previous states?
  3. Set base cases: what are the smallest valid states?
  4. Determine traversal order: which way do you fill the table?
  5. Identify what to return: dp[n]? max(dp)? dp[m][n]?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Example trace:
nums = [2,7,9,3,1]

dp[0] = 2
dp[1] = max(2,7) = 7
dp[2] = max(dp[1], dp[0]+9) = max(7, 2+9) = 11
dp[3] = max(dp[2], dp[1]+3) = max(11, 7+3) = 11
dp[4] = max(dp[3], dp[2]+1) = max(11, 11+1) = 12

Return dp[4] = 12 ✓ (rob houses 0,2,4: 2+9+1=12)

Optimization:
prev2=0, prev1=0
for num: curr=max(prev1, prev2+num); prev2=prev1; prev1=curr

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Fill in the DP table for a small example:
  Draw the table with rows and columns labeled.
  Fill left to right, top to bottom (or the appropriate order).
  Check each cell against your recurrence.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP solves problems with OVERLAPPING SUBPROBLEMS and OPTIMAL SUBSTRUCTURE.
The recurrence relation is the heart of DP.
Once you have the recurrence, the code practically writes itself.

Practice: Can you derive the recurrence WITHOUT looking at the solution?
That's the skill you're building!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# 1D DP:
dp = [0] * (n+1)
dp[0] = base_case_0
dp[1] = base_case_1
for i in range(2, n+1):
    dp[i] = f(dp[i-1], dp[i-2])  # your recurrence
return dp[n]

# 2D DP:
dp = [[0]*(n+1) for _ in range(m+1)]
for i in range(m+1): dp[i][0] = ...  # base cases
for j in range(n+1): dp[0][j] = ...  # base cases
for i in range(1,m+1):
    for j in range(1,n+1):
        dp[i][j] = f(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])  # recurrence

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n) for 1D DP, O(m*n) for 2D DP
Space: O(n) or O(m*n), can often optimize to O(1) or O(n) with rolling array

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Wrong recurrence relation (most common mistake!)
2. Off-by-one in dp array size (use dp[n+1] for n elements)
3. Wrong base cases
4. Wrong traversal direction (sometimes must go right-to-left)
5. Returning wrong part of dp table

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving with full dp array: can you optimize space to O(1)?
Many 1D DP problems only need the previous 1 or 2 values.
Many 2D DP problems only need the previous row.
```

---

## Problem 224/250 — LeetCode #198: House Robber

**Pattern:** `1D DP` | **Position in Roadmap:** #224

```
LeetCode 213 — House Robber II
Problem #224 of 250 | Difficulty: Medium | Pattern: 1D DP — Circular

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 213: House Robber II | Medium

For DP problems, ask:
  1. What is the "subproblem"? (What smaller answer helps build the bigger answer?)
  2. What do I need to REMEMBER? (What are my DP state variables?)
  3. How does dp[i] relate to dp[i-1] or dp[i][j] to dp[i-1][j]?

DP Type: Circular 1D DP (run twice)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 1D DP — Circular

Recurrence Relation:
rob(nums[0..n-2]) and rob(nums[1..n-1]), take maximum
Same recurrence as House Robber I, but applied to two subarrays.

Base Cases:
Same as LC198 for each subarray

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP Thinking Steps:
  1. Define what dp[i] (or dp[i][j]) represents in plain English
  2. Find the recurrence: how does dp[i] depend on previous states?
  3. Set base cases: what are the smallest valid states?
  4. Determine traversal order: which way do you fill the table?
  5. Identify what to return: dp[n]? max(dp)? dp[m][n]?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Example trace:
nums = [2,3,2]

Case 1: rob [2,3] (skip last): dp=[2,3] → max=3
Case 2: rob [3,2] (skip first): dp=[3,3] → max=3

Return max(3,3) = 3 ✓

Why split? Can't rob both first AND last house (they're adjacent in circular array).

Optimization:
def rob(nums): two-variable DP; return max(rob(nums[:-1]), rob(nums[1:]))

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Fill in the DP table for a small example:
  Draw the table with rows and columns labeled.
  Fill left to right, top to bottom (or the appropriate order).
  Check each cell against your recurrence.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP solves problems with OVERLAPPING SUBPROBLEMS and OPTIMAL SUBSTRUCTURE.
The recurrence relation is the heart of DP.
Once you have the recurrence, the code practically writes itself.

Practice: Can you derive the recurrence WITHOUT looking at the solution?
That's the skill you're building!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# 1D DP:
dp = [0] * (n+1)
dp[0] = base_case_0
dp[1] = base_case_1
for i in range(2, n+1):
    dp[i] = f(dp[i-1], dp[i-2])  # your recurrence
return dp[n]

# 2D DP:
dp = [[0]*(n+1) for _ in range(m+1)]
for i in range(m+1): dp[i][0] = ...  # base cases
for j in range(n+1): dp[0][j] = ...  # base cases
for i in range(1,m+1):
    for j in range(1,n+1):
        dp[i][j] = f(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])  # recurrence

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n) for 1D DP, O(m*n) for 2D DP
Space: O(n) or O(m*n), can often optimize to O(1) or O(n) with rolling array

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Wrong recurrence relation (most common mistake!)
2. Off-by-one in dp array size (use dp[n+1] for n elements)
3. Wrong base cases
4. Wrong traversal direction (sometimes must go right-to-left)
5. Returning wrong part of dp table

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving with full dp array: can you optimize space to O(1)?
Many 1D DP problems only need the previous 1 or 2 values.
Many 2D DP problems only need the previous row.
```

---

## Problem 225/250 — LeetCode #213: House Robber II

**Pattern:** `1D DP` | **Position in Roadmap:** #225

```
LeetCode 337 — House Robber III
Problem #225 of 250 | Difficulty: Medium | Pattern: Tree DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 337: House Robber III | Medium

For DP problems, ask:
  1. What is the "subproblem"? (What smaller answer helps build the bigger answer?)
  2. What do I need to REMEMBER? (What are my DP state variables?)
  3. How does dp[i] relate to dp[i-1] or dp[i][j] to dp[i-1][j]?

DP Type: Tree DP — Post-order

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Tree DP

Recurrence Relation:
At each node, return pair (rob_this, skip_this):
  rob_this = node.val + skip_left + skip_right
  skip_this = max(rob_left,skip_left) + max(rob_right,skip_right)

Base Cases:
null node: return (0, 0)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP Thinking Steps:
  1. Define what dp[i] (or dp[i][j]) represents in plain English
  2. Find the recurrence: how does dp[i] depend on previous states?
  3. Set base cases: what are the smallest valid states?
  4. Determine traversal order: which way do you fill the table?
  5. Identify what to return: dp[n]? max(dp)? dp[m][n]?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Example trace:
    3
   / \
  2   3
   \    \
    3    1

At leaf 3 (child of 2): return (3, 0)
At leaf 1 (child of 3): return (1, 0)
At node 2: rob=2+0=2, skip=max(3,0)+max(0,0)=3 → return (2,3)
At node 3 (right): rob=3+0=3, skip=max(1,0)=1 → return (3,1)
At root 3: rob=3+3+1=7, skip=max(2,3)+max(3,1)=3+3=6 → return (7,6)
Answer: max(7,6) = 7 ✓



━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Fill in the DP table for a small example:
  Draw the table with rows and columns labeled.
  Fill left to right, top to bottom (or the appropriate order).
  Check each cell against your recurrence.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP solves problems with OVERLAPPING SUBPROBLEMS and OPTIMAL SUBSTRUCTURE.
The recurrence relation is the heart of DP.
Once you have the recurrence, the code practically writes itself.

Practice: Can you derive the recurrence WITHOUT looking at the solution?
That's the skill you're building!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# 1D DP:
dp = [0] * (n+1)
dp[0] = base_case_0
dp[1] = base_case_1
for i in range(2, n+1):
    dp[i] = f(dp[i-1], dp[i-2])  # your recurrence
return dp[n]

# 2D DP:
dp = [[0]*(n+1) for _ in range(m+1)]
for i in range(m+1): dp[i][0] = ...  # base cases
for j in range(n+1): dp[0][j] = ...  # base cases
for i in range(1,m+1):
    for j in range(1,n+1):
        dp[i][j] = f(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])  # recurrence

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n) for 1D DP, O(m*n) for 2D DP
Space: O(n) or O(m*n), can often optimize to O(1) or O(n) with rolling array

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Wrong recurrence relation (most common mistake!)
2. Off-by-one in dp array size (use dp[n+1] for n elements)
3. Wrong base cases
4. Wrong traversal direction (sometimes must go right-to-left)
5. Returning wrong part of dp table

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving with full dp array: can you optimize space to O(1)?
Many 1D DP problems only need the previous 1 or 2 values.
Many 2D DP problems only need the previous row.
```

---

## Problem 226/250 — LeetCode #91: Decode Ways

**Pattern:** `1D DP` | **Position in Roadmap:** #226

```
LeetCode 300 — Longest Increasing Subsequence
Problem #226 of 250 | Difficulty: Medium | Pattern: 1D DP or Binary Search

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 300: Longest Increasing Subsequence | Medium

For DP problems, ask:
  1. What is the "subproblem"? (What smaller answer helps build the bigger answer?)
  2. What do I need to REMEMBER? (What are my DP state variables?)
  3. How does dp[i] relate to dp[i-1] or dp[i][j] to dp[i-1][j]?

DP Type: 1D DP or Patience Sorting (Binary Search)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 1D DP or Binary Search

Recurrence Relation:
dp[i] = max(dp[j]+1) for all j<i where nums[j]<nums[i]
OR: Binary search patience sorting for O(n log n)

Base Cases:
dp[i] = 1 for all i (single element is LIS of length 1)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP Thinking Steps:
  1. Define what dp[i] (or dp[i][j]) represents in plain English
  2. Find the recurrence: how does dp[i] depend on previous states?
  3. Set base cases: what are the smallest valid states?
  4. Determine traversal order: which way do you fill the table?
  5. Identify what to return: dp[n]? max(dp)? dp[m][n]?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Example trace:
nums = [10,9,2,5,3,7,101,18]

dp[0]=1 (10)
dp[1]=1 (9, nothing before is smaller)
dp[2]=1 (2)
dp[3]=2 (5, 2<5 so dp[2]+1=2)
dp[4]=2 (3, 2<3 so dp[2]+1=2)
dp[5]=3 (7, 5<7 dp[3]+1=3, 3<7 dp[4]+1=3)
dp[6]=4 (101, 7<101 dp[5]+1=4)
dp[7]=4 (18, 7<18 dp[5]+1=4)

max(dp) = 4 ✓ (LIS: 2,3,7,18 or 2,5,7,101)

Optimization:
O(n log n): maintain 'tails' array. Binary search for insertion position.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Fill in the DP table for a small example:
  Draw the table with rows and columns labeled.
  Fill left to right, top to bottom (or the appropriate order).
  Check each cell against your recurrence.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP solves problems with OVERLAPPING SUBPROBLEMS and OPTIMAL SUBSTRUCTURE.
The recurrence relation is the heart of DP.
Once you have the recurrence, the code practically writes itself.

Practice: Can you derive the recurrence WITHOUT looking at the solution?
That's the skill you're building!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# 1D DP:
dp = [0] * (n+1)
dp[0] = base_case_0
dp[1] = base_case_1
for i in range(2, n+1):
    dp[i] = f(dp[i-1], dp[i-2])  # your recurrence
return dp[n]

# 2D DP:
dp = [[0]*(n+1) for _ in range(m+1)]
for i in range(m+1): dp[i][0] = ...  # base cases
for j in range(n+1): dp[0][j] = ...  # base cases
for i in range(1,m+1):
    for j in range(1,n+1):
        dp[i][j] = f(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])  # recurrence

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n) for 1D DP, O(m*n) for 2D DP
Space: O(n) or O(m*n), can often optimize to O(1) or O(n) with rolling array

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Wrong recurrence relation (most common mistake!)
2. Off-by-one in dp array size (use dp[n+1] for n elements)
3. Wrong base cases
4. Wrong traversal direction (sometimes must go right-to-left)
5. Returning wrong part of dp table

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving with full dp array: can you optimize space to O(1)?
Many 1D DP problems only need the previous 1 or 2 values.
Many 2D DP problems only need the previous row.
```

---

## Problem 227/250 — LeetCode #322: Coin Change

**Pattern:** `Unbounded Knapsack` | **Position in Roadmap:** #227

```
LeetCode 1143 — Longest Common Subsequence
Problem #227 of 250 | Difficulty: Medium | Pattern: 2D DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 1143: Longest Common Subsequence | Medium

For DP problems, ask:
  1. What is the "subproblem"? (What smaller answer helps build the bigger answer?)
  2. What do I need to REMEMBER? (What are my DP state variables?)
  3. How does dp[i] relate to dp[i-1] or dp[i][j] to dp[i-1][j]?

DP Type: 2D DP — Classic

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 2D DP

Recurrence Relation:
dp[i][j] = dp[i-1][j-1]+1 if text1[i-1]==text2[j-1]
           else max(dp[i-1][j], dp[i][j-1])

Base Cases:
dp[0][j] = 0, dp[i][0] = 0 (empty string has LCS=0 with anything)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP Thinking Steps:
  1. Define what dp[i] (or dp[i][j]) represents in plain English
  2. Find the recurrence: how does dp[i] depend on previous states?
  3. Set base cases: what are the smallest valid states?
  4. Determine traversal order: which way do you fill the table?
  5. Identify what to return: dp[n]? max(dp)? dp[m][n]?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Example trace:
text1="abcde", text2="ace"

   | '' a c e
 --+---------
 ''|  0 0 0 0
 a |  0 1 1 1
 b |  0 1 1 1
 c |  0 1 2 2
 d |  0 1 2 2
 e |  0 1 2 3

dp[5][3] = 3 ✓ (LCS = "ace")

If chars match: diagonal+1
If not: max(up, left)



━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Fill in the DP table for a small example:
  Draw the table with rows and columns labeled.
  Fill left to right, top to bottom (or the appropriate order).
  Check each cell against your recurrence.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP solves problems with OVERLAPPING SUBPROBLEMS and OPTIMAL SUBSTRUCTURE.
The recurrence relation is the heart of DP.
Once you have the recurrence, the code practically writes itself.

Practice: Can you derive the recurrence WITHOUT looking at the solution?
That's the skill you're building!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# 1D DP:
dp = [0] * (n+1)
dp[0] = base_case_0
dp[1] = base_case_1
for i in range(2, n+1):
    dp[i] = f(dp[i-1], dp[i-2])  # your recurrence
return dp[n]

# 2D DP:
dp = [[0]*(n+1) for _ in range(m+1)]
for i in range(m+1): dp[i][0] = ...  # base cases
for j in range(n+1): dp[0][j] = ...  # base cases
for i in range(1,m+1):
    for j in range(1,n+1):
        dp[i][j] = f(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])  # recurrence

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n) for 1D DP, O(m*n) for 2D DP
Space: O(n) or O(m*n), can often optimize to O(1) or O(n) with rolling array

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Wrong recurrence relation (most common mistake!)
2. Off-by-one in dp array size (use dp[n+1] for n elements)
3. Wrong base cases
4. Wrong traversal direction (sometimes must go right-to-left)
5. Returning wrong part of dp table

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving with full dp array: can you optimize space to O(1)?
Many 1D DP problems only need the previous 1 or 2 values.
Many 2D DP problems only need the previous row.
```

---

## Problem 228/250 — LeetCode #518: Coin Change II

**Pattern:** `DP` | **Position in Roadmap:** #228

```
LeetCode 72 — Edit Distance
Problem #228 of 250 | Difficulty: Medium | Pattern: 2D DP — Three Operations

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 72: Edit Distance | Medium

For DP problems, ask:
  1. What is the "subproblem"? (What smaller answer helps build the bigger answer?)
  2. What do I need to REMEMBER? (What are my DP state variables?)
  3. How does dp[i] relate to dp[i-1] or dp[i][j] to dp[i-1][j]?

DP Type: 2D DP — Edit Distance

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 2D DP — Three Operations

Recurrence Relation:
dp[i][j] = dp[i-1][j-1] if word1[i-1]==word2[j-1]
           else 1 + min(dp[i-1][j],   # delete from word1
                      dp[i][j-1],   # insert into word1
                      dp[i-1][j-1]) # replace

Base Cases:
dp[i][0] = i (delete i chars from word1 to reach empty)
dp[0][j] = j (insert j chars to reach word2)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP Thinking Steps:
  1. Define what dp[i] (or dp[i][j]) represents in plain English
  2. Find the recurrence: how does dp[i] depend on previous states?
  3. Set base cases: what are the smallest valid states?
  4. Determine traversal order: which way do you fill the table?
  5. Identify what to return: dp[n]? max(dp)? dp[m][n]?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Example trace:
word1="horse", word2="ros"

   |  '' r  o  s
---+-------------
 ''|  0  1  2  3
 h |  1  1  2  3
 o |  2  2  1  2
 r |  3  2  2  2
 s |  4  3  3  2
 e |  5  4  4  3

dp[5][3] = 3 ✓ (horse→rorse→rose→ros)



━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Fill in the DP table for a small example:
  Draw the table with rows and columns labeled.
  Fill left to right, top to bottom (or the appropriate order).
  Check each cell against your recurrence.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP solves problems with OVERLAPPING SUBPROBLEMS and OPTIMAL SUBSTRUCTURE.
The recurrence relation is the heart of DP.
Once you have the recurrence, the code practically writes itself.

Practice: Can you derive the recurrence WITHOUT looking at the solution?
That's the skill you're building!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# 1D DP:
dp = [0] * (n+1)
dp[0] = base_case_0
dp[1] = base_case_1
for i in range(2, n+1):
    dp[i] = f(dp[i-1], dp[i-2])  # your recurrence
return dp[n]

# 2D DP:
dp = [[0]*(n+1) for _ in range(m+1)]
for i in range(m+1): dp[i][0] = ...  # base cases
for j in range(n+1): dp[0][j] = ...  # base cases
for i in range(1,m+1):
    for j in range(1,n+1):
        dp[i][j] = f(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])  # recurrence

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n) for 1D DP, O(m*n) for 2D DP
Space: O(n) or O(m*n), can often optimize to O(1) or O(n) with rolling array

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Wrong recurrence relation (most common mistake!)
2. Off-by-one in dp array size (use dp[n+1] for n elements)
3. Wrong base cases
4. Wrong traversal direction (sometimes must go right-to-left)
5. Returning wrong part of dp table

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving with full dp array: can you optimize space to O(1)?
Many 1D DP problems only need the previous 1 or 2 values.
Many 2D DP problems only need the previous row.
```

---

## Problem 229/250 — LeetCode #377: Combination Sum IV

**Pattern:** `DP` | **Position in Roadmap:** #229

```
LeetCode 322 — Coin Change
Problem #229 of 250 | Difficulty: Medium | Pattern: 1D DP — Unbounded Knapsack

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 322: Coin Change | Medium

For DP problems, ask:
  1. What is the "subproblem"? (What smaller answer helps build the bigger answer?)
  2. What do I need to REMEMBER? (What are my DP state variables?)
  3. How does dp[i] relate to dp[i-1] or dp[i][j] to dp[i-1][j]?

DP Type: 1D DP — Unbounded Knapsack

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 1D DP — Unbounded Knapsack

Recurrence Relation:
dp[i] = min(dp[i-coin]+1) for each coin if coin<=i
dp[i] = minimum coins to make amount i

Base Cases:
dp[0] = 0 (0 coins to make amount 0)
dp[1..amount] = infinity

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP Thinking Steps:
  1. Define what dp[i] (or dp[i][j]) represents in plain English
  2. Find the recurrence: how does dp[i] depend on previous states?
  3. Set base cases: what are the smallest valid states?
  4. Determine traversal order: which way do you fill the table?
  5. Identify what to return: dp[n]? max(dp)? dp[m][n]?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Example trace:
coins=[1,5,11], amount=15

dp[0]=0
dp[1]: coin=1→dp[0]+1=1; min=1
dp[5]: coin=1→dp[4]+1=5, coin=5→dp[0]+1=1; min=1
dp[11]: coin=1→11, coin=5→dp[6]+1=3, coin=11→dp[0]+1=1; min=1
dp[15]: coin=1→dp[14]+1, coin=5→dp[10]+1=3, coin=11→dp[4]+1=5; min=3

Answer: dp[15]=3 ✓ (5+5+5 or 11+1+1+1+1... actually 11+4? No, 5+5+5=3 coins!)



━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Fill in the DP table for a small example:
  Draw the table with rows and columns labeled.
  Fill left to right, top to bottom (or the appropriate order).
  Check each cell against your recurrence.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP solves problems with OVERLAPPING SUBPROBLEMS and OPTIMAL SUBSTRUCTURE.
The recurrence relation is the heart of DP.
Once you have the recurrence, the code practically writes itself.

Practice: Can you derive the recurrence WITHOUT looking at the solution?
That's the skill you're building!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# 1D DP:
dp = [0] * (n+1)
dp[0] = base_case_0
dp[1] = base_case_1
for i in range(2, n+1):
    dp[i] = f(dp[i-1], dp[i-2])  # your recurrence
return dp[n]

# 2D DP:
dp = [[0]*(n+1) for _ in range(m+1)]
for i in range(m+1): dp[i][0] = ...  # base cases
for j in range(n+1): dp[0][j] = ...  # base cases
for i in range(1,m+1):
    for j in range(1,n+1):
        dp[i][j] = f(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])  # recurrence

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n) for 1D DP, O(m*n) for 2D DP
Space: O(n) or O(m*n), can often optimize to O(1) or O(n) with rolling array

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Wrong recurrence relation (most common mistake!)
2. Off-by-one in dp array size (use dp[n+1] for n elements)
3. Wrong base cases
4. Wrong traversal direction (sometimes must go right-to-left)
5. Returning wrong part of dp table

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving with full dp array: can you optimize space to O(1)?
Many 1D DP problems only need the previous 1 or 2 values.
Many 2D DP problems only need the previous row.
```

---

## Problem 230/250 — LeetCode #139: Word Break

**Pattern:** `DP` | **Position in Roadmap:** #230

```
LeetCode 518 — Coin Change II
Problem #230 of 250 | Difficulty: Medium | Pattern: 1D DP — Count Combinations

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 518: Coin Change II | Medium

For DP problems, ask:
  1. What is the "subproblem"? (What smaller answer helps build the bigger answer?)
  2. What do I need to REMEMBER? (What are my DP state variables?)
  3. How does dp[i] relate to dp[i-1] or dp[i][j] to dp[i-1][j]?

DP Type: 1D DP — Combination Count

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 1D DP — Count Combinations

Recurrence Relation:
dp[i] += dp[i-coin] for each coin if coin<=i
dp[i] = number of ways to make amount i

Base Cases:
dp[0] = 1 (one way to make amount 0: use no coins)
dp[1..amount] = 0

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP Thinking Steps:
  1. Define what dp[i] (or dp[i][j]) represents in plain English
  2. Find the recurrence: how does dp[i] depend on previous states?
  3. Set base cases: what are the smallest valid states?
  4. Determine traversal order: which way do you fill the table?
  5. Identify what to return: dp[n]? max(dp)? dp[m][n]?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Example trace:
coins=[1,2,5], amount=5

Process coin=1: dp[1]+=dp[0]=1, dp[2]+=dp[1]=1,...dp[5]=1
Process coin=2: dp[2]+=dp[0]=2, dp[3]+=dp[1]=2, dp[4]+=dp[2]=3, dp[5]+=dp[3]=3→4... wait
  dp=[1,1,2,2,3,3] after coin=1
  After coin=2: dp[2]+=dp[0]→2+1=... let me redo

dp[0]=1, rest=0
coin=1: dp[1]+=dp[0]→1, dp[2]+=dp[1]→1, dp[3]→1, dp[4]→1, dp[5]→1
coin=2: dp[2]+=dp[0]→2, dp[3]+=dp[1]→2, dp[4]+=dp[2]→3, dp[5]+=dp[3]→3
coin=5: dp[5]+=dp[0]→4

Answer: 4 ✓ (ways: [1,1,1,1,1],[1,1,1,2],[1,2,2],[5])

Key: process each coin FULLY before the next → ensures each coin used ≥0 times.



━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Fill in the DP table for a small example:
  Draw the table with rows and columns labeled.
  Fill left to right, top to bottom (or the appropriate order).
  Check each cell against your recurrence.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP solves problems with OVERLAPPING SUBPROBLEMS and OPTIMAL SUBSTRUCTURE.
The recurrence relation is the heart of DP.
Once you have the recurrence, the code practically writes itself.

Practice: Can you derive the recurrence WITHOUT looking at the solution?
That's the skill you're building!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# 1D DP:
dp = [0] * (n+1)
dp[0] = base_case_0
dp[1] = base_case_1
for i in range(2, n+1):
    dp[i] = f(dp[i-1], dp[i-2])  # your recurrence
return dp[n]

# 2D DP:
dp = [[0]*(n+1) for _ in range(m+1)]
for i in range(m+1): dp[i][0] = ...  # base cases
for j in range(n+1): dp[0][j] = ...  # base cases
for i in range(1,m+1):
    for j in range(1,n+1):
        dp[i][j] = f(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])  # recurrence

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n) for 1D DP, O(m*n) for 2D DP
Space: O(n) or O(m*n), can often optimize to O(1) or O(n) with rolling array

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Wrong recurrence relation (most common mistake!)
2. Off-by-one in dp array size (use dp[n+1] for n elements)
3. Wrong base cases
4. Wrong traversal direction (sometimes must go right-to-left)
5. Returning wrong part of dp table

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving with full dp array: can you optimize space to O(1)?
Many 1D DP problems only need the previous 1 or 2 values.
Many 2D DP problems only need the previous row.
```

---

## Problem 231/250 — LeetCode #300: Longest Increasing Subsequence

**Pattern:** `DP` | **Position in Roadmap:** #231

```
LeetCode 494 — Target Sum
Problem #231 of 250 | Difficulty: Medium | Pattern: 1D DP or DFS

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 494: Target Sum | Medium

For DP problems, ask:
  1. What is the "subproblem"? (What smaller answer helps build the bigger answer?)
  2. What do I need to REMEMBER? (What are my DP state variables?)
  3. How does dp[i] relate to dp[i-1] or dp[i][j] to dp[i-1][j]?

DP Type: 1D DP / Subset Sum Variant

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 1D DP or DFS

Recurrence Relation:
dp[amount] = number of subsets that sum to 'amount'
For each num: update dp (right to left for 0/1 choices)

Base Cases:
dp[offset] = 1 (one way to make 0: use no numbers)
offset = sum(nums) to handle negatives

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP Thinking Steps:
  1. Define what dp[i] (or dp[i][j]) represents in plain English
  2. Find the recurrence: how does dp[i] depend on previous states?
  3. Set base cases: what are the smallest valid states?
  4. Determine traversal order: which way do you fill the table?
  5. Identify what to return: dp[n]? max(dp)? dp[m][n]?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Example trace:
nums=[1,1,1,1,1], target=3

Sum=5. Create dp of size 2*5+1=11, offset=5.
dp[5]=1 (initial: 0 offset)

After num=1: dp[6]+=dp[5]=1, dp[4]+=dp[5]=1
After num=1: dp[7]+=dp[6]=1, dp[5]+=dp[6]+dp[4]=2, dp[3]+=dp[4]=1
...continue...

Final: dp[5+3]=dp[8] = 5 ✓

Alternatively: DFS with memoization on (index, current_sum).



━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Fill in the DP table for a small example:
  Draw the table with rows and columns labeled.
  Fill left to right, top to bottom (or the appropriate order).
  Check each cell against your recurrence.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP solves problems with OVERLAPPING SUBPROBLEMS and OPTIMAL SUBSTRUCTURE.
The recurrence relation is the heart of DP.
Once you have the recurrence, the code practically writes itself.

Practice: Can you derive the recurrence WITHOUT looking at the solution?
That's the skill you're building!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# 1D DP:
dp = [0] * (n+1)
dp[0] = base_case_0
dp[1] = base_case_1
for i in range(2, n+1):
    dp[i] = f(dp[i-1], dp[i-2])  # your recurrence
return dp[n]

# 2D DP:
dp = [[0]*(n+1) for _ in range(m+1)]
for i in range(m+1): dp[i][0] = ...  # base cases
for j in range(n+1): dp[0][j] = ...  # base cases
for i in range(1,m+1):
    for j in range(1,n+1):
        dp[i][j] = f(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])  # recurrence

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n) for 1D DP, O(m*n) for 2D DP
Space: O(n) or O(m*n), can often optimize to O(1) or O(n) with rolling array

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Wrong recurrence relation (most common mistake!)
2. Off-by-one in dp array size (use dp[n+1] for n elements)
3. Wrong base cases
4. Wrong traversal direction (sometimes must go right-to-left)
5. Returning wrong part of dp table

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving with full dp array: can you optimize space to O(1)?
Many 1D DP problems only need the previous 1 or 2 values.
Many 2D DP problems only need the previous row.
```

---

## Problem 232/250 — LeetCode #1143: Longest Common Subsequence

**Pattern:** `2D DP` | **Position in Roadmap:** #232

```
LeetCode 416 — Partition Equal Subset Sum
Problem #232 of 250 | Difficulty: Medium | Pattern: 0/1 Knapsack

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 416: Partition Equal Subset Sum | Medium

For DP problems, ask:
  1. What is the "subproblem"? (What smaller answer helps build the bigger answer?)
  2. What do I need to REMEMBER? (What are my DP state variables?)
  3. How does dp[i] relate to dp[i-1] or dp[i][j] to dp[i-1][j]?

DP Type: 0/1 Knapsack / Subset Sum

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 0/1 Knapsack

Recurrence Relation:
dp[j] |= dp[j-num] for j from target down to num
dp[j] = True if we can make sum j using some subset

Base Cases:
dp[0] = True (empty subset sums to 0)
dp[j] = False for j > 0

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP Thinking Steps:
  1. Define what dp[i] (or dp[i][j]) represents in plain English
  2. Find the recurrence: how does dp[i] depend on previous states?
  3. Set base cases: what are the smallest valid states?
  4. Determine traversal order: which way do you fill the table?
  5. Identify what to return: dp[n]? max(dp)? dp[m][n]?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Example trace:
nums=[1,5,11,5], total=22, target=11

dp=[T,F,F,F,F,F,F,F,F,F,F,F] (size 12)

num=1: j from 11 to 1: dp[1]=dp[0]=T → dp=[T,T,F,...F]
num=5: j from 11 to 5: dp[6]=dp[1]=T, dp[5]=dp[0]=T → dp=[T,T,F,F,F,T,T,F,F,F,F,F]
num=11: dp[11]=dp[0]=T → dp[11]=True → return True ✓

(Process right to left to avoid using same element twice!)

Optimization:
Right-to-left iteration ensures each number used at most once (0/1 knapsack).

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Fill in the DP table for a small example:
  Draw the table with rows and columns labeled.
  Fill left to right, top to bottom (or the appropriate order).
  Check each cell against your recurrence.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP solves problems with OVERLAPPING SUBPROBLEMS and OPTIMAL SUBSTRUCTURE.
The recurrence relation is the heart of DP.
Once you have the recurrence, the code practically writes itself.

Practice: Can you derive the recurrence WITHOUT looking at the solution?
That's the skill you're building!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# 1D DP:
dp = [0] * (n+1)
dp[0] = base_case_0
dp[1] = base_case_1
for i in range(2, n+1):
    dp[i] = f(dp[i-1], dp[i-2])  # your recurrence
return dp[n]

# 2D DP:
dp = [[0]*(n+1) for _ in range(m+1)]
for i in range(m+1): dp[i][0] = ...  # base cases
for j in range(n+1): dp[0][j] = ...  # base cases
for i in range(1,m+1):
    for j in range(1,n+1):
        dp[i][j] = f(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])  # recurrence

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n) for 1D DP, O(m*n) for 2D DP
Space: O(n) or O(m*n), can often optimize to O(1) or O(n) with rolling array

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Wrong recurrence relation (most common mistake!)
2. Off-by-one in dp array size (use dp[n+1] for n elements)
3. Wrong base cases
4. Wrong traversal direction (sometimes must go right-to-left)
5. Returning wrong part of dp table

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving with full dp array: can you optimize space to O(1)?
Many 1D DP problems only need the previous 1 or 2 values.
Many 2D DP problems only need the previous row.
```

---

## Problem 233/250 — LeetCode #72: Edit Distance

**Pattern:** `2D DP` | **Position in Roadmap:** #233

```
LeetCode 377 — Combination Sum IV
Problem #233 of 250 | Difficulty: Medium | Pattern: 1D DP — Order Matters

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 377: Combination Sum IV | Medium

For DP problems, ask:
  1. What is the "subproblem"? (What smaller answer helps build the bigger answer?)
  2. What do I need to REMEMBER? (What are my DP state variables?)
  3. How does dp[i] relate to dp[i-1] or dp[i][j] to dp[i-1][j]?

DP Type: 1D DP — Permutation Count

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 1D DP — Order Matters

Recurrence Relation:
dp[i] += dp[i-num] for each num if num<=i
(Outer loop = amounts, inner loop = nums → ORDER MATTERS → permutations)

Base Cases:
dp[0] = 1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP Thinking Steps:
  1. Define what dp[i] (or dp[i][j]) represents in plain English
  2. Find the recurrence: how does dp[i] depend on previous states?
  3. Set base cases: what are the smallest valid states?
  4. Determine traversal order: which way do you fill the table?
  5. Identify what to return: dp[n]? max(dp)? dp[m][n]?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Example trace:
nums=[1,2,3], target=4

dp[0]=1
dp[1]: num=1→dp[0]=1; total=1
dp[2]: num=1→dp[1]=1, num=2→dp[0]=1; total=2
dp[3]: num=1→dp[2]=2, num=2→dp[1]=1, num=3→dp[0]=1; total=4
dp[4]: num=1→dp[3]=4, num=2→dp[2]=2, num=3→dp[1]=1; total=7

Answer: 7 ✓

Note: (1,2,1) and (2,1,1) etc. are different orderings → permutations!



━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Fill in the DP table for a small example:
  Draw the table with rows and columns labeled.
  Fill left to right, top to bottom (or the appropriate order).
  Check each cell against your recurrence.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP solves problems with OVERLAPPING SUBPROBLEMS and OPTIMAL SUBSTRUCTURE.
The recurrence relation is the heart of DP.
Once you have the recurrence, the code practically writes itself.

Practice: Can you derive the recurrence WITHOUT looking at the solution?
That's the skill you're building!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# 1D DP:
dp = [0] * (n+1)
dp[0] = base_case_0
dp[1] = base_case_1
for i in range(2, n+1):
    dp[i] = f(dp[i-1], dp[i-2])  # your recurrence
return dp[n]

# 2D DP:
dp = [[0]*(n+1) for _ in range(m+1)]
for i in range(m+1): dp[i][0] = ...  # base cases
for j in range(n+1): dp[0][j] = ...  # base cases
for i in range(1,m+1):
    for j in range(1,n+1):
        dp[i][j] = f(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])  # recurrence

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n) for 1D DP, O(m*n) for 2D DP
Space: O(n) or O(m*n), can often optimize to O(1) or O(n) with rolling array

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Wrong recurrence relation (most common mistake!)
2. Off-by-one in dp array size (use dp[n+1] for n elements)
3. Wrong base cases
4. Wrong traversal direction (sometimes must go right-to-left)
5. Returning wrong part of dp table

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving with full dp array: can you optimize space to O(1)?
Many 1D DP problems only need the previous 1 or 2 values.
Many 2D DP problems only need the previous row.
```

---

## Problem 234/250 — LeetCode #62: Unique Paths

**Pattern:** `Grid DP` | **Position in Roadmap:** #234

```
LeetCode 96 — Unique Binary Search Trees
Problem #234 of 250 | Difficulty: Medium | Pattern: DP / Catalan Number

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 96: Unique Binary Search Trees | Medium

For DP problems, ask:
  1. What is the "subproblem"? (What smaller answer helps build the bigger answer?)
  2. What do I need to REMEMBER? (What are my DP state variables?)
  3. How does dp[i] relate to dp[i-1] or dp[i][j] to dp[i-1][j]?

DP Type: 1D DP — Catalan Number

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: DP / Catalan Number

Recurrence Relation:
G(n) = sum of G(i-1)*G(n-i) for i from 1 to n
G(i-1) = left subtree structures, G(n-i) = right subtree structures when root=i

Base Cases:
G(0) = 1 (empty tree)
G(1) = 1 (single node)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP Thinking Steps:
  1. Define what dp[i] (or dp[i][j]) represents in plain English
  2. Find the recurrence: how does dp[i] depend on previous states?
  3. Set base cases: what are the smallest valid states?
  4. Determine traversal order: which way do you fill the table?
  5. Identify what to return: dp[n]? max(dp)? dp[m][n]?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Example trace:
n=3: which numbers can be root? 1, 2, or 3.

Root=1: left=G(0)=1, right=G(2)=2 → 1*2=2 trees
Root=2: left=G(1)=1, right=G(1)=1 → 1*1=1 tree
Root=3: left=G(2)=2, right=G(0)=1 → 2*1=2 trees

G(3) = 2+1+2 = 5 ✓

G(2): Root=1: G(0)*G(1)=1; Root=2: G(1)*G(0)=1 → G(2)=2



━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Fill in the DP table for a small example:
  Draw the table with rows and columns labeled.
  Fill left to right, top to bottom (or the appropriate order).
  Check each cell against your recurrence.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP solves problems with OVERLAPPING SUBPROBLEMS and OPTIMAL SUBSTRUCTURE.
The recurrence relation is the heart of DP.
Once you have the recurrence, the code practically writes itself.

Practice: Can you derive the recurrence WITHOUT looking at the solution?
That's the skill you're building!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# 1D DP:
dp = [0] * (n+1)
dp[0] = base_case_0
dp[1] = base_case_1
for i in range(2, n+1):
    dp[i] = f(dp[i-1], dp[i-2])  # your recurrence
return dp[n]

# 2D DP:
dp = [[0]*(n+1) for _ in range(m+1)]
for i in range(m+1): dp[i][0] = ...  # base cases
for j in range(n+1): dp[0][j] = ...  # base cases
for i in range(1,m+1):
    for j in range(1,n+1):
        dp[i][j] = f(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])  # recurrence

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n) for 1D DP, O(m*n) for 2D DP
Space: O(n) or O(m*n), can often optimize to O(1) or O(n) with rolling array

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Wrong recurrence relation (most common mistake!)
2. Off-by-one in dp array size (use dp[n+1] for n elements)
3. Wrong base cases
4. Wrong traversal direction (sometimes must go right-to-left)
5. Returning wrong part of dp table

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving with full dp array: can you optimize space to O(1)?
Many 1D DP problems only need the previous 1 or 2 values.
Many 2D DP problems only need the previous row.
```

---

## Problem 235/250 — LeetCode #63: Unique Paths II

**Pattern:** `Grid DP` | **Position in Roadmap:** #235

```
LeetCode 91 — Decode Ways
Problem #235 of 250 | Difficulty: Medium | Pattern: 1D DP — Conditional

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 91: Decode Ways | Medium

For DP problems, ask:
  1. What is the "subproblem"? (What smaller answer helps build the bigger answer?)
  2. What do I need to REMEMBER? (What are my DP state variables?)
  3. How does dp[i] relate to dp[i-1] or dp[i][j] to dp[i-1][j]?

DP Type: 1D DP — Conditional Recurrence

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 1D DP — Conditional

Recurrence Relation:
dp[i] = dp[i-1] if s[i-1] != '0'  (single digit decode)
       + dp[i-2] if s[i-2:i] in ['10'..'26']  (two digit decode)

Base Cases:
dp[0] = 1 (empty string has 1 way)
dp[1] = 0 if s[0]=='0' else 1

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP Thinking Steps:
  1. Define what dp[i] (or dp[i][j]) represents in plain English
  2. Find the recurrence: how does dp[i] depend on previous states?
  3. Set base cases: what are the smallest valid states?
  4. Determine traversal order: which way do you fill the table?
  5. Identify what to return: dp[n]? max(dp)? dp[m][n]?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Example trace:
s = "226"

dp[0]=1, dp[1]=1 (s[0]='2'≠'0')

dp[2]: '2'≠'0'→dp[1]=1; s[0:2]='22' in 10-26→dp[0]=1; dp[2]=2
dp[3]: '6'≠'0'→dp[2]=2; s[1:3]='26' in 10-26→dp[1]=1; dp[3]=3

Return dp[3]=3 ✓ (ways: 2-2-6, 22-6, 2-26)



━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Fill in the DP table for a small example:
  Draw the table with rows and columns labeled.
  Fill left to right, top to bottom (or the appropriate order).
  Check each cell against your recurrence.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP solves problems with OVERLAPPING SUBPROBLEMS and OPTIMAL SUBSTRUCTURE.
The recurrence relation is the heart of DP.
Once you have the recurrence, the code practically writes itself.

Practice: Can you derive the recurrence WITHOUT looking at the solution?
That's the skill you're building!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# 1D DP:
dp = [0] * (n+1)
dp[0] = base_case_0
dp[1] = base_case_1
for i in range(2, n+1):
    dp[i] = f(dp[i-1], dp[i-2])  # your recurrence
return dp[n]

# 2D DP:
dp = [[0]*(n+1) for _ in range(m+1)]
for i in range(m+1): dp[i][0] = ...  # base cases
for j in range(n+1): dp[0][j] = ...  # base cases
for i in range(1,m+1):
    for j in range(1,n+1):
        dp[i][j] = f(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])  # recurrence

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n) for 1D DP, O(m*n) for 2D DP
Space: O(n) or O(m*n), can often optimize to O(1) or O(n) with rolling array

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Wrong recurrence relation (most common mistake!)
2. Off-by-one in dp array size (use dp[n+1] for n elements)
3. Wrong base cases
4. Wrong traversal direction (sometimes must go right-to-left)
5. Returning wrong part of dp table

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving with full dp array: can you optimize space to O(1)?
Many 1D DP problems only need the previous 1 or 2 values.
Many 2D DP problems only need the previous row.
```

---

## Problem 236/250 — LeetCode #64: Minimum Path Sum

**Pattern:** `Grid DP` | **Position in Roadmap:** #236

```
LeetCode 139 — Word Break
Problem #236 of 250 | Difficulty: Medium | Pattern: 1D DP — Dictionary Lookup

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 139: Word Break | Medium

For DP problems, ask:
  1. What is the "subproblem"? (What smaller answer helps build the bigger answer?)
  2. What do I need to REMEMBER? (What are my DP state variables?)
  3. How does dp[i] relate to dp[i-1] or dp[i][j] to dp[i-1][j]?

DP Type: 1D DP — Boolean

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 1D DP — Dictionary Lookup

Recurrence Relation:
dp[i] = any(dp[j] and s[j:i] in wordDict) for j in range(i)
dp[i] = True if s[0:i] can be segmented

Base Cases:
dp[0] = True (empty string always segmentable)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP Thinking Steps:
  1. Define what dp[i] (or dp[i][j]) represents in plain English
  2. Find the recurrence: how does dp[i] depend on previous states?
  3. Set base cases: what are the smallest valid states?
  4. Determine traversal order: which way do you fill the table?
  5. Identify what to return: dp[n]? max(dp)? dp[m][n]?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Example trace:
s="leetcode", wordDict=["leet","code"]

dp[0]=True
dp[1]: check s[0:1]="l" → not in dict → dp[1]=False
dp[2]: "le"→no, → False
dp[3]: "lee"→no → False
dp[4]: "leet"→in dict AND dp[0]=True → dp[4]=True!
...
dp[8]: s[4:8]="code"→in dict AND dp[4]=True → dp[8]=True!

Return dp[8]=True ✓



━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Fill in the DP table for a small example:
  Draw the table with rows and columns labeled.
  Fill left to right, top to bottom (or the appropriate order).
  Check each cell against your recurrence.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP solves problems with OVERLAPPING SUBPROBLEMS and OPTIMAL SUBSTRUCTURE.
The recurrence relation is the heart of DP.
Once you have the recurrence, the code practically writes itself.

Practice: Can you derive the recurrence WITHOUT looking at the solution?
That's the skill you're building!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# 1D DP:
dp = [0] * (n+1)
dp[0] = base_case_0
dp[1] = base_case_1
for i in range(2, n+1):
    dp[i] = f(dp[i-1], dp[i-2])  # your recurrence
return dp[n]

# 2D DP:
dp = [[0]*(n+1) for _ in range(m+1)]
for i in range(m+1): dp[i][0] = ...  # base cases
for j in range(n+1): dp[0][j] = ...  # base cases
for i in range(1,m+1):
    for j in range(1,n+1):
        dp[i][j] = f(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])  # recurrence

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n) for 1D DP, O(m*n) for 2D DP
Space: O(n) or O(m*n), can often optimize to O(1) or O(n) with rolling array

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Wrong recurrence relation (most common mistake!)
2. Off-by-one in dp array size (use dp[n+1] for n elements)
3. Wrong base cases
4. Wrong traversal direction (sometimes must go right-to-left)
5. Returning wrong part of dp table

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving with full dp array: can you optimize space to O(1)?
Many 1D DP problems only need the previous 1 or 2 values.
Many 2D DP problems only need the previous row.
```

---

## Problem 237/250 — LeetCode #53: Maximum Subarray

**Pattern:** `Kadane / DP` | **Position in Roadmap:** #237

```
LeetCode 152 — Maximum Product Subarray
Problem #237 of 250 | Difficulty: Medium | Pattern: 1D DP — Track Min and Max

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 152: Maximum Product Subarray | Medium

For DP problems, ask:
  1. What is the "subproblem"? (What smaller answer helps build the bigger answer?)
  2. What do I need to REMEMBER? (What are my DP state variables?)
  3. How does dp[i] relate to dp[i-1] or dp[i][j] to dp[i-1][j]?

DP Type: 1D DP — Track Both Min and Max

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 1D DP — Track Min and Max

Recurrence Relation:
max_here = max(num, num*max_here, num*min_here)
min_here = min(num, num*max_here_prev, num*min_here)
(Negative times negative = positive! So track both min and max.)

Base Cases:
max_here = min_here = nums[0]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP Thinking Steps:
  1. Define what dp[i] (or dp[i][j]) represents in plain English
  2. Find the recurrence: how does dp[i] depend on previous states?
  3. Set base cases: what are the smallest valid states?
  4. Determine traversal order: which way do you fill the table?
  5. Identify what to return: dp[n]? max(dp)? dp[m][n]?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Example trace:
nums = [2,3,-2,4]

start: max_here=2, min_here=2, result=2
num=3: max=max(3,6,6)=6, min=min(3,6,6)=3, result=6
num=-2: max=max(-2,-12,-6)=-2, min=min(-2,-12,-6)=-12, result=6
num=4: max=max(4,-8,-48)=4, min=min(4,-8,-48)=-48, result=6

Return 6 ✓ (subarray [2,3])

Why track min? If we later see -3: min*(-3) = -12*(-3) = 36 (new max!)



━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Fill in the DP table for a small example:
  Draw the table with rows and columns labeled.
  Fill left to right, top to bottom (or the appropriate order).
  Check each cell against your recurrence.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP solves problems with OVERLAPPING SUBPROBLEMS and OPTIMAL SUBSTRUCTURE.
The recurrence relation is the heart of DP.
Once you have the recurrence, the code practically writes itself.

Practice: Can you derive the recurrence WITHOUT looking at the solution?
That's the skill you're building!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# 1D DP:
dp = [0] * (n+1)
dp[0] = base_case_0
dp[1] = base_case_1
for i in range(2, n+1):
    dp[i] = f(dp[i-1], dp[i-2])  # your recurrence
return dp[n]

# 2D DP:
dp = [[0]*(n+1) for _ in range(m+1)]
for i in range(m+1): dp[i][0] = ...  # base cases
for j in range(n+1): dp[0][j] = ...  # base cases
for i in range(1,m+1):
    for j in range(1,n+1):
        dp[i][j] = f(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])  # recurrence

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n) for 1D DP, O(m*n) for 2D DP
Space: O(n) or O(m*n), can often optimize to O(1) or O(n) with rolling array

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Wrong recurrence relation (most common mistake!)
2. Off-by-one in dp array size (use dp[n+1] for n elements)
3. Wrong base cases
4. Wrong traversal direction (sometimes must go right-to-left)
5. Returning wrong part of dp table

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving with full dp array: can you optimize space to O(1)?
Many 1D DP problems only need the previous 1 or 2 values.
Many 2D DP problems only need the previous row.
```

---

## Problem 238/250 — LeetCode #152: Maximum Product Subarray

**Pattern:** `DP` | **Position in Roadmap:** #238

```
LeetCode 62 — Unique Paths
Problem #238 of 250 | Difficulty: Medium | Pattern: 2D DP — Grid Paths

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 62: Unique Paths | Medium

For DP problems, ask:
  1. What is the "subproblem"? (What smaller answer helps build the bigger answer?)
  2. What do I need to REMEMBER? (What are my DP state variables?)
  3. How does dp[i] relate to dp[i-1] or dp[i][j] to dp[i-1][j]?

DP Type: 2D DP — Grid

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 2D DP — Grid Paths

Recurrence Relation:
dp[i][j] = dp[i-1][j] + dp[i][j-1]
(Paths to (i,j) = paths from above + paths from left)

Base Cases:
dp[0][j] = 1 (only one way: go all right)
dp[i][0] = 1 (only one way: go all down)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP Thinking Steps:
  1. Define what dp[i] (or dp[i][j]) represents in plain English
  2. Find the recurrence: how does dp[i] depend on previous states?
  3. Set base cases: what are the smallest valid states?
  4. Determine traversal order: which way do you fill the table?
  5. Identify what to return: dp[n]? max(dp)? dp[m][n]?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Example trace:
3×7 grid

dp initialized with all 1s on first row and column.

dp[1][1] = dp[0][1]+dp[1][0] = 1+1 = 2
dp[1][2] = dp[0][2]+dp[1][1] = 1+2 = 3
...
dp[2][6] = 28 ✓

Same as C(m+n-2, m-1) combinatorially!

Optimization:
Can use 1D dp: dp[j] += dp[j-1] for each row

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Fill in the DP table for a small example:
  Draw the table with rows and columns labeled.
  Fill left to right, top to bottom (or the appropriate order).
  Check each cell against your recurrence.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP solves problems with OVERLAPPING SUBPROBLEMS and OPTIMAL SUBSTRUCTURE.
The recurrence relation is the heart of DP.
Once you have the recurrence, the code practically writes itself.

Practice: Can you derive the recurrence WITHOUT looking at the solution?
That's the skill you're building!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# 1D DP:
dp = [0] * (n+1)
dp[0] = base_case_0
dp[1] = base_case_1
for i in range(2, n+1):
    dp[i] = f(dp[i-1], dp[i-2])  # your recurrence
return dp[n]

# 2D DP:
dp = [[0]*(n+1) for _ in range(m+1)]
for i in range(m+1): dp[i][0] = ...  # base cases
for j in range(n+1): dp[0][j] = ...  # base cases
for i in range(1,m+1):
    for j in range(1,n+1):
        dp[i][j] = f(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])  # recurrence

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n) for 1D DP, O(m*n) for 2D DP
Space: O(n) or O(m*n), can often optimize to O(1) or O(n) with rolling array

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Wrong recurrence relation (most common mistake!)
2. Off-by-one in dp array size (use dp[n+1] for n elements)
3. Wrong base cases
4. Wrong traversal direction (sometimes must go right-to-left)
5. Returning wrong part of dp table

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving with full dp array: can you optimize space to O(1)?
Many 1D DP problems only need the previous 1 or 2 values.
Many 2D DP problems only need the previous row.
```

---

## Problem 239/250 — LeetCode #33: Search in Rotated Sorted Array

**Pattern:** `Binary Search` | **Position in Roadmap:** #239

```
LeetCode 63 — Unique Paths II
Problem #239 of 250 | Difficulty: Medium | Pattern: 2D DP — Grid with Obstacles

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 63: Unique Paths II | Medium

For DP problems, ask:
  1. What is the "subproblem"? (What smaller answer helps build the bigger answer?)
  2. What do I need to REMEMBER? (What are my DP state variables?)
  3. How does dp[i] relate to dp[i-1] or dp[i][j] to dp[i-1][j]?

DP Type: 2D DP — Grid with Obstacles

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 2D DP — Grid with Obstacles

Recurrence Relation:
dp[i][j] = 0 if obstacle at (i,j)
           else dp[i-1][j] + dp[i][j-1]

Base Cases:
dp[0][0] = 1 if no obstacle, else 0
First row/col: 0 if any obstacle blocks the path from start

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP Thinking Steps:
  1. Define what dp[i] (or dp[i][j]) represents in plain English
  2. Find the recurrence: how does dp[i] depend on previous states?
  3. Set base cases: what are the smallest valid states?
  4. Determine traversal order: which way do you fill the table?
  5. Identify what to return: dp[n]? max(dp)? dp[m][n]?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Example trace:
obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]

   0  1  2
0: 1  1  1
1: 1  0  1  (obstacle at (1,1) → 0)
2: 1  1  2

Return dp[2][2] = 2 ✓



━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Fill in the DP table for a small example:
  Draw the table with rows and columns labeled.
  Fill left to right, top to bottom (or the appropriate order).
  Check each cell against your recurrence.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP solves problems with OVERLAPPING SUBPROBLEMS and OPTIMAL SUBSTRUCTURE.
The recurrence relation is the heart of DP.
Once you have the recurrence, the code practically writes itself.

Practice: Can you derive the recurrence WITHOUT looking at the solution?
That's the skill you're building!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# 1D DP:
dp = [0] * (n+1)
dp[0] = base_case_0
dp[1] = base_case_1
for i in range(2, n+1):
    dp[i] = f(dp[i-1], dp[i-2])  # your recurrence
return dp[n]

# 2D DP:
dp = [[0]*(n+1) for _ in range(m+1)]
for i in range(m+1): dp[i][0] = ...  # base cases
for j in range(n+1): dp[0][j] = ...  # base cases
for i in range(1,m+1):
    for j in range(1,n+1):
        dp[i][j] = f(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])  # recurrence

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n) for 1D DP, O(m*n) for 2D DP
Space: O(n) or O(m*n), can often optimize to O(1) or O(n) with rolling array

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Wrong recurrence relation (most common mistake!)
2. Off-by-one in dp array size (use dp[n+1] for n elements)
3. Wrong base cases
4. Wrong traversal direction (sometimes must go right-to-left)
5. Returning wrong part of dp table

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving with full dp array: can you optimize space to O(1)?
Many 1D DP problems only need the previous 1 or 2 values.
Many 2D DP problems only need the previous row.
```

---

## Problem 240/250 — LeetCode #75: Sort Colors

**Pattern:** `Two Pointers` | **Position in Roadmap:** #240

```
LeetCode 64 — Minimum Path Sum
Problem #240 of 250 | Difficulty: Medium | Pattern: 2D DP — Min Cost Grid

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LeetCode 64: Minimum Path Sum | Medium

For DP problems, ask:
  1. What is the "subproblem"? (What smaller answer helps build the bigger answer?)
  2. What do I need to REMEMBER? (What are my DP state variables?)
  3. How does dp[i] relate to dp[i-1] or dp[i][j] to dp[i-1][j]?

DP Type: 2D DP — Minimum Cost

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 2D DP — Min Cost Grid

Recurrence Relation:
dp[i][j] = grid[i][j] + min(dp[i-1][j], dp[i][j-1])

Base Cases:
dp[0][0] = grid[0][0]
First row: cumulative sum
First col: cumulative sum

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP Thinking Steps:
  1. Define what dp[i] (or dp[i][j]) represents in plain English
  2. Find the recurrence: how does dp[i] depend on previous states?
  3. Set base cases: what are the smallest valid states?
  4. Determine traversal order: which way do you fill the table?
  5. Identify what to return: dp[n]? max(dp)? dp[m][n]?

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Example trace:
grid = [[1,3,1],[1,5,1],[4,2,1]]

dp[0] = [1,4,5]  (1, 1+3, 4+1)
dp[1][0]=1+1=2, dp[1][1]=5+min(4,2)=7, dp[1][2]=1+min(5,7)=6
dp[2][0]=4+2=6, dp[2][1]=2+min(7,6)=8, dp[2][2]=1+min(6,8)=7

Return dp[2][2]=7 ✓ (path: 1→3→1→1→1)



━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Fill in the DP table for a small example:
  Draw the table with rows and columns labeled.
  Fill left to right, top to bottom (or the appropriate order).
  Check each cell against your recurrence.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

DP solves problems with OVERLAPPING SUBPROBLEMS and OPTIMAL SUBSTRUCTURE.
The recurrence relation is the heart of DP.
Once you have the recurrence, the code practically writes itself.

Practice: Can you derive the recurrence WITHOUT looking at the solution?
That's the skill you're building!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# 1D DP:
dp = [0] * (n+1)
dp[0] = base_case_0
dp[1] = base_case_1
for i in range(2, n+1):
    dp[i] = f(dp[i-1], dp[i-2])  # your recurrence
return dp[n]

# 2D DP:
dp = [[0]*(n+1) for _ in range(m+1)]
for i in range(m+1): dp[i][0] = ...  # base cases
for j in range(n+1): dp[0][j] = ...  # base cases
for i in range(1,m+1):
    for j in range(1,n+1):
        dp[i][j] = f(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])  # recurrence

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time: O(n) for 1D DP, O(m*n) for 2D DP
Space: O(n) or O(m*n), can often optimize to O(1) or O(n) with rolling array

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Wrong recurrence relation (most common mistake!)
2. Off-by-one in dp array size (use dp[n+1] for n elements)
3. Wrong base cases
4. Wrong traversal direction (sometimes must go right-to-left)
5. Returning wrong part of dp table

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

After solving with full dp array: can you optimize space to O(1)?
Many 1D DP problems only need the previous 1 or 2 values.
Many 2D DP problems only need the previous row.
```

---

## Problem 241/250 — LeetCode #56: Merge Intervals

**Pattern:** `Intervals` | **Position in Roadmap:** #241

```
LeetCode 10 — Regular Expression Matching
Problem #241 of 250 | Difficulty: Hard | Pattern: 2D DP — Pattern Matching

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🎯 FINAL STRETCH — Problem #241 of 250!

LeetCode 10: Regular Expression Matching | Hard

You've come a long way! This is an advanced problem.
Core technique: 2D DP — Pattern Matching

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 2D DP — Pattern Matching

At this level:
  You should recognize the pattern immediately.
  You should know WHICH approach to try first.
  You should be able to identify the edge cases.

Key questions for 2D DP — Pattern Matching:
  - What is the state? (DP: dp[i][j] represents what?)
  - What are the transitions? (How does state i depend on i-1?)
  - What is the base case? (Smallest valid state)
  - What is the answer? (Which state or combination gives the final answer?)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For hard problems:
  1. Start with brute force. What's the obvious but slow approach?
  2. Identify what's being recomputed. Can you memoize it?
  3. What is the "subproblem" structure?
  4. Is there a greedy proof? Or must you try all options (DP)?

Don't rush to code. Spend 10+ minutes planning.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Regular Expression Matching" with pattern 2D DP — Pattern Matching:

Step 1: Define the DP/recursion state clearly.
Step 2: Write the recurrence relation on paper.
Step 3: Handle edge cases.
Step 4: Implement bottom-up (or top-down with memo).
Step 5: Verify with small examples.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Use LeetCode's example 1.
Draw the DP table (for 2D DP problems).
Fill it step by step according to your recurrence.
Verify the final answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

You're solving LeetCode #10 — a Hard problem!

The fact that you've reached problem #241 means:
  ✓ You understand the fundamental patterns
  ✓ You can recognize and apply DSA templates
  ✓ You've built systematic problem-solving skills

Hard problems are just combinations of patterns you already know!
Regular Expression Matching uses 2D DP — Pattern Matching — you've seen this before.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# For 2D DP — Pattern Matching:
# See previous 2D DP — Pattern Matching problems for the template
# Apply the same structure with this problem's specific twist

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Analyze your solution:
  Time: How many states × time per state?
  Space: How much does your dp table or recursion use?

Target for this problem:
  Hard problems often need O(n²) or O(n log n) — the naive O(n³) or O(2^n) is too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not spending enough time on the state definition
2. Wrong transition direction (especially in interval DP)
3. Missing edge cases for empty strings/arrays
4. Integer overflow for counting problems

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🏆 ACHIEVEMENT UNLOCKED: Problem #241/250

Keep going — 9 more problems to master!

After solving: Think about what GENERAL PATTERN this problem exemplifies.
In an interview, you want to be able to identify the pattern in < 2 minutes.
```

---

## Problem 242/250 — LeetCode #49: Group Anagrams

**Pattern:** `Hash Map` | **Position in Roadmap:** #242

```
LeetCode 44 — Wildcard Matching
Problem #242 of 250 | Difficulty: Hard | Pattern: 2D DP — Wildcard

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🎯 FINAL STRETCH — Problem #242 of 250!

LeetCode 44: Wildcard Matching | Hard

You've come a long way! This is an advanced problem.
Core technique: 2D DP — Wildcard

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 2D DP — Wildcard

At this level:
  You should recognize the pattern immediately.
  You should know WHICH approach to try first.
  You should be able to identify the edge cases.

Key questions for 2D DP — Wildcard:
  - What is the state? (DP: dp[i][j] represents what?)
  - What are the transitions? (How does state i depend on i-1?)
  - What is the base case? (Smallest valid state)
  - What is the answer? (Which state or combination gives the final answer?)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For hard problems:
  1. Start with brute force. What's the obvious but slow approach?
  2. Identify what's being recomputed. Can you memoize it?
  3. What is the "subproblem" structure?
  4. Is there a greedy proof? Or must you try all options (DP)?

Don't rush to code. Spend 10+ minutes planning.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Wildcard Matching" with pattern 2D DP — Wildcard:

Step 1: Define the DP/recursion state clearly.
Step 2: Write the recurrence relation on paper.
Step 3: Handle edge cases.
Step 4: Implement bottom-up (or top-down with memo).
Step 5: Verify with small examples.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Use LeetCode's example 1.
Draw the DP table (for 2D DP problems).
Fill it step by step according to your recurrence.
Verify the final answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

You're solving LeetCode #44 — a Hard problem!

The fact that you've reached problem #242 means:
  ✓ You understand the fundamental patterns
  ✓ You can recognize and apply DSA templates
  ✓ You've built systematic problem-solving skills

Hard problems are just combinations of patterns you already know!
Wildcard Matching uses 2D DP — Wildcard — you've seen this before.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# For 2D DP — Wildcard:
# See previous 2D DP — Wildcard problems for the template
# Apply the same structure with this problem's specific twist

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Analyze your solution:
  Time: How many states × time per state?
  Space: How much does your dp table or recursion use?

Target for this problem:
  Hard problems often need O(n²) or O(n log n) — the naive O(n³) or O(2^n) is too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not spending enough time on the state definition
2. Wrong transition direction (especially in interval DP)
3. Missing edge cases for empty strings/arrays
4. Integer overflow for counting problems

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🏆 ACHIEVEMENT UNLOCKED: Problem #242/250

Keep going — 8 more problems to master!

After solving: Think about what GENERAL PATTERN this problem exemplifies.
In an interview, you want to be able to identify the pattern in < 2 minutes.
```

---

## Problem 243/250 — LeetCode #3: Longest Substring Without Repeating Characters

**Pattern:** `Sliding Window` | **Position in Roadmap:** #243

```
LeetCode 115 — Distinct Subsequences
Problem #243 of 250 | Difficulty: Hard | Pattern: 2D DP — Count

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🎯 FINAL STRETCH — Problem #243 of 250!

LeetCode 115: Distinct Subsequences | Hard

You've come a long way! This is an advanced problem.
Core technique: 2D DP — Count

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 2D DP — Count

At this level:
  You should recognize the pattern immediately.
  You should know WHICH approach to try first.
  You should be able to identify the edge cases.

Key questions for 2D DP — Count:
  - What is the state? (DP: dp[i][j] represents what?)
  - What are the transitions? (How does state i depend on i-1?)
  - What is the base case? (Smallest valid state)
  - What is the answer? (Which state or combination gives the final answer?)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For hard problems:
  1. Start with brute force. What's the obvious but slow approach?
  2. Identify what's being recomputed. Can you memoize it?
  3. What is the "subproblem" structure?
  4. Is there a greedy proof? Or must you try all options (DP)?

Don't rush to code. Spend 10+ minutes planning.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Distinct Subsequences" with pattern 2D DP — Count:

Step 1: Define the DP/recursion state clearly.
Step 2: Write the recurrence relation on paper.
Step 3: Handle edge cases.
Step 4: Implement bottom-up (or top-down with memo).
Step 5: Verify with small examples.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Use LeetCode's example 1.
Draw the DP table (for 2D DP problems).
Fill it step by step according to your recurrence.
Verify the final answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

You're solving LeetCode #115 — a Hard problem!

The fact that you've reached problem #243 means:
  ✓ You understand the fundamental patterns
  ✓ You can recognize and apply DSA templates
  ✓ You've built systematic problem-solving skills

Hard problems are just combinations of patterns you already know!
Distinct Subsequences uses 2D DP — Count — you've seen this before.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# For 2D DP — Count:
# See previous 2D DP — Count problems for the template
# Apply the same structure with this problem's specific twist

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Analyze your solution:
  Time: How many states × time per state?
  Space: How much does your dp table or recursion use?

Target for this problem:
  Hard problems often need O(n²) or O(n log n) — the naive O(n³) or O(2^n) is too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not spending enough time on the state definition
2. Wrong transition direction (especially in interval DP)
3. Missing edge cases for empty strings/arrays
4. Integer overflow for counting problems

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🏆 ACHIEVEMENT UNLOCKED: Problem #243/250

Keep going — 7 more problems to master!

After solving: Think about what GENERAL PATTERN this problem exemplifies.
In an interview, you want to be able to identify the pattern in < 2 minutes.
```

---

## Problem 244/250 — LeetCode #98: Validate Binary Search Tree

**Pattern:** `BST` | **Position in Roadmap:** #244

```
LeetCode 312 — Burst Balloons
Problem #244 of 250 | Difficulty: Hard | Pattern: Interval DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🎯 FINAL STRETCH — Problem #244 of 250!

LeetCode 312: Burst Balloons | Hard

You've come a long way! This is an advanced problem.
Core technique: Interval DP

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Interval DP

At this level:
  You should recognize the pattern immediately.
  You should know WHICH approach to try first.
  You should be able to identify the edge cases.

Key questions for Interval DP:
  - What is the state? (DP: dp[i][j] represents what?)
  - What are the transitions? (How does state i depend on i-1?)
  - What is the base case? (Smallest valid state)
  - What is the answer? (Which state or combination gives the final answer?)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For hard problems:
  1. Start with brute force. What's the obvious but slow approach?
  2. Identify what's being recomputed. Can you memoize it?
  3. What is the "subproblem" structure?
  4. Is there a greedy proof? Or must you try all options (DP)?

Don't rush to code. Spend 10+ minutes planning.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Burst Balloons" with pattern Interval DP:

Step 1: Define the DP/recursion state clearly.
Step 2: Write the recurrence relation on paper.
Step 3: Handle edge cases.
Step 4: Implement bottom-up (or top-down with memo).
Step 5: Verify with small examples.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Use LeetCode's example 1.
Draw the DP table (for 2D DP problems).
Fill it step by step according to your recurrence.
Verify the final answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

You're solving LeetCode #312 — a Hard problem!

The fact that you've reached problem #244 means:
  ✓ You understand the fundamental patterns
  ✓ You can recognize and apply DSA templates
  ✓ You've built systematic problem-solving skills

Hard problems are just combinations of patterns you already know!
Burst Balloons uses Interval DP — you've seen this before.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# For Interval DP:
# See previous Interval DP problems for the template
# Apply the same structure with this problem's specific twist

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Analyze your solution:
  Time: How many states × time per state?
  Space: How much does your dp table or recursion use?

Target for this problem:
  Hard problems often need O(n²) or O(n log n) — the naive O(n³) or O(2^n) is too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not spending enough time on the state definition
2. Wrong transition direction (especially in interval DP)
3. Missing edge cases for empty strings/arrays
4. Integer overflow for counting problems

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🏆 ACHIEVEMENT UNLOCKED: Problem #244/250

Keep going — 6 more problems to master!

After solving: Think about what GENERAL PATTERN this problem exemplifies.
In an interview, you want to be able to identify the pattern in < 2 minutes.
```

---

## Problem 245/250 — LeetCode #200: Number of Islands

**Pattern:** `Graph DFS/BFS` | **Position in Roadmap:** #245

```
LeetCode 87 — Scramble String
Problem #245 of 250 | Difficulty: Hard | Pattern: 3D DP / Memoization

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🎯 FINAL STRETCH — Problem #245 of 250!

LeetCode 87: Scramble String | Hard

You've come a long way! This is an advanced problem.
Core technique: 3D DP / Memoization

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: 3D DP / Memoization

At this level:
  You should recognize the pattern immediately.
  You should know WHICH approach to try first.
  You should be able to identify the edge cases.

Key questions for 3D DP / Memoization:
  - What is the state? (DP: dp[i][j] represents what?)
  - What are the transitions? (How does state i depend on i-1?)
  - What is the base case? (Smallest valid state)
  - What is the answer? (Which state or combination gives the final answer?)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For hard problems:
  1. Start with brute force. What's the obvious but slow approach?
  2. Identify what's being recomputed. Can you memoize it?
  3. What is the "subproblem" structure?
  4. Is there a greedy proof? Or must you try all options (DP)?

Don't rush to code. Spend 10+ minutes planning.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Scramble String" with pattern 3D DP / Memoization:

Step 1: Define the DP/recursion state clearly.
Step 2: Write the recurrence relation on paper.
Step 3: Handle edge cases.
Step 4: Implement bottom-up (or top-down with memo).
Step 5: Verify with small examples.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Use LeetCode's example 1.
Draw the DP table (for 2D DP problems).
Fill it step by step according to your recurrence.
Verify the final answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

You're solving LeetCode #87 — a Hard problem!

The fact that you've reached problem #245 means:
  ✓ You understand the fundamental patterns
  ✓ You can recognize and apply DSA templates
  ✓ You've built systematic problem-solving skills

Hard problems are just combinations of patterns you already know!
Scramble String uses 3D DP / Memoization — you've seen this before.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# For 3D DP / Memoization:
# See previous 3D DP  problems for the template
# Apply the same structure with this problem's specific twist

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Analyze your solution:
  Time: How many states × time per state?
  Space: How much does your dp table or recursion use?

Target for this problem:
  Hard problems often need O(n²) or O(n log n) — the naive O(n³) or O(2^n) is too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not spending enough time on the state definition
2. Wrong transition direction (especially in interval DP)
3. Missing edge cases for empty strings/arrays
4. Integer overflow for counting problems

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🏆 ACHIEVEMENT UNLOCKED: Problem #245/250

Keep going — 5 more problems to master!

After solving: Think about what GENERAL PATTERN this problem exemplifies.
In an interview, you want to be able to identify the pattern in < 2 minutes.
```

---

## Problem 246/250 — LeetCode #207: Course Schedule

**Pattern:** `Graph / Topological Sort` | **Position in Roadmap:** #246

```
LeetCode 84 — Largest Rectangle in Histogram
Problem #246 of 250 | Difficulty: Hard | Pattern: Monotonic Stack

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🎯 FINAL STRETCH — Problem #246 of 250!

LeetCode 84: Largest Rectangle in Histogram | Hard

You've come a long way! This is an advanced problem.
Core technique: Monotonic Stack

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Monotonic Stack

At this level:
  You should recognize the pattern immediately.
  You should know WHICH approach to try first.
  You should be able to identify the edge cases.

Key questions for Monotonic Stack:
  - What is the state? (DP: dp[i][j] represents what?)
  - What are the transitions? (How does state i depend on i-1?)
  - What is the base case? (Smallest valid state)
  - What is the answer? (Which state or combination gives the final answer?)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For hard problems:
  1. Start with brute force. What's the obvious but slow approach?
  2. Identify what's being recomputed. Can you memoize it?
  3. What is the "subproblem" structure?
  4. Is there a greedy proof? Or must you try all options (DP)?

Don't rush to code. Spend 10+ minutes planning.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Largest Rectangle in Histogram" with pattern Monotonic Stack:

Step 1: Define the DP/recursion state clearly.
Step 2: Write the recurrence relation on paper.
Step 3: Handle edge cases.
Step 4: Implement bottom-up (or top-down with memo).
Step 5: Verify with small examples.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Use LeetCode's example 1.
Draw the DP table (for 2D DP problems).
Fill it step by step according to your recurrence.
Verify the final answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

You're solving LeetCode #84 — a Hard problem!

The fact that you've reached problem #246 means:
  ✓ You understand the fundamental patterns
  ✓ You can recognize and apply DSA templates
  ✓ You've built systematic problem-solving skills

Hard problems are just combinations of patterns you already know!
Largest Rectangle in Histogram uses Monotonic Stack — you've seen this before.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# For Monotonic Stack:
# See previous Monotonic Stack problems for the template
# Apply the same structure with this problem's specific twist

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Analyze your solution:
  Time: How many states × time per state?
  Space: How much does your dp table or recursion use?

Target for this problem:
  Hard problems often need O(n²) or O(n log n) — the naive O(n³) or O(2^n) is too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not spending enough time on the state definition
2. Wrong transition direction (especially in interval DP)
3. Missing edge cases for empty strings/arrays
4. Integer overflow for counting problems

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🏆 ACHIEVEMENT UNLOCKED: Problem #246/250

Keep going — 4 more problems to master!

After solving: Think about what GENERAL PATTERN this problem exemplifies.
In an interview, you want to be able to identify the pattern in < 2 minutes.
```

---

## Problem 247/250 — LeetCode #215: Kth Largest Element in an Array

**Pattern:** `Heap / Quickselect` | **Position in Roadmap:** #247

```
LeetCode 42 — Trapping Rain Water
Problem #247 of 250 | Difficulty: Hard | Pattern: Two Pointers / Monotonic Stack

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🎯 FINAL STRETCH — Problem #247 of 250!

LeetCode 42: Trapping Rain Water | Hard

You've come a long way! This is an advanced problem.
Core technique: Two Pointers / Monotonic Stack

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Two Pointers / Monotonic Stack

At this level:
  You should recognize the pattern immediately.
  You should know WHICH approach to try first.
  You should be able to identify the edge cases.

Key questions for Two Pointers / Monotonic Stack:
  - What is the state? (DP: dp[i][j] represents what?)
  - What are the transitions? (How does state i depend on i-1?)
  - What is the base case? (Smallest valid state)
  - What is the answer? (Which state or combination gives the final answer?)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For hard problems:
  1. Start with brute force. What's the obvious but slow approach?
  2. Identify what's being recomputed. Can you memoize it?
  3. What is the "subproblem" structure?
  4. Is there a greedy proof? Or must you try all options (DP)?

Don't rush to code. Spend 10+ minutes planning.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Trapping Rain Water" with pattern Two Pointers / Monotonic Stack:

Step 1: Define the DP/recursion state clearly.
Step 2: Write the recurrence relation on paper.
Step 3: Handle edge cases.
Step 4: Implement bottom-up (or top-down with memo).
Step 5: Verify with small examples.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Use LeetCode's example 1.
Draw the DP table (for 2D DP problems).
Fill it step by step according to your recurrence.
Verify the final answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

You're solving LeetCode #42 — a Hard problem!

The fact that you've reached problem #247 means:
  ✓ You understand the fundamental patterns
  ✓ You can recognize and apply DSA templates
  ✓ You've built systematic problem-solving skills

Hard problems are just combinations of patterns you already know!
Trapping Rain Water uses Two Pointers / Monotonic Stack — you've seen this before.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# For Two Pointers / Monotonic Stack:
# See previous Two Pointers  problems for the template
# Apply the same structure with this problem's specific twist

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Analyze your solution:
  Time: How many states × time per state?
  Space: How much does your dp table or recursion use?

Target for this problem:
  Hard problems often need O(n²) or O(n log n) — the naive O(n³) or O(2^n) is too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not spending enough time on the state definition
2. Wrong transition direction (especially in interval DP)
3. Missing edge cases for empty strings/arrays
4. Integer overflow for counting problems

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🏆 ACHIEVEMENT UNLOCKED: Problem #247/250

Keep going — 3 more problems to master!

After solving: Think about what GENERAL PATTERN this problem exemplifies.
In an interview, you want to be able to identify the pattern in < 2 minutes.
```

---

## Problem 248/250 — LeetCode #322: Coin Change

**Pattern:** `DP` | **Position in Roadmap:** #248

```
LeetCode 23 — Merge K Sorted Lists
Problem #248 of 250 | Difficulty: Hard | Pattern: Heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🎯 FINAL STRETCH — Problem #248 of 250!

LeetCode 23: Merge K Sorted Lists | Hard

You've come a long way! This is an advanced problem.
Core technique: Heap

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Heap

At this level:
  You should recognize the pattern immediately.
  You should know WHICH approach to try first.
  You should be able to identify the edge cases.

Key questions for Heap:
  - What is the state? (DP: dp[i][j] represents what?)
  - What are the transitions? (How does state i depend on i-1?)
  - What is the base case? (Smallest valid state)
  - What is the answer? (Which state or combination gives the final answer?)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For hard problems:
  1. Start with brute force. What's the obvious but slow approach?
  2. Identify what's being recomputed. Can you memoize it?
  3. What is the "subproblem" structure?
  4. Is there a greedy proof? Or must you try all options (DP)?

Don't rush to code. Spend 10+ minutes planning.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Merge K Sorted Lists" with pattern Heap:

Step 1: Define the DP/recursion state clearly.
Step 2: Write the recurrence relation on paper.
Step 3: Handle edge cases.
Step 4: Implement bottom-up (or top-down with memo).
Step 5: Verify with small examples.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Use LeetCode's example 1.
Draw the DP table (for 2D DP problems).
Fill it step by step according to your recurrence.
Verify the final answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

You're solving LeetCode #23 — a Hard problem!

The fact that you've reached problem #248 means:
  ✓ You understand the fundamental patterns
  ✓ You can recognize and apply DSA templates
  ✓ You've built systematic problem-solving skills

Hard problems are just combinations of patterns you already know!
Merge K Sorted Lists uses Heap — you've seen this before.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# For Heap:
# See previous Heap problems for the template
# Apply the same structure with this problem's specific twist

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Analyze your solution:
  Time: How many states × time per state?
  Space: How much does your dp table or recursion use?

Target for this problem:
  Hard problems often need O(n²) or O(n log n) — the naive O(n³) or O(2^n) is too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not spending enough time on the state definition
2. Wrong transition direction (especially in interval DP)
3. Missing edge cases for empty strings/arrays
4. Integer overflow for counting problems

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🏆 ACHIEVEMENT UNLOCKED: Problem #248/250

Keep going — 2 more problems to master!

After solving: Think about what GENERAL PATTERN this problem exemplifies.
In an interview, you want to be able to identify the pattern in < 2 minutes.
```

---

## Problem 249/250 — LeetCode #1143: Longest Common Subsequence

**Pattern:** `DP` | **Position in Roadmap:** #249

```
LeetCode 297 — Serialize and Deserialize Binary Tree
Problem #249 of 250 | Difficulty: Hard | Pattern: BFS / DFS

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🎯 FINAL STRETCH — Problem #249 of 250!

LeetCode 297: Serialize and Deserialize Binary Tree | Hard

You've come a long way! This is an advanced problem.
Core technique: BFS / DFS

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: BFS / DFS

At this level:
  You should recognize the pattern immediately.
  You should know WHICH approach to try first.
  You should be able to identify the edge cases.

Key questions for BFS / DFS:
  - What is the state? (DP: dp[i][j] represents what?)
  - What are the transitions? (How does state i depend on i-1?)
  - What is the base case? (Smallest valid state)
  - What is the answer? (Which state or combination gives the final answer?)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For hard problems:
  1. Start with brute force. What's the obvious but slow approach?
  2. Identify what's being recomputed. Can you memoize it?
  3. What is the "subproblem" structure?
  4. Is there a greedy proof? Or must you try all options (DP)?

Don't rush to code. Spend 10+ minutes planning.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Serialize and Deserialize Binary Tree" with pattern BFS / DFS:

Step 1: Define the DP/recursion state clearly.
Step 2: Write the recurrence relation on paper.
Step 3: Handle edge cases.
Step 4: Implement bottom-up (or top-down with memo).
Step 5: Verify with small examples.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Use LeetCode's example 1.
Draw the DP table (for 2D DP problems).
Fill it step by step according to your recurrence.
Verify the final answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

You're solving LeetCode #297 — a Hard problem!

The fact that you've reached problem #249 means:
  ✓ You understand the fundamental patterns
  ✓ You can recognize and apply DSA templates
  ✓ You've built systematic problem-solving skills

Hard problems are just combinations of patterns you already know!
Serialize and Deserialize Binary Tree uses BFS / DFS — you've seen this before.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# For BFS / DFS:
# See previous BFS  problems for the template
# Apply the same structure with this problem's specific twist

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Analyze your solution:
  Time: How many states × time per state?
  Space: How much does your dp table or recursion use?

Target for this problem:
  Hard problems often need O(n²) or O(n log n) — the naive O(n³) or O(2^n) is too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not spending enough time on the state definition
2. Wrong transition direction (especially in interval DP)
3. Missing edge cases for empty strings/arrays
4. Integer overflow for counting problems

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🏆 ACHIEVEMENT UNLOCKED: Problem #249/250

Keep going — 1 more problems to master!

After solving: Think about what GENERAL PATTERN this problem exemplifies.
In an interview, you want to be able to identify the pattern in < 2 minutes.
```

---

## Problem 250/250 — LeetCode #76: Minimum Window Substring

**Pattern:** `Sliding Window` | **Position in Roadmap:** #250

```
LeetCode 4 — Median of Two Sorted Arrays
Problem #250 of 250 | Difficulty: Hard | Pattern: Binary Search

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🎯 FINAL STRETCH — Problem #250 of 250!

LeetCode 4: Median of Two Sorted Arrays | Hard

You've come a long way! This is an advanced problem.
Core technique: Binary Search

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Pattern: Binary Search

At this level:
  You should recognize the pattern immediately.
  You should know WHICH approach to try first.
  You should be able to identify the edge cases.

Key questions for Binary Search:
  - What is the state? (DP: dp[i][j] represents what?)
  - What are the transitions? (How does state i depend on i-1?)
  - What is the base case? (Smallest valid state)
  - What is the answer? (Which state or combination gives the final answer?)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For hard problems:
  1. Start with brute force. What's the obvious but slow approach?
  2. Identify what's being recomputed. Can you memoize it?
  3. What is the "subproblem" structure?
  4. Is there a greedy proof? Or must you try all options (DP)?

Don't rush to code. Spend 10+ minutes planning.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

For "Median of Two Sorted Arrays" with pattern Binary Search:

Step 1: Define the DP/recursion state clearly.
Step 2: Write the recurrence relation on paper.
Step 3: Handle edge cases.
Step 4: Implement bottom-up (or top-down with memo).
Step 5: Verify with small examples.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Use LeetCode's example 1.
Draw the DP table (for 2D DP problems).
Fill it step by step according to your recurrence.
Verify the final answer.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

You're solving LeetCode #4 — a Hard problem!

The fact that you've reached problem #250 means:
  ✓ You understand the fundamental patterns
  ✓ You can recognize and apply DSA templates
  ✓ You've built systematic problem-solving skills

Hard problems are just combinations of patterns you already know!
Median of Two Sorted Arrays uses Binary Search — you've seen this before.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

# For Binary Search:
# See previous Binary Search problems for the template
# Apply the same structure with this problem's specific twist

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Analyze your solution:
  Time: How many states × time per state?
  Space: How much does your dp table or recursion use?

Target for this problem:
  Hard problems often need O(n²) or O(n log n) — the naive O(n³) or O(2^n) is too slow.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not spending enough time on the state definition
2. Wrong transition direction (especially in interval DP)
3. Missing edge cases for empty strings/arrays
4. Integer overflow for counting problems

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🏆 ACHIEVEMENT UNLOCKED: Problem #250/250

You're at the FINAL PROBLEM! 250/250 complete! 🎉

After solving: Think about what GENERAL PATTERN this problem exemplifies.
In an interview, you want to be able to identify the pattern in < 2 minutes.
```

---

