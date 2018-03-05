package com.powerge.wise.powerge.otherPages.queXian;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.Items;
import com.powerge.wise.powerge.databinding.FragmentQxOnItBinding;
import com.powerge.wise.powerge.databinding.ItemQxFlBinding;

import java.util.ArrayList;
import java.util.List;

public class QxOnItFragment extends Fragment {

    public QxOnItFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createData();
    }


    public static QxOnItFragment newInstance() {
        QxOnItFragment fragment = new QxOnItFragment();
        return fragment;
    }

    FragmentQxOnItBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_qx_on_it, container, false);
        binding.contentQxOn.setLayoutManager(new LinearLayoutManager(getContext()));
        XAdapter<Items, ItemQxFlBinding> adapter = new XAdapter.SimpleAdapter<>(BR.item, R.layout.item_qx_fl);
        binding.contentQxOn.setAdapter(adapter);
        adapter.setList(list);
        return binding.getRoot();
    }

    List<Items> list = new ArrayList<>();

    private void createData() {
        for (int i = 0; i < 10; i++) {
            Items item = new Items();
            item.setText1("非常紧急");
            item.setText3("李大锤");
            item.setText4("天小官");
            item.setText5("烦闷爆开了，无法关闭");
            list.add(item);
        }
    }

}
