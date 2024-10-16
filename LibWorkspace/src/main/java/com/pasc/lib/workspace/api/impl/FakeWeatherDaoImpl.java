package com.pasc.lib.workspace.api.impl;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pasc.lib.workspace.api.NewsDao;
import com.pasc.lib.workspace.api.WeatherDao;
import com.pasc.lib.workspace.api.WeatherDaoParams;
import com.pasc.lib.workspace.bean.InteractionNewsBean;
import com.pasc.lib.workspace.bean.MainPageWeatherInfo;
import com.pasc.lib.workspace.bean.WeatherCity;
import com.pasc.lib.workspace.util.AssetUtils;

import java.util.ArrayList;
import java.util.List;

public class FakeWeatherDaoImpl extends FakeDaoImpl implements WeatherDao {

    public FakeWeatherDaoImpl(Context context) {
        super(context);
    }

    @Override
    public WeatherCity getWeatherCity() {
        WeatherCity weatherCity = new WeatherCity();
        weatherCity.setMainPageShowName("深圳市");
        return weatherCity;
    }

    @Override
    public WeatherCity saveWeatherCity(WeatherCity weatherCity) {
        weatherCity.setMainPageShowName(weatherCity.getCityName());
        return weatherCity;
    }

    @Override
    public MainPageWeatherInfo getWeatherFromCache(WeatherDaoParams params) {
        MainPageWeatherInfo weatherInfo = new MainPageWeatherInfo();
        weatherInfo.tmp = "32°";
        weatherInfo.cond_txt = "晴";
        weatherInfo.city = "深圳市";
        return weatherInfo;
    }

    @Override
    public MainPageWeatherInfo getWeatherFromNet(WeatherDaoParams params) {
        MainPageWeatherInfo weatherInfo = new MainPageWeatherInfo();
        weatherInfo.tmp = "32°";
        weatherInfo.cond_txt = "晴";
        weatherInfo.city = "珠海市";
        return weatherInfo;
    }
}
