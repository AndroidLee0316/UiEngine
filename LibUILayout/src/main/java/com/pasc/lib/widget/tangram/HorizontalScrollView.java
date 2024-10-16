package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

public class HorizontalScrollView extends BaseCardView {

    private RecyclerView recyclerView;

    public HorizontalScrollView(Context context) {
        super(context);
    }

    @Override
    protected void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.workspace_card_container, this);
        recyclerView = getViewById(R.id.mine_card_RecyclerView);
        recyclerView.setLayoutManager(
            new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}
