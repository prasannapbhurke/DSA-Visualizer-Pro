package com.dsavisualizer.app;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.HashSet;
import java.util.Set;

public class VisualizerView extends View {
    private int[] data;
    private float[] animatedData;
    private int highlight1 = -1, highlight2 = -1;
    private Set<Integer> sortedIndices = new HashSet<>();
    private Paint barPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int baseColor = Color.parseColor("#61dafb");
    private int highlightColor = Color.parseColor("#EF4444");
    private int sortedColor = Color.parseColor("#10B981");

    public VisualizerView(Context context) {
        super(context);
        init();
    }

    public VisualizerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        textPaint.setColor(Color.parseColor("#94A3B8"));
        textPaint.setTextSize(28f);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void init(int[] d) {
        if (d == null) return;
        data = d.clone();
        animatedData = new float[data.length];
        for (int i = 0; i < data.length; i++) animatedData[i] = data[i];
        postInvalidate();
    }

    public void updateData(int[] newData) {
        if (data == null || data.length != newData.length) {
            init(newData);
            return;
        }
        float[] startData = animatedData.clone();
        data = newData.clone();
        
        post(() -> {
            ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
            animator.setDuration(450);
            animator.setInterpolator(new android.view.animation.OvershootInterpolator(1.5f));
            animator.addUpdateListener(animation -> {
                float fraction = (float) animation.getAnimatedValue();
                for (int i = 0; i < data.length; i++) {
                    animatedData[i] = startData[i] + (data[i] - startData[i]) * fraction;
                }
                invalidate();
            });
            animator.start();
        });
    }

    public void setHighlight(int h1, int h2) {
        this.highlight1 = h1;
        this.highlight2 = h2;
        postInvalidate();
    }

    public void setSorted(int index) {
        sortedIndices.add(index);
        postInvalidate();
    }

    public void setPalette(int baseColor, int highlightColor, int sortedColor) {
        this.baseColor = baseColor;
        this.highlightColor = highlightColor;
        this.sortedColor = sortedColor;
        postInvalidate();
    }

    public void clearSorted() {
        sortedIndices.clear();
        postInvalidate();
    }

    public void reset() {
        data = null;
        animatedData = null;
        sortedIndices.clear();
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (animatedData == null || animatedData.length == 0) return;

        float padding = 40f;
        float availableWidth = getWidth() - (padding * 2);
        float availableHeight = getHeight() - (padding * 3);
        
        int n = animatedData.length;
        float barWidth = (availableWidth / n) * 0.7f;
        float spacing = (availableWidth / n) * 0.3f;
        
        float maxVal = 0;
        for (float val : animatedData) maxVal = Math.max(maxVal, val);
        if (maxVal == 0) maxVal = 1;

        for (int i = 0; i < n; i++) {
            float h = (animatedData[i] / maxVal) * availableHeight;
            float left = padding + i * (barWidth + spacing);
            float top = getHeight() - padding - h;
            float right = left + barWidth;
            float bottom = getHeight() - padding;

            if (i == highlight1 || i == highlight2) {
                barPaint.setColor(highlightColor);
            } else if (sortedIndices.contains(i)) {
                barPaint.setColor(sortedColor);
            } else {
                barPaint.setColor(baseColor);
            }

            canvas.drawRoundRect(left, top, right, bottom, 15f, 15f, barPaint);
            canvas.drawText(String.valueOf(data[i]), left + barWidth/2, top - 15f, textPaint);
        }
    }
}
