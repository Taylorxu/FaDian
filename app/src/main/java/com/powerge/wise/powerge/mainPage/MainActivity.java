package com.powerge.wise.powerge.mainPage;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.powerge.wise.basestone.heart.util.LogUtils;
import com.powerge.wise.powerge.LoginActivity;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.databinding.ActivityMainBinding;
import com.powerge.wise.powerge.helper.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity implements FirstFragment.OnFirstFragmentInteractionListener {
    private MainAdapter mSectionsPagerAdapter;
    ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mSectionsPagerAdapter = new MainAdapter(getSupportFragmentManager());
        mainBinding.container.setAdapter(mSectionsPagerAdapter);
        mainBinding.container.addOnPageChangeListener(onPageChangeListener);
        mainBinding.container.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                final float alphaValue = Math.abs(Math.abs(position) - 1);
                page.setAlpha(alphaValue);
            }
        });
        BottomNavigationViewHelper.disableShiftMode(mainBinding.navigation);
        mainBinding.navigation.setOnNavigationItemSelectedListener(OnNISListener);

    }


    private BottomNavigationView.OnNavigationItemSelectedListener OnNISListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mainBinding.container.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    mainBinding.container.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    mainBinding.container.setCurrentItem(2);
                    return true;
                case R.id.navigation_mine:
                    mainBinding.container.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    mainBinding.navigation.setSelectedItemId(R.id.navigation_home);
                    break;
                case 1:
                    mainBinding.navigation.setSelectedItemId(R.id.navigation_dashboard);
                    break;
                case 2:
                    mainBinding.navigation.setSelectedItemId(R.id.navigation_notifications);
                    break;
                case 3:
                    mainBinding.navigation.setSelectedItemId(R.id.navigation_mine);
                    break;
            }

        }
    };

    /**
     * 第一个fragment的接口
     *
     * @param uri
     */
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
