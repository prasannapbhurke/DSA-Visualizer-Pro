package com.dsavisualizer.app.data.snippets;

public final class PythonSnippetProvider {

    private PythonSnippetProvider() {
    }

    public static String getPythonCodeSnippet(String techniqueId, String style) {
        if ("selection_sort".equals(techniqueId)) {
            return getPythonSelectionSortPdf(style);
        }
        if ("insertion_sort".equals(techniqueId)) {
            return getPythonInsertionSortPdf(style);
        }
        if ("merging".equals(techniqueId)) {
            return getPythonMergingPdf(style);
        }
        if ("merge_sort".equals(techniqueId)) {
            return getPythonMergeSortPdf(style);
        }
        if ("radix_sort".equals(techniqueId)) {
            return getPythonRadixSortPdf(style);
        }
        if ("find_linked_list".equals(techniqueId)) {
            return getPythonSearchInLinkedList(style);
        }
        if ("delete_linked_list".equals(techniqueId)) {
            return getPythonDeleteNodeByItemLinkedList(style);
        }
        switch (techniqueId) {
            case "linear_search":
                if ("Functions".equals(style)) {
                    return "def linear(arr, n, item):\n"
                        + "    loc = 1\n"
                        + "    arr[n + 1] = item\n"
                        + "    while arr[loc] != item:\n"
                        + "        loc += 1\n\n"
                        + "    if loc == n + 1:\n"
                        + "        return 0\n"
                        + "    else:\n"
                        + "        return loc\n\n"
                        + "n = int(input())\n"
                        + "arr = [0] + list(map(int, input().split())) + [0]\n"
                        + "item = int(input())\n"
                        + "loc = linear(arr, n, item)\n"
                        + "print(\"Location:\", loc)";
                }
                if ("Recursion".equals(style)) {
                    return "def linear(arr, n, item, loc):\n"
                        + "    if arr[loc] == item:\n"
                        + "        if loc == n + 1:\n"
                        + "            return 0\n"
                        + "        else:\n"
                        + "            return loc\n"
                        + "    return linear(arr, n, item, loc + 1)\n\n"
                        + "n = int(input())\n"
                        + "arr = [0] + list(map(int, input().split())) + [0]\n"
                        + "item = int(input())\n"
                        + "arr[n + 1] = item\n"
                        + "loc = linear(arr, n, item, 1)\n"
                        + "print(\"Location:\", loc)";
                }
                return "n = int(input())\n"
                    + "arr = [0] + list(map(int, input().split())) + [0]\n"
                    + "item = int(input())\n\n"
                    + "arr[n + 1] = item\n"
                    + "loc = 1\n"
                    + "while arr[loc] != item:\n"
                    + "    loc += 1\n\n"
                    + "if loc == n + 1:\n"
                    + "    loc = 0\n\n"
                    + "print(\"Location:\", loc)";
            case "binary_search":
                if ("Functions".equals(style)) {
                    return "def binary(arr, lb, ub, item):\n"
                        + "    beg = lb\n"
                        + "    end = ub\n"
                        + "    while beg <= end:\n"
                        + "        mid = (beg + end) // 2\n\n"
                        + "        if arr[mid] == item:\n"
                        + "            return mid\n"
                        + "        elif item < arr[mid]:\n"
                        + "            end = mid - 1\n"
                        + "        else:\n"
                        + "            beg = mid + 1\n\n"
                        + "    return -1\n\n"
                        + "n = int(input())\n"
                        + "arr = list(map(int, input().split()))\n"
                        + "item = int(input())\n"
                        + "loc = binary(arr, 0, n - 1, item)\n"
                        + "print(\"Location:\", loc)";
                }
                if ("Recursion".equals(style)) {
                    return "def binary(arr, beg, end, item):\n"
                        + "    if beg > end:\n"
                        + "        return -1\n"
                        + "    mid = (beg + end) // 2\n\n"
                        + "    if arr[mid] == item:\n"
                        + "        return mid\n"
                        + "    elif item < arr[mid]:\n"
                        + "        return binary(arr, beg, mid - 1, item)\n"
                        + "    else:\n"
                        + "        return binary(arr, mid + 1, end, item)\n\n"
                        + "n = int(input())\n"
                        + "arr = list(map(int, input().split()))\n"
                        + "item = int(input())\n"
                        + "loc = binary(arr, 0, n - 1, item)\n"
                        + "print(\"Location:\", loc)";
                }
                return "n = int(input())\n"
                    + "arr = list(map(int, input().split()))\n"
                    + "item = int(input())\n\n"
                    + "beg = 0\n"
                    + "end = n - 1\n"
                    + "mid = (beg + end) // 2\n"
                    + "while beg <= end and arr[mid] != item:\n"
                    + "    if item < arr[mid]:\n"
                    + "        end = mid - 1\n"
                    + "    else:\n"
                    + "        beg = mid + 1\n"
                    + "    mid = (beg + end) // 2\n"
                    + "if beg <= end and arr[mid] == item:\n"
                    + "    print(\"Location:\", mid)\n"
                    + "else:\n"
                    + "    print(\"Location: -1\")";
            case "jump_search":
                if ("Functions".equals(style)) {
                    return "import math\n\n"
                        + "def get_block_size(length):\n"
                        + "    return int(math.sqrt(length))\n\n"
                        + "def scan_block(arr, target, start, end):\n"
                        + "    for index in range(start, end):\n"
                        + "        if arr[index] == target:\n"
                        + "            return index\n"
                        + "    return -1\n\n"
                        + "def jump_search(arr, target):\n"
                        + "    block_size = get_block_size(len(arr))\n"
                        + "    start = 0\n"
                        + "    while start < len(arr) and arr[min(start + block_size, len(arr)) - 1] < target:\n"
                        + "        start += block_size\n"
                        + "    return scan_block(arr, target, start, min(start + block_size, len(arr)))";
                }
                if ("Recursion".equals(style)) {
                    return "import math\n\n"
                        + "def recursive_scan(arr, target, index, end):\n"
                        + "    if index > end:\n"
                        + "        return -1\n"
                        + "    if arr[index] == target:\n"
                        + "        return index\n"
                        + "    return recursive_scan(arr, target, index + 1, end)\n\n"
                        + "def jump_search_recursive(arr, target, start=0, block_size=None):\n"
                        + "    if block_size is None:\n"
                        + "        block_size = int(math.sqrt(len(arr)))\n"
                        + "    if start >= len(arr):\n"
                        + "        return -1\n"
                        + "    end = min(start + block_size, len(arr))\n"
                        + "    if arr[end - 1] >= target:\n"
                        + "        return recursive_scan(arr, target, start, end - 1)\n"
                        + "    return jump_search_recursive(arr, target, end, block_size)";
                }
                return "import math\n\n"
                    + "def jump_search(arr, target):\n"
                    + "    step = int(math.sqrt(len(arr)))\n"
                    + "    prev = 0\n"
                    + "    while prev < len(arr) and arr[min(step, len(arr)) - 1] < target:\n"
                    + "        prev = step\n"
                    + "        step += int(math.sqrt(len(arr)))\n"
                    + "        if prev >= len(arr):\n"
                    + "            return -1\n"
                    + "    while prev < min(step, len(arr)):\n"
                    + "        if arr[prev] == target:\n"
                    + "            return prev\n"
                    + "        prev += 1\n"
                    + "    return -1";
            case "bubble_sort":
                if ("Functions".equals(style)) {
                    return "def bubble(arr, n):\n"
                        + "    for k in range(1, n):\n"
                        + "        ptr = 0\n"
                        + "        while ptr <= n - k - 1:\n"
                        + "            if arr[ptr] < arr[ptr + 1]:\n"
                        + "                arr[ptr], arr[ptr + 1] = arr[ptr + 1], arr[ptr]\n"
                        + "            ptr += 1\n\n"
                        + "n = int(input())\n"
                        + "arr = list(map(int, input().split()))\n\n"
                        + "bubble(arr, n)\n"
                        + "print(*arr)";
                }
                if ("Recursion".equals(style)) {
                    return "def bubble(arr, n):\n"
                        + "    if n == 1:\n"
                        + "        return\n\n"
                        + "    ptr = 0\n"
                        + "    while ptr <= n - 2:\n"
                        + "        if arr[ptr] < arr[ptr + 1]:\n"
                        + "            arr[ptr], arr[ptr + 1] = arr[ptr + 1], arr[ptr]\n"
                        + "        ptr += 1\n\n"
                        + "    bubble(arr, n - 1)\n\n"
                        + "n = int(input())\n"
                        + "arr = list(map(int, input().split()))\n\n"
                        + "bubble(arr, n)\n"
                        + "print(*arr)";
                }
                return "n = int(input())\n"
                    + "arr = list(map(int, input().split()))\n\n"
                    + "for k in range(1, n):\n"
                    + "    ptr = 0\n"
                    + "    while ptr <= n - k - 1:\n"
                    + "        if arr[ptr] < arr[ptr + 1]:\n"
                    + "            arr[ptr], arr[ptr + 1] = arr[ptr + 1], arr[ptr]\n"
                    + "        ptr += 1\n\n"
                    + "print(*arr)";
            case "selection_sort":
                if ("Functions".equals(style)) {
                    return "def find_min_index(arr, start):\n"
                        + "    min_index = start\n"
                        + "    for index in range(start + 1, len(arr)):\n"
                        + "        if arr[index] < arr[min_index]:\n"
                        + "            min_index = index\n"
                        + "    return min_index\n\n"
                        + "def selection_sort(arr):\n"
                        + "    for i in range(len(arr) - 1):\n"
                        + "        min_index = find_min_index(arr, i)\n"
                        + "        arr[i], arr[min_index] = arr[min_index], arr[i]";
                }
                if ("Recursion".equals(style)) {
                    return "def selection_sort_recursive(arr, start=0):\n"
                        + "    if start >= len(arr) - 1:\n"
                        + "        return\n"
                        + "    min_index = start\n"
                        + "    for index in range(start + 1, len(arr)):\n"
                        + "        if arr[index] < arr[min_index]:\n"
                        + "            min_index = index\n"
                        + "    arr[start], arr[min_index] = arr[min_index], arr[start]\n"
                        + "    selection_sort_recursive(arr, start + 1)";
                }
                return "def selection_sort(arr):\n"
                    + "    for i in range(len(arr) - 1):\n"
                    + "        min_index = i\n"
                    + "        for j in range(i + 1, len(arr)):\n"
                    + "            if arr[j] < arr[min_index]:\n"
                    + "                min_index = j\n"
                    + "        arr[i], arr[min_index] = arr[min_index], arr[i]";
            case "insertion_sort":
                if ("Functions".equals(style)) {
                    return "def place_value(arr, index):\n"
                        + "    key = arr[index]\n"
                        + "    j = index - 1\n"
                        + "    while j >= 0 and arr[j] > key:\n"
                        + "        arr[j + 1] = arr[j]\n"
                        + "        j -= 1\n"
                        + "    arr[j + 1] = key\n\n"
                        + "def insertion_sort(arr):\n"
                        + "    for index in range(1, len(arr)):\n"
                        + "        place_value(arr, index)";
                }
                if ("Recursion".equals(style)) {
                    return "def insertion_sort_recursive(arr, n=None):\n"
                        + "    if n is None:\n"
                        + "        n = len(arr)\n"
                        + "    if n <= 1:\n"
                        + "        return\n"
                        + "    insertion_sort_recursive(arr, n - 1)\n"
                        + "    key = arr[n - 1]\n"
                        + "    j = n - 2\n"
                        + "    while j >= 0 and arr[j] > key:\n"
                        + "        arr[j + 1] = arr[j]\n"
                        + "        j -= 1\n"
                        + "    arr[j + 1] = key";
                }
                return "def insertion_sort(arr):\n"
                    + "    for i in range(1, len(arr)):\n"
                    + "        key = arr[i]\n"
                    + "        j = i - 1\n"
                    + "        while j >= 0 and arr[j] > key:\n"
                    + "            arr[j + 1] = arr[j]\n"
                    + "            j -= 1\n"
                    + "        arr[j + 1] = key";
            case "stack_basics":
                return getPythonStackBasics(style);
            case "linked_stack_push":
                return getPythonLinkedStackPush(style);
            case "linked_stack_pop":
                return getPythonLinkedStackPop(style);
            case "quicksort":
                return getPythonQuicksort(style);
            case "queue_basics":
                return getPythonLinearQueue(style);
            case "circular_queue":
                return getPythonCircularQueue(style);
            case "linked_queue":
                return getPythonLinkedQueue(style);
            case "priority_queue_array":
                return getPythonPriorityQueueArray(style);
            case "priority_queue_2d":
                return getPythonPriorityQueue2d(style);
            case "preorder_traversal":
                return getPythonPreorderTraversal(style);
            case "postorder_traversal":
                return getPythonPostorderTraversal(style);
            case "bst_search_parent_tracking":
                return getPythonBstSearchParentTracking(style);
            case "bst_insertion":
                return getPythonBstInsertion(style);
            case "bst_deletion_case_a":
                return getPythonBstDeletionCaseA(style);
            case "bst_deletion_case_b":
                return getPythonBstDeletionCaseB(style);
            case "bst_deletion_del":
                return getPythonBstDeletionDel(style);
            case "max_heap_insertion":
                return getPythonMaxHeapInsertion(style);
            case "max_heap_deletion":
                return getPythonMaxHeapDeletion(style);
            case "heapsort":
                return getPythonHeapSort(style);
            case "singly_linked_list":
                if ("Functions".equals(style)) {
                    return "class Node:\n"
                        + "    def __init__(self, data):\n"
                        + "        self.data = data\n"
                        + "        self.next = None\n\n"
                        + "def get_tail(node):\n"
                        + "    while node.next is not None:\n"
                        + "        node = node.next\n"
                        + "    return node\n\n"
                        + "def insert_at_end(head, value):\n"
                        + "    if head is None:\n"
                        + "        return Node(value)\n"
                        + "    get_tail(head).next = Node(value)\n"
                        + "    return head";
                }
                if ("Recursion".equals(style)) {
                    return "class Node:\n"
                        + "    def __init__(self, data):\n"
                        + "        self.data = data\n"
                        + "        self.next = None\n\n"
                        + "def insert_at_end_recursive(head, value):\n"
                        + "    if head is None:\n"
                        + "        return Node(value)\n"
                        + "    head.next = insert_at_end_recursive(head.next, value)\n"
                        + "    return head";
                }
                return "class Node:\n"
                    + "    def __init__(self, data):\n"
                    + "        self.data = data\n"
                    + "        self.next = None\n\n"
                    + "def insert_at_end(head, value):\n"
                    + "    current = head\n"
                    + "    while current.next is not None:\n"
                    + "        current = current.next\n"
                    + "    current.next = Node(value)";
            case "traversing_linked_list":
            case "print_linked_list":
                if ("Functions".equals(style)) {
                    return "class Node:\n"
                        + "    def __init__(self, data):\n"
                        + "        self.data = data\n"
                        + "        self.next = None\n\n"
                        + "def printList(ptr):\n"
                        + "    while ptr:\n"
                        + "        print(ptr.data, end=\" \")\n"
                        + "        ptr = ptr.next\n\n"
                        + "n = int(input())\n"
                        + "head = None\n"
                        + "temp = None\n"
                        + "for _ in range(n):\n"
                        + "    val = int(input())\n"
                        + "    newNode = Node(val)\n"
                        + "    if head is None:\n"
                        + "        head = temp = newNode\n"
                        + "    else:\n"
                        + "        temp.next = newNode\n"
                        + "        temp = newNode\n"
                        + "printList(head)";
                }
                if ("Recursion".equals(style)) {
                    return "class Node:\n"
                        + "    def __init__(self, data):\n"
                        + "        self.data = data\n"
                        + "        self.next = None\n\n"
                        + "def printList(ptr):\n"
                        + "    if ptr is None:\n"
                        + "        return\n"
                        + "    print(ptr.data, end=\" \")\n"
                        + "    printList(ptr.next)\n\n"
                        + "n = int(input())\n"
                        + "head = None\n"
                        + "temp = None\n"
                        + "for _ in range(n):\n"
                        + "    val = int(input())\n"
                        + "    newNode = Node(val)\n"
                        + "    if head is None:\n"
                        + "        head = temp = newNode\n"
                        + "    else:\n"
                        + "        temp.next = newNode\n"
                        + "        temp = newNode\n"
                        + "printList(head)";
                }
                return "class Node:\n"
                    + "    def __init__(self, data):\n"
                    + "        self.data = data\n"
                    + "        self.next = None\n\n"
                    + "n = int(input())\n"
                    + "head = None\n"
                    + "temp = None\n"
                    + "for _ in range(n):\n"
                    + "    val = int(input())\n"
                    + "    newNode = Node(val)\n"
                    + "    if head is None:\n"
                    + "        head = temp = newNode\n"
                    + "    else:\n"
                    + "        temp.next = newNode\n"
                    + "        temp = newNode\n"
                    + "ptr = head\n"
                    + "while ptr:\n"
                    + "    print(ptr.data, end=\" \")\n"
                    + "    ptr = ptr.next";
            case "count_nodes_linked_list":
                return getPythonCountNodesLinkedList(style);
            case "search_in_linked_list":
                return getPythonSearchInLinkedList(style);
            case "insert_at_beginning_linked_list":
                return getPythonInsertAtBeginningLinkedList(style);
            case "insert_after_given_node_linked_list":
                return getPythonInsertAfterGivenNodeLinkedList(style);
            case "insert_into_sorted_linked_list":
                return getPythonInsertIntoSortedLinkedList(style);
            case "delete_node_given_location_linked_list":
                return getPythonDeleteNodeGivenLocationLinkedList(style);
            case "delete_node_by_item_linked_list":
                return getPythonDeleteNodeByItemLinkedList(style);
            case "traverse_circular_header_list":
                return getPythonTraverseCircularHeaderList(style);
            case "search_circular_header_list":
                return getPythonSearchCircularHeaderList(style);
            case "delete_circular_header_list":
                return getPythonDeleteCircularHeaderList(style);
            case "tree_traversal":
                if ("Functions".equals(style)) {
                    return "def visit(node):\n"
                        + "    if node is None:\n"
                        + "        return\n"
                        + "    print(node.data, end=' ')\n\n"
                        + "def preorder(root):\n"
                        + "    if root is None:\n"
                        + "        return\n"
                        + "    visit(root)\n"
                        + "    preorder(root.left)\n"
                        + "    preorder(root.right)";
                }
                if ("Recursion".equals(style)) {
                    return "def inorder(root):\n"
                        + "    if root is None:\n"
                        + "        return\n"
                        + "    inorder(root.left)\n"
                        + "    print(root.data, end=' ')\n"
                        + "    inorder(root.right)";
                }
                return "def inorder_iterative(root):\n"
                    + "    stack = []\n"
                    + "    current = root\n"
                    + "    while current is not None or stack:\n"
                    + "        while current is not None:\n"
                    + "            stack.append(current)\n"
                    + "            current = current.left\n"
                    + "        current = stack.pop()\n"
                    + "        print(current.data, end=' ')\n"
                    + "        current = current.right";
            case "graph_traversal":
                if ("Functions".equals(style)) {
                    return "from collections import deque\n\n"
                        + "def mark_visited(node, visited, queue):\n"
                        + "    visited.add(node)\n"
                        + "    queue.append(node)\n\n"
                        + "def add_neighbors(node, graph, visited, queue):\n"
                        + "    for next_node in graph[node]:\n"
                        + "        if next_node not in visited:\n"
                        + "            mark_visited(next_node, visited, queue)\n\n"
                        + "def bfs(start, graph):\n"
                        + "    visited = set()\n"
                        + "    queue = deque()\n"
                        + "    mark_visited(start, visited, queue)\n"
                        + "    while queue:\n"
                        + "        node = queue.popleft()\n"
                        + "        print(node, end=' ')\n"
                        + "        add_neighbors(node, graph, visited, queue)";
                }
                if ("Recursion".equals(style)) {
                    return "def dfs(node, graph, visited=None):\n"
                        + "    if visited is None:\n"
                        + "        visited = set()\n"
                        + "    visited.add(node)\n"
                        + "    print(node, end=' ')\n"
                        + "    for next_node in graph[node]:\n"
                        + "        if next_node not in visited:\n"
                        + "            dfs(next_node, graph, visited)";
                }
                return "from collections import deque\n\n"
                    + "def bfs(start, graph):\n"
                    + "    visited = {start}\n"
                    + "    queue = deque([start])\n"
                    + "    while queue:\n"
                    + "        node = queue.popleft()\n"
                    + "        print(node, end=' ')\n"
                    + "        for next_node in graph[node]:\n"
                    + "            if next_node not in visited:\n"
                    + "                visited.add(next_node)\n"
                    + "                queue.append(next_node)";
            case "warshall_algorithm":
                return getPythonWarshallAlgorithm(style);
            case "floyd_algorithm":
                return getPythonFloydAlgorithm(style);
            case "find_edge_graph":
                return getPythonFindEdgeGraph(style);
            case "insert_node_graph":
                return getPythonInsertNodeGraph(style);
            case "insert_edge_graph":
                return getPythonInsertEdgeGraph(style);
            case "delete_edge_graph":
                return getPythonDeleteEdgeGraph(style);
            case "delete_vertex_graph":
                return getPythonDeleteVertexGraph(style);
            case "breadth_first_search":
                return getPythonBreadthFirstSearch(style);
            case "depth_first_search":
                return getPythonDepthFirstSearch(style);
            case "topological_sort":
                return getPythonTopologicalSort(style);
            default:
                return "# Python code for this topic will be added here.";
        }
    }

    private static String getPythonCountNodesLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "def count_nodes(ptr):",
                "    if ptr is None:",
                "        return 0",
                "    return 1 + count_nodes(ptr.next)");
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "def count_nodes(ptr):",
                "    num = 0",
                "    while ptr:",
                "        num += 1",
                "        ptr = ptr.next",
                "    return num");
        }
        return String.join("\n",
            "count = 0",
            "ptr = head",
            "while ptr:",
            "    count += 1",
            "    ptr = ptr.next");
    }

    private static String getPythonSearchInLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "def search(ptr, item):",
                "    if ptr is None:",
                "        return None",
                "    if ptr.data == item:",
                "        return ptr",
                "    return search(ptr.next, item)");
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "def search(ptr, item):",
                "    while ptr:",
                "        if ptr.data == item:",
                "            return ptr",
                "        ptr = ptr.next",
                "    return None");
        }
        return String.join("\n",
            "ptr = head",
            "loc = None",
            "while ptr:",
            "    if ptr.data == item:",
            "        loc = ptr",
            "        break",
            "    ptr = ptr.next");
    }

    private static String getPythonInsertAtBeginningLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return "# Recursion is not applicable here because insertion at beginning is a single-step pointer update.";
        }
        return String.join("\n",
            "INFO = [0] * 100",
            "LINK = list(range(1, 100)) + [-1]",
            "START = -1",
            "AVAIL = 0",
            "def ins_first(item):",
            "    global START, AVAIL",
            "    if AVAIL == -1:",
            "        return",
            "    new = AVAIL",
            "    AVAIL = LINK[AVAIL]",
            "    INFO[new] = item",
            "    LINK[new] = START",
            "    START = new");
    }

    private static String getPythonInsertAfterGivenNodeLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return "# Recursion is not applicable here because insertion after a given node is a direct pointer operation.";
        }
        return String.join("\n",
            "INFO = [0] * 100",
            "LINK = list(range(1, 100)) + [-1]",
            "START = -1",
            "AVAIL = 0",
            "def ins_loc(loc, item):",
            "    global START, AVAIL",
            "    if AVAIL == -1:",
            "        return",
            "    new = AVAIL",
            "    AVAIL = LINK[AVAIL]",
            "    INFO[new] = item",
            "    if loc == -1:",
            "        LINK[new] = START",
            "        START = new",
            "    else:",
            "        LINK[new] = LINK[loc]",
            "        LINK[loc] = new");
    }

    private static String getPythonInsertIntoSortedLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "def findARec(ptr, item, previous):",
                "    if ptr == -1:",
                "        return previous",
                "    if item < INFO[ptr]:",
                "        return previous",
                "    return findARec(LINK[ptr], item, ptr)");
        }
        return String.join("\n",
            "def findA(item):",
            "    global START",
            "    if START == -1:",
            "        return -1",
            "    if item < INFO[START]:",
            "        return -1",
            "    save = START",
            "    ptr = LINK[START]",
            "    while ptr != -1:",
            "        if item < INFO[ptr]:",
            "            return save",
            "        save = ptr",
            "        ptr = LINK[ptr]",
            "    return save");
    }

    private static String getPythonDeleteNodeGivenLocationLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return "# Recursion is not applicable here because deletion by location is a direct pointer update.";
        }
        return String.join("\n",
            "def delete(loc, locp):",
            "    global START, AVAIL",
            "    if locp == -1:",
            "        START = LINK[START]",
            "    else:",
            "        LINK[locp] = LINK[loc]",
            "    LINK[loc] = AVAIL",
            "    AVAIL = loc");
    }

    private static String getPythonDeleteNodeByItemLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return "# Recursion is not suitable here because predecessor tracking is required.";
        }
        return String.join("\n",
            "def findb(item):",
            "    global START",
            "    if START == -1:",
            "        return (-1, -1)",
            "    if INFO[START] == item:",
            "        return (START, -1)",
            "    save = START",
            "    ptr = LINK[START]",
            "    while ptr != -1:",
            "        if INFO[ptr] == item:",
            "            return (ptr, save)",
            "        save = ptr",
            "        ptr = LINK[ptr]",
            "    return (-1, -1)");
    }

    private static String getPythonTraverseCircularHeaderList(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "def traverse(ptr, header):",
                "    if ptr == header:",
                "        return",
                "    print(ptr.data, end=\" \")",
                "    traverse(ptr.next, header)");
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "def traverse(header):",
                "    ptr = header.next",
                "    while ptr != header:",
                "        print(ptr.data, end=\" \")",
                "        ptr = ptr.next");
        }
        return String.join("\n",
            "ptr = header.next",
            "while ptr != header:",
            "    print(ptr.data, end=\" \")",
            "    ptr = ptr.next");
    }

    private static String getPythonSearchCircularHeaderList(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "def search(ptr, header, item):",
                "    if ptr == header:",
                "        return None",
                "    if ptr.data == item:",
                "        return ptr",
                "    return search(ptr.next, header, item)");
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "def search(header, item):",
                "    ptr = header.next",
                "    while ptr != header and ptr.data != item:",
                "        ptr = ptr.next",
                "    if ptr != header:",
                "        return ptr",
                "    return None");
        }
        return String.join("\n",
            "ptr = header.next",
            "while ptr != header and ptr.data != item:",
            "    ptr = ptr.next",
            "print(\"Element found\" if ptr != header else \"Element not found\")");
    }

    private static String getPythonDeleteCircularHeaderList(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "def find(ptr, header, prev, item):",
                "    if ptr == header:",
                "        return None, prev",
                "    if ptr.data == item:",
                "        return ptr, prev",
                "    return find(ptr.next, header, ptr, item)");
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "def delete_node(header, item):",
                "    ptr = header.next",
                "    save = header",
                "    while ptr != header and ptr.data != item:",
                "        save = ptr",
                "        ptr = ptr.next",
                "    if ptr == header:",
                "        print(\"Item not found\")",
                "    else:",
                "        save.next = ptr.next",
                "        print(\"Item deleted\")");
        }
        return String.join("\n",
            "ptr = header.next",
            "save = header",
            "while ptr != header and ptr.data != item:",
            "    save = ptr",
            "    ptr = ptr.next",
            "if ptr == header:",
            "    print(\"Item not found\")",
            "else:",
            "    save.next = ptr.next",
            "    print(\"Item deleted\")");
    }

    private static String getPythonStackBasics(String style) {
        if ("Recursion".equals(style)) {
            return "# Recursion is not applicable for array stack PUSH and POP in this book style.";
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "MAX = 10",
                "stack = []",
                "",
                "def push(item):",
                "    if len(stack) == MAX:",
                "        print(\"Overflow\")",
                "        return",
                "    stack.append(item)",
                "",
                "def pop_item():",
                "    if len(stack) == 0:",
                "        print(\"Underflow\")",
                "        return None",
                "    return stack.pop()",
                "",
                "while True:",
                "    print(\"1. PUSH\")",
                "    print(\"2. POP\")",
                "    print(\"3. EXIT\")",
                "    choice = int(input(\"Enter choice: \"))",
                "",
                "    if choice == 1:",
                "        item = int(input(\"Enter item: \"))",
                "        push(item)",
                "    elif choice == 2:",
                "        deleted = pop_item()",
                "        if deleted is not None:",
                "            print(\"Deleted item:\", deleted)",
                "    else:",
                "        break");
        }
        return String.join("\n",
            "MAX = 10",
            "stack = []",
            "",
            "while True:",
            "    print(\"1. PUSH\")",
            "    print(\"2. POP\")",
            "    print(\"3. EXIT\")",
            "    choice = int(input(\"Enter choice: \"))",
            "",
            "    if choice == 1:",
            "        if len(stack) == MAX:",
            "            print(\"Overflow\")",
            "        else:",
            "            item = int(input(\"Enter item: \"))",
            "            stack.append(item)",
            "    elif choice == 2:",
            "        if len(stack) == 0:",
            "            print(\"Underflow\")",
            "        else:",
            "            deleted = stack.pop()",
            "            print(\"Deleted item:\", deleted)",
            "    else:",
            "        break");
    }

    private static String getPythonLinkedStackPush(String style) {
        if ("Recursion".equals(style)) {
            return "# Recursion is not applicable for linked stack PUSH in this book style.";
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "class Node:",
                "    def __init__(self, data):",
                "        self.data = data",
                "        self.next = None",
                "",
                "def push(top, item):",
                "    new_node = Node(item)",
                "    new_node.next = top",
                "    return new_node",
                "",
                "def display(top):",
                "    ptr = top",
                "    while ptr is not None:",
                "        print(ptr.data, end=\" \")",
                "        ptr = ptr.next",
                "    print()",
                "",
                "top = None",
                "n = int(input(\"Enter number of items: \"))",
                "for _ in range(n):",
                "    item = int(input(\"Enter item: \"))",
                "    top = push(top, item)",
                "",
                "print(\"Stack after PUSH operations:\")",
                "display(top)");
        }
        return String.join("\n",
            "class Node:",
            "    def __init__(self, data):",
            "        self.data = data",
            "        self.next = None",
            "",
            "top = None",
            "n = int(input(\"Enter number of items: \"))",
            "for _ in range(n):",
            "    item = int(input(\"Enter item: \"))",
            "    new_node = Node(item)",
            "    new_node.next = top",
            "    top = new_node",
            "",
            "print(\"Stack after PUSH operations:\")",
            "ptr = top",
            "while ptr is not None:",
            "    print(ptr.data, end=\" \")",
            "    ptr = ptr.next",
            "print()");
    }

    private static String getPythonLinkedStackPop(String style) {
        if ("Recursion".equals(style)) {
            return "# Recursion is not applicable for linked stack POP in this book style.";
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "class Node:",
                "    def __init__(self, data):",
                "        self.data = data",
                "        self.next = None",
                "",
                "def push(top, item):",
                "    new_node = Node(item)",
                "    new_node.next = top",
                "    return new_node",
                "",
                "def pop_item(top):",
                "    if top is None:",
                "        print(\"Underflow\")",
                "        return top, None",
                "    item = top.data",
                "    top = top.next",
                "    return top, item",
                "",
                "top = None",
                "top = push(top, 10)",
                "top = push(top, 20)",
                "top = push(top, 30)",
                "top, deleted = pop_item(top)",
                "if deleted is not None:",
                "    print(\"Deleted item:\", deleted)",
                "print(\"Stack after POP:\")",
                "ptr = top",
                "while ptr is not None:",
                "    print(ptr.data, end=\" \")",
                "    ptr = ptr.next",
                "print()");
        }
        return String.join("\n",
            "class Node:",
            "    def __init__(self, data):",
            "        self.data = data",
            "        self.next = None",
            "",
            "top = None",
            "new_node = Node(10)",
            "new_node.next = top",
            "top = new_node",
            "new_node = Node(20)",
            "new_node.next = top",
            "top = new_node",
            "new_node = Node(30)",
            "new_node.next = top",
            "top = new_node",
            "",
            "if top is None:",
            "    print(\"Underflow\")",
            "else:",
            "    deleted = top.data",
            "    top = top.next",
            "    print(\"Deleted item:\", deleted)",
            "",
            "print(\"Stack after POP:\")",
            "ptr = top",
            "while ptr is not None:",
            "    print(ptr.data, end=\" \")",
            "    ptr = ptr.next",
            "print()");
    }

    private static String getPythonQuicksort(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "def partition(arr, low, high):",
                "    pivot = arr[low]",
                "    left = low + 1",
                "    right = high",
                "",
                "    while left <= right:",
                "        while left <= high and arr[left] <= pivot:",
                "            left += 1",
                "        while arr[right] > pivot:",
                "            right -= 1",
                "        if left < right:",
                "            arr[left], arr[right] = arr[right], arr[left]",
                "",
                "    arr[low], arr[right] = arr[right], arr[low]",
                "    return right",
                "",
                "def quicksort(arr, low, high):",
                "    if low < high:",
                "        loc = partition(arr, low, high)",
                "        quicksort(arr, low, loc - 1)",
                "        quicksort(arr, loc + 1, high)",
                "",
                "n = int(input(\"Enter number of elements: \"))",
                "arr = list(map(int, input(\"Enter elements: \").split()))",
                "quicksort(arr, 0, n - 1)",
                "print(\"Sorted array:\")",
                "print(*arr)");
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "def partition(arr, low, high):",
                "    pivot = arr[low]",
                "    left = low + 1",
                "    right = high",
                "    while left <= right:",
                "        while left <= high and arr[left] <= pivot:",
                "            left += 1",
                "        while arr[right] > pivot:",
                "            right -= 1",
                "        if left < right:",
                "            arr[left], arr[right] = arr[right], arr[left]",
                "    arr[low], arr[right] = arr[right], arr[low]",
                "    return right",
                "",
                "def quicksort(arr, low, high):",
                "    if low < high:",
                "        loc = partition(arr, low, high)",
                "        quicksort(arr, low, loc - 1)",
                "        quicksort(arr, loc + 1, high)",
                "",
                "n = int(input(\"Enter number of elements: \"))",
                "arr = list(map(int, input(\"Enter elements: \").split()))",
                "quicksort(arr, 0, n - 1)",
                "print(\"Sorted array:\")",
                "print(*arr)");
        }
        return String.join("\n",
            "def partition(arr, low, high):",
            "    pivot = arr[low]",
            "    left = low + 1",
            "    right = high",
            "",
            "    while left <= right:",
            "        while left <= high and arr[left] <= pivot:",
            "            left += 1",
            "        while arr[right] > pivot:",
            "            right -= 1",
            "        if left < right:",
            "            arr[left], arr[right] = arr[right], arr[left]",
            "",
            "    arr[low], arr[right] = arr[right], arr[low]",
            "    return right",
            "",
            "n = int(input(\"Enter number of elements: \"))",
            "arr = list(map(int, input(\"Enter elements: \").split()))",
            "lower = []",
            "upper = []",
            "",
            "if n > 1:",
            "    lower.append(0)",
            "    upper.append(n - 1)",
            "",
            "while lower:",
            "    beg = lower.pop()",
            "    end = upper.pop()",
            "    loc = partition(arr, beg, end)",
            "",
            "    if beg < loc - 1:",
            "        lower.append(beg)",
            "        upper.append(loc - 1)",
            "    if loc + 1 < end:",
            "        lower.append(loc + 1)",
            "        upper.append(end)",
            "",
            "print(\"Sorted array:\")",
            "print(*arr)");
    }

    private static String getPythonLinearQueue(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "def insert(queue, front, rear, n, item):",
                "    if rear == n:",
                "        print(\"OVERFLOW\")",
                "        return front, rear",
                "",
                "    if front == -1:",
                "        front = 0",
                "        rear = 0",
                "    else:",
                "        rear += 1",
                "",
                "    queue[rear] = item",
                "    return front, rear",
                "",
                "def delete(queue, front, rear):",
                "    if front == -1:",
                "        print(\"UNDERFLOW\")",
                "        return front, rear",
                "",
                "    print(\"Deleted:\", queue[front])",
                "    if front == rear:",
                "        return -1, -1",
                "    else:",
                "        return front + 1, rear",
                "",
                "n = int(input(\"Enter queue size: \"))",
                "queue = [0] * (n + 1)",
                "front = -1",
                "rear = -1",
                "item = int(input(\"Enter item to insert: \"))",
                "front, rear = insert(queue, front, rear, n, item)",
                "front, rear = delete(queue, front, rear)");
        }
        if ("Recursion".equals(style)) {
            return "print(\"Recursion not applicable for queue insertion and deletion\")";
        }
        return String.join("\n",
            "n = int(input(\"Enter queue size: \"))",
            "queue = [0] * (n + 1)",
            "front = -1",
            "rear = -1",
            "",
            "item = int(input(\"Enter item to insert: \"))",
            "",
            "if rear == n:",
            "    print(\"OVERFLOW\")",
            "else:",
            "    if front == -1:",
            "        front = 0",
            "        rear = 0",
            "    else:",
            "        rear += 1",
            "    queue[rear] = item",
            "    print(\"Item inserted\")",
            "",
            "if front == -1:",
            "    print(\"UNDERFLOW\")",
            "else:",
            "    deleted = queue[front]",
            "    if front == rear:",
            "        front = -1",
            "        rear = -1",
            "    else:",
            "        front += 1",
            "    print(\"Deleted:\", deleted)");
    }

    private static String getPythonCircularQueue(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "def insert(queue, front, rear, n, item):",
                "    if (front == 0 and rear == n - 1) or (front == rear + 1):",
                "        print(\"OVERFLOW\")",
                "        return front, rear",
                "",
                "    if front == -1:",
                "        front = 0",
                "        rear = 0",
                "    elif rear == n - 1:",
                "        rear = 0",
                "    else:",
                "        rear += 1",
                "",
                "    queue[rear] = item",
                "    return front, rear",
                "",
                "def delete(queue, front, rear, n):",
                "    if front == -1:",
                "        print(\"UNDERFLOW\")",
                "        return front, rear",
                "",
                "    print(\"Deleted:\", queue[front])",
                "    if front == rear:",
                "        return -1, -1",
                "    elif front == n - 1:",
                "        return 0, rear",
                "    else:",
                "        return front + 1, rear",
                "",
                "n = int(input(\"Enter queue size: \"))",
                "queue = [0] * n",
                "front = -1",
                "rear = -1",
                "item = int(input(\"Enter item to insert: \"))",
                "front, rear = insert(queue, front, rear, n, item)",
                "front, rear = delete(queue, front, rear, n)");
        }
        if ("Recursion".equals(style)) {
            return "print(\"Recursion not applicable for circular queue operations\")";
        }
        return String.join("\n",
            "n = int(input(\"Enter queue size: \"))",
            "queue = [0] * n",
            "front = -1",
            "rear = -1",
            "",
            "item = int(input(\"Enter item to insert: \"))",
            "",
            "if (front == 0 and rear == n - 1) or (front == rear + 1):",
            "    print(\"OVERFLOW\")",
            "else:",
            "    if front == -1:",
            "        front = 0",
            "        rear = 0",
            "    elif rear == n - 1:",
            "        rear = 0",
            "    else:",
            "        rear += 1",
            "    queue[rear] = item",
            "    print(\"Item inserted\")",
            "",
            "if front == -1:",
            "    print(\"UNDERFLOW\")",
            "else:",
            "    deleted = queue[front]",
            "    if front == rear:",
            "        front = -1",
            "        rear = -1",
            "    elif front == n - 1:",
            "        front = 0",
            "    else:",
            "        front += 1",
            "    print(\"Deleted:\", deleted)");
    }

    private static String getPythonLinkedQueue(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "class Node:",
                "    def __init__(self, data):",
                "        self.data = data",
                "        self.next = None",
                "",
                "def insert(front, rear, item):",
                "    new_node = Node(item)",
                "    if front is None:",
                "        return new_node, new_node",
                "    else:",
                "        rear.next = new_node",
                "        return front, new_node",
                "",
                "def delete(front, rear):",
                "    if front is None:",
                "        print(\"UNDERFLOW\")",
                "        return front, rear",
                "",
                "    print(\"Deleted:\", front.data)",
                "    front = front.next",
                "    if front is None:",
                "        rear = None",
                "    return front, rear",
                "",
                "front = None",
                "rear = None",
                "item = int(input(\"Enter item to insert: \"))",
                "front, rear = insert(front, rear, item)",
                "print(\"Item inserted\")",
                "front, rear = delete(front, rear)");
        }
        if ("Recursion".equals(style)) {
            return "print(\"Recursion not applicable for linked queue operations\")";
        }
        return String.join("\n",
            "class Node:",
            "    def __init__(self, data):",
            "        self.data = data",
            "        self.next = None",
            "",
            "front = None",
            "rear = None",
            "",
            "item = int(input(\"Enter item to insert: \"))",
            "new_node = Node(item)",
            "",
            "if front is None:",
            "    front = rear = new_node",
            "else:",
            "    rear.next = new_node",
            "    rear = new_node",
            "",
            "print(\"Item inserted\")",
            "",
            "if front is None:",
            "    print(\"UNDERFLOW\")",
            "else:",
            "    deleted = front.data",
            "    front = front.next",
            "    print(\"Deleted:\", deleted)");
    }

    private static String getPythonPriorityQueueArray(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "def insert(data, priority, size, item, p):",
                "    i = size - 1",
                "    while i >= 0 and priority[i] > p:",
                "        data[i + 1] = data[i]",
                "        priority[i + 1] = priority[i]",
                "        i -= 1",
                "    data[i + 1] = item",
                "    priority[i + 1] = p",
                "    return size + 1",
                "",
                "def delete(data, priority, size):",
                "    if size == 0:",
                "        print(\"UNDERFLOW\")",
                "        return size",
                "",
                "    print(\"Deleted:\", data[0])",
                "    for i in range(size - 1):",
                "        data[i] = data[i + 1]",
                "        priority[i] = priority[i + 1]",
                "    return size - 1",
                "",
                "n = int(input(\"Enter size of queue: \"))",
                "data = [0] * n",
                "priority = [0] * n",
                "size = 0",
                "item = int(input(\"Enter item: \"))",
                "p = int(input(\"Enter priority: \"))",
                "size = insert(data, priority, size, item, p)",
                "print(\"Item inserted\")",
                "size = delete(data, priority, size)");
        }
        if ("Recursion".equals(style)) {
            return "print(\"Recursion not applicable for priority queue (array)\")";
        }
        return String.join("\n",
            "n = int(input(\"Enter size of queue: \"))",
            "data = [0] * n",
            "priority = [0] * n",
            "size = 0",
            "",
            "item = int(input(\"Enter item: \"))",
            "p = int(input(\"Enter priority: \"))",
            "",
            "i = size - 1",
            "while i >= 0 and priority[i] > p:",
            "    data[i + 1] = data[i]",
            "    priority[i + 1] = priority[i]",
            "    i -= 1",
            "",
            "data[i + 1] = item",
            "priority[i + 1] = p",
            "size += 1",
            "print(\"Item inserted\")",
            "",
            "if size == 0:",
            "    print(\"UNDERFLOW\")",
            "else:",
            "    deleted = data[0]",
            "    for j in range(size - 1):",
            "        data[j] = data[j + 1]",
            "        priority[j] = priority[j + 1]",
            "    size -= 1",
            "    print(\"Deleted:\", deleted)");
    }

    private static String getPythonPriorityQueue2d(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "def insert(queue, front, rear, n, m, item):",
                "    if rear[m] == n - 1:",
                "        print(\"OVERFLOW\")",
                "        return front, rear",
                "",
                "    if front[m] == -1:",
                "        front[m] = 0",
                "        rear[m] = 0",
                "    else:",
                "        rear[m] += 1",
                "",
                "    queue[m][rear[m]] = item",
                "    return front, rear",
                "",
                "def delete(queue, front, rear, p):",
                "    k = -1",
                "    for i in range(p):",
                "        if front[i] != -1:",
                "            k = i",
                "            break",
                "",
                "    if k == -1:",
                "        print(\"UNDERFLOW\")",
                "        return front, rear",
                "",
                "    print(\"Deleted:\", queue[k][front[k]])",
                "    if front[k] == rear[k]:",
                "        front[k] = -1",
                "        rear[k] = -1",
                "    else:",
                "        front[k] += 1",
                "    return front, rear",
                "",
                "p = int(input(\"Enter number of priority levels: \"))",
                "n = int(input(\"Enter size of each queue: \"))",
                "queue = [[0] * n for _ in range(p)]",
                "front = [-1] * p",
                "rear = [-1] * p",
                "item = int(input(\"Enter item: \"))",
                "m = int(input(\"Enter priority level: \"))",
                "front, rear = insert(queue, front, rear, n, m, item)",
                "print(\"Item inserted\")",
                "front, rear = delete(queue, front, rear, p)");
        }
        if ("Recursion".equals(style)) {
            return "print(\"Recursion not applicable for priority queue (2D array)\")";
        }
        return String.join("\n",
            "p = int(input(\"Enter number of priority levels: \"))",
            "n = int(input(\"Enter size of each queue: \"))",
            "",
            "queue = [[0]*n for _ in range(p)]",
            "front = [-1]*p",
            "rear = [-1]*p",
            "",
            "item = int(input(\"Enter item: \"))",
            "m = int(input(\"Enter priority level: \"))",
            "",
            "if rear[m] == n - 1:",
            "    print(\"OVERFLOW\")",
            "else:",
            "    if front[m] == -1:",
            "        front[m] = 0",
            "        rear[m] = 0",
            "    else:",
            "        rear[m] += 1",
            "    queue[m][rear[m]] = item",
            "    print(\"Item inserted\")",
            "",
            "k = -1",
            "for i in range(p):",
            "    if front[i] != -1:",
            "        k = i",
            "        break",
            "",
            "if k == -1:",
            "    print(\"UNDERFLOW\")",
            "else:",
            "    deleted = queue[k][front[k]]",
            "    if front[k] == rear[k]:",
            "        front[k] = -1",
            "        rear[k] = -1",
            "    else:",
            "        front[k] += 1",
            "    print(\"Deleted:\", deleted)");
    }

    private static String getPythonPreorderTraversal(String style) {
        String setup = "class Node:\n"
            + "    def __init__(self, data):\n"
            + "        self.data = data\n"
            + "        self.left = None\n"
            + "        self.right = None\n\n";
        if ("Functions".equals(style)) {
            return setup
                + "def preorder(ptr):\n"
                + "    if ptr is None:\n"
                + "        return\n"
                + "    print(ptr.data, end=\" \")\n"
                + "    preorder(ptr.left)\n"
                + "    preorder(ptr.right)\n\n"
                + "n = int(input(\"Enter number of nodes: \"))\n"
                + "nodes = []\n"
                + "for i in range(n):\n"
                + "    val = int(input(f\"Enter value of node {i}: \"))\n"
                + "    nodes.append(Node(val))\n"
                + "for i in range(n):\n"
                + "    l, r = map(int, input(f\"Enter left and right child index of node {i} (-1 if none): \").split())\n"
                + "    if l != -1:\n"
                + "        nodes[i].left = nodes[l]\n"
                + "    if r != -1:\n"
                + "        nodes[i].right = nodes[r]\n"
                + "preorder(nodes[0])";
        }
        if ("Recursion".equals(style)) {
            return setup
                + "def preorderRec(root):\n"
                + "    if root is None:\n"
                + "        return\n"
                + "    print(root.data, end=\" \")\n"
                + "    preorderRec(root.left)\n"
                + "    preorderRec(root.right)\n\n"
                + "n = int(input(\"Enter number of nodes: \"))\n"
                + "nodes = []\n"
                + "for i in range(n):\n"
                + "    val = int(input(f\"Enter value of node {i}: \"))\n"
                + "    nodes.append(Node(val))\n"
                + "for i in range(n):\n"
                + "    l, r = map(int, input(f\"Enter left and right child index of node {i} (-1 if none): \").split())\n"
                + "    if l != -1:\n"
                + "        nodes[i].left = nodes[l]\n"
                + "    if r != -1:\n"
                + "        nodes[i].right = nodes[r]\n"
                + "preorderRec(nodes[0])";
        }
        return setup
            + "n = int(input(\"Enter number of nodes: \"))\n"
            + "nodes = []\n"
            + "for i in range(n):\n"
            + "    val = int(input(f\"Enter value of node {i}: \"))\n"
            + "    nodes.append(Node(val))\n"
            + "for i in range(n):\n"
            + "    l, r = map(int, input(f\"Enter left and right child index of node {i} (-1 if none): \").split())\n"
            + "    if l != -1:\n"
            + "        nodes[i].left = nodes[l]\n"
            + "    if r != -1:\n"
            + "        nodes[i].right = nodes[r]\n"
            + "stack = []\n"
            + "ptr = nodes[0]\n"
            + "stack.append(None)\n"
            + "while ptr is not None:\n"
            + "    print(ptr.data, end=\" \")\n"
            + "    if ptr.right is not None:\n"
            + "        stack.append(ptr.right)\n"
            + "    if ptr.left is not None:\n"
            + "        ptr = ptr.left\n"
            + "    else:\n"
            + "        ptr = stack.pop()";
    }

    private static String getPythonPostorderTraversal(String style) {
        String setup = "class Node:\n"
            + "    def __init__(self, data):\n"
            + "        self.data = data\n"
            + "        self.left = None\n"
            + "        self.right = None\n\n";
        if ("Functions".equals(style)) {
            return setup
                + "def postorder(ptr):\n"
                + "    if ptr is None:\n"
                + "        return\n"
                + "    postorder(ptr.left)\n"
                + "    postorder(ptr.right)\n"
                + "    print(ptr.data, end=\" \")\n\n"
                + "n = int(input(\"Enter number of nodes: \"))\n"
                + "nodes = []\n"
                + "for i in range(n):\n"
                + "    val = int(input(f\"Enter value of node {i}: \"))\n"
                + "    nodes.append(Node(val))\n"
                + "for i in range(n):\n"
                + "    l, r = map(int, input(f\"Enter left and right child index of node {i} (-1 if none): \").split())\n"
                + "    if l != -1:\n"
                + "        nodes[i].left = nodes[l]\n"
                + "    if r != -1:\n"
                + "        nodes[i].right = nodes[r]\n"
                + "postorder(nodes[0])";
        }
        if ("Recursion".equals(style)) {
            return setup
                + "def postorderRec(root):\n"
                + "    if root is None:\n"
                + "        return\n"
                + "    postorderRec(root.left)\n"
                + "    postorderRec(root.right)\n"
                + "    print(root.data, end=\" \")\n\n"
                + "n = int(input(\"Enter number of nodes: \"))\n"
                + "nodes = []\n"
                + "for i in range(n):\n"
                + "    val = int(input(f\"Enter value of node {i}: \"))\n"
                + "    nodes.append(Node(val))\n"
                + "for i in range(n):\n"
                + "    l, r = map(int, input(f\"Enter left and right child index of node {i} (-1 if none): \").split())\n"
                + "    if l != -1:\n"
                + "        nodes[i].left = nodes[l]\n"
                + "    if r != -1:\n"
                + "        nodes[i].right = nodes[r]\n"
                + "postorderRec(nodes[0])";
        }
        return setup
            + "n = int(input(\"Enter number of nodes: \"))\n"
            + "nodes = []\n"
            + "for i in range(n):\n"
            + "    val = int(input(f\"Enter value of node {i}: \"))\n"
            + "    nodes.append(Node(val))\n"
            + "for i in range(n):\n"
            + "    l, r = map(int, input(f\"Enter left and right child index of node {i} (-1 if none): \").split())\n"
            + "    if l != -1:\n"
            + "        nodes[i].left = nodes[l]\n"
            + "    if r != -1:\n"
            + "        nodes[i].right = nodes[r]\n"
            + "stack = []\n"
            + "output = []\n"
            + "stack.append(nodes[0])\n"
            + "while stack:\n"
            + "    temp = stack.pop()\n"
            + "    output.append(temp)\n"
            + "    if temp.left:\n"
            + "        stack.append(temp.left)\n"
            + "    if temp.right:\n"
            + "        stack.append(temp.right)\n"
            + "while output:\n"
            + "    print(output.pop().data, end=\" \")";
    }

    private static String getPythonBstSearchParentTracking(String style) {
        String setup = "class Node:\n"
            + "    def __init__(self, data):\n"
            + "        self.data = data\n"
            + "        self.left = None\n"
            + "        self.right = None\n\n";
        if ("Functions".equals(style)) {
            return setup
                + "loc = None\n"
                + "par = None\n\n"
                + "def find(ptr, save, item):\n"
                + "    global loc, par\n"
                + "    if ptr is None:\n"
                + "        loc = None\n"
                + "        par = save\n"
                + "        return\n"
                + "    if item == ptr.data:\n"
                + "        loc = ptr\n"
                + "        par = save\n"
                + "        return\n"
                + "    if item < ptr.data:\n"
                + "        find(ptr.left, ptr, item)\n"
                + "    else:\n"
                + "        find(ptr.right, ptr, item)\n\n"
                + "n = int(input())\n"
                + "nodes = [Node(int(input())) for _ in range(n)]\n"
                + "for i in range(n):\n"
                + "    l, r = map(int, input().split())\n"
                + "    if l != -1:\n"
                + "        nodes[i].left = nodes[l]\n"
                + "    if r != -1:\n"
                + "        nodes[i].right = nodes[r]\n"
                + "item = int(input())\n"
                + "find(nodes[0], None, item)\n"
                + "if loc:\n"
                + "    print(\"Found. Parent:\", par.data if par else \"NULL\")\n"
                + "else:\n"
                + "    print(\"Not Found. Parent:\", par.data if par else \"NULL\")";
        }
        if ("Recursion".equals(style)) {
            return setup
                + "loc = None\n"
                + "par = None\n\n"
                + "def findRec(root, parent, item):\n"
                + "    global loc, par\n"
                + "    if root is None:\n"
                + "        loc = None\n"
                + "        par = parent\n"
                + "        return\n"
                + "    if item == root.data:\n"
                + "        loc = root\n"
                + "        par = parent\n"
                + "        return\n"
                + "    if item < root.data:\n"
                + "        findRec(root.left, root, item)\n"
                + "    else:\n"
                + "        findRec(root.right, root, item)\n\n"
                + "n = int(input())\n"
                + "nodes = [Node(int(input())) for _ in range(n)]\n"
                + "for i in range(n):\n"
                + "    l, r = map(int, input().split())\n"
                + "    if l != -1:\n"
                + "        nodes[i].left = nodes[l]\n"
                + "    if r != -1:\n"
                + "        nodes[i].right = nodes[r]\n"
                + "item = int(input())\n"
                + "findRec(nodes[0], None, item)\n"
                + "if loc:\n"
                + "    print(\"Found. Parent:\", par.data if par else \"NULL\")\n"
                + "else:\n"
                + "    print(\"Not Found. Parent:\", par.data if par else \"NULL\")";
        }
        return setup
            + "n = int(input())\n"
            + "nodes = [Node(int(input())) for _ in range(n)]\n"
            + "for i in range(n):\n"
            + "    l, r = map(int, input().split())\n"
            + "    if l != -1:\n"
            + "        nodes[i].left = nodes[l]\n"
            + "    if r != -1:\n"
            + "        nodes[i].right = nodes[r]\n"
            + "item = int(input())\n"
            + "root = nodes[0]\n"
            + "ptr = root\n"
            + "save = None\n"
            + "loc = None\n"
            + "par = None\n"
            + "if root is None:\n"
            + "    loc = None\n"
            + "    par = None\n"
            + "elif item == root.data:\n"
            + "    loc = root\n"
            + "    par = None\n"
            + "else:\n"
            + "    if item < root.data:\n"
            + "        ptr = root.left\n"
            + "        save = root\n"
            + "    else:\n"
            + "        ptr = root.right\n"
            + "        save = root\n"
            + "    while ptr is not None:\n"
            + "        if item == ptr.data:\n"
            + "            loc = ptr\n"
            + "            par = save\n"
            + "            break\n"
            + "        elif item < ptr.data:\n"
            + "            save = ptr\n"
            + "            ptr = ptr.left\n"
            + "        else:\n"
            + "            save = ptr\n"
            + "            ptr = ptr.right\n"
            + "    if ptr is None:\n"
            + "        loc = None\n"
            + "        par = save\n"
            + "if loc:\n"
            + "    print(\"Found. Parent:\", par.data if par else \"NULL\")\n"
            + "else:\n"
            + "    print(\"Not Found. Parent:\", par.data if par else \"NULL\")";
    }

    private static String getPythonBstInsertion(String style) {
        String setup = "class Node:\n"
            + "    def __init__(self, data):\n"
            + "        self.data = data\n"
            + "        self.left = None\n"
            + "        self.right = None\n\n";
        if ("Functions".equals(style)) {
            return setup
                + "def insertFunc(root, item):\n"
                + "    if root is None:\n"
                + "        return Node(item)\n"
                + "    if item < root.data:\n"
                + "        root.left = insertFunc(root.left, item)\n"
                + "    elif item > root.data:\n"
                + "        root.right = insertFunc(root.right, item)\n"
                + "    return root\n\n"
                + "n = int(input())\n"
                + "root = None\n"
                + "for _ in range(n):\n"
                + "    val = int(input())\n"
                + "    root = insertFunc(root, val)\n"
                + "item = int(input())\n"
                + "root = insertFunc(root, item)\n"
                + "print(\"Insertion Done\")";
        }
        if ("Recursion".equals(style)) {
            return setup
                + "def insertRec(root, item):\n"
                + "    if root is None:\n"
                + "        return Node(item)\n"
                + "    if item < root.data:\n"
                + "        root.left = insertRec(root.left, item)\n"
                + "    elif item > root.data:\n"
                + "        root.right = insertRec(root.right, item)\n"
                + "    return root\n\n"
                + "n = int(input())\n"
                + "root = None\n"
                + "for _ in range(n):\n"
                + "    val = int(input())\n"
                + "    root = insertRec(root, val)\n"
                + "item = int(input())\n"
                + "root = insertRec(root, item)\n"
                + "print(\"Insertion Done\")";
        }
        return setup
            + "def insert(root, item):\n"
            + "    parent = None\n"
            + "    ptr = root\n"
            + "    while ptr:\n"
            + "        parent = ptr\n"
            + "        if item < ptr.data:\n"
            + "            ptr = ptr.left\n"
            + "        elif item > ptr.data:\n"
            + "            ptr = ptr.right\n"
            + "        else:\n"
            + "            return root\n"
            + "    newNode = Node(item)\n"
            + "    if parent is None:\n"
            + "        return newNode\n"
            + "    elif item < parent.data:\n"
            + "        parent.left = newNode\n"
            + "    else:\n"
            + "        parent.right = newNode\n"
            + "    return root\n\n"
            + "n = int(input())\n"
            + "root = None\n"
            + "for _ in range(n):\n"
            + "    val = int(input())\n"
            + "    root = insert(root, val)\n"
            + "item = int(input())\n"
            + "root = insert(root, item)\n"
            + "print(\"Insertion Done\")";
    }

    private static String getPythonBstDeletionCaseA(String style) {
        String base = "class Node:\n"
            + "    def __init__(self, data):\n"
            + "        self.data = data\n"
            + "        self.left = None\n"
            + "        self.right = None\n\n"
            + "def insert(root, val):\n"
            + "    if root is None:\n"
            + "        return Node(val)\n"
            + "    if val < root.data:\n"
            + "        root.left = insert(root.left, val)\n"
            + "    elif val > root.data:\n"
            + "        root.right = insert(root.right, val)\n"
            + "    return root\n\n";
        if ("Functions".equals(style)) {
            return base
                + "def deleteFunc(root, item):\n"
                + "    if root is None:\n"
                + "        return None\n"
                + "    if item < root.data:\n"
                + "        root.left = deleteFunc(root.left, item)\n"
                + "    elif item > root.data:\n"
                + "        root.right = deleteFunc(root.right, item)\n"
                + "    else:\n"
                + "        if root.left is None:\n"
                + "            return root.right\n"
                + "        elif root.right is None:\n"
                + "            return root.left\n"
                + "    return root\n\n"
                + "n = int(input())\nroot = None\nfor _ in range(n):\n    root = insert(root, int(input()))\nroot = deleteFunc(root, int(input()))\nprint(\"Deletion Done\")";
        }
        if ("Recursion".equals(style)) {
            return base
                + "def deleteRec(root, item):\n"
                + "    if root is None:\n"
                + "        return None\n"
                + "    if item < root.data:\n"
                + "        root.left = deleteRec(root.left, item)\n"
                + "    elif item > root.data:\n"
                + "        root.right = deleteRec(root.right, item)\n"
                + "    else:\n"
                + "        if root.left is None and root.right is None:\n"
                + "            return None\n"
                + "        elif root.left is not None:\n"
                + "            return root.left\n"
                + "        else:\n"
                + "            return root.right\n"
                + "    return root\n\n"
                + "n = int(input())\nroot = None\nfor _ in range(n):\n    root = insert(root, int(input()))\nroot = deleteRec(root, int(input()))\nprint(\"Deletion Done\")";
        }
        return base
            + "def deleteCaseA(root, item):\n"
            + "    ptr = root\n    par = None\n"
            + "    while ptr and ptr.data != item:\n"
            + "        par = ptr\n"
            + "        if item < ptr.data:\n"
            + "            ptr = ptr.left\n"
            + "        else:\n"
            + "            ptr = ptr.right\n"
            + "    if ptr is None:\n"
            + "        return root\n"
            + "    if ptr.left is None and ptr.right is None:\n"
            + "        child = None\n"
            + "    elif ptr.left:\n"
            + "        child = ptr.left\n"
            + "    else:\n"
            + "        child = ptr.right\n"
            + "    if par:\n"
            + "        if par.left == ptr:\n"
            + "            par.left = child\n"
            + "        else:\n"
            + "            par.right = child\n"
            + "    else:\n"
            + "        root = child\n"
            + "    return root\n\n"
            + "n = int(input())\nroot = None\nfor _ in range(n):\n    root = insert(root, int(input()))\nroot = deleteCaseA(root, int(input()))\nprint(\"Deletion Done\")";
    }

    private static String getPythonBstDeletionCaseB(String style) {
        String base = "class Node:\n"
            + "    def __init__(self, data):\n"
            + "        self.data = data\n"
            + "        self.left = None\n"
            + "        self.right = None\n\n"
            + "def insert(root, val):\n"
            + "    if root is None:\n"
            + "        return Node(val)\n"
            + "    if val < root.data:\n"
            + "        root.left = insert(root.left, val)\n"
            + "    elif val > root.data:\n"
            + "        root.right = insert(root.right, val)\n"
            + "    return root\n\n";
        if ("Functions".equals(style) || "Recursion".equals(style)) {
            String name = "Functions".equals(style) ? "deleteFunc" : "deleteRec";
            return base
                + "def " + name + "(root, item):\n"
                + "    if root is None:\n"
                + "        return None\n"
                + "    if item < root.data:\n"
                + "        root.left = " + name + "(root.left, item)\n"
                + "    elif item > root.data:\n"
                + "        root.right = " + name + "(root.right, item)\n"
                + "    else:\n"
                + "        if root.left and root.right:\n"
                + "            suc = root.right\n"
                + "            while suc.left:\n"
                + "                suc = suc.left\n"
                + "            root.data = suc.data\n"
                + "            root.right = " + name + "(root.right, suc.data)\n"
                + "    return root\n\n"
                + "n = int(input())\nroot = None\nfor _ in range(n):\n    root = insert(root, int(input()))\nroot = " + name + "(root, int(input()))\nprint(\"Deletion Done\")";
        }
        return base
            + "def deleteCaseB(root, item):\n"
            + "    ptr = root\n"
            + "    while ptr and ptr.data != item:\n"
            + "        if item < ptr.data:\n"
            + "            ptr = ptr.left\n"
            + "        else:\n"
            + "            ptr = ptr.right\n"
            + "    if ptr is None:\n"
            + "        return root\n"
            + "    if ptr.left and ptr.right:\n"
            + "        suc_parent = ptr\n"
            + "        suc = ptr.right\n"
            + "        while suc.left:\n"
            + "            suc_parent = suc\n"
            + "            suc = suc.left\n"
            + "        ptr.data = suc.data\n"
            + "        if suc_parent.left == suc:\n"
            + "            suc_parent.left = suc.right\n"
            + "        else:\n"
            + "            suc_parent.right = suc.right\n"
            + "    return root\n\n"
            + "n = int(input())\nroot = None\nfor _ in range(n):\n    root = insert(root, int(input()))\nroot = deleteCaseB(root, int(input()))\nprint(\"Deletion Done\")";
    }

    private static String getPythonBstDeletionDel(String style) {
        String base = "class Node:\n"
            + "    def __init__(self, data):\n"
            + "        self.data = data\n"
            + "        self.left = None\n"
            + "        self.right = None\n\n"
            + "def insert(root, val):\n"
            + "    if root is None:\n"
            + "        return Node(val)\n"
            + "    if val < root.data:\n"
            + "        root.left = insert(root.left, val)\n"
            + "    elif val > root.data:\n"
            + "        root.right = insert(root.right, val)\n"
            + "    return root\n\n";
        String name = "Iterative".equals(style) ? "deleteNode" : ("Functions".equals(style) ? "deleteFunc" : "deleteRec");
        return base
            + "def " + name + "(root, item):\n"
            + "    if root is None:\n"
            + "        print(\"ITEM not in tree\")\n"
            + "        return None\n"
            + "    if item < root.data:\n"
            + "        root.left = " + name + "(root.left, item)\n"
            + "    elif item > root.data:\n"
            + "        root.right = " + name + "(root.right, item)\n"
            + "    else:\n"
            + "        if root.left and root.right:\n"
            + "            suc = root.right\n"
            + "            while suc.left:\n"
            + "                suc = suc.left\n"
            + "            root.data = suc.data\n"
            + "            root.right = " + name + "(root.right, suc.data)\n"
            + "        elif root.left:\n"
            + "            return root.left\n"
            + "        else:\n"
            + "            return root.right\n"
            + "    return root\n\n"
            + "n = int(input())\nroot = None\nfor _ in range(n):\n    root = insert(root, int(input()))\nroot = " + name + "(root, int(input()))\nprint(\"Deletion Done\")";
    }

    private static String getPythonMaxHeapInsertion(String style) {
        if ("Functions".equals(style)) {
            return "def insertHeap(tree, n, item):\n    n += 1\n    ptr = n\n    while ptr > 1:\n        par = ptr // 2\n        if item <= tree[par]:\n            tree[ptr] = item\n            return n\n        tree[ptr] = tree[par]\n        ptr = par\n    tree[ptr] = item\n    return n\n\nn = int(input())\ntree = [0]*100\nfor i in range(1, n+1):\n    tree[i] = int(input())\nitem = int(input())\nn = insertHeap(tree, n, item)\nfor i in range(1, n+1):\n    print(tree[i], end=\" \")";
        }
        if ("Recursion".equals(style)) {
            return "def insertRec(tree, ptr, item):\n    if ptr == 1:\n        tree[ptr] = item\n        return\n    par = ptr // 2\n    if item <= tree[par]:\n        tree[ptr] = item\n        return\n    tree[ptr] = tree[par]\n    insertRec(tree, par, item)\n\nn = int(input())\ntree = [0]*100\nfor i in range(1, n+1):\n    tree[i] = int(input())\nitem = int(input())\nn += 1\ninsertRec(tree, n, item)\nfor i in range(1, n+1):\n    print(tree[i], end=\" \")";
        }
        return "n = int(input())\ntree = [0]*100\nfor i in range(1, n+1):\n    tree[i] = int(input())\nitem = int(input())\nn += 1\nptr = n\nwhile ptr > 1:\n    par = ptr // 2\n    if item <= tree[par]:\n        tree[ptr] = item\n        break\n    tree[ptr] = tree[par]\n    ptr = par\nif ptr == 1:\n    tree[ptr] = item\nfor i in range(1, n+1):\n    print(tree[i], end=\" \")";
    }

    private static String getPythonMaxHeapDeletion(String style) {
        if ("Functions".equals(style)) {
            return "def deleteHeap(tree, n):\n    item = tree[1]\n    last = tree[n]\n    n -= 1\n    ptr = 1\n    while 2 * ptr <= n:\n        left = 2 * ptr\n        right = left + 1\n        child = right if (right <= n and tree[right] > tree[left]) else left\n        if last >= tree[child]:\n            break\n        tree[ptr] = tree[child]\n        ptr = child\n    tree[ptr] = last\n    return item\n\nn = int(input())\ntree = [0]*100\nfor i in range(1, n+1):\n    tree[i] = int(input())\ndeleted = deleteHeap(tree, n)\nprint(\"Deleted element:\", deleted)";
        }
        if ("Recursion".equals(style)) {
            return "def deleteRec(tree, ptr, n, last):\n    left = 2 * ptr\n    right = left + 1\n    if left > n:\n        tree[ptr] = last\n        return\n    child = right if (right <= n and tree[right] > tree[left]) else left\n    if last >= tree[child]:\n        tree[ptr] = last\n        return\n    tree[ptr] = tree[child]\n    deleteRec(tree, child, n, last)\n\nn = int(input())\ntree = [0]*100\nfor i in range(1, n+1):\n    tree[i] = int(input())\nitem = tree[1]\nlast = tree[n]\nn -= 1\ndeleteRec(tree, 1, n, last)\nprint(\"Deleted element:\", item)\nfor i in range(1, n+1):\n    print(tree[i], end=\" \")";
        }
        return "n = int(input())\ntree = [0]*100\nfor i in range(1, n+1):\n    tree[i] = int(input())\nitem = tree[1]\nlast = tree[n]\nn -= 1\nptr = 1\nwhile 2 * ptr <= n:\n    left = 2 * ptr\n    right = left + 1\n    child = right if (right <= n and tree[right] > tree[left]) else left\n    if last >= tree[child]:\n        break\n    tree[ptr] = tree[child]\n    ptr = child\ntree[ptr] = last\nprint(\"Deleted element:\", item)\nfor i in range(1, n+1):\n    print(tree[i], end=\" \")";
    }

    private static String getPythonHeapSort(String style) {
        String helpers = "def insertHeap(A, n, item):\n    ptr = n + 1\n    while ptr > 1:\n        par = ptr // 2\n        if item <= A[par]:\n            A[ptr] = item\n            return\n        A[ptr] = A[par]\n        ptr = par\n    A[ptr] = item\n\ndef deleteHeap(A, n):\n    item = A[1]\n    last = A[n]\n    n -= 1\n    ptr = 1\n    while 2 * ptr <= n:\n        left = 2 * ptr\n        right = left + 1\n        child = right if (right <= n and A[right] > A[left]) else left\n        if last >= A[child]:\n            break\n        A[ptr] = A[child]\n        ptr = child\n    A[ptr] = last\n    return item\n\n";
        if ("Functions".equals(style)) {
            return helpers + "def buildHeap(A, n):\n    for j in range(2, n + 1):\n        insertHeap(A, j - 1, A[j])\n\ndef sortHeap(A, n):\n    size = n\n    while size > 1:\n        item = deleteHeap(A, size)\n        A[size] = item\n        size -= 1\n\nn = int(input())\nA = [0]*200\nfor i in range(1, n+1):\n    A[i] = int(input())\nbuildHeap(A, n)\nsortHeap(A, n)\nfor i in range(1, n+1):\n    print(A[i], end=\" \")";
        }
        if ("Recursion".equals(style)) {
            return helpers + "def deleteMaxRec(A, n):\n    if n <= 1:\n        return\n    item = deleteHeap(A, n)\n    A[n] = item\n    deleteMaxRec(A, n - 1)\n\nn = int(input())\nA = [0]*200\nfor i in range(1, n+1):\n    A[i] = int(input())\nfor j in range(2, n+1):\n    insertHeap(A, j - 1, A[j])\ndeleteMaxRec(A, n)\nfor i in range(1, n+1):\n    print(A[i], end=\" \")";
        }
        return helpers + "n = int(input())\nA = [0]*200\nfor i in range(1, n+1):\n    A[i] = int(input())\nfor j in range(2, n+1):\n    insertHeap(A, j - 1, A[j])\nsize = n\nwhile size > 1:\n    item = deleteHeap(A, size)\n    A[size] = item\n    size -= 1\nfor i in range(1, n+1):\n    print(A[i], end=\" \")";
    }

    private static String getPythonWarshallAlgorithm(String style) { return String.join("\n", "m = int(input())", "A = [list(map(int, input().split())) for _ in range(m)]", "P = [[0 if A[i][j] == 0 else 1 for j in range(m)] for i in range(m)]", "for k in range(m):", "    for i in range(m):", "        for j in range(m):", "            P[i][j] = P[i][j] or (P[i][k] and P[k][j])", "for row in P:", "    print(*row)"); }

    private static String getPythonFloydAlgorithm(String style) { return String.join("\n", "INF = 999999", "m = int(input())", "W = [list(map(int, input().split())) for _ in range(m)]", "Q = [[INF if W[i][j] == 0 else W[i][j] for j in range(m)] for i in range(m)]", "for k in range(m):", "    for i in range(m):", "        for j in range(m):", "            Q[i][j] = min(Q[i][j], Q[i][k] + Q[k][j])", "for row in Q:", "    print(*row)"); }

    private static String getPythonFindEdgeGraph(String style) { return String.join("\n", "n = int(input())", "graph = [list(map(int, input().split())) for _ in range(n)]", "a, b = map(int, input().split())", "print(\"Edge Found\" if 0 <= a < n and 0 <= b < n and graph[a][b] == 1 else \"Edge Not Found\")"); }

    private static String getPythonInsertNodeGraph(String style) { return String.join("\n", "n = int(input())", "node = list(map(int, input().split()))", "item = int(input())", "if item in node:", "    print(\"Node Exists\")", "else:", "    node.append(item)", "    print(\"Node Inserted\")", "    print(*node)"); }

    private static String getPythonInsertEdgeGraph(String style) { return String.join("\n", "n = int(input())", "graph = [list(map(int, input().split())) for _ in range(n)]", "a, b = map(int, input().split())", "if a < 0 or a >= n or b < 0 or b >= n:", "    print(\"Invalid Edge\")", "else:", "    graph[a][b] = 1", "    print(\"Edge Inserted\")"); }

    private static String getPythonDeleteEdgeGraph(String style) { return String.join("\n", "n = int(input())", "graph = [list(map(int, input().split())) for _ in range(n)]", "a, b = map(int, input().split())", "if a < 0 or a >= n or b < 0 or b >= n or graph[a][b] == 0:", "    print(\"Edge Not Found\")", "else:", "    graph[a][b] = 0", "    print(\"Edge Deleted\")"); }

    private static String getPythonDeleteVertexGraph(String style) { return String.join("\n", "n = int(input())", "graph = [list(map(int, input().split())) for _ in range(n)]", "target = int(input())", "if target < 0 or target >= n:", "    print(\"Node Not Found\")", "else:", "    for i in range(n):", "        graph[target][i] = 0", "        graph[i][target] = 0", "    print(\"Node Deleted\")"); }

    private static String getPythonBreadthFirstSearch(String style) { return String.join("\n", "from collections import deque", "n = int(input())", "graph = [list(map(int, input().split())) for _ in range(n)]", "start = int(input())", "status = [1] * n", "q = deque([start])", "status[start] = 2", "while q:", "    node = q.popleft()", "    print(node, end=' ')", "    status[node] = 3", "    for i in range(n):", "        if graph[node][i] == 1 and status[i] == 1:", "            q.append(i)", "            status[i] = 2"); }

    private static String getPythonDepthFirstSearch(String style) { return String.join("\n", "n = int(input())", "graph = [list(map(int, input().split())) for _ in range(n)]", "start = int(input())", "status = [1] * n", "stack = [start]", "status[start] = 2", "while stack:", "    node = stack.pop()", "    print(node, end=' ')", "    status[node] = 3", "    for i in range(n):", "        if graph[node][i] == 1 and status[i] == 1:", "            stack.append(i)", "            status[i] = 2"); }

    private static String getPythonTopologicalSort(String style) { return String.join("\n", "from collections import deque", "n = int(input())", "graph = [list(map(int, input().split())) for _ in range(n)]", "indeg = [0] * n", "for i in range(n):", "    for j in range(n):", "        if graph[i][j] == 1:", "            indeg[j] += 1", "q = deque(i for i in range(n) if indeg[i] == 0)", "while q:", "    node = q.popleft()", "    print(node, end=' ')", "    for j in range(n):", "        if graph[node][j] == 1:", "            indeg[j] -= 1", "            if indeg[j] == 0:", "                q.append(j)"); }

    private static String getPythonInsertionSortPdf(String style) { return String.join("\n", "n = int(input())", "A = [0] * (n + 1)", "A[0] = -99999", "for i in range(1, n + 1):", "    A[i] = int(input())", "for k in range(2, n + 1):", "    temp = A[k]", "    ptr = k - 1", "    while temp < A[ptr]:", "        A[ptr + 1] = A[ptr]", "        ptr -= 1", "    A[ptr + 1] = temp", "for i in range(1, n + 1):", "    print(A[i], end=' ')"); }

    private static String getPythonSelectionSortPdf(String style) { return String.join("\n", "n = int(input())", "A = [0] * (n + 1)", "for i in range(1, n + 1):", "    A[i] = int(input())", "for k in range(1, n):", "    min_val = A[k]", "    loc = k", "    for j in range(k + 1, n + 1):", "        if min_val > A[j]:", "            min_val = A[j]", "            loc = j", "    A[k], A[loc] = A[loc], A[k]", "for i in range(1, n + 1):", "    print(A[i], end=' ')"); }

    private static String getPythonMergingPdf(String style) { return String.join("\n", "R = int(input())", "A = [0] * (R + 1)", "for i in range(1, R + 1):", "    A[i] = int(input())", "S = int(input())", "B = [0] * (S + 1)", "for i in range(1, S + 1):", "    B[i] = int(input())", "C = [0] * (R + S + 1)", "NA, NB, PTR = 1, 1, 1", "while NA <= R and NB <= S:", "    if A[NA] < B[NB]:", "        C[PTR] = A[NA]", "        NA += 1", "    else:", "        C[PTR] = B[NB]", "        NB += 1", "    PTR += 1", "if NA > R:", "    for k in range(0, S - NB + 1):", "        C[PTR + k] = B[NB + k]", "else:", "    for k in range(0, R - NA + 1):", "        C[PTR + k] = A[NA + k]", "for i in range(1, R + S + 1):", "    print(C[i], end=' ')"); }

    private static String getPythonMergeSortPdf(String style) { return String.join("\n", "def merge(A, l1, s1, B, l2, s2, C, sc):", "    i, j, k = s1, s2, sc", "    while i < s1 + l1 and j < s2 + l2:", "        if A[i] < B[j]:", "            C[k] = A[i]", "            i += 1", "        else:", "            C[k] = B[j]", "            j += 1", "        k += 1", "    while i < s1 + l1:", "        C[k] = A[i]", "        i += 1", "        k += 1", "    while j < s2 + l2:", "        C[k] = B[j]", "        j += 1", "        k += 1", "def mergePass(A, n, L, B):", "    q = n // (2 * L)", "    s = 2 * L * q", "    i = 1", "    while i <= s:", "        merge(A, L, i, A, L, i + L, B, i)", "        i += 2 * L", "    if s + L < n:", "        merge(A, L, s + 1, A, n - s - L, s + L + 1, B, s + 1)", "    else:", "        for x in range(s + 1, n + 1):", "            B[x] = A[x]", "n = int(input())", "A = [0] * (n + 1)", "B = [0] * (n + 1)", "for i in range(1, n + 1):", "    A[i] = int(input())", "L = 1", "while L < n:", "    mergePass(A, n, L, B)", "    mergePass(B, n, 2 * L, A)", "    L = 4 * L", "for i in range(1, n + 1):", "    print(A[i], end=' ')"); }

    private static String getPythonRadixSortPdf(String style) { return String.join("\n", "n = int(input())", "A = [int(input()) for _ in range(n)]", "d = int(input())", "divisor = 1", "for k in range(1, d + 1):", "    Q = [[] for _ in range(10)]", "    for i in range(n):", "        digit = (A[i] // divisor) % 10", "        Q[digit].append(A[i])", "    index = 0", "    for i in range(10):", "        for num in Q[i]:", "            A[index] = num", "            index += 1", "    divisor *= 10", "for i in A:", "    print(i, end=' ')"); }

}
