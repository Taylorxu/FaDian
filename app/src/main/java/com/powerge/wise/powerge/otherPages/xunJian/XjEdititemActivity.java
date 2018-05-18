package com.powerge.wise.powerge.otherPages.xunJian;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.basestone.heart.ui.XViewHolder;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.XunJianFormBean;
import com.powerge.wise.powerge.databinding.ActivityXjEdititemBinding;
import com.powerge.wise.powerge.databinding.ItemXjRadiobtListBinding;

import java.util.ArrayList;
import java.util.List;

import static com.powerge.wise.powerge.otherPages.xunJian.XjFillFormActivity.extraResult;
import static com.powerge.wise.powerge.otherPages.xunJian.XjFillFormActivity.requestCode;

public class XjEdititemActivity extends AppCompatActivity {
    private ActivityXjEdititemBinding binding;
    public static String extraKeyNitem = "ITEM";
    private XunJianFormBean xunJianFormBean;

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
        putData();
        initView();
    }

    private void initView() {
        binding.title.setText(xunJianFormBean.getCheckItem());
        if (xunJianFormBean.imgvisibility() == View.VISIBLE) {//单选列表
            binding.rtMultiple.setVisibility(View.VISIBLE);
            binding.rtMultiple.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            binding.rtMultiple.setAdapter(adapter);
            adapter.setItemClickListener(itemClickListener);
        } else {
            binding.etItemValue.setVisibility(View.VISIBLE);
            binding.etItemValue.addTextChangedListener(new TextChangedListener());
        }

    }

    String options[] = new String[]{"是", "否"};
    XAdapter<OneValueClass, ItemXjRadiobtListBinding> adapter = new XAdapter.SimpleAdapter<OneValueClass, ItemXjRadiobtListBinding>(0, R.layout.item_xj_radiobt_list) {
        @Override
        public void onBindViewHolder(XViewHolder<OneValueClass, ItemXjRadiobtListBinding> holder, int position) {
            super.onBindViewHolder(holder, position);
            OneValueClass data = getItemData(position);
            holder.getBinding().rbName.setText(options[data.getKey()]);
            holder.getBinding().rbName.setTag(data.getKey());
            holder.getBinding().ivState.setAlpha(0f);
        }
    };
    private ImageView oldImageView = null;
    XAdapter.OnItemClickListener<XunJianFormBean, ItemXjRadiobtListBinding> itemClickListener = new XAdapter.OnItemClickListener<XunJianFormBean, ItemXjRadiobtListBinding>() {
        @Override
        public void onItemClick(XViewHolder<XunJianFormBean, ItemXjRadiobtListBinding> holder) {
            if (oldImageView != null) {
                oldImageView.animate().scaleX(0f).scaleY(0f).alpha(0f).setDuration(100);
            }
            holder.getBinding().ivState.animate().scaleX(1f).scaleY(1f).alpha(1f).setDuration(300);
            oldImageView = holder.getBinding().ivState;
            xunJianFormBean.setRadioBtResult(Integer.parseInt(holder.getBinding().rbName.getTag().toString()));
        }
    };


    public class TextChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            xunJianFormBean.setCheckResult(s.toString());
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
                setResult(RESULT_OK, data);
                finish();
                break;
        }
    }


    private void putData() {
        List<OneValueClass> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            OneValueClass one = new OneValueClass();
            one.setKey(i);
            list.add(one);
        }
        adapter.setList(list);
    }

    class OneValueClass {
        private int key;

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }
    }
}
