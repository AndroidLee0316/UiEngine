package com.pasc.lib.widget.tangram.attr;

import com.pasc.lib.widget.tangram.util.CellUtils;
import com.pasc.lib.widget.tangram.util.ConfigUtils;

import com.pasc.lib.widget.tangram.util.StringUtils;
import com.tmall.wireless.tangram.dataparser.concrete.Cell;
import org.json.JSONObject;

public class ImgAttr {

  private boolean oval;
  private String scaleTypeName;

  private ImgAttr() {
  }

  private String url; // 图片地址
  private int width; // 宽,-1是match_parent,-2是wrap_parent
  private int height; // 高,-1是match_parent,-2是wrap_parent
  private boolean visible; // 是否显示
  private Integer defaultResBuildIn; // 内置的默认图片，可以为空
  private String defaultResFromConfig; // 从配置获取的默认图片
  private int[] margin; // 周边边距
  private float widthRatio; // 宽占比
  private float heightRatio; // 高占比
  private int ratioRef; // 比例参照

  public void setOval(boolean oval) {
    this.oval = oval;
  }

  public void setScaleTypeName(String scaleTypeName) {
    this.scaleTypeName = scaleTypeName;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public void setDefaultResBuildIn(Integer defaultResBuildIn) {
    this.defaultResBuildIn = defaultResBuildIn;
  }

  public void setDefaultResFromConfig(String defaultResFromConfig) {
    this.defaultResFromConfig = defaultResFromConfig;
  }

  public void setMargin(int[] margin) {
    this.margin = margin;
  }

  public void setWidthRatio(float widthRatio) {
    this.widthRatio = widthRatio;
  }

  public void setHeightRatio(float heightRatio) {
    this.heightRatio = heightRatio;
  }

  public void setRatioRef(int ratioRef) {
    this.ratioRef = ratioRef;
  }

  public Integer getRatioRef() {
    return ratioRef;
  }

  public Float getWidthRatio() {
    return widthRatio;
  }

  public Float getHeightRatio() {
    return heightRatio;
  }

  public int[] getMargin() {
    return margin;
  }

  public String getUrl() {
    return url;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public boolean isVisible() {
    return visible;
  }

  public Integer getDefaultResBuildIn() {
    return defaultResBuildIn;
  }

  public String getDefaultResFromConfig() {
    return defaultResFromConfig;
  }

  public boolean isOval() {
    return oval;
  }

  public String getScaleTypeName() {
    return scaleTypeName;
  }

  public static class Builder {

    private final JSONObject jsonData;
    private String fieldName;
    private double widthDefault;
    private double heightDefault;
    private boolean visibleDefault;
    private Integer defaultRes;
    private int[] defaultMargin = new int[] { 0, 0, 0, 0 };
    private float widthRatioDefault = 1;
    private float heightRatioDefault = 1;
    private int ratioRefDefault = 0;
    private boolean ovalDefault = false;
    private String scaleTypeDefault = "FIT_XY";

    public Builder setOvalDefault(boolean ovalDefault) {
      this.ovalDefault = ovalDefault;
      return this;
    }

    public Builder setScaleTypeDefault(String scaleTypeDefault) {
      this.scaleTypeDefault = scaleTypeDefault;
      return this;
    }

    public Builder setWidthRatioDefault(float widthRatioDefault) {
      this.widthRatioDefault = widthRatioDefault;
      return this;
    }

    public Builder setHeightRatioDefault(float heightRatioDefault) {
      this.heightRatioDefault = heightRatioDefault;
      return this;
    }

    public Builder setRatioRefDefault(int ratioRefDefault) {
      this.ratioRefDefault = ratioRefDefault;
      return this;
    }

    public Builder setDefaultMargin(int[] defaultMargin) {
      this.defaultMargin = defaultMargin;
      return this;
    }

    public Builder setWidthDefault(double widthDefault) {
      this.widthDefault = widthDefault;
      return this;
    }

    public Builder setHeightDefault(double heightDefault) {
      this.heightDefault = heightDefault;
      return this;
    }

    public Builder setVisibleDefault(boolean visibleDefault) {
      this.visibleDefault = visibleDefault;
      return this;
    }

    public Builder setDefaultRes(Integer defaultRes) {
      this.defaultRes = defaultRes;
      return this;
    }

    public Builder(JSONObject jsonData, String fieldName) {
      this.jsonData = jsonData;
      this.fieldName = fieldName;
    }

    public ImgAttr build() {
      ImgAttr imgAttr = new ImgAttr();
      String visibleName = fieldName + "Visible";
      visibleName = StringUtils.lowerFirstLetter(visibleName);
      imgAttr.visible = ConfigUtils.getBoolean(jsonData, visibleName, visibleDefault);

      String widthName = fieldName + "Width";
      widthName = StringUtils.lowerFirstLetter(widthName);
      double widthDouble = ConfigUtils.getDouble(jsonData, widthName, widthDefault);
      if (widthDouble > 0d) {
        imgAttr.width = CellUtils.dp2px(widthDouble);
      } else if (widthDouble > -2d) {
        imgAttr.width = -1;
      } else {
        imgAttr.width = -2;
      }

      String heightName = fieldName + "Height";
      heightName = StringUtils.lowerFirstLetter(heightName);
      double heightDouble = ConfigUtils.getDouble(jsonData, heightName, heightDefault);
      if (heightDouble > 0d) {
        imgAttr.height = CellUtils.dp2px(heightDouble);
      } else if (heightDouble > -2d) {
        imgAttr.height = -1;
      } else {
        imgAttr.height = -2;
      }

      String defaultResName = fieldName + "Default";
      defaultResName = StringUtils.lowerFirstLetter(defaultResName);
      imgAttr.defaultResFromConfig = ConfigUtils.getString(jsonData, defaultResName);

      imgAttr.defaultResBuildIn = defaultRes;

      String urlName = fieldName + "Url";
      urlName = StringUtils.lowerFirstLetter(urlName);
      imgAttr.url = ConfigUtils.getString(jsonData, urlName);

      String marginName = fieldName + "Margin";
      marginName = StringUtils.lowerFirstLetter(marginName);
      if (jsonData.has(marginName)) {
        imgAttr.margin = CellUtils.getPaddingFromJson(jsonData, marginName);
      } else {
        imgAttr.margin = defaultMargin;
      }

      String widthRatioName = fieldName + "WidthRatio";
      widthRatioName = StringUtils.lowerFirstLetter(widthRatioName);
      imgAttr.widthRatio = ConfigUtils.getFloat(jsonData, widthRatioName, widthRatioDefault);

      String heightRatioName = fieldName + "HeightRatio";
      heightRatioName = StringUtils.lowerFirstLetter(heightRatioName);
      imgAttr.heightRatio = ConfigUtils.getFloat(jsonData, heightRatioName, heightRatioDefault);

      String ratioRefName = fieldName + "RatioRef";
      ratioRefName = StringUtils.lowerFirstLetter(ratioRefName);
      imgAttr.ratioRef = ConfigUtils.getInt(jsonData, ratioRefName, ratioRefDefault);

      String ovalName = fieldName + "Oval";
      ovalName = StringUtils.lowerFirstLetter(ovalName);
      imgAttr.oval = ConfigUtils.getBoolean(jsonData, ovalName, ovalDefault);

      String scaleTypeName = fieldName + "ScaleType";
      scaleTypeName = StringUtils.lowerFirstLetter(scaleTypeName);
      imgAttr.scaleTypeName = ConfigUtils.getString(jsonData, scaleTypeName, scaleTypeDefault);

      return imgAttr;
    }
  }
}
