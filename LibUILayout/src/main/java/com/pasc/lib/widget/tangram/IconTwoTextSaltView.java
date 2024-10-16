package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class IconTwoTextSaltView extends BaseCardView {

    private ImageView iconView;
    private TextView titleView;
    private TextView descView;
    private View gapView;

    public IconTwoTextSaltView(Context context) {
        super(context);
    }

    @Override
    protected void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.workspace_icon_two_text_salt_view, this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setForeground(getResources().getDrawable(R.drawable.fg_white_background_view, null));
        }

        iconView = getViewById(R.id.iconView);
        titleView = getViewById(R.id.titleView);
        gapView = getViewById(R.id.gapView);
        descView = getViewById(R.id.descView);
    }

    public ImageView getIconView() {
        return iconView;
    }

    public TextView getTitleView() {
        return titleView;
    }

    public TextView getDescView() {
        return descView;
    }

    public View getGapView() {
        return gapView;
    }
}
