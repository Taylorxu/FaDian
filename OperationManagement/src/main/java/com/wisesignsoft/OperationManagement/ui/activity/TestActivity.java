package com.wisesignsoft.OperationManagement.ui.activity;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.net.RequestTask;
import com.wisesignsoft.OperationManagement.net.request.RequestDict;
import com.wisesignsoft.OperationManagement.net.response.QueryValidCiByModelNameResponse;
import com.wisesignsoft.OperationManagement.view.mview.RefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    private List<String> dataList = new ArrayList<>();
    private RefreshRecyclerView rv;
    private MyAdapter myAdapter;
    private SwipeRefreshLayout srl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        srl = (SwipeRefreshLayout) findViewById(R.id.srl);
        srl.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_blue_light, android.R.color.holo_green_light);
        rv = (RefreshRecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setLoadMoreEnable(true);//允许加载更多
        rv.setFooterResource(R.layout.foot_view);//设置脚布局
        myAdapter = new MyAdapter(dataList);
        rv.setAdapter(myAdapter);

    }

    public void button(View view) {
        List<String> list = new ArrayList<>();
        list.add("INC_TYPE");
        RequestDict.queryValidCiByModelName(list, new RequestTask.ResultCallback<QueryValidCiByModelNameResponse>() {
            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onResponse(QueryValidCiByModelNameResponse response) {
//
//                EventClassificationActivity.startSelf(TestActivity.this, datas);
//                Log.i("YCS", "onResponse: datas：" + datas.size());
//                Log.i("YCS", "onResponse: listsize:" + datas.get(0).getList().size());
//                Log.i("YCS", "onResponse: listsizelistsize:" + datas.get(0).getList().get(0).getList().size());
//                Log.i("YCS", "onResponse: listsizelistsizelistsize:" + datas.get(0).getList().get(0).getList().get(0).getList());
            }
        });
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            dataList.add("数据" + i);
        }
        rv.notifyData();
    }

    private void initListener() {
        rv.setOnLoadMoreListener(new RefreshRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMoreListener() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            dataList.add("更多数据" + i);
                        }
                        rv.notifyData();//刷新数据
                    }
                }, 2000);
            }
        });
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dataList.clear();
                        initData();
                        srl.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }


    private Handler handler = new Handler();

    private static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


        private List<String> dataList;

        public MyAdapter(List<String> dataList) {
            this.dataList = dataList;
        }

        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.foot_view, parent, false));
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
            holder.tv.setText(dataList.get(position));
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView tv;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.tv_foot_view);
            }
        }

        @Override
        public int getItemCount() {
            return (dataList == null || dataList.size() == 0) ? 0 : dataList.size();
        }
    }
}
