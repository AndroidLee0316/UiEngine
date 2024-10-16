package com.pasc.business.mine.callback;

import com.pasc.business.mine.bean.JsAddressJson;
import com.pasc.business.mine.params.AddressItem;
import com.pasc.business.mine.resp.AddressIdResp;

/*
 * Copyright (C) 2016 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author chendaixi947
 * @version 1.0
 * @date 2018/8/24
 * @des
 * @modify
 */
public interface UserAddressChangeListener {
    /**添加地址了*/
    void  addAddress(JsAddressJson addressItem);

    /**修改地址了*/
    void updateAddress();

    /**删除地址了*/
    void  deleteAddress();

}
