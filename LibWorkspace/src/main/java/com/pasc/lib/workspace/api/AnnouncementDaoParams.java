package com.pasc.lib.workspace.api;

import com.google.gson.annotations.SerializedName;

public class AnnouncementDaoParams {

    @SerializedName("osType")
    private String osType;

    @SerializedName("version")
    private String version;

    @SerializedName("publishPage")
    private String publishPage;

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPublishPage() {
        return publishPage;
    }

    public void setPublishPage(String publishPage) {
        this.publishPage = publishPage;
    }
}
