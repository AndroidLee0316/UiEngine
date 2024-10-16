package com.pasc.lib.widget.tangram;

import android.support.annotation.NonNull;
import com.pasc.lib.widget.tangram.model.DataSourceItem;
import com.pasc.lib.widget.tangram.util.CellUtils;
import com.pasc.lib.widget.tangram.util.DataUtils;
import com.tmall.wireless.tangram.MVHelper;
import com.tmall.wireless.tangram.structure.BaseCell;
import java.util.List;
import org.json.JSONObject;

public class PersonalInfoCell extends BaseCardCell<PersonalInfoView> {

  private List<BaseCell> imageCells;
  private List<BaseCell> personNameCells;
  private List<BaseCell> authCells;

  @Override public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
    super.parseWith(data, resolver);

    imageCells = CellUtils.parseWith(data, resolver, serviceManager, "imageItems");
    personNameCells = CellUtils.parseWith(data, resolver, serviceManager, "personNameItems");
    authCells = CellUtils.parseWith(data, resolver, serviceManager, "authItems");
  }

  @Override protected boolean isDefaultDataEnable() {
    return true;
  }

  @Override protected void bindViewData(@NonNull PersonalInfoView view) {
    super.bindViewData(view);

    RoundedImageView roundedImageView = view.getImageView();
    CellUtils.bindView(imageCells, roundedImageView);
    SingleTextView personNameView = view.getPersonNameView();
    CellUtils.bindView(personNameCells, personNameView);
    TwoIconTextView authView = view.getAuthView();
    CellUtils.bindView(authCells, authView);

    DataSourceItem data = getDataSourceItem();
    if (data != null) {
      DataUtils.setImageData(this, roundedImageView.getImageView(), data, "img");
      DataUtils.setTextData(personNameView.getTitleView(), data, "personName");
      DataUtils.setImageData(this, authView.getIconView(), data, "authIcon");
      DataUtils.setImageData(this, authView.getArrowIconView(), data, "authArrowIcon");
      DataUtils.setTextData(authView.getTitleView(), data, "authTitle");
      DataUtils.setTextColor(authView.getTitleView(), data, "authTitleColor");
    }
  }
}
