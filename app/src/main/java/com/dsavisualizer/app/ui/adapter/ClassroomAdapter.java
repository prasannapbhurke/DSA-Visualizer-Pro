package com.dsavisualizer.app.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dsavisualizer.app.R;
import com.dsavisualizer.app.model.ClassroomInfo;

import java.util.ArrayList;
import java.util.List;

public class ClassroomAdapter extends RecyclerView.Adapter<ClassroomAdapter.ClassroomViewHolder> {

    public interface ClassroomClickListener {
        void onClassroomClicked(@NonNull ClassroomInfo classroomInfo);
    }

    private final List<ClassroomInfo> classrooms = new ArrayList<>();
    private final ClassroomClickListener listener;

    public ClassroomAdapter(@NonNull ClassroomClickListener listener) {
        this.listener = listener;
    }

    public void submitList(@NonNull List<ClassroomInfo> nextItems) {
        classrooms.clear();
        classrooms.addAll(nextItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClassroomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_classroom, parent, false);
        return new ClassroomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassroomViewHolder holder, int position) {
        ClassroomInfo classroom = classrooms.get(position);
        holder.titleText.setText(classroom.name);
        holder.descriptionText.setText(classroom.description.trim().isEmpty()
            ? holder.itemView.getContext().getString(R.string.classroom_description_default)
            : classroom.description);
        holder.codeText.setText(holder.itemView.getContext().getString(R.string.classroom_join_code_label, classroom.joinCode));
        holder.metaText.setText(holder.itemView.getContext().getString(
            R.string.classroom_meta_label,
            classroom.teacherName.trim().isEmpty()
                ? holder.itemView.getContext().getString(R.string.classroom_teacher_unknown)
                : classroom.teacherName,
            classroom.studentCount
        ));
        holder.itemView.setOnClickListener(v -> listener.onClassroomClicked(classroom));
    }

    @Override
    public int getItemCount() {
        return classrooms.size();
    }

    static class ClassroomViewHolder extends RecyclerView.ViewHolder {
        final TextView titleText;
        final TextView descriptionText;
        final TextView codeText;
        final TextView metaText;

        ClassroomViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.classroom_item_title_text);
            descriptionText = itemView.findViewById(R.id.classroom_item_description_text);
            codeText = itemView.findViewById(R.id.classroom_item_code_text);
            metaText = itemView.findViewById(R.id.classroom_item_meta_text);
        }
    }
}
