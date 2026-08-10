package com.dsavisualizer.app.ui.fragment;

import android.os.Bundle;
import android.os.SystemClock;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dsavisualizer.app.R;
import com.dsavisualizer.app.data.AppRepository;
import com.dsavisualizer.app.data.AppPreferences;
import com.dsavisualizer.app.data.RemoteSyncManager;
import com.dsavisualizer.app.learning.ActionType;
import com.dsavisualizer.app.learning.AlgorithmLearningPlugin;
import com.dsavisualizer.app.learning.AnswerFeedback;
import com.dsavisualizer.app.learning.EngineController;
import com.dsavisualizer.app.learning.ExplanationEngine;
import com.dsavisualizer.app.learning.LearningEngineRegistry;
import com.dsavisualizer.app.learning.LearningInput;
import com.dsavisualizer.app.learning.LearningQuestion;
import com.dsavisualizer.app.learning.QuestionGenerator;
import com.dsavisualizer.app.learning.QuestionOption;
import com.dsavisualizer.app.learning.StepState;
import com.dsavisualizer.app.learning.TechniquePracticeScenarioFactory;
import com.dsavisualizer.app.learning.TheoryQuestion;
import com.dsavisualizer.app.learning.TheoryQuestionFactory;
import com.dsavisualizer.app.model.TechniqueContent;
import com.dsavisualizer.app.ui.HapticFeedbackHelper;
import com.dsavisualizer.app.ui.viewmodel.TechniqueSessionViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PracticeFragment extends Fragment {

    private static final String ARG_TECHNIQUE_ID = "technique_id";
    private static final int QUESTION_SET_SIZE = 50;
    private enum QuizMode { THEORY, PRACTICAL }

    private String techniqueId = "linear_search";
    private LearningQuestion currentQuestion;
    private TheoryQuestion currentTheoryQuestion;
    private StepState currentStepState;
    private List<StepState> practicalQuestionSet = new ArrayList<>();
    private List<TheoryQuestion> theoryQuestionSet = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private boolean currentQuestionSolved = false;
    private boolean currentQuestionAnsweredOnce = false;
    private boolean currentQuestionMistakeLogged = false;
    private QuizMode currentMode = QuizMode.THEORY;
    private RadioGroup optionsGroup;
    private TextView topicNameView;
    private TextView questionView;
    private TextView feedbackView;
    private TextView summaryView;
    private TextView hintView;
    private TechniqueSessionViewModel sessionViewModel;
    private EngineController engineController;
    private RemoteSyncManager remoteSyncManager;
    private AppPreferences appPreferences;
    private TechniquePracticeScenarioFactory practiceScenarioFactory;
    private TheoryQuestionFactory theoryQuestionFactory;
    private boolean answerSubmittedForCurrentQuestion = false;
    private int questionsAnswered = 0;
    private int correctAnswers = 0;
    private int scorePoints = 0;
    private long sessionStartMs = 0L;
    private MaterialButton checkButton;
    private MaterialButton nextButton;
    private MaterialButton reviewButton;
    private boolean reviewMode = false;
    private int reviewIndex = 0;
    private List<AppPreferences.MistakeReview> mistakeReviews = Collections.emptyList();

    public static PracticeFragment newInstance(String techniqueId) {
        PracticeFragment fragment = new PracticeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TECHNIQUE_ID, techniqueId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_practice, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animateFragmentEntrance(view);
        techniqueId = getArguments() == null ? "linear_search" : getArguments().getString(ARG_TECHNIQUE_ID, "linear_search");

        topicNameView = view.findViewById(R.id.practice_topic_name);
        questionView = view.findViewById(R.id.practice_question);
        optionsGroup = view.findViewById(R.id.practice_options_group);
        feedbackView = view.findViewById(R.id.practice_feedback);
        summaryView = view.findViewById(R.id.practice_state_summary);
        hintView = view.findViewById(R.id.practice_hint);
        checkButton = view.findViewById(R.id.practice_check_button);
        nextButton = view.findViewById(R.id.practice_next_button);
        reviewButton = view.findViewById(R.id.practice_review_button);
        MaterialButtonToggleGroup modeToggle = view.findViewById(R.id.practice_mode_toggle);
        applyThemeColors(view);

        sessionViewModel = new ViewModelProvider(requireActivity()).get(TechniqueSessionViewModel.class);
        remoteSyncManager = RemoteSyncManager.getInstance(requireContext());
        appPreferences = new AppPreferences(requireContext());
        engineController = new EngineController(
            new QuestionGenerator(),
            new ExplanationEngine(),
            new com.dsavisualizer.app.learning.DifficultyManager()
        );
        practiceScenarioFactory = new TechniquePracticeScenarioFactory();
        theoryQuestionFactory = new TheoryQuestionFactory();
        sessionStartMs = SystemClock.elapsedRealtime();
        TechniqueContent content = AppRepository.getContent(techniqueId);
        if (topicNameView != null) {
            topicNameView.setText(content == null ? prettifyTechniqueId(techniqueId) : content.title);
        }

        modeToggle.check(R.id.practice_mode_theory);
        modeToggle.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (!isChecked) {
                return;
            }
            HapticFeedbackHelper.performSelection(group);
            reviewMode = false;
            currentMode = checkedId == R.id.practice_mode_practical ? QuizMode.PRACTICAL : QuizMode.THEORY;
            resetQuestionSet();
            loadQuestion();
        });

        sessionViewModel.getCurrentLearningStep().observe(getViewLifecycleOwner(), stepState -> {
            currentStepState = stepState;
            if (!reviewMode && currentMode == QuizMode.PRACTICAL && practicalQuestionSet.isEmpty()) {
                loadQuestion();
            }
        });

        checkButton.setOnClickListener(v -> checkAnswer());
        nextButton.setOnClickListener(v -> {
            if (reviewMode) {
                showNextMistakeReview();
            } else if (!currentQuestionSolved) {
                feedbackView.setVisibility(View.VISIBLE);
                feedbackView.setText("Solve this question correctly to unlock the next one.");
            } else {
                currentQuestionIndex += 1;
                loadQuestion();
            }
        });
        reviewButton.setOnClickListener(v -> toggleReviewMode());

        loadQuestion();
    }

    private void animateFragmentEntrance(View view) {
        view.setAlpha(0f);
        view.setTranslationY(dpToPx(24));
        view.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(260L)
            .start();
    }

    private void loadQuestion() {
        if (reviewMode) {
            return;
        }
        if (currentQuestionIndex >= QUESTION_SET_SIZE) {
            showCompletedQuestionSet();
            return;
        }
        if (currentMode == QuizMode.THEORY) {
            loadTheoryQuestion();
            return;
        }
        ensurePracticalQuestionSet();
        StepState stepState = practicalQuestionSet.isEmpty() || currentQuestionIndex >= practicalQuestionSet.size()
            ? null
            : practicalQuestionSet.get(currentQuestionIndex);
        if (stepState == null) {
            stepState = buildFallbackStep();
        }
        currentStepState = stepState;
        if (stepState == null) {
            questionView.setText(R.string.practice_start_visualization);
            summaryView.setText(R.string.practice_no_step);
            optionsGroup.removeAllViews();
            hintView.setVisibility(View.GONE);
            feedbackView.setVisibility(View.GONE);
            return;
        }

        engineController.start(Collections.singletonList(stepState), resolvePracticalDifficulty());
        currentQuestion = engineController.getCurrentQuestion();
        if (currentQuestion == null) {
            return;
        }

        currentTheoryQuestion = null;
        summaryView.setText(buildQuestionSummary(stepState, "Practical"));
        questionView.setText(currentQuestion.prompt);
        if (currentQuestion.hint == null || currentQuestion.hint.trim().isEmpty()) {
            hintView.setVisibility(View.GONE);
        } else {
            hintView.setVisibility(View.VISIBLE);
            hintView.setText(getString(R.string.practice_hint_prefix, currentQuestion.hint));
        }

        optionsGroup.removeAllViews();
        for (QuestionOption option : currentQuestion.options) {
            RadioButton radioButton = new RadioButton(requireContext());
            radioButton.setId(View.generateViewId());
            radioButton.setTag(option.actionType);
            radioButton.setText(getString(R.string.practice_radio_option, option.id, option.label));
            styleQuizOption(radioButton);
            optionsGroup.addView(radioButton);
        }
        optionsGroup.clearCheck();
        answerSubmittedForCurrentQuestion = false;
        currentQuestionSolved = false;
        currentQuestionAnsweredOnce = false;
        currentQuestionMistakeLogged = false;
        feedbackView.setVisibility(View.GONE);
        feedbackView.setText("");
        checkButton.setEnabled(true);
        checkButton.setAlpha(1f);
        nextButton.setEnabled(false);
        nextButton.setAlpha(0.45f);
        nextButton.setText(currentQuestionIndex + 1 >= QUESTION_SET_SIZE ? "Finish Set" : "Next Question");
        reviewButton.setText(R.string.practice_review_mistakes);
    }

    private void loadTheoryQuestion() {
        ensureTheoryQuestionSet();
        currentTheoryQuestion = theoryQuestionSet.isEmpty() || currentQuestionIndex >= theoryQuestionSet.size()
            ? null
            : theoryQuestionSet.get(currentQuestionIndex);
        currentQuestion = null;
        currentStepState = null;
        if (currentTheoryQuestion == null) {
            questionView.setText("Theory questions are not available for this topic yet.");
            summaryView.setText("Theory mode is preparing question banks for this topic.");
            optionsGroup.removeAllViews();
            hintView.setVisibility(View.GONE);
            feedbackView.setVisibility(View.GONE);
            checkButton.setEnabled(false);
            checkButton.setAlpha(0.45f);
            nextButton.setEnabled(false);
            nextButton.setAlpha(0.45f);
            return;
        }

        summaryView.setText(buildTheorySummary(currentTheoryQuestion));
        questionView.setText(currentTheoryQuestion.prompt);
        hintView.setVisibility(View.GONE);
        optionsGroup.removeAllViews();
        for (int i = 0; i < currentTheoryQuestion.options.size(); i++) {
            RadioButton radioButton = new RadioButton(requireContext());
            radioButton.setId(View.generateViewId());
            radioButton.setTag(i);
            radioButton.setText(getString(R.string.practice_radio_option, String.valueOf((char) ('A' + i)), currentTheoryQuestion.options.get(i)));
            styleQuizOption(radioButton);
            optionsGroup.addView(radioButton);
        }
        optionsGroup.clearCheck();
        answerSubmittedForCurrentQuestion = false;
        currentQuestionSolved = false;
        currentQuestionAnsweredOnce = false;
        currentQuestionMistakeLogged = false;
        feedbackView.setVisibility(View.GONE);
        feedbackView.setText("");
        checkButton.setEnabled(true);
        checkButton.setAlpha(1f);
        nextButton.setEnabled(false);
        nextButton.setAlpha(0.45f);
        nextButton.setText(currentQuestionIndex + 1 >= QUESTION_SET_SIZE ? "Finish Set" : "Next Question");
        reviewButton.setText(R.string.practice_review_mistakes);
    }

    private void checkAnswer() {
        if (currentQuestionSolved) {
            feedbackView.setVisibility(View.VISIBLE);
            feedbackView.setText("This question is already solved. Move to the next one.");
            return;
        }
        if (currentMode == QuizMode.THEORY) {
            checkTheoryAnswer();
            return;
        }
        int checkedId = optionsGroup.getCheckedRadioButtonId();
        if (checkedId == -1) {
            feedbackView.setVisibility(View.VISIBLE);
            feedbackView.setText(R.string.practice_select_answer);
            return;
        }

        View selectedView = optionsGroup.findViewById(checkedId);
        ActionType selectedAction = selectedView == null || selectedView.getTag() == null
            ? ActionType.COMPLETE
            : (ActionType) selectedView.getTag();

        AnswerFeedback feedback = engineController.submit(selectedAction);
        HapticFeedbackHelper.performConfirm(feedbackView);
        feedbackView.setVisibility(View.VISIBLE);
        feedbackView.setText(feedback.correct
            ? getString(R.string.practice_feedback_correct, feedback.explanation)
            : getString(R.string.practice_feedback_wrong, feedback.explanation));

        if (feedback.correct) {
            currentQuestionSolved = true;
            nextButton.setEnabled(true);
            nextButton.setAlpha(1f);
            checkButton.setEnabled(false);
            checkButton.setAlpha(0.45f);
            if (!answerSubmittedForCurrentQuestion) {
                answerSubmittedForCurrentQuestion = true;
                questionsAnswered += 1;
                correctAnswers += 1;
                if (!currentQuestionAnsweredOnce) {
                    scorePoints += resolveDifficultyWeight();
                }
                remoteSyncManager.savePracticeProgress(
                    techniqueId,
                    resolvePracticeModeApiValue(),
                    scorePoints,
                    true
                );
            }
            return;
        }

        currentQuestionAnsweredOnce = true;
        nextButton.setEnabled(false);
        nextButton.setAlpha(0.45f);
        if (!currentQuestionMistakeLogged) {
            currentQuestionMistakeLogged = true;
            QuestionOption correctOption = currentQuestion == null ? null : currentQuestion.getCorrectOption();
            appPreferences.saveMistakeReview(
                techniqueId,
                currentQuestion == null ? "" : currentQuestion.prompt,
                correctOption == null ? "" : correctOption.label,
                feedback.explanation,
                resolvePracticeModeApiValue()
            );
            remoteSyncManager.savePracticeProgress(
                techniqueId,
                resolvePracticeModeApiValue(),
                scorePoints,
                false
            );
        }
    }

    private void checkTheoryAnswer() {
        int checkedId = optionsGroup.getCheckedRadioButtonId();
        if (checkedId == -1) {
            feedbackView.setVisibility(View.VISIBLE);
            feedbackView.setText(R.string.practice_select_answer);
            return;
        }
        View selectedView = optionsGroup.findViewById(checkedId);
        int selectedIndex = selectedView == null || selectedView.getTag() == null
            ? -1
            : (Integer) selectedView.getTag();
        if (currentTheoryQuestion == null) {
            return;
        }
        boolean correct = selectedIndex == currentTheoryQuestion.correctIndex;
        feedbackView.setVisibility(View.VISIBLE);
        feedbackView.setText((correct ? "Correct\n" : "Wrong\n") + currentTheoryQuestion.explanation);
        if (correct) {
            currentQuestionSolved = true;
            nextButton.setEnabled(true);
            nextButton.setAlpha(1f);
            checkButton.setEnabled(false);
            checkButton.setAlpha(0.45f);
            if (!answerSubmittedForCurrentQuestion) {
                answerSubmittedForCurrentQuestion = true;
                questionsAnswered += 1;
                correctAnswers += 1;
                if (!currentQuestionAnsweredOnce) {
                    scorePoints += 1;
                }
                remoteSyncManager.savePracticeProgress(techniqueId, "theory", scorePoints, true);
            }
            return;
        }
        currentQuestionAnsweredOnce = true;
        nextButton.setEnabled(false);
        nextButton.setAlpha(0.45f);
        if (!currentQuestionMistakeLogged) {
            currentQuestionMistakeLogged = true;
            String correctLabel = currentTheoryQuestion.correctIndex >= 0 && currentTheoryQuestion.correctIndex < currentTheoryQuestion.options.size()
                ? currentTheoryQuestion.options.get(currentTheoryQuestion.correctIndex)
                : "";
            appPreferences.saveMistakeReview(
                techniqueId,
                currentTheoryQuestion.prompt,
                correctLabel,
                currentTheoryQuestion.explanation,
                "theory"
            );
            remoteSyncManager.savePracticeProgress(techniqueId, "theory", scorePoints, false);
        }
    }

    private StepState buildFallbackStep() {
        TechniqueContent content = AppRepository.getContent(techniqueId);
        AlgorithmLearningPlugin plugin = LearningEngineRegistry.get(resolvePluginId());
        if (plugin == null) {
            return null;
        }
        LearningInput input = new LearningInput(
            toList(content == null ? new int[0] : content.sampleInput),
            content == null ? 0 : content.sampleTarget,
            resolveTraversalMode(),
            "",
            ""
        );
        return plugin.getStepState(input, 0);
    }

    private StepState buildIndependentPracticeStep() {
        TechniqueContent content = AppRepository.getContent(techniqueId);
        return practiceScenarioFactory == null ? null : practiceScenarioFactory.generate(techniqueId, content);
    }

    private void ensurePracticalQuestionSet() {
        if (!practicalQuestionSet.isEmpty()) {
            return;
        }
        practicalQuestionSet = buildPracticeQuestionSet();
        currentQuestionIndex = 0;
    }

    private void ensureTheoryQuestionSet() {
        if (!theoryQuestionSet.isEmpty()) {
            return;
        }
        TechniqueContent content = AppRepository.getContent(techniqueId);
        theoryQuestionSet = theoryQuestionFactory == null ? new ArrayList<>() : theoryQuestionFactory.build(techniqueId, content);
        currentQuestionIndex = 0;
    }

    private List<StepState> buildPracticeQuestionSet() {
        List<StepState> items = new ArrayList<>();
        List<String> seenPrompts = new ArrayList<>();
        int maxAttempts = QUESTION_SET_SIZE * 6;
        for (int i = 0; i < maxAttempts && items.size() < QUESTION_SET_SIZE; i++) {
            StepState candidate = buildIndependentPracticeStep();
            if (candidate == null) {
                candidate = buildFallbackStep();
            }
            if (candidate == null) {
                break;
            }
            String promptKey = candidate.decisionContext == null ? candidate.summary : candidate.decisionContext.prompt;
            if (promptKey == null) {
                promptKey = "";
            }
            String uniquenessKey = promptKey + "|" + candidate.correctAction + "|" + candidate.summary;
            if (seenPrompts.contains(uniquenessKey)) {
                continue;
            }
            seenPrompts.add(uniquenessKey);
            items.add(candidate);
        }
        int fillIndex = 0;
        while (!items.isEmpty() && items.size() < QUESTION_SET_SIZE) {
            items.add(items.get(fillIndex % items.size()));
            fillIndex += 1;
        }
        return items;
    }

    private void resetQuestionSet() {
        practicalQuestionSet.clear();
        theoryQuestionSet.clear();
        currentQuestionIndex = 0;
        currentQuestionSolved = false;
        currentQuestionAnsweredOnce = false;
        currentQuestionMistakeLogged = false;
        currentTheoryQuestion = null;
        currentQuestion = null;
        questionsAnswered = 0;
        correctAnswers = 0;
        scorePoints = 0;
    }

    private String buildQuestionSummary(StepState stepState, String modeLabel) {
        StringBuilder builder = new StringBuilder();
        builder.append(modeLabel)
            .append("   |   Question ")
            .append(Math.min(currentQuestionIndex + 1, QUESTION_SET_SIZE))
            .append("/")
            .append(QUESTION_SET_SIZE)
            .append("   |   Solved ")
            .append(correctAnswers)
            .append("/")
            .append(QUESTION_SET_SIZE);
        if (stepState.summary != null && !stepState.summary.isEmpty()) {
            builder.append("\n\n").append(stepState.summary);
        }
        builder.append("\n\nStep-by-step practical mode: answer the current move correctly to unlock the next question.");
        return builder.toString();
    }

    private String buildTheorySummary(TheoryQuestion question) {
        StringBuilder builder = new StringBuilder();
        builder.append("Theory   |   Question ")
            .append(Math.min(currentQuestionIndex + 1, QUESTION_SET_SIZE))
            .append("/")
            .append(QUESTION_SET_SIZE)
            .append("   |   Solved ")
            .append(correctAnswers)
            .append("/")
            .append(QUESTION_SET_SIZE);
        if (question != null && question.explanation != null && !question.explanation.isEmpty()) {
            builder.append("\n\nTopic: ").append(techniqueId.replace('_', ' '));
        }
        return builder.toString();
    }

    private void showCompletedQuestionSet() {
        questionView.setText("Question set complete.");
        summaryView.setText((currentMode == QuizMode.THEORY ? "Theory" : "Practical")
            + " set complete.\n\nSolved: " + correctAnswers + "/" + QUESTION_SET_SIZE + "\nScore: " + scorePoints);
        hintView.setVisibility(View.GONE);
        optionsGroup.removeAllViews();
        feedbackView.setVisibility(View.VISIBLE);
        feedbackView.setText("Great work. Switch between Theory and Practical to start another 50-question set.");
        checkButton.setEnabled(false);
        checkButton.setAlpha(0.45f);
        nextButton.setEnabled(false);
        nextButton.setAlpha(0.45f);
        nextButton.setText("Completed");
    }

    private com.dsavisualizer.app.learning.DifficultyLevel resolvePracticalDifficulty() {
        return com.dsavisualizer.app.learning.DifficultyLevel.GUIDED;
    }

    private int resolveDifficultyWeight() {
        return 1;
    }

    private String resolvePracticeModeApiValue() {
        return "practical";
    }

    private void toggleReviewMode() {
        if (reviewMode) {
            reviewMode = false;
            loadQuestion();
            return;
        }
        mistakeReviews = appPreferences.getMistakeReviews(techniqueId);
        if (mistakeReviews.isEmpty()) {
            feedbackView.setVisibility(View.VISIBLE);
            feedbackView.setText(R.string.practice_no_mistakes);
            return;
        }
        reviewMode = true;
        reviewIndex = 0;
        showMistakeReview(mistakeReviews.get(reviewIndex));
    }

    private void showNextMistakeReview() {
        if (mistakeReviews.isEmpty()) {
            reviewMode = false;
            loadQuestion();
            return;
        }
        reviewIndex += 1;
        if (reviewIndex >= mistakeReviews.size()) {
            reviewMode = false;
            loadQuestion();
            return;
        }
        showMistakeReview(mistakeReviews.get(reviewIndex));
    }

    private void showMistakeReview(AppPreferences.MistakeReview mistakeReview) {
        if (mistakeReview == null) {
            return;
        }
        questionView.setText(getString(R.string.practice_review_question, mistakeReview.prompt));
        summaryView.setText(getString(
            R.string.practice_review_summary,
            reviewIndex + 1,
            mistakeReviews.size(),
            prettifyDifficultyLabel(mistakeReview.difficulty)
        ));
        hintView.setVisibility(View.GONE);
        optionsGroup.removeAllViews();
        feedbackView.setVisibility(View.VISIBLE);
        feedbackView.setText(getString(
            R.string.practice_review_feedback,
            mistakeReview.correctLabel,
            mistakeReview.explanation
        ));
        checkButton.setEnabled(false);
        checkButton.setAlpha(0.45f);
        nextButton.setText(reviewIndex + 1 >= mistakeReviews.size()
            ? R.string.practice_exit_review
            : R.string.practice_next_mistake);
        reviewButton.setText(R.string.practice_exit_review);
    }

    private String prettifyDifficultyLabel(String difficulty) {
        if ("expert".equalsIgnoreCase(difficulty)) {
            return getString(R.string.difficulty_expert);
        }
        if ("level_2".equalsIgnoreCase(difficulty) || "intermediate".equalsIgnoreCase(difficulty)) {
            return getString(R.string.difficulty_level_2);
        }
        return getString(R.string.difficulty_guided);
    }

    private String prettifyTechniqueId(String rawTechniqueId) {
        if (rawTechniqueId == null || rawTechniqueId.trim().isEmpty()) {
            return "Technique";
        }
        String[] parts = rawTechniqueId.replace('-', '_').split("_");
        StringBuilder builder = new StringBuilder();
        for (String part : parts) {
            if (part.isEmpty()) {
                continue;
            }
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append(Character.toUpperCase(part.charAt(0)));
            if (part.length() > 1) {
                builder.append(part.substring(1));
            }
        }
        return builder.length() == 0 ? "Technique" : builder.toString();
    }

    private int dpToPx(int dp) {
        return Math.round(dp * requireContext().getResources().getDisplayMetrics().density);
    }

    private int resolveThemeColor(int attrResId, int fallbackColorResId) {
        TypedValue typedValue = new TypedValue();
        if (requireContext().getTheme().resolveAttribute(attrResId, typedValue, true)) {
            if (typedValue.resourceId != 0) {
                return ContextCompat.getColor(requireContext(), typedValue.resourceId);
            }
            return typedValue.data;
        }
        return ContextCompat.getColor(requireContext(), fallbackColorResId);
    }

    private void applyThemeColors(View rootView) {
        boolean isLightTheme = isLightTheme();
        int primary = resolveThemeColor(android.R.attr.textColorPrimary, R.color.text_primary_bright);
        int secondary = resolveThemeColor(android.R.attr.textColorSecondary, R.color.text_support);
        int pageBackground = isLightTheme ? Color.parseColor("#F8FBFF") : ContextCompat.getColor(requireContext(), R.color.surface_panel);
        int panel = isLightTheme ? Color.parseColor("#F4F8FC") : ContextCompat.getColor(requireContext(), R.color.surface_panel_muted);
        int panelAlt = isLightTheme ? Color.parseColor("#EEF3F9") : ContextCompat.getColor(requireContext(), R.color.surface_panel_alt);
        int panelInfo = isLightTheme ? Color.parseColor("#EAF4FF") : ContextCompat.getColor(requireContext(), R.color.surface_panel_info);
        int panelDeep = isLightTheme ? Color.parseColor("#F6F8FB") : ContextCompat.getColor(requireContext(), R.color.surface_panel_deep);
        if (rootView != null) {
            rootView.setBackgroundColor(pageBackground);
        }

        TextView titleView = rootView.findViewById(R.id.practice_title);
        TextView subtitleView = rootView.findViewById(R.id.practice_subtitle);
        TextView topicView = rootView.findViewById(R.id.practice_topic_name);

        if (titleView != null) {
            titleView.setTextColor(primary);
        }
        if (subtitleView != null) {
            subtitleView.setTextColor(secondary);
        }
        if (topicView != null) {
            topicView.setTextColor(secondary);
            ViewCompat.setBackgroundTintList(topicView, android.content.res.ColorStateList.valueOf(panel));
        }
        if (summaryView != null) {
            summaryView.setTextColor(primary);
            ViewCompat.setBackgroundTintList(summaryView, android.content.res.ColorStateList.valueOf(panelAlt));
        }
        if (hintView != null) {
            hintView.setTextColor(primary);
            ViewCompat.setBackgroundTintList(hintView, android.content.res.ColorStateList.valueOf(panelInfo));
        }
        if (questionView != null) {
            questionView.setTextColor(primary);
            ViewCompat.setBackgroundTintList(questionView, android.content.res.ColorStateList.valueOf(panel));
        }
        if (feedbackView != null) {
            feedbackView.setTextColor(primary);
            ViewCompat.setBackgroundTintList(feedbackView, android.content.res.ColorStateList.valueOf(panelDeep));
        }
        if (optionsGroup != null) {
            for (int i = 0; i < optionsGroup.getChildCount(); i++) {
                View child = optionsGroup.getChildAt(i);
                if (child instanceof RadioButton) {
                    styleQuizOption((RadioButton) child);
                }
            }
        }
        if (checkButton != null) {
            checkButton.setTextColor(isLightTheme ? Color.WHITE : checkButton.getCurrentTextColor());
        }
        if (nextButton != null) {
            nextButton.setTextColor(isLightTheme ? primary : nextButton.getCurrentTextColor());
        }
        if (reviewButton != null) {
            reviewButton.setTextColor(isLightTheme ? primary : reviewButton.getCurrentTextColor());
        }
    }

    private void styleQuizOption(RadioButton radioButton) {
        if (radioButton == null || getContext() == null) {
            return;
        }
        boolean isLightTheme = isLightTheme();
        int textColor = resolveThemeColor(android.R.attr.textColorPrimary, R.color.text_primary_bright);
        int strokeColor = isLightTheme ? Color.parseColor("#D7E3F0") : ContextCompat.getColor(requireContext(), R.color.surface_stroke_muted);
        int fillColor = isLightTheme ? Color.parseColor("#FFFFFF") : ContextCompat.getColor(requireContext(), R.color.surface_panel_deep);
        int tintColor = isLightTheme ? Color.parseColor("#2563EB") : ContextCompat.getColor(requireContext(), R.color.text_hint_blue);

        GradientDrawable background = new GradientDrawable();
        background.setCornerRadius(dpToPx(14));
        background.setColor(fillColor);
        background.setStroke(Math.max(1, dpToPx(1)), strokeColor);

        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.bottomMargin = dpToPx(10);
        radioButton.setLayoutParams(params);
        radioButton.setBackground(background);
        radioButton.setButtonTintList(android.content.res.ColorStateList.valueOf(tintColor));
        radioButton.setTextColor(textColor);
        radioButton.setMinHeight(dpToPx(52));
        radioButton.setPadding(dpToPx(14), dpToPx(14), dpToPx(14), dpToPx(14));
    }

    private boolean isLightTheme() {
        TypedValue typedValue = new TypedValue();
        if (requireContext().getTheme().resolveAttribute(androidx.appcompat.R.attr.isLightTheme, typedValue, true)) {
            return typedValue.data != 0;
        }
        return false;
    }

    private String resolvePluginId() {
        if ("binary_search".equals(techniqueId)) {
            return "binary_search";
        }
        if ("bubble_sort".equals(techniqueId)) {
            return "bubble_sort";
        }
        if ("linked_list_insertion".equals(techniqueId) || "insertion_in_linked_list".equals(techniqueId)) {
            return "linked_list_insertion";
        }
        if ("stack".equals(techniqueId)) {
            return "stack";
        }
        if ("tree_traversal".equals(techniqueId)) {
            return "tree_traversal";
        }
        if ("graph_traversal".equals(techniqueId) || "breadth_first_search".equals(techniqueId)) {
            return "graph_traversal";
        }
        return techniqueId;
    }

    private String resolveTraversalMode() {
        if (sessionViewModel == null) {
            return "";
        }
        if ("tree_traversal".equals(techniqueId) && sessionViewModel.getTreeMode().getValue() != null) {
            return sessionViewModel.getTreeMode().getValue();
        }
        if (sessionViewModel.getGraphMode().getValue() != null) {
            return sessionViewModel.getGraphMode().getValue();
        }
        return "";
    }

    private List<Integer> toList(int[] values) {
        List<Integer> list = new ArrayList<>();
        for (int value : values) {
            list.add(value);
        }
        return list;
    }

    @Override
    public void onDestroyView() {
        if (remoteSyncManager != null && questionsAnswered > 0) {
            long durationMs = Math.max(1000L, SystemClock.elapsedRealtime() - sessionStartMs);
            remoteSyncManager.savePracticeSession(techniqueId, currentMode == QuizMode.THEORY ? "theory" : resolvePracticeModeApiValue(), scorePoints, durationMs);
        }
        super.onDestroyView();
    }
}
