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
public class InfoItemThreeImgView extends BaseCardView {

    private TextView titleView;
    private TextView descView;
    private RoundedImageView imgView1;
    private RoundedImageView imgView2;
    private RoundedImageView imgView3;

    public InfoItemThreeImgView(Context context) {
        super(context);
    }

    @Override
    protected void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.lwt_info_item_three_img, this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setForeground(getResources().getDrawable(R.drawable.fg_white_background_view, null));
        }

        titleView = getViewById(R.id.titleView);
        descView = getViewById(R.id.descView);
        imgView1 = getViewById(R.id.imgView1);
        imgView2 = getViewById(R.id.imgView2);
        imgView3 = getViewById(R.id.imgView3);
    }

    public TextView getTitleView() {
        return titleView;
    }

    public TextView getDescView() {
        return descView;
    }

    public RoundedImageView[] getImgViews() {
        return new RoundedImageView[]{imgView1, imgView2, imgView3};
    }
}
