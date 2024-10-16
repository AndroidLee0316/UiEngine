package com.pasc.lib.workspace.bean;

import com.google.gson.annotations.SerializedName;
import com.pasc.lib.workspace.bean.TodayWaterQualityItem;

import java.util.List;

/**
 * 首页今日水质Response
 * Created by chendaixi947 on 2018/5/7
 *
 * @since 1.0
 */
public class TodayWaterQualityResp  {


    @SerializedName("quality")
    public String quality;
    @SerializedName("list")
    public List<TodayWaterQualityItem> waterQualityInfoList;


}
