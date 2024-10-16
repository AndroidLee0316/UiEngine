package com.pasc.business.mine.config;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MineConfigManager {
  /** 设置 */
  @SerializedName("settingConfig")
  public SettingConfigBean settingConfigBean;
  /** 用户资料 */
  @SerializedName("profileConfig")
  public ProfileConfigBean profileConfigBean;
  /** 关于 */
  @SerializedName("aboutConfig")
  public AboutConfigBean aboutConfigBean;
  /** 地址管理中 */
  @SerializedName("defaultAddr")
  public DefaultAddrBean defaultAddrBean;
  @SerializedName("permissions")
  public List<String> permissions;

  public static class SettingConfigBean {
    @SerializedName("profile")
    public boolean profile;
    @SerializedName("security")
    public boolean security;
    @SerializedName("pwdReset")
    public boolean pwdReset;
    @SerializedName("feedback")
    public boolean feedback;
    @SerializedName("clearCache")
    public boolean clearCache;
    @SerializedName("about")
    public boolean about;
    @SerializedName("unregister")
    public boolean unregister;
    @SerializedName("permissions")
    public List<String> permissions;
  }

  public static class ProfileConfigBean {
    @SerializedName("portrait")
    public boolean portrait;
    @SerializedName("mobile")
    public boolean mobile;
    @SerializedName("nickname")
    public boolean nickname;
    @SerializedName("gender")
    public boolean gender;
    @SerializedName("certification")
    public boolean certification;
    @SerializedName("identity")
    public boolean identity;
    @SerializedName("address")
    public boolean address;
    @SerializedName("extend")
    public ExtendInfo extendInfo;
  }

  public static class ExtendInfo {
    @SerializedName("title")
    public String title;
    @SerializedName("router")
    public String router;
    @SerializedName("needCert")
    public boolean needCert;
  }

  public static class AboutConfigBean {
    @SerializedName("version")
    public boolean version;
    @SerializedName("protocol")
    public boolean protocol;
    @SerializedName("copyright")
    public boolean copyright;
    @SerializedName("protocolUrl")
    public String protocolUrl;
    @SerializedName("privatePolicy")
    public String privatePolicy;
  }

  public static class DefaultAddrBean {
    @SerializedName("provinceName")
    public String provinceName;
    @SerializedName("cityName")
    public String cityName;
    @SerializedName("countryName")
    public String countryName;
  }
}
