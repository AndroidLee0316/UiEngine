package com.pasc.business.mine.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import java.util.List;

/**
 * 功能：
 * <p>
 * created by zoujianbo345
 * data : 2018/10/11
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    //标题
    private List<String> mTitles;
    private List<Fragment> mFragments;

    public ViewPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments,
                            List<String> titles) {
        super(fragmentManager);
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (mFragments != null) {
            return mFragments.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        return mTitles != null ? mTitles.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles != null) {
            return mTitles.get(position);
        }
        return null;
    }

    public void setItems(List<Fragment> fragments, List<String> titles) {
        this.mFragments = fragments;
        this.mTitles = titles;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
