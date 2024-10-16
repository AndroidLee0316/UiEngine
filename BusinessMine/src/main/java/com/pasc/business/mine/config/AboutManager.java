package com.pasc.business.mine.config;

import android.text.TextUtils;
import com.pasc.lib.base.AppProxy;

/**
 * 关于统一管理
 */
public class AboutManager {
  private static volatile AboutManager instance;
  private MineConfigManager.AboutConfigBean aboutConfigBean;

  private AboutManager() {

  }

  public static AboutManager getInstance() {
    if (instance == null) {
      synchronized (AboutManager.class) {
        if (instance == null) {
          instance = new AboutManager();
        }
      }
    }
    return instance;
  }

  public void init(MineConfigManager.AboutConfigBean aboutConfigBean) {
    this.aboutConfigBean = aboutConfigBean;
  }

  public boolean showVersion() {
    return aboutConfigBean == null || aboutConfigBean.version;
  }

  public boolean showProtocol() {
    return aboutConfigBean != null && !TextUtils.isEmpty(aboutConfigBean.protocolUrl);
  }

  public boolean showCopyright() {
    return aboutConfigBean == null || aboutConfigBean.copyright;
  }

  public String getProtocolUrl() {
    return aboutConfigBean == null ? ""
        : aboutConfigBean.protocolUrl.startsWith("http") ? aboutConfigBean.protocolUrl
            : AppProxy.getInstance().getH5Host() + aboutConfigBean.protocolUrl;
  }

  public String getPrivatePolicy() {
    return aboutConfigBean == null ? ""
        : aboutConfigBean.privatePolicy.startsWith("http") ? aboutConfigBean.privatePolicy
            : AppProxy.getInstance().getH5Host() + aboutConfigBean.privatePolicy;
  }
}
