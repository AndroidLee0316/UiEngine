package com.pasc.business.mine.config;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.Gson;
import com.pasc.business.mine.util.AssetsUtil;
import com.pasc.lib.base.permission.PermissionUtils;

/**
 * 将URL分发给认证, 登录和人脸
 */
public class MineUrlDispatcher {
  public static void dispatch(Context context, String jsonPath) {
    if (TextUtils.isEmpty(jsonPath)) {
      throw new NullPointerException("请传入正确的serviceConfigPath");
    }
    try {
      MineConfigManager
          mineUrlConfig =
          new Gson().fromJson(AssetsUtil.parseFromAssets(context, jsonPath), MineConfigManager.class);
      SettingManager.getInstance().init(mineUrlConfig.settingConfigBean);
      ProfileManager.getInstance().init(mineUrlConfig.profileConfigBean);
      AboutManager.getInstance().init(mineUrlConfig.aboutConfigBean);
      AddressManager.getInstance().init(mineUrlConfig.defaultAddrBean);
    } catch (Exception e) {
      Log.v("MineUrlDispatcher", "MineUrlDispatcher.dispatch" + e.getMessage());
    }
  }
}
