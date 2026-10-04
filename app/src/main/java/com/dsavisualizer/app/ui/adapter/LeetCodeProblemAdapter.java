package com.dsavisualizer.app.ui.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dsavisualizer.app.R;
import com.dsavisualizer.app.model.LeetCodeProblemModel;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for rendering LeetCode Master 2000 problems with smooth DiffUtil updates.
 */
public class LeetCodeProblemAdapter extends RecyclerView.Adapter<LeetCodeProblemAdapter.ProblemViewHolder> {

    public interface OnProblemActionListener {
        void onProblemClick(LeetCodeProblemModel problem);
        void onSolveToggle(LeetCodeProblemModel problem, int position);
        void onBookmarkToggle(LeetCodeProblemModel problem, int position);
    }

    private final List<LeetCodeProblemModel> items = new ArrayList<>();
    private final OnProblemActionListener listener;

    public LeetCodeProblemAdapter(OnProblemActionListener listener) {
        this.listener = listener;
    }

    public void submitList(List<LeetCodeProblemModel> newItems) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return items.size();
            }

            @Override
            public int getNewListSize() {
                return newItems != null ? newItems.size() : 0;
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return items.get(oldItemPosition).order == newItems.get(newItemPosition).order;
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                LeetCodeProblemModel oldItem = items.get(oldItemPosition);
                LeetCodeProblemModel newItem = newItems.get(newItemPosition);
                return oldItem.isSolved == newItem.isSolved
                        && oldItem.isBookmarked == newItem.isBookmarked
                        && oldItem.title.equals(newItem.title);
            }
        });

        items.clear();
        if (newItems != null) {
            items.addAll(newItems);
        }
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public ProblemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_leetcode_problem, parent, false);
        return new ProblemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProblemViewHolder holder, int position) {
        LeetCodeProblemModel item = items.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ProblemViewHolder extends RecyclerView.ViewHolder {

        private final MaterialCardView card;
        private final ImageButton solveButton;
        private final TextView orderBadge;
        private final TextView numberBadge;
        private final TextView difficultyBadge;
        private final ImageButton bookmarkButton;
        private final TextView titleText;
        private final TextView patternTag;
        private final TextView companyTag;

        ProblemViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.leetcode_card);
            solveButton = itemView.findViewById(R.id.leetcode_solve_checkbox);
            orderBadge = itemView.findViewById(R.id.leetcode_order_badge);
            numberBadge = itemView.findViewById(R.id.leetcode_number_badge);
            difficultyBadge = itemView.findViewById(R.id.leetcode_difficulty_badge);
            bookmarkButton = itemView.findViewById(R.id.leetcode_bookmark_button);
            titleText = itemView.findViewById(R.id.leetcode_problem_title);
            patternTag = itemView.findViewById(R.id.leetcode_pattern_tag);
            companyTag = itemView.findViewById(R.id.leetcode_company_tag);
        }

        void bind(LeetCodeProblemModel item, OnProblemActionListener listener) {
            orderBadge.setText("Part " + item.part + " • #" + item.order);
            numberBadge.setText("LC #" + item.lcNumber);
            titleText.setText(item.title);
            patternTag.setText(item.pattern);

            if (item.companies != null && !item.companies.isEmpty()) {
                companyTag.setVisibility(View.VISIBLE);
                companyTag.setText("🏢 " + item.companies);
            } else {
                companyTag.setVisibility(View.GONE);
            }

            // Difficulty color
            difficultyBadge.setText(item.difficulty);
            String diff = item.difficulty.toLowerCase();
            if (diff.contains("easy")) {
                difficultyBadge.setTextColor(Color.parseColor("#4ADE80")); // Emerald
            } else if (diff.contains("hard")) {
                difficultyBadge.setTextColor(Color.parseColor("#FB7185")); // Rose
            } else {
                difficultyBadge.setTextColor(Color.parseColor("#FBBF24")); // Amber
            }

            // Solved checkbox state
            if (item.isSolved) {
                solveButton.setImageResource(R.drawable.ic_leetcode_check);
                titleText.setAlpha(0.65f);
            } else {
                solveButton.setImageResource(R.drawable.ic_leetcode_uncheck);
                titleText.setAlpha(1.0f);
            }

            // Bookmark state
            if (item.isBookmarked) {
                bookmarkButton.setImageResource(R.drawable.ic_leetcode_star);
            } else {
                bookmarkButton.setImageResource(R.drawable.ic_leetcode_star_border);
            }

            card.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onProblemClick(item);
                }
            });

            solveButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onSolveToggle(item, getBindingAdapterPosition());
                }
            });

            bookmarkButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onBookmarkToggle(item, getBindingAdapterPosition());
                }
            });
        }
    }
}
