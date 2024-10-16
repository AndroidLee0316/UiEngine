package com.pasc.pascuiengine;

import com.pasc.business.workspace.MainPageFragment;

public class MyMainPageFragment extends MainPageFragment {

    @Override
    protected boolean isPullToRefreshEnable() {
        return true;
    }

    @Override public String obtainSearchHint() {
        return null;
    }
}
