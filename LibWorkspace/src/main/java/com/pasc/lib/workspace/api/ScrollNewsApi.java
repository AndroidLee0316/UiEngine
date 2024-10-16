package com.pasc.lib.workspace.api;

import com.pasc.lib.net.param.BaseParam;
import com.pasc.lib.net.resp.BaseResp;
import com.pasc.lib.workspace.BusinessConstants;
import com.pasc.lib.workspace.bean.ScrollNewsParam;
import com.pasc.lib.workspace.bean.ScrollNewsResp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ScrollNewsApi {

    /**
     * 首页滚动资讯
     */
    @FormUrlEncoded
    @POST(BusinessConstants.SCROLL_NEWS)
    Call<BaseResp<ScrollNewsResp>> getScrollNewsSync(
            @Field("jsonData") BaseParam<ScrollNewsParam> paramBaseParam);
}
