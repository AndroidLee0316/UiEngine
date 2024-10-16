package com.pasc.business.mine.net;

import com.pasc.business.mine.bean.CreditInfo;
import com.pasc.lib.base.AppProxy;
import com.pasc.lib.net.ApiGenerator;
import com.pasc.lib.net.transform.RespV2Transformer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ex-wuhaiping001 on 17/12/18.
 * <p>
 * Modified by huangtebian535 on 2018/06/17.
 */

public class CreditBiz {

  public static Single<CreditInfo> queryCredit() {
    return ApiGenerator.createApi(MineApi.class)
        .queryCredit(AppProxy.getInstance().getUserManager().getUserInfo().getToken(), new Object())
        .compose(RespV2Transformer.<CreditInfo>newInstance())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }
}
