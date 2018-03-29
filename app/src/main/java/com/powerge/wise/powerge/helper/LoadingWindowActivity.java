package com.powerge.wise.powerge.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.databinding.ActivityLoadingWindowBinding;
import com.powerge.wise.powerge.databinding.ActivityLoginBinding;

public class LoadingWindowActivity extends Activity {
    public static String LOADINGTEXT = "LOADINGTEXT";
    ActivityLoadingWindowBinding binding;

    public static void start(Context context, String loadingText) {
        Intent starter = new Intent(context, LoadingWindowActivity.class);
        starter.putExtra(LOADINGTEXT, loadingText);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_loading_window);
        binding.loadingText.setText(getIntent().getStringExtra(LOADINGTEXT));
    }
}
