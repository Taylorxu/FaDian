package com.powerge.wise.powerge.operationProjo.net.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.bean.EventClassificationModel;
import com.powerge.wise.powerge.operationProjo.net.view.TempTreeSelectionDataManager;
import com.powerge.wise.powerge.operationProjo.net.view.mview.MyTitle;
import com.powerge.wise.powerge.operationProjo.net.view.mview.NextView;
import com.powerge.wise.powerge.operationProjo.net.view.mview.WorkOrderDataManager;

import java.io.Serializable;
import java.util.List;



public class EventClassificationActivity extends BaseActivity {

    private MyTitle mt_event;
    private RecyclerView rv_event;
    private String id;

    public static void startSelf(Context context, List<EventClassificationModel> datas, String id) {
        Intent intent = new Intent(context, EventClassificationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("datas", (Serializable) datas);
        intent.putExtras(bundle);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_classification);
        TempTreeSelectionDataManager.getManager().addEventClassificationActivity(this);
        init();
        request();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TempTreeSelectionDataManager.getManager().removeEventClassificationActivity(this);
    }

    private void request() {
        List<EventClassificationModel> datas = (List<EventClassificationModel>) getIntent().getSerializableExtra("datas");
        id = getIntent().getStringExtra("id");
        initData(datas);
    }

    private void initData(List<EventClassificationModel> datas) {
        MyAdapter adapter = new MyAdapter(this, datas);
        rv_event.setAdapter(adapter);
    }

    private void init() {
        mt_event = (MyTitle) findViewById(R.id.mt_event_classification);
        rv_event = (RecyclerView) findViewById(R.id.rv_event_classification);

        mt_event.setBack(true, this);
        mt_event.setTitle(getResources().getString(R.string.event_classification));

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rv_event.setLayoutManager(manager);
    }

    private class MyAdapter extends RecyclerView.Adapter {

        private Context context;
        private List<EventClassificationModel> datas;

        public MyAdapter(Context context, List<EventClassificationModel> datas) {
            this.context = context;
            this.datas = datas;
        }

        private class MyHolder extends RecyclerView.ViewHolder {
            NextView tv;
            public MyHolder(View itemView) {
                super(itemView);
                tv = (NextView) itemView.findViewById(R.id.nv_item_event_classification);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_event_classification, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyHolder) {
                final EventClassificationModel model = datas.get(position);
                ((MyHolder) holder).tv.setData(model.getDictName());
                if (model.getList() == null || model.getList().size() == 0) {
                    ((MyHolder) holder).tv.setIV(false);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String temp = model.getDictId();
                            WorkOrderDataManager.getManager().setSingleDateById(id,temp);
                            TempTreeSelectionDataManager.getManager().setTemp(temp);
                            TempTreeSelectionDataManager.getManager().clearEventClassificationActivity();
                        }
                    });
                } else {
                    ((MyHolder) holder).tv.setIV(true);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            datas = model.getList();
                            EventClassificationActivity.startSelf(EventClassificationActivity.this, datas,id);
                        }
                    });
                }
            }
        }

        @Override
        public int getItemCount() {
            return (datas == null || datas.size() == 0) ? 0 : datas.size();
        }
    }
}
