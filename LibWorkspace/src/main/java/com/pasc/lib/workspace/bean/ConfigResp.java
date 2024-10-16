package com.pasc.lib.workspace.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 从服务器返回的配置类.
 * Created by chenruihan410 on 2018/08/07.
 */
public class ConfigResp {
    @SerializedName("list")
    public List<ConfigItem> list;
}
