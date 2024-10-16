package com.pasc.business.mine.params;

import com.google.gson.annotations.SerializedName;
import com.pasc.lib.base.AppProxy;

/**
 * 修改收货地址
 */
public class UpdateAddressParam  {
//    request
//    注：以下字段没修改时可以不传，但省市县有修改必须都传（否则就会修改失败）
//    修改无可设置用户地址为默认地址功能
//    jsonData：{
//        "data": {
//            "token": "xxxxxx",//用户登录返回的token
//                    "addressMobile":"18627576555",
//                    "addressName":"马上来",
//                    "detailAddress":"明治街道报1",
//                    "province":"44",//省（注意：省市县的编码传时就要一起传，要不就都不传）
//                    "city":"4403",//市
//                    "county":"440303",//县
//                    "id":"13" //地址id （必填）
//        }
//    }

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
    @SerializedName("id")
    public String id;
    @SerializedName("isDefault")
    public String isDefault;
    @SerializedName("code")
    public String code;


    public UpdateAddressParam(String addressMobile, String addressName, String detailAddress, String province, String city, String county, String id,String isDefault,String code) {
        this.addressMobile = addressMobile;
        this.addressName = addressName;
        this.detailAddress = detailAddress;
        this.province = province;
        this.city = city;
        this.county = county;
        this.id = id;
        this.isDefault = isDefault;
        this.code=code;
    }
}
