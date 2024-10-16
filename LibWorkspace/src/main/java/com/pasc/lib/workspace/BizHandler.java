package com.pasc.lib.workspace;

import com.pasc.lib.workspace.bean.InteractionNewsResp;

public interface BizHandler {

    InteractionNewsResp getPeopleLiveNewsFromNet(int pageSize, int source);

    InteractionNewsResp getPeopleLiveNewsFromCache(int pageSize, int source);

    int getUnReadMessageCount();
}
