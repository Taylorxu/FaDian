package com.powerge.wise.powerge.otherPages;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.bean.SheBeiRootBean;
import com.powerge.wise.powerge.databinding.ActivitySheBeiInfoBinding;

import java.util.ArrayList;
import java.util.List;

public class SheBeiInfoActivity extends AppCompatActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, SheBeiInfoActivity.class);
        context.startActivity(starter);
    }

    ActivitySheBeiInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_she_bei_info);
        binding.title.setText(getResources().getStringArray(R.array.item_name_array)[4]);
        initView();
    }

    private void initView() {
        createData();
        final ExpandableListAdapter adapter = new ExpandableListAdapter(list);
        binding.contentSheBei.setAdapter(adapter);
        binding.contentSheBei.setGroupIndicator(null);
        binding.contentSheBei.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                boolean groupExpanded = parent.isGroupExpanded(groupPosition);
                adapter.setIndicatorState(groupPosition, groupExpanded);
                return false;
            }
        });
    }

    List<SheBeiRootBean> list = new ArrayList<>();

    private void createData() {
        String[] shebeiName = new String[]{"机组设备", "电气设备", "通风设备"};
        for (int i = 0; i < 3; i++) {
            SheBeiRootBean bean = new SheBeiRootBean();
            bean.setSheBeiName(shebeiName[i]);
            List<SheBeiRootBean.SheBeiChildBean> beiChildBeans = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                SheBeiRootBean.SheBeiChildBean childBean = new SheBeiRootBean.SheBeiChildBean();
                childBean.setName(j + "#" + shebeiName[i]);
                beiChildBeans.add(childBean);
            }
            bean.setSheBeiChild(beiChildBeans);
            list.add(bean);
        }
    }


    public void onClick(View view) {
        if (view.getId() == R.id.btn_back) {
            finish();
        }
    }
}
