package com.pasc.lib.workspace.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class InteractionNewsResp {
    @SerializedName("list")
    public List<InteractionNewsBean> list;

    public List<InteractionNewsBean> getList() {
        return list;
    }

    public void setList(List<InteractionNewsBean> list) {
        this.list = list;
    }
}
