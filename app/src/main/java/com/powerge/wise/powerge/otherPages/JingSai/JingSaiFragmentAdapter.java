package com.powerge.wise.powerge.otherPages.JingSai;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2018/3/2.
 */

public class JingSaiFragmentAdapter extends FragmentPagerAdapter {

    Fragment fragments[] = new Fragment[]{JingSaiPaiMingFragment.newInstance(), new JingSaiDeFenFragment().newInstance()};
    String title[] = new String[]{"得分排名", "事实得分"};

    public JingSaiFragmentAdapter(FragmentManager fragmentManager) {
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
