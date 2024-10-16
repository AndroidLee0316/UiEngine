package com.pasc.lib.workspace.bean;

import com.google.gson.annotations.SerializedName;
import com.pasc.lib.workspace.bean.BaseTokenParam;

/**
 * 首页滚动资讯Param
 * Created by duyuan797 on 18/1/26.
 */

public class ScrollNewsParam extends BaseTokenParam {
    @SerializedName("userId")
    public String userId;

    @SerializedName("offSet")
    public int offSet;

    @SerializedName("pageSize")
    public int pageSize;

    public ScrollNewsParam(String userId, int offSet, int pageSize) {
        this.userId = userId;
        this.offSet = offSet;
        this.pageSize = pageSize;
    }
}
