package com.dsavisualizer.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * Custom dropdown row so the selector can show a title, subtitle, badge, and difficulty.
 */
public class AlgorithmOptionAdapter extends ArrayAdapter<AlgorithmOption> {

    private final LayoutInflater inflater;

    public AlgorithmOptionAdapter(@NonNull Context context, @NonNull List<AlgorithmOption> objects) {
        super(context, 0, objects);
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return bindView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return bindView(position, convertView, parent);
    }

    private View bindView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_algorithm_option, parent, false);
        }

        AlgorithmOption option = getItem(position);
        if (option == null) {
            return view;
        }

        TextView badge = view.findViewById(R.id.option_badge);
        TextView title = view.findViewById(R.id.option_title);
        TextView subtitle = view.findViewById(R.id.option_subtitle);
        TextView difficulty = view.findViewById(R.id.option_difficulty);

        badge.setText(option.getBadgeText());
        title.setText(option.title);
        subtitle.setText(option.subtitle);
        difficulty.setText(option.difficulty);
        return view;
    }
}
