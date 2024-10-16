package com.pasc.business.feedback.net;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ex-lingchun001 on 2018/3/5.
 */

public class FeedBackParam  {
    /** 用户token  **/
    @SerializedName("token")
    public String token;
    @SerializedName("phoneSystemType")
    public String phoneSystemType;//手机系统版本号
    @SerializedName("phoneModel")
    public String phoneModel;//手机型号
    @SerializedName("appVersion")
    public String appVersion;//app版本号
    @SerializedName("question")
    public String question;//反馈内容以及意见
    @SerializedName("picUrls")
    public String imageUrls;//图片

    @SerializedName("userDefineType")
    public String userDefineType;// 用户定义反馈意见类型:0建议 1问题

    public FeedBackParam(String phoneSystemType, String phoneModel, String appVersion,
                         String question, String imageUrls) {
        this.phoneSystemType = phoneSystemType;
        this.phoneModel = phoneModel;
        this.appVersion = appVersion;
        this.question = question;
        this.imageUrls = imageUrls;
    }

    public FeedBackParam(String userDefineType, String phoneSystemType, String phoneModel, String appVersion,
                         String question, String imageUrls) {
        this.userDefineType = userDefineType;
        this.phoneSystemType = phoneSystemType;
        this.phoneModel = phoneModel;
        this.appVersion = appVersion;
        this.question = question;
        this.imageUrls = imageUrls;
    }
}
