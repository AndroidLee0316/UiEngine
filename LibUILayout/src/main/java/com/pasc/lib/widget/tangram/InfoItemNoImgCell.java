package com.pasc.lib.widget.tangram;

import android.support.annotation.NonNull;

/**
 * @see InfoItemCell
 * @deprecated
 */
public class InfoItemNoImgCell extends BaseCardCell<InfoItemNoImgView> {

    public static final String TITLE = "title";
    public static final String DESC = "desc";

    @Override
    protected void bindViewData(@NonNull InfoItemNoImgView view) {
        super.bindViewData(view);

        setTextAndStyle(view, view.getTitleView(), TITLE);
        setTextAndStyle(view, view.getDescView(), DESC);
    }
}
