package com.pasc.lib.workspace.handler;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.pasc.lib.widget.tangram.util.ConfigUtils;
import com.pasc.lib.workspace.handler.impl.TCEventHandlerImpl;
import com.pasc.lib.workspace.handler.impl.TCRouterHandlerImpl;
import com.pasc.lib.workspace.handler.impl.TCStatHandlerImpl;
import com.pasc.lib.workspace.handler.impl.TCWebHandlerImpl;
import com.pasc.lib.workspace.handler.util.ProtocolUtils;
import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.support.SimpleClickSupport;

import org.json.JSONObject;

import java.util.HashMap;

public class TangramClickSupport extends SimpleClickSupport {

    private static final String TAG = "TangramClickSupport";
    public static final String ON_CLICK = "onClick";
    public static final String ON_CLICK_PARAMS = "onClickParams";
    public static final String ON_CLICK_EVENT_ID = "onClickEventId";
    public static final String ON_CLICK_EVENT_PARAMS = "onClickEventParams";

    private final Activity activity;
    private final HashMap<String, TangramClickHandler> handlerMap;

    private TangramClickHandler defaultHandler;
    private TangramClickStatHandler defaultStatHandler;

    public TangramClickSupport(Activity activity) {
        setOptimizedMode(true);

        this.activity = activity;
        defaultHandler = new TCWebHandlerImpl(activity);
        defaultStatHandler = new TCStatHandlerImpl();
        handlerMap = new HashMap<>();

        addClickHandler("router", new TCRouterHandlerImpl());
        addClickHandler("event", new TCEventHandlerImpl());
    }

    /**
     * 添加点击处理器。
     *
     * @param protocol 协议。
     * @param handler  处理器。
     */
    public void addClickHandler(String protocol, TangramClickHandler handler) {
        handlerMap.put(protocol, handler);
    }

    /**
     * 获取字符串.
     *
     * @param name 字段名
     * @return 字符串值.
     */
    private String getString(JSONObject jsonObject, String name) {
        return ConfigUtils.getString(jsonObject, name);
    }

    private JSONObject getJsonObject(BaseCell cell, String name) {
        JSONObject extras = cell.extras;
        return ConfigUtils.getJsonObject(extras, name);
    }

    @Override
    public void defaultClick(View targetView, BaseCell cell, int eventType) {
        super.defaultClick(targetView, cell, eventType);

        try {
            JSONObject jsonObject = cell.extras;

            Object tag = targetView.getTag();
            if (tag != null) {
                if (tag instanceof JSONObject) {
                    Log.d(TAG, "视图里的tag是JSONObject，使用JSONObject进行点击分析");
                    jsonObject = (JSONObject) tag;
                }
            }

            // 点击统计处理
            String eventId = getString(jsonObject, ON_CLICK_EVENT_ID);
            if (!TextUtils.isEmpty(eventId)) {
                JSONObject jsonObjectEventParams = ConfigUtils.getJsonObject(jsonObject, ON_CLICK_EVENT_PARAMS);
                defaultStatHandler.handle(eventId, jsonObjectEventParams);
            }

            // 点击事件分析处理
            String url = getString(jsonObject, ON_CLICK);
            Log.d(TAG, "点击cell?eventType=" + eventType + "&url=" + url);
            JSONObject paramsJsonObject = ConfigUtils.getJsonObject(jsonObject, ON_CLICK_PARAMS);

            url = ProtocolUtils.handleUrlPlaceholder(url);
            int endIndex = url.indexOf("://");
            if (endIndex > 0) {
                String protocol = url.substring(0, endIndex);
                TangramClickHandler tangramClickHandler = handlerMap.get(protocol);
                if (tangramClickHandler == null) {
                    tangramClickHandler = defaultHandler;
                }
                TangramClickParams tangramClickParams = new TangramClickParams();
                tangramClickParams.setCell(cell);
                tangramClickParams.setClickParams(paramsJsonObject);
                tangramClickParams.setClickUrl(url);
                tangramClickParams.setTargetView(targetView);
                tangramClickParams.setEventType(eventType);
                tangramClickHandler.handle(tangramClickParams);
            }
        } catch (Exception e) {
            Log.e(TAG, "Tangram点击事件发生异常：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
