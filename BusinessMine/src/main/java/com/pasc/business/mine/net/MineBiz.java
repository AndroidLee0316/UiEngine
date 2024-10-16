package com.pasc.business.mine.net;

import com.pasc.business.mine.bean.UserMsgInfoParam;
import com.pasc.business.mine.manager.MineManager;
import com.pasc.business.mine.params.ModifyUserParams;
import com.pasc.business.mine.params.UploadHeadImgParam;
import com.pasc.business.mine.resp.InteractionNewsBean;
import com.pasc.business.mine.resp.UploadHeadImgResp;
import com.pasc.business.mine.resp.UserMsgInfoResp;
import com.pasc.lib.base.AppProxy;
import com.pasc.lib.base.util.JsonUtils;
import com.pasc.lib.net.ApiGenerator;
import com.pasc.lib.net.param.BaseParam;
import com.pasc.lib.net.resp.VoidObject;
import com.pasc.lib.net.transform.RespV2Transformer;
import com.pasc.lib.storage.fileDiskCache.FileCacheUtils;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by huanglihou519 on 2018/1/17.
 */

public class MineBiz {

  private static final String TAG = "MineBiz";

  /**
   * 从缓存取出收藏列表
   */
  public static Flowable<List<InteractionNewsBean>> getCollectionInteractionListFromCache(
      int collectType, int offSet, int pageSize) {
    return FileCacheUtils.getListFlowable(
        MineManager.COLLECTION_INTERACTION_NEWS + collectType + AppProxy.getInstance()
            .getUserManager()
            .getUserId(), InteractionNewsBean.class);
  }

  /**
   * 查询个人中心记录的数据
   */
  public static Single<UserMsgInfoResp> getUserMsgInfo() {
    BaseParam<UserMsgInfoParam> param = new BaseParam<>(new UserMsgInfoParam());
    RespV2Transformer<UserMsgInfoResp> respTransformer = RespV2Transformer.newInstance();

    return ApiGenerator.createApi(MineApi.class)
        .getUserMsgInfo(param)
        .compose(respTransformer)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  /**
   * 头像上传
   */
  public static Single<UploadHeadImgResp> uploadHeadImg(String filePath) {
    UploadHeadImgParam fileUpload = new UploadHeadImgParam();
    fileUpload.token = AppProxy.getInstance().getUserManager().getUserInfo().getToken();
    BaseParam<UploadHeadImgParam> param = new BaseParam<>(fileUpload);

    File file = new File(filePath);

    RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
    MultipartBody.Part filePart =
        MultipartBody.Part.createFormData("userHeadImg", file.getName(), fileBody);

    RequestBody paramBody = RequestBody.create(null, JsonUtils.toJson(param));

    RespV2Transformer<UploadHeadImgResp> respTransformer = RespV2Transformer.newInstance();

    return ApiGenerator.createApi(MineApi.class)
        .uploadHeadImg(AppProxy.getInstance().getUserManager().getUserInfo().getToken(), paramBody,
            filePart)
        .compose(respTransformer)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  /**
   * 修改用户信息
   *
   * @param name 用户名
   * @param sex 性别  1男 2 女
   */
  public static Single<VoidObject> modifyUserMsg(String token,String name, int sex) {
    ModifyUserParams params = new ModifyUserParams(name, sex);
    RespV2Transformer<VoidObject> respTransformer = RespV2Transformer.newInstance();
    return ApiGenerator.createApi(MineApi.class)
        .modifyUserMsg(token,params)
        .compose(respTransformer)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }
}
