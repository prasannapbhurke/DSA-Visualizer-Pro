package com.dsavisualizer.app.ui.visualization.runtime;

import java.util.Arrays;

public final class UnionFindStepEngine extends BaseStepEngine {

    public UnionFindStepEngine(VisualizationRuntimeHost host) {
        super(host);
    }

    @Override
    public Step next() {
        if (host == null) {
            return Step.idle(true, false);
        }
        advance(host);
        host.captureCurrentSnapshot();
        return Step.moved(false, host.isPlaybackComplete());
    }

    public static void advance(VisualizationRuntimeHost host) {
        int[] unionParents = host.getUnionParents();
        if (unionParents.length == 0) {
            initializePreview(host);
            unionParents = host.getUnionParents();
        }
        int[] unionPath = host.getUnionPath();
        if (unionPath.length == 0) {
            host.finish("Union-Find has no nodes to traverse.");
            return;
        }

        int unionPathStep = host.getUnionPathStep() + 1;
        host.setUnionPathStep(unionPathStep);
        int unionRootNode = host.getUnionRootNode();
        if (unionPathStep >= unionPath.length) {
            host.finish("Find completed. Root is " + unionRootNode + ".");
            return;
        }

        int unionFocusNode = unionPath[unionPathStep];
        unionRootNode = unionPath[unionPath.length - 1];
        host.setUnionFocusNode(unionFocusNode);
        host.setUnionRootNode(unionRootNode);
        host.setUnionFindState(unionParents, unionFocusNode, unionRootNode);

        if (unionPathStep == unionPath.length - 1) {
            host.setHighlightedLines(4, 2);
            host.updateStatusText("Reached root " + unionRootNode + ".");
            host.finish("Find completed. Root is " + unionRootNode + ".");
            return;
        }

        host.setHighlightedLines(2, 1);
        host.updateStatusText("Move from node " + unionFocusNode + " to parent " + unionParents[unionFocusNode] + ".");
    }

    public static void initializePreview(VisualizationRuntimeHost host) {
        int[] unionParents = new int[] {0, 0, 1, 2, 3, 4};
        int[] unionPath = buildUnionPath(unionParents, unionParents.length - 1);
        int unionPathStep = -1;
        int unionFocusNode = unionPath.length > 0 ? unionPath[0] : -1;
        int unionRootNode = unionPath.length > 0 ? unionPath[unionPath.length - 1] : -1;

        host.setUnionParents(unionParents);
        host.setUnionPath(unionPath);
        host.setUnionPathStep(unionPathStep);
        host.setUnionFocusNode(unionFocusNode);
        host.setUnionRootNode(unionRootNode);
        host.setUnionFindState(unionParents, unionFocusNode, unionRootNode);
    }

    public static int[] buildUnionPath(int[] parents, int startNode) {
        if (parents.length == 0 || startNode < 0 || startNode >= parents.length) {
            return new int[0];
        }
        int[] temp = new int[parents.length + 1];
        int count = 0;
        int node = startNode;
        while (node >= 0 && node < parents.length) {
            temp[count++] = node;
            if (parents[node] == node) {
                break;
            }
            node = parents[node];
        }
        return Arrays.copyOf(temp, count);
    }
}
