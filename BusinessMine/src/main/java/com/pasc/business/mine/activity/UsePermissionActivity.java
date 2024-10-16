package com.pasc.business.mine.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.business.mine.R;
import com.pasc.business.mine.config.SettingManager;
import com.pasc.business.mine.util.Constants;
import com.pasc.business.mine.widget.ItemUsePermissionView;
import com.pasc.lib.base.activity.BaseActivity;
import com.pasc.lib.base.permission.PermissionUtils;
import com.pasc.lib.base.util.StatusBarUtils;
import com.pasc.lib.hybrid.PascHybrid;
import com.pasc.lib.widget.toolbar.PascToolbar;

/**
 * 系统权限列表页面
 * Created by lichangbao on 2019/07/26
 */
@Route(path = Constants.PATH_USE_PERSSION_ACT)
public class UsePermissionActivity extends BaseActivity {

  Context mContext;

  //导航栏
  PascToolbar mTitleView;
  //位置信息
  ItemUsePermissionView mLocationView;
  //相机
  ItemUsePermissionView mCameraView;
  //通讯录
  ItemUsePermissionView mContactView;
  //电话
  ItemUsePermissionView mCallPhoneView;
  //相册
  ItemUsePermissionView mPhotoView;
  //麦克风
  ItemUsePermissionView mMicrophoneView;
  private TextView tvHint;
  private String appName;

  @Override protected int layoutResId() {
    return R.layout.mine_activity_use_permission;
  }

  @Override protected void onInit(@Nullable Bundle savedInstanceState) {
    StatusBarUtils.setStatusBarColor(this,true);
    mContext = this;
    appName = getAppName(this);
    initView();
    initListener();
  }

  @Override
  protected void onResume() {
    super.onResume();
    initData();
  }

  private void initView() {
    tvHint=findViewById(R.id.activity_up_hint);
    mTitleView = findViewById(R.id.activity_up_title);
    mLocationView = findViewById(R.id.activity_up_permission_location);
    mCameraView = findViewById(R.id.activity_up_permission_camera);
    mContactView = findViewById(R.id.activity_up_permission_contact);
    mCallPhoneView = findViewById(R.id.activity_up_permission_callphone);
    mPhotoView = findViewById(R.id.activity_up_permission_photo);
    mMicrophoneView = findViewById(R.id.activity_up_permission_microphone);
    tvHint.setText(parseStr(R.string.mine_use_pemission_hint));
    mLocationView.setTitle(parseStr(R.string.setting_use_permission_location_title))
        .setSubTitle(R.string.setting_use_permission_location_subtitle);
    mCameraView.setTitle(parseStr(R.string.setting_use_permission_camera_title))
        .setSubTitle(R.string.setting_use_permission_camera_subtitle);
    mContactView.setTitle(parseStr(R.string.setting_use_permission_contact_title))
        .setSubTitle(R.string.setting_use_permission_contact_subtitle);
    mCallPhoneView.setTitle(parseStr(R.string.setting_use_permission_call_phone_title))
        .setSubTitle(R.string.setting_use_permission_call_phone_subtitle);
    mPhotoView.setTitle(parseStr(R.string.setting_use_permission_photo_title))
        .setSubTitle(R.string.setting_use_permission_photo_subtitle);
    mMicrophoneView.setTitle(parseStr(R.string.setting_use_permission_microphone_title))
        .setSubTitle(R.string.setting_use_permission_microphone_subtitle);
  }

  private void initListener() {
    mTitleView.setBackIconClickListener(() -> {
      finish();
    });
    mLocationView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        PascHybrid.getInstance().start(mContext, SettingManager.getInstance().getPermission(0));
      }
    });

    mCameraView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        PascHybrid.getInstance().start(mContext, SettingManager.getInstance().getPermission(1));
      }
    });

    mContactView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        PascHybrid.getInstance().start(mContext, SettingManager.getInstance().getPermission(2));
      }
    });

    mCallPhoneView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        PascHybrid.getInstance().start(mContext, SettingManager.getInstance().getPermission(3));
      }
    });

    mPhotoView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        PascHybrid.getInstance().start(mContext, SettingManager.getInstance().getPermission(4));
      }
    });

    mMicrophoneView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        PascHybrid.getInstance().start(mContext, SettingManager.getInstance().getPermission(5));
      }
    });
  }

  private void initData() {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

      boolean hasLocationPemission =
          (PermissionUtils.checkPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
              || PermissionUtils.checkPermission(mContext,
              Manifest.permission.ACCESS_FINE_LOCATION));
      boolean hasCameraPemission =
          PermissionUtils.checkPermission(mContext, Manifest.permission.CAMERA);
      boolean hasContactPemission =
          PermissionUtils.checkPermission(mContext, Manifest.permission.READ_CONTACTS);
      boolean hasCallPhonePemission =
          PermissionUtils.checkPermission(mContext, Manifest.permission.READ_PHONE_STATE);
      boolean hasPhotoPemission =
          PermissionUtils.checkPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE);
      boolean hasMicrophonePemission =
          PermissionUtils.checkPermission(mContext, Manifest.permission.RECORD_AUDIO);

      if (hasLocationPemission) {
        mLocationView.setStatus(R.string.setting_use_permission_opened);
      } else {
        mLocationView.setStatus(R.string.setting_use_permission_unopen);
      }

      if (hasCameraPemission) {
        mCameraView.setStatus(R.string.setting_use_permission_opened);
      } else {
        mCameraView.setStatus(R.string.setting_use_permission_unopen);
      }

      if (hasContactPemission) {
        mContactView.setStatus(R.string.setting_use_permission_opened);
      } else {
        mContactView.setStatus(R.string.setting_use_permission_unopen);
      }

      if (hasCallPhonePemission) {
        mCallPhoneView.setStatus(R.string.setting_use_permission_opened);
      } else {
        mCallPhoneView.setStatus(R.string.setting_use_permission_unopen);
      }

      if (hasPhotoPemission) {
        mPhotoView.setStatus(R.string.setting_use_permission_opened);
      } else {
        mPhotoView.setStatus(R.string.setting_use_permission_unopen);
      }

      if (hasMicrophonePemission) {
        mMicrophoneView.setStatus(R.string.setting_use_permission_opened);
      } else {
        mMicrophoneView.setStatus(R.string.setting_use_permission_unopen);
      }
    } else {
      mLocationView.setStatus("");
      mCameraView.setStatus("");
      mContactView.setStatus("");
      mCallPhoneView.setStatus("");
      mPhotoView.setStatus("");
      mMicrophoneView.setStatus("");
    }
  }

  private String parseStr(@StringRes int strRes) {
    try {
      return String.format(getString(strRes), appName);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return getString(strRes);
  }
  public static String getAppName(Context context) {
    PackageManager pm = context.getPackageManager();
    try {
      PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
      ApplicationInfo applicationInfo = packageInfo.applicationInfo;
      int labelRes = applicationInfo.labelRes;
      return context.getResources().getString(labelRes);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }

    return null;
  }
}
