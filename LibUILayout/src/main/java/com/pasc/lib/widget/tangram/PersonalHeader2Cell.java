package com.pasc.lib.widget.tangram;

import android.support.annotation.NonNull;
import com.pasc.lib.widget.tangram.model.DataSourceItem;
import com.pasc.lib.widget.tangram.util.CellUtils;
import com.pasc.lib.widget.tangram.util.DataUtils;
import com.tmall.wireless.tangram.MVHelper;
import com.tmall.wireless.tangram.structure.BaseCell;
import java.util.List;
import org.json.JSONObject;

public class PersonalHeader2Cell extends BaseCardCell<PersonalHeader2View> {

  private List<BaseCell> imageCells;
  private List<BaseCell> personNameCells;
  private List<BaseCell> authCells;
  private List<BaseCell> scoreCells;
  private List<BaseCell> stepNumCells;

  @Override public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
    super.parseWith(data, resolver);

    imageCells = CellUtils.parseWith(data, resolver, serviceManager, "imageItems");
    personNameCells = CellUtils.parseWith(data, resolver, serviceManager, "personNameItems");
    authCells = CellUtils.parseWith(data, resolver, serviceManager, "authItems");
    scoreCells = CellUtils.parseWith(data, resolver, serviceManager, "scoreItems");
    stepNumCells = CellUtils.parseWith(data, resolver, serviceManager, "stepNumItems");
  }

  @Override protected boolean isDefaultDataEnable() {
    return true;
  }

  @Override protected void bindViewData(@NonNull PersonalHeader2View view) {
    super.bindViewData(view);

    RoundedImageView roundedImageView = view.getImageView();
    SingleTextView personNameView = view.getPersonNameView();
    TwoIconTextView authView = view.getAuthView();
    IconTwoTextView scoreView = view.getScoreView();
    IconTwoTextView stepNumView = view.getStepNumView();

    CellUtils.bindView(imageCells, roundedImageView);
    CellUtils.bindView(personNameCells, personNameView);
    CellUtils.bindView(authCells, authView);
    CellUtils.bindView(scoreCells, scoreView);
    CellUtils.bindView(stepNumCells, stepNumView);

    DataSourceItem data = getDataSourceItem();
    if (data != null) {
      DataUtils.setImageData(this, roundedImageView.getImageView(), data, "img");
      DataUtils.setTextData(personNameView.getTitleView(), data, "personName");
      DataUtils.setImageData(this, authView.getIconView(), data, "authIcon");
      DataUtils.setImageData(this, authView.getArrowIconView(), data, "authArrowIcon");
      DataUtils.setTextData(authView.getTitleView(), data, "authTitle");
      DataUtils.setTextData(scoreView.getDescView(), data, "scoreDesc");
      DataUtils.setTextData(stepNumView.getDescView(), data, "stepNumDesc");
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

  public IconTwoTextCell getStepNumCell() {
    return CellUtils.getFirstCell(stepNumCells);
  }

  public TwoIconTextCell getAuthCell() {
    return CellUtils.getFirstCell(authCells);
  }
}
