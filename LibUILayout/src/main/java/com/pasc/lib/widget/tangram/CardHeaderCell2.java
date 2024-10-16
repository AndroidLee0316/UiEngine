package com.pasc.lib.widget.tangram;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.pasc.lib.widget.tangram.attr.ImgAttr;
import com.pasc.lib.widget.tangram.attr.TextAttr;
import com.pasc.lib.widget.tangram.util.AndroidUtils;
import com.pasc.lib.widget.tangram.util.CellUtils;
import com.tmall.wireless.tangram.MVHelper;

import org.json.JSONObject;

public class CardHeaderCell2 extends BaseCardCell<CardHeaderView2> {

  public static final String TITLE = "title";
  public static final String DESC = "desc";
  public static final String ICON = "icon";
  public static final String ARROW_ICON = "arrowIcon";
  public static final int DEFAULT_PARTITION_COLOR = Color.parseColor("#f2f6f9");

  // 隔离物是否显示
  private boolean partitionVisible = true;
  // 隔离物颜色
  private int partitionColor = DEFAULT_PARTITION_COLOR;
  private ImgAttr iconAttr;
  private ImgAttr arrowIconAttr;
  private TextAttr titleAttr;
  private TextAttr descAttr;

  @Override
  public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
    super.parseWith(data, resolver);

    partitionVisible = getBoolean(data, "partitionVisible", true);
    partitionColor = getColor("partitionColor", DEFAULT_PARTITION_COLOR);

    iconAttr = new ImgAttr.Builder(data, ICON)
        .setVisibleDefault(true)
        .setHeightDefault(16)
        .setWidthDefault(16)
        .setDefaultMargin(new int[] { 0, CellUtils.dp2px(8), 0, 0 })
        .setDefaultRes(R.drawable.ic_main_page_title_service)
        .build();

    arrowIconAttr = new ImgAttr.Builder(data, ARROW_ICON)
        .setVisibleDefault(true)
        .setHeightDefault(12)
        .setWidthDefault(12)
        .setDefaultMargin(new int[] { 0, 0, 0, CellUtils.dp2px(8) })
        .setDefaultRes(R.drawable.ic_card_header_arrow)
        .build();

    titleAttr = new TextAttr.Builder(data, TITLE).setDefaultBold(true)
        .setDefaultFontSize(17)
        .setDefaultColor(Color.parseColor("#333333"))
        .setDefaultFontSizeUnit(
            TypedValue.COMPLEX_UNIT_DIP).build();

    descAttr = new TextAttr.Builder(data, DESC).setDefaultBold(false)
        .setDefaultFontSize(12)
        .setDefaultColor(Color.parseColor("#999999"))
        .setDefaultFontSizeUnit(
            TypedValue.COMPLEX_UNIT_DIP).build();
  }

  @Override protected boolean isDefaultDataEnable() {
    return true;
  }

  @Override protected void bindViewData(CardHeaderView2 view) {
    super.bindViewData(view);

    view.setPartitionVisible(partitionVisible);
    if (partitionVisible) {
      view.setPartitionColor(partitionColor);
    }

    setImage(view.getIconView(), iconAttr);
    setImage(view.getArrowIconView(), arrowIconAttr);
    setText(view.getTitleView(), titleAttr);
    setText(view.getDescView(), descAttr);
  }
}
