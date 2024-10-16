package com.pasc.lib.workspace.handler;

public class UrlProxy implements UrlCreator {

    private static final UrlProxy ourInstance = new UrlProxy();

    private UrlCreator urlCreator;

    public static UrlProxy getInstance() {
        return ourInstance;
    }

    public void init(UrlCreator urlCreator) {
        this.urlCreator = urlCreator;
    }

    private UrlProxy() {
    }

    @Override
    public String getApiUrlRoot() {
        checkUrlCreator();
        return urlCreator.getApiUrlRoot();
    }

    private void checkUrlCreator() {
        if (urlCreator == null) {
            throw new RuntimeException("请先初始化UrlProxy");
        }
    }

    @Override
    public String getH5UrlRoot() {
        checkUrlCreator();
        return urlCreator.getH5UrlRoot();
    }
}
