package com.dsavisualizer.app.ui.view;

import android.graphics.Canvas;

/**
 * Small rendering contract so each algorithm family can evolve independently
 * without turning the main view back into one giant conditional block.
 */
interface VisualizerEngine {

    boolean supports(String techniqueId);

    void draw(Canvas canvas, TechniqueVisualizerView view, String techniqueId);

    int resolveDesiredWidth(TechniqueVisualizerView view, String techniqueId);

    int resolveDesiredHeight(TechniqueVisualizerView view, String techniqueId);
}
