package com.pasc.lib.widget.tangram;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import com.pasc.lib.widget.tangram.attr.ImgAttr;
import com.pasc.lib.widget.tangram.attr.TextAttr;
import com.pasc.lib.widget.tangram.util.CellUtils;
import com.tmall.wireless.tangram.MVHelper;
import org.json.JSONObject;

public class IconTextNearbyServiceCell extends BaseCardCell<IconTextNearbyServiceView> {

  private ImgAttr iconAttr;
  private TextAttr titleAttr;

  @Override
  public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
    super.parseWith(data, resolver);

    iconAttr = new ImgAttr.Builder(data, "icon")
        .build();

    titleAttr = new TextAttr.Builder(data, "title")
        .build();
  }

  @Override protected boolean isDefaultDataEnable() {
    return true;
  }

  @Override
  protected void bindViewData(@NonNull IconTextNearbyServiceView view) {
    super.bindViewData(view);

    setImage(view.getIconView(), iconAttr);
    setText(view.getTitleView(), titleAttr);
  }
}
