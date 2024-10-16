package com.pasc.lib.workspace.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 配置项类.
 * Created by chenruihan410 on 2018/08/07.
 */
public class ConfigItem  implements Serializable {

    @SerializedName("id")
    public int id;

    @SerializedName("configId")
    public String configId;

    @SerializedName("configVersion")
    public String configVersion;

    @SerializedName("chineseName")
    public String chineseName;

    @SerializedName("englishName")
    public String englishName;

    @SerializedName("configContent")
    public String configContent;

    @SerializedName("appVersion")
    public String appVersion;
}
