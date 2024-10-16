package com.pasc.business.mine.server;

import com.pasc.business.mine.net.BaseTokenParam;
import com.pasc.business.mine.net.CSAddressApi;
import com.pasc.business.mine.params.AddressParam;
import com.pasc.business.mine.params.AreaItem;
import com.pasc.business.mine.params.DeleteAddressParam;
import com.pasc.business.mine.params.SetDefaultAddressParam;
import com.pasc.business.mine.params.UpdateAddressParam;
import com.pasc.business.mine.resp.AddressIdResp;
import com.pasc.business.mine.resp.CSAddressListResp;
import com.pasc.business.mine.resp.CSAreaListResp;
import com.pasc.business.mine.resp.EmptyParam;
import com.pasc.business.mine.resp.QueryRealNameResp;
import com.pasc.lib.net.ApiGenerator;
import com.pasc.lib.net.param.BaseParam;
import com.pasc.lib.net.resp.VoidObject;
import com.pasc.lib.net.transform.RespTransformer;
import com.pasc.lib.net.transform.RespV2Transformer;
import com.pasc.lib.storage.fileDiskCache.FileCacheUtils;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/**
 * Created by huanglihou519 on 2018/1/17.
 */

public class CSAddressBiz {

    private static final String TAG = "AddressBiz";

    /**
     * 省市区列表从缓存获取
     */
    public static Flowable<List<AreaItem>> getAreaListFromCache() {
        return FileCacheUtils.getListFlowable(UserConfig.AREA_LIST,AreaItem.class);
    }


    /**
     * 省市区列表从网络获取
     */
    public static Flowable<List<AreaItem>> getAreaListFromNet(String token) {
        EmptyParam param = new EmptyParam();
        //new
        return ApiGenerator.createApi(CSAddressApi.class)
                .queryAreaItem(token,param)
                .compose(RespV2Transformer.<CSAreaListResp>newInstance())
                .flatMap(new Function<CSAreaListResp, SingleSource<List<AreaItem>>>() {

                    @Override
                    public SingleSource<List<AreaItem>> apply(
                            CSAreaListResp areaListResp) {
                        List<AreaItem> list = areaListResp;
                        return Single.just(list);
                    }
                })
                .toFlowable()
                .filter(new Predicate<List<AreaItem>>() {
                    @Override
                    public boolean test(List<AreaItem> movieItems) {
                        return !movieItems.isEmpty();
                    }
                })
                .observeOn(Schedulers.io())
                .doAfterNext(new Consumer<List<AreaItem>>() {
                    @Override
                    public void accept(final List<AreaItem> areaItems) {
                        FileCacheUtils.put(UserConfig.AREA_LIST,areaItems);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 添加地址
     *
     * @return
     */
    public static Single<AddressIdResp> addNewUserAddress(String token, AddressParam addressParam) {
        RespV2Transformer<AddressIdResp> respTransformer = RespV2Transformer.newInstance();
        return ApiGenerator.createApi(CSAddressApi.class)
                .addNewUserAddress(token, addressParam)
                .compose(respTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取收货地址列表
     *
     * @return
     */
    public static Single<CSAddressListResp> getAddressList(String token) {
        RespV2Transformer<CSAddressListResp> respTransformer = RespV2Transformer.newInstance();
        EmptyParam emptyParam = new EmptyParam();
        return ApiGenerator.createApi(CSAddressApi.class)
                .getAddressList(token,emptyParam)
                .compose(respTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 设置默认地址
     *
     * @return
     */
    public static Single<VoidObject> setDefaultAddress(String token, String id) {
        SetDefaultAddressParam param = new SetDefaultAddressParam(id);
        RespV2Transformer<VoidObject> respTransformer = RespV2Transformer.newInstance();
        return ApiGenerator.createApi(CSAddressApi.class)
                .setDefaultAddress(token, param)
                .compose(respTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 修改地址
     */
    public static Single<VoidObject> updateAddress(String token, UpdateAddressParam param) {

        RespV2Transformer<VoidObject> respTransformer = RespV2Transformer.newInstance();
        return ApiGenerator.createApi(CSAddressApi.class)
                .updateAddress(token, param)
                .compose(respTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 删除地址
     */
    public static Single<VoidObject> deleteAddress(String token, String id) {
        DeleteAddressParam param = new DeleteAddressParam(id);
        RespV2Transformer<VoidObject> respTransformer = RespV2Transformer.newInstance();
        return ApiGenerator.createApi(CSAddressApi.class)
                .deleteAddress(token, param)
                .compose(respTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 查询实名认证方式 以及是否绑定了社保
     *
     * @return
     */
    public static Single<QueryRealNameResp> getRealNameType() {
        BaseParam<BaseTokenParam> baseParam = new BaseParam<>(new BaseTokenParam());
        RespTransformer<QueryRealNameResp> respTransformer = RespTransformer.newInstance();

       return ApiGenerator.createApi(UserApi.class).queryRealnameType(baseParam)
                .compose(respTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
