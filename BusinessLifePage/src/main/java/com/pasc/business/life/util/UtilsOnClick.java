package com.pasc.business.life.util;

public class UtilsOnClick {
    // 两次点击按钮之间的点击间隔不能少于200毫秒
    private static final int MIN_CLICK_DELAY_TIME = 200 * 1000;
    private static long lastClickTime;

    public static boolean performClick() {
        boolean flag = true;
        long curClickTime = System.nanoTime();
        if ((curClickTime - lastClickTime) < MIN_CLICK_DELAY_TIME) {
            //两次操作小于200毫秒，不响应click操作
            flag = false;
        }
        lastClickTime = curClickTime;
        return flag;
    }
}
