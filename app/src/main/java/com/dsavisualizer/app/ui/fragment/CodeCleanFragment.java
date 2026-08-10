package com.dsavisualizer.app.ui.fragment;

public class CodeCleanFragment extends TextContentFragment {

    public static CodeCleanFragment newInstance(String techniqueId) {
        CodeCleanFragment fragment = new CodeCleanFragment();
        fragment.setArguments(createArgs(techniqueId, CONTENT_TYPE_CODE, false));
        return fragment;
    }
}
