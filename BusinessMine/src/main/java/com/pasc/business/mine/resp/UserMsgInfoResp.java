package com.pasc.business.mine.resp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 用户信息
 */
public class UserMsgInfoResp {

    @SerializedName("list")
    public List<ListBean> list;

    public static class ListBean {
        //{"list":[{"fieldName":"我的收藏","recordNum":"3"},{"fieldName":"我的业务","recordNum":"0"},{"fieldName":"我的消息","recordNum":"0"}]}}
        @SerializedName("fieldName")
        public String fieldName;
        @SerializedName("recordNum")
        public String recordNum;
    }
}
