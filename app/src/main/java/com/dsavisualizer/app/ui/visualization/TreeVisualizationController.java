package com.dsavisualizer.app.ui.visualization;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.dsavisualizer.app.ui.viewmodel.TechniqueSessionViewModel;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Tree-family UI and traversal setup.
 */
public final class TreeVisualizationController {

    public interface ModeCallback {
        void onModeChanged(String mode);
    }

    private TreeVisualizationController() {
    }

    public static boolean configureInputs(
        String techniqueId,
        TextInputLayout valuesInputLayout,
        TextInputLayout targetInputLayout
    ) {
        if (!VisualizationTechniqueRegistry.isTree(techniqueId)) {
            return false;
        }
        if (VisualizationTechniqueRegistry.needsTreeTargetInput(techniqueId)) {
            targetInputLayout.setVisibility(View.VISIBLE);
            valuesInputLayout.setHint("Tree node values");
            targetInputLayout.setHint("Item");
            return true;
        }
        targetInputLayout.setVisibility(View.GONE);
        valuesInputLayout.setHint("Tree or graph values");
        return true;
    }

    public static String configureTraversalControls(
        Fragment fragment,
        String techniqueId,
        View traversalGroup,
        TextView traversalLabel,
        Spinner traversalSpinner,
        TechniqueSessionViewModel sessionViewModel,
        ModeCallback callback
    ) {
        if ("tree_traversal".equals(techniqueId)) {
            String currentMode = sessionViewModel.getTreeMode().getValue() == null
                ? "Inorder"
                : sessionViewModel.getTreeMode().getValue();
            traversalGroup.setVisibility(View.VISIBLE);
            traversalLabel.setText("Tree Order");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                fragment.requireContext(),
                android.R.layout.simple_spinner_item,
                new String[] {"Inorder", "Preorder", "Postorder"}
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            traversalSpinner.setAdapter(adapter);
            traversalSpinner.setSelection(resolveTreeSelection(currentMode));
            traversalSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                    String nextMode = position == 0 ? "Inorder" : position == 1 ? "Preorder" : "Postorder";
                    sessionViewModel.setTreeMode(nextMode);
                    callback.onModeChanged(nextMode);
                }

                @Override
                public void onNothingSelected(android.widget.AdapterView<?> parent) {
                }
            });
            return currentMode;
        }

        if ("preorder_traversal".equals(techniqueId)) {
            traversalGroup.setVisibility(View.GONE);
            return "Preorder";
        }

        if ("postorder_traversal".equals(techniqueId)) {
            traversalGroup.setVisibility(View.GONE);
            return "Postorder";
        }

        return null;
    }

    private static int resolveTreeSelection(String mode) {
        if ("Preorder".equals(mode)) {
            return 1;
        }
        if ("Postorder".equals(mode)) {
            return 2;
        }
        return 0;
    }
}
