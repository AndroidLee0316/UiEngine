package com.pasc.lib.workspace.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 通用工具方法类
 * Created by chenruihan410 on 2018/08/07.
 */
public class CommonUtils {

    /**
     * 判断网络是否可用
     */
    @SuppressLint("MissingPermission")
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * 将版本号，转换成字符串.
     *
     * @param versionCode 版本号.
     * @return 版本号字符串，格式是x.x.x，最后两位是个数.
     */
    public static String convertVersionCode(int versionCode) {
        String versionCodeStr = String.valueOf(versionCode);
        int length = versionCodeStr.length();
        if (length < 3) {
            throw new RuntimeException("版本号不符合规则");
        }
        StringBuffer buf = new StringBuffer(versionCodeStr);
        buf.insert(buf.length() - 2, "."); // 在倒数第2个数字前插入"."
        buf.insert(buf.length() - 1, "."); // 在倒数第1个数字前插入"."
        return buf.toString();
    }

    /**
     * 获取应用版本号.
     *
     * @param context 上下文.
     * @return 版本号.
     */
    public static int getAppVersionCode(Context context) {
        int versionCode = 0;
        try {
            PackageManager packageManager = context.getPackageManager();
            String packageName = context.getPackageName();
            versionCode = packageManager.getPackageInfo(packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
}
