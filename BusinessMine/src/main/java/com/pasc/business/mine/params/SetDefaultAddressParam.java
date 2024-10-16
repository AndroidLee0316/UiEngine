package com.pasc.business.mine.params;

import com.google.gson.annotations.SerializedName;
import com.pasc.business.mine.net.BaseTokenParam;

/**
 * 设置默认地址
 */
public class SetDefaultAddressParam extends BaseTokenParam {

    @SerializedName("id")
    public String id;

    public SetDefaultAddressParam(String id) {
        this.id = id;
    }
}
