package com.pasc.lib.workspace.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 首页搜索---热词
 * Created by chendaixi947 on 2018/6/12
 *
 * @since 1.0
 */
public class MainSearchHotResp {
    @SerializedName("hotWordsList")
    public List<String> hots;//热词列表

}
