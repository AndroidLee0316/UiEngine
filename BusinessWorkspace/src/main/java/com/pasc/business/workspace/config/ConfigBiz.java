package com.pasc.business.workspace.config;

import android.content.Context;
import android.text.TextUtils;

import com.pasc.business.workspace.Constants;
import com.pasc.lib.base.util.JsonUtils;
import com.pasc.lib.workspace.WorkspaceBiz;
import com.pasc.lib.workspace.bean.ConfigItem;
import com.pasc.lib.workspace.util.ConfigUtils;

//import com.pasc.lib.log.PascLog;

/**
 * 配置业务.
 * Created by chenruihan410 on 2018/08/07.
 */
public class ConfigBiz {

    private static final String ASSET_MAIN = "configSystem/";
    private static final String JSON_SUFFIX = ".json";
    private static final String TAG = "ConfigBiz";

    /**
     * 从缓存获取配置，如果未空则取应用默认的配置.
     *
     * @param context  上下文.
     * @param configId 配置id.
     * @return 配置对象.
     */
    public static ConfigItem getConfigFromCacheOrAssets(Context context, String configId) {
        return WorkspaceBiz.getInstance().getConfigFromCacheOrAssets(context, configId);
    }

    /**
     * 从缓存获取配置.
     *
     * @param context  上下文.
     * @param configId 配置id.
     * @return 配置对象.
     */
    public static ConfigItem getConfigFromCache(Context context, String configId) {
        return ConfigUtils.getConfigFromCache(context, configId);
    }

    /**
     * 获取动态域名的缓存.
     *
     * @param context 上下文.
     * @return 动态域名类.
     */
    public static ConfigUrlRoot getConfigCacheUrlRoot(Context context) {
        if (context == null) return null;
        try {
            ConfigItem configItem = ConfigBiz.getConfigFromCache(context, Constants.CONFIG_ID_URL_ROOT);
            if (configItem != null) {
                String configContent = configItem.configContent;
                if (!TextUtils.isEmpty(configContent)) {
                    ConfigUrlRoot configUrlRoot = JsonUtils.fromJson(configContent, ConfigUrlRoot.class);
                    return configUrlRoot;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从Assets获取配置.
     *
     * @param context  上下文.
     * @param configId 配置id.
     * @return 配置对象.
     */
    public static ConfigItem getConfigFromAssets(Context context, String configId) {
        return WorkspaceBiz.getInstance().getConfigFromAssets(context, configId);
    }

    public static ConfigItem getConfigFromNet(Context context, String configId, String testFlag, String url) throws Exception {
        return WorkspaceBiz.getInstance().getConfigFromNet(context, configId, testFlag, url);
    }

    /**
     * 从网络获取配置.
     *
     * @param context  上下文.
     * @param configId 配置id.
     * @param testFlag 测试标签，0是生产，1是测试.
     * @return 配置对象.
     */
    public static ConfigItem getConfigFromNet(Context context, String configId, String testFlag) throws Exception {
        return WorkspaceBiz.getInstance().getConfigFromNet(context, configId, testFlag);
    }
}
