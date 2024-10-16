package com.pasc.business.mine.resp;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;
import com.pasc.lib.log.PascLog;
import com.raizlabs.android.dbflow.annotation.Column;

/**
 * 政民互动 by zc 2018年05月19日20:28:35
 */
public class InteractionNewsBean  implements MultiItemEntity {

    public @interface SourceType {
        int FROM_COLLECT_NT_NEWS = 1;//来源自 收藏中的南通资讯
        int FROM_COLLECT_ONLINE_NEWS = 2;//来源自 收藏中的在线访谈
        int FROM_MAIN_PAGE_SCROLL_NEWS_TYPE = 3;//来源自 首页滚动资讯
        int FROM_MAIN_PAGE_PEOPLE_LIVE = 4;//来源自 首页底部民生咨询
        int FROM_INTERACTION = 5;//来源自 政民互动
    }


    public long id;

    @SerializedName("titlePicture")
    public String titlePicture;

    @SerializedName("informationType")
    public String informationType;//来源

    @SerializedName("title")
    public String title;

    @SerializedName("subTitle")
    public String subTitle;

    @SerializedName("publishUnit")
    public String publishUnit;

    @SerializedName("infoTime")
    public String infoTime;

    @SerializedName("clickCount")
    public String clickCount;

    @SerializedName("content")
    public String content;

    @SerializedName("h5LinkURL")
    public String h5LinkURL;
    //今日南通 3,时政 4,社会 5,民生 6,国内 7,该字段为空或者传，返回全部数据,
    @SerializedName("type")
    public int type;

    //来源 SourceType，四处公用的一张表，为了避免数据冲突，新增来源判断，增删改查的时候注意sourceType判断
    @Column(name = "sourceType")
    public int sourceType;

    //资讯类型：（1：视频资讯；2：多图资讯 ；3：单图资讯；4：无图资讯）
    @SerializedName("articleType")
    public int articleType;

    @SerializedName("resourceLinks")
    public String resourceLinks;

    @SerializedName("information_link_h5")
    public String informationLinkH5;

    @Override
    public int getItemType() {
        if (articleType >= 1 && articleType <= 5) {//避免未知类型出现
            return articleType;
        } else {
            PascLog.e(InteractionNewsBean.class.getSimpleName(), "InteractionNewsBean articleType:" + articleType);
            return 4;
        }
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        InteractionNewsBean that = (InteractionNewsBean) o;
//
//        return informationId != null ? informationId.equals(that.informationId)
//                : that.informationId == null;
//    }
//
//    @Override
//    public int hashCode() {
//        return informationId != null ? informationId.hashCode() : 0;
//    }

    @Override
    public String toString() {
        return "InteractionNewsBean{" +
                "id=" + id +
                ", titlePicture='" + titlePicture + '\'' +
//                ", origin='" + origin + '\'' +
//                ", title='" + title + '\'' +
//                ", issueDate='" + issueDate + '\'' +
//                ", informationId='" + informationId + '\'' +
//                ", isCollect='" + isCollect + '\'' +
//                ", countRead='" + countRead + '\'' +
                ", type=" + type +
                ", sourceType=" + sourceType +
                ", articleType=" + articleType +
                ", resourceLinks='" + resourceLinks + '\'' +
                ", informationLinkH5='" + informationLinkH5 + '\'' +
                '}';
    }
}
