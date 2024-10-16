package com.pasc.business.mine.resp;

import com.google.gson.annotations.SerializedName;

public class ThirdAreaItem {

    @SerializedName("codeid")
    public String codeid;
    @SerializedName("parentid")
    public String parentid;
    @SerializedName("cityName")
    public String cityName="";
    @SerializedName("children")
    public ThirdAreaItem thirdAreaItem;


    @Override
    public String toString() {
        return cityName;
    }
}
