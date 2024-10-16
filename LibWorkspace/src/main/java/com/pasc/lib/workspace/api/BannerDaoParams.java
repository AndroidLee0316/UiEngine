package com.pasc.lib.workspace.api;

public class BannerDaoParams {

    private String token;
    private String cellId;
    private boolean isCutOut; // 是否刘海

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCellId() {
        return cellId;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public boolean isCutOut() {
        return isCutOut;
    }

    public void setCutOut(boolean cutOut) {
        isCutOut = cutOut;
    }
}
