package com.pasc.lib.workspace.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 首页今日水质请求参数param
 * Created by chendaixi947 on 2018/5/7
 *
 * @since 1.0
 */
public class TodayWaterQualityParam {
    @SerializedName("offSet")
    public int offSet;//当前页数
    @SerializedName("pageSize")
    public int pageSize;//每页的条数，默认10条

    public TodayWaterQualityParam(int offSet, int pageSize) {
        this.offSet = offSet;
        this.pageSize = pageSize;
    }
}
