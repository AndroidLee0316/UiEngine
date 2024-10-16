package com.pasc.pascuiengine;

import android.content.Intent;
import android.os.Bundle;

import com.amap.api.maps.MapsInitializer;
import com.pasc.business.affair.activity.AffairsPageFragment;
import com.pasc.business.life.LifePageFragment;
import com.pasc.business.mine.tangram.SampleMinePageFragment;
import com.pasc.business.workspace.BaseTabActivity;
import com.pasc.business.workspace.content.HotseatDisplayItem;
import com.pasc.lib.statistics.StatisticsManager;
import com.pasc.lib.widget.toast.Toasty;

public class MainActivity extends BaseTabActivity {

    //首页下标
    public static final String CLASS_ID_HOME = "home_tab";
    public static final int TAB_HOME = 0;

    //政务下标
    public static final String CLASS_ID_AFFAIRS = "affairs_tab";
    public static final int TAB_AFFAIRS = TAB_HOME + 1;

    //生活下标
    public static final String CLASS_ID_LIFE = "life_tab";
    public static final int TAB_LIFE = TAB_HOME + 2;
    //我的下标
    public static final String CLASS_ID_SETTINGS = "settings_tab";
    public static final int TAB_SETTINGS = TAB_HOME + 3;
    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(savedInstanceState);
//        MapsInitializer.updatePrivacyShow(this,true,true);
//        MapsInitializer.updatePrivacyAgree(this,true);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }


    @Override
    protected int getContentId() {
        return R.id.home_fragment_container;
    }

    @Override
    protected int getHotseatId() {
        return R.id.home_bottom_tab;
    }

    @Override
    protected int getDefaultTabIndex() {
        return 0;
    }

    @Override
    protected void onDestroy() {
        //PascWebSocketManager.getInstance().close();
        super.onDestroy();

    }


    @Override
    protected void initDisplayInfo() {
        super.initDisplayInfo();
        HotseatDisplayItem mainTab = new HotseatDisplayItem(CLASS_ID_HOME, HotseatDisplayItem.TYPE_FRAGMENT, MyMainPageFragment.class.getName(), R.string.tab_name_home, R.drawable.workspace_tab_home, R.drawable.workspace_tab_home_selected,
                R.color.workspace_tab_title_normal, R.color.workspace_tab_title_selected, null, null, TAB_HOME);
        mHotseatDisplayInfos.add(mainTab);


        HotseatDisplayItem affairsTab = new HotseatDisplayItem(CLASS_ID_AFFAIRS, HotseatDisplayItem.TYPE_FRAGMENT, AffairsPageFragment.class.getName(), R.string.tab_name_affairs, R.drawable.workspace_tab_affair, R.drawable.workspace_tab_affair_selected,
                R.color.workspace_tab_title_normal, R.color.workspace_tab_title_selected, null, null, TAB_AFFAIRS);
        mHotseatDisplayInfos.add(affairsTab);

        HotseatDisplayItem lifeTab = new HotseatDisplayItem(CLASS_ID_LIFE, HotseatDisplayItem.TYPE_FRAGMENT, LifePageFragment.class.getName(), R.string.tab_name_life, R.drawable.workspace_tab_life, R.drawable.workspace_tab_life_select,
                R.color.workspace_tab_title_normal, R.color.workspace_tab_title_selected, null, null, TAB_LIFE);
        mHotseatDisplayInfos.add(lifeTab);

        HotseatDisplayItem meTab = new HotseatDisplayItem(CLASS_ID_SETTINGS, HotseatDisplayItem.TYPE_FRAGMENT,
                SampleMinePageFragment.class.getName(), R.string.tab_name_setting, R.drawable.workspace_tab_mine, R.drawable.workspace_tab_mine_selected,
                R.color.workspace_tab_title_normal, R.color.workspace_tab_title_selected, null, null, TAB_SETTINGS);
        mHotseatDisplayInfos.add(meTab);

    }

    @Override
    protected void switchFragment(int tagIndex, Bundle bundle, boolean fromHotseat, boolean anim) {
        super.switchFragment(tagIndex, bundle, fromHotseat, anim);

        StatisticsManager.getInstance().onEvent("app_tab", getEventLable(tagIndex));
    }

    @Override
    public void onItemClick(int tagIndex) {
        super.onItemClick(tagIndex);
    }

    private String getEventLable(int index) {
        switch (index) {
            case 0:
                return "首页";
            case 1:
                return "办事";
            case 2:
                return "生活";
            case 3:
                return "我的";
            default:
                return "首页";
        }
    }


    @Override
    public void onBackPressed() {
        exit();
    }


    /**
     * 双击退出
     */
    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toasty.init(this).setMessage("再按一次退出").stayShort().show();
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }
}
