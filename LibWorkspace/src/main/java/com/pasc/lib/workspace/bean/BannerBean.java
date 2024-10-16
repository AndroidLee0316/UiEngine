package com.pasc.lib.workspace.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 首页Banner.
 */
public class BannerBean implements Serializable {

    @SerializedName("id")
    private long id;

    @SerializedName("picUrl")
    private String picUrl;

    @SerializedName("picSkipUrl")
    private String picSkipUrl;

    @SerializedName("picName")
    private String picName;

    @SerializedName("serviceId")
    private String serviceId;

    public BannerBean() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicSkipUrl() {
        return picSkipUrl;
    }

    public void setPicSkipUrl(String picSkipUrl) {
        this.picSkipUrl = picSkipUrl;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BannerBean that = (BannerBean) o;

        if (id != that.id) return false;
        if (picUrl != null ? !picUrl.equals(that.picUrl) : that.picUrl != null) return false;
        if (picSkipUrl != null ? !picSkipUrl.equals(that.picSkipUrl) : that.picSkipUrl != null)
            return false;
        if (picName != null ? !picName.equals(that.picName) : that.picName != null) return false;
        return serviceId != null ? serviceId.equals(that.serviceId) : that.serviceId == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (picUrl != null ? picUrl.hashCode() : 0);
        result = 31 * result + (picSkipUrl != null ? picSkipUrl.hashCode() : 0);
        result = 31 * result + (picName != null ? picName.hashCode() : 0);
        result = 31 * result + (serviceId != null ? serviceId.hashCode() : 0);
        return result;
    }
}
