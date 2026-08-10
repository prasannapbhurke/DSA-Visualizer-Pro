package com.dsavisualizer.app.ui.visualization.playback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SnapshotTimeline<T> {

    private int maxSize;
    private final List<T> items = new ArrayList<>();

    public SnapshotTimeline(int maxSize) {
        this.maxSize = Math.max(1, maxSize);
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = Math.max(1, maxSize);
        trimToMaxSize();
    }

    public void clear() {
        items.clear();
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void add(T item) {
        items.add(item);
        trimToMaxSize();
    }

    public T get(int index) {
        return items.get(index);
    }

    public T latest() {
        return items.isEmpty() ? null : items.get(items.size() - 1);
    }

    public T removeLatest() {
        if (items.isEmpty()) {
            return null;
        }
        return items.remove(items.size() - 1);
    }

    public void truncateAfter(int indexInclusive) {
        while (items.size() > indexInclusive + 1) {
            items.remove(items.size() - 1);
        }
    }

    public List<T> asList() {
        return Collections.unmodifiableList(items);
    }

    private void trimToMaxSize() {
        while (items.size() > maxSize) {
            items.remove(0);
        }
    }
}
