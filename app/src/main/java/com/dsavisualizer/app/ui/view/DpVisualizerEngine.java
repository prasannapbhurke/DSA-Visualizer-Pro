package com.dsavisualizer.app.ui.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.dsavisualizer.app.ui.visualization.VisualizationTechniqueRegistry;
import com.dsavisualizer.app.ui.visualization.runtime.DpStepEngine;

/**
 * High-fidelity Canvas renderer for Dynamic Programming:
 * - Knapsack, LCS, LIS, Coin Change, Matrix Chain, Edit Distance, Rod Cutting, Subset Sum, Kadane
 * - Renders 2D subproblem tables with semantic row/col labels, active cell spotlight, dependency highlights,
 *   and optimal solution reconstructions.
 */
final class DpVisualizerEngine implements VisualizerEngine {

    private final Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint highlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF rect = new RectF();

    public DpVisualizerEngine() {
        strokePaint.setStyle(Paint.Style.STROKE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setFakeBoldText(true);
    }

    @Override
    public boolean supports(String techniqueId) {
        return VisualizationTechniqueRegistry.isDynamicProgramming(techniqueId)
            || (techniqueId != null && techniqueId.startsWith("dp_"));
    }

    @Override
    public void draw(Canvas canvas, TechniqueVisualizerView view, String techniqueId) {
        int[][] dp = view.getMatrixValues();
        if (dp == null || dp.length == 0) {
            // Fallback if matrix is not yet populated
            view.drawHorizontalBoxes(canvas, false);
            return;
        }

        int rows = dp.length;
        int cols = dp[0].length;
        float width = view.getWidth();
        float height = view.getHeight();

        float startY = 88f;
        float titleY = 38f;
        float subY = 64f;

        // 1. Draw Table Title & Subproblem header
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(22f);
        textPaint.setColor(Color.parseColor("#F8FAFC"));
        String label = view.resolveMatrixLabel();
        if (label == null || label.isEmpty()) {
            label = "Dynamic Programming State Table";
        }
        canvas.drawText(label, 24f, titleY, textPaint);

        int activeR = view.getMatrixRow();
        int activeC = view.getMatrixCol();
        if (activeR >= 0 && activeC >= 0 && activeR < rows && activeC < cols) {
            textPaint.setTextSize(16f);
            textPaint.setColor(Color.parseColor("#38BDF8"));
            canvas.drawText("Active Cell: dp[" + activeR + "][" + activeC + "] = "
                + (dp[activeR][activeC] >= 9999 ? "INF" : String.valueOf(dp[activeR][activeC])), 24f, subY, textPaint);
        }

        // 2. Compute adaptive cell sizing
        float availableW = width - 110f;
        float availableH = height - startY - 45f;
        float cellSize = Math.min(availableW / Math.max(1, cols), availableH / Math.max(1, rows));
        cellSize = Math.max(34f, Math.min(72f, cellSize));

        float startX = 84f;

        // Retrieve semantic headers if available
        String[] colHeaders = DpStepEngine.getColumnHeaders(techniqueId, cols);
        String[] rowHeaders = DpStepEngine.getRowHeaders(techniqueId, rows);

        // 3. Draw column headers
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(Math.max(11f, Math.min(15f, cellSize * 0.32f)));
        textPaint.setColor(Color.parseColor("#94A3B8"));
        for (int c = 0; c < cols; c++) {
            float cx = startX + (c * cellSize) + (cellSize / 2f);
            String cText = (colHeaders != null && c < colHeaders.length) ? colHeaders[c] : String.valueOf(c);
            canvas.drawText(cText, cx, startY - 8f, textPaint);
        }

        // 4. Draw rows and cells
        for (int r = 0; r < rows; r++) {
            // Row header / index label
            float ry = startY + (r * cellSize) + (cellSize * 0.60f);
            textPaint.setTextAlign(Paint.Align.RIGHT);
            textPaint.setColor(Color.parseColor("#94A3B8"));
            textPaint.setTextSize(Math.max(11f, Math.min(14f, cellSize * 0.30f)));
            String rText = (rowHeaders != null && r < rowHeaders.length) ? rowHeaders[r] : String.valueOf(r);
            canvas.drawText(rText, startX - 10f, ry, textPaint);

            textPaint.setTextAlign(Paint.Align.CENTER);
            for (int c = 0; c < cols; c++) {
                float left = startX + (c * cellSize);
                float top = startY + (r * cellSize);
                float right = left + cellSize - 4f;
                float bottom = top + cellSize - 4f;
                rect.set(left, top, right, bottom);

                boolean isActive = (r == activeR && c == activeC);
                boolean isDependency = (activeR > 0 && activeC >= 0
                    && ((r == activeR - 1 && c == activeC)
                        || (r == activeR - 1 && c == activeC - 1)
                        || (r == activeR && c == activeC - 1)
                        || (r == 0 && rows == 2)));

                int baseColor = Color.parseColor("#0F172A");
                int strokeColor = Color.parseColor("#334155");

                if (isActive) {
                    baseColor = Color.parseColor("#0369A1"); // Bright blue spotlight
                    strokeColor = Color.parseColor("#F59E0B"); // Gold border
                } else if (isDependency) {
                    baseColor = Color.parseColor("#1E293B"); // Subproblem lookup tint
                    strokeColor = Color.parseColor("#38BDF8"); // Cyan border
                } else if (dp[r][c] != 0 && dp[r][c] < 9999) {
                    baseColor = Color.parseColor("#1E1B4B"); // Solved subproblem indigo
                }

                fillPaint.setColor(baseColor);
                canvas.drawRoundRect(rect, 8f, 8f, fillPaint);

                strokePaint.setColor(strokeColor);
                strokePaint.setStrokeWidth(isActive ? 3.0f : (isDependency ? 2.0f : 1.2f));
                canvas.drawRoundRect(rect, 8f, 8f, strokePaint);

                // Cell value
                textPaint.setTextSize(Math.max(12f, cellSize * 0.34f));
                textPaint.setColor(isActive ? Color.parseColor("#FCD34D") : Color.parseColor("#F1F5F9"));
                int val = dp[r][c];
                String textVal;
                if (val >= 9999) {
                    textVal = "∞";
                } else if ("dp_subset_sum".equals(techniqueId)) {
                    textVal = (val == 1) ? "T" : "F";
                } else {
                    textVal = String.valueOf(val);
                }
                canvas.drawText(textVal, (left + right) / 2f, top + (cellSize * 0.58f), textPaint);
            }
        }
    }

    @Override
    public int resolveDesiredWidth(TechniqueVisualizerView view, String techniqueId) {
        return view.resolveScreenWidthDesiredWidth();
    }

    @Override
    public int resolveDesiredHeight(TechniqueVisualizerView view, String techniqueId) {
        return Math.max(view.resolveTraversalDesiredHeight(), 540);
    }
}
