package com.pasc.lib.workspace.api;

public interface DaoBuilder {

    BannerDao getBannerDao();

    ConfigDao getConfigDao();

    ScrollNewsDao getScrollNewsDao();

    AnnouncementDao getAnnouncementDao();

    NewsDao getNewsDao();

    WeatherDao getWeatherDao();
}
