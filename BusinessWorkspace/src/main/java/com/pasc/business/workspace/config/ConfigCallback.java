package com.pasc.business.workspace.config;

/**
 * 配置回调类.
 * Created by chenruihan410 on 2018/08/07.
 */
public abstract class ConfigCallback<T> {
    public T t;

    public void onError(String msg) {
    }


    public void onNext(T t) {
    }

    public void onEmpty() {

    }
}
