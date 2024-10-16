package com.pasc.business.feedback;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pasc.lib.base.activity.BaseActivity;
import com.pasc.lib.base.util.StatusBarUtils;

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
  }
}
