package com.pasc.business.workspace;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.pasc.business.workspace.bean.BizModel;
import com.pasc.business.workspace.config.ConfigBiz;
import com.pasc.business.workspace.constants.BannerArgKey;
import com.pasc.business.workspace.event.RefreshMyProgressEvent;
import com.pasc.business.workspace.helper.NetworkManager;
import com.pasc.business.workspace.util.AsyncUtils;
import com.pasc.business.workspace.widget.BannerCell;
import com.pasc.business.workspace.widget.BannerView;
import com.pasc.business.workspace.widget.DataBoardHouseCell;
import com.pasc.business.workspace.widget.DataBoardHouseView;
import com.pasc.business.workspace.widget.DataBoardWaterCell;
import com.pasc.business.workspace.widget.DataBoardWaterView;
import com.pasc.business.workspace.widget.ListLivelihoodNewsCell;
import com.pasc.business.workspace.widget.ListLivelihoodNewsView;
import com.pasc.business.workspace.widget.MarqueeNewsCell;
import com.pasc.business.workspace.widget.MarqueeNewsView;
import com.pasc.business.workspace.widget.ProgressQueryCell;
import com.pasc.business.workspace.widget.ProgressQueryView;
import com.pasc.business.workspace.widget.RatioImageCell;
import com.pasc.business.workspace.widget.RatioImageView;
import com.pasc.business.workspace.widget.event.BannerItemClickEvent;
import com.pasc.business.workspace.widget.event.InteractionNewsItemClickEvent;
import com.pasc.business.workspace.widget.event.MarqueeNewsClickEvent;
import com.pasc.lib.base.AppProxy;
import com.pasc.lib.base.util.CollectionUtils;
import com.pasc.lib.base.util.NetworkUtils;
import com.pasc.lib.log.PascLog;
import com.pasc.lib.widget.PascUpRollView;
import com.pasc.lib.widget.tangram.BasePascCell;
import com.pasc.lib.widget.tangram.MarqueeTextCell;
import com.pasc.lib.widget.tangram.MarqueeTextModel;
import com.pasc.lib.widget.tangram.TangramEngineBuilder;
import com.pasc.lib.widget.tangram.util.AndroidUtils;
import com.pasc.lib.widget.tangram.util.CardUtils;
import com.pasc.lib.widget.toast.Toasty;
import com.pasc.lib.workspace.UserProxy;
import com.pasc.lib.workspace.WorkspaceBiz;
import com.pasc.lib.workspace.bean.Announcement;
import com.pasc.lib.workspace.bean.BannerBean;
import com.pasc.lib.workspace.bean.ConfigItem;
import com.pasc.lib.workspace.bean.HouseSecurityResp;
import com.pasc.lib.workspace.bean.InteractionNewsBean;
import com.pasc.lib.workspace.bean.InteractionNewsResp;
import com.pasc.lib.workspace.bean.MyAffairListResp;
import com.pasc.lib.workspace.bean.TodayWaterQualityResp;
import com.pasc.lib.workspace.handler.CardLoadHandler;
import com.pasc.lib.workspace.handler.HandlerProxy;
import com.pasc.lib.workspace.handler.StatProxy;
import com.pasc.lib.workspace.handler.TangramClickSupport;
import com.pasc.lib.workspace.handler.event.GoToInteractionEvent;
import com.pasc.lib.workspace.handler.event.GoToLiveLiHoodEvent;
import com.pasc.lib.workspace.handler.event.GoToThreeExceedPapersEvent;
import com.pasc.lib.workspace.handler.event.NotValidEvent;
import com.pasc.lib.workspace.handler.event.SimpleClickEvent;
import com.pasc.lib.workspace.util.AssetUtils;
import com.tmall.wireless.tangram.TangramEngine;
import com.tmall.wireless.tangram.core.adapter.GroupBasicAdapter;
import com.tmall.wireless.tangram.dataparser.concrete.Card;
import com.tmall.wireless.tangram.eventbus.BusSupport;
import com.tmall.wireless.tangram.eventbus.Event;
import com.tmall.wireless.tangram.eventbus.EventContext;
import com.tmall.wireless.tangram.eventbus.EventHandlerWrapper;
import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.support.async.AsyncLoader;
import com.tmall.wireless.tangram.support.async.CardLoadSupport;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.FlowableEmitter;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 可配置界面的基类.
 * Modified by chenruihan410 on 2018/12/07.
 *
 * @since 1.0
 */
public class BaseConfigurableFragment extends BaseLoadingFragment {

  private static final int MAX_BOTTOM_SCROLL_HEIGHT_PX = 600;

  public static final String CARD_MY_AFFAIR_LIST = "myAffairListContainer";
  public static final String CARD_SCROLL_NEWS = "scrollNewsContainer";
  public static final String CARD_TODAY_WATER_QUALITY_INFO = "todayWaterQualityInfoContainer";
  public static final String CARD_HOUSE_SECURITY = "houseSecurityContainer";
  public static final String CARD_INTERACTION_NEWS_LIST = "interactionNewsListContainer";
  public static final String WEATHER_TEMP_UNIT = "°";
  public static final String SCROLL_NEWS_ID = "scrollNews";

  protected RecyclerView configurableRecyclerView;

  protected CompositeDisposable disposables = new CompositeDisposable();

  private boolean onStart = false;
  private float offset;
  private float startY;
  private float lastRawY = 0;
  private int bottomDividerDefaultHeight = 0;

  private Handler uiHandler;

  // 组件缓存
  HashMap<Card, List<BaseCell>> cellsCache = new HashMap<Card, List<BaseCell>>();

  // tangram相关
  protected TangramEngine engine;

  private ValueAnimator animator;
  private NetworkManager.OnNetworkChangeListener onNetworkChangeListener =
      new NetworkManager.OnNetworkChangeListener() {
        @Override
        public void onNetworkChange(int networkType) {
          if (NetworkManager.hasNet()) {
            updateData();
          }
        }
      };

  private HashMap<String, CardLoadHandler> cardLoadHandlers = new HashMap<>();

  private SwipeRefreshLayout swipeRefreshLayout;

  private JSONArray configData; // 已设置到引擎的数据，在引擎设置数据后赋值

  private List<EventHandlerWrapper> eventHandlers;

  // 刷新我的办事事件
  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onEvent(RefreshMyProgressEvent event) {
    showProgressContent();
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onEvent(NotValidEvent event) {
    Toasty.init(getActivity()).setMessage("功能尚未开发").stayShort().show();
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    uiHandler = new Handler(Looper.getMainLooper());
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    if (inflater == null) return null;
    View rootView = inflater.inflate(getLayoutId(), container, false);

    configurableRecyclerView = rootView.findViewById(R.id.configurableRecyclerView);

    EventBus.getDefault().register(this);
    NetworkManager.getInstance().addOnNetworkChangeListener(onNetworkChangeListener);
    return rootView;
  }

  public void onMarqueeTextClose(Event event) {
    ArrayMap<String, String> args = event.args;
    if (args != null) {
      String id = args.get("id");
      WorkspaceBiz.getInstance().readAnnouncement(id);
    }
    EventContext eventContext = event.eventContext;
    if (eventContext != null) {
      BaseCell producer = (BaseCell) eventContext.producer;
      Card card = producer.parent;
      card.removeCell(producer);
    }
  }

  public void onMarqueeTextArrow(Event event) {
    ArrayMap<String, String> args = event.args;
    if (args != null) {
      String skipUrl = args.get("skipUrl");
      if (!TextUtils.isEmpty(skipUrl)) {
        HandlerProxy.getInstance().getProtocolHandler().handle(getActivity(), skipUrl, null);
      }
    }
  }

  /**
   * 获取布局id.
   *
   * @return 布局id.
   */
  protected int getLayoutId() {
    return R.layout.workspace_fragment_base_configurable;
  }

  private boolean isNetworkAvailable() {
    boolean networkAvailable = NetworkUtils.isNetworkAvailable();
    return networkAvailable;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    if (!isNetworkAvailable()) {
      Toasty.init(getActivity())
          .setMessage(getString(R.string.workspace_network_unavailable))
          .show();
    }

    setupMainView();
    setListener();

    swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
    swipeRefreshLayout.setColorSchemeColors(
        ContextCompat.getColor(getActivity(), R.color.pasc_primary));
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        loadConfig(swipeRefreshLayout);
      }
    });
    swipeRefreshLayout.setEnabled(isPullToRefreshEnable());
    swipeRefreshLayout.setProgressViewEndTarget(true,
        AndroidUtils.dip2px(getActivity(), getPullToRefreshEnd()));
  }

  protected int getPullToRefreshEnd() {
    return 128;
  }

  protected boolean isPullToRefreshEnable() {
    return false;
  }

  private void getInteractionNewsList(final Card card,
      @NonNull final AsyncLoader.LoadedCallback callback) {
    card.id = CARD_INTERACTION_NEWS_LIST;

    Disposable getInteractionNewsListFromCache =
        AsyncUtils.asyncCall(this.<InteractionNewsResp>bindUntilEvent(FragmentEvent.DESTROY_VIEW),
            new AsyncAction<InteractionNewsResp>() {
              @Override
              protected void onAction(FlowableEmitter<InteractionNewsResp> emitter)
                  throws Exception {
                InteractionNewsResp resultCache =
                    WorkspaceBiz.getInstance().getPeopleLiveNewsFromCache(10, 4);
                if (resultCache != null) {
                  emitter.onNext(resultCache);
                }
              }
            }, new AsyncCallback<InteractionNewsResp>() {
              @Override
              protected void onCallback(InteractionNewsResp data) throws Exception {
                PascLog.d(TAG, "获取网络民生资讯成功，更新界面");
                if (data == null) return;
                updateDataSource("interactionNewsList", data.list, null);
              }
            });

    disposables.add(getInteractionNewsListFromCache);

    //获取民生资讯数据
    if (NetworkUtils.isNetworkAvailable()) {
      Disposable getInteractionNewsListFromNet =
          AsyncUtils.asyncCall(this.<InteractionNewsResp>bindUntilEvent(FragmentEvent.DESTROY_VIEW),
              new AsyncAction<InteractionNewsResp>() {
                @Override
                protected void onAction(FlowableEmitter<InteractionNewsResp> emitter)
                    throws Exception {
                  InteractionNewsResp resultNet =
                      WorkspaceBiz.getInstance().getPeopleLiveNewsFromNet(10, 4);
                  if (resultNet != null) {
                    emitter.onNext(resultNet);
                  }
                }
              }, new AsyncCallback<InteractionNewsResp>() {
                @Override
                protected void onCallback(InteractionNewsResp data) throws Exception {
                  PascLog.d(TAG, "获取网络民生资讯成功，更新界面");
                  if (data == null) return;
                  updateDataSource("interactionNewsList", data.list, callback);
                }

                @Override
                public void onError(Throwable throwable) {
                  super.onError(throwable);
                  if (callback != null) callback.fail(false);
                }
              });

      disposables.add(getInteractionNewsListFromNet);
    }
  }

  private void getHouseSecurity(final Card card,
      @NonNull final AsyncLoader.LoadedCallback callback) {
    card.id = CARD_HOUSE_SECURITY;
    //获取住房保障数据

    Disposable getHouseSecurityFromCache =
        AsyncUtils.asyncCall(this.<HouseSecurityResp>bindUntilEvent(FragmentEvent.DESTROY_VIEW),
            new AsyncAction<HouseSecurityResp>() {
              @Override
              protected void onAction(FlowableEmitter<HouseSecurityResp> emitter) throws Exception {
                onNextNotNull(emitter, WorkspaceBiz.getInstance().getHouseSecurityFromCache());
              }
            }, new AsyncCallback<HouseSecurityResp>() {
              @Override
              protected void onCallback(HouseSecurityResp data) throws Exception {
                updateDataSource("houseSecurity", data, null);
              }
            });

    disposables.add(getHouseSecurityFromCache);

    if (NetworkUtils.isNetworkAvailable()) {
      Disposable getHouseSecurityFromNet =
          AsyncUtils.asyncCall(this.<HouseSecurityResp>bindUntilEvent(FragmentEvent.DESTROY_VIEW),
              new AsyncAction<HouseSecurityResp>() {
                @Override
                protected void onAction(FlowableEmitter<HouseSecurityResp> emitter)
                    throws Exception {
                  if (NetworkUtils.isNetworkAvailable()) {
                    onNextNotNull(emitter,
                        WorkspaceBiz.getInstance().getHouseSecurityFromNet(getActivity()));
                  }
                }
              }, new AsyncCallback<HouseSecurityResp>() {
                @Override
                protected void onCallback(HouseSecurityResp data) throws Exception {
                  updateDataSource("houseSecurity", data, callback);
                }

                @Override
                public void onError(Throwable throwable) {
                  super.onError(throwable);
                  if (callback != null) {
                    callback.fail(false);
                  }
                }
              });

      disposables.add(getHouseSecurityFromNet);
    }
  }

  private void getTodayWaterQualityInfo(final Card card,
      @NonNull final AsyncLoader.LoadedCallback callback) {
    card.id = CARD_TODAY_WATER_QUALITY_INFO;
    PascLog.d(TAG, "开始异步加载今日水质数据");
    Disposable getTodayWaterQualityFromCache =
        AsyncUtils.asyncCall(this.<TodayWaterQualityResp>bindUntilEvent(FragmentEvent.DESTROY_VIEW),
            new AsyncAction<TodayWaterQualityResp>() {
              @Override
              protected void onAction(FlowableEmitter<TodayWaterQualityResp> emitter)
                  throws Exception {
                TodayWaterQualityResp todayWaterInfoFromCache =
                    WorkspaceBiz.getInstance().getTodayWaterInfoFromCache();
                onNextNotNull(emitter, todayWaterInfoFromCache);
              }
            }, new AsyncCallback<TodayWaterQualityResp>() {
              @Override
              protected void onCallback(TodayWaterQualityResp data) throws Exception {
                PascLog.d(TAG, "获取缓存今日水质数据成功");
                updateDataSource("todayWaterQualityInfo", data, null);
              }
            });
    disposables.add(getTodayWaterQualityFromCache);

    if (NetworkUtils.isNetworkAvailable()) {
      Disposable getTodayWaterQualityFromNet = AsyncUtils.asyncCall(
          this.<TodayWaterQualityResp>bindUntilEvent(FragmentEvent.DESTROY_VIEW),
          new AsyncAction<TodayWaterQualityResp>() {
            @Override
            protected void onAction(FlowableEmitter<TodayWaterQualityResp> emitter)
                throws Exception {
              TodayWaterQualityResp todayWaterInfoFromNet =
                  WorkspaceBiz.getInstance().getTodayWaterInfoFromNet(getActivity());
              onNextNotNull(emitter, todayWaterInfoFromNet);
            }
          }, new AsyncCallback<TodayWaterQualityResp>() {
            @Override
            protected void onCallback(TodayWaterQualityResp data) throws Exception {
              PascLog.d(TAG, "获取网络今日水质数据成功");
              updateDataSource("todayWaterQualityInfo", data, callback);
            }

            @Override
            public void onError(Throwable throwable) {
              super.onError(throwable);
              if (callback != null) {
                callback.fail(false);
              }
            }
          });

      disposables.add(getTodayWaterQualityFromNet);
    }
  }

  private void getScrollNews(final Card card, final AsyncLoader.LoadedCallback callback) {
    card.id = CARD_SCROLL_NEWS;

    // 获取缓存的更新界面
    Disposable getScrollNewsFromCache = AsyncUtils.asyncCall(
        this.<List<InteractionNewsBean>>bindUntilEvent(FragmentEvent.DESTROY_VIEW),
        new AsyncAction<List<InteractionNewsBean>>() {
          @Override
          protected void onAction(FlowableEmitter<List<InteractionNewsBean>> emitter)
              throws Exception {
            List<InteractionNewsBean> scrollNewsFromCache =
                WorkspaceBiz.getInstance().getScrollNewsFromCache();
            onNextNotNull(emitter, scrollNewsFromCache);
          }
        }, new AsyncCallback<List<InteractionNewsBean>>() {
          @Override
          protected void onCallback(List<InteractionNewsBean> newsItems) throws Exception {
            PascLog.d(TAG, "加载缓存滚动新闻信息成功，更新视图");
            if (newsItems.size() > 0) {
              updateDataSource("scrollNews", newsItems, null);
            }
          }
        });
    disposables.add(getScrollNewsFromCache);

    //加载滚动资讯
    if (NetworkUtils.isNetworkAvailable()) {
      Disposable getScrollNewsFromNet = AsyncUtils.asyncCall(
          this.<List<InteractionNewsBean>>bindUntilEvent(FragmentEvent.DESTROY_VIEW),
          new AsyncAction<List<InteractionNewsBean>>() {
            @Override
            protected void onAction(FlowableEmitter<List<InteractionNewsBean>> emitter)
                throws Exception {
              List<InteractionNewsBean> scrollNewsFromNet =
                  WorkspaceBiz.getInstance().getScrollNewsFromNet(getActivity());
              onNextNotNull(emitter, scrollNewsFromNet);
            }
          }, new AsyncCallback<List<InteractionNewsBean>>() {
            @Override
            protected void onCallback(List<InteractionNewsBean> newsItems) throws Exception {
              PascLog.d(TAG, "加载网络滚动新闻信息成功，更新视图");
              updateDataSource("scrollNews", newsItems, callback);
            }

            @Override
            public void onError(Throwable throwable) {
              super.onError(throwable);
              if (callback != null) {
                callback.fail(false);
              }
            }
          });
      disposables.add(getScrollNewsFromNet);
    }
  }

  private void getScrollNews2(final Card card, final AsyncLoader.LoadedCallback callback) {
    JSONObject loadParams = card.loadParams;
    int lineCount = 1;
    if (loadParams != null) {
      lineCount = loadParams.optInt("lineCount", 1);
    }

    final int times = lineCount;

    boolean dataSourceIsEmpty = true; // 数据源是否为空
    BaseCell baseCell = card.getCellById("scrollNews");
    if (baseCell != null && baseCell instanceof MarqueeNewsCell) {
      MarqueeNewsCell marqueeNewsCell = (MarqueeNewsCell) baseCell;
      List listDataSource = marqueeNewsCell.getListDataSource();
      if (listDataSource != null && listDataSource.size() > 0) {
        dataSourceIsEmpty = false;
      }
    }
    final boolean needCache = dataSourceIsEmpty; // 是否需要缓存数据，如果数据源为空则需要

    Disposable disposable = AsyncUtils.asyncCall(
        this.<BizModel<List<InteractionNewsBean>>>bindUntilEvent(FragmentEvent.DESTROY_VIEW),
        new AsyncAction<BizModel<List<InteractionNewsBean>>>() {

          private BizModel<List<BaseCell>> toBizModel(List<InteractionNewsBean> newsBeans)
              throws JSONException {
            if (CollectionUtils.isEmpty(newsBeans)) return null;

            JSONArray jsonArray = new JSONArray();

            JSONObject jsonObject = CardUtils.getItemTemplate(card, "component-marqueeNews");

            if (jsonObject != null) {
              jsonArray.put(jsonObject);

              MarqueeNewsCell baseCell =
                  (MarqueeNewsCell) engine.parseSingleComponent(card, jsonObject);
              baseCell.setDataSource(newsBeans);

              List<BaseCell> baseCells = new ArrayList<>();
              baseCells.add(baseCell);

              BizModel<List<BaseCell>> bizModel = new BizModel<>();
              bizModel.setSuccess(true);
              bizModel.setData(baseCells);
              return bizModel;
            }

            return null;
          }

          @Override
          protected void onAction(FlowableEmitter<BizModel<List<InteractionNewsBean>>> emitter)
              throws Exception {
            if (needCache) {
              // 还没加载数据，从缓存里获取数据进行展示
              try {
                List<InteractionNewsBean> newsCache =
                    WorkspaceBiz.getInstance().getScrollNewsFromCache();

                ArrayList<InteractionNewsBean> beans = new ArrayList<>();
                for (int i = 0; i < times; i++) {
                  beans.addAll(newsCache);
                }

                BizModel<List<InteractionNewsBean>> bizModel = new BizModel<>();
                bizModel.setSource(BizModel.Source.CACHE);
                bizModel.setSuccess(true);
                bizModel.setData(beans);
                onNextNotNull(emitter, bizModel);
              } catch (Exception e) {
                e.printStackTrace();

                BizModel bizModel = new BizModel<>();
                bizModel.setSuccess(false);
                bizModel.setErrorMsg("获取滚动新闻缓存数据异常");
                onNextNotNull(emitter, bizModel);
              }
            }

            if (NetworkUtils.isNetworkAvailable()) {
              try {
                List<InteractionNewsBean> news =
                    WorkspaceBiz.getInstance().getScrollNewsFromNet(getActivity());

                ArrayList<InteractionNewsBean> beans = new ArrayList<>();
                for (int i = 0; i < times; i++) {
                  beans.addAll(news);
                }

                BizModel<List<InteractionNewsBean>> bizModel = new BizModel<>();
                bizModel.setSource(BizModel.Source.NET);
                bizModel.setSuccess(true);
                bizModel.setData(beans);
                onNextNotNull(emitter, bizModel);
              } catch (Exception e) {
                e.printStackTrace();
                BizModel bizModel = new BizModel<>();
                bizModel.setSuccess(false);
                bizModel.setErrorMsg("获取滚动新闻网络数据异常");
                onNextNotNull(emitter, bizModel);
              }
            }
          }
        }, new AsyncCallback<BizModel<List<InteractionNewsBean>>>() {
          @Override
          protected void onCallback(BizModel<List<InteractionNewsBean>> bizModel) throws Exception {
            if (bizModel.isSuccess()) {
              BaseCell cell = card.getCellById(SCROLL_NEWS_ID);
              if (cell != null && cell instanceof MarqueeNewsCell) {
                MarqueeNewsCell marqueeNewsCell = (MarqueeNewsCell) cell;
                List dataSource = bizModel.getData();
                marqueeNewsCell.setDataSource(dataSource);
              }
              if (bizModel.fromNet()) {
                // 如果是从网络获取的，则结束
                callback.finish();
              }
              card.notifyDataChange();
            }
          }
        });

    disposables.add(disposable);
  }

  /**
   * 更新数据源.
   *
   * @param cellId 组件id.
   * @param newObj 新的数据.
   * @param callback 回调.
   */
  private void updateDataSource(String cellId, Object newObj,
      final AsyncLoader.LoadedCallback callback) {
    if (TextUtils.isEmpty(cellId)) return;
    if (newObj == null) return;

    List newDataSource = null;
    if (newObj instanceof List) {
      newDataSource = (List) newObj;
    } else {
      newDataSource = new ArrayList();
      newDataSource.add(newObj);
    }

    if (engine != null) {
      final BaseCell cell = engine.findCellById(cellId);
      if (cell != null && cell instanceof BasePascCell) {
        BasePascCell basePascCell = (BasePascCell) cell;
        Object dataSourceObj = basePascCell.getDataSource();
        if (dataSourceObj != null) {
          if (dataSourceObj instanceof List) {
            List dataSource = (List) dataSourceObj;
            if (isEquals(dataSource, newDataSource)) {
              Log.d(TAG, "数据源相同，不更新界面");
            }
          }
        }
      }

      Log.d(TAG, "数据源不同，更新界面");
      ((BasePascCell) cell).setDataSource(newDataSource);
      engine.update(cell);
      if (callback != null) {
        callback.finish();
      }
    }
  }

  private boolean isEquals(List listOne, List listTwo) {
    if (listOne == null && listTwo == null) return true;
    if (listOne == null && listTwo != null) return false;
    if (listOne != null && listTwo == null) return false;

    int size = listOne.size();
    if (size != listTwo.size()) return false;

    for (int i = 0; i < size; i++) {
      Object objOne = listOne.get(i);
      Object objTwo = listTwo.get(i);
      if (!objOne.equals(objTwo)) {
        return false;
      }
    }

    return true;
  }

  @Override
  public void onStop() {
    super.onStop();
  }

  @Override
  public void onDestroyView() {
    unregisterCardEvent();
    if (engine != null) {
      engine.destroy();
    }
    super.onDestroyView();
    EventBus.getDefault().unregister(this);
    disposables.clear();
    NetworkManager.getInstance().removeOnNetworkChangeListener(onNetworkChangeListener);
  }

  @SuppressLint("ClickableViewAccessibility")
  private void setListener() {

    configurableRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (!configurableRecyclerView.canScrollVertically(1)) {
          VirtualLayoutManager layoutManager = engine.getLayoutManager();
          int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
          int itemCount = engine.getGroupBasicAdapter().getItemCount();

          boolean atLastView = lastVisibleItemPosition == itemCount - 1; // 是否达到最后一个view
          View lastView = layoutManager.findViewByPosition(lastVisibleItemPosition);
          ListLivelihoodNewsView bottomView = null;
          if (lastView instanceof ListLivelihoodNewsView) {
            bottomView = ((ListLivelihoodNewsView) lastView);
          }
          if (atLastView && bottomView != null) {

            Object tag = bottomView.tvBottomTip.getTag();
            if (tag != null && tag.equals("3")) {
              return;
            }

            if (bottomDividerDefaultHeight == 0) {
              bottomDividerDefaultHeight = bottomView.bottomDivider.getHeight();
            }

            final ListLivelihoodNewsView finalBottomView = bottomView;
            finalBottomView.tvBottomTip.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                if (animator != null) {
                  return;
                }
                setBottomTipState(finalBottomView.tvBottomTip, "点击进入民生专栏", "1");
                final ViewGroup.LayoutParams layoutParams =
                    finalBottomView.bottomDivider.getLayoutParams();
                animator = ValueAnimator.ofInt(layoutParams.height, bottomDividerDefaultHeight);
                animator.setDuration(400);
                animator.addListener(
                    new LoadMoreAnimatorListener(finalBottomView, layoutParams, 0));
                final View bottomDivider = finalBottomView.bottomDivider;
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                  @Override
                  public void onAnimationUpdate(ValueAnimator animation) {
                    layoutParams.height = (int) animation.getAnimatedValue();
                    bottomDivider.setLayoutParams(layoutParams);
                  }
                });
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.start();
              }
            });
          }
        }
      }
    });

    View.OnTouchListener onMainPageTouchListener = new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        VirtualLayoutManager layoutManager = engine.getLayoutManager();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        int itemCount = engine.getGroupBasicAdapter().getItemCount();

        boolean atLastView = lastVisibleItemPosition == itemCount - 1; // 是否达到最后一个view
        View lastView = layoutManager.findViewByPosition(lastVisibleItemPosition);
        ListLivelihoodNewsView bottomView = null;
        if (lastView instanceof ListLivelihoodNewsView) {
          bottomView = ((ListLivelihoodNewsView) lastView);
        }

        if (atLastView
            && (!configurableRecyclerView.canScrollVertically(1)
            || event.getAction() == MotionEvent.ACTION_UP)
            && bottomView != null) {

          Object tag = bottomView.tvBottomTip.getTag();
          if (tag != null && tag.equals("3")) {
            return true;
          }

          if (bottomDividerDefaultHeight == 0) {
            bottomDividerDefaultHeight = bottomView.bottomDivider.getHeight();
          }
          switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
              break;
            case MotionEvent.ACTION_MOVE:
              if (!onStart && (event.getRawY() - lastRawY) < 0) {
                onStart = true;
                startY = event.getRawY();
                return true;
              } else if (onStart) {
                offset = startY - event.getRawY();
                if (offset > 0) {
                  offset =
                      (offset > MAX_BOTTOM_SCROLL_HEIGHT_PX ? MAX_BOTTOM_SCROLL_HEIGHT_PX : offset);
                  ViewGroup.LayoutParams layoutParams = bottomView.bottomDivider.getLayoutParams();
                  layoutParams.height = bottomDividerDefaultHeight + (int) (offset
                      * 0.5);
                  bottomView.bottomDivider.setLayoutParams(layoutParams);

                  if (offset >= MAX_BOTTOM_SCROLL_HEIGHT_PX / 3) {
                    setBottomTipState(bottomView.tvBottomTip, "点击进入民生专栏", "1");
                  } else {
                    setBottomTipState(bottomView.tvBottomTip, "点击进入民生专栏", "2");
                  }
                  return true;
                }
              }
              break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
              if (onStart) {
                onStart = false;
                if (animator != null) {
                  return true;
                }
                final ViewGroup.LayoutParams layoutParams =
                    bottomView.bottomDivider.getLayoutParams();
                animator = ValueAnimator.ofInt(layoutParams.height, bottomDividerDefaultHeight);
                animator.setDuration(400);
                animator.addListener(new LoadMoreAnimatorListener(bottomView, layoutParams, 1));
                final View bottomDivider = bottomView.bottomDivider;
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                  @Override
                  public void onAnimationUpdate(ValueAnimator animation) {
                    layoutParams.height = (int) animation.getAnimatedValue();
                    bottomDivider.setLayoutParams(layoutParams);
                  }
                });
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.start();
              }
              break;
            default:
              break;
          }
          lastRawY = event.getRawY();
        }
        return false;
      }
    };
    configurableRecyclerView.setOnTouchListener(onMainPageTouchListener);
  }

  private void setBottomTipState(TextView tvBottomTip,
      String tip, String tag) {
    tvBottomTip.setText(tip);
    tvBottomTip.setTag(tag);
  }

  protected void updateData() {
    //加载滚动资讯
    doLoadCardById(CARD_SCROLL_NEWS);

    //获取今日水质数据
    doLoadCardById(CARD_TODAY_WATER_QUALITY_INFO);

    //获取住房保障数据
    doLoadCardById(CARD_HOUSE_SECURITY);

    //获取民生资讯数据
    doLoadCardById(CARD_INTERACTION_NEWS_LIST);

    //获取进度查询
    doLoadCardById(CARD_MY_AFFAIR_LIST);
  }

  // 获取列表项数量，默认是0
  protected int getItemCount() {
    int itemCount = 0;
    if (engine != null) {
      GroupBasicAdapter<Card, ?> groupBasicAdapter = engine.getGroupBasicAdapter();
      if (groupBasicAdapter != null) {
        itemCount = groupBasicAdapter.getItemCount();
      }
    }
    return itemCount;
  }

  protected void setupMainView() {
    initTangram();
  }

  private void initTangram() {

    TangramClickSupport clickSupport = getConfigClickSupport();
    TangramEngineBuilder tangramEngineBuilder = new TangramEngineBuilder(getActivity())
        .setDefaultCells()
        .registerCell("component-marqueeNews", MarqueeNewsCell.class, MarqueeNewsView.class)
        .registerCell("component-dataBoardWater", DataBoardWaterCell.class,
            DataBoardWaterView.class)
        .registerCell("component-dataBoardHouse", DataBoardHouseCell.class,
            DataBoardHouseView.class)
        .registerCell("component-listLivelihood", ListLivelihoodNewsCell.class,
            ListLivelihoodNewsView.class)
        .registerCell("component-progressQueryView", ProgressQueryCell.class,
            ProgressQueryView.class)
        .registerCell("component-banner", BannerCell.class, BannerView.class)
        .registerCell("component-bannerSecondary", BannerCell.class, BannerView.class);
    addCell(tangramEngineBuilder);

    initCardLoadHandlers(cardLoadHandlers);

    engine = tangramEngineBuilder
        .setClickSupport(clickSupport)
        .setCardLoadSupport(new CardLoadSupport(new AsyncLoader() {
          @Override
          public void loadData(final Card card, @NonNull final LoadedCallback callback) {
            String load = card.load;
            PascLog.d(TAG, "异步加载card.load=" + load);
            CardLoadHandler cardLoadHandler = cardLoadHandlers.get(load);
            if (cardLoadHandler != null) {
              cardLoadHandler.loadData(engine, card, callback);
            }
          }
        }))
        .setBindView(configurableRecyclerView)
        .build();
    engine.setPreLoadNumber(30);

    eventHandlers = buildEventHandlers();
    registerCardEvent();

    loadConfig(null);
  }

  protected void registerCardEvent() {
    if (engine != null) {
      BusSupport busSupport = engine.getService(BusSupport.class);
      if (eventHandlers != null) {
        for (EventHandlerWrapper handler : eventHandlers) {
          busSupport.register(handler);
        }
      }
    }
  }

  protected List<EventHandlerWrapper> buildEventHandlers() {
    ArrayList<EventHandlerWrapper> eventHandlers = new ArrayList<>();
    eventHandlers.add(BusSupport.wrapEventHandler("component-marqueeText-close", null, this,
        "onMarqueeTextClose"));
    eventHandlers.add(BusSupport.wrapEventHandler("component-marqueeText-arrow", null, this,
        "onMarqueeTextArrow"));
    eventHandlers.add(BusSupport.wrapEventHandler("component-banner-onBannerItemClick", null, this,
        "onBannerItemClick"));
    eventHandlers.add(
        BusSupport.wrapEventHandler("component-bannerSecondary-onBannerItemClick", null, this,
            "onBannerItemClick"));
    return eventHandlers;
  }

  public void onBannerItemClick(Event event) {
    if (event == null) {
      return;
    }
    ArrayMap<String, String> args = event.args;
    if (args != null) {
      String serviceId = args.get(BannerArgKey.SERVICE_ID);
      String picSkipUrl = args.get(BannerArgKey.PIC_SKIP_URL);
      Map<String, String> params = new HashMap<String, String>();
      params.put("id", serviceId);
      HandlerProxy.getInstance().getProtocolHandler().handle(getActivity(), picSkipUrl, params);
    }
  }

  protected void unregisterCardEvent() {
    if (engine != null) {
      BusSupport busSupport = engine.getService(BusSupport.class);
      if (busSupport != null) {
        if (eventHandlers != null) {
          for (EventHandlerWrapper handler : eventHandlers) {
            busSupport.unregister(handler);
          }
        }
      }
    }
  }

  private void loadConfig(final SwipeRefreshLayout swipeRefreshLayout) {
    // 加载json布局
    final boolean onlyUseLocalConfig = isOnlyUseLocalConfig(); // 只使用内置配置数据
    final String configId = getConfigId(); // 配置的id
    final String testFlag = isUseTestConfig() ? "1" : "0"; // 0代表生产环境，1代表测试环境
    final FragmentActivity activity = getActivity(); // 上下文
    final boolean needToLoadNetData = NetworkUtils.isNetworkAvailable(); // 是否需要加载网络数据
    final boolean needToLoadCacheData = configData == null; // 是否需要加载缓存数据

    if (onlyUseLocalConfig || needToLoadCacheData || needToLoadNetData) {
      final Disposable disposableLoadConfig =
          AsyncUtils.asyncCall(this.<ConfigItem>bindUntilEvent(FragmentEvent.DESTROY_VIEW),
              new AsyncAction<ConfigItem>() {
                @Override
                protected void onAction(FlowableEmitter<ConfigItem> emitter) throws Exception {
                  if (onlyUseLocalConfig) {
                    ConfigItem configItem = ConfigBiz.getConfigFromAssets(activity, configId);
                    onNextNotNull(emitter, configItem);
                  } else {
                    if (needToLoadCacheData) {
                      try {
                        ConfigItem configItem =
                            ConfigBiz.getConfigFromCacheOrAssets(activity, configId);
                        onNextNotNull(emitter, configItem);
                      } catch (Exception e) {
                        e.printStackTrace();
                        // 加载缓存发生异常，忽略
                      }
                    }
                    if (needToLoadNetData) {
                      try {
                        ConfigItem configItemNet =
                            ConfigBiz.getConfigFromNet(activity, configId, testFlag);
                        onNextNotNull(emitter, configItemNet);
                      } catch (Exception e) {
                        e.printStackTrace();
                        // 加载网络发生异常，忽略，不忽略的话抛异常有可能将缓存数据冲掉
                      }
                    }
                  }
                }
              }, new AsyncCallback<ConfigItem>() {
                @Override
                protected void onCallback(ConfigItem configItem) throws Exception {
                  onReceivedConfig(configItem);
                }

                @Override
                public void onEnd() {
                  if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.setRefreshing(false);
                  }
                }

                @Override
                public void onError(Throwable throwable) {
                  super.onError(throwable);
                  if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.setRefreshing(false);
                  }
                }
              });
      disposables.add(disposableLoadConfig);
    } else {
      if (swipeRefreshLayout != null) {
        swipeRefreshLayout.setRefreshing(false);
      }
    }
  }

  /**
   * 是否使用测试配置.
   *
   * @return 是否测试.
   */
  protected boolean isUseTestConfig() {
    boolean isTestConfig = !AppProxy.getInstance().isProductionEvn();
    return isTestConfig;
  }

  /**
   * 是否需要显示配置的错误信息.
   *
   * @return 是否需要.
   */
  protected boolean isConfigErrorMsgVisible() {
    return false;
  }

  /**
   * 获取配置点击支持器.
   *
   * @return 点击支持器.
   */
  @NonNull
  protected TangramClickSupport getConfigClickSupport() {
    return new TangramClickSupport(getActivity());
  }

  /**
   * 是否只使用本地配置，默认在debug模式下为true，在release默认下为false.
   *
   * @return true/false.
   */
  protected boolean isOnlyUseLocalConfig() {
    boolean debug = AppProxy.getInstance().isDebug();
    return debug;
  }

  /**
   * 初始化卡片加载处理器.
   *
   * @param cardLoadHandlers 处理器容器.
   */
  protected void initCardLoadHandlers(HashMap<String, CardLoadHandler> cardLoadHandlers) {
    cardLoadHandlers.put("getBanner", new CardLoadHandler() {
      @Override
      public void loadData(TangramEngine engine, Card card, AsyncLoader.LoadedCallback callback) {
        getBanner("banner", card, callback);
      }
    });
    cardLoadHandlers.put("getBannerSecondary", new CardLoadHandler() {
      @Override
      public void loadData(TangramEngine engine, Card card, AsyncLoader.LoadedCallback callback) {
        getBanner("bannerSecondary", card, callback);
      }
    });
    cardLoadHandlers.put("getScrollNews", new CardLoadHandler() {
      @Override
      public void loadData(TangramEngine engine, Card card, AsyncLoader.LoadedCallback callback) {
        JSONObject loadParams = card.loadParams;
        if (loadParams == null || loadParams.optInt("version") == 1) {
          getScrollNews(card, callback);
        } else if (loadParams.optInt("version") == 2) {
          getScrollNews2(card, callback);
        }
      }
    });
    cardLoadHandlers.put("getTodayWaterQualityInfo", new CardLoadHandler() {
      @Override
      public void loadData(TangramEngine engine, Card card, AsyncLoader.LoadedCallback callback) {
        getTodayWaterQualityInfo(card, callback);
      }
    });
    cardLoadHandlers.put("getHouseSecurity", new CardLoadHandler() {
      @Override
      public void loadData(TangramEngine engine, Card card, AsyncLoader.LoadedCallback callback) {
        getHouseSecurity(card, callback);
      }
    });
    cardLoadHandlers.put("getInteractionNewsList", new CardLoadHandler() {
      @Override
      public void loadData(TangramEngine engine, Card card, AsyncLoader.LoadedCallback callback) {
        getInteractionNewsList(card, callback);
      }
    });
    cardLoadHandlers.put("getMyAffairList", new CardLoadHandler() {
      @Override
      public void loadData(TangramEngine engine, Card card, AsyncLoader.LoadedCallback callback) {
        getMyAffairList(card, callback);
      }
    });
    cardLoadHandlers.put("getAnnouncement", new CardLoadHandler() {
      @Override
      public void loadData(TangramEngine engine, Card card, AsyncLoader.LoadedCallback callback) {
        getAnnouncement(card, callback);
      }
    });
    cardLoadHandlers.put("getNews", new CardLoadHandler() {
      @Override
      public void loadData(TangramEngine engine, Card card, AsyncLoader.LoadedCallback callback) {
        getNews(card, callback);
      }
    });
  }

  private void getNews(final Card card, final AsyncLoader.LoadedCallback callback) {
    Disposable disposable = AsyncUtils.asyncCall(
        this.<BizModel<List<BaseCell>>>bindUntilEvent(FragmentEvent.DESTROY_VIEW),
        new AsyncAction<BizModel<List<BaseCell>>>() {

          private String[] splitUrl(String urls) {
            if (urls != null) {
              return urls.split(",");
            } else {
              return null;
            }
          }

          private BizModel<List<BaseCell>> toBizModel(List<InteractionNewsBean> newsBeans)
              throws JSONException {
            if (CollectionUtils.isEmpty(newsBeans)) return null;

            JSONArray jsonArray = new JSONArray();

            int index = 0;

            List<InteractionNewsBean> limitResult = newsBeans;
            int maxSize = limitResult.size();

            JSONObject loadParams = card.loadParams;
            if (loadParams != null) {
              if (loadParams.has("max")) {
                int max = loadParams.optInt("max");
                max = Math.min(max, maxSize);
                max = Math.max(0, max);
                limitResult = newsBeans.subList(0, max);
              }
            }

            String infoItemNoImgJson = AssetUtils.getString(getContext(),
                "configSystem/templates/getNews/component-infoItemNoImg.json");
            String infoItemThreeImgJson = AssetUtils.getString(getContext(),
                "configSystem/templates/getNews/component-infoItemThreeImg.json");
            String infoItemOneImgJson = AssetUtils.getString(getContext(),
                "configSystem/templates/getNews/component-infoItemOneImg.json");
            String dividerHorizontalJson = AssetUtils.getString(getContext(),
                "configSystem/templates/getNews/component-dividerHorizontal.json");

            for (InteractionNewsBean item : limitResult) {

              String[] urls = splitUrl(item.getResourceLinks());

              JSONObject jsonObject = new JSONObject(infoItemNoImgJson);
              if (urls != null) {
                int length = urls.length;
                if (length >= 3) {
                  jsonObject = new JSONObject(infoItemThreeImgJson);

                  if (jsonObject != null) {
                    jsonObject.put("img1Url", urls[0]);
                    jsonObject.put("img2Url", urls[1]);
                    jsonObject.put("img3Url", urls[2]);
                  }
                } else if (length < 3 && length > 0) {
                  jsonObject = new JSONObject(infoItemOneImgJson);
                  if (jsonObject != null) {
                    jsonObject.put("imgUrl", urls[0]);
                  }
                }
              }

              if (jsonObject != null) {
                jsonObject.put("title", item.getTitle());

                String issueDate = item.getIssueDate();
                issueDate = issueDate == null ? "" : issueDate;

                String origin = item.getOrigin();
                origin = origin == null ? "" : origin;

                String desc = issueDate + "  " + origin;
                desc = desc.trim();

                jsonObject.put("desc", desc);
                jsonObject.put("onClick", item.getInformationLinkH5());

                if (index == 0) {
                  JSONObject cardHeader = card.optJsonObjectParam("header");
                  if (cardHeader != null) {
                    cardHeader.put("visible", true);
                    jsonArray.put(cardHeader);
                  }
                }

                if (index > 0) {
                  JSONObject divider = new JSONObject(dividerHorizontalJson);
                  if (divider != null) {
                    jsonArray.put(divider);
                  }
                }

                jsonArray.put(jsonObject);
              }

              index++;
            }

            List<BaseCell> baseCells = engine.parseComponent(jsonArray);
            BizModel<List<BaseCell>> bizModel = new BizModel<>();
            bizModel.setSuccess(true);
            bizModel.setData(baseCells);
            return bizModel;
          }

          @Override
          protected void onAction(FlowableEmitter<BizModel<List<BaseCell>>> emitter)
              throws Exception {
            List<BaseCell> cells = card.getCells();
            if (cells == null || cells.size() <= 1) {
              // 还没加载数据，从缓存里获取数据进行展示
              try {
                List<InteractionNewsBean> newsCache = WorkspaceBiz.getInstance().getNewsFromCache();
                BizModel<List<BaseCell>> bizModel = toBizModel(newsCache);
                bizModel.setSource(BizModel.Source.CACHE);
                emitter.onNext(bizModel);
              } catch (Exception e) {
                e.printStackTrace();

                BizModel<List<BaseCell>> bizModel = new BizModel<>();
                bizModel.setSuccess(false);
                bizModel.setErrorMsg("获取新闻缓存数据异常");
                emitter.onNext(bizModel);
              }
            }

            if (NetworkUtils.isNetworkAvailable()) {
              try {
                List<InteractionNewsBean> news = WorkspaceBiz.getInstance().getNewsFromNet();
                BizModel<List<BaseCell>> bizModel = toBizModel(news);
                bizModel.setSource(BizModel.Source.NET);
                emitter.onNext(bizModel);
              } catch (Exception e) {
                e.printStackTrace();
                BizModel<List<BaseCell>> bizModel = new BizModel<>();
                bizModel.setSuccess(false);
                bizModel.setErrorMsg("获取新闻网络数据异常");
                emitter.onNext(bizModel);
              }
            }
          }
        }, new AsyncCallback<BizModel<List<BaseCell>>>() {
          @Override
          protected void onCallback(BizModel<List<BaseCell>> bizModel) throws Exception {
            if (bizModel.isSuccess()) {
              if (bizModel.fromNet()) {
                callback.finish();
              }
              card.removeAllCells();
              card.addCells(bizModel.getData());
              card.notifyDataChange();
            }
          }
        });

    disposables.add(disposable);
  }

  private void getAnnouncement(final Card card, final AsyncLoader.LoadedCallback callback) {
    if (NetworkUtils.isNetworkAvailable()) {
      Disposable disposable =
          AsyncUtils.asyncCall(this.<MarqueeTextModel>bindUntilEvent(FragmentEvent.DESTROY_VIEW),
              new AsyncAction<MarqueeTextModel>() {
                @Override
                protected void onAction(FlowableEmitter<MarqueeTextModel> emitter)
                    throws Exception {
                  Announcement homePageAnnouncement =
                      WorkspaceBiz.getInstance().getHomePageAnnouncement();
                  if (homePageAnnouncement != null) {
                    MarqueeTextModel marqueeTextModel = new MarqueeTextModel();
                    marqueeTextModel.setId(String.valueOf(homePageAnnouncement.getId()));
                    marqueeTextModel.setClosable(homePageAnnouncement.getClosedAble() == 1);
                    marqueeTextModel.setSkipUrl(homePageAnnouncement.getSkipUrl());
                    marqueeTextModel.setTitle(homePageAnnouncement.getTitle());
                    onNextNotNull(emitter, marqueeTextModel);
                  }
                }
              }, new AsyncCallback<MarqueeTextModel>() {
                @Override
                protected void onCallback(MarqueeTextModel model) throws Exception {
                  BaseCell cell = card.getCellById("announcement");
                  if (cell != null && cell instanceof MarqueeTextCell) {
                    MarqueeTextCell marqueeTextCell = (MarqueeTextCell) cell;
                    ArrayList dataSource = new ArrayList();
                    dataSource.add(model);
                    marqueeTextCell.setDataSource(dataSource);
                  }
                  callback.finish();
                  card.notifyDataChange();
                }
              });

      disposables.add(disposable);
    }
  }

  /**
   * 添加自定义的组件.
   *
   * @param builder 引擎builder.
   */
  protected void addCell(TangramEngineBuilder builder) {
  }

  /**
   * 获取配置id.
   *
   * @return 配置id.
   */
  protected String getConfigId() {
    return ConfigIdProxy.getInstance().getConfigIdHome();
  }

  /**
   * 接收到新的配置.
   *
   * @param configItem 配置对象.
   * @throws JSONException
   */
  protected void onReceivedConfig(ConfigItem configItem) throws JSONException {
    if (configItem != null) {
      JSONArray data = new JSONArray(configItem.configContent);
      engine.setData(data);
      configData = data;
    }
  }

  private void getBanner(final String cellId, final Card card,
      final AsyncLoader.LoadedCallback callback) {
    Disposable getBannerFromCache =
        AsyncUtils.asyncCall(this.<List<BannerBean>>bindUntilEvent(FragmentEvent.DESTROY_VIEW),
            new AsyncAction<List<BannerBean>>() {
              @Override
              protected void onAction(FlowableEmitter<List<BannerBean>> emitter) throws Exception {
                onNextNotNull(emitter, WorkspaceBiz.getInstance().getBannerFromCache(cellId));
              }
            }, new AsyncCallback<List<BannerBean>>() {

              @Override
              protected void onCallback(List<BannerBean> banners) throws Exception {
                if (banners.size() > 0) {
                  // 缓存里没有数据则不更新界面
                  updateDataSource(cellId, banners, null);
                }
              }
            });
    disposables.add(getBannerFromCache);
//
//    if (NetworkUtils.isNetworkAvailable()) {
//      Disposable getBannerFromNetDisposable =
//          AsyncUtils.asyncCall(this.<List<BannerBean>>bindUntilEvent(FragmentEvent.DESTROY_VIEW),
//              new AsyncAction<List<BannerBean>>() {
//                @Override
//                protected void onAction(FlowableEmitter<List<BannerBean>> emitter)
//                    throws Exception {
//                  onNextNotNull(emitter, WorkspaceBiz.getInstance()
//                      .getBannerFromNet(getActivity(), cellId, isCutOut()));
//                }
//              }, new AsyncCallback<List<BannerBean>>() {
//
//                @Override
//                protected void onCallback(List<BannerBean> banners) throws Exception {
//                  updateDataSource(cellId, banners, callback);
//                }
//
//                @Override
//                public void onError(Throwable throwable) {
//                  super.onError(throwable);
//                  if (callback != null) {
//                    callback.fail(false);
//                  }
//                }
//              });
//
//      disposables.add(getBannerFromNetDisposable);
//    }
  }

  /**
   * 是否刘海屏.
   *
   * @return 默认是false.
   */
  protected boolean isCutOut() {
    return false;
  }

  /**
   * 政务办事成功后刷新
   */
  private void showProgressContent() {
    doLoadCardById(CARD_MY_AFFAIR_LIST);
  }

  /**
   * 根据id异步加载对应的card.
   *
   * @param id card的id.
   */
  protected void doLoadCardById(String id) {
    Card card = engine.getCardById(id);
    PascLog.d(TAG, "找到异步加载的card=" + card + "，id=" + id);
    if (card != null) {
      CardLoadSupport service = engine.getService(CardLoadSupport.class);
      card.loaded = false;
      card.loading = false;
      service.doLoad(card);
    }
  }

  /**
   * 根据登录态来展示进度查询
   *
   * @param callback 回调
   */
  private void getMyAffairList(final Card card,
      @NonNull final AsyncLoader.LoadedCallback callback) {
    // 设置card的id
    card.id = CARD_MY_AFFAIR_LIST;

    boolean empty = true;
    List<BaseCell> cells = card.getCells();
    if (cells != null) {
      if (cells.size() > 0) {
        for (BaseCell cell : cells) {
          if ("myAffairList".equals(cell.id)) {
            if (cell instanceof BasePascCell) {
              List dataSource = ((BasePascCell) cell).getListDataSource();
              if (dataSource != null && dataSource.size() > 0) {
                empty = false;
                break;
              }
            }
          }
        }
      }
    }
    if (empty) {
      removeCells(card); // 没数据时隐藏
    }

    // 根据登录状态进行操作
    if (UserProxy.getInstance().hasLoggedOn() && NetworkUtils.isNetworkAvailable()) {

      Disposable disposable = AsyncUtils.asyncCall(
          BaseConfigurableFragment.this.<MyAffairListResp>bindUntilEvent(
              FragmentEvent.DESTROY_VIEW), new AsyncAction<MyAffairListResp>() {
            @Override
            protected void onAction(FlowableEmitter<MyAffairListResp> emitter) throws Exception {
              if (NetworkUtils.isNetworkAvailable()) {
                onNextNotNull(emitter,
                    WorkspaceBiz.getInstance().getMainPageAffairListFromNet(getActivity()));
              }
            }
          }, new AsyncCallback<MyAffairListResp>() {
            @Override
            protected void onCallback(MyAffairListResp data) throws Exception {
              PascLog.d(TAG, "加载我的办事成功，更新视图");
              if (data == null || CollectionUtils.isEmpty(data.list)) {
                PascLog.d(TAG, "我的办事没有数据时，隐藏我的办事模块");
                removeCells(card);
                if (callback != null) {
                  callback.finish();
                }
              } else {
                PascLog.d(TAG, "我的办事有数据时，显示我的办事模块");
                List<BaseCell> cells = cellsCache.get(card);
                if (CollectionUtils.isEmpty(cells)) {
                  cells = new ArrayList<BaseCell>();
                  List<BaseCell> cardCells = card.getCells();
                  if (cardCells != null) {
                    cells.addAll(cardCells);
                  }
                }
                if (!CollectionUtils.isEmpty(cells)) {
                  for (BaseCell cell : cells) {
                    if ("myAffairList".equals(cell.id)) {
                      if (cell instanceof BasePascCell) {
                        List dataSource = ((BasePascCell) cell).getListDataSource();
                        List newDataSource = data.list;
                        if (!isEquals(dataSource, newDataSource)) {
                          PascLog.d(TAG, "数据源不同，更新界面");
                          ((BasePascCell) cell).setDataSource(newDataSource);
                        } else {
                          PascLog.d(TAG, "数据源相同，忽略");
                        }
                      }
                      break;
                    }
                  }
                  engine.replaceCells(card, cells);
                  if (callback != null) {
                    callback.finish();
                  }
                }
              }
            }

            @Override
            public void onError(Throwable throwable) {
              super.onError(throwable);
              removeCells(card);
              if (callback != null) {
                callback.fail(false);
              }
            }
          });

      disposables.add(disposable);
    } else {
      PascLog.d(TAG, "未登录，隐藏我的办事模块");
      removeCells(card);
      if (callback != null) {
        callback.finish();
      }
    }
  }

  private List<BaseCell> getCellsCache(Card card) {
    if (card == null) return null;
    List<BaseCell> baseCells = cellsCache.get(card);
    if (baseCells != null) {
      return baseCells;
    } else {
      List<BaseCell> cells = card.getCells();
      ArrayList<BaseCell> newCells = new ArrayList<>();
      if (cells != null) {
        newCells.addAll(cells);
      }
      return newCells;
    }
  }

  // 恢复组件
  private void recoverCells(Card card) {
    if (card == null) return;

    List<BaseCell> baseCells = cellsCache.get(card);
    if (baseCells != null) {
      card.setCells(baseCells);
      cellsCache.remove(card);
    }
  }

  // 移除组件
  private void removeCells(Card card) {
    if (card == null) return;
    List<BaseCell> cells = card.getCells();
    if (cells != null && cells.size() > 0) {
      List<BaseCell> cellsContainer = cellsCache.get(card);
      if (cellsContainer == null) {
        cellsContainer = new ArrayList<BaseCell>();
      }
      cellsContainer.clear();
      cellsContainer.addAll(cells);
      cellsCache.put(card, cellsContainer);
      card.removeAllCells();
      card.notifyDataChange();
    }
  }

  /**
   * 触发跳转到三超证界面事件.
   *
   * @param event 事件.
   */
  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onEvent(final GoToThreeExceedPapersEvent event) {
    FragmentActivity activity = getActivity();
    UserProxy.getInstance().checkLoginWithCallBack(activity, new Runnable() {

      @Override
      public void run() {
        try {
          String url = event.getParams().get("url");
          HandlerProxy.getInstance().getProtocolHandler().handle(getActivity(), url, null);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  private class LoadMoreAnimatorListener extends SimpleAnimatorListener {
    private ListLivelihoodNewsView bottomView;
    private ViewGroup.LayoutParams layoutParam;
    private int mType;

    LoadMoreAnimatorListener(ListLivelihoodNewsView bottomView, ViewGroup.LayoutParams layoutParam
        , int type) {
      this.bottomView = bottomView;
      this.layoutParam = layoutParam;
      this.mType = type;
    }

    @Override
    public void onAnimationEnd(Animator animation) {
      animator = null;
      layoutParam.height = bottomDividerDefaultHeight;
      bottomView.bottomDivider.setLayoutParams(layoutParam);

      if (bottomView.tvBottomTip.getTag() != null
          && (bottomView.tvBottomTip.getTag()).equals("1")) {

        if (mType == 0) {
          bottomView.tvLeftBottomTip.setVisibility(View.GONE);
          bottomView.progressBar.setVisibility(View.VISIBLE);
          setBottomTipState(bottomView.tvBottomTip, "正在进入民生专栏", "3");
        }
        uiHandler.postDelayed(new Runnable() {
          @Override
          public void run() {
            try {
              if (mType == 0) {
                //
                // 将首页滚动到顶部
                bottomView.tvLeftBottomTip.setVisibility(View.VISIBLE);
                configurableRecyclerView.scrollToPosition(0);
                // 跳转到民生资讯
                EventBus.getDefault().post(new GoToLiveLiHoodEvent());
              }
              setBottomTipState(bottomView.tvBottomTip, "点击进入民生专栏", "2");
              bottomView.progressBar.setVisibility(View.GONE);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        }, 1000);
      } else {
        setBottomTipState(bottomView.tvBottomTip, "点击进入民生专栏", "2");
      }
    }
  }

  /**
   * 普通的点击事件.
   *
   * @param event 点击事件.
   */
  @Subscribe(threadMode = ThreadMode.MAIN)
  public final void onEvent(SimpleClickEvent event) {
    Log.d(TAG, "收到普通的点击事件");
    onCellClick(event.getParams());
  }

  /*
   * 普通点击事件的具体实现，复写该方法来实现具体逻辑.
   */
  protected void onCellClick(HashMap<String, String> params) {
  }

  /**
   * 跑马灯数据点击事件.
   *
   * @param event 点击事件.
   */
  @Subscribe(threadMode = ThreadMode.MAIN)
  public final void onEvent(MarqueeNewsClickEvent event) {
    onMarqueeNewsClick(event);
  }

  protected void onMarqueeNewsClick(MarqueeNewsClickEvent event) {
    List<InteractionNewsBean> newsBeans = new ArrayList<>();
    PascUpRollView upRollView = event.getMarqueeNewsView().getUpRollView();
    buildData(newsBeans, upRollView);
    EventBus.getDefault().post(new GoToInteractionEvent<>(GoToInteractionEvent.NT_NEWS, newsBeans));
  }

  private void buildData(List<InteractionNewsBean> newsBeans,
      PascUpRollView upRollView) {

    int childCount = upRollView.getChildCount();
    if (childCount == 1) {
      InteractionNewsBean newsBean = (InteractionNewsBean) upRollView.getChildAt(0).getTag();
      newsBeans.add(newsBean);
    } else if (childCount == 2 || childCount == 3) {
      InteractionNewsBean newsBean = (InteractionNewsBean) upRollView.getChildAt(0).getTag();
      InteractionNewsBean newsBean2 = (InteractionNewsBean) upRollView.getChildAt(1).getTag();
      newsBeans.add(newsBean);
      newsBeans.add(newsBean2);
    } else {
      if (childCount == 4) {
        if (upRollView.isNeedChangeItem()) {
          InteractionNewsBean newsBean = (InteractionNewsBean) upRollView.getChildAt(2).getTag();
          InteractionNewsBean newsBean2 = (InteractionNewsBean) upRollView.getChildAt(3).getTag();
          newsBeans.add(newsBean);
          newsBeans.add(newsBean2);
        } else {
          InteractionNewsBean newsBean = (InteractionNewsBean) upRollView.getChildAt(0).getTag();
          InteractionNewsBean newsBean2 = (InteractionNewsBean) upRollView.getChildAt(1).getTag();
          newsBeans.add(newsBean);
          newsBeans.add(newsBean2);
        }
      }
    }

    //首页--顶部服务模块埋点
    HashMap<String, String> hashMap = new HashMap<>();
    hashMap.put("service_name", "资讯");
    StatProxy.getInstance().onEvent("home", hashMap);
  }

  public TangramEngine getEngine() {
    return engine;
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public final void onEvent(InteractionNewsItemClickEvent event) {
    onInteractionNewsItemClick(event);
  }

  /**
   * 民生资讯被点击事件.
   *
   * @param event 点击事件.
   */
  protected void onInteractionNewsItemClick(InteractionNewsItemClickEvent event) {
    InteractionNewsBean newsItem = event.getItem();

    HandlerProxy.getInstance()
        .getProtocolHandler()
        .handle(getActivity(), newsItem.getInformationLinkH5(), null);

    //埋点
    HashMap<String, String> hashMap = new HashMap<>();
    hashMap.put("information", newsItem.getTitle());
    StatProxy.getInstance().onEvent("home", hashMap);
  }

  @Deprecated
  @Subscribe(threadMode = ThreadMode.MAIN)
  public final void onEvent(BannerItemClickEvent event) {
    onBannerItemClick(event);
  }

  /**
   * 广告项被点击.
   *
   * @param event 点击事件.
   */
  @Deprecated
  protected void onBannerItemClick(BannerItemClickEvent event) {
    // 埋点
    Map<String, String> map = new HashMap<>();
    map.put("service_name", "Banner");
    StatProxy.getInstance().onEvent("home", map);
    // 跳转处理
    BannerBean bannerBean = event.getBannerBean();
    String picSkipUrl = bannerBean.getPicSkipUrl();
    Map<String, String> params = new HashMap<String, String>();
    params.put("id", bannerBean.getServiceId());
    HandlerProxy.getInstance().getProtocolHandler().handle(getActivity(), picSkipUrl, params);
  }
}
