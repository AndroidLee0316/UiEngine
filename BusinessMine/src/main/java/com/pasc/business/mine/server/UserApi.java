package com.pasc.business.mine.server;

import com.pasc.business.mine.net.BaseTokenParam;
import com.pasc.business.mine.params.AddressListParam;
import com.pasc.business.mine.params.AddressParam;
import com.pasc.business.mine.params.DeleteAddressParam;
import com.pasc.business.mine.params.SetDefaultAddressParam;
import com.pasc.business.mine.params.UpdateAddressParam;
import com.pasc.business.mine.resp.AddressIdResp;
import com.pasc.business.mine.resp.AddressListResp;
import com.pasc.business.mine.resp.AreaListResp;
import com.pasc.business.mine.resp.QueryRealNameResp;
import com.pasc.lib.net.param.BaseParam;
import com.pasc.lib.net.resp.BaseResp;
import com.pasc.lib.net.resp.VoidObject;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Copyright (C) 2018 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author yangzijian
 * @date 2018/9/5
 * @des
 * @modify
 **/
public interface UserApi {


    /**
     * 新增用户收货地址
     */
    @FormUrlEncoded
    @POST(UserConfig.NEW_ADDRESS)
    Single<BaseResp<AddressIdResp>> addNewUserAddress(@Field("jsonData") BaseParam<AddressParam> param);

    /**
     * 查询收货地址列表
     */
    @FormUrlEncoded
    @POST(UserConfig.GET_ADDRESS_LIST)
    Single<BaseResp<AddressListResp>> getAddressList(@Field("jsonData") BaseParam<AddressListParam> param);

    /**
     * 设置默认收货地址
     */
    @FormUrlEncoded
    @POST(UserConfig.SET_DEFAULT_ADDRESS)
    Single<BaseResp<VoidObject>> setDefaultAddress(@Field("jsonData") BaseParam<SetDefaultAddressParam> param);

    /**
     * 修改收货地址
     */
    @FormUrlEncoded
    @POST(UserConfig.UPDATE_ADDRESS)
    Single<BaseResp<VoidObject>> updateAddress(@Field("jsonData") BaseParam<UpdateAddressParam> param);

    /**
     * 删除收货地址
     */
    @FormUrlEncoded
    @POST(UserConfig.DELETE_ADDRESS)
    Single<BaseResp<VoidObject>> deleteAddress(@Field("jsonData") BaseParam<DeleteAddressParam> param);

    /**
     * 查询真实姓名 类型
     */
    @FormUrlEncoded
    @POST(UserConfig.QUERY_REALNAME_TYPE)
    Single<BaseResp<QueryRealNameResp>> queryRealnameType(@Field("jsonData") BaseParam<BaseTokenParam> param);


    /**
     * 省市区地址列表
     */
    @FormUrlEncoded
    @POST(UserConfig.AREA_LIST)
    Single<BaseResp<AreaListResp>> queryAreaItem(@Field("jsonData") BaseParam<BaseTokenParam> param);





}
