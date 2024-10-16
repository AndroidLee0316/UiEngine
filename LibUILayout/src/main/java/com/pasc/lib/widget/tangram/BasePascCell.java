package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.pasc.lib.widget.tangram.attr.StyleAttr;
import com.pasc.lib.widget.tangram.model.DataSourceItem;
import com.pasc.lib.widget.tangram.model.JsonItem;
import com.pasc.lib.widget.tangram.util.AndroidUtils;
import com.pasc.lib.widget.tangram.util.CellUtils;
import com.pasc.lib.widget.tangram.util.ConfigUtils;
import com.pasc.lib.widget.tangram.util.JsonUtils;
import com.pasc.lib.widget.toast.Toasty;
import com.tmall.wireless.tangram.MVHelper;
import com.tmall.wireless.tangram.structure.BaseCell;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BasePascCell<V extends View> extends BaseCell<V> {

  private static final String TAG = "LibWidgetTangram";

  private Object dataSource; // 数据源。如果是从json配置里获取的，则是List<JsonItem>

  // 组件是否显示
  protected boolean visible = true;
  private StyleAttr styleAttr;

  public Object getDataSource() {
    return dataSource;
  }

  public List getListDataSource() {
    if (dataSource instanceof List) {
      return (List) dataSource;
    }
    return null;
  }

  protected DataSourceItem getDataSourceItem() {
    if (dataSource != null) {
      if (dataSource instanceof List) {
        List list = (List) this.dataSource;
        if (list.size() > 0) {
          Object obj = list.get(0);
          if (obj instanceof DataSourceItem) {
            return (DataSourceItem) obj;
          }
        }
      } else if (dataSource instanceof DataSourceItem) {
        return (DataSourceItem) dataSource;
      }
    }
    return null;
  }

  private JSONObject defaultData; // 默认的数据

  public void setDataSource(Object dataSource) {
    this.dataSource = dataSource;
  }

  public BasePascCell() {
    super();
    initCell();
  }

  public BasePascCell(String stringType) {
    super(stringType);
    initCell();
  }

  protected void initCell() {

  }

  @Override public void parseStyle(@Nullable JSONObject data) {
    super.parseStyle(data);
    CellUtils.bindStyle(style, getStyleAttr());
  }

  @Override
  public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
    super.parseWith(data, resolver);
    if (data == null) return;

    if (isDefaultDataEnable()) {
      mergeDefaultData(data, resolver);
    }

    visible = getBoolean(data, "visible", true);

    // 设置数据源
    parseDataSource(data); // 约定是数组
  }

  protected boolean isDefaultDataEnable() {
    return false;
  }

  protected JSONObject mergeDefaultData(JSONObject data, MVHelper resolver) {
    Context context = resolver.getVafContext().getContext();
    String key = getDefaultDataKey();
    DefaultDataManager defaultDataManager = DefaultDataManager.getInstance();
    JSONObject defaultData = defaultDataManager.getDefaultData(context, key);
    setDefaultData(defaultData);
    return JsonUtils.mergeJSONObject(defaultData, data);
  }

  protected String getDefaultDataKey() {
    return stringType;
  }

  public JSONObject getDefaultData() {
    return defaultData;
  }

  public void setDefaultData(JSONObject defaultData) {
    this.defaultData = defaultData;
  }

  /**
   * 数据源类型，可以复写此方法，这样从json配置获取的数据会自动转换成此类型。
   *
   * @return 数据源类型。
   */
  protected Class getDataSourceType() {
    return null;
  }

  /**
   * 分析数据类型。
   *
   * @param data json数据对象
   */
  protected void parseDataSource(@NonNull JSONObject data) {
    JSONArray dataSource = data.optJSONArray("dataSource");
    if (dataSource == null) {
      return;
    }
    Class dataSourceType = getDataSourceType();
    if (dataSourceType == null) {
      ArrayList<JsonItem> jsonItems = new ArrayList<JsonItem>();
      int length = dataSource.length();
      for (int i = 0; i < length; i++) {
        JSONObject jsonObject = dataSource.optJSONObject(i);
        JsonItem jsonItem = new JsonItem();
        jsonItem.setJsonObject(jsonObject);
        jsonItems.add(jsonItem);
      }
      setDataSource(jsonItems);
    } else {
      // 此方法已不建议使用
      ArrayList<Object> objects = new ArrayList<>();
      int length = dataSource.length();
      for (int i = 0; i < length; i++) {
        try {
          String dataString = dataSource.get(i).toString();
          Object object = new Gson().fromJson(dataString, dataSourceType);
          objects.add(object);
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
      setDataSource(objects);
    }
  }

  protected void bindViewStyle(@NonNull V view) {
    if (view == null) return;

    // 设置背景颜色
    setViewBackground(view);

    // 设置padding
    StyleAttr styleAttr = getStyleAttr();
    int[] padding = styleAttr.getPadding();
    view.setPadding(padding[3], padding[0], padding[1], padding[2]);

    // 设置margin
    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

    if (layoutParams == null) return;

    // 设置宽高
    layoutParams.width = styleAttr.getWidth();
    layoutParams.height = styleAttr.getHeight();

    if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) return;

    // 设置margin
    int[] margin = styleAttr.getMargin();
    ((ViewGroup.MarginLayoutParams) layoutParams).setMargins(margin[3], margin[0], margin[1],
        margin[2]);
  }

  /**
   * 获取默认的背景图片资源。
   * 建议使用配置的方式设置默认背景。
   */
  @Deprecated
  protected Integer getDefaultBgImgRes() {
    return null;
  }

  // 设置视图的背景
  protected void setViewBackground(V view) {
    if (style == null) return;
    if (view == null) return;

    Integer bgImgRes = getDefaultBgImgRes();
    StyleAttr styleAttr = getStyleAttr();
    String resUrl = styleAttr.getBgImgUrl();
    Integer drawable = ConfigUtils.getDrawableFromUrl(view.getContext(), resUrl);
    if (drawable != null) {
      bgImgRes = drawable;
    }
    if (bgImgRes == null) {
      view.setBackgroundColor(styleAttr.getBgColor());
    } else {
      view.setBackgroundResource(bgImgRes);
    }
  }

  public StyleAttr getStyleAttr() {
    if (styleAttr == null) {
      styleAttr = new StyleAttr();
    }
    return styleAttr;
  }

  @Override
  public void bindView(@NonNull V view) {
    super.bindView(view);
    if (!visible) {
      view.getLayoutParams().height = 0;
      view.setVisibility(View.GONE);
      return;
    } else {
      view.setVisibility(View.VISIBLE);

      bindViewStyle(view);
      bindViewClickListener(view);
      bindViewData(view);
    }
  }

  protected void bindViewData(V view) {

  }

  protected void bindViewClickListener(View view) {
    // 增加点击事件
    String url = getString(extras, "onClick");
    Integer eventType = ConfigUtils.parseEventType(url);

    if (eventType == null) return;

    setOnClickListener(view, eventType);
  }

  protected void setDefaultImageTag(ImageView view, @DrawableRes int drawableId) {
    view.setTag(R.integer.tag_key_default_image, drawableId);
  }

  @Override public void onClick(View v) {
    if (AndroidUtils.isValidClick(v)) {
      super.onClick(v);
    }
  }

  protected int buildDefaultRes(ImageView view) {
    Context context = view.getContext();
    int drawableId = R.drawable.ic_img_error_44;
    int layoutWidth = view.getLayoutParams().width;
    int width = view.getWidth();
    int measuredWidth = view.getMeasuredWidth();
    int maxWidth = view.getMaxWidth();

    Integer imgLoadWidth = null;
    if (layoutWidth > 0) {
      imgLoadWidth = layoutWidth;
    }
    if (imgLoadWidth == null) {
      if (width > 0) {
        imgLoadWidth = width;
      }
    }
    if (imgLoadWidth == null) {
      if (measuredWidth > 0) {
        imgLoadWidth = measuredWidth;
      }
    }
    if (imgLoadWidth == null) {
      if (maxWidth > 0) {
        imgLoadWidth = maxWidth;
      }
    }

    if (imgLoadWidth != null) {
      if (imgLoadWidth <= AndroidUtils.dip2px(context, 16)) {
        drawableId = R.drawable.ic_img_error_16;
      } else if (imgLoadWidth <= AndroidUtils.dip2px(context, 22)) {
        drawableId = R.drawable.ic_img_error_22;
      } else if (imgLoadWidth <= AndroidUtils.dip2px(context, 36)) {
        drawableId = R.drawable.ic_img_error_36;
      } else if (imgLoadWidth <= AndroidUtils.dip2px(context, 42)) {
        drawableId = R.drawable.ic_img_error_42;
      } else if (imgLoadWidth <= AndroidUtils.dip2px(context, 44)) {
        drawableId = R.drawable.ic_img_error_44;
      } else {
        drawableId = R.drawable.img_error_banner;
      }
    }

    return drawableId;
  }

  protected void setDefaultImageTag(ImageView view, String prefix) {
    Context context = view.getContext();
    String defaultImageName = prefix + "Default";
    String defaultImg = getString(extras, defaultImageName);
    Integer drawableId = ConfigUtils.getDrawableFromUrl(context, defaultImg);
    if (drawableId == null) {
      int layoutWidth = view.getLayoutParams().width;
      int width = view.getWidth();
      int measuredWidth = view.getMeasuredWidth();
      int maxWidth = view.getMaxWidth();

      Integer imgLoadWidth = null;
      if (layoutWidth > 0) {
        imgLoadWidth = layoutWidth;
      }
      if (imgLoadWidth == null) {
        if (width > 0) {
          imgLoadWidth = width;
        }
      }
      if (imgLoadWidth == null) {
        if (measuredWidth > 0) {
          imgLoadWidth = measuredWidth;
        }
      }
      if (imgLoadWidth == null) {
        if (maxWidth > 0) {
          imgLoadWidth = maxWidth;
        }
      }

      if (imgLoadWidth != null) {
        if (imgLoadWidth <= AndroidUtils.dip2px(context, 16)) {
          drawableId = R.drawable.ic_img_error_16;
        } else if (imgLoadWidth <= AndroidUtils.dip2px(context, 22)) {
          drawableId = R.drawable.ic_img_error_22;
        } else if (imgLoadWidth <= AndroidUtils.dip2px(context, 36)) {
          drawableId = R.drawable.ic_img_error_36;
        } else if (imgLoadWidth <= AndroidUtils.dip2px(context, 42)) {
          drawableId = R.drawable.ic_img_error_42;
        } else if (imgLoadWidth <= AndroidUtils.dip2px(context, 44)) {
          drawableId = R.drawable.ic_img_error_44;
        } else {
          drawableId = R.drawable.img_error_banner;
        }
      }
    }

    if (TextUtils.isEmpty(defaultImg)) {
      drawableId = R.drawable.ic_img_error_44;
    }

    setDefaultImageTag(view, drawableId);
  }

  /**
   * 获取颜色值.
   *
   * @param name 字段名
   * @return 颜色值.
   */
  protected int getColor(String name, int fallback) {
    if (TextUtils.isEmpty(name)) {
      return fallback;
    }

    Integer color = getColor(name);
    if (color == null) {
      return fallback;
    }
    return color;
  }

  /**
   * 获取颜色值.
   *
   * @param name 字段名
   * @return 颜色值.
   */
  protected Integer getColor(String name) {
    return getColor(name, null);
  }

  protected Integer getColor(String name, JSONObject jsonObject) {
    if (TextUtils.isEmpty(name)) {
      return null;
    }

    Integer color = null;
    String colorStr = null;
    if (jsonObject == null) {
      colorStr = optStringParam(name);
    } else {
      colorStr = getString(jsonObject, name);
    }
    if (!TextUtils.isEmpty(colorStr)) {
      try {
        color = Color.parseColor(colorStr);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return color;
  }

  /**
   * 获取字符串.
   *
   * @param name 字段名
   * @return 字符串值.
   */
  protected String getString(JSONObject jsonObject, String name) {
    return ConfigUtils.getString(jsonObject, name);
  }

  protected String getString(JSONObject jsonObject, String name, String defaultValue) {
    return ConfigUtils.getString(jsonObject, name, defaultValue);
  }

  /**
   * 获取整型值.
   *
   * @param name 字段名
   * @return 整型值.
   */
  protected int getInt(JSONObject jsonObject, String name) {
    return ConfigUtils.getInt(jsonObject, name);
  }

  /**
   * 获取浮点值.
   *
   * @param jsonObject json对象.
   * @param name 字段名.
   * @return 浮点值.
   */
  protected float getFloat(JSONObject jsonObject, String name, float defaultValue) {
    return ConfigUtils.getFloat(jsonObject, name, defaultValue);
  }

  /**
   * 获取整型值.
   *
   * @param name 字段名
   * @return 整型值.
   */
  protected double getDouble(JSONObject jsonObject, String name) {
    return ConfigUtils.getDouble(jsonObject, name);
  }

  protected boolean getBoolean(JSONObject jsonObject, String name) {
    return ConfigUtils.getBoolean(jsonObject, name);
  }

  protected boolean getBoolean(JSONObject jsonObject, String name, boolean defaultValue) {
    return ConfigUtils.getBoolean(jsonObject, name, defaultValue);
  }

  // 获取资源参数
  protected int optResParam(Context context, String key, int defaultRes) {
    String resUrl = optStringParam(key);
    if (!TextUtils.isEmpty(resUrl)) {
      Integer drawable = ConfigUtils.getDrawableFromUrl(context, resUrl);
      if (drawable != null) {
        return drawable;
      }
    }
    return defaultRes;
  }

  public boolean isVisible() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public void setStyle(JSONObject style) {
    if (style != null) {

    }
  }
}
