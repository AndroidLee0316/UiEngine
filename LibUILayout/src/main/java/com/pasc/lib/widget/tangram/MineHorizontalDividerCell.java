package com.pasc.lib.widget.tangram;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.LinearLayout;

import com.pasc.lib.widget.DensityUtils;


/**
 * Copyright (C) 2016 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author chendaixi947
 * @version 1.0
 * @date 2018/10/20
 * 水平分割线，默认高度为 8dp， 默认背景颜色为：#F5F6F9
 */
public class MineHorizontalDividerCell extends BaseCardCell<MineHorizontalDividerView> {
    private static final String PAGE_HEIGHT = "pageHeight";
    private static final String BG_COLOR = "bgColor";

    @Override
    protected void bindViewData(@NonNull MineHorizontalDividerView view) {
        super.bindViewData(view);
        //获取分割线高度，单位为dp
        int pageHeight = getInt(extras, PAGE_HEIGHT);
        if (pageHeight > 0) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.mHorizontalDivider.getLayoutParams();
            layoutParams.height = DensityUtils.dp2px(pageHeight);
            view.mHorizontalDivider.setLayoutParams(layoutParams);
        }

        //获取分割线背景颜色，格式为：#FF0000
        String bgColor = getString(extras, BG_COLOR);
        if (!TextUtils.isEmpty(bgColor) && bgColor.startsWith("#")) {
            view.mHorizontalDivider.setBackgroundColor(Color.parseColor(bgColor));
        }
    }
}
