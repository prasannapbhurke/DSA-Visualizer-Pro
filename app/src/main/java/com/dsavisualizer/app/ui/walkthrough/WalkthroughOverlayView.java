package com.dsavisualizer.app.ui.walkthrough;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.dsavisualizer.app.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class WalkthroughOverlayView extends FrameLayout {

    public interface Listener {
        void onNext();
        void onSkip();
    }

    private final Paint scrimPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint clearPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Rect targetRect = new Rect();
    private final RectF cutoutRect = new RectF();

    private final MaterialCardView messageCard;
    private final TextView titleView;
    private final TextView bodyView;
    private final MaterialButton skipButton;
    private final MaterialButton nextButton;

    private View targetView;
    private Listener listener;
    private int spotlightPadding;
    private int cardMargin;
    private int cardWidth;
    private boolean hideSkipButton;

    public WalkthroughOverlayView(@NonNull Context context) {
        this(context, null);
    }

    public WalkthroughOverlayView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        setClickable(true);
        setFocusable(true);
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        spotlightPadding = dpToPx(12);
        cardMargin = dpToPx(18);
        cardWidth = dpToPx(320);

        scrimPaint.setColor(0xD91A2433);
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        borderPaint.setColor(0xFF7CB7FF);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(dpToPx(2));

        int cardBg = ContextCompat.getColor(context, R.color.surface_card);
        int cardStroke = ContextCompat.getColor(context, R.color.surface_stroke_accent);
        int titleColor = ContextCompat.getColor(context, R.color.text_primary_bright);
        int bodyColor = ContextCompat.getColor(context, R.color.text_secondary);

        messageCard = new MaterialCardView(context);
        messageCard.setCardBackgroundColor(cardBg);
        messageCard.setStrokeColor(cardStroke);
        messageCard.setStrokeWidth(dpToPx(1));
        messageCard.setRadius(dpToPx(20));

        LinearLayout content = new LinearLayout(context);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dpToPx(18), dpToPx(18), dpToPx(18), dpToPx(18));

        titleView = new TextView(context);
        titleView.setTextColor(titleColor);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        titleView.setTypeface(titleView.getTypeface(), android.graphics.Typeface.BOLD);

        bodyView = new TextView(context);
        LinearLayout.LayoutParams bodyParams = new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        );
        bodyParams.topMargin = dpToPx(10);
        bodyView.setLayoutParams(bodyParams);
        bodyView.setTextColor(bodyColor);
        bodyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        bodyView.setLineSpacing(0f, 1.15f);

        LinearLayout actionRow = new LinearLayout(context);
        LinearLayout.LayoutParams actionParams = new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        );
        actionParams.topMargin = dpToPx(16);
        actionRow.setLayoutParams(actionParams);
        actionRow.setGravity(Gravity.END);
        actionRow.setOrientation(LinearLayout.HORIZONTAL);

        skipButton = new MaterialButton(context, null, com.google.android.material.R.attr.materialButtonOutlinedStyle);
        skipButton.setText(R.string.walkthrough_skip);
        skipButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onSkip();
            }
        });

        nextButton = new MaterialButton(context);
        LinearLayout.LayoutParams nextParams = new LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        );
        nextParams.leftMargin = dpToPx(12);
        nextButton.setLayoutParams(nextParams);
        nextButton.setText(R.string.walkthrough_next);
        nextButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onNext();
            }
        });

        actionRow.addView(skipButton);
        actionRow.addView(nextButton);
        content.addView(titleView);
        content.addView(bodyView);
        content.addView(actionRow);
        messageCard.addView(content);

        FrameLayout.LayoutParams cardParams = new FrameLayout.LayoutParams(
            Math.min(cardWidth, getResources().getDisplayMetrics().widthPixels - dpToPx(32)),
            LayoutParams.WRAP_CONTENT
        );
        addView(messageCard, cardParams);
    }

    public void showForTarget(
        @NonNull View target,
        @NonNull CharSequence title,
        @NonNull CharSequence body,
        @NonNull CharSequence nextLabel,
        boolean hideSkip,
        @Nullable Listener stepListener
    ) {
        targetView = target;
        listener = stepListener;
        hideSkipButton = hideSkip;
        titleView.setText(title);
        bodyView.setText(body);
        nextButton.setText(TextUtils.isEmpty(nextLabel) ? getContext().getString(R.string.walkthrough_next) : nextLabel);
        skipButton.setVisibility(hideSkipButton ? GONE : VISIBLE);
        setVisibility(VISIBLE);
        post(this::refreshLayoutForTarget);
    }

    public void clearStep() {
        targetView = null;
        listener = null;
        cutoutRect.setEmpty();
        setVisibility(GONE);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getVisibility() != VISIBLE) {
            return;
        }
        int saveCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null);
        canvas.drawRect(0, 0, getWidth(), getHeight(), scrimPaint);
        if (targetView != null && targetView.getGlobalVisibleRect(targetRect)) {
            updateCutoutRect();
            canvas.drawRoundRect(cutoutRect, dpToPx(18), dpToPx(18), clearPaint);
            canvas.drawRoundRect(cutoutRect, dpToPx(18), dpToPx(18), borderPaint);
        }
        canvas.restoreToCount(saveCount);
    }

    private void refreshLayoutForTarget() {
        if (targetView == null || !targetView.getGlobalVisibleRect(targetRect)) {
            cutoutRect.setEmpty();
            invalidate();
            return;
        }
        updateCutoutRect();
        int availableWidth = Math.max(dpToPx(240), getWidth() - (cardMargin * 2));
        LayoutParams params = (LayoutParams) messageCard.getLayoutParams();
        params.width = Math.min(cardWidth, availableWidth);

        int desiredLeft = (int) Math.max(cardMargin, Math.min(cutoutRect.left, getWidth() - params.width - cardMargin));
        int desiredTop = (int) cutoutRect.bottom + dpToPx(16);
        messageCard.measure(
            MeasureSpec.makeMeasureSpec(params.width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        );
        int cardHeight = messageCard.getMeasuredHeight();
        if (desiredTop + cardHeight + cardMargin > getHeight()) {
            desiredTop = (int) Math.max(cardMargin, cutoutRect.top - cardHeight - dpToPx(16));
        }
        params.leftMargin = desiredLeft;
        params.topMargin = desiredTop;
        messageCard.setLayoutParams(params);
        invalidate();
    }

    private void updateCutoutRect() {
        int[] overlayLocation = new int[2];
        int[] targetLocation = new int[2];
        getLocationOnScreen(overlayLocation);
        targetView.getLocationOnScreen(targetLocation);
        float left = targetLocation[0] - overlayLocation[0] - spotlightPadding;
        float top = targetLocation[1] - overlayLocation[1] - spotlightPadding;
        float right = left + targetView.getWidth() + (spotlightPadding * 2);
        float bottom = top + targetView.getHeight() + (spotlightPadding * 2);
        cutoutRect.set(left, top, right, bottom);
    }

    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }
}
