package com.pasc.business.mine.tangram;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.flyco.roundview.RoundTextView;
import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewScrollEvent;
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView;
import com.pasc.business.mine.R;
import com.pasc.business.mine.activity.MeProfileActivity;
import com.pasc.business.mine.activity.MyCreditScoreActivity;
import com.pasc.business.mine.activity.SettingsActivity;
import com.pasc.business.mine.util.Constants;
import com.pasc.business.mine.util.EventConstants;
import com.pasc.business.mine.util.EventUtils;
import com.pasc.business.user.PascUserCertListener;
import com.pasc.business.user.PascUserConfig;
import com.pasc.business.user.PascUserLoginListener;
import com.pasc.business.user.PascUserManager;
import com.pasc.business.workspace.BaseConfigurableFragment;
import com.pasc.lib.base.AppProxy;
import com.pasc.lib.base.event.BaseEvent;
import com.pasc.lib.base.user.IUserInfo;
import com.pasc.lib.base.user.IUserManager;
import com.pasc.lib.base.util.AppUtils;
import com.pasc.lib.base.util.SPUtils;
import com.pasc.lib.base.util.ScreenUtils;
import com.pasc.lib.log.PascLog;
import com.pasc.lib.router.BaseJumper;
import com.pasc.lib.widget.dialognt.SelectReminderDialog;
import com.pasc.lib.widget.tangram.BasePascCell;
import com.pasc.lib.widget.tangram.model.PersonalHeaderModel;
import com.pasc.lib.widget.tangram.HorizontalScrollCell;
import com.pasc.lib.widget.tangram.LeftIconRightTwoTextCell;
import com.pasc.lib.widget.tangram.LeftIconRightTwoTextView;
import com.pasc.lib.widget.tangram.MineCardHeaderCell;
import com.pasc.lib.widget.tangram.MineCardHeaderView;
import com.pasc.lib.widget.tangram.MineHorizontalDividerCell;
import com.pasc.lib.widget.tangram.MineHorizontalDividerView;
import com.pasc.lib.widget.tangram.PersonalHeaderCell;
import com.pasc.lib.widget.tangram.PersonalHeaderView;
import com.pasc.lib.widget.tangram.SettingItemCell;
import com.pasc.lib.widget.tangram.SettingItemView;
import com.pasc.lib.widget.tangram.TangramEngineBuilder;
import com.pasc.lib.widget.tangram.TopIconBottomTextCell;
import com.pasc.lib.widget.tangram.TopIconBottomTextView;
import com.pasc.lib.widget.tangram.model.DataSourceItem;
import com.pasc.lib.workspace.handler.CardLoadHandler;
import com.pasc.lib.workspace.handler.event.GoToMyAffairEvent;
import com.pasc.lib.workspace.handler.event.GoToMyCardEvent;
import com.pasc.lib.workspace.handler.event.GoToMyFollowEvent;
import com.pasc.lib.workspace.handler.event.GoToMyPayEvent;
import com.pasc.lib.workspace.handler.event.GoToMyRegisterEvent;
import com.pasc.lib.workspace.handler.event.GoToPraiseEvent;
import com.pasc.lib.workspace.handler.event.GoToShareEvent;
import com.pasc.lib.workspace.handler.event.SimpleClickEvent;
import com.tmall.wireless.tangram.TangramEngine;
import com.tmall.wireless.tangram.core.adapter.GroupBasicAdapter;
import com.tmall.wireless.tangram.dataparser.concrete.Card;
import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.support.async.AsyncLoader;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Copyright (C) 2016 pasc Licensed under the Apache License, Version 2.0 (the "License");
 *
 * @author chendaixi947
 * @version 1.0
 * @date 2018/10/20
 * <p>
 * 我的Fragment，使用Tangram技术.
 */
public class BaseMinePageFragment extends BaseConfigurableFragment {

  private static final String SEX_MALE = "1"; // 男
  private static final String SEX_FEMALE = "2"; // 女

  private LinearLayout mToolBar;
  private TextView mTitle;
  private View mToolBarDivider;
  private ImageView mSettingIv;
  private RoundTextView mRedDot;

  private int mStatusBarHeight;
  private int mToolbarBgAlpha = 0;
  private Drawable mToolbarBg;
  private double maxOffset;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View rootView = super.onCreateView(inflater, container, savedInstanceState);

    initView(rootView);
    return rootView;
  }

  @Override protected int getLayoutId() {
    return R.layout.mine_fragment_mine2;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setupToolbar();

    setListener();
  }

  @Override protected void initCardLoadHandlers(HashMap<String, CardLoadHandler> cardLoadHandlers) {
    super.initCardLoadHandlers(cardLoadHandlers);

    cardLoadHandlers.put("getPersonalHeader", new CardLoadHandler() {
      @Override
      public void loadData(TangramEngine engine, Card card, AsyncLoader.LoadedCallback callback) {
        getPersonalHeader(engine, card, callback);
      }
    });
  }

  protected void getPersonalHeader(TangramEngine engine, Card card,
      AsyncLoader.LoadedCallback callback) {
    PersonalHeaderModel personalInfo = getPersonalHeaderModel();
    BaseCell personalHeader = card.getCellById("personalHeader");
    ((BasePascCell) personalHeader).setDataSource(personalInfo);
    engine.update(personalHeader);
    callback.finish();
  }

  protected PersonalHeaderModel getPersonalHeaderModel() {
    PersonalHeaderModel personalHeaderModel = new PersonalHeaderModel();
    // 未登录的信息
    personalHeaderModel.setPersonName("点击登录");
    personalHeaderModel.setAuthArrowIconVisible(true);
    personalHeaderModel.setAuthIconVisible(false);
    personalHeaderModel.setAuthTitle("未实名");
    personalHeaderModel.setImgRes(R.drawable.mine_ic_head_default_logged);
    personalHeaderModel.setScoreDesc("0分");

    IUserManager userManager = AppProxy.getInstance().getUserManager();
    if (userManager != null) {
      boolean login = userManager.isLogin();
      if (login) {
        personalHeaderModel.setPersonName(getPersonName(userManager));
        boolean certified = isCertified(userManager);
        personalHeaderModel.setAuthTitle(certified ? "已实名" : "未实名");
        personalHeaderModel.setAuthIconVisible(certified);
        personalHeaderModel.setAuthArrowIconVisible(!certified);
        personalHeaderModel.setImgUrl(getHeadImg(userManager));
        personalHeaderModel.setImgRes(getHeadImgRes(userManager));
      }
    }

    return personalHeaderModel;
  }

  protected int getHeadImgRes(IUserManager userManager) {
    if (userManager != null) {
      IUserInfo userInfo = userManager.getUserInfo();
      if (userInfo != null) {
        String sex = userInfo.getSex();
        if (SEX_MALE.equals(sex)) {
          return R.drawable.mine_ic_default_head_male;
        } else if (SEX_FEMALE.equals(sex)) {
          return R.drawable.mine_ic_default_head_female;
        }
      }
    }
    return R.drawable.mine_ic_head_default_logged;
  }

  protected String getHeadImg(IUserManager userManager) {
    if (userManager != null) {
      IUserInfo userInfo = userManager.getUserInfo();
      if (userInfo != null) {
        return userInfo.getHeadImg();
      }
    }
    return null;
  }

  public static boolean isCertified(IUserManager userManager) {
    if (hasLoggedOn(userManager)) {
      boolean certified = userManager.isCertified();
      return certified;
    }
    return false;
  }

  public static String getPersonName(IUserManager userManager) {
    if (hasLoggedOn(userManager)) {
      IUserInfo userInfo = userManager.getUserInfo();
      if (userInfo != null) {
        boolean isChanged = "1".equals(userInfo.getNickNameStatus());
        if (isChanged) {
          return userInfo.getNickName();
        }
        if (userManager.isCertified()) {
          return maskRealName(userInfo.getUserName());
        }
        return userInfo.getNickName();
      }
    }
    return "点击登录";
  }

  public static String maskRealName(String username) {
    String maskName = "";
    if (!TextUtils.isEmpty(username)) {
      maskName =
          username.length() > 2 ? username.substring(0, 1) + "*" + username.substring(
              username.length() - 1, username.length())
              : "*" + username.substring(username.length() - 1, username.length());
    }
    return maskName;
  }

  public static boolean hasLoggedOn(IUserManager userManager) {
    if (userManager != null) {
      return userManager.isLogin();
    }
    return false;
  }

  public static boolean hasLoggedOn() {
    IUserManager userManager = AppProxy.getInstance().getUserManager();
    return hasLoggedOn(userManager);
  }

  @Override protected void onCellClick(HashMap<String, String> params) {
    super.onCellClick(params);
    String eventId = params.get("eventId");

    if ("onClickPersonalHeaderImage".equals(eventId) || "onClickPersonalHeaderPersonName".equals(eventId)) {
      if (hasLoggedOn()) {
        startActivity(new Intent(getActivity(), MeProfileActivity.class));
      }else {
        PascUserManager.getInstance().toLogin(null);
      }
    } else if ("onClickPersonalHeaderAuth".equals(eventId)) {
      if (hasLoggedOn()) {
        PascUserManager.getInstance().toCertification(PascUserConfig.CERTIFICATION_TYPE_ALL,null);
      }
    } else if ("onClickPersonalHeaderScore".equals(eventId)) {
      // 跳转个人信用页
      if (hasLoggedOn()) {
        IUserInfo userInfo = AppProxy.getInstance().getUserManager().getUserInfo();
        if (PascUserManager.getInstance().isCertification()) {
          //已认证
          MyCreditScoreActivity.start(getContext());
        } else {
          SelectReminderDialog dialog = new SelectReminderDialog(getContext()
          );
          dialog.setCancelText("取消");
          dialog.setCancelTextColor(R.color.pasc_secondary_text);
          dialog.setConfirmText("去认证");
          dialog.setConfirmTextColor(R.color.blue_27a5f9);
          dialog.setmTvContext("请先进行身份认证再查询个人信用");
          dialog.setCanceledOnTouchOutside(false);
          dialog.setOnSelectedListener(
              new SelectReminderDialog.OnSelectedListener() {
                @Override
                public void onSelected() {
                  PascUserManager.getInstance().toCertification(PascUserConfig.CERTIFICATION_TYPE_ALL_AND_FINISH_WHEN_SUCCESS, new PascUserCertListener() {
                    @Override
                    public void onCertificationSuccess() {
                      MyCreditScoreActivity.start(getContext());
                    }

                    @Override
                    public void onCertificationFailed() {

                    }

                    @Override
                    public void onCertificationCancled() {

                    }
                  });
                }

                @Override
                public void onCancel() {
                }
              });
          dialog.show();
        }
      }
    }
  }

  private void initView(View rootView) {
    mToolBar = rootView.findViewById(R.id.mine_page_toolBar);
    mTitle = rootView.findViewById(R.id.real_user_name);
    mToolBarDivider = rootView.findViewById(R.id.personal_title_line);
    mSettingIv = rootView.findViewById(R.id.personal_center_settings);
    mRedDot = rootView.findViewById(R.id.tv_red_dot);
  }

  private void setListener() {
    mSettingIv.setOnClickListener(onClickListener);
  }

  private View.OnClickListener onClickListener = v -> {
    if (v.getId() == R.id.personal_center_settings) {
      // 设置页
      startActivity(new Intent(getActivity(), SettingsActivity.class));
      EventUtils.onEvent(EventUtils.E_PERSONAL_INFO, EventUtils.L_SETTING);
    }
  };

  private void setupToolbar() {
    if (!showToolbar()){//如果需要隐藏toolbar
      mToolBar.setVisibility(View.GONE);
      return;
    }
    mToolBar.post(() -> {
      mStatusBarHeight = ScreenUtils.getStatusBarHeight(Objects.requireNonNull(getContext()));
      FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mToolBar.getLayoutParams();
      params.height = params.height + mStatusBarHeight;
      mToolBar.setLayoutParams(params);
      mToolBar.setPadding(0, mStatusBarHeight, 0, 0);
    });
    mToolbarBg = ContextCompat.getDrawable(Objects.requireNonNull(getActivity()),
        R.drawable.mine_bg_person_toolbar);
    if (mToolbarBg != null) {
      mToolbarBg.setAlpha(mToolbarBgAlpha);
    }
    mToolBarDivider.setVisibility(View.INVISIBLE);
    ViewCompat.setBackground(mToolBar, mToolbarBg);
    mTitle.setText("");
    if (showSetting()){
      mSettingIv.setVisibility(View.VISIBLE);
    }else {
      mSettingIv.setVisibility(View.GONE);
    }
    if (showPinnedTitle()) {
      Disposable disposable = RxRecyclerView.scrollEvents(configurableRecyclerView)
          .subscribe(new Consumer<RecyclerViewScrollEvent>() {
            @Override
            public void accept(RecyclerViewScrollEvent recyclerViewScrollEvent) {

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
                int toolbarHeight = mToolBar.getHeight();
                if (toolbarHeight > 0) {
                  maxOffset = firstView.getTop();
                }
                if (maxOffset < 0) {
                  int alpha = (int) (255 * (Math.max(0,
                      Math.min(1, Math.abs(maxOffset) * 1.0f / toolbarHeight))));
                  PascLog.d(TAG, "recyclerViewScrollEvent alpha " + alpha);
                  setToolbarState(alpha);
                } else {
                  setToolbarState(0);
                }
              } else {
                setToolbarState(255);
              }
            }
          });
      disposables.add(disposable);
    }
  }

  private void setToolbarState(int alpha) {
    if (mToolbarBgAlpha != alpha) {
      mToolbarBgAlpha = alpha;

      boolean standard = mToolbarBgAlpha >= 200;
      mToolbarBg.setAlpha(mToolbarBgAlpha);

      mTitle.setText(
          standard ? (TextUtils.isEmpty(getTitle()) ? getString(R.string.mine_title) : getTitle())
              : "");
      mSettingIv.setSelected(standard);
      //ivNotification.setSelected(standard);
      //tvSearch.setSelected(standard);

    }
  }

  /**
   * 根据协议地址构建一个事件对象.
   *
   * @param url 协议地址.
   * @return 事件对象.
   */
  private Object buildMyEvent(String url) {
    String prefix = "event://";
    if (url.startsWith(prefix + "GoToMyAffair")) {
      return new GoToMyAffairEvent();
    } else if (url.startsWith(prefix + "GoToMyCard")) {
      return new GoToMyCardEvent();
    } else if (url.startsWith(prefix + "GoToMyFollow")) {
      return new GoToMyFollowEvent();
    } else if (url.startsWith(prefix + "GoToMyPay")) {
      return new GoToMyPayEvent();
    } else if (url.startsWith(prefix + "GoToMyRegister")) {
      return new GoToMyRegisterEvent();
    } else if (url.startsWith(prefix + "GoToPraise")) {
      return new GoToPraiseEvent();
    } else if (url.startsWith(prefix + "GoToShare")) {
      return new GoToShareEvent();
    } else {
      return url.startsWith(prefix + "SimpleClick") ? new SimpleClickEvent() : null;
    }
  }

  @Override protected void addCell(TangramEngineBuilder builder) {
    builder.registerCell("component-leftIconRightTwoText", LeftIconRightTwoTextCell.class,
        LeftIconRightTwoTextView.class)
        .registerCell("component-settingItem", SettingItemCell.class, SettingItemView.class)
        .registerCell("component-horizontalDivider", MineHorizontalDividerCell.class,
            MineHorizontalDividerView.class)
        .registerCell("component-personalHeader", PersonalHeaderCell.class,
            PersonalHeaderView.class)
        .registerCell("component-mineCardHeader", MineCardHeaderCell.class,
            MineCardHeaderView.class)
        .registerCell("component-topIconBottomText", TopIconBottomTextCell.class,
            TopIconBottomTextView.class);
  }

  public String getConfigId() {
    return "pasc.smt.minepage";
  }

  /**
   * 刷新我的卡包
   */
  public <T extends DataSourceItem> void refreshCardView(List<T> cardItems) {
    HorizontalScrollCell mCardCell =
        (HorizontalScrollCell) getEngine().findCellById("personal_my_card");
    if (mCardCell != null) {
      mCardCell.setDataSource(cardItems);
      getEngine().update(mCardCell);
    }
  }

  /**
   * 退出登录刷新界面
   */
  @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
  public void onEventLoginRefresh(
      BaseEvent event) {
    if (EventConstants.USER_LOGIN_STATUS.equals(event.getTag())) {
      refreshView();
      PascUserManager.getInstance().toLogin(null);
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onCertificationRefresh(
      BaseEvent event) {
    if (EventConstants.USER_CERTIFICATE_NOT.equals(event.getTag())
        || EventConstants.USER_CERTIFICATE_SUCCEED.equals(event.getTag())
        || EventConstants.USER_CERTIFICATE_FINISH.equals(event.getTag())) {
      refreshView();
    }
  }

  /**
   * 登录刷新界面
   */
  @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
  public void onEventRefresh(
      BaseEvent event) {
    if (EventConstants.USER_LOGIN_SUCCEED.equals(event.getTag())) {
      refreshView();
    }
  }

  /**
   * 当用户在用户资料界面填写了资料后刷新咱们页面
   */
  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onEventMsg(BaseEvent event) {
    if (EventConstants.USER_MODIFY_USER.equals(event.getTag())) {
      refreshView();
    }
  }

  /**
   * token失效 刷新状态
   */
  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onTokenFail(BaseEvent event) {
    if (EventConstants.USER_INVALID_TOKEN.equals(event.getTag())
        || EventConstants.MINE_KICKOFF_TOKEN.equals(event.getTag())
        || EventConstants.MINE_INVALID_TOKEN.equals(event.getTag())) {
      refreshView();
    }
  }

  /**
   * token失效 刷新状态
   */
  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onUpdate(BaseEvent event) {
    if (EventConstants.USER_UPDATE_MSG.equals(event.getTag())) {
      refreshView();
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMyPraiseEvent(GoToPraiseEvent event) {
    // 跳转去好评
    AppUtils.goAppMarkets(getActivity(), getContext().getPackageName());
  }

  /**
   * 刷新页面
   */
  private void refreshView() {
    //刷新顶部用户信息
    TangramEngine engine = getEngine();
    if (engine != null) {
      GroupBasicAdapter<Card, ?> groupBasicAdapter = engine.getGroupBasicAdapter();
      if (groupBasicAdapter != null) {
        List<Card> groups = groupBasicAdapter.getGroups();
        if (groups != null) {
          for (Card card : groups) {
            card.loading = false;
            card.loaded = false;
          }
        }
      }
      engine.onScrolled();
    }
    showRedDot();
  }

  /**
   * 设置入口红点相关显示
   */
  private void showRedDot() {
    boolean show = (boolean) SPUtils.getInstance().getParam(Constants.IS_BROWSE_SERVICE, false)
        && AppProxy.getInstance().getUserManager().isLogin();
    mRedDot.setVisibility(show ? View.VISIBLE : View.GONE);
  }

  public boolean showPinnedTitle() {
    return false;
  }

  public boolean showSetting(){
    return true;
  }

  public boolean showToolbar(){
    return true;
  }

  public String getTitle() {
    return "";
  }
}
