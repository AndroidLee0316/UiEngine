package com.pasc.lib.workspace.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 配置的平台信息类.
 * Created by chenruihan410 on 2018/08/07.
 */
public class ConfigPlatformInfo {

    @SerializedName("osType")
    public final String osType;
    @SerializedName("osVersion")
    public final String osVersion;
    @SerializedName("deviceModel")
    public final String deviceModel;

    private ConfigPlatformInfo(Builder builder) {
        this.osType = builder.osType;
        this.osVersion = builder.osVersion;
        this.deviceModel = builder.deviceModel;
    }

    public static class Builder {
        private String osType = "android";
        private String osVersion;
        private String deviceModel;

        public Builder osType(String osType) {
            this.osType = osType;
            return this;
        }

        public Builder osVersion(String osVersion) {
            this.osVersion = osVersion;
            return this;
        }

        public Builder deviceModel(String deviceModel) {
            this.deviceModel = deviceModel;
            return this;
        }

        public ConfigPlatformInfo build() {
            return new ConfigPlatformInfo(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
