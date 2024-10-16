package com.pasc.lib.workspace.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 闪屏广告
 * Created by chendaixi947 on 2018/7/7.
 */
//@Table(database = WorkspaceDb.class)
//public class AdvertisementBean extends BaseModel implements Serializable {
public class AdvertisementBean implements Serializable {
    public static final int ENABLE_NO = 0;//禁用
    public static final int ENABLE_YES = 1;//启动

    /**
     * 主键
     */
//    @Column(name = "_id")
//    @PrimaryKey(autoincrement = true)
    public long _id;

    /**
     * 图片url
     */
//    @Column(name = "picUrl")
    @SerializedName("picUrl")
    public String picUrl;

    /**
     * 图片跳转url或json串
     */
//    @Column(name = "picSkipUrl")
    @SerializedName("picSkipUrl")
    public String picSkipUrl;

    /**
     * 图片名称
     */
//    @Column(name = "picName")
    @SerializedName("picName")
    public String picName;

    /**
     * 跳转地址类型  W为外链   N为内链
     */
//    @Column(name = "urlType")
    @SerializedName("urlType")
    public String urlType;

    /**
     * 弹屏ID
     */
//    @Column(name = "id")
    @SerializedName("id")
    public String id;

    /**
     * 弹屏频率:1、仅首次   2、每天一次  3、为每次启动
     */
//    @Column(name = "frequency")
    @SerializedName("frequency")
    public int frequency;

    /**
     * 0为禁用  1为启用
     */
//    @Column(name = "isEnable")
    @SerializedName("isEnable")
    public int isEnable = 1;

    /**
     * 弹屏结束时间
     */
//    @Column(name = "showEndDay")
    @SerializedName("showEndDay")
    public long showEndDay;

    /**
     * 主题
     */
//    @Column(name = "title")
    @SerializedName("title")
    public String title;
}
