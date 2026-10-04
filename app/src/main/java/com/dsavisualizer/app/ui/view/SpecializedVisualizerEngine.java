package com.dsavisualizer.app.ui.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Visualizer engine for Specialized and Computational Geometry / Math algorithms:
 * Convex Hull, Closest Pair of Points, Line Intersection, Sieve of Eratosthenes,
 * Euclidean GCD, Karatsuba Multiplication, Fast Fourier Transform (FFT).
 */
public class SpecializedVisualizerEngine implements VisualizerEngine {

    private static final Set<String> SUPPORTED = new HashSet<>(Arrays.asList(
            "spec_convex_hull",
            "spec_closest_pair",
            "spec_line_intersection",
            "spec_fft", "spec_fast_fourier_transform",
            "spec_karatsuba",
            "spec_sieve", "spec_sieve_eratosthenes",
            "spec_miller_rabin",
            "spec_extended_euclidean", "spec_euclidean_gcd",
            "spec_crt", "spec_chinese_remainder",
            "spec_activity_selection"
    ));

    private final Paint pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint subTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF rect = new RectF();
    private final Path path = new Path();

    public SpecializedVisualizerEngine() {
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(3.5f);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(2f);
        textPaint.setTextAlign(Paint.Align.CENTER);
        subTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    public static boolean isSupportedTechnique(String techniqueId) {
        return techniqueId != null && SUPPORTED.contains(techniqueId);
    }

    @Override
    public boolean supports(String techniqueId) {
        return isSupportedTechnique(techniqueId);
    }

    @Override
    public int resolveDesiredWidth(TechniqueVisualizerView view, String techniqueId) {
        return Math.max(view.getWidth(), 440);
    }

    @Override
    public int resolveDesiredHeight(TechniqueVisualizerView view, String techniqueId) {
        return Math.max(view.getHeight(), 360);
    }

    @Override
    public void draw(Canvas canvas, TechniqueVisualizerView view, String techniqueId) {
        switch (techniqueId != null ? techniqueId : "") {
            case "spec_sieve":
            case "spec_sieve_eratosthenes":
                drawSieve(canvas, view);
                break;
            case "spec_closest_pair":
                drawClosestPair(canvas, view);
                break;
            case "spec_line_intersection":
                drawLineIntersection(canvas, view);
                break;
            case "spec_extended_euclidean":
            case "spec_euclidean_gcd":
                drawExtendedEuclidean(canvas, view);
                break;
            case "spec_karatsuba":
                drawKaratsuba(canvas, view);
                break;
            case "spec_fft":
            case "spec_fast_fourier_transform":
                drawFft(canvas, view);
                break;
            case "spec_miller_rabin":
                drawMillerRabin(canvas, view);
                break;
            case "spec_crt":
            case "spec_chinese_remainder":
                drawCrt(canvas, view);
                break;
            case "spec_activity_selection":
                drawActivitySelection(canvas, view);
                break;
            case "spec_convex_hull":
            default:
                drawConvexHull(canvas, view);
                break;
        }
    }

    private void drawConvexHull(Canvas canvas, TechniqueVisualizerView view) {
        float cx = view.getWidth() / 2f;
        float cy = view.getHeight() / 2f + 10f;

        subTextPaint.setColor(Color.parseColor("#06B6D4"));
        subTextPaint.setTextSize(26f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText("CONVEX HULL (GRAHAM SCAN / MONOTONE CHAIN)", cx, 50f, subTextPaint);

        // Predefined 2D points relative to center
        float[][] points = {
                {-130f, 60f}, {-90f, -70f}, {-30f, -110f}, {40f, -80f},
                {130f, -20f}, {110f, 70f}, {30f, 100f}, {-40f, 90f},
                // Interior points
                {-20f, 10f}, {30f, -20f}, {-50f, -20f}, {60f, 30f}
        };

        // Draw Hull Polygon
        path.reset();
        int[] hullIdx = {0, 1, 2, 3, 4, 5, 6, 7};
        for (int i = 0; i < hullIdx.length; i++) {
            float px = cx + points[hullIdx[i]][0];
            float py = cy + points[hullIdx[i]][1];
            if (i == 0) path.moveTo(px, py);
            else path.lineTo(px, py);
        }
        path.close();

        fillPaint.setColor(Color.parseColor("#1506B6D4"));
        fillPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, fillPaint);

        linePaint.setColor(Color.parseColor("#22D3EE"));
        linePaint.setStrokeWidth(4f);
        canvas.drawPath(path, linePaint);

        // Draw Points
        for (int i = 0; i < points.length; i++) {
            float px = cx + points[i][0];
            float py = cy + points[i][1];
            boolean isHull = i < 8;

            pointPaint.setColor(isHull ? Color.parseColor("#F59E0B") : Color.parseColor("#64748B"));
            canvas.drawCircle(px, py, isHull ? 8f : 5.5f, pointPaint);
        }
    }

    private void drawClosestPair(Canvas canvas, TechniqueVisualizerView view) {
        float cx = view.getWidth() / 2f;
        float cy = view.getHeight() / 2f + 10f;

        subTextPaint.setColor(Color.parseColor("#E11D48"));
        subTextPaint.setTextSize(26f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText("CLOSEST PAIR OF POINTS (DIVIDE & CONQUER)", cx, 50f, subTextPaint);

        // Dividing vertical line
        linePaint.setColor(Color.parseColor("#475569"));
        linePaint.setStrokeWidth(2.5f);
        canvas.drawLine(cx, 70f, cx, view.getHeight() - 40f, linePaint);

        // Delta strip
        fillPaint.setColor(Color.parseColor("#12E11D48"));
        fillPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(cx - 60f, 70f, cx + 60f, view.getHeight() - 40f, fillPaint);

        subTextPaint.setColor(Color.parseColor("#FB7185"));
        subTextPaint.setTextSize(18f);
        canvas.drawText("Strip 2δ", cx, 86f, subTextPaint);

        // Points
        float[][] pts = {
                {-130f, -40f}, {-90f, 50f}, {-40f, -80f}, {-25f, 20f},
                {20f, 35f}, {45f, -60f}, {110f, 70f}, {130f, -30f}
        };

        for (float[] p : pts) {
            pointPaint.setColor(Color.parseColor("#38BDF8"));
            canvas.drawCircle(cx + p[0], cy + p[1], 6.5f, pointPaint);
        }

        // Closest pair connection (pts[3] and pts[4])
        linePaint.setColor(Color.parseColor("#FBBF24"));
        linePaint.setStrokeWidth(4.5f);
        canvas.drawLine(cx + pts[3][0], cy + pts[3][1], cx + pts[4][0], cy + pts[4][1], linePaint);

        pointPaint.setColor(Color.parseColor("#F59E0B"));
        canvas.drawCircle(cx + pts[3][0], cy + pts[3][1], 8f, pointPaint);
        canvas.drawCircle(cx + pts[4][0], cy + pts[4][1], 8f, pointPaint);

        textPaint.setColor(Color.parseColor("#FDE68A"));
        textPaint.setTextSize(18f);
        canvas.drawText("min dist δ = 21.2", cx, cy + pts[3][1] + 32f, textPaint);
    }

    private void drawLineIntersection(Canvas canvas, TechniqueVisualizerView view) {
        float cx = view.getWidth() / 2f;
        float cy = view.getHeight() / 2f;

        subTextPaint.setColor(Color.parseColor("#8B5CF6"));
        subTextPaint.setTextSize(26f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText("LINE SEGMENT INTERSECTION (ORIENTATION CCW)", cx, 50f, subTextPaint);

        // Segment 1: A to B
        float ax = cx - 120f, ay = cy - 60f;
        float bx = cx + 110f, by = cy + 50f;
        linePaint.setColor(Color.parseColor("#38BDF8"));
        linePaint.setStrokeWidth(5f);
        canvas.drawLine(ax, ay, bx, by, linePaint);

        // Segment 2: C to D
        float cxPt = cx - 70f, cyPt = cy + 70f;
        float dxPt = cx + 80f, dyPt = cy - 80f;
        linePaint.setColor(Color.parseColor("#EC4899"));
        canvas.drawLine(cxPt, cyPt, dxPt, dyPt, linePaint);

        // Draw Endpoints
        pointPaint.setColor(Color.WHITE);
        canvas.drawCircle(ax, ay, 7f, pointPaint);
        canvas.drawCircle(bx, by, 7f, pointPaint);
        canvas.drawCircle(cxPt, cyPt, 7f, pointPaint);
        canvas.drawCircle(dxPt, dyPt, 7f, pointPaint);

        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(18f);
        canvas.drawText("A", ax - 14f, ay, textPaint);
        canvas.drawText("B", bx + 16f, by, textPaint);
        canvas.drawText("C", cxPt - 14f, cyPt + 16f, textPaint);
        canvas.drawText("D", dxPt + 16f, dyPt - 8f, textPaint);

        // Intersection point
        float ix = cx - 4f, iy = cy - 4f;
        pointPaint.setColor(Color.parseColor("#10B981"));
        canvas.drawCircle(ix, iy, 11f, pointPaint);

        textPaint.setColor(Color.parseColor("#34D399"));
        textPaint.setTextSize(20f);
        canvas.drawText("Intersection Found (Segments Cross)", cx, cy + 120f, textPaint);
    }

    private void drawSieve(Canvas canvas, TechniqueVisualizerView view) {
        float cx = view.getWidth() / 2f;
        subTextPaint.setColor(Color.parseColor("#10B981"));
        subTextPaint.setTextSize(26f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText("SIEVE OF ERATOSTHENES (PRIMES UP TO 30)", cx, 50f, subTextPaint);

        float startX = 35f;
        float startY = 80f;
        float boxSize = 44f;
        float gap = 8f;
        int cols = 6;

        boolean[] isPrime = new boolean[31];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;
        for (int p = 2; p * p <= 30; p++) {
            if (isPrime[p]) {
                for (int i = p * p; i <= 30; i += p) isPrime[i] = false;
            }
        }

        for (int num = 2; num <= 30; num++) {
            int idx = num - 2;
            int r = idx / cols;
            int c = idx % cols;

            float x = startX + c * (boxSize + gap);
            float y = startY + r * (boxSize + gap);
            rect.set(x, y, x + boxSize, y + boxSize);

            boolean prime = isPrime[num];
            fillPaint.setColor(prime ? Color.parseColor("#059669") : Color.parseColor("#1E293B"));
            borderPaint.setColor(prime ? Color.parseColor("#34D399") : Color.parseColor("#334155"));
            fillPaint.setStyle(Paint.Style.FILL);

            canvas.drawRoundRect(rect, 8f, 8f, fillPaint);
            canvas.drawRoundRect(rect, 8f, 8f, borderPaint);

            textPaint.setColor(prime ? Color.WHITE : Color.parseColor("#64748B"));
            textPaint.setTextSize(20f);
            textPaint.setFakeBoldText(prime);
            canvas.drawText(String.valueOf(num), rect.centerX(), rect.centerY() + 7f, textPaint);
        }
    }

    private void drawExtendedEuclidean(Canvas canvas, TechniqueVisualizerView view) {
        float cx = view.getWidth() / 2f;
        subTextPaint.setColor(Color.parseColor("#F59E0B"));
        subTextPaint.setTextSize(26f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText("EXTENDED EUCLIDEAN ALGORITHM (BÉZOUT IDENTITY)", cx, 50f, subTextPaint);

        String[] steps = {
                "Step 1: gcd(35, 15) -> 35 = 2 × 15 + 5  (q=2, r=5)",
                "Step 2: gcd(15, 5)  -> 15 = 3 × 5 + 0   (r=0 -> GCD=5)",
                "Step 3: Back-Substitution: 5 = 35 × (1) + 15 × (-2)",
                "RESULT: 35(1) + 15(-2) = 5  [x = 1, y = -2, gcd = 5]"
        };

        float y = 90f;
        for (int i = 0; i < steps.length; i++) {
            rect.set(24f, y, view.getWidth() - 24f, y + 44f);
            boolean isResult = (i == steps.length - 1);
            fillPaint.setColor(isResult ? Color.parseColor("#78350F") : Color.parseColor("#1E293B"));
            borderPaint.setColor(isResult ? Color.parseColor("#FBBF24") : Color.parseColor("#475569"));
            fillPaint.setStyle(Paint.Style.FILL);

            canvas.drawRoundRect(rect, 8f, 8f, fillPaint);
            canvas.drawRoundRect(rect, 8f, 8f, borderPaint);

            textPaint.setColor(isResult ? Color.parseColor("#FDE68A") : Color.WHITE);
            textPaint.setTextSize(17f);
            textPaint.setFakeBoldText(isResult);
            canvas.drawText(steps[i], rect.centerX(), rect.centerY() + 6f, textPaint);

            y += 52f;
        }
    }

    private void drawMillerRabin(Canvas canvas, TechniqueVisualizerView view) {
        float cx = view.getWidth() / 2f;
        subTextPaint.setColor(Color.parseColor("#06B6D4"));
        subTextPaint.setTextSize(26f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText("MILLER-RABIN PRIMALITY TEST (WITNESS BASES)", cx, 50f, subTextPaint);

        String[] steps = {
                "Candidate n = 61: Write n - 1 = 60 = 2^s · d  (s = 2, d = 15)",
                "Witness base a = 2: Compute x = a^d mod n = 2^15 mod 61 = 11",
                "Squaring step: x^2 mod 61 = 11^2 mod 61 = 121 mod 61 = 60 ≡ -1 mod 61",
                "VERDICT: 61 is PROBABLY PRIME with error probability < 4^(-k)"
        };

        float y = 90f;
        for (int i = 0; i < steps.length; i++) {
            rect.set(24f, y, view.getWidth() - 24f, y + 44f);
            boolean isResult = (i == steps.length - 1);
            fillPaint.setColor(isResult ? Color.parseColor("#0E7490") : Color.parseColor("#1E293B"));
            borderPaint.setColor(isResult ? Color.parseColor("#22D3EE") : Color.parseColor("#475569"));
            fillPaint.setStyle(Paint.Style.FILL);

            canvas.drawRoundRect(rect, 8f, 8f, fillPaint);
            canvas.drawRoundRect(rect, 8f, 8f, borderPaint);

            textPaint.setColor(isResult ? Color.parseColor("#CFFAFE") : Color.WHITE);
            textPaint.setTextSize(17f);
            textPaint.setFakeBoldText(isResult);
            canvas.drawText(steps[i], rect.centerX(), rect.centerY() + 6f, textPaint);

            y += 52f;
        }
    }

    private void drawCrt(Canvas canvas, TechniqueVisualizerView view) {
        float cx = view.getWidth() / 2f;
        subTextPaint.setColor(Color.parseColor("#F43F5E"));
        subTextPaint.setTextSize(26f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText("CHINESE REMAINDER THEOREM (CRT CONGRUENCE)", cx, 50f, subTextPaint);

        String[] steps = {
                "System: x ≡ 2 (mod 3),  x ≡ 3 (mod 5),  x ≡ 2 (mod 7)",
                "M = 3 × 5 × 7 = 105 | M1 = 35, M2 = 21, M3 = 15",
                "Inverses: 35·y1≡1 (mod 3) -> y1=2; 21·y2≡1 (mod 5) -> y2=1; 15·y3≡1 (mod 7) -> y3=1",
                "SOLUTION: x = (2·35·2 + 3·21·1 + 2·15·1) mod 105 = 233 mod 105 = 23"
        };

        float y = 90f;
        for (int i = 0; i < steps.length; i++) {
            rect.set(24f, y, view.getWidth() - 24f, y + 44f);
            boolean isResult = (i == steps.length - 1);
            fillPaint.setColor(isResult ? Color.parseColor("#881337") : Color.parseColor("#1E293B"));
            borderPaint.setColor(isResult ? Color.parseColor("#FB7185") : Color.parseColor("#475569"));
            fillPaint.setStyle(Paint.Style.FILL);

            canvas.drawRoundRect(rect, 8f, 8f, fillPaint);
            canvas.drawRoundRect(rect, 8f, 8f, borderPaint);

            textPaint.setColor(isResult ? Color.parseColor("#FFE4E6") : Color.WHITE);
            textPaint.setTextSize(17f);
            textPaint.setFakeBoldText(isResult);
            canvas.drawText(steps[i], rect.centerX(), rect.centerY() + 6f, textPaint);

            y += 52f;
        }
    }

    private void drawActivitySelection(Canvas canvas, TechniqueVisualizerView view) {
        float cx = view.getWidth() / 2f;
        subTextPaint.setColor(Color.parseColor("#10B981"));
        subTextPaint.setTextSize(26f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText("ACTIVITY SELECTION (GREEDY SCHEDULING)", cx, 50f, subTextPaint);

        // Timeline intervals
        float leftMargin = 50f;
        float rightMargin = view.getWidth() - 50f;
        float totalTime = 16f;
        float timeScale = (rightMargin - leftMargin) / totalTime;

        // Activities: {start, finish, accepted (1/0), label}
        float[][] acts = {
                {1f, 4f, 1f},
                {3f, 5f, 0f},
                {5f, 7f, 1f},
                {6f, 10f, 0f},
                {8f, 11f, 1f},
                {12f, 16f, 1f}
        };

        float y = 85f;
        float barH = 26f;

        for (int i = 0; i < acts.length; i++) {
            float start = acts[i][0];
            float finish = acts[i][1];
            boolean accepted = (acts[i][2] == 1f);

            float bx1 = leftMargin + start * timeScale;
            float bx2 = leftMargin + finish * timeScale;
            rect.set(bx1, y, bx2, y + barH);

            fillPaint.setColor(accepted ? Color.parseColor("#059669") : Color.parseColor("#334155"));
            borderPaint.setColor(accepted ? Color.parseColor("#34D399") : Color.parseColor("#EF4444"));
            fillPaint.setStyle(Paint.Style.FILL);

            canvas.drawRoundRect(rect, 6f, 6f, fillPaint);
            canvas.drawRoundRect(rect, 6f, 6f, borderPaint);

            textPaint.setColor(Color.WHITE);
            textPaint.setTextSize(14f);
            textPaint.setFakeBoldText(accepted);
            String label = "[" + (int)start + ".." + (int)finish + "] " + (accepted ? "ACCEPTED" : "CONFLICT");
            canvas.drawText(label, rect.centerX(), rect.centerY() + 5f, textPaint);

            y += 34f;
        }

        subTextPaint.setColor(Color.parseColor("#94A3B8"));
        subTextPaint.setTextSize(15f);
        canvas.drawText("Greedy choice: earliest finish time -> 4 non-overlapping activities selected in O(N log N)", cx, y + 22f, subTextPaint);
    }

    private void drawKaratsuba(Canvas canvas, TechniqueVisualizerView view) {
        float cx = view.getWidth() / 2f;
        subTextPaint.setColor(Color.parseColor("#6366F1"));
        subTextPaint.setTextSize(26f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText("KARATSUBA FAST MULTIPLICATION (O(n^1.585))", cx, 50f, subTextPaint);

        String[] steps = {
                "X = 1234, Y = 5678 (m = 2)",
                "z2 = x1 × y1 = 12 × 56 = 672",
                "z0 = x0 × y0 = 34 × 78 = 2652",
                "z1 = (x1+x0)(y1+y0) - z2 - z0 = 46 × 134 - 672 - 2652 = 2840",
                "XY = z2 × 10^4 + z1 × 10^2 + z0 = 7,006,652"
        };

        float y = 90f;
        for (int i = 0; i < steps.length; i++) {
            rect.set(24f, y, view.getWidth() - 24f, y + 42f);
            boolean isResult = (i == steps.length - 1);
            fillPaint.setColor(isResult ? Color.parseColor("#312E81") : Color.parseColor("#1E293B"));
            borderPaint.setColor(isResult ? Color.parseColor("#818CF8") : Color.parseColor("#334155"));
            fillPaint.setStyle(Paint.Style.FILL);

            canvas.drawRoundRect(rect, 8f, 8f, fillPaint);
            canvas.drawRoundRect(rect, 8f, 8f, borderPaint);

            textPaint.setColor(isResult ? Color.parseColor("#C7D2FE") : Color.WHITE);
            textPaint.setTextSize(17f);
            textPaint.setFakeBoldText(isResult);
            canvas.drawText(steps[i], rect.centerX(), rect.centerY() + 6f, textPaint);

            y += 50f;
        }
    }

    private void drawFft(Canvas canvas, TechniqueVisualizerView view) {
        float cx = view.getWidth() / 2f;
        subTextPaint.setColor(Color.parseColor("#EC4899"));
        subTextPaint.setTextSize(26f);
        subTextPaint.setFakeBoldText(true);
        canvas.drawText("COOLEY-TUKEY FAST FOURIER TRANSFORM (FFT)", cx, 50f, subTextPaint);

        // 4-point butterfly diagram
        float[] inputs = {100f, 160f, 220f, 280f};
        float xLeft = 50f;
        float xRight = view.getWidth() - 50f;

        linePaint.setColor(Color.parseColor("#475569"));
        linePaint.setStrokeWidth(3f);

        for (int i = 0; i < 4; i++) {
            float y = inputs[i];
            canvas.drawLine(xLeft, y, xRight, y, linePaint);

            // Left input node
            pointPaint.setColor(Color.parseColor("#38BDF8"));
            canvas.drawCircle(xLeft, y, 7f, pointPaint);
            textPaint.setColor(Color.parseColor("#94A3B8"));
            textPaint.setTextSize(18f);
            canvas.drawText("x[" + i + "]", xLeft - 22f, y + 6f, textPaint);

            // Right output node
            pointPaint.setColor(Color.parseColor("#F472B6"));
            canvas.drawCircle(xRight, y, 7f, pointPaint);
            canvas.drawText("X[" + i + "]", xRight + 24f, y + 6f, textPaint);
        }

        // Butterflies
        linePaint.setColor(Color.parseColor("#F59E0B"));
        canvas.drawLine(xLeft + 60f, inputs[0], xRight - 60f, inputs[2], linePaint);
        canvas.drawLine(xLeft + 60f, inputs[2], xRight - 60f, inputs[0], linePaint);

        canvas.drawLine(xLeft + 60f, inputs[1], xRight - 60f, inputs[3], linePaint);
        canvas.drawLine(xLeft + 60f, inputs[3], xRight - 60f, inputs[1], linePaint);

        textPaint.setColor(Color.parseColor("#FDE68A"));
        textPaint.setTextSize(18f);
        canvas.drawText("Twiddle Factors W_4^k (O(N log N))", cx, 325f, textPaint);
    }
}
