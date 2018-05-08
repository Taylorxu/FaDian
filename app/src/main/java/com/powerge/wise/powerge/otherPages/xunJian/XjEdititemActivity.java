package com.powerge.wise.powerge.otherPages.xunJian;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.XunJianFormBean;
import com.powerge.wise.powerge.databinding.ActivityXjEdititemBinding;

import static com.powerge.wise.powerge.otherPages.xunJian.XjFillFormActivity.extraResult;
import static com.powerge.wise.powerge.otherPages.xunJian.XjFillFormActivity.requestCode;

public class XjEdititemActivity extends AppCompatActivity {
    private ActivityXjEdititemBinding binding;
    public static String extraKeyNitem = "ITEM";
    XunJianFormBean xunJianFormBean;

    public static void start(Activity context, XunJianFormBean item) {
        Intent starter = new Intent(context, XjEdititemActivity.class);
        starter.putExtra(extraKeyNitem, item);
        context.startActivityForResult(starter, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_xj_edititem);
        xunJianFormBean = getIntent().getParcelableExtra(extraKeyNitem);
        initView();
    }

    private void initView() {
        binding.title.setText(xunJianFormBean.getItemName());
        binding.etItemValue.addTextChangedListener(new TextChangedListener());
    }

    public class TextChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            xunJianFormBean.setItemValue(s.toString());
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_done:
                Intent data = new Intent();
                data.putExtra(extraResult, xunJianFormBean);
                setResult(RESULT_OK,data);
                finish();
                break;
        }
    }
}
