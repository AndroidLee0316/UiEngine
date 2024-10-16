package com.pasc.lib.workspace.handler.impl;

import android.util.Log;

import com.pasc.lib.workspace.handler.StatProxy;
import com.pasc.lib.workspace.handler.TangramClickStatHandler;
import com.pasc.lib.workspace.handler.util.ProtocolUtils;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 统计处理器。
 */
public class TCStatHandlerImpl implements TangramClickStatHandler {

    private static final String TAG = "TCStatHandlerImpl";
    public static final String EVENT_LABEL = "eventLabel"; // 保留字段，天眼的label可用此字段

    public TCStatHandlerImpl() {
    }

    @Override
    public void handle(String eventId, JSONObject paramsJsonObject) {
        LinkedHashMap<String, String> params = ProtocolUtils.parseParamsFromJsonObject(paramsJsonObject);
        StringBuffer buf = new StringBuffer("埋点?eventId=").append(eventId);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            buf.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
        Log.d(TAG, buf.toString());

        String eventLabel = params.get(EVENT_LABEL);
        if (eventLabel == null) {
            StatProxy.getInstance().onEvent(eventId, params);
        } else {
            params.remove(EVENT_LABEL);
            StatProxy.getInstance().onEvent(eventId, eventLabel, params);
        }
    }
}
