package com.dsavisualizer.app.data;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dsavisualizer.app.R;
import com.dsavisualizer.app.model.TechniqueItem;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Manages Pro access and campus redeem codes for DSA Visualizer Pro.
 */
public final class ProManager {

    public static final String CODE_DYPCET = "DYPCETFULLACCESS";
    public static final String CODE_NIT = "NITFULLACCESS";

    // Set of advanced / Pro categories
    private static final Set<String> PRO_CATEGORIES = new HashSet<>(Arrays.asList(
        "graph",
        "advanced"
    ));

    // Specific algorithms flagged as Pro
    private static final Set<String> PRO_TECHNIQUE_IDS = new HashSet<>(Arrays.asList(
        "merge_sort",
        "radix_sort",
        "heapsort",
        "avl_tree",
        "trie",
        "huffman_algorithm",
        "dijkstra_algorithm",
        "floyd_algorithm",
        "warshall_algorithm",
        "shortest_paths",
        "topological_sort",
        "spanning_tree",
        "dynamic_programming_basics",
        "union_find",
        "hash_collision_handling"
    ));

    private ProManager() {}

    public static boolean isPro(@NonNull Context context) {
        AppPreferences prefs = new AppPreferences(context);
        return prefs.isProUnlocked();
    }

    public static boolean isProTechnique(@NonNull TechniqueItem item) {
        if (item.categoryId != null && PRO_CATEGORIES.contains(item.categoryId.toLowerCase())) {
            return true;
        }
        if (item.id != null && PRO_TECHNIQUE_IDS.contains(item.id.toLowerCase())) {
            return true;
        }
        return "Hard".equalsIgnoreCase(item.difficulty);
    }

    public static boolean redeemCode(@NonNull Context context, @Nullable String inputCode) {
        if (inputCode == null) {
            return false;
        }
        String clean = inputCode.trim().toUpperCase();
        if (CODE_DYPCET.equals(clean) || CODE_NIT.equals(clean)) {
            AppPreferences prefs = new AppPreferences(context);
            prefs.setProUnlocked(true, clean);
            return true;
        }
        return false;
    }

    public static String getWelcomeMessage(@Nullable String code) {
        if (code == null) return "Lifetime Pro Access Unlocked!";
        String clean = code.trim().toUpperCase();
        if (CODE_DYPCET.equals(clean)) {
            return "🎉 Welcome, DYPCETian! Lifetime Pro Access Unlocked. Enjoy mastering DSA!";
        } else if (CODE_NIT.equals(clean)) {
            return "🎉 Welcome, NITian! Lifetime Pro Access Unlocked. Enjoy mastering DSA!";
        }
        return "🎉 Lifetime Pro Access Unlocked!";
    }

    public static void showRedeemDialog(@NonNull Activity activity, @Nullable Runnable onUnlocked) {
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_redeem_pro, null);
        TextInputEditText inputEdit = dialogView.findViewById(R.id.redeem_code_edit);
        TextView statusText = dialogView.findViewById(R.id.redeem_current_status);

        AppPreferences prefs = new AppPreferences(activity);
        if (prefs.isProUnlocked()) {
            statusText.setVisibility(View.VISIBLE);
            statusText.setText("⭐ Pro Active (" + prefs.getProCode() + ")");
        } else {
            statusText.setVisibility(View.GONE);
        }

        new MaterialAlertDialogBuilder(activity)
            .setTitle("🎓 Campus Pass & DSA Pro")
            .setView(dialogView)
            .setPositiveButton("Redeem Access", (dialog, which) -> {
                String code = inputEdit.getText() != null ? inputEdit.getText().toString() : "";
                if (redeemCode(activity, code)) {
                    String msg = getWelcomeMessage(code);
                    Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
                    if (onUnlocked != null) {
                        onUnlocked.run();
                    }
                } else {
                    Toast.makeText(activity, "Invalid code. Please check with your campus coordinator.", Toast.LENGTH_LONG).show();
                }
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    public static void showProFeatureLockedDialog(@NonNull Activity activity, @NonNull String featureName, @Nullable Runnable onUnlocked) {
        new MaterialAlertDialogBuilder(activity)
            .setTitle("🔒 " + featureName + " (Pro)")
            .setMessage(featureName + " is an advanced Pro topic.\n\nStudents from partner campuses (DYPCET & NIT) can unlock full access for free using their Campus Pass code.")
            .setPositiveButton("Enter College Code", (dialog, which) -> {
                showRedeemDialog(activity, onUnlocked);
            })
            .setNegativeButton("Cancel", null)
            .show();
    }
}
