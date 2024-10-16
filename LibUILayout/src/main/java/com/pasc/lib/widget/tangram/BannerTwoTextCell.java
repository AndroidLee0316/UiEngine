package com.pasc.lib.widget.tangram;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.ViewGroup;
import com.pasc.lib.widget.tangram.attr.ImgAttr;
import com.pasc.lib.widget.tangram.attr.TextAttr;
import com.pasc.lib.widget.tangram.util.CellUtils;
import com.tmall.wireless.tangram.MVHelper;
import org.json.JSONObject;

public class BannerTwoTextCell extends BaseCardCell<BannerTwoTextView> {

  private ImgAttr bannerAttr;
  private TextAttr titleAttr;
  private TextAttr descAttr;
  private int lineGap;
  private int[] textLayoutMargin;

  @Override public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
    super.parseWith(data, resolver);

    bannerAttr = new ImgAttr.Builder(data, "banner")
        .setWidthDefault(ViewGroup.LayoutParams.MATCH_PARENT)
        .setHeightDefault(ViewGroup.LayoutParams.WRAP_CONTENT)
        .setVisibleDefault(true)
        .setRatioRefDefault(1)
        .setWidthRatioDefault(16)
        .setHeightRatioDefault(5)
        .build();

    titleAttr = new TextAttr.Builder(data, "title")
        .setDefaultFontSize(22)
        .setDefaultFontSizeUnit(TypedValue.COMPLEX_UNIT_DIP)
        .setDefaultColor(Color.parseColor("#ffffff"))
        .setDefaultBold(false)
        .build();

    descAttr = new TextAttr.Builder(data, "desc")
        .setDefaultFontSize(16)
        .setDefaultFontSizeUnit(TypedValue.COMPLEX_UNIT_DIP)
        .setDefaultColor(Color.parseColor("#ffffff"))
        .setDefaultBold(false)
        .build();

    double lineGapDouble = data.optDouble("lineGap", 8);
    lineGap = CellUtils.dp2px(lineGapDouble);

    if (data.has("textLayoutMargin")) {
      textLayoutMargin = CellUtils.getPaddingFromJson(data, "textLayoutMargin");
    } else {
      textLayoutMargin = new int[] { 0, 0, 0, CellUtils.dp2px(32) };
    }
  }

  @Override protected boolean isDefaultDataEnable() {
    return true;
  }

  @Override
  public void parseStyle(@Nullable JSONObject data) {
    super.parseStyle(data);
  }

  @Override
  public void postBindView(@NonNull BannerTwoTextView view) {
    super.postBindView(view);
  }

  @Override
  protected void bindViewData(@NonNull BannerTwoTextView view) {
    super.bindViewData(view);

    setImage(view.getBannerView(), bannerAttr);
    setText(view.getTitleView(), titleAttr);
    setText(view.getDescView(), descAttr);

    view.getLineGapView().getLayoutParams().height = lineGap;

    ViewGroup.LayoutParams layoutParams = view.getTextLayout().getLayoutParams();
    if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
      ((ViewGroup.MarginLayoutParams) layoutParams).setMargins(textLayoutMargin[3],
          textLayoutMargin[0], textLayoutMargin[1], textLayoutMargin[2]);
    }
  }
}
