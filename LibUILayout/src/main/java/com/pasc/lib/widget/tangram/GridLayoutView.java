package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GridLayoutView extends BaseCardView {

    private GridLayout gridLayout;

    public GridLayoutView(Context context) {
        super(context);
    }

    public GridLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.workspace_icon_text_grid_view, this);

        gridLayout = getViewById(R.id.gridLayout);
    }

    public GridLayout getGridLayout() {
        return gridLayout;
    }
}
