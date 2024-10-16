package com.pasc.business.mine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pasc.business.mine.R;
import com.pasc.business.mine.adapter.BusinessListAdapter;
import com.pasc.business.mine.bean.BusinessItem;

import java.util.ArrayList;
import java.util.Collection;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 其他通知
 * Created by ruanwei489 on 2018/1/22.
 */

public class OtherNoticeFragment extends Fragment {
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    private ArrayList<BusinessItem> newsList;
    private BusinessListAdapter adapter;
    CompositeDisposable disposables = new CompositeDisposable();
    private @OtherNoticeFragment.LoadingStatus
    int loadingStatus = OtherNoticeFragment.LoadingStatus.REFRESH;

    public @interface LoadingStatus {
        int REFRESH = 0;
        int LOAD_MORE = 1;
        int COMPLETE = 2;
    }

    public static Fragment newInstance() {
        OtherNoticeFragment fragment = new OtherNoticeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
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
        //        loadingStatus = OtherNoticeFragment.LoadingStatus.LOAD_MORE;
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
        if (loadingStatus == OtherNoticeFragment.LoadingStatus.REFRESH) {
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
        loadingStatus = OtherNoticeFragment.LoadingStatus.REFRESH;
    }

    private Collection<BusinessItem> createTestData() {
        ArrayList<BusinessItem> list = new ArrayList<>();
        list.add(BusinessItem.create("《2018年南通市区汽车站、火车站、机场出行时刻表出炉》，点击可查看详情", "1天前"));
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
