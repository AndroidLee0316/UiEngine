package com.pasc.business.workspace.widget;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.pasc.lib.base.util.DensityUtils;
import com.pasc.lib.widget.tangram.BasePascCell;

public class ShadowHorizontalCell extends BasePascCell<ShadowHorizontalView> {

    private static final String TAG = "ShadowHorizontalCell";

    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;
    public static final int NORTH = 4;
    public static final int EAST_SOUTH = 12;
    public static final int EAST_NORTH = 14;
    public static final int WEST_SOUTH = 32;
    public static final int WEST_NORTH = 34;

    @Override
    protected void bindViewData(@NonNull ShadowHorizontalView view) {
        super.bindViewData(view);
        if (view == null) return;
        // 设置高度
        int layoutHeight = DensityUtils.dip2px(view.getContext(), 10);
        int height = optIntParam("height");
        if (height > 0) {
            layoutHeight = height;
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = layoutHeight;

        // 设置渐变
        int orientation = optIntParam("orientation"); // 东南西北，东南，西北，东北，西南。东1南2西3北4
        GradientDrawable.Orientation gradientOrientation = null;
        switch (orientation) {
            case EAST: {
                gradientOrientation = GradientDrawable.Orientation.RIGHT_LEFT;
                break;
            }
            case SOUTH: {
                gradientOrientation = GradientDrawable.Orientation.TOP_BOTTOM;
                break;
            }
            case WEST: {
                gradientOrientation = GradientDrawable.Orientation.LEFT_RIGHT;
                break;
            }
            case NORTH: {
                gradientOrientation = GradientDrawable.Orientation.BOTTOM_TOP;
                break;
            }
            case EAST_SOUTH: {
                gradientOrientation = GradientDrawable.Orientation.TR_BL;
                break;
            }
            case EAST_NORTH: {
                gradientOrientation = GradientDrawable.Orientation.BR_TL;
                break;
            }
            case WEST_SOUTH: {
                gradientOrientation = GradientDrawable.Orientation.TL_BR;
                break;
            }
            case WEST_NORTH: {
                gradientOrientation = GradientDrawable.Orientation.BL_TR;
                break;
            }
            default: {
                gradientOrientation = GradientDrawable.Orientation.TOP_BOTTOM;
                break;
            }
        }

        int[] colors = null;
        int startColor = getColor("startColor", Color.parseColor("#f0f0f0"));
        int endColor = getColor("endColor", Color.parseColor("#fefefe"));
        Integer centerColor = getColor("centerColor");

        if (centerColor == null) {
            colors = new int[]{startColor, endColor};
        } else {
            colors = new int[]{startColor, centerColor, endColor};
        }

        GradientDrawable gradientDrawable = new GradientDrawable(gradientOrientation, colors);
        view.setBackground(gradientDrawable);
    }
}
