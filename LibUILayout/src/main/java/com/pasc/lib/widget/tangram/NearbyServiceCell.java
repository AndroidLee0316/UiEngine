package com.pasc.lib.widget.tangram;

import android.support.annotation.NonNull;
import com.pasc.lib.widget.tangram.util.CellUtils;
import com.tmall.wireless.tangram.MVHelper;
import com.tmall.wireless.tangram.structure.BaseCell;
import java.util.List;
import org.json.JSONObject;

public class NearbyServiceCell extends BaseCardCell<NearbyServiceView> {

  private List<BaseCell> serviceItemCells;
  private List<BaseCell> serviceEntryItemCells;

  @Override
  public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
    super.parseWith(data, resolver);

    serviceItemCells = CellUtils.parseWith(data, resolver, serviceManager, "serviceItems");
    serviceEntryItemCells =
        CellUtils.parseWith(data, resolver, serviceManager, "serviceEntryItems");
  }

  @Override protected boolean isDefaultDataEnable() {
    return true;
  }

  @Override
  protected void bindViewData(@NonNull NearbyServiceView view) {
    super.bindViewData(view);

    CellUtils.bindView(serviceItemCells, view.getServiceItemViews());
    CellUtils.bindView(serviceEntryItemCells, view.getServiceEntryItemView());
  }
}
