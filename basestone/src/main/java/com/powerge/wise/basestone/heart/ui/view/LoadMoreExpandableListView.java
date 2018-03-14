package com.powerge.wise.basestone.heart.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;

import com.powerge.wise.basestone.R;

/**
 * Created by Administrator on 2018/3/14.
 */

public class LoadMoreExpandableListView extends ExpandableListView implements AbsListView.OnScrollListener {

    private Context mContext;
    private View mFootView;
    private int mTotalItemCount;
    private OnLoadMoreListener mLoadMoreListener;
    private boolean mIsLoading=false;

    public LoadMoreExpandableListView(Context context) {
        super(context);
        init(context);
    }

    public LoadMoreExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadMoreExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // 滑到底部后自动加载，判断listview已经停止滚动并且最后可视的条目等于adapter的条目
        int lastVisibleIndex=view.getLastVisiblePosition();
        if (!mIsLoading&&scrollState == OnScrollListener.SCROLL_STATE_IDLE
                && lastVisibleIndex ==mTotalItemCount-1) {
            mIsLoading=true;
            addFooterView(mFootView);
            if (mLoadMoreListener!=null) {
                mLoadMoreListener.onloadMore();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mTotalItemCount=totalItemCount;
    }


    private void init(Context context){
        this.mContext=context;
        mFootView= LayoutInflater.from(context).inflate(R.layout.foot_view,null);
        setOnScrollListener(this);
    }


    public void setOnLoadMoreListener(OnLoadMoreListener listener){
        mLoadMoreListener=listener;
    }

    public interface OnLoadMoreListener{
        void onloadMore();
    }
    public void setLoadCompleted(){
        mIsLoading=false;
        removeFooterView(mFootView);
    }
}
