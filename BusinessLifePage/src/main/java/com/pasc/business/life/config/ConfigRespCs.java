package com.pasc.business.life.config;

import com.google.gson.annotations.SerializedName;

/**
 * 功能：
 * <p>
 * created by zoujianbo345
 * data : 2018/10/22
 */
public class ConfigRespCs {
    //    "appVersion": "app版本号",
    ////            "configContent": "模块配置内容信息"@SerializedName("configId")
    ////            "configContent": "模块配置内容信息"@SerializedName("configId")
    @SerializedName("appVersion")
    public String appVersion;
    @SerializedName("configContent")
    public String configContent;
}
