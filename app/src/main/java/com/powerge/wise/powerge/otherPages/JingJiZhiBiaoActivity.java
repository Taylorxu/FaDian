package com.powerge.wise.powerge.otherPages;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.powerge.wise.basestone.heart.network.FlatMapResponse;
import com.powerge.wise.basestone.heart.network.FlatMapTopRes;
import com.powerge.wise.basestone.heart.network.ResultModel;
import com.powerge.wise.basestone.heart.ui.XAdapter;
import com.powerge.wise.basestone.heart.util.DensityUtil;
import com.powerge.wise.powerge.BR;
import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.User;
import com.powerge.wise.powerge.bean.ZhiBIaoValueBean;
import com.powerge.wise.powerge.bean.ZhiBaioNameBean;
import com.powerge.wise.powerge.config.soap.ApiService;
import com.powerge.wise.powerge.config.soap.request.BaseUrl;
import com.powerge.wise.powerge.config.soap.request.RequestBody;
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope;
import com.powerge.wise.powerge.databinding.ActivityJingJiZhiBiaoBinding;
import com.powerge.wise.powerge.databinding.ItemJiZuZhiBiaoBinding;
import com.powerge.wise.powerge.databinding.ItemZbNameListBinding;
import com.powerge.wise.powerge.databinding.ItemZbValueListBinding;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class JingJiZhiBiaoActivity extends AppCompatActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, JingJiZhiBiaoActivity.class);
        context.startActivity(starter);
    }

    ActivityJingJiZhiBiaoBinding binding;
    List<ZhiBaioNameBean> baioNameList = new ArrayList<>();
    XAdapter<ZhiBaioNameBean, ItemZbNameListBinding> nameListXAdapter = new XAdapter.SimpleAdapter<>(BR.data, R.layout.item_zb_name_list);
    XAdapter<ZhiBIaoValueBean.DetailsBean, ItemZbValueListBinding> valueListXAdapter = new XAdapter.SimpleAdapter<>(BR.data, R.layout.item_zb_value_list);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_jing_ji_zhi_biao);
        binding.title.setText(getResources().getStringArray(R.array.item_name_array)[2]);
//        getZBNameList();
        getZBValueList();

    }


    private void setScrollListenter() {
        binding.zhiBiaoListId.setOnScrollListener(new MyScrollListener());
        for (int i = 0; i < binding.contentJiZu.getChildCount(); i++) {
            LinearLayout view = (LinearLayout) binding.contentJiZu.getChildAt(i);
            ListView listview = (ListView) view.getChildAt(2);
            listview.setOnScrollListener(new MyScrollListener());
        }
    }


    private class MyScrollListener implements AbsListView.OnScrollListener {


        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
// 关键代码
            if (scrollState == SCROLL_STATE_IDLE
                    || scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                View subView = view.getChildAt(0);
                if (subView != null) {
                    final int top = subView.getTop();
                    final int position = view.getFirstVisiblePosition();
                    for (int i = 0; i < binding.contentJiZu.getChildCount(); i++) {
                        LinearLayout view1 = (LinearLayout) binding.contentJiZu.getChildAt(i);
                        ListView listview = (ListView) view1.getChildAt(2);
                        listview.setSelectionFromTop(position, top);
                    }
                    binding.zhiBiaoListId.setSelectionFromTop(position, top);
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            // 关键代码
            View subView = view.getChildAt(0);
            if (subView != null) {
                final int top = subView.getTop();
                for (int i = 0; i < binding.contentJiZu.getChildCount(); i++) {
                    LinearLayout view1 = (LinearLayout) binding.contentJiZu.getChildAt(i);
                    ListView listview = (ListView) view1.getChildAt(2);
                    listview.setSelectionFromTop(firstVisibleItem, top);
                }
                binding.zhiBiaoListId.setSelectionFromTop(firstVisibleItem, top);
            }
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btn_back) {
            finish();
        }
    }

    /**
     * 获取指标名称列表
     */
    private void getZBNameList() {
        ZhiBaioNameBean nameBean = new ZhiBaioNameBean();
        nameBean.setNameSpace(BaseUrl.NAMESPACE_P);
        nameBean.setUserName(User.getCurrentUser().getName());
        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(nameBean));
        ApiService.Creator.get().queryEconomicIndicatorsList(RequestEnvelope.getRequestEnvelope())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModel<List<ZhiBaioNameBean>>>())
                .flatMap(new FlatMapTopRes<List<ZhiBaioNameBean>>())
                .subscribe(new Subscriber<List<ZhiBaioNameBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<ZhiBaioNameBean> list) {
                        if (list.size() > 0) {
                            Adapter adapter = new Adapter(list);
                            binding.zhiBiaoListId.setAdapter(adapter);
                        }
                    }
                });

    }

    private void getZBValueList() {
        ZhiBIaoValueBean valueBean = new ZhiBIaoValueBean();
        valueBean.setNameSpace(BaseUrl.NAMESPACE_P);
        valueBean.setUserName(User.getCurrentUser().getName());
        RequestEnvelope.getRequestEnvelope().setBody(new RequestBody<>(valueBean));
        ApiService.Creator.get().queryEconomicIndicators(RequestEnvelope.getRequestEnvelope())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new FlatMapResponse<ResultModel<List<ZhiBIaoValueBean>>>())
                .flatMap(new FlatMapTopRes<List<ZhiBIaoValueBean>>())
                .subscribe(new Subscriber<List<ZhiBIaoValueBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<ZhiBIaoValueBean> list) {
                        if (list.size() > 0) {
                            createZBValueView(list);
                            createZBNameList(list.get(0).getDetails());
                        }
                    }
                });
    }

    /**
     * 自己组织指标列表集合
     *
     * @param list
     */
    public void createZBNameList(List<ZhiBIaoValueBean.DetailsBean> list) {
        List<ZhiBaioNameBean> zbListAdapter = new ArrayList<>();
        for (ZhiBIaoValueBean.DetailsBean bean : list) {
            ZhiBaioNameBean baioNameBean = new ZhiBaioNameBean();
            baioNameBean.setName(bean.getName());
            zbListAdapter.add(baioNameBean);
        }

        Adapter adapter = new Adapter(zbListAdapter);
        binding.zhiBiaoListId.setAdapter(adapter);
    }

    private void createZBValueView(List<ZhiBIaoValueBean> list) {
        WindowManager wm1 = this.getWindowManager();
        int width1 = wm1.getDefaultDisplay().getWidth() / 2 - DensityUtil.dip2px(this,50);
        for (int i = 0; i < list.size(); i++) {
            LinearLayout view = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_ji_zu_zhi_biao, binding.contentJiZu, false);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(width1, LinearLayout.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(param);
            ItemJiZuZhiBiaoBinding itemBiaoBinding = DataBindingUtil.bind(view.getRootView());
            itemBiaoBinding.zbValueList.setAdapter(new Adapter1(list.get(i).getDetails()));
            itemBiaoBinding.setItemZhiBiao(list.get(i));
            binding.contentJiZu.addView(view);
        }

        setScrollListenter();

    }


    ItemZbNameListBinding nameListBinding;
    ItemZbValueListBinding valueListBinding;

    class Adapter extends BaseAdapter {
        List<ZhiBaioNameBean> list;

        public Adapter(List<ZhiBaioNameBean> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                nameListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_zb_name_list, parent, false);
                convertView = nameListBinding.getRoot();
                convertView.setTag(nameListBinding);
            } else {
                nameListBinding = (ItemZbNameListBinding) convertView.getTag();
            }
            nameListBinding.setData(list.get(position));
            return convertView;
        }
    }


    class Adapter1 extends BaseAdapter {
        List<ZhiBIaoValueBean.DetailsBean> list;

        public Adapter1(List<ZhiBIaoValueBean.DetailsBean> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                valueListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_zb_value_list, parent, false);
                convertView = valueListBinding.getRoot();
                convertView.setTag(valueListBinding);
            } else {
                valueListBinding = (ItemZbValueListBinding) convertView.getTag();
            }
            valueListBinding.setData(list.get(position));
            return convertView;
        }
    }


}
