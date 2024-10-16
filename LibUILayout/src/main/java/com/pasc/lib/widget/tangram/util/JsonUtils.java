package com.pasc.lib.widget.tangram.util;

import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 操作Asset相关的工具集合.
 * Created by chenruihan410 on 2019/07/26.
 */
public class JsonUtils {

  public static Object mergeObject(Object srcObj, Object destObj) {
    if (srcObj == null) {
      return destObj;
    }
    if (destObj == null) {
      return srcObj;
    }
    Class<?> clazzSrc = srcObj.getClass();
    Class<?> clazzDest = destObj.getClass();
    if (!clazzSrc.equals(clazzDest)) {
      return destObj;
    }
    if (srcObj instanceof JSONObject) {
      return mergeJSONObject((JSONObject) srcObj, (JSONObject) destObj);
    } else if (srcObj instanceof JSONArray) {
      return mergeJSONArray((JSONArray) srcObj, (JSONArray) destObj);
    } else {
      return destObj;
    }
  }

  public static JSONArray mergeJSONArray(JSONArray srcArray, JSONArray destArray) {
    if (srcArray == null) {
      return destArray;
    }
    if (destArray == null) {
      return srcArray;
    }
    int lengthDefault = srcArray.length();
    int length = destArray.length();
    int count = Math.min(length, lengthDefault);
    for (int i = 0; i < count; i++) {
      Object valueSrc = srcArray.opt(i);
      if (valueSrc == null) continue;
      Object valueDest = destArray.opt(i);
      Object valueResult = mergeObject(valueSrc, valueDest);
      try {
        destArray.put(i, valueResult);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    return destArray;
  }

  public static JSONObject mergeJSONObject(JSONObject srcData, JSONObject destData) {
    if (srcData == null) {
      return destData;
    }
    if (destData == null) {
      return srcData;
    }

    Iterator<String> keys = srcData.keys();
    while (keys.hasNext()) {
      String key = keys.next();
      Object valueSrc = srcData.opt(key);
      if (valueSrc == null) continue;
      Object valueDest = destData.opt(key);
      Object valueResult = mergeObject(valueSrc, valueDest);
      try {
        destData.put(key, valueResult);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    return destData;
  }
}
