package com.powerge.wise.powerge.operationProjo.net.view.mview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.powerge.wise.powerge.R;
import com.powerge.wise.powerge.operationProjo.net.utils.DisplayUtils;
import com.powerge.wise.powerge.operationProjo.net.view.StandBookView;


/**
 * Created by ycs on 2016/12/17.
 */

public class ListDateView extends LinearLayout{

    private LinearLayout ll_date_list;
    private LinearLayout ll_list_date;
    private TextView tv_list_date;
    private TextView tv_list_date_title;
    private ImageView iv_list_date_view;
    private IListDateClickListener listDateClickListener;

    public ListDateView(Context context) {
        super(context);
        init(context);
    }
    private void init(final Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.list_date_view,this,true);
        tv_list_date_title = view.findViewById(R.id.tv_list_date_title);
        iv_list_date_view = view.findViewById(R.id.iv_list_date_view);
        ll_list_date = view.findViewById(R.id.ll_list_date);
        ll_date_list = view.findViewById(R.id.ll_date_list);
        tv_list_date = view.findViewById(R.id.tv_list_date);
        ll_list_date.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listDateClickListener != null){
                    listDateClickListener.setOnAddView();
                }
            }
        });
        iv_list_date_view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_list_date_view.setSelected(!iv_list_date_view.isSelected());
                if(iv_list_date_view.isSelected()){
                    ll_date_list.setVisibility(VISIBLE);
                    ll_list_date.setVisibility(VISIBLE);
                }else {
                    ll_date_list.setVisibility(GONE);
                    ll_list_date.setVisibility(GONE);
                }
            }
        });
        iv_list_date_view.setSelected(false);
        ll_date_list.setVisibility(GONE);
        ll_list_date.setVisibility(GONE);
    }

    /**
     * 这个是父类的方法，给不同子类设置不同的属性的
     * @param context
     * @param reId
     * @param text
     */
    public void initView(Context context,int reId,String text){
        tv_list_date.setText(text);
        Drawable male = getResources().getDrawable(reId);
        male.setBounds(0, 0, DisplayUtils.sp2px(context, 15), DisplayUtils.sp2px(context, 15));
        tv_list_date.setCompoundDrawables(male, null, null, null);//男
    }
    public void setTitle(String title){
        if(!TextUtils.isEmpty(title)){
            tv_list_date_title.setText(title);
        }
    }

    /**
     * 添加表格view
     * @param standBookView
     */
    public void addStandBook(final StandBookView standBookView){
        ll_date_list.addView(standBookView);
        standBookView.setOnIStandBookClickListener(new StandBookView.IStandBookClickListener() {
            @Override
            public void setDel() {
                removeView(standBookView);
                listDateClickListener.setOnDelView();
            }
        });
        int count = ll_date_list.getChildCount();
        if(count>0){
            View view = ll_date_list.getChildAt(0);
            if(view instanceof StandBookView){
                ((StandBookView)view).setDeleteHide();
            }
        }
    }
    private void removeView(StandBookView standBookView){
        ll_date_list.removeView(standBookView);
    }
    public void setIListDataClickListener(IListDateClickListener listDataClickListener){
        this.listDateClickListener = listDataClickListener;
    }

    public interface IListDateClickListener{
        void setOnAddView();
        void setOnDelView();
    }
    public void clearView(){
        ll_date_list.removeAllViews();
    }
}
