package com.pasc.business.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pasc.business.mine.util.EventUtils;
import com.pasc.lib.base.activity.BaseActivity;
import com.pasc.lib.base.util.StatusBarUtils;
import com.pasc.lib.statistics.StatisticsManager;

/**
 * 统一处理statusbar
 * Created by zhangxu678 on 2018/12/6.
 */
public class BaseStatusActivity extends BaseActivity {
  @Override protected int layoutResId() {
    return 0;
  }

  @Override protected void onInit(@Nullable Bundle bundle) {
    StatusBarUtils.setStatusBarColor(this,true);
  }

  @Override protected void onResume() {
    super.onResume();
    StatusBarUtils.setStatusBarColor(this,true);
    EventUtils.onResume(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
    EventUtils.onPause(this);
  }
}
