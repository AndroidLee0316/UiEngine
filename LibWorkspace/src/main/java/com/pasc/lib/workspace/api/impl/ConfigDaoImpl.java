package com.pasc.lib.workspace.api.impl;

import android.content.Context;
import android.text.TextUtils;

import com.pasc.lib.net.ApiGenerator;
import com.pasc.lib.net.param.BaseParam;
import com.pasc.lib.net.resp.BaseResp;
import com.pasc.lib.workspace.UserProxy;
import com.pasc.lib.workspace.api.ConfigApi;
import com.pasc.lib.workspace.api.ConfigDao;
import com.pasc.lib.workspace.api.ConfigDaoParams;
import com.pasc.lib.workspace.bean.ConfigItem;
import com.pasc.lib.workspace.bean.ConfigItemParam;
import com.pasc.lib.workspace.bean.ConfigParamNew;
import com.pasc.lib.workspace.bean.ConfigResp;
import com.pasc.lib.workspace.bean.ConfigUserInfo;
import com.pasc.lib.workspace.util.BizUtils;
import com.pasc.lib.workspace.util.CommonUtils;
import com.pasc.lib.workspace.util.ConfigUtils;

import retrofit2.Call;

public class ConfigDaoImpl implements ConfigDao {

    private final Context context;

    public ConfigDaoImpl(Context context) {
        this.context = context;
    }

    @Override
    public ConfigResp getConfig(ConfigDaoParams daoParams) {
        String configId = daoParams.getConfigId();
        String testFlag = daoParams.getTestFlag();
        String url = daoParams.getUrl();
        String configVersion = daoParams.getConfigVersion();
        ConfigItem configFromCache = ConfigUtils.getConfigFromCache(context, configId);
        if (configFromCache != null) {
            configVersion = configFromCache.configVersion;
        }
        ConfigItemParam configItemParam = new ConfigItemParam(configId, configVersion);
        int appVersionCode = CommonUtils.getAppVersionCode(context);
        String appVersion = CommonUtils.convertVersionCode(appVersionCode);
        String phoneNumber = UserProxy.getInstance().getPhoneNum();
        String packageName = context.getPackageName();
        ConfigParamNew configParamNew = ConfigParamNew.builder()
                .configItem(configItemParam)
                .appId(packageName)
                .appVersion(appVersion)
                .testFlag(testFlag)
                .configUserInfo(ConfigUserInfo.builder().phoneNumber(phoneNumber).build())
                .build();
        BaseParam<ConfigParamNew> param =
                new BaseParam<>(configParamNew);

        ConfigApi api = ApiGenerator.createApi(ConfigApi.class);
        Call<BaseResp<ConfigResp>> call;
        if (!TextUtils.isEmpty(url)) {
            // 如果url不为空，则按传入的url进行查询
            call = api.queryServiceConfigSync(param, url);
        } else {
            call = api.queryServiceConfigSync(param);
        }
        ConfigResp configResp = BizUtils.getNetData(call);
        return configResp;
    }
}
