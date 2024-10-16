package com.pasc.lib.workspace.handler.impl;

import android.app.Activity;
import android.text.TextUtils;

import com.pasc.lib.workspace.handler.WebProtocolHandler;
import com.pasc.lib.hybrid.PascHybrid;
import com.pasc.lib.hybrid.behavior.WebPageConfig;
import com.pasc.lib.hybrid.nativeability.WebStrategy;
import com.pasc.lib.widget.tangram.util.ConfigUtils;

import java.util.LinkedHashMap;

public class WebProtocolHandlerImpl implements WebProtocolHandler {

    public static final String URL_PARAMS_APP_TITLE = "appTitle"; // 应用工具栏上的标题
    public static final String URL_PARAMS_APP_TOOLBAR = "appToolbar"; // 应用工具栏，值分别为VISIABLE(显示)或者GONE(隐藏)
    public static final int TOOLBAR_VISIABLE = 0; // 工具栏显示
    public static final int TOOLBAR_GONE = 1; // 工具栏隐藏
    public static final String TOOLBAR_VALUE_GONE = "GONE";
    public static final String TOOLBAR_VALUE_VISIABLE = "VISIABLE";

    @Override
    public void handle(Activity activity, String url) {
        handle(activity, url, null);
    }

    @Override
    public void handle(Activity activity, String url, String title) {
        WebStrategy strategy = new WebStrategy().setUrl(url);
        LinkedHashMap<String, String> urlParams = ConfigUtils.parseParamsByRegex(url);
        String appTitle = urlParams.get(URL_PARAMS_APP_TITLE);
        if (!TextUtils.isEmpty(appTitle)) {
            strategy.setTitle(appTitle);
        }
        if (!TextUtils.isEmpty(title)) {
            strategy.setTitle(title);
        }
        String appToolbar = urlParams.get(URL_PARAMS_APP_TOOLBAR);
        if (TOOLBAR_VALUE_GONE.equalsIgnoreCase(appToolbar)) {
            strategy.setToolBarVisibility(TOOLBAR_GONE);
        } else if (TOOLBAR_VALUE_VISIABLE.equalsIgnoreCase(appToolbar)) {
            strategy.setToolBarVisibility(TOOLBAR_VISIABLE);
        }

        WebPageConfig webPageConfig = getWebPageConfig();
        if (webPageConfig != null) {
            PascHybrid.getInstance().with(webPageConfig).start(activity, strategy);
        } else {
            PascHybrid.getInstance().start(activity, strategy);
        }
    }

    protected WebPageConfig getWebPageConfig() {
        return null;
    }
}
