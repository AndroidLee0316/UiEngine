package com.pasc.lib.workspace.api;

import android.content.Context;

import com.pasc.lib.workspace.api.impl.DaoBuilderImpl;
import com.pasc.lib.workspace.api.impl.FakeAnnouncementDaoImpl;
import com.pasc.lib.workspace.api.impl.FakeNewsDaoImpl;
import com.pasc.lib.workspace.api.impl.FakeScrollNewsDaoImpl;
import com.pasc.lib.workspace.api.impl.FakeWeatherDaoImpl;

public class DaoFactory {

    private static DaoFactory daoFactory = new DaoFactory();
    private Context context;
    private DaoBuilder daoBuilder;
    private boolean isFake = false; // 是否模拟数据

    private DaoFactory() {
    }

    public DaoFactory init(Context context) {
        if (context == null) {
            throw new RuntimeException("Context can not be null.");
        }
        Context appContext = context.getApplicationContext();
        this.context = appContext;
        daoBuilder = new DaoBuilderImpl(appContext);

        return this;
    }

    public void setFake(boolean isFake) {
        this.isFake = isFake;
    }

    public void setDaoBuilder(DaoBuilder daoBuilder) {
        this.daoBuilder = daoBuilder;
    }

    public BannerDao getBannerDao() {
        return daoBuilder.getBannerDao();
    }

    public static DaoFactory getInstance() {
        return daoFactory;
    }

    public ConfigDao getConfigDao() {
        return daoBuilder.getConfigDao();
    }

    public ScrollNewsDao getScrollNewsDao() {
        if (isFake) {
            return new FakeScrollNewsDaoImpl(context);
        }
        return daoBuilder.getScrollNewsDao();
    }

    public AnnouncementDao getAnnouncementDao() {
        if (isFake) {
            return new FakeAnnouncementDaoImpl(context);
        }
        return daoBuilder.getAnnouncementDao();
    }

    public NewsDao getNewsDao() {
        if (isFake) {
            return new FakeNewsDaoImpl(context);
        }
        return daoBuilder.getNewsDao();
    }

    public WeatherDao getWeatherDao() {
        if (isFake) {
            return new FakeWeatherDaoImpl(context);
        }
        return daoBuilder.getWeatherDao();
    }
}
