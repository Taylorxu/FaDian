package com.powerge.wise.powerge.otherPages.xunJian;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.SimpleListTextItem;
import com.powerge.wise.powerge.databinding.ActivityXunJianDianSignListBinding;
import com.powerge.wise.powerge.databinding.ItemSignTimeListBinding;

import java.util.ArrayList;
import java.util.List;

public class XunJianDianSignListActivity extends AppCompatActivity {
    ActivityXunJianDianSignListBinding binding;
    public String title;
    public static String titleKey = "TITLE";
    List<SimpleListTextItem> list = new ArrayList<>();

    public static void start(Context context, String titleValue) {
        Intent starter = new Intent(context, XunJianDianSignListActivity.class);
        starter.putExtra(titleKey, titleValue);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_xun_jian_dian_sign_list);
        binding.title.setText(getIntent().getStringExtra(titleKey));
        getData();
        XAdapter<SimpleListTextItem, ItemSignTimeListBinding> adapter = new XAdapter.SimpleAdapter<>(BR.signTime, R.layout.item_sign_time_list);
        binding.contentSignTime.setLayoutManager(new LinearLayoutManager(this));
        binding.contentSignTime.setAdapter(adapter);
        adapter.setList(list);
    }

    private void getData() {
        for (int i = 0; i < 10; i++) {
            SimpleListTextItem textItem = new SimpleListTextItem();
            textItem.setTitle(i + "");
            list.add(textItem);
        }
    }
}
