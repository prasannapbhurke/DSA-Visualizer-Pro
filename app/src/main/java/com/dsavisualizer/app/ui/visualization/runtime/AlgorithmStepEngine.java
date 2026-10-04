package com.dsavisualizer.app.ui.visualization.runtime;

import com.dsavisualizer.app.ui.visualization.VisualizationTechniqueRegistry;

public class AlgorithmStepEngine implements StepEngine {

    private final VisualizationRuntimeHost host;
    private final StepEngine searchEngine;
    private final StepEngine sortEngine;
    private final StepEngine structureEngine;
    private final StepEngine treeEngine;
    private final StepEngine graphEngine;
    private final StepEngine graphExpandedEngine;
    private final StepEngine leetCodeEngine;
    private final StepEngine dpEngine;
    private final StepEngine backtrackingEngine;
    private final StepEngine stringPatternEngine;
    private final StepEngine advancedDsEngine;
    private final StepEngine specializedEngine;

    public AlgorithmStepEngine(VisualizationRuntimeHost host) {
        this.host = host;
        this.searchEngine = new SearchStepEngine(host);
        this.sortEngine = new SortStepEngine(host);
        this.structureEngine = new StructureStepEngine(host);
        this.treeEngine = new TreeStepEngine(host);
        this.graphEngine = new GraphStepEngine(host);
        this.graphExpandedEngine = new GraphExpandedStepEngine(host);
        this.leetCodeEngine = new LeetCodeStepEngine(host);
        this.dpEngine = new DpStepEngine(host);
        this.backtrackingEngine = new BacktrackingStepEngine(host);
        this.stringPatternEngine = new StringPatternStepEngine(host);
        this.advancedDsEngine = new AdvancedDsStepEngine(host);
        this.specializedEngine = new SpecializedStepEngine(host);
    }

    @Override
    public Step next() {
        return resolveEngine().next();
    }

    @Override
    public Step prev() {
        return resolveEngine().prev();
    }

    @Override
    public void reset() {
        resolveEngine().reset();
    }

    private StepEngine resolveEngine() {
        if (host == null) {
            return new StepEngine() {
                @Override
                public Step next() {
                    return Step.idle(true, false);
                }

                @Override
                public Step prev() {
                    return Step.idle(true, false);
                }

                @Override
                public void reset() {
                }
            };
        }
        String techniqueId = host.getTechniqueId();
        if (techniqueId != null && (techniqueId.startsWith("leetcode_") || techniqueId.matches("\\d+"))) {
            return leetCodeEngine;
        }
        if (VisualizationTechniqueRegistry.isDynamicProgramming(techniqueId)) {
            return dpEngine;
        }
        if (VisualizationTechniqueRegistry.isBacktracking(techniqueId)) {
            return backtrackingEngine;
        }
        if (VisualizationTechniqueRegistry.isStringPattern(techniqueId)) {
            return stringPatternEngine;
        }
        if (VisualizationTechniqueRegistry.isAdvancedDs(techniqueId)) {
            return advancedDsEngine;
        }
        if (VisualizationTechniqueRegistry.isSpecialized(techniqueId)) {
            return specializedEngine;
        }
        if (isExpandedGraph(techniqueId)) {
            return graphExpandedEngine;
        }
        if (VisualizationTechniqueRegistry.isSearching(techniqueId)) {
            return searchEngine;
        }
        if (VisualizationTechniqueRegistry.isSorting(techniqueId)) {
            return sortEngine;
        }
        if (VisualizationTechniqueRegistry.isStructure(techniqueId)) {
            return structureEngine;
        }
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) {
            return graphEngine;
        }
        return treeEngine;
    }

    private boolean isExpandedGraph(String id) {
        if (id == null) return false;
        return id.startsWith("graph_bellman_ford") ||
               id.startsWith("graph_floyd_warshall") ||
               id.startsWith("graph_prim") ||
               id.startsWith("graph_kruskal") ||
               id.startsWith("graph_tarjan") ||
               id.startsWith("graph_kosaraju") ||
               id.startsWith("graph_bridges") ||
               id.startsWith("graph_astar") ||
               id.startsWith("graph_a_star") ||
               id.startsWith("graph_edmonds") ||
               id.startsWith("graph_johnson");
    }
}
