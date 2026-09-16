package com.dsavisualizer.app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.graphics.Rect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dsavisualizer.app.ui.fragment.AlgorithmFragment;
import com.dsavisualizer.app.ui.fragment.CodeFragment;
import com.dsavisualizer.app.ui.fragment.PracticeFragment;
import com.dsavisualizer.app.ui.fragment.VisualizationFragment;
import com.dsavisualizer.app.ui.SafeInsetHelper;
import com.dsavisualizer.app.ui.HapticFeedbackHelper;
import com.dsavisualizer.app.data.RemoteSyncManager;
import com.dsavisualizer.app.data.AppPreferences;
import com.dsavisualizer.app.ui.walkthrough.WalkthroughManager;
import com.dsavisualizer.app.ui.walkthrough.WalkthroughOverlayView;
import com.dsavisualizer.app.ui.model.TechniqueDetailUiModel;
import com.dsavisualizer.app.ui.viewmodel.TechniqueDetailViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.snackbar.Snackbar;

public class TechniqueDetailActivity extends AppCompatActivity {

    public static final String EXTRA_TECHNIQUE_ID = "technique_id";
    private static final String STATE_CURRENT_PAGE = "current_page";
    private static final String[] DETAIL_PAGE_TAGS = new String[] {
        "detail_page_0",
        "detail_page_1",
        "detail_page_2",
        "detail_page_3"
    };
    private View detailHeaderSection;
    private View detailFragmentContainer;
    private MaterialButtonToggleGroup flowToggle;
    private boolean studyModeEnabled = false;
    private int currentPage = 0;
    private String techniqueId = "linear_search";
    private AppPreferences appPreferences;
    private WalkthroughOverlayView walkthroughOverlay;
    private final Handler walkthroughHandler = new Handler(Looper.getMainLooper());
    private final Rect walkthroughTempRect = new Rect();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technique_detail);
        boolean landscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        int sidePadding = landscape ? 2 : 8;
        int verticalPadding = landscape ? 2 : 16;
        SafeInsetHelper.applyTopAndBottomInsets(findViewById(R.id.detail_root), sidePadding, verticalPadding, sidePadding, verticalPadding);

        String requestedTechniqueId = getIntent().getStringExtra(EXTRA_TECHNIQUE_ID);
        String resolvedTechniqueId = requestedTechniqueId == null ? "linear_search" : requestedTechniqueId;
        techniqueId = resolvedTechniqueId;
        appPreferences = new AppPreferences(this);
        appPreferences.markVisited(resolvedTechniqueId);
        appPreferences.addRecent(resolvedTechniqueId);
        RemoteSyncManager.getInstance(this).syncTechniqueOpened(resolvedTechniqueId);
        TechniqueDetailViewModel detailViewModel = new ViewModelProvider(this).get(TechniqueDetailViewModel.class);

        TextView titleView = findViewById(R.id.detail_title);
        TextView definitionView = findViewById(R.id.detail_definition);
        TextView exampleView = findViewById(R.id.detail_real_life_example);
        TextView sourceView = findViewById(R.id.detail_source);
        TextView versionView = findViewById(R.id.detail_version);
        TextView bestView = findViewById(R.id.detail_best);
        TextView averageView = findViewById(R.id.detail_average);
        TextView worstView = findViewById(R.id.detail_worst);
        TextView spaceView = findViewById(R.id.detail_space);
        TextView flowLabelView = findViewById(R.id.detail_flow_label);
        flowToggle = findViewById(R.id.detail_flow_toggle);
        detailHeaderSection = findViewById(R.id.detail_header_section);
        detailFragmentContainer = findViewById(R.id.detail_fragment_container);
        applyHeaderThemeColors(
            titleView,
            definitionView,
            exampleView,
            sourceView,
            versionView,
            bestView,
            averageView,
            worstView,
            spaceView,
            flowLabelView
        );

        detailViewModel.getDetailUiModel().observe(this, uiModel -> bindDetailHeader(
            uiModel,
            titleView,
            definitionView,
            exampleView,
            sourceView,
            versionView,
            bestView,
            averageView,
            worstView,
            spaceView
        ));
        detailViewModel.load(resolvedTechniqueId);

        flowToggle.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (!isChecked) {
                return;
            }
            HapticFeedbackHelper.performSelection(group);
            if (checkedId == R.id.detail_flow_understand) {
                showPage(0);
            } else if (checkedId == R.id.detail_flow_visualize) {
                showPage(1);
            } else if (checkedId == R.id.detail_flow_practice) {
                showPage(2);
            } else if (checkedId == R.id.detail_flow_code) {
                showPage(3);
            }
        });
        boolean isLeetCode = resolvedTechniqueId.startsWith("leetcode_");
        View practiceBtn = findViewById(R.id.detail_flow_practice);
        if (practiceBtn != null && isLeetCode) {
            practiceBtn.setVisibility(View.GONE);
        }
        if (isLeetCode && flowLabelView != null) {
            flowLabelView.setText("Interview Solution Flow");
        }

        currentPage = resolveInitialPage(savedInstanceState);
        checkFlowToggle(flowToggle, currentPage);
        showPage(currentPage);
        applyHeaderVisibility();
        findViewById(R.id.detail_root).post(this::maybeStartWalkthrough);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_CURRENT_PAGE, currentPage);
    }

    @Override
    public void onBackPressed() {
        if (currentPage > 0) {
            int targetPage = currentPage - 1;
            boolean isLeetCode = techniqueId != null && techniqueId.startsWith("leetcode_");
            if (isLeetCode && targetPage == 2) {
                targetPage = 1;
            }
            checkFlowToggle(flowToggle, targetPage);
            showPage(targetPage);
            return;
        }
        super.onBackPressed();
    }

    public void setStudyModeEnabled(boolean enabled) {
        studyModeEnabled = enabled;
        applyHeaderVisibility();
    }

    private void applyHeaderVisibility() {
        boolean showHeader = currentPage == 0 && !studyModeEnabled;
        if (detailHeaderSection != null) {
            detailHeaderSection.setVisibility(showHeader ? View.VISIBLE : View.GONE);
        }
        if (detailFragmentContainer != null) {
            android.view.ViewGroup.MarginLayoutParams params =
                (android.view.ViewGroup.MarginLayoutParams) detailFragmentContainer.getLayoutParams();
            params.topMargin = showHeader ? dpToPx(12) : 0;
            detailFragmentContainer.setLayoutParams(params);
        }
    }

    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }

    private void bindDetailHeader(
        TechniqueDetailUiModel uiModel,
        TextView titleView,
        TextView definitionView,
        TextView exampleView,
        TextView sourceView,
        TextView versionView,
        TextView bestView,
        TextView averageView,
        TextView worstView,
        TextView spaceView
    ) {
        if (uiModel == null) {
            return;
        }
        titleView.setText(uiModel.title);
        definitionView.setText(uiModel.definition);
        exampleView.setText(uiModel.example);
        sourceView.setText(uiModel.sourceLabel);
        sourceView.setVisibility(uiModel.sourceLabel == null || uiModel.sourceLabel.trim().isEmpty()
            ? android.view.View.GONE
            : android.view.View.VISIBLE);
        versionView.setText(uiModel.versionLabel);
        versionView.setVisibility(uiModel.versionLabel == null || uiModel.versionLabel.trim().isEmpty()
            ? android.view.View.GONE
            : android.view.View.VISIBLE);
        bestView.setText(uiModel.bestTimeLabel);
        averageView.setText(uiModel.averageTimeLabel);
        worstView.setText(uiModel.worstTimeLabel);
        spaceView.setText(uiModel.spaceLabel);
    }

    private void showPage(int position) {
        if (position != 1 && studyModeEnabled) {
            setStudyModeEnabled(false);
        }
        Fragment existingFragment = getSupportFragmentManager().findFragmentById(R.id.detail_fragment_container);
        if (position == currentPage && fragmentMatchesPage(existingFragment, position)) {
            updateFlowToggleVisuals();
            applyHeaderVisibility();
            return;
        }
        int previousPage = currentPage;
        currentPage = position;
        Fragment fragment = createFragmentForPage(position);
        getSupportFragmentManager()
            .beginTransaction()
            .setCustomAnimations(
                resolveEnterAnimation(position),
                resolveExitAnimation(previousPage)
            )
            .replace(R.id.detail_fragment_container, fragment, DETAIL_PAGE_TAGS[position])
            .commitAllowingStateLoss();
        applyHeaderVisibility();
        updateFlowToggleVisuals();
        maybeShowMiniTip();
    }

    private Fragment createFragmentForPage(int position) {
        if (position == 1) {
            return VisualizationFragment.newInstance(techniqueId);
        }
        if (position == 2) {
            return PracticeFragment.newInstance(techniqueId);
        }
        if (position == 3) {
            return CodeFragment.newInstance(techniqueId);
        }
        return AlgorithmFragment.newInstance(techniqueId);
    }

    private void checkFlowToggle(MaterialButtonToggleGroup flowToggle, int position) {
        if (position == 1) {
            flowToggle.check(R.id.detail_flow_visualize);
        } else if (position == 2) {
            flowToggle.check(R.id.detail_flow_practice);
        } else if (position == 3) {
            flowToggle.check(R.id.detail_flow_code);
        } else {
            flowToggle.check(R.id.detail_flow_understand);
        }
    }

    private int resolveInitialPage(Bundle savedInstanceState) {
        Fragment restoredFragment = getSupportFragmentManager().findFragmentById(R.id.detail_fragment_container);
        int restoredPage = resolvePageForFragment(restoredFragment);
        if (restoredPage >= 0) {
            return restoredPage;
        }
        for (int index = 0; index < DETAIL_PAGE_TAGS.length; index++) {
            Fragment taggedFragment = getSupportFragmentManager().findFragmentByTag(DETAIL_PAGE_TAGS[index]);
            int taggedPage = resolvePageForFragment(taggedFragment);
            if (taggedPage >= 0) {
                return taggedPage;
            }
        }
        if (savedInstanceState != null) {
            return savedInstanceState.getInt(STATE_CURRENT_PAGE, 0);
        }
        return 0;
    }

    private boolean fragmentMatchesPage(Fragment fragment, int page) {
        return resolvePageForFragment(fragment) == page;
    }

    private int resolvePageForFragment(Fragment fragment) {
        if (fragment instanceof VisualizationFragment) {
            return 1;
        }
        if (fragment instanceof PracticeFragment) {
            return 2;
        }
        if (fragment instanceof CodeFragment) {
            return 3;
        }
        if (fragment instanceof AlgorithmFragment) {
            return 0;
        }
        return -1;
    }

    private int resolveEnterAnimation(int position) {
        if (position == 1) {
            return R.anim.fragment_visualize_enter;
        }
        if (position == 2) {
            return R.anim.fragment_quiz_enter;
        }
        if (position == 3) {
            return R.anim.fragment_code_enter;
        }
        return R.anim.fragment_algorithm_enter;
    }

    private int resolveExitAnimation(int position) {
        if (position == 1) {
            return R.anim.fragment_visualize_exit;
        }
        if (position == 2) {
            return R.anim.fragment_quiz_exit;
        }
        if (position == 3) {
            return R.anim.fragment_code_exit;
        }
        return R.anim.fragment_algorithm_exit;
    }

    private void updateFlowToggleVisuals() {
        if (flowToggle == null) {
            return;
        }
        int activeBackground;
        int activeStroke;
        if (currentPage == 1) {
            activeBackground = ContextCompat.getColor(this, R.color.surface_toggle_visual_active);
            activeStroke = ContextCompat.getColor(this, R.color.surface_toggle_visual_stroke);
        } else if (currentPage == 2) {
            activeBackground = ContextCompat.getColor(this, R.color.surface_toggle_quiz_active);
            activeStroke = ContextCompat.getColor(this, R.color.surface_toggle_quiz_stroke);
        } else if (currentPage == 3) {
            activeBackground = ContextCompat.getColor(this, R.color.surface_toggle_code_active);
            activeStroke = ContextCompat.getColor(this, R.color.surface_toggle_code_stroke);
        } else {
            activeBackground = ContextCompat.getColor(this, R.color.surface_toggle_algorithm_active);
            activeStroke = ContextCompat.getColor(this, R.color.surface_toggle_algorithm_stroke);
        }

        int inactiveBackground = ContextCompat.getColor(this, R.color.surface_toggle_inactive);
        int inactiveStroke = ContextCompat.getColor(this, R.color.surface_toggle_inactive_stroke);
        int activeText = resolveThemeColor(android.R.attr.textColorPrimary, R.color.text_primary_bright);
        int inactiveText = resolveThemeColor(android.R.attr.textColorHint, R.color.text_muted);

        int[] buttonIds = new int[] {
            R.id.detail_flow_understand,
            R.id.detail_flow_visualize,
            R.id.detail_flow_practice,
            R.id.detail_flow_code
        };

        int checkedId = flowToggle.getCheckedButtonId();
        for (int buttonId : buttonIds) {
            MaterialButton button = flowToggle.findViewById(buttonId);
            if (button == null) {
                continue;
            }
            boolean active = buttonId == checkedId;
            button.setBackgroundTintList(ColorStateList.valueOf(active ? activeBackground : inactiveBackground));
            button.setStrokeColor(ColorStateList.valueOf(active ? activeStroke : inactiveStroke));
            button.setTextColor(active ? activeText : inactiveText);
        }
    }

    private int resolveThemeColor(int attrResId, int fallbackColorResId) {
        TypedValue typedValue = new TypedValue();
        if (getTheme().resolveAttribute(attrResId, typedValue, true)) {
            if (typedValue.resourceId != 0) {
                return ContextCompat.getColor(this, typedValue.resourceId);
            }
            return typedValue.data;
        }
        return ContextCompat.getColor(this, fallbackColorResId);
    }

    private void applyHeaderThemeColors(
        TextView titleView,
        TextView definitionView,
        TextView exampleView,
        TextView sourceView,
        TextView versionView,
        TextView bestView,
        TextView averageView,
        TextView worstView,
        TextView spaceView,
        TextView flowLabelView
    ) {
        if (titleView != null) {
            titleView.setTextColor(resolveThemeColor(android.R.attr.textColorPrimary, R.color.text_primary_bright));
        }
        if (definitionView != null) {
            definitionView.setTextColor(resolveThemeColor(android.R.attr.textColorSecondary, R.color.text_secondary));
        }
        if (exampleView != null) {
            exampleView.setTextColor(resolveThemeColor(android.R.attr.textColorSecondary, R.color.text_muted_soft));
        }
        if (sourceView != null) {
            sourceView.setTextColor(resolveThemeColor(android.R.attr.textColorLink, R.color.text_hint_blue));
        }
        if (versionView != null) {
            versionView.setTextColor(resolveThemeColor(android.R.attr.textColorTertiary, R.color.text_muted));
        }
        if (flowLabelView != null) {
            flowLabelView.setTextColor(resolveThemeColor(android.R.attr.textColorSecondary, R.color.text_secondary));
        }
        if (isLightTheme()) {
            if (bestView != null) {
                bestView.setTextColor(android.graphics.Color.parseColor("#15803D"));
            }
            if (averageView != null) {
                averageView.setTextColor(android.graphics.Color.parseColor("#B45309"));
            }
            if (worstView != null) {
                worstView.setTextColor(android.graphics.Color.parseColor("#BE123C"));
            }
            if (spaceView != null) {
                spaceView.setTextColor(android.graphics.Color.parseColor("#0369A1"));
            }
        }
    }

    private boolean isLightTheme() {
        TypedValue typedValue = new TypedValue();
        if (getTheme().resolveAttribute(androidx.appcompat.R.attr.isLightTheme, typedValue, true)) {
            return typedValue.data != 0;
        }
        return false;
    }

    private void maybeStartWalkthrough() {
        if (appPreferences == null
            || !WalkthroughManager.STAGE_DETAIL.equals(appPreferences.getWalkthroughStage())
            || appPreferences.isWalkthroughPaused()) {
            maybeShowMiniTip();
            return;
        }
        appPreferences.resumeWalkthrough();
        showWalkthroughStep(
            findViewById(R.id.detail_flow_understand),
            R.string.walkthrough_algorithms_title,
            R.string.walkthrough_algorithms_body,
            new WalkthroughOverlayView.Listener() {
                @Override
                public void onNext() {
                    checkFlowToggle(flowToggle, 0);
                    showPage(0);
                    walkthroughHandler.postDelayed(TechniqueDetailActivity.this::showIterativeWalkthroughStep, 260L);
                }

                @Override
                public void onSkip() {
                    pauseWalkthrough();
                }
            }
        );
    }

    private void showIterativeWalkthroughStep() {
        View iterativeButton = findFragmentWalkthroughView(R.id.content_style_iterative);
        if (iterativeButton == null) {
            showCodeWalkthroughStep();
            return;
        }
        showWalkthroughStep(
            iterativeButton,
            R.string.walkthrough_iterative_title,
            R.string.walkthrough_iterative_body,
            new WalkthroughOverlayView.Listener() {
                @Override
                public void onNext() {
                    iterativeButton.performClick();
                    showCodeWalkthroughStep();
                }

                @Override
                public void onSkip() {
                    pauseWalkthrough();
                }
            }
        );
    }

    private void showCodeWalkthroughStep() {
        checkFlowToggle(flowToggle, 3);
        showPage(3);
        showFragmentWalkthroughStep(
            new int[] { R.id.content_style_toggle, R.id.content_topic_name, R.id.content_style_iterative },
            0,
            R.string.walkthrough_code_title,
            R.string.walkthrough_code_body,
            new WalkthroughOverlayView.Listener() {
                @Override
                public void onNext() {
                    showPracticeWalkthroughStep();
                }

                @Override
                public void onSkip() {
                    pauseWalkthrough();
                }
            }
        );
    }

    private void showPracticeWalkthroughStep() {
        checkFlowToggle(flowToggle, 2);
        showPage(2);
        showFragmentWalkthroughStep(
            new int[] { R.id.practice_mode_toggle, R.id.practice_question, R.id.practice_state_summary },
            0,
            R.string.walkthrough_practice_title,
            R.string.walkthrough_practice_body,
            new WalkthroughOverlayView.Listener() {
                @Override
                public void onNext() {
                    showVisualizationWalkthroughStep();
                }

                @Override
                public void onSkip() {
                    pauseWalkthrough();
                }
            }
        );
    }

    private void showVisualizationWalkthroughStep() {
        checkFlowToggle(flowToggle, 1);
        showPage(1);
        showFragmentWalkthroughStep(
            new int[] { R.id.visual_controls_group, R.id.visual_start_pause_button, R.id.visual_topic_name },
            0,
            R.string.walkthrough_visual_title,
            R.string.walkthrough_visual_body,
            new WalkthroughOverlayView.Listener() {
                @Override
                public void onNext() {
                    appPreferences.setWalkthroughStage(
                        WalkthroughManager.classroomStageForRole(appPreferences.getAuthRole())
                    );
                    dismissWalkthrough();
                }

                @Override
                public void onSkip() {
                    pauseWalkthrough();
                }
            }
        );
    }

    private void maybeShowMiniTip() {
        if (appPreferences == null || !appPreferences.isWalkthroughComplete()) {
            return;
        }
        if (currentPage == 1 && appPreferences.shouldShowTip(WalkthroughManager.TIP_LANDSCAPE)) {
            appPreferences.markTipSeen(WalkthroughManager.TIP_LANDSCAPE);
            Snackbar.make(detailFragmentContainer, R.string.walkthrough_tip_landscape, Snackbar.LENGTH_LONG).show();
            return;
        }
        if (currentPage == 2 && appPreferences.shouldShowTip(WalkthroughManager.TIP_PRACTICE)) {
            appPreferences.markTipSeen(WalkthroughManager.TIP_PRACTICE);
            Snackbar.make(detailFragmentContainer, R.string.walkthrough_tip_practice, Snackbar.LENGTH_LONG).show();
        }
    }

    private void showWalkthroughStep(
        View target,
        int titleRes,
        int bodyRes,
        WalkthroughOverlayView.Listener listener
    ) {
        ensureWalkthroughOverlay();
        walkthroughOverlay.showForTarget(
            target,
            getString(titleRes),
            getString(bodyRes),
            getString(R.string.walkthrough_done),
            false,
            listener
        );
    }

    private void ensureWalkthroughOverlay() {
        if (walkthroughOverlay != null) {
            return;
        }
        walkthroughOverlay = new WalkthroughOverlayView(this);
        FrameLayout root = findViewById(android.R.id.content);
        root.addView(walkthroughOverlay, new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        ));
    }

    private void dismissWalkthrough() {
        walkthroughHandler.removeCallbacksAndMessages(null);
        if (walkthroughOverlay != null) {
            walkthroughOverlay.clearStep();
        }
    }

    private void pauseWalkthrough() {
        appPreferences.pauseWalkthrough();
        dismissWalkthrough();
    }

    private View findFragmentWalkthroughView(int viewId) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("detail_page_" + currentPage);
        View fragmentView = currentFragment == null ? null : currentFragment.getView();
        return fragmentView == null ? null : fragmentView.findViewById(viewId);
    }

    private void showFragmentWalkthroughStep(
        int[] candidateIds,
        int attempt,
        int titleRes,
        int bodyRes,
        WalkthroughOverlayView.Listener listener
    ) {
        View target = findFirstVisibleWalkthroughTarget(candidateIds);
        if (target != null) {
            showWalkthroughStep(target, titleRes, bodyRes, listener);
            return;
        }
        if (attempt >= 8) {
            pauseWalkthrough();
            return;
        }
        walkthroughHandler.postDelayed(
            () -> showFragmentWalkthroughStep(candidateIds, attempt + 1, titleRes, bodyRes, listener),
            140L
        );
    }

    private View findFirstVisibleWalkthroughTarget(int[] candidateIds) {
        if (candidateIds == null) {
            return null;
        }
        for (int candidateId : candidateIds) {
            View fragmentTarget = findFragmentWalkthroughView(candidateId);
            if (isWalkthroughTargetVisible(fragmentTarget)) {
                return fragmentTarget;
            }
            View activityTarget = findViewById(candidateId);
            if (isWalkthroughTargetVisible(activityTarget)) {
                return activityTarget;
            }
        }
        return null;
    }

    private boolean isWalkthroughTargetVisible(View target) {
        return target != null
            && target.getVisibility() == View.VISIBLE
            && target.getWidth() > 0
            && target.getHeight() > 0
            && target.getGlobalVisibleRect(walkthroughTempRect);
    }

    public void promptPracticeAfterVisualization() {
        if (currentPage != 1 || detailFragmentContainer == null) {
            return;
        }
        Snackbar.make(detailFragmentContainer, R.string.walkthrough_prompt_try_quiz, Snackbar.LENGTH_LONG)
            .setAction(R.string.walkthrough_prompt_action_quiz, v -> {
                checkFlowToggle(flowToggle, 2);
                showPage(2);
            })
            .show();
    }
}
