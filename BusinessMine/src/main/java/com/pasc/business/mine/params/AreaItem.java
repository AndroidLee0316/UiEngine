package com.pasc.business.mine.params;

import com.google.gson.annotations.SerializedName;
import com.pasc.business.mine.resp.SecondAreaItem;

import java.util.List;


public class AreaItem {

    @SerializedName("codeid")
    public String codeid;
    @SerializedName("parentid")
    public String parentid;
    @SerializedName("cityName")
    public String cityName;

    @SerializedName("children")
    public List<SecondAreaItem> children;

    @Override
    public String toString() {
        return cityName;
    }
}
