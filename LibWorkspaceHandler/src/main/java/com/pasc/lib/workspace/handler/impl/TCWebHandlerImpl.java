package com.pasc.lib.workspace.handler.impl;

import android.app.Activity;
import android.view.View;

import com.pasc.lib.workspace.handler.HandlerProxy;
import com.pasc.lib.workspace.handler.TangramClickHandler;
import com.pasc.lib.workspace.handler.TangramClickParams;
import com.tmall.wireless.tangram.structure.BaseCell;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 网页处理器。
 */
public class TCWebHandlerImpl implements TangramClickHandler {

    private Activity activity;

    public TCWebHandlerImpl(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void handle(TangramClickParams params) {
        if (params == null) return;
        String clickUrl = params.getClickUrl();
        JSONObject clickParams = params.getClickParams();

        Map<String, String> paramsMap = new HashMap<>();
        if (clickParams != null) {
            Iterator<String> keys = clickParams.keys();
            if (keys != null) {
                while (keys.hasNext()) {
                    try {
                        String key = keys.next();
                        String value = clickParams.getString(key);
                        paramsMap.put(key, value);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        HandlerProxy.getInstance().getProtocolHandler().handle(activity, clickUrl, paramsMap);
    }
}
