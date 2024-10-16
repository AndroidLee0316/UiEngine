package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddressView extends BaseCardView {

    private TextView titleView;
    private TextView descView;

    public AddressView(Context context) {
        super(context);
    }

    @Override
    protected void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.workspace_address_view, this);

        titleView = findViewById(R.id.titleView);
        descView = findViewById(R.id.descView);
    }

    public TextView getTitleView() {
        return titleView;
    }

    public TextView getDescView() {
        return descView;
    }
}
