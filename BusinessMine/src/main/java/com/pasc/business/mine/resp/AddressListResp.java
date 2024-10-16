package com.pasc.business.mine.resp;

import com.google.gson.annotations.SerializedName;
import com.pasc.business.mine.params.AddressItem;

import java.util.List;

/**
 * 地址列表
 */
public class AddressListResp {
    @SerializedName("userAddressList")
    public List<AddressItem> list;
}
