package com.powerge.wise.powerge.helper;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2018/3/23.
 */

public class MeasureListView extends RecyclerView {
    public MeasureListView(Context context) {
        super(context);
    }

    public MeasureListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MeasureListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, mExpandSpec);
    }
}
