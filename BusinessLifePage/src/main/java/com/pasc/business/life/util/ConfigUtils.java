package com.pasc.business.life.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;

import static java.util.regex.Pattern.*;

/**
 * json配置相关的工具类.
 * Created by chenruihan410 on 2018/8/12.
 */
public final class ConfigUtils {

    public static final int EVENT_TYPE_WEB = 1; // 网页
    public static final int EVENT_TYPE_NATIVE = 2; // 原生界面
    public static final int EVENT_TYPE_NATIVE_ROUTER = 3; // 原生界面路由方式
    public static final int EVENT_TYPE_EVENT = 4; // 触发事件总线的事件
    public static final int EVENT_TYPE_NATIVE_MAP = 5; // 原生界面直接映射方式
    public static final String EVENT_PREFIX = "event://";
    public static final String ANDROID_PREFIX = "android://";
    public static final String SMT_PREFIX = "smt://";
    public static final String ROUTER_PREFIX = "router://";
    public static final String NFC_FIND_MONNIEY = "RechargeCommonCardPage?fromType=1";//余额查询
    public static final String NFC_PATH = "pacard://pacard";

    //搜索本地存储key
    public static final String SEARCH_DATA = "search_data";



    /**
     * 从json对象分析点击事件类型.
     *
     * @return 事件类型.
     */
    public static Integer parseEventType(String url) {
        if (!TextUtils.isEmpty(url)) {
            int eventType = EVENT_TYPE_WEB;
            if (url.startsWith(ANDROID_PREFIX)) {
                eventType = EVENT_TYPE_NATIVE;
            } else if (url.startsWith(SMT_PREFIX)) {
                eventType = EVENT_TYPE_NATIVE_MAP;
            } else if (url.startsWith(EVENT_PREFIX)) {
                eventType = EVENT_TYPE_EVENT;
            } else if (url.startsWith(ROUTER_PREFIX)) {
                eventType = EVENT_TYPE_NATIVE_ROUTER;
            } else {
                eventType = EVENT_TYPE_WEB;
            }
            return eventType;
        } else {
            return null;
        }
    }

    /**
     * 解析url参数.
     *
     * @param url url地址.
     * @return 参数.
     */
    public static LinkedHashMap<String, String> parseParamsByRegex(String url) {
        LinkedHashMap<String, String> paramsMap = new LinkedHashMap<>();

        if (TextUtils.isEmpty(url)) {
            return paramsMap;
        }
        Pattern compile = compile("(\\w+)=(\\w*)");
        Matcher matcher = compile.matcher(url);
        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(2);
            paramsMap.put(key, value);
        }

        return paramsMap;
    }

    /**
     * 从url分析出完整的ClassName.
     *
     * @param url         url地址.
     * @param packageName 包名.
     * @return 完整的ClassName.
     */
    public static String parseClassName(String url, String packageName) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        Pattern compile = compile("^[a-zA-Z_]+://([\\./\\w]+)\\??");
        Matcher matcher = compile.matcher(url);
        while (matcher.find()) {
            String str = matcher.group(1);
            str = str.replace('/', '.').replace("package", packageName);
            return str;
        }

        return null;
    }

    /**
     * 从url获取drawable.
     *
     * @param context 上下文.
     * @param url     url地址.
     * @return drawable对应的id.
     */
    public static Integer getDrawableFromUrl(Context context, String url) {
        if (context == null) {
            return null;
        }
        if (TextUtils.isEmpty(url)) {
            return null;
        }

        Integer result = null;
        if (url.startsWith("res://")) {
            Resources resources = context.getResources();
            String packageName = context.getPackageName();
            String resourceName = url.substring("res://".length());
            int drawable = resources.getIdentifier(resourceName, "drawable", packageName);
            if (drawable != 0) {
                result = drawable;
            }
            if (result == null) {
                int mipmap = resources.getIdentifier(resourceName, "mipmap", packageName);
                if (mipmap != 0) {
                    result = mipmap;
                }
            }
        }
        return result;
    }

    /**
     * 获取字符串.
     *
     * @param name 字段名
     * @return 字符串值.
     */
    public static String getString(JSONObject jsonObject, String name) {
        if (jsonObject == null || TextUtils.isEmpty(name)) {
            return null;
        }
        String stringValue = jsonObject.optString(name);
        return stringValue;
    }

    /**
     * 获取整型值.
     *
     * @param name 字段名
     * @return 整型值.
     */
    public static int getInt(JSONObject jsonObject, String name) {
        if (jsonObject == null || TextUtils.isEmpty(name)) {
            return 0;
        }
        int intValue = jsonObject.optInt(name);
        return intValue;
    }

    /**
     * 获取布尔值.
     *
     * @param name 字段名
     * @return 布尔值.
     */
    public static boolean getBoolean(JSONObject jsonObject, String name) {
        if (jsonObject == null || TextUtils.isEmpty(name)) {
            return false;
        }
        boolean booleanValue = jsonObject.optBoolean(name);
        return booleanValue;
    }

    /**
     * 获取整型值.
     *
     * @param name 字段名
     * @return 整型值.
     */
    public static double getDouble(JSONObject jsonObject, String name) {
        if (jsonObject == null || TextUtils.isEmpty(name)) {
            return Double.NaN;
        }
        double doubleValue = jsonObject.optDouble(name);
        return doubleValue;
    }

    /**
     * 获取json对象.
     *
     * @param name 字段名
     * @return json对象.
     */
    public static JSONObject getJsonObject(JSONObject jsonObject, String name) {
        if (jsonObject == null || TextUtils.isEmpty(name)) {
            return null;
        }
        JSONObject jsonObjectValue = jsonObject.optJSONObject(name);
        return jsonObjectValue;
    }
    public static void setBarColor(Activity context, int color){
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = context.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(context.getResources().getColor(color));
                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getVersionCode(Context context){
        PackageManager packageManager=context.getPackageManager();
        PackageInfo packageInfo;
        String versionCode="";
        try {
            packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            versionCode=packageInfo.versionCode+"";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
    public static int getVersionCodes(Context context){
        PackageManager packageManager=context.getPackageManager();
        PackageInfo packageInfo;
        int versionCode = 100;
        try {
            packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            versionCode=packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

}
