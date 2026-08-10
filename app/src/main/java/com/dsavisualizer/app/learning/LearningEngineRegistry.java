package com.dsavisualizer.app.learning;

import com.dsavisualizer.app.learning.plugins.BinarySearchLearningPlugin;
import com.dsavisualizer.app.learning.plugins.BfsLearningPlugin;
import com.dsavisualizer.app.learning.plugins.BubbleSortLearningPlugin;
import com.dsavisualizer.app.learning.plugins.InorderTraversalLearningPlugin;
import com.dsavisualizer.app.learning.plugins.LinkedListInsertionLearningPlugin;
import com.dsavisualizer.app.learning.plugins.StackLearningPlugin;

import java.util.HashMap;
import java.util.Map;

public final class LearningEngineRegistry {

    private static final Map<String, AlgorithmLearningPlugin> PLUGINS = new HashMap<>();

    static {
        register(new BinarySearchLearningPlugin());
        register(new BubbleSortLearningPlugin());
        register(new LinkedListInsertionLearningPlugin());
        register(new StackLearningPlugin());
        register(new InorderTraversalLearningPlugin());
        register(new BfsLearningPlugin());
    }

    private LearningEngineRegistry() {
    }

    private static void register(AlgorithmLearningPlugin plugin) {
        PLUGINS.put(plugin.getAlgorithmId(), plugin);
    }

    public static AlgorithmLearningPlugin get(String algorithmId) {
        return PLUGINS.get(algorithmId);
    }
}
