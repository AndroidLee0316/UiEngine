package com.pasc.lib.widget.tangram;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.pasc.lib.widget.tangram.BasePascCell;

@Deprecated
public class ImageCell extends BasePascCell<ImageView> {

    public static final String IMG = "img";

    @Override
    protected void bindViewData(@NonNull ImageView view) {
        super.bindViewData(view);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        setDefaultImageTag(view, IMG);
        String url = getString(extras, IMG + "Url");
        doLoadImageUrl(view, url);
    }
}
