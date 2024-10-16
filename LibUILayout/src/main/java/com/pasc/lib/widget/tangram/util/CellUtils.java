package com.pasc.lib.widget.tangram.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import android.view.ViewGroup;
import com.pasc.lib.widget.tangram.BasePascCell;
import com.pasc.lib.widget.tangram.attr.StyleAttr;
import com.pasc.lib.widget.tangram.model.DataSourceItem;
import com.tmall.wireless.tangram.MVHelper;
import com.tmall.wireless.tangram.TangramEngine;
import com.tmall.wireless.tangram.core.service.ServiceManager;
import com.tmall.wireless.tangram.dataparser.concrete.BaseCellBinderResolver;
import com.tmall.wireless.tangram.dataparser.concrete.Style;
import com.tmall.wireless.tangram.eventbus.BusSupport;
import com.tmall.wireless.tangram.eventbus.Event;
import com.tmall.wireless.tangram.eventbus.EventContext;
import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.util.TangramViewMetrics;

import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class CellUtils {

  private static final String RP = "rp";

  /**
   * 从Json对象获取字符串数组。
   *
   * @param jsonArray json数组对象。
   * @return 字符串数组。
   */
  public static String[] getStringArrFromJson(JSONArray jsonArray) {
    if (jsonArray == null) return null;
    int length = jsonArray.length();
    if (length <= 0) return null;
    String[] strings = new String[length];
    for (int i = 0; i < length; i++) {
      try {
        strings[i] = jsonArray.getString(i);
      } catch (JSONException e) {
        e.printStackTrace();
        strings[i] = "";
      }
    }
    return strings;
  }

  public static Activity getActivity(View targetView, BaseCell cell) {
    Activity activity = null;
    if (targetView != null) {
      Context targetViewContext = targetView.getContext();
      if (targetViewContext instanceof Activity) {
        activity = (Activity) targetViewContext;
      }
    }
    ServiceManager serviceManager = cell.serviceManager;
    if (serviceManager != null) {
      if (serviceManager instanceof TangramEngine) {
        TangramEngine engine = (TangramEngine) serviceManager;
        RecyclerView contentView = engine.getContentView();
        if (contentView != null) {
          Context contentViewContext = contentView.getContext();
          if (contentViewContext instanceof Activity) {
            activity = (Activity) contentViewContext;
          }
        }
      }
    }
    return activity;
  }

  public static int[] getPaddingFromJson(JSONObject data, String fieldName) {
    int[] padding = new int[4];
    JSONArray paddingArray = data.optJSONArray(fieldName);
    if (paddingArray != null) {
      int size = Math.min(padding.length, paddingArray.length());
      for (int i = 0; i < size; i++) {
        padding[i] = parseSize(paddingArray.optString(i), 0);
      }

      if (size > 0) {
        Arrays.fill(padding, size, padding.length, padding[size - 1]);
      }
    } else {
      String paddingString = data.optString(fieldName);
      if (!TextUtils.isEmpty(paddingString)) {
        padding = getPaddingFromString(paddingString);
      }
    }
    return padding;
  }

  private static int[] getPaddingFromString(@Nullable String paddingString) {
    int[] padding = new int[4];
    if (!TextUtils.isEmpty(paddingString)) {
      try {
        paddingString = paddingString.trim().substring(1, paddingString.length() - 1);
        String paddingStringArray[] = paddingString.split(",");
        int size = paddingStringArray.length > 4 ? 4 : paddingStringArray.length;
        for (int i = 0; i < size; i++) {
          String paddingStr = paddingStringArray[i];
          try {
            if (!TextUtils.isEmpty(paddingStr)) {
              padding[i] = parseSize(paddingStr.trim().replace("\"", ""), 0);
            } else {
              padding[i] = 0;
            }
          } catch (NumberFormatException ignored) {
            padding[i] = 0;
          }
        }
        Arrays.fill(padding, size, padding.length, padding[size - 1]);
      } catch (Exception e) {
        Arrays.fill(padding, 0);
      }
    }
    return padding;
  }

  private static int parseSize(String sourceValue, int defaultValue) {
    int result;
    if (sourceValue != null && sourceValue.length() > 0) {
      sourceValue = sourceValue.trim();
      if (sourceValue.endsWith(RP)) {
        sourceValue = sourceValue.substring(0, sourceValue.length() - 2).trim();
        try {
          double number = Double.parseDouble(sourceValue);
          result = rp2px(number);
        } catch (NumberFormatException e) {
          result = defaultValue;
        }
      } else {
        try {
          double number = Double.parseDouble(sourceValue);
          result = dp2px(number);
        } catch (NumberFormatException e) {
          result = defaultValue;
        }
      }
    } else {
      result = defaultValue;
    }
    return result;
  }

  private static int rp2px(double rpValue) {
    return (int) ((rpValue * TangramViewMetrics.screenWidth()) / TangramViewMetrics.uedScreenWidth()
        + 0.5f);
  }

  public static int dp2px(double dpValue) {
    float scaleRatio = TangramViewMetrics.screenDensity();
    final float scale = scaleRatio < 0 ? 1.0f : scaleRatio;

    int finalValue;
    if (dpValue >= 0) {
      finalValue = (int) (dpValue * scale + 0.5f);
    } else {
      finalValue = -((int) (-dpValue * scale + 0.5f));
    }
    return finalValue;
  }

  public static void postEvent(BaseCell cell, String eventId, ArrayMap<String, String> args) {
    EventContext eventContext = new EventContext();
    eventContext.producer = cell;
    ServiceManager serviceManager = cell.serviceManager;
    if (serviceManager != null) {
      if (serviceManager instanceof TangramEngine) {
        eventContext.tangramCore = (TangramEngine) serviceManager;
      }
    }
    Event event =
        BusSupport.obtainEvent(cell.stringType + "-" + eventId, cell.id, args, eventContext);
    serviceManager.getService(BusSupport.class).post(event);
  }

  public static void bindView(List<BaseCell> cells, View view) {
    if (cells != null) {
      int size = cells.size();
      if (size > 0) {
        for (int i = 0; i < size; i++) {
          BaseCell cell = cells.get(i);
          cell.bindView(view);
        }
      }
    }
  }

  public static void bindView(List<BaseCell> cells, View view, DataSourceItem data) {
    if (cells != null) {
      int size = cells.size();
      if (size > 0) {
        for (int i = 0; i < size; i++) {
          BaseCell cell = cells.get(i);
          if (cell instanceof BasePascCell) {
            ((BasePascCell) cell).setDataSource(data);
          }
          cell.bindView(view);
        }
      }
    }
  }

  public static void bindView(List<BaseCell> cells, List<? extends View> views) {
    if (cells == null) return;
    if (views == null) return;
    int size = cells.size();
    int sizeView = views.size();
    size = Math.min(size, sizeView);
    for (int i = 0; i < size; i++) {
      BaseCell cell = cells.get(i);
      View view = views.get(i);
      // 保证显示
      view.setVisibility(View.VISIBLE);
      cell.bindView(view);
    }
    int count = sizeView - size;
    for (int i = 0; i < count; i++) {
      int index = sizeView - 1 - i;
      // 超出的视图隐藏掉
      views.get(index).setVisibility(View.GONE);
    }
  }

  public static List<BaseCell> parseWith(JSONObject data, MVHelper resolver,
      ServiceManager serviceManager, String itemsName) {
    JSONArray items = data.optJSONArray(itemsName);
    if (items != null) {
      List<BaseCell> cells = ((TangramEngine) serviceManager).parseComponent(items);
      for (BaseCell cell : cells) {
        cell.parseWith(cell.extras, resolver);
      }
      return cells;
    }
    return null;
  }

  public static View createView(Context context, ViewGroup parent, ServiceManager serviceManager,
      BaseCell cell, Map<BaseCell, View> viewMap) {
    if (context == null
        || serviceManager == null
        || cell == null
        || parent == null || viewMap == null) {
      return null;
    }
    View childView = viewMap.get(cell);
    if (childView == null) {
      childView = serviceManager.getService(BaseCellBinderResolver.class)
          .create(cell.stringType)
          .createView(context, parent);
      viewMap.put(cell, childView);
    }

    return childView;
  }

  public static void bindStyle(Style style, StyleAttr styleAttr) {
    if (style == null) {
      return;
    }
    if (styleAttr == null) {
      return;
    }
    styleAttr.setBgColor(style.bgColor);
    styleAttr.setBgImgUrl(style.bgImgUrl);
    styleAttr.setMargin(style.margin);
    styleAttr.setPadding(style.padding);

    JSONObject extras = style.extras;
    int width = extras == null ? 0 : extras.optInt("width");
    if (width >= 0) {
      width = style.width;
    }
    styleAttr.setWidth(width);
    int height = extras == null ? 0 : extras.optInt("height");
    if (height >= 0) {
      height = style.height;
    }
    styleAttr.setHeight(height);
  }

  public static <T extends BaseCell> T getFirstCell(List<? extends BaseCell> cells) {
    if (cells != null && cells.size() > 0) {
      return (T) cells.get(0);
    }
    return null;
  }
}
