package com.powerge.wise.powerge.otherPages.JingSai;


import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.powerge.wise.basestone.heart.network.Notification;
import com.powerge.wise.basestone.heart.ui.WFragment;
import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.basestone.heart.ui.XViewHolder;
import com.powerge.wise.basestone.heart.util.RxBus;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.Items;
import com.powerge.wise.powerge.bean.SimpleListTextItem;
import com.powerge.wise.powerge.databinding.FragmentJingSaiDeFenBinding;
import com.powerge.wise.powerge.databinding.ItemJingSaiDeFenBinding;

import java.util.ArrayList;
import java.util.List;

public class JingSaiDeFenFragment extends WFragment<FragmentJingSaiDeFenBinding> implements View.OnClickListener {
//    FragmentJingSaiDeFenBinding binding = getBinding();

    public JingSaiDeFenFragment() {
    }

    public static JingSaiDeFenFragment newInstance() {

        Bundle args = new Bundle();

        JingSaiDeFenFragment fragment = new JingSaiDeFenFragment();
        fragment.setArguments(args);
        return fragment;
    }

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_jing_sai_de_fen, container, false);
        getData();
        initView();
        return binding.getRoot();
    }*/

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        getData();
        initView();
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_jing_sai_de_fen;
    }


    XAdapter<Items, ItemJingSaiDeFenBinding> adapter = new XAdapter.SimpleAdapter
            <Items, ItemJingSaiDeFenBinding>(0, R.layout.item_jing_sai_de_fen) {
        @Override
        protected void initHolder(XViewHolder<Items, ItemJingSaiDeFenBinding> holder, int viewType) {
            super.initHolder(holder, viewType);

        }

        @Override
        public void onBindViewHolder(XViewHolder<Items, ItemJingSaiDeFenBinding> holder, int position) {
            super.onBindViewHolder(holder, position);
            Items data = getItemData(position);
            holder.getBinding().zhiBiaoName.setText(data.getText1());
            holder.getBinding().zhiBiaoValue.setText(data.getText3());
            holder.getBinding().zuiYou.setText(data.getText4());
            holder.getBinding().shiYing.setText(data.getText5());
            holder.getBinding().junZhi.setText(data.getText6());
            if (position < 3) {
                holder.getBinding().zhiBiaoName.setTextColor(textColor[position]);
                holder.getBinding().zhiBiaoValue.setTextColor(textColor[position]);
                holder.getBinding().zuiYou.setTextColor(textColor[position]);
                holder.getBinding().shiYing.setTextColor(textColor[position]);
                holder.getBinding().junZhi.setTextColor(textColor[position]);

            } else {
                holder.getBinding().zhiBiaoName.setTextColor(textColor[position - 1]);
                holder.getBinding().zhiBiaoValue.setTextColor(textColor[position - 1]);
                holder.getBinding().zuiYou.setTextColor(textColor[position - 1]);
                holder.getBinding().shiYing.setTextColor(textColor[position - 1]);
                holder.getBinding().junZhi.setTextColor(textColor[position - 1]);
            }

        }
    };

    private void initView() {
        getBinding().btnAllZb.setOnClickListener(this);
        getBinding().btnAllJz.setOnClickListener(this);

        getBinding().contentDeFenList.setLayoutManager(new LinearLayoutManager(getContext()));
        getBinding().contentDeFenList.setAdapter(adapter);
        adapter.setList(list);
    }

    List<Items> list = new ArrayList<>();
    String zhi[] = new String[]{"1#机组煤耗", "机组烟尘排放", "机组SO2排放", "机组NOx 排放"};
    int textColor[] = new int[]{
            Color.rgb(227, 30, 77),
            Color.rgb(17, 189, 98),
            Color.rgb(155, 155, 155)};

    private void getData() {
        for (int i = 0; i < 4; i++) {
            Items item = new Items();
            item.setText1(zhi[i]);
            item.setText3("220");
            item.setText4("220-300");
            item.setText5("12.00/13.89");
            item.setText6("899");
            list.add(item);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_all_jz:
                RxBus.getDefault().post(new Notification(001, 0));
                break;
            case R.id.btn_all_zb:
                RxBus.getDefault().post(new Notification(002, 0));
                break;
        }
    }

}
