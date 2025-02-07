package com.pasc.lib.workspace.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 配置的用户信息类.
 * Created by chenruihan410 on 2018/08/07.
 */
public class ConfigUserInfo {
    @SerializedName("phoneNumber")
    public final String phoneNumber;
    @SerializedName("userId")
    public final String userId;
    @SerializedName("userName")
    public final String userName;

    public ConfigUserInfo(Builder builder) {
        this.phoneNumber = builder.phoneNumber;
        this.userId = builder.userId;
        this.userName = builder.userName;
    }

    public static class Builder {
        private String phoneNumber;
        private String userId;
        private String userName;

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public ConfigUserInfo build() {
            return new ConfigUserInfo(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
