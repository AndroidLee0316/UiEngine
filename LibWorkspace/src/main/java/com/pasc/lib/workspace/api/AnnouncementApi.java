package com.pasc.lib.workspace.api;

import com.pasc.lib.net.resp.BaseResp;
import com.pasc.lib.workspace.bean.AnnouncementRsp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.POST;

public interface AnnouncementApi {

    @POST("api/platform/proclamation/online/list")
    Call<BaseResp<AnnouncementRsp>> getAnnouncementSync(
            @Body AnnouncementDaoParams params);
}
