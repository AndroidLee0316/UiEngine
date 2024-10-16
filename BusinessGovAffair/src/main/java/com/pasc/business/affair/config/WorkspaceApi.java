package com.pasc.business.affair.config;

import com.pasc.lib.net.param.BaseParam;
import com.pasc.lib.net.resp.BaseResp;
import com.pasc.lib.workspace.bean.ConfigParamNew;
import com.pasc.lib.workspace.bean.ConfigResp;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Copyright (C) 2018 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author yangzijian
 * @date 2018/9/5
 * @des
 * @modify
 **/
public interface WorkspaceApi {
    @FormUrlEncoded
    @POST(Constants.QUERY_SERVICE_CONFIG_NEW)
    Single<BaseResp<ConfigResp>>queryServiceConfig(
        @Field("jsonData") BaseParam<ConfigParamNew> param);

    @FormUrlEncoded
    @POST(Constants.QUERY_SERVICE_CONFIG_NEW)
    Call<BaseResp<ConfigResp>> queryServiceConfigSync(
        @Field("jsonData") BaseParam<ConfigParamNew> param);


    @POST(Constants.GET_CONFIHG_JSON)
    Single<BaseResp<ConfigRespCs>> getConfigJson(
        @Body ConfigParamCs param);

}
