package com.pasc.business.mine.config;

/**
 * 关于统一管理
 */
public class AddressManager {
  private static volatile AddressManager instance;
  private MineConfigManager.DefaultAddrBean defaultAddrBean;

  private AddressManager() {

  }

  public static AddressManager getInstance() {
    if (instance == null) {
      synchronized (AddressManager.class) {
        if (instance == null) {
          instance = new AddressManager();
        }
      }
    }
    return instance;
  }

  public void init(MineConfigManager.DefaultAddrBean defaultAddrBean) {
    this.defaultAddrBean = defaultAddrBean;
  }

  public String getProvinceName() {
    return defaultAddrBean == null ? "" : defaultAddrBean.provinceName;
  }

  public String getCityName() {
    return defaultAddrBean == null ? "" : defaultAddrBean.cityName;
  }

  public String getCountryName() {
    return defaultAddrBean == null ? "" : defaultAddrBean.countryName;
  }
}
