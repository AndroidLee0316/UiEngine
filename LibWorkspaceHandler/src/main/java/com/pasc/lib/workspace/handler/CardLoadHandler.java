package com.pasc.lib.workspace.handler;

import com.tmall.wireless.tangram.TangramEngine;
import com.tmall.wireless.tangram.dataparser.concrete.Card;
import com.tmall.wireless.tangram.support.async.AsyncLoader;

/**
 * 卡片加载处理器接口.
 */
public interface CardLoadHandler {

    void loadData(TangramEngine engine, Card card, AsyncLoader.LoadedCallback callback);
}
