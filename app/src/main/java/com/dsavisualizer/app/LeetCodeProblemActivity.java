package com.dsavisualizer.app;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dsavisualizer.app.data.AppPreferences;
import com.dsavisualizer.app.data.AppRepository;
import com.dsavisualizer.app.data.LeetCodeDataManager;
import com.dsavisualizer.app.data.LeetCodeProblemDetailRepository;
import com.dsavisualizer.app.model.LeetCodeProblemModel;
import com.dsavisualizer.app.model.TechniqueContent;
import com.dsavisualizer.app.model.TechniqueItem;
import com.dsavisualizer.app.ui.SafeInsetHelper;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;
import java.util.Map;

/**
 * Dedicated Problem Detail screen for the LeetCode Master 2000 Roadmap.
 * Displays problem statement, constraints, expected time & space complexities,
 * company interview tags, direct "Open on LeetCode" external link,
 * sequential Prev/Next navigation, and animated algorithm visualization.
 */
public class LeetCodeProblemActivity extends AppCompatActivity {

    public static final String EXTRA_TECHNIQUE_ID = "technique_id";
    public static final String EXTRA_PROBLEM_ORDER = "problem_order";
    public static final String EXTRA_PROBLEM_NUMBER = "problem_number";
    public static final String EXTRA_PROBLEM_TITLE = "problem_title";
    public static final String EXTRA_PROBLEM_DIFFICULTY = "problem_difficulty";
    public static final String EXTRA_PROBLEM_PATTERN = "problem_pattern";
    public static final String EXTRA_PROBLEM_COMPANIES = "problem_companies";
    public static final String EXTRA_PROBLEM_SLUG = "problem_slug";
    public static final String EXTRA_PROBLEM_URL = "problem_url";

    private static final Map<Integer, String> LC_TO_TECHNIQUE = new HashMap<>();
    static {
        LC_TO_TECHNIQUE.put(1, "leetcode_two_sum");
        LC_TO_TECHNIQUE.put(20, "leetcode_valid_parentheses");
        LC_TO_TECHNIQUE.put(206, "leetcode_reverse_linked_list");
        LC_TO_TECHNIQUE.put(704, "leetcode_binary_search");
        LC_TO_TECHNIQUE.put(121, "leetcode_best_time_stock");
        LC_TO_TECHNIQUE.put(21, "leetcode_merge_two_sorted_lists");
        LC_TO_TECHNIQUE.put(226, "leetcode_invert_binary_tree");
        LC_TO_TECHNIQUE.put(141, "leetcode_linked_list_cycle");
    }

    private LeetCodeDataManager dataManager;
    private AppPreferences preferences;

    private int currentOrder = 1;
    private LeetCodeProblemModel currentProblem;
    private String resolvedTechniqueId;

    private ImageButton backButton;
    private ImageButton solveButton;
    private ImageButton bookmarkButton;
    private TextView titleView;
    private TextView sourceView;
    private TextView difficultyBadge;
    private TextView companyText;
    private TextView descriptionText;
    private TextView timeComplexityView;
    private TextView spaceComplexityView;
    private MaterialButton openWebButton;
    private MaterialButton prevButton;
    private MaterialButton nextButton;
    private TextView orderIndicator;
    private MaterialButton solutionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leetcode_problem);
        SafeInsetHelper.applyTopAndBottomInsets(findViewById(R.id.leetcode_root), 16, 12, 16, 12);

        preferences = new AppPreferences(this);
        dataManager = LeetCodeDataManager.getInstance();

        initViews();

        currentOrder = getIntent().getIntExtra(EXTRA_PROBLEM_ORDER, 1);
        String techId = getIntent().getStringExtra(EXTRA_TECHNIQUE_ID);

        if (techId != null && !techId.isEmpty()) {
            resolvedTechniqueId = techId;
            // 1. Lookup in LC_TO_TECHNIQUE
            for (Map.Entry<Integer, String> entry : LC_TO_TECHNIQUE.entrySet()) {
                if (techId.equals(entry.getValue())) {
                    LeetCodeProblemModel found = dataManager.getProblemByNumber(entry.getKey());
                    if (found != null) {
                        currentOrder = found.order;
                    }
                    break;
                }
            }
            // 2. Lookup numeric suffix from "leetcode_X"
            if (techId.startsWith("leetcode_")) {
                try {
                    int num = Integer.parseInt(techId.substring(9));
                    LeetCodeProblemModel found = dataManager.getProblemByNumber(num);
                    if (found != null) {
                        currentOrder = found.order;
                    }
                } catch (Exception ignored) {}
            }
        }

        dataManager.ensureLoaded(this, problems -> runOnUiThread(() -> {
            dataManager.syncUserState(preferences);
            // If techId was provided with an alias not yet found, try to locate by alias
            if (resolvedTechniqueId != null && currentProblem == null) {
                for (LeetCodeProblemModel p : problems) {
                    if (resolvedTechniqueId.equals("leetcode_" + p.lcNumber)) {
                        currentOrder = p.order;
                        break;
                    }
                }
            }
            loadProblemByOrder(currentOrder);
        }));
    }

    private void initViews() {
        backButton = findViewById(R.id.leetcode_back_button);
        backButton.setOnClickListener(v -> finish());

        solveButton = findViewById(R.id.leetcode_detail_solve_btn);
        bookmarkButton = findViewById(R.id.leetcode_detail_bookmark_btn);
        titleView = findViewById(R.id.leetcode_problem_title);
        sourceView = findViewById(R.id.leetcode_source_title);
        difficultyBadge = findViewById(R.id.leetcode_difficulty_badge);
        companyText = findViewById(R.id.leetcode_company_text);
        descriptionText = findViewById(R.id.leetcode_description_text);
        timeComplexityView = findViewById(R.id.leetcode_time_complexity);
        spaceComplexityView = findViewById(R.id.leetcode_space_complexity);

        openWebButton = findViewById(R.id.leetcode_open_web_button);
        prevButton = findViewById(R.id.leetcode_prev_button);
        nextButton = findViewById(R.id.leetcode_next_button);
        orderIndicator = findViewById(R.id.leetcode_order_indicator);
        solutionButton = findViewById(R.id.leetcode_view_solution_button);

        solveButton.setOnClickListener(v -> toggleSolved());
        bookmarkButton.setOnClickListener(v -> toggleBookmark());

        prevButton.setOnClickListener(v -> {
            if (currentOrder > 1) {
                loadProblemByOrder(currentOrder - 1);
            }
        });

        nextButton.setOnClickListener(v -> {
            if (currentOrder < 2000) {
                loadProblemByOrder(currentOrder + 1);
            }
        });

        openWebButton.setOnClickListener(v -> openLeetCodeWeb());
        solutionButton.setOnClickListener(v -> openSolution());
    }

    private void loadProblemByOrder(int order) {
        currentOrder = order;
        currentProblem = dataManager.getProblemByOrder(order);

        if (currentProblem == null) {
            // Fallback: build from intent extras if data not loaded yet
            int lcNum = getIntent().getIntExtra(EXTRA_PROBLEM_NUMBER, 1);
            String title = getIntent().getStringExtra(EXTRA_PROBLEM_TITLE);
            String diff = getIntent().getStringExtra(EXTRA_PROBLEM_DIFFICULTY);
            String pat = getIntent().getStringExtra(EXTRA_PROBLEM_PATTERN);
            String comp = getIntent().getStringExtra(EXTRA_PROBLEM_COMPANIES);
            String slug = getIntent().getStringExtra(EXTRA_PROBLEM_SLUG);
            String url = getIntent().getStringExtra(EXTRA_PROBLEM_URL);

            currentProblem = new LeetCodeProblemModel(
                    order, lcNum, title != null ? title : "Problem #" + lcNum,
                    diff != null ? diff : "Medium", pat != null ? pat : "Core DSA",
                    "Part 1 Roadmap", (order - 1) / 250 + 1,
                    comp != null ? comp : "Google, Amazon", slug != null ? slug : "",
                    url != null ? url : "https://leetcode.com/problems/"
            );
        }

        currentProblem.isSolved = preferences.isLeetCodeSolved(currentProblem.getId());
        currentProblem.isBookmarked = preferences.isLeetCodeBookmarked(currentProblem.getId());

        // Update UI
        titleView.setText("LC #" + currentProblem.lcNumber + ": " + currentProblem.title);
        sourceView.setText("Part " + currentProblem.part + " • " + currentProblem.pattern + " • Order #" + currentProblem.order);
        difficultyBadge.setText(currentProblem.difficulty);
        applyDifficultyColor(difficultyBadge, currentProblem.difficulty);

        updateSolveButtonState();
        updateBookmarkButtonState();

        if (currentProblem.companies != null && !currentProblem.companies.isEmpty()) {
            findViewById(R.id.leetcode_company_card).setVisibility(View.VISIBLE);
            companyText.setText("Frequently asked at: " + currentProblem.companies);
        } else {
            findViewById(R.id.leetcode_company_card).setVisibility(View.GONE);
        }

        orderIndicator.setText("#" + currentProblem.order + " of 2,000");
        prevButton.setEnabled(currentOrder > 1);
        prevButton.setAlpha(currentOrder > 1 ? 1.0f : 0.4f);
        nextButton.setEnabled(currentOrder < 2000);
        nextButton.setAlpha(currentOrder < 2000 ? 1.0f : 0.4f);

        // Resolve authentic problem details and optimal complexities
        LeetCodeProblemDetailRepository.ProblemDetail detail = LeetCodeProblemDetailRepository.getProblemDetail(
                currentProblem.lcNumber,
                currentProblem.title,
                currentProblem.pattern,
                currentProblem.difficulty,
                currentProblem.companies
        );
        resolvedTechniqueId = detail.techniqueId;
        descriptionText.setText(detail.description);
        timeComplexityView.setText(detail.timeComplexity);
        spaceComplexityView.setText(detail.spaceComplexity);
    }

    private void toggleSolved() {
        if (currentProblem == null) return;
        boolean newState = !currentProblem.isSolved;
        currentProblem.isSolved = newState;
        preferences.setLeetCodeSolved(currentProblem.getId(), newState);
        updateSolveButtonState();
        Toast.makeText(this, newState ? "Marked as Solved! 🏆" : "Marked as Unsolved", Toast.LENGTH_SHORT).show();
    }

    private void toggleBookmark() {
        if (currentProblem == null) return;
        boolean newState = !currentProblem.isBookmarked;
        currentProblem.isBookmarked = newState;
        preferences.toggleLeetCodeBookmark(currentProblem.getId());
        updateBookmarkButtonState();
        Toast.makeText(this, newState ? "Added to Bookmarks ⭐" : "Removed from Bookmarks", Toast.LENGTH_SHORT).show();
    }

    private void updateSolveButtonState() {
        if (currentProblem != null && currentProblem.isSolved) {
            solveButton.setImageResource(R.drawable.ic_leetcode_check);
        } else {
            solveButton.setImageResource(R.drawable.ic_leetcode_uncheck);
        }
    }

    private void updateBookmarkButtonState() {
        if (currentProblem != null && currentProblem.isBookmarked) {
            bookmarkButton.setImageResource(R.drawable.ic_leetcode_star);
        } else {
            bookmarkButton.setImageResource(R.drawable.ic_leetcode_star_border);
        }
    }

    private void openLeetCodeWeb() {
        if (currentProblem == null) return;
        String url = currentProblem.url;
        if (url == null || url.trim().isEmpty()) {
            url = "https://leetcode.com/problems/" + currentProblem.slug + "/";
        }
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Could not open browser for LeetCode.", Toast.LENGTH_SHORT).show();
        }
    }

    private void openSolution() {
        preferences.incrementExploredActions();
        String techId = (currentProblem != null) ? ("leetcode_" + currentProblem.lcNumber) : "leetcode_1";
        Intent intent = new Intent(this, TechniqueDetailActivity.class);
        intent.putExtra(TechniqueDetailActivity.EXTRA_TECHNIQUE_ID, techId);
        intent.putExtra(TechniqueDetailActivity.EXTRA_HIDE_DESCRIPTION, true);
        // Pass roadmap order (1-250) so LogicBlockFragment shows the right entry
        intent.putExtra(TechniqueDetailActivity.EXTRA_LOGIC_ORDER, currentOrder);
        // Open the Logic Block tab (page 1) by default for LeetCode Master 2000
        intent.putExtra(TechniqueDetailActivity.EXTRA_INITIAL_PAGE, 1);
        startActivity(intent);
    }

    private void applyDifficultyColor(TextView badge, String difficulty) {
        if (difficulty == null) return;
        String lower = difficulty.trim().toLowerCase();
        if (lower.contains("hard")) {
            badge.setTextColor(Color.parseColor("#FB7185")); // Rose
        } else if (lower.contains("medium")) {
            badge.setTextColor(Color.parseColor("#FBBF24")); // Amber
        } else {
            badge.setTextColor(Color.parseColor("#34D399")); // Emerald / Green
        }
    }
}
