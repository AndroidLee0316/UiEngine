package com.pasc.lib.workspace;

import com.pasc.lib.workspace.util.CacheUtils;

import java.util.LinkedHashMap;

public class CacheKeyManager {

    private static final CacheKeyManager ourInstance = new CacheKeyManager();

    static CacheKeyManager getInstance() {
        return ourInstance;
    }

    private CacheKeyManager() {
    }

    public String getBannerCacheKey(String cellId) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("cellId", cellId);
        String cacheKey = getCacheKey("banner", map);
        return cacheKey;
    }

    private String getCacheKey(String baseKey, LinkedHashMap<String, String> params) {
        return CacheUtils.getCacheKey("workspace", baseKey, params);
    }
}
