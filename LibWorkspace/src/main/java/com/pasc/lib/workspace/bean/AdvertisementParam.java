package com.pasc.lib.workspace.bean;

import android.support.annotation.StringDef;

import com.google.gson.annotations.SerializedName;
import com.pasc.lib.workspace.bean.BaseTokenParam;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 闪屏广告
 * Created by chendaixi947 on 18/7/7.
 */
public class AdvertisementParam extends BaseTokenParam {
    public static final String ENTRY_SY = "sy";
    public static final String ENTRY_ZW = "zw";
    public static final String ENTRY_SH = "sh";

    /**
     * 栏目  (sy:首页、 zw :政务、sh: 生活)
     */
    @SerializedName("entry")
    public String entry;

    /**
     * 手机系统   ios  或android
     */
    @SerializedName("showType")
    public String showType = "android ";

    /**
     * 本地版本
     */
    @SerializedName("localVersion")
    public String localVersion;

    /**
     * 弹屏ID  非必传  首次不用传
     */
    @SerializedName("id")
    public String id;

    public AdvertisementParam(@EntryType String entry, String localVersion, String id) {
        this.entry = entry;
        this.localVersion = localVersion;
        this.id = id;
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ENTRY_SH, ENTRY_SY, ENTRY_ZW})
    public @interface EntryType {
    }
}
