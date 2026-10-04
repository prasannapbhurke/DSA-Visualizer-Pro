package com.dsavisualizer.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dsavisualizer.app.data.AppPreferences;
import com.dsavisualizer.app.data.LeetCodeDataManager;
import com.dsavisualizer.app.model.LeetCodeProblemModel;
import com.dsavisualizer.app.ui.SafeInsetHelper;
import com.dsavisualizer.app.ui.adapter.LeetCodeProblemAdapter;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.List;

/**
 * Hub Activity for the LeetCode Master 2000 Roadmap.
 * Supports instant search across 2,000 problems, 8-part navigation,
 * multi-filter criteria, and live progress persistence.
 */
public class LeetCodeHubActivity extends AppCompatActivity implements LeetCodeProblemAdapter.OnProblemActionListener {

    private LeetCodeDataManager dataManager;
    private AppPreferences preferences;
    private LeetCodeProblemAdapter adapter;

    private EditText searchInput;
    private ImageButton searchClear;
    private ChipGroup partChipGroup;
    private ChipGroup diffChipGroup;
    private ChipGroup statusChipGroup;

    private TextView progressPercentText;
    private LinearProgressIndicator progressBar;
    private TextView solvedCountText;
    private TextView statEasyText;
    private TextView statMedText;
    private TextView statHardText;
    private TextView countSummaryText;
    private TextView emptyView;

    private int currentPart = 1; // 1 to 8, 0 for All
    private String currentDifficulty = "all";
    private String currentStatus = "all";
    private String currentQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leetcode_hub);
        SafeInsetHelper.applyTopAndBottomInsets(findViewById(R.id.leetcode_hub_root), 16, 12, 16, 16);

        preferences = new AppPreferences(this);
        dataManager = LeetCodeDataManager.getInstance();

        initViews();
        setupListeners();

        // Load 2000 problems from assets asynchronously
        dataManager.ensureLoaded(this, problems -> runOnUiThread(() -> {
            dataManager.syncUserState(preferences);
            updateProgressMetrics();
            applyFilters();
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dataManager != null) {
            dataManager.syncUserState(preferences);
            updateProgressMetrics();
            applyFilters();
        }
    }

    private void initViews() {
        ImageButton backButton = findViewById(R.id.leetcode_hub_back);
        backButton.setOnClickListener(v -> finish());

        searchInput = findViewById(R.id.leetcode_search_input);
        searchClear = findViewById(R.id.leetcode_search_clear);
        partChipGroup = findViewById(R.id.leetcode_part_chip_group);
        diffChipGroup = findViewById(R.id.leetcode_diff_chip_group);
        statusChipGroup = findViewById(R.id.leetcode_status_chip_group);

        progressPercentText = findViewById(R.id.leetcode_progress_percent_text);
        progressBar = findViewById(R.id.leetcode_progress_bar);
        solvedCountText = findViewById(R.id.leetcode_solved_count_text);
        statEasyText = findViewById(R.id.leetcode_stat_easy);
        statMedText = findViewById(R.id.leetcode_stat_med);
        statHardText = findViewById(R.id.leetcode_stat_hard);
        countSummaryText = findViewById(R.id.leetcode_count_summary);
        emptyView = findViewById(R.id.leetcode_empty_view);

        RecyclerView recyclerView = findViewById(R.id.leetcode_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LeetCodeProblemAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void setupListeners() {
        // Search text watcher
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentQuery = s != null ? s.toString().trim() : "";
                searchClear.setVisibility(currentQuery.isEmpty() ? View.GONE : View.VISIBLE);
                applyFilters();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        searchClear.setOnClickListener(v -> {
            searchInput.setText("");
            currentQuery = "";
            applyFilters();
        });

        // Part selector chips
        partChipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.chip_part_1) currentPart = 1;
            else if (checkedId == R.id.chip_part_2) currentPart = 2;
            else if (checkedId == R.id.chip_part_3) currentPart = 3;
            else if (checkedId == R.id.chip_part_4) currentPart = 4;
            else if (checkedId == R.id.chip_part_5) currentPart = 5;
            else if (checkedId == R.id.chip_part_6) currentPart = 6;
            else if (checkedId == R.id.chip_part_7) currentPart = 7;
            else if (checkedId == R.id.chip_part_8) currentPart = 8;
            else if (checkedId == R.id.chip_part_all) currentPart = 0;
            applyFilters();
        });

        // Difficulty chips
        diffChipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.chip_diff_easy) currentDifficulty = "Easy";
            else if (checkedId == R.id.chip_diff_medium) currentDifficulty = "Medium";
            else if (checkedId == R.id.chip_diff_hard) currentDifficulty = "Hard";
            else currentDifficulty = "all";
            applyFilters();
        });

        // Status chips
        statusChipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.chip_status_solved) currentStatus = "solved";
            else if (checkedId == R.id.chip_status_unsolved) currentStatus = "unsolved";
            else if (checkedId == R.id.chip_status_bookmarked) currentStatus = "bookmarked";
            else currentStatus = "all";
            applyFilters();
        });
    }

    private void applyFilters() {
        List<LeetCodeProblemModel> filtered = dataManager.filterProblems(
                currentPart,
                currentDifficulty,
                currentStatus,
                currentQuery
        );

        adapter.submitList(filtered);

        if (filtered.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            countSummaryText.setText("0 problems found");
        } else {
            emptyView.setVisibility(View.GONE);
            String partLabel = currentPart > 0 ? ("in Part " + currentPart) : "across all parts";
            countSummaryText.setText("Showing " + filtered.size() + " problems " + partLabel);
        }
    }

    private void updateProgressMetrics() {
        int total = 2000;
        int solved = dataManager.getSolvedCount();
        int percent = total > 0 ? (solved * 100 / total) : 0;

        progressBar.setProgress(percent);
        progressPercentText.setText(percent + "% Completed");
        solvedCountText.setText(solved + " / 2,000 Solved");

        int easySolved = dataManager.getSolvedCountByDifficulty("Easy");
        int medSolved = dataManager.getSolvedCountByDifficulty("Medium");
        int hardSolved = dataManager.getSolvedCountByDifficulty("Hard");

        statEasyText.setText("🟢 " + easySolved + " Easy");
        statMedText.setText("🟡 " + medSolved + " Med");
        statHardText.setText("🔴 " + hardSolved + " Hard");
    }

    @Override
    public void onProblemClick(LeetCodeProblemModel problem) {
        preferences.incrementExploredActions();
        Intent intent = new Intent(this, LeetCodeProblemActivity.class);
        intent.putExtra(LeetCodeProblemActivity.EXTRA_PROBLEM_ORDER, problem.order);
        intent.putExtra(LeetCodeProblemActivity.EXTRA_PROBLEM_NUMBER, problem.lcNumber);
        intent.putExtra(LeetCodeProblemActivity.EXTRA_PROBLEM_TITLE, problem.title);
        intent.putExtra(LeetCodeProblemActivity.EXTRA_PROBLEM_DIFFICULTY, problem.difficulty);
        intent.putExtra(LeetCodeProblemActivity.EXTRA_PROBLEM_PATTERN, problem.pattern);
        intent.putExtra(LeetCodeProblemActivity.EXTRA_PROBLEM_COMPANIES, problem.companies);
        intent.putExtra(LeetCodeProblemActivity.EXTRA_PROBLEM_SLUG, problem.slug);
        intent.putExtra(LeetCodeProblemActivity.EXTRA_PROBLEM_URL, problem.url);
        startActivity(intent);
    }

    @Override
    public void onSolveToggle(LeetCodeProblemModel problem, int position) {
        boolean newState = !problem.isSolved;
        problem.isSolved = newState;
        preferences.setLeetCodeSolved(problem.getId(), newState);
        adapter.notifyItemChanged(position);
        updateProgressMetrics();
    }

    @Override
    public void onBookmarkToggle(LeetCodeProblemModel problem, int position) {
        boolean newState = !problem.isBookmarked;
        problem.isBookmarked = newState;
        preferences.toggleLeetCodeBookmark(problem.getId());
        adapter.notifyItemChanged(position);
    }
}
