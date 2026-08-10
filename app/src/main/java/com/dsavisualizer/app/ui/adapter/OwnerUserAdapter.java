package com.dsavisualizer.app.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dsavisualizer.app.R;
import com.dsavisualizer.app.model.ManagedUser;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class OwnerUserAdapter extends RecyclerView.Adapter<OwnerUserAdapter.UserViewHolder> {

    public interface UserActionListener {
        void onToggleRole(@NonNull ManagedUser user);
    }

    private final List<ManagedUser> users = new ArrayList<>();
    private final UserActionListener listener;
    private String pendingUserId = "";

    public OwnerUserAdapter(@NonNull UserActionListener listener) {
        this.listener = listener;
    }

    public void submitList(@NonNull List<ManagedUser> nextUsers) {
        users.clear();
        users.addAll(nextUsers);
        notifyDataSetChanged();
    }

    public void setPendingUserId(String pendingUserId) {
        this.pendingUserId = pendingUserId == null ? "" : pendingUserId;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_owner_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        ManagedUser user = users.get(position);
        holder.nameText.setText(user.getPrimaryLabel());
        holder.emailText.setText(user.email.trim().isEmpty()
            ? holder.itemView.getContext().getString(R.string.owner_users_email_missing)
            : user.email);
        holder.roleText.setText(holder.itemView.getContext().getString(
            R.string.owner_users_role_label,
            user.role.trim().isEmpty() ? "student" : user.role
        ));

        String detailText = holder.itemView.getContext().getString(
            R.string.owner_users_meta_label,
            user.createdAt.trim().isEmpty() ? holder.itemView.getContext().getString(R.string.owner_users_meta_unknown) : user.createdAt,
            user.lastSeenAt.trim().isEmpty() ? holder.itemView.getContext().getString(R.string.owner_users_meta_unknown) : user.lastSeenAt
        );
        holder.metaText.setText(detailText);

        boolean isOwner = "owner".equalsIgnoreCase(user.role);
        boolean isPending = user.id.equals(pendingUserId);
        holder.actionButton.setEnabled(!isPending && !isOwner);
        holder.actionButton.setVisibility(isOwner ? View.GONE : View.VISIBLE);
        if (!isOwner) {
            holder.actionButton.setText(
                "teacher".equalsIgnoreCase(user.role)
                    ? R.string.owner_users_demote_button
                    : R.string.owner_users_promote_button
            );
            holder.actionButton.setOnClickListener(v -> listener.onToggleRole(user));
        } else {
            holder.actionButton.setOnClickListener(null);
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        final TextView nameText;
        final TextView emailText;
        final TextView roleText;
        final TextView metaText;
        final MaterialButton actionButton;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.owner_user_name_text);
            emailText = itemView.findViewById(R.id.owner_user_email_text);
            roleText = itemView.findViewById(R.id.owner_user_role_text);
            metaText = itemView.findViewById(R.id.owner_user_meta_text);
            actionButton = itemView.findViewById(R.id.owner_user_action_button);
        }
    }
}
