package com.powerge.wise.powerge.otherPages.xunJian;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.basestone.heart.ui.XViewHolder;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.Detail;
import com.powerge.wise.powerge.databinding.ActivityXunJianDianSignListBinding;
import com.powerge.wise.powerge.databinding.ItemSignTimeListBinding;

import java.util.ArrayList;
import java.util.List;

public class XunJianDianSignListActivity extends AppCompatActivity {
    ActivityXunJianDianSignListBinding binding;
    public String titleText;
    public static String titleKey = "TITLE", listKey = "LIST";
    List<Detail> list = new ArrayList<>();

    public static void start(Context context, List<?> listValue, String title) {
        Intent starter = new Intent(context, XunJianDianSignListActivity.class);
        starter.putExtra(titleKey, title);
        starter.putParcelableArrayListExtra(listKey, (ArrayList<? extends Parcelable>) listValue);
        context.startActivity(starter);
    }

    XAdapter<Detail, ItemSignTimeListBinding> adapter = new XAdapter.SimpleAdapter<Detail, ItemSignTimeListBinding>(0, R.layout.item_sign_time_list) {
        @Override
        public void onBindViewHolder(XViewHolder<Detail, ItemSignTimeListBinding> holder, int position) {
            super.onBindViewHolder(holder, position);
            holder.getBinding().setSignTime(getItemData(position));
            holder.getBinding().setIndex(String.valueOf(position + 1));

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_xun_jian_dian_sign_list);
        titleText = getIntent().getStringExtra(titleKey);
        binding.title.setText(titleText);
        list = getIntent().getParcelableArrayListExtra(listKey);
        binding.contentSignTime.setLayoutManager(new LinearLayoutManager(this));
        binding.contentSignTime.setAdapter(adapter);
        adapter.setItemClickListener(itemClickListener);
        adapter.setList(list);
    }

    XAdapter.OnItemClickListener<Detail, ItemSignTimeListBinding> itemClickListener = new XAdapter.OnItemClickListener<Detail, ItemSignTimeListBinding>() {
        @Override
        public void onItemClick(XViewHolder<Detail, ItemSignTimeListBinding> holder) {
            XjFillFormActivity.starter(getBaseContext(), false, holder.getBinding().getSignTime().getDetail(), titleText);
        }
    };

    public void onClick(View view) {
        finish();
    }

}
