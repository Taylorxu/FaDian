package com.powerge.wise.powerge.otherPages.issues;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2018/3/30.
 */

public class IssuesFragmentsAdapter extends FragmentPagerAdapter {
    Fragment fragments[] = new Fragment[]{IssuesWaittingFragment.newInstance(), IssuseWorkingFragment.newInstance(), IssuesDoneFragment.newInstance()};

    public IssuesFragmentsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
