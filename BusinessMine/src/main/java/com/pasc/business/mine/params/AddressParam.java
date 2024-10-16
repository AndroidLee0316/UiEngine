package com.pasc.business.mine.params;

import com.google.gson.annotations.SerializedName;

import com.pasc.lib.base.AppProxy;
import java.io.Serializable;


public class AddressParam implements Serializable {
    //@SerializedName("userId") public String userId;
    @SerializedName("token")
    public String token = AppProxy.getInstance().getUserManager().getUserInfo().getToken();
    @SerializedName("addressMobile")
    public String addressMobile;
    @SerializedName("addressName")
    public String addressName;
    @SerializedName("detailAddress")
    public String detailAddress;
    @SerializedName("province")
    public String province;
    @SerializedName("city")
    public String city;
    @SerializedName("county")
    public String county;
    @SerializedName("isDefault")
    public String isDefault;
    //邮编
    @SerializedName("code")
    public String code;

    public AddressParam(String addressMobile, String addressName, String detailAddress, String province,
                        String city, String county, String isDefault, String code) {
        //userId = UserManager.getInstance().getUserId();
        this.addressMobile = addressMobile;
        this.addressName = addressName;
        this.detailAddress = detailAddress;
        this.province = province;
        this.city = city;
        this.county = county;
        this.isDefault = isDefault;
        this.code=code;

    }
}
