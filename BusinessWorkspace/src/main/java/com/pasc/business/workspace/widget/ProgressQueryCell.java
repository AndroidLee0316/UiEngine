package com.pasc.business.workspace.widget;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pasc.lib.widget.tangram.BasePascCell;
import com.pasc.lib.workspace.bean.MyAffairItem;
import com.tmall.wireless.tangram.MVHelper;

import org.json.JSONObject;

import java.util.List;

public class ProgressQueryCell extends BasePascCell<ProgressQueryView> {

    @Override
    protected Class getDataSourceType() {
        return MyAffairItem.class;
    }

    @Override
    public void parseStyle(@Nullable JSONObject data) {
        super.parseStyle(data);
    }

    @Override
    public void postBindView(@NonNull ProgressQueryView view) {
        super.postBindView(view);
    }

    @Override
    public boolean isValid() {
        return super.isValid();
    }

    @Override
    protected void bindViewData(@NonNull ProgressQueryView view) {
        super.bindViewData(view);
        if (view == null) return;

        List dataSource = getListDataSource();
        if (dataSource != null && dataSource.size() > 0) {
            view.progressQueryItems.clear();
            view.progressQueryItems.addAll(dataSource);
            view.progressQueryAdapter.notifyDataSetChanged();
        }
    }
}
