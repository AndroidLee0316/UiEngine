package com.pasc.business.workspace.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pasc.business.workspace.adapter.InteractionNewsAdapter;
import com.pasc.business.workspace.widget.event.InteractionNewsItemClickEvent;
import com.pasc.lib.base.util.CollectionUtils;
import com.pasc.lib.widget.tangram.BaseCardCell;
import com.pasc.lib.workspace.bean.InteractionNewsBean;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.List;

public class ListLivelihoodNewsCell extends BaseCardCell<ListLivelihoodNewsView> {

    @Override
    public void parseStyle(@Nullable JSONObject data) {
        super.parseStyle(data);
    }

    @Override
    public void postBindView(@NonNull ListLivelihoodNewsView view) {
        super.postBindView(view);
    }

    @Override
    public boolean isValid() {
        return super.isValid();
    }

    @Override
    protected void bindViewData(@NonNull ListLivelihoodNewsView view) {
        super.bindViewData(view);

        if (view == null) return;
        final List dataSource = getListDataSource();
        if (CollectionUtils.isEmpty(dataSource)) return;

        RecyclerView.Adapter adapter = view.mrvView.getAdapter();
        if (adapter != null) {
            if (adapter instanceof InteractionNewsAdapter) {
                List<InteractionNewsBean> data = ((InteractionNewsAdapter) adapter).getData();
                if (data == dataSource) {
                    // 如果数据是一致的，则不需要重新设置数据
                    return;
                }
            }
        }

        final Context context = view.getContext();
        view.mrvView.setLayoutManager(new LinearLayoutManager(context));
        InteractionNewsAdapter newsAdapter = new InteractionNewsAdapter(dataSource);
        newsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view,
                                    int position) {

                // 跳转h5界面
                Object obj = dataSource.get(position);
                if (obj == null) return;
                if (!(obj instanceof InteractionNewsBean)) return;
                InteractionNewsBean newsItem = (InteractionNewsBean) obj;

                InteractionNewsItemClickEvent event = new InteractionNewsItemClickEvent();
                event.setAdapter(adapter);
                event.setView(view);
                event.setPosition(position);
                event.setItem(newsItem);
                EventBus.getDefault().post(event);
            }
        });
        view.mrvView.setAdapter(newsAdapter);
    }
}
