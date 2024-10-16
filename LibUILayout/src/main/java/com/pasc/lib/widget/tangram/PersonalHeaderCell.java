package com.pasc.lib.widget.tangram;

import android.support.annotation.NonNull;
import com.pasc.lib.widget.tangram.model.DataSourceItem;
import com.pasc.lib.widget.tangram.util.CellUtils;
import com.pasc.lib.widget.tangram.util.DataUtils;
import com.tmall.wireless.tangram.MVHelper;
import com.tmall.wireless.tangram.structure.BaseCell;
import java.util.List;
import org.json.JSONObject;

public class PersonalHeaderCell extends BaseCardCell<PersonalHeaderView> {

  private List<BaseCell> imageCells;
  private List<BaseCell> personNameCells;
  private List<BaseCell> authCells;
  private List<BaseCell> scoreCells;

  @Override public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
    super.parseWith(data, resolver);

    imageCells = CellUtils.parseWith(data, resolver, serviceManager, "imageItems");
    personNameCells = CellUtils.parseWith(data, resolver, serviceManager, "personNameItems");
    authCells = CellUtils.parseWith(data, resolver, serviceManager, "authItems");
    scoreCells = CellUtils.parseWith(data, resolver, serviceManager, "scoreItems");
  }

  @Override protected boolean isDefaultDataEnable() {
    return true;
  }

  @Override protected void bindViewData(@NonNull PersonalHeaderView view) {
    super.bindViewData(view);

    RoundedImageView roundedImageView = view.getImageView();
    SingleTextView personNameView = view.getPersonNameView();
    TwoIconTextView authView = view.getAuthView();
    IconTwoTextScoreView scoreView = view.getScoreView();

    CellUtils.bindView(imageCells, roundedImageView);
    CellUtils.bindView(personNameCells, personNameView);
    CellUtils.bindView(authCells, authView);
    CellUtils.bindView(scoreCells, scoreView);

    DataSourceItem data = getDataSourceItem();
    if (data != null) {
      DataUtils.setImageData(this, roundedImageView.getImageView(), data, "img");
      DataUtils.setTextData(personNameView.getTitleView(), data, "personName");
      DataUtils.setImageData(this, authView.getIconView(), data, "authIcon");
      DataUtils.setImageData(this, authView.getArrowIconView(), data, "authArrowIcon");
      DataUtils.setTextData(authView.getTitleView(), data, "authTitle");
      DataUtils.setTextData(scoreView.getDescView(), data, "scoreDesc");
    }
  }
  public RoundedImageCell getImageCell() {
    return CellUtils.getFirstCell(imageCells);
  }

  public SingleTextCell getPersonNameCell() {
    return CellUtils.getFirstCell(personNameCells);
  }

  public IconTwoTextCell getScoreCell() {
    return CellUtils.getFirstCell(scoreCells);
  }

  public TwoIconTextCell getAuthCell() {
    return CellUtils.getFirstCell(authCells);
  }
}
