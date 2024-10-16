package com.pasc.lib.workspace.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 缓存相关的工具集合.
 * Created by chenruihan410 on 2018/09/04.
 */
public class CacheUtils {

    /**
     * 构建缓存key.
     *
     * @param baseKey 基础的缓存key.
     * @return 缓存key.
     */
    public static String getCacheKey(String moduleName, String baseKey) {
        return getCacheKeyInside(moduleName, baseKey);
    }

    private static String getCacheKeyInside(String moduleName, String baseKey) {
        String key = moduleName + "?" + "baseKey=" + baseKey;
        return key;
    }

    /**
     * 构建缓存key.
     *
     * @param baseKey 基础的缓存key.
     * @param params  参数值.
     * @return 缓存key.
     */
    public static String getCacheKey(String moduleName, String baseKey, LinkedHashMap<String, String> params) {
        StringBuffer keyBuf = new StringBuffer(getCacheKeyInside(moduleName, baseKey));
        if (params != null) {
            Set<Map.Entry<String, String>> entries = params.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                keyBuf.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        return keyBuf.toString();
    }
}
