package com.pasc.lib.workspace;

import android.content.Context;
import android.util.Log;

import com.pasc.lib.base.util.AppUtils;
import com.pasc.lib.base.util.CollectionUtils;
import com.pasc.lib.base.util.JsonUtils;
import com.pasc.lib.log.PascLog;
import com.pasc.lib.net.ApiGenerator;
import com.pasc.lib.net.param.BaseParam;
import com.pasc.lib.net.resp.VoidObject;
import com.pasc.lib.workspace.api.AffairsApi;
import com.pasc.lib.workspace.api.AnnouncementDaoParams;
import com.pasc.lib.workspace.api.BannerDaoParams;
import com.pasc.lib.workspace.api.ConfigDaoParams;
import com.pasc.lib.workspace.api.DaoFactory;
import com.pasc.lib.workspace.api.HouseSecurityApi;
import com.pasc.lib.workspace.api.WaterQualityApi;
import com.pasc.lib.workspace.api.WeatherDaoParams;
import com.pasc.lib.workspace.bean.Announcement;
import com.pasc.lib.workspace.bean.AnnouncementRsp;
import com.pasc.lib.workspace.bean.AppBannerRsp;
import com.pasc.lib.workspace.bean.BannerBean;
import com.pasc.lib.workspace.bean.BaseTokenParam;
import com.pasc.lib.workspace.bean.ConfigItem;
import com.pasc.lib.workspace.bean.ConfigResp;
import com.pasc.lib.workspace.bean.HouseSecurityResp;
import com.pasc.lib.workspace.bean.InteractionNewsBean;
import com.pasc.lib.workspace.bean.InteractionNewsResp;
import com.pasc.lib.workspace.bean.MainPageWeatherInfo;
import com.pasc.lib.workspace.bean.MyAffairListResp;
import com.pasc.lib.workspace.bean.ScrollNewsResp;
import com.pasc.lib.workspace.bean.TodayWaterQualityResp;
import com.pasc.lib.workspace.bean.WeatherCity;
import com.pasc.lib.workspace.util.ArgumentUtils;
import com.pasc.lib.workspace.util.AssetUtils;
import com.pasc.lib.workspace.util.BizUtils;
import com.pasc.lib.workspace.util.ConfigUtils;

import java.util.List;

public class WorkspaceBiz {

    private static final String ASSET_MAIN = "configSystem/";
    private static final String JSON_SUFFIX = ".json";
    private static final String TAG = "WorkspaceBiz";
    private static WorkspaceBiz instance = new WorkspaceBiz();
    private Context context;

    private WorkspaceBiz() {
    }

    public WorkspaceBiz init(Context context) {
        if (context == null) {
            throw new RuntimeException("Context不能为空");
        }
        this.context = context.getApplicationContext();

        DaoFactory.getInstance().init(context);

        return instance;
    }

    public WorkspaceBiz initBizHandler(BizHandler bizHandler) {
        BizProxy.getInstance().init(bizHandler);
        return this;
    }

    public static WorkspaceBiz getInstance() {
        return instance;
    }

    public List<InteractionNewsBean> getScrollNewsFromCache() {
        return CacheProxy.getInstance().getCacheList(BizUtils.getCacheKeyScrollNews(), InteractionNewsBean.class);
    }

    /**
     * 从缓存获取新闻数据.
     *
     * @return 新闻列表.
     */
    public List<InteractionNewsBean> getNewsFromCache() {
        PascLog.d(TAG, "正在获取缓存新闻数据");
        return CacheProxy.getInstance().getCacheList(BizUtils.getCacheKeyNews(), InteractionNewsBean.class);
    }

    public List<InteractionNewsBean> getNewsFromNet() {
        PascLog.d(TAG, "正在获取网络新闻数据");
        List<InteractionNewsBean> news = DaoFactory.getInstance().getNewsDao().getNews();

        if (CollectionUtils.isNotEmpty(news)) {
            PascLog.d(TAG, "获取网络新闻数据成功");

            PascLog.d(TAG, "正在缓存网络新闻数据");
            CacheProxy.getInstance().saveCache(BizUtils.getCacheKeyNews(), news);
            PascLog.d(TAG, "缓存网络新闻数据成功");
        }
        return news;
    }

    public List<InteractionNewsBean> getScrollNewsFromNet(Context context) {
        PascLog.d(TAG, "正在从网络获取滚动新闻");
        BizUtils.checkNetwork(context);

        ScrollNewsResp netData = DaoFactory.getInstance().getScrollNewsDao().getScrollNews();
        List<InteractionNewsBean> scrollNews = netData.scrollNews;

        if (CollectionUtils.isNotEmpty(scrollNews)) {
            PascLog.d(TAG, "正在缓存滚动新闻");
            CacheProxy.getInstance().saveCache(BizUtils.getCacheKeyScrollNews(), scrollNews);
        }

        return scrollNews;
    }

    public TodayWaterQualityResp getTodayWaterInfoFromCache() {
        TodayWaterQualityResp cache = null;
        try {
            cache = CacheProxy.getInstance().getCache(BizUtils.getCacheKeyTodayWater(), TodayWaterQualityResp.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cache;
    }

    public TodayWaterQualityResp getTodayWaterInfoFromNet(Context context) {
        BizUtils.checkNetwork(context);

        TodayWaterQualityResp netData = BizUtils.getNetData(ApiGenerator.createApi(WaterQualityApi.class)
                .getMainPageWaterQualityFromNetSync(new BaseParam<>(VoidObject.getInstance())));

        if (netData.quality != null) {
            CacheProxy.getInstance().saveCache(BizUtils.getCacheKeyTodayWater(), netData);
        }
        return netData;
    }

    public HouseSecurityResp getHouseSecurityFromCache() {
        return CacheProxy.getInstance().getCache(BizUtils.getCacheKeyHouseSecurity(), HouseSecurityResp.class);
    }

    public HouseSecurityResp getHouseSecurityFromNet(Context context) {
        BizUtils.checkNetwork(context);

        HouseSecurityResp netData = BizUtils.getNetData(ApiGenerator.createApi(HouseSecurityApi.class)
                .getHouseSecurityFromNetSync(new BaseParam<>(VoidObject.getInstance())));

        if (netData != null) {
            PascLog.d(TAG, "正在缓存住房保障信息");
            CacheProxy.getInstance().saveCache(BizUtils.getCacheKeyHouseSecurity(), netData);
        }

        return netData;
    }

    public List<BannerBean> getBannerFromNet(Context context, String cellId, boolean isCutOut) {
        PascLog.d(TAG, "正在从网络获取Banner数据?cellId=" + cellId);
        BizUtils.checkNetwork(context);

        String token = UserProxy.getInstance().getToken();
        BannerDaoParams bannerDaoParams = new BannerDaoParams();
        bannerDaoParams.setCellId(cellId);
        bannerDaoParams.setToken(token);
        bannerDaoParams.setCutOut(isCutOut);
        AppBannerRsp data = DaoFactory.getInstance().getBannerDao().getBanner(bannerDaoParams);

        final List<BannerBean> bannerBeans = data.bannerBeans;
        if (CollectionUtils.isNotEmpty(bannerBeans)) {
            PascLog.d(TAG, "正在缓存从网络获取到的Banner数据");
            CacheProxy.getInstance().saveCache(CacheKeyManager.getInstance().getBannerCacheKey(cellId), JsonUtils.toJson(bannerBeans));
            PascLog.d(TAG, "缓存网络Banner数据结束");
        }
        PascLog.d(TAG, "网络的Banner数据?size=" + (bannerBeans == null ? 0 : bannerBeans.size()));
        return bannerBeans;
    }

    public List<BannerBean> getBannerFromCache(String cellId) {
        PascLog.d(TAG, "正在从缓存获取Banner数据?cellId=" + cellId);
        return CacheProxy.getInstance().getCacheList(CacheKeyManager.getInstance().getBannerCacheKey(cellId), BannerBean.class);
    }

    public MyAffairListResp getMainPageAffairListFromNet(Context context) {
        PascLog.d(TAG, "正在从网络获取我的进度查询");
        BizUtils.checkNetwork(context);

        BaseTokenParam baseTokenParam = new BaseTokenParam();
        baseTokenParam.setToken(UserProxy.getInstance().getToken());
        BaseParam<BaseTokenParam> baseParam = new BaseParam<>(baseTokenParam);
        MyAffairListResp netData = BizUtils.getNetData(ApiGenerator.createApi(AffairsApi.class)
                .getMainPageAffairsListSync(baseParam));

        return netData;
    }

    public ConfigItem getConfigFromCacheOrAssets(Context context, String configId) {
        ArgumentUtils.assertNotNull(context);
        ArgumentUtils.assertNotEmpty(configId);

        PascLog.d(TAG, "从缓存获取配置?configId=" + configId);
        ConfigItem configItem = null;
        try {
            configItem = ConfigUtils.getConfigFromCache(context, configId);
            if (configItem == null) {
                PascLog.d(TAG, "无缓存配置?configId=" + configId);
                configItem = getConfigFromAssetsInside(context, configId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return configItem;
    }

    private static ConfigItem getConfigFromAssetsInside(Context context, String configId) {
        String fileName = getAssetFileName(configId);
        String assetsJson = AssetUtils.getString(context, fileName);
        ConfigItem configItem = new ConfigItem();
        configItem.configContent = assetsJson;
        return configItem;
    }

    /**
     * 获取Asset文件名称.
     *
     * @param configId 配置id.
     * @return 文件名称.
     */
    private static String getAssetFileName(String configId) {
        return ASSET_MAIN + configId + JSON_SUFFIX;
    }

    public ConfigItem getConfigFromAssets(Context context, String configId) {
        PascLog.d(TAG, "从Assets获取配置?configId=" + configId);

        ArgumentUtils.assertNotNull(context);
        ArgumentUtils.assertNotEmpty(configId);

        ConfigItem configFromAssets = null;
        try {
            configFromAssets = getConfigFromAssetsInside(context, configId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return configFromAssets;
    }

    public ConfigItem getConfigFromNet(Context context, String configId, String testFlag, String url) {
        PascLog.d(TAG, "从网络获取配置?configId=" + configId + "&testFlag=" + testFlag + "&url=" + url);

        ArgumentUtils.assertNotNull(context);
        ArgumentUtils.assertNotEmpty(configId);
        if (testFlag == null) {
            testFlag = "1"; // 默认是测试环境
        }

        BizUtils.checkNetwork(context);

        ConfigDaoParams configDaoParams = new ConfigDaoParams();
        configDaoParams.setConfigId(configId);
        configDaoParams.setTestFlag(testFlag);
        configDaoParams.setUrl(url);
        ConfigItem configFromCache = ConfigUtils.getConfigFromCache(context, configId);
        if (configFromCache != null) {
            configDaoParams.setConfigVersion(configFromCache.configVersion);
        }

        ConfigResp configResp = DaoFactory.getInstance().getConfigDao().getConfig(configDaoParams);

        ConfigItem result = null;
        if (configResp != null) {
            List<ConfigItem> configItems = configResp.list;
            if (CollectionUtils.isNotEmpty(configItems)) {
                result = configItems.get(0);
            }
        }
        if (result != null) {
            PascLog.d(TAG, "保存配置到缓存?configId=" + configId);
            String phoneNumber = UserProxy.getInstance().getPhoneNum();
            saveConfigToCache(context, result, phoneNumber);
        }

        return result;
    }

    /**
     * 保存配置信息到缓存.
     *
     * @param context 上下文.
     * @param config  配置信息.
     */
    private static void saveConfigToCache(Context context, ConfigItem config, String phoneNumber) {
        String configCacheKey = BizUtils.getCacheKeyConfig(context, config.configId, phoneNumber);
        CacheProxy.getInstance().saveCache(configCacheKey, config);
    }

    public ConfigItem getConfigFromNet(Context context, String configId, String testFlag) {
        return getConfigFromNet(context, configId, testFlag, null);
    }

    /**
     * 获取民生资讯新闻.
     *
     * @param pageSize 分页大小.
     * @param source   来源，4代表是首页.
     * @return 民生资讯新闻.
     */
    public InteractionNewsResp getPeopleLiveNewsFromNet(int pageSize, int source) {
        return BizProxy.getInstance().getPeopleLiveNewsFromNet(pageSize, source);
    }

    public InteractionNewsResp getPeopleLiveNewsFromCache(int pageSize, int source) {
        return BizProxy.getInstance().getPeopleLiveNewsFromCache(pageSize, source);
    }

    public int getUnReadMessageCount() {
        return BizProxy.getInstance().getUnReadMessageCount();
    }

    public MainPageWeatherInfo getWeatherInfoFromNet(WeatherCity city) {
        PascLog.d(TAG, "正在获取网络天气信息");
        WeatherDaoParams params = new WeatherDaoParams();
        params.setCityName(city.getCityName());
        params.setShowName(city.getShowName());
        params.setLatitude(city.getLatitude());
        params.setLongitude(city.getLongitude());
        params.setLocation(city.isLocation());
        params.setDistrictName(city.getDistrictName());
        MainPageWeatherInfo weatherInfo = DaoFactory.getInstance().getWeatherDao().getWeatherFromNet(params);
        if (weatherInfo != null) {
            PascLog.d(TAG, "获取网络天气信息成功");
        }
        return weatherInfo;
    }

    public MainPageWeatherInfo getWeatherInfoFromCache(WeatherCity city) {
        PascLog.d(TAG, "正在获取缓存天气信息");
        WeatherDaoParams params = new WeatherDaoParams();
        params.setCityName(city.getCityName());
        params.setShowName(city.getShowName());
        params.setLatitude(city.getLatitude());
        params.setLongitude(city.getLongitude());
        params.setLocation(city.isLocation());
        params.setDistrictName(city.getDistrictName());
        MainPageWeatherInfo weatherInfo = DaoFactory.getInstance().getWeatherDao().getWeatherFromCache(params);
        if (weatherInfo != null) {
            PascLog.d(TAG, "获取缓存天气信息成功");
        }
        return weatherInfo;
    }

    /**
     * 获取首页的公告.
     *
     * @return 首页公告.
     */
    public Announcement getHomePageAnnouncement() {
        PascLog.d(TAG, "正在获取网络首页公告数据");
        AnnouncementDaoParams params = new AnnouncementDaoParams();
        params.setOsType("android");
        params.setPublishPage("index_tab");
        String versionName = AppUtils.getVersionName();
        params.setVersion(versionName);
        AnnouncementRsp announcementRsp = DaoFactory.getInstance().getAnnouncementDao().getAnnouncement(params);
        if (announcementRsp != null) {
            PascLog.d(TAG, "获取网络首页公告数据成功");

            PascLog.d(TAG, "正在查询首页公告数据是否已读");
            List<Announcement> announcementList = announcementRsp.getAnnouncementList();
            if (announcementList != null && announcementList.size() > 0) {
                Announcement announcement = announcementList.get(0);
                int id = announcement.getId();
                String cacheKeyReadAnnouncement = BizUtils.getCacheKeyReadAnnouncement(String.valueOf(id));
                Boolean isRead = CacheProxy.getInstance().getCache(cacheKeyReadAnnouncement, Boolean.class);
                if (isRead == null) {
                    Log.d(TAG, "首页公告数据未读");
                    return announcement;
                } else {
                    Log.d(TAG, "首页公告数据已读");
                }
            }
        }
        return null;
    }

    public void readAnnouncement(String id) {
        String cacheKeyReadAnnouncement = BizUtils.getCacheKeyReadAnnouncement(id);
        CacheProxy.getInstance().saveCache(cacheKeyReadAnnouncement, true);
        Boolean cache = CacheProxy.getInstance().getCache(cacheKeyReadAnnouncement, Boolean.class);
        Log.d(TAG, "缓存公告已读信息：" + cache);
    }

    public WeatherCity getWeatherCity() {
        PascLog.d(TAG, "正在获取缓存的城市信息");
        WeatherCity weatherCity = DaoFactory.getInstance().getWeatherDao().getWeatherCity();
        if (weatherCity != null) {
            PascLog.d(TAG, "获取缓存的城市信息成功");
        }
        return weatherCity;
    }

    public WeatherCity saveWeatherCity(WeatherCity weatherCity) {
        PascLog.d(TAG, "正在保存城市信息");
        WeatherCity weatherCitySaved = DaoFactory.getInstance().getWeatherDao().saveWeatherCity(weatherCity);
        if (weatherCitySaved != null) {
            PascLog.d(TAG, "保存城市信息成功");
        }
        return weatherCitySaved;
    }
}