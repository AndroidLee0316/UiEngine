package com.pasc.business.affair.router;

import android.content.Context;
import android.os.Bundle;
import com.alibaba.android.arouter.launcher.ARouter;
import com.pasc.business.affair.net.H5UrlManager;
import com.pasc.lib.hybrid.PascHybrid;
import com.pasc.lib.hybrid.nativeability.WebStrategy;
import com.pasc.lib.hybrid.nativeability.WebStrategyType;

/**
 * 功能：路由跳转统一管理类
 * <p>
 * created by zoujianbo345
 * data : 2018/9/10
 */
public class ARouterUtil {

    /**
     *办事局
     */
    public static final int TYPE_DEPARTMENT= 1001;
    /**
     *政务办事大厅
     */
    public static final int TYPE_AFFAIR_LOBBY= 1002;
    /**
     *　市民心声建议
     */
    public static final int TYPE_CITISENS= 1003;
    /**
     *　政务新闻
     */
    public static final int TYPE_AFFAIR_NEWS= 1004;


    /**
     * 简单路由跳转
     * @param url
     */
    public static void startActivity(String url){

        ARouter.getInstance().build(url).navigation();
    }

    /**
     * 简单路由跳转
     * @param url
     */
    public static void startActivity(String url,int type){

        ARouter.getInstance().build(url).withInt("type",type).navigation();
    }

    /**
     * 带bundle 参数路由跳转
     * @param url
     * @param bundle
     *
     */
    public static void startActivity(String url, Bundle bundle){

    }


    /**
     *
     * 默认打开H5链接
     * @param context
     * @param aroutType
     * @param pamars
     */
    public static void startH5(Context context,int aroutType,String pamars){
        startH5(context,aroutType,pamars,true,"#ffffff");
    }
    /**
     *
     * 默认打开H5链接
     * @param context
     * @param aroutType
     * @param pamars
     */
    public static void startH5(Context context,int aroutType,String pamars,boolean inNeedBar){
        startH5(context,aroutType,pamars,inNeedBar,"#ffffff");
    }

    /**
     *
     * 默认打开H5链接

     */
    public static void startH5(Context context,String url){
        PascHybrid.getInstance().start(context, new WebStrategy().setUrl(url)
                .setToolBarVisibility(WebStrategyType.TOOLBAR_GONE)
                .setStatusBarColor("#4b89dc"));
    }

    /**
     * 带参数打开H5链接
     * @param context
     * @param aroutType
     * @param pamars
     * @param isNeedBar
     * @param color
     */
    public static void startH5(Context context,int aroutType,String pamars,boolean isNeedBar,String color){
        String arouts = "";
        switch (aroutType){
            case TYPE_DEPARTMENT:
                arouts = "app/feature/department/#/list/";
                break;
            case TYPE_AFFAIR_LOBBY:
                arouts = "app/feature/citizens-voice/#/list/";
                break;
            case TYPE_CITISENS:
                arouts = "citizens-voice/#/list/";
                break;
            case TYPE_AFFAIR_NEWS:
                arouts ="app/feature/news/#/detail/";
                break;
        }
//        WebPageConfig webPageConfig = new WebPageConfig.Builder()
//                .addCustomerBehavior(ConstantBehaviorName.WEB_BEHAVIOR_PREVIEW_IMAGES, new PreviewPhotoBehavior())
//                .addCustomerBehavior(ConstantBehaviorName.WEB_BEHAVIOR_BROWSE_FILE, new BrowseFileBehavior())
//                //.setBackIconRes()
//                .create();
        //WebPageConfig webPageConfig = new WebPageConfig.Builder().addExtUserAgent
        //        ("/openweb=paschybrid/CHANGSHUSMT_Android,VERSION:1.1.0").create();
        if (isNeedBar){
            PascHybrid.getInstance().start(context,new WebStrategy().setUrl(
                H5UrlManager.H5_HOST+arouts+pamars)
                    .setToolBarVisibility(WebStrategyType.TOOLBAR_VISIBLE).setOldIntercept(
                    WebStrategyType.OLD_INTERCEPT_ON)
                    .setStatusBarColor(color));
        }else {
            PascHybrid.getInstance().start(context,new WebStrategy().setUrl(H5UrlManager.H5_HOST+arouts+pamars)
                    .setToolBarVisibility(WebStrategyType.TOOLBAR_GONE).setOldIntercept(
                    WebStrategyType.OLD_INTERCEPT_ON)
                    .setStatusBarColor(color));
        }
    }
}
