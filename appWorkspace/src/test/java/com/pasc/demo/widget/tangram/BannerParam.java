package com.pasc.demo.widget.tangram;

import com.google.gson.annotations.SerializedName;

public class BannerParam {

    @SerializedName("bannerLocation")
    private int bannerLocation;

    @SerializedName("showType")
    private int showType;

    @SerializedName("versionCode")
    private int versionCode;

    @SerializedName("versionNo")
    private String versionNo;

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public int getBannerLocation() {
        return bannerLocation;
    }

    public void setBannerLocation(int bannerLocation) {
        this.bannerLocation = bannerLocation;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }
}
