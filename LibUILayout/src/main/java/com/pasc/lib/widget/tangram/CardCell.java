package com.pasc.lib.widget.tangram;

import android.support.annotation.NonNull;
import com.pasc.lib.widget.tangram.attr.ImgAttr;
import com.pasc.lib.widget.tangram.attr.TextAttr;
import com.tmall.wireless.tangram.MVHelper;
import org.json.JSONObject;

public class CardCell extends BaseCardCell<CardView> {

  private ImgAttr imgAttr;
  private TextAttr titleAttr;
  private TextAttr descAttr;
  private TextAttr actionDescAttr;
  private TextAttr cardNameAttr;

  @Override
  public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
    super.parseWith(data, resolver);

    imgAttr = new ImgAttr.Builder(data, "img").build();
    titleAttr = new TextAttr.Builder(data, "title").build();
    descAttr = new TextAttr.Builder(data, "desc").build();
    actionDescAttr = new TextAttr.Builder(data, "actionDesc").build();
    cardNameAttr = new TextAttr.Builder(data, "cardName").build();
  }

  @Override protected boolean isDefaultDataEnable() {
    return true;
  }

  @Override
  protected void bindViewData(@NonNull CardView view) {
    super.bindViewData(view);

    setImage(view.getImgView(), imgAttr);
    setText(view.getTitleView(), titleAttr);
    setText(view.getDescView(), descAttr);
    setText(view.getActionDescView(), actionDescAttr);
    setText(view.getCardNameView(), cardNameAttr);
  }
}
