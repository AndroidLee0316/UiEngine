package com.pasc.lib.workspace.handler;

import org.json.JSONObject;

/**
 * Tangram的点击统计接口。
 */
public interface TangramClickStatHandler {

    void handle(String event, JSONObject params);
}
