package com.dsavisualizer.app.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.dsavisualizer.app.ui.fragment.AlgorithmFragment;
import com.dsavisualizer.app.ui.fragment.CodeFragment;
import com.dsavisualizer.app.ui.fragment.PracticeFragment;
import com.dsavisualizer.app.ui.fragment.VisualizationFragment;

public class DetailPagerAdapter extends FragmentStateAdapter {

    private final String techniqueId;

    public DetailPagerAdapter(@NonNull FragmentActivity fragmentActivity, String techniqueId) {
        super(fragmentActivity);
        this.techniqueId = techniqueId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return AlgorithmFragment.newInstance(techniqueId);
        }
        if (position == 1) {
            return VisualizationFragment.newInstance(techniqueId);
        }
        if (position == 2) {
            return PracticeFragment.newInstance(techniqueId);
        }
        return CodeFragment.newInstance(techniqueId);
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
