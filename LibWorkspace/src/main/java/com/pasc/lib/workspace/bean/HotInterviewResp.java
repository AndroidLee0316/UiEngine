package com.pasc.lib.workspace.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 首页热门访谈Response
 * Created by duyuan797 on 18/1/27.
 */

public class HotInterviewResp {
    @SerializedName("hotInterviewList")
    public List<HotInterview> hotInterviewList;
}
