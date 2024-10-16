package com.pasc.business.affair.config;

/**
 * 功能：
 * <p>
 * created by zoujianbo345
 * data : 2018/9/30
 */
public class Constants {
    public static final String TAG = "BusinessWorkspace";
    public static final String MODULE_NAME = "BusinessWorkspace";
    public static final String BASE_KEY_CONFIG = "config";

    public static final String HOST_CONFIG = "https://smt-app.pingan.com.cn/";
    //新版更多服务-根据编码查询服务
    public static final String QUERY_SERVICE_CONFIG_NEW = HOST_CONFIG + "smtapp/configSystem/getConfigInfoNew.do";


    //1：视频资讯；2：多图资讯 ；3：单图资讯；4：无图资讯 ；5：大图类型;6:服务搜索结果标题模块；7：服务模块;8:分割线 9 事项 10 更多
    public final static int TYPE_01 = 1;
    public final static int TYPE_02 = 2;
    public final static int TYPE_03 = 3;
    public final static int TYPE_04 = 4;
    public final static int TYPE_05 = 5;
    public final static int TYPE_06 = 6;
    public final static int TYPE_07 = 7;
    public final static int TYPE_08 = 8;
    public final static int TYPE_09 = 9;
    public final static int TYPE_10 = 10;
    public final static int TYPE_11 = 11;
    public final static int TYPE_12 = 12;

    public final static int MAIN_PAGE_SEARCH_TYPE_TITLE = 1; //标题 类型
    public final static int MAIN_PAGE_SEARCH_TYPE_SERVICE = 7; //服务类型 类型
    public final static int MAIN_PAGE_SEARCH_TYPE_MORE_SERVICE = 2; //更多服务
    public final static int MAIN_PAGE_SEARCH_TYPE_LINE = 1001; //1px分割线



    public static final String ID_CONGFING_SAVE = "configSystem/";
    // 常熟的首页configID
    public static final String ID_CONGFING_HOMEPAGE = "pasc.smt.homepage.base";
    // 常熟的生活configID
    public static final String ID_CONGFING_LIFE = "pasc.smt.lifepage";
    // 常熟更多服务configID
    public static final String ID_CONGFING_SERVICE = "pasc.smt.more.server";
    // 常熟的网上大厅configID
    public static final String ID_CONGFING_LOBBY = "pasc.smt.lobby.online";
    // 常熟的政务渠道json配置 configID
    public static final String ID_CONGFING_CHANNEL = "pasc.pingan.channel";



    public static final String GET_CONFIHG_JSON = "platform/conf/getModuleConfigVersion";




}
