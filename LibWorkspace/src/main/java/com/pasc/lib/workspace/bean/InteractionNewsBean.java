package com.pasc.lib.workspace.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;
import com.pasc.lib.log.PascLog;

public class InteractionNewsBean implements MultiItemEntity {

    public @interface SourceType {
        int FROM_COLLECT_NT_NEWS = 1;//来源自 收藏中的南通资讯
        int FROM_COLLECT_ONLINE_NEWS = 2;//来源自 收藏中的在线访谈
        int FROM_MAIN_PAGE_SCROLL_NEWS_TYPE = 3;//来源自 首页滚动资讯
        int FROM_MAIN_PAGE_PEOPLE_LIVE = 4;//来源自 首页底部民生咨询
        int FROM_INTERACTION = 5;//来源自 政民互动
    }

    @SerializedName("id")
    public long id;

    @SerializedName("titlePicture")
    public String titlePicture;

    @SerializedName("origin")
    public String origin; //来源

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

    @SerializedName("typeName")
    public String typeName; // 类型名称

    //来源 SourceType，四处公用的一张表，为了避免数据冲突，新增来源判断，增删改查的时候注意sourceType判断
    @SerializedName("sourceType")
    public int sourceType;

    //资讯类型：（1：视频资讯；2：多图资讯 ；3：单图资讯；4：无图资讯）
    @SerializedName("articleType")
    public int articleType;

    /**
     * 图片地址，多个图片，中间以逗号隔开.
     */
    @SerializedName("resourceLinks")
    public String resourceLinks;

    @SerializedName("information_link_h5")
    public String informationLinkH5;

    @SerializedName("isTop")
    public int isTop; // 0不需要置顶，1需要置顶

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getInformationId() {
        return informationId;
    }

    public void setInformationId(String informationId) {
        this.informationId = informationId;
    }

    public String getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }

    public String getCountRead() {
        return countRead;
    }

    public void setCountRead(String countRead) {
        this.countRead = countRead;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public int getArticleType() {
        return articleType;
    }

    public void setArticleType(int articleType) {
        this.articleType = articleType;
    }

    public String getResourceLinks() {
        return resourceLinks;
    }

    public void setResourceLinks(String resourceLinks) {
        this.resourceLinks = resourceLinks;
    }

    public String getInformationLinkH5() {
        return informationLinkH5;
    }

    public void setInformationLinkH5(String informationLinkH5) {
        this.informationLinkH5 = informationLinkH5;
    }

    public int getIsTop() {
        return isTop;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }

    public String getTitlePicture() {
        return titlePicture;
    }

    public void setTitlePicture(String titlePicture) {
        this.titlePicture = titlePicture;
    }

    @Override
    public int getItemType() {
        if (articleType >= 1 && articleType <= 5) {//避免未知类型出现
            return articleType;
        } else {
            PascLog.e(InteractionNewsBean.class.getSimpleName(), "InteractionNewsBean articleType:" + articleType);
            return 4;
        }
    }
}