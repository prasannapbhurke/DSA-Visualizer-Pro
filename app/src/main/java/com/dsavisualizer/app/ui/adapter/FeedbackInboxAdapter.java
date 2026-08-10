package com.dsavisualizer.app.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dsavisualizer.app.R;
import com.dsavisualizer.app.model.FeedbackInboxItem;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class FeedbackInboxAdapter extends RecyclerView.Adapter<FeedbackInboxAdapter.FeedbackViewHolder> {
    private final List<FeedbackInboxItem> items = new ArrayList<>();
    private final OnReviewClickListener reviewClickListener;
    private final OnDeleteClickListener deleteClickListener;

    public FeedbackInboxAdapter() {
        this(null, null);
    }

    public FeedbackInboxAdapter(OnReviewClickListener reviewClickListener) {
        this(reviewClickListener, null);
    }

    public FeedbackInboxAdapter(OnReviewClickListener reviewClickListener, OnDeleteClickListener deleteClickListener) {
        this.reviewClickListener = reviewClickListener;
        this.deleteClickListener = deleteClickListener;
    }

    public void submitList(@NonNull List<FeedbackInboxItem> nextItems) {
        items.clear();
        items.addAll(nextItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback_inbox, parent, false);
        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        FeedbackInboxItem item = items.get(position);
        holder.senderText.setText(holder.itemView.getContext().getString(
            R.string.feedback_inbox_sender,
            item.userDisplayName.trim().isEmpty()
                ? holder.itemView.getContext().getString(R.string.classroom_student_default_name)
                : item.userDisplayName
        ));
        holder.metaText.setText(holder.itemView.getContext().getString(
            R.string.feedback_inbox_meta,
            item.userEmail.trim().isEmpty()
                ? holder.itemView.getContext().getString(R.string.owner_users_email_missing)
                : item.userEmail,
            item.userRole.trim().isEmpty() ? "user" : item.userRole,
            item.createdAt.trim().isEmpty()
                ? holder.itemView.getContext().getString(R.string.owner_users_meta_unknown)
                : item.createdAt
        ));
        holder.typeText.setText(holder.itemView.getContext().getString(
            R.string.feedback_inbox_type,
            item.type.trim().isEmpty() ? "general" : item.type
        ));
        holder.messageText.setText(item.message);
        holder.contextText.setText(holder.itemView.getContext().getString(
            R.string.feedback_inbox_context,
            item.techniqueId.trim().isEmpty() ? "-" : item.techniqueId,
            item.screen.trim().isEmpty() ? "-" : item.screen,
            item.difficulty.trim().isEmpty() ? "-" : item.difficulty
        ));
        if (item.className.trim().isEmpty()) {
            holder.classText.setVisibility(View.GONE);
        } else {
            holder.classText.setVisibility(View.VISIBLE);
            holder.classText.setText(holder.itemView.getContext().getString(
                R.string.teacher_feedback_class_line,
                item.className
            ));
        }
        boolean reviewed = !item.reviewedAt.trim().isEmpty();
        holder.reviewStatusText.setText(holder.itemView.getContext().getString(
            reviewed ? R.string.teacher_feedback_reviewed_status : R.string.teacher_feedback_pending_status,
            reviewed
                ? item.reviewedAt
                : holder.itemView.getContext().getString(R.string.teacher_feedback_pending_value)
        ));
        if (reviewClickListener == null) {
            holder.reviewButton.setVisibility(View.GONE);
            holder.reviewButton.setOnClickListener(null);
        } else {
            holder.reviewButton.setVisibility(reviewed ? View.GONE : View.VISIBLE);
            holder.reviewButton.setOnClickListener(v -> reviewClickListener.onReviewClick(item));
        }
        if (deleteClickListener == null) {
            holder.deleteButton.setVisibility(View.GONE);
            holder.deleteButton.setOnClickListener(null);
        } else {
            holder.deleteButton.setVisibility(View.VISIBLE);
            holder.deleteButton.setOnClickListener(v -> deleteClickListener.onDeleteClick(item));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class FeedbackViewHolder extends RecyclerView.ViewHolder {
        final TextView senderText;
        final TextView metaText;
        final TextView typeText;
        final TextView messageText;
        final TextView contextText;
        final TextView classText;
        final TextView reviewStatusText;
        final MaterialButton reviewButton;
        final MaterialButton deleteButton;

        FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            senderText = itemView.findViewById(R.id.feedback_item_sender_text);
            metaText = itemView.findViewById(R.id.feedback_item_meta_text);
            typeText = itemView.findViewById(R.id.feedback_item_type_text);
            messageText = itemView.findViewById(R.id.feedback_item_message_text);
            contextText = itemView.findViewById(R.id.feedback_item_context_text);
            classText = itemView.findViewById(R.id.feedback_item_class_text);
            reviewStatusText = itemView.findViewById(R.id.feedback_item_review_status_text);
            reviewButton = itemView.findViewById(R.id.feedback_item_review_button);
            deleteButton = itemView.findViewById(R.id.feedback_item_delete_button);
        }
    }

    public interface OnReviewClickListener {
        void onReviewClick(FeedbackInboxItem item);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(FeedbackInboxItem item);
    }
}
