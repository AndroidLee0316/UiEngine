package com.pasc.business.affair.config;

import com.google.gson.annotations.SerializedName;

/**
 * 功能：
 * <p>
 * created by zoujianbo345
 * data : 2018/10/22
 */
public class ConfigParamCs {
//    "configId": "配置项id",
//            "appType": "0:ios 1:android",
//            "appVersion": "app版本号"
    public ConfigParamCs(String configId,String appType,String appVersion){
        this.configId = configId;
        this.appType = appType;
        this.appVersion = appVersion;
    }
    @SerializedName("configId")
    public String configId; // 配置id，对应每一块的页面配置
    @SerializedName("appType")
    public String appType; // 配置版本号，用于版本控制
    @SerializedName("appVersion")
    public String appVersion; // 配置版本号，用于版本控制
}
