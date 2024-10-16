package com.pasc.business.workspace;

import com.pasc.business.workspace.impl.ConfigIdProviderImpl;

public class ConfigIdProxy implements ConfigIdProvider {

    private static final ConfigIdProxy ourInstance = new ConfigIdProxy();

    private ConfigIdProvider configIdProvider;

    public static ConfigIdProxy getInstance() {
        return ourInstance;
    }

    private ConfigIdProxy() {
        configIdProvider = new ConfigIdProviderImpl();
    }

    public ConfigIdProxy setConfigIdProvider(ConfigIdProvider configIdProvider) {
        this.configIdProvider = configIdProvider;
        return this;
    }

    @Override
    public String getConfigIdHome() {
        checkConfigIdProvider();
        return configIdProvider.getConfigIdHome();
    }

    private void checkConfigIdProvider() {
        if (configIdProvider == null) {
            throw new RuntimeException("请先初始化ConfigIdProvider");
        }
    }
}
