package com.pasc.business.affair.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.pasc.business.affair.db.NewsInfo;
import com.pasc.business.affair.net.SecondPageBiz;
import com.pasc.business.affair.router.ARouterUtil;
import com.pasc.business.affair.util.TimeUtils;
import com.pasc.business.affair.widget.LoadMoreViewCs;
import com.pasc.lib.affair.R;
import com.pasc.lib.widget.EmptyView;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 功能：
 * <p>
 * created by zoujianbo345
 * data : 2018/8/27
 */
public class NewsFragment extends RxFragment {
    private RecyclerView mainRecyclerView;
    MyAdapter adapter;
    private String type = "01";
    private List<NewsInfo> datas = new ArrayList<>();
    private CompositeDisposable disposables = new CompositeDisposable();
    private EmptyView emptyView;
    //    private SwipeRefreshLayout swipeRefresh;
    public int pageNum = 0;
    private int pageSize = 10;
    private boolean isCLeadDate = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_rv, container, false);
        mainRecyclerView = rootView.findViewById(R.id.main_page_RecyclerView);
        //        swipeRefresh = rootView.findViewById(R.id.swipeRefresh);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyAdapter(datas);
        mainRecyclerView.setAdapter(adapter);
        type = getArguments().getString("type");
        //getNewsInfo();
        emptyView = new EmptyView(getActivity());
        adapter.setEmptyView(emptyView);
        emptyView.setErrorTips("暂无内容哦").setRetryText("暂无内容哦");
        //        emptyView.setErrorTips("未搜索到相关内容哦").setRetryText("未搜索到相关内容哦");
        adapter.setOnItemClickListener(onItemClickListener);
        //        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        //            @Override
        //            public void onRefresh() {
        //                getNewsInfo();
        //            }
        //        });
        adapter.setEnableLoadMore(true);
        adapter.setLoadMoreView(new LoadMoreViewCs());
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (isCLeadDate) {
                    isCLeadDate = false;
                    pageNum = 0;
                }else {
                    pageNum++;
                }

                getNewsInfo();
            }
        }, mainRecyclerView);
    }

    private BaseQuickAdapter.OnItemClickListener onItemClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            ARouterUtil.startH5(getContext(), ARouterUtil.TYPE_AFFAIR_NEWS, type + "/" + datas.get(position).id, true);

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        pageNum = 0;
        getNewsInfo();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {pageNum = 0;}
        getNewsInfo();
    }

    public void cleanAdapterStatu() {
        isCLeadDate = true;
//        adapter.loadMoreEnd(false);
        adapter.loadMoreComplete();
    }

    public void getNewsInfo() {

        Disposable houseSecurityDisposable = SecondPageBiz.getNewsInfo(type, pageNum, pageSize)
                .subscribe(new Consumer<List<NewsInfo>>() {
                    @Override
                    public void accept(List<NewsInfo> infoResp) throws Exception {
                        Log.e("isLoadings",type+"==");
                        if (pageNum == 0) {
                            datas.clear();
                        }

                        if (infoResp != null && infoResp.size() > 0) {
                            //                                saveThreeDb(infoResp.informationlist);
                            datas.addAll(infoResp);
                            adapter.notifyDataSetChanged();
                        }
                        if (infoResp.size() >= 0 && infoResp.size() < 10) {
                            if (adapter.getData().size() > 10) {
                                adapter.loadMoreEnd(false);
                            } else {
                                adapter.loadMoreEnd(true);
                            }
                        }
                        Log.e("isLoadings",type+"==");
                        if (adapter.isLoading()){
                            adapter.loadMoreComplete();
                        }
                    }
                }, new Consumer<Throwable>() {
                    /**
                     1* @param throwable
                     * @throws Exception
                     */
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (adapter.isLoading()){
                            adapter.loadMoreComplete();
                        }
                        emptyView.showErrorLayoutWithNetJudge(new com.pasc.lib.widget.ICallBack() {
                            @Override
                            public void callBack() {
                                pageNum = 0;
                                getNewsInfo();
                            }
                        });
                    }
                });
        disposables.add(houseSecurityDisposable);
    }

    private void saveThreeDb(List<NewsInfo> informationlist) {
        if ("05".equals(type)) {
            if (informationlist.size() >= 3) {
                for (int i = 0; i < 3; i++) {
                    informationlist.get(i).insert();
                }
            } else {
                for (int i = 0; i < informationlist.size(); i++) {
                    informationlist.get(i).insert();
                }
            }
        }

    }

    public static NewsFragment newInstance(String type) {
        NewsFragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    public class MyAdapter<T extends MultiItemEntity>
            extends BaseMultiItemQuickAdapter<T, BaseViewHolder> {
        //        NewsInfo  NewsInfo

        /**
         * Same as QuickAdapter#QuickAdapter(Context,int) but with
         * some initialization data.
         *
         * @param data A new list is created out of this one to avoid mutable list
         */
        public MyAdapter(List<T> data) {
            super(data);
            addItemType(NewsInfo.TYPE_ITEM, R.layout.item_news);//服务模块
        }

        @Override
        protected void convert(BaseViewHolder helper, T item) {
            if (item instanceof NewsInfo) {
                NewsInfo item1 = (NewsInfo) item;
                helper.setText(R.id.tv_title, item1.title).setText(R.id.tv_location, item1.publishUnit).setText(R.id.tv_time, TimeUtils
                    .timeToString(item1.infoTime)).setText(R.id.tv_number, item1.clickCount + "");
                //                hodler.location.setText(info.publishUnit);
                //                hodler.title.setText(info.title);
                //                hodler.time.setText(info.infoTime);
                //                hodler.number.setText(info.clickCount+"");
            }
        }
    }
}
