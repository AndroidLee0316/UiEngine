package com.pasc.lib.widget.tangram;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import com.pasc.lib.widget.tangram.attr.ImgAttr;
import com.pasc.lib.widget.tangram.attr.TextAttr;
import com.pasc.lib.widget.tangram.util.CellUtils;
import com.tmall.wireless.tangram.MVHelper;
import org.json.JSONObject;

public class IconTwoTextCell extends BaseCardCell<IconTwoTextView> {

  public static final String TITLE = "title";
  public static final String DESC = "desc";
  public static final String ICON = "icon";
  public static final String TEXT_LAYOUT_PADDING = "textLayoutPadding";
  public static final String ICON_VISIBLE = "iconVisible";

  private boolean iconVisible = true; //图标是否显示
  private int[] textLayoutPadding; // 文本布局padding
  private int[] iconMargin; // 图标布局margin
  private int[] labelMargin; // 标签布局margin
  private String iconPosition;
  private String textLayoutGravity;
  private boolean labelVisible;
  private String labelType;
  private String label;
  private TextAttr titleAttr;
  private TextAttr descAttr;
  private ImgAttr iconAttr;
  private int lineGap;

  @Override protected boolean isDefaultDataEnable() {
    return true;
  }

  @Override
  public boolean isValid() {
    return super.isValid();
  }

  @Override
  public void postBindView(@NonNull IconTwoTextView view) {
    super.postBindView(view);
  }

  @Override
  public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
    super.parseWith(data, resolver);

    iconVisible = hasParam(ICON_VISIBLE) ? optBoolParam(ICON_VISIBLE) : true;
    textLayoutPadding = CellUtils.getPaddingFromJson(data, TEXT_LAYOUT_PADDING);

    iconPosition = optStringParam("iconPosition");
    iconMargin = CellUtils.getPaddingFromJson(data, "iconMargin");

    textLayoutGravity = optStringParam("textLayoutGravity");

    labelVisible = getBoolean(data, "labelVisible", false);
    labelType = getString(data, "labelType", "dot");
    label = getString(data, "label", "");
    labelMargin = CellUtils.getPaddingFromJson(data, "labelMargin");

    iconAttr = new ImgAttr.Builder(data, ICON)
        .setWidthDefault(42)
        .setHeightDefault(42)
        .setVisibleDefault(true)
        .build();

    titleAttr = new TextAttr.Builder(data, TITLE)
        .setDefaultFontSize(14)
        .setDefaultFontSizeUnit(TypedValue.COMPLEX_UNIT_DIP)
        .setDefaultColor(Color.parseColor("#333333"))
        .setDefaultBold(false)
        .build();

    descAttr = new TextAttr.Builder(data, DESC)
        .setDefaultFontSize(12)
        .setDefaultFontSizeUnit(TypedValue.COMPLEX_UNIT_DIP)
        .setDefaultColor(Color.parseColor("#999999"))
        .setDefaultBold(false)
        .build();

    lineGap = CellUtils.dp2px(data.optDouble("lineGap", 2));
  }

  @Override
  protected void bindViewData(@NonNull IconTwoTextView view) {
    super.bindViewData(view);

    view.setOnlyIconVisible(iconPosition, iconVisible);
    if (iconVisible) {
      view.setIconMargin(iconPosition, iconMargin);
      view.setLabel(iconPosition, labelVisible, labelType, label, labelMargin);
      setImage(view.getIconView(iconPosition), iconAttr);
    }

    view.setTextLayoutPadding(textLayoutPadding);
    view.setTextLayoutGravity(textLayoutGravity);

    setText(view.titleView, titleAttr);
    setText(view.descView, descAttr);

    view.getLineGapView().getLayoutParams().height = lineGap;
  }

  public boolean isIconVisible() {
    return iconVisible;
  }

  public int[] getTextLayoutPadding() {
    return textLayoutPadding;
  }

  public int[] getIconMargin() {
    return iconMargin;
  }

  public int[] getLabelMargin() {
    return labelMargin;
  }

  public String getIconPosition() {
    return iconPosition;
  }

  public String getTextLayoutGravity() {
    return textLayoutGravity;
  }

  public boolean isLabelVisible() {
    return labelVisible;
  }

  public String getLabelType() {
    return labelType;
  }

  public String getLabel() {
    return label;
  }

  public TextAttr getTitleAttr() {
    return titleAttr;
  }

  public TextAttr getDescAttr() {
    return descAttr;
  }

  public ImgAttr getIconAttr() {
    return iconAttr;
  }

  public int getLineGap() {
    return lineGap;
  }
}
