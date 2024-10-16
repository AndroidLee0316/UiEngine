package com.pasc.lib.workspace.bean;

import com.google.gson.annotations.SerializedName;
import com.pasc.lib.workspace.bean.BannerBean;

import java.util.List;

/**
 * Created by chendaixi947 on 2018/7/7
 *
 * @since 1.0
 */
public class AppBannerRsp {
    @SerializedName("appBannerListInfo")
    public List<BannerBean> bannerBeans;
}
