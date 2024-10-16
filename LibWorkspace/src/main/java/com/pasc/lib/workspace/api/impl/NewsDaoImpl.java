package com.pasc.lib.workspace.api.impl;

import android.content.Context;

import com.pasc.lib.net.ApiGenerator;
import com.pasc.lib.net.param.BaseParam;
import com.pasc.lib.workspace.UserProxy;
import com.pasc.lib.workspace.api.NewsDao;
import com.pasc.lib.workspace.api.ScrollNewsApi;
import com.pasc.lib.workspace.bean.InteractionNewsBean;
import com.pasc.lib.workspace.bean.ScrollNewsParam;
import com.pasc.lib.workspace.bean.ScrollNewsResp;
import com.pasc.lib.workspace.util.BizUtils;

import java.util.List;

public class NewsDaoImpl implements NewsDao {

    private final Context context;

    public NewsDaoImpl(Context context) {
        this.context = context;
    }

    @Override
    public List<InteractionNewsBean> getNews() {
        String userId = UserProxy.getInstance().hasLoggedOn() ? UserProxy.getInstance().getUserId() : "";
        ScrollNewsParam param = new ScrollNewsParam(userId, 0, 10);
        param.setToken(UserProxy.getInstance().getToken());

        ScrollNewsResp netData = BizUtils.getNetData(ApiGenerator.createApi(ScrollNewsApi.class)
                .getScrollNewsSync(new BaseParam<>(param)));
        return netData.scrollNews;
    }
}
