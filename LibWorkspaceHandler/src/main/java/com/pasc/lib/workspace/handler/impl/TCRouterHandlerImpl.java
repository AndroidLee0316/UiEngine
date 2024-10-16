package com.pasc.lib.workspace.handler.impl;

import android.view.View;

import com.pasc.lib.workspace.handler.TangramClickHandler;
import com.pasc.lib.workspace.handler.TangramClickParams;
import com.pasc.lib.workspace.handler.util.ProtocolUtils;
import com.pasc.lib.router.BaseJumper;
import com.tmall.wireless.tangram.structure.BaseCell;

import org.json.JSONObject;

import java.util.LinkedHashMap;

/**
 * 路由处理器。
 */
public class TCRouterHandlerImpl implements TangramClickHandler {

    public TCRouterHandlerImpl() {
    }

    @Override
    public void handle(TangramClickParams params) {
        if (params == null) return;
        String url = params.getClickUrl();
        JSONObject clickParams = params.getClickParams();

        LinkedHashMap<String, String> paramsMap = ProtocolUtils.parseParams(url, clickParams);
        ProtocolUtils.handleParamsUrlPlaceholder(paramsMap);
        BaseJumper.jumpSeriaARouter(url, paramsMap);
    }
}
