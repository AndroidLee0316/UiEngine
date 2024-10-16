package com.pasc.business.life.resp;

import com.google.gson.annotations.SerializedName;

public class LocationModel {
    @SerializedName("type")
    public String type = "";
    @SerializedName("street")
    public String street = "";
    @SerializedName("location")
    public String location = "";
    @SerializedName("title")
    public String title = "";
    @SerializedName("iconUrl")
    public String iconUrl = "";
    @SerializedName("desc")
    public String desc = "";
    @SerializedName("onClick")
    public String onClick = "";
}
