package com.pasc.lib.widget.tangram;

import android.support.annotation.NonNull;
import com.pasc.lib.widget.tangram.attr.ImgAttr;
import com.pasc.lib.widget.tangram.attr.TextAttr;
import com.pasc.lib.widget.tangram.util.CellUtils;
import com.tmall.wireless.tangram.MVHelper;
import org.json.JSONObject;

public class IconTwoTextSaltCell extends BaseCardCell<IconTwoTextSaltView> {

  private int gap; // 间隙值
  private ImgAttr iconAttr;
  private TextAttr titleAttr;
  private TextAttr descAttr;

  @Override
  public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
    super.parseWith(data, resolver);

    gap = CellUtils.dp2px(getFloat(data, "lineGap", 6));
    iconAttr = new ImgAttr.Builder(data, "icon").build();
    titleAttr = new TextAttr.Builder(data, "title").build();
    descAttr = new TextAttr.Builder(data, "desc").build();
  }

  @Override protected boolean isDefaultDataEnable() {
    return true;
  }

  @Override
  protected void bindViewData(@NonNull IconTwoTextSaltView view) {
    super.bindViewData(view);
    setImage(view.getIconView(), iconAttr);
    setText(view.getTitleView(), titleAttr);
    setText(view.getDescView(), descAttr);

    view.getGapView().getLayoutParams().height = gap;
  }
}
