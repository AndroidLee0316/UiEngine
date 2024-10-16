package com.pasc.business.workspace;

import android.Manifest;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.percent.PercentFrameLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewScrollEvent;
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView;
import com.pasc.business.workspace.event.RefreshMessageCountEvent;
import com.pasc.business.workspace.util.BarUtils;
import com.pasc.lib.base.permission.PermissionUtils;
import com.pasc.lib.hybrid.PascHybrid;
import com.pasc.lib.hybrid.nativeability.WebStrategy;
import com.pasc.lib.imageloader.PascImageLoader;
import com.pasc.lib.lbs.LbsManager;
import com.pasc.lib.lbs.location.LocationException;
import com.pasc.lib.lbs.location.PascLocationListener;
import com.pasc.lib.lbs.location.bean.PascLocationData;
import com.pasc.lib.log.PascLog;
import com.pasc.lib.router.BaseJumper;
import com.pasc.lib.workspace.UserProxy;
import com.pasc.lib.workspace.WorkspaceBiz;
import com.pasc.lib.workspace.bean.MainPageWeatherInfo;
import com.pasc.lib.workspace.bean.WeatherCity;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 首页工作台Fragment，使用Tangram技术.
 * Modified by chenruihan410 on 2018/07/17.
 *
 * @since 1.0
 */
public abstract class MainPageFragment extends BaseConfigurableFragment {

  private static final String TAG = "MainPageFragment";
  private static final String SAVE_TOOLBAR_ALPHA = "toolbar_alpha";
  private static final long LOCATION_DATA_VALID_TIME = 5 * 60000; //定位有效时间分钟5分钟

  private WeatherCity currentCity; // 当前城市

  private MainPageWeatherInfo weatherInfo;
  private LinearLayout weatherArea;
  private ProgressBar pBarWeatherLoding;
  private int statusBarHeight;
  private int toolbarBgAlpha = 0;
  private Drawable toolbarBg;
  private double maxBannerOffset = 0;
  private boolean isFirstDraw = true;
  private TextView weatherState;
  private TextView tvWeatherTemp;
  private TextView tvWeatherCity;
  private TextView tvSearch;
  private ImageView ivNotification;
  private TextView unReadNumber;
  private ImageView unReadPoint;
  private ConstraintLayout toolbar;
  private View mNotification;
  private View weatherLayout;
  private View noWeather;

  /**
   * askbob入口
   */
  private ImageView askbobIV;

  float searchWidthPercent = 0.75f; // 搜索栏的比例
  float weatherWidthPercent = 0.25f; // 天气栏的比例

  public WeatherCity getCurrentCity() {
    return currentCity;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState != null && savedInstanceState.containsKey(SAVE_TOOLBAR_ALPHA)) {
      toolbarBgAlpha = savedInstanceState.getInt(SAVE_TOOLBAR_ALPHA);
    }
    currentCity = WorkspaceBiz.getInstance().getWeatherCity();
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View rootView = super.onCreateView(inflater, container, savedInstanceState);

    weatherArea = rootView.findViewById(R.id.weather_area);
    pBarWeatherLoding = rootView.findViewById(R.id.weather_loading);
    weatherState = rootView.findViewById(R.id.weather_state);
    tvWeatherTemp = rootView.findViewById(R.id.weather_tmp);
    tvWeatherCity = rootView.findViewById(R.id.weather_city);
    tvSearch = rootView.findViewById(R.id.tv_search);
    ivNotification = rootView.findViewById(R.id.notification);
    unReadNumber = rootView.findViewById(R.id.unReadNumber);
    unReadPoint = rootView.findViewById(R.id.unReadPoint);
    toolbar = rootView.findViewById(R.id.toolbar);
    weatherLayout = rootView.findViewById(R.id.weatherLayout);
    noWeather = rootView.findViewById(R.id.noWeather);
    askbobIV = rootView.findViewById(R.id.iv_askbob);

    weatherLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onClickWeather(v);
      }
    });
    mNotification = rootView.findViewById(R.id.rl_notification);
    if(TextUtils.isEmpty(obtainSearchHint())){
      tvSearch.setHint(obtainSearchHint());
    }

    tvSearch.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onViewClicked(v);
      }
    });

    mNotification.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onViewClicked(v);
      }
    });

    return rootView;
  }

  @Override
  protected int getLayoutId() {
    return R.layout.workspace_fragment_main_page;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    PercentFrameLayout.LayoutParams layoutParams =
        (PercentFrameLayout.LayoutParams) tvSearch.getLayoutParams();
    searchWidthPercent = layoutParams.getPercentLayoutInfo().widthPercent;

    PercentFrameLayout.LayoutParams weatherLayoutParams =
        (PercentFrameLayout.LayoutParams) weatherLayout.getLayoutParams();
    weatherWidthPercent = weatherLayoutParams.getPercentLayoutInfo().widthPercent;

    checkPermissions();
    setupToolbar();
    setupModule();
    setupAskbob();

  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(SAVE_TOOLBAR_ALPHA, toolbarBgAlpha);
  }
//
//  private PascLocationListener mPascLocationListener = new PascLocationListener() {
//    @Override
//    public void onLocationSuccess(PascLocationData data) {
//      PascLog.d(TAG, "定位成功");
//      handleLocationCity(data);
////      LbsManager.getInstance().stopLocation(0, mPascLocationListener);
//    }
//
//    @Override
//    public void onLocationFailure(LocationException e) {
//      PascLog.d(TAG, "定位失败");
//      getSimpleMainPageWeatherInfo(currentCity);
//      LbsManager.getInstance().stopLocation(0, mPascLocationListener);
//    }
//  };

  private void updateWeatherData() {
    if (weatherInfo == null) {
      getSimpleMainPageWeatherInfo(currentCity);
    }
  }

  /**
   * 定位SDK监听函数
   */
  private void handleLocationCity(PascLocationData location) {
    String city = location.getCity();
    city = city == null ? "" : city;

    String district = location.getDistrict();
    district = district == null ? "" : district;

    WeatherCity weatherCity = new WeatherCity();
    weatherCity.setLocation(true);
    weatherCity.setLatitude(location.getLatitude());
    weatherCity.setLongitude(location.getLongitude());
    weatherCity.setCityName(city);
    weatherCity.setDistrictName(district);

    currentCity = WorkspaceBiz.getInstance().saveWeatherCity(weatherCity);

    getSimpleMainPageWeatherInfo(currentCity);
  }

  /**
   * 获取天气简洁信息
   */
  private void getSimpleMainPageWeatherInfo(final WeatherCity city) {
    showWeatherLoading(weatherInfo == null);

    Disposable weatherFromNetDisposable =
        Flowable.create(new FlowableOnSubscribe<MainPageWeatherInfo>() {
          @Override
          public void subscribe(FlowableEmitter<MainPageWeatherInfo> emitter) throws Exception {
            try {
              MainPageWeatherInfo info = WorkspaceBiz.getInstance().getWeatherInfoFromNet(city);
              if (info == null) {
                emitter.tryOnError(new Throwable("无内容"));
              } else {
                emitter.onNext(info);
              }
            } catch (Exception e) {
              e.printStackTrace();
              emitter.tryOnError(new Throwable("发生异常"));
            } finally {
              emitter.onComplete();
            }
          }
        }, BackpressureStrategy.BUFFER)
            .compose(this.<MainPageWeatherInfo>bindUntilEvent(FragmentEvent.DESTROY_VIEW))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<MainPageWeatherInfo>() {
              @Override
              public void accept(MainPageWeatherInfo weatherInfo) throws Exception {
                if (isDetached() || tvWeatherTemp == null) {
                  return;
                }
                showMainPageWeatherInfo(weatherInfo);
              }
            }, new Consumer<Throwable>() {
              @Override
              public void accept(Throwable throwable) throws Exception {
                getCacheWeather(city);
              }
            });
    disposables.add(weatherFromNetDisposable);
  }

  private String getLocationCity(PascLocationData location) {
    if (location != null) {
      String city = location.getCity();
      String district = location.getDistrict();
      if (TextUtils.isEmpty(district) || district.endsWith(PascLocationData.LOC_AREA)) {
        if (!TextUtils.isEmpty(city)) {
          int length = city.length();
          return length > 2 && city.endsWith(PascLocationData.LOC_CITY) ? city.substring(0,
              length - 1) : city;
        }
      } else {
        if (!TextUtils.isEmpty(district)) {
          int length = district.length();
          return length > 2 && district.endsWith(PascLocationData.LOC_COUNTY) ? district.substring(
              0, length - 1) : district;
        }
      }
    }
    return null;
  }

  private void showWeatherLoading(boolean isShow) {
    if (isShow) {
      pBarWeatherLoding.setVisibility(View.VISIBLE);
      weatherArea.setVisibility(View.GONE);
      noWeather.setVisibility(View.GONE);
    } else {
      pBarWeatherLoding.setVisibility(View.GONE);
      weatherArea.setVisibility(View.VISIBLE);
      noWeather.setVisibility(View.GONE);
    }
  }

  private void showMainPageWeatherInfo(MainPageWeatherInfo weatherInfo) {
    this.weatherInfo = weatherInfo;
    pBarWeatherLoding.setVisibility(View.GONE);

    boolean hasWeatherInfo = weatherInfo != null;
    if (hasWeatherInfo) {
      noWeather.setVisibility(View.GONE);
      weatherArea.setVisibility(View.VISIBLE);

      // 描述
      weatherState.setVisibility(View.VISIBLE);
      weatherState.setText(weatherInfo.cond_txt);

      // 城市
      tvWeatherCity.setVisibility(View.VISIBLE);
      String mainPageShowName = currentCity.getMainPageShowName();
      mainPageShowName = mainPageShowName == null ? "" : mainPageShowName;
      if (mainPageShowName.length() > 4) {
        mainPageShowName = mainPageShowName.substring(0, 3) + "...";
      }
      tvWeatherCity.setText(mainPageShowName);

      // 温度
      String weatherTmp = weatherInfo.tmp;
      if (!TextUtils.isEmpty(weatherTmp) && !weatherTmp.endsWith(WEATHER_TEMP_UNIT)) {
        tvWeatherTemp.setText(weatherTmp + WEATHER_TEMP_UNIT);
      } else {
        tvWeatherTemp.setText(weatherTmp);
      }
    } else {
      noWeather.setVisibility(View.VISIBLE);
      weatherArea.setVisibility(View.GONE);
    }
  }

  protected int getWeatherStateIcon(MainPageWeatherInfo weatherInfo) {
    return R.drawable.workspace_ic_weather_fine;
  }

  private void getCacheWeather(final WeatherCity city) {
    Disposable weatherFromCacheDisposable =
        Flowable.create(new FlowableOnSubscribe<MainPageWeatherInfo>() {
          @Override
          public void subscribe(FlowableEmitter<MainPageWeatherInfo> emitter) throws Exception {
            try {
              MainPageWeatherInfo info = WorkspaceBiz.getInstance().getWeatherInfoFromCache(city);
              if (info == null) {
                emitter.tryOnError(new Throwable("无内容"));
              } else {
                emitter.onNext(info);
              }
            } catch (Exception e) {
              e.printStackTrace();
              emitter.tryOnError(new Throwable("发生异常"));
            } finally {
              emitter.onComplete();
            }
          }
        }, BackpressureStrategy.BUFFER)
            .compose(this.<MainPageWeatherInfo>bindUntilEvent(FragmentEvent.DESTROY_VIEW))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<MainPageWeatherInfo>() {
              @Override
              public void accept(MainPageWeatherInfo weatherInfo) throws Exception {
                showMainPageWeatherInfo(weatherInfo);
              }
            }, new Consumer<Throwable>() {
              @Override
              public void accept(Throwable throwable) throws Exception {
                showMainPageWeatherInfo(null);
              }
            });
    disposables.add(weatherFromCacheDisposable);
  }

  @Override
  protected void updateData() {
    super.updateData();

    //更新天气数据
    updateWeatherData();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();

//    LbsManager.getInstance().stopLocation(0, mPascLocationListener);
  }

  private void checkLocPermission() {
    PascLog.d(TAG, "正在判断是否有定位权限");
    Disposable disposable = PermissionUtils.requestWithDialog(getActivity(), new String[] {
        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
    })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Boolean>() {
          @Override
          public void accept(Boolean aBoolean) throws Exception {
            if (aBoolean) {
              PascLog.d(TAG, "当前应用有定位权限");
              showWeatherLoading(true);
//              LbsManager.getInstance().doLocation(0, mPascLocationListener);
            } else {
              PascLog.d(TAG, "当前应用无定位权限");
              getSimpleMainPageWeatherInfo(currentCity);
            }
          }
        });
    disposables.add(disposable);
  }

  private void updateWeatherByCityChanged() {
    WeatherCity newCity = WorkspaceBiz.getInstance().getWeatherCity();
    if (weatherInfo == null || !newCity.equals(currentCity)) {
      currentCity = newCity;
      getCacheWeather(newCity);
    }
  }

  /**
   * 点击天气.
   *
   * @param view 视图.
   */
  protected void onClickWeather(View view) {
    Bundle bundle = new Bundle();
    String cityName = currentCity.getCityName();
    cityName = cityName == null ? "" : cityName;
    bundle.putString("city", cityName);
    BaseJumper.jumpBundleARouter("/weather/detail/main", bundle);
  }

  public void onViewClicked(View view) {
    int id = view.getId();
    if (id == R.id.tv_search) {
      onClickSearch(view);
    } else if (id == R.id.rl_notification) {
      onClickNotification(view);
    }
  }

  // 点击消息
  protected void onClickNotification(View view) {
    //跳转消息页
    UserProxy.getInstance().checkLoginWithCallBack(getActivity(), new Runnable() {
          @Override
          public void run() {
            BaseJumper.jumpARouter("/message/center/main");
          }
        }
    );
  }

  // 点击搜索
  protected void onClickSearch(View view) {
    //跳转搜索页
    BaseJumper.jumpARouter("/search/main/main");
  }

  @Override
  public void onStart() {
    super.onStart();
    updateWeatherByCityChanged();
  }

  private void checkPermissions() {
    checkLocPermission();
  }

  // 设置工具栏状态，工具栏的状态是根据不透明度值来决定的
  private void setToolbarState(int alpha) {
    if (toolbarBgAlpha != alpha) {
      toolbarBgAlpha = alpha;

      boolean standard = toolbarBgAlpha >= 200;
      toolbarBg.setAlpha(toolbarBgAlpha);
      weatherArea.setAlpha(255 - toolbarBgAlpha);

      ivNotification.setSelected(standard);
      askbobIV.setSelected(standard);
      tvSearch.setSelected(standard);
      FragmentActivity activity = getActivity();
      boolean translucentStatus = BarUtils.isTranslucentStatus(activity);
      if (translucentStatus) {
        BarUtils.setStatusBarLightMode(activity, standard);
      }

      float percent =
          searchWidthPercent + (float) ((toolbarBgAlpha * 1.0 / 255 * 1.0) * weatherWidthPercent);
      setTvSearchLayoutParams(percent);
    }
  }

  @Override
  protected void setupMainView() {
    super.setupMainView();

    ivNotification.setSelected(false);
      askbobIV.setSelected(false);
    Disposable disposable = RxRecyclerView.scrollEvents(configurableRecyclerView)
        .subscribe(new Consumer<RecyclerViewScrollEvent>() {
          @Override
          public void accept(RecyclerViewScrollEvent recyclerViewScrollEvent) {
            PascLog.d(TAG, "recyclerViewScrollEvent");
            int itemCount = getItemCount();
            if (itemCount <= 0) {
              return;
            }
            LinearLayoutManager layoutManager =
                (LinearLayoutManager) configurableRecyclerView.getLayoutManager();
            final int position = layoutManager.findFirstVisibleItemPosition();
            if (position == RecyclerView.NO_POSITION) return;
            boolean isFirst = position == 0;
            if (isFirst) {
              View firstView = layoutManager.findViewByPosition(position);
              int toolbarHeight = toolbar.getHeight();
              PascLog.d(TAG, "toolbarHeight=" + toolbarHeight);
              PascLog.d(TAG, "statusBarHeight=" + statusBarHeight);
              int firstViewHeight = firstView.getHeight(); // 首个视图高度
              PascLog.d(TAG, "firstViewHeight=" + firstViewHeight);
              if (maxBannerOffset == 0
                  && firstViewHeight != 0
                  && statusBarHeight != 0 && toolbarHeight > 0) {
                maxBannerOffset = firstViewHeight - toolbarHeight - statusBarHeight;
              }
              if (maxBannerOffset != 0) {
                int alpha = (int) (255 * (1 - ((Math.min(maxBannerOffset,
                    Math.max(0, firstView.getBottom() - toolbarHeight)))
                    / maxBannerOffset)));
                setToolbarState(alpha);
              } else {
                if (isFirstDraw) {
                  isFirstDraw = false;
                }
              }
            } else {
              setToolbarState(255);
            }
          }
        });
    disposables.add(disposable);
  }

  private void setupToolbar() {
    //获取status_bar_height资源的ID
    int resourceId =
        getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
      //根据资源ID获取响应的尺寸值
      statusBarHeight = getResources().getDimensionPixelSize(resourceId);
    }

    boolean translucentStatus = BarUtils.isTranslucentStatus(getActivity());
    if (translucentStatus) {
      FrameLayout.LayoutParams params =
          (FrameLayout.LayoutParams) toolbar.getLayoutParams();
      params.height = params.height + statusBarHeight;
      toolbar.setLayoutParams(params);
      toolbar.setPadding(0, statusBarHeight, 0, 0);
    }

    toolbarBg = ContextCompat.getDrawable(getActivity(), R.drawable.workspace_bg_main_toolbar);
    toolbarBg.setAlpha(toolbarBgAlpha);
    ViewCompat.setBackground(toolbar, toolbarBg);
  }

  private void setTvSearchLayoutParams(float percent) {
    PercentFrameLayout.LayoutParams layoutParams =
        (PercentFrameLayout.LayoutParams) tvSearch.getLayoutParams();
    layoutParams.getPercentLayoutInfo().widthPercent = percent;
    tvSearch.setLayoutParams(layoutParams);
  }

  private void setNotLocation() {
    if (weatherInfo != null) {
      return;
    }
    tvWeatherTemp.setText("暂无定位");
  }

  private void setNotWeatherInfo() {
    if (weatherInfo != null) {
      return;
    }
    tvWeatherTemp.setText("暂无天气");
  }

  private void setupModule() {
    setUserMsgInfo();
  }

  protected void setupAskbob(){
    if (isAskBobShow()){
      PascImageLoader.getInstance().loadImageRes(R.drawable.workspace_askbob_icon, askbobIV);
      askbobIV.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          onAskbobClick();
        }
      });
    }else {
      askbobIV.setVisibility(View.GONE);
    }
  }

  /**
   * 获取用户通知信息
   */
  private void setUserMsgInfo() {
    updateUnReadCountInternal(null);
  }

  /**
   * 更新未读消息数量.
   *
   * @param count 数量.
   */
  public void updateUnReadCount(int count) {
    updateUnReadCountInternal(count);
  }

  private void updateUnReadCountInternal(Integer count) {
    if (count == null) {
      count = getUnReadCount();
    }
    unReadNumber.setVisibility(View.GONE);
    unReadPoint.setVisibility(View.GONE);

    UnreadMessageMode unreadMessageMode = getUnreadMessageMode();
    PascLog.d(TAG, "当前未读消息展示模式：" + unreadMessageMode);
    if (UnreadMessageMode.NUMBER.equals(unreadMessageMode)) {
      // 数字模式
      if (unReadNumber != null) {
        if (count > 0) {
          unReadNumber.setVisibility(View.VISIBLE);
          if (count > 99) {
            unReadNumber.setText("99+");
          } else {
            unReadNumber.setText(String.valueOf(count));
          }
        } else {
          unReadNumber.setVisibility(View.GONE);
        }
      }
    } else if (UnreadMessageMode.POINT.equals(unreadMessageMode)) {
      // 点模式
      if (unReadPoint != null) {
        if (count > 0) {
          unReadPoint.setVisibility(View.VISIBLE);
        } else {
          unReadPoint.setVisibility(View.GONE);
        }
      }
    }
  }

  public static enum UnreadMessageMode {
    POINT, NUMBER
  }

  protected UnreadMessageMode getUnreadMessageMode() {
    return UnreadMessageMode.POINT;
  }

  private int getUnReadCount() {
    int unReadCount = 0;
    boolean hasLoggedOn = UserProxy.getInstance().hasLoggedOn();
    if (hasLoggedOn) {
      unReadCount = WorkspaceBiz.getInstance().getUnReadMessageCount();
    }
    return unReadCount;
  }

  // 刷新消息数量事件
  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onEvent(RefreshMessageCountEvent event) {
    setUserMsgInfo();
  }

  public int getToolbarBgAlpha() {
    return toolbarBgAlpha;
  }

  public void setToolbarBgAlpha(int toolbarBgAlpha) {
    this.toolbarBgAlpha = toolbarBgAlpha;
  }

  /**
   * default search hint value if not empty
   * @return search hint
   */
  public abstract String obtainSearchHint();

  /**
   * set search hint
   * @param searchHint
   */
  public void setSearchHint(String searchHint) {
    if (null == tvSearch) {
      return;
    }
    tvSearch.setHint(searchHint);
  }

  /**
   * 是否显示askbob，给各个项目自己配置，需要各个项目复写自己实现
   * @return
   */
  protected boolean isAskBobShow(){
    return true;
  }

  /**
   * 获取askbob图标，默认不给，需要各个项目复写自己实现
   * @return
   */
  protected String getAskBobUrl(){
    return null;
  }

  /**
   * 点击了askbob图标
   */
  protected void onAskbobClick(){
    if (TextUtils.isEmpty(getAskBobUrl())){
      Log.e(TAG,"askbob'url is empty, please replace method getAskBobUrl() return askbob'url");
      return;
    }
    WebStrategy webStrategy = new WebStrategy();
    webStrategy.url = getAskBobUrl();
    PascHybrid.getInstance().start(mContext, getAskBobUrl());
  }

  @Override
  protected boolean isOnlyUseLocalConfig() {
    return false;
  }
}
