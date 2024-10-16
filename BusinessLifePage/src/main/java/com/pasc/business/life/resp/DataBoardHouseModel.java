package com.pasc.business.life.resp;

import com.google.gson.annotations.SerializedName;

public class DataBoardHouseModel {

    public String type = "component-dataBoardHouse";
    @SerializedName("data")
    public HouseInfoResp data;
}
