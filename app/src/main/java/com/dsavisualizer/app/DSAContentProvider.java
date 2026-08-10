package com.dsavisualizer.app;

import java.util.Map;

/**
 * Reader content for the Lipschutz-aligned app.
 * Where OCR text was extracted from the user's PDF, the step flow mirrors the book.
 * Where OCR was not practical in this pass, the logic is written in the same compact book style.
 */
public class DSAContentProvider {

    public static void populate(Map<String, String[]> templates) {
        registerPatternMatching(templates);
        registerArrays(templates);
        registerLinkedLists(templates);
        registerStacksQueues(templates);
        registerTrees(templates);
        registerGraphs(templates);
        registerChapterNine(templates);
    }

    private static void registerPatternMatching(Map<String, String[]> templates) {
        register(
            templates,
            "Pattern Matching Algorithms",
            lines(
                "Algorithm 3.3: PATTERN_MATCH(P, R, T, S, INDEX)",
                "1. Set K := 1 and MAX := S - R + 1.",
                "2. Repeat while K <= MAX.",
                "3. Compare P[L] with T[K + L - 1] for L = 1 to R.",
                "4. If every comparison matches, set INDEX := K.",
                "5. Otherwise set K := K + 1.",
                "6. If no match occurs, set INDEX := 0."
            ),
            lines(
                "int patternMatch(String text, String pattern) {",
                "    int r = pattern.length();",
                "    int s = text.length();",
                "    int k = 0;",
                "    int max = s - r;",
                "",
                "    while (k <= max) {",
                "        int l = 0;",
                "        while (l < r && pattern.charAt(l) == text.charAt(k + l)) {",
                "            l++;",
                "        }",
                "        if (l == r) {",
                "            return k;",
                "        }",
                "        k++;",
                "    }",
                "    return -1;",
                "}"
            ),
            lines(
                "Algorithm 3.4: TABLE_DRIVEN_PATTERN_MATCH(F, P, T, INDEX)",
                "1. Set K := 1 and S1 := Q0.",
                "2. Read the next text character Tk.",
                "3. Set S(k + 1) := F(Sk, Tk).",
                "4. If the accepting state P is reached, report INDEX.",
                "5. Otherwise continue until the text ends."
            ),
            lines(
                "int tableDrivenMatch(String text, String pattern, int[][] next, int[] map) {",
                "    int state = 0;",
                "    for (int k = 0; k < text.length(); k++) {",
                "        state = next[state][map[text.charAt(k)]];",
                "        if (state == pattern.length()) {",
                "            return k - pattern.length() + 1;",
                "        }",
                "    }",
                "    return -1;",
                "}"
            ),
            lines(
                "Recursive Pattern Match",
                "1. Test the Kth alignment.",
                "2. If it fails, recur with K + 1.",
                "3. Stop when no alignment remains."
            ),
            lines(
                "int recursivePatternMatch(String text, String pattern, int k) {",
                "    if (k > text.length() - pattern.length()) return -1;",
                "    if (sameAt(text, pattern, k, 0)) return k;",
                "    return recursivePatternMatch(text, pattern, k + 1);",
                "}",
                "boolean sameAt(String text, String pattern, int k, int l) {",
                "    if (l == pattern.length()) return true;",
                "    if (text.charAt(k + l) != pattern.charAt(l)) return false;",
                "    return sameAt(text, pattern, k, l + 1);",
                "}"
            )
        );
    }

    private static void registerArrays(Map<String, String[]> templates) {
        register(
            templates,
            "Bubble Sort",
            lines(
                "Bubble Sort from the book",
                "1. Compare adjacent pairs and arrange them in order.",
                "2. After Pass 1, the largest element reaches the last position.",
                "3. Repeat with one fewer comparison on each pass.",
                "4. After N - 1 passes, the list is sorted."
            ),
            lines(
                "void bubbleSort(int[] a) {",
                "    for (int pass = a.length - 1; pass >= 1; pass--) {",
                "        for (int j = 0; j < pass; j++) {",
                "            if (a[j] > a[j + 1]) {",
                "                int temp = a[j];",
                "                a[j] = a[j + 1];",
                "                a[j + 1] = temp;",
                "            }",
                "        }",
                "    }",
                "}"
            ),
            lines(
                "Function PASS(A, LAST)",
                "1. Traverse the array from index 1 through LAST.",
                "2. Exchange any inverted adjacent pair."
            ),
            lines(
                "void pass(int[] a, int last) {",
                "    for (int j = 0; j < last; j++) {",
                "        if (a[j] > a[j + 1]) {",
                "            int temp = a[j];",
                "            a[j] = a[j + 1];",
                "            a[j + 1] = temp;",
                "        }",
                "    }",
                "}"
            ),
            lines(
                "Recursive Bubble Sort",
                "1. Perform one pass.",
                "2. Recur on the first N - 1 items."
            ),
            lines(
                "void bubbleRecursive(int[] a, int n) {",
                "    if (n <= 1) return;",
                "    pass(a, n - 1);",
                "    bubbleRecursive(a, n - 1);",
                "}"
            )
        );

        register(
            templates,
            "Linear Search",
            lines(
                "Algorithm 4.5: LINEAR(DATA, N, ITEM, LOC)",
                "1. Set DATA[N + 1] := ITEM.",
                "2. Set LOC := 1.",
                "3. Repeat while DATA[LOC] != ITEM: set LOC := LOC + 1.",
                "4. If LOC = N + 1, set LOC := 0.",
                "5. Exit."
            ),
            lines(
                "int linearSearchWithSentinel(int[] data, int n, int item) {",
                "    int[] work = new int[n + 1];",
                "    System.arraycopy(data, 0, work, 0, n);",
                "    work[n] = item; // Sentinel item from the book.",
                "    int loc = 0;",
                "    while (work[loc] != item) {",
                "        loc++;",
                "    }",
                "    return loc == n ? -1 : loc;",
                "}"
            ),
            lines(
                "Function FIND_FIRST(DATA, ITEM)",
                "1. Scan the array from left to right.",
                "2. Return the first matching location."
            ),
            lines(
                "int findFirst(int[] data, int item) {",
                "    for (int i = 0; i < data.length; i++) {",
                "        if (data[i] == item) return i;",
                "    }",
                "    return -1;",
                "}"
            ),
            lines(
                "Recursive Linear Search",
                "1. If the current position is past the last element, fail.",
                "2. If DATA[LOC] equals ITEM, succeed.",
                "3. Otherwise recur at LOC + 1."
            ),
            lines(
                "int linearRecursive(int[] data, int item, int loc) {",
                "    if (loc == data.length) return -1;",
                "    if (data[loc] == item) return loc;",
                "    return linearRecursive(data, item, loc + 1);",
                "}"
            )
        );

        register(
            templates,
            "Binary Search",
            lines(
                "Algorithm 4.6: BINARY(DATA, LB, UB, ITEM, LOC)",
                "1. Set BEG := LB, END := UB and MID := INT((BEG + END) / 2).",
                "2. Repeat while BEG <= END and DATA[MID] != ITEM.",
                "3. If ITEM < DATA[MID], set END := MID - 1; else set BEG := MID + 1.",
                "4. Set MID := INT((BEG + END) / 2).",
                "5. If DATA[MID] = ITEM, set LOC := MID; else set LOC := NULL."
            ),
            lines(
                "int binarySearchBookStyle(int[] data, int lb, int ub, int item) {",
                "    int beg = lb;",
                "    int end = ub;",
                "    int mid = (beg + end) / 2;",
                "    while (beg <= end && data[mid] != item) {",
                "        if (item < data[mid]) end = mid - 1;",
                "        else beg = mid + 1;",
                "        mid = (beg + end) / 2;",
                "    }",
                "    return data[mid] == item ? mid : -1;",
                "}"
            ),
            lines(
                "Function BSEARCH(DATA, ITEM)",
                "1. Call binary search on the full sorted segment."
            ),
            lines(
                "int bsearch(int[] data, int item) {",
                "    return binarySearchBookStyle(data, 0, data.length - 1, item);",
                "}"
            ),
            lines(
                "Recursive Binary Search",
                "1. Stop when LOW > HIGH.",
                "2. Compare ITEM with DATA[MID].",
                "3. Recur on the proper half."
            ),
            lines(
                "int binaryRecursive(int[] data, int low, int high, int item) {",
                "    if (low > high) return -1;",
                "    int mid = (low + high) / 2;",
                "    if (data[mid] == item) return mid;",
                "    if (item < data[mid]) return binaryRecursive(data, low, mid - 1, item);",
                "    return binaryRecursive(data, mid + 1, high, item);",
                "}"
            )
        );
    }

    private static void registerLinkedLists(Map<String, String[]> templates) {
        register(
            templates,
            "Singly Linked List",
            lines(
                "Singly Linked List",
                "1. Traverse by following LINK fields.",
                "2. Insert by changing a small number of links.",
                "3. Delete by bypassing the unwanted node."
            ),
            lines(
                "void insertFront(int value) {",
                "    Node node = new Node(value);",
                "    node.next = head;",
                "    head = node;",
                "}"
            ),
            lines(
                "Function INSERT_AFTER(P, ITEM)",
                "1. Create NEW.",
                "2. Set NEW.LINK := P.LINK.",
                "3. Set P.LINK := NEW."
            ),
            lines(
                "void insertAfter(Node p, int value) {",
                "    Node node = new Node(value);",
                "    node.next = p.next;",
                "    p.next = node;",
                "}"
            ),
            lines(
                "Recursive Traversal",
                "1. If P = NULL, stop.",
                "2. Visit P.",
                "3. Recur on P.LINK."
            ),
            lines(
                "void traverseRecursive(Node p) {",
                "    if (p == null) return;",
                "    visit(p.data);",
                "    traverseRecursive(p.next);",
                "}"
            )
        );

        register(
            templates,
            "Doubly Linked List",
            lines(
                "Two-way List",
                "1. Each node has a left link and a right link.",
                "2. Insertions and deletions repair both neighboring pointers."
            ),
            lines(
                "void insertRight(Node p, int value) {",
                "    Node node = new Node(value);",
                "    node.prev = p;",
                "    node.next = p.next;",
                "    if (p.next != null) p.next.prev = node;",
                "    p.next = node;",
                "}"
            ),
            lines(
                "Function DELETE_NODE(P)",
                "1. Set P.LEFT.RIGHT := P.RIGHT when P.LEFT exists.",
                "2. Set P.RIGHT.LEFT := P.LEFT when P.RIGHT exists."
            ),
            lines(
                "void deleteNode(Node p) {",
                "    if (p.prev != null) p.prev.next = p.next;",
                "    if (p.next != null) p.next.prev = p.prev;",
                "}"
            ),
            lines(
                "Recursive Reverse Traversal",
                "1. Visit the node.",
                "2. Recur on the left link."
            ),
            lines(
                "void reverseRecursive(Node p) {",
                "    if (p == null) return;",
                "    visit(p.data);",
                "    reverseRecursive(p.prev);",
                "}"
            )
        );
    }

    private static void registerStacksQueues(Map<String, String[]> templates) {
        register(
            templates,
            "Stack",
            lines("Stack", "1. PUSH adds at TOP.", "2. POP removes from TOP.", "3. The order is LIFO."),
            lines(
                "void push(int value) {",
                "    if (top == stack.length - 1) return;",
                "    stack[++top] = value;",
                "}",
                "int pop() {",
                "    return top == -1 ? -1 : stack[top--];",
                "}"
            ),
            lines("Function PEEK", "1. Return the current top item without removing it."),
            lines("int peek() {", "    return top == -1 ? -1 : stack[top];", "}"),
            lines("Recursive Reverse", "1. Pop one item.", "2. Recur on the reduced stack.", "3. Insert the saved item at the bottom."),
            lines(
                "void reverseRec(java.util.Stack<Integer> s) {",
                "    if (s.isEmpty()) return;",
                "    int item = s.pop();",
                "    reverseRec(s);",
                "    insertBottom(s, item);",
                "}"
            )
        );

        register(
            templates,
            "Quick Sort",
            lines("Quicksort", "1. Partition the active segment.", "2. Process the left and right parts.", "3. Stacks can store unfinished segments."),
            lines(
                "void quickSort(int[] a, int low, int high) {",
                "    if (low >= high) return;",
                "    int pivot = partition(a, low, high);",
                "    quickSort(a, low, pivot - 1);",
                "    quickSort(a, pivot + 1, high);",
                "}"
            ),
            lines("Function PARTITION", "1. Choose a pivot.", "2. Move smaller keys left and larger keys right.", "3. Return the final pivot location."),
            lines(
                "int partition(int[] a, int low, int high) {",
                "    int pivot = a[high];",
                "    int i = low - 1;",
                "    for (int j = low; j < high; j++) {",
                "        if (a[j] <= pivot) {",
                "            i++;",
                "            int temp = a[i]; a[i] = a[j]; a[j] = temp;",
                "        }",
                "    }",
                "    int temp = a[i + 1]; a[i + 1] = a[high]; a[high] = temp;",
                "    return i + 1;",
                "}"
            ),
            lines("Recursive Quicksort", "1. Stop when LOW >= HIGH.", "2. Otherwise partition and recur."),
            lines("void quickRecursive(int[] a, int low, int high) {", "    quickSort(a, low, high);", "}")
        );

        register(
            templates,
            "Tower of Hanoi",
            lines("Tower of Hanoi", "1. Move N - 1 disks to AUX.", "2. Move the largest disk.", "3. Move N - 1 disks to DESTINATION."),
            lines(
                "void hanoi(int n, char from, char aux, char to) {",
                "    if (n == 0) return;",
                "    hanoi(n - 1, from, to, aux);",
                "    move(from, to);",
                "    hanoi(n - 1, aux, from, to);",
                "}"
            ),
            lines("Function MOVE", "1. Output one legal disk move."),
            lines("void move(char from, char to) {", "    System.out.println(from + \" -> \" + to);", "}"),
            lines("Recursive Hanoi", "1. The problem is naturally recursive."),
            lines("void hanoiRecursive(int n, char from, char aux, char to) {", "    hanoi(n, from, aux, to);", "}")
        );

        register(
            templates,
            "Queue",
            lines("Queue", "1. INSERT at REAR.", "2. DELETE at FRONT.", "3. The order is FIFO."),
            lines(
                "void enqueue(int value) {",
                "    if (rear == queue.length - 1) return;",
                "    if (front == -1) front = 0;",
                "    queue[++rear] = value;",
                "}",
                "int dequeue() {",
                "    return (front == -1 || front > rear) ? -1 : queue[front++];",
                "}"
            ),
            lines("Function PEEK_FRONT", "1. Return the front element without deleting it."),
            lines("int peekFront() {", "    return (front == -1 || front > rear) ? -1 : queue[front];", "}"),
            lines("Recursive Queue Traversal", "1. Visit the front item.", "2. Recur on the remainder."),
            lines("void printQueue(int[] q, int front, int rear) {", "    if (front > rear) return;", "    visit(q[front]);", "    printQueue(q, front + 1, rear);", "}")
        );

        register(
            templates,
            "Deque",
            lines("Deque", "1. Insertions and deletions can happen at both ends.", "2. Circular indexing is common."),
            lines(
                "void addFront(int value) {",
                "    front = (front == 0) ? max - 1 : front - 1;",
                "    data[front] = value;",
                "}",
                "void addRear(int value) {",
                "    rear = (rear + 1) % max;",
                "    data[rear] = value;",
                "}"
            ),
            lines("Function REMOVE_FRONT / REMOVE_REAR", "1. Return the item at the selected end.", "2. Update the corresponding pointer."),
            lines("int removeFront() {", "    int value = data[front];", "    front = (front + 1) % max;", "    return value;", "}"),
            lines("Recursive Deque Traversal", "1. Visit the current cell.", "2. Recur on the next circular position."),
            lines("void traverseDeque(int index, int rear) {", "    visit(data[index]);", "    if (index == rear) return;", "    traverseDeque((index + 1) % max, rear);", "}")
        );

        register(
            templates,
            "Priority Queue",
            lines("Priority Queue", "1. Insert by priority order.", "2. Delete the highest-priority item."),
            lines(
                "void insertByPriority(int value) {",
                "    int i = size - 1;",
                "    while (i >= 0 && data[i] > value) {",
                "        data[i + 1] = data[i];",
                "        i--;",
                "    }",
                "    data[i + 1] = value;",
                "    size++;",
                "}"
            ),
            lines("Function DELETE_MIN", "1. Remove the first item of the ordered structure."),
            lines("int deleteMin() {", "    if (size == 0) return -1;", "    int value = data[0];", "    for (int i = 1; i < size; i++) data[i - 1] = data[i];", "    size--;", "    return value;", "}"),
            lines("Recursive Priority Insertion", "1. Shift larger items right.", "2. Recur until the correct place is found."),
            lines("void insertRec(int[] q, int n, int item) {", "    if (n == 0 || q[n - 1] <= item) { q[n] = item; return; }", "    q[n] = q[n - 1];", "    insertRec(q, n - 1, item);", "}")
        );
    }

    private static void registerTrees(Map<String, String[]> templates) {
        register(
            templates,
            "Traversing Binary Trees",
            lines("Traversing Binary Trees", "1. Inorder: LEFT, ROOT, RIGHT.", "2. Preorder: ROOT, LEFT, RIGHT.", "3. Postorder: LEFT, RIGHT, ROOT."),
            lines(
                "void inorder(Node root) {",
                "    if (root == null) return;",
                "    inorder(root.left);",
                "    visit(root.key);",
                "    inorder(root.right);",
                "}"
            ),
            lines("Function POSTORDER", "1. Traverse LEFT.", "2. Traverse RIGHT.", "3. Visit ROOT."),
            lines("void postorder(Node root) {", "    if (root == null) return;", "    postorder(root.left);", "    postorder(root.right);", "    visit(root.key);", "}"),
            lines("Recursive Traversal", "1. Every traversal reduces the task to the left and right subtrees."),
            lines("void preorder(Node root) {", "    if (root == null) return;", "    visit(root.key);", "    preorder(root.left);", "    preorder(root.right);", "}")
        );

        register(
            templates,
            "Traversal Algorithms using Stacks",
            lines("Traversal Algorithms using Stacks", "1. Push left descendants for inorder.", "2. Push ROOT and then RIGHT, LEFT for preorder.", "3. A second stack or visit marker can support postorder."),
            lines(
                "void inorderUsingStack(Node root) {",
                "    java.util.Stack<Node> stack = new java.util.Stack<>();",
                "    while (root != null || !stack.isEmpty()) {",
                "        while (root != null) { stack.push(root); root = root.left; }",
                "        root = stack.pop();",
                "        visit(root.key);",
                "        root = root.right;",
                "    }",
                "}"
            ),
            lines("Function PREORDER_USING_STACK", "1. Push ROOT.", "2. Pop and visit.", "3. Push RIGHT and LEFT."),
            lines("void preorderUsingStack(Node root) {", "    if (root == null) return;", "    java.util.Stack<Node> stack = new java.util.Stack<>();", "    stack.push(root);", "    while (!stack.isEmpty()) {", "        Node node = stack.pop();", "        visit(node.key);", "        if (node.right != null) stack.push(node.right);", "        if (node.left != null) stack.push(node.left);", "    }", "}"),
            lines("Recursive View", "1. The explicit stack stores the work that recursion would normally store implicitly."),
            lines("void explainStackTraversal() {", "    // Stack-based traversals simulate recursive call frames.", "}")
        );

        register(
            templates,
            "Binary Search Trees",
            lines("Binary Search Trees", "1. Keys in the left subtree are smaller.", "2. Keys in the right subtree are larger.", "3. Search follows one path from the root."),
            lines("class Node {", "    int key;", "    Node left;", "    Node right;", "    Node(int key) { this.key = key; }", "}"),
            lines("Function NEXT_BRANCH", "1. Compare KEY with ROOT.KEY.", "2. Move left, right, or stop."),
            lines("Node nextBranch(Node root, int key) {", "    if (key < root.key) return root.left;", "    if (key > root.key) return root.right;", "    return root;", "}"),
            lines("Recursive Search Idea", "1. Recur into the proper subtree according to the comparison."),
            lines("Node searchRec(Node root, int key) {", "    if (root == null || root.key == key) return root;", "    return key < root.key ? searchRec(root.left, key) : searchRec(root.right, key);", "}")
        );

        register(
            templates,
            "Searching and Inserting in Binary Search Trees",
            lines("Searching and Inserting in Binary Search Trees", "1. Compare ITEM with the current node.", "2. Move left or right.", "3. Insert at the first null link on the search path."),
            lines(
                "Node insert(Node root, int key) {",
                "    if (root == null) return new Node(key);",
                "    if (key < root.key) root.left = insert(root.left, key);",
                "    else if (key > root.key) root.right = insert(root.right, key);",
                "    return root;",
                "}"
            ),
            lines("Function SEARCH", "1. Follow one path from the root.", "2. Return the node or NULL."),
            lines("Node search(Node root, int key) {", "    while (root != null && root.key != key) root = key < root.key ? root.left : root.right;", "    return root;", "}"),
            lines("Recursive Insert", "1. Recur into the proper subtree.", "2. Create the node when the null link is reached."),
            lines("Node insertRec(Node root, int key) {", "    return insert(root, key);", "}")
        );

        register(
            templates,
            "Deleting in a Binary Search Tree",
            lines("Deleting in a Binary Search Tree", "1. Delete a leaf directly.", "2. Replace a one-child node by its child.", "3. Replace a two-child node by its inorder successor."),
            lines("Node delete(Node root, int key) {", "    if (root == null) return null;", "    if (key < root.key) root.left = delete(root.left, key);", "    else if (key > root.key) root.right = delete(root.right, key);", "    else if (root.left == null) return root.right;", "    else if (root.right == null) return root.left;", "    else {", "        Node succ = minNode(root.right);", "        root.key = succ.key;", "        root.right = delete(root.right, succ.key);", "    }", "    return root;", "}"),
            lines("Function MIN_NODE", "1. Follow left links until the minimum key is reached."),
            lines("Node minNode(Node p) {", "    while (p.left != null) p = p.left;", "    return p;", "}"),
            lines("Recursive Delete", "1. Search recursively.", "2. Repair links when the key is found."),
            lines("Node deleteRec(Node root, int key) {", "    return delete(root, key);", "}")
        );

        register(
            templates,
            "Heap; Heapsort",
            lines("Heap; Heapsort", "1. Build the heap.", "2. Exchange the root with the last heap element.", "3. Restore heap order and repeat."),
            lines("void heapSort(int[] a) {", "    for (int i = a.length / 2 - 1; i >= 0; i--) heapify(a, a.length, i);", "    for (int end = a.length - 1; end > 0; end--) {", "        int temp = a[0]; a[0] = a[end]; a[end] = temp;", "        heapify(a, end, 0);", "    }", "}"),
            lines("Function HEAPIFY", "1. Compare ROOT with its children.", "2. Promote the larger child.", "3. Continue until heap order holds."),
            lines("void heapify(int[] a, int size, int root) {", "    int largest = root, left = 2 * root + 1, right = 2 * root + 2;", "    if (left < size && a[left] > a[largest]) largest = left;", "    if (right < size && a[right] > a[largest]) largest = right;", "    if (largest != root) {", "        int temp = a[root]; a[root] = a[largest]; a[largest] = temp;", "        heapify(a, size, largest);", "    }", "}"),
            lines("Recursive Heap Repair", "1. Recur on the affected subtree."),
            lines("void heapifyRec(int[] a, int size, int root) {", "    heapify(a, size, root);", "}")
        );

        register(
            templates,
            "Path Lengths; Huffman's Algorithm",
            lines("Path Lengths; Huffman's Algorithm", "1. Begin with one weighted leaf for each symbol.", "2. Repeatedly combine the two least-weight trees.", "3. The final tree gives an optimal prefix code."),
            lines("Node huffman(java.util.PriorityQueue<Node> pq) {", "    while (pq.size() > 1) {", "        Node left = pq.poll();", "        Node right = pq.poll();", "        Node parent = new Node(left.weight + right.weight);", "        parent.left = left; parent.right = right;", "        pq.add(parent);", "    }", "    return pq.poll();", "}"),
            lines("Function WEIGHTED_PATH_LENGTH", "1. For every leaf, add WEIGHT * DEPTH."),
            lines("int weightedPathLength(Node root, int depth) {", "    if (root.left == null && root.right == null) return root.weight * depth;", "    return weightedPathLength(root.left, depth + 1) + weightedPathLength(root.right, depth + 1);", "}"),
            lines("Recursive Code Generation", "1. Append 0 on left edges and 1 on right edges.", "2. Emit the code at each leaf."),
            lines("void emitCodes(Node root, String prefix) {", "    if (root.left == null && root.right == null) {", "        System.out.println(root.symbol + \" : \" + prefix);", "        return;", "    }", "    emitCodes(root.left, prefix + \"0\");", "    emitCodes(root.right, prefix + \"1\");", "}")
        );
    }

    private static void registerGraphs(Map<String, String[]> templates) {
        register(
            templates,
            "Warshall's Algorithm; Shortest Paths",
            lines("Warshall's Algorithm", "1. Let K range over all vertices.", "2. For every pair (I, J), update P[I, J].", "3. Use P[I, J] := P[I, J] OR (P[I, K] AND P[K, J])."),
            lines("void warshall(int[][] p) {", "    for (int k = 0; k < p.length; k++) {", "        for (int i = 0; i < p.length; i++) {", "            for (int j = 0; j < p.length; j++) {", "                p[i][j] = p[i][j] | (p[i][k] & p[k][j]);", "            }", "        }", "    }", "}"),
            lines("Function RELAX", "1. For shortest paths, replace D[I, J] by min(D[I, J], D[I, K] + D[K, J])."),
            lines("void relax(int[][] d, int i, int j, int k) {", "    d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);", "}"),
            lines("Recursive Warshall Stage", "1. Update all pairs using vertex K.", "2. Recur with K + 1."),
            lines("void warshallRecursive(int[][] p, int k) {", "    if (k == p.length) return;", "    for (int i = 0; i < p.length; i++) for (int j = 0; j < p.length; j++) p[i][j] = p[i][j] | (p[i][k] & p[k][j]);", "    warshallRecursive(p, k + 1);", "}")
        );

        register(
            templates,
            "Traversing a Graph",
            lines("Traversing a Graph", "1. BFS uses a queue.", "2. DFS uses recursion or a stack.", "3. Mark each vertex when first visited."),
            lines("void bfs(int start) {", "    java.util.Queue<Integer> q = new java.util.LinkedList<>();", "    mark(start); q.add(start);", "    while (!q.isEmpty()) {", "        int u = q.poll();", "        visit(u);", "        for (int v : adj[u]) if (!marked(v)) { mark(v); q.add(v); }", "    }", "}"),
            lines("Function DFS(U)", "1. Mark and visit U.", "2. Recur on each unmarked adjacent vertex."),
            lines("void dfs(int u) {", "    mark(u); visit(u);", "    for (int v : adj[u]) if (!marked(v)) dfs(v);", "}"),
            lines("Recursive DFS", "1. DFS is naturally recursive."),
            lines("void dfsRecursive(int start) {", "    dfs(start);", "}")
        );

        register(
            templates,
            "Posets; Topological Sorting",
            lines("Topological Sorting", "1. Compute each in-degree.", "2. Queue all zero in-degree vertices.", "3. Remove one, output it, and reduce successor in-degrees."),
            lines("java.util.List<Integer> topologicalSort(Graph g) {", "    java.util.Queue<Integer> q = zeroInDegree(g);", "    java.util.List<Integer> order = new java.util.ArrayList<>();", "    while (!q.isEmpty()) {", "        int u = q.poll();", "        order.add(u);", "        for (int v : adj[u]) if (--indegree[v] == 0) q.add(v);", "    }", "    return order;", "}"),
            lines("Function ZERO_IN_DEGREE", "1. Collect every vertex whose in-degree is 0."),
            lines("java.util.Queue<Integer> zeroInDegree(Graph g) {", "    java.util.Queue<Integer> q = new java.util.LinkedList<>();", "    for (int v = 0; v < g.size(); v++) if (indegree[v] == 0) q.add(v);", "    return q;", "}"),
            lines("Recursive Topological Output", "1. Remove a zero in-degree vertex.", "2. Recur on the updated queue."),
            lines("void topologicalRecursive(java.util.Queue<Integer> q, java.util.List<Integer> order) {", "    if (q.isEmpty()) return;", "    order.add(q.poll());", "    topologicalRecursive(q, order);", "}")
        );

        register(
            templates,
            "Spanning Tree",
            lines("Spanning Tree", "1. Start from one vertex.", "2. Add only edges that reach a new vertex.", "3. Stop when all vertices are connected."),
            lines("java.util.List<Edge> spanningTree(int start) {", "    java.util.List<Edge> tree = new java.util.ArrayList<>();", "    java.util.Queue<Integer> q = new java.util.LinkedList<>();", "    mark(start); q.add(start);", "    while (!q.isEmpty()) {", "        int u = q.poll();", "        for (int v : adj[u]) if (!marked(v)) { mark(v); tree.add(new Edge(u, v)); q.add(v); }", "    }", "    return tree;", "}"),
            lines("Function TREE_EDGE", "1. Accept edge (U, V) only when V is new to the tree."),
            lines("boolean isTreeEdge(int v) {", "    return !marked(v);", "}"),
            lines("Recursive Tree Expansion", "1. Add a new vertex.", "2. Recur on that vertex."),
            lines("void buildTreeRec(int u, java.util.List<Edge> tree) {", "    mark(u);", "    for (int v : adj[u]) if (!marked(v)) { tree.add(new Edge(u, v)); buildTreeRec(v, tree); }", "}")
        );
    }

    private static void registerChapterNine(Map<String, String[]> templates) {
        register(
            templates,
            "Insertion Sort",
            lines("Algorithm 9.1: INSERTION(A, N)", "1. Set A[0] := -infinity.", "2. For K = 2 to N, store TEMP := A[K] and PTR := K - 1.", "3. Move larger items forward.", "4. Insert TEMP in its proper place."),
            lines("void insertionSortBook(int[] data) {", "    int[] a = new int[data.length + 1];", "    System.arraycopy(data, 0, a, 1, data.length);", "    a[0] = Integer.MIN_VALUE; // Sentinel from the book.", "    for (int k = 2; k <= data.length; k++) {", "        int temp = a[k];", "        int ptr = k - 1;", "        while (temp < a[ptr]) {", "            a[ptr + 1] = a[ptr];", "            ptr--;", "        }", "        a[ptr + 1] = temp;", "    }", "    System.arraycopy(a, 1, data, 0, data.length);", "}"),
            lines("Function INSERT(A, K, ITEM)", "1. Shift larger elements right.", "2. Store ITEM into the freed location."),
            lines("void insertIntoPrefix(int[] a, int k, int item) {", "    int j = k - 1;", "    while (j >= 0 && a[j] > item) {", "        a[j + 1] = a[j];", "        j--;", "    }", "    a[j + 1] = item;", "}"),
            lines("Recursive Insertion Sort", "1. Sort the first N - 1 items.", "2. Insert the Nth item into that sorted prefix."),
            lines("void insertionRecursive(int[] a, int n) {", "    if (n <= 1) return;", "    insertionRecursive(a, n - 1);", "    insertIntoPrefix(a, n - 1, a[n - 1]);", "}")
        );

        register(
            templates,
            "Selection Sort",
            lines("Procedure 9.2 and Algorithm 9.3", "1. Find the location LOC of the smallest remaining element.", "2. Interchange A[K] and A[LOC].", "3. Repeat for K = 1 to N - 1."),
            lines("void selectionSortBook(int[] a) {", "    for (int k = 0; k < a.length - 1; k++) {", "        int loc = k;", "        int min = a[k];", "        for (int j = k + 1; j < a.length; j++) {", "            if (min > a[j]) { min = a[j]; loc = j; }", "        }", "        int temp = a[k]; a[k] = a[loc]; a[loc] = temp;", "    }", "}"),
            lines("Function MINLOC", "1. Return the location of the smallest element from K onward."),
            lines("int minLoc(int[] a, int k) {", "    int loc = k;", "    for (int j = k + 1; j < a.length; j++) if (a[j] < a[loc]) loc = j;", "    return loc;", "}"),
            lines("Recursive Selection Sort", "1. Find the minimum of the current suffix.", "2. Swap and recur on the remaining suffix."),
            lines("void selectionRecursive(int[] a, int k) {", "    if (k >= a.length - 1) return;", "    int loc = minLoc(a, k);", "    int temp = a[k]; a[k] = a[loc]; a[loc] = temp;", "    selectionRecursive(a, k + 1);", "}")
        );

        register(
            templates,
            "Merging",
            lines("Algorithm 9.4: MERGING(A, R, B, S, C)", "1. Maintain pointers NA, NB and PTR.", "2. Copy the smaller front element of A or B into C.", "3. When one source finishes, copy the remainder of the other source."),
            lines("int[] mergeBook(int[] a, int[] b) {", "    int na = 0, nb = 0, ptr = 0;", "    int[] c = new int[a.length + b.length];", "    while (na < a.length && nb < b.length) {", "        c[ptr++] = (a[na] < b[nb]) ? a[na++] : b[nb++];", "    }", "    while (na < a.length) c[ptr++] = a[na++];", "    while (nb < b.length) c[ptr++] = b[nb++];", "    return c;", "}"),
            lines("Function MERGE(A, B)", "1. Keep one pointer in each input array and one in the output array."),
            lines("int[] mergeRuns(int[] left, int[] right) {", "    return mergeBook(left, right);", "}"),
            lines("Recursive Merge", "1. Copy one smaller front element.", "2. Recur on the unfinished suffixes."),
            lines("void mergeRecursive(int[] a, int i, int[] b, int j, int[] c, int k) {", "    if (i == a.length) { while (j < b.length) c[k++] = b[j++]; return; }", "    if (j == b.length) { while (i < a.length) c[k++] = a[i++]; return; }", "    if (a[i] < b[j]) c[k] = a[i++]; else c[k] = b[j++];", "    mergeRecursive(a, i, b, j, c, k + 1);", "}")
        );

        register(
            templates,
            "Merge-Sort",
            lines("Procedure 9.6 and Algorithm 9.7", "1. MERGEPASS merges adjacent runs of length L.", "2. MERGESORT repeats merge passes while L < N.", "3. An auxiliary array is used throughout."),
            lines("void mergeSort(int[] a, int left, int right) {", "    if (left >= right) return;", "    int mid = (left + right) / 2;", "    mergeSort(a, left, mid);", "    mergeSort(a, mid + 1, right);", "    mergeInto(a, left, mid, right);", "}"),
            lines("Function MERGEPASS", "1. Execute one complete merge pass across the array."),
            lines("void mergePass(int[] source, int[] target, int run) {", "    // Pair adjacent runs of length run and merge them into target.", "}"),
            lines("Recursive Merge-Sort", "1. Split the array.", "2. Recur on both halves.", "3. Merge the sorted halves."),
            lines("void mergeSortRecursive(int[] a, int left, int right) {", "    mergeSort(a, left, right);", "}")
        );

        register(
            templates,
            "Radix Sort",
            lines("Radix Sort", "1. Sort by the least significant digit.", "2. Repeat on the next more significant digit.", "3. Keep each digit pass stable."),
            lines("void radixSort(int[] a) {", "    int max = maxValue(a);", "    for (int exp = 1; max / exp > 0; exp *= 10) {", "        countSortByDigit(a, exp);", "    }", "}"),
            lines("Function COUNT_SORT_BY_DIGIT", "1. Group keys by one digit position.", "2. Rebuild the list in stable order."),
            lines("void countSortByDigit(int[] a, int exp) {", "    // Stable counting sort for the digit at EXP.", "}"),
            lines("Recursive Radix Passes", "1. Apply the current digit pass.", "2. Recur with the next higher power of 10."),
            lines("void radixRecursive(int[] a, int exp, int max) {", "    if (max / exp == 0) return;", "    countSortByDigit(a, exp);", "    radixRecursive(a, exp * 10, max);", "}")
        );

        register(
            templates,
            "Hashing",
            lines("Hashing", "1. Compute H(KEY).", "2. If the home slot is full, resolve the collision.", "3. Linear probing examines successive circular positions."),
            lines("int insertLinearProbe(int[] table, int key) {", "    int h = key % table.length;", "    while (table[h] != -1) {", "        h = (h + 1) % table.length;", "    }", "    table[h] = key;", "    return h;", "}"),
            lines("Function H(KEY)", "1. Division method: H(k) := k mod m.", "2. Midsquare and folding are other book methods."),
            lines("int hash(int key, int m) {", "    return key % m;", "}"),
            lines("Recursive Probe", "1. If the slot is empty, fail or insert there.", "2. If the key matches, succeed.", "3. Otherwise recur on the next circular slot."),
            lines("int probeRecursive(int[] table, int key, int slot, int checked) {", "    if (checked == table.length || table[slot] == -1) return -1;", "    if (table[slot] == key) return slot;", "    return probeRecursive(table, key, (slot + 1) % table.length, checked + 1);", "}")
        );
    }

    private static void register(
        Map<String, String[]> templates,
        String mode,
        String[] iterativeLogic,
        String[] iterativeCode,
        String[] functionLogic,
        String[] functionCode,
        String[] recursionLogic,
        String[] recursionCode
    ) {
        put(templates, mode, "Logic", "Iterative", iterativeLogic);
        put(templates, mode, "Code", "Iterative", iterativeCode);
        put(templates, mode, "Logic", "Function", functionLogic);
        put(templates, mode, "Code", "Function", functionCode);
        put(templates, mode, "Logic", "Recursion", recursionLogic);
        put(templates, mode, "Code", "Recursion", recursionCode);
    }

    private static void put(Map<String, String[]> templates, String mode, String viewType, String style, String[] lines) {
        templates.put(mode + "_" + viewType + "_" + style, lines);
    }

    private static String[] lines(String... lines) {
        return lines;
    }
}
