package com.dsavisualizer.app.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dsavisualizer.app.R;
import com.dsavisualizer.app.model.TechniqueItem;
import com.dsavisualizer.app.ui.HapticFeedbackHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TechniqueAdapter extends RecyclerView.Adapter<TechniqueAdapter.TechniqueViewHolder> {

    public interface OnTechniqueClickListener {
        void onTechniqueClick(TechniqueItem item);
    }

    private final List<TechniqueItem> items = new ArrayList<>();
    private final OnTechniqueClickListener listener;
    private final Set<String> weakTopicIds = new HashSet<>();

    public TechniqueAdapter(OnTechniqueClickListener listener) {
        this.listener = listener;
        setHasStableIds(true);
    }

    public void submitList(List<TechniqueItem> newItems) {
        if (hasSameItems(newItems)) {
            return;
        }
        items.clear();
        if (newItems != null) {
            items.addAll(newItems);
        }
        notifyDataSetChanged();
    }

    public void setWeakTopicIds(Set<String> weakTopics) {
        weakTopicIds.clear();
        if (weakTopics != null) {
            weakTopicIds.addAll(weakTopics);
        }
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= items.size()) {
            return RecyclerView.NO_ID;
        }
        TechniqueItem item = items.get(position);
        return item.id == null ? RecyclerView.NO_ID : item.id.hashCode();
    }

    @NonNull
    @Override
    public TechniqueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_technique, parent, false);
        return new TechniqueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TechniqueViewHolder holder, int position) {
        TechniqueItem item = items.get(position);
        holder.title.setText(item.title);
        holder.subtitle.setText(item.subtitle);
        holder.difficulty.setText(item.difficulty);
        boolean isWeakTopic = weakTopicIds.contains(item.id);
        holder.weakBadge.setVisibility(isWeakTopic ? View.VISIBLE : View.GONE);
        holder.itemView.setContentDescription(
            holder.itemView.getContext().getString(
                R.string.a11y_technique_card,
                item.title,
                item.difficulty,
                item.subtitle
            )
        );
        holder.itemView.setOnClickListener(view -> {
            HapticFeedbackHelper.performSelection(view);
            listener.onTechniqueClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class TechniqueViewHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final TextView subtitle;
        final TextView difficulty;
        final TextView weakBadge;

        TechniqueViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.technique_title);
            subtitle = itemView.findViewById(R.id.technique_subtitle);
            difficulty = itemView.findViewById(R.id.technique_difficulty);
            weakBadge = itemView.findViewById(R.id.technique_weak_badge);
        }
    }

    private boolean hasSameItems(List<TechniqueItem> newItems) {
        if (newItems == null || newItems.size() != items.size()) {
            return false;
        }
        for (int i = 0; i < items.size(); i++) {
            TechniqueItem current = items.get(i);
            TechniqueItem incoming = newItems.get(i);
            if (incoming == null) {
                return false;
            }
            if (!same(current.id, incoming.id)
                || !same(current.categoryId, incoming.categoryId)
                || !same(current.title, incoming.title)
                || !same(current.subtitle, incoming.subtitle)
                || !same(current.difficulty, incoming.difficulty)) {
                return false;
            }
        }
        return true;
    }

    private boolean same(String left, String right) {
        return left == null ? right == null : left.equals(right);
    }
}
