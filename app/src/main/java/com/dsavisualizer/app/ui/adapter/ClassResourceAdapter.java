package com.dsavisualizer.app.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dsavisualizer.app.R;
import com.dsavisualizer.app.model.ClassResourceItem;

import java.util.ArrayList;
import java.util.List;

public class ClassResourceAdapter extends RecyclerView.Adapter<ClassResourceAdapter.ResourceViewHolder> {
    public interface ResourceClickListener {
        void onResourceClicked(@NonNull ClassResourceItem item);
    }

    private final List<ClassResourceItem> items = new ArrayList<>();
    private final ResourceClickListener listener;

    public ClassResourceAdapter(@NonNull ResourceClickListener listener) {
        this.listener = listener;
    }

    public void submitList(@NonNull List<ClassResourceItem> nextItems) {
        items.clear();
        items.addAll(nextItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ResourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class_resource, parent, false);
        return new ResourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResourceViewHolder holder, int position) {
        ClassResourceItem item = items.get(position);
        holder.titleText.setText(item.title);
        holder.fileText.setText(holder.itemView.getContext().getString(
            R.string.class_resource_file_meta,
            item.fileName,
            item.mimeType,
            item.sizeBytes / 1024
        ));
        holder.metaText.setText(holder.itemView.getContext().getString(
            R.string.class_resource_upload_meta,
            item.type.trim().isEmpty() ? "-" : item.type,
            item.uploaderName.trim().isEmpty()
                ? holder.itemView.getContext().getString(R.string.owner_users_meta_unknown)
                : item.uploaderName,
            item.createdAt.trim().isEmpty()
                ? holder.itemView.getContext().getString(R.string.owner_users_meta_unknown)
                : item.createdAt
        ));
        holder.itemView.setOnClickListener(v -> listener.onResourceClicked(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ResourceViewHolder extends RecyclerView.ViewHolder {
        final TextView titleText;
        final TextView fileText;
        final TextView metaText;

        ResourceViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.class_resource_title_text);
            fileText = itemView.findViewById(R.id.class_resource_file_text);
            metaText = itemView.findViewById(R.id.class_resource_meta_text);
        }
    }
}
