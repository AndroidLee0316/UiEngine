package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayout;
import android.util.ArrayMap;
import android.view.View;
import com.pasc.lib.widget.tangram.util.CellUtils;
import com.tmall.wireless.tangram.MVHelper;
import com.tmall.wireless.tangram.structure.BaseCell;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

public class GridLayoutCell extends BaseCardCell<GridLayoutView> {

  private int columnCount;
  private List<BaseCell> actionCells;
  private HashMap<BaseCell, View> viewMap = new HashMap<>();

  private boolean hasBind = false; // 是否已经绑定数据

  @Override
  public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
    super.parseWith(data, resolver);

    columnCount = data.optInt("columnCount", 3);
    actionCells = CellUtils.parseWith(data, resolver, serviceManager, "actionItems");

    hasBind = false;
  }

  @Override protected boolean isDefaultDataEnable() {
    return true;
  }

  @Override
  protected void bindViewData(@NonNull GridLayoutView view) {
    super.bindViewData(view);

    final Context context = view.getContext();
    final GridLayout gridLayout = view.getGridLayout();
    gridLayout.setColumnCount(columnCount);

    int size = actionCells.size();
    if (size > 0) {
      if (hasBind == false) {
        int childCount = gridLayout.getChildCount();
        if (childCount > 0) {
          gridLayout.removeAllViews();
        }

        for (int i = 0; i < size; i++) {
          BaseCell cell = actionCells.get(i);
          View childView =
              CellUtils.createView(context, gridLayout, serviceManager, cell, viewMap);

          GridLayout.LayoutParams params = new GridLayout.LayoutParams();
          params.columnSpec = GridLayout.spec(i % columnCount, 1f);
          cell.bindView(childView);
          gridLayout.addView(childView, params);
        }
      }
    }

    hasBind = true;
  }
  public List<BaseCell> getActionCells(){
    return actionCells;
  }
  public void setActionCells(List<BaseCell>actionCells){
    hasBind=false;
    this.actionCells=actionCells;
  }
}
