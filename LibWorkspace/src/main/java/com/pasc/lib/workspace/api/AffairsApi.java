package com.pasc.lib.workspace.api;

import com.pasc.lib.net.param.BaseParam;
import com.pasc.lib.net.resp.BaseResp;
import com.pasc.lib.workspace.BusinessConstants;
import com.pasc.lib.workspace.bean.BaseTokenParam;
import com.pasc.lib.workspace.bean.MyAffairListResp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AffairsApi {

    /**
     * 事务列表
     */
    @FormUrlEncoded
    @POST(BusinessConstants.MAIN_PAGE_AFFAIRS_LIST)
    Call<BaseResp<MyAffairListResp>> getMainPageAffairsListSync(
            @Field("jsonData") BaseParam<BaseTokenParam> param);
}
