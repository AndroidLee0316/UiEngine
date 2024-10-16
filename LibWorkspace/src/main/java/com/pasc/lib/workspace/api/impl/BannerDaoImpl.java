package com.pasc.lib.workspace.api.impl;

import android.content.Context;

import com.pasc.lib.net.ApiGenerator;
import com.pasc.lib.net.param.BaseParam;
import com.pasc.lib.workspace.api.BannerApi;
import com.pasc.lib.workspace.api.BannerDao;
import com.pasc.lib.workspace.api.BannerDaoParams;
import com.pasc.lib.workspace.bean.AppBannerRsp;
import com.pasc.lib.workspace.bean.BannerParam;
import com.pasc.lib.workspace.util.BizUtils;
import com.pasc.lib.workspace.util.CommonUtils;

public class BannerDaoImpl implements BannerDao {

    private final Context context;

    public BannerDaoImpl(Context context) {
        this.context = context;
    }

    @Override
    public AppBannerRsp getBanner(BannerDaoParams params) {
        String cellId = params.getCellId();
        String token = params.getToken();
        String entry = BannerParam.ENTRY_SY;
        if ("banner".equals(cellId)) {
            entry = BannerParam.ENTRY_SY; // 南通的接口
        }
        BannerParam param = new BannerParam(entry);
        param.setToken(token);
        int appVersionCode = CommonUtils.getAppVersionCode(context);
        String appVersion = CommonUtils.convertVersionCode(appVersionCode);
        param.versionCode = appVersionCode;
        param.appVersion = appVersion;
        AppBannerRsp netData = BizUtils.getNetData(ApiGenerator.createApi(BannerApi.class).getBannerSync(new BaseParam<>(param)));
        return netData;
    }
}
