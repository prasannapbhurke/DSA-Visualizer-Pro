package com.dsavisualizer.app.ui.visualization.runtime;

import com.dsavisualizer.app.ui.visualization.VisualizationTechniqueRegistry;

public class AlgorithmStepEngine implements StepEngine {

    private final VisualizationRuntimeHost host;
    private final StepEngine searchEngine;
    private final StepEngine sortEngine;
    private final StepEngine structureEngine;
    private final StepEngine treeEngine;
    private final StepEngine graphEngine;

    public AlgorithmStepEngine(VisualizationRuntimeHost host) {
        this.host = host;
        this.searchEngine = new SearchStepEngine(host);
        this.sortEngine = new SortStepEngine(host);
        this.structureEngine = new StructureStepEngine(host);
        this.treeEngine = new TreeStepEngine(host);
        this.graphEngine = new GraphStepEngine(host);
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
}
