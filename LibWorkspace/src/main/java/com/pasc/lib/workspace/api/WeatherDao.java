package com.pasc.lib.workspace.api;

import com.pasc.lib.workspace.bean.WeatherCity;
import com.pasc.lib.workspace.bean.MainPageWeatherInfo;

public interface WeatherDao {

    WeatherCity getWeatherCity();

    WeatherCity saveWeatherCity(WeatherCity weatherCity);

    MainPageWeatherInfo getWeatherFromCache(WeatherDaoParams params);

    MainPageWeatherInfo getWeatherFromNet(WeatherDaoParams params);
}
