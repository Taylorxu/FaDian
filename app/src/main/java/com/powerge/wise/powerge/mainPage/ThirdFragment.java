package com.powerge.wise.powerge.mainPage;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.basestone.heart.ui.view.PagingRecyclerView;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.SimpleListTextItem;
import com.powerge.wise.powerge.databinding.FragmentThirdBinding;
import com.powerge.wise.powerge.databinding.ItemAnnouncesBinding;

import java.util.ArrayList;
import java.util.List;


public class ThirdFragment extends Fragment implements View.OnClickListener {
    FragmentThirdBinding binding;


    public ThirdFragment() {

    }

    public static ThirdFragment newInstance() {
        ThirdFragment secondFragment = new ThirdFragment();
        return secondFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_third, container, false);
        initView();
        return binding.getRoot();
    }

    XAdapter<SimpleListTextItem, ItemAnnouncesBinding> adapter = new XAdapter.SimpleAdapter<>(BR.textItem, R.layout.item_announces);

    private void initView() {
        binding.title.setText("消息");
        binding.btnBack.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.contentView.setLayoutManager(layoutManager);
        binding.contentView.setItemAnimator(new DefaultItemAnimator());
        binding.contentView.setHasFixedSize(true);

        binding.contentView.setOnLoadMoreListener(onLoadMoreListener);
        binding.refreshLayout.setOnRefreshListener(onRefreshListener);
        binding.refreshLayout.setColorSchemeResources(R.color.colorPrimary);

        binding.contentView.setAdapter(adapter);

    }

    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            binding.contentView.setState(PagingRecyclerView.State.Refresh);

        }
    };
    PagingRecyclerView.OnLoadMoreListener onLoadMoreListener = new PagingRecyclerView.OnLoadMoreListener() {
        @Override
        public void onLoadMore(int page) {
            setData(page);
        }
    };

    private void setData(int page) {
        if (page == 1) {
            List<SimpleListTextItem> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                SimpleListTextItem textItem = new SimpleListTextItem();
                textItem.setTitle("title" + i);
                textItem.setContent("这条公告讲的是" + i);
//                textItem.setDate("");
                list.add(textItem);
            }
            adapter.setList(list);
            binding.contentView.setState(list == null || list.size() < 10 ? PagingRecyclerView.State.NoMore : PagingRecyclerView.State.LoadSuccess);
        } else {
            List<SimpleListTextItem> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                SimpleListTextItem textItem = new SimpleListTextItem();
                textItem.setTitle("title" + i);
                textItem.setContent("这条公告讲加载出来的" + i);
//                textItem.setDate("");
                list.add(textItem);
            }
            adapter.addItems(list);
            binding.contentView.setState(list == null || list.size() < 10 ? PagingRecyclerView.State.NoMore : PagingRecyclerView.State.LoadSuccess);
        }
        binding.refreshLayout.setRefreshing(false);
    }

    public void onClick(View view) {
        getActivity().finish();
    }

}
