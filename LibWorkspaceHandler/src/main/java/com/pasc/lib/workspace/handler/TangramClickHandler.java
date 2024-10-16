package com.pasc.lib.workspace.handler;

import android.view.View;

import com.tmall.wireless.tangram.structure.BaseCell;

import org.json.JSONObject;

/**
 * Tangram的点击处理器接口。
 */
public interface TangramClickHandler {

    void handle(TangramClickParams params);
}
