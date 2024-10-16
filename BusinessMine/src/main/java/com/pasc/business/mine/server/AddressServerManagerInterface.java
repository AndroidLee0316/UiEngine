package com.pasc.business.mine.server;


import com.pasc.business.mine.callback.OnResultListener;
import com.pasc.business.mine.params.AddressParam;
import com.pasc.business.mine.params.AreaItem;
import com.pasc.business.mine.params.UpdateAddressParam;
import com.pasc.business.mine.resp.AddressIdResp;
import com.pasc.business.mine.resp.AddressListResp;
import com.pasc.business.mine.resp.QueryRealNameResp;
import com.pasc.lib.net.resp.VoidObject;

import java.util.List;

/**
 * 功能：
 * <p>
 * create by lichangbao702
 * email : 1035268077@qq.com
 * date : 2018/10/19
 */
public interface AddressServerManagerInterface {

    /**
     * 省市区列表从缓存获取
     */
    public void getAreaListFromCache(OnResultListener<List<AreaItem>> listener);


    /**
     * 省市区列表从网络获取
     */
    public void getAreaListFromNet(OnResultListener<List<AreaItem>> listener) ;

    /**
     * 添加地址
     *
     * @return
     */
    public void addNewUserAddress(AddressParam param, OnResultListener<AddressIdResp> listener);

    /**
     * 获取收货地址列表
     *
     * @return
     */
    public void getAddressList(OnResultListener<AddressListResp> listener);


    /**
     * 设置默认地址
     *
     * @return
     */
    public void setDefaultAddress(final String id, OnResultListener<VoidObject> listener);

    /**
     * 修改地址
     */
    public void updateAddress(UpdateAddressParam param, OnResultListener<VoidObject> listener);

    /**
     * 删除地址
     */
    public void deleteAddress(String id, OnResultListener<VoidObject> listener) ;


    /**
     * 查询实名认证方式 以及是否绑定了社保
     *
     * @return
     */
    public void getRealNameType(OnResultListener<QueryRealNameResp> listener);


    public void cleanDispoables();

}
