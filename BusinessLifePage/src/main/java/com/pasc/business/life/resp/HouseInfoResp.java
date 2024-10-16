package com.pasc.business.life.resp;


import com.google.gson.annotations.SerializedName;

public class HouseInfoResp {
    @SerializedName("changePersent")
    public String changePersent;//挂牌均价

    @SerializedName("price")
    public String price;//均价

    @SerializedName("dealCount")
    public String dealCount;//成交套数

}
