package com.pasc.business.life.util;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.pasc.lib.base.util.DensityUtils;
import java.lang.reflect.Field;

/**
 * Created by huangkuan on 17/12/26.
 */

public class ViewUtils {

    /**
     * 检查view集合是否全部隐藏 只有返回true才全部隐藏
     *
     * @param views 要检测的view的ID数组
     * @return
     */
    public static boolean isAllInVisiable(View[] views) {
        for (View view : views) {
            if (view.getVisibility() == View.VISIBLE) {
                return false;
            }
        }
        return true;
    }

    /**
     * 修改tab下划线宽度
     *
     * @param tabs
     * @param leftDip
     * @param rightDip
     * @param leftPading
     * @param rightPading
     */
    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip, float leftPading, float rightPading) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout ll_tab = null;
        try {
            ll_tab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = DensityUtils.dp2px(leftDip);
        int right = DensityUtils.dp2px(rightDip);

        int leftP = DensityUtils.dp2px(leftPading);
        int rightP = DensityUtils.dp2px(rightPading);
        for (int i = 0; i < ll_tab.getChildCount(); i++) {
            View child = ll_tab.getChildAt(i);
            child.setPadding(leftP, 0, rightP, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        setIndicator(tabs, leftDip, rightDip, 0, 0);
    }

    public static void setPaddingToolbar(TextView textView, Context context) {
        //获取status_bar_height资源的ID
        int resourceId =
                context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = 22;
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        LinearLayout.LayoutParams params =
                (LinearLayout.LayoutParams) textView.getLayoutParams();
        params.height = params.height + statusBarHeight;
        textView.setLayoutParams(params);
        textView.setPadding(0, statusBarHeight, 0, 0);


    }
}
