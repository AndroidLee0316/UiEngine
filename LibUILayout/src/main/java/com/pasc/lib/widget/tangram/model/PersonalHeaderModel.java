package com.pasc.lib.widget.tangram.model;

import android.text.TextUtils;

public class PersonalHeaderModel implements DataSourceItem {

  private String personName;
  private String authTitle;
  private String scoreDesc;
  private String imgUrl;
  private boolean authIconVisible;
  private boolean authArrowIconVisible;
  private int imgRes;

  public String getPersonName() {
    return personName;
  }

  public void setPersonName(String personName) {
    this.personName = personName;
  }

  public String getAuthTitle() {
    return authTitle;
  }

  public void setAuthTitle(String authTitle) {
    this.authTitle = authTitle;
  }

  public String getScoreDesc() {
    return scoreDesc;
  }

  public void setScoreDesc(String scoreDesc) {
    this.scoreDesc = scoreDesc;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public boolean isAuthIconVisible() {
    return authIconVisible;
  }

  public void setAuthIconVisible(boolean authIconVisible) {
    this.authIconVisible = authIconVisible;
  }

  public boolean isAuthArrowIconVisible() {
    return authArrowIconVisible;
  }

  public void setAuthArrowIconVisible(boolean authArrowIconVisible) {
    this.authArrowIconVisible = authArrowIconVisible;
  }

  public int getImgRes() {
    return imgRes;
  }

  public void setImgRes(int imgRes) {
    this.imgRes = imgRes;
  }

  @Override public Object optValue(String name) {
    if ("personName".equals(name)) {
      return personName;
    } else if ("authTitle".equals(name)) {
      return authTitle;
    } else if ("authIconVisible".equals(name)) {
      return authIconVisible;
    } else if ("authArrowIconVisible".equals(name)) {
      return authArrowIconVisible;
    } else if ("scoreDesc".equals(name)) {
      return scoreDesc;
    } else if ("imgUrl".equals(name)) {
      if (TextUtils.isEmpty(imgUrl)) {
        return imgRes;
      } else {
        return imgUrl;
      }
    }
    return null;
  }
}
