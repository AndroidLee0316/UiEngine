package com.pasc.business.mine.params;

import com.google.gson.annotations.SerializedName;
import com.pasc.business.mine.net.BaseTokenParam;

/**
 * 删除地址列表
 */
public class DeleteAddressParam extends BaseTokenParam {
    @SerializedName("id")
    public String id;

    public DeleteAddressParam(String id) {
        this.id = id;
    }
}
