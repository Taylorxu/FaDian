package com.wisesignsoft.OperationManagement.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wisesignsoft.OperationManagement.BaseFragment;
import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.adapter.ContractFragmentAdapter;
import com.wisesignsoft.OperationManagement.net.RequestTask;
import com.wisesignsoft.OperationManagement.net.request.RequestDept;
import com.wisesignsoft.OperationManagement.net.response.FindAllDeptTreeResponse;
import com.wisesignsoft.OperationManagement.ui.activity.SelectDeptActivity;
import com.wisesignsoft.OperationManagement.ui.activity.SelectUserActivity;
import com.wisesignsoft.OperationManagement.utils.DividerItemDecoration;
import com.wisesignsoft.OperationManagement.view.mview.LoadingView;
import com.wisesignsoft.OperationManagement.view.mview.SeachView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ycs on 2016/11/18.
 */

public class ContactFragment extends BaseFragment implements SeachView.ISearchView {

    private SeachView sv_contract;
    private RecyclerView rv_contract;
    private List<FindAllDeptTreeResponse.Children> datas = new ArrayList<>();
    private ContractFragmentAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        init(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        request();
    }

    private void init(View view) {
        sv_contract = (SeachView) view.findViewById(R.id.sv_contract_fragment);
        rv_contract = (RecyclerView) view.findViewById(R.id.rv_contract_fragment);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        rv_contract.setLayoutManager(manager);
        rv_contract.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        adapter = new ContractFragmentAdapter(getContext(), datas);
        rv_contract.setAdapter(adapter);

        sv_contract.setISearchViewListener(this);
        sv_contract.setHint("请输入用户名");
    }

    private void request() {
        final LoadingView loadingView = LoadingView.getLoadingView(getContext());
        loadingView.show();
        List<String> list = new ArrayList<>();
        RequestDept.findAllDeptTree(list, new RequestTask.ResultCallback<FindAllDeptTreeResponse>() {
            @Override
            public void onError(Exception e) {
                loadingView.stop(loadingView);
            }

            @Override
            public void onResponse(FindAllDeptTreeResponse response) {
                loadingView.stop(loadingView);
                if (datas != null) {
                    datas.clear();
                }
                datas.addAll(response.getResult().get(0).getChildren());
                initData();
            }
        });
    }

    private void initData() {
        adapter.notifyDataSetChanged();
        adapter.setOnContractListener(new ContractFragmentAdapter.IContractFragmentListener() {
            @Override
            public void setOnNameClickListener(String id) {
                SelectUserActivity.startSelf(getContext(), id,"");
            }

            @Override
            public void setOnEntityClickListener(List<FindAllDeptTreeResponse.Children> datas) {
                SelectDeptActivity.startSelf(getContext(), datas);
            }
        });
    }

    @Override
    public void setOnSearchListener(String key) {
        SelectUserActivity.startSelf(getContext(), "",key);
    }

    @Override
    public void setOnCancelListener() {

    }
}
