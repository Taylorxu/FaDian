package com.powerge.wise.powerge.otherPages.xunJian;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2018/3/12.
 */

public class XunJianDatePagerAdapter extends FragmentPagerAdapter {
    public XunJianDatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    String pageTitle[] = new String[]{"月", "周", "日"};

    @Override
    public Fragment getItem(int position) {
        return XunJianDateFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitle[position];
    }
}
