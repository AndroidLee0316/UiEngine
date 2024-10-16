package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;
import com.pasc.lib.widget.tangram.attr.ImgAttr;
import com.pasc.lib.widget.tangram.attr.TextAttr;
import com.pasc.lib.widget.tangram.model.DataSourceItem;
import com.pasc.lib.widget.tangram.util.DataUtils;
import com.tmall.wireless.tangram.MVHelper;
import org.json.JSONObject;

public class TwoIconTextCell extends BaseCardCell<TwoIconTextView> {

  public static final String ICON = "icon";
  public static final String ARROW_ICON = "arrowIcon";
  public static final String TITLE = "title";
  private ImgAttr iconAttr;
  private ImgAttr arrowIconAttr;
  private TextAttr titleAttr;

  @Override
  public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
    super.parseWith(data, resolver);

    iconAttr = new ImgAttr.Builder(data, ICON).build();
    arrowIconAttr = new ImgAttr.Builder(data, ARROW_ICON).build();
    titleAttr = new TextAttr.Builder(data, TITLE).build();
  }

  public ImgAttr getIconAttr() {
    return iconAttr;
  }

  public ImgAttr getArrowIconAttr() {
    return arrowIconAttr;
  }

  public TextAttr getTitleAttr() {
    return titleAttr;
  }

  @Override protected boolean isDefaultDataEnable() {
    return true;
  }

  @Override
  protected void bindViewData(@NonNull TwoIconTextView view) {
    super.bindViewData(view);

    Context context = view.getContext();

    ImageView iconView = view.getIconView();
    setImage(iconView, iconAttr);
    ImageView arrowIconView = view.getArrowIconView();
    setImage(arrowIconView, arrowIconAttr);
    TextView titleView = view.getTitleView();
    setText(titleView, titleAttr);

    DataSourceItem data = getDataSourceItem();
    if (data != null) {
      DataUtils.setImageData(this, iconView, data, ICON);
      DataUtils.setImageData(this, arrowIconView, data, ARROW_ICON);
      DataUtils.setTextData(titleView, data, TITLE);
    }
  }
}
