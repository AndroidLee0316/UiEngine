package com.pasc.lib.workspace.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 首页搜索参数
 * Created by chendaixi on 2018/6/12.
 */

public class MainSearchParams {
    /**
     * 搜索关键词
     */
    @SerializedName("queryText")
    public String queryText;

    /**
     * 搜索所有的服务--针对获取所有的服务
     */
    @SerializedName("queryAll")
    public String queryAll;

    /**
     * 每一页的条数--针对资讯搜索
     */
    @SerializedName("pageSize")
    public String pageSize;

    /**
     * 当前页数--针对资讯搜索
     */
    @SerializedName("offSet")
    public String offSet;

    public MainSearchParams(String queryText) {
        this.queryText = queryText;
    }

    public MainSearchParams(String queryText, String queryAll) {
        this.queryText = queryText;
        this.queryAll = queryAll;
    }

    public MainSearchParams(String queryText, String pageSize, String offSet) {
        this.queryText = queryText;
        this.pageSize = pageSize;
        this.offSet = offSet;
    }
}
