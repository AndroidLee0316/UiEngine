package com.pasc.business.affair.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.business.affair.router.ARouterPath;
import com.pasc.lib.affair.R;
import com.pasc.lib.base.activity.BaseActivity;

/**
 * 功能：
 * <p>
 * created by zoujianbo345
 * data : 2018/9/2
 */
@Route(path = ARouterPath.AFFAIR_WORK_SCHEDULE)
public class WorkScheduleActivity extends BaseActivity {

    @Override protected int layoutResId() {
        return R.layout.activity_work_schedule;
    }

    @Override protected void onInit(@Nullable Bundle bundle) {
        setTitle("办事进度");
    }
}
