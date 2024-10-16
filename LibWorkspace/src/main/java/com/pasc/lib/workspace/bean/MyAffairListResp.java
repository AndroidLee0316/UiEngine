package com.pasc.lib.workspace.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 我的办事
 */
public class MyAffairListResp {
    @SerializedName("list")
    public List<MyAffairItem> list;
}
