package com.pasc.lib.workspace.api;

public class WeatherDaoParams {

    private boolean isLocation; //是否是定位城市
    private double latitude; //纬度
    private double longitude; //经度
    private String cityName; //请求天气时的城市名（福田，深圳）
    private String showName; //界面上显示的城市名（深圳市 福田区）
    private String districtName; // 地区

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public boolean isLocation() {
        return isLocation;
    }

    public void setLocation(boolean location) {
        isLocation = location;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }
}
