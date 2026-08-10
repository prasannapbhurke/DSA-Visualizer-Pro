package com.dsavisualizer.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.PriorityQueue;

public class StructureView extends View {

    private final LinkedList<Integer> list = new LinkedList<>();
    private final PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

    private final Paint boxPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint accentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private String currentMode = "Stack";
    private int[] hashTable = new int[0];
    private int activeHashIndex = -1;

    public StructureView(Context context) {
        super(context);
        init();
    }

    public StructureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(30f);
        textPaint.setTextAlign(Paint.Align.CENTER);

        accentPaint.setStyle(Paint.Style.STROKE);
        accentPaint.setStrokeWidth(4f);
        accentPaint.setColor(Color.parseColor("#38BDF8"));
    }

    public void setMode(String mode) {
        currentMode = mode;
        invalidate();
    }

    public void clear() {
        list.clear();
        priorityQueue.clear();
        hashTable = new int[0];
        activeHashIndex = -1;
        invalidate();
    }

    public void push(int value) {
        list.addFirst(value);
        postInvalidate();
    }

    public void pop() {
        if (!list.isEmpty()) {
            list.removeFirst();
            postInvalidate();
        }
    }

    public void enqueue(int value) {
        if (currentMode.equals("Priority Queue")) {
            priorityQueue.add(value);
            syncFromPriorityQueue();
        } else if (currentMode.equals("Circular Queue")) {
            if (list.size() < 8) {
                list.addLast(value);
            }
        } else {
            list.addLast(value);
        }

        postInvalidate();
    }

    public void dequeue() {
        if (currentMode.equals("Priority Queue")) {
            if (!priorityQueue.isEmpty()) {
                priorityQueue.poll();
                syncFromPriorityQueue();
            }
        } else if (!list.isEmpty()) {
            list.removeFirst();
        }

        postInvalidate();
    }

    public void addFirst(int value) {
        list.addFirst(value);
        postInvalidate();
    }

    public void addLast(int value) {
        list.addLast(value);
        postInvalidate();
    }

    public void addAt(int position, int value) {
        if (position >= 0 && position <= list.size()) {
            list.add(position, value);
            postInvalidate();
        }
    }

    public void removeFirst() {
        if (!list.isEmpty()) {
            list.removeFirst();
            postInvalidate();
        }
    }

    public void removeLast() {
        if (!list.isEmpty()) {
            list.removeLast();
            postInvalidate();
        }
    }

    public void startHashingDemo(
        int[] values,
        AlgorithmViewModel viewModel,
        GraphView.AlgorithmSleeper sleeper
    ) {
        currentMode = "Hashing";
        hashTable = new int[Math.max(7, values.length + 2)];
        for (int i = 0; i < hashTable.length; i++) {
            hashTable[i] = Integer.MIN_VALUE;
        }

        viewModel.getExecutor().execute(() -> {
            for (int value : values) {
                int home = Math.floorMod(value, hashTable.length);
                int index = home;

                while (hashTable[index] != Integer.MIN_VALUE) {
                    activeHashIndex = index;
                    viewModel.updateState(
                        1,
                        "Collision for " + value + " at slot " + index + ", probe next"
                    );
                    postInvalidate();
                    sleeper.sleep(650);
                    index = (index + 1) % hashTable.length;
                }

                hashTable[index] = value;
                activeHashIndex = index;
                viewModel.updateState(
                    2,
                    "Insert " + value + " into slot " + index + " (home = " + home + ")"
                );
                postInvalidate();
                sleeper.sleep(800);
            }

            activeHashIndex = -1;
            viewModel.updateState(-1, "Hash table built with linear probing");
            postInvalidate();
            viewModel.setRunning(false);
        });
    }

    private void syncFromPriorityQueue() {
        list.clear();
        PriorityQueue<Integer> temp = new PriorityQueue<>(priorityQueue);
        while (!temp.isEmpty()) {
            list.add(temp.poll());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (currentMode.equals("Hashing") && hashTable.length > 0) {
            drawHashTable(canvas);
            return;
        }

        if (list.isEmpty()) {
            return;
        }

        drawLinearStructure(canvas);
    }

    private void drawLinearStructure(Canvas canvas) {
        float centerX = getWidth() / 2f;
        float startY = 100f;
        float boxSize = 80f;
        float spacing = 20f;

        for (int i = 0; i < list.size(); i++) {
            float x = centerX;
            float y = startY + i * (boxSize + spacing);

            if (currentMode.contains("Queue") || currentMode.contains("Linked List") || currentMode.contains("Deque")) {
                if (currentMode.equals("Circular Queue")) {
                    double angle = (2 * Math.PI * i) / 8;
                    float radius = 160f;
                    x = centerX + (float) (radius * Math.cos(angle));
                    y = getHeight() / 2f + (float) (radius * Math.sin(angle));
                } else {
                    x = 100f + i * (boxSize + spacing + 40f);
                    y = getHeight() / 2f;
                }
            }

            boxPaint.setStyle(Paint.Style.FILL);
            boxPaint.setColor(Color.parseColor("#1E293B"));
            canvas.drawRoundRect(x - boxSize / 2f, y - boxSize / 2f, x + boxSize / 2f, y + boxSize / 2f, 12f, 12f, boxPaint);
            canvas.drawRoundRect(x - boxSize / 2f, y - boxSize / 2f, x + boxSize / 2f, y + boxSize / 2f, 12f, 12f, accentPaint);
            canvas.drawText(String.valueOf(list.get(i)), x, y + 10f, textPaint);

            if (currentMode.contains("Linked List") && i < list.size() - 1) {
                float nextX = 100f + (i + 1) * (boxSize + spacing + 40f);
                canvas.drawLine(x + boxSize / 2f, y, nextX - boxSize / 2f, y, accentPaint);
                canvas.drawLine(nextX - boxSize / 2f, y, nextX - boxSize / 2f - 10f, y - 10f, accentPaint);
                canvas.drawLine(nextX - boxSize / 2f, y, nextX - boxSize / 2f - 10f, y + 10f, accentPaint);
            }
        }
    }

    private void drawHashTable(Canvas canvas) {
        float boxWidth = getWidth() / (float) hashTable.length;
        float top = getHeight() / 2f - 70f;
        float bottom = getHeight() / 2f + 10f;

        Paint labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        labelPaint.setColor(Color.parseColor("#CBD5E1"));
        labelPaint.setTextAlign(Paint.Align.CENTER);
        labelPaint.setTextSize(24f);

        for (int i = 0; i < hashTable.length; i++) {
            float left = i * boxWidth + 8f;
            float right = left + boxWidth - 16f;

            if (i == activeHashIndex) {
                boxPaint.setColor(Color.parseColor("#F59E0B"));
            } else {
                boxPaint.setColor(Color.parseColor("#1E293B"));
            }

            boxPaint.setStyle(Paint.Style.FILL);
            canvas.drawRoundRect(left, top, right, bottom, 12f, 12f, boxPaint);
            canvas.drawRoundRect(left, top, right, bottom, 12f, 12f, accentPaint);

            String value = hashTable[i] == Integer.MIN_VALUE ? "-" : String.valueOf(hashTable[i]);
            canvas.drawText(value, (left + right) / 2f, top + 48f, textPaint);
            canvas.drawText(String.valueOf(i), (left + right) / 2f, bottom + 28f, labelPaint);
        }
    }
}
