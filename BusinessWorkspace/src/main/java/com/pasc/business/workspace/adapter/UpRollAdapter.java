package com.pasc.business.workspace.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.pasc.lib.widget.UpRollView;
import com.pasc.business.workspace.R;
import com.pasc.lib.workspace.bean.InteractionNewsBean;

import java.util.List;

public class UpRollAdapter extends UpRollView.BaseAdapter<InteractionNewsBean> {

    private static final String TAG = "UpRollAdapter";

    public UpRollAdapter(Context context, List<InteractionNewsBean> dataList) {
        super(context, dataList);
        this.mDatas = dataList;
    }

    public List<InteractionNewsBean> getData() {
        return this.mDatas;
    }

    @Override
    public View getView(int position) {
        if (mDatas.isEmpty()) {
            return null;
        }
        View wrapper;
        if (mViews.size() == position) {
            wrapper = LayoutInflater.from(mContext).inflate(R.layout.workspace_item_main_news, null);
            wrapper.setTag(mDatas.get(position));
            mViews.add(wrapper);
        } else {
            wrapper = mViews.get(position);
        }
        TextView tvTitle = wrapper.findViewById(R.id.upRollTextView);
        tvTitle.setText(mDatas.get(position).getTitle());
        return wrapper;
    }
}
