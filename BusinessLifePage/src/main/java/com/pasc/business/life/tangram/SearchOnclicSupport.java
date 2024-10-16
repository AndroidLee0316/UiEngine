package com.pasc.business.life.tangram;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.pasc.business.life.event.Event;
import com.pasc.business.life.net.H5UrlManager;
import com.pasc.business.life.net.UrlManager;
import com.pasc.business.life.util.ConfigUtils;
import com.pasc.business.life.util.LoginSuccessActionUtils;
import com.pasc.business.life.util.UtilsOnClick;

import com.pasc.lib.base.AppProxy;
import com.pasc.lib.base.ICallBack;
import com.pasc.lib.base.util.ToastUtils;
import com.pasc.lib.hybrid.PascHybrid;
import com.pasc.lib.hybrid.nativeability.WebStrategy;
import com.pasc.lib.hybrid.nativeability.WebStrategyType;
import com.pasc.lib.life.R;
import com.pasc.lib.router.BaseJumper;
import com.pasc.lib.workspace.handler.OnClickLobbyInterface;
import com.pasc.lib.workspace.handler.OnClickSepportInterface;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 功能：
 * <p>
 * created by zoujianbo345
 * data : 2018/10/17
 */
public class SearchOnclicSupport {
    private static final String TAG = "SearchOnclicSupport";
    public static final String ON_CLICK = "onClick";
    public static final String ON_CLICK_PARAMS = "onClickParams";
    /**
     * 消息跳转H
     */
    public static final String TYPE_PUSH_H5 = "H5";
    /**
     * 消息跳转native
     */
    public static final String TYPE_PUSH_NATIVE = "Native";
    public static final String mBlue_4b89dc = "#ffffff";
    public  OnClickSepportInterface onClickSepportInterface;
    public OnClickLobbyInterface onClickLobby;

    public static SearchOnclicSupport getInstance() {
        return new SearchOnclicSupport();
    }

    public void init(OnClickSepportInterface onClickSepportInterface) {
        this.onClickSepportInterface = onClickSepportInterface;
    }

    public void setOnLobbClick(OnClickLobbyInterface onClickLobby) {
        this.onClickLobby = onClickLobby;
    }
    public void cleanOnLobbClick(){
        onClickLobby = null;
    }

    public static void onItemClick(Context context, String url, boolean hasToolbar) {
        if (TextUtils.isEmpty(url)) {
            ToastUtils.toastMsg(context.getResources().getString(R.string.not_realized_msg));
            return;
        }
        if (hasToolbar) {
            if (url.contains("http://www.jscsfc.com/zjwxsite/webservice/FacilitiesRepairReport")) {
                PascHybrid.getInstance().start(context, new WebStrategy().setUrl(url)
                        .setToolBarVisibility(WebStrategyType.TOOLBAR_VISIBLE).setStatusBarVisibility(
                        WebStrategyType.STATUSBAR_VISIBLE)
                        .setTitle("市政设施报修")
                        .setStatusBarVisibility(WebStrategyType.STATUSBAR_VISIBLE));
            } else if (url.startsWith(H5UrlManager.APP_FEEDBACK_URL)){
                //加入用户信息
                PascHybrid.getInstance().start(context, new WebStrategy().setUrl(url)
                        .setToolBarVisibility(WebStrategyType.TOOLBAR_VISIBLE)
                        .setOldIntercept(WebStrategyType.OLD_INTERCEPT_ON)
                        .setStatusBarVisibility(WebStrategyType.STATUSBAR_VISIBLE));
            }else {

                PascHybrid.getInstance().start(context, new WebStrategy().setUrl(url)
                        .setToolBarVisibility(WebStrategyType.TOOLBAR_VISIBLE).setOldIntercept(
                        WebStrategyType.OLD_INTERCEPT_ON)
                        .setStatusBarVisibility(WebStrategyType.STATUSBAR_VISIBLE));
            }

        } else {

            PascHybrid.getInstance().start(context, new WebStrategy().setUrl(url).setOldIntercept(
                WebStrategyType.OLD_INTERCEPT_ON)
                    .setToolBarVisibility(WebStrategyType.TOOLBAR_GONE).setStatusBarVisibility(
                    WebStrategyType.STATUSBAR_GONE));
        }
    }

    public void onItemClick(Activity activity, String uri) {
        if (onClickSepportInterface != null) {
            onClickSepportInterface.onCLickNfc(activity, uri);
        }
    }

    public void onItemClick(String pamars) {
        if (onClickLobby != null) {
            onClickLobby.onClick(pamars);
        }
    }

    public static void onItemClick(final Context context, String onclick, String onclickPamars) {
        if (TextUtils.isEmpty(onclick)) {
            ToastUtils.toastMsg(context.getResources().getString(R.string.not_realized_msg));
            return;
        }
        if (onclick.contains(ConfigUtils.NFC_PATH)) {
            if (UtilsOnClick.performClick()) {
                SearchOnclicSupport.getInstance().onItemClick((Activity) context, onclick.replace(ConfigUtils.EVENT_PREFIX, ""));
            }
            return;
        }
        if (onclick.contains(Event.GOTO_PHONE_FEES)) {
            LoginSuccessActionUtils.getInstance()
                    .checkLoginWithCallBack(context, new ICallBack() {
                        @Override
                        public void callBack() {
//                            PaServiceManager.getInstance().getPafManager().startPaf((Activity) context, PafManager.FLAG_PAY_PHONE);
                            //                            IPayService iPayService = PayJumper.getIpayService();
                            //                            if (iPayService != null) {
                            //                                iPayService.startPafPhone((Activity) context, UserManager.getInstance().getUserId(), UserManager.getInstance().getMobileNo());
                            //                            }
                        }
                    });
        } else if (onclick.contains(Event.GOTO_DATA_FEES)) {
            LoginSuccessActionUtils.getInstance()
                    .checkLoginWithCallBack(context, new ICallBack() {
                        @Override
                        public void callBack() {
//                            PaServiceManager.getInstance().getPafManager().startPaf((Activity) context, PafManager.FLAG_PAY_FLOW);
                            //                            IPayService iPayService = PayJumper.getIpayService();
                            //                            if (iPayService != null) {
                            //                                iPayService.startPafFlow((Activity) context, UserManager
                            //                                        .getInstance().getUserId(), UserManager.getInstance()
                            //                                        .getMobileNo());
                            //                            }
                        }
                    });
        } else if (onclick.contains(Event.GOTO_OIL_FEES)) {
            LoginSuccessActionUtils.getInstance()
                    .checkLoginWithCallBack(context, new ICallBack() {
                        @Override
                        public void callBack() {
//                            PaServiceManager.getInstance().getPafManager().startPaf((Activity) context, PafManager.FLAG_PAY_OIL);
                            //                            IPayService iPayService = PayJumper.getIpayService();
                            //                            if (iPayService != null) {
                            //                                iPayService.startPafOil((Activity) context, UserManager
                            //                                        .getInstance().getUserId(), UserManager.getInstance()
                            //                                        .getMobileNo());
                            //                            }
                        }
                    });
        } else if (onclick.contains(Event.GOTO_CDSS_SERVICE)) {
            LoginSuccessActionUtils.getInstance()
                    .checkLoginWithCallBack(context, new ICallBack() {
                        @Override
                        public void callBack() {
                            //HomepageJumper.jumpCdssActivity();
                            ToastUtils.toastMsg("jumpCdssActivity");
                        }
                    });
        } else if (onclick.contains(Event.GOTO_ELECTRICITY_FEES)) {
            LoginSuccessActionUtils.getInstance()
                    .checkLoginWithCallBack(context, new ICallBack() {
                        @Override
                        public void callBack() {
//                            PaServiceManager.getInstance().getPafManager().startPaf((Activity) context, PafManager.FLAG_PAY_WATER);
                            //                            IPayService iPayService = PayJumper.getIpayService();
                            //                            if (iPayService != null) {
                            //                                iPayService.startPafWater((Activity) context, UserManager
                            //                                        .getInstance().getUserId(), UserManager.getInstance()
                            //                                        .getMobileNo());
                            //                            }
                        }
                    });
        } else if (onclick.contains(Event.GOTO_ONE_WARN)) {
            //HomepageJumper.jumpEmrgencyActivity();
            ToastUtils.toastMsg("GOTO_ONE_WARN");

            return;
        }
//        if (TextUtils.isEmpty(onclickPamars)) {
//            BaseJumper.jumpARouter(onclick);
//            return;
//        }
        if (onclick.startsWith("router://com.pasc.smt/hybrid/webView/")) {
            if (onclick.startsWith("router://com.pasc.smt/hybrid/webView/noToolbar")) {
                PascHybrid.getInstance().start(context, new WebStrategy().setUrl(onclickPamars).setOldIntercept(
                    WebStrategyType.OLD_INTERCEPT_ON)
                        .setToolBarVisibility(WebStrategyType.TOOLBAR_GONE).setStatusBarVisibility(
                        WebStrategyType.STATUSBAR_GONE));
//                        .setStatusBarColor(mBlue_4b89dc));
            } else if (onclick.startsWith("router://com.pasc.smt/hybrid/webView")) {
                if ("http://www.jscsfc.com/zjwxsite/webservice/FacilitiesRepairReport".equals(onclickPamars)) {

                    PascHybrid.getInstance().start(context, new WebStrategy().setUrl(onclickPamars)
                            .setToolBarVisibility(WebStrategyType.TOOLBAR_VISIBLE).setOldIntercept(
                            WebStrategyType.OLD_INTERCEPT_ON)
                            .setTitle("市政设施报修")
                            .setStatusBarVisibility(WebStrategyType.STATUSBAR_VISIBLE));
                } else {

                    PascHybrid.getInstance().start(context, new WebStrategy().setUrl(onclickPamars)
                            .setToolBarVisibility(WebStrategyType.TOOLBAR_VISIBLE).setOldIntercept(
                            WebStrategyType.OLD_INTERCEPT_ON)
                            .setStatusBarVisibility(WebStrategyType.STATUSBAR_VISIBLE));
                }
            }

        } else if (onclick.startsWith("router://scenery")) {

            PascHybrid.getInstance().start(context, new WebStrategy().setUrl(handleUrlPlaceholder(onclickPamars))
                    .setToolBarVisibility(WebStrategyType.TOOLBAR_GONE).setStatusBarVisibility(
                    WebStrategyType.STATUSBAR_GONE).setOldIntercept(WebStrategyType.OLD_INTERCEPT_ON)
                    .setStatusBarColor(mBlue_4b89dc));
        } else if (onclick.startsWith("event:")) {
            defaultClickEvent(context, onclick);
        } else {
//            Log.e(TAG, "onItemClick: onclick "+onclick);
//            if (onclick.contains(".map/map/main")){
//                onclick = onclick.replace(".map", "/map");
//                Log.e(TAG, "onItemClick: onclick2 "+onclick);
//                onclick = onclick + "?index=bank";
//                Log.e(TAG, "onItemClick: onclick3 "+onclick);
//            }
            LinkedHashMap<String, String> params = parseParams(onclick, onclickPamars);
            Bundle bundle=new Bundle();
            for(Map.Entry<String, String> m: params.entrySet()){
                bundle.putString(m.getKey(),m.getValue());
                //Log.e(TAG, "onItemClick: m.getKey() -> "+m.getKey()+"; m.getValue() -> "+m.getValue());
            }

            BaseJumper.jumpBundleARouter(onclick, bundle);
        }

    }

    /**
     * 点击事件，触发事件协议.
     */
    public static void defaultClickEvent(Context context, String url) {
        if (TextUtils.isEmpty(url)){
            return;
        }

        Object event = buildEvent(context, url);
        if (event == null){
            return;
        }


        //        EventBus.getDefault().post(event);
    }

    /**
     * 根据协议地址构建一个事件对象.
     *
     * @param url 协议地址.
     * @return 事件对象.
     */
    public static Object buildEvent(final Context context, String url) {
        String prefix = ConfigUtils.EVENT_PREFIX;
        if (url.contains(prefix + Event.GOTO_PHONE_FEES)) {
            LoginSuccessActionUtils.getInstance()
                    .checkLoginWithCallBack(context, new ICallBack() {
                        @Override
                        public void callBack() {
//                            PaServiceManager.getInstance().getPafManager().startPaf((Activity) context, PafManager.FLAG_PAY_PHONE);
                            //                            IPayService iPayService = PayJumper.getIpayService();
                            //                            if (iPayService != null) {
                            //                                iPayService.startPafPhone((Activity) context, UserManager.getInstance().getUserId(), UserManager.getInstance().getMobileNo());
                            //                            }
                        }
                    });
        } else if (url.startsWith(prefix + Event.GOTO_ELECTRICITY_FEES)) {
            LoginSuccessActionUtils.getInstance()
                    .checkLoginWithCallBack(context, new ICallBack() {
                        @Override
                        public void callBack() {
//                            PaServiceManager.getInstance().getPafManager().startPaf((Activity) context, PafManager.FLAG_PAY_WATER);
                            //                            IPayService iPayService = PayJumper.getIpayService();
                            //                            if (iPayService != null) {
                            //                                iPayService.startPafWater((Activity) context, UserManager.getInstance().getUserId(), UserManager.getInstance().getMobileNo());
                            //                            }
                        }
                    });
        } else if (url.startsWith(prefix + Event.GOTO_LOGIN)) {
            LoginSuccessActionUtils.getInstance()
                    .checkLoginWithCallBack(context, new ICallBack() {
                        @Override
                        public void callBack() {
                        }
                    });
        } else if (url.startsWith(prefix + Event.GOTO_DATA_FEES)) {
            LoginSuccessActionUtils.getInstance()
                    .checkLoginWithCallBack(context, new ICallBack() {
                        @Override
                        public void callBack() {
//                            PaServiceManager.getInstance().getPafManager().startPaf((Activity) context, PafManager.FLAG_PAY_FLOW);
                            //                            IPayService iPayService = PayJumper.getIpayService();
                            //                            if (iPayService != null) {
                            //                                iPayService.startPafFlow((Activity) context, UserManager.getInstance().getUserId(), UserManager.getInstance().getMobileNo());
                            //                            }
                        }
                    });
        } else if (url.startsWith(prefix + Event.GOTO_OIL_FEES)) {
            LoginSuccessActionUtils.getInstance()
                    .checkLoginWithCallBack(context, new ICallBack() {
                        @Override
                        public void callBack() {
//                            PaServiceManager.getInstance().getPafManager().startPaf((Activity) context, PafManager.FLAG_PAY_OIL);
                            //                            IPayService iPayService = PayJumper.getIpayService();
                            //                            if (iPayService != null) {
                            //                                iPayService.startPafOil((Activity) context, UserManager.getInstance().getUserId(), UserManager.getInstance().getMobileNo());
                            //                            }
                        }
                    });
        }
        return null;
    }

    public static String handleUrlPlaceholder(String url) {
        if (url.contains("{apiHost}")) {
            url = url.replace("{apiHost}", UrlManager.API_HOST);
        }
        if (url.contains("{h5Host}")) {
            url = url.replace("{h5Host}", H5UrlManager.H5_HOST);
        }
        if (url.contains("{token}")) {
            url = url.replace("{token}", AppProxy.getInstance().getUserManager().getToken());
        }
        return url;
    }

    public static void handleParamsUrlPlaceholder(LinkedHashMap<String, String> params) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            if ("url".equals(key)) {
                String value = entry.getValue();
                value = handleUrlPlaceholder(value);
                params.put(key, value);
            }
        }
    }

    /**
     * 从url和json对象分析参数.
     *
     * @param url url地址.
     * @return 参数列表集合.
     */
    @NonNull
    public static LinkedHashMap<String, String> parseParams(@Nullable String url, @Nullable
        String paramsJsonObjects) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        if (!TextUtils.isEmpty(url)){
            //url参数
            LinkedHashMap<String, String> urlParams =  ConfigUtils.parseParamsByRegex(url);
            params.putAll(urlParams);
        }
        if (!TextUtils.isEmpty(paramsJsonObjects)){
            JSONObject paramsJsonObject = null;
            try {
                paramsJsonObject = new JSONObject(paramsJsonObjects);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // params参数
            LinkedHashMap<String, String> jsonParams = parseParamsFromJsonObject(paramsJsonObject);
            params.putAll(jsonParams);
        }
        return params;
    }

    /**
     * 从jsonObject分析参数.
     *
     * @param params 参数对象.
     * @return 参数列表.
     */
    public static LinkedHashMap<String, String> parseParamsFromJsonObject(JSONObject params) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        if (params != null) {
            // 所有参数都统一为字符串，兼容性更好
            Iterator<String> keys = params.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = params.optString(key);
                map.put(key, value);
            }
        }
        return map;
    }
}
