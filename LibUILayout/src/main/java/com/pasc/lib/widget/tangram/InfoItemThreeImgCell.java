package com.pasc.lib.widget.tangram;

import android.support.annotation.NonNull;

import com.makeramen.roundedimageview.RoundedImageView;

/**
 * @see InfoItemCell
 * @deprecated
 */
public class InfoItemThreeImgCell extends BaseCardCell<InfoItemThreeImgView> {

    public static final String TITLE = "title";
    public static final String DESC = "desc";
    public static final String IMG = "img";

    @Override
    protected void bindViewData(@NonNull InfoItemThreeImgView view) {
        super.bindViewData(view);

        RoundedImageView[] imgViews = view.getImgViews();
        for (int i = 0; i < imgViews.length; i++) {
            setImageAndStyle(view, imgViews[i], IMG + (i + 1));
        }
        setTextAndStyle(view, view.getTitleView(), TITLE);
        setTextAndStyle(view, view.getDescView(), DESC);
    }
}
