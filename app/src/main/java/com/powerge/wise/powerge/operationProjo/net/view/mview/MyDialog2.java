package com.powerge.wise.powerge.operationProjo.net.view.mview;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.powerge.wise.powerge.R;


/**
 * Created by ycs on 2016/12/5.
 */

public class MyDialog2 extends Dialog implements View.OnClickListener {

    private TextView tv_delete1;
    private TextView tv_delete2;
    private IDelete iDelete;

    public MyDialog2(Context context) {
        super(context,R.style.my_dialog);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_dialog2,null);
        tv_delete1 = view.findViewById(R.id.tv_delete1);
        tv_delete2 = view.findViewById(R.id.tv_delete2);
        setContentView(view);
        tv_delete1.setOnClickListener(this);
        tv_delete2.setOnClickListener(this);
    }

    public void setiDelete(IDelete iDelete){
        this.iDelete = iDelete;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tv_delete1) {
            if (iDelete != null) {
                iDelete.setDelete1();
            }
            cancel();
            dismiss();

        } else if (i == R.id.tv_delete2) {
            if (iDelete != null) {
                iDelete.setDelete2();
            }
            cancel();
            dismiss();

        }
    }

    public interface IDelete{
        void setDelete1();
        void setDelete2();
    }
}
