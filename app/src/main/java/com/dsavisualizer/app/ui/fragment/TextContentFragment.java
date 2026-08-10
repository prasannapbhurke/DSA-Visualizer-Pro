package com.dsavisualizer.app.ui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dsavisualizer.app.R;
import com.dsavisualizer.app.data.AppPreferences;
import com.dsavisualizer.app.data.AppRepository;
import com.dsavisualizer.app.model.TechniqueContent;
import com.dsavisualizer.app.ui.HapticFeedbackHelper;
import com.dsavisualizer.app.ui.content.TechniqueTextContentResolver;
import com.dsavisualizer.app.ui.viewmodel.TechniqueSessionViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextContentFragment extends Fragment {

    protected static final String ARG_TECHNIQUE_ID = "technique_id";
    protected static final String ARG_CONTENT_TYPE = "content_type";
    protected static final String ARG_SHOW_COMMENTS = "show_comments";
    protected static final String CONTENT_TYPE_ALGORITHM = "algorithm";
    protected static final String CONTENT_TYPE_CODE = "code";

    private float textSizeSp = 16f;
    private static final String TIP_FIRST_CODE_DEFAULT_C = "first_code_default_c";
    private TextView contentView;
    private TextView sectionLabelView;
    private TextView topicNameView;
    private TextView modeHintView;
    private TextView zoomBadgeView;
    private TextView codeThemeLabelView;
    private HorizontalScrollView horizontalScrollView;
    private MaterialButton zoomInButton;
    private MaterialButton zoomOutButton;
    private MaterialButton copyButton;
    private MaterialButton shareButton;
    private MaterialButtonToggleGroup styleToggle;
    private MaterialButtonToggleGroup languageToggle;
    private MaterialButtonToggleGroup commentToggle;
    private MaterialButtonToggleGroup codeThemeToggle;
    private TechniqueSessionViewModel sessionViewModel;
    private TechniqueTextContentResolver contentResolver;
    private String currentRawText = "";
    private boolean showComments = true;
    private View rootContentView;
    private boolean lightTheme;
    private TechniqueContent currentTechniqueContent;
    private AppPreferences appPreferences;

    protected static Bundle createArgs(String techniqueId, String contentType) {
        return createArgs(techniqueId, contentType, true);
    }

    protected static Bundle createArgs(String techniqueId, String contentType, boolean showComments) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TECHNIQUE_ID, techniqueId);
        bundle.putString(ARG_CONTENT_TYPE, contentType);
        bundle.putBoolean(ARG_SHOW_COMMENTS, showComments);
        return bundle;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text_content, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootContentView = view;

        styleToggle = view.findViewById(R.id.content_style_toggle);
        languageToggle = view.findViewById(R.id.content_language_toggle);
        zoomInButton = view.findViewById(R.id.content_zoom_in);
        zoomOutButton = view.findViewById(R.id.content_zoom_out);
        copyButton = view.findViewById(R.id.content_copy);
        shareButton = view.findViewById(R.id.content_share);
        commentToggle = view.findViewById(R.id.content_comment_toggle);
        contentView = view.findViewById(R.id.content_text);
        sectionLabelView = view.findViewById(R.id.content_section_label);
        topicNameView = view.findViewById(R.id.content_topic_name);
        modeHintView = view.findViewById(R.id.content_mode_hint);
        zoomBadgeView = view.findViewById(R.id.content_zoom_badge);
        codeThemeLabelView = view.findViewById(R.id.content_code_theme_label);
        codeThemeToggle = view.findViewById(R.id.content_code_theme_toggle);
        horizontalScrollView = view.findViewById(R.id.content_horizontal_scroll);
        sessionViewModel = new ViewModelProvider(requireActivity()).get(TechniqueSessionViewModel.class);
        contentResolver = new TechniqueTextContentResolver();
        lightTheme = isLightTheme();
        Bundle args = getArguments();
        if (args != null) {
            showComments = args.getBoolean(ARG_SHOW_COMMENTS, true);
            String techniqueId = args.getString(ARG_TECHNIQUE_ID, "linear_search");
            currentTechniqueContent = AppRepository.getContent(techniqueId);
            if (topicNameView != null) {
                topicNameView.setText(currentTechniqueContent == null ? prettifyTechniqueId(techniqueId) : currentTechniqueContent.title);
            }
        }
        applyThemeStyling();
        applyCompactPhoneTweaks();

        appPreferences = new AppPreferences(requireContext());
        boolean firstCodeDefault = isCodeTab() && appPreferences.shouldShowTip(TIP_FIRST_CODE_DEFAULT_C);
        languageToggle.check(firstCodeDefault ? R.id.content_language_c : R.id.content_language_java);
        languageToggle.setVisibility(isCodeTab() ? View.VISIBLE : View.GONE);
        commentToggle.setVisibility(isCodeTab() ? View.VISIBLE : View.GONE);
        if (codeThemeLabelView != null) {
            codeThemeLabelView.setVisibility(isCodeTab() ? View.VISIBLE : View.GONE);
        }
        if (codeThemeToggle != null) {
            codeThemeToggle.setVisibility(isCodeTab() ? View.VISIBLE : View.GONE);
            codeThemeToggle.check(resolveCodeThemeButtonId(appPreferences.getCodeReaderTheme()));
        }
        configureInitialState();
        if (isCodeTab()) {
            if (firstCodeDefault) {
                showComments = true;
                appPreferences.markTipSeen(TIP_FIRST_CODE_DEFAULT_C);
            }
            commentToggle.check(showComments ? R.id.content_comments_on : R.id.content_comments_off);
            commentToggle.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
                if (!isChecked) {
                    return;
                }
                if (!canOpenContent()) {
                    return;
                }
                HapticFeedbackHelper.performSelection(group);
                showComments = checkedId == R.id.content_comments_on;
                bindContent(getSelectedStyle());
            });
        }

        styleToggle.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (!isChecked) {
                if (isAlgorithmTab() && group.getCheckedButtonId() == View.NO_ID) {
                    showStylePlaceholder(true);
                } else if (isCodeTab() && group.getCheckedButtonId() == View.NO_ID) {
                    showStylePlaceholder(false);
                }
                return;
            }
            HapticFeedbackHelper.performSelection(group);
            animateModeSelection(checkedId);
            bindContent(getSelectedStyle());
        });
        languageToggle.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked && isCodeTab() && canOpenContent()) {
                HapticFeedbackHelper.performSelection(group);
                bindContent(getSelectedStyle());
            }
        });
        if (codeThemeToggle != null) {
            codeThemeToggle.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
                if (!isChecked || !isCodeTab() || appPreferences == null) {
                    return;
                }
                HapticFeedbackHelper.performSelection(group);
                appPreferences.setCodeReaderTheme(resolveCodeThemeFromButtonId(checkedId));
                applyThemeStyling();
                if (canOpenContent()) {
                    applyHighlightedContent(sessionViewModel.getHighlightedCodeLine().getValue() == null ? -1 : sessionViewModel.getHighlightedCodeLine().getValue());
                }
            });
        }

        zoomInButton.setOnClickListener(v -> adjustTextSize(1f));
        zoomOutButton.setOnClickListener(v -> adjustTextSize(-1f));
        copyButton.setOnClickListener(v -> copyText());
        shareButton.setOnClickListener(v -> shareText());

        animateTabEntrance();

        sessionViewModel.getHighlightedCodeLine().observe(getViewLifecycleOwner(), line -> {
            if (isCodeTab()) {
                applyHighlightedContent(line == null ? -1 : line);
            }
        });

        sessionViewModel.getHighlightedAlgorithmLine().observe(getViewLifecycleOwner(), line -> {
            if (isAlgorithmTab()) {
                applyHighlightedContent(line == null ? -1 : line);
            }
        });
    }

    private void bindContent(String style) {
        Bundle args = getArguments();
        if (args == null) {
            return;
        }
        String type = args.getString(ARG_CONTENT_TYPE, CONTENT_TYPE_ALGORITHM);
        String techniqueId = args.getString(ARG_TECHNIQUE_ID, "linear_search");

        if (CONTENT_TYPE_CODE.equals(type)) {
            String language = getSelectedLanguage();
            currentRawText = contentResolver.resolve(techniqueId, type, style, language, showComments);
            applyHighlightedContent(sessionViewModel.getHighlightedCodeLine().getValue() == null ? -1 : sessionViewModel.getHighlightedCodeLine().getValue());
        } else {
            currentRawText = contentResolver.resolve(techniqueId, type, style, "Java", showComments);
            applyHighlightedContent(sessionViewModel.getHighlightedAlgorithmLine().getValue() == null ? -1 : sessionViewModel.getHighlightedAlgorithmLine().getValue());
        }
        animateContentReveal();
    }

    private void adjustTextSize(float delta) {
        textSizeSp = Math.max(13f, Math.min(24f, textSizeSp + delta));
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
        if (isCodeTab() && appPreferences != null) {
            appPreferences.setCodeReaderTextSizeSp(textSizeSp);
        }
        updateZoomBadge();
    }

    private void copyText() {
        ClipboardManager clipboardManager = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager != null) {
            clipboardManager.setPrimaryClip(ClipData.newPlainText("content", contentView.getText()));
            HapticFeedbackHelper.performConfirm(contentView);
            Toast.makeText(requireContext(), "Copied", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareText() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "DSA Visualizer Pro Code");
        intent.putExtra(Intent.EXTRA_TEXT, currentRawText);
        startActivity(Intent.createChooser(intent, "Share code"));
    }

    private void applyHighlightedContent(int lineIndex) {
        SpannableString spannable = new SpannableString(currentRawText);
        if (isCodeTab()) {
            applySyntaxHighlighting(spannable);
        }
        applyLineHighlight(spannable, lineIndex);
        contentView.setText(spannable);
    }

    private void applySyntaxHighlighting(SpannableString spannable) {
        String text = spannable.toString();
        CodeThemePalette palette = resolveCodeThemePalette();
        applyPattern(spannable, text, "\"([^\"\\\\]|\\\\.)*\"", palette.stringColor);
        applyPattern(spannable, text, "//.*|#.*|/\\*([\\s\\S]*?)\\*/", palette.commentColor);
        applyPattern(spannable, text, "\\b(public|private|class|return|if|else|while|for|int|void|def|import|from|struct|typedef|static|new|boolean|true|false)\\b", palette.keywordColor);
        applyPattern(spannable, text, "\\b(self|this|null|None)\\b", palette.literalColor);
    }

    private void applyPattern(SpannableString spannable, String text, String regex, int color) {
        Matcher matcher = Pattern.compile(regex, Pattern.MULTILINE).matcher(text);
        while (matcher.find()) {
            spannable.setSpan(new ForegroundColorSpan(color), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    private void applyLineHighlight(SpannableString spannable, int lineIndex) {
        if (lineIndex < 0) {
            return;
        }

        String[] lines = currentRawText.split("\n", -1);
        if (lineIndex >= lines.length) {
            return;
        }

        int start = 0;
        for (int i = 0; i < lineIndex; i++) {
            start += lines[i].length() + 1;
        }
        int end = start + lines[lineIndex].length();
        int highlightColor = isCodeTab() ? resolveCodeThemePalette().lineHighlightColor : android.graphics.Color.parseColor("#1E3A5F");
        spannable.setSpan(
            new BackgroundColorSpan(highlightColor),
            start,
            Math.max(start, end),
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
    }

    private boolean isCodeTab() {
        Bundle args = getArguments();
        return args != null && CONTENT_TYPE_CODE.equals(args.getString(ARG_CONTENT_TYPE, CONTENT_TYPE_ALGORITHM));
    }

    private boolean isAlgorithmTab() {
        Bundle args = getArguments();
        return args != null && CONTENT_TYPE_ALGORITHM.equals(args.getString(ARG_CONTENT_TYPE, CONTENT_TYPE_ALGORITHM));
    }

    private void configureInitialState() {
        if (isAlgorithmTab()) {
            styleToggle.setSelectionRequired(false);
            int defaultStyleId = resolveDefaultStyleButtonId();
            styleToggle.check(defaultStyleId);
            textSizeSp = resolveDefaultAlgorithmTextSizeSp();
            configureAlgorithmViewport(textSizeSp);
            bindContent(getSelectedStyle());
            updateModeButtons(defaultStyleId);
            modeHintView.setText(R.string.content_mode_hint_algorithm);
            return;
        }

        if (isCodeTab()) {
            styleToggle.setSelectionRequired(false);
            styleToggle.clearChecked();
            textSizeSp = appPreferences == null ? 16f : appPreferences.getCodeReaderTextSizeSp();
            configureCodeViewport(textSizeSp);
            showStylePlaceholder(false);
            updateModeButtons(View.NO_ID);
            modeHintView.setText(R.string.content_mode_hint_code);
            return;
        }

        styleToggle.setSelectionRequired(true);
        configureCodeViewport(textSizeSp);
    }

    private void showStylePlaceholder(boolean algorithmTab) {
        currentRawText = "";
        contentView.setText(algorithmTab ? R.string.content_select_style_prompt : R.string.content_select_code_prompt);
        contentView.setTextColor(resolveThemeColor(android.R.attr.textColorHint, R.color.text_muted));
        if (algorithmTab) {
            configureAlgorithmViewport(resolvePlaceholderAlgorithmTextSizeSp());
        } else {
            configureCodeViewport(textSizeSp);
        }
        horizontalScrollView.scrollTo(0, 0);
        modeHintView.setText(algorithmTab ? R.string.content_mode_hint_algorithm : R.string.content_mode_hint_code);
    }

    private void animateContentReveal() {
        int baseTextColor = isCodeTab() ? resolveCodeThemePalette().baseTextColor : resolveThemeColor(android.R.attr.textColorPrimary, R.color.text_primary_bright);
        contentView.setTextColor(baseTextColor);
        if (isCodeTab()) {
            configureCodeViewport(textSizeSp);
        } else {
            configureAlgorithmViewport(textSizeSp);
        }
        horizontalScrollView.scrollTo(0, 0);
        contentView.setAlpha(0f);
        if (isCodeTab()) {
            contentView.setTranslationX(dpToPx(18));
            contentView.setTranslationY(0f);
            contentView.animate()
                .alpha(1f)
                .translationX(0f)
                .setDuration(260L)
                .start();
        } else {
            contentView.setTranslationY(dpToPx(10));
            contentView.setTranslationX(0f);
            contentView.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(220L)
                .start();
        }
        updateModeButtons(styleToggle.getCheckedButtonId());
    }

    private void configureCodeViewport(float sizeSp) {
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeSp);
        contentView.setTypeface(Typeface.MONOSPACE);
        contentView.setHorizontallyScrolling(true);
        contentView.setSingleLine(false);
        contentView.setMaxLines(Integer.MAX_VALUE);
        contentView.setIncludeFontPadding(false);
        contentView.setBreakStrategy(Layout.BREAK_STRATEGY_SIMPLE);
        contentView.setHyphenationFrequency(Layout.HYPHENATION_FREQUENCY_NONE);
        contentView.setLineSpacing(dpToPx(3), 1f);
        contentView.setPaddingRelative(dpToPx(16), dpToPx(14), dpToPx(18), dpToPx(18));
        ViewGroup.LayoutParams params = contentView.getLayoutParams();
        if (params != null) {
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            contentView.setLayoutParams(params);
        }
        horizontalScrollView.setHorizontalScrollBarEnabled(true);
        horizontalScrollView.setScrollbarFadingEnabled(false);
        horizontalScrollView.setSmoothScrollingEnabled(true);
        horizontalScrollView.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
        updateZoomBadge();
        applyCodeSurfaceTheme();
    }

    private void configureAlgorithmViewport(float sizeSp) {
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeSp);
        contentView.setTypeface(Typeface.MONOSPACE);
        contentView.setHorizontallyScrolling(false);
        contentView.setSingleLine(false);
        contentView.setMaxLines(Integer.MAX_VALUE);
        contentView.setIncludeFontPadding(false);
        contentView.setBreakStrategy(Layout.BREAK_STRATEGY_HIGH_QUALITY);
        contentView.setHyphenationFrequency(Layout.HYPHENATION_FREQUENCY_NONE);
        contentView.setLineSpacing(dpToPx(resolveAlgorithmLineSpacingDp()), 1f);
        contentView.setPaddingRelative(
            dpToPx(resolveAlgorithmHorizontalPaddingDp()),
            dpToPx(resolveAlgorithmVerticalPaddingDp()),
            dpToPx(resolveAlgorithmHorizontalPaddingDp()),
            dpToPx(resolveAlgorithmBottomPaddingDp())
        );
        ViewGroup.LayoutParams params = contentView.getLayoutParams();
        if (params != null) {
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            contentView.setLayoutParams(params);
        }
        horizontalScrollView.setHorizontalScrollBarEnabled(false);
        horizontalScrollView.setScrollbarFadingEnabled(true);
        horizontalScrollView.setSmoothScrollingEnabled(false);
        horizontalScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    private float resolveDefaultAlgorithmTextSizeSp() {
        Configuration configuration = requireContext().getResources().getConfiguration();
        int smallestWidth = configuration.smallestScreenWidthDp;
        int screenWidth = configuration.screenWidthDp;
        if (smallestWidth >= 600) {
            return 15f;
        }
        if (screenWidth <= 360) {
            return 13f;
        }
        return 14f;
    }

    private float resolvePlaceholderAlgorithmTextSizeSp() {
        Configuration configuration = requireContext().getResources().getConfiguration();
        if (configuration.smallestScreenWidthDp >= 600) {
            return 15.5f;
        }
        if (configuration.screenWidthDp <= 360) {
            return 13.5f;
        }
        return 15f;
    }

    private int resolveAlgorithmHorizontalPaddingDp() {
        Configuration configuration = requireContext().getResources().getConfiguration();
        if (configuration.smallestScreenWidthDp >= 600) {
            return 18;
        }
        if (configuration.screenWidthDp <= 360) {
            return 10;
        }
        return 14;
    }

    private int resolveAlgorithmVerticalPaddingDp() {
        Configuration configuration = requireContext().getResources().getConfiguration();
        if (configuration.smallestScreenWidthDp >= 600) {
            return 16;
        }
        if (configuration.screenWidthDp <= 360) {
            return 12;
        }
        return 14;
    }

    private int resolveAlgorithmBottomPaddingDp() {
        Configuration configuration = requireContext().getResources().getConfiguration();
        if (configuration.smallestScreenWidthDp >= 600) {
            return 20;
        }
        if (configuration.screenWidthDp <= 360) {
            return 14;
        }
        return 18;
    }

    private int resolveAlgorithmLineSpacingDp() {
        Configuration configuration = requireContext().getResources().getConfiguration();
        if (configuration.smallestScreenWidthDp >= 600) {
            return 5;
        }
        if (configuration.screenWidthDp <= 360) {
            return 3;
        }
        return 4;
    }

    private void updateZoomBadge() {
        if (zoomBadgeView == null) {
            return;
        }
        zoomBadgeView.setText(String.format(java.util.Locale.US, "%.0fsp", textSizeSp));
    }

    private int dpToPx(int dp) {
        return Math.round(dp * requireContext().getResources().getDisplayMetrics().density);
    }

    private void applyCompactPhoneTweaks() {
        if (!isCompactPhone()) {
            return;
        }
        updateToggleGroupButtons(styleToggle, 12f, 44, false);
        updateToggleGroupButtons(languageToggle, 11f, 40, false);
        updateToggleGroupButtons(commentToggle, 11f, 38, false);
        updateToggleGroupButtons(codeThemeToggle, 10.5f, 40, true);
        updateCompactToolbarLayout();
        if (topicNameView != null) {
            topicNameView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11f);
            topicNameView.setPaddingRelative(dpToPx(8), dpToPx(6), dpToPx(8), dpToPx(6));
        }
        if (modeHintView != null) {
            modeHintView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f);
        }
    }

    private void updateToggleGroupButtons(@Nullable MaterialButtonToggleGroup group, float textSizeSp, int minHeightDp, boolean vertical) {
        if (group == null) {
            return;
        }
        group.setOrientation(vertical ? LinearLayout.VERTICAL : LinearLayout.HORIZONTAL);
        for (int i = 0; i < group.getChildCount(); i++) {
            View child = group.getChildAt(i);
            if (!(child instanceof MaterialButton)) {
                continue;
            }
            MaterialButton button = (MaterialButton) child;
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
            button.setMinHeight(dpToPx(minHeightDp));
            button.setInsetTop(0);
            button.setInsetBottom(0);
            ViewGroup.LayoutParams rawParams = button.getLayoutParams();
            if (rawParams instanceof LinearLayout.LayoutParams) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rawParams;
                if (vertical) {
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    params.weight = 0f;
                } else {
                    params.width = 0;
                    params.weight = 1f;
                }
                button.setLayoutParams(params);
            }
        }
    }

    private void updateCompactToolbarLayout() {
        if (zoomOutButton == null || zoomInButton == null || copyButton == null || shareButton == null || zoomBadgeView == null) {
            return;
        }
        View parent = (View) zoomOutButton.getParent();
        if (!(parent instanceof LinearLayout)) {
            return;
        }
        LinearLayout toolbar = (LinearLayout) parent;
        toolbar.setOrientation(LinearLayout.VERTICAL);
        toolbar.setPadding(dpToPx(6), dpToPx(6), dpToPx(6), dpToPx(6));
        applyCompactToolbarChildLayout(zoomOutButton, false);
        applyCompactToolbarChildLayout(zoomInButton, false);
        applyCompactToolbarChildLayout(copyButton, true);
        applyCompactToolbarChildLayout(shareButton, true);
        ViewGroup.LayoutParams rawBadgeParams = zoomBadgeView.getLayoutParams();
        if (rawBadgeParams instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rawBadgeParams;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = dpToPx(34);
            params.setMargins(0, dpToPx(6), 0, 0);
            zoomBadgeView.setLayoutParams(params);
        }
        zoomBadgeView.setMinWidth(0);
        zoomBadgeView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10.5f);
    }

    private void applyCompactToolbarChildLayout(MaterialButton button, boolean withIcon) {
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11f);
        button.setIconPadding(dpToPx(4));
        button.setInsetTop(0);
        button.setInsetBottom(0);
        button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        if (!withIcon) {
            button.setIcon(null);
        }
        ViewGroup.LayoutParams rawParams = button.getLayoutParams();
        if (rawParams instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rawParams;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = dpToPx(34);
            params.weight = 0f;
            params.setMargins(0, params.topMargin == 0 ? 0 : dpToPx(6), 0, 0);
            button.setLayoutParams(params);
        }
    }

    private boolean isCompactPhone() {
        Configuration configuration = requireContext().getResources().getConfiguration();
        return configuration.smallestScreenWidthDp < 600 && configuration.screenWidthDp <= 360;
    }

    private void animateTabEntrance() {
        if (rootContentView == null) {
            return;
        }
        rootContentView.setAlpha(0f);
        if (isCodeTab()) {
            rootContentView.setTranslationX(dpToPx(22));
            rootContentView.animate()
                .alpha(1f)
                .translationX(0f)
                .setDuration(240L)
                .start();
        } else if (isAlgorithmTab()) {
            rootContentView.setTranslationY(dpToPx(12));
            rootContentView.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(220L)
                .start();
        } else {
            rootContentView.animate().alpha(1f).setDuration(180L).start();
        }
    }

    private boolean canOpenContent() {
        return styleToggle != null && styleToggle.getCheckedButtonId() != View.NO_ID;
    }

    private void animateModeSelection(int checkedId) {
        updateModeButtons(checkedId);
        View selected = styleToggle.findViewById(checkedId);
        if (selected == null) {
            return;
        }
        selected.animate().cancel();
        selected.setScaleX(0.96f);
        selected.setScaleY(0.96f);
        selected.animate()
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(180L)
            .start();
    }

    private void updateModeButtons(int checkedId) {
        if (styleToggle == null) {
            return;
        }
        List<Integer> buttonIds = Arrays.asList(
            R.id.content_style_iterative,
            R.id.content_style_functions,
            R.id.content_style_recursion
        );
        boolean algorithmTab = isAlgorithmTab();
        int activeBackground;
        int activeStroke;
        int inactiveBackground;
        int inactiveStroke;
        int activeText;
        int inactiveText;
        if (lightTheme) {
            activeBackground = Color.parseColor(algorithmTab ? "#D9ECFF" : "#EEE4FF");
            activeStroke = Color.parseColor(algorithmTab ? "#4E8FD1" : "#7C5CE0");
            inactiveBackground = Color.parseColor("#F3F6FA");
            inactiveStroke = Color.parseColor("#CBD5E1");
            activeText = Color.parseColor("#0F172A");
            inactiveText = Color.parseColor("#475569");
        } else {
            activeBackground = Color.parseColor(algorithmTab ? "#16334A" : "#2A1D44");
            activeStroke = Color.parseColor(algorithmTab ? "#5BA3D6" : "#8C6EE6");
            inactiveBackground = Color.parseColor(algorithmTab ? "#0F2434" : "#161C30");
            inactiveStroke = Color.parseColor(algorithmTab ? "#334E63" : "#3A3454");
            activeText = Color.parseColor("#F8FAFC");
            inactiveText = Color.parseColor("#C1D1E2");
        }

        for (int id : buttonIds) {
            MaterialButton button = styleToggle.findViewById(id);
            if (button == null) {
                continue;
            }
            boolean active = id == checkedId;
            button.setBackgroundTintList(android.content.res.ColorStateList.valueOf(active ? activeBackground : inactiveBackground));
            button.setStrokeColor(android.content.res.ColorStateList.valueOf(active ? activeStroke : inactiveStroke));
            button.setTextColor(active ? activeText : inactiveText);
            button.setElevation(active ? dpToPx(3) : 0f);
        }
    }

    private String getSelectedStyle() {
        if (styleToggle == null) {
            return "Iterative";
        }
        int checkedId = styleToggle.getCheckedButtonId();
        if (checkedId == R.id.content_style_functions) {
            return "Functions";
        }
        if (checkedId == R.id.content_style_recursion) {
            return "Recursion";
        }
        return "Iterative";
    }

    private String getSelectedLanguage() {
        if (languageToggle == null) {
            return "Java";
        }
        int checkedId = languageToggle.getCheckedButtonId();
        if (checkedId == R.id.content_language_c) {
            return "C";
        }
        if (checkedId == R.id.content_language_python) {
            return "Python";
        }
        return "Java";
    }

    private String prettifyTechniqueId(String techniqueId) {
        if (techniqueId == null || techniqueId.trim().isEmpty()) {
            return "Technique";
        }
        String[] parts = techniqueId.replace('-', '_').split("_");
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

    private void applyThemeStyling() {
        if (sectionLabelView != null) {
            sectionLabelView.setText(isAlgorithmTab() ? R.string.algorithm_section : R.string.source_code);
            sectionLabelView.setTextColor(resolveThemeColor(android.R.attr.textColorSecondary, R.color.text_secondary));
        }
        if (topicNameView != null) {
            topicNameView.setTextColor(resolveThemeColor(android.R.attr.textColorSecondary, R.color.text_primary_soft));
            int topicSurface = lightTheme ? Color.parseColor("#F1F5F9") : ContextCompat.getColor(requireContext(), R.color.surface_panel_muted);
            ViewCompat.setBackgroundTintList(topicNameView, android.content.res.ColorStateList.valueOf(topicSurface));
        }
        if (modeHintView != null) {
            modeHintView.setTextColor(resolveThemeColor(android.R.attr.textColorSecondary, R.color.text_muted_soft));
        }
        if (contentView != null) {
            int baseTextColor = isCodeTab() ? resolveCodeThemePalette().baseTextColor : resolveThemeColor(android.R.attr.textColorPrimary, R.color.text_primary_bright);
            contentView.setTextColor(baseTextColor);
        }
        applyCodeSurfaceTheme();
        updateCodeThemeButtons();
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

    private boolean isLightTheme() {
        TypedValue typedValue = new TypedValue();
        if (requireContext().getTheme().resolveAttribute(androidx.appcompat.R.attr.isLightTheme, typedValue, true)) {
            return typedValue.data != 0;
        }
        return false;
    }

    private int resolveDefaultStyleButtonId() {
        if (currentTechniqueContent == null || currentTechniqueContent.algorithmByStyle == null) {
            return R.id.content_style_iterative;
        }
        if (hasAlgorithmStyle("Iterative")) {
            return R.id.content_style_iterative;
        }
        if (hasAlgorithmStyle("Functions") || hasAlgorithmStyle("Function")) {
            return R.id.content_style_functions;
        }
        if (hasAlgorithmStyle("Recursion")) {
            return R.id.content_style_recursion;
        }
        return R.id.content_style_iterative;
    }

    private boolean hasAlgorithmStyle(String style) {
        if (currentTechniqueContent == null || currentTechniqueContent.algorithmByStyle == null) {
            return false;
        }
        String value = currentTechniqueContent.algorithmByStyle.get(style);
        return value != null && !value.trim().isEmpty();
    }

    private void applyCodeSurfaceTheme() {
        if (!isCodeTab() || horizontalScrollView == null) {
            return;
        }
        CodeThemePalette palette = resolveCodeThemePalette();
        ViewCompat.setBackgroundTintList(horizontalScrollView, ColorStateList.valueOf(palette.surfaceColor));
    }

    private void updateCodeThemeButtons() {
        if (codeThemeToggle == null || !isCodeTab()) {
            return;
        }
        List<Integer> buttonIds = Arrays.asList(
            R.id.content_code_theme_classic,
            R.id.content_code_theme_dim,
            R.id.content_code_theme_contrast
        );
        String currentTheme = appPreferences == null ? AppPreferences.CODE_THEME_CLASSIC : appPreferences.getCodeReaderTheme();
        int activeButtonId = resolveCodeThemeButtonId(currentTheme);
        CodeThemePalette palette = resolveCodeThemePalette();
        int activeBackground = palette.buttonActiveBackground;
        int activeStroke = palette.buttonActiveStroke;
        int inactiveBackground = lightTheme ? Color.parseColor("#F3F6FA") : Color.parseColor("#161C30");
        int inactiveStroke = lightTheme ? Color.parseColor("#CBD5E1") : Color.parseColor("#3A3454");
        int activeText = palette.buttonActiveText;
        int inactiveText = lightTheme ? Color.parseColor("#475569") : Color.parseColor("#C1D1E2");
        for (int id : buttonIds) {
            MaterialButton button = codeThemeToggle.findViewById(id);
            if (button == null) {
                continue;
            }
            boolean active = id == activeButtonId;
            button.setBackgroundTintList(ColorStateList.valueOf(active ? activeBackground : inactiveBackground));
            button.setStrokeColor(ColorStateList.valueOf(active ? activeStroke : inactiveStroke));
            button.setTextColor(active ? activeText : inactiveText);
            button.setElevation(active ? dpToPx(3) : 0f);
        }
    }

    private int resolveCodeThemeButtonId(String theme) {
        if (AppPreferences.CODE_THEME_DIM.equals(theme)) {
            return R.id.content_code_theme_dim;
        }
        if (AppPreferences.CODE_THEME_CONTRAST.equals(theme)) {
            return R.id.content_code_theme_contrast;
        }
        return R.id.content_code_theme_classic;
    }

    private String resolveCodeThemeFromButtonId(int checkedId) {
        if (checkedId == R.id.content_code_theme_dim) {
            return AppPreferences.CODE_THEME_DIM;
        }
        if (checkedId == R.id.content_code_theme_contrast) {
            return AppPreferences.CODE_THEME_CONTRAST;
        }
        return AppPreferences.CODE_THEME_CLASSIC;
    }

    private CodeThemePalette resolveCodeThemePalette() {
        String theme = appPreferences == null ? AppPreferences.CODE_THEME_CLASSIC : appPreferences.getCodeReaderTheme();
        if (AppPreferences.CODE_THEME_DIM.equals(theme)) {
            return new CodeThemePalette(
                Color.parseColor("#D6E2F0"),
                Color.parseColor("#7DD3FC"),
                Color.parseColor("#94A3B8"),
                Color.parseColor("#FBBF24"),
                Color.parseColor("#F9A8D4"),
                Color.parseColor("#28405B"),
                lightTheme ? Color.parseColor("#E4EDF7") : Color.parseColor("#102033"),
                Color.parseColor("#27405C"),
                lightTheme ? Color.parseColor("#DBEAFE") : Color.parseColor("#16334A"),
                lightTheme ? Color.parseColor("#4E8FD1") : Color.parseColor("#5BA3D6"),
                Color.parseColor("#0F172A")
            );
        }
        if (AppPreferences.CODE_THEME_CONTRAST.equals(theme)) {
            return new CodeThemePalette(
                lightTheme ? Color.parseColor("#0B1220") : Color.parseColor("#FFFFFF"),
                Color.parseColor("#2563EB"),
                Color.parseColor("#047857"),
                Color.parseColor("#B45309"),
                Color.parseColor("#BE185D"),
                lightTheme ? Color.parseColor("#BFDBFE") : Color.parseColor("#1D4ED8"),
                lightTheme ? Color.parseColor("#FFFFFF") : Color.parseColor("#0A1624"),
                lightTheme ? Color.parseColor("#94A3B8") : Color.parseColor("#60A5FA"),
                lightTheme ? Color.parseColor("#DBEAFE") : Color.parseColor("#1D4ED8"),
                lightTheme ? Color.parseColor("#2563EB") : Color.parseColor("#93C5FD"),
                lightTheme ? Color.parseColor("#0B1220") : Color.parseColor("#FFFFFF")
            );
        }
        return new CodeThemePalette(
            resolveThemeColor(android.R.attr.textColorPrimary, R.color.text_primary_bright),
            Color.parseColor("#60A5FA"),
            Color.parseColor("#94A3B8"),
            Color.parseColor("#FCD34D"),
            Color.parseColor("#F472B6"),
            Color.parseColor("#1E3A5F"),
            ContextCompat.getColor(requireContext(), R.color.code_surface),
            ContextCompat.getColor(requireContext(), R.color.code_surface_stroke),
            lightTheme ? Color.parseColor("#EEE4FF") : Color.parseColor("#2A1D44"),
            lightTheme ? Color.parseColor("#7C5CE0") : Color.parseColor("#8C6EE6"),
            lightTheme ? Color.parseColor("#0F172A") : Color.parseColor("#F8FAFC")
        );
    }

    private static class CodeThemePalette {
        final int baseTextColor;
        final int keywordColor;
        final int commentColor;
        final int stringColor;
        final int literalColor;
        final int lineHighlightColor;
        final int surfaceColor;
        final int surfaceStrokeColor;
        final int buttonActiveBackground;
        final int buttonActiveStroke;
        final int buttonActiveText;

        CodeThemePalette(int baseTextColor, int keywordColor, int commentColor, int stringColor, int literalColor,
                int lineHighlightColor, int surfaceColor, int surfaceStrokeColor, int buttonActiveBackground,
                int buttonActiveStroke, int buttonActiveText) {
            this.baseTextColor = baseTextColor;
            this.keywordColor = keywordColor;
            this.commentColor = commentColor;
            this.stringColor = stringColor;
            this.literalColor = literalColor;
            this.lineHighlightColor = lineHighlightColor;
            this.surfaceColor = surfaceColor;
            this.surfaceStrokeColor = surfaceStrokeColor;
            this.buttonActiveBackground = buttonActiveBackground;
            this.buttonActiveStroke = buttonActiveStroke;
            this.buttonActiveText = buttonActiveText;
        }
    }
}
