package com.pasc.business.mine.resp;

import com.google.gson.annotations.SerializedName;
import com.pasc.business.mine.params.AreaItem;

import java.util.List;

public class AreaListResp {
    @SerializedName("userAddressList")
    public List<AreaItem> list;
}
