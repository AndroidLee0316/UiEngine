package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.pasc.lib.widget.tangram.attr.ImgAttr;
import com.pasc.lib.widget.tangram.attr.TextAttr;
import com.tmall.wireless.tangram.MVHelper;
import com.tmall.wireless.tangram.TangramEngine;
import com.tmall.wireless.tangram.structure.BaseCell;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class MenuCell extends BaseCardCell<MenuView> {

  private ImgAttr iconAttr;
  private TextAttr titleAttr;
  private List<BaseCell> childCells;
  private HashMap<BaseCell, View> viewsMap = new HashMap<>();

  private boolean hasBind = false; // 是否已经绑定数据

  @Override protected boolean isDefaultDataEnable() {
    return true;
  }

  @Override
  public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
    super.parseWith(data, resolver);

    iconAttr = new ImgAttr.Builder(data, "icon").build();
    titleAttr = new TextAttr.Builder(data, "title").build();

    JSONArray dataSource = data.optJSONArray("menuItems");
    childCells = ((TangramEngine) serviceManager).parseComponent(dataSource);

    for (BaseCell cell : childCells) {
      cell.parseWith(cell.extras, resolver);
    }

    hasBind = false;
  }

  @Override
  protected void bindViewData(@NonNull MenuView view) {
    super.bindViewData(view);

    setImage(view.getIconView(), iconAttr);
    setText(view.getTitleView(), titleAttr);

    if (hasBind == false) {
      LinearLayout menuLayout = view.getMenuLayout();
      final Context context = view.getContext();

      int size = childCells.size();
      if (size > 0) {

        int childCount = menuLayout.getChildCount();
        if (childCount > 0) {
          menuLayout.removeAllViews();
        }

        for (int i = 0; i < size; i++) {
          BaseCell cell = childCells.get(i);
          if (cell instanceof SingleTextCell) {
            View cellView = viewsMap.get(cell);
            if (cellView == null) {
              cellView = new SingleTextView(context);
              LinearLayout.LayoutParams params =
                  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                      ViewGroup.LayoutParams.WRAP_CONTENT);
              cellView.setLayoutParams(params);
              viewsMap.put(cell, cellView);
            }
            cell.bindView(cellView);
            menuLayout.addView(cellView);
          }
        }
      }
    }

    hasBind = true;
  }
}
