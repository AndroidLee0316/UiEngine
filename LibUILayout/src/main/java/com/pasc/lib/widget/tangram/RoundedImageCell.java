package com.pasc.lib.widget.tangram;

import android.support.annotation.NonNull;
import android.widget.ImageView;
import com.pasc.lib.widget.tangram.attr.ImgAttr;
import com.pasc.lib.widget.tangram.model.DataSourceItem;
import com.pasc.lib.widget.tangram.util.DataUtils;
import com.tmall.wireless.tangram.MVHelper;
import org.json.JSONObject;

public class RoundedImageCell extends BaseCardCell<RoundedImageView> {

  public static final String IMG = "img";
  private ImgAttr imgAttr;

  @Override
  public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
    super.parseWith(data, resolver);

    imgAttr = new ImgAttr.Builder(data, IMG)
        .setWidthDefault(64)
        .setHeightDefault(64)
        .setScaleTypeDefault(ImageView.ScaleType.CENTER_CROP.name())
        .setOvalDefault(true)
        .setVisibleDefault(true)
        .build();
  }

  public ImgAttr getImgAttr() {
    return imgAttr;
  }

  @Override protected boolean isDefaultDataEnable() {
    return true;
  }

  @Override
  protected void bindViewData(@NonNull RoundedImageView view) {
    super.bindViewData(view);

    com.makeramen.roundedimageview.RoundedImageView imageView = view.getImageView();
    setImage(imageView, imgAttr);

    DataSourceItem data = getDataSourceItem();
    if (data != null) {
      DataUtils.setImageData(this, imageView, data, IMG);
    }
  }
}
