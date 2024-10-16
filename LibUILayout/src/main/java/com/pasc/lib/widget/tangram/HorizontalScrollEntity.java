package com.pasc.lib.widget.tangram;

import android.support.v4.util.ArrayMap;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.tmall.wireless.tangram.structure.BaseCell;

public class HorizontalScrollEntity implements MultiItemEntity {

  public static final int ITEM_TYPE_IMG_TEXT = 1;
  public static final int ITEM_TYPE_CARD = 2;

  private static ArrayMap<String, Integer> itemTypes = new ArrayMap();

  {
    itemTypes.put("component-imgText", ITEM_TYPE_IMG_TEXT);
    itemTypes.put("component-card", ITEM_TYPE_CARD);
  }

  private BaseCell cell;

  public BaseCell getCell() {
    return cell;
  }

  public void setCell(BaseCell cell) {
    this.cell = cell;
  }

  @Override public int getItemType() {
    if (cell != null) {
      Integer itemType = itemTypes.get(cell.stringType);
      if (itemType != null) {
        return itemType;
      }
    }
    return 0;
  }
}
