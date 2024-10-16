package com.pasc.lib.workspace;

import android.content.Context;

import com.pasc.lib.workspace.bean.InteractionNewsResp;
import com.pasc.lib.workspace.bean.MainPageWeatherInfo;

public class BizProxy implements BizHandler {

    private static final BizProxy ourInstance = new BizProxy();
    private BizHandler bizHandler;

    static BizProxy getInstance() {
        return ourInstance;
    }

    private BizProxy() {
    }

    public void init(BizHandler bizHandler) {
        if (bizHandler == null) {
            throw new RuntimeException("BizHandler不能为空");
        }
        this.bizHandler = bizHandler;
    }

    @Override
    public InteractionNewsResp getPeopleLiveNewsFromNet(int pageSize, int source) {
        checkBizHandler();
        return bizHandler.getPeopleLiveNewsFromNet(pageSize, source);
    }

    @Override
    public InteractionNewsResp getPeopleLiveNewsFromCache(int pageSize, int source) {
        checkBizHandler();
        return bizHandler.getPeopleLiveNewsFromCache(pageSize, source);
    }

    private void checkBizHandler() {
        if (bizHandler == null) {
            throw new RuntimeException("请先初始化BizProxy");
        }
    }

    @Override
    public int getUnReadMessageCount() {
        checkBizHandler();
        return bizHandler.getUnReadMessageCount();
    }
}
