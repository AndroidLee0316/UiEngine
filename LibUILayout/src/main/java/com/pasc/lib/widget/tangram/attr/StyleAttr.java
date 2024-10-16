package com.pasc.lib.widget.tangram.attr;

import android.view.ViewGroup;

public class StyleAttr {
  private int[] margin = new int[] { 0, 0, 0, 0 };
  private int[] padding = new int[] { 0, 0, 0, 0 };
  private int width = ViewGroup.LayoutParams.MATCH_PARENT;
  private int height = ViewGroup.LayoutParams.WRAP_CONTENT;
  private int bgColor;
  private String bgImgUrl;

  public int[] getMargin() {
    return margin;
  }

  public void setMargin(int[] margin) {
    this.margin = margin;
  }

  public int[] getPadding() {
    return padding;
  }

  public void setPadding(int[] padding) {
    this.padding = padding;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public int getBgColor() {
    return bgColor;
  }

  public void setBgColor(int bgColor) {
    this.bgColor = bgColor;
  }

  public String getBgImgUrl() {
    return bgImgUrl;
  }

  public void setBgImgUrl(String bgImgUrl) {
    this.bgImgUrl = bgImgUrl;
  }
}
