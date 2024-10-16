package com.pasc.lib.workspace;

/**
 * 业务常量类.
 * 在工作台组件内，一切的业务常量都放在这里，然后随不同的城市变化而变化，统一换掉.
 * @author chenruihan410
 **/
public class BusinessConstants {

    public static final String DEFAULT_CITY = "南通";

    //跳转公积金时状态查询
    public static final String ACCUMULATION_FUND = "nantongsmt/houseProviFund/houseProviFundBind.do";
    //跳转社保时状态查询
    public static final String SOCIAL_SECURITY = "nantongsmt/socialSecurity/isLogin.do";
    //跳转借书卡时状态查询
    public static final String BOOK_CARD = "nantongsmt/library/cardCheck.do";
    //获取闪屏广告
    public static final String SPLASH_ADVERTISEMENT = "nantongsmt/appProjectile/queryAppProjectileInfo.do";
    //Banner信息获取
    public static final String BANNER_ADVERTISEMENT = "nantongsmt/appBanner/queryAppBannerListInfo.do";


    // 首页搜索---服务
    public static final String MAIN_SEARCH_SERVICE = "nantongsmt/search/queryServiceByText.do";
    //获取数据存在本地
    public static final String MAIN_SEARCH_LOCAL = "nantongsmt/search/seachAllToLocal.do";

    // 首页搜索---热词
    public static final String MAIN_SEARCH_HOT = "nantongsmt/search/getHotWords.do";

    // 首页搜索---全部搜索
    public static final String MAIN_SEARCH_ALL = "nantongsmt/search/seachAllByText.do";
    //办事事项搜索
    public static final String MAIN_SEARCH_AFFAIR = "nantongsmt/search/queryItemByText.do";

    // 首页搜索---资讯
    public static final String MAIN_SEARCH_NEWS = "nantongsmt/search/queryInformationByText.do";
    //首页 我的办事（后台会控制一些是否展示的规则）
    public static final String MAIN_PAGE_AFFAIRS_LIST = "nantongsmt/governmentAffairs/getGovernProcess.do";

    //首页今日水质列表
    public static final String MAIN_PAGE_TODAY_WATER_QUALITY_INFO = "nantongsmt/waterQuality/getWaterQualityToHome.do";

    //首页住房保障
    public static final String HOME_HOUSE_SECURITY = "nantongsmt/housingSecurity/getHousingSecurity.do";

    //首页滚动资讯
    public static final String SCROLL_NEWS = "nantongsmt/societyNews/getNewsList.do";

    //首页热门访谈
    public static final String HOT_INTERVIEW = "nantongsmt/onlineInterview/getHotInterview.do";

    public static  final String API_BANNER = "zuul-service/platform/homePage/queryAppBannerListInfo";

    public static final String QUERY_SERVICE_CONFIG_NEW = "nantongsmt/configSystem/getConfigInfoNew.do"; // 这个是固定的地址

    public final static int MAIN_PAGE_SEARCH_TYPE_TITLE = 1; //标题 类型
    public final static int MAIN_PAGE_SEARCH_TYPE_SERVICE = 7; //服务类型 类型
    public final static int MAIN_PAGE_SEARCH_TYPE_MORE_SERVICE = 2; //更多服务
}
