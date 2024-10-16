package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

/**
 * @see InfoItemView
 * @deprecated
 */
public class InfoItemOneImgView extends BaseCardView {

    private TextView titleView;
    private TextView descView;
    private RoundedImageView imgView;

    public InfoItemOneImgView(Context context) {
        super(context);
    }

    @Override
    protected void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.lwt_info_item_one_img, this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setForeground(getResources().getDrawable(R.drawable.fg_white_background_view, null));
        }

        titleView = getViewById(R.id.titleView);
        descView = getViewById(R.id.descView);
        imgView = getViewById(R.id.imgView);
    }

    public TextView getTitleView() {
        return titleView;
    }

    public TextView getDescView() {
        return descView;
    }

    public RoundedImageView getImgView() {
        return imgView;
    }
}
