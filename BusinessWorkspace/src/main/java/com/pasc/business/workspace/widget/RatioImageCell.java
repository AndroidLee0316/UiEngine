package com.pasc.business.workspace.widget;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.pasc.lib.log.PascLog;
import com.pasc.lib.widget.tangram.BasePascCell;
import com.tmall.wireless.tangram.MVHelper;

import org.json.JSONObject;

@Deprecated
public class RatioImageCell extends BasePascCell<RatioImageView> {

    public static final String IMG = "img";
    private static final String TAG = "RatioImageCell";
    private String imgUrl;
    private float ratio;

    @Override
    public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
        super.parseWith(data, resolver);
        imgUrl = getString(data, IMG + "Url");
        double ratio = getDouble(data, "ratio");
        if (Double.isNaN(ratio)) {
            this.ratio = 2.0f; // 默认宽高比是2比1
        } else {
            this.ratio = (float) ratio;
        }
    }

    @Override
    public void postBindView(@NonNull RatioImageView view) {
        super.postBindView(view);
    }

    @Override
    public boolean isValid() {
        return super.isValid();
    }

    @Override
    protected void bindViewData(@NonNull RatioImageView view) {
        super.bindViewData(view);
        if (view == null) return;
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setRatio(ratio);
        setDefaultImageTag(view, IMG);
        PascLog.d(TAG, "加载比例图片?imgUrl=" + imgUrl);
        doLoadImageUrl(view, imgUrl);
    }
}
