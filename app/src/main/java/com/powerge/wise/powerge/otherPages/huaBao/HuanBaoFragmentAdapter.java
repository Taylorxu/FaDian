package com.powerge.wise.powerge.otherPages.huaBao;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2018/3/2.
 */

public class HuanBaoFragmentAdapter extends FragmentPagerAdapter {

    Fragment fragments[] = new Fragment[]{new HuanBaoJianCeFragment().newInstance(), new HuanBaoKaoHeFragment().newInstance()};
    String title[] = new String[]{"监测", "考核"};

    public HuanBaoFragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

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
        return title[position];
    }
}
