package com.dsavisualizer.app.ui.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dsavisualizer.app.R;
import com.dsavisualizer.app.model.CategoryItem;
import com.dsavisualizer.app.ui.HapticFeedbackHelper;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    public interface OnCategoryClickListener {
        void onCategoryClick(CategoryItem item);
    }

    private final List<CategoryItem> items = new ArrayList<>();
    private final OnCategoryClickListener listener;

    public CategoryAdapter(OnCategoryClickListener listener) {
        this.listener = listener;
        setHasStableIds(true);
    }

    public void submitList(List<CategoryItem> newItems) {
        if (hasSameItems(newItems)) {
            return;
        }
        items.clear();
        if (newItems != null) {
            items.addAll(newItems);
        }
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= items.size()) {
            return RecyclerView.NO_ID;
        }
        CategoryItem item = items.get(position);
        return item.id == null ? RecyclerView.NO_ID : item.id.hashCode();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_card, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryItem item = items.get(position);
        String safeTitle = item.title == null ? "" : item.title.trim();
        String safeSubtitle = item.subtitle == null ? "" : item.subtitle.trim();
        int accentColor = parseAccentColor(item.accentHex);

        holder.title.setText(safeTitle);
        holder.subtitle.setText(safeSubtitle);
        holder.badge.setText(safeTitle.isEmpty() ? "?" : safeTitle.substring(0, 1));
        holder.badge.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        holder.card.setStrokeColor(accentColor);
        holder.badge.setBackground(createBadgeBackground(accentColor));
        holder.itemView.setContentDescription(
            holder.itemView.getContext().getString(R.string.a11y_category_card, safeTitle, safeSubtitle)
        );
        holder.itemView.setOnClickListener(view -> {
            HapticFeedbackHelper.performSelection(view);
            listener.onCategoryClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        final MaterialCardView card;
        final TextView badge;
        final TextView title;
        final TextView subtitle;

        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.category_card);
            badge = itemView.findViewById(R.id.category_badge);
            title = itemView.findViewById(R.id.category_title);
            subtitle = itemView.findViewById(R.id.category_subtitle);
        }
    }

    private int parseAccentColor(String accentHex) {
        try {
            return Color.parseColor(accentHex);
        } catch (IllegalArgumentException | NullPointerException error) {
            return Color.parseColor("#60A5FA");
        }
    }

    private GradientDrawable createBadgeBackground(int accentColor) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(accentColor);
        return drawable;
    }

    private boolean hasSameItems(List<CategoryItem> newItems) {
        if (newItems == null || newItems.size() != items.size()) {
            return false;
        }
        for (int i = 0; i < items.size(); i++) {
            CategoryItem current = items.get(i);
            CategoryItem incoming = newItems.get(i);
            if (incoming == null) {
                return false;
            }
            if (!same(current.id, incoming.id)
                || !same(current.title, incoming.title)
                || !same(current.subtitle, incoming.subtitle)
                || !same(current.accentHex, incoming.accentHex)) {
                return false;
            }
        }
        return true;
    }

    private boolean same(String left, String right) {
        return left == null ? right == null : left.equals(right);
    }
}
