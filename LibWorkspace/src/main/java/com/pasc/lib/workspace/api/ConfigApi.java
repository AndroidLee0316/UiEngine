package com.pasc.lib.workspace.api;

import com.pasc.lib.net.param.BaseParam;
import com.pasc.lib.net.resp.BaseResp;
import com.pasc.lib.workspace.BusinessConstants;
import com.pasc.lib.workspace.bean.ConfigParamNew;
import com.pasc.lib.workspace.bean.ConfigResp;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ConfigApi {

    @FormUrlEncoded
    @POST(BusinessConstants.QUERY_SERVICE_CONFIG_NEW)
    Single<BaseResp<ConfigResp>> queryServiceConfig(
            @Field("jsonData") BaseParam<ConfigParamNew> param);

    @FormUrlEncoded
    @POST(BusinessConstants.QUERY_SERVICE_CONFIG_NEW)
    Call<BaseResp<ConfigResp>> queryServiceConfigSync(
            @Field("jsonData") BaseParam<ConfigParamNew> param);

    /**
     * 查询配置系统的配置.
     *
     * @param url   配置系统接口的完整URL.
     * @param param 接口参数.
     * @return 结果.
     */
    @FormUrlEncoded
    @POST
    Call<BaseResp<ConfigResp>> queryServiceConfigSync(@Field("jsonData") BaseParam<ConfigParamNew> param, @Url String url);
}
