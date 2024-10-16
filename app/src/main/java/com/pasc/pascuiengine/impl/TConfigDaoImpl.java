//package com.pasc.pascuiengine.impl;
//
//import android.content.Context;
//import android.text.TextUtils;
//
//import com.pasc.business.workspace.api.ConfigApi;
//import com.pasc.business.workspace.api.ConfigApiResult;
//import com.pasc.business.workspace.api.ConfigBean;
//import com.pasc.business.workspace.api.ConfigParam;
//import com.pasc.business.workspace.api.UserInfo;
//import com.pasc.lib.base.AppProxy;
//import com.pasc.lib.base.user.IUserInfo;
//import com.pasc.lib.base.user.IUserManager;
//import com.pasc.lib.base.util.AppUtils;
//import com.pasc.lib.net.ApiGenerator;
//import com.pasc.lib.net.resp.BaseResp;
//import com.pasc.lib.workspace.api.ConfigDao;
//import com.pasc.lib.workspace.api.ConfigDaoParams;
//import com.pasc.lib.workspace.bean.ConfigItem;
//import com.pasc.lib.workspace.bean.ConfigResp;
//import com.pasc.lib.workspace.util.BizUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//
//public class TConfigDaoImpl implements ConfigDao {
//
//    private final Context context;
//
//    public TConfigDaoImpl(Context context) {
//        this.context = context;
//    }
//
//    @Override
//    public ConfigResp getConfig(ConfigDaoParams configDaoParams) {
//        String configId = configDaoParams.getConfigId();
//        String configVersion = configDaoParams.getConfigVersion();
//        if (TextUtils.isEmpty(configVersion)) {
//            configVersion = "0.0";
//        }
//
//        String testFlag = configDaoParams.getTestFlag();
//        String url = configDaoParams.getUrl();
//
//        String versionName = AppUtils.getVersionName();
//        ConfigParam configParam = new ConfigParam();
//        configParam.setAppVersion(versionName); // 如1.0.0
//
//        ArrayList<com.pasc.business.workspace.api.ConfigItem> configItems = new ArrayList<>();
//        com.pasc.business.workspace.api.ConfigItem item = new com.pasc.business.workspace.api.ConfigItem();
//        item.setConfigId(configId);
//        item.setConfigVersion(configVersion);
//        configItems.add(item);
//        configParam.setConfigItems(configItems);
//
//        configParam.setTestFlag("1"); // 暂时写死是测试环境
//
//        configParam.setAppId(null);
//
//        UserInfo userInfo = new UserInfo();
//        IUserManager userManager = AppProxy.getInstance().getUserManager();
//        if (userManager != null) {
//            IUserInfo userInfoProxy = userManager.getUserInfo();
//            if (userInfoProxy != null) {
//                String userName = userInfoProxy.getUserName();
//                String mobileNo = userInfoProxy.getMobileNo();
//                userInfo.setPhoneNumber(mobileNo);
//                userInfo.setUserName(userName);
//            }
//        }
//        configParam.setUserInfo(userInfo);
//
//        ConfigApi api = ApiGenerator.createApi(ConfigApi.class);
//        Call<BaseResp<ConfigApiResult>> call = api.getConfigSync(configParam);
//        ConfigApiResult configApiResult = BizUtils.getNetData(call);
//        List<ConfigBean> configBeans = configApiResult.getList();
//
//        if (configBeans != null && configBeans.size() > 0) {
//            ConfigBean configBean = configBeans.get(0);
//
//            ConfigResp configResp = new ConfigResp();
//            configResp.list = new ArrayList<>();
//            ConfigItem configItem = new ConfigItem();
//            configItem.configVersion = configBean.getConfigVersion();
//            configItem.chineseName = configBean.getChineseName();
//            configItem.appVersion = configBean.getAppVersion();
//            configItem.configId = configBean.getConfigId();
//            configItem.configContent = configBean.getConfigContent();
//            configResp.list.add(configItem);
//
//            return configResp;
//        }
//
//        return null;
//    }
//}
