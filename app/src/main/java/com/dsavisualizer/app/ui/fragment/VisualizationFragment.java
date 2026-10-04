package com.dsavisualizer.app.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.util.Log;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.InputType;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.ProgressBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.ColorStateList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dsavisualizer.app.TechniqueDetailActivity;
import com.dsavisualizer.app.R;
import com.dsavisualizer.app.TechniqueDetailActivity;
import com.dsavisualizer.app.data.AppRepository;
import com.dsavisualizer.app.learning.ActionType;
import com.dsavisualizer.app.learning.DecisionContext;
import com.dsavisualizer.app.learning.DecisionType;
import com.dsavisualizer.app.learning.OperationType;
import com.dsavisualizer.app.learning.StepPointer;
import com.dsavisualizer.app.learning.StepState;
import com.dsavisualizer.app.learning.StructureType;
import com.dsavisualizer.app.model.TechniqueContent;
import com.dsavisualizer.app.ui.view.TechniqueVisualizerView;
import com.dsavisualizer.app.ui.viewmodel.TechniqueSessionViewModel;
import com.dsavisualizer.app.ui.visualization.GraphVisualizationController;
import com.dsavisualizer.app.ui.visualization.SearchVisualizationController;
import com.dsavisualizer.app.ui.visualization.SortVisualizationEngine;
import com.dsavisualizer.app.ui.visualization.StructureVisualizationController;
import com.dsavisualizer.app.ui.visualization.TreeVisualizationController;
import com.dsavisualizer.app.ui.visualization.VisualizationTechniqueRegistry;
import com.dsavisualizer.app.ui.visualization.playback.PlaybackController;
import com.dsavisualizer.app.ui.visualization.playback.PlaybackState;
import com.dsavisualizer.app.ui.visualization.runtime.GraphStepEngine;
import com.dsavisualizer.app.ui.visualization.runtime.AlgorithmStepEngine;
import com.dsavisualizer.app.ui.visualization.runtime.SearchStepEngine;
import com.dsavisualizer.app.ui.visualization.runtime.SortStepEngine;
import com.dsavisualizer.app.ui.visualization.runtime.StepEngine;
import com.dsavisualizer.app.ui.visualization.runtime.StructureStepEngine;
import com.dsavisualizer.app.ui.visualization.runtime.TreeStepEngine;
import com.dsavisualizer.app.ui.visualization.runtime.AvlTreeStepEngine;
import com.dsavisualizer.app.ui.visualization.runtime.DijkstraStepEngine;
import com.dsavisualizer.app.ui.visualization.runtime.HeapOperationStepEngine;
import com.dsavisualizer.app.ui.visualization.runtime.HuffmanStepEngine;
import com.dsavisualizer.app.ui.visualization.runtime.MatrixAlgorithmEngine;
import com.dsavisualizer.app.ui.visualization.runtime.TrieStepEngine;
import com.dsavisualizer.app.ui.visualization.runtime.UnionFindStepEngine;
import com.dsavisualizer.app.ui.visualization.runtime.VisualizationRuntimeHost;
import com.dsavisualizer.app.ui.visualization.playback.SnapshotTimeline;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Visualization tab with shared controls for all currently scaffolded techniques.
 * Array-based topics accept user input and random defaults.
 */
public class VisualizationFragment extends Fragment implements VisualizationRuntimeHost {
    private static final String TAG = "VisualizationFragment";

    private static final String ARG_TECHNIQUE_ID = "technique_id";
    private static final String UI_PREFS = "visualization_ui";
    private static final String PREF_CONTROLS_HINT_DISMISSED = "controls_hint_dismissed";
    private static final String STATE_WORKING_VALUES = "visual_state_working_values";
    private static final String STATE_EXPRESSION_INPUT = "visual_state_expression_input";
    private static final String STATE_TARGET = "visual_state_target";
    private static final String STATE_STEP_INDEX = "visual_state_step_index";
    private static final String STATE_AUX_INDEX = "visual_state_aux_index";
    private static final String STATE_FOUND_INDEX = "visual_state_found_index";
    private static final String STATE_STATUS_TEXT = "visual_state_status_text";
    private static final String STATE_EXPLANATION_TEXT = "visual_state_explanation_text";
    private static final String STATE_STRUCTURE_LIMIT = "visual_state_structure_limit";
    private static final String STATE_PLAYBACK_STEP_INDEX = "visual_state_playback_step_index";
    private static final String STATE_PLAYBACK_SPEED = "visual_state_playback_speed";
    private static final String STATE_PLAYBACK_AT_END = "visual_state_playback_at_end";

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Random random = new Random();

    private TechniqueVisualizerView visualizerView;
    private HorizontalScrollView visualizerScrollView;
    private TextView statusText;
    private TextView explanationText;
    private LinearLayout studyHeader;
    private TextView studyTitleText;
    private TextView studyMessageText;
    private TextView livePseudocodeText;
    private TextView studyChipPrimaryText;
    private TextView studyChipSecondaryText;
    private TextView studyChipTertiaryText;
    private LinearLayout traversalGroup;
    private TextView traversalLabel;
    private Spinner traversalSpinner;
    private MaterialButton playPauseButton;
    private MaterialButton focusModeButton;
    private MaterialButton studyExitButton;
    private MaterialButton previousButton;
    private MaterialButton replayButton;
    private MaterialButton nextButton;
    private MaterialButton studyPreviousButton;
    private MaterialButton studyReplayButton;
    private MaterialButton studyPlayButton;
    private MaterialButton studyNextButton;
    private LinearLayout inputRow;
    private LinearLayout inputActionsRow;
    private LinearLayout compactLimitGroup;
    private MaterialButton editInputButton;
    private LinearLayout controlsGroup;
    private LinearLayout controlsStrip;
    private LinearLayout studyControlsStrip;
    private LinearLayout controlsChipHost;
    private NestedScrollView contentScrollView;
    private HorizontalScrollView timelineScrollView;
    private LinearLayout timelineRow;
    private TextView timelineLabel;
    private TextView topicNameText;
    private TextView nowDoingLabel;
    private TextView progressLabel;
    private TextView controlsHintLabel;
    private TextView stepChipView;
    private TextView completedBadgeView;
    private TextView progressBadge;
    private ProgressBar progressBar;
    private View progressTrack;
    private View progressHandle;
    private View controlsProgressLine;
    private View stepDetailsCard;
    private TextView stepDetailsTitle;
    private TextView stepDetailsBody;
    private LinearLayout pointerLegendRow;
    private TextView pointerLegendTitle;
    private TextView pointerLegendBody;
    private LinearLayout formulaRow;
    private TextView formulaTitle;
    private TextView formulaBody;
    private MaterialButton formulaToggleButton;
    private LinearLayout statePanel;
    private TextView stateTitle;
    private TextView stateBody;
    private LinearLayout adjacencyPanel;
    private TextView adjacencyTitle;
    private TextView adjacencyBody;
    private View completionBanner;
    private TextView completionBannerTitle;
    private TextView completionBannerBody;
    private int visualizerDefaultHeight = 0;
    private TextInputLayout valuesInputLayout;
    private TextInputLayout targetInputLayout;
    private TextInputLayout compactLimitInputLayout;
    private TextInputLayout operationInputLayout;
    private EditText valuesInput;
    private EditText targetInput;
    private EditText compactLimitInput;
    private EditText operationInput;
    private LinearLayout operationsGroup;
    private LinearLayout operationPrimaryRow;
    private LinearLayout operationSecondaryRow;
    private MaterialButton operationPrimaryButton;
    private MaterialButton operationSecondaryButton;
    private MaterialButton operationTertiaryButton;
    private MaterialButton operationFourthButton;
    private TextView operationsTitle;
    private LinearLayout studyActionsHost;
    private LinearLayout overlayActionsHost;
    private ViewGroup scrollContentLayout;
    private int originalOperationsIndex = -1;
    private TechniqueSessionViewModel sessionViewModel;
    private StepEngine stepEngine;
    private PlaybackController playbackController;
    private android.view.GestureDetector stepGestureDetector;

    private String techniqueId = "linear_search";
    private String techniqueTitle = "Technique";
    private String currentTraversalMode = "";
    private String expressionInput = "";
    private int[] workingValues = new int[0];
    private int structureLimit = 6;
    private int target;
    private boolean running = false;
    private boolean paused = false;
    private boolean playbackComplete = false;
    private long delayMs = 850L;
    private final Runnable fastForwardRunnable = new Runnable() {
        @Override
        public void run() {
            if (!fastSteppingForward) {
                return;
            }
            stepForwardOnce();
            handler.postDelayed(this, 150L);
        }
    };
    private final Runnable fastBackwardRunnable = new Runnable() {
        @Override
        public void run() {
            if (!fastSteppingBackward) {
                return;
            }
            stepBackwardOnce();
            handler.postDelayed(this, 150L);
        }
    };
    private int stepIndex = -1;
    private int auxIndex = -1;
    private int foundIndex = -1;
    private int binaryPhase = 0;
    private int binaryMidIndex = -1;
    private int[] traversalOrder = new int[0];
    private int[] mergeLeftValues = new int[0];
    private int[] mergeRightValues = new int[0];
    private int[] mergeOutputValues = new int[0];
    private int[] mergeWorkingValues = new int[0];
    private int mergeLeftCursor = 0;
    private int mergeRightCursor = 0;
    private int mergeWriteCursor = -1;
    private int mergeRunSize = 1;
    private int mergePassStart = 0;
    private int radixDivisor = 1;
    private int radixMaxValue = 0;
    private int quickLow = -1;
    private int quickHigh = -1;
    private int[] quickStackLow = new int[0];
    private int[] quickStackHigh = new int[0];
    private int quickStackSize = 0;
    private int[][] matrixValues = new int[0][0];
    private int matrixK = -1;
    private int matrixRow = -1;
    private int matrixCol = -1;
    private String matrixLabel = "";
    private int nQueensBoardSize = 4;
    private int sudokuGridSize = 4;
    private int sudokuPreset = 1;
    private int knightsTourBoardSize = 5;
    private int knightsTourStartPos = 1;
    private int mazeSize = 5;
    private int mazePreset = 1;
    private int hamiltonianNodes = 5;
    private int hamiltonianType = 1;
    private int graphColoringColors = 3;
    private int graphColoringPreset = 1;
    private int dpPreset = 1;
    private int stringPatternPreset = 1;
    private int graphPreset = 1;
    private int graphStartNode = 0;
    private int graphNodeCount = 5;
    private int advancedDsPreset = 1;
    private String advancedDsCustomInput = "";
    private int specializedPreset = 1;
    private String specializedCustomInput = "";
    private int heapSize = 0;
    private int heapModePhase = 0;
    private int heapSortPhase = 0;
    private int heapSortHeapifyIndex = -1;
    private String patternText = "ABACABADABAC";
    private String patternQuery = "ABAC";
    private int patternWindowStart = -1;
    private int patternMatchIndex = -1;
    private boolean patternFound = false;
    private int[] unionParents = new int[0];
    private int[] unionPath = new int[0];
    private int unionPathStep = -1;
    private int unionFocusNode = -1;
    private int unionRootNode = -1;
    private int[] dijkstraDistances = new int[0];
    private boolean[] dijkstraSettled = new boolean[0];
    private int dijkstraCurrentNode = -1;
    private int dijkstraPhase = 0;
    private int dijkstraNeighborIndex = 0;
    private int[][] dijkstraWeights = new int[0][0];
    private int[] huffmanQueueWeights = new int[0];
    private int huffmanMergedWeight = -1;
    private int huffmanMergedLeft = -1;
    private int huffmanMergedRight = -1;
    private int huffmanPreset = 1;
    private String huffmanCustomInput = "";
    private int avlTreePreset = 1;
    private String avlTreeCustomInput = "";
    private boolean focusModeEnabled = false;
    private boolean predictionPending = false;
    private String currentOperationLabel = "Ready";
    private int lastPulsedPlaybackStep = -1;
    private boolean progressScrubbing = false;
    private int lastScrubbedIndex = -1;
    private boolean fastSteppingForward = false;
    private boolean fastSteppingBackward = false;
    private int fastStepStartIndex = -1;
    private float playbackVisualSpeed = 1f;
    private int lastCompletionFlashStep = -1;
    private boolean lastPlaybackWasPlaying = false;
    private Runnable restartBadgeHideRunnable;
    private int lastAnimatedStepNumber = -1;
    private String lastStepDetailsTitle = "";
    private String lastStepDetailsBody = "";
    private String lastLivePseudocodeLine = "";
    private boolean showMathTeaching = true;
    private int restoredPlaybackStepIndex = -1;
    private float restoredPlaybackSpeed = 1f;
    private boolean restoredPlaybackAtEnd = false;
    private int statusVisibilityBeforeFocus = View.VISIBLE;
    private int explanationVisibilityBeforeFocus = View.VISIBLE;
    private int traversalVisibilityBeforeFocus = View.GONE;
    private int inputRowVisibilityBeforeFocus = View.VISIBLE;
    private int inputActionsVisibilityBeforeFocus = View.VISIBLE;
    private int operationsVisibilityBeforeFocus = View.GONE;
    private int controlsVisibilityBeforeFocus = View.VISIBLE;
    private final SnapshotTimeline<Snapshot> timeline = new SnapshotTimeline<>(160);

    public static VisualizationFragment newInstance(String techniqueId) {
        VisualizationFragment fragment = new VisualizationFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TECHNIQUE_ID, techniqueId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_visualization, container, false);
    }

    private void animateFragmentEntrance(View view) {
        view.setAlpha(0f);
        view.setScaleX(0.97f);
        view.setScaleY(0.97f);
        view.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(260L)
            .start();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animateFragmentEntrance(view);

        techniqueId = getArguments() == null ? "linear_search" : getArguments().getString(ARG_TECHNIQUE_ID, "linear_search");
        timeline.setMaxSize(resolveTimelineCapacity());
        TechniqueContent content = AppRepository.getContent(techniqueId);
        techniqueTitle = content == null ? "Technique" : content.title;
        if ("backtracking_n_queens".equals(techniqueId) || "bt_n_queens".equals(techniqueId)) {
            nQueensBoardSize = new com.dsavisualizer.app.data.AppPreferences(requireContext()).getNQueensBoardSize();
            if (nQueensBoardSize < 4) {
                nQueensBoardSize = 4;
            }
        } else if ("backtracking_sudoku".equals(techniqueId) || "bt_sudoku".equals(techniqueId)) {
            com.dsavisualizer.app.data.AppPreferences prefs = new com.dsavisualizer.app.data.AppPreferences(requireContext());
            sudokuGridSize = prefs.getSudokuGridSize();
            sudokuPreset = prefs.getSudokuPreset();
        } else if ("backtracking_knights_tour".equals(techniqueId) || "bt_knights_tour".equals(techniqueId)) {
            com.dsavisualizer.app.data.AppPreferences prefs = new com.dsavisualizer.app.data.AppPreferences(requireContext());
            knightsTourBoardSize = prefs.getKnightsTourBoardSize();
            knightsTourStartPos = prefs.getKnightsTourStartPos();
        }
        sessionViewModel = new ViewModelProvider(requireActivity()).get(TechniqueSessionViewModel.class);
        stepEngine = new AlgorithmStepEngine(this);
        playbackController = new PlaybackController(stepEngine);
        try {
            visualizerView = view.findViewById(R.id.technique_visualizer);
            visualizerScrollView = view.findViewById(R.id.visualizer_scroll_container);
            contentScrollView = view.findViewById(R.id.visual_content_scroll);
            studyHeader = view.findViewById(R.id.visual_study_header);
            studyTitleText = view.findViewById(R.id.visual_study_title);
            topicNameText = view.findViewById(R.id.visual_topic_name);
            studyMessageText = view.findViewById(R.id.visual_study_message);
            livePseudocodeText = view.findViewById(R.id.visual_live_pseudocode);
            studyChipPrimaryText = view.findViewById(R.id.visual_study_chip_primary);
            studyChipSecondaryText = view.findViewById(R.id.visual_study_chip_secondary);
            studyChipTertiaryText = view.findViewById(R.id.visual_study_chip_tertiary);
            statusText = view.findViewById(R.id.visual_status_text);
            explanationText = view.findViewById(R.id.visual_explanation_text);
            traversalGroup = view.findViewById(R.id.visual_traversal_group);
            traversalLabel = view.findViewById(R.id.visual_traversal_label);
            traversalSpinner = view.findViewById(R.id.visual_traversal_spinner);
            playPauseButton = view.findViewById(R.id.visual_start_pause_button);
            focusModeButton = view.findViewById(R.id.visual_focus_button);
            studyExitButton = view.findViewById(R.id.visual_study_exit_button);
            previousButton = view.findViewById(R.id.visual_previous_button);
            replayButton = view.findViewById(R.id.visual_replay_button);
            studyPreviousButton = view.findViewById(R.id.visual_study_previous_button);
            studyReplayButton = view.findViewById(R.id.visual_study_replay_button);
            studyPlayButton = view.findViewById(R.id.visual_study_play_button);
            studyNextButton = view.findViewById(R.id.visual_study_next_button);
            inputRow = view.findViewById(R.id.visual_input_row);
            inputActionsRow = view.findViewById(R.id.visual_input_actions_row);
            compactLimitGroup = view.findViewById(R.id.visual_compact_limit_group);
            controlsGroup = view.findViewById(R.id.visual_controls_group);
            controlsStrip = view.findViewById(R.id.visual_controls_strip);
            studyControlsStrip = view.findViewById(R.id.visual_study_controls_strip);
            timelineScrollView = view.findViewById(R.id.visual_timeline_scroll);
            timelineRow = view.findViewById(R.id.visual_timeline_row);
            timelineLabel = view.findViewById(R.id.visual_timeline_label);
            valuesInputLayout = view.findViewById(R.id.visual_values_input_layout);
            targetInputLayout = view.findViewById(R.id.visual_target_input_layout);
            compactLimitInputLayout = view.findViewById(R.id.visual_compact_limit_input_layout);
            operationInputLayout = view.findViewById(R.id.visual_operation_input_layout);
            valuesInput = view.findViewById(R.id.visual_values_input);
            targetInput = view.findViewById(R.id.visual_target_input);
            compactLimitInput = view.findViewById(R.id.visual_compact_limit_input);
            operationInput = view.findViewById(R.id.visual_operation_input);
            operationsGroup = view.findViewById(R.id.visual_operations_group);
            operationPrimaryRow = view.findViewById(R.id.visual_operation_row_primary);
            operationSecondaryRow = view.findViewById(R.id.visual_operation_row_secondary);
            operationPrimaryButton = view.findViewById(R.id.visual_operation_primary_button);
            operationSecondaryButton = view.findViewById(R.id.visual_operation_secondary_button);
            operationTertiaryButton = view.findViewById(R.id.visual_operation_tertiary_button);
            operationFourthButton = view.findViewById(R.id.visual_operation_fourth_button);
            operationsTitle = view.findViewById(R.id.visual_operations_title);
            studyActionsHost = view.findViewById(R.id.visual_study_actions_host);
            overlayActionsHost = view.findViewById(R.id.visual_overlay_actions_host);
            if (operationsGroup != null && operationsGroup.getParent() instanceof ViewGroup) {
                scrollContentLayout = (ViewGroup) operationsGroup.getParent();
                originalOperationsIndex = scrollContentLayout.indexOfChild(operationsGroup);
            }
            MaterialButton resetButton = view.findViewById(R.id.visual_reset_button);
            MaterialButton stepButton = view.findViewById(R.id.visual_step_button);
            nextButton = stepButton;
            MaterialButton randomButton = view.findViewById(R.id.visual_random_button);
            MaterialButton applyButton = view.findViewById(R.id.visual_apply_button);
            editInputButton = view.findViewById(R.id.visual_edit_input_button);
            nowDoingLabel = view.findViewById(R.id.visual_now_doing_label);
            progressLabel = view.findViewById(R.id.visual_progress_label);
            controlsHintLabel = view.findViewById(R.id.visual_controls_hint_label);
            stepChipView = view.findViewById(R.id.visual_step_chip);
            completedBadgeView = view.findViewById(R.id.visual_completed_badge);
            progressBadge = view.findViewById(R.id.visual_progress_badge);
            progressBar = view.findViewById(R.id.visual_progress_bar);
            progressTrack = view.findViewById(R.id.visual_progress_track);
            progressHandle = view.findViewById(R.id.visual_progress_handle);
            controlsProgressLine = view.findViewById(R.id.visual_controls_progress_line);
            stepDetailsCard = view.findViewById(R.id.visual_step_details_card);
            stepDetailsTitle = view.findViewById(R.id.visual_step_details_title);
            stepDetailsBody = view.findViewById(R.id.visual_step_details_body);
            if (stepDetailsCard != null) {
                stepDetailsCard.setVisibility(View.GONE);
                stepDetailsCard.setAlpha(0f);
            }
            pointerLegendRow = view.findViewById(R.id.visual_pointer_legend_row);
            pointerLegendTitle = view.findViewById(R.id.visual_pointer_legend_title);
            pointerLegendBody = view.findViewById(R.id.visual_pointer_legend_body);
            formulaRow = view.findViewById(R.id.visual_formula_row);
            formulaTitle = view.findViewById(R.id.visual_formula_title);
            formulaBody = view.findViewById(R.id.visual_formula_body);
            formulaToggleButton = view.findViewById(R.id.visual_formula_toggle_button);
            statePanel = view.findViewById(R.id.visual_state_panel);
            stateTitle = view.findViewById(R.id.visual_state_title);
            stateBody = view.findViewById(R.id.visual_state_body);
            adjacencyPanel = view.findViewById(R.id.visual_adjacency_panel);
            adjacencyTitle = view.findViewById(R.id.visual_adjacency_title);
            adjacencyBody = view.findViewById(R.id.visual_adjacency_body);
            completionBanner = view.findViewById(R.id.visual_completion_banner);
            completionBannerTitle = view.findViewById(R.id.visual_completion_banner_title);
            completionBannerBody = view.findViewById(R.id.visual_completion_banner_body);
            if (visualizerView != null && visualizerView.getLayoutParams() != null) {
                visualizerDefaultHeight = visualizerView.getLayoutParams().height;
            }

            if (timelineLabel != null) {
                timelineLabel.setVisibility(View.GONE);
            }
            if (timelineScrollView != null) {
                timelineScrollView.setVisibility(View.GONE);
            }

            if (replayButton != null) {
                replayButton.setText("");
                replayButton.setContentDescription(getString(R.string.vis_restart));
            }
            if (previousButton != null) {
                previousButton.setText("");
                previousButton.setContentDescription(getString(R.string.vis_step_prev));
            }
            if (nextButton != null) {
                nextButton.setText("");
                nextButton.setContentDescription(getString(R.string.vis_step_next));
            }
            if (editInputButton != null) {
                editInputButton.setText("");
                editInputButton.setContentDescription(getString(R.string.vis_edit_input));
            }
            if (topicNameText != null) {
                topicNameText.setText(techniqueTitle);
            }
            if (studyReplayButton != null) {
                studyReplayButton.setText("");
                studyReplayButton.setContentDescription(getString(R.string.vis_restart));
            }
            if (studyPreviousButton != null) {
                studyPreviousButton.setText("");
                studyPreviousButton.setContentDescription(getString(R.string.vis_step_prev));
            }
            if (studyNextButton != null) {
                studyNextButton.setText("");
                studyNextButton.setContentDescription(getString(R.string.vis_step_next));
            }

            try {
                applySafeAreaInsets(view);
                configureImmersiveVisualizationLayout(view);
            } catch (RuntimeException layoutError) {
                Log.e(TAG, "Landscape visualization layout setup failed; using safe fallback.", layoutError);
            }

            visualizerView.bindTechnique(techniqueId);
            setupInputs(content);
            setupTraversalControls();
            applyDefaultData(content);
            boolean restoredState = restoreVisualizationInstanceState(savedInstanceState);
            com.dsavisualizer.app.data.AppPreferences prefs = new com.dsavisualizer.app.data.AppPreferences(requireContext());
            nQueensBoardSize = prefs.getNQueensBoardSize();
            sudokuGridSize = prefs.getSudokuGridSize();
            sudokuPreset = prefs.getSudokuPreset();
            knightsTourBoardSize = prefs.getKnightsTourBoardSize();
            knightsTourStartPos = prefs.getKnightsTourStartPos();
            mazeSize = prefs.getMazeSize();
            mazePreset = prefs.getMazePreset();
            hamiltonianNodes = prefs.getHamiltonianNodes();
            hamiltonianType = prefs.getHamiltonianType();
            graphColoringColors = prefs.getGraphColoringColors();
            graphColoringPreset = prefs.getGraphColoringPreset();
            dpPreset = prefs.getDpPreset(techniqueId);
            graphPreset = prefs.getGraphPreset(techniqueId);
            graphStartNode = prefs.getGraphStartNode(techniqueId);
            graphNodeCount = prefs.getGraphNodeCount(techniqueId);
            advancedDsPreset = prefs.getAdvancedDsPreset(techniqueId);
            advancedDsCustomInput = prefs.getAdvancedDsCustomInput(techniqueId, com.dsavisualizer.app.ui.visualization.runtime.AdvancedDsStepEngine.getDefaultInput(techniqueId, advancedDsPreset));
            specializedPreset = prefs.getSpecializedPreset(techniqueId);
            specializedCustomInput = prefs.getSpecializedCustomInput(techniqueId, com.dsavisualizer.app.ui.visualization.runtime.SpecializedStepEngine.getDefaultInput(techniqueId, specializedPreset));
            huffmanPreset = prefs.getHuffmanPreset();
            huffmanCustomInput = prefs.getHuffmanCustomInput();
            initializeCustomSortPreview();
            bindCurrentState(restoredState ? getSafeText(statusText, "Press Start to begin") : "Press Start to begin");
            if (restoredState && explanationText != null && savedInstanceState != null) {
                explanationText.setText(savedInstanceState.getString(STATE_EXPLANATION_TEXT, getSafeText(explanationText, "")));
            }
            syncScrollToFocus(-1);
            refreshControlsHint();

            playPauseButton.setOnClickListener(v -> {
                dismissControlsHint();
                handlePlayPause();
            });
            resetButton.setOnClickListener(v -> {
                dismissControlsHint();
                if (playbackController != null) {
                    playbackController.reset();
                } else {
                    resetVisualization();
                }
            });
            stepButton.setOnClickListener(v -> {
                dismissControlsHint();
                stepForwardOnce();
            });
            randomButton.setOnClickListener(v -> applyRandomData());
            applyButton.setOnClickListener(v -> applyUserData());
            if (editInputButton != null) {
                editInputButton.setOnClickListener(v -> openInputEditorSheet());
            }
            if (formulaToggleButton != null) {
                formulaToggleButton.setOnClickListener(v -> {
                    showMathTeaching = !showMathTeaching;
                    refreshTeachingDecor(statusText != null && statusText.getText() != null ? statusText.getText().toString() : "");
                });
            }
            focusModeButton.setOnClickListener(v -> toggleFocusMode());
            studyExitButton.setOnClickListener(v -> {
                if (focusModeEnabled) {
                    toggleFocusMode();
                }
            });
            previousButton.setOnClickListener(v -> {
                dismissControlsHint();
                stepBackwardOnce();
            });
            replayButton.setOnClickListener(v -> {
                dismissControlsHint();
                replayCurrentStep();
            });
            studyPreviousButton.setOnClickListener(v -> {
                dismissControlsHint();
                stepBackwardOnce();
            });
            studyReplayButton.setOnClickListener(v -> {
                dismissControlsHint();
                replayCurrentStep();
            });
            studyPlayButton.setOnClickListener(v -> {
                dismissControlsHint();
                handlePlayPause();
            });
            studyNextButton.setOnClickListener(v -> {
                dismissControlsHint();
                stepForwardOnce();
            });
            previousButton.setOnLongClickListener(v -> startFastStep(false));
            studyPreviousButton.setOnLongClickListener(v -> startFastStep(false));
            nextButton.setOnLongClickListener(v -> startFastStep(true));
            studyNextButton.setOnLongClickListener(v -> startFastStep(true));
            bindFastStepRelease(previousButton);
            bindFastStepRelease(studyPreviousButton);
            bindFastStepRelease(nextButton);
            bindFastStepRelease(studyNextButton);
            setupStepGestures();
            if (progressTrack != null) {
                progressTrack.setOnTouchListener((v, event) -> {
                    switch (event.getAction()) {
                        case android.view.MotionEvent.ACTION_DOWN:
                            startProgressScrub(v, event.getX());
                            return true;
                        case android.view.MotionEvent.ACTION_MOVE:
                            updateProgressScrub(v, event.getX());
                            return true;
                        case android.view.MotionEvent.ACTION_UP:
                        case android.view.MotionEvent.ACTION_CANCEL:
                            endProgressScrub(v, event.getX());
                            return true;
                        default:
                            return false;
                    }
                });
            }
            if (playbackController != null) {
                applyPlaybackSpeed(1f);
                playbackController.getPlaybackState().observe(getViewLifecycleOwner(), this::renderPlaybackState);
                if (restoredState && restoredPlaybackStepIndex >= 0) {
                    playbackController.restoreState(new PlaybackState(
                        false,
                        restoredPlaybackStepIndex,
                        restoredPlaybackSpeed,
                        restoredPlaybackStepIndex <= 0,
                        restoredPlaybackAtEnd
                    ));
                }
                renderPlaybackState(playbackController.getPlaybackState().getValue());
            }
            if (contentScrollView != null) {
                contentScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> updateStickyControls(scrollY));
            }
            applyPlayerAccentColors();
            updateBinaryChips();
            captureSnapshot();
        } catch (Throwable startupError) {
            Log.e(TAG, "Visualization startup failed for technique " + techniqueId, startupError);
            showStartupFallback(view);
        }
    }

    private void showStartupFallback(@NonNull View rootView) {
        if (statusText != null) {
            statusText.setVisibility(View.VISIBLE);
            statusText.setText("Visualization could not open safely on this screen yet.");
        }
        if (explanationText != null) {
            explanationText.setVisibility(View.VISIBLE);
            explanationText.setText("Please rotate back, reopen the topic, or try another technique while we isolate this crash path.");
        }
        View visualizer = rootView.findViewById(R.id.technique_visualizer);
        if (visualizer != null) {
            visualizer.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray(STATE_WORKING_VALUES, workingValues);
        outState.putString(STATE_EXPRESSION_INPUT, expressionInput);
        outState.putInt(STATE_STRUCTURE_LIMIT, structureLimit);
        outState.putInt(STATE_TARGET, target);
        outState.putInt(STATE_STEP_INDEX, stepIndex);
        outState.putInt(STATE_AUX_INDEX, auxIndex);
        outState.putInt(STATE_FOUND_INDEX, foundIndex);
        outState.putString(STATE_STATUS_TEXT, getSafeText(statusText, ""));
        outState.putString(STATE_EXPLANATION_TEXT, getSafeText(explanationText, ""));
        PlaybackState playbackState = playbackController == null ? null : playbackController.getPlaybackState().getValue();
        if (playbackState != null) {
            outState.putInt(STATE_PLAYBACK_STEP_INDEX, playbackState.currentStepIndex);
            outState.putFloat(STATE_PLAYBACK_SPEED, playbackState.playbackSpeed);
            outState.putBoolean(STATE_PLAYBACK_AT_END, playbackState.isAtEnd);
        }
    }

    private boolean restoreVisualizationInstanceState(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null || !savedInstanceState.containsKey(STATE_WORKING_VALUES)) {
            return false;
        }
        int[] restoredValues = savedInstanceState.getIntArray(STATE_WORKING_VALUES);
        workingValues = restoredValues == null ? workingValues : restoredValues;
        expressionInput = savedInstanceState.getString(STATE_EXPRESSION_INPUT, expressionInput);
        structureLimit = savedInstanceState.getInt(STATE_STRUCTURE_LIMIT, structureLimit);
        target = savedInstanceState.getInt(STATE_TARGET, target);
        stepIndex = savedInstanceState.getInt(STATE_STEP_INDEX, stepIndex);
        auxIndex = savedInstanceState.getInt(STATE_AUX_INDEX, auxIndex);
        foundIndex = savedInstanceState.getInt(STATE_FOUND_INDEX, foundIndex);
        restoredPlaybackStepIndex = savedInstanceState.getInt(STATE_PLAYBACK_STEP_INDEX, Math.max(0, stepIndex + 1));
        restoredPlaybackSpeed = savedInstanceState.getFloat(STATE_PLAYBACK_SPEED, 1f);
        restoredPlaybackAtEnd = savedInstanceState.getBoolean(STATE_PLAYBACK_AT_END, false);
        if (visualizerView != null) {
            visualizerView.setExpressionInput(expressionInput);
        }
        ensureStructureLimitFitsValues();
        syncValuesInput();
        return true;
    }

    private String getSafeText(@Nullable TextView textView, String fallback) {
        if (textView == null || textView.getText() == null) {
            return fallback;
        }
        return textView.getText().toString();
    }

    private void applySafeAreaInsets(View rootView) {
        final int baseLeft = rootView.getPaddingLeft();
        final int baseTop = rootView.getPaddingTop();
        final int baseRight = rootView.getPaddingRight();
        final int baseBottom = rootView.getPaddingBottom();
        final View matrixContainer = rootView.findViewById(R.id.matrixContainer);
        final int matrixBaseLeft = matrixContainer == null ? 0 : matrixContainer.getPaddingLeft();
        final int matrixBaseTop = matrixContainer == null ? 0 : matrixContainer.getPaddingTop();
        final int matrixBaseRight = matrixContainer == null ? 0 : matrixContainer.getPaddingRight();
        final int matrixBaseBottom = matrixContainer == null ? 0 : matrixContainer.getPaddingBottom();
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (view, windowInsets) -> {
            Insets systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            boolean landscape = isLandscapeLayout();
            int leftPadding = landscape ? baseLeft : baseLeft + systemBars.left;
            int topPadding = landscape ? baseTop : baseTop + systemBars.top;
            int rightPadding = landscape ? baseRight : baseRight + systemBars.right;
            int bottomPadding = landscape ? baseBottom : baseBottom + systemBars.bottom;
            view.setPadding(leftPadding, topPadding, rightPadding, bottomPadding);
            if (matrixContainer != null && isLandscapeLayout()) {
                matrixContainer.setPadding(
                    matrixBaseLeft,
                    matrixBaseTop,
                    matrixBaseRight,
                    matrixBaseBottom
                );
            }
            return windowInsets;
        });
        ViewCompat.requestApplyInsets(rootView);
    }

    private void configureImmersiveVisualizationLayout(View rootView) {
        boolean landscape = isLandscapeLayout();
        View difficultyRow = rootView.findViewById(R.id.visual_difficulty_row);
        View resetButton = rootView.findViewById(R.id.visual_reset_button);
        View focusButton = rootView.findViewById(R.id.visual_focus_button);
        View predictionCard = rootView.findViewById(R.id.visual_prediction_card);

        if (studyHeader != null) {
            studyHeader.setVisibility(View.GONE);
        }
        if (difficultyRow != null) {
            difficultyRow.setVisibility(View.GONE);
        }
        if (resetButton != null) {
            resetButton.setVisibility(View.GONE);
        }
        if (focusButton != null) {
            focusButton.setVisibility(View.GONE);
        }
        if (nowDoingLabel != null) {
            nowDoingLabel.setVisibility(View.GONE);
        }
        if (predictionCard != null) {
            predictionCard.setVisibility(View.GONE);
        }
        if (inputRow != null) {
            inputRow.setVisibility(View.GONE);
        }
        if (inputActionsRow != null) {
            inputActionsRow.setVisibility(View.GONE);
        }
        if (progressLabel != null) {
            progressLabel.setVisibility(View.VISIBLE);
        }
        if (progressTrack != null) {
            progressTrack.setVisibility(View.VISIBLE);
        }

        if (visualizerView != null && visualizerView.getLayoutParams() != null) {
            ViewGroup.LayoutParams params = visualizerView.getLayoutParams();
            if (landscape) {
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            } else {
                params.height = resolvePortraitVisualizerHeight();
            }
            visualizerView.setLayoutParams(params);
        }
        if (visualizerScrollView != null) {
            visualizerScrollView.setHorizontalScrollBarEnabled(true);
            visualizerScrollView.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
            visualizerScrollView.setOnTouchListener(null);
            if (landscape) {
                visualizerScrollView.post(this::lockLandscapeVisualizerToViewport);
            }
        }

        if (scrollContentLayout == null || visualizerScrollView == null) {
            return;
        }

        if (visualizerScrollView.getParent() == scrollContentLayout) {
            scrollContentLayout.removeView(visualizerScrollView);
            scrollContentLayout.addView(visualizerScrollView, 0);
        }

        if (controlsGroup != null && controlsGroup.getParent() == scrollContentLayout) {
            scrollContentLayout.removeView(controlsGroup);
            int visualizerIndex = scrollContentLayout.indexOfChild(visualizerScrollView);
            int targetIndex = Math.min(scrollContentLayout.getChildCount(), visualizerIndex + 1);
            scrollContentLayout.addView(controlsGroup, targetIndex);
        } else if (!landscape && controlsGroup != null && controlsGroup.getParent() instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) controlsGroup.getParent();
            parent.removeView(controlsGroup);
            scrollContentLayout.addView(controlsGroup);
        }
        if (editInputButton != null && editInputButton.getParent() == scrollContentLayout) {
            scrollContentLayout.removeView(editInputButton);
            int controlsIndex = scrollContentLayout.indexOfChild(controlsGroup);
            int targetIndex = Math.min(scrollContentLayout.getChildCount(), controlsIndex + 1);
            scrollContentLayout.addView(editInputButton, targetIndex);
        }
        if (operationsGroup != null && operationsGroup.getParent() == scrollContentLayout) {
            scrollContentLayout.removeView(operationsGroup);
            int anchorIndex = editInputButton != null
                ? scrollContentLayout.indexOfChild(editInputButton)
                : scrollContentLayout.indexOfChild(controlsGroup);
            int targetIndex = Math.min(scrollContentLayout.getChildCount(), anchorIndex + 1);
            scrollContentLayout.addView(operationsGroup, targetIndex);
        }

        if (controlsGroup != null) {
            controlsGroup.setBackground(null);
            controlsGroup.setPadding(0, 0, 0, 0);
            controlsGroup.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
            ViewGroup.LayoutParams rawParams = controlsGroup.getLayoutParams();
            if (rawParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) rawParams;
                int side = landscape ? 0 : Math.round(dpToPx(16));
                params.leftMargin = side;
                params.rightMargin = side;
                params.topMargin = landscape ? 0 : Math.round(dpToPx(8));
                controlsGroup.setLayoutParams(params);
            }
        }
        if (controlsStrip != null) {
            ViewGroup.LayoutParams rawParams = controlsStrip.getLayoutParams();
            if (rawParams instanceof LinearLayout.LayoutParams) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rawParams;
                params.gravity = android.view.Gravity.CENTER_HORIZONTAL;
                controlsStrip.setLayoutParams(params);
            }
        }
        if (editInputButton != null) {
            ViewGroup.LayoutParams rawParams = editInputButton.getLayoutParams();
            if (rawParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) rawParams;
                params.topMargin = Math.round(dpToPx(10));
                editInputButton.setLayoutParams(params);
            }
        }
        if (operationsGroup != null) {
            ViewGroup.LayoutParams rawParams = operationsGroup.getLayoutParams();
            if (rawParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) rawParams;
                params.topMargin = Math.round(dpToPx(10));
                operationsGroup.setLayoutParams(params);
            }
        }

        keepStepChipInHeader();
    }

    private int resolvePortraitVisualizerHeight() {
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        boolean spatialVisualization = isGraphTechnique()
            || isGraphTraversalTechnique()
            || "warshall_algorithm".equals(techniqueId)
            || "floyd_algorithm".equals(techniqueId)
            || "shortest_paths".equals(techniqueId)
            || "dijkstra_algorithm".equals(techniqueId)
            || "tree_traversal".equals(techniqueId)
            || "preorder_traversal".equals(techniqueId)
            || "postorder_traversal".equals(techniqueId)
            || "traversal_using_stacks".equals(techniqueId)
            || "max_heap_insertion".equals(techniqueId)
            || "max_heap_deletion".equals(techniqueId)
            || "avl_tree".equals(techniqueId)
            || "trie".equals(techniqueId)
            || "huffman_algorithm".equals(techniqueId);
        boolean compactLinearVisualization = isSearchingMode()
            || isSortingMode()
            || isStructureMode();
        if ("radix_sort".equals(techniqueId)) {
            return visualizerView != null ? visualizerView.resolveRadixDesiredHeight() : (int) dpToPx(210);
        }
        int minHeight = (int) dpToPx(spatialVisualization ? 232 : (compactLinearVisualization ? 180 : 200));
        int maxHeight = (int) dpToPx(spatialVisualization ? 340 : (compactLinearVisualization ? 230 : 250));
        int preferredHeight = Math.round(screenHeight * (spatialVisualization ? 0.35f : (compactLinearVisualization ? 0.21f : 0.25f)));
        return Math.max(minHeight, Math.min(maxHeight, preferredHeight));
    }

    private void lockLandscapeVisualizerToViewport() {
        if (!isAdded() || getContext() == null || !isLandscapeLayout()
            || visualizerScrollView == null || visualizerView == null) {
            return;
        }
        int viewportWidth = visualizerScrollView.getWidth() - visualizerScrollView.getPaddingLeft() - visualizerScrollView.getPaddingRight();
        int viewportHeight = visualizerScrollView.getHeight() - visualizerScrollView.getPaddingTop() - visualizerScrollView.getPaddingBottom();
        if (viewportWidth <= 0 || viewportHeight <= 0) {
            return;
        }
        ViewGroup.LayoutParams params = visualizerView.getLayoutParams();
        boolean changed = false;
        if (params.width != viewportWidth) {
            params.width = viewportWidth;
            changed = true;
        }
        if (params.height != viewportHeight) {
            params.height = viewportHeight;
            changed = true;
        }
        if (changed) {
            visualizerView.setLayoutParams(params);
        }
        visualizerScrollView.scrollTo(0, 0);
        visualizerView.requestLayout();
        visualizerView.invalidate();
    }

    private boolean isLandscapeLayout() {
        if (!isAdded() || getContext() == null) {
            return false;
        }
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void openNQueensSettingsDialog() {
        if (!isAdded()) {
            return;
        }
        android.app.Dialog dialog = new android.app.Dialog(requireContext());
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_nqueens_settings, null, false);
        dialog.setContentView(view);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));
            int dialogWidth = (int) (getResources().getDisplayMetrics().widthPixels * 0.92f);
            dialog.getWindow().setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        MaterialButton btn4 = view.findViewById(R.id.nqueens_size_4);
        MaterialButton btn5 = view.findViewById(R.id.nqueens_size_5);
        MaterialButton btn6 = view.findViewById(R.id.nqueens_size_6);
        MaterialButton btn8 = view.findViewById(R.id.nqueens_size_8);
        MaterialButton btnClose = view.findViewById(R.id.nqueens_btn_close);

        int selectedColor = android.graphics.Color.parseColor("#7C3AED");
        int unselectedBg = android.graphics.Color.parseColor("#131B2E");
        int unselectedStroke = android.graphics.Color.parseColor("#27354E");
        int textWhite = android.graphics.Color.parseColor("#FFFFFF");
        int textMuted = android.graphics.Color.parseColor("#E2E8F0");

        MaterialButton[] sizeButtons = new MaterialButton[]{btn4, btn5, btn6, btn8};
        int[] sizes = new int[]{4, 5, 6, 8};

        Runnable refreshButtonStyles = () -> {
            for (int i = 0; i < sizeButtons.length; i++) {
                MaterialButton btn = sizeButtons[i];
                if (btn == null) continue;
                boolean isCurrent = (sizes[i] == nQueensBoardSize);
                if (isCurrent) {
                    btn.setBackgroundTintList(android.content.res.ColorStateList.valueOf(selectedColor));
                    btn.setTextColor(textWhite);
                    btn.setStrokeColor(android.content.res.ColorStateList.valueOf(selectedColor));
                    btn.setStrokeWidth(0);
                } else {
                    btn.setBackgroundTintList(android.content.res.ColorStateList.valueOf(unselectedBg));
                    btn.setTextColor(textMuted);
                    btn.setStrokeColor(android.content.res.ColorStateList.valueOf(unselectedStroke));
                    btn.setStrokeWidth((int) (1.5f * getResources().getDisplayMetrics().density));
                }
            }
        };

        refreshButtonStyles.run();

        for (int i = 0; i < sizeButtons.length; i++) {
            final int chosenSize = sizes[i];
            MaterialButton btn = sizeButtons[i];
            if (btn != null) {
                btn.setOnClickListener(v -> {
                    if (nQueensBoardSize != chosenSize) {
                        applyNQueensBoardSize(chosenSize);
                        refreshButtonStyles.run();
                    }
                });
            }
        }

        if (btnClose != null) {
            btnClose.setOnClickListener(v -> dialog.dismiss());
        }

        dialog.show();
    }

    private void applyNQueensBoardSize(int size) {
        nQueensBoardSize = size;
        new com.dsavisualizer.app.data.AppPreferences(requireContext()).setNQueensBoardSize(size);
        resetVisualization();
        bindCurrentState("N-Queens (" + size + "×" + size + ") Chessboard ready. Tap Play or Step to explore.");
        updateProgressHandle(0, estimatePlaybackTotalSteps());
        updatePlayerProgress(PlaybackState.initial());
        if (visualizerView != null) {
            visualizerView.invalidate();
        }
    }

    private void openSudokuSettingsDialog() {
        if (!isAdded()) {
            return;
        }
        android.app.Dialog dialog = new android.app.Dialog(requireContext());
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_sudoku_settings, null, false);
        dialog.setContentView(view);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));
            int dialogWidth = (int) (getResources().getDisplayMetrics().widthPixels * 0.92f);
            dialog.getWindow().setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        MaterialButton btn4 = view.findViewById(R.id.sudoku_size_4);
        MaterialButton btn9 = view.findViewById(R.id.sudoku_size_9);
        MaterialButton btnPreset1 = view.findViewById(R.id.sudoku_preset_1);
        MaterialButton btnPreset2 = view.findViewById(R.id.sudoku_preset_2);
        MaterialButton btnClose = view.findViewById(R.id.sudoku_btn_close);

        int selectedColor = android.graphics.Color.parseColor("#7C3AED");
        int unselectedBg = android.graphics.Color.parseColor("#131B2E");
        int unselectedStroke = android.graphics.Color.parseColor("#27354E");
        int textWhite = android.graphics.Color.parseColor("#FFFFFF");
        int textMuted = android.graphics.Color.parseColor("#E2E8F0");

        Runnable refreshStyles = () -> {
            if (btn4 != null) {
                boolean is4 = (sudokuGridSize == 4);
                btn4.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is4 ? selectedColor : unselectedBg));
                btn4.setTextColor(is4 ? textWhite : textMuted);
                btn4.setStrokeColor(android.content.res.ColorStateList.valueOf(is4 ? selectedColor : unselectedStroke));
                btn4.setStrokeWidth(is4 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btn9 != null) {
                boolean is9 = (sudokuGridSize == 9);
                btn9.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is9 ? selectedColor : unselectedBg));
                btn9.setTextColor(is9 ? textWhite : textMuted);
                btn9.setStrokeColor(android.content.res.ColorStateList.valueOf(is9 ? selectedColor : unselectedStroke));
                btn9.setStrokeWidth(is9 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btnPreset1 != null) {
                boolean isP1 = (sudokuPreset == 1);
                btnPreset1.setBackgroundTintList(android.content.res.ColorStateList.valueOf(isP1 ? selectedColor : unselectedBg));
                btnPreset1.setTextColor(isP1 ? textWhite : textMuted);
                btnPreset1.setStrokeColor(android.content.res.ColorStateList.valueOf(isP1 ? selectedColor : unselectedStroke));
                btnPreset1.setStrokeWidth(isP1 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btnPreset2 != null) {
                boolean isP2 = (sudokuPreset == 2);
                btnPreset2.setBackgroundTintList(android.content.res.ColorStateList.valueOf(isP2 ? selectedColor : unselectedBg));
                btnPreset2.setTextColor(isP2 ? textWhite : textMuted);
                btnPreset2.setStrokeColor(android.content.res.ColorStateList.valueOf(isP2 ? selectedColor : unselectedStroke));
                btnPreset2.setStrokeWidth(isP2 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
        };

        refreshStyles.run();

        if (btn4 != null) {
            btn4.setOnClickListener(v -> {
                if (sudokuGridSize != 4) {
                    applySudokuSettings(4, sudokuPreset);
                    refreshStyles.run();
                }
            });
        }
        if (btn9 != null) {
            btn9.setOnClickListener(v -> {
                if (sudokuGridSize != 9) {
                    applySudokuSettings(9, sudokuPreset);
                    refreshStyles.run();
                }
            });
        }
        if (btnPreset1 != null) {
            btnPreset1.setOnClickListener(v -> {
                if (sudokuPreset != 1) {
                    applySudokuSettings(sudokuGridSize, 1);
                    refreshStyles.run();
                }
            });
        }
        if (btnPreset2 != null) {
            btnPreset2.setOnClickListener(v -> {
                if (sudokuPreset != 2) {
                    applySudokuSettings(sudokuGridSize, 2);
                    refreshStyles.run();
                }
            });
        }
        if (btnClose != null) {
            btnClose.setOnClickListener(v -> dialog.dismiss());
        }

        dialog.show();
    }

    private void applySudokuSettings(int size, int preset) {
        sudokuGridSize = size;
        sudokuPreset = preset;
        com.dsavisualizer.app.data.AppPreferences prefs = new com.dsavisualizer.app.data.AppPreferences(requireContext());
        prefs.setSudokuGridSize(size);
        prefs.setSudokuPreset(preset);
        resetVisualization();
        bindCurrentState("Sudoku (" + size + "×" + size + ", Preset " + preset + ") ready. Tap Play or Step to explore.");
        updateProgressHandle(0, estimatePlaybackTotalSteps());
        updatePlayerProgress(PlaybackState.initial());
        if (visualizerView != null) {
            visualizerView.invalidate();
        }
    }

    private void openKnightsTourSettingsDialog() {
        if (!isAdded()) {
            return;
        }
        android.app.Dialog dialog = new android.app.Dialog(requireContext());
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_knights_tour_settings, null, false);
        dialog.setContentView(view);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));
            int dialogWidth = (int) (getResources().getDisplayMetrics().widthPixels * 0.92f);
            dialog.getWindow().setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        MaterialButton btn5 = view.findViewById(R.id.knights_size_5);
        MaterialButton btn6 = view.findViewById(R.id.knights_size_6);
        MaterialButton btn8 = view.findViewById(R.id.knights_size_8);
        MaterialButton btnCorner = view.findViewById(R.id.knights_start_corner);
        MaterialButton btnCenter = view.findViewById(R.id.knights_start_center);
        MaterialButton btnClose = view.findViewById(R.id.knights_btn_close);

        int selectedColor = android.graphics.Color.parseColor("#7C3AED");
        int unselectedBg = android.graphics.Color.parseColor("#131B2E");
        int unselectedStroke = android.graphics.Color.parseColor("#27354E");
        int textWhite = android.graphics.Color.parseColor("#FFFFFF");
        int textMuted = android.graphics.Color.parseColor("#E2E8F0");

        Runnable refreshStyles = () -> {
            if (btn5 != null) {
                boolean is5 = (knightsTourBoardSize == 5);
                btn5.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is5 ? selectedColor : unselectedBg));
                btn5.setTextColor(is5 ? textWhite : textMuted);
                btn5.setStrokeColor(android.content.res.ColorStateList.valueOf(is5 ? selectedColor : unselectedStroke));
                btn5.setStrokeWidth(is5 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btn6 != null) {
                boolean is6 = (knightsTourBoardSize == 6);
                btn6.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is6 ? selectedColor : unselectedBg));
                btn6.setTextColor(is6 ? textWhite : textMuted);
                btn6.setStrokeColor(android.content.res.ColorStateList.valueOf(is6 ? selectedColor : unselectedStroke));
                btn6.setStrokeWidth(is6 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btn8 != null) {
                boolean is8 = (knightsTourBoardSize == 8);
                btn8.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is8 ? selectedColor : unselectedBg));
                btn8.setTextColor(is8 ? textWhite : textMuted);
                btn8.setStrokeColor(android.content.res.ColorStateList.valueOf(is8 ? selectedColor : unselectedStroke));
                btn8.setStrokeWidth(is8 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btnCorner != null) {
                boolean isCorner = (knightsTourStartPos == 1);
                btnCorner.setBackgroundTintList(android.content.res.ColorStateList.valueOf(isCorner ? selectedColor : unselectedBg));
                btnCorner.setTextColor(isCorner ? textWhite : textMuted);
                btnCorner.setStrokeColor(android.content.res.ColorStateList.valueOf(isCorner ? selectedColor : unselectedStroke));
                btnCorner.setStrokeWidth(isCorner ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btnCenter != null) {
                boolean isCenter = (knightsTourStartPos == 2);
                btnCenter.setBackgroundTintList(android.content.res.ColorStateList.valueOf(isCenter ? selectedColor : unselectedBg));
                btnCenter.setTextColor(isCenter ? textWhite : textMuted);
                btnCenter.setStrokeColor(android.content.res.ColorStateList.valueOf(isCenter ? selectedColor : unselectedStroke));
                btnCenter.setStrokeWidth(isCenter ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
        };

        refreshStyles.run();

        if (btn5 != null) {
            btn5.setOnClickListener(v -> {
                if (knightsTourBoardSize != 5) {
                    applyKnightsTourSettings(5, knightsTourStartPos);
                    refreshStyles.run();
                }
            });
        }
        if (btn6 != null) {
            btn6.setOnClickListener(v -> {
                if (knightsTourBoardSize != 6) {
                    applyKnightsTourSettings(6, knightsTourStartPos);
                    refreshStyles.run();
                }
            });
        }
        if (btn8 != null) {
            btn8.setOnClickListener(v -> {
                if (knightsTourBoardSize != 8) {
                    applyKnightsTourSettings(8, knightsTourStartPos);
                    refreshStyles.run();
                }
            });
        }
        if (btnCorner != null) {
            btnCorner.setOnClickListener(v -> {
                if (knightsTourStartPos != 1) {
                    applyKnightsTourSettings(knightsTourBoardSize, 1);
                    refreshStyles.run();
                }
            });
        }
        if (btnCenter != null) {
            btnCenter.setOnClickListener(v -> {
                if (knightsTourStartPos != 2) {
                    applyKnightsTourSettings(knightsTourBoardSize, 2);
                    refreshStyles.run();
                }
            });
        }
        if (btnClose != null) {
            btnClose.setOnClickListener(v -> dialog.dismiss());
        }

        dialog.show();
    }

    private void applyKnightsTourSettings(int size, int startPos) {
        knightsTourBoardSize = size;
        knightsTourStartPos = startPos;
        com.dsavisualizer.app.data.AppPreferences prefs = new com.dsavisualizer.app.data.AppPreferences(requireContext());
        prefs.setKnightsTourBoardSize(size);
        prefs.setKnightsTourStartPos(startPos);
        resetVisualization();
        bindCurrentState("Knight's Tour (" + size + "×" + size + ", " + (startPos == 2 ? "Center" : "Corner") + ") ready. Tap Play or Step to explore.");
        updateProgressHandle(0, estimatePlaybackTotalSteps());
        updatePlayerProgress(PlaybackState.initial());
        if (visualizerView != null) {
            visualizerView.invalidate();
        }
    }

    private void openMazeSettingsDialog() {
        if (!isAdded()) {
            return;
        }
        android.app.Dialog dialog = new android.app.Dialog(requireContext());
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_maze_settings, null, false);
        dialog.setContentView(view);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));
            int dialogWidth = (int) (getResources().getDisplayMetrics().widthPixels * 0.92f);
            dialog.getWindow().setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        MaterialButton btn4 = view.findViewById(R.id.maze_size_4);
        MaterialButton btn5 = view.findViewById(R.id.maze_size_5);
        MaterialButton btn6 = view.findViewById(R.id.maze_size_6);
        MaterialButton btnPreset1 = view.findViewById(R.id.maze_preset_1);
        MaterialButton btnPreset2 = view.findViewById(R.id.maze_preset_2);
        MaterialButton btnClose = view.findViewById(R.id.maze_btn_close);

        int selectedColor = android.graphics.Color.parseColor("#7C3AED");
        int unselectedBg = android.graphics.Color.parseColor("#131B2E");
        int unselectedStroke = android.graphics.Color.parseColor("#27354E");
        int textWhite = android.graphics.Color.parseColor("#FFFFFF");
        int textMuted = android.graphics.Color.parseColor("#E2E8F0");

        Runnable refreshStyles = () -> {
            if (btn4 != null) {
                boolean is4 = (mazeSize == 4);
                btn4.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is4 ? selectedColor : unselectedBg));
                btn4.setTextColor(is4 ? textWhite : textMuted);
                btn4.setStrokeColor(android.content.res.ColorStateList.valueOf(is4 ? selectedColor : unselectedStroke));
                btn4.setStrokeWidth(is4 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btn5 != null) {
                boolean is5 = (mazeSize == 5);
                btn5.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is5 ? selectedColor : unselectedBg));
                btn5.setTextColor(is5 ? textWhite : textMuted);
                btn5.setStrokeColor(android.content.res.ColorStateList.valueOf(is5 ? selectedColor : unselectedStroke));
                btn5.setStrokeWidth(is5 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btn6 != null) {
                boolean is6 = (mazeSize == 6);
                btn6.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is6 ? selectedColor : unselectedBg));
                btn6.setTextColor(is6 ? textWhite : textMuted);
                btn6.setStrokeColor(android.content.res.ColorStateList.valueOf(is6 ? selectedColor : unselectedStroke));
                btn6.setStrokeWidth(is6 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btnPreset1 != null) {
                boolean isP1 = (mazePreset == 1);
                btnPreset1.setBackgroundTintList(android.content.res.ColorStateList.valueOf(isP1 ? selectedColor : unselectedBg));
                btnPreset1.setTextColor(isP1 ? textWhite : textMuted);
                btnPreset1.setStrokeColor(android.content.res.ColorStateList.valueOf(isP1 ? selectedColor : unselectedStroke));
                btnPreset1.setStrokeWidth(isP1 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btnPreset2 != null) {
                boolean isP2 = (mazePreset == 2);
                btnPreset2.setBackgroundTintList(android.content.res.ColorStateList.valueOf(isP2 ? selectedColor : unselectedBg));
                btnPreset2.setTextColor(isP2 ? textWhite : textMuted);
                btnPreset2.setStrokeColor(android.content.res.ColorStateList.valueOf(isP2 ? selectedColor : unselectedStroke));
                btnPreset2.setStrokeWidth(isP2 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
        };

        refreshStyles.run();

        if (btn4 != null) {
            btn4.setOnClickListener(v -> {
                if (mazeSize != 4) {
                    applyMazeSettings(4, mazePreset);
                    refreshStyles.run();
                }
            });
        }
        if (btn5 != null) {
            btn5.setOnClickListener(v -> {
                if (mazeSize != 5) {
                    applyMazeSettings(5, mazePreset);
                    refreshStyles.run();
                }
            });
        }
        if (btn6 != null) {
            btn6.setOnClickListener(v -> {
                if (mazeSize != 6) {
                    applyMazeSettings(6, mazePreset);
                    refreshStyles.run();
                }
            });
        }
        if (btnPreset1 != null) {
            btnPreset1.setOnClickListener(v -> {
                if (mazePreset != 1) {
                    applyMazeSettings(mazeSize, 1);
                    refreshStyles.run();
                }
            });
        }
        if (btnPreset2 != null) {
            btnPreset2.setOnClickListener(v -> {
                if (mazePreset != 2) {
                    applyMazeSettings(mazeSize, 2);
                    refreshStyles.run();
                }
            });
        }
        if (btnClose != null) {
            btnClose.setOnClickListener(v -> dialog.dismiss());
        }

        dialog.show();
    }

    private void applyMazeSettings(int size, int preset) {
        mazeSize = size;
        mazePreset = preset;
        com.dsavisualizer.app.data.AppPreferences prefs = new com.dsavisualizer.app.data.AppPreferences(requireContext());
        prefs.setMazeSize(size);
        prefs.setMazePreset(preset);
        resetVisualization();
        matrixValues = com.dsavisualizer.app.ui.visualization.runtime.BacktrackingStepEngine.getInitialMaze(size, preset);
        matrixK = -1;
        matrixRow = 0;
        matrixCol = 0;
        matrixLabel = "Rat in a Maze " + size + "x" + size + " (Preset " + preset + ")";
        if (visualizerView != null) {
            visualizerView.setMatrixState(matrixValues, matrixK, matrixRow, matrixCol, matrixLabel);
            visualizerView.invalidate();
        }
        bindCurrentState("Rat in a Maze (" + size + "×" + size + ", Preset " + preset + ") ready. Tap Play or Step to explore.");
        updateProgressHandle(0, estimatePlaybackTotalSteps());
        updatePlayerProgress(PlaybackState.initial());
    }

    private void openHamiltonianSettingsDialog() {
        if (!isAdded()) {
            return;
        }
        android.app.Dialog dialog = new android.app.Dialog(requireContext());
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_hamiltonian_settings, null, false);
        dialog.setContentView(view);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));
            int dialogWidth = (int) (getResources().getDisplayMetrics().widthPixels * 0.92f);
            dialog.getWindow().setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        MaterialButton btn4 = view.findViewById(R.id.hamiltonian_nodes_4);
        MaterialButton btn5 = view.findViewById(R.id.hamiltonian_nodes_5);
        MaterialButton btn6 = view.findViewById(R.id.hamiltonian_nodes_6);
        MaterialButton btnCycle = view.findViewById(R.id.hamiltonian_type_cycle);
        MaterialButton btnPath = view.findViewById(R.id.hamiltonian_type_path);
        MaterialButton btnClose = view.findViewById(R.id.hamiltonian_btn_close);

        int selectedColor = android.graphics.Color.parseColor("#7C3AED");
        int unselectedBg = android.graphics.Color.parseColor("#131B2E");
        int unselectedStroke = android.graphics.Color.parseColor("#27354E");
        int textWhite = android.graphics.Color.parseColor("#FFFFFF");
        int textMuted = android.graphics.Color.parseColor("#E2E8F0");

        Runnable refreshStyles = () -> {
            if (btn4 != null) {
                boolean is4 = (hamiltonianNodes == 4);
                btn4.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is4 ? selectedColor : unselectedBg));
                btn4.setTextColor(is4 ? textWhite : textMuted);
                btn4.setStrokeColor(android.content.res.ColorStateList.valueOf(is4 ? selectedColor : unselectedStroke));
                btn4.setStrokeWidth(is4 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btn5 != null) {
                boolean is5 = (hamiltonianNodes == 5);
                btn5.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is5 ? selectedColor : unselectedBg));
                btn5.setTextColor(is5 ? textWhite : textMuted);
                btn5.setStrokeColor(android.content.res.ColorStateList.valueOf(is5 ? selectedColor : unselectedStroke));
                btn5.setStrokeWidth(is5 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btn6 != null) {
                boolean is6 = (hamiltonianNodes == 6);
                btn6.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is6 ? selectedColor : unselectedBg));
                btn6.setTextColor(is6 ? textWhite : textMuted);
                btn6.setStrokeColor(android.content.res.ColorStateList.valueOf(is6 ? selectedColor : unselectedStroke));
                btn6.setStrokeWidth(is6 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btnCycle != null) {
                boolean isCycle = (hamiltonianType == 1);
                btnCycle.setBackgroundTintList(android.content.res.ColorStateList.valueOf(isCycle ? selectedColor : unselectedBg));
                btnCycle.setTextColor(isCycle ? textWhite : textMuted);
                btnCycle.setStrokeColor(android.content.res.ColorStateList.valueOf(isCycle ? selectedColor : unselectedStroke));
                btnCycle.setStrokeWidth(isCycle ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btnPath != null) {
                boolean isPath = (hamiltonianType == 2);
                btnPath.setBackgroundTintList(android.content.res.ColorStateList.valueOf(isPath ? selectedColor : unselectedBg));
                btnPath.setTextColor(isPath ? textWhite : textMuted);
                btnPath.setStrokeColor(android.content.res.ColorStateList.valueOf(isPath ? selectedColor : unselectedStroke));
                btnPath.setStrokeWidth(isPath ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
        };

        refreshStyles.run();

        if (btn4 != null) {
            btn4.setOnClickListener(v -> {
                if (hamiltonianNodes != 4) {
                    applyHamiltonianSettings(4, hamiltonianType);
                    refreshStyles.run();
                }
            });
        }
        if (btn5 != null) {
            btn5.setOnClickListener(v -> {
                if (hamiltonianNodes != 5) {
                    applyHamiltonianSettings(5, hamiltonianType);
                    refreshStyles.run();
                }
            });
        }
        if (btn6 != null) {
            btn6.setOnClickListener(v -> {
                if (hamiltonianNodes != 6) {
                    applyHamiltonianSettings(6, hamiltonianType);
                    refreshStyles.run();
                }
            });
        }
        if (btnCycle != null) {
            btnCycle.setOnClickListener(v -> {
                if (hamiltonianType != 1) {
                    applyHamiltonianSettings(hamiltonianNodes, 1);
                    refreshStyles.run();
                }
            });
        }
        if (btnPath != null) {
            btnPath.setOnClickListener(v -> {
                if (hamiltonianType != 2) {
                    applyHamiltonianSettings(hamiltonianNodes, 2);
                    refreshStyles.run();
                }
            });
        }
        if (btnClose != null) {
            btnClose.setOnClickListener(v -> dialog.dismiss());
        }

        dialog.show();
    }

    private void applyHamiltonianSettings(int nodes, int type) {
        hamiltonianNodes = nodes;
        hamiltonianType = type;
        com.dsavisualizer.app.data.AppPreferences prefs = new com.dsavisualizer.app.data.AppPreferences(requireContext());
        prefs.setHamiltonianNodes(nodes);
        prefs.setHamiltonianType(type);
        resetVisualization();
        workingValues = new int[nodes];
        for (int i = 0; i < nodes; i++) workingValues[i] = i;
        matrixLabel = "Hamiltonian " + (type == 1 ? "Cycle" : "Path") + " (" + nodes + " Vertices)";
        if (visualizerView != null) {
            visualizerView.setTraversalState(workingValues, new int[0], -1);
            visualizerView.clearActiveEdge();
            visualizerView.invalidate();
        }
        bindCurrentState("Hamiltonian " + (type == 1 ? "Cycle" : "Path") + " (" + nodes + " Vertices) ready. Tap Play or Step to explore.");
        updateProgressHandle(0, estimatePlaybackTotalSteps());
        updatePlayerProgress(PlaybackState.initial());
    }

    private void openGraphColoringSettingsDialog() {
        if (!isAdded()) {
            return;
        }
        android.app.Dialog dialog = new android.app.Dialog(requireContext());
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_graph_coloring_settings, null, false);
        dialog.setContentView(view);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));
            int dialogWidth = (int) (getResources().getDisplayMetrics().widthPixels * 0.92f);
            dialog.getWindow().setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        MaterialButton btnM2 = view.findViewById(R.id.coloring_m_2);
        MaterialButton btnM3 = view.findViewById(R.id.coloring_m_3);
        MaterialButton btnM4 = view.findViewById(R.id.coloring_m_4);
        MaterialButton btnPreset1 = view.findViewById(R.id.coloring_preset_1);
        MaterialButton btnPreset2 = view.findViewById(R.id.coloring_preset_2);
        MaterialButton btnPreset3 = view.findViewById(R.id.coloring_preset_3);
        MaterialButton btnClose = view.findViewById(R.id.coloring_btn_close);

        int selectedColor = android.graphics.Color.parseColor("#7C3AED");
        int unselectedBg = android.graphics.Color.parseColor("#131B2E");
        int unselectedStroke = android.graphics.Color.parseColor("#27354E");
        int textWhite = android.graphics.Color.parseColor("#FFFFFF");
        int textMuted = android.graphics.Color.parseColor("#E2E8F0");

        Runnable refreshStyles = () -> {
            if (btnM2 != null) {
                boolean is2 = (graphColoringColors == 2);
                btnM2.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is2 ? selectedColor : unselectedBg));
                btnM2.setTextColor(is2 ? textWhite : textMuted);
                btnM2.setStrokeColor(android.content.res.ColorStateList.valueOf(is2 ? selectedColor : unselectedStroke));
                btnM2.setStrokeWidth(is2 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btnM3 != null) {
                boolean is3 = (graphColoringColors == 3);
                btnM3.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is3 ? selectedColor : unselectedBg));
                btnM3.setTextColor(is3 ? textWhite : textMuted);
                btnM3.setStrokeColor(android.content.res.ColorStateList.valueOf(is3 ? selectedColor : unselectedStroke));
                btnM3.setStrokeWidth(is3 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btnM4 != null) {
                boolean is4 = (graphColoringColors == 4);
                btnM4.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is4 ? selectedColor : unselectedBg));
                btnM4.setTextColor(is4 ? textWhite : textMuted);
                btnM4.setStrokeColor(android.content.res.ColorStateList.valueOf(is4 ? selectedColor : unselectedStroke));
                btnM4.setStrokeWidth(is4 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btnPreset1 != null) {
                boolean isP1 = (graphColoringPreset == 1);
                btnPreset1.setBackgroundTintList(android.content.res.ColorStateList.valueOf(isP1 ? selectedColor : unselectedBg));
                btnPreset1.setTextColor(isP1 ? textWhite : textMuted);
                btnPreset1.setStrokeColor(android.content.res.ColorStateList.valueOf(isP1 ? selectedColor : unselectedStroke));
                btnPreset1.setStrokeWidth(isP1 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btnPreset2 != null) {
                boolean isP2 = (graphColoringPreset == 2);
                btnPreset2.setBackgroundTintList(android.content.res.ColorStateList.valueOf(isP2 ? selectedColor : unselectedBg));
                btnPreset2.setTextColor(isP2 ? textWhite : textMuted);
                btnPreset2.setStrokeColor(android.content.res.ColorStateList.valueOf(isP2 ? selectedColor : unselectedStroke));
                btnPreset2.setStrokeWidth(isP2 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btnPreset3 != null) {
                boolean isP3 = (graphColoringPreset == 3);
                btnPreset3.setBackgroundTintList(android.content.res.ColorStateList.valueOf(isP3 ? selectedColor : unselectedBg));
                btnPreset3.setTextColor(isP3 ? textWhite : textMuted);
                btnPreset3.setStrokeColor(android.content.res.ColorStateList.valueOf(isP3 ? selectedColor : unselectedStroke));
                btnPreset3.setStrokeWidth(isP3 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
        };

        refreshStyles.run();

        if (btnM2 != null) {
            btnM2.setOnClickListener(v -> {
                if (graphColoringColors != 2) {
                    applyGraphColoringSettings(2, graphColoringPreset);
                    refreshStyles.run();
                }
            });
        }
        if (btnM3 != null) {
            btnM3.setOnClickListener(v -> {
                if (graphColoringColors != 3) {
                    applyGraphColoringSettings(3, graphColoringPreset);
                    refreshStyles.run();
                }
            });
        }
        if (btnM4 != null) {
            btnM4.setOnClickListener(v -> {
                if (graphColoringColors != 4) {
                    applyGraphColoringSettings(4, graphColoringPreset);
                    refreshStyles.run();
                }
            });
        }
        if (btnPreset1 != null) {
            btnPreset1.setOnClickListener(v -> {
                if (graphColoringPreset != 1) {
                    applyGraphColoringSettings(graphColoringColors, 1);
                    refreshStyles.run();
                }
            });
        }
        if (btnPreset2 != null) {
            btnPreset2.setOnClickListener(v -> {
                if (graphColoringPreset != 2) {
                    applyGraphColoringSettings(graphColoringColors, 2);
                    refreshStyles.run();
                }
            });
        }
        if (btnPreset3 != null) {
            btnPreset3.setOnClickListener(v -> {
                if (graphColoringPreset != 3) {
                    applyGraphColoringSettings(graphColoringColors, 3);
                    refreshStyles.run();
                }
            });
        }
        if (btnClose != null) {
            btnClose.setOnClickListener(v -> dialog.dismiss());
        }

        dialog.show();
    }

    private void applyGraphColoringSettings(int colors, int preset) {
        graphColoringColors = colors;
        graphColoringPreset = preset;
        com.dsavisualizer.app.data.AppPreferences prefs = new com.dsavisualizer.app.data.AppPreferences(requireContext());
        prefs.setGraphColoringColors(colors);
        prefs.setGraphColoringPreset(preset);
        resetVisualization();
        int n = com.dsavisualizer.app.ui.visualization.runtime.BacktrackingStepEngine.getColoringGraphNodeCount(preset);
        workingValues = new int[n];
        matrixLabel = "Graph m-Coloring (m=" + colors + ", Preset " + preset + ")";
        if (visualizerView != null) {
            visualizerView.setArrayState(workingValues, -1, -1, -1);
            visualizerView.clearActiveEdge();
            visualizerView.invalidate();
        }
        bindCurrentState("Graph " + colors + "-Coloring (Preset " + preset + ") ready. Tap Play or Step to explore.");
        updateProgressHandle(0, estimatePlaybackTotalSteps());
        updatePlayerProgress(PlaybackState.initial());
    }

    private void openDpSettingsDialog() {
        if (!isAdded()) {
            return;
        }
        android.app.Dialog dialog = new android.app.Dialog(requireContext());
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_dp_settings, null, false);
        dialog.setContentView(view);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));
            int dialogWidth = (int) (getResources().getDisplayMetrics().widthPixels * 0.92f);
            dialog.getWindow().setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        TextView titleView = view.findViewById(R.id.dp_settings_title);
        TextView descDetail = view.findViewById(R.id.dp_preset_desc_detail);
        MaterialButton btnP1 = view.findViewById(R.id.dp_preset_1);
        MaterialButton btnP2 = view.findViewById(R.id.dp_preset_2);
        MaterialButton btnP3 = view.findViewById(R.id.dp_preset_3);
        MaterialButton btnClose = view.findViewById(R.id.dp_btn_close);

        if (titleView != null && techniqueTitle != null) {
            titleView.setText(techniqueTitle + " Setup");
        }

        int selectedColor = android.graphics.Color.parseColor("#7C3AED");
        int unselectedBg = android.graphics.Color.parseColor("#131B2E");
        int unselectedStroke = android.graphics.Color.parseColor("#27354E");
        int textWhite = android.graphics.Color.parseColor("#FFFFFF");
        int textMuted = android.graphics.Color.parseColor("#E2E8F0");

        Runnable refreshStyles = () -> {
            if (descDetail != null) {
                descDetail.setText(com.dsavisualizer.app.ui.visualization.runtime.DpStepEngine.getPresetDescription(techniqueId, dpPreset));
            }
            if (btnP1 != null) {
                boolean is1 = (dpPreset == 1);
                btnP1.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is1 ? selectedColor : unselectedBg));
                btnP1.setTextColor(is1 ? textWhite : textMuted);
                btnP1.setStrokeColor(android.content.res.ColorStateList.valueOf(is1 ? selectedColor : unselectedStroke));
                btnP1.setStrokeWidth(is1 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btnP2 != null) {
                boolean is2 = (dpPreset == 2);
                btnP2.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is2 ? selectedColor : unselectedBg));
                btnP2.setTextColor(is2 ? textWhite : textMuted);
                btnP2.setStrokeColor(android.content.res.ColorStateList.valueOf(is2 ? selectedColor : unselectedStroke));
                btnP2.setStrokeWidth(is2 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btnP3 != null) {
                boolean is3 = (dpPreset == 3);
                btnP3.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is3 ? selectedColor : unselectedBg));
                btnP3.setTextColor(is3 ? textWhite : textMuted);
                btnP3.setStrokeColor(android.content.res.ColorStateList.valueOf(is3 ? selectedColor : unselectedStroke));
                btnP3.setStrokeWidth(is3 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
        };

        refreshStyles.run();

        if (btnP1 != null) {
            btnP1.setOnClickListener(v -> {
                if (dpPreset != 1) {
                    applyDpSettings(1);
                    refreshStyles.run();
                }
            });
        }
        if (btnP2 != null) {
            btnP2.setOnClickListener(v -> {
                if (dpPreset != 2) {
                    applyDpSettings(2);
                    refreshStyles.run();
                }
            });
        }
        if (btnP3 != null) {
            btnP3.setOnClickListener(v -> {
                if (dpPreset != 3) {
                    applyDpSettings(3);
                    refreshStyles.run();
                }
            });
        }
        if (btnClose != null) {
            btnClose.setOnClickListener(v -> dialog.dismiss());
        }

        dialog.show();
    }

    private void applyDpSettings(int preset) {
        dpPreset = preset;
        target = preset;
        com.dsavisualizer.app.data.AppPreferences prefs = new com.dsavisualizer.app.data.AppPreferences(requireContext());
        prefs.setDpPreset(techniqueId, preset);
        resetVisualization();
        initializeCustomSortPreview();
        bindCurrentState("DP Configuration (Preset " + preset + ") applied. Tap Play or Step to visualize.");
        updateProgressHandle(0, estimatePlaybackTotalSteps());
        updatePlayerProgress(PlaybackState.initial());
    }

    private void openStringPatternSettingsDialog() {
        if (!isAdded()) {
            return;
        }
        android.app.Dialog dialog = new android.app.Dialog(requireContext());
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_string_pattern_settings, null, false);
        dialog.setContentView(view);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));
            int dialogWidth = (int) (getResources().getDisplayMetrics().widthPixels * 0.92f);
            dialog.getWindow().setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        TextView titleView = view.findViewById(R.id.strpat_settings_title);
        TextView descDetail = view.findViewById(R.id.strpat_preset_desc_detail);
        MaterialButton btnP1 = view.findViewById(R.id.strpat_preset_1);
        MaterialButton btnP2 = view.findViewById(R.id.strpat_preset_2);
        MaterialButton btnP3 = view.findViewById(R.id.strpat_preset_3);
        com.google.android.material.textfield.TextInputEditText textInput = view.findViewById(R.id.strpat_text_input);
        com.google.android.material.textfield.TextInputEditText queryInput = view.findViewById(R.id.strpat_query_input);
        MaterialButton btnClose = view.findViewById(R.id.strpat_btn_close);

        if (titleView != null && techniqueTitle != null) {
            titleView.setText(techniqueTitle + " Setup");
        }

        if (textInput != null) {
            textInput.setText(patternText != null ? patternText : "");
        }
        if (queryInput != null) {
            queryInput.setText(patternQuery != null ? patternQuery : "");
        }

        int selectedColor = android.graphics.Color.parseColor("#0891B2");
        int unselectedBg = android.graphics.Color.parseColor("#131B2E");
        int unselectedStroke = android.graphics.Color.parseColor("#27354E");
        int textWhite = android.graphics.Color.parseColor("#FFFFFF");
        int textMuted = android.graphics.Color.parseColor("#E2E8F0");

        Runnable refreshStyles = () -> {
            if (descDetail != null) {
                descDetail.setText(com.dsavisualizer.app.ui.visualization.runtime.StringPatternStepEngine.getPresetDescription(techniqueId, stringPatternPreset));
            }
            if (btnP1 != null) {
                boolean is1 = (stringPatternPreset == 1);
                btnP1.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is1 ? selectedColor : unselectedBg));
                btnP1.setTextColor(is1 ? textWhite : textMuted);
                btnP1.setStrokeColor(android.content.res.ColorStateList.valueOf(is1 ? selectedColor : unselectedStroke));
                btnP1.setStrokeWidth(is1 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btnP2 != null) {
                boolean is2 = (stringPatternPreset == 2);
                btnP2.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is2 ? selectedColor : unselectedBg));
                btnP2.setTextColor(is2 ? textWhite : textMuted);
                btnP2.setStrokeColor(android.content.res.ColorStateList.valueOf(is2 ? selectedColor : unselectedStroke));
                btnP2.setStrokeWidth(is2 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btnP3 != null) {
                boolean is3 = (stringPatternPreset == 3);
                btnP3.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is3 ? selectedColor : unselectedBg));
                btnP3.setTextColor(is3 ? textWhite : textMuted);
                btnP3.setStrokeColor(android.content.res.ColorStateList.valueOf(is3 ? selectedColor : unselectedStroke));
                btnP3.setStrokeWidth(is3 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
        };

        refreshStyles.run();

        if (btnP1 != null) {
            btnP1.setOnClickListener(v -> {
                stringPatternPreset = 1;
                if (textInput != null) {
                    textInput.setText(com.dsavisualizer.app.ui.visualization.runtime.StringPatternStepEngine.getPresetText(techniqueId, 1));
                }
                if (queryInput != null) {
                    queryInput.setText(com.dsavisualizer.app.ui.visualization.runtime.StringPatternStepEngine.getPresetQuery(techniqueId, 1));
                }
                refreshStyles.run();
            });
        }
        if (btnP2 != null) {
            btnP2.setOnClickListener(v -> {
                stringPatternPreset = 2;
                if (textInput != null) {
                    textInput.setText(com.dsavisualizer.app.ui.visualization.runtime.StringPatternStepEngine.getPresetText(techniqueId, 2));
                }
                if (queryInput != null) {
                    queryInput.setText(com.dsavisualizer.app.ui.visualization.runtime.StringPatternStepEngine.getPresetQuery(techniqueId, 2));
                }
                refreshStyles.run();
            });
        }
        if (btnP3 != null) {
            btnP3.setOnClickListener(v -> {
                stringPatternPreset = 3;
                if (textInput != null) {
                    textInput.setText(com.dsavisualizer.app.ui.visualization.runtime.StringPatternStepEngine.getPresetText(techniqueId, 3));
                }
                if (queryInput != null) {
                    queryInput.setText(com.dsavisualizer.app.ui.visualization.runtime.StringPatternStepEngine.getPresetQuery(techniqueId, 3));
                }
                refreshStyles.run();
            });
        }
        if (btnClose != null) {
            btnClose.setOnClickListener(v -> {
                String customText = (textInput != null && textInput.getText() != null)
                        ? textInput.getText().toString().trim() : "";
                String customQuery = (queryInput != null && queryInput.getText() != null)
                        ? queryInput.getText().toString().trim() : "";
                if (customText.isEmpty()) {
                    customText = com.dsavisualizer.app.ui.visualization.runtime.StringPatternStepEngine.getPresetText(techniqueId, stringPatternPreset);
                }
                if (customQuery.isEmpty() && !"string_manacher".equals(techniqueId) && !"string_suffix_array".equals(techniqueId)) {
                    customQuery = com.dsavisualizer.app.ui.visualization.runtime.StringPatternStepEngine.getPresetQuery(techniqueId, stringPatternPreset);
                }
                applyStringPatternSettings(stringPatternPreset, customText, customQuery);
                dialog.dismiss();
            });
        }

        dialog.show();
    }

    private void applyStringPatternSettings(int preset, String customText, String customQuery) {
        stringPatternPreset = preset;
        patternText = customText;
        patternQuery = customQuery;
        com.dsavisualizer.app.data.AppPreferences prefs = new com.dsavisualizer.app.data.AppPreferences(requireContext());
        prefs.setStringPatternPreset(techniqueId, preset);
        prefs.setStringPatternText(techniqueId, patternText);
        prefs.setStringPatternQuery(techniqueId, patternQuery);

        if (valuesInput != null) {
            valuesInput.setText(patternText);
        }
        if (targetInput != null) {
            targetInput.setText(patternQuery);
        }

        resetVisualization();
        if (visualizerView != null) {
            visualizerView.setPatternState(patternText, patternQuery, 0, 0, false);
            visualizerView.invalidate();
        }
        bindCurrentState("String Pattern Setup applied. Tap Play or Step to visualize.");
        updateProgressHandle(0, estimatePlaybackTotalSteps());
        updatePlayerProgress(PlaybackState.initial());
    }

    private void openGraphSettingsDialog() {
        if (!isAdded()) {
            return;
        }
        android.app.Dialog dialog = new android.app.Dialog(requireContext());
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_graph_settings, null, false);
        dialog.setContentView(view);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));
            int dialogWidth = (int) (getResources().getDisplayMetrics().widthPixels * 0.92f);
            dialog.getWindow().setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        TextView titleView = view.findViewById(R.id.graph_settings_title);
        TextView descDetail = view.findViewById(R.id.graph_preset_desc_detail);
        MaterialButton btnP1 = view.findViewById(R.id.graph_preset_1);
        MaterialButton btnP2 = view.findViewById(R.id.graph_preset_2);
        MaterialButton btnP3 = view.findViewById(R.id.graph_preset_3);

        MaterialButton btnN0 = view.findViewById(R.id.graph_node_0);
        MaterialButton btnN1 = view.findViewById(R.id.graph_node_1);
        MaterialButton btnN2 = view.findViewById(R.id.graph_node_2);
        MaterialButton btnN3 = view.findViewById(R.id.graph_node_3);
        MaterialButton btnN4 = view.findViewById(R.id.graph_node_4);
        MaterialButton btnN5 = view.findViewById(R.id.graph_node_5);
        MaterialButton[] nodeButtons = {btnN0, btnN1, btnN2, btnN3, btnN4, btnN5};

        MaterialButton btnC4 = view.findViewById(R.id.graph_count_4);
        MaterialButton btnC5 = view.findViewById(R.id.graph_count_5);
        MaterialButton btnC6 = view.findViewById(R.id.graph_count_6);
        MaterialButton btnClose = view.findViewById(R.id.graph_btn_close);

        if (titleView != null && techniqueTitle != null) {
            titleView.setText(techniqueTitle + " Setup");
        }

        final int[] tempPreset = {graphPreset};
        final int[] tempStart = {graphStartNode};
        final int[] tempCount = {graphNodeCount};

        int selectedColor = android.graphics.Color.parseColor("#DC2626");
        int unselectedBg = android.graphics.Color.parseColor("#131B2E");
        int unselectedStroke = android.graphics.Color.parseColor("#27354E");
        int textWhite = android.graphics.Color.parseColor("#FFFFFF");
        int textMuted = android.graphics.Color.parseColor("#E2E8F0");

        Runnable refreshStyles = () -> {
            int p = tempPreset[0];
            int s = tempStart[0];
            int c = tempCount[0];

            if (descDetail != null) {
                descDetail.setText(com.dsavisualizer.app.ui.visualization.runtime.GraphStepEngine.getPresetDescription(techniqueId, p, s, c));
            }

            // Presets
            if (btnP1 != null) {
                boolean is1 = (p == 1);
                btnP1.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is1 ? selectedColor : unselectedBg));
                btnP1.setTextColor(is1 ? textWhite : textMuted);
                btnP1.setStrokeColor(android.content.res.ColorStateList.valueOf(is1 ? selectedColor : unselectedStroke));
                btnP1.setStrokeWidth(is1 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btnP2 != null) {
                boolean is2 = (p == 2);
                btnP2.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is2 ? selectedColor : unselectedBg));
                btnP2.setTextColor(is2 ? textWhite : textMuted);
                btnP2.setStrokeColor(android.content.res.ColorStateList.valueOf(is2 ? selectedColor : unselectedStroke));
                btnP2.setStrokeWidth(is2 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btnP3 != null) {
                boolean is3 = (p == 3);
                btnP3.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is3 ? selectedColor : unselectedBg));
                btnP3.setTextColor(is3 ? textWhite : textMuted);
                btnP3.setStrokeColor(android.content.res.ColorStateList.valueOf(is3 ? selectedColor : unselectedStroke));
                btnP3.setStrokeWidth(is3 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }

            // Start Nodes
            for (int i = 0; i < nodeButtons.length; i++) {
                MaterialButton btn = nodeButtons[i];
                if (btn != null) {
                    if (i >= c) {
                        btn.setVisibility(View.GONE);
                    } else {
                        btn.setVisibility(View.VISIBLE);
                        boolean isSelected = (i == s);
                        btn.setBackgroundTintList(android.content.res.ColorStateList.valueOf(isSelected ? selectedColor : unselectedBg));
                        btn.setTextColor(isSelected ? textWhite : textMuted);
                        btn.setStrokeColor(android.content.res.ColorStateList.valueOf(isSelected ? selectedColor : unselectedStroke));
                        btn.setStrokeWidth(isSelected ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
                    }
                }
            }

            // Node count buttons
            if (btnC4 != null) {
                boolean is4 = (c == 4);
                btnC4.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is4 ? selectedColor : unselectedBg));
                btnC4.setTextColor(is4 ? textWhite : textMuted);
                btnC4.setStrokeColor(android.content.res.ColorStateList.valueOf(is4 ? selectedColor : unselectedStroke));
                btnC4.setStrokeWidth(is4 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btnC5 != null) {
                boolean is5 = (c == 5);
                btnC5.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is5 ? selectedColor : unselectedBg));
                btnC5.setTextColor(is5 ? textWhite : textMuted);
                btnC5.setStrokeColor(android.content.res.ColorStateList.valueOf(is5 ? selectedColor : unselectedStroke));
                btnC5.setStrokeWidth(is5 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btnC6 != null) {
                boolean is6 = (c == 6);
                btnC6.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is6 ? selectedColor : unselectedBg));
                btnC6.setTextColor(is6 ? textWhite : textMuted);
                btnC6.setStrokeColor(android.content.res.ColorStateList.valueOf(is6 ? selectedColor : unselectedStroke));
                btnC6.setStrokeWidth(is6 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
        };

        refreshStyles.run();

        if (btnP1 != null) {
            btnP1.setOnClickListener(v -> {
                tempPreset[0] = 1;
                tempCount[0] = 5;
                if (tempStart[0] >= 5) tempStart[0] = 0;
                refreshStyles.run();
            });
        }
        if (btnP2 != null) {
            btnP2.setOnClickListener(v -> {
                tempPreset[0] = 2;
                tempCount[0] = 6;
                refreshStyles.run();
            });
        }
        if (btnP3 != null) {
            btnP3.setOnClickListener(v -> {
                tempPreset[0] = 3;
                tempCount[0] = 5;
                if (tempStart[0] >= 5) tempStart[0] = 0;
                refreshStyles.run();
            });
        }

        for (int i = 0; i < nodeButtons.length; i++) {
            final int nodeIdx = i;
            if (nodeButtons[i] != null) {
                nodeButtons[i].setOnClickListener(v -> {
                    tempStart[0] = nodeIdx;
                    refreshStyles.run();
                });
            }
        }

        if (btnC4 != null) {
            btnC4.setOnClickListener(v -> {
                tempCount[0] = 4;
                if (tempStart[0] >= 4) tempStart[0] = 0;
                refreshStyles.run();
            });
        }
        if (btnC5 != null) {
            btnC5.setOnClickListener(v -> {
                tempCount[0] = 5;
                if (tempStart[0] >= 5) tempStart[0] = 0;
                refreshStyles.run();
            });
        }
        if (btnC6 != null) {
            btnC6.setOnClickListener(v -> {
                tempCount[0] = 6;
                refreshStyles.run();
            });
        }

        if (btnClose != null) {
            btnClose.setOnClickListener(v -> {
                applyGraphSettings(tempPreset[0], tempStart[0], tempCount[0]);
                dialog.dismiss();
            });
        }

        dialog.show();
    }

    private void applyGraphSettings(int preset, int startNode, int count) {
        graphPreset = preset;
        graphNodeCount = Math.max(4, Math.min(count, 6));
        graphStartNode = Math.max(0, Math.min(startNode, graphNodeCount - 1));

        com.dsavisualizer.app.data.AppPreferences prefs = new com.dsavisualizer.app.data.AppPreferences(requireContext());
        prefs.setGraphPreset(techniqueId, graphPreset);
        prefs.setGraphStartNode(techniqueId, graphStartNode);
        prefs.setGraphNodeCount(techniqueId, graphNodeCount);

        workingValues = new int[graphNodeCount];
        for (int i = 0; i < graphNodeCount; i++) {
            workingValues[i] = i;
        }
        target = graphStartNode;

        resetVisualization();
        if (visualizerView != null) {
            visualizerView.setGraphPreset(graphPreset);
            traversalOrder = buildDynamicGraphTraversal();
            visualizerView.setTraversalState(workingValues, traversalOrder, -1);
            visualizerView.clearActiveEdge();
            visualizerView.invalidate();
        }
        if ("dijkstra_algorithm".equals(techniqueId)) {
            com.dsavisualizer.app.ui.visualization.runtime.DijkstraStepEngine.initializePreview(this);
        } else if ("warshall_algorithm".equals(techniqueId)
            || "floyd_algorithm".equals(techniqueId)
            || "shortest_paths".equals(techniqueId)) {
            initializeCustomSortPreview();
        }
        bindCurrentState("Graph Setup (Preset " + graphPreset + ", Start: Node " + graphStartNode + ") applied. Tap Play or Step to visualize.");
        updateProgressHandle(0, estimatePlaybackTotalSteps());
        updatePlayerProgress(PlaybackState.initial());
    }

    private void openAdvancedDsSettingsDialog() {
        if (!isAdded()) {
            return;
        }
        android.app.Dialog dialog = new android.app.Dialog(requireContext());
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_advanced_ds_settings, null, false);
        dialog.setContentView(view);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));
            int dialogWidth = (int) (getResources().getDisplayMetrics().widthPixels * 0.92f);
            dialog.getWindow().setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        TextView titleView = view.findViewById(R.id.advds_settings_title);
        TextView descDetail = view.findViewById(R.id.advds_preset_desc_detail);
        MaterialButton btnP1 = view.findViewById(R.id.advds_preset_1);
        MaterialButton btnP2 = view.findViewById(R.id.advds_preset_2);
        MaterialButton btnP3 = view.findViewById(R.id.advds_preset_3);
        com.google.android.material.textfield.TextInputEditText customInputView = view.findViewById(R.id.advds_custom_input);
        MaterialButton btnClose = view.findViewById(R.id.advds_btn_close);

        if (titleView != null && techniqueTitle != null) {
            titleView.setText(techniqueTitle + " Setup");
        }

        if (customInputView != null) {
            if (advancedDsCustomInput == null || advancedDsCustomInput.trim().isEmpty()) {
                advancedDsCustomInput = com.dsavisualizer.app.ui.visualization.runtime.AdvancedDsStepEngine.getDefaultInput(techniqueId, advancedDsPreset);
            }
            customInputView.setText(advancedDsCustomInput);
        }

        final int[] tempPreset = {advancedDsPreset};

        int selectedColor = android.graphics.Color.parseColor("#7C3AED");
        int unselectedBg = android.graphics.Color.parseColor("#131B2E");
        int unselectedStroke = android.graphics.Color.parseColor("#27354E");
        int textWhite = android.graphics.Color.parseColor("#FFFFFF");
        int textMuted = android.graphics.Color.parseColor("#E2E8F0");

        Runnable refreshStyles = () -> {
            int p = tempPreset[0];
            if (descDetail != null) {
                descDetail.setText(com.dsavisualizer.app.ui.visualization.runtime.AdvancedDsStepEngine.getPresetDescription(techniqueId, p));
            }
            if (btnP1 != null) {
                boolean is1 = (p == 1);
                btnP1.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is1 ? selectedColor : unselectedBg));
                btnP1.setTextColor(is1 ? textWhite : textMuted);
                btnP1.setStrokeColor(android.content.res.ColorStateList.valueOf(is1 ? selectedColor : unselectedStroke));
                btnP1.setStrokeWidth(is1 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btnP2 != null) {
                boolean is2 = (p == 2);
                btnP2.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is2 ? selectedColor : unselectedBg));
                btnP2.setTextColor(is2 ? textWhite : textMuted);
                btnP2.setStrokeColor(android.content.res.ColorStateList.valueOf(is2 ? selectedColor : unselectedStroke));
                btnP2.setStrokeWidth(is2 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btnP3 != null) {
                boolean is3 = (p == 3);
                btnP3.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is3 ? selectedColor : unselectedBg));
                btnP3.setTextColor(is3 ? textWhite : textMuted);
                btnP3.setStrokeColor(android.content.res.ColorStateList.valueOf(is3 ? selectedColor : unselectedStroke));
                btnP3.setStrokeWidth(is3 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
        };

        refreshStyles.run();

        if (btnP1 != null) {
            btnP1.setOnClickListener(v -> {
                tempPreset[0] = 1;
                if (customInputView != null) {
                    customInputView.setText(com.dsavisualizer.app.ui.visualization.runtime.AdvancedDsStepEngine.getDefaultInput(techniqueId, 1));
                }
                refreshStyles.run();
            });
        }
        if (btnP2 != null) {
            btnP2.setOnClickListener(v -> {
                tempPreset[0] = 2;
                if (customInputView != null) {
                    customInputView.setText(com.dsavisualizer.app.ui.visualization.runtime.AdvancedDsStepEngine.getDefaultInput(techniqueId, 2));
                }
                refreshStyles.run();
            });
        }
        if (btnP3 != null) {
            btnP3.setOnClickListener(v -> {
                tempPreset[0] = 3;
                if (customInputView != null) {
                    customInputView.setText(com.dsavisualizer.app.ui.visualization.runtime.AdvancedDsStepEngine.getDefaultInput(techniqueId, 3));
                }
                refreshStyles.run();
            });
        }

        if (btnClose != null) {
            btnClose.setOnClickListener(v -> {
                String input = customInputView != null && customInputView.getText() != null
                        ? customInputView.getText().toString().trim() : "";
                if (input.isEmpty()) {
                    input = com.dsavisualizer.app.ui.visualization.runtime.AdvancedDsStepEngine.getDefaultInput(techniqueId, tempPreset[0]);
                }
                applyAdvancedDsSettings(tempPreset[0], input);
                dialog.dismiss();
            });
        }

        dialog.show();
    }

    private void applyAdvancedDsSettings(int preset, String customInput) {
        advancedDsPreset = preset;
        advancedDsCustomInput = customInput;
        com.dsavisualizer.app.data.AppPreferences prefs = new com.dsavisualizer.app.data.AppPreferences(requireContext());
        prefs.setAdvancedDsPreset(techniqueId, preset);
        prefs.setAdvancedDsCustomInput(techniqueId, customInput);

        setExpressionInput(customInput);
        if (visualizerView != null) {
            visualizerView.setExpressionInput(customInput);
        }
        if (valuesInput != null) {
            valuesInput.setText(customInput);
        }

        resetVisualization();
        initializeCustomSortPreview();
        if (visualizerView != null) {
            visualizerView.clearPointerState();
            visualizerView.clearActiveEdge();
            visualizerView.invalidate();
        }

        bindCurrentState("Advanced DS Setup (Preset " + preset + ": " + customInput + ") applied. Tap Play or Step to visualize.");
        updateProgressHandle(0, estimatePlaybackTotalSteps());
        updatePlayerProgress(PlaybackState.initial());
    }

    private void openSpecializedSettingsDialog() {
        if (!isAdded()) {
            return;
        }
        android.app.Dialog dialog = new android.app.Dialog(requireContext());
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_specialized_settings, null, false);
        dialog.setContentView(view);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));
            int dialogWidth = (int) (getResources().getDisplayMetrics().widthPixels * 0.92f);
            dialog.getWindow().setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        TextView titleView = view.findViewById(R.id.spec_settings_title);
        TextView descDetail = view.findViewById(R.id.spec_preset_desc_detail);
        MaterialButton btnP1 = view.findViewById(R.id.spec_preset_1);
        MaterialButton btnP2 = view.findViewById(R.id.spec_preset_2);
        MaterialButton btnP3 = view.findViewById(R.id.spec_preset_3);
        com.google.android.material.textfield.TextInputEditText customInputView = view.findViewById(R.id.spec_custom_input);
        MaterialButton btnClose = view.findViewById(R.id.spec_btn_close);

        if (titleView != null && techniqueTitle != null) {
            titleView.setText(techniqueTitle + " Setup");
        }

        if (customInputView != null) {
            if (specializedCustomInput == null || specializedCustomInput.trim().isEmpty()) {
                specializedCustomInput = com.dsavisualizer.app.ui.visualization.runtime.SpecializedStepEngine.getDefaultInput(techniqueId, specializedPreset);
            }
            customInputView.setText(specializedCustomInput);
        }

        final int[] tempPreset = {specializedPreset};

        int selectedColor = android.graphics.Color.parseColor("#10B981");
        int unselectedBg = android.graphics.Color.parseColor("#131B2E");
        int unselectedStroke = android.graphics.Color.parseColor("#27354E");
        int textWhite = android.graphics.Color.parseColor("#FFFFFF");
        int textMuted = android.graphics.Color.parseColor("#E2E8F0");

        Runnable refreshStyles = () -> {
            int p = tempPreset[0];
            if (descDetail != null) {
                descDetail.setText(com.dsavisualizer.app.ui.visualization.runtime.SpecializedStepEngine.getPresetDescription(techniqueId, p));
            }
            if (btnP1 != null) {
                boolean is1 = (p == 1);
                btnP1.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is1 ? selectedColor : unselectedBg));
                btnP1.setTextColor(is1 ? textWhite : textMuted);
                btnP1.setStrokeColor(android.content.res.ColorStateList.valueOf(is1 ? selectedColor : unselectedStroke));
                btnP1.setStrokeWidth(is1 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btnP2 != null) {
                boolean is2 = (p == 2);
                btnP2.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is2 ? selectedColor : unselectedBg));
                btnP2.setTextColor(is2 ? textWhite : textMuted);
                btnP2.setStrokeColor(android.content.res.ColorStateList.valueOf(is2 ? selectedColor : unselectedStroke));
                btnP2.setStrokeWidth(is2 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btnP3 != null) {
                boolean is3 = (p == 3);
                btnP3.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is3 ? selectedColor : unselectedBg));
                btnP3.setTextColor(is3 ? textWhite : textMuted);
                btnP3.setStrokeColor(android.content.res.ColorStateList.valueOf(is3 ? selectedColor : unselectedStroke));
                btnP3.setStrokeWidth(is3 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
        };

        refreshStyles.run();

        if (btnP1 != null) {
            btnP1.setOnClickListener(v -> {
                tempPreset[0] = 1;
                if (customInputView != null) {
                    customInputView.setText(com.dsavisualizer.app.ui.visualization.runtime.SpecializedStepEngine.getDefaultInput(techniqueId, 1));
                }
                refreshStyles.run();
            });
        }
        if (btnP2 != null) {
            btnP2.setOnClickListener(v -> {
                tempPreset[0] = 2;
                if (customInputView != null) {
                    customInputView.setText(com.dsavisualizer.app.ui.visualization.runtime.SpecializedStepEngine.getDefaultInput(techniqueId, 2));
                }
                refreshStyles.run();
            });
        }
        if (btnP3 != null) {
            btnP3.setOnClickListener(v -> {
                tempPreset[0] = 3;
                if (customInputView != null) {
                    customInputView.setText(com.dsavisualizer.app.ui.visualization.runtime.SpecializedStepEngine.getDefaultInput(techniqueId, 3));
                }
                refreshStyles.run();
            });
        }

        if (btnClose != null) {
            btnClose.setOnClickListener(v -> {
                String input = customInputView != null && customInputView.getText() != null
                        ? customInputView.getText().toString().trim() : "";
                if (input.isEmpty()) {
                    input = com.dsavisualizer.app.ui.visualization.runtime.SpecializedStepEngine.getDefaultInput(techniqueId, tempPreset[0]);
                }
                applySpecializedSettings(tempPreset[0], input);
                dialog.dismiss();
            });
        }

        dialog.show();
    }

    private void applySpecializedSettings(int preset, String customInput) {
        specializedPreset = preset;
        specializedCustomInput = customInput;
        com.dsavisualizer.app.data.AppPreferences prefs = new com.dsavisualizer.app.data.AppPreferences(requireContext());
        prefs.setSpecializedPreset(techniqueId, preset);
        prefs.setSpecializedCustomInput(techniqueId, customInput);

        try {
            String[] parts = customInput.split("[,;\\s]+");
            List<Integer> list = new ArrayList<>();
            for (String p : parts) {
                p = p.trim();
                if (!p.isEmpty()) {
                    try {
                        list.add(Integer.parseInt(p));
                    } catch (NumberFormatException ignored) {}
                }
            }
            if (!list.isEmpty()) {
                workingValues = new int[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    workingValues[i] = list.get(i);
                }
            }
        } catch (Exception ignored) {}

        resetVisualization();
        if (visualizerView != null) {
            if (workingValues != null && workingValues.length > 0) {
                visualizerView.setArrayState(workingValues, -1, -1, -1);
            }
            visualizerView.clearPointerState();
            visualizerView.clearActiveEdge();
            visualizerView.invalidate();
        }

        bindCurrentState("Specialized Setup (Preset " + preset + ": " + customInput + ") applied. Tap Play or Step to visualize.");
        updateProgressHandle(0, estimatePlaybackTotalSteps());
        updatePlayerProgress(PlaybackState.initial());
    }

    private void openHuffmanSettingsDialog() {
        if (!isAdded()) {
            return;
        }
        android.app.Dialog dialog = new android.app.Dialog(requireContext());
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_huffman_settings, null, false);
        dialog.setContentView(view);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));
            int dialogWidth = (int) (getResources().getDisplayMetrics().widthPixels * 0.92f);
            dialog.getWindow().setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        TextView titleView = view.findViewById(R.id.huffman_settings_title);
        TextView descDetail = view.findViewById(R.id.huffman_preset_desc_detail);
        MaterialButton btnP1 = view.findViewById(R.id.huffman_preset_1);
        MaterialButton btnP2 = view.findViewById(R.id.huffman_preset_2);
        MaterialButton btnP3 = view.findViewById(R.id.huffman_preset_3);
        com.google.android.material.textfield.TextInputEditText customInputView = view.findViewById(R.id.huffman_custom_input);
        MaterialButton btnClose = view.findViewById(R.id.huffman_btn_close);

        if (titleView != null && techniqueTitle != null) {
            titleView.setText(techniqueTitle + " Setup");
        }

        if (customInputView != null) {
            if (huffmanCustomInput == null || huffmanCustomInput.trim().isEmpty()) {
                huffmanCustomInput = com.dsavisualizer.app.ui.visualization.runtime.HuffmanStepEngine.getDefaultInput(huffmanPreset);
            }
            customInputView.setText(huffmanCustomInput);
        }

        final int[] tempPreset = {huffmanPreset};

        int selectedColor = android.graphics.Color.parseColor("#F59E0B");
        int unselectedBg = android.graphics.Color.parseColor("#131B2E");
        int unselectedStroke = android.graphics.Color.parseColor("#27354E");
        int textWhite = android.graphics.Color.parseColor("#FFFFFF");
        int textMuted = android.graphics.Color.parseColor("#E2E8F0");

        Runnable refreshStyles = () -> {
            int p = tempPreset[0];
            if (descDetail != null) {
                descDetail.setText(com.dsavisualizer.app.ui.visualization.runtime.HuffmanStepEngine.getPresetDescription(p));
            }
            if (btnP1 != null) {
                boolean is1 = (p == 1);
                btnP1.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is1 ? selectedColor : unselectedBg));
                btnP1.setTextColor(is1 ? textWhite : textMuted);
                btnP1.setStrokeColor(android.content.res.ColorStateList.valueOf(is1 ? selectedColor : unselectedStroke));
                btnP1.setStrokeWidth(is1 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btnP2 != null) {
                boolean is2 = (p == 2);
                btnP2.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is2 ? selectedColor : unselectedBg));
                btnP2.setTextColor(is2 ? textWhite : textMuted);
                btnP2.setStrokeColor(android.content.res.ColorStateList.valueOf(is2 ? selectedColor : unselectedStroke));
                btnP2.setStrokeWidth(is2 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
            if (btnP3 != null) {
                boolean is3 = (p == 3);
                btnP3.setBackgroundTintList(android.content.res.ColorStateList.valueOf(is3 ? selectedColor : unselectedBg));
                btnP3.setTextColor(is3 ? textWhite : textMuted);
                btnP3.setStrokeColor(android.content.res.ColorStateList.valueOf(is3 ? selectedColor : unselectedStroke));
                btnP3.setStrokeWidth(is3 ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
            }
        };

        refreshStyles.run();

        if (btnP1 != null) {
            btnP1.setOnClickListener(v -> {
                tempPreset[0] = 1;
                if (customInputView != null) {
                    customInputView.setText(com.dsavisualizer.app.ui.visualization.runtime.HuffmanStepEngine.getDefaultInput(1));
                }
                refreshStyles.run();
            });
        }
        if (btnP2 != null) {
            btnP2.setOnClickListener(v -> {
                tempPreset[0] = 2;
                if (customInputView != null) {
                    customInputView.setText(com.dsavisualizer.app.ui.visualization.runtime.HuffmanStepEngine.getDefaultInput(2));
                }
                refreshStyles.run();
            });
        }
        if (btnP3 != null) {
            btnP3.setOnClickListener(v -> {
                tempPreset[0] = 3;
                if (customInputView != null) {
                    customInputView.setText(com.dsavisualizer.app.ui.visualization.runtime.HuffmanStepEngine.getDefaultInput(3));
                }
                refreshStyles.run();
            });
        }

        if (btnClose != null) {
            btnClose.setOnClickListener(v -> {
                String input = customInputView != null && customInputView.getText() != null
                        ? customInputView.getText().toString().trim() : "";
                if (input.isEmpty()) {
                    input = com.dsavisualizer.app.ui.visualization.runtime.HuffmanStepEngine.getDefaultInput(tempPreset[0]);
                }
                applyHuffmanSettings(tempPreset[0], input);
                dialog.dismiss();
            });
        }

        dialog.show();
    }

    private void applyHuffmanSettings(int preset, String customInput) {
        huffmanPreset = preset;
        huffmanCustomInput = customInput;
        com.dsavisualizer.app.data.AppPreferences prefs = new com.dsavisualizer.app.data.AppPreferences(requireContext());
        prefs.setHuffmanPreset(preset);
        prefs.setHuffmanCustomInput(customInput);

        try {
            String[] parts = customInput.split("[,\\s]+");
            List<Integer> list = new ArrayList<>();
            for (String p : parts) {
                p = p.trim();
                if (!p.isEmpty()) {
                    try {
                        int val = Integer.parseInt(p);
                        if (val > 0) {
                            list.add(val);
                        }
                    } catch (NumberFormatException ignored) {}
                }
            }
            if (list.size() >= 2) {
                workingValues = new int[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    workingValues[i] = list.get(i);
                }
            } else {
                workingValues = com.dsavisualizer.app.ui.visualization.runtime.HuffmanStepEngine.getDefaultWeights(preset);
            }
        } catch (Exception ignored) {
            workingValues = com.dsavisualizer.app.ui.visualization.runtime.HuffmanStepEngine.getDefaultWeights(preset);
        }

        resetVisualization();
        initializeHuffmanPreview();

        bindCurrentState("Huffman Coding (" + com.dsavisualizer.app.ui.visualization.runtime.HuffmanStepEngine.getPresetDescription(preset) + ") ready. Tap Play or Step to visualize.");
        updateProgressHandle(0, estimatePlaybackTotalSteps());
        updatePlayerProgress(PlaybackState.initial());
    }

    private void openAvlTreeSettingsDialog() {
        if (!isAdded()) {
            return;
        }
        android.app.Dialog dialog = new android.app.Dialog(requireContext());
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_avl_tree_settings, null, false);
        dialog.setContentView(view);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));
            int dialogWidth = (int) (getResources().getDisplayMetrics().widthPixels * 0.92f);
            dialog.getWindow().setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        TextView titleView = view.findViewById(R.id.avl_settings_title);
        TextView descDetail = view.findViewById(R.id.avl_preset_desc_detail);
        MaterialButton btnP1 = view.findViewById(R.id.avl_preset_1);
        MaterialButton btnP2 = view.findViewById(R.id.avl_preset_2);
        MaterialButton btnP3 = view.findViewById(R.id.avl_preset_3);
        MaterialButton btnP4 = view.findViewById(R.id.avl_preset_4);
        MaterialButton btnP5 = view.findViewById(R.id.avl_preset_5);
        com.google.android.material.textfield.TextInputEditText customInputView = view.findViewById(R.id.avl_custom_input);
        MaterialButton btnRandom = view.findViewById(R.id.avl_btn_random);
        MaterialButton btnApply = view.findViewById(R.id.avl_btn_apply);

        if (titleView != null && techniqueTitle != null) {
            titleView.setText(techniqueTitle + " Setup");
        }

        if (customInputView != null) {
            if (avlTreeCustomInput == null || avlTreeCustomInput.trim().isEmpty()) {
                avlTreeCustomInput = com.dsavisualizer.app.ui.visualization.runtime.AvlTreeStepEngine.getDefaultInput(avlTreePreset);
            }
            customInputView.setText(avlTreeCustomInput);
        }

        final int[] tempPreset = {avlTreePreset};

        int selectedColor = android.graphics.Color.parseColor("#0284C7");
        int unselectedBg = android.graphics.Color.parseColor("#131B2E");
        int unselectedStroke = android.graphics.Color.parseColor("#27354E");
        int textWhite = android.graphics.Color.parseColor("#FFFFFF");
        int textMuted = android.graphics.Color.parseColor("#E2E8F0");

        Runnable refreshStyles = () -> {
            int p = tempPreset[0];
            if (descDetail != null) {
                descDetail.setText(com.dsavisualizer.app.ui.visualization.runtime.AvlTreeStepEngine.getPresetDescription(p));
            }
            MaterialButton[] buttons = {btnP1, btnP2, btnP3, btnP4, btnP5};
            for (int i = 0; i < buttons.length; i++) {
                MaterialButton b = buttons[i];
                if (b != null) {
                    boolean isSel = (p == (i + 1));
                    b.setBackgroundTintList(android.content.res.ColorStateList.valueOf(isSel ? selectedColor : unselectedBg));
                    b.setTextColor(isSel ? textWhite : textMuted);
                    b.setStrokeColor(android.content.res.ColorStateList.valueOf(isSel ? selectedColor : unselectedStroke));
                    b.setStrokeWidth(isSel ? 0 : (int) (1.5f * getResources().getDisplayMetrics().density));
                }
            }
        };

        refreshStyles.run();

        if (btnP1 != null) {
            btnP1.setOnClickListener(v -> {
                tempPreset[0] = 1;
                if (customInputView != null) {
                    customInputView.setText(com.dsavisualizer.app.ui.visualization.runtime.AvlTreeStepEngine.getDefaultInput(1));
                }
                refreshStyles.run();
            });
        }
        if (btnP2 != null) {
            btnP2.setOnClickListener(v -> {
                tempPreset[0] = 2;
                if (customInputView != null) {
                    customInputView.setText(com.dsavisualizer.app.ui.visualization.runtime.AvlTreeStepEngine.getDefaultInput(2));
                }
                refreshStyles.run();
            });
        }
        if (btnP3 != null) {
            btnP3.setOnClickListener(v -> {
                tempPreset[0] = 3;
                if (customInputView != null) {
                    customInputView.setText(com.dsavisualizer.app.ui.visualization.runtime.AvlTreeStepEngine.getDefaultInput(3));
                }
                refreshStyles.run();
            });
        }
        if (btnP4 != null) {
            btnP4.setOnClickListener(v -> {
                tempPreset[0] = 4;
                if (customInputView != null) {
                    customInputView.setText(com.dsavisualizer.app.ui.visualization.runtime.AvlTreeStepEngine.getDefaultInput(4));
                }
                refreshStyles.run();
            });
        }
        if (btnP5 != null) {
            btnP5.setOnClickListener(v -> {
                tempPreset[0] = 5;
                if (customInputView != null) {
                    customInputView.setText(com.dsavisualizer.app.ui.visualization.runtime.AvlTreeStepEngine.getDefaultInput(5));
                }
                refreshStyles.run();
            });
        }

        if (btnRandom != null) {
            btnRandom.setOnClickListener(v -> {
                java.util.Random rnd = new java.util.Random();
                int type = rnd.nextInt(4) + 1;
                tempPreset[0] = type;
                String rndInput;
                if (type == 1) {
                    int base = 10 + rnd.nextInt(30);
                    int d1 = 5 + rnd.nextInt(15);
                    int d2 = 5 + rnd.nextInt(15);
                    rndInput = base + ", " + (base + d1) + ", " + (base + d1 + d2);
                } else if (type == 2) {
                    int base = 50 + rnd.nextInt(30);
                    int d1 = 5 + rnd.nextInt(15);
                    int d2 = 5 + rnd.nextInt(15);
                    rndInput = base + ", " + (base - d1) + ", " + (base - d1 - d2);
                } else if (type == 3) {
                    int rootVal = 40 + rnd.nextInt(20);
                    int leftVal = rootVal - 15 - rnd.nextInt(10);
                    int rightOfLeft = leftVal + 5 + rnd.nextInt(8);
                    rndInput = rootVal + ", " + leftVal + ", " + rightOfLeft;
                } else {
                    int rootVal = 20 + rnd.nextInt(20);
                    int rightVal = rootVal + 15 + rnd.nextInt(10);
                    int leftOfRight = rightVal - 5 - rnd.nextInt(8);
                    rndInput = rootVal + ", " + rightVal + ", " + leftOfRight;
                }
                if (customInputView != null) {
                    customInputView.setText(rndInput);
                }
                refreshStyles.run();
            });
        }

        if (btnApply != null) {
            btnApply.setOnClickListener(v -> {
                String input = customInputView != null && customInputView.getText() != null
                        ? customInputView.getText().toString().trim() : "";
                if (input.isEmpty()) {
                    input = com.dsavisualizer.app.ui.visualization.runtime.AvlTreeStepEngine.getDefaultInput(tempPreset[0]);
                }
                applyAvlTreeSettings(tempPreset[0], input);
                dialog.dismiss();
            });
        }

        dialog.show();
    }

    private void applyAvlTreeSettings(int preset, String customInput) {
        avlTreePreset = preset;
        avlTreeCustomInput = customInput;
        com.dsavisualizer.app.data.AppPreferences prefs = new com.dsavisualizer.app.data.AppPreferences(requireContext());
        prefs.setAvlTreePreset(preset);
        prefs.setAvlTreeCustomInput(customInput);

        workingValues = com.dsavisualizer.app.ui.visualization.runtime.AvlTreeStepEngine.parseInput(customInput, preset);
        if (valuesInput != null) {
            valuesInput.setText(customInput);
        }

        resetVisualization();
        if (visualizerView != null) {
            visualizerView.setTraversalState(workingValues, new int[0], -1);
            visualizerView.setActiveNodes(-1, -1);
            visualizerView.setStepIndex(-1);
        }
        bindCurrentState("AVL Tree ready (" + com.dsavisualizer.app.ui.visualization.runtime.AvlTreeStepEngine.getPresetDescription(preset) + "). Tap Play or Step to visualize.");
        updateProgressHandle(0, estimatePlaybackTotalSteps());
        updatePlayerProgress(PlaybackState.initial());
    }

    private void openInputEditorSheet() {
        if (!isAdded()) {
            return;
        }
        try {
            if ("avl_tree".equals(techniqueId)) {
                openAvlTreeSettingsDialog();
                return;
            }
        if ("huffman_algorithm".equals(techniqueId)) {
            openHuffmanSettingsDialog();
            return;
        }
        if ("backtracking_n_queens".equals(techniqueId) || "bt_n_queens".equals(techniqueId)) {
            openNQueensSettingsDialog();
            return;
        }
        if ("backtracking_sudoku".equals(techniqueId) || "bt_sudoku".equals(techniqueId)) {
            openSudokuSettingsDialog();
            return;
        }
        if ("backtracking_knights_tour".equals(techniqueId) || "bt_knights_tour".equals(techniqueId)) {
            openKnightsTourSettingsDialog();
            return;
        }
        if ("backtracking_maze_solver".equals(techniqueId) || "bt_maze".equals(techniqueId) || "bt_rat_in_a_maze".equals(techniqueId)) {
            openMazeSettingsDialog();
            return;
        }
        if ("backtracking_hamiltonian".equals(techniqueId) || "bt_hamiltonian".equals(techniqueId)) {
            openHamiltonianSettingsDialog();
            return;
        }
        if ("backtracking_graph_coloring".equals(techniqueId) || "bt_graph_coloring".equals(techniqueId)) {
            openGraphColoringSettingsDialog();
            return;
        }
        if (VisualizationTechniqueRegistry.isDynamicProgramming(techniqueId) || (techniqueId != null && techniqueId.startsWith("dp_"))) {
            openDpSettingsDialog();
            return;
        }
        if (VisualizationTechniqueRegistry.isStringPattern(techniqueId) || "pattern_matching".equals(techniqueId)) {
            openStringPatternSettingsDialog();
            return;
        }
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) {
            openGraphSettingsDialog();
            return;
        }
        if (VisualizationTechniqueRegistry.isAdvancedDs(techniqueId)) {
            openAdvancedDsSettingsDialog();
            return;
        }
        if (VisualizationTechniqueRegistry.isSpecialized(techniqueId)) {
            openSpecializedSettingsDialog();
            return;
        }
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        View sheet = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_input_editor, null, false);
        dialog.setContentView(sheet);

        TextInputLayout editorValuesLayout = sheet.findViewById(R.id.input_editor_values_layout);
        TextInputEditText editorValuesInput = sheet.findViewById(R.id.input_editor_values);
        TextInputLayout editorTargetLayout = sheet.findViewById(R.id.input_editor_target_layout);
        TextInputEditText editorTargetInput = sheet.findViewById(R.id.input_editor_target);
        TextInputLayout editorOperationLayout = sheet.findViewById(R.id.input_editor_operation_layout);
        TextInputEditText editorOperationInput = sheet.findViewById(R.id.input_editor_operation);
        MaterialButton randomButton = sheet.findViewById(R.id.input_editor_random_button);
        MaterialButton applyButton = sheet.findViewById(R.id.input_editor_apply_button);

        if (editorValuesLayout != null && valuesInputLayout != null) {
            editorValuesLayout.setHint(valuesInputLayout.getHint());
            if (isExpressionStackTechnique()) {
                editorValuesLayout.setHelperText(resolveExpressionInputHelperText());
            }
        }
        if (editorValuesInput != null && valuesInput != null && valuesInput.getText() != null) {
            editorValuesInput.setText(valuesInput.getText().toString());
            editorValuesInput.setSelection(editorValuesInput.getText().length());
        }
        if (editorTargetLayout != null && targetInputLayout != null) {
            editorTargetLayout.setHint(targetInputLayout.getHint());
            editorTargetLayout.setVisibility(targetInputLayout.getVisibility());
        }
        if (editorTargetInput != null && targetInput != null && targetInput.getText() != null) {
            editorTargetInput.setText(targetInput.getText().toString());
            editorTargetInput.setSelection(editorTargetInput.getText().length());
        }
        if (editorOperationLayout != null && operationInputLayout != null) {
            editorOperationLayout.setHint(operationInputLayout.getHint());
            editorOperationLayout.setVisibility(operationInputLayout.getVisibility());
        }
        if (editorOperationInput != null && operationInput != null && operationInput.getText() != null) {
            editorOperationInput.setText(operationInput.getText().toString());
            editorOperationInput.setSelection(editorOperationInput.getText().length());
        }
        if (usesStructureLimit()) {
            if (editorTargetLayout != null) {
                editorTargetLayout.setVisibility(View.GONE);
            }
            if (editorOperationLayout != null) {
                editorOperationLayout.setHint(getString(R.string.vis_structure_limit_hint));
                editorOperationLayout.setHelperText(getString(R.string.vis_structure_limit_helper));
                editorOperationLayout.setVisibility(View.VISIBLE);
            }
            if (editorOperationInput != null) {
                editorOperationInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                editorOperationInput.setText(String.valueOf(structureLimit));
                editorOperationInput.setSelection(editorOperationInput.getText() == null ? 0 : editorOperationInput.getText().length());
            }
        }

        if (randomButton != null) {
            randomButton.setOnClickListener(v -> {
                applyRandomData();
                if (editorValuesInput != null && valuesInput != null && valuesInput.getText() != null) {
                    editorValuesInput.setText(valuesInput.getText().toString());
                }
                if (editorTargetInput != null && targetInput != null && targetInput.getText() != null) {
                    editorTargetInput.setText(targetInput.getText().toString());
                }
                if (editorOperationInput != null && operationInput != null && operationInput.getText() != null) {
                    editorOperationInput.setText(operationInput.getText().toString());
                }
                if (usesStructureLimit() && editorOperationInput != null) {
                    editorOperationInput.setText(String.valueOf(structureLimit));
                }
            });
        }
        if (applyButton != null) {
            applyButton.setOnClickListener(v -> {
                if (editorValuesLayout != null) {
                    editorValuesLayout.setError(null);
                }
                if (editorOperationLayout != null) {
                    editorOperationLayout.setError(null);
                }
                if (isExpressionStackTechnique()) {
                    String rawExpression = editorValuesInput == null || editorValuesInput.getText() == null
                        ? ""
                        : editorValuesInput.getText().toString().trim();
                    String validationError = validateExpressionInput(rawExpression);
                    if (validationError != null) {
                        if (editorValuesLayout != null) {
                            editorValuesLayout.setError(validationError);
                        }
                        return;
                    }
                    if (editorValuesLayout != null) {
                        editorValuesLayout.setError(null);
                    }
                }
                if (usesStructureLimit()) {
                    String rawLimit = editorOperationInput == null || editorOperationInput.getText() == null
                        ? ""
                        : editorOperationInput.getText().toString().trim();
                    Integer parsedLimit = parseStructureLimit(rawLimit);
                    if (parsedLimit == null) {
                        if (editorOperationLayout != null) {
                            editorOperationLayout.setError(getString(R.string.vis_structure_limit_invalid));
                        }
                        return;
                    }
                    String rawValues = editorValuesInput == null || editorValuesInput.getText() == null
                        ? ""
                        : editorValuesInput.getText().toString().trim();
                    if (!rawValues.isEmpty()) {
                        try {
                            int pendingSize = parseArray(rawValues).length;
                            if (pendingSize > parsedLimit) {
                                if (editorOperationLayout != null) {
                                    editorOperationLayout.setError(getString(R.string.vis_structure_limit_too_small, pendingSize));
                                }
                                return;
                            }
                        } catch (Exception error) {
                            if (editorValuesLayout != null) {
                                editorValuesLayout.setError(getString(R.string.vis_status_invalid_numbers));
                            }
                            return;
                        }
                    }
                    structureLimit = parsedLimit;
                }
                if (valuesInput != null && editorValuesInput != null) {
                    valuesInput.setText(editorValuesInput.getText());
                }
                if (targetInput != null && editorTargetInput != null) {
                    targetInput.setText(editorTargetInput.getText());
                }
                if (operationInput != null && editorOperationInput != null && !usesStructureLimit()) {
                    operationInput.setText(editorOperationInput.getText());
                }
                applyUserData();
                dialog.dismiss();
            });
        }

        MaterialButton shareButton = sheet.findViewById(R.id.input_editor_share_button);
        if (shareButton != null) {
            shareButton.setOnClickListener(v -> {
                dialog.dismiss();
                shareInteractiveWebLink();
            });
        }

        dialog.show();
        } catch (Throwable t) {
            Log.e(TAG, "Error in openInputEditorSheet", t);
        }
    }

    private void shareInteractiveWebLink() {
        if (!isAdded() || getContext() == null) {
            return;
        }
        try {
            StringBuilder sb = new StringBuilder();
            if (workingValues != null && workingValues.length > 0) {
                for (int i = 0; i < workingValues.length; i++) {
                    if (i > 0) sb.append(",");
                    sb.append(workingValues[i]);
                }
            } else if (valuesInput != null && valuesInput.getText() != null) {
                sb.append(valuesInput.getText().toString().replaceAll("\\s+", ""));
            }
            int step = Math.max(0, stepIndex);
            String tech = techniqueId != null ? techniqueId : "bubble_sort";

            String baseUrl = null;
            try {
                baseUrl = com.dsavisualizer.app.data.ApiConfig.getBaseUrl();
            } catch (Throwable ignored) {
            }

            if (baseUrl == null || baseUrl.trim().isEmpty()
                || baseUrl.contains("localhost")
                || baseUrl.contains("127.0.0.1")
                || baseUrl.contains("172.27.")
                || baseUrl.contains("192.168.")
                || baseUrl.contains("10.")) {
                baseUrl = "https://dsa-visualizer-api-nu.vercel.app";
            }
            while (baseUrl.endsWith("/")) {
                baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
            }

            String webUrl = baseUrl + "/visualize?tech=" + tech
                + "&data=" + sb.toString()
                + "&step=" + step;

            String shareSubject = "DSA Visualization: " + (techniqueTitle != null ? techniqueTitle : tech);
            String shareBody = "Open this interactive visualization in your browser (Play, Step, Replay, and Edit):\n"
                + webUrl;

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(shareIntent, getString(R.string.vis_share_web_link)));
        } catch (Throwable t) {
            Log.e(TAG, "Failed to share visualization web link", t);
        }
    }

    private void keepStepChipInHeader() {
        if (stepChipView == null) {
            return;
        }
        controlsChipHost = null;
        if (controlsStrip != null) {
            controlsStrip.setTag(null);
            controlsStrip.setPadding(
                controlsStrip.getPaddingLeft(),
                controlsStrip.getPaddingTop(),
                controlsStrip.getPaddingRight(),
                controlsStrip.getPaddingBottom()
            );
        }
        ViewGroup chipRow = stepChipView.getParent() instanceof ViewGroup ? (ViewGroup) stepChipView.getParent() : null;
        if (chipRow != null) {
            chipRow.setVisibility(View.VISIBLE);
        }
        if (stepChipView.getLayoutParams() instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) stepChipView.getLayoutParams();
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            stepChipView.setLayoutParams(params);
        }
        stepChipView.setTextSize(11f);
        stepChipView.setPadding(dp(8), dp(4), dp(8), dp(4));
        if (completedBadgeView != null) {
            completedBadgeView.setTextSize(10f);
            completedBadgeView.setPadding(dp(8), dp(4), dp(8), dp(4));
        }
        refreshControlsHint();
    }

    private void refreshInlineChipPadding() {
        if (!isAdded() || controlsStrip == null || controlsChipHost == null) {
            return;
        }
        int dynamicRightPadding = controlsChipHost.getWidth() + dp(8);
        controlsStrip.setPadding(
            controlsStrip.getPaddingLeft(),
            controlsStrip.getPaddingTop(),
            Math.max(dp(50), dynamicRightPadding),
            controlsStrip.getPaddingBottom()
        );
    }

    private void refreshControlsHint() {
        if (controlsHintLabel == null || !isAdded()) {
            return;
        }
        controlsHintLabel.setVisibility(isControlsHintDismissed() ? View.GONE : View.VISIBLE);
    }

    private boolean isControlsHintDismissed() {
        Context context = getContext();
        if (context == null) {
            return true;
        }
        SharedPreferences preferences = context.getSharedPreferences(UI_PREFS, Context.MODE_PRIVATE);
        return preferences.getBoolean(PREF_CONTROLS_HINT_DISMISSED, false);
    }

    private void dismissControlsHint() {
        Context context = getContext();
        if (context == null) {
            return;
        }
        SharedPreferences preferences = context.getSharedPreferences(UI_PREFS, Context.MODE_PRIVATE);
        if (!preferences.getBoolean(PREF_CONTROLS_HINT_DISMISSED, false)) {
            preferences.edit().putBoolean(PREF_CONTROLS_HINT_DISMISSED, true).apply();
        }
        if (controlsHintLabel != null && controlsHintLabel.getVisibility() == View.VISIBLE) {
            controlsHintLabel.animate().alpha(0f).translationY(-dp(4)).setDuration(140L).withEndAction(() -> {
                if (controlsHintLabel != null) {
                    controlsHintLabel.setVisibility(View.GONE);
                    controlsHintLabel.setAlpha(1f);
                    controlsHintLabel.setTranslationY(0f);
                }
            }).start();
        }
    }

    private void setupStepGestures() {
        stepGestureDetector = new android.view.GestureDetector(requireContext(), new android.view.GestureDetector.SimpleOnGestureListener() {
            private static final int SWIPE_DISTANCE_PX = 110;
            private static final int SWIPE_VELOCITY_PX = 180;

            @Override
            public boolean onDown(android.view.MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(android.view.MotionEvent e1, android.view.MotionEvent e2, float velocityX, float velocityY) {
                if (e1 == null || e2 == null || progressScrubbing) {
                    return false;
                }
                float deltaX = e2.getX() - e1.getX();
                float deltaY = e2.getY() - e1.getY();
                if (Math.abs(deltaX) < SWIPE_DISTANCE_PX || Math.abs(deltaX) < Math.abs(deltaY)) {
                    return false;
                }
                if (Math.abs(velocityX) < SWIPE_VELOCITY_PX) {
                    return false;
                }
                dismissControlsHint();
                if (deltaX < 0f) {
                    stepForwardOnce();
                } else {
                    stepBackwardOnce();
                }
                return true;
            }
        });
        View.OnTouchListener swipeListener = (v, event) -> {
            if (stepGestureDetector != null) {
                stepGestureDetector.onTouchEvent(event);
            }
            return false;
        };
        if (visualizerView != null) {
            visualizerView.setOnTouchListener(swipeListener);
        }
        if (visualizerScrollView != null) {
            visualizerScrollView.setOnTouchListener(swipeListener);
        }
    }

    private void setupInputs(TechniqueContent content) {
        valuesInputLayout.setHint("Values");
        targetInputLayout.setHint("Target / Input");
        valuesInput.setInputType(InputType.TYPE_CLASS_TEXT);
        targetInput.setInputType(InputType.TYPE_CLASS_TEXT);

        if (techniqueId != null && techniqueId.startsWith("leetcode_")) {
            configureLeetCodeInputs();
        } else if (SearchVisualizationController.configureInputs(
            techniqueId,
            valuesInputLayout,
            targetInputLayout,
            valuesInput,
            targetInput
        )) {
            if (VisualizationTechniqueRegistry.isPatternMatching(techniqueId)) {
                return;
            }
        } else if (TreeVisualizationController.configureInputs(techniqueId, valuesInputLayout, targetInputLayout)
            || GraphVisualizationController.configureInputs(techniqueId, valuesInputLayout, targetInputLayout)
            || StructureVisualizationController.configureInputs(techniqueId, valuesInputLayout, targetInputLayout)) {
            // Family-specific input configuration handled by extracted controller.
        } else if (VisualizationTechniqueRegistry.isBacktracking(techniqueId)
            || VisualizationTechniqueRegistry.isDynamicProgramming(techniqueId)
            || VisualizationTechniqueRegistry.isStringPattern(techniqueId)
            || VisualizationTechniqueRegistry.isAdvancedDs(techniqueId)
            || VisualizationTechniqueRegistry.isSpecialized(techniqueId)
            || "huffman_algorithm".equals(techniqueId)) {
            targetInputLayout.setVisibility(View.GONE);
            valuesInputLayout.setVisibility(View.GONE);
            if (inputActionsRow != null) {
                inputActionsRow.setVisibility(View.GONE);
            }
        } else if (isSortingMode()) {
            targetInputLayout.setVisibility(View.GONE);
            valuesInputLayout.setHint("Array values");
        } else {
            targetInputLayout.setVisibility(View.VISIBLE);
            valuesInputLayout.setHint("Array values");
            targetInputLayout.setHint("Search number");
        }

        if (isExpressionStackTechnique()) {
            valuesInputLayout.setHint(resolveExpressionInputHint());
            targetInputLayout.setVisibility(View.GONE);
        }

        structureLimit = usesStructureLimit() ? resolveDefaultStructureLimit(content.sampleInput.length) : structureLimit;
        valuesInput.setText(isExpressionStackTechnique() ? resolveDefaultExpressionInput() : join(content.sampleInput));
        if (usesStructureLimit()) {
            targetInputLayout.setVisibility(View.VISIBLE);
            targetInputLayout.setHint(getString(R.string.vis_structure_limit_hint));
            targetInput.setInputType(InputType.TYPE_CLASS_NUMBER);
            styleStructureLimitField(true);
            syncStructureLimitInput();
        } else if (targetInputLayout.getVisibility() == View.VISIBLE) {
            styleStructureLimitField(false);
            targetInput.setText(String.valueOf(content.sampleTarget));
        } else {
            styleStructureLimitField(false);
        }
        setupOperationControls();
        visualizerView.clearPointerState();
        visualizerView.clearActiveEdge();
        visualizerView.setStructureMotion("", -1);
    }

    private void setupOperationControls() {
        StructureVisualizationController.bindOperationControls(
            techniqueId,
            operationsGroup,
            operationsTitle,
            operationPrimaryRow,
            operationSecondaryRow,
            operationInputLayout,
            operationPrimaryButton,
            operationSecondaryButton,
            operationTertiaryButton,
            operationFourthButton,
            this::performStackOperation,
            this::performQueueOperation,
            this::performDequeOperation
        );
        if (isStructureMode()) {
            attachOperationsToOverlayHost();
        } else {
            restoreOperationsPosition();
        }
    }

    private void configureLeetCodeInputs() {
        TechniqueContent content = AppRepository.getContent(techniqueId);
        String title = content != null ? content.title : "LeetCode Problem";
        valuesInputLayout.setHint(title + " values");

        if (content != null && content.sampleTarget != 0) {
            targetInputLayout.setVisibility(View.VISIBLE);
            targetInputLayout.setHint("Target (e.g. " + content.sampleTarget + ")");
        } else if ("leetcode_two_sum".equals(techniqueId) || "leetcode_1".equals(techniqueId)) {
            valuesInputLayout.setHint("Array nums (e.g. 2, 7, 11, 15)");
            targetInputLayout.setVisibility(View.VISIBLE);
            targetInputLayout.setHint("Target sum (e.g. 9)");
        } else if ("leetcode_binary_search".equals(techniqueId) || "leetcode_704".equals(techniqueId)) {
            valuesInputLayout.setHint("Sorted nums (-1, 0, 3, 5, 9, 12)");
            targetInputLayout.setVisibility(View.VISIBLE);
            targetInputLayout.setHint("Target value (e.g. 9)");
        } else {
            targetInputLayout.setVisibility(View.GONE);
        }
    }

    private void setupTraversalControls() {
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) {
            String mode = GraphVisualizationController.configureTraversalControls(
                this,
                techniqueId,
                traversalGroup,
                traversalLabel,
                traversalSpinner,
                sessionViewModel,
                nextMode -> {
                    currentTraversalMode = nextMode;
                    resetVisualization();
                }
            );
            currentTraversalMode = mode == null ? "" : mode;
            return;
        }

        if (VisualizationTechniqueRegistry.isTree(techniqueId)) {
            String mode = TreeVisualizationController.configureTraversalControls(
                this,
                techniqueId,
                traversalGroup,
                traversalLabel,
                traversalSpinner,
                sessionViewModel,
                nextMode -> {
                    currentTraversalMode = nextMode;
                    resetVisualization();
                }
            );
            currentTraversalMode = mode == null ? "" : mode;
            return;
        }

        traversalGroup.setVisibility(View.GONE);
        currentTraversalMode = "";
    }

    private void applyDefaultData(TechniqueContent content) {
        workingValues = content.sampleInput.clone();
        target = content.sampleTarget;
        if (usesStructureLimit()) {
            structureLimit = resolveDefaultStructureLimit(workingValues.length);
            syncStructureLimitInput();
        }
        if (isExpressionStackTechnique()) {
            expressionInput = resolveDefaultExpressionInput();
            valuesInput.setText(expressionInput);
            visualizerView.setExpressionInput(expressionInput);
            workingValues = new int[0];
            return;
        }
        if (VisualizationTechniqueRegistry.isPatternMatching(techniqueId)) {
            com.dsavisualizer.app.data.AppPreferences prefs = new com.dsavisualizer.app.data.AppPreferences(requireContext());
            stringPatternPreset = prefs.getStringPatternPreset(techniqueId);
            patternText = prefs.getStringPatternText(techniqueId, com.dsavisualizer.app.ui.visualization.runtime.StringPatternStepEngine.getPresetText(techniqueId, stringPatternPreset));
            patternQuery = prefs.getStringPatternQuery(techniqueId, com.dsavisualizer.app.ui.visualization.runtime.StringPatternStepEngine.getPresetQuery(techniqueId, stringPatternPreset));
            workingValues = new int[patternText.length()];
            valuesInput.setText(patternText);
            targetInput.setText(patternQuery);
            visualizerView.setPatternState(patternText, patternQuery, 0, 0, false);
            return;
        }
        if ("huffman_algorithm".equals(techniqueId)) {
            com.dsavisualizer.app.data.AppPreferences prefs = new com.dsavisualizer.app.data.AppPreferences(requireContext());
            huffmanPreset = prefs.getHuffmanPreset();
            huffmanCustomInput = prefs.getHuffmanCustomInput();
            if (huffmanCustomInput != null && !huffmanCustomInput.trim().isEmpty()) {
                try {
                    String[] parts = huffmanCustomInput.trim().split("[,\\s]+");
                    List<Integer> list = new ArrayList<>();
                    for (String p : parts) {
                        p = p.trim();
                        if (!p.isEmpty()) {
                            int val = Integer.parseInt(p);
                            if (val > 0) list.add(val);
                        }
                    }
                    if (list.size() >= 2) {
                        workingValues = new int[list.size()];
                        for (int i = 0; i < list.size(); i++) {
                            workingValues[i] = list.get(i);
                        }
                    } else {
                        workingValues = com.dsavisualizer.app.ui.visualization.runtime.HuffmanStepEngine.getDefaultWeights(huffmanPreset);
                    }
                } catch (Exception e) {
                    workingValues = com.dsavisualizer.app.ui.visualization.runtime.HuffmanStepEngine.getDefaultWeights(huffmanPreset);
                }
            } else {
                workingValues = com.dsavisualizer.app.ui.visualization.runtime.HuffmanStepEngine.getDefaultWeights(huffmanPreset);
            }
            return;
        }
        if ("avl_tree".equals(techniqueId)) {
            com.dsavisualizer.app.data.AppPreferences prefs = new com.dsavisualizer.app.data.AppPreferences(requireContext());
            avlTreePreset = prefs.getAvlTreePreset();
            avlTreeCustomInput = prefs.getAvlTreeCustomInput(com.dsavisualizer.app.ui.visualization.runtime.AvlTreeStepEngine.getDefaultInput(avlTreePreset));
            workingValues = com.dsavisualizer.app.ui.visualization.runtime.AvlTreeStepEngine.parseInput(avlTreeCustomInput, avlTreePreset);
            if (valuesInput != null) {
                valuesInput.setText(avlTreeCustomInput);
            }
            return;
        }
        if (VisualizationTechniqueRegistry.isTree(techniqueId)
            && !"traversal_using_stacks".equals(techniqueId)
            && !"huffman_algorithm".equals(techniqueId)
            && !"avl_tree".equals(techniqueId)
            && !"trie".equals(techniqueId)) {
            workingValues = ensureLength(content.sampleInput, 7);
        }
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) {
            com.dsavisualizer.app.data.AppPreferences prefs = new com.dsavisualizer.app.data.AppPreferences(requireContext());
            graphPreset = prefs.getGraphPreset(techniqueId);
            graphStartNode = prefs.getGraphStartNode(techniqueId);
            graphNodeCount = prefs.getGraphNodeCount(techniqueId);
            workingValues = new int[graphNodeCount];
            for (int i = 0; i < graphNodeCount; i++) {
                workingValues[i] = i;
            }
            target = graphStartNode;
            if (visualizerView != null) {
                visualizerView.setGraphPreset(graphPreset);
            }
        }
        if (VisualizationTechniqueRegistry.isAdvancedDs(techniqueId)) {
            com.dsavisualizer.app.data.AppPreferences prefs = new com.dsavisualizer.app.data.AppPreferences(requireContext());
            advancedDsPreset = prefs.getAdvancedDsPreset(techniqueId);
            advancedDsCustomInput = prefs.getAdvancedDsCustomInput(techniqueId, com.dsavisualizer.app.ui.visualization.runtime.AdvancedDsStepEngine.getDefaultInput(techniqueId, advancedDsPreset));
            setExpressionInput(advancedDsCustomInput);
            if (visualizerView != null) {
                visualizerView.setExpressionInput(advancedDsCustomInput);
            }
        }
    }

    private void handlePlayPause() {
        try {
            if (playbackController == null) {
                if (!running) {
                    startVisualization();
                } else {
                    paused = !paused;
                    syncPlayButtonVisual(!paused);
                    if (!paused) {
                        scheduleNextStep();
                    }
                }
                return;
            }

            PlaybackState state = playbackController.getPlaybackState().getValue();
            if (state != null && state.isPlaying) {
                playbackController.pause();
            } else {
                if (playbackComplete || (state != null && state.isAtEnd)) {
                    playbackController.reset();
                }
                playbackController.play();
            }
        } catch (Throwable t) {
            Log.e(TAG, "Error in handlePlayPause", t);
        }
    }

    private void startVisualization() {
        resetRuntimeOnly();
        renderPlaybackState(new PlaybackState(true, 0, 1f, true, false));
    }

    private void scheduleNextStep() {
        if (playbackController != null) {
            playbackController.play();
        }
    }

    private void runSingleStep() {
        if (playbackController != null) {
            playbackController.nextStep();
            return;
        }
        handler.removeCallbacksAndMessages(null);
        if (!running) {
            resetRuntimeOnly();
            running = true;
        }
        paused = true;
        syncPlayButtonVisual(false);
        advanceOneStep();
    }

    private void advanceOneStep() {
        if (playbackController != null) {
            playbackController.nextStep();
            return;
        }
        if (!running) {
            return;
        }
        stepEngine.next();

        if (running && !paused) {
            scheduleNextStep();
        }
    }

    private void advanceSearchStep() {
        SearchStepEngine.advance(this);
    }

    @Override
    public void advancePatternMatchingStep() {
        if (patternText.isEmpty() || patternQuery.isEmpty()) {
            finish("Enter both a text and a pattern.");
            return;
        }

        if (stepIndex == -1) {
            stepIndex = 0;
            auxIndex = 0;
            patternFound = false;
            patternWindowStart = 0;
            patternMatchIndex = 0;
            visualizerView.setPatternState(patternText, patternQuery, patternWindowStart, patternMatchIndex, false);
            setHighlightedLines(1, 0);
            updateStatusText("Align the pattern at position 0.");
            syncScrollToPattern(patternWindowStart);
            return;
        }

        if (stepIndex > patternText.length() - patternQuery.length()) {
            patternFound = false;
            visualizerView.setPatternState(patternText, patternQuery, patternWindowStart, patternMatchIndex, false);
            finish("Pattern not found.");
            return;
        }

        int textIndex = stepIndex + auxIndex;
        if (textIndex >= patternText.length() || auxIndex >= patternQuery.length()) {
            finish("Pattern not found.");
            return;
        }

        char textChar = patternText.charAt(textIndex);
        char patternChar = patternQuery.charAt(auxIndex);
        patternWindowStart = stepIndex;
        patternMatchIndex = auxIndex;
        visualizerView.setPatternState(patternText, patternQuery, patternWindowStart, patternMatchIndex, false);
        setHighlightedLines(2, 1);
        updateStatusText("Compare " + patternChar + " with " + textChar + ".");

        if (textChar == patternChar) {
            auxIndex++;
            if (auxIndex >= patternQuery.length()) {
                patternFound = true;
                patternMatchIndex = patternQuery.length() - 1;
                visualizerView.setPatternState(patternText, patternQuery, patternWindowStart, patternMatchIndex, true);
                setHighlightedLines(4, 2);
                finish("Pattern found at position " + stepIndex + ".");
                return;
            }
            updateStatusText("Matched " + auxIndex + " character(s) so far.");
        } else {
            stepIndex++;
            auxIndex = 0;
            patternMatchIndex = 0;
            setHighlightedLines(5, 3);
            updateStatusText("Mismatch, slide to position " + stepIndex + ".");
            visualizerView.setPatternState(patternText, patternQuery, stepIndex, patternMatchIndex, false);
        }

        syncScrollToPattern(stepIndex);
    }

    private void advanceSortStep() {
        SortStepEngine.advance(this);
    }

    @Override
    public void advanceMergingStep() {
        if (stepIndex == -1) {
            int middle = Math.max(1, workingValues.length / 2);
            mergeLeftValues = Arrays.copyOfRange(workingValues, 0, middle);
            mergeRightValues = Arrays.copyOfRange(workingValues, middle, workingValues.length);
            Arrays.sort(mergeLeftValues);
            Arrays.sort(mergeRightValues);
            mergeOutputValues = createPlaceholderArray(workingValues.length);
            mergeLeftCursor = 0;
            mergeRightCursor = 0;
            mergeWriteCursor = -1;
            stepIndex = 0;
            workingValues = mergeOutputValues.clone();
            visualizerView.setMergeState(mergeLeftValues, mergeRightValues, mergeOutputValues, mergeWriteCursor);
            visualizerView.clearPointerState();
            updateStatusText("Starting merge with two sorted halves");
            setHighlightedLines(1, 0);
            return;
        }

        if (mergeWriteCursor + 1 >= mergeOutputValues.length) {
            workingValues = mergeOutputValues.clone();
            foundIndex = workingValues.length - 1;
            updateArrayVisualization(foundIndex, -1, -1);
            finish("Merging completed");
            return;
        }

        int nextValue;
        String message;
        boolean fromLeft;
        int sourceIndex;
        if (mergeLeftCursor >= mergeLeftValues.length) {
            sourceIndex = mergeRightCursor;
            nextValue = mergeRightValues[mergeRightCursor++];
            fromLeft = false;
            message = "Left list is finished, so copy " + nextValue + " from the right list.";
            setHighlightedLines(3, 2);
        } else if (mergeRightCursor >= mergeRightValues.length) {
            sourceIndex = mergeLeftCursor;
            nextValue = mergeLeftValues[mergeLeftCursor++];
            fromLeft = true;
            message = "Right list is finished, so copy " + nextValue + " from the left list.";
            setHighlightedLines(3, 2);
        } else if (mergeLeftValues[mergeLeftCursor] <= mergeRightValues[mergeRightCursor]) {
            sourceIndex = mergeLeftCursor;
            nextValue = mergeLeftValues[mergeLeftCursor++];
            fromLeft = true;
            message = "Choose " + nextValue + " because it is smaller than the current right-side value.";
            setHighlightedLines(2, 1);
        } else {
            sourceIndex = mergeRightCursor;
            nextValue = mergeRightValues[mergeRightCursor++];
            fromLeft = false;
            message = "Choose " + nextValue + " because it is smaller than the current left-side value.";
            setHighlightedLines(2, 1);
        }

        mergeWriteCursor++;
        mergeOutputValues[mergeWriteCursor] = nextValue;
        workingValues = mergeOutputValues.clone();
        stepIndex = mergeWriteCursor;
        visualizerView.setMergeState(mergeLeftValues, mergeRightValues, mergeOutputValues, mergeWriteCursor);
        visualizerView.setMergeTransfer(fromLeft, sourceIndex, mergeWriteCursor, nextValue);
        updateArrayVisualization(stepIndex, -1, -1);
        updateStatusText(message);
        syncScrollToFocus(stepIndex);
    }

    @Override
    public void advanceMergeSortStep() {
        if (workingValues.length <= 1) {
            finish("Merge Sort completed");
            return;
        }

        if (stepIndex == -1) {
            mergeWorkingValues = workingValues.clone();
            mergeRunSize = 1;
            mergePassStart = 0;
            stepIndex = 0;
        }

        if (mergeRunSize >= mergeWorkingValues.length) {
            workingValues = mergeWorkingValues.clone();
            visualizerView.setMergeState(new int[0], new int[0], workingValues, workingValues.length - 1);
            updateArrayVisualization(workingValues.length - 1, -1, -1);
            finish("Merge Sort completed");
            return;
        }

        if (mergePassStart >= mergeWorkingValues.length) {
            mergePassStart = 0;
            mergeRunSize *= 2;
            if (mergeRunSize >= mergeWorkingValues.length) {
                workingValues = mergeWorkingValues.clone();
                visualizerView.setMergeState(new int[0], new int[0], workingValues, workingValues.length - 1);
                updateArrayVisualization(workingValues.length - 1, -1, -1);
                finish("Merge Sort completed");
                return;
            }
        }

        int leftStart = mergePassStart;
        int middle = Math.min(leftStart + mergeRunSize, mergeWorkingValues.length);
        int rightEnd = Math.min(leftStart + (mergeRunSize * 2), mergeWorkingValues.length);

        if (middle >= rightEnd) {
            mergePassStart += mergeRunSize * 2;
            advanceMergeSortStep();
            return;
        }

        mergeLeftValues = Arrays.copyOfRange(mergeWorkingValues, leftStart, middle);
        mergeRightValues = Arrays.copyOfRange(mergeWorkingValues, middle, rightEnd);
        int[] mergedSegment = mergeArrays(mergeLeftValues, mergeRightValues);
        System.arraycopy(mergedSegment, 0, mergeWorkingValues, leftStart, mergedSegment.length);

        workingValues = mergeWorkingValues.clone();
        mergeOutputValues = mergedSegment.clone();
        mergeWriteCursor = mergedSegment.length - 1;
        stepIndex = leftStart;
        auxIndex = rightEnd - 1;
        visualizerView.setMergeState(mergeLeftValues, mergeRightValues, mergeOutputValues, mergeWriteCursor);
        visualizerView.setPointerState("L", leftStart, "R", middle);
        updateArrayVisualization(leftStart, rightEnd - 1, -1);
        setHighlightedLines(3, 1);
        updateStatusText(
            "Merged runs of size " + mergeRunSize
                + " from index " + leftStart
                + " to " + (rightEnd - 1)
        );
        syncScrollToFocus(leftStart);
        mergePassStart += mergeRunSize * 2;
    }

    @Override
    public void advanceRadixSortStep() {
        if (workingValues.length == 0) {
            finish("No values");
            return;
        }

        if (stepIndex == -1) {
            radixDivisor = 1;
            radixMaxValue = resolveMaxAbsValue(workingValues);
            stepIndex = 0;
            auxIndex = 0;
            foundIndex = -1;
            visualizerView.setRadixOriginalValues(workingValues);
            visualizerView.setRadixMaxValue(radixMaxValue);
            syncRadixVisualizerHeight();
        }

        if (radixDivisor > radixMaxValue) {
            playbackComplete = true;
            visualizerView.setRadixComplete(true, workingValues);
            visualizerView.clearPointerState();
            updateArrayVisualization(0, -1, -1);
            updateStatusText("Radix Sort Completed ✓ | Final Array: " + java.util.Arrays.toString(workingValues));
            finish("Radix Sort Completed ✓");
            return;
        }

        String digitName = resolveRadixPlaceName(radixDivisor);

        if (auxIndex < workingValues.length) {
            int currentIndex = auxIndex;
            int currentValue = workingValues[currentIndex];
            int activeBucket = Math.abs(currentValue / radixDivisor) % 10;
            stepIndex = currentIndex;
            foundIndex = activeBucket;
            visualizerView.setRadixState(radixDivisor, activeBucket, currentIndex + 1);
            syncRadixVisualizerHeight();
            visualizerView.setPointerState(digitName + " = " + activeBucket, currentIndex, "", -1);
            updateArrayVisualization(currentIndex, -1, -1);
            setHighlightedLines(2, 1);

            int prevCount = 0;
            for (int k = 0; k < currentIndex; k++) {
                if (Math.abs(workingValues[k] / radixDivisor) % 10 == activeBucket) {
                    prevCount++;
                }
            }
            String stabilityNote = prevCount > 0 ? " (FIFO: queued after " + prevCount + " item" + (prevCount > 1 ? "s" : "") + ")" : "";
            updateStatusText(
                "Current Pass: " + digitName + " Digit | "
                    + currentValue
                    + " → "
                    + digitName.toLowerCase(Locale.US)
                    + " = "
                    + activeBucket
                    + " ➔ Bucket "
                    + activeBucket
                    + stabilityNote
            );
            syncScrollToFocus(currentIndex);
            auxIndex = currentIndex + 1;
            return;
        }

        int completedDivisor = radixDivisor;
        String completedPlace = resolveRadixPlaceName(completedDivisor);
        workingValues = countingSortByDigit(workingValues, completedDivisor);
        foundIndex = -1;
        auxIndex = 0;
        stepIndex = 0;
        visualizerView.setRadixState(completedDivisor, -1, workingValues.length);
        syncRadixVisualizerHeight();
        visualizerView.clearPointerState();
        updateArrayVisualization(0, -1, -1);
        setHighlightedLines(3, 2);
        updateStatusText(completedPlace + " Digit Completed | Buckets collected stably 0 → 9 | Result: " + java.util.Arrays.toString(workingValues));
        syncScrollToFocus(0);
        radixDivisor *= 10;
    }

    private String resolveRadixPlaceName(int divisor) {
        if (divisor == 1) return "Units";
        if (divisor == 10) return "Tens";
        if (divisor == 100) return "Hundreds";
        if (divisor == 1000) return "Thousands";
        if (divisor == 10000) return "Ten Thousands";
        if (divisor == 100000) return "Hundred Thousands";
        return "10^" + (int) Math.round(Math.log10(divisor));
    }

    private void syncRadixVisualizerHeight() {
        if ("radix_sort".equals(techniqueId) && !isLandscapeLayout() && visualizerView != null) {
            int desiredH = visualizerView.resolveRadixDesiredHeight();
            ViewGroup.LayoutParams lp = visualizerView.getLayoutParams();
            if (lp != null && lp.height != desiredH) {
                lp.height = desiredH;
                visualizerView.setLayoutParams(lp);
            }
        }
    }

    private int[] buildTraversalOrderInternal() {
        if (isGraphTechnique()) {
            return buildDynamicGraphTraversal();
        }
        if ("bst_search_parent_tracking".equals(techniqueId)) {
            return buildBstSearchPath();
        }
        if ("bst_insertion".equals(techniqueId)) {
            return buildBstInsertPath();
        }
        if ("bst_deletion_case_a".equals(techniqueId)
            || "bst_deletion_case_b".equals(techniqueId)
            || "bst_deletion_del".equals(techniqueId)) {
            return buildBstSearchPath();
        }
        
        if ("Preorder".equals(currentTraversalMode)) {
            return buildTreePreorder();
        }
        if ("Postorder".equals(currentTraversalMode)) {
            return buildTreePostorder();
        }
        return buildTreeInorder(); // Default for AVL, Trie, Heap, etc.
    }

    @Override
    public int getGraphStartNode() {
        return graphStartNode;
    }

    @Override
    public int[][] getGraphEdges() {
        return com.dsavisualizer.app.ui.visualization.runtime.GraphStepEngine.getGraphEdges(graphPreset, graphNodeCount);
    }

    private int[] buildDynamicGraphTraversal() {
        int nodeCount = workingValues.length;
        if (nodeCount == 0) return new int[0];
        java.util.List<Integer> order = new java.util.ArrayList<>();
        boolean[] visited = new boolean[nodeCount];
        int[][] edges = getGraphEdges();
        
        java.util.List<java.util.List<Integer>> adj = new java.util.ArrayList<>();
        for (int i = 0; i < nodeCount; i++) adj.add(new java.util.ArrayList<>());
        for (int[] e : edges) {
            if (e[0] < nodeCount && e[1] < nodeCount) {
                adj.get(e[0]).add(e[1]);
                adj.get(e[1]).add(e[0]);
            }
        }
        for (java.util.List<Integer> list : adj) {
            java.util.Collections.sort(list);
        }

        int start = Math.max(0, Math.min(graphStartNode, nodeCount - 1));

        if ("topological_sort".equals(techniqueId)) {
            int[] inDegree = new int[nodeCount];
            java.util.List<java.util.List<Integer>> dirAdj = new java.util.ArrayList<>();
            for (int i = 0; i < nodeCount; i++) dirAdj.add(new java.util.ArrayList<>());
            for (int[] e : edges) {
                if (e[0] < nodeCount && e[1] < nodeCount) {
                    dirAdj.get(e[0]).add(e[1]);
                    inDegree[e[1]]++;
                }
            }
            java.util.Queue<Integer> q = new java.util.LinkedList<>();
            for (int i = 0; i < nodeCount; i++) {
                if (inDegree[i] == 0) q.add(i);
            }
            while (!q.isEmpty()) {
                int curr = q.poll();
                order.add(curr);
                for (int nxt : dirAdj.get(curr)) {
                    inDegree[nxt]--;
                    if (inDegree[nxt] == 0) q.add(nxt);
                }
            }
            for (int i = 0; i < nodeCount; i++) {
                if (!order.contains(i)) order.add(i);
            }
        } else if ("depth_first_search".equals(techniqueId) || "DFS".equals(currentTraversalMode)) {
            java.util.Stack<Integer> stack = new java.util.Stack<>();
            stack.push(start);
            while (!stack.isEmpty()) {
                int curr = stack.pop();
                if (!visited[curr]) {
                    visited[curr] = true;
                    order.add(curr);
                    for (int i = adj.get(curr).size() - 1; i >= 0; i--) {
                        int neighbor = adj.get(curr).get(i);
                        if (!visited[neighbor]) {
                            stack.push(neighbor);
                        }
                    }
                }
            }
            for (int i = 0; i < nodeCount; i++) {
                if (!visited[i]) {
                    stack.push(i);
                    while (!stack.isEmpty()) {
                        int curr = stack.pop();
                        if (!visited[curr]) {
                            visited[curr] = true;
                            order.add(curr);
                            for (int k = adj.get(curr).size() - 1; k >= 0; k--) {
                                int neighbor = adj.get(curr).get(k);
                                if (!visited[neighbor]) {
                                    stack.push(neighbor);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            java.util.Queue<Integer> queue = new java.util.LinkedList<>();
            queue.add(start);
            visited[start] = true;
            while (!queue.isEmpty()) {
                int curr = queue.poll();
                order.add(curr);
                for (int neighbor : adj.get(curr)) {
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        queue.add(neighbor);
                    }
                }
            }
            for (int i = 0; i < nodeCount; i++) {
                if (!visited[i]) {
                    queue.add(i);
                    visited[i] = true;
                    while (!queue.isEmpty()) {
                        int curr = queue.poll();
                        order.add(curr);
                        for (int neighbor : adj.get(curr)) {
                            if (!visited[neighbor]) {
                                visited[neighbor] = true;
                                queue.add(neighbor);
                            }
                        }
                    }
                }
            }
        }
        
        int[] result = new int[order.size()];
        for(int i = 0; i < order.size(); i++) result[i] = order.get(i);
        return result;
    }

    private int[] buildTreePreorder() {
        java.util.List<Integer> order = new java.util.ArrayList<>();
        preorderRecursive(0, order);
        int[] res = new int[order.size()];
        for (int i = 0; i < order.size(); i++) res[i] = order.get(i);
        return res;
    }

    private void preorderRecursive(int index, java.util.List<Integer> order) {
        if (index >= workingValues.length) return;
        order.add(index);
        preorderRecursive(2 * index + 1, order);
        preorderRecursive(2 * index + 2, order);
    }

    private int[] buildTreePostorder() {
        java.util.List<Integer> order = new java.util.ArrayList<>();
        postorderRecursive(0, order);
        int[] res = new int[order.size()];
        for (int i = 0; i < order.size(); i++) res[i] = order.get(i);
        return res;
    }

    private void postorderRecursive(int index, java.util.List<Integer> order) {
        if (index >= workingValues.length) return;
        postorderRecursive(2 * index + 1, order);
        postorderRecursive(2 * index + 2, order);
        order.add(index);
    }

    private int[] buildTreeInorder() {
        java.util.List<Integer> order = new java.util.ArrayList<>();
        inorderRecursive(0, order);
        int[] res = new int[order.size()];
        for (int i = 0; i < order.size(); i++) res[i] = order.get(i);
        return res;
    }

    private void inorderRecursive(int index, java.util.List<Integer> order) {
        if (index >= workingValues.length) return;
        inorderRecursive(2 * index + 1, order);
        order.add(index);
        inorderRecursive(2 * index + 2, order);
    }

    @Override
    public String getTechniqueId() {
        return techniqueId;
    }

    @Override
    public String getExpressionInput() {
        return expressionInput;
    }

    @Override
    public void setExpressionInput(String expressionInput) {
        this.expressionInput = expressionInput == null ? "" : expressionInput.trim();
        if (visualizerView != null) {
            visualizerView.setExpressionInput(this.expressionInput);
        }
    }

    @Override
    public String getTraversalMode() {
        return currentTraversalMode;
    }

    @Override
    public int getDifficultyLevel() {
        if (sessionViewModel == null || sessionViewModel.getDifficultyLevel().getValue() == null) {
            return 1;
        }
        switch (sessionViewModel.getDifficultyLevel().getValue()) {
            case EXPERT:
                return 3;
            case INTERMEDIATE:
                return 2;
            case GUIDED:
            default:
                return 1;
        }
    }

    @Override
    public void showPredictionUI(String question, String[] options, int correctIndex, String mistakeExplanation) {
        predictionPending = true;
        StringBuilder builder = new StringBuilder(question == null ? "Prediction" : question);
        if (options != null && options.length > 0) {
            builder.append("\n");
            for (int i = 0; i < options.length; i++) {
                builder.append("\n").append((char) ('A' + i)).append(". ").append(options[i]);
            }
        }
        if (mistakeExplanation != null && !mistakeExplanation.trim().isEmpty()) {
            builder.append("\n\n").append(mistakeExplanation);
        }
        updateStatusText(builder.toString());
        predictionPending = false;
    }

    @Override
    public boolean isPredictionPending() {
        return predictionPending;
    }

    @Override
    public void resetVisualizationState() {
        resetVisualization();
    }

    @Override
    public void restorePreviousSnapshot() {
        handlePreviousStep();
    }

    @Override
    public boolean canRestorePreviousSnapshot() {
        return timeline.size() > 1;
    }

    @Override
    public void captureCurrentSnapshot() {
        captureSnapshot();
    }

    @Override
    public boolean isPlaybackComplete() {
        return playbackComplete;
    }

    @Override
    public void advanceBinarySearchStep() {
        advanceSearchStep();
    }

    @Override
    public void advanceJumpSearchStep() {
        advanceSearchStep();
    }

    @Override
    public void advanceLinearSearchStep() {
        advanceSearchStep();
    }

    @Override
    public void advanceQuickSortFamilyStep() {
        if (workingValues.length <= 1) {
            finish("Quicksort completed");
            return;
        }

        if (quickLow == -1 && quickStackSize == 0 && stepIndex == -1) {
            pushQuickRange(0, workingValues.length - 1);
        }

        if (quickLow == -1) {
            if (!prepareNextQuickRange()) {
                finish("Quicksort completed");
            }
            return;
        }

        int pivotValue = workingValues[quickHigh];
        if (stepIndex < quickHigh) {
            int currentIndex = stepIndex;
            int currentValue = workingValues[currentIndex];
            if (currentValue <= pivotValue) {
                foundIndex++;
                if (foundIndex != currentIndex) {
                    swap(foundIndex, currentIndex);
                    updateStatusText(
                        "Value " + currentValue
                            + " <= pivot " + pivotValue
                            + ", so swap it into the left partition."
                    );
                    setHighlightedLines(5, 2);
                } else {
                    updateStatusText(
                        "Value " + currentValue
                            + " <= pivot " + pivotValue
                            + ", so it already belongs in the left partition."
                    );
                    setHighlightedLines(4, 1);
                }
            } else {
                updateStatusText(
                    "Value " + currentValue
                        + " > pivot " + pivotValue
                        + ", so leave it on the right side for now."
                );
                setHighlightedLines(4, 1);
            }
            updatePointerState("j", currentIndex, "pivot", quickHigh);
            updateArrayVisualization(currentIndex, quickHigh, foundIndex);
            syncScrollToFocus(currentIndex);
            stepIndex = currentIndex + 1;
            return;
        }

        int pivotIndex = foundIndex + 1;
        if (pivotIndex != quickHigh) {
            swap(pivotIndex, quickHigh);
        }
        updatePointerState("loc", pivotIndex, "pivot", quickHigh);
        updateArrayVisualization(pivotIndex, quickHigh, pivotIndex);
        setHighlightedLines(7, 3);
        updateStatusText(
            "Place pivot " + pivotValue
                + " at index " + pivotIndex
                + ". Left subarray: " + quickLow + " to " + (pivotIndex - 1)
                + ", right subarray: " + (pivotIndex + 1) + " to " + quickHigh + "."
        );
        syncScrollToFocus(pivotIndex);

        int leftLow = quickLow;
        int leftHigh = pivotIndex - 1;
        int rightLow = pivotIndex + 1;
        int rightHigh = quickHigh;
        quickLow = -1;
        quickHigh = -1;
        stepIndex = pivotIndex;
        auxIndex = -1;
        foundIndex = -1;

        if (rightLow < rightHigh) {
            pushQuickRange(rightLow, rightHigh);
        }
        if (leftLow < leftHigh) {
            pushQuickRange(leftLow, leftHigh);
        }
    }

    @Override
    public void advanceHeapSortStep() {
        advanceSortStep();
    }

    @Override
    public void advanceBubbleSortStep() {
        advanceSortStep();
    }

    @Override
    public void advanceSelectionSortStep() {
        advanceSortStep();
    }

    @Override
    public void advanceInsertionSortStep() {
        advanceSortStep();
    }

    @Override
    public void advanceLinearStructureStepCore() {
        StructureStepEngine.advanceLinearStructureStep(this);
    }

    @Override
    public void advanceGraphTraversalStep() {
        GraphStepEngine.advanceTraversalStep(this);
    }

    @Override
    public void advanceTreeTraversalStep() {
        TreeStepEngine.advanceTraversalStep(this);
    }

    @Override
    public int[] getWorkingValues() {
        return workingValues;
    }

    @Override
    public void setWorkingValues(int[] values) {
        workingValues = values;
        if (visualizerView != null) {
            if (VisualizationTechniqueRegistry.isAdvancedDs(techniqueId)) {
                visualizerView.setArrayState(values, visualizerView.getHighlightIndex(), visualizerView.getSecondaryHighlightIndex(), -1);
            } else if (isTreeOrGraph()) {
                visualizerView.setTraversalState(values, traversalOrder, stepIndex);
            }
        }
    }

    @Override
    public int getTarget() {
        return target;
    }

    @Override
    public int getStepIndex() {
        return stepIndex;
    }

    @Override
    public void setStepIndex(int value) {
        stepIndex = value;
        if (visualizerView != null) {
            visualizerView.setStepIndex(value);
        }
    }

    @Override
    public int getAuxIndex() {
        return auxIndex;
    }

    @Override
    public void setAuxIndex(int value) {
        auxIndex = value;
    }

    @Override
    public int getFoundIndex() {
        return foundIndex;
    }

    @Override
    public void setFoundIndex(int value) {
        foundIndex = value;
    }

    @Override
    public int getBinaryPhase() {
        return binaryPhase;
    }

    @Override
    public void setBinaryPhase(int value) {
        binaryPhase = value;
    }

    @Override
    public int getBinaryMidIndex() {
        return binaryMidIndex;
    }

    @Override
    public void setBinaryMidIndex(int value) {
        binaryMidIndex = value;
    }

    @Override
    public int getHeapSize() {
        return heapSize;
    }

    @Override
    public void setHeapSize(int value) {
        heapSize = value;
    }

    @Override
    public int getHeapSortPhase() {
        return heapSortPhase;
    }

    @Override
    public void setHeapSortPhase(int value) {
        heapSortPhase = value;
    }

    @Override
    public int getHeapSortHeapifyIndex() {
        return heapSortHeapifyIndex;
    }

    @Override
    public void setHeapSortHeapifyIndex(int value) {
        heapSortHeapifyIndex = value;
    }

    @Override
    public int getRadixDivisor() {
        return radixDivisor;
    }

    @Override
    public void setRadixDivisor(int value) {
        radixDivisor = value;
    }

    @Override
    public int getRadixMaxValue() {
        return radixMaxValue;
    }

    @Override
    public void setRadixMaxValue(int value) {
        radixMaxValue = value;
        if (visualizerView != null) {
            visualizerView.setRadixMaxValue(value);
        }
    }

    @Override
    public void sortWorkingValues() {
        Arrays.sort(workingValues);
    }

    @Override
    public void swapValues(int left, int right) {
        swap(left, right);
    }

    @Override
    public void updatePointerState(String primaryLabel, int primaryIndex, String secondaryLabel, int secondaryIndex) {
        visualizerView.setPointerState(primaryLabel, primaryIndex, secondaryLabel, secondaryIndex);
    }

    @Override
    public void setStructureMotion(String motion, int index) {
        visualizerView.setStructureMotion(motion, index);
    }

    @Override
    public void clearPointerState() {
        visualizerView.clearPointerState();
    }

    @Override
    public void updateTraversalVisualization(int[] traversalOrder, int activeStep) {
        visualizerView.setTraversalState(workingValues, traversalOrder, activeStep);
    }

    @Override
    public void setActiveEdge(int from, int to) {
        visualizerView.setActiveEdge(from, to);
    }

    @Override
    public void clearActiveEdge() {
        visualizerView.clearActiveEdge();
    }

    @Override
    public void setRadixState(int divisor, int activeBucket) {
        visualizerView.setRadixState(divisor, activeBucket);
    }

    @Override
    public void setRadixState(int divisor, int activeBucket, int processedCount) {
        visualizerView.setRadixState(divisor, activeBucket, processedCount);
    }

    @Override
    public int[] getTraversalOrder() {
        return traversalOrder;
    }

    @Override
    public void setTraversalOrder(int[] values) {
        traversalOrder = values == null ? new int[0] : values;
    }

    @Override
    public int[] buildTraversalOrder() {
        return buildTraversalOrderInternal();
    }

    @Override
    public String getPatternText() {
        return patternText != null ? patternText : "";
    }

    @Override
    public void setPatternText(String text) {
        this.patternText = text != null ? text : "";
    }

    @Override
    public String getPatternQuery() {
        return patternQuery != null ? patternQuery : "";
    }

    @Override
    public void setPatternQuery(String query) {
        this.patternQuery = query != null ? query : "";
    }

    @Override
    public int getPatternPreset() {
        return stringPatternPreset;
    }

    @Override
    public void setPatternPreset(int preset) {
        this.stringPatternPreset = preset;
    }

    @Override
    public void setPatternState(String text, String query, int windowStart, int matchIndex, boolean found) {
        this.patternText = text != null ? text : "";
        this.patternQuery = query != null ? query : "";
        this.patternWindowStart = windowStart;
        this.patternMatchIndex = matchIndex;
        this.patternFound = found;
        if (visualizerView != null) {
            visualizerView.setPatternState(this.patternText, this.patternQuery, windowStart, matchIndex, found);
        }
    }

    @Override
    public void setAuxiliaryArray(int[] aux) {
        if (visualizerView != null) {
            visualizerView.setAuxiliaryArray(aux);
        }
    }

    @Override
    public int getAdvancedDsPreset() {
        return advancedDsPreset;
    }

    @Override
    public void setAdvancedDsPreset(int preset) {
        this.advancedDsPreset = preset;
    }

    @Override
    public String getAdvancedDsCustomInput() {
        return advancedDsCustomInput != null ? advancedDsCustomInput : "";
    }

    @Override
    public void setAdvancedDsCustomInput(String input) {
        this.advancedDsCustomInput = input;
    }

    private void applyRandomData() {
        if (isExpressionStackTechnique()) {
            expressionInput = resolveRandomExpressionInput();
            valuesInput.setText(expressionInput);
            workingValues = new int[0];
            visualizerView.setExpressionInput(expressionInput);
            resetVisualization();
            return;
        }
        if ("pattern_matching".equals(techniqueId)) {
            int textLength = 10 + random.nextInt(6);
            patternText = buildRandomLetters(textLength);
            int maxPattern = Math.max(3, Math.min(5, textLength));
            int patternLength = 2 + random.nextInt(maxPattern - 1);
            int start = random.nextInt(textLength - patternLength + 1);
            patternQuery = patternText.substring(start, start + patternLength);
            workingValues = new int[patternText.length()];
            valuesInput.setText(patternText);
            targetInput.setText(patternQuery);
            resetVisualization();
            return;
        }
        if (isTreeOrGraph()) {
            int[] randomTree = new int[7];
            for (int i = 0; i < randomTree.length; i++) {
                randomTree[i] = random.nextInt(90) + 10;
            }
            workingValues = randomTree;
            valuesInput.setText(join(randomTree));
        } else {
            int size = usesStructureLimit() ? Math.max(1, Math.min(structureLimit, 6)) : 6;
            int[] randomValues = new int[size];
            for (int i = 0; i < size; i++) {
                randomValues[i] = random.nextInt(90) + 10;
            }
            workingValues = randomValues;
            valuesInput.setText(join(randomValues));
        }

        if (usesStructureLimit()) {
            syncStructureLimitInput();
        } else if (targetInputLayout.getVisibility() == View.VISIBLE) {
            target = workingValues[Math.max(0, random.nextInt(workingValues.length))];
            targetInput.setText(String.valueOf(target));
        } else if (isGraphTechnique()) {
            target = 0;
        }

        resetVisualization();
    }

    private void applyUserData() {
        try {
            if (isExpressionStackTechnique()) {
                String rawExpression = valuesInput.getText() == null ? "" : valuesInput.getText().toString().trim();
                String validationError = validateExpressionInput(rawExpression);
                if (validationError != null) {
                    statusText.setText(validationError);
                    return;
                }
                expressionInput = rawExpression;
                workingValues = new int[0];
                visualizerView.setExpressionInput(expressionInput);
                resetVisualization();
                return;
            }
            if ("pattern_matching".equals(techniqueId)) {
                String textValue = valuesInput.getText() == null ? "" : valuesInput.getText().toString().trim();
                String patternValue = targetInput.getText() == null ? "" : targetInput.getText().toString().trim();
                if (textValue.isEmpty() || patternValue.isEmpty()) {
                    statusText.setText("Enter both a text and a pattern.");
                    return;
                }
                patternText = textValue;
                patternQuery = patternValue;
                workingValues = new int[patternText.length()];
                resetVisualization();
                return;
            }
            String rawValues = valuesInput.getText() == null ? "" : valuesInput.getText().toString().trim();
            if (rawValues.isEmpty()) {
                statusText.setText("Please enter valid numbers like 5, 8, 12");
                return;
            }
            workingValues = parseArray(rawValues);
            if (workingValues.length == 0) {
                statusText.setText("Please enter at least one number.");
                return;
            }
            if ("radix_sort".equals(techniqueId) && containsNegativeValues(workingValues)) {
                statusText.setText("Radix Sort visualization currently supports only non-negative integers.");
                return;
            }
            if ("tree_traversal".equals(techniqueId)
                || "preorder_traversal".equals(techniqueId)
                || "postorder_traversal".equals(techniqueId)
                || "bst_search_parent_tracking".equals(techniqueId)
                || "bst_insertion".equals(techniqueId)
                || "bst_deletion_case_a".equals(techniqueId)
                || "bst_deletion_case_b".equals(techniqueId)
                || "bst_deletion_del".equals(techniqueId)
                || "max_heap_insertion".equals(techniqueId)
                || "max_heap_deletion".equals(techniqueId)) {
                workingValues = ensureLength(workingValues, 7);
            }
            if (usesStructureLimit() && workingValues.length > structureLimit) {
                statusText.setText(getString(R.string.vis_structure_limit_too_small, workingValues.length));
                return;
            }
            if (usesStructureLimit()) {
                String rawLimit = compactLimitInput != null && compactLimitInput.getText() != null
                    ? compactLimitInput.getText().toString().trim()
                    : "";
                Integer parsedLimit = parseStructureLimit(rawLimit);
                if (parsedLimit == null) {
                    statusText.setText(getString(R.string.vis_structure_limit_invalid));
                    return;
                }
                if (parsedLimit < workingValues.length) {
                    statusText.setText(getString(R.string.vis_structure_limit_too_small, workingValues.length));
                    return;
                }
                structureLimit = parsedLimit;
                syncStructureLimitInput();
            } else if (targetInputLayout.getVisibility() == View.VISIBLE) {
                String rawTarget = targetInput.getText() == null ? "" : targetInput.getText().toString().trim();
                if (!rawTarget.isEmpty()) {
                    target = Integer.parseInt(rawTarget);
                }
            }
            resetVisualization();
        } catch (Exception error) {
            statusText.setText("Please enter valid numbers like 5, 8, 12");
        }
    }

    private boolean isResettingVisualization = false;

    private void resetVisualization() {
        if (isResettingVisualization) {
            return;
        }
        isResettingVisualization = true;
        try {
            if (playbackController != null) {
                playbackController.stop();
                playbackController.restoreState(PlaybackState.initial());
            }
            handler.removeCallbacksAndMessages(null);
            resetRuntimeOnly();
            bindCurrentState("Press Start to begin");
            captureSnapshot();
        } finally {
            isResettingVisualization = false;
        }
    }

    private void resetRuntimeOnly() {
        running = false;
        paused = true;
        playbackComplete = false;
        hideCompletionBanner();
        lastPulsedPlaybackStep = -1;
        timeline.clear();
        stepIndex = -1;
        auxIndex = -1;
        foundIndex = -1;
        binaryPhase = 0;
        binaryMidIndex = -1;
        traversalOrder = new int[0];
        mergeLeftValues = new int[0];
        mergeRightValues = new int[0];
        mergeOutputValues = new int[0];
        mergeWorkingValues = new int[0];
        mergeLeftCursor = 0;
        mergeRightCursor = 0;
        mergeWriteCursor = -1;
        mergeRunSize = 1;
        mergePassStart = 0;
        radixDivisor = 1;
        radixMaxValue = 0;
        quickLow = -1;
        quickHigh = -1;
        quickStackLow = new int[0];
        quickStackHigh = new int[0];
        quickStackSize = 0;
        heapSortPhase = 0;
        heapSortHeapifyIndex = -1;
        patternWindowStart = -1;
        patternMatchIndex = -1;
        patternFound = false;
        unionPath = new int[0];
        unionPathStep = -1;
        unionFocusNode = -1;
        unionRootNode = -1;
        dijkstraDistances = new int[0];
        dijkstraSettled = new boolean[0];
        dijkstraCurrentNode = -1;
        dijkstraPhase = 0;
        dijkstraNeighborIndex = 0;
        dijkstraWeights = new int[0][0];
        huffmanQueueWeights = new int[0];
        huffmanMergedWeight = -1;
        syncPlayButtonVisual(false);
        visualizerView.clearPointerState();
        visualizerView.clearActiveEdge();
        visualizerView.setStructureMotion("", -1);
        visualizerView.clearCustomSortState();
        visualizerView.clearMatrixState();
        visualizerView.clearPatternState();
        visualizerView.setExpressionInput(expressionInput);
        visualizerView.setUnionFindState(new int[0], -1, -1);
        visualizerView.setDijkstraState(new int[0], new boolean[0], -1);
        visualizerView.setHuffmanState(workingValues, new int[0], -1, -1, -1, -1);
        visualizerView.setActiveNodes(-1, -1);
        visualizerView.setStepIndex(-1);
        setHighlightedLines(-1, -1);

        if ("warshall_algorithm".equals(techniqueId)
            || "floyd_algorithm".equals(techniqueId)
            || "shortest_paths".equals(techniqueId)
            || "graph_floyd_warshall".equals(techniqueId)
            || VisualizationTechniqueRegistry.isDynamicProgramming(techniqueId)
            || VisualizationTechniqueRegistry.isBacktracking(techniqueId)) {
            initializeCustomSortPreview();
            visualizerView.setMatrixState(matrixValues, matrixK, matrixRow, matrixCol, resolveMatrixLabel());
        } else if ("pattern_matching".equals(techniqueId) || VisualizationTechniqueRegistry.isStringPattern(techniqueId)) {
            patternWindowStart = 0;
            patternMatchIndex = 0;
            visualizerView.setPatternState(patternText, patternQuery, patternWindowStart, patternMatchIndex, false);
        } else if ("dijkstra_algorithm".equals(techniqueId)) {
            initializeDijkstraPreview();
        } else if ("huffman_algorithm".equals(techniqueId)) {
            initializeHuffmanPreview();
        } else if (isTreeOrGraph()) {
            visualizerView.setTraversalState(workingValues, traversalOrder, -1);
        } else if (VisualizationTechniqueRegistry.isAdvancedDs(techniqueId)) {
            initializeCustomSortPreview();
        } else {
            updateArrayVisualization(-1, -1, -1);
            initializeCustomSortPreview();
        }
        updateBinaryChips();
        syncScrollToFocus(-1);
    }

    @Override
    public void bindCurrentState(String message) {
        if ("warshall_algorithm".equals(techniqueId)
            || "floyd_algorithm".equals(techniqueId)
            || "shortest_paths".equals(techniqueId)
            || "graph_floyd_warshall".equals(techniqueId)
            || VisualizationTechniqueRegistry.isDynamicProgramming(techniqueId)
            || VisualizationTechniqueRegistry.isBacktracking(techniqueId)) {
            visualizerView.setMatrixState(matrixValues, matrixK, matrixRow, matrixCol, resolveMatrixLabel());
        } else if ("pattern_matching".equals(techniqueId) || VisualizationTechniqueRegistry.isStringPattern(techniqueId)) {
            visualizerView.setPatternState(patternText, patternQuery, patternWindowStart, patternMatchIndex, patternFound);
        } else if ("dijkstra_algorithm".equals(techniqueId)) {
            visualizerView.setDijkstraState(dijkstraDistances, dijkstraSettled, dijkstraCurrentNode);
        } else if ("huffman_algorithm".equals(techniqueId)) {
            visualizerView.setHuffmanState(workingValues, huffmanQueueWeights, huffmanMergedWeight, huffmanMergedLeft, huffmanMergedRight, stepIndex);
        } else if (isTreeOrGraph()) {
            visualizerView.setTraversalState(workingValues, traversalOrder, stepIndex);
        } else if (VisualizationTechniqueRegistry.isAdvancedDs(techniqueId)) {
            visualizerView.invalidate();
        } else {
            updateArrayVisualization(stepIndex, auxIndex, foundIndex);
            refreshCustomSortScene();
        }

        if ("radix_sort".equals(techniqueId) && stepIndex == -1) {
            updateStatusText("Current Pass: Units Digit");
        } else {
            updateStatusText(message);
        }
    }

    @Override
    public void updateArrayVisualization(int active, int secondary, int found) {
        visualizerView.setArrayState(
            workingValues,
            active,
            secondary,
            found,
            resolveCompletedStart(),
            resolveCompletedEnd()
        );
    }

    private int resolveCompletedStart() {
        if ("radix_sort".equals(techniqueId) && (playbackComplete || (radixMaxValue > 0 && radixDivisor > radixMaxValue))) {
            return 0;
        }
        if ("heapsort".equals(techniqueId) && heapSize >= 0 && heapSize < workingValues.length) {
            return heapSize;
        }
        if ("bubble_sort".equals(techniqueId) && stepIndex >= workingValues.length - 1 && workingValues.length > 0) {
            return 0;
        }
        if ("bubble_sort".equals(techniqueId) && stepIndex > 0) {
            return Math.max(0, workingValues.length - stepIndex);
        }
        if ("selection_sort".equals(techniqueId) && stepIndex >= workingValues.length - 1 && workingValues.length > 0) {
            return 0;
        }
        if ("insertion_sort".equals(techniqueId) && stepIndex >= workingValues.length && workingValues.length > 0) {
            return 0;
        }
        if (("selection_sort".equals(techniqueId) || "insertion_sort".equals(techniqueId)) && stepIndex > 0) {
            return 0;
        }
        return -1;
    }

    private int resolveCompletedEnd() {
        if ("radix_sort".equals(techniqueId) && (playbackComplete || (radixMaxValue > 0 && radixDivisor > radixMaxValue))) {
            return workingValues.length - 1;
        }
        if ("heapsort".equals(techniqueId) && heapSize >= 0 && heapSize < workingValues.length) {
            return workingValues.length - 1;
        }
        if ("bubble_sort".equals(techniqueId) && stepIndex >= workingValues.length - 1 && workingValues.length > 0) {
            return workingValues.length - 1;
        }
        if ("bubble_sort".equals(techniqueId) && stepIndex > 0) {
            return workingValues.length - 1;
        }
        if ("selection_sort".equals(techniqueId) && stepIndex >= workingValues.length - 1 && workingValues.length > 0) {
            return workingValues.length - 1;
        }
        if ("insertion_sort".equals(techniqueId) && stepIndex >= workingValues.length && workingValues.length > 0) {
            return workingValues.length - 1;
        }
        if (("selection_sort".equals(techniqueId) || "insertion_sort".equals(techniqueId)) && stepIndex > 0) {
            return Math.min(workingValues.length - 1, stepIndex - 1);
        }
        return -1;
    }

    @Override
    public void updateStatusText(String message) {
        if ("pattern_matching".equals(techniqueId)) {
            statusText.setText("Pattern: " + patternQuery + " | " + message);
        } else if (shouldShowTargetContext()) {
            statusText.setText("Target: " + target + " | " + message);
        } else {
            statusText.setText(message);
        }
        if (visualizerView != null) {
            visualizerView.setInlineTeaching(message, target);
        }
        currentOperationLabel = resolveOperationLabel(message);
        updatePlayerDetails(message);
        refreshTeachingDecor(message);
        if (explanationText != null) {
            explanationText.setText(buildExplanation(message));
            animatePanelTextChange(explanationText, 220L);
        }
        animateLearningPulse();
        updateStudyModeState();
        updateBinaryChips();
        publishLearningState(message);
    }

    private void publishLearningState(String message) {
        if (sessionViewModel == null) {
            return;
        }
        sessionViewModel.setCurrentLearningStep(buildLearningStepState(message));
    }

    private StepState buildLearningStepState(String message) {
        StepState.Builder builder = new StepState.Builder()
            .setAlgorithmId(techniqueId)
            .setAlgorithmTitle(techniqueTitle)
            .setStructureType(resolveLearningStructureType())
            .setOperationType(resolveLearningOperationType())
            .setStepNumber(Math.max(0, timeline.size()))
            .setLinearData(toLearningValues())
            .setSummary(message)
            .setHint(resolveLearningHint())
            .setCorrectAction(resolveLearningAction())
            .setDecisionContext(new DecisionContext(
                resolveDecisionType(),
                resolveLearningPrompt(),
                buildExplanation(message),
                resolvePlausibleActions()
            ));

        for (StepPointer pointer : resolveLearningPointers()) {
            builder.addPointer(pointer.label, pointer.index);
        }

        builder.putMetadata("target", String.valueOf(target));
        builder.putMetadata("mode", currentTraversalMode);
        builder.putMetadata("message", message == null ? "" : message);
        builder.putMetadata("relation", resolveRelationMetadata());
        return builder.build();
    }

    private List<Integer> toLearningValues() {
        List<Integer> values = new ArrayList<>();
        for (int value : workingValues) {
            values.add(value);
        }
        return values;
    }

    private StructureType resolveLearningStructureType() {
        if (isSearchingMode()) {
            return StructureType.SEARCH;
        }
        if (isSortingMode()) {
            return StructureType.SORT;
        }
        if ("stack".equals(techniqueId)) {
            return StructureType.STACK;
        }
        if ("queue".equals(techniqueId) || "deque".equals(techniqueId)) {
            return StructureType.QUEUE;
        }
        if (isStructureMode()) {
            return StructureType.LINKED_LIST;
        }
        if (isGraphTechnique()) {
            return StructureType.GRAPH;
        }
        return StructureType.TREE;
    }

    private OperationType resolveLearningOperationType() {
        if (foundIndex >= 0) {
            return OperationType.FOUND;
        }
        if (isSortingMode() && auxIndex >= 0) {
            return OperationType.COMPARE;
        }
        if (isSearchingMode()) {
            return OperationType.COMPARE;
        }
        if (isStructureMode()) {
            return OperationType.TRAVERSE;
        }
        if (isGraphTechnique()) {
            return OperationType.EXPLORE_EDGE;
        }
        return OperationType.TRAVERSE;
    }

    private DecisionType resolveDecisionType() {
        if (isSearchingMode()) {
            return DecisionType.POINTER_MOVEMENT;
        }
        if (isSortingMode()) {
            return DecisionType.COMPARISON_RESULT;
        }
        if (isStructureMode()) {
            return DecisionType.STRUCTURE_CHANGE;
        }
        return DecisionType.TRAVERSAL_CHOICE;
    }

    private String resolveLearningPrompt() {
        if ("binary_search".equals(techniqueId)) {
            return "What will happen next in Binary Search?";
        }
        if (isSearchingMode()) {
            return "What should the search do next?";
        }
        if (isSortingMode()) {
            return "What should happen to the active values next?";
        }
        if (isStructureMode()) {
            return "What structural change or pointer move should happen next?";
        }
        if (isGraphTechnique()) {
            return "What should the traversal do next?";
        }
        return "What should happen next in this algorithm?";
    }

    private String resolveLearningHint() {
        if ("binary_search".equals(techniqueId)) {
            return "Watch low, mid, high, and compare the target with the middle value.";
        }
        if (isSortingMode()) {
            return "Compare the active values before deciding whether they need to move.";
        }
        if (isStructureMode()) {
            return "Track the active pointer such as top, front, rear, or next.";
        }
        if (isGraphTechnique()) {
            return "Think about the current frontier before choosing the next node or edge.";
        }
        return "Follow the highlighted state before answering.";
    }

    private ActionType resolveLearningAction() {
        String message = statusText == null || statusText.getText() == null ? "" : statusText.getText().toString().toLowerCase();
        if (message.contains("found")) {
            return ActionType.FOUND;
        }
        if ("binary_search".equals(techniqueId)) {
            if (binaryMidIndex >= 0 && binaryMidIndex < workingValues.length) {
                int midValue = workingValues[binaryMidIndex];
                if (target > midValue) {
                    return ActionType.MOVE_RIGHT;
                }
                if (target < midValue) {
                    return ActionType.MOVE_LEFT;
                }
                return ActionType.FOUND;
            }
        }
        if (isSortingMode()) {
            if (stepIndex >= 0 && auxIndex >= 0 && stepIndex < workingValues.length && auxIndex < workingValues.length) {
                return workingValues[stepIndex] > workingValues[auxIndex] ? ActionType.SWAP : ActionType.NO_SWAP;
            }
            return ActionType.MOVE_NEXT;
        }
        if ("stack".equals(techniqueId)) {
            return message.contains("pop") ? ActionType.POP_ITEM : ActionType.PUSH_ITEM;
        }
        if ("queue".equals(techniqueId) || "deque".equals(techniqueId)) {
            return message.contains("dequeue") || message.contains("removed") ? ActionType.DEQUEUE_NODE : ActionType.ENQUEUE_NEIGHBOR;
        }
        if (isStructureMode()) {
            return ActionType.INSERT_NODE;
        }
        if (isGraphTechnique()) {
            return ActionType.ENQUEUE_NEIGHBOR;
        }
        return ActionType.VISIT_NODE;
    }

    private List<ActionType> resolvePlausibleActions() {
        List<ActionType> actions = new ArrayList<>();
        if ("binary_search".equals(techniqueId)) {
            actions.add(ActionType.MOVE_LEFT);
            actions.add(ActionType.MOVE_RIGHT);
            actions.add(ActionType.FOUND);
            return actions;
        }
        if (isSearchingMode()) {
            actions.add(ActionType.MOVE_NEXT);
            actions.add(ActionType.MOVE_LEFT);
            actions.add(ActionType.MOVE_RIGHT);
            actions.add(ActionType.FOUND);
            return actions;
        }
        if (isSortingMode()) {
            actions.add(ActionType.SWAP);
            actions.add(ActionType.NO_SWAP);
            actions.add(ActionType.MOVE_RIGHT);
            return actions;
        }
        if ("stack".equals(techniqueId)) {
            actions.add(ActionType.PUSH_ITEM);
            actions.add(ActionType.POP_ITEM);
            actions.add(ActionType.MOVE_NEXT);
            return actions;
        }
        if (isStructureMode()) {
            actions.add(ActionType.INSERT_NODE);
            actions.add(ActionType.DELETE);
            actions.add(ActionType.MOVE_NEXT);
            return actions;
        }
        if (isGraphTechnique()) {
            actions.add(ActionType.ENQUEUE_NEIGHBOR);
            actions.add(ActionType.VISIT_RIGHT);
            actions.add(ActionType.DEQUEUE_NODE);
            return actions;
        }
        actions.add(ActionType.VISIT_LEFT);
        actions.add(ActionType.VISIT_NODE);
        actions.add(ActionType.VISIT_RIGHT);
        return actions;
    }

    private List<StepPointer> resolveLearningPointers() {
        List<StepPointer> pointers = new ArrayList<>();
        if ("binary_search".equals(techniqueId)) {
            pointers.add(new StepPointer("low", stepIndex));
            pointers.add(new StepPointer("mid", binaryMidIndex));
            pointers.add(new StepPointer("high", auxIndex));
            return pointers;
        }
        if ("bubble_sort".equals(techniqueId)) {
            pointers.add(new StepPointer("pass", stepIndex));
            pointers.add(new StepPointer("j", auxIndex));
            if (auxIndex >= 0) {
                pointers.add(new StepPointer("j+1", auxIndex + 1));
            }
            return pointers;
        }
        if ("selection_sort".equals(techniqueId)) {
            pointers.add(new StepPointer("i", stepIndex));
            pointers.add(new StepPointer("scan", auxIndex));
            pointers.add(new StepPointer("min", foundIndex));
            return pointers;
        }
        if ("insertion_sort".equals(techniqueId)) {
            pointers.add(new StepPointer("i", stepIndex));
            pointers.add(new StepPointer("key", auxIndex));
            if (auxIndex > 0) {
                pointers.add(new StepPointer("prev", auxIndex - 1));
            }
            return pointers;
        }
        if ("quicksort".equals(techniqueId)) {
            pointers.add(new StepPointer("pivot", stepIndex));
            pointers.add(new StepPointer("scan", auxIndex));
            pointers.add(new StepPointer("partition", foundIndex));
            return pointers;
        }
        if ("heapsort".equals(techniqueId)) {
            pointers.add(new StepPointer("root", stepIndex));
            pointers.add(new StepPointer("target", auxIndex));
            if (heapSize >= 0) {
                pointers.add(new StepPointer("heapSize", heapSize));
            }
            return pointers;
        }
        if (stepIndex >= 0) {
            pointers.add(new StepPointer("active", stepIndex));
        }
        if (auxIndex >= 0) {
            pointers.add(new StepPointer("secondary", auxIndex));
        }
        if (foundIndex >= 0) {
            pointers.add(new StepPointer("found", foundIndex));
        }
        return pointers;
    }

    private String resolveRelationMetadata() {
        if ("binary_search".equals(techniqueId) && binaryMidIndex >= 0 && binaryMidIndex < workingValues.length) {
            int midValue = workingValues[binaryMidIndex];
            if (target > midValue) {
                return "greater";
            }
            if (target < midValue) {
                return "less";
            }
            return "equal";
        }
        if (isSortingMode() && stepIndex >= 0 && auxIndex >= 0 && stepIndex < workingValues.length && auxIndex < workingValues.length) {
            return workingValues[stepIndex] > workingValues[auxIndex] ? "out_of_order" : "in_order";
        }
        return "";
    }

    private void updateStudyModeState() {
        if (studyTitleText == null || studyMessageText == null) {
            return;
        }
        studyTitleText.setText(techniqueTitle);
        String explanation = explanationText == null || explanationText.getText() == null
            ? ""
            : explanationText.getText().toString();
        studyMessageText.setText(explanation.isEmpty() ? "Follow the current step carefully." : explanation);

        String[] chips = resolveStudyChips();
        studyChipPrimaryText.setText(chips[0]);
        studyChipSecondaryText.setText(chips[1]);
        studyChipTertiaryText.setText(chips[2]);
        if (nowDoingLabel != null) {
            nowDoingLabel.setText("Now doing: " + currentOperationLabel);
            nowDoingLabel.setTextColor(resolveAccentColor());
        }
    }

    private String[] resolveStudyChips() {
        if ("binary_search".equals(techniqueId)) {
            return new String[] {
                stepIndex >= 0 ? "Low: " + stepIndex : "Low: -",
                binaryMidIndex >= 0 ? "Mid: " + binaryMidIndex : "Mid: -",
                auxIndex >= 0 ? "High: " + auxIndex : "High: -"
            };
        }
        if (isSearchingMode()) {
            return new String[] {
                stepIndex >= 0 ? "Index: " + stepIndex : "Index: -",
                shouldShowTargetContext() ? "Target: " + target : "Target: -",
                foundIndex >= 0 ? "Found: " + foundIndex : "Result: pending"
            };
        }
        if (isSortingMode()) {
            return new String[] {
                stepIndex >= 0 ? "Step: " + (stepIndex + 1) : "Step: 0",
                auxIndex >= 0 ? "Compare: " + auxIndex : "Compare: -",
                resolveCompletedEnd() >= 0 ? "Sorted: " + (resolveCompletedEnd() + 1) : "Sorted: in progress"
            };
        }
        if (isStructureMode()) {
            return new String[] {
                stepIndex >= 0 ? "Focus: " + stepIndex : "Focus: -",
                workingValues.length > 0 ? "Size: " + workingValues.length : "Size: 0",
                currentTraversalMode.isEmpty() ? "Mode: live" : "Mode: " + currentTraversalMode
            };
        }
        if (isGraphTechnique()) {
            return new String[] {
                currentTraversalMode.isEmpty() ? "Mode: graph" : "Mode: " + currentTraversalMode,
                stepIndex >= 0 ? "Step: " + (stepIndex + 1) : "Step: 0",
                activeEdgeLabel()
            };
        }
        return new String[] {
            currentTraversalMode.isEmpty() ? "Mode: tree" : "Mode: " + currentTraversalMode,
            stepIndex >= 0 ? "Node: " + stepIndex : "Node: -",
            shouldShowTargetContext() ? "Target: " + target : "Target: tree"
        };
    }

    private String activeEdgeLabel() {
        int from = visualizerView.getActiveEdgeFrom();
        int to = visualizerView.getActiveEdgeTo();
        if (from >= 0 && to >= 0) {
            return "Edge: " + from + "→" + to;
        }
        return "Edge: waiting";
    }

    private void animateLearningPulse() {
        if (studyMessageText != null) {
            studyMessageText.animate().cancel();
            studyMessageText.setAlpha(0.74f);
            studyMessageText.animate().alpha(1f).setDuration(180L).start();
        }
        if (controlsGroup != null) {
            controlsGroup.animate().cancel();
            controlsGroup.setScaleX(0.995f);
            controlsGroup.setScaleY(0.995f);
            controlsGroup.animate().scaleX(1f).scaleY(1f).setDuration(160L).start();
        }
        if (nowDoingLabel != null) {
            nowDoingLabel.animate().cancel();
            nowDoingLabel.setAlpha(0.7f);
            nowDoingLabel.animate().alpha(1f).setDuration(180L).start();
        }
        if (visualizerView != null) {
            visualizerView.animate().cancel();
            visualizerView.setScaleX(0.992f);
            visualizerView.setScaleY(0.992f);
            visualizerView.animate().scaleX(1f).scaleY(1f).setDuration(180L).start();
        }
    }

    private void updatePlayerDetails(String message) {
        int stepNumber = Math.max(0, timeline.size() - 1);
        String nextTitle = "Step " + stepNumber + ": " + currentOperationLabel;
        String explanation = buildExplanation(message);
        String resolvedBody = explanation.isEmpty() ? message : explanation;
        if (nextTitle.equals(lastStepDetailsTitle) && resolvedBody.equals(lastStepDetailsBody)) {
            if (stepDetailsTitle != null) {
                stepDetailsTitle.setTextColor(resolveAccentColor());
            }
            return;
        }

        lastStepDetailsTitle = nextTitle;
        lastStepDetailsBody = resolvedBody;
        animateStepDetailsChange(stepNumber, nextTitle, resolvedBody);
    }

    private void animateStepDetailsChange(int stepNumber, String title, String body) {
        if (stepDetailsTitle == null || stepDetailsBody == null || stepDetailsCard == null) {
            return;
        }
        stepDetailsCard.animate().cancel();
        stepDetailsCard.setVisibility(View.GONE);
        stepDetailsCard.setAlpha(0f);
        lastAnimatedStepNumber = stepNumber;
        return;
        /*
        boolean forward = stepNumber >= lastAnimatedStepNumber;
        lastAnimatedStepNumber = stepNumber;
        Runnable applyContent = () -> {
            stepDetailsTitle.setText(title);
            stepDetailsTitle.setTextColor(resolveAccentColor());
            stepDetailsBody.setText(buildHighlightedDetails(body));
        };

        if (stepDetailsCard.getAlpha() == 0f || !stepDetailsCard.isLaidOut()) {
            applyContent.run();
            stepDetailsCard.setAlpha(1f);
            stepDetailsCard.setTranslationX(0f);
            if (contentScrollView != null && focusModeEnabled) {
                contentScrollView.post(() -> {
                    int scrollY = stepDetailsCard.getTop() - (int) dpToPx(16);
                    contentScrollView.smoothScrollTo(0, Math.max(0, scrollY));
                });
            }
            return;
        }

        float exitShift = forward ? -22f : 22f;
        float enterShift = forward ? 22f : -22f;
        stepDetailsCard.animate().cancel();
        stepDetailsCard.animate()
            .alpha(0f)
            .translationX(exitShift)
            .setDuration(120L)
            .withEndAction(() -> {
                applyContent.run();
                stepDetailsCard.setTranslationX(enterShift);
                stepDetailsCard.animate()
                    .alpha(1f)
                    .translationX(0f)
                    .setDuration(180L)
                    .withEndAction(() -> {
                        if (contentScrollView != null && focusModeEnabled) {
                            contentScrollView.post(() -> {
                                int scrollY = stepDetailsCard.getTop() - (int) dpToPx(16);
                                contentScrollView.smoothScrollTo(0, Math.max(0, scrollY));
                            });
                        }
                    })
                    .start();
            })
            .start();
        */
    }

    private CharSequence buildHighlightedDetails(String text) {
        if (text == null || text.trim().isEmpty()) {
            return "";
        }
        SpannableString spannable = new SpannableString(text);
        highlightKeyword(spannable, text, "mid", android.graphics.Color.parseColor("#60A5FA"));
        highlightKeyword(spannable, text, "low", android.graphics.Color.parseColor("#60A5FA"));
        highlightKeyword(spannable, text, "high", android.graphics.Color.parseColor("#60A5FA"));
        highlightKeyword(spannable, text, "pivot", android.graphics.Color.parseColor("#F59E0B"));
        highlightKeyword(spannable, text, "swap", android.graphics.Color.parseColor("#34D399"));
        highlightKeyword(spannable, text, "compare", android.graphics.Color.parseColor("#C084FC"));
        highlightKeyword(spannable, text, "target", resolveAccentColor());
        return spannable;
    }

    private void highlightKeyword(SpannableString spannable, String fullText, String keyword, int color) {
        String safeText = fullText.toLowerCase();
        String safeKeyword = keyword.toLowerCase();
        int index = safeText.indexOf(safeKeyword);
        while (index >= 0) {
            int end = index + safeKeyword.length();
            spannable.setSpan(new ForegroundColorSpan(color), index, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), index, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            index = safeText.indexOf(safeKeyword, end);
        }
    }

    private String resolveOperationLabel(String message) {
        if ("linear_search".equals(techniqueId)) {
            return resolveLinearSearchOperationLabel(message);
        }
        if (isSortingMode()) {
            String sortLabel = SortVisualizationEngine.resolveOperationLabel(
                techniqueId,
                message,
                stepIndex,
                auxIndex,
                foundIndex,
                heapSize,
                heapSortPhase
            );
            if (sortLabel != null && !sortLabel.trim().isEmpty()) {
                return sortLabel;
            }
        }
        String safe = message == null ? "" : message.toLowerCase();
        if (safe.contains("start") || safe.contains("align") || safe.contains("initialize")) {
            return "Starting";
        }
        if (safe.contains("inspect") || safe.contains("check") || safe.contains("peek") || safe.contains("front element")
            || safe.contains("rear element") || safe.contains("top element")) {
            return "Inspecting";
        }
        if (safe.contains("swap") || safe.contains("swapping")) {
            return "Swapping";
        }
        if (safe.contains("not found")) {
            return "Not Found";
        }
        if (safe.contains("compar") || safe.contains("match")) {
            return "Comparing";
        }
        if (safe.contains("relax")) {
            return "Relaxing";
        }
        if (safe.contains("rotate")) {
            return "Rotating";
        }
        if (safe.contains("heap")) {
            return "Heapifying";
        }
        if (safe.contains("move low") || safe.contains("move right") || safe.contains("greater")) {
            return "Moving Right";
        }
        if (safe.contains("move high") || safe.contains("move left") || safe.contains("smaller")) {
            return "Moving Left";
        }
        if (safe.contains("slide") || safe.contains("advance") || safe.contains("shift")) {
            return "Advancing";
        }
        if (safe.contains("visit")) {
            return "Visiting Node";
        }
        if (safe.contains("enqueue") || safe.contains("push") || safe.contains("insert")) {
            return "Inserting";
        }
        if (safe.contains("dequeue") || safe.contains("pop") || safe.contains("delete") || safe.contains("remov")) {
            return "Removing";
        }
        if (safe.contains("found")) {
            return "Found";
        }
        if (safe.contains("merge")) {
            return "Merging";
        }
        if (safe.contains("settled") || safe.contains("confirmed") || safe.contains("restored")
            || safe.contains("completed") || safe.contains("mark end")) {
            return "Completed";
        }
        if (safe.contains("partition")) {
            return "Partitioning";
        }
        return "Tracing";
    }

    private void refreshTimeline() {
        if (timelineRow != null) {
            timelineRow.removeAllViews();
        }
        if (timelineLabel != null) {
            timelineLabel.setVisibility(View.GONE);
        }
        if (timelineScrollView != null) {
            timelineScrollView.setVisibility(View.GONE);
        }
    }

    private void jumpToTimelineStep(int index) {
        if (index < 0 || index >= timeline.size()) {
            return;
        }
        handler.removeCallbacksAndMessages(null);
        Snapshot snapshot = timeline.get(index);
        timeline.truncateAfter(index);
        restoreSnapshot(snapshot);
        running = false;
        paused = false;
        syncPlayButtonVisual(false);
        refreshTimeline();
    }

    private boolean safeEquals(String left, String right) {
        return left == null ? right == null : left.equals(right);
    }

    private void toggleFocusMode() {
        focusModeEnabled = !focusModeEnabled;
        if (focusModeEnabled) {
            statusVisibilityBeforeFocus = statusText.getVisibility();
            explanationVisibilityBeforeFocus = explanationText.getVisibility();
            traversalVisibilityBeforeFocus = traversalGroup == null ? View.GONE : traversalGroup.getVisibility();
            inputRowVisibilityBeforeFocus = inputRow == null ? View.GONE : inputRow.getVisibility();
            inputActionsVisibilityBeforeFocus = inputActionsRow == null ? View.GONE : inputActionsRow.getVisibility();
            operationsVisibilityBeforeFocus = operationsGroup == null ? View.GONE : operationsGroup.getVisibility();
            controlsVisibilityBeforeFocus = controlsGroup == null ? View.GONE : controlsGroup.getVisibility();

            studyHeader.setVisibility(View.VISIBLE);
            statusText.setVisibility(View.GONE);
            explanationText.setVisibility(View.GONE);
            if (traversalGroup != null) {
                traversalGroup.setVisibility(View.GONE);
            }
            if (inputRow != null) {
                inputRow.setVisibility(View.GONE);
            }
            if (inputActionsRow != null) {
                inputActionsRow.setVisibility(View.GONE);
            }
            if (operationsGroup != null) {
                operationsGroup.setVisibility(isStructureMode() ? operationsVisibilityBeforeFocus : View.GONE);
            }
            if (controlsGroup != null) {
                controlsGroup.setVisibility(View.GONE);
            }
            attachOperationsToOverlayHost();
        } else {
            studyHeader.setVisibility(View.GONE);
            statusText.setVisibility(statusVisibilityBeforeFocus);
            explanationText.setVisibility(explanationVisibilityBeforeFocus);
            if (traversalGroup != null) {
                traversalGroup.setVisibility(traversalVisibilityBeforeFocus);
            }
            if (inputRow != null) {
                inputRow.setVisibility(inputRowVisibilityBeforeFocus);
            }
            if (inputActionsRow != null) {
                inputActionsRow.setVisibility(inputActionsVisibilityBeforeFocus);
            }
            if (operationsGroup != null) {
                operationsGroup.setVisibility(isStructureMode() ? View.VISIBLE : operationsVisibilityBeforeFocus);
            }
            if (controlsGroup != null) {
                controlsGroup.setVisibility(controlsVisibilityBeforeFocus);
            }
            if (isStructureMode()) {
                attachOperationsToOverlayHost();
            } else {
                restoreOperationsPosition();
            }
        }

        if (getActivity() instanceof TechniqueDetailActivity) {
            ((TechniqueDetailActivity) getActivity()).setStudyModeEnabled(focusModeEnabled);
        }

        focusModeButton.setText(focusModeEnabled ? "Exit Study" : "Study Mode");
        adjustVisualizerHeightForFocusMode();
        if (focusModeEnabled) {
            scrollToStudyOperationZone();
        } else {
            visualizerScrollView.post(() -> visualizerScrollView.fullScroll(View.FOCUS_UP));
        }
        updateStudyModeState();
        updateBinaryChips();
    }

    private void attachOperationsToOverlayHost() {
        if (!isStructureMode() || operationsGroup == null || overlayActionsHost == null) {
            return;
        }
        overlayActionsHost.setVisibility(View.VISIBLE);
        overlayActionsHost.setAlpha(running ? 0f : 1f);
        overlayActionsHost.setTranslationY(running ? -dpToPx(14) : 0f);
        if (operationsTitle != null) {
            operationsTitle.setVisibility(View.GONE);
        }
        operationsGroup.setBackground(null);
        operationsGroup.setPadding(0, 0, 0, 0);
        if (studyActionsHost != null) {
            studyActionsHost.setVisibility(View.GONE);
        }
        if (operationsGroup.getParent() == overlayActionsHost) {
            return;
        }
        ViewGroup currentParent = operationsGroup.getParent() instanceof ViewGroup ? (ViewGroup) operationsGroup.getParent() : null;
        if (currentParent != null) {
            currentParent.removeView(operationsGroup);
        }
        overlayActionsHost.addView(operationsGroup);
    }

    private void restoreOperationsPosition() {
        if (operationsGroup == null || scrollContentLayout == null || originalOperationsIndex < 0) {
            return;
        }
        if (studyActionsHost != null) {
            studyActionsHost.setVisibility(View.GONE);
        }
        if (overlayActionsHost != null) {
            overlayActionsHost.setVisibility(View.GONE);
        }
        operationsGroup.setBackgroundResource(R.drawable.player_controls_strip_background);
        int padding = Math.round(dpToPx(12));
        operationsGroup.setPadding(padding, padding, padding, padding);
        if (operationsGroup.getParent() instanceof ViewGroup) {
            ViewGroup currentParent = (ViewGroup) operationsGroup.getParent();
            currentParent.removeView(operationsGroup);
        }
        int restoreIndex = Math.min(originalOperationsIndex, scrollContentLayout.getChildCount());
        scrollContentLayout.addView(operationsGroup, restoreIndex);
    }

    private void scrollToStudyOperationZone() {
        if (contentScrollView == null) {
            return;
        }
        View targetView = isStructureMode() && operationsGroup != null ? operationsGroup : visualizerScrollView;
        if (targetView == null) {
            return;
        }
        contentScrollView.post(() -> contentScrollView.smoothScrollTo(
            0,
            Math.max(0, Math.round(targetView.getTop() - dpToPx(10)))
        ));
    }

    private void scrollToVisualizerInStudyMode() {
        if (!focusModeEnabled || contentScrollView == null) {
            return;
        }
        contentScrollView.post(() -> {
            if (stepDetailsCard != null) {
                int scrollY = stepDetailsCard.getTop() - (int) dpToPx(16);
                contentScrollView.smoothScrollTo(0, Math.max(0, scrollY));
            }
        });
    }

    private void adjustVisualizerHeightForFocusMode() {
        if (visualizerView == null || visualizerView.getLayoutParams() == null) {
            return;
        }
        ViewGroup.LayoutParams params = visualizerView.getLayoutParams();
        if (focusModeEnabled) {
            int screenHeight = getResources().getDisplayMetrics().heightPixels;
            if (isStructureMode()) {
                params.height = Math.max(380, Math.round(screenHeight * 0.48f));
            } else {
                // Study mode should heavily favor the visualization area.
                params.height = Math.max(640, Math.round(screenHeight * 0.84f));
            }
        } else {
            params.height = visualizerDefaultHeight > 0 ? visualizerDefaultHeight : params.height;
        }
        visualizerView.setLayoutParams(params);
        visualizerView.requestLayout();
    }

    private void handlePreviousStep() {
        if (timeline.size() <= 1) {
            return;
        }
        handler.removeCallbacksAndMessages(null);
        timeline.removeLatest();
        Snapshot snapshot = timeline.latest();
        restoreSnapshot(snapshot);
        refreshTimeline();
        paused = true;
        running = false;
        syncPlayButtonVisual(false);
    }

    private void captureSnapshot() {
        Snapshot snapshot = buildSnapshot();
        if (!timeline.isEmpty() && isSameCheckpoint(timeline.latest(), snapshot)) {
            updateStudyModeState();
            refreshTimeline();
            return;
        }
        timeline.add(snapshot);
        refreshTimeline();
        updateStudyModeState();
    }

    private Snapshot buildSnapshot() {
        Snapshot snapshot = new Snapshot();
        snapshot.techniqueId = techniqueId;
        snapshot.expressionInput = expressionInput;
        snapshot.workingValues = workingValues.clone();
        snapshot.target = target;
        snapshot.stepIndex = stepIndex;
        snapshot.auxIndex = auxIndex;
        snapshot.foundIndex = foundIndex;
        snapshot.binaryPhase = binaryPhase;
        snapshot.binaryMidIndex = binaryMidIndex;
        snapshot.traversalOrder = traversalOrder.clone();
        snapshot.mergeLeftValues = mergeLeftValues.clone();
        snapshot.mergeRightValues = mergeRightValues.clone();
        snapshot.mergeOutputValues = mergeOutputValues.clone();
        snapshot.mergeWorkingValues = mergeWorkingValues.clone();
        snapshot.mergeLeftCursor = mergeLeftCursor;
        snapshot.mergeRightCursor = mergeRightCursor;
        snapshot.mergeWriteCursor = mergeWriteCursor;
        snapshot.mergeRunSize = mergeRunSize;
        snapshot.mergePassStart = mergePassStart;
        snapshot.radixDivisor = radixDivisor;
        snapshot.radixMaxValue = radixMaxValue;
        snapshot.radixCompleted = playbackComplete && "radix_sort".equals(techniqueId);
        snapshot.quickLow = quickLow;
        snapshot.quickHigh = quickHigh;
        snapshot.quickStackLow = quickStackLow.clone();
        snapshot.quickStackHigh = quickStackHigh.clone();
        snapshot.quickStackSize = quickStackSize;
        snapshot.matrixValues = copyMatrix(matrixValues);
        snapshot.matrixK = matrixK;
        snapshot.matrixRow = matrixRow;
        snapshot.matrixCol = matrixCol;
        snapshot.matrixLabel = matrixLabel;
        snapshot.heapSize = heapSize;
        snapshot.heapModePhase = heapModePhase;
        snapshot.heapSortPhase = heapSortPhase;
        snapshot.heapSortHeapifyIndex = heapSortHeapifyIndex;
        snapshot.patternText = patternText;
        snapshot.patternQuery = patternQuery;
        snapshot.patternWindowStart = patternWindowStart;
        snapshot.patternMatchIndex = patternMatchIndex;
        snapshot.patternFound = patternFound;
        snapshot.unionParents = unionParents.clone();
        snapshot.unionPath = unionPath.clone();
        snapshot.unionPathStep = unionPathStep;
        snapshot.unionFocusNode = unionFocusNode;
        snapshot.unionRootNode = unionRootNode;
        snapshot.dijkstraDistances = dijkstraDistances.clone();
        snapshot.dijkstraSettled = copyBooleanArray(dijkstraSettled);
        snapshot.dijkstraCurrentNode = dijkstraCurrentNode;
        snapshot.dijkstraPhase = dijkstraPhase;
        snapshot.dijkstraNeighborIndex = dijkstraNeighborIndex;
        snapshot.dijkstraWeights = copyMatrix(dijkstraWeights);
        snapshot.huffmanQueueWeights = huffmanQueueWeights.clone();
        snapshot.huffmanMergedWeight = huffmanMergedWeight;
        snapshot.huffmanMergedLeft = huffmanMergedLeft;
        snapshot.huffmanMergedRight = huffmanMergedRight;
        snapshot.running = running;
        snapshot.paused = paused;
        snapshot.statusText = statusText.getText() == null ? "" : statusText.getText().toString();
        snapshot.explanationText = explanationText.getText() == null ? "" : explanationText.getText().toString();
        snapshot.primaryPointerLabel = visualizerView.getPrimaryPointerLabel();
        snapshot.primaryPointerIndex = visualizerView.getPrimaryPointerIndex();
        snapshot.secondaryPointerLabel = visualizerView.getSecondaryPointerLabel();
        snapshot.secondaryPointerIndex = visualizerView.getSecondaryPointerIndex();
        snapshot.activeEdgeFrom = visualizerView.getActiveEdgeFrom();
        snapshot.activeEdgeTo = visualizerView.getActiveEdgeTo();
        snapshot.structureOperation = visualizerView.getStructureOperation();
        snapshot.movingStructureIndex = visualizerView.getMovingStructureIndex();
        return snapshot;
    }

    private boolean isSameCheckpoint(Snapshot previous, Snapshot current) {
        if (previous == null || current == null) {
            return false;
        }
        return previous.stepIndex == current.stepIndex
            && previous.auxIndex == current.auxIndex
            && previous.foundIndex == current.foundIndex
            && previous.radixDivisor == current.radixDivisor
            && previous.radixCompleted == current.radixCompleted
            && previous.binaryMidIndex == current.binaryMidIndex
            && previous.matrixK == current.matrixK
            && previous.matrixRow == current.matrixRow
            && previous.matrixCol == current.matrixCol
            && safeEquals(previous.matrixLabel, current.matrixLabel)
            && previous.unionFocusNode == current.unionFocusNode
            && previous.unionRootNode == current.unionRootNode
            && previous.dijkstraCurrentNode == current.dijkstraCurrentNode
            && previous.huffmanMergedWeight == current.huffmanMergedWeight
            && safeEquals(previous.statusText, current.statusText)
            && safeEquals(previous.explanationText, current.explanationText)
            && Arrays.equals(previous.workingValues, current.workingValues);
    }

    private void restoreSnapshot(Snapshot snapshot) {
        if (snapshot == null) {
            return;
        }
        techniqueId = snapshot.techniqueId;
        expressionInput = snapshot.expressionInput == null ? "" : snapshot.expressionInput;
        visualizerView.bindTechnique(techniqueId);
        visualizerView.setExpressionInput(expressionInput);
        workingValues = snapshot.workingValues.clone();
        target = snapshot.target;
        stepIndex = snapshot.stepIndex;
        auxIndex = snapshot.auxIndex;
        foundIndex = snapshot.foundIndex;
        binaryPhase = snapshot.binaryPhase;
        binaryMidIndex = snapshot.binaryMidIndex;
        traversalOrder = snapshot.traversalOrder.clone();
        mergeLeftValues = snapshot.mergeLeftValues.clone();
        mergeRightValues = snapshot.mergeRightValues.clone();
        mergeOutputValues = snapshot.mergeOutputValues.clone();
        mergeWorkingValues = snapshot.mergeWorkingValues.clone();
        mergeLeftCursor = snapshot.mergeLeftCursor;
        mergeRightCursor = snapshot.mergeRightCursor;
        mergeWriteCursor = snapshot.mergeWriteCursor;
        mergeRunSize = snapshot.mergeRunSize;
        mergePassStart = snapshot.mergePassStart;
        radixDivisor = snapshot.radixDivisor;
        radixMaxValue = snapshot.radixMaxValue;
        if (snapshot.radixCompleted) {
            visualizerView.setRadixComplete(true, snapshot.workingValues);
        } else {
            visualizerView.clearRadixComplete();
        }
        quickLow = snapshot.quickLow;
        quickHigh = snapshot.quickHigh;
        quickStackLow = snapshot.quickStackLow.clone();
        quickStackHigh = snapshot.quickStackHigh.clone();
        quickStackSize = snapshot.quickStackSize;
        matrixValues = copyMatrix(snapshot.matrixValues);
        matrixK = snapshot.matrixK;
        matrixRow = snapshot.matrixRow;
        matrixCol = snapshot.matrixCol;
        matrixLabel = snapshot.matrixLabel == null ? "" : snapshot.matrixLabel;
        heapSize = snapshot.heapSize;
        heapModePhase = snapshot.heapModePhase;
        heapSortPhase = snapshot.heapSortPhase;
        heapSortHeapifyIndex = snapshot.heapSortHeapifyIndex;
        patternText = snapshot.patternText;
        patternQuery = snapshot.patternQuery;
        patternWindowStart = snapshot.patternWindowStart;
        patternMatchIndex = snapshot.patternMatchIndex;
        patternFound = snapshot.patternFound;
        unionParents = snapshot.unionParents.clone();
        unionPath = snapshot.unionPath.clone();
        unionPathStep = snapshot.unionPathStep;
        unionFocusNode = snapshot.unionFocusNode;
        unionRootNode = snapshot.unionRootNode;
        dijkstraDistances = snapshot.dijkstraDistances.clone();
        dijkstraSettled = copyBooleanArray(snapshot.dijkstraSettled);
        dijkstraCurrentNode = snapshot.dijkstraCurrentNode;
        dijkstraPhase = snapshot.dijkstraPhase;
        dijkstraNeighborIndex = snapshot.dijkstraNeighborIndex;
        dijkstraWeights = copyMatrix(snapshot.dijkstraWeights);
        huffmanQueueWeights = snapshot.huffmanQueueWeights.clone();
        huffmanMergedWeight = snapshot.huffmanMergedWeight;
        huffmanMergedLeft = snapshot.huffmanMergedLeft;
        huffmanMergedRight = snapshot.huffmanMergedRight;
        running = snapshot.running;
        paused = snapshot.paused;

        if ("pattern_matching".equals(techniqueId)) {
            valuesInput.setText(patternText);
            targetInput.setText(patternQuery);
            visualizerView.setPatternState(patternText, patternQuery, patternWindowStart, patternMatchIndex, patternFound);
        } else if ("warshall_algorithm".equals(techniqueId)
            || "floyd_algorithm".equals(techniqueId)
            || "shortest_paths".equals(techniqueId)
            || "graph_floyd_warshall".equals(techniqueId)
            || VisualizationTechniqueRegistry.isDynamicProgramming(techniqueId)
            || VisualizationTechniqueRegistry.isBacktracking(techniqueId)) {
            visualizerView.setMatrixState(matrixValues, matrixK, matrixRow, matrixCol, resolveMatrixLabel());
        } else if ("dijkstra_algorithm".equals(techniqueId)) {
            visualizerView.setDijkstraState(dijkstraDistances, dijkstraSettled, dijkstraCurrentNode);
        } else if ("huffman_algorithm".equals(techniqueId)) {
            visualizerView.setHuffmanState(workingValues, huffmanQueueWeights, huffmanMergedWeight, huffmanMergedLeft, huffmanMergedRight, stepIndex);
        } else if (isTreeOrGraph()) {
            visualizerView.setTraversalState(workingValues, traversalOrder, stepIndex);
        } else {
            updateArrayVisualization(stepIndex, auxIndex, foundIndex);
            refreshCustomSortScene();
        }

        if (snapshot.primaryPointerIndex >= 0 || snapshot.secondaryPointerIndex >= 0) {
            visualizerView.setPointerState(
                snapshot.primaryPointerLabel,
                snapshot.primaryPointerIndex,
                snapshot.secondaryPointerLabel,
                snapshot.secondaryPointerIndex
            );
        } else {
            visualizerView.clearPointerState();
        }

        if (snapshot.activeEdgeFrom >= 0 && snapshot.activeEdgeTo >= 0) {
            visualizerView.setActiveEdge(snapshot.activeEdgeFrom, snapshot.activeEdgeTo);
        } else {
            visualizerView.clearActiveEdge();
        }

        visualizerView.setStructureMotion(snapshot.structureOperation, snapshot.movingStructureIndex);
        statusText.setText(snapshot.statusText);
        explanationText.setText(snapshot.explanationText);
        updateStudyModeState();
        updateBinaryChips();
    }

    private int[][] copyMatrix(int[][] source) {
        if (source == null) {
            return new int[0][0];
        }
        int[][] copy = new int[source.length][];
        for (int i = 0; i < source.length; i++) {
            copy[i] = source[i] == null ? new int[0] : source[i].clone();
        }
        return copy;
    }

    private boolean[] copyBooleanArray(boolean[] source) {
        return source == null ? new boolean[0] : source.clone();
    }

    private String buildExplanation(String message) {
        if ("binary_search".equals(techniqueId)) {
            if (binaryPhase == 0) {
                return "Binary Search marks the current search range first using low and high.";
            }
            if (binaryPhase == 1) {
                return "Now we inspect the middle value of the current range before deciding which half to keep.";
            }
            return "After the middle comparison, one half is ruled out and the search range becomes smaller.";
        }
        if ("linear_search".equals(techniqueId)) {
            return buildLinearSearchExplanation(message);
        }
        if ("jump_search".equals(techniqueId)) {
            return "Jump Search skips ahead by blocks first, then scans linearly inside the most likely block.";
        }
        if ("pattern_matching".equals(techniqueId)) {
            return "Pattern Matching aligns the pattern with the text, compares characters, and shifts to the next possible position on a mismatch.";
        }
        if (isGraphTechnique()) {
            return buildGraphExplanation(message);
        }
        if ("tree_traversal".equals(techniqueId)) {
            return buildTreeExplanation(message);
        }
        if ("preorder_traversal".equals(techniqueId)) {
            return buildTreeExplanation(message);
        }
        if ("postorder_traversal".equals(techniqueId)) {
            return buildTreeExplanation(message);
        }
        if (isSortingMode()) {
            String sortExplanation = SortVisualizationEngine.buildDetailedExplanation(
                techniqueId,
                message,
                workingValues,
                stepIndex,
                auxIndex,
                foundIndex,
                heapSize,
                heapSortPhase
            );
            if (sortExplanation != null && !sortExplanation.trim().isEmpty()) {
                return sortExplanation;
            }
        }
        if ("bubble_sort".equals(techniqueId)) {
            return "Bubble Sort compares neighbors and pushes the largest unsorted value to the end in each pass.";
        }
        if ("selection_sort".equals(techniqueId)) {
            return "Selection Sort searches for the smallest unsorted value and places it into the next sorted position.";
        }
        if ("insertion_sort".equals(techniqueId)) {
            return "Insertion Sort grows the sorted part by shifting larger values and inserting the current value in place.";
        }
        if ("quicksort".equals(techniqueId)) {
            return "Quicksort partitions values around a pivot, then keeps sorting the smaller left and right parts.";
        }
        if ("warshall_algorithm".equals(techniqueId)) {
            return "Warshall checks whether going through the current intermediate vertex makes one more pair of nodes reachable.";
        }
        if ("floyd_algorithm".equals(techniqueId) || "shortest_paths".equals(techniqueId)) {
            return "This matrix step checks whether the current intermediate vertex gives a cheaper path between two vertices.";
        }
        if ("heapsort".equals(techniqueId) || "max_heap_insertion".equals(techniqueId) || "max_heap_deletion".equals(techniqueId)) {
            return "Heap operations keep the largest value at the root, then restore heap order with percolate-up or percolate-down steps.";
        }
        if ("merging".equals(techniqueId)) {
            return "Merging keeps comparing the first remaining items of the left and right sorted lists, then writes the smaller one into the output.";
        }
        if ("merge_sort".equals(techniqueId)) {
            return "Merge Sort first treats small runs as sorted, then merges larger and larger runs until the whole array becomes sorted.";
        }
        if ("radix_sort".equals(techniqueId)) {
            return "Radix Sort finishes one digit pass at a time, so the buckets for ones, tens, and higher places settle the final order.";
        }
        if (isStructureMode()) {
            if (techniqueId.contains("stack")) {
                return "This stack step updates the top of the stack so push, pop, and peek stay easy to follow.";
            }
            if (techniqueId.contains("queue") || "deque".equals(techniqueId)) {
                return "This queue step updates the front or rear positions so insertion and deletion happen in the right order.";
            }
            if (techniqueId.contains("linked_list") || techniqueId.contains("circular_header_list")) {
                return buildLinkedListExplanation(message);
            }
        }
        if (isTreeOrGraph()) {
            return "This step updates the current node, edge, or traversal rule so the structure becomes easier to understand visually.";
        }
        return buildGenericExplanation(message);
    }

    private String buildGenericExplanation(String message) {
        if (currentOperationLabel == null) {
            return message;
        }
        switch (currentOperationLabel) {
            case "Starting":
                return "This is the setup step where the current pointers, values, or structure state are initialized before the main process continues.";
            case "Inspecting":
                return "This step checks the active value, node, or pointer before the algorithm decides what to do next.";
            case "Comparing":
                return "This step compares the active values so the algorithm can decide whether to keep searching, move, or reorder data.";
            case "Swapping":
                return "This step exchanges two active values because the current order does not satisfy the algorithm's rule.";
            case "Moving Right":
            case "Moving Left":
            case "Advancing":
                return "This step moves the active pointer or position so the algorithm can continue exploring the correct part of the data.";
            case "Inserting":
                return "This step places a new value into the correct position of the current data structure or working state.";
            case "Removing":
                return "This step removes the current value or node while preserving the rule of the data structure.";
            case "Relaxing":
                return "This step checks whether the current route or edge improves the best result found so far.";
            case "Rotating":
                return "This step rotates the structure to restore the required balance or order.";
            case "Heapifying":
                return "This step restores heap order so the largest or most important value stays in the correct position.";
            case "Merging":
                return "This step combines smaller sorted parts into a larger sorted result.";
            case "Partitioning":
                return "This step separates values around a pivot so the next recursive or iterative phase can continue correctly.";
            case "Found":
                return "This step confirms that the target condition has been satisfied, so the algorithm can finish with the result.";
            case "Not Found":
                return "This step confirms that the full search space has been exhausted, so the target does not exist in the current data.";
            case "Completed":
                return "This step confirms the current phase is complete and the structure or result is now in the expected state.";
            default:
                return message;
        }
    }

    private String resolveLinearSearchOperationLabel(String message) {
        String safe = message == null ? "" : message.toLowerCase();
        if (safe.contains("not found")) {
            return "Not Found";
        }
        if (safe.contains("found")) {
            return "Found";
        }
        if (safe.contains("comparing")) {
            return "Checking Value";
        }
        return "Scanning";
    }

    private String buildLinearSearchExplanation(String message) {
        if (message != null && message.toLowerCase().contains("not found")) {
            return "Every value has been checked from left to right, so the target is not present in this list.";
        }
        if (message != null && message.toLowerCase().contains("found")) {
            return "Linear Search has reached index " + stepIndex + ", and the current value matches the target " + target + ".";
        }
        if (stepIndex >= 0 && stepIndex < workingValues.length) {
            return "Linear Search is checking index " + stepIndex + " with value " + workingValues[stepIndex]
                + " against the target " + target + " before moving to the next position.";
        }
        return "Linear Search checks values from left to right until the target is found or the list ends.";
    }

    private String buildGraphExplanation(String message) {
        String safe = message == null ? "" : message.toLowerCase();
        if ("dijkstra_algorithm".equals(techniqueId) || "shortest_paths".equals(techniqueId)) {
            if (safe.contains("pick node")) {
                return "Dijkstra chooses the unsettled node with the smallest known distance so all outgoing relaxations start from the best current path.";
            }
            if (safe.contains("relax edge")) {
                return "This relaxation step checks whether traveling along the highlighted edge gives a shorter route to the neighbor.";
            }
            if (safe.contains("settled")) {
                return "This node is now settled, which means its shortest distance from the source is final.";
            }
            return "This shortest-path step chooses the next useful vertex or edge so the distance table becomes more accurate.";
        }
        if ("warshall_algorithm".equals(techniqueId)) {
            return "Warshall checks whether the current intermediate vertex creates a new reachable path between the highlighted row and column.";
        }
        if ("floyd_algorithm".equals(techniqueId)) {
            return "Floyd compares the direct cost with the cost of routing through the current intermediate vertex to see if the path becomes cheaper.";
        }
        if ("spanning_tree".equals(techniqueId)) {
            return safe.contains("visiting")
                ? "The spanning-tree walkthrough is choosing the next safe node or edge so all vertices become connected without forming unnecessary cycles."
                : "A spanning tree step chooses the next safe edge or node so all vertices become connected without unnecessary cycles.";
        }
        if (stepIndex >= 0 && traversalOrder.length > stepIndex) {
            return "In " + currentTraversalMode + " mode, the traversal is visiting graph node "
                + traversalOrder[stepIndex]
                + (stepIndex > 0 ? " after coming from node " + traversalOrder[stepIndex - 1] + "." : " as the starting node.");
        }
        return "Graph Traversal is running in " + currentTraversalMode + " mode, so the next node depends on that visiting rule.";
    }

    private String buildTreeExplanation(String message) {
        String safe = message == null ? "" : message.toLowerCase();
        if ("bst_search_parent_tracking".equals(techniqueId)) {
            if (safe.contains("found")) {
                return "The search has reached the target node, and the previous node on the path is reported as its parent.";
            }
            if (safe.contains("checking node")) {
                return "BST search compares the target with the current node to decide whether the path should continue left or right.";
            }
            if (safe.contains("not found")) {
                return "The target is not in the tree, so this last visited node shows where the search path ended.";
            }
        }
        if ("bst_insertion".equals(techniqueId)) {
            return safe.contains("path ends")
                ? "The insertion path has reached the correct parent area, so the new item can be attached in BST order."
                : "BST insertion compares the incoming item with the current node to decide whether to continue left or right.";
        }
        if ("bst_deletion_case_a".equals(techniqueId)
            || "bst_deletion_case_b".equals(techniqueId)
            || "bst_deletion_del".equals(techniqueId)) {
            return safe.contains("reached node")
                ? "The deletion path has found the node to remove, so the next action depends on how many children it has."
                : "BST deletion first walks the search path to find the exact node that should be removed.";
        }
        if ("avl_tree".equals(techniqueId)) {
            return safe.contains("rotation")
                ? "AVL rotation rebalances the tree so the height difference stays within the allowed limit."
                : "AVL insertion checks balance factors after an update so the tree can stay height-balanced.";
        }
        if ("trie".equals(techniqueId)) {
            return "Trie insertion follows one character at a time, creating or reusing the next node for each letter in the word.";
        }
        if ("huffman_algorithm".equals(techniqueId)) {
            return "Huffman repeatedly merges the two smallest weights so a prefix tree can be built from the bottom up.";
        }
        if (stepIndex >= 0 && traversalOrder.length > stepIndex) {
            int currentNodeIndex = traversalOrder[stepIndex];
            int currentValue = currentNodeIndex >= 0 && currentNodeIndex < workingValues.length ? workingValues[currentNodeIndex] : currentNodeIndex;
            return resolveTreeTraversalModeDescription() + " Right now the traversal is at node " + currentValue + ".";
        }
        return resolveTreeTraversalModeDescription();
    }

    private String resolveTreeTraversalModeDescription() {
        if ("preorder_traversal".equals(techniqueId)) {
            return "Preorder Traversal visits the root first, then continues through the left and right subtrees.";
        }
        if ("postorder_traversal".equals(techniqueId)) {
            return "Postorder Traversal finishes both subtrees before visiting the current root node.";
        }
        return "Tree Traversal is following " + currentTraversalMode + " order, so the visit sequence changes with the selected mode.";
    }

    private String buildLinkedListExplanation(String message) {
        String safe = message == null ? "" : message.toLowerCase();
        if (stepIndex >= 0 && stepIndex < workingValues.length) {
            int value = workingValues[stepIndex];
            if (safe.contains("visiting")) {
                return "The linked-list walkthrough is currently on node " + value
                    + ", following the next reference one node at a time.";
            }
            if (safe.contains("insert")) {
                return "This step has reached node " + value
                    + ", which helps identify where the new node should be linked into the list.";
            }
            if (safe.contains("delete")) {
                return "This step has reached node " + value
                    + ", which helps verify whether it is the node to remove or the node just before it.";
            }
            if (safe.contains("search")) {
                return "The search is comparing the target with node " + value
                    + " before moving along the next reference.";
            }
        }
        return "This linked-list step follows node links carefully so traversal, insertion, or deletion happens at the correct location.";
    }

    private int resolveTimelineCapacity() {
        if ("warshall_algorithm".equals(techniqueId)
            || "floyd_algorithm".equals(techniqueId)
            || "shortest_paths".equals(techniqueId)
            || "dijkstra_algorithm".equals(techniqueId)
            || "huffman_algorithm".equals(techniqueId)
            || VisualizationTechniqueRegistry.isTree(techniqueId)
            || isGraphTechnique()) {
            return 48;
        }
        if (isStructureMode()) {
            return 72;
        }
        if (isSortingMode()) {
            return 120;
        }
        return 96;
    }

    @Override
    public void updateBinaryChips() {
        // Binary chips were removed from the UI.
    }

    @Override
    public void setHighlightedLines(int codeLineIndex, int algorithmLineIndex) {
        if (sessionViewModel != null) {
            sessionViewModel.setHighlightedCodeLine(codeLineIndex);
            sessionViewModel.setHighlightedAlgorithmLine(algorithmLineIndex);
        }
    }

    @Override
    public void advanceMatrixStep() {
        MatrixAlgorithmEngine.advance(this);
    }

    @Override
    public void advanceHeapOperationStep() {
        HeapOperationStepEngine.advance(this);
    }

    @Override
    public void advanceUnionFindStep() {
        UnionFindStepEngine.advance(this);
    }

    @Override
    public void advanceDijkstraStep() {
        DijkstraStepEngine.advance(this);
    }

    @Override
    public void advanceHuffmanStep() {
        HuffmanStepEngine.advance(this);
    }

    @Override
    public void advanceAvlStep() {
        AvlTreeStepEngine.advance(this);
    }

    @Override
    public void advanceTrieStep() {
        TrieStepEngine.advance(this);
    }

    @Override
    public int[] getDijkstraDistances() {
        return dijkstraDistances;
    }

    @Override
    public void setDijkstraDistances(int[] distances) {
        this.dijkstraDistances = distances;
    }

    @Override
    public boolean[] getDijkstraSettled() {
        return dijkstraSettled;
    }

    @Override
    public void setDijkstraSettled(boolean[] settled) {
        this.dijkstraSettled = settled;
    }

    @Override
    public int getDijkstraCurrentNode() {
        return dijkstraCurrentNode;
    }

    @Override
    public void setDijkstraCurrentNode(int node) {
        this.dijkstraCurrentNode = node;
    }

    @Override
    public int getDijkstraPhase() {
        return dijkstraPhase;
    }

    @Override
    public void setDijkstraPhase(int phase) {
        this.dijkstraPhase = phase;
    }

    @Override
    public int getDijkstraNeighborIndex() {
        return dijkstraNeighborIndex;
    }

    @Override
    public void setDijkstraNeighborIndex(int index) {
        this.dijkstraNeighborIndex = index;
    }

    @Override
    public int[][] getDijkstraWeights() {
        return dijkstraWeights;
    }

    @Override
    public void setDijkstraWeights(int[][] weights) {
        this.dijkstraWeights = weights;
    }

    @Override
    public void setDijkstraState(int[] distances, boolean[] settled, int currentNode) {
        if (visualizerView != null) {
            visualizerView.setDijkstraState(distances, settled, currentNode);
        }
    }

    @Override
    public int[] getHuffmanQueueWeights() {
        return huffmanQueueWeights;
    }

    @Override
    public void setHuffmanQueueWeights(int[] weights) {
        this.huffmanQueueWeights = weights;
    }

    @Override
    public int getHuffmanMergedWeight() {
        return huffmanMergedWeight;
    }

    @Override
    public void setHuffmanMergedWeight(int weight) {
        this.huffmanMergedWeight = weight;
    }

    @Override
    public void setHuffmanState(int[] queueWeights, int mergedWeight) {
        this.huffmanQueueWeights = queueWeights;
        this.huffmanMergedWeight = mergedWeight;
        this.huffmanMergedLeft = -1;
        this.huffmanMergedRight = -1;
        if (visualizerView != null) {
            visualizerView.setHuffmanState(workingValues, queueWeights, mergedWeight, -1, -1, stepIndex);
        }
    }

    @Override
    public void setHuffmanState(int[] queueWeights, int mergedWeight, int leftWeight, int rightWeight) {
        this.huffmanQueueWeights = queueWeights;
        this.huffmanMergedWeight = mergedWeight;
        this.huffmanMergedLeft = leftWeight;
        this.huffmanMergedRight = rightWeight;
        if (visualizerView != null) {
            visualizerView.setHuffmanState(workingValues, queueWeights, mergedWeight, leftWeight, rightWeight, stepIndex);
        }
    }

    @Override
    public void setActiveNodes(int primary, int secondary) {
        if (visualizerView != null) {
            visualizerView.setActiveNodes(primary, secondary);
        }
    }

    @Override
    public int[][] getMatrixValues() {
        return matrixValues;
    }

    @Override
    public void setMatrixValues(int[][] values) {
        this.matrixValues = values;
    }

    @Override
    public int getMatrixK() {
        return matrixK;
    }

    @Override
    public void setMatrixK(int k) {
        this.matrixK = k;
    }

    @Override
    public int getMatrixRow() {
        return matrixRow;
    }

    @Override
    public void setMatrixRow(int row) {
        this.matrixRow = row;
    }

    @Override
    public int getMatrixCol() {
        return matrixCol;
    }

    @Override
    public void setMatrixCol(int col) {
        this.matrixCol = col;
    }

    @Override
    public void setMatrixState(int[][] values, int k, int row, int col, String label) {
        this.matrixValues = copyMatrix(values);
        this.matrixK = k;
        this.matrixRow = row;
        this.matrixCol = col;
        this.matrixLabel = label == null ? "" : label;
        if (visualizerView != null) {
            visualizerView.setMatrixState(values, k, row, col, label);
        }
    }

    @Override
    public int getHeapModePhase() {
        return heapModePhase;
    }

    @Override
    public void setHeapModePhase(int phase) {
        this.heapModePhase = phase;
    }

    @Override
    public int[] getUnionParents() {
        return unionParents;
    }

    @Override
    public void setUnionParents(int[] parents) {
        this.unionParents = parents;
    }

    @Override
    public int[] getUnionPath() {
        return unionPath;
    }

    @Override
    public void setUnionPath(int[] path) {
        this.unionPath = path;
    }

    @Override
    public int getUnionPathStep() {
        return unionPathStep;
    }

    @Override
    public void setUnionPathStep(int step) {
        this.unionPathStep = step;
    }

    @Override
    public int getUnionFocusNode() {
        return unionFocusNode;
    }

    @Override
    public void setUnionFocusNode(int node) {
        this.unionFocusNode = node;
    }

    @Override
    public int getUnionRootNode() {
        return unionRootNode;
    }

    @Override
    public void setUnionRootNode(int node) {
        this.unionRootNode = node;
    }

    @Override
    public void setUnionFindState(int[] parents, int focusNode, int rootNode) {
        if (visualizerView != null) {
            visualizerView.setUnionFindState(parents, focusNode, rootNode);
        }
    }

    @Override
    public void finish(String message) {
        bindCurrentState(message);
        finishWithoutResetLabel();
    }

    @Override
    public void finishWithoutResetLabel() {
        playbackComplete = true;
        running = false;
        paused = false;
        if (playbackController != null) {
            playbackController.stop();
        }
        syncPlayButtonVisual(false);
        showCompletionBanner();
        if (getActivity() instanceof TechniqueDetailActivity) {
            ((TechniqueDetailActivity) getActivity()).promptPracticeAfterVisualization();
        }
    }

    private boolean isSearchingMode() {
        return VisualizationTechniqueRegistry.isSearching(techniqueId);
    }

    private boolean isSortingMode() {
        return VisualizationTechniqueRegistry.isSorting(techniqueId);
    }

    private String resolveSortFamilyIntro() {
        return SortVisualizationEngine.resolveIntro(techniqueId);
    }

    private String resolveSortFamilyStep(int index) {
        return SortVisualizationEngine.resolveStep(techniqueId, index);
    }

    private String resolveSortFamilyDone() {
        return SortVisualizationEngine.resolveDone(techniqueId);
    }

    private boolean isStructureMode() {
        return VisualizationTechniqueRegistry.isStructure(techniqueId);
    }

    private boolean isTreeOrGraph() {
        return VisualizationTechniqueRegistry.isTreeOrGraph(techniqueId);
    }

    private boolean isGraphTechnique() {
        return VisualizationTechniqueRegistry.isGraph(techniqueId);
    }

    private int[] buildBstSearchPath() {
        int[] path = new int[workingValues.length];
        int count = 0;
        int index = 0;
        while (index < workingValues.length) {
            path[count++] = index;
            if (workingValues[index] == target) {
                break;
            }
            index = target < workingValues[index] ? (2 * index) + 1 : (2 * index) + 2;
            if (index >= workingValues.length) {
                break;
            }
        }
        return Arrays.copyOf(path, count);
    }

    private int[] buildBstInsertPath() {
        int[] path = new int[workingValues.length];
        int count = 0;
        int index = 0;
        while (index < workingValues.length) {
            path[count++] = index;
            if (workingValues[index] == target) {
                break;
            }
            int next = target < workingValues[index] ? (2 * index) + 1 : (2 * index) + 2;
            if (next >= workingValues.length) {
                break;
            }
            index = next;
        }
        return Arrays.copyOf(path, count);
    }

    @Override
    public void initializeUnionFindPreview() {
        UnionFindStepEngine.initializePreview(this);
    }

    @Override
    public void initializeDijkstraPreview() {
        DijkstraStepEngine.initializePreview(this);
    }

    @Override
    public void initializeHuffmanPreview() {
        int[] weights = (workingValues != null && workingValues.length >= 2)
                ? workingValues
                : com.dsavisualizer.app.ui.visualization.runtime.HuffmanStepEngine.getDefaultWeights(huffmanPreset);
        com.dsavisualizer.app.ui.visualization.runtime.HuffmanStepEngine.initializePreview(this, weights);
    }

    private int[] parseArray(String raw) {
        String[] pieces = raw.split("[,\\s]+");
        int[] values = new int[pieces.length];
        for (int i = 0; i < pieces.length; i++) {
            values[i] = Integer.parseInt(pieces[i].trim());
        }
        return values;
    }

    private int[] ensureLength(int[] source, int requiredLength) {
        int[] result = new int[requiredLength];
        for (int i = 0; i < requiredLength; i++) {
            result[i] = i < source.length ? source[i] : (random.nextInt(90) + 10);
        }
        return result;
    }

    private String join(int[] values) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(values[i]);
        }
        return builder.toString();
    }

    private void renderPlaybackState(PlaybackState state) {
        if (state == null) {
            return;
        }
        boolean completedThisTick = lastPlaybackWasPlaying && !state.isPlaying && state.isAtEnd;
        running = state.isPlaying;
        paused = !state.isPlaying;
        playbackVisualSpeed = state.playbackSpeed <= 0f ? 1f : state.playbackSpeed;
        previousButton.setEnabled(!state.isAtStart);
        updatePlaybackButtonState(previousButton, !state.isAtStart, false);
        updatePlaybackButtonState(replayButton, !state.isAtStart, false);
        updatePlaybackButtonState(playPauseButton, true, state.isPlaying);
        updatePlaybackButtonState(nextButton, !state.isAtEnd, false);
        updatePlaybackButtonState(studyPreviousButton, !state.isAtStart, false);
        updatePlaybackButtonState(studyReplayButton, !state.isAtStart, false);
        updatePlaybackButtonState(studyPlayButton, true, state.isPlaying);
        updatePlaybackButtonState(studyNextButton, !state.isAtEnd, false);
        updateStructureOverlayPlaybackVisibility(state.isPlaying);
        updatePlayerProgress(state);
        syncPlayButtonVisual(state.isPlaying, completedThisTick);
        lastPlaybackWasPlaying = state.isPlaying;
    }

    private void applyPlaybackSpeed(float speed) {
        if (playbackController != null) {
            playbackController.setSpeed(speed);
        }
        playbackVisualSpeed = speed <= 0f ? 1f : speed;
        delayMs = Math.round(1000f / speed);
    }

    private void updatePlaybackButtonState(MaterialButton button, boolean enabled, boolean selected) {
        if (button == null) {
            return;
        }
        button.setEnabled(enabled);
        button.setClickable(enabled);
        button.setSelected(selected);
        button.setAlpha(1f);
        button.animate()
            .scaleX(selected ? 1.03f : 1f)
            .scaleY(selected ? 1.03f : 1f)
            .setDuration(150L)
            .start();
    }

    private void updateStructureOverlayPlaybackVisibility(boolean playing) {
        if (overlayActionsHost == null || !isStructureMode()) {
            return;
        }
        overlayActionsHost.animate().cancel();
        if (!playing) {
            overlayActionsHost.setVisibility(View.VISIBLE);
        }
        overlayActionsHost.animate()
            .alpha(playing ? 0f : 1f)
            .translationY(playing ? -dpToPx(14) : 0f)
            .setDuration(180L)
            .withEndAction(() -> {
                if (playing) {
                    overlayActionsHost.setVisibility(View.INVISIBLE);
                } else {
                    overlayActionsHost.setAlpha(1f);
                    overlayActionsHost.setTranslationY(0f);
                }
            })
            .start();
    }

    private void updatePlayerProgress(PlaybackState state) {
        int current = Math.max(0, state.currentStepIndex);
        int total = Math.max(1, estimatePlaybackTotalSteps());
        int clampedCurrent = Math.min(current, total);
        if (progressLabel != null) {
            progressLabel.setText("Step " + clampedCurrent + " of " + total);
        }
        if (stepChipView != null) {
            CharSequence previousText = stepChipView.getText();
            stepChipView.setText(clampedCurrent + "/" + total);
            if (previousText == null || !previousText.toString().equals(stepChipView.getText().toString())) {
                stepChipView.animate().cancel();
                stepChipView.setScaleX(0.94f);
                stepChipView.setScaleY(0.94f);
                stepChipView.animate().scaleX(1.06f).scaleY(1.06f).setDuration(90L).withEndAction(() ->
                    stepChipView.animate().scaleX(1f).scaleY(1f).setDuration(140L).start()
                ).start();
            }
        }
        updateFastStepBadge(clampedCurrent);
        if (completedBadgeView != null) {
            if (state.isAtEnd && clampedCurrent > 0) {
                if (completedBadgeView.getVisibility() != View.VISIBLE) {
                    completedBadgeView.setText(resolveCompletedBadgeLabel());
                    styleCompletedBadgeForFamily();
                    completedBadgeView.setVisibility(View.VISIBLE);
                    completedBadgeView.setAlpha(0f);
                    completedBadgeView.setScaleX(0.88f);
                    completedBadgeView.setScaleY(0.88f);
                    completedBadgeView.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(180L).start();
                    completedBadgeView.post(this::refreshInlineChipPadding);
                }
                } else if (completedBadgeView.getVisibility() == View.VISIBLE
                && completedBadgeView.getText() != null
                && completedBadgeView.getText().toString().endsWith("Done")) {
                completedBadgeView.animate().alpha(0f).scaleX(0.92f).scaleY(0.92f).setDuration(120L).withEndAction(() -> {
                    if (completedBadgeView != null) {
                        completedBadgeView.setVisibility(View.GONE);
                        completedBadgeView.setScaleX(1f);
                        completedBadgeView.setScaleY(1f);
                        refreshInlineChipPadding();
                    }
                }).start();
            }
        }
        if (progressBar != null) {
            progressBar.setMax(total);
            progressBar.setProgress(clampedCurrent);
            progressBar.setProgressTintList(android.content.res.ColorStateList.valueOf(resolveAccentColor()));
        }
        if (controlsProgressLine != null) {
            controlsProgressLine.setBackgroundTintList(android.content.res.ColorStateList.valueOf(resolveAccentColor()));
            float ratio = total <= 0 ? 0f : Math.min(1f, Math.max(0f, clampedCurrent / (float) total));
            long duration = Math.max(85L, Math.round(220L / Math.max(0.6f, playbackVisualSpeed)));
            controlsProgressLine.animate().scaleX(Math.max(0.04f, ratio)).setDuration(duration).start();
        }
        updateProgressHandle(clampedCurrent, total);
        if (clampedCurrent != lastPulsedPlaybackStep && clampedCurrent > 0) {
            lastPulsedPlaybackStep = clampedCurrent;
            pulseActivePlaybackButtons();
            if (controlsGroup != null) {
                controlsGroup.performHapticFeedback(android.view.HapticFeedbackConstants.CLOCK_TICK);
            }
        }
        if (state.isAtEnd && clampedCurrent > 0 && clampedCurrent != lastCompletionFlashStep) {
            lastCompletionFlashStep = clampedCurrent;
            animateCompletionFlash();
        } else if (!state.isAtEnd) {
            lastCompletionFlashStep = -1;
        }
    }

    private void syncPlayButtonVisual(boolean playing) {
        syncPlayButtonVisual(playing, false);
    }

    private void syncPlayButtonVisual(boolean playing, boolean celebrateCompletion) {
        updateMaterialPlayButton(playPauseButton, playing, celebrateCompletion);
        updateMaterialPlayButton(studyPlayButton, playing, celebrateCompletion);
    }

    private void pulseActivePlaybackButtons() {
        pulseMaterialButton(playPauseButton, 0f);
        pulseMaterialButton(studyPlayButton, 0f);
        if (nowDoingLabel != null) {
            nowDoingLabel.animate()
                .scaleX(1.03f)
                .scaleY(1.03f)
                .alpha(1f)
                .setDuration(110L)
                .withEndAction(() -> nowDoingLabel.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .alpha(0.98f)
                    .setDuration(170L)
                    .start())
            .start();
        }
    }

    private void pulseStepButtons(boolean forward) {
        MaterialButton primary = forward ? nextButton : previousButton;
        MaterialButton secondary = forward ? studyNextButton : studyPreviousButton;
        pulseMaterialButton(primary, forward ? dpToPx(4) : -dpToPx(4));
        pulseMaterialButton(secondary, forward ? dpToPx(4) : -dpToPx(4));
    }

    private void updateMaterialPlayButton(MaterialButton button, boolean playing, boolean celebrateCompletion) {
        if (button == null) {
            return;
        }
        button.animate().cancel();
        button.setIconResource(playing ? R.drawable.ic_player_pause : R.drawable.ic_player_play);
        button.setText("");
        button.setContentDescription(playing ? getString(R.string.vis_pause) : getString(R.string.vis_play));
        button.animate()
            .scaleX(celebrateCompletion ? 1.08f : (playing ? 1.03f : 1f))
            .scaleY(celebrateCompletion ? 1.08f : (playing ? 1.03f : 1f))
            .setDuration(140L)
            .withEndAction(() -> button.animate()
                .scaleX(playing ? 1.03f : 1f)
                .scaleY(playing ? 1.03f : 1f)
                .setDuration(160L)
                .start())
            .start();
    }

    private void pulseMaterialButton(MaterialButton button, float translationX) {
        if (button == null) {
            return;
        }
        button.animate().cancel();
        button.animate()
            .translationX(translationX)
            .scaleX(1.05f)
            .scaleY(1.05f)
            .setDuration(90L)
            .withEndAction(() -> button.animate()
                .translationX(0f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(140L)
                .start())
            .start();
    }

    private void updateProgressHandle(int current, int total) {
        if (progressBar == null || progressHandle == null) {
            return;
        }
        if (progressScrubbing) {
            return;
        }
        progressBar.post(() -> {
            int trackWidth = progressBar.getWidth();
            if (trackWidth <= 0) {
                return;
            }
            float ratio = total <= 0 ? 0f : Math.min(1f, Math.max(0f, current / (float) total));
            float available = trackWidth - progressHandle.getWidth();
            float handleX = Math.max(0f, available * ratio);
            progressHandle.setTranslationX(handleX);
            progressHandle.setBackgroundTintList(android.content.res.ColorStateList.valueOf(resolveAccentColor()));
            updateProgressBadgePosition(current, handleX, trackWidth);
        });
    }

    private void jumpFromProgressTrack(View trackView, float tapX) {
        if (trackView == null || timeline.size() <= 1) {
            return;
        }
        int targetIndex = resolveTimelineIndexFromTrack(trackView, tapX);
        if (targetIndex < 0) {
            return;
        }
        jumpToTimelineStep(targetIndex);
    }

    private void startProgressScrub(View trackView, float touchX) {
        if (timeline.size() <= 1) {
            return;
        }
        progressScrubbing = true;
        lastScrubbedIndex = -1;
        if (progressBadge != null) {
            progressBadge.setVisibility(View.VISIBLE);
            progressBadge.setAlpha(0f);
            progressBadge.animate().alpha(1f).setDuration(120L).start();
        }
        if (playbackController != null) {
            playbackController.pause();
        }
        updateProgressScrub(trackView, touchX);
    }

    private void updateProgressScrub(View trackView, float touchX) {
        if (!progressScrubbing) {
            return;
        }
        int targetIndex = resolveTimelineIndexFromTrack(trackView, touchX);
        if (targetIndex < 0 || targetIndex == lastScrubbedIndex) {
            return;
        }
        lastScrubbedIndex = targetIndex;
        jumpToTimelineStep(targetIndex);
        if (progressHandle != null && trackView != null) {
            float available = Math.max(0f, trackView.getWidth() - progressHandle.getWidth());
            float ratio = timeline.size() <= 1 ? 0f : targetIndex / (float) (timeline.size() - 1);
            float handleX = available * ratio;
            progressHandle.setTranslationX(handleX);
            updateProgressBadgePosition(targetIndex, handleX, trackView.getWidth());
        }
    }

    private void endProgressScrub(View trackView, float touchX) {
        if (!progressScrubbing) {
            return;
        }
        updateProgressScrub(trackView, touchX);
        progressScrubbing = false;
        lastScrubbedIndex = -1;
        if (progressBadge != null) {
            progressBadge.animate().alpha(0f).setDuration(120L).withEndAction(() -> progressBadge.setVisibility(View.GONE)).start();
        }
        PlaybackState state = playbackController == null ? null : playbackController.getPlaybackState().getValue();
        if (state != null) {
            updateProgressHandle(state.currentStepIndex, Math.max(1, estimatePlaybackTotalSteps()));
        }
    }

    private void updateProgressBadgePosition(int stepIndex, float handleX, int trackWidth) {
        if (progressBadge == null || progressHandle == null || progressTrack == null) {
            return;
        }
        progressBadge.setText("Step " + Math.max(0, stepIndex));
        progressBadge.post(() -> {
            int badgeWidth = progressBadge.getWidth();
            float centered = handleX + (progressHandle.getWidth() / 2f) - (badgeWidth / 2f);
            float clamped = Math.max(0f, Math.min(centered, Math.max(0, trackWidth - badgeWidth)));
            progressBadge.setTranslationX(clamped);
        });
    }

    private int resolveTimelineIndexFromTrack(View trackView, float touchX) {
        if (trackView == null || timeline.size() <= 1) {
            return -1;
        }
        int width = trackView.getWidth();
        if (width <= 0) {
            return -1;
        }
        float clampedX = Math.min(width, Math.max(0f, touchX));
        float ratio = clampedX / width;
        return Math.min(timeline.size() - 1, Math.max(0, Math.round(ratio * (timeline.size() - 1))));
    }

    private int estimatePlaybackTotalSteps() {
        if ("bt_n_queens".equals(techniqueId) || "backtracking_n_queens".equals(techniqueId)) {
            return com.dsavisualizer.app.ui.visualization.runtime.BacktrackingStepEngine.getNQueensSteps(nQueensBoardSize).length;
        }
        if ("bt_sudoku".equals(techniqueId) || "backtracking_sudoku".equals(techniqueId)) {
            return com.dsavisualizer.app.ui.visualization.runtime.BacktrackingStepEngine.getSudokuSteps(sudokuGridSize, sudokuPreset).length;
        }
        if ("bt_knights_tour".equals(techniqueId) || "backtracking_knights_tour".equals(techniqueId)) {
            return com.dsavisualizer.app.ui.visualization.runtime.BacktrackingStepEngine.getKnightsTourMoves(knightsTourBoardSize, knightsTourStartPos).length;
        }
        if ("bt_maze".equals(techniqueId) || "bt_rat_in_a_maze".equals(techniqueId) || "backtracking_maze_solver".equals(techniqueId)) {
            return com.dsavisualizer.app.ui.visualization.runtime.BacktrackingStepEngine.getMazeSteps(mazeSize, mazePreset).length;
        }
        if ("bt_hamiltonian".equals(techniqueId) || "backtracking_hamiltonian".equals(techniqueId)) {
            return com.dsavisualizer.app.ui.visualization.runtime.BacktrackingStepEngine.getHamiltonianSteps(hamiltonianNodes, hamiltonianType).length;
        }
        if ("bt_graph_coloring".equals(techniqueId) || "backtracking_graph_coloring".equals(techniqueId)) {
            return com.dsavisualizer.app.ui.visualization.runtime.BacktrackingStepEngine.getGraphColoringSteps(graphColoringColors, graphColoringPreset).length;
        }
        if (VisualizationTechniqueRegistry.isDynamicProgramming(techniqueId) || (techniqueId != null && techniqueId.startsWith("dp_"))) {
            return com.dsavisualizer.app.ui.visualization.runtime.DpStepEngine.getDpTotalSteps(techniqueId, dpPreset);
        }
        if (VisualizationTechniqueRegistry.isStringPattern(techniqueId) || "pattern_matching".equals(techniqueId)) {
            return com.dsavisualizer.app.ui.visualization.runtime.StringPatternStepEngine.getStringPatternTotalSteps(techniqueId, patternText, patternQuery, stringPatternPreset);
        }
        if (VisualizationTechniqueRegistry.isAdvancedDs(techniqueId)) {
            return com.dsavisualizer.app.ui.visualization.runtime.AdvancedDsStepEngine.getTotalSteps(techniqueId, advancedDsPreset);
        }
        if (VisualizationTechniqueRegistry.isSpecialized(techniqueId)) {
            return com.dsavisualizer.app.ui.visualization.runtime.SpecializedStepEngine.getTotalSteps(techniqueId, specializedPreset);
        }
        if ("avl_tree".equals(techniqueId)) {
            return com.dsavisualizer.app.ui.visualization.runtime.AvlTreeStepEngine.getTotalSteps(workingValues);
        }
        if ("huffman_algorithm".equals(techniqueId)) {
            int[] weights = (workingValues != null && workingValues.length >= 2)
                    ? workingValues
                    : com.dsavisualizer.app.ui.visualization.runtime.HuffmanStepEngine.getDefaultWeights(huffmanPreset);
            return com.dsavisualizer.app.ui.visualization.runtime.HuffmanStepEngine.getTotalSteps(weights);
        }
        if (isSearchingMode()) {
            return Math.max(workingValues.length, timeline.size() - 1);
        }
        if (isSortingMode()) {
            return Math.max(workingValues.length * Math.max(1, workingValues.length - 1), timeline.size() - 1);
        }
        if (isGraphTechnique() || isTreeOrGraph()) {
            if (techniqueId != null && techniqueId.startsWith("graph_")) {
                return com.dsavisualizer.app.ui.visualization.runtime.GraphExpandedStepEngine.getTotalSteps(techniqueId);
            }
            return Math.max(Math.max(traversalOrder.length, workingValues.length), timeline.size() - 1);
        }
        return Math.max(Math.max(workingValues.length, 1), timeline.size() - 1);
    }

    private int resolveAccentColor() {
        if (VisualizationTechniqueRegistry.isSpecialized(techniqueId)) {
            return android.graphics.Color.parseColor("#10B981");
        }
        if (VisualizationTechniqueRegistry.isAdvancedDs(techniqueId)) {
            return android.graphics.Color.parseColor("#8B5CF6");
        }
        if (isSearchingMode()) {
            return android.graphics.Color.parseColor("#2563EB");
        }
        if (isSortingMode()) {
            return android.graphics.Color.parseColor("#D97706");
        }
        if ("stack_basics".equals(techniqueId)
            || "linked_stack_push".equals(techniqueId)
            || "linked_stack_pop".equals(techniqueId)) {
            return android.graphics.Color.parseColor("#64748B");
        }
        if ("queue_basics".equals(techniqueId)
            || "circular_queue".equals(techniqueId)
            || "linked_queue".equals(techniqueId)
            || "priority_queue_array".equals(techniqueId)
            || "priority_queue_2d".equals(techniqueId)) {
            return android.graphics.Color.parseColor("#2563EB");
        }
        if ("deque".equals(techniqueId)) {
            return android.graphics.Color.parseColor("#0F766E");
        }
        if (isStructureMode()) {
            return android.graphics.Color.parseColor("#9333EA");
        }
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) {
            return android.graphics.Color.parseColor("#059669");
        }
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) {
            return android.graphics.Color.parseColor("#DC2626");
        }
        return android.graphics.Color.parseColor("#0EA5E9");
    }

    private int resolveBarColor() {
        if (isSearchingMode()) {
            return android.graphics.Color.parseColor("#16263A");
        }
        if (isSortingMode()) {
            return android.graphics.Color.parseColor("#322112");
        }
        if ("stack_basics".equals(techniqueId)
            || "linked_stack_push".equals(techniqueId)
            || "linked_stack_pop".equals(techniqueId)) {
            return android.graphics.Color.parseColor("#1D2737");
        }
        if ("queue_basics".equals(techniqueId)
            || "circular_queue".equals(techniqueId)
            || "linked_queue".equals(techniqueId)
            || "priority_queue_array".equals(techniqueId)
            || "priority_queue_2d".equals(techniqueId)) {
            return android.graphics.Color.parseColor("#182B43");
        }
        if ("deque".equals(techniqueId)) {
            return android.graphics.Color.parseColor("#163435");
        }
        if (isStructureMode()) {
            return android.graphics.Color.parseColor("#2D1F34");
        }
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) {
            return android.graphics.Color.parseColor("#163126");
        }
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) {
            return android.graphics.Color.parseColor("#301B27");
        }
        return android.graphics.Color.parseColor("#182632");
    }

    private int darkenColor(int color, float factor) {
        int red = Math.max(0, Math.min(255, Math.round(android.graphics.Color.red(color) * factor)));
        int green = Math.max(0, Math.min(255, Math.round(android.graphics.Color.green(color) * factor)));
        int blue = Math.max(0, Math.min(255, Math.round(android.graphics.Color.blue(color) * factor)));
        return android.graphics.Color.rgb(red, green, blue);
    }

    private float dpToPx(float dp) {
        android.content.Context context = getContext();
        float density = context != null
            ? context.getResources().getDisplayMetrics().density
            : android.content.res.Resources.getSystem().getDisplayMetrics().density;
        return dp * density;
    }

    private void applyPlayerAccentColors() {
        int accentColor = resolveAccentColor();
        int barColor = resolveBarColor();
        if (progressBar != null) {
            progressBar.setProgressTintList(android.content.res.ColorStateList.valueOf(accentColor));
        }
        if (controlsProgressLine != null) {
            controlsProgressLine.setBackgroundTintList(android.content.res.ColorStateList.valueOf(accentColor));
        }
        if (stepChipView != null && stepChipView.getBackground() != null) {
            stepChipView.getBackground().setTint(darkenColor(accentColor, 0.84f));
        }
        styleTeachingRows(accentColor, barColor);
        styleCompletedBadgeForFamily();
    }

    private void styleTeachingRows(int accentColor, int barColor) {
        if (pointerLegendRow != null && pointerLegendRow.getBackground() != null) {
            pointerLegendRow.getBackground().setTint(darkenColor(barColor, 0.96f));
        }
        if (formulaRow != null && formulaRow.getBackground() != null) {
            formulaRow.getBackground().setTint(darkenColor(barColor, 0.94f));
        }
        if (formulaToggleButton != null) {
            formulaToggleButton.setTextColor(resolveAccentColor());
        }
        if (completionBanner != null && completionBanner.getBackground() != null) {
            completionBanner.getBackground().setTint(darkenColor(barColor, 0.98f));
        }
    }

    private void tintChip(TextView chip, int backgroundColor, int textColor) {
        if (chip == null) {
            return;
        }
        if (chip.getBackground() != null) {
            chip.getBackground().setTint(backgroundColor);
        }
        chip.setTextColor(textColor);
    }

    private void styleCompletedBadgeForFamily() {
        if (completedBadgeView == null || completedBadgeView.getBackground() == null) {
            return;
        }
        completedBadgeView.getBackground().setTint(resolveCompletedBadgeColor());
        completedBadgeView.setTextColor(resolveCompletedBadgeTextColor());
        android.graphics.drawable.Drawable icon = AppCompatResources.getDrawable(requireContext(), resolveCompletedBadgeIcon());
        if (icon != null) {
            icon.setTint(resolveCompletedBadgeTextColor());
        }
        completedBadgeView.setCompoundDrawablePadding(dp(4));
        completedBadgeView.setCompoundDrawablesRelativeWithIntrinsicBounds(icon, null, null, null);
    }

    private String resolveCompletedBadgeLabel() {
        if (isSearchingMode()) {
            return "Search Done";
        }
        if (isSortingMode()) {
            return "Sort Done";
        }
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) {
            return "Tree Done";
        }
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) {
            return "Graph Done";
        }
        return "Done";
    }

    private int resolveCompletedBadgeColor() {
        if (isSearchingMode()) {
            return android.graphics.Color.parseColor("#17345D");
        }
        if (isSortingMode()) {
            return android.graphics.Color.parseColor("#153C2B");
        }
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) {
            return android.graphics.Color.parseColor("#4A2E13");
        }
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) {
            return android.graphics.Color.parseColor("#4A1F27");
        }
        return darkenColor(resolveAccentColor(), 0.84f);
    }

    private int resolveCompletedBadgeTextColor() {
        if (isSearchingMode()) {
            return android.graphics.Color.parseColor("#DBEAFE");
        }
        if (isSortingMode()) {
            return android.graphics.Color.parseColor("#DCFCE7");
        }
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) {
            return android.graphics.Color.parseColor("#FEF3C7");
        }
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) {
            return android.graphics.Color.parseColor("#FCE7F3");
        }
        return android.graphics.Color.parseColor("#F8FBFF");
    }

    private int resolveCompletedBadgeIcon() {
        if (isSearchingMode()) {
            return R.drawable.ic_badge_search;
        }
        if (isSortingMode()) {
            return R.drawable.ic_badge_sort;
        }
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) {
            return R.drawable.ic_badge_tree;
        }
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) {
            return R.drawable.ic_badge_graph;
        }
        return R.drawable.ic_badge_search;
    }

    private void refreshTeachingDecor(String message) {
        updatePointerLegend();
        updateFormulaRow(message);
        updateStatePanel(message);
        updateStudyPseudocodeStep();
        updateAdjacencyPanel(message);
        updateVisualizerOverlayBadge();
        if (!playbackComplete) {
            hideCompletionBanner();
        }
    }
    private void updateStatePanel(String message) {
        if (statePanel == null || stateBody == null) {
            return;
        }
        List<String> items = new ArrayList<>();
        if (isSearchingMode() || isSortingMode()) {
            appendAlgorithmPointerItems(items);
        }
        if (isStructureMode()) {
            items.add("size = " + workingValues.length);
            if (usesStructureLimit()) {
                items.add("limit = " + structureLimit);
            }
            if (isStackTechnique() && workingValues.length > 0) {
                items.add("top = " + (workingValues.length - 1));
            }
            if (isQueueTechnique() && workingValues.length > 0) {
                items.add("front = 0");
                items.add("rear = " + (workingValues.length - 1));
            }
            if ("deque".equals(techniqueId) && workingValues.length > 0) {
                items.add("left = 0");
                items.add("right = " + (workingValues.length - 1));
            }
        }
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) {
            items.add("nodes = " + workingValues.length);
            if (isGraphTraversalTechnique()) {
                String frontier = resolveTraversalFrontierSummary();
                if (!frontier.isEmpty()) {
                    items.add(frontier);
                }
                items.add("visited = " + Math.max(0, Math.min(stepIndex + 1, traversalOrder.length)));
            }
            if (("dijkstra_algorithm".equals(techniqueId) || "shortest_paths".equals(techniqueId)) && dijkstraDistances.length > 0) {
                items.add("dist = " + resolveDistanceSummary());
            }
        }
        if (stateTitle != null) {
            stateTitle.setTextColor(resolveAccentColor());
        }
        if (items.isEmpty()) {
            hidePanelWithFade(statePanel, stateBody);
            return;
        }
        stateBody.setText(android.text.TextUtils.join("   |   ", items));
        statePanel.setVisibility(View.VISIBLE);
        animatePanelTextChange(statePanel, 180L);
    }

    private void updateStudyPseudocodeStep() {
        if (livePseudocodeText == null) {
            return;
        }
        List<String> lines = resolveMiniPseudocode();
        if (lines.isEmpty()) {
            livePseudocodeText.setVisibility(View.GONE);
            livePseudocodeText.setText("");
            return;
        }
        int highlightedIndex = resolveHighlightedPseudoLineIndex(lines.size());
        if (highlightedIndex < 0 || highlightedIndex >= lines.size()) {
            livePseudocodeText.setVisibility(View.GONE);
            livePseudocodeText.setText("");
            lastLivePseudocodeLine = "";
            return;
        }
        String nextLine = "Step " + (highlightedIndex + 1) + ": " + lines.get(highlightedIndex);
        livePseudocodeText.setTextColor(resolveAccentColor());
        if (nextLine.equals(lastLivePseudocodeLine)) {
            livePseudocodeText.setVisibility(View.VISIBLE);
            return;
        }
        if (!livePseudocodeText.isLaidOut() || lastLivePseudocodeLine.isEmpty()) {
            livePseudocodeText.setAlpha(1f);
            livePseudocodeText.setTranslationY(0f);
            livePseudocodeText.setText(nextLine);
            livePseudocodeText.setVisibility(View.VISIBLE);
            lastLivePseudocodeLine = nextLine;
            return;
        }
        livePseudocodeText.animate().cancel();
        livePseudocodeText.animate()
            .alpha(0f)
            .translationY(-10f)
            .setDuration(110L)
            .withEndAction(() -> {
                livePseudocodeText.setText(nextLine);
                livePseudocodeText.setTranslationY(12f);
                livePseudocodeText.setVisibility(View.VISIBLE);
                livePseudocodeText.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(170L)
                    .start();
                lastLivePseudocodeLine = nextLine;
            })
            .start();
    }
    private List<String> resolveMiniPseudocode() {
        List<String> lines = new ArrayList<>();
        if ("binary_search".equals(techniqueId)) {
            lines.add("while beg <= end");
            lines.add("mid = (beg + end) / 2");
            lines.add("if data[mid] == target return mid");
            lines.add("if data[mid] < target beg = mid + 1");
            lines.add("else end = mid - 1");
            return lines;
        }
        if (isSearchingMode()) {
            lines.add("scan current element");
            lines.add("compare element with target");
            lines.add("if match return found");
            lines.add("else move to next");
            return lines;
        }
        if ("radix_sort".equals(techniqueId)) {
            lines.add("start current digit pass");
            lines.add("read active digit and choose bucket");
            lines.add("place value into bucket");
            lines.add("rebuild array from buckets");
            lines.add("move to next larger digit");
            return lines;
        }
        if ("quicksort".equals(techniqueId)) {
            lines.add("choose pivot for current subarray");
            lines.add("scan next value against pivot");
            lines.add("swap into left partition if needed");
            lines.add("advance partition boundary");
            lines.add("place pivot in final position");
            return lines;
        }
        if (isSortingMode()) {
            lines.add("pick active pair");
            lines.add("compare left and right values");
            lines.add("swap if out of order");
            lines.add("advance to next pair");
            return lines;
        }
        if (isStructureMode()) {
            lines.add("locate active node or end");
            lines.add("update next/front/rear/top links");
            lines.add("commit insert or delete");
            lines.add("move pointer to next state");
            return lines;
        }
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) {
            lines.add(isDirectedGraphTechnique() ? "follow outgoing edges" : "inspect adjacent edges");
            lines.add("choose current node or edge");
            lines.add("update frontier or distance");
            lines.add("mark visit or settle result");
            return lines;
        }
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) {
            lines.add("enter current node");
            lines.add("move left or inspect parent");
            lines.add("visit node");
            lines.add("move right or rebalance");
            return lines;
        }
        return lines;
    }

    private int resolveHighlightedPseudoLineIndex(int lineCount) {
        if (lineCount <= 0) {
            return -1;
        }
        Integer highlighted = sessionViewModel == null ? null : sessionViewModel.getHighlightedCodeLine().getValue();
        if (highlighted == null || highlighted < 0) {
            return 0;
        }
        return Math.max(0, Math.min(lineCount - 1, highlighted - 1));
    }
    private String resolveTraversalFrontierSummary() {
        if (traversalOrder == null || traversalOrder.length == 0) {
            return "";
        }
        List<String> labels = new ArrayList<>();
        int start = Math.max(0, stepIndex + 1);
        int limit = Math.min(traversalOrder.length, start + 4);
        for (int i = start; i < limit; i++) {
            labels.add(String.valueOf(resolveNodeLabel(traversalOrder[i])));
        }
        if (labels.isEmpty()) {
            return currentTraversalMode != null && currentTraversalMode.toUpperCase().contains("DFS")
                ? "stack = empty"
                : "queue = empty";
        }
        String prefix = currentTraversalMode != null && currentTraversalMode.toUpperCase().contains("DFS")
            ? "stack = "
            : "queue = ";
        return prefix + android.text.TextUtils.join(",", labels);
    }

    private String resolveDistanceSummary() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < dijkstraDistances.length && i < 4; i++) {
            if (dijkstraDistances[i] < Integer.MAX_VALUE / 4) {
                items.add(resolveNodeLabel(i) + ":" + dijkstraDistances[i]);
            }
        }
        return items.isEmpty() ? "pending" : android.text.TextUtils.join(",", items);
    }

    private void updateAdjacencyPanel(String message) {
        if (adjacencyPanel == null || adjacencyBody == null) {
            return;
        }
        if (!VisualizationTechniqueRegistry.isGraph(techniqueId)) {
            hidePanelWithFade(adjacencyPanel, adjacencyBody);
            return;
        }
        if (adjacencyTitle != null) {
            adjacencyTitle.setTextColor(resolveAccentColor());
        }
        adjacencyTitle.setText(isGraphTraversalTechnique() ? "Adjacency + Frontier" : "Adjacency");
        adjacencyBody.setText(buildAdjacencySummary(message));
        adjacencyPanel.setVisibility(View.VISIBLE);
        animatePanelTextChange(adjacencyPanel, 180L);
    }

    private void animatePanelTextChange(@Nullable View panel, long durationMs) {
        if (panel == null) {
            return;
        }
        panel.animate().cancel();
        panel.setAlpha(0.72f);
        panel.setTranslationY(dpToPx(6));
        panel.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(durationMs)
            .setInterpolator(new DecelerateInterpolator())
            .start();
    }

    private void hidePanelWithFade(@Nullable View panel, @Nullable TextView body) {
        if (panel == null) {
            return;
        }
        if (panel.getVisibility() != View.VISIBLE) {
            if (body != null) {
                body.setText("");
            }
            return;
        }
        panel.animate().cancel();
        panel.animate()
            .alpha(0f)
            .setDuration(180L)
            .withEndAction(() -> {
                panel.setVisibility(View.GONE);
                panel.setAlpha(1f);
                if (body != null) {
                    body.setText("");
                }
            })
            .start();
    }

    private String buildAdjacencySummary(String message) {
        int count = Math.max(1, Math.min(workingValues.length == 0 ? 5 : workingValues.length, 6));
        List<int[]> edges = buildGraphEdges(count);
        boolean directed = isDirectedGraphTechnique();
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            List<String> neighbors = new ArrayList<>();
            for (int[] edge : edges) {
                if (edge[0] == i) {
                    neighbors.add(resolveNodeLabel(edge[1]) + "(" + resolveGraphEdgeWeight(edge[0], edge[1]) + ")");
                } else if (!directed && edge[1] == i) {
                    neighbors.add(resolveNodeLabel(edge[0]) + "(" + resolveGraphEdgeWeight(edge[0], edge[1]) + ")");
                }
            }
            rows.add(resolveNodeLabel(i) + " -> [" + android.text.TextUtils.join(", ", neighbors) + "]");
        }
        String joined = android.text.TextUtils.join("\n", rows);
        String frontier = resolveTraversalFrontierSummary();
        if (!frontier.isEmpty() && isGraphTraversalTechnique()) {
            joined += "\n" + frontier;
        }
        return joined;
    }

    private List<int[]> buildGraphEdges(int nodeCount) {
        int[][] baseEdges = {
            {0, 1}, {1, 2}, {0, 3}, {1, 3}, {1, 4}, {2, 4}, {3, 4}
        };
        List<int[]> edges = new ArrayList<>();
        for (int[] edge : baseEdges) {
            if (edge[0] >= nodeCount || edge[1] >= nodeCount) {
                continue;
            }
            if ("delete_edge_graph".equals(techniqueId)
                && matchesGraphEdge(edge[0], edge[1], visualizerView == null ? -1 : visualizerView.getActiveEdgeFrom(), visualizerView == null ? -1 : visualizerView.getActiveEdgeTo())) {
                continue;
            }
            edges.add(new int[]{edge[0], edge[1]});
        }
        if ("insert_edge_graph".equals(techniqueId) && visualizerView != null) {
            int from = visualizerView.getActiveEdgeFrom();
            int to = visualizerView.getActiveEdgeTo();
            if (from >= 0 && to >= 0 && from < nodeCount && to < nodeCount) {
                boolean exists = false;
                for (int[] edge : edges) {
                    if (matchesGraphEdge(edge[0], edge[1], from, to)) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    edges.add(new int[]{from, to});
                }
            }
        }
        return edges;
    }

    private boolean matchesGraphEdge(int firstFrom, int firstTo, int secondFrom, int secondTo) {
        if (isDirectedGraphTechnique()) {
            return firstFrom == secondFrom && firstTo == secondTo;
        }
        return (firstFrom == secondFrom && firstTo == secondTo)
            || (firstFrom == secondTo && firstTo == secondFrom);
    }

    private int resolveGraphEdgeWeight(int from, int to) {
        int w = com.dsavisualizer.app.ui.visualization.runtime.GraphStepEngine.getGraphEdgeWeight(graphPreset, from, to);
        if (w > 0) return w;
        if (from >= 0 && to >= 0 && from != to) return Math.abs(from - to) + 2;
        return -1;
    }

    private int resolveNodeLabel(int index) {
        if (index >= 0 && index < workingValues.length) {
            return workingValues[index];
        }
        return index;
    }

    private boolean isDirectedGraphTechnique() {
        return "topological_sort".equals(techniqueId)
            || "warshall_algorithm".equals(techniqueId)
            || "floyd_algorithm".equals(techniqueId)
            || "shortest_paths".equals(techniqueId)
            || "dijkstra_algorithm".equals(techniqueId)
            || "insert_edge_graph".equals(techniqueId)
            || "delete_edge_graph".equals(techniqueId)
            || "find_edge_graph".equals(techniqueId);
    }
    private boolean isStackTechnique() {
        return "stack_basics".equals(techniqueId)
            || "linked_stack_push".equals(techniqueId)
            || "linked_stack_pop".equals(techniqueId);
    }

    private boolean isQueueTechnique() {
        return "queue_basics".equals(techniqueId)
            || "circular_queue".equals(techniqueId)
            || "linked_queue".equals(techniqueId);
    }

    private boolean isGraphTraversalTechnique() {
        return VisualizationTechniqueRegistry.isGraph(techniqueId)
            && !("find_edge_graph".equals(techniqueId)
            || "insert_node_graph".equals(techniqueId)
            || "insert_edge_graph".equals(techniqueId)
            || "delete_edge_graph".equals(techniqueId)
            || "delete_vertex_graph".equals(techniqueId));
    }

    private String resolveOperationMeaningChip(String message) {
        String safe = message == null ? "" : message.toLowerCase();
        if (safe.contains("not found")) {
            return "Miss";
        }
        if (safe.contains("found")) {
            return "Found";
        }
        if (safe.contains("push") || safe.contains("enqueue") || safe.contains("insert") || safe.contains("add")) {
            return "Insert";
        }
        if (safe.contains("pop") || safe.contains("dequeue") || safe.contains("delete") || safe.contains("remov")) {
            return "Delete";
        }
        if (safe.contains("swap")) {
            return "Swap";
        }
        if (safe.contains("compar") || safe.contains("match") || safe.contains("check")) {
            return "Compare";
        }
        if (safe.contains("visit") || safe.contains("travers")) {
            return "Visit";
        }
        if (safe.contains("start") || safe.contains("initial")) {
            return "Start";
        }
        if (safe.contains("complete") || safe.contains("done")) {
            return "Done";
        }
        return "Trace";
    }

    private String resolveFamilyChipLabel() {
        if (isSearchingMode()) {
            return "Search";
        }
        if (isSortingMode()) {
            return "Sort";
        }
        if ("stack_basics".equals(techniqueId)
            || "linked_stack_push".equals(techniqueId)
            || "linked_stack_pop".equals(techniqueId)) {
            return "Stack";
        }
        if ("queue_basics".equals(techniqueId)
            || "circular_queue".equals(techniqueId)
            || "linked_queue".equals(techniqueId)) {
            return "Queue";
        }
        if ("deque".equals(techniqueId)) {
            return "Deque";
        }
        if (isStructureMode()) {
            return "Linked";
        }
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) {
            return "Tree";
        }
        if (VisualizationTechniqueRegistry.isGraph(techniqueId)) {
            return "Graph";
        }
        return "Theme";
    }

    private void updatePointerLegend() {
        if (pointerLegendRow == null || pointerLegendBody == null) {
            return;
        }
        List<String> legendItems = new ArrayList<>();
        appendAlgorithmPointerItems(legendItems);
        appendStructurePointerItems(legendItems);
        if (isTreeOrGraph() && stepIndex >= 0) {
            legendItems.add("node = " + stepIndex);
        }
        if (pointerLegendTitle != null) {
            pointerLegendTitle.setText(R.string.vis_pointer_legend_title);
            pointerLegendTitle.setTextColor(resolveAccentColor());
        }
        if (!legendItems.isEmpty()) {
            pointerLegendBody.setText(android.text.TextUtils.join("   |   ", legendItems));
            pointerLegendRow.setVisibility(View.VISIBLE);
            animatePanelTextChange(pointerLegendRow, 180L);
            return;
        }
        pointerLegendRow.setVisibility(View.GONE);
        pointerLegendBody.setText(legendItems.isEmpty()
            ? getString(R.string.vis_pointer_legend_empty)
            : android.text.TextUtils.join("   •   ", legendItems));
    }

    private void appendAlgorithmPointerItems(List<String> items) {
        if (items == null) {
            return;
        }
        for (StepPointer pointer : resolveLearningPointers()) {
            appendPointerLegendItem(items, pointer.label, pointer.index);
        }
        if (visualizerView != null) {
            appendPointerLegendItem(items, visualizerView.getPrimaryPointerLabel(), visualizerView.getPrimaryPointerIndex());
            appendPointerLegendItem(items, visualizerView.getSecondaryPointerLabel(), visualizerView.getSecondaryPointerIndex());
        }
    }

    private void appendStructurePointerItems(List<String> items) {
        if (items == null || !isStructureMode()) {
            return;
        }
        if (isStackTechnique() && workingValues.length > 0) {
            appendPointerLegendItem(items, "top", workingValues.length - 1);
        }
        if (isQueueTechnique() && workingValues.length > 0) {
            appendPointerLegendItem(items, "front", 0);
            appendPointerLegendItem(items, "rear", workingValues.length - 1);
        }
        if ("deque".equals(techniqueId) && workingValues.length > 0) {
            appendPointerLegendItem(items, "left", 0);
            appendPointerLegendItem(items, "right", workingValues.length - 1);
        }
        if (visualizerView != null) {
            appendPointerLegendItem(items, visualizerView.getPrimaryPointerLabel(), visualizerView.getPrimaryPointerIndex());
            appendPointerLegendItem(items, visualizerView.getSecondaryPointerLabel(), visualizerView.getSecondaryPointerIndex());
        }
    }

    private void appendPointerLegendItem(List<String> legendItems, String label, int index) {
        if (label == null || label.trim().isEmpty() || index < 0) {
            return;
        }
        String normalized = label.toLowerCase();
        if ("low".equals(normalized)) {
            normalized = "beg";
        } else if ("high".equals(normalized)) {
            normalized = "end";
        }
        String item = normalized + " = " + index;
        if (!legendItems.contains(item)) {
            legendItems.add(item);
        }
    }

    private void updateFormulaRow(String message) {
        if (formulaRow == null || formulaBody == null) {
            return;
        }
        if (formulaToggleButton != null) {
            formulaToggleButton.setText(showMathTeaching
                ? getString(R.string.vis_formula_toggle_on)
                : getString(R.string.vis_formula_toggle_off));
        }
        if (!supportsFormulaTeaching()) {
            formulaRow.setVisibility(View.GONE);
            formulaBody.setText("");
            return;
        }
        String formula = resolveFormulaText(message);
        if (formulaTitle != null) {
            formulaTitle.setTextColor(resolveAccentColor());
        }
        if (!showMathTeaching) {
            formulaBody.setText(getString(R.string.vis_formula_hidden));
            formulaRow.setVisibility(View.VISIBLE);
            return;
        }
        if (formula == null || formula.trim().isEmpty()) {
            formulaRow.setVisibility(View.GONE);
            formulaBody.setText("");
            return;
        }
        formulaBody.setText(buildHighlightedDetails(formula));
        formulaRow.setVisibility(View.VISIBLE);
    }

    private String resolveFormulaText(String message) {
        if ("linear_search".equals(techniqueId)) {
            if (stepIndex >= 0 && stepIndex < workingValues.length) {
                return "check arr[" + stepIndex + "] = " + workingValues[stepIndex] + ", target = " + target;
            }
            return null;
        }
        if ("binary_search".equals(techniqueId)) {
            if (stepIndex >= 0 && auxIndex >= 0 && binaryMidIndex >= 0) {
                int midValue = binaryMidIndex < workingValues.length ? workingValues[binaryMidIndex] : -1;
                String safe = message == null ? "" : message.toLowerCase();
                if (safe.contains("target is greater")) {
                    return "item(" + target + ") > data(" + midValue + ")  ->  beg = mid + 1 = " + stepIndex;
                }
                if (safe.contains("target is smaller")) {
                    return "item(" + target + ") < data(" + midValue + ")  ->  end = mid - 1 = " + auxIndex;
                }
                if (safe.contains("found")) {
                    return "item(" + target + ") = data(" + midValue + ") at mid = " + binaryMidIndex;
                }
                return "mid = (beg + end) / 2 = (" + stepIndex + " + " + auxIndex + ") / 2 = " + binaryMidIndex
                    + "   data = " + midValue;
            }
            return null;
        }
        if ("jump_search".equals(techniqueId)) {
            if (workingValues.length == 0) {
                return null;
            }
            int block = Math.max(1, (int) Math.floor(Math.sqrt(workingValues.length)));
            int start = Math.max(0, stepIndex);
            int end = Math.min(workingValues.length - 1, start + block - 1);
            return "block = floor(sqrt(" + workingValues.length + ")) = " + block + "   range = [" + start + ", " + end + "]";
        }
        if ("merge_sort".equals(techniqueId) || "merging".equals(techniqueId)) {
            if (mergeRunSize > 0 && mergePassStart >= 0 && mergeWorkingValues.length > 0) {
                int left = mergePassStart;
                int middle = Math.min(left + mergeRunSize, mergeWorkingValues.length);
                int right = Math.min(left + (mergeRunSize * 2), mergeWorkingValues.length);
                return "mid = left + run = " + left + " + " + mergeRunSize + " = " + middle + "   right = " + right;
            }
            return null;
        }
        if ("heapsort".equals(techniqueId) || "max_heap_insertion".equals(techniqueId) || "max_heap_deletion".equals(techniqueId)) {
            if (stepIndex >= 0) {
                int leftChild = (2 * stepIndex) + 1;
                int rightChild = (2 * stepIndex) + 2;
                return "left = 2i + 1 = " + leftChild + "   right = 2i + 2 = " + rightChild;
            }
            return null;
        }
        if ("dijkstra_algorithm".equals(techniqueId) || "shortest_paths".equals(techniqueId)) {
            int from = visualizerView == null ? -1 : visualizerView.getActiveEdgeFrom();
            int to = visualizerView == null ? -1 : visualizerView.getActiveEdgeTo();
            if (from >= 0
                && to >= 0
                && from < dijkstraWeights.length
                && to < dijkstraWeights[from].length
                && from < dijkstraDistances.length) {
                int dist = dijkstraDistances[from];
                int weight = dijkstraWeights[from][to];
                if (dist < Integer.MAX_VALUE / 4 && weight > 0) {
                    return "dist[" + from + "] + w(" + from + "," + to + ") = " + dist + " + " + weight + " = " + safeAdd(dist, weight);
                }
            }
            return null;
        }
        if ("huffman_algorithm".equals(techniqueId) && message != null) {
            java.util.regex.Matcher matcher = java.util.regex.Pattern
                .compile("merge weights\\s+(\\d+)\\s+and\\s+(\\d+)\\s+into\\s+(\\d+)", java.util.regex.Pattern.CASE_INSENSITIVE)
                .matcher(message);
            if (matcher.find()) {
                return matcher.group(1) + " + " + matcher.group(2) + " = " + matcher.group(3);
            }
        }
        return null;
    }


    private void updateVisualizerOverlayBadge() {
        if (visualizerView == null) {
            return;
        }
        visualizerView.setOverlayBadgeText(resolveVisualizerOverlayBadge());
    }

    private String resolveVisualizerOverlayBadge() {
        if (!playbackComplete) {
            return "";
        }
        if (foundIndex >= 0) {
            return "Found at index " + foundIndex;
        }
        if (isSortingMode()) {
            return "Sorted ascending";
        }
        if (isSearchingMode()) {
            return "Element Not Found";
        }
        if ("huffman_algorithm".equals(techniqueId)) {
            return "Tree completed";
        }
        if ("dijkstra_algorithm".equals(techniqueId) || "shortest_paths".equals(techniqueId)) {
            return "Shortest paths ready";
        }
        return "Run completed";
    }

    private boolean supportsFormulaTeaching() {
        return "linear_search".equals(techniqueId)
            || "binary_search".equals(techniqueId)
            || "jump_search".equals(techniqueId)
            || "merge_sort".equals(techniqueId)
            || "merging".equals(techniqueId)
            || "heapsort".equals(techniqueId)
            || "max_heap_insertion".equals(techniqueId)
            || "max_heap_deletion".equals(techniqueId)
            || "dijkstra_algorithm".equals(techniqueId)
            || "shortest_paths".equals(techniqueId)
            || "huffman_algorithm".equals(techniqueId);
    }

    private void showCompletionBanner() {
        if (completionBanner == null) {
            return;
        }
        if (completionBannerTitle != null) {
            completionBannerTitle.setText(resolveCompletionBannerTitle());
            completionBannerTitle.setTextColor(resolveAccentColor());
        }
        if (completionBannerBody != null) {
            completionBannerBody.setText(resolveCompletionBannerBody());
        }
        completionBanner.setVisibility(View.VISIBLE);
        completionBanner.setAlpha(0f);
        completionBanner.setTranslationY(dpToPx(8));
        completionBanner.animate().alpha(1f).translationY(0f).setDuration(180L).start();
    }

    private void hideCompletionBanner() {
        if (completionBanner == null) {
            return;
        }
        completionBanner.animate().cancel();
        completionBanner.setVisibility(View.GONE);
        completionBanner.setAlpha(1f);
        completionBanner.setTranslationY(0f);
    }

    private String resolveCompletionBannerTitle() {
        if (foundIndex >= 0) {
            return "Target Found";
        }
        if (isSortingMode()) {
            return "Sorting Completed";
        }
        if (isSearchingMode()) {
            return "Search Completed";
        }
        if (VisualizationTechniqueRegistry.isTree(techniqueId)) {
            return "Tree Step Completed";
        }
        if (isStructureMode()) {
            return "Structure Updated";
        }
        return getString(R.string.vis_completion_title);
    }

    private String resolveCompletionBannerBody() {
        if (statusText != null && statusText.getText() != null && statusText.getText().length() > 0) {
            return statusText.getText().toString();
        }
        return getString(R.string.vis_completion_body);
    }

    private void replayCurrentStep() {
        boolean resumePlayback = playbackController != null
            && playbackController.getPlaybackState().getValue() != null
            && playbackController.getPlaybackState().getValue().isPlaying;
        handler.removeCallbacksAndMessages(null);
        if (replayButton != null) {
            animateMaterialReplay(replayButton);
        }
        if (studyReplayButton != null) {
            animateMaterialReplay(studyReplayButton);
        }
        animateReplayLineGlow();
        animateRestartedBadgeFlash();

        if (playbackController != null) {
            playbackController.reset();
            if (resumePlayback) {
                handler.postDelayed(() -> {
                    if (isAdded() && playbackController != null) {
                        playbackController.play();
                    }
                }, 120L);
            }
            return;
        }
        resetVisualization();
    }

    private void stepForwardOnce() {
        try {
            PlaybackState state = playbackController == null ? null : playbackController.getPlaybackState().getValue();
            if (state != null && state.isAtEnd) {
                if (playbackController != null) {
                    playbackController.reset();
                }
            }
            pulseStepButtons(true);
            if (playbackController != null) {
                playbackController.nextStep();
            } else {
                runSingleStep();
            }
        } catch (Throwable t) {
            Log.e(TAG, "Error in stepForwardOnce", t);
        }
    }

    private void stepBackwardOnce() {
        PlaybackState state = playbackController == null ? null : playbackController.getPlaybackState().getValue();
        if ((state != null && state.isAtStart) || (playbackController == null && timeline.size() <= 1)) {
            shakeStepBoundary(false);
            return;
        }
        pulseStepButtons(false);
        if (playbackController != null) {
            playbackController.previousStep();
        } else {
            handlePreviousStep();
        }
    }

    private void shakeStepBoundary(boolean atForwardEdge) {
        MaterialButton primary = atForwardEdge ? nextButton : previousButton;
        MaterialButton secondary = atForwardEdge ? studyNextButton : studyPreviousButton;
        animateMaterialBoundary(primary, atForwardEdge);
        animateMaterialBoundary(secondary, atForwardEdge);
        if (controlsGroup != null) {
            controlsGroup.performHapticFeedback(android.view.HapticFeedbackConstants.REJECT);
        }
    }

    private void animateMaterialReplay(MaterialButton button) {
        button.animate().cancel();
        button.animate()
            .rotation(-16f)
            .scaleX(0.96f)
            .scaleY(0.96f)
            .setDuration(90L)
            .withEndAction(() -> button.animate()
                .rotation(0f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(170L)
                .start())
            .start();
    }

    private void animateMaterialBoundary(MaterialButton button, boolean forwardEdge) {
        if (button == null) {
            return;
        }
        float offset = (forwardEdge ? 1f : -1f) * dpToPx(5);
        button.animate().cancel();
        button.animate()
            .translationX(offset)
            .setDuration(60L)
            .withEndAction(() -> button.animate()
                .translationX(-offset * 0.6f)
                .setDuration(70L)
                .withEndAction(() -> button.animate().translationX(0f).setDuration(90L).start())
                .start())
            .start();
    }

    private void animateReplayLineGlow() {
        if (controlsProgressLine == null) {
            return;
        }
        controlsProgressLine.animate().cancel();
        controlsProgressLine.setAlpha(0.92f);
        controlsProgressLine.setScaleY(1f);
        controlsProgressLine.animate()
            .alpha(1f)
            .scaleY(1.8f)
            .setDuration(110L)
            .withEndAction(() -> controlsProgressLine.animate()
                .alpha(0.92f)
                .scaleY(1f)
                .setDuration(190L)
                .start())
            .start();
    }

    private void animateCompletionFlash() {
        if (controlsProgressLine != null) {
            controlsProgressLine.animate().cancel();
            controlsProgressLine.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#86EFAC")));
            controlsProgressLine.setAlpha(1f);
            controlsProgressLine.setScaleY(1.15f);
            controlsProgressLine.animate()
                .alpha(0.95f)
                .scaleY(2.2f)
                .setDuration(130L)
                .withEndAction(() -> {
                    if (controlsProgressLine != null) {
                        controlsProgressLine.setBackgroundTintList(android.content.res.ColorStateList.valueOf(resolveAccentColor()));
                        controlsProgressLine.animate().alpha(0.92f).scaleY(1f).setDuration(240L).start();
                    }
                })
                .start();
        }
        if (controlsGroup != null) {
            controlsGroup.animate().cancel();
            controlsGroup.setScaleX(1f);
            controlsGroup.setScaleY(1f);
            controlsGroup.animate().scaleX(1.015f).scaleY(1.015f).setDuration(120L).withEndAction(() ->
                controlsGroup.animate().scaleX(1f).scaleY(1f).setDuration(180L).start()
            ).start();
            controlsGroup.performHapticFeedback(android.view.HapticFeedbackConstants.CONFIRM);
        }
        if (stepChipView != null) {
            stepChipView.animate().cancel();
            stepChipView.animate().scaleX(1.08f).scaleY(1.08f).setDuration(110L).withEndAction(() ->
                stepChipView.animate().scaleX(1f).scaleY(1f).setDuration(180L).start()
            ).start();
        }
    }

    private void animateRestartedBadgeFlash() {
        if (completedBadgeView == null || !isAdded()) {
            return;
        }
        if (restartBadgeHideRunnable != null) {
            handler.removeCallbacks(restartBadgeHideRunnable);
        }
        completedBadgeView.animate().cancel();
        completedBadgeView.setText("Restarted");
        if (completedBadgeView.getBackground() != null) {
            completedBadgeView.getBackground().setTint(darkenColor(resolveAccentColor(), 0.84f));
        }
        completedBadgeView.setTextColor(android.graphics.Color.parseColor("#E0F2FE"));
        android.graphics.drawable.Drawable icon = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_player_replay);
        if (icon != null) {
            icon.setTint(android.graphics.Color.parseColor("#E0F2FE"));
        }
        completedBadgeView.setCompoundDrawablePadding(dp(4));
        completedBadgeView.setCompoundDrawablesRelativeWithIntrinsicBounds(icon, null, null, null);
        completedBadgeView.setVisibility(View.VISIBLE);
        completedBadgeView.setAlpha(0f);
        completedBadgeView.setScaleX(0.9f);
        completedBadgeView.setScaleY(0.9f);
        completedBadgeView.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(120L).start();
        completedBadgeView.post(this::refreshInlineChipPadding);
        restartBadgeHideRunnable = () -> {
            if (completedBadgeView == null) {
                return;
            }
            completedBadgeView.animate().alpha(0f).scaleX(0.94f).scaleY(0.94f).setDuration(140L)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (completedBadgeView != null && "Restarted".contentEquals(completedBadgeView.getText())) {
                            completedBadgeView.setVisibility(View.GONE);
                            completedBadgeView.setScaleX(1f);
                            completedBadgeView.setScaleY(1f);
                            completedBadgeView.animate().setListener(null);
                            refreshInlineChipPadding();
                        }
                    }
                })
                .start();
        };
        handler.postDelayed(restartBadgeHideRunnable, 900L);
    }

    private boolean startFastStep(boolean forward) {
        stopFastStepping();
        dismissControlsHint();
        fastStepStartIndex = Math.max(0, estimatePlaybackCurrentStep());
        if (forward) {
            startMaterialScrub(nextButton, true);
            startMaterialScrub(studyNextButton, true);
        } else {
            startMaterialScrub(previousButton, false);
            startMaterialScrub(studyPreviousButton, false);
        }
        updateFastStepBadge(fastStepStartIndex);
        if (forward) {
            fastSteppingForward = true;
            stepForwardOnce();
            handler.postDelayed(fastForwardRunnable, 180L);
        } else {
            fastSteppingBackward = true;
            stepBackwardOnce();
            handler.postDelayed(fastBackwardRunnable, 180L);
        }
        return true;
    }

    private void stopFastStepping() {
        fastSteppingForward = false;
        fastSteppingBackward = false;
        clearFastStepBadge();
        fastStepStartIndex = -1;
        handler.removeCallbacks(fastForwardRunnable);
        handler.removeCallbacks(fastBackwardRunnable);
        stopMaterialScrub(previousButton);
        stopMaterialScrub(studyPreviousButton);
        stopMaterialScrub(nextButton);
        stopMaterialScrub(studyNextButton);
    }

    private void startMaterialScrub(MaterialButton button, boolean forward) {
        if (button == null) {
            return;
        }
        button.animate().cancel();
        button.animate()
            .translationX((forward ? 1f : -1f) * dpToPx(4))
            .scaleX(1.04f)
            .scaleY(1.04f)
            .setDuration(120L)
            .start();
    }

    private void stopMaterialScrub(MaterialButton button) {
        if (button == null) {
            return;
        }
        button.animate().cancel();
        button.animate()
            .translationX(0f)
            .rotation(0f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(120L)
            .start();
    }

    private void bindFastStepRelease(View button) {
        if (button == null) {
            return;
        }
        button.setOnTouchListener((v, event) -> {
            if (event.getAction() == android.view.MotionEvent.ACTION_UP
                || event.getAction() == android.view.MotionEvent.ACTION_CANCEL) {
                stopFastStepping();
            }
            return false;
        });
    }

    private int estimatePlaybackCurrentStep() {
        if (playbackController != null && playbackController.getPlaybackState().getValue() != null) {
            return playbackController.getPlaybackState().getValue().currentStepIndex;
        }
        return Math.max(0, stepIndex + 1);
    }

    private void updateFastStepBadge(int currentStep) {
        if (stepChipView == null || fastStepStartIndex < 0) {
            return;
        }
        int delta = currentStep - fastStepStartIndex;
        if (delta == 0 || (!fastSteppingForward && !fastSteppingBackward)) {
            return;
        }
        String badgeText = delta > 0
            ? getString(R.string.vis_fast_step_forward, delta)
            : getString(R.string.vis_fast_step_backward, Math.abs(delta));
        stepChipView.animate().cancel();
        stepChipView.setText(badgeText);
        if (stepChipView.getBackground() != null) {
            stepChipView.getBackground().setTint(darkenColor(resolveAccentColor(), 0.84f));
        }
        stepChipView.setTextColor(android.graphics.Color.parseColor("#E0F2FE"));
        stepChipView.setAlpha(1f);
    }

    private void clearFastStepBadge() {
        if (stepChipView == null) {
            return;
        }
        CharSequence text = stepChipView.getText();
        if (text != null && text.length() > 0) {
            char first = text.charAt(0);
            if (first == '+' || first == '-') {
                int current = Math.max(0, estimatePlaybackCurrentStep());
                int total = Math.max(1, estimatePlaybackTotalSteps());
                stepChipView.setText(current + "/" + total);
            }
        }
    }

    private void updateStickyControls(int scrollY) {
        if (controlsGroup == null || contentScrollView == null) {
            return;
        }
        if (controlsGroup.getParent() != getView()) {
            controlsGroup.setTranslationY(0f);
            controlsGroup.setScaleX(1f);
            controlsGroup.setScaleY(1f);
            controlsGroup.setElevation(dpToPx(14));
            if (controlsStrip != null) {
                controlsStrip.setScaleY(1f);
            }
            return;
        }
        controlsGroup.post(() -> {
            int stickyThreshold = controlsGroup.getTop();
            float translation = Math.max(0f, scrollY - stickyThreshold);
            controlsGroup.setTranslationY(translation);
            float compactProgress = Math.min(1f, translation / Math.max(1f, dpToPx(32)));
            float scale = 1f - (0.04f * compactProgress);
            controlsGroup.setElevation(translation > 0f ? dpToPx(18) : dpToPx(14));
            controlsGroup.setScaleX(scale);
            controlsGroup.setScaleY(1f - (0.025f * compactProgress));
            if (controlsStrip != null) {
                controlsStrip.setScaleY(1f - (0.05f * compactProgress));
            }
        });
    }

    private String buildRandomLetters(int length) {
        String letters = "ABCDEFXYZ";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(letters.length());
            builder.append(letters.charAt(index));
        }
        return builder.toString();
    }

    private void swap(int left, int right) {
        int temp = workingValues[left];
        workingValues[left] = workingValues[right];
        workingValues[right] = temp;
    }

    @Override
    public void initializeCustomSortPreview() {
        if ("warshall_algorithm".equals(techniqueId)) {
            matrixValues = new int[][] {
                {0, 1, 0, 1},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
                {0, 0, 0, 0}
            };
            matrixK = -1;
            matrixRow = -1;
            matrixCol = -1;
            visualizerView.setMatrixState(matrixValues, matrixK, matrixRow, matrixCol, resolveMatrixLabel());
            return;
        }

        if ("floyd_algorithm".equals(techniqueId) || "shortest_paths".equals(techniqueId)
            || "graph_floyd_warshall".equals(techniqueId) || "graph_johnson".equals(techniqueId)) {
            matrixValues = new int[][] {
                {0, 5, 9999, 10},
                {9999, 0, 3, 9999},
                {9999, 9999, 0, 1},
                {9999, 9999, 9999, 0}
            };
            matrixK = -1;
            matrixRow = -1;
            matrixCol = -1;
            visualizerView.setMatrixState(matrixValues, matrixK, matrixRow, matrixCol, resolveMatrixLabel());
            return;
        }

        if ("bt_n_queens".equals(techniqueId) || "backtracking_n_queens".equals(techniqueId)) {
            int n = nQueensBoardSize >= 4 ? nQueensBoardSize : 4;
            matrixValues = new int[n][n];
            matrixK = -1;
            matrixRow = -1;
            matrixCol = -1;
            matrixLabel = "N-Queens " + n + "x" + n + " Chessboard";
            visualizerView.setMatrixState(matrixValues, matrixK, matrixRow, matrixCol, matrixLabel);
            return;
        }

        if ("bt_sudoku".equals(techniqueId) || "backtracking_sudoku".equals(techniqueId)) {
            matrixValues = com.dsavisualizer.app.ui.visualization.runtime.BacktrackingStepEngine.getInitialSudokuBoard(sudokuGridSize, sudokuPreset);
            matrixK = -1;
            matrixRow = -1;
            matrixCol = -1;
            matrixLabel = "Sudoku " + sudokuGridSize + "x" + sudokuGridSize + " Grid (Preset " + sudokuPreset + ")";
            visualizerView.setMatrixState(matrixValues, matrixK, matrixRow, matrixCol, matrixLabel);
            return;
        }

        if ("bt_knights_tour".equals(techniqueId) || "backtracking_knights_tour".equals(techniqueId)) {
            int n = (knightsTourBoardSize == 8 || knightsTourBoardSize == 6) ? knightsTourBoardSize : 5;
            matrixValues = com.dsavisualizer.app.ui.visualization.runtime.BacktrackingStepEngine.getInitialKnightsTourBoard(n, knightsTourStartPos);
            int startR = (knightsTourStartPos == 2) ? n / 2 : 0;
            int startC = (knightsTourStartPos == 2) ? n / 2 : 0;
            matrixK = 1;
            matrixRow = startR;
            matrixCol = startC;
            matrixLabel = "Knight's Tour " + n + "x" + n + " (" + (knightsTourStartPos == 2 ? "Center" : "Corner") + ")";
            visualizerView.setMatrixState(matrixValues, matrixK, matrixRow, matrixCol, matrixLabel);
            return;
        }

        if ("bt_rat_in_a_maze".equals(techniqueId) || "bt_maze".equals(techniqueId) || "backtracking_maze_solver".equals(techniqueId)) {
            matrixValues = com.dsavisualizer.app.ui.visualization.runtime.BacktrackingStepEngine.getInitialMaze(mazeSize, mazePreset);
            matrixK = -1;
            matrixRow = 0;
            matrixCol = 0;
            matrixLabel = "Rat in a Maze " + mazeSize + "x" + mazeSize + " (Preset " + mazePreset + ")";
            visualizerView.setMatrixState(matrixValues, matrixK, matrixRow, matrixCol, matrixLabel);
            return;
        }

        if ("bt_hamiltonian".equals(techniqueId) || "backtracking_hamiltonian".equals(techniqueId)) {
            workingValues = new int[hamiltonianNodes];
            for (int i = 0; i < hamiltonianNodes; i++) workingValues[i] = i;
            matrixLabel = "Hamiltonian " + (hamiltonianType == 1 ? "Cycle" : "Path") + " (" + hamiltonianNodes + " Vertices)";
            visualizerView.setTraversalState(workingValues, new int[0], -1);
            visualizerView.clearActiveEdge();
            return;
        }

        if ("bt_graph_coloring".equals(techniqueId) || "backtracking_graph_coloring".equals(techniqueId)) {
            int n = com.dsavisualizer.app.ui.visualization.runtime.BacktrackingStepEngine.getColoringGraphNodeCount(graphColoringPreset);
            workingValues = new int[n];
            matrixLabel = "Graph m-Coloring (m=" + graphColoringColors + ", Preset " + graphColoringPreset + ")";
            visualizerView.setArrayState(workingValues, -1, -1, -1);
            visualizerView.clearActiveEdge();
            return;
        }

        if (techniqueId != null && techniqueId.startsWith("dp_")) {
            matrixValues = com.dsavisualizer.app.ui.visualization.runtime.DpStepEngine.getInitialDpMatrix(techniqueId, dpPreset);
            matrixK = -1;
            matrixRow = 0;
            matrixCol = 0;
            matrixLabel = "DP State Table (" + com.dsavisualizer.app.ui.visualization.runtime.DpStepEngine.getPresetDescription(techniqueId, dpPreset) + ")";
            visualizerView.setMatrixState(matrixValues, matrixK, matrixRow, matrixCol, matrixLabel);
            target = dpPreset;
            return;
        }

        if (techniqueId != null && techniqueId.startsWith("string_")) {
            String sample = "ABABDABACDABABCABAB";
            workingValues = new int[sample.length()];
            for (int k = 0; k < sample.length(); k++) workingValues[k] = sample.charAt(k);
            int[] auxValues = new int[] {0, 0, 1, 2, 0, 1, 2, 3, 0};
            visualizerView.setArrayState(workingValues, -1, 0, -1);
            visualizerView.setAuxiliaryArray(auxValues);
            return;
        }

        if (VisualizationTechniqueRegistry.isAdvancedDs(techniqueId)) {
            String input = (advancedDsCustomInput != null && !advancedDsCustomInput.trim().isEmpty())
                    ? advancedDsCustomInput
                    : com.dsavisualizer.app.ui.visualization.runtime.AdvancedDsStepEngine.getDefaultInput(techniqueId, advancedDsPreset);
            if (visualizerView != null) {
                visualizerView.setExpressionInput(input);
            }
            setExpressionInput(input);

            if ("ds_bloom_filter".equals(techniqueId) || "adv_bloom_filter".equals(techniqueId)) {
                // Initialize all 16 bits to 0 in initial preview state
                workingValues = new int[16];
            } else if ("ds_disjoint_set".equals(techniqueId) || "adv_dsu".equals(techniqueId)) {
                workingValues = new int[]{0, 1, 2, 3, 4, 5};
            } else if ("ds_fenwick_tree".equals(techniqueId) || "adv_fenwick_tree".equals(techniqueId)) {
                // Initialize all BIT indices to 0 before steps build the tree
                workingValues = new int[7];
            } else if ("ds_segment_tree".equals(techniqueId) || "adv_segment_tree".equals(techniqueId)
                    || "ds_persistent_segment_tree".equals(techniqueId) || "adv_persistent_st".equals(techniqueId)) {
                int[] parsed = com.dsavisualizer.app.ui.visualization.runtime.AdvancedDsStepEngine.parseBaseArray(input, 4, new int[]{1, 3, 5, 7});
                int a0 = parsed[0];
                int a1 = parsed.length > 1 ? parsed[1] : 3;
                int a2 = parsed.length > 2 ? parsed[2] : 5;
                int a3 = parsed.length > 3 ? parsed[3] : 7;
                // Internal nodes start uncomputed (0), only raw leaf values are set
                workingValues = new int[]{0, 0, 0, a0, a1, a2, a3};
            } else if ("ds_lru_cache".equals(techniqueId) || "adv_lru_cache".equals(techniqueId)) {
                // Cache starts empty before initial put operations
                workingValues = new int[0];
            } else {
                List<Integer> list = new ArrayList<>();
                for (String p : input.split("[,\\s]+")) {
                    try {
                        list.add(Integer.parseInt(p.trim()));
                    } catch (Exception ignored) {}
                }
                if (!list.isEmpty()) {
                    workingValues = new int[list.size()];
                    for (int k = 0; k < list.size(); k++) workingValues[k] = list.get(k);
                } else {
                    workingValues = new int[]{10, 20, 30};
                }
            }
            if (visualizerView != null) {
                visualizerView.setArrayState(workingValues, -1, -1, -1);
            }
            return;
        }

        if (techniqueId != null && techniqueId.startsWith("spec_")) {
            workingValues = new int[] {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
            visualizerView.setArrayState(workingValues, -1, -1, -1);
            return;
        }

        if ("merging".equals(techniqueId) && workingValues.length > 0) {
            int middle = Math.max(1, workingValues.length / 2);
            mergeLeftValues = Arrays.copyOfRange(workingValues, 0, middle);
            mergeRightValues = Arrays.copyOfRange(workingValues, middle, workingValues.length);
            Arrays.sort(mergeLeftValues);
            Arrays.sort(mergeRightValues);
            mergeOutputValues = createPlaceholderArray(workingValues.length);
            mergeWriteCursor = -1;
            visualizerView.setMergeState(mergeLeftValues, mergeRightValues, mergeOutputValues, mergeWriteCursor);
            return;
        }

        if ("merge_sort".equals(techniqueId) && workingValues.length > 0) {
            int previewSize = Math.min(2, workingValues.length);
            mergeLeftValues = Arrays.copyOfRange(workingValues, 0, 1);
            mergeRightValues = Arrays.copyOfRange(workingValues, 1, previewSize);
            mergeOutputValues = createPlaceholderArray(Math.max(mergeLeftValues.length + mergeRightValues.length, 1));
            mergeWriteCursor = -1;
            visualizerView.setMergeState(mergeLeftValues, mergeRightValues, mergeOutputValues, mergeWriteCursor);
            return;
        }

        if ("radix_sort".equals(techniqueId)) {
            radixDivisor = 1;
            visualizerView.clearRadixComplete();
            visualizerView.setRadixState(radixDivisor, -1, 0);
            return;
        }

        if ("max_heap_insertion".equals(techniqueId)
            || "max_heap_deletion".equals(techniqueId)
            || "heapsort".equals(techniqueId)) {
            visualizerView.clearActiveEdge();
        }
    }

    private void refreshCustomSortScene() {
        if ("merging".equals(techniqueId) || "merge_sort".equals(techniqueId)) {
            if (mergeOutputValues.length > 0 || mergeLeftValues.length > 0 || mergeRightValues.length > 0) {
                visualizerView.setMergeState(mergeLeftValues, mergeRightValues, mergeOutputValues, mergeWriteCursor);
            }
            return;
        }

        if ("radix_sort".equals(techniqueId)) {
            int activeBucket = foundIndex >= 0 && foundIndex <= 9 ? foundIndex : -1;
            int count = auxIndex > 0 ? auxIndex : (stepIndex >= 0 ? stepIndex + 1 : -1);
            if (playbackComplete || radixDivisor > radixMaxValue) {
                visualizerView.setRadixComplete(true, workingValues);
            } else {
                visualizerView.clearRadixComplete();
                visualizerView.setRadixState(radixDivisor, activeBucket, count);
            }
            return;
        }

        if ("warshall_algorithm".equals(techniqueId)
            || "floyd_algorithm".equals(techniqueId)
            || "shortest_paths".equals(techniqueId)
            || "graph_floyd_warshall".equals(techniqueId)
            || "graph_johnson".equals(techniqueId)
            || VisualizationTechniqueRegistry.isDynamicProgramming(techniqueId)
            || VisualizationTechniqueRegistry.isBacktracking(techniqueId)) {
            visualizerView.setMatrixState(matrixValues, matrixK, matrixRow, matrixCol, resolveMatrixLabel());
        }
    }

    private int[] createPlaceholderArray(int size) {
        int[] result = new int[size];
        Arrays.fill(result, Integer.MIN_VALUE);
        return result;
    }

    private void pushQuickRange(int low, int high) {
        if (low >= high) {
            return;
        }
        if (quickStackSize >= quickStackLow.length) {
            int newSize = Math.max(4, quickStackSize * 2);
            quickStackLow = Arrays.copyOf(quickStackLow, newSize);
            quickStackHigh = Arrays.copyOf(quickStackHigh, newSize);
        }
        quickStackLow[quickStackSize] = low;
        quickStackHigh[quickStackSize] = high;
        quickStackSize++;
    }

    private boolean prepareNextQuickRange() {
        while (quickStackSize > 0) {
            quickStackSize--;
            int low = quickStackLow[quickStackSize];
            int high = quickStackHigh[quickStackSize];
            if (low >= high) {
                continue;
            }
            quickLow = low;
            quickHigh = high;
            stepIndex = low;
            auxIndex = high;
            foundIndex = low - 1;
            int pivotValue = workingValues[high];
            updatePointerState("j", low, "pivot", high);
            updateArrayVisualization(low, high, foundIndex);
            setHighlightedLines(2, 1);
            updateStatusText(
                "Choose pivot " + pivotValue
                    + " at index " + high
                    + " and partition subarray " + low + " to " + high + "."
            );
            syncScrollToFocus(low);
            return true;
        }
        return false;
    }

    private boolean containsNegativeValues(int[] values) {
        if (values == null) {
            return false;
        }
        for (int value : values) {
            if (value < 0) {
                return true;
            }
        }
        return false;
    }

    private int[] mergeArrays(int[] leftValues, int[] rightValues) {
        int[] merged = new int[leftValues.length + rightValues.length];
        int leftIndex = 0;
        int rightIndex = 0;
        int writeIndex = 0;

        while (leftIndex < leftValues.length && rightIndex < rightValues.length) {
            if (leftValues[leftIndex] <= rightValues[rightIndex]) {
                merged[writeIndex++] = leftValues[leftIndex++];
            } else {
                merged[writeIndex++] = rightValues[rightIndex++];
            }
        }

        while (leftIndex < leftValues.length) {
            merged[writeIndex++] = leftValues[leftIndex++];
        }

        while (rightIndex < rightValues.length) {
            merged[writeIndex++] = rightValues[rightIndex++];
        }
        return merged;
    }

    @Override
    public void buildMaxHeap() {
        for (int i = (workingValues.length / 2) - 1; i >= 0; i--) {
            heapifyDown(i, workingValues.length);
        }
    }

    private void heapifyDown(int index, int length) {
        int current = index;
        while (true) {
            int left = (2 * current) + 1;
            int right = (2 * current) + 2;
            int largest = current;
            if (left < length && workingValues[left] > workingValues[largest]) {
                largest = left;
            }
            if (right < length && workingValues[right] > workingValues[largest]) {
                largest = right;
            }
            if (largest == current) {
                return;
            }
            swap(current, largest);
            current = largest;
        }
    }

    private int[] countingSortByDigit(int[] values, int divisor) {
        return SortVisualizationEngine.countingSortByDigit(values, divisor);
    }

    private int resolveMaxAbsValue(int[] values) {
        return SortVisualizationEngine.resolveMaxAbsValue(values);
    }

    private int safeAdd(int left, int right) {
        return SortVisualizationEngine.safeAdd(left, right);
    }

    @Override
    public String resolveMatrixLabel() {
        if (matrixLabel != null && !matrixLabel.trim().isEmpty()) {
            return matrixLabel;
        }
        return GraphVisualizationController.resolveMatrixLabel(techniqueId);
    }

    private String describeDigit(int divisor) {
        return SortVisualizationEngine.describeDigit(divisor);
    }

    private void performStackOperation(String operation) {
        stopAutomationForManualOperation();

        if ("push".equals(operation)) {
            if (workingValues.length >= structureLimit) {
                if (workingValues.length > 0) {
                    visualizerView.setPointerState("top", workingValues.length - 1, "", -1);
                    syncScrollToFocus(workingValues.length - 1);
                } else {
                    visualizerView.clearPointerState();
                }
                bindCurrentState(getString(R.string.vis_status_stack_overflow, structureLimit));
                scrollToVisualizerInStudyMode();
                return;
            }
            Integer value = readOperationInput();
            if (value == null) {
                value = generateStructureValue();
            }
            workingValues = appendValue(workingValues, value);
            if (operationInput != null) {
                operationInput.setText("");
            }
            visualizerView.setStructureMotion("push", workingValues.length - 1);
            visualizerView.setPointerState("top", workingValues.length - 1, "", -1);
            bindCurrentState("Pushed " + value + " onto the stack");
            syncValuesInput();
            syncScrollToFocus(workingValues.length - 1);
            scrollToVisualizerInStudyMode();
            return;
        }

        if (workingValues.length == 0) {
            if ("pop".equals(operation)) {
                bindCurrentState(getString(R.string.vis_status_stack_underflow));
            } else {
                bindCurrentState(getString(R.string.vis_status_stack_empty));
            }
            return;
        }

        if ("peek".equals(operation)) {
            int topIndex = workingValues.length - 1;
            visualizerView.setStructureMotion("visit", topIndex);
            visualizerView.setPointerState("top", topIndex, "", -1);
            bindCurrentState("Top element is " + workingValues[topIndex]);
            syncScrollToFocus(topIndex);
            scrollToVisualizerInStudyMode();
            return;
        }

        if ("clear".equals(operation)) {
            workingValues = new int[0];
            resetVisualization();
            syncValuesInput();
            bindCurrentState(getString(R.string.vis_status_stack_cleared));
            return;
        }

        int removedIndex = workingValues.length - 1;
        int removedValue = workingValues[removedIndex];
        visualizerView.setStructureMotion("pop", removedIndex);
        visualizerView.setPointerState("top", removedIndex, "", -1);
        bindCurrentState("Popping " + removedValue + " from the stack");
        syncScrollToFocus(removedIndex);
        scrollToVisualizerInStudyMode();
        handler.postDelayed(() -> {
            workingValues = removeLastValue(workingValues);
            if (isAdded()) {
                visualizerView.setStructureMotion("", -1);
                if (workingValues.length > 0) {
                    visualizerView.setPointerState("top", workingValues.length - 1, "", -1);
                } else {
                    visualizerView.clearPointerState();
                }
                bindCurrentState("Popped " + removedValue + " from the stack");
                syncValuesInput();
                syncScrollToFocus(workingValues.length - 1);
                scrollToVisualizerInStudyMode();
            }
        }, Math.min(delayMs, 420L));
    }

    private void performQueueOperation(String operation) {
        stopAutomationForManualOperation();

        if ("enqueue".equals(operation)) {
            if (workingValues.length >= structureLimit) {
                if (workingValues.length > 0) {
                    visualizerView.setPointerState("front", 0, "rear", workingValues.length - 1);
                    syncScrollToFocus(workingValues.length - 1);
                } else {
                    visualizerView.clearPointerState();
                }
                bindCurrentState(getString(R.string.vis_status_queue_overflow, structureLimit));
                scrollToVisualizerInStudyMode();
                return;
            }
            Integer value = readOperationInput();
            if (value == null) {
                value = generateStructureValue();
            }
            workingValues = appendValue(workingValues, value);
            if (operationInput != null) {
                operationInput.setText("");
            }
            visualizerView.setStructureMotion("enqueue_right", workingValues.length - 1);
            visualizerView.setPointerState("front", 0, "rear", workingValues.length - 1);
            bindCurrentState("Enqueued " + value + " into the queue");
            syncValuesInput();
            syncScrollToFocus(workingValues.length - 1);
            scrollToVisualizerInStudyMode();
            return;
        }

        if (workingValues.length == 0) {
            if ("dequeue".equals(operation)) {
                bindCurrentState(getString(R.string.vis_status_queue_underflow));
            } else {
                bindCurrentState(getString(R.string.vis_status_queue_empty));
            }
            return;
        }

        if ("front".equals(operation)) {
            visualizerView.setStructureMotion("visit", 0);
            visualizerView.setPointerState("front", 0, "rear", workingValues.length - 1);
            bindCurrentState("Front element is " + workingValues[0]);
            syncScrollToFocus(0);
            scrollToVisualizerInStudyMode();
            return;
        }

        if ("rear".equals(operation)) {
            int rearIndex = workingValues.length - 1;
            visualizerView.setStructureMotion("visit", rearIndex);
            visualizerView.setPointerState("front", 0, "rear", rearIndex);
            bindCurrentState("Rear element is " + workingValues[rearIndex]);
            syncScrollToFocus(rearIndex);
            scrollToVisualizerInStudyMode();
            return;
        }

        int removedValue = workingValues[0];
        visualizerView.setStructureMotion("dequeue_left", 0);
        visualizerView.setPointerState("front", 0, "rear", workingValues.length - 1);
        bindCurrentState("Dequeuing " + removedValue + " from the queue");
        syncScrollToFocus(0);
        scrollToVisualizerInStudyMode();
        handler.postDelayed(() -> {
            workingValues = removeFirstValue(workingValues);
            if (isAdded()) {
                visualizerView.setStructureMotion("", -1);
                if (workingValues.length > 0) {
                    visualizerView.setPointerState("front", 0, "rear", workingValues.length - 1);
                } else {
                    visualizerView.clearPointerState();
                }
                bindCurrentState("Dequeued " + removedValue + " from the queue");
                syncValuesInput();
                syncScrollToFocus(0);
                scrollToVisualizerInStudyMode();
            }
        }, Math.min(delayMs, 420L));
    }

    private void performDequeOperation(String operation) {
        stopAutomationForManualOperation();

        if ("front_in".equals(operation) || "rear_in".equals(operation)) {
            if (workingValues.length >= structureLimit) {
                if (workingValues.length > 0) {
                    visualizerView.setPointerState("front", 0, "rear", workingValues.length - 1);
                    syncScrollToFocus("front_in".equals(operation) ? 0 : workingValues.length - 1);
                } else {
                    visualizerView.clearPointerState();
                }
                bindCurrentState(getString(R.string.vis_status_deque_overflow, structureLimit));
                scrollToVisualizerInStudyMode();
                return;
            }
            Integer value = readOperationInput();
            if (value == null) {
                value = generateStructureValue();
            }
            workingValues = "front_in".equals(operation) ? prependValue(workingValues, value) : appendValue(workingValues, value);
            if (operationInput != null) {
                operationInput.setText("");
            }
            int focusIndex = "front_in".equals(operation) ? 0 : workingValues.length - 1;
            visualizerView.setStructureMotion("front_in".equals(operation) ? "front_in" : "rear_in", focusIndex);
            visualizerView.setPointerState("front", 0, "rear", workingValues.length - 1);
            bindCurrentState(("front_in".equals(operation) ? "Inserted " : "Added ") + value + " into the deque");
            syncValuesInput();
            syncScrollToFocus(focusIndex);
            scrollToVisualizerInStudyMode();
            return;
        }

        if (workingValues.length == 0) {
            if ("front_out".equals(operation) || "rear_out".equals(operation)) {
                bindCurrentState(getString(R.string.vis_status_deque_underflow));
            } else {
                bindCurrentState(getString(R.string.vis_status_deque_empty));
            }
            return;
        }

        boolean frontOut = "front_out".equals(operation);
        int focusIndex = frontOut ? 0 : workingValues.length - 1;
        int removedValue = workingValues[focusIndex];
        visualizerView.setStructureMotion(frontOut ? "front_out" : "rear_out", focusIndex);
        visualizerView.setPointerState("front", 0, "rear", workingValues.length - 1);
        bindCurrentState((frontOut ? "Removing front " : "Removing rear ") + removedValue);
        syncScrollToFocus(focusIndex);
        scrollToVisualizerInStudyMode();
        handler.postDelayed(() -> {
            workingValues = frontOut ? removeFirstValue(workingValues) : removeLastValue(workingValues);
            if (isAdded()) {
                visualizerView.setStructureMotion("", -1);
                if (workingValues.length > 0) {
                    visualizerView.setPointerState("front", 0, "rear", workingValues.length - 1);
                } else {
                    visualizerView.clearPointerState();
                }
                bindCurrentState((frontOut ? "Removed front " : "Removed rear ") + removedValue);
                syncValuesInput();
                syncScrollToFocus(0);
                scrollToVisualizerInStudyMode();
            }
        }, Math.min(delayMs, 420L));
    }

    private void stopAutomationForManualOperation() {
        handler.removeCallbacksAndMessages(null);
        running = false;
        paused = false;
        syncPlayButtonVisual(false);
        stepIndex = -1;
        auxIndex = -1;
        foundIndex = -1;
        traversalOrder = new int[0];
    }

    private Integer readOperationInput() {
        if (operationInput == null || operationInput.getVisibility() != View.VISIBLE) {
            return null;
        }
        String raw = operationInput.getText() == null ? "" : operationInput.getText().toString().trim();
        if (raw.isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException error) {
            return null;
        }
    }

    private int generateStructureValue() {
        return 10 + random.nextInt(90);
    }

    @Nullable
    private Integer parseStructureLimit(@Nullable String rawLimit) {
        if (rawLimit == null || rawLimit.trim().isEmpty()) {
            return null;
        }
        try {
            int parsed = Integer.parseInt(rawLimit.trim());
            return parsed > 0 ? parsed : null;
        } catch (NumberFormatException error) {
            return null;
        }
    }

    private int[] appendValue(int[] source, int value) {
        int[] result = Arrays.copyOf(source, source.length + 1);
        result[result.length - 1] = value;
        return result;
    }

    private int[] prependValue(int[] source, int value) {
        int[] result = new int[source.length + 1];
        result[0] = value;
        System.arraycopy(source, 0, result, 1, source.length);
        return result;
    }

    private int[] removeLastValue(int[] source) {
        return Arrays.copyOf(source, Math.max(0, source.length - 1));
    }

    private int[] removeFirstValue(int[] source) {
        if (source.length <= 1) {
            return new int[0];
        }
        int[] result = new int[source.length - 1];
        System.arraycopy(source, 1, result, 0, result.length);
        return result;
    }

    private void syncValuesInput() {
        if (valuesInput == null) {
            return;
        }
        valuesInput.setText(isExpressionStackTechnique() ? expressionInput : join(workingValues));
    }

    private void syncStructureLimitInput() {
        if (!usesStructureLimit()) {
            return;
        }
        if (compactLimitInput != null) {
            compactLimitInput.setText(String.valueOf(structureLimit));
        }
        if (targetInput != null) {
            targetInput.setText(String.valueOf(structureLimit));
        }
    }

    private void styleStructureLimitField(boolean compact) {
        if (targetInputLayout == null || targetInput == null || valuesInputLayout == null) {
            return;
        }
        ViewGroup.LayoutParams rawValuesParams = valuesInputLayout.getLayoutParams();
        if (rawValuesParams instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams valuesParams = (LinearLayout.LayoutParams) rawValuesParams;
            if (compact) {
                valuesParams.width = 0;
                valuesParams.weight = 1f;
            } else {
                valuesParams.width = 0;
                valuesParams.weight = 1f;
            }
            valuesInputLayout.setLayoutParams(valuesParams);
        }
        if (compactLimitGroup != null) {
            compactLimitGroup.setVisibility(compact ? View.VISIBLE : View.GONE);
        }
        if (compactLimitInputLayout != null) {
            compactLimitInputLayout.setHintEnabled(false);
        }
        if (compactLimitInput != null) {
            compactLimitInput.setSingleLine(true);
            compactLimitInput.setMaxLines(1);
            compactLimitInput.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, 13f);
        }
        targetInputLayout.setVisibility(compact ? View.GONE : targetInputLayout.getVisibility());
    }

    private boolean usesStructureLimit() {
        return isStackTechnique() || isQueueTechnique() || "deque".equals(techniqueId);
    }

    private boolean shouldShowTargetContext() {
        return targetInputLayout != null
            && targetInputLayout.getVisibility() == View.VISIBLE
            && !usesStructureLimit();
    }

    private int resolveDefaultStructureLimit(int currentSize) {
        return Math.max(1, Math.max(currentSize, 6));
    }

    private void ensureStructureLimitFitsValues() {
        if (!usesStructureLimit()) {
            return;
        }
        structureLimit = Math.max(1, Math.max(structureLimit, workingValues.length));
    }

    private boolean isExpressionStackTechnique() {
        return "postfix_evaluation".equals(techniqueId)
            || "infix_to_postfix".equals(techniqueId)
            || "infix_to_prefix".equals(techniqueId);
    }

    private String resolveExpressionInputHint() {
        if ("postfix_evaluation".equals(techniqueId)) {
            return "Postfix expression (ex: 2 3 4 * +)";
        }
        if ("infix_to_prefix".equals(techniqueId)) {
            return "Infix expression for prefix conversion";
        }
        return "Infix expression for postfix conversion";
    }

    private String resolveExpressionInputHelperText() {
        if ("postfix_evaluation".equals(techniqueId)) {
            return "Use spaces for multi-digit values. Example: 2 3 4 + *";
        }
        return "Use letters/numbers, operators + - * / ^, and parentheses. Example: a+b*(c-d)";
    }

    private String resolveDefaultExpressionInput() {
        if ("postfix_evaluation".equals(techniqueId)) {
            return "2 3 4 * +";
        }
        if ("infix_to_prefix".equals(techniqueId)) {
            return "(e-d^c)*b+a";
        }
        return "a+b*(c^d-e)";
    }

    private String resolveRandomExpressionInput() {
        String[] infixSamples = {"a+b*c", "(a+b)*(c-d)", "a+(b*c)-d", "(x+y)*(z-w)+q"};
        String[] postfixSamples = {"2 3 +", "5 1 2 + 4 * + 3 -", "8 2 / 3 +", "9 4 2 * + 7 -"};
        if ("postfix_evaluation".equals(techniqueId)) {
            return postfixSamples[random.nextInt(postfixSamples.length)];
        }
        return infixSamples[random.nextInt(infixSamples.length)];
    }

    private String validateExpressionInput(String rawExpression) {
        if (rawExpression == null || rawExpression.trim().isEmpty()) {
            return "Enter an expression first.";
        }
        if ("postfix_evaluation".equals(techniqueId)) {
            return validatePostfixInput(rawExpression.trim());
        }
        return validateInfixInput(rawExpression.trim());
    }

    private String validatePostfixInput(String expression) {
        String[] tokens = expression.contains(" ")
            ? expression.split("\\s+")
            : expression.split("");
        int depth = 0;
        int meaningfulTokens = 0;
        for (String token : tokens) {
            if (token == null || token.trim().isEmpty() || ")".equals(token)) {
                continue;
            }
            meaningfulTokens++;
            if (token.matches("-?\\d+")) {
                depth++;
            } else if (token.matches("[+\\-*/^]")) {
                if (depth < 2) {
                    return "Postfix needs two operands before each operator.";
                }
                depth--;
            } else {
                return "Use numbers and operators only, like 2 3 4 + *.";
            }
        }
        if (meaningfulTokens == 0) {
            return "Enter a postfix expression first.";
        }
        if (depth != 1) {
            return "Postfix should finish with exactly one value on the stack.";
        }
        return null;
    }

    private String validateInfixInput(String expression) {
        int balance = 0;
        boolean sawOperand = false;
        boolean lastWasOperator = true;
        for (int i = 0; i < expression.length(); i++) {
            char token = expression.charAt(i);
            if (Character.isWhitespace(token)) {
                continue;
            }
            if (Character.isLetterOrDigit(token)) {
                sawOperand = true;
                lastWasOperator = false;
            } else if (token == '(') {
                balance++;
                lastWasOperator = true;
            } else if (token == ')') {
                if (balance == 0) {
                    return "Parentheses are not balanced.";
                }
                balance--;
                lastWasOperator = false;
            } else if ("+-*/^".indexOf(token) >= 0) {
                if (lastWasOperator) {
                    return "Place an operand before each operator.";
                }
                lastWasOperator = true;
            } else {
                return "Use operands, + - * / ^, and parentheses only.";
            }
        }
        if (!sawOperand) {
            return "Enter an infix expression first.";
        }
        if (balance != 0) {
            return "Parentheses are not balanced.";
        }
        if (lastWasOperator) {
            return "Expression cannot end with an operator.";
        }
        return null;
    }

    private void syncScrollToPattern(int index) {
        if (visualizerScrollView == null || visualizerView == null) {
            return;
        }
        if (isLandscapeLayout()) {
            visualizerScrollView.scrollTo(0, 0);
            return;
        }
        visualizerScrollView.post(() -> {
            int focusX = visualizerView.getPatternFocusScrollX(index);
            int maxScroll = Math.max(0, visualizerView.getMeasuredWidth() - visualizerScrollView.getWidth());
            visualizerScrollView.smoothScrollTo(Math.max(0, Math.min(focusX, maxScroll)), 0);
        });
    }

    @Override
    public void syncScrollToFocus(int index) {
        if (visualizerScrollView == null || visualizerView == null) {
            return;
        }
        if (isLandscapeLayout()) {
            visualizerScrollView.scrollTo(0, 0);
            return;
        }
        visualizerScrollView.post(() -> {
            int focusX = index >= 0
                ? visualizerView.getFocusScrollX(index)
                : Math.max(0, (visualizerView.getMeasuredWidth() - visualizerScrollView.getWidth()) / 2);
            int maxScroll = Math.max(0, visualizerView.getMeasuredWidth() - visualizerScrollView.getWidth());
            visualizerScrollView.smoothScrollTo(Math.max(0, Math.min(focusX, maxScroll)), 0);
        });
        if (contentScrollView != null && timeline.size() > 4 && !isStructureMode() && !isLandscapeLayout()) {
            contentScrollView.post(() -> {
                View targetView = visualizerScrollView != null ? visualizerScrollView : visualizerView;
                if (targetView == null) {
                    return;
                }
                int visualTop = Math.max(0, targetView.getTop() - dp(24));
                contentScrollView.smoothScrollTo(0, visualTop);
            });
        }
    }

    private int dp(int value) {
        android.content.Context context = getContext();
        float density = context != null
            ? context.getResources().getDisplayMetrics().density
            : android.content.res.Resources.getSystem().getDisplayMetrics().density;
        return Math.round(value * density);
    }

    private static class Snapshot {
        String techniqueId = "";
        String expressionInput = "";
        int[] workingValues = new int[0];
        int target;
        int stepIndex;
        int auxIndex;
        int foundIndex;
        int binaryPhase;
        int binaryMidIndex;
        int[] traversalOrder = new int[0];
        int[] mergeLeftValues = new int[0];
        int[] mergeRightValues = new int[0];
        int[] mergeOutputValues = new int[0];
        int[] mergeWorkingValues = new int[0];
        int mergeLeftCursor;
        int mergeRightCursor;
        int mergeWriteCursor;
        int mergeRunSize;
        int mergePassStart;
        int radixDivisor;
        int radixMaxValue;
        boolean radixCompleted;
        int quickLow;
        int quickHigh;
        int[] quickStackLow = new int[0];
        int[] quickStackHigh = new int[0];
        int quickStackSize;
        int[][] matrixValues = new int[0][0];
        int matrixK;
        int matrixRow;
        int matrixCol;
        String matrixLabel = "";
        int heapSize;
        int heapModePhase;
        int heapSortPhase;
        int heapSortHeapifyIndex;
        String patternText = "";
        String patternQuery = "";
        int patternWindowStart;
        int patternMatchIndex;
        boolean patternFound;
        int[] unionParents = new int[0];
        int[] unionPath = new int[0];
        int unionPathStep;
        int unionFocusNode;
        int unionRootNode;
        int[] dijkstraDistances = new int[0];
        boolean[] dijkstraSettled = new boolean[0];
        int dijkstraCurrentNode;
        int dijkstraPhase;
        int dijkstraNeighborIndex;
        int[][] dijkstraWeights = new int[0][0];
        int[] huffmanQueueWeights = new int[0];
        int huffmanMergedWeight;
        int huffmanMergedLeft;
        int huffmanMergedRight;
        boolean running;
        boolean paused;
        String statusText = "";
        String explanationText = "";
        String primaryPointerLabel = "";
        int primaryPointerIndex;
        String secondaryPointerLabel = "";
        int secondaryPointerIndex;
        int activeEdgeFrom;
        int activeEdgeTo;
        String structureOperation = "";
        int movingStructureIndex;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getActivity() instanceof TechniqueDetailActivity) {
            ((TechniqueDetailActivity) getActivity()).setStudyModeEnabled(false);
        }
        stopFastStepping();
        if (playbackController != null) {
            playbackController.release();
        }
        handler.removeCallbacksAndMessages(null);
    }
}


