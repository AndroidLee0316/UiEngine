package com.pasc.lib.widget.tangram;

import android.support.annotation.NonNull;

import com.makeramen.roundedimageview.RoundedImageView;
import com.pasc.lib.widget.tangram.util.CellUtils;
import com.tmall.wireless.tangram.MVHelper;

import org.json.JSONArray;
import org.json.JSONObject;

public class InfoItemCell extends BaseCardCell<InfoItemView> {

    public static final String TITLE = "title";
    public static final String DESC = "desc";
    public static final String IMG_URLS = "imgUrls";
    public static final String TIME = "time";
    public static final String IMG = "img";

    private String[] imgUrls; // 图片地址

    @Override
    public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
        super.parseWith(data, resolver);

        JSONArray jsonArray = optJsonArrayParam(IMG_URLS);
        imgUrls = CellUtils.getStringArrFromJson(jsonArray);
    }

    @Override
    protected void bindViewData(@NonNull InfoItemView view) {
        super.bindViewData(view);


        if (imgUrls != null) {
            int length = imgUrls.length;
            if (length == 1) {
                view.setState(InfoItemView.State.SINGLE_IMG);
                RoundedImageView singleImg = view.getSingleImgView();
                setImageAndStyle(view, singleImg, IMG, null, R.drawable.lwt_img_error, imgUrls[0]);
            } else if (length > 1 && length < 4) {
                view.setState(InfoItemView.State.THREE_IMG);
                RoundedImageView[] threeImgViews = view.getThreeImgViews();
                for (int i = 0; i < length; i++) {
                    setImageAndStyle(view, threeImgViews[i], IMG, null, R.drawable.lwt_img_error, imgUrls[i]);
                }
            } else {
                view.setState(InfoItemView.State.NO_IMG);
            }
        }


        setTextAndStyle(view, view.getTitleView(), TITLE);
        setTextAndStyle(view, view.getDescView(), DESC);
        setTextAndStyle(view, view.getTimeView(), TIME);
    }
}
