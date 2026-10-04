# 📘 Problem 38 of 250 — LeetCode 138: Copy List with Random Pointer

> **Pattern:** Hash Map | **Roadmap Position:** #38/250

---

LeetCode 138 — Copy List with Random Pointer
Problem #38 of 250 | Difficulty: Medium | Pattern: HashMap — Original to Clone Mapping

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
UNDERSTAND THE PROBLEM
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

A linked list where each node has TWO pointers:
  next → next node
  random → ANY node in the list (or null)

Create a DEEP COPY (completely new nodes, no shared references).

Example:
  Original: [[7,null],[13,0],[11,4],[10,2],[1,0]]
  Each entry is [val, random_index]
  
Return the head of the deep-copied list.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
HOW TO THINK ABOUT IT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Challenge: when creating node B's clone,
node B's random might point to a node we haven't cloned yet!

Solution: Two-pass approach with a HashMap:
  Pass 1: Create all clone nodes, store mapping {original → clone}
  Pass 2: Set next and random pointers for all clones

  HashMap: {node1_orig → node1_clone, node2_orig → node2_clone, ...}

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THINK LIKE A HUMAN FIRST
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Imagine photocopying a document with cross-references:
  First, copy all pages (create all nodes).
  Then, fix all the cross-references to point to the COPIES, not originals.

Pass 1: Create clones (copy values only, no pointers)
  map[original_node] = new_clone_node

Pass 2: Wire up pointers
  clone.next = map[original.next]
  clone.random = map[original.random]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TRANSLATE THINKING INTO CODE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

def copyRandomList(head):
    if not head:
        return None

    # Pass 1: Create all clone nodes
    node_map = {None: None}   # None maps to None (for null random pointers)
    curr = head
    while curr:
        node_map[curr] = Node(curr.val)
        curr = curr.next

    # Pass 2: Wire up next and random pointers
    curr = head
    while curr:
        node_map[curr].next = node_map[curr.next]
        node_map[curr].random = node_map[curr.random]
        curr = curr.next

    return node_map[head]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
DRY RUN — VERY IMPORTANT
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Nodes: A(7)→B(13)→C(11), A.random=None, B.random=A, C.random=C

Pass 1:
  node_map = {None:None, A:A'(7), B:B'(13), C:C'(11)}

Pass 2:
  curr=A: A'.next=node_map[B]=B', A'.random=node_map[None]=None
  curr=B: B'.next=node_map[C]=C', B'.random=node_map[A]=A'
  curr=C: C'.next=node_map[None]=None, C'.random=node_map[C]=C'

Return node_map[A] = A' ✓

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
THE MAIN LESSON
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

When copying structures with forward AND backward references,
use a HashMap as a "translation table":
  old node → new node

This two-pass approach avoids the chicken-and-egg problem
of "I need to set B's random to C's clone, but I haven't cloned C yet."

Pass 1 creates all clones.
Pass 2 wires them up.

Note: {None: None} in the map handles null pointers elegantly!

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR MENTAL TEMPLATE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

node_map = {None: None}
curr = head
while curr: node_map[curr] = Node(curr.val); curr = curr.next
curr = head
while curr:
    node_map[curr].next = node_map[curr.next]
    node_map[curr].random = node_map[curr.random]
    curr = curr.next
return node_map[head]

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMPLEXITY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Time:  O(n) — two passes
Space: O(n) — HashMap stores n node mappings

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
COMMON MISTAKES BEGINNERS MAKE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. Not adding None→None to map (null random pointers crash)
2. Single pass (can't set random if clone doesn't exist yet)
3. Sharing node references (not a deep copy!)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
YOUR CHALLENGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

O(1) space approach: interweave copies into original list!
  Step 1: Insert A' between A and B, B' between B and C...
  Step 2: Fix random pointers using interweaved structure
  Step 3: Separate the two lists

---

*Next up: Problem 39 of 250*
