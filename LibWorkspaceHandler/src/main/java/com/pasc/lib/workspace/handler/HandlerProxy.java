package com.pasc.lib.workspace.handler;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.pasc.lib.workspace.handler.impl.HandlerBuilderImpl;

import java.util.Map;

public class HandlerProxy {

    private static final String TAG = "HandlerProxy";
    private static HandlerProxy proxy = new HandlerProxy();
    private Context context;
    private ProtocolHandler protocolHandler;

    private HandlerProxy() {
        protocolHandler = new ProtocolHandler() {
            @Override
            public void handle(Activity activity, String url, Map<String, String> params) {
                // 默认的协议处理器
                Log.w(TAG, "协议处理器未设置，请在初始化HandlerProxy后设置协议处理器.");
            }
        };
    }

    public static HandlerProxy getInstance() {
        return proxy;
    }

    public HandlerProxy init(Context context) {
        Context applicationContext = context.getApplicationContext();
        proxy.context = applicationContext;
        return this;
    }

    public ProtocolHandler getProtocolHandler() {
        return protocolHandler;
    }

    public HandlerProxy setProtocolHandler(ProtocolHandler protocolHandler) {
        this.protocolHandler = protocolHandler;
        return this;
    }
}
