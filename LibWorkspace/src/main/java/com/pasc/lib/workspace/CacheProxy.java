package com.pasc.lib.workspace;

import java.util.List;

public class CacheProxy implements ICache {

    private static final CacheProxy ourInstance = new CacheProxy();

    private ICache cache;

    public static CacheProxy getInstance() {
        return ourInstance;
    }

    private CacheProxy() {
    }

    public void init(ICache cache) {
        this.cache = cache;
    }

    @Override
    public <T> T getCache(String key, Class<T> clazz) {
        if (cache != null) {
            return cache.getCache(key, clazz);
        } else {
            System.err.println("请先初始化缓存代理");
            return null;
        }
    }

    @Override
    public void saveCache(String key, Object value) {
        if (cache != null) {
            cache.saveCache(key, value);
        } else {
            System.err.println("请先初始化缓存代理");
        }
    }

    @Override
    public <T> List<T> getCacheList(String key, Class<T> clazz) {
        if (cache != null) {
            return cache.getCacheList(key, clazz);
        } else {
            System.err.println("请先初始化缓存代理");
            return null;
        }
    }
}
