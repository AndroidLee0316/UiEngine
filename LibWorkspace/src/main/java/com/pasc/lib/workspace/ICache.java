package com.pasc.lib.workspace;

import java.util.List;

public interface ICache {

    <T> T getCache(String key, Class<T> clazz);

    void saveCache(String key, Object value);

    <T> List<T> getCacheList(String key, Class<T> clazz);
}
