package com.pasc.business.mine.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pasc.business.mine.R;
import com.pasc.business.mine.bean.BusinessItem;
import com.pasc.business.mine.adapter.BusinessListAdapter;

import java.util.ArrayList;
import java.util.Collection;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 业务通知
 * Created by ruanwei489 on 2018/1/22.
 */

public class BusinessNoticeFragment extends Fragment {

    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    private ArrayList<BusinessItem> newsList;
    private BusinessListAdapter adapter;
    CompositeDisposable disposables = new CompositeDisposable();

    private @BusinessNoticeFragment.LoadingStatus
    int loadingStatus = BusinessNoticeFragment.LoadingStatus.REFRESH;

    public @interface LoadingStatus {
        int REFRESH = 0;
        int LOAD_MORE = 1;
        int COMPLETE = 2;
    }

    public static Fragment newInstance() {
        return new BusinessNoticeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.mine_fragment_business_notice, null);
        recyclerView = root.findViewById(R.id.recycler_view);
        refreshLayout = root.findViewById(R.id.refresh_layout);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout.setColorSchemeResources(R.color.blue_4d73f4);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        newsList = new ArrayList<>();
        adapter = new BusinessListAdapter(newsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        //adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
        //    @Override public void onLoadMoreRequested() {
        //        loadingStatus = BusinessNoticeFragment.LoadingStatus.LOAD_MORE;
        //        //getData(resIds.size());
        //    }
        //},recyclerView);

        view.post(new Runnable() {
            @Override
            public void run() {
                refreshData();
            }
        });
    }

    private void getData(int size) {
        if (loadingStatus == BusinessNoticeFragment.LoadingStatus.REFRESH) {
            newsList.clear();
            adapter.notifyDataSetChanged();
        }

        newsList.addAll(createTestData());

        refreshLayout.setRefreshing(false);
        adapter.loadMoreComplete();

        adapter.notifyDataSetChanged();
        adapter.loadMoreEnd();
        //loadingStatus = BusinessNoticeFragment.LoadingStatus.COMPLETE;
    }

    private void refreshData() {
        getData(0);
        loadingStatus = BusinessNoticeFragment.LoadingStatus.REFRESH;
    }

    private Collection<BusinessItem> createTestData() {
        ArrayList<BusinessItem> list = new ArrayList<>();
        list.add(BusinessItem.create("您申请的《社保卡申领》业务已提交至下一审批环节，点击可查看业务进度", "2小时前"));
        return list;
    }

    @Override
    public void onDetach() {
        disposables.clear();
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
