package com.pasc.demo.workspace;

import android.app.Activity;
import android.app.Application;

import com.pasc.business.workspace.WorkspaceManager;
import com.pasc.lib.base.AppProxy;
import com.pasc.lib.gaode.location.GaoDeLocationFactory;
import com.pasc.lib.imageloader.PascImageLoader;
import com.pasc.lib.lbs.LbsManager;
import com.pasc.lib.safe.SafeUtil;
import com.pasc.lib.statistics.StatisticsManager;
import com.pasc.lib.storage.fileDiskCache.FileCacheBuilder;
import com.pasc.lib.storage.fileDiskCache.FileCacheUtils;
import com.pasc.lib.workspace.BizHandler;
import com.pasc.lib.workspace.BuildConfig;
import com.pasc.lib.workspace.ICache;
import com.pasc.lib.workspace.User;
import com.pasc.lib.workspace.api.DaoFactory;
import com.pasc.lib.workspace.api.impl.DaoBuilderImpl;
import com.pasc.lib.workspace.bean.InteractionNewsResp;
import com.pasc.lib.workspace.handler.IStat;
import com.pasc.lib.workspace.handler.ProtocolHandler;
import com.pasc.lib.workspace.handler.UrlCreator;

import java.util.List;
import java.util.Map;

public class AppContext extends Application {

    private void initLBS() {
//        //高德定位初始化
//        GaoDeLocationFactory factory = new GaoDeLocationFactory(getApplicationContext());
//        LbsManager.getInstance().initLbs(factory);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        AppProxy.getInstance().init(this, false);

        initLBS();

        // 初始化
        WorkspaceManager workspaceManager = WorkspaceManager.getInstance().init(this);

        // 对接用户
        workspaceManager.initUserProxy(new User() {
            @Override
            public String getToken() {
                return null;
            }

            @Override
            public String getPhoneNum() {
                return null;
            }

            @Override
            public String getUserId() {
                return null;
            }

            @Override
            public boolean hasLoggedOn() {
                return true;
            }

            @Override
            public void notifyOnlineStateChangeInvalidToken() {

            }

            @Override
            public void notifyOnlineStateChangeKickOff() {

            }

            @Override
            public void checkLoginWithCallBack(Activity activity, Runnable callBack) {

            }
        });

        // 对接URL根
        workspaceManager.initUrlProxy(new UrlCreator() {
            @Override
            public String getApiUrlRoot() {
                return "BuildConfig.API_URL_ROOT";
            }

            @Override
            public String getH5UrlRoot() {
                return "https://smt-web-stg.pingan.com.cn:10443";
            }
        });

        // 对接业务逻辑
        workspaceManager.initBizHandler(new BizHandler() {
            @Override
            public InteractionNewsResp getPeopleLiveNewsFromNet(int pageSize, int source) {
                return null;
            }

            @Override
            public InteractionNewsResp getPeopleLiveNewsFromCache(int pageSize, int source) {
                return null;
            }

            @Override
            public int getUnReadMessageCount() {
                return 1;
            }
        });

        // 对接服务协议
        workspaceManager.initProtocolHandler(new ProtocolHandler() {
            @Override
            public void handle(Activity activity, String s, Map<String, String> map) {
                // 处理服务协议
            }
        });

        // 对接数据传输
        workspaceManager.initDaoBuilder(new DaoBuilderImpl(this));

        // 对接统计
        workspaceManager.initStatProxy(new IStat() {
            @Override
            public void onEvent(String eventId, Map<String, String> params) {
                StatisticsManager.getInstance().onEvent(eventId, params);
            }

            @Override
            public void onEvent(String eventId, String label, Map<String, String> params) {
                StatisticsManager.getInstance().onEvent(eventId, label, params);
            }
        });

        // 对接缓存
        workspaceManager.initCacheProxy(new ICache() {

            @Override
            public <T> T getCache(String key, Class<T> clazz) {
                try {
                    T model = FileCacheUtils.getModelSync(key, clazz);
                    return model;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void saveCache(String key, Object value) {
                FileCacheUtils.put(key, value);
            }

            @Override
            public <T> List<T> getCacheList(String key, Class<T> clazz) {
                List<T> result = null;
                try {
                    result = FileCacheUtils.getListSync(key, clazz);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }
        });

        DaoFactory.getInstance().setFake(true);

//        SafeUtil.init(this);
        // 文件存储初始化
        FileCacheUtils.init(this, new FileCacheBuilder());


        PascImageLoader.getInstance().init(this, PascImageLoader.GLIDE_CORE, R.drawable.ic_img_error_44);
    }
}
