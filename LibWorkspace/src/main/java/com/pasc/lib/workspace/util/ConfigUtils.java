package com.pasc.lib.workspace.util;

import android.content.Context;
import android.text.TextUtils;

import com.pasc.lib.workspace.CacheProxy;
import com.pasc.lib.workspace.UserProxy;
import com.pasc.lib.workspace.bean.ConfigItem;

public class ConfigUtils {

    public static ConfigItem getConfigFromCache(Context context, String configId) {
        if (context == null) return null;
        if (TextUtils.isEmpty(configId)) return null;
        String phoneNumber = UserProxy.getInstance().getPhoneNum();
        String configCacheKey = BizUtils.getCacheKeyConfig(context, configId, phoneNumber);
        ConfigItem configItem = CacheProxy.getInstance().getCache(configCacheKey, ConfigItem.class);
        return configItem;
    }
}
