package com.pasc.lib.workspace.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 首页热门访谈
 * Created by duyuan797 on 18/1/27.
 */

//@Table(database = WorkspaceDb.class)
//public class HotInterview extends BaseModel {
public class HotInterview {
//    @Column(name = "interviewId")
//    @PrimaryKey
    public String interviewId;
//    @Column(name = "title")
    @SerializedName("title")
    public String title;
//    @Column(name = "picUrl")
    @SerializedName("picUrl")
    public String picUrl;//海报
//    @Column(name = "guest")
    @SerializedName("guest")
    public String guest;//嘉宾
//    @Column(name = "date")
    @SerializedName("date")
    public String date;
//    @Column(name = "count")
    @SerializedName("count")
    public String count;//评论数
//    @Column(name = "interviewReviewDetailUrl")
    @SerializedName("interviewReviewDetailUrl")
    public String detailUrl;//详情
//    @Column(name = "isCollect")
    @SerializedName("isCollect")
    public String isCollect;

    public HotInterview setCollected(boolean isCollect) {
        this.isCollect = isCollect ? "Y" : "N";
        return this;
    }

    public boolean isCollected() {
        return null != isCollect && isCollect.equals("Y");
    }
}
