package com.powerge.wise.powerge.operationProjo.net.view.mview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wisesignsoft.OperationManagement.R;

import java.util.List;

/**
 * Created by ycs on 2016/11/25.
 */

public class DownBoxView extends RelativeLayout {

    private TextView tv_down;
    private ImageView iv_down;
    private RelativeLayout rl_down;

    public DownBoxView(Context context, List<String> list) {
        super(context);
        init(context, list);
    }

    private void init(final Context context, final List<String> list) {
        View view = LayoutInflater.from(context).inflate(R.layout.down_box_view, this, true);
        rl_down = (RelativeLayout) view.findViewById(R.id.rl_down_box_view);
        tv_down = (TextView) view.findViewById(R.id.tv_down_box_view);
        iv_down = (ImageView) view.findViewById(R.id.iv_down_box_view);
        rl_down.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupwindow(context, rl_down, list);
            }
        });
    }

    private void showPopupwindow(Context context, View view, final List<String> list) {
        View converview = LayoutInflater.from(context).inflate(R.layout.down_box_popup_view, null);
        ListView lv = (ListView) converview.findViewById(R.id.lv_down_box_view);
        final PopupWindow popupWindow = new PopupWindow(context);
        popupWindow.setContentView(converview);
        MyAdapter myAdapter = new MyAdapter(context, list);
        lv.setAdapter(myAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str = list.get(i);
                tv_down.setText(str);
                popupWindow.dismiss();
            }
        });
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_round_app));
        popupWindow.showAsDropDown(view);
    }

    private static class MyAdapter extends BaseAdapter {
        private Context context;
        private List<String> list;

        public MyAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            MyHolder holder;
            if (view == null) {
                holder = new MyHolder();
                view = LayoutInflater.from(context).inflate(R.layout.item_popup_tv, viewGroup, false);
                holder.tv = (TextView) view.findViewById(R.id.tv);
                view.setTag(holder);
            } else {
                holder = (MyHolder) view.getTag();
            }
            holder.tv.setText(list.get(i));
            return view;
        }

        private static class MyHolder {
            TextView tv;
        }
    }
}
