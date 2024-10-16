package com.pasc.pascuiengine.impl;

import android.content.Context;

import com.pasc.lib.workspace.api.BannerDao;
import com.pasc.lib.workspace.api.ConfigDao;
import com.pasc.lib.workspace.api.NewsDao;
import com.pasc.lib.workspace.api.ScrollNewsDao;
import com.pasc.lib.workspace.api.WeatherDao;
import com.pasc.lib.workspace.api.impl.DaoBuilderImpl;

public class TDaoBuilderImpl extends DaoBuilderImpl {

    public TDaoBuilderImpl(Context context) {
        super(context);
    }

//    @Override
//    public BannerDao getBannerDao() {
//        return new TBannerDaoImpl(getContext());
//    }
//
//    @Override
//    public ConfigDao getConfigDao() {
//        return new TConfigDaoImpl(getContext());
//    }
//
//    @Override
//    public ScrollNewsDao getScrollNewsDao() {
//        return new TScrollNewsDaoImpl();
//    }
//
//    @Override
//    public WeatherDao getWeatherDao() {
//        return new TWeatherDaoImpl();
//    }
//
    @Override
    public NewsDao getNewsDao() {
        return new TNewsDaoImpl();
    }

}
