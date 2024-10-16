package com.pasc.business.mine.bean;

import com.google.gson.annotations.SerializedName;
import com.pasc.business.mine.BuildConfig;
import com.pasc.business.mine.net.BaseTokenParam;

public class UserMsgInfoParam extends BaseTokenParam {
    @SerializedName("osType")
    public String osType = "2";
    @SerializedName("versionCode")
    public String versionCode = BuildConfig.VERSION_CODE + "";
    //@SerializedName("loginTime")
    //public String loginTime = UserRouterBiz.fetchUserInfoServices().getUser(). + "";
}
