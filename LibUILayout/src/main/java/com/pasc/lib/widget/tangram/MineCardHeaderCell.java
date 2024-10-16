package com.pasc.lib.widget.tangram;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

/**
 * Copyright (C) 2016 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author chendaixi947
 * @version 1.0
 * @date 2018/10/20
 */
public class MineCardHeaderCell extends BaseCardCell<MineCardHeaderView> {
    private static final String BOLD = "bold";
    private static final String TITLE = "title";
    private static final String ARROW = "arrow";
    private static final String SHOW_ARROW = "showArrow";
    private static final String SHOW_BOTTOM_DIVIDER = "showBottomDivider";
    private static final String TEXT_STYLE = "textStyle";
    private static final String DESC = "desc";

    @Override
    protected void bindViewData(@NonNull MineCardHeaderView view) {
        super.bindViewData(view);
        setTextAndStyle(view, view.mTitle, TITLE);
        setTextAndStyle(view, view.mDesc, DESC);
        setImageAndStyle(view, view.mArrow, ARROW);

        String textStyle = getString(extras, TEXT_STYLE);
        if (!TextUtils.isEmpty(textStyle) && BOLD.equals(textStyle)) {
            //加粗
            view.mTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        } else {
            //常规
            view.mTitle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }

        boolean isShowArrow = getBoolean(extras, SHOW_ARROW);
        boolean isShowBottomDivider = getBoolean(extras, SHOW_BOTTOM_DIVIDER);
        view.mArrow.setVisibility(isShowArrow ? View.VISIBLE : View.GONE);
        view.mBottomDivider.setVisibility(isShowBottomDivider ? View.VISIBLE : View.GONE);
    }
}
