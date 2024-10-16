package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;

import com.pasc.lib.widget.tangram.util.AndroidUtils;
import com.tmall.wireless.tangram.MVHelper;

import org.json.JSONObject;

/**
 * 水平分割视图组件.
 */
public class HorizontalDividerCell extends BaseCardCell<HorizontalDividerView> {

    private static int DEFAULT_COLOR = Color.parseColor("#f2f6f9");
    private static int DEFAULT_HEIGHT = 8;

    // 背景颜色
    private int color = DEFAULT_COLOR;
    // 高度，单位是dp
    private float height = DEFAULT_HEIGHT;

    @Override
    public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
        super.parseWith(data, resolver);

        color = getColor("color", DEFAULT_COLOR);

        double heightValue = optDoubleParam("height");
        if (heightValue != Double.NaN && heightValue > 0) {
            height = (float) heightValue;
        }
    }

    @Override
    protected void bindViewData(@NonNull HorizontalDividerView view) {
        super.bindViewData(view);

        view.setColor(color);

        Context context = view.getContext();
        int heightPx = Math.max(AndroidUtils.dip2px(context, height), 1); // 最小是1个像素
        view.setHeight(heightPx);
    }
}
