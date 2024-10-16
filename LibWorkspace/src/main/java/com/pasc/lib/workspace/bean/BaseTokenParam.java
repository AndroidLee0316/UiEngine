package com.pasc.lib.workspace.bean;

import com.google.gson.annotations.SerializedName;

public class BaseTokenParam {

    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
