package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CardHeaderView2 extends BaseCardView {

    private ImageView iconView;
    private ImageView arrowIconView;
    private TextView titleView;
    private TextView descView;
    private View partitionView;
    private View contentView;

    public CardHeaderView2(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_main_module_header_2, this);

        contentView = findViewById(R.id.contentView);
        iconView = findViewById(R.id.iconView);
        partitionView = findViewById(R.id.partitionView);
        arrowIconView = findViewById(R.id.arrowIconView);
        titleView = findViewById(R.id.titleView);
        descView = findViewById(R.id.descView);
    }

    public TextView getDescView() {
        return descView;
    }

    public ImageView getIconView() {
        return iconView;
    }

    public ImageView getArrowIconView() {
        return arrowIconView;
    }

    public TextView getTitleView() {
        return titleView;
    }

    public void setPartitionVisible(boolean partitionVisible) {
        if (partitionView != null) {
            int visibilityOld = partitionView.getVisibility();
            int visibilityNew = partitionVisible ? View.VISIBLE : View.GONE;
            if (visibilityOld != visibilityNew) {
                partitionView.setVisibility(visibilityNew);
            }
        }
    }

    public void setPartitionColor(int color) {
        if (partitionView != null) {
            partitionView.setBackgroundColor(color);
        }
    }

    public void setContentVisible(boolean contentVisible) {
        if (contentView != null) {
            int visibilityOld = contentView.getVisibility();
            int visibilityNew = contentVisible ? View.VISIBLE : View.GONE;
            if (visibilityOld != visibilityNew) {
                contentView.setVisibility(visibilityNew);
            }
        }
    }
}
