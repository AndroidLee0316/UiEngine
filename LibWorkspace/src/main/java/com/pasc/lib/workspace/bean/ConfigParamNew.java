package com.pasc.lib.workspace.bean;

import android.os.Build;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置参数类.
 * Created by chenruihan410 on 2018/08/07.
 */
public class ConfigParamNew {
    @SerializedName("configItems")
    public List<ConfigItemParam> configItems; // 需要获取的配置项列表
    @SerializedName("appVersion")
    public String appVersion;  // 应用版本
    @SerializedName("appId")
    public String appId; // 应用ID，对应的是包名
    @SerializedName("testFlag")
    public String testFlag; // 0代表生产环境，1代表测试环境
    @SerializedName("userInfo")
    public ConfigUserInfo configUserInfo;
    @SerializedName("platform")
    public ConfigPlatformInfo platform;

    public ConfigParamNew(Builder builder) {
        this.configItems = builder.configItems;
        this.appVersion = builder.appVersion;
        this.appId = builder.appId;
        this.testFlag = builder.testFlag;
        this.configUserInfo = builder.configUserInfo;
        platform = ConfigPlatformInfo.builder()
                .deviceModel(Build.MODEL)
                .osVersion(Build.VERSION.RELEASE)
                .build();
    }

    public static class Builder {
        private List<ConfigItemParam> configItems;
        private String appVersion;
        private String appId;
        private String testFlag;
        private ConfigUserInfo configUserInfo;

        public Builder configItems(List<ConfigItemParam> configItems) {
            this.configItems = configItems;
            return this;
        }

        public Builder configItem(ConfigItemParam configItem) {
            configItems = new ArrayList<ConfigItemParam>();
            configItems.add(configItem);
            return this;
        }

        public Builder appVersion(String appVersion) {
            this.appVersion = appVersion;
            return this;
        }

        public Builder appId(String appId) {
            this.appId = appId;
            return this;
        }

        public Builder testFlag(String testFlag) {
            this.testFlag = testFlag;
            return this;
        }

        public Builder configUserInfo(ConfigUserInfo configUserInfo) {
            this.configUserInfo = configUserInfo;
            return this;
        }

        public ConfigParamNew build() {
            return new ConfigParamNew(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
