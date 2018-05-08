package com.powerge.wise.powerge.otherPages.xunJian;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.databinding.ActivityXjEdititemBinding;

import static com.powerge.wise.powerge.otherPages.xunJian.XjFillFormActivity.requestCode;

public class XjEdititemActivity extends AppCompatActivity {
    private ActivityXjEdititemBinding binding;
    public static String extraKeyName = "ITEMNAME";
    private String title;

    public static void start(Activity context, String itemName) {
        Intent starter = new Intent(context, XjEdititemActivity.class);
        starter.putExtra(extraKeyName, itemName);
        context.startActivityForResult(starter,requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_xj_edititem);
        title = getIntent().getStringExtra(extraKeyName);
        initView();
    }

    private void initView() {
        binding.title.setText(title);
        createView();
    }

    private void createView() {

    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_done:
                break;
        }
    }
}
