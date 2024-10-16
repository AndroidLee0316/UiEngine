package com.pasc.lib.widget.tangram.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.pasc.lib.widget.tangram.model.DataSourceItem;
import com.tmall.wireless.tangram.structure.BaseCell;

public class DataUtils {

  public static void setTextData(TextView textView,
      DataSourceItem data, String textKey) {
    if (textView == null || data == null || TextUtils.isEmpty(textKey)) {
      return;
    }
    String text = DataUtils.getValueFromData(data, textKey, String.class);
    if (text != null) {
      textView.setText(text);
    }
  }

  public static void setImageData(BaseCell cell, ImageView imageView, DataSourceItem data,
      String imgKeyPrefix) {
    if (cell == null || imageView == null || data == null || TextUtils.isEmpty(imgKeyPrefix)) {
      return;
    }

    Context context = imageView.getContext();
    Boolean iconVisible =
        DataUtils.getValueFromData(data, imgKeyPrefix + "Visible", Boolean.class);
    if (iconVisible != null) {
      int iconVisibility = iconVisible ? View.VISIBLE : View.GONE;
      imageView.setVisibility(iconVisibility);
    }

    String authIconUrl =
        DataUtils.getImgUrlFromData(context, data, imgKeyPrefix + "Url");
    if (!TextUtils.isEmpty(authIconUrl)) {
      cell.doLoadImageUrl(imageView, authIconUrl);
    }
  }

  public static Integer getIntValueFromData(DataSourceItem data, String name) {
    if (data == null || name == null) {
      return null;
    }
    Object obj = data.optValue(name);
    if (obj instanceof Integer) {
      return (Integer) obj;
    }
    return null;
  }

  public static <T> T getValueFromData(DataSourceItem data, String name, Class<T> clazz) {
    if (data == null || name == null || clazz == null) {
      return null;
    }
    Object obj = data.optValue(name);
    if (obj != null) {
      if (obj.getClass().equals(clazz)) {
        return (T) obj;
      }
    }
    return null;
  }

  public static String getImgUrlFromData(Context context, DataSourceItem data, String key) {
    if (context == null || data == null || key == null) {
      return null;
    }

    String imgUrl = getValueFromData(data, key, String.class);
    if (TextUtils.isEmpty(imgUrl)) {
      Integer imgRes = getValueFromData(data, key, Integer.class);
      if (imgRes != null) {
        imgUrl = DataUtils.getImgUrl(context, imgRes);
      }
    }
    if (TextUtils.isEmpty(imgUrl)) {
      return null;
    }
    return imgUrl;
  }

  public static String getImgUrl(Context context, @DrawableRes int res) {
    if (context == null) {
      return null;
    }
    Resources resources = context.getResources();
    String resourceTypeName = resources.getResourceTypeName(res);
    if ("drawable".equalsIgnoreCase(resourceTypeName) || "mipmap".equalsIgnoreCase(
        resourceTypeName)) {
      String resourceEntryName = resources.getResourceEntryName(res);
      return "res://" + resourceEntryName;
    }
    return null;
  }

  public static void setTextColor(TextView textView,
      DataSourceItem data, String color) {
    if (textView == null || data == null) {
      return;
    }
    String textColor = DataUtils.getValueFromData(data, color, String.class);

    if (TextUtils.isEmpty(textColor)) {
      return;
    }
    textView.setTextColor(Color.parseColor(textColor));
  }
}
