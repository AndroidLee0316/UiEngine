package com.pasc.business.affair.activity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pasc.business.affair.config.ConfigRespCs;
import com.pasc.business.affair.config.Constants;
import com.pasc.business.affair.widget.tablayout.TabLayout;
import com.pasc.lib.affair.R;
import com.pasc.lib.base.util.AppUtils;
import com.pasc.lib.base.util.StatusBarUtils;
import com.pasc.lib.newscenter.adapter.NewsCenterCommonPagerAdapter;
import com.pasc.lib.newscenter.bean.NewsColumnBean;
import com.pasc.lib.newscenter.data.NewsDataManager;
import com.pasc.lib.newscenter.fragment.NewsCenterListFragment;
import com.pasc.lib.newscenter.net.NewsCenterNetManager;
import com.pasc.lib.widget.banner.view.BannerViewPager;
import com.trello.rxlifecycle2.components.support.RxFragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;


//办事大厅

public class AffairsPageFragment extends RxFragment implements AppBarLayout.OnOffsetChangedListener, View.OnClickListener {
    protected final String TAG = getClass().getSimpleName();
    TabLayout tabTop;
    //   暂时修改试用
//   旧的 import com.tmall.wireless.tangram.view.BannerViewPager;
    BannerViewPager pager;
    AppBarLayout appBarLayout;
    private CompositeDisposable disposables = new CompositeDisposable();


    //    @BindView(R.id.iv_phone_01)
//    ImageView ivPhone01;
//    @BindView(R.id.iv_mayor_mailbox_01)
    ImageView ivMayorMailbox01;
    private PageAdapter adapter;
    TextView title;
    private NewsCenterCommonPagerAdapter pagerAdapter;
    private String defaultColumnType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_affair_page, container, false);

        tabTop = rootView.findViewById(R.id.top_tab);
        pager = rootView.findViewById(R.id.pager);
        appBarLayout = rootView.findViewById(R.id.appBarLayout);
        rootView.findViewById(R.id.tv_lobby).setOnClickListener(this);
        rootView.findViewById(R.id.rl_lobby).setOnClickListener(this);
        rootView.findViewById(R.id.ll_myzj).setOnClickListener(this);

        rootView.findViewById(R.id.ll_traffic).setOnClickListener(this);
        rootView.findViewById(R.id.ll_reside).setOnClickListener(this);
        rootView.findViewById(R.id.ll_renshe).setOnClickListener(this);
        rootView.findViewById(R.id.ll_security).setOnClickListener(this);

        rootView.findViewById(R.id.ll_online).setOnClickListener(this);
        rootView.findViewById(R.id.ll_suggest).setOnClickListener(this);
        rootView.findViewById(R.id.ll_complaint).setOnClickListener(this);
        title = rootView.findViewById(R.id.ctv_title);
        //获取status_bar_height资源的ID
        int resourceId =
                getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = 22;
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        RelativeLayout.LayoutParams params =
                (RelativeLayout.LayoutParams) title.getLayoutParams();
        params.height = params.height + statusBarHeight;
        title.setLayoutParams(params);
        title.setPadding(0, statusBarHeight, 0, 0);

        Disposable
            subscribe = NewsCenterNetManager.getNewsColumnList("").observeOn(
            AndroidSchedulers.mainThread()).subscribe(new Consumer<List<NewsColumnBean>>() {
            @Override public void accept(List<NewsColumnBean> list) throws Exception {

                if(list != null && list.size() > 0) {
                    //NewsCenterCommonFragment.this.showData(list);
                    //NewsDataManager.saveNewsColumnTypeList("", list);
                    showData(list);
                    NewsDataManager.saveNewsColumnTypeList("", list);
                }

            }
        }, new Consumer<Throwable>() {
            @Override public void accept(Throwable throwable) throws Exception {
                //NewsCenterCommonFragment.this.showNetErrorUI();
                Log.d("getColumnList", "accept: throwable -> " + throwable.getMessage());
            }
        });

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override public void onPageSelected(int position) {
               getFragment(position).initData();
            }

            @Override public void onPageScrollStateChanged(int state) {
            }
        });
        tabTop.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) {
                //tab.mView.getTextView().setTypeface(Typeface.DEFAULT_BOLD);
                pager.setCurrentItem(tab.getPosition());


            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {
                //tab.mView.getTextView().setTypeface(Typeface.DEFAULT);
            }

            @Override public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        return rootView;
    }

    private SparseArray<NewsCenterListFragment> fragments = new SparseArray();
    private List<NewsColumnBean> newsColumnBeanList = new ArrayList();
    private void showData(List<NewsColumnBean> list) {
        this.newsColumnBeanList.clear();
        this.newsColumnBeanList.addAll(list);
        if(this.newsColumnBeanList.size() > 0) {
            List<Fragment> fragmentList = new ArrayList();
            Iterator var3 = this.newsColumnBeanList.iterator();

            while(var3.hasNext()) {
                NewsColumnBean newsColumnBean = (NewsColumnBean)var3.next();
                if(newsColumnBean != null) {
                    fragmentList.add(NewsCenterListFragment.newInstance(newsColumnBean.columnType));
                } else {
                    fragmentList.add(NewsCenterListFragment.newInstance("1"));
                }
            }

           pagerAdapter = new NewsCenterCommonPagerAdapter(getChildFragmentManager(), this.newsColumnBeanList, fragmentList);
            this.pager.setAdapter(this.pagerAdapter);
            this.pager.setOffscreenPageLimit(this.pagerAdapter.getCount());
            if(this.newsColumnBeanList.size() > 1) {
                showTabLayout();
            }
            tabTop.setupWithViewPager(pager);

            this.showDefaultColumn();
        }

    }
    private void showTabLayout() {
        if(this.tabTop != null) {
            this.tabTop.setVisibility(View.VISIBLE);
        }

    }
    private void showDefaultColumn() {
        int currentTabIndex = 0;

        for(int i = 0; i < this.newsColumnBeanList.size(); ++i) {
            if(!TextUtils.isEmpty(this.defaultColumnType) && this.defaultColumnType.equals(((NewsColumnBean)this.newsColumnBeanList.get(i)).columnType)) {
                currentTabIndex = i;
                break;
            }
        }

        final int finalCurrentTabIndex = currentTabIndex;
        pager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override public void onGlobalLayout() {
                pager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                setPagerIndex(finalCurrentTabIndex);
            }
        });
    }

    public void setPagerIndex(int index) {
        if(this.pager != null && this.pagerAdapter != null && index < this.pagerAdapter.getCount()) {
            this.pager.setCurrentItem(index);
            this.getFragment(index).initData();
        }

    }

    private NewsCenterListFragment getFragment(int position) {
        NewsCenterListFragment fragment = (NewsCenterListFragment)this.pagerAdapter.getItem(position);
        if(fragment == null) {
            fragment = NewsCenterListFragment.newInstance("");
            fragments.append(position, fragment);
        }

        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initView();
    }

    @Override
    public void onStart() {
        super.onStart();
        initTangram();
    }

    @Override
    public void onResume() {
        super.onResume();
//        StatusBarUtils.setStatusBarColor(getActivity(),true);
        appBarLayout.addOnOffsetChangedListener(this);
//        NewsFragment fragment = (NewsFragment) getChildFragmentManager().findFragmentByTag(getFragmentTag(R.id.pager, pager.getCurrentItem()));
      /*  NewsFragment fragment = (NewsFragment) adapter.getItem(pager.getCurrentItem());
        if (fragment != null){
            fragment.cleanAdapterStatu();
        }*/
        //getConfigJson();
    }

    private static String getFragmentTag(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    @Override
    public void onPause() {
        super.onPause();
        appBarLayout.removeOnOffsetChangedListener(this);
    }

    private void initView() {
        adapter = new PageAdapter(getChildFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(pagerAdapter.getCount());
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabTop.setupWithViewPager(pager);
    }

    private void initTangram() {
        // 初始化TangramBuilder


    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (verticalOffset == 0) {
                appBarLayout.setElevation(0);
            } else {
                appBarLayout.setElevation(0);
            }
        }

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_lobby) {
//            SearchOnclicSupport.onItemClick(getContext(),"http://192.168.126.182:3100/#/list/04",true);
//            HomepageJumper.jumpLobbyActivity();
        } else if (view.getId() == R.id.rl_lobby) {
            //HomepageJumper.jumpLobbyActivity();
        } else if (view.getId() == R.id.ll_traffic) {
            //ARouterUtil.startH5(getContext(), ARouterUtil.TYPE_DEPARTMENT, "20", true);
        } else if (view.getId() == R.id.ll_reside) {
            //ARouterUtil.startH5(getContext(), ARouterUtil.TYPE_DEPARTMENT, "13", true);
        } else if (view.getId() == R.id.ll_renshe) {
            //ARouterUtil.startH5(getContext(), ARouterUtil.TYPE_DEPARTMENT, "16", true);
        } else if (view.getId() == R.id.ll_security) {
            //ARouterUtil.startH5(getContext(), ARouterUtil.TYPE_DEPARTMENT, "11", true);
        } else if (view.getId() == R.id.ll_online) {
            //ARouterUtil.startH5(getContext(), ARouterUtil.TYPE_AFFAIR_LOBBY, "01", true);
        } else if (view.getId() == R.id.ll_suggest) {
            //ARouterUtil.startH5(getContext(), ARouterUtil.TYPE_AFFAIR_LOBBY, "02", true);
        } else if (view.getId() == R.id.ll_complaint) {
            //ARouterUtil.startH5(getContext(), ARouterUtil.TYPE_AFFAIR_LOBBY, "03", true);
        } else if (view.getId() == R.id.ll_myzj) {
            //ARouterUtil.startH5(getContext(), ARouterUtil.TYPE_AFFAIR_LOBBY, "04", true);
        }

    }

    public String getChannel(Context context) {
        String channel = "";
        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo ai = pm.getApplicationInfo(
                    context.getPackageName(),
                    PackageManager.GET_META_DATA);
            String value = ai.metaData.getString("CHANNEL_NAME");
            if (value != null) {
                channel = value;
            }
        } catch (Exception e) {
// 忽略找不到包信息的异常
        }
        return channel;
    }
}
