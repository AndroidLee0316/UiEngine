
package com.pasc.business.affair.net;

import com.pasc.lib.base.AppProxy;

/**
 * h5地址
 * Created by duyuan797 on 18/2/1.
 */

public class H5UrlManager {

    public static final String BETA_DOMAIN = "http://smt-stg.yun.city.pingan.com/changshu/stg3/";

    public static final String PRODUCT_DOMAIN = "https://smt.yun.city.pingan.com/changshu/";

//    public static final String H5_HOST = AppProxy.getInstance().isProductionEvn() ? PRODUCT_DOMAIN : BETA_DOMAIN; // H5的域名

    public static final String H5_HOST = AppProxy.getInstance().isProductionEvn() ? PRODUCT_DOMAIN : BETA_DOMAIN; // H5的域名




}
