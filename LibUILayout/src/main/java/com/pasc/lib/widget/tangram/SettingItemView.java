package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Copyright (C) 2016 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author chendaixi947
 * @version 1.0
 * @date 2018/10/20
 */
public class SettingItemView extends BaseCardView {
    TextView mTitle;
    View mBottomDivider;
    ImageView mLeftImg;
    ImageView mRightArrow;
    TextView mRightDesc;


    public SettingItemView(Context context) {
        super(context);
    }

    @Override
    protected void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.mine_setting_item, this);
        this.mTitle = getViewById(R.id.tv_left_desc);
        this.mBottomDivider = getViewById(R.id.bottom_divider);
        this.mLeftImg = getViewById(R.id.iv_left_icon);
        this.mRightArrow = getViewById(R.id.iv_right_arrow);
        this.mRightDesc = getViewById(R.id.iv_right_text);
    }
}
