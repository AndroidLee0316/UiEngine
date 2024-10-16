package com.pasc.lib.widget.tangram;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Copyright (C) 2016 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author chendaixi947
 * @version 1.0
 * @date 2018/10/20
 * 左边icon图标，右边两行文字组件
 */
public class LeftIconRightTwoTextCell extends BaseCardCell<LeftIconRightTwoTextView> {
    private static final String TITLE = "title";
    private static final String DESC = "desc";
    private static final String ICON = "icon";
    private static final String SHOW_RIGHT_DIVIDER = "showRightDivider";

    @Override
    protected void bindViewData(@NonNull LeftIconRightTwoTextView view) {
        super.bindViewData(view);
        setImageAndStyle(view, view.mIcon, ICON);
        setTextAndStyle(view, view.mIntro, DESC);
        setTextAndStyle(view, view.mTitle, TITLE);
        boolean isShowRightDivider = getBoolean(extras, SHOW_RIGHT_DIVIDER);
        view.mRightDivider.setVisibility(isShowRightDivider ? View.VISIBLE : View.GONE);
    }
}
