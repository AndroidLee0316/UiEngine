package com.pasc.lib.workspace.api.impl;

import android.content.Context;

import com.pasc.lib.workspace.api.WeatherDao;
import com.pasc.lib.workspace.api.WeatherDaoParams;
import com.pasc.lib.workspace.bean.MainPageWeatherInfo;
import com.pasc.lib.workspace.bean.WeatherCity;

public class WeatherDaoImpl implements WeatherDao {

    private final Context context;

    public WeatherDaoImpl(Context context) {
        this.context = context;
    }

    @Override
    public WeatherCity getWeatherCity() {
        return null;
    }

    @Override
    public WeatherCity saveWeatherCity(WeatherCity weatherCity) {
        return null;
    }

    @Override
    public MainPageWeatherInfo getWeatherFromCache(WeatherDaoParams params) {
        return null;
    }

    @Override
    public MainPageWeatherInfo getWeatherFromNet(WeatherDaoParams params) {
        return null;
    }
}
