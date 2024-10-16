package com.pasc.lib.workspace.api;

import com.pasc.lib.net.param.BaseParam;
import com.pasc.lib.net.resp.BaseResp;
import com.pasc.lib.workspace.BusinessConstants;
import com.pasc.lib.workspace.bean.AppBannerRsp;
import com.pasc.lib.workspace.bean.BannerParam;
import com.pasc.lib.workspace.bean.ConfigParamNew;
import com.pasc.lib.workspace.bean.ConfigResp;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface BannerApi {

    /**
     * Banner广告获取
     */
    @FormUrlEncoded
    @POST(BusinessConstants.BANNER_ADVERTISEMENT)
    Call<BaseResp<AppBannerRsp>> getBannerSync(
            @Field("jsonData") BaseParam<BannerParam> param);

    /**
     * Banner广告获取
     */
    @FormUrlEncoded
    @POST(BusinessConstants.BANNER_ADVERTISEMENT)
    Single<BaseResp<AppBannerRsp>> getBanner(
            @Field("jsonData") BaseParam<BannerParam> param);
}
