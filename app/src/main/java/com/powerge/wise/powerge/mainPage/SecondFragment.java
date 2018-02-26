package com.powerge.wise.powerge.mainPage;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.SimpleListTextItem;
import com.powerge.wise.powerge.databinding.FragmentSecondBinding;
import com.powerge.wise.powerge.databinding.ItemAnnouncesBinding;

import java.util.ArrayList;
import java.util.List;


public class SecondFragment extends Fragment {
    FragmentSecondBinding binding;
    List<SimpleListTextItem> listTextItems = new ArrayList<>();
    public SecondFragment() {

    }

    public SecondFragment newInstance() {
        SecondFragment secondFragment = new SecondFragment();
        return secondFragment;
    }

    XAdapter<SimpleListTextItem, ItemAnnouncesBinding> adapter = new XAdapter.SimpleAdapter<>(BR.textItem, R.layout.item_announces);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container, false);
        initView();
        return binding.getRoot();
    }


    @SuppressLint("ResourceAsColor")
    private void initView() {
        setData();
        binding.refreshLayout.setColorSchemeColors(R.color.colorPrimary);
        binding.refreshLayout.setOnRefreshListener(refreshListener);
        binding.contentAnnounceList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.contentAnnounceList.setAdapter(adapter);
        adapter.setList(listTextItems);

    }

    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
//TODO get data
            setData();

        }
    };

    private void setData() {

        for (int i = 0; i < 10; i++) {
            SimpleListTextItem textItem = new SimpleListTextItem();
            textItem.setTitle("title" + i);
            textItem.setContent("这条公告讲的是" + i);
//                textItem.setDate("");
            listTextItems.add(textItem);
        }
        adapter.notifyDataSetChanged();
        binding.refreshLayout.setRefreshing(false);
    }

}
