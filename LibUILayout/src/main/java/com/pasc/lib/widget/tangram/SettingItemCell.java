package com.pasc.lib.widget.tangram;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

/**
 * Copyright (C) 2016 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author chendaixi947
 * @version 1.0
 * @date 2018/10/20
 */
public class SettingItemCell extends BaseCardCell<SettingItemView> {

    private SettingItemView settingItemView;
    public static final String TITLE = "title";
    public static final String ICON = "icon";
    public static final String SHOW_BOTTOM_DIVIDER = "showBottomDivider";
    public static final String DESC = "desc";
    public static final String ARROW = "arrow";


    private String updateTitle;
    private String updateDesc;
    private int updateArrow;
    private int updateDivider;


    @Override
    protected void bindViewData(@NonNull SettingItemView view) {
        super.bindViewData(view);
        this.settingItemView = view;
        setTextAndStyle(view, view.mTitle, TITLE);
        setImageAndStyle(view, view.mRightArrow, ARROW);
        setTextAndStyle(view, view.mRightDesc, DESC);

        String iconUrl = getString(extras, ICON + "Url");
        if (TextUtils.isEmpty(iconUrl)) {
            view.mLeftImg.setVisibility(View.GONE);
        } else {
            view.mLeftImg.setVisibility(View.VISIBLE);
            setImageAndStyle(view, view.mLeftImg, ICON);
        }
        boolean isShowBottomDivider = getBoolean(extras, SHOW_BOTTOM_DIVIDER);
        view.mBottomDivider.setVisibility(isShowBottomDivider ? View.VISIBLE : View.GONE);

        if (!TextUtils.isEmpty(updateTitle)){
            updateTitle(updateTitle);
        }

        if (!TextUtils.isEmpty(updateDesc)){
            updateDesc(updateDesc);
        }
    }

    public void updateTitle(String title){
        updateTitle = title;
        if (settingItemView.mTitle != null){
            settingItemView.mTitle.setText(title);
        }
    }

    public void updateDesc(String desc){
        updateDesc = desc;
        if (settingItemView.mRightDesc != null){
            settingItemView.mRightDesc.setText(desc);
        }
    }

    public void updateArrow(int visiable){
        updateArrow = visiable;
        if (settingItemView.mRightArrow != null){
            settingItemView.mRightArrow.setVisibility(visiable);
        }
    }

    public void updateDivider(int visiable){
        updateDivider = visiable;
        if (settingItemView.mBottomDivider != null){
            settingItemView.mBottomDivider.setVisibility(visiable);
        }
    }

}
