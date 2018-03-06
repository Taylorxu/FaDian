package com.powerge.wise.powerge.otherPages.PlanTasksMag;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

/**
 * Created by Administrator on 2018/3/6.
 */

public class PlanTasksViewPagerAd extends FragmentPagerAdapter {
    public PlanTasksViewPagerAd(FragmentManager fm) {
        super(fm);
    }

    Fragment fragments[] = new Fragment[]{PlanTasksDailyFragment.newInstance(), PlanTasksSpecialFragment.newInstance()};
    String pageTitles[] = new String[]{"日常", "专项"};

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles[position];
    }
}
