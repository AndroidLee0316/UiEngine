package com.pasc.business.mine.params;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AddressItem implements Parcelable {
    @SerializedName("addressMobile")
    public String addressMobile = "";
    @SerializedName("code")
    public String code = "";
    @SerializedName("city")
    public String city = "";
    @SerializedName("county")
    public String county = "";
    @SerializedName("userId")
    public String userId = "";
    @SerializedName("isDefault")
    public String isDefault = "";
    @SerializedName("province")
    public String province = "";
    @SerializedName("cityName")
    public String cityName = "";
    @SerializedName("detailAddress")
    public String detailAddress = "";
    @SerializedName("addressName")
    public String addressName;
    @SerializedName("id")
    public String id = "";
    @SerializedName("provinceName")
    public String provinceName = "";
    @SerializedName("countyName")
    public String countyName = "";

    @Override
    public String toString() {
        return "AddressItem{" +
                "addressMobile='" + addressMobile + '\'' +
                ", code='" + code + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", userId='" + userId + '\'' +
                ", isDefault='" + isDefault + '\'' +
                ", province='" + province + '\'' +
                ", cityName='" + cityName + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", addressName='" + addressName + '\'' +
                ", id='" + id + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", countyName='" + countyName + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.addressMobile);
        dest.writeString(this.code);
        dest.writeString(this.city);
        dest.writeString(this.county);
        dest.writeString(this.userId);
        dest.writeString(this.isDefault);
        dest.writeString(this.province);
        dest.writeString(this.cityName);
        dest.writeString(this.detailAddress);
        dest.writeString(this.addressName);
        dest.writeString(this.id);
        dest.writeString(this.provinceName);
        dest.writeString(this.countyName);
    }

    public AddressItem() {
    }

    protected AddressItem(Parcel in) {
        this.addressMobile = in.readString();
        this.code = in.readString();
        this.city = in.readString();
        this.county = in.readString();
        this.userId = in.readString();
        this.isDefault = in.readString();
        this.province = in.readString();
        this.cityName = in.readString();
        this.detailAddress = in.readString();
        this.addressName = in.readString();
        this.id = in.readString();
        this.provinceName = in.readString();
        this.countyName = in.readString();
    }

    public static final Creator<AddressItem> CREATOR = new Creator<AddressItem>() {
        @Override
        public AddressItem createFromParcel(Parcel source) {
            return new AddressItem(source);
        }

        @Override
        public AddressItem[] newArray(int size) {
            return new AddressItem[size];
        }
    };
}
