package com.pasc.business.mine.config;

/**
 * 人脸相关URL统一管理
 */
public class ProfileManager {
  private static volatile ProfileManager instance;
  private MineConfigManager.ProfileConfigBean profileConfigBean;

  private ProfileManager() {

  }

  public static ProfileManager getInstance() {
    if (instance == null) {
      synchronized (ProfileManager.class) {
        if (instance == null) {
          instance = new ProfileManager();
        }
      }
    }
    return instance;
  }

  public void init(MineConfigManager.ProfileConfigBean profileConfigBean) {
    this.profileConfigBean = profileConfigBean;
  }

  public boolean showAddress() {
    return profileConfigBean == null || profileConfigBean.address;
  }

  public boolean showCertification() {
    return profileConfigBean == null || profileConfigBean.certification;
  }

  public boolean showMobile() {
    return profileConfigBean == null || profileConfigBean.mobile;
  }

  public boolean showPortrait() {
    return profileConfigBean == null || profileConfigBean.portrait;
  }

  public boolean showNickname() {
    return profileConfigBean == null || profileConfigBean.nickname;
  }

  public boolean showGender() {
    return profileConfigBean == null || profileConfigBean.gender;
  }

  public boolean showIdentity() {
    return profileConfigBean == null || profileConfigBean.identity;
  }

  public boolean showExtend() {
    return profileConfigBean != null && profileConfigBean.extendInfo != null;
  }

  public MineConfigManager.ProfileConfigBean getProfileConfigBean() {
    return profileConfigBean;
  }
}
