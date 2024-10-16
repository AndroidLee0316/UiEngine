package com.pasc.business.mine.resp;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class SecondAreaItem {

    @SerializedName("codeid")
    public String codeid;
    @SerializedName("parentid")
    public String parentid;
    @SerializedName("cityName")
    public String cityName="";

    @SerializedName("children")
    public List<ThirdAreaItem> children;

    @Override
    public String toString() {
        return cityName;
    }
}
