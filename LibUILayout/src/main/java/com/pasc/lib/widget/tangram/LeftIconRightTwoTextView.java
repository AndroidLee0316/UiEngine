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
 * 左边icon图标，右边两行文字组件
 */
public class LeftIconRightTwoTextView extends BaseCardView {
    TextView mTitle;
    TextView mIntro;
    ImageView mIcon;
    View mRightDivider;


    public LeftIconRightTwoTextView(Context context) {
        super(context);
    }

    @Override
    protected void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.mine_view_left_icon_two_right_text, this);
        this.mTitle = getViewById(R.id.title);
        this.mIntro = getViewById(R.id.intro);
        this.mIcon = getViewById(R.id.icon);
        this.mRightDivider = getViewById(R.id.right_divider);
    }
}
