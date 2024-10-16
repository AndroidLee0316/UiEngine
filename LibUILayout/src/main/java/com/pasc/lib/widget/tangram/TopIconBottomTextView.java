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
public class TopIconBottomTextView extends BaseCardView {
    TextView mTitle;
    ImageView mIcon;
    View mRedDot;


    public TopIconBottomTextView(Context context) {
        super(context);
    }

    @Override
    protected void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.mine_top_img_bellow_tx, this);
        this.mTitle = getViewById(R.id.tv_title);
        this.mRedDot = getViewById(R.id.iv_red_dot);
        this.mIcon = getViewById(R.id.icon);
    }
}
