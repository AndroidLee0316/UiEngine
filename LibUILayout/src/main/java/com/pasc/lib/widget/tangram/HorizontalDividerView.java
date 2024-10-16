package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 水平分割视图.
 */
public class HorizontalDividerView extends BaseCardView {

    private View partitionView;

    public HorizontalDividerView(Context context) {
        super(context);
    }

    @Override
    protected void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.horizontal_divider_view, this);
        partitionView = getViewById(R.id.partitionView);
    }

    public void setColor(int color) {
        partitionView.setBackgroundColor(color);
    }

    public void setHeight(int height) {
        partitionView.getLayoutParams().height = height;
    }
}
