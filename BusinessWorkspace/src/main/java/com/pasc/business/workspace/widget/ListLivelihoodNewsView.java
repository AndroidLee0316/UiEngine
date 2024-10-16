package com.pasc.business.workspace.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pasc.lib.widget.MeasureRecyclerView;
import com.pasc.business.workspace.R;
import com.pasc.lib.widget.tangram.BaseCardView;

/**
 * 民生资讯列表视图
 */
public class ListLivelihoodNewsView extends BaseCardView {

    public MeasureRecyclerView mrvView;
    public TextView tvBottomTip;
    public ProgressBar progressBar;
    public View bottomDivider;
    public TextView tvLeftBottomTip;

    public ListLivelihoodNewsView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.workspace_item_main_livelihood_news, this);

        // 初始化视图
        mrvView = getViewById(R.id.rv_progress);
        tvBottomTip = getViewById(R.id.tv_bottom_tip);
        progressBar = getViewById(R.id.progressBar_load_more);
        bottomDivider = getViewById(R.id.bottom_divider);
        tvLeftBottomTip = getViewById(R.id.tv_lefit_bottom_tip);
    }
}
