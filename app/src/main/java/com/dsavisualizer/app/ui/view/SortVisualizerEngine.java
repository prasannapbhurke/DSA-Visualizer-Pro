package com.dsavisualizer.app.ui.view;

import android.graphics.Canvas;

/**
 * Sorting algorithms use the shared horizontal scene, but separating them as
 * their own engine gives us room for deeper pass-specific visuals later.
 */
final class SortVisualizerEngine implements VisualizerEngine {

    @Override
    public boolean supports(String techniqueId) {
        return "bubble_sort".equals(techniqueId)
            || "selection_sort".equals(techniqueId)
            || "insertion_sort".equals(techniqueId)
            || "merging".equals(techniqueId)
            || "merge_sort".equals(techniqueId)
            || "radix_sort".equals(techniqueId)
            || "quicksort".equals(techniqueId)
            || "heapsort".equals(techniqueId);
    }

    @Override
    public void draw(Canvas canvas, TechniqueVisualizerView view, String techniqueId) {
        if ("merging".equals(techniqueId) || "merge_sort".equals(techniqueId)) {
            view.drawMergeScene(canvas);
            return;
        }
        if ("radix_sort".equals(techniqueId)) {
            view.drawRadixScene(canvas);
            return;
        }
        if ("heapsort".equals(techniqueId)) {
            view.drawHeapTree(canvas);
            return;
        }
        view.drawHorizontalBoxes(canvas, false);
    }

    @Override
    public int resolveDesiredWidth(TechniqueVisualizerView view, String techniqueId) {
        return view.resolveArrayDesiredWidth();
    }

    @Override
    public int resolveDesiredHeight(TechniqueVisualizerView view, String techniqueId) {
        if ("radix_sort".equals(techniqueId)) {
            return Math.max(view.resolveStandardDesiredHeight(), 760);
        }
        if ("merging".equals(techniqueId) || "merge_sort".equals(techniqueId)) {
            return Math.max(view.resolveStandardDesiredHeight(), 520);
        }
        if ("heapsort".equals(techniqueId)) {
            return Math.max(view.resolveStandardDesiredHeight(), 620);
        }
        return view.resolveStandardDesiredHeight();
    }
}
