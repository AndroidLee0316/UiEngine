package com.pasc.business.workspace.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 首页证明互动消息列表.
 * Created by chenruihan410 on 2018/08/30.
 */
public class MainPageInteractionNews {

    public long id;

    @SerializedName("titlePicture")
    public String titlePicture;

    @SerializedName("origin")
    public String origin;//来源

    @SerializedName("title")
    public String title;

    @SerializedName("issueDate")
    public String issueDate;

    @SerializedName("informationId")
    public String informationId;

    @SerializedName("isCollect")
    public String isCollect;

    @SerializedName("countRead")
    public String countRead;

    //今日南通 3,时政 4,社会 5,民生 6,国内 7,该字段为空或者传，返回全部数据,
    @SerializedName("type")
    public int type;

    //来源 SourceType，四处公用的一张表，为了避免数据冲突，新增来源判断，增删改查的时候注意sourceType判断
    public int sourceType;

    //资讯类型：（1：视频资讯；2：多图资讯 ；3：单图资讯；4：无图资讯）
    @SerializedName("articleType")
    public int articleType;

    @SerializedName("resourceLinks")
    public String resourceLinks;

    @SerializedName("information_link_h5")
    public String informationLinkH5;
}
