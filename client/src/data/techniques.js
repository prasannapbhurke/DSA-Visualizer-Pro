export const categories = [
  { id: 'searching', title: 'Searching', subtitle: 'Linear, binary, jump, and pattern matching' },
  { id: 'sorting', title: 'Sorting', subtitle: 'Comparison and non-comparison ordering techniques' },
  { id: 'stack', title: 'Stack', subtitle: 'LIFO operations and linked-stack flows' },
  { id: 'queue', title: 'Queue', subtitle: 'Linear, circular, linked, priority, and deque flows' },
  { id: 'linked_list', title: 'Linked List', subtitle: 'Traversal, insertion, deletion, and search steps' },
  { id: 'trees', title: 'Trees', subtitle: 'Traversals, BST, heaps, AVL, trie, and Huffman' },
  { id: 'graph', title: 'Graph', subtitle: 'Traversal, shortest path, closure, and ordering' },
  { id: 'foundations', title: 'Foundations', subtitle: 'Recursion and dynamic programming basics' },
  { id: 'advanced', title: 'Advanced', subtitle: 'Union-Find and hash collision handling' },
];

/**
 * Compact source table for the web visualizer catalog.
 *
 * Tuple order:
 * [technique id, category id, display title, difficulty label, short description]
 *
 * Keep this list in sync with the Android technique IDs so shared links and
 * backend progress records can point to the same algorithm across platforms.
 */
const techniqueItems = [
  ['linear_search', 'searching', 'Linear Search', 'Easy', 'Checks each value from left to right.'],
  ['binary_search', 'searching', 'Binary Search', 'Medium', 'Cuts the sorted range in half.'],
  ['jump_search', 'searching', 'Jump Search', 'Medium', 'Skips blocks before scanning locally.'],
  ['pattern_matching', 'searching', 'Pattern Matching', 'Medium', 'Slides a pattern across a larger text.'],
  ['bubble_sort', 'sorting', 'Bubble Sort', 'Easy', 'Swaps neighboring values to bubble up larger items.'],
  ['selection_sort', 'sorting', 'Selection Sort', 'Easy', 'Finds the smallest unsorted value each pass.'],
  ['insertion_sort', 'sorting', 'Insertion Sort', 'Easy', 'Builds the sorted prefix one value at a time.'],
  ['merging', 'sorting', 'Merging', 'Medium', 'Combines two sorted runs into one output.'],
  ['merge_sort', 'sorting', 'Merge Sort', 'Hard', 'Split, sort, and merge recursively.'],
  ['quicksort', 'sorting', 'Quicksort', 'Medium', 'Partitions around a pivot.'],
  ['heapsort', 'sorting', 'Heap Sort', 'Hard', 'Extracts max values from a heap.'],
  ['radix_sort', 'sorting', 'Radix Sort', 'Hard', 'Settles order digit by digit.'],
  ['stack_basics', 'stack', 'Stack Operations', 'Easy', 'Push, pop, and peek using the top pointer.'],
  ['linked_stack_push', 'stack', 'Linked Stack PUSH', 'Easy', 'Push a node onto a linked stack.'],
  ['linked_stack_pop', 'stack', 'Linked Stack POP', 'Easy', 'Remove the top node from a linked stack.'],
  ['queue_basics', 'queue', 'Linear Queue', 'Easy', 'Tracks front and rear for enqueue/dequeue.'],
  ['circular_queue', 'queue', 'Circular Queue', 'Medium', 'Wraps around when rear reaches the end.'],
  ['linked_queue', 'queue', 'Linked Queue', 'Medium', 'Uses linked nodes for queue flow.'],
  ['priority_queue_array', 'queue', 'Priority Queue (Array)', 'Medium', 'Removes the highest-priority front item.'],
  ['priority_queue_2d', 'queue', 'Priority Queue (2D)', 'Hard', 'Separates items by priority bands.'],
  ['deque', 'queue', 'Deque', 'Medium', 'Adds and removes from both ends.'],
  ['singly_linked_list', 'linked_list', 'Singly Linked List', 'Medium', 'Follows next pointers node by node.'],
  ['traversing_linked_list', 'linked_list', 'Traversing a Linked List', 'Easy', 'Visits each node in sequence.'],
  ['search_in_linked_list', 'linked_list', 'Search in Linked List', 'Easy', 'Looks for the first matching node.'],
  ['insert_at_beginning_linked_list', 'linked_list', 'Insert at Beginning', 'Easy', 'Links a new node before the head.'],
  ['insert_into_sorted_linked_list', 'linked_list', 'Insert Into Sorted Linked List', 'Medium', 'Finds the correct sorted gap.'],
  ['delete_node_by_item_linked_list', 'linked_list', 'Delete by Item', 'Medium', 'Finds and removes the first matching node.'],
  ['tree_traversal', 'trees', 'Tree Traversal', 'Medium', 'Follows an order through the tree.'],
  ['preorder_traversal', 'trees', 'Preorder Traversal', 'Medium', 'Visits root, left, then right.'],
  ['postorder_traversal', 'trees', 'Postorder Traversal', 'Medium', 'Finishes both subtrees before the root.'],
  ['bst_search_parent_tracking', 'trees', 'BST Search with Parent Tracking', 'Medium', 'Searches while remembering the parent.'],
  ['bst_insertion', 'trees', 'BST Insertion', 'Medium', 'Walks to the correct insert position.'],
  ['bst_deletion_del', 'trees', 'BST Deletion', 'Hard', 'Finds the node and resolves the right case.'],
  ['max_heap_insertion', 'trees', 'Max Heap Insertion', 'Medium', 'Percolates a new value upward.'],
  ['max_heap_deletion', 'trees', 'Max Heap Deletion', 'Medium', 'Moves the root out and restores heap order.'],
  ['avl_tree', 'trees', 'AVL Tree', 'Hard', 'Rotates to stay balanced.'],
  ['trie', 'trees', 'Trie', 'Hard', 'Follows characters through prefix nodes.'],
  ['huffman_algorithm', 'trees', 'Huffman Algorithm', 'Hard', 'Merges the two lightest weights repeatedly.'],
  ['graph_traversal', 'graph', 'Graph Traversal', 'Medium', 'Visits nodes with BFS or DFS rules.'],
  ['breadth_first_search', 'graph', 'Breadth-First Search', 'Medium', 'Expands level by level from the frontier.'],
  ['depth_first_search', 'graph', 'Depth-First Search', 'Medium', 'Goes deep before backtracking.'],
  ['dijkstra_algorithm', 'graph', 'Dijkstra Algorithm', 'Hard', 'Settles the shortest-known node first.'],
  ['warshall_algorithm', 'graph', 'Warshall Algorithm', 'Hard', 'Builds transitive closure through intermediates.'],
  ['floyd_algorithm', 'graph', 'Floyd Algorithm', 'Hard', 'Relaxes all-pairs shortest paths.'],
  ['topological_sort', 'graph', 'Topological Sort', 'Hard', 'Orders a DAG so edges go forward.'],
  ['spanning_tree', 'graph', 'Spanning Tree', 'Hard', 'Builds a cycle-free connected structure.'],
  ['recursion_basics', 'foundations', 'Recursion Basics', 'Easy', 'Shrinks the problem toward a base case.'],
  ['dynamic_programming_basics', 'foundations', 'Dynamic Programming Basics', 'Medium', 'Reuses solved subproblems.'],
  ['union_find', 'advanced', 'Union-Find', 'Medium', 'Follows parents to the representative root.'],
  ['hash_collision_handling', 'advanced', 'Hash Collision Handling', 'Medium', 'Finds the next valid slot or chain node.'],
];

export const techniques = techniqueItems.map(([id, categoryId, title, difficulty, description]) => ({
  id,
  categoryId,
  title,
  difficulty,
  description,
}));

const sampleValuesByCategory = {
  searching: [12, 7, 19, 4, 25, 9],
  sorting: [29, 10, 14, 37, 13, 8],
  stack: [18, 24, 31, 42],
  queue: [11, 22, 33, 44],
  linked_list: [9, 14, 21, 28, 34],
  trees: [40, 20, 60, 10, 30, 50, 70],
  graph: [0, 1, 2, 3, 4],
  foundations: [5, 3, 2, 1],
  advanced: [0, 1, 2, 3, 4],
};

const sampleTargetByTechnique = {
  linear_search: 25,
  binary_search: 19,
  jump_search: 25,
  pattern_matching: 'ABA',
  bst_search_parent_tracking: 50,
  bst_insertion: 55,
  bst_deletion_del: 30,
};

function findTechnique(id) {
  return techniques.find((item) => item.id === id) || null;
}

function resolveTechnique(id) {
  return techniques.find((item) => item.id === id) || techniques[0];
}

function getSampleValues(technique) {
  return sampleValuesByCategory[technique.categoryId] || [5, 8, 12, 15];
}

function getSampleTarget(technique) {
  return sampleTargetByTechnique[technique.id] ?? getSampleValues(technique)[Math.min(2, getSampleValues(technique).length - 1)];
}

/**
 * Builds one timeline card/state for a visualizer.
 *
 * `nodes` and `edges` are optional graph/tree metadata. The React client draws
 * them on Canvas, while linear/array techniques use `values` and index fields.
 */
function createStep(label, description, values, options = {}) {
  return {
    label,
    description,
    values,
    focusIndex: options.focusIndex ?? -1,
    secondaryIndex: options.secondaryIndex ?? -1,
    doneIndices: options.doneIndices ?? [],
    badges: options.badges ?? [],
    nodes: options.nodes ?? null,
    edges: options.edges ?? null,
    note: options.note ?? '',
  };
}

function buildSearchSteps(technique) {
  const values = getSampleValues(technique);
  const target = getSampleTarget(technique);
  if (technique.id === 'binary_search') {
    const sorted = [...values].sort((left, right) => left - right);
    const mid = Math.floor((sorted.length - 1) / 2);
    return [
      createStep('Starting', `Mark the full sorted range before the search begins for target ${target}.`, sorted, {
        badges: [`low: 0`, `high: ${sorted.length - 1}`],
      }),
      createStep('Inspect Mid', `Check the middle value ${sorted[mid]} at index ${mid}.`, sorted, {
        focusIndex: mid,
        badges: [`low: 0`, `mid: ${mid}`, `high: ${sorted.length - 1}`],
      }),
      createStep('Move Right', `Because ${target} is larger than ${sorted[mid]}, keep the right half only.`, sorted, {
        focusIndex: mid + 1,
        badges: [`low: ${mid + 1}`, `high: ${sorted.length - 1}`],
      }),
      createStep('Found', `The remaining middle value matches target ${target}.`, sorted, {
        focusIndex: sorted.indexOf(target),
        doneIndices: [sorted.indexOf(target)],
      }),
    ];
  }
  if (technique.id === 'jump_search') {
    return [
      createStep('Jump Block', `Jump to the end of the first block while looking for target ${target}.`, values, {
        focusIndex: 1,
        badges: ['block: 2'],
      }),
      createStep('Jump Again', `The target is still ahead, so move to the next block boundary.`, values, {
        focusIndex: 3,
        badges: ['block: 4'],
      }),
      createStep('Linear Scan', `Now scan inside the promising block one value at a time.`, values, {
        focusIndex: 4,
      }),
      createStep('Found', `Target ${target} is found inside the scanned block.`, values, {
        focusIndex: values.indexOf(target),
        doneIndices: [values.indexOf(target)],
      }),
    ];
  }
  if (technique.id === 'pattern_matching') {
    return [
      createStep('Align Pattern', 'Place the pattern under the start of the text.', [0, 1, 2, 3, 4, 5], {
        badges: ['pattern: ABA'],
      }),
      createStep('Compare Window', 'Compare each aligned character in the current window.', [0, 1, 2, 3, 4, 5], {
        focusIndex: 1,
      }),
      createStep('Shift Window', 'A mismatch means the pattern shifts one position to the right.', [0, 1, 2, 3, 4, 5], {
        focusIndex: 2,
      }),
      createStep('Match Found', 'All characters match in this window, so the pattern is found.', [0, 1, 2, 3, 4, 5], {
        doneIndices: [2, 3, 4],
      }),
    ];
  }
  return values.map((value, index) =>
    createStep(
      value === target ? 'Found' : 'Checking Value',
      value === target
        ? `Linear Search reaches index ${index} and finds target ${target}.`
        : `Compare target ${target} with value ${value} at index ${index}.`,
      values,
      {
        focusIndex: index,
        doneIndices: value === target ? [index] : [],
      }
    )
  );
}

function buildSortSteps(technique) {
  const values = getSampleValues(technique);
  if (technique.id === 'selection_sort') {
    return [
      createStep('Scan Minimum', 'Start from the first unsorted position and scan for the smallest remaining value.', values, {
        focusIndex: 0,
        secondaryIndex: 1,
      }),
      createStep('New Minimum', 'A smaller value is found, so the current minimum pointer moves.', values, {
        focusIndex: 5,
        secondaryIndex: 0,
      }),
      createStep('Placing Minimum', 'Swap the minimum into the next sorted position.', [8, 10, 14, 37, 13, 29], {
        focusIndex: 0,
        doneIndices: [0],
      }),
      createStep('Grow Sorted Prefix', 'Repeat the same scan for the next unsorted position.', [8, 10, 13, 37, 14, 29], {
        focusIndex: 2,
        doneIndices: [0, 1, 2],
      }),
    ];
  }
  if (technique.id === 'bubble_sort') {
    return [
      createStep('Comparing', 'Compare neighboring values and keep the larger one moving right.', values, {
        focusIndex: 0,
        secondaryIndex: 1,
      }),
      createStep('Swapping', 'These two neighbors are out of order, so swap them.', [10, 29, 14, 37, 13, 8], {
        focusIndex: 0,
        secondaryIndex: 1,
      }),
      createStep('Pass Complete', 'One full pass places the largest remaining item at the end.', [10, 14, 29, 13, 8, 37], {
        doneIndices: [5],
      }),
      createStep('Sorted', 'After repeated passes, every value settles in ascending order.', [8, 10, 13, 14, 29, 37], {
        doneIndices: [0, 1, 2, 3, 4, 5],
      }),
    ];
  }
  if (technique.id === 'insertion_sort') {
    return [
      createStep('Pick Key', 'Take the next unsorted value as the key to insert.', values, {
        focusIndex: 2,
      }),
      createStep('Shift Left', 'Shift larger sorted values rightward to make room for the key.', [10, 29, 14, 37, 13, 8], {
        focusIndex: 1,
        secondaryIndex: 2,
      }),
      createStep('Insert Key', 'Place the key into the gap that keeps the prefix sorted.', [10, 14, 29, 37, 13, 8], {
        focusIndex: 1,
        doneIndices: [0, 1, 2],
      }),
      createStep('Sorted Prefix Grows', 'Repeat with the next key until the whole array is sorted.', [8, 10, 13, 14, 29, 37], {
        doneIndices: [0, 1, 2, 3, 4, 5],
      }),
    ];
  }
  if (technique.id === 'heapsort') {
    return [
      createStep('Heap Ready', 'Build a max heap so the largest value is at the root.', [37, 29, 14, 10, 13, 8], {
        focusIndex: 0,
      }),
      createStep('Extract Max', 'Swap the root with the last unsorted index.', [8, 29, 14, 10, 13, 37], {
        focusIndex: 5,
        doneIndices: [5],
      }),
      createStep('Heapifying', 'Percolate the new root down until heap order is restored.', [29, 13, 14, 10, 8, 37], {
        focusIndex: 0,
      }),
      createStep('Sorted', 'Repeat extraction until the sorted suffix covers the whole array.', [8, 10, 13, 14, 29, 37], {
        doneIndices: [0, 1, 2, 3, 4, 5],
      }),
    ];
  }
  if (technique.id === 'merge_sort' || technique.id === 'merging') {
    return [
      createStep('Split Runs', 'Treat smaller runs as sorted building blocks.', values, {
        badges: ['left run', 'right run'],
      }),
      createStep('Compare Fronts', 'Compare the first remaining values of both runs.', values, {
        focusIndex: 0,
        secondaryIndex: 3,
      }),
      createStep('Write Smaller Value', 'Copy the smaller front value into the output run.', [10, 14, 29, 8, 13, 37], {
        doneIndices: [0, 1, 2],
      }),
      createStep('Merged Output', 'Continue until the final merged run is fully sorted.', [8, 10, 13, 14, 29, 37], {
        doneIndices: [0, 1, 2, 3, 4, 5],
      }),
    ];
  }
  if (technique.id === 'radix_sort') {
    return [
      createStep('Ones Place', 'Group values by the ones digit.', values, {
        badges: ['digit: ones'],
      }),
      createStep('Tens Place', 'Stable grouping by the tens digit refines the order.', [8, 10, 13, 14, 29, 37], {
        badges: ['digit: tens'],
      }),
      createStep('Sorted', 'After the last digit pass, the final order is settled.', [8, 10, 13, 14, 29, 37], {
        doneIndices: [0, 1, 2, 3, 4, 5],
      }),
    ];
  }
  return [
    createStep('Partition', 'Read the pivot boundary and separate smaller and larger values.', values, {
      focusIndex: 2,
    }),
    createStep('Left Partition', 'The left side is recursively settled around the pivot.', [10, 13, 14, 8, 29, 37], {
      focusIndex: 1,
    }),
    createStep('Right Partition', 'The right side is recursively settled next.', [8, 10, 13, 14, 29, 37], {
      focusIndex: 4,
    }),
    createStep('Sorted', 'All partitions are resolved, so the whole array is sorted.', [8, 10, 13, 14, 29, 37], {
      doneIndices: [0, 1, 2, 3, 4, 5],
    }),
  ];
}

function buildStructureSteps(technique) {
  const values = getSampleValues(technique);
  if (technique.categoryId === 'linked_list') {
    return [
      createStep('Visit Head', 'Start from the head node and inspect the current item.', values, {
        focusIndex: 0,
      }),
      createStep('Follow Link', 'Move along the next pointer to continue the traversal.', values, {
        focusIndex: 1,
      }),
      createStep('Operate Here', `${technique.title} reaches the key node where the main action happens.`, values, {
        focusIndex: 2,
        secondaryIndex: 1,
      }),
      createStep('Links Updated', 'The list pointers now reflect the completed operation.', values, {
        focusIndex: 3,
      }),
    ];
  }
  if (technique.id === 'deque') {
    return [
      createStep('Front In', 'Insert a new value at the front end.', [55, ...values], {
        focusIndex: 0,
        badges: ['front', 'rear'],
      }),
      createStep('Rear In', 'Insert another value at the rear end.', [55, ...values, 66], {
        focusIndex: values.length + 1,
        badges: ['front', 'rear'],
      }),
      createStep('Front Out', 'Remove the current front value.', values, {
        focusIndex: 0,
      }),
      createStep('Rear Out', 'Remove the rear value while keeping the middle intact.', values.slice(0, -1), {
        focusIndex: values.length - 2,
      }),
    ];
  }
  if (technique.categoryId === 'queue') {
    return [
      createStep('Front / Rear', 'Mark the front and rear positions before updating the queue.', values, {
        badges: ['front: 0', `rear: ${values.length - 1}`],
      }),
      createStep('Enqueue', 'Add a new value at the rear of the queue.', [...values, 55], {
        focusIndex: values.length,
      }),
      createStep('Dequeue', 'Remove the front value and shift the live window forward.', values.slice(1), {
        focusIndex: 0,
      }),
      createStep('Peek', 'Inspect the current front value without removing it.', values.slice(1), {
        focusIndex: 0,
      }),
    ];
  }
  return [
    createStep('Push', 'Add a new value to the top of the stack.', [...values, 55], {
      focusIndex: values.length,
      badges: ['top'],
    }),
    createStep('Peek', 'Inspect the top value before the next change.', [...values, 55], {
      focusIndex: values.length,
      badges: ['top'],
    }),
    createStep('Pop', 'Remove the top value and reveal the next item underneath.', values, {
      focusIndex: values.length - 1,
      badges: ['top'],
    }),
  ];
}

function buildTreeSteps(technique) {
  const nodes = [
    { id: 40, x: 50, y: 10 },
    { id: 20, x: 25, y: 36 },
    { id: 60, x: 75, y: 36 },
    { id: 10, x: 14, y: 64 },
    { id: 30, x: 36, y: 64 },
    { id: 50, x: 64, y: 64 },
    { id: 70, x: 86, y: 64 },
  ];
  const edges = [
    [40, 20], [40, 60], [20, 10], [20, 30], [60, 50], [60, 70],
  ];
  if (technique.id === 'avl_tree') {
    return [
      createStep('Imbalance Detected', 'The tree becomes unbalanced after insertion.', [], {
        nodes,
        edges,
        note: 'balance factor: +2',
      }),
      createStep('Rotate', 'Perform a rotation to restore AVL balance.', [], {
        nodes,
        edges,
        note: 'right rotation',
      }),
      createStep('Balanced', 'The tree height is balanced again.', [], {
        nodes,
        edges,
        note: 'balance restored',
      }),
    ];
  }
  if (technique.id === 'trie') {
    return [
      createStep('Root', 'Start from the trie root.', [], {
        nodes: [
          { id: 'root', x: 50, y: 12 },
          { id: 'c', x: 50, y: 36 },
          { id: 'a', x: 50, y: 60 },
          { id: 't', x: 50, y: 84 },
        ],
        edges: [['root', 'c'], ['c', 'a'], ['a', 't']],
      }),
      createStep('Character Path', 'Follow one character edge at a time.', [], {
        nodes: [
          { id: 'root', x: 50, y: 12 },
          { id: 'c', x: 50, y: 36 },
          { id: 'a', x: 50, y: 60 },
          { id: 't', x: 50, y: 84 },
        ],
        edges: [['root', 'c'], ['c', 'a'], ['a', 't']],
        note: 'word: cat',
      }),
    ];
  }
  return [
    createStep('Visit Root', `Start ${technique.title} from the root node.`, [], {
      nodes,
      edges,
      note: 'root: 40',
    }),
    createStep('Follow Left Branch', 'Move through the next branch according to the traversal or BST rule.', [], {
      nodes,
      edges,
      note: 'current: 20',
    }),
    createStep('Resolve Target Node', 'The target node or operation point is now highlighted.', [], {
      nodes,
      edges,
      note: technique.id.includes('bst') ? 'target region reached' : 'visit complete',
    }),
  ];
}

function buildGraphSteps(technique) {
  const nodes = [
    { id: 0, x: 18, y: 30 },
    { id: 1, x: 50, y: 16 },
    { id: 2, x: 82, y: 30 },
    { id: 3, x: 32, y: 70 },
    { id: 4, x: 68, y: 70 },
  ];
  const edges = [
    [0, 1], [1, 2], [0, 3], [1, 3], [1, 4], [2, 4], [3, 4],
  ];
  if (technique.id === 'dijkstra_algorithm') {
    return [
      createStep('Pick Source', 'Initialize the source node with distance 0.', [], {
        nodes,
        edges,
        badges: ['dist: 0, inf, inf, inf, inf'],
      }),
      createStep('Relax Edges', 'Check whether outgoing edges improve neighbor distances.', [], {
        nodes,
        edges,
        note: 'relax 0 -> 1 and 0 -> 3',
      }),
      createStep('Settle Node', 'Mark the smallest unsettled node as final.', [], {
        nodes,
        edges,
        note: 'settled: 0, 1',
      }),
    ];
  }
  if (technique.id === 'warshall_algorithm' || technique.id === 'floyd_algorithm') {
    return [
      createStep('Intermediate Vertex', 'Choose the current intermediate vertex k.', [0, 1, 2, 3], {
        focusIndex: 1,
        badges: ['k = 1'],
      }),
      createStep('Check Pair', 'Evaluate the highlighted row/column pair through k.', [0, 1, 2, 3], {
        focusIndex: 2,
        secondaryIndex: 3,
      }),
      createStep('Update Matrix', 'Apply the improved reachability or shorter distance.', [0, 1, 2, 3], {
        doneIndices: [0, 1, 2],
      }),
    ];
  }
  return [
    createStep('Visit Start Node', `Start ${technique.title} from the chosen source node.`, [], {
      nodes,
      edges,
      note: 'source: 0',
    }),
    createStep('Expand Frontier', 'Move to the next reachable node according to the traversal rule.', [], {
      nodes,
      edges,
      note: technique.id === 'depth_first_search' ? 'stack-driven' : 'queue-driven',
    }),
    createStep('Traversal Grows', 'The visited region expands while edges show the active path.', [], {
      nodes,
      edges,
      note: 'visited: 0, 1, 3',
    }),
  ];
}

function buildFoundationSteps(technique) {
  if (technique.id === 'dynamic_programming_basics') {
    return [
      createStep('Base Cases', 'Fill the simplest states first so later states can reuse them.', [1, 1, 0, 0], {
        doneIndices: [0, 1],
      }),
      createStep('Reuse Earlier State', 'Compute the next state from already solved results.', [1, 1, 2, 0], {
        focusIndex: 2,
      }),
      createStep('Table Ready', 'The table now stores results for fast lookup.', [1, 1, 2, 3], {
        doneIndices: [0, 1, 2, 3],
      }),
    ];
  }
  return [
    createStep('Call Problem', 'Start with the original problem size.', [4, 3, 2, 1], {
      focusIndex: 0,
    }),
    createStep('Smaller Subproblem', 'Reduce the problem toward the base case.', [3, 2, 1], {
      focusIndex: 1,
    }),
    createStep('Base Case', 'Reach the stopping condition and return upward.', [1], {
      doneIndices: [0],
    }),
  ];
}

function buildAdvancedSteps(technique) {
  if (technique.id === 'union_find') {
    return [
      createStep('Follow Parent', 'Move from the current node to its parent.', [0, 1, 2, 3, 4], {
        badges: ['parents: 0,0,1,3,3'],
        focusIndex: 2,
      }),
      createStep('Reach Root', 'Continue until the representative root is found.', [0, 1, 2, 3, 4], {
        focusIndex: 1,
      }),
      createStep('Compress Path', 'Shorten the path so later lookups are faster.', [0, 1, 2, 3, 4], {
        doneIndices: [2],
      }),
    ];
  }
  return [
    createStep('Hash Slot', 'Compute the primary hash slot for the new key.', [12, 19, 0, 0, 0], {
      focusIndex: 1,
    }),
    createStep('Collision', 'The slot is occupied, so follow the collision rule.', [12, 19, 0, 0, 0], {
      focusIndex: 2,
    }),
    createStep('Insert', 'Place the key in the first valid location found.', [12, 19, 26, 0, 0], {
      doneIndices: [2],
    }),
  ];
}

export function buildTechniqueSteps(techniqueId) {
  const technique = resolveTechnique(techniqueId);
  if (!technique) {
    return [];
  }
  switch (technique.categoryId) {
    case 'searching':
      return buildSearchSteps(technique);
    case 'sorting':
      return buildSortSteps(technique);
    case 'stack':
    case 'queue':
    case 'linked_list':
      return buildStructureSteps(technique);
    case 'trees':
      return buildTreeSteps(technique);
    case 'graph':
      return buildGraphSteps(technique);
    case 'foundations':
      return buildFoundationSteps(technique);
    case 'advanced':
      return buildAdvancedSteps(technique);
    default:
      return [];
  }
}

export function getTechniqueById(techniqueId) {
  return findTechnique(techniqueId);
}

export function getTechniquesForCategory(categoryId) {
  return techniques.filter((item) => item.categoryId === categoryId);
}
