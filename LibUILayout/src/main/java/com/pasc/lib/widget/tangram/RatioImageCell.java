package com.pasc.lib.widget.tangram;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import com.pasc.lib.widget.tangram.attr.ImgAttr;
import com.tmall.wireless.tangram.MVHelper;
import org.json.JSONObject;

public class RatioImageCell extends BaseCardCell<RatioImageView> {

  private ImgAttr imgAttr;

  @Override
  public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
    super.parseWith(data, resolver);

    imgAttr = new ImgAttr.Builder(data, "img")
        .build();
  }

  @Override protected boolean isDefaultDataEnable() {
    return true;
  }

  @Override
  protected void bindViewData(@NonNull RatioImageView view) {
    super.bindViewData(view);

    setImage(view.getRatioImageView(), imgAttr);
  }
}
