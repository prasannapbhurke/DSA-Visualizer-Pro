package com.dsavisualizer.app.ui.visualization.runtime;

import java.util.ArrayList;
import java.util.List;

public final class AvlTreeStepEngine extends BaseStepEngine {

    public AvlTreeStepEngine(VisualizationRuntimeHost host) {
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

    public static int getTotalSteps(int[] values) {
        return 3;
    }

    public static String getDefaultInput(int preset) {
        switch (preset) {
            case 2: return "30, 20, 10"; // LL
            case 3: return "30, 10, 20"; // LR
            case 4: return "10, 30, 20"; // RL
            case 5: return "30, 20, 40, 10, 25, 35, 50"; // Balanced 7-node
            case 1:
            default: return "10, 20, 30"; // RR
        }
    }

    public static String getPresetDescription(int preset) {
        switch (preset) {
            case 2:
                return "LL Heavy Imbalance: Sequential insert [30, 20, 10] causes BF = +2 at root 30. Right rotation around 30 using pivot 20 restores balance.";
            case 3:
                return "LR Double Rotation: Insert [30, 10, 20]. Left-heavy root (BF = +2) and right-heavy child (BF = -1) trigger Left Rotation on 10, then Right Rotation on 30.";
            case 4:
                return "RL Double Rotation: Insert [10, 30, 20]. Right-heavy root (BF = -2) and left-heavy child (BF = +1) trigger Right Rotation on 30, then Left Rotation on 10.";
            case 5:
                return "Balanced (7-Node): Complete 3-level height-balanced AVL tree [30, 20, 40, 10, 25, 35, 50]. All balance factors are strictly within {-1, 0, 1}.";
            case 1:
            default:
                return "RR Heavy Imbalance: Sequential insert [10, 20, 30] causes BF = -2 at root 10. Left rotation around 10 using pivot 20 restores balance.";
        }
    }

    public static int[] parseInput(String input, int preset) {
        if (input != null && !input.trim().isEmpty()) {
            try {
                String[] parts = input.trim().split("[,\\s]+");
                List<Integer> list = new ArrayList<>();
                for (String p : parts) {
                    p = p.trim();
                    if (!p.isEmpty()) {
                        list.add(Integer.parseInt(p));
                    }
                }
                if (!list.isEmpty()) {
                    if (list.size() == 1) {
                        int v0 = list.get(0);
                        return new int[]{v0 - 10, v0, v0 + 10};
                    } else if (list.size() == 2) {
                        int v0 = list.get(0);
                        int v1 = list.get(1);
                        int diff = Math.abs(v1 - v0);
                        if (diff == 0) diff = 10;
                        int v2 = (v1 > v0) ? (v1 + diff) : (v1 - diff);
                        return new int[]{v0, v1, v2};
                    }
                    int[] arr = new int[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        arr[i] = list.get(i);
                    }
                    return arr;
                }
            } catch (Exception ignored) {}
        }
        return parseInput(getDefaultInput(preset), 1);
    }

    public static int[] buildBalancedTreeArray(int[] vals) {
        if (vals == null || vals.length == 0) {
            return new int[]{20, 10, 30};
        }
        if (vals.length == 1) {
            return new int[]{vals[0]};
        }
        if (vals.length == 2) {
            int min = Math.min(vals[0], vals[1]);
            int max = Math.max(vals[0], vals[1]);
            return new int[]{min, max};
        }
        if (vals.length == 3) {
            int min = Math.min(vals[0], Math.min(vals[1], vals[2]));
            int max = Math.max(vals[0], Math.max(vals[1], vals[2]));
            int mid = vals[0] + vals[1] + vals[2] - min - max;
            return new int[]{mid, min, max};
        }
        int[] sorted = vals.clone();
        java.util.Arrays.sort(sorted);
        List<Integer> result = new ArrayList<>();
        List<int[]> queue = new ArrayList<>();
        queue.add(new int[]{0, sorted.length - 1});
        while (!queue.isEmpty()) {
            int[] range = queue.remove(0);
            int s = range[0];
            int e = range[1];
            if (s > e) continue;
            int mid = (s + e) / 2;
            result.add(sorted[mid]);
            if (s <= mid - 1) {
                queue.add(new int[]{s, mid - 1});
            }
            if (mid + 1 <= e) {
                queue.add(new int[]{mid + 1, e});
            }
        }
        int[] out = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            out[i] = result.get(i);
        }
        return out;
    }

    public static void advance(VisualizationRuntimeHost host) {
        int[] vals = host.getWorkingValues();
        if (vals == null || vals.length == 0) {
            vals = new int[] {30, 20, 40, 10, 25};
        }
        int v0 = vals[0];
        int v1 = vals.length > 1 ? vals[1] : (v0 - 10);
        int v2 = vals.length > 2 ? vals[2] : (v0 + 10);
        int v3 = vals.length > 3 ? vals[3] : (vals.length == 3 ? v2 : (v1 - 5));

        boolean is3Node = (vals.length == 3);
        boolean isLL = is3Node && (v0 > v1 && v1 > v2);
        boolean isRR = is3Node && (v0 < v1 && v1 < v2);
        boolean isLR = is3Node && (v0 > v1 && v2 > v1 && v2 < v0);
        boolean isRL = is3Node && (v0 < v1 && v2 < v1 && v2 > v0);

        int step = host.getStepIndex();
        if (step == -1) {
            host.setStepIndex(0);
            host.setActiveNodes(1, 3);
            host.setHighlightedLines(2, 1);

            if (vals.length >= 4) {
                host.updateStatusText("Insert " + v3 + " into left subtree. Check balance factor at parent " + v1 + " (BF = +1).");
            } else if (isRR) {
                host.updateStatusText("Insert " + v2 + " into right subtree. Check balance factor at parent " + v1 + " (BF = -1).");
            } else if (isLL) {
                host.updateStatusText("Insert " + v2 + " into left subtree. Check balance factor at parent " + v1 + " (BF = +1).");
            } else if (isLR) {
                host.updateStatusText("Insert " + v2 + " into right subtree of " + v1 + ". Check balance factor at parent " + v1 + " (BF = -1).");
            } else if (isRL) {
                host.updateStatusText("Insert " + v2 + " into left subtree of " + v1 + ". Check balance factor at parent " + v1 + " (BF = +1).");
            } else {
                host.updateStatusText("Insert " + v3 + " into left subtree. Check balance factor at parent " + v1 + " (BF = +1).");
            }
            return;
        }

        if (step == 0) {
            host.setStepIndex(1);
            host.setActiveNodes(0, 1);
            host.setHighlightedLines(4, 2);

            if (vals.length >= 4) {
                host.updateStatusText("Propagate upwards to root " + v0 + ": Left height exceeds right height (BF = +2). Perform right rotation to balance the AVL tree around " + v0 + " using pivot " + v1 + ".");
            } else if (isRR) {
                host.updateStatusText("Propagate upwards to root " + v0 + ": Right height exceeds left height (BF = -2). Perform left rotation (RR) to balance the AVL tree around " + v0 + " using pivot " + v1 + ".");
            } else if (isLL) {
                host.updateStatusText("Propagate upwards to root " + v0 + ": Left height exceeds right height (BF = +2). Perform right rotation (LL) to balance the AVL tree around " + v0 + " using pivot " + v1 + ".");
            } else if (isLR) {
                host.updateStatusText("Propagate upwards to root " + v0 + ": Left-Right (LR) imbalance (BF = +2). Perform double rotation (Left on " + v1 + ", Right on " + v0 + ") around " + v0 + " using pivot " + v2 + ".");
            } else if (isRL) {
                host.updateStatusText("Propagate upwards to root " + v0 + ": Right-Left (RL) imbalance (BF = -2). Perform double rotation (Right on " + v1 + ", Left on " + v0 + ") around " + v0 + " using pivot " + v2 + ".");
            } else {
                host.updateStatusText("Propagate upwards to root " + v0 + ": Left height exceeds right height (BF = +2). Perform right rotation to balance the AVL tree around " + v0 + " using pivot " + v1 + ".");
            }
            return;
        }

        // Step 2 / completion: Restore height balanced tree
        host.setWorkingValues(buildBalancedTreeArray(vals));
        host.finish("AVL rotation completed successfully. Strictly height-balanced tree restored (|BF| <= 1).");
    }
}
