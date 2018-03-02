package com.powerge.wise.powerge.otherPages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.powerge.wise.basestone.heart.ui.EndLessOnScrollListener;
import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.SimpleListTextItem;
import com.powerge.wise.powerge.databinding.ActivityMorningMeetingBinding;
import com.powerge.wise.powerge.databinding.ItemZhiZhangLogesBinding;

import java.util.ArrayList;
import java.util.List;

public class MorningMeetingActivity extends AppCompatActivity {

    List<SimpleListTextItem> listTextItems = new ArrayList<>();

    public static void start(Context context) {
        Intent starter = new Intent(context, MorningMeetingActivity.class);
        context.startActivity(starter);
    }

    ActivityMorningMeetingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_morning_meeting);
        binding.title.setText(getResources().getStringArray(R.array.item_name_array)[6]);
        initView();
    }

    XAdapter<SimpleListTextItem, ItemZhiZhangLogesBinding> adapter = new XAdapter.SimpleAdapter<>(BR.textItem, R.layout.item_zhi_zhang_loges);

    @SuppressLint("ResourceAsColor")
    private void initView() {
        binding.refreshLayout.setColorSchemeColors(R.color.colorPrimary);
        binding.refreshLayout.setOnRefreshListener(refreshListener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.contentLog.setLayoutManager(layoutManager);
        binding.contentLog.setAdapter(adapter);
        adapter.setList(listTextItems);
        binding.contentLog.setOnScrollListener(new EndLessOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                refreshData(currentPage);
            }
        });
    }


    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
//TODO get data
            refreshData(1);

        }
    };


    private void refreshData(int currentPage) {

        for (int i = 0; i < 10; i++) {
            SimpleListTextItem textItem = new SimpleListTextItem();
            textItem.setTitle("值长:阿拉丁" + i);
            textItem.setContent("今天做了" + i + "见事情");
            textItem.setLogTitle(i + "#机组的日志");
//                textItem.setDate("");
            listTextItems.add(textItem);
        }
        adapter.notifyDataSetChanged();
        binding.refreshLayout.setRefreshing(false);
    }

    public void onClick(View view) {
        finish();
    }
}
