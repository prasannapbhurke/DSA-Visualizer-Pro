package com.dsavisualizer.app.ui.fragment;

public class AlgorithmFragment extends TextContentFragment {

    public static AlgorithmFragment newInstance(String techniqueId) {
        AlgorithmFragment fragment = new AlgorithmFragment();
        fragment.setArguments(createArgs(techniqueId, CONTENT_TYPE_ALGORITHM));
        return fragment;
    }
}
