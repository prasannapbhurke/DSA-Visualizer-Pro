# 📘 Problem 219 of 250 — LeetCode 787: Cheapest Flights Within K Stops

> **Pattern:** Graph / DP | **Roadmap Position:** #219/250

---

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

---

*Next up: Problem 220 of 250*
