package com.pasc.lib.widget.tangram;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Copyright (C) 2016 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author chendaixi947
 * @version 1.0
 * @date 2018/10/20
 */
public class TopIconBottomTextCell extends BaseCardCell<TopIconBottomTextView> {
    private static final String TITLE = "title";
    private static final String ICON = "icon";
    private static final String SHOW_RED_DOT = "showRedDot";

    @Override
    protected void bindViewData(@NonNull TopIconBottomTextView view) {
        super.bindViewData(view);
        setImageAndStyle(view, view.mIcon, ICON);
        setTextAndStyle(view, view.mTitle, TITLE);
        boolean isShowRedDot = getBoolean(extras, SHOW_RED_DOT);
        view.mRedDot.setVisibility(isShowRedDot ? View.VISIBLE : View.GONE);
    }
}
