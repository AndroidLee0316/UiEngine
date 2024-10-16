package com.pasc.business.affair.net;

import com.pasc.lib.base.AppProxy;

/**
 * 接口地址
 * Created by duyuan797 on 17/3/22.
 */

public class UrlManager {

    //public static final String BETA_DOMAIN = "smt-app-stg.pingan.com.cn";
    //public static final String BETA_DOMAIN = "cssc-smt-stg1.yun.city.pingan.com";
    public static final String BETA_DOMAIN = "cssc-smt-stg3.yun.city.pingan.com";

    public static final String PRODUCT_DOMAIN = "cssc-smt.yun.city.pingan.com";

    public static final String
        API_HOST = AppProxy.getInstance().isProductionEvn() ? PRODUCT_DOMAIN : BETA_DOMAIN; // api的域名

    public static final String PRODUCT_HOST = "https://" + PRODUCT_DOMAIN + "/api/";

    public static final String BETA_HOST = "http://" + BETA_DOMAIN + "/api/";
    //    public static final String BETA_HOST = "http://103.28.215.220:58080/";

    public static final String DEV_HOST = "http://10.180.111.21:8080/";


    /**
     * 临时web页面
     */


    //意见反馈附件
    public static final String FEED_BACK_IMAGE = "platform/feedback/uploadFeedbackImage";

    //意见反馈
    public static final String ADD_FEED_BACK = "platform/feedback/submitFeedback";

    //热门搜索
    public static final String SEARCH_HOT = "search/hotword/list";
    public static final String SEARCH_SERVICE = "search/serving/list";

    //资讯搜索
    public static final String SEARCH_INFORMATION = "search/information/list";
    //办事大厅搜索全部
    public static final String SEARCH_SERVICE_ALL = "search/servicehall/list";

    //办事大厅搜索部门
    public static final String SEARCH_SERVICE_DEPARTMENT = "search/servicehall/department/list";
    //办事大厅搜索事项
    public static final String SEARCH_SERVICE_MATTER = "search/servicehall/matter/list";
    //办事大厅搜索事项
    public static final String SEARCH_MAIN_ALL = "search/home/list";

    //办事大厅搜索事项
    public static final String SEARCH_SEARVICE = "search/serving/list";
    //    头像上传
    //获取资讯列表；
    public static final String GET_NEWS_INFO = "platform/information/getList";

    /**
     * 获取bannar
     */
    public static final String GET_HOME_BANNAR = "platform/homePage/queryAppBannerListInfo";
    /**
     * 获取推荐
     */
    public static final String GET_HOME_RECOMMEND = "platform/homePage/getReconmendList";

    //获取附近地图 poi检索列表
    public static final String NEARBY_POI_INFO = "platform/mapSearchInfo/getList";
    //获取房产信息；
    public static final String GET_HOUSE_INFO = "platform/haofang/getDataDetail";
    //获取疾病预测信息；
    public static final String GET_CDSSS_INFO = "platform/cdss/userLoginAuth";
    //获取疾病预测解密后信息；
    public static final String GET_CDSSS_DECODE_DATA = "platform/cdss/decodeData";

    public static final String CREDIT_SCORE = "platform/qianhai/myCredit";

    //根据手机号获取用户信息
    public static final String USER_INFO_BY_MOBILE = "auth/user/getUserInfoByMobile";

    //获取收货地址列表
    public static final String GET_ADDRESS_LIST = "auth/address/getUserAddrByUserId";

    public static final String ONE_CARD_AUTHUTL_TEST = "http://cssc-cc-stg.yun.city.pingan.com/cs-app/";
    public static final String ONE_CARD_AUTHUTL_PTODUCT = "http://cssc-cc.yun.city.pingan.com/cs-app/";
    public static final String ONE_CARD_PLALFORM_TEST = "http://sc-cc-stg.yun.city.pingan.com/app-web/";
    public static final String ONE_CARD_PLALFROM_PTODUCT = "http://sc-cc.yun.city.pingan.com/app-web/";

    public static final String OPEN_PLALFROM_TEST = "http://cssc-smt-stg3.yun.city.pingan.com/api/auth";
    public static final String OPEN_PLALFROM_PRODUCT = "http://cssc-smt.yun.city.pingan.com/api/auth";

    public static final String
        ONE_CARD_AUTHUTL_URL = AppProxy.getInstance().isProductionEvn() ? ONE_CARD_AUTHUTL_PTODUCT : ONE_CARD_AUTHUTL_TEST;
    public static final String ONE_CARD_PLATFORM_URL = AppProxy.getInstance().isProductionEvn() ? ONE_CARD_PLALFROM_PTODUCT : ONE_CARD_PLALFORM_TEST;
    public static final String
        OPEN_PLALFROM_URL = AppProxy.getInstance().isProductionEvn() ? OPEN_PLALFROM_PRODUCT : OPEN_PLALFROM_TEST;


}


