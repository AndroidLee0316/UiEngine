package com.pasc.lib.widget.tangram;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.ViewGroup;

import com.tmall.wireless.tangram.MVHelper;

import org.json.JSONObject;

/**
 * 功能：
 * <p>
 * created by zoujianbo345
 * data : 2018/8/28
 */
public class DividerHorizontalModle extends BasePascCell<DividerHorizontalView> {

    private static final String TAG = "ShadowHorizontalModel";


    private String color;
    private int  height;

    @Override
    public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
        super.parseWith(data, resolver);

        color = data.optString("color");
        height = data.optInt("height");

    }


    @Override
    protected void bindViewData(@NonNull DividerHorizontalView view) {
        super.bindViewData(view);
        // 设置高度
        if (height <= 0) {
            height = Math.round(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, view.getResources().getDisplayMetrics()));
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = height;
        if (TextUtils.isEmpty(color)) {
            view.setBackgroundColor(Color.parseColor("#f4f4f4"));
        } else {
            view.setBackgroundColor(Color.parseColor(color));
        }
    }
}
