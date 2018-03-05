package com.powerge.wise.powerge.otherPages.queXian;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.powerge.wise.powerge.otherPages.huaBao.HuanBaoJianCeFragment;
import com.powerge.wise.powerge.otherPages.huaBao.HuanBaoKaoHeFragment;

/**
 * Created by Administrator on 2018/3/5.
 */

public class QxVPAdapter extends FragmentPagerAdapter {
    Fragment fragments[] = new Fragment[]{QxOnItFragment.newInstance(), QxDoneFragment.newInstance(), QxDelayFragment.newInstance()};
    String title[] = new String[]{"处理中", "已完成", "未处理"};

    public QxVPAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
