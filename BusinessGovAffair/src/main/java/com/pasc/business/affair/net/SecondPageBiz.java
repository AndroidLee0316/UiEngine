package com.pasc.business.affair.net;

import com.pasc.business.affair.db.NewsInfo;
import com.pasc.business.affair.resp.SearchAffairInfoResp;
import com.pasc.business.affair.params.SearchAffairPermars;

import com.pasc.lib.net.ApiGenerator;
import com.pasc.lib.net.param.BaseParam;
import com.pasc.lib.net.transform.RespTransformer;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 功能：
 * <p>
 * created by zoujianbo345
 * data : 2018/9/3  personalCredit
 */
public class SecondPageBiz {
    /**
     * 从服务器获取搜索服务数据
     */
    public static Flowable<Object> test(String query) {
//        RespTransformer<SearchInfoResp> respTransformer = RespTransformer.newInstance();
//        // new
//        Type resType = new TypeToken<BaseResp<SearchInfoResp>>() {
//        }.getType();
//        Map<String, String> paraMap = ParaUtil.getParaMap(new BaseParam<>(new Test()));
//        return RxJavaNetManager.getInstance()
//                .<BaseResp<SearchInfoResp>>
//                        postSingle(resType, UrlManager.TEST, null, paraMap)
//                //old
//                //        return ApiGenerator.createApi(Api.class)
//                //                .getHouseSecurityFromNet(new BaseParam<>(VoidObject.getInstance()))
//                .compose(respTransformer)
//                .toFlowable()
//                .observeOn(Schedulers.io())
//                //               .doOnNext(new Consumer<HouseSecurityResp>() {
//                //                   @Override
//                //                   public void accept(HouseSecurityResp houseSecurityResp) throws Exception {
//                //
//                //                   }
//                //               })
//
//
//                .observeOn(AndroidSchedulers.mainThread());
        return null;
    }
    /**
     * 从服务器获取搜索政务数据
     */
    public static Flowable<SearchAffairInfoResp> getSearchAffairInfo(String query) {
        BaseParam<SearchAffairPermars> params = new BaseParam<>(new SearchAffairPermars(query,""));
        RespTransformer respTransformer = RespTransformer.newInstance();

        return ApiGenerator.createApi(Api.class)
                .getSearchInfoAffair(params)
                .compose(respTransformer)
                .toFlowable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    /**
     * 获取资讯
     */
    public static Flowable<List<NewsInfo>> getNewsInfo(String type, int pageNum, int pageSize) {
        SearchAffairPermars params = new SearchAffairPermars(type,pageNum,pageSize);
        RespTransformer respTransformer = RespTransformer.newInstance();

        return ApiGenerator.createApi(Api.class)
                .getNewsInfo(params)
                .compose(respTransformer)
                .toFlowable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
