package com.pasc.lib.workspace.api;

public class ConfigDaoParams {

    private String configId;
    private String testFlag;
    private String url;
    private String configVersion;

    public String getConfigVersion() {
        return configVersion;
    }

    public void setConfigVersion(String configVersion) {
        this.configVersion = configVersion;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getTestFlag() {
        return testFlag;
    }

    public void setTestFlag(String testFlag) {
        this.testFlag = testFlag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
