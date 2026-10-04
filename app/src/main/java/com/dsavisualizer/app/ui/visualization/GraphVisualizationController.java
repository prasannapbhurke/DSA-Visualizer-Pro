package com.dsavisualizer.app.ui.visualization;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.dsavisualizer.app.ui.viewmodel.TechniqueSessionViewModel;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Graph-family UI and traversal setup.
 */
public final class GraphVisualizationController {

    public interface ModeCallback {
        void onModeChanged(String mode);
    }

    private GraphVisualizationController() {
    }

    public static boolean configureInputs(
        String techniqueId,
        TextInputLayout valuesInputLayout,
        TextInputLayout targetInputLayout
    ) {
        if (!VisualizationTechniqueRegistry.isGraph(techniqueId)) {
            return false;
        }
        valuesInputLayout.setHint("Graph node values");
        if (needsTargetInput(techniqueId)) {
            targetInputLayout.setVisibility(View.VISIBLE);
            targetInputLayout.setHint(resolveTargetHint(techniqueId));
        } else {
            targetInputLayout.setVisibility(View.GONE);
        }
        return true;
    }

    private static boolean needsTargetInput(String techniqueId) {
        return "find_edge_graph".equals(techniqueId)
            || "insert_node_graph".equals(techniqueId)
            || "insert_edge_graph".equals(techniqueId)
            || "delete_edge_graph".equals(techniqueId)
            || "delete_vertex_graph".equals(techniqueId);
    }

    private static String resolveTargetHint(String techniqueId) {
        if ("insert_node_graph".equals(techniqueId) || "delete_vertex_graph".equals(techniqueId)) {
            return "Node value";
        }
        return "Node / Edge input";
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
        if (!VisualizationTechniqueRegistry.isGraph(techniqueId)) {
            return null;
        }

        String currentMode = sessionViewModel.getGraphMode().getValue() == null
            ? "BFS"
            : sessionViewModel.getGraphMode().getValue();
        traversalGroup.setVisibility(View.VISIBLE);
        traversalLabel.setText("Graph Mode");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            fragment.requireContext(),
            android.R.layout.simple_spinner_item,
            new String[] {"BFS", "DFS"}
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        traversalSpinner.setAdapter(adapter);

        if ("depth_first_search".equals(techniqueId)) {
            currentMode = "DFS";
        } else if ("breadth_first_search".equals(techniqueId)) {
            currentMode = "BFS";
        } else if ("topological_sort".equals(techniqueId)) {
            currentMode = "Topological";
        }

        traversalSpinner.setSelection("DFS".equals(currentMode) ? 1 : 0);
        traversalSpinner.setEnabled("graph_traversal".equals(techniqueId));
        traversalSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                String nextMode = position == 0 ? "BFS" : "DFS";
                sessionViewModel.setGraphMode(nextMode);
                callback.onModeChanged(nextMode);
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
            }
        });
        return currentMode;
    }

    public static String resolveMatrixLabel(String techniqueId) {
        if ("warshall_algorithm".equals(techniqueId)) {
            return "Warshall Matrix";
        }
        if ("floyd_algorithm".equals(techniqueId)) {
            return "Floyd Distance Matrix";
        }
        if ("shortest_paths".equals(techniqueId)) {
            return "Shortest Path Matrix";
        }
        if ("backtracking_n_queens".equals(techniqueId) || "bt_n_queens".equals(techniqueId)) {
            return "N-Queens 4x4 Chessboard";
        }
        if ("backtracking_sudoku".equals(techniqueId) || "bt_sudoku".equals(techniqueId)) {
            return "Sudoku 4x4 Grid";
        }
        if ("backtracking_knights_tour".equals(techniqueId) || "bt_knights_tour".equals(techniqueId)) {
            return "Knight's Tour 5x5";
        }
        if ("backtracking_maze_solver".equals(techniqueId) || "bt_maze".equals(techniqueId) || "bt_rat_in_a_maze".equals(techniqueId)) {
            return "Rat in a Maze 4x4";
        }
        return "Matrix";
    }
}
