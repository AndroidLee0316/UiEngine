package com.pasc.business.workspace;

import com.pasc.lib.workspace.BusinessConstants;

public class Constants {

    public static final String TAG = "BusinessWorkspace";
    public static final String MODULE_NAME = "BusinessWorkspace";
    public static final String BASE_KEY_CONFIG = "config";

    //新版更多服务-根据编码查询服务
    public static final String QUERY_SERVICE_CONFIG_NEW = BusinessConstants.QUERY_SERVICE_CONFIG_NEW; // 这个是固定的地址

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

    public final static int MAIN_PAGE_SEARCH_TYPE_TITLE = 1; //标题 类型
    public final static int MAIN_PAGE_SEARCH_TYPE_SERVICE = 7; //服务类型 类型
    public final static int MAIN_PAGE_SEARCH_TYPE_MORE_SERVICE = 2; //更多服务

    public static final int EVENT_TYPE_WEB = 1; // 网页
    public static final int EVENT_TYPE_NATIVE = 2; // 原生界面
    public static final int EVENT_TYPE_NATIVE_ROUTER = 3; // 原生界面路由方式
    public static final int EVENT_TYPE_EVENT = 4; // 触发事件总线的事件
    public static final int EVENT_TYPE_NATIVE_MAP = 5; // 原生界面直接映射方式

    // 动态域名配置的ID
    public static final String CONFIG_ID_URL_ROOT = "pasc.smt.urlroot";
}
