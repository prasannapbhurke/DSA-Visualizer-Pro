package com.dsavisualizer.app.ui.content;

import java.util.Map;

/**
 * Formats raw algorithm/code text for learning-oriented presentation.
 * This keeps comment generation and cleanup outside the Fragment.
 */
public class TextContentFormatter {

    private static final int ALGORITHM_WRAP_LIMIT = 44;

    public String formatAlgorithm(String rawText) {
        if (rawText == null || rawText.trim().isEmpty()) {
            return "";
        }
        String[] lines = rawText.replace("\r\n", "\n").split("\n", -1);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            if (i > 0) {
                result.append('\n');
            }
            result.append(formatAlgorithmLine(lines[i]));
        }
        return result.toString();
    }

    public String formatCode(String techniqueId, String rawText, String language, boolean showComments) {
        if (!showComments) {
            return removeComments(rawText, language);
        }
        return addDetailedComments(techniqueId, rawText, language);
    }

    private String removeComments(String text, String language) {
        if ("Python".equalsIgnoreCase(language)) {
            return stripPythonComments(text);
        }
        return stripCStyleComments(text);
    }

    private String addDetailedComments(String techniqueId, String text, String language) {
        if (techniqueId == null) {
            return addAutoComments(text, language);
        }
        if (hasTechniqueTemplate(techniqueId)) {
            if ("Python".equalsIgnoreCase(language)) {
                return addPythonAlgorithmComments(techniqueId, text);
            }
            return addCJavaAlgorithmComments(techniqueId, text);
        }
        String base = addAutoComments(text, language);
        if ("Python".equalsIgnoreCase(language)) {
            return addPythonAlgorithmComments(techniqueId, base);
        }
        return addCJavaAlgorithmComments(techniqueId, base);
    }

    private boolean hasTechniqueTemplate(String techniqueId) {
        switch (techniqueId) {
            case "stack_basics":
            case "linked_stack_push":
            case "linked_stack_pop":
            case "queue_basics":
            case "circular_queue":
            case "linked_queue":
            case "priority_queue_array":
            case "priority_queue_2d":
            case "deque":
            case "singly_linked_list":
            case "traversing_linked_list":
            case "print_linked_list":
            case "count_nodes_linked_list":
            case "search_in_linked_list":
            case "find_linked_list":
            case "insert_at_beginning_linked_list":
            case "insert_after_given_node_linked_list":
            case "insert_into_sorted_linked_list":
            case "delete_node_given_location_linked_list":
            case "delete_node_by_item_linked_list":
            case "delete_linked_list":
            case "traverse_circular_header_list":
            case "search_circular_header_list":
            case "delete_circular_header_list":
            case "tree_traversal":
            case "preorder_traversal":
            case "postorder_traversal":
            case "bst_search_parent_tracking":
            case "bst_insertion":
            case "bst_deletion_case_a":
            case "bst_deletion_case_b":
            case "bst_deletion_del":
            case "max_heap_insertion":
            case "max_heap_deletion":
            case "traversal_using_stacks":
            case "huffman_algorithm":
            case "avl_tree":
            case "trie":
            case "graph_traversal":
            case "warshall_algorithm":
            case "floyd_algorithm":
            case "shortest_paths":
            case "dijkstra_algorithm":
            case "find_edge_graph":
            case "insert_node_graph":
            case "insert_edge_graph":
            case "delete_edge_graph":
            case "delete_vertex_graph":
            case "breadth_first_search":
            case "depth_first_search":
            case "topological_sort":
            case "spanning_tree":
                return true;
            default:
                return false;
        }
    }

    private String addAutoComments(String text, String language) {
        if ("Python".equalsIgnoreCase(language)) {
            return addPythonComments(text);
        }
        return addCJavaComments(text);
    }

    private String addCJavaAlgorithmComments(String techniqueId, String text) {
        String[] lines = text.split("\n");
        StringBuilder result = new StringBuilder();
        boolean addedHeader = false;
        for (String line : lines) {
            String trimmed = line.trim();
            String indent = leadingIndent(line);
            if (!addedHeader && !trimmed.isEmpty()) {
                result.append(indent).append("/* ")
                    .append(resolveTechniqueHeader(techniqueId))
                    .append(" */\n");
                addedHeader = true;
            }
            if ("bubble_sort".equals(techniqueId)) {
                if (trimmed.startsWith("for") && trimmed.contains("k")) {
                    result.append(indent).append("// Step: Outer pass controls how many times we sweep through the array\n");
                }
                if (trimmed.startsWith("while") && trimmed.contains("ptr")) {
                    result.append(indent).append("// Step: Compare adjacent elements inside the current pass\n");
                }
                if (trimmed.contains("if") && trimmed.contains("arr[") && trimmed.contains("ptr + 1")) {
                    result.append(indent).append("// Step: Swap if elements are in the wrong order (descending)\n");
                }
            } else if ("selection_sort".equals(techniqueId)) {
                if (trimmed.startsWith("for") && trimmed.contains("k")) {
                    result.append(indent).append("// Step: Select the next position to place the smallest remaining value\n");
                }
                if (trimmed.contains("min") && trimmed.contains("loc")) {
                    result.append(indent).append("// Step: Track the smallest value and its location\n");
                }
                if (trimmed.startsWith("for") && trimmed.contains("j")) {
                    result.append(indent).append("// Step: Scan the unsorted part to find the minimum\n");
                }
                if (trimmed.contains("temp") && trimmed.contains("A[")) {
                    result.append(indent).append("// Step: Swap the found minimum into its correct position\n");
                }
            } else if ("insertion_sort".equals(techniqueId)) {
                if (trimmed.startsWith("for") && trimmed.contains("k")) {
                    result.append(indent).append("// Step: Pick the next element to insert into the sorted section\n");
                }
                if (trimmed.contains("temp") && trimmed.contains("A[")) {
                    result.append(indent).append("// Step: Store the current element to insert\n");
                }
                if (trimmed.startsWith("while") && trimmed.contains("ptr")) {
                    result.append(indent).append("// Step: Shift larger elements to the right to make space\n");
                }
            } else if ("binary_search".equals(techniqueId)) {
                if (trimmed.contains("low") && trimmed.contains("high")) {
                    result.append(indent).append("// Step: Maintain the current search range\n");
                }
                if (trimmed.contains("mid") && trimmed.contains("=")) {
                    result.append(indent).append("// Step: Check the middle element of the range\n");
                }
                if (trimmed.contains("if") && trimmed.contains("==")) {
                    result.append(indent).append("// Step: Match found, return the position\n");
                }
            } else if ("linear_search".equals(techniqueId)) {
                if (trimmed.startsWith("for") || trimmed.startsWith("while")) {
                    result.append(indent).append("// Step: Scan each element sequentially\n");
                }
                if (trimmed.contains("if") && trimmed.contains("item")) {
                    result.append(indent).append("// Step: Check whether the current element matches the target\n");
                }
            } else if ("jump_search".equals(techniqueId)) {
                if (trimmed.contains("sqrt") || trimmed.contains("block")) {
                    result.append(indent).append("// Step: Compute the jump size for block scanning\n");
                }
                if (trimmed.contains("while") && trimmed.contains("block")) {
                    result.append(indent).append("// Step: Jump block by block until the target range is found\n");
                }
            } else if ("merge_sort".equals(techniqueId) || "merging".equals(techniqueId)) {
                if (trimmed.contains("merge")) {
                    result.append(indent).append("// Step: Merge two sorted sections into one sorted list\n");
                }
            } else if ("radix_sort".equals(techniqueId)) {
                if (trimmed.contains("divisor")) {
                    result.append(indent).append("// Step: Process digits from least significant to most significant\n");
                }
                if (trimmed.contains("bucket") || trimmed.contains("Q[")) {
                    result.append(indent).append("// Step: Distribute numbers into buckets based on the current digit\n");
                }
            } else if ("heapsort".equals(techniqueId)) {
                if (trimmed.contains("heap") && trimmed.contains("swap")) {
                    result.append(indent).append("// Step: Move the maximum to the end and restore heap order\n");
                }
            } else if (isStackTechnique(techniqueId)) {
                appendStackComments(result, indent, trimmed);
            } else if (isQueueTechnique(techniqueId)) {
                appendQueueComments(result, indent, trimmed, techniqueId);
            } else if (isLinkedListTechnique(techniqueId)) {
                appendLinkedListComments(result, indent, trimmed, techniqueId);
            } else if (isTreeTechnique(techniqueId)) {
                appendTreeComments(result, indent, trimmed, techniqueId);
            } else if (isGraphTechnique(techniqueId)) {
                appendGraphComments(result, indent, trimmed, techniqueId);
            }
            result.append(line).append('\n');
        }
        if (result.length() > 0) {
            result.setLength(result.length() - 1);
        }
        return result.toString();
    }

    private String addPythonAlgorithmComments(String techniqueId, String text) {
        String[] lines = text.split("\n");
        StringBuilder result = new StringBuilder();
        boolean addedHeader = false;
        for (String line : lines) {
            String trimmed = line.trim();
            String indent = leadingIndent(line);
            if (!addedHeader && !trimmed.isEmpty()) {
                result.append(indent).append("# ").append(resolveTechniqueHeader(techniqueId)).append('\n');
                addedHeader = true;
            }
            if ("bubble_sort".equals(techniqueId)) {
                if (trimmed.startsWith("for ") && trimmed.contains("k")) {
                    result.append(indent).append("# Step: Outer pass controls how many sweeps over the array\n");
                }
                if (trimmed.startsWith("while") && trimmed.contains("ptr")) {
                    result.append(indent).append("# Step: Compare adjacent elements inside the current pass\n");
                }
            } else if ("selection_sort".equals(techniqueId)) {
                if (trimmed.startsWith("for ") && trimmed.contains("k")) {
                    result.append(indent).append("# Step: Select the next position to place the minimum\n");
                }
                if (trimmed.contains("min_index")) {
                    result.append(indent).append("# Step: Track the smallest value and its location\n");
                }
            } else if ("insertion_sort".equals(techniqueId)) {
                if (trimmed.startsWith("for ") && trimmed.contains("k")) {
                    result.append(indent).append("# Step: Pick the next element to insert into the sorted section\n");
                }
                if (trimmed.startsWith("while ") && trimmed.contains("ptr")) {
                    result.append(indent).append("# Step: Shift larger elements to the right\n");
                }
            } else if ("binary_search".equals(techniqueId)) {
                if (trimmed.contains("low") && trimmed.contains("high")) {
                    result.append(indent).append("# Step: Maintain the current search range\n");
                }
            } else if (isStackTechnique(techniqueId)) {
                if (trimmed.contains("append(")) {
                    result.append(indent).append("# Step: Push the new value at the logical top of the stack\n");
                } else if (trimmed.contains("pop(")) {
                    result.append(indent).append("# Step: Remove the topmost value and shrink the stack by one position\n");
                } else if (trimmed.contains("[-1]")) {
                    result.append(indent).append("# Step: Read the current top element without removing it\n");
                }
            } else if (isQueueTechnique(techniqueId)) {
                if (trimmed.contains("append(")) {
                    result.append(indent).append("# Step: Insert the new item at the rear end of the queue\n");
                } else if (trimmed.contains("pop(0)")) {
                    result.append(indent).append("# Step: Remove the front item because a queue serves oldest items first\n");
                } else if (trimmed.contains("[0]")) {
                    result.append(indent).append("# Step: Inspect the front item without deleting it\n");
                }
            } else if (isLinkedListTechnique(techniqueId)) {
                if (trimmed.startsWith("class Node") || trimmed.startsWith("class node")) {
                    result.append(indent).append("# Step: Define one linked-list node containing data and a next link\n");
                } else if (trimmed.contains("while") && trimmed.contains("ptr")) {
                    result.append(indent).append("# Step: Walk node by node until the end condition is reached\n");
                } else if (trimmed.contains(".next")) {
                    result.append(indent).append("# Step: Update links carefully so the list remains connected\n");
                }
            } else if (isTreeTechnique(techniqueId)) {
                if (trimmed.contains("mid") || trimmed.contains("root")) {
                    result.append(indent).append("# Step: Work from the current tree node and decide the next branch\n");
                } else if (trimmed.contains("left") || trimmed.contains("right")) {
                    result.append(indent).append("# Step: Move to the left or right child according to the tree rule\n");
                }
            } else if (isGraphTechnique(techniqueId)) {
                if (trimmed.contains("queue") || trimmed.contains("stack")) {
                    result.append(indent).append("# Step: Use the helper structure to control the next graph visit\n");
                } else if (trimmed.contains("dist") || trimmed.contains("cost")) {
                    result.append(indent).append("# Step: Update the best known distance when a shorter route is found\n");
                } else if (trimmed.contains("adj") || trimmed.contains("graph")) {
                    result.append(indent).append("# Step: Scan the connected neighbors of the current vertex\n");
                }
            }
            result.append(line).append('\n');
        }
        if (result.length() > 0) {
            result.setLength(result.length() - 1);
        }
        return result.toString();
    }

    private String resolveTechniqueHeader(String techniqueId) {
        if (isStackTechnique(techniqueId)) {
            return "Stack walkthrough: watch how the top position changes after each operation.";
        }
        if (isQueueTechnique(techniqueId)) {
            return "Queue walkthrough: follow how front and rear control insertion and deletion.";
        }
        if (isLinkedListTechnique(techniqueId)) {
            return "Linked-list walkthrough: each step shows how pointers move or links are updated.";
        }
        if (isTreeTechnique(techniqueId)) {
            return "Tree walkthrough: focus on node comparisons, child movement, and structural updates.";
        }
        if (isGraphTechnique(techniqueId)) {
            return "Graph walkthrough: track vertices, edges, and the rule used to choose the next step.";
        }
        return "Program to demonstrate the algorithm";
    }

    private void appendStackComments(StringBuilder result, String indent, String trimmed) {
        if (trimmed.contains("top") && trimmed.contains("-1")) {
            result.append(indent).append("// Step 1: Initialize the top pointer so the stack starts in the empty state\n");
        }
        if (trimmed.contains("OVERFLOW") || trimmed.contains("isFull")) {
            result.append(indent).append("// Step 2: Check whether another push would exceed the allowed stack capacity\n");
        }
        if (trimmed.contains("UNDERFLOW") || trimmed.contains("isEmpty")) {
            result.append(indent).append("// Step 3: Check whether pop or peek is being attempted on an empty stack\n");
        }
        if (trimmed.contains("top++") || trimmed.contains("++top")) {
            result.append(indent).append("// Step 4: Move the top pointer upward to reserve the next free stack position\n");
        }
        if (trimmed.contains("top--") || trimmed.contains("--top")) {
            result.append(indent).append("// Step 5: Move the top pointer downward after removing the current top item\n");
        }
        if (trimmed.contains("stack[top]") || trimmed.contains("arr[top]")) {
            result.append(indent).append("// Step 6: Read from or write to the current top position of the stack\n");
        }
    }

    private void appendQueueComments(StringBuilder result, String indent, String trimmed, String techniqueId) {
        if (trimmed.contains("front = -1") || trimmed.contains("rear = -1")) {
            result.append(indent).append("// Step 1: Initialize front and rear so the queue begins empty\n");
        }
        if (trimmed.contains("OVERFLOW") || trimmed.contains("full")) {
            result.append(indent).append("// Step 2: Confirm there is free space before inserting a new queue item\n");
        }
        if (trimmed.contains("UNDERFLOW") || trimmed.contains("empty")) {
            result.append(indent).append("// Step 3: Confirm deletion is not being attempted on an empty queue\n");
        }
        if (trimmed.contains("rear++") || trimmed.contains("rear = 0")) {
            result.append(indent).append("// Step 4: Advance the rear position so the new item goes to the correct insertion point\n");
        }
        if (trimmed.contains("front++") || trimmed.contains("front = 0")) {
            result.append(indent).append("// Step 5: Advance the front position after one item leaves the queue\n");
        }
        if ("deque".equals(techniqueId) && (trimmed.contains("front_in") || trimmed.contains("rear_in") || trimmed.contains("front_out") || trimmed.contains("rear_out"))) {
            result.append(indent).append("// Step 6: Choose whether the deque operation should happen at the front side or the rear side\n");
        }
    }

    private void appendLinkedListComments(StringBuilder result, String indent, String trimmed, String techniqueId) {
        if (trimmed.startsWith("struct node") || trimmed.contains("class Node")) {
            result.append(indent).append("// Step 1: Define the node structure that stores data and the link to the next node\n");
        }
        if (trimmed.contains("malloc") || trimmed.contains("new Node")) {
            result.append(indent).append("// Step 2: Create a fresh node in memory before attaching it to the linked list\n");
        }
        if (trimmed.contains("head == NULL") || trimmed.contains("head == null")) {
            result.append(indent).append("// Step 3: Handle the special case where the linked list is currently empty\n");
        }
        if (trimmed.contains("ptr = ptr->next") || trimmed.contains("ptr = ptr.next")) {
            result.append(indent).append("// Step 4: Move the traversal pointer to the next node in the chain\n");
        }
        if (trimmed.contains("ptr != NULL") || trimmed.contains("ptr != null")) {
            result.append(indent).append("// Step 5: Continue node-by-node until the list ends or the target is found\n");
        }
        if (trimmed.contains("next =") || trimmed.contains(".next =")) {
            result.append(indent).append("// Step 6: Update the link field carefully so the chain remains correctly connected\n");
        }
        if (techniqueId.contains("delete") && (trimmed.contains("free(") || trimmed.contains("delete "))) {
            result.append(indent).append("// Step 7: Release the removed node only after neighboring links have been repaired\n");
        }
    }

    private void appendTreeComments(StringBuilder result, String indent, String trimmed, String techniqueId) {
        if (trimmed.contains("root") || trimmed.contains("node")) {
            result.append(indent).append("// Step 1: Start from the current tree node and inspect its role in the operation\n");
        }
        if (trimmed.contains("left") || trimmed.contains("right")) {
            result.append(indent).append("// Step 2: Move to the left or right child according to the tree ordering rule\n");
        }
        if (trimmed.contains("stack") && "traversal_using_stacks".equals(techniqueId)) {
            result.append(indent).append("// Step 3: Use an explicit stack to remember nodes that must be revisited later\n");
        }
        if (trimmed.contains("heap") || trimmed.contains("parent") || trimmed.contains("child")) {
            result.append(indent).append("// Step 4: Compare parent and child positions to preserve heap order\n");
        }
        if (trimmed.contains("rotate") || trimmed.contains("rotation")) {
            result.append(indent).append("// Step 5: Rotate the subtree to restore the AVL balance condition\n");
        }
        if (trimmed.contains("frequency") || trimmed.contains("priority") || trimmed.contains("queue")) {
            result.append(indent).append("// Step 6: Choose the smallest available weights before merging them into the Huffman tree\n");
        }
        if (trimmed.contains("character") || trimmed.contains("word") || trimmed.contains("end")) {
            result.append(indent).append("// Step 7: Follow one character at a time and mark the end when the full key is stored\n");
        }
    }

    private void appendGraphComments(StringBuilder result, String indent, String trimmed, String techniqueId) {
        if (trimmed.contains("matrix") || trimmed.contains("adj")) {
            result.append(indent).append("// Step 1: Inspect the matrix or adjacency structure to find the next related vertex\n");
        }
        if (trimmed.contains("queue") && ("breadth_first_search".equals(techniqueId) || "graph_traversal".equals(techniqueId))) {
            result.append(indent).append("// Step 2: Use a queue so vertices are visited level by level in BFS order\n");
        }
        if (trimmed.contains("stack") && "depth_first_search".equals(techniqueId)) {
            result.append(indent).append("// Step 3: Use a stack or recursion so one branch is explored deeply before backtracking\n");
        }
        if (trimmed.contains("dist") || trimmed.contains("cost") || trimmed.contains("relax")) {
            result.append(indent).append("// Step 4: Relax the edge and keep the smaller distance whenever a better path is discovered\n");
        }
        if (trimmed.contains("indegree") || trimmed.contains("topo")) {
            result.append(indent).append("// Step 5: Track dependency counts so the next valid topological node can be selected\n");
        }
        if (trimmed.contains("closure") || trimmed.contains("reach")) {
            result.append(indent).append("// Step 6: Update reachability through an intermediate vertex in the transitive-closure matrix\n");
        }
    }

    private boolean isStackTechnique(String techniqueId) {
        return "stack_basics".equals(techniqueId)
            || "linked_stack_push".equals(techniqueId)
            || "linked_stack_pop".equals(techniqueId);
    }

    private boolean isQueueTechnique(String techniqueId) {
        return "queue_basics".equals(techniqueId)
            || "circular_queue".equals(techniqueId)
            || "linked_queue".equals(techniqueId)
            || "priority_queue_array".equals(techniqueId)
            || "priority_queue_2d".equals(techniqueId)
            || "deque".equals(techniqueId);
    }

    private boolean isLinkedListTechnique(String techniqueId) {
        return techniqueId != null
            && (techniqueId.contains("linked_list")
            || techniqueId.contains("circular_header_list")
            || "find_linked_list".equals(techniqueId)
            || "delete_linked_list".equals(techniqueId));
    }

    private boolean isTreeTechnique(String techniqueId) {
        switch (techniqueId) {
            case "tree_traversal":
            case "preorder_traversal":
            case "postorder_traversal":
            case "bst_search_parent_tracking":
            case "bst_insertion":
            case "bst_deletion_case_a":
            case "bst_deletion_case_b":
            case "bst_deletion_del":
            case "max_heap_insertion":
            case "max_heap_deletion":
            case "traversal_using_stacks":
            case "huffman_algorithm":
            case "avl_tree":
            case "trie":
                return true;
            default:
                return false;
        }
    }

    private boolean isGraphTechnique(String techniqueId) {
        switch (techniqueId) {
            case "graph_traversal":
            case "warshall_algorithm":
            case "floyd_algorithm":
            case "shortest_paths":
            case "dijkstra_algorithm":
            case "find_edge_graph":
            case "insert_node_graph":
            case "insert_edge_graph":
            case "delete_edge_graph":
            case "delete_vertex_graph":
            case "breadth_first_search":
            case "depth_first_search":
            case "topological_sort":
            case "spanning_tree":
                return true;
            default:
                return false;
        }
    }

    private String addCJavaComments(String text) {
        String[] lines = text.split("\n");
        StringBuilder result = new StringBuilder();
        boolean addedHeader = false;
        int stepCounter = 1;
        for (String line : lines) {
            String trimmed = line.trim();
            if (!addedHeader && !trimmed.isEmpty()) {
                result.append("/* Program to demonstrate the algorithm */\n");
                addedHeader = true;
            }
            if (trimmed.startsWith("int main") || trimmed.startsWith("public static void main")) {
                result.append(leadingIndent(line)).append("// Entry point of the program\n");
            }
            if (trimmed.startsWith("int n")
                || trimmed.startsWith("int[]")
                || trimmed.startsWith("int arr")
                || trimmed.startsWith("int A[")) {
                result.append(leadingIndent(line)).append("// Declare variables and arrays used by the algorithm\n");
            }
            if (trimmed.contains("scanf(\"%d\", &n") || trimmed.contains("nextInt()")) {
                result.append(leadingIndent(line))
                    .append("// Step ").append(stepCounter++).append(": Read the size of input\n");
            }
            if (trimmed.contains("printf(\"Enter elements")
                || trimmed.contains("printf(\"Enter element")
                || trimmed.contains("println(\"Enter")) {
                result.append(leadingIndent(line))
                    .append("// Step ").append(stepCounter++).append(": Ask the user to enter the elements\n");
            }
            if (trimmed.startsWith("for ") || trimmed.startsWith("for(")) {
                result.append(leadingIndent(line)).append("// Loop to process each element one by one\n");
            }
            if (trimmed.startsWith("while ") || trimmed.startsWith("while(")) {
                result.append(leadingIndent(line)).append("// Loop continues while the condition is true\n");
            }
            if (trimmed.contains("scanf(")) {
                result.append(leadingIndent(line)).append("// Read one value from user input\n");
            }
            if (trimmed.contains("printf(") || trimmed.contains("System.out")) {
                result.append(leadingIndent(line)).append("// Display output to the user\n");
            }
            if (trimmed.contains("temp") && trimmed.contains("=") && trimmed.contains("[")) {
                result.append(leadingIndent(line)).append("// Use a temporary variable for swapping\n");
            }
            if (trimmed.contains("= arr[") && trimmed.contains("arr[")) {
                result.append(leadingIndent(line)).append("// Swap elements to fix order\n");
            }
            if (trimmed.startsWith("return")) {
                result.append(leadingIndent(line)).append("// Return from the function\n");
            }
            result.append(line).append('\n');
        }
        if (result.length() > 0) {
            result.setLength(result.length() - 1);
        }
        return result.toString();
    }

    private String addPythonComments(String text) {
        String[] lines = text.split("\n");
        StringBuilder result = new StringBuilder();
        boolean addedHeader = false;
        int stepCounter = 1;
        for (String line : lines) {
            String trimmed = line.trim();
            if (!addedHeader && !trimmed.isEmpty()) {
                result.append("# Program to demonstrate the algorithm\n");
                addedHeader = true;
            }
            if (trimmed.startsWith("def ")) {
                result.append(leadingIndent(line)).append("# Function definition for the algorithm\n");
            }
            if (trimmed.contains("n = int(input") || trimmed.startsWith("n = int(input")) {
                result.append(leadingIndent(line))
                    .append("# Step ").append(stepCounter++).append(": Read the size of input\n");
            }
            if (trimmed.contains("input(")) {
                result.append(leadingIndent(line)).append("# Read one value from user input\n");
            }
            if (trimmed.startsWith("for ") || trimmed.startsWith("while ")) {
                result.append(leadingIndent(line)).append("# Loop to process each element\n");
            }
            if (trimmed.startsWith("print(")) {
                result.append(leadingIndent(line)).append("# Display output to the user\n");
            }
            result.append(line).append('\n');
        }
        if (result.length() > 0) {
            result.setLength(result.length() - 1);
        }
        return result.toString();
    }

    private String leadingIndent(String line) {
        int index = 0;
        while (index < line.length() && Character.isWhitespace(line.charAt(index))) {
            index++;
        }
        return line.substring(0, index);
    }

    private String formatAlgorithmLine(String line) {
        if (line == null || line.isEmpty()) {
            return "";
        }
        String indent = leadingIndent(line);
        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            return "";
        }
        if (isHeadingLine(trimmed)) {
            return indent + trimmed;
        }

        String prefix = "";
        String body = trimmed;
        int prefixEnd = extractPrefixEnd(body);
        if (prefixEnd > 0) {
            prefix = body.substring(0, prefixEnd);
            body = body.substring(prefixEnd).trim();
        }

        body = normalizeClauses(body);
        String continuationIndent = indent + repeat(" ", prefix.length() > 0 ? prefix.length() : 4);
        String wrapped = wrapAlgorithmBody(body, indent + prefix, continuationIndent);
        return prefix.isEmpty() ? indent + wrapped : indent + prefix + wrapped;
    }

    private boolean isHeadingLine(String trimmed) {
        return !(trimmed.startsWith("1.")
            || trimmed.startsWith("2.")
            || trimmed.startsWith("3.")
            || trimmed.startsWith("4.")
            || trimmed.startsWith("5.")
            || trimmed.startsWith("6.")
            || trimmed.startsWith("7.")
            || trimmed.startsWith("8.")
            || trimmed.startsWith("9.")
            || trimmed.startsWith("("));
    }

    private int extractPrefixEnd(String body) {
        if (body.length() >= 2 && Character.isDigit(body.charAt(0))) {
            int index = 0;
            while (index < body.length() && Character.isDigit(body.charAt(index))) {
                index++;
            }
            if (index < body.length() && body.charAt(index) == '.') {
                index++;
                while (index < body.length() && body.charAt(index) == ' ') {
                    index++;
                }
                return index;
            }
        }
        if (body.startsWith("(")) {
            int closing = body.indexOf(')');
            if (closing > 0 && closing + 1 < body.length() && body.charAt(closing + 1) == ' ') {
                return closing + 2;
            }
        }
        return 0;
    }

    private String normalizeClauses(String body) {
        String normalized = body;
        normalized = normalized.replace("; else ", ";\nelse ");
        normalized = normalized.replace("; Else ", ";\nElse ");
        normalized = normalized.replace(", else ", ",\nelse ");
        normalized = normalized.replace(", Else ", ",\nElse ");
        if (normalized.startsWith("If ") && normalized.contains(", set ")) {
            normalized = normalized.replaceFirst(", set ", ",\nset ");
        }
        normalized = normalized.replace(" else set ", " else\nset ");
        normalized = normalized.replace(" Else set ", " Else\nset ");
        normalized = normalized.replace(" then: ", "\nthen: ");
        return normalized;
    }

    private String wrapAlgorithmBody(String body, String firstIndent, String continuationIndent) {
        String[] segments = body.split("\n", -1);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < segments.length; i++) {
            if (i > 0) {
                result.append('\n').append(continuationIndent);
            }
            result.append(wrapSingleClause(segments[i].trim(), i == 0 ? firstIndent.length() : continuationIndent.length(), continuationIndent));
        }
        return result.toString();
    }

    private String wrapSingleClause(String clause, int currentIndentLength, String continuationIndent) {
        if (clause.length() + currentIndentLength <= ALGORITHM_WRAP_LIMIT) {
            return clause;
        }
        StringBuilder wrapped = new StringBuilder();
        String remaining = clause;
        boolean first = true;
        while (remaining.length() + (first ? currentIndentLength : continuationIndent.length()) > ALGORITHM_WRAP_LIMIT) {
            int available = ALGORITHM_WRAP_LIMIT - (first ? currentIndentLength : continuationIndent.length());
            int split = findSafeBreak(remaining, available);
            if (split <= 0) {
                break;
            }
            String part = remaining.substring(0, split).trim();
            if (!first) {
                wrapped.append(continuationIndent);
            }
            wrapped.append(part).append('\n');
            remaining = remaining.substring(split).trim();
            first = false;
        }
        if (!remaining.isEmpty()) {
            if (!first) {
                wrapped.append(continuationIndent);
            }
            wrapped.append(remaining);
        } else if (wrapped.length() > 0 && wrapped.charAt(wrapped.length() - 1) == '\n') {
            wrapped.setLength(wrapped.length() - 1);
        }
        return wrapped.toString();
    }

    private int findSafeBreak(String text, int available) {
        int depthParen = 0;
        int depthBracket = 0;
        int best = -1;
        for (int i = 0; i < text.length() && i < available; i++) {
            char c = text.charAt(i);
            if (c == '(') depthParen++;
            else if (c == ')') depthParen = Math.max(0, depthParen - 1);
            else if (c == '[') depthBracket++;
            else if (c == ']') depthBracket = Math.max(0, depthBracket - 1);

            if (depthParen > 0 || depthBracket > 0) {
                continue;
            }

            if (c == ',' || c == ';') {
                best = i + 1;
            }

            if (matchesWordBreak(text, i, " and ")
                || matchesWordBreak(text, i, " AND ")
                || matchesWordBreak(text, i, " or ")
                || matchesWordBreak(text, i, " OR ")
                || matchesWordBreak(text, i, " else ")
                || matchesWordBreak(text, i, " Else ")
                || matchesWordBreak(text, i, " then ")
                || matchesWordBreak(text, i, " then: ")) {
                best = i;
            }
        }
        return best;
    }

    private boolean matchesWordBreak(String text, int index, String token) {
        return index >= 0
            && index + token.length() <= text.length()
            && text.regionMatches(index, token, 0, token.length());
    }

    private String repeat(String value, int count) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append(value);
        }
        return builder.toString();
    }

    private String stripCStyleComments(String text) {
        StringBuilder builder = new StringBuilder();
        boolean inString = false;
        boolean inChar = false;
        boolean inLineComment = false;
        boolean inBlockComment = false;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            char next = i + 1 < text.length() ? text.charAt(i + 1) : '\0';

            if (inLineComment) {
                if (c == '\n') {
                    inLineComment = false;
                    builder.append(c);
                }
                continue;
            }
            if (inBlockComment) {
                if (c == '*' && next == '/') {
                    inBlockComment = false;
                    i++;
                }
                continue;
            }
            if (inString) {
                builder.append(c);
                if (c == '\\' && next != '\0') {
                    builder.append(next);
                    i++;
                    continue;
                }
                if (c == '"') {
                    inString = false;
                }
                continue;
            }
            if (inChar) {
                builder.append(c);
                if (c == '\\' && next != '\0') {
                    builder.append(next);
                    i++;
                    continue;
                }
                if (c == '\'') {
                    inChar = false;
                }
                continue;
            }

            if (c == '/' && next == '/') {
                inLineComment = true;
                i++;
                continue;
            }
            if (c == '/' && next == '*') {
                inBlockComment = true;
                i++;
                continue;
            }
            if (c == '"') {
                inString = true;
                builder.append(c);
                continue;
            }
            if (c == '\'') {
                inChar = true;
                builder.append(c);
                continue;
            }
            builder.append(c);
        }
        return builder.toString();
    }

    private String stripPythonComments(String text) {
        StringBuilder builder = new StringBuilder();
        boolean inSingle = false;
        boolean inDouble = false;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            char next = i + 1 < text.length() ? text.charAt(i + 1) : '\0';

            if (inSingle) {
                builder.append(c);
                if (c == '\\' && next != '\0') {
                    builder.append(next);
                    i++;
                    continue;
                }
                if (c == '\'') {
                    inSingle = false;
                }
                continue;
            }
            if (inDouble) {
                builder.append(c);
                if (c == '\\' && next != '\0') {
                    builder.append(next);
                    i++;
                    continue;
                }
                if (c == '"') {
                    inDouble = false;
                }
                continue;
            }
            if (c == '#') {
                while (i < text.length() && text.charAt(i) != '\n') {
                    i++;
                }
                if (i < text.length()) {
                    builder.append('\n');
                }
                continue;
            }
            if (c == '"') {
                inDouble = true;
                builder.append(c);
                continue;
            }
            if (c == '\'') {
                inSingle = true;
                builder.append(c);
                continue;
            }
            builder.append(c);
        }
        return builder.toString();
    }
}
