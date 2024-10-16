package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Copyright (C) 2016 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author chendaixi947
 * @version 1.0
 * @date 2018/10/20
 * 水平分割线
 */
public class MineHorizontalDividerView extends BaseCardView {
    View mHorizontalDivider;

    public MineHorizontalDividerView(Context context) {
        super(context);
    }

    @Override
    protected void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.mine_horizontal_divider, this);
        mHorizontalDivider = getViewById(R.id.horizontal_divider);
    }
}
