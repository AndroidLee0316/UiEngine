package com.pasc.business.life.net;

import com.pasc.business.life.resp.HouseInfoResp;
import com.pasc.lib.net.ApiGenerator;
import com.pasc.lib.net.resp.VoidObject;

import com.pasc.lib.net.transform.RespV2Transformer;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 功能：
 * <p>
 * created by zoujianbo345
 * data : 2018/9/3  personalCredit
 */
public class LifePageBiz {
   /**
     * 从服务器获取房产数据
     */
    public static Flowable<HouseInfoResp> getHouseInfo() {
      RespV2Transformer<HouseInfoResp> respTransformer = RespV2Transformer.newInstance();

        return ApiGenerator.createApi(Api.class)
                .getHouseInfo(VoidObject.getInstance())
                .compose(respTransformer)
                .toFlowable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
