//package com.pasc.pascuiengine.impl;
//
//import com.pasc.lib.weather.data.WeatherInfo;
//import com.pasc.lib.weather.data.params.WeatherCityInfo;
//import com.pasc.lib.weather.utils.WeatherDataManager;
//import com.pasc.lib.workspace.api.WeatherDao;
//import com.pasc.lib.workspace.api.WeatherDaoParams;
//import com.pasc.lib.workspace.bean.MainPageWeatherInfo;
//import com.pasc.lib.workspace.bean.WeatherCity;
//
//public class TWeatherDaoImpl implements WeatherDao {
//
//    @Override
//    public WeatherCity getWeatherCity() {
//        WeatherCityInfo weatherCityInfo = WeatherDataManager.getInstance().getCurrentSelectedCity();
//        WeatherCity weatherCity = new WeatherCity();
//        if (weatherCityInfo == null || weatherCityInfo.getCity() == null) {
//            // 产品城市
//            weatherCity.setMainPageShowName("深圳市");
//            weatherCity.setCityName("深圳市");
//            weatherCity.setShowName("深圳市");
//            weatherCity.setLocation(false);
//            return weatherCity;
//        } else {
//            String showNameByProduct = weatherCityInfo.getShowNameByProduct("深圳市");
//            weatherCity.setMainPageShowName(showNameByProduct);
//            weatherCity.setShowName(weatherCityInfo.getShowName());
//            weatherCity.setCityName(weatherCityInfo.getCity());
//            weatherCity.setLongitude(weatherCityInfo.getLongitude());
//            weatherCity.setLatitude(weatherCityInfo.getLatitude());
//            weatherCity.setLocation(weatherCityInfo.isLocation());
//            weatherCity.setDistrictName(weatherCityInfo.getDistrict());
//            return weatherCity;
//        }
//    }
//
//    @Override
//    public WeatherCity saveWeatherCity(WeatherCity weatherCity) {
//        String cityName = weatherCity.getCityName();
//        WeatherCityInfo weatherCityInfo = new WeatherCityInfo(cityName);
//        weatherCityInfo.setCity(weatherCity.getCityName());
//        weatherCityInfo.setShowName(weatherCity.getShowName());
//        weatherCityInfo.setDistrict(weatherCity.getDistrictName());
//        weatherCityInfo.setLongitude(weatherCity.getLongitude());
//        weatherCityInfo.setLatitude(weatherCity.getLatitude());
//        weatherCityInfo.setIsLocation(weatherCity.isLocation());
//
//        WeatherDataManager.getInstance().saveCurrentSelectedCity(weatherCityInfo);
//
//        WeatherCity weatherCitySave = getWeatherCity();
//        return weatherCitySave;
//    }
//
//    @Override
//    public MainPageWeatherInfo getWeatherFromCache(WeatherDaoParams weatherDaoParams) {
//        WeatherCityInfo weatherCityInfo = new WeatherCityInfo(weatherDaoParams.getCityName());
//        weatherCityInfo.setCity(weatherDaoParams.getCityName());
//        weatherCityInfo.setShowName(weatherDaoParams.getShowName());
//        weatherCityInfo.setLongitude(weatherDaoParams.getLongitude());
//        weatherCityInfo.setLatitude(weatherDaoParams.getLatitude());
//        weatherCityInfo.setIsLocation(weatherDaoParams.isLocation());
//        weatherCityInfo.setDistrict(weatherDaoParams.getDistrictName());
//
//        WeatherInfo weatherInfo = WeatherDataManager.getInstance().getWeatherInfoFromCache(weatherCityInfo);
//        return convertToMainPageWeatherInfo(weatherInfo);
//    }
//
//    @Override
//    public MainPageWeatherInfo getWeatherFromNet(WeatherDaoParams weatherDaoParams) {
//        WeatherCityInfo weatherCityInfo = new WeatherCityInfo(weatherDaoParams.getCityName());
//        weatherCityInfo.setCity(weatherDaoParams.getCityName());
//        weatherCityInfo.setShowName(weatherDaoParams.getShowName());
//        weatherCityInfo.setLongitude(weatherDaoParams.getLongitude());
//        weatherCityInfo.setLatitude(weatherDaoParams.getLatitude());
//        weatherCityInfo.setIsLocation(weatherDaoParams.isLocation());
//        weatherCityInfo.setDistrict(weatherDaoParams.getDistrictName());
//
//        WeatherInfo weatherInfo = WeatherDataManager.getInstance().getWeatherInfoFromNet(weatherCityInfo);
//        return convertToMainPageWeatherInfo(weatherInfo);
//    }
//
//    private MainPageWeatherInfo convertToMainPageWeatherInfo(WeatherInfo weatherInfo) {
//        if (weatherInfo == null) {
//            return null;
//        }
//
//        MainPageWeatherInfo mainPageWeatherInfo = new MainPageWeatherInfo();
//        mainPageWeatherInfo.city = weatherInfo.city;
//        mainPageWeatherInfo.cond_image_url = weatherInfo.cond_image_url;
//        mainPageWeatherInfo.cond_txt = weatherInfo.cond_txt;
//        mainPageWeatherInfo.qlty = weatherInfo.qlty;
//        mainPageWeatherInfo.tmp = weatherInfo.tmp;
//        mainPageWeatherInfo.id = weatherInfo.id;
//        return mainPageWeatherInfo;
//    }
//}
