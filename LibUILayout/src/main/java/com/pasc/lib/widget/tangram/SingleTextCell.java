package com.pasc.lib.widget.tangram;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.widget.TextView;
import com.pasc.lib.widget.tangram.attr.TextAttr;
import com.pasc.lib.widget.tangram.model.DataSourceItem;
import com.pasc.lib.widget.tangram.util.DataUtils;
import com.tmall.wireless.tangram.MVHelper;
import org.json.JSONObject;

public class SingleTextCell extends BaseCardCell<SingleTextView> {

  public static final String GRAVITY = "gravity";
  public static final String TITLE = "title";

  private String gravity;
  private TextAttr titleAttr;

  @Override
  public boolean isValid() {
    return super.isValid();
  }

  @Override
  public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
    super.parseWith(data, resolver);

    gravity = getString(data, GRAVITY, "left");

    titleAttr = new TextAttr.Builder(data, TITLE).setDefaultFontSize(15)
        .setDefaultFontSizeUnit(TypedValue.COMPLEX_UNIT_DIP)
        .setDefaultColor(
            Color.parseColor("#333333"))
        .setDefaultBold(false)
        .build();
  }

  public String getGravity() {
    return gravity;
  }

  public TextAttr getTitleAttr() {
    return titleAttr;
  }

  @Override
  public void postBindView(@NonNull SingleTextView view) {
    super.postBindView(view);
  }

  @Override
  protected void bindViewData(@NonNull SingleTextView view) {
    super.bindViewData(view);

    view.setGravity(gravity);
    TextView titleView = view.getTitleView();
    setText(titleView, titleAttr);

    DataSourceItem data = getDataSourceItem();
    if (data != null) {
      DataUtils.setTextData(titleView, data, TITLE);
    }
  }
}
