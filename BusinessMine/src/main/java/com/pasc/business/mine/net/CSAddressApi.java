package com.pasc.business.mine.net;

import com.pasc.business.mine.manager.MineManager;
import com.pasc.business.mine.params.AddressParam;
import com.pasc.business.mine.params.DeleteAddressParam;
import com.pasc.business.mine.params.SetDefaultAddressParam;
import com.pasc.business.mine.params.UpdateAddressParam;
import com.pasc.business.mine.resp.AddressIdResp;
import com.pasc.business.mine.resp.CSAddressListResp;
import com.pasc.business.mine.resp.CSAreaListResp;
import com.pasc.business.mine.resp.EmptyParam;
import com.pasc.business.mine.resp.QueryRealNameResp;
import com.pasc.lib.net.resp.BaseV2Resp;
import com.pasc.lib.net.resp.VoidObject;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * 功能：
 * <p>
 * create by lichangbao702
 * email : 1035268077@qq.com
 * date : 2018/10/19
 */
public interface CSAddressApi {



    /**
     * 新增用户收货地址
     */
    @POST(MineManager.NEW_ADDRESS)
    Single<BaseV2Resp<AddressIdResp>> addNewUserAddress(
            @Header("token") String token,
            @Body AddressParam param);

    /**
     * 查询收货地址列表
     */
    @POST(MineManager.GET_ADDRESS_LIST)
    Single<BaseV2Resp<CSAddressListResp>> getAddressList(
            @Header("token") String token,
            @Body EmptyParam param);

    /**
     * 设置默认收货地址
     */
    @POST(MineManager.SET_DEFAULT_ADDRESS)
    Single<BaseV2Resp<VoidObject>> setDefaultAddress(
            @Header("token") String token,
            @Body SetDefaultAddressParam param);

    /**
     * 修改收货地址
     */
    @POST(MineManager.UPDATE_ADDRESS)
    Single<BaseV2Resp<VoidObject>> updateAddress(
            @Header("token") String token,
            @Body UpdateAddressParam param);

    /**
     * 删除收货地址
     */
    @POST(MineManager.DELETE_ADDRESS)
    Single<BaseV2Resp<VoidObject>> deleteAddress(
            @Header("token") String token,
            @Body DeleteAddressParam param);

    /**
     * 查询真实姓名 类型
     */
    @POST(MineManager.QUERY_REALNAME_TYPE)
    Single<BaseV2Resp<QueryRealNameResp>> queryRealnameType(
            @Header("token") String token,
            @Body BaseTokenParam param);

    /**
     * 省市区地址列表
     */
    @POST(MineManager.AREA_LIST)
    Single<BaseV2Resp<CSAreaListResp>> queryAreaItem(
            @Header("token") String token,
            @Body EmptyParam param);


}
