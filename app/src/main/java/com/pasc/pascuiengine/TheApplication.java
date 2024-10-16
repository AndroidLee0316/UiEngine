package com.pasc.pascuiengine;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.widget.ImageView;

import com.amap.api.maps.MapsInitializer;
import com.pasc.business.feedback.net.FeedBackManager;
import com.pasc.business.mine.config.MineUrlDispatcher;
import com.pasc.business.ota.OtaManager;
//import com.pasc.business.paservice.biz.IPaServiceBiz;
//import com.pasc.business.paservice.manager.PaServiceManager;
import com.pasc.business.user.PascUserManager;
import com.pasc.business.user.PascUserManagerImpl;
import com.pasc.business.workspace.WorkspaceManager;
import com.pasc.lib.base.AppProxy;
import com.pasc.lib.base.event.BaseEvent;
import com.pasc.lib.base.user.IUserManager;
import com.pasc.lib.base.util.AppUtils;
import com.pasc.lib.gaode.location.GaoDeLocationFactory;
import com.pasc.lib.hiddendoor.utils.HiddenDoorManager;
import com.pasc.lib.hybrid.HybridInitConfig;
import com.pasc.lib.hybrid.PascHybrid;
import com.pasc.lib.hybrid.behavior.ConstantBehaviorName;
import com.pasc.lib.hybrid.callback.HybridInitCallback;
import com.pasc.lib.hybrid.callback.InjectJsCallback;
import com.pasc.lib.hybrid.callback.WebErrorListener;
import com.pasc.lib.hybrid.eh.behavior.GetVideoBehavior;
import com.pasc.lib.hybrid.eh.behavior.PlayVideoBehavior;
import com.pasc.lib.hybrid.eh.router.HybridBehaviorService;
import com.pasc.lib.hybrid.nativeability.WebStrategyType;
import com.pasc.lib.imageloader.PascImageLoader;
import com.pasc.lib.lbs.LbsManager;
import com.pasc.lib.log.LogConfiguration;
import com.pasc.lib.log.PascLog;
import com.pasc.lib.log.printer.Printer;
import com.pasc.lib.log.printer.file.FilePrinter;
import com.pasc.lib.log.printer.file.naming.DateFileNameGenerator;
import com.pasc.lib.log.utils.SDCardUtils;
import com.pasc.lib.net.ApiV2Error;
import com.pasc.lib.net.HttpDynamicParams;
import com.pasc.lib.net.NetConfig;
import com.pasc.lib.net.NetManager;
import com.pasc.lib.net.download.DownLoadManager;
import com.pasc.lib.net.resp.BaseV2Resp;
import com.pasc.lib.net.transform.NetV2Observer;
import com.pasc.lib.net.transform.NetV2ObserverManager;
import com.pasc.lib.openplatform.CertificationCallback;
import com.pasc.lib.openplatform.IBizCallback;
import com.pasc.lib.openplatform.InitJSSDKBehavior;
import com.pasc.lib.openplatform.OpenPlatformProvider;
import com.pasc.lib.openplatform.PascOpenPlatform;
import com.pasc.lib.openplatform.UserAuthBehavior;
import com.pasc.lib.router.BaseJumper;
import com.pasc.lib.router.RouterManager;
import com.pasc.lib.router.ServiceProtocol;
import com.pasc.lib.safe.SafeUtil;
import com.pasc.lib.share.ShareManager;
import com.pasc.lib.share.config.AppSecretConfig;
import com.pasc.lib.storage.fileDiskCache.FileCacheBuilder;
import com.pasc.lib.storage.fileDiskCache.FileCacheUtils;
import com.pasc.lib.userbase.base.RouterTable;
import com.pasc.lib.workspace.BizHandler;
import com.pasc.lib.workspace.ICache;
import com.pasc.lib.workspace.User;
import com.pasc.lib.workspace.api.DaoFactory;
import com.pasc.lib.workspace.bean.InteractionNewsResp;
import com.pasc.lib.workspace.handler.ProtocolHandler;
import com.pasc.lib.workspace.handler.UrlCreator;
import com.pasc.pascuiengine.BuildConfig;
import com.pasc.pascuiengine.MainActivity;
import com.pasc.pascuiengine.R;
import com.pasc.pascuiengine.event.EventTable;
import com.pasc.pascuiengine.impl.TDaoBuilderImpl;
import com.pasc.pascuiengine.utils.ConvertUtil;
import com.pasc.pascuiengine.utils.HeaderUtil;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

import static com.raizlabs.android.dbflow.config.FlowLog.TAG;


public class TheApplication extends Application {

    private static String SDCARD_LOG_FILE_DIR = "Smart/log";
    private static String DEFAULT_LOG_TAG = "smt";
    private static String SYSTEM_ID = "smt";

    private static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationContext = getApplicationContext();
        //文件存储初始化
        FileCacheUtils.init(this, new FileCacheBuilder());
        if (AppUtils.getPIDName(this).equals(getPackageName())) {//主进程
            initHiddenDoor(); //AppProxy初始化前调用才起作用
            initPascLog();  //AppProxy初始化前调用才起作用

            AppProxy.getInstance().init(this, false)
                    .setIsDebug(BuildConfig.DEBUG)
                    .setProductType(2)
                    .setHost(HiddenDoorManager.getInstance().getApiHost())
                    .setH5Host(HiddenDoorManager.getInstance().getApiHost())
                    .setVersionName(BuildConfig.VERSION_NAME);
            PascUserManager.getInstance().init(this,new PascUserManagerImpl(),null);
            /**********网络*************/
//            SafeUtil.init(this);
            /***注册服务***/
            initNet();
            initARouter();
            initHybrid();


            registerActivityListener();
            registerUserStatus();

            Completable.create(emitter -> {
                initLBS();
//                initARouter();
                initImageLoader();
                initWeather();
                initWorkspace();
                initLibShare();
                initMine();
                initFeedback();
            }).subscribeOn(Schedulers.computation()).observeOn(Schedulers.computation()).subscribe();


        }
    }

    private void certiPinner(NetConfig.Builder builder) {
//        PinnerProxy.addCertPinner(builder);
    }

    private void initFeedback() {
        FeedBackManager.setApiGet(new FeedBackManager.ApiGet() {
            @Override
            public String getFeedbackImageUrl() {
                return AppProxy.getInstance().getHost()+"/api/platform/feedback/uploadFeedbackImage";
            }

            @Override
            public String getFeedbackUrl() {
                return AppProxy.getInstance().getHost()+"/api/platform/feedback/submitFeedback";
            }

            @Override
            public String getToken() {
                return AppProxy.getInstance().getUserManager().getToken();
            }
        });
    }

    private void initMine() {
        MineUrlDispatcher.dispatch(this, "pasc.pingan.service.mine.json");
    }

    private void initHiddenDoor() {
        HiddenDoorManager.getInstance().init(this, "https://csapi.csx.cn","https://csweb.csx.cn/changsha", "api");
        HiddenDoorManager.getInstance().setLogPrintFileDefault(false);
        HiddenDoorManager.getInstance().setLogPrintAndroidDefault(true);
        HiddenDoorManager.getInstance().setLogCatchCrashDefault(true);
        HiddenDoorManager.getInstance().setLogReportNetDefault(false);
    }

    private void initPascLog() {
        PascLog.openAndroidPrinter(HiddenDoorManager.getInstance().isOpenAndroidPrinter());
        PascLog.openFilePrinter(HiddenDoorManager.getInstance().isOpenFilePrinter());
        PascLog.openCatchCrash(HiddenDoorManager.getInstance().isOpenCatchCrash());
        PascLog.openReportLog(HiddenDoorManager.getInstance().isOpenReportNet());

        String logFileDir = SDCardUtils.getAppDir(this, SDCARD_LOG_FILE_DIR);
        Printer printer = new FilePrinter.Builder(logFileDir).fileNameGenerator(new DateFileNameGenerator()).fileSaveTime(3).build();
        LogConfiguration configuration = new LogConfiguration.Builder().tag(DEFAULT_LOG_TAG).threadInfoEnable().stackTraceEnable(2).borderEnable().build();
        PascLog.init(this, SYSTEM_ID, configuration, new Printer[]{printer});
    }

    // 初始化工作台
    private void initWorkspace() {

        // 初始化
        WorkspaceManager workspaceManager = WorkspaceManager.getInstance().init(this);

        // 对接用户代理
        workspaceManager.initUserProxy(new User() {
            @Override
            public String getToken() {
                return AppProxy.getInstance().getUserManager().getToken();
            }

            @Override
            public String getPhoneNum() {
                return AppProxy.getInstance().getUserManager().getMobile();
            }

            @Override
            public String getUserId() {
                return AppProxy.getInstance().getUserManager().getUserId();
            }

            @Override
            public boolean hasLoggedOn() {
                return AppProxy.getInstance().getUserManager().isLogin();
            }

            @Override
            public void notifyOnlineStateChangeInvalidToken() {
                EventBus.getDefault().post(new BaseEvent("user_invalid_token"));
            }

            @Override
            public void notifyOnlineStateChangeKickOff() {
                EventBus.getDefault().post(new BaseEvent("user_invalid_token"));
            }

            @Override
            public void checkLoginWithCallBack(Activity activity, Runnable runnable) {

            }
        });

        // 对接URL根代理
        workspaceManager.initUrlProxy(new UrlCreator() {
            @Override
            public String getApiUrlRoot() {
                return AppProxy.getInstance().getHost();
            }

            @Override
            public String getH5UrlRoot() {
                return AppProxy.getInstance().getH5Host();
            }
        });

        // 对接业务逻辑
        workspaceManager.initBizHandler(new BizHandler() {
            @Override
            public InteractionNewsResp getPeopleLiveNewsFromNet(int i, int i1) {
                return null;
            }

            @Override
            public InteractionNewsResp getPeopleLiveNewsFromCache(int i, int i1) {
                return null;
            }

            @Override
            public int getUnReadMessageCount() {
                return 0;
            }
        });

        // 对接服务协议
        workspaceManager.initProtocolHandler(new ProtocolHandler() {
            @Override
            public void handle(Activity activity, String url, Map<String, String> map) {
                ServiceProtocol.instance().startService(activity, url, map);
            }
        });

        // 对接数据传输
        workspaceManager.initDaoBuilder(new TDaoBuilderImpl(this));
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
    }

    private void initLibShare() {
        //初始化三方登录
        AppSecretConfig.Builder builder = new AppSecretConfig.Builder();
        //深圳测试环境的appId: qq：1106846108，微信：wx764f76ca1629210d
        builder.setQqAppId("101525610").setWechatAppId("wxce4d13ccd4df6040");
//        builder.setQqAppId("101525610").setWechatAppId("wx013e9e5bdbe8a2b3");
        //ShareManager.getInstance().init(this, builder.build());
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    public static Context getApplication() {
        return applicationContext;
    }

    /**
     * 初始化路由
     */
    private void initARouter() {
        RouterManager.initARouter(this, BuildConfig.DEBUG);
        RouterManager.instance().setApiGet(new com.pasc.lib.router.interceptor.ApiGet() {
            @Override
            public boolean isLogin() {
                // 路由拦截是否 已经等；
                return AppProxy.getInstance().getUserManager().isLogin();
            }

            @Override
            public boolean isCertification() {
                // 路由拦截是否 已经 实名认证；
                return AppProxy.getInstance().getUserManager().isCertified();
            }

            @Override
            public void beforeInterceptor(String path, Bundle bundle) {
//                if ("/user/login/main".equals (path)){
//                    //跳转登陆界面之前，把之前的登陆 拦截器取消
//                    LoginInterceptor.notifyCallBack (false);
//                }else if ("/user/account_security/act".equals (path)){
//                    //跳转实名认证界面之前，把之前的实名认证 拦截器取消
//                    CertificationInterceptor.notifyCallBack (false);
//                }
            }

            @Override
            public void gotoLogin(String targetPath, Bundle targetBundle) {
                // 路由拦截 跳转登陆；
                BaseJumper.jumpARouter(RouterTable.Login.PATH_LOGIN_ACTIVITY);
            }

            @Override
            public void gotoCertification(String targetPath, Bundle targetBundle) {
                //去认证 todo
                BaseJumper.jumpARouter(RouterTable.User.PATH_USER_ACCOUNT_SECURITY_ACT);
            }
        });
    }

    /****初始化网络****/
    private void initNet() {
        // 测试全屏、弹屏广告专用HostUrl
        NetConfig.Builder builder = new NetConfig.Builder(this);
        certiPinner(builder);
        NetConfig config = builder
                .baseUrl(HiddenDoorManager.getInstance().getApiHost())
                .headers(HeaderUtil.getHeaders(BuildConfig.DEBUG))
                .gson(ConvertUtil.getConvertGson())
                .isDebug(BuildConfig.DEBUG)
                .build();
        NetManager.init(config);

        HttpDynamicParams.getInstance().setDynamicParam(new HttpDynamicParams.DynamicParam() {
            @Override
            public Map<String, String> headers() {
                return HeaderUtil.dynamicHeaders();
            }
        });

        DownLoadManager.getDownInstance().init(this, 3, 5, 0);
    }

    private void initLBS() {
        //高德定位初始化
//        GaoDeLocationFactory factory = new GaoDeLocationFactory(getApplicationContext());
//        LbsManager.getInstance().initLbs(factory);
    }


    /**
     * 初始化图片加载框架
     */
    private void initImageLoader() {
        PascImageLoader.getInstance().init(this, PascImageLoader.GLIDE_CORE, R.color.C_EAF7FF);
    }


//    /**
//     * 初始化平安服务
//     */
//    private void initPaService() {
//        PaServiceManager.getInstance().initConfig("pasc.pingan.paservice.json", new IPaServiceBiz() {
//            @Override
//            public String getUserId() {
//                return AppProxy.getInstance().getUserManager().getUserId();
//            }
//
//            @Override
//            public String getMobileNo() {
//                return AppProxy.getInstance().getUserManager().getMobile();
//            }
//
//            @Override
//            public String getToken() {
//                return AppProxy.getInstance().getUserManager().getToken();
//            }
//
//            @Override
//            public boolean isOpenFace() {
//                return AppProxy.getInstance().getUserManager().isOpenFaceVerify();
//            }
//        });
//
//    }

    private void initWeather() {
//        WeatherUIManager.getInstance().init(this);
//        WeatherUIManager.getInstance().setGateway("/api");
//        .setBackDrawableIcon(R.drawable.ic_back_black)
//
//        .setSelectCityTextColor(R.color.red_d83e51)
//        .setLocationDrawableIcon(R.drawable.login_login_face)
//
//        .setToolbarDrawableIcon(R.drawable.login_login_face,R.drawable.login_login_face);

    }




    private void initOta() {
        OtaManager.instance().setApiGet(new OtaManager.ApiGet() {
            public String getUrl() {
                return AppProxy.getInstance().getHost() + "/api/platform/appVersion/queryNewVersionInfo";
            }
        });
    }


    private void initHybrid() {
        Completable.create(emitter -> {
            QbSdk.initX5Environment(getApplicationContext(), new QbSdk.PreInitCallback() {
                @Override
                public void onCoreInitFinished() {

                }

                @Override
                public void onViewInitFinished(boolean b) {

                }
            });
            HybridBehaviorService webBehaviorService = HybridBehaviorService.getInstance();
            PascHybrid.getInstance().init(new HybridInitConfig()
                    .addCustomerBehavior(ConstantBehaviorName.GET_USERINFO, webBehaviorService.GetUserInfoBehaviorObject())
                    .addCustomerBehavior(ConstantBehaviorName.OPEN_SHARE, webBehaviorService.ShareBehaviorObject())
                    .addCustomerBehavior(ConstantBehaviorName.CALL_PHONE, webBehaviorService.CallPhoneBehaviorObject())
                    .addCustomerBehavior(ConstantBehaviorName.WEB_BEHAVIOR_PREVIEW_IMAGES, webBehaviorService.PreviewPhotoBehaviorObject())
                    .addCustomerBehavior(ConstantBehaviorName.WEB_BEHAVIOR_BROWSE_FILE, webBehaviorService.BrowseFileBehaviorObject())
                    .addCustomerBehavior(ConstantBehaviorName.INIT_JSSDK, new InitJSSDKBehavior())
                    .addCustomerBehavior(ConstantBehaviorName.USER_AUTH, new UserAuthBehavior())
                    .addCustomerBehavior(ConstantBehaviorName.CHOOSE_VIDEO, new GetVideoBehavior())
                    .addCustomerBehavior(ConstantBehaviorName.PLAY_VIDEO, new PlayVideoBehavior())
                    .setHybridInitCallback(new HybridInitCallback() {

                        @Override
                        public void loadImage(ImageView imageView, String url) {
                            PascImageLoader.getInstance().loadImageUrl(url, imageView);
                        }

                        @Override
                        public void setWebSettings(WebSettings settings) {
                            settings.setUserAgent(settings.getUserAgentString()
                                    + "/openweb=paschybrid/MaanshanSMT_Android,VERSION:"
                                    + BuildConfig.VERSION_NAME);
                        }

                        @Override
                        public String themeColorString() {
                            return "#333333";
                        }

                        @Override
                        public int titleCloseButton() {
                            return WebStrategyType.CLOSEBUTTON_FRISTPAGE_GONE;
                        }

                        @Override
                        public void onWebViewCreate(WebView webView) {

                        }

                        @Override
                        public void onWebViewProgressChanged(WebView webView, int i) {

                        }

                        @Override
                        public void onWebViewPageFinished(WebView webView, String s) {

                        }


                    })
                    .setWebErrorListener(new WebErrorListener() {
                        @Override
                        public void onWebError(int i, String s, String failUrl) {
//                            NetCollectIssueInfo netCollectIssueInfo = new NetCollectIssueInfo(NetCollectIssueInfo.INFO_TYPE_WEB);
//                            netCollectIssueInfo.setUrl(failUrl);
//                            netCollectIssueInfo.setErrorCode(i);
//                            netCollectIssueInfo.setErrorMsg(s);
//                            netCollectIssueInfo.setErrorType(NetCollectIssueInfo.ERROR_TYPE_SERVICE_ERR);
//                            PascAPMCollectProxy.getInstance().collectInfo(netCollectIssueInfo);
                        }
                    }).setInjectJsCallback(new InjectJsCallback() {
                        @Override
                        public void injectJs(WebView webView) {
                            String jsStr1 = "";
                            try {
                                InputStream in = getApplicationContext().getAssets().open("webBackOverride.js");
                                byte buff[] = new byte[1024];
                                ByteArrayOutputStream fromFile = new ByteArrayOutputStream();
                                do {
                                    int numRead = in.read(buff);
                                    if (numRead <= 0) {
                                        break;
                                    }
                                    fromFile.write(buff, 0, numRead);
                                } while (true);
                                jsStr1 = fromFile.toString();
                                Log.d(TAG, "jsStr1=" + jsStr1);
                                in.close();
                                fromFile.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.d(TAG, "IOException=" + e);

                            }
                            webView.evaluateJavascript(jsStr1, new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String value) {//js与native交互的回调函数
                                    Log.d(TAG, "value=" + value);
                                }
                            });
                        }
                    }));

            PascOpenPlatform.getInstance().init(new OpenPlatformProvider() {
                /*
                   开放平台的baseURL，不同城市有不同的host
                   */
                @Override
                public String getOpenPlatformBaseUrl() {
                    return BuildConfig.DEBUG ? "http://cssc-smt-stg5.yun.city.pingan.com/api/auth"
                            : "http://cssc-smt-stg5.yun.city.pingan.com/api/auth";
                }

                @Override
                public void getUserToken(IBizCallback iBizCallback) {
                    if (AppProxy.getInstance().getUserManager().isLogin()) {
                        iBizCallback.onLoginSuccess(AppProxy.getInstance().getUserManager().getToken());
                    }
                }

                @Override
                public void getCertification(Context context, CertificationCallback certificationCallback) {
                    if (AppProxy.getInstance().getUserManager().isCertified()) {
                        certificationCallback.certification(true);
                    } else {
                        certificationCallback.certification(false);
                    }
                }

                /*
                               开放平台可以动态注册交互行为，由server端返回交互行为名称列表，我们再去动态注册交互行为，可以保证
                               一定的安全性
                               nativeApis: 需要注册的交互行为协议名称列表
                               */
                @Override
                public void openPlatformBehavior(com.pasc.lib.hybrid.webview.PascWebView pascWebView, List<String> nativeApis) {

                }

                @Override
                public void onOpenPlatformError(int code, String msg) {

                }

                @Override
                public int getAppIcon() {
                    return 0;
                }

                @Override
                public int getStyleColor() {
                    return R.color.theme_color;
                }

                @Override
                public int getBackIconColor() {
                    return 0;
                }
            });
        }).subscribe();
    }

    /***监听网络 用户状态**/
    private void registerUserStatus() {
        NetV2ObserverManager.getInstance().registerObserver(new NetV2Observer() {
            @Override
            public void notifyErrorNet(BaseV2Resp baseV2Resp) {
                String code = baseV2Resp.code;
                if ("200".equals(code)) {
                } else {
                    if (("USER_TOKEN_INVALID".equals(code))) {//token失效了
                        BaseEvent baseEvent = new BaseEvent(EventTable.User.user_invalid_token_tag);
                        EventBus.getDefault().post(baseEvent);
                        AppProxy.getInstance().getUserManager().exitUser(getApplicationContext());
                        BaseJumper.jumpARouter(RouterTable.Login.PATH_LOGIN_ACTIVITY);
                        throw new ApiV2Error(code, "");
                    } else if ("USER_TOKEN_KICK".equals(code)) {//账号被踢了
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        BaseEvent baseEvent = new BaseEvent(EventTable.User.user_kickoff_tag);
                        EventBus.getDefault().post(baseEvent);
                        AppProxy.getInstance().getUserManager().userKicked(getApplicationContext());
                        throw new ApiV2Error(code, "");
                    }
//                    else {
//                        throw new ApiV2Error (code, baseV2Resp.msg);
//                    }
                }
            }
        });
    }

    private WeakReference<Activity> mCurrentActivity;

    public WeakReference<Activity> getCurrentActivity() {
        return mCurrentActivity;
    }

    private void registerActivityListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle bundle) {
                    //do nothing
                }

                @Override
                public void onActivityStarted(Activity activity) {
                    //do nothing
                }

                @Override
                public void onActivityResumed(Activity activity) {
                    mCurrentActivity = new WeakReference<>(activity);
                }

                @Override
                public void onActivityPaused(Activity activity) {
                    //do nothing
                }

                @Override
                public void onActivityStopped(Activity activity) {
                    //do nothing
                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                    //do nothing
                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    //do nothing
                }
            });
        }

    }


    public class LooperThread extends Thread {
        public Handler handler1;

        @Override
        public void run() {
            super.run();
            Looper.prepare();
            handler1 = new Handler() {
                public void handleMessage(Message msg) {
                }
            };

            Message m = handler1.obtainMessage();
            m.what = 0x11;
            handler1.sendMessage(m);
            Looper.loop();
        }
    }
}

