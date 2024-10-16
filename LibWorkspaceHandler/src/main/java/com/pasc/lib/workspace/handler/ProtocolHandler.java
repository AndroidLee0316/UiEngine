package com.pasc.lib.workspace.handler;

import android.app.Activity;

import java.util.Map;

/**
 * 协议处理器.
 */
public interface ProtocolHandler {

    void handle(Activity activity, String url, Map<String, String> params);
}
