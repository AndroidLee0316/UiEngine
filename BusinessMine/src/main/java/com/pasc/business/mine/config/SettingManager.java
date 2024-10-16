package com.pasc.business.mine.config;

import com.pasc.lib.base.AppProxy;
import com.pasc.lib.base.util.CollectionUtils;

/**
 * 认证相关url统一管理
 */
public class SettingManager {
  private static volatile SettingManager instance;
  private MineConfigManager.SettingConfigBean settingConfigBean;

  private SettingManager() {

  }

  public static SettingManager getInstance() {
    if (instance == null) {
      synchronized (SettingManager.class) {
        if (instance == null) {
          instance = new SettingManager();
        }
      }
    }
    return instance;
  }

  public void init(MineConfigManager.SettingConfigBean settingConfigBean) {
    this.settingConfigBean = settingConfigBean;
  }

  public boolean showProfile() {
    return settingConfigBean == null || settingConfigBean.profile;
  }

  public boolean showSecurity() {
    return settingConfigBean == null || settingConfigBean.security;
  }

  public boolean showAbout() {
    return settingConfigBean == null || settingConfigBean.about;
  }

  public boolean showClearCache() {
    return settingConfigBean == null || settingConfigBean.clearCache;
  }

  public boolean showFeedback() {
    return settingConfigBean == null || settingConfigBean.feedback;
  }

  public boolean showPwdReset() {
    return settingConfigBean == null || settingConfigBean.pwdReset;
  }

  public boolean showUnregister() {
    return settingConfigBean == null || settingConfigBean.unregister;
  }

  public boolean showPermission() {
    return settingConfigBean != null && !CollectionUtils.isEmpty(settingConfigBean.permissions);
  }

  public String getPermission(int i) {
    if (settingConfigBean == null || CollectionUtils.isEmpty(settingConfigBean.permissions)) {
      return null;
    }
    if (settingConfigBean.permissions.get(i) == null && settingConfigBean.permissions.get(i)
        .startsWith("http")) {
      return settingConfigBean.permissions.get(i);
    }
    return AppProxy.getInstance().getH5Host() + settingConfigBean.permissions.get(i);
  }
}
