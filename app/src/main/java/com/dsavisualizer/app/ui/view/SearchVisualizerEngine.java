package com.dsavisualizer.app.ui.view;

import android.graphics.Canvas;

/**
 * Search algorithms share the same resizable horizontal track, but they keep
 * their own pointer and discard-range rules inside the host view state.
 */
final class SearchVisualizerEngine implements VisualizerEngine {

    @Override
    public boolean supports(String techniqueId) {
        return "linear_search".equals(techniqueId)
            || "binary_search".equals(techniqueId)
            || "jump_search".equals(techniqueId)
            || "pattern_matching".equals(techniqueId);
    }

    @Override
    public void draw(Canvas canvas, TechniqueVisualizerView view, String techniqueId) {
        if ("pattern_matching".equals(techniqueId)) {
            view.drawPatternScene(canvas);
            return;
        }
        view.drawHorizontalBoxes(canvas, false);
    }

    @Override
    public int resolveDesiredWidth(TechniqueVisualizerView view, String techniqueId) {
        if ("pattern_matching".equals(techniqueId)) {
            return view.resolvePatternDesiredWidth();
        }
        return view.resolveArrayDesiredWidth();
    }

    @Override
    public int resolveDesiredHeight(TechniqueVisualizerView view, String techniqueId) {
        return view.resolveStandardDesiredHeight();
    }
}
