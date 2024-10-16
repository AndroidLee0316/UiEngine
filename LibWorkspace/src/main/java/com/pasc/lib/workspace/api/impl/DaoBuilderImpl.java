package com.pasc.lib.workspace.api.impl;

import android.content.Context;

import com.pasc.lib.workspace.api.AnnouncementDao;
import com.pasc.lib.workspace.api.BannerDao;
import com.pasc.lib.workspace.api.ConfigDao;
import com.pasc.lib.workspace.api.DaoBuilder;
import com.pasc.lib.workspace.api.NewsDao;
import com.pasc.lib.workspace.api.ScrollNewsDao;
import com.pasc.lib.workspace.api.WeatherDao;

public class DaoBuilderImpl implements DaoBuilder {

    private final Context context;

    public Context getContext() {
        return context;
    }

    public DaoBuilderImpl(Context context) {
        this.context = context;
    }

    @Override
    public BannerDao getBannerDao() {
        return new BannerDaoImpl(context);
    }

    @Override
    public ConfigDao getConfigDao() {
        return new ConfigDaoImpl(context);
    }

    @Override
    public ScrollNewsDao getScrollNewsDao() {
        return new ScrollNewsDaoImpl(context);
    }

    @Override
    public AnnouncementDao getAnnouncementDao() {
        return new AnnouncementDaoImpl(context);
    }

    @Override
    public NewsDao getNewsDao() {
        return new NewsDaoImpl(context);
    }

    @Override
    public WeatherDao getWeatherDao() {
        return new WeatherDaoImpl(context);
    }
}
