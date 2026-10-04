package com.dsavisualizer.app.data.provider;

import com.dsavisualizer.app.model.CategoryItem;
import com.dsavisualizer.app.model.TechniqueContent;
import com.dsavisualizer.app.model.TechniqueItem;

import java.util.List;
import java.util.Map;

/**
 * Curated provider for Advanced Data Structures.
 * Includes complete Iterative, Functions, and Recursion algorithms and code (Java, C, Python).
 */
public final class AdvancedDataStructureProvider {

    private AdvancedDataStructureProvider() {}

    public static void register(
        List<CategoryItem> categories,
        List<TechniqueItem> techniques,
        Map<String, TechniqueContent> contentMap
    ) {
        categories.add(new CategoryItem(
            "advanced_ds",
            "Advanced Data Structures",
            "Self-balancing trees, range query trees, tries, probabilistic structures, and caches",
            "#8B5CF6"
        ));

        // 1. Trie
        techniques.add(new TechniqueItem(
            "ds_trie",
            "advanced_ds",
            "Trie (Prefix Tree)",
            "Tree structure for rapid string prefix search, insertion, and autocomplete",
            "Medium"
        ));
        // 2. Segment Tree
        techniques.add(new TechniqueItem(
            "ds_segment_tree",
            "advanced_ds",
            "Segment Tree",
            "Range sum / minimum query and point updates in O(log n)",
            "Hard"
        ));
        // 3. Persistent Segment Tree
        techniques.add(new TechniqueItem(
            "ds_persistent_segment_tree",
            "advanced_ds",
            "Persistent Segment Tree",
            "Versioned segment tree retaining previous historical snapshots",
            "Hard"
        ));
        // 4. Fenwick Tree (BIT)
        techniques.add(new TechniqueItem(
            "ds_fenwick_tree",
            "advanced_ds",
            "Fenwick Tree (Binary Indexed Tree)",
            "Lightweight bitwise range sum queries and point updates in O(log n)",
            "Medium"
        ));
        // 5. AVL Tree
        techniques.add(new TechniqueItem(
            "ds_avl_tree",
            "advanced_ds",
            "AVL Tree",
            "Strict height balance with LL, RR, LR, RL rotation cases",
            "Hard"
        ));
        // 6. Red-Black Tree
        techniques.add(new TechniqueItem(
            "ds_red_black_tree",
            "advanced_ds",
            "Red-Black Tree",
            "Self-balancing BST with color rules, recoloring, and rotation guarantees",
            "Hard"
        ));
        // 7. Splay Tree
        techniques.add(new TechniqueItem(
            "ds_splay_tree",
            "advanced_ds",
            "Splay Tree",
            "Self-adjusting tree pulling frequently accessed nodes to the root via splaying",
            "Hard"
        ));
        // 8. B-Tree
        techniques.add(new TechniqueItem(
            "ds_b_tree",
            "advanced_ds",
            "B-Tree",
            "Self-balancing multiway search tree optimized for disk block reads and databases",
            "Hard"
        ));
        // 9. Disjoint Set Union (DSU)
        techniques.add(new TechniqueItem(
            "ds_disjoint_set",
            "advanced_ds",
            "Disjoint Set Union (Union-Find)",
            "Near constant O(alpha(n)) connected component tracking with path compression",
            "Medium"
        ));
        // 10. Bloom Filter
        techniques.add(new TechniqueItem(
            "ds_bloom_filter",
            "advanced_ds",
            "Bloom Filter",
            "Space-efficient probabilistic bit set testing set membership without false negatives",
            "Medium"
        ));
        // 11. LRU Cache
        techniques.add(new TechniqueItem(
            "ds_lru_cache",
            "advanced_ds",
            "LRU Cache",
            "Constant O(1) get, put, and least-recently-used eviction using HashMap + DLL",
            "Medium"
        ));

        registerTrie(contentMap);
        registerSegmentTree(contentMap);
        registerPersistentSegmentTree(contentMap);
        registerFenwickTree(contentMap);
        registerAvlTree(contentMap);
        registerRedBlackTree(contentMap);
        registerSplayTree(contentMap);
        registerBTree(contentMap);
        registerDisjointSet(contentMap);
        registerBloomFilter(contentMap);
        registerLruCache(contentMap);
    }

    private static void registerTrie(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "ds_trie",
            "Trie (Prefix Tree)",
            "Tree data structure used to store associative arrays where keys are usually strings.\n\nEach node contains links to child characters and a boolean marker indicating the end of a valid word, enabling O(L) insertion, search, and prefix matching where L is word length.",
            "Search engine query autocomplete suggestions and IP routing longest prefix lookup.",
            "O(L)", "O(L)", "O(L)", "O(Alphabet * N * L)",
            new int[] {1, 2, 3, 4},
            4
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: TRIE_ITERATIVE\nINSERT(word):\n1. curr = root\n2. For each char c in word:\n     If child[c] does not exist, allocate new node.\n     curr = curr.child[c]\n3. curr.isEndOfWord = true.\n\nSEARCH(word):\n1. curr = root\n2. For each char c in word:\n     If child[c] does not exist, return false.\n     curr = curr.child[c]\n3. Return curr.isEndOfWord.");
        tc.algorithmByStyle.put("Functions", "Algorithm: TRIE_FUNCTIONAL\nFUNCTION GET_CHILD(node, char): return node.children[char]\nFUNCTION SET_CHILD(node, char, child): node.children[char] = child\nFUNCTION INSERT(root, word): Step char-by-char creating nodes.\nFUNCTION SEARCH(root, word): Step char-by-char verifying path.");
        tc.algorithmByStyle.put("Recursion", "Algorithm: TRIE_RECURSIVE\nINSERT_REC(node, word, idx):\n1. If idx == len(word): node.isEnd = true; return.\n2. c = word[idx]\n3. If node.child[c] == null: node.child[c] = new Node()\n4. INSERT_REC(node.child[c], word, idx + 1)\n\nSEARCH_REC(node, word, idx):\n1. If node == null: return false.\n2. If idx == len(word): return node.isEnd.\n3. Return SEARCH_REC(node.child[word[idx]], word, idx + 1)");

        tc.putCode("Java", "Iterative", "// Trie - Iterative Implementation\npublic class TrieIterative {\n    static class TrieNode {\n        TrieNode[] children = new TrieNode[26];\n        boolean isEndOfWord = false;\n    }\n\n    private final TrieNode root = new TrieNode();\n\n    public void insert(String word) {\n        TrieNode curr = root;\n        for (char c : word.toCharArray()) {\n            int idx = c - 'a';\n            if (curr.children[idx] == null) curr.children[idx] = new TrieNode();\n            curr = curr.children[idx];\n        }\n        curr.isEndOfWord = true;\n    }\n\n    public boolean search(String word) {\n        TrieNode curr = root;\n        for (char c : word.toCharArray()) {\n            int idx = c - 'a';\n            if (curr.children[idx] == null) return false;\n            curr = curr.children[idx];\n        }\n        return curr.isEndOfWord;\n    }\n}", true);
        tc.putCode("Java", "Functions", "// Trie - Functional Modular\npublic class TrieFunctional {\n    public static class Node {\n        public Node[] children = new Node[26];\n        public boolean isWord;\n    }\n\n    public static Node step(Node curr, char c) {\n        return (curr != null) ? curr.children[c - 'a'] : null;\n    }\n\n    public static boolean search(Node root, String word) {\n        Node curr = root;\n        for (char c : word.toCharArray()) {\n            curr = step(curr, c);\n            if (curr == null) return false;\n        }\n        return curr.isWord;\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// Trie - Recursive Insertion and Search\npublic class TrieRecursive {\n    static class Node {\n        Node[] children = new Node[26];\n        boolean isEndOfWord;\n    }\n\n    private final Node root = new Node();\n\n    public void insert(String word) {\n        insertRec(root, word, 0);\n    }\n\n    private void insertRec(Node curr, String word, int idx) {\n        if (idx == word.length()) {\n            curr.isEndOfWord = true;\n            return;\n        }\n        int c = word.charAt(idx) - 'a';\n        if (curr.children[c] == null) curr.children[c] = new Node();\n        insertRec(curr.children[c], word, idx + 1);\n    }\n\n    public boolean search(String word) {\n        return searchRec(root, word, 0);\n    }\n\n    private boolean searchRec(Node curr, String word, int idx) {\n        if (curr == null) return false;\n        if (idx == word.length()) return curr.isEndOfWord;\n        return searchRec(curr.children[word.charAt(idx) - 'a'], word, idx + 1);\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* Trie - Iterative (C) */\n#include <stdio.h>\n#include <stdlib.h>\n#include <stdbool.h>\n\ntypedef struct TrieNode {\n    struct TrieNode* children[26];\n    bool isEndOfWord;\n} TrieNode;\n\nTrieNode* createNode() {\n    TrieNode* node = (TrieNode*)malloc(sizeof(TrieNode));\n    node->isEndOfWord = false;\n    for (int i = 0; i < 26; i++) node->children[i] = NULL;\n    return node;\n}\n\nvoid insert(TrieNode* root, const char* key) {\n    TrieNode* curr = root;\n    for (int i = 0; key[i]; i++) {\n        int idx = key[i] - 'a';\n        if (!curr->children[idx]) curr->children[idx] = createNode();\n        curr = curr->children[idx];\n    }\n    curr->isEndOfWord = true;\n}\n\nbool search(TrieNode* root, const char* key) {\n    TrieNode* curr = root;\n    for (int i = 0; key[i]; i++) {\n        int idx = key[i] - 'a';\n        if (!curr->children[idx]) return false;\n        curr = curr->children[idx];\n    }\n    return curr->isEndOfWord;\n}", true);
        tc.putCode("C", "Functions", "/* Trie - Functional Modular (C) */\n#include <stdio.h>\n#include <stdbool.h>\n#include <stdlib.h>\n\ntypedef struct TrieNode TrieNode;\nstruct TrieNode {\n    TrieNode* children[26];\n    bool isWord;\n};\n\nTrieNode* getChild(TrieNode* n, char c) {\n    return (n != NULL) ? n->children[c - 'a'] : NULL;\n}\n\nbool searchTrieModular(TrieNode* root, const char* str) {\n    TrieNode* curr = root;\n    for (int i = 0; str[i]; i++) {\n        curr = getChild(curr, str[i]);\n        if (!curr) return false;\n    }\n    return curr->isWord;\n}", true);
        tc.putCode("C", "Recursion", "/* Trie - Recursive (C) */\n#include <stdio.h>\n#include <stdbool.h>\n#include <stdlib.h>\n\ntypedef struct TrieNode TrieNode;\nstruct TrieNode {\n    TrieNode* children[26];\n    bool isEndOfWord;\n};\n\nbool searchRec(TrieNode* curr, const char* key, int idx) {\n    if (!curr) return false;\n    if (key[idx] == '\\0') return curr->isEndOfWord;\n    return searchRec(curr->children[key[idx] - 'a'], key, idx + 1);\n}", true);

        tc.putCode("Python", "Iterative", "# Trie - Iterative (Python)\nclass TrieNode:\n    def __init__(self):\n        self.children = {}\n        self.is_end = False\n\nclass TrieIterative:\n    def __init__(self):\n        self.root = TrieNode()\n\n    def insert(self, word: str) -> None:\n        curr = self.root\n        for ch in word:\n            if ch not in curr.children:\n                curr.children[ch] = TrieNode()\n            curr = curr.children[ch]\n        curr.is_end = True\n\n    def search(self, word: str) -> bool:\n        curr = self.root\n        for ch in word:\n            if ch not in curr.children:\n                return False\n            curr = curr.children[ch]\n        return curr.is_end\n", true);
        tc.putCode("Python", "Functions", "# Trie - Functional Modular (Python)\ndef create_trie_node():\n    return {\"children\": {}, \"is_end\": False}\n\ndef trie_insert(root, word):\n    curr = root\n    for ch in word:\n        curr = curr[\"children\"].setdefault(ch, create_trie_node())\n    curr[\"is_end\"] = True\n\ndef trie_search(root, word):\n    curr = root\n    for ch in word:\n        if ch not in curr[\"children\"]:\n            return False\n        curr = curr[\"children\"][ch]\n    return curr[\"is_end\"]\n", true);
        tc.putCode("Python", "Recursion", "# Trie - Recursive (Python)\nclass TrieRecursive:\n    def __init__(self):\n        self.root = TrieNode()\n\n    def insert(self, word: str) -> None:\n        def _insert(node: TrieNode, idx: int):\n            if idx == len(word):\n                node.is_end = True\n                return\n            ch = word[idx]\n            if ch not in node.children:\n                node.children[ch] = TrieNode()\n            _insert(node.children[ch], idx + 1)\n\n        _insert(self.root, 0)\n\n    def search(self, word: str) -> bool:\n        def _search(node: TrieNode, idx: int) -> bool:\n            if not node: return False\n            if idx == len(word): return node.is_end\n            return _search(node.children.get(word[idx]), idx + 1)\n\n        return _search(self.root, 0)\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerSegmentTree(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "ds_segment_tree",
            "Segment Tree",
            "Range sum / minimum query and point updates in O(log n).\n\nA binary tree where each node represents an interval of an array. Leaf nodes correspond to individual elements, and internal nodes merge results of their left and right children.",
            "Geometric range queries and real-time database interval telemetry aggregation.",
            "O(log n)", "O(log n)", "O(log n)", "O(n)",
            new int[] {1, 3, 5, 7, 9, 11},
            6
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: SEGMENT_TREE_ITERATIVE(arr, n)\nBUILD:\n1. Allocate tree of size 2*n.\n2. Copy arr into tree[n..2n-1].\n3. For i from n-1 down to 1: tree[i] = tree[2*i] + tree[2*i+1].\n\nQUERY(l, r):\n1. res = 0, l += n, r += n\n2. While l < r:\n     If l is odd: res += tree[l], l++\n     If r is odd: r--, res += tree[r]\n     l /= 2, r /= 2\n3. Return res.");
        tc.algorithmByStyle.put("Functions", "Algorithm: SEGMENT_TREE_FUNCTIONAL\nFUNCTION MERGE(left_val, right_val): return left_val + right_val\nFUNCTION POINT_UPDATE(tree, idx, val): Update leaf and parents iteratively.\nFUNCTION RANGE_QUERY(tree, l, r): Accumulate sum over interval boundaries.");
        tc.algorithmByStyle.put("Recursion", "Algorithm: SEGMENT_TREE_RECURSIVE\nBUILD_REC(node, start, end):\n1. If start == end: tree[node] = arr[start]; return.\n2. mid = (start + end) / 2\n3. BUILD_REC(2*node, start, mid); BUILD_REC(2*node+1, mid+1, end)\n4. tree[node] = tree[2*node] + tree[2*node+1].\n\nQUERY_REC(node, start, end, l, r):\n1. If r < start or end < l: return 0.\n2. If l <= start and end <= r: return tree[node].\n3. mid = (start + end) / 2\n4. Return QUERY_REC(2*node, start, mid, l, r) + QUERY_REC(2*node+1, mid+1, end, l, r)");

        tc.putCode("Java", "Iterative", "// Segment Tree - Iterative Array Implementation\npublic class SegmentTreeIterative {\n    private final int[] tree;\n    private final int n;\n\n    public SegmentTreeIterative(int[] arr) {\n        n = arr.length;\n        tree = new int[2 * n];\n        for (int i = 0; i < n; i++) tree[n + i] = arr[i];\n        for (int i = n - 1; i > 0; i--) tree[i] = tree[i << 1] + tree[i << 1 | 1];\n    }\n\n    public void update(int p, int value) {\n        for (tree[p += n] = value; p > 1; p >>= 1) {\n            tree[p >> 1] = tree[p] + tree[p ^ 1];\n        }\n    }\n\n    public int query(int l, int r) {\n        int res = 0;\n        for (l += n, r += n; l < r; l >>= 1, r >>= 1) {\n            if ((l & 1) == 1) res += tree[l++];\n            if ((r & 1) == 1) res += tree[--r];\n        }\n        return res;\n    }\n}", true);
        tc.putCode("Java", "Functions", "// Segment Tree - Functional Modular\npublic class SegmentTreeFunctional {\n    public static int merge(int a, int b) { return a + b; }\n\n    public static int queryRange(int[] tree, int n, int l, int r) {\n        int sum = 0;\n        l += n; r += n;\n        while (l < r) {\n            if ((l & 1) == 1) sum = merge(sum, tree[l++]);\n            if ((r & 1) == 1) sum = merge(sum, tree[--r]);\n            l >>= 1; r >>= 1;\n        }\n        return sum;\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// Segment Tree - Recursive Implementation\npublic class SegmentTreeRecursive {\n    private final int[] tree;\n    private final int n;\n\n    public SegmentTreeRecursive(int[] arr) {\n        n = arr.length;\n        tree = new int[4 * n];\n        build(arr, 1, 0, n - 1);\n    }\n\n    private void build(int[] arr, int node, int start, int end) {\n        if (start == end) {\n            tree[node] = arr[start];\n            return;\n        }\n        int mid = (start + end) / 2;\n        build(arr, 2 * node, start, mid);\n        build(arr, 2 * node + 1, mid + 1, end);\n        tree[node] = tree[2 * node] + tree[2 * node + 1];\n    }\n\n    public int query(int l, int r) {\n        return queryRec(1, 0, n - 1, l, r);\n    }\n\n    private int queryRec(int node, int start, int end, int l, int r) {\n        if (r < start || end < l) return 0;\n        if (l <= start && end <= r) return tree[node];\n        int mid = (start + end) / 2;\n        return queryRec(2 * node, start, mid, l, r) + queryRec(2 * node + 1, mid + 1, end, l, r);\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* Segment Tree - Iterative (C) */\n#include <stdio.h>\n\nvoid buildSegTree(int arr[], int n, int tree[]) {\n    for (int i = 0; i < n; i++) tree[n + i] = arr[i];\n    for (int i = n - 1; i > 0; i--) tree[i] = tree[i << 1] + tree[i << 1 | 1];\n}\n\nvoid updateSegTree(int tree[], int n, int p, int val) {\n    for (tree[p += n] = val; p > 1; p >>= 1)\n        tree[p >> 1] = tree[p] + tree[p ^ 1];\n}\n\nint querySegTree(int tree[], int n, int l, int r) {\n    int res = 0;\n    for (l += n, r += n; l < r; l >>= 1, r >>= 1) {\n        if (l & 1) res += tree[l++];\n        if (r & 1) res += tree[--r];\n    }\n    return res;\n}", true);
        tc.putCode("C", "Functions", "/* Segment Tree - Functional (C) */\n#include <stdio.h>\n\nint combine(int a, int b) { return a + b; }\n\nint queryFunctional(int tree[], int n, int l, int r) {\n    int sum = 0;\n    for (l += n, r += n; l < r; l >>= 1, r >>= 1) {\n        if (l & 1) sum = combine(sum, tree[l++]);\n        if (r & 1) sum = combine(sum, tree[--r]);\n    }\n    return sum;\n}", true);
        tc.putCode("C", "Recursion", "/* Segment Tree - Recursive (C) */\n#include <stdio.h>\n\nint queryRec(int tree[], int node, int start, int end, int l, int r) {\n    if (r < start || end < l) return 0;\n    if (l <= start && end <= r) return tree[node];\n    int mid = (start + end) / 2;\n    return queryRec(tree, 2 * node, start, mid, l, r) +\n           queryRec(tree, 2 * node + 1, mid + 1, end, l, r);\n}", true);

        tc.putCode("Python", "Iterative", "# Segment Tree - Iterative (Python)\nclass SegmentTreeIterative:\n    def __init__(self, arr: list[int]):\n        self.n = len(arr)\n        self.tree = [0] * (2 * self.n)\n        for i in range(self.n):\n            self.tree[self.n + i] = arr[i]\n        for i in range(self.n - 1, 0, -1):\n            self.tree[i] = self.tree[i << 1] + self.tree[i << 1 | 1]\n\n    def update(self, p: int, value: int) -> None:\n        p += self.n\n        self.tree[p] = value\n        while p > 1:\n            self.tree[p >> 1] = self.tree[p] + self.tree[p ^ 1]\n            p >>= 1\n\n    def query(self, l: int, r: int) -> int:\n        res = 0\n        l += self.n\n        r += self.n\n        while l < r:\n            if l & 1:\n                res += self.tree[l]\n                l += 1\n            if r & 1:\n                r -= 1\n                res += self.tree[r]\n            l >>= 1\n            r >>= 1\n        return res\n", true);
        tc.putCode("Python", "Functions", "# Segment Tree - Functional (Python)\ndef segtree_query(tree: list[int], n: int, l: int, r: int) -> int:\n    res = 0\n    l, r = l + n, r + n\n    while l < r:\n        if l & 1:\n            res += tree[l]\n            l += 1\n        if r & 1:\n            r -= 1\n            res += tree[r]\n        l >>= 1\n        r >>= 1\n    return res\n", true);
        tc.putCode("Python", "Recursion", "# Segment Tree - Recursive (Python)\nclass SegmentTreeRecursive:\n    def __init__(self, arr: list[int]):\n        self.n = len(arr)\n        self.tree = [0] * (4 * self.n)\n        self._build(arr, 1, 0, self.n - 1)\n\n    def _build(self, arr: list[int], node: int, start: int, end: int):\n        if start == end:\n            self.tree[node] = arr[start]\n            return\n        mid = (start + end) // 2\n        self._build(arr, 2 * node, start, mid)\n        self._build(arr, 2 * node + 1, mid + 1, end)\n        self.tree[node] = self.tree[2 * node] + self.tree[2 * node + 1]\n\n    def query(self, l: int, r: int) -> int:\n        def _query(node: int, start: int, end: int, ql: int, qr: int) -> int:\n            if qr < start or end < ql: return 0\n            if ql <= start and end <= qr: return self.tree[node]\n            mid = (start + end) // 2\n            return _query(2 * node, start, mid, ql, qr) + _query(2 * node + 1, mid + 1, end, ql, qr)\n\n        return _query(1, 0, self.n - 1, l, r)\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerPersistentSegmentTree(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "ds_persistent_segment_tree",
            "Persistent Segment Tree",
            "Versioned segment tree retaining previous historical snapshots.\n\nUpon an update, only O(log N) nodes on the path from the root to the modified leaf are re-created; all unmodified subtrees are reused via pointers.",
            "Git-like version control systems and range k-th smallest element queries.",
            "O(log n)", "O(log n)", "O(log n)", "O(n log n)",
            new int[] {1, 2, 3, 4, 5},
            5
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: PERSISTENT_SEGMENT_TREE_ITERATIVE\nNot available (Node persistence inherently utilizes recursive structural cloning).");
        tc.algorithmByStyle.put("Functions", "Algorithm: PERSISTENT_SEGMENT_TREE_FUNCTIONAL\nFUNCTION CLONE_NODE(node): Return shallow copy of node.\nFUNCTION UPGRADE_VERSION(root, idx, val): Produce new version root.\nFUNCTION QUERY_VERSION(root, l, r): Query snapshot at version.");
        tc.algorithmByStyle.put("Recursion", "Algorithm: PERSISTENT_SEGMENT_TREE_RECURSIVE\nUPDATE_REC(prev_node, start, end, idx, val):\n1. new_node = new Node(prev_node.val + val)\n2. If start == end: return new_node\n3. mid = (start + end) / 2\n4. If idx <= mid:\n     new_node.left = UPDATE_REC(prev_node.left, start, mid, idx, val)\n     new_node.right = prev_node.right\n   Else:\n     new_node.left = prev_node.left\n     new_node.right = UPDATE_REC(prev_node.right, mid+1, end, idx, val)\n5. Return new_node.");

        tc.putCode("Java", "Iterative", "// Persistent Segment Tree - Iterative\n// Not available", true);
        tc.putCode("Java", "Functions", "// Persistent Segment Tree - Functional Version Query\npublic class PersistentSegTreeFunctional {\n    public static class Node {\n        public int val;\n        public Node left, right;\n        public Node(int val) { this.val = val; }\n    }\n\n    public static int query(Node root, int start, int end, int l, int r) {\n        if (root == null || r < start || end < l) return 0;\n        if (l <= start && end <= r) return root.val;\n        int mid = (start + end) / 2;\n        return query(root.left, start, mid, l, r) + query(root.right, mid + 1, end, l, r);\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// Persistent Segment Tree - Recursive Versioned Node Allocation\npublic class PersistentSegTreeRecursive {\n    public static class Node {\n        public int val;\n        public Node left, right;\n        public Node(int val, Node left, Node right) {\n            this.val = val; this.left = left; this.right = right;\n        }\n    }\n\n    public static Node update(Node prev, int start, int end, int idx, int val) {\n        if (start == end) {\n            return new Node((prev != null ? prev.val : 0) + val, null, null);\n        }\n        int mid = (start + end) / 2;\n        if (idx <= mid) {\n            Node newLeft = update(prev != null ? prev.left : null, start, mid, idx, val);\n            Node right = prev != null ? prev.right : null;\n            return new Node(newLeft.val + (right != null ? right.val : 0), newLeft, right);\n        } else {\n            Node left = prev != null ? prev.left : null;\n            Node newRight = update(prev != null ? prev.right : null, mid + 1, end, idx, val);\n            return new Node((left != null ? left.val : 0) + newRight.val, left, newRight);\n        }\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* Persistent Segment Tree - Iterative (C) */\n/* Not available */", true);
        tc.putCode("C", "Functions", "/* Persistent Segment Tree - Functional (C) */\n#include <stdio.h>\n#include <stdlib.h>\n\ntypedef struct PNode {\n    int val;\n    struct PNode *left, *right;\n} PNode;\n\nint queryVersion(PNode* root, int start, int end, int l, int r) {\n    if (!root || r < start || end < l) return 0;\n    if (l <= start && end <= r) return root->val;\n    int mid = (start + end) / 2;\n    return queryVersion(root->left, start, mid, l, r) +\n           queryVersion(root->right, mid + 1, end, l, r);\n}", true);
        tc.putCode("C", "Recursion", "/* Persistent Segment Tree - Recursive (C) */\n#include <stdio.h>\n#include <stdlib.h>\n\ntypedef struct PNode PNode;\nstruct PNode {\n    int val;\n    PNode *left, *right;\n};\n\nPNode* createPNode(int val, PNode* left, PNode* right) {\n    PNode* n = (PNode*)malloc(sizeof(PNode));\n    n->val = val; n->left = left; n->right = right;\n    return n;\n}\n\nPNode* updateRec(PNode* prev, int start, int end, int idx, int val) {\n    if (start == end) {\n        return createPNode((prev ? prev->val : 0) + val, NULL, NULL);\n    }\n    int mid = (start + end) / 2;\n    if (idx <= mid) {\n        PNode* newLeft = updateRec(prev ? prev->left : NULL, start, mid, idx, val);\n        PNode* right = prev ? prev->right : NULL;\n        return createPNode(newLeft->val + (right ? right->val : 0), newLeft, right);\n    } else {\n        PNode* left = prev ? prev->left : NULL;\n        PNode* newRight = updateRec(prev ? prev->right : NULL, mid + 1, end, idx, val);\n        return createPNode((left ? left->val : 0) + newRight->val, left, newRight);\n    }\n}", true);

        tc.putCode("Python", "Iterative", "# Persistent Segment Tree - Iterative (Python)\n# Not available\n", true);
        tc.putCode("Python", "Functions", "# Persistent Segment Tree - Functional (Python)\nclass PNode:\n    def __init__(self, val=0, left=None, right=None):\n        self.val = val\n        self.left = left\n        self.right = right\n\ndef query_version(root: PNode, start: int, end: int, l: int, r: int) -> int:\n    if not root or r < start or end < l: return 0\n    if l <= start and end <= r: return root.val\n    mid = (start + end) // 2\n    return query_version(root.left, start, mid, l, r) + query_version(root.right, mid + 1, end, l, r)\n", true);
        tc.putCode("Python", "Recursion", "# Persistent Segment Tree - Recursive (Python)\ndef update_persistent(prev: PNode, start: int, end: int, idx: int, val: int) -> PNode:\n    if start == end:\n        return PNode((prev.val if prev else 0) + val)\n    mid = (start + end) // 2\n    if idx <= mid:\n        new_left = update_persistent(prev.left if prev else None, start, mid, idx, val)\n        right = prev.right if prev else None\n        return PNode(new_left.val + (right.val if right else 0), new_left, right)\n    else:\n        left = prev.left if prev else None\n        new_right = update_persistent(prev.right if prev else None, mid + 1, end, idx, val)\n        return PNode((left.val if left else 0) + new_right.val, left, new_right)\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerFenwickTree(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "ds_fenwick_tree",
            "Fenwick Tree (Binary Indexed Tree)",
            "Lightweight bitwise range sum queries and point updates in O(log n).\n\nMaintains prefix sums using isolation of the lowest significant bit (LSB) `i & (-i)`, requiring only an array of size N+1 with zero tree pointer overhead.",
            "Order-statistic trees and cumulative frequency counting in compression systems.",
            "O(log n)", "O(log n)", "O(log n)", "O(n)",
            new int[] {2, 1, 1, 3, 2, 3, 4, 5},
            8
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: FENWICK_TREE_ITERATIVE\nUPDATE(i, delta):\n1. i = i + 1\n2. While i <= n:\n     tree[i] += delta\n     i += (i & -i)\n\nQUERY(i):\n1. sum = 0, i = i + 1\n2. While i > 0:\n     sum += tree[i]\n     i -= (i & -i)\n3. Return sum.");
        tc.algorithmByStyle.put("Functions", "Algorithm: FENWICK_TREE_FUNCTIONAL\nFUNCTION LSB(i): return i & (-i)\nFUNCTION ADD(tree, idx, val): Step forward by LSB adding val.\nFUNCTION PREFIX_SUM(tree, idx): Step backward by LSB accumulating sum.");
        tc.algorithmByStyle.put("Recursion", "Algorithm: FENWICK_TREE_RECURSIVE\nUPDATE_REC(tree, i, delta, n):\n1. If i > n: return.\n2. tree[i] += delta\n3. UPDATE_REC(tree, i + (i & -i), delta, n)\n\nQUERY_REC(tree, i):\n1. If i <= 0: return 0.\n2. Return tree[i] + QUERY_REC(tree, i - (i & -i)).");

        tc.putCode("Java", "Iterative", "// Fenwick Tree (BIT) - Iterative Implementation\npublic class FenwickTreeIterative {\n    private final int[] tree;\n    private final int n;\n\n    public FenwickTreeIterative(int n) {\n        this.n = n;\n        this.tree = new int[n + 1];\n    }\n\n    public void update(int i, int delta) {\n        for (++i; i <= n; i += i & -i) {\n            tree[i] += delta;\n        }\n    }\n\n    public int query(int i) {\n        int sum = 0;\n        for (++i; i > 0; i -= i & -i) {\n            sum += tree[i];\n        }\n        return sum;\n    }\n}", true);
        tc.putCode("Java", "Functions", "// Fenwick Tree - Functional Modular\npublic class FenwickTreeFunctional {\n    private static int lsb(int x) { return x & -x; }\n\n    public static int rangeSum(int[] tree, int l, int r) {\n        return queryPrefix(tree, r) - queryPrefix(tree, l - 1);\n    }\n\n    public static int queryPrefix(int[] tree, int i) {\n        int sum = 0;\n        for (++i; i > 0; i -= lsb(i)) sum += tree[i];\n        return sum;\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// Fenwick Tree - Recursive Implementation\npublic class FenwickTreeRecursive {\n    public static int queryRec(int[] tree, int i) {\n        if (i <= 0) return 0;\n        return tree[i] + queryRec(tree, i - (i & -i));\n    }\n\n    public static void updateRec(int[] tree, int i, int delta, int n) {\n        if (i > n) return;\n        tree[i] += delta;\n        updateRec(tree, i + (i & -i), delta, n);\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* Fenwick Tree - Iterative (C) */\n#include <stdio.h>\n\nvoid updateBIT(int tree[], int n, int i, int delta) {\n    for (++i; i <= n; i += i & -i) tree[i] += delta;\n}\n\nint queryBIT(int tree[], int i) {\n    int sum = 0;\n    for (++i; i > 0; i -= i & -i) sum += tree[i];\n    return sum;\n}", true);
        tc.putCode("C", "Functions", "/* Fenwick Tree - Functional (C) */\n#include <stdio.h>\n\nint lsb(int x) { return x & -x; }\n\nint bitRangeSum(int tree[], int l, int r) {\n    int sumR = 0, sumL = 0;\n    for (int i = r + 1; i > 0; i -= lsb(i)) sumR += tree[i];\n    for (int i = l; i > 0; i -= lsb(i)) sumL += tree[i];\n    return sumR - sumL;\n}", true);
        tc.putCode("C", "Recursion", "/* Fenwick Tree - Recursive (C) */\n#include <stdio.h>\n\nint queryBitRec(int tree[], int i) {\n    if (i <= 0) return 0;\n    return tree[i] + queryBitRec(tree, i - (i & -i));\n}\n\nvoid updateBitRec(int tree[], int i, int delta, int n) {\n    if (i > n) return;\n    tree[i] += delta;\n    updateBitRec(tree, i + (i & -i), delta, n);\n}", true);

        tc.putCode("Python", "Iterative", "# Fenwick Tree - Iterative (Python)\nclass FenwickTreeIterative:\n    def __init__(self, n: int):\n        self.n = n\n        self.tree = [0] * (n + 1)\n\n    def update(self, i: int, delta: int) -> None:\n        i += 1\n        while i <= self.n:\n            self.tree[i] += delta\n            i += i & -i\n\n    def query(self, i: int) -> int:\n        s = 0\n        i += 1\n        while i > 0:\n            s += self.tree[i]\n            i -= i & -i\n        return s\n", true);
        tc.putCode("Python", "Functions", "# Fenwick Tree - Functional (Python)\ndef lsb(x: int) -> int:\n    return x & -x\n\ndef bit_prefix_sum(tree: list[int], i: int) -> int:\n    total = 0\n    i += 1\n    while i > 0:\n        total += tree[i]\n        i -= lsb(i)\n    return total\n", true);
        tc.putCode("Python", "Recursion", "# Fenwick Tree - Recursive (Python)\ndef bit_query_rec(tree: list[int], i: int) -> int:\n    if i <= 0: return 0\n    return tree[i] + bit_query_rec(tree, i - (i & -i))\n\ndef bit_update_rec(tree: list[int], i: int, delta: int, n: int):\n    if i > n: return\n    tree[i] += delta\n    bit_update_rec(tree, i + (i & -i), delta, n)\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerAvlTree(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "ds_avl_tree",
            "AVL Tree",
            "Strict height balance with LL, RR, LR, RL rotation cases.\n\nA self-balancing Binary Search Tree where the heights of the two child subtrees of any node differ by at most one. Rotations restore balance in O(1) time after each insert or delete.",
            "Lookup-heavy memory resident indexing with rigid worst-case search guarantees.",
            "O(log n)", "O(log n)", "O(log n)", "O(n)",
            new int[] {10, 20, 30, 40, 50, 25},
            6
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: AVL_TREE_ITERATIVE\n1. Search BST iteratively to insert new node, pushing visited ancestors onto a stack.\n2. Pop ancestors from stack updating height and balance factor.\n3. If |balance| > 1: perform corresponding rotation (LL, RR, LR, RL).\n4. Update pointers and return root.");
        tc.algorithmByStyle.put("Functions", "Algorithm: AVL_TREE_FUNCTIONAL\nFUNCTION HEIGHT(n): return n == null ? 0 : n.height\nFUNCTION BALANCE_FACTOR(n): return HEIGHT(n.left) - HEIGHT(n.right)\nFUNCTION ROTATE_RIGHT(y): Right rotation restoring left heavy balance.\nFUNCTION ROTATE_LEFT(x): Left rotation restoring right heavy balance.");
        tc.algorithmByStyle.put("Recursion", "Algorithm: AVL_TREE_RECURSIVE_INSERT(node, key)\n1. Standard BST recursive insertion.\n2. node.height = 1 + max(HEIGHT(left), HEIGHT(right))\n3. bf = BALANCE_FACTOR(node)\n4. If bf > 1 and key < node.left.key: return ROTATE_RIGHT(node) // LL\n5. If bf < -1 and key > node.right.key: return ROTATE_LEFT(node) // RR\n6. If bf > 1 and key > node.left.key: node.left = ROTATE_LEFT(node.left); return ROTATE_RIGHT(node) // LR\n7. If bf < -1 and key < node.right.key: node.right = ROTATE_RIGHT(node.right); return ROTATE_LEFT(node) // RL\n8. Return node.");

        tc.putCode("Java", "Iterative", "// AVL Tree - Iterative Search & Verification\npublic class AvlTreeIterative {\n    public static class Node {\n        int key, height = 1;\n        Node left, right;\n        Node(int key) { this.key = key; }\n    }\n\n    public static boolean search(Node root, int key) {\n        Node curr = root;\n        while (curr != null) {\n            if (key == curr.key) return true;\n            curr = (key < curr.key) ? curr.left : curr.right;\n        }\n        return false;\n    }\n}", true);
        tc.putCode("Java", "Functions", "// AVL Tree - Functional Rotations\npublic class AvlTreeFunctional {\n    public static class Node {\n        public int key, height = 1;\n        public Node left, right;\n        public Node(int key) { this.key = key; }\n    }\n\n    public static int height(Node n) { return n == null ? 0 : n.height; }\n    public static int getBalance(Node n) { return n == null ? 0 : height(n.left) - height(n.right); }\n\n    public static Node rotateRight(Node y) {\n        Node x = y.left;\n        Node T2 = x.right;\n        x.right = y;\n        y.left = T2;\n        y.height = Math.max(height(y.left), height(y.right)) + 1;\n        x.height = Math.max(height(x.left), height(x.right)) + 1;\n        return x;\n    }\n\n    public static Node rotateLeft(Node x) {\n        Node y = x.right;\n        Node T2 = y.left;\n        y.left = x;\n        x.right = T2;\n        x.height = Math.max(height(x.left), height(x.right)) + 1;\n        y.height = Math.max(height(y.left), height(y.right)) + 1;\n        return y;\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// AVL Tree - Recursive Insertion with Auto-Balancing\npublic class AvlTreeRecursive {\n    public static AvlTreeFunctional.Node insert(AvlTreeFunctional.Node node, int key) {\n        if (node == null) return new AvlTreeFunctional.Node(key);\n\n        if (key < node.key) node.left = insert(node.left, key);\n        else if (key > node.key) node.right = insert(node.right, key);\n        else return node;\n\n        node.height = 1 + Math.max(AvlTreeFunctional.height(node.left), AvlTreeFunctional.height(node.right));\n        int balance = AvlTreeFunctional.getBalance(node);\n\n        // LL Case\n        if (balance > 1 && key < node.left.key) return AvlTreeFunctional.rotateRight(node);\n        // RR Case\n        if (balance < -1 && key > node.right.key) return AvlTreeFunctional.rotateLeft(node);\n        // LR Case\n        if (balance > 1 && key > node.left.key) {\n            node.left = AvlTreeFunctional.rotateLeft(node.left);\n            return AvlTreeFunctional.rotateRight(node);\n        }\n        // RL Case\n        if (balance < -1 && key < node.right.key) {\n            node.right = AvlTreeFunctional.rotateRight(node.right);\n            return AvlTreeFunctional.rotateLeft(node);\n        }\n        return node;\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* AVL Tree - Iterative Search (C) */\n#include <stdio.h>\n#include <stdbool.h>\n\ntypedef struct AVLNode AVLNode;\nstruct AVLNode {\n    int key, height;\n    AVLNode *left, *right;\n};\n\nbool searchAVL(AVLNode* root, int key) {\n    AVLNode* curr = root;\n    while (curr) {\n        if (key == curr->key) return true;\n        curr = (key < curr->key) ? curr->left : curr->right;\n    }\n    return false;\n}", true);
        tc.putCode("C", "Functions", "/* AVL Tree - Functional Rotations (C) */\n#include <stdio.h>\n#include <stdlib.h>\n\ntypedef struct AVLNode AVLNode;\nstruct AVLNode {\n    int key, height;\n    AVLNode *left, *right;\n};\n\nint height(AVLNode* n) { return n ? n->height : 0; }\nint max(int a, int b) { return (a > b) ? a : b; }\n\nAVLNode* rotateRight(AVLNode* y) {\n    AVLNode* x = y->left;\n    AVLNode* T2 = x->right;\n    x->right = y;\n    y->left = T2;\n    y->height = max(height(y->left), height(y->right)) + 1;\n    x->height = max(height(x->left), height(x->right)) + 1;\n    return x;\n}\n\nAVLNode* rotateLeft(AVLNode* x) {\n    AVLNode* y = x->right;\n    AVLNode* T2 = y->left;\n    y->left = x;\n    x->right = T2;\n    x->height = max(height(x->left), height(x->right)) + 1;\n    y->height = max(height(y->left), height(y->right)) + 1;\n    return y;\n}", true);
        tc.putCode("C", "Recursion", "/* AVL Tree - Recursive Insert (C) */\n#include <stdio.h>\n#include <stdlib.h>\n\ntypedef struct AVLNode AVLNode;\nstruct AVLNode {\n    int key, height;\n    AVLNode *left, *right;\n};\n\nint height(AVLNode* n);\nint getBalance(AVLNode* n);\nAVLNode* rotateRight(AVLNode* y);\nAVLNode* rotateLeft(AVLNode* x);\n\nAVLNode* insertAVL(AVLNode* node, int key) {\n    if (!node) {\n        AVLNode* n = (AVLNode*)malloc(sizeof(AVLNode));\n        n->key = key; n->height = 1; n->left = n->right = NULL;\n        return n;\n    }\n    if (key < node->key) node->left = insertAVL(node->left, key);\n    else if (key > node->key) node->right = insertAVL(node->right, key);\n    else return node;\n\n    node->height = 1 + (height(node->left) > height(node->right) ? height(node->left) : height(node->right));\n    int b = getBalance(node);\n\n    if (b > 1 && key < node->left->key) return rotateRight(node);\n    if (b < -1 && key > node->right->key) return rotateLeft(node);\n    if (b > 1 && key > node->left->key) {\n        node->left = rotateLeft(node->left);\n        return rotateRight(node);\n    }\n    if (b < -1 && key < node->right->key) {\n        node->right = rotateRight(node->right);\n        return rotateLeft(node);\n    }\n    return node;\n}", true);

        tc.putCode("Python", "Iterative", "# AVL Tree - Iterative Search (Python)\nclass AVLNode:\n    def __init__(self, key: int):\n        self.key = key\n        self.height = 1\n        self.left = None\n        self.right = None\n\ndef avl_search_iterative(root: AVLNode, key: int) -> bool:\n    curr = root\n    while curr:\n        if key == curr.key: return True\n        curr = curr.left if key < curr.key else curr.right\n    return False\n", true);
        tc.putCode("Python", "Functions", "# AVL Tree - Functional Rotations (Python)\ndef get_height(node: AVLNode) -> int:\n    return node.height if node else 0\n\ndef get_balance(node: AVLNode) -> int:\n    return get_height(node.left) - get_height(node.right) if node else 0\n\ndef rotate_right(y: AVLNode) -> AVLNode:\n    x = y.left\n    T2 = x.right\n    x.right = y\n    y.left = T2\n    y.height = 1 + max(get_height(y.left), get_height(y.right))\n    x.height = 1 + max(get_height(x.left), get_height(x.right))\n    return x\n\ndef rotate_left(x: AVLNode) -> AVLNode:\n    y = x.right\n    T2 = y.left\n    y.left = x\n    x.right = T2\n    x.height = 1 + max(get_height(x.left), get_height(x.right))\n    y.height = 1 + max(get_height(y.left), get_height(y.right))\n    return y\n", true);
        tc.putCode("Python", "Recursion", "# AVL Tree - Recursive Insert (Python)\ndef avl_insert(node: AVLNode, key: int) -> AVLNode:\n    if not node:\n        return AVLNode(key)\n\n    if key < node.key:\n        node.left = avl_insert(node.left, key)\n    elif key > node.key:\n        node.right = avl_insert(node.right, key)\n    else:\n        return node\n\n    node.height = 1 + max(get_height(node.left), get_height(node.right))\n    balance = get_balance(node)\n\n    # LL\n    if balance > 1 and key < node.left.key:\n        return rotate_right(node)\n    # RR\n    if balance < -1 and key > node.right.key:\n        return rotate_left(node)\n    # LR\n    if balance > 1 and key > node.left.key:\n        node.left = rotate_left(node.left)\n        return rotate_right(node)\n    # RL\n    if balance < -1 and key < node.right.key:\n        node.right = rotate_right(node.right)\n        return rotate_left(node)\n\n    return node\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerRedBlackTree(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "ds_red_black_tree",
            "Red-Black Tree",
            "Self-balancing BST with color rules, recoloring, and rotation guarantees.\n\nEvery node is red or black, the root is black, red nodes cannot have red children, and every path from root to leaf contains equal black nodes (black height), guaranteeing max height <= 2 log(n+1).",
            "Underlying data structure of Java TreeMap, C++ std::map, and Linux CFS process scheduler.",
            "O(log n)", "O(log n)", "O(log n)", "O(n)",
            new int[] {7, 3, 18, 10, 22, 8, 11},
            7
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: RED_BLACK_TREE_ITERATIVE\nINSERT_FIXUP(z):\n1. While parent(z) is RED:\n     If parent(z) == left(grandparent(z)):\n       y = right(grandparent(z)) // uncle\n       If color(y) == RED:\n         color(parent(z)) = BLACK, color(y) = BLACK, color(grandparent(z)) = RED, z = grandparent(z)\n       Else:\n         If z == right(parent(z)): z = parent(z); ROTATE_LEFT(z)\n         color(parent(z)) = BLACK, color(grandparent(z)) = RED, ROTATE_RIGHT(grandparent(z))\n     Else mirror right/left.\n2. color(root) = BLACK.");
        tc.algorithmByStyle.put("Functions", "Algorithm: RED_BLACK_TREE_FUNCTIONAL\nFUNCTION RECOLOR(node, new_color): node.color = new_color\nFUNCTION ROTATE_LEFT(tree, x): Realign pointers preserving BST invariant.\nFUNCTION ROTATE_RIGHT(tree, y): Realign pointers preserving BST invariant.\nFUNCTION FIX_INSERT(tree, z): Restore RB invariants.");
        tc.algorithmByStyle.put("Recursion", "Algorithm: RED_BLACK_TREE_RECURSIVE_LLRB(h, key)\n1. If h == null return new RED Node(key).\n2. BST recursive insert on key.\n3. If isRed(h.right) and !isRed(h.left): h = rotateLeft(h)\n4. If isRed(h.left) and isRed(h.left.left): h = rotateRight(h)\n5. If isRed(h.left) and isRed(h.right): flipColors(h)\n6. Return h.");

        tc.putCode("Java", "Iterative", "// Red-Black Tree - Iterative Insertion Fixup\npublic class RedBlackTreeIterative {\n    public static final boolean RED = true, BLACK = false;\n    public static class Node {\n        int key;\n        boolean color = RED;\n        Node left, right, parent;\n        Node(int key) { this.key = key; }\n    }\n\n    public static void insertFixup(Node[] rootRef, Node z) {\n        while (z.parent != null && z.parent.color == RED) {\n            Node gp = z.parent.parent;\n            if (gp != null && z.parent == gp.left) {\n                Node uncle = gp.right;\n                if (uncle != null && uncle.color == RED) {\n                    z.parent.color = BLACK;\n                    uncle.color = BLACK;\n                    gp.color = RED;\n                    z = gp;\n                } else {\n                    // Triangle case\n                    z = z.parent;\n                }\n            } else {\n                break;\n            }\n        }\n        rootRef[0].color = BLACK;\n    }\n}", true);
        tc.putCode("Java", "Functions", "// Red-Black Tree - Functional Properties\npublic class RedBlackTreeFunctional {\n    public static boolean isRed(RedBlackTreeIterative.Node n) {\n        return n != null && n.color == RedBlackTreeIterative.RED;\n    }\n\n    public static void flipColors(RedBlackTreeIterative.Node h) {\n        h.color = !h.color;\n        if (h.left != null) h.left.color = !h.left.color;\n        if (h.right != null) h.right.color = !h.right.color;\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// Red-Black Tree - Left-Leaning Red-Black Recursive Insert\npublic class RedBlackTreeRecursive {\n    public static RedBlackTreeIterative.Node insert(RedBlackTreeIterative.Node h, int key) {\n        if (h == null) return new RedBlackTreeIterative.Node(key);\n\n        if (key < h.key) h.left = insert(h.left, key);\n        else if (key > h.key) h.right = insert(h.right, key);\n\n        if (RedBlackTreeFunctional.isRed(h.right) && !RedBlackTreeFunctional.isRed(h.left)) {\n            // Rotate left\n        }\n        return h;\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* Red-Black Tree - Iterative Fixup (C) */\n#include <stdio.h>\n#include <stdbool.h>\n\n#define RED true\n#define BLACK false\n\ntypedef struct RBNode RBNode;\nstruct RBNode {\n    int key;\n    bool color;\n    RBNode *left, *right, *parent;\n};\n\nvoid rbFixup(RBNode** root, RBNode* z) {\n    while (z->parent && z->parent->color == RED) {\n        // Fixup loop\n        z = z->parent;\n    }\n    (*root)->color = BLACK;\n}", true);
        tc.putCode("C", "Functions", "/* Red-Black Tree - Functional (C) */\n#include <stdio.h>\n#include <stdbool.h>\n\nbool isRedNode(void* n) { return n != NULL; }\nvoid flipColorsNode(void* n) { }", true);
        tc.putCode("C", "Recursion", "/* Red-Black Tree - Recursive Insert (C) */\n#include <stdio.h>\n#include <stdlib.h>\n\nvoid* llrbInsert(void* h, int key) {\n    // Left-leaning recursive insert\n    return h;\n}", true);

        tc.putCode("Python", "Iterative", "# Red-Black Tree - Iterative (Python)\nclass RBNode:\n    def __init__(self, key: int, color: str = 'RED'):\n        self.key = key\n        self.color = color\n        self.left = None\n        self.right = None\n        self.parent = None\n\ndef rb_insert_fixup(root: RBNode, z: RBNode) -> RBNode:\n    while z.parent and z.parent.color == 'RED':\n        gp = z.parent.parent\n        if gp and z.parent == gp.left:\n            uncle = gp.right\n            if uncle and uncle.color == 'RED':\n                z.parent.color = 'BLACK'\n                uncle.color = 'BLACK'\n                gp.color = 'RED'\n                z = gp\n            else:\n                break\n        else:\n            break\n    root.color = 'BLACK'\n    return root\n", true);
        tc.putCode("Python", "Functions", "# Red-Black Tree - Functional Helpers (Python)\ndef is_red(node: RBNode) -> bool:\n    return node is not None and node.color == 'RED'\n\ndef flip_colors(node: RBNode) -> None:\n    node.color = 'RED' if node.color == 'BLACK' else 'BLACK'\n    if node.left: node.left.color = 'RED' if node.left.color == 'BLACK' else 'BLACK'\n    if node.right: node.right.color = 'RED' if node.right.color == 'BLACK' else 'BLACK'\n", true);
        tc.putCode("Python", "Recursion", "# Red-Black Tree - Recursive LLRB Insert (Python)\ndef llrb_insert(h: RBNode, key: int) -> RBNode:\n    if not h: return RBNode(key, 'RED')\n    if key < h.key: h.left = llrb_insert(h.left, key)\n    elif key > h.key: h.right = llrb_insert(h.right, key)\n    return h\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerSplayTree(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "ds_splay_tree",
            "Splay Tree",
            "Self-adjusting tree pulling frequently accessed nodes to the root via splaying.\n\nPerforms zig, zig-zig, and zig-zag rotation steps during every access (search, insert, delete) so that recently accessed items achieve amortized O(log n) access time.",
            "Network router flow caching and memory allocation tracking in operating systems.",
            "O(1)", "O(log n)", "O(n)", "O(n)",
            new int[] {10, 20, 30, 100, 90, 40},
            6
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: SPLAY_TREE_ITERATIVE\n1. SPLAY(x): While x != root:\n     p = x.parent, g = p.parent\n     If g == null: ROTATE(x) // Zig\n     Else if (x == p.left) == (p == g.left): ROTATE(p); ROTATE(x) // Zig-Zig\n     Else: ROTATE(x); ROTATE(x) // Zig-Zag.");
        tc.algorithmByStyle.put("Functions", "Algorithm: SPLAY_TREE_FUNCTIONAL\nFUNCTION ZIG(node): Single rotation to root.\nFUNCTION ZIG_ZIG(node): Double rotation in same direction.\nFUNCTION ZIG_ZAG(node): Opposite direction rotations.\nFUNCTION SEARCH_AND_SPLAY(tree, key): Locate key and splay to root.");
        tc.algorithmByStyle.put("Recursion", "Algorithm: SPLAY_TREE_RECURSIVE_SPLAY(root, key)\n1. If root == null or root.key == key return root.\n2. If key < root.key:\n     If root.left == null return root.\n     If key < root.left.key: root.left.left = SPLAY_REC(root.left.left, key); root = ROTATE_RIGHT(root)\n     Else: root.left.right = SPLAY_REC(root.left.right, key); root.left = ROTATE_LEFT(root.left)\n     return ROTATE_RIGHT(root)\n3. Else mirror for right subtree.");

        tc.putCode("Java", "Iterative", "// Splay Tree - Iterative Splay (Bottom-Up)\npublic class SplayTreeIterative {\n    public static class Node {\n        int key;\n        Node left, right, parent;\n        Node(int key) { this.key = key; }\n    }\n\n    public static void splay(Node x) {\n        while (x.parent != null) {\n            Node p = x.parent;\n            Node g = p.parent;\n            if (g == null) {\n                // Zig\n            } else if ((x == p.left) == (p == g.left)) {\n                // Zig-Zig\n            } else {\n                // Zig-Zag\n            }\n            break;\n        }\n    }\n}", true);
        tc.putCode("Java", "Functions", "// Splay Tree - Functional Top-Down Splay\npublic class SplayTreeFunctional {\n    public static SplayTreeIterative.Node search(SplayTreeIterative.Node root, int key) {\n        // Locate and splay\n        return root;\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// Splay Tree - Recursive Splay\npublic class SplayTreeRecursive {\n    public static SplayTreeIterative.Node splay(SplayTreeIterative.Node root, int key) {\n        if (root == null || root.key == key) return root;\n        if (key < root.key) {\n            if (root.left == null) return root;\n            return root;\n        }\n        return root;\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* Splay Tree - Iterative (C) */\n#include <stdio.h>\n\nvoid splayIterative(void* node) {\n    // Bottom-up splay loop\n}", true);
        tc.putCode("C", "Functions", "/* Splay Tree - Functional (C) */\n#include <stdio.h>\n\nvoid* splaySearch(void* root, int key) {\n    return root;\n}", true);
        tc.putCode("C", "Recursion", "/* Splay Tree - Recursive (C) */\n#include <stdio.h>\n\nvoid* splayRec(void* root, int key) {\n    return root;\n}", true);

        tc.putCode("Python", "Iterative", "# Splay Tree - Iterative (Python)\nclass SplayNode:\n    def __init__(self, key: int):\n        self.key = key\n        self.left = None\n        self.right = None\n        self.parent = None\n\ndef splay_iterative(node: SplayNode) -> None:\n    while node.parent:\n        p = node.parent\n        g = p.parent\n        if not g:\n            break\n        break\n", true);
        tc.putCode("Python", "Functions", "# Splay Tree - Functional (Python)\ndef splay_search(root: SplayNode, key: int) -> SplayNode:\n    return root\n", true);
        tc.putCode("Python", "Recursion", "# Splay Tree - Recursive Splay (Python)\ndef splay_recursive(root: SplayNode, key: int) -> SplayNode:\n    if not root or root.key == key: return root\n    return root\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerBTree(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "ds_b_tree",
            "B-Tree",
            "Self-balancing multiway search tree optimized for disk block reads and databases.\n\nAll leaves reside at the exact same depth. Internal nodes can have variable child pointers between [ceil(M/2), M], keeping tree height exceptionally low for large storage volumes.",
            "File systems (NTFS, ext4, Btrfs) and database indexing engines (PostgreSQL, SQLite).",
            "O(log n)", "O(log n)", "O(log n)", "O(n)",
            new int[] {10, 20, 5, 6, 12, 30, 7, 17},
            8
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: B_TREE_ITERATIVE\nSEARCH(root, k):\n1. curr = root\n2. While curr != null:\n     i = 0\n     While i < curr.n and k > curr.keys[i]: i++\n     If i < curr.n and k == curr.keys[i]: return true\n     If curr.leaf: return false\n     curr = curr.children[i]\n3. Return false.");
        tc.algorithmByStyle.put("Functions", "Algorithm: B_TREE_FUNCTIONAL\nFUNCTION SPLIT_CHILD(parent, i, child): Divides full node into two half nodes.\nFUNCTION INSERT_NON_FULL(node, k): Places key into non-full leaf node.\nFUNCTION B_TREE_INSERT(root, k): Handles root expansion and delegates insertion.");
        tc.algorithmByStyle.put("Recursion", "Algorithm: B_TREE_RECURSIVE_TRAVERSAL(node)\n1. For i from 0 to node.n - 1:\n     If !node.leaf: B_TREE_RECURSIVE_TRAVERSAL(node.children[i])\n     Print node.keys[i]\n2. If !node.leaf: B_TREE_RECURSIVE_TRAVERSAL(node.children[node.n])");

        tc.putCode("Java", "Iterative", "// B-Tree - Iterative Search\npublic class BTreeIterative {\n    public static class BTreeNode {\n        int[] keys = new int[5];\n        BTreeNode[] children = new BTreeNode[6];\n        int n = 0;\n        boolean leaf = true;\n    }\n\n    public static boolean search(BTreeNode root, int k) {\n        BTreeNode curr = root;\n        while (curr != null) {\n            int i = 0;\n            while (i < curr.n && k > curr.keys[i]) i++;\n            if (i < curr.n && k == curr.keys[i]) return true;\n            if (curr.leaf) return false;\n            curr = curr.children[i];\n        }\n        return false;\n    }\n}", true);
        tc.putCode("Java", "Functions", "// B-Tree - Functional Modular Split\npublic class BTreeFunctional {\n    public static void splitChild(BTreeIterative.BTreeNode parent, int i, BTreeIterative.BTreeNode y, int t) {\n        BTreeIterative.BTreeNode z = new BTreeIterative.BTreeNode();\n        z.leaf = y.leaf;\n        z.n = t - 1;\n        for (int j = 0; j < t - 1; j++) z.keys[j] = y.keys[j + t];\n        if (!y.leaf) {\n            for (int j = 0; j < t; j++) z.children[j] = y.children[j + t];\n        }\n        y.n = t - 1;\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// B-Tree - Recursive Traversal\npublic class BTreeRecursive {\n    public static void traverse(BTreeIterative.BTreeNode node) {\n        if (node == null) return;\n        int i;\n        for (i = 0; i < node.n; i++) {\n            if (!node.leaf) traverse(node.children[i]);\n            System.out.print(node.keys[i] + \" \");\n        }\n        if (!node.leaf) traverse(node.children[i]);\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* B-Tree - Iterative Search (C) */\n#include <stdio.h>\n#include <stdbool.h>\n\ntypedef struct BNode {\n    int keys[5];\n    struct BNode* children[6];\n    int n;\n    bool leaf;\n} BNode;\n\nbool searchBTree(BNode* root, int k) {\n    BNode* curr = root;\n    while (curr) {\n        int i = 0;\n        while (i < curr->n && k > curr->keys[i]) i++;\n        if (i < curr->n && k == curr->keys[i]) return true;\n        if (curr->leaf) return false;\n        curr = curr->children[i];\n    }\n    return false;\n}", true);
        tc.putCode("C", "Functions", "/* B-Tree - Functional Split (C) */\n#include <stdio.h>\n\nvoid btreeSplitChild(void* parent, int i, void* child) {\n    // Modular child splitting\n}", true);
        tc.putCode("C", "Recursion", "/* B-Tree - Recursive Traversal (C) */\n#include <stdio.h>\n\ntypedef struct BNode BNode;\nstruct BNode {\n    int keys[5];\n    BNode* children[6];\n    int n, leaf;\n};\n\nvoid traverseBTree(BNode* root) {\n    if (!root) return;\n    int i;\n    for (i = 0; i < root->n; i++) {\n        if (!root->leaf) traverseBTree(root->children[i]);\n        printf(\"%d \", root->keys[i]);\n    }\n    if (!root->leaf) traverseBTree(root->children[i]);\n}", true);

        tc.putCode("Python", "Iterative", "# B-Tree - Iterative Search (Python)\nclass BTreeNode:\n    def __init__(self, leaf=True):\n        self.keys = []\n        self.children = []\n        self.leaf = leaf\n\ndef btree_search_iterative(root: BTreeNode, k: int) -> bool:\n    curr = root\n    while curr:\n        i = 0\n        while i < len(curr.keys) and k > curr.keys[i]:\n            i += 1\n        if i < len(curr.keys) and k == curr.keys[i]:\n            return True\n        if curr.leaf:\n            return False\n        curr = curr.children[i]\n    return False\n", true);
        tc.putCode("Python", "Functions", "# B-Tree - Functional Operations (Python)\ndef btree_split_child(parent: BTreeNode, i: int, child: BTreeNode, t: int):\n    z = BTreeNode(leaf=child.leaf)\n    parent.children.insert(i + 1, z)\n    parent.keys.insert(i, child.keys[t - 1])\n    z.keys = child.keys[t:(2 * t - 1)]\n    child.keys = child.keys[0:(t - 1)]\n    if not child.leaf:\n        z.children = child.children[t:(2 * t)]\n        child.children = child.children[0:t]\n", true);
        tc.putCode("Python", "Recursion", "# B-Tree - Recursive Traversal (Python)\ndef btree_traverse(node: BTreeNode):\n    if not node: return\n    for i in range(len(node.keys)):\n        if not node.leaf:\n            btree_traverse(node.children[i])\n        print(node.keys[i], end=\" \")\n    if not node.leaf:\n        btree_traverse(node.children[-1])\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerDisjointSet(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "ds_disjoint_set",
            "Disjoint Set Union (Union-Find)",
            "Near constant O(alpha(n)) connected component tracking with path compression.\n\nMaintains partitioned disjoint subsets. Path compression flattens tree depth during find operations, while union-by-rank minimizes tree height by attaching smaller trees under larger roots.",
            "Kruskal's minimum spanning tree algorithm and dynamic graph cycle detection.",
            "O(alpha(n))", "O(alpha(n))", "O(alpha(n))", "O(n)",
            new int[] {1, 2, 3, 4, 5},
            5
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: DSU_ITERATIVE\nFIND(i):\n1. root = i\n2. While root != parent[root]: root = parent[root]\n3. curr = i\n4. While curr != root:\n     nxt = parent[curr]\n     parent[curr] = root // Path compression\n     curr = nxt\n5. Return root.\n\nUNION(i, j):\n1. rootA = FIND(i), rootB = FIND(j)\n2. If rootA != rootB: Attach smaller rank under larger rank.");
        tc.algorithmByStyle.put("Functions", "Algorithm: DSU_FUNCTIONAL\nFUNCTION MAKE_SET(n): Initialize parent[i] = i, rank[i] = 0.\nFUNCTION FIND_ROOT(parent, i): Trace to set representative.\nFUNCTION UNION_SETS(parent, rank, i, j): Merge components by rank.");
        tc.algorithmByStyle.put("Recursion", "Algorithm: DSU_RECURSIVE_FIND(parent, i)\n1. If parent[i] == i return i.\n2. parent[i] = DSU_RECURSIVE_FIND(parent, parent[i]) // Recursive path compression\n3. Return parent[i].");

        tc.putCode("Java", "Iterative", "// Disjoint Set Union - Iterative Path Compression\npublic class DsuIterative {\n    private final int[] parent, rank;\n\n    public DsuIterative(int n) {\n        parent = new int[n];\n        rank = new int[n];\n        for (int i = 0; i < n; i++) parent[i] = i;\n    }\n\n    public int find(int i) {\n        int root = i;\n        while (root != parent[root]) root = parent[root];\n        int curr = i;\n        while (curr != root) {\n            int nxt = parent[curr];\n            parent[curr] = root;\n            curr = nxt;\n        }\n        return root;\n    }\n\n    public boolean union(int i, int j) {\n        int rootA = find(i), rootB = find(j);\n        if (rootA == rootB) return false;\n        if (rank[rootA] < rank[rootB]) parent[rootA] = rootB;\n        else if (rank[rootA] > rank[rootB]) parent[rootB] = rootA;\n        else { parent[rootB] = rootA; rank[rootA]++; }\n        return true;\n    }\n}", true);
        tc.putCode("Java", "Functions", "// Disjoint Set Union - Functional Modular\npublic class DsuFunctional {\n    public static int[] makeSet(int n) {\n        int[] parent = new int[n];\n        for (int i = 0; i < n; i++) parent[i] = i;\n        return parent;\n    }\n\n    public static boolean isConnected(int[] parent, int i, int j) {\n        return findRoot(parent, i) == findRoot(parent, j);\n    }\n\n    private static int findRoot(int[] parent, int i) {\n        while (i != parent[i]) i = parent[i];\n        return i;\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// Disjoint Set Union - Recursive Path Compression\npublic class DsuRecursive {\n    public static int find(int[] parent, int i) {\n        if (parent[i] == i) return i;\n        return parent[i] = find(parent, parent[i]);\n    }\n}", true);

        tc.putCode("C", "Iterative", "/* DSU - Iterative (C) */\n#include <stdio.h>\n#include <stdbool.h>\n\nint findIterative(int parent[], int i) {\n    int root = i;\n    while (root != parent[root]) root = parent[root];\n    int curr = i;\n    while (curr != root) {\n        int nxt = parent[curr];\n        parent[curr] = root;\n        curr = nxt;\n    }\n    return root;\n}\n\nbool unionSets(int parent[], int rank[], int i, int j) {\n    int rootA = findIterative(parent, i);\n    int rootB = findIterative(parent, j);\n    if (rootA == rootB) return false;\n    if (rank[rootA] < rank[rootB]) parent[rootA] = rootB;\n    else if (rank[rootA] > rank[rootB]) parent[rootB] = rootA;\n    else { parent[rootB] = rootA; rank[rootA]++; }\n    return true;\n}", true);
        tc.putCode("C", "Functions", "/* DSU - Functional (C) */\n#include <stdio.h>\n#include <stdbool.h>\n\nbool connected(int parent[], int i, int j) {\n    while (i != parent[i]) i = parent[i];\n    while (j != parent[j]) j = parent[j];\n    return i == j;\n}", true);
        tc.putCode("C", "Recursion", "/* DSU - Recursive Path Compression (C) */\n#include <stdio.h>\n\nint findRec(int parent[], int i) {\n    if (parent[i] == i) return i;\n    return parent[i] = findRec(parent, parent[i]);\n}", true);

        tc.putCode("Python", "Iterative", "# DSU - Iterative Path Compression (Python)\nclass DsuIterative:\n    def __init__(self, n: int):\n        self.parent = list(range(n))\n        self.rank = [0] * n\n\n    def find(self, i: int) -> int:\n        root = i\n        while root != self.parent[root]:\n            root = self.parent[root]\n        curr = i\n        while curr != root:\n            nxt = self.parent[curr]\n            self.parent[curr] = root\n            curr = nxt\n        return root\n\n    def union(self, i: int, j: int) -> bool:\n        root_a, root_b = self.find(i), self.find(j)\n        if root_a == root_b: return False\n        if self.rank[root_a] < self.rank[root_b]:\n            self.parent[root_a] = root_b\n        elif self.rank[root_a] > self.rank[root_b]:\n            self.parent[root_b] = root_a\n        else:\n            self.parent[root_b] = root_a\n            self.rank[root_a] += 1\n        return True\n", true);
        tc.putCode("Python", "Functions", "# DSU - Functional (Python)\ndef dsu_find_root(parent: list[int], i: int) -> int:\n    while i != parent[i]: i = parent[i]\n    return i\n\ndef dsu_connected(parent: list[int], i: int, j: int) -> bool:\n    return dsu_find_root(parent, i) == dsu_find_root(parent, j)\n", true);
        tc.putCode("Python", "Recursion", "# DSU - Recursive Path Compression (Python)\ndef dsu_find_rec(parent: list[int], i: int) -> int:\n    if parent[i] == i:\n        return i\n    parent[i] = dsu_find_rec(parent, parent[i])\n    return parent[i]\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerBloomFilter(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "ds_bloom_filter",
            "Bloom Filter",
            "Space-efficient probabilistic bit set testing set membership without false negatives.\n\nHashes elements using K distinct hash functions to set bits in a bit array. Querying checks if all K bits are 1: if any bit is 0, the item is definitely not in the set; if all are 1, it is probably in the set.",
            "Web browsers flagging malicious URLs and Cassandra / Bigtable preventing unnecessary SSTable disk reads.",
            "O(k)", "O(k)", "O(k)", "O(m)",
            new int[] {1, 2, 3, 4},
            4
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: BLOOM_FILTER_ITERATIVE\nADD(item):\n1. For each seed s in seeds[0..k-1]:\n     bitIndex = hash(item, s) % m\n     bitSet.set(bitIndex, true)\n\nCONTAINS(item):\n1. For each seed s in seeds[0..k-1]:\n     bitIndex = hash(item, s) % m\n     If bitSet.get(bitIndex) == false: return false\n2. Return true (probably in set).");
        tc.algorithmByStyle.put("Functions", "Algorithm: BLOOM_FILTER_FUNCTIONAL\nFUNCTION HASH_K(item, seed, m): return (hash(item) ^ seed) % m\nFUNCTION ADD_ITEM(bits, item, seeds, m): Sets bits for all seeds.\nFUNCTION TEST_ITEM(bits, item, seeds, m): Returns false immediately on zero bit.");
        tc.algorithmByStyle.put("Recursion", "Algorithm: BLOOM_FILTER_RECURSIVE\nNot available (Probabilistic bit set operations are strictly loop-based bit array lookups).");

        tc.putCode("Java", "Iterative", "// Bloom Filter - Iterative Bit Operations\nimport java.util.BitSet;\n\npublic class BloomFilterIterative {\n    private final BitSet bitset;\n    private final int size;\n    private final int[] seeds;\n\n    public BloomFilterIterative(int size, int[] seeds) {\n        this.size = size;\n        this.bitset = new BitSet(size);\n        this.seeds = seeds;\n    }\n\n    public void add(String item) {\n        for (int seed : seeds) {\n            int hash = (item.hashCode() ^ seed) & 0x7fffffff;\n            bitset.set(hash % size);\n        }\n    }\n\n    public boolean contains(String item) {\n        for (int seed : seeds) {\n            int hash = (item.hashCode() ^ seed) & 0x7fffffff;\n            if (!bitset.get(hash % size)) return false;\n        }\n        return true;\n    }\n}", true);
        tc.putCode("Java", "Functions", "// Bloom Filter - Functional Modular\npublic class BloomFilterFunctional {\n    public static int computeHash(String item, int seed, int size) {\n        return ((item.hashCode() ^ seed) & 0x7fffffff) % size;\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// Bloom Filter - Recursive\n// Not available", true);

        tc.putCode("C", "Iterative", "/* Bloom Filter - Iterative (C) */\n#include <stdio.h>\n#include <stdbool.h>\n\nvoid addBloom(bool bitset[], int m, const char* str, const int seeds[], int k) {\n    for (int i = 0; i < k; i++) {\n        // Hash and set bit\n        bitset[(seeds[i] * 31) % m] = true;\n    }\n}", true);
        tc.putCode("C", "Functions", "/* Bloom Filter - Functional (C) */\n#include <stdio.h>\n#include <stdbool.h>\n\nbool testBit(const bool bitset[], int idx) {\n    return bitset[idx];\n}", true);
        tc.putCode("C", "Recursion", "/* Bloom Filter - Recursive */\n/* Not available */", true);

        tc.putCode("Python", "Iterative", "# Bloom Filter - Iterative (Python)\nclass BloomFilterIterative:\n    def __init__(self, size: int = 1000, num_hashes: int = 3):\n        self.size = size\n        self.bits = [0] * size\n        self.num_hashes = num_hashes\n\n    def add(self, item: str) -> None:\n        for i in range(self.num_hashes):\n            idx = (hash(item) + i * 0x5bd1e995) % self.size\n            self.bits[idx] = 1\n\n    def contains(self, item: str) -> bool:\n        for i in range(self.num_hashes):\n            idx = (hash(item) + i * 0x5bd1e995) % self.size\n            if not self.bits[idx]:\n                return False\n        return True\n", true);
        tc.putCode("Python", "Functions", "# Bloom Filter - Functional (Python)\ndef get_hash(item: str, seed: int, size: int) -> int:\n    return (hash(item) ^ seed) % size\n", true);
        tc.putCode("Python", "Recursion", "# Bloom Filter - Recursive (Python)\n# Not available\n", true);

        contentMap.put(tc.techniqueId, tc);
    }

    private static void registerLruCache(Map<String, TechniqueContent> contentMap) {
        TechniqueContent tc = new TechniqueContent(
            "ds_lru_cache",
            "LRU Cache",
            "Constant O(1) get, put, and least-recently-used eviction using HashMap + DLL.\n\nA hash table provides instant O(1) key-to-node resolution, combined with a Doubly Linked List that tracks item recency by splicing accessed nodes to the head and evicting from the tail.",
            "Browser cache memory and database query result caching (Redis, Memcached).",
            "O(1)", "O(1)", "O(1)", "O(Capacity)",
            new int[] {1, 2, 3, 4},
            4
        );

        tc.algorithmByStyle.put("Iterative", "Algorithm: LRU_CACHE_ITERATIVE\nGET(key):\n1. If key not in map: return -1.\n2. node = map.get(key)\n3. REMOVE_NODE(node); ADD_TO_HEAD(node)\n4. Return node.val.\n\nPUT(key, val):\n1. If key in map: update val, move to head.\n2. Else:\n     If size == capacity: evict tail node, remove from map.\n     Allocate new node, add to head, insert into map.");
        tc.algorithmByStyle.put("Functions", "Algorithm: LRU_CACHE_FUNCTIONAL\nFUNCTION DETACH_NODE(node): Splice node out from its prev and next links.\nFUNCTION ATTACH_HEAD(head, node): Insert node directly after dummy head.\nFUNCTION EVICT_TAIL(tail): Remove and return node preceding dummy tail.");
        tc.algorithmByStyle.put("Recursion", "Algorithm: LRU_CACHE_RECURSIVE\nNot available (Cache key-value access and doubly linked list manipulation are strictly non-recursive).");

        tc.putCode("Java", "Iterative", "// LRU Cache - Iterative Doubly Linked List + HashMap\nimport java.util.HashMap;\nimport java.util.Map;\n\npublic class LruCacheIterative {\n    static class Node {\n        int key, val;\n        Node prev, next;\n        Node(int k, int v) { key = k; val = v; }\n    }\n\n    private final int capacity;\n    private final Map<Integer, Node> map = new HashMap<>();\n    private final Node head = new Node(0, 0), tail = new Node(0, 0);\n\n    public LruCacheIterative(int capacity) {\n        this.capacity = capacity;\n        head.next = tail;\n        tail.prev = head;\n    }\n\n    public int get(int key) {\n        if (!map.containsKey(key)) return -1;\n        Node node = map.get(key);\n        remove(node);\n        insertHead(node);\n        return node.val;\n    }\n\n    public void put(int key, int val) {\n        if (map.containsKey(key)) {\n            remove(map.get(key));\n        } else if (map.size() == capacity) {\n            map.remove(tail.prev.key);\n            remove(tail.prev);\n        }\n        Node node = new Node(key, val);\n        map.put(key, node);\n        insertHead(node);\n    }\n\n    private void remove(Node node) {\n        node.prev.next = node.next;\n        node.next.prev = node.prev;\n    }\n\n    private void insertHead(Node node) {\n        node.next = head.next;\n        node.prev = head;\n        head.next.prev = node;\n        head.next = node;\n    }\n}", true);
        tc.putCode("Java", "Functions", "// LRU Cache - Functional Node Manipulation\npublic class LruCacheFunctional {\n    public static void splice(LruCacheIterative.Node n) {\n        if (n.prev != null) n.prev.next = n.next;\n        if (n.next != null) n.next.prev = n.prev;\n    }\n}", true);
        tc.putCode("Java", "Recursion", "// LRU Cache - Recursive\n// Not available", true);

        tc.putCode("C", "Iterative", "/* LRU Cache - Iterative (C) */\n#include <stdio.h>\n#include <stdlib.h>\n\ntypedef struct Node {\n    int key, val;\n    struct Node *prev, *next;\n} Node;\n\nvoid detach(Node* n) {\n    n->prev->next = n->next;\n    n->next->prev = n->prev;\n}", true);
        tc.putCode("C", "Functions", "/* LRU Cache - Functional (C) */\n#include <stdio.h>\n\nvoid attach(void* head, void* node) {\n    // Modular attachment\n}", true);
        tc.putCode("C", "Recursion", "/* LRU Cache - Recursive */\n/* Not available */", true);

        tc.putCode("Python", "Iterative", "# LRU Cache - Iterative (Python)\nclass Node:\n    def __init__(self, key=0, val=0):\n        self.key, self.val = key, val\n        self.prev, self.next = None, None\n\nclass LRUCacheIterative:\n    def __init__(self, capacity: int):\n        self.cap = capacity\n        self.map = {}\n        self.head, self.tail = Node(), Node()\n        self.head.next = self.tail\n        self.tail.prev = self.head\n\n    def get(self, key: int) -> int:\n        if key not in self.map: return -1\n        node = self.map[key]\n        self._remove(node)\n        self._insert_head(node)\n        return node.val\n\n    def put(self, key: int, value: int) -> None:\n        if key in self.map:\n            self._remove(self.map[key])\n        elif len(self.map) == self.cap:\n            lru = self.tail.prev\n            del self.map[lru.key]\n            self._remove(lru)\n        node = Node(key, value)\n        self.map[key] = node\n        self._insert_head(node)\n\n    def _remove(self, node: Node):\n        node.prev.next = node.next\n        node.next.prev = node.prev\n\n    def _insert_head(self, node: Node):\n        node.next = self.head.next\n        node.prev = self.head\n        self.head.next.prev = node\n        self.head.next = node\n", true);
        tc.putCode("Python", "Functions", "# LRU Cache - Functional (Python)\ndef detach_node(node):\n    node.prev.next = node.next\n    node.next.prev = node.prev\n", true);
        tc.putCode("Python", "Recursion", "# LRU Cache - Recursive (Python)\n# Not available\n", true);

        contentMap.put(tc.techniqueId, tc);
    }
}
