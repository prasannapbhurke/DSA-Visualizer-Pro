package com.dsavisualizer.app.ui.visualization.runtime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Deterministic model of a full Huffman prefix tree for visualization.
 * Builds the tree step-by-step to match {@link HuffmanStepEngine}, computes
 * prefix codes, and assigns planar hierarchical coordinates for clean rendering.
 */
public final class HuffmanTreeModel {

    public static final class Node {
        public final int id;
        public final int weight;
        public final String character;
        public final Node left;
        public final Node right;
        public final int stepCreated; // 0 for initial leaf, 1..N-1 for internal node
        public String code = "";
        public int depth = 0;
        public float x = 0f;
        public float y = 0f;

        public Node(int id, int weight, String character, Node left, Node right, int stepCreated) {
            this.id = id;
            this.weight = weight;
            this.character = character == null ? "" : character;
            this.left = left;
            this.right = right;
            this.stepCreated = stepCreated;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }
    }

    public static final class CodeEntry {
        public final String character;
        public final int weight;
        public final String code;

        public CodeEntry(String character, int weight, String code) {
            this.character = character;
            this.weight = weight;
            this.code = code;
        }
    }

    private final Node root;
    private final List<Node> leaves = new ArrayList<>();
    private final List<Node> allNodes = new ArrayList<>();
    private final int maxDepth;
    private final int[] initialWeights;

    private HuffmanTreeModel(Node root, int[] initialWeights) {
        this.root = root;
        this.initialWeights = initialWeights != null ? initialWeights.clone() : new int[0];
        if (root != null) {
            collectNodes(root, allNodes);
            collectLeavesInOrder(root, leaves);
            this.maxDepth = computeMaxDepth(root);
        } else {
            this.maxDepth = 0;
        }
    }

    public Node getRoot() {
        return root;
    }

    public List<Node> getLeaves() {
        return leaves;
    }

    public List<Node> getAllNodes() {
        return allNodes;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public int[] getInitialWeights() {
        return initialWeights;
    }

    public List<CodeEntry> getCodesList() {
        List<CodeEntry> result = new ArrayList<>();
        for (Node leaf : leaves) {
            result.add(new CodeEntry(leaf.character, leaf.weight, leaf.code));
        }
        Collections.sort(result, new Comparator<CodeEntry>() {
            @Override
            public int compare(CodeEntry o1, CodeEntry o2) {
                // If character is letter, sort alphabetically, otherwise by weight
                int charCompare = o1.character.compareTo(o2.character);
                if (charCompare != 0) {
                    return charCompare;
                }
                return Integer.compare(o1.weight, o2.weight);
            }
        });
        return result;
    }

    public static HuffmanTreeModel build(int[] weights) {
        if (weights == null || weights.length == 0) {
            return new HuffmanTreeModel(null, weights);
        }
        int[] sorted = weights.clone();
        Arrays.sort(sorted);

        if (sorted.length == 1) {
            Node single = new Node(0, sorted[0], resolveCharacter(sorted, 0, sorted[0]), null, null, 0);
            single.code = "0";
            return new HuffmanTreeModel(single, sorted);
        }

        List<Node> activeList = new ArrayList<>();
        for (int i = 0; i < sorted.length; i++) {
            String ch = resolveCharacter(sorted, i, sorted[i]);
            activeList.add(new Node(i, sorted[i], ch, null, null, 0));
        }

        int nextId = sorted.length;
        int step = 1;

        Comparator<Node> nodeComparator = new Comparator<Node>() {
            @Override
            public int compare(Node a, Node b) {
                int w = Integer.compare(a.weight, b.weight);
                if (w != 0) {
                    return w;
                }
                return Integer.compare(a.id, b.id);
            }
        };

        while (activeList.size() > 1) {
            Collections.sort(activeList, nodeComparator);
            Node first = activeList.remove(0);
            Node second = activeList.remove(0);
            int mergedWeight = first.weight + second.weight;

            Node parent = new Node(nextId++, mergedWeight, "", first, second, step);
            activeList.add(parent);
            step++;
        }

        Node root = activeList.isEmpty() ? null : activeList.get(0);
        assignCodesAndDepths(root, "", 0);
        return new HuffmanTreeModel(root, sorted);
    }

    public void layout(float left, float top, float width, float height, boolean compact) {
        if (root == null || leaves.isEmpty()) {
            return;
        }

        int numLeaves = leaves.size();
        float margin = compact ? 22f : 32f;
        float availableW = Math.max(width - 2f * margin, 80f);

        for (int i = 0; i < numLeaves; i++) {
            Node leaf = leaves.get(i);
            leaf.x = left + margin + (numLeaves > 1 ? (i / (float) (numLeaves - 1)) * availableW : availableW / 2f);
        }

        layoutSubtreeX(root);

        float levelHeight = Math.min(compact ? 66f : 80f, height / Math.max(1, maxDepth));
        assignYCoordinates(root, top, levelHeight);
    }

    private static void layoutSubtreeX(Node node) {
        if (node == null || node.isLeaf()) {
            return;
        }
        layoutSubtreeX(node.left);
        layoutSubtreeX(node.right);
        if (node.left != null && node.right != null) {
            node.x = (node.left.x + node.right.x) / 2f;
        } else if (node.left != null) {
            node.x = node.left.x;
        } else if (node.right != null) {
            node.x = node.right.x;
        }
    }

    private static void assignYCoordinates(Node node, float topY, float levelHeight) {
        if (node == null) {
            return;
        }
        node.y = topY + (node.depth * levelHeight);
        assignYCoordinates(node.left, topY, levelHeight);
        assignYCoordinates(node.right, topY, levelHeight);
    }

    private static void assignCodesAndDepths(Node node, String prefix, int depth) {
        if (node == null) {
            return;
        }
        node.code = prefix;
        node.depth = depth;
        if (node.left != null) {
            assignCodesAndDepths(node.left, prefix + "0", depth + 1);
        }
        if (node.right != null) {
            assignCodesAndDepths(node.right, prefix + "1", depth + 1);
        }
    }

    private static void collectLeavesInOrder(Node node, List<Node> leaves) {
        if (node == null) {
            return;
        }
        if (node.isLeaf()) {
            leaves.add(node);
            return;
        }
        collectLeavesInOrder(node.left, leaves);
        collectLeavesInOrder(node.right, leaves);
    }

    private static void collectNodes(Node node, List<Node> allNodes) {
        if (node == null) {
            return;
        }
        allNodes.add(node);
        collectNodes(node.left, allNodes);
        collectNodes(node.right, allNodes);
    }

    private static int computeMaxDepth(Node node) {
        if (node == null) {
            return 0;
        }
        if (node.isLeaf()) {
            return node.depth;
        }
        return Math.max(computeMaxDepth(node.left), computeMaxDepth(node.right));
    }

    private static String resolveCharacter(int[] sortedWeights, int index, int weight) {
        // Check for CLRS Canonical: {5, 9, 12, 13, 16, 45} -> {f, e, c, b, d, a}
        if (sortedWeights.length == 6
                && sortedWeights[0] == 5
                && sortedWeights[1] == 9
                && sortedWeights[2] == 12
                && sortedWeights[3] == 13
                && sortedWeights[4] == 16
                && sortedWeights[5] == 45) {
            switch (weight) {
                case 45: return "a";
                case 13: return "b";
                case 12: return "c";
                case 16: return "d";
                case 9:  return "e";
                case 5:  return "f";
                default: break;
            }
        }
        // Generic: letters A, B, C...
        if (index < 26) {
            return String.valueOf((char) ('A' + index));
        }
        return "S" + (index + 1);
    }
}
