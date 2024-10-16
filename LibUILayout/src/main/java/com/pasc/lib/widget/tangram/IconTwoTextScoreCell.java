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

public class IconTwoTextScoreCell extends BaseCardCell<IconTwoTextScoreView> {

  public static final String ICON = "icon";
  public static final String TITLE = "title";
  public static final String DESC = "desc";
  private ImgAttr iconAttr;
  private TextAttr titleAttr;
  private TextAttr descAttr;

  @Override
  public boolean isValid() {
    return super.isValid();
  }

  @Override
  public void postBindView(@NonNull IconTwoTextScoreView view) {
    super.postBindView(view);
  }

  @Override
  public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
    super.parseWith(data, resolver);

    iconAttr = new ImgAttr.Builder(data, ICON).build();
    titleAttr = new TextAttr.Builder(data, TITLE).build();
    descAttr = new TextAttr.Builder(data, DESC).build();
  }

  @Override protected boolean isDefaultDataEnable() {
    return true;
  }

  @Override
  protected void bindViewData(@NonNull IconTwoTextScoreView view) {
    super.bindViewData(view);
    Context context = view.getContext();

    ImageView iconView = view.getIconView();
    setImage(iconView, iconAttr);
    TextView titleView = view.getTitleView();
    setText(titleView, titleAttr);
    TextView descView = view.getDescView();
    setText(descView, descAttr);

    DataSourceItem data = getDataSourceItem();
    if (data != null) {
      DataUtils.setImageData(this, iconView, data, ICON);
      DataUtils.setTextData(titleView, data, TITLE);
      DataUtils.setTextData(descView, data, DESC);
    }
  }
}
