package com.pasc.lib.workspace.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 首页办事事项搜索结果
 * Created by zc on 2018-08-08 19:45:29
 *
 * @since 1.0
 */
public class MainSearchAffairResp {
    @SerializedName("list")
    public List<MainSearchAffairItem> list;//资讯列表

}
