package com.pasc.business.feedback.net;

import com.pasc.lib.net.resp.BaseV2Resp;
import com.pasc.lib.net.resp.VoidObject;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * Copyright (C) 2018 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author yangzijian
 * @date 2018/9/5
 * @des
 * @modify
 **/
public interface FeedBackApi {
  /**
   * 意见反馈
   * 上传图
   */
  @Multipart
  @POST
  Single<BaseV2Resp<String>> uploadFeedbackImage(@Url String url,@Header("token") String token,@Header("type") String type,
                                                 @Part MultipartBody.Part part);



  /**
   * 意见反馈
   */
  @POST
  Single<BaseV2Resp<VoidObject>> addFeedBack(@Url String url, @Header("token") String token,
                                             @Body FeedBackParam param);



}
