package com.pasc.business.workspace.config;

import android.content.Context;

/**
 * Created by yintangwen952 on 2018/7/10.
 */

public class ConfigRepository {

    //默认asset下配置主目录
    private static final String ASSET_MAIN = "configSystem/";
    private static final String JSON_SUFFIX = ".json";
    private static final String DEFAULT_CONFIG_VERSION = "0.0";
    private static String mAppVersion;
    private static String mAppId;
    private static String mUserId;
    private static volatile ConfigRepository mRepository;
    private static Context mContext;

    /**
     * 创建单例获取
     *
     * @return
     */
    public static ConfigRepository getInstance() {
        if (mRepository == null) {
            synchronized (ConfigRepository.class) {
                if (mRepository == null) {
                    mRepository = new ConfigRepository();
                }
            }
        }
        return mRepository;
    }

    /**
     * 初始化配置查询参数
     *
     * @param context
     * @param appVersion
     * @param appId
     * @param userId
     */
    public void initConfig(Context context, String appVersion,
                           String appId,
                           String userId) {
        mContext = context;
        mAppVersion = appVersion;
        mAppId = appId;
        mUserId = userId;
    }


}