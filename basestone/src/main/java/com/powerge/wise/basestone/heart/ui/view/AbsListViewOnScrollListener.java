package com.powerge.wise.basestone.heart.ui.view;

import android.widget.AbsListView;

/**
 * Created by Administrator on 2018/3/14.
 */

public abstract class AbsListViewOnScrollListener implements AbsListView.OnScrollListener {

    //是否正在上拉数据
    private boolean loading = true;
    private int currentPage = 0;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem) {
            currentPage++;
            onLoadMore(currentPage);
            loading = true;
        }
    }

    public abstract void onLoadMore(int currentPage);
}
