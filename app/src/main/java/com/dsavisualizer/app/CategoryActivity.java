package com.dsavisualizer.app;

import android.content.Intent;
import android.os.Bundle;
import android.content.res.Configuration;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dsavisualizer.app.data.AppRepository;
import com.dsavisualizer.app.data.ProManager;
import com.dsavisualizer.app.data.RemoteSyncManager;
import com.dsavisualizer.app.model.TechniqueItem;
import com.dsavisualizer.app.ui.SafeInsetHelper;
import com.dsavisualizer.app.ui.adapter.TechniqueAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CategoryActivity extends AppCompatActivity {

    public static final String EXTRA_CATEGORY_ID = "category_id";
    public static final String EXTRA_CATEGORY_TITLE = "category_title";

    private final List<TechniqueItem> allTechniques = new ArrayList<>();
    private TechniqueAdapter adapter;
    private boolean navigationInProgress = false;
    private RemoteSyncManager remoteSyncManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        SafeInsetHelper.applyTopAndBottomInsets(findViewById(R.id.category_root), 20, 20, 20, 20);

        String categoryId = getIntent().getStringExtra(EXTRA_CATEGORY_ID);
        String categoryTitle = getIntent().getStringExtra(EXTRA_CATEGORY_TITLE);

        TextView titleView = findViewById(R.id.category_title);
        EditText searchInput = findViewById(R.id.category_search_input);
        RecyclerView recyclerView = findViewById(R.id.technique_recycler_view);

        titleView.setText(categoryTitle == null ? "Category" : categoryTitle);
        remoteSyncManager = RemoteSyncManager.getInstance(this);

        adapter = new TechniqueAdapter(this::openTechnique);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        recyclerView.setAdapter(adapter);

        loadTechniques(categoryId);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterTechniques(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationInProgress = false;
        refreshWeakTopics();
    }

    private void loadTechniques(String categoryId) {
        if (categoryId == null) {
            adapter.submitList(allTechniques);
            return;
        }

        new Thread(() -> {
            List<TechniqueItem> loaded = AppRepository.getTechniquesForCategory(categoryId);
            runOnUiThread(() -> {
                if (isFinishing() || isDestroyed()) {
                    return;
                }
                allTechniques.clear();
                allTechniques.addAll(loaded);
                adapter.submitList(allTechniques);
                refreshWeakTopics();
                if (loaded.isEmpty()) {
                    Toast.makeText(this, "No techniques found for this category yet.", Toast.LENGTH_SHORT).show();
                }
            });
        }, "dsa-technique-load").start();
    }

    private void filterTechniques(String query) {
        String normalized = query.trim().toLowerCase();
        if (normalized.isEmpty()) {
            adapter.submitList(allTechniques);
            return;
        }

        List<TechniqueItem> filtered = new ArrayList<>();
        for (TechniqueItem item : allTechniques) {
            if (item.title.toLowerCase().contains(normalized) || item.subtitle.toLowerCase().contains(normalized)) {
                filtered.add(item);
            }
        }
        adapter.submitList(filtered);
    }

    private void openTechnique(TechniqueItem item) {
        if (navigationInProgress) {
            return;
        }
        if (!ProManager.isPro(this) && ProManager.isProTechnique(item)) {
            ProManager.showProFeatureLockedDialog(this, item.title, () -> {
                adapter.notifyDataSetChanged();
                openTechnique(item);
            });
            return;
        }
        navigationInProgress = true;
        Intent intent = new Intent(this, TechniqueDetailActivity.class);
        intent.putExtra(TechniqueDetailActivity.EXTRA_TECHNIQUE_ID, item.id);
        startActivity(intent);
    }

    private void refreshWeakTopics() {
        if (remoteSyncManager == null) {
            return;
        }
        remoteSyncManager.fetchDashboard(snapshot -> {
            Set<String> weakTopicIds = new HashSet<>();
            if (snapshot != null) {
                for (RemoteSyncManager.WeakTopicItem item : snapshot.weakTopics) {
                    weakTopicIds.add(item.techniqueId);
                }
            }
            adapter.setWeakTopicIds(weakTopicIds);
        });
    }
}
