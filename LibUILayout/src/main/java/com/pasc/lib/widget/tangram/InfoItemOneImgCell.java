package com.pasc.lib.widget.tangram;

import android.support.annotation.NonNull;

/**
 * @see InfoItemCell
 * @deprecated
 */
public class InfoItemOneImgCell extends BaseCardCell<InfoItemOneImgView> {

    public static final String TITLE = "title";
    public static final String DESC = "desc";
    public static final String IMG = "img";

    @Override
    protected void bindViewData(@NonNull InfoItemOneImgView view) {
        super.bindViewData(view);

        setImageAndStyle(view, view.getImgView(), IMG);
        setTextAndStyle(view, view.getTitleView(), TITLE);
        setTextAndStyle(view, view.getDescView(), DESC);
    }
}
