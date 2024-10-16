package com.pasc.lib.workspace.bean;

import android.support.annotation.StringDef;

import com.google.gson.annotations.SerializedName;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class BannerParam extends BaseTokenParam {
    public static final String ENTRY_SY = "sy";
    public static final String ENTRY_SZYX = "szyx";

    /**
     * 页面入口  (sy:首页、 szyx :深圳印象)
     */
    @SerializedName("entry")
    public String entry;

    /**
     * 手机系统   ios  或android
     */
    @SerializedName("showType")
    public String showType = "android ";

    /**
     * 手机需要刘海banner传 Y   不需要刘海图片传N  不传默认为N
     */
    @SerializedName("isIphoneX")
    public String isIphoneX = "N";

    @SerializedName("appVersion")
    public String appVersion;

    @SerializedName("versionCode")
    public int versionCode;

    public BannerParam(@BannerEntryType String entry) {
        this.entry = entry;
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ENTRY_SY, ENTRY_SZYX})
    public @interface BannerEntryType {
    }
}
