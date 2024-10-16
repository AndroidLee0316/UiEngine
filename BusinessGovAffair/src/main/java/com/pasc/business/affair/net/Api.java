package com.pasc.business.affair.net;

import com.pasc.business.affair.db.NewsInfo;
import com.pasc.business.affair.params.SearchAffairPermars;

import com.pasc.business.affair.resp.SearchAffairInfoResp;
import com.pasc.lib.net.param.BaseParam;
import io.reactivex.Single;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 功能：
 * <p>
 * created by zoujianbo345
 * data : 2018/9/12
 */
public interface Api {

    @FormUrlEncoded
    @POST(UrlManager.GET_NEWS_INFO)
    Single<SearchAffairInfoResp> getSearchInfoAffair(
            @Field("jsonData") BaseParam<SearchAffairPermars> param);
    @POST(UrlManager.GET_NEWS_INFO)
    Single<List<NewsInfo>> getNewsInfo(
            @Body SearchAffairPermars param);
}
