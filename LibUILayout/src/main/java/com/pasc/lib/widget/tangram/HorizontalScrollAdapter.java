package com.pasc.lib.widget.tangram;

import android.view.View;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

public class HorizontalScrollAdapter
    extends BaseMultiItemQuickAdapter<HorizontalScrollEntity, BaseViewHolder> {

  public HorizontalScrollAdapter(List<HorizontalScrollEntity> data) {
    super(data);
    addItemType(HorizontalScrollEntity.ITEM_TYPE_IMG_TEXT,
        R.layout.component_hs_child_item_img_text);
    addItemType(HorizontalScrollEntity.ITEM_TYPE_CARD,
        R.layout.component_hs_child_item_card);
  }

  @Override
  protected void convert(BaseViewHolder helper, HorizontalScrollEntity entity) {
    View componentView = helper.getView(R.id.componentView);
    entity.getCell().bindView(componentView);
  }
}
