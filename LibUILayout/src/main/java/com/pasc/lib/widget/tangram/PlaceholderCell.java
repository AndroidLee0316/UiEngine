package com.pasc.lib.widget.tangram;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tmall.wireless.tangram.TangramEngine;
import com.tmall.wireless.tangram.core.adapter.GroupBasicAdapter;
import com.tmall.wireless.tangram.dataparser.concrete.Card;
import com.tmall.wireless.tangram.dataparser.concrete.PojoGroupBasicAdapter;
import com.tmall.wireless.tangram.structure.BaseCell;

import java.util.List;

/**
 * 占位视图组件.
 */
public class PlaceholderCell extends BaseCardCell<PlaceholderView> {

    private static final String TAG = "PlaceholderCell";
    private static int DEFAULT_HEIGHT = 0;

    @Override
    protected void bindViewData(@NonNull PlaceholderView view) {
        super.bindViewData(view);

        // 高度
        String heightStr = optStringParam("height");

        RecyclerView contentView = null;
        PojoGroupBasicAdapter adapter = null;
        if (serviceManager != null) {
            if (serviceManager instanceof TangramEngine) {
                TangramEngine serviceManager = (TangramEngine) this.serviceManager;
                GroupBasicAdapter<Card, ?> groupBasicAdapter = serviceManager.getGroupBasicAdapter();
                if (groupBasicAdapter != null && groupBasicAdapter instanceof PojoGroupBasicAdapter) {
                    adapter = (PojoGroupBasicAdapter) groupBasicAdapter;
                }

                contentView = serviceManager.getContentView();
            }
        }

        int height = DEFAULT_HEIGHT;
        if ("match_parent".equals(heightStr)) {
            height = contentView.getHeight();
        } else {
            try {
                height = Integer.valueOf(heightStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (height > 0) {
            if (adapter != null) {
                int itemCount = adapter.getItemCount(); // 总的数量
                if (itemCount > 0) {
                    String cardId = optStringParam("fromCardId");
                    Card card = adapter.getCardById(cardId);

                    List<BaseCell> cells = card.getCells();
                    if (cells != null && cells.size() > 0) {
                        int startIndex = adapter.getPositionByItem(cells.get(0));

                        int lastIndex = itemCount - 1; // 最后的索引
                        int diffCount = lastIndex - startIndex; // 相差数量

                        int childViewCount = contentView.getChildCount();
                        if (childViewCount > 0) {
                            View lastView = contentView.getChildAt(childViewCount - 1); //  最后一个view

                            int bottom = lastView.getBottom();
                            View startView = contentView.getChildAt(childViewCount - diffCount);
                            if (startView != null) {
                                int top = startView.getTop();

                                int headHeight = Math.abs(top - bottom);

                                height = height - headHeight;
                            }
                        }
                    }
                }
            }
        }

        height = height > 0 ? height : DEFAULT_HEIGHT;
        view.getLayoutParams().height = height;
    }
}
