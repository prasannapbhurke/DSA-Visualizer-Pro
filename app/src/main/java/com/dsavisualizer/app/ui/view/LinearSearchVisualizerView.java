package com.dsavisualizer.app.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class LinearSearchVisualizerView extends View {

    private final Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int[] values = new int[0];
    private int currentIndex = -1;
    private int foundIndex = -1;

    public LinearSearchVisualizerView(Context context) {
        super(context);
        init();
    }

    public LinearSearchVisualizerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(36f);
    }

    public void setValues(int[] values) {
        this.values = values == null ? new int[0] : values.clone();
        currentIndex = -1;
        foundIndex = -1;
        invalidate();
    }

    public void updateState(int currentIndex, int foundIndex) {
        this.currentIndex = currentIndex;
        this.foundIndex = foundIndex;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (values.length == 0) {
            return;
        }

        float padding = 24f;
        float cellWidth = (getWidth() - (padding * 2)) / values.length;
        float top = getHeight() * 0.32f;
        float bottom = top + 140f;

        for (int i = 0; i < values.length; i++) {
            float left = padding + (i * cellWidth);
            float right = left + cellWidth - 12f;

            if (i == foundIndex) {
                fillPaint.setColor(Color.parseColor("#22C55E"));
            } else if (i == currentIndex) {
                fillPaint.setColor(Color.parseColor("#F59E0B"));
            } else {
                fillPaint.setColor(Color.parseColor("#1E293B"));
            }

            canvas.drawRoundRect(left, top, right, bottom, 24f, 24f, fillPaint);
            canvas.drawText(String.valueOf(values[i]), (left + right) / 2f, top + 78f, textPaint);
            canvas.drawText(String.valueOf(i), (left + right) / 2f, bottom + 42f, textPaint);
        }
    }
}
