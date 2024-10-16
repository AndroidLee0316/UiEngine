package com.pasc.lib.widget.tangram.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    Pattern compile = Pattern.compile("(\\w+)=(\\w*)");
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
   * @param url url地址.
   * @param packageName 包名.
   * @return 完整的ClassName.
   */
  public static String parseClassName(String url, String packageName) {
    if (TextUtils.isEmpty(url)) {
      return null;
    }
    Pattern compile = Pattern.compile("^[a-zA-Z_]+://([\\./\\w]+)\\??");
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
   * @param url url地址.
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

  public static String getString(JSONObject jsonObject, String name) {
    return getString(jsonObject, name, null);
  }

  /**
   * 获取字符串.
   *
   * @param name 字段名
   * @return 字符串值.
   */
  public static String getString(JSONObject jsonObject, String name, String defaultValue) {
    if (jsonObject == null || TextUtils.isEmpty(name)) {
      return null;
    }
    String stringValue = jsonObject.optString(name);
    if (defaultValue != null) {
      if (TextUtils.isEmpty(stringValue)) {
        return defaultValue;
      }
    }
    return stringValue;
  }

  /**
   * 获取整型值.
   *
   * @param name 字段名
   * @return 整型值.
   */
  public static int getInt(JSONObject jsonObject, String name) {
    return getInt(jsonObject, name, 0);
  }

  public static int getInt(JSONObject jsonObject, String name, int defaultValue) {
    if (jsonObject == null || TextUtils.isEmpty(name)) {
      return defaultValue;
    }
    if (!jsonObject.has(name)) {
      return defaultValue;
    }
    int intValue = jsonObject.optInt(name);
    return intValue;
  }

  public static double getDouble(JSONObject jsonObject, String name, double defaultValue) {
    if (jsonObject == null || TextUtils.isEmpty(name)) {
      return defaultValue;
    }
    if (!jsonObject.has(name)) {
      return defaultValue;
    }
    double doubleValue = jsonObject.optDouble(name);
    return doubleValue;
  }

  /**
   * 获取布尔值.
   *
   * @param name 字段名
   * @return 布尔值.
   */
  public static boolean getBoolean(JSONObject jsonObject, String name) {
    return getBoolean(jsonObject, name, false);
  }

  /**
   * 获取布尔值.
   *
   * @param name 字段名
   * @return 布尔值.
   */
  public static boolean getBoolean(JSONObject jsonObject, String name, boolean defaultValue) {
    if (jsonObject == null || TextUtils.isEmpty(name)) {
      return false;
    }
    boolean booleanValue = jsonObject.optBoolean(name, defaultValue);
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

  /**
   * 将json数组转换成java数组.
   *
   * @param dataSource 数据源.
   * @param clazz 转换类型.
   * @param <T> 泛型.
   * @return java数组对象.
   */
  public static <T> ArrayList<T> toArrayList(JSONArray dataSource, Class<T> clazz) {
    ArrayList<T> list = new ArrayList<T>();
    if (dataSource != null && clazz != null) {
      int length = dataSource.length();
      for (int i = 0; i < length; i++) {
        try {
          JSONObject jsonObject = dataSource.getJSONObject(i);
          Gson gson = new Gson();
          T item = gson.fromJson(jsonObject.toString(), clazz);
          list.add(item);
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    }
    return list;
  }

  /**
   * 将对象转换成json数组.
   *
   * @param object java对象.
   * @return json数组.
   */
  public static JSONArray toJSONArray(Object object) {
    try {
      Gson gson = new Gson();
      String json = gson.toJson(object);
      JSONArray jsonArray = new JSONArray(json);
      return jsonArray;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static float getFloat(JSONObject jsonObject, String name, float defaultValue) {
    if (jsonObject == null || TextUtils.isEmpty(name)) {
      return defaultValue;
    }
    float value = (float) jsonObject.optDouble(name, defaultValue);
    return value;
  }

  /**
   * 获取颜色。
   */
  public static int getColor(JSONObject jsonObject, String name, int defaultColor) {
    Integer color = null;
    if (jsonObject != null) {
      if (!TextUtils.isEmpty(name)) {
        String colorStr = getString(jsonObject, name);
        if (!TextUtils.isEmpty(colorStr)) {
          try {
            color = Color.parseColor(colorStr);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }
    if (color == null) {
      return defaultColor;
    }
    return color;
  }
}
