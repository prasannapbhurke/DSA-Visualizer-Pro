package com.dsavisualizer.app.ui.visualization;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.dsavisualizer.app.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Structure-family UI wiring for stack, queue, and deque controls.
 */
public final class StructureVisualizationController {

    public interface OperationHandler {
        void onOperation(String operation);
    }

    private StructureVisualizationController() {
    }

    public static boolean configureInputs(
        String techniqueId,
        TextInputLayout valuesInputLayout,
        TextInputLayout targetInputLayout
    ) {
        if (!VisualizationTechniqueRegistry.isStructure(techniqueId)) {
            return false;
        }
        valuesInputLayout.setHint("Values");
        if (needsTargetInput(techniqueId)) {
            targetInputLayout.setVisibility(View.VISIBLE);
            targetInputLayout.setHint(resolveTargetHint(techniqueId));
        } else {
            targetInputLayout.setVisibility(View.GONE);
        }
        return true;
    }

    private static boolean needsTargetInput(String techniqueId) {
        return "search_in_linked_list".equals(techniqueId)
            || "find_linked_list".equals(techniqueId)
            || "insert_at_beginning_linked_list".equals(techniqueId)
            || "insert_after_given_node_linked_list".equals(techniqueId)
            || "insert_into_sorted_linked_list".equals(techniqueId)
            || "delete_node_given_location_linked_list".equals(techniqueId)
            || "delete_node_by_item_linked_list".equals(techniqueId)
            || "delete_linked_list".equals(techniqueId);
    }

    private static String resolveTargetHint(String techniqueId) {
        if ("delete_node_given_location_linked_list".equals(techniqueId)) {
            return "Location / Index";
        }
        if ("insert_at_beginning_linked_list".equals(techniqueId)
            || "insert_after_given_node_linked_list".equals(techniqueId)
            || "insert_into_sorted_linked_list".equals(techniqueId)) {
            return "Item to insert";
        }
        return "Item";
    }

    public static void bindOperationControls(
        String techniqueId,
        View operationsGroup,
        View operationsTitle,
        View primaryRow,
        View secondaryRow,
        TextInputLayout operationInputLayout,
        MaterialButton primaryButton,
        MaterialButton secondaryButton,
        MaterialButton tertiaryButton,
        MaterialButton fourthButton,
        OperationHandler stackHandler,
        OperationHandler queueHandler,
        OperationHandler dequeHandler
    ) {
        if ("stack_basics".equals(techniqueId)
            || "linked_stack_push".equals(techniqueId)
            || "linked_stack_pop".equals(techniqueId)) {
            operationsGroup.setVisibility(View.VISIBLE);
            if (operationsTitle != null) {
                operationsTitle.setVisibility(View.GONE);
            }
            operationInputLayout.setVisibility(View.GONE);
            if (primaryRow != null) {
                primaryRow.setVisibility(View.VISIBLE);
            }
            if (secondaryRow != null) {
                secondaryRow.setVisibility(View.GONE);
            }
            configureCompactActionButton(primaryButton, "Pop", R.drawable.ic_action_remove_mini);
            configureCompactActionButton(secondaryButton, "Push", R.drawable.ic_action_add_mini);
            tertiaryButton.setVisibility(View.GONE);
            fourthButton.setVisibility(View.GONE);

            primaryButton.setOnClickListener(v -> stackHandler.onOperation("pop"));
            secondaryButton.setOnClickListener(v -> stackHandler.onOperation("push"));
            return;
        }

        if ("queue_basics".equals(techniqueId)
            || "circular_queue".equals(techniqueId)
            || "linked_queue".equals(techniqueId)) {
            operationsGroup.setVisibility(View.VISIBLE);
            if (operationsTitle != null) {
                operationsTitle.setVisibility(View.GONE);
            }
            operationInputLayout.setVisibility(View.GONE);
            if (primaryRow != null) {
                primaryRow.setVisibility(View.VISIBLE);
            }
            if (secondaryRow != null) {
                secondaryRow.setVisibility(View.GONE);
            }
            configureCompactActionButton(primaryButton, "Dequeue", R.drawable.ic_action_remove_mini);
            configureCompactActionButton(secondaryButton, "Enqueue", R.drawable.ic_action_add_mini);
            tertiaryButton.setVisibility(View.GONE);
            fourthButton.setVisibility(View.GONE);

            primaryButton.setOnClickListener(v -> queueHandler.onOperation("dequeue"));
            secondaryButton.setOnClickListener(v -> queueHandler.onOperation("enqueue"));
            return;
        }

        if ("priority_queue_array".equals(techniqueId)
            || "priority_queue_2d".equals(techniqueId)) {
            operationsGroup.setVisibility(View.VISIBLE);
            if (operationsTitle != null) {
                operationsTitle.setVisibility(View.GONE);
            }
            operationInputLayout.setVisibility(View.GONE);
            if (primaryRow != null) {
                primaryRow.setVisibility(View.VISIBLE);
            }
            if (secondaryRow != null) {
                secondaryRow.setVisibility(View.GONE);
            }
            configureCompactActionButton(primaryButton, "Dequeue", R.drawable.ic_action_remove_mini);
            configureCompactActionButton(secondaryButton, "Enqueue", R.drawable.ic_action_add_mini);
            tertiaryButton.setVisibility(View.GONE);
            fourthButton.setVisibility(View.GONE);

            primaryButton.setOnClickListener(v -> queueHandler.onOperation("dequeue"));
            secondaryButton.setOnClickListener(v -> queueHandler.onOperation("enqueue"));
            return;
        }

        if ("deque".equals(techniqueId)) {
            operationsGroup.setVisibility(View.VISIBLE);
            if (operationsTitle != null) {
                operationsTitle.setVisibility(View.GONE);
            }
            operationInputLayout.setVisibility(View.GONE);
            if (primaryRow != null) {
                primaryRow.setVisibility(View.VISIBLE);
            }
            if (secondaryRow != null) {
                secondaryRow.setVisibility(View.VISIBLE);
            }
            configureCompactActionButton(primaryButton, "Pop Front", R.drawable.ic_action_remove_mini);
            configureCompactActionButton(secondaryButton, "Push Rear", R.drawable.ic_action_add_mini);
            configureCompactActionButton(tertiaryButton, "Push Front", R.drawable.ic_action_add_mini);
            configureCompactActionButton(fourthButton, "Pop Rear", R.drawable.ic_action_remove_mini);
            tertiaryButton.setVisibility(View.VISIBLE);
            fourthButton.setVisibility(View.VISIBLE);

            primaryButton.setOnClickListener(v -> dequeHandler.onOperation("front_out"));
            secondaryButton.setOnClickListener(v -> dequeHandler.onOperation("rear_in"));
            tertiaryButton.setOnClickListener(v -> dequeHandler.onOperation("front_in"));
            fourthButton.setOnClickListener(v -> dequeHandler.onOperation("rear_out"));
            return;
        }

        if (primaryRow != null) {
            primaryRow.setVisibility(View.VISIBLE);
        }
        if (secondaryRow != null) {
            secondaryRow.setVisibility(View.VISIBLE);
        }
        if (operationsTitle != null) {
            operationsTitle.setVisibility(View.VISIBLE);
        }
        resetActionButton(primaryButton);
        resetActionButton(secondaryButton);
        resetActionButton(tertiaryButton);
        resetActionButton(fourthButton);
        tertiaryButton.setVisibility(View.VISIBLE);
        fourthButton.setVisibility(View.VISIBLE);
        operationsGroup.setVisibility(View.GONE);
    }

    private static void configureCompactActionButton(MaterialButton button, String text, int iconRes) {
        resetActionButton(button);
        button.setVisibility(View.VISIBLE);
        button.setText(text);
        button.setTextSize(11f);
        int horizontal = dp(button, 8);
        int vertical = dp(button, 5);
        button.setPadding(horizontal, vertical, horizontal, vertical);
        button.setMinHeight(dp(button, 36));
        button.setMinWidth(dp(button, 104));
        button.setCornerRadius(dp(button, 18));
        boolean lightTheme = isLightTheme(button);
        button.setBackgroundTintList(ColorStateList.valueOf(
            lightTheme ? Color.parseColor("#EEF3F8") : Color.parseColor("#7A1A2635")
        ));
        button.setStrokeWidth(dp(button, 1));
        button.setStrokeColor(ColorStateList.valueOf(
            lightTheme ? Color.parseColor("#B7CBE0") : Color.parseColor("#7AB8D7F6")
        ));
        button.setRippleColor(ColorStateList.valueOf(
            lightTheme ? Color.parseColor("#220F172A") : Color.parseColor("#33FFFFFF")
        ));
        int textColor = resolveThemeColor(button, android.R.attr.textColorPrimary, R.color.text_primary_bright);
        button.setTextColor(textColor);
        button.setIconResource(iconRes);
        button.setIconTint(ColorStateList.valueOf(textColor));
        button.setIconPadding(dp(button, 6));
        button.setIconSize(dp(button, 12));
        button.setInsetTop(0);
        button.setInsetBottom(0);
        ViewCompat.setElevation(button, dp(button, 10));
    }

    private static void resetActionButton(MaterialButton button) {
        button.setVisibility(View.VISIBLE);
        button.setTextSize(14f);
        int horizontal = dp(button, 16);
        int vertical = dp(button, 10);
        button.setPadding(horizontal, vertical, horizontal, vertical);
        button.setMinHeight(dp(button, 48));
        button.setMinWidth(0);
        button.setIcon(null);
    }

    private static int dp(View view, int value) {
        return Math.round(value * view.getResources().getDisplayMetrics().density);
    }

    private static int resolveThemeColor(View view, int attrResId, int fallbackColorResId) {
        TypedValue typedValue = new TypedValue();
        if (view.getContext().getTheme().resolveAttribute(attrResId, typedValue, true)) {
            if (typedValue.resourceId != 0) {
                return ContextCompat.getColor(view.getContext(), typedValue.resourceId);
            }
            return typedValue.data;
        }
        return ContextCompat.getColor(view.getContext(), fallbackColorResId);
    }

    private static boolean isLightTheme(View view) {
        TypedValue typedValue = new TypedValue();
        if (view.getContext().getTheme().resolveAttribute(androidx.appcompat.R.attr.isLightTheme, typedValue, true)) {
            return typedValue.data != 0;
        }
        return false;
    }
}
