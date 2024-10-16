package com.pasc.lib.workspace.api;

import com.pasc.lib.net.param.BaseParam;
import com.pasc.lib.net.resp.BaseResp;
import com.pasc.lib.net.resp.VoidObject;
import com.pasc.lib.workspace.BusinessConstants;
import com.pasc.lib.workspace.bean.TodayWaterQualityResp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WaterQualityApi {

    /**
     * 今日水质列表接口
     */
    @FormUrlEncoded
    @POST(BusinessConstants.MAIN_PAGE_TODAY_WATER_QUALITY_INFO)
    Call<BaseResp<TodayWaterQualityResp>> getMainPageWaterQualityFromNetSync(
            @Field("jsonData") BaseParam<VoidObject> param);
}
