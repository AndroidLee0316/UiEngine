package com.pasc.lib.widget.tangram;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import com.pasc.lib.widget.tangram.attr.ImgAttr;
import com.pasc.lib.widget.tangram.attr.TextAttr;
import com.tmall.wireless.tangram.MVHelper;
import org.json.JSONObject;

public class ImgTextCell extends BaseCardCell<ImgTextView> {

  private ImgAttr imgAttr;
  private TextAttr titleAttr;

  @Override
  public boolean isValid() {
    return super.isValid();
  }

  @Override
  public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
    super.parseWith(data, resolver);

    imgAttr = new ImgAttr.Builder(data, "img").build();
    titleAttr = new TextAttr.Builder(data, "title").build();
  }

  @Override protected boolean isDefaultDataEnable() {
    return true;
  }

  @Override
  public void postBindView(@NonNull ImgTextView view) {
    super.postBindView(view);
  }

  @Override
  protected void bindViewData(@NonNull ImgTextView view) {
    super.bindViewData(view);

    setImage(view.getImg(), imgAttr);
    setText(view.getTitle(), titleAttr);
  }
}
