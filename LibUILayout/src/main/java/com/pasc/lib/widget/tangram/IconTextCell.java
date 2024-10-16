package com.pasc.lib.widget.tangram;

import android.support.annotation.NonNull;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pasc.lib.widget.tangram.attr.ImgAttr;
import com.pasc.lib.widget.tangram.util.AndroidUtils;
import com.pasc.lib.widget.tangram.util.CellUtils;
import com.tmall.wireless.tangram.MVHelper;

import org.json.JSONObject;

public class IconTextCell extends BaseCardCell<IconTextView> {

  public static final String TITLE = "title";
  public static final String GAP = "gap";

  private float gap; // 间隙值
  private int gapPx; // 间隙值像素

  private String label;
  private boolean labelVisible;
  private int[] labelMargin; // 标签布局margin
  private String labelType;

  private ImgAttr iconAttr;
  private String textLayoutGravity;

  @Override
  public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
    super.parseWith(data, resolver);

    gap = getFloat(data, GAP, 2);
    gapPx = AndroidUtils.getPixel(gap);

    labelVisible = getBoolean(data, "labelVisible", false);
    labelType = getString(data, "labelType", "dot");
    label = getString(data, "label", "");
    labelMargin = CellUtils.getPaddingFromJson(data, "labelMargin");
    textLayoutGravity = optStringParam("textLayoutGravity");
    iconAttr = new ImgAttr.Builder(data, "icon")
        .setWidthDefault(36)
        .setHeightDefault(36)
        .setVisibleDefault(true)
        .build();
  }

  @Override
  protected void bindViewData(@NonNull IconTextView view) {
    super.bindViewData(view);

    ImageView iconView = view.getIconView();
    TextView titleView = view.getTitleView();

    view.setLabel(labelVisible, labelType, label, labelMargin);

    setImage(iconView, iconAttr);
    setTextAndStyle(view, titleView, TITLE);

    LinearLayout.LayoutParams layoutParams =
        (LinearLayout.LayoutParams) titleView.getLayoutParams();
    layoutParams.setMargins(0, gapPx, 0, 0);
    if ("right".equals(textLayoutGravity)) {
      titleView.setGravity(Gravity.RIGHT);
    } else if ("left".equals(textLayoutGravity)) {
      titleView.setGravity(Gravity.LEFT);
    } else {
      titleView.setGravity(Gravity.CENTER);
    }
  }
}
