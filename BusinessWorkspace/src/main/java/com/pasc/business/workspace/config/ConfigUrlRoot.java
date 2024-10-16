package com.pasc.business.workspace.config;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 动态域名配置类.
 */
public class ConfigUrlRoot implements Serializable {

    @SerializedName("h5UrlRoot")
    private String h5UrlRoot;

    @SerializedName("apiUrlRoot")
    private String apiUrlRoot;

    public String getH5UrlRoot() {
        return h5UrlRoot;
    }

    public void setH5UrlRoot(String h5UrlRoot) {
        this.h5UrlRoot = h5UrlRoot;
    }

    public String getApiUrlRoot() {
        return apiUrlRoot;
    }

    public void setApiUrlRoot(String apiUrlRoot) {
        this.apiUrlRoot = apiUrlRoot;
    }
}
