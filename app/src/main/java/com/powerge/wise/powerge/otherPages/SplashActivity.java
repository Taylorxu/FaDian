package com.powerge.wise.powerge.otherPages;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.mainPage.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.start(getBaseContext());
                finish();
            }
        }, 3000);

    }
}
