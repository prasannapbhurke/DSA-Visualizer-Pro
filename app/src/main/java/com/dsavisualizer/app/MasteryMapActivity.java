package com.dsavisualizer.app;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dsavisualizer.app.data.AppRepository;
import com.dsavisualizer.app.data.RemoteSyncManager;
import com.dsavisualizer.app.model.CategoryItem;
import com.dsavisualizer.app.model.TechniqueItem;
import com.dsavisualizer.app.ui.SafeInsetHelper;
import com.dsavisualizer.app.ui.adapter.CategoryAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasteryMapActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;
    private CategoryAdapter adapter;
    private final List<CategoryItem> categories = new ArrayList<>();
    private Map<String, Integer> masteryColors = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mastery_map);
        SafeInsetHelper.applyTopAndBottomInsets(findViewById(R.id.mastery_root), 20, 20, 20, 20);

        masteryColors.put("beginner", ContextCompat.getColor(this, R.color.mastery_beginner));
        masteryColors.put("intermediate", ContextCompat.getColor(this, R.color.mastery_intermediate));
        masteryColors.put("expert", ContextCompat.getColor(this, R.color.mastery_expert));
        masteryColors.put("", ContextCompat.getColor(this, R.color.mastery_none));

        categoryRecyclerView = findViewById(R.id.category_recycler_view);
        adapter = new CategoryAdapter(this::openCategory);
        int smallestWidth = getResources().getConfiguration().smallestScreenWidthDp;
        boolean isTablet = smallestWidth >= 600;
        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        int spanCount = isLandscape ? (isTablet ? 4 : 4) : (isTablet ? 3 : 2);
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));
        categoryRecyclerView.setAdapter(adapter);

        loadCategories();
    }

    private void loadCategories() {
        categories.clear();
        // Load categories from AppRepository or hardcoded
        // For now, assume categories are loaded
        categories.addAll(com.dsavisualizer.app.data.HomeCatalog.getCategories());
        adapter.submitList(categories);
    }

    private void openCategory(CategoryItem categoryItem) {
        // Show techniques for the category with mastery colors
        LinearLayout container = findViewById(R.id.techniques_container);
        container.removeAllViews();

        List<TechniqueItem> techniques = AppRepository.getTechniquesForCategory(categoryItem.id);
        for (TechniqueItem technique : techniques) {
            TextView textView = new TextView(this);
            textView.setText(technique.title);
            textView.setPadding(16, 16, 16, 16);
            textView.setTextSize(16);
            // Set background color based on mastery
            // For now, random color, but should get from progress
            textView.setBackgroundColor(masteryColors.get("beginner"));
            container.addView(textView);
        }
    }
}