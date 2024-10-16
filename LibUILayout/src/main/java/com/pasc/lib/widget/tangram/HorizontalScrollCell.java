package com.pasc.lib.widget.tangram;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.pasc.lib.widget.tangram.util.AndroidUtils;
import com.pasc.lib.widget.tangram.util.CellUtils;
import com.tmall.wireless.tangram.MVHelper;
import com.tmall.wireless.tangram.structure.BaseCell;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class HorizontalScrollCell extends BaseCardCell<HorizontalScrollView> {

  private int hGap = 6;
  private int scrollMarginLeft = 15;
  private int scrollMarginRight = 15;
  private List<BaseCell> items;
  private List<HorizontalScrollEntity> horizontalScrollEntities;

  @Override public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
    super.parseWith(data, resolver);

    hGap = data.optInt("hGap", 6);
    scrollMarginLeft = data.optInt("scrollMarginLeft", 15);
    scrollMarginRight = data.optInt("scrollMarginRight", 15);

    items = CellUtils.parseWith(data, resolver, serviceManager, "items");
    if (items != null) {
      horizontalScrollEntities = new ArrayList<>();
      for (BaseCell cell : items) {
        HorizontalScrollEntity child = new HorizontalScrollEntity();
        child.setCell(cell);
        horizontalScrollEntities.add(child);
      }
    }
  }

  @Override protected boolean isDefaultDataEnable() {
    return true;
  }

  @Override
  protected void bindViewData(@NonNull HorizontalScrollView view) {
    super.bindViewData(view);

    final Context context = view.getContext();
    RecyclerView recyclerView = view.getRecyclerView();

    int itemDecorationCount = recyclerView.getItemDecorationCount();
    if (itemDecorationCount <= 0) {
      recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

        @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
            RecyclerView.State state) {
          super.getItemOffsets(outRect, view, parent, state);
          int position = parent.getChildAdapterPosition(view);
          RecyclerView.Adapter adapter = parent.getAdapter();
          boolean isFirst = position == 0;
          int left = isFirst ? AndroidUtils.dip2px(context, scrollMarginLeft)
              : AndroidUtils.dip2px(context, hGap);
          boolean isLast = adapter.getItemCount() - 1 == position;
          int right = isLast ? AndroidUtils.dip2px(context, scrollMarginRight) : 0;
          outRect.set(left, 0, right, 0);
        }
      });
    }

    List newData = horizontalScrollEntities;
    if (newData == null) {
      recyclerView.setAdapter(null);
    } else {
      boolean needUpdateData = true; // 是否需要更新数据
      RecyclerView.Adapter oldAdapter = recyclerView.getAdapter();
      if (oldAdapter != null) {
        if (oldAdapter instanceof HorizontalScrollAdapter) {
          List oldData = ((HorizontalScrollAdapter) oldAdapter).getData();
          if (oldData == newData) {
            needUpdateData = false;
          }
        }
      }

      if (needUpdateData) {
        HorizontalScrollAdapter adapter = new HorizontalScrollAdapter(newData);
        recyclerView.setAdapter(adapter);
      }
    }
  }
}