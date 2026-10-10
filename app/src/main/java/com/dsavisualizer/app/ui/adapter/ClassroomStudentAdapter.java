package com.dsavisualizer.app.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dsavisualizer.app.R;
import com.dsavisualizer.app.model.ClassroomStudentSummary;

import java.util.ArrayList;
import java.util.List;

public class ClassroomStudentAdapter extends RecyclerView.Adapter<ClassroomStudentAdapter.StudentViewHolder> {
    public interface StudentClickListener {
        void onStudentClicked(@NonNull ClassroomStudentSummary student);
    }

    private final List<ClassroomStudentSummary> students = new ArrayList<>();
    private final StudentClickListener listener;

    public ClassroomStudentAdapter(@NonNull StudentClickListener listener) {
        this.listener = listener;
    }

    public void submitList(@NonNull List<ClassroomStudentSummary> nextStudents) {
        students.clear();
        students.addAll(nextStudents);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_classroom_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        ClassroomStudentSummary student = students.get(position);

        if (student.rollNumber != null && !student.rollNumber.trim().isEmpty()) {
            holder.rollBadge.setVisibility(View.VISIBLE);
            holder.rollBadge.setText("ROLL: " + student.rollNumber.trim());
        } else {
            holder.rollBadge.setVisibility(View.GONE);
        }

        if (student.studentBatch != null && !student.studentBatch.trim().isEmpty()) {
            holder.batchBadge.setVisibility(View.VISIBLE);
            holder.batchBadge.setText(student.studentBatch.trim());
        } else {
            holder.batchBadge.setVisibility(View.GONE);
        }

        holder.nameText.setText(student.displayName.trim().isEmpty()
            ? holder.itemView.getContext().getString(R.string.classroom_student_default_name)
            : student.displayName);
        holder.emailText.setText(student.email.trim().isEmpty()
            ? holder.itemView.getContext().getString(R.string.owner_users_email_missing)
            : student.email);
        String progressStr = holder.itemView.getContext().getString(
            R.string.classroom_student_progress,
            student.totalTechniquesStarted,
            student.completedTechniques,
            student.totalPracticeSessions,
            student.streakDays
        );
        if (student.totalPracticeSessions > 0) {
            progressStr += " • Avg Quiz: " + student.averageQuizScore + "%";
        }
        holder.progressText.setText(progressStr);
        holder.activityText.setText(holder.itemView.getContext().getString(
            R.string.classroom_student_activity,
            student.joinedAt.trim().isEmpty()
                ? holder.itemView.getContext().getString(R.string.owner_users_meta_unknown)
                : student.joinedAt,
            student.lastActiveAt.trim().isEmpty()
                ? holder.itemView.getContext().getString(R.string.owner_users_meta_unknown)
                : student.lastActiveAt
        ));
        holder.itemView.setOnClickListener(v -> listener.onStudentClicked(student));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        final TextView rollBadge;
        final TextView batchBadge;
        final TextView nameText;
        final TextView emailText;
        final TextView progressText;
        final TextView activityText;

        StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            rollBadge = itemView.findViewById(R.id.classroom_student_roll_badge);
            batchBadge = itemView.findViewById(R.id.classroom_student_batch_badge);
            nameText = itemView.findViewById(R.id.classroom_student_name_text);
            emailText = itemView.findViewById(R.id.classroom_student_email_text);
            progressText = itemView.findViewById(R.id.classroom_student_progress_text);
            activityText = itemView.findViewById(R.id.classroom_student_activity_text);
        }
    }
}
