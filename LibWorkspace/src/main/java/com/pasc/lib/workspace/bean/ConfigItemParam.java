package com.pasc.lib.workspace.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 配置项类.
 * Created by chenruihan410 on 2018/08/07.
 */
public class ConfigItemParam {

    @SerializedName("configId")
    public String configId; // 配置id，对应每一块的页面配置
    @SerializedName("configVersion")
    public String configVersion; // 配置版本号，用于版本控制

    public ConfigItemParam() {
    }

    public ConfigItemParam(String configId, String configVersion) {
        this.configId = configId;
        this.configVersion = configVersion;
    }
}
