package com.pasc.business.workspace;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/*
 * Copyright (C) 2016 pasc Licensed under the Apache License, Version 2.0 (the "License");
 * @author 上勇
 * @date 2018-07-04
 * @des 模板首页加载fragment异常的提示fragment
 * @version v1.0
 * @modify On 2018-06-28 by author for reason ...
 */
public class ToastFragment extends Fragment {
    private TextView mToastTextView;
    private CharSequence mTtext = null;

    // 系统会在创建片段时调用此方法。您应该在实现内初始化您想在片段暂停或停止后恢复时保留的必需片段组件。
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 系统会在片段首次绘制其用户界面时调用此方法。 要想为您的片段绘制 UI，
    // 您从此方法中返回的 View 必须是片段布局的根视图。如果片段未提供 UI，您可以返回 null。
    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.workspace_fragment_toast, container, false);

        mToastTextView = (TextView) rootView.findViewById(R.id.show_messsage_tv);
        if (mToastTextView != null) {
            mToastTextView.setText(mTtext);
        }

        return rootView;
    }

    public void setToastMsg(CharSequence text) {
        mTtext = text;
        if (mToastTextView != null) {
            mToastTextView.setText(mTtext);
        }
    }
}
