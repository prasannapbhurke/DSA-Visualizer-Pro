package com.dsavisualizer.app.data.snippets;

public final class CSnippetProvider {

    private CSnippetProvider() {
    }

    public static String getCCodeSnippet(String techniqueId, String style) {
        if ("selection_sort".equals(techniqueId)) {
            return getCSelectionSortPdf(style);
        }
        if ("insertion_sort".equals(techniqueId)) {
            return getCInsertionSortPdf(style);
        }
        if ("merging".equals(techniqueId)) {
            return getCMergingPdf(style);
        }
        if ("merge_sort".equals(techniqueId)) {
            return getCMergeSortPdf(style);
        }
        if ("radix_sort".equals(techniqueId)) {
            return getCRadixSortPdf(style);
        }
        if ("find_linked_list".equals(techniqueId)) {
            return getCSearchInLinkedList(style);
        }
        if ("delete_linked_list".equals(techniqueId)) {
            return getCDeleteNodeByItemLinkedList(style);
        }
        switch (techniqueId) {
            case "linear_search":
                if ("Functions".equals(style)) {
                    return "#include <stdio.h>\n"
                        + "int linear(int arr[], int n, int item) {\n"
                        + "    int loc = 1;\n"
                        + "    arr[n + 1] = item;\n"
                        + "    while(arr[loc] != item)\n"
                        + "        loc++;\n\n"
                        + "    if(loc == n + 1)\n"
                        + "        return 0;\n"
                        + "    else\n"
                        + "        return loc;\n"
                        + "}\n"
                        + "int main() {\n"
                        + "    int n, i, item, loc;\n"
                        + "    printf(\"Enter number of elements: \");\n"
                        + "    scanf(\"%d\" , &n);\n\n"
                        + "    int arr[n + 2];\n\n"
                        + "    printf(\"Enter elements:\\n\");\n"
                        + "    for(i = 1; i <= n; i++)\n"
                        + "        scanf(\"%d\" , &arr[i]);\n\n"
                        + "    printf(\"Enter item to search: \");\n"
                        + "    scanf(\"%d\" , &item);\n\n"
                        + "    loc = linear(arr, n, item);\n\n"
                        + "    printf(\"Location: %d\\n\" , loc);\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "#include <stdio.h>\n"
                        + "int linear(int arr[], int n, int item, int loc) {\n"
                        + "    if(arr[loc] == item) {\n"
                        + "        if(loc == n + 1)\n"
                        + "            return 0;\n"
                        + "        else\n"
                        + "            return loc;\n"
                        + "    }\n"
                        + "    return linear(arr, n, item, loc + 1);\n"
                        + "}\n"
                        + "int main() {\n"
                        + "    int n, i, item, loc;\n"
                        + "    printf(\"Enter number of elements: \");\n"
                        + "    scanf(\"%d\" , &n);\n\n"
                        + "    int arr[n + 2];\n\n"
                        + "    printf(\"Enter elements:\\n\");\n"
                        + "    for(i = 1; i <= n; i++)\n"
                        + "        scanf(\"%d\" , &arr[i]);\n\n"
                        + "    printf(\"Enter item to search: \");\n"
                        + "    scanf(\"%d\" , &item);\n\n"
                        + "    arr[n + 1] = item;\n"
                        + "    loc = linear(arr, n, item, 1);\n\n"
                        + "    printf(\"Location: %d\\n\" , loc);\n"
                        + "}";
                }
                return "#include <stdio.h>\n"
                    + "int main() {\n"
                    + "    int n, i, item, loc;\n"
                    + "    printf(\"Enter number of elements: \");\n"
                    + "    scanf(\"%d\" , &n);\n\n"
                    + "    int arr[n + 1];\n\n"
                    + "    printf(\"Enter elements:\\n\");\n"
                    + "    for(i = 1; i <= n; i++)\n"
                    + "        scanf(\"%d\" , &arr[i]);\n\n"
                    + "    printf(\"Enter item to search: \");\n"
                    + "    scanf(\"%d\" , &item);\n\n"
                    + "    arr[n + 1] = item;\n"
                    + "    loc = 1;\n\n"
                    + "    while(arr[loc] != item)\n"
                    + "        loc++;\n\n"
                    + "    if(loc == n + 1)\n"
                    + "        loc = 0;\n\n"
                    + "    printf(\"Location: %d\\n\" , loc);\n"
                    + "}";
            case "binary_search":
                if ("Functions".equals(style)) {
                    return "#include <stdio.h>\n"
                        + "int binary(int arr[], int lb, int ub, int item) {\n"
                        + "    int beg = lb, end = ub, mid;\n"
                        + "    while(beg <= end) {\n"
                        + "        mid = (beg + end) / 2;\n\n"
                        + "        if(arr[mid] == item)\n"
                        + "            return mid;\n"
                        + "        else if(item < arr[mid])\n"
                        + "            end = mid - 1;\n"
                        + "        else\n"
                        + "            beg = mid + 1;\n"
                        + "    }\n"
                        + "    return -1;\n"
                        + "}\n"
                        + "int main() {\n"
                        + "    int n, i, item;\n"
                        + "    printf(\"Enter number of elements: \");\n"
                        + "    scanf(\"%d\" , &n);\n\n"
                        + "    int arr[n];\n\n"
                        + "    printf(\"Enter sorted elements:\\n\");\n"
                        + "    for(i = 0; i < n; i++)\n"
                        + "        scanf(\"%d\" , &arr[i]);\n\n"
                        + "    printf(\"Enter item to search: \");\n"
                        + "    scanf(\"%d\" , &item);\n\n"
                        + "    int loc = binary(arr, 0, n - 1, item);\n\n"
                        + "    printf(\"Location: %d\\n\" , loc);\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "#include <stdio.h>\n"
                        + "int binary(int arr[], int beg, int end, int item) {\n"
                        + "    if(beg > end)\n"
                        + "        return -1;\n"
                        + "    int mid = (beg + end) / 2;\n\n"
                        + "    if(arr[mid] == item)\n"
                        + "        return mid;\n"
                        + "    else if(item < arr[mid])\n"
                        + "        return binary(arr, beg, mid - 1, item);\n"
                        + "    else\n"
                        + "        return binary(arr, mid + 1, end, item);\n"
                        + "}\n"
                        + "int main() {\n"
                        + "    int n, i, item;\n"
                        + "    printf(\"Enter number of elements: \");\n"
                        + "    scanf(\"%d\" , &n);\n\n"
                        + "    int arr[n];\n\n"
                        + "    printf(\"Enter sorted elements:\\n\");\n"
                        + "    for(i = 0; i < n; i++)\n"
                        + "        scanf(\"%d\" , &arr[i]);\n\n"
                        + "    printf(\"Enter item to search: \");\n"
                        + "    scanf(\"%d\" , &item);\n\n"
                        + "    int loc = binary(arr, 0, n - 1, item);\n\n"
                        + "    printf(\"Location: %d\\n\" , loc);\n"
                        + "}";
                }
                return "#include <stdio.h>\n"
                    + "int main() {\n"
                    + "    int n, i, item, loc = -1;\n"
                    + "    printf(\"Enter number of elements: \");\n"
                    + "    scanf(\"%d\" , &n);\n\n"
                    + "    int arr[n];\n\n"
                    + "    printf(\"Enter sorted elements:\\n\");\n"
                    + "    for(i = 0; i < n; i++)\n"
                    + "        scanf(\"%d\" , &arr[i]);\n\n"
                    + "    printf(\"Enter item to search: \");\n"
                    + "    scanf(\"%d\" , &item);\n\n"
                    + "    int beg = 0, end = n - 1, mid;\n"
                    + "    mid = (beg + end) / 2;\n\n"
                    + "    while(beg <= end && arr[mid] != item) {\n"
                    + "        if(item < arr[mid])\n"
                    + "            end = mid - 1;\n"
                    + "        else\n"
                    + "            beg = mid + 1;\n\n"
                    + "        mid = (beg + end) / 2;\n"
                    + "    }\n\n"
                    + "    if(beg <= end && arr[mid] == item)\n"
                    + "        loc = mid;\n"
                    + "    else\n"
                    + "        loc = -1;\n\n"
                    + "    printf(\"Location: %d\\n\" , loc);\n"
                    + "}";
            case "jump_search":
                if ("Functions".equals(style)) {
                    return "#include <math.h>\n\n"
                        + "int get_block_size(int n) {\n"
                        + "    return (int)sqrt(n);\n"
                        + "}\n\n"
                        + "int scan_block(int arr[], int target, int start, int end) {\n"
                        + "    for (int i = start; i < end; i++) {\n"
                        + "        if (arr[i] == target) {\n"
                        + "            return i;\n"
                        + "        }\n"
                        + "    }\n"
                        + "    return -1;\n"
                        + "}\n\n"
                        + "int jump_search(int arr[], int n, int target) {\n"
                        + "    int block_size = get_block_size(n);\n"
                        + "    int start = 0;\n"
                        + "    while (start < n && arr[(start + block_size < n ? start + block_size : n) - 1] < target) {\n"
                        + "        start += block_size;\n"
                        + "    }\n"
                        + "    return scan_block(arr, target, start, (start + block_size < n ? start + block_size : n));\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "#include <math.h>\n\n"
                        + "int recursive_scan(int arr[], int target, int index, int end) {\n"
                        + "    if (index > end) {\n"
                        + "        return -1;\n"
                        + "    }\n"
                        + "    if (arr[index] == target) {\n"
                        + "        return index;\n"
                        + "    }\n"
                        + "    return recursive_scan(arr, target, index + 1, end);\n"
                        + "}\n\n"
                        + "int jump_search_recursive(int arr[], int n, int target, int start, int block_size) {\n"
                        + "    if (start >= n) {\n"
                        + "        return -1;\n"
                        + "    }\n"
                        + "    int end = start + block_size < n ? start + block_size : n;\n"
                        + "    if (arr[end - 1] >= target) {\n"
                        + "        return recursive_scan(arr, target, start, end - 1);\n"
                        + "    }\n"
                        + "    return jump_search_recursive(arr, n, target, end, block_size);\n"
                        + "}";
                }
                return "int jump_search(int arr[], int n, int target) {\n"
                    + "    int step = (int)sqrt(n);\n"
                    + "    int prev = 0;\n"
                    + "    while (prev < n && arr[(step < n ? step : n) - 1] < target) {\n"
                    + "        prev = step;\n"
                    + "        step += (int)sqrt(n);\n"
                    + "        if (prev >= n) {\n"
                    + "            return -1;\n"
                    + "        }\n"
                    + "    }\n"
                    + "    while (prev < n && prev < step) {\n"
                    + "        if (arr[prev] == target) {\n"
                    + "            return prev;\n"
                    + "        }\n"
                    + "        prev++;\n"
                    + "    }\n"
                    + "    return -1;\n"
                    + "}";
            case "bubble_sort":
                if ("Functions".equals(style)) {
                    return "#include <stdio.h>\n\n"
                        + "void bubble(int arr[], int n) {\n"
                        + "    int k, ptr, temp;\n"
                        + "    for(k = 1; k <= n - 1; k++) {\n"
                        + "        ptr = 0;\n"
                        + "        while(ptr <= n - k - 1) {\n"
                        + "            if(arr[ptr] < arr[ptr + 1]) {\n"
                        + "                temp = arr[ptr];\n"
                        + "                arr[ptr] = arr[ptr + 1];\n"
                        + "                arr[ptr + 1] = temp;\n"
                        + "            }\n"
                        + "            ptr++;\n"
                        + "        }\n"
                        + "    }\n"
                        + "}\n\n"
                        + "int main() {\n"
                        + "    int n, i;\n"
                        + "    printf(\"Enter number of elements: \");\n"
                        + "    scanf(\"%d\", &n);\n\n"
                        + "    int arr[n];\n"
                        + "    printf(\"Enter elements:\\n\");\n"
                        + "    for(i = 0; i < n; i++)\n"
                        + "        scanf(\"%d\", &arr[i]);\n\n"
                        + "    bubble(arr, n);\n\n"
                        + "    printf(\"Sorted array:\\n\");\n"
                        + "    for(i = 0; i < n; i++)\n"
                        + "        printf(\"%d \", arr[i]);\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "#include <stdio.h>\n\n"
                        + "void bubble(int arr[], int n) {\n"
                        + "    if(n == 1)\n"
                        + "        return;\n\n"
                        + "    int ptr = 0, temp;\n\n"
                        + "    while(ptr <= n - 2) {\n"
                        + "        if(arr[ptr] < arr[ptr + 1]) {\n"
                        + "            temp = arr[ptr];\n"
                        + "            arr[ptr] = arr[ptr + 1];\n"
                        + "            arr[ptr + 1] = temp;\n"
                        + "        }\n"
                        + "        ptr++;\n"
                        + "    }\n\n"
                        + "    bubble(arr, n - 1);\n"
                        + "}\n\n"
                        + "int main() {\n"
                        + "    int n, i;\n"
                        + "    printf(\"Enter number of elements: \");\n"
                        + "    scanf(\"%d\", &n);\n\n"
                        + "    int arr[n];\n"
                        + "    printf(\"Enter elements:\\n\");\n"
                        + "    for(i = 0; i < n; i++)\n"
                        + "        scanf(\"%d\", &arr[i]);\n\n"
                        + "    bubble(arr, n);\n\n"
                        + "    printf(\"Sorted array:\\n\");\n"
                        + "    for(i = 0; i < n; i++)\n"
                        + "        printf(\"%d \", arr[i]);\n"
                        + "}";
                }
                return "#include <stdio.h>\n"
                    + "int main() {\n"
                    + "    int n, i, k, temp;\n\n"
                    + "    printf(\"Enter number of elements: \");\n"
                    + "    scanf(\"%d\", &n);\n\n"
                    + "    int arr[n];\n"
                    + "    printf(\"Enter elements:\\n\");\n"
                    + "    for(i = 0; i < n; i++)\n"
                    + "        scanf(\"%d\", &arr[i]);\n\n"
                    + "    for(k = 1; k <= n - 1; k++) {\n"
                    + "        int ptr = 0;\n"
                    + "        while(ptr <= n - k - 1) {\n"
                    + "            if(arr[ptr] < arr[ptr + 1]) {\n"
                    + "                temp = arr[ptr];\n"
                    + "                arr[ptr] = arr[ptr + 1];\n"
                    + "                arr[ptr + 1] = temp;\n"
                    + "            }\n"
                    + "            ptr++;\n"
                    + "        }\n"
                    + "    }\n\n"
                    + "    printf(\"Sorted array:\\n\");\n"
                    + "    for(i = 0; i < n; i++)\n"
                    + "        printf(\"%d \", arr[i]);\n"
                    + "}";
            case "selection_sort":
                if ("Functions".equals(style)) {
                    return "int find_min_index(int arr[], int n, int start) {\n"
                        + "    int min_index = start;\n"
                        + "    for (int i = start + 1; i < n; i++) {\n"
                        + "        if (arr[i] < arr[min_index]) {\n"
                        + "            min_index = i;\n"
                        + "        }\n"
                        + "    }\n"
                        + "    return min_index;\n"
                        + "}\n\n"
                        + "void swap_values(int arr[], int left, int right) {\n"
                        + "    int temp = arr[left];\n"
                        + "    arr[left] = arr[right];\n"
                        + "    arr[right] = temp;\n"
                        + "}\n\n"
                        + "void selection_sort(int arr[], int n) {\n"
                        + "    for (int i = 0; i < n - 1; i++) {\n"
                        + "        int min_index = find_min_index(arr, n, i);\n"
                        + "        swap_values(arr, i, min_index);\n"
                        + "    }\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "void selection_sort_recursive(int arr[], int n, int start) {\n"
                        + "    if (start >= n - 1) {\n"
                        + "        return;\n"
                        + "    }\n"
                        + "    int min_index = start;\n"
                        + "    for (int i = start + 1; i < n; i++) {\n"
                        + "        if (arr[i] < arr[min_index]) {\n"
                        + "            min_index = i;\n"
                        + "        }\n"
                        + "    }\n"
                        + "    int temp = arr[start];\n"
                        + "    arr[start] = arr[min_index];\n"
                        + "    arr[min_index] = temp;\n"
                        + "    selection_sort_recursive(arr, n, start + 1);\n"
                        + "}";
                }
                return "void selection_sort(int arr[], int n) {\n"
                    + "    for (int i = 0; i < n - 1; i++) {\n"
                    + "        int min_index = i;\n"
                    + "        for (int j = i + 1; j < n; j++) {\n"
                    + "            if (arr[j] < arr[min_index]) {\n"
                    + "                min_index = j;\n"
                    + "            }\n"
                    + "        }\n"
                    + "        int temp = arr[i];\n"
                    + "        arr[i] = arr[min_index];\n"
                    + "        arr[min_index] = temp;\n"
                    + "    }\n"
                    + "}";
            case "insertion_sort":
                if ("Functions".equals(style)) {
                    return "void place_value(int arr[], int index) {\n"
                        + "    int key = arr[index];\n"
                        + "    int j = index - 1;\n"
                        + "    while (j >= 0 && arr[j] > key) {\n"
                        + "        arr[j + 1] = arr[j];\n"
                        + "        j--;\n"
                        + "    }\n"
                        + "    arr[j + 1] = key;\n"
                        + "}\n\n"
                        + "void insertion_sort(int arr[], int n) {\n"
                        + "    for (int i = 1; i < n; i++) {\n"
                        + "        place_value(arr, i);\n"
                        + "    }\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "void insertion_sort_recursive(int arr[], int n) {\n"
                        + "    if (n <= 1) {\n"
                        + "        return;\n"
                        + "    }\n"
                        + "    insertion_sort_recursive(arr, n - 1);\n"
                        + "    int key = arr[n - 1];\n"
                        + "    int j = n - 2;\n"
                        + "    while (j >= 0 && arr[j] > key) {\n"
                        + "        arr[j + 1] = arr[j];\n"
                        + "        j--;\n"
                        + "    }\n"
                        + "    arr[j + 1] = key;\n"
                        + "}";
                }
                return "void insertion_sort(int arr[], int n) {\n"
                    + "    for (int i = 1; i < n; i++) {\n"
                    + "        int key = arr[i];\n"
                    + "        int j = i - 1;\n"
                    + "        while (j >= 0 && arr[j] > key) {\n"
                    + "            arr[j + 1] = arr[j];\n"
                    + "            j--;\n"
                    + "        }\n"
                    + "        arr[j + 1] = key;\n"
                    + "    }\n"
                    + "}";
            case "stack_basics":
                return getCStackBasics(style);
            case "linked_stack_push":
                return getCLinkedStackPush(style);
            case "linked_stack_pop":
                return getCLinkedStackPop(style);
            case "quicksort":
                return getCQuicksort(style);
            case "queue_basics":
                return getCLinearQueue(style);
            case "circular_queue":
                return getCCircularQueue(style);
            case "linked_queue":
                return getCLinkedQueue(style);
            case "priority_queue_array":
                return getCPriorityQueueArray(style);
            case "priority_queue_2d":
                return getCPriorityQueue2d(style);
            case "preorder_traversal":
                return getCPreorderTraversal(style);
            case "postorder_traversal":
                return getCPostorderTraversal(style);
            case "bst_search_parent_tracking":
                return getCBstSearchParentTracking(style);
            case "bst_insertion":
                return getCBstInsertion(style);
            case "bst_deletion_case_a":
                return getCBstDeletionCaseA(style);
            case "bst_deletion_case_b":
                return getCBstDeletionCaseB(style);
            case "bst_deletion_del":
                return getCBstDeletionDel(style);
            case "max_heap_insertion":
                return getCMaxHeapInsertion(style);
            case "max_heap_deletion":
                return getCMaxHeapDeletion(style);
            case "heapsort":
                return getCHeapSort(style);
            case "singly_linked_list":
                if ("Functions".equals(style)) {
                    return "typedef struct Node {\n"
                        + "    int data;\n"
                        + "    struct Node *next;\n"
                        + "} Node;\n\n"
                        + "Node *create_node(int value) {\n"
                        + "    Node *node = (Node *)malloc(sizeof(Node));\n"
                        + "    node->data = value;\n"
                        + "    node->next = NULL;\n"
                        + "    return node;\n"
                        + "}\n\n"
                        + "Node *get_tail(Node *head) {\n"
                        + "    while (head->next != NULL) {\n"
                        + "        head = head->next;\n"
                        + "    }\n"
                        + "    return head;\n"
                        + "}\n\n"
                        + "Node *insert_at_end(Node *head, int value) {\n"
                        + "    if (head == NULL) {\n"
                        + "        return create_node(value);\n"
                        + "    }\n"
                        + "    get_tail(head)->next = create_node(value);\n"
                        + "    return head;\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "typedef struct Node {\n"
                        + "    int data;\n"
                        + "    struct Node *next;\n"
                        + "} Node;\n\n"
                        + "Node *insert_at_end_recursive(Node *head, int value) {\n"
                        + "    if (head == NULL) {\n"
                        + "        Node *node = (Node *)malloc(sizeof(Node));\n"
                        + "        node->data = value;\n"
                        + "        node->next = NULL;\n"
                        + "        return node;\n"
                        + "    }\n"
                        + "    head->next = insert_at_end_recursive(head->next, value);\n"
                        + "    return head;\n"
                        + "}";
                }
                return "typedef struct Node {\n"
                    + "    int data;\n"
                    + "    struct Node *next;\n"
                    + "} Node;\n\n"
                    + "void insert_at_end(Node *head, int value) {\n"
                    + "    Node *current = head;\n"
                    + "    while (current->next != NULL) {\n"
                    + "        current = current->next;\n"
                    + "    }\n"
                    + "    current->next = malloc(sizeof(Node));\n"
                    + "    current->next->data = value;\n"
                    + "    current->next->next = NULL;\n"
                    + "}";
            case "traversing_linked_list":
            case "print_linked_list":
                if ("Functions".equals(style)) {
                    return "#include <stdio.h>\n#include <stdlib.h>\n"
                        + "struct node {\nint data;\nstruct node* next;\n};\n"
                        + "void printList(struct node* ptr) {\nwhile(ptr != NULL) {\nprintf(\"%d \", ptr->data);\nptr = ptr->next;\n}\n}\n"
                        + "int main() {\nint n, i;\nprintf(\"Enter number of nodes: \");\nscanf(\"%d\" , &n);\n"
                        + "struct node *head = NULL, *temp, *newNode;\n"
                        + "for(i = 0; i < n; i++) {\nnewNode = (struct node*)malloc(sizeof(struct node));\nprintf(\"Enter data: \");\nscanf(\"%d\" , &newNode->data);\nnewNode->next = NULL;\nif(head == NULL)\nhead = temp = newNode;\nelse {\ntemp->next = newNode;\ntemp = newNode;\n}\n}\nprintList(head);\n}";
                }
                if ("Recursion".equals(style)) {
                    return "#include <stdio.h>\n#include <stdlib.h>\n"
                        + "struct node {\nint data;\nstruct node* next;\n};\n"
                        + "void printList(struct node* ptr) {\nif(ptr == NULL)\nreturn;\nprintf(\"%d \", ptr->data);\nprintList(ptr->next);\n}\n"
                        + "int main() {\nint n, i;\nprintf(\"Enter number of nodes: \");\nscanf(\"%d\" , &n);\n"
                        + "struct node *head = NULL, *temp, *newNode;\n"
                        + "for(i = 0; i < n; i++) {\nnewNode = (struct node*)malloc(sizeof(struct node));\nprintf(\"Enter data: \");\nscanf(\"%d\" , &newNode->data);\nnewNode->next = NULL;\nif(head == NULL)\nhead = temp = newNode;\nelse {\ntemp->next = newNode;\ntemp = newNode;\n}\n}\nprintList(head);\n}";
                }
                return "#include <stdio.h>\n#include <stdlib.h>\n"
                    + "struct node {\nint data;\nstruct node* next;\n};\n"
                    + "int main() {\nint n, i;\nprintf(\"Enter number of nodes: \");\nscanf(\"%d\" , &n);\nstruct node *head = NULL, *temp, *newNode;\n"
                    + "for(i = 0; i < n; i++) {\nnewNode = (struct node*)malloc(sizeof(struct node));\nprintf(\"Enter data: \");\nscanf(\"%d\" , &newNode->data);\nnewNode->next = NULL;\nif(head == NULL)\nhead = temp = newNode;\nelse {\ntemp->next = newNode;\ntemp = newNode;\n}\n}\nstruct node* ptr = head;\nwhile(ptr != NULL) {\nprintf(\"%d \", ptr->data);\nptr = ptr->next;\n}\n}";
            case "count_nodes_linked_list":
                return getCCountNodesLinkedList(style);
            case "search_in_linked_list":
                return getCSearchInLinkedList(style);
            case "insert_at_beginning_linked_list":
                return getCInsertAtBeginningLinkedList(style);
            case "insert_after_given_node_linked_list":
                return getCInsertAfterGivenNodeLinkedList(style);
            case "insert_into_sorted_linked_list":
                return getCInsertIntoSortedLinkedList(style);
            case "delete_node_given_location_linked_list":
                return getCDeleteNodeGivenLocationLinkedList(style);
            case "delete_node_by_item_linked_list":
                return getCDeleteNodeByItemLinkedList(style);
            case "traverse_circular_header_list":
                return getCTraverseCircularHeaderList(style);
            case "search_circular_header_list":
                return getCSearchCircularHeaderList(style);
            case "delete_circular_header_list":
                return getCDeleteCircularHeaderList(style);
            case "tree_traversal":
                if ("Functions".equals(style)) {
                    return "void visit_node(struct Node *node) {\n"
                        + "    if (node == NULL) {\n"
                        + "        return;\n"
                        + "    }\n"
                        + "    printf(\"%d \", node->data);\n"
                        + "}\n\n"
                        + "void preorder(struct Node *root) {\n"
                        + "    if (root == NULL) {\n"
                        + "        return;\n"
                        + "    }\n"
                        + "    visit_node(root);\n"
                        + "    preorder(root->left);\n"
                        + "    preorder(root->right);\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "void inorder(struct Node *root) {\n"
                        + "    if (root == NULL) {\n"
                        + "        return;\n"
                        + "    }\n"
                        + "    inorder(root->left);\n"
                        + "    printf(\"%d \", root->data);\n"
                        + "    inorder(root->right);\n"
                        + "}";
                }
                return "#include <stdio.h>\n\n"
                    + "void inorder_iterative(struct Node *root) {\n"
                    + "    struct Node *stack[100];\n"
                    + "    int top = -1;\n"
                    + "    struct Node *current = root;\n\n"
                    + "    while (current != NULL || top != -1) {\n"
                    + "        while (current != NULL) {\n"
                    + "            stack[++top] = current;\n"
                    + "            current = current->left;\n"
                    + "        }\n"
                    + "        current = stack[top--];\n"
                    + "        printf(\"%d \", current->data);\n"
                    + "        current = current->right;\n"
                    + "    }\n"
                    + "}";
            case "graph_traversal":
                if ("Functions".equals(style)) {
                    return "void mark_visited(int visited[], int node, int queue[], int *rear) {\n"
                        + "    visited[node] = 1;\n"
                        + "    queue[++(*rear)] = node;\n"
                        + "}\n\n"
                        + "void add_neighbors(int graph[10][10], int size, int node, int visited[], int queue[], int *rear) {\n"
                        + "    for (int next = 0; next < size; next++) {\n"
                        + "        if (graph[node][next] && !visited[next]) {\n"
                        + "            mark_visited(visited, next, queue, rear);\n"
                        + "        }\n"
                        + "    }\n"
                        + "}\n\n"
                        + "void bfs(int start, int graph[10][10], int size) {\n"
                        + "    int visited[10] = {0};\n"
                        + "    int queue[10];\n"
                        + "    int front = 0;\n"
                        + "    int rear = -1;\n"
                        + "    mark_visited(visited, start, queue, &rear);\n"
                        + "    while (front <= rear) {\n"
                        + "        int node = queue[front++];\n"
                        + "        printf(\"%d \", node);\n"
                        + "        add_neighbors(graph, size, node, visited, queue, &rear);\n"
                        + "    }\n"
                        + "}";
                }
                if ("Recursion".equals(style)) {
                    return "void dfs(int node, int graph[10][10], int visited[], int size) {\n"
                        + "    visited[node] = 1;\n"
                        + "    printf(\"%d \", node);\n"
                        + "    for (int next = 0; next < size; next++) {\n"
                        + "        if (graph[node][next] && !visited[next]) {\n"
                        + "            dfs(next, graph, visited, size);\n"
                        + "        }\n"
                        + "    }\n"
                        + "}";
                }
                return "void bfs(int start, int graph[10][10], int size) {\n"
                    + "    int visited[10] = {0};\n"
                    + "    int queue[10];\n"
                    + "    int front = 0;\n"
                    + "    int rear = -1;\n"
                    + "    queue[++rear] = start;\n"
                    + "    visited[start] = 1;\n"
                    + "    while (front <= rear) {\n"
                    + "        int node = queue[front++];\n"
                    + "        printf(\"%d \", node);\n"
                    + "        for (int next = 0; next < size; next++) {\n"
                    + "            if (graph[node][next] && !visited[next]) {\n"
                    + "                visited[next] = 1;\n"
                    + "                queue[++rear] = next;\n"
                    + "            }\n"
                    + "        }\n"
                    + "    }\n"
                    + "}";
            case "warshall_algorithm":
                return getCWarshallAlgorithm(style);
            case "floyd_algorithm":
                return getCFloydAlgorithm(style);
            case "find_edge_graph":
                return getCFindEdgeGraph(style);
            case "insert_node_graph":
                return getCInsertNodeGraph(style);
            case "insert_edge_graph":
                return getCInsertEdgeGraph(style);
            case "delete_edge_graph":
                return getCDeleteEdgeGraph(style);
            case "delete_vertex_graph":
                return getCDeleteVertexGraph(style);
            case "breadth_first_search":
                return getCBreadthFirstSearch(style);
            case "depth_first_search":
                return getCDepthFirstSearch(style);
            case "topological_sort":
                return getCTopologicalSort(style);
            default:
                return "/* C code for this topic will be added here. */";
        }
    }

    private static String getCCountNodesLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "struct node { int data; struct node* next; };",
                "int count(struct node* ptr) {",
                "    if(ptr == NULL) return 0;",
                "    return 1 + count(ptr->next);",
                "}");
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "struct node { int data; struct node* next; };",
                "int count(struct node* ptr) {",
                "    int num = 0;",
                "    while(ptr != NULL) {",
                "        num++;",
                "        ptr = ptr->next;",
                "    }",
                "    return num;",
                "}");
        }
        return String.join("\n",
            "struct node { int data; struct node* next; };",
            "int main() {",
            "    int num = 0;",
            "    struct node* ptr = NULL;",
            "    while(ptr != NULL) {",
            "        num++;",
            "        ptr = ptr->next;",
            "    }",
            "}");
    }

    private static String getCSearchInLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "struct node { int data; struct node* next; };",
                "struct node* search(struct node* ptr, int item) {",
                "    if(ptr == NULL) return NULL;",
                "    if(ptr->data == item) return ptr;",
                "    return search(ptr->next, item);",
                "}");
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "struct node { int data; struct node* next; };",
                "struct node* search(struct node* ptr, int item) {",
                "    while(ptr != NULL) {",
                "        if(ptr->data == item) return ptr;",
                "        ptr = ptr->next;",
                "    }",
                "    return NULL;",
                "}");
        }
        return String.join("\n",
            "struct node { int data; struct node* next; };",
            "int main() {",
            "    struct node *ptr = NULL, *loc = NULL;",
            "    int item = 0;",
            "    while(ptr != NULL) {",
            "        if(ptr->data == item) { loc = ptr; break; }",
            "        ptr = ptr->next;",
            "    }",
            "}");
    }

    private static String getCInsertAtBeginningLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return "/* Recursion is not applicable here because insertion at beginning is a single-step pointer update. */";
        }
        return String.join("\n",
            "int INFO[100], LINK[100];",
            "int START = -1, AVAIL = 0;",
            "void insFirst(int item) {",
            "    if(AVAIL == -1) return;",
            "    int NEW = AVAIL;",
            "    AVAIL = LINK[AVAIL];",
            "    INFO[NEW] = item;",
            "    LINK[NEW] = START;",
            "    START = NEW;",
            "}");
    }

    private static String getCInsertAfterGivenNodeLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return "/* Recursion is not applicable here because insertion after a given node is a direct pointer operation. */";
        }
        return String.join("\n",
            "int INFO[100], LINK[100];",
            "int START = -1, AVAIL = 0;",
            "void insLoc(int loc, int item) {",
            "    if(AVAIL == -1) return;",
            "    int NEW = AVAIL;",
            "    AVAIL = LINK[AVAIL];",
            "    INFO[NEW] = item;",
            "    if(loc == -1) { LINK[NEW] = START; START = NEW; }",
            "    else { LINK[NEW] = LINK[loc]; LINK[loc] = NEW; }",
            "}");
    }

    private static String getCInsertIntoSortedLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "int INFO[100], LINK[100];",
                "int findARec(int ptr, int item, int previous) {",
                "    if(ptr == -1) return previous;",
                "    if(item < INFO[ptr]) return previous;",
                "    return findARec(LINK[ptr], item, ptr);",
                "}");
        }
        return String.join("\n",
            "int INFO[100], LINK[100];",
            "int START = -1;",
            "int findA(int item) {",
            "    if(START == -1) return -1;",
            "    if(item < INFO[START]) return -1;",
            "    int save = START;",
            "    int ptr = LINK[START];",
            "    while(ptr != -1) {",
            "        if(item < INFO[ptr]) return save;",
            "        save = ptr;",
            "        ptr = LINK[ptr];",
            "    }",
            "    return save;",
            "}");
    }

    private static String getCDeleteNodeGivenLocationLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return "/* Recursion is not applicable here because deletion by location is a direct pointer update. */";
        }
        return String.join("\n",
            "int LINK[100];",
            "int START = -1, AVAIL = 0;",
            "void del(int loc, int locp) {",
            "    if(locp == -1) START = LINK[START];",
            "    else LINK[locp] = LINK[loc];",
            "    LINK[loc] = AVAIL;",
            "    AVAIL = loc;",
            "}");
    }

    private static String getCDeleteNodeByItemLinkedList(String style) {
        if ("Recursion".equals(style)) {
            return "/* Recursion is not suitable here because predecessor tracking is required. */";
        }
        return String.join("\n",
            "int INFO[100], LINK[100];",
            "int START = -1;",
            "void findb(int item, int *loc, int *locp) {",
            "    if(START == -1) { *loc = -1; *locp = -1; return; }",
            "    if(INFO[START] == item) { *loc = START; *locp = -1; return; }",
            "    int save = START;",
            "    int ptr = LINK[START];",
            "    while(ptr != -1) {",
            "        if(INFO[ptr] == item) { *loc = ptr; *locp = save; return; }",
            "        save = ptr;",
            "        ptr = LINK[ptr];",
            "    }",
            "    *loc = -1; *locp = -1;",
            "}");
    }

    private static String getCTraverseCircularHeaderList(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "void traverse(struct Node* ptr, struct Node* header) {",
                "    if(ptr == header) return;",
                "    printf(\"%d \", ptr->data);",
                "    traverse(ptr->next, header);",
                "}");
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "void traverse(struct Node* header) {",
                "    struct Node* ptr = header->next;",
                "    while(ptr != header) {",
                "        printf(\"%d \", ptr->data);",
                "        ptr = ptr->next;",
                "    }",
                "}");
        }
        return String.join("\n",
            "struct Node* ptr = header->next;",
            "while(ptr != header) {",
            "    printf(\"%d \", ptr->data);",
            "    ptr = ptr->next;",
            "}");
    }

    private static String getCSearchCircularHeaderList(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "struct Node* search(struct Node* ptr, struct Node* header, int item) {",
                "    if(ptr == header) return NULL;",
                "    if(ptr->data == item) return ptr;",
                "    return search(ptr->next, header, item);",
                "}");
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "struct Node* search(struct Node* header, int item) {",
                "    struct Node* ptr = header->next;",
                "    while(ptr != header && ptr->data != item) {",
                "        ptr = ptr->next;",
                "    }",
                "    if(ptr != header) return ptr;",
                "    return NULL;",
                "}");
        }
        return String.join("\n",
            "struct Node* ptr = header->next;",
            "while(ptr != header && ptr->data != item) {",
            "    ptr = ptr->next;",
            "}",
            "if(ptr != header) printf(\"Element found\\n\");",
            "else printf(\"Element not found\\n\");");
    }

    private static String getCDeleteCircularHeaderList(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "struct Node* find(struct Node* ptr, struct Node* header, struct Node* prev, int item, struct Node** locp) {",
                "    if(ptr == header) { *locp = prev; return NULL; }",
                "    if(ptr->data == item) { *locp = prev; return ptr; }",
                "    return find(ptr->next, header, ptr, item, locp);",
                "}");
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "void deleteNode(struct Node* header, int item) {",
                "    struct Node* ptr = header->next;",
                "    struct Node* save = header;",
                "    while(ptr != header && ptr->data != item) {",
                "        save = ptr;",
                "        ptr = ptr->next;",
                "    }",
                "    if(ptr == header) return;",
                "    save->next = ptr->next;",
                "}");
        }
        return String.join("\n",
            "struct Node* ptr = header->next;",
            "struct Node* save = header;",
            "while(ptr != header && ptr->data != item) {",
            "    save = ptr;",
            "    ptr = ptr->next;",
            "}",
            "if(ptr == header) printf(\"Item not found\\n\");",
            "else save->next = ptr->next;");
    }

    private static String getCStackBasics(String style) {
        if ("Recursion".equals(style)) {
            return "/* Recursion is not applicable for array stack PUSH and POP in this book style. */";
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "#define MAX 10",
                "",
                "int STACK[MAX];",
                "int TOP = -1;",
                "",
                "void push(int item) {",
                "    if(TOP == MAX - 1) {",
                "        printf(\"Overflow\\n\");",
                "        return;",
                "    }",
                "    TOP = TOP + 1;",
                "    STACK[TOP] = item;",
                "}",
                "",
                "int pop() {",
                "    int item;",
                "    if(TOP == -1) {",
                "        printf(\"Underflow\\n\");",
                "        return -1;",
                "    }",
                "    item = STACK[TOP];",
                "    TOP = TOP - 1;",
                "    return item;",
                "}",
                "",
                "int main() {",
                "    int choice, item;",
                "    do {",
                "        printf(\"1. PUSH\\n2. POP\\n3. EXIT\\n\");",
                "        printf(\"Enter choice: \");",
                "        scanf(\"%d\", &choice);",
                "",
                "        if(choice == 1) {",
                "            printf(\"Enter item: \");",
                "            scanf(\"%d\", &item);",
                "            push(item);",
                "        } else if(choice == 2) {",
                "            item = pop();",
                "            if(item != -1) {",
                "                printf(\"Deleted item: %d\\n\", item);",
                "            }",
                "        }",
                "    } while(choice != 3);",
                "    return 0;",
                "}");
        }
        return String.join("\n",
            "#include <stdio.h>",
            "#define MAX 10",
            "",
            "int main() {",
            "    int STACK[MAX];",
            "    int TOP = -1;",
            "    int choice, item;",
            "",
            "    do {",
            "        printf(\"1. PUSH\\n2. POP\\n3. EXIT\\n\");",
            "        printf(\"Enter choice: \");",
            "        scanf(\"%d\", &choice);",
            "",
            "        if(choice == 1) {",
            "            if(TOP == MAX - 1) {",
            "                printf(\"Overflow\\n\");",
            "            } else {",
            "                printf(\"Enter item: \");",
            "                scanf(\"%d\", &item);",
            "                TOP = TOP + 1;",
            "                STACK[TOP] = item;",
            "            }",
            "        } else if(choice == 2) {",
            "            if(TOP == -1) {",
            "                printf(\"Underflow\\n\");",
            "            } else {",
            "                item = STACK[TOP];",
            "                TOP = TOP - 1;",
            "                printf(\"Deleted item: %d\\n\", item);",
            "            }",
            "        }",
            "    } while(choice != 3);",
            "    return 0;",
            "}");
    }

    private static String getCLinearQueue(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "",
                "void insert(int queue[], int *front, int *rear, int n, int item) {",
                "    if (*rear == n) {",
                "        printf(\"OVERFLOW\\n\");",
                "        return;",
                "    }",
                "    if (*front == -1) {",
                "        *front = 0;",
                "        *rear = 0;",
                "    } else {",
                "        (*rear)++;",
                "    }",
                "    queue[*rear] = item;",
                "}",
                "",
                "void delete(int queue[], int *front, int *rear) {",
                "    if (*front == -1) {",
                "        printf(\"UNDERFLOW\\n\");",
                "        return;",
                "    }",
                "    printf(\"Deleted: %d\\n\", queue[*front]);",
                "    if (*front == *rear) {",
                "        *front = -1;",
                "        *rear = -1;",
                "    } else {",
                "        (*front)++;",
                "    }",
                "}",
                "",
                "int main() {",
                "    int n, front = -1, rear = -1, item;",
                "    printf(\"Enter queue size: \");",
                "    scanf(\"%d\", &n);",
                "    int queue[n + 1];",
                "    printf(\"Enter item to insert: \");",
                "    scanf(\"%d\", &item);",
                "    insert(queue, &front, &rear, n, item);",
                "    delete(queue, &front, &rear);",
                "    return 0;",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "",
                "int main() {",
                "    printf(\"Recursion not applicable for queue insertion and deletion\\n\");",
                "    return 0;",
                "}");
        }
        return String.join("\n",
            "#include <stdio.h>",
            "",
            "int main() {",
            "    int n, front = -1, rear = -1, item;",
            "    printf(\"Enter queue size: \");",
            "    scanf(\"%d\", &n);",
            "    int queue[n + 1];",
            "    printf(\"Enter item to insert: \");",
            "    scanf(\"%d\", &item);",
            "    if (rear == n) {",
            "        printf(\"OVERFLOW\\n\");",
            "    } else {",
            "        if (front == -1) {",
            "            front = 0;",
            "            rear = 0;",
            "        } else {",
            "            rear++;",
            "        }",
            "        queue[rear] = item;",
            "        printf(\"Item inserted\\n\");",
            "    }",
            "    if (front == -1) {",
            "        printf(\"UNDERFLOW\\n\");",
            "    } else {",
            "        int deleted = queue[front];",
            "        if (front == rear) {",
            "            front = -1;",
            "            rear = -1;",
            "        } else {",
            "            front++;",
            "        }",
            "        printf(\"Deleted: %d\\n\", deleted);",
            "    }",
            "    return 0;",
            "}");
    }

    private static String getCCircularQueue(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "",
                "void insert(int queue[], int *front, int *rear, int n, int item) {",
                "    if ((*front == 0 && *rear == n - 1) || (*front == *rear + 1)) {",
                "        printf(\"OVERFLOW\\n\");",
                "        return;",
                "    }",
                "    if (*front == -1) {",
                "        *front = 0;",
                "        *rear = 0;",
                "    } else if (*rear == n - 1) {",
                "        *rear = 0;",
                "    } else {",
                "        (*rear)++;",
                "    }",
                "    queue[*rear] = item;",
                "}",
                "",
                "void delete(int queue[], int *front, int *rear, int n) {",
                "    if (*front == -1) {",
                "        printf(\"UNDERFLOW\\n\");",
                "        return;",
                "    }",
                "    printf(\"Deleted: %d\\n\", queue[*front]);",
                "    if (*front == *rear) {",
                "        *front = -1;",
                "        *rear = -1;",
                "    } else if (*front == n - 1) {",
                "        *front = 0;",
                "    } else {",
                "        (*front)++;",
                "    }",
                "}",
                "",
                "int main() {",
                "    int n, front = -1, rear = -1, item;",
                "    printf(\"Enter queue size: \");",
                "    scanf(\"%d\", &n);",
                "    int queue[n];",
                "    printf(\"Enter item to insert: \");",
                "    scanf(\"%d\", &item);",
                "    insert(queue, &front, &rear, n, item);",
                "    delete(queue, &front, &rear, n);",
                "    return 0;",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "",
                "int main() {",
                "    printf(\"Recursion not applicable for circular queue operations\\n\");",
                "    return 0;",
                "}");
        }
        return String.join("\n",
            "#include <stdio.h>",
            "",
            "int main() {",
            "    int n, front = -1, rear = -1, item;",
            "    printf(\"Enter queue size: \");",
            "    scanf(\"%d\", &n);",
            "    int queue[n];",
            "    printf(\"Enter item to insert: \");",
            "    scanf(\"%d\", &item);",
            "    if ((front == 0 && rear == n - 1) || (front == rear + 1)) {",
            "        printf(\"OVERFLOW\\n\");",
            "    } else {",
            "        if (front == -1) {",
            "            front = 0;",
            "            rear = 0;",
            "        } else if (rear == n - 1) {",
            "            rear = 0;",
            "        } else {",
            "            rear++;",
            "        }",
            "        queue[rear] = item;",
            "        printf(\"Item inserted\\n\");",
            "    }",
            "    if (front == -1) {",
            "        printf(\"UNDERFLOW\\n\");",
            "    } else {",
            "        int deleted = queue[front];",
            "        if (front == rear) {",
            "            front = -1;",
            "            rear = -1;",
            "        } else if (front == n - 1) {",
            "            front = 0;",
            "        } else {",
            "            front++;",
            "        }",
            "        printf(\"Deleted: %d\\n\", deleted);",
            "    }",
            "    return 0;",
            "}");
    }

    private static String getCLinkedQueue(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "#include <stdlib.h>",
                "",
                "struct Node {",
                "    int data;",
                "    struct Node* next;",
                "};",
                "",
                "void insert(struct Node** front, struct Node** rear, int item) {",
                "    struct Node* newNode = (struct Node*)malloc(sizeof(struct Node));",
                "    newNode->data = item;",
                "    newNode->next = NULL;",
                "    if (*front == NULL) {",
                "        *front = *rear = newNode;",
                "    } else {",
                "        (*rear)->next = newNode;",
                "        *rear = newNode;",
                "    }",
                "}",
                "",
                "void delete(struct Node** front, struct Node** rear) {",
                "    if (*front == NULL) {",
                "        printf(\"UNDERFLOW\\n\");",
                "        return;",
                "    }",
                "    printf(\"Deleted: %d\\n\", (*front)->data);",
                "    struct Node* temp = *front;",
                "    *front = (*front)->next;",
                "    free(temp);",
                "    if (*front == NULL)",
                "        *rear = NULL;",
                "}",
                "",
                "int main() {",
                "    struct Node *front = NULL, *rear = NULL;",
                "    int item;",
                "    printf(\"Enter item to insert: \");",
                "    scanf(\"%d\", &item);",
                "    insert(&front, &rear, item);",
                "    printf(\"Item inserted\\n\");",
                "    delete(&front, &rear);",
                "    return 0;",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "",
                "int main() {",
                "    printf(\"Recursion not applicable for linked queue operations\\n\");",
                "    return 0;",
                "}");
        }
        return String.join("\n",
            "#include <stdio.h>",
            "#include <stdlib.h>",
            "",
            "struct Node {",
            "    int data;",
            "    struct Node* next;",
            "};",
            "",
            "int main() {",
            "    struct Node *front = NULL, *rear = NULL;",
            "    int item;",
            "    printf(\"Enter item to insert: \");",
            "    scanf(\"%d\", &item);",
            "    struct Node* newNode = (struct Node*)malloc(sizeof(struct Node));",
            "    newNode->data = item;",
            "    newNode->next = NULL;",
            "    if (front == NULL) {",
            "        front = rear = newNode;",
            "    } else {",
            "        rear->next = newNode;",
            "        rear = newNode;",
            "    }",
            "    printf(\"Item inserted\\n\");",
            "    if (front == NULL) {",
            "        printf(\"UNDERFLOW\\n\");",
            "    } else {",
            "        int deleted = front->data;",
            "        struct Node* temp = front;",
            "        front = front->next;",
            "        free(temp);",
            "        printf(\"Deleted: %d\\n\", deleted);",
            "    }",
            "    return 0;",
            "}");
    }

    private static String getCPriorityQueueArray(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "",
                "int insert(int data[], int priority[], int size, int item, int p) {",
                "    int i;",
                "    for (i = size - 1; i >= 0 && priority[i] > p; i--) {",
                "        data[i + 1] = data[i];",
                "        priority[i + 1] = priority[i];",
                "    }",
                "    data[i + 1] = item;",
                "    priority[i + 1] = p;",
                "    return size + 1;",
                "}",
                "",
                "int delete(int data[], int priority[], int size) {",
                "    if (size == 0) {",
                "        printf(\"UNDERFLOW\\n\");",
                "        return size;",
                "    }",
                "    printf(\"Deleted: %d\\n\", data[0]);",
                "    for (int i = 0; i < size - 1; i++) {",
                "        data[i] = data[i + 1];",
                "        priority[i] = priority[i + 1];",
                "    }",
                "    return size - 1;",
                "}",
                "",
                "int main() {",
                "    int n, size = 0, item, p;",
                "    printf(\"Enter size of queue: \");",
                "    scanf(\"%d\", &n);",
                "    int data[n], priority[n];",
                "    printf(\"Enter item: \");",
                "    scanf(\"%d\", &item);",
                "    printf(\"Enter priority: \");",
                "    scanf(\"%d\", &p);",
                "    size = insert(data, priority, size, item, p);",
                "    printf(\"Item inserted\\n\");",
                "    size = delete(data, priority, size);",
                "    return 0;",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "",
                "int main() {",
                "    printf(\"Recursion not applicable for priority queue (array)\\n\");",
                "    return 0;",
                "}");
        }
        return String.join("\n",
            "#include <stdio.h>",
            "",
            "int main() {",
            "    int n, size = 0, item, p;",
            "    printf(\"Enter size of queue: \");",
            "    scanf(\"%d\", &n);",
            "    int data[n], priority[n];",
            "    printf(\"Enter item: \");",
            "    scanf(\"%d\", &item);",
            "    printf(\"Enter priority: \");",
            "    scanf(\"%d\", &p);",
            "    int i;",
            "    for (i = size - 1; i >= 0 && priority[i] > p; i--) {",
            "        data[i + 1] = data[i];",
            "        priority[i + 1] = priority[i];",
            "    }",
            "    data[i + 1] = item;",
            "    priority[i + 1] = p;",
            "    size++;",
            "    printf(\"Item inserted\\n\");",
            "    if (size == 0) {",
            "        printf(\"UNDERFLOW\\n\");",
            "    } else {",
            "        int deleted = data[0];",
            "        for (int j = 0; j < size - 1; j++) {",
            "            data[j] = data[j + 1];",
            "            priority[j] = priority[j + 1];",
            "        }",
            "        size--;",
            "        printf(\"Deleted: %d\\n\", deleted);",
            "    }",
            "    return 0;",
            "}");
    }

    private static String getCPriorityQueue2d(String style) {
        if ("Functions".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "",
                "void insert(int queue[][100], int front[], int rear[], int n, int m, int item) {",
                "    if (rear[m] == n - 1) {",
                "        printf(\"OVERFLOW\\n\");",
                "        return;",
                "    }",
                "    if (front[m] == -1) {",
                "        front[m] = 0;",
                "        rear[m] = 0;",
                "    } else {",
                "        rear[m]++;",
                "    }",
                "    queue[m][rear[m]] = item;",
                "}",
                "",
                "void delete(int queue[][100], int front[], int rear[], int p) {",
                "    int k = -1;",
                "    for (int i = 0; i < p; i++) {",
                "        if (front[i] != -1) {",
                "            k = i;",
                "            break;",
                "        }",
                "    }",
                "    if (k == -1) {",
                "        printf(\"UNDERFLOW\\n\");",
                "        return;",
                "    }",
                "    printf(\"Deleted: %d\\n\", queue[k][front[k]]);",
                "    if (front[k] == rear[k]) {",
                "        front[k] = -1;",
                "        rear[k] = -1;",
                "    } else {",
                "        front[k]++;",
                "    }",
                "}",
                "",
                "int main() {",
                "    int p, n;",
                "    printf(\"Enter number of priority levels: \");",
                "    scanf(\"%d\", &p);",
                "    printf(\"Enter size of each queue: \");",
                "    scanf(\"%d\", &n);",
                "    int queue[100][100];",
                "    int front[100], rear[100];",
                "    for (int i = 0; i < p; i++) {",
                "        front[i] = -1;",
                "        rear[i] = -1;",
                "    }",
                "    int item, m;",
                "    printf(\"Enter item: \");",
                "    scanf(\"%d\", &item);",
                "    printf(\"Enter priority level: \");",
                "    scanf(\"%d\", &m);",
                "    insert(queue, front, rear, n, m, item);",
                "    printf(\"Item inserted\\n\");",
                "    delete(queue, front, rear, p);",
                "    return 0;",
                "}");
        }
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "",
                "int main() {",
                "    printf(\"Recursion not applicable for priority queue (2D array)\\n\");",
                "    return 0;",
                "}");
        }
        return String.join("\n",
            "#include <stdio.h>",
            "",
            "int main() {",
            "    int p, n;",
            "    printf(\"Enter number of priority levels: \");",
            "    scanf(\"%d\", &p);",
            "    printf(\"Enter size of each queue: \");",
            "    scanf(\"%d\", &n);",
            "    int queue[p][n];",
            "    int front[p], rear[p];",
            "    for (int i = 0; i < p; i++) {",
            "        front[i] = -1;",
            "        rear[i] = -1;",
            "    }",
            "    int item, m;",
            "    printf(\"Enter item: \");",
            "    scanf(\"%d\", &item);",
            "    printf(\"Enter priority level: \");",
            "    scanf(\"%d\", &m);",
            "    if (rear[m] == n - 1) {",
            "        printf(\"OVERFLOW\\n\");",
            "    } else {",
            "        if (front[m] == -1) {",
            "            front[m] = 0;",
            "            rear[m] = 0;",
            "        } else {",
            "            rear[m]++;",
            "        }",
            "        queue[m][rear[m]] = item;",
            "        printf(\"Item inserted\\n\");",
            "    }",
            "    int k = -1;",
            "    for (int i = 0; i < p; i++) {",
            "        if (front[i] != -1) {",
            "            k = i;",
            "            break;",
            "        }",
            "    }",
            "    if (k == -1) {",
            "        printf(\"UNDERFLOW\\n\");",
            "    } else {",
            "        int deleted = queue[k][front[k]];",
            "        if (front[k] == rear[k]) {",
            "            front[k] = -1;",
            "            rear[k] = -1;",
            "        } else {",
            "            front[k]++;",
            "        }",
            "        printf(\"Deleted: %d\\n\", deleted);",
            "    }",
            "    return 0;",
            "}");
    }

    private static String getCPreorderTraversal(String style) {
        String header = "#include <stdio.h>\n#include <stdlib.h>\n\n"
            + "struct Node {\n"
            + "    int data;\n"
            + "    struct Node *left, *right;\n"
            + "};\n\n"
            + "struct Node* create(int data) {\n"
            + "    struct Node* n = (struct Node*)malloc(sizeof(struct Node));\n"
            + "    n->data = data;\n"
            + "    n->left = n->right = NULL;\n"
            + "    return n;\n"
            + "}\n\n";
        if ("Functions".equals(style)) {
            return header
                + "void preorder(struct Node* ptr) {\n"
                + "    if (ptr == NULL) return;\n"
                + "    printf(\"%d \", ptr->data);\n"
                + "    preorder(ptr->left);\n"
                + "    preorder(ptr->right);\n"
                + "}\n\n"
                + "int main() {\n"
                + "    int n, i;\n"
                + "    printf(\"Enter number of nodes: \");\n"
                + "    scanf(\"%d\", &n);\n"
                + "    struct Node* nodes[100];\n"
                + "    for (i = 0; i < n; i++) {\n"
                + "        int val;\n"
                + "        printf(\"Enter value of node %d: \", i);\n"
                + "        scanf(\"%d\", &val);\n"
                + "        nodes[i] = create(val);\n"
                + "    }\n"
                + "    for (i = 0; i < n; i++) {\n"
                + "        int l, r;\n"
                + "        printf(\"Enter left and right child index of node %d (-1 if none): \", i);\n"
                + "        scanf(\"%d %d\", &l, &r);\n"
                + "        if (l != -1) nodes[i]->left = nodes[l];\n"
                + "        if (r != -1) nodes[i]->right = nodes[r];\n"
                + "    }\n"
                + "    preorder(nodes[0]);\n"
                + "    return 0;\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return header
                + "void preorderRec(struct Node* root) {\n"
                + "    if (root == NULL) return;\n"
                + "    printf(\"%d \", root->data);\n"
                + "    preorderRec(root->left);\n"
                + "    preorderRec(root->right);\n"
                + "}\n\n"
                + "int main() {\n"
                + "    int n, i;\n"
                + "    printf(\"Enter number of nodes: \");\n"
                + "    scanf(\"%d\", &n);\n"
                + "    struct Node* nodes[100];\n"
                + "    for (i = 0; i < n; i++) {\n"
                + "        int val;\n"
                + "        printf(\"Enter value of node %d: \", i);\n"
                + "        scanf(\"%d\", &val);\n"
                + "        nodes[i] = create(val);\n"
                + "    }\n"
                + "    for (i = 0; i < n; i++) {\n"
                + "        int l, r;\n"
                + "        printf(\"Enter left and right child index of node %d (-1 if none): \", i);\n"
                + "        scanf(\"%d %d\", &l, &r);\n"
                + "        if (l != -1) nodes[i]->left = nodes[l];\n"
                + "        if (r != -1) nodes[i]->right = nodes[r];\n"
                + "    }\n"
                + "    preorderRec(nodes[0]);\n"
                + "    return 0;\n"
                + "}";
        }
        return header
            + "int main() {\n"
            + "    int n, i;\n"
            + "    printf(\"Enter number of nodes: \");\n"
            + "    scanf(\"%d\", &n);\n"
            + "    struct Node* nodes[100];\n"
            + "    for (i = 0; i < n; i++) {\n"
            + "        int val;\n"
            + "        printf(\"Enter value of node %d: \", i);\n"
            + "        scanf(\"%d\", &val);\n"
            + "        nodes[i] = create(val);\n"
            + "    }\n"
            + "    for (i = 0; i < n; i++) {\n"
            + "        int l, r;\n"
            + "        printf(\"Enter left and right child index of node %d (-1 if none): \", i);\n"
            + "        scanf(\"%d %d\", &l, &r);\n"
            + "        if (l != -1) nodes[i]->left = nodes[l];\n"
            + "        if (r != -1) nodes[i]->right = nodes[r];\n"
            + "    }\n"
            + "    struct Node* stack[100];\n"
            + "    int top = 0;\n"
            + "    struct Node* ptr = nodes[0];\n"
            + "    stack[top++] = NULL;\n"
            + "    while (ptr != NULL) {\n"
            + "        printf(\"%d \", ptr->data);\n"
            + "        if (ptr->right != NULL) {\n"
            + "            stack[top++] = ptr->right;\n"
            + "        }\n"
            + "        if (ptr->left != NULL) {\n"
            + "            ptr = ptr->left;\n"
            + "        } else {\n"
            + "            ptr = stack[--top];\n"
            + "        }\n"
            + "    }\n"
            + "    return 0;\n"
            + "}";
    }

    private static String getCPostorderTraversal(String style) {
        String header = "#include <stdio.h>\n#include <stdlib.h>\n\n"
            + "struct Node {\n"
            + "    int data;\n"
            + "    struct Node *left, *right;\n"
            + "};\n\n"
            + "struct Node* create(int data) {\n"
            + "    struct Node* n = (struct Node*)malloc(sizeof(struct Node));\n"
            + "    n->data = data;\n"
            + "    n->left = n->right = NULL;\n"
            + "    return n;\n"
            + "}\n\n";
        if ("Functions".equals(style)) {
            return header
                + "void postorder(struct Node* ptr) {\n"
                + "    if (ptr == NULL) return;\n"
                + "    postorder(ptr->left);\n"
                + "    postorder(ptr->right);\n"
                + "    printf(\"%d \", ptr->data);\n"
                + "}\n\n"
                + "int main() {\n"
                + "    int n, i;\n"
                + "    printf(\"Enter number of nodes: \");\n"
                + "    scanf(\"%d\", &n);\n"
                + "    struct Node* nodes[100];\n"
                + "    for (i = 0; i < n; i++) {\n"
                + "        int val;\n"
                + "        printf(\"Enter value of node %d: \", i);\n"
                + "        scanf(\"%d\", &val);\n"
                + "        nodes[i] = create(val);\n"
                + "    }\n"
                + "    for (i = 0; i < n; i++) {\n"
                + "        int l, r;\n"
                + "        printf(\"Enter left and right child index of node %d (-1 if none): \", i);\n"
                + "        scanf(\"%d %d\", &l, &r);\n"
                + "        if (l != -1) nodes[i]->left = nodes[l];\n"
                + "        if (r != -1) nodes[i]->right = nodes[r];\n"
                + "    }\n"
                + "    postorder(nodes[0]);\n"
                + "    return 0;\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return header
                + "void postorderRec(struct Node* root) {\n"
                + "    if (root == NULL) return;\n"
                + "    postorderRec(root->left);\n"
                + "    postorderRec(root->right);\n"
                + "    printf(\"%d \", root->data);\n"
                + "}\n\n"
                + "int main() {\n"
                + "    int n, i;\n"
                + "    printf(\"Enter number of nodes: \");\n"
                + "    scanf(\"%d\", &n);\n"
                + "    struct Node* nodes[100];\n"
                + "    for (i = 0; i < n; i++) {\n"
                + "        int val;\n"
                + "        printf(\"Enter value of node %d: \", i);\n"
                + "        scanf(\"%d\", &val);\n"
                + "        nodes[i] = create(val);\n"
                + "    }\n"
                + "    for (i = 0; i < n; i++) {\n"
                + "        int l, r;\n"
                + "        printf(\"Enter left and right child index of node %d (-1 if none): \", i);\n"
                + "        scanf(\"%d %d\", &l, &r);\n"
                + "        if (l != -1) nodes[i]->left = nodes[l];\n"
                + "        if (r != -1) nodes[i]->right = nodes[r];\n"
                + "    }\n"
                + "    postorderRec(nodes[0]);\n"
                + "    return 0;\n"
                + "}";
        }
        return header
            + "int main() {\n"
            + "    int n, i;\n"
            + "    printf(\"Enter number of nodes: \");\n"
            + "    scanf(\"%d\", &n);\n"
            + "    struct Node* nodes[100];\n"
            + "    for (i = 0; i < n; i++) {\n"
            + "        int val;\n"
            + "        printf(\"Enter value of node %d: \", i);\n"
            + "        scanf(\"%d\", &val);\n"
            + "        nodes[i] = create(val);\n"
            + "    }\n"
            + "    for (i = 0; i < n; i++) {\n"
            + "        int l, r;\n"
            + "        printf(\"Enter left and right child index of node %d (-1 if none): \", i);\n"
            + "        scanf(\"%d %d\", &l, &r);\n"
            + "        if (l != -1) nodes[i]->left = nodes[l];\n"
            + "        if (r != -1) nodes[i]->right = nodes[r];\n"
            + "    }\n"
            + "    struct Node* stack[100];\n"
            + "    struct Node* output[100];\n"
            + "    int top1 = -1, top2 = -1;\n"
            + "    stack[++top1] = nodes[0];\n"
            + "    while (top1 != -1) {\n"
            + "        struct Node* temp = stack[top1--];\n"
            + "        output[++top2] = temp;\n"
            + "        if (temp->left != NULL) stack[++top1] = temp->left;\n"
            + "        if (temp->right != NULL) stack[++top1] = temp->right;\n"
            + "    }\n"
            + "    while (top2 != -1) {\n"
            + "        printf(\"%d \", output[top2--]->data);\n"
            + "    }\n"
            + "    return 0;\n"
            + "}";
    }

    private static String getCBstSearchParentTracking(String style) {
        String header = "#include <stdio.h>\n#include <stdlib.h>\n\n"
            + "struct Node {\n"
            + "    int data;\n"
            + "    struct Node *left, *right;\n"
            + "};\n\n"
            + "struct Node* create(int data) {\n"
            + "    struct Node* n = (struct Node*)malloc(sizeof(struct Node));\n"
            + "    n->data = data;\n"
            + "    n->left = n->right = NULL;\n"
            + "    return n;\n"
            + "}\n\n";
        if ("Functions".equals(style)) {
            return header
                + "void find(struct Node* ptr, struct Node* save, int item, struct Node** loc, struct Node** par) {\n"
                + "    if (ptr == NULL) {\n"
                + "        *loc = NULL;\n"
                + "        *par = save;\n"
                + "        return;\n"
                + "    }\n"
                + "    if (item == ptr->data) {\n"
                + "        *loc = ptr;\n"
                + "        *par = save;\n"
                + "        return;\n"
                + "    }\n"
                + "    if (item < ptr->data)\n"
                + "        find(ptr->left, ptr, item, loc, par);\n"
                + "    else\n"
                + "        find(ptr->right, ptr, item, loc, par);\n"
                + "}\n\n"
                + "int main() {\n"
                + "    int n, i, item;\n"
                + "    scanf(\"%d\", &n);\n"
                + "    struct Node* nodes[100];\n"
                + "    for (i = 0; i < n; i++) {\n"
                + "        int val;\n"
                + "        scanf(\"%d\", &val);\n"
                + "        nodes[i] = create(val);\n"
                + "    }\n"
                + "    for (i = 0; i < n; i++) {\n"
                + "        int l, r;\n"
                + "        scanf(\"%d %d\", &l, &r);\n"
                + "        if (l != -1) nodes[i]->left = nodes[l];\n"
                + "        if (r != -1) nodes[i]->right = nodes[r];\n"
                + "    }\n"
                + "    scanf(\"%d\", &item);\n"
                + "    struct Node *loc = NULL, *par = NULL;\n"
                + "    find(nodes[0], NULL, item, &loc, &par);\n"
                + "    if (loc != NULL)\n"
                + "        printf(\"Found. Parent: %d\\n\", par ? par->data : -1);\n"
                + "    else\n"
                + "        printf(\"Not Found. Parent: %d\\n\", par ? par->data : -1);\n"
                + "    return 0;\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return header
                + "void findRec(struct Node* root, struct Node* parent, int item, struct Node** loc, struct Node** par) {\n"
                + "    if (root == NULL) {\n"
                + "        *loc = NULL;\n"
                + "        *par = parent;\n"
                + "        return;\n"
                + "    }\n"
                + "    if (item == root->data) {\n"
                + "        *loc = root;\n"
                + "        *par = parent;\n"
                + "        return;\n"
                + "    }\n"
                + "    if (item < root->data)\n"
                + "        findRec(root->left, root, item, loc, par);\n"
                + "    else\n"
                + "        findRec(root->right, root, item, loc, par);\n"
                + "}\n\n"
                + "int main() {\n"
                + "    int n, i, item;\n"
                + "    scanf(\"%d\", &n);\n"
                + "    struct Node* nodes[100];\n"
                + "    for (i = 0; i < n; i++) {\n"
                + "        int val;\n"
                + "        scanf(\"%d\", &val);\n"
                + "        nodes[i] = create(val);\n"
                + "    }\n"
                + "    for (i = 0; i < n; i++) {\n"
                + "        int l, r;\n"
                + "        scanf(\"%d %d\", &l, &r);\n"
                + "        if (l != -1) nodes[i]->left = nodes[l];\n"
                + "        if (r != -1) nodes[i]->right = nodes[r];\n"
                + "    }\n"
                + "    scanf(\"%d\", &item);\n"
                + "    struct Node *loc = NULL, *par = NULL;\n"
                + "    findRec(nodes[0], NULL, item, &loc, &par);\n"
                + "    if (loc != NULL)\n"
                + "        printf(\"Found. Parent: %d\\n\", par ? par->data : -1);\n"
                + "    else\n"
                + "        printf(\"Not Found. Parent: %d\\n\", par ? par->data : -1);\n"
                + "    return 0;\n"
                + "}";
        }
        return header
            + "int main() {\n"
            + "    int n, i, item;\n"
            + "    scanf(\"%d\", &n);\n"
            + "    struct Node* nodes[100];\n"
            + "    for (i = 0; i < n; i++) {\n"
            + "        int val;\n"
            + "        scanf(\"%d\", &val);\n"
            + "        nodes[i] = create(val);\n"
            + "    }\n"
            + "    for (i = 0; i < n; i++) {\n"
            + "        int l, r;\n"
            + "        scanf(\"%d %d\", &l, &r);\n"
            + "        if (l != -1) nodes[i]->left = nodes[l];\n"
            + "        if (r != -1) nodes[i]->right = nodes[r];\n"
            + "    }\n"
            + "    scanf(\"%d\", &item);\n"
            + "    struct Node *root = nodes[0], *ptr = root, *save = NULL;\n"
            + "    struct Node *loc = NULL, *par = NULL;\n"
            + "    if (root == NULL) {\n"
            + "        loc = NULL;\n"
            + "        par = NULL;\n"
            + "    } else if (item == root->data) {\n"
            + "        loc = root;\n"
            + "        par = NULL;\n"
            + "    } else {\n"
            + "        if (item < root->data) {\n"
            + "            ptr = root->left;\n"
            + "            save = root;\n"
            + "        } else {\n"
            + "            ptr = root->right;\n"
            + "            save = root;\n"
            + "        }\n"
            + "        while (ptr != NULL) {\n"
            + "            if (item == ptr->data) {\n"
            + "                loc = ptr;\n"
            + "                par = save;\n"
            + "                break;\n"
            + "            } else if (item < ptr->data) {\n"
            + "                save = ptr;\n"
            + "                ptr = ptr->left;\n"
            + "            } else {\n"
            + "                save = ptr;\n"
            + "                ptr = ptr->right;\n"
            + "            }\n"
            + "        }\n"
            + "        if (ptr == NULL) {\n"
            + "            loc = NULL;\n"
            + "            par = save;\n"
            + "        }\n"
            + "    }\n"
            + "    if (loc != NULL)\n"
            + "        printf(\"Found. Parent: %d\\n\", par ? par->data : -1);\n"
            + "    else\n"
            + "        printf(\"Not Found. Parent: %d\\n\", par ? par->data : -1);\n"
            + "    return 0;\n"
            + "}";
    }

    private static String getCBstInsertion(String style) {
        String header = "#include <stdio.h>\n#include <stdlib.h>\n\n"
            + "struct Node {\n"
            + "    int data;\n"
            + "    struct Node *left, *right;\n"
            + "};\n\n"
            + "struct Node* create(int data) {\n"
            + "    struct Node* n = (struct Node*)malloc(sizeof(struct Node));\n"
            + "    n->data = data;\n"
            + "    n->left = n->right = NULL;\n"
            + "    return n;\n"
            + "}\n\n";
        if ("Functions".equals(style)) {
            return header
                + "struct Node* insertFunc(struct Node* root, int item) {\n"
                + "    if (root == NULL)\n"
                + "        return create(item);\n"
                + "    if (item < root->data)\n"
                + "        root->left = insertFunc(root->left, item);\n"
                + "    else if (item > root->data)\n"
                + "        root->right = insertFunc(root->right, item);\n"
                + "    return root;\n"
                + "}\n\n"
                + "int main() {\n"
                + "    int n, val, item;\n"
                + "    scanf(\"%d\", &n);\n"
                + "    struct Node* root = NULL;\n"
                + "    for (int i = 0; i < n; i++) {\n"
                + "        scanf(\"%d\", &val);\n"
                + "        root = insertFunc(root, val);\n"
                + "    }\n"
                + "    scanf(\"%d\", &item);\n"
                + "    root = insertFunc(root, item);\n"
                + "    printf(\"Insertion Done\\n\");\n"
                + "    return 0;\n"
                + "}";
        }
        if ("Recursion".equals(style)) {
            return header
                + "struct Node* insertRec(struct Node* root, int item) {\n"
                + "    if (root == NULL)\n"
                + "        return create(item);\n"
                + "    if (item < root->data)\n"
                + "        root->left = insertRec(root->left, item);\n"
                + "    else if (item > root->data)\n"
                + "        root->right = insertRec(root->right, item);\n"
                + "    return root;\n"
                + "}\n\n"
                + "int main() {\n"
                + "    int n, val, item;\n"
                + "    scanf(\"%d\", &n);\n"
                + "    struct Node* root = NULL;\n"
                + "    for (int i = 0; i < n; i++) {\n"
                + "        scanf(\"%d\", &val);\n"
                + "        root = insertRec(root, val);\n"
                + "    }\n"
                + "    scanf(\"%d\", &item);\n"
                + "    root = insertRec(root, item);\n"
                + "    printf(\"Insertion Done\\n\");\n"
                + "    return 0;\n"
                + "}";
        }
        return header
            + "struct Node* insert(struct Node* root, int item) {\n"
            + "    struct Node *parent = NULL, *ptr = root;\n"
            + "    while (ptr != NULL) {\n"
            + "        parent = ptr;\n"
            + "        if (item < ptr->data)\n"
            + "            ptr = ptr->left;\n"
            + "        else if (item > ptr->data)\n"
            + "            ptr = ptr->right;\n"
            + "        else\n"
            + "            return root;\n"
            + "    }\n"
            + "    struct Node* newNode = create(item);\n"
            + "    if (parent == NULL)\n"
            + "        return newNode;\n"
            + "    else if (item < parent->data)\n"
            + "        parent->left = newNode;\n"
            + "    else\n"
            + "        parent->right = newNode;\n"
            + "    return root;\n"
            + "}\n\n"
            + "int main() {\n"
            + "    int n, val, item;\n"
            + "    scanf(\"%d\", &n);\n"
            + "    struct Node* root = NULL;\n"
            + "    for (int i = 0; i < n; i++) {\n"
            + "        scanf(\"%d\", &val);\n"
            + "        root = insert(root, val);\n"
            + "    }\n"
            + "    scanf(\"%d\", &item);\n"
            + "    root = insert(root, item);\n"
            + "    printf(\"Insertion Done\\n\");\n"
            + "    return 0;\n"
            + "}";
    }

    private static String getCBstDeletionCaseA(String style) {
        String base = "#include <stdio.h>\n#include <stdlib.h>\n\n"
            + "struct Node { int data; struct Node *left, *right; };\n"
            + "struct Node* create(int data) { struct Node* n = (struct Node*)malloc(sizeof(struct Node)); n->data = data; n->left = n->right = NULL; return n; }\n"
            + "struct Node* insert(struct Node* root, int val) { if (root == NULL) return create(val); if (val < root->data) root->left = insert(root->left, val); else if (val > root->data) root->right = insert(root->right, val); return root; }\n";
        if ("Functions".equals(style)) {
            return base + "struct Node* deleteFunc(struct Node* root, int item) { if (root == NULL) return NULL; if (item < root->data) root->left = deleteFunc(root->left, item); else if (item > root->data) root->right = deleteFunc(root->right, item); else { if (root->left == NULL) return root->right; else if (root->right == NULL) return root->left; } return root; }\nint main() { int n, val, item; scanf(\"%d\", &n); struct Node* root = NULL; for (int i = 0; i < n; i++) { scanf(\"%d\", &val); root = insert(root, val); } scanf(\"%d\", &item); root = deleteFunc(root, item); printf(\"Deletion Done\\n\"); return 0; }";
        }
        if ("Recursion".equals(style)) {
            return base + "struct Node* deleteRec(struct Node* root, int item) { if (root == NULL) return NULL; if (item < root->data) root->left = deleteRec(root->left, item); else if (item > root->data) root->right = deleteRec(root->right, item); else { if (root->left == NULL && root->right == NULL) return NULL; else if (root->left != NULL) return root->left; else return root->right; } return root; }\nint main() { int n, val, item; scanf(\"%d\", &n); struct Node* root = NULL; for (int i = 0; i < n; i++) { scanf(\"%d\", &val); root = insert(root, val); } scanf(\"%d\", &item); root = deleteRec(root, item); printf(\"Deletion Done\\n\"); return 0; }";
        }
        return base + "struct Node* deleteCaseA(struct Node* root, int item) { struct Node *ptr = root, *par = NULL; while (ptr != NULL && ptr->data != item) { par = ptr; if (item < ptr->data) ptr = ptr->left; else ptr = ptr->right; } if (ptr == NULL) return root; struct Node* child; if (ptr->left == NULL && ptr->right == NULL) child = NULL; else if (ptr->left != NULL) child = ptr->left; else child = ptr->right; if (par != NULL) { if (par->left == ptr) par->left = child; else par->right = child; } else { root = child; } return root; }\nint main() { int n, val, item; scanf(\"%d\", &n); struct Node* root = NULL; for (int i = 0; i < n; i++) { scanf(\"%d\", &val); root = insert(root, val); } scanf(\"%d\", &item); root = deleteCaseA(root, item); printf(\"Deletion Done\\n\"); return 0; }";
    }

    private static String getCBstDeletionCaseB(String style) {
        String base = "#include <stdio.h>\n#include <stdlib.h>\n\n"
            + "struct Node { int data; struct Node *left, *right; };\n"
            + "struct Node* create(int data) { struct Node* n = (struct Node*)malloc(sizeof(struct Node)); n->data = data; n->left = n->right = NULL; return n; }\n"
            + "struct Node* insert(struct Node* root, int val) { if (root == NULL) return create(val); if (val < root->data) root->left = insert(root->left, val); else if (val > root->data) root->right = insert(root->right, val); return root; }\n";
        if ("Functions".equals(style) || "Recursion".equals(style)) {
            String name = "Functions".equals(style) ? "deleteFunc" : "deleteRec";
            return base + "struct Node* " + name + "(struct Node* root, int item) { if (root == NULL) return NULL; if (item < root->data) root->left = " + name + "(root->left, item); else if (item > root->data) root->right = " + name + "(root->right, item); else if (root->left != NULL && root->right != NULL) { struct Node* suc = root->right; while (suc->left != NULL) suc = suc->left; root->data = suc->data; root->right = " + name + "(root->right, suc->data); } return root; }\nint main() { int n, val, item; scanf(\"%d\", &n); struct Node* root = NULL; for (int i = 0; i < n; i++) { scanf(\"%d\", &val); root = insert(root, val); } scanf(\"%d\", &item); root = " + name + "(root, item); printf(\"Deletion Done\\n\"); return 0; }";
        }
        return base + "struct Node* deleteCaseB(struct Node* root, int item) { struct Node *ptr = root; while (ptr != NULL && ptr->data != item) { if (item < ptr->data) ptr = ptr->left; else ptr = ptr->right; } if (ptr == NULL) return root; if (ptr->left != NULL && ptr->right != NULL) { struct Node *sucParent = ptr; struct Node *suc = ptr->right; while (suc->left != NULL) { sucParent = suc; suc = suc->left; } ptr->data = suc->data; if (sucParent->left == suc) sucParent->left = suc->right; else sucParent->right = suc->right; } return root; }\nint main() { int n, val, item; scanf(\"%d\", &n); struct Node* root = NULL; for (int i = 0; i < n; i++) { scanf(\"%d\", &val); root = insert(root, val); } scanf(\"%d\", &item); root = deleteCaseB(root, item); printf(\"Deletion Done\\n\"); return 0; }";
    }

    private static String getCBstDeletionDel(String style) {
        String base = "#include <stdio.h>\n#include <stdlib.h>\n\n"
            + "struct Node { int data; struct Node *left, *right; };\n"
            + "struct Node* create(int data) { struct Node* n = (struct Node*)malloc(sizeof(struct Node)); n->data = data; n->left = n->right = NULL; return n; }\n"
            + "struct Node* insert(struct Node* root, int val) { if (root == NULL) return create(val); if (val < root->data) root->left = insert(root->left, val); else if (val > root->data) root->right = insert(root->right, val); return root; }\n";
        String name = "Iterative".equals(style) ? "deleteNode" : ("Functions".equals(style) ? "deleteFunc" : "deleteRec");
        return base + "struct Node* " + name + "(struct Node* root, int item) { if (root == NULL) { printf(\"ITEM not in tree\\n\"); return NULL; } if (item < root->data) root->left = " + name + "(root->left, item); else if (item > root->data) root->right = " + name + "(root->right, item); else { if (root->left != NULL && root->right != NULL) { struct Node* suc = root->right; while (suc->left != NULL) suc = suc->left; root->data = suc->data; root->right = " + name + "(root->right, suc->data); } else if (root->left != NULL) return root->left; else return root->right; } return root; }\nint main() { int n, val, item; scanf(\"%d\", &n); struct Node* root = NULL; for (int i = 0; i < n; i++) { scanf(\"%d\", &val); root = insert(root, val); } scanf(\"%d\", &item); root = " + name + "(root, item); printf(\"Deletion Done\\n\"); return 0; }";
    }

    private static String getCMaxHeapInsertion(String style) {
        String base = "#include <stdio.h>\n";
        if ("Functions".equals(style)) {
            return base + "int insertHeap(int tree[], int n, int item) { n = n + 1; int ptr = n; while (ptr > 1) { int par = ptr / 2; if (item <= tree[par]) { tree[ptr] = item; return n; } tree[ptr] = tree[par]; ptr = par; } tree[ptr] = item; return n; }\nint main() { int n, tree[100], item; scanf(\"%d\", &n); for (int i = 1; i <= n; i++) scanf(\"%d\", &tree[i]); scanf(\"%d\", &item); n = insertHeap(tree, n, item); for (int i = 1; i <= n; i++) printf(\"%d \", tree[i]); return 0; }";
        }
        if ("Recursion".equals(style)) {
            return base + "void insertRec(int tree[], int ptr, int item) { if (ptr == 1) { tree[ptr] = item; return; } int par = ptr / 2; if (item <= tree[par]) { tree[ptr] = item; return; } tree[ptr] = tree[par]; insertRec(tree, par, item); }\nint main() { int n, tree[100], item; scanf(\"%d\", &n); for (int i = 1; i <= n; i++) scanf(\"%d\", &tree[i]); scanf(\"%d\", &item); n = n + 1; insertRec(tree, n, item); for (int i = 1; i <= n; i++) printf(\"%d \", tree[i]); return 0; }";
        }
        return base + "int main() { int n, tree[100], item; scanf(\"%d\", &n); for (int i = 1; i <= n; i++) scanf(\"%d\", &tree[i]); scanf(\"%d\", &item); n = n + 1; int ptr = n; while (ptr > 1) { int par = ptr / 2; if (item <= tree[par]) { tree[ptr] = item; break; } tree[ptr] = tree[par]; ptr = par; } if (ptr == 1) tree[ptr] = item; for (int i = 1; i <= n; i++) printf(\"%d \", tree[i]); return 0; }";
    }

    private static String getCMaxHeapDeletion(String style) {
        String base = "#include <stdio.h>\n";
        if ("Functions".equals(style)) {
            return base + "int deleteHeap(int tree[], int n) { int item = tree[1]; int last = tree[n]; n--; int ptr = 1; while (2 * ptr <= n) { int left = 2 * ptr; int right = left + 1; int child = (right <= n && tree[right] > tree[left]) ? right : left; if (last >= tree[child]) break; tree[ptr] = tree[child]; ptr = child; } tree[ptr] = last; return item; }\nint main() { int n, tree[100]; scanf(\"%d\", &n); for (int i = 1; i <= n; i++) scanf(\"%d\", &tree[i]); int deleted = deleteHeap(tree, n); printf(\"Deleted element: %d\\n\", deleted); return 0; }";
        }
        if ("Recursion".equals(style)) {
            return base + "void deleteRec(int tree[], int ptr, int n, int last) { int left = 2 * ptr; int right = left + 1; if (left > n) { tree[ptr] = last; return; } int child = (right <= n && tree[right] > tree[left]) ? right : left; if (last >= tree[child]) { tree[ptr] = last; return; } tree[ptr] = tree[child]; deleteRec(tree, child, n, last); }\nint main() { int n, tree[100]; scanf(\"%d\", &n); for (int i = 1; i <= n; i++) scanf(\"%d\", &tree[i]); int item = tree[1]; int last = tree[n]; n--; deleteRec(tree, 1, n, last); printf(\"Deleted element: %d\\n\", item); for (int i = 1; i <= n; i++) printf(\"%d \", tree[i]); return 0; }";
        }
        return base + "int main() { int n, tree[100]; scanf(\"%d\", &n); for (int i = 1; i <= n; i++) scanf(\"%d\", &tree[i]); int item = tree[1]; int last = tree[n]; n--; int ptr = 1; while (2 * ptr <= n) { int left = 2 * ptr; int right = left + 1; int child = (right <= n && tree[right] > tree[left]) ? right : left; if (last >= tree[child]) break; tree[ptr] = tree[child]; ptr = child; } tree[ptr] = last; printf(\"Deleted element: %d\\n\", item); for (int i = 1; i <= n; i++) printf(\"%d \", tree[i]); return 0; }";
    }

    private static String getCHeapSort(String style) {
        String helpers = "void insertHeap(int A[], int n, int item) { int ptr = n + 1; while (ptr > 1) { int par = ptr / 2; if (item <= A[par]) { A[ptr] = item; return; } A[ptr] = A[par]; ptr = par; } A[ptr] = item; }\nint deleteHeap(int A[], int n) { int item = A[1]; int last = A[n]; n--; int ptr = 1; while (2 * ptr <= n) { int left = 2 * ptr; int right = left + 1; int child = (right <= n && A[right] > A[left]) ? right : left; if (last >= A[child]) break; A[ptr] = A[child]; ptr = child; } A[ptr] = last; return item; }\n";
        if ("Functions".equals(style)) {
            return "#include <stdio.h>\n" + helpers + "void buildHeap(int A[], int n) { for (int j = 2; j <= n; j++) insertHeap(A, j - 1, A[j]); }\nvoid sortHeap(int A[], int n) { int size = n; while (size > 1) { int item = deleteHeap(A, size); A[size] = item; size--; } }\nint main() { int n, A[200]; scanf(\"%d\", &n); for (int i = 1; i <= n; i++) scanf(\"%d\", &A[i]); buildHeap(A, n); sortHeap(A, n); for (int i = 1; i <= n; i++) printf(\"%d \", A[i]); return 0; }";
        }
        if ("Recursion".equals(style)) {
            return "#include <stdio.h>\n" + helpers + "void deleteMaxRec(int A[], int n) { if (n <= 1) return; int item = deleteHeap(A, n); A[n] = item; deleteMaxRec(A, n - 1); }\nint main() { int n, A[200]; scanf(\"%d\", &n); for (int i = 1; i <= n; i++) scanf(\"%d\", &A[i]); for (int j = 2; j <= n; j++) insertHeap(A, j - 1, A[j]); deleteMaxRec(A, n); for (int i = 1; i <= n; i++) printf(\"%d \", A[i]); return 0; }";
        }
        return "#include <stdio.h>\n" + helpers + "int main() { int n, A[200]; scanf(\"%d\", &n); for (int i = 1; i <= n; i++) scanf(\"%d\", &A[i]); for (int j = 2; j <= n; j++) insertHeap(A, j - 1, A[j]); int size = n; while (size > 1) { int item = deleteHeap(A, size); A[size] = item; size--; } for (int i = 1; i <= n; i++) printf(\"%d \", A[i]); return 0; }";
    }

    private static String getCLinkedStackPush(String style) {
        if ("Recursion".equals(style)) {
            return "/* Recursion is not applicable for linked stack PUSH in this book style. */";
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "#include <stdlib.h>",
                "",
                "struct node {",
                "    int data;",
                "    struct node* next;",
                "};",
                "",
                "void push(struct node** top, int item) {",
                "    struct node* newNode = (struct node*)malloc(sizeof(struct node));",
                "    if(newNode == NULL) {",
                "        printf(\"Overflow\\n\");",
                "        return;",
                "    }",
                "    newNode->data = item;",
                "    newNode->next = *top;",
                "    *top = newNode;",
                "}",
                "",
                "void display(struct node* top) {",
                "    while(top != NULL) {",
                "        printf(\"%d \", top->data);",
                "        top = top->next;",
                "    }",
                "    printf(\"\\n\");",
                "}",
                "",
                "int main() {",
                "    int n, i, item;",
                "    struct node* top = NULL;",
                "    printf(\"Enter number of items: \");",
                "    scanf(\"%d\", &n);",
                "    for(i = 0; i < n; i++) {",
                "        printf(\"Enter item: \");",
                "        scanf(\"%d\", &item);",
                "        push(&top, item);",
                "    }",
                "    printf(\"Stack after PUSH operations:\\n\");",
                "    display(top);",
                "    return 0;",
                "}");
        }
        return String.join("\n",
            "#include <stdio.h>",
            "#include <stdlib.h>",
            "",
            "struct node {",
            "    int data;",
            "    struct node* next;",
            "};",
            "",
            "int main() {",
            "    int n, i, item;",
            "    struct node* top = NULL;",
            "    struct node* newNode;",
            "    struct node* ptr;",
            "",
            "    printf(\"Enter number of items: \");",
            "    scanf(\"%d\", &n);",
            "",
            "    for(i = 0; i < n; i++) {",
            "        newNode = (struct node*)malloc(sizeof(struct node));",
            "        if(newNode == NULL) {",
            "            printf(\"Overflow\\n\");",
            "            return 0;",
            "        }",
            "        printf(\"Enter item: \");",
            "        scanf(\"%d\", &item);",
            "        newNode->data = item;",
            "        newNode->next = top;",
            "        top = newNode;",
            "    }",
            "",
            "    printf(\"Stack after PUSH operations:\\n\");",
            "    ptr = top;",
            "    while(ptr != NULL) {",
            "        printf(\"%d \", ptr->data);",
            "        ptr = ptr->next;",
            "    }",
            "    printf(\"\\n\");",
            "    return 0;",
            "}");
    }

    private static String getCLinkedStackPop(String style) {
        if ("Recursion".equals(style)) {
            return "/* Recursion is not applicable for linked stack POP in this book style. */";
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "#include <stdlib.h>",
                "",
                "struct node {",
                "    int data;",
                "    struct node* next;",
                "};",
                "",
                "void push(struct node** top, int item) {",
                "    struct node* newNode = (struct node*)malloc(sizeof(struct node));",
                "    newNode->data = item;",
                "    newNode->next = *top;",
                "    *top = newNode;",
                "}",
                "",
                "int pop(struct node** top) {",
                "    if(*top == NULL) {",
                "        printf(\"Underflow\\n\");",
                "        return -1;",
                "    }",
                "    struct node* temp = *top;",
                "    int item = temp->data;",
                "    *top = temp->next;",
                "    free(temp);",
                "    return item;",
                "}",
                "",
                "void display(struct node* top) {",
                "    while(top != NULL) {",
                "        printf(\"%d \", top->data);",
                "        top = top->next;",
                "    }",
                "    printf(\"\\n\");",
                "}",
                "",
                "int main() {",
                "    struct node* top = NULL;",
                "    int deleted;",
                "    push(&top, 10);",
                "    push(&top, 20);",
                "    push(&top, 30);",
                "    deleted = pop(&top);",
                "    if(deleted != -1) {",
                "        printf(\"Deleted item: %d\\n\", deleted);",
                "    }",
                "    printf(\"Stack after POP:\\n\");",
                "    display(top);",
                "    return 0;",
                "}");
        }
        return String.join("\n",
            "#include <stdio.h>",
            "#include <stdlib.h>",
            "",
            "struct node {",
            "    int data;",
            "    struct node* next;",
            "};",
            "",
            "int main() {",
            "    struct node *top = NULL, *newNode, *ptr;",
            "    int item;",
            "",
            "    newNode = (struct node*)malloc(sizeof(struct node));",
            "    newNode->data = 10;",
            "    newNode->next = top;",
            "    top = newNode;",
            "",
            "    newNode = (struct node*)malloc(sizeof(struct node));",
            "    newNode->data = 20;",
            "    newNode->next = top;",
            "    top = newNode;",
            "",
            "    newNode = (struct node*)malloc(sizeof(struct node));",
            "    newNode->data = 30;",
            "    newNode->next = top;",
            "    top = newNode;",
            "",
            "    if(top == NULL) {",
            "        printf(\"Underflow\\n\");",
            "    } else {",
            "        ptr = top;",
            "        item = ptr->data;",
            "        top = ptr->next;",
            "        free(ptr);",
            "        printf(\"Deleted item: %d\\n\", item);",
            "    }",
            "",
            "    printf(\"Stack after POP:\\n\");",
            "    ptr = top;",
            "    while(ptr != NULL) {",
            "        printf(\"%d \", ptr->data);",
            "        ptr = ptr->next;",
            "    }",
            "    printf(\"\\n\");",
            "    return 0;",
            "}");
    }

    private static String getCQuicksort(String style) {
        if ("Recursion".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "",
                "int partition(int arr[], int low, int high) {",
                "    int pivot = arr[low];",
                "    int left = low + 1;",
                "    int right = high;",
                "",
                "    while(left <= right) {",
                "        while(left <= high && arr[left] <= pivot) {",
                "            left++;",
                "        }",
                "        while(arr[right] > pivot) {",
                "            right--;",
                "        }",
                "        if(left < right) {",
                "            int temp = arr[left];",
                "            arr[left] = arr[right];",
                "            arr[right] = temp;",
                "        }",
                "    }",
                "",
                "    arr[low] = arr[right];",
                "    arr[right] = pivot;",
                "    return right;",
                "}",
                "",
                "void quicksort(int arr[], int low, int high) {",
                "    if(low < high) {",
                "        int loc = partition(arr, low, high);",
                "        quicksort(arr, low, loc - 1);",
                "        quicksort(arr, loc + 1, high);",
                "    }",
                "}",
                "",
                "int main() {",
                "    int n, i;",
                "    printf(\"Enter number of elements: \");",
                "    scanf(\"%d\", &n);",
                "    int arr[n];",
                "    printf(\"Enter elements:\\n\");",
                "    for(i = 0; i < n; i++) {",
                "        scanf(\"%d\", &arr[i]);",
                "    }",
                "    quicksort(arr, 0, n - 1);",
                "    printf(\"Sorted array:\\n\");",
                "    for(i = 0; i < n; i++) {",
                "        printf(\"%d \", arr[i]);",
                "    }",
                "    return 0;",
                "}");
        }
        if ("Functions".equals(style)) {
            return String.join("\n",
                "#include <stdio.h>",
                "",
                "int partition(int arr[], int low, int high) {",
                "    int pivot = arr[low];",
                "    int left = low + 1;",
                "    int right = high;",
                "    while(left <= right) {",
                "        while(left <= high && arr[left] <= pivot) {",
                "            left++;",
                "        }",
                "        while(arr[right] > pivot) {",
                "            right--;",
                "        }",
                "        if(left < right) {",
                "            int temp = arr[left];",
                "            arr[left] = arr[right];",
                "            arr[right] = temp;",
                "        }",
                "    }",
                "    arr[low] = arr[right];",
                "    arr[right] = pivot;",
                "    return right;",
                "}",
                "",
                "void quicksort(int arr[], int low, int high) {",
                "    int loc;",
                "    if(low < high) {",
                "        loc = partition(arr, low, high);",
                "        quicksort(arr, low, loc - 1);",
                "        quicksort(arr, loc + 1, high);",
                "    }",
                "}",
                "",
                "int main() {",
                "    int n, i;",
                "    printf(\"Enter number of elements: \");",
                "    scanf(\"%d\", &n);",
                "    int arr[n];",
                "    printf(\"Enter elements:\\n\");",
                "    for(i = 0; i < n; i++) {",
                "        scanf(\"%d\", &arr[i]);",
                "    }",
                "    quicksort(arr, 0, n - 1);",
                "    printf(\"Sorted array:\\n\");",
                "    for(i = 0; i < n; i++) {",
                "        printf(\"%d \", arr[i]);",
                "    }",
                "    return 0;",
                "}");
        }
        return String.join("\n",
            "#include <stdio.h>",
            "",
            "int partition(int arr[], int low, int high) {",
            "    int pivot = arr[low];",
            "    int left = low + 1;",
            "    int right = high;",
            "",
            "    while(left <= right) {",
            "        while(left <= high && arr[left] <= pivot) {",
            "            left++;",
            "        }",
            "        while(arr[right] > pivot) {",
            "            right--;",
            "        }",
            "        if(left < right) {",
            "            int temp = arr[left];",
            "            arr[left] = arr[right];",
            "            arr[right] = temp;",
            "        }",
            "    }",
            "",
            "    arr[low] = arr[right];",
            "    arr[right] = pivot;",
            "    return right;",
            "}",
            "",
            "int main() {",
            "    int n, i;",
            "    printf(\"Enter number of elements: \");",
            "    scanf(\"%d\", &n);",
            "    int arr[n];",
            "    int lower[n];",
            "    int upper[n];",
            "    int top = -1;",
            "",
            "    printf(\"Enter elements:\\n\");",
            "    for(i = 0; i < n; i++) {",
            "        scanf(\"%d\", &arr[i]);",
            "    }",
            "",
            "    if(n > 1) {",
            "        top = top + 1;",
            "        lower[top] = 0;",
            "        upper[top] = n - 1;",
            "    }",
            "",
            "    while(top != -1) {",
            "        int beg = lower[top];",
            "        int end = upper[top];",
            "        int loc;",
            "        top = top - 1;",
            "        loc = partition(arr, beg, end);",
            "",
            "        if(beg < loc - 1) {",
            "            top = top + 1;",
            "            lower[top] = beg;",
            "            upper[top] = loc - 1;",
            "        }",
            "        if(loc + 1 < end) {",
            "            top = top + 1;",
            "            lower[top] = loc + 1;",
            "            upper[top] = end;",
            "        }",
            "    }",
            "",
            "    printf(\"Sorted array:\\n\");",
            "    for(i = 0; i < n; i++) {",
            "        printf(\"%d \", arr[i]);",
            "    }",
            "    return 0;",
            "}");
    }

    private static String getCWarshallAlgorithm(String style) { return String.join("\n", "#include <stdio.h>", "int main() {", "    int m, A[101][101], P[101][101];", "    scanf(\"%d\", &m);", "    for (int i = 1; i <= m; i++) for (int j = 1; j <= m; j++) scanf(\"%d\", &A[i][j]);", "    for (int i = 1; i <= m; i++) for (int j = 1; j <= m; j++) P[i][j] = (A[i][j] == 0) ? 0 : 1;", "    for (int k = 1; k <= m; k++) for (int i = 1; i <= m; i++) for (int j = 1; j <= m; j++) P[i][j] = P[i][j] || (P[i][k] && P[k][j]);", "    for (int i = 1; i <= m; i++) { for (int j = 1; j <= m; j++) printf(\"%d \", P[i][j]); printf(\"\\n\"); }", "    return 0;", "}"); }

    private static String getCFloydAlgorithm(String style) { return String.join("\n", "#include <stdio.h>", "#define INF 999999", "int main() {", "    int m, W[101][101], Q[101][101];", "    scanf(\"%d\", &m);", "    for (int i = 1; i <= m; i++) for (int j = 1; j <= m; j++) scanf(\"%d\", &W[i][j]);", "    for (int i = 1; i <= m; i++) for (int j = 1; j <= m; j++) Q[i][j] = (W[i][j] == 0) ? INF : W[i][j];", "    for (int k = 1; k <= m; k++) for (int i = 1; i <= m; i++) for (int j = 1; j <= m; j++) Q[i][j] = (Q[i][j] < Q[i][k] + Q[k][j]) ? Q[i][j] : (Q[i][k] + Q[k][j]);", "    for (int i = 1; i <= m; i++) { for (int j = 1; j <= m; j++) printf(\"%d \", Q[i][j]); printf(\"\\n\"); }", "    return 0;", "}"); }

    private static String getCFindEdgeGraph(String style) { return String.join("\n", "#include <stdio.h>", "int main() {", "    int n, a, b, graph[100][100];", "    scanf(\"%d\", &n);", "    for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) scanf(\"%d\", &graph[i][j]);", "    scanf(\"%d %d\", &a, &b);", "    if (a >= 0 && a < n && b >= 0 && b < n && graph[a][b] == 1) printf(\"Edge Found\\n\"); else printf(\"Edge Not Found\\n\");", "    return 0;", "}"); }

    private static String getCInsertNodeGraph(String style) { return String.join("\n", "#include <stdio.h>", "int main() {", "    int n, item, node[100];", "    scanf(\"%d\", &n);", "    for (int i = 0; i < n; i++) scanf(\"%d\", &node[i]);", "    scanf(\"%d\", &item);", "    for (int i = 0; i < n; i++) if (node[i] == item) { printf(\"Node Exists\\n\"); return 0; }", "    node[n++] = item; printf(\"Node Inserted\\n\"); for (int i = 0; i < n; i++) printf(\"%d \", node[i]);", "    return 0;", "}"); }

    private static String getCInsertEdgeGraph(String style) { return String.join("\n", "#include <stdio.h>", "int main() {", "    int n, a, b, graph[100][100];", "    scanf(\"%d\", &n);", "    for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) scanf(\"%d\", &graph[i][j]);", "    scanf(\"%d %d\", &a, &b);", "    if (a < 0 || a >= n || b < 0 || b >= n) printf(\"Invalid Edge\\n\"); else { graph[a][b] = 1; printf(\"Edge Inserted\\n\"); }", "    return 0;", "}"); }

    private static String getCDeleteEdgeGraph(String style) { return String.join("\n", "#include <stdio.h>", "int main() {", "    int n, a, b, graph[100][100];", "    scanf(\"%d\", &n);", "    for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) scanf(\"%d\", &graph[i][j]);", "    scanf(\"%d %d\", &a, &b);", "    if (a < 0 || a >= n || b < 0 || b >= n || graph[a][b] == 0) printf(\"Edge Not Found\\n\"); else { graph[a][b] = 0; printf(\"Edge Deleted\\n\"); }", "    return 0;", "}"); }

    private static String getCDeleteVertexGraph(String style) { return String.join("\n", "#include <stdio.h>", "int main() {", "    int n, target, graph[100][100];", "    scanf(\"%d\", &n);", "    for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) scanf(\"%d\", &graph[i][j]);", "    scanf(\"%d\", &target);", "    if (target < 0 || target >= n) printf(\"Node Not Found\\n\"); else { for (int i = 0; i < n; i++) { graph[target][i] = 0; graph[i][target] = 0; } printf(\"Node Deleted\\n\"); }", "    return 0;", "}"); }

    private static String getCBreadthFirstSearch(String style) { return String.join("\n", "#include <stdio.h>", "int queue[100], front = 0, rear = -1;", "void enqueue(int x) { queue[++rear] = x; }", "int dequeue() { return queue[front++]; }", "int isEmpty() { return front > rear; }", "int main() {", "    int n, start, graph[100][100], status[100];", "    scanf(\"%d\", &n);", "    for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) scanf(\"%d\", &graph[i][j]);", "    scanf(\"%d\", &start);", "    for (int i = 0; i < n; i++) status[i] = 1;", "    enqueue(start); status[start] = 2;", "    while (!isEmpty()) { int node = dequeue(); printf(\"%d \", node); status[node] = 3; for (int i = 0; i < n; i++) if (graph[node][i] == 1 && status[i] == 1) { enqueue(i); status[i] = 2; } }", "    return 0;", "}"); }

    private static String getCDepthFirstSearch(String style) { return String.join("\n", "#include <stdio.h>", "int stackArr[100], top = -1;", "void push(int x) { stackArr[++top] = x; }", "int pop() { return stackArr[top--]; }", "int isEmpty() { return top == -1; }", "int main() {", "    int n, start, graph[100][100], status[100];", "    scanf(\"%d\", &n);", "    for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) scanf(\"%d\", &graph[i][j]);", "    scanf(\"%d\", &start);", "    for (int i = 0; i < n; i++) status[i] = 1;", "    push(start); status[start] = 2;", "    while (!isEmpty()) { int node = pop(); printf(\"%d \", node); status[node] = 3; for (int i = 0; i < n; i++) if (graph[node][i] == 1 && status[i] == 1) { push(i); status[i] = 2; } }", "    return 0;", "}"); }

    private static String getCTopologicalSort(String style) { return String.join("\n", "#include <stdio.h>", "int queue[100], front = 0, rear = -1;", "void enqueue(int x) { queue[++rear] = x; }", "int dequeue() { return queue[front++]; }", "int isEmpty() { return front > rear; }", "int main() {", "    int n, graph[100][100], indeg[100] = {0};", "    scanf(\"%d\", &n);", "    for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) { scanf(\"%d\", &graph[i][j]); if (graph[i][j] == 1) indeg[j]++; }", "    for (int i = 0; i < n; i++) if (indeg[i] == 0) enqueue(i);", "    while (!isEmpty()) { int node = dequeue(); printf(\"%d \", node); for (int j = 0; j < n; j++) if (graph[node][j] == 1) { indeg[j]--; if (indeg[j] == 0) enqueue(j); } }", "    return 0;", "}"); }

    private static String getCInsertionSortPdf(String style) { return String.join("\n", "#include <stdio.h>", "int main() {", "    int n; scanf(\"%d\", &n); int A[100]; A[0] = -99999; for (int i = 1; i <= n; i++) scanf(\"%d\", &A[i]);", "    for (int k = 2; k <= n; k++) { int temp = A[k]; int ptr = k - 1; while (temp < A[ptr]) { A[ptr + 1] = A[ptr]; ptr--; } A[ptr + 1] = temp; }", "    for (int i = 1; i <= n; i++) printf(\"%d \", A[i]);", "    return 0;", "}"); }

    private static String getCSelectionSortPdf(String style) { return String.join("\n", "#include <stdio.h>", "int main() {", "    int n; scanf(\"%d\", &n); int A[100]; for (int i = 1; i <= n; i++) scanf(\"%d\", &A[i]);", "    for (int k = 1; k <= n - 1; k++) { int min = A[k], loc = k; for (int j = k + 1; j <= n; j++) if (min > A[j]) { min = A[j]; loc = j; } int temp = A[k]; A[k] = A[loc]; A[loc] = temp; }", "    for (int i = 1; i <= n; i++) printf(\"%d \", A[i]);", "    return 0;", "}"); }

    private static String getCMergingPdf(String style) { return String.join("\n", "#include <stdio.h>", "int main() {", "    int R, S; scanf(\"%d\", &R); int A[100]; for (int i = 1; i <= R; i++) scanf(\"%d\", &A[i]); scanf(\"%d\", &S); int B[100]; for (int i = 1; i <= S; i++) scanf(\"%d\", &B[i]); int C[200]; int NA = 1, NB = 1, PTR = 1; while (NA <= R && NB <= S) { if (A[NA] < B[NB]) C[PTR++] = A[NA++]; else C[PTR++] = B[NB++]; } if (NA > R) for (int k = 0; k <= S - NB; k++) C[PTR + k] = B[NB + k]; else for (int k = 0; k <= R - NA; k++) C[PTR + k] = A[NA + k]; for (int i = 1; i <= R + S; i++) printf(\"%d \", C[i]); return 0; }"); }

    private static String getCMergeSortPdf(String style) { return String.join("\n", "#include <stdio.h>", "void merge(int A[], int l1, int s1, int B[], int l2, int s2, int C[], int sc) { int i = s1, j = s2, k = sc; while (i < s1 + l1 && j < s2 + l2) { if (A[i] < B[j]) C[k++] = A[i++]; else C[k++] = B[j++]; } while (i < s1 + l1) C[k++] = A[i++]; while (j < s2 + l2) C[k++] = B[j++]; }", "void mergePass(int A[], int n, int L, int B[]) { int q = n / (2 * L), s = 2 * L * q; for (int i = 1; i <= s; i += 2 * L) merge(A, L, i, A, L, i + L, B, i); if (s + L < n) merge(A, L, s + 1, A, n - s - L, s + L + 1, B, s + 1); else for (int i = s + 1; i <= n; i++) B[i] = A[i]; }", "int main() { int n; scanf(\"%d\", &n); int A[100], B[100]; for (int i = 1; i <= n; i++) scanf(\"%d\", &A[i]); int L = 1; while (L < n) { mergePass(A, n, L, B); mergePass(B, n, 2 * L, A); L = 4 * L; } for (int i = 1; i <= n; i++) printf(\"%d \", A[i]); return 0; }"); }

    private static String getCRadixSortPdf(String style) { return String.join("\n", "#include <stdio.h>", "int main() {", "    int n, d; scanf(\"%d\", &n); int A[100]; for (int i = 0; i < n; i++) scanf(\"%d\", &A[i]); scanf(\"%d\", &d); int divisor = 1;", "    for (int k = 1; k <= d; k++) { int Q[10][100], front[10], rear[10]; for (int i = 0; i < 10; i++) front[i] = rear[i] = -1; for (int i = 0; i < n; i++) { int digit = (A[i] / divisor) % 10; if (front[digit] == -1) front[digit] = 0; Q[digit][++rear[digit]] = A[i]; } int index = 0; for (int i = 0; i < 10; i++) if (front[i] != -1) for (int j = front[i]; j <= rear[i]; j++) A[index++] = Q[i][j]; divisor *= 10; }", "    for (int i = 0; i < n; i++) printf(\"%d \", A[i]);", "    return 0;", "}"); }

}
