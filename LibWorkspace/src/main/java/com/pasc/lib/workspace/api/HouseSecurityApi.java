package com.pasc.lib.workspace.api;

import com.pasc.lib.net.param.BaseParam;
import com.pasc.lib.net.resp.BaseResp;
import com.pasc.lib.net.resp.VoidObject;
import com.pasc.lib.workspace.BusinessConstants;
import com.pasc.lib.workspace.bean.HouseSecurityResp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HouseSecurityApi {

    /**
     * 住房保障接口
     */
    @FormUrlEncoded
    @POST(BusinessConstants.HOME_HOUSE_SECURITY)
    Call<BaseResp<HouseSecurityResp>> getHouseSecurityFromNetSync(
            @Field("jsonData") BaseParam<VoidObject> param);
}
