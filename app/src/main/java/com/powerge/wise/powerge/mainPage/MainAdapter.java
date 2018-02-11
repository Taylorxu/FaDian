package com.powerge.wise.powerge.mainPage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2018/1/30.
 */

public class MainAdapter extends FragmentPagerAdapter {
    public MainAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FirstFragment().newInstance();
            case 1:
                return new SecondFragment().newInstance();
            case 2:
                return new ThirdFragment().newInstance();
            case 3:
                return new FourthFragment().newInstance();
        }
        return new FirstFragment().newInstance();
    }

    @Override
    public int getCount() {
        return 4;
    }
}
