package com.pasc.lib.workspace.bean;

/**
 * 当前城市.
 */
public class WeatherCity {

    private boolean isLocation; // 是否是定位城市
    private double latitude; // 纬度
    private double longitude; // 经度
    private String cityName; // 市名称
    private String showName; // 显示名称
    private String districtName; // 地区名称
    private String mainPageShowName; // 首页显示名称

    public String getMainPageShowName() {
        return mainPageShowName;
    }

    public void setMainPageShowName(String mainPageShowName) {
        this.mainPageShowName = mainPageShowName;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeatherCity that = (WeatherCity) o;

        if (isLocation != that.isLocation) return false;
        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        if (cityName != null ? !cityName.equals(that.cityName) : that.cityName != null)
            return false;
        return showName != null ? showName.equals(that.showName) : that.showName == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (isLocation ? 1 : 0);
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
        result = 31 * result + (showName != null ? showName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WeatherCity{" +
                "isLocation=" + isLocation +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", cityName='" + cityName + '\'' +
                ", showName='" + showName + '\'' +
                '}';
    }
}
