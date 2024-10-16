package com.pasc.lib.widget.tangram;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.tmall.wireless.tangram.MVHelper;
import com.tmall.wireless.tangram.util.ImageUtils;

import org.json.JSONObject;

/**
 * 功能：
 * <p>
 * created by zoujianbo345
 * data : 2018/8/22
 */
public class LifeIconTwoTxtModel extends BaseCardCell<IconTwoTxtView> {
    private String mTitle;
    private String mDesc;
    private String iconUrl;
    private boolean isGone;

    @Override
    public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
        super.parseWith(data, resolver);

        mTitle = data.optString("title");
        mDesc = data.optString("desc");
        iconUrl = data.optString("iconUrl");
        isGone = data.optBoolean("isGone");
    }

    @Override
    protected void bindViewData(@NonNull IconTwoTxtView view) {
        super.bindViewData(view);
        view.intro.setText(mDesc);
        view.title.setText(mTitle);
        if(TextUtils.isEmpty(iconUrl)){
            view.icon.setVisibility(View.GONE);
        }else {
            view.icon.setVisibility(View.VISIBLE);
        }
        ImageUtils.doLoadImageUrl(view.icon,iconUrl);

//        if (isGone) {
//            view.line.setVisibility(View.INVISIBLE);
//        }else {
//            view.line.setVisibility(View.VISIBLE);
//        }
    }
}
