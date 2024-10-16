package com.pasc.lib.workspace.handler.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.pasc.lib.workspace.handler.UrlProxy;
import com.pasc.lib.widget.tangram.util.ConfigUtils;
import com.pasc.lib.workspace.UserProxy;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自定义的协议工具类.
 * Created by chenruihan410 on 2018/8/12.
 */
public final class ProtocolUtils {

    public static void handleParamsUrlPlaceholder(LinkedHashMap<String, String> params) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            if ("url".equals(key)) {
                String value = entry.getValue();
                value = handleUrlPlaceholder(value);
                params.put(key, value);
            }
        }
    }

    public static String handleUrlPlaceholder(String url) {
        if (url.contains("{apiUrlRoot}")) {
            url = url.replace("{apiUrlRoot}", UrlProxy.getInstance().getApiUrlRoot());
        }
        if (url.contains("{h5UrlRoot}")) {
            url = url.replace("{h5UrlRoot}", UrlProxy.getInstance().getH5UrlRoot());
        }
        if (url.contains("{token}")) {
            String token = UserProxy.getInstance().getToken();
            if (!TextUtils.isEmpty(token)) {
                url = url.replace("{token}", token);
            }
        }
        return url;
    }

    /**
     * 从url和json对象分析参数.
     *
     * @param url              url地址.
     * @param paramsJsonObject 参数的json对象.
     * @return 参数列表集合.
     */
    @NonNull
    public static LinkedHashMap<String, String> parseParams(String url, JSONObject paramsJsonObject) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();

        // url参数
        LinkedHashMap<String, String> urlParams = ConfigUtils.parseParamsByRegex(url);

        // params参数
        LinkedHashMap<String, String> jsonParams = parseParamsFromJsonObject(paramsJsonObject);

        params.putAll(urlParams);
        params.putAll(jsonParams);
        return params;
    }

    /**
     * 从jsonObject分析参数.
     *
     * @param params 参数对象.
     * @return 参数列表.
     */
    public static LinkedHashMap<String, String> parseParamsFromJsonObject(JSONObject params) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        if (params != null) {
            // 所有参数都统一为字符串，兼容性更好
            Iterator<String> keys = params.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = params.optString(key);
                map.put(key, value);
            }
        }
        return map;
    }
}
