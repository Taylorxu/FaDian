package com.powerge.wise.powerge.operationProjo.net.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.operationProjo.net.BaseActivity;
import com.powerge.wise.powerge.operationProjo.net.view.mview.MyTitle;
import com.powerge.wise.powerge.operationProjo.net.view.mview.SeachView;




public class SelectPersonnelActivity extends BaseActivity {

    private MyTitle mt_person;
    private SeachView sv_person;
    private RecyclerView rv_person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_personnel);
        init();
    }

    private void init() {
        mt_person = (MyTitle) findViewById(R.id.mt_select_person);
        sv_person = (SeachView) findViewById(R.id.sv_select_person);
        rv_person = (RecyclerView) findViewById(R.id.rv_select_person);

        mt_person.setBack(true, this);
        mt_person.setTitle(getResources().getString(R.string.select_person));
    }
}
