package com.dsavisualizer.app.ui.visualization;

import android.text.InputType;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

/**
 * Search-family UI setup decisions.
 */
public final class SearchVisualizationController {

    private SearchVisualizationController() {
    }

    public static boolean configureInputs(
        String techniqueId,
        TextInputLayout valuesInputLayout,
        TextInputLayout targetInputLayout,
        EditText valuesInput,
        EditText targetInput
    ) {
        if (!VisualizationTechniqueRegistry.isSearching(techniqueId)) {
            return false;
        }

        if (VisualizationTechniqueRegistry.isPatternMatching(techniqueId)) {
            valuesInputLayout.setHint("Text");
            targetInputLayout.setVisibility(android.view.View.VISIBLE);
            targetInputLayout.setHint("Pattern");
            valuesInput.setInputType(InputType.TYPE_CLASS_TEXT);
            targetInput.setInputType(InputType.TYPE_CLASS_TEXT);
            return true;
        }

        targetInputLayout.setVisibility(android.view.View.VISIBLE);
        valuesInputLayout.setHint("Array values");
        targetInputLayout.setHint("Search number");
        valuesInput.setInputType(InputType.TYPE_CLASS_TEXT);
        targetInput.setInputType(InputType.TYPE_CLASS_TEXT);
        return true;
    }
}
