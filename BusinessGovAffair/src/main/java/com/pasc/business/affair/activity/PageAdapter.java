package com.pasc.business.affair.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * 功能：
 * <p>
 * created by zoujianbo345
 * data : 2018/8/27
 */
public class PageAdapter extends FragmentStatePagerAdapter {
    public String[] getTabs() {
        return tabs;
    }

    private String[] tabs = new String[]{ "时政", "社会", "经济", "部门", "镇区"};

    private Fragment[] fragments;

    public PageAdapter(FragmentManager fm) {
        super(fm);
        initFragments();
    }

    private void initFragments() {
        fragments = new Fragment[]{
                NewsFragment.newInstance("05"),
                NewsFragment.newInstance("07"),
                NewsFragment.newInstance("06"),
                NewsFragment.newInstance("08"),
                NewsFragment.newInstance("09"),
        };
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
