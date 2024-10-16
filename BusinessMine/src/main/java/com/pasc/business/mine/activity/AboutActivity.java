package com.pasc.business.mine.activity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.pasc.business.mine.R;
import com.pasc.business.mine.config.AboutManager;
import com.pasc.business.mine.util.EventUtils;
import com.pasc.business.ota.UpdateManager;
import com.pasc.lib.base.util.AppUtils;
import com.pasc.lib.base.util.ToastUtils;
import com.pasc.lib.base.widget.LoadingDialog;
import com.pasc.lib.hybrid.PascHybrid;
import com.pasc.lib.hybrid.nativeability.WebStrategy;
import com.pasc.lib.ota.callback.UpdateCallBack;
import com.pasc.lib.widget.toolbar.PascToolbar;

import java.util.HashMap;

/**
 * 关于界面
 * Created by ruanwei489 on 2018/1/22.
 */

public class AboutActivity extends BaseStatusActivity {

  public static final String ABOUT_ME = "https://smt-web-stg.pingan.com.cn:10443/nantong/protocol";
  //    public static final String ABOUT_ME = "https://smt-web.pingan.com.cn/sz/smt/protocal";

  Context mContext;
  TextView tvAboutVersion;
  View mCheckVersionRL;
  TextView tvCheckVersion;
  TextView tvProtocol;
  TextView tvPrivatePolicy;
  View serviceDivider;
  private LoadingDialog loadingDialog;
  private PascToolbar mToolBar;
  private RoundedImageView roundedImageView;
  private TextView tvUpdateHint;
  private View viewRedDot;
  private View viewVersion;
  private View tvCopyright;

  @Override
  protected int layoutResId() {
    return R.layout.mine_activity_about;
  }

  @Override
  protected void onInit(@Nullable Bundle bundle) {
    mContext = this;
    initView();
    checkUpdate(false, false);
    updateUIFromConfig();
  }

  private void updateUIFromConfig() {
    viewVersion.setVisibility(
        AboutManager.getInstance().showVersion() ? View.VISIBLE : View.GONE);
    tvProtocol.setVisibility(
        AboutManager.getInstance().showProtocol() ? View.VISIBLE : View.GONE);
    tvCopyright.setVisibility(
        AboutManager.getInstance().showCopyright() ? View.VISIBLE : View.GONE);
  }

  private void initView() {

    viewVersion = findViewById(R.id.ll_version);
    tvCopyright = findViewById(R.id.tv_copyright);
    mCheckVersionRL = findViewById(R.id.rl_check_version);
    tvAboutVersion = findViewById(R.id.tv_about_version);
    tvCheckVersion = findViewById(R.id.tv_check_version);
    tvProtocol = findViewById(R.id.tv_protocol);
    roundedImageView = findViewById(R.id.riv_icon);
    mToolBar = findViewById(R.id.common_about_title);
    tvUpdateHint = findViewById(R.id.tv_update_hint);
    viewRedDot = findViewById(R.id.iv_red_dot);
    tvPrivatePolicy = findViewById(R.id.tv_private_policy);
    serviceDivider = findViewById(R.id.service_divider);
    mToolBar.addCloseImageButton().setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    tvAboutVersion.setText("v" + AppUtils.getVersionName(this));
    mCheckVersionRL.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        HashMap<String, String> map = new HashMap<>();
        map.put("check_version", "点击触发版本更行");
        EventUtils.onEvent(EventUtils.E_PERSONAL_INFO,
            EventUtils.L_ABOUT, map);
        checkUpdate(true, true);
      }
    });
    tvProtocol.setOnClickListener(v -> {
      //                Dove.getInstance().start(AboutActivity.this, new WebStrategy().setUrl(ABOUT_ME));
      HashMap<String, String> map = new HashMap<>();
      map.put("about_us", "服务协议btn被点击");
      EventUtils.onEvent(EventUtils.E_PERSONAL_INFO,
          EventUtils.L_ABOUT, map);

      PascHybrid.getInstance()
          .start(AboutActivity.this, new WebStrategy().setUrl(
              TextUtils.isEmpty(AboutManager.getInstance().getProtocolUrl()) ? ABOUT_ME
                  : AboutManager.getInstance().getProtocolUrl()));
    });
    if (TextUtils.isEmpty(AboutManager.getInstance().getPrivatePolicy())) {
      serviceDivider.setVisibility(View.INVISIBLE);
      tvPrivatePolicy.setVisibility(View.GONE);
    } else {
      serviceDivider.setVisibility(View.VISIBLE);
      tvPrivatePolicy.setVisibility(View.VISIBLE);
    }

    tvPrivatePolicy.setOnClickListener(view -> {
      PascHybrid.getInstance()
          .start(AboutActivity.this,
              new WebStrategy().setUrl(AboutManager.getInstance().getPrivatePolicy()));
    });
  }

  private void loadLauncher() {
    PackageManager packageManager = null;
    ApplicationInfo applicationInfo = null;
    try {
      packageManager = getApplicationContext()
          .getPackageManager();
      applicationInfo = packageManager.getApplicationInfo(
          getPackageName(), 0);
    } catch (PackageManager.NameNotFoundException e) {
      applicationInfo = null;
    }
    Drawable d = packageManager.getApplicationIcon(applicationInfo);
    roundedImageView.setImageDrawable(d);
    roundedImageView.setOval(true);
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
    //UpdateManager.getInstance().checkUpdate(this, false, isShowUpdateDialog, new UpdateCallBack() {
    //    @Override
    //    public void onStart() {
    //        super.onStart();
    //        if (loadingDialog == null){
    //            loadingDialog = new LoadingDialog(mContext, R.string.list_loading);
    //        }
    //        if (isShowUpdateDialog) {
    //            loadingDialog.show();
    //        }
    //    }
    //
    //    @Override
    //    public void onSuccess() {
    //        if (loadingDialog != null) {
    //            loadingDialog.dismiss();
    //        }
    //    }
    //
    //    @Override
    //    public void onNoNewApk() {
    //        if (loadingDialog != null) {
    //            loadingDialog.dismiss();
    //        }
    //        setUpdateMsg(false, isClick);
    //    }
    //
    //    @Override
    //    public void onHasNewApk(boolean isForceUpdate) {
    //        super.onHasNewApk(isForceUpdate);
    //        setUpdateMsg(true, isClick);
    //    }
    //
    //    @Override
    //    public void onError(String msg) {
    //        ToastUtils.toastMsg(msg);
    //        if (loadingDialog != null) {
    //            loadingDialog.dismiss();
    //        }
    //    }
    //});

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

  @Override
  protected void onResume() {
    super.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
  }
}
