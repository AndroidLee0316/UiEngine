package com.pasc.business.workspace.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pasc.lib.widget.PascUpRollView;
import com.pasc.business.workspace.R;
import com.pasc.lib.widget.tangram.BaseCardView;

public class MarqueeNewsView extends BaseCardView {

    private PascUpRollView upRollView;
    private ImageView imgView;
    private ImageView bgImgView;
    private View rootView;

    public MarqueeNewsView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.workspace_item_main_news_model, this);

        rootView = getViewById(R.id.rootView);
        upRollView = getViewById(R.id.upRollView);
        imgView = getViewById(R.id.imgView);
        bgImgView = getViewById(R.id.bgImgView);
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    public PascUpRollView getUpRollView() {
        return upRollView;
    }

    public ImageView getImgView() {
        return imgView;
    }

    public ImageView getBgImgView() {
        return bgImgView;
    }

    public void setImgGap(int imgGap) {
        if (imgView != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imgView.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin, imgGap, layoutParams.bottomMargin);
        }
    }
}
