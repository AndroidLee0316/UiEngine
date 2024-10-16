package com.pasc.business.mine.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JsAddressJson implements Serializable{
    @SerializedName("name")
    private String name = "";            //联系人姓名
    @SerializedName("mobilePhone")
    private String mobilePhone = "";      //联系人电话
    @SerializedName("detailAddress")
    private String detailAddress = "";   //详细地址(除省市区)
    @SerializedName("isDefault")
    private String isDefault = "";        //是否是默认地址
    @SerializedName("address")
    private String address = "";         //全部地址(自己拼接)
    @SerializedName("selectedAddress")
    private String selectedAddress = ""; //选择的省市区(自己拼接显示)
    @SerializedName("addressID")
    private String addressID = "";       //地址的id
    @SerializedName("code")
    private String code = "";            //省市区对应的行政地址编码
    @SerializedName("province")
    private String province = "";        //省
    @SerializedName("city")
    private String city = "";            //市
    @SerializedName("district")
    private String district = "";        //区
    @SerializedName("provinceID")
    private String provinceID = "";      //省id
    @SerializedName("cityID")
    private String cityID = "";          //市id
    @SerializedName("districtID")
    private String districtID = "";      //区id

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSelectedAddress() {
        return selectedAddress;
    }

    public void setSelectedAddress(String selectedAddress) {
        this.selectedAddress = selectedAddress;
    }

    public String getAddressID() {
        return addressID;
    }

    public void setAddressID(String addressID) {
        this.addressID = addressID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(String provinceID) {
        this.provinceID = provinceID;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getDistrictID() {
        return districtID;
    }

    public void setDistrictID(String districtID) {
        this.districtID = districtID;
    }
}