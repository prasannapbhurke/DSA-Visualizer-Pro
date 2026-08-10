package com.dsavisualizer.app.ui.fragment;

public class CodeFragment extends TextContentFragment {

    public static CodeFragment newInstance(String techniqueId) {
        CodeFragment fragment = new CodeFragment();
        fragment.setArguments(createArgs(techniqueId, CONTENT_TYPE_CODE));
        return fragment;
    }
}
