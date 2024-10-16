//package com.pasc.business.life.tangram;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.text.TextUtils;
//import android.view.View;
//
//import com.pasc.business.life.event.Event;
//import com.pasc.business.life.net.H5UrlManager;
//import com.pasc.business.life.net.UrlManager;
//import com.pasc.business.life.util.ConfigUtils;
//import com.pasc.business.life.util.LoginSuccessActionUtils;
//import com.pasc.business.life.util.UtilsOnClick;
//import com.pasc.business.paservice.manager.PaServiceManager;
//import com.pasc.business.paservice.manager.PafManager;
//import com.pasc.lib.base.AppProxy;
//import com.pasc.lib.base.ICallBack;
//import com.pasc.lib.base.util.ToastUtils;
//import com.pasc.lib.hybrid.PascHybrid;
//import com.pasc.lib.hybrid.nativeability.WebStrategy;
//import com.pasc.lib.hybrid.nativeability.WebStrategyType;
//import com.pasc.lib.log.PascLog;
//import com.pasc.lib.router.BaseJumper;
//
//import com.tmall.wireless.tangram.structure.BaseCell;
//import com.tmall.wireless.tangram.support.SimpleClickSupport;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.util.Iterator;
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import org.json.JSONObject;
//
//import static java.util.regex.Pattern.compile;
//
//
//public class TangramClickSupport extends SimpleClickSupport {
//
//    private static final String TAG = "TangramClickSupport";
//    public static final String ON_CLICK = "onClick";
//    public static final String ON_CLICK_PARAMS = "onClickParams";
//    private final String mBlue_4b89dc = "#4b89dc";
//
//    public TangramClickSupport() {
//        setOptimizedMode(true);
//    }
//
//    /**
//     * 分析事件名称.
//     *
//     * @param url url.
//     * @return 事件名称.
//     */
//    private static String parseEventName(String url) {
//        Pattern compile = compile("^event://(\\w+)\\??");
//        Matcher matcher = compile.matcher(url);
//        while (matcher.find()) {
//            String str = matcher.group(1);
//            return str;
//        }
//        return null;
//    }
//
//    @Override
//    public void defaultClick(final View targetView, BaseCell cell, int eventType) {
//        super.defaultClick(targetView, cell, eventType);
//        try {
//            Context context = targetView.getContext();
//            String url = cell.extras.optString(ON_CLICK);
//            final String nfcUri = url;
//            PascLog.d(TAG, "点击cell?eventType=" + eventType + "&url=" + url);
//            if (!TextUtils.isEmpty(url)) {
//                if (url.contains(ConfigUtils.NFC_PATH)) {
//                    if (UtilsOnClick.performClick()) {
//                        SearchOnclicSupport.getInstance().onItemClick((Activity) context, url.replace(ConfigUtils.EVENT_PREFIX, ""));
//                    }
//                    return;
//                }
//                url = handleUrlPlaceholder(url);
//                JSONObject paramsJsonObject = cell.extras.optJSONObject(ON_CLICK_PARAMS);
//                if (eventType == ConfigUtils.EVENT_TYPE_NATIVE) {
//                    String packageName = context.getPackageName();
//                    String className = "";
//                    //                            = UrlUtils.parseClassName(url, packageName);
//                    if (!TextUtils.isEmpty(className)) {
//                        Intent intent = new Intent();
//                        intent.setClassName(context, className);
//
//                        LinkedHashMap<String, String> params = parseParams(url, paramsJsonObject);
//                        handleParamsUrlPlaceholder(params);
//
//                        for (Map.Entry<String, String> entry : params.entrySet()) {
//                            String key = entry.getKey();
//                            String value = entry.getValue();
//                            intent.putExtra(key, value);
//                        }
//
//                        if (context instanceof Activity) {
//                            context.startActivity(intent);
//                        } else {
//                            PascLog.e(TAG, "上下文不是Activity不能跳转");
//                        }
//                    }
//
//                } else if (eventType == ConfigUtils.EVENT_TYPE_EVENT) {
//                    defaultClickEvent(targetView, cell);
//                } else if (eventType == ConfigUtils.EVENT_TYPE_NATIVE_ROUTER) {
//                    LinkedHashMap<String, String> params = parseParams(url, paramsJsonObject);
//                    handleParamsUrlPlaceholder(params);
//
////                    if (cell.extras.optString(ON_CLICK).startsWith("router://com.pingan.cs.map/map/main")) {
////                        //跳转到附近地图
////                        MapJumper.jumpNearbyActivity(new Bundle(), "");
////                        return;
////                    }
//
//                    //todo 路由直接打开wevivew,后续hybrid框架实现
//                    if (cell.extras.optString(ON_CLICK).startsWith("router://com.pasc" +
//                            ".smt/hybrid/webView/")) {
//                        if (cell.extras.optString(ON_CLICK).startsWith("router://com.pasc" +
//                                ".smt/hybrid/webView/noToolbar")) {
//
//                            PascHybrid.getInstance().start(context, new WebStrategy().setUrl(params.get
//                                    ("url")).setOldIntercept(WebStrategyType.OLD_INTERCEPT_ON)
//                                    .setToolBarVisibility(WebStrategyType.TOOLBAR_GONE)
//                                    .setStatusBarVisibility(WebStrategyType.STATUSBAR_VISIBLE)
//                                    .setStatusBarColor(mBlue_4b89dc));
//                        } else {
//                            if (params.get("url").contains("http://www.jscsfc.com/zjwxsite/webservice/FacilitiesRepairReport")) {
//                                PascHybrid.getInstance().start(context, new WebStrategy().setUrl(params.get
//                                        ("url")).setTitle("市政设施报修").setOldIntercept(WebStrategyType.OLD_INTERCEPT_ON)
//                                        .setStatusBarVisibility(WebStrategyType.STATUSBAR_VISIBLE));
//                            } else {
//
//                                PascHybrid.getInstance().start(context, new WebStrategy().setUrl(params.get
//                                        ("url")).setOldIntercept(WebStrategyType.OLD_INTERCEPT_ON)
//                                        .setStatusBarVisibility(WebStrategyType.STATUSBAR_VISIBLE));
//                            }
//                        }
//                    } else if (cell.extras.optString(ON_CLICK).startsWith("router://scenery")) {
//
//                        PascHybrid.getInstance().start(context, new WebStrategy().setUrl
//                                (handleUrlPlaceholder(params.get("url"))).setOldIntercept(
//                            WebStrategyType.OLD_INTERCEPT_ON)
//                                .setToolBarVisibility(WebStrategyType.TOOLBAR_VISIBLE)
//                                .setStatusBarVisibility(WebStrategyType.TOOLBAR_VISIBLE));
//                        //                                                        .setStatusBarColor(mBlue_4b89dc))
//                        if (cell.extras.optString(ON_CLICK).startsWith("router://scenery/type/lobby")) {
//                            SearchOnclicSupport.getInstance().onItemClick(cell.extras.toString());
//                        };
//                    } else {
//                        Bundle bundle = new Bundle();
//                        for (Map.Entry<String, String> m : params.entrySet()) {
//                            bundle.putString(m.getKey(), m.getValue());
//                        }
//                        BaseJumper.jumpBundleARouter(url, bundle);
//                    }
//                } else if (eventType == ConfigUtils.EVENT_TYPE_WEB) {
//                                        //SimpleBrowserActivity.actionStart(context, url);
//                } else if (eventType == ConfigUtils.EVENT_TYPE_NATIVE_MAP) {
//                    if (context instanceof Activity) {
//                        //                        ProtocolUtils.parseSmtProtocol((Activity) context, url);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            PascLog.e(TAG, "Tangram点击事件发生异常：" + e.getMessage());
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//     * 点击事件，触发事件协议.
//     *
//     * @param targetView 视图.
//     * @param cell       对应的cell.
//     */
//    private void defaultClickEvent(View targetView, BaseCell cell) {
//        String url = cell.extras.optString(ON_CLICK);
//        if (TextUtils.isEmpty(url)){
//            return;
//        }
//
//        Object event = buildEvent(targetView.getContext(), url);
//        if (event == null){
//            PascLog.d(TAG, "事件协议无效?url=" + url);
//        }
//        if (event == null){
//            return;
//        }
//
//        putParamsIntoEvent(event, cell, url);
//
//        //        EventBus.getDefault().post(event);
//    }
//
//    /**
//     * 将参数放进事件对象.
//     *
//     * @param event 事件对象.
//     * @param cell  Cell对象.
//     * @param url   地址.
//     */
//    private void putParamsIntoEvent(Object event, BaseCell cell, String url) {
//        //        if (event instanceof BaseEvent) {
//        //            JSONObject paramsJsonObject = cell.extras.optJSONObject(ON_CLICK_PARAMS);
//        //            LinkedHashMap<String, String> params = parseParams(url, paramsJsonObject);
//        //            handleParamsUrlPlaceholder(params);
//        //
//        //            BaseEvent baseEvent = (BaseEvent) event;
//        //            for (Map.Entry<String, String> entry : params.entrySet()) {
//        //                String key = entry.getKey();
//        //                String value = entry.getValue();
//        //                baseEvent.put(key, value);
//        //            }
//        //        }
//    }
//
//    /**
//     * 根据协议地址构建一个事件对象.
//     *
//     * @param url 协议地址.
//     * @return 事件对象.
//     */
//    private Object buildEvent(final Context context, String url) {
//        String prefix = ConfigUtils.EVENT_PREFIX;
//        final String userId = AppProxy.getInstance().getUserManager().getUserId();
//        final String mobileNo = AppProxy.getInstance().getUserManager().getMobile();
//
//        if (url.startsWith(prefix + Event.GOTO_PHONE_FEES)) {
//            LoginSuccessActionUtils.getInstance()
//                    .checkLoginWithCallBack(context, new ICallBack() {
//                        @Override
//                        public void callBack() {
//                            PaServiceManager.getInstance().getPafManager().startPaf((Activity) context, PafManager.FLAG_PAY_PHONE,
//                                userId, mobileNo);
//                        }
//                    });
//        } else if (url.startsWith(prefix + Event.GOTO_ELECTRICITY_FEES)) {
//            LoginSuccessActionUtils.getInstance()
//                    .checkLoginWithCallBack(context, new ICallBack() {
//                        @Override
//                        public void callBack() {
//                            PaServiceManager.getInstance().getPafManager().startPaf((Activity) context, PafManager.FLAG_PAY_WATER);
////                            IPayService iPayService = PayJumper.getIpayService();
////                            if (iPayService != null) {
////                                iPayService.startPafWater((Activity) context, UserManager
////                                        .getInstance().getUserId(), UserManager.getInstance()
////                                        .getMobileNo());
////                            }
//                        }
//                    });
//        } else if (url.startsWith(prefix + Event.GOTO_LOGIN)) {
//            LoginSuccessActionUtils.getInstance()
//                    .checkLoginWithCallBack(context, new ICallBack() {
//                        @Override
//                        public void callBack() {
//                        }
//                    });
//        } else if (url.startsWith(prefix + Event.GOTO_HOUSE_INDEX)) {
//            //AppJumper.jumpMainActivity(3);
//
//        } else if (url.startsWith(prefix + Event.GOTO_DATA_FEES)) {
//            LoginSuccessActionUtils.getInstance()
//                    .checkLoginWithCallBack(context, new ICallBack() {
//                        @Override
//                        public void callBack() {
//                            PaServiceManager.getInstance().getPafManager().startPaf((Activity) context, PafManager.FLAG_PAY_FLOW);
////                            IPayService iPayService = PayJumper.getIpayService();
////                            if (iPayService != null) {
////                                iPayService.startPafFlow((Activity) context, UserManager
////                                        .getInstance().getUserId(), UserManager.getInstance()
////                                        .getMobileNo());
////                            }
//                        }
//                    });
//        } else if (url.startsWith(prefix + Event.GOTO_ONE_WARN)) {
//            //HomepageJumper.jumpEmrgencyActivity();
//
//        } else if (url.startsWith(prefix + Event.GOTO_OIL_FEES)) {
//            LoginSuccessActionUtils.getInstance()
//                    .checkLoginWithCallBack(context, new ICallBack() {
//                        @Override
//                        public void callBack() {
//                            PaServiceManager.getInstance().getPafManager().startPaf((Activity) context, PafManager.FLAG_PAY_OIL);
////                            IPayService iPayService = PayJumper.getIpayService();
////                            if (iPayService != null) {
////                                iPayService.startPafOil((Activity) context, UserManager
////                                        .getInstance().getUserId(), UserManager.getInstance()
////                                        .getMobileNo());
////                            }
//                        }
//                    });
//        } else if (url.startsWith(prefix + Event.GOTO_RECOMMEND_APP)) {
//            //推荐app
//           /* String recommend = H5UrlManager.APP_SHARE_URL;
//
//            PascHybrid.getInstance().setToolBarCallback(new ToolBarCallback() {
//                @Override
//                public void toolBarRecycleCallback (WebCommonTitleView webCommonTitleView, Context context, boolean b) {
//                    String color = "#333333";
//                    webCommonTitleView.setLeftTextColor(Color.parseColor(color));
//                    webCommonTitleView.setTitleTextColor(Color.parseColor(color));
//                    webCommonTitleView.setRightTextColor(Color.parseColor(color));
//                    webCommonTitleView.setSubTitleColor(Color.parseColor(color));
//                    webCommonTitleView.getLeftIv().setColorFilter(Color.parseColor(color));
//                    webCommonTitleView.getRightIv().setColorFilter(Color.parseColor(color));
//                }
//            });
//
//
//            PascHybrid.getInstance()
//                    .start(context, new WebStrategy().setOldIntercept(WebStrategyType.OLD_INTERCEPT_ON)
//                            .setUrl(recommend).setTitle("推荐" + context.getResources().getString(R.string.app_name))
//                            .setToolBarVisibility(WebStrategyType.TOOLBAR_VISIBLE)
//                            .setStatusBarVisibility(WebStrategyType.STATUSBAR_VISIBLE)
//                            .setStatusBarColor("#00000000"));*/
//
//        } else if (url.startsWith(prefix + Event.GOTO_FEEDBACK)) {
//            //用户反馈
//            LoginSuccessActionUtils.getInstance()
//                    .checkLoginWithCallBack(context, new ICallBack() {
//                        @Override
//                        public void callBack() {
//                            //SettingsJumper.jumpFeedBackActivity();
//                        }
//                    });
//            //SettingsJumper.jumpFeedBackActivity();
//        } else if (url.startsWith(prefix + Event.GOTO_MY_CREDIT)) {
//            //我的信用
//            LoginSuccessActionUtils.getInstance()
//                    .checkLoginWithCallBack(context, new ICallBack() {
//                        @Override
//                        public void callBack() {
//                            //MineJumper.jumpPersonalCreditCSActivity();
//                            ToastUtils.toastMsg("我的信用");
//                        }
//                    });
//            //SettingsJumper.jumpFeedBackActivity();
//        } else if (url.startsWith(prefix + Event.GOTO_MAP_SERVICE)) {
//            //地图
//            //            LoginSuccessActionUtils.getInstance()
//            //                    .checkLoginWithCallBack(context, new ICallBack() {
//            //                        @Override
//            //                        public void callBack() {
//            //                            MapJumper.jumpNearbyActivity(new Bundle());
//            //                        }
//            //                    });
//            //MapJumper.jumpNearbyActivity(new Bundle(), "");
//            ToastUtils.toastMsg("地图待完善");
//        } else if (url.startsWith(prefix + Event.GOTO_CDSS_SERVICE)) {
//            LoginSuccessActionUtils.getInstance()
//                    .checkLoginWithCallBack(context, new ICallBack() {
//                        @Override
//                        public void callBack() {
//                            //HomepageJumper.jumpCdssActivity();
//                            ToastUtils.toastMsg("跳转CdssActivity待完善");
//
//                        }
//                    });
//        } else if (url.startsWith(prefix + Event.GOTO_SOCIAL_SECURITY)) {
//            LoginSuccessActionUtils.getInstance()
//                    .checkLoginWithCallBack(context, new ICallBack() {
//                        @Override
//                        public void callBack() {
//                            String append = "?uiparams={\"hideBottomLine\":\"false\"}";
//                            try{
//                                 append = "?uiparams="+ URLEncoder.encode("{\"hideBottomLine\":\"false\"}","utf-8");
//                            } catch (UnsupportedEncodingException e){
//                                e.printStackTrace();
//                            }
//
//                            //临时处理，url#后面无法识别参数。所以把#放在参数后面
//                          /*  String
//                                appSocialSecurityUrl = H5UrlManager.APP_SOCIAL_SECURITY_URL+append+"#/socialCard";
//                            PascHybrid.getInstance().start(context
//                                    , new WebStrategy().setUrl(appSocialSecurityUrl)
//
//                            );*/
//
//                            ToastUtils.toastMsg("GOTO_SOCIAL_SECURITY");
//
//                        }
//                    });
//        }
//        return null;
//    }
//
//
//    private void handleParamsUrlPlaceholder(LinkedHashMap<String, String> params) {
//        for (Map.Entry<String, String> entry : params.entrySet()) {
//            String key = entry.getKey();
//            if ("url".equals(key)) {
//                String value = entry.getValue();
//                value = handleUrlPlaceholder(value);
//                params.put(key, value);
//            }
//        }
//    }
//
//    private String handleUrlPlaceholder(String url) {
//        if (url.contains("{apiHost}")) {
//            url = url.replace("{apiHost}", UrlManager.API_HOST);
//        }
//        if (url.contains("{h5Host}")) {
//            url = url.replace("{h5Host}", H5UrlManager.H5_HOST);
//        }
//        if (url.contains("{token}")) {
//            url = url.replace("{token}", AppProxy.getInstance().getUserManager().getToken());
//        }
//        return url;
//    }
//
//    /**
//     * 从url和json对象分析参数.
//     *
//     * @param url              url地址.
//     * @param paramsJsonObject 参数的json对象.
//     * @return 参数列表集合.
//     */
//    @NonNull
//    private LinkedHashMap<String, String> parseParams(String url, JSONObject paramsJsonObject) {
//        LinkedHashMap<String, String> params = new LinkedHashMap<>();
//
//        // url参数
//        LinkedHashMap<String, String> urlParams = ConfigUtils.parseParamsByRegex(url);
//
//        // params参数
//        LinkedHashMap<String, String> jsonParams = parseParamsFromJsonObject(paramsJsonObject);
//
//        params.putAll(urlParams);
//        params.putAll(jsonParams);
//        return params;
//    }
//
//    /**
//     * 从jsonObject分析参数.
//     *
//     * @param params 参数对象.
//     * @return 参数列表.
//     */
//    private LinkedHashMap<String, String> parseParamsFromJsonObject(JSONObject params) {
//        LinkedHashMap<String, String> map = new LinkedHashMap<>();
//        if (params != null) {
//            // 所有参数都统一为字符串，兼容性更好
//            Iterator<String> keys = params.keys();
//            while (keys.hasNext()) {
//                String key = keys.next();
//                String value = params.optString(key);
//                map.put(key, value);
//            }
//        }
//        return map;
//    }
//}
