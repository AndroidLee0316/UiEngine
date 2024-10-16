package com.pasc.business.workspace.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pasc.lib.log.PascLog;
import com.pasc.lib.widget.MeasureRecyclerView;
import com.pasc.lib.router.BaseJumper;
import com.pasc.business.workspace.R;
import com.pasc.business.workspace.adapter.ProgressQueryAdapter;
import com.pasc.lib.workspace.bean.MyAffairItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgressQueryView extends CardView {

    public static final String AFFAIR_ID = "affair_id";
    public static final String AFFAIR_STATUS = "affair_status";
    private static final String TAG = "ProgressQueryView";
    MeasureRecyclerView mrvView;
    ProgressQueryAdapter progressQueryAdapter;
    List<MyAffairItem> progressQueryItems = new ArrayList<>();

    public ProgressQueryView(@NonNull Context context) {
        super(context);
        initView(context, null);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.workspace_item_main_progress_query, this);

        mrvView = findViewById(R.id.rv_progress);

        mrvView.setLayoutManager(new LinearLayoutManager(context));
        progressQueryAdapter = new ProgressQueryAdapter(progressQueryItems);
        mrvView.setAdapter(progressQueryAdapter);

        progressQueryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position < progressQueryItems.size()) {
                    MyAffairItem myAffairItem = progressQueryItems.get(position);
                    int statusNo = myAffairItem.statuNo;
                    Map map = new HashMap<>();
                    map.put(AFFAIR_ID, myAffairItem.no);
                    map.put(AFFAIR_STATUS, statusNo);
                    PascLog.d(TAG, "路由跳转到进度查询详情/governmentAffairs/myAffairsDetail/main?affair_id=" + myAffairItem.no + "&affair_status=" + statusNo);
                    BaseJumper.jumpSeriaARouter("/governmentAffairs/myAffairsDetail/main", map);
                }
            }
        });
    }
}
