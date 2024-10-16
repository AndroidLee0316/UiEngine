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
public class MineCardHeaderView extends BaseCardView {
    TextView mTitle;
    View mBottomDivider;
    TextView mDesc;
    ImageView mArrow;

    public MineCardHeaderView(Context context) {
        super(context);
    }

    @Override
    protected void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.mine_card_header, this);
        mTitle = getViewById(R.id.title);
        mArrow = getViewById(R.id.right_arrow);
        mDesc = getViewById(R.id.desc);
        mBottomDivider = getViewById(R.id.bottom_horizontal_divider);
    }
}
