package com.pasc.lib.widget.tangram;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import com.pasc.lib.widget.tangram.attr.ImgAttr;
import com.pasc.lib.widget.tangram.attr.TextAttr;
import com.pasc.lib.widget.tangram.util.CellUtils;
import com.tmall.wireless.tangram.MVHelper;
import com.tmall.wireless.tangram.dataparser.concrete.Cell;
import org.json.JSONObject;

public class TwoIconTwoTextCell extends BaseCardCell<TwoIconTwoTextView> {

  private ImgAttr iconAttr;
  private ImgAttr arrowIconAttr;
  private TextAttr titleAttr;
  private TextAttr descAttr;
  private int lineGap;

  @Override
  public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
    super.parseWith(data, resolver);

    iconAttr = new ImgAttr.Builder(data, "icon").setDefaultMargin(
        new int[] { 0, CellUtils.dp2px(8), 0, 0 })
        .setDefaultRes(R.drawable.ic_circle_orange_education)
        .setHeightDefault(24)
        .setWidthDefault(24)
        .setVisibleDefault(true)
        .build();

    arrowIconAttr = new ImgAttr.Builder(data, "arrowIcon").setDefaultMargin(new int[] {
        0, 0, 0,
        CellUtils.dp2px(8)
    })
        .setDefaultRes(R.drawable.ic_arrow_right_lifemap)
        .setHeightDefault(12)
        .setWidthDefault(12)
        .setVisibleDefault(true)
        .build();

    titleAttr = new TextAttr.Builder(data, "title").setDefaultColor(Color.parseColor("#333333"))
        .setDefaultFontSizeUnit(
            TypedValue.COMPLEX_UNIT_DIP)
        .setDefaultFontSize(15)
        .build();

    descAttr = new TextAttr.Builder(data, "desc").setDefaultColor(Color.parseColor("#999999"))
        .setDefaultFontSizeUnit(
            TypedValue.COMPLEX_UNIT_DIP)
        .setDefaultFontSize(12)
        .build();

    lineGap = CellUtils.dp2px(data.optInt("lineGap", 4));
  }

  @Override protected boolean isDefaultDataEnable() {
    return true;
  }

  @Override
  protected void bindViewData(@NonNull TwoIconTwoTextView view) {
    super.bindViewData(view);

    view.getLingGapView().getLayoutParams().height = lineGap;

    setImage(view.getIconView(), iconAttr);
    setImage(view.getArrowIconView(), arrowIconAttr);
    setText(view.getTitleView(), titleAttr);
    setText(view.getDescView(), descAttr);
  }
}
