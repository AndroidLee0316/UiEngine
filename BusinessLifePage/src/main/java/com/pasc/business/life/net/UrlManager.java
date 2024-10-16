package com.pasc.business.life.net;

import com.pasc.lib.base.AppProxy;

/**
 * 接口地址
 * Created by duyuan797 on 17/3/22.
 */

public class UrlManager {

    public static final String BETA_DOMAIN = "cssc-smt-stg3.yun.city.pingan.com";

    public static final String PRODUCT_DOMAIN = "cssc-smt.yun.city.pingan.com";

    public static final String
        API_HOST = AppProxy.getInstance().isProductionEvn() ? PRODUCT_DOMAIN : BETA_DOMAIN; // api的域名

    //获取房产信息；
    public static final String GET_HOUSE_INFO = "platform/haofang/getDataDetail";
}


