package com.pasc.business.mine.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pasc.business.mine.R;
import com.pasc.business.mine.config.SettingManager;
import com.pasc.business.mine.util.EventConstants;
import com.pasc.business.mine.util.EventUtils;
import com.pasc.business.mine.util.FixMemLeakUtil;
import com.pasc.business.mine.util.RouterTable;
import com.pasc.business.mine.widget.ChooseOptionDialog;
import com.pasc.business.mine.widget.IconLoadingDialog;
import com.pasc.business.ota.UpdateManager;
import com.pasc.business.user.PascUserLoginListener;
import com.pasc.business.user.PascUserManager;
import com.pasc.lib.base.AppProxy;
import com.pasc.lib.base.event.BaseEvent;
import com.pasc.lib.base.user.IUserInfo;
import com.pasc.lib.base.user.IUserManager;
import com.pasc.lib.base.util.CacheUtils;
import com.pasc.lib.base.util.ToastUtils;
import com.pasc.lib.ota.callback.UpdateCallBack;
import com.pasc.lib.router.BaseJumper;
import com.pasc.lib.router.interceptor.BaseRouterTable;
import com.pasc.lib.router.interceptor.InterceptorUtil;
import com.pasc.lib.widget.toolbar.PascToolbar;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SettingsActivity extends BaseStatusActivity {
  public static final String LAYOUT_TYPE = "layoutType";
  LinearLayout mLlLogined;
  TextView mTvLogout;
  TextView tvUnregister;
  PascToolbar mCommonTitleView;
  TextView tvCacheSize;
  private Context mContext;
  private View viewUserInfo, viewClearCache, viewAbout, viewAccountSafety, viewPwdReset, viewOption,
      viewRedDot, viewPermission;
  private TextView tvUpdateHint;
  private ChooseOptionDialog logoutDialog, clearCacheDialog;
  private CompositeDisposable disposables = new CompositeDisposable();

  @Override
  protected int layoutResId() {
    return R.layout.mine_activity_setting;
  }

  @Override
  protected void onInit(@Nullable Bundle bundle) {
    initView();
    EventBus.getDefault().register(this);
    mContext = this;
    checkUpdate(false, false);
  }

  private void initView() {
    mLlLogined = findViewById(R.id.ll_logined);
    mTvLogout = findViewById(R.id.tv_logout);
    mCommonTitleView = findViewById(R.id.ctv_title);
    tvCacheSize = findViewById(R.id.tv_cache_size);
    viewUserInfo = findViewById(R.id.tv_user_info);
    viewClearCache = findViewById(R.id.ll_clear_cache);
    viewAbout = findViewById(R.id.tv_about);
    viewAccountSafety = findViewById(R.id.tv_account_safety);
    viewPwdReset = findViewById(R.id.tv_pwd_reset);
    viewOption = findViewById(R.id.tv_Opinion);
    viewRedDot = findViewById(R.id.iv_red_dot);
    tvUpdateHint = findViewById(R.id.tv_update_hint);
    tvUnregister = findViewById(R.id.tv_unregister);
    viewPermission = findViewById(R.id.tv_permission);

    viewUserInfo.setOnClickListener(onClickListener);
    viewClearCache.setOnClickListener(onClickListener);
    viewAbout.setOnClickListener(onClickListener);
    viewPermission.setOnClickListener(onClickListener);
    findViewById(R.id.tv_logout).setOnClickListener(onClickListener);
    viewAccountSafety.setOnClickListener(onClickListener);
    viewOption.setOnClickListener(onClickListener);
    viewPwdReset.setOnClickListener(onClickListener);
    tvUnregister.setOnClickListener(onClickListener);
    mCommonTitleView.addCloseImageButton().setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        finish();
      }
    });
    viewUserInfo.setVisibility(
        SettingManager.getInstance().showProfile() ? View.VISIBLE : View.GONE);
    viewClearCache.setVisibility(
        SettingManager.getInstance().showClearCache() ? View.VISIBLE : View.GONE);
    viewAbout.setVisibility(SettingManager.getInstance().showAbout() ? View.VISIBLE : View.GONE);
    viewAccountSafety.setVisibility(
        SettingManager.getInstance().showSecurity() ? View.VISIBLE : View.GONE);
    viewPwdReset.setVisibility(
        SettingManager.getInstance().showPwdReset() ? View.VISIBLE : View.GONE);
    viewOption.setVisibility(
        SettingManager.getInstance().showFeedback() ? View.VISIBLE : View.GONE);
    viewPermission.setVisibility(
        SettingManager.getInstance().showPermission() ? View.VISIBLE : View.GONE);
  }

  /**
   * 接收到实名认证成功后关闭掉自身
   */
  @Subscribe(threadMode = ThreadMode.MAIN) public void onCertification(BaseEvent event) {
    if (EventConstants.USER_CERTIFICATE_SUCCEED.equals(event.getTag())) {
      finish();
    }
  }

  @Override protected void onStart() {
    super.onStart();
    setView();
  }

  private void setView() {
    //boolean hasLogin = AppProxy.getInstance().getUserManager().isLogin();
    //mLlLogined.setVisibility(hasLogin ? View.VISIBLE : View.GONE);

    refreshCacheSize();
  }

  @Override protected void onResume() {
    super.onResume();
    boolean hasLogin = hasLoggedOn();
    mTvLogout.setVisibility(hasLogin ? View.VISIBLE : View.GONE);
    tvUnregister.setVisibility(
        SettingManager.getInstance().showUnregister() && hasLogin ? View.VISIBLE : View.GONE);
  }

  private void refreshCacheSize() {
    try {
      tvCacheSize.setText(CacheUtils.getTotalCacheSize(this));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  View.OnClickListener onClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      int i = v.getId();
      if (i == R.id.tv_user_info) {
        //if (UserRouterBiz.fetchLoginStateServices().isNeedLogin(SettingsActivity.this, v)) {

        HashMap<String, String> map = new HashMap<>();
        map.put(EventUtils.KEY_SETING, EventUtils.VALUE_USER_DATA);
        EventUtils.onEvent(EventUtils.E_PERSONAL_INFO, EventUtils.L_SETTING, map);

        if (!hasLoggedOn()) {
          Bundle bundle = new Bundle();
          bundle.putBoolean(BaseRouterTable.BundleKey.KEY_NEED_LOGIN, true);
          BaseJumper.jumpBundleARouter(RouterTable.MAIN_PROFILE, bundle);
          return;
        }
        startActivity(new Intent(SettingsActivity.this, MeProfileActivity.class));
      } else if (i == R.id.tv_about) {//TODO 仅仅测试城市文明分的入口  后期需要删除掉
        //CivilizationActivity.start(this);
        startActivity(new Intent(SettingsActivity.this, AboutActivity.class));

        HashMap<String, String> map = new HashMap<>();
        map.put(EventUtils.KEY_SETING, EventUtils.VALUE_SETTING_ABOUT);
        EventUtils.onEvent(EventUtils.E_PERSONAL_INFO, EventUtils.L_SETTING, map);
      } else if (i == R.id.ll_clear_cache) {
        showClearDialog();

        HashMap<String, String> map = new HashMap<>();
        map.put(EventUtils.KEY_SETING, EventUtils.VALUE_CLEAR_CACHE);
        EventUtils.onEvent(EventUtils.E_PERSONAL_INFO, EventUtils.L_SETTING, map);
      } else if (i == R.id.tv_logout) {
        showChooseDialog();

        HashMap<String, String> map = new HashMap<>();
        map.put(EventUtils.KEY_SETING, EventUtils.VALUE_QUIT_LOGIN);
        EventUtils.onEvent(EventUtils.E_PERSONAL_INFO, EventUtils.L_SETTING, map);
      } else if (i == R.id.tv_account_safety) {
        //if (UserRouterBiz.fetchLoginStateServices().isNeedLogin(SettingsActivity.this, v)) {
        HashMap<String, String> map = new HashMap<>();
        map.put(EventUtils.KEY_SETING, EventUtils.VALUE_CERTIFICAION);
        EventUtils.onEvent(EventUtils.E_PERSONAL_INFO, EventUtils.L_SETTING, map);
        if (hasLoggedOn()){
          PascUserManager.getInstance().toAccount();
        }else {
          PascUserManager.getInstance().toLogin(new PascUserLoginListener() {
            @Override
            public void onLoginSuccess() {
              PascUserManager.getInstance().toAccount();
            }

            @Override
            public void onLoginFailed() {

            }

            @Override
            public void onLoginCancled() {

            }
          });
        }
      } else if (i == R.id.tv_Opinion) {
        //if (UserRouterBiz.fetchLoginStateServices().isNeedLogin(mContext, v)) {
        HashMap<String, String> map = new HashMap<>();
        map.put(EventUtils.KEY_SETING, EventUtils.VALUE_FEEDBACK);
        EventUtils.onEvent(EventUtils.E_PERSONAL_INFO, EventUtils.L_SETTING, map);
        if (!hasLoggedOn()) {
          Bundle bundle = new Bundle();
          bundle.putBoolean(BaseRouterTable.BundleKey.KEY_NEED_LOGIN, true);
          bundle.putInt(LAYOUT_TYPE, 0);
          BaseJumper.jumpBundleARouter(RouterTable.MAIN_FEEDBACK, SettingsActivity.this, 0, bundle);
          return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt(LAYOUT_TYPE, 0);
        BaseJumper.jumpBundleARouter(RouterTable.MAIN_FEEDBACK, SettingsActivity.this, 0, bundle);
      } else if (i == R.id.tv_pwd_reset) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(BaseRouterTable.BundleKey.KEY_NEED_LOGIN, "true");
        InterceptorUtil.instance()
            .startService(SettingsActivity.this, "smt://resetPassword", hashMap,
                new InterceptorUtil.InterceptorCallback() {
                  public void onSuccess(Activity activity, String url, Map<String, String> param) {
                    PascUserManager.getInstance().toPasswordSetOrUpdate();
                  }
                });
      } else if (i == R.id.tv_unregister) {
        BaseJumper.jumpARouter("/app/main/unregist");
      } else if (i == R.id.tv_permission) {
        startActivity(new Intent(SettingsActivity.this, UsePermissionActivity.class));
      }
    }
  };

  private String getPhoneNum() {
    String mobile = null;
    AppProxy appProxy = AppProxy.getInstance();
    IUserManager userManager = appProxy.getUserManager();
    if (userManager != null) {
      mobile = userManager.getMobile();
      if (TextUtils.isEmpty(mobile)) {
        IUserInfo userInfo = userManager.getUserInfo();
        if (userInfo != null) {
          mobile = userInfo.getMobileNo();
        }
      }
    }
    return mobile;
  }

  private boolean hasLoggedOn() {
    return AppProxy.getInstance().getUserManager().isLogin();
  }

  private void showClearDialog() {
    if (clearCacheDialog == null) {
      clearCacheDialog = new ChooseOptionDialog(this, R.layout.mine_confirm_cache);
      clearCacheDialog.setOnSelectedListener(new ChooseOptionDialog.OnSelectedListener() {
        @Override public void onFirst() {

        }

        @Override public void onSecond() {
          HashMap<String, String> map = new HashMap<>();
          map.put("clear_cache_dialog", "确认清理缓存");
          EventUtils.onEvent(EventUtils.E_PERSONAL_INFO,
              EventUtils.L_SETTING, map);
          try {
            showLoading(getString(R.string.mine_clearing_cache));
            CacheUtils.clearAllCache(mContext);
            //refreshCacheSize();
          } catch (Exception e) {
            e.printStackTrace();
          } finally {
            tvCacheSize.postDelayed(new Runnable() {
              @Override public void run() {
                dismissLoading();
                showClearSucceed();
                tvCacheSize.setText("0.00M");
              }
            }, 1000);
          }
        }

        @Override public void onCancel() {
          HashMap<String, String> map = new HashMap<>();
          map.put("clear_cache_dialog", "取消缓存清理");
          EventUtils.onEvent(EventUtils.E_PERSONAL_INFO,
              EventUtils.L_SETTING, map);
        }
      });
    }
    addDialog(clearCacheDialog);
  }

  @Override public void onBackPressed() {
    super.onBackPressed();
    finish();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
    disposables.clear();
    //FixMemLeakUtil.fixLeak(this);
  }

  private void setUpdateMsg(boolean isHasNewApk, boolean isClick) {
    if (isFinishing()) {
      return;
    }
    if (!isHasNewApk && isClick) {
      ToastUtils.toastMsg(R.string.mine_no_update);
      return;
    }
    if (!isClick) {
      tvUpdateHint.setText(isHasNewApk ? getString(R.string.mine_new_version)
          : getString(R.string.mine_no_new_version));
      viewRedDot.setVisibility(isHasNewApk ? View.VISIBLE : View.GONE);
    }
  }

  /**
   * 是否是点击请求的更新逻辑
   */
  private void checkUpdate(final boolean isShowUpdateDialog, final boolean isClick) {
    new UpdateManager.Builder(this)
        .isMainActivity(false) // 是否为主界面
        .showUpdateDialog(isShowUpdateDialog)
        .showNotification(isClick) // 显示通知
        .resumePoint(false)
        .showProgressDialog(isClick) // 显示进度对话框
        .updateCallBack(new UpdateCallBack() {
          @Override
          public void onSuccess() {
          }

          @Override
          public void onHasNewApk(boolean isForce) {
            setUpdateMsg(true, isClick);
          }

          @Override public void onNoNewApk() {
            super.onNoNewApk();
            setUpdateMsg(false, isClick);
          }

          @Override
          public void downLoadProgress(int progress) {
          }

          @Override
          public void onError(String msg) {

          }
        })
        .build().checkUpdate();
  }

  private void showChooseDialog() {
    if (logoutDialog == null) {
      logoutDialog =
          new ChooseOptionDialog(this, R.layout.mine_user_dialog_logout);
      logoutDialog.setOnSelectedListener(new ChooseOptionDialog.OnSelectedListener() {
        @Override public void onFirst() {

        }

        @Override public void onSecond() {
          AppProxy.getInstance().getUserManager().exitUser(SettingsActivity.this);
          //每次退出登录清空信用描述
          //SPUtils.getInstance().setParam(UserConstant.REPORT_SCORE_DESC, "");
          setView();
          //EventBus.getDefault().postSticky(new LoginEvent(0x100));
          //AppProxy.getInstance().getUserManager().exitUser(SettingsActivity.this);
          //其他页面也需要跳转到登陆页，统一改成MyHomeFragment接到EventBus发送的退出事件再跳转到登陆页
          //actionStart(LoginActivity.class);
          BaseEvent event = new BaseEvent(EventConstants.USER_LOGIN_STATUS);
          event.put(EventConstants.STATUS, EventConstants.USER_LOGIN_STATUS_OUT_VALUE);
          EventBus.getDefault().post(event);

          HashMap<String, String> map = new HashMap<>();
          map.put("logout_dialog", "确认退出登录");
          EventUtils.onEvent(EventUtils.E_PERSONAL_INFO,
              EventUtils.L_SETTING, map);
          finish();
        }

        @Override public void onCancel() {
          HashMap<String, String> map = new HashMap<>();
          map.put("logout_dialog", "取消退出登录");
          EventUtils.onEvent(EventUtils.E_PERSONAL_INFO,
              EventUtils.L_SETTING, map);
        }
      });
    }
    if (logoutDialog != null && !logoutDialog.isShowing()) {
      logoutDialog.show();
    }
  }

  private void showClearSucceed() {
    final IconLoadingDialog iconLoadingDialog = new IconLoadingDialog(this, false);
    iconLoadingDialog.setContent(R.string.mine_clearing_succed);
    iconLoadingDialog.show();
    disposables.add(Observable.timer(2, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
      @Override public void accept(Long aLong) throws Exception {
        if (isFinishing()) {
          return;
        }
        iconLoadingDialog.dismiss();
      }
    }));
  }
}