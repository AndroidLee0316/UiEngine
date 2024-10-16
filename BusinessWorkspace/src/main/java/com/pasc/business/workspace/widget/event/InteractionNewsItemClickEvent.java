package com.pasc.business.workspace.widget.event;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pasc.lib.workspace.bean.InteractionNewsBean;

/**
 * 民生资讯某项被点击事件.
 */
public class InteractionNewsItemClickEvent {

    private BaseQuickAdapter adapter;
    private View view;
    private int position;
    private InteractionNewsBean item;

    public InteractionNewsBean getItem() {
        return item;
    }

    public void setItem(InteractionNewsBean item) {
        this.item = item;
    }

    public BaseQuickAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(BaseQuickAdapter adapter) {
        this.adapter = adapter;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
