package com.pasc.business.workspace;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.pasc.lib.log.PascLog;
import com.pasc.business.workspace.content.HotseatDisplayItem;
import com.pasc.business.workspace.widget.Hotseat;
import com.pasc.business.workspace.widget.HotseatItemView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author 陈上勇
 * @version v1.0
 * @des 首页模板activity，是抽象出来的一个主界面UI调度类：主要完成多fragment的管理和切换逻辑
 * @date 2018-06-12
 * @modify On 2018-06-28 by author for reason ...
 * @Note: 此类是抽象的共性类，如果需要修改请在组里面吱一声
 */
public abstract class BaseTabActivity extends FragmentActivity implements Hotseat.OnHotseatClickListener {
    //onSaveInstanceState接口对fragment现场保存的标识，为解决单activity多fragment界面现场恢复异常【这个标识copy from system】
    protected static final String FRAGMENTS_TAG = "android:support:fragments";    // 设置下标参数
    protected static final String BUNDLE_TAB_INDEX = "bundle_tab_index";

    private static final String TAG = BaseTabActivity.class.getSimpleName();

    private final int INIT_CAPACITY_HOTSEAT_ITME_COUNT = 4;

    protected Hotseat mHotseat;
    protected ArrayList<HotseatDisplayItem> mHotseatDisplayInfos = new ArrayList<>(INIT_CAPACITY_HOTSEAT_ITME_COUNT);
    private HashMap<String, Fragment> mHomeActivityFragmentsCache = new HashMap<>(INIT_CAPACITY_HOTSEAT_ITME_COUNT);
    //需要记切换前的tagIndex,方便隐藏之前的然后显示新的
    private String mSaveClassId = "";

    protected boolean isNeedShowTitle() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // mInstance = this;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        if (!isNeedShowTitle()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        super.setContentView(layoutResID);
    }

    @Override
    public void onItemClick(int tagIndex) {
        PascLog.i(TAG, "onItemClick:" + tagIndex);
        switchFragment(tagIndex, null, true);
    }

    @Override
    public boolean onItemDoubleClick(int tagIndex) {
        final String classId = mHotseat.getFouceTabClassId();
        if (TextUtils.isEmpty(classId)) {
            PascLog.e(TAG, "当前 FouceTabClassId 怎么会为空咧？？");
            return false;
        }

        Fragment fragment = mHomeActivityFragmentsCache.get(classId);
        if (null == fragment) {
            PascLog.e(TAG, "当前 FouceTabClassId的Fragment怎么会为空咧？？");
            return false;
        }

        return false;
    }

    @Override
    public void updateActionBar(HotseatItemView.ActionBarInfo actionBarInfo) {
        // Title
    }

    private void switchFragment(int tagIndex) {
        switchFragment(tagIndex, null);
    }

    private void switchFragment(int tagIndex, Bundle bundle) {
        PascLog.i(TAG, "switchFragment:" + tagIndex);
        switchFragment(tagIndex, bundle, false);
    }

    private void switchFragment(int tagIndex, Bundle bundle, boolean fromHotseat) {
        PascLog.i(TAG, "switchFragment:" + tagIndex);
        switchFragment(tagIndex, bundle, fromHotseat, false);
    }

    protected void switchFragment(final int tagIndex, Bundle bundle, boolean fromHotseat, boolean anim) {
        if (tagIndex < 0) {
            PascLog.e(TAG, "我的乖乖，怎么会有位置是：" + tagIndex + " 的内容可以切换咧，得check一下是否Hotseat没有内容？");
            return;
        }

        final HotseatItemView.ComponentName componentName = getComponentNameByTabIndex(tagIndex);
        if(componentName==null){
            return;
        }
        final String classId = componentName.getClassId();
        if (mSaveClassId.endsWith(classId)) {
            PascLog.e(TAG, "同一个fragment不需要切换，直接return ~");
            return;
        }

        PascLog.i(TAG, "switchFragment:" + tagIndex);
        Fragment fragment = getFragment(componentName);
        if (null == fragment) {
            Toast.makeText(this, "switchFragment:" + tagIndex + " getFragment return null classId", Toast.LENGTH_LONG).show();
            return;
        }
        fragment.setArguments(bundle);

        if (!fromHotseat) {
            mHotseat.setFocusIndex(tagIndex);
        }

        //注意这里不能缓存FragmentTransaction 需要每次都去get
        FragmentTransaction ft = anim ? getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.workspace_enter_from_right, R.anim.workspace_exit_to_left, R.anim.workspace_enter_from_left, R.anim.workspace_exit_to_right) :
                getSupportFragmentManager().beginTransaction();

        if (null != mHomeActivityFragmentsCache.get(mSaveClassId)) {
            ft.hide(mHomeActivityFragmentsCache.get(mSaveClassId));
        }

        if (!fragment.isAdded()) {
            ft.add(getContentId(), fragment, fragment.getClass().getSimpleName());
        }

        ft.show(fragment).commitAllowingStateLoss();

        mSaveClassId = classId;
    }

    private HotseatItemView.ComponentName getComponentNameByTabIndex(int tagIndex) {
        return mHotseat.getComponentNameByTagIndex(tagIndex);
    }

    //index是不固定的，唯一固定的是classID,因此需要将tab的index转成对应的classID
    private Fragment getFragment(HotseatItemView.ComponentName componentName) {
        PascLog.i(TAG, "getFragment:" + componentName);
        final String classId = componentName.getClassId();
        if (TextUtils.isEmpty(classId)) {
            return null;
        }

        if (mHomeActivityFragmentsCache.get(componentName.getClassId()) != null) {
            return mHomeActivityFragmentsCache.get(componentName.getClassId());
        }

        Fragment fragment = null;
        String msg = "";

        PascLog.i(TAG, "getFragmentByPos to get Plugin fragement:" + classId);
        Class<?> cls = null;
        String clsName = componentName.getFullName();
        if (clsName != null) {
            try {
                cls = getClassLoader().loadClass(clsName);
            } catch (ClassNotFoundException e) {
                PascLog.e(TAG, "loadPluginFragmentClassById:" + classId + " ClassNotFound:" + clsName + "Exception", e);
                PascLog.w(TAG, "没有找到：" + clsName + " 是不是被混淆了~");
            }
        }

        if (cls != null) {
            try {
                fragment = (Fragment) cls.newInstance();
            } catch (InstantiationException e) {
                PascLog.e(TAG, "InstantiationException", e);
            } catch (IllegalAccessException e) {
                PascLog.e(TAG, "IllegalAccessException", e);
            }
        }

        msg = "Not found classId：" + classId + "，请先check提供这个Fragment的插件是否有安装哈(⊙o⊙)~";

        if (fragment == null) {
            fragment = new ToastFragment();
            ((ToastFragment) fragment).setToastMsg(msg);
        }

        mHomeActivityFragmentsCache.put(classId, fragment);

        return fragment;// new Fragment();
    }

    // for child
    protected void init(Bundle savedInstanceState) {
        //初始化插件的显示info
        initDisplayInfo();
        initHotseat();

        //初始化View
        initView(savedInstanceState);
    }

    protected void initHotseat() {
        // step1：get到控件对象
        if (mHotseat == null) {
            mHotseat = findViewById(getHotseatId());
        }

        if (mHotseat == null) {
            throw new IllegalArgumentException("mHotseat can not be null!");
        }

        // step2：初始化内容
        mHotseat.clear();
        // 根据具体的MainActivity来配置
        for (HotseatDisplayItem item : mHotseatDisplayInfos) {
            // 当前Hotseat上暂时之放置fragment
            if (item.action_type != HotseatDisplayItem.TYPE_FRAGMENT) {
                continue;
            }

            mHotseat.addCellItem(item);
        }

        // step3：设置监听
        mHotseat.addHotseatClickObserver(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // checkUpgradeInfo();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        PascLog.i(TAG, " onNewIntent");
        setIntent(intent);
        int index = intent.getIntExtra(BUNDLE_TAB_INDEX, -1);
        if (index >= 0) {
            switchFragment(index);
        }
    }

    // 主动去销毁一个Activity时，如:按返回键，onSaveInstanceState()就不会被调用
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // 当前不对mFragments进行状态保存[恢复的时候状态错乱了]，在这里通过mCurrentPage来保存并进行恢复
        if (outState != null) {
            outState.remove(FRAGMENTS_TAG);

            if (!TextUtils.isEmpty(mSaveClassId)) {
                outState.putInt(BUNDLE_TAB_INDEX, mHotseat.getPosByClassId(mSaveClassId));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void initDisplayInfo() {
        mHotseatDisplayInfos.clear();
    }

    protected abstract int getContentId();

    protected abstract int getHotseatId();

    protected abstract int getDefaultTabIndex();

    //启动的时候需要加装一些必要的tab
    protected void preLoadTabFragment() {
        //switchFragment(tagIndex, bundle, false);
    }

    //初始化View
    protected void initView(Bundle savedInstanceState) {
        // step3：默认聚焦位置
        final int index = savedInstanceState != null ? savedInstanceState.getInt(BUNDLE_TAB_INDEX,
                getDefaultTabIndex()) : getIntent().getIntExtra(BUNDLE_TAB_INDEX, getDefaultTabIndex());

        //非焦点的tab,也需要提前加载好的内容，如果没有可以不复写
        preLoadTabFragment();

        switchFragment(index, null, false);
    }
}
