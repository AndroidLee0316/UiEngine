//package com.pasc.pascuiengine.impl;
//
//import android.content.Context;
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.pasc.business.workspace.api.BannerApi;
//import com.pasc.business.workspace.api.BannerParam;
//import com.pasc.lib.base.util.AppUtils;
//import com.pasc.lib.net.ApiGenerator;
//import com.pasc.lib.workspace.api.BannerDao;
//import com.pasc.lib.workspace.api.BannerDaoParams;
//import com.pasc.lib.workspace.bean.AppBannerRsp;
//import com.pasc.lib.workspace.bean.BannerBean;
//import com.pasc.lib.workspace.util.BizUtils;
//import com.pasc.lib.workspace.util.CommonUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TBannerDaoImpl implements BannerDao {
//
//    private static final String TAG = "BannerDao";
//    private final Context context;
//
//    public TBannerDaoImpl(Context context) {
//        this.context = context;
//    }
//
//    @Override
//    public AppBannerRsp getBanner(BannerDaoParams bannerDaoParams) {
//        String cellId = bannerDaoParams.getCellId();
//        String token = bannerDaoParams.getToken();
//        boolean cutOut = bannerDaoParams.isCutOut();
//        Log.d(TAG, "是否取刘海图片：" + cutOut);
//
//        int appVersionCode = CommonUtils.getAppVersionCode(context);
//        BannerParam bannerParam = new BannerParam();
//        if ("bannerSecondary".equals(cellId)) {
//            bannerParam.setBannerLocation(1);
//        } else {
//            bannerParam.setBannerLocation(0);
//        }
//        bannerParam.setShowType(1);
//        bannerParam.setVersionCode(appVersionCode);
//        String versionNo = AppUtils.getVersionName(context);
//        bannerParam.setVersionNo(versionNo);
//        List<BannerBean> bannerBeans = BizUtils.getNetData(ApiGenerator.createApi(BannerApi.class).getBannerSync(bannerParam));
//        AppBannerRsp appBannerRsp = new AppBannerRsp();
//        ArrayList<BannerBean> bannerModels = new ArrayList<>();
//        for (BannerBean item : bannerBeans) {
//            com.pasc.lib.workspace.bean.BannerBean bannerBean = new com.pasc.lib.workspace.bean.BannerBean();
//
//            String picUrl = item.getPicUrl();
//            if (cutOut) {
//                String specialUrl = item.getSpecialUrl();
//                if (!TextUtils.isEmpty(specialUrl)) {
//                    picUrl = specialUrl;
//                }
//            }
//
//            bannerBean.setPicUrl(picUrl);
//            bannerBean.setPicSkipUrl(item.getPicSkipUrl());
//            bannerBean.setPicName(item.getBannerName());
//            bannerModels.add(bannerBean);
//        }
//        appBannerRsp.bannerBeans = bannerModels;
//        return appBannerRsp;
//    }
//}
