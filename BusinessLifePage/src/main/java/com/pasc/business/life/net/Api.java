package com.pasc.business.life.net;

import com.pasc.business.life.resp.HouseInfoResp;
import com.pasc.lib.net.resp.BaseV2Resp;
import com.pasc.lib.net.resp.VoidObject;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 功能：
 * <p>
 * created by zoujianbo345
 * data : 2018/9/12
 */
public interface Api {

    @POST(UrlManager.GET_HOUSE_INFO)
    Single<BaseV2Resp<HouseInfoResp>> getHouseInfo(@Body VoidObject object);
}
