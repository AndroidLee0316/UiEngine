package com.pasc.lib.widget.tangram.attr;

import android.graphics.Color;
import android.util.TypedValue;

import com.pasc.lib.widget.tangram.util.ConfigUtils;

import org.json.JSONObject;

public class TextAttr {

  private TextAttr() {
  }

  private boolean visible = true;
  private String content; // 内容
  private int fontSize; // 字体大小
  private int fontSizeUnit; // 字体大小单位
  private int color; // 颜色
  private boolean bold; // 是否加粗

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setFontSize(int fontSize) {
    this.fontSize = fontSize;
  }

  public void setFontSizeUnit(int fontSizeUnit) {
    this.fontSizeUnit = fontSizeUnit;
  }

  public void setColor(int color) {
    this.color = color;
  }

  public void setBold(boolean bold) {
    this.bold = bold;
  }

  public boolean isVisible() {
    return visible;
  }

  public String getContent() {
    return content;
  }

  public int getFontSize() {
    return fontSize;
  }

  public int getFontSizeUnit() {
    return fontSizeUnit;
  }

  public int getColor() {
    return color;
  }

  public boolean isBold() {
    return bold;
  }

  public static class Builder {

    private final JSONObject jsonData;
    private final String fieldName;
    private int defaultFontSize = 16;
    private int defaultFontSizeUnit = TypedValue.COMPLEX_UNIT_DIP;
    private int defaultColor = Color.BLACK;
    private boolean defaultBold;

    public Builder setDefaultFontSizeUnit(int defaultFontSizeUnit) {
      this.defaultFontSizeUnit = defaultFontSizeUnit;
      return this;
    }

    public Builder setDefaultFontSize(int defaultFontSize) {
      this.defaultFontSize = defaultFontSize;
      return this;
    }

    public Builder setDefaultColor(int defaultColor) {
      this.defaultColor = defaultColor;
      return this;
    }

    public Builder setDefaultBold(boolean defaultBold) {
      this.defaultBold = defaultBold;
      return this;
    }

    public Builder(JSONObject jsonData, String fieldName) {
      this.jsonData = jsonData;
      this.fieldName = fieldName;
    }

    public TextAttr build() {
      TextAttr attr = new TextAttr();

      attr.content = ConfigUtils.getString(jsonData, fieldName);
      attr.color = ConfigUtils.getColor(jsonData, fieldName + "Color", defaultColor);
      int fontSize = ConfigUtils.getInt(jsonData, fieldName + "FontSize");
      if (fontSize > 0) {
        attr.fontSize = fontSize;
      } else {
        attr.fontSize = defaultFontSize;
      }
      attr.bold = ConfigUtils.getBoolean(jsonData, fieldName + "Bold", defaultBold);
      attr.visible = ConfigUtils.getBoolean(jsonData, fieldName + "Visible", true);
      attr.fontSizeUnit = defaultFontSizeUnit;

      return attr;
    }
  }
}
