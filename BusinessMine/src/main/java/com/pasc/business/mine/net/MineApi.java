package com.pasc.business.mine.net;

import com.pasc.business.mine.bean.CreditInfo;
import com.pasc.business.mine.bean.UserMsgInfoParam;
import com.pasc.business.mine.manager.MineManager;
import com.pasc.business.mine.params.CollectionInteractionParam;
import com.pasc.business.mine.params.ModifyUserParams;
import com.pasc.business.mine.resp.InteractionNewsResp;
import com.pasc.business.mine.resp.UploadHeadImgResp;
import com.pasc.business.mine.resp.UserMsgInfoResp;
import com.pasc.lib.net.param.BaseParam;
import com.pasc.lib.net.resp.BaseResp;
import com.pasc.lib.net.resp.BaseV2Resp;
import com.pasc.lib.net.resp.VoidObject;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Copyright (C) 2018 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author yangzijian
 * @date 2018/9/5
 * @des
 * @modify
 **/
public interface MineApi {




  /**
   * 查询信用
   */
  @POST(MineManager.CREDIT_QUERY)
  Single<BaseV2Resp<CreditInfo>> queryCredit(
      @Header("token") String token,@Body Object o);

  /**
   * 获取用户 消息 收藏  等数量
   */
  @FormUrlEncoded
  @POST(MineManager.USER_MSG_NUM)
  Single<BaseV2Resp<UserMsgInfoResp>> getUserMsgInfo(
      @Field("jsonData") BaseParam<UserMsgInfoParam> param);

  /**
   * 政民互动  收藏的新闻列表
   */
  @FormUrlEncoded
  @POST(MineManager.COLLECTION_INTERACTION_NEWS)
  Single<BaseResp<InteractionNewsResp>> getCollectionInteractionNews(
      @Field("jsonData") BaseParam<CollectionInteractionParam> param);

  /**
   * 用户头像上传
   */
  @Multipart @POST(MineManager.USER_HEAD_IMG_UPLOAD)
  Single<BaseV2Resp<UploadHeadImgResp>> uploadHeadImg(@Header("token") String token,@Part("jsonData") RequestBody param,
      @Part MultipartBody.Part file);

  /**
   * 修改用户信息
   */
  @POST(MineManager.MODIFY_USER_MSG) Single<BaseV2Resp<VoidObject>> modifyUserMsg(@Header("token") String token,
      @Body ModifyUserParams param);
}
