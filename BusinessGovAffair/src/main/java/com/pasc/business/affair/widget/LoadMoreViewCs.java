package com.pasc.business.affair.widget;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.pasc.lib.affair.R;

/**
 * 功能：
 * <p>
 * created by zoujianbo345
 * data : 2018/10/18
 */
public class LoadMoreViewCs extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.layout_loadmore;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.ll_lodding;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.view_load_fail;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }


}
