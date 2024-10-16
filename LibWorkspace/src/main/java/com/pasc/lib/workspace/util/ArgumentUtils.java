package com.pasc.lib.workspace.util;

import android.text.TextUtils;

/**
 * 参数相关的工具集合.
 * Created by chenruihan410 on 2018/09/04.
 */
public class ArgumentUtils {

    /**
     * 断言不为空.
     *
     * @param object 参数对象.
     */
    public static void assertNotNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Object is null");
        }
    }

    /**
     * 断言字符串不为空.
     *
     * @param text 文本对象.
     */
    public static void assertNotEmpty(CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            throw new IllegalArgumentException("Text is empty");
        }
    }
}
