package com.pasc.lib.widget.tangram;

import android.support.annotation.NonNull;

@Deprecated
public class CardHeaderCell extends BaseCardCell<CardHeaderView> {

    public static final String TITLE = "title";
    public static final String DESC = "desc";
    public static final String ICON = "icon";

    @Override
    protected void bindViewData(@NonNull CardHeaderView view) {
        super.bindViewData(view);

        setImageAndStyle(view, view.mIcon, ICON);
        setTextAndStyle(view, view.mDesc, DESC);
        setTextAndStyle(view, view.mTitle, TITLE);
    }
}
